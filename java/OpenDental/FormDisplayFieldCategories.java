//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:57 PM
//

package OpenDental;

import OpenDental.FormDisplayFieldCategories;
import OpenDental.FormDisplayFields;
import OpenDental.Lan;
import OpenDentBusiness.DisplayFieldCategory;

/**
* 
*/
public class FormDisplayFieldCategories  extends System.Windows.Forms.Form 
{
    private Label label1 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    //private bool changed;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private ListBox listCategory = new ListBox();
    //private List<DisplayField> ListShowing;
    //private List<DisplayField> ListAvailable;
    /**
    * 
    */
    public FormDisplayFieldCategories() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDisplayFieldCategories.class);
        this.label1 = new System.Windows.Forms.Label();
        this.listCategory = new System.Windows.Forms.ListBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(194, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Select a category";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listCategory
        //
        this.listCategory.FormattingEnabled = true;
        this.listCategory.Location = new System.Drawing.Point(23, 34);
        this.listCategory.Name = "listCategory";
        this.listCategory.Size = new System.Drawing.Size(155, 108);
        this.listCategory.TabIndex = 57;
        this.listCategory.DoubleClick += new System.EventHandler(this.listCategory_DoubleClick);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(92, 170);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 56;
        this.butOK.Text = "OK";
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
        this.butCancel.Location = new System.Drawing.Point(173, 170);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormDisplayFieldCategories
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(271, 209);
        this.Controls.Add(this.listCategory);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDisplayFieldCategories";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Display Fields";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormDisplayFields_FormClosing);
        this.Load += new System.EventHandler(this.FormDisplayFields_Load);
        this.ResumeLayout(false);
    }

    private void formDisplayFields_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 1;i < Enum.GetNames(DisplayFieldCategory.class).Length;i++)
        {
            //skip None because user not allowed to select that
            listCategory.Items.Add(Enum.GetNames(DisplayFieldCategory.class)[i]);
        }
        listCategory.SelectedIndex = 0;
    }

    private void listCategory_DoubleClick(Object sender, EventArgs e) throws Exception {
        FormDisplayFields FormF = new FormDisplayFields();
        FormF.category = (DisplayFieldCategory)listCategory.SelectedIndex + 1;
        FormF.ShowDialog();
        Close();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        FormDisplayFields FormF = new FormDisplayFields();
        FormF.category = (DisplayFieldCategory)listCategory.SelectedIndex + 1;
        FormF.ShowDialog();
        Close();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formDisplayFields_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


