//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventHandler;

public interface GetDailyScriptReportV2CompletedEventHandler   
{
    void invoke(Object sender, GetDailyScriptReportV2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetDailyScriptReportV2CompletedEventHandler> getInvocationList() throws Exception ;

}


