//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:50 PM
//

package OpenDental;

import OpenDental.FormPatientSelect;
import OpenDental.FormSubscriberSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Family;

/**
* Only used when adding an insurance plan from ContrFamily.  Lets user select the subscriber from
*/
public class FormSubscriberSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listPats = new System.Windows.Forms.ListBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butMore;
    private Family FamCur;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * When the form closes with OK, this will contain the patient num selected.
    */
    public long SelectedPatNum = new long();
    /**
    * 
    */
    public FormSubscriberSelect(Family famCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        FamCur = famCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSubscriberSelect.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listPats = new System.Windows.Forms.ListBox();
        this.butMore = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(277, 241);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(92, 26);
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
        this.butOK.Location = new System.Drawing.Point(277, 200);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(92, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listPats
        //
        this.listPats.Location = new System.Drawing.Point(32, 79);
        this.listPats.Name = "listPats";
        this.listPats.Size = new System.Drawing.Size(187, 186);
        this.listPats.TabIndex = 2;
        this.listPats.DoubleClick += new System.EventHandler(this.listPats_DoubleClick);
        //
        // butMore
        //
        this.butMore.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMore.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butMore.setAutosize(true);
        this.butMore.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMore.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMore.setCornerRadius(4F);
        this.butMore.Location = new System.Drawing.Point(277, 79);
        this.butMore.Name = "butMore";
        this.butMore.Size = new System.Drawing.Size(92, 26);
        this.butMore.TabIndex = 3;
        this.butMore.Text = "More Patients";
        this.butMore.Click += new System.EventHandler(this.butMore_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(33, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(287, 38);
        this.label1.TabIndex = 4;
        this.label1.Text = "If subscriber has not been entered, cancel and add them before continuing.";
        //
        // FormSubscriberSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(401, 295);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butMore);
        this.Controls.Add(this.listPats);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSubscriberSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Subscriber";
        this.Load += new System.EventHandler(this.FormSubscriberSelect_Load);
        this.ResumeLayout(false);
    }

    private void formSubscriberSelect_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            listPats.Items.Add(FamCur.ListPats[i].GetNameFL());
        }
    }

    private void listPats_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listPats.SelectedIndex == -1)
        {
            return ;
        }
         
        SelectedPatNum = FamCur.ListPats[listPats.SelectedIndex].PatNum;
        DialogResult = DialogResult.OK;
    }

    private void butMore_Click(Object sender, System.EventArgs e) throws Exception {
        FormPatientSelect FormP = new FormPatientSelect();
        FormP.SelectionModeOnly = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SelectedPatNum = FormP.SelectedPatNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listPats.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please pick a patient first.");
            return ;
        }
         
        SelectedPatNum = FamCur.ListPats[listPats.SelectedIndex].PatNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


