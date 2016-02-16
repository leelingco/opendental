//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupTerminalEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupTerminalEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupTerminalEligibilityCompletedEventHandler;

/**
* 
*/
public class __MultilookupTerminalEligibilityCompletedEventHandler   implements lookupTerminalEligibilityCompletedEventHandler
{
    public void invoke(Object sender, lookupTerminalEligibilityCompletedEventArgs e) throws Exception {
        IList<lookupTerminalEligibilityCompletedEventHandler> copy = new IList<lookupTerminalEligibilityCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupTerminalEligibilityCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupTerminalEligibilityCompletedEventHandler d = (lookupTerminalEligibilityCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupTerminalEligibilityCompletedEventHandler> _invocationList = new ArrayList<lookupTerminalEligibilityCompletedEventHandler>();
    public static lookupTerminalEligibilityCompletedEventHandler combine(lookupTerminalEligibilityCompletedEventHandler a, lookupTerminalEligibilityCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupTerminalEligibilityCompletedEventHandler ret = new __MultilookupTerminalEligibilityCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupTerminalEligibilityCompletedEventHandler remove(lookupTerminalEligibilityCompletedEventHandler a, lookupTerminalEligibilityCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupTerminalEligibilityCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupTerminalEligibilityCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupTerminalEligibilityCompletedEventHandler ret = new __MultilookupTerminalEligibilityCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupTerminalEligibilityCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


