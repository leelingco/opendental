//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormWiki;
import OpenDental.FormWikiAllPages;
import OpenDental.FormWikiExternalLink;
import OpenDental.FormWikiFileFolder;
import OpenDental.FormWikiImages;
import OpenDental.FormWikiTableEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiLists;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormWikiEdit;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;

public class FormWikiEdit  extends Form 
{

    public WikiPage WikiPageCur;
    /**
    * Need a reference to the form where this was launched from so that we can tell it to refresh later.
    */
    public FormWiki OwnerForm;
    private boolean closingIsSave = new boolean();
    //used to differentiate what action caused the form to close.
    private String AggregateContent = new String();
    private int ScrollTop = new int();
    public FormWikiEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
        this.textContent.TextChanged += new System.EventHandler(this.textContent_TextChanged);
    }

    private void formWikiEdit_Load(Object sender, EventArgs e) throws Exception {
        resizeControls();
        //LayoutToolBar();
        Text = "Wiki Edit - " + WikiPageCur.PageTitle;
        textContent.setText(WikiPageCur.PageContent);
        textContent.setSelectionStart(textContent.getText().Length);
        textContent.setSelectionLength(0);
        String[] strArray = new String[1];
        strArray[0] = "\r\n";
        int rowCount = textContent.getText().Split(strArray, StringSplitOptions.None).Length;
        fillNumbers(rowCount);
        //RefreshHtml();
        textContent.Focus();
    }

    /**
    * Because FormWikiAllPages is no longer modal, this is necessary to be able to tell FormWikiEdit to refresh with inserted data.
    */
    public void refreshPage(WikiPage selectedWikiPage) throws Exception {
        int tempStart = textContent.getSelectionStart();
        if (selectedWikiPage == null)
        {
            textContent.paste("[[]]");
            textContent.setSelectionStart(tempStart + 2);
        }
        else
        {
            textContent.paste(("[[" + selectedWikiPage.PageTitle + "]]"));
            textContent.setSelectionStart(tempStart + selectedWikiPage.PageTitle.Length + 4);
        } 
        textContent.Focus();
        textContent.setSelectionLength(0);
    }

    private void fillNumbers(int rowCount) throws Exception {
        StringBuilder strb = new StringBuilder();
        for (int i = 1;i < rowCount + 10;i++)
        {
            strb.Append(i.ToString());
            strb.Append("\r\n");
        }
        textNumbers.Text = strb.ToString();
    }

    private void refreshHtml() throws Exception {
        webBrowserWiki.AllowNavigation = true;
        try
        {
            //remember scroll
            if (webBrowserWiki.Document != null)
            {
                ScrollTop = webBrowserWiki.Document.GetElementsByTagName("HTML")[0].ScrollTop;
            }
             
            webBrowserWiki.DocumentText = WikiPages.translateToXhtml(textContent.getText(),true);
        }
        catch (Exception ex)
        {
        }
    
    }

    //don't refresh
    //textContent.Focus();//this was causing a bug where it would re-highlight text after a backspace.
    private void webBrowserWiki_DocumentCompleted(Object sender, WebBrowserDocumentCompletedEventArgs e) throws Exception {
        webBrowserWiki.Document.GetElementsByTagName("HTML")[0].ScrollTop = ScrollTop;
        textContent.Focus();
    }

    private void resizeControls() throws Exception {
        int topborder = 53;
        //textNumbers resize
        textNumbers.Top = topborder;
        textNumbers.Height = ClientSize.Height - topborder;
        //text resize
        textContent.Top = topborder;
        textContent.Height = ClientSize.Height - topborder;
        textContent.Left = 32;
        textContent.Width = ClientSize.Width / 2 - 2 - textContent.Left;
        //Browser resize
        webBrowserWiki.Top = topborder;
        webBrowserWiki.Height = ClientSize.Height - topborder;
        webBrowserWiki.Left = ClientSize.Width / 2 + 2;
        webBrowserWiki.Width = ClientSize.Width / 2 - 2;
        //Toolbar resize
        ToolBarMain.Width = ClientSize.Width;
        toolBar2.Width = ClientSize.Width;
        ToolBarMain.Invalidate();
        toolBar2.Invalidate();
        layoutToolBars();
    }

    //Button move
    //butRefresh.Left=ClientSize.Width/2+2;
    private void formWikiEdit_SizeChanged(Object sender, EventArgs e) throws Exception {
        resizeControls();
    }

    private void textContent_TextChanged(Object sender, EventArgs e) throws Exception {
        refreshHtml();
    }

    private void textContent_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        //this doesn't always fire, which is good because the user can still use the arrow keys to move around.
        //look through all tables:
        MatchCollection matches = Regex.Matches(textContent.getText(), "\\{\\|(.+?)\\|\\}", RegexOptions.Singleline);
        for (Object __dummyForeachVar0 : matches)
        {
            Match match = (Match)__dummyForeachVar0;
            if (textContent.getSelectionStart() > match.Index && textContent.getSelectionStart() < match.Index + match.Length)
            {
                e.Handled = true;
                MsgBox.show(this,"Direct editing of tables is not allowed here.  Use the table button or double click to edit.");
                return ;
            }
             
        }
    }

    private void textContent_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        int idx = textContent.GetCharIndexFromPosition(e.Location);
        tableOrDoubleClick(idx);
    }

    //we don't care what this returns because we don't want to do anything else.
    /**
    * This is called both when a user double clicks anywhere in the edit box, or when the click the Table button in the toolbar.  This ONLY handles popping up an edit window for an existing table.  If the cursor was not in an existing table, then this returns false.  After that, the behavior in the two areas differs.  Returns true if it popped up.
    */
    private boolean tableOrDoubleClick(int charIdx) throws Exception {
        //there is some code clutter in this method from when we used TableViews.  It seems harmless, but can be removed whenever.
        MatchCollection matches = new MatchCollection();
        //Tables-------------------------------------------------------------------------------
        String strTableClicked = "";
        String strTableFirst = "";
        int countTable = 0;
        matches = Regex.Matches(textContent.getText(), "\\{\\|(.+?)\\|\\}", RegexOptions.Singleline);
        countTable = matches.Count;
        for (int i = 0;i < matches.Count;i++)
        {
            if (i == 0)
            {
                strTableFirst = matches[i].Value;
            }
             
            if (charIdx > matches[i].Index && charIdx < matches[i].Index + matches[i].Length)
            {
                strTableClicked = matches[i].Value;
            }
             
        }
        //handle the clicks----------------------------------------------------------------------------
        String strTableLoad = "";
        if (!StringSupport.equals(strTableClicked, ""))
        {
            //clicked in a table
            strTableLoad = strTableClicked;
        }
        else
        {
            return false;
        } 
        //did not click inside a table
        textContent.setSelectionLength(0);
        //otherwise we get an annoying highlight
        FormWikiTableEdit formT = new FormWikiTableEdit();
        formT.Markup = strTableLoad;
        formT.CountTablesInPage = countTable;
        formT.IsNew = false;
        formT.ShowDialog();
        if (formT.DialogResult != DialogResult.OK)
        {
            return true;
        }
         
        if (formT.Markup == null)
        {
            //indicates delete
            textContent.setText(textContent.getText().Replace(strTableLoad, ""));
            textContent.setSelectionLength(0);
            return true;
        }
         
        textContent.setText(textContent.getText().Replace(strTableLoad, formT.Markup));
        textContent.setSelectionLength(0);
        return true;
    }

    private void webBrowserWiki_Navigated(Object sender, WebBrowserNavigatedEventArgs e) throws Exception {
        webBrowserWiki.AllowNavigation = false;
    }

    private void layoutToolBars() throws Exception {
        ToolBarMain.getButtons().Clear();
        //Refresh no longer needed.
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Save"), 1, "", "Save"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Cancel"), 2, "", "Cancel"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Int Link"), 7, "", "Int Link"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"File"), 7, "", "File Link"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Folder"), 7, "", "Folder Link"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Ext Link"), 8, "", "Ext Link"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Heading1"), 9, "", "H1"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Heading2"), 10, "", "H2"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Heading3"), 11, "", "H3"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Table"), 15, "", "Table"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Image"), 16, "", "Image"));
        toolBar2.getButtons().Clear();
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Cut"), 3, "", "Cut"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Copy"), 4, "", "Copy"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Paste"), 5, "", "Paste"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Undo"), 6, "", "Undo"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Bold"), 12, "", "Bold"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Italic"), 13, "", "Italic"));
        toolBar2.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Color"), 14, "", "Color"));
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Save"))
        {
            save_Click();
        }
        else if (__dummyScrutVar0.equals("Cancel"))
        {
            cancel_Click();
        }
        else if (__dummyScrutVar0.equals("Int Link"))
        {
            int_Link_Click();
        }
        else if (__dummyScrutVar0.equals("File Link"))
        {
            file_Link_Click();
        }
        else if (__dummyScrutVar0.equals("Folder Link"))
        {
            folder_Link_Click();
        }
        else if (__dummyScrutVar0.equals("Ext Link"))
        {
            ext_Link_Click();
        }
        else if (__dummyScrutVar0.equals("H1"))
        {
            h1_Click();
        }
        else if (__dummyScrutVar0.equals("H2"))
        {
            h2_Click();
        }
        else if (__dummyScrutVar0.equals("H3"))
        {
            h3_Click();
        }
        else if (__dummyScrutVar0.equals("Table"))
        {
            table_Click();
        }
        else if (__dummyScrutVar0.equals("Image"))
        {
            image_Click();
        }
                   
    }

    private void toolBar2_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar1 = e.getButton().getTag().ToString();
        if (__dummyScrutVar1.equals("Cut"))
        {
            cut_Click();
        }
        else if (__dummyScrutVar1.equals("Copy"))
        {
            copy_Click();
        }
        else if (__dummyScrutVar1.equals("Paste"))
        {
            paste_Click();
        }
        else if (__dummyScrutVar1.equals("Undo"))
        {
            undo_Click();
        }
        else if (__dummyScrutVar1.equals("Bold"))
        {
            bold_Click();
        }
        else if (__dummyScrutVar1.equals("Italic"))
        {
            italic_Click();
        }
        else if (__dummyScrutVar1.equals("Color"))
        {
            color_Click();
        }
               
    }

    private void menuItemCut_Click(Object sender, EventArgs e) throws Exception {
        cut_Click();
    }

    private void menuItemCopy_Click(Object sender, EventArgs e) throws Exception {
        copy_Click();
    }

    private void menuItemPaste_Click(Object sender, EventArgs e) throws Exception {
        paste_Click();
    }

    private void menuItemUndo_Click(Object sender, EventArgs e) throws Exception {
        undo_Click();
    }

    private void save_Click() throws Exception {
        if (!validateWikiPage(true))
        {
            return ;
        }
         
        WikiPage wikiPageDB = WikiPages.getByTitle(WikiPageCur.PageTitle);
        if (wikiPageDB != null && WikiPageCur.DateTimeSaved < wikiPageDB.DateTimeSaved)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This page has been modified and saved since it was opened on this computer.  Save anyway?"))
            {
                return ;
            }
             
        }
         
        WikiPageCur.PageContent = textContent.getText();
        //Fix case on all internal links
        MatchCollection matches = Regex.Matches(WikiPageCur.PageContent, "\\[\\[.+?\\]\\]");
        for (Object __dummyForeachVar1 : matches)
        {
            Match match = (Match)__dummyForeachVar1;
            if (match.Value.StartsWith("[[img:") || match.Value.StartsWith("[[keywords:") || match.Value.StartsWith("[[file:") || match.Value.StartsWith("[[folder:") || match.Value.StartsWith("[[list:") || match.Value.StartsWith("[[color:"))
            {
                continue;
            }
             
            //we don't care about these.  We are only checking internal links
            //Get the pagename of the link
            String oldTitle = match.Value.Substring(2, match.Value.Length - 4);
            String newTitle = WikiPages.getTitle(oldTitle);
            if (StringSupport.equals(oldTitle, newTitle))
            {
                continue;
            }
             
            //casing matches
            if (StringSupport.equals(newTitle, ""))
            {
                continue;
            }
             
            //broken link, leave alone
            WikiPageCur.PageContent = WikiPageCur.PageContent.Replace("[[" + oldTitle + "]]", "[[" + newTitle + "]]");
        }
        WikiPageCur.UserNum = Security.getCurUser().UserNum;
        Regex regex = new Regex("\\[\\[(keywords:).+?\\]\\]");
        //only grab first match
        Match m = regex.Match(textContent.getText());
        WikiPageCur.KeyWords = m.Value.Replace("[[keywords:", "").TrimEnd(']');
        //will be empty string if no match
        WikiPages.insertAndArchive(WikiPageCur);
        FormWiki formWiki = (FormWiki)this.OwnerForm;
        if (formWiki != null && !formWiki.IsDisposed)
        {
            formWiki.refreshPage(WikiPageCur.PageTitle);
        }
         
        closingIsSave = true;
        Close();
    }

    //should be dialog result??
    private void cancel_Click() throws Exception {
        Close();
    }

    private void cut_Click() throws Exception {
        textContent.cut();
        textContent.Focus();
    }

    //RefreshHtml();
    private void copy_Click() throws Exception {
        textContent.copy();
        textContent.Focus();
    }

    //RefreshHtml();
    private void paste_Click() throws Exception {
        textContent.paste();
        textContent.Focus();
    }

    //RefreshHtml();
    private void undo_Click() throws Exception {
        textContent.undo();
        textContent.Focus();
    }

    //RefreshHtml();
    private void int_Link_Click() throws Exception {
        FormWikiAllPages FormWAPSelect = new FormWikiAllPages();
        FormWAPSelect.OwnerForm = this;
        FormWAPSelect.Show();
    }

    private void file_Link_Click() throws Exception {
        FormWikiFileFolder formWFF = new FormWikiFileFolder();
        formWFF.ShowDialog();
        if (formWFF.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textContent.paste("[[file:" + formWFF.SelectedLink + "]]");
    }

    //RefreshHtml();
    private void folder_Link_Click() throws Exception {
        FormWikiFileFolder formWFF = new FormWikiFileFolder();
        formWFF.IsFolderMode = true;
        formWFF.ShowDialog();
        if (formWFF.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textContent.paste("[[folder:" + formWFF.SelectedLink + "]]");
    }

    //RefreshHtml();
    private void ext_Link_Click() throws Exception {
        FormWikiExternalLink FormWEL = new FormWikiExternalLink();
        FormWEL.ShowDialog();
        int tempStart = textContent.getSelectionStart();
        if (FormWEL.DialogResult != DialogResult.OK)
        {
            textContent.paste("<a href=\"\"></a>");
            textContent.setSelectionStart(tempStart + 11);
            textContent.setSelectionLength(0);
            return ;
        }
         
        textContent.paste("<a href=\"" + FormWEL.URL + "\">" + FormWEL.DisplayText + "</a>");
        textContent.Focus();
    }

    //RefreshHtml();
    private void h1_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "<h1>" + textContent.getSelectedText() + "</h1>";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 4 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    private void h2_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "<h2>" + textContent.getSelectedText() + "</h2>";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 4 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    private void h3_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "<h3>" + textContent.getSelectedText() + "</h3>";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 4 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    private void bold_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "<b>" + textContent.getSelectedText() + "</b>";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 3 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    private void italic_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "<i>" + textContent.getSelectedText() + "</i>";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 3 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    private void color_Click() throws Exception {
        int tempStart = textContent.getSelectionStart();
        int tempLength = textContent.getSelectionLength();
        String s = "[[color:red|" + textContent.getSelectedText() + "]]";
        textContent.paste(s);
        textContent.Focus();
        if (tempLength == 0)
        {
            //nothing selected, place cursor in middle of new tags
            textContent.setSelectionStart(tempStart + 12 + tempLength);
        }
        else
        {
            textContent.setSelectionStart(tempStart + s.Length);
            textContent.setSelectionLength(0);
        } 
    }

    //RefreshHtml();
    /**
    * Works for a new table and for an existing table.
    */
    private void table_Click() throws Exception {
        int idx = textContent.getSelectionStart();
        if (tableOrDoubleClick(idx))
        {
            return ;
        }
         
        //so it was already handled with an edit table dialog
        //User did not click inside a table, so they must want to add a new table.
        FormWikiTableEdit FormWTE = new FormWikiTableEdit();
        FormWTE.Markup = "{|\r\n" + 
        "!Width=\"100\"|Heading1!!Width=\"100\"|Heading2!!Width=\"100\"|Heading3\r\n" + 
        "|-\r\n" + 
        "|||||\r\n" + 
        "|-\r\n" + 
        "|||||\r\n" + 
        "|}";
        FormWTE.IsNew = true;
        FormWTE.ShowDialog();
        if (FormWTE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textContent.setSelectionLength(0);
        textContent.paste(FormWTE.Markup);
        textContent.setSelectionLength(0);
        textContent.Focus();
    }

    private void image_Click() throws Exception {
        FormWikiImages FormWI = new FormWikiImages();
        FormWI.ShowDialog();
        if (FormWI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textContent.paste("[[img:" + FormWI.SelectedImageName + "]]");
    }

    //webBrowserWiki.AllowNavigation=true;
    //RefreshHtml();
    /**
    * Validates content, and keywords.  isForSaving can be false if just validating for refresh.
    */
    private boolean validateWikiPage(boolean isForSaving) throws Exception {
        //xml validation----------------------------------------------------------------------------------------------------
        String s = textContent.getText();
        //"<",">", and "&"-----------------------------------------------------------------------------------------------------------
        s = s.Replace("&", "&amp;");
        s = s.Replace("&amp;<", "&lt;");
        //because "&" was changed to "&amp;" in the line above.
        s = s.Replace("&amp;>", "&gt;");
        //because "&" was changed to "&amp;" in the line above.
        s = "<body>" + s + "</body>";
        XmlDocument doc = new XmlDocument();
        StringReader reader = new StringReader(s);
        try
        {
            doc.Load(reader);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }

        try
        {
            //we do it this way to skip checking the main node itself since it's a dummy node.
            ValidateNodes(doc.DocumentElement.ChildNodes);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }

        //Cannot have CR within tag definition---------------------------------------------------------------------------------
        //(?<!&) means only match strings that do not start with an '&'. This is so we can continue to use '&' as an escape character for '<'.
        //<.*?> means anything as short as possible that is contained inside a tag
        MatchCollection tagMatches = Regex.Matches(textContent.getText(), "(?<!&)<.*?>", RegexOptions.Singleline);
        for (int i = 0;i < tagMatches.Count;i++)
        {
            if (tagMatches[i].ToString().Contains("\r\n"))
            {
                MessageBox.Show(Lan.g(this,"Tag definitions cannot contain a return line: ") + tagMatches[i].Value.Replace("\r\n", ""));
                return false;
            }
             
        }
        //image validation-----------------------------------------------------------------------------------------------------
        String wikiImagePath = WikiPages.getWikiPath();
        //this also creates folder if it's missing.
        MatchCollection matches = Regex.Matches(textContent.getText(), "\\[\\[(img:).*?\\]\\]");
        // [[img:myimage.jpg]]
        if (matches.Count > 0 && !PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Cannot use images in wiki if storing images in database.");
            return false;
        }
         
        if (isForSaving)
        {
            for (int i = 0;i < matches.Count;i++)
            {
                String imgPath = CodeBase.ODFileUtils.CombinePaths(wikiImagePath, matches[i].Value.Substring(6).Trim(']'));
                if (!System.IO.File.Exists(imgPath))
                {
                    MessageBox.Show(Lan.g(this,"Not allowed to save because image does not exist: ") + imgPath);
                    return false;
                }
                 
            }
        }
         
        //List validation-----------------------------------------------------------------------------------------------------
        matches = Regex.Matches(textContent.getText(), "\\[\\[(list:).*?\\]\\]");
        for (Object __dummyForeachVar2 : matches)
        {
            // [[list:CustomList]]
            Match match = (Match)__dummyForeachVar2;
            if (!WikiLists.CheckExists(match.Value.Substring(7).Trim(']')))
            {
                MessageBox.Show(Lan.g(this,"Wiki list does not exist in database") + " : " + match.Value.Substring(7).Trim(']'));
                return false;
            }
             
        }
        //spacing around bullets-----------------------------------------------------------------------------------------------
        String[] lines = textContent.getText().Split(new String[]{ "\r\n" }, StringSplitOptions.None);
        for (int i = 0;i < lines.Length;i++)
        {
            if (lines[i].Trim().StartsWith("*"))
            {
                if (!lines[i].StartsWith("*"))
                {
                    MsgBox.show(this,"Stars used for lists may not have a space before them.");
                    return false;
                }
                 
                if (lines[i].Trim().StartsWith("* "))
                {
                    MsgBox.show(this,"Stars used for lists may not have a space after them.");
                    return false;
                }
                 
            }
             
            if (lines[i].Trim().StartsWith("#"))
            {
                if (!lines[i].StartsWith("#"))
                {
                    MsgBox.show(this,"Hashes used for lists may not have a space before them.");
                    return false;
                }
                 
                if (lines[i].Trim().StartsWith("# "))
                {
                    MsgBox.show(this,"Hashes used for lists may not have a space after them.");
                    return false;
                }
                 
            }
             
        }
        //Invalid characters inside of various tags--------------------------------------------
        matches = Regex.Matches(textContent.getText(), "\\[\\[.*?\\]\\]");
        for (Object __dummyForeachVar3 : matches)
        {
            Match match = (Match)__dummyForeachVar3;
            if (match.Value.Contains("\"") && !match.Value.StartsWith("[[color:"))
            {
                //allow colored text to have quotes.
                MessageBox.Show(Lan.g(this,"Link cannot contain double quotes: ") + match.Value);
                return false;
            }
             
            //This is not needed because our regex doesn't even catch them if the span a line break.  It's just interpreted as plain text.
            //if(match.Value.Contains("\r") || match.Value.Contains("\n")) {
            //	MessageBox.Show(Lan.g(this,"Link cannot contain carriage returns: ")+match.Value);
            //	return false;
            //}
            if (match.Value.StartsWith("[[img:") || match.Value.StartsWith("[[keywords:") || match.Value.StartsWith("[[file:") || match.Value.StartsWith("[[folder:") || match.Value.StartsWith("[[list:") || match.Value.StartsWith("[[color:"))
            {
            }
            else
            {
                //other tags
                if (match.Value.Contains("|"))
                {
                    MessageBox.Show(Lan.g(this,"Internal link cannot contain a pipe character:") + match.Value);
                    return false;
                }
                 
            } 
        }
        //Table markup rigorously formatted----------------------------------------------------------------------
        //{|
        //!Width="100"|Column Heading 1!!Width="150"|Column Heading 2!!Width="75"|Column Heading 3
        //|-
        //|Cell 1||Cell 2||Cell 3
        //|-
        //|Cell A||Cell B||Cell C
        //|}
        //Although this is rarely needed, it might still come in handy in certain cases, like paste, or when user doesn't add the |} until later, and other hacks.
        matches = Regex.Matches(s, "\\{\\|(.+?)\\|\\}", RegexOptions.Singleline);
        for (Object __dummyForeachVar4 : matches)
        {
            Match match = (Match)__dummyForeachVar4;
            lines = match.Value.Split(new String[]{ "{|\r\n", "\r\n|-\r\n", "\r\n|}" }, StringSplitOptions.RemoveEmptyEntries);
            if (!match.Value.StartsWith("{|"))
            {
                MsgBox.show(this,"The first line of a table markup section must be exactly {|, with no additional characters.");
                return false;
            }
             
            if (!lines[0].StartsWith("!"))
            {
                MsgBox.show(this,"The second line of a table markup section must start with ! to indicate column headers.");
                return false;
            }
             
            if (lines[0].StartsWith("! "))
            {
                MsgBox.show(this,"In the table, at line 2, there cannot be a space after the first !");
                return false;
            }
             
            String[] cells = lines[0].Substring(1).Split(new String[]{ "!!" }, StringSplitOptions.None);
            for (int c = 0;c < cells.Length;c++)
            {
                //this also strips off the leading !
                if (!Regex.IsMatch(cells[c], "^(Width=\")\\d+\"\\|"))
                {
                    //e.g. Width="90"|
                    MsgBox.show(this,"In the table markup, each header must be formatted like this: Width=\"#\"|...");
                    return false;
                }
                 
            }
            for (int i = 1;i < lines.Length;i++)
            {
                //loop through the lines after the header
                if (!lines[i].StartsWith("|"))
                {
                    MessageBox.Show(Lan.g(this,"Table rows must start with |.  At line ") + (i + 1).ToString() + Lan.g(this,", this was found instead:") + lines[i]);
                    return false;
                }
                 
            }
            //if(lines[i].StartsWith("| ")) {
            //	MessageBox.Show(Lan.g(this,"In the table, at line ")+(i+1).ToString()+Lan.g(this,", there cannot be a space after the first |."));
            //	return false;
            //}
            //lines[i].in
            //I guess we don't really care what they put in a row.  We can just interpret garbage as a single cell.
            if (!match.Value.EndsWith("\r\n|}"))
            {
                MsgBox.show(this,"The last line of a table markup section must be exactly |}, with no additional characters.");
                return false;
            }
             
        }
        return true;
    }

    //this doesn't work since the match stops after |}.
    /**
    * Recursive.
    */
    private void validateNodes(XmlNodeList nodes) throws Exception {
        for (Object __dummyForeachVar5 : nodes)
        {
            XmlNode node = (XmlNode)__dummyForeachVar5;
            if (node.NodeType == XmlNodeType.Comment)
            {
                throw new ApplicationException("The comment tag <!-- --> " + node.Name + " is not allowed.");
            }
             
            if (node.NodeType == XmlNodeType.ProcessingInstruction)
            {
                throw new ApplicationException("The XML processing instruction <?xml ?> " + node.Name + " is not allowed.");
            }
             
            if (node.NodeType == XmlNodeType.XmlDeclaration)
            {
                throw new ApplicationException("XML declarations like <?xml ?> " + node.Name + "> are not allowed.");
            }
             
            if (node.NodeType != XmlNodeType.Element)
            {
                continue;
            }
             
            //check child nodes for nested duplicate
            Name __dummyScrutVar2 = node.Name;
            if (__dummyScrutVar2.equals("i") || __dummyScrutVar2.equals("b") || __dummyScrutVar2.equals("h1") || __dummyScrutVar2.equals("h2") || __dummyScrutVar2.equals("h3") || __dummyScrutVar2.equals("a"))
            {
                for (int i = 0;i < node.Attributes.Count;i++)
                {
                    //only allowed attribute is href
                    if (!StringSupport.equals(node.Attributes[i].Name, "href"))
                    {
                        throw new ApplicationException(node.Attributes[i].Name + " attribute is not allowed on <a> tag.");
                    }
                     
                    if (node.Attributes[i].InnerText.StartsWith("wiki:"))
                    {
                        throw new ApplicationException("wiki: is not allowed in an <a> tag.  Use [[ ]] instead of <a>.");
                    }
                     
                }
            }
            else if (__dummyScrutVar2.equals("img"))
            {
                throw new ApplicationException("Image tags are not allowed. Instead use [[img: ... ]]");
            }
            else
            {
                throw new ApplicationException("<" + node.Name + "> is not one of the allowed tags. To display as plain text, escape the brackets with ampersands. I.e. \"&<" + node.Name + "&>\"");
            }  
            ValidateNodes(node.ChildNodes);
            ValidateDuplicateNesting(node.Name, node.ChildNodes);
        }
    }

    /**
    * Recursive.
    */
    private void validateDuplicateNesting(String nodeName, XmlNodeList nodes) throws Exception {
        for (Object __dummyForeachVar6 : nodes)
        {
            XmlNode node = (XmlNode)__dummyForeachVar6;
            if (node.NodeType != XmlNodeType.Element)
            {
                continue;
            }
             
            if (StringSupport.equals(nodeName, node.Name))
            {
                throw new ApplicationException("There are multiple <" + node.Name + "> tags nested within each other.  Remove the unneeded tags.");
            }
             
            ValidateDuplicateNesting(nodeName, node.ChildNodes);
        }
    }

    private void formWikiEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //handles both the Cancel button and the user clicking on the x, and also the save button.
        if (closingIsSave)
        {
            return ;
        }
         
        if (!StringSupport.equals(textContent.getText(), WikiPageCur.PageContent))
        {
            //why is this line of code here, why is it important?--Ryan
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Unsaved changes will be lost. Would you like to continue?"))
            {
                e.Cancel = true;
            }
             
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormWikiEdit.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.contextMenuMain = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemCut = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemCopy = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemPaste = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemUndo = new System.Windows.Forms.ToolStripMenuItem();
        this.textNumbers = new System.Windows.Forms.TextBox();
        this.toolBar2 = new OpenDental.UI.ODToolBar();
        this.textContent = new OpenDental.TextBoxWiki();
        this.webBrowserWiki = new System.Windows.Forms.WebBrowser();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.contextMenuMain.SuspendLayout();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "refresh.gif");
        this.imageListMain.Images.SetKeyName(1, "save.gif");
        this.imageListMain.Images.SetKeyName(2, "cancel.gif");
        this.imageListMain.Images.SetKeyName(3, "cut.gif");
        this.imageListMain.Images.SetKeyName(4, "copy.gif");
        this.imageListMain.Images.SetKeyName(5, "paste.gif");
        this.imageListMain.Images.SetKeyName(6, "undo.gif");
        this.imageListMain.Images.SetKeyName(7, "link.gif");
        this.imageListMain.Images.SetKeyName(8, "linkExternal.gif");
        this.imageListMain.Images.SetKeyName(9, "h1.gif");
        this.imageListMain.Images.SetKeyName(10, "h2.gif");
        this.imageListMain.Images.SetKeyName(11, "h3.gif");
        this.imageListMain.Images.SetKeyName(12, "bold.gif");
        this.imageListMain.Images.SetKeyName(13, "italic.gif");
        this.imageListMain.Images.SetKeyName(14, "color.gif");
        this.imageListMain.Images.SetKeyName(15, "table.gif");
        this.imageListMain.Images.SetKeyName(16, "image.gif");
        //
        // contextMenuMain
        //
        this.contextMenuMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemCut, this.menuItemCopy, this.menuItemPaste, this.toolStripMenuItem2, this.menuItemUndo });
        this.contextMenuMain.Name = "contextMenuMain";
        this.contextMenuMain.Size = new System.Drawing.Size(102, 98);
        //
        // menuItemCut
        //
        this.menuItemCut.Name = "menuItemCut";
        this.menuItemCut.Size = new System.Drawing.Size(101, 22);
        this.menuItemCut.Text = "Cut";
        this.menuItemCut.Click += new System.EventHandler(this.menuItemCut_Click);
        //
        // menuItemCopy
        //
        this.menuItemCopy.Name = "menuItemCopy";
        this.menuItemCopy.Size = new System.Drawing.Size(101, 22);
        this.menuItemCopy.Text = "Copy";
        this.menuItemCopy.Click += new System.EventHandler(this.menuItemCopy_Click);
        //
        // menuItemPaste
        //
        this.menuItemPaste.Name = "menuItemPaste";
        this.menuItemPaste.Size = new System.Drawing.Size(101, 22);
        this.menuItemPaste.Text = "Paste";
        this.menuItemPaste.Click += new System.EventHandler(this.menuItemPaste_Click);
        //
        // toolStripMenuItem2
        //
        this.toolStripMenuItem2.Name = "toolStripMenuItem2";
        this.toolStripMenuItem2.Size = new System.Drawing.Size(98, 6);
        //
        // menuItemUndo
        //
        this.menuItemUndo.Name = "menuItemUndo";
        this.menuItemUndo.Size = new System.Drawing.Size(101, 22);
        this.menuItemUndo.Text = "Undo";
        this.menuItemUndo.Click += new System.EventHandler(this.menuItemUndo_Click);
        //
        // textNumbers
        //
        this.textNumbers.Font = new System.Drawing.Font("Courier New", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textNumbers.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(24)))), ((int)(((byte)(117)))), ((int)(((byte)(133)))));
        this.textNumbers.Location = new System.Drawing.Point(0, 58);
        this.textNumbers.Multiline = true;
        this.textNumbers.Name = "textNumbers";
        this.textNumbers.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNumbers.Size = new System.Drawing.Size(50, 514);
        this.textNumbers.TabIndex = 81;
        this.textNumbers.Text = "1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n10\r\n11\r\n12\r\n13\r\n188\r\n288";
        this.textNumbers.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // toolBar2
        //
        this.toolBar2.setImageList(this.imageListMain);
        this.toolBar2.Location = new System.Drawing.Point(0, 26);
        this.toolBar2.Name = "toolBar2";
        this.toolBar2.Size = new System.Drawing.Size(863, 25);
        this.toolBar2.TabIndex = 82;
        this.toolBar2.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.toolBar2.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.toolBar2_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textContent
        //
        this.textContent.setContextMenuStrip(this.contextMenuMain);
        this.textContent.setFont(new System.Drawing.Font("Courier New", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.textContent.Location = new System.Drawing.Point(32, 58);
        this.textContent.Name = "textContent";
        this.textContent.setReadOnly(false);
        this.textContent.setSelectedText("");
        this.textContent.setSelectionLength(0);
        this.textContent.setSelectionStart(0);
        this.textContent.Size = new System.Drawing.Size(438, 514);
        this.textContent.TabIndex = 80;
        this.textContent.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textContent_KeyPress);
        this.textContent.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.textContent_MouseDoubleClick);
        //
        // webBrowserWiki
        //
        this.webBrowserWiki.AllowWebBrowserDrop = false;
        this.webBrowserWiki.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserWiki.Location = new System.Drawing.Point(474, 58);
        this.webBrowserWiki.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserWiki.Name = "webBrowserWiki";
        this.webBrowserWiki.Size = new System.Drawing.Size(470, 514);
        this.webBrowserWiki.TabIndex = 78;
        this.webBrowserWiki.WebBrowserShortcutsEnabled = false;
        this.webBrowserWiki.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowserWiki_DocumentCompleted);
        this.webBrowserWiki.Navigated += new System.Windows.Forms.WebBrowserNavigatedEventHandler(this.webBrowserWiki_Navigated);
        //
        // ToolBarMain
        //
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(863, 25);
        this.ToolBarMain.TabIndex = 3;
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
        // FormWikiEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(944, 573);
        this.Controls.Add(this.toolBar2);
        this.Controls.Add(this.textContent);
        this.Controls.Add(this.textNumbers);
        this.Controls.Add(this.webBrowserWiki);
        this.Controls.Add(this.ToolBarMain);
        this.Name = "FormWikiEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Wiki Edit";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormWikiEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormWikiEdit_Load);
        this.SizeChanged += new System.EventHandler(this.FormWikiEdit_SizeChanged);
        this.contextMenuMain.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.WebBrowser webBrowserWiki = new System.Windows.Forms.WebBrowser();
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private OpenDental.TextBoxWiki textContent;
    private System.Windows.Forms.ContextMenuStrip contextMenuMain = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemCut = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemCopy = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemPaste = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemUndo = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.TextBox textNumbers = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODToolBar toolBar2;
}


