//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:17 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormInsSpanEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpans;

/**
* 
*/
public class FormInsSpanEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.TextBox textFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textTo = new System.Windows.Forms.TextBox();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butDelete;
    private CovSpan CovSpanCur;
    /**
    * 
    */
    public FormInsSpanEdit(CovSpan covSpanCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        CovSpanCur = covSpanCur.copy();
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsSpanEdit.class);
        this.textTo = new System.Windows.Forms.TextBox();
        this.textFrom = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textTo
        //
        this.textTo.Location = new System.Drawing.Point(131, 34);
        this.textTo.Name = "textTo";
        this.textTo.Size = new System.Drawing.Size(101, 20);
        this.textTo.TabIndex = 1;
        //
        // textFrom
        //
        this.textFrom.Location = new System.Drawing.Point(15, 34);
        this.textFrom.Name = "textFrom";
        this.textFrom.Size = new System.Drawing.Size(100, 20);
        this.textFrom.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(131, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 16);
        this.label2.TabIndex = 8;
        this.label2.Text = "To ADA";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(13, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 16);
        this.label3.TabIndex = 9;
        this.label3.Text = "From ADA";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(246, 88);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(246, 124);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 124);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(86, 26);
        this.butDelete.TabIndex = 11;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormInsSpanEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(348, 168);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textFrom);
        this.Controls.Add(this.textTo);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsSpanEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Ins Coverage Span";
        this.Load += new System.EventHandler(this.FormInsSpanEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsSpanEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textFrom.Text = CovSpanCur.FromCode;
        textTo.Text = CovSpanCur.ToCode;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            //if not match to D****
            if (!Regex.IsMatch(textFrom.Text, "^D\\w{4}$") || !Regex.IsMatch(textTo.Text, "^D\\w{4}$"))
            {
                if (!MsgBox.show(this,true,"One of the codes is not a standard ADA code.  Use anyway?"))
                {
                    return ;
                }
                 
            }
             
        }
         
        CovSpanCur.FromCode = textFrom.Text;
        CovSpanCur.ToCode = textTo.Text;
        try
        {
            if (IsNew)
            {
                CovSpans.insert(CovSpanCur);
            }
            else
            {
                CovSpans.update(CovSpanCur);
            } 
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            CovSpans.delete(CovSpanCur);
            DialogResult = DialogResult.OK;
        } 
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


