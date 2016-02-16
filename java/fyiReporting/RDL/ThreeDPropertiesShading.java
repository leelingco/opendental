//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.ThreeDPropertiesShadingEnum;

public class ThreeDPropertiesShading   
{
    static public ThreeDPropertiesShadingEnum getStyle(String s, ReportLog rl) throws Exception {
        ThreeDPropertiesShadingEnum sh = ThreeDPropertiesShadingEnum.None;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("None"))
        {
            sh = ThreeDPropertiesShadingEnum.None;
        }
        else if (__dummyScrutVar0.equals("Simple"))
        {
            sh = ThreeDPropertiesShadingEnum.Simple;
        }
        else if (__dummyScrutVar0.equals("Real"))
        {
            sh = ThreeDPropertiesShadingEnum.Real;
        }
        else
        {
            rl.logError(4,"Unknown Shading '" + s + "'.  None assumed.");
            sh = ThreeDPropertiesShadingEnum.None;
        }   
        return sh;
    }

}


