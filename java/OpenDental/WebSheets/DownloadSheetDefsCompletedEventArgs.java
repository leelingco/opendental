//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class DownloadSheetDefsCompletedEventArgs  extends System.ComponentModel.AsyncCompletedEventArgs 
{

    private Object[] results = new Object[]();
    public DownloadSheetDefsCompletedEventArgs(Object[] results, System.Exception exception, boolean cancelled, Object userState) throws Exception {
        super(exception, cancelled, userState);
        this.results = results;
    }

    /**
    * 
    */
    public OpenDental.WebSheets.webforms_sheetdef[] getResult() throws Exception {
        this.RaiseExceptionIfNecessary();
        return ((OpenDental.WebSheets.webforms_sheetdef[])(this.results[0]));
    }

}


