//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAutoNoteCompose;
import OpenDental.Lan;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcGroupItem;
import OpenDentBusiness.ProcGroupItems;
import OpenDentBusiness.Security;

public class FormProcNoteAppend  extends Form 
{

    public Procedure ProcCur;
    public FormProcNoteAppend() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formProcNoteAppend_Load(Object sender, EventArgs e) throws Exception {
        textUser.Text = Security.getCurUser().UserName;
        textNotes.Text = ProcCur.Note;
        //there is no signature to display when this form is opened.
        //signatureBoxWrapper.FillSignature(false,"","");
        signatureBoxWrapper.BringToFront();
    }

    //signatureBoxWrapper.ClearSignature();
    private void buttonUseAutoNote_Click(Object sender, EventArgs e) throws Exception {
        FormAutoNoteCompose FormA = new FormAutoNoteCompose();
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.OK)
        {
            textAppended.AppendText(FormA.CompletedNote);
        }
         
    }

    private String getSignatureKey() throws Exception {
        //ProcCur.Note was already assembled as it will appear in proc edit window.  We want to key on that.
        //Procs and proc groups are keyed differently
        String keyData = new String();
        if (StringSupport.equals(ProcedureCodes.getStringProcCode(ProcCur.CodeNum), ProcedureCodes.GroupProcCode))
        {
            keyData = ProcCur.ProcDate.ToShortDateString();
            keyData += ProcCur.DateEntryC.ToShortDateString();
            keyData += ProcCur.UserNum.ToString();
            //Security.CurUser.UserName;
            keyData += ProcCur.Note;
            List<ProcGroupItem> groupItemList = ProcGroupItems.getForGroup(ProcCur.ProcNum);
            for (int i = 0;i < groupItemList.Count;i++)
            {
                //Orders the list to ensure same key in all cases.
                keyData += groupItemList[i].ProcGroupItemNum.ToString();
            }
        }
        else
        {
            //regular proc
            keyData = ProcCur.Note + ProcCur.UserNum.ToString();
        } 
        return keyData;
    }

    //MsgBoxCopyPaste msgb=new MsgBoxCopyPaste(keyData);
    //msgb.ShowDialog();
    private void saveSignature() throws Exception {
        //This is not a good pattern to copy, because it's simpler than usual.  Try FormCommItem.
        String keyData = getSignatureKey();
        ProcCur.Signature = signatureBoxWrapper.getSignature(keyData);
        ProcCur.SigIsTopaz = signatureBoxWrapper.getSigIsTopaz();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Procedure procOld = ProcCur.copy();
        ProcCur.UserNum = Security.getCurUser().UserNum;
        ProcCur.Note = textNotes.Text + "\r\n" + DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString() + " " + Security.getCurUser().UserName + ":  " + textAppended.Text;
        try
        {
            saveSignature();
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error saving signature.") + "\r\n" + ex.Message);
        }

        //and continue with the rest of this method
        Procedures.update(ProcCur,procOld);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.signatureBoxWrapper = new OpenDental.UI.SignatureBoxWrapper();
        this.label15 = new System.Windows.Forms.Label();
        this.buttonUseAutoNote = new OpenDental.UI.Button();
        this.textUser = new System.Windows.Forms.TextBox();
        this.textNotes = new OpenDental.ODtextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.textAppended = new OpenDental.ODtextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(440, 447);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(521, 447);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // signatureBoxWrapper
        //
        this.signatureBoxWrapper.BackColor = System.Drawing.SystemColors.ControlDark;
        this.signatureBoxWrapper.setLabelText(null);
        this.signatureBoxWrapper.Location = new System.Drawing.Point(112, 340);
        this.signatureBoxWrapper.Name = "signatureBoxWrapper";
        this.signatureBoxWrapper.Size = new System.Drawing.Size(364, 81);
        this.signatureBoxWrapper.TabIndex = 107;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(109, 319);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(136, 18);
        this.label15.TabIndex = 108;
        this.label15.Text = "Signature / Initials";
        this.label15.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // buttonUseAutoNote
        //
        this.buttonUseAutoNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonUseAutoNote.setAutosize(true);
        this.buttonUseAutoNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonUseAutoNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonUseAutoNote.setCornerRadius(4F);
        this.buttonUseAutoNote.Location = new System.Drawing.Point(230, 17);
        this.buttonUseAutoNote.Name = "buttonUseAutoNote";
        this.buttonUseAutoNote.Size = new System.Drawing.Size(80, 22);
        this.buttonUseAutoNote.TabIndex = 113;
        this.buttonUseAutoNote.Text = "Auto Note";
        this.buttonUseAutoNote.Click += new System.EventHandler(this.buttonUseAutoNote_Click);
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(112, 18);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(116, 20);
        this.textUser.TabIndex = 112;
        //
        // textNotes
        //
        this.textNotes.AcceptsTab = true;
        this.textNotes.Location = new System.Drawing.Point(112, 38);
        this.textNotes.Multiline = true;
        this.textNotes.Name = "textNotes";
        this.textNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textNotes.ReadOnly = true;
        this.textNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNotes.Size = new System.Drawing.Size(450, 164);
        this.textNotes.TabIndex = 110;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(37, 19);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(73, 16);
        this.label16.TabIndex = 111;
        this.label16.Text = "User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(24, 42);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(86, 16);
        this.label7.TabIndex = 109;
        this.label7.Text = "Original Note";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textAppended
        //
        this.textAppended.AcceptsTab = true;
        this.textAppended.Location = new System.Drawing.Point(112, 208);
        this.textAppended.Multiline = true;
        this.textAppended.Name = "textAppended";
        this.textAppended.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textAppended.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textAppended.Size = new System.Drawing.Size(450, 108);
        this.textAppended.TabIndex = 115;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 212);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(103, 16);
        this.label1.TabIndex = 114;
        this.label1.Text = "Appended Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormProcNoteAppend
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(608, 483);
        this.Controls.Add(this.textAppended);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.buttonUseAutoNote);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.signatureBoxWrapper);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormProcNoteAppend";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Note Append";
        this.Load += new System.EventHandler(this.FormProcNoteAppend_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.SignatureBoxWrapper signatureBoxWrapper;
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button buttonUseAutoNote;
    private System.Windows.Forms.TextBox textUser = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textNotes;
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textAppended;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


