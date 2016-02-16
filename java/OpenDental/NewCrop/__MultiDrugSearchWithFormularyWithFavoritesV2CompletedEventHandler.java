//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV2CompletedEventArgs;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler;

/**
* 
*/
public class __MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler   implements DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler
{
    public void invoke(Object sender, DrugSearchWithFormularyWithFavoritesV2CompletedEventArgs e) throws Exception {
        IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler> copy = new IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler d = (DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler> _invocationList = new ArrayList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler>();
    public static DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler combine(DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler a, DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler ret = new __MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler remove(DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler a, DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler ret = new __MultiDrugSearchWithFormularyWithFavoritesV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


