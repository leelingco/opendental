//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableContacts  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableContacts() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 5;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TableContacts","Last Name");
        Fields[1] = Lan.g("TableContacts","First Name");
        Fields[2] = Lan.g("TableContacts","Wk Phone");
        Fields[3] = Lan.g("TableContacts","Fax");
        Fields[4] = Lan.g("TableContacts","Notes");
        ColWidth[0] = 120;
        ColWidth[1] = 100;
        ColWidth[2] = 90;
        ColWidth[3] = 90;
        ColWidth[4] = 250;
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
        // TableContacts
        //
        this.Name = "TableContacts";
        this.Load += new System.EventHandler(this.TableContacts_Load);
    }

    private void tableContacts_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


