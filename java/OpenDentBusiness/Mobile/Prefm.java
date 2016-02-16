//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.TableBase;

/**
* This table is called preference in the mobile database.  This is to simply to avoid having to rewrite DataConnection.TestConnection().  The primary key of this table has an m in it to remind us that the preferences are totally different than in the main program.
*/
public class Prefm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long PrefNum = new long();
    /**
    * The text 'key' in the key/value pairing.
    */
    public String PrefmName = new String();
    // this is named PrefmName rather than PrefName because there would be name ambiguity with Pref.PrefName which would cause a compilation error in the main program whereever PrefName (Pref.PrefName) is used.
    /**
    * The stored value.
    */
    public String ValueString = new String();
}


