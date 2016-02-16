//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Reseller;

/**
* 
*/
public class Resellers   
{
    /**
    * Gets one Reseller from the db.
    */
    public static Reseller getOne(long resellerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Reseller>GetObject(MethodBase.GetCurrentMethod(), resellerNum);
        }
         
        return Crud.ResellerCrud.SelectOne(resellerNum);
    }

    /**
    * 
    */
    public static long insert(Reseller reseller) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            reseller.ResellerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), reseller);
            return reseller.ResellerNum;
        }
         
        return Crud.ResellerCrud.Insert(reseller);
    }

    /**
    * 
    */
    public static void update(Reseller reseller) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reseller);
            return ;
        }
         
        Crud.ResellerCrud.Update(reseller);
    }

    /**
    * Make sure to check that the reseller does not have any customers before deleting them.
    */
    public static void delete(long resellerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), resellerNum);
            return ;
        }
         
        String command = "DELETE FROM reseller WHERE ResellerNum = " + POut.long(resellerNum);
        Db.nonQ(command);
    }

    /**
    * Gets a list of resellers and some of their information.  Only used from FormResellers to fill the grid.
    */
    public static DataTable getResellerList() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT ResellerNum,patient.PatNum,LName,FName,Preferred,WkPhone,WirelessPhone,PhoneNumberVal,Address,City,State,Email,PatStatus " + "FROM reseller " + "INNER JOIN patient ON reseller.PatNum=patient.PatNum " + "LEFT JOIN phonenumber ON phonenumber.PatNum=patient.PatNum " + "GROUP BY patient.PatNum " + "ORDER BY LName ";
        return Db.getTable(command);
    }

    /**
    * Gets all of the customers of the reseller (family members) that have active services.  Only used from FormResellerEdit to fill the grid.
    */
    public static DataTable getResellerCustomersList(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT patient.PatNum,RegKey,procedurecode.ProcCode,procedurecode.Descript,resellerservice.Fee,repeatcharge.DateStart,repeatcharge.DateStop,repeatcharge.Note " + "FROM patient " + "INNER JOIN registrationkey ON patient.PatNum=registrationkey.PatNum AND IsResellerCustomer=1 " + "LEFT JOIN repeatcharge ON patient.PatNum=repeatcharge.PatNum " + "LEFT JOIN procedurecode ON repeatcharge.ProcCode=procedurecode.ProcCode " + "LEFT JOIN reseller ON patient.Guarantor=reseller.PatNum " + "LEFT JOIN resellerservice ON reseller.ResellerNum=resellerservice.resellerNum AND resellerservice.CodeNum=procedurecode.CodeNum " + "WHERE patient.PatNum!='" + POut.long(patNum) + "' " + "AND patient.Guarantor='" + POut.long(patNum) + "' " + "ORDER BY patient.LName ";
        return Db.getTable(command);
    }

    /**
    * Checks the database to see if the user name is already in use.
    */
    public static boolean isUserNameInUse(long patNum, String userName) throws Exception {
        String command = "SELECT COUNT(*) FROM reseller WHERE PatNum!=" + POut.long(patNum) + " AND UserName='" + POut.string(userName) + "'";
        if (PIn.int(Db.getScalar(command)) > 0)
        {
            return true;
        }
         
        return false;
    }

    //User name in use.
    /**
    * Checks the database to see if the patient is a reseller.
    */
    public static boolean isResellerFamily(long guarantor) throws Exception {
        String command = "SELECT COUNT(*) FROM reseller WHERE PatNum=" + POut.long(guarantor);
        if (PIn.int(Db.getScalar(command)) > 0)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Checks the database to see if the reseller has customers with active repeating charges.
    */
    public static boolean hasActiveResellerCustomers(Reseller reseller) throws Exception {
        String command = "SELECT COUNT(*) FROM patient\r\n" + 
        "\t\t\t\tINNER JOIN registrationkey ON patient.PatNum=registrationkey.PatNum AND IsResellerCustomer=1\r\n" + 
        "\t\t\t\tINNER JOIN repeatcharge ON patient.PatNum=repeatcharge.PatNum\r\n" + 
        "\t\t\t\tINNER JOIN procedurecode ON repeatcharge.ProcCode=procedurecode.ProcCode\r\n" + 
        "\t\t\t\tINNER JOIN resellerservice ON procedurecode.CodeNum=resellerservice.CodeNum \r\n" + 
        "\t\t\t\tWHERE ((DATE(repeatcharge.DateStart)<=DATE(NOW()) AND ((YEAR(repeatcharge.DateStop)<1880) OR (DATE(NOW()<DATE(repeatcharge.DateStop))))))\r\n" + 
        "\t\t\t\tAND patient.Guarantor=" + POut.long(reseller.PatNum) + " " + "AND resellerservice.ResellerNum=" + POut.long(reseller.ResellerNum) + " " + "GROUP BY patient.PatNum";
        if (PIn.int(Db.getScalar(command)) > 0)
        {
            return true;
        }
         
        return false;
    }

}


