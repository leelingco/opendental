//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;


/**
* The supplied DataRows must include the following columns: DateTime
*/
public class MedicalOrderLineComparer  extends IComparer<DataRow> 
{
    /**
    * 
    */
    public int compare(DataRow x, DataRow y) throws Exception {
        DateTime dt1 = (DateTime)x["DateTime"];
        DateTime dt2 = (DateTime)y["DateTime"];
        return dt1.CompareTo(dt2);
    }

}


