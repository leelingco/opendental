//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatFieldDef;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PatFieldDefs   
{
    private static PatFieldDef[] list = new PatFieldDef[]();
    /**
    * A list of all allowable patFields.
    */
    //No need to check RemotingRole; no call to db.
    public static PatFieldDef[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(PatFieldDef[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM patfielddef";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "PatFieldDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        setList(new PatFieldDef[table.Rows.Count]);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            getList()[i] = new PatFieldDef();
            getList()[i].PatFieldDefNum = PIn.Long(table.Rows[i][0].ToString());
            getList()[i].FieldName = PIn.String(table.Rows[i][1].ToString());
            getList()[i].FieldType = (PatFieldType)PIn.Int(table.Rows[i][2].ToString());
            getList()[i].PickList = PIn.String(table.Rows[i][3].ToString());
        }
    }

    /**
    * Must supply the old field name so that the patient lists can be updated.
    */
    public static void update(PatFieldDef patFieldDef, String oldFieldName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patFieldDef, oldFieldName);
            return ;
        }
         
        Crud.PatFieldDefCrud.Update(patFieldDef);
        String command = "UPDATE patfield SET FieldName='" + POut.string(patFieldDef.FieldName) + "' " + "WHERE FieldName='" + POut.string(oldFieldName) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(PatFieldDef patFieldDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            patFieldDef.PatFieldDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), patFieldDef);
            return patFieldDef.PatFieldDefNum;
        }
         
        return Crud.PatFieldDefCrud.Insert(patFieldDef);
    }

    /**
    * Surround with try/catch, because it will throw an exception if any patient is using this def.
    */
    public static void delete(PatFieldDef patFieldDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patFieldDef);
            return ;
        }
         
        String command = "SELECT LName,FName FROM patient,patfield WHERE " + "patient.PatNum=patfield.PatNum " + "AND FieldName='" + POut.string(patFieldDef.FieldName) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String s = Lans.g("PatFieldDef","Not allowed to delete. Already in use by ") + table.Rows.Count.ToString() + " " + Lans.g("PatFieldDef","patients, including") + " \r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 5)
                {
                    break;
                }
                 
                s += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString() + "\r\n";
            }
            throw new ApplicationException(s);
        }
         
        command = "DELETE FROM patfielddef WHERE PatFieldDefNum =" + POut.long(patFieldDef.PatFieldDefNum);
        Db.nonQ(command);
    }

    /**
    * GetFieldName returns the field name identified by the field definition number passed as a parameter.
    */
    public static String getFieldName(long patFieldDefNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].PatFieldDefNum == patFieldDefNum)
            {
                return getList()[i].FieldName;
            }
             
        }
        return "";
    }

    /**
    * GetPickListByFieldName returns the pick list identified by the field name passed as a parameter.
    */
    public static String getPickListByFieldName(String FieldName) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getList()[i].FieldName, FieldName))
            {
                return getList()[i].PickList;
            }
             
        }
        return "";
    }

}


