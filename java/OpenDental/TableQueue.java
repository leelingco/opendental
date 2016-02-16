//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableQueue  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableQueue() throws Exception {
        initializeComponent();
        MaxRows = 20;
        MaxCols = 5;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,19,14);
        Fields[0] = Lan.g("TableQueue","Patient Name");
        Fields[1] = Lan.g("TableQueue","Carrier Name");
        Fields[2] = Lan.g("TableQueue","Clearinghouse");
        Fields[3] = Lan.g("TableQueue","Status");
        Fields[4] = Lan.g("TableQueue","Missing Info");
        ColWidth[0] = 130;
        ColWidth[1] = 170;
        ColWidth[2] = 120;
        ColWidth[3] = 120;
        ColWidth[4] = 300;
        //ColAlign[1]=HorizontalAlignment.Center;
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
        this.SuspendLayout();
        //
        // TableQueue
        //
        this.Name = "TableQueue";
        this.Load += new System.EventHandler(this.TableQueue_Load);
        this.ResumeLayout(false);
    }

    private void tableQueue_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


