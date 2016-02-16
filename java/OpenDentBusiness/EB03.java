//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EbenefitCategory;

public class EB03   
{
    private String code = new String();
    private String descript = new String();
    private EbenefitCategory serviceType = EbenefitCategory.None;
    private boolean isSupported = new boolean();
    public EB03(String code, String descript, EbenefitCategory serviceType) throws Exception {
        this.code = code;
        this.descript = descript;
        this.serviceType = serviceType;
        this.isSupported = true;
    }

    public EB03(String code, String descript) throws Exception {
        this.code = code;
        this.descript = descript;
        this.serviceType = EbenefitCategory.None;
        //ignored
        this.isSupported = false;
    }

    public EbenefitCategory getServiceType() throws Exception {
        return serviceType;
    }

    public void setServiceType(EbenefitCategory value) throws Exception {
        serviceType = value;
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


