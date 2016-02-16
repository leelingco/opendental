//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentalWpf;

import OpenDentBusiness.DashboardQueries;


/**
* 
*/
public class ContrDashProdInc  extends UserControl 
{

    /**
    * Path 0 is prod, and path 1 is inc.
    */
    private List<Path> ListPaths = new List<Path>();
    /**
    * 0 is prod, and 1 is inc.  Each item contains a list of dots.
    */
    private List<List<Ellipse>> ListDots = new List<List<Ellipse>>();
    /**
    * 0 is prod, and 1 is inc.  Each item contains a list of dots that are used for hover effects, not visible.
    */
    private List<List<Ellipse>> ListDotsBig = new List<List<Ellipse>>();
    private boolean IsHovering = new boolean();
    /**
    * 0 is prod, and 1 is inc.  Each item contains a list of amounts. This is the data that will be plotted.
    */
    private List<List<int>> ListData = new List<List<int>>();
    /**
    * Each path gets a different color.
    */
    private List<Color> ListColors = new List<Color>();
    /**
    * For a small office, this might be 20k.  Big office might be 200k.
    */
    private int YIncrement = new int();
    /**
    * Either 1 or 1000.
    */
    private double YMultFactor = new double();
    private List<int> visibleIndicesOld = new List<int>();
    private List<int> visibleIndices = new List<int>();
    public ContrDashProdInc() throws Exception {
        InitializeComponent();
    }

    public void fillData() throws Exception {
        YMultFactor = 1000;
        //colors-------------------------------------------------------------------------------
        ListColors = new List<Color>();
        ListColors.Add(Colors.MediumBlue);
        //production
        ListColors.Add(Colors.Green);
        //income
        //data---------------------------------------------------------------------------------
        ListData = DashboardQueries.getProdInc(getDateStart(),getDateEnd());
        fillGraph();
        visibleIndices = new List<int>();
        visibleIndices.Add(0);
        visibleIndices.Add(1);
        visibleIndicesOld = new List<int>(visibleIndices);
    }

    /**
    * This lets us set some paths not visible.  An empty collection means that all data is visible.
    */
    public void setVisibleIndices(List<int> value) throws Exception {
        visibleIndices = value;
        fadeInAndOut();
    }

    public DateTime getDateStart() throws Exception {
        DateTime dateFirstThisMonth = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
        return dateFirstThisMonth.AddMonths(-12);
    }

    public DateTime getDateEnd() throws Exception {
        DateTime dateFirstThisMonth = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
        return dateFirstThisMonth.AddDays(-1);
    }

