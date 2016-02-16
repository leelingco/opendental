//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Rows;

public class TableWorkClass   
{
    public Rows Data;
    // Runtime data; either original query if no groups
    // or sorting or a copied version that is grouped/sorted
    public List<GroupEntry> Groups = new List<GroupEntry>();
    // Runtime groups; array of GroupEntry
    public int GroupNestCount = new int();
    // Runtime: calculated on fly for # of table rows that are part of a group
    //    used to handle toggling of a group
    public Grouping RecursiveGroup;
    // Runtime: set with a recursive; currently on support a single recursive group
    public TableWorkClass() throws Exception {
        Data = null;
        Groups = null;
        GroupNestCount = 0;
        RecursiveGroup = null;
    }

}


