//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientAllergyHistory2CompletedEventHandler;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventArgs;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientAllergyHistory2CompletedEventHandler   implements GetPatientAllergyHistory2CompletedEventHandler
{
    public void invoke(Object sender, GetPatientAllergyHistory2CompletedEventArgs e) throws Exception {
        IList<GetPatientAllergyHistory2CompletedEventHandler> copy = new IList<GetPatientAllergyHistory2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientAllergyHistory2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientAllergyHistory2CompletedEventHandler d = (GetPatientAllergyHistory2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientAllergyHistory2CompletedEventHandler> _invocationList = new ArrayList<GetPatientAllergyHistory2CompletedEventHandler>();
    public static GetPatientAllergyHistory2CompletedEventHandler combine(GetPatientAllergyHistory2CompletedEventHandler a, GetPatientAllergyHistory2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientAllergyHistory2CompletedEventHandler ret = new __MultiGetPatientAllergyHistory2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientAllergyHistory2CompletedEventHandler remove(GetPatientAllergyHistory2CompletedEventHandler a, GetPatientAllergyHistory2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientAllergyHistory2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientAllergyHistory2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientAllergyHistory2CompletedEventHandler ret = new __MultiGetPatientAllergyHistory2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientAllergyHistory2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


