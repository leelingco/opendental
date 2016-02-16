//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.InnoDb;
import OpenDentBusiness.Lans;
import OpenDentBusiness.MiscData;
import OpenDental.FormInnoDb;


/**
* 
*/
public class FormInnoDb  extends System.Windows.Forms.Form 
{

    /**
    * 
    */
    public FormInnoDb() throws Exception {
        initializeComponent();
        Lan.C(this, new System.Windows.Forms.Control[]{ this.textBox1 });
        Lan.f(this);
    }

    private void formInnoDb_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        StringBuilder strB = new StringBuilder();
        strB.Append('-', 60);
        textBox1.Text = DateTime.Now.ToString() + strB.ToString() + "\r\n";
        Application.DoEvents();
        textBox1.Text += Lans.g("FormInnoDb","Default Storage Engine: " + InnoDb.getDefaultEngine().ToString() + "\r\n");
        Application.DoEvents();
        textBox1.Text += InnoDb.getEngineCount();
        Application.DoEvents();
        Cursor = Cursors.Default;
    }

    /**
    * Will only convert to MyISAM if default storage engine set to MyISAM.
    */
    private void butToMyIsam_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will convert all tables in the database to the MyISAM storage engine.  This may take several minutes.\r\nContinue?"))
        {
            return ;
        }
         
        if (StringSupport.equals(InnoDb.getDefaultEngine(), "InnoDB"))
        {
            MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(Lan.g("FormInnoDB","You will first need to change your default storage engine to MyISAM.  Make sure that the following line is in your my.ini file: \r\n" + "default-storage-engine=MyISAM.\r\n" + "Then, restart the MySQL service and return here."));
            msgbox.ShowDialog();
            return ;
        }
         
        try
        {
            MiscData.makeABackup();
        }
        catch (Exception ex)
        {
            if (!StringSupport.equals(ex.Message, ""))
            {
                MessageBox.Show(ex.Message);
            }
             
            MsgBox.show("FormInnoDb","Backup failed. Your database has not been altered.");
            return ;
        }

        Cursor = Cursors.WaitCursor;
        textBox1.Text += Lans.g("FormInnoDb","Default Storage Engine: " + InnoDb.getDefaultEngine().ToString() + "\r\n");
        Application.DoEvents();
        int numchanged = InnoDb.convertTables("InnoDB","MyISAM");
        textBox1.Text += Lan.g("FormInnoDb","Number of tables converted to MyIsam: ") + numchanged.ToString() + "\r\n";
        Application.DoEvents();
        textBox1.Text += InnoDb.getEngineCount();
        Application.DoEvents();
        Cursor = Cursors.Default;
    }

    /**
    * Will only convert to InnoDB if default storage engine set to InnoDB and skip-innodb is not in my.ini file, which disables InnoDB engine.
    */
    private void butToInnoDb_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will convert all tables in the database to the InnoDB storage engine.  This may take several minutes.\r\nContinue?"))
        {
            return ;
        }
         
        if (!InnoDb.isInnodbAvail())
        {
            MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(Lan.g("FormInnoDb","InnoDB storage engine is disabled.  In order for InnoDB tables to work you must comment out the skip-innodb line in your my.ini file, like this:\r\n" + "#skip-innodb\r\n" + "and, if present, comment out the default-storage-engine line like this: \r\n" + "#default-storage-engine=MyISAM.\r\n" + "Then, restart the MySQL service and return here."));
            msgbox.ShowDialog();
            return ;
        }
         
        if (StringSupport.equals(InnoDb.getDefaultEngine(), "MyISAM"))
        {
            MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(Lan.g("FormInnoDB","You will first need to change your default storage engine to InnoDB.  In your my.ini file, comment out the default-storage-engine line like this: \r\n" + "#default-storage-engine=MyISAM.\r\n" + "Then, restart the MySQL service and return here."));
            msgbox.ShowDialog();
            return ;
        }
         
        try
        {
            MiscData.makeABackup();
        }
        catch (Exception ex)
        {
            if (!StringSupport.equals(ex.Message, ""))
            {
                MessageBox.Show(ex.Message);
            }
             
            MsgBox.show("FormInnoDb","Backup failed. Your database has not been altered.");
            return ;
        }

        Cursor = Cursors.WaitCursor;
        textBox1.Text += Lans.g("FormInnoDb","Default Storage Engine: " + InnoDb.getDefaultEngine().ToString() + "\r\n");
        Application.DoEvents();
        int numchanged = InnoDb.convertTables("MyISAM","InnoDB");
        textBox1.Text += Lan.g("FormInnoDb","Number of tables converted to InnoDB: ") + numchanged.ToString() + "\r\n";
        Application.DoEvents();
        textBox1.Text += InnoDb.getEngineCount();
        Application.DoEvents();
        Cursor = Cursors.Default;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInnoDb.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butToMyIsam = new OpenDental.UI.Button();
        this.butToInnoDb = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(539, 500);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(625, 500);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textBox1
        //
        this.textBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textBox1.Font = new System.Drawing.Font("Courier New", 8.25F);
        this.textBox1.Location = new System.Drawing.Point(21, 32);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textBox1.Size = new System.Drawing.Size(679, 456);
        this.textBox1.TabIndex = 18;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(507, 20);
        this.label1.TabIndex = 19;
        this.label1.Text = "This tool will convert all tables in the database to the selected storage engine." + "";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butToMyIsam
        //
        this.butToMyIsam.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToMyIsam.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butToMyIsam.setAutosize(true);
        this.butToMyIsam.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToMyIsam.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToMyIsam.setCornerRadius(4F);
        this.butToMyIsam.Location = new System.Drawing.Point(21, 500);
        this.butToMyIsam.Name = "butToMyIsam";
        this.butToMyIsam.Size = new System.Drawing.Size(104, 24);
        this.butToMyIsam.TabIndex = 20;
        this.butToMyIsam.Text = "Convert to MyIsam";
        this.butToMyIsam.Click += new System.EventHandler(this.butToMyIsam_Click);
        //
        // butToInnoDb
        //
        this.butToInnoDb.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToInnoDb.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butToInnoDb.setAutosize(true);
        this.butToInnoDb.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToInnoDb.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToInnoDb.setCornerRadius(4F);
        this.butToInnoDb.Location = new System.Drawing.Point(136, 500);
        this.butToInnoDb.Name = "butToInnoDb";
        this.butToInnoDb.Size = new System.Drawing.Size(104, 24);
        this.butToInnoDb.TabIndex = 21;
        this.butToInnoDb.Text = "Convert to InnoDb";
        this.butToInnoDb.Click += new System.EventHandler(this.butToInnoDb_Click);
        //
        // FormInnoDb
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.butToInnoDb);
        this.Controls.Add(this.butToMyIsam);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormInnoDb";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Convert Database Storage Engine";
        this.Load += new System.EventHandler(this.FormInnoDb_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butToMyIsam;
    private OpenDental.UI.Button butToInnoDb;
}


