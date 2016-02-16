//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormResellerServiceEdit;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RegistrationKey;
import OpenDentBusiness.RegistrationKeys;
import OpenDentBusiness.Reseller;
import OpenDentBusiness.Resellers;
import OpenDentBusiness.ResellerService;
import OpenDentBusiness.ResellerServices;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormResellerEdit  extends Form 
{

    private Reseller ResellerCur;
    private DataTable TableCustomers = new DataTable();
    private List<ResellerService> ListServices = new List<ResellerService>();
    public FormResellerEdit(Reseller reseller) throws Exception {
        ResellerCur = reseller;
        initializeComponent();
        Lan.F(this);
        gridMain.ContextMenu = menuRightClick;
    }

    private void formResellerEdit_Load(Object sender, EventArgs e) throws Exception {
        //Only Jordan should be able to alter reseller credentials.
        if (!Security.isAuthorized(Permissions.SecurityAdmin,true))
        {
            labelCredentials.Text = "Only users with Security Admin can edit credentials.";
            textUserName.ReadOnly = true;
            textPassword.ReadOnly = true;
        }
         
        textUserName.Text = ResellerCur.UserName;
        textPassword.Text = ResellerCur.ResellerPassword;
        fillGridMain();
        fillGridServices();
    }

    private void fillGridMain() throws Exception {
        double total = 0;
        TableCustomers = Resellers.getResellerCustomersList(ResellerCur.PatNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("PatNum",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("RegKey",130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("ProcCode",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Descript",180);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Fee",70);
        col.setTextAlign(HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("DateStart",80);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("DateStop",80);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < TableCustomers.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(TableCustomers.Rows[i]["PatNum"].ToString());
            row.getCells().Add(TableCustomers.Rows[i]["RegKey"].ToString());
            row.getCells().Add(TableCustomers.Rows[i]["ProcCode"].ToString());
            row.getCells().Add(TableCustomers.Rows[i]["Descript"].ToString());
            double fee = PIn.Double(TableCustomers.Rows[i]["Fee"].ToString());
            row.getCells().Add(fee.ToString("F"));
            total += fee;
            row.getCells().Add(PIn.Date(TableCustomers.Rows[i]["DateStart"].ToString()).ToShortDateString());
            row.getCells().Add(PIn.Date(TableCustomers.Rows[i]["DateStop"].ToString()).ToShortDateString());
            row.getCells().Add(TableCustomers.Rows[i]["Note"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        labelTotal.Text = "Total: " + total.ToString("C");
    }

    private void fillGridServices() throws Exception {
        ListServices = ResellerServices.getServicesForReseller(ResellerCur.ResellerNum);
        gridServices.beginUpdate();
        gridServices.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Description",180);
        gridServices.getColumns().add(col);
        col = new ODGridColumn("Fee",0);
        col.setTextAlign(HorizontalAlignment.Right);
        gridServices.getColumns().add(col);
        gridServices.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListServices.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ProcedureCodes.GetLaymanTerm(ListServices[i].CodeNum));
            row.getCells().Add(ListServices[i].Fee.ToString("F"));
            gridServices.getRows().add(row);
        }
        gridServices.endUpdate();
    }

    private void menuItemAccount_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() < 0)
        {
            MsgBox.show(this,"Please select a customer first.");
            return ;
        }
         
        GotoModule.GotoAccount(PIn.Long(TableCustomers.Rows[gridMain.getSelectedIndex()]["PatNum"].ToString()));
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        //Only Jordan should be able to add services.
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        ResellerService resellerService = new ResellerService();
        resellerService.ResellerNum = ResellerCur.ResellerNum;
        FormResellerServiceEdit FormRSE = new FormResellerServiceEdit(resellerService);
        FormRSE.IsNew = true;
        FormRSE.ShowDialog();
        if (FormRSE.DialogResult == DialogResult.OK)
        {
            fillGridServices();
        }
         
    }

    private void gridServices_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //Only Jordan should be able to edit services.
        if (!Security.isAuthorized(Permissions.SecurityAdmin,true))
        {
            return ;
        }
         
        ResellerService resellerService = ListServices[gridServices.getSelectedIndex()];
        FormResellerServiceEdit FormRSE = new FormResellerServiceEdit(resellerService);
        FormRSE.ShowDialog();
        if (FormRSE.DialogResult == DialogResult.OK)
        {
            fillGridMain();
            fillGridServices();
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //Only Jordan should be able to delete resellers.
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        //Do not let the reseller be deleted if they have customers in their list.
        if (Resellers.hasActiveResellerCustomers(ResellerCur))
        {
            MsgBox.show(this,"This reseller cannot be deleted until all active services are removed from their customers.  This should be done using the reseller portal.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"This will update PatStatus to inactive and set every registartion key's stop date.\r\nContinue?"))
        {
            return ;
        }
         
        Patient patOld = Patients.getPat(ResellerCur.PatNum);
        Patient patCur = patOld.copy();
        patCur.PatStatus = PatientStatus.Inactive;
        Patients.update(patCur,patOld);
        RegistrationKey[] regKeys = RegistrationKeys.getForPatient(patCur.PatNum);
        for (int i = 0;i < regKeys.Length;i++)
        {
            DateTime dateTimeNow = MiscData.getNowDateTime();
            if (regKeys[i].DateEnded.Year > 1880 && regKeys[i].DateEnded < dateTimeNow)
            {
                continue;
            }
             
            //Key already ended.  Nothing to do.
            regKeys[i].DateEnded = MiscData.getNowDateTime();
            RegistrationKeys.Update(regKeys[i]);
        }
        Resellers.delete(ResellerCur.ResellerNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textPassword.Text, "") && StringSupport.equals(textUserName.Text.Trim(), ""))
        {
            MsgBox.show(this,"User Name cannot be blank.");
            return ;
        }
         
        if (!StringSupport.equals(textUserName.Text, "") && StringSupport.equals(textPassword.Text.Trim(), ""))
        {
            MsgBox.show(this,"Password cannot be blank.");
            return ;
        }
         
        if (!StringSupport.equals(textUserName.Text, "") && Resellers.IsUserNameInUse(ResellerCur.PatNum, textUserName.Text))
        {
            MsgBox.show(this,"User Name already in use.");
            return ;
        }
         
        ResellerCur.UserName = textUserName.Text;
        ResellerCur.ResellerPassword = textPassword.Text;
        Resellers.update(ResellerCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
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
        this.label6 = new System.Windows.Forms.Label();
        this.textUserName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelCredentials = new System.Windows.Forms.Label();
        this.menuRightClick = new System.Windows.Forms.ContextMenu();
        this.menuItemAccount = new System.Windows.Forms.MenuItem();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.gridServices = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.labelTotal = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label6.Location = new System.Drawing.Point(12, 393);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(371, 24);
        this.label6.TabIndex = 40;
        this.label6.Text = "The customers list is managed by the reseller using the Reseller Portal.";
        //
        // textUserName
        //
        this.textUserName.Location = new System.Drawing.Point(104, 26);
        this.textUserName.Name = "textUserName";
        this.textUserName.Size = new System.Drawing.Size(247, 20);
        this.textUserName.TabIndex = 245;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(6, 27);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(95, 19);
        this.label4.TabIndex = 247;
        this.label4.Text = "User Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(104, 54);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(247, 20);
        this.textPassword.TabIndex = 246;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 55);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(95, 19);
        this.label5.TabIndex = 248;
        this.label5.Text = "Password";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelCredentials);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textUserName);
        this.groupBox1.Controls.Add(this.textPassword);
        this.groupBox1.Location = new System.Drawing.Point(14, 8);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(400, 99);
        this.groupBox1.TabIndex = 250;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Reseller Portal Credentials";
        //
        // labelCredentials
        //
        this.labelCredentials.Location = new System.Drawing.Point(104, 77);
        this.labelCredentials.Name = "labelCredentials";
        this.labelCredentials.Size = new System.Drawing.Size(290, 18);
        this.labelCredentials.TabIndex = 249;
        this.labelCredentials.Text = "Any user name and password will work.";
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
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 113);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(516, 274);
        this.gridMain.TabIndex = 39;
        this.gridMain.setTitle("Customers");
        this.gridMain.setTranslationName("TableCustomers");
        //
        // gridServices
        //
        this.gridServices.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridServices.setHScrollVisible(false);
        this.gridServices.Location = new System.Drawing.Point(534, 113);
        this.gridServices.Name = "gridServices";
        this.gridServices.setScrollValue(0);
        this.gridServices.Size = new System.Drawing.Size(248, 274);
        this.gridServices.TabIndex = 252;
        this.gridServices.setTitle("Available Services");
        this.gridServices.setTranslationName("TableAvailableServices");
        this.gridServices.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridServices.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridServices_CellDoubleClick(sender, e);
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
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(102, 456);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(426, 27);
        this.label1.TabIndex = 254;
        this.label1.Text = "Delete the reseller.\r\nSets reseller PatStatus to inactive and disables all reg ke" + "ys.";
        //
        // labelTotal
        //
        this.labelTotal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelTotal.Location = new System.Drawing.Point(389, 393);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(139, 24);
        this.labelTotal.TabIndex = 255;
        this.labelTotal.Text = "Total: $0.00";
        this.labelTotal.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label3.Location = new System.Drawing.Point(533, 73);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(251, 31);
        this.label3.TabIndex = 256;
        this.label3.Text = "Each reseller pays different prices and has different services available for thei" + "r customers.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
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
        this.butAdd.Location = new System.Drawing.Point(682, 393);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(100, 26);
        this.butAdd.TabIndex = 253;
        this.butAdd.Text = "&Add Service";
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
        this.butOK.Location = new System.Drawing.Point(612, 458);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 251;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(12, 458);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 41;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(707, 458);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormResellerEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(804, 494);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.labelTotal);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridServices);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Name = "FormResellerEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reseller Edit";
        this.Load += new System.EventHandler(this.FormResellerEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textUserName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelCredentials = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ContextMenu menuRightClick = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemAccount = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODGrid gridServices;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTotal = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


