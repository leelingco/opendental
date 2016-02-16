//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;


/**
* Summary description for ContrAnesthesia.
*/
public class ContrAnesthesia  extends System.Windows.Forms.Control 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * The index in Anesthesics.List of the currently selected anesthetic.
    */
    private int selectedAnestheticRecord = new int();
    public AnestheticRecord AnestheticRecordCur = new AnestheticRecord();
    /**
    * The index in Anesthetics.List of the currently selected anesthetic.
    */
    public int getSelectedAnestheticRecord() throws Exception {
        return selectedAnestheticRecord;
    }

    public void setSelectedAnestheticRecord(int value) throws Exception {
        selectedAnestheticRecord = value;
    }

    /**
    * Used in LoadData.
    */
    public void saveCurAnestheticRecord(int anestheticRecordNum) throws Exception {
    }

    /**
    * 
    */
    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        Focus();
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

}


