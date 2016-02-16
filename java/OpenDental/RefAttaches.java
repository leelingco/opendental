//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.RefAttach;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;

/*================================================================================================
		=================================== class RefAttaches ==========================================*/
/**
* 
*/
public class RefAttaches   
{
    //<summary>for this patient only</summary>
    //public static RefAttach[] List;
    //<summary></summary>
    //public static RefAttach Cur;
    //<summary></summary>
    //public static Hashtable HList;//key:refAttachNum, value:RefAttach
    /**
    * For one patient
    */
    public static RefAttach[] refresh(int patNum) throws Exception {
        String command = "SELECT * FROM refattach" + " WHERE patnum = " + patNum.ToString() + " ORDER BY itemorder";
        DataTable table = General.GetTable(command);
        RefAttach[] List = new RefAttach[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //HList=new Hashtable();
            List[i] = new RefAttach();
            List[i].RefAttachNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].ReferralNum = PIn.PInt(table.Rows[i][1].ToString());
            List[i].PatNum = PIn.PInt(table.Rows[i][2].ToString());
            List[i].ItemOrder = PIn.PInt(table.Rows[i][3].ToString());
            List[i].RefDate = PIn.PDate(table.Rows[i][4].ToString());
            List[i].IsFrom = PIn.PBool(table.Rows[i][5].ToString());
        }
        return List;
    }

    //HList.Add(List[i].RefAttachNum,List[i]);
    /**
    * 
    */
    public static boolean isReferralAttached(int referralNum) throws Exception {
        String command = "SELECT * FROM refattach" + " WHERE referralnum = '" + referralNum + "'";
        DataTable table = General.GetTable(command);
        if (table.Rows.Count > 0)
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    /**
    * Returns a list of patient names that are attached to this referral. Used to display in the referral edit window.
    */
    public static String[] getPats(int refNum, boolean IsFrom) throws Exception {
        String command = "SELECT CONCAT(patient.LName,', ',patient.FName) " + "FROM patient,refattach,referral " + "WHERE patient.PatNum=refattach.PatNum " + "AND refattach.ReferralNum=referral.ReferralNum " + "AND refattach.IsFrom=" + POut.pBool(IsFrom) + " AND referral.ReferralNum=" + refNum.ToString();
        //MessageBox.Show(command);
        DataTable table = General.GetTable(command);
        String[] retStr = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retStr[i] = PIn.PString(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Pass in all the refattaches for the patient.  This funtion finds the first referral from a Dr and returns that Dr's name.  Used in specialty practices.  Function is only used right now in the Dr. Ceph bridge.
    */
    public static String getReferringDr(RefAttach[] attachList) throws Exception {
        if (attachList.Length == 0)
        {
            return "";
        }
         
        if (!attachList[0].IsFrom)
        {
            return "";
        }
         
        Referral referral = Referrals.GetReferral(attachList[0].ReferralNum);
        if (referral.PatNum != 0)
        {
            return "";
        }
         
        String retVal = referral.FName + " " + referral.MName + " " + referral.LName;
        if (!StringSupport.equals(referral.Title, ""))
        {
            retVal += ", " + referral.Title;
        }
         
        return retVal;
    }

}


