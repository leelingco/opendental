//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReportForRdl;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.PrintSituation;

//using MySql.Data.MySqlClient;
//SEE THE BOTTOM OF THIS FILE FOR INFO ON HOW TO EDIT THE ORIGINAL RDL FILES TO MAKE THEM WORK BETTER WITH OPEN DENTAL.
//THIS WILL ALLOW US TO UPDATE VERSIONS OF RDL WITHOUT 'BREAKING' THE EXISTING FUNCTIONALITY
/**
* 
*/
public class FormReportForRdl  extends System.Windows.Forms.Form 
{
    private fyiReporting.RdlViewer.RdlViewer viewer;
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.ContextMenu menuScrollMode = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemContinuous = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemContinuousFacing = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemFacing = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSinglePage = new System.Windows.Forms.MenuItem();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    /**
    * 
    */
    public FormReportForRdl() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        setSourceFilePath(null);
        setSourceRdlString(null);
        Lan.f(this);
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReportForRdl.class);
        this.viewer = new fyiReporting.RdlViewer.RdlViewer();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.menuScrollMode = new System.Windows.Forms.ContextMenu();
        this.menuItemContinuous = new System.Windows.Forms.MenuItem();
        this.menuItemContinuousFacing = new System.Windows.Forms.MenuItem();
        this.menuItemFacing = new System.Windows.Forms.MenuItem();
        this.menuItemSinglePage = new System.Windows.Forms.MenuItem();
        this.SuspendLayout();
        //
        // viewer
        //
        this.viewer.Cursor = System.Windows.Forms.Cursors.Default;
        this.viewer.setFolder(null);
        this.viewer.Location = new System.Drawing.Point(45, 56);
        this.viewer.Name = "viewer";
        this.viewer.setPageCurrent(1);
        this.viewer.setParameters(null);
        this.viewer.setReportName(null);
        this.viewer.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.Continuous);
        this.viewer.setShowParameterPanel(true);
        this.viewer.Size = new System.Drawing.Size(856, 453);
        this.viewer.setSourceFile(null);
        this.viewer.setSourceRdl(null);
        this.viewer.TabIndex = 2;
        this.viewer.Text = "rdlViewer1";
        this.viewer.setZoom(0.3662712F);
        this.viewer.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitPage);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(987, 25);
        this.ToolBarMain.TabIndex = 5;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "");
        this.imageListMain.Images.SetKeyName(1, "");
        this.imageListMain.Images.SetKeyName(2, "");
        this.imageListMain.Images.SetKeyName(3, "");
        this.imageListMain.Images.SetKeyName(4, "");
        this.imageListMain.Images.SetKeyName(5, "");
        this.imageListMain.Images.SetKeyName(6, "");
        //
        // menuScrollMode
        //
        this.menuScrollMode.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemContinuous, this.menuItemContinuousFacing, this.menuItemFacing, this.menuItemSinglePage });
        //
        // menuItemContinuous
        //
        this.menuItemContinuous.Index = 0;
        this.menuItemContinuous.Text = "Continuous";
        this.menuItemContinuous.Click += new System.EventHandler(this.menuItemContinuous_Click);
        //
        // menuItemContinuousFacing
        //
        this.menuItemContinuousFacing.Index = 1;
        this.menuItemContinuousFacing.Text = "Continuous Facing";
        this.menuItemContinuousFacing.Click += new System.EventHandler(this.menuItemContinuousFacing_Click);
        //
        // menuItemFacing
        //
        this.menuItemFacing.Index = 2;
        this.menuItemFacing.Text = "Facing";
        this.menuItemFacing.Click += new System.EventHandler(this.menuItemFacing_Click);
        //
        // menuItemSinglePage
        //
        this.menuItemSinglePage.Index = 3;
        this.menuItemSinglePage.Text = "Single Page";
        this.menuItemSinglePage.Click += new System.EventHandler(this.menuItemSinglePage_Click);
        //
        // FormReportForRdl
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(987, 712);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.viewer);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormReportForRdl";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Report";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormRDLreport_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormReport_Layout);
        this.ResumeLayout(false);
    }

    private void formRDLreport_Load(Object sender, System.EventArgs e) throws Exception {
        layoutToolBar();
        viewer.setShowParameterPanel(false);
        viewer.rebuild();
    }

    /**
    * Either this or SourceRdlString should be set before opening the form.
    */
    public String getSourceFilePath() throws Exception {
        return viewer.getSourceFile();
    }

    public void setSourceFilePath(String value) throws Exception {
        viewer.setSourceFile(value);
    }

    /**
    * Either this or SourceFilePath should be set before opening the form.
    */
    public String getSourceRdlString() throws Exception {
        return viewer.getSourceRdl();
    }

    public void setSourceRdlString(String value) throws Exception {
        viewer.setSourceRdl(value);
    }

    public fyiReporting.RDL.Report getRdlReport() throws Exception {
        return viewer.getReport();
    }

    private void formReport_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        //the viewer dockFill does not work right, so this handles it manually:
        viewer.Location = new Point(0, ToolBarMain.Bottom);
        viewer.Size = new Size(ClientSize.Width, ClientSize.Height - viewer.Top);
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Print"), 0, "", "Print"));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Save PDF"),4,"Save as Adobe PDF","PDF"));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Export"),3,"","Export"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        //ToolBarMain.Buttons.Add(new ODToolBarButton("",1,"Go Back One Page","Back"));
        //ODToolBarButton button=new ODToolBarButton("",-1,"","PageNum");
        //button.Style=ODToolBarButtonStyle.Label;
        //ToolBarMain.Buttons.Add(button);
        //ToolBarMain.Buttons.Add(new ODToolBarButton("",2,"Go Forward One Page","Fwd"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Zoom In"), 6, "", "Zoom"));
        //ODToolBarButton button=new ODToolBarButton("Scroll Mode",-1,"","");
        //button.Style=ODToolBarButtonStyle.DropDownButton;
        //button.DropDownMenu=menuScrollMode;
        //ToolBarMain.Buttons.Add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, "Close This Window", "Close"));
    }

    //ToolBarMain.Invalidate();
    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        //MessageBox.Show(e.Button.Tag.ToString());
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Print"))
        {
            print_Click();
        }
        else if (__dummyScrutVar0.equals("PDF"))
        {
            pDF_Click();
        }
        else if (__dummyScrutVar0.equals("Export"))
        {
            export_Click();
        }
        else if (__dummyScrutVar0.equals("Back"))
        {
            back_Click();
        }
        else if (__dummyScrutVar0.equals("Fwd"))
        {
            fwd_Click();
        }
        else if (__dummyScrutVar0.equals("Zoom"))
        {
            zoom_Click();
        }
        else if (__dummyScrutVar0.equals("Close"))
        {
            Close();
        }
               
    }

    private void print_Click() throws Exception {
        PrintDocument pd = new PrintDocument();
        pd.DocumentName = viewer.getSourceFile();
        pd.PrinterSettings.FromPage = 1;
        pd.PrinterSettings.ToPage = viewer.getPageCount();
        pd.PrinterSettings.MaximumPage = viewer.getPageCount();
        pd.PrinterSettings.MinimumPage = 1;
        pd.DefaultPageSettings.Landscape = viewer.getPageWidth() > viewer.getPageHeight();
        pd.DefaultPageSettings.Margins = new Margins(50, 50, 50, 50);
        //Half-inch all around.
        //This prevents a bug caused by some printer drivers not reporting their papersize.
        //But remember that other countries use A4 paper instead of 8 1/2 x 11.
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Report printed " + pd.DocumentName))
            {
                if (pd.PrinterSettings.PrintRange == PrintRange.Selection)
                {
                    pd.PrinterSettings.FromPage = viewer.getPageCurrent();
                }
                 
                viewer.print(pd);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void back_Click() throws Exception {
    }

    /*viewer.  .se.PageCurrent--;
    			ToolBarMain.Buttons["PageNum"].Text=(viewer.PageCurrent).ToString()
    				+" / "+viewer.PageCount.ToString();
    			ToolBarMain.Invalidate();*/
    private void fwd_Click() throws Exception {
    }

    /*
    			//if(printPreviewControl2.StartPage==totalPages-1) return;
    			viewer.PageCurrent++;
    			ToolBarMain.Buttons["PageNum"].Text=(viewer.PageCurrent).ToString()
    				+" / "+viewer.PageCount.ToString();
    			ToolBarMain.Invalidate();*/
    private void pDF_Click() throws Exception {
        MessageBox.Show("Not functional yet");
    }

    private void export_Click() throws Exception {
        MessageBox.Show("Not functional yet");
    }

    /*SaveFileDialog saveFileDialog2=new SaveFileDialog();
    			saveFileDialog2.AddExtension=true;
    			//saveFileDialog2.Title=Lan.g(this,"Select Folder to Save File To");
    			saveFileDialog2.FileName=MyReport.ReportName+".txt";
    			if(!Directory.Exists(PrefC.GetString(PrefName.ExportPath"))){
    				try{
    					Directory.CreateDirectory(PrefC.GetString(PrefName.ExportPath"));
    					saveFileDialog2.InitialDirectory=PrefC.GetString(PrefName.ExportPath");
    				}
    				catch{
    					//initialDirectory will be blank
    				}
    			}
    			else{
    				saveFileDialog2.InitialDirectory=PrefC.GetString(PrefName.ExportPath");
    			}
    			//saveFileDialog2.DefaultExt="txt";
    			saveFileDialog2.Filter="Text files(*.txt)|*.txt|Excel Files(*.xls)|*.xls|All files(*.*)|*.*";
    			saveFileDialog2.FilterIndex=0;
    			if(saveFileDialog2.ShowDialog()!=DialogResult.OK){
    				return;
    			}
    			try{
    				using(StreamWriter sw=new StreamWriter(saveFileDialog2.FileName,false)){
    					String line="";  
    					for(int i=0;i<MyReport.ReportTable.Columns.Count;i++){
    						line+=MyReport.ReportTable.Columns[i].Caption;
    						if(i<MyReport.ReportTable.Columns.Count-1){
    							line+="\t";
    						}
    					}
    					sw.WriteLine(line);
    					string cell;
    					for(int i=0;i<MyReport.ReportTable.Rows.Count;i++){
    						line="";
    						for(int j=0;j<MyReport.ReportTable.Columns.Count;j++){
    							cell=MyReport.ReportTable.Rows[i][j].ToString();
    							cell=cell.Replace("\r","");
    							cell=cell.Replace("\n","");
    							cell=cell.Replace("\t","");
    							cell=cell.Replace("\"","");
    							line+=cell;
    							if(j<MyReport.ReportTable.Columns.Count-1){
    								line+="\t";
    							}
    						}
    						sw.WriteLine(line);
    					}
    				}//using
    			}
    			catch{
    				MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
    				return;
    			}
    			MessageBox.Show(Lan.g(this,"File created successfully"));*/
    private void zoom_Click() throws Exception {
        if (viewer.getZoomMode() == fyiReporting.RdlViewer.ZoomEnum.FitPage)
        {
            //then zoom in
            viewer.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitWidth);
            ToolBarMain.getButtons().get___idx("Zoom").setText(Lan.g(this,"Zoom Out"));
            ToolBarMain.getButtons().get___idx("Zoom").setImageIndex(6);
        }
        else
        {
            //zoom out
            viewer.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitPage);
            ToolBarMain.getButtons().get___idx("Zoom").setText(Lan.g(this,"Zoom In"));
            ToolBarMain.getButtons().get___idx("Zoom").setImageIndex(5);
        } 
        ToolBarMain.Invalidate();
    }

    private void menuItemContinuous_Click(Object sender, System.EventArgs e) throws Exception {
        viewer.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.Continuous);
    }

    private void menuItemContinuousFacing_Click(Object sender, System.EventArgs e) throws Exception {
        viewer.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.ContinuousFacing);
    }

    private void menuItemFacing_Click(Object sender, System.EventArgs e) throws Exception {
        viewer.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.Facing);
    }

    private void menuItemSinglePage_Click(Object sender, System.EventArgs e) throws Exception {
        viewer.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.SinglePage);
    }

}


/*
Things to keep in mind the next time we upgrade to a new version of RDL.
These details may also need to be fine tuned to support various features that we want in OD.
Definition\Query.cs in an important class.
Query.GetData() is important where it sets the timeout.  Setting timeout is not allowed on the MySQL connector.  They might have fixed this bug.
Runtime\RdlEngineConfig.cs, must make sure there is a datasource like this:
		<DataSource>
			<DataProvider>MySQL.NET</DataProvider>
			<CodeModule>MySql.Data.dll</CodeModule>
			<ClassName>MySql.Data.MySqlClient.MySqlConnection</ClassName>
			<TableSelect>show tables</TableSelect>
			<Interface>SQL</Interface>
			<ReplaceParameters>true</ReplaceParameters>
		</DataSource>
Must also have MySQL.Data.dll in the same folder as RdlDesigner.exe.
 */