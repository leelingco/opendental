//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Stores small bits of data for a wide variety of purposes.  Any data that's too small to warrant its own table will usually end up here.
*/
public class Pref  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PrefNum = new long();
    /**
    * The text 'key' in the key/value pairing.
    */
    public String PrefName = new String();
    /**
    * The stored value.
    */
    public String ValueString = new String();
    /**
    * Documentation on usage and values of each pref.  Mostly deprecated now in favor of using XML comments in the code.
    */
    public String Comments = new String();
}


