//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class LabPanels   
{
    /**
    * 
    */
    public static List<LabPanel> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabPanel>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM labpanel WHERE PatNum=" + POut.long(patNum);
        return Crud.LabPanelCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<LabPanel> getPanelsForOrder(long medicalOrderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabPanel>>GetObject(MethodBase.GetCurrentMethod(), medicalOrderNum);
        }
         
        if (medicalOrderNum == 0)
        {
            return new List<LabPanel>();
        }
         
        String command = "SELECT * FROM labpanel WHERE MedicalOrderNum=" + POut.long(medicalOrderNum);
        return Crud.LabPanelCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void delete(long labPanelNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labPanelNum);
            return ;
        }
         
        String command = "DELETE FROM labpanel WHERE LabPanelNum = " + POut.long(labPanelNum);
        Db.nonQ(command);
    }

    public static List<long> getChangedSinceLabPanelNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince, eligibleForUploadPatNumList);
        }
         
        String strEligibleForUploadPatNums = "";
        DataTable table = new DataTable();
        if (eligibleForUploadPatNumList.Count > 0)
        {
            for (int i = 0;i < eligibleForUploadPatNumList.Count;i++)
            {
                if (i > 0)
                {
                    strEligibleForUploadPatNums += "OR ";
                }
                 
                strEligibleForUploadPatNums += "PatNum='" + eligibleForUploadPatNumList[i].ToString() + "' ";
            }
            String command = "SELECT LabPanelNum FROM labpanel WHERE DateTStamp > " + POut.dateT(changedSince) + " AND (" + strEligibleForUploadPatNums + ")";
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        List<long> labPanelnums = new List<long>(table.Rows.Count);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            labPanelnums.Add(PIn.Long(table.Rows[i]["LabPanelNum"].ToString()));
        }
        return labPanelnums;
    }

    /**
    * Used along with GetChangedSinceLabPanelNums
    */
    public static List<LabPanel> getMultLabPanels(List<long> labpanelNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabPanel>>GetObject(MethodBase.GetCurrentMethod(), labpanelNums);
        }
         
        String strLabPanelNums = "";
        DataTable table = new DataTable();
        if (labpanelNums.Count > 0)
        {
            for (int i = 0;i < labpanelNums.Count;i++)
            {
                if (i > 0)
                {
                    strLabPanelNums += "OR ";
                }
                 
                strLabPanelNums += "LabPanelNum='" + labpanelNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM labpanel WHERE " + strLabPanelNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        LabPanel[] multLabPanels = Crud.LabPanelCrud.TableToList(table).ToArray();
        List<LabPanel> LabPanelList = new List<LabPanel>(multLabPanels);
        return LabPanelList;
    }

    /**
    * 
    */
    public static long insert(LabPanel labPanel) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            labPanel.LabPanelNum = Meth.GetLong(MethodBase.GetCurrentMethod(), labPanel);
            return labPanel.LabPanelNum;
        }
         
        return Crud.LabPanelCrud.Insert(labPanel);
    }

    /**
    * 
    */
    public static void update(LabPanel labPanel) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labPanel);
            return ;
        }
         
        Crud.LabPanelCrud.Update(labPanel);
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all labpanels of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE labpanel SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    /**
    * Gets one LabPanel from the db.
    */
    public static LabPanel getOne(long labPanelNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<LabPanel>GetObject(MethodBase.GetCurrentMethod(), labPanelNum);
        }
         
        return Crud.LabPanelCrud.SelectOne(labPanelNum);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		 
				///<summary></summary>
		public static List<LabPanel> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<LabPanel>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM labpanel WHERE PatNum = "+POut.Long(patNum);
			return Crud.LabPanelCrud.SelectMany(command);
		}
		
		///<summary></summary>
		public static void Delete(long labPanelNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),labPanelNum);
				return;
			}
			string command= "DELETE FROM labpanel WHERE LabPanelNum = "+POut.Long(labPanelNum);
			Db.NonQ(command);
		}
		*/