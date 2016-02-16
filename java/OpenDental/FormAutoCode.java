//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormAutoCode;
import OpenDental.FormAutoCodeEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AutoCode;
import OpenDentBusiness.AutoCodeC;
import OpenDentBusiness.AutoCodes;
import OpenDentBusiness.InvalidType;

/**
* 
*/
public class FormAutoCode  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListBox listAutoCodes = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private System.ComponentModel.Container components = null;
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormAutoCode() throws Exception {
        initializeComponent();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoCode.class);
        this.listAutoCodes = new System.Windows.Forms.ListBox();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listAutoCodes
        //
        this.listAutoCodes.Location = new System.Drawing.Point(28, 26);
        this.listAutoCodes.Name = "listAutoCodes";
        this.listAutoCodes.Size = new System.Drawing.Size(178, 316);
        this.listAutoCodes.TabIndex = 0;
        this.listAutoCodes.DoubleClick += new System.EventHandler(this.listAutoCodes_DoubleClick);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(230, 390);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(80, 26);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butAdd.Location = new System.Drawing.Point(26, 354);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(90, 26);
        this.butAdd.TabIndex = 5;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butDelete.Location = new System.Drawing.Point(118, 354);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(90, 26);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormAutoCode
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(338, 430);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.listAutoCodes);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutoCode";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Auto Codes";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormAutoCode_Closing);
        this.Load += new System.EventHandler(this.FormAutoCode_Load);
        this.ResumeLayout(false);
    }

    private void formAutoCode_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        AutoCodes.refreshCache();
        listAutoCodes.Items.Clear();
        for (int i = 0;i < AutoCodeC.getList().Length;i++)
        {
            if (AutoCodeC.getList()[i].IsHidden)
            {
                listAutoCodes.Items.Add(AutoCodeC.getList()[i].Description + "(hidden)");
            }
            else
            {
                listAutoCodes.Items.Add(AutoCodeC.getList()[i].Description);
            } 
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormAutoCodeEdit FormACE = new FormAutoCodeEdit();
        FormACE.IsNew = true;
        FormACE.AutoCodeCur = new AutoCode();
        AutoCodes.insert(FormACE.AutoCodeCur);
        FormACE.ShowDialog();
        if (FormACE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillList();
    }

    private void listAutoCodes_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listAutoCodes.SelectedIndex == -1)
        {
            return ;
        }
         
        AutoCode AutoCodeCur = AutoCodeC.getList()[listAutoCodes.SelectedIndex];
        FormAutoCodeEdit FormACE = new FormAutoCodeEdit();
        FormACE.AutoCodeCur = AutoCodeCur;
        FormACE.ShowDialog();
        if (FormACE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillList();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listAutoCodes.SelectedIndex < 0)
        {
            MessageBox.Show(Lan.g(this,"You must first select a row"));
            return ;
        }
         
        AutoCode autoCodeCur = AutoCodeC.getList()[listAutoCodes.SelectedIndex];
        try
        {
            AutoCodes.delete(autoCodeCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        changed = true;
        fillList();
    }

    private void formAutoCode_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.AutoCodes);
        }
         
        DialogResult = DialogResult.OK;
    }

}


