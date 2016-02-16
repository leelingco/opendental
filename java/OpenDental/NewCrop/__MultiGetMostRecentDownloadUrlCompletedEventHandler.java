//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetMostRecentDownloadUrlCompletedEventHandler;
import OpenDental.NewCrop.GetMostRecentDownloadUrlCompletedEventArgs;
import OpenDental.NewCrop.GetMostRecentDownloadUrlCompletedEventHandler;

/**
* 
*/
public class __MultiGetMostRecentDownloadUrlCompletedEventHandler   implements GetMostRecentDownloadUrlCompletedEventHandler
{
    public void invoke(Object sender, GetMostRecentDownloadUrlCompletedEventArgs e) throws Exception {
        IList<GetMostRecentDownloadUrlCompletedEventHandler> copy = new IList<GetMostRecentDownloadUrlCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetMostRecentDownloadUrlCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetMostRecentDownloadUrlCompletedEventHandler d = (GetMostRecentDownloadUrlCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetMostRecentDownloadUrlCompletedEventHandler> _invocationList = new ArrayList<GetMostRecentDownloadUrlCompletedEventHandler>();
    public static GetMostRecentDownloadUrlCompletedEventHandler combine(GetMostRecentDownloadUrlCompletedEventHandler a, GetMostRecentDownloadUrlCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetMostRecentDownloadUrlCompletedEventHandler ret = new __MultiGetMostRecentDownloadUrlCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetMostRecentDownloadUrlCompletedEventHandler remove(GetMostRecentDownloadUrlCompletedEventHandler a, GetMostRecentDownloadUrlCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetMostRecentDownloadUrlCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetMostRecentDownloadUrlCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetMostRecentDownloadUrlCompletedEventHandler ret = new __MultiGetMostRecentDownloadUrlCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetMostRecentDownloadUrlCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


