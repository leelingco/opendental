//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDefinitions;
import OpenDental.FormSheetDefs;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormSheetImport;
import OpenDental.FormSheetPicker;
import OpenDental.FormSheetSetup;
import OpenDental.FormTerminal;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
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
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPatientForms  extends Form 
{

    DataTable table = new DataTable();
    public long PatNum = new long();
    public FormPatientForms() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPatientForms_Load(Object sender, EventArgs e) throws Exception {
        Patient pat = Patients.getLim(PatNum);
        Text = Lan.g(this,"Patient Forms for") + " " + pat.getNameFL();
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //if a sheet is selected, remember it
        long selectedSheetNum = 0;
        if (gridMain.getSelectedIndex() != -1)
        {
            selectedSheetNum = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["SheetNum"].ToString());
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Time"),42);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Kiosk"), 55, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),210);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Image Category"),120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        table = Sheets.getPatientFormsTable(PatNum);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["date"].ToString());
            row.getCells().Add(table.Rows[i]["time"].ToString());
            row.getCells().Add(table.Rows[i]["showInTerminal"].ToString());
            row.getCells().Add(table.Rows[i]["description"].ToString());
            row.getCells().Add(table.Rows[i]["imageCat"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        if (selectedSheetNum != 0)
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (table.Rows[i]["SheetNum"].ToString() == selectedSheetNum.ToString())
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!StringSupport.equals(table.Rows[e.getRow()]["DocNum"].ToString(), "0"))
        {
            long docNum = PIn.Long(table.Rows[e.getRow()]["DocNum"].ToString());
            GotoModule.gotoImage(PatNum,docNum);
            return ;
        }
         
        //Sheets
        long sheetNum = PIn.Long(table.Rows[e.getRow()]["SheetNum"].ToString());
        Sheet sheet = Sheets.getSheet(sheetNum);
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        if (FormSF.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void menuItemSheets_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSheetDefs FormSD = new FormSheetDefs();
        FormSD.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Sheets");
        fillGrid();
    }

    private void menuItemImageCats_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions formD = new FormDefinitions(DefCat.ImageCats);
        formD.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Defs");
        fillGrid();
    }

    private void menuItemOptions_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormSheetSetup formSS = new FormSheetSetup();
        formSS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "ShowForms");
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormSheetPicker FormS = new FormSheetPicker();
        FormS.SheetType = SheetTypeEnum.PatientForm;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDef sheetDef;
        Sheet sheet = null;
        for (int i = 0;i < FormS.SelectedSheetDefs.Count;i++)
        {
            //only useful if not Terminal
            sheetDef = FormS.SelectedSheetDefs[i];
            sheet = SheetUtil.createSheet(sheetDef,PatNum);
            SheetParameter.setParameter(sheet,"PatNum",PatNum);
            SheetFiller.fillFields(sheet);
            SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
            if (FormS.TerminalSend)
            {
                sheet.InternalNote = "";
                //because null not ok
                sheet.ShowInTerminal = (byte)(Sheets.getBiggestShowInTerminal(PatNum) + 1);
                Sheets.saveNewSheet(sheet);
            }
             
        }
        //save each sheet.
        if (FormS.TerminalSend)
        {
            //do not show a dialog now.
            //User will need to click the terminal button.
            fillGrid();
        }
        else
        {
            FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
            FormSF.ShowDialog();
            if (FormSF.DialogResult == DialogResult.OK)
            {
                fillGrid();
            }
             
        } 
    }

    private void butTerminal_Click(Object sender, EventArgs e) throws Exception {
        boolean hasTerminal = false;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (!StringSupport.equals(table.Rows[i]["showInTerminal"].ToString(), ""))
            {
                hasTerminal = true;
                break;
            }
             
        }
        if (!hasTerminal)
        {
            MsgBox.show(this,"No forms for this patient are set to show in the terminal.");
            return ;
        }
         
        FormTerminal formT = new FormTerminal();
        formT.IsSimpleMode = true;
        formT.PatNum = PatNum;
        formT.ShowDialog();
        fillGrid();
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length != 1)
        {
            MsgBox.show(this,"Please select one completed sheet from the list above first.");
            return ;
        }
         
        long sheetNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["SheetNum"].ToString());
        if (sheetNum == 0)
        {
            MsgBox.show(this,"Must select a sheet.");
            return ;
        }
         
        Sheet sheet = Sheets.getSheet(sheetNum);
        Sheet sheet2 = sheet.copy();
        sheet2.DateTimeSheet = DateTime.Now;
        sheet2.SheetFields = new List<SheetField>(sheet.SheetFields);
        for (int i = 0;i < sheet2.SheetFields.Count;i++)
        {
            sheet2.SheetFields[i].IsNew = true;
            if (sheet2.SheetFields[i].FieldType == SheetFieldType.SigBox)
            {
                sheet2.SheetFields[i].FieldValue = "";
            }
             
        }
        //clear signatures
        //no need to set SheetNums here.  That's done from inside FormSheetFillEdit
        sheet2.setIsNew(true);
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet2);
        FormSF.ShowDialog();
        if (FormSF.DialogResult == DialogResult.OK)
        {
            fillGrid();
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (table.Rows[i]["SheetNum"].ToString() == sheet2.SheetNum.ToString())
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
            SecurityLogs.makeLogEntry(Permissions.Copy,PatNum,"Patient form " + sheet.Description + " from " + sheet.DateTimeSheet.ToString() + " copied");
        }
         
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length != 1)
        {
            MsgBox.show(this,"Please select one completed form from the list above first.");
            return ;
        }
         
        long sheetNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["SheetNum"].ToString());
        long docNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["DocNum"].ToString());
        Document doc = null;
        if (docNum != 0)
        {
            doc = Documents.getByNum(docNum);
            String extens = Path.GetExtension(doc.FileName);
            if (!StringSupport.equals(extens.ToLower(), ".pdf"))
            {
                MsgBox.show(this,"Only pdf's and sheets can be imported into the database.");
                return ;
            }
             
        }
         
        Sheet sheet = null;
        if (sheetNum != 0)
        {
            sheet = Sheets.getSheet(sheetNum);
            if (sheet.SheetType != SheetTypeEnum.PatientForm && sheet.SheetType != SheetTypeEnum.MedicalHistory)
            {
                MsgBox.show(this,"For now, only sheets of type 'PatientForm' and 'MedicalHistory' can be imported.");
                return ;
            }
             
        }
         
        FormSheetImport formSI = new FormSheetImport();
        formSI.SheetCur = sheet;
        formSI.DocCur = doc;
        formSI.ShowDialog();
    }

    //No need to refresh grid because no changes could have been made.
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
        this.menuStrip1 = new System.Windows.Forms.MenuStrip();
        this.setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemSheets = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemImageCats = new System.Windows.Forms.ToolStripMenuItem();
        this.showFormsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.butCopy = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butTerminal = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butCancel = new OpenDental.UI.Button();
        this.menuStrip1.SuspendLayout();
        this.SuspendLayout();
        //
        // menuStrip1
        //
        this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.setupToolStripMenuItem });
        this.menuStrip1.Location = new System.Drawing.Point(0, 0);
        this.menuStrip1.Name = "menuStrip1";
        this.menuStrip1.Size = new System.Drawing.Size(615, 24);
        this.menuStrip1.TabIndex = 8;
        this.menuStrip1.Text = "menuStrip1";
        //
        // setupToolStripMenuItem
        //
        this.setupToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemSheets, this.menuItemImageCats, this.showFormsToolStripMenuItem });
        this.setupToolStripMenuItem.Name = "setupToolStripMenuItem";
        this.setupToolStripMenuItem.Size = new System.Drawing.Size(49, 20);
        this.setupToolStripMenuItem.Text = "Setup";
        //
        // menuItemSheets
        //
        this.menuItemSheets.Name = "menuItemSheets";
        this.menuItemSheets.Size = new System.Drawing.Size(166, 22);
        this.menuItemSheets.Text = "Sheets";
        this.menuItemSheets.Click += new System.EventHandler(this.menuItemSheets_Click);
        //
        // menuItemImageCats
        //
        this.menuItemImageCats.Name = "menuItemImageCats";
        this.menuItemImageCats.Size = new System.Drawing.Size(166, 22);
        this.menuItemImageCats.Text = "Image Categories";
        this.menuItemImageCats.Click += new System.EventHandler(this.menuItemImageCats_Click);
        //
        // showFormsToolStripMenuItem
        //
        this.showFormsToolStripMenuItem.Name = "showFormsToolStripMenuItem";
        this.showFormsToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
        this.showFormsToolStripMenuItem.Text = "Options";
        this.showFormsToolStripMenuItem.Click += new System.EventHandler(this.menuItemOptions_Click);
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(328, 588);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(173, 18);
        this.label1.TabIndex = 10;
        this.label1.Text = "(from form into database)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy.Location = new System.Drawing.Point(170, 585);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(75, 24);
        this.butCopy.TabIndex = 11;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butImport.Location = new System.Drawing.Point(249, 585);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 24);
        this.butImport.TabIndex = 9;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butTerminal
        //
        this.butTerminal.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTerminal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butTerminal.setAutosize(true);
        this.butTerminal.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTerminal.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTerminal.setCornerRadius(4F);
        this.butTerminal.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butTerminal.Location = new System.Drawing.Point(91, 585);
        this.butTerminal.Name = "butTerminal";
        this.butTerminal.Size = new System.Drawing.Size(75, 24);
        this.butTerminal.TabIndex = 7;
        this.butTerminal.Text = "Kiosk";
        this.butTerminal.Click += new System.EventHandler(this.butTerminal_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(12, 585);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 6;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 27);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(592, 538);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Patient Forms and Medical Histories");
        this.gridMain.setTranslationName("FormPatientForms");
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
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(530, 585);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPatientForms
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(615, 618);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.butTerminal);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.menuStrip1);
        this.MainMenuStrip = this.menuStrip1;
        this.Name = "FormPatientForms";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Forms and Medical Histories";
        this.Load += new System.EventHandler(this.FormPatientForms_Load);
        this.menuStrip1.ResumeLayout(false);
        this.menuStrip1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butTerminal;
    private System.Windows.Forms.MenuStrip menuStrip1 = new System.Windows.Forms.MenuStrip();
    private System.Windows.Forms.ToolStripMenuItem setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemSheets = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemImageCats = new System.Windows.Forms.ToolStripMenuItem();
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCopy;
    private System.Windows.Forms.ToolStripMenuItem showFormsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
}


