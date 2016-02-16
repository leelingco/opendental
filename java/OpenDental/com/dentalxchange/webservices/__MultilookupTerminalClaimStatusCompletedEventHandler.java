//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupTerminalClaimStatusCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupTerminalClaimStatusCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupTerminalClaimStatusCompletedEventHandler;

/**
* 
*/
public class __MultilookupTerminalClaimStatusCompletedEventHandler   implements lookupTerminalClaimStatusCompletedEventHandler
{
    public void invoke(Object sender, lookupTerminalClaimStatusCompletedEventArgs e) throws Exception {
        IList<lookupTerminalClaimStatusCompletedEventHandler> copy = new IList<lookupTerminalClaimStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupTerminalClaimStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupTerminalClaimStatusCompletedEventHandler d = (lookupTerminalClaimStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupTerminalClaimStatusCompletedEventHandler> _invocationList = new ArrayList<lookupTerminalClaimStatusCompletedEventHandler>();
    public static lookupTerminalClaimStatusCompletedEventHandler combine(lookupTerminalClaimStatusCompletedEventHandler a, lookupTerminalClaimStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupTerminalClaimStatusCompletedEventHandler ret = new __MultilookupTerminalClaimStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupTerminalClaimStatusCompletedEventHandler remove(lookupTerminalClaimStatusCompletedEventHandler a, lookupTerminalClaimStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupTerminalClaimStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupTerminalClaimStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupTerminalClaimStatusCompletedEventHandler ret = new __MultilookupTerminalClaimStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupTerminalClaimStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


