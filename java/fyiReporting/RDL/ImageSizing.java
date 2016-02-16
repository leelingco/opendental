//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.ReportLog;

/**
* Use ImageSizing when you want to take a string and map it to the ImageSizingEnum.
*/
public class ImageSizing   
{
    /**
    * Given a string return the cooresponding enumeration.
    * 
    *  @param s 
    *  @return The enumerated value corresponding to the string.
    */
    static public ImageSizingEnum getStyle(String s) throws Exception {
        return getStyle(s,null);
    }

    static public ImageSizingEnum getStyle(String s, ReportLog rl) throws Exception {
        ImageSizingEnum rs = ImageSizingEnum.AutoSize;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("AutoSize"))
        {
            rs = ImageSizingEnum.AutoSize;
        }
        else if (__dummyScrutVar0.equals("Fit"))
        {
            rs = ImageSizingEnum.Fit;
        }
        else if (__dummyScrutVar0.equals("FitProportional"))
        {
            rs = ImageSizingEnum.FitProportional;
        }
        else if (__dummyScrutVar0.equals("Clip"))
        {
            rs = ImageSizingEnum.Clip;
        }
        else
        {
            if (rl != null)
                rl.logError(4,"Unknown ImageSizing '" + s + "'.  AutoSize assumed.");
             
            rs = ImageSizingEnum.AutoSize;
        }    
        return rs;
    }

}


