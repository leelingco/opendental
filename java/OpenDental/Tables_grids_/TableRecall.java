//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.Tables_grids_;


/**
* 
*/
public class TableRecall  extends OpenDental.ContrTable 
{
    private System.ComponentModel.IContainer components = null;
    /**
    * 
    */
    public TableRecall() throws Exception {
        // This call is required by the Windows Form Designer.
        initializeComponent();
    }

    //InstantClasses();
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

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // TableRecall
        //
        this.Name = "TableRecall";
        this.Load += new System.EventHandler(this.TableRecall_Load);
        this.ResumeLayout(false);
    }

    //This table is not used yet.  It could replace the listview controls in FormAptsOther and FormRecallListEdit.
    /*
    		///<summary></summary>
    		public void InstantClasses(){
    			MaxRows=4;
    			MaxCols=6;
    			ShowScroll=true;
    			FieldsArePresent=true;
    			HeadingIsPresent=true;
    			InstantClassesPar();
    			SetRowHeight(0,3,14);
    			SetGridColor(Color.LightGray);
    			SetBackGColor(Color.White);
    			Heading=Lan.g("TableRecall","Family Members");
    			Fields[0]=Lan.g("TableRecall","Name");
    			Fields[1]=Lan.g("TableRecall","Position");
    			Fields[2]=Lan.g("TableRecall","Gender");
    			Fields[3]=Lan.g("TableRecall","Status");
    			Fields[4]=Lan.g("TableRecall","Age");
    			Fields[5]=Lan.g("TableRecall","Recall Due");
    			ColWidth[0]=140;
    			ColWidth[1]=70;
    			ColWidth[2]=60;
    			ColWidth[3]=70;
    			ColWidth[4]=50;
    			ColWidth[5]=80;
    			LayoutTables();
    		}*/
    private void tableRecall_Load(Object sender, System.EventArgs e) throws Exception {
    }

}


