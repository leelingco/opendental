//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetCounselingMessagesCompletedEventHandler;
import OpenDental.NewCrop.GetCounselingMessagesCompletedEventArgs;
import OpenDental.NewCrop.GetCounselingMessagesCompletedEventHandler;

/**
* 
*/
public class __MultiGetCounselingMessagesCompletedEventHandler   implements GetCounselingMessagesCompletedEventHandler
{
    public void invoke(Object sender, GetCounselingMessagesCompletedEventArgs e) throws Exception {
        IList<GetCounselingMessagesCompletedEventHandler> copy = new IList<GetCounselingMessagesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetCounselingMessagesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetCounselingMessagesCompletedEventHandler d = (GetCounselingMessagesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetCounselingMessagesCompletedEventHandler> _invocationList = new ArrayList<GetCounselingMessagesCompletedEventHandler>();
    public static GetCounselingMessagesCompletedEventHandler combine(GetCounselingMessagesCompletedEventHandler a, GetCounselingMessagesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetCounselingMessagesCompletedEventHandler ret = new __MultiGetCounselingMessagesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetCounselingMessagesCompletedEventHandler remove(GetCounselingMessagesCompletedEventHandler a, GetCounselingMessagesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetCounselingMessagesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetCounselingMessagesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetCounselingMessagesCompletedEventHandler ret = new __MultiGetCounselingMessagesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetCounselingMessagesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


