//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupClaimStatusCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupClaimStatusCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupFamilyEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupFamilyEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupTerminalClaimStatusCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupTerminalClaimStatusCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.lookupTerminalEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupTerminalEligibilityCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventHandler;
import OpenDental.Properties.Settings;


//------------------------------------------------------------------------------// <auto-generated>//     This code was generated by a tool.//     Runtime Version:4.0.30319.235////     Changes to this file may cause incorrect behavior and will be lost if//     the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------//// This source code was auto-generated by Microsoft.VSDesigner, Version 4.0.30319.235.///**
* 
*/
public class WebServiceService  extends System.Web.Services.Protocols.SoapHttpClientProtocol 
{

    private System.Threading.SendOrPostCallback lookupEligibilityOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback lookupClaimStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback lookupFamilyEligibilityOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback lookupTerminalEligibilityOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback lookupTerminalClaimStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback updateTerminalOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback lookupClaimOperationCompleted = new System.Threading.SendOrPostCallback();
    private boolean useDefaultCredentialsSetExplicitly = new boolean();
    /**
    * 
    */
    public WebServiceService() throws Exception {
        this.setUrl(Settings.getDefault().getOpenDental_com_dentalxchange_webservices_WebServiceService());
        if ((this.isLocalFileSystemWebService(this.getUrl()) == true))
        {
            this.setUseDefaultCredentials(true);
            this.useDefaultCredentialsSetExplicitly = false;
        }
        else
        {
            this.useDefaultCredentialsSetExplicitly = true;
        } 
    }

    public String getUrl() throws Exception {
        return super.Url;
    }

    public void setUrl(String value) throws Exception {
        if ((((this.IsLocalFileSystemWebService(super.Url) == true) && (this.useDefaultCredentialsSetExplicitly == false)) && (this.isLocalFileSystemWebService(value) == false)))
        {
            super.UseDefaultCredentials = false;
        }
         
        super.Url = value;
    }

    public boolean getUseDefaultCredentials() throws Exception {
        return super.UseDefaultCredentials;
    }

    public void setUseDefaultCredentials(boolean value) throws Exception {
        super.UseDefaultCredentials = value;
        this.useDefaultCredentialsSetExplicitly = true;
    }

    /**
    * 
    */
    public lookupEligibilityCompletedEventHandler lookupEligibilityCompleted;
    /**
    * 
    */
    public lookupClaimStatusCompletedEventHandler lookupClaimStatusCompleted;
    /**
    * 
    */
    public lookupFamilyEligibilityCompletedEventHandler lookupFamilyEligibilityCompleted;
    /**
    * 
    */
    public lookupTerminalEligibilityCompletedEventHandler lookupTerminalEligibilityCompleted;
    /**
    * 
    */
    public lookupTerminalClaimStatusCompletedEventHandler lookupTerminalClaimStatusCompleted;
    /**
    * 
    */
    public updateTerminalCompletedEventHandler updateTerminalCompleted;
    /**
    * 
    */
    public lookupClaimCompletedEventHandler lookupClaimCompleted;
    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupEligibility(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupEligibility", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupEligibilityAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupEligibilityOperationCompleted == null))
        {
            this.lookupEligibilityOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupEligibilityOperationCompleted);
        }
         
