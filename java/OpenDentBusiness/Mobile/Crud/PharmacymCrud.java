//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Pharmacym;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PharmacymCrud   
{
    /**
    * Gets one Pharmacym object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static Pharmacym selectOne(long customerNum, long pharmacyNum) throws Exception {
        String command = "SELECT * FROM pharmacym " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PharmacyNum = " + POut.long(pharmacyNum);
        List<Pharmacym> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Pharmacym object from the database using a query.
    */
    public static Pharmacym selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Pharmacym> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Pharmacym objects from the database using a query.
    */
    public static List<Pharmacym> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Pharmacym> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Pharmacym> tableToList(DataTable table) throws Exception {
        List<Pharmacym> retVal = new List<Pharmacym>();
        Pharmacym pharmacym;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            pharmacym = new Pharmacym();
            pharmacym.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            pharmacym.PharmacyNum = PIn.Long(table.Rows[i]["PharmacyNum"].ToString());
            pharmacym.StoreName = PIn.String(table.Rows[i]["StoreName"].ToString());
            pharmacym.Phone = PIn.String(table.Rows[i]["Phone"].ToString());
            pharmacym.Fax = PIn.String(table.Rows[i]["Fax"].ToString());
            pharmacym.Address = PIn.String(table.Rows[i]["Address"].ToString());
            pharmacym.Address2 = PIn.String(table.Rows[i]["Address2"].ToString());
            pharmacym.City = PIn.String(table.Rows[i]["City"].ToString());
            pharmacym.State = PIn.String(table.Rows[i]["State"].ToString());
            pharmacym.Zip = PIn.String(table.Rows[i]["Zip"].ToString());
            pharmacym.Note = PIn.String(table.Rows[i]["Note"].ToString());
            retVal.Add(pharmacym);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one Pharmacym into the database.
    */
    public static long insert(Pharmacym pharmacym, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            pharmacym.PharmacyNum = ReplicationServers.getKey("pharmacym","PharmacyNum");
        }
         
        String command = "INSERT INTO pharmacym (";
        command += "PharmacyNum,";
        command += "CustomerNum,StoreName,Phone,Fax,Address,Address2,City,State,Zip,Note) VALUES(";
        command += POut.long(pharmacym.PharmacyNum) + ",";
        command += POut.long(pharmacym.CustomerNum) + "," + "'" + POut.string(pharmacym.StoreName) + "'," + "'" + POut.string(pharmacym.Phone) + "'," + "'" + POut.string(pharmacym.Fax) + "'," + "'" + POut.string(pharmacym.Address) + "'," + "'" + POut.string(pharmacym.Address2) + "'," + "'" + POut.string(pharmacym.City) + "'," + "'" + POut.string(pharmacym.State) + "'," + "'" + POut.string(pharmacym.Zip) + "'," + "'" + POut.string(pharmacym.Note) + "')";
        Db.nonQ(command);
        return pharmacym.PharmacyNum;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one Pharmacym in the database.
    */
    public static void update(Pharmacym pharmacym) throws Exception {
        String command = "UPDATE pharmacym SET " + "StoreName  = '" + POut.string(pharmacym.StoreName) + "', " + "Phone      = '" + POut.string(pharmacym.Phone) + "', " + "Fax        = '" + POut.string(pharmacym.Fax) + "', " + "Address    = '" + POut.string(pharmacym.Address) + "', " + "Address2   = '" + POut.string(pharmacym.Address2) + "', " + "City       = '" + POut.string(pharmacym.City) + "', " + "State      = '" + POut.string(pharmacym.State) + "', " + "Zip        = '" + POut.string(pharmacym.Zip) + "', " + "Note       = '" + POut.string(pharmacym.Note) + "' " + "WHERE CustomerNum = " + POut.long(pharmacym.CustomerNum) + " AND PharmacyNum = " + POut.long(pharmacym.PharmacyNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Pharmacym from the database.
    */
    public static void delete(long customerNum, long pharmacyNum) throws Exception {
        String command = "DELETE FROM pharmacym " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PharmacyNum = " + POut.long(pharmacyNum);
        Db.nonQ(command);
    }

    /**
    * Converts one Pharmacy object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static Pharmacym convertToM(Pharmacy pharmacy) throws Exception {
        Pharmacym pharmacym = new Pharmacym();
        //CustomerNum cannot be set.  Remains 0.
        pharmacym.PharmacyNum = pharmacy.PharmacyNum;
        pharmacym.StoreName = pharmacy.StoreName;
        pharmacym.Phone = pharmacy.Phone;
        pharmacym.Fax = pharmacy.Fax;
        pharmacym.Address = pharmacy.Address;
        pharmacym.Address2 = pharmacy.Address2;
        pharmacym.City = pharmacy.City;
        pharmacym.State = pharmacy.State;
        pharmacym.Zip = pharmacy.Zip;
        pharmacym.Note = pharmacy.Note;
        return pharmacym;
    }

}


