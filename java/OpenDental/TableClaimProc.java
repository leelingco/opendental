//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableClaimProc  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableClaimProc() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 13;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = Lan.g("TableClaimProc","Procedures");
        Fields[0] = Lan.g("TableClaimProc","Date");
        Fields[1] = Lan.g("TableClaimProc","Prov");
        Fields[2] = Lan.g("TableClaimProc","Code");
        Fields[3] = Lan.g("TableClaimProc","Tth");
        Fields[4] = Lan.g("TableClaimProc","Description");
        Fields[5] = Lan.g("TableClaimProc","Fee Billed");
        Fields[6] = Lan.g("TableClaimProc","Deduct");
        Fields[7] = Lan.g("TableClaimProc","Ins Est");
        Fields[8] = Lan.g("TableClaimProc","Ins Pay");
        Fields[9] = Lan.g("TableClaimProc","WriteOff");
        Fields[10] = Lan.g("TableClaimProc","Status");
        Fields[11] = Lan.g("TableClaimProc","Pmt");
        Fields[12] = Lan.g("TableClaimProc","Remarks");
        ColAlign[5] = HorizontalAlignment.Right;
        ColAlign[6] = HorizontalAlignment.Right;
        ColAlign[7] = HorizontalAlignment.Right;
        ColAlign[8] = HorizontalAlignment.Right;
        ColAlign[9] = HorizontalAlignment.Right;
        ColAlign[10] = HorizontalAlignment.Center;
        ColAlign[11] = HorizontalAlignment.Center;
        ColWidth[0] = 70;
        ColWidth[1] = 50;
        ColWidth[2] = 50;
        ColWidth[3] = 35;
        ColWidth[4] = 130;
        ColWidth[5] = 65;
        ColWidth[6] = 65;
        ColWidth[7] = 65;
        ColWidth[8] = 65;
        ColWidth[9] = 65;
        ColWidth[10] = 50;
        ColWidth[11] = 40;
        ColWidth[12] = 170;
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


