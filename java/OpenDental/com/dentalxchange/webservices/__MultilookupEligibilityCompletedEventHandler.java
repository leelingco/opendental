//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventHandler;

/**
* 
*/
public class __MultilookupEligibilityCompletedEventHandler   implements lookupEligibilityCompletedEventHandler
{
    public void invoke(Object sender, lookupEligibilityCompletedEventArgs e) throws Exception {
        IList<lookupEligibilityCompletedEventHandler> copy = new IList<lookupEligibilityCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupEligibilityCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupEligibilityCompletedEventHandler d = (lookupEligibilityCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupEligibilityCompletedEventHandler> _invocationList = new ArrayList<lookupEligibilityCompletedEventHandler>();
    public static lookupEligibilityCompletedEventHandler combine(lookupEligibilityCompletedEventHandler a, lookupEligibilityCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupEligibilityCompletedEventHandler ret = new __MultilookupEligibilityCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupEligibilityCompletedEventHandler remove(lookupEligibilityCompletedEventHandler a, lookupEligibilityCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupEligibilityCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupEligibilityCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupEligibilityCompletedEventHandler ret = new __MultilookupEligibilityCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupEligibilityCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


