//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PIn;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Family   
{
    /**
    * 
    */
    public Family() throws Exception {
    }

    /**
    * List of patients in the family.
    */
    public Patient[] ListPats = new Patient[]();
    /**
    * Tries to get the LastName,FirstName of the patient from this family.  If not found, then gets the name from the database.
    */
    public String getNameInFamLF(long myPatNum) throws Exception {
        for (int i = 0;i < ListPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ListPats[i].PatNum == myPatNum)
            {
                return ListPats[i].GetNameLF();
            }
             
        }
        return getLim(myPatNum).getNameLF();
    }

    /**
    * Gets last, (preferred) first middle
    */
    public String getNameInFamLFI(int myi) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retStr = "";
        if (StringSupport.equals(ListPats[myi].Preferred, ""))
        {
            retStr = ListPats[myi].LName + ", " + ListPats[myi].FName + " " + ListPats[myi].MiddleI;
        }
        else
        {
            retStr = ListPats[myi].LName + ", '" + ListPats[myi].Preferred + "' " + ListPats[myi].FName + " " + ListPats[myi].MiddleI;
        } 
        return retStr;
    }

    /**
    * Gets a formatted name from the family list.  If the patient is not in the family list, then it gets that info from the database.
    */
    public String getNameInFamFL(long myPatNum) throws Exception {
        for (int i = 0;i < ListPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ListPats[i].PatNum == myPatNum)
            {
                return ListPats[i].GetNameFL();
            }
             
        }
        return getLim(myPatNum).getNameFL();
    }

    /**
    * Gets a formatted name from the family list.  If the patient is not in the family list, then it gets that info from the database.
    */
    public String getNameInFamFLnoPref(long myPatNum) throws Exception {
        for (int i = 0;i < ListPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ListPats[i].PatNum == myPatNum)
            {
                return ListPats[i].GetNameFLnoPref();
            }
             
        }
        return getLim(myPatNum).getNameFLnoPref();
    }

    /**
    * Gets (preferred)first middle last
    */
    public String getNameInFamFLI(int myi) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retStr = "";
        if (StringSupport.equals(ListPats[myi].Preferred, ""))
        {
            retStr = ListPats[myi].FName + " " + ListPats[myi].MiddleI + " " + ListPats[myi].LName;
        }
        else
        {
            retStr = "'" + ListPats[myi].Preferred + "' " + ListPats[myi].FName + " " + ListPats[myi].MiddleI + " " + ListPats[myi].LName;
        } 
        return retStr;
    }

    /**
    * Gets first name from the family list.  If the patient is not in the family list, then it gets that info from the database.  Includes preferred.
    */
    public String getNameInFamFirst(long myPatNum) throws Exception {
        for (int i = 0;i < ListPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ListPats[i].PatNum == myPatNum)
            {
                return ListPats[i].GetNameFirst();
            }
             
        }
        return getLim(myPatNum).getNameFirst();
    }

    /**
    * The index of the patient within the family.  Returns -1 if not found.
    */
    public int getIndex(long patNum) throws Exception {
        for (int i = 0;i < ListPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ListPats[i].PatNum == patNum)
            {
                return i;
            }
             
        }
        return -1;
    }

    /**
    * Gets a copy of a specific patient from within the family. Does not make a call to the database.
    */
    public Patient getPatient(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        Patient retVal = null;
        for (int i = 0;i < ListPats.Length;i++)
        {
            if (ListPats[i].PatNum == patNum)
            {
                retVal = ListPats[i].Copy();
                break;
            }
             
        }
        return retVal;
    }

    /**
    * Duplicate of the same class in Patients.  Gets nine of the most useful fields from the db for the given patnum.
    */
    public static Patient getLim(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        if (patNum == 0)
        {
            return new Patient();
        }
         
        String command = "SELECT PatNum,LName,FName,MiddleI,Preferred,CreditType,Guarantor,HasIns,SSN " + "FROM patient " + "WHERE PatNum = '" + patNum.ToString() + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return new Patient();
        }
         
        Patient Lim = new Patient();
        Lim.PatNum = PIn.Long(table.Rows[0][0].ToString());
        Lim.LName = PIn.String(table.Rows[0][1].ToString());
        Lim.FName = PIn.String(table.Rows[0][2].ToString());
        Lim.MiddleI = PIn.String(table.Rows[0][3].ToString());
        Lim.Preferred = PIn.String(table.Rows[0][4].ToString());
        Lim.CreditType = PIn.String(table.Rows[0][5].ToString());
        Lim.Guarantor = PIn.Long(table.Rows[0][6].ToString());
        Lim.HasIns = PIn.String(table.Rows[0][7].ToString());
        Lim.SSN = PIn.String(table.Rows[0][8].ToString());
        return Lim;
    }

}


