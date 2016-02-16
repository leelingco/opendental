//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class Sheets   
{
    /**
    * Gets one Sheet from the database.
    */
    public static Sheet createObject(long sheetNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Sheet>GetObject(MethodBase.GetCurrentMethod(), sheetNum);
        }
         
        return Crud.SheetCrud.SelectOne(sheetNum);
    }

    /**
    * Gets a single sheet from the database.  Then, gets all the fields and parameters for it.  So it returns a fully functional sheet.
    */
    public static Sheet getSheet(long sheetNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        Sheet sheet = createObject(sheetNum);
        SheetFields.getFieldsAndParameters(sheet);
        return sheet;
    }

    /**
    * This is normally done in FormSheetFillEdit, but if we bypass that window for some reason, we can also save a new sheet here.  Does not save any drawings.  Does not save signatures.  Does not save any parameters (PatNum parameters never get saved anyway).
    */
    public static void saveNewSheet(Sheet sheet) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!sheet.getIsNew())
        {
            throw new Exception("Only new sheets allowed");
        }
         
        insert(sheet);
        for (Object __dummyForeachVar0 : sheet.SheetFields)
        {
            SheetField fld = (SheetField)__dummyForeachVar0;
            fld.SheetNum = sheet.SheetNum;
            SheetFields.insert(fld);
        }
    }

    /**
    * Used in FormRefAttachEdit to show all referral slips for the patient/referral combo.  Usually 0 or 1 results.
    */
    public static List<Sheet> getReferralSlips(long patNum, long referralNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Sheet>>GetObject(MethodBase.GetCurrentMethod(), patNum, referralNum);
        }
         
        String command = "SELECT * FROM sheet WHERE PatNum=" + POut.long(patNum) + " AND EXISTS(SELECT * FROM sheetfield " + "WHERE sheet.SheetNum=sheetfield.SheetNum " + "AND sheetfield.FieldType=" + POut.Long(((Enum)SheetFieldType.Parameter).ordinal()) + " AND sheetfield.FieldName='ReferralNum' " + "AND sheetfield.FieldValue='" + POut.long(referralNum) + "')" + " ORDER BY DateTimeSheet";
        return Crud.SheetCrud.SelectMany(command);
    }

    /**
    * Used in FormLabCaseEdit to view an existing lab slip.  Will return null if none exist.
    */
    public static Sheet getLabSlip(long patNum, long labCaseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Sheet>GetObject(MethodBase.GetCurrentMethod(), patNum, labCaseNum);
        }
         
        String command = "SELECT sheet.* FROM sheet,sheetfield " + "WHERE sheet.SheetNum=sheetfield.SheetNum" + " AND sheet.PatNum=" + POut.long(patNum) + " AND sheet.SheetType=" + POut.Long(((Enum)SheetTypeEnum.LabSlip).ordinal()) + " AND sheetfield.FieldType=" + POut.Long(((Enum)SheetFieldType.Parameter).ordinal()) + " AND sheetfield.FieldName='LabCaseNum' " + "AND sheetfield.FieldValue='" + POut.long(labCaseNum) + "'";
        return Crud.SheetCrud.SelectOne(command);
    }

    /**
    * Used in FormRxEdit to view an existing rx.  Will return null if none exist.
    */
    public static Sheet getRx(long patNum, long rxNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Sheet>GetObject(MethodBase.GetCurrentMethod(), patNum, rxNum);
        }
         
        String command = "SELECT sheet.* FROM sheet,sheetfield " + "WHERE sheet.PatNum=" + POut.long(patNum) + " AND sheet.SheetType=" + POut.Long(((Enum)SheetTypeEnum.Rx).ordinal()) + " AND sheetfield.FieldType=" + POut.Long(((Enum)SheetFieldType.Parameter).ordinal()) + " AND sheetfield.FieldName='RxNum' " + "AND sheetfield.FieldValue='" + POut.long(rxNum) + "'";
        return Crud.SheetCrud.SelectOne(command);
    }

    /**
    * Gets all sheets for a patient that have the terminal flag set.
    */
    public static List<Sheet> getForTerminal(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Sheet>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM sheet WHERE PatNum=" + POut.long(patNum) + " AND ShowInTerminal > 0 ORDER BY ShowInTerminal";
        return Crud.SheetCrud.SelectMany(command);
    }

    /**
    * Get all sheets for a patient for today.
    */
    public static List<Sheet> getForPatientForToday(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Sheet>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String datesql = "CURDATE()";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            datesql = "(SELECT CURRENT_DATE FROM dual)";
        }
         
        String command = "SELECT * FROM sheet WHERE PatNum=" + POut.long(patNum) + " AND " + DbHelper.dateColumn("DateTimeSheet") + " = " + datesql;
        return Crud.SheetCrud.SelectMany(command);
    }

    /**
    * Get all sheets for a patient.
    */
    public static List<Sheet> getForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Sheet>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM sheet WHERE PatNum=" + POut.long(patNum);
        return Crud.SheetCrud.SelectMany(command);
    }

    /**
    * Gets the most recent Exam Sheet based on description to fill a patient letter.
    */
    public static Sheet getMostRecentExamSheet(long patNum, String examDescript) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Sheet>GetObject(MethodBase.GetCurrentMethod(), patNum, examDescript);
        }
         
        String command = "SELECT * FROM sheet WHERE DateTimeSheet=" + "(SELECT MAX(DateTimeSheet) FROM sheet WHERE PatNum=" + POut.long(patNum) + " " + "AND Description='" + POut.string(examDescript) + "') " + "AND PatNum=" + POut.long(patNum) + " " + "AND Description='" + POut.string(examDescript) + "' " + "LIMIT 1";
        return Crud.SheetCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(Sheet sheet) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sheet.SheetNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sheet);
            return sheet.SheetNum;
        }
         
        return Crud.SheetCrud.Insert(sheet);
    }

    /**
    * 
    */
    public static void update(Sheet sheet) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheet);
            return ;
        }
         
        Crud.SheetCrud.Update(sheet);
    }

    /**
    * 
    */
    public static void deleteObject(long sheetNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sheetNum);
            return ;
        }
         
        String command = "DELETE FROM sheetfield WHERE SheetNum=" + POut.long(sheetNum);
        Db.nonQ(command);
        Crud.SheetCrud.Delete(sheetNum);
    }

    /**
    * Converts parameters into sheetfield objects, and then saves those objects in the database.  The parameters will never again enjoy full parameter status, but will just be read-only fields from here on out.  It ignores PatNum parameters, since those are already part of the sheet itself.
    */
    public static void saveParameters(Sheet sheet) throws Exception {
        //No need to check RemotingRole; no call to db
        SheetField field;
        for (int i = 0;i < sheet.Parameters.Count;i++)
        {
            if (StringSupport.equals(sheet.Parameters[i].ParamName, "PatNum"))
            {
                continue;
            }
             
            field = new SheetField();
            field.setIsNew(true);
            field.SheetNum = sheet.SheetNum;
            field.FieldType = SheetFieldType.Parameter;
            field.FieldName = sheet.Parameters[i].ParamName;
            field.FieldValue = sheet.Parameters[i].ParamValue.ToString();
            //the object will be an int. Stored as a string.
            field.FontSize = 0;
            field.FontName = "";
            field.FontIsBold = false;
            field.XPos = 0;
            field.YPos = 0;
            field.Width = 0;
            field.Height = 0;
            field.GrowthBehavior = GrowthBehaviorEnum.None;
            field.RadioButtonValue = "";
            SheetFields.insert(field);
        }
    }

    /**
    * Loops through all the fields in the sheet and appends together all the FieldValues.  It obviously excludes all SigBox fieldtypes.  It does include Drawing fieldtypes, so any change at all to any drawing will invalidate the signature.  It does include Image fieldtypes, although that's just a filename and does not really have any meaningful data about the image itself.  The order is absolutely critical.
    */
    public static String getSignatureKey(Sheet sheet) throws Exception {
        //No need to check RemotingRole; no call to db
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0;i < sheet.SheetFields.Count;i++)
        {
            if (StringSupport.equals(sheet.SheetFields[i].FieldValue, ""))
            {
                continue;
            }
             
            if (sheet.SheetFields[i].FieldType == SheetFieldType.SigBox)
            {
                continue;
            }
             
            strBuild.Append(sheet.SheetFields[i].FieldValue);
        }
        return strBuild.ToString();
    }

    public static DataTable getPatientFormsTable(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        //DataConnection dcon=new DataConnection();
        DataTable table = new DataTable("");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("date");
        table.Columns.Add("dateOnly", DateTime.class);
        //to help with sorting
        table.Columns.Add("dateTime", DateTime.class);
        table.Columns.Add("description");
        table.Columns.Add("DocNum");
        table.Columns.Add("imageCat");
        table.Columns.Add("SheetNum");
        table.Columns.Add("showInTerminal");
        table.Columns.Add("time");
        table.Columns.Add("timeOnly", TimeSpan.class);
        //to help with sorting
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        //sheet---------------------------------------------------------------------------------------
        String command = "SELECT DateTimeSheet,SheetNum,Description,ShowInTerminal " + "FROM sheet WHERE PatNum =" + POut.long(patNum) + " " + "AND (SheetType=" + POut.Long(((Enum)SheetTypeEnum.PatientForm).ordinal()) + " OR SheetType=" + POut.Long(((Enum)SheetTypeEnum.MedicalHistory).ordinal());
        if (PrefC.getBool(PrefName.PatientFormsShowConsent))
        {
            command += " OR SheetType=" + POut.Long(((Enum)SheetTypeEnum.Consent).ordinal());
        }
         
        //Show consent forms if pref is true.
        command += ")";
        //+"ORDER BY ShowInTerminal";//DATE(DateTimeSheet),ShowInTerminal,TIME(DateTimeSheet)";
        DataTable rawSheet = Db.getTable(command);
        DateTime dateT = new DateTime();
        for (int i = 0;i < rawSheet.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawSheet.Rows[i]["DateTimeSheet"].ToString());
            row["date"] = dateT.ToShortDateString();
            row["dateOnly"] = dateT.Date;
            row["dateTime"] = dateT;
            row["description"] = rawSheet.Rows[i]["Description"].ToString();
            row["DocNum"] = "0";
            row["imageCat"] = "";
            row["SheetNum"] = rawSheet.Rows[i]["SheetNum"].ToString();
            if (StringSupport.equals(rawSheet.Rows[i]["ShowInTerminal"].ToString(), "0"))
            {
                row["showInTerminal"] = "";
            }
            else
            {
                row["showInTerminal"] = rawSheet.Rows[i]["ShowInTerminal"].ToString();
            } 
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["time"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["timeOnly"] = dateT.TimeOfDay;
            rows.Add(row);
        }
        //document---------------------------------------------------------------------------------------
        command = "SELECT DateCreated,DocCategory,DocNum,Description " + "FROM document,definition " + "WHERE document.DocCategory=definition.DefNum" + " AND PatNum =" + POut.long(patNum) + " AND definition.ItemValue LIKE '%F%'";
        //+" ORDER BY DateCreated";
        DataTable rawDoc = Db.getTable(command);
        long docCat = new long();
        for (int i = 0;i < rawDoc.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawDoc.Rows[i]["DateCreated"].ToString());
            row["date"] = dateT.ToShortDateString();
            row["dateOnly"] = dateT.Date;
            row["dateTime"] = dateT;
            row["description"] = rawDoc.Rows[i]["Description"].ToString();
            row["DocNum"] = rawDoc.Rows[i]["DocNum"].ToString();
            docCat = PIn.Long(rawDoc.Rows[i]["DocCategory"].ToString());
            row["imageCat"] = DefC.getName(DefCat.ImageCats,docCat);
            row["SheetNum"] = "0";
            row["showInTerminal"] = "";
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["time"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["timeOnly"] = dateT.TimeOfDay;
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            //Sorting
            table.Rows.Add(rows[i]);
        }
        DataView view = table.DefaultView;
        view.Sort = "dateOnly,showInTerminal,timeOnly";
        table = view.ToTable();
        return table;
    }

    /**
    * Returns all sheets for the given patient in the given date range which have a description matching the examDescript in a case insensitive manner. If examDescript is blank, then sheets with any description are returned.
    */
    public static List<Sheet> getExamSheetsTable(long patNum, DateTime startDate, DateTime endDate, String examDescript) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Sheet>>GetObject(MethodBase.GetCurrentMethod(), patNum, startDate, endDate, examDescript);
        }
         
        String command = "SELECT * " + "FROM sheet WHERE PatNum=" + POut.long(patNum) + " " + "AND SheetType=" + POut.int(((Enum)SheetTypeEnum.ExamSheet).ordinal()) + " ";
        if (!StringSupport.equals(examDescript, ""))
        {
            command += "AND Description LIKE '%" + POut.string(examDescript) + "%' ";
        }
         
        //case insensitive text matches
        command += "AND " + DbHelper.dateColumn("DateTimeSheet") + ">=" + POut.date(startDate) + " AND " + DbHelper.dateColumn("DateTimeSheet") + "<=" + POut.date(endDate) + " " + "ORDER BY DateTimeSheet";
        return Crud.SheetCrud.SelectMany(command);
    }

    /**
    * Used to get sheets filled via the web.
    */
    public static DataTable getWebFormSheetsTable(DateTime dateFrom, DateTime dateTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateFrom, dateTo);
        }
         
        DataTable table = new DataTable("");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("date");
        table.Columns.Add("dateOnly", DateTime.class);
        //to help with sorting
        table.Columns.Add("dateTime", DateTime.class);
        table.Columns.Add("description");
        table.Columns.Add("time");
        table.Columns.Add("timeOnly", TimeSpan.class);
        //to help with sorting
        table.Columns.Add("PatNum");
        table.Columns.Add("SheetNum");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT DateTimeSheet,Description,PatNum,SheetNum " + "FROM sheet WHERE " + "DateTimeSheet >= " + POut.date(dateFrom) + " AND DateTimeSheet <= " + POut.Date(dateTo.AddDays(1)) + " " + "AND IsWebForm = " + POut.bool(true) + " " + "AND (SheetType=" + POut.Long(((Enum)SheetTypeEnum.PatientForm).ordinal()) + " OR SheetType=" + POut.Long(((Enum)SheetTypeEnum.MedicalHistory).ordinal()) + ") ";
        DataTable rawSheet = Db.getTable(command);
        DateTime dateT = new DateTime();
        for (int i = 0;i < rawSheet.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawSheet.Rows[i]["DateTimeSheet"].ToString());
            row["date"] = dateT.ToShortDateString();
            row["dateOnly"] = dateT.Date;
            row["dateTime"] = dateT;
            row["description"] = rawSheet.Rows[i]["Description"].ToString();
            row["PatNum"] = rawSheet.Rows[i]["PatNum"].ToString();
            row["SheetNum"] = rawSheet.Rows[i]["SheetNum"].ToString();
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["time"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["timeOnly"] = dateT.TimeOfDay;
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        DataView view = table.DefaultView;
        view.Sort = "dateOnly,timeOnly";
        table = view.ToTable();
        return table;
    }

    public static boolean containsStaticField(Sheet sheet, String fieldName) throws Exception {
        for (Object __dummyForeachVar1 : sheet.SheetFields)
        {
            //No need to check RemotingRole; no call to db
            SheetField field = (SheetField)__dummyForeachVar1;
            if (field.FieldType != SheetFieldType.StaticText)
            {
                continue;
            }
             
            if (field.FieldValue.Contains("[" + fieldName + "]"))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * 
    */
    public static byte getBiggestShowInTerminal(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<byte>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT MAX(ShowInTerminal) FROM sheet WHERE PatNum=" + POut.long(patNum);
        return PIn.byte(Db.getScalar(command));
    }

    /**
    * 
    */
    public static void clearFromTerminal(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE sheet SET ShowInTerminal=0 WHERE PatNum=" + POut.long(patNum);
        Db.nonQ(command);
    }

}


