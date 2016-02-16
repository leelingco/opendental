//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcNote;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class ProcNotes   
{
    public static long insert(ProcNote procNote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            procNote.ProcNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), procNote);
            return procNote.ProcNoteNum;
        }
         
        return Crud.ProcNoteCrud.Insert(procNote);
    }

    public static ProcNote getProcNotesForPat(long patNum, DateTime dateStart, DateTime dateEnd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ProcNote>GetObject(MethodBase.GetCurrentMethod(), patNum, dateStart, dateEnd);
        }
         
        String command = "SELECT procnote.* FROM procnote " + "INNER JOIN procedurelog ON procedurelog.ProcNum=procnote.ProcNum " + "WHERE procnote.PatNum=" + POut.long(patNum) + " " + "AND procnote.EntryDateTime BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND procedurelog.ProcStatus!=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + " " + "ORDER BY procnote.EntryDateTime DESC LIMIT 1";
        return Crud.ProcNoteCrud.SelectOne(command);
    }

}


/*
		///<summary></summary>
		internal static bool PreviousNoteExists(int procNum){
			string command="SELECT COUNT(*) FROM procnote WHERE ProcNum="+POut.PInt(procNum);
			DataConnection dcon=new DataConnection();
			if(dcon.GetCount(command)=="0"){
				return false;
			}
			return true;
		}*/