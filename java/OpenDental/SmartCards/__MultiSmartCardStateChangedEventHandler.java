//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.SmartCards.__MultiSmartCardStateChangedEventHandler;
import OpenDental.SmartCards.SmartCardStateChangedEventArgs;
import OpenDental.SmartCards.SmartCardStateChangedEventHandler;

public class __MultiSmartCardStateChangedEventHandler   implements SmartCardStateChangedEventHandler
{
    public void invoke(Object sender, SmartCardStateChangedEventArgs e) throws Exception {
        IList<SmartCardStateChangedEventHandler> copy = new IList<SmartCardStateChangedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SmartCardStateChangedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SmartCardStateChangedEventHandler d = (SmartCardStateChangedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SmartCardStateChangedEventHandler> _invocationList = new ArrayList<SmartCardStateChangedEventHandler>();
    public static SmartCardStateChangedEventHandler combine(SmartCardStateChangedEventHandler a, SmartCardStateChangedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSmartCardStateChangedEventHandler ret = new __MultiSmartCardStateChangedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SmartCardStateChangedEventHandler remove(SmartCardStateChangedEventHandler a, SmartCardStateChangedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SmartCardStateChangedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SmartCardStateChangedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSmartCardStateChangedEventHandler ret = new __MultiSmartCardStateChangedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SmartCardStateChangedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


