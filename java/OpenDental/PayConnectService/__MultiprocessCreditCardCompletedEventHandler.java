//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultiprocessCreditCardCompletedEventHandler;
import OpenDental.PayConnectService.processCreditCardCompletedEventArgs;
import OpenDental.PayConnectService.processCreditCardCompletedEventHandler;

/**
* 
*/
public class __MultiprocessCreditCardCompletedEventHandler   implements processCreditCardCompletedEventHandler
{
    public void invoke(Object sender, processCreditCardCompletedEventArgs e) throws Exception {
        IList<processCreditCardCompletedEventHandler> copy = new IList<processCreditCardCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<processCreditCardCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            processCreditCardCompletedEventHandler d = (processCreditCardCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<processCreditCardCompletedEventHandler> _invocationList = new ArrayList<processCreditCardCompletedEventHandler>();
    public static processCreditCardCompletedEventHandler combine(processCreditCardCompletedEventHandler a, processCreditCardCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiprocessCreditCardCompletedEventHandler ret = new __MultiprocessCreditCardCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static processCreditCardCompletedEventHandler remove(processCreditCardCompletedEventHandler a, processCreditCardCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<processCreditCardCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<processCreditCardCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiprocessCreditCardCompletedEventHandler ret = new __MultiprocessCreditCardCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<processCreditCardCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