    private void fillGraph() throws Exception {
        double wCol = rectMain.Width / 11d;
        for (double i = 1;i < 11;i++)
        {
            //vertical lines----------------------------------------------------------------------
            Line line = new Line();
            line.X1 = rectMain.Left() + (i * wCol);
            line.Y1 = rectMain.Top();
            line.X2 = rectMain.Left() + (i * wCol);
            line.Y2 = rectMain.Bottom();
            line.Stroke = Brushes.LightGray;
            line.StrokeThickness = 1;
            canvasMain.Children.Add(line);
            //year marker
            if (getDateStart().AddMonths((int)i).Month == 1)
            {
                line = new Line();
                line.X1 = rectMain.Left() + (i * wCol) - wCol / 2d;
                line.Y1 = rectMain.Top();
                line.X2 = rectMain.Left() + (i * wCol) - wCol / 2d;
                line.Y2 = rectMain.Bottom();
                line.Stroke = Brushes.Black;
                line.StrokeThickness = 1.5;
                Canvas.SetZIndex(line, 5);
                //same as grid outline, causing horizontal lines to go under
                canvasMain.Children.Add(line);
            }
             
        }
        for (double i = 0;i < 12;i++)
        {
            //x axis numbers-----------------------------------------------------------------------
            Label label = new Label();
            String content = getDateStart().AddMonths((int)i).ToString("%M");
            label.Content = content;
            label.MaxWidth = 100;
            Canvas.SetTop(label, rectMain.Bottom() - 4);
            Typeface typeface = new Typeface(FontFamily, FontStyle, FontWeight, FontStretch);
            FormattedText ft = new FormattedText(content, CultureInfo.CurrentCulture, FlowDirection.LeftToRight, typeface, FontSize, Foreground);
            double wText = ft.Width;
            //Debug.WriteLine(content+": "+wText.ToString("F0"));
            Canvas.SetLeft(label, rectMain.Left() + (i * wCol) - wText / 2d - 5);
            canvasMain.Children.Add(label);
        }
        //calculate max y and increments---------------------------------------------------------
        double maxValD = 0;
        for (int p = 0;p < ListData.Count;p++)
        {
            for (int i = 0;i < ListData[p].Count;i++)
            {
                if ((ListData[p][i] / YMultFactor) > maxValD)
                {
                    maxValD = (double)ListData[p][i] / YMultFactor;
                }
                 
            }
        }
        //add 5% for white space
        maxValD = 1.05 * maxValD;
        //round up to nearest int. This is important if the max is just over 1k.
        int maxVal = (int)maxValD + 1;
        //calculate amount for each tick.  No more than 10 ticks
        //replace the code below later with a more elegant algorithm to handle unlimited scaling
        YIncrement = 1;
        if (maxVal > 10)
        {
            YIncrement = 2;
        }
         
        if (maxVal > 20)
        {
            YIncrement = 5;
        }
         
        if (maxVal > 50)
        {
            YIncrement = 10;
        }
         
        if (maxVal > 100)
        {
            YIncrement = 20;
        }
         
        if (maxVal > 200)
        {
            YIncrement = 50;
        }
         
        //etc
        //int yCount=(int)(maxVal/10000);
        //double hRow=rectMain.Height/(yCount-1);
        double hRow = rectMain.Height / (double)maxVal * (double)YIncrement;
        //in pixels
        int tickCount = (int)(rectMain.Height / hRow);
        for (double i = 0;i < tickCount;i++)
        {
            //trunc to int. Does not include the tick at zero.
            //horizontal lines----------------------------------------------------------------------
            //the first tick is not at zero
            Line line = new Line();
            line.X1 = rectMain.Left();
            line.Y1 = rectMain.Bottom() - ((i + 1) * hRow);
            line.X2 = rectMain.Right();
            line.Y2 = rectMain.Bottom() - ((i + 1) * hRow);
            line.Stroke = Brushes.LightGray;
            line.StrokeThickness = 1;
            canvasMain.Children.Add(line);
        }
        for (double i = 0;i < tickCount;i++)
        {
            //y axis numbers-----------------------------------------------------------------------
            Label label = new Label();
            String content = ((i + 1) * YIncrement).ToString();
            if (YMultFactor == 1000)
            {
                content = content + "k";
            }
             
            label.Content = content;
            label.MaxWidth = 200;
            Canvas.SetTop(label, rectMain.Bottom() - ((i + 1) * hRow) - 14);
            Canvas.SetLeft(label, -3);
            label.Width = rectMain.Left() + 5;
            label.HorizontalContentAlignment = HorizontalAlignment.Right;
            canvasMain.Children.Add(label);
        }
        //Initialize-----------------------------------------------------------------------------
        ListPaths = new List<Path>();
        ListDots = new List<List<Ellipse>>();
        ListDotsBig = new List<List<Ellipse>>();
        for (int p = 0;p < ListData.Count;p++)
        {
            //Paths and dots-------------------------------------------------------------------------
            PathFigure pathFig = new PathFigure();
            List<Ellipse> listDotsOneType = new List<Ellipse>();
            List<Ellipse> listDotsBigOneType = new List<Ellipse>();
            for (int i = 0;i < ListData[p].Count;i++)
            {
                Point pt = new Point(rectMain.Left() + (i * wCol), rectMain.Bottom() - (double)((double)ListData[p][i] / YMultFactor * hRow / (double)YIncrement));
                if (i == 0)
                {
                    pt.X += 1;
                }
                 
                if (i == ListData[p].Count - 1)
                {
                    pt.X -= 1;
                }
                 
                if (i == 0)
                {
                    pathFig.StartPoint = pt;
                }
                else
                {
                    LineSegment lineSeg = new LineSegment();
                    lineSeg.Point = pt;
                    pathFig.Segments.Add(lineSeg);
                } 
                //dots
                Ellipse ellipse = new Ellipse();
                ellipse.Height = 4;
                ellipse.Width = 4;
                Canvas.SetLeft(ellipse, pt.X - 2);
                Canvas.SetTop(ellipse, pt.Y - 2);
                ellipse.Fill = new SolidColorBrush(ListColors[p]);
                Panel.SetZIndex(ellipse, 6);
                canvasMain.Children.Add(ellipse);
                listDotsOneType.Add(ellipse);
                //dotsBig
                ellipse = new Ellipse();
                ellipse.Height = 14;
                ellipse.Width = 14;
                Canvas.SetLeft(ellipse, pt.X - 7);
                Canvas.SetTop(ellipse, pt.Y - 7);
                ellipse.Opacity = 0;
                ellipse.Fill = Brushes.Red;
                Panel.SetZIndex(ellipse, 7);
                //in front of everything
                ellipse.MouseEnter += new MouseEventHandler(dotBig_MouseEnter);
                ellipse.MouseLeave += new MouseEventHandler(dotBig_MouseLeave);
                canvasMain.Children.Add(ellipse);
                listDotsBigOneType.Add(ellipse);
            }
            PathGeometry pathGeo = new PathGeometry();
            pathGeo.Figures.Add(pathFig);
            Path path = new Path();
            path.Data = pathGeo;
            path.Stroke = new SolidColorBrush(ListColors[p]);
            path.StrokeThickness = 1.5;
            Panel.SetZIndex(path, 6);
            //in front of grid
            canvasMain.Children.Add(path);
            ListPaths.Add(path);
            ListDots.Add(listDotsOneType);
            ListDotsBig.Add(listDotsBigOneType);
        }
    }

