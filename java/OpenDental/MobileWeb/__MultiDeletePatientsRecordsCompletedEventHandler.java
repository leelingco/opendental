//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiDeletePatientsRecordsCompletedEventHandler;
import OpenDental.MobileWeb.DeletePatientsRecordsCompletedEventHandler;

/**
* 
*/
public class __MultiDeletePatientsRecordsCompletedEventHandler   implements DeletePatientsRecordsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<DeletePatientsRecordsCompletedEventHandler> copy = new IList<DeletePatientsRecordsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DeletePatientsRecordsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DeletePatientsRecordsCompletedEventHandler d = (DeletePatientsRecordsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DeletePatientsRecordsCompletedEventHandler> _invocationList = new ArrayList<DeletePatientsRecordsCompletedEventHandler>();
    public static DeletePatientsRecordsCompletedEventHandler combine(DeletePatientsRecordsCompletedEventHandler a, DeletePatientsRecordsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDeletePatientsRecordsCompletedEventHandler ret = new __MultiDeletePatientsRecordsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DeletePatientsRecordsCompletedEventHandler remove(DeletePatientsRecordsCompletedEventHandler a, DeletePatientsRecordsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DeletePatientsRecordsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DeletePatientsRecordsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDeletePatientsRecordsCompletedEventHandler ret = new __MultiDeletePatientsRecordsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DeletePatientsRecordsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


