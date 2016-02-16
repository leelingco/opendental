//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;


/**
* 
*/
public class TableTimeBar  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableTimeBar() throws Exception {
        initializeComponent();
        // This call is required by the Windows Form Designer.
        MaxRows = 40;
        MaxCols = 1;
        ShowScroll = false;
        FieldsArePresent = false;
        HeadingIsPresent = false;
        instantClassesPar();
        setRowHeight(0,39,14);
        ColWidth[0] = 13;
        ColAlign[0] = HorizontalAlignment.Center;
        SetGridColor(Color.LightGray);
        /*TopBorder[0,6]=Color.Black;
        			TopBorder[0,12]=Color.Black;
        			TopBorder[0,18]=Color.Black;
        			TopBorder[0,24]=Color.Black;
        			TopBorder[0,30]=Color.Black;
        			TopBorder[0,36]=Color.Black;*/
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
        // TableTimeBar
        //
        this.Name = "TableTimeBar";
        this.Load += new System.EventHandler(this.TableTimeBar_Load);
        this.ResumeLayout(false);
    }

    private void tableTimeBar_Load(Object sender, System.EventArgs e) throws Exception {
        layoutTables();
    }

    private void butSlider_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    private void butSlider_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    private void butSlider_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

}


