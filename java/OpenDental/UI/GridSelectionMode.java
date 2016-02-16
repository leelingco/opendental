//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum GridSelectionMode
{
    /**
    * Specifies the selection behavior of an ODGrid.
    */
    //[ComVisible(true)]
    /**
    * 0-No items can be selected.
    */
    None,
    /**
    * 1-Only one row can be selected.
    */
    One,
    /**
    * 2-Only one cell can be selected.
    */
    OneCell,
    /**
    * 3-Multiple items can be selected, and the user can use the SHIFT, CTRL, and arrow keys to make selections
    */
    MultiExtended
}

/*This is a template of typical grid code which can be quickly pasted into any form.
 
		using OpenDental.UI;
		FillGrid(){
			gridMain.BeginUpdate();
			gridMain.Columns.Clear();
			ODGridColumn col=new ODGridColumn(Lan.g("Table",""),);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("Table",""),);
			gridMain.Columns.Add(col);
			 
			gridMain.Rows.Clear();
			ODGridRow row;
			for(int i=0;i<List.Length;i++){
				row=new ODGridRow();
				row.Cells.Add("");
				row.Cells.Add("");
			  
				gridMain.Rows.Add(row);
			}
			gridMain.EndUpdate();
		}
*/