//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PatField;
import OpenDentBusiness.TableBase;

/**
* These are custom fields added and managed by the user.
*/
public class PatField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PatFieldNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to patfielddef.FieldName.  The full name is shown here for ease of use when running queries.  But the user is only allowed to change fieldNames in the patFieldDef setup window.
    */
    public String FieldName = new String();
    /**
    * Any text that the user types in.  For picklists, this will contain the picked text.  For dates, this is stored as the user typed it, after validating that it could be parsed.  So queries that involve dates won't work very well.  If we want better handling of date fields, we should add a column to this table.  Checkbox will either have a value of 1, or else the row will be deleted from the db.  Currency is handled in a culture neutral way, just like other currency in the db.
    */
    public String FieldValue = new String();
    //<summary>The last date that this field was updated.  Useful for certain reports.  User controls.  Loosely automated.</summary>
    //public DateTime DateLastUpdated;
    /**
    * 
    */
    public PatField copy() throws Exception {
        return (PatField)this.MemberwiseClone();
    }

}


