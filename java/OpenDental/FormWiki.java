//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormWikiEdit;
import OpenDental.FormWikiHistory;
import OpenDental.FormWikiIncomingLinks;
import OpenDental.FormWikiLists;
import OpenDental.FormWikiRename;
import OpenDental.FormWikiSearch;
import OpenDental.FormWikiSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormWiki;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;

public class FormWiki  extends Form 
{

    public WikiPage WikiPageCur;
    private List<String> historyNav = new List<String>();
    /**
    * Number of pages back that you are browsing. Current page == 0, Oldest page == historyNav.Length.
    */
    private int historyNavBack = new int();
    static final int FEATURE_DISABLE_NAVIGATION_SOUNDS = 21;
    static final int SET_FEATURE_ON_PROCESS = 0x00000002;
    static /* [UNSUPPORTED] 'extern' modifier not supported */ int coInternetSetFeatureEnabled(int FeatureEntry, int dwFlags, boolean fEnable) throws Exception ;

    public FormWiki() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWiki_Load(Object sender, EventArgs e) throws Exception {
        //disable the annoying clicking sound when the browser navigates
        coInternetSetFeatureEnabled(FEATURE_DISABLE_NAVIGATION_SOUNDS,SET_FEATURE_ON_PROCESS,true);
        webBrowserWiki.StatusTextChanged += new EventHandler(WebBrowserWiki_StatusTextChanged);
        Rectangle rectWorkingArea = System.Windows.Forms.Screen.GetWorkingArea(this);
        Top = 0;
        Left = Math.Max(0, ((rectWorkingArea.Width - 960) / 2) + rectWorkingArea.Left);
        Width = Math.Min(rectWorkingArea.Width, 960);
        Height = rectWorkingArea.Height;
        layoutToolBar();
        historyNav = new List<String>();
        historyNavBack = 0;
        //This is the pointer that keeps track of our position in historyNav.  0 means this is the newest page in history, a positive number is the number of pages before the newest page.
        loadWikiPage("Home");
    }

    /**
    * Because FormWikiEdit is no longer modal, this is necessary to be able to tell FormWiki to refresh when saving an edited page.
    */
    public void refreshPage(String pageTitle) throws Exception {
        historyNavBack--;
        //We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
        loadWikiPage(pageTitle);
    }

    private void webBrowserWiki_StatusTextChanged(Object sender, EventArgs e) throws Exception {
        //if(webBrowserWiki.StatusText=="") {
        //  return;
        //}
        labelStatus.Text = webBrowserWiki.StatusText;
        if (StringSupport.equals(labelStatus.Text, "Done"))
        {
            labelStatus.Text = "";
        }
         
    }

    /**
    * Before calling this, make sure to increment/decrement the historyNavBack index to keep track of the position in history.  If loading a new page, decrement historyNavBack before calling this function.
    */
    private void loadWikiPage(String pageTitle) throws Exception {
        //This is called from 11 different places, any time the program needs to refresh a page from the db.
        //It's also called from the browser_Navigating event when a "wiki:" link is clicked.
        WikiPage wpage = WikiPages.getByTitle(pageTitle);
        if (wpage == null)
        {
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"That page does not exist. Would you like to create it?"))
            {
                return ;
            }
             
