//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package MobileWeb;

import CS2JNet.JavaSupport.language.RefSupport;
import MobileWeb.Util;
import OpenDentBusiness.Mobile.Pharmacym;
import OpenDentBusiness.Mobile.Pharmacyms;
import WebForms.Logger;


//------------------------------------------------------------------------------// <auto-generated>//     This code was generated by a tool.////     Changes to this file may cause incorrect behavior and will be lost if//     the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------
public class PharmacyDetails  extends System.Web.UI.Page 
{

    private long PharmacyNum = 0;
    private long CustomerNum = 0;
    private Util util = new Util();
    public String DialLinkPhone = "";
    public Pharmacym phar;
    protected void page_Load(Object sender, EventArgs e) throws Exception {
        try
        {
            CustomerNum = util.GetCustomerNum(Message);
            if (CustomerNum == 0)
            {
                return ;
            }
             
            if (Request["PharmacyNum"] != null)
            {
                RefSupport refVar___0 = new RefSupport();
                Int64.TryParse(Request["PharmacyNum"].ToString().Trim(), refVar___0);
                PharmacyNum = refVar___0.getValue();
            }
             
            phar = Pharmacyms.getOne(CustomerNum,PharmacyNum);
            String DialString1 = "&nbsp;&nbsp;&nbsp;<a href=\"tel:";
            String DialString2 = "\" class=\"style2\">dial</a>";
            if (!String.IsNullOrEmpty(phar.Phone))
            {
                DialLinkPhone = DialString1 + phar.Phone + DialString2;
            }
             
        }
        catch (Exception ex)
        {
            LabelError.Text = Util.ErrorMessage;
            Logger.logError(ex);
        }
    
    }

    /**
    * Message control.
    * 
    * Auto-generated field.
    * To modify move field declaration from designer file to code-behind file.
    */
    protected System.Web.UI.WebControls.Literal Message = new System.Web.UI.WebControls.Literal();
    /**
    * LabelError control.
    * 
    * Auto-generated field.
    * To modify move field declaration from designer file to code-behind file.
    */
    protected System.Web.UI.WebControls.Label LabelError = new System.Web.UI.WebControls.Label();
}

