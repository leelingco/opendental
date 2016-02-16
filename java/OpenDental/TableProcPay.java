//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableProcPay  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableProcPay() throws Exception {
        initializeComponent();
        MaxRows = 5;
        MaxCols = 4;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,4,14);
        Heading = Lan.g("TableProcPay","Patient Payments");
        Fields[0] = Lan.g("TableProcPay","Entry Date");
        //Fields[1]=Lan.g("TableProcPay","Prov");
        Fields[1] = Lan.g("TableProcPay","Amount");
        Fields[2] = Lan.g("TableProcPay","Tot Amt");
        Fields[3] = Lan.g("TableProcPay","Note");
        ColAlign[0] = HorizontalAlignment.Center;
        ColAlign[1] = HorizontalAlignment.Right;
        ColAlign[2] = HorizontalAlignment.Right;
        ColWidth[0] = 70;
        //ColWidth[1]=55;
        ColWidth[1] = 55;
        ColWidth[2] = 55;
        ColWidth[3] = 250;
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
        // TableProcPay
        //
        this.Name = "TableProcPay";
        this.Load += new System.EventHandler(this.TableProcPay_Load);
    }

    private void tableProcPay_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


