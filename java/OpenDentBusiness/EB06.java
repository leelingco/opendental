//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.BenefitTimePeriod;

/**
* Time period qualifier
*/
public class EB06   
{
    private String code = new String();
    private String descript = new String();
    private BenefitTimePeriod timePeriod = BenefitTimePeriod.None;
    private boolean isSupported = new boolean();
    public EB06(String code, String descript, BenefitTimePeriod timePeriod) throws Exception {
        this.code = code;
        this.descript = descript;
        this.timePeriod = timePeriod;
        this.isSupported = true;
    }

    public EB06(String code, String descript) throws Exception {
        this.code = code;
        this.descript = descript;
        this.timePeriod = BenefitTimePeriod.None;
        //ignored
        this.isSupported = false;
    }

    public BenefitTimePeriod getTimePeriod() throws Exception {
        return timePeriod;
    }

    public void setTimePeriod(BenefitTimePeriod value) throws Exception {
        timePeriod = value;
    }

    public String getCode() throws Exception {
        return code;
    }

    public void setCode(String value) throws Exception {
        code = value;
    }

    public String getDescript() throws Exception {
        return descript;
    }

    public void setDescript(String value) throws Exception {
        descript = value;
    }

    public boolean getIsSupported() throws Exception {
        return isSupported;
    }

    public void setIsSupported(boolean value) throws Exception {
        isSupported = value;
    }

}


