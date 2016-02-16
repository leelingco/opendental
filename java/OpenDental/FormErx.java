//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormErx;
import OpenDental.Lan;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Provider;

public class FormErx  extends Form 
{

    private String browseToUrl = "";
    public Provider prov;
    public Employee emp;
    public Patient pat;
    public FormErx() throws Exception {
        initializeComponent();
        Lan.F(this);
        SHDocVw.WebBrowser axBrowser = (SHDocVw.WebBrowser)browser.ActiveXInstance;
        //axBrowser.NewWindow2+=new SHDocVw.DWebBrowserEvents2_NewWindow3EventHandler(axBrowser_NewWindow2);
        axBrowser.NewWindow3 += new SHDocVw.DWebBrowserEvents2_NewWindow3EventHandler(axBrowser_NewWindow3);
    }

    public FormErx(String url) throws Exception {
        initializeComponent();
        Lan.F(this);
        browseToUrl = url;
    }

    private void formErx_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        //work on this later if needed.
        Application.DoEvents();
        if (!StringSupport.equals(browseToUrl, ""))
        {
            //Use the window as a simple web browswer when a URL is passed in.
            Text = "";
            browser.Navigate(browseToUrl);
            return ;
        }
         
        composeNewRx();
    }

    /**
    * Uses the public prov, emp and pat variables to build a new prescription and load it within browser control. Loads the compose tab in NewCrop's web interface.
    */
    private void composeNewRx() throws Exception {
        String clickThroughXml = "";
        // ErxXml.BuildClickThroughXml(prov,emp,pat);
        String xmlBase64 = System.Web.HttpUtility.HtmlEncode(Convert.ToBase64String(ASCIIEncoding.ASCII.GetBytes(clickThroughXml)));
        xmlBase64 = xmlBase64.Replace("+", "%2B");
        //A common base 64 character which needs to be escaped within URLs.
        xmlBase64 = xmlBase64.Replace("/", "%2F");
        //A common base 64 character which needs to be escaped within URLs.
        xmlBase64 = xmlBase64.Replace("=", "%3D");
        //Base 64 strings usually end in '=', which could mean a new parameter definition within the URL so we escape.
        String postdata = "RxInput=base64:" + xmlBase64;
        byte[] PostDataBytes = System.Text.Encoding.UTF8.GetBytes(postdata);
        String additionalHeaders = "Content-Type: application/x-www-form-urlencoded\r\n";
        String newCropUrl = "https://secure.newcropaccounts.com/interfacev7/rxentry.aspx";
        browser.Navigate(newCropUrl, "", PostDataBytes, additionalHeaders);
    }

    /**
    * This event fires when a link is clicked within the webbrowser control which opens in a new window.
    */
    private void browser_NewWindow(Object sender, CancelEventArgs e) throws Exception {
        //By default, new windows launched by clicking a link from within the webbrowser control, open in Internet Explorer, even if the system default is another web browser such as Mozilla.
        //We had a problem with cookies not being carried over from our webbrowser control into Internet Explorer when a link is clicked.
        //To preserve cookies, we intercept the new window creation, cancel it, then launch the destination URL in a new window within OD.
        String destinationUrl = browser.StatusText;
        //This is the URL of the page that is supposed to open in a new window. For example, the "ScureScripts Drug History" link.
        if (Regex.IsMatch(destinationUrl, "^.*javascript\\:.*$", RegexOptions.IgnoreCase))
        {
            return ;
        }
         
        e.Cancel = true;
        //Cancel Internet Explorer from launching.
        FormErx browserWindowNew = new FormErx(destinationUrl);
        //Open the page in a new window, but stay inside of OD.
        browserWindowNew.WindowState = FormWindowState.Normal;
        browserWindowNew.ShowDialog();
    }

    /**
    * This event fires when a javascript snippet calls window.open() to open a URL in a new browser window. When window.open() is called, our browser_NewWindow() event function does not fire.
    */
    //private void axBrowser_NewWindow2(object sender,AxSHDocVw.DWebBrowserEvents2_NewWindow2Event e) {
    //  Form1 frmWB;
    //  frmWB = new Form1();
    //  frmWB.axWebBrowser1.RegisterAsBrowser = true;
    //  e.ppDisp = frmWB.axWebBrowser1.Application;
    //  frmWB.Visible = true;
    //}
    void axBrowser_NewWindow3(RefSupport<Object> ppDisp, RefSupport<boolean> Cancel, uint dwFlags, String bstrUrlContext, String bstrUrl) throws Exception {
    }

    public void browser_DocumentCompleted(Object sender, WebBrowserDocumentCompletedEventArgs e) throws Exception {
        Cursor = Cursors.Default;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormErx.class);
        this.browser = new System.Windows.Forms.WebBrowser();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // browser
        //
        this.browser.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.browser.Location = new System.Drawing.Point(0, 0);
        this.browser.MinimumSize = new System.Drawing.Size(20, 20);
        this.browser.Name = "browser";
        this.browser.Size = new System.Drawing.Size(973, 658);
        this.browser.TabIndex = 4;
        this.browser.Url = new System.Uri("", System.UriKind.Relative);
        this.browser.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.browser_DocumentCompleted);
        this.browser.NewWindow += new System.ComponentModel.CancelEventHandler(this.browser_NewWindow);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(891, 666);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormErx
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(974, 696);
        this.Controls.Add(this.browser);
        this.Controls.Add(this.butCancel);
        this.Cursor = System.Windows.Forms.Cursors.Default;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormErx";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "ERx";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormErx_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.WebBrowser browser = new System.Windows.Forms.WebBrowser();
}


