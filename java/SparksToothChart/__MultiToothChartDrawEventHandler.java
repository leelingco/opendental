//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import SparksToothChart.__MultiToothChartDrawEventHandler;
import SparksToothChart.ToothChartDrawEventArgs;

/**
* 
*/
public class __MultiToothChartDrawEventHandler   implements SparksToothChart.ToothChartDrawEventHandler
{
    public void invoke(Object sender, ToothChartDrawEventArgs e) throws Exception {
        IList<SparksToothChart.ToothChartDrawEventHandler> copy = new IList<SparksToothChart.ToothChartDrawEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SparksToothChart.ToothChartDrawEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SparksToothChart.ToothChartDrawEventHandler d = (SparksToothChart.ToothChartDrawEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SparksToothChart.ToothChartDrawEventHandler> _invocationList = new ArrayList<SparksToothChart.ToothChartDrawEventHandler>();
    public static SparksToothChart.ToothChartDrawEventHandler combine(SparksToothChart.ToothChartDrawEventHandler a, SparksToothChart.ToothChartDrawEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiToothChartDrawEventHandler ret = new __MultiToothChartDrawEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SparksToothChart.ToothChartDrawEventHandler remove(SparksToothChart.ToothChartDrawEventHandler a, SparksToothChart.ToothChartDrawEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SparksToothChart.ToothChartDrawEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SparksToothChart.ToothChartDrawEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiToothChartDrawEventHandler ret = new __MultiToothChartDrawEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


