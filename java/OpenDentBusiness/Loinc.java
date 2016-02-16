//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Loinc;
import OpenDentBusiness.TableBase;

/**
* Logical Observation Identifiers Names and Codes (LOINC) used to identify both lab panels and lab results. Widths specified are from LOINC documentation and may not represent length of fields in the Open Dental Database.
*/
public class Loinc  extends TableBase 
{
    /**
    * Primary key. Internal use only.
    */
    public long LoincNum = new long();
    /**
    * #EULA REQUIRED# Also called LOINC_NUM in the official LOINCDB. Width-10. LOINC244 column 1.
    */
    public String LoincCode = new String();
    //LOINC_NUM;
    /**
    * #EULA REQUIRED# First Major axis:component or analyte. Width-255. LOINC244 column 2.
    */
    public String Component = new String();
    /**
    * #EULA REQUIRED# Second major axis:property observed (e.g., mass vs. substance). Width-30. LOINC244 column 3.
    */
    public String PropertyObserved = new String();
    //Property;
    /**
    * #EULA REQUIRED# Third major axis:timing of the measurement (e.g., point in time vs 24 hours). Width-15. LOINC244 column 4.
    */
    public String TimeAspct = new String();
    //Time_Aspct;
    /**
    * #EULA REQUIRED# Fourth major axis:type of specimen or system (e.g., serum vs urine). Width-100 LOINC244. column 5.
    */
    public String SystemMeasured = new String();
    //System;
    /**
    * #EULA REQUIRED# Fifth major axis:scale of measurement (e.g., qualitative vs. quantitative). Width-30. LOINC244 column 6.
    */
    public String ScaleType = new String();
    //Scale_Typ;
    /**
    * #EULA REQUIRED# Sixth major axis:method of measurement. Width-50. LOINC244 column 7.
    */
    public String MethodType = new String();
    //Method_Typ;
    /**
    * #EULA REQUIRED# Width-10. LOINC244 column 13.ACTIVE = Concept is active. Use at will.TRIAL = Concept is experimental in nature. Use with caution as the concept and associated attributes may change. DISCOURAGED = Concept is not recommended for current use. New mappings to this concept are discouraged; although existing may mappings may continue to be valid in context. Wherever  possible, the superseding concept is indicated in the MAP_TO field in the MAP_TO table (see Table 28b) and should be used instead. DEPRECATED = Concept is deprecated. Concept should not be used, but it is retained in LOINC for historical purposes. Wherever possible, the superseding concept is indicated in the MAP_TO field (see Table 28b) and should be used both for new mappings and updating existing implementations..
    */
    public String StatusOfCode = new String();
    //Status;
    /**
    * #EULA REQUIRED# Introduced in version 2.07, this field is a concatenation of the fully specified LOINC name. The field width may change in a future release. Width 40. LOINC244 column 29.
    */
    public String NameShort = new String();
    //ShortName;
    /**
    * 1=Laboratory class; 2=Clinical class; 3=Claims attachments; 4=Surveys. LOINC244 column 16.
    */
    public String ClassType = new String();
    /**
    * Y/N field that indicates that units are required when this LOINC is included as an OBX segment in a HIPAA attachment. LOINC244 column 26.
    */
    public boolean UnitsRequired = new boolean();
    /**
    * Defines term as order only, observation only, or both. A fourth category, Subset, is used for terms that are subsets of a panel but do not represent a package that is known to be orderable we have defined them only to make it easier to maintain panels or other sets within the LOINC construct. LOINC244 column 30.
    */
    public String OrderObs = new String();
    /**
    * A value in this field means that the content should be delivered in the named field/subfield of the HL7 message. When NULL, the data for this data element should be sent in an OBX segment with this LOINC code stored in OBX-3 and with the value in the OBX-5. Width 50. LOINC244 column 32.
    */
    public String HL7FieldSubfieldID = new String();
    //HL7_Field_Subfield_ID;
    /**
    * External copyright holders copyright notice for this LOINC code. LOINC244 column 33.  Colwidth=4000.
    */
    public String ExternalCopyrightNotice = new String();
    //External_Copyright_Notice;
    /**
    * This field contains the LOINC term in a more readable format than the fully specified name. The long common names have been created via a table driven algorithmic process. Most abbreviations and acronyms that are used in the LOINC database have been fully spelled out in English. Width 255. LOINC244 column 35.
    */
    public String NameLongCommon = new String();
    //LONG_COMMON_NAME;
    /**
    * The Unified Code for Units of Measure (UCUM) is a code system intended to include all units of measures being contemporarily used in international science, engineering, and business. (www.unitsofmeasure.org ) This field contains example units of measures for this term expressed as UCUM units. Width 255. LOINC244 column 1.
    */
    public String UnitsUCUM = new String();
    //Example_UCUM_Units;
    /**
    * Ranking of approximately 2000 common tests performed by laboratories in USA. LOINC244 column 45.
    */
    public int RankCommonTests = new int();
    //Common_Test_Rank;
    /**
    * Ranking of approximately 300 common orders performed by laboratories in USA. LOINC244 column 46.
    */
    public int RankCommonOrders = new int();
    //Common_Order_Rank;
    /**
    * 
    */
    public Loinc clone() {
        try
        {
            return (Loinc)this.MemberwiseClone();
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

}


