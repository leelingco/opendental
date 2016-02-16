//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventArgs;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventHandler;

public interface DrugAllergyInteractionV2CompletedEventHandler   
{
    void invoke(Object sender, DrugAllergyInteractionV2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<DrugAllergyInteractionV2CompletedEventHandler> getInvocationList() throws Exception ;

}


