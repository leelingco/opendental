//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EnumPopupLevel;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Popup;
import OpenDentBusiness.Popups;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Popups   
{
    /**
    * Gets all active popups that should be displayed for a single patient.
    */
    public static List<Popup> getForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Popup>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        if (patNum == 0)
        {
            return new List<Popup>();
        }
         
        Patient patCur = Patients.getPat(patNum);
        //any family level popup for anyone in the family
        String command = "SELECT * FROM popup " + "WHERE IsDisabled=0 " + "AND IsArchived=0 " + "AND (PatNum = " + POut.long(patNum) + " " + "OR (PatNum IN (SELECT PatNum FROM patient " + "WHERE Guarantor = " + POut.long(patCur.Guarantor) + ") " + "AND PopupLevel = " + POut.int(((Enum)EnumPopupLevel.Family).ordinal()) + ") ";
        //any superfamily level popup for anyone in the superfamily
        if (patCur.SuperFamily != 0)
        {
            //They are part of a super family
            command += "OR (PatNum IN (SELECT PatNum FROM patient " + "WHERE SuperFamily = " + POut.long(patCur.SuperFamily) + ") " + "AND PopupLevel = " + POut.int(((Enum)EnumPopupLevel.SuperFamily).ordinal()) + ") ";
        }
         
        command += ")";
        return Crud.PopupCrud.SelectMany(command);
    }

    /**
    * Gets current and disabled popups for a single family.  If patient is part of a superfamily, it will get all popups for the entire superfamily.
    */
    public static List<Popup> getForFamily(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Popup>>GetObject(MethodBase.GetCurrentMethod(), pat);
        }
         
        String command = "SELECT * FROM popup " + "WHERE (PatNum IN (SELECT PatNum FROM patient " + "WHERE Guarantor = " + POut.long(pat.Guarantor) + ") ";
        if (pat.SuperFamily != 0)
        {
            //They are part of a super family
            command += "OR PatNum IN (SELECT PatNum FROM patient " + "WHERE SuperFamily = " + POut.long(pat.SuperFamily) + ") ";
        }
         
        command += ") " + "AND IsArchived = 0 " + "ORDER BY PatNum";
        return Crud.PopupCrud.SelectMany(command);
    }

    /**
    * Gets the most recent deleted popups for a single family.  If patient is part of a superfamily, it will get all popups for the entire superfamily.
    */
    public static List<Popup> getDeletedForFamily(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Popup>>GetObject(MethodBase.GetCurrentMethod(), pat);
        }
         
        String command = "SELECT * FROM popup " + "WHERE PatNum IN (SELECT PatNum FROM patient " + "WHERE Guarantor = " + POut.long(pat.Guarantor) + ") ";
        if (pat.SuperFamily != 0)
        {
            //They are part of a super family
            command += "OR PatNum IN (SELECT PatNum FROM patient " + "WHERE SuperFamily = " + POut.long(pat.SuperFamily) + ") ";
        }
         
        command += "AND PopupNumArchive = 0 " + "ORDER BY PatNum";
        return Crud.PopupCrud.SelectMany(command);
    }

    //The most recent pop up in the archives.
    /**
    * Gets all archived popups for a single popup.
    */
    public static List<Popup> getArchivesForPopup(long popupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Popup>>GetObject(MethodBase.GetCurrentMethod(), popupNum);
        }
         
        String command = "SELECT * FROM popup" + " WHERE PopupNumArchive = " + POut.long(popupNum) + " ORDER BY DateTimeEntry";
        return Crud.PopupCrud.SelectMany(command);
    }

    /**
    * Gets the most recent date and time that the popup was last edited.  Returns min value if no archive was found.
    */
    public static DateTime getLastEditDateTimeForPopup(long popupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DateTime>GetObject(MethodBase.GetCurrentMethod(), popupNum);
        }
         
        String command = "SELECT DateTimeEntry FROM popup" + " WHERE PopupNumArchive = " + POut.long(popupNum) + " ORDER BY DateTimeEntry DESC" + " LIMIT 1";
        DataTable rawTable = Db.getTable(command);
        if (rawTable.Rows.Count == 0)
        {
            return DateTime.MinValue;
        }
         
        return PIn.DateT(rawTable.Rows[0]["DateTimeEntry"].ToString());
    }

    /**
    * Copies all family level popups when a family member leaves a family. Copies from other family members to patient, and from patient to guarantor.
    */
    public static void copyForMovingFamilyMember(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        //Get a list of all popups for the family
        String command = "SELECT * FROM popup " + "WHERE PopupLevel = " + POut.int(((Enum)EnumPopupLevel.Family).ordinal()) + " " + "AND PatNum IN (SELECT PatNum FROM patient WHERE Guarantor = " + POut.long(pat.Guarantor) + ")" + "AND PopupNumArchive = 0 ";
        List<Popup> FamilyPopups = Crud.PopupCrud.SelectMany(command);
        Popup popupCur;
        for (int i = 0;i < FamilyPopups.Count;i++)
        {
            popupCur = FamilyPopups[i].Copy();
            if (popupCur.PatNum == pat.PatNum)
            {
                //if popup is on the patient who's leaving, copy to guarantor of old family.
                popupCur.PatNum = pat.Guarantor;
            }
            else
            {
                //if popup is on some other family member, then copy to this patient.
                popupCur.PatNum = pat.PatNum;
            } 
            DateTime oldDate = popupCur.DateTimeEntry;
            long newPk = Popups.insert(popupCur);
            //changes the PK
            editPopupDate(oldDate,newPk);
            List<Popup> archivePopups = GetArchivesForPopup(FamilyPopups[i].PopupNum);
            Popup popupArchive;
            for (int j = 0;j < archivePopups.Count;j++)
            {
                popupArchive = archivePopups[j].Copy();
                if (popupArchive.PatNum == pat.PatNum)
                {
                    //if popup is on the patient who's leaving, copy to guarantor of old family.
                    popupArchive.PatNum = pat.Guarantor;
                }
                else
                {
                    //if popup is on some other family member, then copy to this patient.
                    popupArchive.PatNum = pat.PatNum;
                } 
                popupArchive.PopupNumArchive = newPk;
                DateTime oldArchiveDate = popupArchive.DateTimeEntry;
                long newArchivePk = Popups.insert(popupArchive);
                //changes the PK
                editPopupDate(oldArchiveDate,newArchivePk);
            }
        }
    }

    /**
    * When a patient leaves a superfamily, this copies the superfamily level popups to be in both places. Takes pat leaving, and new superfamily. If newSuperFamily is 0, superfamily popups will not be copied from the old superfamily.
    */
    public static void copyForMovingSuperFamily(Patient pat, long newSuperFamily) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat, newSuperFamily);
            return ;
        }
         
        //Get a list of all popups for the super family
        String command = "SELECT * FROM popup " + "WHERE PopupLevel = " + POut.int(((Enum)EnumPopupLevel.SuperFamily).ordinal()) + " " + "AND PatNum IN (SELECT PatNum FROM patient WHERE SuperFamily = " + POut.long(pat.SuperFamily) + ")" + "AND PopupNumArchive = 0 ";
        //This includes all the archived ones as well
        List<Popup> SuperFamilyPopups = Crud.PopupCrud.SelectMany(command);
        Popup popupCur;
        for (int i = 0;i < SuperFamilyPopups.Count;i++)
        {
            popupCur = SuperFamilyPopups[i].Copy();
            if (popupCur.PatNum == pat.PatNum)
            {
                //if popup is on the patient who's leaving, copy to superfamily head of old superfamily.
                popupCur.PatNum = pat.SuperFamily;
                if (newSuperFamily == 0)
                {
                    //If they are not going to a superfamily, set popup to family level
                    String commandUpdateFam = "UPDATE popup " + "SET PopupLevel = " + POut.int(((Enum)EnumPopupLevel.Family).ordinal()) + " " + "WHERE PopupNum = " + POut.long(popupCur.PopupNum);
                    Db.nonQ(commandUpdateFam);
                }
                 
            }
            else
            {
                //if popup is on some other super family member, then copy to this patient.
                popupCur.PatNum = pat.PatNum;
                if (newSuperFamily == 0)
                {
                    //If they are not going to a superfamily, set popup to family level
                    popupCur.PopupLevel = EnumPopupLevel.Family;
                }
                 
            } 
            DateTime oldDate = popupCur.DateTimeEntry;
            long newPk = Popups.insert(popupCur);
            //changes the PK
            //Update the DateTimeEntry on the copy to correctly reflect when the original popup was created.
            editPopupDate(oldDate,newPk);
            //Now we need to copy all of the archives of the original popup to point to the copy.
            List<Popup> archivePopups = GetArchivesForPopup(SuperFamilyPopups[i].PopupNum);
            Popup popupArchive;
            for (int j = 0;j < archivePopups.Count;j++)
            {
                popupArchive = archivePopups[j].Copy();
                popupArchive.PopupNumArchive = newPk;
                DateTime oldArchiveDate = popupArchive.DateTimeEntry;
                long newArchivePk = Popups.insert(popupArchive);
                //changes the PK
                editPopupDate(oldArchiveDate,newArchivePk);
            }
        }
    }

    /**
    * Moves all family and superfamily level popups for a patient being deleted so that those popups stay in the family/superfamily.
    */
    public static void moveForDeletePat(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE popup ";
        if (pat.PatNum == pat.Guarantor)
        {
            //When deleting the guarantor, move all superfamily popups to the superfamily head
            command += "SET PatNum = " + POut.long(pat.SuperFamily) + " " + "WHERE PopupLevel = " + POut.int(((Enum)EnumPopupLevel.SuperFamily).ordinal()) + " " + "AND PatNum = " + POut.long(pat.PatNum);
        }
        else
        {
            //Move all family/superfamily popups to the guarantor
            command += "SET PatNum = " + POut.long(pat.Guarantor) + " " + "WHERE (PopupLevel = " + POut.int(((Enum)EnumPopupLevel.Family).ordinal()) + " " + "OR PopupLevel = " + POut.int(((Enum)EnumPopupLevel.SuperFamily).ordinal()) + ") " + "AND PatNum = " + POut.long(pat.PatNum);
        } 
        Db.nonQ(command);
    }

    /**
    * Popup dates are not normally changed.  This only occurs when creating exact copies of popups and their archives when moving a patient from a family or superfamily.
    */
    private static void editPopupDate(DateTime oldDate, long newPk) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), oldDate, newPk);
            return ;
        }
         
        String commandUpdate = "UPDATE popup " + "SET DateTimeEntry = " + POut.dateT(oldDate) + " " + "WHERE PopupNum = " + POut.long(newPk);
        Db.nonQ(commandUpdate);
    }

    /**
    * Brings all superfamily level popups for a superfamily being disbanded to the family level.
    */
    public static void removeForDisbandingSuperFamily(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE popup " + "SET PopupLevel = " + POut.int(((Enum)EnumPopupLevel.Family).ordinal()) + " " + "WHERE PopupLevel = " + POut.int(((Enum)EnumPopupLevel.SuperFamily).ordinal()) + " " + "AND PatNum IN (SELECT PatNum FROM patient WHERE SuperFamily=" + POut.long(pat.SuperFamily) + ") " + "AND PopupNumArchive = 0";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(Popup popup) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            popup.PopupNum = Meth.GetLong(MethodBase.GetCurrentMethod(), popup);
            return popup.PopupNum;
        }
         
        return Crud.PopupCrud.Insert(popup);
    }

    /**
    * Create an archive of the pop up before updating.
    */
    public static void update(Popup popup) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), popup);
            return ;
        }
         
        Crud.PopupCrud.Update(popup);
    }

    /**
    * Only called when moving popups for a patient that is leaving a superfamily but not going to another superfamily.
    */
    public static void deleteObject(Popup popup) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), popup);
            return ;
        }
         
        Crud.PopupCrud.Delete(popup.PopupNum);
    }

}


