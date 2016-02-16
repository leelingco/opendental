//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptFieldDef;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ApptFieldDefs   
{
    /**
    * A list of all ApptFieldDefs.
    */
    private static List<ApptFieldDef> listt = new List<ApptFieldDef>();
    /**
    * A list of all ApptFieldDefs.
    */
    public static List<ApptFieldDef> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<ApptFieldDef> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM apptfielddef ORDER BY FieldName";
        //important to order so that listt matches order returned in other queries.
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ApptFieldDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ApptFieldDefCrud.TableToList(table);
    }

    /**
    * Must supply the old field name so that the apptFields attached to appointments can be updated.  Will throw exception if new FieldName is already in use.
    */
    public static void update(ApptFieldDef apptFieldDef, String oldFieldName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptFieldDef, oldFieldName);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM apptfielddef WHERE FieldName='" + POut.string(apptFieldDef.FieldName) + "' " + "AND ApptFieldDefNum != " + POut.long(apptFieldDef.ApptFieldDefNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormApptFieldDefEdit","Field name already in use."));
        }
         
        Crud.ApptFieldDefCrud.Update(apptFieldDef);
        command = "UPDATE apptfield SET FieldName='" + POut.string(apptFieldDef.FieldName) + "' " + "WHERE FieldName='" + POut.string(oldFieldName) + "'";
        Db.nonQ(command);
    }

    /**
    * Surround with try/catch in case field name already in use.
    */
    public static long insert(ApptFieldDef apptFieldDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            apptFieldDef.ApptFieldDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), apptFieldDef);
            return apptFieldDef.ApptFieldDefNum;
        }
         
        String command = "SELECT COUNT(*) FROM apptfielddef WHERE FieldName='" + POut.string(apptFieldDef.FieldName) + "'";
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormApptFieldDefEdit","Field name already in use."));
        }
         
        return Crud.ApptFieldDefCrud.Insert(apptFieldDef);
    }

    /**
    * Surround with try/catch, because it will throw an exception if any appointment is using this def.
    */
    public static void delete(ApptFieldDef apptFieldDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptFieldDef);
            return ;
        }
         
        String command = "SELECT LName,FName,AptDateTime " + "FROM patient,apptfield,appointment WHERE " + "patient.PatNum=appointment.PatNum " + "AND appointment.AptNum=apptfield.AptNum " + "AND FieldName='" + POut.string(apptFieldDef.FieldName) + "'";
        DataTable table = Db.getTable(command);
        DateTime aptDateTime = new DateTime();
        if (table.Rows.Count > 0)
        {
            String s = Lans.g("FormApptFieldDefEdit","Not allowed to delete. Already in use by ") + table.Rows.Count.ToString() + " " + Lans.g("FormApptFieldDefEdit","appointments, including") + " \r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                aptDateTime = PIn.DateT(table.Rows[i]["AptDateTime"].ToString());
                s += table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + POut.dateT(aptDateTime,false) + "\r\n";
            }
            throw new ApplicationException(s);
        }
         
        command = "DELETE FROM apptfielddef WHERE ApptFieldDefNum =" + POut.long(apptFieldDef.ApptFieldDefNum);
        Db.nonQ(command);
    }

    /*
    		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
    		///<summary></summary>
    		public static List<ApptFieldDef> Refresh(long patNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<ApptFieldDef>>(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command="SELECT * FROM apptfielddef WHERE PatNum = "+POut.Long(patNum);
    			return Crud.ApptFieldDefCrud.SelectMany(command);
    		}
    		///<summary>Gets one ApptFieldDef from the db.</summary>
    		public static ApptFieldDef GetOne(long apptFieldDefNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetObject<ApptFieldDef>(MethodBase.GetCurrentMethod(),apptFieldDefNum);
    			}
    			return Crud.ApptFieldDefCrud.SelectOne(apptFieldDefNum);
    		}
    		*/
    public static String getFieldName(long apptFieldDefNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListt()[i].ApptFieldDefNum == apptFieldDefNum)
            {
                return getListt()[i].FieldName;
            }
             
        }
        return "";
    }

    /**
    * GetPickListByFieldName returns the pick list identified by the field name passed as a parameter.
    */
    public static String getPickListByFieldName(String FieldName) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListt()[i].FieldName, FieldName))
            {
                return getListt()[i].PickList;
            }
             
        }
        return "";
    }

    /**
    * Returns true if there are any duplicate field names in the entire apptfielddef table.
    */
    public static boolean hasDuplicateFieldNames() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT COUNT(*) FROM apptfielddef GROUP BY FieldName HAVING COUNT(FieldName) > 1";
        return (!StringSupport.equals(Db.getScalar(command), ""));
    }

}


