//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiDeleteAllRecordsCompletedEventHandler;
import OpenDental.MobileWeb.DeleteAllRecordsCompletedEventHandler;

/**
* 
*/
public class __MultiDeleteAllRecordsCompletedEventHandler   implements DeleteAllRecordsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<DeleteAllRecordsCompletedEventHandler> copy = new IList<DeleteAllRecordsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DeleteAllRecordsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DeleteAllRecordsCompletedEventHandler d = (DeleteAllRecordsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DeleteAllRecordsCompletedEventHandler> _invocationList = new ArrayList<DeleteAllRecordsCompletedEventHandler>();
    public static DeleteAllRecordsCompletedEventHandler combine(DeleteAllRecordsCompletedEventHandler a, DeleteAllRecordsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDeleteAllRecordsCompletedEventHandler ret = new __MultiDeleteAllRecordsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DeleteAllRecordsCompletedEventHandler remove(DeleteAllRecordsCompletedEventHandler a, DeleteAllRecordsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DeleteAllRecordsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DeleteAllRecordsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDeleteAllRecordsCompletedEventHandler ret = new __MultiDeleteAllRecordsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DeleteAllRecordsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


