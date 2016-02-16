//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userods;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPageHist;
import OpenDentBusiness.WikiPageHists;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiHistory  extends Form 
{

    public String PageTitleCur = new String();
    private List<WikiPageHist> ListWikiPageHists = new List<WikiPageHist>();
    public FormWikiHistory() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiHistory_Load(Object sender, EventArgs e) throws Exception {
        resizeControls();
        //textContent.ReadOnly=true;
        fillGrid();
        gridMain.SetSelected(gridMain.getRows().Count - 1, true);
        //There will always be at least one page in the history
        LoadWikiPage(ListWikiPageHists[gridMain.getSelectedIndices()[0]]);
        //should never be null.
        Text = "Wiki History - " + PageTitleCur;
    }

    private void resizeControls() throws Exception {
        //assuming gridMain, textNumbers do not change width or location.
        Rectangle actualWorkingArea = new Rectangle(294, 12, ClientSize.Width - 397, ClientSize.Height - 24);
        //textNumbers resize
        textNumbers.Height = actualWorkingArea.Height;
        //text resize
        textContent.Top = actualWorkingArea.Top;
        textContent.Height = actualWorkingArea.Height;
        textContent.Left = actualWorkingArea.Left;
        textContent.Width = actualWorkingArea.Width / 2 - 2;
        //Browser resize
        webBrowserWiki.Top = actualWorkingArea.Top;
        webBrowserWiki.Height = actualWorkingArea.Height;
        webBrowserWiki.Left = actualWorkingArea.Left + actualWorkingArea.Width / 2 + 2;
        webBrowserWiki.Width = actualWorkingArea.Width / 2 - 2;
    }

    //Button move
    //butRefresh.Left=ClientSize.Width/2+2;
    private void loadWikiPage(WikiPageHist WikiPageCur) throws Exception {
        webBrowserWiki.DocumentText = WikiPages.translateToXhtml(WikiPageCur.PageContent,false);
        textContent.setText(WikiPageCur.PageContent);
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"User"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Del"),25);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Saved"),80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ListWikiPageHists = WikiPageHists.getByTitle(PageTitleCur);
        ListWikiPageHists.Add(WikiPages.pageToHist(WikiPages.getByTitle(PageTitleCur)));
        for (int i = 0;i < ListWikiPageHists.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Userods.GetName(ListWikiPageHists[i].UserNum));
            row.getCells().add((ListWikiPageHists[i].IsDeleted ? "X" : ""));
            row.getCells().Add(ListWikiPageHists[i].DateTimeSaved.ToString());
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
        LoadWikiPage(ListWikiPageHists[gridMain.getSelectedIndices()[0]]);
        gridMain.Focus();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //MsgBoxCopyPaste mbox = new MsgBoxCopyPaste(ListWikiPageHists[e.Row].PageContent);
    //mbox.ShowDialog();
    //FormWikiEdit FormWE = new FormWikiEdit();
    //FormWE.WikiPageCur=listWikiPages[gridMain.SelectedIndices[0]];
    //FormWE.ShowDialog();
    //if(FormWE.DialogResult!=DialogResult.OK) {
    //  return;
    //}
    //FillGrid();
    //LoadWikiPage(listWikiPages[0]);
    private void webBrowserWiki_Navigated(Object sender, WebBrowserNavigatedEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = false;
    }

    //to disable links in pages.
    private void butRevert_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        if (gridMain.getSelectedIndex() == gridMain.getRows().Count - 1)
        {
            return ;
        }
         
        //current revision of page
        //DialogResult=DialogResult.OK;
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Revert page to currently selected revision?"))
        {
            return ;
        }
         
        WikiPage wikiPageNew = WikiPageHists.RevertFrom(ListWikiPageHists[gridMain.getSelectedIndex()]);
        wikiPageNew.UserNum = Security.getCurUser().UserNum;
        WikiPages.insertAndArchive(wikiPageNew);
        fillGrid();
        gridMain.setSelected(false);
        gridMain.SetSelected(gridMain.getRows().Count - 1, true);
        //select the new revision.
        gridMain.scrollToEnd();
    }

    //in case there are LOTS of revisions. Should this go in the fill grid code?
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
        this.textNumbers = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butRevert = new OpenDental.UI.Button();
        this.textContent = new OpenDental.TextBoxWiki();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textNumbers
        //
        this.textNumbers.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.textNumbers.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textNumbers.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(24)))), ((int)(((byte)(117)))), ((int)(((byte)(133)))));
        this.textNumbers.Location = new System.Drawing.Point(266, 12);
        this.textNumbers.Multiline = true;
        this.textNumbers.Name = "textNumbers";
        this.textNumbers.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNumbers.Size = new System.Drawing.Size(46, 614);
        this.textNumbers.TabIndex = 83;
        this.textNumbers.Text = "1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n10\r\n11\r\n12\r\n13\r\n188\r\n288";
        this.textNumbers.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(248, 614);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Page History");
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
        this.gridMain.Click += new System.EventHandler(this.gridMain_Click);
        //
        // butRevert
        //
        this.butRevert.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRevert.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRevert.setAutosize(true);
        this.butRevert.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRevert.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRevert.setCornerRadius(4F);
        this.butRevert.Location = new System.Drawing.Point(1067, 12);
        this.butRevert.Name = "butRevert";
        this.butRevert.Size = new System.Drawing.Size(75, 24);
        this.butRevert.TabIndex = 84;
        this.butRevert.Text = "Revert";
        this.butRevert.Click += new System.EventHandler(this.butRevert_Click);
        //
        // textContent
        //
        this.textContent.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.textContent.Location = new System.Drawing.Point(294, 12);
        this.textContent.Name = "textContent";
        this.textContent.setReadOnly(true);
        this.textContent.setSelectedText("");
        this.textContent.setSelectionLength(0);
        this.textContent.setSelectionStart(0);
        this.textContent.Size = new System.Drawing.Size(347, 614);
        this.textContent.TabIndex = 82;
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.AllowWebBrowserDrop = false;
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.IsWebBrowserContextMenuEnabled = false;
        this.webBrowserWiki.Location = new System.Drawing.Point(647, 12);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(414, 614);
        this.webBrowserWiki.TabIndex = 6;
        this.webBrowserWiki.WebBrowserShortcutsEnabled = false;
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
        this.butClose.Location = new System.Drawing.Point(1067, 602);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormWikiHistory
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1154, 638);
        this.Controls.Add(this.butRevert);
        this.Controls.Add(this.textContent);
        this.Controls.Add(this.textNumbers);
        this.Controls.Add(this.webBrowserWiki);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormWikiHistory";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Wiki History";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormWikiHistory_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private OpenDental.TextBoxWiki textContent;
    private System.Windows.Forms.TextBox textNumbers = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butRevert;
}


