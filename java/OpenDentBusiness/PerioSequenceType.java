//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum PerioSequenceType
{
    /**
    * In perio, the type of measurements for a given row.0
    */
    Mobility,
    /**
    * 1
    */
    Furcation,
    /**
    * 2-AKA recession.
    */
    GingMargin,
    /**
    * 3-MucoGingivalJunction- the division between attached and unattached mucosa.
    */
    MGJ,
    /**
    * 4
    */
    Probing,
    /**
    * 5-For the skiptooth type, set surf to none, and ToothValue to 1.
    */
    SkipTooth,
    /**
    * 6. Sum of flags for bleeding(1), suppuration(2), plaque(4), and calculus(8).
    */
    Bleeding,
    /**
    * 7. But this type is never saved to the db. It is always calculated on the fly.
    */
    CAL
}

