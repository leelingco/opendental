//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DoseCheckCompletedEventArgs;
import OpenDental.NewCrop.DoseCheckCompletedEventHandler;

public interface DoseCheckCompletedEventHandler   
{
    void invoke(Object sender, DoseCheckCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<DoseCheckCompletedEventHandler> getInvocationList() throws Exception ;

}


