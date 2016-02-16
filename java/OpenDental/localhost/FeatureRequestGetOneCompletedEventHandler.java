//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.FeatureRequestGetOneCompletedEventArgs;
import OpenDental.localhost.FeatureRequestGetOneCompletedEventHandler;

public interface FeatureRequestGetOneCompletedEventHandler   
{
    void invoke(Object sender, FeatureRequestGetOneCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<FeatureRequestGetOneCompletedEventHandler> getInvocationList() throws Exception ;

}


