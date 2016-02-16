//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetDailyScriptReportV3CompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventHandler;

/**
* 
*/
public class __MultiGetDailyScriptReportV3CompletedEventHandler   implements GetDailyScriptReportV3CompletedEventHandler
{
    public void invoke(Object sender, GetDailyScriptReportV3CompletedEventArgs e) throws Exception {
        IList<GetDailyScriptReportV3CompletedEventHandler> copy = new IList<GetDailyScriptReportV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetDailyScriptReportV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetDailyScriptReportV3CompletedEventHandler d = (GetDailyScriptReportV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetDailyScriptReportV3CompletedEventHandler> _invocationList = new ArrayList<GetDailyScriptReportV3CompletedEventHandler>();
    public static GetDailyScriptReportV3CompletedEventHandler combine(GetDailyScriptReportV3CompletedEventHandler a, GetDailyScriptReportV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetDailyScriptReportV3CompletedEventHandler ret = new __MultiGetDailyScriptReportV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetDailyScriptReportV3CompletedEventHandler remove(GetDailyScriptReportV3CompletedEventHandler a, GetDailyScriptReportV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetDailyScriptReportV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetDailyScriptReportV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetDailyScriptReportV3CompletedEventHandler ret = new __MultiGetDailyScriptReportV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetDailyScriptReportV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


