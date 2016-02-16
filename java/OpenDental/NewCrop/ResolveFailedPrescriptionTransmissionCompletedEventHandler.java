//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventArgs;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventHandler;

public interface ResolveFailedPrescriptionTransmissionCompletedEventHandler   
{
    void invoke(Object sender, ResolveFailedPrescriptionTransmissionCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> getInvocationList() throws Exception ;

}


