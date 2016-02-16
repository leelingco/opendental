//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiPassProgressDelegate;
import OpenDental.PassProgressDelegate;

/**
* 
*/
public class __MultiPassProgressDelegate   implements PassProgressDelegate
{
    public void invoke(double newCurVal, String newDisplayText, double newMaxVal, String errorMessage) throws Exception {
        IList<PassProgressDelegate> copy = new IList<PassProgressDelegate>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PassProgressDelegate>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PassProgressDelegate d = (PassProgressDelegate)__dummyForeachVar0;
            d.invoke(newCurVal, newDisplayText, newMaxVal, errorMessage);
        }
    }

    private System.Collections.Generic.IList<PassProgressDelegate> _invocationList = new ArrayList<PassProgressDelegate>();
    public static PassProgressDelegate combine(PassProgressDelegate a, PassProgressDelegate b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPassProgressDelegate ret = new __MultiPassProgressDelegate();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PassProgressDelegate remove(PassProgressDelegate a, PassProgressDelegate b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PassProgressDelegate> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PassProgressDelegate> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPassProgressDelegate ret = new __MultiPassProgressDelegate();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PassProgressDelegate> getInvocationList() throws Exception {
        return _invocationList;
    }

}


