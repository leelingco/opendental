//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableApptProcs  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableApptProcs() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 6;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = "Procedures";
        Fields[0] = Lan.g("TableApptProcs","Stat");
        Fields[1] = Lan.g("TableApptProcs","Priority");
        Fields[2] = Lan.g("TableApptProcs","Tth");
        Fields[3] = Lan.g("TableApptProcs","Surf");
        Fields[4] = Lan.g("TableApptProcs","Description");
        Fields[5] = Lan.g("TableApptProcs","Fee");
        ColAlign[5] = HorizontalAlignment.Right;
        ColWidth[0] = 40;
        ColWidth[1] = 55;
        ColWidth[2] = 30;
        ColWidth[3] = 40;
        ColWidth[4] = 175;
        ColWidth[5] = 60;
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
        // TableApptProcs
        //
        this.Name = "TableApptProcs";
        this.Load += new System.EventHandler(this.TableApptProcs_Load);
        this.ResumeLayout(false);
    }

    private void tableApptProcs_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


