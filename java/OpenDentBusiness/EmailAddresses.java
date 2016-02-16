//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Db;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EmailAddresses   
{
    /**
    * A list of all EmailAddresses.
    */
    private static List<EmailAddress> listt = new List<EmailAddress>();
    /**
    * A list of all EmailAddresses.
    */
    public static List<EmailAddress> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<EmailAddress> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM emailaddress";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "EmailAddress";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.EmailAddressCrud.TableToList(table);
    }

    /**
    * Gets the default email address for the clinic/practice. Takes a clinic num. If clinic num is 0 or there is no default for that clinic, it will get practice default. May return a new blank object.
    */
    public static EmailAddress getByClinic(long clinicNum) throws Exception {
        EmailAddress emailAddress = null;
        Clinic clinic = Clinics.getClinic(clinicNum);
        if (PrefC.getBool(PrefName.EasyNoClinics) || clinic == null)
        {
            //No clinic, get practice default
            emailAddress = getOne(PrefC.getLong(PrefName.EmailDefaultAddressNum));
        }
        else
        {
            emailAddress = getOne(clinic.EmailAddressNum);
            if (emailAddress == null)
            {
                //clinic.EmailAddressNum 0. Use default.
                emailAddress = getOne(PrefC.getLong(PrefName.EmailDefaultAddressNum));
            }
             
        } 
        if (emailAddress == null)
        {
            if (listt.Count > 0)
            {
                //user didn't set a default
                emailAddress = listt[0];
            }
            else
            {
                emailAddress = new EmailAddress();
            } 
        }
         
        return emailAddress;
    }

    //To avoid null checks.
    /**
    * Gets one EmailAddress from the cached listt.  Might be null.
    */
    public static EmailAddress getOne(long emailAddressNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemoteRole; Calls GetTableRemotelyIfNeeded().
            if (getListt()[i].EmailAddressNum == emailAddressNum)
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Checks to make sure at least one email address has a valid (not blank) SMTP server.
    */
    public static boolean existsValidEmail() throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            if (!StringSupport.equals(getListt()[i].SMTPserver, ""))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * 
    */
    public static long insert(EmailAddress emailAddress) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            emailAddress.EmailAddressNum = Meth.GetLong(MethodBase.GetCurrentMethod(), emailAddress);
            return emailAddress.EmailAddressNum;
        }
         
        return Crud.EmailAddressCrud.Insert(emailAddress);
    }

    /**
    * 
    */
    public static void update(EmailAddress emailAddress) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), emailAddress);
            return ;
        }
         
        Crud.EmailAddressCrud.Update(emailAddress);
    }

    /**
    * 
    */
    public static void delete(long emailAddressNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), emailAddressNum);
            return ;
        }
         
        String command = "DELETE FROM emailaddress WHERE EmailAddressNum = " + POut.long(emailAddressNum);
        Db.nonQ(command);
    }

}


