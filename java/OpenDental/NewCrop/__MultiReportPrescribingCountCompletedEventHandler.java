//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiReportPrescribingCountCompletedEventHandler;
import OpenDental.NewCrop.ReportPrescribingCountCompletedEventArgs;
import OpenDental.NewCrop.ReportPrescribingCountCompletedEventHandler;

/**
* 
*/
public class __MultiReportPrescribingCountCompletedEventHandler   implements ReportPrescribingCountCompletedEventHandler
{
    public void invoke(Object sender, ReportPrescribingCountCompletedEventArgs e) throws Exception {
        IList<ReportPrescribingCountCompletedEventHandler> copy = new IList<ReportPrescribingCountCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ReportPrescribingCountCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ReportPrescribingCountCompletedEventHandler d = (ReportPrescribingCountCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ReportPrescribingCountCompletedEventHandler> _invocationList = new ArrayList<ReportPrescribingCountCompletedEventHandler>();
    public static ReportPrescribingCountCompletedEventHandler combine(ReportPrescribingCountCompletedEventHandler a, ReportPrescribingCountCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiReportPrescribingCountCompletedEventHandler ret = new __MultiReportPrescribingCountCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ReportPrescribingCountCompletedEventHandler remove(ReportPrescribingCountCompletedEventHandler a, ReportPrescribingCountCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ReportPrescribingCountCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ReportPrescribingCountCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiReportPrescribingCountCompletedEventHandler ret = new __MultiReportPrescribingCountCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ReportPrescribingCountCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


