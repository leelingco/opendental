//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupClaimCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventHandler;

/**
* 
*/
public class __MultilookupClaimCompletedEventHandler   implements lookupClaimCompletedEventHandler
{
    public void invoke(Object sender, lookupClaimCompletedEventArgs e) throws Exception {
        IList<lookupClaimCompletedEventHandler> copy = new IList<lookupClaimCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupClaimCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupClaimCompletedEventHandler d = (lookupClaimCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupClaimCompletedEventHandler> _invocationList = new ArrayList<lookupClaimCompletedEventHandler>();
    public static lookupClaimCompletedEventHandler combine(lookupClaimCompletedEventHandler a, lookupClaimCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupClaimCompletedEventHandler ret = new __MultilookupClaimCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupClaimCompletedEventHandler remove(lookupClaimCompletedEventHandler a, lookupClaimCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupClaimCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupClaimCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupClaimCompletedEventHandler ret = new __MultilookupClaimCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupClaimCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


