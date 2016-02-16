//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SnomedAllergy;

/**
* 
*/
public class AllergyDefs   
{
    /**
    * Gets one AllergyDef from the db.
    */
    public static AllergyDef getOne(long allergyDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<AllergyDef>GetObject(MethodBase.GetCurrentMethod(), allergyDefNum);
        }
         
        return Crud.AllergyDefCrud.SelectOne(allergyDefNum);
    }

    /**
    * Gets one AllergyDef matching the specified allergyDefNum from the list passed in. If none found will search the db for a matching allergydef. Returns null if not found in the db.
    */
    public static AllergyDef getOne(long allergyDefNum, List<AllergyDef> listAllergyDef) throws Exception {
        for (int i = 0;i < listAllergyDef.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (allergyDefNum == listAllergyDef[i].AllergyDefNum)
            {
                return listAllergyDef[i];
            }
             
        }
        return getOne(allergyDefNum);
    }

    //Gets the allergydef matching the allergy so we can use it to populate the grid
    /**
    * Gets one AllergyDef from the db with matching description, returns null if not found.
    */
    public static AllergyDef getByDescription(String allergyDescription) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<AllergyDef>GetObject(MethodBase.GetCurrentMethod(), allergyDescription);
        }
         
        String command = "SELECT * FROM allergyDef WHERE Description='" + POut.string(allergyDescription) + "'";
        List<AllergyDef> retVal = Crud.AllergyDefCrud.SelectMany(command);
        if (retVal.Count > 0)
        {
            return retVal[0];
        }
         
        return null;
    }

    /**
    * 
    */
    public static long insert(AllergyDef allergyDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            allergyDef.AllergyDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), allergyDef);
            return allergyDef.AllergyDefNum;
        }
         
        return Crud.AllergyDefCrud.Insert(allergyDef);
    }

    /**
    * 
    */
    public static void update(AllergyDef allergyDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), allergyDef);
            return ;
        }
         
        Crud.AllergyDefCrud.Update(allergyDef);
    }

    /**
    * 
    */
    public static List<AllergyDef> tableToList(DataTable table) throws Exception {
        return Crud.AllergyDefCrud.TableToList(table);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * 
    */
    public static void delete(long allergyDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), allergyDefNum);
            return ;
        }
         
        String command = "DELETE FROM allergydef WHERE AllergyDefNum = " + POut.long(allergyDefNum);
        Db.nonQ(command);
    }

    /**
    * Gets all AllergyDefs based on hidden status.
    */
    public static List<AllergyDef> getAll(boolean isHidden) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<AllergyDef>>GetObject(MethodBase.GetCurrentMethod(), isHidden);
        }
         
        String command = "";
        if (!isHidden)
        {
            command = "SELECT * FROM allergydef WHERE IsHidden=" + POut.bool(isHidden) + " ORDER BY Description";
        }
        else
        {
            command = "SELECT * FROM allergydef ORDER BY Description";
        } 
        return Crud.AllergyDefCrud.SelectMany(command);
    }

    /**
    * Returns true if the allergy def is in use and false if not.
    */
    public static boolean defIsInUse(long allergyDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), allergyDefNum);
        }
         
        String command = "SELECT COUNT(*) FROM allergy WHERE AllergyDefNum=" + POut.long(allergyDefNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            return true;
        }
         
        if (allergyDefNum == PrefC.getLong(PrefName.AllergiesIndicateNone))
        {
            return true;
        }
         
        return false;
    }

    public static List<long> getChangedSinceAllergyDefNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT AllergyDefNum FROM allergydef WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> allergyDefNums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            allergyDefNums.Add(PIn.Long(dt.Rows[i]["AllergyDefNum"].ToString()));
        }
        return allergyDefNums;
    }

    /**
    * Used along with GetChangedSinceAllergyDefNums
    */
    public static List<AllergyDef> getMultAllergyDefs(List<long> allergyDefNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<AllergyDef>>GetObject(MethodBase.GetCurrentMethod(), allergyDefNums);
        }
         
        String strAllergyDefNums = "";
        DataTable table = new DataTable();
        if (allergyDefNums.Count > 0)
        {
            for (int i = 0;i < allergyDefNums.Count;i++)
            {
                if (i > 0)
                {
                    strAllergyDefNums += "OR ";
                }
                 
                strAllergyDefNums += "AllergyDefNum='" + allergyDefNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM allergydef WHERE " + strAllergyDefNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        AllergyDef[] multAllergyDefs = Crud.AllergyDefCrud.TableToList(table).ToArray();
        List<AllergyDef> allergyDefList = new List<AllergyDef>(multAllergyDefs);
        return allergyDefList;
    }

    /**
    * Do not call from outside of ehr.  Returns the text for a SnomedAllergy Enum as it should appear in human readable form for a CCD.
    */
    public static String getSnomedAllergyDesc(SnomedAllergy snomed) throws Exception {
        String result = new String();
        switch(snomed)
        {
            case AdverseReactions: 
                //TODO: hide snomed code from foreign users
                result = "420134006 - Propensity to adverse reactions (disorder)";
                break;
            case AdverseReactionsToDrug: 
                result = "419511003 - Propensity to adverse reactions to drug (disorder)";
                break;
            case AdverseReactionsToFood: 
                result = "418471000 - Propensity to adverse reactions to food (disorder)";
                break;
            case AdverseReactionsToSubstance: 
                result = "419199007 - Propensity to adverse reactions to substance (disorder)";
                break;
            case AllergyToSubstance: 
                result = "418038007 - Allergy to substance (disorder)";
                break;
            case DrugAllergy: 
                result = "416098002 - Drug allergy (disorder)";
                break;
            case DrugIntolerance: 
                result = "59037007 - Drug intolerance (disorder)";
                break;
            case FoodAllergy: 
                result = "235719002 - Food allergy (disorder)";
                break;
            case FoodIntolerance: 
                result = "420134006 - Food intolerance (disorder)";
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

    /**
    * Returns the name of the allergy. Returns an empty string if allergyDefNum=0.
    */
    public static String getDescription(long allergyDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), allergyDefNum);
        }
         
        if (allergyDefNum == 0)
        {
            return "";
        }
         
        return Crud.AllergyDefCrud.SelectOne(allergyDefNum).Description;
    }

    /**
    * Returns the AllergyDef with the corresponding SNOMED allergyTo code. Returns null if codeValue is empty string.
    */
    public static AllergyDef getAllergyDefFromCode(String codeValue) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<AllergyDef>GetObject(MethodBase.GetCurrentMethod(), codeValue);
        }
         
        if (StringSupport.equals(codeValue, ""))
        {
            return null;
        }
         
        String command = "SELECT * FROM allergyDef WHERE SnomedAllergyTo=" + POut.string(codeValue);
        return Crud.AllergyDefCrud.SelectOne(command);
    }

    /**
    * Returns the AllergyDef with the corresponding Medication. Returns null if medicationNum is 0.
    */
    public static AllergyDef getAllergyDefFromMedication(long medicationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<AllergyDef>GetObject(MethodBase.GetCurrentMethod(), medicationNum);
        }
         
        if (medicationNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM allergyDef WHERE MedicationNum=" + POut.long(medicationNum);
        return Crud.AllergyDefCrud.SelectOne(command);
    }

}


