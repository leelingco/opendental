//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DataTransferObject;

/**
* Gets an object which must be serializable.  Calling code will convert object to specific type.
*/
public class DtoGetObject  extends DataTransferObject 
{
    /**
    * This is the "FullName" string representation of the type of object that we expect back as a result.  Examples: System.Int32, OpenDentBusiness.Patient, OpenDentBusiness.Patient[], List<OpenDentBusiness.Patient>.  DataTable and DataSet not allowed.
    */
    public String ObjectType = new String();
}


