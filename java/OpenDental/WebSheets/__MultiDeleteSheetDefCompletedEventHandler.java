//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiDeleteSheetDefCompletedEventHandler;
import OpenDental.WebSheets.DeleteSheetDefCompletedEventHandler;

/**
* 
*/
public class __MultiDeleteSheetDefCompletedEventHandler   implements DeleteSheetDefCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<DeleteSheetDefCompletedEventHandler> copy = new IList<DeleteSheetDefCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DeleteSheetDefCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DeleteSheetDefCompletedEventHandler d = (DeleteSheetDefCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DeleteSheetDefCompletedEventHandler> _invocationList = new ArrayList<DeleteSheetDefCompletedEventHandler>();
    public static DeleteSheetDefCompletedEventHandler combine(DeleteSheetDefCompletedEventHandler a, DeleteSheetDefCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDeleteSheetDefCompletedEventHandler ret = new __MultiDeleteSheetDefCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DeleteSheetDefCompletedEventHandler remove(DeleteSheetDefCompletedEventHandler a, DeleteSheetDefCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DeleteSheetDefCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DeleteSheetDefCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDeleteSheetDefCompletedEventHandler ret = new __MultiDeleteSheetDefCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DeleteSheetDefCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


