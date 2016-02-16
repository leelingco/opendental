//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiSendMissingHealthplanInformationCompletedEventHandler;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventArgs;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventHandler;

/**
* 
*/
public class __MultiSendMissingHealthplanInformationCompletedEventHandler   implements SendMissingHealthplanInformationCompletedEventHandler
{
    public void invoke(Object sender, SendMissingHealthplanInformationCompletedEventArgs e) throws Exception {
        IList<SendMissingHealthplanInformationCompletedEventHandler> copy = new IList<SendMissingHealthplanInformationCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SendMissingHealthplanInformationCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SendMissingHealthplanInformationCompletedEventHandler d = (SendMissingHealthplanInformationCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SendMissingHealthplanInformationCompletedEventHandler> _invocationList = new ArrayList<SendMissingHealthplanInformationCompletedEventHandler>();
    public static SendMissingHealthplanInformationCompletedEventHandler combine(SendMissingHealthplanInformationCompletedEventHandler a, SendMissingHealthplanInformationCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSendMissingHealthplanInformationCompletedEventHandler ret = new __MultiSendMissingHealthplanInformationCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SendMissingHealthplanInformationCompletedEventHandler remove(SendMissingHealthplanInformationCompletedEventHandler a, SendMissingHealthplanInformationCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SendMissingHealthplanInformationCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SendMissingHealthplanInformationCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSendMissingHealthplanInformationCompletedEventHandler ret = new __MultiSendMissingHealthplanInformationCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SendMissingHealthplanInformationCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


