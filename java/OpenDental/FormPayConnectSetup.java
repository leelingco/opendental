//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:27 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormPayConnectSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPayConnectSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private LinkLabel linkLabel1 = new LinkLabel();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private CheckBox checkEnabled = new CheckBox();
    private Label label1 = new Label();
    private ComboBox comboPaymentType = new ComboBox();
    private Program prog;
    private Label label2 = new Label();
    private TextBox textUsername = new TextBox();
    private Label label3 = new Label();
    private TextBox textPassword = new TextBox();
    private ProgramProperty propUsername = null;
    private ProgramProperty propPassword = null;
    private ProgramProperty propPayType = null;
    /**
    * 
    */
    public FormPayConnectSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayConnectSetup.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.linkLabel1 = new System.Windows.Forms.LinkLabel();
        this.checkEnabled = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.comboPaymentType = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textUsername = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(243, 227);
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
        this.butOK.Location = new System.Drawing.Point(146, 227);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // linkLabel1
        //
        this.linkLabel1.LinkArea = new System.Windows.Forms.LinkArea(29, 28);
        this.linkLabel1.Location = new System.Drawing.Point(20, 20);
        this.linkLabel1.Name = "linkLabel1";
        this.linkLabel1.Size = new System.Drawing.Size(312, 17);
        this.linkLabel1.TabIndex = 3;
        this.linkLabel1.TabStop = true;
        this.linkLabel1.Text = "The PayConnect website is at www.dentalxchange.com";
        this.linkLabel1.UseCompatibleTextRendering = true;
        this.linkLabel1.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.linkLabel1_LinkClicked);
        //
        // checkEnabled
        //
        this.checkEnabled.Location = new System.Drawing.Point(21, 49);
        this.checkEnabled.Name = "checkEnabled";
        this.checkEnabled.Size = new System.Drawing.Size(104, 18);
        this.checkEnabled.TabIndex = 4;
        this.checkEnabled.Text = "Enabled";
        this.checkEnabled.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 158);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 16);
        this.label1.TabIndex = 53;
        this.label1.Text = "Payment Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboPaymentType
        //
        this.comboPaymentType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPaymentType.FormattingEnabled = true;
        this.comboPaymentType.Location = new System.Drawing.Point(21, 177);
        this.comboPaymentType.MaxDropDownItems = 25;
        this.comboPaymentType.Name = "comboPaymentType";
        this.comboPaymentType.Size = new System.Drawing.Size(205, 21);
        this.comboPaymentType.TabIndex = 54;
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(18, 77);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(55, 13);
        this.label2.TabIndex = 55;
        this.label2.Text = "Username";
        //
        // textUsername
        //
        this.textUsername.Location = new System.Drawing.Point(20, 94);
        this.textUsername.Name = "textUsername";
        this.textUsername.Size = new System.Drawing.Size(206, 20);
        this.textUsername.TabIndex = 56;
        //
        // label3
        //
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(18, 118);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(53, 13);
        this.label3.TabIndex = 57;
        this.label3.Text = "Password";
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(21, 135);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(205, 20);
        this.textPassword.TabIndex = 58;
        //
        // FormPayConnectSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(344, 274);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textUsername);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboPaymentType);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkEnabled);
        this.Controls.Add(this.linkLabel1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPayConnectSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "PayConnect Setup";
        this.Load += new System.EventHandler(this.FormXchargeSetup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formXchargeSetup_Load(Object sender, EventArgs e) throws Exception {
        prog = Programs.getCur(ProgramName.PayConnect);
        if (prog == null)
        {
            return ;
        }
         
        checkEnabled.Checked = prog.Enabled;
        List<ProgramProperty> props = ProgramProperties.getListForProgram(prog.ProgramNum);
        for (int i = 0;i < props.Count;i++)
        {
            if (StringSupport.equals(props[i].PropertyDesc, "Username"))
            {
                propUsername = props[i];
            }
            else if (StringSupport.equals(props[i].PropertyDesc, "Password"))
            {
                propPassword = props[i];
            }
            else if (StringSupport.equals(props[i].PropertyDesc, "PaymentType"))
            {
                propPayType = props[i];
            }
               
        }
        textUsername.Text = propUsername.PropertyValue;
        textPassword.Text = propPassword.PropertyValue;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()].Length;i++)
        {
            comboPaymentType.Items.Add(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].ItemName);
            if (StringSupport.equals(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].DefNum.ToString(), propPayType.PropertyValue))
            {
                comboPaymentType.SelectedIndex = i;
            }
             
        }
    }

    private void linkLabel1_LinkClicked(Object sender, LinkLabelLinkClickedEventArgs e) throws Exception {
        Process.Start("http://www.dentalxchange.com");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (prog == null)
        {
            MsgBox.show(this,"PayConnect entry is missing from the database.");
            return ;
        }
         
        //should never happen
        if (comboPaymentType.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a payment type first.");
            return ;
        }
         
        prog.Enabled = checkEnabled.Checked;
        Programs.update(prog);
        propUsername.PropertyValue = textUsername.Text;
        ProgramProperties.update(propUsername);
        propPassword.PropertyValue = textPassword.Text;
        ProgramProperties.update(propPassword);
        propPayType.PropertyValue = DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][comboPaymentType.SelectedIndex].DefNum.ToString();
        ProgramProperties.update(propPayType);
        DataValid.setInvalid(InvalidType.Programs);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


