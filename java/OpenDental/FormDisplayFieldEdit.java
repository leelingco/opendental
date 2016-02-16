//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:57 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDisplayFieldEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.DisplayField;

/**
* 
*/
public class FormDisplayFieldEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private TextBox textInternalName = new TextBox();
    private TextBox textDescription = new TextBox();
    private Label label2 = new Label();
    private Label label3 = new Label();
    private OpenDental.ValidNum textWidth;
    private TextBox textWidthMin = new TextBox();
    private Label label4 = new Label();
    private Label label5 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DisplayField FieldCur;
    private Font headerFont = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Bold);
    /**
    * 
    */
    public FormDisplayFieldEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDisplayFieldEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textInternalName = new System.Windows.Forms.TextBox();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textWidthMin = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textWidth = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(-1, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(140, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Internal Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInternalName
        //
        this.textInternalName.Location = new System.Drawing.Point(141, 15);
        this.textInternalName.Name = "textInternalName";
        this.textInternalName.ReadOnly = true;
        this.textInternalName.Size = new System.Drawing.Size(348, 20);
        this.textInternalName.TabIndex = 3;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(141, 41);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(348, 20);
        this.textDescription.TabIndex = 5;
        this.textDescription.TextChanged += new System.EventHandler(this.textDescription_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(-1, 42);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(140, 17);
        this.label2.TabIndex = 4;
        this.label2.Text = "New Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(-1, 94);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(140, 17);
        this.label3.TabIndex = 6;
        this.label3.Text = "Column Width";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textWidthMin
        //
        this.textWidthMin.Location = new System.Drawing.Point(141, 67);
        this.textWidthMin.Name = "textWidthMin";
        this.textWidthMin.ReadOnly = true;
        this.textWidthMin.Size = new System.Drawing.Size(71, 20);
        this.textWidthMin.TabIndex = 9;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(2, 68);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(137, 17);
        this.label4.TabIndex = 8;
        this.label4.Text = "Minimum Width";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(215, 68);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(175, 17);
        this.label5.TabIndex = 10;
        this.label5.Text = "(based on text above)";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textWidth
        //
        this.textWidth.Location = new System.Drawing.Point(141, 93);
        this.textWidth.setMaxVal(2000);
        this.textWidth.setMinVal(1);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(71, 20);
        this.textWidth.TabIndex = 7;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(414, 130);
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
        this.butCancel.Location = new System.Drawing.Point(414, 171);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormDisplayFieldEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(541, 222);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textWidthMin);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textInternalName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDisplayFieldEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Display Field";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormDisplayFieldEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormDisplayFieldEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDisplayFieldEdit_Load(Object sender, EventArgs e) throws Exception {
        textInternalName.Text = FieldCur.InternalName;
        textDescription.Text = FieldCur.Description;
        textWidth.Text = FieldCur.ColumnWidth.ToString();
        fillWidth();
    }

    private void fillWidth() throws Exception {
        Graphics g = this.CreateGraphics();
        int width = new int();
        if (StringSupport.equals(textDescription.Text, ""))
        {
            width = (int)g.MeasureString(textInternalName.Text, headerFont).Width;
        }
        else
        {
            width = (int)g.MeasureString(textDescription.Text, headerFont).Width;
        } 
        textWidthMin.Text = width.ToString();
        g.Dispose();
    }

    private void textDescription_TextChanged(Object sender, EventArgs e) throws Exception {
        fillWidth();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textWidth.errorProvider1.GetError(textWidth), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        FieldCur.Description = textDescription.Text;
        FieldCur.ColumnWidth = PIn.Int(textWidth.Text);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formDisplayFieldEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


