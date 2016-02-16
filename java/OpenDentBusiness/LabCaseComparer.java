//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;


/**
* The supplied DataRows must include the following columns: AptDateTime,patient
*/
public class LabCaseComparer  extends IComparer<DataRow> 
{
    /**
    * 
    */
    public int compare(DataRow x, DataRow y) throws Exception {
        DateTime dtx = (DateTime)x["AptDateTime"];
        DateTime dty = (DateTime)y["AptDateTime"];
        if (dty != dtx)
        {
            return dtx.CompareTo(dty);
        }
         
        return x["patient"].ToString().CompareTo(y["patient"].ToString());
    }

}


