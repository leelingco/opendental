//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;


public class DrillParameter   
{
    public String ParameterName = new String();
    public String ParameterValue = new String();
    public String ParameterOmit = new String();
    public DrillParameter(String name, String pvalue, String omit) throws Exception {
        ParameterName = name;
        ParameterValue = pvalue;
        ParameterOmit = omit;
    }

}


