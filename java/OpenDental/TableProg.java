//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.Lan;

/**
* 
*/
public class TableProg  extends OpenDental.ContrTable 
{
    /**
    * 
    */
    public float NoteWidth = new float();
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableProg() throws Exception {
        initializeComponent();
    }

    // This call is required by the Windows Form Designer.
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
        // TableProg
        //
        this.Name = "TableProg";
        this.Size = new System.Drawing.Size(484, 168);
        this.Load += new System.EventHandler(this.TableProg_Load);
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    public void instantClasses() throws Exception {
    }

    private void tableProg_Load(Object sender, System.EventArgs e) throws Exception {
        MaxRows = 20;
        MaxCols = 8;
        ShowScroll = true;
        FieldsArePresent = true;
        HeadingIsPresent = true;
        instantClassesPar();
        setRowHeight(0,19,14);
        Heading = Lan.g("TableProg","Progress Notes");
        Fields[0] = Lan.g("TableProg","Date");
        Fields[1] = Lan.g("TableProg","Th");
        Fields[2] = Lan.g("TableProg","Surf");
        Fields[3] = Lan.g("TableProg","Dx");
        Fields[4] = Lan.g("TableProg","Description");
        Fields[5] = Lan.g("TableProg","Stat");
        Fields[6] = Lan.g("TableProg","Prov");
        Fields[7] = Lan.g("TableProg","Amount");
        ColAlign[7] = HorizontalAlignment.Right;
        ColWidth[0] = 63;
        ColWidth[1] = 22;
        ColWidth[2] = 37;
        ColWidth[3] = 22;
        ColWidth[4] = 225;
        ColWidth[5] = 25;
        ColWidth[6] = 35;
        ColWidth[7] = 50;
        layoutTables();
        NoteWidth = (float)(ColWidth[2] + ColWidth[3] + ColWidth[4] + ColWidth[5] + ColWidth[6] + ColWidth[7]);
    }

    //GridColor=Color.Gray;
    /**
    * 
    */
    public void setProcRow(int row) throws Exception {
        for (int j = 0;j < MaxCols;j++)
        {
            LeftBorder[j, row] = DefaultGridColor;
            TopBorder[j, row] = DefaultGridColor;
            IsOverflow[j, row] = false;
        }
    }

    /**
    * 
    */
    public void setNoteRow(int row) throws Exception {
        for (int j = 0;j < 2;j++)
        {
            LeftBorder[j, row] = DefaultGridColor;
            TopBorder[j, row] = DefaultGridColor;
            IsOverflow[j, row] = false;
        }
        LeftBorder[2, row] = DefaultGridColor;
        TopBorder[2, row] = Color.White;
        IsOverflow[2, row] = false;
        for (int j = 3;j < MaxCols;j++)
        {
            LeftBorder[j, row] = Color.White;
            TopBorder[j, row] = Color.White;
            IsOverflow[j, row] = true;
        }
    }

    /**
    * 
    */
    public void setFirstNoteRow(int row) throws Exception {
        for (int j = 0;j < 2;j++)
        {
            LeftBorder[j, row] = DefaultGridColor;
            TopBorder[j, row] = DefaultGridColor;
            IsOverflow[j, row] = false;
        }
        LeftBorder[2, row] = DefaultGridColor;
        TopBorder[2, row] = DefaultGridColor;
        IsOverflow[2, row] = false;
        for (int j = 3;j < MaxCols;j++)
        {
            LeftBorder[j, row] = Color.White;
            TopBorder[j, row] = DefaultGridColor;
            IsOverflow[j, row] = true;
        }
    }

}


