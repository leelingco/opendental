//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;


/**
* 
*/
public class TableCodeList  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public static int rowHeight = 14;
    /**
    * 
    */
    public TableCodeList() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 10;
        MaxCols = 3;
        ShowScroll = false;
        FieldsArePresent = false;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,9,14);
        ColAlign[1] = HorizontalAlignment.Right;
        ColWidth[0] = 90;
        ColWidth[1] = 50;
        ColWidth[2] = 45;
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
        this.SuspendLayout();
        //
        // TableCodeList
        //
        this.Name = "TableCodeList";
        this.Click += new System.EventHandler(this.TableCodeList_Click);
        this.Load += new System.EventHandler(this.TableCodeList_Load);
        this.ResumeLayout(false);
    }

    private void tableCodeList_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

    private void tableCodeList_Click(Object sender, System.EventArgs e) throws Exception {
    }

}


