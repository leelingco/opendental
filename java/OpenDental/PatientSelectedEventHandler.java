//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PatientSelectedEventArgs;
import OpenDental.PatientSelectedEventHandler;

public interface PatientSelectedEventHandler   
{
    void invoke(Object sender, PatientSelectedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<PatientSelectedEventHandler> getInvocationList() throws Exception ;

}


