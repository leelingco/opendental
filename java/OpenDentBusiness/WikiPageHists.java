//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.WikiPage;
import OpenDentBusiness.WikiPageHist;

/**
* 
*/
public class WikiPageHists   
{
    /**
    * Ordered by dateTimeSaved.
    */
    public static List<WikiPageHist> getByTitle(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<WikiPageHist>>GetObject(MethodBase.GetCurrentMethod(), pageTitle);
        }
         
        String command = "SELECT * FROM wikipagehist WHERE PageTitle = '" + POut.string(pageTitle) + "' ORDER BY DateTimeSaved";
        return Crud.WikiPageHistCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<String> getDeletedPages(String searchText, boolean ignoreContent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod(), searchText, ignoreContent);
        }
         
        List<String> retVal = new List<String>();
        DataTable tableResults = new DataTable();
        DataTable tableNewestDateTimes = new DataTable();
        String[] searchTokens = searchText.Split(' ');
        String command = "";
        command = "SELECT PageTitle, MAX(DateTimeSaved) AS DateTimeSaved FROM wikipagehist GROUP BY PageTitle";
        tableNewestDateTimes = Db.getTable(command);
        command = "SELECT PageTitle,DateTimeSaved FROM wikipagehist " + "WHERE PageTitle NOT LIKE '\\_%' ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            // \_ represents a literal _ because _ has a special meaning in LIKE clauses.
            //The second \ is just to escape the first \.  The other option would be to pass the \ through POut.String.
            command += "AND PageTitle LIKE '%" + POut.String(searchTokens[i]) + "%' ";
        }
        command += "AND PageTitle NOT IN (SELECT PageTitle FROM wikipage) " + "AND IsDeleted=1 " + "ORDER BY PageTitle";
        //ignore pages that were re-added after they were deleted
        tableResults = Db.getTable(command);
        for (int i = 0;i < tableResults.Rows.Count;i++)
        {
            if (retVal.Contains(tableResults.Rows[i]["PageTitle"].ToString()))
            {
                continue;
            }
             
            for (int j = 0;j < tableNewestDateTimes.Rows.Count;j++)
            {
                //already found this page
                if (tableNewestDateTimes.Rows[j]["PageTitle"].ToString() != tableResults.Rows[i]["PageTitle"].ToString())
                {
                    continue;
                }
                 
                //not the right page
                if (tableNewestDateTimes.Rows[j]["DateTimeSaved"].ToString() != tableResults.Rows[i]["DateTimeSaved"].ToString())
                {
                    continue;
                }
                 
                //not the right DateTimeSaved
                //This page is both deleted and there are no newer revisions of the page exist
                retVal.Add(tableResults.Rows[i]["PageTitle"].ToString());
                break;
            }
        }
        //Match Content Second-----------------------------------------------------------------------------------
        if (!ignoreContent)
        {
            command = "SELECT PageTitle,DateTimeSaved FROM wikipagehist " + "WHERE PageTitle NOT LIKE '\\_%' ";
            for (int i = 0;i < searchTokens.Length;i++)
            {
                command += "AND PageContent LIKE '%" + POut.String(searchTokens[i]) + "%' ";
            }
            command += "AND PageTitle NOT IN (SELECT PageTitle FROM wikipage) " + "AND IsDeleted=1 " + "ORDER BY PageTitle";
            //ignore pages that exist again...
            tableResults = Db.getTable(command);
            for (int i = 0;i < tableResults.Rows.Count;i++)
            {
                if (retVal.Contains(tableResults.Rows[i]["PageTitle"].ToString()))
                {
                    continue;
                }
                 
                for (int j = 0;j < tableNewestDateTimes.Rows.Count;j++)
                {
                    //already found this page
                    if (tableNewestDateTimes.Rows[j]["PageTitle"].ToString() != tableResults.Rows[i]["PageTitle"].ToString())
                    {
                        continue;
                    }
                     
                    //not the right page
                    if (tableNewestDateTimes.Rows[j]["DateTimeSaved"].ToString() != tableResults.Rows[i]["DateTimeSaved"].ToString())
                    {
                        continue;
                    }
                     
                    //not the right DateTimeSaved
                    //This page is both deleted and there are no newer revisions of the page exist
                    retVal.Add(tableResults.Rows[i]["PageTitle"].ToString());
                    break;
                }
            }
        }
         
        return retVal;
    }

    /**
    * Only returns the most recently deleted version of the page. Returns null if not found.
    */
    public static WikiPageHist getDeletedByTitle(String pageTitle) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<WikiPageHist>GetObject(MethodBase.GetCurrentMethod(), pageTitle);
        }
         
        String command = "SELECT * FROM wikipagehist " + "WHERE PageTitle = '" + POut.string(pageTitle) + "' " + "AND IsDeleted=1 " + "AND DateTimeSaved=" + "(SELECT MAX(DateTimeSaved) " + "FROM wikipagehist " + "WHERE PageTitle = '" + POut.string(pageTitle) + "' " + "AND IsDeleted=1)";
        return Crud.WikiPageHistCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(WikiPageHist wikiPageHist) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            wikiPageHist.WikiPageNum = Meth.GetLong(MethodBase.GetCurrentMethod(), wikiPageHist);
            return wikiPageHist.WikiPageNum;
        }
         
        return Crud.WikiPageHistCrud.Insert(wikiPageHist);
    }

    public static WikiPage revertFrom(WikiPageHist wikiPageHist) throws Exception {
        WikiPage retVal = new WikiPage();
        //retVal.WikiPageNum
        //retVal.UserNum
        retVal.PageTitle = wikiPageHist.PageTitle;
        retVal.PageContent = wikiPageHist.PageContent;
        retVal.KeyWords = "";
        Match m = Regex.Match(wikiPageHist.PageContent, "\\[\\[(keywords:).*?\\]\\]");
        if (m.Length > 0)
        {
            retVal.KeyWords = m.Value.Substring(11).TrimEnd(']');
        }
         
        return retVal;
    }

}


//retVal.DateTimeSaved=DateTime.Now;//gets set when inserted.
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one WikiPageHist from the db.</summary>
		public static WikiPageHist GetOne(long wikiPageNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<WikiPageHist>(MethodBase.GetCurrentMethod(),wikiPageNum);
			}
			return Crud.WikiPageHistCrud.SelectOne(wikiPageNum);
		}
		///<summary></summary>
		public static void Update(WikiPageHist wikiPageHist){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),wikiPageHist);
				return;
			}
			Crud.WikiPageHistCrud.Update(wikiPageHist);
		}
		///<summary></summary>
		public static void Delete(long wikiPageNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),wikiPageNum);
				return;
			}
			string command= "DELETE FROM wikipagehist WHERE WikiPageNum = "+POut.Long(wikiPageNum);
			Db.NonQ(command);
		}
		*/