//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Describes the templates for letter merges to Word.
*/
public class LetterMerge  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LetterMergeNum = new long();
    /**
    * Description of this letter.
    */
    public String Description = new String();
    /**
    * The filename of the Word template. eg MyTemplate.doc.
    */
    public String TemplateName = new String();
    /**
    * The name of the data file. eg MyTemplate.txt.
    */
    public String DataFileName = new String();
    /**
    * FK to definition.DefNum.
    */
    public long Category = new long();
    /**
    * Not a database column.  Filled using fk from the lettermergefields table.  A collection of strings representing field names.
    */
    public List<String> Fields = new List<String>();
}


