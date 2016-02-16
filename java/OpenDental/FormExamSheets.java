//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormSheetDefs;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormSheetPicker;
import OpenDental.Lan;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetTypeEnum;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormExamSheets;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormExamSheets  extends Form 
{

    //DataTable table;
    private List<Sheet> sheetList = new List<Sheet>();
    public long PatNum = new long();
    public FormExamSheets() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formExamSheets_Load(Object sender, EventArgs e) throws Exception {
        Patient pat = Patients.getLim(PatNum);
        Text = Lan.g(this,"Exam Sheets for") + " " + pat.getNameFL();
        fillListExamTypes();
        fillGrid();
    }

    private void fillListExamTypes() throws Exception {
        listExamTypes.Items.Clear();
        List<SheetDef> sheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.ExamSheet);
        for (int i = 0;i < sheetDefs.Count;i++)
        {
            listExamTypes.Items.Add(sheetDefs[i].Description);
        }
    }

    private void listExamTypes_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        int idx = listExamTypes.IndexFromPoint(e.Location);
        if (idx == -1)
        {
            return ;
        }
         
        textExamDescript.Text = listExamTypes.Items[idx].ToString();
        fillGrid();
    }

    private void textExamDescript_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //if a sheet is selected, remember it
        long selectedSheetNum = 0;
        if (gridMain.getSelectedIndex() != -1)
        {
            selectedSheetNum = sheetList[gridMain.getSelectedIndex()].SheetNum;
        }
         
        //PIn.Long(table.Rows[gridMain.GetSelectedIndex()]["SheetNum"].ToString());
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Time"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),210);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        sheetList = Sheets.GetExamSheetsTable(PatNum, DateTime.MinValue, DateTime.MaxValue, textExamDescript.Text);
        for (int i = 0;i < sheetList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(sheetList[i].DateTimeSheet.ToShortDateString());
            // ["date"].ToString());
            row.getCells().Add(sheetList[i].DateTimeSheet.ToShortTimeString());
            // ["time"].ToString());
            row.getCells().Add(sheetList[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        //reselect
        if (selectedSheetNum != 0)
        {
            for (int i = 0;i < sheetList.Count;i++)
            {
                if (sheetList[i].SheetNum == selectedSheetNum)
                {
                    //table.Rows[i]["SheetNum"].ToString()==selectedSheetNum.ToString()) {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //Sheets
        long sheetNum = sheetList[e.getRow()].SheetNum;
        // PIn.Long(table.Rows[e.Row]["SheetNum"].ToString());
        Sheet sheet = Sheets.getSheet(sheetNum);
        //must use this to get fields
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
        fillListExamTypes();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormSheetPicker FormS = new FormSheetPicker();
        FormS.SheetType = SheetTypeEnum.ExamSheet;
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
        }
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        if (FormSF.DialogResult == DialogResult.OK)
        {
            fillGrid();
            gridMain.setSelected(false);
            //unselect all rows
            gridMain.SetSelected(gridMain.getRows().Count - 1, true);
        }
         
    }

    //Select the newly added row. Always last, since ordered by date.
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormExamSheets.class);
        this.menuStrip1 = new System.Windows.Forms.MenuStrip();
        this.setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemSheets = new System.Windows.Forms.ToolStripMenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.textExamDescript = new System.Windows.Forms.TextBox();
        this.listExamTypes = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
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
        this.menuStrip1.Size = new System.Drawing.Size(441, 24);
        this.menuStrip1.TabIndex = 8;
        this.menuStrip1.Text = "menuStrip1";
        //
        // setupToolStripMenuItem
        //
        this.setupToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemSheets });
        this.setupToolStripMenuItem.Name = "setupToolStripMenuItem";
        this.setupToolStripMenuItem.Size = new System.Drawing.Size(49, 20);
        this.setupToolStripMenuItem.Text = "Setup";
        //
        // menuItemSheets
        //
        this.menuItemSheets.Name = "menuItemSheets";
        this.menuItemSheets.Size = new System.Drawing.Size(108, 22);
        this.menuItemSheets.Text = "Sheets";
        this.menuItemSheets.Click += new System.EventHandler(this.menuItemSheets_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 35);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(199, 16);
        this.label1.TabIndex = 46;
        this.label1.Text = "Show Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textExamDescript
        //
        this.textExamDescript.Location = new System.Drawing.Point(11, 53);
        this.textExamDescript.Name = "textExamDescript";
        this.textExamDescript.Size = new System.Drawing.Size(196, 20);
        this.textExamDescript.TabIndex = 47;
        this.textExamDescript.TextChanged += new System.EventHandler(this.textExamDescript_TextChanged);
        //
        // listExamTypes
        //
        this.listExamTypes.FormattingEnabled = true;
        this.listExamTypes.Location = new System.Drawing.Point(11, 94);
        this.listExamTypes.Name = "listExamTypes";
        this.listExamTypes.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listExamTypes.Size = new System.Drawing.Size(196, 108);
        this.listExamTypes.TabIndex = 48;
        this.listExamTypes.MouseClick += new System.Windows.Forms.MouseEventHandler(this.listExamTypes_MouseClick);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(9, 76);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(156, 16);
        this.label2.TabIndex = 49;
        this.label2.Text = "Custom Types";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
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
        this.butAdd.Location = new System.Drawing.Point(11, 492);
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
        this.gridMain.Location = new System.Drawing.Point(12, 208);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(418, 278);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Exam Sheets");
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
        this.butCancel.Location = new System.Drawing.Point(355, 492);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormExamSheets
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(441, 525);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listExamTypes);
        this.Controls.Add(this.textExamDescript);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.menuStrip1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MainMenuStrip = this.menuStrip1;
        this.Name = "FormExamSheets";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Exam Sheets";
        this.Load += new System.EventHandler(this.FormExamSheets_Load);
        this.menuStrip1.ResumeLayout(false);
        this.menuStrip1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.MenuStrip menuStrip1 = new System.Windows.Forms.MenuStrip();
    private System.Windows.Forms.ToolStripMenuItem setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemSheets = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textExamDescript = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listExamTypes = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


