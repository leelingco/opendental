//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultilookupFamilyEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupFamilyEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupFamilyEligibilityCompletedEventHandler;

/**
* 
*/
public class __MultilookupFamilyEligibilityCompletedEventHandler   implements lookupFamilyEligibilityCompletedEventHandler
{
    public void invoke(Object sender, lookupFamilyEligibilityCompletedEventArgs e) throws Exception {
        IList<lookupFamilyEligibilityCompletedEventHandler> copy = new IList<lookupFamilyEligibilityCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<lookupFamilyEligibilityCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            lookupFamilyEligibilityCompletedEventHandler d = (lookupFamilyEligibilityCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<lookupFamilyEligibilityCompletedEventHandler> _invocationList = new ArrayList<lookupFamilyEligibilityCompletedEventHandler>();
    public static lookupFamilyEligibilityCompletedEventHandler combine(lookupFamilyEligibilityCompletedEventHandler a, lookupFamilyEligibilityCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultilookupFamilyEligibilityCompletedEventHandler ret = new __MultilookupFamilyEligibilityCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static lookupFamilyEligibilityCompletedEventHandler remove(lookupFamilyEligibilityCompletedEventHandler a, lookupFamilyEligibilityCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<lookupFamilyEligibilityCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<lookupFamilyEligibilityCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultilookupFamilyEligibilityCompletedEventHandler ret = new __MultilookupFamilyEligibilityCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<lookupFamilyEligibilityCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


