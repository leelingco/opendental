//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;


/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrPanelTable  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public ContrPanelTable() throws Exception {
        initializeComponent();
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        //
        // ContrPanelTable
        //
        this.BackColor = System.Drawing.SystemColors.Window;
        this.Name = "ContrPanelTable";
    }

}


