//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiGetSheetDefAddressCompletedEventHandler;
import OpenDental.WebSheets.GetSheetDefAddressCompletedEventArgs;
import OpenDental.WebSheets.GetSheetDefAddressCompletedEventHandler;

/**
* 
*/
public class __MultiGetSheetDefAddressCompletedEventHandler   implements GetSheetDefAddressCompletedEventHandler
{
    public void invoke(Object sender, GetSheetDefAddressCompletedEventArgs e) throws Exception {
        IList<GetSheetDefAddressCompletedEventHandler> copy = new IList<GetSheetDefAddressCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetSheetDefAddressCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetSheetDefAddressCompletedEventHandler d = (GetSheetDefAddressCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetSheetDefAddressCompletedEventHandler> _invocationList = new ArrayList<GetSheetDefAddressCompletedEventHandler>();
    public static GetSheetDefAddressCompletedEventHandler combine(GetSheetDefAddressCompletedEventHandler a, GetSheetDefAddressCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetSheetDefAddressCompletedEventHandler ret = new __MultiGetSheetDefAddressCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetSheetDefAddressCompletedEventHandler remove(GetSheetDefAddressCompletedEventHandler a, GetSheetDefAddressCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetSheetDefAddressCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetSheetDefAddressCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetSheetDefAddressCompletedEventHandler ret = new __MultiGetSheetDefAddressCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetSheetDefAddressCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


