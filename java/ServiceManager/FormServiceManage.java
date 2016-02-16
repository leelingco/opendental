//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package ServiceManager;

import CS2JNet.System.StringSupport;

public class FormServiceManage  extends Form 
{

    public ServiceController ServControllerCur = new ServiceController();
    public List<ServiceController> allOpenDentServices = new List<ServiceController>();
    public String pathToExe = new String();
    public FormServiceManage() throws Exception {
        initializeComponent();
    }

    private void formServiceManager_Load(Object sender, EventArgs e) throws Exception {
        allOpenDentServices = new List<ServiceController>();
        ServiceController[] serviceControllersAll = ServiceController.GetServices();
        for (int i = 0;i < serviceControllersAll.Length;i++)
        {
            if (serviceControllersAll[i].ServiceName.StartsWith("OpenDent"))
            {
                allOpenDentServices.Add(serviceControllersAll[i]);
            }
             
        }
        if (ServControllerCur != null)
        {
            //installed
            textName.Text = ServControllerCur.ServiceName;
            RegistryKey hklm = Registry.LocalMachine;
            hklm = hklm.OpenSubKey("System\\CurrentControlSet\\Services\\" + ServControllerCur.ServiceName);
            pathToExe = hklm.GetValue("ImagePath").ToString();
            pathToExe = pathToExe.Replace("\"", "");
            pathToExe = Path.GetDirectoryName(pathToExe);
            textPathToExe.Text = pathToExe;
            textStatus.Text = "Installed";
            butInstall.Enabled = false;
            butUninstall.Enabled = true;
            butBrowse.Enabled = false;
            textPathToExe.ReadOnly = true;
            textName.ReadOnly = true;
            if (ServControllerCur.Status == ServiceControllerStatus.Running)
            {
                textStatus.Text += ", Running";
                butStart.Enabled = false;
                butStop.Enabled = true;
            }
            else
            {
                textStatus.Text += ", Stopped";
                butStart.Enabled = true;
                butStop.Enabled = false;
            } 
        }
        else
        {
            textStatus.Text = "Not installed";
            textPathToExe.Text = Directory.GetCurrentDirectory();
            textName.Text = "";
            textName.ReadOnly = false;
            textPathToExe.ReadOnly = false;
            butInstall.Enabled = true;
            butUninstall.Enabled = false;
            butStart.Enabled = false;
            butStop.Enabled = false;
        } 
    }

    private void butInstall_Click(Object sender, EventArgs e) throws Exception {
        if (textName.Text.Length < 8 || !StringSupport.equals(textName.Text.Substring(0, 8), "OpenDent"))
        {
            MessageBox.Show("Error.  Service name must begin with \"OpenDent\".");
            return ;
        }
         
        for (int i = 0;i < allOpenDentServices.Count;i++)
        {
            //create list of all OpenDent service install paths to ensure only one service can be installed from each directory
            if (textName.Text == allOpenDentServices[i].ServiceName)
            {
                MessageBox.Show("Error.  An OpenDentHL7 service with this name is already installed.  Names must be unique.");
                return ;
            }
             
            RegistryKey hklm = Registry.LocalMachine;
            hklm = hklm.OpenSubKey("System\\CurrentControlSet\\Services\\" + allOpenDentServices[i].ServiceName);
            String installedServicePath = hklm.GetValue("ImagePath").ToString();
            installedServicePath = installedServicePath.Replace("\"", "");
            installedServicePath = Path.GetDirectoryName(installedServicePath);
            if (StringSupport.equals(installedServicePath, textPathToExe.Text))
            {
                MessageBox.Show("Error.  Cannot install an HL7 service from this directory.  Another OpenDentHL7 service is already installed from this directory.");
                return ;
            }
             
        }
        Process process = new Process();
        process.StartInfo.WorkingDirectory = textPathToExe.Text;
        process.StartInfo.FileName = "installutil.exe";
        //new strategy for having control over servicename
        //InstallUtil /ServiceName=OpenDentHL7_abc OpenDentHL7.exe
        process.StartInfo.Arguments = "/ServiceName=" + textName.Text + " OpenDentHL7.exe";
        process.Start();
        try
        {
            process.WaitForExit(10000);
            if (process.ExitCode != 0)
            {
                MessageBox.Show("Error. Exit code:" + process.ExitCode.ToString());
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Error. Did not exit after 10 seconds.");
        }

        ServiceController[] serviceControllersAll = ServiceController.GetServices();
        for (int i = 0;i < serviceControllersAll.Length;i++)
        {
            if (serviceControllersAll[i].ServiceName == textName.Text)
            {
                ServControllerCur = serviceControllersAll[i];
            }
             
        }
        butRefresh_Click(this,e);
    }

    private void butUninstall_Click(Object sender, EventArgs e) throws Exception {
        RegistryKey hklm = Registry.LocalMachine;
        Process process = new Process();
        process.StartInfo.WorkingDirectory = textPathToExe.Text;
        process.StartInfo.FileName = "installutil.exe";
        process.StartInfo.Arguments = "/u /ServiceName=" + textName.Text + " OpenDentHL7.exe";
        process.Start();
        try
        {
            process.WaitForExit(10000);
            if (process.ExitCode != 0)
            {
                MessageBox.Show("Error. Exit code:" + process.ExitCode.ToString());
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Error. Did not exit after 5 seconds.");
            return ;
        }

        ServControllerCur = null;
        DialogResult = DialogResult.OK;
    }

    private void butStart_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        try
        {
            ServiceController service = new ServiceController(textName.Text);
            service.MachineName = Environment.MachineName;
            service.Start();
            service.WaitForStatus(ServiceControllerStatus.Running, new TimeSpan(0, 0, 7));
            ServControllerCur = service;
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }

        Cursor = Cursors.Default;
        butRefresh_Click(this,e);
    }

    private void butStop_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        try
        {
            ServiceController service = new ServiceController(textName.Text);
            service.Stop();
            service.WaitForStatus(ServiceControllerStatus.Stopped, new TimeSpan(0, 0, 7));
            ServControllerCur = service;
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }

        Cursor = Cursors.Default;
        butRefresh_Click(this,e);
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        formServiceManager_Load(this,e);
    }

