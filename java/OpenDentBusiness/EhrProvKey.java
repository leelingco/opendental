//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrProvKey;
import OpenDentBusiness.TableBase;

/**
* Only used by OD customer support to store and track Ehr Provider Keys for customers.
*/
public class EhrProvKey  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrProvKeyNum = new long();
    /**
    * FK to patient.PatNum. There can be multiple EhrProvKeys per patient/customer.
    */
    public long PatNum = new long();
    /**
    * The provider LName.
    */
    public String LName = new String();
    /**
    * The provider FName.
    */
    public String FName = new String();
    /**
    * The key assigned to the provider
    */
    public String ProvKey = new String();
    /**
    * Usually 1.  Can be less, like .5 or .25 to indicate possible discount is justified.
    */
    public float FullTimeEquiv = new float();
    /**
    * Any notes that the tech wishes to include regarding this situation.
    */
    public String Notes = new String();
    /**
    * True if the provider has access to the reports needed to show MU.  Changing this will require a new provider key.
    */
    public boolean HasReportAccess = new boolean();
    /**
    * 
    */
    public EhrProvKey copy() throws Exception {
        return (EhrProvKey)MemberwiseClone();
    }

}


