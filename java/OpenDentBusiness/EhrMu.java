//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrMeasureType;
import OpenDentBusiness.MuMet;

/**
* Helps track whether the current patient has met the measurement objectives.
*/
public class EhrMu   
{
    /**
    * 
    */
    public EhrMeasureType MeasureType = EhrMeasureType.ProblemList;
    /**
    * 
    */
    public MuMet Met = MuMet.False;
    /**
    * 
    */
    public String Details = new String();
    /**
    * 
    */
    public String Action = new String();
    /**
    * 
    */
    public String Action2 = new String();
}


