//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* These are templates that are used to send simple letters to patients.
*/
public class Letter  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LetterNum = new long();
    /**
    * Description of the Letter.
    */
    public String Description = new String();
    /**
    * Text of the letter
    */
    public String BodyText = new String();
}


