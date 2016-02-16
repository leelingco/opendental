//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.checkRequest;
import OpenDental.PayConnectService.debitCardRequest;
import OpenDental.PayConnectService.merchantInfoRequest;
import OpenDental.PayConnectService.transType;


/**
* 
*/
public abstract class transRequest   
{

    private transType transTypeField = transType.SALE;
    /**
    * 
    */
    public transType getTransType() throws Exception {
        return this.transTypeField;
    }

    public void setTransType(transType value) throws Exception {
        this.transTypeField = value;
    }

}


