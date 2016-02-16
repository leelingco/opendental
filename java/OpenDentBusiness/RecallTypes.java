//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Interval;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RecallTrigger;
import OpenDentBusiness.RecallTriggers;
import OpenDentBusiness.RecallType;
import OpenDentBusiness.RecallTypeC;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class RecallTypes   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM recalltype ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "RecallType";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<RecallType> list = Crud.RecallTypeCrud.TableToList(table);
        //reorder rows for better usability
        RecallTypeC.setListt(new List<RecallType>());
        for (int i = 0;i < list.Count;i++)
        {
            if (list[i].RecallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialProphy))
            {
                RecallTypeC.getListt().Add(list[i]);
                break;
            }
             
        }
        for (int i = 0;i < list.Count;i++)
        {
            if (list[i].RecallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialChildProphy))
            {
                RecallTypeC.getListt().Add(list[i]);
                break;
            }
             
        }
        for (int i = 0;i < list.Count;i++)
        {
            if (list[i].RecallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialPerio))
            {
                RecallTypeC.getListt().Add(list[i]);
                break;
            }
             
        }
        for (int i = 0;i < list.Count;i++)
        {
            //now add the rest
            if (!RecallTypeC.getListt().Contains(list[i]))
            {
                RecallTypeC.getListt().Add(list[i]);
            }
             
        }
    }

    /**
    * 
    */
    public static long insert(RecallType recallType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            recallType.RecallTypeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), recallType);
            return recallType.RecallTypeNum;
        }
         
        return Crud.RecallTypeCrud.Insert(recallType);
    }

    /**
    * 
    */
    public static void update(RecallType recallType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recallType);
            return ;
        }
         
        Crud.RecallTypeCrud.Update(recallType);
    }

    public static String getDescription(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (recallTypeNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            if (RecallTypeC.getListt()[i].RecallTypeNum == recallTypeNum)
            {
                return RecallTypeC.getListt()[i].Description;
            }
             
        }
        return "";
    }

    public static Interval getInterval(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (recallTypeNum == 0)
        {
            return new Interval(0,0,0,0);
        }
         
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            if (RecallTypeC.getListt()[i].RecallTypeNum == recallTypeNum)
            {
                return RecallTypeC.getListt()[i].DefaultInterval;
            }
             
        }
        return new Interval(0,0,0,0);
    }

    /**
    * Returns a collection of proccodes (D####).  Count could be zero.
    */
    public static List<String> getProcs(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<String> retVal = new List<String>();
        if (recallTypeNum == 0)
        {
            return retVal;
        }
         
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            if (RecallTypeC.getListt()[i].RecallTypeNum == recallTypeNum)
            {
                if (StringSupport.equals(RecallTypeC.getListt()[i].Procedures, ""))
                {
                    return retVal;
                }
                 
                String[] strArray = RecallTypeC.getListt()[i].Procedures.Split(',');
                retVal.AddRange(strArray);
                return retVal;
            }
             
        }
        return retVal;
    }

    /**
    * Also makes sure both types are defined as special types.
    */
    public static boolean perioAndProphyBothHaveTriggers() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (RecallTypes.getPerioType() == 0 || RecallTypes.getProphyType() == 0)
        {
            return false;
        }
         
        if (RecallTriggers.getForType(RecallTypes.getPerioType()).Count == 0)
        {
            return false;
        }
         
        if (RecallTriggers.getForType(RecallTypes.getProphyType()).Count == 0)
        {
            return false;
        }
         
        return true;
    }

    public static String getTimePattern(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (recallTypeNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            if (RecallTypeC.getListt()[i].RecallTypeNum == recallTypeNum)
            {
                return RecallTypeC.getListt()[i].TimePattern;
            }
             
        }
        return "";
    }

    public static String getSpecialTypeStr(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialProphy))
        {
            return Lans.g("FormRecallTypeEdit","Prophy");
        }
         
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialChildProphy))
        {
            return Lans.g("FormRecallTypeEdit","ChildProphy");
        }
         
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialPerio))
        {
            return Lans.g("FormRecallTypeEdit","Perio");
        }
         
        return "";
    }

    public static boolean isSpecialRecallType(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialProphy))
        {
            return true;
        }
         
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialChildProphy))
        {
            return true;
        }
         
        if (recallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialPerio))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Gets a list of all active recall types.  Those without triggers are excluded.  Perio and Prophy are both included.  One of them should later be removed from the collection.
    */
    public static List<RecallType> getActive() throws Exception {
        //No need to check RemotingRole; no call to db.
        List<RecallType> retVal = new List<RecallType>();
        List<RecallTrigger> triggers = new List<RecallTrigger>();
        for (int i = 0;i < RecallTypeC.getListt().Count;i++)
        {
            triggers = RecallTriggers.GetForType(RecallTypeC.getListt()[i].RecallTypeNum);
            if (triggers.Count > 0)
            {
                retVal.Add(RecallTypeC.getListt()[i].Copy());
            }
             
        }
        return retVal;
    }

    /*
    		///<summary>Gets a list of all inactive recall types.  Only those without triggers are included.</summary>
    		public static List<RecallType> GetInactive(){
    			//No need to check RemotingRole; no call to db.
    			List<RecallType> retVal=new List<RecallType>();
    			List<RecallTrigger> triggers;
    			for(int i=0;i<RecallTypeC.Listt.Count;i++){
    				triggers=RecallTriggers.GetForType(RecallTypeC.Listt[i].RecallTypeNum);
    				if(triggers.Count==0){
    					retVal.Add(RecallTypeC.Listt[i].Clone());
    				}
    			}
    			return retVal;
    		}*/
    /**
    * Gets the pref table RecallTypeSpecialProphy RecallTypeNum.
    */
    //No need to check RemotingRole; no call to db.
    public static long getProphyType() throws Exception {
        return PrefC.getLong(PrefName.RecallTypeSpecialProphy);
    }

    /**
    * Gets the pref table RecallTypeSpecialPerio RecallTypeNum.
    */
    //No need to check RemotingRole; no call to db.
    public static long getPerioType() throws Exception {
        return PrefC.getLong(PrefName.RecallTypeSpecialPerio);
    }

    /**
    * Gets the pref table RecallTypeSpecialChildProphy RecallTypeNum.
    */
    //No need to check RemotingRole; no call to db.
    public static long getChildProphyType() throws Exception {
        return PrefC.getLong(PrefName.RecallTypeSpecialChildProphy);
    }

    /**
    * Deletes the current recalltype and recalltrigger tables and fills them with our default.  Typically ran to switch T codes to D codes.
    */
    public static void setToDefault() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "DELETE FROM recalltype";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,TimePattern,Procedures) VALUES (1,'Prophy',393217,'/XXXX/','D0120,D1110')";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,TimePattern,Procedures) VALUES (2,'Child Prophy',0,'XXX','D0120,D1120,D1208')";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,TimePattern,Procedures) VALUES (3,'Perio',262144,'/XXXX/','D4910')";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,Procedures) VALUES (4,'4BW',16777216,'D0274')";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,Procedures) VALUES (5,'Pano',83886080,'D0330')";
        Db.nonQ(command);
        command = "INSERT INTO recalltype (RecallTypeNum,Description,DefaultInterval,Procedures) VALUES (6,'FMX',83886080,'D0210')";
        Db.nonQ(command);
        command = "DELETE FROM recalltrigger";
        Db.nonQ(command);
        //command="INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (1,1,"+ProcedureCodes.GetCodeNum("D0415")+")";//collection of microorg for culture
        //Db.NonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (1,1," + ProcedureCodes.getCodeNum("D0150") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (2,4," + ProcedureCodes.getCodeNum("D0274") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (3,5," + ProcedureCodes.getCodeNum("D0330") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (4,6," + ProcedureCodes.getCodeNum("D0210") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (5,1," + ProcedureCodes.getCodeNum("D1110") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (6,1," + ProcedureCodes.getCodeNum("D1120") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (7,3," + ProcedureCodes.getCodeNum("D4910") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (8,3," + ProcedureCodes.getCodeNum("D4341") + ")";
        Db.nonQ(command);
        command = "INSERT INTO recalltrigger (RecallTriggerNum,RecallTypeNum,CodeNum) VALUES (9,1," + ProcedureCodes.getCodeNum("D0120") + ")";
        Db.nonQ(command);
        //Update the special types in preference table.
        command = "UPDATE preference SET ValueString='1' WHERE PrefName='RecallTypeSpecialProphy'";
        Db.nonQ(command);
        command = "UPDATE preference SET ValueString='2' WHERE PrefName='RecallTypeSpecialChildProphy'";
        Db.nonQ(command);
        command = "UPDATE preference SET ValueString='3' WHERE PrefName='RecallTypeSpecialPerio'";
        Db.nonQ(command);
        command = "UPDATE preference SET ValueString='1,2,3' WHERE PrefName='RecallTypesShowingInList'";
        Db.nonQ(command);
    }

}


