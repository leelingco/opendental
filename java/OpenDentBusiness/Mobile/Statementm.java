//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Statementm;
import OpenDentBusiness.TableBase;

/**
* Links allergies to patients. Patient portal version
*/
public class Statementm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long StatementNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * This will always be a valid and reasonable date regardless of whether it's actually been sent yet.
    */
    public DateTime DateSent = new DateTime();
    /**
    * FK to document.DocNum when a pdf has been archived.
    */
    public long DocNum = new long();
    /**
    * 
    */
    public Statementm copy() throws Exception {
        return (Statementm)this.MemberwiseClone();
    }

}


