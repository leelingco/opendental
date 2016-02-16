//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;


public enum ParamValueType
{
    //MessageBox.Show(outputValue);
    /**
    * Specifies the type of value that the parameter will accept.  Also used in the ContrMultInput control to determine what kind of input to display.Parameter takes a date/time value.
    */
    Date,
    /**
    * Parameter takes a string value.
    */
    String,
    /**
    * Parameter takes a boolean value.  If false, then the snippet will not even be included. Because of the way this is implemented, the snippet can specify a true or false value, and the user can select whether to include the snippet.  So the parameter can specify whether to include a false value among many other possibilities.  There should not be a ? in a boolean snippet.
    */
    Boolean,
    /**
    * Parameter takes an integer value.
    */
    Integer,
    /**
    * Parameter takes a number(double) value which can include a decimal.
    */
    Number,
    /**
    * Parameter takes an enumeration value(s).  User must select from a list.
    */
    Enum,
    /**
    * A list will be presented to the user based on the results of this query.  Column one of the query results should contain the values, and column two should contain the display text.  One typical use is when choosing providers: "SELECT ProvNum,Abbr FROM provider WHERE IsHidden=0 ORDER BY ItemOrder"
    */
    QueryData
}

