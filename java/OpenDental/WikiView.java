//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:01 PM
//

package OpenDental;


/**
* Not a db table.  This is stored in a wikipage as xml markup.  Pulled out for manipulation, and then stored back in the markup.
*/
public class WikiView   
{
    public String ViewName = new String();
    public String OrderBy = new String();
    /**
    * The column names must exactly match existing column name.
    */
    public List<String> Columns = new List<String>();
}


