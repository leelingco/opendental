//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PhoneGraphs;
import OpenDental.FormPhoneGraphEdit;
import OpenDental.Properties.Resources;

public class FormPhoneGraphEdit  extends Form 
{

    private long EmployeeNum = new long();
    public PhoneGraph PhoneGraphCur;
    public FormPhoneGraphEdit(long employeeNum) throws Exception {
        initializeComponent();
        Lan.F(this);
        EmployeeNum = employeeNum;
    }

    private void formPhoneGraphCreate_Load(Object sender, System.EventArgs e) throws Exception {
        if (PhoneGraphCur.getIsNew())
        {
            return ;
        }
         
        textDateEntry.Text = PhoneGraphCur.DateEntry.ToShortDateString();
        checkIsGraphed.Checked = PhoneGraphCur.IsGraphed;
    }

    private void butToday_Click(Object sender, System.EventArgs e) throws Exception {
        textDateEntry.Text = DateTime.Today.ToShortDateString();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (PhoneGraphCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        PhoneGraphs.delete(PhoneGraphCur.PhoneGraphNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDateEntry.Text, "") || !StringSupport.equals(textDateEntry.errorProvider1.GetError(textDateEntry), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        //user brought in an existing entry and may have modified it so get rid of it and recreate it in its entirety
        //EG...
        //user brought in 9/27/13 Checked: TRUE...
        //then changed date to 9/28/13 Checked: FALSE...
        //user has expectation that the 9/27 entry will be gone and new 9/28 entry will be created
        if (!PhoneGraphCur.getIsNew())
        {
            PhoneGraphs.delete(PhoneGraphCur.PhoneGraphNum);
        }
         
        PhoneGraph pg = new PhoneGraph();
        pg.EmployeeNum = EmployeeNum;
        pg.DateEntry = PIn.Date(textDateEntry.Text);
        pg.IsGraphed = checkIsGraphed.Checked;
        PhoneGraphs.insertOrUpdate(pg);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        this.DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPhoneGraphEdit.class);
        this.checkIsGraphed = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butToday = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textDateEntry = new OpenDental.ValidDate();
        this.SuspendLayout();
        //
        // checkIsGraphed
        //
        this.checkIsGraphed.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsGraphed.Location = new System.Drawing.Point(10, 40);
        this.checkIsGraphed.Name = "checkIsGraphed";
        this.checkIsGraphed.Size = new System.Drawing.Size(109, 17);
        this.checkIsGraphed.TabIndex = 0;
        this.checkIsGraphed.Text = "Is Graphed";
        this.checkIsGraphed.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsGraphed.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 15);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(86, 18);
        this.label1.TabIndex = 9;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butToday.Location = new System.Drawing.Point(205, 14);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(63, 22);
        this.butToday.TabIndex = 78;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 67);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 15;
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
        this.butOK.Location = new System.Drawing.Point(146, 67);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 13;
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
        this.butCancel.Location = new System.Drawing.Point(230, 67);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 14;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(102, 15);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.Size = new System.Drawing.Size(82, 20);
        this.textDateEntry.TabIndex = 10;
        //
        // FormPhoneGraphEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(317, 103);
        this.Controls.Add(this.butToday);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkIsGraphed);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPhoneGraphEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Phone Graph Edit";
        this.Load += new System.EventHandler(this.FormPhoneGraphCreate_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.CheckBox checkIsGraphed = new System.Windows.Forms.CheckBox();
    private OpenDental.ValidDate textDateEntry;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butToday;
}


