//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class ValidEventArgs  extends System.EventArgs 
{
    private DateTime dateViewing = new DateTime();
    private List<int> itypes = new List<int>();
    private boolean onlyLocal = new boolean();
    private long taskNum = new long();
    /**
    * 
    */
    public ValidEventArgs(DateTime dateViewing, List<int> itypes, boolean onlyLocal, long taskNum) throws Exception {
        super();
        this.dateViewing = dateViewing;
        this.itypes = itypes;
        this.onlyLocal = onlyLocal;
        this.taskNum = taskNum;
    }

    /**
    * 
    */
    public DateTime getDateViewing() throws Exception {
        return dateViewing;
    }

    /**
    * 
    */
    public List<int> getITypes() throws Exception {
        return itypes;
    }

    /**
    * 
    */
    public boolean getOnlyLocal() throws Exception {
        return onlyLocal;
    }

    /**
    * 
    */
    public long getTaskNum() throws Exception {
        return taskNum;
    }

}


