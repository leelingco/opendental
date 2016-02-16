//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiGetUserNameCompletedEventHandler;
import OpenDental.MobileWeb.GetUserNameCompletedEventArgs;
import OpenDental.MobileWeb.GetUserNameCompletedEventHandler;

/**
* 
*/
public class __MultiGetUserNameCompletedEventHandler   implements GetUserNameCompletedEventHandler
{
    public void invoke(Object sender, GetUserNameCompletedEventArgs e) throws Exception {
        IList<GetUserNameCompletedEventHandler> copy = new IList<GetUserNameCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetUserNameCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetUserNameCompletedEventHandler d = (GetUserNameCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetUserNameCompletedEventHandler> _invocationList = new ArrayList<GetUserNameCompletedEventHandler>();
    public static GetUserNameCompletedEventHandler combine(GetUserNameCompletedEventHandler a, GetUserNameCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetUserNameCompletedEventHandler ret = new __MultiGetUserNameCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetUserNameCompletedEventHandler remove(GetUserNameCompletedEventHandler a, GetUserNameCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetUserNameCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetUserNameCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetUserNameCompletedEventHandler ret = new __MultiGetUserNameCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetUserNameCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


