//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* An autocode automates entering procedures.  The user only has to pick composite, for instance, and the autocode figures out the code based on the number of surfaces, and posterior vs. anterior.  Autocodes also enforce and suggest changes to a procedure code if the number of surfaces or other properties change.
*/
public class AutoCode  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AutoCodeNum = new long();
    /**
    * Displays meaningful decription, like "Amalgam".
    */
    public String Description = new String();
    /**
    * User can hide autocodes
    */
    public boolean IsHidden = new boolean();
    /**
    * This will be true if user no longer wants to see this autocode message when closing a procedure. This makes it less intrusive, but it can still be used in procedure buttons.
    */
    public boolean LessIntrusive = new boolean();
}


