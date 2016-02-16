//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:31 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormProcApptColorEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.ProcApptColors;

/**
* Summary description for FormBasicTemplate.
*/
public class FormProcApptColorEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private ColorDialog colorDialog1 = new ColorDialog();
    private TextBox textCodeRange = new TextBox();
    private Label label2 = new Label();
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butChange;
    private Panel panelColor = new Panel();
    private Label labelBeforeTime = new Label();
    public ProcApptColor ProcApptColorCur;
    private CheckBox checkPrevDate = new CheckBox();
    public ApptViewItem ApptVItem;
    /**
    * 
    */
    public FormProcApptColorEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcApptColorEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.textCodeRange = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.panelColor = new System.Windows.Forms.Panel();
        this.labelBeforeTime = new System.Windows.Forms.Label();
        this.checkPrevDate = new System.Windows.Forms.CheckBox();
        this.butChange = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(51, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(83, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Code Range";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCodeRange
        //
        this.textCodeRange.Location = new System.Drawing.Point(136, 20);
        this.textCodeRange.Name = "textCodeRange";
        this.textCodeRange.Size = new System.Drawing.Size(200, 20);
        this.textCodeRange.TabIndex = 1;
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(140, 42);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(92, 13);
        this.label2.TabIndex = 16;
        this.label2.Text = "Ex: D1050-D1060";
        //
        // panelColor
        //
        this.panelColor.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
        this.panelColor.Location = new System.Drawing.Point(136, 64);
        this.panelColor.Name = "panelColor";
        this.panelColor.Size = new System.Drawing.Size(24, 24);
        this.panelColor.TabIndex = 127;
        //
        // labelBeforeTime
        //
        this.labelBeforeTime.Location = new System.Drawing.Point(54, 65);
        this.labelBeforeTime.Name = "labelBeforeTime";
        this.labelBeforeTime.Size = new System.Drawing.Size(80, 20);
        this.labelBeforeTime.TabIndex = 126;
        this.labelBeforeTime.Text = "Text Color";
        this.labelBeforeTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkPrevDate
        //
        this.checkPrevDate.AutoSize = true;
        this.checkPrevDate.Location = new System.Drawing.Point(136, 102);
        this.checkPrevDate.Name = "checkPrevDate";
        this.checkPrevDate.Size = new System.Drawing.Size(120, 17);
        this.checkPrevDate.TabIndex = 3;
        this.checkPrevDate.Text = "Show previous date";
        this.checkPrevDate.UseVisualStyleBackColor = true;
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(170, 64);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(75, 24);
        this.butChange.TabIndex = 2;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(20, 162);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(92, 24);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "&Delete";
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
        this.butOK.Location = new System.Drawing.Point(335, 131);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(335, 162);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormProcApptColorEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(433, 208);
        this.Controls.Add(this.checkPrevDate);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.panelColor);
        this.Controls.Add(this.labelBeforeTime);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textCodeRange);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcApptColorEdit";
        this.Padding = new System.Windows.Forms.Padding(3);
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Proc Appt Color";
        this.Load += new System.EventHandler(this.FormProcApptColorEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProcApptColorEdit_Load(Object sender, System.EventArgs e) throws Exception {
        checkPrevDate.Checked = ProcApptColorCur.ShowPreviousDate;
        textCodeRange.Text = ProcApptColorCur.CodeRange;
        if (!ProcApptColorCur.getIsNew())
        {
            panelColor.BackColor = ProcApptColorCur.ColorText;
        }
        else
        {
            panelColor.BackColor = System.Drawing.Color.Black;
        } 
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog colorDlg = new ColorDialog();
        colorDlg.AllowFullOpen = true;
        colorDlg.AnyColor = true;
        colorDlg.SolidColorOnly = false;
        colorDlg.Color = panelColor.BackColor;
        if (colorDlg.ShowDialog() == DialogResult.OK)
        {
            panelColor.BackColor = colorDlg.Color;
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (ProcApptColorCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete this procedure color range?"))
        {
            return ;
        }
         
        try
        {
            ProcApptColors.delete(ProcApptColorCur.ProcApptColorNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textCodeRange.Text.Trim(), ""))
        {
            MessageBox.Show(Lan.g(this,"Code range cannot be blank."));
            return ;
        }
         
        ProcApptColorCur.ColorText = panelColor.BackColor;
        ProcApptColorCur.CodeRange = textCodeRange.Text;
        ProcApptColorCur.ShowPreviousDate = checkPrevDate.Checked;
        try
        {
            if (ProcApptColorCur.getIsNew())
            {
                ProcApptColors.insert(ProcApptColorCur);
            }
            else
            {
                ProcApptColors.update(ProcApptColorCur);
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

}


