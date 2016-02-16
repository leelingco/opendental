//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventHandler;

public interface GetAllRenewalRequestsV3CompletedEventHandler   
{
    void invoke(Object sender, GetAllRenewalRequestsV3CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetAllRenewalRequestsV3CompletedEventHandler> getInvocationList() throws Exception ;

}


