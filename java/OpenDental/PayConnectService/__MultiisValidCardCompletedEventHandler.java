//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultiisValidCardCompletedEventHandler;
import OpenDental.PayConnectService.isValidCardCompletedEventArgs;
import OpenDental.PayConnectService.isValidCardCompletedEventHandler;

/**
* 
*/
public class __MultiisValidCardCompletedEventHandler   implements isValidCardCompletedEventHandler
{
    public void invoke(Object sender, isValidCardCompletedEventArgs e) throws Exception {
        IList<isValidCardCompletedEventHandler> copy = new IList<isValidCardCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<isValidCardCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            isValidCardCompletedEventHandler d = (isValidCardCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<isValidCardCompletedEventHandler> _invocationList = new ArrayList<isValidCardCompletedEventHandler>();
    public static isValidCardCompletedEventHandler combine(isValidCardCompletedEventHandler a, isValidCardCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiisValidCardCompletedEventHandler ret = new __MultiisValidCardCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static isValidCardCompletedEventHandler remove(isValidCardCompletedEventHandler a, isValidCardCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<isValidCardCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<isValidCardCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiisValidCardCompletedEventHandler ret = new __MultiisValidCardCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<isValidCardCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


