//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.WikiListHeaderWidth;
import OpenDentBusiness.WikiListHeaderWidths;

/**
* 
*/
public class WikiLists   
{
    /*We will probably add this back in later. (TranslateToHTML)
    		/// <summary>Returns an html formatted table generated from a "SELECT * FROM wikiList_&lt;tablename&gt;" query.</summary>
    		public static string TranslateToHTML(string tableName) {
    			string command = "SELECT * FROM wikiList_"+tableName;//TODO: userOD table used just for testing purposes.
    			DataTable Table = Db.GetTable(command);
    			StringBuilder TableBuilder = new StringBuilder();
    			TableBuilder.AppendLine("\t<table>");
    					TableBuilder.AppendLine("\t\t<tr>");
    					TableBuilder.AppendLine("\t\t\t<td align=\"center\" colspan=\""+Table.Columns.Count+"\">");
    					TableBuilder.AppendLine("\t\t\t<h3>List : "+tableName+"</h3>");
    					TableBuilder.AppendLine("\t\t\t</td>");
    					TableBuilder.AppendLine("\t\t</tr>");
    				TableBuilder.AppendLine("\t\t<tr>");
    				foreach(DataColumn col in Table.Columns){
    					TableBuilder.AppendLine("\t\t\t<th>");
    					TableBuilder.AppendLine("\t\t\t\t<b>"+col.ColumnName+"</b>");
    					TableBuilder.AppendLine("\t\t\t</th>");
    				}
    				TableBuilder.AppendLine("\t\t</tr>");
    			//TODO: table headers
    			foreach(DataRow row in Table.Rows) {
    				TableBuilder.AppendLine("\t\t<tr>");
    				foreach(object cell in row.ItemArray) {
    					TableBuilder.AppendLine("\t\t\t<td>");
    					TableBuilder.AppendLine("\t\t\t\t"+cell.ToString());
    					TableBuilder.AppendLine("\t\t\t</td>");
    				}
    				TableBuilder.AppendLine("\t\t</tr>");
    			}
    			TableBuilder.AppendLine("\t</table>");
    			return TableBuilder.ToString();
    		}*/
    public static boolean checkExists(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), listName);
        }
         
        String command = "SHOW TABLES LIKE 'wikilist\\_" + POut.string(listName) + "'";
        if (Db.getTable(command).Rows.Count == 1)
        {
            return true;
        }
         
        return false;
    }

    //found exacty one table with that name
    //no table found with that name
    public static DataTable getByName(String listName) throws Exception {
        return getByName(listName,"");
    }

    public static DataTable getByName(String listName, String orderBy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), listName, orderBy);
        }
         
        DataTable tableDescript = Db.getTable("DESCRIBE wikilist_" + POut.string(listName));
        String command = "SELECT * FROM wikilist_" + POut.string(listName);
        //Manual ovverride of Order By
        if (!StringSupport.equals(orderBy, ""))
        {
            command += " ORDER BY " + POut.string(orderBy);
            return Db.getTable(command);
        }
         
        //Automatic OrderBy
        Count __dummyScrutVar0 = tableDescript.Rows.Count;
        if (__dummyScrutVar0.equals(0))
        {
        }
        else //should never happen
        if (__dummyScrutVar0.equals(1))
        {
            command += " ORDER BY " + POut.string(listName) + "Num";
        }
        else
        {
            //order by PK
            //order by the second column, even though we show the primary key
            command += " ORDER BY " + tableDescript.Rows[1]["Field"];
        }  
        return Db.getTable(command);
    }

    /**
    * Creates empty table with a single column for PK. List name must be formatted correctly before being passed here. Ie. no spaces, all lowercase.
    */
    public static void createNewWikiList(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName);
            return ;
        }
         
        //listname is checked in the UI for proper format.
        String command = "CREATE TABLE wikilist_" + POut.string(listName) + " (" + POut.string(listName) + "Num bigint NOT NULL auto_increment PRIMARY KEY ) DEFAULT CHARSET=utf8";
        Db.nonQ(command);
        WikiListHeaderWidth headerWidth = new WikiListHeaderWidth();
        headerWidth.ColName = listName + "Num";
        headerWidth.ColWidth = 100;
        headerWidth.ListName = listName;
        WikiListHeaderWidths.insertNew(headerWidth);
    }

    /**
    * Column is automatically named "Column#" where # is the number of columns+1.
    */
    public static void addColumn(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName);
            return ;
        }
         
        //Find Valid column name-----------------------------------------------------------------------------------------
        DataTable columnNames = Db.getTable("DESCRIBE wikilist_" + POut.string(listName));
        String newColumnName = "Column1";
        for (int i = 0;i < columnNames.Rows.Count + 1;i++)
        {
            //default in case table has no columns. Should never happen.
            //+1 to guarantee we can find a valid name.
            newColumnName = "Column" + (1 + i);
            for (int j = 0;j < columnNames.Rows.Count;j++)
            {
                //ie. Column1, Column2, Column3...
                if (StringSupport.equals(newColumnName, columnNames.Rows[j]["Field"].ToString()))
                {
                    newColumnName = "";
                    break;
                }
                 
            }
            if (!StringSupport.equals(newColumnName, ""))
            {
                break;
            }
             
        }
        //found a valid name.
        if (StringSupport.equals(newColumnName, ""))
        {
            throw new ApplicationException("Could not create valid column name.");
        }
         
        //should never happen.
        //Add new column name--------------------------------------------------------------------------------------------
        String command = "ALTER TABLE wikilist_" + POut.string(listName) + " ADD COLUMN " + POut.string(newColumnName) + " TEXT NOT NULL";
        Db.nonQ(command);
        //Add column widths to wikiListHeaderWidth Table-----------------------------------------------------------------
        WikiListHeaderWidth headerWidth = new WikiListHeaderWidth();
        headerWidth.ColName = newColumnName;
        headerWidth.ListName = listName;
        headerWidth.ColWidth = 100;
        WikiListHeaderWidths.insertNew(headerWidth);
    }

    /**
    * Check to see if column can be deleted, returns true is the column contains only nulls.
    */
    public static boolean checkColumnEmpty(String listName, String colName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), listName, colName);
        }
         
        String command = "SELECT COUNT(*) FROM wikilist_" + POut.string(listName) + " WHERE " + POut.string(colName) + "!=''";
        return Db.getCount(command).Equals("0");
    }

    /**
    * Check to see if column can be deleted, returns true is the column contains only nulls.
    */
    public static void deleteColumn(String listName, String colName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetBool(MethodBase.GetCurrentMethod(), listName, colName);
            return ;
        }
         
        String command = "ALTER TABLE wikilist_" + POut.string(listName) + " DROP " + POut.string(colName);
        Db.nonQ(command);
        WikiListHeaderWidths.delete(listName,colName);
    }

    /**
    * Shifts the column to the left, does nothing if trying to shift leftmost two columns.
    */
    public static void shiftColumnLeft(String listName, String colName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetBool(MethodBase.GetCurrentMethod(), listName, colName);
            return ;
        }
         
        DataTable columnNames = Db.getTable("DESCRIBE wikilist_" + POut.string(listName));
        if (columnNames.Rows.Count < 3)
        {
            return ;
        }
         
        //not enough columns to reorder.
        if (StringSupport.equals(colName, columnNames.Rows[0][0].ToString()) || StringSupport.equals(colName, columnNames.Rows[1][0].ToString()))
        {
            return ;
        }
         
        //trying to re-order PK or first column.
        //No need to return here, but also no need to continue.
        String command = "";
        for (int i = 2;i < columnNames.Rows.Count;i++)
        {
            if (StringSupport.equals(columnNames.Rows[i][0].ToString(), colName))
            {
                command = "ALTER TABLE wikilist_" + POut.string(listName) + " MODIFY " + POut.string(colName) + " TEXT NOT NULL AFTER " + POut.String(columnNames.Rows[i - 2][0].ToString());
                Db.nonQ(command);
                return ;
            }
             
        }
    }

    //no column found. Should never reach this location.
    /**
    * Shifts the column to the right, does nothing if trying to shift the rightmost column.
    */
    public static void shiftColumnRight(String listName, String colName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetBool(MethodBase.GetCurrentMethod(), listName, colName);
            return ;
        }
         
        DataTable columnNames = Db.getTable("DESCRIBE wikilist_" + POut.string(listName));
        if (columnNames.Rows.Count < 3)
        {
            return ;
        }
         
        //not enough columns to reorder.
        //don't shift the PK
        if (StringSupport.equals(colName, columnNames.Rows[0][0].ToString()) || StringSupport.equals(colName, columnNames.Rows[columnNames.Rows.Count - 1][0].ToString()))
        {
            return ;
        }
         
        //don't shift the last column
        //No need to return here, but also no need to continue.
        String command = "";
        for (int i = 1;i < columnNames.Rows.Count - 1;i++)
        {
            if (StringSupport.equals(columnNames.Rows[i][0].ToString(), colName))
            {
                command = "ALTER TABLE wikilist_" + POut.string(listName) + " MODIFY " + POut.string(colName) + " TEXT NOT NULL AFTER " + POut.String(columnNames.Rows[i + 1][0].ToString());
                Db.nonQ(command);
                return ;
            }
             
        }
    }

    //no column found. Should never reach this location.
    /**
    * Adds one item to wiki list and returns the new PK.
    */
    public static long addItem(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), listName);
        }
         
        String command = "INSERT INTO wikilist_" + POut.string(listName) + " VALUES ()";
        return Db.nonQ(command,true);
    }

    //inserts empty row with auto generated PK.
    /**
    * @param ItemTable Should be a DataTable object with a single DataRow containing the item.
    */
    public static void updateItem(String listName, DataTable ItemTable) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName, ItemTable);
            return ;
        }
         
        if (ItemTable.Columns.Count < 2)
        {
            return ;
        }
         
        //if the table contains only a PK column.
        String command = "UPDATE wikilist_" + POut.string(listName) + " SET ";
        for (int i = 1;i < ItemTable.Columns.Count;i++)
        {
            //inserts empty row with auto generated PK.
            //start at 1 because we do not need to update the PK
            command += POut.String(ItemTable.Columns[i].ColumnName) + "='" + POut.String(ItemTable.Rows[0][i].ToString()) + "',";
        }
        command = command.Trim(',') + " WHERE " + POut.string(listName) + "Num = " + ItemTable.Rows[0][0];
        Db.nonQ(command);
    }

    public static DataTable getItem(String listName, long itemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), listName, itemNum);
        }
         
        DataTable retVal = new DataTable();
        String command = "SELECT * FROM wikilist_" + POut.string(listName) + " WHERE " + POut.string(listName) + "Num = " + POut.long(itemNum);
        retVal = Db.getTable(command);
        return retVal;
    }

    public static void deleteItem(String listName, long itemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName, itemNum);
            return ;
        }
         
        String command = "DELETE FROM wikilist_" + POut.string(listName) + " WHERE " + POut.string(listName) + "Num = " + POut.long(itemNum);
        Db.nonQ(command);
    }

    public static void deleteList(String listName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), listName);
            return ;
        }
         
        String command = "DROP TABLE wikilist_" + POut.string(listName);
        Db.nonQ(command);
        WikiListHeaderWidths.deleteForList(listName);
    }

    public static List<String> getAllLists() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SHOW TABLES LIKE 'wikilist\\_%'";
        //must escape _ (underscore) otherwise it is interpreted as a wildcard character.
        DataTable Table = Db.getTable(command);
        for (Object __dummyForeachVar0 : Table.Rows)
        {
            DataRow row = (DataRow)__dummyForeachVar0;
            retVal.Add(row[0].ToString());
        }
        return retVal;
    }

    /**
    * Surround with try catch.  Safely renames list by creating new list, selecting existing list into new list, then deleting existing list.This code could be used to either copy or backup lists in the future. (With minor modifications).
    */
    public static void rename(String nameOriginal, String nameNew) throws Exception {
        //Name should already have been validated and available.
        String command = "CREATE TABLE wikilist_" + POut.string(nameNew) + " AS SELECT * FROM wikilist_" + POut.string(nameOriginal);
        Db.nonQ(command);
        //Validate content before altering and deleting things
        DataTable tableNew = getByName(nameNew);
        DataTable tableOld = getByName(nameOriginal);
        if (tableNew.Rows.Count != tableOld.Rows.Count)
        {
            command = "DROP TABLE wikilist_" + POut.string(nameNew);
            Db.nonQ(command);
            throw new Exception("Error occured renaming list.  Mismatch found in row count. No changes made.");
        }
         
        if (tableNew.Columns.Count != tableOld.Columns.Count)
        {
            command = "DROP TABLE wikilist_" + POut.string(nameNew);
            Db.nonQ(command);
            throw new Exception("Error occured renaming list.  Mismatch found in column count. No changes made.");
        }
         
        for (int r1 = 0;r1 < tableNew.Rows.Count;r1++)
        {
            for (int r2 = 0;r2 < tableOld.Rows.Count;r2++)
            {
                if (tableNew.Rows[r1][0] != tableOld.Rows[r2][0])
                {
                    continue;
                }
                 
                for (int c = 0;c < tableNew.Columns.Count;c++)
                {
                    //pk does not match
                    //both lists have same number of columns
                    if (tableNew.Rows[r1][c] == tableOld.Rows[r2][c])
                    {
                        continue;
                    }
                     
                    throw new Exception("Error occured renaming list.  Mismatch Error found in row data. No changes made.");
                }
            }
        }
        //contents match
        //end columns
        //end tableOld
        //end tableNew
        //Alter table names----------------------------------------------------------------------------
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE wikilist_" + POut.string(nameNew) + " CHANGE " + POut.string(nameOriginal) + "Num " + POut.string(nameNew) + "Num bigint NOT NULL auto_increment PRIMARY KEY";
        }
        else
        {
            command = "RENAME COLUMN wikilist_" + POut.string(nameNew) + "." + POut.string(nameOriginal) + "Num TO " + POut.string(nameNew) + "Num";
        } 
        Db.nonQ(command);
        command = "UPDATE wikilistheaderwidth SET ListName='" + POut.string(nameNew) + "' WHERE ListName='" + POut.string(nameOriginal) + "'";
        Db.nonQ(command);
        command = "UPDATE wikilistheaderwidth SET ColName='" + POut.string(nameNew) + "Num' WHERE ListName='" + POut.string(nameNew) + "' AND ColName='" + POut.string(nameOriginal) + "Num'";
        Db.nonQ(command);
        //drop old table---------------------
        command = "DROP TABLE wikilist_" + POut.string(nameOriginal);
        Db.nonQ(command);
    }

}


