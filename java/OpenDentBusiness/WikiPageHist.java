//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.WikiPageHist;

/**
* Rows never edited, just added.  Contains all historical versions of each page as well.
*/
public class WikiPageHist  extends TableBase 
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
    * Will not be unique because there are multiple revisions per page.
    */
    public String PageTitle = new String();
    /**
    * The entire contents of the revision are stored in "wiki markup language".  This should never be updated.  Medtext (16M)
    */
    public String PageContent = new String();
    /**
    * The DateTime from the original WikiPage object.
    */
    public DateTime DateTimeSaved = new DateTime();
    /**
    * This flag will only be set for the revision where the user marked it deleted, not the ones prior.
    */
    public boolean IsDeleted = new boolean();
    /**
    * 
    */
    public WikiPageHist copy() throws Exception {
        return (WikiPageHist)MemberwiseClone();
    }

}


