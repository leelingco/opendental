//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetDailyScriptReportCompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportCompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportCompletedEventHandler;

/**
* 
*/
public class __MultiGetDailyScriptReportCompletedEventHandler   implements GetDailyScriptReportCompletedEventHandler
{
    public void invoke(Object sender, GetDailyScriptReportCompletedEventArgs e) throws Exception {
        IList<GetDailyScriptReportCompletedEventHandler> copy = new IList<GetDailyScriptReportCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetDailyScriptReportCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetDailyScriptReportCompletedEventHandler d = (GetDailyScriptReportCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetDailyScriptReportCompletedEventHandler> _invocationList = new ArrayList<GetDailyScriptReportCompletedEventHandler>();
    public static GetDailyScriptReportCompletedEventHandler combine(GetDailyScriptReportCompletedEventHandler a, GetDailyScriptReportCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetDailyScriptReportCompletedEventHandler ret = new __MultiGetDailyScriptReportCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetDailyScriptReportCompletedEventHandler remove(GetDailyScriptReportCompletedEventHandler a, GetDailyScriptReportCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetDailyScriptReportCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetDailyScriptReportCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetDailyScriptReportCompletedEventHandler ret = new __MultiGetDailyScriptReportCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetDailyScriptReportCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


