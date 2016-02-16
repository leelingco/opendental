//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableDefs  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableDefs() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 24;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,23,14);
        ColWidth[0] = 180;
        ColWidth[1] = 170;
        ColWidth[2] = 50;
        ColWidth[3] = 40;
        Fields[0] = Lan.g("TableDefs","Name");
        Fields[1] = Lan.g("TableDefs","Value");
        Fields[2] = Lan.g("TableDefs","Color");
        Fields[3] = Lan.g("TableDefs","Hide");
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
        // TableDefs
        //
        this.Name = "TableDefs";
        this.Load += new System.EventHandler(this.TableDefs_Load);
        this.ResumeLayout(false);
    }

    private void tableDefs_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


