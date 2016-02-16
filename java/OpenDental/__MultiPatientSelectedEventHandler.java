//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiPatientSelectedEventHandler;
import OpenDental.PatientSelectedEventArgs;
import OpenDental.PatientSelectedEventHandler;

/**
* 
*/
public class __MultiPatientSelectedEventHandler   implements PatientSelectedEventHandler
{
    public void invoke(Object sender, PatientSelectedEventArgs e) throws Exception {
        IList<PatientSelectedEventHandler> copy = new IList<PatientSelectedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PatientSelectedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PatientSelectedEventHandler d = (PatientSelectedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PatientSelectedEventHandler> _invocationList = new ArrayList<PatientSelectedEventHandler>();
    public static PatientSelectedEventHandler combine(PatientSelectedEventHandler a, PatientSelectedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPatientSelectedEventHandler ret = new __MultiPatientSelectedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PatientSelectedEventHandler remove(PatientSelectedEventHandler a, PatientSelectedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PatientSelectedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PatientSelectedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPatientSelectedEventHandler ret = new __MultiPatientSelectedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PatientSelectedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


