//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventArgs;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventHandler;

public interface GetPatientFreeFormAllergyHistoryCompletedEventHandler   
{
    void invoke(Object sender, GetPatientFreeFormAllergyHistoryCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> getInvocationList() throws Exception ;

}


