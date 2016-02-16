//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler;
import OpenDental.NewCrop.FormularyAlternativesWithDrugInfo2CompletedEventArgs;
import OpenDental.NewCrop.FormularyAlternativesWithDrugInfo2CompletedEventHandler;

/**
* 
*/
public class __MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler   implements FormularyAlternativesWithDrugInfo2CompletedEventHandler
{
    public void invoke(Object sender, FormularyAlternativesWithDrugInfo2CompletedEventArgs e) throws Exception {
        IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler> copy = new IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FormularyAlternativesWithDrugInfo2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FormularyAlternativesWithDrugInfo2CompletedEventHandler d = (FormularyAlternativesWithDrugInfo2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler> _invocationList = new ArrayList<FormularyAlternativesWithDrugInfo2CompletedEventHandler>();
    public static FormularyAlternativesWithDrugInfo2CompletedEventHandler combine(FormularyAlternativesWithDrugInfo2CompletedEventHandler a, FormularyAlternativesWithDrugInfo2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler ret = new __MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FormularyAlternativesWithDrugInfo2CompletedEventHandler remove(FormularyAlternativesWithDrugInfo2CompletedEventHandler a, FormularyAlternativesWithDrugInfo2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler ret = new __MultiFormularyAlternativesWithDrugInfo2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FormularyAlternativesWithDrugInfo2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


