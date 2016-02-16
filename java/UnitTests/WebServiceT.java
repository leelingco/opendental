//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.WebServiceTests;

public class WebServiceT   
{
    /**
    * 
    */
    public static String runAll() throws Exception {
        String retVal = "";
        //GetString
        String strResult = WebServiceTests.getString("Input");
        if (!StringSupport.equals(strResult, "Input-Processed"))
        {
            throw new Exception("Should be Input-Processed");
        }
         
        retVal += "GetString: Passed.\r\n";
        strResult = WebServiceTests.getStringNull("Input");
        if (strResult != null)
        {
            throw new Exception("Should be null");
        }
         
        retVal += "GetStringNull: Passed.\r\n";
        strResult = WebServiceTests.getStringCarriageReturn("Carriage\r\nReturn");
        if (!StringSupport.equals(strResult, "Carriage\r\nReturn-Processed"))
        {
            throw new Exception("Should be Carriage\r\nReturn-Processed");
        }
         
        retVal += "GetStringCarriageReturn: Passed.\r\n";
        //GetInt
        int intResult = WebServiceTests.getInt(1);
        if (intResult != 2)
        {
            throw new Exception("Should be 2");
        }
         
        retVal += "GetInt: Passed.\r\n";
        //GetLong
        long longResult = WebServiceTests.GetLong(1);
        if (longResult != 2)
        {
            throw new Exception("Should be 2");
        }
         
        retVal += "GetLong: Passed.\r\n";
        //GetVoid
        WebServiceTests.getVoid();
        retVal += "GetVoid: Passed.\r\n";
        //GetBool
        boolean boolResult = WebServiceTests.getBool();
        if (boolResult != true)
        {
            throw new Exception("Should be true");
        }
         
        retVal += "GetBool: Passed.\r\n";
        //GetObject
        Patient pat = WebServiceTests.getObjectPat();
        if (!StringSupport.equals(pat.LName, "Smith"))
        {
            throw new Exception("Should be Smith");
        }
         
        if (pat.FName != null)
        {
            throw new Exception("Should be null");
        }
         
        retVal += "GetObjectPat: Passed.\r\n";
        //GetTable
        DataTable table = WebServiceTests.getTable();
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "cell00"))
        {
            throw new Exception("Should be cell00");
        }
         
        retVal += "GetTable: Passed.\r\n";
        //GetTable with carriage return
        table = WebServiceTests.getTableCarriageReturn();
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "cell\r\n00"))
        {
            throw new Exception("Should be cell\r\n00");
        }
         
        retVal += "GetTableCarriageReturn: Passed.\r\n";
        //Get2by3
        table = WebServiceTests.getTable2by3();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            for (int j = 0;j < table.Columns.Count;j++)
            {
                if (!StringSupport.equals(table.Rows[i][j].ToString(), "cell" + i.ToString() + j.ToString()))
                {
                    throw new Exception("Should be cell" + i.ToString() + j.ToString());
                }
                 
            }
        }
        retVal += "GetTable2by3: Passed.\r\n";
        //GetSpecialChars
        table = WebServiceTests.getTableSpecialChars();
        char[] chars;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            for (int j = 0;j < table.Columns.Count;j++)
            {
                if (!StringSupport.equals(table.Rows[i][j].ToString(), "cell" + i.ToString() + j.ToString() + chars[i * 2 + j].ToString()))
                {
                    throw new Exception("Should be cell" + i.ToString() + j.ToString() + chars[i * 2 + j].ToString());
                }
                 
            }
        }
        retVal += "GetTableSpecialChars: Passed.\r\n";
        //GetDataTypes
        table = WebServiceTests.getTableDataTypes();
        if (table.Rows[0][0].GetType() != String.class)
        {
            throw new Exception("Should be " + String.class.ToString());
        }
         
        if (table.Rows[0][1].GetType() != double.class)
        {
            throw new Exception("Should be " + double.class.ToString());
        }
         
        if (table.Rows[0][2].GetType() != DateTime.class)
        {
            throw new Exception("Should be " + DateTime.class.ToString());
        }
         
        retVal += "GetTableDataTypes: Passed.\r\n";
        //GetDataSet
        DataSet ds = WebServiceTests.getDataSet();
        if (!StringSupport.equals(ds.Tables[0].TableName, "table0"))
        {
            throw new Exception("Should be table0");
        }
         
        retVal += "GetDataSet: Passed.\r\n";
        //GetList
        List<int> listInt = WebServiceTests.getListInt();
        if (listInt[0] != 2)
        {
            throw new Exception("Should be 2");
        }
         
        retVal += "GetListInt: Passed.\r\n";
        //GetArrayPatient
        Patient[] arrayPat = WebServiceTests.getArrayPatient();
        if (!StringSupport.equals(arrayPat[0].LName, "Jones"))
        {
            throw new Exception("Should be Jones");
        }
         
        if (arrayPat[1] != null)
        {
            throw new Exception("Should be null");
        }
         
        retVal += "GetArrayPatient: Passed.\r\n";
        //SendNullParam
        strResult = WebServiceTests.sendNullParam(null);
        if (!StringSupport.equals(strResult, "nullOK"))
        {
            throw new Exception("Should be nullOK");
        }
         
        retVal += "SendNullParam: Passed.\r\n";
        //GetObjectNull
        Patient pat2 = WebServiceTests.getObjectNull();
        if (pat2 != null)
        {
            throw new Exception("Should be null");
        }
         
        retVal += "GetObjectNull: Passed.\r\n";
        //SendColorParam
        Color colorResult = WebServiceTests.SendColorParam(Color.Fuchsia);
        if (colorResult.ToArgb() != Color.Green.ToArgb())
        {
            throw new Exception("Should be green.");
        }
         
        retVal += "SendColorParam: Passed.\r\n";
        //SendProviderColor
        Provider prov = new Provider();
        prov.ProvColor = Color.Fuchsia;
        strResult = WebServiceTests.sendProviderColor(prov);
        if (!StringSupport.equals(strResult, "fuchsiaOK"))
        {
            throw new Exception("Should be fuchsiaOK.");
        }
         
        retVal += "SendProviderColor: Passed.\r\n";
        //SendSheetParameter
        SheetParameter sheetParam = new SheetParameter(false,"ParamNameOK");
        strResult = WebServiceTests.sendSheetParameter(sheetParam);
        if (!StringSupport.equals(strResult, "paramNameOK"))
        {
            throw new Exception("Should be paramNameOK.");
        }
         
        retVal += "SendSheetParameter: Passed.\r\n";
        //SendSheetWithFields
        Sheet sheet = new Sheet();
        sheet.SheetFields = new List<SheetField>();
        sheet.Parameters = new List<SheetParameter>();
        SheetField field = new SheetField();
        field.FieldName = "FieldNameGreen";
        sheet.SheetFields.Add(field);
        strResult = WebServiceTests.sendSheetWithFields(sheet);
        if (!StringSupport.equals(strResult, "fieldOK"))
        {
            throw new Exception("Should be fieldOK.");
        }
         
        retVal += "SendSheetWithFields: Passed.\r\n";
        //SendSheetDefWithFields
        SheetDef sheetdef = new SheetDef();
        sheetdef.SheetFieldDefs = new List<SheetFieldDef>();
        sheetdef.Parameters = new List<SheetParameter>();
        SheetFieldDef fielddef = new SheetFieldDef();
        fielddef.FieldName = "FieldNameTeal";
        sheetdef.SheetFieldDefs.Add(fielddef);
        strResult = WebServiceTests.sendSheetDefWithFieldDefs(sheetdef);
        if (!StringSupport.equals(strResult, "fielddefOK"))
        {
            throw new Exception("Should be fielddefOK.");
        }
         
        retVal += "SendSheetDefWithFieldDefs: Passed.\r\n";
        //TimeSpanNeg
        TimeSpan tspan = WebServiceTests.getTimeSpan();
        if (tspan != new TimeSpan(1, 0, 0))
        {
            throw new Exception("Should be 1 hour.");
        }
         
        retVal += "GetTimeSpan: Passed.\r\n";
        //GetStringContainingCR
        strResult = WebServiceTests.getStringContainingCR();
        //strResult=strResult.Replace("\\r","\r");
        if (!StringSupport.equals(strResult, "Line1\r\nLine2"))
        {
            throw new Exception("Should be Line1\r\nLine2");
        }
         
        retVal += "GetStringContainingCR: Passed.\r\n";
        return retVal;
    }

}


/*
			//GetListTasksContainingCR
			Task task=WebServiceTests.GetListTasksContainingCR()[0];
			if(task.Descript!="Line1\r\nLine2") {
				throw new Exception("Should be Line1\r\nLine2");
			}
			retVal+="GetListTasksContainingCR: Passed.\r\n";*/