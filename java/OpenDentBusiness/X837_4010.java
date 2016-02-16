//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:14 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ElectID;
import OpenDentBusiness.ElectIDs;
import OpenDentBusiness.Employers;
import OpenDentBusiness.InsFilingCodes;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.ProviderIdent;
import OpenDentBusiness.ProviderIdents;
import OpenDentBusiness.Providers;
import OpenDentBusiness.ProviderSupplementalID;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.X12Generator;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X12TransactionItem;
import OpenDentBusiness.X12Validate;

/**
* 
*/
public class X837_4010  extends X12object 
{
    public X837_4010(String messageText) throws Exception {
        super(messageText);
    }

    public static void generateMessageText(StreamWriter sw, Clearinghouse clearhouse, int batchNum, List<ClaimSendQueueItem> functionalGroupDental) throws Exception {
        //Interchange Control Header (Interchange number tracked separately from transactionNum)
        //We set it to between 1 and 999 for simplicity
        //ISA01,ISA02: 00 + 10 spaces
        //ISA03,ISA04: 00 + 10 spaces
        //ISA05: Sender ID type: ZZ=mutually defined. 30=TIN. Validated
        //ISA06: Sender ID(TIN). Or might be TIN of Open Dental
        //ISA07: Receiver ID type: ZZ=mutually defined. 30=TIN. Validated
        //ISA08: Receiver ID. Validated to make sure length is at least 2.
        //ISA09: today's date
        //ISA10: current time
        //ISA11 and ISA12.
        //ISA13: interchange control number, right aligned:
        //ISA14: no acknowledgment requested
        sw.Write("ISA*00*          *" + "00*          *" + clearhouse.ISA05 + "*" + X12Generator.getISA06(clearhouse) + "*" + clearhouse.ISA07 + "*" + sout(clearhouse.ISA08,15,15) + "*" + DateTime.Today.ToString("yyMMdd") + "*" + DateTime.Now.ToString("HHmm") + "*" + "U*00401*" + batchNum.ToString().PadLeft(9, '0') + "*" + "0*" + clearhouse.ISA15 + "*");
        //ISA15: T=Test P=Production. Validated.
        sw.WriteLine(":~");
        //ISA16: use ':'
        //Functional groups: one for dental and one for medical
        //But we instead need to restrict file output to either medical OR dental, not both.
        //So this part is changing.  One or the other.
        //if(functionalGroupMedical.Count>0) {
        //	WriteFunctionalGroup(sw,functionalGroupMedical,batchNum,clearhouse);
        //}
        //if(functionalGroupDental.Count>0) {
        WriteFunctionalGroup(sw, functionalGroupDental, batchNum, clearhouse);
        //}
        //Interchange Control Trailer
        //IEA01: number of functional groups
        sw.WriteLine("IEA*1*" + batchNum.ToString().PadLeft(9, '0') + "~");
    }

