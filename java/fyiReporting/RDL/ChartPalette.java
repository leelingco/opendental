//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ChartPaletteEnum;
import fyiReporting.RDL.ReportLog;

public class ChartPalette   
{
    static public ChartPaletteEnum getStyle(String s, ReportLog rl) throws Exception {
        ChartPaletteEnum p = ChartPaletteEnum.Default;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Default"))
        {
            p = ChartPaletteEnum.Default;
        }
        else if (__dummyScrutVar0.equals("EarthTones"))
        {
            p = ChartPaletteEnum.EarthTones;
        }
        else if (__dummyScrutVar0.equals("Excel"))
        {
            p = ChartPaletteEnum.Excel;
        }
        else if (__dummyScrutVar0.equals("GrayScale"))
        {
            p = ChartPaletteEnum.GrayScale;
        }
        else if (__dummyScrutVar0.equals("Light"))
        {
            p = ChartPaletteEnum.Light;
        }
        else if (__dummyScrutVar0.equals("Pastel"))
        {
            p = ChartPaletteEnum.Pastel;
        }
        else if (__dummyScrutVar0.equals("SemiTransparent"))
        {
            p = ChartPaletteEnum.SemiTransparent;
        }
        else
        {
            rl.logError(4,"Unknown ChartPalette '" + s + "'.  Default assumed.");
            p = ChartPaletteEnum.Default;
        }       
        return p;
    }

}


