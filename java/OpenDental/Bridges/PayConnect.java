//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PayConnectService.Credentials;
import OpenDental.PayConnectService.creditCardRequest;
import OpenDental.PayConnectService.MerchantService;
import OpenDental.PayConnectService.transResponse;
import OpenDental.PayConnectService.transType;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

public class PayConnect   
{
    private static Credentials getCredentials(Program prog) throws Exception {
        Credentials cred = new OpenDental.PayConnectService.Credentials();
        ArrayList props = ProgramProperties.getForProgram(prog.ProgramNum);
        cred.setUsername("");
        cred.setPassword("");
        for (int i = 0;i < props.Count;i++)
        {
            ProgramProperty prop = (ProgramProperty)props[i];
            if (StringSupport.equals(prop.PropertyDesc, "Username"))
            {
                cred.setUsername(prop.PropertyValue);
            }
            else if (StringSupport.equals(prop.PropertyDesc, "Password"))
            {
                cred.setPassword(prop.PropertyValue);
            }
              
        }
        cred.setClient("OpenDental2");
        cred.setServiceID("DCI Web Service ID: 006328");
        //Production
        //cred.ServiceID="DCI Web Service ID: 002778";//Testing
        cred.setversion("0310");
        return cred;
    }

    public static creditCardRequest buildSaleRequest(double amount, String cardNumber, int expYear, int expMonth, String nameOnCard, String securityCode, String zip, String magData, transType transtype, String refNumber) throws Exception {
        creditCardRequest request = new OpenDental.PayConnectService.creditCardRequest();
        request.setAmount(amount);
        request.setAmountSpecified(true);
        request.setCardNumber(cardNumber);
        request.setExpiration(new OpenDental.PayConnectService.expiration());
        request.getExpiration().setyear(expYear);
        request.getExpiration().setmonth(expMonth);
        if (magData != null)
        {
            //MagData is the data returned from magnetic card readers. Will only be present if a card was swiped.
            request.setMagData(magData);
        }
         
        request.setNameOnCard(nameOnCard);
        request.setRefNumber(refNumber);
        request.setSecurityCode(securityCode);
        request.setTransType(transtype);
        request.setZip(zip);
        return request;
    }

    /**
    * Shows a message box on error.
    */
    public static transResponse processCreditCard(OpenDental.PayConnectService.creditCardRequest request) throws Exception {
        try
        {
            Program prog = Programs.getCur(ProgramName.PayConnect);
            OpenDental.PayConnectService.Credentials cred = getCredentials(prog);
            MerchantService ms = new OpenDental.PayConnectService.MerchantService();
            transResponse response = ms.processCreditCard(cred,request);
            ms.Dispose();
            if (response.getStatus().getcode() != 0)
            {
                //Error
                MessageBox.Show(Lan.g("PayConnect","Payment failed") + ". \r\n" + Lan.g("PayConnect","Error message from") + " Pay Connect: \"" + response.getStatus().getdescription() + "\"");
            }
             
            return response;
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g("PayConnect","Payment failed") + ". \r\n" + Lan.g("PayConnect","Error message from") + " Open Dental: \"" + ex.Message + "\"");
        }

        return null;
    }

}


