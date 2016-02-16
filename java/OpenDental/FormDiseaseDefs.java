//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormDiseaseDefEdit;
import OpenDental.FormDiseaseDefs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormDiseaseDefs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private Label label1 = new Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private OpenDental.UI.Button butOK;
    /**
    * Set to true when user is using this to select a disease def. Currently used when adding Alerts to Rx.
    */
    public boolean IsSelectionMode = new boolean();
    /**
    * Set to true when user is using FormMedical to allow multiple problems to be selected at once.
    */
    public boolean IsMultiSelect = new boolean();
    /**
    * If IsSelectionMode, then after closing with OK, this will contain number.
    */
    public long SelectedDiseaseDefNum = new long();
    /**
    * If IsMultiSelect, then this will contain a list of numbers when closing with OK.
    */
    public List<long> SelectedDiseaseDefNums = new List<long>();
    private OpenDental.UI.ODGrid gridMain;
    private boolean IsChanged = new boolean();
    /**
    * 
    */
    public FormDiseaseDefs() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDiseaseDefs.class);
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 11);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(335, 20);
        this.label1.TabIndex = 8;
        this.label1.Text = "This is a list of medical problems that patients might have. ";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 35);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(548, 628);
        this.gridMain.TabIndex = 16;
        this.gridMain.setTitle(null);
        this.gridMain.setTranslationName(null);
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(584, 605);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(79, 26);
        this.butOK.TabIndex = 15;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(584, 464);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(79, 26);
        this.butDown.TabIndex = 14;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(584, 432);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(79, 26);
        this.butUp.TabIndex = 13;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(584, 637);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(79, 26);
        this.butClose.TabIndex = 1;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(584, 265);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormDiseaseDefs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(682, 675);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDiseaseDefs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Problems";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormDiseaseDefs_FormClosing);
        this.Load += new System.EventHandler(this.FormDiseaseDefs_Load);
        this.ResumeLayout(false);
    }

    private void formDiseaseDefs_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
            if (IsMultiSelect)
            {
                gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
            }
             
        }
        else
        {
            butOK.Visible = false;
        } 
        fillGrid();
    }

    private void fillGrid() throws Exception {
        DiseaseDefs.refreshCache();
        //listMain.SelectionMode=SelectionMode.MultiExtended;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"ICD-9"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"ICD-10"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"SNOMED CT"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),250);
        gridMain.getColumns().add(col);
        if (!IsSelectionMode)
        {
            col = new ODGridColumn(Lan.g(this,"Hidden"),50);
            gridMain.getColumns().add(col);
        }
         
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        if (IsSelectionMode)
        {
            for (int i = 0;i < DiseaseDefs.getList().Length;i++)
            {
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(DiseaseDefs.getList()[i].ICD9Code);
                row.getCells().Add(DiseaseDefs.getList()[i].Icd10Code);
                row.getCells().Add(DiseaseDefs.getList()[i].SnomedCode);
                row.getCells().Add(DiseaseDefs.getList()[i].DiseaseName);
                gridMain.getRows().add(row);
            }
        }
        else
        {
            for (int i = 0;i < DiseaseDefs.getListLong().Length;i++)
            {
                //Not selection mode - show hidden
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(DiseaseDefs.getListLong()[i].ICD9Code);
                row.getCells().Add(DiseaseDefs.getList()[i].Icd10Code);
                row.getCells().Add(DiseaseDefs.getListLong()[i].SnomedCode);
                row.getCells().Add(DiseaseDefs.getListLong()[i].DiseaseName);
                row.getCells().add(DiseaseDefs.getListLong()[i].IsHidden ? "X" : "");
                gridMain.getRows().add(row);
            }
        } 
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!IsSelectionMode && !Security.isAuthorized(Permissions.ProblemEdit))
        {
            return ;
        }
         
        //trying to double click to edit, but no permission.
        if (gridMain.getSelectedIndices().Length == 0)
        {
            return ;
        }
         
        if (IsSelectionMode)
        {
            if (IsMultiSelect)
            {
                SelectedDiseaseDefNums = new List<long>();
                SelectedDiseaseDefNums.Add(DiseaseDefs.getList()[gridMain.getSelectedIndex()].DiseaseDefNum);
            }
            else
            {
                SelectedDiseaseDefNum = DiseaseDefs.getList()[gridMain.getSelectedIndex()].DiseaseDefNum;
            } 
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //everything below this point is _not_ selection mode.  User guaranteed to have permission for ProblemEdit.
        FormDiseaseDefEdit FormD = new FormDiseaseDefEdit(DiseaseDefs.getListLong()[gridMain.getSelectedIndex()]);
        FormD.ShowDialog();
        //Security log entry made inside that form.
        if (FormD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        IsChanged = true;
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.ProblemEdit))
        {
            return ;
        }
         
        DiseaseDef def = new DiseaseDef();
        def.ItemOrder = DiseaseDefs.getListLong().Length;
        FormDiseaseDefEdit FormD = new FormDiseaseDefEdit(def);
        FormD.IsNew = true;
        FormD.ShowDialog();
        //Security log entry made inside that form.
        if (FormD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        IsChanged = true;
        fillGrid();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        //These aren't yet optimized for multiselection.
        int selected = gridMain.getSelectedIndex();
        try
        {
            DiseaseDefs.moveUp(gridMain.getSelectedIndex());
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        fillGrid();
        if (selected == 0)
        {
            gridMain.setSelected(0,true);
        }
        else
        {
            gridMain.setSelected(selected - 1,true);
        } 
        IsChanged = true;
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        //These aren't yet optimized for multiselection.
        int selected = gridMain.getSelectedIndex();
        try
        {
            DiseaseDefs.moveDown(gridMain.getSelectedIndex());
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        fillGrid();
        if (selected == DiseaseDefs.getListLong().Length - 1)
        {
            gridMain.getSelectedIndex();
        }
        else
        {
            gridMain.setSelected(selected + 1,true);
        } 
        IsChanged = true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless IsSelectionMode
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        if (IsMultiSelect)
        {
            SelectedDiseaseDefNums = new List<long>();
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                SelectedDiseaseDefNums.Add(DiseaseDefs.getList()[gridMain.getSelectedIndices()[i]].DiseaseDefNum);
            }
        }
        else if (IsSelectionMode)
        {
            SelectedDiseaseDefNum = DiseaseDefs.getList()[gridMain.getSelectedIndex()].DiseaseDefNum;
        }
        else
        {
            SelectedDiseaseDefNum = DiseaseDefs.getListLong()[gridMain.getSelectedIndex()].DiseaseDefNum;
        }  
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    //also closes if not IsSelectionMode
    private void formDiseaseDefs_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (IsChanged)
        {
            DataValid.setInvalid(InvalidType.Diseases);
        }
         
    }

}


