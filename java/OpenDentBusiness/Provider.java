//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Provider;
import OpenDentBusiness.TableBase;

/**
* A provider is usually a dentist or a hygienist.  But a provider might also be a denturist, a dental student, or a dental hygiene student.  A provider might also be a 'dummy', used only for billing purposes or for notes in the Appointments module.  There is no limit to the number of providers that can be added.
*/
public class Provider  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProvNum = new long();
    /**
    * Abbreviation.  There was a limit of 5 char before version 5.4.  The new limit is 255 char.  This will allow more elegant solutions to various problems.  Providers will no longer be referred to by FName and LName.  Abbr is used as a human readable primary key.
    */
    public String Abbr = new String();
    /**
    * Order that provider will show in lists. Was 1-based, now 0-based.
    */
    public int ItemOrder = new int();
    /**
    * Last name.
    */
    public String LName = new String();
    /**
    * First name.
    */
    public String FName = new String();
    /**
    * Middle inital or name.
    */
    public String MI = new String();
    /**
    * eg. DMD or DDS. Was 'title' in previous versions.
    */
    public String Suffix = new String();
    /**
    * FK to feesched.FeeSchedNum.
    */
    public long FeeSched = new long();
    /**
    * Enum:DentalSpecialty
    */
    public DentalSpecialty Specialty = DentalSpecialty.General;
    /**
    * or TIN.  No punctuation
    */
    public String SSN = new String();
    /**
    * can include punctuation
    */
    public String StateLicense = new String();
    /**
    * .
    */
    public String DEANum = new String();
    /**
    * True if hygienist.
    */
    public boolean IsSecondary = new boolean();
    //
    /**
    * Color that shows in appointments
    */
    public Color ProvColor = new Color();
    /**
    * If true, provider will not show on any lists
    */
    public boolean IsHidden = new boolean();
    /**
    * True if the SSN field is actually a Tax ID Num
    */
    public boolean UsingTIN = new boolean();
    /**
    * No longer used since each state assigns a different ID.  Use the providerident instead which allows you to assign a different BCBS ID for each Payor ID.
    */
    public String BlueCrossID = new String();
    /**
    * Signature on file.
    */
    public boolean SigOnFile = new boolean();
    /**
    * .
    */
    public String MedicaidID = new String();
    /**
    * Color that shows in appointments as outline when highlighted.
    */
    public Color OutlineColor = new Color();
    /**
    * FK to schoolclass.SchoolClassNum Used in dental schools.  Each student is a provider.  This keeps track of which class they are in.
    */
    public long SchoolClassNum = new long();
    /**
    * US NPI, and Canadian CDA provider number.
    */
    public String NationalProvID = new String();
    /**
    * Canadian field required for e-claims.  Assigned by CDA.  It's OK to have multiple providers with the same OfficeNum.  Max length should be 4.
    */
    public String CanadianOfficeNum = new String();
    /**
    * .
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * FK to ??. Field used to set the Anesthesia Provider type. Used to filter the provider dropdowns on FormAnestheticRecord
    */
    public long AnesthProvType = new long();
    /**
    * If none of the supplied taxonomies works.  This will show on claims.
    */
    public String TaxonomyCodeOverride = new String();
    /**
    * For Canada. Set to true if CDA Net provider.
    */
    public boolean IsCDAnet = new boolean();
    /**
    * The name of this field is bad and will soon be changed to MedicalSoftID.  This allows an ID field that can be used for HL7 synch with other software.  Before this field was added, we were using prov abbreviation, which did not work well.
    */
    public String EcwID = new String();
    /**
    * Allows using ehr module for this provider.  Tied to provider fname and lname.
    */
    public String EhrKey = new String();
    /**
    * Provider medical State ID.
    */
    public String StateRxID = new String();
    /**
    * True if the provider key for this provider is set up for report access.
    */
    public boolean EhrHasReportAccess = new boolean();
    /**
    * Default is false because most providers are persons.  But some dummy providers used for practices or billing entities are not persons.  This is needed on 837s.
    */
    public boolean IsNotPerson = new boolean();
    /**
    * The state abbreviation where the state license number in the StateLicense field is legally registered.
    */
    public String StateWhereLicensed = new String();
    /**
    * FK to EmailAddress.EmailAddressNum.  Optional, can be 0.
    */
    public long EmailAddressNum = new long();
    /**
    * Used only for serialization purposes
    */
    public int getProvColorXml() throws Exception {
        return ProvColor.ToArgb();
    }

    public void setProvColorXml(int value) throws Exception {
        ProvColor = Color.FromArgb(value);
    }

    /**
    * Used only for serialization purposes
    */
    public int getOutlineColorXml() throws Exception {
        return OutlineColor.ToArgb();
    }

    public void setOutlineColorXml(int value) throws Exception {
        OutlineColor = Color.FromArgb(value);
    }

    /**
    * Returns a copy of this Provider.
    */
    public Provider copy() throws Exception {
        return (Provider)MemberwiseClone();
    }

    public Provider() throws Exception {
    }

    public Provider(long provNum, String abbr, int itemOrder, String lName, String fName, String mI, String suffix, long feeSched, DentalSpecialty specialty, String sSN, String stateLicense, String dEANum, boolean isSecondary, Color provColor, boolean isHidden, boolean usingTIN, String blueCrossID, boolean sigOnFile, String medicaidID, Color outlineColor, long schoolClassNum, String nationalProvID, String canadianOfficeNum, long anesthProvType) throws Exception {
        ProvNum = provNum;
        Abbr = abbr;
        ItemOrder = itemOrder;
        LName = lName;
        FName = fName;
        MI = mI;
        Suffix = suffix;
        FeeSched = feeSched;
        Specialty = specialty;
        SSN = sSN;
        StateLicense = stateLicense;
        DEANum = dEANum;
        IsSecondary = isSecondary;
        ProvColor = provColor;
        IsHidden = isHidden;
        UsingTIN = usingTIN;
        BlueCrossID = blueCrossID;
        SigOnFile = sigOnFile;
        MedicaidID = medicaidID;
        OutlineColor = outlineColor;
        SchoolClassNum = schoolClassNum;
        NationalProvID = nationalProvID;
        CanadianOfficeNum = canadianOfficeNum;
        //DateTStamp
        AnesthProvType = anesthProvType;
    }

    /**
    * For use in areas of the program where we have more room than just simple abbr.  Such as pick boxes in reports.  This will give Abbr - LName, FName (hidden).
    */
    public String getLongDesc() throws Exception {
        String retval = Abbr + " - " + LName + ", " + FName;
        if (IsHidden)
        {
            retval += " " + Lans.g("Providers","(hidden)");
        }
         
        return retval;
    }

    /**
    * FName MI. LName, Suffix
    */
    public String getFormalName() throws Exception {
        String retVal = FName + " " + MI;
        if (MI.Length == 1)
        {
            retVal += ".";
        }
         
        if (!StringSupport.equals(MI, ""))
        {
            retVal += " ";
        }
         
        retVal += LName;
        if (!StringSupport.equals(Suffix, ""))
        {
            retVal += ", " + Suffix;
        }
         
        return retVal;
    }

}


