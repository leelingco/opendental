//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class EntityKeyMember   
{

    private String keyField = new String();
    private Object valueField = new Object();
    /**
    * 
    */
    public String getKey() throws Exception {
        return this.keyField;
    }

    public void setKey(String value) throws Exception {
        this.keyField = value;
    }

    /**
    * 
    */
    public Object getValue() throws Exception {
        return this.valueField;
    }

    public void setValue(Object value) throws Exception {
        this.valueField = value;
    }

}


