//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RequestedPageType;


/**
* 
*/
public class DestinationType   
{

    private RequestedPageType requestedPageField = RequestedPageType.compose;
    private String logoutPageField = new String();
    private String sessionTimeoutInMinutesField = new String();
    /**
    * 
    */
    public RequestedPageType getrequestedPage() throws Exception {
        return this.requestedPageField;
    }

    public void setrequestedPage(RequestedPageType value) throws Exception {
        this.requestedPageField = value;
    }

    /**
    * 
    */
    public String getlogoutPage() throws Exception {
        return this.logoutPageField;
    }

    public void setlogoutPage(String value) throws Exception {
        this.logoutPageField = value;
    }

    /**
    * 
    */
    public String getsessionTimeoutInMinutes() throws Exception {
        return this.sessionTimeoutInMinutesField;
    }

    public void setsessionTimeoutInMinutes(String value) throws Exception {
        this.sessionTimeoutInMinutesField = value;
    }

}


