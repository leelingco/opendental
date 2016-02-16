//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.GetSheetDefAddressCompletedEventArgs;
import OpenDental.WebSheets.GetSheetDefAddressCompletedEventHandler;

public interface GetSheetDefAddressCompletedEventHandler   
{
    void invoke(Object sender, GetSheetDefAddressCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetSheetDefAddressCompletedEventHandler> getInvocationList() throws Exception ;

}


