//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InsBenefitType;

public class EB01   
{
    private String code = new String();
    private String descript = new String();
    private InsBenefitType benefitType = InsBenefitType.ActiveCoverage;
    private boolean isSupported = new boolean();
    public EB01(String code, String descript, InsBenefitType benefitType) throws Exception {
        this.code = code;
        this.descript = descript;
        this.benefitType = benefitType;
        this.isSupported = true;
    }

    public EB01(String code, String descript) throws Exception {
        this.code = code;
        this.descript = descript;
        this.benefitType = InsBenefitType.ActiveCoverage;
        //ignored
        this.isSupported = false;
    }

    public InsBenefitType getBenefitType() throws Exception {
        return benefitType;
    }

    public void setBenefitType(InsBenefitType value) throws Exception {
        benefitType = value;
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


