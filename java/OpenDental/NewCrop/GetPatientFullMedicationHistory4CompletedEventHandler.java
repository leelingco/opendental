//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventHandler;

public interface GetPatientFullMedicationHistory4CompletedEventHandler   
{
    void invoke(Object sender, GetPatientFullMedicationHistory4CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetPatientFullMedicationHistory4CompletedEventHandler> getInvocationList() throws Exception ;

}


