//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.OrthoChart;
import OpenDentBusiness.TableBase;

/**
* For the orthochart feature, each row in this table is one cell in that grid.  An empty cell corresponds to a missing db table row.
*/
public class OrthoChart  extends TableBase 
{
    /**
    * Primary key.
    */
    public long OrthoChartNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date of service.
    */
    public DateTime DateService = new DateTime();
    /**
    * .
    */
    public String FieldName = new String();
    /**
    * .
    */
    public String FieldValue = new String();
    /**
    * 
    */
    public OrthoChart copy() throws Exception {
        return (OrthoChart)this.MemberwiseClone();
    }

}


