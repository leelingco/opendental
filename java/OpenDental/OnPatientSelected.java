//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.OnPatientSelected;
import OpenDentBusiness.Patient;

public interface OnPatientSelected   
{
    void invoke(Patient pat) throws Exception ;

    System.Collections.Generic.IList<OnPatientSelected> getInvocationList() throws Exception ;

}


