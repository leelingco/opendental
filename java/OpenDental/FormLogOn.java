//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:19 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormLogOn;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.UserGroups;
import OpenDentBusiness.Userod;
import OpenDentBusiness.UserodC;
import OpenDentBusiness.Userods;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLogOn  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listUser = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private List<Userod> shortList = new List<Userod>();
    /**
    * 
    */
    public FormLogOn() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLogOn.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listUser = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(361, 321);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Exit";
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
        this.butOK.Location = new System.Drawing.Point(361, 280);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listUser
        //
        this.listUser.Location = new System.Drawing.Point(31, 31);
        this.listUser.Name = "listUser";
        this.listUser.Size = new System.Drawing.Size(141, 316);
        this.listUser.TabIndex = 2;
        this.listUser.MouseUp += new System.Windows.Forms.MouseEventHandler(this.listUser_MouseUp);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(30, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(122, 18);
        this.label1.TabIndex = 6;
        this.label1.Text = "User";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(188, 10);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(122, 18);
        this.label2.TabIndex = 7;
        this.label2.Text = "Password";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(189, 31);
        this.textPassword.Name = "textPassword";
        this.textPassword.PasswordChar = '*';
        this.textPassword.Size = new System.Drawing.Size(215, 20);
        this.textPassword.TabIndex = 0;
        //
        // FormLogOn
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(464, 378);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listUser);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.Name = "FormLogOn";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Log On";
        this.Load += new System.EventHandler(this.FormLogOn_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLogOn_Load(Object sender, System.EventArgs e) throws Exception {
        fillListBox();
    }

    private void listUser_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        textPassword.Focus();
    }

    private void butResetPassword_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //This feature was never used, so was removed.
    //FormPasswordReset FormPR=new FormPasswordReset();
    //FormPR.ShowDialog();
    private void fillListBox() throws Exception {
        Userods.refreshCache();
        UserGroups.refreshCache();
        GroupPermissions.refreshCache();
        listUser.BeginUpdate();
        listUser.Items.Clear();
        shortList = UserodC.getShortList();
        for (int i = 0;i < shortList.Count;i++)
        {
            listUser.Items.Add(shortList[i]);
            if (Security.getCurUser() != null && shortList[i].UserNum == Security.getCurUser().UserNum)
            {
                listUser.SelectedIndex = i;
            }
             
        }
        if (listUser.SelectedIndex == -1)
        {
            listUser.SelectedIndex = 0;
        }
         
        listUser.EndUpdate();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Userod selectedUser = (Userod)listUser.SelectedItem;
        if (!Userods.CheckTypedPassword(textPassword.Text, selectedUser.Password))
        {
            MsgBox.show(this,"Incorrect password");
            return ;
        }
         
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb && StringSupport.equals(selectedUser.Password, "") && StringSupport.equals(textPassword.Text, ""))
        {
            MsgBox.show(this,"When using the web service, not allowed to log in with no password.  A password should be added for this user.");
            return ;
        }
         
        Security.setCurUser(selectedUser.copy());
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            String password = textPassword.Text;
            if (Programs.usingEcwTightOrFullMode())
            {
                //ecw requires hash, but non-ecw requires actual password
                password = Userods.encryptPassword(password,true);
            }
             
            Security.PasswordTyped = password;
        }
         
        if (PrefC.getBool(PrefName.TasksCheckOnStartup))
        {
            int taskcount = Tasks.userTasksCount(Security.getCurUser().UserNum);
            if (taskcount > 0)
            {
                MessageBox.Show(Lan.g(this,"There are ") + taskcount + Lan.g(this," unread tasks on your tasklists."));
            }
             
        }
         
        Plugins.hookAddCode(this,"FormLogOn.butOK_Click_end");
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


