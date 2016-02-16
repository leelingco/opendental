//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableClearinghouses  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableClearinghouses() throws Exception {
        // This call is required by the Windows Form Designer.
        initializeComponent();
        MaxRows = 10;
        MaxCols = 5;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,9,14);
        Heading = Lan.g("TableClearinghouses","Clearinghouses");
        Fields[0] = Lan.g("TableClearinghouses","Description");
        Fields[1] = Lan.g("TableClearinghouses","Export Path");
        Fields[2] = Lan.g("TableClearinghouses","Format");
        Fields[3] = Lan.g("TableClearinghouses","Is Default");
        Fields[4] = Lan.g("TableClearinghouses","Payors");
        ColWidth[0] = 150;
        ColWidth[1] = 230;
        ColWidth[2] = 110;
        ColWidth[3] = 60;
        ColWidth[4] = 310;
        layoutTables();
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
         
        super.dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        //
        // TableClearinghouses
        //
        this.Name = "TableClearinghouses";
        this.Load += new System.EventHandler(this.TableClearinghouses_Load);
    }

    private void tableClearinghouses_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


