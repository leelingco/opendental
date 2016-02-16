//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableProvIdent  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableProvIdent() throws Exception {
        initializeComponent();
        //This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 3;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        //Heading=Lan.g("TableQueue","Provider Identifiers");
        Fields[0] = Lan.g("TableQueue","Payor ID");
        Fields[1] = Lan.g("TableQueue","Type");
        Fields[2] = Lan.g("TableQueue","ID Number");
        ColWidth[0] = 90;
        ColWidth[1] = 110;
        ColWidth[2] = 100;
        //ColAlign[1]=HorizontalAlignment.Center;
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
        // TableProvIdent
        //
        this.Name = "TableProvIdent";
        this.Load += new System.EventHandler(this.TableProvIdent_Load);
    }

    private void tableProvIdent_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


