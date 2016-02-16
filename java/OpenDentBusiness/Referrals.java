//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RefAttach;
import OpenDentBusiness.RefAttaches;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Referrals   
{
    /**
    * All referrals for all patients.  Just as needed.  Cache refresh could be more intelligent and faster.
    */
    private static Referral[] list = new Referral[]();
    //No need to check RemotingRole; no call to db.
    public static Referral[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(Referral[] value) throws Exception {
        list = value;
    }

    /**
    * Refreshes all referrals for all patients.  Need to rework at some point so less memory is consumed.  Also refreshes dynamically, so no need to invalidate local data.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM referral ORDER BY lname";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Referral";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ReferralCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static void update(Referral refer) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), refer);
            return ;
        }
         
        Crud.ReferralCrud.Update(refer);
    }

    /**
    * 
    */
    public static long insert(Referral refer) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            refer.ReferralNum = Meth.GetLong(MethodBase.GetCurrentMethod(), refer);
            return refer.ReferralNum;
        }
         
        return Crud.ReferralCrud.Insert(refer);
    }

    /**
    * 
    */
    public static void delete(Referral refer) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), refer);
            return ;
        }
         
        String command = "DELETE FROM referral " + "WHERE referralnum = '" + refer.ReferralNum + "'";
        Db.nonQ(command);
    }

    private static Referral getFromList(long referralNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].ReferralNum == referralNum)
            {
                return getList()[i];
            }
             
        }
        //couldn't find it, so refresh list and try again
        Referrals.refreshCache();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ReferralNum == referralNum)
            {
                return getList()[i];
            }
             
        }
        return null;
    }

    /**
    * Includes title like DMD on the end.
    */
    public static String getNameLF(long referralNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (referralNum == 0)
        {
            return "";
        }
         
        Referral referral = getFromList(referralNum);
        if (referral == null)
        {
            return "";
        }
         
        String retVal = referral.LName;
        if (!StringSupport.equals(referral.FName, ""))
        {
            retVal += ", " + referral.FName + " " + referral.MName;
        }
         
        if (!StringSupport.equals(referral.Title, ""))
        {
            retVal += ", " + referral.Title;
        }
         
        return retVal;
    }

    //specialty seems to wordy to add here
    /**
    * Includes title, such as DMD.
    */
    public static String getNameFL(long referralNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (referralNum == 0)
        {
            return "";
        }
         
        Referral referral = getFromList(referralNum);
        if (referral == null)
        {
            return "";
        }
         
        return referral.getNameFL();
    }

    /**
    * 
    */
    public static String getPhone(long referralNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (referralNum == 0)
            return "";
         
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ReferralNum == referralNum)
            {
                if (getList()[i].Telephone.Length == 10)
                {
                    return getList()[i].Telephone.Substring(0, 3) + "-" + getList()[i].Telephone.Substring(3, 3) + "-" + getList()[i].Telephone.Substring(6);
                }
                 
                return getList()[i].Telephone;
            }
             
        }
        return "";
    }

    /**
    * Returns a list of Referrals with names similar to the supplied string.  Used in dropdown list from referral field in FormPatientAddAll for faster entry.
    */
    public static List<Referral> getSimilarNames(String referralLName) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Referral> retVal = new List<Referral>();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].LName.ToUpper().IndexOf(referralLName.ToUpper()) == 0)
            {
                retVal.Add(getList()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Gets Referral info from memory. Does not make a call to the database unless needed.
    */
    public static Referral getReferral(long referralNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (referralNum == 0)
        {
            return null;
        }
         
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ReferralNum == referralNum)
            {
                return getList()[i].Copy();
            }
             
        }
        refreshCache();
        for (int i = 0;i < getList().Length;i++)
        {
            //must be outdated
            if (getList()[i].ReferralNum == referralNum)
            {
                return getList()[i].Copy();
            }
             
        }
        throw new ApplicationException("Error.  Referral not found: " + referralNum.ToString());
    }

    /**
    * Gets the first referral "from" for the given patient.  Will return null if no "from" found for patient.
    */
    public static Referral getReferralForPat(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<RefAttach> RefAttachList = RefAttaches.refresh(patNum);
        for (int i = 0;i < RefAttachList.Count;i++)
        {
            if (RefAttachList[i].IsFrom)
            {
                return GetReferral(RefAttachList[i].ReferralNum);
            }
             
        }
        return null;
    }

}


