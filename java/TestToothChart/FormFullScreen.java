//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestToothChart;


public class FormFullScreen  extends Form 
{

    //public ToothChartWrapper toothChartPub;
    public FormFullScreen() throws Exception {
        initializeComponent();
    }

    private void formFullScreen_Load(Object sender, EventArgs e) throws Exception {
    }

    private void formFullScreen_FormClosed(Object sender, FormClosedEventArgs e) throws Exception {
        this.toothChartForBig.Dispose();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        SparksToothChart.ToothChartData toothChartData1 = new SparksToothChart.ToothChartData();
        this.toothChartForBig = new SparksToothChart.ToothChartWrapper();
        this.SuspendLayout();
        //
        // toothChartForBig
        //
        this.toothChartForBig.setAutoFinish(false);
        this.toothChartForBig.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChartForBig.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChartForBig.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChartForBig.Dock = System.Windows.Forms.DockStyle.Fill;
        this.toothChartForBig.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChartForBig.Location = new System.Drawing.Point(0, 0);
        this.toothChartForBig.Name = "toothChartForBig";
        this.toothChartForBig.setPreferredPixelFormatNumber(0);
        this.toothChartForBig.Size = new System.Drawing.Size(1214, 821);
        this.toothChartForBig.TabIndex = 196;
        toothChartData1.setSizeControl(new System.Drawing.Size(1214, 821));
        this.toothChartForBig.setTcData(toothChartData1);
        this.toothChartForBig.setUseHardware(false);
        //
        // FormFullScreen
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1214, 821);
        this.Controls.Add(this.toothChartForBig);
        this.Name = "FormFullScreen";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormFullScreen";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormFullScreen_Load);
        this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.FormFullScreen_FormClosed);
        this.ResumeLayout(false);
    }

    public SparksToothChart.ToothChartWrapper toothChartForBig;
}
//Required for DirectX.

//Required for DirectX.