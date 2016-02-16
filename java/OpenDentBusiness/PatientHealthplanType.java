//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.HealthplanType;


/**
* 
*/
public class PatientHealthplanType   
{

    private String healthplanIDField = new String();
    private HealthplanType healthplanTypeIDField = HealthplanType.Summary;
    private String groupField = new String();
    /**
    * 
    */
    public String gethealthplanID() throws Exception {
        return this.healthplanIDField;
    }

    public void sethealthplanID(String value) throws Exception {
        this.healthplanIDField = value;
    }

    /**
    * 
    */
    public HealthplanType gethealthplanTypeID() throws Exception {
        return this.healthplanTypeIDField;
    }

    public void sethealthplanTypeID(HealthplanType value) throws Exception {
        this.healthplanTypeIDField = value;
    }

    /**
    * 
    */
    public String getgroup() throws Exception {
        return this.groupField;
    }

    public void setgroup(String value) throws Exception {
        this.groupField = value;
    }

}


