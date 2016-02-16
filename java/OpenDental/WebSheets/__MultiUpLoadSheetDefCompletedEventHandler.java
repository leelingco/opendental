//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiUpLoadSheetDefCompletedEventHandler;
import OpenDental.WebSheets.UpLoadSheetDefCompletedEventHandler;

/**
* 
*/
public class __MultiUpLoadSheetDefCompletedEventHandler   implements UpLoadSheetDefCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<UpLoadSheetDefCompletedEventHandler> copy = new IList<UpLoadSheetDefCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<UpLoadSheetDefCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            UpLoadSheetDefCompletedEventHandler d = (UpLoadSheetDefCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<UpLoadSheetDefCompletedEventHandler> _invocationList = new ArrayList<UpLoadSheetDefCompletedEventHandler>();
    public static UpLoadSheetDefCompletedEventHandler combine(UpLoadSheetDefCompletedEventHandler a, UpLoadSheetDefCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiUpLoadSheetDefCompletedEventHandler ret = new __MultiUpLoadSheetDefCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static UpLoadSheetDefCompletedEventHandler remove(UpLoadSheetDefCompletedEventHandler a, UpLoadSheetDefCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<UpLoadSheetDefCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<UpLoadSheetDefCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiUpLoadSheetDefCompletedEventHandler ret = new __MultiUpLoadSheetDefCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<UpLoadSheetDefCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


