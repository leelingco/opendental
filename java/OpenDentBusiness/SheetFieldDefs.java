//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldDefC;

/**
* 
*/
public class SheetFieldDefs   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM sheetfielddef ORDER BY SheetDefNum";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "sheetfielddef";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        SheetFieldDefC.setListt(Crud.SheetFieldDefCrud.TableToList(table));
    }

    /**
    * Gets all internal SheetFieldDefs from the database for a specific sheet, used in FormSheetFieldExam.
    */
    public static List<SheetFieldDef> getForExamSheet(long sheetDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SheetFieldDef>>GetObject(MethodBase.GetCurrentMethod(), sheetDefNum);
        }
         
        String command = "SELECT * FROM sheetfielddef WHERE SheetDefNum=" + POut.long(sheetDefNum) + " " + "AND ((FieldName!='misc' AND FieldName!='') OR (ReportableName!='')) " + "GROUP BY FieldName,ReportableName";
        return Crud.SheetFieldDefCrud.SelectMany(command);
    }

    /**
    * Gets one SheetFieldDef from the database.
    */
    public static SheetFieldDef createObject(long sheetFieldDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SheetFieldDef>GetObject(MethodBase.GetCurrentMethod(), sheetFieldDefNum);
        }
         
        return Crud.SheetFieldDefCrud.SelectOne(sheetFieldDefNum);
    }

    /**
    * 
    */
    public static long insert(SheetFieldDef sheetFieldDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sheetFieldDef.SheetFieldDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sheetFieldDef);
            return sheetFieldDef.SheetFieldDefNum;
        }
         
        return Crud.SheetFieldDefCrud.Insert(sheetFieldDef);
    }

    /*
    		///<summary></summary>
    		public static void Update(SheetFieldDef sheetFieldDef) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),sheetFieldDef);
    				return;
    			}
    			Crud.SheetFieldDefCrud.Update(sheetFieldDef);
    		}*/
    /**
    * 
    */
    public static void deleteObject(long sheetFieldDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheetFieldDefNum);
            return ;
        }
         
        Crud.SheetFieldDefCrud.Delete(sheetFieldDefNum);
    }

}


