//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.County;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Counties   
{
    /**
    * Gets county names similar to the one provided.
    */
    public static County[] refresh(String name) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<County[]>GetObject(MethodBase.GetCurrentMethod(), name);
        }
         
        String command = "SELECT * from county " + "WHERE CountyName LIKE '" + name + "%' " + "ORDER BY CountyName";
        List<County> list = Crud.CountyCrud.SelectMany(command);
        for (int i = 0;i < list.Count;i++)
        {
            list[i].OldCountyName = list[i].CountyName;
        }
        return list.ToArray();
    }

    /**
    * 
    */
    public static County[] refresh() throws Exception {
        return refresh("");
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Gets an array of strings containing all the counties in alphabetical order.  Used for the screening interface which must be simpler than the usual interface.
    */
    public static String[] getListNames() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT CountyName from county " + "ORDER BY CountyName";
        DataTable table = Db.getTable(command);
        String[] ListNames = new String[table.Rows.Count];
        for (int i = 0;i < ListNames.Length;i++)
        {
            ListNames[i] = PIn.String(table.Rows[i]["CountyName"].ToString());
        }
        return ListNames;
    }

    /**
    * Need to make sure Countyname not already in db.
    */
    public static long insert(County county) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), county);
        }
         
        return Crud.CountyCrud.Insert(county);
    }

    /**
    * Updates the Countyname and code in the County table, and also updates all patients that were using the oldCounty name.
    */
    public static void update(County county) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), county);
            return ;
        }
         
        //Can't use CRUD here because we're updating by the OldCountyName
        String command = "UPDATE county SET " + "CountyName ='" + POut.string(county.CountyName) + "'" + ",CountyCode ='" + POut.string(county.CountyCode) + "'" + " WHERE CountyName = '" + POut.string(county.OldCountyName) + "'";
        Db.nonQ(command);
        //then, update all patients using that County
        command = "UPDATE patient SET " + "County ='" + POut.string(county.CountyName) + "'" + " WHERE County = '" + POut.string(county.OldCountyName) + "'";
        Db.nonQ(command);
    }

    /**
    * Must run UsedBy before running this.
    */
    public static void delete(County Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from county WHERE CountyName = '" + POut.string(Cur.CountyName) + "'";
        Db.nonQ(command);
    }

    /**
    * Use before DeleteCur to determine if this County name is in use. Returns a formatted string that can be used to quickly display the names of all patients using the Countyname.
    */
    public static String usedBy(String countyName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), countyName);
        }
         
        String command = "SELECT LName,FName FROM patient " + "WHERE County = '" + POut.string(countyName) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        String retVal = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal += PIn.String(table.Rows[i][0].ToString()) + ", " + PIn.String(table.Rows[i][1].ToString());
            if (i < table.Rows.Count - 1)
            {
                //if not the last row
                retVal += "\r";
            }
             
        }
        return retVal;
    }

    /**
    * Use before Insert to determine if this County name already exists. Also used when closing patient edit window to validate that the Countyname exists.
    */
    public static boolean doesExist(String countyName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), countyName);
        }
         
        String command = "SELECT * FROM county " + "WHERE CountyName = '" + POut.string(countyName) + "' ";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return false;
        }
        else
        {
            return true;
        } 
    }

}


