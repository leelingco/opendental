//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;


/**
* A list of query favorites that users can run.
*/
public class UserQuery   
{
    public UserQuery() {
    }

    /**
    * Primary key.
    */
    public int QueryNum = new int();
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


