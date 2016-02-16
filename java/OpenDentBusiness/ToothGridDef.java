//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToothGridCellType;
import OpenDentBusiness.ToothGridDef;

/**
* Defines the columns present in a tooth grid, which is a special kind of sheet field def that shows a grid with 32 rows and configurable columns.  Can be edited without damaging any completed sheets.
*/
public class ToothGridDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ToothGridDefNum = new long();
    /**
    * FK to sheetfielddef.SheetFieldDefNum
    */
    public long SheetFieldDefNum = new long();
    /**
    * This is the internal name that OD uses to identify the column.  Blank if this is a user-defined column.  We will keep a hard-coded list of available NameInternals in the code to pick from.
    */
    public String NameInternal = new String();
    /**
    * The user may override the internal name for display purposes.  If this is a user-defined column, this is the only name, since there is no NameInternal.
    */
    public String NameShowing = new String();
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
    public ToothGridDef copy() throws Exception {
        return (ToothGridDef)this.MemberwiseClone();
    }

}


