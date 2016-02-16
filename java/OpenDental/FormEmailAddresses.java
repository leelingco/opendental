//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormEmailAddressEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEmailAddresses;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEmailAddresses  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public long EmailAddressNum = new long();
    public boolean IsChanged = new boolean();
    public FormEmailAddresses() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEmailAddresses_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            labelInboxComputerName.Visible = false;
            textInboxComputerName.Visible = false;
            butThisComputer.Visible = false;
            labelInboxCheckInterval.Visible = false;
            textInboxCheckInterval.Visible = false;
            labelInboxCheckUnits.Visible = false;
        }
        else
        {
            textInboxComputerName.Text = PrefC.getString(PrefName.EmailInboxComputerName);
            textInboxCheckInterval.Text = PrefC.getInt(PrefName.EmailInboxCheckInterval).ToString();
        } 
        //Calls PIn() internally.
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            EmailAddressNum = EmailAddresses.getListt()[gridMain.getSelectedIndex()].EmailAddressNum;
            DialogResult = DialogResult.OK;
        }
        else
        {
            FormEmailAddressEdit FormEAE = new FormEmailAddressEdit();
            FormEAE.EmailAddressCur = EmailAddresses.getListt()[e.getRow()];
            FormEAE.ShowDialog();
            if (FormEAE.DialogResult == DialogResult.OK)
            {
                fillGrid();
            }
             
        } 
    }

    private void fillGrid() throws Exception {
        EmailAddresses.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"User Name"),300);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Default"),20);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < EmailAddresses.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(EmailAddresses.getListt()[i].EmailUsername);
            row.getCells().add((EmailAddresses.getListt()[i].EmailAddressNum == PrefC.getLong(PrefName.EmailDefaultAddressNum)) ? "X" : "");
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butSetDefault_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        if (Prefs.UpdateLong(PrefName.EmailDefaultAddressNum, EmailAddresses.getListt()[gridMain.getSelectedIndex()].EmailAddressNum))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEmailAddressEdit FormEAE = new FormEmailAddressEdit();
        FormEAE.EmailAddressCur = new EmailAddress();
        FormEAE.IsNew = true;
        FormEAE.ShowDialog();
        if (FormEAE.DialogResult == DialogResult.OK)
        {
            fillGrid();
            IsChanged = true;
        }
         
    }

    private void butThisComputer_Click(Object sender, EventArgs e) throws Exception {
        textInboxComputerName.Text = Dns.GetHostName();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            if (gridMain.getSelectedIndex() == -1)
            {
                MsgBox.show(this,"Please select an email address.");
                return ;
            }
             
            EmailAddressNum = EmailAddresses.getListt()[gridMain.getSelectedIndex()].EmailAddressNum;
        }
        else
        {
            //The following fields are only visible when not in selection mode.
            if (StringSupport.equals(textInboxComputerName.Text.Trim().ToLower(), "localhost") || StringSupport.equals(textInboxComputerName.Text.Trim(), "127.0.0.1"))
            {
                //If we allowed localhost, then there would potentially be email duplication in the db when emails are pulled in.
                MsgBox.show(this,"Computer name to fetch new email from cannot be localhost or 127.0.0.1 or any other loopback address.");
                return ;
            }
             
            int inboxCheckIntervalMinuteCount = 0;
            try
            {
                inboxCheckIntervalMinuteCount = int.Parse(textInboxCheckInterval.Text);
                if (inboxCheckIntervalMinuteCount < 1 || inboxCheckIntervalMinuteCount > 60)
                {
                    throw new ApplicationException("Invalid value.");
                }
                 
            }
            catch (Exception __dummyCatchVar0)
            {
                //User never sees this message.
                MsgBox.show(this,"Inbox check interval must be between 1 and 60 inclusive.");
                return ;
            }

            Prefs.UpdateString(PrefName.EmailInboxComputerName, textInboxComputerName.Text);
            Prefs.updateInt(PrefName.EmailInboxCheckInterval,inboxCheckIntervalMinuteCount);
        } 
        DialogResult = DialogResult.OK;
    }

    private void formEmailAddresses_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (IsChanged)
        {
            DataValid.setInvalid(InvalidType.Email);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailAddresses.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butSetDefault = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textInboxComputerName = new OpenDental.ODtextBox();
        this.labelInboxComputerName = new System.Windows.Forms.Label();
        this.labelInboxCheckInterval = new System.Windows.Forms.Label();
        this.textInboxCheckInterval = new OpenDental.ODtextBox();
        this.labelInboxCheckUnits = new System.Windows.Forms.Label();
        this.butThisComputer = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(391, 440);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Email Addresses");
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
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(435, 136);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 3;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butSetDefault
        //
        this.butSetDefault.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetDefault.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSetDefault.setAutosize(true);
        this.butSetDefault.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetDefault.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetDefault.setCornerRadius(4F);
        this.butSetDefault.Location = new System.Drawing.Point(435, 56);
        this.butSetDefault.Name = "butSetDefault";
        this.butSetDefault.Size = new System.Drawing.Size(75, 24);
        this.butSetDefault.TabIndex = 3;
        this.butSetDefault.Text = "Set Default";
        this.butSetDefault.Click += new System.EventHandler(this.butSetDefault_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(435, 516);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
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
        this.butOK.Location = new System.Drawing.Point(435, 472);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textInboxComputerName
        //
        this.textInboxComputerName.AcceptsTab = true;
        this.textInboxComputerName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textInboxComputerName.DetectUrls = false;
        this.textInboxComputerName.Location = new System.Drawing.Point(18, 472);
        this.textInboxComputerName.Name = "textInboxComputerName";
        this.textInboxComputerName.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textInboxComputerName.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textInboxComputerName.Size = new System.Drawing.Size(240, 24);
        this.textInboxComputerName.TabIndex = 5;
        this.textInboxComputerName.Text = "";
        //
        // labelInboxComputerName
        //
        this.labelInboxComputerName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelInboxComputerName.Location = new System.Drawing.Point(16, 453);
        this.labelInboxComputerName.Name = "labelInboxComputerName";
        this.labelInboxComputerName.Size = new System.Drawing.Size(335, 18);
        this.labelInboxComputerName.TabIndex = 6;
        this.labelInboxComputerName.Text = "Computer Name To Fetch New Email From";
        this.labelInboxComputerName.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelInboxCheckInterval
        //
        this.labelInboxCheckInterval.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelInboxCheckInterval.Location = new System.Drawing.Point(16, 497);
        this.labelInboxCheckInterval.Name = "labelInboxCheckInterval";
        this.labelInboxCheckInterval.Size = new System.Drawing.Size(335, 18);
        this.labelInboxCheckInterval.TabIndex = 7;
        this.labelInboxCheckInterval.Text = "Inbox Check Interval";
        this.labelInboxCheckInterval.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textInboxCheckInterval
        //
        this.textInboxCheckInterval.AcceptsTab = true;
        this.textInboxCheckInterval.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textInboxCheckInterval.DetectUrls = false;
        this.textInboxCheckInterval.Location = new System.Drawing.Point(18, 516);
        this.textInboxCheckInterval.Name = "textInboxCheckInterval";
        this.textInboxCheckInterval.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textInboxCheckInterval.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textInboxCheckInterval.Size = new System.Drawing.Size(30, 24);
        this.textInboxCheckInterval.TabIndex = 8;
        this.textInboxCheckInterval.Text = "";
        //
        // labelInboxCheckUnits
        //
        this.labelInboxCheckUnits.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelInboxCheckUnits.Location = new System.Drawing.Point(49, 519);
        this.labelInboxCheckUnits.Name = "labelInboxCheckUnits";
        this.labelInboxCheckUnits.Size = new System.Drawing.Size(198, 18);
        this.labelInboxCheckUnits.TabIndex = 9;
        this.labelInboxCheckUnits.Text = "minutes (1 to 60)";
        this.labelInboxCheckUnits.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butThisComputer
        //
        this.butThisComputer.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butThisComputer.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butThisComputer.setAutosize(true);
        this.butThisComputer.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butThisComputer.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butThisComputer.setCornerRadius(4F);
        this.butThisComputer.Location = new System.Drawing.Point(264, 472);
        this.butThisComputer.Name = "butThisComputer";
        this.butThisComputer.Size = new System.Drawing.Size(87, 24);
        this.butThisComputer.TabIndex = 10;
        this.butThisComputer.Text = "This Computer";
        this.butThisComputer.Click += new System.EventHandler(this.butThisComputer_Click);
        //
        // FormEmailAddresses
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(529, 558);
        this.Controls.Add(this.butThisComputer);
        this.Controls.Add(this.labelInboxCheckUnits);
        this.Controls.Add(this.textInboxCheckInterval);
        this.Controls.Add(this.labelInboxCheckInterval);
        this.Controls.Add(this.labelInboxComputerName);
        this.Controls.Add(this.textInboxComputerName);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butSetDefault);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEmailAddresses";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Email Addresses";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormEmailAddresses_FormClosing);
        this.Load += new System.EventHandler(this.FormEmailAddresses_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butSetDefault;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butOK;
    private OpenDental.ODtextBox textInboxComputerName;
    private System.Windows.Forms.Label labelInboxComputerName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelInboxCheckInterval = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textInboxCheckInterval;
    private System.Windows.Forms.Label labelInboxCheckUnits = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butThisComputer;
}


