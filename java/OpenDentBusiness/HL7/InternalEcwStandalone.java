//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

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
public class InternalEcwStandalone   
{
    public static HL7Def getDeepInternal(HL7Def def) throws Exception {
        //ok to pass in null
        //HL7Def def=HL7Defs.GetInternalFromDb("eCWStandalone");
        if (def == null)
        {
            //wasn't in the database
            def = new HL7Def();
            def.setIsNew(true);
            def.Description = "eCW Standalone";
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
            def.InternalType = "eCWStandalone";
            def.InternalTypeVersion = Assembly.GetAssembly(Db.class).GetName().Version.ToString();
            def.IsEnabled = false;
            def.Note = "";
            def.ShowDemographics = HL7ShowDemographics.ChangeAndAdd;
            def.ShowAccount = true;
            def.ShowAppts = true;
        }
         
        def.hl7DefMessages = new List<HL7DefMessage>();
        //so that if this is called repeatedly, it won't pile on duplicate messages.
        //in either case, now get all child objects, which can't be in the database.
        //----------------------------------------------------------------------------------------------------------------------------------
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
        seg.addField(2,"pat.ChartNumber");
        //PID.4, Alternate Patient ID, PID.4 is not saved with using standalone integration
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
        seg.addField(2,"guar.ChartNumber");
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
        return def;
    }

}


