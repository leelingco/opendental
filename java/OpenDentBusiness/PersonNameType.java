//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PersonNamePrefix;
import OpenDentBusiness.PersonNameSuffix;


/**
* 
*/
public class PersonNameType   
{

    private String lastField = new String();
    private String firstField = new String();
    private String middleField = new String();
    private PersonNamePrefix prefixField = PersonNamePrefix.Ms;
    private boolean prefixFieldSpecified = new boolean();
    private PersonNameSuffix suffixField = PersonNameSuffix.DDS;
    private boolean suffixFieldSpecified = new boolean();
    /**
    * 
    */
    public String getlast() throws Exception {
        return this.lastField;
    }

    public void setlast(String value) throws Exception {
        this.lastField = value;
    }

    /**
    * 
    */
    public String getfirst() throws Exception {
        return this.firstField;
    }

    public void setfirst(String value) throws Exception {
        this.firstField = value;
    }

    /**
    * 
    */
    public String getmiddle() throws Exception {
        return this.middleField;
    }

    public void setmiddle(String value) throws Exception {
        this.middleField = value;
    }

    /**
    * 
    */
    public PersonNamePrefix getprefix() throws Exception {
        return this.prefixField;
    }

    public void setprefix(PersonNamePrefix value) throws Exception {
        this.prefixField = value;
    }

    /**
    * 
    */
    public boolean getprefixSpecified() throws Exception {
        return this.prefixFieldSpecified;
    }

    public void setprefixSpecified(boolean value) throws Exception {
        this.prefixFieldSpecified = value;
    }

    /**
    * 
    */
    public PersonNameSuffix getsuffix() throws Exception {
        return this.suffixField;
    }

    public void setsuffix(PersonNameSuffix value) throws Exception {
        this.suffixField = value;
    }

    /**
    * 
    */
    public boolean getsuffixSpecified() throws Exception {
        return this.suffixFieldSpecified;
    }

    public void setsuffixSpecified(boolean value) throws Exception {
        this.suffixFieldSpecified = value;
    }

}


