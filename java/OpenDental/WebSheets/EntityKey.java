//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.EntityKeyMember;


/**
* 
*/
public class EntityKey   
{

    private String entitySetNameField = new String();
    private String entityContainerNameField = new String();
    private EntityKeyMember[] entityKeyValuesField = new EntityKeyMember[]();
    /**
    * 
    */
    public String getEntitySetName() throws Exception {
        return this.entitySetNameField;
    }

    public void setEntitySetName(String value) throws Exception {
        this.entitySetNameField = value;
    }

    /**
    * 
    */
    public String getEntityContainerName() throws Exception {
        return this.entityContainerNameField;
    }

    public void setEntityContainerName(String value) throws Exception {
        this.entityContainerNameField = value;
    }

    /**
    * 
    */
    public EntityKeyMember[] getEntityKeyValues() throws Exception {
        return this.entityKeyValuesField;
    }

    public void setEntityKeyValues(EntityKeyMember[] value) throws Exception {
        this.entityKeyValuesField = value;
    }

}


