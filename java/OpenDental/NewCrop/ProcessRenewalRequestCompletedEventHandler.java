//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventArgs;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventHandler;

public interface ProcessRenewalRequestCompletedEventHandler   
{
    void invoke(Object sender, ProcessRenewalRequestCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<ProcessRenewalRequestCompletedEventHandler> getInvocationList() throws Exception ;

}


