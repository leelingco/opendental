//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.TableBase;

/**
* An individual procedure code color range.
*/
public class ProcApptColor  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcApptColorNum = new long();
    /**
    * Procedure code range defined by user.  Includes commas and dashes, but no spaces.  The codes need not be valid since they are ranges.
    */
    public String CodeRange = new String();
    /**
    * Adds most recent completed date to ProcsColored
    */
    public boolean ShowPreviousDate = new boolean();
    /**
    * Color that shows in appointments
    */
    public Color ColorText = new Color();
    public ProcApptColor copy() throws Exception {
        return (ProcApptColor)this.MemberwiseClone();
    }

}