    private void butBrowse_Click(Object sender, EventArgs e) throws Exception {
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        dlg.SelectedPath = textPathToExe.Text;
        if (dlg.ShowDialog() == DialogResult.OK)
        {
            textPathToExe.Text = dlg.SelectedPath;
        }
         
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
        this.butRefresh = new System.Windows.Forms.Button();
        this.butStop = new System.Windows.Forms.Button();
        this.butStart = new System.Windows.Forms.Button();
        this.butUninstall = new System.Windows.Forms.Button();
        this.butInstall = new System.Windows.Forms.Button();
        this.textStatus = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textPathToExe = new System.Windows.Forms.TextBox();
        this.butBrowse = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butRefresh
        //
        this.butRefresh.Location = new System.Drawing.Point(377, 80);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 23);
        this.butRefresh.TabIndex = 4;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.UseVisualStyleBackColor = true;
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butStop
        //
        this.butStop.Location = new System.Drawing.Point(359, 113);
        this.butStop.Name = "butStop";
        this.butStop.Size = new System.Drawing.Size(75, 23);
        this.butStop.TabIndex = 3;
        this.butStop.Text = "Stop";
        this.butStop.UseVisualStyleBackColor = true;
        this.butStop.Click += new System.EventHandler(this.butStop_Click);
        //
        // butStart
        //
        this.butStart.Location = new System.Drawing.Point(278, 113);
        this.butStart.Name = "butStart";
        this.butStart.Size = new System.Drawing.Size(75, 23);
        this.butStart.TabIndex = 2;
        this.butStart.Text = "Start";
        this.butStart.UseVisualStyleBackColor = true;
        this.butStart.Click += new System.EventHandler(this.butStart_Click);
        //
        // butUninstall
        //
        this.butUninstall.Location = new System.Drawing.Point(197, 113);
        this.butUninstall.Name = "butUninstall";
        this.butUninstall.Size = new System.Drawing.Size(75, 23);
        this.butUninstall.TabIndex = 1;
        this.butUninstall.Text = "Uninstall";
        this.butUninstall.UseVisualStyleBackColor = true;
        this.butUninstall.Click += new System.EventHandler(this.butUninstall_Click);
        //
        // butInstall
        //
        this.butInstall.Location = new System.Drawing.Point(116, 113);
        this.butInstall.Name = "butInstall";
        this.butInstall.Size = new System.Drawing.Size(75, 23);
        this.butInstall.TabIndex = 0;
        this.butInstall.Text = "Install";
        this.butInstall.UseVisualStyleBackColor = true;
        this.butInstall.Click += new System.EventHandler(this.butInstall_Click);
        //
        // textStatus
        //
        this.textStatus.Location = new System.Drawing.Point(156, 82);
        this.textStatus.Name = "textStatus";
        this.textStatus.ReadOnly = true;
        this.textStatus.Size = new System.Drawing.Size(217, 20);
        this.textStatus.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(106, 83);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(48, 18);
        this.label2.TabIndex = 2;
        this.label2.Text = "Status";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(51, 31);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(103, 18);
        this.label1.TabIndex = 5;
        this.label1.Text = "Service Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(156, 30);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(217, 20);
        this.textName.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(6, 57);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(148, 18);
        this.label3.TabIndex = 7;
        this.label3.Text = "Path to OpenDentHL7.exe";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPathToExe
        //
        this.textPathToExe.Location = new System.Drawing.Point(156, 56);
        this.textPathToExe.Name = "textPathToExe";
        this.textPathToExe.Size = new System.Drawing.Size(448, 20);
        this.textPathToExe.TabIndex = 8;
        //
        // butBrowse
        //
        this.butBrowse.Location = new System.Drawing.Point(608, 55);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(75, 23);
        this.butBrowse.TabIndex = 9;
        this.butBrowse.Text = "Browse";
        this.butBrowse.UseVisualStyleBackColor = true;
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // FormServiceManage
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(697, 166);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textPathToExe);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.butStop);
        this.Controls.Add(this.butStart);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butUninstall);
        this.Controls.Add(this.textStatus);
        this.Controls.Add(this.butInstall);
        this.Name = "FormServiceManage";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Manage Service";
        this.Load += new System.EventHandler(this.FormServiceManager_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butRefresh = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butStop = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butStart = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butUninstall = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butInstall = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textStatus = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPathToExe = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butBrowse = new System.Windows.Forms.Button();
}


