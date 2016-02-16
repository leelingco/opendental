//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental;


/**
* Holds information about a provider's Schedule. Not actual database table.
*/
public class ApptSearchProviderSchedule   
{
    /**
    * FK to Provider
    */
    public long ProviderNum = new long();
    /**
    * Date of the ProviderSchedule.
    */
    public DateTime SchedDate = new DateTime();
    /**
    * This contains a bool for each 5 minute block throughout the day. True means provider is scheduled to work, False means provider is not scheduled to work.
    */
    public boolean[] ProvSchedule = new boolean[]();
    /**
    * This contains a bool for each 5 minute block throughout the day. True means available, False means something is scheduled there or the provider is not scheduled to work.
    */
    public boolean[] ProvBar = new boolean[]();
    /**
    * Constructor.
    */
    public ApptSearchProviderSchedule() throws Exception {
        ProvSchedule = new boolean[288];
        ProvBar = new boolean[288];
    }

}


