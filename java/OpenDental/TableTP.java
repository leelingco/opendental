//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableTP  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableTP() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
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
        this.SuspendLayout();
        //
        // TableTP
        //
        this.Name = "TableTP";
        this.Load += new System.EventHandler(this.TableTP_Load);
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    public void instantClasses() throws Exception {
        MaxRows = 20;
        MaxCols = 10;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = Lan.g("TableTP","Treatment Plan");
        Fields[0] = Lan.g("TableTP","Priority");
        Fields[1] = Lan.g("TableTP","Tth");
        Fields[2] = Lan.g("TableTP","Surf");
        Fields[3] = Lan.g("TableTP","Code");
        Fields[4] = Lan.g("TableTP","Description");
        Fields[5] = Lan.g("TableTP","Fee");
        Fields[6] = Lan.g("TableTP","Pri Ins");
        Fields[7] = Lan.g("TableTP","Sec Ins");
        Fields[8] = Lan.g("TableTP","Pat");
        Fields[9] = Lan.g("TableTP","Pre Est");
        ColAlign[5] = HorizontalAlignment.Right;
        ColAlign[6] = HorizontalAlignment.Right;
        ColAlign[7] = HorizontalAlignment.Right;
        ColAlign[8] = HorizontalAlignment.Right;
        ColAlign[9] = HorizontalAlignment.Right;
        ColWidth[0] = 55;
        ColWidth[1] = 40;
        ColWidth[2] = 40;
        ColWidth[3] = 50;
        ColWidth[4] = 250;
        ColWidth[5] = 45;
        ColWidth[6] = 45;
        ColWidth[7] = 45;
        ColWidth[8] = 45;
        ColWidth[9] = 45;
        DefaultGridColor = Color.LightGray;
        layoutTables();
    }

    private void tableTP_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


