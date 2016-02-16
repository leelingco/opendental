//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.Mobile.LabPanelm;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class LabPanelmCrud   
{
    /**
    * Gets one LabPanelm object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static LabPanelm selectOne(long customerNum, long labPanelNum) throws Exception {
        String command = "SELECT * FROM labpanelm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND LabPanelNum = " + POut.long(labPanelNum);
        List<LabPanelm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one LabPanelm object from the database using a query.
    */
    public static LabPanelm selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LabPanelm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of LabPanelm objects from the database using a query.
    */
    public static List<LabPanelm> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LabPanelm> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<LabPanelm> tableToList(DataTable table) throws Exception {
        List<LabPanelm> retVal = new List<LabPanelm>();
        LabPanelm labPanelm;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            labPanelm = new LabPanelm();
            labPanelm.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            labPanelm.LabPanelNum = PIn.Long(table.Rows[i]["LabPanelNum"].ToString());
            labPanelm.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            labPanelm.LabNameAddress = PIn.String(table.Rows[i]["LabNameAddress"].ToString());
            labPanelm.SpecimenCondition = PIn.String(table.Rows[i]["SpecimenCondition"].ToString());
            labPanelm.SpecimenSource = PIn.String(table.Rows[i]["SpecimenSource"].ToString());
            labPanelm.ServiceId = PIn.String(table.Rows[i]["ServiceId"].ToString());
            labPanelm.ServiceName = PIn.String(table.Rows[i]["ServiceName"].ToString());
            labPanelm.MedicalOrderNum = PIn.Long(table.Rows[i]["MedicalOrderNum"].ToString());
            retVal.Add(labPanelm);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one LabPanelm into the database.
    */
    public static long insert(LabPanelm labPanelm, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            labPanelm.LabPanelNum = ReplicationServers.getKey("labpanelm","LabPanelNum");
        }
         
        String command = "INSERT INTO labpanelm (";
        command += "LabPanelNum,";
        command += "CustomerNum,PatNum,LabNameAddress,SpecimenCondition,SpecimenSource,ServiceId,ServiceName,MedicalOrderNum) VALUES(";
        command += POut.long(labPanelm.LabPanelNum) + ",";
        command += POut.long(labPanelm.CustomerNum) + "," + POut.long(labPanelm.PatNum) + "," + "'" + POut.string(labPanelm.LabNameAddress) + "'," + "'" + POut.string(labPanelm.SpecimenCondition) + "'," + "'" + POut.string(labPanelm.SpecimenSource) + "'," + "'" + POut.string(labPanelm.ServiceId) + "'," + "'" + POut.string(labPanelm.ServiceName) + "'," + POut.long(labPanelm.MedicalOrderNum) + ")";
        Db.nonQ(command);
        return labPanelm.LabPanelNum;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one LabPanelm in the database.
    */
    public static void update(LabPanelm labPanelm) throws Exception {
        String command = "UPDATE labpanelm SET " + "PatNum           =  " + POut.long(labPanelm.PatNum) + ", " + "LabNameAddress   = '" + POut.string(labPanelm.LabNameAddress) + "', " + "SpecimenCondition= '" + POut.string(labPanelm.SpecimenCondition) + "', " + "SpecimenSource   = '" + POut.string(labPanelm.SpecimenSource) + "', " + "ServiceId        = '" + POut.string(labPanelm.ServiceId) + "', " + "ServiceName      = '" + POut.string(labPanelm.ServiceName) + "', " + "MedicalOrderNum  =  " + POut.long(labPanelm.MedicalOrderNum) + " " + "WHERE CustomerNum = " + POut.long(labPanelm.CustomerNum) + " AND LabPanelNum = " + POut.long(labPanelm.LabPanelNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one LabPanelm from the database.
    */
    public static void delete(long customerNum, long labPanelNum) throws Exception {
        String command = "DELETE FROM labpanelm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND LabPanelNum = " + POut.long(labPanelNum);
        Db.nonQ(command);
    }

    /**
    * Converts one LabPanel object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static LabPanelm convertToM(LabPanel labPanel) throws Exception {
        LabPanelm labPanelm = new LabPanelm();
        //CustomerNum cannot be set.  Remains 0.
        labPanelm.LabPanelNum = labPanel.LabPanelNum;
        labPanelm.PatNum = labPanel.PatNum;
        labPanelm.LabNameAddress = labPanel.LabNameAddress;
        labPanelm.SpecimenCondition = labPanel.SpecimenCondition;
        labPanelm.SpecimenSource = labPanel.SpecimenSource;
        labPanelm.ServiceId = labPanel.ServiceId;
        labPanelm.ServiceName = labPanel.ServiceName;
        labPanelm.MedicalOrderNum = labPanel.MedicalOrderNum;
        return labPanelm;
    }

}


