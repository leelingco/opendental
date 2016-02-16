//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Tooth;

public class ProcedureLogic   
{
    /**
    * The supplied DataRows must include the following columns: ProcStatus(optional),Priority(optional),ToothRange,ToothNum,ProcCode.  This sorts procedures based on priority, then tooth number, then procCode.  It does not care about dates or status.  Currently used in Account module, appointments, and Chart module sorting.  TP uses Procedures.ProcedureComparer.
    */
    public static int compareProcedures(DataRow x, DataRow y) throws Exception {
        //first, by status
        if (x.Table.Columns.Contains("ProcStatus") && y.Table.Columns.Contains("ProcStatus"))
        {
            if (x["ProcStatus"].ToString() != y["ProcStatus"].ToString())
            {
                //Cn,TP,R,EO,EC,C,D
                int xIdx = 0;
                DataRow.INDEXER.APPLY __dummyScrutVar0 = x["ProcStatus"].ToString();
                if (__dummyScrutVar0.equals("7"))
                {
                    //Cn
                    xIdx = 0;
                }
                else if (__dummyScrutVar0.equals("1"))
                {
                    //TP
                    xIdx = 1;
                }
                else if (__dummyScrutVar0.equals("5"))
                {
                    //R
                    xIdx = 2;
                }
                else if (__dummyScrutVar0.equals("4"))
                {
                    //EO
                    xIdx = 3;
                }
                else if (__dummyScrutVar0.equals("3"))
                {
                    //EC
                    xIdx = 4;
                }
                else if (__dummyScrutVar0.equals("2"))
                {
                    //C
                    xIdx = 5;
                }
                else if (__dummyScrutVar0.equals("6"))
                {
                    //D
                    xIdx = 6;
                }
                       
                int yIdx = 0;
                DataRow.INDEXER.APPLY __dummyScrutVar1 = y["ProcStatus"].ToString();
                if (__dummyScrutVar1.equals("7"))
                {
                    //Cn
                    yIdx = 0;
                }
                else if (__dummyScrutVar1.equals("1"))
                {
                    //TP
                    yIdx = 1;
                }
                else if (__dummyScrutVar1.equals("5"))
                {
                    //R
                    yIdx = 2;
                }
                else if (__dummyScrutVar1.equals("4"))
                {
                    //EO
                    yIdx = 3;
                }
                else if (__dummyScrutVar1.equals("3"))
                {
                    //EC
                    yIdx = 4;
                }
                else if (__dummyScrutVar1.equals("2"))
                {
                    //C
                    yIdx = 5;
                }
                else if (__dummyScrutVar1.equals("6"))
                {
                    //D
                    yIdx = 6;
                }
                       
                return xIdx.CompareTo(yIdx);
            }
             
        }
         
        //by priority
        if (x.Table.Columns.Contains("Priority") && y.Table.Columns.Contains("Priority"))
        {
            if (x["Priority"].ToString() != y["Priority"].ToString())
            {
                //if priorities are different
                if (StringSupport.equals(x["Priority"].ToString(), "0"))
                {
                    return 1;
                }
                 
                //x is greater than y. Priorities always come first.
                if (StringSupport.equals(y["Priority"].ToString(), "0"))
                {
                    return -1;
                }
                 
                return DefC.GetOrder(DefCat.TxPriorities, PIn.Long(x["Priority"].ToString())).CompareTo(DefC.GetOrder(DefCat.TxPriorities, PIn.Long(y["Priority"].ToString())));
            }
             
        }
         
        //x is less than y. Priorities always come first.
        //priorities are the same, so sort by toothrange
        if (x["ToothRange"].ToString() != y["ToothRange"].ToString())
        {
            return x["ToothRange"].ToString().CompareTo(y["ToothRange"].ToString());
        }
         
        //empty toothranges come before filled toothrange values
        //toothranges are the same (usually empty), so compare toothnumbers
        if (x["ToothNum"].ToString() != y["ToothNum"].ToString())
        {
            return Tooth.ToInt(x["ToothNum"].ToString()).CompareTo(Tooth.ToInt(y["ToothNum"].ToString()));
        }
         
        return x["ProcCode"].ToString().CompareTo(y["ProcCode"].ToString());
    }

}


//this also puts invalid or empty toothnumbers before the others.
//priority and toothnums are the same, so sort by proccode.
//return 0;//priority, tooth number, and proccode are all the same