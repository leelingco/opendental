//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PatFieldDef;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.TableBase;

/**
* These are the definitions for the custom patient fields added and managed by the user.
*/
public class PatFieldDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PatFieldDefNum = new long();
    /**
    * The name of the field that the user will be allowed to fill in the patient info window.
    */
    public String FieldName = new String();
    /**
    * Enum:PatFieldType Text=0,PickList=1,Date=2,Checkbox=3,Currency=4
    */
    public PatFieldType FieldType = PatFieldType.Text;
    /**
    * The text that contains pick list values.
    */
    public String PickList = new String();
    //<summary>Enum:PatFieldMapping Certain reports such as Medicaid make use of patient fields that are explicitly mapped.</summary>
    //public PatFieldMapping FieldMapping;
    /**
    * 
    */
    public PatFieldDef copy() throws Exception {
        return (PatFieldDef)this.MemberwiseClone();
    }

}


