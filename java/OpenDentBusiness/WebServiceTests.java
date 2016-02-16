//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.POut;
import OpenDentBusiness.Provider;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Task;

/**
* These are only run from the Unit Testing framework
*/
public class WebServiceTests   
{
    public static String getString(String str) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), str);
        }
         
        return str + "-Processed";
    }

    public static String getStringNull(String str) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), str);
        }
         
        return null;
    }

    public static String getStringCarriageReturn(String str) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), str);
        }
         
        return str + "-Processed";
    }

    public static int getInt(int intVal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), intVal);
        }
         
        return 2;
    }

    public static long getLong(long longVal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), longVal);
        }
         
        return 2;
    }

    public static void getVoid() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        return ;
    }

    public static boolean getBool() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        return true;
    }

    public static Patient getObjectPat() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod());
        }
         
        Patient pat = new Patient();
        pat.LName = "Smith";
        pat.FName = null;
        return pat;
    }

    public static DataTable getTable() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT 'cell00'";
        DataTable table = Db.getTable(command);
        return table;
    }

    public static DataTable getTableCarriageReturn() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT '" + POut.string("cell\r\n00") + "'";
        DataTable table = Db.getTable(command);
        return table;
    }

    public static DataTable getTable2by3() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT '" + POut.string("cell00") + "','" + POut.string("cell01") + "' " + "UNION ALL " + "SELECT '" + POut.string("cell10") + "','" + POut.string("cell11") + "' " + "UNION ALL " + "SELECT '" + POut.string("cell20") + "','" + POut.string("cell21") + "'";
        DataTable table = Db.getTable(command);
        return table;
    }

    //also table with special chars: |, <, >, &, ', ", and \
    public static DataTable getTableSpecialChars() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        //Special characters in the columns as well as in the column names
        String command = "SELECT '" + POut.string("cell00|") + "' AS '|<>','" + POut.string("cell01<") + "' AS '&\\'\"\\\\' " + "UNION ALL " + "SELECT '" + POut.string("cell10>") + "','" + POut.string("cell11&") + "' " + "UNION ALL " + "SELECT '" + POut.string("cell20\'") + "','" + POut.string("cell21\"") + "' " + "UNION ALL " + "SELECT '" + POut.string("cell30\\") + "','" + POut.string("cell31/") + "'";
        DataTable table = Db.getTable(command);
        table.TableName = "Table|<>&'\"\\";
        return table;
    }

    public static DataTable getTableDataTypes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "DROP TABLE IF EXISTS tempdt;" + "CREATE TABLE tempdt (TString VARCHAR(50),TDecimal DECIMAL(10,2),TDateTime DATETIME);" + "INSERT INTO tempdt (TString,TDecimal,TDateTime) VALUES ('string',123.45,DATE('2013-04-11'));";
        Db.nonQ(command);
        command = "SELECT * FROM tempdt;";
        DataTable table = Db.getTable(command);
        return table;
    }

    public static DataSet getDataSet() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT 'cell00'";
        DataSet ds = new DataSet();
        DataTable table = Db.getTable(command);
        table.TableName = "table0";
        ds.Tables.Add(table);
        return ds;
    }

    public static List<int> getListInt() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<int>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<int> listInt = new List<int>();
        listInt.Add(2);
        return listInt;
    }

    public static Patient[] getArrayPatient() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        Patient[] retVal = new Patient[2];
        retVal[0] = new Patient();
        retVal[0].LName = "Jones";
        retVal[1] = null;
        return retVal;
    }

    public static String sendNullParam(String str) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), str);
        }
         
        if (str == null)
        {
            return "nullOK";
        }
        else
        {
            return "null not found";
        } 
    }

    public static Patient getObjectNull() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod());
        }
         
        return null;
    }

    public static Color sendColorParam(Color color) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Color>GetObject(MethodBase.GetCurrentMethod(), color);
        }
         
        if (color.ToArgb() == Color.Fuchsia.ToArgb())
        {
            return Color.Green;
        }
         
        return Color.Red;
    }

    //indicates error
    public static String sendProviderColor(Provider prov) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), prov);
        }
         
        if (prov.ProvColor.ToArgb() == Color.Fuchsia.ToArgb())
        {
            return "fuchsiaOK";
        }
         
        return "error";
    }

    public static String sendSheetParameter(SheetParameter sheetParam) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), sheetParam);
        }
         
        if (StringSupport.equals(sheetParam.ParamName, "ParamNameOK"))
        {
            return "paramNameOK";
        }
         
        return "error";
    }

    public static String sendSheetWithFields(Sheet sheet) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), sheet);
        }
         
        if (StringSupport.equals(sheet.SheetFields[0].FieldName, "FieldNameGreen"))
        {
            return "fieldOK";
        }
         
        return "error";
    }

    public static String sendSheetDefWithFieldDefs(SheetDef sheetdef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), sheetdef);
        }
         
        if (StringSupport.equals(sheetdef.SheetFieldDefs[0].FieldName, "FieldNameTeal"))
        {
            return "fielddefOK";
        }
         
        return "error";
    }

    public static TimeSpan getTimeSpan() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TimeSpan>GetObject(MethodBase.GetCurrentMethod());
        }
         
        return new TimeSpan(1, 0, 0);
    }

    public static String getStringContainingCR() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        return "Line1\r\nLine2";
    }

    public static List<Task> getListTasksContainingCR() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        Task task = new Task();
        task.Descript = "Line1\r\nLine2";
        List<Task> retVal = new List<Task>();
        retVal.Add(task);
        return retVal;
    }

}


