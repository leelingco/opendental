//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:01 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class HL7DefCrud   
{
    /**
    * Gets one HL7Def object from the database using the primary key.  Returns null if not found.
    */
    public static HL7Def selectOne(long hL7DefNum) throws Exception {
        String command = "SELECT * FROM hl7def " + "WHERE HL7DefNum = " + POut.Long(hL7DefNum);
        List<HL7Def> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one HL7Def object from the database using a query.
    */
    public static HL7Def selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7Def> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of HL7Def objects from the database using a query.
    */
    public static List<HL7Def> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7Def> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<HL7Def> tableToList(DataTable table) throws Exception {
        List<HL7Def> retVal = new List<HL7Def>();
        HL7Def hL7Def = new HL7Def();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            hL7Def = new HL7Def();
            hL7Def.HL7DefNum = PIn.Long(table.Rows[i]["HL7DefNum"].ToString());
            hL7Def.Description = PIn.String(table.Rows[i]["Description"].ToString());
            hL7Def.ModeTx = (ModeTxHL7)PIn.Int(table.Rows[i]["ModeTx"].ToString());
            hL7Def.IncomingFolder = PIn.String(table.Rows[i]["IncomingFolder"].ToString());
            hL7Def.OutgoingFolder = PIn.String(table.Rows[i]["OutgoingFolder"].ToString());
            hL7Def.IncomingPort = PIn.String(table.Rows[i]["IncomingPort"].ToString());
            hL7Def.OutgoingIpPort = PIn.String(table.Rows[i]["OutgoingIpPort"].ToString());
            hL7Def.FieldSeparator = PIn.String(table.Rows[i]["FieldSeparator"].ToString());
            hL7Def.ComponentSeparator = PIn.String(table.Rows[i]["ComponentSeparator"].ToString());
            hL7Def.SubcomponentSeparator = PIn.String(table.Rows[i]["SubcomponentSeparator"].ToString());
            hL7Def.RepetitionSeparator = PIn.String(table.Rows[i]["RepetitionSeparator"].ToString());
            hL7Def.EscapeCharacter = PIn.String(table.Rows[i]["EscapeCharacter"].ToString());
            hL7Def.IsInternal = PIn.Bool(table.Rows[i]["IsInternal"].ToString());
            hL7Def.InternalType = PIn.String(table.Rows[i]["InternalType"].ToString());
            hL7Def.InternalTypeVersion = PIn.String(table.Rows[i]["InternalTypeVersion"].ToString());
            hL7Def.IsEnabled = PIn.Bool(table.Rows[i]["IsEnabled"].ToString());
            hL7Def.Note = PIn.String(table.Rows[i]["Note"].ToString());
            hL7Def.HL7Server = PIn.String(table.Rows[i]["HL7Server"].ToString());
            hL7Def.HL7ServiceName = PIn.String(table.Rows[i]["HL7ServiceName"].ToString());
            hL7Def.ShowDemographics = (HL7ShowDemographics)PIn.Int(table.Rows[i]["ShowDemographics"].ToString());
            hL7Def.ShowAppts = PIn.Bool(table.Rows[i]["ShowAppts"].ToString());
            hL7Def.ShowAccount = PIn.Bool(table.Rows[i]["ShowAccount"].ToString());
            retVal.Add(hL7Def);
        }
        return retVal;
    }

    /**
    * Inserts one HL7Def into the database.  Returns the new priKey.
    */
    public static long insert(HL7Def hL7Def) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            hL7Def.HL7DefNum = DbHelper.GetNextOracleKey("hl7def", "HL7DefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(hL7Def,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        hL7Def.HL7DefNum++;
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
            return insert(hL7Def,false);
        } 
    }

    /**
    * Inserts one HL7Def into the database.  Provides option to use the existing priKey.
    */
    public static long insert(HL7Def hL7Def, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            hL7Def.HL7DefNum = ReplicationServers.GetKey("hl7def", "HL7DefNum");
        }
         
        String command = "INSERT INTO hl7def (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "HL7DefNum,";
        }
         
        command += "Description,ModeTx,IncomingFolder,OutgoingFolder,IncomingPort,OutgoingIpPort,FieldSeparator,ComponentSeparator,SubcomponentSeparator,RepetitionSeparator,EscapeCharacter,IsInternal,InternalType,InternalTypeVersion,IsEnabled,Note,HL7Server,HL7ServiceName,ShowDemographics,ShowAppts,ShowAccount) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(hL7Def.HL7DefNum) + ",";
        }
         
        command += "'" + POut.String(hL7Def.Description) + "'," + POut.Int((int)hL7Def.ModeTx) + "," + "'" + POut.String(hL7Def.IncomingFolder) + "'," + "'" + POut.String(hL7Def.OutgoingFolder) + "'," + "'" + POut.String(hL7Def.IncomingPort) + "'," + "'" + POut.String(hL7Def.OutgoingIpPort) + "'," + "'" + POut.String(hL7Def.FieldSeparator) + "'," + "'" + POut.String(hL7Def.ComponentSeparator) + "'," + "'" + POut.String(hL7Def.SubcomponentSeparator) + "'," + "'" + POut.String(hL7Def.RepetitionSeparator) + "'," + "'" + POut.String(hL7Def.EscapeCharacter) + "'," + POut.Bool(hL7Def.IsInternal) + "," + "'" + POut.String(hL7Def.InternalType) + "'," + "'" + POut.String(hL7Def.InternalTypeVersion) + "'," + POut.Bool(hL7Def.IsEnabled) + "," + DbHelper.ParamChar + "paramNote," + "'" + POut.String(hL7Def.HL7Server) + "'," + "'" + POut.String(hL7Def.HL7ServiceName) + "'," + POut.Int((int)hL7Def.ShowDemographics) + "," + POut.Bool(hL7Def.ShowAppts) + "," + POut.Bool(hL7Def.ShowAccount) + ")";
        if (hL7Def.Note == null)
        {
            hL7Def.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7Def.Note);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramNote);
        }
        else
        {
            hL7Def.HL7DefNum = Db.NonQ(command, true, paramNote);
        } 
        return hL7Def.HL7DefNum;
    }

    /**
    * Updates one HL7Def in the database.
    */
    public static void update(HL7Def hL7Def) throws Exception {
        String command = "UPDATE hl7def SET " + "Description          = '" + POut.String(hL7Def.Description) + "', " + "ModeTx               =  " + POut.Int((int)hL7Def.ModeTx) + ", " + "IncomingFolder       = '" + POut.String(hL7Def.IncomingFolder) + "', " + "OutgoingFolder       = '" + POut.String(hL7Def.OutgoingFolder) + "', " + "IncomingPort         = '" + POut.String(hL7Def.IncomingPort) + "', " + "OutgoingIpPort       = '" + POut.String(hL7Def.OutgoingIpPort) + "', " + "FieldSeparator       = '" + POut.String(hL7Def.FieldSeparator) + "', " + "ComponentSeparator   = '" + POut.String(hL7Def.ComponentSeparator) + "', " + "SubcomponentSeparator= '" + POut.String(hL7Def.SubcomponentSeparator) + "', " + "RepetitionSeparator  = '" + POut.String(hL7Def.RepetitionSeparator) + "', " + "EscapeCharacter      = '" + POut.String(hL7Def.EscapeCharacter) + "', " + "IsInternal           =  " + POut.Bool(hL7Def.IsInternal) + ", " + "InternalType         = '" + POut.String(hL7Def.InternalType) + "', " + "InternalTypeVersion  = '" + POut.String(hL7Def.InternalTypeVersion) + "', " + "IsEnabled            =  " + POut.Bool(hL7Def.IsEnabled) + ", " + "Note                 =  " + DbHelper.ParamChar + "paramNote, " + "HL7Server            = '" + POut.String(hL7Def.HL7Server) + "', " + "HL7ServiceName       = '" + POut.String(hL7Def.HL7ServiceName) + "', " + "ShowDemographics     =  " + POut.Int((int)hL7Def.ShowDemographics) + ", " + "ShowAppts            =  " + POut.Bool(hL7Def.ShowAppts) + ", " + "ShowAccount          =  " + POut.Bool(hL7Def.ShowAccount) + " " + "WHERE HL7DefNum = " + POut.Long(hL7Def.HL7DefNum);
        if (hL7Def.Note == null)
        {
            hL7Def.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7Def.Note);
        Db.NonQ(command, paramNote);
    }

    /**
    * Updates one HL7Def in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(HL7Def hL7Def, HL7Def oldHL7Def) throws Exception {
        String command = "";
        if (hL7Def.Description != oldHL7Def.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(hL7Def.Description) + "'";
        }
         
        if (hL7Def.ModeTx != oldHL7Def.ModeTx)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ModeTx = " + POut.Int((int)hL7Def.ModeTx) + "";
        }
         
        if (hL7Def.IncomingFolder != oldHL7Def.IncomingFolder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IncomingFolder = '" + POut.String(hL7Def.IncomingFolder) + "'";
        }
         
        if (hL7Def.OutgoingFolder != oldHL7Def.OutgoingFolder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OutgoingFolder = '" + POut.String(hL7Def.OutgoingFolder) + "'";
        }
         
        if (hL7Def.IncomingPort != oldHL7Def.IncomingPort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IncomingPort = '" + POut.String(hL7Def.IncomingPort) + "'";
        }
         
        if (hL7Def.OutgoingIpPort != oldHL7Def.OutgoingIpPort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OutgoingIpPort = '" + POut.String(hL7Def.OutgoingIpPort) + "'";
        }
         
        if (hL7Def.FieldSeparator != oldHL7Def.FieldSeparator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldSeparator = '" + POut.String(hL7Def.FieldSeparator) + "'";
        }
         
        if (hL7Def.ComponentSeparator != oldHL7Def.ComponentSeparator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ComponentSeparator = '" + POut.String(hL7Def.ComponentSeparator) + "'";
        }
         
        if (hL7Def.SubcomponentSeparator != oldHL7Def.SubcomponentSeparator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SubcomponentSeparator = '" + POut.String(hL7Def.SubcomponentSeparator) + "'";
        }
         
        if (hL7Def.RepetitionSeparator != oldHL7Def.RepetitionSeparator)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RepetitionSeparator = '" + POut.String(hL7Def.RepetitionSeparator) + "'";
        }
         
        if (hL7Def.EscapeCharacter != oldHL7Def.EscapeCharacter)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EscapeCharacter = '" + POut.String(hL7Def.EscapeCharacter) + "'";
        }
         
        if (hL7Def.IsInternal != oldHL7Def.IsInternal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsInternal = " + POut.Bool(hL7Def.IsInternal) + "";
        }
         
        if (hL7Def.InternalType != oldHL7Def.InternalType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InternalType = '" + POut.String(hL7Def.InternalType) + "'";
        }
         
        if (hL7Def.InternalTypeVersion != oldHL7Def.InternalTypeVersion)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InternalTypeVersion = '" + POut.String(hL7Def.InternalTypeVersion) + "'";
        }
         
        if (hL7Def.IsEnabled != oldHL7Def.IsEnabled)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsEnabled = " + POut.Bool(hL7Def.IsEnabled) + "";
        }
         
        if (hL7Def.Note != oldHL7Def.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = " + DbHelper.ParamChar + "paramNote";
        }
         
        if (hL7Def.HL7Server != oldHL7Def.HL7Server)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HL7Server = '" + POut.String(hL7Def.HL7Server) + "'";
        }
         
        if (hL7Def.HL7ServiceName != oldHL7Def.HL7ServiceName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HL7ServiceName = '" + POut.String(hL7Def.HL7ServiceName) + "'";
        }
         
        if (hL7Def.ShowDemographics != oldHL7Def.ShowDemographics)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowDemographics = " + POut.Int((int)hL7Def.ShowDemographics) + "";
        }
         
        if (hL7Def.ShowAppts != oldHL7Def.ShowAppts)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowAppts = " + POut.Bool(hL7Def.ShowAppts) + "";
        }
         
        if (hL7Def.ShowAccount != oldHL7Def.ShowAccount)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowAccount = " + POut.Bool(hL7Def.ShowAccount) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (hL7Def.Note == null)
        {
            hL7Def.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7Def.Note);
        command = "UPDATE hl7def SET " + command + " WHERE HL7DefNum = " + POut.Long(hL7Def.HL7DefNum);
        Db.NonQ(command, paramNote);
    }

    /**
    * Deletes one HL7Def from the database.
    */
    public static void delete(long hL7DefNum) throws Exception {
        String command = "DELETE FROM hl7def " + "WHERE HL7DefNum = " + POut.Long(hL7DefNum);
        Db.NonQ(command);
    }

}


