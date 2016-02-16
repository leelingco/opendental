//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental.Eclaims;

import OpenDentBusiness.Clearinghouse;

/**
* Summary description for AOS. added by SPK 7/13/05
*/
public class AOS   
{
    /**
    * 
    */
    public AOS() throws Exception {
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