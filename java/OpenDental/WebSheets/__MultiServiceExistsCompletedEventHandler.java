//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiServiceExistsCompletedEventHandler;
import OpenDental.WebSheets.ServiceExistsCompletedEventArgs;
import OpenDental.WebSheets.ServiceExistsCompletedEventHandler;

/**
* 
*/
public class __MultiServiceExistsCompletedEventHandler   implements ServiceExistsCompletedEventHandler
{
    public void invoke(Object sender, ServiceExistsCompletedEventArgs e) throws Exception {
        IList<ServiceExistsCompletedEventHandler> copy = new IList<ServiceExistsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ServiceExistsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ServiceExistsCompletedEventHandler d = (ServiceExistsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ServiceExistsCompletedEventHandler> _invocationList = new ArrayList<ServiceExistsCompletedEventHandler>();
    public static ServiceExistsCompletedEventHandler combine(ServiceExistsCompletedEventHandler a, ServiceExistsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiServiceExistsCompletedEventHandler ret = new __MultiServiceExistsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ServiceExistsCompletedEventHandler remove(ServiceExistsCompletedEventHandler a, ServiceExistsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ServiceExistsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ServiceExistsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiServiceExistsCompletedEventHandler ret = new __MultiServiceExistsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ServiceExistsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


