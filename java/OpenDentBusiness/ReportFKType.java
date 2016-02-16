//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ReportFKType
{
    /**
    * Used as the enumeration of FieldValueType.ForeignKey.  Basically, this allows lists to be included in the parameter list.  The lists are those common short lists that are used so frequently.  The user can only select one from the list, and the primary key of that item will be used as the parameter.0
    */
    None,
    /**
    * The schoolclass table in the database. Used for dental schools.
    */
    SchoolClass,
    /**
    * The schoolcourse table in the database. Used for dental schools.
    */
    SchoolCourse
}

