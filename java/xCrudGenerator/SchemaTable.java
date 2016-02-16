//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package xCrudGenerator;

import OpenDentBusiness.CrudSpecialColType;

public class SchemaTable   
{
    //do not inherit from TableBase because it's not a real table
    /**
    * 
    */
    public long TempCoreNum = new long();
    /**
    * 
    */
    public TimeSpan TimeOfDayTest = new TimeSpan();
    /**
    * 
    */
    public DateTime TimeStampTest = new DateTime();
    /**
    * 
    */
    public DateTime DateTest = new DateTime();
    /**
    * 
    */
    public DateTime DateTimeTest = new DateTime();
    /**
    * 
    */
    public TimeSpan TimeSpanTest = new TimeSpan();
    /**
    * 
    */
    public double CurrencyTest = new double();
    /**
    * 
    */
    public boolean BoolTest = new boolean();
    /**
    * The crud will create this as a varchar(255) and the programmer must change it manually.
    */
    public String TextSmallTest = new String();
    // >255 & <4k
    /**
    * The crud should generate mysql=text and oracle=clob
    */
    public String TextMediumTest = new String();
    // >4k & <65k
    /**
    * The crud should generate mysql=text and oracle=clob
    */
    public String TextLargeTest = new String();
    // >65k
    /**
    * 
    */
    public String VarCharTest = new String();
}


// <255