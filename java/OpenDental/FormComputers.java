//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:53 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormComputers;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.Computers;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;

/**
* 
*/
public class FormComputers  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listComputer = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSetSimpleGraphics;
    private Label label3 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    // Required designer variable.
    //private Programs Programs=new Programs();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormComputers() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormComputers.class);
        this.listComputer = new System.Windows.Forms.ListBox();
        this.butClose = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.butSetSimpleGraphics = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // listComputer
        //
        this.listComputer.Items.AddRange(new Object[]{ "" });
        this.listComputer.Location = new System.Drawing.Point(17, 97);
        this.listComputer.Name = "listComputer";
        this.listComputer.Size = new System.Drawing.Size(282, 277);
        this.listComputer.TabIndex = 34;
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
        this.butClose.Location = new System.Drawing.Point(438, 395);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 38;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(19, 6);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(409, 44);
        this.label1.TabIndex = 43;
        this.label1.Text = "Computers are added to this list every time you use Open Dental.  You can safely " + "delete unused computer names from this list to speed up messaging.";
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
        this.butDelete.Location = new System.Drawing.Point(18, 395);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 26);
        this.butDelete.TabIndex = 44;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(17, 71);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(294, 23);
        this.label2.TabIndex = 45;
        this.label2.Text = "ComputerName";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSetSimpleGraphics
        //
        this.butSetSimpleGraphics.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetSimpleGraphics.setAutosize(true);
        this.butSetSimpleGraphics.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetSimpleGraphics.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetSimpleGraphics.setCornerRadius(4F);
        this.butSetSimpleGraphics.Location = new System.Drawing.Point(40, 182);
        this.butSetSimpleGraphics.Name = "butSetSimpleGraphics";
        this.butSetSimpleGraphics.Size = new System.Drawing.Size(115, 24);
        this.butSetSimpleGraphics.TabIndex = 81;
        this.butSetSimpleGraphics.Text = "Use Simple Graphics";
        this.butSetSimpleGraphics.Click += new System.EventHandler(this.butSetSimpleGraphics_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(18, 25);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(161, 151);
        this.label3.TabIndex = 82;
        this.label3.Text = resources.GetString("label3.Text");
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butSetSimpleGraphics);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(318, 92);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(195, 219);
        this.groupBox1.TabIndex = 83;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Fix a Workstation";
        //
        // FormComputers
        //
        this.AcceptButton = this.butClose;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(535, 441);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listComputer);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormComputers";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Computers";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormComputers_Closing);
        this.Load += new System.EventHandler(this.FormComputers_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formComputers_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        Computers.refreshCache();
        listComputer.Items.Clear();
        String itemName = "";
        for (int i = 0;i < Computers.getList().Length;i++)
        {
            itemName = Computers.getList()[i].CompName;
            //+" ("+Computers.List[i].PrinterName+")";
            listComputer.Items.Add(itemName);
        }
    }

    /**
    * Set graphics for selected computer to simple.  Makes audit log entry.
    */
    private void butSetSimpleGraphics_Click(Object sender, EventArgs e) throws Exception {
        if (listComputer.SelectedIndex == -1)
        {
            return ;
        }
         
        ComputerPrefs.SetToSimpleGraphics(Computers.getList()[listComputer.SelectedIndex].CompName);
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Set the graphics for computer " + Computers.getList()[listComputer.SelectedIndex].CompName + " to simple");
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listComputer.SelectedIndex == -1)
        {
            return ;
        }
         
        Computers.Delete(Computers.getList()[listComputer.SelectedIndex]);
        changed = true;
        fillList();
    }

    /*private void listProgram_DoubleClick(object sender, System.EventArgs e) {
    			if(listProgram.SelectedIndex==-1)
    				return;
    			Programs.Cur=Programs.List[listProgram.SelectedIndex];
    			FormProgramLinkEdit FormPE=new FormProgramLinkEdit();
    			FormPE.ShowDialog();
    			FillList();
    		}*/
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formComputers_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Computers);
        }
         
    }

}


