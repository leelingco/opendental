//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebCamOD;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Phones;
import WebCamOD.VideoCapture;
import WebCamOD.FormWebCamOD;

public class FormWebCamOD  extends Form 
{

    private IntPtr intPtrVideo = new IntPtr();
    private VideoCapture vidCapt;
    private String IpAddressCur = new String();
    /**
    * This is set to minVal when starting up.  Whenever saving a screenshot, if the purge date is not today, then it runs a purge to keep the number of files from getting too big.  So this will usually happen within 5 minutes of someone clocking in for the day.
    */
    private DateTime datePurged = new DateTime();
    public FormWebCamOD() throws Exception {
        initializeComponent();
    }

    private void formWebCamOD_Load(Object sender, EventArgs e) throws Exception {
        datePurged = DateTime.MinValue;
        Process[] processes = Process.GetProcessesByName("WebCamOD");
        for (int p = 0;p < processes.Length;p++)
        {
            if (Process.GetCurrentProcess().Id == processes[p].Id)
            {
                continue;
            }
             
            //another process was found
            MessageBox.Show("WebCamOD is already running.");
            Application.Exit();
            return ;
        }
        //since this tool is only used at HQ, we hard code everything
        IPHostEntry iphostentry = Dns.GetHostEntry(Environment.MachineName);
        OpenDentBusiness.DataConnection dbcon = new OpenDentBusiness.DataConnection();
        try
        {
            dbcon.setDb("10.10.1.200","customers","root","","","",OpenDentBusiness.DatabaseType.MySql);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("This tool is not designed for general use.");
            return ;
        }

        //get ipaddress on startup
        IpAddressCur = "";
        for (Object __dummyForeachVar0 : iphostentry.AddressList)
        {
            IPAddress ipaddress = (IPAddress)__dummyForeachVar0;
            if (ipaddress.ToString().Contains("10.10.1"))
            {
                IpAddressCur = ipaddress.ToString();
            }
             
        }
        intPtrVideo = IntPtr.Zero;
        timerPhoneWebCam.Enabled = true;
        timerScreenShots.Enabled = true;
        timerScreenShots_Tick(this,new EventArgs());
    }

    //force an initial screenshot
    private void timerPhoneWebCam_Tick(Object sender, EventArgs e) throws Exception {
        if (vidCapt == null)
        {
            if (intPtrVideo != IntPtr.Zero)
            {
                // Release any previous buffer
                Marshal.FreeCoTaskMem(intPtrVideo);
                intPtrVideo = IntPtr.Zero;
            }
             
            int deviceCount = VideoCapture.getDeviceCount();
            if (deviceCount > 0)
            {
                try
                {
                    vidCapt = new VideoCapture(0, 640, 480, 24, pictBoxVideo);
                }
                catch (Exception __dummyCatchVar1)
                {
                    //image capture will now continue below if successful
                    Phones.SetWebCamImage(IpAddressCur, null, Environment.MachineName);
                    return ;
                }
            
            }
             
            //haven't actually seen this happen since we started properly disposing of vidCapt
            Phones.SetWebCamImage(IpAddressCur, null, Environment.MachineName);
        }
         
        if (vidCapt != null)
        {
            if (intPtrVideo != IntPtr.Zero)
            {
                // Release any previous buffer
                Marshal.FreeCoTaskMem(intPtrVideo);
                intPtrVideo = IntPtr.Zero;
            }
             
            Bitmap bitmapSmall = null;
            try
            {
                intPtrVideo = vidCapt.click();
                //will fail if camera unplugged
                Bitmap bitmap = new Bitmap(vidCapt.getWidth(), vidCapt.getHeight(), vidCapt.getStride(), PixelFormat.Format24bppRgb, intPtrVideo);
                bitmap.RotateFlip(RotateFlipType.RotateNoneFlipY);
                // If the image is upsidedown
                int w = 50;
                int h = (int)(((float)w) / 640f * 480f);
                bitmapSmall = new Bitmap(w, h);
                Graphics g = Graphics.FromImage(bitmapSmall);
                try
                {
                    {
                        g.DrawImage(bitmap, new Rectangle(0, 0, bitmapSmall.Width, bitmapSmall.Height));
                    }
                }
                finally
                {
                    if (g != null)
                        Disposable.mkDisposable(g).dispose();
                     
                }
                bitmap.Dispose();
                bitmap = null;
            }
            catch (Exception __dummyCatchVar2)
            {
                //bitmapSmall will remain null
                vidCapt.dispose();
                vidCapt = null;
            }
            finally
            {
            }
            //To prevent the above slow try/catch from happening again and again.
            //Marshal.FreeCoTaskMem(intPtrVideo);
            if (!StringSupport.equals(IpAddressCur, ""))
            {
                //found entry in phone table matching this machine ip.
                Phones.SetWebCamImage(IpAddressCur, bitmapSmall, Environment.MachineName);
            }
             
            if (bitmapSmall != null)
            {
                bitmapSmall.Dispose();
                bitmapSmall = null;
            }
             
        }
         
    }

