//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestToothChart;

import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PIn;

public class FormPerioTest  extends Form 
{

    public FormPerioTest() throws Exception {
        initializeComponent();
        //Assume that a hardware format on the default adapter using 16 bit color and 16 bit depth buffer
        //with 4X antialiasing will work for testing purposes.
        toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat("0;Hardware;32;D16;R5G6B5;FourSamples"));
        toothChart.setDrawMode(OpenDentBusiness.DrawingMode.DirectX);
    }

    private void formPerioTest_Load(Object sender, EventArgs e) throws Exception {
        toothChart.setColorBackground(Color.White);
        toothChart.setColorText(Color.Black);
        toothChart.setPerioMode(true);
        toothChart.setColorBleeding(butColorBleed.BackColor);
        toothChart.setColorSuppuration(butColorPus.BackColor);
        toothChart.setColorGingivalMargin(butColorGM.BackColor);
        toothChart.setColorCAL(butColorCAL.BackColor);
        toothChart.setColorMGJ(butColorMGJ.BackColor);
        toothChart.setColorProbing(butColorProbing.BackColor);
        toothChart.setColorProbingRed(butColorProbingRed.BackColor);
        toothChart.setColorFurcations(butColorFurc.BackColor);
        toothChart.setColorFurcationsRed(butColorFurcRed.BackColor);
        toothChart.setRedLimitProbing(PIn.Int(labelRedLimitProbing.Text));
        toothChart.setRedLimitFurcations(PIn.Int(labelRedLimitFurcations.Text));
        toothChart.setMissing("13");
        toothChart.setMissing("14");
        toothChart.setMissing("18");
        toothChart.setMissing("25");
        toothChart.setMissing("26");
        toothChart.SetImplant("14", Color.Gray);
        //Movements are too low of a priority to test right now.  We might not even want to implement them.
        //toothChart.MoveTooth("4",0,0,0,0,-5,0);
        //toothChart.MoveTooth("16",0,20,0,-3,0,0);
        //toothChart.MoveTooth("24",15,2,0,0,0,0);
        toothChart.SetMobility("2", "3", Color.Red);
        toothChart.SetMobility("7", "2", Color.Red);
        toothChart.SetMobility("8", "2", Color.Red);
        toothChart.SetMobility("9", "2", Color.Red);
        toothChart.SetMobility("10", "2", Color.Red);
        toothChart.SetMobility("16", "3", Color.Red);
        toothChart.SetMobility("24", "2", Color.Red);
        toothChart.SetMobility("31", "3", Color.Red);
        toothChart.addPerioMeasure(1,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
        toothChart.addPerioMeasure(2,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
        toothChart.addPerioMeasure(3,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
        toothChart.addPerioMeasure(5,PerioSequenceType.Furcation,1,-1,-1,-1,-1,-1);
        toothChart.addPerioMeasure(30,PerioSequenceType.Furcation,-1,-1,-1,-1,3,-1);
        for (int i = 1;i <= 32;i++)
        {
            //string tooth_id=i.ToString();
            //bleeding and suppuration on all MB sites
            //bleeding only all DL sites
            //suppuration only all B sites
            //blood=1, suppuration=2, both=3
            toothChart.addPerioMeasure(i,PerioSequenceType.Bleeding,3,2,-1,-1,-1,1);
            toothChart.addPerioMeasure(i,PerioSequenceType.GingMargin,0,1,1,1,0,0);
            toothChart.addPerioMeasure(i,PerioSequenceType.Probing,3,2,3,4,2,3);
            toothChart.addPerioMeasure(i,PerioSequenceType.CAL,3,3,4,5,2,3);
            //basically GingMargin+Probing, unless one of them is -1
            toothChart.addPerioMeasure(i,PerioSequenceType.MGJ,5,5,5,6,6,6);
        }
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        PrintDocument pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.Print();
    }

    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        Graphics g = ev.Graphics;
        Bitmap bitmap = toothChart.getBitmap();
        g.DrawImage(bitmap, 75, 75, bitmap.Width, bitmap.Height);
    }

    private void butColorGM_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorCAL_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorMGJ_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorProbing_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorProbingRed_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorFurc_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void butColorFurcRed_Click(Object sender, EventArgs e) throws Exception {
        showColor(sender);
    }

    private void showColor(Object sender) throws Exception {
        Color color = ((Button)sender).BackColor;
        int colorint = color.ToArgb();
        CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(colorint.ToString());
        msgbox.ShowDialog();
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
        SparksToothChart.ToothChartData toothChartData7 = new SparksToothChart.ToothChartData();
        this.butPrint = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelRedLimitProbing = new System.Windows.Forms.Label();
        this.labelRedLimitFurcations = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.butColorFurcRed = new System.Windows.Forms.Button();
        this.label9 = new System.Windows.Forms.Label();
        this.butColorProbingRed = new System.Windows.Forms.Button();
        this.label8 = new System.Windows.Forms.Label();
        this.butColorProbing = new System.Windows.Forms.Button();
        this.label7 = new System.Windows.Forms.Label();
        this.butColorFurc = new System.Windows.Forms.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.butColorMGJ = new System.Windows.Forms.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butColorCAL = new System.Windows.Forms.Button();
        this.butColorGM = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.butColorPus = new System.Windows.Forms.Button();
        this.butColorBleed = new System.Windows.Forms.Button();
        this.toothChart = new SparksToothChart.ToothChartWrapper();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butPrint
        //
        this.butPrint.Location = new System.Drawing.Point(691, 722);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 216;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelRedLimitProbing);
        this.groupBox1.Controls.Add(this.labelRedLimitFurcations);
        this.groupBox1.Controls.Add(this.label12);
        this.groupBox1.Controls.Add(this.label11);
        this.groupBox1.Controls.Add(this.label10);
        this.groupBox1.Controls.Add(this.butColorFurcRed);
        this.groupBox1.Controls.Add(this.label9);
        this.groupBox1.Controls.Add(this.butColorProbingRed);
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.butColorProbing);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.butColorFurc);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.butColorMGJ);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.butColorCAL);
        this.groupBox1.Controls.Add(this.butColorGM);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butColorPus);
        this.groupBox1.Controls.Add(this.butColorBleed);
        this.groupBox1.Location = new System.Drawing.Point(32, 695);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(427, 174);
        this.groupBox1.TabIndex = 217;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Setup Colors (not editable here)";
        //
        // labelRedLimitProbing
        //
        this.labelRedLimitProbing.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelRedLimitProbing.Location = new System.Drawing.Point(323, 144);
        this.labelRedLimitProbing.Name = "labelRedLimitProbing";
        this.labelRedLimitProbing.Size = new System.Drawing.Size(22, 18);
        this.labelRedLimitProbing.TabIndex = 73;
        this.labelRedLimitProbing.Text = "4";
        this.labelRedLimitProbing.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // labelRedLimitFurcations
        //
        this.labelRedLimitFurcations.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelRedLimitFurcations.Location = new System.Drawing.Point(323, 125);
        this.labelRedLimitFurcations.Name = "labelRedLimitFurcations";
        this.labelRedLimitFurcations.Size = new System.Drawing.Size(22, 18);
        this.labelRedLimitFurcations.TabIndex = 72;
        this.labelRedLimitFurcations.Text = "2";
        this.labelRedLimitFurcations.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(191, 144);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(130, 18);
        this.label12.TabIndex = 71;
        this.label12.Text = "Red Limit Probing";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(191, 126);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(130, 18);
        this.label11.TabIndex = 70;
        this.label11.Text = "Red Limit Furcations";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(222, 101);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(99, 18);
        this.label10.TabIndex = 69;
        this.label10.Text = "Furcations Red";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorFurcRed
        //
        this.butColorFurcRed.BackColor = System.Drawing.Color.DarkRed;
        this.butColorFurcRed.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorFurcRed.Location = new System.Drawing.Point(323, 99);
        this.butColorFurcRed.Name = "butColorFurcRed";
        this.butColorFurcRed.Size = new System.Drawing.Size(22, 22);
        this.butColorFurcRed.TabIndex = 68;
        this.butColorFurcRed.UseVisualStyleBackColor = false;
        this.butColorFurcRed.Click += new System.EventHandler(this.butColorFurcRed_Click);
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(243, 51);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(78, 18);
        this.label9.TabIndex = 67;
        this.label9.Text = "Probing Red";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorProbingRed
        //
        this.butColorProbingRed.BackColor = System.Drawing.Color.Red;
        this.butColorProbingRed.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorProbingRed.Location = new System.Drawing.Point(323, 49);
        this.butColorProbingRed.Name = "butColorProbingRed";
        this.butColorProbingRed.Size = new System.Drawing.Size(22, 22);
        this.butColorProbingRed.TabIndex = 66;
        this.butColorProbingRed.UseVisualStyleBackColor = false;
        this.butColorProbingRed.Click += new System.EventHandler(this.butColorProbingRed_Click);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(243, 26);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(78, 18);
        this.label8.TabIndex = 65;
        this.label8.Text = "Probing";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorProbing
        //
        this.butColorProbing.BackColor = System.Drawing.Color.Green;
        this.butColorProbing.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorProbing.Location = new System.Drawing.Point(323, 24);
        this.butColorProbing.Name = "butColorProbing";
        this.butColorProbing.Size = new System.Drawing.Size(22, 22);
        this.butColorProbing.TabIndex = 64;
        this.butColorProbing.UseVisualStyleBackColor = false;
        this.butColorProbing.Click += new System.EventHandler(this.butColorProbing_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(243, 76);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(78, 18);
        this.label7.TabIndex = 63;
        this.label7.Text = "Furcations";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorFurc
        //
        this.butColorFurc.BackColor = System.Drawing.Color.Black;
        this.butColorFurc.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorFurc.Location = new System.Drawing.Point(323, 74);
        this.butColorFurc.Name = "butColorFurc";
        this.butColorFurc.Size = new System.Drawing.Size(22, 22);
        this.butColorFurc.TabIndex = 62;
        this.butColorFurc.UseVisualStyleBackColor = false;
        this.butColorFurc.Click += new System.EventHandler(this.butColorFurc_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(6, 126);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(136, 18);
        this.label6.TabIndex = 61;
        this.label6.Text = "Mucogingival Junction";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorMGJ
        //
        this.butColorMGJ.BackColor = System.Drawing.Color.DarkOrange;
        this.butColorMGJ.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorMGJ.Location = new System.Drawing.Point(144, 124);
        this.butColorMGJ.Name = "butColorMGJ";
        this.butColorMGJ.Size = new System.Drawing.Size(22, 22);
        this.butColorMGJ.TabIndex = 59;
        this.butColorMGJ.UseVisualStyleBackColor = false;
        this.butColorMGJ.Click += new System.EventHandler(this.butColorMGJ_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(6, 101);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(136, 18);
        this.label3.TabIndex = 58;
        this.label3.Text = "Clinical Attachment Level";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(6, 76);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(136, 18);
        this.label4.TabIndex = 57;
        this.label4.Text = "Gingival Margin";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorCAL
        //
        this.butColorCAL.BackColor = System.Drawing.Color.MediumBlue;
        this.butColorCAL.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorCAL.Location = new System.Drawing.Point(144, 99);
        this.butColorCAL.Name = "butColorCAL";
        this.butColorCAL.Size = new System.Drawing.Size(22, 22);
        this.butColorCAL.TabIndex = 56;
        this.butColorCAL.UseVisualStyleBackColor = false;
        this.butColorCAL.Click += new System.EventHandler(this.butColorCAL_Click);
        //
        // butColorGM
        //
        this.butColorGM.BackColor = System.Drawing.Color.Purple;
        this.butColorGM.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorGM.ForeColor = System.Drawing.SystemColors.ControlText;
        this.butColorGM.Location = new System.Drawing.Point(144, 74);
        this.butColorGM.Name = "butColorGM";
        this.butColorGM.Size = new System.Drawing.Size(22, 22);
        this.butColorGM.TabIndex = 55;
        this.butColorGM.UseVisualStyleBackColor = false;
        this.butColorGM.Click += new System.EventHandler(this.butColorGM_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(64, 51);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(78, 18);
        this.label2.TabIndex = 54;
        this.label2.Text = "Suppuration";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(64, 26);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(78, 18);
        this.label1.TabIndex = 53;
        this.label1.Text = "Bleeding";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorPus
        //
        this.butColorPus.BackColor = System.Drawing.Color.Gold;
        this.butColorPus.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorPus.Location = new System.Drawing.Point(144, 49);
        this.butColorPus.Name = "butColorPus";
        this.butColorPus.Size = new System.Drawing.Size(22, 22);
        this.butColorPus.TabIndex = 52;
        this.butColorPus.UseVisualStyleBackColor = false;
        //
        // butColorBleed
        //
        this.butColorBleed.BackColor = System.Drawing.Color.Red;
        this.butColorBleed.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorBleed.Location = new System.Drawing.Point(144, 24);
        this.butColorBleed.Name = "butColorBleed";
        this.butColorBleed.Size = new System.Drawing.Size(22, 22);
        this.butColorBleed.TabIndex = 51;
        this.butColorBleed.UseVisualStyleBackColor = false;
        //
        // toothChart
        //
        this.toothChart.setAutoFinish(false);
        this.toothChart.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChart.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChart.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChart.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChart.Location = new System.Drawing.Point(66, 12);
        this.toothChart.Name = "toothChart";
        this.toothChart.setPerioMode(false);
        this.toothChart.setPreferredPixelFormatNumber(0);
        this.toothChart.Size = new System.Drawing.Size(700, 667);
        this.toothChart.TabIndex = 198;
        toothChartData7.setSizeControl(new System.Drawing.Size(700, 667));
        this.toothChart.setTcData(toothChartData7);
        this.toothChart.setUseHardware(false);
        //
        // FormPerioTest
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(846, 881);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.toothChart);
        this.Name = "FormPerioTest";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormPerioTest";
        this.Load += new System.EventHandler(this.FormPerioTest_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private SparksToothChart.ToothChartWrapper toothChart;
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butColorPus = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butColorBleed = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorMGJ = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorCAL = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butColorGM = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorFurc = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorProbing = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorProbingRed = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butColorFurcRed = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelRedLimitFurcations = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelRedLimitProbing = new System.Windows.Forms.Label();
}


