//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Adjustment;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* An adjustment in the patient account.  Usually, adjustments are very simple, just being assigned to one patient and provider.  But they can also be attached to a procedure to represent a discount on that procedure.  Attaching adjustments to procedures is not automated, so it is not very common.
*/
public class Adjustment  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AdjNum = new long();
    /**
    * The date that the adjustment shows in the patient account.
    */
    public DateTime AdjDate = new DateTime();
    /**
    * Amount of adjustment.  Can be pos or neg.
    */
    public double AdjAmt = new double();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to definition.DefNum.
    */
    public long AdjType = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * Note for this adjustment.
    */
    public String AdjNote = new String();
    /**
    * Procedure date.  Not when the adjustment was entered.  This is what the aging will be based on in a future version.
    */
    public DateTime ProcDate = new DateTime();
    /**
    * FK to procedurelog.ProcNum.  Only used if attached to a procedure.  Otherwise, 0.
    */
    public long ProcNum = new long();
    /**
    * Timestamp automatically generated and user not allowed to change.  The actual date of entry.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * FK to clinic.ClinicNum.
    */
    public long ClinicNum = new long();
    /**
    * FK to statement.StatementNum.  Only used when the statement in an invoice.
    */
    public long StatementNum = new long();
    /**
    * 
    */
    public Adjustment clone() {
        try
        {
            return (Adjustment)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


