//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.User_Controls;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.User_Controls.__MultiKeyboardClickEventHandler;

public class __MultiKeyboardClickEventHandler   implements OpenDental.User_Controls.KeyboardClickEventHandler
{
    public void invoke(Object sender, OpenDental.User_Controls.KeyboardClickEventArgs e) throws Exception {
        IList<OpenDental.User_Controls.KeyboardClickEventHandler> copy = new IList<OpenDental.User_Controls.KeyboardClickEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OpenDental.User_Controls.KeyboardClickEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OpenDental.User_Controls.KeyboardClickEventHandler d = (OpenDental.User_Controls.KeyboardClickEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<OpenDental.User_Controls.KeyboardClickEventHandler> _invocationList = new ArrayList<OpenDental.User_Controls.KeyboardClickEventHandler>();
    public static OpenDental.User_Controls.KeyboardClickEventHandler combine(OpenDental.User_Controls.KeyboardClickEventHandler a, OpenDental.User_Controls.KeyboardClickEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiKeyboardClickEventHandler ret = new __MultiKeyboardClickEventHandler();
        ret._invocationList = a.getInvocationList();
        //public event KeyboardClickEventHandler KeyClick=null;
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static OpenDental.User_Controls.KeyboardClickEventHandler remove(OpenDental.User_Controls.KeyboardClickEventHandler a, OpenDental.User_Controls.KeyboardClickEventHandler b) throws Exception {
        //this doesn't seem to work.  Grrr.
        //MessageBox.Show(this.CanFocus.ToString());
        //this.CanFocus=false;
        /**
        * 
        */
        if (a == null || b == null)
            return a;
         
        //KeyEventArgs kArgs=new KeyEventArgs( KeyboardClickEventArgs(myTxt);
        System.Collections.Generic.IList<OpenDental.User_Controls.KeyboardClickEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<OpenDental.User_Controls.KeyboardClickEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            //MessageBox.Show(txt);
            __MultiKeyboardClickEventHandler ret = new __MultiKeyboardClickEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<OpenDental.User_Controls.KeyboardClickEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


