//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.Employees;
import OpenDentBusiness.TimeCardRule;
import OpenDentBusiness.TimeCardRules;
import OpenDental.FormTimeCardRuleEdit;
import OpenDental.Properties.Resources;

public class FormTimeCardRuleEdit  extends Form 
{

    public TimeCardRule timeCardRule;
    public FormTimeCardRuleEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTimeCardRuleEdit_Load(Object sender, EventArgs e) throws Exception {
        listEmp.Items.Add(Lan.g(this,"All Employees"));
        listEmp.SelectedIndex = 0;
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            listEmp.Items.Add(Employees.getListShort()[i].FName + " " + Employees.getListShort()[i].LName);
            if (Employees.getListShort()[i].EmployeeNum == timeCardRule.EmployeeNum)
            {
                listEmp.SelectedIndex = i + 1;
            }
             
        }
        textOverHoursPerDay.Text = timeCardRule.OverHoursPerDay.ToStringHmm();
        textAfterTimeOfDay.Text = timeCardRule.AfterTimeOfDay.ToStringHmm();
        textBeforeTimeOfDay.Text = timeCardRule.BeforeTimeOfDay.ToStringHmm();
    }

    private void but5pm_Click(Object sender, EventArgs e) throws Exception {
        DateTime dt = new DateTime(2010, 1, 1, 17, 0, 0);
        textAfterTimeOfDay.Text = dt.ToShortTimeString();
    }

    private void but6am_Click(Object sender, EventArgs e) throws Exception {
        DateTime dt = new DateTime(2010, 1, 1, 6, 0, 0);
        textBeforeTimeOfDay.Text = dt.ToShortTimeString();
    }

    /**
    * If entering text in overtime, clear differential text boxes.
    */
    private void textOverHoursPerDay_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textOverHoursPerDay.Text, ""))
        {
            textAfterTimeOfDay.Text = "";
            textBeforeTimeOfDay.Text = "";
        }
         
    }

    /**
    * If entering text in differential boxes, clear overtime text box.
    */
    private void textBeforeTimeOfDay_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textBeforeTimeOfDay.Text, ""))
        {
            textOverHoursPerDay.Text = "";
        }
         
    }

    /**
    * If entering text in differential boxes, clear overtime text box.
    */
    private void textAfterTimeOfDay_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textAfterTimeOfDay.Text, ""))
        {
            textOverHoursPerDay.Text = "";
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (timeCardRule.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Are you sure you want to delete this time card rule?"))
        {
            return ;
        }
         
        TimeCardRules.delete(timeCardRule.TimeCardRuleNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //Verify Data.
        if (listEmp.SelectedIndex < 0)
        {
            MsgBox.show(this,"Please select an employee.");
            return ;
        }
         
        TimeSpan overHoursPerDay = TimeSpan.Zero;
        if (!StringSupport.equals(textOverHoursPerDay.Text, ""))
        {
            try
            {
                if (textOverHoursPerDay.Text.Contains(":"))
                {
                    overHoursPerDay = TimeSpan.Parse(textOverHoursPerDay.Text);
                }
                else
                {
                    overHoursPerDay = TimeSpan.FromHours(PIn.Double(textOverHoursPerDay.Text));
                } 
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Over hours per day invalid.");
                return ;
            }

            if (overHoursPerDay == TimeSpan.Zero || overHoursPerDay.Days > 0)
            {
                MsgBox.show(this,"Over hours per day invalid.");
                return ;
            }
             
        }
         
        TimeSpan afterTimeOfDay = TimeSpan.Zero;
        if (!StringSupport.equals(textAfterTimeOfDay.Text, ""))
        {
            try
            {
                afterTimeOfDay = DateTime.Parse(textAfterTimeOfDay.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"After time of day invalid.");
                return ;
            }

            if (afterTimeOfDay == TimeSpan.Zero || afterTimeOfDay.Days > 0)
            {
                MsgBox.show(this,"After time of day invalid.");
                return ;
            }
             
        }
         
        TimeSpan beforeTimeOfDay = TimeSpan.Zero;
        if (!StringSupport.equals(textBeforeTimeOfDay.Text, ""))
        {
            try
            {
                beforeTimeOfDay = DateTime.Parse(textBeforeTimeOfDay.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar2)
            {
                MsgBox.show(this,"Before time of day invalid.");
                return ;
            }

            if (beforeTimeOfDay == TimeSpan.Zero || beforeTimeOfDay.Days > 0)
            {
                MsgBox.show(this,"Before time of day invalid.");
                return ;
            }
             
        }
         
        if (overHoursPerDay == TimeSpan.Zero && afterTimeOfDay == TimeSpan.Zero && beforeTimeOfDay == TimeSpan.Zero)
        {
            MsgBox.show(this,"Either over hours, after or before time of day must be entered.");
            return ;
        }
         
        //save-------------------------------------------------
        if (listEmp.SelectedIndex == 0)
        {
            timeCardRule.EmployeeNum = 0;
        }
        else
        {
            //All employees.
            timeCardRule.EmployeeNum = Employees.getListShort()[listEmp.SelectedIndex - 1].EmployeeNum;
        } 
        timeCardRule.OverHoursPerDay = overHoursPerDay;
        timeCardRule.AfterTimeOfDay = afterTimeOfDay;
        timeCardRule.BeforeTimeOfDay = beforeTimeOfDay;
        if (timeCardRule.getIsNew())
        {
            TimeCardRules.insert(timeCardRule);
        }
        else
        {
            TimeCardRules.update(timeCardRule);
        } 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTimeCardRuleEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textOverHoursPerDay = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textAfterTimeOfDay = new System.Windows.Forms.TextBox();
        this.listEmp = new System.Windows.Forms.ListBox();
        this.but6am = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.textBeforeTimeOfDay = new System.Windows.Forms.TextBox();
        this.but5pm = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 127);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(253, 18);
        this.label1.TabIndex = 4;
        this.label1.Text = "Employee";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(18, 10);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(153, 18);
        this.label2.TabIndex = 6;
        this.label2.Text = "Overtime if over Hours Per Day";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOverHoursPerDay
        //
        this.textOverHoursPerDay.Location = new System.Drawing.Point(174, 10);
        this.textOverHoursPerDay.Name = "textOverHoursPerDay";
        this.textOverHoursPerDay.Size = new System.Drawing.Size(62, 20);
        this.textOverHoursPerDay.TabIndex = 7;
        this.textOverHoursPerDay.Text = "8:00";
        this.textOverHoursPerDay.TextChanged += new System.EventHandler(this.textOverHoursPerDay_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(39, 46);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(111, 18);
        this.label3.TabIndex = 8;
        this.label3.Text = "After Time of Day";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAfterTimeOfDay
        //
        this.textAfterTimeOfDay.Location = new System.Drawing.Point(153, 46);
        this.textAfterTimeOfDay.Name = "textAfterTimeOfDay";
        this.textAfterTimeOfDay.Size = new System.Drawing.Size(62, 20);
        this.textAfterTimeOfDay.TabIndex = 9;
        this.textAfterTimeOfDay.Text = "17:00";
        this.textAfterTimeOfDay.TextChanged += new System.EventHandler(this.textAfterTimeOfDay_TextChanged);
        //
        // listEmp
        //
        this.listEmp.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listEmp.FormattingEnabled = true;
        this.listEmp.Location = new System.Drawing.Point(21, 149);
        this.listEmp.Name = "listEmp";
        this.listEmp.Size = new System.Drawing.Size(215, 238);
        this.listEmp.TabIndex = 156;
        //
        // but6am
        //
        this.but6am.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but6am.setAutosize(true);
        this.but6am.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but6am.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but6am.setCornerRadius(4F);
        this.but6am.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.but6am.Location = new System.Drawing.Point(221, 17);
        this.but6am.Name = "but6am";
        this.but6am.Size = new System.Drawing.Size(35, 22);
        this.but6am.TabIndex = 161;
        this.but6am.Text = "6 AM";
        this.but6am.Click += new System.EventHandler(this.but6am_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(39, 19);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 18);
        this.label4.TabIndex = 159;
        this.label4.Text = "Before Time of Day";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBeforeTimeOfDay
        //
        this.textBeforeTimeOfDay.Location = new System.Drawing.Point(153, 19);
        this.textBeforeTimeOfDay.Name = "textBeforeTimeOfDay";
        this.textBeforeTimeOfDay.Size = new System.Drawing.Size(62, 20);
        this.textBeforeTimeOfDay.TabIndex = 160;
        this.textBeforeTimeOfDay.Text = "6:00";
        this.textBeforeTimeOfDay.TextChanged += new System.EventHandler(this.textBeforeTimeOfDay_TextChanged);
        //
        // but5pm
        //
        this.but5pm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but5pm.setAutosize(true);
        this.but5pm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but5pm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but5pm.setCornerRadius(4F);
        this.but5pm.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.but5pm.Location = new System.Drawing.Point(221, 45);
        this.but5pm.Name = "but5pm";
        this.but5pm.Size = new System.Drawing.Size(35, 22);
        this.but5pm.TabIndex = 158;
        this.but5pm.Text = "5 PM";
        this.but5pm.Click += new System.EventHandler(this.but5pm_Click);
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
        this.butDelete.Location = new System.Drawing.Point(21, 412);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 155;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(198, 412);
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
        this.butCancel.Location = new System.Drawing.Point(279, 412);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.but6am);
        this.groupBox2.Controls.Add(this.textAfterTimeOfDay);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.but5pm);
        this.groupBox2.Controls.Add(this.textBeforeTimeOfDay);
        this.groupBox2.Location = new System.Drawing.Point(21, 41);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(282, 75);
        this.groupBox2.TabIndex = 158;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "or Differential Hours";
        //
        // FormTimeCardRuleEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(366, 448);
        this.Controls.Add(this.textOverHoursPerDay);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.listEmp);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormTimeCardRuleEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Time Card Rule Edit";
        this.Load += new System.EventHandler(this.FormTimeCardRuleEdit_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOverHoursPerDay = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAfterTimeOfDay = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.ListBox listEmp = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button but5pm;
    private OpenDental.UI.Button but6am;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBeforeTimeOfDay = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
}


