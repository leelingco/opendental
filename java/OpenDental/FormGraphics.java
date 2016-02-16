//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:11 PM
//

package OpenDental;

import CodeBase.OpenGLWinFormsControl;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormGraphics;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DrawingMode;
import SparksToothChart.ToothChartDirectX;
import SparksToothChart.ToothChartWrapper;

/**
* Summary description for FormBasicTemplate.
*/
public class FormGraphics  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private CheckBox checkHardwareAccel = new CheckBox();
    private CheckBox checkDoubleBuffering = new CheckBox();
    private GroupBox group3DToothChart = new GroupBox();
    private OpenDental.UI.ODGrid gridFormats;
    private CodeBase.OpenGLWinFormsControl.PixelFormatValue[] formats = new CodeBase.OpenGLWinFormsControl.PixelFormatValue[0];
    private SparksToothChart.ToothChartDirectX.DirectXDeviceFormat[] xformats = new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat[0];
    private int selectedFormatNum = 0;
    private String selectedDirectXFormat = "";
    private CheckBox checkAllFormats = new CheckBox();
    //private bool refreshAllowed=false;
    private RadioButton radioSimpleChart = new RadioButton();
    private RadioButton radioOpenGLChart = new RadioButton();
    private GroupBox groupFilters = new GroupBox();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label4 = new Label();
    private TextBox textSelected = new TextBox();
    private Label label3 = new Label();
    private RadioButton radioDirectXChart = new RadioButton();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormGraphics() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormGraphics.class);
        this.checkHardwareAccel = new System.Windows.Forms.CheckBox();
        this.checkDoubleBuffering = new System.Windows.Forms.CheckBox();
        this.group3DToothChart = new System.Windows.Forms.GroupBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textSelected = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.groupFilters = new System.Windows.Forms.GroupBox();
        this.checkAllFormats = new System.Windows.Forms.CheckBox();
        this.gridFormats = new OpenDental.UI.ODGrid();
        this.radioSimpleChart = new System.Windows.Forms.RadioButton();
        this.radioOpenGLChart = new System.Windows.Forms.RadioButton();
        this.radioDirectXChart = new System.Windows.Forms.RadioButton();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.group3DToothChart.SuspendLayout();
        this.groupFilters.SuspendLayout();
        this.SuspendLayout();
        //
        // checkHardwareAccel
        //
        this.checkHardwareAccel.Location = new System.Drawing.Point(6, 19);
        this.checkHardwareAccel.Name = "checkHardwareAccel";
        this.checkHardwareAccel.Size = new System.Drawing.Size(282, 18);
        this.checkHardwareAccel.TabIndex = 2;
        this.checkHardwareAccel.Text = "Hardware Acceleration (checked by default)";
        this.checkHardwareAccel.UseVisualStyleBackColor = true;
        this.checkHardwareAccel.Click += new System.EventHandler(this.checkHardwareAccel_Click);
        //
        // checkDoubleBuffering
        //
        this.checkDoubleBuffering.Location = new System.Drawing.Point(6, 42);
        this.checkDoubleBuffering.Name = "checkDoubleBuffering";
        this.checkDoubleBuffering.Size = new System.Drawing.Size(282, 17);
        this.checkDoubleBuffering.TabIndex = 4;
        this.checkDoubleBuffering.Text = "Use Double-Buffering";
        this.checkDoubleBuffering.UseVisualStyleBackColor = true;
        this.checkDoubleBuffering.Click += new System.EventHandler(this.checkDoubleBuffering_Click);
        //
        // group3DToothChart
        //
        this.group3DToothChart.Controls.Add(this.label4);
        this.group3DToothChart.Controls.Add(this.textSelected);
        this.group3DToothChart.Controls.Add(this.label3);
        this.group3DToothChart.Controls.Add(this.label2);
        this.group3DToothChart.Controls.Add(this.label1);
        this.group3DToothChart.Controls.Add(this.groupFilters);
        this.group3DToothChart.Controls.Add(this.gridFormats);
        this.group3DToothChart.Location = new System.Drawing.Point(28, 91);
        this.group3DToothChart.Name = "group3DToothChart";
        this.group3DToothChart.Size = new System.Drawing.Size(833, 455);
        this.group3DToothChart.TabIndex = 5;
        this.group3DToothChart.TabStop = false;
        this.group3DToothChart.Text = "Options For 3D Tooth Chart";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(60, 195);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(608, 16);
        this.label4.TabIndex = 15;
        this.label4.Text = " Formats are listed from most recommended on top to least recommended on bottom.";
        //
        // textSelected
        //
        this.textSelected.Location = new System.Drawing.Point(6, 192);
        this.textSelected.Name = "textSelected";
        this.textSelected.ReadOnly = true;
        this.textSelected.Size = new System.Drawing.Size(53, 20);
        this.textSelected.TabIndex = 14;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(3, 174);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(159, 16);
        this.label3.TabIndex = 13;
        this.label3.Text = "Currently selected format number";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 131);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(818, 45);
        this.label2.TabIndex = 12;
        this.label2.Text = resources.GetString("label2.Text");
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(818, 20);
        this.label1.TabIndex = 11;
        this.label1.Text = "Most users will never need to change any of these options.  These are only used w" + "hen the 3D tooth chart is not working properly.";
        //
        // groupFilters
        //
        this.groupFilters.Controls.Add(this.checkHardwareAccel);
        this.groupFilters.Controls.Add(this.checkDoubleBuffering);
        this.groupFilters.Controls.Add(this.checkAllFormats);
        this.groupFilters.Location = new System.Drawing.Point(6, 39);
        this.groupFilters.Name = "groupFilters";
        this.groupFilters.Size = new System.Drawing.Size(295, 88);
        this.groupFilters.TabIndex = 10;
        this.groupFilters.TabStop = false;
        this.groupFilters.Text = "OpenGL filters for list below";
        //
        // checkAllFormats
        //
        this.checkAllFormats.Location = new System.Drawing.Point(6, 64);
        this.checkAllFormats.Name = "checkAllFormats";
        this.checkAllFormats.Size = new System.Drawing.Size(282, 17);
        this.checkAllFormats.TabIndex = 9;
        this.checkAllFormats.Text = "Show All Formats";
        this.checkAllFormats.UseVisualStyleBackColor = true;
        this.checkAllFormats.Click += new System.EventHandler(this.checkAllFormats_Click);
        //
        // gridFormats
        //
        this.gridFormats.setHScrollVisible(false);
        this.gridFormats.Location = new System.Drawing.Point(6, 226);
        this.gridFormats.Name = "gridFormats";
        this.gridFormats.setScrollValue(0);
        this.gridFormats.Size = new System.Drawing.Size(821, 223);
        this.gridFormats.TabIndex = 8;
        this.gridFormats.setTitle("Available Graphics Formats");
        this.gridFormats.setTranslationName(null);
        this.gridFormats.CellClick = __MultiODGridClickEventHandler.combine(this.gridFormats.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridFormats_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // radioSimpleChart
        //
        this.radioSimpleChart.Location = new System.Drawing.Point(34, 36);
        this.radioSimpleChart.Name = "radioSimpleChart";
        this.radioSimpleChart.Size = new System.Drawing.Size(146, 19);
        this.radioSimpleChart.TabIndex = 6;
        this.radioSimpleChart.TabStop = true;
        this.radioSimpleChart.Text = "Use Simple Tooth Chart";
        this.radioSimpleChart.UseVisualStyleBackColor = true;
        this.radioSimpleChart.Click += new System.EventHandler(this.radioSimpleChart_Click);
        //
        // radioOpenGLChart
        //
        this.radioOpenGLChart.Location = new System.Drawing.Point(34, 56);
        this.radioOpenGLChart.Name = "radioOpenGLChart";
        this.radioOpenGLChart.Size = new System.Drawing.Size(242, 19);
        this.radioOpenGLChart.TabIndex = 7;
        this.radioOpenGLChart.TabStop = true;
        this.radioOpenGLChart.Text = "Use OpenGL Tooth Chart";
        this.radioOpenGLChart.UseVisualStyleBackColor = true;
        this.radioOpenGLChart.Click += new System.EventHandler(this.radioOpenGLChart_Click);
        //
        // radioDirectXChart
        //
        this.radioDirectXChart.Location = new System.Drawing.Point(34, 15);
        this.radioDirectXChart.Name = "radioDirectXChart";
        this.radioDirectXChart.Size = new System.Drawing.Size(233, 19);
        this.radioDirectXChart.TabIndex = 8;
        this.radioDirectXChart.TabStop = true;
        this.radioDirectXChart.Text = "Use DirectX Tooth Chart (recommended)";
        this.radioDirectXChart.UseVisualStyleBackColor = true;
        this.radioDirectXChart.Click += new System.EventHandler(this.radioDirectXChart_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(684, 557);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(786, 557);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormGraphics
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(892, 594);
        this.Controls.Add(this.radioDirectXChart);
        this.Controls.Add(this.radioOpenGLChart);
        this.Controls.Add(this.radioSimpleChart);
        this.Controls.Add(this.group3DToothChart);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormGraphics";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Graphics Preferences";
        this.Load += new System.EventHandler(this.FormGraphics_Load);
        this.group3DToothChart.ResumeLayout(false);
        this.group3DToothChart.PerformLayout();
        this.groupFilters.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formGraphics_Load(Object sender, EventArgs e) throws Exception {
        if (Environment.OSVersion.Platform == PlatformID.Unix)
        {
            //Force simple mode on Unix systems.
            MsgBox.show(this,"Linux users must use simple tooth chart.");
            radioDirectXChart.Enabled = false;
            radioOpenGLChart.Enabled = false;
            group3DToothChart.Enabled = false;
            return ;
        }
         
        //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
        checkHardwareAccel.Checked = ComputerPrefs.getLocalComputer().GraphicsUseHardware;
        checkDoubleBuffering.Checked = ComputerPrefs.getLocalComputer().GraphicsDoubleBuffering;
        selectedFormatNum = ComputerPrefs.getLocalComputer().PreferredPixelFormatNum;
        selectedDirectXFormat = ComputerPrefs.getLocalComputer().DirectXFormat;
        textSelected.Text = "";
        if (ComputerPrefs.getLocalComputer().GraphicsSimple == DrawingMode.Simple2D)
        {
            radioSimpleChart.Checked = true;
            group3DToothChart.Enabled = false;
        }
        else if (ComputerPrefs.getLocalComputer().GraphicsSimple == DrawingMode.DirectX)
        {
            radioDirectXChart.Checked = true;
            group3DToothChart.Enabled = true;
            groupFilters.Enabled = false;
        }
        else
        {
            //OpenGL
            radioOpenGLChart.Checked = true;
            group3DToothChart.Enabled = true;
            groupFilters.Enabled = true;
        }  
        refreshFormats();
    }

    private void fillGrid() throws Exception {
        int selectionIndex = -1;
        gridFormats.beginUpdate();
        gridFormats.getRows().Clear();
        if (this.radioDirectXChart.Checked)
        {
            textSelected.Text = "";
            gridFormats.beginUpdate();
            gridFormats.getColumns().Clear();
            ODGridColumn col = new ODGridColumn(Lan.g(this,"FormatNum"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Adapter"),60);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Accelerated"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Buffered"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"ColorBits"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"ColorFormat"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"DepthBits"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"DepthFormat"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Antialiasing"),75);
            gridFormats.getColumns().add(col);
            gridFormats.endUpdate();
            for (int i = 0;i < xformats.Length;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add((i + 1).ToString());
                row.getCells().Add(xformats[i].adapter.Adapter.ToString());
                row.getCells().add(xformats[i].deviceType == DeviceType.Hardware ? "Yes" : "No");
                //Supports hardware acceleration?
                row.getCells().add("Yes");
                //Supports double-buffering. All DirectX devices support double-buffering as required.
                row.getCells().Add(ToothChartDirectX.GetFormatBitCount(xformats[i].backBufferFormat).ToString());
                //Color bits.
                row.getCells().Add(Enum.GetName(Format.class, xformats[i].backBufferFormat));
                row.getCells().Add(ToothChartDirectX.GetDepthFormatBitCount(xformats[i].depthStencilFormat).ToString());
                //Depth buffer bits.
                row.getCells().Add(Enum.GetName(DepthFormat.class, xformats[i].depthStencilFormat));
                row.getCells().Add(ToothChartDirectX.GetMultiSampleNumberForType(xformats[i].maxMultiSampleType).ToString());
                gridFormats.getRows().add(row);
                if (StringSupport.equals(xformats[i].ToString(), selectedDirectXFormat))
                {
                    selectionIndex = i;
                    textSelected.Text = (i + 1).ToString();
                }
                 
            }
        }
        else if (this.radioOpenGLChart.Checked)
        {
            textSelected.Text = selectedFormatNum.ToString();
            gridFormats.beginUpdate();
            gridFormats.getColumns().Clear();
            ODGridColumn col = new ODGridColumn(Lan.g(this,"FormatNum"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"OpenGL"),60);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Windowed"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Bitmapped"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Palette"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Accelerated"),80);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Buffered"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"ColorBits"),75);
            gridFormats.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"DepthBits"),75);
            gridFormats.getColumns().add(col);
            gridFormats.endUpdate();
            for (int i = 0;i < formats.Length;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(formats[i].formatNumber.ToString());
                row.getCells().add(OpenGLWinFormsControl.FormatSupportsOpenGL(formats[i].pfd) ? "Yes" : "No");
                row.getCells().add(OpenGLWinFormsControl.FormatSupportsWindow(formats[i].pfd) ? "Yes" : "No");
                row.getCells().add(OpenGLWinFormsControl.FormatSupportsBitmap(formats[i].pfd) ? "Yes" : "No");
                row.getCells().add(OpenGLWinFormsControl.FormatUsesPalette(formats[i].pfd) ? "Yes" : "No");
                row.getCells().add(OpenGLWinFormsControl.FormatSupportsAcceleration(formats[i].pfd) ? "Yes" : "No");
                row.getCells().add(OpenGLWinFormsControl.FormatSupportsDoubleBuffering(formats[i].pfd) ? "Yes" : "No");
                row.getCells().Add(formats[i].pfd.cColorBits.ToString());
                row.getCells().Add(formats[i].pfd.cDepthBits.ToString());
                gridFormats.getRows().add(row);
                if (formats[i].formatNumber == selectedFormatNum)
                {
                    selectionIndex = i;
                }
                 
            }
        }
          
        gridFormats.endUpdate();
        if (selectionIndex >= 0)
        {
            gridFormats.setSelected(selectionIndex,true);
        }
         
    }

    /**
    * Get all formats for the grid based on the current filters.
    */
    private void refreshFormats() throws Exception {
        this.Cursor = Cursors.WaitCursor;
        gridFormats.getRows().Clear();
        gridFormats.Invalidate();
        textSelected.Text = "";
        Application.DoEvents();
        if (this.radioDirectXChart.Checked)
        {
            xformats = ToothChartDirectX.getStandardDeviceFormats();
        }
        else if (this.radioOpenGLChart.Checked)
        {
            OpenGLWinFormsControl contextFinder = new OpenGLWinFormsControl();
            //Get raw formats.
            Gdi.PIXELFORMATDESCRIPTOR[] rawformats = OpenGLWinFormsControl.GetPixelFormats(contextFinder.getHDC());
            if (checkAllFormats.Checked)
            {
                formats = new CodeBase.OpenGLWinFormsControl.PixelFormatValue[rawformats.Length];
                for (int i = 0;i < rawformats.Length;i++)
                {
                    formats[i] = new CodeBase.OpenGLWinFormsControl.PixelFormatValue();
                    formats[i].formatNumber = i + 1;
                    formats[i].pfd = rawformats[i];
                }
            }
            else
            {
                //Prioritize formats as requested by the user.
                formats = OpenGLWinFormsControl.PrioritizePixelFormats(rawformats, checkDoubleBuffering.Checked, checkHardwareAccel.Checked);
            } 
            contextFinder.Dispose();
        }
          
        //Update the format grid to reflect possible changes in formats.
        fillGrid();
        this.Cursor = Cursors.Default;
    }

    private void radioSimpleChart_Click(Object sender, EventArgs e) throws Exception {
        group3DToothChart.Enabled = false;
    }

    private void radioDirectXChart_Click(Object sender, EventArgs e) throws Exception {
        group3DToothChart.Enabled = true;
        groupFilters.Enabled = false;
        refreshFormats();
    }

    private void radioOpenGLChart_Click(Object sender, EventArgs e) throws Exception {
        group3DToothChart.Enabled = true;
        groupFilters.Enabled = true;
        refreshFormats();
    }

    private void checkHardwareAccel_Click(Object sender, EventArgs e) throws Exception {
        refreshFormats();
    }

    private void checkDoubleBuffering_Click(Object sender, EventArgs e) throws Exception {
        refreshFormats();
    }

    private void checkAllFormats_Click(Object sender, EventArgs e) throws Exception {
        checkHardwareAccel.Enabled = !checkAllFormats.Checked;
        checkDoubleBuffering.Enabled = !checkAllFormats.Checked;
        refreshFormats();
    }

    private void gridFormats_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int formatNum = Convert.ToInt32(gridFormats.getRows().get___idx(e.getRow()).getCells()[0].Text);
        textSelected.Text = formatNum.ToString();
        if (radioDirectXChart.Checked)
        {
            selectedDirectXFormat = xformats[formatNum - 1].ToString();
        }
        else if (this.radioOpenGLChart.Checked)
        {
            selectedFormatNum = formatNum;
        }
          
    }

    /**
    * CAUTION: Runs slowly. May take minutes. Returns the string "invalid" if no suitable Direct X format can be found.
    */
    public static String getPreferredDirectXFormat(Form callingForm) throws Exception {
        SparksToothChart.ToothChartDirectX.DirectXDeviceFormat[] possibleStandardFormats = ToothChartDirectX.getStandardDeviceFormats();
        for (int i = 0;i < possibleStandardFormats.Length;i++)
        {
            if (TestDirectXFormat(callingForm, possibleStandardFormats[i].ToString()))
            {
                return possibleStandardFormats[i].ToString();
            }
             
        }
        return "invalid";
    }

    /**
    * Returns true if the given directXFormat works for a DirectX tooth chart on the local computer.
    */
    public static boolean testDirectXFormat(Form callingForm, String directXFormat) throws Exception {
        ToothChartWrapper toothChartTest = new ToothChartWrapper();
        toothChartTest.Visible = false;
        //We add the invisible tooth chart to our form so that the device context will initialize properly
        //and our device creation test will then be accurate.
        callingForm.Controls.Add(toothChartTest);
        toothChartTest.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(directXFormat));
        toothChartTest.setDrawMode(DrawingMode.DirectX);
        //Creates the device.
        if (toothChartTest.getDrawMode() == DrawingMode.Simple2D)
        {
            //The chart is set back to 2D mode when there is an error initializing.
            callingForm.Controls.Remove(toothChartTest);
            toothChartTest.Dispose();
            return false;
        }
         
        try
        {
            //Now we check to be sure that one can draw and retrieve a screen shot from a DirectX control
            //using the specified device format.
            Bitmap screenShot = toothChartTest.getBitmap();
            screenShot.Dispose();
        }
        catch (Exception __dummyCatchVar0)
        {
            callingForm.Controls.Remove(toothChartTest);
            toothChartTest.Dispose();
            return false;
        }

        callingForm.Controls.Remove(toothChartTest);
        toothChartTest.Dispose();
        return true;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
        if (radioDirectXChart.Checked)
        {
            if (!TestDirectXFormat(this, selectedDirectXFormat))
            {
                MessageBox.Show(Lan.g(this,"Please choose a different device format, " + "the selected device format will not support the DirectX 3D tooth chart on this computer"));
                return ;
            }
             
            ComputerPrefs.getLocalComputer().GraphicsSimple = DrawingMode.DirectX;
            ComputerPrefs.getLocalComputer().DirectXFormat = selectedDirectXFormat;
        }
        else if (radioSimpleChart.Checked)
        {
            ComputerPrefs.getLocalComputer().GraphicsSimple = DrawingMode.Simple2D;
        }
        else
        {
            //OpenGL
            OpenGLWinFormsControl contextTester = new OpenGLWinFormsControl();
            try
            {
                if (contextTester.taoInitializeContexts(selectedFormatNum) != selectedFormatNum)
                {
                    throw new Exception(Lan.g(this,"Could not initialize pixel format ") + selectedFormatNum.ToString() + ".");
                }
                 
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Please choose a different pixel format, the selected pixel format will not support the 3D tooth chart on this computer: " + ex.Message));
                contextTester.Dispose();
                return ;
            }

            contextTester.Dispose();
            ComputerPrefs.getLocalComputer().GraphicsUseHardware = checkHardwareAccel.Checked;
            ComputerPrefs.getLocalComputer().GraphicsDoubleBuffering = checkDoubleBuffering.Checked;
            ComputerPrefs.getLocalComputer().PreferredPixelFormatNum = selectedFormatNum;
            ComputerPrefs.getLocalComputer().GraphicsSimple = DrawingMode.OpenGL;
        }  
        ComputerPrefs.update(ComputerPrefs.getLocalComputer());
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


