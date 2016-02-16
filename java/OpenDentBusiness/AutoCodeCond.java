//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCondition;
import OpenDentBusiness.TableBase;

/**
* AutoCode condition.  Always attached to an AutoCodeItem, which is then, in turn, attached to an autocode.  There is usually only one or two conditions for a given AutoCodeItem.
*/
public class AutoCodeCond  extends TableBase 
{
    //
    /**
    * Primary key.
    */
    public long AutoCodeCondNum = new long();
    /**
    * FK to autocodeitem.AutoCodeItemNum.
    */
    public long AutoCodeItemNum = new long();
    /**
    * Enum:AutoCondition
    */
    public AutoCondition Cond = AutoCondition.Anterior;
}


