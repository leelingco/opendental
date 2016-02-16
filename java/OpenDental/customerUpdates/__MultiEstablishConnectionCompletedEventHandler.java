//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.__MultiEstablishConnectionCompletedEventHandler;
import OpenDental.customerUpdates.EstablishConnectionCompletedEventArgs;
import OpenDental.customerUpdates.EstablishConnectionCompletedEventHandler;

/**
* 
*/
public class __MultiEstablishConnectionCompletedEventHandler   implements EstablishConnectionCompletedEventHandler
{
    public void invoke(Object sender, EstablishConnectionCompletedEventArgs e) throws Exception {
        IList<EstablishConnectionCompletedEventHandler> copy = new IList<EstablishConnectionCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<EstablishConnectionCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            EstablishConnectionCompletedEventHandler d = (EstablishConnectionCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<EstablishConnectionCompletedEventHandler> _invocationList = new ArrayList<EstablishConnectionCompletedEventHandler>();
    public static EstablishConnectionCompletedEventHandler combine(EstablishConnectionCompletedEventHandler a, EstablishConnectionCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiEstablishConnectionCompletedEventHandler ret = new __MultiEstablishConnectionCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static EstablishConnectionCompletedEventHandler remove(EstablishConnectionCompletedEventHandler a, EstablishConnectionCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<EstablishConnectionCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<EstablishConnectionCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiEstablishConnectionCompletedEventHandler ret = new __MultiEstablishConnectionCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<EstablishConnectionCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


