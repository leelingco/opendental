//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler;
import OpenDental.NewCrop.GetMeaningfulUsePatientEncounterInfoCompletedEventArgs;
import OpenDental.NewCrop.GetMeaningfulUsePatientEncounterInfoCompletedEventHandler;

/**
* 
*/
public class __MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler   implements GetMeaningfulUsePatientEncounterInfoCompletedEventHandler
{
    public void invoke(Object sender, GetMeaningfulUsePatientEncounterInfoCompletedEventArgs e) throws Exception {
        IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler> copy = new IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetMeaningfulUsePatientEncounterInfoCompletedEventHandler d = (GetMeaningfulUsePatientEncounterInfoCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler> _invocationList = new ArrayList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler>();
    public static GetMeaningfulUsePatientEncounterInfoCompletedEventHandler combine(GetMeaningfulUsePatientEncounterInfoCompletedEventHandler a, GetMeaningfulUsePatientEncounterInfoCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler ret = new __MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetMeaningfulUsePatientEncounterInfoCompletedEventHandler remove(GetMeaningfulUsePatientEncounterInfoCompletedEventHandler a, GetMeaningfulUsePatientEncounterInfoCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler ret = new __MultiGetMeaningfulUsePatientEncounterInfoCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetMeaningfulUsePatientEncounterInfoCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


