//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.TreatPlan;

/**
* A treatment plan saved by a user.  Does not include the default tp, which is just a list of procedurelog entries with a status of tp.  A treatplan has many proctp's attached to it.
*/
public class TreatPlan  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TreatPlanNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * The date of the treatment plan
    */
    public DateTime DateTP = new DateTime();
    /**
    * The heading that shows at the top of the treatment plan.  Usually 'Proposed Treatment Plan'
    */
    public String Heading = new String();
    /**
    * A note specific to this treatment plan that shows at the bottom.
    */
    public String Note = new String();
    /**
    * The encrypted and bound signature in base64 format.  The signature is bound to the concatenation of the tp Note, DateTP, and to each proctp Descript and PatAmt.
    */
    public String Signature = new String();
    /**
    * True if the signature is in Topaz format rather than OD format.
    */
    public boolean SigIsTopaz = new boolean();
    /**
    * FK to patient.PatNum. Can be 0.  The patient responsible for approving the treatment.  Public health field not visible to everyone else.
    */
    public long ResponsParty = new long();
    /**
    * 
    */
    public TreatPlan copy() throws Exception {
        return (TreatPlan)MemberwiseClone();
    }

}


