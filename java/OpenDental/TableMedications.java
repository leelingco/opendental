//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableMedications  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableMedications() throws Exception {
        initializeComponent();
        MaxRows = 50;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,49,14);
        Fields[0] = Lan.g("TableMedications","Drug Name");
        Fields[1] = Lan.g("TableMedications","Generic Name");
        Fields[2] = Lan.g("TableMedications","Notes");
        Fields[3] = Lan.g("TableMedications","Notes for Patient");
        ColWidth[0] = 100;
        ColWidth[1] = 100;
        ColWidth[2] = 370;
        ColWidth[3] = 370;
        //ColAlign[1]=HorizontalAlignment.Right;
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
        // TableAutoBilling
        //
        this.Name = "TableAutoBilling";
        this.Load += new System.EventHandler(this.TableAutoBilling_Load);
    }

    private void tableAutoBilling_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


