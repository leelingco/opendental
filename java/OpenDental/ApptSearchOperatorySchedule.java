//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental;


/**
* Holds information about a operatory's Schedule. Not actual database table.
*/
public class ApptSearchOperatorySchedule   
{
    /**
    * FK to Operatory
    */
    public long OperatoryNum = new long();
    /**
    * Date of the OperatorySchedule.
    */
    public DateTime SchedDate = new DateTime();
    /**
    * This contains a bool for each 5 minute block throughout the day. True means operatory is open, False means operatory is in use.
    */
    public boolean[] OperatorySched = new boolean[]();
    /**
    * List of providers 'allowed' to work in this operatory.
    */
    public List<long> ProviderNums = new List<long>();
}


