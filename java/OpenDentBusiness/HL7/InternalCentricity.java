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
public class InternalCentricity   
{
    public static HL7Def getDeepInternal(HL7Def def) throws Exception {
        //ok to pass in null
        if (def == null)
        {
            //wasn't in the database
            def = new HL7Def();
            def.setIsNew(true);
            def.Description = "Centricity";
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
            def.InternalType = "Centricity";
            def.InternalTypeVersion = Assembly.GetAssembly(Db.class).GetName().Version.ToString();
            def.IsEnabled = false;
            def.Note = "";
            def.ShowDemographics = HL7ShowDemographics.ChangeAndAdd;
            def.ShowAccount = true;
            def.ShowAppts = true;
        }
         
        def.hl7DefMessages = new List<HL7DefMessage>();
        HL7DefMessage msg = new HL7DefMessage();
        HL7DefSegment seg = new HL7DefSegment();
        //=======================================================================================================================
        //Detail financial transaction (DFT)
        def.addMessage(msg,MessageTypeHL7.DFT,EventTypeHL7.P03,InOutHL7.Outgoing,2);
        //MSH (Message Header) segment-------------------------------------------------
        msg.addSegment(seg,0,SegmentNameHL7.MSH);
        //HL7 documentation says field 1 is Field Separator.  "This field contains the separator between the segment ID and the first real field.  As such it serves as the separator and defines the character to be used as a separator for the rest of the message." (HL7 v2.6 documentation) The separator is usually | (pipes) and is part of field 0, which is the segment ID followed by a |.  Encoding Characters is the first real field, so it will be numbered starting with 1 in our def.
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
        //MSH.16, Application Ack Type (AL=Always, NE=Never, ER=Error/reject conditions only, SU=Successful completion only)
        seg.addFieldFixed(15,DataTypeHL7.ID,"NE");
        //EVN (Event Type) segment-----------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,1,SegmentNameHL7.EVN);
        //EVN.1, Event Type, example P03
        seg.addField(1,"eventType");
        //EVN.2, Recorded Date/Time
        seg.addField(2,"dateTime.Now");
        //EVN.3, Event Reason Code
        seg.addFieldFixed(3,DataTypeHL7.IS,"01");
        //PID (Patient Identification) segment-----------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,2,SegmentNameHL7.PID);
        //PID.1, Sequence Number (1 for DFT's)  "This field contains the number that identifies this transaction.  For the first occurrence of the segment, the sequence number shall be one, for the second occurrence, the sequence number shall be two, etc." (HL7 v2.6 documentation)  We only send 1 PID segment in DFT's so this number will always be 1.
        seg.addFieldFixed(1,DataTypeHL7.SI,"1");
        //PID.2, Patient ID (External)
        seg.addField(2,"pat.ChartNumber");
        //PID.3, Patient ID (Internal)
        seg.addField(3,"pat.PatNum");
        //PV1 (Patient Visit) segment--------------------------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,3,SegmentNameHL7.PV1);
        //PV1.1, Set ID - PV1 (1 for DFT's)  See the comment above for the Sequence Number of the PID segment.  Always 1 since we only send one PV1 segment per DFT message.
        seg.addFieldFixed(1,DataTypeHL7.SI,"1");
        //PV1.2, Patient Class  (E=Emergency, I=Inpatient, O=Outpatient, P=Preadmit, R=Recurring patient, B=Obstetrics, C=Commercial Account, N=Not Applicable, U=Unkown)  We will just send O for outpatient for every DFT message.
        seg.addFieldFixed(2,DataTypeHL7.IS,"O");
        //todo: ClinicNum?
        //PV1.3, Assigned Patient Location
        //PV1.7, Attending/Primary Care Doctor
        seg.addField(7,"prov.provIdNameLFM");
        //todo: Referring Dr?
        //PV1.8, Referring Doctor
        //PV1.19, Visit Number
        seg.addField(19,"apt.AptNum");
        //PV1.44, Admit Date/Time
        seg.addField(44,"proc.procDateTime");
        //PV1.50, Alternate Visit ID
        //FT1 (Financial Transaction Information) segment------------------------------
        seg = new HL7DefSegment();
        msg.addSegment(seg,4,true,true,SegmentNameHL7.FT1);
        //FT1.1, Sequence Number (starts with 1)
        seg.addField(1,"sequenceNum");
        //FT1.2, Transaction ID
        seg.addField(2,"proc.ProcNum");
        //FT1.4, Transaction Date (YYYYMMDDHHMMSS)
        seg.addField(4,"proc.procDateTime");
        //FT1.5, Transaction Posting Date (YYYYMMDDHHMMSS)
        seg.addField(5,"proc.procDateTime");
        //FT1.6, Transaction Type
        seg.addFieldFixed(6,DataTypeHL7.IS,"CG");
        //FT1.10, Transaction Quantity
        seg.addFieldFixed(10,DataTypeHL7.NM,"1.0");
        //FT1.11, Transaction Amount Extended (Total fee to charge for this procedure, independent of transaction quantity)
        seg.addField(11,"proc.ProcFee");
        //FT1.12, Transaction Amount Unit (Fee for this procedure for each transaction quantity)
        seg.addField(12,"proc.ProcFee");
        //todo: ClinicNum?
        //FT1.16, Assigned Patient Location
        //FT1.19, Diagnosis Code
        seg.addField(19,"proc.DiagnosticCode");
        //FT1.21, Ordering Provider
        seg.addField(21,"prov.provIdNameLFM");
        //FT1.22, Unit Cost (procedure fee)
        seg.addField(22,"proc.ProcFee");
        //FT1.25, Procedure Code
        seg.addField(25,"proccode.ProcCode");
        //FT1.26, Modifiers (treatment area)
        seg.addField(26,"proc.toothSurfRange");
        return def;
    }

}


//DG1 (Diagnosis) segment is optional, skip for now
//PR1 (Procedures) segment is optional, skip for now