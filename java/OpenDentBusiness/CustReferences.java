//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CustReference;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CustReferences   
{
    /**
    * Gets one CustReference from the db.
    */
    public static CustReference getOne(long custReferenceNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<CustReference>GetObject(MethodBase.GetCurrentMethod(), custReferenceNum);
        }
         
        return Crud.CustReferenceCrud.SelectOne(custReferenceNum);
    }

    /**
    * 
    */
    public static long insert(CustReference custReference) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            custReference.CustReferenceNum = Meth.GetLong(MethodBase.GetCurrentMethod(), custReference);
            return custReference.CustReferenceNum;
        }
         
        return Crud.CustReferenceCrud.Insert(custReference);
    }

    /**
    * 
    */
    public static void update(CustReference custReference) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), custReference);
            return ;
        }
         
        Crud.CustReferenceCrud.Update(custReference);
    }

    /**
    * Might not be used.  Might implement when a patient is deleted but doesn't happen often if ever.
    */
    public static void delete(long custReferenceNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), custReferenceNum);
            return ;
        }
         
        String command = "DELETE FROM custreference WHERE CustReferenceNum = " + POut.long(custReferenceNum);
        Db.nonQ(command);
    }

    /**
    * Used only from FormReferenceSelect to get the list of references.
    */
    public static DataTable getReferenceTable(boolean limit, long[] billingTypes, boolean showBadRefs, boolean showUsed, boolean showGuarOnly, String city, String state, String zip, String areaCode, String specialty, int superFam, String lname, String fname, String patnum, int age) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), limit, billingTypes, showBadRefs, showUsed, showGuarOnly, city, state, zip, areaCode, specialty, superFam, lname, fname, patnum, age);
        }
         
        String billingSnippet = "";
        if (billingTypes.Length != 0)
        {
            for (int i = 0;i < billingTypes.Length;i++)
            {
                if (i == 0)
                {
                    billingSnippet += "AND (";
                }
                else
                {
                    billingSnippet += "OR ";
                } 
                billingSnippet += "BillingType=" + POut.Long(billingTypes[i]) + " ";
                if (i == billingTypes.Length - 1)
                {
                    billingSnippet += ") ";
                }
                 
            }
        }
         
        String phonedigits = "";
        for (int i = 0;i < areaCode.Length;i++)
        {
            if (Regex.IsMatch(areaCode[i].ToString(), "[0-9]"))
            {
                phonedigits = phonedigits + areaCode[i];
            }
             
        }
        String regexp = "";
        for (int i = 0;i < phonedigits.Length;i++)
        {
            if (i < 1)
            {
                regexp = "^[^0-9]?";
            }
             
            //Allows phone to start with "("
            regexp += phonedigits[i] + "[^0-9]*";
        }
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("CustReferenceNum");
        table.Columns.Add("PatNum");
        table.Columns.Add("FName");
        table.Columns.Add("LName");
        table.Columns.Add("HmPhone");
        table.Columns.Add("State");
        table.Columns.Add("City");
        table.Columns.Add("Zip");
        table.Columns.Add("Specialty");
        table.Columns.Add("age");
        table.Columns.Add("SuperFamily");
        table.Columns.Add("DateMostRecent");
        table.Columns.Add("TimesUsed");
        table.Columns.Add("IsBadRef");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT * FROM (SELECT cr.*,p.LName,p.FName,p.HmPhone,p.State,p.City,p.Zip,p.Birthdate,pf.FieldValue,\r\n" + 
        "\t\t\t\t(SELECT COUNT(*) FROM patient tempp WHERE tempp.SuperFamily=p.SuperFamily AND tempp.SuperFamily<>0) AS SuperFamily,\r\n" + 
        "\t\t\t\t(SELECT COUNT(*) FROM custrefentry tempcre WHERE tempcre.PatNumRef=cr.PatNum) AS TimesUsed\r\n" + 
        "\t\t\t\tFROM custreference cr\r\n" + 
        "\t\t\t\tINNER JOIN patient p ON cr.PatNum=p.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN patfield pf ON cr.PatNum=pf.PatNum AND pf.FieldName=\'Specialty\' \r\n" + 
        "\t\t\t\tWHERE cr.CustReferenceNum<>0 ";
        //This just makes the following AND statements brainless.
        //excludes deleted, etc.
        command += "AND (p.PatStatus=" + POut.int(((Enum)PatientStatus.Patient).ordinal()) + " OR p.PatStatus=" + POut.int(((Enum)PatientStatus.NonPatient).ordinal()) + ") " + billingSnippet;
        if (age > 0)
        {
            command += "AND p.Birthdate <" + POut.Date(DateTime.Now.AddYears(-age)) + " ";
        }
         
        if (!StringSupport.equals(regexp, ""))
        {
            command += "AND (p.HmPhone REGEXP '" + POut.string(regexp) + "' )";
        }
         
        command += (lname.Length > 0 ? "AND (p.LName LIKE '" + POut.string(lname) + "%' OR p.Preferred LIKE '" + POut.string(lname) + "%') " : "") + (fname.Length > 0 ? "AND (p.FName LIKE '" + POut.string(fname) + "%' OR p.Preferred LIKE '" + POut.string(fname) + "%') " : "") + (city.Length > 0 ? "AND p.City LIKE '" + POut.string(city) + "%' " : "") + (state.Length > 0 ? "AND p.State LIKE '" + POut.string(state) + "%' " : "") + (zip.Length > 0 ? "AND p.Zip LIKE '" + POut.string(zip) + "%' " : "") + (patnum.Length > 0 ? "AND p.PatNum LIKE '" + POut.string(patnum) + "%' " : "") + (specialty.Length > 0 ? "AND pf.FieldValue LIKE '" + POut.string(specialty) + "%' " : "") + (showBadRefs ? "" : "AND cr.IsBadRef=0 ") + (showGuarOnly ? "AND p.Guarantor=p.PatNum" : "");
        if (limit)
        {
            command = DbHelper.limitOrderBy(command,40);
        }
         
        command += ") AS tempcustref WHERE PatNum<>0 ";
        //Once again just making AND statements brainless.
        if (superFam > 0)
        {
            command += "AND SuperFamily>" + POut.int(superFam) + " ";
        }
         
        if (showUsed)
        {
            command += "AND TimesUsed>0 ";
        }
         
        DataTable rawtable = Db.getTable(command);
        for (int i = 0;i < rawtable.Rows.Count;i++)
        {
            row = table.NewRow();
            row["CustReferenceNum"] = rawtable.Rows[i]["CustReferenceNum"].ToString();
            row["PatNum"] = rawtable.Rows[i]["PatNum"].ToString();
            row["FName"] = rawtable.Rows[i]["FName"].ToString();
            row["LName"] = rawtable.Rows[i]["LName"].ToString();
            row["HmPhone"] = rawtable.Rows[i]["HmPhone"].ToString();
            row["State"] = rawtable.Rows[i]["State"].ToString();
            row["City"] = rawtable.Rows[i]["City"].ToString();
            row["Zip"] = rawtable.Rows[i]["Zip"].ToString();
            row["Specialty"] = rawtable.Rows[i]["FieldValue"].ToString();
            row["age"] = Patients.DateToAge(PIn.Date(rawtable.Rows[i]["Birthdate"].ToString())).ToString();
            row["SuperFamily"] = rawtable.Rows[i]["SuperFamily"].ToString();
            DateTime recentDate = PIn.DateT(rawtable.Rows[i]["DateMostRecent"].ToString());
            row["DateMostRecent"] = "";
            if (recentDate.Year > 1880)
            {
                row["DateMostRecent"] = recentDate.ToShortDateString();
            }
             
            row["TimesUsed"] = rawtable.Rows[i]["TimesUsed"].ToString();
            row["IsBadRef"] = rawtable.Rows[i]["IsBadRef"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Returns FName 'Preferred' M LName.  This is here because I get names by patnum a lot with references.
    */
    public static String getCustNameFL(long patNum) throws Exception {
        //Calls to the db happen in the other s classes.
        Patient pat = Patients.getLim(patNum);
        return Patients.getNameFL(pat.LName,pat.FName,pat.Preferred,pat.MiddleI);
    }

}


