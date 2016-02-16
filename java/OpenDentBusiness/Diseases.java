//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Disease;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Diseases   
{
    public static Disease getSpecificDiseaseForPatient(long patNum, long diseaseDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Disease>GetObject(MethodBase.GetCurrentMethod(), patNum, diseaseDefNum);
        }
         
        String command = "SELECT * FROM disease WHERE PatNum=" + POut.long(patNum) + " AND DiseaseDefNum=" + POut.long(diseaseDefNum);
        return Crud.DiseaseCrud.SelectOne(command);
    }

    /**
    * Gets one disease by DiseaseNum from the db.
    */
    public static Disease getOne(long diseaseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Disease>GetObject(MethodBase.GetCurrentMethod(), diseaseNum);
        }
         
        return Crud.DiseaseCrud.SelectOne(diseaseNum);
    }

    /**
    * Gets a list of all Diseases for a given patient.  Includes hidden. Sorted by diseasedef.ItemOrder.
    */
    public static List<Disease> refresh(long patNum) throws Exception {
        return refresh(patNum,false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Gets a list of all Diseases for a given patient. Set showActive true to only show active Diseases.
    */
    public static List<Disease> refresh(long patNum, boolean showActiveOnly) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Disease>>GetObject(MethodBase.GetCurrentMethod(), patNum, showActiveOnly);
        }
         
        String command = "SELECT disease.* FROM disease " + "WHERE PatNum=" + POut.long(patNum);
        if (showActiveOnly)
        {
            command += " AND ProbStatus=0";
        }
         
        return Crud.DiseaseCrud.SelectMany(command);
    }

    /**
    * Gets a list of all Diseases for a given patient. Show innactive returns all, otherwise only resolved and active problems.
    */
    public static List<Disease> refresh(boolean showInnactive, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Disease>>GetObject(MethodBase.GetCurrentMethod(), showInnactive, patNum);
        }
         
        String command = "SELECT disease.* FROM disease " + "WHERE PatNum=" + POut.long(patNum);
        if (!showInnactive)
        {
            command += " AND (ProbStatus=" + POut.int(((Enum)ProblemStatus.Active).ordinal()) + " OR ProbStatus=" + POut.int(((Enum)ProblemStatus.Resolved).ordinal()) + ")";
        }
         
        return Crud.DiseaseCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(Disease disease) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), disease);
            return ;
        }
         
        Crud.DiseaseCrud.Update(disease);
    }

    /**
    * 
    */
    public static long insert(Disease disease) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            disease.DiseaseNum = Meth.GetLong(MethodBase.GetCurrentMethod(), disease);
            return disease.DiseaseNum;
        }
         
        return Crud.DiseaseCrud.Insert(disease);
    }

    /**
    * 
    */
    public static void delete(Disease disease) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), disease);
            return ;
        }
         
        String command = "DELETE FROM disease WHERE DiseaseNum =" + POut.long(disease.DiseaseNum);
        Db.nonQ(command);
    }

    /**
    * Deletes all diseases for one patient.
    */
    public static void deleteAllForPt(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "DELETE FROM disease WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    public static List<long> getChangedSinceDiseaseNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
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
            String command = "SELECT DiseaseNum FROM disease WHERE DateTStamp > " + POut.dateT(changedSince) + " AND (" + strEligibleForUploadPatNums + ")";
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        List<long> diseasenums = new List<long>(table.Rows.Count);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            diseasenums.Add(PIn.Long(table.Rows[i]["DiseaseNum"].ToString()));
        }
        return diseasenums;
    }

    /**
    * Used along with GetChangedSinceDiseaseNums
    */
    public static List<Disease> getMultDiseases(List<long> diseaseNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Disease>>GetObject(MethodBase.GetCurrentMethod(), diseaseNums);
        }
         
        String strDiseaseNums = "";
        DataTable table = new DataTable();
        if (diseaseNums.Count > 0)
        {
            for (int i = 0;i < diseaseNums.Count;i++)
            {
                if (i > 0)
                {
                    strDiseaseNums += "OR ";
                }
                 
                strDiseaseNums += "DiseaseNum='" + diseaseNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM disease WHERE " + strDiseaseNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Disease[] multDiseases = Crud.DiseaseCrud.TableToList(table).ToArray();
        List<Disease> diseaseList = new List<Disease>(multDiseases);
        return diseaseList;
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all diseases of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE disease SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all diseases of a patient that are the status specified.
    */
    public static void resetTimeStamps(long patNum, ProblemStatus status) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, status);
            return ;
        }
         
        String command = "UPDATE disease SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        command += " AND ProbStatus = " + POut.int(((Enum)status).ordinal());
        Db.nonQ(command);
    }

}


