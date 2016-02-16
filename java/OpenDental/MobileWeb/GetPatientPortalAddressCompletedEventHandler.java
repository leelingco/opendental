//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.GetPatientPortalAddressCompletedEventArgs;
import OpenDental.MobileWeb.GetPatientPortalAddressCompletedEventHandler;

public interface GetPatientPortalAddressCompletedEventHandler   
{
    void invoke(Object sender, GetPatientPortalAddressCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetPatientPortalAddressCompletedEventHandler> getInvocationList() throws Exception ;

}