    //IEA02: Interchange control number
    private static void writeFunctionalGroup(StreamWriter sw, List<ClaimSendQueueItem> queueItems, int batchNum, Clearinghouse clearhouse) throws Exception {
        int transactionNum = 1;
        //Gets incremented for each carrier. Can be reused in other functional groups and interchanges, so not persisted
        //Functional Group Header
        String groupControlNumber = batchNum.ToString();
        //Must be unique within file.  We will use batchNum
        boolean isMedical = false;
        //queueItems[0].IsMedical;
        //this needs to be changed.  Medical should not be sent with dental.
        /*if(isMedical) {
        				//groupControlNumber="2";//this works for now because only two groups
        				sw.WriteLine("GS*HC*"//GS01: Health Care Claim
        					+X12Generator.GetGS02(clearhouse)+"*"//GS02: Senders Code. Sometimes OpenDental.  Sometimes the sending clinic. Validated
        					+Sout(clearhouse.GS03,15,2)+"*"//GS03: Application Receiver's Code
        					+DateTime.Today.ToString("yyyyMMdd")+"*"//GS04: today's date
        					+DateTime.Now.ToString("HHmm")+"*"//GS05: current time
        					+groupControlNumber+"*"//GS06: Group control number. Max length 9. No padding necessary. 
        					+"X*"//GS07: X
        					+"005010X222~");//GS08: Version
        			}
        			else {//dental*/
        //groupControlNumber="1";
        //GS01: Health Care Claim
        //GS02: Senders Code. Sometimes Jordan Sparks.  Sometimes the sending clinic.
        //GS03: Application Receiver's Code
        //GS04: today's date
        //GS05: current time
        sw.WriteLine("GS*HC*" + X12Generator.getGS02(clearhouse) + "*" + sout(clearhouse.GS03,15,2) + "*" + DateTime.Today.ToString("yyyyMMdd") + "*" + DateTime.Now.ToString("HHmm") + "*" + groupControlNumber + "*" + "X*" + "004010X097A1~");
        //GS06: Group control number. Max length 9. No padding necessary.
        //GS07: X
        //GS08: Version
        //}
        //Gets an array with PayorID,ProvBill,Subscriber,PatNum,ClaimNum all in the correct order
        List<long> claimNums = new List<long>();
        for (int i = 0;i < queueItems.Count;i++)
        {
            claimNums.Add(queueItems[i].ClaimNum);
        }
        List<X12TransactionItem> claimItems = Claims.GetX12TransactionInfo(claimNums);
        boolean newTrans = new boolean();
        //true if this loop has transaction header
        boolean hasFooter = new boolean();
        //true if this loop has transaction footer(if the Next loop is a newTrans)
        int HLcount = 1;
        int parentProv = 0;
        //the HL sequence # of the current provider.
        int parentSubsc = 0;
        //the HL sequence # of the current subscriber.
        String hasSubord = "";
        //0 if no subordinate, 1 if at least one subordinate
        Claim claim;
        InsPlan insPlan;
        InsPlan otherPlan = new InsPlan();
        InsSub sub;
        InsSub otherSub = new InsSub();
        Patient patient;
        Patient subscriber;
        Patient otherSubsc = new Patient();
        Carrier carrier;
        Carrier otherCarrier = new Carrier();
        List<ClaimProc> claimProcList = new List<ClaimProc>();
        //all claimProcs for a patient.
        List<ClaimProc> claimProcs = new List<ClaimProc>();
        List<Procedure> procList = new List<Procedure>();
        List<ToothInitial> initialList = new List<ToothInitial>();
        List<PatPlan> patPlans = new List<PatPlan>();
        Procedure proc;
        ProcedureCode procCode;
        Provider provTreat;
        //might be different for each proc
        Provider billProv = null;
        Clinic clinic = null;
        boolean isSecondaryPreauth = false;
        int seg = 0;
        for (int i = 0;i < claimItems.Count;i++)
        {
            //segments for a particular ST-SE transaction
            //if this is the first claim
            if (i == 0 || claimItems[i].PayorId0 != claimItems[i - 1].PayorId0)
            {
                //or the payorID has changed
                newTrans = true;
                seg = 0;
            }
            else
                newTrans = false; 
            if (newTrans)
            {
                //Transaction Set Header (one for each carrier)
                //transactionNum gets incremented in SE section
                //ST02 Transact. control #. Must be unique within ISA
                //NO: So we used combination of transaction and group, eg 00011
                seg++;
                if (isMedical)
                {
                    //ST01
                    sw.WriteLine("ST*837*" + transactionNum.ToString().PadLeft(4, '0') + "*" + "005010X222~");
                }
                else
                {
                    //ST02 4/9
                    //ST03: Implementation convention reference
                    //dental
                    //ST01
                    sw.WriteLine("ST*837*" + transactionNum.ToString().PadLeft(4, '0') + "~");
                } 
                //ST02
                seg++;
                //BHT03. Can be same as ST02
                //BHT04: Date
                sw.WriteLine("BHT*0019*00*" + transactionNum.ToString().PadLeft(4, '0') + "*" + DateTime.Now.ToString("yyyyMMdd") + "*" + DateTime.Now.ToString("HHmmss") + "*" + "CH~");
                //BHT05: Time
                //BHT06: Type=Chargable
                if (!isMedical)
                {
                    seg++;
                    if (StringSupport.equals(clearhouse.ISA15, "T"))
                    {
                        //validated
                        sw.WriteLine("REF*87*004010X097DA1~");
                    }
                    else
                    {
                        sw.WriteLine("REF*87*004010X097A1~");
                    } 
                }
                 
                //1000A Submitter is OPEN DENTAL and sometimes it's the practice
                //(depends on clearinghouse and Partnership agreements)
                //See 2010AA PER (after REF) for the new billing provider contact phone number
                //1000A NM1: required
                seg++;
                write1000A_NM1(sw,clearhouse);
                //1000A PER: required. Contact number.
                seg++;
                //always one seg
                write1000A_PER(sw,clearhouse);
                //1000B Receiver is always the Clearinghouse
                //1000B NM1: required
                seg++;
                //NM101: 40=receiver
                //NM102: 2=nonperson
                //NM103:Receiver Name
                //NM104-NM107 not used since not a person
                //NM108: 46 indicates ETIN
                sw.WriteLine("NM1*40*" + "2*" + sout(clearhouse.Description,35,1) + "*" + "****" + "46*" + sout(clearhouse.ISA08,80,2) + "~");
                //NM109: Receiver ID Code. aka ETIN#.
                HLcount = 1;
                parentProv = 0;
                //the HL sequence # of the current provider.
                parentSubsc = 0;
                //the HL sequence # of the current subscriber.
                hasSubord = "";
            }
             
            //0 if no subordinate, 1 if at least one subordinate
            //HL Loops:
            //if first claim
            //or new Transaction set
            if (i == 0 || newTrans || claimItems[i].ProvBill1 != claimItems[i - 1].ProvBill1)
            {
                //or prov has changed
                //this is a workaround for finding clinic address.  In OD, address is not a provider level field.  All we have access to is the claim.
                //So providers shouldn't move between clinics.  We will use the clinic of the first claim, which is arbitrary.
                //An improvement would be to generate another loop if provider changes address for different claims.  Complicated.
                if (!PrefC.getBool(PrefName.EasyNoClinics))
                {
                    //if using clinics
                    Claim clm = Claims.GetClaim(claimItems[i].ClaimNum4);
                    long clinicNum = clm.ClinicNum;
                    clinic = Clinics.getClinic(clinicNum);
                }
                 
                //2000A HL: Billing/Pay-to provider HL loop
                seg++;
                sw.WriteLine("HL*" + HLcount.ToString() + "*" + "*" + "20*" + "1~");
                //HL01: Heirarchical ID
                //HL02: No parent. Not used
                //HL03: Heirarchical level code. 20=Information source
                //HL04: Heirarchical child code. 1=child HL present
                billProv = ProviderC.getListLong()[Providers.GetIndexLong(claimItems[i].ProvBill1)];
                if (isMedical)
                {
                    //2000A PRV: Provider Specialty Information
                    seg++;
                    //PRV01: Provider Code. BI=Billing
                    //PRV02: taxonomy code
                    sw.WriteLine("PRV*BI*" + "PXC*" + X12Generator.getTaxonomy(billProv) + "~");
                }
                else
                {
                    //PRV03: Provider taxonomy code
                    //dental
                    //2000A PRV: Provider Specialty Information (Optional Rendering prov for all claims in this HL)
                    //Not used when the Billing or Pay-to Provider (we do not support pay-to provider for 4010s) is a group (not a person) and the individual Rendering Provider is in loop 2310B.
                    if (!StringSupport.equals(billProv.FName, ""))
                    {
                        //We send the PRV segment when billing prov is a person. Loop 2310B is always used, so we do not need to check anything else.
                        seg++;
                        //PRV01: Provider Code. BI=Billing, PT=Pay-To
                        //PRV02: mutually defined taxonomy codes
                        sw.WriteLine("PRV*PT*" + "ZZ*" + X12Generator.getTaxonomy(billProv) + "~");
                    }
                     
                } 
                //PRV03: Provider taxonomy code
                //2010AA NM1: Billing provider
                seg++;
                sw.Write("NM1*85*");
                //NM101: 85=Billing provider
                if (StringSupport.equals(billProv.FName, ""))
                {
                    sw.Write("2*");
                }
                else
                {
                    //NM102: 1=person,2=non-person
                    sw.Write("1*");
                } 
                //NM103: Last name
                //NM103 allowable length increased to 60?
                //NM104: First name. Might be blank.
                sw.Write(sout(billProv.LName,35) + "*" + sout(billProv.FName,25) + "*" + sout(billProv.MI,25) + "*" + "*" + "*");
                //NM105: Middle name. Since this is optional, there is no min length.
                //NM106: not used
                //NM107: Name suffix. not used
                //It's after the NPI date now, so only one choice here:
                sw.Write("XX*");
                //NM108: ID code qualifier. 24=EIN. 34=SSN, XX=NPI
                sw.WriteLine(sout(billProv.NationalProvID,80) + "~");
                //NM109: ID code. NPI validated
                //2010AA N3: Billing provider address
                seg++;
                if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    sw.Write("N3*" + sout(PrefC.getString(PrefName.PracticeBillingAddress),55));
                }
                else //N301: Address
                if (clinic == null)
                {
                    sw.Write("N3*" + sout(PrefC.getString(PrefName.PracticeAddress),55));
                }
                else
                {
                    //N301: Address
                    sw.Write("N3*" + sout(clinic.Address,55));
                }  
                //N301: Address
                if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    if (StringSupport.equals(PrefC.getString(PrefName.PracticeBillingAddress2), ""))
                    {
                        sw.WriteLine("~");
                    }
                    else
                    {
                        //N302: Address2. Optional.
                        sw.WriteLine("*" + sout(PrefC.getString(PrefName.PracticeBillingAddress2),55) + "~");
                    } 
                }
                else if (clinic == null)
                {
                    if (StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                    {
                        sw.WriteLine("~");
                    }
                    else
                    {
                        //N302: Address2. Optional.
                        sw.WriteLine("*" + sout(PrefC.getString(PrefName.PracticeAddress2),55) + "~");
                    } 
                }
                else
                {
                    if (StringSupport.equals(clinic.Address2, ""))
                    {
                        sw.WriteLine("~");
                    }
                    else
                    {
                        //N302: Address2. Optional.
                        sw.WriteLine("*" + sout(clinic.Address2,55) + "~");
                    } 
                }  
                //2010AA N4: Billing prov City,State,Zip
                seg++;
                if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
                {
                    //N401: City
                    //N402: State
                    sw.WriteLine("N4*" + sout(PrefC.getString(PrefName.PracticeBillingCity),30) + "*" + sout(PrefC.getString(PrefName.PracticeBillingST),2) + "*" + Sout(PrefC.getString(PrefName.PracticeBillingZip).Replace("-", ""), 15) + "~");
                }
                else //N403: Zip
                if (clinic == null)
                {
                    //N401: City
                    //N402: State
                    sw.WriteLine("N4*" + sout(PrefC.getString(PrefName.PracticeCity),30) + "*" + sout(PrefC.getString(PrefName.PracticeST),2) + "*" + Sout(PrefC.getString(PrefName.PracticeZip).Replace("-", ""), 15) + "~");
                }
                else
                {
                    //N403: Zip
                    //N401: City
                    //N402: State
                    sw.WriteLine("N4*" + sout(clinic.City,30) + "*" + sout(clinic.State,2) + "*" + Sout(clinic.Zip.Replace("-", ""), 15) + "~");
                }  
                //N403: Zip
                if (!isMedical)
                {
                    //2010AA REF: Office phone number. Required by WebMD.  Can possibly be removed now that we're using NPI.
                    if (StringSupport.equals(clearhouse.ISA08, "0135WCH00"))
                    {
                        //if WebMD
                        seg++;
                        if (clinic == null)
                        {
                            sw.WriteLine("REF*LU*" + PrefC.getString(PrefName.PracticePhone) + "~");
                        }
                        else
                        {
                            sw.WriteLine("REF*LU*" + clinic.Phone + "~");
                        } 
                    }
                     
                    //2010AA REF: License #. Required by RECS clearinghouse,
                    //but everyone else should find it useful too.
                    if (!StringSupport.equals(billProv.StateLicense, ""))
                    {
                        seg++;
                        //REF01: 0B=state license #.
                        sw.WriteLine("REF*0B*" + sout(billProv.StateLicense,30) + "~");
                    }
                     
                    //2010AA REF: Secondary ID number(s). Only required by some carriers.
                    seg += WriteProv_REF(sw, billProv, claimItems[i].PayorId0);
                }
                 
                //It's after the NPI date, now.  So either TIN or SSN (EI or SY) is required here.
                seg++;
                sw.Write("REF*");
                if (billProv.UsingTIN)
                {
                    sw.Write("EI*");
                }
                else
                {
                    //REF01: qualifier. EI=EIN
                    //SSN
                    sw.Write("SY*");
                } 
                //REF01: qualifier. SY=SSN
                sw.WriteLine(sout(billProv.SSN,30) + "~");
                //REF02: ID #
                if (isMedical)
                {
                    //2010AA PER: Billing Provider Contact Info. Required if different than in 1000A
                    //We'll always include it for simplicity
                    seg++;
                    if (clinic == null)
                    {
                        //PER01: IC=Information Contact
                        //PER02:Name. Practice title
                        //PER03:Comm Number Qualifier: TE=Telephone
                        sw.WriteLine("PER*IC*" + sout(PrefC.getString(PrefName.PracticeTitle),60,1) + "*" + "TE*" + sout(PrefC.getString(PrefName.PracticePhone),256,1) + "~");
                    }
                    else
                    {
                        //PER04:Comm Number. aka telephone number
                        //PER01: IC=Information Contact
                        //PER02:Name. Practice title
                        //PER03:Comm Number Qualifier: TE=Telephone
                        sw.WriteLine("PER*IC*" + sout(PrefC.getString(PrefName.PracticeTitle),60,1) + "*" + "TE*" + sout(clinic.Phone,256,1) + "~");
                    } 
                }
                 
                //PER04:Comm Number. aka telephone number
                parentProv = HLcount;
                HLcount++;
            }
             
            claim = Claims.GetClaim(claimItems[i].ClaimNum4);
            insPlan = InsPlans.GetPlan(claim.PlanNum, new List<InsPlan>());
            sub = InsSubs.getSub(claim.InsSubNum,null);
            //insPlan could be null if db corruption. No error checking for that
            if (claim.PlanNum2 > 0)
            {
                otherPlan = InsPlans.GetPlan(claim.PlanNum2, new List<InsPlan>());
                otherSub = InsSubs.getSub(claim.InsSubNum2,null);
                otherSubsc = Patients.getPat(otherSub.Subscriber);
                otherCarrier = Carriers.getCarrier(otherPlan.CarrierNum);
            }
             
            patient = Patients.getPat(claim.PatNum);
            subscriber = Patients.getPat(sub.Subscriber);
            carrier = Carriers.getCarrier(insPlan.CarrierNum);
            claimProcList = ClaimProcs.refresh(patient.PatNum);
            claimProcs = ClaimProcs.GetForSendClaim(claimProcList, claim.ClaimNum);
            procList = Procedures.refresh(claim.PatNum);
            initialList = ToothInitials.refresh(claim.PatNum);
            patPlans = PatPlans.refresh(patient.PatNum);
            /*if(clearhouse.ISA08=="113504607" && claim.Attachments.Count>0){//If Tesia and has attachments
            					claim.ClaimNote="TESIA#"+claim.ClaimNum.ToString()+" "+claim.ClaimNote;
            					string saveFolder=clearhouse.ExportPath;//we've already tested to make sure this exists.
            					string saveFile;
            					string storedFile;
            					for(int c=0;c<claim.Attachments.Count;c++){
            						saveFile=ODFileUtils.CombinePaths(saveFolder,
            							claim.ClaimNum.ToString()+"_"+(c+1).ToString()+Path.GetExtension(claim.Attachments[c].DisplayedFileName)+".IMG");
            						storedFile=ODFileUtils.CombinePaths(FormEmailMessageEdit.GetAttachPath(),claim.Attachments[c].ActualFileName);
            						File.Clone(storedFile,saveFile,true);
            					}					
            				}*/
            //if patient changed
            if (i == 0 || newTrans || claimItems[i].PatNum3 != claimItems[i - 1].PatNum3 || claimItems[i].ProvBill1 != claimItems[i - 1].ProvBill1)
            {
                //or prov has changed
                if (claimItems[i].PatNum3 == claimItems[i].Subscriber2)
                {
                    //if patient is the subscriber
                    hasSubord = "0";
                }
                else
                {
                    //-claim level will follow
                    //subordinate patients will not follow in this loop.  The subscriber loop will be duplicated for them.
                    //patient is not the subscriber
                    hasSubord = "1";
                } 
                //-patient will always follow
                //2000B HL: Subscriber HL loop
                seg++;
                //HL01: Heirarchical ID
                //HL02: parent is always the provider HL
                //HL03: 22=Subscriber
                sw.WriteLine("HL*" + HLcount.ToString() + "*" + parentProv.ToString() + "*" + "22*" + hasSubord + "~");
                //HL04: 1=additional subordinate HL segment (patient). 0=no subord
                //2000B SBR:
                seg++;
                sw.Write("SBR*");
                if (StringSupport.equals(claim.ClaimType, "PreAuth"))
                {
                    if (PatPlans.GetOrdinal(claim.InsSubNum, patPlans) == 2 && claim.PlanNum2 != 0)
                    {
                        isSecondaryPreauth = true;
                        sw.Write("S*");
                    }
                    else
                    {
                        sw.Write("P*");
                    } 
                }
                else if (StringSupport.equals(claim.ClaimType, "P"))
                {
                    sw.Write("P*");
                }
                else //SBR01: Payer responsibility code
                if (StringSupport.equals(claim.ClaimType, "S"))
                {
                    sw.Write("S*");
                }
                else
                {
                    sw.Write("T*");
                }   
                //T=Tertiary
                //todo: what about Cap?
                if (claimItems[i].PatNum3 == claimItems[i].Subscriber2)
                {
                    //if patient is the subscriber
                    sw.Write("18*");
                }
                else
                {
                    //SBR02: Relationship. 18=self
                    sw.Write("*");
                } 
                //empty if patient is not subscriber.
                //SBR03: Group Number
                sw.Write(sout(insPlan.GroupNum,30) + "*" + sout(insPlan.GroupName,60) + "*" + "*");
                //SBR04: Group Name
                //SBR05: Not used
                if (isMedical)
                {
                    sw.Write("*");
                }
                else
                {
                    //SBR06 not used.
                    if (claim.PlanNum2 > 0)
                    {
                        sw.Write("1*");
                    }
                    else
                    {
                        //SBR06: 1=Coordination of benefits. 6=No coordination
                        sw.Write("6*");
                    } 
                } 
                sw.Write("**");
                //SBR07 & 08 not used.
                sw.WriteLine(getFilingCode(insPlan) + "~");
                //"CI~");//SBR09: 12=PPO,17=DMO,BL=BCBS,CI=CommercialIns,FI=FEP,HM=HMO
                //,MC=Medicaid,SA=self-administered, etc.
                //SBR09 will not be used once PlanID is mandated.
                //if(isMedical){
                //2000B PAT. Required when patient is subscriber and one of the fields is needed.
                //We will never need these fields: deceased date, weight, or pregnancy
                //}
                //2010BA NM1: Subscriber Name
                seg++;
                //NM101: IL=Insured or Subscriber
                //NM102: 1=Person
                //NM103: LName. Never blank, because validated in the patient edit window when a patient is added/edited.
                //NM104: FName
                //NM105: MiddleName
                //NM106: not used
                //NM107: suffix. Not present in Open Dental yet.
                //NM108: MI=MemberID
                sw.WriteLine("NM1*IL*" + "1*" + sout(subscriber.LName,35) + "*" + sout(subscriber.FName,25) + "*" + sout(subscriber.MiddleI,25) + "*" + "*" + "*" + "MI*" + Sout(sub.SubscriberID.Replace("-", ""), 80) + "~");
                //NM109: Subscriber ID
                //At the request of WebMD, we are including N3,N4,and DMG even if patient is not subscriber.
                //This does not make the transaction non-compliant, and they find it useful.
                //if(claimAr[3,i].ToString()==claimAr[2,i].ToString()){//if patient is the subscriber
                //2010BA N3: Subscriber Address. Only if patient is the subscriber
                seg++;
                sw.Write("N3*" + sout(subscriber.Address,55,1));
                //N301: address
                if (StringSupport.equals(subscriber.Address2, ""))
                {
                    sw.WriteLine("~");
                }
                else
                {
                    //N302: Address2. Optional.
                    sw.WriteLine("*" + sout(subscriber.Address2,55) + "~");
                } 
                //2010BA N4: CityStZip. Only if patient is the subscriber
                seg++;
                //N401: City
                //N402: State
                sw.WriteLine("N4*" + sout(subscriber.City,30,2) + "*" + sout(subscriber.State,2,2) + "*" + Sout(subscriber.Zip.Replace("-", ""), 15, 3) + "~");
                //N403: Zip
                //2010BA DMG: Subscr. Demographics. Only if patient is the subscriber
                seg++;
                if (subscriber.Birthdate.Year < 1900)
                {
                    //DMG01: use D8
                    //DMG02: birthdate.
                    //We probably do this because users complained that they didn't have access to the subsriber dob.  Nobody has ever complained about this.
                    sw.WriteLine("DMG*D8*" + subscriber.Birthdate.ToString("19000101") + "*" + getGender(subscriber.Gender) + "~");
                }
                else
                {
                    //DMG03: gender. F,M,or U
                    //DMG01: use D8
                    //DMG02: birthdate
                    sw.WriteLine("DMG*D8*" + subscriber.Birthdate.ToString("yyyyMMdd") + "*" + getGender(subscriber.Gender) + "~");
                } 
                //DMG03: gender. F,M,or U
                //}//if provider is the subscriber
                //2010BA REF: Secondary ID. Situational. Not used.
                //2010BA REF: Casualty Claim number. Not used.
                //Medical: 2010BA PER: Property and casualty subscriber contact info. Not used
                //2010BB: PayerName
                //2010BB NM1: Name
                seg++;
                sw.Write("NM1*PR*" + "2*");
                //NM101: PR=Payer
                //NM102: 2=Non person
                if (StringSupport.equals(clearhouse.ISA08, "EMS"))
                {
                    //This is a special situation requested by EMS.  This tacks the employer onto the end of the carrier.
                    sw.Write(sout(carrier.CarrierName,17) + "|" + sout(Employers.getName(insPlan.EmployerNum),17) + "*");
                }
                else
                {
                    sw.Write(sout(carrier.CarrierName,35) + "*");
                } 
                //NM103: Name. Length can be 60 in the new medical specs.
                sw.Write("****" + "PI*");
                //NM104-07 not used
                //NM108: PI=PayorID
                sw.WriteLine(sout(getCarrierElectID(carrier,clearhouse),80,2) + "~");
                //NM109: PayorID
                //2010BB N3: Carrier Address
                seg++;
                sw.Write("N3*" + sout(carrier.Address,55));
                //N301: address
                if (StringSupport.equals(carrier.Address2, ""))
                {
                    sw.WriteLine("~");
                }
                else
                {
                    //N302: Address2. Optional.
                    sw.WriteLine("*" + sout(carrier.Address2,55) + "~");
                } 
                //2010BB N4: Carrier City,St,Zip
                seg++;
                //N401: City
                //N402: State
                sw.WriteLine("N4*" + sout(carrier.City,30,2) + "*" + sout(carrier.State,2,2) + "*" + Sout(carrier.Zip.Replace("-", ""), 15, 3) + "~");
                //N403: Zip
                //2010BB Ref: Payer secondary ID. Not used
                //Credit card info. Not used.
                parentSubsc = HLcount;
                HLcount++;
            }
             
            //if((i==0 || claimAr[3,i].ToString() != claimAr[3,i-1].ToString())//if patient changed
            //	&& claimAr[3,i].ToString() != claimAr[2,i].ToString())//AND patient is not subscriber
            //{
            if (claimItems[i].PatNum3 != claimItems[i].Subscriber2)
            {
                //if patient is not subscriber
                //2000C Patient HL loop
                seg++;
                //HL01:Heirarchical ID
                sw.WriteLine("HL*" + HLcount.ToString() + "*" + parentSubsc.ToString() + "*" + "23*" + "0~");
                //HL02: parent is always the subscriber HL
                //HL03: 23=Dependent
                //HL04: never a subordinate
                //2000C PAT
                seg++;
                if (isMedical)
                {
                    sw.WriteLine("PAT*" + getRelat(claim.PatRelat) + "~");
                }
                else
                {
                    //PAT01: Relat
                    //PAT04 not used, so no further lines needed.
                    //PAT01: Relat
                    //PAT02 & 03 not used
                    sw.WriteLine("PAT*" + getRelat(claim.PatRelat) + "*" + "**" + getStudent(patient.StudentStatus) + "~");
                } 
                //PAT04: Student status code: N,P,or F
                //2010CA NM1: Patient Name
                seg++;
                //NM101: QC=Patient
                //NM102: 1=Person
                //NM103: Lname. Never blank, because validated in the patient edit window when a patient is added/edited.
                sw.Write("NM1*QC*" + "1*" + sout(patient.LName,35) + "*" + sout(patient.FName,25));
                //NM104: Fname
                String patID = patient.SSN;
                for (int p = 0;p < patPlans.Count;p++)
                {
                    if (patPlans[p].InsSubNum == claim.InsSubNum)
                    {
                        patID = patPlans[p].PatID.Replace("-", "");
                    }
                     
                }
                if (isMedical)
                {
                    if (StringSupport.equals(patient.MiddleI, ""))
                    {
                        sw.WriteLine("~");
                    }
                    else
                    {
                        sw.WriteLine("*" + sout(patient.MiddleI,25) + "~");
                    } 
                }
                else
                {
                    //NM105: Mid name
                    //NM106: prefix not used. NM107: No suffix field in Open Dental
                    //NM108-NM112 no longer allowed to be used.
                    //instead of including a patID here, the patient should get their own subsriber loop.
                    if (StringSupport.equals(patID, ""))
                    {
                        if (StringSupport.equals(patient.MiddleI, ""))
                        {
                            sw.WriteLine("~");
                        }
                        else
                        {
                            sw.WriteLine("*" + sout(patient.MiddleI,25) + "~");
                        } 
                    }
                    else
                    {
                        //NM105: Mid name
                        //id not blank
                        //NM105: Mid name (whether or not empty)
                        //NM106: prefix not used. NM107: No suffix field in Open Dental
                        //NM108: MI=Member ID
                        sw.WriteLine("*" + sout(patient.MiddleI,25) + "*" + "**" + "MI*" + sout(patID,80) + "~");
                    } 
                } 
                //NM109: Patient ID
                //2010CA N3: Patient address
                seg++;
                sw.Write("N3*" + sout(patient.Address,55));
                //N301: address
                if (StringSupport.equals(patient.Address2, ""))
                {
                    sw.WriteLine("~");
                }
                else
                {
                    //N302: Address2. Optional.
                    sw.WriteLine("*" + sout(patient.Address2,55) + "~");
                } 
                //2010CA N4: City State Zip
                seg++;
                //N401: City
                //N402: State
                sw.WriteLine("N4*" + sout(patient.City,30) + "*" + sout(patient.State,2) + "*" + Sout(patient.Zip.Replace("-", ""), 15) + "~");
                //N403: Zip
                //2010CA DMG: Patient demographic
                seg++;
                //DMG01: use D8
                //DMG02: Birthdate
                sw.WriteLine("DMG*D8*" + patient.Birthdate.ToString("yyyyMMdd") + "*" + getGender(patient.Gender) + "~");
                //DMG03: gender
                //2010CA REF: Property and casualty claim number.
                //2010CA PER: Property and casualty patient contact info
                HLcount++;
            }
             
            //2300 CLM: Claim
            //the next 8 lines fix a rare bug where the total doesn't match the sum of the procs.  This would result in invalid X12
            double claimFeeBilled = 0;
            for (int j = 0;j < claimProcs.Count;j++)
            {
                claimFeeBilled += (double)claimProcs[j].FeeBilled;
            }
            if (claimFeeBilled != (double)claim.ClaimFee)
            {
                claim.ClaimFee = (double)claimFeeBilled;
                Claims.update(claim);
            }
             
            seg++;
            //CLM01: A unique id. Can support 20 char.  By using both PatNum and ClaimNum, it is possible to search for a patient as well as to ensure uniqueness. We might need to allow user to override for claims based on preauths.
            //CLM02: Claim Fee
            //CLM03 & 04 not used
            sw.Write("CLM*" + Sout(claim.PatNum.ToString() + "/" + claim.ClaimNum.ToString(), 20) + "*" + claimFeeBilled.ToString("f2") + "*" + "**" + getPlaceService(claim.PlaceService) + "::1*" + "Y*" + "A*");
            //CLM05: place+1. 1=Original claim
            //CLM06: prov sig on file (always yes)
            //CLM07: prov accepts medicaid assignment. OD has no field for this, so no choice
            if (sub.AssignBen)
            {
                sw.Write("Y*");
            }
            else
            {
                //CLM08: assign ben. Y or N
                sw.Write("N*");
            } 
            //if(insPlan.ReleaseInfo){
            sw.Write("Y");
            //CLM09: release info. Y or I(which we don't support)
            //}
            //else{
            //	sw.Write("N");//this is not allowed and is now blocked way ahead of time.
            //}
            if (!isMedical && StringSupport.equals(claim.ClaimType, "PreAuth"))
            {
                //* for CLM09. CLM10 not used
                sw.WriteLine("**" + getRelatedCauses(claim) + "*" + "*" + "******" + "PB~");
            }
            else
            {
                //CLM11: Accident related, including state. Might be blank.
                //CLM12: special programs like EPSTD
                //CLM13-18 not used
                //CLM19 PB=Predetermination of Benefits. Not allowed in medical claims. What is the replacement??
                if (StringSupport.equals(getRelatedCauses(claim), ""))
                {
                    sw.WriteLine("~");
                }
                else
                {
                    //* for CLM09. CLM10 not used
                    sw.WriteLine("**" + getRelatedCauses(claim) + "~");
                } 
            } 
            //CLM11: Accident related, including state
            //CLM20: delay reason code
            //2300 DTP: Date of onset of current illness (medical)
            //2300 DTP: Initial treatment date (spinal manipulation) (medical)
            //2300 DTP: Date last seen (foot care) (medical)
            //2300 DTP: Date accute manifestation (spinal manipulation) (medical)
            //2300 DTP: Date referral
            //2300 DTP: Date accident
            if (claim.AccidentDate.Year > 1880)
            {
                seg++;
                //DTP01: 439=accident
                //DTP02: use D8
                sw.WriteLine("DTP*439*" + "D8*" + claim.AccidentDate.ToString("yyyyMMdd") + "~");
            }
             
            //2300 DTP: (a bunch more useless medical dates)
            if (!isMedical)
            {
                //2300 DTP: Date ortho appliance placed
                if (claim.OrthoDate.Year > 1880)
                {
                    seg++;
                    //DTP01: 452=Appliance placement
                    //DTP02: use D8
                    sw.WriteLine("DTP*452*" + "D8*" + claim.OrthoDate.ToString("yyyyMMdd") + "~");
                }
                 
                //2300 DTP: Date of service for claim. Not used if predeterm
                if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
                {
                    if (claim.DateService.Year > 1880)
                    {
                        seg++;
                        //DTP01: 472=Service
                        //DTP02: use D8
                        sw.WriteLine("DTP*472*" + "D8*" + claim.DateService.ToString("yyyyMMdd") + "~");
                    }
                     
                }
                 
                //2300 DN1: Months of ortho service
                if (claim.IsOrtho)
                {
                    seg++;
                    //DN101 . Orthodontic total months of treatment. Not used because no field yet in OD.
                    sw.WriteLine("DN1*" + "*" + claim.OrthoRemainM.ToString() + "~");
                }
                 
                //2300 DN2: Missing teeth
                List<String> missingTeeth = ToothInitials.GetMissingOrHiddenTeeth(initialList);
                boolean doSkip = new boolean();
                int countMissing = 0;
                for (int j = 0;j < missingTeeth.Count;j++)
                {
                    //if the missing tooth is missing because of an extraction being billed here, then exclude it
                    //still needed, even though missing teeth are not based on procedures any longer
                    doSkip = false;
                    for (int p = 0;p < claimProcs.Count;p++)
                    {
                        proc = Procedures.GetProcFromList(procList, claimProcs[p].ProcNum);
                        procCode = ProcedureCodes.getProcCode(proc.CodeNum);
                        if (procCode.PaintType == ToothPaintingType.Extraction && StringSupport.equals(proc.ToothNum, missingTeeth[j]))
                        {
                            doSkip = true;
                            break;
                        }
                         
                    }
                    if (doSkip)
                    {
                        continue;
                    }
                     
                    countMissing++;
                    if (countMissing > 35)
                    {
                        continue;
                    }
                     
                    //segment max use 35
                    seg++;
                    sw.WriteLine("DN2*" + missingTeeth[j] + "*" + "M~");
                }
            }
             
            //DN201: tooth number
            //DN202: M=Missing, I=Impacted, E=To be extracted
            //2300 PWK: Paperwork. Used to identify attachments.
            /*if(clearhouse.ISA08=="113504607" && claim.Attachments.Count>0) {//If Tesia and has attachments
            					seg++;
            					sw.WriteLine("PWK*"
            						+"OZ*"//PWK01: ReportTypeCode. OZ=Support data for claim.
            						+"EL*"//PWK02: Report Transmission Code. EL=Electronic
            						+"**"//PWK03 and 04: not used
            						+"AC*"//PWK05: Identification Code Qualifier. AC=Attachment Control Number
            						+"TES"+claim.ClaimNum.ToString()+"~");//PWK06: Identification Code.
            				}*/
            //No validation is done.  However, warnings are displayed if:
            //Warning if attachments are listed as Mail even though we are sending electronically.
            //Warning if any PWK segments are needed, and there is no ID code.
            //PWK can repeat max 10 times.
            String pwk02 = new String();
            String idCode = claim.AttachmentID;
            if (StringSupport.equals(idCode, ""))
            {
                //must be min of two char, so we need to make one up.
                idCode = "  ";
            }
             
            idCode = sout(idCode,80,2);
            if (claim.AttachedFlags.Contains("Mail"))
            {
                pwk02 = "BM";
            }
            else
            {
                //By Mail
                pwk02 = "EL";
            } 
            //Elect
            if (claim.AttachedFlags.Contains("EoB"))
            {
                seg++;
                //PWK01: ReportTypeCode. EB=EoB.
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "EB*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            if (claim.AttachedFlags.Contains("Note"))
            {
                seg++;
                //PWK01: ReportTypeCode. OB=Operative Note.
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "OB*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            if (claim.AttachedFlags.Contains("Perio"))
            {
                seg++;
                //PWK01: ReportTypeCode. P6=Perio
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "P6*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            if (claim.AttachedFlags.Contains("Misc") || claim.AttachedImages > 0)
            {
                seg++;
                //PWK01: ReportTypeCode. OZ=Support Data For Claim.
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "OZ*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            if (claim.Radiographs > 0)
            {
                seg++;
                //PWK01: ReportTypeCode. RB=Radiology Films.
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "RB*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            if (claim.AttachedModels > 0)
            {
                seg++;
                //PWK01: ReportTypeCode. DA=Dental Models.
                //PWK02: Report Transmission Code. EL=Electronic, BM=By Mail
                //PWK03 and 04: not used
                //PWK05: Identification Code Qualifier. AC=Attachment Control Number
                sw.WriteLine("PWK*" + "DA*" + pwk02 + "*" + "**" + "AC*" + idCode + "~");
            }
             
            //PWK06: Identification Code.
            //2300 CN1: Contract Info (medical)
            //2300 AMT: Patient amount paid
            //2300 AMT: Total Purchased Service Amt (medical)
            //2300 REF: (A bunch of ref segments for medical which we don't need)
            //2300 REF: Predetermination ID
            if (!StringSupport.equals(claim.PreAuthString, ""))
            {
                seg++;
                //note for REF01: G1 replaced G3 in 837_A specs, including 837pro.
                //REF01: G1=prior auth number
                sw.WriteLine("REF*G1*" + sout(claim.PreAuthString,30) + "~");
            }
             
            //REF02: Prior Auth Identifier
            //2300 REF: Original Reference Number
            //aka Original Document Control Number/Internal Control Number (DCN/ICN).
            //aka Transaction Control Number (TCN).
            //aka Claim Reference Number.
            //Seems to be required by Medicaid when voiding a claim or resubmitting a claim by setting the CLM05-3.
            //todo: Implement
            //2300 REF: Referral number
            if (!StringSupport.equals(claim.RefNumString, ""))
            {
                seg++;
                //REF01: 9F=Referral number. Ok for medical, too.
                sw.WriteLine("REF*9F*" + sout(claim.RefNumString,30) + "~");
            }
             
            //2300 K3: File info (medical). Not used.
            //2300 NTE: Note
            String note = "";
            if (!StringSupport.equals(claim.AttachmentID, "") && !claim.ClaimNote.StartsWith(claim.AttachmentID))
            {
                note = claim.AttachmentID + " ";
            }
             
            note += claim.ClaimNote;
            if (!StringSupport.equals(note, ""))
            {
                seg++;
                //NTE01: ADD=Additional infor
                sw.WriteLine("NTE*ADD*" + sout(note,80) + "~");
            }
             
            //2300 CR1: (medical)Ambulance transport info
            //2300 CR2: (medical) Spinal Manipulation Service Info
            //2300 CRC: (medical) About 3 irrelevant segments
            ArrayList diagnoses = new ArrayList();
            //princDiag will always be the first element.
            if (isMedical)
            {
                for (int j = 0;j < claimProcs.Count;j++)
                {
                    proc = Procedures.GetProcFromList(procList, claimProcs[j].ProcNum);
                    if (StringSupport.equals(proc.DiagnosticCode, ""))
                    {
                        continue;
                    }
                     
                    if (proc.IsPrincDiag)
                    {
                        if (diagnoses.Contains(proc.DiagnosticCode))
                        {
                            diagnoses.Remove(proc.DiagnosticCode);
                        }
                         
                        diagnoses.Insert(0, proc.DiagnosticCode);
                    }
                    else
                    {
                        //princDiag always goes first. There will always be one.
                        //not princDiag
                        if (!diagnoses.Contains(proc.DiagnosticCode))
                        {
                            diagnoses.Add(proc.DiagnosticCode);
                        }
                         
                    } 
                }
                //2300 HI: (medical) Health Care Diagnosis Code. Required
                seg++;
                //HI01-1: BK=ICD-9 Principal Diagnosis
                sw.Write("HI*" + "BK:" + sout((String)diagnoses[0],30).Replace(".", ""));
                for (int j = 1;j < diagnoses.Count;j++)
                {
                    //HI01-2: Diagnosis code. No periods.
                    if (j > 11)
                    {
                        continue;
                    }
                     
                    //maximum of 12 diagnoses
                    //this is the * from the _previous_ field.
                    //HI0#-1: BF=ICD-9 Diagnosis
                    sw.Write("*" + "BF:" + sout((String)diagnoses[j],30).Replace(".", ""));
                }
                //HI0#-2: Diagnosis code. No periods.
                sw.WriteLine("~");
            }
             
            //2300 HI: (medical) Anesthesia related procedure
            //2300 HI: (medical) Condition information
            //2300 HCP: (medical) (not used) Claim Pricing/Repricing Info
            //2310A Referring provider. We don't use.
            //2310B Rendering provider. Only required if different from the billing provider
            //But required by WebClaim, so we will always include it
            provTreat = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvTreat)];
            //if(claim.ProvTreat!=claim.ProvBill){
            //2310B NM1: name
            seg++;
            //82=rendering prov
            //NM102: 1=person
            //NM103: LName
            //NM104: FName
            sw.Write("NM1*82*" + "1*" + sout(provTreat.LName,35) + "*" + sout(provTreat.FName,25) + "*" + sout(provTreat.MI,25) + "*" + "*" + "*");
            //NM105: MiddleName
            //NM106: not used
            //NM107: suffix. We don't support
            //It's after the NPI date, now, so always send NPI here:
            sw.Write("XX*");
            //NM108: ID code qualifier. 24=EIN. 34=SSN, XX=NPI
            sw.WriteLine(sout(provTreat.NationalProvID,80) + "~");
            //NM109: ID code.  NPI validated.
            //2310B PRV: Rendering provider information
            if (isMedical)
            {
                seg++;
                //PRV01: PE=Performing
                //PRV02: PXC=Health Care Provider Taxonomy Code
                sw.WriteLine("PRV*" + "PE*" + "PXC*" + X12Generator.getTaxonomy(provTreat) + "~");
            }
            else
            {
                //PRV03: Taxonomy code
                //dental
                seg++;
                //PRV01: PE=Performing
                //PRV02: ZZ=mutually defined taxonomy code
                sw.WriteLine("PRV*" + "PE*" + "ZZ*" + X12Generator.getTaxonomy(provTreat) + "~");
            } 
            //PRV03: Taxonomy code
            //2310B REF: Rendering provider secondary ID
            //All of these will be eliminated when NPI is mandated.
            //isMedical, only allowed types are 0B,1G,G2,and LU.
            //2310B REF: Rendering provider secondary ID.
            seg++;
            //REF01: 0B=state license #
            sw.WriteLine("REF*0B*" + sout(provTreat.StateLicense,30) + "~");
            if (!isMedical)
            {
                //we can't support these numbers very well yet for medical
                //2310B REF: Rendering Provider Secondary ID number(s). Only required by some carriers.
                seg += WriteProv_REF(sw, provTreat, claimItems[i].PayorId0);
            }
             
            //2310C (medical)Purchased Service provider secondary ID. We don't support this for medical
            //2310C (not medical)NM1: Service facility location.  Only required if PlaceService is 21,22,31, or 35. 35 does not exist in CPT, so we assume 33
            //if(!isMedical &&
            if (claim.PlaceService == PlaceOfService.InpatHospital || claim.PlaceService == PlaceOfService.OutpatHospital || claim.PlaceService == PlaceOfService.SkilledNursFac || claim.PlaceService == PlaceOfService.CustodialCareFacility)
            {
                //AdultLivCareFac
                seg++;
                //FA=Facility
                //NM102: 2=non-person
                //NM103:Facility Name
                //NM104: not used
                //NM105: not used
                //NM106: not used
                //NM107: not used
                //NM108: XX=NPI
                sw.WriteLine("NM1*FA*" + "2*" + sout(billProv.LName,35) + "*" + "*" + "*" + "*" + "*" + "XX*" + sout(billProv.NationalProvID,80) + "~");
            }
             
            //NM109: NPI. Validated.
            //or 2310D (medical)NM1: Service facility location. Required if different from 2010AA. Not supported.
            //2310D (medical)N3,N4,REF,PER: not supported.
            //2310E (medical)NM1,REF Supervising Provider. Not supported.
            //2310F (medical)NM1,N3,N4 Ambulance Pickup location. Not supported.
            //2310G (medical)NM1,N3,N4 Ambulance Dropoff location. Not supported.
            //2320 Other subscriber
            if (claim.PlanNum2 > 0)
            {
                seg++;
                sw.Write("SBR*");
                if (StringSupport.equals(claim.ClaimType, "PreAuth"))
                {
                    if (isSecondaryPreauth)
                    {
                        sw.Write("P*");
                    }
                    else
                    {
                        sw.Write("S*");
                    } 
                }
                else if (StringSupport.equals(claim.ClaimType, "S"))
                {
                    sw.Write("P*");
                }
                else //SBR01: Payer responsibility code
                if (StringSupport.equals(claim.ClaimType, "P"))
                {
                    sw.Write("S*");
                }
                else
                {
                    sw.Write("T*");
                }   
                //T=Tertiary
                sw.Write(getRelat(claim.PatRelat2) + "*");
                //SBR02: Individual Relationship Code
                //SBR03: Group Number
                sw.Write(sout(otherPlan.GroupNum,30) + "*" + sout(otherPlan.GroupName,60) + "*" + "*" + "*");
                //SBR04: Group Name
                //SBR05: Dental: Not used, Medical: not supported
                //SBR06: Not used
                sw.Write("**");
                //SBR07 & 08 not used.
                sw.WriteLine("CI~");
                //SBR09: 12=PPO,17=DMO,BL=BCBS,CI=CommercialIns,FI=FEP,HM=HMO
                //,MC=Medicaid,SA=self-administered, etc. There are too many. I'm just going
                //to use CI for everyone. I don't think anyone will care.
                //After mandated National Plan ID, then not used anymore.
                //2320 CAS: Claim Level Adjustments. Not supported.
                //2320 AMT: COB Payer paid amount
                if (!StringSupport.equals(claim.ClaimType, "P"))
                {
                    double paidOtherIns = 0;
                    for (int j = 0;j < claimProcs.Count;j++)
                    {
                        paidOtherIns += ClaimProcs.ProcInsPayPri(claimProcList, claimProcs[j].ProcNum, claimProcs[j].InsSubNum);
                    }
                    seg++;
                    //AMT01: D=Payor amount paid
                    sw.WriteLine("AMT*D*" + paidOtherIns.ToString("F") + "~");
                }
                 
                //AMT02: Amount
                //2320 AMT: (medical) COB Total Non-Covered Amt (A8)
                //2320 AMT: (medical) Remaining Patient liability (EAF)
                //2320 AMT: COB Approved Amt (AAE)
                //2320 AMT: COB Allowed Amt (B6)
                //2320 AMT: COB Patient Responsibility (F2)
                //2320 AMT: COB Coverage Amt (AU)
                //2320 AMT: COB Discount Amt (D8)
                //2320 AMT: COB Patient Paid Amt (F5)
                //2320 DMG: Other subscriber demographics
                seg++;
                //DMG01: use D8
                //DMG02: Birthdate
                sw.WriteLine("DMG*D8*" + otherSubsc.Birthdate.ToString("yyyyMMdd") + "*" + getGender(otherSubsc.Gender) + "~");
                //DMG03: gender
                //2320 OI: Other ins info
                seg++;
                sw.Write("OI***");
                //OI01 & 02 not used
                if (otherSub.AssignBen)
                {
                    sw.Write("Y***");
                }
                else
                {
                    //OI03: assign ben. Y or N. OI04(medical): we don't support
                    sw.Write("N***");
                } 
                //if(otherPlan.ReleaseInfo){
                sw.WriteLine("Y~");
                //OI06: release info. Y or I(which we don't support)
                //}
                //else{
                //	sw.WriteLine("N~");
                //}
                //2320 MOA: (medical) We don't support
                //2330A NM1: Other subscriber name
                seg++;
                //NM010: IL=insured
                //NM102: 1=person
                //NM103: LName. Never blank, because validated in the patient edit window when a patient is added/edited.
                //NM104: FName
                //NM105: MiddleName
                //NM106: not used
                //NM107: suffix. No corresponding field in OD
                //NM108: MI=Member ID
                sw.WriteLine("NM1*IL*" + "1*" + sout(otherSubsc.LName,35) + "*" + sout(otherSubsc.FName,25) + "*" + sout(otherSubsc.MiddleI,25) + "*" + "*" + "*" + "MI*" + sout(otherSub.SubscriberID,80) + "~");
                //NM109: ID
                //2330A N3: Address
                seg++;
                sw.Write("N3*" + sout(otherSubsc.Address,55));
                //N301: address
                if (StringSupport.equals(otherSubsc.Address2, ""))
                {
                    sw.WriteLine("~");
                }
                else
                {
                    sw.WriteLine("*" + sout(otherSubsc.Address2,55) + "~");
                } 
                //N302: address2
                //2330A N4: City State Zip
                seg++;
                //N401: City
                //N402: State
                sw.WriteLine("N4*" + sout(otherSubsc.City,30) + "*" + sout(otherSubsc.State,2) + "*" + Sout(otherSubsc.Zip.Replace("-", ""), 15) + "~");
                //N403: Zip
                //2330A REF: Other subscriber secondary ID. Not supported.
                //2330B NM1: Other payer name
                seg++;
                sw.Write("NM1*PR*" + "2*");
                //NM101: PR=Payer
                //NM102: 2=non-person
                if (StringSupport.equals(clearhouse.ISA08, "EMS"))
                {
                    //This is a special situation requested by EMS.  This tacks the employer onto the end of the carrier.
                    sw.Write(sout(otherCarrier.CarrierName,17) + "|" + sout(Employers.getName(otherPlan.EmployerNum),17) + "*");
                }
                else
                {
                    sw.Write(sout(otherCarrier.CarrierName,35) + "*");
                } 
                //NM103: Name. Length can be 60 in the new medical specs.
                sw.Write("*" + "*" + "*" + "*" + "PI*");
                //NM104:
                //NM105:
                //NM106: not used
                //NM107: not used
                //NM108: PI=Payor ID. XV must be used after national plan ID mandated.
                sw.WriteLine(sout(getCarrierElectID(otherCarrier,clearhouse),80,2) + "~");
                //NM109: PayorID
                //2230B N3,N4: (medical) Other payer address. We don't support.
                //2330B PER: Other payer contact info. Not needed.
                //2330B DTP: Claim Paid date
                if (!StringSupport.equals(claim.ClaimType, "P"))
                {
                    DateTime datePaidOtherIns = DateTime.Today;
                    DateTime dtThisCP = new DateTime();
                    for (int j = 0;j < claimProcs.Count;j++)
                    {
                        dtThisCP = ClaimProcs.GetDatePaid(claimProcList, claimProcs[j].ProcNum, claimProcs[j].PlanNum);
                        if (dtThisCP > datePaidOtherIns)
                        {
                            datePaidOtherIns = dtThisCP;
                        }
                         
                    }
                    //it's a required segment, so always include it.
                    seg++;
                    //DTP01: 573=Date claim paid
                    //DTP02: date time format qualifier
                    sw.WriteLine("DTP*573*" + "D8*" + datePaidOtherIns.ToString("yyyyMMdd") + "~");
                }
                 
            }
             
            for (int j = 0;j < claimProcs.Count;j++)
            {
                //DTP03 date
                //2330B DTP: (medical) Claim adjudication date. We might need to add this
                //2330B REF: Other payer secondary ID
                //2330B REF: Other payer referral number
                //2330B REF: Other payer claim adjustment indicator
                //2330C: Other payer patient info. Only used in COB.
                //2330D: Other payer referring provider. We don't support
                //2330E,F,G,H,I: (medical) not supported
                //if(claim.PlanNum2>0){
                //2400 Service Lines
                proc = Procedures.GetProcFromList(procList, claimProcs[j].ProcNum);
                procCode = ProcedureCodes.getProcCode(proc.CodeNum);
                //2400 LX: Line Counter. or (medical) Service Line Number
                seg++;
                sw.WriteLine("LX*" + (j + 1).ToString() + "~");
                if (isMedical)
                {
                    //2400 SV1: Professional Service
                    seg++;
                    //SV101 Composite Medical Procedure Identifier
                    //SV101-1: HC=Health Care
                    //SV101-2: Procedure code. The rest of SV101 is not supported
                    sw.Write("SV1*" + "HC:" + Sout(claimProcs[j].CodeSent) + "*" + claimProcs[j].FeeBilled.ToString() + "*" + "MJ*" + "0*");
                    //SV102: Charge Amt
                    //SV103: MJ=minutes
                    //SV104: Quantity of anesthesia. We don't support, so always 0.
                    if (proc.PlaceService == claim.PlaceService)
                    {
                        sw.Write("*");
                    }
                    else
                    {
                        //SV105: Place of Service Code if different from claim
                        sw.Write(getPlaceService(proc.PlaceService) + "*");
                    } 
                    sw.Write("*");
                    //SV106: not used
                    //SV107: Composite Diagnosis Code Pointer. Required when 2300HI(Health Care Diagnosis Code) is used (always).
                    //SV107-1: Primary diagnosis. Only allowed pointers 1-8 even though 2300HI supports 12 diagnoses.
                    //We don't validate that there are not more than 8 diagnoses on one claim.
                    //If the diagnosis we need is not in the first 8, then we will use the primary.
                    if (StringSupport.equals(proc.DiagnosticCode, ""))
                    {
                        //If the diagnosis is blank, we will use the primary.
                        sw.Write("1");
                    }
                    else
                    {
                        //use primary.
                        int diagI = 1;
                        for (int d = 0;d < diagnoses.Count;d++)
                        {
                            if (d > 7)
                            {
                                continue;
                            }
                             
                            //we can't point to any except first 8.
                            if (StringSupport.equals((String)diagnoses[d], proc.DiagnosticCode))
                            {
                                diagI = d + 1;
                            }
                             
                        }
                        sw.Write(diagI.ToString());
                    } 
                    //SV107-2 through 4: Other diagnoses, which we don't support yet.
                    sw.WriteLine("~");
                }
                else
                {
                    //sw.Write("*");//SV108: not used
                    //sw.Write("*");//SV109: Emergency indicator. Required if emergency. Y or blank. Not supported.
                    //sw.Write("*");//SV110: not used
                    //sw.Write("*");//SV111: EPSTD indicator (Medicaid from screening). Y or blank. Not supported.
                    //sw.Write("*");//SV112: Family planning indicator for Medicaid. Y or blank. Not supported.
                    //sw.Write("**");//SV113 and SV114: not used
                    //SV115: Copay status code: 0 or blank. Not supported
                    //2400 SV5,PWK,CR1,CR2,CR3,CR5,CRC(x4): (medical)Unsupported
                    //if isMedical
                    //2400 SV3: Dental Service
                    seg++;
                    //SV301-1: AD=ADA CDT, SV301-2:Procedure code
                    sw.Write("SV3*" + "AD:" + Sout(claimProcs[j].CodeSent, 5) + "*" + claimProcs[j].FeeBilled.ToString() + "*");
                    //SV302: Charge Amount
                    if (proc.PlaceService == claim.PlaceService)
                    {
                        sw.Write("*");
                    }
                    else
                    {
                        //SV303: Location Code if different from claim
                        sw.Write(getPlaceService(proc.PlaceService) + "*");
                    } 
                    //SV304: Area
                    sw.WriteLine(getArea(proc,procCode) + "*" + proc.Prosthesis + "*" + "1~");
                    //SV305: Initial or Replacement
                    //SV306: Procedure count
                    //2400 TOO: Tooth number/surface
                    if (procCode.TreatArea == TreatmentArea.Tooth)
                    {
                        seg++;
                        //TOO01: JP=National tooth numbering
                        sw.WriteLine("TOO*JP*" + proc.ToothNum + "~");
                    }
                    else //TOO02: Tooth number
                    if (procCode.TreatArea == TreatmentArea.Surf)
                    {
                        seg++;
                        //TOO01: JP=National tooth numbering
                        sw.Write("TOO*JP*" + proc.ToothNum + "*");
                        //TOO02: Tooth number
                        String validSurfaces = Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum);
                        for (int k = 0;k < validSurfaces.Length;k++)
                        {
                            if (k > 0)
                            {
                                sw.Write(":");
                            }
                             
                            sw.Write(validSurfaces.Substring(k, 1));
                        }
                        //TOO03: Surfaces
                        sw.WriteLine("~");
                    }
                    else if (procCode.TreatArea == TreatmentArea.ToothRange)
                    {
                        String[] individTeeth = proc.ToothRange.Split(',');
                        for (int t = 0;t < individTeeth.Length;t++)
                        {
                            seg++;
                            //TOO01: JP=National tooth numbering
                            sw.WriteLine("TOO*JP*" + individTeeth[t] + "~");
                        }
                    }
                       
                } 
                //TOO02: Tooth number
                //2400 DTP: Date of service if different from claim, but we will always show the date. Better compatibility.
                //Required if medical anyway.
                //if(claim.DateService!=proc.ProcDate){
                if (!StringSupport.equals(claim.ClaimType, "PreAuth"))
                {
                    seg++;
                    //DTP01: 472=Service
                    //DTP02: use D8
                    sw.WriteLine("DTP*472*" + "D8*" + proc.ProcDate.ToString("yyyyMMdd") + "~");
                }
                 
                //}
                //2400 DTP: Date prior placement
                if (StringSupport.equals(proc.Prosthesis, "R"))
                {
                    //already validated date
                    seg++;
                    //DTP01: 441=Prior placement
                    //DTP02: use D8
                    sw.WriteLine("DTP*441*" + "D8*" + proc.DateOriginalProsth.ToString("yyyyMMdd") + "~");
                }
                 
                //2400 DTP: Date ortho appliance placed. Not used.
                //2400 DTP: Date ortho appliance replaced.  Not used.
                //2400 DTP: (medical) Rx date. Not supported.
                //2400 DTP: (medical) Date Certification Revision. Not supported.
                //2400 DTP: (medical) Date begin therapy. Not supported.
                //2400 DTP: (medical) Date last certification. Not supported.
                //2400 DTP: (medical) Date last seen. Not supported.
                //2400 DTP: (medical) Dialysis test dates. Not supported.
                //2400 DTP: (medical) Date blood gas test. Not supported.
                //2400 DTP: (medical) Date shipped. Not supported.
                //2400 DTP: (medical) Date last x-ray. Not supported.
                //2400 DTP: (medical) Date initial tx. Not supported.
                //2400 QTY: (medical) Ambulance patient count. Not supported.
                //2400 QTY: Anesthesia quantity. Not used.
                //2400 MEA,CN1: (medical) . Not supported.
                //2400 REF: (medical) A variety of medical REFs not supported.
                //2400 REF: Pretermination ID. Not used.
                //2400 REF: Referral #. Not used.
                //2400 REF: Line item control number(Proc Num)
                seg++;
                //REF01: 6R=Procedure control number
                sw.WriteLine("REF*6R*" + proc.ProcNum.ToString() + "~");
                //2400 AMT(x4): (medical) Various amounts. Not supported
                //2400 K3: (medical) Not supported.
                //2400 NTE: Line note
                if (!StringSupport.equals(proc.ClaimNote, ""))
                {
                    seg++;
                    //NTE01: ADD=Additional info
                    sw.WriteLine("NTE*ADD*" + sout(proc.ClaimNote,80) + "~");
                }
                 
                //2400 NTE,PS1,HCP: (medical) Not supported
                //2410 LIN,CTP,REF: (medical) Not supported
                //2420A NM1: Rendering provider name. Only if different from the claim.
                if (claim.ProvTreat != proc.ProvNum && PrefC.getBool(PrefName.EclaimsSeparateTreatProv))
                {
                    provTreat = ProviderC.getListLong()[Providers.getIndexLong(proc.ProvNum)];
                    seg++;
                    //82=rendering prov
                    //NM102: 1=person
                    //NM103: LName
                    //NM104: FName
                    sw.Write("NM1*82*" + "1*" + sout(provTreat.LName,35) + "*" + sout(provTreat.FName,25) + "*" + sout(provTreat.MI,25) + "*" + "*" + "*");
                    //NM105: MiddleName
                    //NM106: not used.
                    //NM107: suffix. not supported.
                    //After NPI date, so always do it one way:
                    sw.Write("XX*");
                    //NM108: XX=NPI
                    sw.Write(sout(provTreat.NationalProvID,80));
                    //NM109: ID.  NPI validated.
                    sw.WriteLine("~");
                    //2420A PRV: Rendering provider information
                    seg++;
                    sw.Write("PRV*");
                    sw.Write("PE*");
                    //PRV01: PE=Performing
                    if (isMedical)
                    {
                        sw.Write("PXC*");
                    }
                    else
                    {
                        //PRV02: PXC=health care provider taxonomy code
                        sw.Write("ZZ*");
                    } 
                    //PRV02: ZZ=mutually defined taxonomy code
                    sw.WriteLine(X12Generator.getTaxonomy(provTreat) + "~");
                    //PRV03: Taxonomy code
                    //2420A REF: Rendering provider secondary ID.
                    //2420A REF: (medical)Required before NPI date. We already enforce NPI in NM109.  Less allowed values.
                    seg++;
                    //REF01: 0B=state license #
                    sw.WriteLine("REF*0B*" + sout(provTreat.StateLicense,30) + "~");
                }
                 
            }
            //2420A REF: Rendering provider secondary ID
            //if(!isMedical){//we can't support these numbers very well yet for medical
            //	seg+=WriteProv_REF(sw,provTreat,(string)claimAr[0,i]);
            //}
            //2420B,C,D,E,F,G,H,I,2430,2440: (medical) not supported
            //for int i claimProcs
            //if this is the last loop
            if (i == claimItems.Count - 1 || claimItems[i].PayorId0 != claimItems[i + 1].PayorId0)
            {
                //or the payorID will change
                hasFooter = true;
            }
            else
            {
                hasFooter = false;
            } 
            if (hasFooter)
            {
                //Transaction Trailer
                seg++;
                //SE01: Total segments, including ST & SE
                sw.WriteLine("SE*" + seg.ToString() + "*" + transactionNum.ToString().PadLeft(4, '0') + "~");
                if (i < claimItems.Count - 1)
                {
                    //if this is not the last loop
                    transactionNum++;
                }
                 
            }
             
        }
        //sw.WriteLine();
        //for claimAr i
        //Functional Group Trailer
        //GE01: Number of transaction sets included
        sw.WriteLine("GE*" + transactionNum.ToString() + "*" + groupControlNumber + "~");
    }

    //GE02: Group Control number. Must be identical to GS06
    private static boolean isApex(Clearinghouse clearinghouse) throws Exception {
        return (StringSupport.equals(clearinghouse.ISA08, "99999"));
    }

    private static boolean isTesia(Clearinghouse clearinghouse) throws Exception {
        return (StringSupport.equals(clearinghouse.ISA08, "113504607"));
    }

    /**
    * Sometimes writes the name information for Open Dental. Sometimes it writes practice info.
    */
    private static void write1000A_NM1(StreamWriter sw, Clearinghouse clearhouse) throws Exception {
        if (StringSupport.equals(clearhouse.SenderTIN, ""))
        {
            //use OD
            sw.WriteLine("NM1*41*" + "2*" + "OPEN DENTAL SOFTWARE*" + "****46*" + "810624427~");
        }
        else
        {
            //NM101: 41=submitter
            //NM102: 2=nonperson
            //NM103:Submitter Name
            //NM108: 46 indicates ETIN
            //NM109: ID Code. aka ETIN#.
            //NM101: 41=submitter
            //NM102: 1=person,2=nonperson
            //NM103:Submitter Name. Validated.
            //NM108: 46 indicates ETIN
            sw.WriteLine("NM1*41*" + "2*" + sout(clearhouse.SenderName,35,1) + "*" + "****46*" + sout(clearhouse.SenderTIN,80,2) + "~");
        } 
    }

    //NM109: ID Code. aka ETIN#. Validated to be at least 2.
    /**
    * Usually writes the contact information for Open Dental. But for inmediata and AOS clearinghouses, it writes practice contact info.
    */
    private static void write1000A_PER(StreamWriter sw, Clearinghouse clearhouse) throws Exception {
        if (StringSupport.equals(clearhouse.SenderTIN, ""))
        {
            //use OD
            sw.WriteLine("PER*IC*" + "JORDAN SPARKS*" + "TE*" + "8776861248~");
        }
        else
        {
            //PER01:Function code: IC=Information Contact
            //PER02:Name
            //PER03:Comm Number Qualifier: TE=Telephone
            //PER04:Comm Number. aka telephone number
            //PER01:Function code: IC=Information Contact
            //PER02:Name. Validated.
            //PER03:Comm Number Qualifier: TE=Telephone
            sw.WriteLine("PER*IC*" + sout(clearhouse.SenderName,60,1) + "*" + "TE*" + clearhouse.SenderTelephone + "~");
        } 
    }

    //PER04:Comm Number. aka telephone number. Validated to be exactly 10 digits.
    /**
    * This is depedent only on the electronic payor id # rather than the clearinghouse.  Used for billing prov and also for treating prov. Returns the number of segments written
    */
    private static int writeProv_REF(StreamWriter sw, Provider prov, String payorID) throws Exception {
        int retVal = 0;
        ElectID electID = ElectIDs.getID(payorID);
        //if(electID==null){
        //	return;
        //}
        if (electID != null && electID.IsMedicaid && prov.MedicaidID.Trim().Length > 2)
        {
            retVal++;
            //REF01: ID qualifier. 1D=Medicaid
            sw.WriteLine("REF*" + "1D*" + sout(prov.MedicaidID,30,1) + "~");
        }
         
        //REF02. ID number
        //I don't think there would be additional id's if Medicaid, but just in case, no return.
        ProviderIdent[] provIdents = ProviderIdents.getForPayor(prov.ProvNum,payorID);
        for (int i = 0;i < provIdents.Length;i++)
        {
            if (provIdents[i].IDNumber.Trim().Length < 2)
            {
                continue;
            }
             
            //ensure's it's not blank
            retVal++;
            //REF01: ID qualifier, a 2 letter code representing type
            sw.WriteLine("REF*" + GetProvTypeQualifier(provIdents[i].SuppIDType) + "*" + provIdents[i].IDNumber + "~");
        }
        return retVal;
    }

    //REF02. ID number
    private static String getProvTypeQualifier(ProviderSupplementalID provType) throws Exception {
        switch(provType)
        {
            case BlueCross: 
                return "1A";
            case BlueShield: 
                return "1B";
            case SiteNumber: 
                return "G5";
            case CommercialNumber: 
                return "G2";
        
        }
        return "";
    }

    private static String getCarrierElectID(Carrier carrier, Clearinghouse clearhouse) throws Exception {
        String electid = carrier.ElectID;
        if (StringSupport.equals(electid, "") && isApex(clearhouse))
        {
            return "PAPRM";
        }
         
        //only for Apex
        //paper claims
        if (StringSupport.equals(electid, "") && isTesia(clearhouse))
        {
            return "00000";
        }
         
        //only for Tesia
        //paper claims
        if (electid.Length < 3)
        {
            return "06126";
        }
         
        return electid;
    }

    //paper claims
    private static String getGender(PatientGender patGender) throws Exception {
        switch(patGender)
        {
            case Male: 
                return "M";
            case Female: 
                return "F";
            case Unknown: 
                return "U";
        
        }
        return "";
    }

    private static String getRelat(Relat relat) throws Exception {
        switch(relat)
        {
            case Self: 
                return "18";
            case Child: 
                return "19";
            case Dependent: 
                return "76";
            case Employee: 
                return "20";
            case HandicapDep: 
                return "22";
            case InjuredPlaintiff: 
                return "41";
            case LifePartner: 
                return "53";
            case SignifOther: 
                return "29";
            case Spouse: 
                return "01";
        
        }
        return "";
    }

    private static String getStudent(String studentStatus) throws Exception {
        if (StringSupport.equals(studentStatus, "P"))
        {
            return "P";
        }
         
        if (StringSupport.equals(studentStatus, "F"))
        {
            return "F";
        }
         
        return "N";
    }

    //either N or blank
    private static String getPlaceService(PlaceOfService place) throws Exception {
        switch(place)
        {
            case AmbulatorySurgicalCenter: 
                return "24";
            case CustodialCareFacility: 
                return "33";
            case EmergencyRoomHospital: 
                return "23";
            case FederalHealthCenter: 
                return "50";
            case InpatHospital: 
                return "21";
            case MilitaryTreatFac: 
                return "26";
            case MobileUnit: 
                return "15";
            case Office: 
            case OtherLocation: 
                return "11";
            case OutpatHospital: 
                return "22";
            case PatientsHome: 
                return "12";
            case PublicHealthClinic: 
                return "71";
            case RuralHealthClinic: 
                return "72";
            case School: 
                return "03";
            case SkilledNursFac: 
                return "31";
        
        }
        return "11";
    }

    private static String getRelatedCauses(Claim claim) throws Exception {
        if (StringSupport.equals(claim.AccidentRelated, ""))
        {
            return "";
        }
         
        //even though the specs let you submit all three types at once, we only allow one of the three
        if (StringSupport.equals(claim.AccidentRelated, "A"))
        {
            return "AA:::" + sout(claim.AccidentST,2,2);
        }
        else if (StringSupport.equals(claim.AccidentRelated, "E"))
        {
            return "EM";
        }
        else
        {
            return "OA";
        }  
    }

    // if(claim.AccidentRelated=="O"){
    /**
    * This used to be an enumeration.
    */
    private static String getFilingCode(InsPlan plan) throws Exception {
        String filingcode = InsFilingCodes.getEclaimCode(plan.FilingCode);
        //must be one or two char in length.
        if (StringSupport.equals(filingcode, "") || filingcode.Length > 2)
        {
            return "CI";
        }
         
        return sout(filingcode,2,1);
    }

    /*
    			switch(plan.FilingCode){
    				case InsFilingCodeOld.SelfPay:
    					return "09";
    				case InsFilingCodeOld.OtherNonFed:
    					return "11";
    				case InsFilingCodeOld.PPO:
    					return "12";
    				case InsFilingCodeOld.POS:
    					return "13";
    				case InsFilingCodeOld.EPO:
    					return "14";
    				case InsFilingCodeOld.Indemnity:
    					return "15";
    				case InsFilingCodeOld.HMO_MedicareRisk:
    					return "16";
    				case InsFilingCodeOld.DMO:
    					return "17";
    				case InsFilingCodeOld.BCBS:
    					return "BL";
    				case InsFilingCodeOld.Champus:
    					return "CH";
    				case InsFilingCodeOld.Commercial_Insurance:
    					return "CI";
    				case InsFilingCodeOld.Disability:
    					return "DS";
    				case InsFilingCodeOld.FEP:
    					return "FI";
    				case InsFilingCodeOld.HMO:
    					return "HM";
    				case InsFilingCodeOld.LiabilityMedical:
    					return "LM";
    				case InsFilingCodeOld.MedicarePartB:
    					return "MB";
    				case InsFilingCodeOld.Medicaid:
    					return "MC";
    				case InsFilingCodeOld.ManagedCare_NonHMO:
    					return "MH";
    				case InsFilingCodeOld.OtherFederalProgram:
    					return "OF";
    				case InsFilingCodeOld.SelfAdministered:
    					return "SA";
    				case InsFilingCodeOld.Veterans:
    					return "VA";
    				case InsFilingCodeOld.WorkersComp:
    					return "WC";
    				case InsFilingCodeOld.MutuallyDefined:
    					return "ZZ";
    				default:
    					return "CI";
    			}
    			*/
    private static String getArea(Procedure proc, ProcedureCode procCode) throws Exception {
        if (procCode.TreatArea == TreatmentArea.Arch)
        {
            if (StringSupport.equals(proc.Surf, "U"))
            {
                return "01";
            }
             
            if (StringSupport.equals(proc.Surf, "L"))
            {
                return "02";
            }
             
        }
         
        if (procCode.TreatArea == TreatmentArea.Mouth)
        {
            return "";
        }
         
        if (procCode.TreatArea == TreatmentArea.Quad)
        {
            if (StringSupport.equals(proc.Surf, "UR"))
            {
                return "10";
            }
             
            if (StringSupport.equals(proc.Surf, "UL"))
            {
                return "20";
            }
             
            if (StringSupport.equals(proc.Surf, "LR"))
            {
                return "40";
            }
             
            if (StringSupport.equals(proc.Surf, "LL"))
            {
                return "30";
            }
             
        }
         
        if (procCode.TreatArea == TreatmentArea.Sextant)
        {
            return "";
        }
         
        //we will assume that these are very rarely billed to ins
        if (procCode.TreatArea == TreatmentArea.Surf)
        {
            return "";
        }
         
        //might need to enhance this
        if (procCode.TreatArea == TreatmentArea.Tooth)
        {
            return "";
        }
         
        //might need to enhance this
        if (procCode.TreatArea == TreatmentArea.ToothRange)
        {
            //already checked for blank tooth range
            if (Tooth.IsMaxillary(proc.ToothRange.Split(',')[0]))
            {
                return "01";
            }
            else
            {
                return "02";
            } 
        }
         
        return "";
    }

    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String intputStr, int maxL, int minL) throws Exception {
        String retStr = intputStr.ToUpper();
        //Debug.Write(retStr+",");
        retStr = Regex.Replace(retStr, "[^\\w!\"&'\\(\\)\\+,-\\./;\\?= #]", "");
        //replaces characters in this input string
        //Allowed: !"&'()+,-./;?=(space)#   # is actually part of extended character set
        //[](any single char)^(that is not)\w(A-Z or 0-9) or one of the above chars.
        retStr = Regex.Replace(retStr, "[_]", "");
        //replaces _
        retStr = retStr.Trim();
        //removes leading and trailing spaces.
        if (maxL != -1)
        {
            if (retStr.Length > maxL)
            {
                retStr = retStr.Substring(0, maxL);
            }
             
        }
         
        if (minL != -1)
        {
            if (retStr.Length < minL)
            {
                retStr = retStr.PadRight(minL, ' ');
            }
             
        }
         
        return retStr;
    }

