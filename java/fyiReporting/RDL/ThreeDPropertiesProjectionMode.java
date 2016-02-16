//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ThreeDPropertiesProjectionModeEnum;

public class ThreeDPropertiesProjectionMode   
{
    static public ThreeDPropertiesProjectionModeEnum getStyle(String s) throws Exception {
        ThreeDPropertiesProjectionModeEnum pm = ThreeDPropertiesProjectionModeEnum.Perspective;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Perspective"))
        {
            pm = ThreeDPropertiesProjectionModeEnum.Perspective;
        }
        else if (__dummyScrutVar0.equals("Orthographic"))
        {
            pm = ThreeDPropertiesProjectionModeEnum.Orthographic;
        }
        else
        {
            pm = ThreeDPropertiesProjectionModeEnum.Perspective;
        }  
        return pm;
    }

}


