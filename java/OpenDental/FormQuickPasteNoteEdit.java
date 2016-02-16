//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuickPasteNoteEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.QuickPasteNote;
import OpenDental.QuickPasteNotes;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;

/**
* Summary description for FormBasicTemplate.
*/
public class FormQuickPasteNoteEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private QuickPasteNote QuickNote;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAbbreviation = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormQuickPasteNoteEdit(QuickPasteNote quickNote) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        QuickNote = quickNote;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuickPasteNoteEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textNote = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textAbbreviation = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(592, 514);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
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
        this.butOK.Location = new System.Drawing.Point(497, 514);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(38, 62);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(628, 431);
        this.textNote.TabIndex = 1;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(40, 514);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(37, 44);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 16);
        this.label1.TabIndex = 15;
        this.label1.Text = "Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textAbbreviation
        //
        this.textAbbreviation.Location = new System.Drawing.Point(38, 22);
        this.textAbbreviation.Name = "textAbbreviation";
        this.textAbbreviation.Size = new System.Drawing.Size(252, 20);
        this.textAbbreviation.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(37, 4);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(157, 16);
        this.label2.TabIndex = 17;
        this.label2.Text = "Abbreviation";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(293, 13);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(365, 33);
        this.label3.TabIndex = 18;
        this.label3.Text = "If you type a ? immediately followed by the abbreviation, your note will be inser" + "ted";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormQuickPasteNoteEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(719, 564);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textAbbreviation);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQuickPasteNoteEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Quick Paste Note";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormQuickPasteNoteEdit_Closing);
        this.Load += new System.EventHandler(this.FormQuickPasteNoteEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formQuickPasteNoteEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AutoNoteQuickNoteEdit,true))
        {
            textAbbreviation.ReadOnly = true;
            textAbbreviation.BackColor = SystemColors.Window;
            textNote.ReadOnly = true;
            textNote.BackColor = SystemColors.Window;
            butDelete.Enabled = false;
            butOK.Enabled = false;
        }
         
        textAbbreviation.Text = QuickNote.Abbreviation;
        textNote.Text = QuickNote.Note;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (MessageBox.Show(Lan.g(this,"Delete note?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        QuickPasteNotes.Delete(QuickNote);
        QuickNote.QuickPasteNoteNum = 0;
        //triggers an action in the calling form
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (textAbbreviation.Text.Contains("?"))
        {
            MsgBox.show(this,"Question mark not allowed in abbreviation.  Use the question mark later when trying to insert a quick note.");
            return ;
        }
         
        QuickNote.Abbreviation = textAbbreviation.Text;
        if (!StringSupport.equals(QuickNote.Abbreviation, ""))
        {
            if (QuickPasteNotes.AbbrAlreadyInUse(QuickNote))
            {
                MessageBox.Show(Lan.g(this,"Abbreviation is already in use."));
                return ;
            }
             
        }
         
        QuickNote.Note = textNote.Text;
        if (IsNew)
        {
            QuickPasteNotes.Insert(QuickNote);
        }
        else
        {
            QuickPasteNotes.Update(QuickNote);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formQuickPasteNoteEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            QuickPasteNotes.Delete(QuickNote);
            DialogResult = DialogResult.Cancel;
        }
         
    }

}


