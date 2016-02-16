//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefC;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldDefC;
import OpenDentBusiness.SheetFieldDefs;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class SheetDefs   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM sheetdef ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "sheetdef";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        SheetDefC.setListt(Crud.SheetDefCrud.TableToList(table));
    }

    /**
    * Gets one SheetDef from the cache.  Also includes the fields and parameters for the sheetdef.
    */
    public static SheetDef getSheetDef(long sheetDefNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        SheetDef sheetdef = null;
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetDefNum == sheetDefNum)
            {
                sheetdef = SheetDefC.getListt()[i].Copy();
                break;
            }
             
        }
        //if sheetdef is null, it will crash, just as it should.
        getFieldsAndParameters(sheetdef);
        return sheetdef;
    }

    /**
    * Includes all attached fields.  It simply deletes all the old fields and inserts new ones.
    */
    public static long insertOrUpdate(SheetDef sheetDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sheetDef.SheetDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sheetDef);
            return sheetDef.SheetDefNum;
        }
         
        String command = new String();
        if (!sheetDef.getIsNew())
        {
            command = "DELETE FROM sheetfielddef WHERE SheetDefNum=" + POut.long(sheetDef.SheetDefNum);
            Db.nonQ(command);
        }
         
        if (sheetDef.getIsNew())
        {
            sheetDef.SheetDefNum = Crud.SheetDefCrud.Insert(sheetDef);
        }
        else
        {
            Crud.SheetDefCrud.Update(sheetDef);
        } 
        for (Object __dummyForeachVar0 : sheetDef.SheetFieldDefs)
        {
            SheetFieldDef field = (SheetFieldDef)__dummyForeachVar0;
            field.setIsNew(true);
            field.SheetDefNum = sheetDef.SheetDefNum;
            SheetFieldDefs.insert(field);
        }
        return sheetDef.SheetDefNum;
    }

    /**
    * 
    */
    public static void deleteObject(long sheetDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheetDefNum);
            return ;
        }
         
        //validate that not already in use by a refferral.
        String command = "SELECT LName,FName FROM referral WHERE Slip=" + POut.long(sheetDefNum);
        DataTable table = Db.getTable(command);
        //int count=PIn.PInt(Db.GetCount(command));
        String names = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                names += ", ";
            }
             
            names += table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["LName"].ToString();
        }
        if (table.Rows.Count > 0)
        {
            throw new ApplicationException(Lans.g("sheetDefs","SheetDef is already in use by referrals(s). Not allowed to delete. ") + names);
        }
         
        //validate that not already in use by automation.
        command = "SELECT AutomationNum FROM automation WHERE SheetDefNum=" + POut.long(sheetDefNum);
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            throw new ApplicationException(Lans.g("sheetDefs","SheetDef is in use by automation. Not allowed to delete."));
        }
         
        command = "DELETE FROM sheetfielddef WHERE SheetDefNum=" + POut.long(sheetDefNum);
        Db.nonQ(command);
        Crud.SheetDefCrud.Delete(sheetDefNum);
    }

    /**
    * Sheetdefs and sheetfielddefs are archived separately.  So when we need to use a sheetdef, we must run this method to pull all the associated fields from the archive.  Then it will be ready for printing, copying, etc.
    */
    public static void getFieldsAndParameters(SheetDef sheetdef) throws Exception {
        //No need to check RemotingRole; no call to db.
        sheetdef.SheetFieldDefs = new List<SheetFieldDef>();
        sheetdef.Parameters = SheetParameter.getForType(sheetdef.SheetType);
        for (int i = 0;i < SheetFieldDefC.getListt().Count;i++)
        {
            //images first
            if (SheetFieldDefC.getListt()[i].SheetDefNum != sheetdef.SheetDefNum)
            {
                continue;
            }
             
            if (SheetFieldDefC.getListt()[i].FieldType != SheetFieldType.Image)
            {
                continue;
            }
             
            sheetdef.SheetFieldDefs.Add(SheetFieldDefC.getListt()[i].Copy());
        }
        for (int i = 0;i < SheetFieldDefC.getListt().Count;i++)
        {
            //then all other fields
            if (SheetFieldDefC.getListt()[i].SheetDefNum != sheetdef.SheetDefNum)
            {
                continue;
            }
             
            if (SheetFieldDefC.getListt()[i].FieldType == SheetFieldType.Image)
            {
                continue;
            }
             
            if (SheetFieldDefC.getListt()[i].FieldType == SheetFieldType.Parameter)
            {
                continue;
            }
             
            //sheetfielddefs never store parameters.
            //sheetfields do store filled parameters, but that's different.
            //else{
            sheetdef.SheetFieldDefs.Add(SheetFieldDefC.getListt()[i].Copy());
        }
    }

    //}
    /**
    * Gets all custom sheetdefs(without fields or parameters) for a particular type.
    */
    public static List<SheetDef> getCustomForType(SheetTypeEnum sheettype) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<SheetDef> retVal = new List<SheetDef>();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetType == sheettype)
            {
                retVal.Add(SheetDefC.getListt()[i].Copy());
            }
             
        }
        return retVal;
    }

    /**
    * Gets the description from the cache.
    */
    public static String getDescription(long sheetDefNum) throws Exception {
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (SheetDefC.getListt()[i].SheetDefNum == sheetDefNum)
            {
                return SheetDefC.getListt()[i].Description;
            }
             
        }
        return "";
    }

}


