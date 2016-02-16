//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:21 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormMountainside;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class FormMountainside  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.CheckBox checkEnabled = new System.Windows.Forms.CheckBox();
    private IContainer components = new IContainer();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProgName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProgDesc = new System.Windows.Forms.TextBox();
    // Required designer variable.
    /**
    * This Program link is new.
    */
    public boolean IsNew = new boolean();
    public Program ProgramCur;
    private List<ProgramProperty> PropertyList = new List<ProgramProperty>();
    //private static Thread thread;
    private TextBox textHL7FolderOut = new TextBox();
    private Label labelHL7FolderOut = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private Label label5 = new Label();
    /**
    * 
    */
    public FormMountainside() throws Exception {
        components = new System.ComponentModel.Container();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMountainside.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.checkEnabled = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textProgName = new System.Windows.Forms.TextBox();
        this.textProgDesc = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textHL7FolderOut = new System.Windows.Forms.TextBox();
        this.labelHL7FolderOut = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(524, 224);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(443, 224);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // checkEnabled
        //
        this.checkEnabled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkEnabled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkEnabled.Location = new System.Drawing.Point(161, 60);
        this.checkEnabled.Name = "checkEnabled";
        this.checkEnabled.Size = new System.Drawing.Size(98, 18);
        this.checkEnabled.TabIndex = 41;
        this.checkEnabled.Text = "Enabled";
        this.checkEnabled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkEnabled.Click += new System.EventHandler(this.checkEnabled_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(58, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(187, 18);
        this.label1.TabIndex = 44;
        this.label1.Text = "Internal Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProgName
        //
        this.textProgName.Location = new System.Drawing.Point(246, 9);
        this.textProgName.Name = "textProgName";
        this.textProgName.ReadOnly = true;
        this.textProgName.Size = new System.Drawing.Size(275, 20);
        this.textProgName.TabIndex = 45;
        //
        // textProgDesc
        //
        this.textProgDesc.Location = new System.Drawing.Point(246, 34);
        this.textProgDesc.Name = "textProgDesc";
        this.textProgDesc.Size = new System.Drawing.Size(275, 20);
        this.textProgDesc.TabIndex = 47;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(57, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(187, 18);
        this.label2.TabIndex = 46;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7FolderOut
        //
        this.textHL7FolderOut.Location = new System.Drawing.Point(234, 48);
        this.textHL7FolderOut.Name = "textHL7FolderOut";
        this.textHL7FolderOut.Size = new System.Drawing.Size(275, 20);
        this.textHL7FolderOut.TabIndex = 51;
        //
        // labelHL7FolderOut
        //
        this.labelHL7FolderOut.Location = new System.Drawing.Point(6, 49);
        this.labelHL7FolderOut.Name = "labelHL7FolderOut";
        this.labelHL7FolderOut.Size = new System.Drawing.Size(226, 18);
        this.labelHL7FolderOut.TabIndex = 50;
        this.labelHL7FolderOut.Text = "Out from Mountainside";
        this.labelHL7FolderOut.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textHL7FolderOut);
        this.groupBox1.Controls.Add(this.labelHL7FolderOut);
        this.groupBox1.Location = new System.Drawing.Point(12, 93);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(538, 86);
        this.groupBox1.TabIndex = 52;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "HL7 Synch Folder";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 19);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(478, 18);
        this.label5.TabIndex = 45;
        this.label5.Text = "Folder locations must be valid on the computer where the HL7 process is running";
        //
        // FormMountainside
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(611, 260);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.textProgDesc);
        this.Controls.Add(this.textProgName);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkEnabled);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMountainside";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Mountainside Setup";
        this.Load += new System.EventHandler(this.FormEClinicalWorks_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProgramLinkEdit_Closing);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEClinicalWorks_Load(Object sender, System.EventArgs e) throws Exception {
        fillForm();
    }

    private void fillForm() throws Exception {
        ProgramProperties.refreshCache();
        PropertyList = ProgramProperties.getListForProgram(ProgramCur.ProgramNum);
        textProgName.Text = ProgramCur.ProgName;
        textProgDesc.Text = ProgramCur.ProgDesc;
        checkEnabled.Checked = ProgramCur.Enabled;
        textHL7FolderOut.Text = PrefC.getString(PrefName.HL7FolderOut);
    }

    private void checkEnabled_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"You will need to restart Open Dental to see the effects.");
    }

    private boolean saveToDb() throws Exception {
        if (StringSupport.equals(textProgDesc.Text, ""))
        {
            MsgBox.show(this,"Description may not be blank.");
            return false;
        }
         
        if (StringSupport.equals(textHL7FolderOut.Text, ""))
        {
            MsgBox.show(this,"HL7 out folder may not be blank.");
            return false;
        }
         
        ProgramCur.ProgDesc = textProgDesc.Text;
        ProgramCur.Enabled = checkEnabled.Checked;
        Programs.update(ProgramCur);
        Prefs.UpdateString(PrefName.HL7FolderOut, textHL7FolderOut.Text);
        DataValid.setInvalid(InvalidType.Programs,InvalidType.Prefs);
        return true;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveToDb())
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formProgramLinkEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


