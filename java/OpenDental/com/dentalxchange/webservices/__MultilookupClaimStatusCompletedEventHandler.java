//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupClaimStatusCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupClaimStatusCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupClaimStatusCompletedEventHandler;

/**
* 
*/
public class __MultilookupClaimStatusCompletedEventHandler   implements lookupClaimStatusCompletedEventHandler
{
    public void invoke(Object sender, lookupClaimStatusCompletedEventArgs e) throws Exception {
        IList<lookupClaimStatusCompletedEventHandler> copy = new IList<lookupClaimStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupClaimStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupClaimStatusCompletedEventHandler d = (lookupClaimStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupClaimStatusCompletedEventHandler> _invocationList = new ArrayList<lookupClaimStatusCompletedEventHandler>();
    public static lookupClaimStatusCompletedEventHandler combine(lookupClaimStatusCompletedEventHandler a, lookupClaimStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupClaimStatusCompletedEventHandler ret = new __MultilookupClaimStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupClaimStatusCompletedEventHandler remove(lookupClaimStatusCompletedEventHandler a, lookupClaimStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupClaimStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupClaimStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupClaimStatusCompletedEventHandler ret = new __MultilookupClaimStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupClaimStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


