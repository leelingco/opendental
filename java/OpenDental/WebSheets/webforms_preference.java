//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.EntityObject;


/**
* 
*/
public class webforms_preference  extends EntityObject 
{

    private int colorBorderField = new int();
    private long dentalOfficeIDField = new long();
    private String cultureNameField = new String();
    /**
    * 
    */
    public int getColorBorder() throws Exception {
        return this.colorBorderField;
    }

    public void setColorBorder(int value) throws Exception {
        this.colorBorderField = value;
    }

    /**
    * 
    */
    public long getDentalOfficeID() throws Exception {
        return this.dentalOfficeIDField;
    }

    public void setDentalOfficeID(long value) throws Exception {
        this.dentalOfficeIDField = value;
    }

    /**
    * 
    */
    public String getCultureName() throws Exception {
        return this.cultureNameField;
    }

    public void setCultureName(String value) throws Exception {
        this.cultureNameField = value;
    }

}


