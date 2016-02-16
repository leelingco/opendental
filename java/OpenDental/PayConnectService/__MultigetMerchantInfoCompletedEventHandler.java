//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultigetMerchantInfoCompletedEventHandler;
import OpenDental.PayConnectService.getMerchantInfoCompletedEventArgs;
import OpenDental.PayConnectService.getMerchantInfoCompletedEventHandler;

/**
* 
*/
public class __MultigetMerchantInfoCompletedEventHandler   implements getMerchantInfoCompletedEventHandler
{
    public void invoke(Object sender, getMerchantInfoCompletedEventArgs e) throws Exception {
        IList<getMerchantInfoCompletedEventHandler> copy = new IList<getMerchantInfoCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<getMerchantInfoCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            getMerchantInfoCompletedEventHandler d = (getMerchantInfoCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<getMerchantInfoCompletedEventHandler> _invocationList = new ArrayList<getMerchantInfoCompletedEventHandler>();
    public static getMerchantInfoCompletedEventHandler combine(getMerchantInfoCompletedEventHandler a, getMerchantInfoCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultigetMerchantInfoCompletedEventHandler ret = new __MultigetMerchantInfoCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static getMerchantInfoCompletedEventHandler remove(getMerchantInfoCompletedEventHandler a, getMerchantInfoCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<getMerchantInfoCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<getMerchantInfoCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultigetMerchantInfoCompletedEventHandler ret = new __MultigetMerchantInfoCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<getMerchantInfoCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


