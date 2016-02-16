//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.ThreeDPropertiesDrawingStyleEnum;

public class ThreeDPropertiesDrawingStyle   
{
    static public ThreeDPropertiesDrawingStyleEnum getStyle(String s, ReportLog rl) throws Exception {
        ThreeDPropertiesDrawingStyleEnum ds = ThreeDPropertiesDrawingStyleEnum.Cylinder;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Cylinder"))
        {
            ds = ThreeDPropertiesDrawingStyleEnum.Cylinder;
        }
        else if (__dummyScrutVar0.equals("Cube"))
        {
            ds = ThreeDPropertiesDrawingStyleEnum.Cube;
        }
        else
        {
            rl.logError(4,"Unknown DrawingStyle '" + s + "'.  Cube assumed.");
            ds = ThreeDPropertiesDrawingStyleEnum.Cube;
        }  
        return ds;
    }

}