        this.InvokeAsync("lookupEligibility", new Object[]{ in0, in1 }, this.lookupEligibilityOperationCompleted, userState);
    }

    private void onlookupEligibilityOperationCompleted(Object arg) throws Exception {
        if ((this.lookupEligibilityCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupEligibilityCompleted.invoke(this,new lookupEligibilityCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupClaimStatus(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupClaimStatus", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupClaimStatusAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupClaimStatusAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupClaimStatusAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupClaimStatusOperationCompleted == null))
        {
            this.lookupClaimStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupClaimStatusOperationCompleted);
        }
         
        this.InvokeAsync("lookupClaimStatus", new Object[]{ in0, in1 }, this.lookupClaimStatusOperationCompleted, userState);
    }

    private void onlookupClaimStatusOperationCompleted(Object arg) throws Exception {
        if ((this.lookupClaimStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupClaimStatusCompleted.invoke(this,new lookupClaimStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupFamilyEligibility(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupFamilyEligibility", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupFamilyEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupFamilyEligibilityAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupFamilyEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupFamilyEligibilityOperationCompleted == null))
        {
            this.lookupFamilyEligibilityOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupFamilyEligibilityOperationCompleted);
        }
         
        this.InvokeAsync("lookupFamilyEligibility", new Object[]{ in0, in1 }, this.lookupFamilyEligibilityOperationCompleted, userState);
    }

    private void onlookupFamilyEligibilityOperationCompleted(Object arg) throws Exception {
        if ((this.lookupFamilyEligibilityCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupFamilyEligibilityCompleted.invoke(this,new lookupFamilyEligibilityCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupTerminalEligibility(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupTerminalEligibility", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupTerminalEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupTerminalEligibilityAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupTerminalEligibilityAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupTerminalEligibilityOperationCompleted == null))
        {
            this.lookupTerminalEligibilityOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupTerminalEligibilityOperationCompleted);
        }
         
        this.InvokeAsync("lookupTerminalEligibility", new Object[]{ in0, in1 }, this.lookupTerminalEligibilityOperationCompleted, userState);
    }

    private void onlookupTerminalEligibilityOperationCompleted(Object arg) throws Exception {
        if ((this.lookupTerminalEligibilityCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupTerminalEligibilityCompleted.invoke(this,new lookupTerminalEligibilityCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupTerminalClaimStatus(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupTerminalClaimStatus", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupTerminalClaimStatusAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupTerminalClaimStatusAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupTerminalClaimStatusAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupTerminalClaimStatusOperationCompleted == null))
        {
            this.lookupTerminalClaimStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupTerminalClaimStatusOperationCompleted);
        }
         
        this.InvokeAsync("lookupTerminalClaimStatus", new Object[]{ in0, in1 }, this.lookupTerminalClaimStatusOperationCompleted, userState);
    }

    private void onlookupTerminalClaimStatusOperationCompleted(Object arg) throws Exception {
        if ((this.lookupTerminalClaimStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupTerminalClaimStatusCompleted.invoke(this,new lookupTerminalClaimStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response updateTerminal(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("updateTerminal", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void updateTerminalAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.updateTerminalAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void updateTerminalAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.updateTerminalOperationCompleted == null))
        {
            this.updateTerminalOperationCompleted = new System.Threading.SendOrPostCallback(this.OnupdateTerminalOperationCompleted);
        }
         
        this.InvokeAsync("updateTerminal", new Object[]{ in0, in1 }, this.updateTerminalOperationCompleted, userState);
    }

    private void onupdateTerminalOperationCompleted(Object arg) throws Exception {
        if ((this.updateTerminalCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.updateTerminalCompleted.invoke(this,new updateTerminalCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public OpenDental.com.dentalxchange.webservices.Response lookupClaim(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        Object[] results = this.Invoke("lookupClaim", new Object[]{ in0, in1 });
        return ((OpenDental.com.dentalxchange.webservices.Response)(results[0]));
    }

    /**
    * 
    */
    public void lookupClaimAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1) throws Exception {
        this.lookupClaimAsync(in0,in1,null);
    }

    /**
    * 
    */
    public void lookupClaimAsync(OpenDental.com.dentalxchange.webservices.Credentials in0, OpenDental.com.dentalxchange.webservices.Request in1, Object userState) throws Exception {
        if ((this.lookupClaimOperationCompleted == null))
        {
            this.lookupClaimOperationCompleted = new System.Threading.SendOrPostCallback(this.OnlookupClaimOperationCompleted);
        }
         
        this.InvokeAsync("lookupClaim", new Object[]{ in0, in1 }, this.lookupClaimOperationCompleted, userState);
    }

    private void onlookupClaimOperationCompleted(Object arg) throws Exception {
        if ((this.lookupClaimCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.lookupClaimCompleted.invoke(this,new lookupClaimCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public void cancelAsync(Object userState) throws Exception {
        super.CancelAsync(userState);
    }

    private boolean isLocalFileSystemWebService(String url) throws Exception {
        if (((url == null) || (StringSupport.equals(url, String.Empty))))
        {
            return false;
        }
         
        System.Uri wsUri = new System.Uri(url);
        if (((wsUri.Port >= 1024) && (String.Compare(wsUri.Host, "localHost", System.StringComparison.OrdinalIgnoreCase) == 0)))
        {
            return true;
        }
         
        return false;
    }

}


