//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;


/**
* Summary description for ContrAnesthesia.
*/
public class ContrAnesthesiaData  extends System.Windows.Forms.Control 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * The index in Anesthesics.List of the currently selected anesthetic.
    */
    private int selectedAnestheticData = new int();
    public AnestheticData AnestheticDataCur = new AnestheticData();
    /**
    * The index in Anesthetics.List of the currently selected anesthetic.
    */
    public int getSelectedAnestheticData() throws Exception {
        return selectedAnestheticData;
    }

    public void setSelectedAnestheticData(int value) throws Exception {
        selectedAnestheticData = value;
    }

    /**
    * Used in LoadData.
    */
    public void saveCurAnestheticData(int anestheticDataNum) throws Exception {
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


