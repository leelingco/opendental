//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DownloadDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DownloadDetailResult   
{

    private Result resultField;
    private DownloadDetail downloadDetailField;
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
    public DownloadDetail getdownloadDetail() throws Exception {
        return this.downloadDetailField;
    }

    public void setdownloadDetail(DownloadDetail value) throws Exception {
        this.downloadDetailField = value;
    }

}


