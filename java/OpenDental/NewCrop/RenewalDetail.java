//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.NCRenewal;


/**
* 
*/
public class RenewalDetail   
{

    private String externalLocationIdField = new String();
    private String externalDoctorIdField = new String();
    private NCRenewal renewalField;
    /**
    * 
    */
    public String getExternalLocationId() throws Exception {
        return this.externalLocationIdField;
    }

    public void setExternalLocationId(String value) throws Exception {
        this.externalLocationIdField = value;
    }

    /**
    * 
    */
    public String getExternalDoctorId() throws Exception {
        return this.externalDoctorIdField;
    }

    public void setExternalDoctorId(String value) throws Exception {
        this.externalDoctorIdField = value;
    }

    /**
    * 
    */
    public NCRenewal getrenewal() throws Exception {
        return this.renewalField;
    }

    public void setrenewal(NCRenewal value) throws Exception {
        this.renewalField = value;
    }

}


