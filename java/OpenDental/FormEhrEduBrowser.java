//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;


public class FormEhrEduBrowser  extends Form 
{

    public String ResourceURL = new String();
    public boolean DidPrint = new boolean();
    public FormEhrEduBrowser(String resourceURL) throws Exception {
        ResourceURL = resourceURL;
        initializeComponent();
    }

    private void formEduBrowser_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        try
        {
            webBrowser1.Url = new Uri(ResourceURL);
        }
        catch (UriFormatException ex)
        {
            MessageBox.Show("The specified URL is in an incorrect format.  Did you include the http:// ?");
            DialogResult = DialogResult.Cancel;
        }

        Cursor = Cursors.Default;
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        //use the modeless version, which also allows user to choose printer
        webBrowser1.ShowPrintDialog();
        DidPrint = true;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.butClose = new System.Windows.Forms.Button();
        this.webBrowser1 = new System.Windows.Forms.WebBrowser();
        this.butPrint = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(804, 610);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // webBrowser1
        //
        this.webBrowser1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowser1.Location = new System.Drawing.Point(1, 1);
        this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowser1.Name = "webBrowser1";
        this.webBrowser1.Size = new System.Drawing.Size(882, 603);
        this.webBrowser1.TabIndex = 1;
        //
        // butPrint
        //
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.Location = new System.Drawing.Point(628, 610);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 2;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormEduBrowser
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(884, 639);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.webBrowser1);
        this.Controls.Add(this.butClose);
        this.Name = "FormEduBrowser";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Educational Resources";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormEduBrowser_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.WebBrowser webBrowser1 = new System.Windows.Forms.WebBrowser();
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
}


