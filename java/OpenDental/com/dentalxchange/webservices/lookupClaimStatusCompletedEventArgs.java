//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class lookupClaimStatusCompletedEventArgs  extends System.ComponentModel.AsyncCompletedEventArgs 
{

    private Object[] results = new Object[]();
    public lookupClaimStatusCompletedEventArgs(Object[] results, System.Exception exception, boolean cancelled, Object userState) throws Exception {
        super(exception, cancelled, userState);
        this.results = results;
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response getResult() throws Exception {
        this.RaiseExceptionIfNecessary();
        return ((OpenDental.com.dentalxchange.webservices.Response)(this.results[0]));
    }

}


