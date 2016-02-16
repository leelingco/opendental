//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AppointmentRule;
import OpenDentBusiness.TableBase;

/**
* For now, the rule is simple. It simply blocks all double booking of the specified code range.  The double booking would have to be for the same provider.  This can later be extended to provide more complex rules, such as partial double booking, time limitations, etc.
*/
public class AppointmentRule  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AppointmentRuleNum = new long();
    /**
    * The description of the rule which will be displayed to the user.
    */
    public String RuleDesc = new String();
    /**
    * The procedure code of the start of the range.
    */
    public String CodeStart = new String();
    /**
    * The procedure code of the end of the range.
    */
    public String CodeEnd = new String();
    /**
    * Usually true.  But this does allow you to turn off a rule temporarily without losing the settings.
    */
    public boolean IsEnabled = new boolean();
    /**
    * Returns a copy of this AppointmentRule.
    */
    public AppointmentRule clone() {
        try
        {
            return (AppointmentRule)this.MemberwiseClone();
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


