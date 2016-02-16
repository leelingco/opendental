//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetSideEffectsCompletedEventArgs;
import OpenDental.NewCrop.GetSideEffectsCompletedEventHandler;

public interface GetSideEffectsCompletedEventHandler   
{
    void invoke(Object sender, GetSideEffectsCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetSideEffectsCompletedEventHandler> getInvocationList() throws Exception ;

}


