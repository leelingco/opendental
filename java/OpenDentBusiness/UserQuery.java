//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* A list of query favorites that users can run.
*/
public class UserQuery  extends TableBase 
{
    /**
    * Primary key.
    */
    public long QueryNum = new long();
    /**
    * Description.
    */
    public String Description = new String();
    /**
    * The name of the file to export to.
    */
    public String FileName = new String();
    /**
    * The text of the query.
    */
    public String QueryText = new String();
}


