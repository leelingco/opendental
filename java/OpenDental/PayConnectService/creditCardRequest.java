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
public class creditCardRequest  extends transRequest 
{

    private String cardNumberField = new String();
    private OpenDental.PayConnectService.expiration expirationField;
    private String magDataField = new String();
    private String nameOnCardField = new String();
    private double amountField = new double();
    private boolean amountFieldSpecified = new boolean();
    private String refNumberField = new String();
    private String zipField = new String();
    private String securityCodeField = new String();
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
    public String getMagData() throws Exception {
        return this.magDataField;
    }

    public void setMagData(String value) throws Exception {
        this.magDataField = value;
    }

    /**
    * 
    */
    public String getNameOnCard() throws Exception {
        return this.nameOnCardField;
    }

    public void setNameOnCard(String value) throws Exception {
        this.nameOnCardField = value;
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
    public boolean getAmountSpecified() throws Exception {
        return this.amountFieldSpecified;
    }

    public void setAmountSpecified(boolean value) throws Exception {
        this.amountFieldSpecified = value;
    }

    /**
    * 
    */
    public String getRefNumber() throws Exception {
        return this.refNumberField;
    }

    public void setRefNumber(String value) throws Exception {
        this.refNumberField = value;
    }

    /**
    * 
    */
    public String getZip() throws Exception {
        return this.zipField;
    }

    public void setZip(String value) throws Exception {
        this.zipField = value;
    }

    /**
    * 
    */
    public String getSecurityCode() throws Exception {
        return this.securityCodeField;
    }

    public void setSecurityCode(String value) throws Exception {
        this.securityCodeField = value;
    }

}


