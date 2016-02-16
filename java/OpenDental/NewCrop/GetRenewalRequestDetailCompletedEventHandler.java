//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventHandler;

public interface GetRenewalRequestDetailCompletedEventHandler   
{
    void invoke(Object sender, GetRenewalRequestDetailCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetRenewalRequestDetailCompletedEventHandler> getInvocationList() throws Exception ;

}