    void dotBig_MouseEnter(Object sender, MouseEventArgs e) throws Exception {
        if (IsHovering)
        {
            return ;
        }
         
        //don't jump when two big dots overlap
        int idxPath = -1;
        int idxDot = -1;
        for (int i = 0;i < ListDotsBig.Count;i++)
        {
            if (visibleIndices != null && visibleIndices.Count > 0 && !visibleIndices.Contains(i))
            {
                continue;
            }
             
            if (ListDotsBig[i].IndexOf((Ellipse)sender) == -1)
            {
                continue;
            }
             
            idxPath = i;
            idxDot = ListDotsBig[i].IndexOf((Ellipse)sender);
        }
        if (idxDot == -1)
        {
            return ;
        }
         
        labelHover.Opacity = 1;
        String numFormat = "n0";
        if (YMultFactor == 1000)
        {
            numFormat = "c0";
        }
         
        String content = getDateStart().AddMonths(idxDot).ToString("MMM") + ": " + ListData[idxPath][idxDot].ToString(numFormat);
        labelHover.Content = content;
        double xCenter = Canvas.GetLeft((Ellipse)sender) + 7;
        double yCenter = Canvas.GetTop((Ellipse)sender) + 7;
        Typeface typeface = new Typeface(FontFamily, FontStyle, FontWeight, FontStretch);
        FormattedText ft = new FormattedText(labelHover.Content.ToString(), CultureInfo.CurrentCulture, FlowDirection.LeftToRight, typeface, FontSize, Foreground);
        double wText = ft.Width;
        Canvas.SetLeft(labelHover, xCenter - wText / 2d - 1);
        Canvas.SetTop(labelHover, yCenter - 23);
        Panel.SetZIndex(labelHover, 8);
        //bring to front of other element
        IsHovering = true;
    }

