//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;

/**
* 
*/
public class SheetFields   
{
    /**
    * Gets one SheetField from the database.
    */
    public static SheetField createObject(long sheetFieldNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SheetField>GetObject(MethodBase.GetCurrentMethod(), sheetFieldNum);
        }
         
        return Crud.SheetFieldCrud.SelectOne(sheetFieldNum);
    }

    public static List<SheetField> getListForSheet(long sheetNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SheetField>>GetObject(MethodBase.GetCurrentMethod(), sheetNum);
        }
         
        String command = "SELECT * FROM sheetfield WHERE SheetNum=" + POut.long(sheetNum) + " ORDER BY SheetFieldNum";
        return Crud.SheetFieldCrud.SelectMany(command);
    }

    //the ordering is CRITICAL because the signature key is based on order.
    /**
    * When we need to use a sheet, we must run this method to pull all the associated fields and parameters from the database.  Then it will be ready for printing, copying, etc.
    */
    public static void getFieldsAndParameters(Sheet sheet) throws Exception {
        //No need to check RemotingRole; no call to db.
        sheet.SheetFields = getListForSheet(sheet.SheetNum);
        //so parameters will also be in the field list, but they will just be ignored from here on out.
        //because we will have an explicit parameter list instead.
        sheet.Parameters = new List<SheetParameter>();
        SheetParameter param;
        for (int i = 0;i < sheet.SheetFields.Count;i++)
        {
            //int paramVal;
            if (sheet.SheetFields[i].FieldType == SheetFieldType.Parameter)
            {
                param = new SheetParameter(true, sheet.SheetFields[i].FieldName, sheet.SheetFields[i].FieldValue);
                sheet.Parameters.Add(param);
            }
             
        }
    }

    /**
    * Used in SheetFiller to fill patient letter with exam sheet information.  Will return null if no exam sheet matching the description exists for the patient.  Usually just returns one field, but will return a list of fields if it's for a RadioButtonGroup.
    */
    public static List<SheetField> getFieldFromExamSheet(long patNum, String examDescript, String fieldName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SheetField>>GetObject(MethodBase.GetCurrentMethod(), patNum, examDescript, fieldName);
        }
         
        Sheet sheet = Sheets.getMostRecentExamSheet(patNum,examDescript);
        if (sheet == null)
        {
            return null;
        }
         
        String command = "SELECT * FROM sheetfield WHERE SheetNum=" + POut.long(sheet.SheetNum) + " " + "AND (RadioButtonGroup='" + POut.string(fieldName) + "' OR ReportableName='" + POut.string(fieldName) + "' OR FieldName='" + POut.string(fieldName) + "')";
        return Crud.SheetFieldCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(SheetField sheetField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sheetField.SheetFieldNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sheetField);
            return sheetField.SheetFieldNum;
        }
         
        return Crud.SheetFieldCrud.Insert(sheetField);
    }

    /**
    * 
    */
    public static void update(SheetField sheetField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheetField);
            return ;
        }
         
        Crud.SheetFieldCrud.Update(sheetField);
    }

    /**
    * 
    */
    public static void deleteObject(long sheetFieldNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheetFieldNum);
            return ;
        }
         
        Crud.SheetFieldCrud.Delete(sheetFieldNum);
    }

    /**
    * Deletes all existing drawing fields for a sheet from the database and then adds back the list supplied.
    */
    public static void setDrawings(List<SheetField> drawingList, long sheetNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), drawingList, sheetNum);
            return ;
        }
         
        String command = "DELETE FROM sheetfield WHERE SheetNum=" + POut.long(sheetNum) + " AND FieldType=" + POut.Long(((Enum)SheetFieldType.Drawing).ordinal());
        Db.nonQ(command);
        for (Object __dummyForeachVar0 : drawingList)
        {
            SheetField field = (SheetField)__dummyForeachVar0;
            insert(field);
        }
    }

}


