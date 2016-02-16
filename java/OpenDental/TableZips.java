//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableZips  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableZips() throws Exception {
        initializeComponent();
        MaxRows = 50;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TableZips","ZipCode");
        Fields[1] = Lan.g("TableZips","City");
        Fields[2] = Lan.g("TableZips","State");
        Fields[3] = Lan.g("TableZips","Frequent");
        ColWidth[0] = 100;
        ColWidth[1] = 200;
        ColWidth[2] = 100;
        ColWidth[3] = 100;
        DefaultGridColor = Color.LightGray;
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
        //
        // TableZips
        //
        this.Name = "TableZips";
        this.Load += new System.EventHandler(this.TableZips_Load);
    }

    private void tableZips_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


