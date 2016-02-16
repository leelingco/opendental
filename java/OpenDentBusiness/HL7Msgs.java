//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SegmentNameHL7;

/**
* 
*/
public class HL7Msgs   
{
    public static List<HL7Msg> getOnePending() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7Msg>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM hl7msg WHERE HL7Status=" + POut.Long(((Enum)HL7MessageStatus.OutPending).ordinal()) + " " + DbHelper.limitAnd(1);
        return Crud.HL7MsgCrud.SelectMany(command);
    }

    //Just 0 or 1 item in list for now.
    /**
    * When called we will make sure to send a startDate and endDate.  Status parameter 0:All, 1:OutPending, 2:OutSent, 3:OutFailed, 4:InProcessed, 5:InFailed
    */
    public static List<HL7Msg> getHL7Msgs(DateTime startDate, DateTime endDate, long patNum, int status) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7Msg>>GetObject(MethodBase.GetCurrentMethod(), startDate, endDate, patNum, status);
        }
         
        //join with the patient table so we can display patient name instead of PatNum
        String command = "SELECT *\tFROM hl7msg\tWHERE DATE(hl7msg.DateTStamp) BETWEEN " + POut.date(startDate) + " AND " + POut.date(endDate) + " ";
        if (patNum > 0)
        {
            command += "AND hl7msg.PatNum=" + POut.long(patNum) + " ";
        }
         
        if (status > 0)
        {
            command += "AND hl7msg.HL7Status=" + POut.Long(status - 1) + " ";
        }
         
        //minus 1 because 0=All but our enum starts at 0
        command += "ORDER BY hl7msg.DateTStamp";
        return Crud.HL7MsgCrud.SelectMany(command);
    }

    /**
    * Gets the message control ID of the message we are attempting to send, for TCP/IP acknowledgment.
    */
    public static String getControlId(HL7Msg msg) throws Exception {
        String retval = "";
        if (msg == null)
        {
            return retval;
        }
         
        int controlIdOrder = 0;
        MessageHL7 msgHl7 = new MessageHL7(msg.MsgText);
        //creates the segments
        HL7Def def = HL7Defs.getOneDeepEnabled();
        if (def == null)
        {
            return retval;
        }
         
        HL7DefMessage hl7defmsg = null;
        for (int i = 0;i < def.hl7DefMessages.Count;i++)
        {
            if (def.hl7DefMessages[i].MessageType == msgHl7.MsgType)
            {
                hl7defmsg = def.hl7DefMessages[i];
                break;
            }
             
        }
        if (hl7defmsg == null)
        {
            return retval;
        }
         
        for (int s = 0;s < hl7defmsg.hl7DefSegments.Count;s++)
        {
            //No message definition for this type of message in the enabled def
            //get MSH segment
            if (hl7defmsg.hl7DefSegments[s].SegmentName == SegmentNameHL7.MSH)
            {
                for (int f = 0;f < hl7defmsg.hl7DefSegments[s].hl7DefFields.Count;f++)
                {
                    //find messageControlId field in MSH segment def
                    if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "messageControlId"))
                    {
                        controlIdOrder = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                        break;
                    }
                     
                }
                break;
            }
             
        }
        if (controlIdOrder == 0)
        {
            return retval;
        }
         
        for (int i = 0;i < msgHl7.Segments.Count;i++)
        {
            //No messageControlId defined for this MSH segment
            //get control ID from message located in MSH segment with field determined above
            if (msgHl7.Segments[i].Name == SegmentNameHL7.MSH)
            {
                retval = msgHl7.Segments[i].Fields[controlIdOrder].ToString();
            }
             
        }
        return retval;
    }

    /**
    * 
    */
    public static long insert(HL7Msg hL7Msg) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hL7Msg.HL7MsgNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hL7Msg);
            return hL7Msg.HL7MsgNum;
        }
         
        return Crud.HL7MsgCrud.Insert(hL7Msg);
    }

    /**
    * 
    */
    public static void update(HL7Msg hL7Msg) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7Msg);
            return ;
        }
         
        Crud.HL7MsgCrud.Update(hL7Msg);
    }

    /**
    * 
    */
    public static boolean messageWasSent(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        //string command="SELECT COUNT(*) FROM hl7msg WHERE AptNum="+POut.Long(aptNum);
        String command = "SELECT COUNT(*) FROM hl7msg WHERE AptNum=" + POut.long(aptNum) + " AND MsgText LIKE '%DFT%'";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Doesn't delete the old messages, but just the text of the message.  This avoids breaking MessageWasSent().  Only affects messages that are at least four months old, regardless of status.
    */
    public static void deleteOldMsgText() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "UPDATE hl7msg SET MsgText='' " + "WHERE DateTStamp < ADDDATE(CURDATE(),INTERVAL -4 MONTH)";
        Db.nonQ(command);
    }

    public static List<HL7Msg> getOneExisting(HL7Msg hl7Msg) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7Msg>>GetObject(MethodBase.GetCurrentMethod(), hl7Msg);
        }
         
        String command = "SELECT * FROM hl7msg WHERE MsgText='" + POut.string(hl7Msg.MsgText) + "' " + DbHelper.limitAnd(1);
        return Crud.HL7MsgCrud.SelectMany(command);
    }

    //Just 0 or 1 item in list for now.
    public static void updateDateTStamp(HL7Msg hl7Msg) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hl7Msg);
            return ;
        }
         
        String command = "UPDATE hl7msg SET DateTStamp=CURRENT_TIMESTAMP WHERE MsgText='" + POut.string(hl7Msg.MsgText) + "' ";
        Db.nonQ(command);
    }

}


