//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ImageSourceEnum;

// Illegal or unspecified
public class ImageSource   
{
    static public ImageSourceEnum getStyle(String s) throws Exception {
        ImageSourceEnum rs = ImageSourceEnum.External;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("External"))
        {
            rs = ImageSourceEnum.External;
        }
        else if (__dummyScrutVar0.equals("Embedded"))
        {
            rs = ImageSourceEnum.Embedded;
        }
        else if (__dummyScrutVar0.equals("Database"))
        {
            rs = ImageSourceEnum.Database;
        }
        else
        {
            rs = ImageSourceEnum.Unknown;
        }   
        return rs;
    }

}


