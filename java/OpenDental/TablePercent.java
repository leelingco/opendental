//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TablePercent  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TablePercent() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 6;
        MaxCols = 2;
        ShowScroll = true;
        FieldsArePresent = false;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,5,14);
        Heading = Lan.g("TablePercent","Coverage Spans");
        ColWidth[0] = 120;
        ColWidth[1] = 132;
        layoutTables();
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
         
        super.dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // TablePercent
        //
        this.Name = "TablePercent";
        this.Load += new System.EventHandler(this.TablePercent_Load);
        this.ResumeLayout(false);
    }

    private void tablePercent_Load(Object sender, System.EventArgs e) throws Exception {
    }

}


