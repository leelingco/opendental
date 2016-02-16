//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.BenefitCoverageLevel;

public class EB02   
{
    private String code = new String();
    private String descript = new String();
    private BenefitCoverageLevel coverageLevel = BenefitCoverageLevel.None;
    private boolean isSupported = new boolean();
    public EB02(String code, String descript, BenefitCoverageLevel coverageLevel) throws Exception {
        this.code = code;
        this.descript = descript;
        this.coverageLevel = coverageLevel;
        this.isSupported = true;
    }

    public EB02(String code, String descript) throws Exception {
        this.code = code;
        this.descript = descript;
        this.coverageLevel = BenefitCoverageLevel.Individual;
        //ignored
        this.isSupported = false;
    }

    public BenefitCoverageLevel getCoverageLevel() throws Exception {
        return coverageLevel;
    }

    public void setCoverageLevel(BenefitCoverageLevel value) throws Exception {
        coverageLevel = value;
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


