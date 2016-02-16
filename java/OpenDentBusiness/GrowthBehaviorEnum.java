//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;


public enum GrowthBehaviorEnum
{
    /*StatementHeader,
    		TxPlanHeader,
    		Postcard*/
    /**
    * For sheetFieldsNot allowed to grow.  Max size would be Height and Width.
    */
    None,
    /**
    * Can grow down if needed, and will push nearby objects out of the way so that there is no overlap.
    */
    DownLocal,
    /**
    * Can grow down, and will push down all objects on the sheet that are below it.  Mostly used when drawing grids.
    */
    DownGlobal
}

