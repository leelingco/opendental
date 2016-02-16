//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPageHist;
import OpenDentBusiness.WikiPageHists;

/**
* 
*/
public class WikiPages   
{
    /**
    * The only wiki page that gets cached is the master page.
    */
    private static WikiPage masterPage;
    /**
    * 
    */
    public static WikiPage getMasterPage() throws Exception {
        if (masterPage == null)
        {
            refreshCache();
        }
         
        return masterPage;
    }

    public static void setMasterPage(WikiPage value) throws Exception {
        masterPage = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM wikipage WHERE PageTitle='_Master'";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "WikiPage";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        masterPage = Crud.WikiPageCrud.TableToList(table)[0];
    }

    /**
    * Returns null if page does not exist.
    */
    public static WikiPage getByTitle(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<WikiPage>GetObject(MethodBase.GetCurrentMethod(), pageTitle);
        }
         
        String command = "SELECT * FROM wikipage WHERE PageTitle='" + POut.string(pageTitle) + "'";
        return Crud.WikiPageCrud.SelectOne(command);
    }

    /**
    * Returns a list of pages with PageTitle LIKE '%searchText%'.  Excludes titles that start with underscore.
    */
    public static List<WikiPage> getByTitleContains(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<WikiPage>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String command = "SELECT * FROM wikipage WHERE PageTitle NOT LIKE '\\_%' " + "AND PageTitle LIKE '%" + POut.string(searchText) + "%' ORDER BY PageTitle";
        return Crud.WikiPageCrud.SelectMany(command);
    }

    /**
    * Used when saving a page to check and fix the capitalization on each internal link. So the returned pagetitle might have different capitalization than the supplied pagetitle
    */
    public static String getTitle(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), pageTitle);
        }
         
        String command = "SELECT PageTitle FROM wikipage WHERE PageTitle = '" + POut.string(pageTitle) + "'";
        return Db.getScalar(command);
    }

    /**
    * Archives first by moving to WikiPageHist if it already exists.  Then, in either case, it inserts the new page.
    */
    public static long insertAndArchive(WikiPage wikiPage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            wikiPage.WikiPageNum = Meth.GetLong(MethodBase.GetCurrentMethod(), wikiPage);
            return wikiPage.WikiPageNum;
        }
         
        WikiPage wpExisting = getByTitle(wikiPage.PageTitle);
        if (wpExisting != null)
        {
            WikiPageHist wpHist = pageToHist(wpExisting);
            WikiPageHists.insert(wpHist);
            String command = "DELETE FROM wikipage WHERE PageTitle = '" + POut.string(wikiPage.PageTitle) + "'";
            Db.nonQ(command);
        }
         
        return Crud.WikiPageCrud.Insert(wikiPage);
    }

    /**
    * Searches keywords, title, content.
    */
    public static List<String> getForSearch(String searchText, boolean ignoreContent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod(), searchText, ignoreContent);
        }
         
        List<String> retVal = new List<String>();
        DataTable tableResults = new DataTable();
        String[] searchTokens = POut.string(searchText).Split(' ');
        String command = "";
        //Match keywords first-----------------------------------------------------------------------------------
        command = "SELECT PageTitle FROM wikiPage " + "WHERE PageTitle NOT LIKE '\\_%' ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            // \_ represents a literal _ because _ has a special meaning in LIKE clauses.
            //The second \ is just to escape the first \.  The other option would be to pass the \ through POut.String.
            command += "AND KeyWords LIKE '%" + POut.String(searchTokens[i]) + "%' ";
        }
        command += "GROUP BY PageTitle " + "ORDER BY PageTitle";
        tableResults = Db.getTable(command);
        for (int i = 0;i < tableResults.Rows.Count;i++)
        {
            if (!retVal.Contains(tableResults.Rows[i]["PageTitle"].ToString()))
            {
                retVal.Add(tableResults.Rows[i]["PageTitle"].ToString());
            }
             
        }
        //Match PageTitle Second-----------------------------------------------------------------------------------
        command = "SELECT PageTitle FROM wikiPage " + "WHERE PageTitle NOT LIKE '\\_%' ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += "AND PageTitle LIKE '%" + POut.String(searchTokens[i]) + "%' ";
        }
        command += "GROUP BY PageTitle " + "ORDER BY PageTitle";
        tableResults = Db.getTable(command);
        for (int i = 0;i < tableResults.Rows.Count;i++)
        {
            if (!retVal.Contains(tableResults.Rows[i]["PageTitle"].ToString()))
            {
                retVal.Add(tableResults.Rows[i]["PageTitle"].ToString());
            }
             
        }
        //Match Content third-----------------------------------------------------------------------------------
        if (!ignoreContent)
        {
            command = "SELECT PageTitle FROM wikiPage " + "WHERE PageTitle NOT LIKE '\\_%' ";
            for (int i = 0;i < searchTokens.Length;i++)
            {
                command += "AND PageContent LIKE '%" + POut.String(searchTokens[i]) + "%' ";
            }
            command += "GROUP BY PageTitle " + "ORDER BY PageTitle";
            tableResults = Db.getTable(command);
            for (int i = 0;i < tableResults.Rows.Count;i++)
            {
                if (!retVal.Contains(tableResults.Rows[i]["PageTitle"].ToString()))
                {
                    retVal.Add(tableResults.Rows[i]["PageTitle"].ToString());
                }
                 
            }
        }
         
        return retVal;
    }

    /**
    * Returns a list of all pages that reference "PageTitle".  No historical pages.
    */
    public static List<WikiPage> getIncomingLinks(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<WikiPage>>GetObject(MethodBase.GetCurrentMethod(), pageTitle);
        }
         
        List<WikiPage> retVal = new List<WikiPage>();
        String command = "SELECT * FROM wikipage WHERE PageContent LIKE '%[[" + POut.string(pageTitle) + "]]%' ORDER BY PageTitle";
        return Crud.WikiPageCrud.SelectMany(command);
    }

    /**
    * Validation was already done in FormWikiRename to make sure that the page does not already exist in WikiPage table.  But what if the page already exists in WikiPageHistory?  In that case, previous history for the other page would start showing as history for the newly renamed page, which is fine.
    */
    public static void rename(WikiPage wikiPage, String newPageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), wikiPage, newPageTitle);
            return ;
        }
         
        //a later improvement would be to validate again here in the business layer.
        wikiPage.UserNum = Security.getCurUser().UserNum;
        insertAndArchive(wikiPage);
        //Rename all pages in both tables: wikiPage and wikiPageHist.
        String command = "UPDATE wikipage SET PageTitle='" + POut.string(newPageTitle) + "'WHERE PageTitle='" + POut.string(wikiPage.PageTitle) + "'";
        Db.nonQ(command);
        command = "UPDATE wikipagehist SET PageTitle='" + POut.string(newPageTitle) + "'WHERE PageTitle='" + POut.string(wikiPage.PageTitle) + "'";
        Db.nonQ(command);
        //For now, we will simply fix existing links in history.
        //The way this is written currently is case sensitive.  That's fine, but it means that all existing links must be perfect, including case, or they will not get updated.
        //To enforce proper case, we fix it when saving each page in the WikiEdit window.
        command = "UPDATE wikipage SET PageContent=REPLACE(PageContent,'[[" + POut.string(wikiPage.PageTitle) + "]]', '[[" + POut.string(newPageTitle) + "]]')";
        Db.nonQ(command);
        command = "UPDATE wikipagehist SET PageContent=REPLACE(PageContent,'[[" + POut.string(wikiPage.PageTitle) + "]]', '[[" + POut.string(newPageTitle) + "]]')";
        Db.nonQ(command);
        return ;
    }

    /**
    * Used in TranslateToXhtml to know whether to mark a page as not exists.
    */
    public static List<boolean> checkPageNamesExist(List<String> pageTitles) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<boolean>>GetObject(MethodBase.GetCurrentMethod(), pageTitles);
        }
         
        String command = "SELECT PageTitle FROM wikipage WHERE ";
        for (int i = 0;i < pageTitles.Count;i++)
        {
            if (i > 0)
            {
                command += "OR ";
            }
             
            command += "PageTitle='" + POut.String(pageTitles[i]) + "' ";
        }
        DataTable table = Db.getTable(command);
        List<boolean> retVal = new List<boolean>();
        for (int p = 0;p < pageTitles.Count;p++)
        {
            boolean valForThisPage = false;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (table.Rows[i]["PageTitle"].ToString().ToLower() == pageTitles[p].ToLower())
                {
                    valForThisPage = true;
                    break;
                }
                 
            }
            retVal.Add(valForThisPage);
        }
        return retVal;
    }

    /*///<summary>Update may be implemented when versioning is improved.</summary>
    		public static void Update(WikiPage wikiPage){
    			Insert(wikiPage);
    			//if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    			//  Meth.GetVoid(MethodBase.GetCurrentMethod(),wikiPage);
    			//  return;
    			//}
    			//Crud.WikiPageCrud.Update(wikiPage);
    		}*/
    /**
    * Typically returns something similar to \\SERVER\OpenDentImages\Wiki
    */
    public static String getWikiPath() throws Exception {
        //No need to check RemotingRole; no call to db.
        String wikiPath = new String();
        if (!PrefC.getAtoZfolderUsed())
        {
            throw new ApplicationException("Must be using AtoZ folders.");
        }
         
        wikiPath = Path.Combine(ImageStore.getPreferredAtoZpath(), "Wiki");
        if (!Directory.Exists(wikiPath))
        {
            Directory.CreateDirectory(wikiPath);
        }
         
        return wikiPath;
    }

    /**
    * Surround with try/catch.  Also aggregates the content into the master page.  If isPreviewOnly, then the internal links will not be checked to see if the page exists, as it would make the refresh sluggish.  And isPreviewOnly also changes the pointer so that the page looks non-clickable.
    */
    public static String translateToXhtml(String wikiContent, boolean isPreviewOnly) throws Exception {
        //No need to check RemotingRole; no call to db.
        String s = wikiContent;
        MatchCollection matches = new MatchCollection();
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
            {
                //try {
                doc.Load(reader);
            }
        }
        finally
        {
            if (reader != null)
                Disposable.mkDisposable(reader).dispose();
             
        }
        //}
        //catch(Exception ex) {
        //	return MasterPage.PageContent.Replace("@@@body@@@",ex.Message);
        //}
        //[[img:myimage.gif]]------------------------------------------------------------------------------------------------------------
        //MatchCollection matches;
        matches = Regex.Matches(s, "\\[\\[(img:).+?\\]\\]");
        for (Object __dummyForeachVar0 : matches)
        {
            Match match = (Match)__dummyForeachVar0;
            String imgName = match.Value.Substring(match.Value.IndexOf(":") + 1).TrimEnd("]".ToCharArray());
            String fullPath = CodeBase.ODFileUtils.combinePaths(getWikiPath(),POut.string(imgName));
            s = s.Replace(match.Value, "<img src=\"file:///" + fullPath.Replace("\\", "/") + "\"></img>");
        }
        //"\" />");
        //[[keywords: key1, key2, etc.]]------------------------------------------------------------------------------------------------
        matches = Regex.Matches(s, "\\[\\[(keywords:).*?\\]\\]");
        for (Object __dummyForeachVar1 : matches)
        {
            Match match = (Match)__dummyForeachVar1;
            //should be only one
            s = s.Replace(match.Value, "<span class=\"keywords\">keywords:" + match.Value.Substring(11).TrimEnd("]".ToCharArray()) + "</span>");
        }
        //[[file:C:\eaula.txt]]------------------------------------------------------------------------------------------------
        matches = Regex.Matches(s, "\\[\\[(file:).*?\\]\\]");
        for (Object __dummyForeachVar2 : matches)
        {
            Match match = (Match)__dummyForeachVar2;
            String fileName = match.Value.Replace("[[file:", "").TrimEnd(']');
            s = s.Replace(match.Value, "<a href=\"wikifile:" + fileName + "\">file:" + fileName + "</a>");
        }
        //[[folder:\\serverfiles\storage\]]------------------------------------------------------------------------------------------------
        matches = Regex.Matches(s, "\\[\\[(folder:).*?\\]\\]");
        for (Object __dummyForeachVar3 : matches)
        {
            Match match = (Match)__dummyForeachVar3;
            String folderName = match.Value.Replace("[[folder:", "").TrimEnd(']');
            s = s.Replace(match.Value, "<a href=\"folder:" + folderName + "\">folder:" + folderName + "</a>");
        }
        //[[list:listname]]------------------------------------------------------------------------------------------------
        //matches=Regex.Matches(s,@"\[\[(list:).*?\]\]");
        //foreach(Match match in matches) {
        //	s=s.Replace(match.Value,WikiLists.TranslateToHTML(match.Value.Substring(7).Trim(']')));
        //}
        //[[color:red|text]]----------------------------------------------------------------------------------------------------------------
        matches = Regex.Matches(s, "\\[\\[(color:).*?\\]\\]");
        for (Object __dummyForeachVar4 : matches)
        {
            //.*? matches as few as possible.
            Match match = (Match)__dummyForeachVar4;
            //string[] paragraphs = match.Value.Split(new string[] { "\r\n" },StringSplitOptions.None);
            String tempText = "<span style=\"color:";
            String[] tokens = match.Value.Split('|');
            if (tokens.Length < 2)
            {
                continue;
            }
             
            //not enough tokens
            if (tokens[0].Split(':').Length != 2)
            {
                continue;
            }
             
            for (int i = 0;i < tokens.Length;i++)
            {
                //Must have a color token and a color value seperated by a colon, no more no less.
                if (i == 0)
                {
                    tempText += tokens[0].Split(':')[1] + ";\">";
                    continue;
                }
                 
                //close <span> tag
                tempText += (i > 1 ? "|" : "") + tokens[i];
            }
            tempText = tempText.TrimEnd(']');
            tempText += "</span>";
            s = s.Replace(match.Value, tempText);
        }
        //[[InternalLink]]--------------------------------------------------------------------------------------------------------------
        matches = Regex.Matches(s, "\\[\\[.+?\\]\\]");
        List<String> pageNamesToCheck = new List<String>();
        List<boolean> pageNamesExist = new List<boolean>();
        if (!isPreviewOnly)
        {
            for (Object __dummyForeachVar5 : matches)
            {
                Match match = (Match)__dummyForeachVar5;
                pageNamesToCheck.Add(match.Value.Trim('[', ']'));
            }
            if (pageNamesToCheck.Count > 0)
            {
                pageNamesExist = CheckPageNamesExist(pageNamesToCheck);
            }
             
        }
         
        for (Object __dummyForeachVar6 : matches)
        {
            //this gets a list of bools for all pagenames in one shot.  One query.
            Match match = (Match)__dummyForeachVar6;
            String styleNotExists = "";
            if (!isPreviewOnly)
            {
                String pageName = match.Value.Trim('[', ']');
                int idx = pageNamesToCheck.IndexOf(pageName);
                if (!pageNamesExist[idx])
                {
                    styleNotExists = "class='PageNotExists' ";
                }
                 
            }
             
            /*.Replace(" ","_")*/
            s = s.Replace(match.Value, "<a " + styleNotExists + "href=\"" + "wiki:" + match.Value.Trim('[', ']') + "\">" + match.Value.Trim('[', ']') + "</a>");
        }
        //Unordered List----------------------------------------------------------------------------------------------------------------
        //Instead of using a regex, this will hunt through the rows in sequence.
        //later nesting by running ***, then **, then *
        s = processList(s,"*");
        //numbered list---------------------------------------------------------------------------------------------------------------------
        s = processList(s,"#");
        //table-------------------------------------------------------------------------------------------------------------------------
        //{|
        //!Width="100"|Column Heading 1!!Width="150"|Column Heading 2!!Width=""|Column Heading 3
        //|-
        //|Cell 1||Cell 2||Cell 3
        //|-
        //|Cell A||Cell B||Cell C
        //|}
        //There are many ways to parse this.  Our strategy is to do it in a way that the generated xml is never invalid.
        //As the user types, the above example will frequently be in a state of partial completeness, and the parsing should gracefully continue anyway.
        //rigorous enforcement only happens when validating during a save, not here.
        matches = Regex.Matches(s, "\\{\\|(.+?)\\|\\}", RegexOptions.Singleline);
        for (Object __dummyForeachVar7 : matches)
        {
            Match match = (Match)__dummyForeachVar7;
            String tableStrOrig = match.Value;
            StringBuilder strbTable = new StringBuilder();
            String[] lines = tableStrOrig.Split(new String[]{ "{|\r\n", "\r\n|-\r\n", "\r\n|}" }, StringSplitOptions.RemoveEmptyEntries);
            strbTable.AppendLine("<table>");
            List<String> colWidths = new List<String>();
            for (int i = 0;i < lines.Length;i++)
            {
                if (lines[i].StartsWith("!"))
                {
                    //header
                    strbTable.AppendLine("<tr>");
                    lines[i] = lines[i].Substring(1);
                    //strips off the leading !
                    String[] cells = lines[i].Split(new String[]{ "!!" }, StringSplitOptions.None);
                    colWidths.Clear();
                    for (int c = 0;c < cells.Length;c++)
                    {
                        if (Regex.IsMatch(cells[c], "(Width=\")\\d+\"\\|"))
                        {
                            //e.g. Width="90"|
                            strbTable.Append("<th ");
                            String width = cells[c].Substring(7);
                            //90"|Column Heading 1
                            width = width.Substring(0, width.IndexOf("\""));
                            //90
                            colWidths.Add(width);
                            strbTable.Append("Width=\"" + width + "\">");
                            strbTable.Append(ProcessParagraph(cells[c].Substring(cells[c].IndexOf("|") + 1), false));
                            //surround with p tags. Allow CR in header.
                            strbTable.AppendLine("</th>");
                        }
                        else
                        {
                            strbTable.Append("<th>");
                            strbTable.Append(ProcessParagraph(cells[c], false));
                            //surround with p tags. Allow CR in header.
                            strbTable.AppendLine("</th>");
                        } 
                    }
                    strbTable.AppendLine("</tr>");
                }
                else if (StringSupport.equals(lines[i].Trim(), "|-"))
                {
                }
                else
                {
                    //totally ignore these rows
                    //normal row
                    strbTable.AppendLine("<tr>");
                    lines[i] = lines[i].Substring(1);
                    //strips off the leading |
                    String[] cells = lines[i].Split(new String[]{ "||" }, StringSplitOptions.None);
                    for (int c = 0;c < cells.Length;c++)
                    {
                        strbTable.Append("<td Width=\"" + colWidths[c] + "\">");
                        strbTable.Append(ProcessParagraph(cells[c], false));
                        strbTable.AppendLine("</td>");
                    }
                    strbTable.AppendLine("</tr>");
                }  
            }
            strbTable.Append("</table>");
            s = s.Replace(tableStrOrig, strbTable.ToString());
        }
        StringBuilder strbSnew = new StringBuilder();
        //a paragraph is defined as all text between sibling tags, even if just a \r\n.
        int iScanInParagraph = 0;
        //scan starting at the beginning of s.  S gets chopped from the start each time we grab a paragraph or a sibiling element.
        //The scanning position represents the verified paragraph content, and does not advance beyond that.
        //move <body> tag over.
        strbSnew.Append("<body>");
        s = s.Substring(6);
        boolean startsWithCR = false;
        //todo: handle one leading CR if there is no text preceding it.
        if (s.StartsWith("\r\n"))
        {
            startsWithCR = true;
        }
         
        String tagName = new String();
        Match tagCurMatch = new Match();
        while (true)
        {
            //loop to either construct a paragraph, or to immediately add the next tag to strbSnew.
            iScanInParagraph = s.IndexOf("<", iScanInParagraph);
            //Advance the scanner to the start of the next tag
            if (iScanInParagraph == -1)
            {
                throw new ApplicationException("No tags found.");
            }
             
            //there aren't any more tags, so current paragraph goes to end of string.  This won't happen
            //strbSnew.Append(ProcessParagraph(s));
            if (s.Substring(iScanInParagraph).StartsWith("</body>"))
            {
                strbSnew.Append(ProcessParagraph(s.Substring(0, iScanInParagraph), startsWithCR));
                //startsWithCR=false;
                //strbSnew.Append("</body>");
                s = "";
                iScanInParagraph = 0;
                break;
            }
             
            tagName = "";
            tagCurMatch = Regex.Match(s.Substring(iScanInParagraph), "^<.*?>");
            //regMatch);//.*? means any char, zero or more, as few as possible
            if (tagCurMatch == null)
            {
                throw new ApplicationException("Unexpected tag: " + s.Substring(iScanInParagraph));
            }
             
            //shouldn't happen unless closing bracket is missing
            //message not seen by user, look in FormWikiEdit for relevant error messages.
            if (tagCurMatch.Value.Trim('<', '>').EndsWith("/"))
            {
                throw new ApplicationException("All elements must have a beginning and ending tag. Unexpected tag: " + s.Substring(iScanInParagraph));
            }
             
            //self terminating tags NOT are allowed
            //this should catch all non-allowed self-terminating tags i.e. <br />, <inherits />, etc...
            //not seen by user
            //Nesting of identical tags causes problems:
            //<h1><h1>some text</h1></h1>
            //The first <h1> will match with the first </h1>.
            //We don't have time to support this outlier, so we will catch it in the validator when they save.
            //One possible strategy here might be:
            //idxNestedDuplicate=s.IndexOf("<"+tagName+">");
            //if(idxNestedDuplicate<s.IndexOf("</"+tagName+">"){
            //
            //}
            //Another possible strategy might be to use regular expressions.
            tagName = tagCurMatch.Value.Split(new String[]{ "<", " ", ">" }, StringSplitOptions.RemoveEmptyEntries)[0];
            //works with tags like <i>, <span ...>, and <img .../>
            if (s.IndexOf("</" + tagName + ">") == -1)
            {
                throw new ApplicationException("No ending tag: " + s.Substring(iScanInParagraph));
            }
             
            //this will happen if no ending tag.
            System.String __dummyScrutVar0 = tagName;
            if (__dummyScrutVar0.equals("a") || __dummyScrutVar0.equals("b") || __dummyScrutVar0.equals("i") || __dummyScrutVar0.equals("span"))
            {
                iScanInParagraph = s.IndexOf("</" + tagName + ">", iScanInParagraph) + 3 + tagName.Length;
                continue;
            }
            else //continues scanning this paragraph.
            if (__dummyScrutVar0.equals("h1") || __dummyScrutVar0.equals("h2") || __dummyScrutVar0.equals("h3") || __dummyScrutVar0.equals("ol") || __dummyScrutVar0.equals("ul") || __dummyScrutVar0.equals("table") || __dummyScrutVar0.equals("img"))
            {
                //can NOT be self-terminating
                if (iScanInParagraph == 0)
                {
                }
                else
                {
                    //s starts with a non-paragraph tag, so there is no partially assembled paragraph to process.
                    //do nothing
                    //we are already part way into assembling a paragraph.
                    strbSnew.Append(ProcessParagraph(s.Substring(0, iScanInParagraph), startsWithCR));
                    startsWithCR = false;
                    //subsequent paragraphs will not need this
                    s = s.Substring(iScanInParagraph);
                    //chop off start of s
                    iScanInParagraph = 0;
                } 
                //scan to the end of this element
                int iScanSibling = s.IndexOf("</" + tagName + ">") + 3 + tagName.Length;
                //tags without a closing tag were caught above.
                //move the non-paragraph content over to s new.
                strbSnew.Append(s.Substring(0, iScanSibling));
                s = s.Substring(iScanSibling);
            }
            else
            {
                throw new ApplicationException("Unexpected tag: " + s.Substring(iScanInParagraph));
            }  
        }
        //scanning will start a totally new paragraph
        strbSnew.Append("</body>");
        doc = new XmlDocument();
        StringReader reader = new StringReader(strbSnew.ToString());
        try
        {
            {
                //try {
                doc.Load(reader);
            }
        }
        finally
        {
            if (reader != null)
                Disposable.mkDisposable(reader).dispose();
             
        }
        //}
        //catch(Exception ex) {
        //	return MasterPage.PageContent.Replace("@@@body@@@",ex.Message);
        //}
        StringBuilder strbOut = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = "\t";
        settings.OmitXmlDeclaration = true;
        settings.NewLineChars = "\r\n";
        XmlWriter writer = XmlWriter.Create(strbOut, settings);
        try
        {
            {
                doc.WriteTo(writer);
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        //spaces can't be handled prior to this point because &nbsp; crashes the xml parser.
        strbOut.Replace("  ", "&nbsp;&nbsp;");
        //handle extra spaces.
        strbOut.Replace("<td></td>", "<td>&nbsp;</td>");
        //force blank table cells to show not collapsed
        strbOut.Replace("<th></th>", "<th>&nbsp;</th>");
        //and blank table headers
        strbOut.Replace("{{nbsp}}", "&nbsp;");
        //couldn't add the &nbsp; earlier because
        strbOut.Replace("<p></p>", "<p>&nbsp;</p>");
        //probably redundant but harmless
        //aggregate with master
        s = getMasterPage().PageContent.Replace("@@@body@@@", strbOut.ToString());
        return s;
    }

    /*
    			//js This code is buggy.  It will need very detailed comments and careful review before/if we ever turn it back on.
    			if(isPreviewOnly) {
    				//do not change cursor from pointer to IBeam to Hand as you move the cursor around the preview page
    				s=s.Replace("*{\r\n\t","*{\r\n\tcursor:default;\r\n\t");
    				//do not underline links if you hover over them in the preview window
    				s=s.Replace("a:hover{\r\n\ttext-decoration:underline;","a:hover{\r\n\t");
    			}*/
    /**
    * This will get called repeatedly.  prefixChars is, for now, * or #.  Returns the altered text of the full document.
    */
    private static String processList(String s, String prefixChars) throws Exception {
        String[] lines = s.Split(new String[]{ "\r\n" }, StringSplitOptions.None);
        //includes empty elements
        String blockOriginal = null;
        //once a list is found, this string will be filled with the original text.
        StringBuilder strb = null;
        for (int i = 0;i < lines.Length;i++)
        {
            //this will contain the final output enclosed in <ul> or <ol> tags.
            if (blockOriginal == null)
            {
                //we are still hunting for the first line of a list.
                if (lines[i].StartsWith(prefixChars))
                {
                    //we found the first line of a list.
                    blockOriginal = lines[i] + "\r\n";
                    strb = new StringBuilder();
                    if (prefixChars.Contains("*"))
                    {
                        strb.Append("<ul>\r\n");
                    }
                    else if (prefixChars.Contains("#"))
                    {
                        strb.Append("<ol>\r\n");
                    }
                      
                    lines[i] = lines[i].Substring(prefixChars.Length);
                    //strip off the prefixChars
                    strb.Append("<li><span class='ListItemContent'>");
                    //lines[i]=lines[i].Replace("  ","[[nbsp]][[nbsp]]");//handle extra spaces.  We may move this to someplace more global
                    strb.Append(lines[i]);
                    strb.Append("</span></li>\r\n");
                }
                else
                {
                } 
            }
            else
            {
                //no list
                //nothing to do
                //we are already building our list
                if (lines[i].StartsWith(prefixChars))
                {
                    //we found another line of a list.  Could be a middle line or the last line.
                    blockOriginal += lines[i] + "\r\n";
                    lines[i] = lines[i].Substring(prefixChars.Length);
                    //strip off the prefixChars
                    strb.Append("<li><span class='ListItemContent'>");
                    //lines[i]=lines[i].Replace("  ","[[nbsp]][[nbsp]]");//handle extra spaces.  We may move this to someplace more global
                    strb.Append(lines[i]);
                    strb.Append("</span></li>\r\n");
                }
                else
                {
                    //end of list.  The previous line was the last line.
                    if (prefixChars.Contains("*"))
                    {
                        strb.Append("</ul>\r\n");
                    }
                    else if (prefixChars.Contains("#"))
                    {
                        strb.Append("</ol>\r\n");
                    }
                      
                    //manually replace just the first occurance of the identified list.
                    s = s.Substring(0, s.IndexOf(blockOriginal)) + strb.ToString() + s.Substring(s.IndexOf(blockOriginal) + blockOriginal.Length);
                    //s=s.Replace(blockOriginal,strb.ToString()); //old strategy, buggy.
                    blockOriginal = null;
                } 
            } 
        }
        return s;
    }

    /**
    * This will wrap the text in p tags as well as handle internal carriage returns.  startsWithCR is only used on the first paragraph for the unusual case where the entire content starts with a CR.  This prevents stripping it off.
    */
    private static String processParagraph(String paragraph, boolean startsWithCR) throws Exception {
        if (paragraph.StartsWith("\r\n") && !startsWithCR)
        {
            paragraph = paragraph.Substring(2);
        }
         
        if (StringSupport.equals(paragraph, ""))
        {
            return "";
        }
         
        //this must come after the first CR is stripped off, but before the ending CR is stripped off.
        if (paragraph.EndsWith("\r\n"))
        {
            //trailing CR remove
            paragraph = paragraph.Substring(0, paragraph.Length - 2);
        }
         
        //if the paragraph starts with any number of spaces followed by a tag such as <b> or <span>, then we need to force those spaces to show.
        if (paragraph.StartsWith(" ") && paragraph.TrimStart(' ').StartsWith("<"))
        {
            paragraph = "{{nbsp}}" + paragraph.Substring(1);
        }
         
        //this will later be converted to &nbsp;
        paragraph = paragraph.Replace("\r\n", "<br/>");
        //We tried </p><p>, but that didn't allow bold, italic, or color to span lines.
        paragraph = "<p>" + paragraph + "</p>";
        //surround paragraph with tags
        paragraph = paragraph.Replace("<p> ", "<p>{{nbsp}}");
        //spaces at the beginnings of paragraphs
        paragraph = paragraph.Replace("<br/> ", "<br/>{{nbsp}}");
        //spaces at beginnings of lines
        paragraph = paragraph.Replace("<br/></p>", "<br/>{{nbsp}}</p>");
        return paragraph;
    }

    //have a cr show if it's at the end of a paragraph
    /**
    * Creates historical entry of deletion into wikiPageHist, and deletes current page from WikiPage.
    */
    public static void delete(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pageTitle);
            return ;
        }
         
        WikiPage wikiPage = getByTitle(pageTitle);
        WikiPageHist wikiPageHist = pageToHist(wikiPage);
        //preserve the existing page with user credentials
        WikiPageHists.insert(wikiPageHist);
        //make entry to show who deleted the page
        wikiPageHist.IsDeleted = true;
        wikiPageHist.UserNum = Security.getCurUser().UserNum;
        WikiPageHists.insert(wikiPageHist);
        String command = "DELETE FROM wikipage WHERE PageTitle = '" + POut.string(pageTitle) + "'";
        Db.nonQ(command);
    }

    public static WikiPageHist pageToHist(WikiPage wikiPage) throws Exception {
        //No need to check RemotingRole; no call to db.
        WikiPageHist wikiPageHist = new WikiPageHist();
        wikiPageHist.WikiPageNum = -1;
        //todo:handle this -1, shouldn't be a problem since we always get pages by Title.
        wikiPageHist.UserNum = wikiPage.UserNum;
        wikiPageHist.PageTitle = wikiPage.PageTitle;
        wikiPageHist.PageContent = wikiPage.PageContent;
        wikiPageHist.DateTimeSaved = wikiPage.DateTimeSaved;
        //This gets set to NOW if this page is then inserted
        wikiPageHist.IsDeleted = false;
        return wikiPageHist;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<WikiPage> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<WikiPage>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM wikipage WHERE PatNum = "+POut.Long(patNum);
			return Crud.WikiPageCrud.SelectMany(command);
		}
		///<summary>Gets one WikiPage from the db.</summary>
		public static WikiPage GetOne(long wikiPageNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<WikiPage>(MethodBase.GetCurrentMethod(),wikiPageNum);
			}
			return Crud.WikiPageCrud.SelectOne(wikiPageNum);
		}
		*/