//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TablePermissions  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TablePermissions() throws Exception {
        initializeComponent();
        MaxRows = 20;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TablePermissions","Name");
        Fields[1] = Lan.g("TablePermissions","Require Password");
        Fields[2] = Lan.g("TablePermissions","Before Date");
        Fields[3] = Lan.g("TablePermissions","Before Days");
        ColWidth[0] = 200;
        ColWidth[1] = 110;
        ColWidth[2] = 100;
        ColWidth[3] = 100;
        ColAlign[1] = HorizontalAlignment.Center;
        ColAlign[2] = HorizontalAlignment.Center;
        ColAlign[3] = HorizontalAlignment.Center;
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
        // TablePermissions
        //
        this.Name = "TablePermissions";
        this.Load += new System.EventHandler(this.TablePermissions_Load);
    }

    private void tablePermissions_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


