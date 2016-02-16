//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.CustRefEntries;
import OpenDentBusiness.CustRefEntry;
import OpenDentBusiness.CustReferences;
import OpenDental.Properties.Resources;

public class FormReferenceEntryEdit  extends Form 
{

    private CustRefEntry CustRefEntryCur;
    public FormReferenceEntryEdit(CustRefEntry refEntry) throws Exception {
        initializeComponent();
        Lan.F(this);
        CustRefEntryCur = refEntry;
    }

    private void formReferenceEdit_Load(Object sender, EventArgs e) throws Exception {
        textCustomer.Text = CustReferences.getCustNameFL(CustRefEntryCur.PatNumCust);
        textReferredTo.Text = CustReferences.getCustNameFL(CustRefEntryCur.PatNumRef);
        if (CustRefEntryCur.DateEntry.Year > 1880)
        {
            textDateEntry.Text = CustRefEntryCur.DateEntry.ToShortDateString();
        }
         
        textNote.Text = CustRefEntryCur.Note;
    }

    private void butDeleteAll_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Delete Reference Entry?"))
        {
            return ;
        }
         
        CustRefEntries.delete(CustRefEntryCur.CustRefEntryNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        CustRefEntryCur.Note = textNote.Text;
        CustRefEntries.update(CustRefEntryCur);
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
        this.textNote = new OpenDental.ODtextBox();
        this.textCustomer = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.textDateEntry = new OpenDental.ValidDate();
        this.label12 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textReferredTo = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butDeleteAll = new OpenDental.UI.Button();
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
        this.butOK.Location = new System.Drawing.Point(305, 282);
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
        this.butCancel.Location = new System.Drawing.Point(386, 282);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(138, 109);
        this.textNote.MaxLength = 255;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Payment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(242, 141);
        this.textNote.TabIndex = 4;
        //
        // textCustomer
        //
        this.textCustomer.Location = new System.Drawing.Point(138, 27);
        this.textCustomer.Name = "textCustomer";
        this.textCustomer.ReadOnly = true;
        this.textCustomer.Size = new System.Drawing.Size(242, 20);
        this.textCustomer.TabIndex = 130;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(11, 29);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(125, 16);
        this.label11.TabIndex = 131;
        this.label11.Text = "New Customer";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(138, 80);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(100, 20);
        this.textDateEntry.TabIndex = 132;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(5, 84);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(131, 16);
        this.label12.TabIndex = 133;
        this.label12.Text = "Date Entry";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 110);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(128, 16);
        this.label1.TabIndex = 134;
        this.label1.Text = "Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textReferredTo
        //
        this.textReferredTo.Location = new System.Drawing.Point(138, 54);
        this.textReferredTo.Name = "textReferredTo";
        this.textReferredTo.ReadOnly = true;
        this.textReferredTo.Size = new System.Drawing.Size(242, 20);
        this.textReferredTo.TabIndex = 135;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 58);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(131, 16);
        this.label2.TabIndex = 136;
        this.label2.Text = "Used as a Reference";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butDeleteAll
        //
        this.butDeleteAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDeleteAll.setAutosize(true);
        this.butDeleteAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteAll.setCornerRadius(4F);
        this.butDeleteAll.Image = Resources.getdeleteX();
        this.butDeleteAll.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteAll.Location = new System.Drawing.Point(12, 282);
        this.butDeleteAll.Name = "butDeleteAll";
        this.butDeleteAll.Size = new System.Drawing.Size(84, 24);
        this.butDeleteAll.TabIndex = 137;
        this.butDeleteAll.Text = "&Delete";
        this.butDeleteAll.Click += new System.EventHandler(this.butDeleteAll_Click);
        //
        // FormReferenceEntryEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(473, 318);
        this.Controls.Add(this.butDeleteAll);
        this.Controls.Add(this.textReferredTo);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.textCustomer);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormReferenceEntryEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reference Entry Edit";
        this.Load += new System.EventHandler(this.FormReferenceEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.TextBox textCustomer = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEntry;
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReferredTo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDeleteAll;
}


