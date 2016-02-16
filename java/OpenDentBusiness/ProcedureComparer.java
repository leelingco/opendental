//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Tooth;

/*================================================================================================================
	=========================================== class ProcedureComparer =============================================*/
/**
* This sorts procedures based on priority, then tooth number, then code (but if Canadian lab code, uses proc code here instead of lab code).  Finally, if comparing a proc and its Canadian lab code, it puts the lab code after the proc.  It does not care about dates or status.  Currently used in TP module only.  The Chart module, Account module, and appointments use Procedurelog.CompareProcedures().
*/
public class ProcedureComparer  extends IComparer 
{
    /**
    * This sorts procedures based on priority, then tooth number.  It does not care about dates or status.  Currently used in TP module and Chart module sorting.
    */
    int iComparer___Compare(Object objx, Object objy) throws Exception {
        Procedure x = (Procedure)objx;
        Procedure y = (Procedure)objy;
        //first, by priority
        if (x.Priority != y.Priority)
        {
            //if priorities are different
            if (x.Priority == 0)
            {
                return 1;
            }
             
            //x is greater than y. Priorities always come first.
            if (y.Priority == 0)
            {
                return -1;
            }
             
            return DefC.getOrder(DefCat.TxPriorities,x.Priority).CompareTo(DefC.getOrder(DefCat.TxPriorities,y.Priority));
        }
         
        //x is less than y. Priorities always come first.
        //priorities are the same, so sort by toothrange
        if (!StringSupport.equals(x.ToothRange, y.ToothRange))
        {
            return x.ToothRange.CompareTo(y.ToothRange);
        }
         
        //empty toothranges come before filled toothrange values
        //toothranges are the same (usually empty), so compare toothnumbers
        if (!StringSupport.equals(x.ToothNum, y.ToothNum))
        {
            return Tooth.toInt(x.ToothNum).CompareTo(Tooth.toInt(y.ToothNum));
        }
         
        return ProcedureCodes.getStringProcCode(x.CodeNum).CompareTo(ProcedureCodes.getStringProcCode(y.CodeNum));
    }

}


//this also puts invalid or empty toothnumbers before the others.
//priority and toothnums are the same, so sort by code.
/*string adaX=x.Code;
			if(x.ProcNumLab !=0){//if x is a Canadian lab proc
				//then use the Code of the procedure instead of the lab code
				adaX=Procedures.GetOneProc(
			}
			string adaY=y.Code;*/
//return x.Code.CompareTo(y.Code);
//return 0;//priority, tooth number, and code are all the same