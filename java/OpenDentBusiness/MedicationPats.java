//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxPat;

/**
* 
*/
public class MedicationPats   
{
    /**
    * Normally, includeDiscontinued is false.  User needs to check a box to include discontinued.
    */
    public static List<MedicationPat> refresh(long patNum, boolean includeDiscontinued) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicationPat>>GetObject(MethodBase.GetCurrentMethod(), patNum, includeDiscontinued);
        }
         
        String command = "SELECT * FROM medicationpat WHERE PatNum = " + POut.long(patNum);
        if (includeDiscontinued)
        {
        }
        else
        {
            //this only happens when user checks box to show discontinued or for MU.
            //no restriction on DateStop
            //exclude discontinued.  This is the default.
            command += " AND (DateStop < " + POut.date(new DateTime(1880, 1, 1)) + " OR DateStop >= CURDATE())";
        } 
        return Crud.MedicationPatCrud.SelectMany(command);
    }

    //include all the meds that are not discontinued.
    //Show medications that are today or a future stopdate - they are not yet discontinued.
    /**
    * Gets all active medications for the patient.  Exactly like Refresh() except this does not return medications when DateStop has today's date.  Currently only called from FormReconcileMedication.
    */
    public static List<MedicationPat> getMedPatsForReconcile(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicationPat>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM medicationpat WHERE PatNum = " + POut.long(patNum) + " AND (DateStop < " + POut.date(new DateTime(1880, 1, 1)) + " OR DateStop > CURDATE())";
        return Crud.MedicationPatCrud.SelectMany(command);
    }

    //include all the meds that are not discontinued.
    //Show medications that are a future stopdate.
    /**
    * 
    */
    public static MedicationPat getOne(long medicationPatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<MedicationPat>GetObject(MethodBase.GetCurrentMethod(), medicationPatNum);
        }
         
        String command = "SELECT * FROM medicationpat WHERE MedicationPatNum = " + POut.long(medicationPatNum);
        return Crud.MedicationPatCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(MedicationPat Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.MedicationPatCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(MedicationPat Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.MedicationPatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.MedicationPatNum;
        }
         
        return Crud.MedicationPatCrud.Insert(Cur);
    }

    /**
    * For CPOE.  Used for both manual rx and eRx through NewCrop.  Creates or updates a medical order using the given prescription information.
    * Since rxCui is not part of the prescription, it must be passed in as a separate parameter.
    * If isProvOrder is true, then the medical order provNum will be set to the prescription provNum.  If isProvOrder is false, then the medical order provNum will be set to 0.
    * The MedDescript and NewCropGuid will always be copied from the prescription to the medical order and the medical order MedicationNum will be set to 0.
    * This method return the medOrderNum for the new/updated medicationPat. Unlike most medical orders this does not create an entry in the medical order table.
    */
    public static long insertOrUpdateMedOrderForRx(RxPat rxPat, long rxCui, boolean isProvOrder) throws Exception {
        long medOrderNum = new long();
        MedicationPat medOrder = new MedicationPat();
        //The medication order corresponding to the prescription.
        medOrder.DateStart = rxPat.RxDate;
        medOrder.DateStop = rxPat.RxDate.AddDays(7);
        //Is there a way to easily calculate this information from the prescription information? The medical order will be inactive after this date.
        medOrder.MedDescript = rxPat.Drug;
        medOrder.RxCui = rxCui;
        medOrder.NewCropGuid = rxPat.NewCropGuid;
        medOrder.PatNote = rxPat.Sig;
        medOrder.PatNum = rxPat.PatNum;
        if (isProvOrder)
        {
            medOrder.ProvNum = rxPat.ProvNum;
            medOrder.IsCpoe = true;
        }
         
        MedicationPat medOrderOld = null;
        if (!String.IsNullOrEmpty(rxPat.NewCropGuid))
        {
            //This check prevents an extra db call when the order is being created for a prescription written from inside of OD manually instead of using NewCrop.
            medOrderOld = MedicationPats.getMedicationOrderByNewCropGuid(rxPat.NewCropGuid);
        }
         
        if (medOrderOld == null)
        {
            medOrder.setIsNew(true);
            //Might not be necessary, but does not hurt.
            medOrderNum = MedicationPats.insert(medOrder);
        }
        else
        {
            //The medication order was already in our database. Update it.
            medOrder.MedicationPatNum = medOrderOld.MedicationPatNum;
            MedicationPats.update(medOrder);
            medOrderNum = medOrder.MedicationPatNum;
        } 
        return medOrderNum;
    }

    /**
    * 
    */
    public static void delete(MedicationPat Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from medicationpat WHERE medicationpatNum = '" + Cur.MedicationPatNum.ToString() + "'";
        Db.nonQ(command);
    }

    public static List<long> getChangedSinceMedicationPatNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
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
            String command = "SELECT MedicationPatNum FROM medicationpat WHERE DateTStamp > " + POut.dateT(changedSince) + " AND (" + strEligibleForUploadPatNums + ")";
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        List<long> medicationpatnums = new List<long>(table.Rows.Count);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            medicationpatnums.Add(PIn.Long(table.Rows[i]["MedicationPatNum"].ToString()));
        }
        return medicationpatnums;
    }

    /**
    * Used along with GetChangedSinceMedicationPatNums
    */
    public static List<MedicationPat> getMultMedicationPats(List<long> medicationPatNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicationPat>>GetObject(MethodBase.GetCurrentMethod(), medicationPatNums);
        }
         
        String strMedicationPatNums = "";
        DataTable table = new DataTable();
        if (medicationPatNums.Count > 0)
        {
            for (int i = 0;i < medicationPatNums.Count;i++)
            {
                if (i > 0)
                {
                    strMedicationPatNums += "OR ";
                }
                 
                strMedicationPatNums += "MedicationPatNum='" + medicationPatNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM medicationpat WHERE " + strMedicationPatNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        MedicationPat[] multMedicationPats = Crud.MedicationPatCrud.TableToList(table).ToArray();
        List<MedicationPat> medicationPatList = new List<MedicationPat>(multMedicationPats);
        return medicationPatList;
    }

    /**
    * Get list of MedicationPats by MedicationNum for a particular patient.
    */
    public static List<MedicationPat> getMedicationPatsByMedicationNum(long medicationNum, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicationPat>>GetObject(MethodBase.GetCurrentMethod(), medicationNum, patNum);
        }
         
        String command = "SELECT * FROM medicationpat WHERE PatNum=" + POut.long(patNum) + " AND MedicationNum=" + POut.long(medicationNum);
        return Crud.MedicationPatCrud.SelectMany(command);
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all medicationpats of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE medicationpat SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all medicationpats of a patient that are the status specified.
    */
    public static void resetTimeStamps(long patNum, boolean onlyActive) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, onlyActive);
            return ;
        }
         
        String command = "UPDATE medicationpat SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum = " + POut.long(patNum);
        if (onlyActive)
        {
            command += " AND (DateStop > 1880 OR DateStop <= CURDATE())";
        }
         
        Db.nonQ(command);
    }

    /**
    * Used for NewCrop medication orders only.
    */
    public static MedicationPat getMedicationOrderByNewCropGuid(String newCropGuid) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<MedicationPat>GetObject(MethodBase.GetCurrentMethod(), newCropGuid);
        }
         
        String command = "SELECT * FROM medicationpat WHERE NewCropGuid='" + POut.string(newCropGuid) + "'";
        List<MedicationPat> medicationOrderNewCrop = Crud.MedicationPatCrud.SelectMany(command);
        if (medicationOrderNewCrop.Count == 0)
        {
            return null;
        }
         
        return medicationOrderNewCrop[0];
    }

    /**
    * Used to synch medication.RxCui with medicationpat.RxCui.  Updates all medicationpat.RxCui to the given value for those medication pats linked to the given medication num.
    */
    public static void updateRxCuiForMedication(long medicationNum, long rxCui) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), medicationNum, rxCui);
            return ;
        }
         
        String command = "UPDATE medicationpat SET RxCui=" + POut.long(rxCui) + " WHERE MedicationNum=" + POut.long(medicationNum);
        Db.nonQ(command);
    }

    public static boolean isMedActive(MedicationPat medicationPat) throws Exception {
        if (medicationPat.DateStop.Year < 1880 || medicationPat.DateStop >= DateTime.Today)
        {
            return true;
        }
         
        return false;
    }

}


