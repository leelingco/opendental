//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableClaimPay  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableClaimPay() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 5;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = Lan.g("TableClaimPay","Insurance Checks");
        Fields[0] = Lan.g("TableClaimPay","Date");
        Fields[1] = Lan.g("TableClaimPay","Amount");
        Fields[2] = Lan.g("TableClaimPay","Check Num");
        Fields[3] = Lan.g("TableClaimPay","Bank/Branch");
        Fields[4] = Lan.g("TableClaimPay","Note");
        ColAlign[1] = HorizontalAlignment.Right;
        ColWidth[0] = 70;
        ColWidth[1] = 80;
        ColWidth[2] = 100;
        ColWidth[3] = 100;
        ColWidth[4] = 180;
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
        // TableClaimProc
        //
        this.Name = "TableClaimProc";
        this.Load += new System.EventHandler(this.TableClaimProc_Load);
        this.ResumeLayout(false);
    }

    private void tableClaimProc_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


