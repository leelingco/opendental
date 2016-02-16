//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Encounter;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Encounters   
{
    /**
    * 
    */
    public static List<Encounter> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Encounter>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM encounter WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateEncounter";
        return Crud.EncounterCrud.SelectMany(command);
    }

    /**
    * Gets one Encounter from the db.
    */
    public static Encounter getOne(long encounterNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Encounter>GetObject(MethodBase.GetCurrentMethod(), encounterNum);
        }
         
        return Crud.EncounterCrud.SelectOne(encounterNum);
    }

    /**
    * Automatically generate and insert encounter as long as there is no other encounter with that date and provider for that patient.  Does not insert an encounter if one of the CQM default encounter prefs are invalid.
    */
    public static void insertDefaultEncounter(long patNum, long provNum, DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, provNum, date);
            return ;
        }
         
        //Validate prefs. If they are not set, we have nothing to insert so no reason to check.
        if (StringSupport.equals(PrefC.getString(PrefName.CQMDefaultEncounterCodeSystem), "") || StringSupport.equals(PrefC.getString(PrefName.CQMDefaultEncounterCodeValue), "none"))
        {
            return ;
        }
         
        //If no encounter for date for this patient
        String command = "SELECT COUNT(*) NumEncounters FROM encounter WHERE encounter.PatNum=" + POut.long(patNum) + " " + "AND encounter.DateEncounter=" + POut.date(date) + " " + "AND encounter.ProvNum=" + POut.long(provNum);
        int count = PIn.int(Db.getCount(command));
        if (count > 0)
        {
            return ;
        }
         
        //Encounter already exists for date
        //Insert encounter with default encounter code system and code value set in Setup>EHR>Settings
        Encounter encounter = new Encounter();
        encounter.PatNum = patNum;
        encounter.ProvNum = provNum;
        encounter.DateEncounter = date;
        encounter.CodeSystem = PrefC.getString(PrefName.CQMDefaultEncounterCodeSystem);
        encounter.CodeValue = PrefC.getString(PrefName.CQMDefaultEncounterCodeValue);
        insert(encounter);
    }

    /**
    * 
    */
    public static long insert(Encounter encounter) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            encounter.EncounterNum = Meth.GetLong(MethodBase.GetCurrentMethod(), encounter);
            return encounter.EncounterNum;
        }
         
        return Crud.EncounterCrud.Insert(encounter);
    }

    /**
    * 
    */
    public static void update(Encounter encounter) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), encounter);
            return ;
        }
         
        Crud.EncounterCrud.Update(encounter);
    }

    /**
    * 
    */
    public static void delete(long encounterNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), encounterNum);
            return ;
        }
         
        String command = "DELETE FROM encounter WHERE EncounterNum = " + POut.long(encounterNum);
        Db.nonQ(command);
    }

}


