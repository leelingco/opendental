//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.ClaimL;
import OpenDental.ClearinghouseL;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.CCDField;
import OpenDental.Eclaims.CCDFieldInputter;
import OpenDental.Eclaims.FormCCDPrint;
import OpenDental.FormClaimPrint;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.CanadianNetwork;
import OpenDentBusiness.CanadianNetworks;
import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.YN;

public class Canadian   
{
    /**
    * Field A06. The third character is for version of OD. Code OD1 corresponds to all versions 11.x.x.x, code OD2 corresponds to all versions 12.x.x.x, etc...
    */
    public static String softwareSystemId() throws Exception {
        return "OD1";
    }

    //Version appVersion=new Version(Application.ProductVersion);
    //return "OD"+(appVersion.Major%10);
    /**
    * Called directly instead of from Eclaims.SendBatches.  Includes one claim.  Sets claim status internally if successfully sent.  Returns the EtransNum of the ack.  Includes various user interaction such as displaying of messages, printing, triggering of COB claims, etc.  For a normal claim, primaryEOB will be blank.  But if this is a COB(type7), then we need to embed the primary EOB by passing it in. The queueItem.ClearinghouseNum must refer to a valid Canadian clearinghouse.
    */
    public static long sendClaim(ClaimSendQueueItem queueItem, boolean doPrint) throws Exception {
        Clearinghouse clearhouse = Clearinghouses.getClearinghouse(queueItem.ClearinghouseNum);
        //Warning: this path is not handled properly if trailing slash is missing:
        String saveFolder = clearhouse.ExportPath;
        if (!Directory.Exists(saveFolder))
        {
            throw new ApplicationException(saveFolder + " not found.");
        }
         
        Etrans etrans;
        Claim claim;
        Clinic clinic;
        Provider billProv;
        Provider treatProv;
        InsPlan insPlan;
        InsSub insSub;
        Carrier carrier;
        InsPlan insPlan2 = null;
        InsSub insSub2 = null;
        Carrier carrier2 = null;
        List<PatPlan> patPlansForPatient = new List<PatPlan>();
        Patient patient;
        Patient subscriber;
        List<ClaimProc> claimProcList = new List<ClaimProc>();
        //all claimProcs for a patient.
        List<ClaimProc> claimProcsClaim = new List<ClaimProc>();
        List<Procedure> procListAll = new List<Procedure>();
        List<Procedure> extracted = new List<Procedure>();
        List<Procedure> procListLabForOne = new List<Procedure>();
        //Lab fees for one procedure
        Patient subscriber2 = null;
        Procedure proc;
        ProcedureCode procCode;
        StringBuilder strb = new StringBuilder();
        String primaryEOBResponse = "";
        String primaryClaimRequestMessage = "";
        claim = Claims.getClaim(queueItem.ClaimNum);
        claimProcList = ClaimProcs.refresh(claim.PatNum);
        claimProcsClaim = ClaimProcs.GetForSendClaim(claimProcList, claim.ClaimNum);
        long planNum = claim.PlanNum;
        long insSubNum = claim.InsSubNum;
        Relat patRelat = claim.PatRelat;
        long planNum2 = claim.PlanNum2;
        long insSubNum2 = claim.InsSubNum2;
        Relat patRelat2 = claim.PatRelat2;
        if (StringSupport.equals(claim.ClaimType, "PreAuth"))
        {
            etrans = Etranss.setClaimSentOrPrinted(queueItem.ClaimNum,queueItem.PatNum,clearhouse.ClearinghouseNum,EtransType.Predeterm_CA,0);
        }
        else if (StringSupport.equals(claim.ClaimType, "S"))
        {
            //Secondary
            //We first need to verify that the claimprocs on the secondary/cob claim are the same as the claimprocs on the primary claim.
            etrans = Etranss.setClaimSentOrPrinted(queueItem.ClaimNum,queueItem.PatNum,clearhouse.ClearinghouseNum,EtransType.ClaimCOB_CA,0);
            long claimNumPrimary = 0;
            for (int i = 0;i < claimProcsClaim.Count;i++)
            {
                List<ClaimProc> claimProcsForProc = ClaimProcs.GetForProc(claimProcList, claimProcsClaim[i].ProcNum);
                boolean matchingPrimaryProc = false;
                for (int j = 0;j < claimProcsForProc.Count;j++)
                {
                    if (claimProcsForProc[j].ClaimNum != 0 && claimProcsForProc[j].ClaimNum != claim.ClaimNum && (claimNumPrimary == 0 || claimNumPrimary == claimProcsForProc[j].ClaimNum))
                    {
                        claimNumPrimary = claimProcsForProc[j].ClaimNum;
                        matchingPrimaryProc = true;
                        break;
                    }
                     
                }
                if (!matchingPrimaryProc)
                {
                    throw new ApplicationException(Lan.g("Canadian","The procedures attached to this COB claim must be the same as the procedures attached to the primary claim."));
                }
                 
            }
            if (ClaimProcs.GetForSendClaim(claimProcList, claimNumPrimary).Count != claimProcsClaim.Count)
            {
                throw new ApplicationException(Lan.g("Canadian","The procedures attached to this COB claim must be the same as the procedures attached to the primary claim."));
            }
             
            //Now ensure that the primary claim recieved an EOB response, or else we cannot send a COB.
            List<Etrans> etransPrimary = Etranss.getHistoryOneClaim(claimNumPrimary);
            for (int i = 0;i < etransPrimary.Count;i++)
            {
                primaryClaimRequestMessage = EtransMessageTexts.GetMessageText(etransPrimary[i].EtransMessageTextNum);
                Etrans etransPrimaryAck = Etranss.GetEtrans(etransPrimary[i].AckEtransNum);
                if (StringSupport.equals(etransPrimaryAck.AckCode.ToUpper(), "R"))
                {
                    continue;
                }
                 
                if (etransPrimaryAck != null)
                {
                    primaryEOBResponse = EtransMessageTexts.getMessageText(etransPrimaryAck.EtransMessageTextNum);
                }
                 
                break;
            }
            if (primaryEOBResponse.Length < 22)
            {
                throw new ApplicationException(Lan.g("Canadian","A COB can only be sent when the primary claim has received a version 04 EOB response."));
            }
            else
            {
                String messageVersion = primaryEOBResponse.Substring(18, 2);
                //Field A03 always exists on all messages and is always in the same location.
                String messageType = primaryEOBResponse.Substring(20, 2);
                //Field A04 always exists on all messages and is always in the same location.
                if (!StringSupport.equals(messageVersion, "04") || !StringSupport.equals(messageType, "21"))
                {
                    throw new ApplicationException(Lan.g("Canadian","A COB can only be sent when the primary claim has received a version 04 EOB response."));
                }
                 
            } 
            //message type 21 is EOB
            Claim claimPrimary = Claims.getClaim(claimNumPrimary);
            planNum = claimPrimary.PlanNum;
            insSubNum = claimPrimary.InsSubNum;
            patRelat = claimPrimary.PatRelat;
            planNum2 = claimPrimary.PlanNum2;
            insSubNum2 = claimPrimary.InsSubNum2;
            patRelat2 = claimPrimary.PatRelat2;
        }
        else
        {
            //primary claim
            etrans = Etranss.setClaimSentOrPrinted(queueItem.ClaimNum,queueItem.PatNum,clearhouse.ClearinghouseNum,EtransType.Claim_CA,0);
        }  
        claim = Claims.getClaim(claim.ClaimNum);
        //Refresh the claim since the status might have changed above.
        clinic = Clinics.getClinic(claim.ClinicNum);
        billProv = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvBill)];
        treatProv = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvTreat)];
        insPlan = InsPlans.GetPlan(planNum, new List<InsPlan>());
        insSub = InsSubs.GetSub(insSubNum, new List<InsSub>());
        if (planNum2 > 0)
        {
            insPlan2 = InsPlans.GetPlan(planNum2, new List<InsPlan>());
            insSub2 = InsSubs.GetSub(insSubNum2, new List<InsSub>());
            carrier2 = Carriers.getCarrier(insPlan2.CarrierNum);
            subscriber2 = Patients.getPat(insSub2.Subscriber);
        }
         
        if (StringSupport.equals(claim.ClaimType, "S"))
        {
            carrier = Carriers.getCarrier(insPlan2.CarrierNum);
        }
        else
        {
            carrier = Carriers.getCarrier(insPlan.CarrierNum);
        } 
        CanadianNetwork network = CanadianNetworks.getNetwork(carrier.CanadianNetworkNum);
        patPlansForPatient = PatPlans.refresh(claim.PatNum);
        patient = Patients.getPat(claim.PatNum);
        subscriber = Patients.getPat(insSub.Subscriber);
        procListAll = Procedures.refresh(claim.PatNum);
        extracted = Procedures.GetCanadianExtractedTeeth(procListAll);
        strb = new StringBuilder();
        //A01 transaction prefix 12 AN
        strb.Append(tidyAN(network.CanadianTransactionPrefix,12));
        //A02 office sequence number 6 N
        strb.Append(tidyN(etrans.OfficeSequenceNumber,6));
        //A03 format version number 2 N
        if (StringSupport.equals(carrier.CDAnetVersion, ""))
        {
            strb.Append("04");
        }
        else
        {
            strb.Append(carrier.CDAnetVersion);
        } 
        //A04 transaction code 2 N
        if (StringSupport.equals(claim.ClaimType, "PreAuth"))
        {
            strb.Append("03");
        }
        else
        {
            //Predetermination
            if (StringSupport.equals(claim.ClaimType, "S"))
            {
                strb.Append("07");
            }
            else
            {
                //cob
                strb.Append("01");
            } 
        } 
        //claim
        //A05 carrier id number 6 N
        strb.Append(carrier.ElectID);
        //already validated as 6 digit number.
        //A06 software system id 3 AN
        strb.Append(softwareSystemId());
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //A10 encryption method 1 N
            strb.Append(carrier.CanadianEncryptionMethod);
        }
         
        //validated in UI
        //A07 message length. 5 N in version 04, 4 N in version 02
        //We simply create a place holder here. We come back at the end of message construction and record the actual final message length.
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            strb.Append("0000");
        }
        else
        {
            //version 04
            strb.Append("00000");
        } 
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //A08 email flag 1 N
            if (StringSupport.equals(claim.CanadianMaterialsForwarded, ""))
            {
                strb.Append("0");
            }
            else //no additional information
            if (claim.CanadianMaterialsForwarded.Contains("E"))
            {
                strb.Append("1");
            }
            else
            {
                //E-Mail to follow.
                strb.Append("2");
            }  
        }
        else
        {
            //Letter to follow
            //version 04
            //A08 materials forwarded 1 AN
            strb.Append(getMaterialsForwarded(claim.CanadianMaterialsForwarded));
        } 
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //A09 carrier transaction counter 5 N
            strb.Append(tidyN(etrans.CarrierTransCounter,5));
        }
         
        //B01 CDA provider number 9 AN
        strb.Append(tidyAN(treatProv.NationalProvID,9));
        //already validated
        //B02 (treating) provider office number 4 AN
        strb.Append(tidyAN(treatProv.CanadianOfficeNum,4));
        //already validated
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //B03 billing provider number 9 AN
            //might need to account for possible 5 digit prov id assigned by carrier
            strb.Append(tidyAN(billProv.NationalProvID,9));
            //already validated
            //B04 billing provider office number 4 AN
            strb.Append(tidyAN(billProv.CanadianOfficeNum,4));
            //already validated
            //B05 referring provider 10 AN
            strb.Append(tidyAN(claim.CanadianReferralProviderNum,10));
            //B06 referral reason 2 N
            strb.Append(TidyN(claim.CanadianReferralReason, 2));
        }
         
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C01 primary policy/plan number 8 AN
            //only validated to ensure that it's not blank and is less than 8. Also that no spaces.
            strb.Append(tidyAN(insPlan.GroupNum,8));
        }
        else
        {
            //version 04
            //C01 primary policy/plan number 12 AN
            //only validated to ensure that it's not blank and is less than 12. Also that no spaces.
            strb.Append(tidyAN(insPlan.GroupNum,12));
        } 
        //C11 primary division/section number 10 AN
        strb.Append(tidyAN(insPlan.DivisionNo,10));
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C02 subscriber id number 11 AN
            strb.Append(TidyAN(insSub.SubscriberID.Replace("-", ""), 11));
        }
        else
        {
            //validated
            //version 04
            //C02 subscriber id number 12 AN
            strb.Append(TidyAN(insSub.SubscriberID.Replace("-", ""), 12));
        } 
        //validated
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //C17 primary dependant code 2 N
            String patID = "";
            for (int p = 0;p < patPlansForPatient.Count;p++)
            {
                if (patPlansForPatient[p].InsSubNum == insSubNum)
                {
                    patID = patPlansForPatient[p].PatID;
                }
                 
            }
            strb.Append(tidyN(patID,2));
        }
         
        //C03 relationship code 1 N
        //User interface does not only show Canadian options, but all options are handled.
        strb.Append(getRelationshipCode(patRelat));
        //C04 patient's sex 1 A
        //validated to not include "unknown"
        if (patient.Gender == PatientGender.Male)
        {
            strb.Append("M");
        }
        else
        {
            strb.Append("F");
        } 
        //C05 patient birthday 8 N
        strb.Append(patient.Birthdate.ToString("yyyyMMdd"));
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C06 patient last name 25 A
            strb.Append(tidyA(patient.LName,25));
        }
        else
        {
            //validated
            //version 04
            //C06 patient last name 25 AE
            strb.Append(tidyAE(patient.LName,25,true));
        } 
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C07 patient first name 15 A
            strb.Append(tidyA(patient.FName,15));
        }
        else
        {
            //validated
            //version 04
            //C07 patient first name 15 AE
            strb.Append(tidyAE(patient.FName,15,true));
        } 
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C08 patient middle initial 1 A
            strb.Append(tidyA(patient.MiddleI,1));
        }
        else
        {
            //version 04
            //C08 patient middle initial 1 AE
            strb.Append(tidyAE(patient.MiddleI,1));
        } 
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C09 eligibility exception code 1 N
            String exceptionCode = TidyN(patient.CanadianEligibilityCode, 1);
            //Validated.
            if (StringSupport.equals(exceptionCode, "4"))
            {
                exceptionCode = "0";
            }
             
            //Code 4 in version 04 means "code not applicable", but in version 02, value 0 means "code not applicable".
            strb.Append(exceptionCode);
        }
        else
        {
            //validated
            //version 04
            //C09 eligibility exception code 1 N
            strb.Append(tidyN((patient.CanadianEligibilityCode == 0) ? 4 : patient.CanadianEligibilityCode,1));
        } 
        //Validated. Use "code not applicable" when no value has been specified by the user.
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //C10 name of school 25 A
            //validated if patient 18yrs or older and full-time student (or disabled student)
            strb.Append(tidyA(patient.SchoolName,25));
        }
        else
        {
            //version 04
            //C10 name of school 25 AEN
            //validated if patient 18yrs or older and full-time student (or disabled student)
            strb.Append(tidyAEN(patient.SchoolName,25));
        } 
        boolean C19PlanRecordPresent = (StringSupport.equals(insPlan.CanadianPlanFlag, "A") || StringSupport.equals(insPlan.CanadianPlanFlag, "N") || StringSupport.equals(insPlan.CanadianPlanFlag, "V"));
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //C12 plan flag 1 A
            strb.Append(Canadian.getPlanFlag(insPlan.CanadianPlanFlag));
            //C18 plan record count 1 N
            if (C19PlanRecordPresent)
            {
                strb.Append("1");
            }
            else
            {
                strb.Append("0");
            } 
        }
         
        CCDFieldInputter primaryClaimData = null;
        if (StringSupport.equals(claim.ClaimType, "S"))
        {
            primaryClaimData = new CCDFieldInputter(primaryClaimRequestMessage);
        }
         
        //D01 subscriber birthday 8 N
        strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D01").valuestr : subscriber.Birthdate.ToString("yyyyMMdd"));
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D02 subscriber last name 25 A
            strb.Append(tidyA(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D02").valuestr : subscriber.LName,25));
        }
        else
        {
            //validated
            //version 04
            //D02 subscriber last name 25 AE
            strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D02").valuestr : subscriber.LName,25,true));
        } 
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D03 subscriber first name 15 A
            strb.Append(tidyA(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D03").valuestr : subscriber.FName,15));
        }
        else
        {
            //validated
            //version 04
            //D03 subscriber first name 15 AE
            strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D03").valuestr : subscriber.FName,15,true));
        } 
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D04 subscriber middle initial 1 A
            strb.Append(tidyA(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D04").valuestr : subscriber.MiddleI,1));
        }
        else
        {
            //version 04
            //D04 subscriber middle initial 1 AE
            strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D04").valuestr : subscriber.MiddleI,1));
        } 
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D05 subscriber address line one 30 AN
            strb.Append(tidyAN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D05").valuestr : subscriber.Address,30,true));
        }
        else
        {
            //validated
            //version 04
            //D05 subscriber address line one 30 AEN
            strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D05").valuestr : subscriber.Address,30,true));
        } 
        //validated
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D06 subscriber address line two 30 AN
            strb.Append(tidyAN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D06").valuestr : subscriber.Address2,30,true));
        }
        else
        {
            //version 04
            //D06 subscriber address line two 30 AEN
            strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D06").valuestr : subscriber.Address2,30,true));
        } 
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D07 subscriber city 20 A
            strb.Append(tidyA(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D07").valuestr : subscriber.City,20));
        }
        else
        {
            //validated
            //version 04
            //D07 subscriber city 20 AEN
            strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D07").valuestr : subscriber.City,20,true));
        } 
        //validated
        //D08 subscriber province/state 2 A
        strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D08").valuestr : subscriber.State);
        //very throroughly validated previously
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //D09 subscriber postal/zip code 6 AN
            strb.Append(tidyAN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D09").valuestr : subscriber.Zip.Replace("-", "").Replace(" ", ""),6));
        }
        else
        {
            //validated.
            //version 04
            //D09 subscriber postal/zip code 9 AN
            strb.Append(tidyAN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D09").valuestr : subscriber.Zip.Replace("-", "").Replace(" ", ""),9));
        } 
        //validated.
        //D10 language of insured 1 A
        strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("D10").valuestr : (StringSupport.equals(subscriber.Language, "fr") ? "F" : "E"));
        boolean orthoRecordFlag = false;
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //D11 card sequence/version number 2 N
            //Not validated against type of carrier yet. Might need to check if Dentaide.
            strb.Append(TidyN(insPlan.DentaideCardSequence, 2));
            //E18 secondary coverage flag 1 A
            if (planNum2 > 0)
            {
                strb.Append("Y");
            }
            else
            {
                strb.Append("N");
            } 
            //E20 secondary record count 1 N
            if (planNum2 == 0)
            {
                strb.Append("0");
            }
            else
            {
                strb.Append("1");
            } 
            //F06 number of procedures performed 1 N. Must be between 1 and 7.  UI prevents attaching more than 7.
            strb.Append(TidyN(claimProcsClaim.Count, 1));
            //number validated
            //F22 extracted teeth count 2 N
            strb.Append(TidyN(extracted.Count, 2));
            //validated against matching prosthesis
            if (StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                orthoRecordFlag = (claim.CanadaEstTreatStartDate.Year > 1880 || claim.CanadaInitialPayment != 0 || claim.CanadaPaymentMode != 0 || claim.CanadaTreatDuration != 0 || claim.CanadaNumAnticipatedPayments != 0 || claim.CanadaAnticipatedPayAmount != 0);
                //F25 Orthodontic Record Flag 1 N
                if (orthoRecordFlag)
                {
                    strb.Append("1");
                }
                else
                {
                    strb.Append("0");
                } 
            }
             
            if (StringSupport.equals(claim.ClaimType, "S"))
            {
                //cob
                //G39 Embedded Transaction Length N 4
                strb.Append(Canadian.TidyN(primaryEOBResponse.Length, 4));
            }
             
        }
         
        //Secondary carrier fields (E19 to E07) ONLY included if E20=1----------------------------------------------------
        if (planNum2 != 0)
        {
            if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //version 04
                //E19 secondary carrier transaction number 6 N
                strb.Append(tidyN(etrans.CarrierTransCounter2,6));
            }
             
            //E01 sec carrier id number 6 N
            strb.Append(carrier2.ElectID);
            //already validated as 6 digit number.
            if (StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //E02 sec carrier policy/plan num 8 AN
                //only validated to ensure that it's not blank and is less than 8. Also that no spaces.
                //We might later allow 999999 if sec carrier is unlisted or unknown.
                strb.Append(tidyAN(insPlan2.GroupNum,8));
            }
            else
            {
                //version 04
                //E02 sec carrier policy/plan num 12 AN
                //only validated to ensure that it's not blank and is less than 12. Also that no spaces.
                //We might later allow 999999 if sec carrier is unlisted or unknown.
                strb.Append(tidyAN(insPlan2.GroupNum,12));
            } 
            //E05 sec division/section num 10 AN
            strb.Append(tidyAN(insPlan2.DivisionNo,10));
            if (StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //E03 sec plan subscriber id 11 AN
                strb.Append(TidyAN(insSub2.SubscriberID.Replace("-", ""), 11));
            }
            else
            {
                //validated
                //version 04
                //E03 sec plan subscriber id 12 AN
                strb.Append(TidyAN(insSub2.SubscriberID.Replace("-", ""), 12));
            } 
            //validated
            if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //version 04
                //E17 sec dependent code 2 N
                String patID = "";
                for (int p = 0;p < patPlansForPatient.Count;p++)
                {
                    if (patPlansForPatient[p].InsSubNum == insSubNum2)
                    {
                        patID = patPlansForPatient[p].PatID;
                    }
                     
                }
                strb.Append(tidyN(patID,2));
                //E06 sec relationship code 1 N
                //User interface does not only show Canadian options, but all options are handled.
                strb.Append(getRelationshipCode(patRelat2));
            }
             
            //E04 sec subscriber birthday 8 N
            strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E04").valuestr : subscriber2.Birthdate.ToString("yyyyMMdd"));
            //validated
            if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //version 04
                //E08 sec subscriber last name 25 AE
                strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E08").valuestr : subscriber2.LName,25,true));
                //validated
                //E09 sec subscriber first name 15 AE
                strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E09").valuestr : subscriber2.FName,15,true));
                //validated
                //E10 sec subscriber middle initial 1 AE
                strb.Append(tidyAE(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E10").valuestr : subscriber2.MiddleI,1));
                //E11 sec subscriber address one 30 AEN
                strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E11").valuestr : subscriber2.Address,30,true));
                //validated
                //E12 sec subscriber address two 30 AEN
                strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E12").valuestr : subscriber2.Address2,30,true));
                //E13 sec subscriber city 20 AEN
                strb.Append(tidyAEN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E13").valuestr : subscriber2.City,20,true));
                //validated
                //E14 sec subscriber province/state 2 A
                strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E14").valuestr : subscriber2.State);
                //very throroughly validated previously
                //E15 sec subscriber postal/zip code 9 AN
                strb.Append(tidyAN(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E15").valuestr : subscriber2.Zip.Replace("-", "").Replace(" ", ""),9));
                //validated
                //E16 sec language 1 A
                strb.Append(StringSupport.equals(claim.ClaimType, "S") ? primaryClaimData.getFieldById("E16").valuestr : (StringSupport.equals(subscriber2.Language, "fr") ? "F" : "E"));
                //E07 sec card sequence/version num 2 N
                //todo Not validated yet.
                strb.Append(TidyN(StringSupport.equals(claim.ClaimType, "S") ? PIn.Int(primaryClaimData.getFieldById("E07").valuestr) : insPlan2.DentaideCardSequence, 2));
            }
             
        }
        else
        {
            //End of secondary subscriber fields---------------------------------------------------------------------------
            //There is no secondary plan.
            if (StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //Secondary subscriber fields are always available in version 2. Since there is no plan, put blank data as a filler.
                //E01 N 6
                strb.Append("000000");
                //E02 AN 8
                strb.Append("        ");
                //E05 AN 10
                strb.Append("          ");
                //E03 AN 11
                strb.Append("           ");
                //E04 N 8
                strb.Append("00000000");
            }
             
        } 
        if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
        {
            //F01 payee code 1 N
            if ((!StringSupport.equals(claim.ClaimType, "S") && insSub.AssignBen) || (StringSupport.equals(claim.ClaimType, "S") && insSub2.AssignBen))
            {
                if (StringSupport.equals(carrier.CDAnetVersion, "02"))
                {
                    strb.Append("0");
                }
                else
                {
                    //pay dentist
                    //version 04
                    strb.Append("4");
                } 
            }
            else
            {
                //pay dentist
                strb.Append("1");
            } 
        }
         
        //pay subscriber
        //F02 accident date 8 N
        if (claim.AccidentDate.Year > 1900)
        {
            //if accident related
            strb.Append(claim.AccidentDate.ToString("yyyyMMdd"));
        }
        else
        {
            //validated
            strb.Append(tidyN(0,8));
        } 
        if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
        {
            //F03 predetermination number 14 AN
            strb.Append(tidyAN(claim.PreAuthString,14));
        }
         
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //F15 Is this an Initial Replacement? A 1
            String initialPlacement = "Y";
            DateTime initialPlacementDate = DateTime.MinValue;
            if (StringSupport.equals(claim.CanadianIsInitialUpper, "Y"))
            {
                initialPlacement = "Y";
                initialPlacementDate = claim.CanadianDateInitialUpper;
            }
            else if (StringSupport.equals(claim.CanadianIsInitialLower, "Y"))
            {
                initialPlacement = "Y";
                initialPlacementDate = claim.CanadianDateInitialLower;
            }
            else if (StringSupport.equals(claim.CanadianIsInitialUpper, "N"))
            {
                initialPlacement = "N";
                initialPlacementDate = claim.CanadianDateInitialUpper;
            }
            else if (StringSupport.equals(claim.CanadianIsInitialLower, "N"))
            {
                initialPlacement = "N";
                initialPlacementDate = claim.CanadianDateInitialLower;
            }
                
            strb.Append(initialPlacement);
            //F04 date of initial placement 8 N
            if (initialPlacementDate.Year > 1900)
            {
                strb.Append(initialPlacementDate.ToString("yyyyMMdd"));
            }
            else
            {
                strb.Append("00000000");
            } 
            //F05 tx req'd for ortho purposes 1 A
            if (claim.IsOrtho)
            {
                strb.Append("Y");
            }
            else
            {
                strb.Append("N");
            } 
            //F06 number of procedures performed 1 N. Must be between 1 and 7.  UI prevents attaching more than 7.
            strb.Append(TidyN(claimProcsClaim.Count, 1));
        }
        else
        {
            //number validated
            //version 04
            //F15 initial placement upper 1 A  Y or N or X
            strb.Append(Canadian.tidyA(claim.CanadianIsInitialUpper,1));
            //validated
            //F04 date of initial placement upper 8 N
            if (claim.CanadianDateInitialUpper.Year > 1900)
            {
                strb.Append(claim.CanadianDateInitialUpper.ToString("yyyyMMdd"));
            }
            else
            {
                strb.Append("00000000");
            } 
            //F18 initial placement lower 1 A
            strb.Append(Canadian.tidyA(claim.CanadianIsInitialLower,1));
            //validated
            //F19 date of initial placement lower 8 N
            if (claim.CanadianDateInitialLower.Year > 1900)
            {
                strb.Append(claim.CanadianDateInitialLower.ToString("yyyyMMdd"));
            }
            else
            {
                strb.Append("00000000");
            } 
            //F05 tx req'd for ortho purposes 1 A
            if (claim.IsOrtho)
            {
                strb.Append("Y");
            }
            else
            {
                strb.Append("N");
            } 
            //F20 max prosth material 1 N
            if (claim.CanadianMaxProsthMaterial == 7)
            {
                //our fake crown code
                strb.Append("0");
            }
            else
            {
                strb.Append(claim.CanadianMaxProsthMaterial.ToString());
            } 
            //validated
            //F21 mand prosth material 1 N
            if (claim.CanadianMandProsthMaterial == 7)
            {
                //our fake crown code
                strb.Append("0");
            }
            else
            {
                strb.Append(claim.CanadianMandProsthMaterial.ToString());
            } 
        } 
        //validated
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            for (int t = 0;t < extracted.Count;t++)
            {
                //version 04
                //If F22 is non-zero. Repeat for the number of times specified by F22.----------------------------------------------
                //F23 extracted tooth num 2 N
                //todo: check validation
                strb.Append(TidyN(Tooth.ToInternat(extracted[t].ToothNum), 2));
                //validated
                //F24 extraction date 8 N
                //todo: check validation
                strb.Append(extracted[t].ProcDate.ToString("yyyyMMdd"));
            }
        }
         
        //validated
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            if (StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                //G46 Current Predetermination Page Number N 1
                strb.Append("1");
                //Always 1 page, because UI prevents attaching more than 7 procedures per claim in Canadian mode.
                //G47 Last Predetermination Page Number N 1
                strb.Append("1");
                //Always 1 page, because UI prevents attaching more than 7 procedures per claim in Canadian mode.
                if (orthoRecordFlag)
                {
                    //F25 is set
                    //F37 Estimated Treatment Starting Date N 8
                    strb.Append(Canadian.TidyN(claim.CanadaEstTreatStartDate.ToString("yyyyMMdd"), 8));
                    double firstExamFee = 0;
                    double diagnosticPhaseFee = 0;
                    //F26 First Examination Fee D 6
                    strb.Append(Canadian.tidyD(firstExamFee,6));
                    //optional
                    //F27 Diagnostic Phase Fee D 6
                    strb.Append(Canadian.tidyD(diagnosticPhaseFee,6));
                    //optional
                    //F28 Initial Payment D 6
                    strb.Append(Canadian.tidyD(claim.CanadaInitialPayment,6));
                    //F29 Payment Mode N 1
                    strb.Append(Canadian.TidyN(claim.CanadaPaymentMode, 1));
                    //Validated in UI.
                    //F30 Treatment Duration N 2
                    strb.Append(Canadian.TidyN(claim.CanadaTreatDuration, 2));
                    //F31 Number of Anticipated Payments N 2
                    strb.Append(Canadian.TidyN(claim.CanadaNumAnticipatedPayments, 2));
                    //F32 Anticipated Payment Amount D 6
                    strb.Append(Canadian.tidyD(claim.CanadaAnticipatedPayAmount,6));
                }
                 
            }
             
        }
         
        for (int p = 0;p < claimProcsClaim.Count;p++)
        {
            //Procedures: Repeat for number of times specified by F06.----------------------------------------------------------
            proc = Procedures.GetProcFromList(procListAll, claimProcsClaim[p].ProcNum);
            procCode = ProcedureCodes.getProcCode(proc.CodeNum);
            procListLabForOne = Procedures.GetCanadianLabFees(proc.ProcNum, procListAll);
            //F07 proc line number 1 N
            strb.Append((p + 1).ToString());
            if (StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //F08 procedure code 5 N
                strb.Append(TidyN(claimProcsClaim[p].CodeSent, 5).Trim().PadLeft(5, '0'));
            }
            else
            {
                //version 04
                //F08 procedure code 5 AN
                strb.Append(TidyAN(claimProcsClaim[p].CodeSent, 5).Trim().PadLeft(5, '0'));
            } 
            if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                //F09 date of service 8 N
                strb.Append(claimProcsClaim[p].ProcDate.ToString("yyyyMMdd"));
            }
             
            //validated
            //F10 international tooth, sextant, quad, or arch 2 N
            strb.Append(getToothQuadOrArch(proc,procCode));
            //F11 tooth surface 5 A
            //the SurfTidy function is very thorough, so it's OK to use TidyAN
            if (procCode.TreatArea == TreatmentArea.Surf)
            {
                strb.Append(tidyAN(Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum),5));
            }
            else
            {
                strb.Append("     ");
            } 
            //F12 dentist's fee claimed 6 D
            strb.Append(TidyD(claimProcsClaim[p].FeeBilled, 6));
            if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //version 04
                //F34 lab procedure code #1 5 AN
                if (procListLabForOne.Count > 0)
                {
                    strb.Append(TidyAN(ProcedureCodes.GetProcCode(procListLabForOne[0].CodeNum).ProcCode, 5).Trim().PadLeft(5, '0'));
                }
                else
                {
                    strb.Append("     ");
                } 
            }
             
            //F13 lab procedure fee #1 6 D
            if (procListLabForOne.Count > 0)
            {
                strb.Append(TidyD(procListLabForOne[0].ProcFee, 6));
            }
            else
            {
                strb.Append("000000");
            } 
            if (StringSupport.equals(carrier.CDAnetVersion, "02"))
            {
                //F14 Unit of Time D 4
                //This is a somewhat deprecated field becacuse it no longer exists in version 04. Some carriers reject claims
                //if there is a time specified for a procedure that does not require a time. It is safest for now to just set
                //this value to zero always.
                double procHours = 0;
                //procHours=(PrefC.GetInt(PrefName.AppointmentTimeIncrement)*procCode.ProcTime.Length)/60.0;
                strb.Append(tidyD(procHours,4));
            }
            else
            {
                //version 04
                //F35 lab procedure code #2 5 AN
                if (procListLabForOne.Count > 1)
                {
                    strb.Append(TidyAN(ProcedureCodes.GetProcCode(procListLabForOne[1].CodeNum).ProcCode, 5).Trim().PadLeft(5, '0'));
                }
                else
                {
                    strb.Append("     ");
                } 
                //F36 lab procedure fee #2 6 D
                if (procListLabForOne.Count > 1)
                {
                    strb.Append(TidyD(procListLabForOne[1].ProcFee, 6));
                }
                else
                {
                    strb.Append("000000");
                } 
                //F16 procedure type codes 5 A
                strb.Append(tidyA((proc.CanadianTypeCodes == null || StringSupport.equals(proc.CanadianTypeCodes, "")) ? "X" : proc.CanadianTypeCodes,5));
                //F17 remarks code 2 N
                //optional.  PMP field.  See C12. Zeros when not used.
                strb.Append("00");
            } 
        }
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            //version 04
            //C19 plan record 30 AN
            if (C19PlanRecordPresent)
            {
                if (StringSupport.equals(insPlan.CanadianPlanFlag, "A"))
                {
                    //insPlan.CanadianDiagnosticCode and insPlan.CanadianInstitutionCode are validated in the UI.
                    strb.Append(Canadian.tidyAN(Canadian.tidyAN(insPlan.CanadianDiagnosticCode,6,true) + Canadian.tidyAN(insPlan.CanadianInstitutionCode,6,true),30,true));
                }
                else
                {
                    //N or V. These two plan flags are not yet in use. Presumably, for future use.
                    strb.Append(Canadian.tidyAN("",30));
                } 
            }
             
        }
         
        //We are required to append the primary EOB. This is not a data dictionary field.
        if (StringSupport.equals(claim.ClaimType, "S"))
        {
            strb.Append(convertEOBVersion(primaryEOBResponse,carrier.CDAnetVersion));
        }
         
        //Now we go back and fill in the actual message length now that we know the number for sure.
        if (StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            strb.Replace("0000", Canadian.TidyN(strb.Length, 4), 31, 4);
        }
        else
        {
            //version 04
            strb.Replace("00000", Canadian.TidyN(strb.Length, 5), 32, 5);
        } 
        //end of creating the message
        //this is where we attempt the actual sending:
        String result = "";
        boolean resultIsError = false;
        try
        {
            result = PassToIca(strb.ToString(), clearhouse);
        }
        catch (ApplicationException ex)
        {
            result = ex.Message;
            resultIsError = true;
        }

        //Attach an ack to the etrans
        Etrans etransAck = new Etrans();
        etransAck.PatNum = etrans.PatNum;
        etransAck.PlanNum = etrans.PlanNum;
        etransAck.InsSubNum = etrans.InsSubNum;
        etransAck.CarrierNum = etrans.CarrierNum;
        etransAck.ClaimNum = etrans.ClaimNum;
        etransAck.DateTimeTrans = DateTime.Now;
        CCDFieldInputter fieldInputter = null;
        if (resultIsError)
        {
            etransAck.Etype = EtransType.AckError;
            etrans.Note = "failed";
        }
        else
        {
            fieldInputter = new CCDFieldInputter(result);
            CCDField fieldG05 = fieldInputter.getFieldById("G05");
            if (fieldG05 != null)
            {
                etransAck.AckCode = fieldG05.valuestr;
                if (StringSupport.equals(etransAck.AckCode, "M"))
                {
                    //Manually print the claim form.
                    printManualClaimForm(claim);
                }
                 
            }
             
            etransAck.Etype = fieldInputter.getEtransType();
        } 
        Etranss.insert(etransAck);
        Etranss.setMessage(etransAck.EtransNum,result);
        etrans.AckEtransNum = etransAck.EtransNum;
        Etranss.update(etrans);
        Etranss.SetMessage(etrans.EtransNum, strb.ToString());
        if (resultIsError)
        {
            throw new ApplicationException(result);
        }
         
        if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
        {
            Claims.setCanadianClaimSent(queueItem.ClaimNum);
            //when called from ClaimEdit, that window will close immediately, so we're directly changing the db.
            CCDField fieldTransRefNum = fieldInputter.getFieldById("G01");
            if (fieldTransRefNum != null)
            {
                if (!StringSupport.equals(etransAck.AckCode, "R"))
                {
                    claim.CanadaTransRefNum = fieldTransRefNum.valuestr;
                    Claims.update(claim);
                }
                 
            }
             
        }
         
        if (doPrint)
        {
            new FormCCDPrint(etrans,result,true);
        }
         
        //Physically print the form.
        if (!StringSupport.equals(claim.ClaimType, "PreAuth") && !StringSupport.equals(claim.ClaimType, "S") && etransAck.Etype == EtransType.ClaimEOB_CA && planNum2 > 0)
        {
            //if an eob was returned and patient has secondary insurance.
            //if an EOB is returned, there are two possibilities.
            //1. The EOB contains an embedded EOB because the same carrier is both pri and sec.  Both got printed above.
            //2. The EOB does not contain an embedded EOB, indicating that a COB claim needs to be created and sent.
            //That is done here, automatically.
            //UI already prevents the initial automatic creation of the secondary claim for Canada.
            String embeddedLength = fieldInputter.getValue("G39");
            if (StringSupport.equals(embeddedLength, "") || StringSupport.equals(embeddedLength, "0000"))
            {
                //no embedded message
                Claim claim2 = new Claim();
                claim2.PatNum = claim.PatNum;
                claim2.DateService = claim.DateService;
                claim2.DateSent = DateTime.Today;
                claim2.ClaimStatus = "W";
                claim2.PlanNum = planNum2;
                claim2.InsSubNum = insSubNum2;
                claim2.PatRelat = patRelat2;
                claim2.PlanNum2 = planNum;
                claim2.InsSubNum2 = insSubNum;
                claim2.PatRelat2 = patRelat;
                claim2.ClaimType = "S";
                claim2.ProvTreat = claim.ProvTreat;
                claim2.IsProsthesis = "N";
                claim2.ProvBill = claim.ProvBill;
                claim2.EmployRelated = YN.No;
                claim2.AccidentDate = claim.AccidentDate;
                claim2.IsOrtho = claim.IsOrtho;
                claim2.CanadianDateInitialLower = claim.CanadianDateInitialLower;
                claim2.CanadianDateInitialUpper = claim.CanadianDateInitialUpper;
                claim2.CanadianIsInitialLower = claim.CanadianIsInitialLower;
                claim2.CanadianIsInitialUpper = claim.CanadianIsInitialUpper;
                claim2.CanadianMandProsthMaterial = claim.CanadianMandProsthMaterial;
                claim2.CanadianMaterialsForwarded = claim.CanadianMaterialsForwarded;
                claim2.CanadianMaxProsthMaterial = claim.CanadianMaxProsthMaterial;
                claim2.CanadianReferralProviderNum = claim.CanadianReferralProviderNum;
                claim2.CanadianReferralReason = claim.CanadianReferralReason;
                Claims.insert(claim2);
                //to retreive a key for new Claim.ClaimNum
                ClaimProc[] claimProcsClaim2 = new ClaimProc[claimProcsClaim.Count];
                long procNum = new long();
                for (int i = 0;i < claimProcsClaim.Count;i++)
                {
                    //loop through the procs from claim 1
                    //and try to find an estimate that can be used
                    procNum = claimProcsClaim[i].ProcNum;
                    claimProcsClaim2[i] = Procedures.GetClaimProcEstimate(procNum, claimProcList, insPlan2, claim2.InsSubNum2);
                }
                for (int i = 0;i < claimProcsClaim2.Length;i++)
                {
                    //loop through each claimProc
                    //and create any missing estimates just in case
                    if (claimProcsClaim2[i] == null)
                    {
                        claimProcsClaim2[i] = new ClaimProc();
                        proc = Procedures.GetProcFromList(procListAll, claimProcsClaim[i].ProcNum);
                        ClaimProcs.CreateEst(claimProcsClaim2[i], proc, insPlan2, insSub2);
                    }
                     
                }
                for (int i = 0;i < claimProcsClaim2.Length;i++)
                {
                    proc = Procedures.GetProcFromList(procListAll, claimProcsClaim2[i].ProcNum);
                    //1:1
                    claimProcsClaim2[i].ClaimNum = claim2.ClaimNum;
                    claimProcsClaim2[i].Status = ClaimProcStatus.NotReceived;
                    claimProcsClaim2[i].CodeSent = claimProcsClaim[i].CodeSent;
                    claimProcsClaim2[i].LineNumber = (byte)(i + 1);
                    ClaimProcs.Update(claimProcsClaim2[i]);
                }
                claimProcList = ClaimProcs.refresh(claim2.PatNum);
                Family fam = Patients.getFamily(claim2.PatNum);
                List<InsSub> subList = InsSubs.refreshForFam(fam);
                List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
                List<Benefit> benefitList = Benefits.Refresh(patPlansForPatient, subList);
                ClaimL.CalculateAndUpdate(procListAll, planList, claim2, patPlansForPatient, benefitList, patient.getAge(), subList);
                ClaimSendQueueItem queueItem2 = Claims.GetQueueList(claim2.ClaimNum, claim2.ClinicNum, 0)[0];
                String responseMessageVersion = result.Substring(18, 2);
                //Field A03 always exists on all messages and is always in the same location.
                //ok to skip validation
                //We can only send an electronic secondary claim when the EOB received from the primary insurance is a version 04 message and when
                //the secondary carrier accepts secondary claims electronically (COBs). Otherwise, the user must send the claim by paper.
                if (!StringSupport.equals(responseMessageVersion, "02") && (carrier2.CanadianSupportedTypes & CanSupTransTypes.CobClaimTransaction_07) == CanSupTransTypes.CobClaimTransaction_07)
                {
                    long etransNum = sendClaim(queueItem2,doPrint);
                    return etransNum;
                }
                 
                //recursive
                //for now, we'll return the etransnum of the secondary ack.
                //The secondary carrier does not support COB claim transactions. We must print a manual claim form.
                if (doPrint)
                {
                    printManualClaimForm(claim2);
                }
                 
            }
            else
            {
            } 
        }
         
        return etransAck.EtransNum;
    }

    //an embedded message exists
    //string embeddedMsg=fieldInputter.GetValue("G40");
    //MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(embeddedMsg);
    //msgbox.Show();
    //actually, nothing to do here because already printed above.
    public static void printManualClaimForm(Claim claim) throws Exception {
        try
        {
            FormClaimPrint FormCP = new FormClaimPrint();
            FormCP.PatNumCur = claim.PatNum;
            FormCP.ClaimNumCur = claim.ClaimNum;
            FormCP.ClaimFormCur = null;
            //so that it will pull from the individual claim or plan.
            PrintDocument pd = new PrintDocument();
            if (PrinterL.setPrinter(pd,PrintSituation.Claim,claim.PatNum,"Canadian claim manually printed"))
            {
                pd.PrinterSettings.Copies = 1;
                //Used to be sent in the FormCP.PrintImmediate function call below.  Moved up here to keep same logic.
                if (FormCP.printImmediate(pd.PrinterSettings))
                {
                    Etranss.SetClaimSentOrPrinted(claim.ClaimNum, claim.PatNum, 0, EtransType.ClaimPrinted, 0);
                }
                 
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //Oh well, the user can manually reprint if needed.
    public static void showManualClaimForm(Claim claim) throws Exception {
        FormClaimPrint FormCP = new FormClaimPrint();
        FormCP.PatNumCur = claim.PatNum;
        FormCP.ClaimNumCur = claim.ClaimNum;
        FormCP.ClaimFormCur = null;
        //so that it will pull from the individual claim or plan.
        FormCP.ShowDialog();
    }

    private static String convertEOBVersion(String primaryEOB, String versionTo) throws Exception {
        try
        {
            CCDFieldInputter primaryEOBfields = new CCDFieldInputter(primaryEOB);
            CCDField fieldA03 = primaryEOBfields.getFieldById("A03");
            if (fieldA03 != null && !StringSupport.equals(fieldA03.valuestr, versionTo))
            {
                StringBuilder strb = new StringBuilder(primaryEOB);
                return strb.ToString();
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return primaryEOB;
    }

    //todo: perform format conversion here.
    //There was an error converting the primary EOB to the correct version. Just return the original and hope it works.
    //No conversion necessary/possible.
    /**
    * Used for ITRANS and Claimstream. Takes a string, creates a file, and drops it into the clearinghouse export path.  Waits for the response, and then returns it as a string.  Will throw an exception if response not received in a reasonable amount of time.
    */
    public static String passToIca(String msgText, Clearinghouse clearhouse) throws Exception {
        if (clearhouse == null)
        {
            throw new ApplicationException(Lan.g("Canadian","A CDAnet compatible clearinghouse could not be found."));
        }
         
        String saveFolder = clearhouse.ExportPath;
        if (!Directory.Exists(saveFolder))
        {
            throw new ApplicationException(saveFolder + " not found.");
        }
         
        boolean isItrans = (clearhouse.CommBridge == EclaimsCommBridge.ITRANS);
        boolean isClaimstream = (clearhouse.CommBridge == EclaimsCommBridge.Claimstream);
        if (isClaimstream)
        {
            String certFilePath = CodeBase.ODFileUtils.combinePaths(saveFolder,"OPENDENTAL.pem");
            if (!File.Exists(certFilePath))
            {
                File.WriteAllBytes(certFilePath, Resources.getOPENDENTAL_PEM());
            }
             
        }
         
        String officeSequenceNumber = msgText.Substring(12, 6);
        //Field A02. Office Sequence Number is always part of every message type and is always in the same place.
        int fileNum = PIn.Int(officeSequenceNumber) % 1000;
        //first, delete the result file from previous communication so that no such files can affect the loop logic below.
        String outputFile = CodeBase.ODFileUtils.combinePaths(saveFolder,"output." + fileNum.ToString().PadLeft(3, '0'));
        if (File.Exists(outputFile))
        {
            File.Delete(outputFile);
        }
         
        //no exception thrown if file does not exist.
        //create the input file with data:
        String tempInputFile = CodeBase.ODFileUtils.combinePaths(saveFolder,"tempinput." + fileNum.ToString().PadLeft(3, '0'));
        //First, write to a temp file so that the clearinghouse sofware does not try to send the file while it is still being written.
        File.WriteAllText(tempInputFile, msgText, Encoding.GetEncoding(850));
        //Now that the file is completely written, rename it to the input format that the clearinghouse will recognize and process.
        String inputFile = CodeBase.ODFileUtils.combinePaths(saveFolder,"input." + fileNum.ToString().PadLeft(3, '0'));
        File.Move(tempInputFile, inputFile);
        //The input file should not exist, because the clearinghouse software should process input files promptly, unless the clearinghouse service is off for 1000 transactions in a row. We want an exception to be thrown if this file already exists.
        DateTime start = DateTime.Now;
        while (DateTime.Now < start.AddSeconds(120))
        {
            //We wait for up to 120 seconds. Responses can take up to 95 seconds and we need some extra time to be sure.
            if (File.Exists(outputFile))
            {
                break;
            }
             
            Thread.Sleep(200);
            //1/10 second
            Application.DoEvents();
        }
        //The _nput.### file is just the input.### renamed after it is processed. The clearinghouse service renames the file so that it is not processed more than once.
        String nputFile = CodeBase.ODFileUtils.combinePaths(saveFolder,"_nput." + fileNum.ToString().PadLeft(3, '0'));
        //We delete the intermediate file so that claim data is not just lying around.
        if (File.Exists(nputFile))
        {
            //The file would not appear to exist if there was a permission issue.
            File.Delete(nputFile);
        }
         
        //no exception thrown if file does not exist.
        if (!File.Exists(outputFile))
        {
            if (isItrans)
            {
                throw new ApplicationException("No response from iCAService. Ensure that the iCAService is started and the iCA folder has the necessary permissions.");
            }
            else if (isClaimstream)
            {
                throw new ApplicationException("No response from the CCDWS service. Ensure that the CCDWS service is started and the ccd folder has the necessary permissions.");
            }
              
            throw new ApplicationException("No response from clearinghouse service. Ensure that the clearinghouse service is started and the export folder has the necessary permissions.");
        }
         
        //Other clearinghouses, if we ever support them.
        byte[] resultBytes = File.ReadAllBytes(outputFile);
        String result = Encoding.GetEncoding(850).GetString(resultBytes);
        //strip the prefix.  Example prefix: 123456,0,000,
        String resultPrefix = "";
        //Find position of third comma
        Match match = Regex.Match(result, "^\\d*,\\d+,\\d+,");
        if (match.Success)
        {
            resultPrefix = result.Substring(0, match.Length);
            result = result.Substring(resultPrefix.Length);
        }
         
        //We delete the output file so that claim data is not just lying around.
        if (File.Exists(outputFile))
        {
            //The file would not appear to exist if there was a permission issue.
            File.Delete(outputFile);
        }
         
        //no exception thrown if file does not exist.
        if (result.Length < 42)
        {
            //The shortest message is a version 02 Request for Pended Claims with length 42. Any message shorter is considered to be an error message.
            //The only valid message less than 42 characters in length is an Outstanding Transactions Acknowledgement indicating that the mailbox is empty.
            if (result.Length >= 25 && result.Substring(12).StartsWith("NO MORE ITEMS"))
            {
                return result;
            }
             
            //Since the message does not have a well defined format, we skip the code below for parsing the result to check for a mailbox indicator.
            //Additionally, there is no need to check for a mailbox indicator because this message from ITRANS tells us that the mailbox is empty anyway.
            String[] responses = resultPrefix.Split(',');
            String errorCode = responses[1];
            String response = "Error " + errorCode + "\r\n\r\n" + "Raw Response:\r\n" + resultPrefix + result + "\r\n\r\n";
            if (isItrans)
            {
                if (StringSupport.equals(errorCode, "1013"))
                {
                    response += "iCAService could not locate a valid digital certificate.\r\n";
                }
                 
                String errorFile = CodeBase.ODFileUtils.CombinePaths(Path.GetDirectoryName(clearhouse.ClientProgram), "ica.log");
                String errorlog = "";
                if (File.Exists(errorFile))
                {
                    errorlog = File.ReadAllText(errorFile);
                }
                 
                response += "\r\nPlease see http://goitrans.com/itrans_support/itrans_claim_support_error_codes.htm for more details.\r\n";
                response += "Error log:\r\n" + errorlog;
            }
            else if (isClaimstream)
            {
                String errorDescription = "";
                RefSupport<String> refVar___0 = new RefSupport<String>(errorDescription);
                String errorMessage = getErrorMessageForCodeClaimstream(errorCode,refVar___0);
                errorDescription = refVar___0.getValue();
                response += "Error Message: " + errorMessage + "\r\n";
                response += "Error Description: " + errorDescription + "\r\n\r\n";
                response += "For further error details, read the log file ccdws.log.";
            }
              
            throw new ApplicationException(response);
        }
         
        //We parse the result to look for a few things no matter what type of transaction we are dealing with.
        String requestOfficeSequenceNumber = msgText.Substring(12, 6);
        //Field A02 always exists on all messages and is always in the same location.
        String responseOfficeSequenceNumber = result.Substring(12, 6);
        //Field A02 always exists on all messages and is always in the same location.
        String requestMessageType = msgText.Substring(20, 2);
        //Field A04 always exists on all messages and is always in the same location.
        String responseMessageType = result.Substring(20, 2);
        //Field A04 always exists on all messages and is always in the same location.
        if (!StringSupport.equals(responseOfficeSequenceNumber, requestOfficeSequenceNumber))
        {
            //Type 04 is an ROT, type 14 is an ROT response. Keep in mind that ROT transactions can get multiple types of responses back.
            if (!StringSupport.equals(requestMessageType, "04") || (StringSupport.equals(requestMessageType, "04") && StringSupport.equals(responseMessageType, "14")))
            {
                if (isItrans)
                {
                    throw new ApplicationException(Lan.g("Canadian","The office sequence number in the response from ITRANS is invalid. Response") + ": " + result);
                }
                else if (isClaimstream)
                {
                    throw new ApplicationException(Lan.g("Canadian","The office sequence number in the response from Claimstream is invalid. Response") + ": " + result);
                }
                  
                throw new ApplicationException(Lan.g("Canadian","The office sequence number in the response from the clearinghouse is invalid. Response") + ": " + result);
            }
             
        }
         
        //Other clearinghouses, if we ever support them.
        CCDFieldInputter messageData = new CCDFieldInputter(result);
        //We check the mailbox indicator here, only when the response is returned the first time instead of showing it in FormCCDPrint, because
        //we do not want the mailbox indicator to be examined multiple times, which could happen if a transaction is viewed again using FormCCDPrint.
        CCDField mailboxIndicator = messageData.getFieldById("A11");
        if (mailboxIndicator != null)
        {
            //Field A11 should exist in all response types, but just in case.
            if (StringSupport.equals(mailboxIndicator.valuestr.ToUpper(), "Y") || StringSupport.equals(mailboxIndicator.valuestr.ToUpper(), "O"))
            {
                MsgBox.show("Canadian","NOTIFICATION: Items are waiting in the mailbox. Retrieve these items by going to the Manage module, click the Send Claims button, " + "then click the Outstanding button. This box will continue to show each time a claim is sent until the mailbox is cleared.");
            }
             
        }
         
        return result;
    }

    /**
    * Examines the errorCode passed in and returns the short error message string and sets the errorDescription corresponding to the code. Works for status codes and internal codes.
    */
    private static String getErrorMessageForCodeClaimstream(String errorCode, RefSupport<String> errorDescription) throws Exception {
        if (StringSupport.equals(errorCode, "0"))
        {
            //Status code
            errorDescription.setValue("The request was sent to the remote server and the response was successfully received and stored in the output file.");
            return "Success";
        }
        else if (StringSupport.equals(errorCode, "1001"))
        {
            //Status code
            errorDescription.setValue("A general error occurred. Check the log file for details, including internal error.");
            return "General error";
        }
        else if (StringSupport.equals(errorCode, "1026"))
        {
            //Status code
            errorDescription.setValue("CCDWS could not connect to the remote server.");
            return "No answer";
        }
        else if (StringSupport.equals(errorCode, "1033"))
        {
            //Status code
            errorDescription.setValue("The input file could not be read.");
            return "Error reading input";
        }
        else if (StringSupport.equals(errorCode, "1034"))
        {
            //Status code
            errorDescription.setValue("The input request does not conform to CDAnet v2, v3 or v4 message standards. See the log file for details.");
            return "Request invalid";
        }
        else if (StringSupport.equals(errorCode, "1042"))
        {
            //Status code
            errorDescription.setValue("A timeout occurred either sending the message to the server (write timeout) or receiving a response from the server (read timeout).");
            return "Server timeout";
        }
        else if (StringSupport.equals(errorCode, "1043"))
        {
            //Status code
            errorDescription.setValue("The server responded with unexpected data. This may happen if the server address is incorrect or the server is not correctly configured.");
            return "Invalid characters";
        }
        else if (StringSupport.equals(errorCode, "1045"))
        {
            //Status code
            errorDescription.setValue("The server disconnected unexpectedly. This will most frequently occur if a connection was made, but an SSL error was detected.");
            return "Server disconnect";
        }
        else if (StringSupport.equals(errorCode, "2"))
        {
            //Internal error code
            errorDescription.setValue("The specified file or directory does not exist or cannot be found. This message can occur whenever a specified file does not exist or a component of a path does not specify an existing directory.");
            return "No such file or directory";
        }
        else if (StringSupport.equals(errorCode, "8"))
        {
            //Internal error code
            //errorDescription="Not enough memory on CCDWS host, or there is a memory leak. Try rebooting workstation. Contact support personnel if problem persists.";
            errorDescription.setValue("Not enough memory on CCDWS host, or there is a memory leak. Try rebooting workstation.");
            return "Out of memory";
        }
        else if (StringSupport.equals(errorCode, "13"))
        {
            //Internal error code
            errorDescription.setValue("Application may not have permissions to read, write or execute one or more files, such as a log file, configuration file etc. Ensure that CCDWS user has rights to all of its read and write locations.");
            return "Permission denied";
        }
        else if (StringSupport.equals(errorCode, "21"))
        {
            //Internal error code
            errorDescription.setValue("The file attempting to be accessed is a directory, not a regular file. Check the configuration option of the file being used, and check the file system to ensure that a directory of the same name does not exist.");
            return "Is a directory";
        }
        else if (StringSupport.equals(errorCode, "24"))
        {
            //Internal error code
            errorDescription.setValue("No more file descriptors are available, so no more files can be opened.");
            return "Too many open files";
        }
        else if (StringSupport.equals(errorCode, "112"))
        {
            //Internal error code
            //errorDescription="Log files and/or temporary files cannot be written because there is no space left on the hard disk. Free up space by deleting old files, check the disk partitions to be certain that it is correctly partitioned, and look into purchasing a larger disk if necessary.";
            errorDescription.setValue("Log files and/or temporary files cannot be written because there is no space left on the hard disk.");
            return "There is not enough space on the disk";
        }
        else if (StringSupport.equals(errorCode, "6012"))
        {
            //Internal error code
            errorDescription.setValue("Payload contents were not in the expected format. Remote client is sending invalid data.");
            return "Payload parse error";
        }
        else if (StringSupport.equals(errorCode, "7001"))
        {
            //Internal error code
            errorDescription.setValue("A fault occurred, and the server has determined it is a problem with CCDWS. Ensure the configuration for the destination is correct.");
            return "SOAP: The service returned a client fault";
        }
        else if (StringSupport.equals(errorCode, "7002"))
        {
            //Internal error code
            //errorDescription="A fault occurred, and the server has determined it is a problem with the server itself. If the problem continues, contact support personnel.";
            errorDescription.setValue("A fault occurred, and the server has determined it is a problem with the server itself.");
            return "SOAP: The service returned a server fault";
        }
        else if (StringSupport.equals(errorCode, "7011"))
        {
            //Internal error code
            //errorDescription="Contact support personnel.";
            errorDescription.setValue("Contact CCDWS support personnel.");
            return "SOAP: Internal error";
        }
        else if (StringSupport.equals(errorCode, "7012"))
        {
            //Internal error code
            errorDescription.setValue("General SOAP fault. Neither client nor server has been specified. Look for further information in the log file.");
            return "SOAP: An exception raised by the service";
        }
        else if (StringSupport.equals(errorCode, "7014"))
        {
            //Internal error code
            errorDescription.setValue("An HTTP response was returned but did not contain a body.");
            return "SOAP: No data in HTTP message";
        }
        else if (StringSupport.equals(errorCode, "7020"))
        {
            //Internal error code
            //errorDescription="Not enough memory on CCDWS to create SOAP objects, or there is a memory leak. If host has sufficient memory ensure other memory intensive applications are not using it up. Try rebooting workstation. Contact support personnel if problem persists.";
            errorDescription.setValue("Not enough memory on CCDWS to create SOAP objects. Try rebooting workstation.");
            return "SOAP: Out of memory";
        }
        else if (StringSupport.equals(errorCode, "7023"))
        {
            //Internal error code
            //errorDescription="The WSDL expects a non-null element, but a null element was found. Contact support personnel.";
            errorDescription.setValue("The WSDL expects a non-null element, but a null element was found.");
            return "SOAP: An element was null while it is not supposed to be null";
        }
        else if (StringSupport.equals(errorCode, "7028"))
        {
            //Internal error code
            errorDescription.setValue("The endpoint is not specified in the configuration file or the destination host is not accepting connections on the expected port. Confirm the endpoint with the destination administrator. Ensure firewall is allowing access to the endpoint.");
            return "SOAP: A connection error occurred";
        }
        else if (StringSupport.equals(errorCode, "7030"))
        {
            //Internal error code
            //errorDescription="A general SSL occurred. Details may be displayed a fault message in the log.";
            errorDescription.setValue("A general SSL occurred. Details may be displayed in a fault message in the log.");
            return "SOAP: An SSL error occurred";
        }
        else if (StringSupport.equals(errorCode, "7039"))
        {
            //Internal error code
            errorDescription.setValue("Check the endpoint for the destination refers to a webservice destination. HTTP responses that contain data but not a SOAP response will generate this error.");
            return "SOAP: SOAP version mismatch or no SOAP message";
        }
        else if (StringSupport.equals(errorCode, "7098"))
        {
            //Internal error code
            //errorDescription="A timeout occurred waiting for a response. Contact support personnel if problem persists.";
            errorDescription.setValue("A timeout occurred waiting for a response.");
            return "SOAP: Read timeout";
        }
        else if (StringSupport.equals(errorCode, "7099"))
        {
            //Internal error code
            //errorDescription="This can occur if the destination server terminated the connection. If the error continues contact support personnel.";
            errorDescription.setValue("This can occur if the destination server terminated the connection.");
            return "SOAP: Server disconnect";
        }
        else if (StringSupport.equals(errorCode, "7400"))
        {
            //Internal error code
            errorDescription.setValue("The web service appears to be malformed. Check that all of the configuration options for the destination are appropriate.");
            return "HTTP: Bad Request";
        }
        else if (StringSupport.equals(errorCode, "7401"))
        {
            //Internal error code
            errorDescription.setValue("The endpoint attempting to be accessed requires authentication. Check that all of the configuration options for the destination are appropriate.");
            return "HTTP: Unauthorized";
        }
        else if (StringSupport.equals(errorCode, "7403"))
        {
            //Internal error code
            errorDescription.setValue("The server has forbidden access to the endpoint attempting to be accessed. Check that all of the configuration options for the destination are appropriate.");
            return "HTTP: Forbidden";
        }
        else if (StringSupport.equals(errorCode, "7404"))
        {
            //Internal error code
            errorDescription.setValue("The endpoint's path is not valid. Check that all of the configuration options for the destination are appropriate.");
            return "HTTP: Not Found";
        }
        else if (StringSupport.equals(errorCode, "7405"))
        {
            //Internal error code
            errorDescription.setValue("Some servers are poorly configured where the endpoint is a path or is to be redirected. If it is specified with a trailing slash, ensure that this is provided on the endpoint.");
            return "HTTP: Method Not Allowed";
        }
        else if (StringSupport.equals(errorCode, "7407"))
        {
            //Internal error code
            errorDescription.setValue("Ensure that the ProxyPassword configuration option is populated correctly, and that all other proxy related options are valid.");
            return "HTTP: Proxy Authentication. Required";
        }
        else if (StringSupport.equals(errorCode, "7408"))
        {
            //Internal error code
            //errorDescription="Problem writing data to the destination host. Try again, and contact support personnel if the problem persists.";
            errorDescription.setValue("Problem writing data to the destination host.");
            return "HTTP: Request Timeout";
        }
        else if (StringSupport.equals(errorCode, "7415"))
        {
            //Internal error code
            //errorDescription="This may be the result of an incompatibility between the SOAP version submitted by CCDWS and that of the destination server. Contact support personnel.";
            errorDescription.setValue("This may be the result of an incompatibility between the SOAP version submitted by CCDWS and that of the destination server.");
            return "HTTP: Unsupported Media Type";
        }
        else if (StringSupport.equals(errorCode, "7500"))
        {
            //Internal error code
            //errorDescription="An internal error occurred at the destination server. Contact support personnel.";
            errorDescription.setValue("An internal error occurred at the destination server.");
            return "HTTP: Internal Server Error";
        }
        else if (StringSupport.equals(errorCode, "7501"))
        {
            //Internal error code
            //errorDescription="The destination server does not support the functionality to fulfil the request. Confirm that the destination endpoint is correct. Contact support personnel if the problem persists.";
            errorDescription.setValue("The destination server does not support the functionality to fulfil the request. Confirm that the destination endpoint is correct.");
            return "HTTP: Not Implemented";
        }
        else if (StringSupport.equals(errorCode, "7502"))
        {
            //Internal error code
            //errorDescription="The destination server received an invalid response from an upstream server. Contact support personnel if the problem persists.";
            errorDescription.setValue("The destination server received an invalid response from an upstream server.");
            return "HTTP: Bad Gateway";
        }
        else if (StringSupport.equals(errorCode, "7503"))
        {
            //Internal error code
            //errorDescription="The destination server is unable to handle the request due to temporary overloading or maintenance of the server. Contact support personnel if the problem persists.";
            errorDescription.setValue("The destination server is unable to handle the request due to temporary overloading or maintenance of the server.");
            return "HTTP: Service Unavailable";
        }
        else if (StringSupport.equals(errorCode, "7504"))
        {
            //Internal error code
            //errorDescription="The destination server did not receive a timely response from an upstream server. Contact support personnel if the problem persists.";
            errorDescription.setValue("The destination server did not receive a timely response from an upstream server.");
            return "HTTP: Gateway Timeout";
        }
        else if (StringSupport.equals(errorCode, "7505"))
        {
            //Internal error code
            //errorDescription="The SOAP version supported by CCDWS and that of the destination are not compatible. Contact support personnel.";
            errorDescription.setValue("The SOAP version supported by CCDWS and that of the destination are not compatible.");
            return "HTTP: HTTP Version not supported";
        }
                                                 
        errorDescription.setValue("UNKNOWN");
        return "UNKNOWN";
    }

    public static char getCanadianChar(byte b) throws Exception {
        return Encoding.GetEncoding(850).GetString(new byte[]{ b })[0];
    }

    /**
    * Only used for Canadian messages. If carrier is not null, then returns the first clearinghouse with a payorid matching the carrier if one exists. If no carrier specific clearinghouses are located, then returns the default clearinghouse if it is Canadian. Otherwise, in the worst case, will return the first Canadian clearinghouse that it can find. If for some reason no Canadian clearinghouses exist, then null is returned.
    */
    public static Clearinghouse getCanadianClearinghouse(Carrier carrier) throws Exception {
        String payerid = "";
        if (carrier != null)
        {
            payerid = carrier.ElectID;
        }
         
        //Returns the default dental clearinghouse if the payerid is not associated to any clearinghouses.
        long clearinghouseNum = Clearinghouses.automateClearinghouseSelection(payerid,EnumClaimMedType.Dental);
        if (clearinghouseNum != 0)
        {
            Clearinghouse clearhouse = Clearinghouses.getClearinghouse(clearinghouseNum);
            if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
            {
                return clearhouse;
            }
             
        }
         
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            //Might not be Canadian, if for instance they accidentally set their default clearinghouse to an American clearinghouse.
            //Return the default dental clearinghouse if it is a Canadian clearinghouse.
            if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == Clearinghouses.getListt()[i].ClearinghouseNum && Clearinghouses.getListt()[i].Eformat == ElectronicClaimFormat.Canadian)
            {
                return Clearinghouses.getListt()[i];
            }
             
        }
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            //Return the first Canadian clearinghouse if one exists.
            if (Clearinghouses.getListt()[i].Eformat == ElectronicClaimFormat.Canadian)
            {
                return Clearinghouses.getListt()[i];
            }
             
        }
        return null;
    }

    //No Canadian clearinghouses exist. Should never happen, unless the user manually changes the ITRANS and ClaimStream clearinghouses to another electronic format.
    /**
    * Decimal.
    */
    public static String tidyD(double number, int width) throws Exception {
        String retVal = (number * 100).ToString("F0");
        if (retVal.Length > width)
        {
            return retVal.Substring(0, width);
        }
         
        return retVal.PadLeft(width, '0');
    }

    //this should never happen, but it might prevent a malformed claim.
    /**
    * Numeric
    */
    public static String tidyN(int number, int width) throws Exception {
        String retVal = number.ToString();
        if (retVal.Length > width)
        {
            return retVal.Substring(0, width);
        }
         
        return retVal.PadLeft(width, '0');
    }

    //this should never happen, but it might prevent a malformed claim.
    /**
    * Numeric
    */
    public static String tidyN(String numText, int width) throws Exception {
        String retVal = "";
        try
        {
            int number = Convert.ToInt32(numText);
            retVal = number.ToString();
        }
        catch (Exception __dummyCatchVar2)
        {
            retVal = "";
        }

        if (retVal.Length > width)
        {
            return retVal.Substring(0, width);
        }
         
        return retVal.PadLeft(width, '0');
    }

    //this should never happen, but it might prevent a malformed claim.
    /**
    * This should never involve user input and is rarely used.  It only handles width and padding.
    */
    public static String tidyA(String text, int width) throws Exception {
        if (text.Length > width)
        {
            return text.Substring(0, width);
        }
         
        return text.PadRight(width, ' ');
    }

    /**
    * Alphabetic in addition to apost, dash, comma, space, and extended characters (128-255). No numbers. Returns string will letters all upper-case. For testing, here are some extended chars: à â ç é è ê ë î ï ô û ù ü ÿ
    */
    public static String tidyAE(String text, int width) throws Exception {
        return tidyAE(text,width,false);
    }

    /**
    * Alphabetic in addition to apost, dash, comma, space, and extended characters (128-255). No numbers. For testing, here are some extended chars: à â ç é è ê ë î ï ô û ù ü ÿ
    */
    public static String tidyAE(String text, int width, boolean allowLowercase) throws Exception {
        if (!allowLowercase)
        {
            text = text.ToUpper();
        }
         
        text = Regex.Replace(text, "[0-9]", "");
        //replace
        //any character that's a number
        //with nothing (delete it). This extra replace is needed, because \w in the replace below includes numerical digits.
        text = Regex.Replace(text, "[^\\w\'\\-, ]", "");
        //replace
        //any character that's not a word character or an apost, dash, comma, or space
        //with nothing (delete it)
        if (text.Length > width)
        {
            return text.Substring(0, width);
        }
         
        return text.PadRight(width, ' ');
    }

    /**
    * Alphabetic/Numeric, no extended characters (128-255).  Returns string with letters all upper-case.
    */
    public static String tidyAN(String text, int width) throws Exception {
        return tidyAN(text,width,false);
    }

    /**
    * Alphabetic/Numeric, no extended characters (128-255).
    */
    public static String tidyAN(String text, int width, boolean allowLowercase) throws Exception {
        if (!allowLowercase)
        {
            text = text.ToUpper();
        }
         
        text = Regex.Replace(text, "[^a-zA-Z0-9 \'\\-,]", "");
        //replace
        //any char that is not A-Z,0-9,space,apost,dash,or comma
        //with nothing (delete it)
        if (text.Length > width)
        {
            return text.Substring(0, width);
        }
         
        return text.PadRight(width, ' ');
    }

    /**
    * Alphabetic/Numeric, with extended characters (128-255).
    */
    public static String tidyAEN(String text, int width) throws Exception {
        return tidyAEN(text,width,false);
    }

    /**
    * Alphabetic/Numeric with extended characters (128-255). Only handles width and padding. For testing, here are some: à â ç é è ê ë î ï ô û ù ü ÿ
    */
    public static String tidyAEN(String text, int width, boolean allowLowercase) throws Exception {
        if (!allowLowercase)
        {
            text = text.ToUpper();
        }
         
        if (text.Length > width)
        {
            return text.Substring(0, width);
        }
         
        return text.PadRight(width, ' ');
    }

    /**
    * Must always return single char.
    */
    private static String getMaterialsForwarded(String materials) throws Exception {
        boolean E = materials.Contains("E");
        boolean C = materials.Contains("C");
        boolean M = materials.Contains("M");
        boolean X = materials.Contains("X");
        boolean I = materials.Contains("I");
        if (E && C && M && X && I)
        {
            return "Z";
        }
         
        if (C && M && X && I)
        {
            return "Y";
        }
         
        if (E && C && X && I)
        {
            return "W";
        }
         
        if (E && C && M && I)
        {
            return "V";
        }
         
        if (E && C && M && X)
        {
            return "U";
        }
         
        if (M && X && I)
        {
            return "T";
        }
         
        if (C && M && X)
        {
            return "R";
        }
         
        if (C && M && I)
        {
            return "R";
        }
         
        if (E && C && I)
        {
            return "Q";
        }
         
        if (E && C && X)
        {
            return "P";
        }
         
        if (E && C && M)
        {
            return "O";
        }
         
        if (X && I)
        {
            return "N";
        }
         
        if (M && I)
        {
            return "L";
        }
         
        if (M && X)
        {
            return "K";
        }
         
        if (C && I)
        {
            return "J";
        }
         
        if (C && X)
        {
            return "H";
        }
         
        if (C && M)
        {
            return "G";
        }
         
        if (E && I)
        {
            return "F";
        }
         
        if (E && X)
        {
            return "D";
        }
         
        if (E && M)
        {
            return "B";
        }
         
        if (E && C)
        {
            return "A";
        }
         
        if (I)
        {
            return "I";
        }
         
        if (X)
        {
            return "X";
        }
         
        if (M)
        {
            return "M";
        }
         
        if (C)
        {
            return "C";
        }
         
        if (E)
        {
            return "E";
        }
         
        return " ";
    }

    /**
    * Used in C03 and E06
    */
    public static String getRelationshipCode(Relat relat) throws Exception {
        switch(relat)
        {
            case Self: 
                return "1";
            case Spouse: 
                return "2";
            case Child: 
                return "3";
            case LifePartner: 
            case SignifOther: 
                return "4";
            default: 
                return "5";
        
        }
    }

    //commonlaw spouse
    //other (ex elderly care)
    public static String getPlanFlag(String planFlag) throws Exception {
        if (StringSupport.equals(planFlag, "A") || StringSupport.equals(planFlag, "V"))
        {
            return planFlag;
        }
         
        return " ";
    }

    /**
    * Because the numberins scheme is slightly different for version 2, this field (C09) should always be passed through here.
    */
    public static String getEligibilityCode(byte rawCode, boolean isVersion02) throws Exception {
        if (isVersion02 && rawCode == 4)
        {
            return "0";
        }
         
        return rawCode.ToString();
    }

    /**
    * Checks for either valid USA state or valid Canadian territory.
    */
    private static boolean isValidST(String ST) throws Exception {
        if (isValidTerritory(ST) || isValidState(ST))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Checks for valid USA state.
    */
    private static boolean isValidState(String ST) throws Exception {
        String[] validStates = new String[]{ "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "WA", "WV", "WI", "WY" };
        for (int i = 0;i < validStates.Length;i++)
        {
            if (StringSupport.equals(validStates[i], ST))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Checks for valid Canadian terriroty.
    */
    private static boolean isValidTerritory(String ST) throws Exception {
        //http://www.nrcan.gc.ca/earth-sciences/geography-boundary/geographical-name/translators/5782
        String[] validStates = new String[]{ "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT", "LB", "NF", "PQ" };
        for (int i = 0;i < validStates.Length;i++)
        {
            //Canadian province codes.
            //Alberta
            //Britich Columbia
            //Manitoba
            //New Brunswick
            //Newfoundland and Labrador
            //Nova Scotia
            //Northwest Territories
            //Nunavut
            //Ontario
            //Prince Edward Island
            //Quebec
            //Saskatchewan
            //Yukon
            //Traditional Canadian province codes which somehow made it into our application, but we are going to leave them because they are probably harmless.
            //Newfoundland and Labrador - This appeared in Canada Post publications (e.g., The Canadian Postal Code Directory) for the mainland section of the province of Newfoundland and Labrador.
            //Newfoundland and Labrador - Nfld. and later NF (the two-letter abbreviation used before the province's name changed to Newfoundland and Labrador) and T.-N. (French version, for Terre-Neuve)
            //Quebec	- Que. and P.Q. (French version, for Province du Québec); later, PQ evolved from P.Q. as the first two-letter non-punctuated abbreviation.
            if (StringSupport.equals(validStates[i], ST))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Validates USA and Canadian postal codes.
    */
    private static boolean isValidZip(String zip) throws Exception {
        if (Regex.IsMatch(zip.Trim(), "^[0-9]{5}$"))
        {
            return true;
        }
         
        //USA 5 digit zip code.
        if (Regex.IsMatch(zip.Trim().Replace("-", ""), "^[0-9]{9}$"))
        {
            return true;
        }
         
        //USA 9 digit zip code.
        if (Regex.IsMatch(zip.Trim(), "^[A-Z][0-9][A-Z][ ]?[0-9][A-Z][0-9]$"))
        {
            return true;
        }
         
        return false;
    }

    //Canadian postal code. ANANAN or ANA NAN, with whitespace in front or behind.
    private static String getToothQuadOrArch(Procedure proc, ProcedureCode procCode) throws Exception {
        switch(procCode.TreatArea)
        {
            case Arch: 
                return "00";
            case Mouth: 
            case None: 
                return "00";
            case Quad: 
                //if(proc.Surf=="U"){
                //}
                //else{
                //	return "01";
                //}
                if (StringSupport.equals(proc.Surf, "UR"))
                {
                    return "10";
                }
                else if (StringSupport.equals(proc.Surf, "UL"))
                {
                    return "20";
                }
                else if (StringSupport.equals(proc.Surf, "LR"))
                {
                    return "40";
                }
                else
                {
                    return "30";
                }   
            case Sextant: 
                //LL
                if (StringSupport.equals(proc.Surf, "1"))
                {
                    return "01";
                }
                else if (StringSupport.equals(proc.Surf, "2"))
                {
                    return "02";
                }
                else if (StringSupport.equals(proc.Surf, "3"))
                {
                    return "03";
                }
                else if (StringSupport.equals(proc.Surf, "4"))
                {
                    return "04";
                }
                else if (StringSupport.equals(proc.Surf, "5"))
                {
                    return "05";
                }
                else if (StringSupport.equals(proc.Surf, "6"))
                {
                    return "06";
                }
                      
                return "00";
            case Surf: 
            case Tooth: 
                return Tooth.toInternat(proc.ToothNum);
            case ToothRange: 
                //Invalid or unspecified sextant.
                String[] range = proc.ToothRange.Split(',');
                if (range.Length == 0 || !Tooth.IsValidDB(range[0]))
                {
                    return "00";
                }
                else if (Tooth.IsMaxillary(range[0]))
                {
                    return "00";
                }
                  
                return "00";
        
        }
        return "00";
    }

    //will never happen
    /**
    * Returns a string describing all missing data on this claim.  Claim will not be allowed to be sent electronically unless this string comes back empty.
    */
    public static String getMissingData(ClaimSendQueueItem queueItem) throws Exception {
        String retVal = "";
        Clearinghouse clearhouse = ClearinghouseL.getClearinghouse(queueItem.ClearinghouseNum);
        Claim claim = Claims.getClaim(queueItem.ClaimNum);
        Clinic clinic = Clinics.getClinic(claim.ClinicNum);
        Provider billProv = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvBill)];
        Provider treatProv = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvTreat)];
        InsSub insSub = InsSubs.GetSub(claim.InsSubNum, new List<InsSub>());
        InsPlan insPlan = InsPlans.GetPlan(claim.PlanNum, new List<InsPlan>());
        Carrier carrier = Carriers.getCarrier(insPlan.CarrierNum);
        if (carrier.CanadianNetworkNum == 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Primary carrier network not set";
        }
         
        if (!StringSupport.equals(carrier.CDAnetVersion, "02"))
        {
            if (!StringSupport.equals(carrier.CDAnetVersion, "04"))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Primary carrier CDANet version must be 02 or 04";
            }
             
            if (carrier.CanadianEncryptionMethod != 1 && carrier.CanadianEncryptionMethod != 2 && carrier.CanadianEncryptionMethod != 3)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Primary carrier encryption method must be 1, 2 or 3";
            }
             
        }
         
        InsSub insSub2 = null;
        InsPlan insPlan2 = null;
        Carrier carrier2 = null;
        Patient subscriber2 = null;
        if (!StringSupport.equals(claim.ClaimType, "S") && claim.PlanNum2 > 0)
        {
            insSub2 = InsSubs.GetSub(claim.InsSubNum2, new List<InsSub>());
            insPlan2 = InsPlans.GetPlan(claim.PlanNum2, new List<InsPlan>());
            carrier2 = Carriers.getCarrier(insPlan2.CarrierNum);
            if (carrier2.CanadianNetworkNum == 0)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Secondary carrier network not set";
            }
             
            if (!StringSupport.equals(carrier2.CDAnetVersion, "02"))
            {
                if (!StringSupport.equals(carrier2.CDAnetVersion, "04"))
                {
                    if (!StringSupport.equals(retVal, ""))
                        retVal += ", ";
                     
                    retVal += "Secondary carrier CDANet version must be 02 or 04";
                }
                 
                if (carrier2.CanadianEncryptionMethod != 1 && carrier2.CanadianEncryptionMethod != 2 && carrier2.CanadianEncryptionMethod != 3)
                {
                    if (!StringSupport.equals(retVal, ""))
                        retVal += ", ";
                     
                    retVal += "Secondary carrier encryption method must be 1, 2 or 3";
                }
                 
            }
             
            subscriber2 = Patients.getPat(insSub2.Subscriber);
        }
         
        Patient patient = Patients.getPat(claim.PatNum);
        Patient subscriber = Patients.getPat(insSub.Subscriber);
        List<ClaimProc> claimProcList = ClaimProcs.refresh(patient.PatNum);
        //all claimProcs for a patient.
        List<ClaimProc> claimProcsClaim = ClaimProcs.GetForSendClaim(claimProcList, claim.ClaimNum);
        List<Procedure> procListAll = Procedures.refresh(claim.PatNum);
        Procedure proc;
        ProcedureCode procCode;
        List<Procedure> extracted = Procedures.GetCanadianExtractedTeeth(procListAll);
        if (!Regex.IsMatch(carrier.ElectID, "^[0-9]{6}$"))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "CarrierId 6 digits";
        }
         
        if (treatProv.NationalProvID.Length != 9)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "TreatingProv CDA num 9 digits";
        }
         
        if (treatProv.CanadianOfficeNum.Length != 4)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "TreatingProv office num 4 char";
        }
         
        if (billProv.NationalProvID.Length != 9)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "BillingProv CDA num 9 digits";
        }
         
        if (billProv.CanadianOfficeNum.Length != 4)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "BillingProv office num 4 char";
        }
         
        if (insPlan.GroupNum.Length == 0 || insPlan.GroupNum.Length > 12 || insPlan.GroupNum.Contains(" "))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Plan Number";
        }
         
        if (StringSupport.equals(insSub.SubscriberID, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "SubscriberID";
        }
         
        //if patient is not subscriber
        if (claim.PatNum != insSub.Subscriber && claim.PatRelat == Relat.Self)
        {
            //and relat is self
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Claim Relationship";
        }
         
        if (patient.Gender == PatientGender.Unknown)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Patient gender";
        }
         
        if (patient.Birthdate.Year < 1880 || patient.Birthdate > DateTime.Today)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Patient birthdate";
        }
         
        if (StringSupport.equals(patient.LName, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Patient lastname";
        }
         
        if (StringSupport.equals(patient.FName, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Patient firstname";
        }
         
        if (patient.getAge() >= 18 && patient.CanadianEligibilityCode == 1)
        {
            //fulltimeStudent
            if (StringSupport.equals(patient.SchoolName, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Patient school name";
            }
             
        }
         
        if (subscriber.Birthdate.Year < 1880 || subscriber.Birthdate > DateTime.Today)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber birthdate";
        }
         
        if (StringSupport.equals(subscriber.LName, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber lastname";
        }
         
        if (StringSupport.equals(subscriber.FName, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber firstname";
        }
         
        if (StringSupport.equals(subscriber.Address, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber address";
        }
         
        if (StringSupport.equals(subscriber.City, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber city";
        }
         
        if (!isValidST(subscriber.State))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber Province";
        }
         
        if (!isValidZip(subscriber.Zip))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Subscriber Postalcode";
        }
         
        if (claimProcsClaim.Count > 7)
        {
            //user interface enforces prevention of claim with 0 procs.
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Over 7 procs";
        }
         
        //incomplete. Also duplicate for max
        //user interface also needs to be improved to prompt and remind about extracted teeth
        /*if(isInitialLowerProsth && MandProsthMaterial!=0 && CountLower(extracted.Count)==0){
        				if(retVal!="")
        					retVal+=",";
        				retVal+="Missing teeth not entered";
        			}*/
        if (StringSupport.equals(carrier.ElectID, "000064"))
        {
            //Checks for Pacific Blue Cross (PBC) for primary as required for certification.
            List<PatPlan> patPlansForPatient = PatPlans.refresh(claim.PatNum);
            for (int p = 0;p < patPlansForPatient.Count;p++)
            {
                if (patPlansForPatient[p].InsSubNum == claim.InsSubNum)
                {
                    retVal += GetMissingDataForPatPlanPacificBlueCross(patPlansForPatient[p], insPlan);
                    break;
                }
                 
            }
        }
         
        if (carrier2 != null && StringSupport.equals(carrier2.ElectID, "000064"))
        {
            //Checks for Pacific Blue Cross (PBC) for secondary as required for certification.
            List<PatPlan> patPlansForPatient = PatPlans.refresh(claim.PatNum);
            for (int p = 0;p < patPlansForPatient.Count;p++)
            {
                if (patPlansForPatient[p].InsSubNum == claim.InsSubNum2)
                {
                    retVal += GetMissingDataForPatPlanPacificBlueCross(patPlansForPatient[p], insPlan2);
                    break;
                }
                 
            }
        }
         
        if (!StringSupport.equals(claim.ClaimType, "S") && claim.PlanNum2 > 0)
        {
            if (!Regex.IsMatch(carrier2.ElectID, "^[0-9]{6}$"))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec CarrierId 6 digits";
            }
             
            if (insPlan2.GroupNum.Length == 0 || insPlan2.GroupNum.Length > 12 || insPlan2.GroupNum.Contains(" "))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Plan Number";
            }
             
            if (StringSupport.equals(insSub2.SubscriberID, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec SubscriberID";
            }
             
            //if patient is not subscriber
            if (claim.PatNum != insSub2.Subscriber && claim.PatRelat2 == Relat.Self)
            {
                //and relat is self
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Relationship";
            }
             
            if (subscriber2.Birthdate.Year < 1880 || subscriber2.Birthdate > DateTime.Today)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber birthdate";
            }
             
            if (StringSupport.equals(subscriber2.LName, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber lastname";
            }
             
            if (StringSupport.equals(subscriber2.FName, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber firstname";
            }
             
            if (StringSupport.equals(subscriber2.Address, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber address";
            }
             
            if (StringSupport.equals(subscriber2.City, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber city";
            }
             
            if (!isValidST(subscriber2.State))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber Province";
            }
             
            if (!isValidZip(subscriber2.Zip))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Sec Subscriber Postalcode";
            }
             
        }
         
        if (!StringSupport.equals(claim.CanadianReferralProviderNum, "") && claim.CanadianReferralReason == 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Referral reason";
        }
         
        if (StringSupport.equals(claim.CanadianReferralProviderNum, "") && claim.CanadianReferralReason != 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Referral provider";
        }
         
        //Max Prosth--------------------------------------------------------------------------------------------------
        if (StringSupport.equals(claim.CanadianIsInitialUpper, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Max prosth";
        }
         
        if (claim.CanadianDateInitialUpper > DateTime.MinValue)
        {
            if (claim.CanadianDateInitialUpper.Year < 1900 || claim.CanadianDateInitialUpper >= DateTime.Today)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Date initial upper";
            }
             
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialUpper, "N") && claim.CanadianDateInitialUpper.Year < 1900)
        {
            //missing date
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Date initial upper";
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialUpper, "N") && claim.CanadianMaxProsthMaterial == 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Max prosth material";
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialUpper, "X") && claim.CanadianMaxProsthMaterial != 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Max prosth material";
        }
         
        //Mand prosth--------------------------------------------------------------------------------------------------
        if (StringSupport.equals(claim.CanadianIsInitialLower, ""))
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Mand prosth";
        }
         
        if (claim.CanadianDateInitialLower > DateTime.MinValue)
        {
            if (claim.CanadianDateInitialLower.Year < 1900 || claim.CanadianDateInitialLower >= DateTime.Today)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Date initial lower";
            }
             
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialLower, "N") && claim.CanadianDateInitialLower.Year < 1900)
        {
            //missing date
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Date initial lower";
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialLower, "N") && claim.CanadianMandProsthMaterial == 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Mand prosth material";
        }
         
        if (StringSupport.equals(claim.CanadianIsInitialLower, "X") && claim.CanadianMandProsthMaterial != 0)
        {
            if (!StringSupport.equals(retVal, ""))
                retVal += ", ";
             
            retVal += "Mand prosth material";
        }
         
        //missing teeth---------------------------------------------------------------------------------------------------
        /*Can't do this because extracted teeth count is allowed to be zero
        			if(claim.CanadianIsInitialLower=="Y" && claim.CanadianMandProsthMaterial!=7) {//initial lower, but not crown
        				if(extracted.Count==0) {
        					if(retVal!="")
        						retVal+=", ";
        					retVal+="Missing teeth not entered";
        				}
        			}
        			if(claim.CanadianIsInitialUpper=="Y" && claim.CanadianMaxProsthMaterial!=7) {//initial upper, but not crown
        				if(extracted.Count==0) {
        					if(retVal!="")
        						retVal+=", ";
        					retVal+="Missing teeth not entered";
        				}
        			}
        			*/
        if (claim.AccidentDate > DateTime.MinValue)
        {
            if (claim.AccidentDate.Year < 1900 || claim.AccidentDate > DateTime.Today)
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ",";
                 
                retVal += "Accident date";
            }
             
        }
         
        if (!billProv.IsCDAnet)
        {
            retVal += "Billing provider is not setup as a CDANet provider.";
        }
         
        if (!treatProv.IsCDAnet)
        {
            retVal += "Treating provider is not setup as a CDANet provider.";
        }
         
        for (int i = 0;i < claimProcsClaim.Count;i++)
        {
            proc = Procedures.GetProcFromList(procListAll, claimProcsClaim[i].ProcNum);
            procCode = ProcedureCodes.getProcCode(proc.CodeNum);
            //Procedure dates are not included in predetermination requests so we do not need to check the dates for claimtype 'PreAuth'.
            if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                if (claimProcsClaim[i].ProcDate.Year < 1970 || claimProcsClaim[i].ProcDate > DateTime.Today)
                {
                    if (!StringSupport.equals(retVal, ""))
                    {
                        retVal += ", ";
                    }
                     
                    retVal += "proc " + procCode.ProcCode + " procedure date";
                }
                 
            }
             
            if (procCode.TreatArea == TreatmentArea.Arch && StringSupport.equals(proc.Surf, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                {
                    retVal += ", ";
                }
                 
                retVal += "proc " + procCode.ProcCode + " missing arch";
            }
             
            if (procCode.TreatArea == TreatmentArea.ToothRange && StringSupport.equals(proc.ToothRange, ""))
            {
                if (!StringSupport.equals(retVal, ""))
                {
                    retVal += ", ";
                }
                 
                retVal += "proc " + procCode.ProcCode + " tooth range";
            }
             
            if ((procCode.TreatArea == TreatmentArea.Tooth || procCode.TreatArea == TreatmentArea.Surf) && !Tooth.isValidDB(proc.ToothNum))
            {
                if (!StringSupport.equals(retVal, ""))
                {
                    retVal += ", ";
                }
                 
                retVal += "proc " + procCode.ProcCode + " tooth number";
            }
             
            if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                List<Procedure> labFeesForProc = Procedures.GetCanadianLabFees(proc.ProcNum, procListAll);
                for (int j = 0;j < labFeesForProc.Count;j++)
                {
                    if (labFeesForProc[j].ProcStatus != ProcStat.C)
                    {
                        ProcedureCode procCodeLab = ProcedureCodes.GetProcCode(labFeesForProc[j].CodeNum);
                        if (!StringSupport.equals(retVal, ""))
                        {
                            retVal += ", ";
                        }
                         
                        retVal += "proc " + procCode.ProcCode + " lab fee " + procCodeLab.ProcCode + " not complete";
                    }
                     
                }
            }
             
        }
        for (int i = 0;i < extracted.Count;i++)
        {
            if (extracted[i].ProcDate.Date > DateTime.Today)
            {
                retVal += "extraction date in future";
            }
             
        }
        return retVal;
    }

    /**
    * Only call this function for a patPlan such that the carrier has an electid of 000064, which signified that it is for Pacific Blue Cross (PBC).
    */
    private static String getMissingDataForPatPlanPacificBlueCross(PatPlan patPlan, InsPlan insPlan) throws Exception {
        String retVal = "";
        String dependantCode = patPlan.PatID;
        //C17
        int dependantNum = -1;
        if (!StringSupport.equals(dependantCode, ""))
        {
            try
            {
                dependantNum = PIn.Int(dependantCode);
            }
            catch (Exception __dummyCatchVar3)
            {
            }
        
        }
         
        String relationshipCode = getRelationshipCode(patPlan.Relationship);
        //C03
        if (StringSupport.equals(relationshipCode, "1"))
        {
            //self
            if (!StringSupport.equals(dependantCode, "00"))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Dependant code must be 00 for Self with Pacific Blue Cross";
            }
             
        }
        else if (StringSupport.equals(relationshipCode, "2"))
        {
            //spouse
            if (!StringSupport.equals(dependantCode, "01") && !(dependantNum >= 90 && dependantNum <= 99))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Dependant code must be 01, or between 90 and 99 for Spouse with Pacific Blue Cross";
            }
             
        }
        else if (StringSupport.equals(relationshipCode, "3"))
        {
            //child
            if (dependantNum < 2 || dependantNum > 89 || dependantCode.Length != 2)
            {
                retVal += "Dependant code must be between 02 and 89 for Child with Pacific Blue Cross";
            }
             
        }
        else if (StringSupport.equals(relationshipCode, "4"))
        {
            //common law spouse
            if (!StringSupport.equals(dependantCode, "01") && !(dependantNum >= 90 && dependantNum <= 99))
            {
                if (!StringSupport.equals(retVal, ""))
                    retVal += ", ";
                 
                retVal += "Dependant code must be 01, or between 90 and 99 for Common-law with Pacific Blue Cross";
            }
             
        }
        else
        {
            retVal += "Relationship code must be Self, Spouse, Child, LifePartner, or SignifOther with Pacific Blue Cross";
        }    
        return retVal;
    }

}


