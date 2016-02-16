//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugScheduleType;
import OpenDentBusiness.ResponseCodeType;
import OpenDentBusiness.ResponseDenyCodeType;


/**
* 
*/
public class PrescriptionRenewalResponseType   
{

    private String renewalRequestIdentifierField = new String();
    private ResponseCodeType responseCodeField = ResponseCodeType.Accept;
    private String refillCountField = new String();
    private DrugScheduleType drugScheduleField = DrugScheduleType.Item1;
    private boolean drugScheduleFieldSpecified = new boolean();
    private ResponseDenyCodeType responseDenyCodeField = ResponseDenyCodeType.PatientUnknownToThePrescriber;
    private boolean responseDenyCodeFieldSpecified = new boolean();
    private String messageToPharmacistField = new String();
    private String idField = new String();
    /**
    * 
    */
    public String getrenewalRequestIdentifier() throws Exception {
        return this.renewalRequestIdentifierField;
    }

    public void setrenewalRequestIdentifier(String value) throws Exception {
        this.renewalRequestIdentifierField = value;
    }

    /**
    * 
    */
    public ResponseCodeType getresponseCode() throws Exception {
        return this.responseCodeField;
    }

    public void setresponseCode(ResponseCodeType value) throws Exception {
        this.responseCodeField = value;
    }

    /**
    * 
    */
    public String getrefillCount() throws Exception {
        return this.refillCountField;
    }

    public void setrefillCount(String value) throws Exception {
        this.refillCountField = value;
    }

    /**
    * 
    */
    public DrugScheduleType getdrugSchedule() throws Exception {
        return this.drugScheduleField;
    }

    public void setdrugSchedule(DrugScheduleType value) throws Exception {
        this.drugScheduleField = value;
    }

    /**
    * 
    */
    public boolean getdrugScheduleSpecified() throws Exception {
        return this.drugScheduleFieldSpecified;
    }

    public void setdrugScheduleSpecified(boolean value) throws Exception {
        this.drugScheduleFieldSpecified = value;
    }

    /**
    * 
    */
    public ResponseDenyCodeType getresponseDenyCode() throws Exception {
        return this.responseDenyCodeField;
    }

    public void setresponseDenyCode(ResponseDenyCodeType value) throws Exception {
        this.responseDenyCodeField = value;
    }

    /**
    * 
    */
    public boolean getresponseDenyCodeSpecified() throws Exception {
        return this.responseDenyCodeFieldSpecified;
    }

    public void setresponseDenyCodeSpecified(boolean value) throws Exception {
        this.responseDenyCodeFieldSpecified = value;
    }

    /**
    * 
    */
    public String getmessageToPharmacist() throws Exception {
        return this.messageToPharmacistField;
    }

    public void setmessageToPharmacist(String value) throws Exception {
        this.messageToPharmacistField = value;
    }

    /**
    * 
    */
    public String getID() throws Exception {
        return this.idField;
    }

    public void setID(String value) throws Exception {
        this.idField = value;
    }

}


