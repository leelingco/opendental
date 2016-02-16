//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.X12Generator;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X12Validate;

/**
* 
*/
public class X270  extends X12object 
{
    public X270(String messageText) throws Exception {
        super(messageText);
    }

    /**
    * In progress.  Probably needs a different name.  Info must be validated first.
    */
    public static String generateMessageText(Clearinghouse clearhouse, Carrier carrier, Provider billProv, Clinic clinic, InsPlan insPlan, Patient subscriber, InsSub insSub) throws Exception {
        int batchNum = Clearinghouses.getNextBatchNumber(clearhouse);
        String groupControlNumber = batchNum.ToString();
        //Must be unique within file.  We will use batchNum
        int transactionNum = 1;
        StringBuilder strb = new StringBuilder();
        //Interchange Control Header
        //ISA01,ISA02: 00 + 10 spaces
        //ISA03,ISA04: 00 + 10 spaces
        //ISA05: Sender ID type: ZZ=mutually defined. 30=TIN. Validated
        //ISA06: Sender ID(TIN). Or might be TIN of Open Dental
        //ISA07: Receiver ID type: ZZ=mutually defined. 30=TIN. Validated
        //ISA08: Receiver ID. Validated to make sure length is at least 2.
        //ISA09: today's date
        //ISA10: current time
        //ISA11 and ISA12.
        //ISA13: interchange control number, right aligned:
        //ISA14: no acknowledgment requested
        strb.AppendLine("ISA*00*          *" + "00*          *" + clearhouse.ISA05 + "*" + X12Generator.getISA06(clearhouse) + "*" + clearhouse.ISA07 + "*" + sout(clearhouse.ISA08,15,15) + "*" + DateTime.Today.ToString("yyMMdd") + "*" + DateTime.Now.ToString("HHmm") + "*" + "U*00401*" + batchNum.ToString().PadLeft(9, '0') + "*" + "0*" + clearhouse.ISA15 + "*" + ":~");
        //ISA15: T=Test P=Production. Validated.
        //ISA16: use ':'
        //Functional Group Header
        //GS01: HS for 270 benefit inquiry
        //GS02: Senders Code. Sometimes Jordan Sparks.  Sometimes the sending clinic.
        //GS03: Application Receiver's Code
        //GS04: today's date
        //GS05: current time
        strb.AppendLine("GS*HS*" + X12Generator.getGS02(clearhouse) + "*" + sout(clearhouse.GS03,15,2) + "*" + DateTime.Today.ToString("yyyyMMdd") + "*" + DateTime.Now.ToString("HHmm") + "*" + groupControlNumber + "*" + "X*" + "004010X092~");
        //GS06: Group control number. Max length 9. No padding necessary.
        //GS07: X
        //GS08: Version
        //Beginning of transaction--------------------------------------------------------------------------------
        int seg = 0;
        //count segments for the ST-SE transaction
        //Transaction Set Header
        //ST02 Transact. control #. Must be unique within ISA
        seg++;
        //ST01
        strb.AppendLine("ST*270*" + transactionNum.ToString().PadLeft(4, '0') + "~");
        //ST02
        seg++;
        //BHT02: 13=request
        //BHT03. Can be same as ST02
        //BHT04: Date
        strb.AppendLine("BHT*0022*13*" + transactionNum.ToString().PadLeft(4, '0') + "*" + DateTime.Now.ToString("yyyyMMdd") + "*" + DateTime.Now.ToString("HHmmss") + "~");
        //BHT05: Time, BHT06: not used
        //HL Loops-----------------------------------------------------------------------------------------------
        int HLcount = 1;
        //2000A HL: Information Source--------------------------------------------------------------------------
        seg++;
        strb.AppendLine("HL*" + HLcount.ToString() + "*" + "*" + "20*" + "1~");
        //HL01: Heirarchical ID.  Here, it's always 1.
        //HL02: No parent. Not used
        //HL03: Heirarchical level code. 20=Information source
        //HL04: Heirarchical child code. 1=child HL present
        //2100A NM1
        seg++;
        //NM101: PR=Payer
        //NM102: 2=Non person
        //NM103: Name Last.
        //NM104-07 not used
        //NM108: PI=PayorID
        strb.AppendLine("NM1*PR*" + "2*" + sout(carrier.CarrierName,35) + "*" + "****" + "PI*" + sout(carrier.ElectID,80,2) + "~");
        //NM109: PayorID. Validated to be at least length of 2.
        HLcount++;
        //2000B HL: Information Receiver------------------------------------------------------------------------
        seg++;
        strb.AppendLine("HL*" + HLcount.ToString() + "*" + "1*" + "21*" + "1~");
        //HL01: Heirarchical ID.  Here, it's always 2.
        //HL02: Heirarchical parent id number.  1 in this simple message.
        //HL03: Heirarchical level code. 21=Information receiver
        //HL04: Heirarchical child code. 1=child HL present
        seg++;
        //2100B NM1: Information Receiver Name
        //NM101: 1P=Provider
        //NM102: 1=person,2=non-person
        //NM103: Last name
        //NM104: First name
        //NM105: Middle name
        //NM106: not used
        //NM107: Name suffix. not used
        //NM108: ID code qualifier. 24=EIN. 34=SSN, XX=NPI
        strb.AppendLine("NM1*1P*" + (billProv.IsNotPerson ? "2" : "1") + "*" + sout(billProv.LName,35) + "*" + sout(billProv.FName,25) + "*" + sout(billProv.MI,25,1) + "*" + "*" + "*" + "XX*" + sout(billProv.NationalProvID,80) + "~");
        //NM109: ID code. NPI validated
        //2100B REF: Information Receiver ID
        seg++;
        strb.Append("REF*");
        if (billProv.UsingTIN)
        {
            strb.Append("TJ*");
        }
        else
        {
            //REF01: qualifier. TJ=Federal TIN
            //SSN
            strb.Append("SY*");
        } 
        //REF01: qualifier. SY=SSN
        strb.AppendLine(sout(billProv.SSN,30) + "~");
        //REF02: ID
        //2100B N3: Information Receiver Address
        seg++;
        if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
        {
            strb.Append("N3*" + sout(PrefC.getString(PrefName.PracticeBillingAddress),55));
        }
        else //N301: Address
        if (clinic == null)
        {
            strb.Append("N3*" + sout(PrefC.getString(PrefName.PracticeAddress),55));
        }
        else
        {
            //N301: Address
            strb.Append("N3*" + sout(clinic.Address,55));
        }  
        //N301: Address
        if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
        {
            if (StringSupport.equals(PrefC.getString(PrefName.PracticeBillingAddress2), ""))
            {
                strb.AppendLine("~");
            }
            else
            {
                //N302: Address2. Optional.
                strb.AppendLine("*" + sout(PrefC.getString(PrefName.PracticeBillingAddress2),55) + "~");
            } 
        }
        else if (clinic == null)
        {
            if (StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
            {
                strb.AppendLine("~");
            }
            else
            {
                //N302: Address2. Optional.
                strb.AppendLine("*" + sout(PrefC.getString(PrefName.PracticeAddress2),55) + "~");
            } 
        }
        else
        {
            if (StringSupport.equals(clinic.Address2, ""))
            {
                strb.AppendLine("~");
            }
            else
            {
                //N302: Address2. Optional.
                strb.AppendLine("*" + sout(clinic.Address2,55) + "~");
            } 
        }  
        //2100B N4: Information Receiver City/State/Zip
        seg++;
        if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
        {
            //N401: City
            //N402: State
            strb.AppendLine("N4*" + sout(PrefC.getString(PrefName.PracticeBillingCity),30) + "*" + sout(PrefC.getString(PrefName.PracticeBillingST),2) + "*" + Sout(PrefC.getString(PrefName.PracticeBillingZip).Replace("-", ""), 15) + "~");
        }
        else //N403: Zip
        if (clinic == null)
        {
            //N401: City
            //N402: State
            strb.AppendLine("N4*" + sout(PrefC.getString(PrefName.PracticeCity),30) + "*" + sout(PrefC.getString(PrefName.PracticeST),2) + "*" + Sout(PrefC.getString(PrefName.PracticeZip).Replace("-", ""), 15) + "~");
        }
        else
        {
            //N403: Zip
            //N401: City
            //N402: State
            strb.AppendLine("N4*" + sout(clinic.City,30) + "*" + sout(clinic.State,2) + "*" + Sout(clinic.Zip.Replace("-", ""), 15) + "~");
        }  
        //N403: Zip
        //2100B PRV: Information Receiver Provider Info
        seg++;
        //PRV*PE*ZZ*1223G0001X~
        //PRV01: Provider Code. PE=Performing.  There are many other choices.
        //PRV02: ZZ=Mutually defined = health care provider taxonomy code
        strb.AppendLine("PRV*PE*" + "ZZ*" + X12Generator.getTaxonomy(billProv) + "~");
        //PRV03: Specialty code
        HLcount++;
        //2000C HL: Subscriber-----------------------------------------------------------------------------------
        seg++;
        strb.AppendLine("HL*" + HLcount.ToString() + "*" + "2*" + "22*" + "0~");
        //HL01: Heirarchical ID.  Here, it's always 3.
        //HL02: Heirarchical parent id number.  2 in this simple message.
        //HL03: Heirarchical level code. 22=Subscriber
        //HL04: Heirarchical child code. 0=no child HL present (no dependent)
        //2000C TRN: Subscriber Trace Number
        seg++;
        //TRN01: Trace Type Code.  1=Current Transaction Trace Numbers
        //TRN02: Trace Number.  We don't really have a good primary key yet.  Keep it simple. Use 1.
        strb.AppendLine("TRN*1*" + "1*" + "1" + billProv.SSN + "~");
        //TRN03: Entity Identifier. First digit is 1=EIN.  Next 9 digits are EIN.  Length validated.
        //2100C NM1: Subscriber Name
        seg++;
        //NM101: IL=Insured or Subscriber
        //NM102: 1=Person
        //NM103: LName
        //NM104: FName
        //NM105: MiddleName
        //NM106: not used
        //NM107: suffix. Not present in Open Dental yet.
        //NM108: MI=MemberID
        strb.AppendLine("NM1*IL*" + "1*" + sout(subscriber.LName,35) + "*" + sout(subscriber.FName,25) + "*" + sout(subscriber.MiddleI,25) + "*" + "*" + "*" + "MI*" + Sout(insSub.SubscriberID.Replace("-", ""), 80) + "~");
        //NM109: Subscriber ID. Validated to be L>2.
        //2100C REF: Subscriber Additional Information.  Without this, old plans seem to be frequently returned.
        seg++;
        //REF01: 6P=GroupNumber
        strb.AppendLine("REF*6P*" + sout(insPlan.GroupNum,30) + "~");
        //REF02: Supplemental ID. Validated.
        //2100C DMG: Subscriber Demographic Information
        seg++;
        //DMG01: Date Time Period Qualifier.  D8=CCYYMMDD
        strb.AppendLine("DMG*D8*" + subscriber.Birthdate.ToString("yyyyMMdd") + "~");
        //DMG02: Subscriber birthdate.  Validated
        //DMG03: Gender code.  Situational.  F or M.  Since this was left out in the example,
        //and since we don't want to send the wrong gender, we will not send this element.
        //2100C DTP: Subscriber Date.  Deduced through trial and error that this is required by EHG even though not by X12 specs.
        seg++;
        //DTP01: Qualifier.  307=Eligibility
        //DTP02: Format Qualifier.
        strb.AppendLine("DTP*307*" + "D8*" + DateTime.Today.ToString("yyyyMMdd") + "~");
        //DTP03: Date
        //2110C EQ: Subscriber Eligibility or Benefit Enquiry Information
        //X12 documentation seems to say that we can loop this 99 times to request very specific benefits.
        //ClaimConnect wants to see either an EQ*30 for "an eligibility request", or an EQ*35 for "a general benefits request".
        //The director of vendor implementation at ClaimConnect has informed us that we should send an EQ*35 to get the full set of benefits.
        seg++;
        strb.AppendLine("EQ*35~");
        //Dental Care
        //seg++;
        //strb.AppendLine("EQ*30~");//EQ01: 30=General Coverage
        //seg++;
        //strb.AppendLine("EQ*23~");//Diagnostic
        //seg++;
        //strb.AppendLine("EQ*4~");//Diagnostic Xray
        //seg++;
        //strb.AppendLine("EQ*41~");//Routine Preventive
        //seg++;
        //strb.AppendLine("EQ*25~");//Restorative
        //seg++;
        //strb.AppendLine("EQ*26~");//Endo
        //seg++;
        //strb.AppendLine("EQ*24~");//Perio
        //seg++;
        //strb.AppendLine("EQ*40~");//Oral Surgery
        //seg++;
        //strb.AppendLine("EQ*36~");//Crowns
        //seg++;
        //strb.AppendLine("EQ*39~");//Prosth
        //seg++;
        //strb.AppendLine("EQ*27~");//Maxillofacial Prosth
        //seg++;
        //strb.AppendLine("EQ*37~");//Accident
        //seg++;
        //strb.AppendLine("EQ*38~");//Ortho
        //seg++;
        //strb.AppendLine("EQ*28~");//Adjunctive
        //
        //2000D If we add a dependent loop it would go here.  It would be about 20 lines.
        //2100D, etc
        //EQ series, etc.
        //Not allowed to send this unless subscriber and dependent are different
        //We would also have to add code to process the EBs which distinguishes between subscribers and dependents.
        //
        //Transaction Trailer
        seg++;
        //SE01: Total segments, including ST & SE
        strb.AppendLine("SE*" + seg.ToString() + "*" + transactionNum.ToString().PadLeft(4, '0') + "~");
        //End of transaction--------------------------------------------------------------------------------------
        //Functional Group Trailer
        //GE01: Number of transaction sets included
        strb.AppendLine("GE*" + transactionNum.ToString() + "*" + groupControlNumber + "~");
        //GE02: Group Control number. Must be identical to GS06
        //Interchange Control Trailer
        //IEA01: number of functional groups
        strb.AppendLine("IEA*1*" + batchNum.ToString().PadLeft(9, '0') + "~");
        return strb.ToString();
    }

    //IEA02: Interchange control number
    /*
    			return @"
    ISA*00*          *00*          *30*AA0989922      *30*330989922      *030519*1608*U*00401*000012145*1*T*:~
    GS*HS*AA0989922*330989922*20030519*1608*12145*X*004010X092~
    ST*270*0001~
    BHT*0022*13*ASX012145WEB*20030519*1608~
    HL*1**20*1~
    NM1*PR*2*Metlife*****PI*65978~
    HL*2*1*21*1~
    NM1*1P*1*PROVLAST*PROVFIRST****XX*1234567893~
    REF*TJ*200384584~
    N3*JUNIT ROAD~
    N4*CHICAGO*IL*60602~
    PRV*PE*ZZ*1223G0001X~
    HL*3*2*22*0~
    TRN*1*12145*1AA0989922~
    NM1*IL*1*SUBLASTNAME*SUBFIRSTNAME****MI*123456789~
    DMG*D8*19750323~
    DTP*307*D8*20030519~
    EQ*30~
    SE*17*0001~
    GE*1*12145~
    IEA*1*000012145~";
    			*/
    //return "ISA*00*          *00*          *30*AA0989922      *30*330989922      *030519*1608*U*00401*000012145*1*T*:~GS*HS*AA0989922*330989922*20030519*1608*12145*X*004010X092~ST*270*0001~BHT*0022*13*ASX012145WEB*20030519*1608~HL*1**20*1~NM1*PR*2*Metlife*****PI*65978~HL*2*1*21*1~NM1*1P*1*PROVLAST*PROVFIRST****XX*1234567893~REF*TJ*200384584~N3*JUNIT ROAD~N4*CHICAGO*IL*60602~PRV*PE*ZZ*1223G0001X~HL*3*2*22*0~TRN*1*12145*1AA0989922~NM1*IL*1*SUBLASTNAME*SUBFIRSTNAME****MI*123456789~DMG*D8*19750323~DTP*307*D8*20030519~EQ*30~SE*17*0001~GE*1*12145~IEA*1*000012145~";
    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String inputStr, int maxL, int minL) throws Exception {
        return X12Generator.sout(inputStr,maxL,minL);
    }

    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String str, int maxL) throws Exception {
        return X12Generator.sout(str,maxL,-1);
    }

    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    private static String sout(String str) throws Exception {
        return X12Generator.sout(str,-1,-1);
    }

    public static String validate(Clearinghouse clearhouse, Carrier carrier, Provider billProv, Clinic clinic, InsPlan insPlan, Patient subscriber, InsSub insSub) throws Exception {
        StringBuilder strb = new StringBuilder();
        X12Validate.iSA(clearhouse,strb);
        X12Validate.carrier(carrier,strb);
        if (carrier.ElectID.Length < 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Electronic ID");
        }
         
        if (billProv.SSN.Length != 9)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Prov TIN 9 digits");
        }
         
        X12Validate.billProv(billProv,strb);
        if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
        {
            X12Validate.billingAddress(strb);
        }
        else if (clinic == null)
        {
            X12Validate.practiceAddress(strb);
        }
        else
        {
            X12Validate.clinic(clinic,strb);
        }  
        if (insSub.SubscriberID.Length < 2)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("SubscriberID");
        }
         
        if (subscriber.Birthdate.Year < 1880)
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Subscriber Birthdate");
        }
         
        if (StringSupport.equals(insPlan.GroupNum, ""))
        {
            if (strb.Length != 0)
            {
                strb.Append(",");
            }
             
            strb.Append("Group Number");
        }
         
        return strb.ToString();
    }

}


