//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;


/**
* 
*/
public class TableLan  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableLan() throws Exception {
        initializeComponent();
        MaxRows = 50;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,49,14);
        Fields[0] = "English";
        Fields[1] = "Eng Comments";
        Fields[2] = "Language";
        Fields[3] = "Language Comments";
        //Fields[4]="Culture";
        ColWidth[0] = 210;
        ColWidth[1] = 210;
        ColWidth[2] = 210;
        ColWidth[3] = 210;
        //ColWidth[4]=200;
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
        // TableLan
        //
        this.Name = "TableLan";
        this.Load += new System.EventHandler(this.TableLan_Load);
    }

    private void tableLan_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


