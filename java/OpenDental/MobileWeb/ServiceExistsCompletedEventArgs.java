//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class ServiceExistsCompletedEventArgs  extends System.ComponentModel.AsyncCompletedEventArgs 
{

    private Object[] results = new Object[]();
    public ServiceExistsCompletedEventArgs(Object[] results, System.Exception exception, boolean cancelled, Object userState) throws Exception {
        super(exception, cancelled, userState);
        this.results = results;
    }

    /**
    * 
    */
    public boolean getResult() throws Exception {
        this.RaiseExceptionIfNecessary();
        return ((boolean)(this.results[0]));
    }

}


