//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:21 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormMountDefEdit;
import OpenDental.FormMountDefs;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MountDef;
import OpenDentBusiness.MountDefC;
import OpenDentBusiness.MountDefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormMountDefs  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormMountDefs() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMountDefs.class);
        this.listMain = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(24, 42);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(262, 264);
        this.listMain.TabIndex = 2;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(461, 27);
        this.label1.TabIndex = 11;
        this.label1.Text = "This is a list of radiograph mounts or image composites.  You can freely delete o" + "r move any of these items without affecting patient records";
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(24, 313);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(396, 313);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(204, 313);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(82, 26);
        this.butDown.TabIndex = 36;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(113, 313);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(82, 26);
        this.butUp.TabIndex = 37;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // FormMountDefs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(494, 356);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMountDefs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Mount Definitions";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormMounts_FormClosing);
        this.Load += new System.EventHandler(this.FormMountDefs_Load);
        this.ResumeLayout(false);
    }

    private void formMountDefs_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        MountDefs.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < MountDefC.getListt().Count;i++)
        {
            listMain.Items.Add(MountDefC.getListt()[i].Description);
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        MountDef mount = new MountDef();
        FormMountDefEdit FormM = new FormMountDefEdit(mount);
        FormM.IsNew = true;
        FormM.ShowDialog();
        fillList();
        changed = true;
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormMountDefEdit FormM = new FormMountDefEdit(MountDefC.getListt()[listMain.SelectedIndex]);
        FormM.ShowDialog();
        fillList();
        changed = true;
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
    }

    /*int selected=0;
    			if(listViewButtons.SelectedIndices.Count==0) {
    				return;
    			}
    			else if(listViewButtons.SelectedIndices[0]==0) {
    				return;
    			}
    			else {
    				ProcButton but=ButtonList[listViewButtons.SelectedIndices[0]].Clone();
    				but.ItemOrder--;
    				ProcButtons.Update(but);
    				selected=but.ItemOrder;
    				but=ButtonList[listViewButtons.SelectedIndices[0]-1].Clone();
    				but.ItemOrder++;
    				ProcButtons.Update(but);
    			}
    			FillButtons();
    			changed=true;
    			listViewButtons.SelectedIndices.Clear();
    			listViewButtons.SelectedIndices.Add(selected);*/
    private void butDown_Click(Object sender, EventArgs e) throws Exception {
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formMounts_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ToolBut);
        }
         
    }

}


