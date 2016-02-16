//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormWikiListEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiLists;
import OpenDental.Properties.Resources;

public class FormWikiLists  extends Form 
{

    private List<String> wikiLists = new List<String>();
    public FormWikiLists() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiLists_Load(Object sender, EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        listBox1.Items.Clear();
        wikiLists = WikiLists.getAllLists();
        for (Object __dummyForeachVar0 : wikiLists)
        {
            String list = (String)__dummyForeachVar0;
            listBox1.Items.Add(list.Substring(9));
        }
    }

    private void listBox1_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listBox1.SelectedIndices.Count < 1)
        {
            return ;
        }
         
        FormWikiListEdit FormWLE = new FormWikiListEdit();
        FormWLE.WikiListCurName = wikiLists[listBox1.SelectedIndex].Substring(9);
        FormWLE.ShowDialog();
        fillList();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        InputBox inputListName = new InputBox("New List Name");
        inputListName.ShowDialog();
        if (inputListName.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //Format input as it would be saved in the database--------------------------------------------
        inputListName.textResult.Text = inputListName.textResult.Text.ToLower().Replace(" ", "");
        //Validate list name---------------------------------------------------------------------------
        if (DbHelper.isMySQLReservedWord(inputListName.textResult.Text))
        {
            //Can become an issue when retrieving column header names.
            MsgBox.show(this,"List name is a reserved word in MySQL.");
            return ;
        }
         
        if (StringSupport.equals(inputListName.textResult.Text, ""))
        {
            MsgBox.show(this,"List name cannot be blank.");
            return ;
        }
         
        if (WikiLists.CheckExists(inputListName.textResult.Text))
        {
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"List already exists with that name. Would you like to edit existing list?"))
            {
                return ;
            }
             
        }
         
        FormWikiListEdit FormWLE = new FormWikiListEdit();
        FormWLE.WikiListCurName = inputListName.textResult.Text;
        //FormWLE.IsNew=true;//set within the form.
        FormWLE.ShowDialog();
        fillList();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.listBox1 = new System.Windows.Forms.ListBox();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listBox1
        //
        this.listBox1.FormattingEnabled = true;
        this.listBox1.Location = new System.Drawing.Point(12, 12);
        this.listBox1.Name = "listBox1";
        this.listBox1.Size = new System.Drawing.Size(197, 511);
        this.listBox1.TabIndex = 4;
        this.listBox1.DoubleClick += new System.EventHandler(this.listBox1_DoubleClick);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(215, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 5;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(215, 498);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormWikiLists
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(302, 534);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listBox1);
        this.Controls.Add(this.butClose);
        this.Name = "FormWikiLists";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "WikiLists";
        this.Load += new System.EventHandler(this.FormWikiLists_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ListBox listBox1 = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
}


