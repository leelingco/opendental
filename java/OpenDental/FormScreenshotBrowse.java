//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;

public class FormScreenshotBrowse  extends Form 
{

    public String ScreenshotPath = new String();
    private String[] files = new String[]();
    public FormScreenshotBrowse() throws Exception {
        initializeComponent();
    }

    private void formScreenshotBrowse_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        pictureBoxMain.Image = Image.FromFile(ScreenshotPath);
        String folder = Path.GetDirectoryName(ScreenshotPath);
        files = Directory.GetFileSystemEntries(folder, "*-*-*");
        //to exclude thumbs.db
        String[] arrayFileNames = new String[files.Length];
        DateTime[] arrayDates = new DateTime[files.Length];
        for (int i = 0;i < files.Length;i++)
        {
            String filename = Path.GetFileNameWithoutExtension(files[i]);
            //2011-08-20-07112332
            if (filename.Length == 19)
            {
                filename = filename.Substring(0, 10) + " " + filename.Substring(11, 2) + ":" + filename.Substring(13, 2) + ":" + filename.Substring(15, 2);
            }
             
            //2011-08-20 07:11:32
            arrayFileNames[i] = files[i];
            arrayDates[i] = DateTime.ParseExact(filename, "yyyy-MM-dd HH:mm:ss", CultureInfo.CurrentCulture);
        }
        Array.Sort(arrayDates, arrayFileNames);
        for (int i = 0;i < arrayFileNames.Length;i++)
        {
            //sort filenames by date
            //if(arrayFileNames.Length>50) {
            //	arrayFileNames=arrayFileNames.CopyTo(
            //}
            String filename = Path.GetFileNameWithoutExtension(arrayFileNames[i]);
            //2011-08-20-07112332
            if (filename.Length == 19)
            {
                filename = filename.Substring(0, 10) + " " + filename.Substring(11, 2) + ":" + filename.Substring(13, 2) + ":" + filename.Substring(15, 2);
            }
             
            //2011-08-20 07:11:32
            listFiles.Items.Add(filename);
            if (StringSupport.equals(ScreenshotPath, arrayFileNames[i]))
            {
                listFiles.SetSelected(i, true);
            }
             
        }
        Cursor = Cursors.Default;
        listFiles.SelectedIndexChanged += new EventHandler(listFiles_SelectedIndexChanged);
    }

    void listFiles_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        pictureBoxMain.Image = Image.FromFile(files[listFiles.SelectedIndex]);
        Cursor = Cursors.Default;
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
        this.listFiles = new System.Windows.Forms.ListBox();
        this.pictureBoxMain = new System.Windows.Forms.PictureBox();
        ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMain)).BeginInit();
        this.SuspendLayout();
        //
        // listFiles
        //
        this.listFiles.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listFiles.FormattingEnabled = true;
        this.listFiles.IntegralHeight = false;
        this.listFiles.Location = new System.Drawing.Point(0, 0);
        this.listFiles.Name = "listFiles";
        this.listFiles.Size = new System.Drawing.Size(157, 707);
        this.listFiles.TabIndex = 0;
        //
        // pictureBoxMain
        //
        this.pictureBoxMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.pictureBoxMain.Location = new System.Drawing.Point(159, 0);
        this.pictureBoxMain.Name = "pictureBoxMain";
        this.pictureBoxMain.Size = new System.Drawing.Size(829, 707);
        this.pictureBoxMain.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
        this.pictureBoxMain.TabIndex = 1;
        this.pictureBoxMain.TabStop = false;
        //
        // FormScreenshotBrowse
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(988, 707);
        this.Controls.Add(this.pictureBoxMain);
        this.Controls.Add(this.listFiles);
        this.Name = "FormScreenshotBrowse";
        this.Text = "FormScreenshotBrowse";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormScreenshotBrowse_Load);
        ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMain)).EndInit();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.ListBox listFiles = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.PictureBox pictureBoxMain = new System.Windows.Forms.PictureBox();
}


