//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultiisValidExpirationCompletedEventHandler;
import OpenDental.PayConnectService.isValidExpirationCompletedEventArgs;
import OpenDental.PayConnectService.isValidExpirationCompletedEventHandler;

/**
* 
*/
public class __MultiisValidExpirationCompletedEventHandler   implements isValidExpirationCompletedEventHandler
{
    public void invoke(Object sender, isValidExpirationCompletedEventArgs e) throws Exception {
        IList<isValidExpirationCompletedEventHandler> copy = new IList<isValidExpirationCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<isValidExpirationCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            isValidExpirationCompletedEventHandler d = (isValidExpirationCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<isValidExpirationCompletedEventHandler> _invocationList = new ArrayList<isValidExpirationCompletedEventHandler>();
    public static isValidExpirationCompletedEventHandler combine(isValidExpirationCompletedEventHandler a, isValidExpirationCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiisValidExpirationCompletedEventHandler ret = new __MultiisValidExpirationCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static isValidExpirationCompletedEventHandler remove(isValidExpirationCompletedEventHandler a, isValidExpirationCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<isValidExpirationCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<isValidExpirationCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiisValidExpirationCompletedEventHandler ret = new __MultiisValidExpirationCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<isValidExpirationCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


