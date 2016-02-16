//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class VixWinBase41   
{
    /**
    * 
    */
    public VixWinBase41() throws Exception {
    }

    /**
    * Sends data for Patient.Cur by command line interface.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            return ;
        }
         
        //Example: c:\vixwin\vixwin -I 12AB@# -N Bill^Smith -P X:\VXImages\12AB#$\
        String info = "-I " + convertToBase41(pat.PatNum);
        String ppImagePath = ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Image Path");
        info += " -N " + pat.FName.Replace(" ", "") + "^" + pat.LName.Replace(" ", "");
        //no spaces allowed
        if (StringSupport.equals(ppImagePath, ""))
        {
            MessageBox.Show("Image Path cannot be left blank.");
            return ;
        }
         
        if (!ppImagePath.EndsWith("\\"))
        {
            //if program path doesn't end with "\" then add it to the end.
            ppImagePath += "\\";
        }
         
        ppImagePath += convertToBase41(pat.PatNum) + "\\";
        //if we later allow ChartNumbers, then we will have to validate them to be 6 digits or less.
        info += " -P " + ppImagePath;
        //create image directory if it doesn't exist
        if (!Directory.Exists(ppImagePath))
        {
            try
            {
                //if the directory doesn't exist
                Directory.CreateDirectory(ppImagePath);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show("Unable to create patient image directory " + ppImagePath);
                return ;
            }
        
        }
         
        try
        {
            //MessageBox.Show(info);
            Process.Start(path, info);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(path + " is not available. Ensure that the program and image paths are valid.");
        }
    
    }

    /**
    * Returns the value of x^y. We cannot use Math.Pow(), because Math.Pow() uses doubles only, which it has rounding errors with large numbers.
    * We need our result to be a perfect integer. We assume y>=0.
    */
    private static long pow(long x, long y) throws Exception {
        long result = 1;
        for (int p = 0;p < y;p++)
        {
            result = result * x;
        }
        return result;
    }

    /**
    * This function will translate the Base10 Dentrix ID to the Base41 VixWin ID.
    */
    private static String convertToBase41(long intPatNum) throws Exception {
        String retVal = "";
        String base41Array = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@#$%^";
        long intID = intPatNum;
        long intTemp = 0;
        long intMultiplier = 0;
        for (int i = 5;i >= 0;i--)
        {
            intMultiplier = Pow(41, i);
            intTemp = 0;
            if (intID >= intMultiplier)
            {
                intTemp = intID / intMultiplier;
            }
             
            //resulting float is truncated intentionally
            intID = intID - intTemp * intMultiplier;
            retVal = retVal + base41Array[(int)intTemp];
        }
        return retVal;
    }

}


