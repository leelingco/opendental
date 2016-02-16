//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Some program links (bridges), have properties that need to be set.  The property names are always hard coded.  User can change the value.  The property is usually retrieved based on its name.
*/
public class ProgramProperty  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProgramPropertyNum = new long();
    /**
    * FK to program.ProgramNum
    */
    public long ProgramNum = new long();
    /**
    * The description or prompt for this property.  Blank for workstation overrides of program path.
    */
    public String PropertyDesc = new String();
    /**
    * The value.
    */
    public String PropertyValue = new String();
    /**
    * The human-readable name of the computer on the network (not the IP address).  Only used when overriding program path.  Blank for typical Program Properties.
    */
    public String ComputerName = new String();
}


