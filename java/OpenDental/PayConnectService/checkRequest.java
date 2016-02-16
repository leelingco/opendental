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
public class checkRequest  extends transRequest 
{

    private String checkNumberField = new String();
    private String transitNumberField = new String();
    private String accountNumberField = new String();
    private double amountField = new double();
    private boolean amountFieldSpecified = new boolean();
    private String nameOnCheckField = new String();
    /**
    * 
    */
    public String getCheckNumber() throws Exception {
        return this.checkNumberField;
    }

    public void setCheckNumber(String value) throws Exception {
        this.checkNumberField = value;
    }

    /**
    * 
    */
    public String getTransitNumber() throws Exception {
        return this.transitNumberField;
    }

    public void setTransitNumber(String value) throws Exception {
        this.transitNumberField = value;
    }

    /**
    * 
    */
    public String getAccountNumber() throws Exception {
        return this.accountNumberField;
    }

    public void setAccountNumber(String value) throws Exception {
        this.accountNumberField = value;
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
    public String getNameOnCheck() throws Exception {
        return this.nameOnCheckField;
    }

    public void setNameOnCheck(String value) throws Exception {
        this.nameOnCheckField = value;
    }

}


