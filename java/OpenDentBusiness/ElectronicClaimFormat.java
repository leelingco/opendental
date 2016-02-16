//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ElectronicClaimFormat
{
    /**
    * For every type of electronic claim format that Open Dental can create, there will be an item in this enumeration.  All e-claim formats are hard coded due to complexity.0-Not in database, but used in various places in program.
    */
    None,
    /**
    * 1-The American standard through 12/31/11.
    */
    x837D_4010,
    /**
    * 2-Proprietary format for Renaissance.
    */
    Renaissance,
    /**
    * 3-CDAnet format version 4.
    */
    Canadian,
    /**
    * 4-CSV file adaptable for use in Netherlands.
    */
    Dutch,
    /**
    * 5-The American standard starting on 1/1/12.
    */
    x837D_5010_dental,
    /**
    * 6-Either professional or medical.  The distiction is stored at the claim level.
    */
    x837_5010_med_inst
}

