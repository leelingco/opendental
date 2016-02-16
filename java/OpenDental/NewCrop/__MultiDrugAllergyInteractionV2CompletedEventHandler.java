//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiDrugAllergyInteractionV2CompletedEventHandler;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventArgs;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventHandler;

/**
* 
*/
public class __MultiDrugAllergyInteractionV2CompletedEventHandler   implements DrugAllergyInteractionV2CompletedEventHandler
{
    public void invoke(Object sender, DrugAllergyInteractionV2CompletedEventArgs e) throws Exception {
        IList<DrugAllergyInteractionV2CompletedEventHandler> copy = new IList<DrugAllergyInteractionV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DrugAllergyInteractionV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DrugAllergyInteractionV2CompletedEventHandler d = (DrugAllergyInteractionV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DrugAllergyInteractionV2CompletedEventHandler> _invocationList = new ArrayList<DrugAllergyInteractionV2CompletedEventHandler>();
    public static DrugAllergyInteractionV2CompletedEventHandler combine(DrugAllergyInteractionV2CompletedEventHandler a, DrugAllergyInteractionV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDrugAllergyInteractionV2CompletedEventHandler ret = new __MultiDrugAllergyInteractionV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DrugAllergyInteractionV2CompletedEventHandler remove(DrugAllergyInteractionV2CompletedEventHandler a, DrugAllergyInteractionV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DrugAllergyInteractionV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DrugAllergyInteractionV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDrugAllergyInteractionV2CompletedEventHandler ret = new __MultiDrugAllergyInteractionV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DrugAllergyInteractionV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


