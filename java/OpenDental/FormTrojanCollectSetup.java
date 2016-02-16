//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:56 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormTrojanCollectSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTrojanCollectSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private TextBox textExportFolder = new TextBox();
    private Label label2 = new Label();
    private ComboBox comboBillType = new ComboBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormTrojanCollectSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTrojanCollectSetup.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textExportFolder = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboBillType = new System.Windows.Forms.ComboBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(477, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Export Folder.  Should be a shared network folder, and must be the same for all c" + "omputers.";
        //
        // textExportFolder
        //
        this.textExportFolder.Location = new System.Drawing.Point(15, 40);
        this.textExportFolder.Name = "textExportFolder";
        this.textExportFolder.Size = new System.Drawing.Size(406, 20);
        this.textExportFolder.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 91);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(294, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "Billing type for patients sent to collections";
        //
        // comboBillType
        //
        this.comboBillType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboBillType.FormattingEnabled = true;
        this.comboBillType.Location = new System.Drawing.Point(15, 110);
        this.comboBillType.MaxDropDownItems = 50;
        this.comboBillType.Name = "comboBillType";
        this.comboBillType.Size = new System.Drawing.Size(198, 21);
        this.comboBillType.TabIndex = 7;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(264, 166);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(364, 166);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormTrojanCollectSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(491, 217);
        this.Controls.Add(this.comboBillType);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textExportFolder);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTrojanCollectSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Trojan Express Collect";
        this.Load += new System.EventHandler(this.FormTrojanCollectSetup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTrojanCollectSetup_Load(Object sender, EventArgs e) throws Exception {
        textExportFolder.Text = PrefC.getString(PrefName.TrojanExpressCollectPath);
        long billtype = PrefC.getLong(PrefName.TrojanExpressCollectBillingType);
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            comboBillType.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].DefNum == billtype)
            {
                comboBillType.SelectedIndex = i;
            }
             
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        if (!Directory.Exists(textExportFolder.Text))
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Export folder does not exist.");
            return ;
        }
         
        if (comboBillType.SelectedIndex == -1)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Please select a billing type.");
            return ;
        }
         
        long billtype = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][comboBillType.SelectedIndex].DefNum;
        if (Prefs.UpdateString(PrefName.TrojanExpressCollectPath, textExportFolder.Text) | Prefs.updateLong(PrefName.TrojanExpressCollectBillingType,billtype))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


