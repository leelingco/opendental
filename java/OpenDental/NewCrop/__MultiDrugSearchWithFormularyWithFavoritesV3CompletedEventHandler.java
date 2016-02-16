//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV3CompletedEventArgs;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler;

/**
* 
*/
public class __MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler   implements DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler
{
    public void invoke(Object sender, DrugSearchWithFormularyWithFavoritesV3CompletedEventArgs e) throws Exception {
        IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler> copy = new IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler d = (DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler> _invocationList = new ArrayList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler>();
    public static DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler combine(DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler a, DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler ret = new __MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler remove(DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler a, DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler ret = new __MultiDrugSearchWithFormularyWithFavoritesV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


