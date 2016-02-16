//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:53 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ProcedureLogic;

/**
* The supplied DataRows must include the following columns: ProcNum,DateTime,(Priority not needed),ToothRange,ToothNum,ProcCode. This sorts all objects in Account module based on their types, dates, toothrange, toothnum, and proccode.  Times are always ignored if present.
*/
public class AccountLineComparer  extends IComparer<DataRow> 
{
    /**
    * 
    */
    public int compare(DataRow x, DataRow y) throws Exception {
        //if dates are different, then sort by date
        if (((DateTime)x["DateTime"]).Date != ((DateTime)y["DateTime"]).Date)
        {
            return (((DateTime)x["DateTime"]).Date).CompareTo(((DateTime)y["DateTime"]).Date);
        }
         
        //Sort by Type (right now just sorts procedures first...)
        if (!StringSupport.equals(x["ProcNum"].ToString(), "0") && StringSupport.equals(y["ProcNum"].ToString(), "0"))
        {
            return -1;
        }
         
        if (StringSupport.equals(x["ProcNum"].ToString(), "0") && !StringSupport.equals(y["ProcNum"].ToString(), "0"))
        {
            return 1;
        }
         
        //Sort procedures by status, priority, tooth region/num, proc code
        if (!StringSupport.equals(x["ProcNum"].ToString(), "0") && !StringSupport.equals(y["ProcNum"].ToString(), "0"))
        {
            return ProcedureLogic.compareProcedures(x,y);
        }
         
        return 0;
    }

}


//if both are procedures
/*
	///<summary>A generic comparison that sorts the rows of the payplanamort table by date and type.</summary>
	class PayPlanLineComparer : IComparer<DataRow>	{
		///<summary>A generic comparison that sorts the rows of the payplanamort table by date and type.</summary>
		public int Compare (DataRow rowA,DataRow rowB){
			//if dates are different, then sort by date
			if((DateTime)rowA["DateTime"]!=(DateTime)rowB["DateTime"]){
				return ((DateTime)rowA["DateTime"]).CompareTo((DateTime)rowB["DateTime"]);
			}
			//Charges come before paysplits, but rare to be on same date anyway.
			if(rowA["PayPlanChargeNum"].ToString()!="0" && rowB["PaySplitNum"].ToString()=="0"){
				return -1;
			}
			if(rowA["PaySplitNum"].ToString()=="0" && rowB["PayPlanChargeNum"].ToString()!="0"){
				return 1;
			}
			return 0;
		}
	}*/