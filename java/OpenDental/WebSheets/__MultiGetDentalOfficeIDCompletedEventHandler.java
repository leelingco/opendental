//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiGetDentalOfficeIDCompletedEventHandler;
import OpenDental.WebSheets.GetDentalOfficeIDCompletedEventArgs;
import OpenDental.WebSheets.GetDentalOfficeIDCompletedEventHandler;

/**
* 
*/
public class __MultiGetDentalOfficeIDCompletedEventHandler   implements GetDentalOfficeIDCompletedEventHandler
{
    public void invoke(Object sender, GetDentalOfficeIDCompletedEventArgs e) throws Exception {
        IList<GetDentalOfficeIDCompletedEventHandler> copy = new IList<GetDentalOfficeIDCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetDentalOfficeIDCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetDentalOfficeIDCompletedEventHandler d = (GetDentalOfficeIDCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetDentalOfficeIDCompletedEventHandler> _invocationList = new ArrayList<GetDentalOfficeIDCompletedEventHandler>();
    public static GetDentalOfficeIDCompletedEventHandler combine(GetDentalOfficeIDCompletedEventHandler a, GetDentalOfficeIDCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetDentalOfficeIDCompletedEventHandler ret = new __MultiGetDentalOfficeIDCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetDentalOfficeIDCompletedEventHandler remove(GetDentalOfficeIDCompletedEventHandler a, GetDentalOfficeIDCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetDentalOfficeIDCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetDentalOfficeIDCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetDentalOfficeIDCompletedEventHandler ret = new __MultiGetDentalOfficeIDCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetDentalOfficeIDCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


