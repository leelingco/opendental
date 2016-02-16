//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.SubstitutionCondition;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;

/**
* A list setup ahead of time with all the procedure codes used by the office.  Every procedurelog entry which is attached to a patient is also linked to this table.
*/
public class ProcedureCode  extends TableBase 
{
    /**
    * Primary Key.  This happened in version 4.8.7.
    */
    public long CodeNum = new long();
    /**
    * Was Primary key, but now CodeNum is primary key.  Can hold dental codes, medical codes, custom codes, etc.
    */
    public String ProcCode = new String();
    /**
    * The main description.
    */
    public String Descript = new String();
    /**
    * Abbreviated description.
    */
    public String AbbrDesc = new String();
    /**
    * X's and /'s describe Dr's time and assistant's time in the same increments as the user has set.
    */
    public String ProcTime = new String();
    /**
    * FK to definition.DefNum.  The category that this code will be found under in the search window.  Has nothing to do with insurance categories.
    */
    //[XmlIgnore]
    public long ProcCat = new long();
    /**
    * Enum:TreatmentArea
    */
    public TreatmentArea TreatArea = TreatmentArea.None;
    /**
    * If true, do not usually bill this procedure to insurance.
    */
    public boolean NoBillIns = new boolean();
    /**
    * True if Crown,Bridge,Denture, or RPD. Forces user to enter Initial or Replacement and Date.
    */
    public boolean IsProsth = new boolean();
    /**
    * The default procedure note to copy when marking complete.
    */
    //[XmlIgnore]
    public String DefaultNote = new String();
    /**
    * Identifies hygiene procedures so that the correct provider can be selected.
    */
    public boolean IsHygiene = new boolean();
    /**
    * No longer used.
    */
    //[XmlIgnore]
    public int GTypeNum = new int();
    /**
    * For Medicaid.  There may be more later.
    */
    //[XmlIgnore]
    public String AlternateCode1 = new String();
    /**
    * FK to procedurecode.ProcCode.  The actual medical code that is being referenced must be setup first.  Anytime a procedure it added, this medical code will also be added to that procedure.  The user can change it in procedurelog.
    */
    //[XmlIgnore]
    public String MedicalCode = new String();
    /**
    * Used by some offices even though no user interface built yet.  SalesTaxPercentage has been added to the preference table to store the amount of sales tax to apply as an adjustment attached to a procedurelog entry.
    */
    //[XmlIgnore]
    public boolean IsTaxed = new boolean();
    /**
    * Enum:ToothPaintingType
    */
    public ToothPaintingType PaintType = ToothPaintingType.None;
    /**
    * If set to anything but 0, then this will override the graphic color for all procedures of this code, regardless of the status.
    */
    public Color GraphicColor = new Color();
    /**
    * When creating treatment plans, this description will be used instead of the technical description.
    */
    //[XmlIgnore]
    public String LaymanTerm = new String();
    /**
    * Only used in Canada.  Set to true if this procedure code is only used as an adjunct to track the lab fee.
    */
    //[XmlIgnore]
    public boolean IsCanadianLab = new boolean();
    /**
    * This is true if this procedure code existed before ADA code distribution changed at version 4.8, false otherwise.
    */
    //[XmlIgnore]
    public boolean PreExisting = new boolean();
    /**
    * Support for Base Units for a Code (like anesthesia).  Should normally be zero.
    */
    //[XmlIgnore]
    public int BaseUnits = new int();
    /**
    * FK to procedurecode.ProcCode.  Used for posterior composites because insurance substitutes the amalgam code when figuring the coverage.
    */
    //[XmlIgnore]
    public String SubstitutionCode = new String();
    /**
    * Enum:SubstitutionCondition Used so that posterior composites only substitute if tooth is molar.  Ins usually pays for premolar composites.
    */
    //[XmlIgnore]
    public SubstitutionCondition SubstOnlyIf = SubstitutionCondition.Always;
    /**
    * Last datetime that this row was inserted or updated.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Set to true if the procedure takes more than one appointment to complete.
    */
    public boolean IsMultiVisit = new boolean();
    /**
    * 11 digits or blank, enforced.  For 837I
    */
    public String DrugNDC = new String();
    /**
    * Gets copied to procedure.RevCode.  For 837I
    */
    public String RevenueCodeDefault = new String();
    /**
    * FK to provider.ProvNum.  0 for none. Otherwise, this provider will be used for this code instead of the normal provider.
    */
    public long ProvNumDefault = new long();
    /**
    * Not a database column.  Only used for xml import function.
    */
    private String procCatDescript = new String();
    public ProcedureCode() throws Exception {
        ProcTime = "/X/";
        //procCode.ProcCat=DefC.Short[(long)DefCat.ProcCodeCats][0].DefNum;
        GraphicColor = Color.FromArgb(0);
    }

    /**
    * Used only for serialization purposes
    */
    public int getGraphicColorXml() throws Exception {
        return GraphicColor.ToArgb();
    }

    public void setGraphicColorXml(int value) throws Exception {
        GraphicColor = Color.FromArgb(value);
    }

    //[XmlElement(DataType="string",ElementName="ProcCatDescript")]
    public String getProcCatDescript() throws Exception {
        if (ProcCat == 0)
        {
            return procCatDescript;
        }
         
        return DefC.getName(DefCat.ProcCodeCats,ProcCat);
    }

    //only used in xml import. We have an incomplete object.
    public void setProcCatDescript(String value) throws Exception {
        procCatDescript = value;
    }

    /**
    * Returns a copy of this Procedurecode.
    */
    public ProcedureCode copy() throws Exception {
        return (ProcedureCode)this.MemberwiseClone();
    }

}


