//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormAllergyDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormAllergySetup  extends Form 
{

    private List<AllergyDef> listAllergyDefs = new List<AllergyDef>();
    public boolean IsSelectionMode = new boolean();
    public long SelectedAllergyDefNum = new long();
    public FormAllergySetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAllergySetup_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butOK.Visible = true;
            butClose.Text = "Cancel";
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        listAllergyDefs = AllergyDefs.GetAll(checkShowHidden.Checked);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormAllergySetup","Desciption"),160);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormAllergySetup","Hidden"),60);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < listAllergyDefs.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(listAllergyDefs[i].Description);
            if (listAllergyDefs[i].IsHidden)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void checkShowHidden_CheckedChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedAllergyDefNum = listAllergyDefs[e.getRow()].AllergyDefNum;
            DialogResult = DialogResult.OK;
        }
        else
        {
            FormAllergyDefEdit formA = new FormAllergyDefEdit();
            formA.AllergyDefCur = listAllergyDefs[gridMain.getSelectedIndex()];
            formA.ShowDialog();
            fillGrid();
        } 
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAllergyDefEdit formA = new FormAllergyDefEdit();
        formA.AllergyDefCur = new AllergyDef();
        formA.AllergyDefCur.setIsNew(true);
        formA.ShowDialog();
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //Only visible in IsSelectionMode.
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Select at least one allergy.");
            return ;
        }
         
        SelectedAllergyDefNum = listAllergyDefs[gridMain.getSelectedIndex()].AllergyDefNum;
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.checkShowHidden = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // checkShowHidden
        //
        this.checkShowHidden.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.checkShowHidden.Location = new System.Drawing.Point(296, 148);
        this.checkShowHidden.Name = "checkShowHidden";
        this.checkShowHidden.Size = new System.Drawing.Size(98, 24);
        this.checkShowHidden.TabIndex = 5;
        this.checkShowHidden.TabStop = false;
        this.checkShowHidden.Text = "Show Hidden";
        this.checkShowHidden.UseVisualStyleBackColor = true;
        this.checkShowHidden.CheckedChanged += new System.EventHandler(this.checkShowHidden_CheckedChanged);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(26, 24);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(242, 310);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Allergies");
        this.gridMain.setTranslationName(null);
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
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
        this.butAdd.Location = new System.Drawing.Point(296, 24);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 3;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(296, 310);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(296, 280);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 6;
        this.butOK.Text = "&OK";
        this.butOK.Visible = false;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormAllergySetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(396, 361);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.checkShowHidden);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Name = "FormAllergySetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Allergy Setup";
        this.Load += new System.EventHandler(this.FormAllergySetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.CheckBox checkShowHidden = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butOK;
}


