//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.ModeTxHL7;
import OpenDentBusiness.TableBase;

/**
* .
*/
public class HL7Def  extends TableBase 
{
    /**
    * Primary key.
    */
    public long HL7DefNum = new long();
    /**
    * 
    */
    public String Description = new String();
    /**
    * Enum:ModeTxHL7 File, TcpIp.
    */
    public ModeTxHL7 ModeTx = ModeTxHL7.File;
    /**
    * Only used for File mode
    */
    public String IncomingFolder = new String();
    /**
    * Only used for File mode
    */
    public String OutgoingFolder = new String();
    /**
    * Only used for tcpip mode. Example: 1461
    */
    public String IncomingPort = new String();
    /**
    * Only used for tcpip mode. Example: 192.168.0.23:1462
    */
    public String OutgoingIpPort = new String();
    /**
    * Only relevant for outgoing. Incoming field separators are defined in MSH. Default |.
    */
    public String FieldSeparator = new String();
    /**
    * Only relevant for outgoing. Incoming field separators are defined in MSH. Default ^.
    */
    public String ComponentSeparator = new String();
    ///<summary>Only relevant for outgoing. Incoming field separators are defined in MSH. Default &.</summary>
    public String SubcomponentSeparator = new String();
    /**
    * Only relevant for outgoing. Incoming field separators are defined in MSH. Default ~.
    */
    public String RepetitionSeparator = new String();
    /**
    * Only relevant for outgoing. Incoming field separators are defined in MSH. Default \.
    */
    public String EscapeCharacter = new String();
    /**
    * If this is set, then there will be no child tables. Internal types are fully defined within the C# code rather than in the database.
    */
    public boolean IsInternal = new boolean();
    /**
    * This will always have a value because we always start with a copy of some internal type.
    */
    public String InternalType = new String();
    /**
    * Example: 12.2.14. This will be empty if IsInternal. This records the version at which they made their copy. We might have made significant improvements since their copy.
    */
    public String InternalTypeVersion = new String();
    /**
    * .
    */
    public boolean IsEnabled = new boolean();
    /**
    * 
    */
    public String Note = new String();
    /**
    * The machine name of the computer where the OpenDentHL7 service for this def is running.
    */
    public String HL7Server = new String();
    /**
    * The name of the HL7 service for this def.  Must begin with OpenDent...
    */
    public String HL7ServiceName = new String();
    /**
    * Enum:HL7ShowDemographics Hide,Show,Change,ChangeAndAdd
    */
    public HL7ShowDemographics ShowDemographics = HL7ShowDemographics.Hide;
    /**
    * Show Appointments module.
    */
    public boolean ShowAppts = new boolean();
    /**
    * Show Account module
    */
    public boolean ShowAccount = new boolean();
    /**
    * List of messages associated with this hierarchical definition.  Use items in this list to get to items lower in the hierarchy.
    */
    public List<HL7DefMessage> hl7DefMessages = new List<HL7DefMessage>();
    /**
    * 
    */
    public HL7Def clone() {
        try
        {
            return (HL7Def)this.MemberwiseClone();
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

    public void addMessage(HL7DefMessage msg, MessageTypeHL7 messageType, EventTypeHL7 eventType, InOutHL7 inOrOut, int itemOrder, String note) throws Exception {
        if (hl7DefMessages == null)
        {
            hl7DefMessages = new List<HL7DefMessage>();
        }
         
        msg.MessageType = messageType;
        msg.EventType = eventType;
        msg.InOrOut = inOrOut;
        msg.ItemOrder = itemOrder;
        msg.Note = note;
        this.hl7DefMessages.Add(msg);
    }

    public void addMessage(HL7DefMessage msg, MessageTypeHL7 messageType, EventTypeHL7 eventType, InOutHL7 inOrOut, int itemOrder) throws Exception {
        addMessage(msg,messageType,eventType,inOrOut,itemOrder,"");
    }

}


