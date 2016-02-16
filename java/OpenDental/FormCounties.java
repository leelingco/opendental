//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:54 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCounties;
import OpenDental.FormCountyEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Counties;
import OpenDentBusiness.County;

/**
* Summary description for FormBasicTemplate.
*/
public class FormCounties  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listCounties = new System.Windows.Forms.ListBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private County[] CountiesList = new County[]();
    /**
    * 
    */
    public FormCounties() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCounties.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listCounties = new System.Windows.Forms.ListBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(338, 605);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(86, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(338, 564);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(86, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listCounties
        //
        this.listCounties.Location = new System.Drawing.Point(12, 30);
        this.listCounties.Name = "listCounties";
        this.listCounties.Size = new System.Drawing.Size(298, 602);
        this.listCounties.TabIndex = 2;
        this.listCounties.DoubleClick += new System.EventHandler(this.listCounties_DoubleClick);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(338, 461);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(86, 26);
        this.butDelete.TabIndex = 72;
        this.butDelete.Text = "&Remove";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(338, 423);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(86, 26);
        this.butAdd.TabIndex = 71;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(11, 5);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(437, 19);
        this.label1.TabIndex = 73;
        this.label1.Text = "This is a list of Counties  for Public Health";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormCounties
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(464, 646);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listCounties);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormCounties";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Counties";
        this.Load += new System.EventHandler(this.FormCounties_Load);
        this.ResumeLayout(false);
    }

    private void formCounties_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        CountiesList = Counties.refresh();
        listCounties.Items.Clear();
        String s = "";
        for (int i = 0;i < CountiesList.Length;i++)
        {
            s = CountiesList[i].CountyName;
            if (!StringSupport.equals(CountiesList[i].CountyCode, ""))
            {
                s += ", " + CountiesList[i].CountyCode;
            }
             
            listCounties.Items.Add(s);
        }
    }

    private void listCounties_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listCounties.SelectedIndex == -1)
        {
            return ;
        }
         
        FormCountyEdit FormSE = new FormCountyEdit();
        FormSE.CountyCur = CountiesList[listCounties.SelectedIndex];
        FormSE.ShowDialog();
        if (FormSE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillList();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormCountyEdit FormSE = new FormCountyEdit();
        FormSE.IsNew = true;
        FormSE.CountyCur = new County();
        FormSE.ShowDialog();
        if (FormSE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillList();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCounties.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        County CountyCur = CountiesList[listCounties.SelectedIndex];
        String usedBy = Counties.usedBy(CountyCur.CountyName);
        if (!StringSupport.equals(usedBy, ""))
        {
            MessageBox.Show(Lan.g(this,"Cannot delete County because it is already in use by the following patients: \r") + usedBy);
            return ;
        }
         
        Counties.delete(CountyCur);
        fillList();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