    //Debug.WriteLine(retStr);
    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String str, int maxL) throws Exception {
        return sout(str,maxL,-1);
    }

    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String str) throws Exception {
        return sout(str,-1,-1);
    }

    /**
    * Returns a string describing all missing data on this claim.  Claim will not be allowed to be sent electronically unless this string comes back empty.  There is also an out parameter containing any warnings.  Warnings will not block sending.
    */
    public static void validate(ClaimSendQueueItem queueItem) throws Exception {
        //,out string warning) {
        StringBuilder strb = new StringBuilder();
        String warning = "";
        Clearinghouse clearhouse = null;
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            //ClearinghouseL.GetClearinghouse(queueItem.ClearinghouseNum);
            if (Clearinghouses.getListt()[i].ClearinghouseNum == queueItem.ClearinghouseNum)
            {
                clearhouse = Clearinghouses.getListt()[i];
            }
             
        }
        if (clearhouse == null)
        {
            throw new ApplicationException("Error. Could not locate Clearinghouse.");
        }
         
        Claim claim = Claims.getClaim(queueItem.ClaimNum);
        Clinic clinic = Clinics.getClinic(claim.ClinicNum);
        //if(clearhouse.Eformat==ElectronicClaimFormat.X12){//not needed since this is always true
        X12Validate.iSA(clearhouse,strb);
        if (clearhouse.GS03.Length < 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Clearinghouse GS03");
        }
         
        List<X12TransactionItem> claimItems = Claims.getX12TransactionInfo(((ClaimSendQueueItem)queueItem).ClaimNum);
        //just to get prov. Needs work.
        Provider billProv = ProviderC.getListLong()[Providers.GetIndexLong(claimItems[0].ProvBill1)];
        Provider treatProv = ProviderC.getListLong()[Providers.getIndexLong(claim.ProvTreat)];
        InsPlan insPlan = InsPlans.getPlan(claim.PlanNum,null);
        InsSub sub = InsSubs.getSub(claim.InsSubNum,null);
        //if(insPlan.IsMedical) {
        //	return "Medical e-claims not allowed";
        //}
        //billProv
        X12Validate.billProv(billProv,strb);
        //treatProv
        if (StringSupport.equals(treatProv.LName, ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Treating Prov LName");
        }
         
        if (StringSupport.equals(treatProv.FName, ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Treating Prov FName");
        }
         
        //Treating prov SSN/TIN is not sent on paper or eclaims. Do not verify or block.
        if (!Regex.IsMatch(treatProv.NationalProvID, "^(80840)?[0-9]{10}$"))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Treating Prov NPI must be a 10 digit number with an optional prefix of 80840");
        }
         
        if (treatProv.TaxonomyCodeOverride.Length > 0 && treatProv.TaxonomyCodeOverride.Length != 10)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Treating Prov Taxonomy Code must be 10 characters");
        }
         
        if (StringSupport.equals(treatProv.StateLicense, ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Treating Prov Lic #");
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeTitle), ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Practice Title");
        }
         
        if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
        {
            X12Validate.billingAddress(strb);
        }
        else if (clinic == null)
        {
            X12Validate.practiceAddress(strb);
        }
        else
        {
            X12Validate.clinic(clinic,strb);
        }  
        if (!sub.ReleaseInfo)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("InsPlan Release of Info");
        }
         
        Carrier carrier = Carriers.getCarrier(insPlan.CarrierNum);
        X12Validate.carrier(carrier,strb);
        ElectID electID = ElectIDs.getID(carrier.ElectID);
        if (electID != null && electID.IsMedicaid && StringSupport.equals(billProv.MedicaidID, ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("BillProv Medicaid ID");
        }
         
        Patient patient = Patients.getPat(claim.PatNum);
        if (claim.PlanNum2 > 0)
        {
            InsPlan insPlan2 = InsPlans.GetPlan(claim.PlanNum2, new List<InsPlan>());
            InsSub sub2 = InsSubs.getSub(claim.InsSubNum2,null);
            if (sub2.SubscriberID.Length < 2)
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary SubscriberID");
            }
             
            Patient subscriber2 = Patients.getPat(sub2.Subscriber);
            //Always exists because validated in UI.
            if (subscriber2.Birthdate.Year < 1880)
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Subscriber Birthdate");
            }
             
            if (subscriber2.PatNum != patient.PatNum)
            {
                //Patient address is validated below, so we only need to check if subscriber is not the patient.
                X12Validate.subscriber2(subscriber2,strb);
            }
             
            Carrier carrier2 = Carriers.getCarrier(insPlan2.CarrierNum);
            if (StringSupport.equals(carrier2.Address.Trim(), ""))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Carrier Address");
            }
             
            if (carrier2.City.Trim().Length < 2)
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Carrier City");
            }
             
            if (carrier2.State.Trim().Length != 2)
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Carrier State(2 char)");
            }
             
            if (carrier2.Zip.Trim().Length < 3)
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Carrier Zip");
            }
             
            //if patient is not subscriber
            if (claim.PatNum != sub2.Subscriber && claim.PatRelat2 == Relat.Self)
            {
                //and relat is self
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary Relationship");
            }
             
        }
        else
        {
            //other insurance not specified
            if (StringSupport.equals(claim.ClaimType, "S"))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append("Secondary claim missing other insurance");
            }
             
        } 
        //Provider Idents:
        /*ProviderSupplementalID[] providerIdents=ElectIDs.GetRequiredIdents(carrier.ElectID);
        				//No longer any required supplemental IDs
        			for(int i=0;i<providerIdents.Length;i++){
        				if(!ProviderIdents.IdentExists(providerIdents[i],billProv.ProvNum,carrier.ElectID)){
        					if(retVal!="")
        						strb.Append(",";
        					strb.Append("Billing Prov Supplemental ID:"+providerIdents[i].ToString();
        				}
        			}*/
        if (sub.SubscriberID.Length < 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("SubscriberID");
        }
         
        Patient subscriber = Patients.getPat(sub.Subscriber);
        //We do not need to validate subscriber DOB, because the date '01/01/1900' will be sent above if the DOB is before the year 1900.
        if (subscriber.PatNum != patient.PatNum)
        {
            //Patient address is validated below, so we only need to check if subscriber is not the patient.
            X12Validate.subscriber(subscriber,strb);
        }
         
        //if patient is not subscriber
        if (claim.PatNum != sub.Subscriber && claim.PatRelat == Relat.Self)
        {
            //and relat is self
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Claim Relationship");
        }
         
        if (StringSupport.equals(patient.Address.Trim(), ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient Address");
        }
         
        if (patient.City.Trim().Length < 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient City");
        }
         
        if (patient.State.Trim().Length != 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient State");
        }
         
        if (patient.Zip.Trim().Length < 3)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient Zip");
        }
         
        if (patient.Birthdate.Year < 1880)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient Birthdate");
        }
         
        if (StringSupport.equals(claim.AccidentRelated, "A") && claim.AccidentST.Length != 2)
        {
            //auto accident with no state
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Auto accident State");
        }
         
        /*if(clearhouse.ISA08=="113504607" && claim.Attachments.Count>0) {//If Tesia and has attachments
        				string storedFile;
        				for(int c=0;c<claim.Attachments.Count;c++) {
        					storedFile=ODFileUtils.CombinePaths(FormEmailMessageEdit.GetAttachPath(),claim.Attachments[c].ActualFileName);
        					if(!File.Exists(storedFile)){
        						if(retVal!="")
        							strb.Append(",";
        						strb.Append("attachments missing";
        						break;
        					}
        				}
        			}*/
        //Warning if attachments are listed as Mail even though we are sending electronically.
        boolean pwkNeeded = false;
        if (!StringSupport.equals(claim.AttachedFlags, "Mail"))
        {
            //in other words, if there are additional flags.
            pwkNeeded = true;
        }
         
        if (claim.Radiographs > 0 || claim.AttachedImages > 0 || claim.AttachedModels > 0)
        {
            pwkNeeded = true;
        }
         
        if (claim.AttachedFlags.Contains("Mail") && pwkNeeded)
        {
            if (!StringSupport.equals(warning, ""))
                warning += ",";
             
            warning += "Attachments set to Mail";
        }
         
        //Warning if any PWK segments are needed, and there is no ID code.
        if (pwkNeeded && StringSupport.equals(claim.AttachmentID, ""))
        {
            if (!StringSupport.equals(warning, ""))
                warning += ",";
             
            warning += "Attachment ID missing";
        }
         
        //List<ClaimProc> claimProcList=ClaimProcs.Refresh(patient.PatNum);
        //List<ClaimProc> claimProcs=ClaimProcs.GetForSendClaim(claimProcList,claim.ClaimNum);
        //List<Procedure> procList=Procedures.Refresh(claim.PatNum);
        List<ClaimProc> claimProcList = ClaimProcs.refreshForClaim(claim.ClaimNum);
        List<ClaimProc> claimProcs = ClaimProcs.GetForSendClaim(claimProcList, claim.ClaimNum);
        if (claimProcs.Count == 0)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("No procedures attached please recreate claim");
        }
         
        List<Procedure> procList = Procedures.GetProcsFromClaimProcs(claimProcs);
        Procedure proc;
        ProcedureCode procCode;
        boolean princDiagExists = false;
        for (int i = 0;i < claimProcs.Count;i++)
        {
            proc = Procedures.GetProcFromList(procList, claimProcs[i].ProcNum);
            procCode = ProcedureCodes.getProcCode(proc.CodeNum);
            if (procCode.TreatArea == TreatmentArea.Arch && StringSupport.equals(proc.Surf, ""))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(procCode.AbbrDesc + " missing arch");
            }
             
            if (procCode.TreatArea == TreatmentArea.ToothRange && StringSupport.equals(proc.ToothRange, ""))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(procCode.AbbrDesc + " tooth range");
            }
             
            if ((procCode.TreatArea == TreatmentArea.Tooth || procCode.TreatArea == TreatmentArea.Surf) && !Tooth.isValidDB(proc.ToothNum))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(procCode.AbbrDesc + " tooth number");
            }
             
            if (procCode.TreatArea == TreatmentArea.Surf && StringSupport.equals(proc.Surf, ""))
            {
                if (strb.Length != 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(procCode.AbbrDesc + " surface missing");
            }
             
            if (procCode.IsProsth)
            {
                if (StringSupport.equals(proc.Prosthesis, ""))
                {
                    //they didn't enter whether Initial or Replacement
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append(procCode.AbbrDesc + " Prosthesis");
                }
                 
                if (StringSupport.equals(proc.Prosthesis, "R") && proc.DateOriginalProsth.Year < 1880)
                {
                    //if a replacement, they didn't enter a date
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append(procCode.AbbrDesc + " Prosth Date");
                }
                 
            }
             
            //if(proc.PlaceService!=claim.PlaceService) {
            //  if(strb.Length!=0) {
            //    strb.Append(",");
            //  }
            //  strb.Append("Proc place of service does not match claim "+procCode.ProcCode);
            //}
            if (insPlan.IsMedical)
            {
                if (StringSupport.equals(proc.DiagnosticCode, ""))
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Procedure Diagnosis");
                }
                 
                if (proc.IsPrincDiag && !StringSupport.equals(proc.DiagnosticCode, ""))
                {
                    princDiagExists = true;
                }
                 
            }
             
            if (claim.ProvTreat != proc.ProvNum && PrefC.getBool(PrefName.EclaimsSeparateTreatProv))
            {
                treatProv = ProviderC.getListLong()[Providers.getIndexLong(proc.ProvNum)];
                if (StringSupport.equals(treatProv.LName, ""))
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Treating Prov LName");
                }
                 
                if (StringSupport.equals(treatProv.FName, ""))
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Treating Prov FName");
                }
                 
                //Treating prov SSN/TIN is not sent on paper or eclaims. Do not verify or block.
                if (treatProv.NationalProvID.Length < 2)
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Treating Prov NPI");
                }
                 
                if (treatProv.TaxonomyCodeOverride.Length > 0 && treatProv.TaxonomyCodeOverride.Length != 10)
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Treating Prov Taxonomy Code for proc " + procCode.ProcCode + " must be 10 characters");
                }
                 
                if (StringSupport.equals(treatProv.StateLicense, ""))
                {
                    if (strb.Length != 0)
                    {
                        strb.Append(",");
                    }
                     
                    strb.Append("Treating Prov Lic #");
                }
                 
            }
             
        }
        //will add any other checks as needed. Can't think of any others at the moment.
        //for int i claimProcs
        if (insPlan.IsMedical && !princDiagExists)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Princ Diagnosis");
        }
         
        /*
        						if(==""){
        							if(strb.Length!=0) {
        								strb.Append(",");
        							}
        							strb.Append("";
        						}*/
        //return strb.ToString();
        queueItem.Warnings = warning;
        queueItem.MissingData = strb.ToString();
    }

    /**
    * //Loops through the 837 to find the transaction number for the specified claim. Will return 0 if can't find.
    */
    //public int GetTransNum(long claimNum) {
    //  string curTransNumStr="";
    //  for(int i=0;i<Segments.Count;i++) {
    //    if(Segments[i].SegmentID=="ST"){
    //      curTransNumStr=Segments[i].Get(2);
    //    }
    //    if(Segments[i].SegmentID=="CLM"){
    //      if(Segments[i].Get(1).TrimStart(new char[] {'0'})==claimNum.ToString()){//if for specified claim
    //        try {
    //          return PIn.Int(curTransNumStr);
    //        }
    //        catch {
    //          return 0;
    //        }
    //      }
    //    }
    //  }
    //  return 0;
    //}
    /**
    * Loops through the 837 to see if attachments were sent.
    */
    public boolean attachmentsWereSent(long claimNum) throws Exception {
        boolean isCurrentClaim = false;
        for (int i = 0;i < Segments.Count;i++)
        {
            if (StringSupport.equals(Segments[i].SegmentID, "CLM"))
            {
                //The following check is currently broken, because we need to compare the claimNum to the portion of the CLM01 which is after the separator (/ or -). CLM01 is typically formatted like PatNum/ClaimNum
                if (Segments[i].Get(1).TrimStart(new char[]{ '0' }) == claimNum.ToString())
                {
                    //if for specified claim
                    isCurrentClaim = true;
                }
                else
                {
                    isCurrentClaim = false;
                } 
            }
             
            if (StringSupport.equals(Segments[i].SegmentID, "PWK") && isCurrentClaim)
            {
                return true;
            }
             
        }
        return false;
    }

}


