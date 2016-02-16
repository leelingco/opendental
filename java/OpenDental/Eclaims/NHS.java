//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import OpenDentBusiness.Clearinghouse;

/**
* United Kindgdom National Health Service (NHS).
*/
public class NHS   
{
    /**
    * 
    */
    public NHS() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. If they failed, a rollback will happen automatically by deleting the previously created FP17 file. The batchnum is supplied for the possible rollback.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        boolean retVal = true;
        return retVal;
    }

}


