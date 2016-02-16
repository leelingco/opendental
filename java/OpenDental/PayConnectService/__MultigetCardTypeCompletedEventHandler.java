//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultigetCardTypeCompletedEventHandler;
import OpenDental.PayConnectService.getCardTypeCompletedEventArgs;
import OpenDental.PayConnectService.getCardTypeCompletedEventHandler;

/**
* 
*/
public class __MultigetCardTypeCompletedEventHandler   implements getCardTypeCompletedEventHandler
{
    public void invoke(Object sender, getCardTypeCompletedEventArgs e) throws Exception {
        IList<getCardTypeCompletedEventHandler> copy = new IList<getCardTypeCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<getCardTypeCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            getCardTypeCompletedEventHandler d = (getCardTypeCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<getCardTypeCompletedEventHandler> _invocationList = new ArrayList<getCardTypeCompletedEventHandler>();
    public static getCardTypeCompletedEventHandler combine(getCardTypeCompletedEventHandler a, getCardTypeCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultigetCardTypeCompletedEventHandler ret = new __MultigetCardTypeCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static getCardTypeCompletedEventHandler remove(getCardTypeCompletedEventHandler a, getCardTypeCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<getCardTypeCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<getCardTypeCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultigetCardTypeCompletedEventHandler ret = new __MultigetCardTypeCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<getCardTypeCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


