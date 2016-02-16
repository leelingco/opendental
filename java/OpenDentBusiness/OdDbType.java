//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;


public enum OdDbType
{
    /**
    * C#:bool, MySql:tinyint(3)or(1), Oracle:number(3),
    */
    Bool,
    /**
    * C#:byte, MySql:tinyint unsigned, Oracle:number(3), Range:0-255.
    */
    Byte,
    /**
    * C#:double, MySql:double, Oracle:number(38,8), Need to change C# type to Decimal.  Need to change MySQL type.
    */
    Currency,
    /**
    * C#:DateTime, MySql:date, Oracle:date, 0000-00-00 not allowed in Oracle and causes problems in MySql
    */
    Date,
    /**
    * C#:DateTime, MySql:datetime, Oracle:date,
    */
    DateTime,
    /**
    * C#:DateTime, MySql:timestamp, Oracle:date + trigger,
    */
    DateTimeStamp,
    /**
    * C#:enum, MySql:tinyint, Oracle:number(3),
    */
    Enum,
    /**
    * C#:float, MySql:float, Oracle:number(38,8),
    */
    Float,
    /**
    * C#:int32, MySql:int,smallint(if careful), Oracle:number(11), Range:-2,147,483,647-2,147,483,647.  Also used for colors
    */
    Int,
    /**
    * C#:long, MySql:bigint, Oracle:number(20), Range:–9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
    */
    Long,
    /**
    * C#:string, MySql:text,mediumtext, Oracle:varchar2,clob, Range:256+. MaxSizes: MySql.text=65k, Oracle.varchar2=4k.
    */
    Text,
    /**
    * C#:TimeSpan, MySql:time, Oracle:date, Range:Valid time of day.
    */
    TimeOfDay,
    /**
    * C#:TimeSpan, MySql:time, Oracle:varchar2, Range:Pos or neg spans of many days.  Oracle has no such type.
    */
    TimeSpan,
    /**
    * C#:string, MySql:varchar(255), Oracle:varchar2(255), MaxSize:255
    */
    VarChar255
}

