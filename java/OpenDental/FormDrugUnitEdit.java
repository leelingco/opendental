//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DrugUnit;
import OpenDentBusiness.DrugUnits;
import OpenDental.Properties.Resources;

public class FormDrugUnitEdit  extends Form 
{

    public DrugUnit DrugUnitCur;
    public boolean IsNew = new boolean();
    public FormDrugUnitEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formDrugUnitEdit_Load(Object sender, EventArgs e) throws Exception {
        textUnitIdentifier.Text = DrugUnitCur.UnitIdentifier;
        textUnitText.Text = DrugUnitCur.UnitText;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        try
        {
            DrugUnits.delete(DrugUnitCur.DrugUnitNum);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textUnitIdentifier.Text, "") || StringSupport.equals(textUnitText.Text, ""))
        {
            MsgBox.show(this,"Bank fields are not allowed.");
            return ;
        }
         
        DrugUnitCur.UnitIdentifier = textUnitIdentifier.Text;
        DrugUnitCur.UnitText = textUnitText.Text;
        if (IsNew)
        {
            for (int i = 0;i < DrugUnits.getListt().Count;i++)
            {
                if (DrugUnits.getListt()[i].UnitIdentifier == textUnitIdentifier.Text)
                {
                    MsgBox.show(this,"Unit with this identifier already exists.");
                    return ;
                }
                 
            }
            DrugUnits.insert(DrugUnitCur);
        }
        else
        {
            DrugUnits.update(DrugUnitCur);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.textUnitText = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textUnitIdentifier = new System.Windows.Forms.TextBox();
        this.labelCVXCode = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(185, 98);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(266, 98);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(26, 98);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textUnitText
        //
        this.textUnitText.Location = new System.Drawing.Point(113, 53);
        this.textUnitText.Name = "textUnitText";
        this.textUnitText.Size = new System.Drawing.Size(228, 20);
        this.textUnitText.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 52);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(88, 20);
        this.label1.TabIndex = 112;
        this.label1.Text = "Unit Text";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUnitIdentifier
        //
        this.textUnitIdentifier.Location = new System.Drawing.Point(113, 27);
        this.textUnitIdentifier.Name = "textUnitIdentifier";
        this.textUnitIdentifier.Size = new System.Drawing.Size(77, 20);
        this.textUnitIdentifier.TabIndex = 0;
        //
        // labelCVXCode
        //
        this.labelCVXCode.Location = new System.Drawing.Point(23, 26);
        this.labelCVXCode.Name = "labelCVXCode";
        this.labelCVXCode.Size = new System.Drawing.Size(88, 20);
        this.labelCVXCode.TabIndex = 110;
        this.labelCVXCode.Text = "Unit Identifier";
        this.labelCVXCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormDrugUnitEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(366, 149);
        this.Controls.Add(this.textUnitText);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textUnitIdentifier);
        this.Controls.Add(this.labelCVXCode);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormDrugUnitEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Drug Unit Edit";
        this.Load += new System.EventHandler(this.FormDrugUnitEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textUnitText = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUnitIdentifier = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelCVXCode = new System.Windows.Forms.Label();
}


