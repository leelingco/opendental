//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:32 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.ClearinghouseL;
import OpenDental.Lan;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;

public class Dutch   
{
    /**
    * Called from Eclaims and includes multiple claims.  This should return the text of the file that was sent so that it can be saved inthe db.  If this returns an empty result, then claims won't be marked as sent.  The problem is that we create multiple files here.
    */
    public static String sendBatch(List<ClaimSendQueueItem> queueItems, int batchNum) throws Exception {
        for (int i = 0;i < queueItems.Count;i++)
        {
            //We assume for now one file per claim.
            if (!CreateClaim(queueItems[i], batchNum))
            {
                return "";
            }
             
        }
        return "Sent";
    }

    //no need to translate.  User will not see.
    /**
    * Called once for each claim to be created.  For claims with a lot of procedures, this may actually create multiple claims.  Normally returns empty string unless something went wrong.
    */
    public static boolean createClaim(ClaimSendQueueItem queueItem, int batchNum) throws Exception {
        StringBuilder strb = new StringBuilder();
        String t = "\t";
        strb.Append("110\t111\t112\t118\t203/403\tF108/204/404\t205/405\t206\t207\t208\t209\t210\t211\t212\t215\t217\t219\t406\t408\t409\t410\t411\t413\t414\t415\t416\t418\t419\t420\t421\t422\t423\t424\t425\t426\t428\t429\t430\t432\t433\r\n");
        Clearinghouse clearhouse = ClearinghouseL.getClearinghouse(queueItem.ClearinghouseNum);
        Claim claim = Claims.getClaim(queueItem.ClaimNum);
        Provider provBill = Providers.getProv(claim.ProvBill);
        Patient pat = Patients.getPat(claim.PatNum);
        InsPlan insplan = InsPlans.GetPlan(claim.PlanNum, new List<InsPlan>());
        InsSub insSub = InsSubs.GetSub(claim.InsSubNum, new List<InsSub>());
        Carrier carrier = Carriers.getCarrier(insplan.CarrierNum);
        List<ClaimProc> claimProcList = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcsForClaim = ClaimProcs.GetForSendClaim(claimProcList, claim.ClaimNum);
        List<Procedure> procList = Procedures.refresh(claim.PatNum);
        Procedure proc;
        ProcedureCode procCode;
        for (int i = 0;i < claimProcsForClaim.Count;i++)
        {
            //ProcedureCode procCode;
            proc = Procedures.GetProcFromList(procList, claimProcsForClaim[i].ProcNum);
            //procCode=Pro
            strb.Append(provBill.SSN + t);
            //110
            strb.Append(provBill.MedicaidID + t);
            //111
            strb.Append(t);
            //112
            strb.Append(t);
            //118
            strb.Append(pat.SSN + t);
            //203/403
            strb.Append(carrier.CarrierName + t);
            //carrier name?
            strb.Append(insSub.SubscriberID + t);
            strb.Append(pat.PatNum.ToString() + t);
            strb.Append(pat.Birthdate.ToString("dd-MM-yyyy") + t);
            if (pat.Gender == PatientGender.Female)
            {
                strb.Append("2" + t);
            }
            else
            {
                //"V"+t);
                strb.Append("1" + t);
            } 
            //M"+t);
            strb.Append("1" + t);
            strb.Append(dutchLName(pat.LName) + t);
            //last name without prefix
            strb.Append(dutchLNamePrefix(pat.LName) + t);
            //prefix
            strb.Append("2" + t);
            strb.Append(dutchInitials(pat) + t);
            //215. initials
            strb.Append(pat.Zip + t);
            strb.Append(dutchAddressNumber(pat.Address) + t);
            //219 house number.  Already validated.
            strb.Append(t);
            strb.Append(proc.ProcDate.ToString("dd-MM-yyyy") + t);
            //procDate
            procCode = ProcedureCodes.getProcCode(proc.CodeNum);
            String strProcCode = procCode.ProcCode;
            if (strProcCode.EndsWith("00"))
            {
                //ending with 00 indicates it's a lab code.
                strb.Append("02" + t);
            }
            else
            {
                strb.Append("01" + t);
            } 
            //409. Procedure code (01) or lab costs (02)
            strb.Append(t);
            strb.Append(t);
            strb.Append(strProcCode + t);
            strb.Append(getUL(proc,procCode) + t);
            //414. U/L.
            strb.Append(Tooth.toInternat(proc.ToothNum) + t);
            strb.Append(Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum) + t);
            //needs validation
            strb.Append(t);
            if (StringSupport.equals(claim.AccidentRelated, ""))
            {
                //not accident
                strb.Append("N" + t);
            }
            else
            {
                strb.Append("J" + t);
            } 
            strb.Append(pat.SSN + t);
            strb.Append(t);
            strb.Append(t);
            strb.Append(t);
            strb.Append(proc.ProcFee.ToString("F") + t);
            strb.Append("1" + t);
            strb.Append(proc.ProcFee.ToString("F") + t);
            strb.Append(t);
            strb.Append(t);
            strb.Append(proc.ProcFee.ToString("F") + t);
            strb.Append(t);
            strb.Append(t);
            strb.Append("\r\n");
        }
        String saveFolder = clearhouse.ExportPath;
        if (!Directory.Exists(saveFolder))
        {
            MessageBox.Show(saveFolder + " " + Lan.g("Dutch","not found."));
            return false;
        }
         
