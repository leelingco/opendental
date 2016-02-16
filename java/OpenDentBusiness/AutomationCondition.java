//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCondComparison;
import OpenDentBusiness.AutoCondField;
import OpenDentBusiness.AutomationCondition;
import OpenDentBusiness.TableBase;

/**
* Each condition evaluates to true or false.  A series of conditions for a single automation is ANDed together.
*/
public class AutomationCondition  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AutomationConditionNum = new long();
    /**
    * FK to automation.AutomationNum.
    */
    public long AutomationNum = new long();
    /**
    * Enum:AutoCondField
    */
    public AutoCondField CompareField = AutoCondField.SheetNotCompletedTodayWithName;
    /**
    * Enum:AutoCondComparison Not all comparisons are allowed with all data types.
    */
    public AutoCondComparison Comparison = AutoCondComparison.Equals;
    /**
    * .
    */
    public String CompareString = new String();
    /**
    * 
    */
    public AutomationCondition clone() {
        try
        {
            return (AutomationCondition)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


