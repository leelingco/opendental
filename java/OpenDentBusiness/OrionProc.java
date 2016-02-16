//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.OrionDPC;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.TableBase;

/**
* This table is only used by one customer.  1:1 relationship to procedurelog table.
*/
public class OrionProc  extends TableBase 
{
    /**
    * Primary key.
    */
    public long OrionProcNum = new long();
    /**
    * FK to procedurelog.ProcNum
    */
    public long ProcNum = new long();
    /**
    * Enum:OrionDPC NotSpecified=0,None=1,_1A=2,_1B=3,_1C=4,_2=5,_3=6,_4=7,_5=8.
    */
    public OrionDPC DPC = OrionDPC.NotSpecified;
    /**
    * Enum:OrionDPC None=0,1A=1,1B=2,1C=3,2=4,3=5,4=6,5=7
    */
    public OrionDPC DPCpost = OrionDPC.NotSpecified;
    /**
    * System adds days to the diagnosis date based upon the DPC entered for that procedure. If DPC = none the system will return “No Schedule by Date”.
    */
    public DateTime DateScheduleBy = new DateTime();
    /**
    * Default to current date.  Provider shall have to ability to edit with a previous date, but not a future date.
    */
    public DateTime DateStopClock = new DateTime();
    /**
    * Enum:OrionStatus None=0,TP=1,C=2,E=4,R=8,RO=16,CS=32,CR=64,CA-Tx=128,CA-ERPD=256,CA-P/D=512,S=1024,ST=2048,W=4096,A=8192
    */
    public OrionStatus Status2 = OrionStatus.None;
    /**
    * .
    */
    public boolean IsOnCall = new boolean();
    /**
    * Indicates in the clinical note that effective communication was used for this encounter.
    */
    public boolean IsEffectiveComm = new boolean();
    /**
    * .
    */
    public boolean IsRepair = new boolean();
    public OrionProc copy() throws Exception {
        return (OrionProc)this.MemberwiseClone();
    }

}


