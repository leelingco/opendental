//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:53 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCommItem;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Userods;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormCommItem  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listMode = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listSentOrReceived = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.ListBox listType = new System.Windows.Forms.ListBox();
    private TextBox textPatientName = new TextBox();
    private Label label5 = new Label();
    private TextBox textUser = new TextBox();
    private Label label16 = new Label();
    private Label labelCommlogNum = new Label();
    private TextBox textCommlogNum = new TextBox();
    private OpenDental.UI.SignatureBoxWrapper signatureBoxWrapper;
    private Commlog CommlogCur;
    private boolean IsStartingUp = new boolean();
    private OpenDental.UI.Button butNow;
    private OpenDental.UI.Button butNowEnd;
    private TextBox textDateTimeEnd = new TextBox();
    private Label labelDateTimeEnd = new Label();
    private boolean SigChanged = new boolean();
    /**
    * 
    */
    public FormCommItem(Commlog commlogCur) throws Exception {
        initializeComponent();
        Lan.f(this);
        CommlogCur = commlogCur;
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCommItem.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
        this.textDateTime = new System.Windows.Forms.TextBox();
        this.listMode = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.listSentOrReceived = new System.Windows.Forms.ListBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.textPatientName = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.labelCommlogNum = new System.Windows.Forms.Label();
        this.textCommlogNum = new System.Windows.Forms.TextBox();
        this.signatureBoxWrapper = new OpenDental.UI.SignatureBoxWrapper();
        this.butNow = new OpenDental.UI.Button();
        this.butNowEnd = new OpenDental.UI.Button();
        this.textDateTimeEnd = new System.Windows.Forms.TextBox();
        this.labelDateTimeEnd = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(1, 33);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(81, 18);
        this.label1.TabIndex = 0;
        this.label1.Text = "Date / Time";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(80, 80);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(82, 16);
        this.label6.TabIndex = 5;
        this.label6.Text = "Type";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(564, 515);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 6;
        this.butOK.Text = "&OK";
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(564, 548);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(27, 544);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 25);
        this.butDelete.TabIndex = 17;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(81, 197);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(82, 16);
        this.label2.TabIndex = 18;
        this.label2.Text = "Note";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listType
        //
        this.listType.Location = new System.Drawing.Point(82, 98);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(120, 95);
        this.listType.TabIndex = 20;
        this.listType.SelectedIndexChanged += new System.EventHandler(this.listType_SelectedIndexChanged);
        //
        // textDateTime
        //
        this.textDateTime.Location = new System.Drawing.Point(82, 31);
        this.textDateTime.Name = "textDateTime";
        this.textDateTime.Size = new System.Drawing.Size(205, 20);
        this.textDateTime.TabIndex = 21;
        this.textDateTime.TextChanged += new System.EventHandler(this.textDateTime_TextChanged);
        //
        // listMode
        //
        this.listMode.Location = new System.Drawing.Point(215, 98);
        this.listMode.Name = "listMode";
        this.listMode.Size = new System.Drawing.Size(73, 95);
        this.listMode.TabIndex = 23;
        this.listMode.SelectedIndexChanged += new System.EventHandler(this.listMode_SelectedIndexChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(214, 81);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(82, 16);
        this.label3.TabIndex = 22;
        this.label3.Text = "Mode";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listSentOrReceived
        //
        this.listSentOrReceived.Location = new System.Drawing.Point(303, 98);
        this.listSentOrReceived.Name = "listSentOrReceived";
        this.listSentOrReceived.Size = new System.Drawing.Size(87, 43);
        this.listSentOrReceived.TabIndex = 25;
        this.listSentOrReceived.SelectedValueChanged += new System.EventHandler(this.listSentOrReceived_SelectedValueChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(302, 80);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(142, 16);
        this.label4.TabIndex = 24;
        this.label4.Text = "Sent or Received";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(82, 217);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.CommLog);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(557, 209);
        this.textNote.TabIndex = 27;
        this.textNote.TextChanged += new System.EventHandler(this.textNote_TextChanged);
        //
        // textPatientName
        //
        this.textPatientName.Location = new System.Drawing.Point(82, 7);
        this.textPatientName.Name = "textPatientName";
        this.textPatientName.ReadOnly = true;
        this.textPatientName.Size = new System.Drawing.Size(205, 20);
        this.textPatientName.TabIndex = 30;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(4, 9);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(78, 18);
        this.label5.TabIndex = 29;
        this.label5.Text = "Patient";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(402, 7);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(119, 20);
        this.textUser.TabIndex = 103;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(327, 8);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(73, 16);
        this.label16.TabIndex = 102;
        this.label16.Text = "User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCommlogNum
        //
        this.labelCommlogNum.Location = new System.Drawing.Point(154, 552);
        this.labelCommlogNum.Name = "labelCommlogNum";
        this.labelCommlogNum.Size = new System.Drawing.Size(96, 16);
        this.labelCommlogNum.TabIndex = 104;
        this.labelCommlogNum.Text = "CommlogNum";
        this.labelCommlogNum.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCommlogNum
        //
        this.textCommlogNum.Location = new System.Drawing.Point(256, 549);
        this.textCommlogNum.Name = "textCommlogNum";
        this.textCommlogNum.ReadOnly = true;
        this.textCommlogNum.Size = new System.Drawing.Size(188, 20);
        this.textCommlogNum.TabIndex = 105;
        //
        // signatureBoxWrapper
        //
        this.signatureBoxWrapper.BackColor = System.Drawing.SystemColors.ControlDark;
        this.signatureBoxWrapper.setLabelText(null);
        this.signatureBoxWrapper.Location = new System.Drawing.Point(82, 432);
        this.signatureBoxWrapper.Name = "signatureBoxWrapper";
        this.signatureBoxWrapper.Size = new System.Drawing.Size(364, 81);
        this.signatureBoxWrapper.TabIndex = 106;
        this.signatureBoxWrapper.SignatureChanged += new System.EventHandler(this.signatureBoxWrapper_SignatureChanged);
        //
        // butNow
        //
        this.butNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow.setAutosize(true);
        this.butNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow.setCornerRadius(4F);
        this.butNow.Location = new System.Drawing.Point(293, 31);
        this.butNow.Name = "butNow";
        this.butNow.Size = new System.Drawing.Size(48, 21);
        this.butNow.TabIndex = 107;
        this.butNow.Text = "Now";
        this.butNow.Click += new System.EventHandler(this.butNow_Click);
        //
        // butNowEnd
        //
        this.butNowEnd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNowEnd.setAutosize(true);
        this.butNowEnd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNowEnd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNowEnd.setCornerRadius(4F);
        this.butNowEnd.Location = new System.Drawing.Point(293, 55);
        this.butNowEnd.Name = "butNowEnd";
        this.butNowEnd.Size = new System.Drawing.Size(48, 21);
        this.butNowEnd.TabIndex = 110;
        this.butNowEnd.Text = "Now";
        this.butNowEnd.Click += new System.EventHandler(this.butNowEnd_Click);
        //
        // textDateTimeEnd
        //
        this.textDateTimeEnd.Location = new System.Drawing.Point(82, 55);
        this.textDateTimeEnd.Name = "textDateTimeEnd";
        this.textDateTimeEnd.Size = new System.Drawing.Size(205, 20);
        this.textDateTimeEnd.TabIndex = 109;
        //
        // labelDateTimeEnd
        //
        this.labelDateTimeEnd.Location = new System.Drawing.Point(1, 57);
        this.labelDateTimeEnd.Name = "labelDateTimeEnd";
        this.labelDateTimeEnd.Size = new System.Drawing.Size(81, 18);
        this.labelDateTimeEnd.TabIndex = 108;
        this.labelDateTimeEnd.Text = "End";
        this.labelDateTimeEnd.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormCommItem
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(662, 594);
        this.Controls.Add(this.butNowEnd);
        this.Controls.Add(this.textDateTimeEnd);
        this.Controls.Add(this.labelDateTimeEnd);
        this.Controls.Add(this.butNow);
        this.Controls.Add(this.signatureBoxWrapper);
        this.Controls.Add(this.textCommlogNum);
        this.Controls.Add(this.labelCommlogNum);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.textPatientName);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.listSentOrReceived);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.listMode);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDateTime);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormCommItem";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Communication Item";
        this.Load += new System.EventHandler(this.FormCommItem_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formCommItem_Load(Object sender, System.EventArgs e) throws Exception {
        IsStartingUp = true;
        textPatientName.Text = Patients.getLim(CommlogCur.PatNum).getNameFL();
        textUser.Text = Userods.getName(CommlogCur.UserNum);
        //might be blank.
        textDateTime.Text = CommlogCur.CommDateTime.ToShortDateString() + "  " + CommlogCur.CommDateTime.ToShortTimeString();
        if (CommlogCur.DateTimeEnd.Year > 1880)
        {
            textDateTimeEnd.Text = CommlogCur.DateTimeEnd.ToShortDateString() + "  " + CommlogCur.DateTimeEnd.ToShortTimeString();
        }
         
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()].Length;i++)
        {
            //there will usually be a commtype set before this dialog is opened
            listType.Items.Add(DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][i].DefNum == CommlogCur.CommType)
            {
                listType.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < Enum.GetNames(CommItemMode.class).Length;i++)
        {
            listMode.Items.Add(Lan.g("enumCommItemMode", Enum.GetNames(CommItemMode.class)[i]));
        }
        listMode.SelectedIndex = ((Enum)CommlogCur.Mode_).ordinal();
        for (int i = 0;i < Enum.GetNames(CommSentOrReceived.class).Length;i++)
        {
            listSentOrReceived.Items.Add(Lan.g("enumCommSentOrReceived", Enum.GetNames(CommSentOrReceived.class)[i]));
        }
        try
        {
            listSentOrReceived.SelectedIndex = ((Enum)CommlogCur.SentOrReceived).ordinal();
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show((((Enum)CommlogCur.SentOrReceived).ordinal()).ToString());
        }

        //checkIsStatementSent.Checked=CommlogCur.IsStatementSent;
        textNote.Text = CommlogCur.Note;
        textNote.SelectionStart = textNote.Text.Length;
        labelCommlogNum.Visible = false;
        textCommlogNum.Visible = false;
        textCommlogNum.Text = CommlogCur.CommlogNum.ToString();
        if (!PrefC.getBool(PrefName.DistributorKey))
        {
            labelDateTimeEnd.Visible = false;
            textDateTimeEnd.Visible = false;
            butNow.Visible = false;
            butNowEnd.Visible = false;
        }
         
        textNote.Select();
        String keyData = getSignatureKey();
        signatureBoxWrapper.fillSignature(CommlogCur.SigIsTopaz,keyData,CommlogCur.Signature);
        signatureBoxWrapper.BringToFront();
        IsStartingUp = false;
        if (!Security.isAuthorized(Permissions.CommlogEdit,CommlogCur.CommDateTime))
        {
            if (IsNew)
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
            butDelete.Enabled = false;
            butOK.Enabled = false;
        }
         
    }

    private void signatureBoxWrapper_SignatureChanged(Object sender, EventArgs e) throws Exception {
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        textUser.Text = Userods.getName(CommlogCur.UserNum);
        SigChanged = true;
    }

    private void clearSignature() throws Exception {
        //so this happens only if user changes the note
        if (!IsStartingUp && !SigChanged)
        {
            //and the original signature is still showing.
            //SigChanged=true;//happens automatically through the event.
            signatureBoxWrapper.clearSignature();
        }
         
    }

    private String getSignatureKey() throws Exception {
        String keyData = CommlogCur.UserNum.ToString();
        keyData += CommlogCur.CommDateTime.ToString();
        keyData += CommlogCur.Mode_.ToString();
        keyData += CommlogCur.SentOrReceived.ToString();
        if (CommlogCur.Note != null)
        {
            keyData += CommlogCur.Note.ToString();
        }
         
        return keyData;
    }

    private void saveSignature() throws Exception {
        if (SigChanged)
        {
            String keyData = getSignatureKey();
            CommlogCur.Signature = signatureBoxWrapper.getSignature(keyData);
            CommlogCur.SigIsTopaz = signatureBoxWrapper.getSigIsTopaz();
        }
         
    }

    private void textDateTime_TextChanged(Object sender, EventArgs e) throws Exception {
        clearSignature();
    }

    private void listType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        clearSignature();
    }

    private void listMode_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        clearSignature();
    }

    private void listSentOrReceived_SelectedValueChanged(Object sender, EventArgs e) throws Exception {
        clearSignature();
    }

    private void textNote_TextChanged(Object sender, EventArgs e) throws Exception {
        clearSignature();
    }

    private void butNow_Click(Object sender, EventArgs e) throws Exception {
        textDateTime.Text = DateTime.Now.ToShortDateString() + "  " + DateTime.Now.ToShortTimeString();
    }

    private void butNowEnd_Click(Object sender, EventArgs e) throws Exception {
        textDateTimeEnd.Text = DateTime.Now.ToShortDateString() + "  " + DateTime.Now.ToShortTimeString();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        //button not enabled if no permission
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        SecurityLogs.makeLogEntry(Permissions.CommlogEdit,CommlogCur.PatNum,"Delete");
        Commlogs.delete(CommlogCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //button not enabled if no permission
        if (StringSupport.equals(textDateTime.Text, ""))
        {
            MsgBox.show(this,"Please enter a date first.");
            return ;
        }
         
        try
        {
            DateTime.Parse(textDateTime.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            MsgBox.show(this,"Date / Time invalid.");
            return ;
        }

        if (!StringSupport.equals(textDateTimeEnd.Text, ""))
        {
            try
            {
                DateTime.Parse(textDateTimeEnd.Text);
            }
            catch (Exception __dummyCatchVar2)
            {
                MsgBox.show(this,"End date and time invalid.");
                return ;
            }

            CommlogCur.DateTimeEnd = PIn.DateT(textDateTimeEnd.Text);
        }
         
        CommlogCur.CommDateTime = PIn.DateT(textDateTime.Text);
        //there may not be a commtype selected.
        if (listType.SelectedIndex == -1)
        {
            CommlogCur.CommType = 0;
        }
        else
        {
            CommlogCur.CommType = DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][listType.SelectedIndex].DefNum;
        } 
        CommlogCur.Mode_ = (CommItemMode)listMode.SelectedIndex;
        CommlogCur.SentOrReceived = (CommSentOrReceived)listSentOrReceived.SelectedIndex;
        CommlogCur.Note = textNote.Text;
        try
        {
            saveSignature();
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error saving signature.") + "\r\n" + ex.Message);
            return ;
        }

        if (IsNew)
        {
            Commlogs.insert(CommlogCur);
            SecurityLogs.makeLogEntry(Permissions.CommlogEdit,CommlogCur.PatNum,"Insert");
        }
        else
        {
            Commlogs.update(CommlogCur);
            SecurityLogs.makeLogEntry(Permissions.CommlogEdit,CommlogCur.PatNum,"");
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


