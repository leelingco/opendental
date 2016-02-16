//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.CallFireService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.CallFireService.sendSMSCampaignCompletedEventArgs;
import OpenDental.CallFireService.sendSMSCampaignCompletedEventHandler;

public interface sendSMSCampaignCompletedEventHandler   
{
    void invoke(Object sender, sendSMSCampaignCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<sendSMSCampaignCompletedEventHandler> getInvocationList() throws Exception ;

}


