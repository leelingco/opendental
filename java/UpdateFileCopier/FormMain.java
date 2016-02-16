//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package UpdateFileCopier;


public class FormMain  extends Form 
{

    private String SourceDirectory = new String();
    private String DestDirectory = new String();
    private int OpenDentProcessId = new int();
    public FormMain(String sourceDirectory, String openDentProcessId, String destDirectory) throws Exception {
        initializeComponent();
        SourceDirectory = sourceDirectory;
        DestDirectory = destDirectory;
        OpenDentProcessId = Int32.Parse(openDentProcessId);
    }

    //deprecated, but we'll just leave it here.
    private void formMain_Load(Object sender, EventArgs e) throws Exception {
    }

    private void formMain_Shown(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        //kill all processes named OpenDental.
        //If the software has been rebranded, the original exe will NOT be correctly closed.
        killProcess("OpenDental");
        //kill all processes named WebCamOD.
        //web cam does not always close properly when updater kills OpenDental
        //web cam relies on shared library, OpenDentBusiness.dll (shared ref with OpenDental).
        //if this lib can't be updated then the opendental update/install fails
        killProcess("WebCamOD");
        /* Don't bother with this anymore.  It always happens very quickly anyway.
        			if(OpenDentProcessId!=0){//it could be zero for debugging
        				try {
        					Process process=Process.GetProcessById(OpenDentProcessId);
        					now=DateTime.Now;
        					while(!process.HasExited) {
        						Application.DoEvents();
        					}
        					//TimeSpan span=DateTime.Now-now;
        					//MessageBox.Show("Time waited to exit: "+span.ToString());//~.07 seconds
        				}
        				catch { }//sometimes, it happens so fast that it would fail to get the processById.
        			}*/
        //wait for a moment to make sure they have really exited.
        Thread.Sleep(300);
        DirectoryInfo dirInfoSource = new DirectoryInfo(SourceDirectory);
        //DirectoryInfo dirInfoDest=new DirectoryInfo(DestDirectory);
        FileInfo[] appfiles = dirInfoSource.GetFiles();
        for (int i = 0;i < appfiles.Length;i++)
        {
            //create install log file directory
            /*Maybe later
            			string logDir = Path.Combine(DestDirectory,"InstallLogs");
            			if(!Directory.Exists(logDir)) {
            				Directory.CreateDirectory(logDir);
            			}
            			//create the log file so we can document the status of each file
            			using(StreamWriter sw = new StreamWriter(Path.Combine(logDir,string.Format(@"OD_Install {0}.txt",DateTime.Now.ToString("yyyy-MMM-dd hh-mm-tt"))))) {
            				sw.WriteLine(string.Format(@"Copying {0} files...",appfiles.Length));*/
            //Any file exclusions will have happened when originally copying files into the AtoZ folder.
            //And that happens in OpenDental.PrefL.CheckProgramVersion().
            String source = appfiles[i].FullName;
            String dest = Path.Combine(DestDirectory, appfiles[i].Name);
            try
            {
                //sw.WriteLine(string.Format(@"Copying file: {0} to {1}...",source,dest));
                File.Copy(source, dest, true);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        //Exception ex) {
        //silently fail.  This can prevent all kinds of problems if there are extra files sitting around.
        /*sw.WriteLine(
        							string.Format(@"***** File copy failed *****{0}Source: {1}{0}Dest: {2}{0}Error: {3}",
        								Environment.NewLine + "   ",
        								source,
        								dest,
        								ex.Message));*/
        //}
        //DirectoryInfo dirInfoDest=new DirectoryInfo(DestDirectory);
        //MessageBox.Show(dirInfoDest.GetFiles().Length.ToString());
        //The above test shows that by the time it gets to this point,
        //the files have already been copied over, so short wait.
        Thread.Sleep(300);
        //If Open Dental has been rebranded, then change this value:
        Process.Start(Path.Combine(DestDirectory, "OpenDental.exe"));
        Cursor = Cursors.Default;
        Application.Exit();
    }

    private static void killProcess(String name) throws Exception {
        Process[] processes = Process.GetProcessesByName(name);
        for (int i = 0;i < processes.Length;i++)
        {
            try
            {
                processes[i].Kill();
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
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
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(110, 42);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(81, 13);
        this.label1.TabIndex = 0;
        this.label1.Text = "Copying Files....";
        //
        // FormMain
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(302, 101);
        this.Controls.Add(this.label1);
        this.Name = "FormMain";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Update File Copier";
        this.Load += new System.EventHandler(this.FormMain_Load);
        this.Shown += new System.EventHandler(this.FormMain_Shown);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}
//CloseMainWindow and Close were ineffective if a dialog was open.//Kill() could fail if the process is closed between the time that the process list is read and the time that the Kill() function is called.//Since each Kill() call could take a few seconds, this exception could easily be caused by user interaction. In this case, the instance//is already closed so we don't need to take any further action.

//CloseMainWindow and Close were ineffective if a dialog was open.//Kill() could fail if the process is closed between the time that the process list is read and the time that the Kill() function is called.//Since each Kill() call could take a few seconds, this exception could easily be caused by user interaction. In this case, the instance//is already closed so we don't need to take any further action.