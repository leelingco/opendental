//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableAutoItem  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableAutoItem() throws Exception {
        initializeComponent();
        MaxRows = 20;
        MaxCols = 3;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TableAutoItem","Code");
        Fields[1] = Lan.g("TableAutoItem","Description");
        Fields[2] = Lan.g("TableAutoItem","Conditions");
        ColWidth[0] = 100;
        ColWidth[1] = 200;
        ColWidth[2] = 400;
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
        // TableAutoItem
        //
        this.Name = "TableAutoItem";
        this.Load += new System.EventHandler(this.TableAutoItem_Load);
    }

    private void tableAutoItem_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


