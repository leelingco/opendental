//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.Forms;

import OpenDental.Lan;

/**
* 
*/
public class TableCarriers  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableCarriers() throws Exception {
        initializeComponent();
        MaxRows = 50;
        MaxCols = 8;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,49,14);
        Fields[0] = Lan.g("TableCarriers","Carrier Name");
        Fields[1] = Lan.g("TableCarriers","Phone");
        Fields[2] = Lan.g("TableCarriers","Address");
        Fields[3] = Lan.g("TableCarriers","Address2");
        Fields[4] = Lan.g("TableCarriers","City");
        Fields[5] = Lan.g("TableCarriers","ST");
        Fields[6] = Lan.g("TableCarriers","Zip");
        Fields[7] = Lan.g("TableCarriers","ElectID");
        ColWidth[0] = 160;
        ColWidth[1] = 90;
        ColWidth[2] = 130;
        ColWidth[3] = 120;
        ColWidth[4] = 110;
        ColWidth[5] = 60;
        ColWidth[6] = 90;
        ColWidth[7] = 60;
        //ColAlign[1]=HorizontalAlignment.Right;
        //ColAlign[2]=HorizontalAlignment.Right;
        //ColAlign[3]=HorizontalAlignment.Right;
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
        // TableCarriers
        //
        this.Name = "TableCarriers";
        this.Load += new System.EventHandler(this.TableCarriers_Load);
    }

    private void tableCarriers_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


