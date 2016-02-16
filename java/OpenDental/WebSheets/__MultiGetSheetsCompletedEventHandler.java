//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiGetSheetsCompletedEventHandler;
import OpenDental.WebSheets.GetSheetsCompletedEventArgs;
import OpenDental.WebSheets.GetSheetsCompletedEventHandler;

/**
* 
*/
public class __MultiGetSheetsCompletedEventHandler   implements GetSheetsCompletedEventHandler
{
    public void invoke(Object sender, GetSheetsCompletedEventArgs e) throws Exception {
        IList<GetSheetsCompletedEventHandler> copy = new IList<GetSheetsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetSheetsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetSheetsCompletedEventHandler d = (GetSheetsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetSheetsCompletedEventHandler> _invocationList = new ArrayList<GetSheetsCompletedEventHandler>();
    public static GetSheetsCompletedEventHandler combine(GetSheetsCompletedEventHandler a, GetSheetsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetSheetsCompletedEventHandler ret = new __MultiGetSheetsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetSheetsCompletedEventHandler remove(GetSheetsCompletedEventHandler a, GetSheetsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetSheetsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetSheetsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetSheetsCompletedEventHandler ret = new __MultiGetSheetsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetSheetsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


