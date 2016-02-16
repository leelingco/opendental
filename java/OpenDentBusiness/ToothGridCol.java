//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToothGridCellType;
import OpenDentBusiness.ToothGridCol;

/**
* Defines the columns present in a single completed tooth grid, which is a special kind of sheet field that shows a grid with 32 rows and configurable columns.  The entire grid is a single large sheet field.  This table defines how the grid is layed out on an actual sheet, pulled initially from a ToothGridDef.  The data itself is recorded in ToothGridCell.
*/
public class ToothGridCol  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ToothGridColNum = new long();
    /**
    * FK to sheet.SheetFieldNum.  Required.
    */
    public long SheetFieldNum = new long();
    /**
    * Pulled from the ToothGridDef.  This can be a NameInternal , or it can be a NameShowing if it's a user-defined column.
    */
    public String NameItem = new String();
    /**
    * Enum:ToothGridCellType  0=HardCoded, 1=Tooth, 2=Surface, 3=FreeText.
    */
    public ToothGridCellType CellType = ToothGridCellType.HardCoded;
    /**
    * Order of the column to display.  Every entry must have a unique itemorder.
    */
    public int ItemOrder = new int();
    /**
    * .
    */
    public int ColumnWidth = new int();
    /**
    * FK to procedurecode.CodeNum.  This allows data entered to flow into main program as actual completed or tp procedures.
    */
    public long CodeNum = new long();
    /**
    * Enum:ProcStat  If these flow into main program, then this is the status that the new procs will have.
    */
    public OpenDentBusiness.ProcStat ProcStatus = OpenDentBusiness.ProcStat.TP;
    public ToothGridCol copy() throws Exception {
        return (ToothGridCol)this.MemberwiseClone();
    }

}


