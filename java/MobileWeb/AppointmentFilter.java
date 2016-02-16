//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package MobileWeb;

import CS2JNet.JavaSupport.language.RefSupport;
import MobileWeb.Util;
import OpenDentBusiness.Mobile.Providerm;
import OpenDentBusiness.Mobile.Providerms;
import WebForms.Logger;


//------------------------------------------------------------------------------// <auto-generated>//     This code was generated by a tool.////     Changes to this file may cause incorrect behavior and will be lost if//     the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------
public class AppointmentFilter  extends System.Web.UI.Page 
{

    private long CustomerNum = 0;
    private long ProvNum = 0;
    private Util util = new Util();
    protected void page_Load(Object sender, EventArgs e) throws Exception {
        try
        {
            CustomerNum = util.GetCustomerNum(Message);
            if (CustomerNum == 0)
            {
                return ;
            }
             
            List<Providerm> providermList = Providerms.getProviderms(CustomerNum);
            for (int i = 0;i < providermList.Count;i++)
            {
                //the elements inthe drop down are padded left and right so that they are centered.
                String abbr = providermList[i].Abbr;
                int PadLength = 14 - abbr.Length / 2;
                providermList[i].Abbr = abbr.PadLeft(PadLength, ' ').PadRight(PadLength, ' ').Replace(" ", "&nbsp;");
            }
            Repeater1.DataSource = providermList;
            Repeater1.DataBind();
        }
        catch (Exception ex)
        {
            LabelError.Text = Util.ErrorMessage;
            Logger.logError(ex);
        }
    
    }

    public String getSelected(Providerm pv) throws Exception {
        try
        {
            if (Session["ProvNum"] != null)
            {
                RefSupport refVar___0 = new RefSupport();
                Int64.TryParse(Session["ProvNum"].ToString(), refVar___0);
                ProvNum = refVar___0.getValue();
            }
             
            if (pv.ProvNum == ProvNum)
            {
                return " selected=\"selected\"";
            }
            else
            {
                return "";
            } 
        }
        catch (Exception ex)
        {
            Logger.logError("CustomerNum=" + CustomerNum + " pv.ProvNum=" + pv.ProvNum,ex);
            return "";
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
    /**
    * Repeater1 control.
    * 
    * Auto-generated field.
    * To modify move field declaration from designer file to code-behind file.
    */
    protected System.Web.UI.WebControls.Repeater Repeater1 = new System.Web.UI.WebControls.Repeater();
}

