//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import OpenDentBusiness.UserodC;
import OpenDentBusiness.Userods;

public class FormUserPick  extends Form 
{

    private List<Userod> shortList = new List<Userod>();
    /**
    * If this form closes with OK, then this value will be filled.
    */
    public long SelectedUserNum = new long();
    public FormUserPick() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formUserPick_Load(Object sender, EventArgs e) throws Exception {
        shortList = UserodC.getShortList();
        for (int i = 0;i < shortList.Count;i++)
        {
            listUser.Items.Add(shortList[i]);
        }
    }

    private void listUser_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listUser.SelectedIndex == -1)
        {
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.TaskEdit,true) && Userods.GetInbox(shortList[listUser.SelectedIndex].UserNum) != 0)
        {
            MsgBox.show(this,"Please select a user that does not have an inbox.");
            return ;
        }
         
        SelectedUserNum = shortList[listUser.SelectedIndex].UserNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listUser.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please pick a user first.");
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.TaskEdit,true) && Userods.GetInbox(shortList[listUser.SelectedIndex].UserNum) != 0)
        {
            MsgBox.show(this,"Please select a user that does not have an inbox.");
            return ;
        }
         
        SelectedUserNum = shortList[listUser.SelectedIndex].UserNum;
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
        this.listUser = new System.Windows.Forms.ListBox();
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
        this.butOK.Location = new System.Drawing.Point(215, 451);
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
        this.butCancel.Location = new System.Drawing.Point(215, 492);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listUser
        //
        this.listUser.FormattingEnabled = true;
        this.listUser.Location = new System.Drawing.Point(12, 18);
        this.listUser.Name = "listUser";
        this.listUser.Size = new System.Drawing.Size(176, 498);
        this.listUser.TabIndex = 4;
        this.listUser.DoubleClick += new System.EventHandler(this.listUser_DoubleClick);
        //
        // FormUserPick
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(316, 534);
        this.Controls.Add(this.listUser);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormUserPick";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pick User";
        this.Load += new System.EventHandler(this.FormUserPick_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listUser = new System.Windows.Forms.ListBox();
}


