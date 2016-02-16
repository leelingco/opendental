//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuickPasteCat;
import OpenDental.Lan;
import OpenDentBusiness.QuickPasteCat;
import OpenDentBusiness.QuickPasteCats;

/**
* Summary description for FormBasicTemplate.
*/
public class FormQuickPasteCat  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private QuickPasteCat QuickCat;
    /**
    * 
    */
    public FormQuickPasteCat(QuickPasteCat quickCat) throws Exception {
        QuickCat = quickCat;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuickPasteCat.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(497, 405);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
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
        this.butOK.Location = new System.Drawing.Point(497, 364);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(125, 28);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(357, 20);
        this.textDescription.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 30);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(117, 15);
        this.label1.TabIndex = 3;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listType
        //
        this.listType.Location = new System.Drawing.Point(126, 61);
        this.listType.Name = "listType";
        this.listType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listType.Size = new System.Drawing.Size(120, 368);
        this.listType.TabIndex = 4;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(7, 63);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(117, 15);
        this.label2.TabIndex = 5;
        this.label2.Text = "Default for Types";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormQuickPasteCat
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(588, 445);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQuickPasteCat";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Quick Paste Category";
        this.Load += new System.EventHandler(this.FormQuickPasteCat_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formQuickPasteCat_Load(Object sender, System.EventArgs e) throws Exception {
        listType.Items.Clear();
        String[] types = new String[]();
        if (QuickCat.DefaultForTypes == null || StringSupport.equals(QuickCat.DefaultForTypes, ""))
        {
            types = new String[0];
        }
        else
        {
            types = QuickCat.DefaultForTypes.Split(',');
        } 
        for (int i = 0;i < Enum.GetNames(OpenDentBusiness.QuickPasteType.class).Length;i++)
        {
            listType.Items.Add(Lan.g(this, Enum.GetNames(OpenDentBusiness.QuickPasteType.class)[i]));
            for (int j = 0;j < types.Length;j++)
            {
                if (i.ToString() == types[j])
                {
                    listType.SetSelected(i, true);
                }
                 
            }
        }
        textDescription.Text = QuickCat.Description;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        QuickCat.Description = textDescription.Text;
        QuickCat.DefaultForTypes = "";
        for (int i = 0;i < listType.SelectedIndices.Count;i++)
        {
            if (i > 0)
            {
                QuickCat.DefaultForTypes += ",";
            }
             
            QuickCat.DefaultForTypes += listType.SelectedIndices[i].ToString();
        }
        QuickPasteCats.update(QuickCat);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


