//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.__MultiGetFileCompletedEventHandler;
import OpenDental.EmdeonITSTest.GetFileCompletedEventArgs;
import OpenDental.EmdeonITSTest.GetFileCompletedEventHandler;

/**
* 
*/
public class __MultiGetFileCompletedEventHandler   implements GetFileCompletedEventHandler
{
    public void invoke(Object sender, GetFileCompletedEventArgs e) throws Exception {
        IList<GetFileCompletedEventHandler> copy = new IList<GetFileCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetFileCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetFileCompletedEventHandler d = (GetFileCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetFileCompletedEventHandler> _invocationList = new ArrayList<GetFileCompletedEventHandler>();
    public static GetFileCompletedEventHandler combine(GetFileCompletedEventHandler a, GetFileCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetFileCompletedEventHandler ret = new __MultiGetFileCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetFileCompletedEventHandler remove(GetFileCompletedEventHandler a, GetFileCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetFileCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetFileCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetFileCompletedEventHandler ret = new __MultiGetFileCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetFileCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


