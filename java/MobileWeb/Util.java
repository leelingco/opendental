//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package MobileWeb;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import MobileWeb.Properties.Settings;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.Mobile.Patientm;
import OpenDentBusiness.Mobile.Patientms;
import OpenDentBusiness.Mobile.Userm;
import OpenDentBusiness.Mobile.Userms;
import OpenDentBusiness.POut;
import WebForms.Logger;

public class Util   
{
    public static String ErrorMessage = "There has been an error in processing your request.";
    public void setMobileDbConnection() throws Exception {
        String connectStr = Settings.getDefault().getDBMobileWeb();
        OpenDentBusiness.DataConnection dc = new OpenDentBusiness.DataConnection();
        dc.setDb(connectStr,"",DatabaseType.MySql,true);
    }

    public long getDentalOfficeID(String username, String password) throws Exception {
        long DentalOfficeID = 0;
        String md5password = (new WebHostSynch.Util()).mD5Encrypt(password);
        try
        {
            // a query involving both username and password is used because 2 dental offices could potentially have the same username
            String command = "SELECT * FROM userm WHERE UserName='" + POut.string(username) + "' AND Password='" + POut.string(md5password) + "'";
            Userm um = Userms.getOne(command);
            if (um == null)
            {
                DentalOfficeID = 0;
            }
            else
            {
                //user password combination incorrect- specify message if necessary
                DentalOfficeID = um.CustomerNum;
            } 
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            return DentalOfficeID;
        }

        if (StringSupport.equals(username.ToLower(), "demo"))
        {
            //for demo only
            DentalOfficeID = getDemoDentalOfficeID();
        }
         
        return DentalOfficeID;
    }

    /**
    * If Properties.Settings.Default.something is used in AppointmentList.aspx.cs page it give a  MobileWeb.Properties.Settings is inaccessible due to its protection level
    */
    public long getDemoDentalOfficeID() throws Exception {
        return Settings.getDefault().getDemoDentalOfficeID();
    }

    /**
    * If Properties.Settings.Default.something is used in AppointmentList.aspx.cs page it give a  MobileWeb.Properties.Settings is inaccessible due to its protection level
    */
    public DateTime getDemoTodayDate() throws Exception {
        return Settings.getDefault().getDemoTodayDate();
    }

    public String getPatientName(long PatNum, long CustomerNum) throws Exception {
        try
        {
            String PatName = "";
            Patientm pat = Patientms.getOne(CustomerNum,PatNum);
            PatName = getPatientName(pat);
            return PatName;
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            return "";
        }
    
    }

    public String getPatientName(Patientm pat) throws Exception {
        try
        {
            String PatName = "";
            PatName += pat.LName + ", ";
            if (!String.IsNullOrEmpty(pat.Preferred))
            {
                PatName += "'" + pat.Preferred + "'";
            }
             
            PatName += " " + pat.FName + " ";
            if (!String.IsNullOrEmpty(pat.MiddleI))
            {
                PatName += pat.MiddleI + ".";
            }
             
            return PatName;
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            return "";
        }
    
    }

    public long getCustomerNum(System.Web.UI.WebControls.Literal Message) throws Exception {
        long CustomerNum = 0;
        try
        {
            Message.Text = "";
            if (HttpContext.Current.Session["CustomerNum"] == null)
            {
                return 0;
            }
             
            RefSupport<long> refVar___0 = new RefSupport<long>();
            Int64.TryParse(HttpContext.Current.Session["CustomerNum"].ToString(), refVar___0);
            CustomerNum = refVar___0.getValue();
            if (CustomerNum != 0)
            {
                Message.Text = "LoggedIn";
            }
             
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            return CustomerNum;
        }

        return CustomerNum;
    }

}


