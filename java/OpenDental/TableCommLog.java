//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableCommLog  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableCommLog() throws Exception {
        initializeComponent();
        MaxRows = 50;
        MaxCols = 2;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        Heading = Lan.g("TableCommLog","Communications Log - Appointment Scheduling");
        instantClassesPar();
        setRowHeight(0,49,14);
        Fields[0] = Lan.g("TableCommLog","Date");
        Fields[1] = Lan.g("TableCommLog","Note");
        //Fields[2]=Lan.g("TableCommLog","-Insurance Est");
        //Fields[3]=Lan.g("TableCommLog","=Amount Due");
        ColWidth[0] = 70;
        ColWidth[1] = 530;
        //ColWidth[2]=100;
        //ColWidth[3]=100;
        //ColAlign[1]=HorizontalAlignment.Right;
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
        // TableAutoBilling
        //
        this.Name = "TableAutoBilling";
        this.Load += new System.EventHandler(this.TableAutoBilling_Load);
    }

    private void tableAutoBilling_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