    private void timerScreenShots_Tick(Object sender, EventArgs e) throws Exception {
        //ticks every 5 minutes
        int extension = Phones.IsOnClock(IpAddressCur, Environment.MachineName);
        if (extension == 0)
        {
            return ;
        }
         
        //if this person is on break
        //don't save a screenshot
        String folder = "\\\\serverfiles\\storage\\My\\Jordan\\ScreenshotsByWorkstation\\" + Environment.MachineName;
        if (!Directory.Exists(folder))
        {
            Directory.CreateDirectory(folder);
        }
         
        if (datePurged.Date != DateTime.Today)
        {
            String[] files = Directory.GetFiles(folder);
            for (int f = 0;f < files.Length;f++)
            {
                if (files[f].EndsWith("db"))
                {
                    continue;
                }
                 
                //skip thumbs.db
                DateTime dtCreated = File.GetCreationTime(files[f]);
                if (dtCreated.AddDays(7).Date < DateTime.Today)
                {
                    File.Delete(files[f]);
                }
                 
            }
            datePurged = DateTime.Today;
        }
         
        //create the image-----------------------------------------------------------------
        Point origin = new Point(0, 0);
        int right = 0;
        int bottom = 0;
        for (int s = 0;s < System.Windows.Forms.Screen.AllScreens.Length;s++)
        {
            //all screens together form a giant image.  We just need to know where origin is as well as size.
            if (System.Windows.Forms.Screen.AllScreens[s].WorkingArea.X < origin.X || System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Y < origin.Y)
            {
                //screen must be to top or left of primary.  Use its origin.
                origin = new Point(System.Windows.Forms.Screen.AllScreens[s].WorkingArea.X, System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Y);
            }
             
            if (System.Windows.Forms.Screen.AllScreens[s].WorkingArea.X + System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Width > right)
            {
                //screen must be to right of primary.  Use its right-most extension.
                right = System.Windows.Forms.Screen.AllScreens[s].WorkingArea.X + System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Width;
            }
             
            if (System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Y + System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Height > bottom)
            {
                //screen must be to bottom of primary.  Use its bottom-most extension.
                bottom = System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Y + System.Windows.Forms.Screen.AllScreens[s].WorkingArea.Height;
            }
             
        }
        //calculate total width and height, remembering that origin can be negative
        Size sizeAllScreens = new Size(right - origin.X, bottom - origin.Y);
        //example 100-(-20)=120, or 100-20=80.
        Bitmap bmp = new Bitmap(sizeAllScreens.Width, sizeAllScreens.Height);
        Graphics g = Graphics.FromImage(bmp);
        try
        {
            {
                g.CopyFromScreen(origin, new Point(0, 0), sizeAllScreens);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        //save the image----------------------------------------------------------------------
        //I tried a variety of file types.  The resulting file sizes were very similar.
        String filename = folder + "\\" + DateTime.Now.ToString("yyyy-MM-dd-HHmmssff") + ".jpg";
        bmp.Save(filename);
        //make a thumbnail with height of 50
        int thumbW = (int)((double)bmp.Width / (double)bmp.Height * 50d);
        Bitmap bmpThumb = new Bitmap(thumbW, 50);
        Graphics gThumb = Graphics.FromImage(bmpThumb);
        gThumb.DrawImage(bmp, 0, 0, thumbW, 50);
        gThumb.Dispose();
        gThumb = null;
        Phones.setScreenshot(extension,filename,bmpThumb);
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
         
        if (vidCapt != null)
        {
            vidCapt.dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormWebCamOD.class);
        this.pictBoxVideo = new System.Windows.Forms.PictureBox();
        this.timerPhoneWebCam = new System.Windows.Forms.Timer(this.components);
        this.label1 = new System.Windows.Forms.Label();
        this.timerScreenShots = new System.Windows.Forms.Timer(this.components);
        ((System.ComponentModel.ISupportInitialize)(this.pictBoxVideo)).BeginInit();
        this.SuspendLayout();
        //
        // pictBoxVideo
        //
        this.pictBoxVideo.Location = new System.Drawing.Point(153, 116);
        this.pictBoxVideo.Name = "pictBoxVideo";
        this.pictBoxVideo.Size = new System.Drawing.Size(64, 48);
        this.pictBoxVideo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
        this.pictBoxVideo.TabIndex = 0;
        this.pictBoxVideo.TabStop = false;
        //
        // timerPhoneWebCam
        //
        this.timerPhoneWebCam.Interval = 1600;
        this.timerPhoneWebCam.Tick += new System.EventHandler(this.timerPhoneWebCam_Tick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(362, 106);
        this.label1.TabIndex = 2;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // timerScreenShots
        //
        this.timerScreenShots.Interval = 300000;
        this.timerScreenShots.Tick += new System.EventHandler(this.timerScreenShots_Tick);
        //
        // FormWebCamOD
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(378, 177);
        this.Controls.Add(this.pictBoxVideo);
        this.Controls.Add(this.label1);
        this.Name = "FormWebCamOD";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "WebCamOD";
        this.Load += new System.EventHandler(this.FormWebCamOD_Load);
        ((System.ComponentModel.ISupportInitialize)(this.pictBoxVideo)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.PictureBox pictBoxVideo = new System.Windows.Forms.PictureBox();
    private System.Windows.Forms.Timer timerPhoneWebCam = new System.Windows.Forms.Timer();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Timer timerScreenShots = new System.Windows.Forms.Timer();
}
//IpAddress192,bitmapSmall,Environment.MachineName);

//IpAddress192,bitmapSmall,Environment.MachineName);