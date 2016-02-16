//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormLabCaseSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Laboratories;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLabCaseSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    private OpenDental.UI.Button butAdd;
    public long PatNum = new long();
    /**
    * This only has a value when DialogResult=OK.
    */
    public long SelectedLabCaseNum = new long();
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    private List<LabCase> labCaseList = new List<LabCase>();
    public boolean IsPlanned = new boolean();
    /**
    * 
    */
    public FormLabCaseSelect() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLabCaseSelect.class);
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
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
        this.butAdd.Location = new System.Drawing.Point(12, 213);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(81, 26);
        this.butAdd.TabIndex = 127;
        this.butAdd.Text = "New";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(407, 213);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 8;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(498, 213);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 46);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(561, 145);
        this.gridMain.TabIndex = 128;
        this.gridMain.setTitle("Lab Cases");
        this.gridMain.setTranslationName("TableLabCaseSelect");
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(562, 34);
        this.label1.TabIndex = 130;
        this.label1.Text = "Select a lab case from the list below or create a new one.  This list will not sh" + "ow lab cases that are already attached to other appointments.";
        //
        // FormLabCaseSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(599, 257);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLabCaseSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Lab Case";
        this.Load += new System.EventHandler(this.FormLabCaseSelect_Load);
        this.ResumeLayout(false);
    }

    private void formLabCaseSelect_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
        if (labCaseList.Count > 0)
        {
            gridMain.setSelected(0,true);
        }
         
    }

    private void fillGrid() throws Exception {
        labCaseList = LabCases.getForPat(PatNum,IsPlanned);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableLabCaseSelect","Date Created"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCaseSelect","Lab"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCaseSelect","Phone"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabCaseSelect","Instructions"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DateTime dateCreated = new DateTime();
        Laboratory lab;
        for (int i = 0;i < labCaseList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            dateCreated = labCaseList[i].DateTimeCreated;
            row.getCells().Add(dateCreated.ToString("ddd") + " " + dateCreated.ToShortDateString() + " " + dateCreated.ToShortTimeString());
            lab = Laboratories.GetOne(labCaseList[i].LaboratoryNum);
            row.getCells().add(lab.Description);
            row.getCells().add(lab.Phone);
            row.getCells().Add(labCaseList[i].Instructions);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        LabCase lab = new LabCase();
        lab.PatNum = PatNum;
        Patient pat = Patients.getPat(PatNum);
        lab.ProvNum = Patients.getProvNum(pat);
        lab.DateTimeCreated = MiscData.getNowDateTime();
        LabCases.insert(lab);
        FormLabCaseEdit FormL = new FormLabCaseEdit();
        FormL.CaseCur = lab;
        FormL.IsNew = true;
        FormL.ShowDialog();
        if (FormL.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SelectedLabCaseNum = FormL.CaseCur.LabCaseNum;
        DialogResult = DialogResult.OK;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedLabCaseNum = labCaseList[e.getRow()].LabCaseNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedLabCaseNum = labCaseList[gridMain.getSelectedIndex()].LabCaseNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


