//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.FeatureRequestDiscussGetListCompletedEventArgs;
import OpenDental.localhost.FeatureRequestDiscussGetListCompletedEventHandler;

public interface FeatureRequestDiscussGetListCompletedEventHandler   
{
    void invoke(Object sender, FeatureRequestDiscussGetListCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<FeatureRequestDiscussGetListCompletedEventHandler> getInvocationList() throws Exception ;

}


