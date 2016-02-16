//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Mobile.Userm;
import OpenDentBusiness.Mobile.Userms;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Userods;
import WebForms.Logger;
import WebHostSynch.DbInit;
import WebHostSynch.RegistrationKey;
import WebHostSynch.RegistrationKeys;

public class Util   
{
    /**
    * This method is redundant. It may be deleted later. Some older versions of OD may use this method.
    */
    public boolean checkRegistrationKey(String RegistrationKeyFromDentalOffice) throws Exception {
        Logger.information("In CheckRegistrationKey() RegistrationKeyFromDentalOffice=" + RegistrationKeyFromDentalOffice);
        String connectStr = ConfigurationManager.ConnectionStrings["DBRegKey"].ConnectionString;
        RegistrationKey RegistrationKeyFromDb = null;
        try
        {
            RegistrationKeys registrationKeys = new RegistrationKeys();
            registrationKeys.setDb(connectStr);
            RegistrationKeyFromDb = registrationKeys.getByKey(RegistrationKeyFromDentalOffice);
            DateTime d1 = new DateTime(1902, 1, 1);
            if (d1 < RegistrationKeyFromDb.DateDisabled && RegistrationKeyFromDb.DateDisabled < DateTime.Today)
            {
                Logger.information("RegistrationKey has been disabled. Dental OfficeId=" + RegistrationKeyFromDb.PatNum);
                return false;
            }
             
            if (d1 < RegistrationKeyFromDb.DateEnded && RegistrationKeyFromDb.DateEnded < DateTime.Today)
            {
                Logger.information("RegistrationKey DateEnded date is past. Dental OfficeId=" + RegistrationKeyFromDb.PatNum);
                return false;
            }
             
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            Logger.information("Exception thrown. IpAddress=" + HttpContext.Current.Request.UserHostAddress + " RegistrationKey=" + RegistrationKeyFromDentalOffice);
            return false;
        }

        return true;
    }

    public void setMobileDbConnection() throws Exception {
        DbInit.init();
    }

    public boolean isPaidCustomer(long customerNum) throws Exception {
        int count = 0;
        String connectStr = ConfigurationManager.ConnectionStrings["DBRegKey"].ConnectionString;
        //This query is seeing if any family member of the patient with the registration key has a specific repeating charge
        String command = "SELECT COUNT(*) FROM repeatcharge,patient " + "WHERE repeatcharge.PatNum=patient.PatNum " + "AND patient.Guarantor=(SELECT patkey.Guarantor FROM patient patkey WHERE patkey.PatNum=" + POut.long(customerNum) + ") " + "AND ProcCode='027' AND (DateStop='0001-01-01' OR DateStop > NOW())";
        WebHostSynch.Db db = new WebHostSynch.Db();
        db.setConn(connectStr);
        DataTable table = db.getTable(command);
        if (table.Rows.Count != 0)
        {
            count = PIn.Int(table.Rows[0][0].ToString());
        }
         
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public long getDentalOfficeID(String RegistrationKeyFromDentalOffice) throws Exception {
        String connectStr = ConfigurationManager.ConnectionStrings["DBRegKey"].ConnectionString;
        RegistrationKey RegistrationKeyFromDb = null;
        try
        {
            RegistrationKeys registrationKeys = new RegistrationKeys();
            registrationKeys.setDb(connectStr);
            RegistrationKeyFromDb = registrationKeys.getByKey(RegistrationKeyFromDentalOffice);
            DateTime d1 = new DateTime(1902, 1, 1);
            if (d1 < RegistrationKeyFromDb.DateDisabled && RegistrationKeyFromDb.DateDisabled < DateTime.Today)
            {
                Logger.information("RegistrationKey has been disabled. Dental OfficeId=" + RegistrationKeyFromDb.PatNum);
                return 0;
            }
             
            if (d1 < RegistrationKeyFromDb.DateEnded && RegistrationKeyFromDb.DateEnded < DateTime.Today)
            {
                Logger.information("RegistrationKey DateEnded date is past. Dental OfficeId=" + RegistrationKeyFromDb.PatNum);
                return 0;
            }
             
        }
        catch (Exception ex)
        {
            Logger.logError("Exception thrown. IpAddress=" + HttpContext.Current.Request.UserHostAddress + " RegistrationKey=" + RegistrationKeyFromDentalOffice,ex);
            return 0;
        }

        return RegistrationKeyFromDb.PatNum;
    }

    public void setMobileWebUserPassword(long customerNum, String UserName, String Password) throws Exception {
        Userm um = Userms.GetOne(customerNum, 1);
        boolean UserExists = true;
        if (um == null)
        {
            um = new Userm();
            UserExists = false;
        }
         
        um.CustomerNum = customerNum;
        um.UsermNum = 1;
        //always 1
        um.UserName = UserName;
        if (!StringSupport.equals(Password, ""))
        {
            //do not update password if password string is empty
            um.Password = mD5Encrypt(Password);
        }
         
        if (UserExists)
        {
            Userms.update(um);
        }
        else
        {
            Userms.insert(um);
        } 
    }

    public String getMobileWebUserName(long customerNum) throws Exception {
        String UserName = "";
        Userm um = Userms.GetOne(customerNum, 1);
        if (um != null)
        {
            UserName = um.UserName;
        }
         
        return UserName;
    }

    public String mD5Encrypt(String inputPass) throws Exception {
        /*
        				byte[] unicodeBytes=Encoding.Unicode.GetBytes(inputPass);
        				HashAlgorithm algorithm=MD5.Create();
        				byte[] hashbytes2=algorithm.ComputeHash(unicodeBytes);
        				return Convert.ToBase64String(hashbytes2);
        		 */
        String salt = "saturn";
        return Userods.encryptPassword(inputPass + salt,false);
    }

}


