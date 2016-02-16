//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.EntityKey;
import OpenDental.WebSheets.EntityReferenceOfwebforms_preference;
import OpenDental.WebSheets.EntityReferenceOfwebforms_sheet;
import OpenDental.WebSheets.RelatedEnd;


/**
* 
*/
public abstract class EntityReference  extends RelatedEnd 
{

    private EntityKey entityKeyField;
    /**
    * 
    */
    public EntityKey getEntityKey() throws Exception {
        return this.entityKeyField;
    }

    public void setEntityKey(EntityKey value) throws Exception {
        this.entityKeyField = value;
    }

}


