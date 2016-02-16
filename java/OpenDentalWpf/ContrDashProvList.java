//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentalWpf;

import OpenDentalWpf.ContrDashProvList_Row;
import OpenDentBusiness.DashboardQueries;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Providers;


/**
* 
*/
public class ContrDashProvList  extends UserControl 
{

    DataTable table = new DataTable();
    DateTime DateShowing = new DateTime();
    public ContrDashProvList() throws Exception {
        InitializeComponent();
        DateShowing = DateTime.Today;
        gridMain.SelectionMode = DataGridSelectionMode.Extended;
        gridMain.SelectionUnit = DataGridSelectionUnit.FullRow;
    }

    public void fillData() throws Exception {
        textDate.Text = DateShowing.ToString("ddd") + " " + DateShowing.ToShortDateString();
        table = DashboardQueries.getProvList(DateShowing);
        fillScreen();
    }

    public List<int> getSelectedIndices() throws Exception {
        List<int> retVal = new List<int>();
        for (int i = 0;i < gridMain.SelectedItems.Count;i++)
        {
            retVal.Add(gridMain.Items.IndexOf(gridMain.SelectedItems[i]));
        }
        return retVal;
    }

    private void fillScreen() throws Exception {
        List<ContrDashProvList_Row> ListProv = new List<ContrDashProvList_Row>();
        ContrDashProvList_Row row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new ContrDashProvList_Row();
            row.setProvName(Providers.GetAbbr(PIn.Long(table.Rows[i]["ProvNum"].ToString())));
            System.Drawing.Color c1 = Providers.GetColor(PIn.Long(table.Rows[i]["ProvNum"].ToString()));
            row.setProvColor(Color.FromArgb(c1.A, c1.R, c1.G, c1.B));
            double production = PIn.Decimal(table.Rows[i]["production"].ToString());
            if (production == 0)
            {
                row.setProduction("");
            }
            else
            {
                row.setProduction(production.ToString("c0"));
            } 
            double income = PIn.Decimal(table.Rows[i]["income"].ToString());
            if (income == 0)
            {
                row.setIncome("");
            }
            else
            {
                row.setIncome(income.ToString("c0"));
            } 
            ListProv.Add(row);
        }
        //Style style=new Style(typeof(TextBlock));
        //style.Setters.Add(new Setter(TextBlock.HorizontalAlignmentProperty,HorizontalAlignment.Right));
        //((DataGridTextColumn)gridMain.Columns[2]).ElementStyle=style;
        //((DataGridTextColumn)gridMain.Columns[2]).Binding=new Binding("Production");
        //((DataGridTextColumn)gridMain.Columns[2]).Binding.StringFormat="c0";
        gridMain.ItemsSource = ListProv;
    }

    private void labelR_MouseDown(Object sender, MouseButtonEventArgs e) throws Exception {
        DateShowing = DateShowing.AddDays(1);
        fillData();
    }

    private void labelL_MouseDown(Object sender, MouseButtonEventArgs e) throws Exception {
        DateShowing = DateShowing.AddDays(-1);
        fillData();
    }

}


