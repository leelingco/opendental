//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;


public enum ProcUnitQtyType
{
    /**
    * 0-Only allowed on dental, and only option allowed on dental.  This is also the default for all procs in our UI.  For example, 4 PAs all on one line on the e-claim.
    */
    MultProcs,
    /**
    * 1-Only allowed on medical SV103.
    */
    MinutesAnesth,
    /**
    * 2-Allowed on medical SV103 and institutional SV204.  This is the default for both medical and inst when creating X12 claims, regardless of what is set on the proc.
    */
    ServiceUnits,
    /**
    * 3-Only allowed on institutional SV204.
    */
    Days
}

