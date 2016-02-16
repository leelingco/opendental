//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:59 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrLabResultsCopyToCrud   
{
    /**
    * Gets one EhrLabResultsCopyTo object from the database using the primary key.  Returns null if not found.
    */
    public static EhrLabResultsCopyTo selectOne(long ehrLabResultsCopyToNum) throws Exception {
        String command = "SELECT * FROM ehrlabresultscopyto " + "WHERE EhrLabResultsCopyToNum = " + POut.Long(ehrLabResultsCopyToNum);
        List<EhrLabResultsCopyTo> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrLabResultsCopyTo object from the database using a query.
    */
    public static EhrLabResultsCopyTo selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabResultsCopyTo> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrLabResultsCopyTo objects from the database using a query.
    */
    public static List<EhrLabResultsCopyTo> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabResultsCopyTo> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrLabResultsCopyTo> tableToList(DataTable table) throws Exception {
        List<EhrLabResultsCopyTo> retVal = new List<EhrLabResultsCopyTo>();
        EhrLabResultsCopyTo ehrLabResultsCopyTo = new EhrLabResultsCopyTo();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrLabResultsCopyTo = new EhrLabResultsCopyTo();
            ehrLabResultsCopyTo.EhrLabResultsCopyToNum = PIn.Long(table.Rows[i]["EhrLabResultsCopyToNum"].ToString());
            ehrLabResultsCopyTo.EhrLabNum = PIn.Long(table.Rows[i]["EhrLabNum"].ToString());
            ehrLabResultsCopyTo.CopyToID = PIn.String(table.Rows[i]["CopyToID"].ToString());
            ehrLabResultsCopyTo.CopyToLName = PIn.String(table.Rows[i]["CopyToLName"].ToString());
            ehrLabResultsCopyTo.CopyToFName = PIn.String(table.Rows[i]["CopyToFName"].ToString());
            ehrLabResultsCopyTo.CopyToMiddleNames = PIn.String(table.Rows[i]["CopyToMiddleNames"].ToString());
            ehrLabResultsCopyTo.CopyToSuffix = PIn.String(table.Rows[i]["CopyToSuffix"].ToString());
            ehrLabResultsCopyTo.CopyToPrefix = PIn.String(table.Rows[i]["CopyToPrefix"].ToString());
            ehrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID = PIn.String(table.Rows[i]["CopyToAssigningAuthorityNamespaceID"].ToString());
            ehrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID = PIn.String(table.Rows[i]["CopyToAssigningAuthorityUniversalID"].ToString());
            ehrLabResultsCopyTo.CopyToAssigningAuthorityIDType = PIn.String(table.Rows[i]["CopyToAssigningAuthorityIDType"].ToString());
            String copyToNameTypeCode = table.Rows[i]["CopyToNameTypeCode"].ToString();
            if (StringSupport.equals(copyToNameTypeCode, ""))
            {
                ehrLabResultsCopyTo.CopyToNameTypeCode = (HL70200)0;
            }
            else
                try
                {
                    ehrLabResultsCopyTo.CopyToNameTypeCode = (HL70200)Enum.Parse(HL70200.class, copyToNameTypeCode);
                }
                catch (Exception __dummyCatchVar0)
                {
                    ehrLabResultsCopyTo.CopyToNameTypeCode = (HL70200)0;
                }
             
            String copyToIdentifierTypeCode = table.Rows[i]["CopyToIdentifierTypeCode"].ToString();
            if (StringSupport.equals(copyToIdentifierTypeCode, ""))
            {
                ehrLabResultsCopyTo.CopyToIdentifierTypeCode = (HL70203)0;
            }
            else
                try
                {
                    ehrLabResultsCopyTo.CopyToIdentifierTypeCode = (HL70203)Enum.Parse(HL70203.class, copyToIdentifierTypeCode);
                }
                catch (Exception __dummyCatchVar1)
                {
                    ehrLabResultsCopyTo.CopyToIdentifierTypeCode = (HL70203)0;
                }
             
            retVal.Add(ehrLabResultsCopyTo);
        }
        return retVal;
    }

    /**
    * Inserts one EhrLabResultsCopyTo into the database.  Returns the new priKey.
    */
    public static long insert(EhrLabResultsCopyTo ehrLabResultsCopyTo) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrLabResultsCopyTo.EhrLabResultsCopyToNum = DbHelper.GetNextOracleKey("ehrlabresultscopyto", "EhrLabResultsCopyToNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrLabResultsCopyTo,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrLabResultsCopyTo.EhrLabResultsCopyToNum++;
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
            return insert(ehrLabResultsCopyTo,false);
        } 
    }

    /**
    * Inserts one EhrLabResultsCopyTo into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrLabResultsCopyTo ehrLabResultsCopyTo, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrLabResultsCopyTo.EhrLabResultsCopyToNum = ReplicationServers.GetKey("ehrlabresultscopyto", "EhrLabResultsCopyToNum");
        }
         
        String command = "INSERT INTO ehrlabresultscopyto (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrLabResultsCopyToNum,";
        }
         
        command += "EhrLabNum,CopyToID,CopyToLName,CopyToFName,CopyToMiddleNames,CopyToSuffix,CopyToPrefix,CopyToAssigningAuthorityNamespaceID,CopyToAssigningAuthorityUniversalID,CopyToAssigningAuthorityIDType,CopyToNameTypeCode,CopyToIdentifierTypeCode) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrLabResultsCopyTo.EhrLabResultsCopyToNum) + ",";
        }
         
        command += POut.Long(ehrLabResultsCopyTo.EhrLabNum) + "," + "'" + POut.String(ehrLabResultsCopyTo.CopyToID) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToLName) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToFName) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToMiddleNames) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToSuffix) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToPrefix) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityIDType) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToNameTypeCode.ToString()) + "'," + "'" + POut.String(ehrLabResultsCopyTo.CopyToIdentifierTypeCode.ToString()) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrLabResultsCopyTo.EhrLabResultsCopyToNum = Db.NonQ(command, true);
        } 
        return ehrLabResultsCopyTo.EhrLabResultsCopyToNum;
    }

    /**
    * Updates one EhrLabResultsCopyTo in the database.
    */
    public static void update(EhrLabResultsCopyTo ehrLabResultsCopyTo) throws Exception {
        String command = "UPDATE ehrlabresultscopyto SET " + "EhrLabNum                          =  " + POut.Long(ehrLabResultsCopyTo.EhrLabNum) + ", " + "CopyToID                           = '" + POut.String(ehrLabResultsCopyTo.CopyToID) + "', " + "CopyToLName                        = '" + POut.String(ehrLabResultsCopyTo.CopyToLName) + "', " + "CopyToFName                        = '" + POut.String(ehrLabResultsCopyTo.CopyToFName) + "', " + "CopyToMiddleNames                  = '" + POut.String(ehrLabResultsCopyTo.CopyToMiddleNames) + "', " + "CopyToSuffix                       = '" + POut.String(ehrLabResultsCopyTo.CopyToSuffix) + "', " + "CopyToPrefix                       = '" + POut.String(ehrLabResultsCopyTo.CopyToPrefix) + "', " + "CopyToAssigningAuthorityNamespaceID= '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID) + "', " + "CopyToAssigningAuthorityUniversalID= '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID) + "', " + "CopyToAssigningAuthorityIDType     = '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityIDType) + "', " + "CopyToNameTypeCode                 = '" + POut.String(ehrLabResultsCopyTo.CopyToNameTypeCode.ToString()) + "', " + "CopyToIdentifierTypeCode           = '" + POut.String(ehrLabResultsCopyTo.CopyToIdentifierTypeCode.ToString()) + "' " + "WHERE EhrLabResultsCopyToNum = " + POut.Long(ehrLabResultsCopyTo.EhrLabResultsCopyToNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrLabResultsCopyTo in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrLabResultsCopyTo ehrLabResultsCopyTo, EhrLabResultsCopyTo oldEhrLabResultsCopyTo) throws Exception {
        String command = "";
        if (ehrLabResultsCopyTo.EhrLabNum != oldEhrLabResultsCopyTo.EhrLabNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EhrLabNum = " + POut.Long(ehrLabResultsCopyTo.EhrLabNum) + "";
        }
         
        if (ehrLabResultsCopyTo.CopyToID != oldEhrLabResultsCopyTo.CopyToID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToID = '" + POut.String(ehrLabResultsCopyTo.CopyToID) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToLName != oldEhrLabResultsCopyTo.CopyToLName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToLName = '" + POut.String(ehrLabResultsCopyTo.CopyToLName) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToFName != oldEhrLabResultsCopyTo.CopyToFName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToFName = '" + POut.String(ehrLabResultsCopyTo.CopyToFName) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToMiddleNames != oldEhrLabResultsCopyTo.CopyToMiddleNames)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToMiddleNames = '" + POut.String(ehrLabResultsCopyTo.CopyToMiddleNames) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToSuffix != oldEhrLabResultsCopyTo.CopyToSuffix)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToSuffix = '" + POut.String(ehrLabResultsCopyTo.CopyToSuffix) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToPrefix != oldEhrLabResultsCopyTo.CopyToPrefix)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToPrefix = '" + POut.String(ehrLabResultsCopyTo.CopyToPrefix) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID != oldEhrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToAssigningAuthorityNamespaceID = '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityNamespaceID) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID != oldEhrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToAssigningAuthorityUniversalID = '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityUniversalID) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToAssigningAuthorityIDType != oldEhrLabResultsCopyTo.CopyToAssigningAuthorityIDType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToAssigningAuthorityIDType = '" + POut.String(ehrLabResultsCopyTo.CopyToAssigningAuthorityIDType) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToNameTypeCode != oldEhrLabResultsCopyTo.CopyToNameTypeCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToNameTypeCode = '" + POut.String(ehrLabResultsCopyTo.CopyToNameTypeCode.ToString()) + "'";
        }
         
        if (ehrLabResultsCopyTo.CopyToIdentifierTypeCode != oldEhrLabResultsCopyTo.CopyToIdentifierTypeCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CopyToIdentifierTypeCode = '" + POut.String(ehrLabResultsCopyTo.CopyToIdentifierTypeCode.ToString()) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrlabresultscopyto SET " + command + " WHERE EhrLabResultsCopyToNum = " + POut.Long(ehrLabResultsCopyTo.EhrLabResultsCopyToNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrLabResultsCopyTo from the database.
    */
    public static void delete(long ehrLabResultsCopyToNum) throws Exception {
        String command = "DELETE FROM ehrlabresultscopyto " + "WHERE EhrLabResultsCopyToNum = " + POut.Long(ehrLabResultsCopyToNum);
        Db.NonQ(command);
    }

}

