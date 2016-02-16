//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CDSPermission;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CDSPermissionCrud   
{
    /**
    * Gets one CDSPermission object from the database using the primary key.  Returns null if not found.
    */
    public static CDSPermission selectOne(long cDSPermissionNum) throws Exception {
        String command = "SELECT * FROM cdspermission " + "WHERE CDSPermissionNum = " + POut.long(cDSPermissionNum);
        List<CDSPermission> list = tableToList(Db.getTable(command));
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
         
        List<CDSPermission> list = tableToList(Db.getTable(command));
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
         
        List<CDSPermission> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CDSPermission> tableToList(DataTable table) throws Exception {
        List<CDSPermission> retVal = new List<CDSPermission>();
        CDSPermission cDSPermission;
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
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            cDSPermission.CDSPermissionNum = DbHelper.getNextOracleKey("cdspermission","CDSPermissionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(cDSPermission, true);
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
            return Insert(cDSPermission, false);
        } 
    }

    /**
    * Inserts one CDSPermission into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CDSPermission cDSPermission, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            cDSPermission.CDSPermissionNum = ReplicationServers.getKey("cdspermission","CDSPermissionNum");
        }
         
        String command = "INSERT INTO cdspermission (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "CDSPermissionNum,";
        }
         
        command += "UserNum,SetupCDS,ShowCDS,ShowInfobutton,EditBibliography,ProblemCDS,MedicationCDS,AllergyCDS,DemographicCDS,LabTestCDS,VitalCDS) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(cDSPermission.CDSPermissionNum) + ",";
        }
         
        command += POut.long(cDSPermission.UserNum) + "," + POut.bool(cDSPermission.SetupCDS) + "," + POut.bool(cDSPermission.ShowCDS) + "," + POut.bool(cDSPermission.ShowInfobutton) + "," + POut.bool(cDSPermission.EditBibliography) + "," + POut.bool(cDSPermission.ProblemCDS) + "," + POut.bool(cDSPermission.MedicationCDS) + "," + POut.bool(cDSPermission.AllergyCDS) + "," + POut.bool(cDSPermission.DemographicCDS) + "," + POut.bool(cDSPermission.LabTestCDS) + "," + POut.bool(cDSPermission.VitalCDS) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            cDSPermission.CDSPermissionNum = Db.nonQ(command,true);
        } 
        return cDSPermission.CDSPermissionNum;
    }

    /**
    * Updates one CDSPermission in the database.
    */
    public static void update(CDSPermission cDSPermission) throws Exception {
        String command = "UPDATE cdspermission SET " + "UserNum         =  " + POut.long(cDSPermission.UserNum) + ", " + "SetupCDS        =  " + POut.bool(cDSPermission.SetupCDS) + ", " + "ShowCDS         =  " + POut.bool(cDSPermission.ShowCDS) + ", " + "ShowInfobutton  =  " + POut.bool(cDSPermission.ShowInfobutton) + ", " + "EditBibliography=  " + POut.bool(cDSPermission.EditBibliography) + ", " + "ProblemCDS      =  " + POut.bool(cDSPermission.ProblemCDS) + ", " + "MedicationCDS   =  " + POut.bool(cDSPermission.MedicationCDS) + ", " + "AllergyCDS      =  " + POut.bool(cDSPermission.AllergyCDS) + ", " + "DemographicCDS  =  " + POut.bool(cDSPermission.DemographicCDS) + ", " + "LabTestCDS      =  " + POut.bool(cDSPermission.LabTestCDS) + ", " + "VitalCDS        =  " + POut.bool(cDSPermission.VitalCDS) + " " + "WHERE CDSPermissionNum = " + POut.long(cDSPermission.CDSPermissionNum);
        Db.nonQ(command);
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
             
            command += "UserNum = " + POut.long(cDSPermission.UserNum) + "";
        }
         
        if (cDSPermission.SetupCDS != oldCDSPermission.SetupCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SetupCDS = " + POut.bool(cDSPermission.SetupCDS) + "";
        }
         
        if (cDSPermission.ShowCDS != oldCDSPermission.ShowCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowCDS = " + POut.bool(cDSPermission.ShowCDS) + "";
        }
         
        if (cDSPermission.ShowInfobutton != oldCDSPermission.ShowInfobutton)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ShowInfobutton = " + POut.bool(cDSPermission.ShowInfobutton) + "";
        }
         
        if (cDSPermission.EditBibliography != oldCDSPermission.EditBibliography)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EditBibliography = " + POut.bool(cDSPermission.EditBibliography) + "";
        }
         
        if (cDSPermission.ProblemCDS != oldCDSPermission.ProblemCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProblemCDS = " + POut.bool(cDSPermission.ProblemCDS) + "";
        }
         
        if (cDSPermission.MedicationCDS != oldCDSPermission.MedicationCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MedicationCDS = " + POut.bool(cDSPermission.MedicationCDS) + "";
        }
         
        if (cDSPermission.AllergyCDS != oldCDSPermission.AllergyCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AllergyCDS = " + POut.bool(cDSPermission.AllergyCDS) + "";
        }
         
        if (cDSPermission.DemographicCDS != oldCDSPermission.DemographicCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DemographicCDS = " + POut.bool(cDSPermission.DemographicCDS) + "";
        }
         
        if (cDSPermission.LabTestCDS != oldCDSPermission.LabTestCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LabTestCDS = " + POut.bool(cDSPermission.LabTestCDS) + "";
        }
         
        if (cDSPermission.VitalCDS != oldCDSPermission.VitalCDS)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "VitalCDS = " + POut.bool(cDSPermission.VitalCDS) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE cdspermission SET " + command + " WHERE CDSPermissionNum = " + POut.long(cDSPermission.CDSPermissionNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one CDSPermission from the database.
    */
    public static void delete(long cDSPermissionNum) throws Exception {
        String command = "DELETE FROM cdspermission " + "WHERE CDSPermissionNum = " + POut.long(cDSPermissionNum);
        Db.nonQ(command);
    }

}


