//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;


public enum FieldValueType
{
    //MessageBox.Show(outputValue);
    /**
    * Specifies the type of value that the field will accept.  Used in ParameterDef.ValueType and ReportObject.ValueType properties.  Also used in the ContrMultInput control to determine what kind of input to display.Field takes a date value.
    */
    Date,
    /**
    * Field takes a string value.
    */
    String,
    /**
    * Field takes a boolean value.  For a Parameter, if false, then the snippet will not even be included. Because of the way this is implemented, the snippet can specify a true or false value, and the user can select whether to include the snippet.  So the parameter can specify whether to include a false value among many other possibilities.  There should not be a ? in a boolean snippet.
    */
    Boolean,
    /**
    * Field takes an integer value.
    */
    Integer,
    /**
    * Field takes a number(double) value which can include a decimal.
    */
    Number,
    /**
    * Field takes an enumeration value(s), usually in int form from a dropdown list.
    */
    Enum,
    /**
    * Field takes definition.DefNum value from a def category. Presented to user as a dropdown list for that category.
    */
    Def,
    /**
    * Only used in ReportObject. When a table comes back from the database, if the expected value is an age, then this column type should be used.  Just retreive the birthdate and the program will convert it to an age.
    */
    Age,
    /**
    * 
    */
    ForeignKey,
    /**
    * Only used in FormQuestionnaire.
    */
    YesNoUnknown
}

