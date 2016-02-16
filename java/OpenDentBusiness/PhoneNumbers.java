//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.PhoneNumber;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PhoneNumbers   
{
    public static List<PhoneNumber> getPhoneNumbers(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PhoneNumber>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM phonenumber WHERE PatNum=" + POut.long(patNum);
        return Crud.PhoneNumberCrud.SelectMany(command);
    }

    public static PhoneNumber getByVal(String phoneNumberVal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PhoneNumber>GetObject(MethodBase.GetCurrentMethod(), phoneNumberVal);
        }
         
        String command = "SELECT * FROM phonenumber WHERE PhoneNumberVal='" + POut.string(phoneNumberVal) + "'";
        return Crud.PhoneNumberCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(PhoneNumber phoneNumber) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            phoneNumber.PhoneNumberNum = Meth.GetLong(MethodBase.GetCurrentMethod(), phoneNumber);
            return phoneNumber.PhoneNumberNum;
        }
         
        return Crud.PhoneNumberCrud.Insert(phoneNumber);
    }

    /**
    * 
    */
    public static void update(PhoneNumber phoneNumber) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneNumber);
            return ;
        }
         
        Crud.PhoneNumberCrud.Update(phoneNumber);
    }

    public static void deleteObject(long phoneNumberNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phoneNumberNum);
            return ;
        }
         
        Crud.PhoneNumberCrud.Delete(phoneNumberNum);
    }

}