            FormWikiEdit FormWE = new FormWikiEdit();
            FormWE.WikiPageCur = new WikiPage();
            FormWE.WikiPageCur.setIsNew(true);
            FormWE.WikiPageCur.PageTitle = pageTitle;
            //link back
            FormWE.WikiPageCur.PageContent = "[[" + WikiPageCur.PageTitle + "]]\r\n" + "<h1>" + pageTitle + "</h1>\r\n";
            //page title
            FormWE.OwnerForm = this;
            FormWE.Show();
            return ;
        }
         
        WikiPageCur = wpage;
        webBrowserWiki.DocumentText = WikiPages.translateToXhtml(WikiPageCur.PageContent,false);
        Text = "Wiki - " + WikiPageCur.PageTitle;
        //This region is duplicated in webBrowserWiki_Navigating() for external links.  Modifications here will need to be reflected there.
        int indexInHistory = historyNav.Count - (1 + historyNavBack);
        //historyNavBack number of pages before the last page in history.  This is the index of the page we are loading.
        if (historyNav.Count == 0)
        {
            //empty history
            historyNavBack = 0;
            historyNav.Add("wiki:" + pageTitle);
        }
        else if (historyNavBack < 0)
        {
            //historyNavBack could be negative here.  This means before the action that caused this load, we were not navigating through history, simply set back to 0 and add to historyNav[] if necessary.
            historyNavBack = 0;
            if (!StringSupport.equals(historyNav[historyNav.Count - 1], "wiki:" + pageTitle))
            {
                historyNav.Add("wiki:" + pageTitle);
            }
             
        }
        else if (historyNavBack >= 0 && !StringSupport.equals(historyNav[indexInHistory], "wiki:" + pageTitle))
        {
            //branching from page in history
            historyNav.RemoveRange(indexInHistory, historyNavBack + 1);
            //remove "forward" history. branching off in a new direction
            historyNavBack = 0;
            historyNav.Add("wiki:" + pageTitle);
        }
           
    }

    private void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Back"), 0, "", "Back"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Forward"), 1, "", "Forward"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Setup"), 2, Lan.g(this,"Setup master page and styles."), "Setup"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Home"), 3, "", "Home"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Edit"), 4, "", "Edit"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Print"), 5, "", "Print"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Rename"), 6, "", "Rename"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Delete"), 7, "", "Delete"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"History"), 8, "", "History"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Incoming Links"), 9, "", "Inc Links"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add"), 10, "", "Add"));
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            //not supported in oracle.
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Lists"), 13, "", "Lists"));
        }
         
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"All Pages"),11,"","All Pages"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Search"), 12, "", "Search"));
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Back"))
        {
            back_Click();
        }
        else if (__dummyScrutVar0.equals("Forward"))
        {
            forward_Click();
        }
        else if (__dummyScrutVar0.equals("Setup"))
        {
            setup_Click();
        }
        else if (__dummyScrutVar0.equals("Home"))
        {
            home_Click();
        }
        else if (__dummyScrutVar0.equals("Edit"))
        {
            edit_Click();
        }
        else if (__dummyScrutVar0.equals("Print"))
        {
            print_Click();
        }
        else if (__dummyScrutVar0.equals("Rename"))
        {
            rename_Click();
        }
        else if (__dummyScrutVar0.equals("Delete"))
        {
            delete_Click();
        }
        else if (__dummyScrutVar0.equals("History"))
        {
            history_Click();
        }
        else if (__dummyScrutVar0.equals("Inc Links"))
        {
            inc_Link_Click();
        }
        else if (__dummyScrutVar0.equals("Add"))
        {
            add_Click();
        }
        else if (__dummyScrutVar0.equals("Lists"))
        {
            lists_Click();
        }
        else if (__dummyScrutVar0.equals("Search"))
        {
            search_Click();
        }
                     
    }

    private void back_Click() throws Exception {
        if (historyNavBack < historyNav.Count - 1)
        {
            historyNavBack++;
        }
         
        navToHistory();
    }

    //if(historyNav.Count<2) {//should always be 1 or greater
    //  MsgBox.Show(this,"No more history");
    //  return;
    //}
    //string pageName=historyNav[historyNav.Count-2];//-1 is the last/current page.
    //if(pageName.StartsWith("wiki:")) {
    //  pageName=pageName.Substring(5);
    //  WikiPage wpage=WikiPages.GetByTitle(pageName);
    //  if(wpage==null) {
    //    MessageBox.Show("'"+historyNav[historyNav.Count-2]+"' page does not exist.");//very rare
    //    return;
    //  }
    //  historyNav.RemoveAt(historyNav.Count-1);//remove the current page from history
    //  LoadWikiPage(pageName);//because it's a duplicate, it won't add it again to the list.
    //}
    //else if(pageName.StartsWith("http://")) {//www
    //  //historyNav.RemoveAt(historyNav.Count-1);//remove the current page from history
    //  //no need to set the text because the Navigating event will fire and take care of that.
    //  webBrowserWiki.Navigate(pageName);//adds new page to history
    //}
    //else {
    //  //?
    //}
    private void forward_Click() throws Exception {
        if (historyNavBack > 0)
        {
            historyNavBack--;
        }
         
        navToHistory();
    }

    /**
    * Loads page from history based on historyCurIndex.
    */
    private void navToHistory() throws Exception {
        if (historyNavBack < 0 || historyNavBack > historyNav.Count - 1)
        {
            //This should never happen.
            MsgBox.show(this,"Invalid history index.");
            return ;
        }
         
        String pageName = historyNav[historyNav.Count - (1 + historyNavBack)];
        //-1 is the last/current page.
        if (pageName.StartsWith("wiki:"))
        {
            pageName = pageName.Substring(5);
            WikiPage wpage = WikiPages.getByTitle(pageName);
            if (wpage == null)
            {
                MessageBox.Show("'" + historyNav[historyNav.Count - (1 + historyNavBack)] + "' page does not exist.");
                return ;
            }
             
            //very rare
            //historyNavBack--;//no need to decrement since this is only called from Back_Click and Forward_Click and the appropriate adjustment to this index happens there
            loadWikiPage(pageName);
        }
        else //because it's a duplicate, it won't add it again to the list.
        if (pageName.StartsWith("http://"))
        {
            //www
            //no need to set the text because the Navigating event will fire and take care of that.
            webBrowserWiki.Navigate(pageName);
        }
        else
        {
        }  
    }

    //?
    private void setup_Click() throws Exception {
        FormWikiSetup FormWS = new FormWikiSetup();
        FormWS.ShowDialog();
        if (FormWS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (WikiPageCur == null)
        {
            return ;
        }
         
        //if browsing the WWW
        historyNavBack--;
        //We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
        loadWikiPage(WikiPageCur.PageTitle);
    }

    private void home_Click() throws Exception {
        historyNavBack--;
        //We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
        loadWikiPage("Home");
    }

    //TODO later:replace with dynamic PrefC.Getstring(PrefName.WikiHomePage)
    private void edit_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        FormWikiEdit FormWE = new FormWikiEdit();
        FormWE.WikiPageCur = WikiPageCur;
        FormWE.OwnerForm = this;
        FormWE.Show();
    }

    private void print_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        webBrowserWiki.ShowPrintDialog();
    }

    private void rename_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        FormWikiRename FormWR = new FormWikiRename();
        FormWR.PageTitle = WikiPageCur.PageTitle;
        FormWR.ShowDialog();
        if (FormWR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        WikiPages.rename(WikiPageCur,FormWR.PageTitle);
        historyNav[historyNav.Count - (1 + historyNavBack)] = "wiki:" + FormWR.PageTitle;
        //keep history updated, do not decrement historyNavBack, stay at the same index in history
        //historyNavBack--;//no need to decrement history counter since we are loading the same page, just with a different name, historyNav was edited above with new name
        loadWikiPage(FormWR.PageTitle);
    }

    private void delete_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        if (StringSupport.equals(WikiPageCur.PageTitle, "Home"))
        {
            MsgBox.show(this,"Cannot delete homepage.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this wiki page?  It will still be available from the Search window if needed."))
        {
            return ;
        }
         
        WikiPages.delete(WikiPageCur.PageTitle);
        //historyNavBack--;//do not decrement, load will consider this a branch and put "wiki:Home" in place of the deleted page and remove "forward" history.
        loadWikiPage("Home");
    }

    private void history_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        FormWikiHistory FormWH = new FormWikiHistory();
        FormWH.PageTitleCur = WikiPageCur.PageTitle;
        FormWH.ShowDialog();
        //historyNavBack--;//no need to decrement since we are loading the same page, possibly a different version, but the same PageTitle
        loadWikiPage(FormWH.PageTitleCur);
    }

    //if(FormWH.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //Nothing to do here.
    private void inc_Link_Click() throws Exception {
        if (WikiPageCur == null)
        {
            return ;
        }
         
        FormWikiIncomingLinks FormWIC = new FormWikiIncomingLinks();
        FormWIC.PageTitleCur = WikiPageCur.PageTitle;
        FormWIC.ShowDialog();
        if (FormWIC.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        historyNavBack--;
        //We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
        loadWikiPage(FormWIC.JumpToPage.PageTitle);
    }

    private void add_Click() throws Exception {
        FormWikiRename FormWR = new FormWikiRename();
        FormWR.ShowDialog();
        if (FormWR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        FormWikiEdit FormWE = new FormWikiEdit();
        FormWE.WikiPageCur = new WikiPage();
        FormWE.WikiPageCur.setIsNew(true);
        FormWE.WikiPageCur.PageTitle = FormWR.PageTitle;
        //link back
        FormWE.WikiPageCur.PageContent = "[[" + WikiPageCur.PageTitle + "]]\r\n" + "<h1>" + FormWR.PageTitle + "</h1>\r\n";
        //page title
        FormWE.OwnerForm = this;
        FormWE.Show();
    }

    /*No longer used
    		private void All_Pages_Click() {
    			FormWikiAllPages FormWAP=new FormWikiAllPages();
    			FormWAP.ShowDialog();
    			if(FormWAP.DialogResult!=DialogResult.OK) {
    			  return;
    			}
    			historyNavBack--;//We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
    			LoadWikiPage(FormWAP.SelectedWikiPage.PageTitle);
    		}*/
    private void lists_Click() throws Exception {
        FormWikiLists FormWL = new FormWikiLists();
        FormWL.ShowDialog();
    }

    private void search_Click() throws Exception {
        FormWikiSearch FormWS = new FormWikiSearch();
        FormWS.ShowDialog();
        if (FormWS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (StringSupport.equals(FormWS.wikiPageTitleSelected, ""))
        {
            return ;
        }
         
        historyNavBack--;
        //We have to decrement historyNavBack to tell whether or not we need to branch our page history
        loadWikiPage(FormWS.wikiPageTitleSelected);
    }

    private void webBrowserWiki_Navigating(Object sender, WebBrowserNavigatingEventArgs e) throws Exception {
        //For debugging, we need to remember that the following happens when you click on an internal link:
        //1. This method fires. url includes 'wiki:'
        //2. This causes LoadWikiPage method to fire.  It loads the document text.
        //3. Which causes this method to fire again.  url is "about:blank".
        //This doesn't seem to be a problem.  We wrote it so that it won't go into an infinite loop, but it's something to be aware of.
        if (StringSupport.equals(e.Url.ToString(), "about:blank"))
        {
        }
        else //webBrowserWiki.DocumentText was set externally. We want to ignore this situation.
        if (e.Url.ToString().StartsWith("about:"))
        {
            //malformed URL.
            e.Cancel = true;
            return ;
        }
        else if (e.Url.ToString().StartsWith("wiki:"))
        {
            //user clicked on an internal link
            historyNavBack--;
            //We have to decrement historyNavBack to tell whether or not we need to branch our page history or add to page history
            LoadWikiPage(e.Url.ToString().Substring(5));
            e.Cancel = true;
            return ;
        }
        else if (e.Url.ToString().Contains("wikifile:"))
        {
            String fileName = e.Url.ToString().Substring(e.Url.ToString().LastIndexOf("wikifile:") + 9).Replace("/", "\\");
            if (!File.Exists(fileName))
            {
                MessageBox.Show(Lan.g(this,"File does not exist: ") + fileName);
                e.Cancel = true;
                return ;
            }
             
            try
            {
                System.Diagnostics.Process.Start(fileName);
            }
            catch (Exception ex)
            {
            }

            e.Cancel = true;
            return ;
        }
        else if (e.Url.ToString().Contains("folder:"))
        {
            String folderName = e.Url.ToString().Substring(e.Url.ToString().LastIndexOf("folder:") + 7).Replace("/", "\\");
            if (!Directory.Exists(folderName))
            {
                MessageBox.Show(Lan.g(this,"Folder does not exist: ") + folderName);
                e.Cancel = true;
                return ;
            }
             
            try
            {
                System.Diagnostics.Process.Start(folderName);
            }
            catch (Exception ex)
            {
            }

            e.Cancel = true;
            return ;
        }
        else if (e.Url.ToString().StartsWith("http"))
        {
            try
            {
                //navigating outside of wiki by clicking a link
                System.Diagnostics.Process.Start(e.Url.ToString());
            }
            catch (Exception ex)
            {
            }

            e.Cancel = true;
            return ;
        }
              
    }

    //Stops the page from loading in FormWiki.
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormWiki.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.labelStatus = new System.Windows.Forms.Label();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Left.gif");
        this.imageListMain.Images.SetKeyName(1, "Right.gif");
        this.imageListMain.Images.SetKeyName(2, "Manage22.gif");
        this.imageListMain.Images.SetKeyName(3, "home.gif");
        this.imageListMain.Images.SetKeyName(4, "editPencil.gif");
        this.imageListMain.Images.SetKeyName(5, "print.gif");
        this.imageListMain.Images.SetKeyName(6, "rename.gif");
        this.imageListMain.Images.SetKeyName(7, "deleteX.gif");
        this.imageListMain.Images.SetKeyName(8, "history.gif");
        this.imageListMain.Images.SetKeyName(9, "incoming.gif");
        this.imageListMain.Images.SetKeyName(10, "Add.gif");
        this.imageListMain.Images.SetKeyName(11, "allpages.gif");
        this.imageListMain.Images.SetKeyName(12, "search.gif");
        this.imageListMain.Images.SetKeyName(13, "WikiLists.png");
        //
        // labelStatus
        //
        this.labelStatus.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.labelStatus.Location = new System.Drawing.Point(-3, 686);
        this.labelStatus.Name = "labelStatus";
        this.labelStatus.Size = new System.Drawing.Size(947, 18);
        this.labelStatus.TabIndex = 73;
        this.labelStatus.Text = "Status Bar";
        this.labelStatus.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(944, 25);
        this.ToolBarMain.TabIndex = 72;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.Location = new System.Drawing.Point(0, 28);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(944, 657);
        this.webBrowserWiki.TabIndex = 0;
        this.webBrowserWiki.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowserWiki_Navigating);
        //
        // FormWiki
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(944, 704);
        this.Controls.Add(this.labelStatus);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.webBrowserWiki);
        this.Name = "FormWiki";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Wiki";
        this.Load += new System.EventHandler(this.FormWiki_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.Label labelStatus = new System.Windows.Forms.Label();
}


