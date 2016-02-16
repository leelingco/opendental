//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.LCC.Disposable;
import OpenDental.Eclaims.FormTerminal;

/**
* Summary description for FormTerminal.
*/
public class FormTerminal  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textTx = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textRx = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProgress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private Rs232 moRS232 = new Rs232();
    /**
    * The character array of received bytes.
    */
    private StringBuilder RxBuff = new StringBuilder();
    /**
    * CTRL-A. Start Of Header?
    */
    private static final char SOH = (char)1;
    /**
    * CTRL-D. End Of Transmission
    */
    private static final char EOT = (char)4;
    /**
    * CTRL-F. Positive ACKnowledgement
    */
    private static final char ACK = (char)6;
    /**
    * CTRL-U. Negative AcKnowledgement
    */
    private static final char NAK = (char)21;
    //
    /**
    * CTRL-X. CANcel
    */
    private static final char CAN = (char)24;
    /**
    * 
    */
    public FormTerminal() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        moRS232 = new Rs232();
        moRS232.Port = 1;
        moRS232.BaudRate = 9600;
        moRS232.DataBit = 8;
        moRS232.StopBit = Rs232.DataStopBit.StopBit_1;
        moRS232.Parity = Rs232.DataParity.Parity_None;
        moRS232.Timeout = 1500;
        moRS232.CommEvent += new Rs232.CommEventHandler(moRS232_CommEvent);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTerminal.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textTx = new System.Windows.Forms.TextBox();
        this.textRx = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textProgress = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(25, 0);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 20);
        this.label1.TabIndex = 0;
        this.label1.Text = "Sent";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textTx
        //
        this.textTx.Location = new System.Drawing.Point(25, 22);
        this.textTx.Multiline = true;
        this.textTx.Name = "textTx";
        this.textTx.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textTx.Size = new System.Drawing.Size(452, 28);
        this.textTx.TabIndex = 1;
        //
        // textRx
        //
        this.textRx.Location = new System.Drawing.Point(25, 83);
        this.textRx.Multiline = true;
        this.textRx.Name = "textRx";
        this.textRx.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textRx.Size = new System.Drawing.Size(452, 41);
        this.textRx.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(25, 61);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 20);
        this.label2.TabIndex = 2;
        this.label2.Text = "Received";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textProgress
        //
        this.textProgress.Location = new System.Drawing.Point(25, 157);
        this.textProgress.Multiline = true;
        this.textProgress.Name = "textProgress";
        this.textProgress.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textProgress.Size = new System.Drawing.Size(452, 418);
        this.textProgress.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(25, 135);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 20);
        this.label3.TabIndex = 5;
        this.label3.Text = "Progress";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormTerminal
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(637, 612);
        this.Controls.Add(this.textProgress);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textRx);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textTx);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormTerminal";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Modem Terminal";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormTerminal_Closing);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void button1_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /**
    * 
    */
    public void openConnection(int port) throws Exception {
        moRS232.Port = port;
        moRS232.Open();
        moRS232.Dtr = true;
        moRS232.Rts = true;
        moRS232.EnableEvents();
        textProgress.Text += "Modem Connection Opened\r\n";
        RxBuff = new StringBuilder();
    }

    /**
    * 
    */
    public void closeConnection() throws Exception {
        textProgress.Text += "Modem Connection Closed\r\n";
        scrollProgress();
        moRS232.Close();
    }

    /**
    * 
    */
    public void dial(String phone) throws Exception {
        moRS232.PurgeBuffer(Rs232.PurgeBuffers.TxClear | Rs232.PurgeBuffers.RXClear);
        String str = "ATDT" + phone + "\r\n";
        textTx.Text += str;
        textProgress.Text += "Dialed: " + phone + "\r\n";
        moRS232.Write(str);
    }

    /**
    * 
    */
    public void send(String str) throws Exception {
        moRS232.PurgeBuffer(Rs232.PurgeBuffers.TxClear | Rs232.PurgeBuffers.RXClear);
        str += "\r\n";
        textTx.Text += str;
        textProgress.Text += "Sent: " + str;
        scrollProgress();
        moRS232.Write(str);
    }

    /**
    * 
    */
    public void send(byte[] block) throws Exception {
        moRS232.PurgeBuffer(Rs232.PurgeBuffers.TxClear | Rs232.PurgeBuffers.RXClear);
        textTx.Text += "**block**\r\n";
        textProgress.Text += "Sent block\r\n";
        scrollProgress();
        moRS232.Write(block);
    }

    /**
    * 
    */
    public void send(char singleChar) throws Exception {
        moRS232.PurgeBuffer(Rs232.PurgeBuffers.TxClear | Rs232.PurgeBuffers.RXClear);
        if (isSpecialCode(singleChar))
        {
            textTx.Text += displaySpecialCode(singleChar) + "\r\n";
            textProgress.Text += "Sent " + displaySpecialCode(singleChar) + "\r\n";
        }
        else
        {
            textTx.Text += singleChar.ToString() + "\r\n";
            textProgress.Text += "Sent char " + singleChar.ToString() + "\r\n";
        } 
        scrollProgress();
        moRS232.Write(new byte[]{ (byte)singleChar });
    }

    /**
    * Throws an exception if expected text not received within timeout period.
    */
    public void waitFor(String expectedText, double timeoutMS) throws Exception {
        if (timeoutMS > 60000)
        {
            throw new Exception("Not allowed to wait longer than 60 seconds");
        }
         
        textProgress.Text += "Waiting for '" + expectedText + "'\r\n";
        scrollProgress();
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            if (RxBuff.ToString().IndexOf(expectedText) != -1)
            {
                textProgress.Text += "Receieved: " + expectedText + "'\r\n";
                scrollProgress();
                return ;
            }
             
            Application.DoEvents();
        }
        throw new Exception("Timed out waiting for " + expectedText + ". Actual text received so far: '" + charsToString(RxBuff) + "'");
    }

    /**
    * Throws an exception if expected text not received within timeout period. Returns the response received out of the given expectedReplies.
    */
    public String waitFor(String expectedText1, String expectedText2, double timeoutMS) throws Exception {
        if (timeoutMS > 60000)
        {
            throw new Exception("Not allowed to wait longer than 60 seconds");
        }
         
        textProgress.Text += "Waiting for '" + expectedText1 + "' or '" + expectedText2 + "'\r\n";
        scrollProgress();
        //textProgress.Refresh();
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            if (RxBuff.ToString().IndexOf(expectedText1) != -1)
            {
                textProgress.Text += "Receieved: " + expectedText1 + "'\r\n";
                scrollProgress();
                return expectedText1;
            }
             
            if (RxBuff.ToString().IndexOf(expectedText2) != -1)
            {
                textProgress.Text += "Receieved: " + expectedText2 + "'\r\n";
                scrollProgress();
                return expectedText2;
            }
             
            Application.DoEvents();
        }
        throw new Exception("Timed out waiting for " + expectedText1 + " or " + expectedText2 + ". Actual text received so far: '" + charsToString(RxBuff) + "'");
    }

    /**
    * Throws an exception if expected byte not received within timeout period.
    */
    public void waitFor(byte expectedByte, double timeoutMS) throws Exception {
        if (timeoutMS > 60000)
        {
            throw new Exception("Not allowed to wait longer than 60 seconds");
        }
         
        textProgress.Text += "Waiting for byte " + expectedByte.ToString() + "\r\n";
        scrollProgress();
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            for (int i = 0;i < RxBuff.Length;i++)
            {
                if (RxBuff[i] == (char)expectedByte)
                {
                    if (isSpecialCode((char)expectedByte))
                    {
                        textProgress.Text += "Receieved expected byte: " + displaySpecialCode((char)expectedByte) + "'\r\n";
                    }
                    else
                    {
                        textProgress.Text += "Receieved expected byte: " + expectedByte.ToString() + "'\r\n";
                    } 
                    scrollProgress();
                    return ;
                }
                 
            }
            Application.DoEvents();
        }
        throw new Exception("Timed out waiting for byte " + expectedByte.ToString() + ". Actual text received so far: '" + charsToString(RxBuff) + "'");
    }

    /**
    * Gets a single byte from the RxBuff. Throws exception if no byte received within the given time. Does not clear this byte from the RxBuff in any way.  That's up to the calling code.
    */
    public byte getOneByte(double timeoutMS) throws Exception {
        if (timeoutMS > 60000)
        {
            throw new Exception("Not allowed to wait longer than 60 seconds");
        }
         
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            if (RxBuff.Length > 0)
            {
                if (IsSpecialCode(RxBuff[RxBuff.Length - 1]))
                {
                    textProgress.Text += "Receieved: byte " + DisplaySpecialCode(RxBuff[RxBuff.Length - 1]) + "\r\n";
                }
                else
                {
                    textProgress.Text += "Receieved: byte " + RxBuff[RxBuff.Length - 1].ToString() + "'\r\n";
                } 
                scrollProgress();
                return (byte)RxBuff[RxBuff.Length - 1];
            }
             
            Application.DoEvents();
        }
        throw new Exception("Timed out.  No bytes received yet.");
    }

    /**
    * Gets a precise number of bytes from RxBuff. Throws an error if not received in time.  Also, make sure to clear RxBuff before and after using this function.
    */
    public byte[] getBytes(int numberOfBytes, double timeoutMS) throws Exception {
        if (timeoutMS > 60000)
        {
            throw new Exception("Not allowed to wait longer than 60 seconds");
        }
         
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            if (RxBuff.Length >= numberOfBytes)
            {
                textProgress.Text += "Receieved block\r\n";
                scrollProgress();
                byte[] retVal = new byte[numberOfBytes];
                for (int i = 0;i < numberOfBytes;i++)
                {
                    retVal[i] = (byte)RxBuff[i];
                }
                return retVal;
            }
             
            Application.DoEvents();
        }
        throw new Exception("Timed out.  " + numberOfBytes.ToString() + " bytes not received yet.");
    }

    /**
    * 
    */
    public void clearRxBuff() throws Exception {
        RxBuff = new StringBuilder();
    }

    /**
    * Receives all text within the given timespan.
    */
    public String receive(double timeoutMS) throws Exception {
        if (timeoutMS > 20000)
        {
            throw new Exception("Not allowed to wait longer than 20 seconds");
        }
         
        textProgress.Text += "Receiving...\r\n";
        scrollProgress();
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(timeoutMS) > DateTime.Now)
        {
            Application.DoEvents();
        }
        textProgress.Text += "Receieved: " + charsToString(RxBuff) + "'\r\n";
        scrollProgress();
        return charsToString(RxBuff);
    }

    private void scrollProgress() throws Exception {
        textProgress.SelectionStart = textProgress.Text.Length;
        textProgress.ScrollToCaret();
    }

    /**
    * Converts the char array to a display string. Any of the 5 special chars are transformed into meaningful display strings.
    */
    private String charsToString(StringBuilder inputChars) throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0;i < inputChars.Length;i++)
        {
            if (IsSpecialCode(inputChars[i]))
            {
                strBuilder.Append(DisplaySpecialCode(inputChars[i]));
            }
            else
            {
                strBuilder.Append(inputChars[i]);
            } 
        }
        return strBuilder.ToString();
    }

    /**
    * 
    */
    public void pause(double ms) throws Exception {
        if (ms > 20000)
        {
            throw new Exception("Not allowed to pause longer than 20 seconds");
        }
         
        textProgress.Text += "Pausing for " + ms.ToString() + " ms\r\n";
        scrollProgress();
        DateTime startTime = DateTime.Now;
        while (startTime.AddMilliseconds(ms) > DateTime.Now)
        {
            Application.DoEvents();
        }
        textProgress.Text += "Done pausing\r\n";
        scrollProgress();
    }

    //textProgress.Refresh();
    /**
    * Transmits a file using Xmodem protocol.
    */
    public void uploadXmodem(String filePath) throws Exception {
        textProgress.Text += "Sending " + filePath + " by Xmodem\r\n";
        //divide file into 128 byte packets
        byte[][] bytes = new byte[][]();
        FileStream fs = File.Open(filePath, FileMode.Open, FileAccess.Read);
        try
        {
            {
                int numberPackets = (int)Math.Ceiling((double)fs.Length / 128);
                bytes = new byte[numberPackets];
                byte[] buffer = new byte[]();
                for (int i = 0;i < numberPackets;i++)
                {
                    //this will usually be 128 bytes long, except for the last loop
                    buffer = new byte[128];
                    fs.Read(buffer, 0, 128);
                    bytes[i] = new byte[128];
                    buffer.CopyTo(bytes[i], 0);
                }
            }
        }
        finally
        {
            if (fs != null)
                Disposable.mkDisposable(fs).dispose();
             
        }
        //GetPacketNumber. If greater than 255, repeatedly subtract 256
        //1's complement = 255-packet#
        //checksum=sum of all bytes. If greater than 255, repeatedly subtract 256
        //Actual send:
        //wait for NAK:
        WaitFor((byte)NAK, 50000);
        //if wait longer than given time, then throw timout exception
        byte[] block = new byte[]();
        byte packetNumber = new byte();
        byte response = new byte();
        for (int i = 0;i < bytes.GetLength(0);i++)
        {
            SendPacket:// (block):
            block = new byte[132];
            //1: SOH byte
            block[0] = (byte)SOH;
            //2: packet Number
            packetNumber = (byte)Math.IEEERemainder((double)i, (double)256);
            block[1] = packetNumber;
            //3: 1's complement of packet number
            block[2] = (byte)(255 - packetNumber);
            //4: the packet
            bytes[i].CopyTo(block, 3);
            //5: checksum
            block[131] = GetCheckSum(block);
            Send(block);
            GetResponse:clearRxBuff();
            response = GetOneByte(40000);
            if (response == (byte)CAN)
            {
                throw new Exception("Transfer cancelled by receiver");
            }
            else if (response == (byte)NAK)
            {
                goto SendPacket
            }
            else //resend
            if (response != (byte)ACK)
            {
                goto GetResponse
            }
               
        }
        //if anything other than ACK received
        //get another byte
        //Note: the gotos will not result in any infinite loops, because even in the worst case
        //scenario, the sender will give up and quit sending responses, resulting in a timeout.
        //Once all blocks sent, sent EOT
        send(EOT);
        //If receive NAK, send another EOT
        WaitFor((byte)NAK, 40000);
        send(EOT);
        //If receive ACK, then done.
        WaitFor((byte)ACK, 40000);
    }

    //Note.  Allowed to send a CAN byte (2 is better) between blocks to cancel upload
    private byte getCheckSum(byte[] input) throws Exception {
        byte retVal = 0;
        for (int i = 0;i < input.Length;i++)
        {
            retVal += input[i];
        }
        return (byte)Math.IEEERemainder((double)retVal, (double)256);
    }

    /**
    * Receives a file using Xmodem protocol.
    */
    public void downloadXmodem(String filePath) throws Exception {
        textProgress.Text += "Retrieving " + filePath + " by Xmodem\r\n";
        //send ACK
        int attempts = 0;
        clearRxBuff();
        while (attempts < 5)
        {
            attempts++;
            send(ACK);
            try
            {
                GetOneByte(5000);
                break;
            }
            catch (Exception __dummyCatchVar0)
            {
                continue;
            }
        
        }
        //if no response within given time, then try sending another ACK
        //Send up to about 5 ACKs before giving up
        byte packetNumber = 0;
        byte[] block = new byte[]();
        while (true)
        {
            //response){
            clearRxBuff();
            //receive a block 132 bytes long
            block = GetBytes(132, 30000);
            //1: verify SOH. If not present, send NAK.
            if (block[0] != (byte)SOH)
            {
                send(NAK);
                continue;
            }
             
            //2: verify packet number incrementing correctly. If wrong, send CAN
            if (block[1] != packetNumber)
            {
                send(CAN);
            }
             
        }
    }

    //3: verify 1's complement of packet number. If wrong, send CAN
    //4: temporarily store the packet of 128 bytes
    //5: validate the checksum.
    //If checksum correct, then add packet to the file. Send ACK
    //If incorrect, send NAK and prepare to receive this block again.
    //If EOT received instead of SOH, send NAK
    //If receive another EOT, send ACK
    //transfer complete
    //Note: Allowed to to cancel at any time by sending a CAN byte (2 is better)
    /*
    			byte response;
    			for(int i=0;i<bytes.GetLength(0);i++){
    			SendPacket:// (block):
    				block=new byte[132];
    				//1: SOH byte
    				block[0]=(byte)SOH;
    				//2: packet Number
    				packetNumber=(byte)Math.IEEERemainder((double)i,(double)256);
    				block[1]=packetNumber;
    				//3: 1's complement of packet number
    				block[2]=(byte)(255-packetNumber);
    				//4: the packet
    				bytes[i].CopyTo(block,3);
    				//5: checksum
    				block[131]=GetCheckSum(block);
    				Send(block);
    			GetResponse:*/
    /**
    * Events raised when a communication event occurs.
    */
    private void moRS232_CommEvent(Rs232 source, Rs232.EventMasks Mask) throws Exception {
        if ((Mask & Rs232.EventMasks.RxChar) > 0)
        {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0;i < source.InputStream.Length;i++)
            {
                //loop through each new char and handle it.
                RxBuff.Append((char)source.InputStream[i]);
                if (isSpecialCode((char)source.InputStream[i]))
                {
                    strBuilder.Append(displaySpecialCode((char)source.InputStream[i]));
                }
                else
                {
                    strBuilder.Append((char)source.InputStream[i]);
                } 
            }
            textRx.Text += strBuilder.ToString();
        }
         
    }

    private boolean isSpecialCode(char inputChar) throws Exception {
        if ((int)inputChar >= 32)
        {
            return false;
        }
         
        return true;
    }

    /*
    			if(inputChar==SOH
    				|| inputChar==EOT
    				|| inputChar==ACK
    				|| inputChar==NAK
    				|| inputChar==CAN)
    			{
    				return true;
    			}
    			return false;*/
    /**
    * Test if IsSpecialCode first.  Then, this returns a string representation for display purposes only.
    */
    private String displaySpecialCode(char inputChar) throws Exception {
        if (inputChar == SOH)
        {
            return "<SOH>";
        }
         
        if (inputChar == EOT)
        {
            return "<EOT>";
        }
         
        if (inputChar == ACK)
        {
            return "<ACK>";
        }
         
        if (inputChar == NAK)
        {
            return "<NAK>";
        }
         
        if (inputChar == CAN)
        {
            return "<CAN>";
        }
         
        //Line Feed
        if ((int)inputChar == 10 || (int)inputChar == 13)
        {
            return inputChar.ToString();
        }
         
        //Carriage Return
        if ((int)inputChar < 32)
        {
            return "<" + ((int)inputChar).ToString() + ">";
        }
         
        return "";
    }

    /**
    * Converts any string to an acceptable format for modem ASCII. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    public String sout(String intputStr, int maxL, int minL) throws Exception {
        String retStr = intputStr.ToUpper();
        //Debug.Write(retStr+",");
        retStr = Regex.Replace(retStr, "[^\\w!\"&'\\(\\)\\+,-\\./;\\?= ]", "");
        //replaces characters in this input string
        //Allowed: !"&'()+,-./;?=(space)
        //[](any single char)^(that is not)\w(A-Z or 0-9) or one of the above chars.
        //retStr=Regex.Replace(retStr,"[_]","");//replaces _
        if (maxL != -1)
        {
            if (retStr.Length > maxL)
            {
                retStr = retStr.Substring(0, maxL);
            }
             
        }
         
        if (minL != -1)
        {
            if (retStr.Length < minL)
            {
                retStr = retStr.PadRight(minL, ' ');
            }
             
        }
         
        return retStr;
    }

    //Debug.WriteLine(retStr);
    /**
    * Converts any string to an acceptable format for modem ASCII. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    public String sout(String str, int maxL) throws Exception {
        return sout(str,maxL,-1);
    }

    /**
    * Converts any string to an acceptable format for modem ASCII. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    public String sout(String str) throws Exception {
        return sout(str,-1,-1);
    }

    private void formTerminal_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        closeConnection();
    }

}


