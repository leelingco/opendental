//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cdcrec;
import OpenDentBusiness.TableBase;

//using System.Collections;
//using System.Drawing;
/**
* CDC Race and Ethnicity.  About 200 rows.  This table is not used anywhere right now.
*/
public class Cdcrec  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CdcrecNum = new long();
    /**
    * CDCREC Code.  Example: 1002-5.  Not allowed to edit this column once saved in the database.
    */
    public String CdcrecCode = new String();
    /**
    * Heirarchical Code. Example:
    * R1       =="American Indian or alaska Native"R1.01    =="American Indian"R1.01.001=="Abenaki"Not allowed to edit this column once saved in the database.
    */
    public String HeirarchicalCode = new String();
    /**
    * Description.
    */
    public String Description = new String();
    /**
    * 
    */
    public Cdcrec copy() throws Exception {
        return (Cdcrec)this.MemberwiseClone();
    }

}


