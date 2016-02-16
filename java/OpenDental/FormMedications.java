//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:20 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormMedicationEdit;
import OpenDental.FormMedications;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;

/**
* Summary description for FormBasicTemplate.
*/
public class FormMedications  extends System.Windows.Forms.Form 
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
    public boolean IsSelectionMode = new boolean();
    private OpenDental.UI.Button butAddGeneric;
    private OpenDental.UI.Button butAddBrand;
    private OpenDental.UI.ODGrid gridMain;
    public TextBox textSearch = new TextBox();
    private Label label1 = new Label();
    /**
    * the number returned if using select mode.
    */
    public long SelectedMedicationNum = new long();
    private List<Medication> medList = new List<Medication>();
    /**
    * 
    */
    public FormMedications() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMedications.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butAddGeneric = new OpenDental.UI.Button();
        this.butAddBrand = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(858, 635);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(858, 594);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butAddGeneric
        //
        this.butAddGeneric.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddGeneric.setAutosize(true);
        this.butAddGeneric.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddGeneric.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddGeneric.setCornerRadius(4F);
        this.butAddGeneric.Image = Resources.getAdd();
        this.butAddGeneric.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddGeneric.Location = new System.Drawing.Point(9, 3);
        this.butAddGeneric.Name = "butAddGeneric";
        this.butAddGeneric.Size = new System.Drawing.Size(113, 26);
        this.butAddGeneric.TabIndex = 33;
        this.butAddGeneric.Text = "Add Generic";
        this.butAddGeneric.Click += new System.EventHandler(this.butAddGeneric_Click);
        //
        // butAddBrand
        //
        this.butAddBrand.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddBrand.setAutosize(true);
        this.butAddBrand.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddBrand.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddBrand.setCornerRadius(4F);
        this.butAddBrand.Image = Resources.getAdd();
        this.butAddBrand.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddBrand.Location = new System.Drawing.Point(128, 3);
        this.butAddBrand.Name = "butAddBrand";
        this.butAddBrand.Size = new System.Drawing.Size(113, 26);
        this.butAddBrand.TabIndex = 34;
        this.butAddBrand.Text = "Add Brand";
        this.butAddBrand.Click += new System.EventHandler(this.butAddBrand_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 32);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(836, 630);
        this.gridMain.TabIndex = 37;
        this.gridMain.setTitle("Medications");
        this.gridMain.setTranslationName("FormMedications");
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
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(443, 8);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(195, 20);
        this.textSearch.TabIndex = 0;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(315, 11);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(127, 17);
        this.label1.TabIndex = 39;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormMedications
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(941, 671);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAddBrand);
        this.Controls.Add(this.butAddGeneric);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMedications";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Medications";
        this.Load += new System.EventHandler(this.FormMedications_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formMedications_Load(Object sender, System.EventArgs e) throws Exception {
        //not refreshed in localdata
        fillGrid();
        //if(SelectGenericMode){
        //	this.Text=Lan.g(this,"Select Generic Medication");
        //butAdd.Visible=false;//visible, but it ONLY lets you add a generic
        //}
        if (IsSelectionMode)
        {
            this.Text = Lan.g(this,"Select Medication");
        }
        else
        {
            butOK.Visible = false;
            butCancel.Text = Lan.g(this,"Close");
        } 
    }

    private void fillGrid() throws Exception {
        medList = Medications.GetList(textSearch.Text);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Drug Name"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Generic Name"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"RxNorm"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Notes for Generic"),250);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < medList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (medList[i].MedicationNum == medList[i].GenericNum)
            {
                //isGeneric
                row.getCells().Add(medList[i].MedName);
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(medList[i].MedName);
                row.getCells().Add(Medications.GetGenericName(medList[i].GenericNum));
            } 
            row.getCells().add(medList[i].RxCui == 0 ? "" : medList[i].RxCui.ToString());
            row.getCells().Add(medList[i].Notes);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butLexiComp_Click(Object sender, EventArgs e) throws Exception {
        System.Diagnostics.Process.Start("http://www.lexi.com/ods/");
    }

    private void butAddGeneric_Click(Object sender, System.EventArgs e) throws Exception {
        Medication MedicationCur = new Medication();
        Medications.insert(MedicationCur);
        //so that we will have the primary key
        MedicationCur.GenericNum = MedicationCur.MedicationNum;
        FormMedicationEdit FormME = new FormMedicationEdit();
        FormME.MedicationCur = MedicationCur;
        FormME.IsNew = true;
        FormME.ShowDialog();
        fillGrid();
    }

    private void butAddBrand_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"You must first highlight the generic medication from the list.  If it is not already on the list, then you must add it first."));
            return ;
        }
         
        Medication selected = medList[gridMain.getSelectedIndex()];
        if (selected.MedicationNum != selected.GenericNum)
        {
            MessageBox.Show(Lan.g(this,"The selected medication is not generic."));
            return ;
        }
         
        Medication MedicationCur = new Medication();
        Medications.insert(MedicationCur);
        //so that we will have the primary key
        MedicationCur.GenericNum = selected.MedicationNum;
        FormMedicationEdit FormME = new FormMedicationEdit();
        FormME.MedicationCur = MedicationCur;
        FormME.IsNew = true;
        FormME.ShowDialog();
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedMedicationNum = medList[e.getRow()].MedicationNum;
            DialogResult = DialogResult.OK;
        }
        else
        {
            //normal mode from main menu
            //edit
            FormMedicationEdit FormME = new FormMedicationEdit();
            FormME.MedicationCur = medList[e.getRow()];
            FormME.ShowDialog();
            fillGrid();
        } 
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //this button is not visible if not selection mode.
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        SelectedMedicationNum = medList[gridMain.getSelectedIndex()].MedicationNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


