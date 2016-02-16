//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DiagnosisType;


/**
* 
*/
public class PatientDiagnosisType   
{

    private String diagnosisIDField = new String();
    private DiagnosisType diagnosisTypeField = DiagnosisType.ICD9;
    private String onsetDateField = new String();
    private String diagnosisNameField = new String();
    private String recordedDateField = new String();
    /**
    * 
    */
    public String getdiagnosisID() throws Exception {
        return this.diagnosisIDField;
    }

    public void setdiagnosisID(String value) throws Exception {
        this.diagnosisIDField = value;
    }

    /**
    * 
    */
    public DiagnosisType getdiagnosisType() throws Exception {
        return this.diagnosisTypeField;
    }

    public void setdiagnosisType(DiagnosisType value) throws Exception {
        this.diagnosisTypeField = value;
    }

    /**
    * 
    */
    public String getonsetDate() throws Exception {
        return this.onsetDateField;
    }

    public void setonsetDate(String value) throws Exception {
        this.onsetDateField = value;
    }

    /**
    * 
    */
    public String getdiagnosisName() throws Exception {
        return this.diagnosisNameField;
    }

    public void setdiagnosisName(String value) throws Exception {
        this.diagnosisNameField = value;
    }

    /**
    * 
    */
    public String getrecordedDate() throws Exception {
        return this.recordedDateField;
    }

    public void setrecordedDate(String value) throws Exception {
        this.recordedDateField = value;
    }

}


