//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.FormPatientForms;
import OpenDental.FormPatientPickWebForm;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormWebFormSetup;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.SheetUtil;
import OpenDental.UI.ODGridColumn;
import OpenDental.WebSheets.Sheets;
import OpenDental.WebSheets.webforms_sheetfield;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetTypeEnum;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWebForms  extends Form 
{

    /**
    * This Form does 3 things:
    * 1) Retrieve data of filled out web forms from a web service and convert them into sheets and patients. Using the first name, last name and birth date it will check for existing patients. If an existing patient is found a new sheet is created. If no patient is found, a  patient and a sheet is created.
    * 2) Send a list of the Sheets that have been created to the Server for deletion.
    * 3)Show all the sheets that have been created in 1) using a date filter.
    */
    public FormWebForms() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWebForms_Load(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        DateTime dateFrom = DateTimeOD.getToday();
        DateTime dateTo = DateTimeOD.getToday();
        try
        {
            dateFrom = PIn.Date(textDateStart.Text);
            //handles blank
            if (!StringSupport.equals(textDateEnd.Text, ""))
            {
                //if it is blank, default to today
                dateTo = PIn.Date(textDateEnd.Text);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Invalid date");
            return ;
        }

        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Time"),42);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient Last Name"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient First Name"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),210);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        DataTable table = Sheets.getWebFormSheetsTable(dateFrom,dateTo);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            long patNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            long sheetNum = PIn.Long(table.Rows[i]["SheetNum"].ToString());
            Patient pat = Patients.getPat(patNum);
            if (pat != null)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(table.Rows[i]["date"].ToString());
                row.getCells().Add(table.Rows[i]["time"].ToString());
                row.getCells().add(pat.LName);
                row.getCells().add(pat.FName);
                row.getCells().Add(table.Rows[i]["description"].ToString());
                row.setTag(sheetNum);
                gridMain.getRows().add(row);
            }
             
        }
        gridMain.endUpdate();
    }

    private void retrieveAndSaveData() throws Exception {
        try
        {
            Sheets wh = new Sheets();
            wh.Timeout = 300000;
            //5 minutes.  Default is 100000 (1.66667 minutes).
            wh.setUrl(PrefC.getString(PrefName.WebHostSynchServerURL));
            String RegistrationKey = PrefC.getString(PrefName.RegistrationKey);
            if (wh.getDentalOfficeID(RegistrationKey) == 0)
            {
                MsgBox.show(this,"Registration key provided by the dental office is incorrect");
                return ;
            }
             
            OpenDental.WebSheets.SheetAndSheetField[] arraySheets = wh.getSheets(RegistrationKey);
            List<long> SheetsForDeletion = new List<long>();
            if (arraySheets.Count() == 0)
            {
                MsgBox.show(this,"No Patient forms retrieved from server");
                return ;
            }
             
            for (int i = 0;i < arraySheets.Length;i++)
            {
                //loop through all incoming sheets
                try
                {
                    //this try catch is put so that a defective downloaded sheet does not stop other sheets from being downloaded.
                    long patNum = 0;
                    String lName = "";
                    String fName = "";
                    DateTime bDate = DateTime.MinValue;
                    for (int j = 0;j < arraySheets[i].web_sheetfieldlist.Count();j++)
                    {
                        //loop through each field in this sheet to find First name, last name, and DOB
                        if (arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("lname") || arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("lastname"))
                        {
                            lName = arraySheets[i].web_sheetfieldlist[j].FieldValue;
                        }
                         
                        if (arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("fname") || arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("firstname"))
                        {
                            fName = arraySheets[i].web_sheetfieldlist[j].FieldValue;
                        }
                         
                        if (arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("bdate") || arraySheets[i].web_sheetfieldlist[j].FieldName.ToLower().Contains("birthdate"))
                        {
                            bDate = PIn.Date(arraySheets[i].web_sheetfieldlist[j].FieldValue);
                        }
                         
                    }
                    if (bDate.Year < 1880)
                    {
                    }
                     
                    //log invalid birth date  format. Shouldn't happen, though.
                    patNum = Patients.getPatNumByNameAndBirthday(lName,fName,bDate);
                    if (patNum == 0)
                    {
                        FormPatientPickWebForm FormPpw = new FormPatientPickWebForm();
                        FormPpw.LnameEntered = lName;
                        FormPpw.FnameEntered = fName;
                        FormPpw.BdateEntered = bDate;
                        FormPpw.ShowDialog();
                        if (FormPpw.DialogResult == DialogResult.Cancel)
                        {
                            break;
                        }
                        else //user wants to stop importing altogether
                        //we will pick up where we left off here next time
                        if (FormPpw.DialogResult == DialogResult.Ignore)
                        {
                            continue;
                        }
                          
                        //user wants to skip this patient import only
                        //future feature suggestion... 4th state = discard
                        //mark this patient's import sheet for delete go to the next patient
                        //SheetsForDeletion.Add(arraySheets[i].web_sheet.SheetID);
                        //continue
                        patNum = FormPpw.SelectedPatNum;
                    }
                    else
                    {
                        //might be zero to indicate new patient
                        //A match was found so make a log entry what the match was.
                        Patient pat = Patients.getPat(patNum);
                        //Security log for OD automatically importing a sheet into a patient.
                        SecurityLogs.makeLogEntry(Permissions.SheetEdit,patNum,"Web form import from: " + lName + ", " + fName + " " + bDate.ToShortDateString() + "\r\nAuto imported into: " + pat.LName + ", " + pat.FName + " " + pat.Birthdate.ToShortDateString());
                    } 
                    if (patNum == 0)
                    {
                        Patient newPat = CreatePatient(lName, fName, bDate, arraySheets[i]);
                        patNum = newPat.PatNum;
                        //Security log for user creating a new patient.
                        SecurityLogs.makeLogEntry(Permissions.SheetEdit,patNum,"Web form import from: " + lName + ", " + fName + " " + bDate.ToShortDateString() + "\r\nUser created new pat: " + newPat.LName + ", " + newPat.FName + " " + newPat.Birthdate.ToShortDateString());
                    }
                     
                    Sheet newSheet = CreateSheet(patNum, arraySheets[i]);
                    if (dataExistsInDb(newSheet))
                    {
                        SheetsForDeletion.Add(arraySheets[i].web_sheet.SheetID);
                    }
                     
                }
                catch (Exception e)
                {
                    MessageBox.Show(e.Message);
                }
            
            }
            // end of for loop
            wh.DeleteSheetData(RegistrationKey, SheetsForDeletion.ToArray());
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
            return ;
        }

        MsgBox.show(this,"Done");
    }

    /**
    * Compares values of the sheet with values that have been inserted into the db.  Returns false if the data was not saved properly.
    */
    private boolean dataExistsInDb(Sheet sheet) throws Exception {
        boolean dataExistsInDb = true;
        if (sheet != null)
        {
            long SheetNum = sheet.SheetNum;
            Sheet sheetFromDb = Sheets.getSheet(SheetNum);
            if (sheetFromDb != null)
            {
                dataExistsInDb = compareSheets(sheetFromDb,sheet);
            }
             
        }
         
        return dataExistsInDb;
    }

    /**
    * This method is used only for testing with security certificates that has problems.
    */
    private void ignoreCertificateErrors() throws Exception {
        /**
        * the line below will allow the code to continue by not throwing an exception.
        * It will accept the security certificate if there is a problem with the security certificate.
        */
        System.Net.ServicePointManager.ServerCertificateValidationCallback += ;
    }

    /**
    * do stuff here and return true or false accordingly.
    * In this particular case it always returns true i.e accepts any certificate.
    */
    /* sample code 
    				if(sslPolicyErrors==System.Net.Security.SslPolicyErrors.None) return true;
    				// the sample below allows expired certificates
    				foreach(X509ChainStatus s in chain.ChainStatus) {
    					// allows expired certificates
    					if(string.Equals(s.Status.ToString(),"NotTimeValid",
    						StringComparison.OrdinalIgnoreCase)) {
    						return true;
    					}						
    				}*/
    /**
    * 
    */
    private Patient createPatient(String LastName, String FirstName, DateTime birthDate, OpenDental.WebSheets.SheetAndSheetField sAnds) throws Exception {
        Patient newPat = new Patient();
        newPat.LName = LastName;
        newPat.FName = FirstName;
        newPat.Birthdate = birthDate;
        newPat.ClinicNum = Security.getCurUser().ClinicNum;
        Type t = newPat.GetType();
        FieldInfo[] fi = t.GetFields();
        for (Object __dummyForeachVar0 : fi)
        {
            FieldInfo field = (FieldInfo)__dummyForeachVar0;
            // find match for fields in Patients in the web_sheetfieldlist
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ WebSheetFieldList = sAnds.getweb_sheetfieldlist().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sf) => {
                return sf.FieldName.ToLower() == field.Name.ToLower();
            }" */);
            if (WebSheetFieldList.Count() > 0)
            {
                for (int i = 0;i < WebSheetFieldList.Count();i++)
                {
                    // this loop is used to fill a field that may generate mutiple values for a single field in the patient.
                    //for example the field gender has 2 eqivalent sheet fields in the web_sheetfieldlist
                    webforms_sheetfield sf = WebSheetFieldList.ElementAt(i);
                    String SheetWebFieldValue = sf.getFieldValue();
                    String RadioButtonValue = sf.getRadioButtonValue();
                    FillPatientFields(newPat, field, SheetWebFieldValue, RadioButtonValue);
                }
            }
             
        }
        try
        {
            Patients.insert(newPat,false);
            //set Guarantor field the same as PatNum
            Patient patOld = newPat.copy();
            newPat.Guarantor = newPat.PatNum;
            Patients.update(newPat,patOld);
        }
        catch (Exception e)
        {
            gridMain.endUpdate();
            MessageBox.Show(e.Message);
        }

        return newPat;
    }

    /**
    * 
    */
    private Sheet createSheet(long PatNum, OpenDental.WebSheets.SheetAndSheetField sAnds) throws Exception {
        Sheet newSheet = null;
        try
        {
            SheetDef sheetDef = new SheetDef(SheetTypeEnum.values()[sAnds.getweb_sheet().getSheetType()]);
            newSheet = SheetUtil.createSheet(sheetDef,PatNum);
            SheetParameter.setParameter(newSheet,"PatNum",PatNum);
            newSheet.DateTimeSheet = sAnds.getweb_sheet().getDateTimeSheet();
            newSheet.Description = sAnds.getweb_sheet().getDescription();
            newSheet.Height = sAnds.getweb_sheet().getHeight();
            newSheet.Width = sAnds.getweb_sheet().getWidth();
            newSheet.FontName = sAnds.getweb_sheet().getFontName();
            newSheet.FontSize = sAnds.getweb_sheet().getFontSize();
            newSheet.SheetType = SheetTypeEnum.values()[sAnds.getweb_sheet().getSheetType()];
            newSheet.IsLandscape = sAnds.getweb_sheet().getIsLandscape() == (sbyte)1 ? true : false;
            newSheet.InternalNote = "";
            newSheet.IsWebForm = true;
            for (int i = 0;i < sAnds.getweb_sheetfieldlist().Count();i++)
            {
                //loop through each variable in a single sheetfield
                SheetField sheetfield = new SheetField();
                sheetfield.FieldName = sAnds.getweb_sheetfieldlist()[i].FieldName;
                sheetfield.FieldType = (SheetFieldType)sAnds.getweb_sheetfieldlist()[i].FieldType;
                //sheetfield.FontIsBold=sAnds.web_sheetfieldlist[i].FontIsBold==(sbyte)1?true:false;
                if (sAnds.getweb_sheetfieldlist()[i].FontIsBold == (sbyte)1)
                {
                    sheetfield.FontIsBold = true;
                }
                else
                {
                    sheetfield.FontIsBold = false;
                } 
                sheetfield.FontIsBold = sAnds.getweb_sheetfieldlist()[i].FontIsBold == (sbyte)1 ? true : false;
                sheetfield.FontName = sAnds.getweb_sheetfieldlist()[i].FontName;
                sheetfield.FontSize = sAnds.getweb_sheetfieldlist()[i].FontSize;
                sheetfield.Height = sAnds.getweb_sheetfieldlist()[i].Height;
                sheetfield.Width = sAnds.getweb_sheetfieldlist()[i].Width;
                sheetfield.XPos = sAnds.getweb_sheetfieldlist()[i].XPos;
                sheetfield.YPos = sAnds.getweb_sheetfieldlist()[i].YPos;
                //sheetfield.IsRequired=sAnds.web_sheetfieldlist[i].IsRequired==(sbyte)1?true:false;
                if (sAnds.getweb_sheetfieldlist()[i].IsRequired == (sbyte)1)
                {
                    sheetfield.IsRequired = true;
                }
                else
                {
                    sheetfield.IsRequired = false;
                } 
                sheetfield.TabOrder = sAnds.getweb_sheetfieldlist()[i].TabOrder;
                sheetfield.RadioButtonGroup = sAnds.getweb_sheetfieldlist()[i].RadioButtonGroup;
                sheetfield.RadioButtonValue = sAnds.getweb_sheetfieldlist()[i].RadioButtonValue;
                sheetfield.GrowthBehavior = (GrowthBehaviorEnum)sAnds.getweb_sheetfieldlist()[i].GrowthBehavior;
                sheetfield.FieldValue = sAnds.getweb_sheetfieldlist()[i].FieldValue;
                newSheet.SheetFields.Add(sheetfield);
            }
            // end of j loop
            Sheets.saveNewSheet(newSheet);
            return newSheet;
        }
        catch (Exception e)
        {
            gridMain.endUpdate();
            MessageBox.Show(e.Message);
        }

        return newSheet;
    }

    /**
    * 
    */
    private void fillPatientFields(Patient newPat, FieldInfo field, String SheetWebFieldValue, String RadioButtonValue) throws Exception {
        try
        {
            Name __dummyScrutVar0 = field.Name;
            if (__dummyScrutVar0.equals("Birthdate"))
            {
                DateTime birthDate = PIn.Date(SheetWebFieldValue);
                field.SetValue(newPat, birthDate);
            }
            else if (__dummyScrutVar0.equals("Gender"))
            {
                if (StringSupport.equals(RadioButtonValue, "Male"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, PatientGender.Male);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Female"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, PatientGender.Female);
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("Position"))
            {
                if (StringSupport.equals(RadioButtonValue, "Married"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, PatientPosition.Married);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Single"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, PatientPosition.Single);
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("PreferContactMethod") || __dummyScrutVar0.equals("PreferConfirmMethod") || __dummyScrutVar0.equals("PreferRecallMethod"))
            {
                if (StringSupport.equals(RadioButtonValue, "HmPhone"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, ContactMethod.HmPhone);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "WkPhone"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, ContactMethod.WkPhone);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "WirelessPh"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, ContactMethod.WirelessPh);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Email"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, ContactMethod.Email);
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("StudentStatus"))
            {
                if (StringSupport.equals(RadioButtonValue, "Nonstudent"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, "");
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Fulltime"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, "F");
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Parttime"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, "P");
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar0.equals("ins1Relat") || __dummyScrutVar0.equals("ins2Relat"))
            {
                if (StringSupport.equals(RadioButtonValue, "Self"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, Relat.Self);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Spouse"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, Relat.Spouse);
                    }
                     
                }
                 
                if (StringSupport.equals(RadioButtonValue, "Child"))
                {
                    if (StringSupport.equals(SheetWebFieldValue, "X"))
                    {
                        field.SetValue(newPat, Relat.Child);
                    }
                     
                }
                 
            }
            else
            {
                field.SetValue(newPat, SheetWebFieldValue);
            }      
        }
        catch (Exception e)
        {
            //switch case
            gridMain.endUpdate();
            MessageBox.Show(field.Name + e.Message);
        }
    
    }

    /**
    * 
    */
    private boolean compareSheets(Sheet sheetFromDb, Sheet newSheet) throws Exception {
        boolean isEqual = true;
        //the 2 sheets are sorted before comparison because in some cases SheetFields[i] refers to a different field in sheetFromDb than in newSheet
        Sheet sortedSheetFromDb = new Sheet();
        Sheet sortedNewSheet = new Sheet();
        sortedSheetFromDb.SheetFields = sheetFromDb.SheetFields.OrderBy(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sf) => {
            return sf.SheetFieldNum;
        }" */).ToList();
        sortedNewSheet.SheetFields = newSheet.SheetFields.OrderBy(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sf) => {
            return sf.SheetFieldNum;
        }" */).ToList();
        for (int i = 0;i < sortedSheetFromDb.SheetFields.Count;i++)
        {
            for (Object __dummyForeachVar1 : sortedSheetFromDb.SheetFields[i].GetType().GetFields())
            {
                // read each parameter of the SheetField like Fontsize,FieldValue, FontIsBold, XPos, YPos etc.
                FieldInfo fieldinfo = (FieldInfo)__dummyForeachVar1;
                String dbSheetFieldValue = "";
                String newSheetFieldValue = "";
                //.ToString() works for Int64, Int32, Enum, DateTime(bithdate), Boolean, Double
                if (fieldinfo.GetValue(sortedSheetFromDb.SheetFields[i]) != null)
                {
                    dbSheetFieldValue = fieldinfo.GetValue(sortedSheetFromDb.SheetFields[i]).ToString();
                }
                 
                if (fieldinfo.GetValue(newSheet.SheetFields[i]) != null)
                {
                    newSheetFieldValue = fieldinfo.GetValue(sortedNewSheet.SheetFields[i]).ToString();
                }
                 
                if (!StringSupport.equals(dbSheetFieldValue, newSheetFieldValue))
                {
                    isEqual = false;
                }
                 
            }
        }
        return isEqual;
    }

    private void butRetrieve_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        //this.backgroundWorker1.RunWorkerAsync(); call this  method if theread is to be used later.
        retrieveAndSaveData();
        // if a thread is used this method will go into backgroundWorker1_DoWork
        fillGrid();
        // if a thread is used this method will go into backgroundWorker1_RunWorkerCompleted
        Cursor = Cursors.Default;
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            FormWebFormSetup formW = new FormWebFormSetup();
            formW.ShowDialog();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long sheetNum = (Long)gridMain.getRows().get___idx(e.getRow()).getTag();
        Sheet sheet = Sheets.getSheet(sheetNum);
        GotoModule.gotoFamily(sheet.PatNum);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long sheetNum = (Long)gridMain.getRows().get___idx(e.getRow()).getTag();
        Sheet sheet = Sheets.getSheet(sheetNum);
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        fillGrid();
    }

    //We must refresh the grid because the web form clicked might have been deleted by the user.
    private void gridMain_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Right)
        {
            menuWebFormsRight.Show(gridMain, new Point(e.X, e.Y));
        }
         
    }

    /*
    		private void menuItemViewSheet_Click(object sender,EventArgs e) {
    			long sheetNum=(long)gridMain.Rows[gridMain.SelectedIndices[0]].Tag;
    			Sheet sheet=Sheets.GetSheet(sheetNum);
    			FormSheetFillEdit FormSF=new FormSheetFillEdit(sheet);
    			FormSF.ShowDialog();
    		}
    		private void menuItemImportSheet_Click(object sender,EventArgs e) {
    			long sheetNum=(long)gridMain.Rows[gridMain.SelectedIndices[0]].Tag;
    			Sheet sheet=Sheets.GetSheet(sheetNum);
    			FormSheetImport formSI=new FormSheetImport();
    			formSI.SheetCur=sheet;
    			formSI.ShowDialog();
    		}*/
    private void menuWebFormsRight_Popup(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            menuItemViewAllSheets.Visible = false;
        }
        else
        {
            menuItemViewAllSheets.Visible = true;
        } 
    }

    private void menuItemViewAllSheets_Click(Object sender, EventArgs e) throws Exception {
        long sheetNum = (long)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag;
        Sheet sheet = Sheets.getSheet(sheetNum);
        FormPatientForms formP = new FormPatientForms();
        formP.PatNum = sheet.PatNum;
        formP.ShowDialog();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.groupDateRange = new System.Windows.Forms.GroupBox();
        this.butRefresh = new OpenDental.UI.Button();
        this.butToday = new OpenDental.UI.Button();
        this.textDateStart = new OpenDental.ValidDate();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.labelEndDate = new System.Windows.Forms.Label();
        this.textDateEnd = new OpenDental.ValidDate();
        this.menuWebFormsRight = new System.Windows.Forms.ContextMenu();
        this.menuItemViewAllSheets = new System.Windows.Forms.MenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.butRetrieve = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butCancel = new OpenDental.UI.Button();
        this.groupDateRange.SuspendLayout();
        this.SuspendLayout();
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // groupDateRange
        //
        this.groupDateRange.Controls.Add(this.butRefresh);
        this.groupDateRange.Controls.Add(this.butToday);
        this.groupDateRange.Controls.Add(this.textDateStart);
        this.groupDateRange.Controls.Add(this.labelStartDate);
        this.groupDateRange.Controls.Add(this.labelEndDate);
        this.groupDateRange.Controls.Add(this.textDateEnd);
        this.groupDateRange.Location = new System.Drawing.Point(134, 2);
        this.groupDateRange.Name = "groupDateRange";
        this.groupDateRange.Size = new System.Drawing.Size(245, 69);
        this.groupDateRange.TabIndex = 238;
        this.groupDateRange.TabStop = false;
        this.groupDateRange.Text = "Show Retrieved Forms";
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(158, 39);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(77, 24);
        this.butRefresh.TabIndex = 243;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(158, 14);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(77, 24);
        this.butToday.TabIndex = 242;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // textDateStart
        //
        this.textDateStart.BackColor = System.Drawing.SystemColors.Window;
        this.textDateStart.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textDateStart.Location = new System.Drawing.Point(75, 16);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 223;
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(6, 19);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(69, 14);
        this.labelStartDate.TabIndex = 221;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelEndDate
        //
        this.labelEndDate.Location = new System.Drawing.Point(6, 44);
        this.labelEndDate.Name = "labelEndDate";
        this.labelEndDate.Size = new System.Drawing.Size(69, 14);
        this.labelEndDate.TabIndex = 222;
        this.labelEndDate.Text = "End Date";
        this.labelEndDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(75, 41);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 224;
        //
        // menuWebFormsRight
        //
        this.menuWebFormsRight.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemViewAllSheets });
        this.menuWebFormsRight.Popup += new System.EventHandler(this.menuWebFormsRight_Popup);
        //
        // menuItemViewAllSheets
        //
        this.menuItemViewAllSheets.Index = 0;
        this.menuItemViewAllSheets.Text = "View this patient\'s forms";
        this.menuItemViewAllSheets.Click += new System.EventHandler(this.menuItemViewAllSheets_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(502, 35);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(197, 36);
        this.label1.TabIndex = 239;
        this.label1.Text = "(All retrieved forms are automatically attached to the correct patient)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butRetrieve
        //
        this.butRetrieve.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRetrieve.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRetrieve.setAutosize(true);
        this.butRetrieve.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRetrieve.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRetrieve.setCornerRadius(4F);
        this.butRetrieve.Location = new System.Drawing.Point(536, 8);
        this.butRetrieve.Name = "butRetrieve";
        this.butRetrieve.Size = new System.Drawing.Size(123, 24);
        this.butRetrieve.TabIndex = 46;
        this.butRetrieve.Text = "&Retrieve New Forms";
        this.butRetrieve.Click += new System.EventHandler(this.butRetrieve_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 77);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(647, 290);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Webforms");
        this.gridMain.setTranslationName("TableWebforms");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridMain_MouseUp);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(669, 343);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormWebForms
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(755, 399);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupDateRange);
        this.Controls.Add(this.butRetrieve);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Menu = this.mainMenu1;
        this.Name = "FormWebForms";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Web Forms";
        this.Load += new System.EventHandler(this.FormWebForms_Load);
        this.groupDateRange.ResumeLayout(false);
        this.groupDateRange.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSetup = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.Button butRetrieve;
    private System.Windows.Forms.GroupBox groupDateRange = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butToday;
    private OpenDental.ValidDate textDateStart;
    private System.Windows.Forms.Label labelStartDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelEndDate = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.ContextMenu menuWebFormsRight = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemViewAllSheets = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


