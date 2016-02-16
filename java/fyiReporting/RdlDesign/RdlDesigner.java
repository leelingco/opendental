//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:21 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.Image;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RdlDesign.DesignCtl;
import fyiReporting.RdlDesign.DesignCtl.__MultiHeightEventHandler;
import fyiReporting.RdlDesign.DesignCtl.__MultiOpenSubreportEventHandler;
import fyiReporting.RdlDesign.DesignerUtility;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DesktopConfig;
import fyiReporting.RdlDesign.DialogAbout;
import fyiReporting.RdlDesign.DialogDatabase;
import fyiReporting.RdlDesign.DialogDataSourceRef;
import fyiReporting.RdlDesign.DialogDataSources;
import fyiReporting.RdlDesign.DialogEmbeddedImages;
import fyiReporting.RdlDesign.DialogToolOptions;
import fyiReporting.RdlDesign.DialogValidateRdl;
import fyiReporting.RdlDesign.FindTab;
import fyiReporting.RdlDesign.HeightEventArgs;
import fyiReporting.RdlDesign.MDIChild;
import fyiReporting.RdlDesign.MDIChild.__MultiRdlChangeHandler;
import fyiReporting.RdlDesign.PropertyDialog;
import fyiReporting.RdlDesign.PropertyTypeEnum;
import fyiReporting.RdlDesign.RdlDesigner;
import fyiReporting.RdlDesign.SimpleButton;
import fyiReporting.RdlDesign.SimpleToggle;
import fyiReporting.RdlDesign.StaticLists;
import fyiReporting.RdlDesign.SubReportEventArgs;
import fyiReporting.RdlViewer.DataSourcePassword;
import java.util.ArrayList;
import java.util.List;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* RdlDesigner is used for building and previewing RDL based reports.
*/
public class RdlDesigner  extends System.Windows.Forms.Form 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private MDIChild printChild = null;
    SortedList<DateTime, String> _RecentFiles = null;
    List<String> _CurrentFiles = null;
    // temporary variable for current files
    List<String> _Toolbar = null;
    // temporary variable for toolbar entries
    List<String> _TempReportFiles = null;
    // temporary files created for report browsing
    int _RecentFilesMax = 5;
    // # of items in recent files
    Process _ServerProcess = null;
    // process for the RdlDesktop.exe --
    private NeedPassword _GetPassword;
    private String _DataSourceReferencePassword = null;
    private boolean bGotPassword = false;
    private boolean bMono = false;
    private final String DefaultHelpUrl = "http://www.fyireporting.com/helpv2/index.html";
    private final String DefaultSupportUrl = "http://www.fyireporting.com/forum";
    private String _HelpUrl = new String();
    private String _SupportUrl = new String();
    private DialogValidateRdl _ValidateRdl = null;
    private boolean _ShowEditLines = true;
    private boolean _ShowReportItemOutline = false;
    private boolean _ShowTabbedInterface = true;
    private final String TEMPRDL = "_tempfile_.rdl";
    private int TEMPRDL_INC = 0;
    // Status bar
    private System.Windows.Forms.StatusBar mainSB = new System.Windows.Forms.StatusBar();
    private StatusBarPanel statusPrimary = new StatusBarPanel();
    private StatusBarPanel statusSelected = new StatusBarPanel();
    private StatusBarPanel statusPosition = new StatusBarPanel();
    // Tool bar  --- if you add to this list LOOK AT INIT TOOLBAR FIRST
    boolean bSuppressChange = false;
    private System.Windows.Forms.ToolBar mainTB = new System.Windows.Forms.ToolBar();
    public System.Windows.Forms.TabControl mainTC = new System.Windows.Forms.TabControl();
    private SimpleToggle ctlBold = null;
    private SimpleToggle ctlItalic = null;
    private SimpleToggle ctlUnderline = null;
    private ComboBox ctlFont = null;
    private ComboBox ctlFontSize = null;
    private ComboBox ctlForeColor = null;
    private ComboBox ctlBackColor = null;
    private Button ctlNew = null;
    private Button ctlOpen = null;
    private Button ctlSave = null;
    private Button ctlCut = null;
    private Button ctlCopy = null;
    private Button ctlUndo = null;
    private Button ctlPaste = null;
    private Button ctlPrint = null;
    private Button ctlPdf = null;
    private Button ctlXml = null;
    private Button ctlHtml = null;
    private Button ctlMht = null;
    private ComboBox ctlZoom = null;
    private SimpleToggle ctlInsertCurrent = null;
    private SimpleToggle ctlInsertTextbox = null;
    private SimpleToggle ctlInsertChart = null;
    private SimpleToggle ctlInsertRectangle = null;
    private SimpleToggle ctlInsertTable = null;
    private SimpleToggle ctlInsertMatrix = null;
    private SimpleToggle ctlInsertList = null;
    private SimpleToggle ctlInsertLine = null;
    private SimpleToggle ctlInsertImage = null;
    private SimpleToggle ctlInsertSubreport = null;
    // Edit items
    private System.Windows.Forms.TextBox ctlEditTextbox = null;
    // when you're editting textbox's
    private System.Windows.Forms.Label ctlEditLabel = null;
    // Menu Items
    MenuItem menuFSep1 = new MenuItem();
    MenuItem menuFSep2 = new MenuItem();
    MenuItem menuFSep3 = new MenuItem();
    MenuItem menuFSep4 = new MenuItem();
    // File
    MenuItem menuNew = new MenuItem();
    MenuItem menuOpen = new MenuItem();
    MenuItem menuClose = new MenuItem();
    MenuItem menuSave = new MenuItem();
    MenuItem menuSaveAs = new MenuItem();
    MenuItem menuExport = new MenuItem();
    MenuItem menuExportXml = new MenuItem();
    MenuItem menuExportPdf = new MenuItem();
    MenuItem menuExportHtml = new MenuItem();
    MenuItem menuExportMHtml = new MenuItem();
    MenuItem menuPrint = new MenuItem();
    MenuItem menuRecentFile = new MenuItem();
    MenuItem menuExit = new MenuItem();
    // Edit
    MenuItem menuEdit = new MenuItem();
    MenuItem menuEditUndo = new MenuItem();
    MenuItem menuEditRedo = new MenuItem();
    MenuItem menuEditCut = new MenuItem();
    MenuItem menuEditCopy = new MenuItem();
    MenuItem menuEditPaste = new MenuItem();
    MenuItem menuEditDelete = new MenuItem();
    MenuItem menuEditSelectAll = new MenuItem();
    MenuItem menuEditFind = new MenuItem();
    MenuItem menuEditFindNext = new MenuItem();
    MenuItem menuEditReplace = new MenuItem();
    MenuItem menuEditGoto = new MenuItem();
    MenuItem menuEditFormatXml = new MenuItem();
    // View
    MenuItem menuView = new MenuItem();
    MenuItem menuViewDesigner = new MenuItem();
    MenuItem menuViewRDL = new MenuItem();
    MenuItem menuViewPreview = new MenuItem();
    MenuItem menuViewBrowser = new MenuItem();
    MenuItem menuViewProperties = new MenuItem();
    // Data
    MenuItem menuData = new MenuItem();
    MenuItem menuDataSources = new MenuItem();
    MenuItem menuDataSets = new MenuItem();
    MenuItem menuEmbeddedImages = new MenuItem();
    MenuItem menuNewDataSourceRef = new MenuItem();
    // Format
    MenuItem menuFormatAlign = new MenuItem();
    MenuItem menuFormatAlignL = new MenuItem();
    MenuItem menuFormatAlignC = new MenuItem();
    MenuItem menuFormatAlignR = new MenuItem();
    MenuItem menuFormatAlignT = new MenuItem();
    MenuItem menuFormatAlignM = new MenuItem();
    MenuItem menuFormatAlignB = new MenuItem();
    MenuItem menuFormatSize = new MenuItem();
    MenuItem menuFormatSizeW = new MenuItem();
    MenuItem menuFormatSizeH = new MenuItem();
    MenuItem menuFormatSizeB = new MenuItem();
    MenuItem menuFormatHorz = new MenuItem();
    MenuItem menuFormatHorzE = new MenuItem();
    MenuItem menuFormatHorzI = new MenuItem();
    MenuItem menuFormatHorzD = new MenuItem();
    MenuItem menuFormatHorzZ = new MenuItem();
    MenuItem menuFormatVert = new MenuItem();
    MenuItem menuFormatVertE = new MenuItem();
    MenuItem menuFormatVertI = new MenuItem();
    MenuItem menuFormatVertD = new MenuItem();
    MenuItem menuFormatVertZ = new MenuItem();
    MenuItem menuFormatPaddingLeft = new MenuItem();
    MenuItem menuFormatPaddingLeftI = new MenuItem();
    MenuItem menuFormatPaddingLeftD = new MenuItem();
    MenuItem menuFormatPaddingLeftZ = new MenuItem();
    MenuItem menuFormatPaddingRight = new MenuItem();
    MenuItem menuFormatPaddingRightI = new MenuItem();
    MenuItem menuFormatPaddingRightD = new MenuItem();
    MenuItem menuFormatPaddingRightZ = new MenuItem();
    MenuItem menuFormatPaddingTop = new MenuItem();
    MenuItem menuFormatPaddingTopI = new MenuItem();
    MenuItem menuFormatPaddingTopD = new MenuItem();
    MenuItem menuFormatPaddingTopZ = new MenuItem();
    MenuItem menuFormatPaddingBottom = new MenuItem();
    MenuItem menuFormatPaddingBottomI = new MenuItem();
    MenuItem menuFormatPaddingBottomD = new MenuItem();
    MenuItem menuFormatPaddingBottomZ = new MenuItem();
    MenuItem menuFormat = new MenuItem();
    // Tools
    MenuItem menuTools = new MenuItem();
    MenuItem menuToolsValidateSchema = new MenuItem();
    MenuItem menuToolsProcess = new MenuItem();
    MenuItem menuToolsOptions = new MenuItem();
    // Window
    MenuItem menuCascade = new MenuItem();
    MenuItem menuTileH = new MenuItem();
    MenuItem menuTileV = new MenuItem();
    MenuItem menuTile = new MenuItem();
    private System.Windows.Forms.Button bTable = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bLine = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bImage = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bRectangle = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSubreport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bList = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bChart = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bText = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bMatrix = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bPrint = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSave = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bOpen = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bPaste = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCopy = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCut = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bNew = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bUndo = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bPdf = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bXml = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bHtml = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bMht = new System.Windows.Forms.Button();
    MenuItem menuCloseAll = new MenuItem();
    public RdlDesigner(boolean mono) throws Exception {
        bMono = mono;
        getStartupState();
        buildMenus();
        initializeComponent();
        this.MdiChildActivate += new EventHandler(RdlDesigner_MdiChildActivate);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.RdlDesigner_Closing);
        _GetPassword = new NeedPassword() 
          { 
            public System.String invoke() throws Exception {
                return this.GetPassword();
            }

            public List<NeedPassword> getInvocationList() throws Exception {
                List<NeedPassword> ret = new ArrayList<NeedPassword>();
                ret.add(this);
                return ret;
            }
        
          };
        initToolbar();
        initStatusBar();
        // open up the current files if any
        if (_CurrentFiles != null)
        {
            for (Object __dummyForeachVar0 : _CurrentFiles)
            {
                String file = (String)__dummyForeachVar0;
                createMDIChild(file,null,false);
            }
            _CurrentFiles = null;
        }
         
        // don't need this any longer
        DesignTabChanged(this, new EventArgs());
    }

    // force toolbar to get updated
    private void initStatusBar() throws Exception {
        mainSB = new StatusBar();
        mainSB.Parent = this;
        mainSB.Dock = DockStyle.Bottom;
        mainSB.Anchor = AnchorStyles.Top | AnchorStyles.Left;
        statusPrimary = new StatusBarPanel();
        statusPrimary.Width = 260;
        statusPrimary.MinWidth = 10;
        statusPrimary.AutoSize = StatusBarPanelAutoSize.Spring;
        statusPrimary.Text = "";
        statusPrimary.BorderStyle = StatusBarPanelBorderStyle.None;
        statusSelected = new StatusBarPanel();
        statusSelected.AutoSize = StatusBarPanelAutoSize.Contents;
        statusSelected.Alignment = HorizontalAlignment.Center;
        statusSelected.ToolTipText = "Name of selected ReportItem";
        statusSelected.Width = 192;
        statusSelected.MinWidth = 10;
        statusSelected.Text = "";
        statusSelected.BorderStyle = StatusBarPanelBorderStyle.Sunken;
        statusPosition = new StatusBarPanel();
        statusPosition.AutoSize = StatusBarPanelAutoSize.Contents;
        statusPosition.ToolTipText = "Position of selected ReportItem";
        statusPosition.Width = 96;
        statusPosition.MinWidth = 10;
        statusPosition.Alignment = HorizontalAlignment.Center;
        statusPosition.Text = "";
        statusPosition.BorderStyle = StatusBarPanelBorderStyle.Raised;
        mainSB.Size = new System.Drawing.Size(472, 22);
        mainSB.ShowPanels = true;
        mainSB.Location = new System.Drawing.Point(0, 282);
        mainSB.Name = "mainSB";
        mainSB.Panels.AddRange(new System.Windows.Forms.StatusBarPanel[]{ this.statusPrimary, this.statusSelected, this.statusPosition });
        this.Controls.Add(mainSB);
    }

    private void initToolbar() throws Exception {
        boolean bResumeLayout = false;
        // Clear out anything from before
        if (mainTB != null)
        {
            this.SuspendLayout();
            mainTB.SuspendLayout();
            bResumeLayout = true;
            mainTB.Controls.Clear();
            if (ctlBold != null)
            {
                ctlBold = null;
            }
             
            if (ctlItalic != null)
            {
                ctlItalic = null;
            }
             
            if (ctlUnderline != null)
            {
                ctlUnderline = null;
            }
             
            if (ctlFont != null)
            {
                ctlFont = null;
            }
             
            if (ctlFontSize != null)
            {
                ctlFontSize = null;
            }
             
            if (ctlForeColor != null)
            {
                ctlForeColor = null;
            }
             
            if (ctlBackColor != null)
            {
                ctlBackColor = null;
            }
             
            if (ctlNew != null)
            {
                ctlNew = null;
            }
             
            if (ctlOpen != null)
            {
                ctlOpen = null;
            }
             
            if (ctlSave != null)
            {
                ctlSave = null;
            }
             
            if (ctlCut != null)
            {
                ctlCut = null;
            }
             
            if (ctlCopy != null)
            {
                ctlCopy = null;
            }
             
            if (ctlUndo != null)
            {
                ctlUndo = null;
            }
             
            if (ctlPaste != null)
            {
                ctlPaste = null;
            }
             
            if (ctlPrint != null)
            {
                ctlPrint = null;
            }
             
            if (ctlPdf != null)
            {
                ctlPdf = null;
            }
             
            if (ctlXml != null)
            {
                ctlXml = null;
            }
             
            if (ctlHtml != null)
            {
                ctlHtml = null;
            }
             
            if (ctlMht != null)
            {
                ctlMht = null;
            }
             
            if (ctlZoom != null)
            {
                ctlZoom = null;
            }
             
            if (ctlInsertCurrent != null)
            {
                ctlInsertCurrent = null;
            }
             
            if (ctlInsertTextbox != null)
            {
                ctlInsertTextbox = null;
            }
             
            if (ctlInsertChart != null)
            {
                ctlInsertChart = null;
            }
             
            if (ctlInsertRectangle != null)
            {
                ctlInsertRectangle = null;
            }
             
            if (ctlInsertTable != null)
            {
                ctlInsertTable = null;
            }
             
            if (ctlInsertMatrix != null)
            {
                ctlInsertMatrix = null;
            }
             
            if (ctlInsertList != null)
            {
                ctlInsertList = null;
            }
             
            if (ctlInsertLine != null)
            {
                ctlInsertLine = null;
            }
             
            if (ctlInsertImage != null)
            {
                ctlInsertImage = null;
            }
             
            if (ctlInsertSubreport != null)
            {
                ctlInsertSubreport = null;
            }
             
            if (ctlEditTextbox != null)
            {
                ctlEditTextbox = null;
            }
             
            if (ctlEditLabel != null)
            {
                ctlEditLabel = null;
            }
             
        }
        else
        {
            mainTB = new ToolBar();
            mainTB.SizeChanged += new EventHandler(mainTB_SizeChanged);
            mainTB.SuspendLayout();
        } 
        ;
        ;
        int y = 2;
        int x = LEFTMARGIN;
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(RdlDesigner.class);
        for (Object __dummyForeachVar1 : _Toolbar)
        {
            // Build the controls the user wants
            String tbi = (String)__dummyForeachVar1;
            System.String __dummyScrutVar0 = tbi;
            if (__dummyScrutVar0.equals("\n") || __dummyScrutVar0.equals("Newline"))
            {
                y += LINEHEIGHT;
                x = LEFTMARGIN;
            }
            else if (__dummyScrutVar0.equals("Bold"))
            {
                x += initToolbarBold(x,y);
            }
            else if (__dummyScrutVar0.equals("Italic"))
            {
                x += initToolbarItalic(x,y);
            }
            else if (__dummyScrutVar0.equals("Underline"))
            {
                x += initToolbarUnderline(x,y);
            }
            else if (__dummyScrutVar0.equals("Space"))
            {
                x += 5;
            }
            else if (__dummyScrutVar0.equals("Font"))
            {
                x += initToolbarFont(x,y);
            }
            else if (__dummyScrutVar0.equals("FontSize"))
            {
                x += initToolbarFontSize(x,y);
            }
            else if (__dummyScrutVar0.equals("ForeColor"))
            {
                RefSupport<int> refVar___0 = new RefSupport<int>(x);
                ctlForeColor = initToolbarColor(refVar___0,y,"Fore Color");
                x = refVar___0.getValue();
                ctlForeColor.SelectedValueChanged += new EventHandler(ctlForeColor_Change);
                ctlForeColor.Validated += new EventHandler(ctlForeColor_Change);
            }
            else if (__dummyScrutVar0.equals("BackColor"))
            {
                RefSupport<int> refVar___1 = new RefSupport<int>(x);
                ctlBackColor = initToolbarColor(refVar___1,y,"Back Color");
                x = refVar___1.getValue();
                ctlBackColor.SelectedValueChanged += new EventHandler(ctlBackColor_Change);
                ctlBackColor.Validated += new EventHandler(ctlBackColor_Change);
            }
            else if (__dummyScrutVar0.equals("New"))
            {
                RefSupport<int> refVar___2 = new RefSupport<int>(x);
                ctlNew = InitToolbarMenu(refVar___2, y, "New", bNew.Image, new EventHandler(this.menuFileNewReport_Click));
                x = refVar___2.getValue();
            }
            else if (__dummyScrutVar0.equals("Open"))
            {
                RefSupport<int> refVar___3 = new RefSupport<int>(x);
                ctlOpen = InitToolbarMenu(refVar___3, y, "Open", bOpen.Image, new EventHandler(this.menuFileOpen_Click));
                x = refVar___3.getValue();
            }
            else if (__dummyScrutVar0.equals("Save"))
            {
                RefSupport<int> refVar___4 = new RefSupport<int>(x);
                ctlSave = InitToolbarMenu(refVar___4, y, "Save", bSave.Image, new EventHandler(this.menuFileSave_Click));
                x = refVar___4.getValue();
            }
            else if (__dummyScrutVar0.equals("Cut"))
            {
                RefSupport<int> refVar___5 = new RefSupport<int>(x);
                ctlCut = InitToolbarMenu(refVar___5, y, "Cut", bCut.Image, new EventHandler(this.menuEditCut_Click));
                x = refVar___5.getValue();
            }
            else if (__dummyScrutVar0.equals("Copy"))
            {
                RefSupport<int> refVar___6 = new RefSupport<int>(x);
                ctlCopy = InitToolbarMenu(refVar___6, y, "Copy", bCopy.Image, new EventHandler(this.menuEditCopy_Click));
                x = refVar___6.getValue();
            }
            else if (__dummyScrutVar0.equals("Undo"))
            {
                RefSupport<int> refVar___7 = new RefSupport<int>(x);
                ctlUndo = InitToolbarMenu(refVar___7, y, "Undo", bUndo.Image, new EventHandler(this.menuEditUndo_Click));
                x = refVar___7.getValue();
            }
            else if (__dummyScrutVar0.equals("Paste"))
            {
                RefSupport<int> refVar___8 = new RefSupport<int>(x);
                ctlPaste = InitToolbarMenu(refVar___8, y, "Paste", bPaste.Image, new EventHandler(this.menuEditPaste_Click));
                x = refVar___8.getValue();
            }
            else if (__dummyScrutVar0.equals("Print"))
            {
                RefSupport<int> refVar___9 = new RefSupport<int>(x);
                ctlPrint = InitToolbarMenu(refVar___9, y, "Print", bPrint.Image, new EventHandler(this.menuFilePrint_Click));
                x = refVar___9.getValue();
            }
            else if (__dummyScrutVar0.equals("XML"))
            {
                RefSupport<int> refVar___10 = new RefSupport<int>(x);
                ctlXml = InitToolbarMenu(refVar___10, y, "XML", bXml.Image, new EventHandler(this.menuExportXml_Click));
                x = refVar___10.getValue();
            }
            else if (__dummyScrutVar0.equals("PDF"))
            {
                RefSupport<int> refVar___11 = new RefSupport<int>(x);
                ctlPdf = InitToolbarMenu(refVar___11, y, "PDF", bPdf.Image, new EventHandler(this.menuExportPdf_Click));
                x = refVar___11.getValue();
            }
            else if (__dummyScrutVar0.equals("HTML"))
            {
                RefSupport<int> refVar___12 = new RefSupport<int>(x);
                ctlHtml = InitToolbarMenu(refVar___12, y, "HTML", bHtml.Image, new EventHandler(this.menuExportHtml_Click));
                x = refVar___12.getValue();
            }
            else if (__dummyScrutVar0.equals("MHT"))
            {
                RefSupport<int> refVar___13 = new RefSupport<int>(x);
                ctlMht = InitToolbarMenu(refVar___13, y, "MHT", bMht.Image, new EventHandler(this.menuExportMHtml_Click));
                x = refVar___13.getValue();
            }
            else if (__dummyScrutVar0.equals("Edit"))
            {
                RefSupport<int> refVar___14 = new RefSupport<int>(x);
                ctlEditTextbox = initToolbarEditTextbox(refVar___14,y);
                x = refVar___14.getValue();
            }
            else if (__dummyScrutVar0.equals("Textbox"))
            {
                RefSupport<int> refVar___15 = new RefSupport<int>(x);
                ctlInsertTextbox = InitToolbarInsertToogle(refVar___15, y, "Textbox", bText.Image);
                x = refVar___15.getValue();
            }
            else if (__dummyScrutVar0.equals("Chart"))
            {
                RefSupport<int> refVar___16 = new RefSupport<int>(x);
                ctlInsertChart = InitToolbarInsertToogle(refVar___16, y, "Chart", bChart.Image);
                x = refVar___16.getValue();
            }
            else if (__dummyScrutVar0.equals("Rectangle"))
            {
                RefSupport<int> refVar___17 = new RefSupport<int>(x);
                ctlInsertRectangle = InitToolbarInsertToogle(refVar___17, y, "Rectangle", bRectangle.Image);
                x = refVar___17.getValue();
            }
            else if (__dummyScrutVar0.equals("Table"))
            {
                RefSupport<int> refVar___18 = new RefSupport<int>(x);
                ctlInsertTable = InitToolbarInsertToogle(refVar___18, y, "Table", bTable.Image);
                x = refVar___18.getValue();
            }
            else if (__dummyScrutVar0.equals("Matrix"))
            {
                RefSupport<int> refVar___19 = new RefSupport<int>(x);
                ctlInsertMatrix = InitToolbarInsertToogle(refVar___19, y, "Matrix", bMatrix.Image);
                x = refVar___19.getValue();
            }
            else if (__dummyScrutVar0.equals("List"))
            {
                RefSupport<int> refVar___20 = new RefSupport<int>(x);
                ctlInsertList = InitToolbarInsertToogle(refVar___20, y, "List", bList.Image);
                x = refVar___20.getValue();
            }
            else if (__dummyScrutVar0.equals("Line"))
            {
                RefSupport<int> refVar___21 = new RefSupport<int>(x);
                ctlInsertLine = InitToolbarInsertToogle(refVar___21, y, "Line", bLine.Image);
                x = refVar___21.getValue();
            }
            else if (__dummyScrutVar0.equals("Image"))
            {
                RefSupport<int> refVar___22 = new RefSupport<int>(x);
                ctlInsertImage = InitToolbarInsertToogle(refVar___22, y, "Image", bImage.Image);
                x = refVar___22.getValue();
            }
            else if (__dummyScrutVar0.equals("Subreport"))
            {
                RefSupport<int> refVar___23 = new RefSupport<int>(x);
                ctlInsertSubreport = InitToolbarInsertToogle(refVar___23, y, "Subreport", bSubreport.Image);
                x = refVar___23.getValue();
            }
            else if (__dummyScrutVar0.equals("Zoom"))
            {
                RefSupport<int> refVar___24 = new RefSupport<int>(x);
                ctlZoom = initToolbarZoom(refVar___24,y);
                x = refVar___24.getValue();
                ctlZoom.SelectedValueChanged += new EventHandler(ctlZoom_Change);
                ctlZoom.Validated += new EventHandler(ctlZoom_Change);
            }
            else
            {
            }                                
        }
        // put the tab control in
        if (mainTC == null)
        {
            mainTC = new TabControl();
            mainTC.SelectedIndexChanged += new EventHandler(mainTC_SelectedIndexChanged);
            mainTC.ShowToolTips = true;
        }
         
        mainTC.Parent = mainTB;
        mainTC.Visible = _ShowTabbedInterface;
        if (_ShowTabbedInterface)
        {
            for (Object __dummyForeachVar2 : this.MdiChildren)
            {
                // When tabbed we force the mdi children to be maximized (on reset)
                MDIChild mc = (MDIChild)__dummyForeachVar2;
                mc.WindowState = FormWindowState.Maximized;
            }
        }
         
        mainTC.Location = new Point(0, y + LINEHEIGHT + 1);
        mainTC.Size = new Size(mainTB.Width, LINEHEIGHT);
        if (_ShowTabbedInterface)
            y += LINEHEIGHT;
         
        mainTB.Parent = this;
        mainTB.BorderStyle = BorderStyle.None;
        mainTB.DropDownArrows = true;
        mainTB.Name = "mainTB";
        mainTB.ShowToolTips = true;
        //			mainTB.Size = new Size(440,20);
        mainTB.TabIndex = 1;
        mainTB.AutoSize = false;
        mainTB.Height = y + LINEHEIGHT + 1;
        // 1 was 4
        if (bResumeLayout)
        {
            mainTB.ResumeLayout();
            this.ResumeLayout();
        }
         
    }

    void mainTB_SizeChanged(Object sender, EventArgs e) throws Exception {
        mainTC.Width = mainTB.Width;
    }

    void mainTC_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        MDIChild mc = mainTC.SelectedTab == null ? null : (mainTC.SelectedTab.Tag instanceof MDIChild ? (MDIChild)mainTC.SelectedTab.Tag : (MDIChild)null);
        mdi_Activate(mc);
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean lockWindowUpdate(IntPtr hWndLock) throws Exception ;

    void mdi_Activate(MDIChild mc) throws Exception {
        if (mc == null)
            return ;
         
        LockWindowUpdate(this.Handle);
        mc.Activate();
        this.Refresh();
        //Forces a synchronous redraw of all controls
        LockWindowUpdate(IntPtr.Zero);
    }

    public int getRecentFilesMax() throws Exception {
        return _RecentFilesMax;
    }

    public void setRecentFilesMax(int value) throws Exception {
        _RecentFilesMax = value;
    }

    public SortedList<DateTime, String> getRecentFiles() throws Exception {
        return _RecentFiles;
    }

    public String getHelpUrl() throws Exception {
        if (_HelpUrl != null && _HelpUrl.Length > 0)
            return _HelpUrl;
         
        return DefaultHelpUrl;
    }

    public void setHelpUrl(String value) throws Exception {
        _HelpUrl = value.Length > 0 ? value : DefaultHelpUrl;
    }

    public boolean getShowEditLines() throws Exception {
        return _ShowEditLines;
    }

    public void setShowEditLines(boolean value) throws Exception {
        _ShowEditLines = value;
    }

    public boolean getShowReportItemOutline() throws Exception {
        return _ShowReportItemOutline;
    }

    public void setShowReportItemOutline(boolean value) throws Exception {
        _ShowReportItemOutline = value;
    }

    public boolean getShowTabbedInterface() throws Exception {
        return _ShowTabbedInterface;
    }

    public void setShowTabbedInterface(boolean value) throws Exception {
        _ShowTabbedInterface = value;
    }

    public String getSupportUrl() throws Exception {
        if (_SupportUrl != null && _SupportUrl.Length > 0)
            return _SupportUrl;
         
        return DefaultSupportUrl;
    }

    public void setSupportUrl(String value) throws Exception {
        _SupportUrl = value.Length > 0 ? value : DefaultSupportUrl;
    }

    public List<String> getToolbar() throws Exception {
        return _Toolbar;
    }

    public void setToolbar(List<String> value) throws Exception {
        _Toolbar = value;
        initToolbar();
    }

    // reset the toolbar
    public List<String> getToolbarDefault() throws Exception {
        return new List<String>(new String[]{ "New", "Open", "Save", "Space", "Cut", "Copy", "Paste", "Undo", "Space", "Textbox", "Chart", "Table", "List", "Image", "Matrix", "Subreport", "Rectangle", "Line", "Space", "Edit", "Newline", "Bold", "Italic", "Underline", "Space", "Font", "FontSize", "Space", "ForeColor", "BackColor", "Space", "Print", "Space", "Zoom", "Space", "PDF", "HTML", "XML", "MHT" });
            ;
    }

    public List<String> getToolbarAllowDups() throws Exception {
        return new List<String>(new String[]{ "Space", "Newline" });
            ;
    }

    /**
    * All of the possible items that can be placed on a toolbar
    */
    public String[] getToolbarOperations() throws Exception {
        return new String[]{ "Newline", "Bold", "Italic", "Underline", "Space", "Font", "FontSize", "ForeColor", "BackColor", "New", "Open", "Save", "Cut", "Copy", "Undo", "Paste", "Print", "XML", "PDF", "HTML", "Edit", "Textbox", "Chart", "Rectangle", "Table", "Matrix", "List", "Line", "Image", "Subreport", "Zoom" };
    }

    private Button initToolbarMenu(RefSupport<int> x, int y, String t, System.EventHandler handler) throws Exception {
        RefSupport<int> refVar___25 = new RefSupport<int>(x.getValue());
        resVar___0 = initToolbarMenu(refVar___25,y,t,null,handler);
        x.setValue(refVar___25.getValue());
        return resVar___0;
    }

    private Button initToolbarMenu(RefSupport<int> x, int y, String t, Image img, System.EventHandler handler) throws Exception {
        SimpleButton ctl = new SimpleButton(this);
        ctl.Click += handler;
        if (img == null)
        {
            ctl.Text = t;
            ctl.Font = new Font("Arial", 8, FontStyle.Regular);
            Graphics g = ctl.CreateGraphics();
            try
            {
                {
                    SizeF fs = g.MeasureString(ctl.Text, ctl.Font);
                    ctl.Height = (int)fs.Height + 8;
                    // 8 is for margin so entire text shows in button
                    ctl.Width = (int)fs.Width + 4;
                    ctl.TextAlign = ContentAlignment.MiddleCenter;
                }
            }
            finally
            {
                if (g != null)
                    Disposable.mkDisposable(g).dispose();
                 
            }
        }
        else
        {
            ctl.Image = img;
            ctl.ImageAlign = ContentAlignment.MiddleCenter;
            ctl.Height = img.getHeight() + 5;
            ctl.Width = img.getWidth() + 8;
            ctl.Text = "";
        } 
        ctl.Tag = t;
        ctl.Left = x.getValue();
        ctl.Top = y;
        ctl.FlatStyle = FlatStyle.Flat;
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(ctl, t);
        mainTB.Controls.Add(ctl);
        x.setValue(x.getValue() + ctl.Width);
        return ctl;
    }

    private System.Windows.Forms.TextBox initToolbarEditTextbox(RefSupport<int> x, int y) throws Exception {
        System.Windows.Forms.TextBox tb = new System.Windows.Forms.TextBox();
        Label l = this.ctlEditLabel = new Label();
        l.AutoSize = true;
        l.Font = new Font("Arial", 8, FontStyle.Bold | FontStyle.Italic);
        l.Text = "fx";
        l.Left = x.getValue();
        l.Top = y + (tb.Height / 2) - (l.Height / 2);
        mainTB.Controls.Add(l);
        x.setValue(x.getValue() + l.Width);
        tb.Left = x.getValue();
        tb.Width = 230;
        x.setValue(x.getValue() + tb.Width);
        tb.Top = y;
        tb.Validated += new EventHandler(this.EditTextbox_Validated);
        // handler for edit changes
        tb.KeyDown += new KeyEventHandler(EditTextBox_KeyDown);
        mainTB.Controls.Add(tb);
        return tb;
    }

    private SimpleToggle initToolbarInsertToogle(RefSupport<int> x, int y, String t) throws Exception {
        RefSupport<int> refVar___26 = new RefSupport<int>(x.getValue());
        SimpleToggle resVar___1 = initToolbarInsertToogle(refVar___26,y,t,null);
        x.setValue(refVar___26.getValue());
        return resVar___1;
    }

    private SimpleToggle initToolbarInsertToogle(RefSupport<int> x, int y, String t, Image bImg) throws Exception {
        SimpleToggle ctl = new SimpleToggle();
        ctl.setUpColor(this.BackColor);
        ctl.Click += new EventHandler(this.Insert_Click);
        // click handler for all inserts
        if (bImg == null)
        {
            ctl.Text = t;
            Graphics g = ctl.CreateGraphics();
            try
            {
                {
                    SizeF fs = g.MeasureString(ctl.Text, ctl.Font);
                    ctl.Height = (int)fs.Height + 8;
                    // 8 is for margins
                    ctl.Width = (int)fs.Width + 12;
                }
            }
            finally
            {
                if (g != null)
                    Disposable.mkDisposable(g).dispose();
                 
            }
        }
        else
        {
            ctl.Image = bImg;
            ctl.ImageAlign = ContentAlignment.MiddleCenter;
            ctl.Height = bImg.getHeight() + 5;
            ctl.Width = bImg.getWidth() + 8;
            ctl.Text = "";
        } 
        ctl.Tag = t;
        ctl.Left = x.getValue();
        ctl.Top = y;
        ctl.FlatStyle = FlatStyle.Flat;
        ToolTip tipb = new ToolTip();
        tipb.AutomaticDelay = 500;
        tipb.ShowAlways = true;
        tipb.SetToolTip(ctl, t);
        mainTB.Controls.Add(ctl);
        x.setValue(x.getValue() + ctl.Width);
        return ctl;
    }

    private int initToolbarBold(int x, int y) throws Exception {
        ctlBold = new SimpleToggle();
        ctlBold.setUpColor(this.BackColor);
        ctlBold.Click += new EventHandler(ctlBold_Click);
        ctlBold.Font = new Font("Courier New", 10, FontStyle.Bold);
        ctlBold.Text = "B";
        Graphics g = ctlBold.CreateGraphics();
        try
        {
            {
                SizeF fs = g.MeasureString(ctlBold.Text, ctlBold.Font);
                ctlBold.Height = (int)fs.Height + 6;
                // 6 is for margins
                ctlBold.Width = ctlBold.Height;
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        ctlBold.Tag = "bold";
        ctlBold.Left = x;
        ctlBold.Top = y;
        ctlBold.FlatStyle = FlatStyle.Flat;
        ctlBold.TextAlign = ContentAlignment.MiddleCenter;
        ToolTip tipb = new ToolTip();
        tipb.AutomaticDelay = 500;
        tipb.ShowAlways = true;
        tipb.SetToolTip(ctlBold, "Bold");
        mainTB.Controls.Add(ctlBold);
        return ctlBold.Width;
    }

    private int initToolbarItalic(int x, int y) throws Exception {
        ctlItalic = new SimpleToggle();
        ctlItalic.setUpColor(this.BackColor);
        ctlItalic.Click += new EventHandler(ctlItalic_Click);
        ctlItalic.Font = new Font("Courier New", 10, FontStyle.Italic | FontStyle.Bold);
        ctlItalic.Text = "I";
        Graphics g = ctlItalic.CreateGraphics();
        try
        {
            {
                SizeF fs = g.MeasureString(ctlItalic.Text, ctlItalic.Font);
                ctlItalic.Height = (int)fs.Height + 6;
                // 6 is for margins
                ctlItalic.Width = ctlItalic.Height;
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        ctlItalic.Tag = "italic";
        ctlItalic.Left = x;
        ctlItalic.Top = y;
        ctlItalic.FlatStyle = FlatStyle.Flat;
        ToolTip tipb = new ToolTip();
        tipb.AutomaticDelay = 500;
        tipb.ShowAlways = true;
        tipb.SetToolTip(ctlItalic, "Italic");
        mainTB.Controls.Add(ctlItalic);
        return ctlItalic.Width;
    }

    private int initToolbarUnderline(int x, int y) throws Exception {
        ctlUnderline = new SimpleToggle();
        ctlUnderline.setUpColor(this.BackColor);
        ctlUnderline.Click += new EventHandler(ctlUnderline_Click);
        ctlUnderline.Font = new Font("Courier New", 10, FontStyle.Underline | FontStyle.Bold);
        ctlUnderline.Text = "U";
        Graphics g = ctlUnderline.CreateGraphics();
        try
        {
            {
                SizeF fs = g.MeasureString(ctlUnderline.Text, ctlUnderline.Font);
                ctlUnderline.Height = (int)fs.Height + 6;
                // 6 is for margins
                ctlUnderline.Width = ctlUnderline.Height;
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        ctlUnderline.Tag = "italic";
        ctlUnderline.Left = x;
        ctlUnderline.Top = y;
        ctlUnderline.FlatStyle = FlatStyle.Flat;
        ToolTip tipb = new ToolTip();
        tipb.AutomaticDelay = 500;
        tipb.ShowAlways = true;
        tipb.SetToolTip(ctlUnderline, "Underline");
        mainTB.Controls.Add(ctlUnderline);
        return ctlUnderline.Width;
    }

    private int initToolbarFont(int x, int y) throws Exception {
        // Create the font
        ctlFont = new ComboBox();
        ctlFont.SelectedValueChanged += new EventHandler(ctlFont_Change);
        ctlFont.Validated += new EventHandler(ctlFont_Change);
        ctlFont.Left = x;
        ctlFont.Top = y;
        ctlFont.DropDownStyle = ComboBoxStyle.DropDown;
        for (Object __dummyForeachVar3 : FontFamily.Families)
        {
            FontFamily ff = (FontFamily)__dummyForeachVar3;
            ctlFont.Items.Add(ff.Name);
        }
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(ctlFont, "Font");
        mainTB.Controls.Add(ctlFont);
        return ctlFont.Width;
    }

    private int initToolbarFontSize(int x, int y) throws Exception {
        // Create the font
        ctlFontSize = new ComboBox();
        ctlFontSize.SelectedValueChanged += new EventHandler(ctlFontSize_Change);
        ctlFontSize.Validated += new EventHandler(ctlFontSize_Change);
        ctlFontSize.Width = 42;
        ctlFontSize.Left = x;
        ctlFontSize.Top = y;
        ctlFontSize.DropDownStyle = ComboBoxStyle.DropDown;
        String[] sizes = new String[]{ "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72" };
        ctlFontSize.Items.AddRange(sizes);
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(ctlFontSize, "Font Size");
        mainTB.Controls.Add(ctlFontSize);
        return ctlFontSize.Width;
    }

    private ComboBox initToolbarColor(RefSupport<int> x, int y, String t) throws Exception {
        // Create the font
        ComboBox ctl = new ComboBox();
        ctl.Width = 100;
        ctl.Left = x.getValue();
        ctl.Top = y;
        ctl.Tag = t;
        ctl.DropDownStyle = ComboBoxStyle.DropDown;
        ctl.Items.AddRange(StaticLists.ColorList);
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(ctl, t);
        mainTB.Controls.Add(ctl);
        x.setValue(x.getValue() + ctl.Width);
        return ctl;
    }

    private ComboBox initToolbarZoom(RefSupport<int> x, int y) throws Exception {
        ComboBox ctl = new ComboBox();
        ctl.Width = 85;
        ctl.Left = x.getValue();
        ctl.Top = y;
        ctl.Tag = "Zoom";
        ctl.DropDownStyle = ComboBoxStyle.DropDownList;
        ctl.Items.AddRange(StaticLists.ZoomList);
        ToolTip tip = new ToolTip();
        tip.AutomaticDelay = 500;
        tip.ShowAlways = true;
        tip.SetToolTip(ctl, "Zoom");
        mainTB.Controls.Add(ctl);
        x.setValue(x.getValue() + ctl.Width);
        return ctl;
    }

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
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(RdlDesigner.class);
        this.bTable = new System.Windows.Forms.Button();
        this.bLine = new System.Windows.Forms.Button();
        this.bImage = new System.Windows.Forms.Button();
        this.bRectangle = new System.Windows.Forms.Button();
        this.bSubreport = new System.Windows.Forms.Button();
        this.bList = new System.Windows.Forms.Button();
        this.bChart = new System.Windows.Forms.Button();
        this.bText = new System.Windows.Forms.Button();
        this.bMatrix = new System.Windows.Forms.Button();
        this.bPrint = new System.Windows.Forms.Button();
        this.bSave = new System.Windows.Forms.Button();
        this.bOpen = new System.Windows.Forms.Button();
        this.bPaste = new System.Windows.Forms.Button();
        this.bCopy = new System.Windows.Forms.Button();
        this.bCut = new System.Windows.Forms.Button();
        this.bNew = new System.Windows.Forms.Button();
        this.bUndo = new System.Windows.Forms.Button();
        this.bPdf = new System.Windows.Forms.Button();
        this.bXml = new System.Windows.Forms.Button();
        this.bHtml = new System.Windows.Forms.Button();
        this.bMht = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // bTable
        //
        this.bTable.Image = ((System.Drawing.Image)(resources.GetObject("bTable.Image")));
        this.bTable.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bTable.Location = new System.Drawing.Point(608, 32);
        this.bTable.Name = "bTable";
        this.bTable.TabIndex = 0;
        this.bTable.Text = "Table";
        this.bTable.Visible = false;
        //
        // bLine
        //
        this.bLine.Image = ((System.Drawing.Image)(resources.GetObject("bLine.Image")));
        this.bLine.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bLine.Location = new System.Drawing.Point(608, 288);
        this.bLine.Name = "bLine";
        this.bLine.TabIndex = 1;
        this.bLine.Text = "Line";
        this.bLine.Visible = false;
        //
        // bImage
        //
        this.bImage.Image = ((System.Drawing.Image)(resources.GetObject("bImage.Image")));
        this.bImage.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bImage.Location = new System.Drawing.Point(608, 256);
        this.bImage.Name = "bImage";
        this.bImage.TabIndex = 2;
        this.bImage.Text = "Image";
        this.bImage.Visible = false;
        //
        // bRectangle
        //
        this.bRectangle.Image = ((System.Drawing.Image)(resources.GetObject("bRectangle.Image")));
        this.bRectangle.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bRectangle.Location = new System.Drawing.Point(608, 224);
        this.bRectangle.Name = "bRectangle";
        this.bRectangle.TabIndex = 3;
        this.bRectangle.Text = "Rect";
        this.bRectangle.Visible = false;
        //
        // bSubreport
        //
        this.bSubreport.Image = ((System.Drawing.Image)(resources.GetObject("bSubreport.Image")));
        this.bSubreport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bSubreport.Location = new System.Drawing.Point(608, 192);
        this.bSubreport.Name = "bSubreport";
        this.bSubreport.TabIndex = 4;
        this.bSubreport.Text = "Subreport";
        this.bSubreport.Visible = false;
        //
        // bList
        //
        this.bList.Image = ((System.Drawing.Image)(resources.GetObject("bList.Image")));
        this.bList.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bList.Location = new System.Drawing.Point(608, 160);
        this.bList.Name = "bList";
        this.bList.TabIndex = 5;
        this.bList.Text = "List";
        this.bList.Visible = false;
        //
        // bChart
        //
        this.bChart.Image = ((System.Drawing.Image)(resources.GetObject("bChart.Image")));
        this.bChart.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bChart.Location = new System.Drawing.Point(608, 128);
        this.bChart.Name = "bChart";
        this.bChart.TabIndex = 6;
        this.bChart.Text = "Chart";
        this.bChart.Visible = false;
        //
        // bText
        //
        this.bText.Image = ((System.Drawing.Image)(resources.GetObject("bText.Image")));
        this.bText.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bText.Location = new System.Drawing.Point(608, 96);
        this.bText.Name = "bText";
        this.bText.TabIndex = 7;
        this.bText.Text = "Text";
        this.bText.Visible = false;
        //
        // bMatrix
        //
        this.bMatrix.Image = ((System.Drawing.Image)(resources.GetObject("bMatrix.Image")));
        this.bMatrix.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bMatrix.Location = new System.Drawing.Point(608, 64);
        this.bMatrix.Name = "bMatrix";
        this.bMatrix.TabIndex = 8;
        this.bMatrix.Text = "Matrix";
        this.bMatrix.Visible = false;
        //
        // bPrint
        //
        this.bPrint.Image = ((System.Drawing.Image)(resources.GetObject("bPrint.Image")));
        this.bPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bPrint.Location = new System.Drawing.Point(504, 192);
        this.bPrint.Name = "bPrint";
        this.bPrint.TabIndex = 9;
        this.bPrint.Text = "Print";
        this.bPrint.Visible = false;
        //
        // bSave
        //
        this.bSave.Image = ((System.Drawing.Image)(resources.GetObject("bSave.Image")));
        this.bSave.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bSave.Location = new System.Drawing.Point(504, 160);
        this.bSave.Name = "bSave";
        this.bSave.TabIndex = 10;
        this.bSave.Text = "Save";
        this.bSave.Visible = false;
        //
        // bOpen
        //
        this.bOpen.Image = ((System.Drawing.Image)(resources.GetObject("bOpen.Image")));
        this.bOpen.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bOpen.Location = new System.Drawing.Point(504, 128);
        this.bOpen.Name = "bOpen";
        this.bOpen.TabIndex = 11;
        this.bOpen.Text = "Open";
        this.bOpen.Visible = false;
        //
        // bPaste
        //
        this.bPaste.Image = ((System.Drawing.Image)(resources.GetObject("bPaste.Image")));
        this.bPaste.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bPaste.Location = new System.Drawing.Point(504, 96);
        this.bPaste.Name = "bPaste";
        this.bPaste.TabIndex = 12;
        this.bPaste.Text = "Paste";
        this.bPaste.Visible = false;
        //
        // bCopy
        //
        this.bCopy.Image = ((System.Drawing.Image)(resources.GetObject("bCopy.Image")));
        this.bCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bCopy.Location = new System.Drawing.Point(504, 64);
        this.bCopy.Name = "bCopy";
        this.bCopy.TabIndex = 13;
        this.bCopy.Text = "Copy";
        this.bCopy.Visible = false;
        //
        // bCut
        //
        this.bCut.Image = ((System.Drawing.Image)(resources.GetObject("bCut.Image")));
        this.bCut.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bCut.Location = new System.Drawing.Point(504, 32);
        this.bCut.Name = "bCut";
        this.bCut.TabIndex = 14;
        this.bCut.Text = "Cut";
        this.bCut.Visible = false;
        //
        // bNew
        //
        this.bNew.Image = ((System.Drawing.Image)(resources.GetObject("bNew.Image")));
        this.bNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bNew.Location = new System.Drawing.Point(504, 224);
        this.bNew.Name = "bNew";
        this.bNew.TabIndex = 15;
        this.bNew.Text = "New";
        this.bNew.Visible = false;
        //
        // bUndo
        //
        this.bUndo.Image = ((System.Drawing.Image)(resources.GetObject("bUndo.Image")));
        this.bUndo.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bUndo.Location = new System.Drawing.Point(504, 256);
        this.bUndo.Name = "bUndo";
        this.bUndo.TabIndex = 16;
        this.bUndo.Text = "Undo";
        this.bUndo.Visible = false;
        //
        // bPdf
        //
        this.bPdf.Image = ((System.Drawing.Image)(resources.GetObject("bPdf.Image")));
        this.bPdf.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bPdf.Location = new System.Drawing.Point(504, 320);
        this.bPdf.Name = "bPdf";
        this.bPdf.TabIndex = 17;
        this.bPdf.Text = "PDF";
        this.bPdf.Visible = false;
        //
        // bXml
        //
        this.bXml.Image = ((System.Drawing.Image)(resources.GetObject("bXml.Image")));
        this.bXml.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bXml.Location = new System.Drawing.Point(504, 288);
        this.bXml.Name = "bXml";
        this.bXml.TabIndex = 18;
        this.bXml.Text = "XML";
        this.bXml.Visible = false;
        //
        // bHtml
        //
        this.bHtml.Image = ((System.Drawing.Image)(resources.GetObject("bHtml.Image")));
        this.bHtml.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bHtml.Location = new System.Drawing.Point(608, 320);
        this.bHtml.Name = "bHtml";
        this.bHtml.TabIndex = 19;
        this.bHtml.Text = "HTML";
        this.bHtml.Visible = false;
        //
        // bMht
        //
        this.bMht.Image = ((System.Drawing.Image)(resources.GetObject("bMht.Image")));
        this.bMht.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bMht.Location = new System.Drawing.Point(504, 360);
        this.bMht.Name = "bMht";
        this.bMht.TabIndex = 20;
        this.bMht.Text = "HTML";
        this.bMht.Visible = false;
        //
        // RdlDesigner
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(712, 470);
        this.Controls.Add(this.bMht);
        this.Controls.Add(this.bHtml);
        this.Controls.Add(this.bXml);
        this.Controls.Add(this.bPdf);
        this.Controls.Add(this.bUndo);
        this.Controls.Add(this.bNew);
        this.Controls.Add(this.bCut);
        this.Controls.Add(this.bCopy);
        this.Controls.Add(this.bPaste);
        this.Controls.Add(this.bOpen);
        this.Controls.Add(this.bSave);
        this.Controls.Add(this.bPrint);
        this.Controls.Add(this.bMatrix);
        this.Controls.Add(this.bText);
        this.Controls.Add(this.bChart);
        this.Controls.Add(this.bList);
        this.Controls.Add(this.bSubreport);
        this.Controls.Add(this.bRectangle);
        this.Controls.Add(this.bImage);
        this.Controls.Add(this.bLine);
        this.Controls.Add(this.bTable);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "RdlDesigner";
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.Text = "fyiReporting Designer";
        this.ResumeLayout(false);
    }

    /**
    * The main entry point for the application.
    */
    public static void main(String[] args) throws Exception {
        RdlDesigner.Main();
    }

    static void Main() throws Exception {
        boolean bMono = new boolean();
        String[] args = Environment.GetCommandLineArgs();
        if (args.Length >= 2 && (StringSupport.equals(args[1].ToLower(), "/m") || StringSupport.equals(args[1].ToLower(), "-m")))
        {
            // user want to run with mono simplifications
            bMono = true;
        }
        else
        {
            bMono = false;
            Application.EnableVisualStyles();
            Application.DoEvents();
        } 
        // when Mono this goes into a loop
        Application.Run(new RdlDesigner(bMono));
    }

    private void buildMenus() throws Exception {
        // FILE MENU
        menuNew = new MenuItem("&New Report...", new EventHandler(this.menuFileNewReport_Click), Shortcut.CtrlN);
        menuOpen = new MenuItem("&Open...", new EventHandler(this.menuFileOpen_Click), Shortcut.CtrlO);
        menuClose = new MenuItem("&Close", new EventHandler(this.menuFileClose_Click), Shortcut.CtrlW);
        menuFSep1 = new MenuItem("-");
        menuSave = new MenuItem("&Save", new EventHandler(this.menuFileSave_Click), Shortcut.CtrlS);
        menuSaveAs = new MenuItem("Save &As...", new EventHandler(this.menuFileSaveAs_Click));
        menuPrint = new MenuItem("Print...", new EventHandler(this.menuFilePrint_Click), Shortcut.CtrlP);
        menuExport = new MenuItem("Export");
        menuExportPdf = new MenuItem("PDF...", new EventHandler(this.menuExportPdf_Click));
        menuExportXml = new MenuItem("XML...", new EventHandler(this.menuExportXml_Click));
        menuExportHtml = new MenuItem("Web Page, HTML...", new EventHandler(this.menuExportHtml_Click));
        menuExportMHtml = new MenuItem("Web Archive, single file MHT...", new EventHandler(this.menuExportMHtml_Click));
        menuExport.MenuItems.AddRange(new MenuItem[]{ menuExportPdf, menuExportHtml, menuExportMHtml, menuExportXml });
        menuFSep2 = new MenuItem("-");
        MenuItem menuRecentItem = new MenuItem("");
        menuRecentFile = new MenuItem("Recent &Files");
        menuRecentFile.MenuItems.AddRange(new MenuItem[]{ menuRecentItem });
        menuFSep3 = new MenuItem("-");
        menuExit = new MenuItem("E&xit", new EventHandler(this.menuFileExit_Click), Shortcut.CtrlQ);
        // Create file menu and add array of sub-menu items
        MenuItem menuFile = new MenuItem("&File");
        menuFile.Popup += new EventHandler(this.menuFile_Popup);
        menuFile.MenuItems.AddRange(new MenuItem[]{ menuNew, menuOpen, menuClose, menuFSep1, menuSave, menuSaveAs, menuPrint, menuExport, menuFSep2, menuRecentFile, menuFSep3, menuExit });
        // Intialize the recent file menu
        recentFilesMenu();
        // EDIT MENU
        menuEditUndo = new MenuItem("&Undo", new EventHandler(this.menuEditUndo_Click), Shortcut.CtrlZ);
        menuEditRedo = new MenuItem("&Redo", new EventHandler(this.menuEditRedo_Click), Shortcut.CtrlY);
        menuFSep1 = new MenuItem("-");
        menuEditCut = new MenuItem("Cu&t", new EventHandler(this.menuEditCut_Click), Shortcut.CtrlX);
        menuEditCopy = new MenuItem("&Copy", new EventHandler(this.menuEditCopy_Click), Shortcut.CtrlC);
        menuEditPaste = new MenuItem("&Paste", new EventHandler(this.menuEditPaste_Click), Shortcut.CtrlV);
        menuEditDelete = new MenuItem("&Delete", new EventHandler(this.menuEditDelete_Click));
        menuFSep2 = new MenuItem("-");
        menuEditSelectAll = new MenuItem("Select &All", new EventHandler(this.menuEditSelectAll_Click), Shortcut.CtrlA);
        menuFSep3 = new MenuItem("-");
        menuEditFind = new MenuItem("&Find...", new EventHandler(this.menuEditFind_Click), Shortcut.CtrlF);
        menuEditFindNext = new MenuItem("Find Next", new EventHandler(this.menuEditFindNext_Click), Shortcut.F3);
        menuEditReplace = new MenuItem("&Replace...", new EventHandler(this.menuEditReplace_Click), Shortcut.CtrlH);
        menuEditGoto = new MenuItem("&Go To...", new EventHandler(this.menuEditGoto_Click), Shortcut.CtrlG);
        menuFSep4 = new MenuItem("-");
        menuEditFormatXml = new MenuItem("Format XM&L", new EventHandler(this.menuEdit_FormatXml), Shortcut.CtrlL);
        // Create edit menu and add array of sub-menu items
        menuEdit = new MenuItem("&Edit");
        menuEdit.Popup += new EventHandler(this.menuEdit_Popup);
        menuEdit.MenuItems.AddRange(new MenuItem[]{ menuEditUndo, menuEditRedo, menuFSep1, menuEditCut, menuEditPaste, menuEditDelete, menuFSep2, menuEditSelectAll, menuFSep3, menuEditFind, menuEditFindNext, menuEditReplace, menuEditGoto, menuFSep4, menuEditFormatXml });
        // View Menu
        menuViewDesigner = new MenuItem("Designer", new EventHandler(this.menuViewDesigner_Click), Shortcut.F7);
        menuViewRDL = new MenuItem("RDL Text", new EventHandler(this.menuViewRDL_Click), Shortcut.ShiftF7);
        menuViewPreview = new MenuItem("Preview", new EventHandler(this.menuViewPreview_Click), Shortcut.F5);
        menuViewBrowser = new MenuItem("Show Report in Browser", new EventHandler(this.menuViewBrowser_Click), Shortcut.F6);
        menuViewProperties = new MenuItem("Properties...", new EventHandler(this.menuEditProperties_Click), Shortcut.F4);
        menuView = new MenuItem("&View");
        menuView.Popup += new EventHandler(menuView_Popup);
        menuView.MenuItems.AddRange(new MenuItem[]{ menuViewDesigner, menuViewRDL, menuViewPreview, new MenuItem("-"), menuViewBrowser, new MenuItem("-"), menuViewProperties });
        // Data Menu
        menuNewDataSourceRef = new MenuItem("&Create Shared Data Source...", new EventHandler(this.menuFileNewDataSourceRef_Click));
        menuDataSources = new MenuItem("Data &Sources...", new EventHandler(this.menuDataSources_Click));
        menuDataSets = new MenuItem("&Data Sets");
        menuDataSets.MenuItems.Add(new MenuItem());
        // this will actually get build dynamically
        menuEmbeddedImages = new MenuItem("&Embedded Images...", new EventHandler(this.menuEmbeddedImages_Click));
        menuData = new MenuItem("&Data");
        menuData.Popup += new EventHandler(this.menuData_Popup);
        menuData.MenuItems.AddRange(new MenuItem[]{ menuDataSets, menuDataSources, new MenuItem("-"), menuEmbeddedImages, new MenuItem("-"), menuNewDataSourceRef });
        // Format menu
        menuFormatAlign = new MenuItem("&Align");
        menuFormatAlignL = new MenuItem("&Lefts", new EventHandler(this.menuFormatAlignL_Click));
        menuFormatAlignC = new MenuItem("&Centers", new EventHandler(this.menuFormatAlignC_Click));
        menuFormatAlignR = new MenuItem("&Rights", new EventHandler(this.menuFormatAlignR_Click));
        menuFormatAlignT = new MenuItem("&Tops", new EventHandler(this.menuFormatAlignT_Click));
        menuFormatAlignM = new MenuItem("&Middles", new EventHandler(this.menuFormatAlignM_Click));
        menuFormatAlignB = new MenuItem("&Bottoms", new EventHandler(this.menuFormatAlignB_Click));
        menuFormatAlign.MenuItems.AddRange(new MenuItem[]{ menuFormatAlignL, menuFormatAlignC, menuFormatAlignR, new MenuItem("-"), menuFormatAlignT, menuFormatAlignM, menuFormatAlignB });
        menuFormatSize = new MenuItem("&Size");
        menuFormatSizeW = new MenuItem("&Width", new EventHandler(this.menuFormatSizeW_Click));
        menuFormatSizeH = new MenuItem("&Height", new EventHandler(this.menuFormatSizeH_Click));
        menuFormatSizeB = new MenuItem("&Both", new EventHandler(this.menuFormatSizeB_Click));
        menuFormatSize.MenuItems.AddRange(new MenuItem[]{ menuFormatSizeW, menuFormatSizeH, menuFormatSizeB });
        menuFormatHorz = new MenuItem("&Horizontal Spacing");
        menuFormatHorzE = new MenuItem("&Make Equal", new EventHandler(this.menuFormatHorzE_Click));
        menuFormatHorzI = new MenuItem("&Increase", new EventHandler(this.menuFormatHorzI_Click));
        menuFormatHorzD = new MenuItem("&Decrease", new EventHandler(this.menuFormatHorzD_Click));
        menuFormatHorzZ = new MenuItem("&Zero", new EventHandler(this.menuFormatHorzZ_Click));
        menuFormatHorz.MenuItems.AddRange(new MenuItem[]{ menuFormatHorzE, menuFormatHorzI, menuFormatHorzD, menuFormatHorzZ });
        menuFormatVert = new MenuItem("&Vertical Spacing");
        menuFormatVertE = new MenuItem("&Make Equal", new EventHandler(this.menuFormatVertE_Click));
        menuFormatVertI = new MenuItem("&Increase", new EventHandler(this.menuFormatVertI_Click));
        menuFormatVertD = new MenuItem("&Decrease", new EventHandler(this.menuFormatVertD_Click));
        menuFormatVertZ = new MenuItem("&Zero", new EventHandler(this.menuFormatVertZ_Click));
        menuFormatVert.MenuItems.AddRange(new MenuItem[]{ menuFormatVertE, menuFormatVertI, menuFormatVertD, menuFormatVertZ });
        menuFormatPaddingLeft = new MenuItem("Padding Left");
        menuFormatPaddingLeftI = new MenuItem("Increase", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingLeftD = new MenuItem("Decrease", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingLeftZ = new MenuItem("Zero", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingLeft.MenuItems.AddRange(new MenuItem[]{ menuFormatPaddingLeftI, menuFormatPaddingLeftD, menuFormatPaddingLeftZ });
        menuFormatPaddingRight = new MenuItem("Padding Right");
        menuFormatPaddingRightI = new MenuItem("Increase", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingRightD = new MenuItem("Decrease", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingRightZ = new MenuItem("Zero", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingRight.MenuItems.AddRange(new MenuItem[]{ menuFormatPaddingRightI, menuFormatPaddingRightD, menuFormatPaddingRightZ });
        menuFormatPaddingTop = new MenuItem("Padding Top");
        menuFormatPaddingTopI = new MenuItem("Increase", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingTopD = new MenuItem("Decrease", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingTopZ = new MenuItem("Zero", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingTop.MenuItems.AddRange(new MenuItem[]{ menuFormatPaddingTopI, menuFormatPaddingTopD, menuFormatPaddingTopZ });
        menuFormatPaddingBottom = new MenuItem("Padding Bottom");
        menuFormatPaddingBottomI = new MenuItem("Increase", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingBottomD = new MenuItem("Decrease", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingBottomZ = new MenuItem("Zero", new EventHandler(this.menuFormatPadding_Click));
        menuFormatPaddingBottom.MenuItems.AddRange(new MenuItem[]{ menuFormatPaddingBottomI, menuFormatPaddingBottomD, menuFormatPaddingBottomZ });
        menuFormat = new MenuItem("F&ormat");
        menuFormat.Popup += new EventHandler(menuFormat_Popup);
        menuFormat.MenuItems.AddRange(new MenuItem[]{ menuFormatAlign, menuFormatSize, menuFormatHorz, menuFormatVert, new MenuItem("-"), menuFormatPaddingLeft, menuFormatPaddingRight, menuFormatPaddingTop, menuFormatPaddingBottom });
        // Tools menu
        this.menuToolsProcess = new MenuItem("Start Desktop Server", new EventHandler(this.menuToolsProcess_Click));
        this.menuToolsValidateSchema = new MenuItem("Validate RDL", new EventHandler(this.menuToolsValidateSchema_Click));
        this.menuToolsOptions = new MenuItem("Options...", new EventHandler(this.menuToolsOptions_Click));
        menuTools = new MenuItem("&Tools");
        menuTools.MenuItems.AddRange(new MenuItem[]{ menuToolsValidateSchema, new MenuItem("-"), menuToolsProcess, new MenuItem("-"), menuToolsOptions });
        menuTools.Popup += new EventHandler(this.menuTools_Popup);
        // WINDOW MENU
        menuCascade = new MenuItem("&Cascade", new EventHandler(this.menuWndCascade_Click), Shortcut.CtrlShiftJ);
        menuTileH = new MenuItem("&Horizontally", new EventHandler(this.menuWndTileH_Click), Shortcut.CtrlShiftK);
        menuTileV = new MenuItem("&Vertically", new EventHandler(this.menuWndTileV_Click), Shortcut.CtrlShiftL);
        menuTile = new MenuItem("&Tile");
        menuTile.MenuItems.AddRange(new MenuItem[]{ menuTileH, menuTileV });
        menuCloseAll = new MenuItem("Close &All", new EventHandler(this.menuWndCloseAll_Click), Shortcut.CtrlShiftW);
        // Add the Window menu
        MenuItem menuWindow = new MenuItem("&Window");
        menuWindow.Popup += new EventHandler(this.menuWnd_Popup);
        menuWindow.MdiList = true;
        menuWindow.MenuItems.AddRange(new MenuItem[]{ menuCascade, menuTile, menuCloseAll });
        // HELP MENU
        MenuItem menuAbout = new MenuItem("&About...", new EventHandler(this.menuHelpAbout_Click));
        MenuItem menuHelpItem = new MenuItem("&Help", new EventHandler(this.menuHelpHelp_Click));
        MenuItem menuSupport = new MenuItem("&Support", new EventHandler(this.menuHelpSupport_Click));
        MenuItem menuHelp = new MenuItem("&Help");
        menuHelp.MenuItems.AddRange(new MenuItem[]{ menuHelpItem, new MenuItem("-"), menuSupport, new MenuItem("-"), menuAbout });
        MainMenu menuMain = new MainMenu(new MenuItem[]{ menuFile, menuEdit, menuView, menuData, menuFormat, menuTools, menuWindow, menuHelp });
        IsMdiContainer = true;
        this.Menu = menuMain;
    }

    private boolean okToSave() throws Exception {
        for (Object __dummyForeachVar4 : this.MdiChildren)
        {
            MDIChild mc = (MDIChild)__dummyForeachVar4;
            if (!mc.okToClose())
                return false;
             
        }
        return true;
    }

    private void menuFile_Popup(Object sender, EventArgs e) throws Exception {
        // These menus require an MDIChild in order to work
        boolean bEnable = this.MdiChildren.Length > 0 ? true : false;
        menuClose.Enabled = bEnable;
        menuSave.Enabled = bEnable;
        menuSaveAs.Enabled = bEnable;
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        menuPrint.Enabled = menuExport.Enabled = (mc != null && StringSupport.equals(mc.getDesignTab(), "preview"));
        // Recent File is enabled when there exists some files
        menuRecentFile.Enabled = this._RecentFiles.Count <= 0 ? false : true;
    }

    private void menuFileClose_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.Close();
         
    }

    private void menuFileExit_Click(Object sender, EventArgs e) throws Exception {
        if (!okToSave())
            return ;
         
        saveStartupState();
        menuToolsCloseProcess(false);
        cleanupTempFiles();
        Environment.Exit(0);
    }

    private void menuFileOpen_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        OpenFileDialog ofd = new OpenFileDialog();
        if (mc != null)
        {
            try
            {
                ofd.InitialDirectory = Path.GetDirectoryName(mc.getSourceFile());
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
         
        ofd.DefaultExt = "rdl";
        ofd.Filter = "Report files (*.rdl)|*.rdl|" + "All files (*.*)|*.*";
        ofd.FilterIndex = 1;
        ofd.CheckFileExists = true;
        ofd.Multiselect = true;
        if (ofd.ShowDialog(this) == DialogResult.OK)
        {
            for (Object __dummyForeachVar5 : ofd.FileNames)
            {
                String file = (String)__dummyForeachVar5;
                createMDIChild(file,null,false);
            }
            recentFilesMenu();
        }
         
    }

    // update the menu for recent files
    // Create an MDI child.   Only creates it if not already open.
    private MDIChild createMDIChild(String file, String rdl, boolean bMenuUpdate) throws Exception {
        MDIChild mcOpen = null;
        if (file != null)
        {
            file = file.Trim();
            for (Object __dummyForeachVar6 : this.MdiChildren)
            {
                MDIChild mc = (MDIChild)__dummyForeachVar6;
                if (mc.getSourceFile() != null && StringSupport.equals(file, mc.getSourceFile().Trim()))
                {
                    // we found it
                    mcOpen = mc;
                    break;
                }
                 
            }
        }
         
        if (mcOpen == null)
        {
            MDIChild mc = null;
            try
            {
                mc = new MDIChild(this.ClientRectangle.Width * 3 / 5, this.ClientRectangle.Height * 3 / 5);
                mc.OnSelectionChanged = __MultiRdlChangeHandler.combine(mc.OnSelectionChanged,new fyiReporting.RdlDesign.MDIChild.RdlChangeHandler() 
                  { 
                    public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                        selectionChanged(sender, e);
                    }

                    public List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.OnSelectionMoved = __MultiRdlChangeHandler.combine(mc.OnSelectionMoved,new fyiReporting.RdlDesign.MDIChild.RdlChangeHandler() 
                  { 
                    public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                        selectionMoved(sender, e);
                    }

                    public List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.OnReportItemInserted = __MultiRdlChangeHandler.combine(mc.OnReportItemInserted,new fyiReporting.RdlDesign.MDIChild.RdlChangeHandler() 
                  { 
                    public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                        reportItemInserted(sender, e);
                    }

                    public List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.OnDesignTabChanged = __MultiRdlChangeHandler.combine(mc.OnDesignTabChanged,new fyiReporting.RdlDesign.MDIChild.RdlChangeHandler() 
                  { 
                    public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                        designTabChanged(sender, e);
                    }

                    public List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.MDIChild.RdlChangeHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.OnOpenSubreport = __MultiOpenSubreportEventHandler.combine(mc.OnOpenSubreport,new fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler() 
                  { 
                    public System.Void invoke(System.Object source, SubReportEventArgs e) throws Exception {
                        openSubReportEvent(source, e);
                    }

                    public List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.OnHeightChanged = __MultiHeightEventHandler.combine(mc.OnHeightChanged,new fyiReporting.RdlDesign.DesignCtl.HeightEventHandler() 
                  { 
                    public System.Void invoke(System.Object source, HeightEventArgs e) throws Exception {
                        heightChanged(source, e);
                    }

                    public List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> getInvocationList() throws Exception {
                        List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler>();
                        ret.add(this);
                        return ret;
                    }
                
                  });
                mc.MdiParent = this;
                if (this._ShowTabbedInterface)
                    mc.WindowState = FormWindowState.Maximized;
                 
                mc.getViewer().GetDataSourceReferencePassword = _GetPassword;
                if (file != null)
                {
                    mc.getViewer().setFolder(Path.GetDirectoryName(file));
                    mc.setSourceFile(file);
                    mc.Text = Path.GetFileName(file);
                    mc.getViewer().setFolder(Path.GetDirectoryName(file));
                    mc.getViewer().setReportName(Path.GetFileNameWithoutExtension(file));
                    noteRecentFiles(file,bMenuUpdate);
                }
                else
                {
                    mc.setSourceRdl(rdl);
                    mc.getViewer().setReportName(mc.Text = "Untitled");
                } 
                mc.showEditLines(this._ShowEditLines);
                mc.setShowReportItemOutline(this.getShowReportItemOutline());
                // add to toolbar tab
                TabPage tp = new TabPage();
                tp.Location = new System.Drawing.Point(0, 0);
                tp.Name = mc.Text;
                tp.TabIndex = 1;
                tp.Text = mc.Text;
                tp.Tag = mc;
                // tie the mdichild to the tabpage
                tp.ToolTipText = file;
                mainTC.Controls.Add(tp);
                mc.setTab(tp);
                mc.Show();
                mcOpen = mc;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                if (mc != null)
                    mc.Close();
                 
                return null;
            }
        
        }
        else
        {
            mcOpen.Activate();
        } 
        return mcOpen;
    }

    private void designTabChanged(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        String tab = "";
        if (mc != null)
            tab = mc.getDesignTab();
         
        boolean bEnableEdit = false;
        boolean bEnableDesign = false;
        boolean bEnablePreview = false;
        System.String __dummyScrutVar1 = tab;
        if (__dummyScrutVar1.equals("edit"))
        {
            bEnableEdit = true;
        }
        else if (__dummyScrutVar1.equals("design"))
        {
            bEnableDesign = true;
        }
        else if (__dummyScrutVar1.equals("preview"))
        {
            bEnablePreview = true;
        }
           
        if (!bEnableEdit && this._ValidateRdl != null)
        {
            this._ValidateRdl.Close();
        }
         
        if (ctlBold != null)
            ctlBold.Enabled = bEnableDesign;
         
        if (ctlItalic != null)
            ctlItalic.Enabled = bEnableDesign;
         
        if (ctlUnderline != null)
            ctlUnderline.Enabled = bEnableDesign;
         
        if (ctlFont != null)
            ctlFont.Enabled = bEnableDesign;
         
        if (ctlFontSize != null)
            ctlFontSize.Enabled = bEnableDesign;
         
        if (ctlForeColor != null)
            ctlForeColor.Enabled = bEnableDesign;
         
        if (ctlBackColor != null)
            ctlBackColor.Enabled = bEnableDesign;
         
        if (ctlCut != null)
            ctlCut.Enabled = bEnableDesign | bEnableEdit;
         
        if (ctlCopy != null)
            ctlCopy.Enabled = bEnableDesign | bEnableEdit;
         
        if (ctlUndo != null)
            ctlUndo.Enabled = bEnableDesign | bEnableEdit;
         
        if (ctlPaste != null)
            ctlPaste.Enabled = bEnableDesign | bEnableEdit;
         
        if (ctlPrint != null)
            ctlPrint.Enabled = bEnablePreview;
         
        if (ctlInsertTextbox != null)
            ctlInsertTextbox.Enabled = bEnableDesign;
         
        if (ctlInsertChart != null)
            ctlInsertChart.Enabled = bEnableDesign;
         
        if (ctlInsertRectangle != null)
            ctlInsertRectangle.Enabled = bEnableDesign;
         
        if (ctlInsertTable != null)
            ctlInsertTable.Enabled = bEnableDesign;
         
        if (ctlInsertMatrix != null)
            ctlInsertMatrix.Enabled = bEnableDesign;
         
        if (ctlInsertList != null)
            ctlInsertList.Enabled = bEnableDesign;
         
        if (ctlInsertLine != null)
            ctlInsertLine.Enabled = bEnableDesign;
         
        if (ctlInsertImage != null)
            ctlInsertImage.Enabled = bEnableDesign;
         
        if (ctlInsertSubreport != null)
            ctlInsertSubreport.Enabled = bEnableDesign;
         
        if (ctlPdf != null)
            ctlPdf.Enabled = bEnablePreview;
         
        if (ctlXml != null)
            ctlXml.Enabled = bEnablePreview;
         
        if (ctlHtml != null)
            ctlHtml.Enabled = bEnablePreview;
         
        if (ctlMht != null)
            ctlMht.Enabled = bEnablePreview;
         
        this.enableEditTextBox();
        if (ctlZoom != null)
        {
            ctlZoom.Enabled = bEnablePreview;
            String zText = "Actual Size";
            if (mc != null)
            {
                switch(mc.getZoomMode())
                {
                    case FitWidth: 
                        zText = "Fit Width";
                        break;
                    case FitPage: 
                        zText = "Fit Page";
                        break;
                    case UseZoom: 
                        if (mc.getZoom() == 1)
                            zText = "Actual Size";
                        else
                            zText = String.Format("{0:0}", mc.getZoom() * 100f); 
                        break;
                
                }
                ctlZoom.Text = zText;
            }
             
        }
         
        // when no active sheet
        if (this.ctlSave != null)
            this.ctlSave.Enabled = mc != null;
         
        // Update the status and position information
        setStatusNameAndPosition();
    }

    private void enableEditTextBox() throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        boolean bEnable = false;
        if (this.ctlEditTextbox == null || mc == null || !StringSupport.equals(mc.getDesignTab(), "design") || mc.getDrawCtl().getSelectedCount() != 1)
        {
        }
        else
        {
            XmlNode tn = mc.getDrawCtl().getSelectedList()[0] instanceof XmlNode ? (XmlNode)mc.getDrawCtl().getSelectedList()[0] : (XmlNode)null;
            if (tn != null && StringSupport.equals(tn.Name, "Textbox"))
            {
                ctlEditTextbox.Text = mc.getDrawCtl().getElementValue(tn,"Value","");
                bEnable = true;
            }
             
        } 
        if (ctlEditTextbox != null)
        {
            if (!bEnable)
                ctlEditTextbox.Text = "";
             
            ctlEditTextbox.Enabled = bEnable;
            ctlEditLabel.Enabled = bEnable;
        }
         
    }

    private void reportItemInserted(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        // turn off the current selection after an item is inserted
        if (ctlInsertCurrent != null)
        {
            ctlInsertCurrent.Checked = false;
            mc.setCurrentInsert(null);
            ctlInsertCurrent = null;
        }
         
    }

    private void openSubReportEvent(Object sender, SubReportEventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        String file = mc.getViewer().getFolder();
        if (e.getSubReportName()[0] == Path.DirectorySeparatorChar)
            file = file + e.getSubReportName();
        else
            file = file + Path.DirectorySeparatorChar + e.getSubReportName() + ".rdl"; 
        createMDIChild(file,null,true);
    }

    private void heightChanged(Object sender, HeightEventArgs e) throws Exception {
        if (e.getHeight() == null)
        {
            statusPosition.Text = "";
            return ;
        }
         
        RegionInfo rinfo = new RegionInfo(CultureInfo.CurrentCulture.LCID);
        float h = DesignXmlDraw.getSize(e.getHeight());
        String sh = new String();
        if (rinfo.IsMetric)
        {
            sh = String.Format("   height={0:0.00}cm        ", h / (DesignXmlDraw.POINTSIZED / 2.54d));
        }
        else
        {
            sh = String.Format("   height={0:0.00}\"        ", h / DesignXmlDraw.POINTSIZED);
        } 
        statusPosition.Text = sh;
    }

    private void selectionMoved(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        setStatusNameAndPosition();
    }

    private void selectionChanged(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        // handle edit tab first
        if (StringSupport.equals(mc.getRdlEditor().getDesignTab(), "edit"))
        {
            setStatusNameAndPosition();
            return ;
        }
         
        bSuppressChange = true;
        // don't process changes in status bar
        setStatusNameAndPosition();
        this.enableEditTextBox();
        // handling enabling/disabling of textbox
        StyleInfo si = mc.getSelectedStyle();
        if (si == null)
            return ;
         
        if (ctlBold != null)
            ctlBold.Checked = si.isFontBold() ? true : false;
         
        if (ctlItalic != null)
            ctlItalic.Checked = si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic ? true : false;
         
        if (ctlUnderline != null)
            ctlUnderline.Checked = si.TextDecoration == TextDecorationEnum.Underline ? true : false;
         
        if (ctlFont != null)
            ctlFont.Text = si.FontFamily;
         
        if (ctlFontSize != null)
        {
            String rs = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.#}", si.FontSize);
            ctlFontSize.Text = rs;
        }
         
        if (ctlForeColor != null)
            ctlForeColor.Text = ColorTranslator.ToHtml(si.Color);
         
        if (ctlBackColor != null)
            ctlBackColor.Text = ColorTranslator.ToHtml(si.BackgroundColor);
         
        bSuppressChange = false;
    }

    private void menuData_Popup(Object sender, EventArgs ea) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        boolean bEnable = false;
        if (mc != null && StringSupport.equals(mc.getDesignTab(), "design"))
            bEnable = true;
         
        this.menuDataSources.Enabled = this.menuDataSets.Enabled = this.menuEmbeddedImages.Enabled = bEnable;
        if (!bEnable)
            return ;
         
        // Run thru all the existing DataSets
        menuDataSets.MenuItems.Clear();
        menuDataSets.MenuItems.Add(new MenuItem("New...", new EventHandler(this.menuDataSets_Click)));
        DesignXmlDraw draw = mc.getDrawCtl();
        XmlNode rNode = draw.getReportNode();
        XmlNode dsNode = draw.getNamedChildNode(rNode,"DataSets");
        if (dsNode != null)
        {
            for (Object __dummyForeachVar7 : dsNode)
            {
                XmlNode dNode = (XmlNode)__dummyForeachVar7;
                if (!StringSupport.equals(dNode.Name, "DataSet"))
                    continue;
                 
                XmlAttribute nAttr = dNode.Attributes["Name"];
                if (nAttr == null)
                    continue;
                 
                // shouldn't really happen
                menuDataSets.MenuItems.Add(new MenuItem(nAttr.Value, new EventHandler(this.menuDataSets_Click)));
            }
        }
         
    }

    private void menuDataSources_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().startUndoGroup("DataSources Dialog");
        DialogDataSources dlgDS = new DialogDataSources(mc.getDrawCtl());
        dlgDS.StartPosition = FormStartPosition.CenterParent;
        DialogResult dr = dlgDS.ShowDialog();
        mc.getEditor().endUndoGroup(dr == DialogResult.OK);
        if (dr == DialogResult.OK)
            mc.setModified(true);
         
    }

    private void menuDataSets_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null || mc.getDrawCtl() == null || mc.getReportDocument() == null)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        mc.getEditor().startUndoGroup("DataSet Dialog");
        String dsname = menu.Text;
        // Find the dataset we need
        List<XmlNode> ds = new List<XmlNode>();
        DesignXmlDraw draw = mc.getDrawCtl();
        XmlNode rNode = draw.getReportNode();
        XmlNode dsNode = draw.getCreateNamedChildNode(rNode,"DataSets");
        XmlNode dataset = null;
        // find the requested dataset: the menu text equals the name of the dataset
        int dsCount = 0;
        // count of the datasets
        String onlyOneDsname = null;
        for (Object __dummyForeachVar8 : dsNode)
        {
            XmlNode dNode = (XmlNode)__dummyForeachVar8;
            if (!StringSupport.equals(dNode.Name, "DataSet"))
                continue;
             
            XmlAttribute nAttr = dNode.Attributes["Name"];
            if (nAttr == null)
                continue;
             
            // shouldn't really happen
            if (dsCount == 0)
                onlyOneDsname = nAttr.Value;
             
            // we keep track of 1st name;
            dsCount++;
            if (StringSupport.equals(nAttr.Value, dsname))
                dataset = dNode;
             
        }
        boolean bNew = false;
        if (dataset == null)
        {
            // This must be the new menu item
            dataset = draw.createElement(dsNode,"DataSet",null);
            // create empty node
            bNew = true;
        }
         
        ds.Add(dataset);
        PropertyDialog pd = new PropertyDialog(mc.getDrawCtl(), ds, PropertyTypeEnum.DataSets);
        DialogResult dr = pd.ShowDialog();
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            if (dsCount == 1)
            {
                // if we used to just have one DataSet we may need to fix up DataRegions
                //	that were defaulting to that name
                dsCount = 0;
                boolean bUseName = false;
                for (Object __dummyForeachVar9 : dsNode)
                {
                    XmlNode dNode = (XmlNode)__dummyForeachVar9;
                    if (!StringSupport.equals(dNode.Name, "DataSet"))
                        continue;
                     
                    XmlAttribute nAttr = dNode.Attributes["Name"];
                    if (nAttr == null)
                        continue;
                     
                    // shouldn't really happen
                    dsCount++;
                    if (StringSupport.equals(onlyOneDsname, nAttr.Value))
                        bUseName = true;
                     
                }
                if (bUseName && dsCount > 1)
                {
                    for (Object __dummyForeachVar10 : draw.getReportNames().getReportItems())
                    {
                        XmlNode drNode = (XmlNode)__dummyForeachVar10;
                        Name __dummyScrutVar3 = drNode.Name;
                        // If a DataRegion doesn't have a dataset name specified use previous one
                        if (__dummyScrutVar3.equals("Table") || __dummyScrutVar3.equals("List") || __dummyScrutVar3.equals("Matrix") || __dummyScrutVar3.equals("Chart"))
                        {
                            XmlNode aNode = draw.getNamedChildNode(drNode,"DataSetName");
                            if (aNode == null)
                                draw.createElement(drNode,"DataSetName",onlyOneDsname);
                             
                        }
                        else
                        {
                        } 
                    }
                }
                 
            }
             
            mc.setModified(true);
        }
        else if (bNew)
        {
            // if canceled and new DataSet get rid of temp node
            dsNode.RemoveChild(dataset);
        }
          
        if (pd.getDelete())
            // user must have hit a delete button for this to get set
            dsNode.RemoveChild(dataset);
         
        if (!dsNode.HasChildNodes)
            // If no dataset exists we remove DataSets
            draw.removeElement(rNode,"DataSets");
         
        mc.getEditor().endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
    }

    private void menuEmbeddedImages_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().startUndoGroup("Embedded Images Dialog");
        DialogEmbeddedImages dlgEI = new DialogEmbeddedImages(mc.getDrawCtl());
        dlgEI.StartPosition = FormStartPosition.CenterParent;
        DialogResult dr = dlgEI.ShowDialog();
        mc.getEditor().endUndoGroup(dr == DialogResult.OK);
        if (dr == DialogResult.OK)
            mc.setModified(true);
         
    }

    private void menuFileNewDataSourceRef_Click(Object sender, System.EventArgs e) throws Exception {
        DialogDataSourceRef dlgDS = new DialogDataSourceRef();
        dlgDS.StartPosition = FormStartPosition.CenterParent;
        dlgDS.ShowDialog();
        if (dlgDS.DialogResult == DialogResult.Cancel)
            return ;
         
    }

    private void menuFileNewReport_Click(Object sender, System.EventArgs e) throws Exception {
        DialogDatabase dlgDB = new DialogDatabase(this);
        dlgDB.StartPosition = FormStartPosition.CenterParent;
        dlgDB.FormBorderStyle = FormBorderStyle.SizableToolWindow;
        // show modally
        dlgDB.ShowDialog();
        if (dlgDB.DialogResult == DialogResult.Cancel)
            return ;
         
        String rdl = dlgDB.getResultReport();
        // Create the MDI child using the RDL syntax the wizard generates
        MDIChild mc = createMDIChild(null,rdl,false);
        mc.setModified(true);
        // Force building of report names for new reports
        if (mc.getDrawCtl().getReportNames() == null)
        {
        }
         
    }

    private void menuFilePrint_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (printChild != null)
        {
            // already printing
            MessageBox.Show("Can only print one file at a time.", "RDL Design");
            return ;
        }
         
        printChild = mc;
        PrintDocument pd = new PrintDocument();
        pd.DocumentName = mc.getSourceFile();
        pd.PrinterSettings.FromPage = 1;
        pd.PrinterSettings.ToPage = mc.getPageCount();
        pd.PrinterSettings.MaximumPage = mc.getPageCount();
        pd.PrinterSettings.MinimumPage = 1;
        pd.DefaultPageSettings.Landscape = mc.getPageWidth() > mc.getPageHeight() ? true : false;
        PrintDialog dlg = new PrintDialog();
        dlg.Document = pd;
        dlg.AllowSelection = true;
        dlg.AllowSomePages = true;
        if (dlg.ShowDialog() == DialogResult.OK)
        {
            try
            {
                if (pd.PrinterSettings.PrintRange == PrintRange.Selection)
                {
                    pd.PrinterSettings.FromPage = mc.getPageCurrent();
                }
                 
                mc.print(pd);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Print error: " + ex.Message, "RDL Design");
            }
        
        }
         
        printChild = null;
    }

    private void menuFileSave_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!mc.fileSave())
            return ;
         
        noteRecentFiles(mc.getSourceFile(),true);
        if (mc.getEditor() != null)
            mc.getEditor().clearUndo();
         
        return ;
    }

    private void menuExportXml_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.export("xml");
        return ;
    }

    private void menuExportHtml_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.export("html");
        return ;
    }

    private void menuExportMHtml_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.export("mht");
        return ;
    }

    private void menuExportPdf_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.export("pdf");
        return ;
    }

    private void menuFileSaveAs_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!mc.fileSaveAs())
            return ;
         
        mc.getViewer().setFolder(Path.GetDirectoryName(mc.getSourceFile()));
        mc.getViewer().setReportName(Path.GetFileNameWithoutExtension(mc.getSourceFile()));
        mc.Text = Path.GetFileName(mc.getSourceFile());
        noteRecentFiles(mc.getSourceFile(),true);
        if (mc.getEditor() != null)
            mc.getEditor().clearUndo();
         
        return ;
    }

    private void menuEdit_Popup(Object sender, EventArgs ea) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        // These menus require an MDIChild in order to work
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        menuEdit.MenuItems.Clear();
        if (e == null || !StringSupport.equals(e.getDesignTab(), "edit"))
        {
            menuEditUndo.Text = e == null ? "Undo" : "Undo " + e.getUndoDescription();
            /* menuEditRedo,*/
            menuEdit.MenuItems.AddRange(new MenuItem[]{ menuEditUndo, menuFSep1, menuEditCut, menuEditCopy, menuEditPaste, menuEditDelete, menuFSep2, menuEditSelectAll });
            if (mc == null || e == null)
            {
                menuEditUndo.Enabled = menuEditRedo.Enabled = menuEditCut.Enabled = menuEditCopy.Enabled = menuEditPaste.Enabled = menuEditDelete.Enabled = menuEditSelectAll.Enabled = false;
                return ;
            }
             
        }
        else
        {
            menuEditUndo.Text = "Undo";
            menuEdit.MenuItems.AddRange(new MenuItem[]{ menuEditUndo, menuEditRedo, menuFSep1, menuEditCut, menuEditCopy, menuEditPaste, menuEditDelete, menuFSep2, menuEditSelectAll, menuFSep3, menuEditFind, menuEditFindNext, menuEditReplace, menuEditGoto, menuFSep4, menuEditFormatXml });
        } 
        menuEditUndo.Enabled = e.getCanUndo();
        menuEditRedo.Enabled = e.getCanRedo();
        boolean bSelection = e.getSelectionLength() > 0;
        // any text selected?
        menuEditCut.Enabled = bSelection;
        menuEditCopy.Enabled = bSelection;
        menuEditPaste.Enabled = Clipboard.GetDataObject().GetDataPresent(DataFormats.Text);
        menuEditDelete.Enabled = bSelection;
        menuEditSelectAll.Enabled = true;
        boolean bAnyText = e.getText().Length > 0;
        // any text to search at all?
        menuEditFind.Enabled = menuEditFindNext.Enabled = menuEditReplace.Enabled = menuEditGoto.Enabled = bAnyText;
    }

    private void menuEditUndo_Click(Object sender, System.EventArgs ea) throws Exception {
        if (this.ctlEditTextbox != null && ctlEditTextbox.Focused)
        {
            ctlEditTextbox.Undo();
            return ;
        }
         
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getCanUndo() == true)
        {
            e.undo();
            MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
            if (mc != null && StringSupport.equals(mc.getDesignTab(), "design"))
            {
                e.getDesignCtl().setScrollControls();
            }
             
            this.SelectionChanged(this, new EventArgs());
        }
         
    }

    private void menuEditRedo_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getCanRedo() == true)
        {
            e.redo();
        }
         
    }

    private void menuEditCut_Click(Object sender, System.EventArgs ea) throws Exception {
        if (this.ctlEditTextbox != null && ctlEditTextbox.Focused)
        {
            ctlEditTextbox.Cut();
            return ;
        }
         
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getSelectionLength() > 0)
            e.cut();
         
    }

    private void menuEditCopy_Click(Object sender, System.EventArgs ea) throws Exception {
        if (this.ctlEditTextbox != null && ctlEditTextbox.Focused)
        {
            ctlEditTextbox.Copy();
            return ;
        }
         
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getSelectionLength() > 0)
            e.copy();
         
    }

    private void menuEditPaste_Click(Object sender, System.EventArgs ea) throws Exception {
        if (this.ctlEditTextbox != null && ctlEditTextbox.Focused)
        {
            ctlEditTextbox.Paste();
            return ;
        }
         
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (Clipboard.GetDataObject().GetDataPresent(DataFormats.Text) == true || Clipboard.GetDataObject().GetDataPresent(DataFormats.Bitmap) == true)
            e.paste();
         
    }

    private void menuEditDelete_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getSelectionLength() > 0)
            e.setSelectedText("");
         
    }

    private void menuEditProperties_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        e.getDesignCtl().menuProperties_Click();
    }

    private void menuEditSelectAll_Click(Object sender, System.EventArgs ea) throws Exception {
        if (this.ctlEditTextbox != null && ctlEditTextbox.Focused)
        {
            ctlEditTextbox.SelectAll();
            return ;
        }
         
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        e.selectAll();
    }

    private void menuEditFind_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        FindTab tab = new FindTab(e);
        tab.Show();
    }

    private void menuEditFindNext_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        FindTab tab = new FindTab(e);
        tab.Show();
    }

    private void menuEdit_FormatXml(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        if (e.getText().Length > 0)
        {
            try
            {
                e.setText(DesignerUtility.formatXml(e.getText()));
                e.setModified(true);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Format XML");
            }
        
        }
         
    }

    private void menuEditReplace_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        FindTab tab = new FindTab(e);
        tab.tcFRG.SelectedTab = tab.tabReplace;
        tab.Show();
    }

    private void menuEditGoto_Click(Object sender, System.EventArgs ea) throws Exception {
        fyiReporting.RdlDesign.RdlEditPreview e = getEditor();
        if (e == null)
            return ;
         
        FindTab tab = new FindTab(e);
        tab.tcFRG.SelectedTab = tab.tabGoTo;
        tab.Show();
    }

    private void menuHelpAbout_Click(Object sender, System.EventArgs ea) throws Exception {
        DialogAbout dlg = new DialogAbout();
        dlg.ShowDialog();
    }

    private void menuHelpHelp_Click(Object sender, System.EventArgs ea) throws Exception {
        try
        {
            System.Diagnostics.Process.Start(getHelpUrl());
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message + "\n\n" + "Resetting Help URL to default.", "Help URL Invalid");
            _HelpUrl = DefaultHelpUrl;
        }
    
    }

    private void menuHelpSupport_Click(Object sender, System.EventArgs ea) throws Exception {
        try
        {
            System.Diagnostics.Process.Start(getSupportUrl());
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message + "\n\n" + "Resetting Support URL to default.", "Support URL Invalid");
            _SupportUrl = DefaultSupportUrl;
        }
    
    }

    public fyiReporting.RdlDesign.RdlEditPreview getEditor() throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return null;
         
        return mc.getEditor();
    }

    private void menuTools_Popup(Object sender, EventArgs e) throws Exception {
        // If the server process isn't running then we'll start it up
        if (_ServerProcess != null && _ServerProcess.HasExited)
            _ServerProcess = null;
         
        menuToolsProcess.Text = this._ServerProcess == null ? "Start Desktop" : "Stop Desktop";
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        this.menuToolsValidateSchema.Enabled = (mc != null && StringSupport.equals(mc.getDesignTab(), "edit"));
    }

    private void menuToolsProcess_Click(Object sender, EventArgs e) throws Exception {
        if (_ServerProcess == null)
            menuToolsStartProcess(true);
        else
            menuToolsCloseProcess(true); 
    }

    public void menuToolsStartProcess(boolean bMsg) throws Exception {
        if (_ServerProcess != null && !_ServerProcess.HasExited)
            return ;
         
        String pswd = getPassword();
        try
        {
            String filename = String.Format("{0}{1}", AppDomain.CurrentDomain.BaseDirectory, "RdlDesktop.exe");
            ProcessStartInfo psi = new ProcessStartInfo(filename);
            if (pswd != null)
                psi.Arguments = "/p" + pswd;
             
            psi.RedirectStandardError = psi.RedirectStandardInput = psi.RedirectStandardOutput = true;
            psi.UseShellExecute = false;
            //psi.WindowStyle = ProcessWindowStyle.Hidden;
            psi.CreateNoWindow = true;
            _ServerProcess = Process.Start(psi);
        }
        catch (Exception ex)
        {
            if (bMsg)
                MessageBox.Show(ex.Message, "Unable to start Desktop");
             
        }

        return ;
    }

    public void menuToolsCloseProcess(boolean bMsg) throws Exception {
        if (_ServerProcess == null)
            return ;
         
        if (!_ServerProcess.HasExited)
        {
            try
            {
                _ServerProcess.StandardInput.WriteLine("x");
            }
            catch (Exception ex)
            {
                // x stops the server
                if (bMsg)
                    MessageBox.Show(ex.Message, "Error stopping process");
                 
            }
        
        }
         
        _ServerProcess = null;
    }

    private void menuToolsOptions_Click(Object sender, EventArgs e) throws Exception {
        DialogToolOptions dlg = new DialogToolOptions(this);
        DialogResult rc = dlg.ShowDialog();
    }

    private void menuToolsValidateSchema_Click(Object sender, EventArgs e) throws Exception {
        if (_ValidateRdl == null)
        {
            _ValidateRdl = new DialogValidateRdl(this);
            _ValidateRdl.Show();
        }
        else
            _ValidateRdl.BringToFront(); 
        return ;
    }

    public void validateSchemaClosing() throws Exception {
        this._ValidateRdl = null;
    }

    private void menuWnd_Popup(Object sender, EventArgs e) throws Exception {
        // These menus require an MDIChild in order to work
        boolean bEnable = this.MdiChildren.Length > 0 ? true : false;
        menuCascade.Enabled = bEnable;
        menuTile.Enabled = bEnable;
        menuCloseAll.Enabled = bEnable;
    }

    private void menuWndCascade_Click(Object sender, EventArgs e) throws Exception {
        this.LayoutMdi(MdiLayout.Cascade);
    }

    private void menuWndCloseAll_Click(Object sender, EventArgs e) throws Exception {
        for (Object __dummyForeachVar11 : this.MdiChildren)
        {
            Form f = (Form)__dummyForeachVar11;
            f.Close();
        }
    }

    private void menuWndTileH_Click(Object sender, EventArgs e) throws Exception {
        this.LayoutMdi(MdiLayout.TileHorizontal);
    }

    private void menuWndTileV_Click(Object sender, EventArgs e) throws Exception {
        this.LayoutMdi(MdiLayout.TileVertical);
    }

    private void menuRecentItem_Click(Object sender, System.EventArgs e) throws Exception {
        MenuItem m = (MenuItem)sender;
        int si = m.Text.IndexOf(" ");
        String file = m.Text.Substring(si + 1);
        createMDIChild(file,null,true);
    }

    private void rdlDesigner_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        saveStartupState();
        menuToolsCloseProcess(false);
        cleanupTempFiles();
    }

    private void noteRecentFiles(String name, boolean bResetMenu) throws Exception {
        if (name == null)
            return ;
         
        name = name.Trim();
        if (_RecentFiles.ContainsValue(name))
        {
            // need to move it to top of list; so remove old one
            int loc = _RecentFiles.IndexOfValue(name);
            _RecentFiles.RemoveAt(loc);
        }
         
        if (_RecentFiles.Count >= _RecentFilesMax)
        {
            _RecentFiles.RemoveAt(0);
        }
         
        // remove the first entry
        _RecentFiles.Add(DateTime.Now, name);
        if (bResetMenu)
            recentFilesMenu();
         
        return ;
    }

    public void recentFilesMenu() throws Exception {
        menuRecentFile.MenuItems.Clear();
        int mi = 1;
        for (int i = _RecentFiles.Count - 1;i >= 0;i--)
        {
            String menuText = String.Format("&{0} {1}", mi++, _RecentFiles.Values[i]);
            MenuItem m = new MenuItem(menuText);
            m.Click += new EventHandler(this.menuRecentItem_Click);
            menuRecentFile.MenuItems.Add(m);
        }
    }

    public void resetPassword() throws Exception {
        bGotPassword = false;
        _DataSourceReferencePassword = null;
    }

    public String getPassword() throws Exception {
        if (bGotPassword)
            return _DataSourceReferencePassword;
         
        DataSourcePassword dlg = new DataSourcePassword();
        DialogResult rc = dlg.ShowDialog();
        bGotPassword = true;
        if (rc == DialogResult.OK)
            _DataSourceReferencePassword = dlg.getPassPhrase();
         
        return _DataSourceReferencePassword;
    }

    private void getStartupState() throws Exception {
        String optFileName = AppDomain.CurrentDomain.BaseDirectory + "designerstate.xml";
        _RecentFiles = new SortedList<DateTime, String>();
        _CurrentFiles = new List<String>();
        _HelpUrl = DefaultHelpUrl;
        // set as default
        _SupportUrl = DefaultSupportUrl;
        try
        {
            XmlDocument xDoc = new XmlDocument();
            xDoc.PreserveWhitespace = false;
            xDoc.Load(optFileName);
            XmlNode xNode = new XmlNode();
            xNode = xDoc.SelectSingleNode("//designerstate");
            String[] args = Environment.GetCommandLineArgs();
            for (int i = 1;i < args.Length;i++)
            {
                String larg = args[i].ToLower();
                if (StringSupport.equals(larg, "/m") || StringSupport.equals(larg, "-m"))
                    continue;
                 
                if (File.Exists(args[i]))
                    // only add it if it exists
                    _CurrentFiles.Add(args[i]);
                 
            }
            for (Object __dummyForeachVar15 : xNode.ChildNodes)
            {
                // Loop thru all the child nodes
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar15;
                Name __dummyScrutVar4 = xNodeLoop.Name;
                if (__dummyScrutVar4.equals("RecentFiles"))
                {
                    DateTime now = DateTime.Now;
                    now = now.Subtract(new TimeSpan(0, 1, 0, 0, 0));
                    for (Object __dummyForeachVar12 : xNodeLoop.ChildNodes)
                    {
                        // subtract an hour
                        XmlNode xN = (XmlNode)__dummyForeachVar12;
                        String file = xN.InnerText.Trim();
                        if (File.Exists(file))
                        {
                            // only add it if it exists
                            _RecentFiles.Add(now, file);
                            now = now.AddSeconds(1);
                        }
                         
                    }
                }
                else if (__dummyScrutVar4.equals("RecentFilesMax"))
                {
                    try
                    {
                        this._RecentFilesMax = Convert.ToInt32(xNodeLoop.InnerText);
                    }
                    catch (Exception __dummyCatchVar1)
                    {
                        this._RecentFilesMax = 5;
                    }
                
                }
                else if (__dummyScrutVar4.equals("CurrentFiles"))
                {
                    if (_CurrentFiles.Count > 0)
                        break;
                     
                    for (Object __dummyForeachVar13 : xNodeLoop.ChildNodes)
                    {
                        // don't open other current files if opened with argument
                        XmlNode xN = (XmlNode)__dummyForeachVar13;
                        String file = xN.InnerText.Trim();
                        if (File.Exists(file))
                            // only add it if it exists
                            _CurrentFiles.Add(file);
                         
                    }
                }
                else if (__dummyScrutVar4.equals("Toolbar"))
                {
                    _Toolbar = new List<String>();
                    for (Object __dummyForeachVar14 : xNodeLoop.ChildNodes)
                    {
                        XmlNode xN = (XmlNode)__dummyForeachVar14;
                        String item = xN.InnerText.Trim();
                        _Toolbar.Add(item);
                    }
                }
                else if (__dummyScrutVar4.equals("Help"))
                {
                    if (xNodeLoop.InnerText.Length > 0)
                        //empty means to use the default
                        _HelpUrl = xNodeLoop.InnerText;
                     
                }
                else if (__dummyScrutVar4.equals("Support"))
                {
                    if (xNodeLoop.InnerText.Length > 0)
                        //empty means to use the default
                        _SupportUrl = xNodeLoop.InnerText;
                     
                }
                else if (__dummyScrutVar4.equals("EditLines"))
                {
                    _ShowEditLines = (StringSupport.equals(xNodeLoop.InnerText.ToLower(), "true"));
                }
                else if (__dummyScrutVar4.equals("OutlineReportItems"))
                {
                    this.setShowReportItemOutline((StringSupport.equals(xNodeLoop.InnerText.ToLower(), "true")));
                }
                else if (__dummyScrutVar4.equals("ShowTabbedInterface"))
                {
                    this._ShowTabbedInterface = (StringSupport.equals(xNodeLoop.InnerText.ToLower(), "true"));
                }
                else
                {
                }         
            }
        }
        catch (Exception ex)
        {
            // Didn't sucessfully get the startup state but don't really care
            Console.WriteLine(String.Format("Exception in GetStartupState ignored.\n{0}\n{1}", ex.Message, ex.StackTrace));
        }

        if (_Toolbar == null)
            // Use this as the default toolbar
            _Toolbar = this.getToolbarDefault();
         
        return ;
    }

    private void saveStartupState() throws Exception {
        try
        {
            int[] colors = getCustomColors();
            // get custom colors
            XmlDocument xDoc = new XmlDocument();
            XmlProcessingInstruction xPI = new XmlProcessingInstruction();
            xPI = xDoc.CreateProcessingInstruction("xml", "version='1.0' encoding='UTF-8'");
            xDoc.AppendChild(xPI);
            XmlNode xDS = xDoc.CreateElement("designerstate");
            xDoc.AppendChild(xDS);
            XmlNode xN = new XmlNode();
            // Loop thru the current files
            XmlNode xFiles = xDoc.CreateElement("CurrentFiles");
            xDS.AppendChild(xFiles);
            for (Object __dummyForeachVar16 : this.MdiChildren)
            {
                MDIChild mc = (MDIChild)__dummyForeachVar16;
                String file = mc.getSourceFile();
                if (file == null)
                    continue;
                 
                xN = xDoc.CreateElement("file");
                xN.InnerText = file;
                xFiles.AppendChild(xN);
            }
            // Recent File Count
            XmlNode rfc = xDoc.CreateElement("RecentFilesMax");
            xDS.AppendChild(rfc);
            rfc.InnerText = this._RecentFilesMax.ToString();
            // Loop thru recent files list
            xFiles = xDoc.CreateElement("RecentFiles");
            xDS.AppendChild(xFiles);
            for (Object __dummyForeachVar17 : _RecentFiles.Values)
            {
                String f = (String)__dummyForeachVar17;
                xN = xDoc.CreateElement("file");
                xN.InnerText = f;
                xFiles.AppendChild(xN);
            }
            // Help File URL
            XmlNode hfu = xDoc.CreateElement("Help");
            xDS.AppendChild(hfu);
            hfu.InnerText = this._HelpUrl;
            // Show Line numbers
            XmlNode bln = xDoc.CreateElement("EditLines");
            xDS.AppendChild(bln);
            bln.InnerText = this._ShowEditLines ? "true" : "false";
            // Outline reportitems
            XmlNode ori = xDoc.CreateElement("OutlineReportItems");
            xDS.AppendChild(ori);
            ori.InnerText = this.getShowReportItemOutline() ? "true" : "false";
            // ShowTabbedInterface
            XmlNode sti = xDoc.CreateElement("ShowTabbedInterface");
            xDS.AppendChild(sti);
            sti.InnerText = this._ShowTabbedInterface ? "true" : "false";
            // Save the toolbar items
            XmlNode xTB = xDoc.CreateElement("Toolbar");
            xDS.AppendChild(xTB);
            for (Object __dummyForeachVar18 : _Toolbar)
            {
                String t = (String)__dummyForeachVar18;
                xN = xDoc.CreateElement("item");
                xN.InnerText = t;
                xTB.AppendChild(xN);
            }
            // Save the custom colors
            StringBuilder sb = new StringBuilder();
            for (Object __dummyForeachVar19 : colors)
            {
                int c = (Integer)__dummyForeachVar19;
                sb.Append(c.ToString());
                sb.Append(",");
            }
            sb.Remove(sb.Length - 1, 1);
            // remove last ","
            xN = xDoc.CreateElement("CustomColors");
            xN.InnerText = sb.ToString();
            xDS.AppendChild(xN);
            // Save the file
            String optFileName = AppDomain.CurrentDomain.BaseDirectory + "designerstate.xml";
            xDoc.Save(optFileName);
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        return ;
    }

    // still want to leave even on error
    static public int[] getCustomColors() throws Exception {
        String optFileName = AppDomain.CurrentDomain.BaseDirectory + "designerstate.xml";
        int white = 16777215;
        // default to white (magic number)
        int[] cArray = new int[]{ white, white, white, white, white, white, white, white, white, white, white, white, white, white, white, white };
        try
        {
            XmlDocument xDoc = new XmlDocument();
            xDoc.PreserveWhitespace = false;
            xDoc.Load(optFileName);
            XmlNode xNode = new XmlNode();
            xNode = xDoc.SelectSingleNode("//designerstate");
            String tcolors = "";
            for (Object __dummyForeachVar20 : xNode.ChildNodes)
            {
                // Loop thru all the child nodes
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar20;
                if (!StringSupport.equals(xNodeLoop.Name, "CustomColors"))
                    continue;
                 
                tcolors = xNodeLoop.InnerText;
                break;
            }
            String[] colorList = tcolors.Split(',');
            int i = 0;
            for (Object __dummyForeachVar21 : colorList)
            {
                String c = (String)__dummyForeachVar21;
                try
                {
                    cArray[i] = int.Parse(c);
                }
                catch (Exception __dummyCatchVar3)
                {
                    cArray[i] = white;
                }

                i++;
                if (i >= cArray.Length)
                    break;
                 
            }
        }
        catch (Exception __dummyCatchVar4)
        {
        }

        return cArray;
    }

    // Only allow 16 custom colors
    // Didn't sucessfully get the startup state but don't really care
    static public void setCustomColors(int[] colors) throws Exception {
        String optFileName = AppDomain.CurrentDomain.BaseDirectory + "designerstate.xml";
        StringBuilder sb = new StringBuilder();
        for (Object __dummyForeachVar22 : colors)
        {
            int c = (Integer)__dummyForeachVar22;
            sb.Append(c.ToString());
            sb.Append(",");
        }
        sb.Remove(sb.Length - 1, 1);
        try
        {
            // remove last ","
            XmlDocument xDoc = new XmlDocument();
            xDoc.PreserveWhitespace = false;
            xDoc.Load(optFileName);
            XmlNode xNode = new XmlNode();
            xNode = xDoc.SelectSingleNode("//designerstate");
            // Loop thru all the child nodes
            XmlNode cNode = null;
            for (Object __dummyForeachVar23 : xNode.ChildNodes)
            {
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar23;
                if (StringSupport.equals(xNodeLoop.Name, "CustomColors"))
                {
                    cNode = xNodeLoop;
                    break;
                }
                 
            }
            if (cNode == null)
            {
                cNode = xDoc.CreateElement("CustomColors");
                xNode.AppendChild(cNode);
            }
             
            cNode.InnerText = sb.ToString();
            xDoc.Save(optFileName);
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message, "Custom Color Save Failed");
        }

        return ;
    }

    private void editTextbox_Validated(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null || mc == null || !StringSupport.equals(mc.getDesignTab(), "design") || mc.getDrawCtl().getSelectedCount() != 1 || mc.getEditor() == null)
            return ;
         
        mc.getEditor().SetSelectedText(ctlEditTextbox.Text);
    }

    private void insert_Click(Object sender, EventArgs e) throws Exception {
        if (ctlInsertCurrent != null)
            ctlInsertCurrent.Checked = false;
         
        SimpleToggle ctl = (SimpleToggle)sender;
        ctlInsertCurrent = ctl.Checked ? ctl : null;
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.setFocus();
        mc.setCurrentInsert(ctlInsertCurrent == null ? null : (String)ctlInsertCurrent.Tag);
    }

    private void ctlBold_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.applyStyleToSelected("FontWeight",ctlBold.Checked ? "Bold" : "Normal");
        setMDIChildFocus(mc);
    }

    private void ctlItalic_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.applyStyleToSelected("FontStyle",ctlItalic.Checked ? "Italic" : "Normal");
        setMDIChildFocus(mc);
    }

    private void ctlUnderline_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.applyStyleToSelected("TextDecoration",ctlUnderline.Checked ? "Underline" : "None");
        setMDIChildFocus(mc);
    }

    private void ctlForeColor_Change(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!bSuppressChange)
            mc.ApplyStyleToSelected("Color", ctlForeColor.Text);
         
        setMDIChildFocus(mc);
    }

    private void ctlBackColor_Change(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!bSuppressChange)
            mc.ApplyStyleToSelected("BackgroundColor", ctlBackColor.Text);
         
        setMDIChildFocus(mc);
    }

    private void ctlFont_Change(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!bSuppressChange)
            mc.ApplyStyleToSelected("FontFamily", ctlFont.Text);
         
        setMDIChildFocus(mc);
    }

    private void ctlFontSize_Change(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (!bSuppressChange)
            mc.ApplyStyleToSelected("FontSize", ctlFontSize.Text + "pt");
         
        setMDIChildFocus(mc);
    }

    private void ctlZoom_Change(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.setFocus();
        Text __dummyScrutVar5 = ctlZoom.Text;
        if (__dummyScrutVar5.equals("Actual Size"))
        {
            mc.setZoom(1);
        }
        else if (__dummyScrutVar5.equals("Fit Page"))
        {
            mc.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitPage);
        }
        else if (__dummyScrutVar5.equals("Fit Width"))
        {
            mc.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitWidth);
        }
        else
        {
            String s = ctlZoom.Text.Substring(0, ctlZoom.Text.Length - 1);
            float z = new float();
            try
            {
                z = Convert.ToSingle(s) / 100f;
                mc.setZoom(z);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Zoom Value Invalid");
            }
        
        }   
    }

    private void rdlDesigner_MdiChildActivate(Object sender, EventArgs e) throws Exception {
        if (this._ValidateRdl != null)
            // don't keep the validation open when window changes
            this._ValidateRdl.Close();
         
        DesignTabChanged(sender, e);
        SelectionChanged(sender, e);
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.setFocus();
        mainTC.SelectTab(mc.getTab());
    }

    private void setMDIChildFocus(MDIChild mc) throws Exception {
        // We don't want to be triggering any change events when the focus is changing
        boolean bSuppress = bSuppressChange;
        bSuppressChange = true;
        mc.setFocus();
        bSuppressChange = bSuppress;
    }

    private void setStatusNameAndPosition() throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
        {
            statusPosition.Text = statusSelected.Text = "";
        }
        else if (StringSupport.equals(mc.getDesignTab(), "design"))
            setStatusNameAndPositionDesign(mc);
        else if (StringSupport.equals(mc.getDesignTab(), "edit"))
            setStatusNameAndPositionEdit(mc);
        else
        {
            statusPosition.Text = statusSelected.Text = "";
        }   
        return ;
    }

    private void setStatusNameAndPositionDesign(MDIChild mc) throws Exception {
        if (mc.getDrawCtl().getSelectedCount() <= 0)
        {
            statusPosition.Text = statusSelected.Text = "";
            return ;
        }
         
        // Handle position
        PointF pos = mc.getSelectionPosition();
        SizeF sz = mc.getSelectionSize();
        String spos = new String();
        if (pos.X == float.MinValue)
            // no item selected is probable cause
            spos = "";
        else
        {
            RegionInfo rinfo = new RegionInfo(CultureInfo.CurrentCulture.LCID);
            double m72 = DesignXmlDraw.POINTSIZED;
            if (rinfo.IsMetric)
            {
                if (sz.Width == float.MinValue)
                    // item is in a table/matrix is probably cause
                    spos = String.Format("   x={0:0.00}cm, y={1:0.00}cm        ", pos.X / (m72 / 2.54d), pos.Y / (m72 / 2.54d));
                else
                    spos = String.Format("   x={0:0.00}cm, y={1:0.00}cm, w={2:0.00}cm, h={3:0.00}cm        ", pos.X / (m72 / 2.54d), pos.Y / (m72 / 2.54d), sz.Width / (m72 / 2.54d), sz.Height / (m72 / 2.54d)); 
            }
            else
            {
                if (sz.Width == float.MinValue)
                    spos = String.Format("   x={0:0.00}\", y={1:0.00}\"        ", pos.X / m72, pos.Y / m72);
                else
                    spos = String.Format("   x={0:0.00}\", y={1:0.00}\", w={2:0.00}\", h={3:0.00}\"        ", pos.X / m72, pos.Y / m72, sz.Width / m72, sz.Height / m72); 
            } 
        } 
        if (!StringSupport.equals(spos, statusPosition.Text))
            statusPosition.Text = spos;
         
        // Handle text
        String sname = mc.getSelectionName();
        if (!StringSupport.equals(sname, statusSelected.Text))
            statusSelected.Text = sname;
         
        return ;
    }

    private void setStatusNameAndPositionEdit(MDIChild mc) throws Exception {
        String spos = String.Format("Ln {0}  Ch {1}", mc.getCurrentLine(), mc.getCurrentCh());
        if (!StringSupport.equals(spos, statusSelected.Text))
            statusSelected.Text = spos;
         
        if (!StringSupport.equals(statusPosition.Text, ""))
            statusPosition.Text = "";
         
        return ;
    }

    private void editTextBox_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        // Force scroll up and down
        KeyCode __dummyScrutVar6 = e.KeyCode;
        if (__dummyScrutVar6.equals(Keys.Enter))
        {
            mc.setFocus();
            e.Handled = true;
        }
        else if (__dummyScrutVar6.equals(Keys.Escape))
        {
            if (mc.getDrawCtl().getSelectedCount() == 1)
            {
                XmlNode tn = mc.getDrawCtl().getSelectedList()[0] instanceof XmlNode ? (XmlNode)mc.getDrawCtl().getSelectedList()[0] : (XmlNode)null;
                if (tn != null && StringSupport.equals(tn.Name, "Textbox"))
                {
                    ctlEditTextbox.Text = mc.getDrawCtl().getElementValue(tn,"Value","");
                    e.Handled = true;
                }
                 
            }
             
        }
        else
        {
        }  
    }

    private void menuFormat_Popup(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        // Determine if group operation on selected is currently allowed
        boolean bEnable = (mc != null && StringSupport.equals(mc.getDesignTab(), "design") && mc.getDrawCtl().getAllowGroupOperationOnSelected());
        this.menuFormatAlignB.Enabled = this.menuFormatAlignC.Enabled = this.menuFormatAlignL.Enabled = this.menuFormatAlignM.Enabled = this.menuFormatAlignR.Enabled = this.menuFormatAlignT.Enabled = bEnable;
        menuFormatSizeW.Enabled = menuFormatSizeH.Enabled = menuFormatSizeB.Enabled = bEnable;
        menuFormatHorzE.Enabled = menuFormatHorzI.Enabled = menuFormatHorzD.Enabled = menuFormatHorzZ.Enabled = bEnable;
        menuFormatVertE.Enabled = menuFormatVertI.Enabled = menuFormatVertD.Enabled = menuFormatVertZ.Enabled = bEnable;
        bEnable = (mc != null && StringSupport.equals(mc.getDesignTab(), "design") && mc.getDrawCtl().getSelectedCount() > 0);
        this.menuFormatPaddingBottomI.Enabled = this.menuFormatPaddingBottomD.Enabled = this.menuFormatPaddingBottomZ.Enabled = this.menuFormatPaddingTopI.Enabled = this.menuFormatPaddingTopD.Enabled = this.menuFormatPaddingTopZ.Enabled = this.menuFormatPaddingLeftI.Enabled = this.menuFormatPaddingLeftD.Enabled = this.menuFormatPaddingLeftZ.Enabled = this.menuFormatPaddingRightI.Enabled = this.menuFormatPaddingRightD.Enabled = this.menuFormatPaddingRightZ.Enabled = bEnable;
    }

    private void menuFormatAlignC_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignCenters();
    }

    private void menuFormatAlignL_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignLefts();
    }

    private void menuFormatAlignR_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignRights();
    }

    private void menuFormatAlignB_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignBottoms();
    }

    private void menuFormatAlignT_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignTops();
    }

    private void menuFormatAlignM_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().alignMiddles();
    }

    private void menuFormatSizeH_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().sizeHeights();
    }

    private void menuFormatSizeW_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().sizeWidths();
    }

    private void menuFormatSizeB_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().sizeBoth();
    }

    private void menuFormatHorzE_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().horzSpacingMakeEqual();
    }

    private void menuFormatHorzI_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().horzSpacingIncrease();
    }

    private void menuFormatHorzD_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().horzSpacingDecrease();
    }

    private void menuFormatHorzZ_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().horzSpacingMakeZero();
    }

    private void menuFormatVertE_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().vertSpacingMakeEqual();
    }

    private void menuFormatVertI_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().vertSpacingIncrease();
    }

    private void menuFormatVertD_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().vertSpacingDecrease();
    }

    private void menuFormatVertZ_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getEditor().getDesignCtl().vertSpacingMakeZero();
    }

    private void menuView_Popup(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        boolean bEnable = mc != null;
        menuViewDesigner.Enabled = menuViewRDL.Enabled = menuViewPreview.Enabled = bEnable;
        menuViewProperties.Enabled = bEnable && StringSupport.equals(mc.getDesignTab(), "design");
    }

    private void menuViewDesigner_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getRdlEditor().setDesignTab("design");
    }

    private void menuViewRDL_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getRdlEditor().setDesignTab("edit");
    }

    private void menuViewBrowser_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        try
        {
            menuToolsStartProcess(true);
            // start desktop if not already up
            DesktopConfig dc = DialogToolOptions.getDesktopConfiguration();
            String rdlfile = Path.GetFileNameWithoutExtension(mc.getSourceFile()) + "_" + (++TEMPRDL_INC).ToString() + TEMPRDL;
            String file = new String();
            if (Path.IsPathRooted(dc.Directory))
                file = dc.Directory + Path.DirectorySeparatorChar + rdlfile;
            else
                file = AppDomain.CurrentDomain.BaseDirectory + dc.Directory + Path.DirectorySeparatorChar + rdlfile; 
            if (_TempReportFiles == null)
            {
                _TempReportFiles = new List<String>();
                _TempReportFiles.Add(file);
            }
            else
            {
                if (!_TempReportFiles.Contains(file))
                    _TempReportFiles.Add(file);
                 
            } 
            StreamWriter sw = File.CreateText(file);
            sw.Write(mc.getSourceRdl());
            sw.Close();
            // http://localhost:8080/aReport.rdl?rs:Format=HTML
            String url = String.Format("http://localhost:{0}/{1}?rd:Format=HTML", dc.Port, rdlfile);
            System.Diagnostics.Process.Start(url);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Unable to Show Report");
        }
    
    }

    private void menuViewPreview_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        mc.getRdlEditor().setDesignTab("preview");
    }

    private void menuFormatPadding_Click(Object sender, System.EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        MenuItem mi = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        String padname = null;
        int paddiff = 0;
        if (mi == menuFormatPaddingLeftI)
        {
            padname = "PaddingLeft";
            paddiff = 4;
        }
        else if (mi == menuFormatPaddingLeftD)
        {
            padname = "PaddingLeft";
            paddiff = -4;
        }
        else if (mi == menuFormatPaddingLeftZ)
        {
            padname = "PaddingLeft";
            paddiff = 0;
        }
        else if (mi == menuFormatPaddingRightI)
        {
            padname = "PaddingRight";
            paddiff = 4;
        }
        else if (mi == menuFormatPaddingRightD)
        {
            padname = "PaddingRight";
            paddiff = -4;
        }
        else if (mi == menuFormatPaddingRightZ)
        {
            padname = "PaddingRight";
            paddiff = 0;
        }
        else if (mi == menuFormatPaddingTopI)
        {
            padname = "PaddingTop";
            paddiff = 4;
        }
        else if (mi == menuFormatPaddingTopD)
        {
            padname = "PaddingTop";
            paddiff = -4;
        }
        else if (mi == menuFormatPaddingTopZ)
        {
            padname = "PaddingTop";
            paddiff = 0;
        }
        else if (mi == menuFormatPaddingBottomI)
        {
            padname = "PaddingBottom";
            paddiff = 4;
        }
        else if (mi == menuFormatPaddingBottomD)
        {
            padname = "PaddingBottom";
            paddiff = -4;
        }
        else if (mi == menuFormatPaddingBottomZ)
        {
            padname = "PaddingBottom";
            paddiff = 0;
        }
                    
        if (padname != null)
            mc.getEditor().getDesignCtl().setPadding(padname,paddiff);
         
    }

    private void cleanupTempFiles() throws Exception {
        if (_TempReportFiles == null)
            return ;
         
        for (Object __dummyForeachVar24 : _TempReportFiles)
        {
            String file = (String)__dummyForeachVar24;
            try
            {
                // It's ok for the delete to fail
                File.Delete(file);
            }
            catch (Exception __dummyCatchVar5)
            {
            }
        
        }
        _TempReportFiles = null;
    }

}


