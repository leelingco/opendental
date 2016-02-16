//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetSubmittedMessageTransactionStatusCompletedEventHandler;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventHandler;

/**
* 
*/
public class __MultiGetSubmittedMessageTransactionStatusCompletedEventHandler   implements GetSubmittedMessageTransactionStatusCompletedEventHandler
{
    public void invoke(Object sender, GetSubmittedMessageTransactionStatusCompletedEventArgs e) throws Exception {
        IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> copy = new IList<GetSubmittedMessageTransactionStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetSubmittedMessageTransactionStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetSubmittedMessageTransactionStatusCompletedEventHandler d = (GetSubmittedMessageTransactionStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> _invocationList = new ArrayList<GetSubmittedMessageTransactionStatusCompletedEventHandler>();
    public static GetSubmittedMessageTransactionStatusCompletedEventHandler combine(GetSubmittedMessageTransactionStatusCompletedEventHandler a, GetSubmittedMessageTransactionStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetSubmittedMessageTransactionStatusCompletedEventHandler ret = new __MultiGetSubmittedMessageTransactionStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetSubmittedMessageTransactionStatusCompletedEventHandler remove(GetSubmittedMessageTransactionStatusCompletedEventHandler a, GetSubmittedMessageTransactionStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetSubmittedMessageTransactionStatusCompletedEventHandler ret = new __MultiGetSubmittedMessageTransactionStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


