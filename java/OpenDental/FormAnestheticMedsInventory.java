//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormAnestheticMedsIntake;
import OpenDental.FormAnesthMedsEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormAnestheticMedsInventory;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormAnestheticMedsInventory  extends Form 
{

    private List<AnesthMedsInventory> listAnestheticMeds = new List<AnesthMedsInventory>();
    public HorizontalAlignment textAlign = new HorizontalAlignment();
    public FormAnestheticMedsInventory() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAnestheticMedsInventory_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        listAnestheticMeds = AnesthMeds.CreateObjects();
        gridAnesthMedsInventory.beginUpdate();
        gridAnesthMedsInventory.getColumns().Clear();
        textAlign = HorizontalAlignment.Center;
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Anesthetic Medication"),200,textAlign);
        gridAnesthMedsInventory.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"How Supplied"),200,textAlign);
        gridAnesthMedsInventory.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Quantity on Hand (mL)"),180,textAlign);
        gridAnesthMedsInventory.getColumns().add(col);
        gridAnesthMedsInventory.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < listAnestheticMeds.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(listAnestheticMeds[i].AnesthMedName);
            row.getCells().Add(listAnestheticMeds[i].AnesthHowSupplied);
            row.getCells().Add(listAnestheticMeds[i].QtyOnHand);
            gridAnesthMedsInventory.getRows().add(row);
        }
        gridAnesthMedsInventory.endUpdate();
    }

    private void butAddAnesthMeds_Click(Object sender, EventArgs e) throws Exception {
        AnesthMedsInventory med = new AnesthMedsInventory();
        med.IsNew = true;
        FormAnesthMedsEdit FormM = new FormAnesthMedsEdit();
        FormM.Med = med;
        FormM.ShowDialog();
        if (FormM.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void gridAnesthMedsInventory_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        Userod curUser = Security.getCurUser();
        if (GroupPermissions.hasPermission(curUser.UserGroupNum,Permissions.AnesthesiaControlMeds))
        {
            FormAnesthMedsEdit FormME = new FormAnesthMedsEdit();
            FormME.Med = listAnestheticMeds[e.getRow()];
            FormME.ShowDialog();
            if (FormME.DialogResult == DialogResult.OK)
            {
                fillGrid();
            }
             
            return ;
        }
        else
        {
            MessageBox.Show(Lan.g(this,"You must be an administrator with rights to control anesthetic medication inventory levels to unlock this action"));
            return ;
        } 
    }

    private void butAnesthMedIntake_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AnesthesiaIntakeMeds))
        {
            butAnesthMedIntake.Enabled = false;
            return ;
        }
        else
        {
            FormAnestheticMedsIntake FormI = new FormAnestheticMedsIntake();
            FormI.ShowDialog();
            if (FormI.DialogResult == DialogResult.OK)
            {
                fillGrid();
            }
             
        } 
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butAdjustQtys_Click(Object sender, EventArgs e) throws Exception {
        Userod curUser = Security.getCurUser();
        if (GroupPermissions.hasPermission(curUser.UserGroupNum,Permissions.AnesthesiaControlMeds))
        {
            FormAnesthMedsEdit FormA = new FormAnesthMedsEdit();
            AnesthMedsInventory med = new AnesthMedsInventory();
            med.IsNew = true;
            FormA.ShowDialog();
            return ;
        }
        else
        {
            MessageBox.Show(Lan.g(this,"You must be an administrator to unlock this action"));
            return ;
        } 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnestheticMedsInventory.class);
        this.groupAnestheticMeds = new System.Windows.Forms.GroupBox();
        this.labelIntakeNewMeds = new System.Windows.Forms.Label();
        this.butAnesthMedIntake = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butAddAnesthMeds = new OpenDental.UI.Button();
        this.gridAnesthMedsInventory = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.groupAnestheticMeds.SuspendLayout();
        this.SuspendLayout();
        //
        // groupAnestheticMeds
        //
        this.groupAnestheticMeds.Controls.Add(this.label1);
        this.groupAnestheticMeds.Controls.Add(this.labelIntakeNewMeds);
        this.groupAnestheticMeds.Controls.Add(this.butAnesthMedIntake);
        this.groupAnestheticMeds.Controls.Add(this.butClose);
        this.groupAnestheticMeds.Controls.Add(this.butCancel);
        this.groupAnestheticMeds.Controls.Add(this.butAddAnesthMeds);
        this.groupAnestheticMeds.Controls.Add(this.gridAnesthMedsInventory);
        this.groupAnestheticMeds.Location = new System.Drawing.Point(20, 20);
        this.groupAnestheticMeds.Name = "groupAnestheticMeds";
        this.groupAnestheticMeds.Size = new System.Drawing.Size(705, 331);
        this.groupAnestheticMeds.TabIndex = 5;
        this.groupAnestheticMeds.TabStop = false;
        this.groupAnestheticMeds.Text = "Anesthetic Medications";
        //
        // labelIntakeNewMeds
        //
        this.labelIntakeNewMeds.Location = new System.Drawing.Point(246, 276);
        this.labelIntakeNewMeds.Name = "labelIntakeNewMeds";
        this.labelIntakeNewMeds.Size = new System.Drawing.Size(272, 26);
        this.labelIntakeNewMeds.TabIndex = 147;
        this.labelIntakeNewMeds.Text = "This button should only be used after anesthetic  medications are added to the li" + "st above";
        //
        // butAnesthMedIntake
        //
        this.butAnesthMedIntake.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAnesthMedIntake.setAutosize(true);
        this.butAnesthMedIntake.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAnesthMedIntake.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAnesthMedIntake.setCornerRadius(4F);
        this.butAnesthMedIntake.Image = Resources.getAdd();
        this.butAnesthMedIntake.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAnesthMedIntake.Location = new System.Drawing.Point(104, 276);
        this.butAnesthMedIntake.Name = "butAnesthMedIntake";
        this.butAnesthMedIntake.Size = new System.Drawing.Size(136, 26);
        this.butAnesthMedIntake.TabIndex = 146;
        this.butAnesthMedIntake.Text = "Intake new meds";
        this.butAnesthMedIntake.UseVisualStyleBackColor = true;
        this.butAnesthMedIntake.Click += new System.EventHandler(this.butAnesthMedIntake_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClose.Location = new System.Drawing.Point(596, 276);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(90, 26);
        this.butClose.TabIndex = 145;
        this.butClose.Text = "Save and Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(524, 276);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(66, 26);
        this.butCancel.TabIndex = 144;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butAddAnesthMeds
        //
        this.butAddAnesthMeds.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddAnesthMeds.setAutosize(true);
        this.butAddAnesthMeds.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddAnesthMeds.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddAnesthMeds.setCornerRadius(4F);
        this.butAddAnesthMeds.Image = Resources.getAdd();
        this.butAddAnesthMeds.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddAnesthMeds.Location = new System.Drawing.Point(11, 49);
        this.butAddAnesthMeds.Name = "butAddAnesthMeds";
        this.butAddAnesthMeds.Size = new System.Drawing.Size(82, 26);
        this.butAddAnesthMeds.TabIndex = 76;
        this.butAddAnesthMeds.Text = "Add New";
        this.butAddAnesthMeds.UseVisualStyleBackColor = true;
        this.butAddAnesthMeds.Click += new System.EventHandler(this.butAddAnesthMeds_Click);
        //
        // gridAnesthMedsInventory
        //
        this.gridAnesthMedsInventory.setHScrollVisible(false);
        this.gridAnesthMedsInventory.Location = new System.Drawing.Point(104, 33);
        this.gridAnesthMedsInventory.Name = "gridAnesthMedsInventory";
        this.gridAnesthMedsInventory.setScrollValue(0);
        this.gridAnesthMedsInventory.Size = new System.Drawing.Size(580, 206);
        this.gridAnesthMedsInventory.TabIndex = 4;
        this.gridAnesthMedsInventory.setTitle("Anesthetic Medication Inventory");
        this.gridAnesthMedsInventory.setTranslationName(null);
        this.gridAnesthMedsInventory.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridAnesthMedsInventory.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridAnesthMedsInventory_CellDoubleClick(sender, e);
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
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(277, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(203, 13);
        this.label1.TabIndex = 148;
        this.label1.Text = "Double-click to adjust inventory quantities";
        //
        // FormAnestheticMedsInventory
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(754, 381);
        this.Controls.Add(this.groupAnestheticMeds);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnestheticMedsInventory";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Anesthetic Medication Inventory";
        this.Load += new System.EventHandler(this.FormAnestheticMedsInventory_Load);
        this.groupAnestheticMeds.ResumeLayout(false);
        this.groupAnestheticMeds.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridAnesthMedsInventory;
    private System.Windows.Forms.GroupBox groupAnestheticMeds = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddAnesthMeds;
    private System.Windows.Forms.Label labelIntakeNewMeds = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAnesthMedIntake;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


