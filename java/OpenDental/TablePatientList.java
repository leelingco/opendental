//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TablePatientList  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TablePatientList() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 20;
        MaxCols = 10;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = Lan.g("tbPatientList","Patient List");
        Fields[0] = Lan.g("TablePatientList","Last Name");
        Fields[1] = Lan.g("TablePatientList","First Name");
        Fields[2] = Lan.g("TablePatientList","MI");
        Fields[3] = Lan.g("TablePatientList","Pref'd Name");
        Fields[4] = Lan.g("TablePatientList","Age");
        Fields[5] = Lan.g("TablePatientList","SSN");
        Fields[6] = Lan.g("TablePatientList","Phone");
        Fields[7] = Lan.g("TablePatientList","Address");
        Fields[8] = Lan.g("TablePatientList","Status");
        Fields[9] = Lan.g("TablePatientList","Bill Type");
        ColWidth[0] = 80;
        ColWidth[1] = 80;
        ColWidth[2] = 25;
        ColWidth[3] = 80;
        ColWidth[4] = 40;
        ColWidth[5] = 65;
        ColWidth[6] = 90;
        ColWidth[7] = 100;
        ColWidth[8] = 65;
        ColWidth[9] = 75;
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
        // TablePatientList
        //
        this.Name = "TablePatientList";
        this.Load += new System.EventHandler(this.TablePatientList_Load);
        this.ResumeLayout(false);
    }

    private void tablePatientList_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

}


