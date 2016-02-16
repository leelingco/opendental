//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RdlViewer;

import CS2JNet.System.LCC.Disposable;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RDL.OneFileStreamGen;
import fyiReporting.RDL.OutputPresentationType;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RDLParser;
import fyiReporting.RDL.UserReportParameter;
import fyiReporting.RdlViewer.DialogMessages;
import fyiReporting.RdlViewer.PageDrawing;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* RdlViewer displays RDL files or syntax.
*/
public class RdlViewer  extends System.Windows.Forms.Control 
{
    public NeedPassword GetDataSourceReferencePassword = null;
    boolean _InPaint = false;
    boolean _InLoading = false;
    private String _SourceFileName = new String();
    // file name to use
    private String _SourceRdl = new String();
    // source Rdl; if provided overrides filename
    private String _Parameters = new String();
    // parameters to run the report
    private fyiReporting.RDL.Report _Report;
    // the report
    private String _Folder = new String();
    // folder for DataSourceReference (if file name not provided)
    private Pages _pgs;
    // the pages of the report to view
    //private PageDrawing _pd;			// draws the pages of a report
    private boolean _loadFailed = new boolean();
    // last load of report failed
    private float _leftMargin = new float();
    // left margin; calculated based on size of window & scroll style
    // report information
    private float _PageWidth = new float();
    // width of page
    private float _PageHeight = new float();
    // height of page
    private String _ReportDescription = new String();
    private String _ReportAuthor = new String();
    private String _ReportName = new String();
    private IList _errorMsgs = new IList();
    // Zoom
    private float _zoom = new float();
    // zoom factor
    private float DpiX = new float();
    private float DpiY = new float();
    private fyiReporting.RdlViewer.ZoomEnum _zoomMode = fyiReporting.RdlViewer.ZoomEnum.FitWidth;
    private float _leftGap = 10;
    // right margin: 10 points
    private float _rightGap = 10;
    // left margin: 10 points
    private float _pageGap = 10;
    // gap between pages: 10 points
    // printing
    private int printEndPage = new int();
    // end page
    private int printCurrentPage = new int();
    // current page to print
    // Scrollbars
    private fyiReporting.RdlViewer.ScrollModeEnum _ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.SinglePage;
    private VScrollBar _vScroll = new VScrollBar();
    private ToolTip _vScrollToolTip = new ToolTip();
    private HScrollBar _hScroll = new HScrollBar();
    private PageDrawing _DrawPanel;
    // the main drawing panel
    private Button _RunButton = new Button();
    private PictureBox _WarningButton = new PictureBox();
    private ScrollableControl _ParameterPanel = new ScrollableControl();
    // panel for specifying parameters
    private int _ParametersMaxHeight = new int();
    // max height of controls in _ParameterPanel
    private boolean _ShowParameters = true;
    public RdlViewer() throws Exception {
        _SourceFileName = null;
        _SourceRdl = null;
        _Parameters = null;
        // parameters to run the report
        _pgs = null;
        // the pages of the report to view
        _loadFailed = false;
        _PageWidth = 0;
        _PageHeight = 0;
        _ReportDescription = null;
        _ReportAuthor = null;
        _ReportName = null;
        _zoom = -1;
        // force zoom to be calculated
        // Get our graphics DPI
        Graphics g = null;
        try
        {
            g = this.CreateGraphics();
            DpiX = g.DpiX;
            DpiY = g.DpiY;
        }
        catch (Exception __dummyCatchVar0)
        {
            DpiX = DpiY = 96;
        }
        finally
        {
            if (g != null)
                g.Dispose();
             
        }
        _ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.Continuous;
        // Handle the controls
        _vScroll = new VScrollBar();
        _vScroll.Scroll += new ScrollEventHandler(this.VerticalScroll);
        _vScroll.Enabled = false;
        // tooltip
        _vScrollToolTip = new ToolTip();
        _vScrollToolTip.AutomaticDelay = 100;
        // .1 seconds
        _vScrollToolTip.AutoPopDelay = 1000;
        // 1 second
        _vScrollToolTip.ReshowDelay = 100;
        // .1 seconds
        _vScrollToolTip.InitialDelay = 10;
        // .01 seconds
        _vScrollToolTip.ShowAlways = false;
        _vScrollToolTip.SetToolTip(_vScroll, "");
        _hScroll = new HScrollBar();
        _hScroll.Scroll += new ScrollEventHandler(this.HorizontalScroll);
        _hScroll.Enabled = false;
        _DrawPanel = new PageDrawing(null);
        _DrawPanel.Paint += new PaintEventHandler(this.DrawPanelPaint);
        _DrawPanel.Resize += new EventHandler(this.DrawPanelResize);
        _DrawPanel.MouseWheel += new MouseEventHandler(DrawPanelMouseWheel);
        _DrawPanel.KeyDown += new KeyEventHandler(DrawPanelKeyDown);
        _RunButton = new Button();
        _RunButton.Parent = this;
        _RunButton.Text = "Run Report";
        _RunButton.Width = 90;
        _RunButton.FlatStyle = FlatStyle.Flat;
        _RunButton.Click += new System.EventHandler(ParametersViewClick);
        _WarningButton = new PictureBox();
        _WarningButton.Parent = this;
        _WarningButton.Width = 15;
        _WarningButton.Height = 15;
        _WarningButton.Paint += new PaintEventHandler(_WarningButton_Paint);
        _WarningButton.Click += new System.EventHandler(WarningClick);
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(_WarningButton, "Click to see Report Warnings");
        _ParameterPanel = new ScrollableControl();
        this.Layout += new LayoutEventHandler(RdlViewer_Layout);
        this.SuspendLayout();
        // Must be added in this order for DockStyle to work correctly
        this.Controls.Add(_DrawPanel);
        this.Controls.Add(_vScroll);
        this.Controls.Add(_hScroll);
        this.Controls.Add(_ParameterPanel);
        this.ResumeLayout(false);
    }

