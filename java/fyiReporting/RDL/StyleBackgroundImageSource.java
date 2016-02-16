//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.StyleBackgroundImageSourceEnum;

// Illegal (or no) value specified
public class StyleBackgroundImageSource   
{
    static public StyleBackgroundImageSourceEnum getStyle(String s) throws Exception {
        StyleBackgroundImageSourceEnum rs = StyleBackgroundImageSourceEnum.External;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("External"))
        {
            rs = StyleBackgroundImageSourceEnum.External;
        }
        else if (__dummyScrutVar0.equals("Embedded"))
        {
            rs = StyleBackgroundImageSourceEnum.Embedded;
        }
        else if (__dummyScrutVar0.equals("Database"))
        {
            rs = StyleBackgroundImageSourceEnum.Database;
        }
        else
        {
            // user error just force to normal TODO
            rs = StyleBackgroundImageSourceEnum.External;
        }   
        return rs;
    }

}


