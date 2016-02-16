//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrTrigger;

/**
* Not a DB table. Used to pass intervention information to FormCDSI based on matched trigger.
*/
public class CDSIntervention   
{
    /**
    * The EHRtrigger that this CDSIntervention is generated from.
    */
    public EhrTrigger EhrTrigger;
    /**
    * The message generated for the user based on the specific objects that triggered the intervention.
    */
    public String InterventionMessage = new String();
    /**
    * The list of objects that will be passed to FormInfobutton.
    */
    public List<Object> TriggerObjects = new List<Object>();
}


