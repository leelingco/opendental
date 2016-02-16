//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;


/**
* 
*/
public class Format   
{
    /**
    * 
    */
    public static String numberHideZero(Object original) throws Exception {
        //string original){
        if (original == null)
        {
            return "";
        }
         
        double num = (Double)original;
        //PIn.PDouble(original);
        if (num == 0)
        {
            return "";
        }
         
        return num.ToString("N");
    }

}


//}