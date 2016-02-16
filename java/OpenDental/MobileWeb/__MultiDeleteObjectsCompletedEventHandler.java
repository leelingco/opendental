//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiDeleteObjectsCompletedEventHandler;
import OpenDental.MobileWeb.DeleteObjectsCompletedEventHandler;

/**
* 
*/
public class __MultiDeleteObjectsCompletedEventHandler   implements DeleteObjectsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<DeleteObjectsCompletedEventHandler> copy = new IList<DeleteObjectsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DeleteObjectsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DeleteObjectsCompletedEventHandler d = (DeleteObjectsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DeleteObjectsCompletedEventHandler> _invocationList = new ArrayList<DeleteObjectsCompletedEventHandler>();
    public static DeleteObjectsCompletedEventHandler combine(DeleteObjectsCompletedEventHandler a, DeleteObjectsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDeleteObjectsCompletedEventHandler ret = new __MultiDeleteObjectsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DeleteObjectsCompletedEventHandler remove(DeleteObjectsCompletedEventHandler a, DeleteObjectsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DeleteObjectsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DeleteObjectsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDeleteObjectsCompletedEventHandler ret = new __MultiDeleteObjectsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DeleteObjectsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


