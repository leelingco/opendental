//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiDeleteSheetDataCompletedEventHandler;
import OpenDental.WebSheets.DeleteSheetDataCompletedEventHandler;

/**
* 
*/
public class __MultiDeleteSheetDataCompletedEventHandler   implements DeleteSheetDataCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<DeleteSheetDataCompletedEventHandler> copy = new IList<DeleteSheetDataCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DeleteSheetDataCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DeleteSheetDataCompletedEventHandler d = (DeleteSheetDataCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DeleteSheetDataCompletedEventHandler> _invocationList = new ArrayList<DeleteSheetDataCompletedEventHandler>();
    public static DeleteSheetDataCompletedEventHandler combine(DeleteSheetDataCompletedEventHandler a, DeleteSheetDataCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDeleteSheetDataCompletedEventHandler ret = new __MultiDeleteSheetDataCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DeleteSheetDataCompletedEventHandler remove(DeleteSheetDataCompletedEventHandler a, DeleteSheetDataCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DeleteSheetDataCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DeleteSheetDataCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDeleteSheetDataCompletedEventHandler ret = new __MultiDeleteSheetDataCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DeleteSheetDataCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


