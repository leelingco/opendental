//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.Result;
import OpenDental.NewCrop.TransmissionSummary;


/**
* 
*/
public class TransmissionSummaryResult   
{

    private Result resultField;
    private TransmissionSummary[] transmissionSummaryDetailArrayField = new TransmissionSummary[]();
    /**
    * 
    */
    public Result getresult() throws Exception {
        return this.resultField;
    }

    public void setresult(Result value) throws Exception {
        this.resultField = value;
    }

    /**
    * 
    */
    public TransmissionSummary[] gettransmissionSummaryDetailArray() throws Exception {
        return this.transmissionSummaryDetailArrayField;
    }

    public void settransmissionSummaryDetailArray(TransmissionSummary[] value) throws Exception {
        this.transmissionSummaryDetailArrayField = value;
    }

}


