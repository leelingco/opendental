//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiOnPatientSelected;
import OpenDental.OnPatientSelected;
import OpenDentBusiness.Patient;

public class __MultiOnPatientSelected   implements OnPatientSelected
{
    public void invoke(Patient pat) throws Exception {
        IList<OnPatientSelected> copy = new IList<OnPatientSelected>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OnPatientSelected>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OnPatientSelected d = (OnPatientSelected)__dummyForeachVar0;
            d.invoke(pat);
        }
    }

    private System.Collections.Generic.IList<OnPatientSelected> _invocationList = new ArrayList<OnPatientSelected>();
    public static OnPatientSelected combine(OnPatientSelected a, OnPatientSelected b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiOnPatientSelected ret = new __MultiOnPatientSelected();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static OnPatientSelected remove(OnPatientSelected a, OnPatientSelected b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<OnPatientSelected> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<OnPatientSelected> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiOnPatientSelected ret = new __MultiOnPatientSelected();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<OnPatientSelected> getInvocationList() throws Exception {
        return _invocationList;
    }

}


