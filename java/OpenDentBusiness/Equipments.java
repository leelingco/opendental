//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.EnumEquipmentDisplayMode;
import OpenDentBusiness.Equipment;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Equipments   
{
    /**
    * 
    */
    public static List<Equipment> getList(DateTime fromDate, DateTime toDate, EnumEquipmentDisplayMode display, String snDesc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Equipment>>GetObject(MethodBase.GetCurrentMethod(), fromDate, toDate, display, snDesc);
        }
         
        String command = "";
        if (display == EnumEquipmentDisplayMode.Purchased)
        {
            command = "SELECT * FROM equipment " + "WHERE DatePurchased >= " + POut.date(fromDate) + " AND DatePurchased <= " + POut.date(toDate) + " AND (SerialNumber LIKE '%" + POut.string(snDesc) + "%' OR Description LIKE '%" + POut.string(snDesc) + "%' OR Location LIKE '%" + POut.string(snDesc) + "%')" + " ORDER BY DatePurchased";
        }
         
        if (display == EnumEquipmentDisplayMode.Sold)
        {
            command = "SELECT * FROM equipment " + "WHERE DateSold >= " + POut.date(fromDate) + " AND DateSold <= " + POut.date(toDate) + " AND (SerialNumber LIKE '%" + POut.string(snDesc) + "%' OR Description LIKE '%" + POut.string(snDesc) + "%' OR Location LIKE '%" + POut.string(snDesc) + "%')" + " ORDER BY DatePurchased";
        }
         
        if (display == EnumEquipmentDisplayMode.All)
        {
            command = "SELECT * FROM equipment " + "WHERE ((DatePurchased >= " + POut.date(fromDate) + " AND DatePurchased <= " + POut.date(toDate) + ")" + " OR (DateSold >= " + POut.date(fromDate) + " AND DateSold <= " + POut.date(toDate) + "))" + " AND (SerialNumber LIKE '%" + POut.string(snDesc) + "%' OR Description LIKE '%" + POut.string(snDesc) + "%' OR Location LIKE '%" + POut.string(snDesc) + "%')" + " ORDER BY DatePurchased";
        }
         
        return Crud.EquipmentCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(Equipment equip) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            equip.EquipmentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), equip);
            return equip.EquipmentNum;
        }
         
        return Crud.EquipmentCrud.Insert(equip);
    }

    /**
    * 
    */
    public static void update(Equipment equip) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), equip);
            return ;
        }
         
        Crud.EquipmentCrud.Update(equip);
    }

    /**
    * 
    */
    public static void delete(Equipment equip) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), equip);
            return ;
        }
         
        String command = "DELETE FROM equipment" + " WHERE EquipmentNum = " + POut.long(equip.EquipmentNum);
        Db.nonQ(command);
    }

    /**
    * Generates a unique 3 char alphanumeric serialnumber.  Checks to make sure it's not already in use.
    */
    public static String generateSerialNum() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String retVal = "";
        boolean isDuplicate = true;
        Random rand = new Random();
        while (isDuplicate)
        {
            retVal = "";
            for (int i = 0;i < 4;i++)
            {
                int r = rand.Next(0, 34);
                if (r < 9)
                {
                    retVal += (char)('1' + r);
                }
                else
                {
                    //1-9, no zero
                    retVal += (char)('A' + r - 9);
                } 
            }
            String command = "SELECT COUNT(*) FROM equipment WHERE SerialNumber = '" + POut.string(retVal) + "'";
            if (StringSupport.equals(Db.getScalar(command), "0"))
            {
                isDuplicate = false;
            }
             
        }
        return retVal;
    }

}


