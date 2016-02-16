//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestForm;


public class Form1  extends Form 
{

    public Form1() throws Exception {
        initializeComponent();
    }

    private void form1_Load(Object sender, EventArgs e) throws Exception {
        setProcs();
    }

    private void setProcs() throws Exception {
        chart.SuspendLayout();
        chart.ResetTeeth();
        for (int i = 1;i <= 13;i++)
        {
            chart.SetInvisible(i.ToString());
            chart.SetImplant(i.ToString(), Color.Red);
        }
        for (int i = 2;i < 6;i++)
        {
            chart.SetCrown(i.ToString(), Color.Red);
        }
        for (int i = 22;i <= 32;i++)
        {
            chart.SetInvisible(i.ToString());
            chart.SetImplant(i.ToString(), Color.Red);
        }
        for (int i = 27;i < 31;i++)
        {
            chart.SetCrown(i.ToString(), Color.Red);
        }
        //chart.MoveTooth("2",30,0,0,0,0,0);
        /*chart.HideTooth("31");
        			chart.MoveTooth("32",0,30,0,8.5f,0,0);
        			chart.SetCrownColor("30",Color.DarkGoldenrod);
        			chart.SetSurfaceColors("3","MOD",Color.DarkRed);
        			chart.SetBigX("7",Color.Blue);
        			chart.SetToPrimary("13");*/
        chart.ResumeLayout();
    }

    private void button1_Click(Object sender, EventArgs e) throws Exception {
        setProcs();
    }

    /*chart.MoveTooth("13",45,0,0,0f,-2,0);
    			chart.SetInvisible("31");
    			chart.MoveTooth("32",0,30,0,8.5f,0,0);
    			chart.MoveTooth("24",0,0,-10,0,0,0);
    			chart.MoveTooth("23",20,0,10,0,0,0);
    			chart.MoveTooth("25",0,0,0,0,3,0);
    			chart.MoveTooth("8",0,0,0,-1,0,0);
    			chart.MoveTooth("9",0,0,0,-1,0,0);
    			chart.MoveTooth("11",0,80,0,0,-12,0);
    			chart.MoveTooth("1",0,0,30,0,0,2);
    			chart.MoveTooth("12",0,15,0,3,0,0);
    			*/
    /*for(int i=1;i<=32;i++){
    				if(i==3 || i==14 || i==19 || i==30){
    					continue;
    				}
    				chart.SetToPrimary(i.ToString());
    			}
    			chart.MoveTooth("3",0,0,0,0,-2,0);
    			chart.MoveTooth("14",0,0,0,0,-2,0);
    			chart.MoveTooth("19",0,0,0,0,-2,0);
    			chart.MoveTooth("30",0,0,0,0,-2,0);*/
    /*chart.SetToPrimary("3");
    			chart.SetToPrimary("4");
    			chart.SetInvisible("31");
    			chart.MoveTooth("32",0,30,0,8.5f,0,0);
    			chart.SetSurfaceColors("4","B",Color.Red);
    			chart.SetSurfaceColors("A","O",Color.Blue);
    			chart.SetInvisible("14");
    			chart.SetPontic("14",Color.Red);
    			chart.SetRCT("12",Color.Red);*/
    //for(int i=1;i<=14;i++){
    //	chart.SetRCT(i.ToString(),Color.Red);
    //}
    private void butRed_Click(Object sender, EventArgs e) throws Exception {
    }

    //chart.SetSurfaceColors("19","LMOD",Color.Red);
    private void butBlue_Click(Object sender, EventArgs e) throws Exception {
    }

    //chart.SetSurfaceColors("19","LMOD",Color.Blue);
    private void butInvalid_Click(Object sender, EventArgs e) throws Exception {
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
        this.button1 = new System.Windows.Forms.Button();
        this.butBlue = new System.Windows.Forms.Button();
        this.butRed = new System.Windows.Forms.Button();
        this.butInvalid = new System.Windows.Forms.Button();
        this.chart = new SparksToothChart.GraphicalToothChart();
        this.SuspendLayout();
        //
        // button1
        //
        this.button1.Location = new System.Drawing.Point(356, 12);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(75, 23);
        this.button1.TabIndex = 1;
        this.button1.Text = "refresh";
        this.button1.UseVisualStyleBackColor = true;
        this.button1.Click += new System.EventHandler(this.button1_Click);
        //
        // butBlue
        //
        this.butBlue.Location = new System.Drawing.Point(551, 12);
        this.butBlue.Name = "butBlue";
        this.butBlue.Size = new System.Drawing.Size(75, 23);
        this.butBlue.TabIndex = 2;
        this.butBlue.Text = "blue";
        this.butBlue.UseVisualStyleBackColor = true;
        this.butBlue.Click += new System.EventHandler(this.butBlue_Click);
        //
        // butRed
        //
        this.butRed.Location = new System.Drawing.Point(588, 41);
        this.butRed.Name = "butRed";
        this.butRed.Size = new System.Drawing.Size(75, 23);
        this.butRed.TabIndex = 3;
        this.butRed.Text = "red";
        this.butRed.UseVisualStyleBackColor = true;
        this.butRed.Click += new System.EventHandler(this.butRed_Click);
        //
        // butInvalid
        //
        this.butInvalid.Location = new System.Drawing.Point(230, 12);
        this.butInvalid.Name = "butInvalid";
        this.butInvalid.Size = new System.Drawing.Size(75, 23);
        this.butInvalid.TabIndex = 4;
        this.butInvalid.Text = "Invalidate";
        this.butInvalid.UseVisualStyleBackColor = true;
        this.butInvalid.Click += new System.EventHandler(this.butInvalid_Click);
        //
        // chart
        //
        this.chart.Location = new System.Drawing.Point(186, 105);
        this.chart.Name = "chart";
        this.chart.TabIndex = 5;
        //
        // Form1
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(943, 757);
        this.Controls.Add(this.chart);
        this.Controls.Add(this.butInvalid);
        this.Controls.Add(this.butRed);
        this.Controls.Add(this.butBlue);
        this.Controls.Add(this.button1);
        this.Name = "Form1";
        this.Text = "Form1";
        this.Load += new System.EventHandler(this.Form1_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button button1 = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butBlue = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRed = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butInvalid = new System.Windows.Forms.Button();
    private SparksToothChart.GraphicalToothChart chart = new SparksToothChart.GraphicalToothChart();
}
//chart.Invalidate();

//chart.Invalidate();