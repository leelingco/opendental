//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cdcrecs;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatientRace;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PatientRaces   
{
    /**
    * Gets all PatientRace enteries from the db.
    */
    public static List<PatientRace> getForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PatientRace>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM patientrace WHERE PatNum = " + POut.long(patNum);
        return Crud.PatientRaceCrud.SelectMany(command);
    }

    /**
    * Gets all patintrace entries for the patient and returns all of their races as a list of ints.  The list of ints corresponds to the PatRace enum.
    */
    public static List<int> getPatRaceList(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<PatientRace> patEntries = getForPatient(patNum);
        List<int> listPatRace = new List<int>();
        for (int i = 0;i < patEntries.Count;i++)
        {
            listPatRace.Add((int)patEntries[i].Race);
        }
        return listPatRace;
    }

    /**
    * Returns the PatientRaceOld enum based on the PatientRace entries for the patient passed in.  Calls GetPatRaceList to get the list of races.
    */
    public static PatientRaceOld getPatientRaceOldFromPatientRaces(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<int> races = getPatRaceList(patNum);
        if (races.Count == 0)
        {
            return (PatientRaceOld.Unknown);
        }
         
        //Unknown is default for PatientRaceOld
        if (races.Contains(((Enum)PatRace.White).ordinal()))
        {
            if (races.Contains(((Enum)PatRace.Hispanic).ordinal()))
            {
                return PatientRaceOld.HispanicLatino;
            }
             
            return PatientRaceOld.White;
        }
         
        if (races.Contains(((Enum)PatRace.AfricanAmerican).ordinal()))
        {
            if (races.Contains(((Enum)PatRace.Hispanic).ordinal()))
            {
                return PatientRaceOld.BlackHispanic;
            }
             
            return PatientRaceOld.AfricanAmerican;
        }
         
        if (races.Contains(((Enum)PatRace.Aboriginal).ordinal()))
        {
            return PatientRaceOld.Aboriginal;
        }
         
        if (races.Contains(((Enum)PatRace.AmericanIndian).ordinal()))
        {
            return PatientRaceOld.AmericanIndian;
        }
         
        if (races.Contains(((Enum)PatRace.Asian).ordinal()))
        {
            return PatientRaceOld.Asian;
        }
         
        if (races.Contains(((Enum)PatRace.HawaiiOrPacIsland).ordinal()))
        {
            return PatientRaceOld.HawaiiOrPacIsland;
        }
         
        if (races.Contains(((Enum)PatRace.Multiracial).ordinal()))
        {
            return PatientRaceOld.Multiracial;
        }
         
        if (races.Contains(((Enum)PatRace.Other).ordinal()))
        {
            return PatientRaceOld.Other;
        }
         
        return PatientRaceOld.Unknown;
    }

    //Hispanic
    //DeclinedToSpecify
    /**
    * Gets a list of PatRaces that correspond to a PatientRaceOld enum.
    */
    public static List<PatRace> getPatRacesFromPatientRaceOld(PatientRaceOld raceOld) throws Exception {
        List<PatRace> retVal = new List<PatRace>();
        switch(raceOld)
        {
            case Unknown: 
                break;
            case Multiracial: 
                //Do nothing.  No entry means "Unknown", the old default.
                retVal.Add(PatRace.Multiracial);
                break;
            case HispanicLatino: 
                retVal.Add(PatRace.White);
                retVal.Add(PatRace.Hispanic);
                break;
            case AfricanAmerican: 
                retVal.Add(PatRace.AfricanAmerican);
                break;
            case White: 
                retVal.Add(PatRace.White);
                break;
            case HawaiiOrPacIsland: 
                retVal.Add(PatRace.HawaiiOrPacIsland);
                break;
            case AmericanIndian: 
                retVal.Add(PatRace.AmericanIndian);
                break;
            case Asian: 
                retVal.Add(PatRace.Asian);
                break;
            case Other: 
                retVal.Add(PatRace.Other);
                break;
            case Aboriginal: 
                retVal.Add(PatRace.Aboriginal);
                break;
            case BlackHispanic: 
                retVal.Add(PatRace.AfricanAmerican);
                retVal.Add(PatRace.Hispanic);
                break;
        
        }
        return retVal;
    }

    /**
    * Inserts or Deletes neccesary PatientRace entries for the specified patient given the list of PatRaces provided.
    */
    public static void reconcile(long patNum, List<PatRace> listPatRaces) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, listPatRaces);
            return ;
        }
         
        String command = new String();
        if (listPatRaces.Count == 0)
        {
            //DELETE all for the patient if listPatRaces is empty.
            command = "DELETE FROM patientrace WHERE PatNum = " + POut.long(patNum);
            //Can't use CRUD layer here because there might be multiple races for one patient.
            Db.nonQ(command);
            return ;
        }
         
        List<PatientRace> listPatientRaces = new List<PatientRace>();
        //Rename this variable and the listPatRaces variable so it is easier to indicate which is the "selected" list and which is the db list.
        command = "SELECT * FROM patientrace WHERE PatNum = " + POut.long(patNum);
        listPatientRaces = Crud.PatientRaceCrud.SelectMany(command);
        for (int i = 0;i < listPatientRaces.Count;i++)
        {
            //delete excess rows
            if (!listPatRaces.Contains((PatRace)listPatientRaces[i].Race))
            {
                //if there is a PatientRace row that does not match the new list of PatRaces, delete it
                Crud.PatientRaceCrud.Delete(listPatientRaces[i].PatientRaceNum);
            }
             
        }
        for (int i = 0;i < listPatRaces.Count;i++)
        {
            //insert new rows
            boolean insertNeeded = true;
            for (int j = 0;j < listPatientRaces.Count;j++)
            {
                if (listPatRaces[i] == listPatientRaces[j].Race)
                {
                    insertNeeded = false;
                }
                 
            }
            if (insertNeeded)
            {
                PatientRace pr = new PatientRace();
                pr.PatNum = patNum;
                pr.Race = listPatRaces[i];
                pr.CdcrecCode = Cdcrecs.GetByPatRace(listPatRaces[i]);
                Crud.PatientRaceCrud.Insert(pr);
            }
             
        }
    }

}


//next PatRace
//return;
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<PatientRace> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<PatientRace>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM patientrace WHERE PatNum = "+POut.Long(patNum);
			return Crud.PatientRaceCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(PatientRace patientRace){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				patientRace.PatientRaceNum=Meth.GetLong(MethodBase.GetCurrentMethod(),patientRace);
				return patientRace.PatientRaceNum;
			}
			return Crud.PatientRaceCrud.Insert(patientRace);
		}
		///<summary></summary>
		public static void Delete(long patientRaceNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),patientRaceNum);
				return;
			}
			string command= "DELETE FROM patientrace WHERE PatientRaceNum = "+POut.Long(patientRaceNum);
			Db.NonQ(command);
		}
		*/