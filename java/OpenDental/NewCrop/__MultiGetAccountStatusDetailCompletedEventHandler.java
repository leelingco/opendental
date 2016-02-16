//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAccountStatusDetailCompletedEventHandler;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventArgs;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventHandler;

/**
* 
*/
public class __MultiGetAccountStatusDetailCompletedEventHandler   implements GetAccountStatusDetailCompletedEventHandler
{
    public void invoke(Object sender, GetAccountStatusDetailCompletedEventArgs e) throws Exception {
        IList<GetAccountStatusDetailCompletedEventHandler> copy = new IList<GetAccountStatusDetailCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAccountStatusDetailCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAccountStatusDetailCompletedEventHandler d = (GetAccountStatusDetailCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAccountStatusDetailCompletedEventHandler> _invocationList = new ArrayList<GetAccountStatusDetailCompletedEventHandler>();
    public static GetAccountStatusDetailCompletedEventHandler combine(GetAccountStatusDetailCompletedEventHandler a, GetAccountStatusDetailCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAccountStatusDetailCompletedEventHandler ret = new __MultiGetAccountStatusDetailCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAccountStatusDetailCompletedEventHandler remove(GetAccountStatusDetailCompletedEventHandler a, GetAccountStatusDetailCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAccountStatusDetailCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAccountStatusDetailCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAccountStatusDetailCompletedEventHandler ret = new __MultiGetAccountStatusDetailCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAccountStatusDetailCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


