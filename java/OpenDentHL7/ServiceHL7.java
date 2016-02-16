//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentHL7;

import OpenDentBusiness.HL7.EcwADT;
import OpenDentBusiness.HL7.EcwSIU;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Programs;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.HL7.MessageConstructor;
import OpenDentBusiness.HL7.MessageParser;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.ModeTxHL7;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;


/**
* This is the still the main Service class.  I just put it in a different file to keep it separate.
*/
public class ServiceHL7  extends ServiceBase 
{

    private static boolean ecwOldIsReceiving = new boolean();
    private System.Threading.Timer ecwOldTimerSend = new System.Threading.Timer();
    private System.Threading.Timer ecwOldTimerReceive = new System.Threading.Timer();
    private static String ecwOldHl7FolderIn = new String();
    private static String ecwOldHl7FolderOut = new String();
    /**
    * Indicates the standalone mode for eCW, or the use of Mountainside.  In both cases, chartNumber will be used instead of PatNum.
    */
    private static boolean ecwOldIsStandalone = new boolean();
    private void ecwOldSendAndReceive() throws Exception {
        ecwOldIsStandalone = true;
        //and for Mountainside
        if (Programs.usingEcwTightOrFullMode())
        {
            ecwOldIsStandalone = false;
        }
         
        //#if DEBUG//just so I don't forget to remove it later.
        //IsStandalone=false;
        //#endif
        ecwOldHl7FolderOut = PrefC.getString(PrefName.HL7FolderOut);
        if (!Directory.Exists(ecwOldHl7FolderOut))
        {
            throw new ApplicationException(ecwOldHl7FolderOut + " does not exist.");
        }
         
        //start polling the folder for waiting messages to import.  Every 5 seconds.
        TimerCallback timercallbackReceive = new TimerCallback(EcwOldTimerCallbackReceiveFunction);
        ecwOldTimerReceive = new System.Threading.Timer(timercallbackReceive, null, 5000, 5000);
        if (ecwOldIsStandalone)
        {
            return ;
        }
         
        //do not continue with the HL7 sending code below
        //start polling the db for new HL7 messages to send. Every 1.8 seconds.
        ecwOldHl7FolderIn = PrefC.getString(PrefName.HL7FolderIn);
        if (!Directory.Exists(ecwOldHl7FolderIn))
        {
            throw new ApplicationException(ecwOldHl7FolderIn + " does not exist.");
        }
         
        TimerCallback timercallbackSend = new TimerCallback(EcwOldTimerCallbackSendFunction);
        ecwOldTimerSend = new System.Threading.Timer(timercallbackSend, null, 1800, 1800);
    }

    private void ecwOldTimerCallbackReceiveFunction(Object stateInfo) throws Exception {
        //process all waiting messages
        if (ecwOldIsReceiving)
        {
            return ;
        }
         
        //already in the middle of processing files
        ecwOldIsReceiving = true;
        String[] existingFiles = Directory.GetFiles(ecwOldHl7FolderOut);
        for (int i = 0;i < existingFiles.Length;i++)
        {
            EcwOldProcessMessage(existingFiles[i]);
        }
        ecwOldIsReceiving = false;
    }

