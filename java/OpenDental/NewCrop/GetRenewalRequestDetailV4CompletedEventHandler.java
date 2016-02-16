//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventHandler;

public interface GetRenewalRequestDetailV4CompletedEventHandler   
{
    void invoke(Object sender, GetRenewalRequestDetailV4CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetRenewalRequestDetailV4CompletedEventHandler> getInvocationList() throws Exception ;

}


