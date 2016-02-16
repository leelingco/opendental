//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* When doing a lettermerge, a data file is created with certain fields.  This is a list of those fields for each lettermerge.
*/
public class LetterMergeField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long FieldNum = new long();
    /**
    * FK to lettermerge.LetterMergeNum.
    */
    public long LetterMergeNum = new long();
    /**
    * One of the preset available field names.
    */
    public String FieldName = new String();
}


