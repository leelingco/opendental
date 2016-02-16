//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiDownloadSheetDefsCompletedEventHandler;
import OpenDental.WebSheets.DownloadSheetDefsCompletedEventArgs;
import OpenDental.WebSheets.DownloadSheetDefsCompletedEventHandler;

/**
* 
*/
public class __MultiDownloadSheetDefsCompletedEventHandler   implements DownloadSheetDefsCompletedEventHandler
{
    public void invoke(Object sender, DownloadSheetDefsCompletedEventArgs e) throws Exception {
        IList<DownloadSheetDefsCompletedEventHandler> copy = new IList<DownloadSheetDefsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DownloadSheetDefsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DownloadSheetDefsCompletedEventHandler d = (DownloadSheetDefsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DownloadSheetDefsCompletedEventHandler> _invocationList = new ArrayList<DownloadSheetDefsCompletedEventHandler>();
    public static DownloadSheetDefsCompletedEventHandler combine(DownloadSheetDefsCompletedEventHandler a, DownloadSheetDefsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDownloadSheetDefsCompletedEventHandler ret = new __MultiDownloadSheetDefsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DownloadSheetDefsCompletedEventHandler remove(DownloadSheetDefsCompletedEventHandler a, DownloadSheetDefsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DownloadSheetDefsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DownloadSheetDefsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDownloadSheetDefsCompletedEventHandler ret = new __MultiDownloadSheetDefsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DownloadSheetDefsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


