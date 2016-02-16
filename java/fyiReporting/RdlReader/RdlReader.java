//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RdlReader;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RdlReader.DialogAbout;
import fyiReporting.RdlReader.MDIChild;
import fyiReporting.RdlReader.RdlReader;
import fyiReporting.RdlReader.ZoomTo;
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
* RdlReader is a application for displaying reports based on RDL.
*/
public class RdlReader  extends System.Windows.Forms.Form 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private MDIChild printChild = null;
    SortedList _RecentFiles = null;
    ArrayList _CurrentFiles = null;
    // temporary variable for current files
    private NeedPassword _GetPassword;
    private String _DataSourceReferencePassword = null;
    private boolean bMono = new boolean();
    // Menu declarations
    MenuItem menuFSep1 = new MenuItem();
    MenuItem menuFSep2 = new MenuItem();
    MenuItem menuFSep3 = new MenuItem();
    MenuItem menuOpen = new MenuItem();
    MenuItem menuClose = new MenuItem();
    MenuItem menuSaveAs = new MenuItem();
    MenuItem menuPrint = new MenuItem();
    MenuItem menuRecentFile = new MenuItem();
    MenuItem menuExit = new MenuItem();
    MenuItem menuFile = new MenuItem();
    MenuItem menuPLZoomTo = new MenuItem();
    MenuItem menuPLActualSize = new MenuItem();
    MenuItem menuPLFitPage = new MenuItem();
    MenuItem menuPLFitWidth = new MenuItem();
    MenuItem menuPLSinglePage = new MenuItem();
    MenuItem menuPLContinuous = new MenuItem();
    MenuItem menuPLFacing = new MenuItem();
    MenuItem menuPLContinuousFacing = new MenuItem();
    MenuItem menuPL = new MenuItem();
    MenuItem menuView = new MenuItem();
    MenuItem menuCascade = new MenuItem();
    MenuItem menuTileH = new MenuItem();
    MenuItem menuTileV = new MenuItem();
    MenuItem menuTile = new MenuItem();
    MenuItem menuCloseAll = new MenuItem();
    MenuItem menuWindow = new MenuItem();
    MainMenu menuMain = new MainMenu();
    public RdlReader(boolean mono) throws Exception {
        bMono = mono;
        getStartupState();
        buildMenus();
        initializeComponent();
        this.Closing += new System.ComponentModel.CancelEventHandler(this.RdlReader_Closing);
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
        // open up the current files if any
        if (_CurrentFiles != null)
        {
            for (Object __dummyForeachVar0 : _CurrentFiles)
            {
                String file = (String)__dummyForeachVar0;
                MDIChild mc = new MDIChild(this.ClientRectangle.Width * 3 / 4, this.ClientRectangle.Height * 3 / 4);
                mc.MdiParent = this;
                mc.getViewer().GetDataSourceReferencePassword = _GetPassword;
                mc.setSourceFile(file);
                mc.Text = file;
                mc.Show();
            }
            _CurrentFiles = null;
        }
         
    }

    // don't need this any longer
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

    String getPassword() throws Exception {
        if (_DataSourceReferencePassword != null)
            return _DataSourceReferencePassword;
         
        DataSourcePassword dlg = new DataSourcePassword();
        if (dlg.ShowDialog() == DialogResult.OK)
            _DataSourceReferencePassword = dlg.getPassPhrase();
         
        return _DataSourceReferencePassword;
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(RdlReader.class);
        //
        // RdlReader
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(712, 470);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "RdlReader";
        this.Text = "fyiReporting Reader";
    }

    /**
    * The main entry point for the application.
    */
    public static void main(String[] args) throws Exception {
        RdlReader.Main();
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
        Application.Run(new RdlReader(bMono));
    }

    private void buildMenus() throws Exception {
        // FILE MENU
        menuOpen = new MenuItem("&Open...", new EventHandler(this.menuFileOpen_Click), Shortcut.CtrlO);
        menuClose = new MenuItem("&Close", new EventHandler(this.menuFileClose_Click), Shortcut.CtrlW);
        menuFSep1 = new MenuItem("-");
        menuSaveAs = new MenuItem("&Save As...", new EventHandler(this.menuFileSaveAs_Click), Shortcut.CtrlS);
        menuPrint = new MenuItem("Print...", new EventHandler(this.menuFilePrint_Click), Shortcut.CtrlP);
        menuFSep2 = new MenuItem("-");
        MenuItem menuRecentItem = new MenuItem("");
        menuRecentFile = new MenuItem("Recent &Files");
        menuRecentFile.MenuItems.AddRange(new MenuItem[]{ menuRecentItem });
        menuFSep3 = new MenuItem("-");
        menuExit = new MenuItem("E&xit", new EventHandler(this.menuFileExit_Click), Shortcut.CtrlQ);
        // Create file menu and add array of sub-menu items
        menuFile = new MenuItem("&File");
        menuFile.Popup += new EventHandler(this.menuFile_Popup);
        menuFile.MenuItems.AddRange(new MenuItem[]{ menuOpen, menuClose, menuFSep1, menuSaveAs, menuPrint, menuFSep2, menuRecentFile, menuFSep3, menuExit });
        // Intialize the recent file menu
        recentFilesMenu();
        // VIEW MENU
        menuPLZoomTo = new MenuItem("&Zoom To...", new EventHandler(this.menuPLZoomTo_Click));
        menuPLActualSize = new MenuItem("Act&ual Size", new EventHandler(this.menuPLActualSize_Click));
        menuPLFitPage = new MenuItem("Fit &Page", new EventHandler(this.menuPLFitPage_Click));
        menuPLFitWidth = new MenuItem("Fit &Width", new EventHandler(this.menuPLFitWidth_Click));
        menuFSep1 = new MenuItem("-");
        menuPLSinglePage = new MenuItem("Single Page", new EventHandler(this.menuPLSinglePage_Click));
        menuPLContinuous = new MenuItem("Continuous", new EventHandler(this.menuPLContinuous_Click));
        menuPLFacing = new MenuItem("Facing", new EventHandler(this.menuPLFacing_Click));
        menuPLContinuousFacing = new MenuItem("Continuous Facing", new EventHandler(this.menuPLContinuousFacing_Click));
        menuPL = new MenuItem("Page La&yout");
        menuPL.Popup += new EventHandler(this.menuPL_Popup);
        menuPL.MenuItems.AddRange(new MenuItem[]{ menuPLSinglePage, menuPLContinuous, menuPLFacing, menuPLContinuousFacing });
        menuView = new MenuItem("&View");
        menuView.Popup += new EventHandler(this.menuView_Popup);
        menuView.MenuItems.AddRange(new MenuItem[]{ menuPLZoomTo, menuPLActualSize, menuPLFitPage, menuPLFitWidth, menuFSep1, menuPL });
        // WINDOW MENU
        menuCascade = new MenuItem("&Cascade", new EventHandler(this.menuWndCascade_Click), Shortcut.CtrlShiftJ);
        menuTileH = new MenuItem("&Horizontally", new EventHandler(this.menuWndTileH_Click), Shortcut.CtrlShiftK);
        menuTileV = new MenuItem("&Vertically", new EventHandler(this.menuWndTileV_Click), Shortcut.CtrlShiftL);
        menuTile = new MenuItem("&Tile");
        menuTile.MenuItems.AddRange(new MenuItem[]{ menuTileH, menuTileV });
        menuCloseAll = new MenuItem("Close &All", new EventHandler(this.menuWndCloseAll_Click), Shortcut.CtrlShiftW);
        // Add the Window menu
        menuWindow = new MenuItem("&Window");
        menuWindow.Popup += new EventHandler(this.menuWnd_Popup);
        menuWindow.MdiList = true;
        menuWindow.MenuItems.AddRange(new MenuItem[]{ menuCascade, menuTile, menuCloseAll });
        // HELP MENU
        MenuItem menuAbout = new MenuItem("&About...", new EventHandler(this.menuHelpAbout_Click));
        MenuItem menuHelp = new MenuItem("&Help");
        menuHelp.MenuItems.AddRange(new MenuItem[]{ menuAbout });
        // MAIN
        menuMain = new MainMenu(new MenuItem[]{ menuFile, menuView, menuWindow, menuHelp });
        IsMdiContainer = true;
        this.Menu = menuMain;
    }

    private void menuFile_Popup(Object sender, EventArgs e) throws Exception {
        // These menus require an MDIChild in order to work
        boolean bEnable = this.MdiChildren.Length > 0 ? true : false;
        menuClose.Enabled = bEnable;
        menuSaveAs.Enabled = bEnable;
        menuPrint.Enabled = bEnable;
        // Recent File is enabled when there exists some files
        menuRecentFile.Enabled = this._RecentFiles.Count <= 0 ? false : true;
    }

    private void menuFileClose_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.Close();
         
    }

    private void menuFileExit_Click(Object sender, EventArgs e) throws Exception {
        Environment.Exit(0);
    }

    private void menuFileOpen_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Report files (*.rdl)|*.rdl|" + "All files (*.*)|*.*";
        ofd.FilterIndex = 1;
        ofd.CheckFileExists = true;
        ofd.Multiselect = true;
        if (ofd.ShowDialog(this) == DialogResult.OK)
        {
            for (Object __dummyForeachVar1 : ofd.FileNames)
            {
                String file = (String)__dummyForeachVar1;
                createMDIChild(file,false);
            }
            recentFilesMenu();
        }
         
    }

    private void menuRecentItem_Click(Object sender, System.EventArgs e) throws Exception {
        MenuItem m = (MenuItem)sender;
        String file = m.Text.Substring(2);
        createMDIChild(file,true);
    }

    // Create an MDI child.   Only creates it if not already open
    private void createMDIChild(String file, boolean bMenuUpdate) throws Exception {
        MDIChild mcOpen = null;
        if (file != null)
        {
            file = file.Trim();
            for (Object __dummyForeachVar2 : this.MdiChildren)
            {
                MDIChild mc = (MDIChild)__dummyForeachVar2;
                if (StringSupport.equals(file, mc.getSourceFile().Trim()))
                {
                    // we found it
                    mcOpen = mc;
                    break;
                }
                 
            }
        }
         
        if (mcOpen == null)
        {
            MDIChild mc = new MDIChild(this.ClientRectangle.Width * 3 / 4, this.ClientRectangle.Height * 3 / 4);
            mc.MdiParent = this;
            mc.getViewer().GetDataSourceReferencePassword = _GetPassword;
            mc.setSourceFile(file);
            mc.Text = file;
            noteRecentFiles(file,bMenuUpdate);
            mc.Show();
        }
        else
            mcOpen.Activate(); 
    }

    private void menuFilePrint_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        if (printChild != null)
        {
            // already printing
            MessageBox.Show("Can only print one file at a time.");
            return ;
        }
         
        printChild = mc;
        PrintDocument pd = new PrintDocument();
        pd.DocumentName = mc.getSourceFile();
        pd.PrinterSettings.FromPage = 1;
        pd.PrinterSettings.ToPage = mc.getViewer().PageCount;
        pd.PrinterSettings.MaximumPage = mc.getViewer().PageCount;
        pd.PrinterSettings.MinimumPage = 1;
        if (mc.getViewer().PageWidth > mc.getViewer().PageHeight)
            pd.DefaultPageSettings.Landscape = true;
        else
            pd.DefaultPageSettings.Landscape = false; 
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
                    pd.PrinterSettings.FromPage = mc.getViewer().PageCurrent;
                }
                 
                mc.getViewer().Print(pd);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Print error: " + ex.Message);
            }
        
        }
         
        printChild = null;
    }

    private void menuFileSaveAs_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        SaveFileDialog sfd = new SaveFileDialog();
        sfd.Filter = "PDF files (*.pdf)|*.pdf|" + "XML files (*.xml)|*.xml|" + "HTML files (*.html)|*.html";
        sfd.FilterIndex = 1;
        String file = mc.getSourceFile();
        if (file != null)
        {
            int index = file.LastIndexOf('.');
            if (index > 1)
                sfd.FileName = file.Substring(0, index) + ".pdf";
            else
                sfd.FileName = "*.pdf"; 
        }
        else
            sfd.FileName = "*.pdf"; 
        if (sfd.ShowDialog(this) != DialogResult.OK)
            return ;
         
        // save the report in a rendered format
        String ext = null;
        int i = sfd.FileName.LastIndexOf('.');
        if (i < 1)
            ext = "";
        else
            ext = sfd.FileName.Substring(i + 1).ToLower(); 
        System.String __dummyScrutVar0 = ext;
        if (__dummyScrutVar0.equals("pdf") || __dummyScrutVar0.equals("xml") || __dummyScrutVar0.equals("html") || __dummyScrutVar0.equals("htm"))
        {
            try
            {
                mc.getViewer().SaveAs(sfd.FileName, ext);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, ex.Message, "Save As Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        
        }
        else
        {
            MessageBox.Show(this, String.Format("{0} is not a valid file type.  File extension must be PDF, XML, or HTML.", sfd.FileName), "Save As Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            break;
                ;
        } 
        return ;
    }

    private void menuHelpAbout_Click(Object sender, System.EventArgs ea) throws Exception {
        DialogAbout dlg = new DialogAbout();
        dlg.ShowDialog();
    }

    private void menuView_Popup(Object sender, EventArgs e) throws Exception {
        // These menus require an MDIChild in order to work
        boolean bEnable = this.MdiChildren.Length > 0 ? true : false;
        menuPLZoomTo.Enabled = bEnable;
        menuPLActualSize.Enabled = bEnable;
        menuPLFitPage.Enabled = bEnable;
        menuPLFitWidth.Enabled = bEnable;
        menuPL.Enabled = bEnable;
        if (!bEnable)
            return ;
         
        // Now handle checking the correct sizing menu
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        menuPLActualSize.Checked = menuPLFitPage.Checked = menuPLFitWidth.Checked = false;
        if (mc.getViewer().ZoomMode == fyiReporting.RdlViewer.ZoomEnum.FitWidth)
            menuPLFitWidth.Checked = true;
        else if (mc.getViewer().ZoomMode == fyiReporting.RdlViewer.ZoomEnum.FitPage)
            menuPLFitPage.Checked = true;
        else if (mc.getViewer().Zoom == 1)
            menuPLActualSize.Checked = true;
           
    }

    private void menuPL_Popup(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        menuPLSinglePage.Checked = menuPLContinuous.Checked = menuPLFacing.Checked = menuPLContinuousFacing.Checked = false;
            ;
        ScrollMode __dummyScrutVar1 = mc.getViewer().ScrollMode;
        if (__dummyScrutVar1.equals(fyiReporting.RdlViewer.ScrollModeEnum.Continuous))
        {
            menuPLContinuous.Checked = true;
        }
        else if (__dummyScrutVar1.equals(fyiReporting.RdlViewer.ScrollModeEnum.ContinuousFacing))
        {
            menuPLContinuousFacing.Checked = true;
        }
        else if (__dummyScrutVar1.equals(fyiReporting.RdlViewer.ScrollModeEnum.Facing))
        {
            menuPLFacing.Checked = true;
        }
        else if (__dummyScrutVar1.equals(fyiReporting.RdlViewer.ScrollModeEnum.SinglePage))
        {
            menuPLSinglePage.Checked = true;
        }
            
    }

    private void menuPLZoomTo_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc == null)
            return ;
         
        ZoomTo dlg = new ZoomTo(mc.getViewer());
        dlg.StartPosition = FormStartPosition.CenterParent;
        dlg.ShowDialog();
    }

    private void menuPLActualSize_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().Zoom = 1;
         
    }

    private void menuPLFitPage_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ZoomMode = fyiReporting.RdlViewer.ZoomEnum.FitPage;
         
    }

    private void menuPLFitWidth_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ZoomMode = fyiReporting.RdlViewer.ZoomEnum.FitWidth;
         
    }

    private void menuPLSinglePage_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.SinglePage;
         
    }

    private void menuPLContinuous_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.Continuous;
         
    }

    private void menuPLFacing_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.Facing;
         
    }

    private void menuPLContinuousFacing_Click(Object sender, EventArgs e) throws Exception {
        MDIChild mc = this.ActiveMdiChild instanceof MDIChild ? (MDIChild)this.ActiveMdiChild : (MDIChild)null;
        if (mc != null)
            mc.getViewer().ScrollMode = fyiReporting.RdlViewer.ScrollModeEnum.ContinuousFacing;
         
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
        for (Object __dummyForeachVar3 : this.MdiChildren)
        {
            Form f = (Form)__dummyForeachVar3;
            f.Close();
        }
    }

    private void menuWndTileH_Click(Object sender, EventArgs e) throws Exception {
        this.LayoutMdi(MdiLayout.TileHorizontal);
    }

    private void menuWndTileV_Click(Object sender, EventArgs e) throws Exception {
        this.LayoutMdi(MdiLayout.TileVertical);
    }

    private void rdlReader_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        saveStartupState();
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
         
        if (_RecentFiles.Count >= 5)
        {
            _RecentFiles.RemoveAt(0);
        }
         
        // remove the first entry
        _RecentFiles.Add(DateTime.Now, name);
        if (bResetMenu)
            recentFilesMenu();
         
        return ;
    }

    private void recentFilesMenu() throws Exception {
        menuRecentFile.MenuItems.Clear();
        int mi = 1;
        for (int i = _RecentFiles.Count - 1;i >= 0;i--)
        {
            String menuText = String.Format("&{0} {1}", mi++, (String)(_RecentFiles.GetValueList()[i]));
            MenuItem m = new MenuItem(menuText);
            m.Click += new EventHandler(this.menuRecentItem_Click);
            menuRecentFile.MenuItems.Add(m);
        }
    }

    private void getStartupState() throws Exception {
        String optFileName = AppDomain.CurrentDomain.BaseDirectory + "readerstate.xml";
        _RecentFiles = new SortedList();
        _CurrentFiles = new ArrayList();
        try
        {
            XmlDocument xDoc = new XmlDocument();
            xDoc.PreserveWhitespace = false;
            xDoc.Load(optFileName);
            XmlNode xNode = new XmlNode();
            xNode = xDoc.SelectSingleNode("//readerstate");
            for (Object __dummyForeachVar6 : xNode.ChildNodes)
            {
                // Loop thru all the child nodes
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar6;
                Name __dummyScrutVar2 = xNodeLoop.Name;
                if (__dummyScrutVar2.equals("RecentFiles"))
                {
                    DateTime now = DateTime.Now;
                    now = now.Subtract(new TimeSpan(0, 1, 0, 0, 0));
                    for (Object __dummyForeachVar4 : xNodeLoop.ChildNodes)
                    {
                        // subtract an hour
                        XmlNode xN = (XmlNode)__dummyForeachVar4;
                        String file = xN.InnerText.Trim();
                        if (File.Exists(file))
                        {
                            // only add it if it exists
                            _RecentFiles.Add(now, file);
                            now = now.AddSeconds(1);
                        }
                         
                    }
                }
                else if (__dummyScrutVar2.equals("CurrentFiles"))
                {
                    for (Object __dummyForeachVar5 : xNodeLoop.ChildNodes)
                    {
                        XmlNode xN = (XmlNode)__dummyForeachVar5;
                        String file = xN.InnerText.Trim();
                        if (File.Exists(file))
                            // only add it if it exists
                            _CurrentFiles.Add(file);
                         
                    }
                }
                else
                {
                }  
            }
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return ;
    }

    // Didn't sucessfully get the startup state but don't really care
    private void saveStartupState() throws Exception {
        try
        {
            XmlDocument xDoc = new XmlDocument();
            XmlProcessingInstruction xPI = new XmlProcessingInstruction();
            xPI = xDoc.CreateProcessingInstruction("xml", "version='1.0' encoding='UTF-8'");
            xDoc.AppendChild(xPI);
            XmlNode xDS = xDoc.CreateElement("readerstate");
            xDoc.AppendChild(xDS);
            XmlNode xN = new XmlNode();
            // Loop thru the current files
            XmlNode xFiles = xDoc.CreateElement("CurrentFiles");
            xDS.AppendChild(xFiles);
            for (Object __dummyForeachVar7 : this.MdiChildren)
            {
                MDIChild mc = (MDIChild)__dummyForeachVar7;
                String file = mc.getSourceFile();
                if (file == null)
                    continue;
                 
                xN = xDoc.CreateElement("file");
                xN.InnerText = file;
                xFiles.AppendChild(xN);
            }
            // Loop thru recent files list
            xFiles = xDoc.CreateElement("RecentFiles");
            xDS.AppendChild(xFiles);
            for (Object __dummyForeachVar8 : _RecentFiles.Values)
            {
                String f = (String)__dummyForeachVar8;
                xN = xDoc.CreateElement("file");
                xN.InnerText = f;
                xFiles.AppendChild(xN);
            }
            String optFileName = AppDomain.CurrentDomain.BaseDirectory + "readerstate.xml";
            xDoc.Save(optFileName);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return ;
    }

}


// still want to leave even on error