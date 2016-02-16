//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import WebHostSynch.RegistrationKey;

/**
* Keeps track of which product keys have been assigned to which customers. This datatype is only used if the program is being run from a distributor installation. A single customer is allowed to have more than one key, to accommodate for various circumstances, including having multiple physical business locations.
*/
public class RegistrationKey   
{
    /**
    * Primary Key.
    */
    public long RegistrationKeyNum = new long();
    /**
    * FK to patient.PatNum. The customer to which this registration key applies.
    */
    public long PatNum = new long();
    /**
    * The registration key as stored in the customer database.
    */
    public String RegKey = new String();
    /**
    * Db note about the registration key. Specifically, the note must include information about the location to which this key pertains, since once at least one key must be assigned to each location to be legal.
    */
    public String Note = new String();
    /**
    * This will help later with tracking for licensing.
    */
    public DateTime DateStarted = new DateTime();
    /**
    * This is used to completely disable a key.  Might possibly even cripple the user's program.  Usually only used if reassigning another key due to abuse or error.  If no date specified, then this key is still valid.
    */
    public DateTime DateDisabled = new DateTime();
    /**
    * This is used when the customer cancels monthly support.  This still allows the customer to get downloads for bug fixes, but only up through a certain version.  Our web server program will use this date to deduce which version they are allowed to have.  Any version that was released as a beta before this date is allowed to be downloaded.
    */
    public DateTime DateEnded = new DateTime();
    /**
    * This is assigned automatically based on whether the registration key is a US version vs. a foreign version.  The foreign version is not able to unlock the procedure codes.  There are muliple layers of safeguards in place.
    */
    public boolean IsForeign = new boolean();
    /**
    * Deprecated.
    */
    public boolean UsesServerVersion = new boolean();
    /**
    * We have given this customer a free version.  Typically in India.
    */
    public boolean IsFreeVersion = new boolean();
    /**
    * This customer is not using the software with live patient data, but only for testing and development purposes.
    */
    public boolean IsOnlyForTesting = new boolean();
    /**
    * Typically 100, although it can be more for multilocation offices.
    */
    public int VotesAllotted = new int();
    public RegistrationKey copy() throws Exception {
        return (RegistrationKey)this.MemberwiseClone();
    }

}


