//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Allergy;
import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Allergym;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AllergymCrud   
{
    /**
    * Gets one Allergym object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static Allergym selectOne(long customerNum, long allergyNum) throws Exception {
        String command = "SELECT * FROM allergym " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND AllergyNum = " + POut.long(allergyNum);
        List<Allergym> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Allergym object from the database using a query.
    */
    public static Allergym selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Allergym> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Allergym objects from the database using a query.
    */
    public static List<Allergym> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Allergym> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Allergym> tableToList(DataTable table) throws Exception {
        List<Allergym> retVal = new List<Allergym>();
        Allergym allergym;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            allergym = new Allergym();
            allergym.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            allergym.AllergyNum = PIn.Long(table.Rows[i]["AllergyNum"].ToString());
            allergym.AllergyDefNum = PIn.Long(table.Rows[i]["AllergyDefNum"].ToString());
            allergym.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            allergym.Reaction = PIn.String(table.Rows[i]["Reaction"].ToString());
            allergym.StatusIsActive = PIn.Bool(table.Rows[i]["StatusIsActive"].ToString());
            allergym.DateAdverseReaction = PIn.Date(table.Rows[i]["DateAdverseReaction"].ToString());
            retVal.Add(allergym);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one Allergym into the database.
    */
    public static long insert(Allergym allergym, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            allergym.AllergyNum = ReplicationServers.getKey("allergym","AllergyNum");
        }
         
        String command = "INSERT INTO allergym (";
        command += "AllergyNum,";
        command += "CustomerNum,AllergyDefNum,PatNum,Reaction,StatusIsActive,DateAdverseReaction) VALUES(";
        command += POut.long(allergym.AllergyNum) + ",";
        command += POut.long(allergym.CustomerNum) + "," + POut.long(allergym.AllergyDefNum) + "," + POut.long(allergym.PatNum) + "," + "'" + POut.string(allergym.Reaction) + "'," + POut.bool(allergym.StatusIsActive) + "," + POut.date(allergym.DateAdverseReaction) + ")";
        Db.nonQ(command);
        return allergym.AllergyNum;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one Allergym in the database.
    */
    public static void update(Allergym allergym) throws Exception {
        String command = "UPDATE allergym SET " + "AllergyDefNum      =  " + POut.long(allergym.AllergyDefNum) + ", " + "PatNum             =  " + POut.long(allergym.PatNum) + ", " + "Reaction           = '" + POut.string(allergym.Reaction) + "', " + "StatusIsActive     =  " + POut.bool(allergym.StatusIsActive) + ", " + "DateAdverseReaction=  " + POut.date(allergym.DateAdverseReaction) + " " + "WHERE CustomerNum = " + POut.long(allergym.CustomerNum) + " AND AllergyNum = " + POut.long(allergym.AllergyNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Allergym from the database.
    */
    public static void delete(long customerNum, long allergyNum) throws Exception {
        String command = "DELETE FROM allergym " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND AllergyNum = " + POut.long(allergyNum);
        Db.nonQ(command);
    }

    /**
    * Converts one Allergy object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static Allergym convertToM(Allergy allergy) throws Exception {
        Allergym allergym = new Allergym();
        //CustomerNum cannot be set.  Remains 0.
        allergym.AllergyNum = allergy.AllergyNum;
        allergym.AllergyDefNum = allergy.AllergyDefNum;
        allergym.PatNum = allergy.PatNum;
        allergym.Reaction = allergy.Reaction;
        allergym.StatusIsActive = allergy.StatusIsActive;
        allergym.DateAdverseReaction = allergy.DateAdverseReaction;
        return allergym;
    }

}


