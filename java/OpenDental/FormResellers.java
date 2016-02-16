//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormPatientSelect;
import OpenDental.FormResellerEdit;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Reseller;
import OpenDentBusiness.Resellers;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormResellers;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormResellers  extends Form 
{

    private DataTable TableResellers = new DataTable();
    public FormResellers() throws Exception {
        initializeComponent();
        Lan.F(this);
        gridMain.ContextMenu = menuRightClick;
    }

    private void formResellers_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        TableResellers = Resellers.getResellerList();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("PatNum",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("LName",150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("FName",130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Email",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("WkPhone",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("PhoneNumberVal",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Address",180);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("City",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("State",40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("PatStatus",80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < TableResellers.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(TableResellers.Rows[i]["PatNum"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["LName"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["FName"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["Email"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["WkPhone"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["PhoneNumberVal"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["Address"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["City"].ToString());
            row.getCells().Add(TableResellers.Rows[i]["State"].ToString());
            row.getCells().Add(((PatientStatus)PIn.Int(TableResellers.Rows[i]["PatStatus"].ToString())).ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Reseller reseller = Resellers.GetOne(PIn.Long(TableResellers.Rows[e.getRow()]["ResellerNum"].ToString()));
        FormResellerEdit FormRE = new FormResellerEdit(reseller);
        FormRE.ShowDialog();
        fillGrid();
    }

    //Could have deleted the reseller.
    private void menuItemAccount_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() < 0)
        {
            MsgBox.show(this,"Please select a reseller first.");
            return ;
        }
         
        GotoModule.GotoAccount(PIn.Long(TableResellers.Rows[gridMain.getSelectedIndex()]["PatNum"].ToString()));
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        //Only Jordan should be able to add resellers.
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (Patients.getLim(FormPS.SelectedPatNum).Guarantor != FormPS.SelectedPatNum)
        {
            MsgBox.show(this,"Customer must be a guarantor before they can be added as a reseller.");
            return ;
        }
         
        if (Resellers.isResellerFamily(FormPS.SelectedPatNum))
        {
            MsgBox.show(this,"Customer is already a reseller.  CustomerNum: " + FormPS.SelectedPatNum);
            return ;
        }
         
        Reseller reseller = new Reseller();
        reseller.PatNum = FormPS.SelectedPatNum;
        Resellers.insert(reseller);
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormResellers.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label2 = new System.Windows.Forms.Label();
        this.menuRightClick = new System.Windows.Forms.ContextMenu();
        this.menuItemAccount = new System.Windows.Forms.MenuItem();
        this.labelResellerParagraph = new System.Windows.Forms.Label();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 165);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(780, 372);
        this.gridMain.TabIndex = 38;
        this.gridMain.setTitle("Resellers");
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
        // label2
        //
        this.label2.Location = new System.Drawing.Point(502, 133);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(209, 26);
        this.label2.TabIndex = 40;
        this.label2.Text = "Add as a customer first.\r\nThey must also be the guarantor.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // menuRightClick
        //
        this.menuRightClick.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemAccount });
        //
        // menuItemAccount
        //
        this.menuItemAccount.Index = 0;
        this.menuItemAccount.Text = "Go to Account";
        this.menuItemAccount.Click += new System.EventHandler(this.menuItemAccount_Click);
        //
        // labelResellerParagraph
        //
        this.labelResellerParagraph.Location = new System.Drawing.Point(12, 15);
        this.labelResellerParagraph.Name = "labelResellerParagraph";
        this.labelResellerParagraph.Size = new System.Drawing.Size(780, 115);
        this.labelResellerParagraph.TabIndex = 41;
        this.labelResellerParagraph.Text = resources.GetString("labelResellerParagraph.Text");
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(717, 550);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(717, 133);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 35;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormResellers
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(808, 583);
        this.Controls.Add(this.labelResellerParagraph);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Name = "FormResellers";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Resellers";
        this.Load += new System.EventHandler(this.FormResellers_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ContextMenu menuRightClick = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemAccount = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.Label labelResellerParagraph = new System.Windows.Forms.Label();
}


