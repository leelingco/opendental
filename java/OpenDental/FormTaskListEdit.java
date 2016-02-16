//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormTaskListEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.TaskObjectType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTaskListEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listDateType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textDescript = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDate textDateTL;
    private TaskList Cur;
    private System.Windows.Forms.CheckBox checkFromNum = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ListBox listObjectType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private TextBox textTaskListNum = new TextBox();
    private Label labelTaskListNum = new Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormTaskListEdit(TaskList cur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Cur = cur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTaskListEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDescript = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.listDateType = new System.Windows.Forms.ListBox();
        this.checkFromNum = new System.Windows.Forms.CheckBox();
        this.textDateTL = new OpenDental.ValidDate();
        this.listObjectType = new System.Windows.Forms.ListBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textTaskListNum = new System.Windows.Forms.TextBox();
        this.labelTaskListNum = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(395, 191);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 5;
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
        this.butOK.Location = new System.Drawing.Point(395, 150);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(116, 19);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescript
        //
        this.textDescript.Location = new System.Drawing.Point(127, 18);
        this.textDescript.Name = "textDescript";
        this.textDescript.Size = new System.Drawing.Size(293, 20);
        this.textDescript.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 50);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(116, 19);
        this.label2.TabIndex = 4;
        this.label2.Text = "Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(218, 47);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(185, 32);
        this.label3.TabIndex = 6;
        this.label3.Text = "Leave blank unless you want this list to show on a dated list";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(8, 82);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(116, 19);
        this.label4.TabIndex = 7;
        this.label4.Text = "Date Type";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listDateType
        //
        this.listDateType.Location = new System.Drawing.Point(127, 83);
        this.listDateType.Name = "listDateType";
        this.listDateType.Size = new System.Drawing.Size(120, 56);
        this.listDateType.TabIndex = 2;
        //
        // checkFromNum
        //
        this.checkFromNum.CheckAlign = System.Drawing.ContentAlignment.TopRight;
        this.checkFromNum.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkFromNum.Location = new System.Drawing.Point(8, 149);
        this.checkFromNum.Name = "checkFromNum";
        this.checkFromNum.Size = new System.Drawing.Size(133, 21);
        this.checkFromNum.TabIndex = 3;
        this.checkFromNum.Text = "Is From Repeating";
        this.checkFromNum.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateTL
        //
        this.textDateTL.Location = new System.Drawing.Point(127, 50);
        this.textDateTL.Name = "textDateTL";
        this.textDateTL.Size = new System.Drawing.Size(87, 20);
        this.textDateTL.TabIndex = 1;
        //
        // listObjectType
        //
        this.listObjectType.Location = new System.Drawing.Point(127, 173);
        this.listObjectType.Name = "listObjectType";
        this.listObjectType.Size = new System.Drawing.Size(120, 43);
        this.listObjectType.TabIndex = 15;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(8, 172);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(116, 19);
        this.label6.TabIndex = 16;
        this.label6.Text = "Object Type";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTaskListNum
        //
        this.textTaskListNum.Location = new System.Drawing.Point(366, 94);
        this.textTaskListNum.Name = "textTaskListNum";
        this.textTaskListNum.ReadOnly = true;
        this.textTaskListNum.Size = new System.Drawing.Size(54, 20);
        this.textTaskListNum.TabIndex = 136;
        this.textTaskListNum.Visible = false;
        //
        // labelTaskListNum
        //
        this.labelTaskListNum.Location = new System.Drawing.Point(276, 95);
        this.labelTaskListNum.Name = "labelTaskListNum";
        this.labelTaskListNum.Size = new System.Drawing.Size(88, 16);
        this.labelTaskListNum.TabIndex = 135;
        this.labelTaskListNum.Text = "TaskListNum";
        this.labelTaskListNum.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelTaskListNum.Visible = false;
        //
        // FormTaskListEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(503, 242);
        this.Controls.Add(this.textTaskListNum);
        this.Controls.Add(this.labelTaskListNum);
        this.Controls.Add(this.listObjectType);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textDateTL);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.checkFromNum);
        this.Controls.Add(this.listDateType);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTaskListEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Task List";
        this.Load += new System.EventHandler(this.FormTaskListEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTaskListEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescript.Text = Cur.Descript;
        if (Cur.DateTL.Year > 1880)
        {
            textDateTL.Text = Cur.DateTL.ToShortDateString();
        }
         
        for (int i = 0;i < Enum.GetNames(TaskDateType.class).Length;i++)
        {
            listDateType.Items.Add(Lan.g("enumTaskDateType", Enum.GetNames(TaskDateType.class)[i]));
            if (((Enum)Cur.DateType).ordinal() == i)
            {
                listDateType.SelectedIndex = i;
            }
             
        }
        if (Cur.FromNum == 0)
        {
            checkFromNum.Checked = false;
            checkFromNum.Enabled = false;
        }
        else
        {
            checkFromNum.Checked = true;
        } 
        if (Cur.IsRepeating)
        {
            textDateTL.Enabled = false;
            listObjectType.Enabled = false;
            if (Cur.Parent != 0)
            {
                //not a main parent
                listDateType.Enabled = false;
            }
             
        }
         
        for (int i = 0;i < Enum.GetNames(TaskObjectType.class).Length;i++)
        {
            listObjectType.Items.Add(Lan.g("enumTaskObjectType", Enum.GetNames(TaskObjectType.class)[i]));
            if (((Enum)Cur.ObjectType).ordinal() == i)
            {
                listObjectType.SelectedIndex = i;
            }
             
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateTL.errorProvider1.GetError(textDateTL), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        Cur.Descript = textDescript.Text;
        Cur.DateTL = PIn.Date(textDateTL.Text);
        Cur.DateType = (TaskDateType)listDateType.SelectedIndex;
        if (!checkFromNum.Checked)
        {
            //user unchecked the box
            Cur.FromNum = 0;
        }
         
        Cur.ObjectType = (TaskObjectType)listObjectType.SelectedIndex;
        try
        {
            if (IsNew)
            {
                TaskLists.insert(Cur);
            }
            else
            {
                TaskLists.update(Cur);
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


