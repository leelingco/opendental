//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.DownloadSheetDefsCompletedEventArgs;
import OpenDental.WebSheets.DownloadSheetDefsCompletedEventHandler;

public interface DownloadSheetDefsCompletedEventHandler   
{
    void invoke(Object sender, DownloadSheetDefsCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<DownloadSheetDefsCompletedEventHandler> getInvocationList() throws Exception ;

}


