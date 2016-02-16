//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.WikiListHeaderWidth;

/**
* 
*/
public class WikiListHeaderWidths   
{
    /**
    * Used temporarily.
    */
    public static String dummyColName = "Xrxzes";
    /**
    * A list of all WikiListHeaderWidths.
    */
    private static List<WikiListHeaderWidth> listt = new List<WikiListHeaderWidth>();
    /**
    * A list of all WikiListHeaderWidths.
    */
    public static List<WikiListHeaderWidth> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<WikiListHeaderWidth> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM wikilistheaderwidth";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "WikiListHeaderWidth";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        setListt(Crud.WikiListHeaderWidthCrud.TableToList(table));
    }

    /*///<summary>Returns header widths for list sorted in the same order as the columns appear in the DB. Can be more efficient than using cache.</summary>
    		public static List<WikiListHeaderWidth> GetForListNoCache(string listName) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<WikiListHeaderWidth>>(MethodBase.GetCurrentMethod(),listName);
    			}
    			List<WikiListHeaderWidth> retVal = new List<WikiListHeaderWidth>();
    			List<WikiListHeaderWidth> tempList = new List<WikiListHeaderWidth>();
    			string command="DESCRIBE wikilist_"+POut.String(listName);
    			DataTable listDescription=Db.GetTable(command);
    			command="SELECT * FROM wikilistheaderwidth WHERE ListName='"+POut.String(listName)+"'";
    			tempList=Crud.WikiListHeaderWidthCrud.SelectMany(command);
    			for(int i=0;i<listDescription.Rows.Count;i++) {
    				for(int j=0;j<tempList.Count;j++) {
    					//Add WikiListHeaderWidth from tempList to retVal if it is the next row in listDescription.
    					if(listDescription.Rows[i][0].ToString()==tempList[j].ColName) {
    						retVal.Add(tempList[j]);
    						break;
    					}
    				}
    				//next description row.
    			}
    			return retVal;
    		}*/
    /**
    * Returns header widths for list sorted in the same order as the columns appear in the DB. Uses cache.
    */
    public static List<WikiListHeaderWidth> getForList(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<WikiListHeaderWidth>>GetObject(MethodBase.GetCurrentMethod(), listName);
        }
         
        List<WikiListHeaderWidth> retVal = new List<WikiListHeaderWidth>();
        String command = "DESCRIBE wikilist_" + POut.string(listName);
        DataTable tableListDescription = Db.getTable(command);
        for (int i = 0;i < tableListDescription.Rows.Count;i++)
        {
            for (int j = 0;j < getListt().Count;j++)
            {
                //Add WikiListHeaderWidth from tempList to retVal if it is the next row in listDescription.
                if (tableListDescription.Rows[i][0].ToString() == getListt()[j].ColName)
                {
                    retVal.Add(getListt()[j]);
                    break;
                }
                 
            }
        }
        return retVal;
    }

    //next description row.
    /**
    * Also alters the db table for the list itself.  Throws exception if number of columns does not match.
    */
    public static void updateNamesAndWidths(String listName, List<WikiListHeaderWidth> columnDefs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), columnDefs);
            return ;
        }
         
        String command = "DESCRIBE wikilist_" + POut.string(listName);
        DataTable tableListDescription = Db.getTable(command);
        if (tableListDescription.Rows.Count != columnDefs.Count)
        {
            throw new ApplicationException("List schema has been altered. Unable to save changes to list.");
        }
         
        for (int i = 0;i < tableListDescription.Rows.Count;i++)
        {
            //rename Columns with dummy names in case user is renaming a new column with an old name.---------------------------------------------
            if (StringSupport.equals(tableListDescription.Rows[i][0].ToString().ToLower(), POut.string(listName) + "num"))
            {
                continue;
            }
             
            //skip primary key
            command = "ALTER TABLE wikilist_" + POut.string(listName) + " CHANGE " + POut.String(tableListDescription.Rows[i][0].ToString()) + " " + POut.string(dummyColName + i) + " TEXT NOT NULL";
            Db.nonQ(command);
            command = "UPDATE wikiListHeaderWidth SET ColName='" + POut.string(dummyColName + i) + "' " + "WHERE ListName='" + POut.string(listName) + "' " + "AND ColName='" + POut.String(tableListDescription.Rows[i][0].ToString()) + "'";
            Db.nonQ(command);
        }
        for (int i = 0;i < tableListDescription.Rows.Count;i++)
        {
            //rename columns names and widths-------------------------------------------------------------------------------------------------------
            if (StringSupport.equals(tableListDescription.Rows[i][0].ToString().ToLower(), listName + "num"))
            {
                continue;
            }
             
            //skip primary key
            command = "ALTER TABLE wikilist_" + POut.string(listName) + " CHANGE  " + POut.string(dummyColName + i) + " " + POut.String(columnDefs[i].ColName) + " TEXT NOT NULL";
            Db.nonQ(command);
            command = "UPDATE wikiListHeaderWidth " + "SET ColName='" + POut.String(columnDefs[i].ColName) + "', ColWidth='" + POut.Int(columnDefs[i].ColWidth) + "' " + "WHERE ListName='" + POut.string(listName) + "' " + "AND ColName='" + POut.string(dummyColName + i) + "'";
            Db.nonQ(command);
        }
        //handle width of PK seperately because we do not rename the PK column, ever.
        command = "UPDATE wikiListHeaderWidth SET ColWidth='" + POut.Int(columnDefs[0].ColWidth) + "' " + "WHERE ListName='" + POut.string(listName) + "' AND ColName='" + POut.String(columnDefs[0].ColName) + "'";
        Db.nonQ(command);
        refreshCache();
    }

    /**
    * No error checking. Only called from WikiLists.
    */
    public static void insertNew(WikiListHeaderWidth newWidth) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), newWidth);
            return ;
        }
         
        Crud.WikiListHeaderWidthCrud.Insert(newWidth);
        refreshCache();
    }

    /**
    * No error checking. Only called from WikiLists after the corresponding column has been dropped from its respective table.
    */
    public static void delete(String listName, String colName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName, colName);
            return ;
        }
         
        String command = "DELETE FROM wikilistheaderwidth WHERE ListName='" + POut.string(listName) + "' AND ColName='" + POut.string(colName) + "'";
        Db.nonQ(command);
        refreshCache();
    }

    public static void deleteForList(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName);
            return ;
        }
         
        String command = "DELETE FROM wikilistheaderwidth WHERE ListName='" + POut.string(listName) + "'";
        Db.nonQ(command);
        refreshCache();
    }

}


