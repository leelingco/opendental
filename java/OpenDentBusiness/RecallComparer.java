//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PIn;
import OpenDentBusiness.RecallListSort;

/**
* The supplied DataRows must include the following columns: Guarantor, PatNum, guarLName, guarFName, LName, FName, DateDue, maxDateDue, billingType.  maxDateDue is the most recent DateDue for all family members in the list and needs to be the same for all family members.  This date will be used for better grouping.
*/
public class RecallComparer  extends IComparer<DataRow> 
{
    public boolean GroupByFamilies = new boolean();
    /**
    * rather than by the ordinary DueDate.
    */
    public RecallListSort SortBy = RecallListSort.DueDate;
    /**
    * 
    */
    public int compare(DataRow x, DataRow y) throws Exception {
        //NOTE: Even if grouping by families, each family is not necessarily going to have a guarantor.
        if (GroupByFamilies)
        {
            if (SortBy == RecallListSort.Alphabetical)
            {
                //if guarantors are different, sort by guarantor name
                if (x["Guarantor"].ToString() != y["Guarantor"].ToString())
                {
                    if (x["guarLName"].ToString() != y["guarLName"].ToString())
                    {
                        return x["guarLName"].ToString().CompareTo(y["guarLName"].ToString());
                    }
                     
                    return x["guarFName"].ToString().CompareTo(y["guarFName"].ToString());
                }
                 
                return 0;
            }
            else //order within family does not matter
            if (SortBy == RecallListSort.DueDate)
            {
                DateTime xD = PIn.Date(x["maxDateDue"].ToString());
                DateTime yD = PIn.Date(y["maxDateDue"].ToString());
                if (xD != yD)
                {
                    return (xD.CompareTo(yD));
                }
                 
                //if dates are same, sort/group by guarantor
                if (x["Guarantor"].ToString() != y["Guarantor"].ToString())
                {
                    return (x["Guarantor"].ToString().CompareTo(y["Guarantor"].ToString()));
                }
                 
                //within the same family, sort by actual DueDate
                xD = PIn.Date(x["DateDue"].ToString());
                yD = PIn.Date(y["DateDue"].ToString());
                return (xD.CompareTo(yD));
            }
            else //return 0;
            if (SortBy == RecallListSort.BillingType)
            {
                if (x["billingType"].ToString() != y["billingType"].ToString())
                {
                    return x["billingType"].ToString().CompareTo(y["billingType"].ToString());
                }
                 
                //if billing types are the same, sort by dueDate
                DateTime xD = PIn.Date(x["maxDateDue"].ToString());
                DateTime yD = PIn.Date(y["maxDateDue"].ToString());
                if (xD != yD)
                {
                    return (xD.CompareTo(yD));
                }
                 
                //if dates are same, sort/group by guarantor
                if (x["Guarantor"].ToString() != y["Guarantor"].ToString())
                {
                    return (x["Guarantor"].ToString().CompareTo(y["Guarantor"].ToString()));
                }
                 
            }
               
        }
        else
        {
            //individual patients
            if (SortBy == RecallListSort.Alphabetical)
            {
                if (x["LName"].ToString() != y["LName"].ToString())
                {
                    return x["LName"].ToString().CompareTo(y["LName"].ToString());
                }
                 
                return x["FName"].ToString().CompareTo(y["FName"].ToString());
            }
            else if (SortBy == RecallListSort.DueDate)
            {
                if ((DateTime)x["DateDue"] != (DateTime)y["DateDue"])
                {
                    return ((DateTime)x["DateDue"]).CompareTo(((DateTime)y["DateDue"]));
                }
                 
                return x["LName"].ToString().CompareTo(y["LName"].ToString());
            }
            else //if duedates are the same, sort by LName
            if (SortBy == RecallListSort.BillingType)
            {
                if (x["billingType"].ToString() != y["billingType"].ToString())
                {
                    return x["billingType"].ToString().CompareTo(y["billingType"].ToString());
                }
                 
                //if billing types are the same, sort by dueDate
                if ((DateTime)x["DateDue"] != (DateTime)y["DateDue"])
                {
                    return ((DateTime)x["DateDue"]).CompareTo(((DateTime)y["DateDue"]));
                }
                 
                return x["LName"].ToString().CompareTo(y["LName"].ToString());
            }
               
        } 
        return 0;
    }

}


//if duedates are the same, sort by LName