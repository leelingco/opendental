//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToothGridCell;

/**
* Holds one recorded cell value for a tooth grid, which is a special kind of sheet field type that shows a grid with 32 rows and configurable columns.  The entire grid is a single large sheet field.
*/
public class ToothGridCell  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ToothGridCellNum = new long();
    /**
    * FK to sheet.SheetFieldNum.  Required.
    */
    public long SheetFieldNum = new long();
    /**
    * FK to toothgridcol.ToothGridColNum.  This tells which column it belongs in.  Can't use the column name here because multiple columns could have the same name.
    */
    public long ToothGridColNum = new long();
    /**
    * Cannot be empty.  For a tooth-level cell, the only allowed value is X.  If the cell is unchecked, then it won't even have a row in this table.  For a surface level column, only valid surfaces can be entered:MOIDBFLV  Enforced.  FreeText columns can have any text up to 255 char.
    */
    public String ValueEntered = new String();
    /**
    * Corresponds exactly to procedurelog.ToothNum.  May be blank, otherwise 1-32, 51-82, A-T, or AS-TS, 1 or 2 char.  Gets internationalized as being displayed.
    */
    public String ToothNum = new String();
    public ToothGridCell copy() throws Exception {
        return (ToothGridCell)this.MemberwiseClone();
    }

}


