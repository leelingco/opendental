//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSetMobileWebUserPasswordCompletedEventHandler;
import OpenDental.MobileWeb.SetMobileWebUserPasswordCompletedEventHandler;

/**
* 
*/
public class __MultiSetMobileWebUserPasswordCompletedEventHandler   implements SetMobileWebUserPasswordCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SetMobileWebUserPasswordCompletedEventHandler> copy = new IList<SetMobileWebUserPasswordCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SetMobileWebUserPasswordCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SetMobileWebUserPasswordCompletedEventHandler d = (SetMobileWebUserPasswordCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SetMobileWebUserPasswordCompletedEventHandler> _invocationList = new ArrayList<SetMobileWebUserPasswordCompletedEventHandler>();
    public static SetMobileWebUserPasswordCompletedEventHandler combine(SetMobileWebUserPasswordCompletedEventHandler a, SetMobileWebUserPasswordCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSetMobileWebUserPasswordCompletedEventHandler ret = new __MultiSetMobileWebUserPasswordCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SetMobileWebUserPasswordCompletedEventHandler remove(SetMobileWebUserPasswordCompletedEventHandler a, SetMobileWebUserPasswordCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SetMobileWebUserPasswordCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SetMobileWebUserPasswordCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSetMobileWebUserPasswordCompletedEventHandler ret = new __MultiSetMobileWebUserPasswordCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SetMobileWebUserPasswordCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


