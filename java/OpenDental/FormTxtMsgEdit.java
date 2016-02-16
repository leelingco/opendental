//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.CallFireService.SMSService;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.YN;

public class FormTxtMsgEdit  extends Form 
{

    public long PatNum = new long();
    public String WirelessPhone = new String();
    public String Message = new String();
    public YN TxtMsgOk = YN.Unknown;
    public FormTxtMsgEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTxtMsgEdit_Load(Object sender, EventArgs e) throws Exception {
        textWirelessPhone.Text = WirelessPhone;
        textMessage.Text = Message;
    }

    /**
    * May be called from other parts of the program without showing this form. You must still create an instance of this form though. Checks CallFire bridge, if it is OK to send a text, etc. (Buttons to load this form are usually  disabled if it is not OK, but this is needed for Confirmations, Recalls, etc.)
    */
    public void sendText(long patNum, String wirelessPhone, String message, YN txtMsgOk) throws Exception {
        if (Plugins.hookMethod(this,"FormTxtMsgEdit.SendText_Start",patNum,wirelessPhone,message,txtMsgOk))
        {
            return ;
        }
         
        if (StringSupport.equals(wirelessPhone, ""))
        {
            MsgBox.show(this,"Please enter a phone number.");
            return ;
        }
         
        if (!Programs.isEnabled(ProgramName.CallFire))
        {
            MsgBox.show(this,"CallFire Program Link must be enabled.");
            return ;
        }
         
        if (txtMsgOk == YN.Unknown && PrefC.getBool(PrefName.TextMsgOkStatusTreatAsNo))
        {
            MsgBox.show(this,"It is not OK to text this patient.");
            return ;
        }
         
        if (txtMsgOk == YN.No)
        {
            MsgBox.show(this,"It is not OK to text this patient.");
            return ;
        }
         
        String key = ProgramProperties.getPropVal(ProgramName.CallFire,"Key From CallFire");
        String msg = wirelessPhone + "," + message.Replace(",", "");
        try
        {
            //ph#,msg Commas in msg cause error.
            SMSService callFire = new SMSService();
            callFire.sendSMSCampaign(key, new String[]{ msg }, "Open Dental");
        }
        catch (Exception ex)
        {
            MsgBox.show(this,"Error sending text message.\r\n\r\n" + ex.Message);
            return ;
        }

        Commlog commlog = new Commlog();
        commlog.CommDateTime = DateTime.Now;
        commlog.DateTStamp = DateTime.Now;
        commlog.CommType = DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][0].DefNum;
        //The first one in the list.  We can enhance later.
        commlog.Mode_ = CommItemMode.Text;
        commlog.Note = msg;
        //phone,note
        commlog.PatNum = patNum;
        commlog.SentOrReceived = CommSentOrReceived.Sent;
        commlog.UserNum = Security.getCurUser().UserNum;
        commlog.DateTimeEnd = DateTime.Now;
        Commlogs.insert(commlog);
        SecurityLogs.makeLogEntry(Permissions.CommlogEdit,commlog.PatNum,"Insert Text Message");
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textMessage.Text, ""))
        {
            MsgBox.show(this,"Please enter a message first.");
            return ;
        }
         
        SendText(PatNum, textWirelessPhone.Text, textMessage.Text, TxtMsgOk);
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
        this.textWirelessPhone = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textMessage = new OpenDental.ODtextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textWirelessPhone
        //
        this.textWirelessPhone.Location = new System.Drawing.Point(107, 21);
        this.textWirelessPhone.Name = "textWirelessPhone";
        this.textWirelessPhone.Size = new System.Drawing.Size(128, 20);
        this.textWirelessPhone.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(89, 20);
        this.label1.TabIndex = 6;
        this.label1.Text = "Phone Number";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(15, 46);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(86, 20);
        this.label2.TabIndex = 6;
        this.label2.Text = "Text Message";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMessage
        //
        this.textMessage.Location = new System.Drawing.Point(107, 46);
        this.textMessage.Multiline = true;
        this.textMessage.Name = "textMessage";
        this.textMessage.setQuickPasteType(OpenDentBusiness.QuickPasteType.TxtMsg);
        this.textMessage.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textMessage.Size = new System.Drawing.Size(186, 83);
        this.textMessage.TabIndex = 4;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(176, 152);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&Send";
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
        this.butCancel.Location = new System.Drawing.Point(257, 152);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormTxtMsgEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(344, 188);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textWirelessPhone);
        this.Controls.Add(this.textMessage);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormTxtMsgEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Text Message";
        this.Load += new System.EventHandler(this.FormTxtMsgEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.ODtextBox textMessage;
    private System.Windows.Forms.TextBox textWirelessPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


