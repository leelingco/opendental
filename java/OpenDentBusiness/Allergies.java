//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Allergy;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Allergies   
{
    /**
    * 
    */
    public static List<Allergy> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Allergy>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM allergy WHERE PatNum = " + POut.long(patNum);
        return Crud.AllergyCrud.SelectMany(command);
    }

    /**
    * Gets one Allergy from the db.
    */
    public static Allergy getOne(long allergyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Allergy>GetObject(MethodBase.GetCurrentMethod(), allergyNum);
        }
         
        return Crud.AllergyCrud.SelectOne(allergyNum);
    }

    /**
    * 
    */
    public static long insert(Allergy allergy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            allergy.AllergyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), allergy);
            return allergy.AllergyNum;
        }
         
        return Crud.AllergyCrud.Insert(allergy);
    }

    /**
    * 
    */
    public static void update(Allergy allergy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), allergy);
            return ;
        }
         
        Crud.AllergyCrud.Update(allergy);
    }

    /**
    * 
    */
    public static void delete(long allergyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), allergyNum);
            return ;
        }
         
        String command = "DELETE FROM allergy WHERE AllergyNum = " + POut.long(allergyNum);
        Db.nonQ(command);
    }

    /**
    * Gets all allergies for patient whether active or not.
    */
    public static List<Allergy> getAll(long patNum, boolean showInactive) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Allergy>>GetObject(MethodBase.GetCurrentMethod(), patNum, showInactive);
        }
         
        String command = "SELECT * FROM allergy WHERE PatNum = " + POut.long(patNum);
        if (!showInactive)
        {
            command += " AND StatusIsActive<>0";
        }
         
        return Crud.AllergyCrud.SelectMany(command);
    }

    public static List<long> getChangedSinceAllergyNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT AllergyNum FROM allergy WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> allergynums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            allergynums.Add(PIn.Long(dt.Rows[i]["AllergyNum"].ToString()));
        }
        return allergynums;
    }

    /**
    * Used along with GetChangedSinceAllergyNums
    */
    public static List<Allergy> getMultAllergies(List<long> allergyNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Allergy>>GetObject(MethodBase.GetCurrentMethod(), allergyNums);
        }
         
        String strAllergyNums = "";
        DataTable table = new DataTable();
        if (allergyNums.Count > 0)
        {
            for (int i = 0;i < allergyNums.Count;i++)
            {
                if (i > 0)
                {
                    strAllergyNums += "OR ";
                }
                 
                strAllergyNums += "AllergyNum='" + allergyNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM allergy WHERE " + strAllergyNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Allergy[] multAllergies = Crud.AllergyCrud.TableToList(table).ToArray();
        List<Allergy> allergyList = new List<Allergy>(multAllergies);
        return allergyList;
    }

    /**
    * Returns an array of all patient names who are using this allergy.
    */
    public static String[] getPatNamesForAllergy(long allergyDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), allergyDefNum);
        }
         
        String command = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(LName,', '),FName),' '),Preferred) FROM allergy,patient " + "WHERE allergy.PatNum=patient.PatNum " + "AND allergy.AllergyDefNum=" + POut.long(allergyDefNum);
        DataTable table = Db.getTable(command);
        String[] retVal = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all allergies of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE allergy SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all allergies of a patient that are the status specified
    */
    public static void resetTimeStamps(long patNum, boolean onlyActive) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, onlyActive);
            return ;
        }
         
        String command = "UPDATE allergy SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        if (onlyActive)
        {
            command += " AND StatusIsActive = " + POut.bool(onlyActive);
        }
         
        Db.nonQ(command);
    }

}


