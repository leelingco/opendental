//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.DiseaseDefm;
import OpenDentBusiness.TableBase;

/**
* A list of diseases that can be assigned to patients.
*/
public class DiseaseDefm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long DiseaseDefNum = new long();
    /**
    * .
    */
    public String DiseaseName = new String();
    /**
    * 
    */
    public DiseaseDefm copy() throws Exception {
        return (DiseaseDefm)this.MemberwiseClone();
    }

}


