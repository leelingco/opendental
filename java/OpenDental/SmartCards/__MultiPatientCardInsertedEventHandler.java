//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.SmartCards.__MultiPatientCardInsertedEventHandler;
import OpenDental.SmartCards.PatientCardInsertedEventArgs;
import OpenDental.SmartCards.PatientCardInsertedEventHandler;

public class __MultiPatientCardInsertedEventHandler   implements PatientCardInsertedEventHandler
{
    public void invoke(Object sender, PatientCardInsertedEventArgs e) throws Exception {
        IList<PatientCardInsertedEventHandler> copy = new IList<PatientCardInsertedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PatientCardInsertedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PatientCardInsertedEventHandler d = (PatientCardInsertedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PatientCardInsertedEventHandler> _invocationList = new ArrayList<PatientCardInsertedEventHandler>();
    public static PatientCardInsertedEventHandler combine(PatientCardInsertedEventHandler a, PatientCardInsertedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPatientCardInsertedEventHandler ret = new __MultiPatientCardInsertedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PatientCardInsertedEventHandler remove(PatientCardInsertedEventHandler a, PatientCardInsertedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PatientCardInsertedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PatientCardInsertedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPatientCardInsertedEventHandler ret = new __MultiPatientCardInsertedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PatientCardInsertedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


