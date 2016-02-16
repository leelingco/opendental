//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormWikiEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiAllPages  extends Form 
{

    private List<WikiPage> listWikiPages = new List<WikiPage>();
    /**
    * Need a reference to the form where this was launched from so that we can tell it to refresh later.
    */
    public FormWikiEdit OwnerForm;
    public FormWikiAllPages() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiAllPages_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    //gridMain.SetSelected(0,true);
    //LoadWikiPage(listWikiPages[0]);
    private void loadWikiPage(WikiPage WikiPageCur) throws Exception {
        webBrowserWiki.DocumentText = WikiPages.translateToXhtml(WikiPageCur.PageContent,false);
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
        listWikiPages = WikiPages.GetByTitleContains(textSearch.Text);
        for (int i = 0;i < listWikiPages.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            //if(listWikiPages[i].IsDeleted) {//color is not a good way to indicate deleted because it is not self explanatory.  Need another column for Deleted X.
            //	row.ColorText=Color.Red;
            //}
            row.getCells().Add(listWikiPages[i].PageTitle);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = true;
        LoadWikiPage(listWikiPages[e.getRow()]);
        gridMain.Focus();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (OwnerForm != null && !OwnerForm.IsDisposed)
        {
            OwnerForm.RefreshPage(listWikiPages[e.getRow()]);
        }
         
        Close();
    }

    private void webBrowserWiki_Navigated(Object sender, WebBrowserNavigatedEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = false;
    }

    //to disable links in pages.
    /**
    * 
    */
    private void butBrackets_Click(Object sender, EventArgs e) throws Exception {
        if (OwnerForm != null && !OwnerForm.IsDisposed)
        {
            OwnerForm.refreshPage(null);
        }
         
        Close();
    }

    /**
    * 
    */
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a page first.");
            return ;
        }
         
        if (OwnerForm != null && !OwnerForm.IsDisposed)
        {
            OwnerForm.RefreshPage(listWikiPages[gridMain.getSelectedIndex()]);
        }
         
        Close();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butBrackets = new OpenDental.UI.Button();
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
        this.butOK.Location = new System.Drawing.Point(925, 617);
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
        this.butCancel.Location = new System.Drawing.Point(925, 647);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.AllowWebBrowserDrop = false;
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.IsWebBrowserContextMenuEnabled = false;
        this.webBrowserWiki.Location = new System.Drawing.Point(266, 32);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(653, 639);
        this.webBrowserWiki.TabIndex = 9;
        this.webBrowserWiki.WebBrowserShortcutsEnabled = false;
        this.webBrowserWiki.Navigated += new System.Windows.Forms.WebBrowserNavigatedEventHandler(this.webBrowserWiki_Navigated);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 32);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(248, 639);
        this.gridMain.TabIndex = 8;
        this.gridMain.setTitle("All Wiki Pages");
        this.gridMain.setTranslationName("TableWikiHistory");
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
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(112, 8);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(100, 20);
        this.textSearch.TabIndex = 0;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(102, 18);
        this.label1.TabIndex = 11;
        this.label1.Text = "Search Titles";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butBrackets
        //
        this.butBrackets.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrackets.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBrackets.setAutosize(true);
        this.butBrackets.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrackets.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrackets.setCornerRadius(4F);
        this.butBrackets.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butBrackets.Location = new System.Drawing.Point(925, 587);
        this.butBrackets.Name = "butBrackets";
        this.butBrackets.Size = new System.Drawing.Size(75, 24);
        this.butBrackets.TabIndex = 12;
        this.butBrackets.Text = "[[  ]]";
        this.butBrackets.Click += new System.EventHandler(this.butBrackets_Click);
        //
        // FormWikiAllPages
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1012, 683);
        this.Controls.Add(this.butBrackets);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.webBrowserWiki);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiAllPages";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "All Wiki Pages";
        this.Load += new System.EventHandler(this.FormWikiAllPages_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.TextBox textSearch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butBrackets;
}


