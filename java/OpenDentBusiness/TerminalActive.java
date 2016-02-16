//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.TerminalActive;
import OpenDentBusiness.TerminalStatusEnum;

/**
* Each row is one computer that currently acting as a terminal for new patient info input.
*/
public class TerminalActive  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TerminalActiveNum = new long();
    /**
    * The name of the computer where the terminal is active.
    */
    public String ComputerName = new String();
    /**
    * Enum:TerminalStatusEnum  No longer used.  Instead, the PatNum field is used.  Used to indicates at what point the patient was in the sequence. 0=standby, 1=PatientInfo, 2=Medical, 3=UpdateOnly.  If status is 1, then nobody else on the network could open the patient edit window for that patient.
    */
    public TerminalStatusEnum TerminalStatus = TerminalStatusEnum.Standby;
    /**
    * FK to patient.PatNum.  The patient currently showing in the terminal.  If 0, then terminal is in standby mode.
    */
    public long PatNum = new long();
    /**
    * 
    */
    public TerminalActive copy() throws Exception {
        TerminalActive t = new TerminalActive();
        t.TerminalActiveNum = TerminalActiveNum;
        t.ComputerName = ComputerName;
        t.TerminalStatus = TerminalStatus;
        t.PatNum = PatNum;
        return t;
    }

}