    /**
    * True if Parameter panel should be shown.
    */
    public boolean getShowParameterPanel() throws Exception {
        loadPageIfNeeded();
        return _ShowParameters;
    }

    public void setShowParameterPanel(boolean value) throws Exception {
        _ShowParameters = value;
        rdlViewer_Layout(this,null);
    }

    // re layout based on new report
    /**
    * Returns the number of pages in the report.  0 is returned if no report has been loaded.
    */
    public int getPageCount() throws Exception {
        loadPageIfNeeded();
        if (_pgs == null)
            return 0;
        else
            return _pgs.getPageCount(); 
    }

    /**
    * Sets/Returns the page currently showing
    */
    public int getPageCurrent() throws Exception {
        if (_pgs == null)
            return 0;
         
        int pc = (int)(_pgs.getPageCount() * (long)_vScroll.Value / (double)_vScroll.Maximum) + 1;
        if (pc > _pgs.getPageCount())
            pc = _pgs.getPageCount();
         
        return pc;
    }

    public void setPageCurrent(int value) throws Exception {
        if (_pgs == null)
            return ;
         
        // Contributed by Henrique (h2a) 07/14/2006
        if (value <= _pgs.getPageCount() && value >= 1)
        {
            _vScroll.Value = (int)(_vScroll.Maximum / _pgs.getPageCount() * (value - 1));
            String tt = String.Format("Page {0} of {1}", (int)(_pgs.getPageCount() * (long)_vScroll.Value / (double)_vScroll.Maximum) + 1, _pgs.getPageCount());
            _vScrollToolTip.SetToolTip(_vScroll, tt);
            _DrawPanel.Invalidate();
        }
        else
            throw new ArgumentOutOfRangeException("PageCurrent", value, String.Format("Value must be between 1 and {0}.", _pgs.getPageCount())); 
    }

    /**
    * Gets the report definition.
    */
    public fyiReporting.RDL.Report getReport() throws Exception {
        loadPageIfNeeded();
        return _Report;
    }

    /**
    * Forces the report to get rebuilt especially after changing parameters or data.
    */
    public void rebuild() throws Exception {
        loadPageIfNeeded();
        if (_Report == null)
            throw new Exception("Report must be loaded prior to Rebuild being called.");
         
        _pgs = getPages(this._Report);
        _DrawPanel.setPgs(_pgs);
        _vScroll.Value = 0;
        calcZoom();
        _DrawPanel.Invalidate();
    }

    /**
    * Gets/Sets the ScrollMode.
    * SinglePage: Shows a single page shows in pane.
    * Continuous: Shows pages as a continuous vertical column.
    * Facing: Shows first page on right side of pane, then alternating
    * with single page scrolling.
    * ContinuousFacing: Shows 1st page on right side of pane, then alternating
    * with continuous scrolling.
    */
    public fyiReporting.RdlViewer.ScrollModeEnum getScrollMode() throws Exception {
        return _ScrollMode;
    }

