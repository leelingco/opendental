//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventArgs;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventHandler;

public interface SendMissingHealthplanInformationCompletedEventHandler   
{
    void invoke(Object sender, SendMissingHealthplanInformationCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<SendMissingHealthplanInformationCompletedEventHandler> getInvocationList() throws Exception ;

}


