//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.BenefitQuantity;

/**
* Quantity qualifier
*/
public class EB09   
{
    private String code = new String();
    private String descript = new String();
    private BenefitQuantity quantityQualifier = BenefitQuantity.None;
    private boolean isSupported = new boolean();
    public EB09(String code, String descript, BenefitQuantity quantityQualifier) throws Exception {
        this.code = code;
        this.descript = descript;
        this.quantityQualifier = quantityQualifier;
        this.isSupported = true;
    }

    public EB09(String code, String descript) throws Exception {
        this.code = code;
        this.descript = descript;
        this.quantityQualifier = BenefitQuantity.None;
        //ignored
        this.isSupported = false;
    }

    public BenefitQuantity getQuantityQualifier() throws Exception {
        return quantityQualifier;
    }

    public void setQuantityQualifier(BenefitQuantity value) throws Exception {
        quantityQualifier = value;
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


