//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.TableBase;

/**
* Since we can send e-claims to multiple clearinghouses, this table keeps track of each clearinghouse.  Will eventually be used for individual carriers as well if they accept
*/
public class Clearinghouse  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClearinghouseNum = new long();
    /**
    * Description of this clearinghouse
    */
    public String Description = new String();
    /**
    * The path to export the X12 file to. \ is now optional.
    */
    public String ExportPath = new String();
    /**
    * A list of all payors which should have claims sent to this clearinghouse. Comma delimited with no spaces.  Not necessary if IsDefault.
    */
    public String Payors = new String();
    /**
    * Enum:ElectronicClaimFormat The format of the file that gets sent electronically.
    */
    public ElectronicClaimFormat Eformat = ElectronicClaimFormat.None;
    /**
    * Sender ID Qualifier. Usually ZZ, sometimes 30. Seven other values are allowed as specified in X12 document, but probably never used.
    */
    public String ISA05 = new String();
    /**
    * Used in ISA06, GS02, 1000A NM1, and 1000A PER.  If blank, then 810624427 is used to indicate Open Dental.
    */
    public String SenderTIN = new String();
    /**
    * Receiver ID Qualifier.  Usually ZZ, sometimes 30. Seven other values are allowed as specified in X12 document, but probably never used.
    */
    public String ISA07 = new String();
    /**
    * Receiver ID. Also used in GS03. Provided by clearinghouse. Examples: BCBSGA or 0135WCH00(webMD)
    */
    public String ISA08 = new String();
    /**
    * "P" for Production or "T" for Test.
    */
    public String ISA15 = new String();
    /**
    * Password is usually combined with the login ID for user validation.
    */
    public String Password = new String();
    /**
    * The path that all incoming response files will be saved to. \ is now optional.
    */
    public String ResponsePath = new String();
    /**
    * Enum:EclaimsCommBridge  One of the included hard-coded communications briges.  Or none to just create the claim files without uploading.
    */
    public EclaimsCommBridge CommBridge = EclaimsCommBridge.None;
    /**
    * If applicable, this is the name of the client program to launch.  It is even used by the hard-coded comm bridges, because the user may have changed the installation directory or exe name.
    */
    public String ClientProgram = new String();
    /**
    * Each clearinghouse increments their batch numbers by one each time a claim file is sent.  User never sees this number.  Maxes out at 999, then loops back to 1.  This field must NOT be cached and must be ignored in the code except where it explicitly retrieves it from the db.  Defaults to 0 for brand new clearinghouses, which causes the first batch to go out as #1.
    */
    public int LastBatchNumber = new int();
    /**
    * Was not used.  1,2,3,or 4. The port that the modem is connected to if applicable. Always uses 9600 baud and standard settings. Will crash if port or modem not valid.
    */
    public byte ModemPort = new byte();
    /**
    * A clearinghouse usually has a login ID that is used with the password in order to access the remote server.  This value is not usualy used within the actual claim.
    */
    public String LoginID = new String();
    /**
    * Used in 1000A NM1 and 1000A PER.  But if SenderTIN is blank, then OPEN DENTAL SOFTWARE is used instead.
    */
    public String SenderName = new String();
    /**
    * Used in 1000A PER.  But if SenderTIN is blank, then 8776861248 is used instead.  10 digit phone is required by WebMD and is universally assumed, so for now, this must be either blank or 10 digits.
    */
    public String SenderTelephone = new String();
    /**
    * Usually the same as ISA08, but at least one clearinghouse uses a different number here.
    */
    public String GS03 = new String();
    /**
    * Authorization information. Almost always blank. Used for Denti-Cal.
    */
    public String ISA02 = new String();
    /**
    * Security information. Almost always blank. Used for Denti-Cal.
    */
    public String ISA04 = new String();
    /**
    * X12 component element separator. Two digit hexadecimal string representing an ASCII character or blank. Usually blank, implying 3A which represents ':'. For Denti-Cal, hexadecimal value 22 must be used, corresponding to '"'.
    */
    public String ISA16 = new String();
    /**
    * X12 data element separator. Two digit hexadecimal string representing an ASCII character or blank. Usually blank, implying 2A which represents '*'. For Denti-Cal, hexadecimal value 1D must be used, corresponding to the "group separator" character which has no visual representation.
    */
    public String SeparatorData = new String();
    /**
    * X12 segment terminator. Two digit hexadecimal string representing an ASCII character or blank. Usually blank, implying 7E which represents '~'. For Denti-Cal, hexadecimal value 1C must be used, corresponding to the "file separator" character which has no visual representation.
    */
    public String SeparatorSegment = new String();
    public Clearinghouse() throws Exception {
    }

}


