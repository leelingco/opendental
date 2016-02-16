//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import OpenDental.UI.ODGridCellList;

/**
* 
*/
public class ODGridRow   
{
    private ODGridCellList cells;
    private Color colorBackG = new Color();
    private boolean bold = new boolean();
    private Color colorText = new Color();
    private Color colorLborder = new Color();
    private Object tag = new Object();
    private String note = new String();
    //private int height;
    /**
    * Creates a new ODGridRow.
    */
    public ODGridRow() throws Exception {
        cells = new ODGridCellList();
        colorBackG = Color.White;
        bold = false;
        colorText = Color.Black;
        colorLborder = Color.Empty;
        tag = null;
        note = "";
    }

    //height=0;
    /**
    * 
    */
    public ODGridCellList getCells() throws Exception {
        return cells;
    }

    /**
    * Background color.
    */
    public Color getColorBackG() throws Exception {
        return colorBackG;
    }

    public void setColorBackG(Color value) throws Exception {
        colorBackG = value;
    }

    /**
    * 
    */
    public boolean getBold() throws Exception {
        return bold;
    }

    public void setBold(boolean value) throws Exception {
        bold = value;
    }

    /**
    * This sets the text color for the whole row.  Each gridCell also has a colorText property that will override this if set.
    */
    public Color getColorText() throws Exception {
        return colorText;
    }

    public void setColorText(Color value) throws Exception {
        colorText = value;
    }

    /**
    * 
    */
    public Color getColorLborder() throws Exception {
        return colorLborder;
    }

    public void setColorLborder(Color value) throws Exception {
        colorLborder = value;
    }

    /**
    * Used to store any kind of object that is associated with the row.
    */
    public Object getTag() throws Exception {
        return tag;
    }

    public void setTag(Object value) throws Exception {
        tag = value;
    }

    /**
    * This is a very special field.  Since most of the tables in OD require the ability to attach long notes to each row, this field makes it possible.  Any note set here will be drawn as a sort of subrow.  The note can span multiple columns, as defined in grid.NoteSpanStart and grid.NoteSpanStop.
    */
    public String getNote() throws Exception {
        return note;
    }

    public void setNote(String value) throws Exception {
        note = value;
    }

}


/*
		///<Summary>If not set (0), then the row height is computed automatically.</Summary>
		public int Height {
			get {
				return height;
			}
			set {
				height=value;
			}
		}*/