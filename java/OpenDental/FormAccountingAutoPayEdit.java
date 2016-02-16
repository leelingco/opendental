//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAccountingAutoPayEdit;
import OpenDental.FormAccountPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AccountingAutoPay;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAccountingAutoPayEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private ComboBox comboPayType = new ComboBox();
    private Label label7 = new Label();
    private ListBox listAccounts = new ListBox();
    private Label label6 = new Label();
    private OpenDental.UI.Button butRemove;
    private OpenDental.UI.Button butAdd;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public AccountingAutoPay AutoPayCur;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    /**
    * Array List of AccountNums.
    */
    private ArrayList accountAL = new ArrayList();
    /**
    * 
    */
    public FormAccountingAutoPayEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAccountingAutoPayEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.comboPayType = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.listAccounts = new System.Windows.Forms.ListBox();
        this.label6 = new System.Windows.Forms.Label();
        this.butRemove = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(515, 273);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(515, 232);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // comboPayType
        //
        this.comboPayType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPayType.FormattingEnabled = true;
        this.comboPayType.Location = new System.Drawing.Point(256, 22);
        this.comboPayType.Name = "comboPayType";
        this.comboPayType.Size = new System.Drawing.Size(230, 21);
        this.comboPayType.TabIndex = 43;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(5, 22);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(245, 19);
        this.label7.TabIndex = 42;
        this.label7.Text = "When this type of payment is entered:";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listAccounts
        //
        this.listAccounts.FormattingEnabled = true;
        this.listAccounts.Location = new System.Drawing.Point(252, 49);
        this.listAccounts.Name = "listAccounts";
        this.listAccounts.Size = new System.Drawing.Size(230, 95);
        this.listAccounts.TabIndex = 41;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(85, 49);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(165, 69);
        this.label6.TabIndex = 40;
        this.label6.Text = "User will get to pick from this list of accounts to deposit into.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butRemove
        //
        this.butRemove.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemove.setAutosize(true);
        this.butRemove.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemove.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemove.setCornerRadius(4F);
        this.butRemove.Location = new System.Drawing.Point(407, 153);
        this.butRemove.Name = "butRemove";
        this.butRemove.Size = new System.Drawing.Size(75, 26);
        this.butRemove.TabIndex = 45;
        this.butRemove.Text = "Remove";
        this.butRemove.Click += new System.EventHandler(this.butRemove_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(324, 153);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 44;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butDelete.Location = new System.Drawing.Point(28, 273);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 26);
        this.butDelete.TabIndex = 46;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormAccountingAutoPayEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(642, 324);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butRemove);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.comboPayType);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.listAccounts);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAccountingAutoPayEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Auto Pay Entry";
        this.Load += new System.EventHandler(this.FormAccountingAutoPayEdit_Load);
        this.ResumeLayout(false);
    }

    private void formAccountingAutoPayEdit_Load(Object sender, EventArgs e) throws Exception {
        if (AutoPayCur == null)
        {
            MessageBox.Show("Autopay cannot be null.");
        }
         
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()].Length;i++)
        {
            //just for debugging
            comboPayType.Items.Add(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].DefNum == AutoPayCur.PayType)
            {
                comboPayType.SelectedIndex = i;
            }
             
        }
        if (AutoPayCur.PickList == null)
        {
            AutoPayCur.PickList = "";
        }
         
        String[] strArray = AutoPayCur.PickList.Split(new char[]{ ',' });
        accountAL = new ArrayList();
        for (int i = 0;i < strArray.Length;i++)
        {
            if (StringSupport.equals(strArray[i], ""))
            {
                continue;
            }
             
            accountAL.Add(PIn.Long(strArray[i]));
        }
        fillList();
    }

    private void fillList() throws Exception {
        listAccounts.Items.Clear();
        for (int i = 0;i < accountAL.Count;i++)
        {
            listAccounts.Items.Add(Accounts.getDescript((long)accountAL[i]));
        }
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAccountPick FormA = new FormAccountPick();
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        accountAL.Add(FormA.SelectedAccount.AccountNum);
        fillList();
    }

    private void butRemove_Click(Object sender, EventArgs e) throws Exception {
        if (listAccounts.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        accountAL.RemoveAt(listAccounts.SelectedIndex);
        fillList();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        AutoPayCur = null;
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (comboPayType.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a pay type first.");
            return ;
        }
         
        if (accountAL.Count == 0)
        {
            MsgBox.show(this,"Please add at least one account to the pick list first.");
            return ;
        }
         
        AutoPayCur.PayType = DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][comboPayType.SelectedIndex].DefNum;
        AutoPayCur.PickList = "";
        for (int i = 0;i < accountAL.Count;i++)
        {
            if (i > 0)
            {
                AutoPayCur.PickList += ",";
            }
             
            AutoPayCur.PickList += accountAL[i].ToString();
        }
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            AutoPayCur = null;
        }
         
        DialogResult = DialogResult.Cancel;
    }

}


