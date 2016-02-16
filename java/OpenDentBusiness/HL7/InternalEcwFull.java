//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import OpenDentBusiness.DataTypeHL7;
import OpenDentBusiness.Db;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.ModeTxHL7;
import OpenDentBusiness.SegmentNameHL7;

/**
* 
*/
public class InternalEcwFull   
{
    public static HL7Def getDeepInternal(HL7Def def) throws Exception {
        //ok to pass in null
        if (def == null)
        {
            //wasn't in the database
            def = new HL7Def();
            def.setIsNew(true);
            def.Description = "eCW Full";
            def.ModeTx = ModeTxHL7.File;
            def.IncomingFolder = "";
            def.OutgoingFolder = "";
            def.IncomingPort = "";
            def.OutgoingIpPort = "";
            def.FieldSeparator = "|";
            def.ComponentSeparator = "^";
            def.SubcomponentSeparator = "&";
            def.RepetitionSeparator = "~";
            def.EscapeCharacter = "\\";
            def.IsInternal = true;
            def.InternalType = "eCWFull";
            def.InternalTypeVersion = Assembly.GetAssembly(Db.class).GetName().Version.ToString();
            def.IsEnabled = false;
            def.Note = "";
            def.ShowDemographics = HL7ShowDemographics.Show;
            def.ShowAccount = true;
            def.ShowAppts = false;
        }
         
        //for now
        def.hl7DefMessages = new List<HL7DefMessage>();
        //so that if this is called repeatedly, it won't pile on duplicate messages.
        //in either case, now get all child objects, which can't be in the database.
        //======================================================================================================================
        //eCW incoming patient information (ADT).
        HL7DefMessage msg = new HL7DefMessage();
        def.addMessage(msg,MessageTypeHL7.ADT,EventTypeHL7.A04,InOutHL7.Incoming,0);
        //MSH segment------------------------------------------------------------------
        HL7DefSegment seg = new HL7DefSegment();
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //MSH.8, Message Type
        seg.addField(8,"messageType");
        //MSH.9, Message Control ID
        seg.addField(9,"messageControlId");
        //PID segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,2,SegmentNameHL7.PID);
        //PID.2, Patient ID
        seg.addField(2,"pat.PatNum");
        //PID.4, Alternate Patient ID
        seg.addField(4,"pat.ChartNumber");
        //PID.5, Patient Name
        seg.addField(5,"pat.nameLFM");
        //PID.7, Date/Time of Birth
        seg.addField(7,"pat.birthdateTime");
        //PID.8, Administrative Sex
        seg.addField(8,"pat.Gender");
        //PID.10, Race
        seg.addField(10,"pat.Race");
        //PID.11, Patient Address
        seg.addField(11,"pat.addressCityStateZip");
        //PID.13, Phone Number - Home
        seg.addField(13,"pat.HmPhone");
        //PID.14, Phone Number - Business
        seg.addField(14,"pat.WkPhone");
        //PID.16, Marital Status
        seg.addField(16,"pat.Position");
        //PID.19, SSN - Patient
        seg.addField(19,"pat.SSN");
        //PID.22, Fee Schedule
        seg.addField(22,"pat.FeeSched");
        //GT1 segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,5,SegmentNameHL7.GT1);
        //GT1.2, Guarantor Number
        seg.addField(2,"guar.PatNum");
        //GT1.3, Guarantor Name
        seg.addField(3,"guar.nameLFM");
        //GT1.5, Guarantor Address
        seg.addField(5,"guar.addressCityStateZip");
        //GT1.6, Guarantor Phone Number - Home
        seg.addField(6,"guar.HmPhone");
        //GT1.7, Guarantor Phone Number - Business
        seg.addField(7,"guar.WkPhone");
        //GT1.8, Guarantor Date/Time of Birth
        seg.addField(8,"guar.birthdateTime");
        //GT1.9, Guarantor Administrative Sex
        seg.addField(9,"guar.Gender");
        //GT1.12, Guarantor SSN
        seg.addField(12,"guar.SSN");
        //======================================================================================================================
        //eCW incoming appointment information (SIU - Schedule information unsolicited).
        msg = new HL7DefMessage();
        def.addMessage(msg,MessageTypeHL7.SIU,EventTypeHL7.S12,InOutHL7.Incoming,1);
        //MSH segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //MSH.8, Message Type
        seg.addField(8,"messageType");
        //MSH.9, Message Control ID
        seg.addField(9,"messageControlId");
        //PID segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,2,SegmentNameHL7.PID);
        //PID.2
        seg.addField(2,"pat.PatNum");
        //PID.4, Alternate Patient ID
        seg.addField(4,"pat.ChartNumber");
        //PID.5, Patient Name
        seg.addField(5,"pat.nameLFM");
        //PID.7, Date/Time of Birth
        seg.addField(7,"pat.birthdateTime");
        //PID.8, Administrative Sex
        seg.addField(8,"pat.Gender");
        //PID.10, Race
        seg.addField(10,"pat.Race");
        //PID.11, Patient Address
        seg.addField(11,"pat.addressCityStateZip");
        //PID.13, Phone Number - Home
        seg.addField(13,"pat.HmPhone");
        //PID.14, Phone Number - Business
        seg.addField(14,"pat.WkPhone");
        //PID.16, Marital Status
        seg.addField(16,"pat.Position");
        //PID.19, SSN - Patient
        seg.addField(19,"pat.SSN");
        //PID.22, Fee Schedule
        seg.addField(22,"pat.FeeSched");
        //SCH segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,1,SegmentNameHL7.SCH);
        //SCH.1, Placer Appointment ID.  In the old eCW interface, we were pulling from SCH.2, which was always the same as SCH.1.
        seg.addField(1,"apt.AptNum");
        //SCH.7, Appointment Reason
        seg.addField(7,"apt.Note");
        //SCH.11, Appointment Timing Quantity
        seg.addField(11,"apt.lengthStartEnd");
        //AIG segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,4,false,true,SegmentNameHL7.AIG);
        //AIG.3, Resource ID^Resource Name (Lname, Fname all as a string)
        seg.addField(3,"prov.provIdName");
        //PV1 segment.-----------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,3,false,true,SegmentNameHL7.PV1);
        //PV1.7, Attending/Primary Care Doctor, UPIN^LastName^FirstName^MI
        seg.addField(7,"prov.provIdNameLFM");
        //=======================================================================================================================
        //Acknowledgment message (ACK)
        msg = new HL7DefMessage();
        def.addMessage(msg,MessageTypeHL7.ACK,EventTypeHL7.A04,InOutHL7.Incoming,2);
        //MSH segment------------------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //MSH.8, Message Type
        seg.addField(8,"messageType");
        //MSA (Message Acknowledgment) segment-----------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,1,SegmentNameHL7.MSA);
        //MSA.1, Acknowledgment Code
        seg.addField(1,"ackCode");
        //MSA.2, Message Control ID
        seg.addField(2,"messageControlId");
        //=======================================================================================================================
        //Detail financial transaction (DFT)
        msg = new HL7DefMessage();
        def.addMessage(msg,MessageTypeHL7.DFT,EventTypeHL7.P03,InOutHL7.Outgoing,3);
        //MSH (Message Header) segment-------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //MSH.1, Encoding Characters (DataType.ST)
        seg.addField(1,"separators^~\\&");
        //MSH.2, Sending Application
        seg.addFieldFixed(2,DataTypeHL7.HD,"OD");
        //MSH.4, Receiving Application
        seg.addFieldFixed(4,DataTypeHL7.HD,"ECW");
        //MSH.6, Message Date and Time (YYYYMMDDHHMMSS)
        seg.addField(6,"dateTime.Now");
        //MSH.8, Message Type^Event Type, example DFT^P03
        seg.addField(8,"messageType");
        //MSH.9, Message Control ID
        seg.addField(9,"messageControlId");
        //MSH.10, Processing ID (P-production, T-test)
        seg.addFieldFixed(10,DataTypeHL7.PT,"P");
        //MSH.11, Version ID
        seg.addFieldFixed(11,DataTypeHL7.VID,"2.3");
        //EVN (Event Type) segment-----------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,1,SegmentNameHL7.EVN);
        //EVN.1, Event Type, example P03
        seg.addField(1,"eventType");
        //EVN.2, Recorded Date/Time
        seg.addField(2,"dateTime.Now");
        //PID (Patient Identification) segment-----------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,2,SegmentNameHL7.PID);
        //PID.1, Sequence Number (1 for DFT's)
        seg.addFieldFixed(1,DataTypeHL7.ST,"1");
        //PID.2, Patient ID (Account number.  eCW requires this to be the same # as came in on PID.4.)
        seg.addField(2,"pat.ChartNumber");
        //PID.3, Patient MRN number
        seg.addField(3,"pat.PatNum");
        //PID.5, Patient Name (Last^First^MI)
        seg.addField(5,"pat.nameLFM");
        //PID.7, Birthdate
        seg.addField(7,"pat.birthdateTime");
        //PID.8, Gender
        seg.addField(8,"pat.Gender");
        //PID.10, Race
        seg.addField(10,"pat.Race");
        //PID.11, Address
        seg.addField(11,"pat.addressCityStateZip");
        //PID.13, Home Phone
        seg.addField(13,"pat.HmPhone");
        //PID.14, Work Phone
        seg.addField(14,"pat.WkPhone");
        //PID.16, Marital Status
        seg.addField(16,"pat.Position");
        //PID.19, SSN
        seg.addField(19,"pat.SSN");
        //PV1 (Patient Visit) segment--------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,3,SegmentNameHL7.PV1);
        //PV1.7, Attending/Primary Care Doctor
        seg.addField(7,"prov.provIdNameLFM");
        //PV1.19, Visit Number
        seg.addField(19,"apt.AptNum");
        //FT1 (Financial Transaction Information) segment------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,4,true,true,SegmentNameHL7.FT1);
        //FT1.1, Sequence Number (starts with 1)
        seg.addField(1,"sequenceNum");
        //FT1.4, Transaction Date (YYYYMMDDHHMMSS)
        seg.addField(4,"proc.procDateTime");
        //FT1.5, Transaction Posting Date (YYYYMMDDHHMMSS)
        seg.addField(5,"proc.procDateTime");
        //FT1.6, Transaction Type
        seg.addFieldFixed(6,DataTypeHL7.IS,"CG");
        //FT1.10, Transaction Quantity
        seg.addFieldFixed(10,DataTypeHL7.NM,"1.0");
        //FT1.19, Diagnosis Code
        seg.addField(19,"proc.DiagnosticCode");
        //FT1.20, Performed by Code (provider)
        seg.addField(20,"prov.provIdNameLFM");
        //FT1.21, Ordering Provider
        seg.addField(21,"prov.provIdNameLFM");
        //FT1.22, Unit Cost (procedure fee)
        seg.addField(22,"proc.ProcFee");
        //FT1.25, Procedure Code
        seg.addField(25,"proccode.ProcCode");
        //FT1.26, Modifiers (treatment area)
        seg.addField(26,"proc.toothSurfRange");
        //DG1 (Diagnosis) segment is optional, skip for now
        //ZX1 (PDF Data) segment-------------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,5,SegmentNameHL7.ZX1);
        //ZX1.1
        seg.addFieldFixed(1,DataTypeHL7.ST,"6");
        //ZX1.2
        seg.addFieldFixed(2,DataTypeHL7.ST,"PDF");
        //ZX1.3
        seg.addFieldFixed(3,DataTypeHL7.ST,"PATHOLOGY^Pathology Report^L");
        //ZX1.4
        seg.addField(4,"pdfDescription");
        //ZX1.5
        seg.addField(5,"pdfDataAsBase64");
        //=======================================================================================================================
        //Message Acknowledgment (ACK)
        msg = new HL7DefMessage();
        def.addMessage(msg,MessageTypeHL7.ACK,EventTypeHL7.A04,InOutHL7.Outgoing,4);
        //MSH (Message Header) segment-------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //MSH.1, Encoding Characters (DataType.ST)
        seg.addField(1,"separators^~\\&");
        //MSH.2, Sending Application
        seg.addFieldFixed(2,DataTypeHL7.HD,"OD");
        //MSH.4, Receiving Application
        seg.addFieldFixed(4,DataTypeHL7.HD,"ECW");
        //MSH.6, Message Date and Time (YYYYMMDDHHMMSS)
        seg.addField(6,"dateTime.Now");
        //MSH.8, Message Type^Event Type, example DFT^P03
        seg.addField(8,"messageType");
        //MSH.9, Message Control ID
        seg.addField(9,"messageControlId");
        //MSH.10, Processing ID (P-production, T-test)
        seg.addFieldFixed(10,DataTypeHL7.PT,"P");
        //MSH.11, Version ID
        seg.addFieldFixed(11,DataTypeHL7.VID,"2.3");
        //MSA (Message Acknowledgment) segment-----------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,1,SegmentNameHL7.MSA);
        //MSA.1, Acknowledgment Code
        seg.addField(1,"ackCode");
        //MSA.2, Message Control ID
        seg.addField(2,"messageControlId");
        return def;
    }

}


