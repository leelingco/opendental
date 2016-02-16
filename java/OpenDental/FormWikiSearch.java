//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPageHists;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiSearch  extends Form 
{

    private List<String> listWikiPageTitles = new List<String>();
    public String wikiPageTitleSelected = new String();
    public FormWikiSearch() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiSearch_Load(Object sender, EventArgs e) throws Exception {
        Rectangle rectWorkingArea = System.Windows.Forms.Screen.GetWorkingArea(this);
        Top = 0;
        Left = Math.Max(0, ((rectWorkingArea.Width - 1200) / 2) + rectWorkingArea.Left);
        Width = Math.Min(rectWorkingArea.Width, 1200);
        Height = rectWorkingArea.Height;
        fillGrid();
        wikiPageTitleSelected = "";
    }

    private void loadWikiPage(String WikiPageTitleCur) throws Exception {
        webBrowserWiki.AllowNavigation = true;
        butRestore.Enabled = false;
        if (checkDeletedOnly.Checked)
        {
            webBrowserWiki.DocumentText = WikiPages.translateToXhtml(WikiPageHists.getDeletedByTitle(WikiPageTitleCur).PageContent,true);
            butRestore.Enabled = true;
        }
        else
        {
            webBrowserWiki.DocumentText = WikiPages.translateToXhtml(WikiPages.getByTitle(WikiPageTitleCur).PageContent,true);
        } 
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Title"),70);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g(this,"Saved"),42);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        if (checkDeletedOnly.Checked)
        {
            listWikiPageTitles = WikiPageHists.GetDeletedPages(textSearch.Text, checkIgnoreContent.Checked);
        }
        else
        {
            listWikiPageTitles = WikiPages.GetForSearch(textSearch.Text, checkIgnoreContent.Checked);
        } 
        for (int i = 0;i < listWikiPageTitles.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listWikiPageTitles[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        webBrowserWiki.DocumentText = "";
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        LoadWikiPage(listWikiPageTitles[e.getRow()]);
        gridMain.Focus();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //SelectedWikiPage=listWikiPages[e.Row];
        if (checkDeletedOnly.Checked)
        {
            return ;
        }
         
        wikiPageTitleSelected = listWikiPageTitles[e.getRow()];
        DialogResult = DialogResult.OK;
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        //FillGrid();
        timer1.Stop();
        timer1.Start();
    }

    private void checkIgnoreContent_CheckedChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkDeletedOnly_CheckedChanged(Object sender, EventArgs e) throws Exception {
        butOK.Enabled = !checkDeletedOnly.Checked;
        fillGrid();
    }

    private void webBrowserWiki_Navigated(Object sender, WebBrowserNavigatedEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = false;
    }

    //to disable links in pages.
    private void butRestore_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        //should never happen.
        wikiPageTitleSelected = listWikiPageTitles[gridMain.getSelectedIndices()[0]];
        if (WikiPages.getByTitle(wikiPageTitleSelected) != null)
        {
            MsgBox.show(this,"Selected page has already been restored.");
            return ;
        }
         
        //should never happen.
        WikiPage wikiPageRestored = WikiPageHists.RevertFrom(WikiPageHists.GetDeletedByTitle(listWikiPageTitles[gridMain.getSelectedIndices()[0]]));
        wikiPageRestored.UserNum = Security.getCurUser().UserNum;
        WikiPages.insertAndArchive(wikiPageRestored);
        DialogResult = DialogResult.OK;
    }

    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        timer1.Stop();
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length > 0)
        {
            wikiPageTitleSelected = listWikiPageTitles[gridMain.getSelectedIndices()[0]];
        }
         
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
        this.components = new System.ComponentModel.Container();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.checkIgnoreContent = new System.Windows.Forms.CheckBox();
        this.checkDeletedOnly = new System.Windows.Forms.CheckBox();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butRestore = new OpenDental.UI.Button();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(248, 612);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Wiki Pages");
        this.gridMain.setTranslationName("TableWikiSearchPages");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(82, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(76, 18);
        this.label1.TabIndex = 13;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(160, 12);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(100, 20);
        this.textSearch.TabIndex = 0;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // checkIgnoreContent
        //
        this.checkIgnoreContent.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIgnoreContent.Location = new System.Drawing.Point(266, 9);
        this.checkIgnoreContent.Name = "checkIgnoreContent";
        this.checkIgnoreContent.Size = new System.Drawing.Size(188, 22);
        this.checkIgnoreContent.TabIndex = 14;
        this.checkIgnoreContent.Text = "Ignore Content";
        this.checkIgnoreContent.CheckedChanged += new System.EventHandler(this.checkIgnoreContent_CheckedChanged);
        //
        // checkDeletedOnly
        //
        this.checkDeletedOnly.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDeletedOnly.Location = new System.Drawing.Point(460, 9);
        this.checkDeletedOnly.Name = "checkDeletedOnly";
        this.checkDeletedOnly.Size = new System.Drawing.Size(188, 22);
        this.checkDeletedOnly.TabIndex = 15;
        this.checkDeletedOnly.Text = "Deleted Only";
        this.checkDeletedOnly.CheckedChanged += new System.EventHandler(this.checkDeletedOnly_CheckedChanged);
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.AllowNavigation = false;
        this.webBrowserWiki.AllowWebBrowserDrop = false;
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.IsWebBrowserContextMenuEnabled = false;
        this.webBrowserWiki.Location = new System.Drawing.Point(266, 38);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(825, 612);
        this.webBrowserWiki.TabIndex = 11;
        this.webBrowserWiki.WebBrowserShortcutsEnabled = false;
        this.webBrowserWiki.Navigated += new System.Windows.Forms.WebBrowserNavigatedEventHandler(this.webBrowserWiki_Navigated);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(1097, 596);
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
        this.butCancel.Location = new System.Drawing.Point(1097, 626);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butRestore
        //
        this.butRestore.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRestore.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRestore.setAutosize(true);
        this.butRestore.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRestore.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRestore.setCornerRadius(4F);
        this.butRestore.Enabled = false;
        this.butRestore.Location = new System.Drawing.Point(1097, 12);
        this.butRestore.Name = "butRestore";
        this.butRestore.Size = new System.Drawing.Size(75, 24);
        this.butRestore.TabIndex = 16;
        this.butRestore.Text = "Restore";
        this.butRestore.Click += new System.EventHandler(this.butRestore_Click);
        //
        // timer1
        //
        this.timer1.Interval = 500;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // FormWikiSearch
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1184, 662);
        this.Controls.Add(this.butRestore);
        this.Controls.Add(this.checkDeletedOnly);
        this.Controls.Add(this.checkIgnoreContent);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.webBrowserWiki);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiSearch";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Wiki Search";
        this.Load += new System.EventHandler(this.FormWikiSearch_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSearch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkIgnoreContent = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkDeletedOnly = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butRestore;
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
}


