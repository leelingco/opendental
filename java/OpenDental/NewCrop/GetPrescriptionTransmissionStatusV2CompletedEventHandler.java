//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventHandler;

public interface GetPrescriptionTransmissionStatusV2CompletedEventHandler   
{
    void invoke(Object sender, GetPrescriptionTransmissionStatusV2CompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> getInvocationList() throws Exception ;

}


