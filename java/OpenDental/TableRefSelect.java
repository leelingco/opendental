//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableRefSelect  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableRefSelect() throws Exception {
        initializeComponent();
        instantClasses();
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
        // TableRefSelect
        //
        this.Name = "TableRefSelect";
        this.Load += new System.EventHandler(this.TableRefSelect_Load);
    }

    /**
    * 
    */
    public void instantClasses() throws Exception {
        MaxRows = 20;
        MaxCols = 7;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        SetGridColor(Color.LightGray);
        SetBackGColor(Color.White);
        Heading = Lan.g("TableRefSelect","Select Referral");
        Fields[0] = Lan.g("TableRefSelect","Last Name");
        Fields[1] = Lan.g("TableRefSelect","FirstName");
        Fields[2] = Lan.g("TableRefSelect","MI");
        Fields[3] = Lan.g("TableRefSelect","Title");
        Fields[4] = Lan.g("TableRefSelect","Specialty");
        Fields[5] = Lan.g("TableRefSelect","Patient");
        Fields[6] = Lan.g("TableRefSelect","Note");
        ColWidth[0] = 140;
        ColWidth[1] = 80;
        ColWidth[2] = 25;
        ColWidth[3] = 65;
        ColWidth[4] = 75;
        ColWidth[5] = 50;
        ColWidth[6] = 375;
        layoutTables();
    }

    private void tableRefSelect_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


