//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Mobile.Patientm;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.TableBase;

/**
* One row for each patient.  Unlike main program, this does not include deleted patients.  Primary key is first two fields combined.
*/
public class Patientm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long PatNum = new long();
    /**
    * Last name.
    */
    public String LName = new String();
    /**
    * First name.
    */
    public String FName = new String();
    /**
    * Middle initial or name.
    */
    public String MiddleI = new String();
    /**
    * Preferred name, aka nickname.
    */
    public String Preferred = new String();
    /**
    * Enum:PatientStatus
    */
    public PatientStatus PatStatus = PatientStatus.Patient;
    /**
    * Enum:PatientGender
    */
    public PatientGender Gender = PatientGender.Male;
    /**
    * Enum:PatientPosition Marital status would probably be a better name for this column.
    */
    public PatientPosition Position = PatientPosition.Single;
    /**
    * Age is not stored in the database.  Age is always calculated as needed from birthdate.
    */
    public DateTime Birthdate = new DateTime();
    /**
    * .
    */
    public String Address = new String();
    /**
    * Optional second address line.
    */
    public String Address2 = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * 2 Char in USA
    */
    public String State = new String();
    /**
    * Postal code.  For Canadian claims, it must be ANANAN.  No validation gets done except there.
    */
    public String Zip = new String();
    /**
    * Home phone. Includes any punctuation
    */
    public String HmPhone = new String();
    /**
    * .
    */
    public String WkPhone = new String();
    /**
    * .
    */
    public String WirelessPhone = new String();
    /**
    * FK to patientm.PatNum.  Head of household.
    */
    public long Guarantor = new long();
    /**
    * Derived from Birthdate.  Not in the database table.
    */
    public int Age = new int();
    /**
    * .
    */
    public String Email = new String();
    /**
    * Address or phone note.  Will probably limit to first 255 characters of patient.AddrNote.
    */
    public String AddrNote = new String();
    /**
    * FK to clinic.ClinicNum. Can be zero if not attached to a clinic or no clinics set up.
    */
    public long ClinicNum = new long();
    /**
    * Insurance Estimate for entire family.
    */
    public double InsEst = new double();
    /**
    * Total balance for entire family before insurance estimate.  Not the same as the sum of the 4 aging balances because this can be negative.  Only stored with guarantor.
    */
    public double BalTotal = new double();
    /**
    * Enum:ContactMethod
    */
    public ContactMethod PreferContactMethod = ContactMethod.None;
    /**
    * If this is blank, then the chart info for this patient will not be uploaded.  If this has a value, then this is the password that a patient must use to access their info online.
    */
    public String OnlinePassword = new String();
    /**
    * Returns a copy of this Patientm.
    */
    public Patientm copy() throws Exception {
        return (Patientm)this.MemberwiseClone();
    }

    public String toString() {
        try
        {
            return "Patient: " + getNameLF();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * LName, 'Preferred' FName M
    */
    public String getNameLF() throws Exception {
        return Patients.getNameLF(LName,FName,Preferred,MiddleI);
    }

    /**
    * FName 'Preferred' M LName
    */
    public String getNameFL() throws Exception {
        return Patients.getNameFL(LName,FName,Preferred,MiddleI);
    }

    /**
    * FName M LName
    */
    public String getNameFLnoPref() throws Exception {
        return Patients.getNameFLnoPref(LName,FName,MiddleI);
    }

    /**
    * FName/Preferred LName
    */
    public String getNameFirstOrPrefL() throws Exception {
        return Patients.getNameFirstOrPrefL(LName,FName,Preferred);
    }

    /**
    * FName/Preferred M. LName
    */
    public String getNameFirstOrPrefML() throws Exception {
        return Patients.getNameFirstOrPrefML(LName,FName,Preferred,MiddleI);
    }

    //<summary>Title FName M LName</summary>
    //public string GetNameFLFormal() {
    //	return Patients.GetNameFLFormal(LName,FName,MiddleI,Title);
    //}
    /**
    * Includes preferred.
    */
    public String getNameFirst() throws Exception {
        return Patients.getNameFirst(FName,Preferred);
    }

    /**
    * 
    */
    public String getNameFirstOrPreferred() throws Exception {
        return Patients.getNameFirstOrPreferred(FName,Preferred);
    }

}


