//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.RequestCodeSystemDownloadCompletedEventArgs;
import OpenDental.localhost.RequestCodeSystemDownloadCompletedEventHandler;

public interface RequestCodeSystemDownloadCompletedEventHandler   
{
    void invoke(Object sender, RequestCodeSystemDownloadCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<RequestCodeSystemDownloadCompletedEventHandler> getInvocationList() throws Exception ;

}


