//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SigElement;
import OpenDentBusiness.SigElements;
import OpenDentBusiness.Signalod;
import OpenDentBusiness.Signalods;
import OpenDentBusiness.SignalType;
import OpenDentBusiness.Task;

/**
* 
*/
public class Signalods   
{
    /**
    * Gets all Signals and Acks Since a given DateTime.  If it can't connect to the database, then it no longer throws an error, but instead returns a list of length 0.  Remeber that the supplied dateTime is server time.  This has to be accounted for.
    */
    public static List<Signalod> refreshTimed(DateTime sinceDateT) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Signalod>>GetObject(MethodBase.GetCurrentMethod(), sinceDateT);
        }
         
        String command = "SELECT * FROM signalod " + "WHERE SigDateTime>" + POut.dateT(sinceDateT) + " " + "OR AckTime>" + POut.dateT(sinceDateT) + " " + "ORDER BY SigDateTime";
        //note: this might return an occasional row that has both times newer.
        List<Signalod> sigList = new List<Signalod>();
        try
        {
            sigList = Crud.SignalodCrud.SelectMany(command);
            sigList.Sort();
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        //we don't want an error message to show, because that can cause a cascade of a large number of error messages.
        SigElement[] sigElementsAll = SigElements.GetElements(sigList);
        for (int i = 0;i < sigList.Count;i++)
        {
            sigList[i].ElementList = SigElements.GetForSig(sigElementsAll, sigList[i].SignalNum);
        }
        return sigList;
    }

    /**
    * Process all Signals and Acks Since a given DateTime.  Only to be used by OpenDentalWebService.  Returns latest valid signal Date/Time.  Can throw exception.
    */
    public static void refreshForWeb(RefSupport<DateTime> sinceDateT) throws Exception {
        try
        {
            //No need to check RemotingRole; no call to db.
            if (sinceDateT.getValue().Year < 1880)
            {
                sinceDateT.setValue(MiscData.getNowDateTime());
            }
             
            //Get all invalid types since given time.
            List<int> itypes = Signalods.getInvalidTypes(Signalods.refreshTimed(sinceDateT.getValue()));
            if (itypes.Count <= 0)
            {
                return ;
            }
             
            String itypesStr = "";
            for (int i = 0;i < itypes.Count;i++)
            {
                if (i > 0)
                {
                    itypesStr += ",";
                }
                 
                itypesStr += ((int)itypes[i]).ToString();
            }
            //Refresh the cache for the given invalid types.
            Cache.refreshCache(itypesStr);
            sinceDateT.setValue(OpenDentBusiness.MiscData.getNowDateTime());
        }
        catch (Exception e)
        {
            //Most likely cause for an exception here would be a thread collision between 2 consumers trying to refresh the cache at the exact same instant.
            //There is a chance that performing as subsequent refresh here would cause yet another collision but it's the best we can do without redesigning the entire cache pattern.
            Cache.refresh(InvalidType.AllLocal);
            throw new Exception("Server cache may be invalid. Please try again. Error: " + e.Message);
        }
    
    }

    /**
    * This excludes all Invalids.  It is only concerned with text and button messages.  It includes all messages, whether acked or not.  It's up to the UI to filter out acked if necessary.  Also includes all unacked messages regardless of date.
    */
    public static List<Signalod> refreshFullText(DateTime sinceDateT) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Signalod>>GetObject(MethodBase.GetCurrentMethod(), sinceDateT);
        }
         
        //always include all unacked.
        String command = "SELECT * FROM signalod " + "WHERE (SigDateTime>" + POut.dateT(sinceDateT) + " " + "OR AckTime>" + POut.dateT(sinceDateT) + " " + "OR AckTime<" + POut.date(new DateTime(1880, 1, 1),true) + ") " + "AND SigType=" + POut.Long(((Enum)SignalType.Button).ordinal()) + " ORDER BY SigDateTime";
        //note: this might return an occasional row that has both times newer.
        List<Signalod> sigList = new List<Signalod>();
        try
        {
            sigList = Crud.SignalodCrud.SelectMany(command);
            sigList.Sort();
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        //we don't want an error message to show, because that can cause a cascade of a large number of error messages.
        SigElement[] sigElementsAll = SigElements.GetElements(sigList);
        for (int i = 0;i < sigList.Count;i++)
        {
            sigList[i].ElementList = SigElements.GetForSig(sigElementsAll, sigList[i].SignalNum);
        }
        return sigList;
    }

    //retVal;
    /**
    * Only used when starting up to get the current button state.  Only gets unacked messages.  There may well be extra and useless messages included.  But only the lights will be used anyway, so it doesn't matter.
    */
    public static List<Signalod> refreshCurrentButState() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Signalod>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //buttons only
        String command = "SELECT * FROM signalod " + "WHERE SigType=0 " + "AND AckTime<" + POut.date(new DateTime(1880, 1, 1),true) + " " + "ORDER BY SigDateTime";
        List<Signalod> sigList = new List<Signalod>();
        try
        {
            sigList = Crud.SignalodCrud.SelectMany(command);
            sigList.Sort();
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        //we don't want an error message to show, because that can cause a cascade of a large number of error messages.
        SigElement[] sigElementsAll = SigElements.GetElements(sigList);
        for (int i = 0;i < sigList.Count;i++)
        {
            sigList[i].ElementList = SigElements.GetForSig(sigElementsAll, sigList[i].SignalNum);
        }
        return sigList;
    }

    /**
    * 
    */
    public static void update(Signalod sig) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sig);
            return ;
        }
         
        Crud.SignalodCrud.Update(sig);
    }

    /**
    * 
    */
    public static long insert(Signalod sig) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sig.SignalNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sig);
            return sig.SignalNum;
        }
         
        //we need to explicitly get the server time in advance rather than using NOW(),
        //because we need to update the signal object soon after creation.
        sig.SigDateTime = OpenDentBusiness.MiscData.getNowDateTime();
        return Crud.SignalodCrud.Insert(sig);
    }

    //<summary>There's no such thing as deleting a signal</summary>
    /*public void Delete(){
    			string command= "DELETE from Signalod WHERE SignalNum = '"
    				+POut.PInt(SignalNum)+"'";
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /**
    * After a refresh, this is used to determine whether the Appt Module needs to be refreshed.  Must supply the current date showing as well as the recently retrieved signal list.
    */
    public static boolean apptNeedsRefresh(List<Signalod> signalList, DateTime dateTimeShowing) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<String> iTypeList = new List<String>();
        for (int i = 0;i < signalList.Count;i++)
        {
            iTypeList = new List<String>(signalList[i].ITypes.Split(','));
            if (iTypeList.Contains((((Enum)InvalidType.Date).ordinal()).ToString()) && signalList[i].DateViewing.Date == dateTimeShowing)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * After a refresh, this is used to determine whether the Current user has received any new tasks through subscription.  Must supply the current usernum as well as the recently retrieved signal list.  The signal list will include any task changes including status changes and deletions.  This will be called twice, once with isPopup=true and once with isPopup=false.
    */
    public static List<Task> getNewTaskPopupsThisUser(List<Signalod> signalList, long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), signalList, userNum);
        }
         
        List<Signalod> sigListFiltered = new List<Signalod>();
        for (int i = 0;i < signalList.Count;i++)
        {
            if (signalList[i].ITypes == (((Enum)InvalidType.TaskPopup).ordinal()).ToString())
            {
                sigListFiltered.Add(signalList[i]);
            }
             
        }
        if (sigListFiltered.Count == 0)
        {
            return new List<Task>();
        }
         
        //no task popup signals
        String command = "SELECT task.* FROM taskancestor,task,tasklist,tasksubscription " + "WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum " + "AND tasksubscription.TaskListNum=tasklist.TaskListNum " + "AND tasksubscription.UserNum=" + POut.long(userNum) + " AND (";
        for (int i = 0;i < sigListFiltered.Count;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "task.TaskNum= " + POut.Long(sigListFiltered[i].TaskNum);
        }
        command += ")";
        return Crud.TaskCrud.SelectMany(command);
    }

    /**
    * After a refresh, this is used to get a list containing all flags of types that need to be refreshed.   Types of Date and Task are not included.  Because type are an enumeration, the returned list is int32, not int64.
    */
    public static List<int> getInvalidTypes(List<Signalod> signalodList) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<int> retVal = new List<int>();
        String[] strArray = new String[]();
        for (int i = 0;i < signalodList.Count;i++)
        {
            if (signalodList[i].SigType != SignalType.Invalid)
            {
                continue;
            }
             
            if (signalodList[i].ITypes == (((Enum)InvalidType.Date).ordinal()).ToString())
            {
                continue;
            }
             
            if (signalodList[i].ITypes == (((Enum)InvalidType.Task).ordinal()).ToString())
            {
                continue;
            }
             
            if (signalodList[i].ITypes == (((Enum)InvalidType.TaskPopup).ordinal()).ToString())
            {
                continue;
            }
             
            strArray = signalodList[i].ITypes.Split(',');
            for (int t = 0;t < strArray.Length;t++)
            {
                if (!retVal.Contains(PIn.Int(strArray[t])))
                {
                    retVal.Add(PIn.Int(strArray[t]));
                }
                 
            }
        }
        return retVal;
    }

    /**
    * After a refresh, this gets a list of only the button signals.
    */
    public static List<Signalod> getButtonSigs(List<Signalod> signalodList) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Signalod> list = new List<Signalod>();
        for (int i = 0;i < signalodList.Count;i++)
        {
            if (signalodList[i].SigType != SignalType.Button)
            {
                continue;
            }
             
            list.Add(signalodList[i]);
        }
        return list;
    }

    /**
    * When user clicks on a colored light, they intend to ack it to turn it off.  This acks all signals with the specified index.  This is in case multiple signals have been created from different workstations.  This acks them all in one shot.  Must specify a time because you only want to ack signals earlier than the last time this workstation was refreshed.  A newer signal would not get acked.
    * If this seems slow, then I will need to check to make sure all these tables are properly indexed.
    */
    public static void ackButton(int buttonIndex, DateTime time) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), buttonIndex, time);
            return ;
        }
         
        //FIXME:UPDATE-MULTIPLE-TABLES
        /*string command= "UPDATE signalod,sigelement,sigelementdef "
        				+"SET signalod.AckTime = ";
        				if(FormChooseDatabase.DBtype==DatabaseType.Oracle) {
        					command+="(SELECT CURRENT_TIMESTAMP FROM DUAL)";
        				}else{//Assume MySQL
        					command+="NOW()";
        				}
        				command+=" "
        				+"WHERE signalod.AckTime < '1880-01-01' "
        				+"AND SigDateTime <= '"+POut.PDateT(time)+"' "
        				+"AND signalod.SignalNum=sigelement.SignalNum "
        				+"AND sigelement.SigElementDefNum=sigelementdef.SigElementDefNum "
        				+"AND sigelementdef.LightRow="+POut.PInt(buttonIndex);
        			Db.NonQ(command);*/
        //Rewritten so that the SQL is compatible with both Oracle and MySQL.
        String command = "SELECT signalod.SignalNum FROM signalod,sigelement,sigelementdef " + "WHERE signalod.AckTime < " + POut.date(new DateTime(1880, 1, 1),true) + " " + "AND SigDateTime <= " + POut.dateT(time) + " " + "AND signalod.SignalNum=sigelement.SignalNum " + "AND sigelement.SigElementDefNum=sigelementdef.SigElementDefNum " + "AND sigelementdef.LightRow=" + POut.Long(buttonIndex);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        command = "UPDATE signalod SET AckTime = ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command += POut.dateT(OpenDentBusiness.MiscData.getNowDateTime());
        }
        else
        {
            //Assume MySQL
            command += "NOW()";
        } 
        command += " WHERE ";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            command += "SignalNum=" + table.Rows[i][0].ToString();
            if (i < table.Rows.Count - 1)
            {
                command += " OR ";
            }
             
        }
        Db.nonQ(command);
    }

    /**
    * Won't work with InvalidType.Date, InvalidType.Task, or InvalidType.TaskPopup  yet.
    */
    public static void setInvalid(InvalidType... itypes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), itypes);
            return ;
        }
         
        String itypeString = "";
        for (int i = 0;i < itypes.Length;i++)
        {
            if (i > 0)
            {
                itypeString += ",";
            }
             
            itypeString += ((int)itypes[i]).ToString();
        }
        Signalod sig = new Signalod();
        sig.ITypes = itypeString;
        sig.DateViewing = DateTime.MinValue;
        sig.SigType = SignalType.Invalid;
        sig.TaskNum = 0;
        insert(sig);
    }

}


