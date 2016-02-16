//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RefAttach;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class RefAttaches   
{
    /**
    * For one patient
    */
    public static List<RefAttach> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RefAttach>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM refattach" + " WHERE PatNum = " + POut.long(patNum) + " ORDER BY itemorder";
        return Crud.RefAttachCrud.SelectMany(command);
    }

    /**
    * For the ReferralsPatient window.  showAll is only used for the referred procs view.
    */
    public static List<RefAttach> refreshFiltered(long patNum, boolean showAll, long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RefAttach>>GetObject(MethodBase.GetCurrentMethod(), patNum, showAll, procNum);
        }
         
        String command = "SELECT * FROM refattach " + "WHERE PatNum = " + POut.long(patNum) + " ";
        if (procNum != 0)
        {
            //for procedure
            if (!showAll)
            {
                //hide regular referrals
                command += "AND ProcNum=" + POut.long(procNum) + " ";
            }
             
        }
         
        command += "ORDER BY ItemOrder";
        return Crud.RefAttachCrud.SelectMany(command);
    }

    /**
    * For FormReferralProckTrack.
    */
    public static List<RefAttach> refreshForReferralProcTrack(DateTime dateFrom, DateTime dateTo, boolean complete) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RefAttach>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo, complete);
        }
         
        String command = "SELECT * FROM refattach " + "WHERE ProcNum IN(SELECT ProcNum FROM procedurelog) " + "AND RefDate>=" + POut.date(dateFrom) + " " + "AND RefDate<=" + POut.date(dateTo) + " ";
        if (!complete)
        {
            command += "AND DateProcComplete=" + POut.Date(DateTime.MinValue) + " ";
        }
         
        command += "ORDER BY RefDate";
        return Crud.RefAttachCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(RefAttach attach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), attach);
            return ;
        }
         
        Crud.RefAttachCrud.Update(attach);
    }

    /**
    * 
    */
    public static long insert(RefAttach attach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            attach.RefAttachNum = Meth.GetLong(MethodBase.GetCurrentMethod(), attach);
            return attach.RefAttachNum;
        }
         
        return Crud.RefAttachCrud.Insert(attach);
    }

    /**
    * 
    */
    public static void delete(RefAttach attach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), attach);
            return ;
        }
         
        String command = "DELETE FROM refattach " + "WHERE refattachnum = '" + attach.RefAttachNum + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static boolean isReferralAttached(long referralNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), referralNum);
        }
         
        String command = "SELECT * FROM refattach" + " WHERE ReferralNum = '" + referralNum + "'";
        DataTable table = Db.getTable(command);
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
    public static String[] getPats(long refNum, boolean IsFrom) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), refNum, IsFrom);
        }
         
        String command = "SELECT CONCAT(CONCAT(patient.LName,', '),patient.FName) " + "FROM patient,refattach,referral " + "WHERE patient.PatNum=refattach.PatNum " + "AND refattach.ReferralNum=referral.ReferralNum " + "AND refattach.IsFrom=" + POut.bool(IsFrom) + " AND referral.ReferralNum=" + refNum.ToString();
        DataTable table = Db.getTable(command);
        String[] retStr = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retStr[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Gets the referral number for this patient.  If multiple, it returns the first one.  If none, it returns 0.  Does not consider referred To.
    */
    public static long getReferralNum(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT ReferralNum " + "FROM refattach " + "WHERE refattach.PatNum =" + POut.long(patNum) + " " + "AND refattach.IsFrom=1 " + "ORDER BY ItemOrder ";
        command = DbHelper.limitOrderBy(command,1);
        return PIn.long(Db.getScalar(command));
    }

}


