//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalSummaryResultV2;


/**
* 
*/
public class GetAllRenewalRequestsV2CompletedEventArgs  extends System.ComponentModel.AsyncCompletedEventArgs 
{

    private Object[] results = new Object[]();
    public GetAllRenewalRequestsV2CompletedEventArgs(Object[] results, System.Exception exception, boolean cancelled, Object userState) throws Exception {
        super(exception, cancelled, userState);
        this.results = results;
    }

    /**
    * 
    */
    public RenewalSummaryResultV2 getResult() throws Exception {
        this.RaiseExceptionIfNecessary();
        return ((RenewalSummaryResultV2)(this.results[0]));
    }

}


