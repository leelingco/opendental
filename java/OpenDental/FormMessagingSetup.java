//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:20 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormMessagingSetup;
import OpenDental.FormSigElementDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.SignalElementType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormMessagingSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private ListBox listMessages = new ListBox();
    private Label label5 = new Label();
    private ListBox listExtras = new ListBox();
    private Label label4 = new Label();
    private ListBox listToFrom = new ListBox();
    private Label label1 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private SigElementDef[] ListUser = new SigElementDef[]();
    private SigElementDef[] ListExtras = new SigElementDef[]();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private SigElementDef[] ListMessages = new SigElementDef[]();
    /**
    * 
    */
    public FormMessagingSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMessagingSetup.class);
        this.listMessages = new System.Windows.Forms.ListBox();
        this.label5 = new System.Windows.Forms.Label();
        this.listExtras = new System.Windows.Forms.ListBox();
        this.label4 = new System.Windows.Forms.Label();
        this.listToFrom = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMessages
        //
        this.listMessages.FormattingEnabled = true;
        this.listMessages.Location = new System.Drawing.Point(192, 38);
        this.listMessages.Name = "listMessages";
        this.listMessages.Size = new System.Drawing.Size(98, 368);
        this.listMessages.TabIndex = 18;
        this.listMessages.DoubleClick += new System.EventHandler(this.listMessages_DoubleClick);
        this.listMessages.Click += new System.EventHandler(this.listMessages_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(190, 19);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 17;
        this.label5.Text = "Messages";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listExtras
        //
        this.listExtras.FormattingEnabled = true;
        this.listExtras.Location = new System.Drawing.Point(111, 38);
        this.listExtras.Name = "listExtras";
        this.listExtras.Size = new System.Drawing.Size(75, 368);
        this.listExtras.TabIndex = 16;
        this.listExtras.DoubleClick += new System.EventHandler(this.listExtras_DoubleClick);
        this.listExtras.Click += new System.EventHandler(this.listExtras_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(109, 19);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(78, 16);
        this.label4.TabIndex = 15;
        this.label4.Text = "Extras";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listToFrom
        //
        this.listToFrom.FormattingEnabled = true;
        this.listToFrom.Location = new System.Drawing.Point(30, 38);
        this.listToFrom.Name = "listToFrom";
        this.listToFrom.Size = new System.Drawing.Size(75, 368);
        this.listToFrom.TabIndex = 12;
        this.listToFrom.DoubleClick += new System.EventHandler(this.listToFrom_DoubleClick);
        this.listToFrom.Click += new System.EventHandler(this.listToFrom_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(28, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(78, 16);
        this.label1.TabIndex = 11;
        this.label1.Text = "Users";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(365, 379);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(365, 241);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 19;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(365, 273);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(75, 26);
        this.butUp.TabIndex = 20;
        this.butUp.Text = "Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(365, 305);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(75, 26);
        this.butDown.TabIndex = 21;
        this.butDown.Text = "Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // FormMessagingSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(492, 430);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listMessages);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.listExtras);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.listToFrom);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMessagingSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Messaging Setup";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormMessagingSetup_FormClosing);
        this.Load += new System.EventHandler(this.FormMessagingSetup_Load);
        this.ResumeLayout(false);
    }

    private void formMessagingSetup_Load(Object sender, EventArgs e) throws Exception {
        fillLists();
    }

    private void fillLists() throws Exception {
        SigElementDefs.refreshCache();
        ListUser = SigElementDefs.getSubList(SignalElementType.User);
        ListExtras = SigElementDefs.getSubList(SignalElementType.Extra);
        ListMessages = SigElementDefs.getSubList(SignalElementType.Message);
        listToFrom.Items.Clear();
        for (int i = 0;i < ListUser.Length;i++)
        {
            listToFrom.Items.Add(ListUser[i].SigText);
        }
        listExtras.Items.Clear();
        for (int i = 0;i < ListExtras.Length;i++)
        {
            listExtras.Items.Add(ListExtras[i].SigText);
        }
        listMessages.Items.Clear();
        for (int i = 0;i < ListMessages.Length;i++)
        {
            listMessages.Items.Add(ListMessages[i].SigText);
        }
    }

    private void listToFrom_Click(Object sender, EventArgs e) throws Exception {
        listExtras.SelectedIndex = -1;
        listMessages.SelectedIndex = -1;
    }

    private void listExtras_Click(Object sender, EventArgs e) throws Exception {
        listToFrom.SelectedIndex = -1;
        listMessages.SelectedIndex = -1;
    }

    private void listMessages_Click(Object sender, EventArgs e) throws Exception {
        listToFrom.SelectedIndex = -1;
        listExtras.SelectedIndex = -1;
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormSigElementDefEdit FormS = new FormSigElementDefEdit();
        FormS.ElementCur = new SigElementDef();
        FormS.ElementCur.LightColor = Color.White;
        //default is user
        if (listExtras.SelectedIndex != -1)
        {
            FormS.ElementCur.SigElementType = SignalElementType.Extra;
        }
         
        if (listMessages.SelectedIndex != -1)
        {
            FormS.ElementCur.SigElementType = SignalElementType.Message;
        }
         
        FormS.IsNew = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //set the order
        SigElementDef element = FormS.ElementCur.copy();
        if (element.SigElementType == SignalElementType.User)
        {
            element.ItemOrder = ListUser.Length;
            SigElementDefs.update(element);
        }
        else if (element.SigElementType == SignalElementType.Extra)
        {
            element.ItemOrder = ListExtras.Length;
            SigElementDefs.update(element);
        }
        else if (element.SigElementType == SignalElementType.Message)
        {
            element.ItemOrder = ListMessages.Length;
            SigElementDefs.update(element);
        }
           
        fillLists();
        for (int i = 0;i < ListUser.Length;i++)
        {
            //Select the item
            if (ListUser[i].SigElementDefNum == element.SigElementDefNum)
            {
                listToFrom.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < ListExtras.Length;i++)
        {
            if (ListExtras[i].SigElementDefNum == element.SigElementDefNum)
            {
                listExtras.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < ListMessages.Length;i++)
        {
            if (ListMessages[i].SigElementDefNum == element.SigElementDefNum)
            {
                listMessages.SelectedIndex = i;
            }
             
        }
    }

    private void listToFrom_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listToFrom.SelectedIndex == -1)
        {
            return ;
        }
         
        FormSigElementDefEdit FormS = new FormSigElementDefEdit();
        FormS.ElementCur = ListUser[listToFrom.SelectedIndex];
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillLists();
    }

    //not possible to change ItemOrder here.
    private void listExtras_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listExtras.SelectedIndex == -1)
        {
            return ;
        }
         
        FormSigElementDefEdit FormS = new FormSigElementDefEdit();
        FormS.ElementCur = ListExtras[listExtras.SelectedIndex];
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillLists();
    }

    private void listMessages_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMessages.SelectedIndex == -1)
        {
            return ;
        }
         
        FormSigElementDefEdit FormS = new FormSigElementDefEdit();
        FormS.ElementCur = ListMessages[listMessages.SelectedIndex];
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillLists();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        if (listToFrom.SelectedIndex == -1 && listExtras.SelectedIndex == -1 && listMessages.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        int selected = new int();
        if (listToFrom.SelectedIndex != -1)
        {
            selected = listToFrom.SelectedIndex;
            if (selected == 0)
            {
                return ;
            }
             
            SigElementDefs.moveUp(selected,ListUser);
            fillLists();
            listToFrom.SelectedIndex = selected - 1;
        }
        else if (listExtras.SelectedIndex != -1)
        {
            selected = listExtras.SelectedIndex;
            if (selected == 0)
            {
                return ;
            }
             
            SigElementDefs.moveUp(selected,ListExtras);
            fillLists();
            listExtras.SelectedIndex = selected - 1;
        }
        else if (listMessages.SelectedIndex != -1)
        {
            selected = listMessages.SelectedIndex;
            if (selected == 0)
            {
                return ;
            }
             
            SigElementDefs.moveUp(selected,ListMessages);
            fillLists();
            listMessages.SelectedIndex = selected - 1;
        }
           
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        if (listToFrom.SelectedIndex == -1 && listExtras.SelectedIndex == -1 && listMessages.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        int selected = new int();
        if (listToFrom.SelectedIndex != -1)
        {
            selected = listToFrom.SelectedIndex;
            if (selected == listToFrom.Items.Count - 1)
            {
                return ;
            }
             
            SigElementDefs.moveDown(selected,ListUser);
            fillLists();
            listToFrom.SelectedIndex = selected + 1;
        }
        else if (listExtras.SelectedIndex != -1)
        {
            selected = listExtras.SelectedIndex;
            if (selected == listExtras.Items.Count - 1)
            {
                return ;
            }
             
            SigElementDefs.moveDown(selected,ListExtras);
            fillLists();
            listExtras.SelectedIndex = selected + 1;
        }
        else if (listMessages.SelectedIndex != -1)
        {
            selected = listMessages.SelectedIndex;
            if (selected == listMessages.Items.Count - 1)
            {
                return ;
            }
             
            SigElementDefs.moveDown(selected,ListMessages);
            fillLists();
            listMessages.SelectedIndex = selected + 1;
        }
           
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formMessagingSetup_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        DataValid.setInvalid(InvalidType.Signals);
    }

}


