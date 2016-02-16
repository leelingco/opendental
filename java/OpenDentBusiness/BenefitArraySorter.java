//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Benefit;

/*================================================================================================================
	=========================================== class BenefitArraySorter =============================================*/
/**
* 
*/
public class BenefitArraySorter  extends IComparer 
{
    /**
    * 
    */
    int iComparer___Compare(Object x, Object y) throws Exception {
        Benefit[] array1 = (Benefit[])x;
        Benefit ben1 = null;
        for (long i = 0;i < array1.Length;i++)
        {
            if (array1[i] == null)
            {
                continue;
            }
             
            ben1 = array1[i].Copy();
            break;
        }
        Benefit[] array2 = (Benefit[])y;
        Benefit ben2 = null;
        for (int i = 0;i < array2.Length;i++)
        {
            if (array2[i] == null)
            {
                continue;
            }
             
            ben2 = array2[i].Copy();
            break;
        }
        return (ben1.compareTo(ben2));
    }

}


