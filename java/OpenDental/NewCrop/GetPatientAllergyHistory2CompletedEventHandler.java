//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventArgs;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventHandler;

public interface GetPatientAllergyHistory2CompletedEventHandler   
{
    void invoke(Object sender, GetPatientAllergyHistory2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetPatientAllergyHistory2CompletedEventHandler> getInvocationList() throws Exception ;

}


