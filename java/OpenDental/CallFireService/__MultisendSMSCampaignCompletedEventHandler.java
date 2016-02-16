//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.CallFireService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.CallFireService.__MultisendSMSCampaignCompletedEventHandler;
import OpenDental.CallFireService.sendSMSCampaignCompletedEventArgs;
import OpenDental.CallFireService.sendSMSCampaignCompletedEventHandler;

/**
* 
*/
public class __MultisendSMSCampaignCompletedEventHandler   implements sendSMSCampaignCompletedEventHandler
{
    public void invoke(Object sender, sendSMSCampaignCompletedEventArgs e) throws Exception {
        IList<sendSMSCampaignCompletedEventHandler> copy = new IList<sendSMSCampaignCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<sendSMSCampaignCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            sendSMSCampaignCompletedEventHandler d = (sendSMSCampaignCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<sendSMSCampaignCompletedEventHandler> _invocationList = new ArrayList<sendSMSCampaignCompletedEventHandler>();
    public static sendSMSCampaignCompletedEventHandler combine(sendSMSCampaignCompletedEventHandler a, sendSMSCampaignCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultisendSMSCampaignCompletedEventHandler ret = new __MultisendSMSCampaignCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static sendSMSCampaignCompletedEventHandler remove(sendSMSCampaignCompletedEventHandler a, sendSMSCampaignCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<sendSMSCampaignCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<sendSMSCampaignCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultisendSMSCampaignCompletedEventHandler ret = new __MultisendSMSCampaignCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<sendSMSCampaignCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


