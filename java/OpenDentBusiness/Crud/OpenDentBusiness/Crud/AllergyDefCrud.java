//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:55 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AllergyDefCrud   
{
    /**
    * Gets one AllergyDef object from the database using the primary key.  Returns null if not found.
    */
    public static AllergyDef selectOne(long allergyDefNum) throws Exception {
        String command = "SELECT * FROM allergydef " + "WHERE AllergyDefNum = " + POut.Long(allergyDefNum);
        List<AllergyDef> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one AllergyDef object from the database using a query.
    */
    public static AllergyDef selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AllergyDef> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of AllergyDef objects from the database using a query.
    */
    public static List<AllergyDef> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AllergyDef> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<AllergyDef> tableToList(DataTable table) throws Exception {
        List<AllergyDef> retVal = new List<AllergyDef>();
        AllergyDef allergyDef = new AllergyDef();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            allergyDef = new AllergyDef();
            allergyDef.AllergyDefNum = PIn.Long(table.Rows[i]["AllergyDefNum"].ToString());
            allergyDef.Description = PIn.String(table.Rows[i]["Description"].ToString());
            allergyDef.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            allergyDef.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            allergyDef.SnomedType = (SnomedAllergy)PIn.Int(table.Rows[i]["SnomedType"].ToString());
            allergyDef.MedicationNum = PIn.Long(table.Rows[i]["MedicationNum"].ToString());
            allergyDef.UniiCode = PIn.String(table.Rows[i]["UniiCode"].ToString());
            retVal.Add(allergyDef);
        }
        return retVal;
    }

    /**
    * Inserts one AllergyDef into the database.  Returns the new priKey.
    */
    public static long insert(AllergyDef allergyDef) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            allergyDef.AllergyDefNum = DbHelper.GetNextOracleKey("allergydef", "AllergyDefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(allergyDef,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        allergyDef.AllergyDefNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(allergyDef,false);
        } 
    }

    /**
    * Inserts one AllergyDef into the database.  Provides option to use the existing priKey.
    */
    public static long insert(AllergyDef allergyDef, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            allergyDef.AllergyDefNum = ReplicationServers.GetKey("allergydef", "AllergyDefNum");
        }
         
        String command = "INSERT INTO allergydef (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "AllergyDefNum,";
        }
         
        command += "Description,IsHidden,SnomedType,MedicationNum,UniiCode) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(allergyDef.AllergyDefNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += "'" + POut.String(allergyDef.Description) + "'," + POut.Bool(allergyDef.IsHidden) + "," + POut.Int((int)allergyDef.SnomedType) + "," + POut.Long(allergyDef.MedicationNum) + "," + "'" + POut.String(allergyDef.UniiCode) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            allergyDef.AllergyDefNum = Db.NonQ(command, true);
        } 
        return allergyDef.AllergyDefNum;
    }

    /**
    * Updates one AllergyDef in the database.
    */
    public static void update(AllergyDef allergyDef) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE allergydef SET " + "Description  = '" + POut.String(allergyDef.Description) + "', " + "IsHidden     =  " + POut.Bool(allergyDef.IsHidden) + ", " + "SnomedType   =  " + POut.Int((int)allergyDef.SnomedType) + ", " + "MedicationNum=  " + POut.Long(allergyDef.MedicationNum) + ", " + "UniiCode     = '" + POut.String(allergyDef.UniiCode) + "' " + "WHERE AllergyDefNum = " + POut.Long(allergyDef.AllergyDefNum);
        Db.NonQ(command);
    }

    /**
    * Updates one AllergyDef in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(AllergyDef allergyDef, AllergyDef oldAllergyDef) throws Exception {
        String command = "";
        if (allergyDef.Description != oldAllergyDef.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(allergyDef.Description) + "'";
        }
         
        if (allergyDef.IsHidden != oldAllergyDef.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.Bool(allergyDef.IsHidden) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (allergyDef.SnomedType != oldAllergyDef.SnomedType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SnomedType = " + POut.Int((int)allergyDef.SnomedType) + "";
        }
         
        if (allergyDef.MedicationNum != oldAllergyDef.MedicationNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedicationNum = " + POut.Long(allergyDef.MedicationNum) + "";
        }
         
        if (allergyDef.UniiCode != oldAllergyDef.UniiCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UniiCode = '" + POut.String(allergyDef.UniiCode) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE allergydef SET " + command + " WHERE AllergyDefNum = " + POut.Long(allergyDef.AllergyDefNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one AllergyDef from the database.
    */
    public static void delete(long allergyDefNum) throws Exception {
        String command = "DELETE FROM allergydef " + "WHERE AllergyDefNum = " + POut.Long(allergyDefNum);
        Db.NonQ(command);
    }

}


