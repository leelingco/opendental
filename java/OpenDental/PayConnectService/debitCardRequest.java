//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.transRequest;


/**
* 
*/
public class debitCardRequest  extends transRequest 
{

    private String cardNumberField = new String();
    private OpenDental.PayConnectService.expiration expirationField;
    private double amountField = new double();
    private String pINField = new String();
    /**
    * 
    */
    public String getCardNumber() throws Exception {
        return this.cardNumberField;
    }

    public void setCardNumber(String value) throws Exception {
        this.cardNumberField = value;
    }

    /**
    * 
    */
    public OpenDental.PayConnectService.expiration getExpiration() throws Exception {
        return this.expirationField;
    }

    public void setExpiration(OpenDental.PayConnectService.expiration value) throws Exception {
        this.expirationField = value;
    }

    /**
    * 
    */
    public double getAmount() throws Exception {
        return this.amountField;
    }

    public void setAmount(double value) throws Exception {
        this.amountField = value;
    }

    /**
    * 
    */
    public String getPIN() throws Exception {
        return this.pINField;
    }

    public void setPIN(String value) throws Exception {
        this.pINField = value;
    }

}


