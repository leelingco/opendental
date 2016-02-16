//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.Undo;
import fyiReporting.RdlDesign.UndoItem;

/**
     * Groups several undo events into one transaction.  Needed when one
	 * user action corresponds to multiple dom events
     * */
public class UndoGroup   implements UndoItem
{
    Undo _undo;
    String description = new String();
    List<UndoItem> undoItems = new List<UndoItem>();
    public UndoGroup(Undo undo, String description) throws Exception {
        _undo = undo;
        this.description = description;
    }

    public void addUndoItem(UndoItem ui) throws Exception {
        undoItems.Add(ui);
    }

    public int getCount() throws Exception {
        return undoItems.Count;
    }

    public void undo() throws Exception {
        for (int i = undoItems.Count - 1;i >= 0;i--)
        {
            // loop thru group items backwards
            UndoItem ui = undoItems[i] instanceof UndoItem ? (UndoItem)undoItems[i] : (UndoItem)null;
            _undo.setUndoing(true);
            ui.undo();
        }
    }

    public String getDescription() throws Exception {
        return description;
    }

}


