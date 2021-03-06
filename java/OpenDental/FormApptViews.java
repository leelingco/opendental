//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormApptViewEdit;
import OpenDental.FormApptViews;
import OpenDental.FormProcApptColors;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewC;
import OpenDentBusiness.ApptViews;
import OpenDentBusiness.Cache;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptViews  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.ListBox listViews = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioTen = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioFifteen = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.CheckBox checkTwoRows = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butCancel;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private RadioButton radioFive = new RadioButton();
    private OpenDental.UI.Button butProcColors;
    private boolean viewChanged = new boolean();
    /**
    * 
    */
    public FormApptViews() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptViews.class);
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.listViews = new System.Windows.Forms.ListBox();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioFive = new System.Windows.Forms.RadioButton();
        this.radioFifteen = new System.Windows.Forms.RadioButton();
        this.radioTen = new System.Windows.Forms.RadioButton();
        this.checkTwoRows = new System.Windows.Forms.CheckBox();
        this.butProcColors = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(447, 433);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(57, 32);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(158, 23);
        this.label1.TabIndex = 1;
        this.label1.Text = "Views";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listViews
        //
        this.listViews.Location = new System.Drawing.Point(56, 60);
        this.listViews.Name = "listViews";
        this.listViews.Size = new System.Drawing.Size(183, 329);
        this.listViews.TabIndex = 2;
        this.listViews.DoubleClick += new System.EventHandler(this.listViews_DoubleClick);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(151, 437);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(89, 24);
        this.butDown.TabIndex = 38;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(151, 399);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(89, 24);
        this.butUp.TabIndex = 39;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(55, 399);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(89, 24);
        this.butAdd.TabIndex = 36;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioFive);
        this.groupBox1.Controls.Add(this.radioFifteen);
        this.groupBox1.Controls.Add(this.radioTen);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(279, 54);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(169, 82);
        this.groupBox1.TabIndex = 40;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Time Increments";
        //
        // radioFive
        //
        this.radioFive.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioFive.Location = new System.Drawing.Point(23, 19);
        this.radioFive.Name = "radioFive";
        this.radioFive.Size = new System.Drawing.Size(100, 18);
        this.radioFive.TabIndex = 2;
        this.radioFive.Text = "5 Min";
        //
        // radioFifteen
        //
        this.radioFifteen.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioFifteen.Location = new System.Drawing.Point(23, 57);
        this.radioFifteen.Name = "radioFifteen";
        this.radioFifteen.Size = new System.Drawing.Size(100, 18);
        this.radioFifteen.TabIndex = 1;
        this.radioFifteen.Text = "15 Min";
        //
        // radioTen
        //
        this.radioTen.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioTen.Location = new System.Drawing.Point(23, 38);
        this.radioTen.Name = "radioTen";
        this.radioTen.Size = new System.Drawing.Size(100, 18);
        this.radioTen.TabIndex = 0;
        this.radioTen.Text = "10 Min";
        //
        // checkTwoRows
        //
        this.checkTwoRows.Location = new System.Drawing.Point(0, 0);
        this.checkTwoRows.Name = "checkTwoRows";
        this.checkTwoRows.Size = new System.Drawing.Size(104, 24);
        this.checkTwoRows.TabIndex = 0;
        //
        // butProcColors
        //
        this.butProcColors.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProcColors.setAutosize(true);
        this.butProcColors.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProcColors.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProcColors.setCornerRadius(4F);
        this.butProcColors.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butProcColors.Location = new System.Drawing.Point(279, 159);
        this.butProcColors.Name = "butProcColors";
        this.butProcColors.Size = new System.Drawing.Size(82, 24);
        this.butProcColors.TabIndex = 41;
        this.butProcColors.Text = "Proc Colors";
        this.butProcColors.Click += new System.EventHandler(this.butProcColors_Click);
        //
        // FormApptViews
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(546, 485);
        this.Controls.Add(this.butProcColors);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listViews);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptViews";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment Views";
        this.Load += new System.EventHandler(this.FormApptViews_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormApptViews_FormClosing);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formApptViews_Load(Object sender, System.EventArgs e) throws Exception {
        fillViewList();
        if (PrefC.getInt(PrefName.AppointmentTimeIncrement) == 5)
        {
            radioFive.Checked = true;
        }
        else if (PrefC.getInt(PrefName.AppointmentTimeIncrement) == 10)
        {
            radioTen.Checked = true;
        }
        else
        {
            radioFifteen.Checked = true;
        }  
    }

    private void fillViewList() throws Exception {
        Cache.refresh(InvalidType.Views);
        listViews.Items.Clear();
        String F = new String();
        for (int i = 0;i < ApptViewC.getList().Length;i++)
        {
            if (i < 12)
                F = "F" + (i + 1).ToString() + "-";
            else
                F = ""; 
            listViews.Items.Add(F + ApptViewC.getList()[i].Description);
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ApptView ApptViewCur = new ApptView();
        ApptViewCur.ItemOrder = ApptViewC.getList().Length;
        ApptViews.insert(ApptViewCur);
        //this also gets the primary key
        FormApptViewEdit FormAVE = new FormApptViewEdit();
        FormAVE.ApptViewCur = ApptViewCur;
        FormAVE.IsNew = true;
        FormAVE.ShowDialog();
        if (FormAVE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        viewChanged = true;
        fillViewList();
        listViews.SelectedIndex = listViews.Items.Count - 1;
    }

    //this works even if no items
    private void listViews_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listViews.SelectedIndex == -1)
        {
            return ;
        }
         
        int selected = listViews.SelectedIndex;
        ApptView ApptViewCur = ApptViewC.getList()[listViews.SelectedIndex];
        FormApptViewEdit FormAVE = new FormApptViewEdit();
        FormAVE.ApptViewCur = ApptViewCur;
        FormAVE.ShowDialog();
        if (FormAVE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        viewChanged = true;
        fillViewList();
        if (selected < listViews.Items.Count)
        {
            listViews.SelectedIndex = selected;
        }
         
    }

    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        if (listViews.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        if (listViews.SelectedIndex == 0)
        {
            return ;
        }
         
        //can't go up any more
        int selected = listViews.SelectedIndex;
        //it will flip flop with the one above it
        ApptView ApptViewCur = ApptViewC.getList()[listViews.SelectedIndex];
        ApptViewCur.ItemOrder = ApptViewCur.ItemOrder - 1;
        ApptViews.update(ApptViewCur);
        //now the other
        ApptViewCur = ApptViewC.getList()[listViews.SelectedIndex - 1];
        ApptViewCur.ItemOrder = ApptViewCur.ItemOrder + 1;
        ApptViews.update(ApptViewCur);
        viewChanged = true;
        fillViewList();
        listViews.SelectedIndex = selected - 1;
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        if (listViews.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        if (listViews.SelectedIndex == ApptViewC.getList().Length - 1)
        {
            return ;
        }
         
        //can't go down any more
        int selected = listViews.SelectedIndex;
        //it will flip flop with the one below it
        ApptView ApptViewCur = ApptViewC.getList()[listViews.SelectedIndex];
        ApptViewCur.ItemOrder = ApptViewCur.ItemOrder + 1;
        ApptViews.update(ApptViewCur);
        //now the other
        ApptViewCur = ApptViewC.getList()[listViews.SelectedIndex + 1];
        ApptViewCur.ItemOrder = ApptViewCur.ItemOrder - 1;
        ApptViews.update(ApptViewCur);
        viewChanged = true;
        fillViewList();
        listViews.SelectedIndex = selected + 1;
    }

    private void butProcColors_Click(Object sender, EventArgs e) throws Exception {
        FormProcApptColors formProcColors = new FormProcApptColors();
        formProcColors.ShowDialog();
        DialogResult = DialogResult.None;
    }

    //This is required to prevent FormApptViews from closing.
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formApptViews_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        int newIncrement = 15;
        if (radioFive.Checked)
        {
            newIncrement = 5;
        }
         
        if (radioTen.Checked)
        {
            newIncrement = 10;
        }
         
        if (Prefs.updateInt(PrefName.AppointmentTimeIncrement,newIncrement))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        if (viewChanged)
        {
            DataValid.setInvalid(InvalidType.Views);
        }
         
    }

}


