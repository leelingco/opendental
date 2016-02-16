//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableProcSelect  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableProcSelect() throws Exception {
        initializeComponent();
        MaxRows = 5;
        MaxCols = 6;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,4,14);
        //Heading=Lan.g("TableProcSelect","Patient Payments");
        Fields[0] = Lan.g("TableProcSelect","Date");
        Fields[1] = Lan.g("TableProcSelect","Prov");
        Fields[2] = Lan.g("TableProcSelect","Code");
        Fields[3] = Lan.g("TableProcSelect","Tooth");
        Fields[4] = Lan.g("TableProcSelect","Description");
        Fields[5] = Lan.g("TableProcSelect","Fee");
        //ColAlign[0]=HorizontalAlignment.Center;
        ColAlign[5] = HorizontalAlignment.Right;
        ColWidth[0] = 70;
        ColWidth[1] = 55;
        ColWidth[2] = 55;
        ColWidth[3] = 50;
        ColWidth[4] = 250;
        ColWidth[5] = 60;
        DefaultGridColor = Color.LightGray;
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
        // TableProcSelect
        //
        this.Name = "TableProcSelect";
        this.Load += new System.EventHandler(this.TableProcSelect_Load);
    }

    private void tableProcSelect_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