    private void ecwOldProcessMessage(String fullPath) throws Exception {
        String msgtext = "";
        int i = 0;
        while (i < 5)
        {
            try
            {
                msgtext = File.ReadAllText(fullPath);
                break;
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            Thread.Sleep(200);
            i++;
            if (i == 5)
            {
                EventLog.WriteEntry("Could not read text from file due to file locking issues.", EventLogEntryType.Error);
                return ;
            }
             
        }
        try
        {
            MessageHL7 msg = new MessageHL7(msgtext);
            //this creates an entire heirarchy of objects.
            if (msg.MsgType == MessageTypeHL7.ADT)
            {
                if (IsVerboseLogging)
                {
                    EventLog.WriteEntry("OpenDentHL7", "Processed ADT message", EventLogEntryType.Information);
                }
                 
                EcwADT.processMessage(msg,ecwOldIsStandalone,IsVerboseLogging);
            }
            else if (msg.MsgType == MessageTypeHL7.SIU && !ecwOldIsStandalone)
            {
                //appointments don't get imported if standalone mode.
                if (IsVerboseLogging)
                {
                    EventLog.WriteEntry("OpenDentHL7", "Processed SUI message", EventLogEntryType.Information);
                }
                 
                EcwSIU.processMessage(msg,IsVerboseLogging);
            }
              
        }
        catch (Exception ex)
        {
            EventLog.WriteEntry(ex.Message + "\r\n" + ex.StackTrace, EventLogEntryType.Error);
            return ;
        }

        try
        {
            //we won't be processing DFT messages.
            //else if(msg.MsgType==MessageType.DFT) {
            //ADT.ProcessMessage(msg);
            //}
            File.Delete(fullPath);
        }
        catch (Exception ex)
        {
            EventLog.WriteEntry("Delete failed for " + fullPath + "\r\n" + ex.Message, EventLogEntryType.Error);
        }
    
    }

    private void ecwOldTimerCallbackSendFunction(Object stateInfo) throws Exception {
        //does not happen for standalone
        List<HL7Msg> list = HL7Msgs.getOnePending();
        String filename = new String();
        for (int i = 0;i < list.Count;i++)
        {
            //Right now, there will only be 0 or 1 item in the list.
            if (list[i].AptNum == 0)
            {
                filename = CodeBase.ODFileUtils.createRandomFile(ecwOldHl7FolderIn,".txt");
            }
            else
            {
                filename = Path.Combine(ecwOldHl7FolderIn, list[i].AptNum.ToString() + ".txt");
            } 
            //EventLog.WriteEntry("Attempting to create file: "+filename);
            File.WriteAllText(filename, list[i].MsgText);
            list[i].HL7Status = HL7MessageStatus.OutSent;
            HL7Msgs.Update(list[i]);
            //set the status to sent.
            HL7Msgs.deleteOldMsgText();
        }
    }

    //This is inside the loop so that it happens less frequently.  To clean up incoming messages, we may move this someday.
    private void ecwOldStop() throws Exception {
        if (ecwOldTimerSend != null)
        {
            ecwOldTimerSend.Dispose();
        }
         
    }

    private boolean IsVerboseLogging = new boolean();
    private System.Threading.Timer timerSendFiles = new System.Threading.Timer();
    private System.Threading.Timer timerReceiveFiles = new System.Threading.Timer();
    private System.Threading.Timer timerSendTCP = new System.Threading.Timer();
    private Socket socketIncomingMain = new Socket();
    private Socket socketIncomingWorker = new Socket();
    /**
    * 
    */
    private byte[] dataBufferIncoming = new byte[]();
    private StringBuilder strbFullMsg = new StringBuilder();
    private String hl7FolderIn = new String();
    private String hl7FolderOut = new String();
    private static boolean isReceivingFiles = new boolean();
    private static final char MLLP_START_CHAR = (char)11;
    // HEX 0B
    private static final char MLLP_END_CHAR = (char)28;
    // HEX 1C
    private static final char MLLP_ENDMSG_CHAR = (char)13;
    // HEX 0D
    private static final char MLLP_ACK_CHAR = (char)6;
    // HEX 06
    private static final char MLLP_NACK_CHAR = (char)21;
    // HEX 15
    /**
    * 
    */
    private HL7Def HL7DefEnabled;
    private static boolean IsSendTCPConnected = new boolean();
    public ServiceHL7() throws Exception {
        initializeComponent();
        CanStop = true;
        IsSendTCPConnected = true;
        //bool to keep track of whether connection attempts have failed in the past. At startup, the 'previous' attempt is assumed successful.
        EventLog.WriteEntry("OpenDentHL7", DateTime.Now.ToLongTimeString() + " - Initialized.");
    }

    protected void onStart(String[] args) throws Exception {
        startManually();
    }

    public void startManually() throws Exception {
        //connect to OD db.
        XmlDocument document = new XmlDocument();
        String pathXml = Path.Combine(Application.StartupPath, "FreeDentalConfig.xml");
        try
        {
            document.Load(pathXml);
        }
        catch (Exception __dummyCatchVar0)
        {
            EventLog.WriteEntry("OpenDentHL7", DateTime.Now.ToLongTimeString() + " - Could not find " + pathXml, EventLogEntryType.Error);
            throw new ApplicationException("Could not find " + pathXml);
        }

        XPathNavigator Navigator = document.CreateNavigator();
        XPathNavigator nav = new XPathNavigator();
        OpenDentBusiness.DataConnection.DBtype = OpenDentBusiness.DatabaseType.MySql;
        nav = Navigator.SelectSingleNode("//DatabaseConnection");
        String computerName = nav.SelectSingleNode("ComputerName").Value;
        String database = nav.SelectSingleNode("Database").Value;
        String user = nav.SelectSingleNode("User").Value;
        String password = nav.SelectSingleNode("Password").Value;
        XPathNavigator verboseNav = Navigator.SelectSingleNode("//HL7verbose");
        if (verboseNav != null && StringSupport.equals(verboseNav.Value, "True"))
        {
            IsVerboseLogging = true;
            EventLog.WriteEntry("OpenDentHL7", "Verbose mode.", EventLogEntryType.Information);
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        try
        {
            //Try to connect to the database directly
            dcon.setDb(computerName,database,user,password,"","",OpenDentBusiness.DataConnection.DBtype);
            //a direct connection does not utilize lower privileges.
            RemotingClient.RemotingRole = RemotingRole.ClientDirect;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new ApplicationException("Connection to database failed.");
        }

        //(Exception ex){
        //check db version
        String dbVersion = PrefC.getString(PrefName.ProgramVersion);
        if (!StringSupport.equals(Application.ProductVersion.ToString(), dbVersion))
        {
            EventLog.WriteEntry("OpenDentHL7", "Versions do not match.  Db version:" + dbVersion + ".  Application version:" + Application.ProductVersion.ToString(), EventLogEntryType.Error);
            throw new ApplicationException("Versions do not match.  Db version:" + dbVersion + ".  Application version:" + Application.ProductVersion.ToString());
        }
         
        if (Programs.isEnabled(ProgramName.eClinicalWorks) && !HL7Defs.isExistingHL7Enabled())
        {
            //eCW enabled, and no HL7def enabled.
            //prevent startup:
            long progNum = Programs.getProgramNum(ProgramName.eClinicalWorks);
            String hl7Server = ProgramProperties.getPropVal(progNum,"HL7Server");
            String hl7ServiceName = ProgramProperties.getPropVal(progNum,"HL7ServiceName");
            if (StringSupport.equals(hl7Server, ""))
            {
                //for the first time run
                ProgramProperties.SetProperty(progNum, "HL7Server", System.Environment.MachineName);
                hl7Server = System.Environment.MachineName;
            }
             
            if (StringSupport.equals(hl7ServiceName, ""))
            {
                //for the first time run
                ProgramProperties.SetProperty(progNum, "HL7ServiceName", this.ServiceName);
                hl7ServiceName = this.ServiceName;
            }
             
            if (hl7Server.ToLower() != System.Environment.MachineName.ToLower())
            {
                EventLog.WriteEntry("OpenDentHL7", "The HL7 Server name does not match the name set in Program Links eClinicalWorks Setup.  Server name: " + System.Environment.MachineName + ", Server name in Program Links: " + hl7Server, EventLogEntryType.Error);
                throw new ApplicationException("The HL7 Server name does not match the name set in Program Links eClinicalWorks Setup.  Server name: " + System.Environment.MachineName + ", Server name in Program Links: " + hl7Server);
            }
             
            if (hl7ServiceName.ToLower() != this.ServiceName.ToLower())
            {
                EventLog.WriteEntry("OpenDentHL7", "The HL7 Service Name does not match the name set in Program Links eClinicalWorks Setup.  Service name: " + this.ServiceName + ", Service name in Program Links: " + hl7ServiceName, EventLogEntryType.Error);
                throw new ApplicationException("The HL7 Service Name does not match the name set in Program Links eClinicalWorks Setup.  Service name: " + this.ServiceName + ", Service name in Program Links: " + hl7ServiceName);
            }
             
            ecwOldSendAndReceive();
            return ;
        }
         
        HL7Def hL7Def = HL7Defs.getOneDeepEnabled();
        if (hL7Def == null)
        {
            return ;
        }
         
        if (StringSupport.equals(hL7Def.HL7Server, ""))
        {
            hL7Def.HL7Server = System.Environment.MachineName;
            HL7Defs.update(hL7Def);
        }
         
        if (StringSupport.equals(hL7Def.HL7ServiceName, ""))
        {
            hL7Def.HL7ServiceName = this.ServiceName;
            HL7Defs.update(hL7Def);
        }
         
        if (hL7Def.HL7Server.ToLower() != System.Environment.MachineName.ToLower())
        {
            EventLog.WriteEntry("OpenDentHL7", "The HL7 Server name does not match the name in the enabled HL7Def Setup.  Server name: " + System.Environment.MachineName + ", Server name in HL7Def: " + hL7Def.HL7Server, EventLogEntryType.Error);
            throw new ApplicationException("The HL7 Server name does not match the name in the enabled HL7Def Setup.  Server name: " + System.Environment.MachineName + ", Server name in HL7Def: " + hL7Def.HL7Server);
        }
         
        if (hL7Def.HL7ServiceName.ToLower() != this.ServiceName.ToLower())
        {
            EventLog.WriteEntry("OpenDentHL7", "The HL7 Service Name does not match the name in the enabled HL7Def Setup.  Service name: " + this.ServiceName + ", Service name in HL7Def: " + hL7Def.HL7ServiceName, EventLogEntryType.Error);
            throw new ApplicationException("The HL7 Service Name does not match the name in the enabled HL7Def Setup.  Service name: " + this.ServiceName + ", Service name in HL7Def: " + hL7Def.HL7ServiceName);
        }
         
        HL7DefEnabled = hL7Def;
        //so we can access it later from other methods
        if (HL7DefEnabled.ModeTx == ModeTxHL7.File)
        {
            hl7FolderOut = HL7DefEnabled.OutgoingFolder;
            hl7FolderIn = HL7DefEnabled.IncomingFolder;
            if (!Directory.Exists(hl7FolderOut))
            {
                EventLog.WriteEntry("OpenDentHL7", "The outgoing HL7 folder does not exist.  Path is set to: " + hl7FolderOut, EventLogEntryType.Error);
                throw new ApplicationException("The outgoing HL7 folder does not exist.  Path is set to: " + hl7FolderOut);
            }
             
            if (!Directory.Exists(hl7FolderIn))
            {
                EventLog.WriteEntry("OpenDentHL7", "The incoming HL7 folder does not exist.  Path is set to: " + hl7FolderIn, EventLogEntryType.Error);
                throw new ApplicationException("The incoming HL7 folder does not exist.  Path is set to: " + hl7FolderIn);
            }
             
            //start polling the folder for waiting messages to import.  Every 5 seconds.
            TimerCallback timercallbackReceive = new TimerCallback(TimerCallbackReceiveFiles);
            timerReceiveFiles = new System.Threading.Timer(timercallbackReceive, null, 5000, 5000);
            //start polling the db for new HL7 messages to send. Every 1.8 seconds.
            TimerCallback timercallbackSend = new TimerCallback(TimerCallbackSendFiles);
            timerSendFiles = new System.Threading.Timer(timercallbackSend, null, 1800, 1800);
        }
        else
        {
            //TCP/IP
            createIncomingTcpListener();
            //start a timer to poll the database and to send messages as needed.  Every 6 seconds.  We increased the time between polling the database from 3 seconds to 6 seconds because we are now waiting 5 seconds for a message acknowledgment from eCW.
            TimerCallback timercallbackSendTCP = new TimerCallback(TimerCallbackSendTCP);
            timerSendTCP = new System.Threading.Timer(timercallbackSendTCP, null, 1800, 6000);
        } 
    }

    private void timerCallbackReceiveFiles(Object stateInfo) throws Exception {
        //process all waiting messages
        if (isReceivingFiles)
        {
            return ;
        }
         
        //already in the middle of processing files
        isReceivingFiles = true;
        String[] existingFiles = Directory.GetFiles(hl7FolderIn);
        for (int i = 0;i < existingFiles.Length;i++)
        {
            ProcessMessageFile(existingFiles[i]);
        }
        isReceivingFiles = false;
    }

    private void processMessageFile(String fullPath) throws Exception {
        String msgtext = "";
        int i = 0;
        while (i < 5)
        {
            try
            {
                msgtext = File.ReadAllText(fullPath);
                break;
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            Thread.Sleep(200);
            i++;
            if (i == 5)
            {
                EventLog.WriteEntry("Could not read text from file due to file locking issues.", EventLogEntryType.Error);
                return ;
            }
             
        }
        try
        {
            MessageHL7 msg = new MessageHL7(msgtext);
            //this creates an entire heirarchy of objects.
            MessageParser.process(msg,IsVerboseLogging);
            if (IsVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Processed message " + msg.MsgType.ToString(), EventLogEntryType.Information);
            }
             
        }
        catch (Exception ex)
        {
            EventLog.WriteEntry(ex.Message + "\r\n" + ex.StackTrace, EventLogEntryType.Error);
            return ;
        }

        try
        {
            File.Delete(fullPath);
        }
        catch (Exception ex)
        {
            EventLog.WriteEntry("Delete failed for " + fullPath + "\r\n" + ex.Message, EventLogEntryType.Error);
        }
    
    }

    protected void onStop() throws Exception {
        //later: inform od via signal that this service has shut down
        ecwOldStop();
        if (timerSendFiles != null)
        {
            timerSendFiles.Dispose();
        }
         
    }

    private void timerCallbackSendFiles(Object stateInfo) throws Exception {
        List<HL7Msg> list = HL7Msgs.getOnePending();
        String filename = new String();
        for (int i = 0;i < list.Count;i++)
        {
            //Right now, there will only be 0 or 1 item in the list.
            filename = CodeBase.ODFileUtils.createRandomFile(hl7FolderOut,".txt");
            File.WriteAllText(filename, list[i].MsgText);
            list[i].HL7Status = HL7MessageStatus.OutSent;
            HL7Msgs.Update(list[i]);
            //set the status to sent.
            HL7Msgs.deleteOldMsgText();
        }
    }

    //This is inside the loop so that it happens less frequently.  To clean up incoming messages, we may move this someday.
    private void createIncomingTcpListener() throws Exception {
        try
        {
            //Use Minimal Lower Layer Protocol (MLLP):
            //To send a message:              StartBlockChar(11) -          Payload            - EndBlockChar(28) - EndDataChar(13).
            //An ack message looks like this: StartBlockChar(11) - AckChar(0x06)/NakChar(0x15) - EndBlockChar(28) - EndDataChar(13).
            //Ack is part of MLLP V2.  In it, every message requires an ack or nak.  It's unclear when a nak would be useful.
            //Also in V2, every incoming message must be persisted by storing in our db.
            //We will just start with version 1 and not do acks at first unless needed.
            socketIncomingMain = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPEndPoint endpointLocal = new IPEndPoint(IPAddress.Any, int.Parse(HL7DefEnabled.IncomingPort));
            socketIncomingMain.Bind(endpointLocal);
            socketIncomingMain.Listen(1);
            //Listen for and queue incoming connection requests.  There should only be one.
            if (IsVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Listening", EventLogEntryType.Information);
            }
             
            //Asynchronously process incoming connection attempts:
            socketIncomingMain.BeginAccept(new AsyncCallback(OnConnectionAccepted), socketIncomingMain);
        }
        catch (Exception ex)
        {
            EventLog.WriteEntry("OpenDentHL7", "Error creating incoming TCP listener\r\n" + ex.Message + "\r\n" + ex.StackTrace, EventLogEntryType.Error);
            throw ex;
        }
    
    }

    //service will stop working at this point.
    /**
    * Runs in a separate thread
    */
    private void onConnectionAccepted(IAsyncResult asyncResult) throws Exception {
        if (IsVerboseLogging)
        {
            EventLog.WriteEntry("OpenDentHL7", "Connection Accepted", EventLogEntryType.Information);
        }
         
        try
        {
            socketIncomingWorker = socketIncomingMain.EndAccept(asyncResult);
            //end the BeginAccept.  Get reference to new Socket.
            //Use the worker socket to wait for data.
            //This is very short for testing.  Once we are confident in splicing together multiple chunks, lengthen this.
            dataBufferIncoming = new byte[8];
            strbFullMsg = new StringBuilder();
            //We will keep reusing the same workerSocket instead of maintaining a list of worker sockets
            //because this program is guaranteed to only have one incoming connection at a time.
            if (IsVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "BeginReceive", EventLogEntryType.Information);
            }
             
            socketIncomingWorker.BeginReceive(dataBufferIncoming, 0, dataBufferIncoming.Length, SocketFlags.None, new AsyncCallback(OnDataReceived), null);
            //the main socket is now free to wait for another connection.
            socketIncomingMain.BeginAccept(new AsyncCallback(OnConnectionAccepted), socketIncomingMain);
        }
        catch (ObjectDisposedException __dummyCatchVar3)
        {
            //Socket has been closed.  Try to start over.
            createIncomingTcpListener();
        }
        catch (Exception ex)
        {
            //If this fails, service stops running
            //not sure what went wrong.
            EventLog.WriteEntry("OpenDentHL7", "Error in OnConnectionAccpeted:\r\n" + ex.Message + "\r\n" + ex.StackTrace, EventLogEntryType.Error);
            throw ex;
        }
    
    }

    //service will stop working at this point.
    /**
    * Runs in a separate thread
    */
    private void onDataReceived(IAsyncResult asyncResult) throws Exception {
        int byteCountReceived = socketIncomingWorker.EndReceive(asyncResult);
        //blocks until data is recieved.
        char[] chars = new char[byteCountReceived];
        Decoder decoder = Encoding.UTF8.GetDecoder();
        decoder.GetChars(dataBufferIncoming, 0, byteCountReceived, chars, 0);
        //doesn't necessarily get all bytes from the buffer because buffer could be half full.
        strbFullMsg.Append(chars);
        //strbFullMsg might already have partial data
        //I think we are guaranteed to have received at least one char.
        boolean isFullMsg = false;
        boolean isMalformed = false;
        if (strbFullMsg.Length == 1 && strbFullMsg[0] == MLLP_ENDMSG_CHAR)
        {
            //the only char in the message is the end char
            strbFullMsg.Clear();
            //this must be the very end of a previously processed message.  Discard.
            isFullMsg = false;
        }
        else //else if(strbFullMsg[0]!=MLLP_START_CHAR) {
        if (strbFullMsg.Length > 0 && strbFullMsg[0] != MLLP_START_CHAR)
        {
            //Malformed message.
            isFullMsg = true;
            //we're going to do this so that the error gets saved in the database further down.
            isMalformed = true;
        }
        else //so that the next two lines won't crash
        //last char is the endmsg char.
        if (strbFullMsg.Length >= 3 && strbFullMsg[strbFullMsg.Length - 1] == MLLP_ENDMSG_CHAR && strbFullMsg[strbFullMsg.Length - 2] == MLLP_END_CHAR)
        {
            //the second-to-the-last char is the end char.
            //we have a complete message
            strbFullMsg.Remove(0, 1);
            //strip off the start char
            strbFullMsg.Remove(strbFullMsg.Length - 2, 2);
            //strip off the end chars
            isFullMsg = true;
        }
        else //so that the next line won't crash
        if (strbFullMsg.Length >= 2 && strbFullMsg[strbFullMsg.Length - 1] == MLLP_END_CHAR)
        {
            //the last char is the end char.
            //we will treat this as a complete message, because the endmsg char is optional.
            //if the endmsg char gets sent in a subsequent block, the code above will discard it.
            strbFullMsg.Remove(0, 1);
            //strip off the start char
            strbFullMsg.Remove(strbFullMsg.Length - 1, 1);
            //strip off the end char
            isFullMsg = true;
        }
        else
        {
            isFullMsg = false;
        }    
        //this is an incomplete message.  Continue to receive more blocks.
        //end of big if statement-------------------------------------------------
        if (!isFullMsg)
        {
            dataBufferIncoming = new byte[8];
            //clear the buffer
            socketIncomingWorker.BeginReceive(dataBufferIncoming, 0, dataBufferIncoming.Length, SocketFlags.None, new AsyncCallback(OnDataReceived), null);
            return ;
        }
         
        //get another block
        //Prepare to save message to database if malformed and not processed
        HL7Msg hl7Msg = new HL7Msg();
        hl7Msg.MsgText = strbFullMsg.ToString();
        strbFullMsg.Clear();
        //ready for the next message
        boolean isProcessed = true;
        String messageControlId = "";
        String ackEvent = "";
        if (isMalformed)
        {
            hl7Msg.HL7Status = HL7MessageStatus.InFailed;
            hl7Msg.Note = "This message is malformed so it was not processed.";
            HL7Msgs.insert(hl7Msg);
            isProcessed = false;
        }
        else
        {
            MessageHL7 messageHl7Object = new MessageHL7(hl7Msg.MsgText);
            try
            {
                //this creates an entire heirarchy of objects.
                MessageParser.process(messageHl7Object,IsVerboseLogging);
                //also saves the message to the db
                messageControlId = messageHl7Object.ControlId;
                ackEvent = messageHl7Object.AckEvent;
            }
            catch (Exception ex)
            {
                EventLog.WriteEntry("OpenDentHL7", "Error in OnDataRecieved when processing message:\r\n" + ex.Message + "\r\n" + ex.StackTrace, EventLogEntryType.Error);
                isProcessed = false;
            }
        
        } 
        MessageHL7 hl7Ack = MessageConstructor.generateACK(messageControlId,isProcessed,ackEvent);
        if (hl7Ack == null)
        {
            EventLog.WriteEntry("OpenDentHL7", "No ACK defined for the enabled HL7 definition or no HL7 definition enabled.", EventLogEntryType.Information);
            return ;
        }
         
        byte[] ackByteOutgoing = Encoding.ASCII.GetBytes(MLLP_START_CHAR + hl7Ack.toString() + MLLP_END_CHAR + MLLP_ENDMSG_CHAR);
        if (IsVerboseLogging)
        {
            EventLog.WriteEntry("OpenDentHL7", "Beginning to send ACK.\r\n" + MLLP_START_CHAR + hl7Ack.toString() + MLLP_END_CHAR + MLLP_ENDMSG_CHAR, EventLogEntryType.Information);
        }
         
        socketIncomingWorker.Send(ackByteOutgoing);
        //this is a locking call
        //eCW uses the same worker socket to send the next message. Without this call to BeginReceive, they would attempt to send again
        //and the send would fail since we were no longer listening in this thread. eCW would timeout after 30 seconds of waiting for their
        //acknowledgement, then they would close their end and create a new socket for the next message. With this call, we can accept message
        //after message without waiting for a new connection.
        dataBufferIncoming = new byte[8];
        //clear the buffer
        socketIncomingWorker.BeginReceive(dataBufferIncoming, 0, dataBufferIncoming.Length, SocketFlags.None, new AsyncCallback(OnDataReceived), null);
    }

    private void timerCallbackSendTCP(Object stateInfo) throws Exception {
        List<HL7Msg> list = HL7Msgs.getOnePending();
        for (int i = 0;i < list.Count;i++)
        {
            //Right now, there will only be 0 or 1 item in the list.
            String sendMsgControlId = HL7Msgs.GetControlId(list[i]);
            //could be empty string
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            String[] strIpPort = HL7DefEnabled.OutgoingIpPort.Split(':');
            //this was already validated in the HL7DefEdit window.
            IPAddress ipaddress = IPAddress.Parse(strIpPort[0]);
            //already validated
            int port = int.Parse(strIpPort[1]);
            //already validated
            IPEndPoint endpoint = new IPEndPoint(ipaddress, port);
            try
            {
                socket.Connect(endpoint);
                if (!IsSendTCPConnected)
                {
                    //Previous run failed to connect. This time connected so make log entry that connection was successful
                    EventLog.WriteEntry("OpenDentHL7", "The HL7 send TCP/IP socket connection failed to connect previously and was successful this time.", EventLogEntryType.Information);
                    IsSendTCPConnected = true;
                }
                 
            }
            catch (SocketException ex)
            {
                if (IsSendTCPConnected)
                {
                    //Previous run connected fine, make log entry and set bool to false
                    EventLog.WriteEntry("OpenDentHL7", "The HL7 send TCP/IP socket connection failed to connect.\r\nException: " + ex.Message, EventLogEntryType.Warning);
                    IsSendTCPConnected = false;
                }
                 
                return ;
            }

            String data = MLLP_START_CHAR + list[i].MsgText + MLLP_END_CHAR + MLLP_ENDMSG_CHAR;
            try
            {
                byte[] byteData = Encoding.ASCII.GetBytes(data);
                socket.Send(byteData);
                //this is a blocking call
                //For MLLP V2, do a blocking Receive here, along with a timeout.
                byte[] ackBuffer = new byte[256];
                //plenty big enough to receive the entire ack/nack response
                socket.ReceiveTimeout = 5000;
                //5 second timeout. Database is polled every 6 seconds for a new message to send, 5 second wait for acknowledgment
                int byteCountReceived = socket.Receive(ackBuffer);
                //blocking Receive
                char[] chars = new char[byteCountReceived];
                Encoding.UTF8.GetDecoder().GetChars(ackBuffer, 0, byteCountReceived, chars, 0);
                StringBuilder strbAckMsg = new StringBuilder();
                strbAckMsg.Append(chars);
                if (strbAckMsg.Length > 0 && strbAckMsg[0] != MLLP_START_CHAR)
                {
                    list[i].Note = list[i].Note + "Malformed acknowledgment.\r\n";
                    HL7Msgs.Update(list[i]);
                    throw new Exception("Malformed acknowledgment.");
                }
                else //last char is the endmsg char.
                if (strbAckMsg.Length >= 3 && strbAckMsg[strbAckMsg.Length - 1] == MLLP_ENDMSG_CHAR && strbAckMsg[strbAckMsg.Length - 2] == MLLP_END_CHAR)
                {
                    //the second-to-the-last char is the end char.
                    //we have a complete message
                    strbAckMsg.Remove(0, 1);
                    //strip off the start char
                    strbAckMsg.Remove(strbAckMsg.Length - 2, 2);
                }
                else //strip off the end chars
                //so that the next line won't crash
                if (strbAckMsg.Length >= 2 && strbAckMsg[strbAckMsg.Length - 1] == MLLP_END_CHAR)
                {
                    //the last char is the end char.
                    //we will treat this as a complete message, because the endmsg char is optional.
                    strbAckMsg.Remove(0, 1);
                    //strip off the start char
                    strbAckMsg.Remove(strbFullMsg.Length - 1, 1);
                }
                else
                {
                    //strip off the end char
                    list[i].Note = list[i].Note + "Malformed acknowledgment.\r\n";
                    HL7Msgs.Update(list[i]);
                    throw new Exception("Malformed acknowledgment.");
                }   
                MessageHL7 ackMsg = new MessageHL7(strbAckMsg.ToString());
                try
                {
                    MessageParser.processAck(ackMsg,IsVerboseLogging);
                }
                catch (Exception ex)
                {
                    list[i].Note = list[i].Note + ackMsg.toString() + "\r\nError processing acknowledgment.\r\n";
                    HL7Msgs.Update(list[i]);
                    throw new Exception("Error processing acknowledgment.\r\n" + ex.Message);
                }

                if (StringSupport.equals(ackMsg.AckCode, "") || StringSupport.equals(ackMsg.ControlId, ""))
                {
                    list[i].Note = list[i].Note + ackMsg.toString() + "\r\nInvalid ACK message.  Attempt to resend.\r\n";
                    HL7Msgs.Update(list[i]);
                    throw new Exception("Invalid ACK message received.");
                }
                 
                if (StringSupport.equals(ackMsg.ControlId, sendMsgControlId) && StringSupport.equals(ackMsg.AckCode, "AA"))
                {
                    //acknowledged received (Application acknowledgment: Accept)
                    list[i].Note = list[i].Note + ackMsg.toString() + "\r\nMessage ACK (acknowledgment) received.\r\n";
                }
                else if (StringSupport.equals(ackMsg.ControlId, sendMsgControlId) && !StringSupport.equals(ackMsg.AckCode, "AA"))
                {
                    //ACK received for this message, but ack code was not acknowledgment accepted
                    if (list[i].Note.Contains("NACK4"))
                    {
                        //this is the 5th negative acknowledgment, don't try again
                        list[i].Note = list[i].Note + "Ack code: " + ackMsg.AckCode + "\r\nThis is NACK5, the message status has been changed to OutFailed. We will not attempt to send again.\r\n";
                        list[i].HL7Status = HL7MessageStatus.OutFailed;
                    }
                    else if (list[i].Note.Contains("NACK"))
                    {
                        //failed sending at least once already
                        list[i].Note = list[i].Note + "Ack code: " + ackMsg.AckCode + "\r\nNACK" + list[i].Note.Split(new String[]{ "NACK" }, StringSplitOptions.None).Length + "\r\n";
                    }
                    else
                    {
                        list[i].Note = list[i].Note + "Ack code: " + ackMsg.AckCode + "\r\nMessage NACK (negative acknowlegment) received. We will try to send again.\r\n";
                    }  
                    HL7Msgs.Update(list[i]);
                    return ;
                }
                else
                {
                    //Add NACK note to hl7msg table entry
                    //socket closed in finally
                    //ack received for control ID that does not match the control ID of message just sent
                    list[i].Note = list[i].Note + "Sent message control ID: " + sendMsgControlId + "\r\nAck message control ID: " + ackMsg.ControlId + "\r\nAck received for message other than message just sent.  We will try to send again.\r\n";
                    HL7Msgs.Update(list[i]);
                    return ;
                }  
            }
            catch (SocketException ex)
            {
                EventLog.WriteEntry("OpenDentHL7", "The HL7 send TCP/IP socket encountered an error when sending message or receiving acknowledgment.\r\nSocket Exception: " + ex.Message, EventLogEntryType.Warning);
                return ;
            }
            catch (Exception ex)
            {
                //Try again in 3 seconds. socket closed in finally
                EventLog.WriteEntry("OpenDentHL7", "The HL7 send TCP/IP encountered an error when sending message or receiving acknowledgment.\r\nException: " + ex.Message, EventLogEntryType.Warning);
                return ;
            }
            finally
            {
                //socket closed in finally
                if (socket != null)
                {
                    socket.Shutdown(SocketShutdown.Both);
                    socket.Close();
                }
                 
            }
            list[i].HL7Status = HL7MessageStatus.OutSent;
            HL7Msgs.Update(list[i]);
            //set the status to sent and save ack message in Note field.
            HL7Msgs.deleteOldMsgText();
        }
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        components = new System.ComponentModel.Container();
    }

}
//This is inside the loop so that it happens less frequently.  To clean up incoming messages, we may move this someday.//this.ServiceName = "Service1";

//This is inside the loop so that it happens less frequently.  To clean up incoming messages, we may move this someday.//this.ServiceName = "Service1";