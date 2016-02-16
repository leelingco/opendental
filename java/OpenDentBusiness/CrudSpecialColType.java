//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;


public enum CrudSpecialColType
{
    /**
    * There are also some patterns we follow that do not require special types.  For enums, crud automatically generates tinyint.  For itemorders, we manually change mysql type to smallint?.
    */
    None,
    /**
    * User not allowed to change.  Insert uses NOW(), Update exludes this column, Select treats this like a date.
    */
    DateEntry,
    /**
    * Insert uses NOW(), Update and Select treat this like a Date.
    */
    DateEntryEditable,
    /**
    * Is set and updated by MySQL.  Leave these columns completely out of Insert and Update statements.  The name of the column must be exactly: DateTStamp for the crud schema logic to work.
    */
    TimeStamp,
    /**
    * Same C# type as Date, but the MySQL database uses datetime instead of date.
    */
    DateT,
    /**
    * User not allowed to change.  Insert uses NOW(), Update exludes this column, Select treats this like a DateT.
    */
    DateTEntry,
    /**
    * Insert uses NOW(), Update and Select treat this like a DateT.
    */
    DateTEntryEditable,
    /**
    * Database type is tinyint signed.  C# type is int.  Range -128 to 127.  The validation does not check to make sure the db is signed.  The programmer must do that.  So far, only used for percent fields because -1 is required to be accepted.  For most other fields, such as enums and itemorders, the solution is to change the field in C# to a byte, indicating a range of 0-255.  It usually doesn't matter whether the database accepts values to 255 or only to 127.  The validation does not check that.
    */
    TinyIntSigned,
    /**
    * We do not want this column updated except as part of a separate routine.  Warning! The logic fails if this is used on the last column in a table.
    */
    ExcludeFromUpdate,
    /**
    * Instead of storing this enum as an int in the db, it is stored as a string.  Very rarely used.
    */
    EnumAsString,
    /**
    * For most C# TimeSpans, the default db type is TimeOfDay.  But for the few that need to use negative values or values greater than 24 hours, they get marked as this special type.  Handled differently in MySQL vs Oracle.
    */
    TimeSpanNeg,
    /**
    * Many C# strings are varchar(255).  Most longer ones are mysql text or oracle varchar2.  But if they might go over 4000 char, then in oracle, they must be clob.  Clobs are handled significantly differently in oracle, so we are tracking those.  There is also a consideration for columns in mysql that might go over 65,000 char, but we do not need to track those in C#.
    */
    TextIsClob
}

