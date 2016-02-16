//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetDailyScriptReportV2CompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventHandler;

/**
* 
*/
public class __MultiGetDailyScriptReportV2CompletedEventHandler   implements GetDailyScriptReportV2CompletedEventHandler
{
    public void invoke(Object sender, GetDailyScriptReportV2CompletedEventArgs e) throws Exception {
        IList<GetDailyScriptReportV2CompletedEventHandler> copy = new IList<GetDailyScriptReportV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetDailyScriptReportV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetDailyScriptReportV2CompletedEventHandler d = (GetDailyScriptReportV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetDailyScriptReportV2CompletedEventHandler> _invocationList = new ArrayList<GetDailyScriptReportV2CompletedEventHandler>();
    public static GetDailyScriptReportV2CompletedEventHandler combine(GetDailyScriptReportV2CompletedEventHandler a, GetDailyScriptReportV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetDailyScriptReportV2CompletedEventHandler ret = new __MultiGetDailyScriptReportV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetDailyScriptReportV2CompletedEventHandler remove(GetDailyScriptReportV2CompletedEventHandler a, GetDailyScriptReportV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetDailyScriptReportV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetDailyScriptReportV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetDailyScriptReportV2CompletedEventHandler ret = new __MultiGetDailyScriptReportV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetDailyScriptReportV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


