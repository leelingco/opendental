//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcTP;
import OpenDentBusiness.TableBase;

/**
* These are copies of procedures that are attached to treatment plans.
*/
public class ProcTP  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcTPNum = new long();
    /**
    * FK to treatplan.TreatPlanNum.  The treatment plan to which this proc is attached.
    */
    public long TreatPlanNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to procedurelog.ProcNum.  It is very common for the referenced procedure to be missing.  This procNum is only here to compare and test the existence of the referenced procedure.  If present, it will check to see whether the procedure is still status TP.
    */
    public long ProcNumOrig = new long();
    /**
    * The order of this proc within its tp.  This is set when the tp is first created and can't be changed.  Drastically simplifies loading the tp.
    */
    public int ItemOrder = new int();
    /**
    * FK to definition.DefNum which contains the text of the priority.
    */
    public long Priority = new long();
    /**
    * A simple string displaying the tooth number.  If international tooth numbers are used, then this will be in international format already.
    */
    public String ToothNumTP = new String();
    /**
    * Tooth surfaces or area.  This is already converted for international use.  If arch or quad, then it will have U,LR, etc.
    */
    public String Surf = new String();
    /**
    * Not a foreign key.  Simply display text.  Can be changed by user at any time.
    */
    public String ProcCode = new String();
    /**
    * Description is originally copied from procedurecode.Descript, but user can change it.
    */
    public String Descript = new String();
    /**
    * The fee charged to the patient. Never gets automatically updated.
    */
    public double FeeAmt = new double();
    /**
    * The amount primary insurance is expected to pay. Never gets automatically updated.
    */
    public double PriInsAmt = new double();
    /**
    * The amount secondary insurance is expected to pay. Never gets automatically updated.
    */
    public double SecInsAmt = new double();
    /**
    * The amount the patient is expected to pay. Never gets automatically updated.
    */
    public double PatAmt = new double();
    /**
    * The amount of discount.  Currently only used for PPOs.
    */
    public double Discount = new double();
    /**
    * Text from prognosis definition.  Can be changed by user at any time.
    */
    public String Prognosis = new String();
    /**
    * Text from diagnosis definition.  Can be changed by user at any time.
    */
    public String Dx = new String();
    /**
    * 
    */
    public ProcTP copy() throws Exception {
        return (ProcTP)MemberwiseClone();
    }

}


