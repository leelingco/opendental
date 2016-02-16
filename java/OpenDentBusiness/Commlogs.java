//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;

/**
* 
*/
public class Commlogs   
{
    /**
    * Gets all items for the current patient ordered by date.
    */
    public static List<Commlog> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Commlog>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM commlog" + " WHERE PatNum = '" + patNum + "'" + " ORDER BY CommDateTime";
        return Crud.CommlogCrud.SelectMany(command);
    }

    /**
    * Gets one commlog item from database.
    */
    public static Commlog getOne(long commlogNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Commlog>GetObject(MethodBase.GetCurrentMethod(), commlogNum);
        }
         
        return Crud.CommlogCrud.SelectOne(commlogNum);
    }

    /**
    * If a commlog exists with today's date for the current user and has no stop time, then that commlog is returned so it can be reopened.  Otherwise, return null.
    */
    public static Commlog getIncompleteEntry(long userNum, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Commlog>GetObject(MethodBase.GetCurrentMethod(), userNum, patNum);
        }
         
        //no need for Oracle compatibility
        //support call or chat, DefNums
        String command = "SELECT * FROM commlog WHERE DATE(CommDateTime)=CURDATE() " + "AND UserNum=" + POut.long(userNum) + " " + "AND PatNum=" + POut.long(patNum) + " " + "AND (CommType=292 OR CommType=441) " + "AND Mode_=" + POut.int(((Enum)CommItemMode.Phone).ordinal()) + " " + "AND DateTimeEnd < '1880-01-01' LIMIT 1";
        return Crud.CommlogCrud.SelectOne(command);
    }

    //mode=phone
    /**
    * 
    */
    public static long insert(Commlog comm) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            comm.CommlogNum = Meth.GetLong(MethodBase.GetCurrentMethod(), comm);
            return comm.CommlogNum;
        }
         
        return Crud.CommlogCrud.Insert(comm);
    }

    /**
    * 
    */
    public static void update(Commlog comm) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), comm);
            return ;
        }
         
        Crud.CommlogCrud.Update(comm);
    }

    /**
    * 
    */
    public static void delete(Commlog comm) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), comm);
            return ;
        }
         
        Crud.CommlogCrud.Delete(comm.CommlogNum);
    }

    /**
    * Used when printing or emailing recall to make a commlog entry without any display.
    */
    public static void insertForRecall(long patNum, CommItemMode _mode, int numberOfReminders, long defNumNewStatus) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, _mode, numberOfReminders, defNumNewStatus);
            return ;
        }
         
        long recallType = Commlogs.getTypeAuto(CommItemTypeAuto.RECALL);
        String command = new String();
        String datesql = "CURDATE()";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            datesql = "(SELECT CURRENT_DATE FROM dual)";
        }
         
        if (recallType != 0)
        {
            command = "SELECT COUNT(*) FROM commlog WHERE ";
            command += DbHelper.dateColumn("CommDateTime") + " = " + datesql;
            command += " AND PatNum=" + POut.long(patNum) + " AND CommType=" + POut.long(recallType) + " AND Mode_=" + POut.Long(((Enum)_mode).ordinal()) + " AND SentOrReceived=1";
            if (!StringSupport.equals(Db.getCount(command), "0"))
            {
                return ;
            }
             
        }
         
        Commlog com = new Commlog();
        com.PatNum = patNum;
        com.CommDateTime = DateTime.Now;
        com.CommType = recallType;
        com.Mode_ = _mode;
        com.SentOrReceived = CommSentOrReceived.Sent;
        com.Note = "";
        if (numberOfReminders == 0)
        {
            com.Note = Lans.g("FormRecallList","Recall reminder.");
        }
        else if (numberOfReminders == 1)
        {
            com.Note = Lans.g("FormRecallList","Second recall reminder.");
        }
        else if (numberOfReminders == 2)
        {
            com.Note = Lans.g("FormRecallList","Third recall reminder.");
        }
        else
        {
            com.Note = Lans.g("FormRecallList","Recall reminder:") + " " + (numberOfReminders + 1).ToString();
        }   
        if (defNumNewStatus == 0)
        {
            com.Note += "  " + Lans.g("Commlogs","Status None");
        }
        else
        {
            com.Note += "  " + DefC.getName(DefCat.RecallUnschedStatus,defNumNewStatus);
        } 
        com.UserNum = Security.getCurUser().UserNum;
        insert(com);
    }

    /**
    * Returns a defnum.  If no match, then it returns the first one in the list in that category.
    */
    public static long getTypeAuto(CommItemTypeAuto typeauto) throws Exception {
        for (int i = 0;i < DefC.getLong()[((Enum)DefCat.CommLogTypes).ordinal()].Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (DefC.getLong()[((Enum)DefCat.CommLogTypes).ordinal()][i].ItemValue == typeauto.ToString())
            {
                return DefC.getLong()[((Enum)DefCat.CommLogTypes).ordinal()][i].DefNum;
            }
             
        }
        if (DefC.getLong()[((Enum)DefCat.CommLogTypes).ordinal()].Length > 0)
        {
            return DefC.getLong()[((Enum)DefCat.CommLogTypes).ordinal()][0].DefNum;
        }
         
        return 0;
    }

    public static int getRecallUndoCount(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), date);
        }
         
        String command = "SELECT COUNT(*) FROM commlog " + "WHERE " + DbHelper.dateColumn("CommDateTime") + " = " + POut.date(date) + " " + "AND (SELECT ItemValue FROM definition WHERE definition.DefNum=commlog.CommType) ='" + CommItemTypeAuto.RECALL.ToString() + "'";
        return PIn.int(Db.getScalar(command));
    }

    public static void recallUndo(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), date);
            return ;
        }
         
        String command = "DELETE FROM commlog " + "WHERE " + DbHelper.dateColumn("CommDateTime") + " = " + POut.date(date) + " " + "AND (SELECT ItemValue FROM definition WHERE definition.DefNum=commlog.CommType) ='" + CommItemTypeAuto.RECALL.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Gets all commlogs for family that contain a DateTimeEnd entry.  Used internally to keep track of how long calls lasted.
    */
    public static List<Commlog> getTimedCommlogsForPat(long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Commlog>>GetObject(MethodBase.GetCurrentMethod(), guarantor);
        }
         
        String command = "SELECT commlog.* FROM commlog " + "INNER JOIN patient ON commlog.PatNum=patient.PatNum AND patient.Guarantor=" + POut.long(guarantor) + " " + "WHERE " + DbHelper.year("commlog.DateTimeEnd") + ">1";
        return Crud.CommlogCrud.SelectMany(command);
    }

}


