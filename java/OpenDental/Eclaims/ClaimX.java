//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import OpenDentBusiness.Clearinghouse;

/**
* ClaimX. added by RSM 7/27/11
*/
public class ClaimX   
{
    /**
    * 
    */
    public ClaimX() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        try
        {
            //call the client program
            //Process process=
            Process.Start(clearhouse.ClientProgram);
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }

        return true;
    }

}


//process.EnableRaisingEvents=true;
//process.WaitForExit();
//X12.Rollback(clearhouse,batchNum);//doesn't actually do anything