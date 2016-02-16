//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITS;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITS.PutFileExtCompletedEventArgs;
import OpenDental.EmdeonITS.PutFileExtCompletedEventHandler;

public interface PutFileExtCompletedEventHandler   
{
    void invoke(Object sender, PutFileExtCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<PutFileExtCompletedEventHandler> getInvocationList() throws Exception ;

}


