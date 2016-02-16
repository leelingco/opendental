//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package MobileWeb;

import MobileWeb.Util;

public class Global  extends System.Web.HttpApplication 
{
    protected void application_Start(Object sender, EventArgs e) throws Exception {
        (new Util()).setMobileDbConnection();
    }

    protected void session_Start(Object sender, EventArgs e) throws Exception {
    }

    protected void application_BeginRequest(Object sender, EventArgs e) throws Exception {
    }

    protected void application_AuthenticateRequest(Object sender, EventArgs e) throws Exception {
    }

    protected void application_Error(Object sender, EventArgs e) throws Exception {
    }

    protected void session_End(Object sender, EventArgs e) throws Exception {
    }

    protected void application_End(Object sender, EventArgs e) throws Exception {
    }

}


