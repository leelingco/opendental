//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ProviderCrud   
{
    /**
    * Gets one Provider object from the database using the primary key.  Returns null if not found.
    */
    public static Provider selectOne(long provNum) throws Exception {
        String command = "SELECT * FROM provider " + "WHERE ProvNum = " + POut.Long(provNum);
        List<Provider> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Provider object from the database using a query.
    */
    public static Provider selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Provider> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Provider objects from the database using a query.
    */
    public static List<Provider> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Provider> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Provider> tableToList(DataTable table) throws Exception {
        List<Provider> retVal = new List<Provider>();
        Provider provider = new Provider();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            provider = new Provider();
            provider.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            provider.Abbr = PIn.String(table.Rows[i]["Abbr"].ToString());
            provider.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            provider.LName = PIn.String(table.Rows[i]["LName"].ToString());
            provider.FName = PIn.String(table.Rows[i]["FName"].ToString());
            provider.MI = PIn.String(table.Rows[i]["MI"].ToString());
            provider.Suffix = PIn.String(table.Rows[i]["Suffix"].ToString());
            provider.FeeSched = PIn.Long(table.Rows[i]["FeeSched"].ToString());
            provider.Specialty = (DentalSpecialty)PIn.Int(table.Rows[i]["Specialty"].ToString());
            provider.SSN = PIn.String(table.Rows[i]["SSN"].ToString());
            provider.StateLicense = PIn.String(table.Rows[i]["StateLicense"].ToString());
            provider.DEANum = PIn.String(table.Rows[i]["DEANum"].ToString());
            provider.IsSecondary = PIn.Bool(table.Rows[i]["IsSecondary"].ToString());
            provider.ProvColor = Color.FromArgb(PIn.Int(table.Rows[i]["ProvColor"].ToString()));
            provider.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            provider.UsingTIN = PIn.Bool(table.Rows[i]["UsingTIN"].ToString());
            provider.BlueCrossID = PIn.String(table.Rows[i]["BlueCrossID"].ToString());
            provider.SigOnFile = PIn.Bool(table.Rows[i]["SigOnFile"].ToString());
            provider.MedicaidID = PIn.String(table.Rows[i]["MedicaidID"].ToString());
            provider.OutlineColor = Color.FromArgb(PIn.Int(table.Rows[i]["OutlineColor"].ToString()));
            provider.SchoolClassNum = PIn.Long(table.Rows[i]["SchoolClassNum"].ToString());
            provider.NationalProvID = PIn.String(table.Rows[i]["NationalProvID"].ToString());
            provider.CanadianOfficeNum = PIn.String(table.Rows[i]["CanadianOfficeNum"].ToString());
            provider.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            provider.AnesthProvType = PIn.Long(table.Rows[i]["AnesthProvType"].ToString());
            provider.TaxonomyCodeOverride = PIn.String(table.Rows[i]["TaxonomyCodeOverride"].ToString());
            provider.IsCDAnet = PIn.Bool(table.Rows[i]["IsCDAnet"].ToString());
            provider.EcwID = PIn.String(table.Rows[i]["EcwID"].ToString());
            provider.EhrKey = PIn.String(table.Rows[i]["EhrKey"].ToString());
            provider.StateRxID = PIn.String(table.Rows[i]["StateRxID"].ToString());
            provider.EhrHasReportAccess = PIn.Bool(table.Rows[i]["EhrHasReportAccess"].ToString());
            provider.IsNotPerson = PIn.Bool(table.Rows[i]["IsNotPerson"].ToString());
            provider.StateWhereLicensed = PIn.String(table.Rows[i]["StateWhereLicensed"].ToString());
            provider.EmailAddressNum = PIn.Long(table.Rows[i]["EmailAddressNum"].ToString());
            retVal.Add(provider);
        }
        return retVal;
    }

    /**
    * Inserts one Provider into the database.  Returns the new priKey.
    */
    public static long insert(Provider provider) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            provider.ProvNum = DbHelper.GetNextOracleKey("provider", "ProvNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(provider,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        provider.ProvNum++;
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
            return insert(provider,false);
        } 
    }

    /**
    * Inserts one Provider into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Provider provider, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            provider.ProvNum = ReplicationServers.GetKey("provider", "ProvNum");
        }
         
        String command = "INSERT INTO provider (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ProvNum,";
        }
         
        command += "Abbr,ItemOrder,LName,FName,MI,Suffix,FeeSched,Specialty,SSN,StateLicense,DEANum,IsSecondary,ProvColor,IsHidden,UsingTIN,BlueCrossID,SigOnFile,MedicaidID,OutlineColor,SchoolClassNum,NationalProvID,CanadianOfficeNum,AnesthProvType,TaxonomyCodeOverride,IsCDAnet,EcwID,EhrKey,StateRxID,EhrHasReportAccess,IsNotPerson,StateWhereLicensed,EmailAddressNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(provider.ProvNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += "'" + POut.String(provider.Abbr) + "'," + POut.Int(provider.ItemOrder) + "," + "'" + POut.String(provider.LName) + "'," + "'" + POut.String(provider.FName) + "'," + "'" + POut.String(provider.MI) + "'," + "'" + POut.String(provider.Suffix) + "'," + POut.Long(provider.FeeSched) + "," + POut.Int((int)provider.Specialty) + "," + "'" + POut.String(provider.SSN) + "'," + "'" + POut.String(provider.StateLicense) + "'," + "'" + POut.String(provider.DEANum) + "'," + POut.Bool(provider.IsSecondary) + "," + POut.Int(provider.ProvColor.ToArgb()) + "," + POut.Bool(provider.IsHidden) + "," + POut.Bool(provider.UsingTIN) + "," + "'" + POut.String(provider.BlueCrossID) + "'," + POut.Bool(provider.SigOnFile) + "," + "'" + POut.String(provider.MedicaidID) + "'," + POut.Int(provider.OutlineColor.ToArgb()) + "," + POut.Long(provider.SchoolClassNum) + "," + "'" + POut.String(provider.NationalProvID) + "'," + "'" + POut.String(provider.CanadianOfficeNum) + "'," + POut.Long(provider.AnesthProvType) + "," + "'" + POut.String(provider.TaxonomyCodeOverride) + "'," + POut.Bool(provider.IsCDAnet) + "," + "'" + POut.String(provider.EcwID) + "'," + "'" + POut.String(provider.EhrKey) + "'," + "'" + POut.String(provider.StateRxID) + "'," + POut.Bool(provider.EhrHasReportAccess) + "," + POut.Bool(provider.IsNotPerson) + "," + "'" + POut.String(provider.StateWhereLicensed) + "'," + POut.Long(provider.EmailAddressNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            provider.ProvNum = Db.NonQ(command, true);
        } 
        return provider.ProvNum;
    }

    /**
    * Updates one Provider in the database.
    */
    public static void update(Provider provider) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE provider SET " + "Abbr                = '" + POut.String(provider.Abbr) + "', " + "ItemOrder           =  " + POut.Int(provider.ItemOrder) + ", " + "LName               = '" + POut.String(provider.LName) + "', " + "FName               = '" + POut.String(provider.FName) + "', " + "MI                  = '" + POut.String(provider.MI) + "', " + "Suffix              = '" + POut.String(provider.Suffix) + "', " + "FeeSched            =  " + POut.Long(provider.FeeSched) + ", " + "Specialty           =  " + POut.Int((int)provider.Specialty) + ", " + "SSN                 = '" + POut.String(provider.SSN) + "', " + "StateLicense        = '" + POut.String(provider.StateLicense) + "', " + "DEANum              = '" + POut.String(provider.DEANum) + "', " + "IsSecondary         =  " + POut.Bool(provider.IsSecondary) + ", " + "ProvColor           =  " + POut.Int(provider.ProvColor.ToArgb()) + ", " + "IsHidden            =  " + POut.Bool(provider.IsHidden) + ", " + "UsingTIN            =  " + POut.Bool(provider.UsingTIN) + ", " + "BlueCrossID         = '" + POut.String(provider.BlueCrossID) + "', " + "SigOnFile           =  " + POut.Bool(provider.SigOnFile) + ", " + "MedicaidID          = '" + POut.String(provider.MedicaidID) + "', " + "OutlineColor        =  " + POut.Int(provider.OutlineColor.ToArgb()) + ", " + "SchoolClassNum      =  " + POut.Long(provider.SchoolClassNum) + ", " + "NationalProvID      = '" + POut.String(provider.NationalProvID) + "', " + "CanadianOfficeNum   = '" + POut.String(provider.CanadianOfficeNum) + "', " + "AnesthProvType      =  " + POut.Long(provider.AnesthProvType) + ", " + "TaxonomyCodeOverride= '" + POut.String(provider.TaxonomyCodeOverride) + "', " + "IsCDAnet            =  " + POut.Bool(provider.IsCDAnet) + ", " + "EcwID               = '" + POut.String(provider.EcwID) + "', " + "EhrKey              = '" + POut.String(provider.EhrKey) + "', " + "StateRxID           = '" + POut.String(provider.StateRxID) + "', " + "EhrHasReportAccess  =  " + POut.Bool(provider.EhrHasReportAccess) + ", " + "IsNotPerson         =  " + POut.Bool(provider.IsNotPerson) + ", " + "StateWhereLicensed  = '" + POut.String(provider.StateWhereLicensed) + "', " + "EmailAddressNum     =  " + POut.Long(provider.EmailAddressNum) + " " + "WHERE ProvNum = " + POut.Long(provider.ProvNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Provider in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Provider provider, Provider oldProvider) throws Exception {
        String command = "";
        if (provider.Abbr != oldProvider.Abbr)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Abbr = '" + POut.String(provider.Abbr) + "'";
        }
         
        if (provider.ItemOrder != oldProvider.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(provider.ItemOrder) + "";
        }
         
        if (provider.LName != oldProvider.LName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LName = '" + POut.String(provider.LName) + "'";
        }
         
        if (provider.FName != oldProvider.FName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FName = '" + POut.String(provider.FName) + "'";
        }
         
        if (provider.MI != oldProvider.MI)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MI = '" + POut.String(provider.MI) + "'";
        }
         
        if (provider.Suffix != oldProvider.Suffix)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Suffix = '" + POut.String(provider.Suffix) + "'";
        }
         
        if (provider.FeeSched != oldProvider.FeeSched)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FeeSched = " + POut.Long(provider.FeeSched) + "";
        }
         
        if (provider.Specialty != oldProvider.Specialty)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Specialty = " + POut.Int((int)provider.Specialty) + "";
        }
         
        if (provider.SSN != oldProvider.SSN)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SSN = '" + POut.String(provider.SSN) + "'";
        }
         
        if (provider.StateLicense != oldProvider.StateLicense)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "StateLicense = '" + POut.String(provider.StateLicense) + "'";
        }
         
        if (provider.DEANum != oldProvider.DEANum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DEANum = '" + POut.String(provider.DEANum) + "'";
        }
         
        if (provider.IsSecondary != oldProvider.IsSecondary)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsSecondary = " + POut.Bool(provider.IsSecondary) + "";
        }
         
        if (provider.ProvColor != oldProvider.ProvColor)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvColor = " + POut.Int(provider.ProvColor.ToArgb()) + "";
        }
         
        if (provider.IsHidden != oldProvider.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.Bool(provider.IsHidden) + "";
        }
         
        if (provider.UsingTIN != oldProvider.UsingTIN)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UsingTIN = " + POut.Bool(provider.UsingTIN) + "";
        }
         
        if (provider.BlueCrossID != oldProvider.BlueCrossID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BlueCrossID = '" + POut.String(provider.BlueCrossID) + "'";
        }
         
        if (provider.SigOnFile != oldProvider.SigOnFile)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SigOnFile = " + POut.Bool(provider.SigOnFile) + "";
        }
         
        if (provider.MedicaidID != oldProvider.MedicaidID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedicaidID = '" + POut.String(provider.MedicaidID) + "'";
        }
         
        if (provider.OutlineColor != oldProvider.OutlineColor)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OutlineColor = " + POut.Int(provider.OutlineColor.ToArgb()) + "";
        }
         
        if (provider.SchoolClassNum != oldProvider.SchoolClassNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SchoolClassNum = " + POut.Long(provider.SchoolClassNum) + "";
        }
         
        if (provider.NationalProvID != oldProvider.NationalProvID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "NationalProvID = '" + POut.String(provider.NationalProvID) + "'";
        }
         
        if (provider.CanadianOfficeNum != oldProvider.CanadianOfficeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CanadianOfficeNum = '" + POut.String(provider.CanadianOfficeNum) + "'";
        }
         
        //DateTStamp can only be set by MySQL
        if (provider.AnesthProvType != oldProvider.AnesthProvType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AnesthProvType = " + POut.Long(provider.AnesthProvType) + "";
        }
         
        if (provider.TaxonomyCodeOverride != oldProvider.TaxonomyCodeOverride)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaxonomyCodeOverride = '" + POut.String(provider.TaxonomyCodeOverride) + "'";
        }
         
        if (provider.IsCDAnet != oldProvider.IsCDAnet)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsCDAnet = " + POut.Bool(provider.IsCDAnet) + "";
        }
         
        if (provider.EcwID != oldProvider.EcwID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EcwID = '" + POut.String(provider.EcwID) + "'";
        }
         
        if (provider.EhrKey != oldProvider.EhrKey)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EhrKey = '" + POut.String(provider.EhrKey) + "'";
        }
         
        if (provider.StateRxID != oldProvider.StateRxID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "StateRxID = '" + POut.String(provider.StateRxID) + "'";
        }
         
        if (provider.EhrHasReportAccess != oldProvider.EhrHasReportAccess)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EhrHasReportAccess = " + POut.Bool(provider.EhrHasReportAccess) + "";
        }
         
        if (provider.IsNotPerson != oldProvider.IsNotPerson)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsNotPerson = " + POut.Bool(provider.IsNotPerson) + "";
        }
         
        if (provider.StateWhereLicensed != oldProvider.StateWhereLicensed)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "StateWhereLicensed = '" + POut.String(provider.StateWhereLicensed) + "'";
        }
         
        if (provider.EmailAddressNum != oldProvider.EmailAddressNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailAddressNum = " + POut.Long(provider.EmailAddressNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE provider SET " + command + " WHERE ProvNum = " + POut.Long(provider.ProvNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Provider from the database.
    */
    public static void delete(long provNum) throws Exception {
        String command = "DELETE FROM provider " + "WHERE ProvNum = " + POut.Long(provNum);
        Db.NonQ(command);
    }

}


