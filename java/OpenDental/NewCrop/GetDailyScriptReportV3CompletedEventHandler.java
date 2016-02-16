//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventHandler;

public interface GetDailyScriptReportV3CompletedEventHandler   
{
    void invoke(Object sender, GetDailyScriptReportV3CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetDailyScriptReportV3CompletedEventHandler> getInvocationList() throws Exception ;

}


