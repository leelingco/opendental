//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.FeatureRequestDiscussSubmitCompletedEventArgs;
import OpenDental.customerUpdates.FeatureRequestDiscussSubmitCompletedEventHandler;

public interface FeatureRequestDiscussSubmitCompletedEventHandler   
{
    void invoke(Object sender, FeatureRequestDiscussSubmitCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<FeatureRequestDiscussSubmitCompletedEventHandler> getInvocationList() throws Exception ;

}


