//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormEClinicalWorks;
import OpenDental.FormMountainside;
import OpenDental.FormPayConnectSetup;
import OpenDental.FormProgramLinkEdit;
import OpenDental.FormProgramLinks;
import OpenDental.FormUAppoint;
import OpenDental.FormXchargeSetup;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramC;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormProgramLinks  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListBox listProgram = new System.Windows.Forms.ListBox();
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    // Required designer variable.
    private Programs Programs = new Programs();
    private Label label2 = new Label();
    private Label label1 = new Label();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormProgramLinks() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProgramLinks.class);
        this.listProgram = new System.Windows.Forms.ListBox();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // listProgram
        //
        this.listProgram.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listProgram.Items.AddRange(new Object[]{ "" });
        this.listProgram.Location = new System.Drawing.Point(17, 41);
        this.listProgram.Name = "listProgram";
        this.listProgram.Size = new System.Drawing.Size(282, 576);
        this.listProgram.TabIndex = 34;
        this.listProgram.DoubleClick += new System.EventHandler(this.listProgram_DoubleClick);
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
        this.butClose.Location = new System.Drawing.Point(349, 633);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 38;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butAdd.Location = new System.Drawing.Point(17, 631);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 41;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 2);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(294, 36);
        this.label2.TabIndex = 43;
        this.label2.Text = "Double click on one of the programs in the list below to enable it or change its " + "settings";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(97, 627);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(240, 32);
        this.label1.TabIndex = 44;
        this.label1.Text = "Do not Add unless you have a totally custom bridge which we don\'t support.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormProgramLinks
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(452, 669);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listProgram);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProgramLinks";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Program Links";
        this.Load += new System.EventHandler(this.FormProgramLinks_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProgramLinks_Closing);
        this.ResumeLayout(false);
    }

    private void formProgramLinks_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        Programs.refreshCache();
        listProgram.Items.Clear();
        String itemName = "";
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            itemName = ProgramC.getListt()[i].ProgDesc;
            if (ProgramC.getListt()[i].Enabled)
                itemName += "(enabled)";
             
            listProgram.Items.Add(itemName);
        }
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormProgramLinkEdit FormPE = new FormProgramLinkEdit();
        FormPE.IsNew = true;
        FormPE.ProgramCur = new Program();
        FormPE.ShowDialog();
        changed = true;
        //because we don't really know what they did, so assume changed.
        fillList();
    }

    private void listProgram_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listProgram.SelectedIndex == -1)
        {
            return ;
        }
         
        Program program = ProgramC.getListt()[listProgram.SelectedIndex].Copy();
        if (StringSupport.equals(program.ProgName, "UAppoint"))
        {
            FormUAppoint FormU = new FormUAppoint();
            FormU.ProgramCur = program;
            FormU.ShowDialog();
            if (FormU.DialogResult == DialogResult.OK)
            {
                changed = true;
                fillList();
            }
             
            return ;
        }
         
        if (StringSupport.equals(program.ProgName, "eClinicalWorks"))
        {
            if (!Security.isAuthorized(Permissions.SecurityAdmin))
            {
                return ;
            }
             
            FormEClinicalWorks FormECW = new FormEClinicalWorks();
            FormECW.ProgramCur = program;
            FormECW.ShowDialog();
            if (FormECW.DialogResult == DialogResult.OK)
            {
                changed = true;
                fillList();
            }
             
            return ;
        }
         
        if (StringSupport.equals(program.ProgName, "Mountainside"))
        {
            FormMountainside FormM = new FormMountainside();
            FormM.ProgramCur = program;
            FormM.ShowDialog();
            if (FormM.DialogResult == DialogResult.OK)
            {
                changed = true;
                fillList();
            }
             
            return ;
        }
         
        if (StringSupport.equals(program.ProgName, "PayConnect"))
        {
            FormPayConnectSetup fpcs = new FormPayConnectSetup();
            fpcs.ShowDialog();
            if (fpcs.DialogResult == DialogResult.OK)
            {
                changed = true;
                fillList();
            }
             
            return ;
        }
         
        if (StringSupport.equals(program.ProgName, "Xcharge"))
        {
            FormXchargeSetup fxcs = new FormXchargeSetup();
            fxcs.ShowDialog();
            if (fxcs.DialogResult == DialogResult.OK)
            {
                changed = true;
                fillList();
            }
             
            return ;
        }
         
        FormProgramLinkEdit FormPE = new FormProgramLinkEdit();
        FormPE.ProgramCur = program;
        FormPE.ShowDialog();
        changed = true;
        fillList();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formProgramLinks_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Programs,InvalidType.ToolBut);
        }
         
    }

}


