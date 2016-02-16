//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiIncomingLinks  extends Form 
{

    public String PageTitleCur = new String();
    public WikiPage JumpToPage;
    private List<WikiPage> ListWikiPages = new List<WikiPage>();
    public FormWikiIncomingLinks() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWiki_Load(Object sender, EventArgs e) throws Exception {
        Text = "Incoming links to " + PageTitleCur;
        fillGrid();
        if (ListWikiPages.Count == 0)
        {
            MsgBox.show(this,"This page has no incoming links.");
            Close();
        }
         
    }

    private void loadWikiPage(WikiPage wikiPage) throws Exception {
        webBrowserWiki.DocumentText = WikiPages.translateToXhtml(wikiPage.PageContent,false);
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Page Title"),70);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g(this,"Saved"),42);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        ListWikiPages = WikiPages.getIncomingLinks(PageTitleCur);
        for (int i = 0;i < ListWikiPages.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListWikiPages[i].PageTitle);
            //row.Cells.Add(page.DateTimeSaved.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 1)
        {
            return ;
        }
         
        webBrowserWiki.AllowNavigation = true;
        LoadWikiPage(ListWikiPages[gridMain.getSelectedIndices()[0]]);
        gridMain.Focus();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        JumpToPage = ListWikiPages[e.getRow()];
        DialogResult = DialogResult.OK;
    }

    private void webBrowserWiki_Navigated(Object sender, WebBrowserNavigatedEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = false;
    }

    //to disable links in pages.
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(248, 621);
        this.gridMain.TabIndex = 6;
        this.gridMain.setTitle("Incoming Links");
        this.gridMain.setTranslationName("TableIncomingLinks");
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
        this.gridMain.Click += new System.EventHandler(this.gridMain_Click);
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.AllowNavigation = false;
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.IsWebBrowserContextMenuEnabled = false;
        this.webBrowserWiki.Location = new System.Drawing.Point(266, 12);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(592, 621);
        this.webBrowserWiki.TabIndex = 0;
        this.webBrowserWiki.Navigated += new System.Windows.Forms.WebBrowserNavigatedEventHandler(this.webBrowserWiki_Navigated);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(864, 609);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormWikiIncomingLinks
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(951, 645);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.webBrowserWiki);
        this.Name = "FormWikiIncomingLinks";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Incoming Links";
        this.Load += new System.EventHandler(this.FormWiki_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butClose;
}


