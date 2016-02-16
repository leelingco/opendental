//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7.FieldConstructor;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SegmentNameHL7;

/**
* This is the engine that will construct our outgoing HL7 messages.
*/
public class MessageConstructor   
{
    /**
    * Returns null if there is no DFT defined for the enabled HL7Def.
    */
    public static MessageHL7 generateDFT(List<Procedure> procList, EventTypeHL7 eventType, Patient pat, Patient guar, long aptNum, String pdfDescription, String pdfDataString) throws Exception {
        //add event (A04 etc) parameters later if needed
        //In \\SERVERFILES\storage\OPEN DENTAL\Programmers Documents\Standards (X12, ADA, etc)\HL7\Version2.6\V26_CH02_Control_M4_JAN2007.doc
        //On page 28, there is a Message Construction Pseudocode as well as a flowchart which might help.
        Provider prov = Providers.getProv(Patients.getProvNum(pat));
        Appointment apt = Appointments.getOneApt(aptNum);
        MessageHL7 messageHL7 = new MessageHL7(MessageTypeHL7.DFT);
        HL7Def hl7Def = HL7Defs.getOneDeepEnabled();
        if (hl7Def == null)
        {
            return null;
        }
         
        //find a DFT message in the def
        HL7DefMessage hl7DefMessage = null;
        for (int i = 0;i < hl7Def.hl7DefMessages.Count;i++)
        {
            if (hl7Def.hl7DefMessages[i].MessageType == MessageTypeHL7.DFT)
            {
                hl7DefMessage = hl7Def.hl7DefMessages[i];
                break;
            }
             
        }
        //continue;
        if (hl7DefMessage == null)
        {
            return null;
        }
         
        for (int s = 0;s < hl7DefMessage.hl7DefSegments.Count;s++)
        {
            //DFT message type is not defined so do nothing and return
            int countRepeat = 1;
            if (hl7DefMessage.hl7DefSegments[s].SegmentName == SegmentNameHL7.FT1)
            {
                countRepeat = procList.Count;
            }
             
            for (int repeat = 0;repeat < countRepeat;repeat++)
            {
                //for example, countRepeat can be zero in the case where we are only sending a PDF of the TP to eCW, and no procs.
                //FT1 is optional and can repeat so add as many FT1's as procs in procList
                //if(hl7DefMessage.hl7DefSegments[s].SegmentName==SegmentNameHL7.FT1) {
                if (hl7DefMessage.hl7DefSegments[s].SegmentName == SegmentNameHL7.FT1 && procList.Count > repeat)
                {
                    prov = Providers.GetProv(procList[repeat].ProvNum);
                }
                 
                SegmentHL7 seg = new SegmentHL7(hl7DefMessage.hl7DefSegments[s].SegmentName);
                seg.SetField(0, hl7DefMessage.hl7DefSegments[s].SegmentName.ToString());
                for (int f = 0;f < hl7DefMessage.hl7DefSegments[s].hl7DefFields.Count;f++)
                {
                    String fieldName = hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].FieldName;
                    if (StringSupport.equals(fieldName, ""))
                    {
                        //If fixed text instead of field name just add text to segment
                        seg.SetField(hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].OrdinalPos, hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].FixedText);
                    }
                    else
                    {
                        //seg.SetField(hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].OrdinalPos,
                        //FieldConstructor.GenerateDFT(hl7Def,fieldName,pat,prov,procList[repeat],guar,apt,repeat+1,eventType,pdfDescription,pdfDataString));
                        Procedure proc = null;
                        if (procList.Count > repeat)
                        {
                            //procList could be an empty list
                            proc = procList[repeat];
                        }
                         
                        seg.SetField(hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].OrdinalPos, FieldConstructor.GenerateDFT(hl7Def, fieldName, pat, prov, proc, guar, apt, repeat + 1, eventType, pdfDescription, pdfDataString));
                    } 
                }
                messageHL7.Segments.Add(seg);
            }
        }
        return messageHL7;
    }

    /**
    * Returns null if no HL7 def is enabled or no ACK is defined in the enabled def.
    */
    public static MessageHL7 generateACK(String controlId, boolean isAck, String ackEvent) throws Exception {
        MessageHL7 messageHL7 = new MessageHL7(MessageTypeHL7.ACK);
        messageHL7.ControlId = controlId;
        messageHL7.AckEvent = ackEvent;
        HL7Def hl7Def = HL7Defs.getOneDeepEnabled();
        if (hl7Def == null)
        {
            return null;
        }
         
        //no def enabled, return null
        //find an ACK message in the def
        HL7DefMessage hl7DefMessage = null;
        for (int i = 0;i < hl7Def.hl7DefMessages.Count;i++)
        {
            if (hl7Def.hl7DefMessages[i].MessageType == MessageTypeHL7.ACK && hl7Def.hl7DefMessages[i].InOrOut == InOutHL7.Outgoing)
            {
                hl7DefMessage = hl7Def.hl7DefMessages[i];
                break;
            }
             
        }
        if (hl7DefMessage == null)
        {
            return null;
        }
         
        for (int s = 0;s < hl7DefMessage.hl7DefSegments.Count;s++)
        {
            //ACK message type is not defined so do nothing and return
            //go through each segment in the def
            SegmentHL7 seg = new SegmentHL7(hl7DefMessage.hl7DefSegments[s].SegmentName);
            seg.SetField(0, hl7DefMessage.hl7DefSegments[s].SegmentName.ToString());
            for (int f = 0;f < hl7DefMessage.hl7DefSegments[s].hl7DefFields.Count;f++)
            {
                String fieldName = hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].FieldName;
                if (StringSupport.equals(fieldName, ""))
                {
                    //If fixed text instead of field name just add text to segment
                    seg.SetField(hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].OrdinalPos, hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].FixedText);
                }
                else
                {
                    seg.SetField(hl7DefMessage.hl7DefSegments[s].hl7DefFields[f].OrdinalPos, FieldConstructor.GenerateACK(hl7Def, fieldName, controlId, isAck, ackEvent));
                } 
            }
            messageHL7.Segments.Add(seg);
        }
        return messageHL7;
    }

}


