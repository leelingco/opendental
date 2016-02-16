//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.IsPaidCustomerCompletedEventArgs;
import OpenDental.MobileWeb.IsPaidCustomerCompletedEventHandler;

public interface IsPaidCustomerCompletedEventHandler   
{
    void invoke(Object sender, IsPaidCustomerCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<IsPaidCustomerCompletedEventHandler> getInvocationList() throws Exception ;

}


