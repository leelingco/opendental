//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.SetPreferencesV2CompletedEventArgs;
import OpenDental.WebSheets.SetPreferencesV2CompletedEventHandler;

public interface SetPreferencesV2CompletedEventHandler   
{
    void invoke(Object sender, SetPreferencesV2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<SetPreferencesV2CompletedEventHandler> getInvocationList() throws Exception ;

}


