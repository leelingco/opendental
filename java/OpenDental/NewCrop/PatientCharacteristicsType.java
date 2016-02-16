//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GenderType;
import OpenDental.NewCrop.LanguageType;


/**
* 
*/
public class PatientCharacteristicsType   
{

    private String dobField = new String();
    private GenderType genderField = GenderType.M;
    private boolean genderFieldSpecified = new boolean();
    private String heightField = new String();
    private String heightUnitsField = new String();
    private String weightField = new String();
    private String weightUnitsField = new String();
    private LanguageType languageField = LanguageType.Arabic;
    private boolean languageFieldSpecified = new boolean();
    /**
    * 
    */
    public String getdob() throws Exception {
        return this.dobField;
    }

    public void setdob(String value) throws Exception {
        this.dobField = value;
    }

    /**
    * 
    */
    public GenderType getgender() throws Exception {
        return this.genderField;
    }

    public void setgender(GenderType value) throws Exception {
        this.genderField = value;
    }

    /**
    * 
    */
    public boolean getgenderSpecified() throws Exception {
        return this.genderFieldSpecified;
    }

    public void setgenderSpecified(boolean value) throws Exception {
        this.genderFieldSpecified = value;
    }

    /**
    * 
    */
    public String getheight() throws Exception {
        return this.heightField;
    }

    public void setheight(String value) throws Exception {
        this.heightField = value;
    }

    /**
    * 
    */
    public String getheightUnits() throws Exception {
        return this.heightUnitsField;
    }

    public void setheightUnits(String value) throws Exception {
        this.heightUnitsField = value;
    }

    /**
    * 
    */
    public String getweight() throws Exception {
        return this.weightField;
    }

    public void setweight(String value) throws Exception {
        this.weightField = value;
    }

    /**
    * 
    */
    public String getweightUnits() throws Exception {
        return this.weightUnitsField;
    }

    public void setweightUnits(String value) throws Exception {
        this.weightUnitsField = value;
    }

    /**
    * 
    */
    public LanguageType getlanguage() throws Exception {
        return this.languageField;
    }

    public void setlanguage(LanguageType value) throws Exception {
        this.languageField = value;
    }

    /**
    * 
    */
    public boolean getlanguageSpecified() throws Exception {
        return this.languageFieldSpecified;
    }

    public void setlanguageSpecified(boolean value) throws Exception {
        this.languageFieldSpecified = value;
    }

}


