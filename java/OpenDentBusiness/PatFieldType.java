//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum PatFieldType
{
    /**
    * 0
    */
    Text,
    /**
    * 1
    */
    PickList,
    /**
    * 2-Stored in db as entered, already localized.  For example, it could be 2/04/11, 2/4/11, 2/4/2011, or any other variant.  This makes it harder to create queries that filter by date, but easier to display dates as part of results.
    */
    Date,
    /**
    * 3-If checked, value stored as "1".  If unchecked, row deleted.
    */
    Checkbox,
    /**
    * 4-This seems to have been added without implementing.  Not sure what will happen if someone tries to use it.
    */
    Currency
}

//public enum PatFieldMapping{
//<summary>0</summary>
//None,
//<summary>1</summary>
//IncomeForPoverty
//}