//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrderType;
import OpenDentBusiness.TableBase;

/**
* Ehr. Lab and radiology orders.  Medication orders are simply fields in medicationPat.
*/
public class MedicalOrder  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MedicalOrderNum = new long();
    /**
    * Enum:MedicalOrderType Laboratory=0,Radiology=1.
    */
    public MedicalOrderType MedOrderType = MedicalOrderType.Laboratory;
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * Date and time of order.
    */
    public DateTime DateTimeOrder = new DateTime();
    /**
    * User will be required to type entire order out from scratch.
    */
    public String Description = new String();
    /**
    * EHR requires Active/Discontinued status. 0=Active, 1=Discontinued.
    */
    public boolean IsDiscontinued = new boolean();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * 
    */
    public MedicalOrder copy() throws Exception {
        return (MedicalOrder)this.MemberwiseClone();
    }

}


