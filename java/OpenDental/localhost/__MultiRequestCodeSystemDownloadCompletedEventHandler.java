//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiRequestCodeSystemDownloadCompletedEventHandler;
import OpenDental.localhost.RequestCodeSystemDownloadCompletedEventArgs;
import OpenDental.localhost.RequestCodeSystemDownloadCompletedEventHandler;

/**
* 
*/
public class __MultiRequestCodeSystemDownloadCompletedEventHandler   implements RequestCodeSystemDownloadCompletedEventHandler
{
    public void invoke(Object sender, RequestCodeSystemDownloadCompletedEventArgs e) throws Exception {
        IList<RequestCodeSystemDownloadCompletedEventHandler> copy = new IList<RequestCodeSystemDownloadCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<RequestCodeSystemDownloadCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            RequestCodeSystemDownloadCompletedEventHandler d = (RequestCodeSystemDownloadCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<RequestCodeSystemDownloadCompletedEventHandler> _invocationList = new ArrayList<RequestCodeSystemDownloadCompletedEventHandler>();
    public static RequestCodeSystemDownloadCompletedEventHandler combine(RequestCodeSystemDownloadCompletedEventHandler a, RequestCodeSystemDownloadCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiRequestCodeSystemDownloadCompletedEventHandler ret = new __MultiRequestCodeSystemDownloadCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static RequestCodeSystemDownloadCompletedEventHandler remove(RequestCodeSystemDownloadCompletedEventHandler a, RequestCodeSystemDownloadCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<RequestCodeSystemDownloadCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<RequestCodeSystemDownloadCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiRequestCodeSystemDownloadCompletedEventHandler ret = new __MultiRequestCodeSystemDownloadCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<RequestCodeSystemDownloadCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


