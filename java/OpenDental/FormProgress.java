//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.FormProgress;
import OpenDental.Lan;

/**
* Summary description for FormBasicTemplate.
*/
public class FormProgress  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ProgressBar progressBar1 = new System.Windows.Forms.ProgressBar();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    //<summary></summary>
    //public string FileName;
    /**
    * 
    */
    public double MaxVal = new double();
    private System.Windows.Forms.Label labelProgress = new System.Windows.Forms.Label();
    /**
    * 
    */
    public double CurrentVal = new double();
    /**
    * eg: ?currentVal MB of ?maxVal MB copied.  The two parameters will be replaced by numbers using the format based on NumberFormat.  If there are no parameters, then it will just display the text as is.
    */
    public String DisplayText = new String();
    /**
    * F for fixed.2, N to include comma, etc.
    */
    public String NumberFormat = new String();
    /**
    * Since only int values are allowed for progress bar, this allows you to use a double for the current and max.  The true value of the progress bar will be obtained by multiplying the double by the number here.  For example, 100 if you want to show MB like this: 3.15 MB.  The current value might be 3.1496858596859609.  This will set the currentValue of the progress bar to 315.
    */
    public int NumberMultiplication = new int();
    private Label labelError = new Label();
    public String ErrorMessage = new String();
    /**
    * Sets the number of milliseconds between ticks. Must be >0, default is 200.
    */
    public int TickMS = new int();
    /**
    * 
    */
    public FormProgress() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProgress.class);
        this.butCancel = new OpenDental.UI.Button();
        this.progressBar1 = new System.Windows.Forms.ProgressBar();
        this.label1 = new System.Windows.Forms.Label();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.labelProgress = new System.Windows.Forms.Label();
        this.labelError = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(376, 215);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // progressBar1
        //
        this.progressBar1.Location = new System.Drawing.Point(73, 99);
        this.progressBar1.Name = "progressBar1";
        this.progressBar1.Size = new System.Drawing.Size(377, 23);
        this.progressBar1.TabIndex = 2;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(71, 69);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Progress";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // timer1
        //
        this.timer1.Enabled = true;
        this.timer1.Interval = 200;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // labelProgress
        //
        this.labelProgress.Location = new System.Drawing.Point(71, 135);
        this.labelProgress.Name = "labelProgress";
        this.labelProgress.Size = new System.Drawing.Size(402, 55);
        this.labelProgress.TabIndex = 4;
        this.labelProgress.Text = "Preparing for Upload";
        //
        // labelError
        //
        this.labelError.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelError.ForeColor = System.Drawing.Color.DarkRed;
        this.labelError.Location = new System.Drawing.Point(32, 13);
        this.labelError.Name = "labelError";
        this.labelError.Size = new System.Drawing.Size(456, 41);
        this.labelError.TabIndex = 5;
        this.labelError.Text = "Error Message";
        this.labelError.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormProgress
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(500, 294);
        this.Controls.Add(this.labelError);
        this.Controls.Add(this.labelProgress);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.progressBar1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProgress";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormProgress_Load);
        this.ResumeLayout(false);
    }

    private void formProgress_Load(Object sender, System.EventArgs e) throws Exception {
        progressBar1.Maximum = (int)(MaxVal * NumberMultiplication);
        labelError.Visible = false;
        if (TickMS != null && TickMS > 0)
        {
            timer1.Interval = TickMS;
        }
         
    }

    /**
    * Happens every 200 ms
    */
    private void timer1_Tick(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.Default;
        if (!String.IsNullOrEmpty(ErrorMessage))
        {
            labelError.Visible = true;
            labelError.Text = ErrorMessage;
        }
         
        //and this form will also not close because the currentVal will never reach the maxVal.
        //progress bar shows 0 maxVal size
        progressBar1.Maximum = (int)(MaxVal * NumberMultiplication);
        String progress = DisplayText.Replace("?currentVal", CurrentVal.ToString(NumberFormat));
        progress = progress.Replace("?maxVal", MaxVal.ToString(NumberFormat));
        labelProgress.Text = progress;
        //=((double)CurrentVal/1024).ToString("F")+" MB of "
        //+((double)MaxVal/1024).ToString("F")+" MB copied";
        if (CurrentVal < MaxVal)
        {
            progressBar1.Value = (int)(CurrentVal * (double)NumberMultiplication);
        }
        else
        {
            //must be done.
            //progressBar1.Value=progressBar1.Maximum;
            DialogResult = DialogResult.OK;
        } 
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.Default;
        //probably not needed
        DialogResult = DialogResult.Cancel;
    }

}


