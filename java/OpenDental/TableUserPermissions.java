//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableUserPermissions  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableUserPermissions() throws Exception {
        initializeComponent();
        MaxRows = 20;
        MaxCols = 3;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TableUserPermissions","Name");
        Fields[1] = Lan.g("TableUserPermissions","Has Permission");
        Fields[2] = Lan.g("TableUserPermissions","IsLogged");
        ColAlign[1] = HorizontalAlignment.Center;
        ColAlign[2] = HorizontalAlignment.Center;
        ColWidth[0] = 150;
        ColWidth[1] = 100;
        ColWidth[2] = 100;
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
        // TableUserPermissions
        //
        this.Name = "TableUserPermissions";
        this.Load += new System.EventHandler(this.TableUserPermissions_Load);
    }

    private void tableUserPermissions_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


