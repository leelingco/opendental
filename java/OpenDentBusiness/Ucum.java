//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.Ucum;

/**
* Unified Code for Units of Measure.  UCUM is not a stricly defined list of codes but is instead a language definition that allows for all units and derived units to be named.  Examples: g (grams), g/L (grams per liter), g/L/s (grams per liter per second), g/L/s/s (grams per liter per second per second), etc... are all allowed units meaning there is an infinite number of units that can be defined using UCUM conventions.  The codes stored in this table are merely a common subset that was readily available and premade.
*/
public class Ucum  extends TableBase 
{
    /**
    * Primary key.
    */
    public long UcumNum = new long();
    /**
    * Indexed.  Also called concept code. Example: mol/mL
    */
    public String UcumCode = new String();
    /**
    * Also called Concept Name.  Human readable form of the UCUM code. Example: Moles Per MilliLiter [Substance Concentration Units]
    */
    public String Description = new String();
    /**
    * True if this unit of measure is or has ever been in use.  Useful for assisting users to select common units.
    */
    public boolean IsInUse = new boolean();
    public Ucum copy() throws Exception {
        return (Ucum)this.MemberwiseClone();
    }

    public Ucum() throws Exception {
    }

}


