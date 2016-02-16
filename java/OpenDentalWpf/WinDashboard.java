//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentalWpf;

import OpenDentalWpf.PrintHelper;
import OpenDentBusiness.DashboardAR;
import OpenDentBusiness.DashboardARs;
import OpenDentBusiness.DashboardQueries;
import OpenDentBusiness.Lans;


/**
* 
*/
public class WinDashboard  extends Window 
{

    public WinDashboard() throws Exception {
        InitializeComponent();
    }

    private void window_Loaded(Object sender, RoutedEventArgs e) throws Exception {
        contrDashProvList.FillData();
        //prodProvs
        List<Color> listColorsProd = DashboardQueries.getProdProvColors();
        List<List<int>> listDataProd = DashboardQueries.GetProdProvs(contrDashProdProvs.DateStart, contrDashProdProvs.DateEnd);
        contrDashProdProvs.FillData(Lans.g(this,"Production by Prov"), 1000, listColorsProd, listDataProd);
        //A/R
        List<Color> listColorsAR = new List<Color>();
        listColorsAR.Add(Colors.Firebrick);
        List<DashboardAR> listDashAR = DashboardARs.Refresh(contrDashAR.DateStart);
        if (listDashAR.Count == 0)
        {
            MessageBoxResult result = MessageBox.Show(Lans.g(this,"A one-time routine needs to be run that will take a few moments."), "", MessageBoxButton.OKCancel);
            if (result != MessageBoxResult.OK)
            {
                Close();
                return ;
            }
             
        }
         
        List<List<int>> listDataAR = DashboardQueries.GetAR(contrDashAR.DateStart, contrDashAR.DateEnd, listDashAR);
        contrDashAR.FillData(Lans.g(this,"Accounts Receivable"), 1000, listColorsAR, listDataAR);
        //ProdInc
        contrDashProdInc.FillData();
        //new pat
        List<Color> listColorsNP = new List<Color>();
        listColorsNP.Add(Colors.Chocolate);
        List<List<int>> listDataNP = DashboardQueries.GetNewPatients(contrDashNewPat.DateStart, contrDashNewPat.DateEnd);
        contrDashNewPat.FillData(Lans.g(this,"New Patients"), 1, listColorsNP, listDataNP);
    }

    private void window_Activated(Object sender, EventArgs e) throws Exception {
    }

    private void contrDashProvList_SelectionChanged(Object sender, SelectionChangedEventArgs e) throws Exception {
        contrDashProdProvs.VisibleIndices = contrDashProvList.SelectedIndices;
    }

