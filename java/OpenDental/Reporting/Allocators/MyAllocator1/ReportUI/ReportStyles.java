//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1.ReportUI;


public class ReportStyles   
{
    public static Color _BacgroundColor_MaroonLight = Color.FromArgb(229, 222, 222);
    public static Color _ForgroundColor_MaroonDark = Color.FromArgb(132, 0, 0);
    // Maroon
    public static void set_DefaultDataGridViewStyle(DataGridView dgv) throws Exception {
        DataGridViewCellStyle defaultCellStyle1 = new DataGridViewCellStyle();
        defaultCellStyle1.Padding = new Padding(0, 0, 0, 0);
        defaultCellStyle1.Font = new Font("Arial", 8F);
        defaultCellStyle1.ForeColor = _ForgroundColor_MaroonDark;
        defaultCellStyle1.BackColor = _BacgroundColor_MaroonLight;
        DataGridViewCellStyle defaultCellStyle2 = new DataGridViewCellStyle(defaultCellStyle1);
        defaultCellStyle2.BackColor = Color.White;
        // Set the Styles to the GridView
        dgv.DefaultCellStyle = defaultCellStyle1;
        dgv.AlternatingRowsDefaultCellStyle = defaultCellStyle2;
        dgv.CellBorderStyle = DataGridViewCellBorderStyle.None;
        dgv.RowHeadersVisible = false;
    }

}


