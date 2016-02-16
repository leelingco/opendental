//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum OrionStatus
{
    /**
    * 0- None.  While a normal orion proc would never have this status2, it is still needed for flags in ChartViews.  And it's also possible that a status2 slipped through the cracks and was not assigned, leaving it with this value.
    */
    None,
    /**
    * 1– Treatment planned
    */
    TP,
    /**
    * 2– Completed
    */
    C,
    /**
    * 4– Existing prior to incarceration
    */
    E,
    /**
    * 8– Refused treatment
    */
    R,
    /**
    * 16– Referred out to specialist
    */
    RO,
    /**
    * 32– Completed by specialist
    */
    CS,
    /**
    * 64– Completed by registry
    */
    CR,
    /**
    * 128- Cancelled, tx plan changed
    */
    CA_Tx,
    /**
    * 256- Cancelled, eligible parole
    */
    CA_EPRD,
    /**
    * 512- Cancelled, parole/discharge
    */
    CA_PD,
    /**
    * 1024– Suspended, unacceptable plaque
    */
    S,
    /**
    * 2048- Stop clock, multi visit
    */
    ST,
    /**
    * 4096– Watch
    */
    W,
    /**
    * 8192– Alternative
    */
    A
}