    private void butPrint_Click(Object sender, RoutedEventArgs e) throws Exception {
        //move this first section, including the dlg into PrintHelper, analogous to OpenDental.PrinterL.  Or maybe into OpenDentalWpf.PrinterL?
        PrintDialog dlg = new PrintDialog();
        PrintQueue pq = LocalPrintServer.GetDefaultPrintQueue();
        PrintTicket tick = pq.DefaultPrintTicket;
        tick.PageOrientation = PageOrientation.Landscape;
        dlg.PrintTicket = tick;
        dlg.PrintQueue = pq;
        if (dlg.ShowDialog() != true)
        {
            return ;
        }
         
        FixedDocument document = new FixedDocument();
        document.PrintTicket = dlg.PrintTicket;
        document.DocumentPaginator.PageSize = new Size(dlg.PrintableAreaWidth, dlg.PrintableAreaHeight);
        Canvas canvas1 = PrintHelper.getCanvas(document);
        //set up a grid for printing that's the same as the main grid except for the bottom section with the buttons
        Grid gridPrint = new Grid();
        gridPrint.Width = 906;
        gridPrint.Height = 603;
        //5 columns
        gridPrint.ColumnDefinitions.Add(new ColumnDefinition());
        ColumnDefinition colDef = new ColumnDefinition();
        colDef.Width = new GridLength(3);
        gridPrint.ColumnDefinitions.Add(colDef);
        gridPrint.ColumnDefinitions.Add(new ColumnDefinition());
        colDef = new ColumnDefinition();
        colDef.Width = new GridLength(3);
        gridPrint.ColumnDefinitions.Add(colDef);
        gridPrint.ColumnDefinitions.Add(new ColumnDefinition());
        //3 rows
        gridPrint.RowDefinitions.Add(new RowDefinition());
        RowDefinition rowDef = new RowDefinition();
        rowDef.Height = new GridLength(3);
        gridPrint.RowDefinitions.Add(rowDef);
        gridPrint.RowDefinitions.Add(new RowDefinition());
        //draw rectangles to separate sections
        //3 vert:
        Rectangle rect = new Rectangle();
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 300;
        Grid.SetRow(rect, 0);
        Grid.SetColumn(rect, 1);
        gridPrint.Children.Add(rect);
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 3;
        Grid.SetRow(rect, 1);
        Grid.SetColumn(rect, 1);
        gridPrint.Children.Add(rect);
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 300;
        Grid.SetRow(rect, 2);
        Grid.SetColumn(rect, 1);
        gridPrint.Children.Add(rect);
        //1 horiz
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 300;
        rect.Height = 3;
        Grid.SetRow(rect, 1);
        Grid.SetColumn(rect, 2);
        gridPrint.Children.Add(rect);
        //3 more vert:
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 300;
        Grid.SetRow(rect, 0);
        Grid.SetColumn(rect, 3);
        gridPrint.Children.Add(rect);
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 3;
        Grid.SetRow(rect, 1);
        Grid.SetColumn(rect, 3);
        gridPrint.Children.Add(rect);
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 3;
        rect.Height = 300;
        Grid.SetRow(rect, 2);
        Grid.SetColumn(rect, 3);
        gridPrint.Children.Add(rect);
        //1 more horiz
        rect = new Rectangle();
        rect.Fill = Brushes.LightGray;
        rect.Width = 300;
        rect.Height = 3;
        Grid.SetRow(rect, 1);
        Grid.SetColumn(rect, 4);
        gridPrint.Children.Add(rect);
        //add the grid to the canvas
        canvas1.Children.Add(gridPrint);
        double center = canvas1.Width / 2d;
        Canvas.SetLeft(gridPrint, (canvas1.Width / 2d) - (gridPrint.Width / 2));
        //draw a rectangle around the entire grid
        rect = new Rectangle();
        rect.Stroke = Brushes.DarkGray;
        rect.StrokeThickness = 1;
        rect.Width = 906;
        rect.Height = 603;
        Canvas.SetLeft(rect, (canvas1.Width / 2d) - (rect.Width / 2));
        canvas1.Children.Add(rect);
        //add the five dashboard controls
        gridMain.Children.Remove(contrDashProvList);
        gridPrint.Children.Add(contrDashProvList);
        gridMain.Children.Remove(contrDashProdProvs);
        gridPrint.Children.Add(contrDashProdProvs);
        gridMain.Children.Remove(contrDashAR);
        gridPrint.Children.Add(contrDashAR);
        gridMain.Children.Remove(contrDashProdInc);
        gridPrint.Children.Add(contrDashProdInc);
        gridMain.Children.Remove(contrDashNewPat);
        gridPrint.Children.Add(contrDashNewPat);
        //Canvas.SetTop(contrDashProdInc,
        //dlg.PrintDocument(document.DocumentPaginator,"Dashboard");//old
        XpsDocumentWriter writer = PrintQueue.CreateXpsDocumentWriter(dlg.PrintQueue);
        Cursor = Cursors.Wait;
        writer.Write(document, dlg.PrintTicket);
        //use WriteAsynch usually, but we can't here because we "borrowed" the controls from the screen.
        Cursor = Cursors.Arrow;
        //myPanel.Measure(new Size(dialog.PrintableAreaWidth,dialog.PrintableAreaHeight));
        //myPanel.Arrange(new Rect(new Point(0, 0),myPanel.DesiredSize));
        //dlg.PrintVisual(gridMain,"Dashboard");
        gridPrint.Children.Remove(contrDashProvList);
        gridMain.Children.Add(contrDashProvList);
        gridPrint.Children.Remove(contrDashProdProvs);
        gridMain.Children.Add(contrDashProdProvs);
        gridPrint.Children.Remove(contrDashAR);
        gridMain.Children.Add(contrDashAR);
        gridPrint.Children.Remove(contrDashProdInc);
        gridMain.Children.Add(contrDashProdInc);
        gridPrint.Children.Remove(contrDashNewPat);
        gridMain.Children.Add(contrDashNewPat);
    }

    private void butClose_Click(Object sender, RoutedEventArgs e) throws Exception {
        Close();
    }

}


