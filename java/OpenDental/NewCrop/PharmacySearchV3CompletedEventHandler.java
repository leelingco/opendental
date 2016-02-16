//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventArgs;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventHandler;

public interface PharmacySearchV3CompletedEventHandler   
{
    void invoke(Object sender, PharmacySearchV3CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<PharmacySearchV3CompletedEventHandler> getInvocationList() throws Exception ;

}


