//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.TableBase;

/**
* Allows customization of which fields display in various lists and grids.  For now, the only grid is ProgressNotes.  Will also eventually let users set column widths and translate titles.  For now, the selections are the same for all computers.
*/
public class DisplayField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DisplayFieldNum = new long();
    /**
    * This is the internal name that OD uses to identify the field within this category.  This will be the default description if the user doesn't specify an alternate.
    */
    public String InternalName = new String();
    /**
    * Order to display in the grid or list. Every entry must have a unique itemorder.
    */
    public int ItemOrder = new int();
    /**
    * Optional alternate description to display for field.  Can be in another language.  For the ortho category, this is the 'key', since InternalName is blank.
    */
    public String Description = new String();
    /**
    * For grid columns, this lets user override the column width.  Especially useful for foreign languages.
    */
    public int ColumnWidth = new int();
    /**
    * Enum:DisplayFieldCategory If category is 0, then this is attached to a ChartView.
    */
    public DisplayFieldCategory Category = DisplayFieldCategory.None;
    /**
    * FK to chartview.ChartViewNum. 0 if attached to a category.
    */
    public long ChartViewNum = new long();
    public DisplayField() throws Exception {
    }

    public DisplayField(String internalName, int columnWidth, DisplayFieldCategory category) throws Exception {
        this.InternalName = internalName;
        //this.Description=description;
        this.ColumnWidth = columnWidth;
        this.Description = "";
        this.Category = category;
    }

    /**
    * Returns a copy.
    */
    public DisplayField copy() throws Exception {
        return (DisplayField)this.MemberwiseClone();
    }

    public String toString() {
        try
        {
            return this.InternalName;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


