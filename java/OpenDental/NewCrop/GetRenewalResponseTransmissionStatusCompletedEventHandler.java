//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventHandler;

public interface GetRenewalResponseTransmissionStatusCompletedEventHandler   
{
    void invoke(Object sender, GetRenewalResponseTransmissionStatusCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> getInvocationList() throws Exception ;

}


