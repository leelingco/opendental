//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugOTCType;
import OpenDentBusiness.DrugScheduleType;


/**
* 
*/
public class ExternalDrugOverrideType   
{

    private String externalDrugConceptField = new String();
    private String externalDrugNameField = new String();
    private String externalDrugStrengthField = new String();
    private String externalDrugStrengthUOMField = new String();
    private String externalDrugStrengthWithUOMField = new String();
    private String externalDrugDosageFormField = new String();
    private String externalDrugRouteField = new String();
    private String externalDrugIdentifierField = new String();
    private String externalDrugIdentifierTypeField = new String();
    private DrugScheduleType externalDrugScheduleField = DrugScheduleType.Item1;
    private DrugOTCType externalDrugOverTheCounterField = DrugOTCType.Yes;
    private boolean externalDrugOverTheCounterFieldSpecified = new boolean();
    private String externalDrugNdcField = new String();
    /**
    * 
    */
    public String getexternalDrugConcept() throws Exception {
        return this.externalDrugConceptField;
    }

    public void setexternalDrugConcept(String value) throws Exception {
        this.externalDrugConceptField = value;
    }

    /**
    * 
    */
    public String getexternalDrugName() throws Exception {
        return this.externalDrugNameField;
    }

    public void setexternalDrugName(String value) throws Exception {
        this.externalDrugNameField = value;
    }

    /**
    * 
    */
    public String getexternalDrugStrength() throws Exception {
        return this.externalDrugStrengthField;
    }

    public void setexternalDrugStrength(String value) throws Exception {
        this.externalDrugStrengthField = value;
    }

    /**
    * 
    */
    public String getexternalDrugStrengthUOM() throws Exception {
        return this.externalDrugStrengthUOMField;
    }

    public void setexternalDrugStrengthUOM(String value) throws Exception {
        this.externalDrugStrengthUOMField = value;
    }

    /**
    * 
    */
    public String getexternalDrugStrengthWithUOM() throws Exception {
        return this.externalDrugStrengthWithUOMField;
    }

    public void setexternalDrugStrengthWithUOM(String value) throws Exception {
        this.externalDrugStrengthWithUOMField = value;
    }

    /**
    * 
    */
    public String getexternalDrugDosageForm() throws Exception {
        return this.externalDrugDosageFormField;
    }

    public void setexternalDrugDosageForm(String value) throws Exception {
        this.externalDrugDosageFormField = value;
    }

    /**
    * 
    */
    public String getexternalDrugRoute() throws Exception {
        return this.externalDrugRouteField;
    }

    public void setexternalDrugRoute(String value) throws Exception {
        this.externalDrugRouteField = value;
    }

    /**
    * 
    */
    public String getexternalDrugIdentifier() throws Exception {
        return this.externalDrugIdentifierField;
    }

    public void setexternalDrugIdentifier(String value) throws Exception {
        this.externalDrugIdentifierField = value;
    }

    /**
    * 
    */
    public String getexternalDrugIdentifierType() throws Exception {
        return this.externalDrugIdentifierTypeField;
    }

    public void setexternalDrugIdentifierType(String value) throws Exception {
        this.externalDrugIdentifierTypeField = value;
    }

    /**
    * 
    */
    public DrugScheduleType getexternalDrugSchedule() throws Exception {
        return this.externalDrugScheduleField;
    }

    public void setexternalDrugSchedule(DrugScheduleType value) throws Exception {
        this.externalDrugScheduleField = value;
    }

    /**
    * 
    */
    public DrugOTCType getexternalDrugOverTheCounter() throws Exception {
        return this.externalDrugOverTheCounterField;
    }

    public void setexternalDrugOverTheCounter(DrugOTCType value) throws Exception {
        this.externalDrugOverTheCounterField = value;
    }

    /**
    * 
    */
    public boolean getexternalDrugOverTheCounterSpecified() throws Exception {
        return this.externalDrugOverTheCounterFieldSpecified;
    }

    public void setexternalDrugOverTheCounterSpecified(boolean value) throws Exception {
        this.externalDrugOverTheCounterFieldSpecified = value;
    }

    /**
    * 
    */
    public String getexternalDrugNdc() throws Exception {
        return this.externalDrugNdcField;
    }

    public void setexternalDrugNdc(String value) throws Exception {
        this.externalDrugNdcField = value;
    }

}


