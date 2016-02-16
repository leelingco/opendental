//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabAbnormalFlag;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class LabResults   
{
    public static List<LabResult> getForPanel(long labPanelNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabResult>>GetObject(MethodBase.GetCurrentMethod(), labPanelNum);
        }
         
        String command = "SELECT * FROM labresult WHERE LabPanelNum = " + POut.long(labPanelNum);
        return Crud.LabResultCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void delete(long labResultNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labResultNum);
            return ;
        }
         
        String command = "DELETE FROM labresult WHERE LabResultNum = " + POut.long(labResultNum);
        Db.nonQ(command);
    }

    /**
    * Deletes all Lab Results associated with Lab Panel.
    */
    public static void deleteForPanel(long labPanelNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labPanelNum);
            return ;
        }
         
        String command = "DELETE FROM labresult WHERE LabPanelNum = " + POut.long(labPanelNum);
        Db.nonQ(command);
    }

    public static List<long> getChangedSinceLabResultNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT LabResultNum FROM labresult WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> labresultNums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            labresultNums.Add(PIn.Long(dt.Rows[i]["LabResultNum"].ToString()));
        }
        return labresultNums;
    }

    /**
    * Used along with GetChangedSinceLabResultNums
    */
    public static List<LabResult> getMultLabResults(List<long> labresultNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabResult>>GetObject(MethodBase.GetCurrentMethod(), labresultNums);
        }
         
        String strLabResultNums = "";
        DataTable table = new DataTable();
        if (labresultNums.Count > 0)
        {
            for (int i = 0;i < labresultNums.Count;i++)
            {
                if (i > 0)
                {
                    strLabResultNums += "OR ";
                }
                 
                strLabResultNums += "LabResultNum='" + labresultNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM labresult WHERE " + strLabResultNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        LabResult[] multLabResults = Crud.LabResultCrud.TableToList(table).ToArray();
        List<LabResult> LabResultList = new List<LabResult>(multLabResults);
        return LabResultList;
    }

    /**
    * Get all lab results for one patient.
    */
    public static List<LabResult> getAllForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabResult>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM labresult WHERE LabPanelNum IN (SELECT LabPanelNum FROM labpanel WHERE PatNum=" + POut.long(patNum) + ")";
        return Crud.LabResultCrud.SelectMany(command);
    }

    /**
    * Insert new lab results.
    */
    public static long insert(LabResult labResult) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            labResult.LabResultNum = Meth.GetLong(MethodBase.GetCurrentMethod(), labResult);
            return labResult.LabResultNum;
        }
         
        return Crud.LabResultCrud.Insert(labResult);
    }

    /**
    * Update existing lab results.
    */
    public static void update(LabResult labResult) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labResult);
            return ;
        }
         
        Crud.LabResultCrud.Update(labResult);
    }

    /*
    		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
    		///<summary></summary>
    		public static List<LabResult> Refresh(long patNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<LabResult>>(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command="SELECT * FROM labresult WHERE PatNum = "+POut.Long(patNum);
    			return Crud.LabResultCrud.SelectMany(command);
    		}
    		///<summary>Gets one LabResult from the db.</summary>
    		public static LabResult GetOne(long labResultNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetObject<LabResult>(MethodBase.GetCurrentMethod(),labResultNum);
    			}
    			return Crud.LabResultCrud.SelectOne(labResultNum);
    		}
    		
    		*/
    /**
    * Returns the text for a SnomedAllergy Enum as it should appear in human readable form for a CCD.
    */
    public static String getAbnormalFlagDesc(LabAbnormalFlag abnormalFlag) throws Exception {
        String result = new String();
        switch(abnormalFlag)
        {
            case Above: 
                result = "above high normal";
                break;
            case Normal: 
                result = "normal";
                break;
            case Below: 
                result = "below normal";
                break;
            case None: 
                result = "";
                break;
            default: 
                result = "Error";
                break;
        
        }
        return result;
    }

}


