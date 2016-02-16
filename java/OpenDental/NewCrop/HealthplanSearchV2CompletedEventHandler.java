//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventArgs;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventHandler;

public interface HealthplanSearchV2CompletedEventHandler   
{
    void invoke(Object sender, HealthplanSearchV2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<HealthplanSearchV2CompletedEventHandler> getInvocationList() throws Exception ;

}


