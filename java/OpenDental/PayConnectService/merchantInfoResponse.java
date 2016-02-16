//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.Response;


/**
* 
*/
public class merchantInfoResponse  extends Response 
{

    private boolean forceDuplicatesField = new boolean();
    private boolean autoCloseBatchField = new boolean();
    private boolean echeckField = new boolean();
    private String acceptedCardsField = new String();
    /**
    * 
    */
    public boolean getForceDuplicates() throws Exception {
        return this.forceDuplicatesField;
    }

    public void setForceDuplicates(boolean value) throws Exception {
        this.forceDuplicatesField = value;
    }

    /**
    * 
    */
    public boolean getAutoCloseBatch() throws Exception {
        return this.autoCloseBatchField;
    }

    public void setAutoCloseBatch(boolean value) throws Exception {
        this.autoCloseBatchField = value;
    }

    /**
    * 
    */
    public boolean getEcheck() throws Exception {
        return this.echeckField;
    }

    public void setEcheck(boolean value) throws Exception {
        this.echeckField = value;
    }

    /**
    * 
    */
    public String getAcceptedCards() throws Exception {
        return this.acceptedCardsField;
    }

    public void setAcceptedCards(String value) throws Exception {
        this.acceptedCardsField = value;
    }

}


