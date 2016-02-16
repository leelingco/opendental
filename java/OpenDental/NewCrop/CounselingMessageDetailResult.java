//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.CounselingMessageDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class CounselingMessageDetailResult   
{

    private Result resultField;
    private CounselingMessageDetail[] counselingMessageDetailField = new CounselingMessageDetail[]();
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
    public CounselingMessageDetail[] getcounselingMessageDetail() throws Exception {
        return this.counselingMessageDetailField;
    }

    public void setcounselingMessageDetail(CounselingMessageDetail[] value) throws Exception {
        this.counselingMessageDetailField = value;
    }

}