        String saveFile = CodeBase.ODFileUtils.combinePaths(saveFolder,"claims" + claim.ClaimNum.ToString() + ".txt");
        File.WriteAllText(saveFile, strb.ToString());
        return true;
    }

    //MessageBox.Show(strb.ToString());
    /**
    * Returns only the portion of the LName not including the prefix
    */
    public static String dutchLName(String fullLName) throws Exception {
        //eg. Berg, van der
        if (!fullLName.Contains(","))
        {
            return fullLName;
        }
         
        return fullLName.Substring(0, fullLName.IndexOf(","));
    }

    /**
    * Returns only the prefix of the LName
    */
    public static String dutchLNamePrefix(String fullLName) throws Exception {
        //eg. Berg, van der
        if (!fullLName.Contains(","))
        {
            return "";
        }
         
        if (fullLName.EndsWith(","))
        {
            return "";
        }
         
        String retVal = fullLName.Substring(fullLName.IndexOf(",") + 1);
        // van der
        retVal.TrimStart(' ');
        return retVal;
    }

    public static String dutchInitials(Patient pat) throws Exception {
        String[] arrayFirstNames = pat.FName.Split(new char[]{ '-' }, StringSplitOptions.RemoveEmptyEntries);
        String retVal = "";
        for (int i = 0;i < arrayFirstNames.Length;i++)
        {
            retVal += arrayFirstNames[i].Substring(0, 1).ToUpper() + ".";
        }
        if (!StringSupport.equals(pat.MiddleI, ""))
        {
            retVal += pat.MiddleI.Substring(0, 1).ToUpper() + ".";
        }
         
        return retVal;
    }

    /**
    * Returns only the house number portion of the address.  Expects a number.  Already validated that some text came before the number.
    */
    public static String dutchAddressNumber(String address) throws Exception {
        Match match = Regex.Match(address, "[0-9]+");
        return match.Value;
    }

    //find the first group of numbers
    /**
    * Returns either 0,1,or 2
    */
    public static String getUL(Procedure proc, ProcedureCode procCode) throws Exception {
        if (procCode.TreatArea == TreatmentArea.Arch)
        {
            if (StringSupport.equals(proc.Surf, "U"))
            {
                return "1";
            }
             
            if (StringSupport.equals(proc.Surf, "L"))
            {
                return "2";
            }
             
            return "0";
        }
        else
        {
            return "0";
        } 
    }

    //should never happen
    /**
    * Returns a string describing all missing data on this claim.  Claim will not be allowed to be sent electronically unless this string comes back empty.  There is also an out parameter containing any warnings.  Warnings will not block sending.
    */
    public static void getMissingData(ClaimSendQueueItem queueItem) throws Exception {
        //,out string warning) {
        StringBuilder strb = new StringBuilder();
        String warning = "";
        Claim claim = Claims.getClaim(queueItem.ClaimNum);
        Patient pat = Patients.getPat(claim.PatNum);
        if (!Regex.IsMatch(pat.Address, "^[a-zA-Z ]+[0-9]+"))
        {
            //format must be streetname, then some numbers, then anything else.
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Patient address format");
        }
         
        //return strb.ToString();
        queueItem.MissingData = strb.ToString();
        queueItem.Warnings = warning;
    }

}


