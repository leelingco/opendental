//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiDrugDiseaseInteractionCompletedEventHandler;
import OpenDental.NewCrop.DrugDiseaseInteractionCompletedEventArgs;
import OpenDental.NewCrop.DrugDiseaseInteractionCompletedEventHandler;

/**
* 
*/
public class __MultiDrugDiseaseInteractionCompletedEventHandler   implements DrugDiseaseInteractionCompletedEventHandler
{
    public void invoke(Object sender, DrugDiseaseInteractionCompletedEventArgs e) throws Exception {
        IList<DrugDiseaseInteractionCompletedEventHandler> copy = new IList<DrugDiseaseInteractionCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DrugDiseaseInteractionCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DrugDiseaseInteractionCompletedEventHandler d = (DrugDiseaseInteractionCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DrugDiseaseInteractionCompletedEventHandler> _invocationList = new ArrayList<DrugDiseaseInteractionCompletedEventHandler>();
    public static DrugDiseaseInteractionCompletedEventHandler combine(DrugDiseaseInteractionCompletedEventHandler a, DrugDiseaseInteractionCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDrugDiseaseInteractionCompletedEventHandler ret = new __MultiDrugDiseaseInteractionCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DrugDiseaseInteractionCompletedEventHandler remove(DrugDiseaseInteractionCompletedEventHandler a, DrugDiseaseInteractionCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DrugDiseaseInteractionCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DrugDiseaseInteractionCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDrugDiseaseInteractionCompletedEventHandler ret = new __MultiDrugDiseaseInteractionCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DrugDiseaseInteractionCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