    void dotBig_MouseLeave(Object sender, MouseEventArgs e) throws Exception {
        if (!IsHovering)
        {
            return ;
        }
         
        IsHovering = false;
        labelHover.Opacity = 0;
        Panel.SetZIndex(labelHover, 0);
    }

    //send it to the back so it won't interfere with mouse hover
    private void checkProduction_Checked(Object sender, RoutedEventArgs e) throws Exception {
        if (ListPaths == null)
        {
            return ;
        }
         
        visibleIndices.Add(0);
        fadeInAndOut();
    }

    private void checkProduction_Unchecked(Object sender, RoutedEventArgs e) throws Exception {
        if (checkIncome.IsChecked == false)
        {
            visibleIndices.Remove(0);
            //because the next line will add it back
            checkProduction.IsChecked = true;
            return ;
        }
         
        //don't allow unchecking both
        visibleIndices.Remove(0);
        fadeInAndOut();
    }

    private void checkIncome_Checked(Object sender, RoutedEventArgs e) throws Exception {
        if (ListPaths == null)
        {
            return ;
        }
         
        visibleIndices.Add(1);
        fadeInAndOut();
    }

    private void checkIncome_Unchecked(Object sender, RoutedEventArgs e) throws Exception {
        if (checkProduction.IsChecked == false)
        {
            visibleIndices.Remove(1);
            //because the next line will add it back
            checkIncome.IsChecked = true;
            return ;
        }
         
        //don't allow unchecking both
        visibleIndices.Remove(1);
        fadeInAndOut();
    }

    private void fadeInAndOut() throws Exception {
        if (ListPaths == null)
        {
            return ;
        }
         
        //Fade out--------------------------------------------------------------------------
        DoubleAnimation animation = new DoubleAnimation();
        animation.From = 1;
        animation.To = 0;
        animation.Duration = TimeSpan.FromMilliseconds(200);
        animation.FillBehavior = FillBehavior.HoldEnd;
        for (int p = 0;p < ListPaths.Count;p++)
        {
            if (visibleIndices.Count == 0 || visibleIndices.Contains(p))
            {
                continue;
            }
             
            //don't fade out if it's supposed to be currently visible
            if (visibleIndicesOld != null && visibleIndicesOld.Count > 0 && !visibleIndicesOld.Contains(p))
            {
                continue;
            }
             
            //don't fade out if it was already not visible
            ListPaths[p].BeginAnimation(OpacityProperty, animation);
            for (int i = 0;i < ListDots[p].Count;i++)
            {
                ListDots[p][i].BeginAnimation(OpacityProperty, animation);
                ListDotsBig[p][i].IsHitTestVisible = false;
            }
        }
        //the other elements won't interfere with hit test because of z-order.
        //Fade in---------------------------------------------------------------------------
        animation = new DoubleAnimation();
        animation.From = 0;
        animation.To = 1;
        animation.Duration = TimeSpan.FromMilliseconds(200);
        animation.FillBehavior = FillBehavior.HoldEnd;
        for (int p = 0;p < ListPaths.Count;p++)
        {
            if (visibleIndices.Count > 0 && !visibleIndices.Contains(p))
            {
                continue;
            }
             
            //don't fade in if it's supposed to be currently not visible
            if (visibleIndicesOld == null || visibleIndicesOld.Count == 0 || visibleIndicesOld.Contains(p))
            {
                continue;
            }
             
            //don't fade in if it was already visible
            ListPaths[p].BeginAnimation(OpacityProperty, animation);
            for (int i = 0;i < ListDots[p].Count;i++)
            {
                ListDots[p][i].BeginAnimation(OpacityProperty, animation);
                ListDotsBig[p][i].IsHitTestVisible = true;
            }
        }
        visibleIndicesOld = new List<int>(visibleIndices);
    }

}


