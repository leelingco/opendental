//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Patientm;
import OpenDentBusiness.Mobile.Prefm;
import OpenDentBusiness.Mobile.PrefmC;
import OpenDentBusiness.Mobile.PrefmName;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Pref;
import OpenDentBusiness.Prefs;

public class Prefms   
{
    public static PrefmC loadPreferences(long customerNum) throws Exception {
        String command = "SELECT * FROM preferencem WHERE CustomerNum = " + POut.long(customerNum);
        DataTable table = Db.getTable(command);
        Prefm prefm = new Prefm();
        PrefmC prefmc = new PrefmC();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            prefm = new Prefm();
            if (table.Columns.Contains("PrefNum"))
            {
                prefm.PrefNum = PIn.Long(table.Rows[i]["PrefNum"].ToString());
            }
             
            prefm.PrefmName = PIn.String(table.Rows[i]["PrefName"].ToString());
            prefm.ValueString = PIn.String(table.Rows[i]["ValueString"].ToString());
            prefmc.Dict.Add(prefm.PrefmName, prefm);
        }
        HttpContext.Current.Session["prefmC"] = prefmc;
        return prefmc;
    }

    /**
    * Load the preferences from the session. If it is not found in the session it's loaded from the database
    */
    public static PrefmC loadPreferences() throws Exception {
        PrefmC prefmc = (PrefmC)HttpContext.Current.Session["prefmC"];
        if (prefmc == null)
        {
            if (HttpContext.Current.Session["Patient"] != null)
            {
                long DentalOfficeID = ((Patientm)HttpContext.Current.Session["Patient"]).CustomerNum;
                prefmc = loadPreferences(DentalOfficeID);
            }
             
        }
         
        return prefmc;
    }

    /**
    * converts a Pref to a Prefm object
    */
    public static Prefm convertToM(Pref pref) throws Exception {
        Prefm prefm = new Prefm();
        prefm.PrefNum = pref.PrefNum;
        prefm.PrefmName = pref.PrefName;
        prefm.ValueString = pref.ValueString;
        return prefm;
    }

    /**
    * Returns a Prefm object when provided with the PrefName. Note that the CustomerNum field of the return object is not populated.
    */
    public static Prefm getPrefm(String PrefName) throws Exception {
        Pref pref = Prefs.GetPref(PrefName);
        Prefm prefm = ConvertToM(pref);
        return prefm;
    }

    /**
    * Returns true if a change was required, or false if no change needed. This method is no longer used and may be deleted later. Dennis Mathew: Dec 24, 2011
    */
    public void updateString(long customerNum, PrefmName prefmName, String newValue) throws Exception {
        String command = "SELECT * FROM preferencem " + "WHERE CustomerNum =" + POut.long(customerNum) + " AND PrefName = '" + POut.String(prefmName.ToString()) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            command = "UPDATE preferencem SET " + "ValueString = '" + POut.string(newValue) + "' " + "WHERE CustomerNum =" + POut.long(customerNum) + " AND PrefName = '" + POut.String(prefmName.ToString()) + "'";
            Db.nonQ(command);
        }
        else
        {
            command = "INSERT into preferencem " + "(CustomerNum,PrefName,ValueString) VALUES " + "(" + POut.long(customerNum) + ",'" + POut.String(prefmName.ToString()) + "','" + POut.string(newValue) + "')";
            Db.nonQ(command);
        } 
    }

    public static void updatePreference(Prefm prefm) throws Exception {
        String command = "SELECT * FROM preferencem " + "WHERE CustomerNum =" + POut.long(prefm.CustomerNum) + " AND PrefNum = " + POut.long(prefm.PrefNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            command = "UPDATE preferencem SET " + "ValueString = '" + POut.string(prefm.ValueString) + "' " + "WHERE CustomerNum =" + POut.long(prefm.CustomerNum) + " AND PrefNum = " + POut.long(prefm.PrefNum);
            Db.nonQ(command);
        }
        else
        {
            command = "INSERT into preferencem " + "(CustomerNum,PrefNum,PrefName,ValueString) VALUES " + "(" + POut.long(prefm.CustomerNum) + "," + POut.long(prefm.PrefNum) + ",'" + POut.String(prefm.PrefmName.ToString()) + "','" + POut.string(prefm.ValueString) + "')";
            Db.nonQ(command);
        } 
    }

}


