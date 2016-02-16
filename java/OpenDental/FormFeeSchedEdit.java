//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:09 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormFeeSchedEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.ProviderC;

/**
* Summary description for FormBasicTemplate.
*/
public class FormFeeSchedEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Label label2 = new Label();
    private ListBox listType = new ListBox();
    private CheckBox checkIsHidden = new CheckBox();
    public FeeSched FeeSchedCur;
    /**
    * 
    */
    public FormFeeSchedEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeeSchedEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
        this.checkIsHidden = new System.Windows.Forms.CheckBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(160, 20);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(291, 20);
        this.textDescription.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(9, 46);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(148, 17);
        this.label2.TabIndex = 11;
        this.label2.Text = "Type";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listType
        //
        this.listType.FormattingEnabled = true;
        this.listType.Location = new System.Drawing.Point(160, 46);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(120, 43);
        this.listType.TabIndex = 12;
        //
        // checkIsHidden
        //
        this.checkIsHidden.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.Location = new System.Drawing.Point(70, 95);
        this.checkIsHidden.Name = "checkIsHidden";
        this.checkIsHidden.Size = new System.Drawing.Size(104, 19);
        this.checkIsHidden.TabIndex = 13;
        this.checkIsHidden.Text = "Hidden";
        this.checkIsHidden.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHidden.UseVisualStyleBackColor = true;
        this.checkIsHidden.Click += new System.EventHandler(this.checkIsHidden_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(309, 170);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 9;
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
        this.butCancel.Location = new System.Drawing.Point(400, 170);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormFeeSchedEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(501, 214);
        this.Controls.Add(this.checkIsHidden);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFeeSchedEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Fee Schedule";
        this.Load += new System.EventHandler(this.FormFeeSchedEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formFeeSchedEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = FeeSchedCur.Description;
        if (!FeeSchedCur.getIsNew())
        {
            listType.Enabled = false;
        }
         
        for (int i = 0;i < Enum.GetNames(FeeScheduleType.class).Length;i++)
        {
            listType.Items.Add(Enum.GetNames(FeeScheduleType.class)[i]);
            if (((Enum)FeeSchedCur.FeeSchedType).ordinal() == i)
            {
                listType.SetSelected(i, true);
            }
             
        }
        checkIsHidden.Checked = FeeSchedCur.IsHidden;
    }

    private void checkIsHidden_Click(Object sender, EventArgs e) throws Exception {
        //Don't allow fees to be hidden if they are in use by a provider.
        if (!checkIsHidden.Checked)
        {
            return ;
        }
         
        //Unhiding a fee. OK.
        if (FeeSchedCur.FeeSchedType != FeeScheduleType.Normal)
        {
            return ;
        }
         
        //Not Normal fee. Not in use by a provider.
        String providersUsingFee = "";
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (FeeSchedCur.FeeSchedNum == ProviderC.getListShort()[i].FeeSched)
            {
                if (!StringSupport.equals(providersUsingFee, ""))
                {
                    //There is a name before this on the list
                    providersUsingFee += ", ";
                }
                 
                providersUsingFee += ProviderC.getListShort()[i].Abbr;
            }
             
        }
        if (!StringSupport.equals(providersUsingFee, ""))
        {
            MessageBox.Show(Lan.g(this,"Cannot hide. Fee schedule is currently in use by the following providers:\r\n") + providersUsingFee);
            checkIsHidden.Checked = false;
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Description cannot be blank.");
            return ;
        }
         
        FeeSchedCur.Description = textDescription.Text;
        FeeSchedCur.FeeSchedType = (FeeScheduleType)listType.SelectedIndex;
        FeeSchedCur.IsHidden = checkIsHidden.Checked;
        try
        {
            if (FeeSchedCur.getIsNew())
            {
                FeeScheds.insert(FeeSchedCur);
            }
            else
            {
                FeeScheds.update(FeeSchedCur);
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


