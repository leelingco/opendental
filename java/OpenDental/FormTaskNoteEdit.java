//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.TaskNote;
import OpenDentBusiness.TaskNotes;
import OpenDentBusiness.Userods;
import OpenDental.Properties.Resources;

public class FormTaskNoteEdit  extends Form 
{

    public TaskNote TaskNoteCur;
    /**
    * Only called when DialogResult is OK (for OK button and sometimes delete button).
    */
    public static class __MultiDelegateEditComplete   implements DelegateEditComplete
    {
        public void invoke(Object sender) throws Exception {
            IList<DelegateEditComplete> copy = new IList<DelegateEditComplete>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<DelegateEditComplete>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                DelegateEditComplete d = (DelegateEditComplete)__dummyForeachVar0;
                d.invoke(sender);
            }
        }

        private System.Collections.Generic.IList<DelegateEditComplete> _invocationList = new ArrayList<DelegateEditComplete>();
        public static DelegateEditComplete combine(DelegateEditComplete a, DelegateEditComplete b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiDelegateEditComplete ret = new __MultiDelegateEditComplete();
            /**
            * Called when this form is closed.
            */
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static DelegateEditComplete remove(DelegateEditComplete a, DelegateEditComplete b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<DelegateEditComplete> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<DelegateEditComplete> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                //Tasknotes are not editable unless user has TaskEdit permission.
                __MultiDelegateEditComplete ret = new __MultiDelegateEditComplete();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<DelegateEditComplete> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface DelegateEditComplete   
    {
        void invoke(Object sender) throws Exception ;

        System.Collections.Generic.IList<DelegateEditComplete> getInvocationList() throws Exception ;
    
    }

    public DelegateEditComplete EditComplete;
    public FormTaskNoteEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTaskNoteEdit_Load(Object sender, EventArgs e) throws Exception {
        textDateTime.Text = TaskNoteCur.DateTimeNote.ToString();
        textUser.Text = Userods.getName(TaskNoteCur.UserNum);
        textNote.Text = TaskNoteCur.Note;
        this.Top += 150;
        if (TaskNoteCur.getIsNew())
        {
            textDateTime.ReadOnly = true;
        }
        else if (!Security.isAuthorized(Permissions.TaskEdit))
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
          
    }

    private void onEditComplete() throws Exception {
        if (EditComplete != null)
        {
            EditComplete.invoke(this);
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        if (TaskNoteCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            Close();
            return ;
        }
         
        //Needed because the window is called as a non-modal window.
        TaskNotes.delete(TaskNoteCur.TaskNoteNum);
        DialogResult = DialogResult.OK;
        onEditComplete();
        Close();
    }

    //Needed because the window is called as a non-modal window.
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textNote.Text, ""))
        {
            MsgBox.show(this,"Please enter a note, or delete this entry.");
            return ;
        }
         
        try
        {
            TaskNoteCur.DateTimeNote = DateTime.Parse(textDateTime.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix date.");
            return ;
        }

        TaskNoteCur.Note = textNote.Text;
        if (TaskNoteCur.getIsNew())
        {
            TaskNotes.insert(TaskNoteCur);
        }
        else
        {
            TaskNotes.update(TaskNoteCur);
        } 
        DialogResult = DialogResult.OK;
        onEditComplete();
        Close();
    }

    //Needed because the window is called as a non-modal window.
    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
        Close();
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
        this.label2 = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textDateTime = new System.Windows.Forms.TextBox();
        this.butDelete = new OpenDental.UI.Button();
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
        this.butOK.Location = new System.Drawing.Point(471, 315);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(564, 315);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(0, 15);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(97, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "Date / Time";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(98, 63);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Adjustment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(541, 218);
        this.textNote.TabIndex = 0;
        this.textNote.Text = "";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(3, 66);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(94, 16);
        this.label4.TabIndex = 9;
        this.label4.Text = "Note";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(98, 37);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(134, 20);
        this.textUser.TabIndex = 126;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(2, 39);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(94, 16);
        this.label16.TabIndex = 127;
        this.label16.Text = "User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTime
        //
        this.textDateTime.Location = new System.Drawing.Point(98, 11);
        this.textDateTime.Name = "textDateTime";
        this.textDateTime.Size = new System.Drawing.Size(164, 20);
        this.textDateTime.TabIndex = 128;
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
        this.butDelete.Location = new System.Drawing.Point(33, 315);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(80, 24);
        this.butDelete.TabIndex = 129;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormTaskNoteEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(664, 366);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDateTime);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormTaskNoteEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Task Note Edit";
        this.Load += new System.EventHandler(this.FormTaskNoteEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUser = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTime = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
}
//Needed because the window is called as a non-modal window.

//Needed because the window is called as a non-modal window.