//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package ServiceManager;

import ServiceManager.FormServiceManage;
import ServiceManager.FormMain;

public class FormMain  extends Form 
{

    List<ServiceController> serviceControllerList = new List<ServiceController>();
    public FormMain() throws Exception {
        initializeComponent();
    }

    private void formMain_Load(Object sender, EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        listMain.Items.Clear();
        serviceControllerList = new List<ServiceController>();
        ServiceController[] serviceControllersAll = ServiceController.GetServices();
        for (int i = 0;i < serviceControllersAll.Length;i++)
        {
            if (serviceControllersAll[i].ServiceName.StartsWith("OpenDent"))
            {
                serviceControllerList.Add(serviceControllersAll[i]);
                listMain.Items.Add(serviceControllersAll[i].ServiceName);
            }
             
        }
    }

    private void listMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        FormServiceManage FormS = new FormServiceManage();
        FormS.allOpenDentServices = serviceControllerList;
        FormS.ServControllerCur = serviceControllerList[listMain.SelectedIndex];
        FormS.ShowDialog();
        fillList();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormServiceManage FormS = new FormServiceManage();
        FormS.allOpenDentServices = serviceControllerList;
        FormS.ShowDialog();
        fillList();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMain.class);
        this.listMain = new System.Windows.Forms.ListBox();
        this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
        this.label1 = new System.Windows.Forms.Label();
        this.butAdd = new System.Windows.Forms.Button();
        this.backgroundWorker2 = new System.ComponentModel.BackgroundWorker();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.FormattingEnabled = true;
        this.listMain.Location = new System.Drawing.Point(26, 114);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(174, 303);
        this.listMain.TabIndex = 1;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(23, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(318, 98);
        this.label1.TabIndex = 2;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // butAdd
        //
        this.butAdd.Location = new System.Drawing.Point(26, 423);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 3;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormMain
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(353, 469);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listMain);
        this.Name = "FormMain";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Service Manager";
        this.Load += new System.EventHandler(this.FormMain_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private System.ComponentModel.BackgroundWorker backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.ComponentModel.BackgroundWorker backgroundWorker2 = new System.ComponentModel.BackgroundWorker();
}


