//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.WikiPage;

/**
* Rows never edited, just added.  Contains all only newest versions of each page.
*/
public class WikiPage  extends TableBase 
{
    /**
    * Primary key.
    */
    public long WikiPageNum = new long();
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * Must be unique.  Any character is allowed except: \r, \n, and ".  Needs to be tested, especially with apostrophes.
    */
    public String PageTitle = new String();
    /**
    * Automatically filled from the [[Keywords:]] tab in the PageContent field as page is being saved.
    */
    public String KeyWords = new String();
    /**
    * Content of page stored in "wiki markup language".  This should never be updated.  Medtext (16M)
    */
    public String PageContent = new String();
    /**
    * The DateTime that the page was saved to the DB.  User can't edit.
    */
    public DateTime DateTimeSaved = new DateTime();
    //<summary>Deprecated.  Remove this.  When used in wikipagehist, this flag will only be set for the revision where the user marked it deleted (the last one).</summary>
    //public bool IsDeleted;
    /**
    * 
    */
    public WikiPage copy() throws Exception {
        return (WikiPage)MemberwiseClone();
    }

}


