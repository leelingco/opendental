//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:56 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CDSPermissionCrud   
{
    /**
    * Gets one CDSPermission object from the database using the primary key.  Returns null if not found.
    */
    public static CDSPermission selectOne(long cDSPermissionNum) throws Exception {
        String command = "SELECT * FROM cdspermission " + "WHERE CDSPermissionNum = " + POut.Long(cDSPermissionNum);
        List<CDSPermission> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one CDSPermission object from the database using a query.
    */
    public static CDSPermission selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CDSPermission> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of CDSPermission objects from the database using a query.
    */
    public static List<CDSPermission> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CDSPermission> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CDSPermission> tableToList(DataTable table) throws Exception {
        List<CDSPermission> retVal = new List<CDSPermission>();
        CDSPermission cDSPermission = new CDSPermission();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            cDSPermission = new CDSPermission();
            cDSPermission.CDSPermissionNum = PIn.Long(table.Rows[i]["CDSPermissionNum"].ToString());
            cDSPermission.UserNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            cDSPermission.SetupCDS = PIn.Bool(table.Rows[i]["SetupCDS"].ToString());
            cDSPermission.ShowCDS = PIn.Bool(table.Rows[i]["ShowCDS"].ToString());
            cDSPermission.ShowInfobutton = PIn.Bool(table.Rows[i]["ShowInfobutton"].ToString());
            cDSPermission.EditBibliography = PIn.Bool(table.Rows[i]["EditBibliography"].ToString());
            cDSPermission.ProblemCDS = PIn.Bool(table.Rows[i]["ProblemCDS"].ToString());
            cDSPermission.MedicationCDS = PIn.Bool(table.Rows[i]["MedicationCDS"].ToString());
            cDSPermission.AllergyCDS = PIn.Bool(table.Rows[i]["AllergyCDS"].ToString());
            cDSPermission.DemographicCDS = PIn.Bool(table.Rows[i]["DemographicCDS"].ToString());
            cDSPermission.LabTestCDS = PIn.Bool(table.Rows[i]["LabTestCDS"].ToString());
            cDSPermission.VitalCDS = PIn.Bool(table.Rows[i]["VitalCDS"].ToString());
            retVal.Add(cDSPermission);
        }
        return retVal;
    }

    /**
    * Inserts one CDSPermission into the database.  Returns the new priKey.
    */
    public static long insert(CDSPermission cDSPermission) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            cDSPermission.CDSPermissionNum = DbHelper.GetNextOracleKey("cdspermission", "CDSPermissionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(cDSPermission,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        cDSPermission.CDSPermissionNum++;
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
            return insert(cDSPermission,false);
        } 
    }

    /**
    * Inserts one CDSPermission into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CDSPermission cDSPermission, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            cDSPermission.CDSPermissionNum = ReplicationServers.GetKey("cdspermission", "CDSPermissionNum");
        }
         
        String command = "INSERT INTO cdspermission (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "CDSPermissionNum,";
        }
         
        command += "UserNum,SetupCDS,ShowCDS,ShowInfobutton,EditBibliography,ProblemCDS,MedicationCDS,AllergyCDS,DemographicCDS,LabTestCDS,VitalCDS) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(cDSPermission.CDSPermissionNum) + ",";
        }
         
        command += POut.Long(cDSPermission.UserNum) + "," + POut.Bool(cDSPermission.SetupCDS) + "," + POut.Bool(cDSPermission.ShowCDS) + "," + POut.Bool(cDSPermission.ShowInfobutton) + "," + POut.Bool(cDSPermission.EditBibliography) + "," + POut.Bool(cDSPermission.ProblemCDS) + "," + POut.Bool(cDSPermission.MedicationCDS) + "," + POut.Bool(cDSPermission.AllergyCDS) + "," + POut.Bool(cDSPermission.DemographicCDS) + "," + POut.Bool(cDSPermission.LabTestCDS) + "," + POut.Bool(cDSPermission.VitalCDS) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            cDSPermission.CDSPermissionNum = Db.NonQ(command, true);
        } 
        return cDSPermission.CDSPermissionNum;
    }

    /**
    * Updates one CDSPermission in the database.
    */
    public static void update(CDSPermission cDSPermission) throws Exception {
        String command = "UPDATE cdspermission SET " + "UserNum         =  " + POut.Long(cDSPermission.UserNum) + ", " + "SetupCDS        =  " + POut.Bool(cDSPermission.SetupCDS) + ", " + "ShowCDS         =  " + POut.Bool(cDSPermission.ShowCDS) + ", " + "ShowInfobutton  =  " + POut.Bool(cDSPermission.ShowInfobutton) + ", " + "EditBibliography=  " + POut.Bool(cDSPermission.EditBibliography) + ", " + "ProblemCDS      =  " + POut.Bool(cDSPermission.ProblemCDS) + ", " + "MedicationCDS   =  " + POut.Bool(cDSPermission.MedicationCDS) + ", " + "AllergyCDS      =  " + POut.Bool(cDSPermission.AllergyCDS) + ", " + "DemographicCDS  =  " + POut.Bool(cDSPermission.DemographicCDS) + ", " + "LabTestCDS      =  " + POut.Bool(cDSPermission.LabTestCDS) + ", " + "VitalCDS        =  " + POut.Bool(cDSPermission.VitalCDS) + " " + "WHERE CDSPermissionNum = " + POut.Long(cDSPermission.CDSPermissionNum);
        Db.NonQ(command);
    }

    /**
    * Updates one CDSPermission in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(CDSPermission cDSPermission, CDSPermission oldCDSPermission) throws Exception {
        String command = "";
        if (cDSPermission.UserNum != oldCDSPermission.UserNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserNum = " + POut.Long(cDSPermission.UserNum) + "";
        }
         
        if (cDSPermission.SetupCDS != oldCDSPermission.SetupCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SetupCDS = " + POut.Bool(cDSPermission.SetupCDS) + "";
        }
         
        if (cDSPermission.ShowCDS != oldCDSPermission.ShowCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowCDS = " + POut.Bool(cDSPermission.ShowCDS) + "";
        }
         
        if (cDSPermission.ShowInfobutton != oldCDSPermission.ShowInfobutton)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowInfobutton = " + POut.Bool(cDSPermission.ShowInfobutton) + "";
        }
         
        if (cDSPermission.EditBibliography != oldCDSPermission.EditBibliography)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EditBibliography = " + POut.Bool(cDSPermission.EditBibliography) + "";
        }
         
        if (cDSPermission.ProblemCDS != oldCDSPermission.ProblemCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemCDS = " + POut.Bool(cDSPermission.ProblemCDS) + "";
        }
         
        if (cDSPermission.MedicationCDS != oldCDSPermission.MedicationCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedicationCDS = " + POut.Bool(cDSPermission.MedicationCDS) + "";
        }
         
        if (cDSPermission.AllergyCDS != oldCDSPermission.AllergyCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AllergyCDS = " + POut.Bool(cDSPermission.AllergyCDS) + "";
        }
         
        if (cDSPermission.DemographicCDS != oldCDSPermission.DemographicCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DemographicCDS = " + POut.Bool(cDSPermission.DemographicCDS) + "";
        }
         
        if (cDSPermission.LabTestCDS != oldCDSPermission.LabTestCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LabTestCDS = " + POut.Bool(cDSPermission.LabTestCDS) + "";
        }
         
        if (cDSPermission.VitalCDS != oldCDSPermission.VitalCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "VitalCDS = " + POut.Bool(cDSPermission.VitalCDS) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE cdspermission SET " + command + " WHERE CDSPermissionNum = " + POut.Long(cDSPermission.CDSPermissionNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one CDSPermission from the database.
    */
    public static void delete(long cDSPermissionNum) throws Exception {
        String command = "DELETE FROM cdspermission " + "WHERE CDSPermissionNum = " + POut.Long(cDSPermissionNum);
        Db.NonQ(command);
    }

}


