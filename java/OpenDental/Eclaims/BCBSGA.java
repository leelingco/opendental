//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.FormTerminal;
import OpenDental.Eclaims.x837Controller;
import OpenDentBusiness.Clearinghouse;

/**
* Summary description for BCBSGA.
*/
public class BCBSGA   
{
    /**
    * 
    */
    public BCBSGA() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. If they failed, a rollback will happen automatically by deleting the previously created X12 file. The batchnum is supplied for the possible rollback.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        boolean retVal = true;
        FormTerminal FormT = new FormTerminal();
        try
        {
            FormT.Show();
            FormT.OpenConnection(clearhouse.ModemPort);
            //1. Dial
            FormT.dial("17065713158");
            //2. Wait for connect, then pause 3 seconds
            FormT.WaitFor("CONNECT 9600", 50000);
            FormT.Pause(3000);
            FormT.clearRxBuff();
            //3. Send Submitter login record
            //position,length indicated for each
            //1,6 /SLRON=Submitter login
            //7,12 Submitter ID
            //19,8 submitter password
            //27,3 use NAT
            //30,8 suggested 8-BYTE CRC of the file for unique ID. No spaces.
            //But I used the batch number instead
            String submitterLogin = "/SLRON" + FormT.sout(clearhouse.LoginID,12,12) + FormT.sout(clearhouse.Password,8,8) + "NAT" + batchNum.ToString().PadLeft(8, '0') + "ANSI837D  1    " + "X" + "ANSI" + "BCS" + "00";
            //38,15 "ANSI837D  1    "=Dental claims
            //53,1 X=Xmodem, or Y for transmission protocol
            //54,4 use ANSI
            //58,3 BCS=BlueCrossBlueShield
            //61,2 use 00 for filler
            FormT.send(submitterLogin);
            //4. Receive Y, indicating login accepted
            if (StringSupport.equals(FormT.WaitFor("Y", "N", 20000), "Y"))
            {
                //5. Wait 1 second.
                FormT.Pause(1000);
            }
            else
            {
                throw new Exception(FormT.Receive(5000));
            } 
            //6. If login rejected, receive an N,
            //followed by Transmission acknowledgement explaining
            //7. Send file using X-modem or Z-modem
            //slash not handled properly if missing:
            FormT.uploadXmodem(clearhouse.ExportPath + "claims" + batchNum.ToString() + ".txt");
            //8. After transmitting, pause for 1 second.
            FormT.Pause(1000);
            FormT.clearRxBuff();
            //9. Send submitter logout record
            //1,7 /SLROFF=Submitter logout
            //8,12 Submitter ID
            String submitterLogout = "/SLROFF" + FormT.sout(clearhouse.LoginID,12,12) + batchNum.ToString().PadLeft(8, '0') + "!" + "\r\n";
            //20,8 matches field in submitter Login
            //28,1 use ! to retrieve transmission acknowledgement record
            FormT.send(submitterLogout);
            //10. Prepare to receive the Transmission acknowledgement.  This is a receipt.
            FormT.Receive(5000);
        }
        catch (Exception ex)
        {
            //this is displayed in the progress box so user can see.
            MessageBox.Show(ex.Message);
            x837Controller.rollback(clearhouse,batchNum);
            retVal = false;
        }
        finally
        {
            FormT.closeConnection();
        }
        return retVal;
    }

    /**
    * Retrieves any waiting reports from this clearinghouse. Returns true if the communications were successful, and false if they failed.
    */
    public static boolean retrieve(Clearinghouse clearhouse) throws Exception {
        boolean retVal = true;
        FormTerminal FormT = new FormTerminal();
        try
        {
            FormT.Show();
            FormT.OpenConnection(clearhouse.ModemPort);
            FormT.dial("17065713158");
            //2. Wait for connect, then pause 3 seconds
            FormT.WaitFor("CONNECT 9600", 50000);
            FormT.Pause(3000);
            FormT.clearRxBuff();
            //1. Send submitter login record
            //1,6 /SLRON=Submitter login
            //7,12 Submitter ID
            String submitterLogin = "/SLRON" + FormT.sout(clearhouse.LoginID,12,12) + FormT.sout(clearhouse.Password,8,8) + "   " + "12345678" + "*              " + "X" + "MDD " + "VND" + "00";
            //19,8 submitter password
            //27,3 use 3 spaces
            //Possible issue with Trans ID
            //30,8. they did not explain this field very well in documentation
            //38,15 "    *          "=All available. spacing ok?
            //53,1 X=Xmodem, or Y for transmission protocol
            //54,4 use 'MDD '
            //58,3 Vendor ID is yet to be assigned by BCBS
            //61,2 Software version not important
            byte response = (byte)'Y';
            String retrieveFile = "";
            while (response == (byte)'Y')
            {
                FormT.clearRxBuff();
                FormT.send(submitterLogin);
                response = 0;
                while (response != (byte)'N' && response != (byte)'Y' && response != (byte)'Z')
                {
                    response = FormT.GetOneByte(20000);
                    FormT.clearRxBuff();
                    Application.DoEvents();
                }
                //2. If not accepted, N is returned
                //3. and must receive transmission acknowledgement
                if (response == (byte)'N')
                {
                    MessageBox.Show(FormT.Receive(10000));
                    break;
                }
                 
                //4. If login accepted, but no records, Z is returned. Hang up.
                if (response == (byte)'Z')
                {
                    MessageBox.Show("No reports to retrieve");
                    break;
                }
                 
                //5. If record(s) available, Y is returned, followed by dos filename and 32 char subj.
                //less than one second since all text is supposed to immediately follow the Y
                retrieveFile = FormT.Receive(800).Substring(0, 12);
                //12 char in dos filename
                FormT.clearRxBuff();
                //6. Pause for 1 second. (already mostly handled);
                FormT.Pause(200);
                //7. Receive file using Xmodem
                //path must include trailing slash for now.
                FormT.downloadXmodem(clearhouse.ResponsePath + retrieveFile);
                //8. Pause for 5 seconds.
                FormT.Pause(5000);
            }
        }
        catch (Exception ex)
        {
            //9. Repeat all steps including login until a Z is returned.
            MessageBox.Show(ex.Message);
            //FormT.Close();//Also closes connection
            retVal = false;
        }
        finally
        {
            FormT.closeConnection();
        }
        return retVal;
    }

}


