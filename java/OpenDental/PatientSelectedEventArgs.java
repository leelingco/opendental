//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.Patient;

/**
* 
*/
public class PatientSelectedEventArgs   
{
    private Patient pat;
    /**
    * 
    */
    public PatientSelectedEventArgs(Patient pat) throws Exception {
        this.pat = pat;
    }

    /**
    * 
    */
    public Patient getPat() throws Exception {
        return pat;
    }

}


