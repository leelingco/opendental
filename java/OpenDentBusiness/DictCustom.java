//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Spell check custom dictionary, shared by the whole office.
*/
public class DictCustom  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DictCustomNum = new long();
    /**
    * No space or punctuation allowed.
    */
    public String WordText = new String();
}