    public void setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum value) throws Exception {
        _ScrollMode = value;
        calcZoom();
        this._DrawPanel.Invalidate();
    }

    /**
    * Holds a file name that contains the RDL (Report Specification Language).  Setting
    * this field will cause a new report to be loaded into the viewer.
    * SourceFile is mutually exclusive with SourceRdl.  Setting SourceFile will nullify SourceRdl.
    */
    public String getSourceFile() throws Exception {
        return _SourceFileName;
    }

    public void setSourceFile(String value) throws Exception {
        _SourceFileName = value;
        _SourceRdl = null;
        _vScroll.Value = _hScroll.Value = 0;
        _pgs = null;
        // reset pages, only if SourceRdl is also unavailable
        _DrawPanel.setPgs(null);
        _loadFailed = false;
        // attempt to load the report
        if (this.Visible)
        {
            loadPageIfNeeded();
            // force load of report
            this._DrawPanel.Invalidate();
        }
         
    }

    /**
    * Holds the XML source of the report in RDL (Report Specification Language).
    * SourceRdl is mutually exclusive with SourceFile.  Setting SourceRdl will nullify SourceFile.
    */
    public String getSourceRdl() throws Exception {
        return _SourceRdl;
    }

    public void setSourceRdl(String value) throws Exception {
        _SourceRdl = value;
        _SourceFileName = null;
        _pgs = null;
        // reset pages
        _DrawPanel.setPgs(null);
        _loadFailed = false;
        // attempt to load the report
        _vScroll.Value = _hScroll.Value = 0;
        if (this.Visible)
        {
            loadPageIfNeeded();
            // force load of report
            this._DrawPanel.Invalidate();
        }
         
    }

    /**
    * Holds the folder to data source reference files when SourceFileName not available.
    */
    public String getFolder() throws Exception {
        return _Folder;
    }

    public void setFolder(String value) throws Exception {
        _Folder = value;
    }

    /// <summary>
    /// Parameters passed to report when run.  Parameters are separated by '&'.  For example,
    /// OrderID=10023&OrderDate=10/14/2002
    /// Note: these parameters will override the user specified ones.
    /// </summary>
    public String getParameters() throws Exception {
        return _Parameters;
    }

    public void setParameters(String value) throws Exception {
        _Parameters = value;
    }

    /**
    * The height of the report page (in points) as defined within the report.
    */
    public float getPageHeight() throws Exception {
        loadPageIfNeeded();
        return _PageHeight;
    }

    /**
    * The width of the report page (in points) as defined within the report.
    */
    public float getPageWidth() throws Exception {
        loadPageIfNeeded();
        return _PageWidth;
    }

    /**
    * Description of the report.
    */
    public String getReportDescription() throws Exception {
        loadPageIfNeeded();
        return _ReportDescription;
    }

    /**
    * Author of the report.
    */
    public String getReportAuthor() throws Exception {
        loadPageIfNeeded();
        return _ReportAuthor;
    }

    /**
    * Name of the report.
    */
    public String getReportName() throws Exception {
        return _ReportName;
    }

    public void setReportName(String value) throws Exception {
        _ReportName = value;
    }

    /**
    * Zoom factor.  For example, .5 is a 50% reduction, 2 is 200% increase.
    * Setting this value will force ZoomMode to UseZoom.
    */
    public float getZoom() throws Exception {
        return _zoom;
    }

    public void setZoom(float value) throws Exception {
        _zoom = value;
        this._zoomMode = fyiReporting.RdlViewer.ZoomEnum.UseZoom;
        calcZoom();
        // this adjust any scrolling issues
        this._DrawPanel.Invalidate();
    }

    /**
    * ZoomMode.  Optionally, allows zoom to dynamically change depending on pane size.
    */
    public fyiReporting.RdlViewer.ZoomEnum getZoomMode() throws Exception {
        return _zoomMode;
    }

    public void setZoomMode(fyiReporting.RdlViewer.ZoomEnum value) throws Exception {
        _zoomMode = value;
        calcZoom();
        // force zoom calculation
        this._DrawPanel.Invalidate();
    }

    /**
    * Print the report.
    */
    public void print(PrintDocument pd) throws Exception {
        loadPageIfNeeded();
        pd.PrintPage += new PrintPageEventHandler(PrintPage);
        printCurrentPage = -1;
        PrintRange __dummyScrutVar0 = pd.PrinterSettings.PrintRange;
        if (__dummyScrutVar0.equals(PrintRange.AllPages))
        {
            printCurrentPage = 0;
            printEndPage = _pgs.getPageCount() - 1;
        }
        else if (__dummyScrutVar0.equals(PrintRange.Selection))
        {
            printCurrentPage = pd.PrinterSettings.FromPage - 1;
            printEndPage = pd.PrinterSettings.FromPage - 1;
        }
        else if (__dummyScrutVar0.equals(PrintRange.SomePages))
        {
            printCurrentPage = pd.PrinterSettings.FromPage - 1;
            if (printCurrentPage < 0)
                printCurrentPage = 0;
             
            printEndPage = pd.PrinterSettings.ToPage - 1;
            if (printEndPage >= _pgs.getPageCount())
                printEndPage = _pgs.getPageCount() - 1;
             
        }
           
        pd.Print();
    }

    private void printPage(Object sender, PrintPageEventArgs e) throws Exception {
        System.Drawing.Rectangle r = new System.Drawing.Rectangle(0, 0, int.MaxValue, int.MaxValue);
        _DrawPanel.draw(e.Graphics,printCurrentPage,r,false);
        printCurrentPage++;
        if (printCurrentPage > printEndPage)
            e.HasMorePages = false;
        else
            e.HasMorePages = true; 
    }

    /**
    * Save the file.  The extension determines the type of file to save.
    * 
    *  @param FileName Name of the file to be saved to.
    *  @param ext Type of file to save.  Should be "pdf", "xml", "html", "mhtml".
    */
    public void saveAs(String FileName, String type) throws Exception {
        loadPageIfNeeded();
        String ext = type.ToLower();
        OneFileStreamGen sg = new OneFileStreamGen(FileName,true);
        try
        {
            // overwrite with this name
            System.String __dummyScrutVar1 = ext;
            if (__dummyScrutVar1.equals("pdf"))
            {
                _Report.runRenderPdf(sg,_pgs);
            }
            else if (__dummyScrutVar1.equals("xml"))
            {
                _Report.runRender(sg,OutputPresentationType.XML);
            }
            else if (__dummyScrutVar1.equals("html") || __dummyScrutVar1.equals("htm"))
            {
                _Report.runRender(sg,OutputPresentationType.HTML);
            }
            else if (__dummyScrutVar1.equals("mhtml") || __dummyScrutVar1.equals("mht"))
            {
                _Report.runRender(sg,OutputPresentationType.MHTML);
            }
            else
            {
                throw new Exception("Unsupported file extension for SaveAs");
            }    
        }
        finally
        {
            if (sg != null)
            {
                sg.closeMainStream();
            }
             
        }
        return ;
    }

    private void drawPanelPaint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        synchronized (this)
        {
            {
                // Only handle one paint at a time
                if (_InPaint)
                    return ;
                 
                _InPaint = true;
            }
        }
        Graphics g = e.Graphics;
        try
        {
            // never want to die in here
            if (!_InLoading)
            {
                // If we're in the process of loading don't paint
                loadPageIfNeeded();
                // make sure we have something to show
                if (_zoom < 0)
                    calcZoom();
                 
                // new report or resize client requires new zoom factor
                // Draw the page
                _DrawPanel.Draw(g, _zoom, _leftMargin, _pageGap, PointsX(_hScroll.Value), PointsY(_vScroll.Value), e.ClipRectangle);
            }
             
        }
        catch (Exception ex)
        {
            // don't want to kill process if we die
            Font font = new Font("Arial", 8);
            try
            {
                g.DrawString(ex.Message + "\r\n" + ex.StackTrace, font, Brushes.Black, 0, 0);
            }
            finally
            {
                if (font != null)
                    Disposable.mkDisposable(font).dispose();
                 
            }
        }

        synchronized (this)
        {
            {
                _InPaint = false;
            }
        }
    }

    private void drawPanelResize(Object sender, EventArgs e) throws Exception {
        calcZoom();
        // calc zoom
        _DrawPanel.Refresh();
    }

    private float pointsX(float x) throws Exception {
        return x * 72f / DpiX;
    }

    // pixels to points
    private float pointsY(float y) throws Exception {
        return y * 72f / DpiY;
    }

    private float POINTSIZEF = 72.27f;
    private int pixelsX(float x) throws Exception {
        return (int)(x * DpiX / POINTSIZEF);
    }

    // points to pixels
    private int pixelsY(float y) throws Exception {
        return (int)(y * DpiY / POINTSIZEF);
    }

    private void calcZoom() throws Exception {
        switch(_zoomMode)
        {
            case UseZoom: 
                if (_zoom <= 0)
                    // normalize invalid values
                    _zoom = 1;
                 
                break;
            case FitWidth: 
                // nothing to calculate
                calcZoomFitWidth();
                break;
            case FitPage: 
                calcZoomFitPage();
                break;
        
        }
        if (_zoom <= 0)
            _zoom = 1;
         
        float w = PointsX(_DrawPanel.Width);
        // convert to points
        if (w > (this._PageWidth + _leftGap + _rightGap) * _zoom)
            _leftMargin = ((w - (this._PageWidth + _leftGap + _rightGap) * _zoom) / 2) / _zoom;
        else
            _leftMargin = _leftGap; 
        if (_leftMargin < 0)
            _leftMargin = 0;
         
        setScrollControls();
        return ;
    }

    // zoom affects the scroll bars
    private void calcZoomFitPage() throws Exception {
        try
        {
            float w = PointsX(_DrawPanel.Width);
            // convert to points
            float h = PointsY(_DrawPanel.Height);
            float xratio = w / (this._PageWidth + _leftGap + _rightGap);
            float yratio = h / (this._PageHeight + this._pageGap + this._pageGap);
            _zoom = Math.Min(xratio, yratio);
        }
        catch (Exception __dummyCatchVar1)
        {
            _zoom = 1;
        }
    
    }

    // shouldn't ever happen but this routine must never throw exception
    private void calcZoomFitWidth() throws Exception {
        try
        {
            float w = PointsX(_DrawPanel.Width);
            // convert to points
            float h = PointsY(_DrawPanel.Height);
            _zoom = w / (this._PageWidth + _leftGap + _rightGap);
        }
        catch (Exception __dummyCatchVar2)
        {
            _zoom = 1;
        }
    
    }

    // shouldn't ever happen but this routine must never throw exception
    // Obtain the Pages by running the report
    private fyiReporting.RDL.Report getReport() throws Exception {
        String prog = new String();
        // Obtain the source
        if (_loadFailed)
            prog = getReportErrorMsg();
        else if (_SourceRdl != null)
            prog = _SourceRdl;
        else if (_SourceFileName != null)
            prog = getRdlSource();
        else
            prog = getReportEmptyMsg();   
        // Compile the report
        // Now parse the file
        RDLParser rdlp;
        fyiReporting.RDL.Report r;
        try
        {
            _errorMsgs = null;
            rdlp = new RDLParser(prog);
            rdlp.setDataSourceReferencePassword(GetDataSourceReferencePassword);
            if (_SourceFileName != null)
                rdlp.setFolder(Path.GetDirectoryName(_SourceFileName));
            else
                rdlp.setFolder(this.getFolder()); 
            r = rdlp.parse();
            if (r.getErrorMaxSeverity() > 0)
            {
                _errorMsgs = r.getErrorItems();
                // keep a copy of the errors
                int severity = r.getErrorMaxSeverity();
                r.errorReset();
                if (severity > 4)
                {
                    r = null;
                    // don't return when severe errors
                    _loadFailed = true;
                }
                 
            }
             
            // If we've loaded the report; we should tell it where it got loaded from
            if (r != null && !_loadFailed)
            {
                try
                {
                    // Don't care much if this fails; and don't want to null out report if it does
                    if (_SourceFileName != null)
                    {
                        r.setName(Path.GetFileNameWithoutExtension(_SourceFileName));
                        r.setFolder(Path.GetDirectoryName(_SourceFileName));
                    }
                    else
                    {
                        r.setFolder(this.getFolder());
                        r.setName(this.getReportName());
                    } 
                }
                catch (Exception __dummyCatchVar3)
                {
                }
            
            }
             
        }
        catch (Exception ex)
        {
            _loadFailed = true;
            _errorMsgs = new List<String>();
            // create new error list
            _errorMsgs.Add(ex.Message);
            // put the message in it
            _errorMsgs.Add(ex.StackTrace);
            //   and the stack trace
            r = null;
        }

        if (r != null)
        {
            _PageWidth = r.getPageWidthPoints();
            _PageHeight = r.getPageHeightPoints();
            _ReportDescription = r.getDescription();
            _ReportAuthor = r.getAuthor();
            parametersBuild(r);
        }
        else
        {
            _PageWidth = 0;
            _PageHeight = 0;
            _ReportDescription = null;
            _ReportAuthor = null;
            _ReportName = null;
        } 
        return r;
    }

    private String getReportEmptyMsg() throws Exception {
        String prog = "<Report><Width>8.5in</Width><Body><Height>1in</Height><ReportItems><Textbox><Value></Value><Style><FontWeight>Bold</FontWeight></Style><Height>.3in</Height><Width>5 in</Width></Textbox></ReportItems></Body></Report>";
        return prog;
    }

    private String getReportErrorMsg() throws Exception {
        String data1 = "<?xml version=\'1.0\' encoding=\'UTF-8\'?>\r\n" + 
        "<Report> \r\n" + 
        "\t<LeftMargin>.4in</LeftMargin><Width>8.5in</Width>\r\n" + 
        "\t<Author></Author>\r\n" + 
        "\t<DataSources>\r\n" + 
        "\t\t<DataSource Name=\'DS1\'>\r\n" + 
        "\t\t\t<ConnectionProperties> \r\n" + 
        "\t\t\t\t<DataProvider>xxx</DataProvider>\r\n" + 
        "\t\t\t\t<ConnectString></ConnectString>\r\n" + 
        "\t\t\t</ConnectionProperties>\r\n" + 
        "\t\t</DataSource>\r\n" + 
        "\t</DataSources>\r\n" + 
        "\t<DataSets>\r\n" + 
        "\t\t<DataSet Name=\'Data\'>\r\n" + 
        "\t\t\t<Query>\r\n" + 
        "\t\t\t\t<DataSourceName>DS1</DataSourceName>\r\n" + 
        "\t\t\t</Query>\r\n" + 
        "\t\t\t<Fields>\r\n" + 
        "\t\t\t\t<Field Name=\'Error\'> \r\n" + 
        "\t\t\t\t\t<DataField>Error</DataField>\r\n" + 
        "\t\t\t\t\t<TypeName>String</TypeName>\r\n" + 
        "\t\t\t\t</Field>\r\n" + 
        "\t\t\t</Fields>";
        String data2 = "\r\n" + 
        "\t\t</DataSet>\r\n" + 
        "\t</DataSets>\r\n" + 
        "\t<PageHeader>\r\n" + 
        "\t\t<Height>1 in</Height>\r\n" + 
        "\t\t<ReportItems>\r\n" + 
        "\t\t\t<Textbox><Top>.1in</Top><Value>fyiReporting Software, LLC</Value><Style><FontSize>18pt</FontSize><FontWeight>Bold</FontWeight></Style></Textbox>\r\n" + 
        "\t\t\t<Textbox><Top>.1in</Top><Left>4.25in</Left><Value>=Globals!ExecutionTime</Value><Style><Format>dddd, MMMM dd, yyyy hh:mm:ss tt</Format><FontSize>12pt</FontSize><FontWeight>Bold</FontWeight></Style></Textbox>\r\n" + 
        "\t\t\t<Textbox><Top>.5in</Top><Value>Errors processing report</Value><Style><FontSize>12pt</FontSize><FontWeight>Bold</FontWeight></Style></Textbox>\r\n" + 
        "\t\t</ReportItems>\r\n" + 
        "\t</PageHeader>\r\n" + 
        "\t<Body><Height>3 in</Height>\r\n" + 
        "\t\t<ReportItems>\r\n" + 
        "\t\t\t<Table>\r\n" + 
        "\t\t\t\t<Style><BorderStyle>Solid</BorderStyle></Style>\r\n" + 
        "\t\t\t\t<TableColumns>\r\n" + 
        "\t\t\t\t\t<TableColumn><Width>7 in</Width></TableColumn>\r\n" + 
        "\t\t\t\t</TableColumns>\r\n" + 
        "\t\t\t\t<Header>\r\n" + 
        "\t\t\t\t\t<TableRows>\r\n" + 
        "\t\t\t\t\t\t<TableRow>\r\n" + 
        "\t\t\t\t\t\t\t<Height>15 pt</Height>\r\n" + 
        "\t\t\t\t\t\t\t<TableCells>\r\n" + 
        "\t\t\t\t\t\t\t\t<TableCell>\r\n" + 
        "\t\t\t\t\t\t\t\t\t<ReportItems><Textbox><Value>Messages</Value><Style><FontWeight>Bold</FontWeight></Style></Textbox></ReportItems>\r\n" + 
        "\t\t\t\t\t\t\t\t</TableCell>\r\n" + 
        "\t\t\t\t\t\t\t</TableCells>\r\n" + 
        "\t\t\t\t\t\t</TableRow>\r\n" + 
        "\t\t\t\t\t</TableRows>\r\n" + 
        "\t\t\t\t\t<RepeatOnNewPage>true</RepeatOnNewPage>\r\n" + 
        "\t\t\t\t</Header>\r\n" + 
        "\t\t\t\t<Details>\r\n" + 
        "\t\t\t\t\t<TableRows>\r\n" + 
        "\t\t\t\t\t\t<TableRow>\r\n" + 
        "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
        "\t\t\t\t\t\t\t<TableCells>\r\n" + 
        "\t\t\t\t\t\t\t\t<TableCell>\r\n" + 
        "\t\t\t\t\t\t\t\t\t<ReportItems><Textbox Name=\'ErrorMsg\'><Value>=Fields!Error.Value</Value><CanGrow>true</CanGrow></Textbox></ReportItems>\r\n" + 
        "\t\t\t\t\t\t\t\t</TableCell>\r\n" + 
        "\t\t\t\t\t\t\t</TableCells>\r\n" + 
        "\t\t\t\t\t\t</TableRow>\r\n" + 
        "\t\t\t\t\t</TableRows>\r\n" + 
        "\t\t\t\t</Details>\r\n" + 
        "\t\t\t</Table>\r\n" + 
        "\t\t</ReportItems>\r\n" + 
        "\t</Body>\r\n" + 
        "</Report>";
        StringBuilder sb = new StringBuilder(data1, data1.Length + data2.Length + 1000);
        // Build out the error messages
        sb.Append("<Rows>");
        for (Object __dummyForeachVar0 : _errorMsgs)
        {
            String msg = (String)__dummyForeachVar0;
            sb.Append("<Row><Error>");
            String newmsg = msg.Replace("&", "&amp;");
            newmsg = newmsg.Replace("<", "&lt;");
            sb.Append(newmsg);
            sb.Append("</Error></Row>");
        }
        sb.Append("</Rows>");
        sb.Append(data2);
        return sb.ToString();
    }

    private Pages getPages() throws Exception {
        this._Report = getReport();
        if (_loadFailed)
            // retry on failure; this will get error report
            this._Report = getReport();
         
        return getPages(this._Report);
    }

    private Pages getPages(fyiReporting.RDL.Report report) throws Exception {
        Pages pgs = null;
        ListDictionary ld = getParameters();
        try
        {
            // split parms into dictionary
            report.RunGetData(ld);
            pgs = report.buildPages();
            if (report.getErrorMaxSeverity() > 0)
            {
                if (_errorMsgs == null)
                    _errorMsgs = report.getErrorItems();
                else
                {
                    for (Object __dummyForeachVar1 : report.getErrorItems())
                    {
                        // keep a copy of the errors
                        String err = (String)__dummyForeachVar1;
                        _errorMsgs.Add(err);
                    }
                } 
                report.errorReset();
            }
             
        }
        catch (Exception e)
        {
            String msg = e.Message;
        }

        return pgs;
    }

    private ListDictionary getParameters() throws Exception {
        ListDictionary ld = new ListDictionary();
        if (_Parameters == null)
            return ld;
         
        // dictionary will be empty in this case
        // parms are separated by &
        char[] breakChars = new char[]{ '&' };
        String[] ps = _Parameters.Split(breakChars);
        for (Object __dummyForeachVar2 : ps)
        {
            String p = (String)__dummyForeachVar2;
            int iEq = p.IndexOf("=");
            if (iEq > 0)
            {
                String name = p.Substring(0, iEq);
                String val = p.Substring(iEq + 1);
                ld.Add(name, val);
            }
             
        }
        return ld;
    }

    private String getRdlSource() throws Exception {
        StreamReader fs = null;
        String prog = null;
        try
        {
            fs = new StreamReader(_SourceFileName);
            prog = fs.ReadToEnd();
        }
        finally
        {
            if (fs != null)
                fs.Close();
             
        }
        return prog;
    }

    /**
    * Call LoadPageIfNeeded when a routine requires the report to be loaded in order
    * to fulfill the request.
    */
    private void loadPageIfNeeded() throws Exception {
        if (_pgs == null)
        {
            Cursor savec = null;
            try
            {
                _InLoading = true;
                savec = this.Cursor;
                // this could take a while so put up wait cursor
                this.Cursor = Cursors.WaitCursor;
                _pgs = getPages();
                _DrawPanel.setPgs(_pgs);
                calcZoom();
            }
            finally
            {
                // this could affect zoom
                _InLoading = false;
                if (savec != null)
                    this.Cursor = savec;
                 
            }
            rdlViewer_Layout(this,null);
        }
         
    }

    // re layout based on new report
    private void parametersBuild(fyiReporting.RDL.Report r) throws Exception {
        // Remove all previous controls
        _ParameterPanel.Controls.Clear();
        _ParameterPanel.AutoScroll = true;
        int yPos = 10;
        for (Object __dummyForeachVar4 : r.getUserReportParameters())
        {
            UserReportParameter rp = (UserReportParameter)__dummyForeachVar4;
            if (rp.getPrompt() == null)
                continue;
             
            // skip parameters that don't have a prompt
            // Create a label
            Label label = new Label();
            label.Parent = _ParameterPanel;
            label.AutoSize = true;
            label.Text = rp.getPrompt();
            label.Location = new Point(10, yPos);
            // Create a control
            Control v = new Control();
            int width = 90;
            if (rp.getDisplayValues() == null)
            {
                TextBox tb = new TextBox();
                v = tb;
                tb.Height = tb.PreferredHeight;
                tb.Validated += new System.EventHandler(ParametersTextValidated);
            }
            else
            {
                ComboBox cb = new ComboBox();
                // create a label to auto
                Label l = new Label();
                l.AutoSize = true;
                l.Visible = false;
                cb.Leave += new EventHandler(ParametersLeave);
                v = cb;
                width = 0;
                for (Object __dummyForeachVar3 : rp.getDisplayValues())
                {
                    String s = (String)__dummyForeachVar3;
                    l.Text = s;
                    if (width < l.Width)
                        width = l.Width;
                     
                    cb.Items.Add(s);
                }
                if (width > 0)
                {
                    l.Text = "XX";
                    width += l.Width;
                }
                else
                    // give some extra room for the drop down arrow
                    width = 90; 
            } 
            // just force the default
            v.Parent = _ParameterPanel;
            v.Width = width;
            v.Location = new Point(label.Location.X + label.Width + 5, yPos);
            if (rp.getDefaultValue() != null)
            {
                StringBuilder sb = new StringBuilder();
                for (int i = 0;i < rp.getDefaultValue().Length;i++)
                {
                    if (i > 0)
                        sb.Append(", ");
                     
                    sb.Append(rp.getDefaultValue()[i].ToString());
                }
                v.Text = sb.ToString();
            }
             
            v.Tag = rp;
            v.Validated += new System.EventHandler(ParametersTextValidated);
            yPos += Math.Max(label.Height, v.Height) + 5;
        }
        this._ParametersMaxHeight = yPos;
    }

    private void parametersLeave(Object sender, EventArgs e) throws Exception {
        ComboBox cb = sender instanceof ComboBox ? (ComboBox)sender : (ComboBox)null;
        if (cb == null)
            return ;
         
        UserReportParameter rp = cb.Tag instanceof UserReportParameter ? (UserReportParameter)cb.Tag : (UserReportParameter)null;
        if (rp == null)
            return ;
         
        try
        {
            rp.setValue(cb.Text);
        }
        catch (ArgumentException ae)
        {
            MessageBox.Show(ae.Message, "Invalid Report Parameter");
        }
    
    }

    private void parametersTextValidated(Object sender, System.EventArgs e) throws Exception {
        TextBox tb = sender instanceof TextBox ? (TextBox)sender : (TextBox)null;
        if (tb == null)
            return ;
         
        UserReportParameter rp = tb.Tag instanceof UserReportParameter ? (UserReportParameter)tb.Tag : (UserReportParameter)null;
        if (rp == null)
            return ;
         
        try
        {
            rp.setValue(tb.Text);
        }
        catch (ArgumentException ae)
        {
            MessageBox.Show(ae.Message, "Invalid Report Parameter");
        }
    
    }

    private void parametersViewClick(Object sender, System.EventArgs e) throws Exception {
        _errorMsgs = null;
        // reset the error message
        if (this._Report == null)
            return ;
         
        boolean bFail = false;
        for (Object __dummyForeachVar5 : _Report.getUserReportParameters())
        {
            UserReportParameter rp = (UserReportParameter)__dummyForeachVar5;
            if (rp.getPrompt() == null)
                continue;
             
            if (rp.getValue() == null && !rp.getNullable())
            {
                MessageBox.Show(String.Format("Parameter '{0}' is required but not provided.", rp.getPrompt()), "Report Parameter Missing");
                bFail = true;
            }
             
        }
        if (bFail)
            return ;
         
        _pgs = getPages(this._Report);
        _DrawPanel.setPgs(_pgs);
        _vScroll.Value = 0;
        calcZoom();
        _WarningButton.Visible = warningVisible();
        _DrawPanel.Invalidate();
    }

    private void warningClick(Object sender, System.EventArgs e) throws Exception {
        if (_errorMsgs == null)
            return ;
         
        // shouldn't even be visible if no warnings
        DialogMessages dm = new DialogMessages(_errorMsgs);
        dm.ShowDialog();
        return ;
    }

    private void setScrollControls() throws Exception {
        if (_pgs == null)
        {
            // nothing loaded; nothing to do
            _vScroll.Enabled = _hScroll.Enabled = false;
            _vScroll.Value = _hScroll.Value = 0;
            return ;
        }
         
        setScrollControlsV();
        setScrollControlsH();
    }

    private void setScrollControlsV() throws Exception {
        // calculate the vertical scroll needed
        float h = PointsY(_DrawPanel.Height);
        // height of pane
        if (_zoom * ((this._PageHeight + this._pageGap) * _pgs.getPageCount() + this._pageGap) <= h)
        {
            _vScroll.Enabled = false;
            _vScroll.Value = 0;
            return ;
        }
         
        _vScroll.Minimum = 0;
        _vScroll.Maximum = (int)(pixelsY((this._PageHeight + this._pageGap) * _pgs.getPageCount() + this._pageGap));
        _vScroll.Value = Math.Min(_vScroll.Value, _vScroll.Maximum);
        if (this._zoomMode == fyiReporting.RdlViewer.ZoomEnum.FitPage)
        {
            _vScroll.LargeChange = (int)(_vScroll.Maximum / _pgs.getPageCount());
            _vScroll.SmallChange = _vScroll.LargeChange;
        }
        else
        {
            _vScroll.LargeChange = (int)(_DrawPanel.Height / _zoom);
            _vScroll.SmallChange = _vScroll.LargeChange / 5;
        } 
        _vScroll.Enabled = true;
        String tt = String.Format("Page {0} of {1}", (int)(_pgs.getPageCount() * (long)_vScroll.Value / (double)_vScroll.Maximum) + 1, _pgs.getPageCount());
        _vScrollToolTip.SetToolTip(_vScroll, tt);
        return ;
    }

    //			switch (_ScrollMode)
    //			{
    //				case ScrollModeEnum.SinglePage:
    //					break;
    //				case ScrollModeEnum.Continuous:
    //				case ScrollModeEnum.ContinuousFacing:
    //				case ScrollModeEnum.Facing:
    //					break;
    //			}
    private void setScrollControlsH() throws Exception {
        // calculate the horizontal scroll needed
        float w = PointsX(_DrawPanel.Width);
        // width of pane
        if (_zoomMode == fyiReporting.RdlViewer.ZoomEnum.FitPage || _zoomMode == fyiReporting.RdlViewer.ZoomEnum.FitWidth || _zoom * (this._PageWidth + this._leftGap + this._rightGap) <= w)
        {
            _hScroll.Enabled = false;
            _hScroll.Value = 0;
            return ;
        }
         
        _hScroll.Minimum = 0;
        _hScroll.Maximum = (int)(pixelsX(this._PageWidth + this._leftGap + this._rightGap));
        _hScroll.Value = Math.Min(_hScroll.Value, _hScroll.Maximum);
        _hScroll.LargeChange = (int)(_DrawPanel.Width / _zoom);
        _hScroll.SmallChange = _hScroll.LargeChange / 5;
        _hScroll.Enabled = true;
        return ;
    }

    private void horizontalScroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (e.NewValue == _hScroll.Value)
            return ;
         
        // don't need to scroll if already there
        _DrawPanel.Invalidate();
    }

    private void verticalScroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (e.NewValue == _vScroll.Value)
            return ;
         
        // don't need to scroll if already there
        String tt = String.Format("Page {0} of {1}", (int)(_pgs.getPageCount() * (long)_vScroll.Value / (double)_vScroll.Maximum) + 1, _pgs.getPageCount());
        _vScrollToolTip.SetToolTip(_vScroll, tt);
        _DrawPanel.Invalidate();
    }

    private void drawPanelMouseWheel(Object sender, MouseEventArgs e) throws Exception {
        int wvalue = new int();
        if (e.Delta < 0)
        {
            if (_vScroll.Value < _vScroll.Maximum)
            {
                wvalue = _vScroll.Value + _vScroll.SmallChange;
                _vScroll.Value = Math.Min(_vScroll.Maximum - _DrawPanel.Height, wvalue);
                _DrawPanel.Refresh();
            }
             
        }
        else
        {
            if (_vScroll.Value > _vScroll.Minimum)
            {
                wvalue = _vScroll.Value - _vScroll.SmallChange;
                _vScroll.Value = Math.Max(_vScroll.Minimum, wvalue);
                _DrawPanel.Refresh();
            }
             
        } 
    }

    private void drawPanelKeyDown(Object sender, KeyEventArgs e) throws Exception {
        // Force scroll up and down
        if (e.KeyCode == Keys.Down)
        {
            _vScroll.Value = Math.Min(_vScroll.Value + _vScroll.SmallChange, _vScroll.Maximum);
            _DrawPanel.Refresh();
            e.Handled = true;
        }
        else if (e.KeyCode == Keys.Up)
        {
            _vScroll.Value = Math.Max(_vScroll.Value - _vScroll.SmallChange, 0);
            _DrawPanel.Refresh();
            e.Handled = true;
        }
        else if (e.KeyCode == Keys.PageDown)
        {
            _vScroll.Value = Math.Min(_vScroll.Value + _vScroll.LargeChange, _vScroll.Maximum);
            _DrawPanel.Refresh();
            e.Handled = true;
        }
        else if (e.KeyCode == Keys.PageUp)
        {
            _vScroll.Value = Math.Max(_vScroll.Value - _vScroll.LargeChange, 0);
            _DrawPanel.Refresh();
            e.Handled = true;
        }
            
    }

    private boolean warningVisible() throws Exception {
        if (!_ShowParameters)
            return false;
         
        return _errorMsgs != null;
    }

    private void rdlViewer_Layout(Object sender, LayoutEventArgs e) throws Exception {
        int pHeight = new int();
        if (_ShowParameters)
        {
            // Only the parameter panel is visible
            _ParameterPanel.Visible = true;
            _RunButton.Visible = true;
            _WarningButton.Visible = warningVisible();
            _ParameterPanel.Location = new Point(0, 0);
            _ParameterPanel.Width = this.Width - _RunButton.Width - _WarningButton.Width - 5;
            pHeight = this.Height / 3;
            if (pHeight > _ParametersMaxHeight)
                pHeight = _ParametersMaxHeight;
             
            if (pHeight < _RunButton.Height + 15)
                pHeight = _RunButton.Height + 15;
             
            _ParameterPanel.Height = pHeight;
        }
        else
        {
            //				pHeight=_RunButton.Height + 15;
            pHeight = 0;
            _RunButton.Visible = false;
            _WarningButton.Visible = false;
            _ParameterPanel.Visible = false;
        } 
        _DrawPanel.Location = new Point(0, pHeight);
        _DrawPanel.Width = this.Width - _vScroll.Width;
        _DrawPanel.Height = this.Height - _hScroll.Height - pHeight;
        _hScroll.Location = new Point(0, this.Height - _hScroll.Height);
        _hScroll.Width = _DrawPanel.Width;
        _vScroll.Location = new Point(this.Width - _vScroll.Width, _DrawPanel.Location.Y);
        _vScroll.Height = _DrawPanel.Height;
        _RunButton.Location = new Point(this.Width - _RunButton.Width - 2 - _WarningButton.Width, 10);
        _WarningButton.Location = new Point(_RunButton.Location.X + _RunButton.Width + 2, 13);
    }

    private void _WarningButton_Paint(Object sender, PaintEventArgs e) throws Exception {
        int midPoint = _WarningButton.Width / 2;
        Graphics g = e.Graphics;
        Point[] triangle = new Point[5];
        triangle[0] = triangle[4] = new Point(midPoint - 1, 0);
        triangle[1] = new Point(0, _WarningButton.Height - 1);
        triangle[2] = new Point(_WarningButton.Width, _WarningButton.Height - 1);
        triangle[3] = new Point(midPoint + 1, 0);
        g.FillPolygon(Brushes.Yellow, triangle);
        g.DrawPolygon(Pens.Black, triangle);
        g.FillRectangle(Brushes.Red, midPoint - 1, 5, 2, 5);
        g.FillRectangle(Brushes.Red, midPoint - 1, 11, 2, 2);
    }

}


