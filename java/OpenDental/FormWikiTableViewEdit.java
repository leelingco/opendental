//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.WikiView;
import OpenDental.Properties.Resources;

public class FormWikiTableViewEdit  extends Form 
{

    public WikiView WikiViewCur;
    /**
    * Passed in.  Some will show in the listbox and some in the grid.
    */
    public List<String> ColsAll = new List<String>();
    /**
    * The subset of ColsAll which will show in listAvail.
    */
    private List<String> ColsAvail = new List<String>();
    public boolean IsNew = new boolean();
    public FormWikiTableViewEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiTableViewEdit_Load(Object sender, EventArgs e) throws Exception {
        textViewName.Text = WikiViewCur.ViewName;
        fillLists(false);
    }

    /**
    * Except on startup, the existing orderby should be preserved.
    */
    private void fillLists(boolean preserveOrderBy) throws Exception {
        if (preserveOrderBy)
        {
            WikiViewCur.OrderBy = "";
            if (comboOrderBy.SelectedIndex > 0)
            {
                WikiViewCur.OrderBy = comboOrderBy.SelectedItem.ToString();
            }
             
        }
         
        //------------------------------
        ColsAvail = new List<String>();
        for (int a = 0;a < ColsAll.Count;a++)
        {
            if (!WikiViewCur.Columns.Contains(ColsAll[a]))
            {
                ColsAvail.Add(ColsAll[a]);
            }
             
        }
        listAvail.Items.Clear();
        for (int i = 0;i < ColsAvail.Count;i++)
        {
            listAvail.Items.Add(ColsAvail[i]);
        }
        listShowing.Items.Clear();
        for (int i = 0;i < WikiViewCur.Columns.Count;i++)
        {
            listShowing.Items.Add(WikiViewCur.Columns[i]);
        }
        comboOrderBy.Items.Clear();
        comboOrderBy.Items.Add(Lan.g(this,"none"));
        for (int i = 0;i < WikiViewCur.Columns.Count;i++)
        {
            comboOrderBy.Items.Add(WikiViewCur.Columns[i]);
            if (StringSupport.equals(WikiViewCur.OrderBy, WikiViewCur.Columns[i]))
            {
                comboOrderBy.SelectedIndex = i + 1;
            }
             
        }
    }

    private void butRight_Click(Object sender, EventArgs e) throws Exception {
        if (listAvail.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item on the left first.");
            return ;
        }
         
        //It should to be put back in the same order as the CollsAll, although it's not critical.  Columns may still get out of order later.
        List<String> colsShowingTemp = new List<String>(WikiViewCur.Columns);
        colsShowingTemp.Add(ColsAvail[listAvail.SelectedIndex]);
        WikiViewCur.Columns = new List<String>();
        for (int i = 0;i < ColsAll.Count;i++)
        {
            if (colsShowingTemp.Contains(ColsAll[i]))
            {
                WikiViewCur.Columns.Add(ColsAll[i]);
            }
             
        }
        fillLists(true);
    }

    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
        if (listShowing.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item on the right first.");
            return ;
        }
         
        WikiViewCur.Columns.RemoveAt(listShowing.SelectedIndex);
        fillLists(true);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this view?"))
        {
            return ;
        }
         
        WikiViewCur = null;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textViewName.Text, ""))
        {
            MsgBox.show(this,"Please enter a view name first.");
            return ;
        }
         
        if (WikiViewCur.Columns.Count == 0)
        {
            MsgBox.show(this,"Please select columns to show first.");
            return ;
        }
         
        WikiViewCur.ViewName = textViewName.Text;
        WikiViewCur.OrderBy = "";
        if (comboOrderBy.SelectedIndex > 0)
        {
            WikiViewCur.OrderBy = comboOrderBy.SelectedItem.ToString();
        }
         
        //WikiViewCur will be read and processed from the calling form.
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
        this.labelView = new System.Windows.Forms.Label();
        this.textViewName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.listAvail = new System.Windows.Forms.ListBox();
        this.listShowing = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.comboOrderBy = new System.Windows.Forms.ComboBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.butRight = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelView
        //
        this.labelView.Location = new System.Drawing.Point(12, 11);
        this.labelView.Name = "labelView";
        this.labelView.Size = new System.Drawing.Size(94, 18);
        this.labelView.TabIndex = 35;
        this.labelView.Text = "View Name";
        this.labelView.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textViewName
        //
        this.textViewName.Location = new System.Drawing.Point(112, 11);
        this.textViewName.Name = "textViewName";
        this.textViewName.Size = new System.Drawing.Size(119, 20);
        this.textViewName.TabIndex = 36;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 59);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(94, 18);
        this.label1.TabIndex = 37;
        this.label1.Text = "Available Columns";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listAvail
        //
        this.listAvail.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listAvail.FormattingEnabled = true;
        this.listAvail.Location = new System.Drawing.Point(15, 82);
        this.listAvail.Name = "listAvail";
        this.listAvail.Size = new System.Drawing.Size(161, 446);
        this.listAvail.TabIndex = 38;
        //
        // listShowing
        //
        this.listShowing.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listShowing.FormattingEnabled = true;
        this.listShowing.Location = new System.Drawing.Point(282, 82);
        this.listShowing.Name = "listShowing";
        this.listShowing.Size = new System.Drawing.Size(161, 446);
        this.listShowing.TabIndex = 42;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(281, 59);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(94, 18);
        this.label2.TabIndex = 41;
        this.label2.Text = "Showing Columns";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 38);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(94, 18);
        this.label3.TabIndex = 43;
        this.label3.Text = "Order By";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.label3.Visible = false;
        //
        // comboOrderBy
        //
        this.comboOrderBy.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboOrderBy.FormattingEnabled = true;
        this.comboOrderBy.Location = new System.Drawing.Point(112, 35);
        this.comboOrderBy.Name = "comboOrderBy";
        this.comboOrderBy.Size = new System.Drawing.Size(163, 21);
        this.comboOrderBy.TabIndex = 44;
        this.comboOrderBy.Visible = false;
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
        this.butDelete.Location = new System.Drawing.Point(15, 541);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 45;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(206, 227);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(44, 24);
        this.butLeft.TabIndex = 40;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(206, 193);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(44, 24);
        this.butRight.TabIndex = 39;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(356, 541);
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
        this.butCancel.Location = new System.Drawing.Point(437, 541);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormWikiTableViewEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(524, 573);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.comboOrderBy);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listShowing);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butLeft);
        this.Controls.Add(this.butRight);
        this.Controls.Add(this.listAvail);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textViewName);
        this.Controls.Add(this.labelView);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiTableViewEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Wiki Table View";
        this.Load += new System.EventHandler(this.FormWikiTableViewEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelView = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textViewName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listAvail = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butLeft;
    private System.Windows.Forms.ListBox listShowing = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboOrderBy = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butDelete;
}


