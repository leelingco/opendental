//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.POut;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class OrionProcs   
{
    /*
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all OrionProcs.</summary>
    		private static List<OrionProc> listt;
    		///<summary>A list of all OrionProcs.</summary>
    		public static List<OrionProc> Listt{
    			get {
    				if(listt==null) {
    					RefreshCache();
    				}
    				return listt;
    			}
    			set {
    				listt=value;
    			}
    		}
    		///<summary></summary>
    		public static DataTable RefreshCache(){
    			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    			string command="SELECT * FROM orionproc ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="OrionProc";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.OrionProcCrud.TableToList(table);
    		}
    		*/
    /**
    * Gets one OrionProc from the db.
    */
    public static OrionProc getOne(long orionProcNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OrionProc>GetObject(MethodBase.GetCurrentMethod(), orionProcNum);
        }
         
        return Crud.OrionProcCrud.SelectOne(orionProcNum);
    }

    /**
    * Gets one OrionProc from the db by ProcNum
    */
    public static OrionProc getOneByProcNum(long ProcNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OrionProc>GetObject(MethodBase.GetCurrentMethod(), ProcNum);
        }
         
        String command = "SELECT * FROM orionproc " + "WHERE ProcNum=" + POut.long(ProcNum) + " " + DbHelper.limitAnd(1);
        return Crud.OrionProcCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(OrionProc orionProc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            orionProc.OrionProcNum = Meth.GetLong(MethodBase.GetCurrentMethod(), orionProc);
            return orionProc.OrionProcNum;
        }
         
        return Crud.OrionProcCrud.Insert(orionProc);
    }

    /**
    * 
    */
    public static void update(OrionProc orionProc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), orionProc);
            return ;
        }
         
        Crud.OrionProcCrud.Update(orionProc);
    }

    public static DataTable getCancelledScheduleDateByToothOrSurf(long PatNum, String ToothNum, String Surf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum, ToothNum, Surf);
        }
         
        String optionalclause = "";
        if (StringSupport.equals(POut.string(ToothNum), ""))
        {
            optionalclause = " AND procedurelog.Surf='" + POut.string(Surf) + "'";
        }
         
        String command = "SELECT orionproc.DateScheduleBy,procedurelog.ToothNum,procedurelog.Surf " + "FROM orionproc " + "LEFT JOIN procedurelog ON orionproc.ProcNum=procedurelog.ProcNum " + "WHERE procedurelog.PatNum=" + POut.long(PatNum) + " AND orionproc.Status2=128 " + " AND procedurelog.ToothNum='" + POut.string(ToothNum) + "'" + optionalclause + " AND " + DbHelper.year("orionproc.DateScheduleBy") + ">1880" + " ORDER BY orionproc.DateScheduleBy ";
        command = DbHelper.limitOrderBy(command,1);
        return Db.getTable(command);
    }

    /**
    * Loops through every procedure attached to an appt and sets the Status2 to complete.
    */
    public static void setCompleteInAppt(List<Procedure> procsInAppt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procsInAppt);
            return ;
        }
         
        OrionProc orionProc;
        for (int i = 0;i < procsInAppt.Count;i++)
        {
            orionProc = GetOneByProcNum(procsInAppt[i].ProcNum);
            orionProc.Status2 = OrionStatus.C;
            update(orionProc);
        }
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<OrionProc> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<OrionProc>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM orionproc WHERE PatNum = "+POut.Long(patNum);
			return Crud.OrionProcCrud.SelectMany(command);
		}
		
		///<summary></summary>
		public static void Delete(long orionProcNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),orionProcNum);
				return;
			}
			string command= "DELETE FROM orionproc WHERE OrionProcNum = "+POut.Long(orionProcNum);
			Db.NonQ(command);
		}
		
		
		*/