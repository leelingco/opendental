//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;

import OpenDental.Lan;
import OpenDentBusiness.YN;


/**
* 
*/
public class ContrYN  extends UserControl 
{

    /**
    * 
    */
    public ContrYN() throws Exception {
        initializeComponent();
        Lan.C(this, new Control[]{ checkY, checkN });
        checkN.Location = new Point(checkY.Right + 10, checkN.Top);
    }

    /**
    * Gets or sets the value of the control
    */
    public YN getCurrentValue() throws Exception {
        if (checkY.Checked)
        {
            return YN.Yes;
        }
        else if (checkN.Checked)
        {
            return YN.No;
        }
          
        return YN.Unknown;
    }

    public void setCurrentValue(YN value) throws Exception {
        if (value == YN.Yes)
        {
            checkY.Checked = true;
            checkN.Checked = false;
        }
        else if (value == YN.No)
        {
            checkY.Checked = false;
            checkN.Checked = true;
        }
        else
        {
            checkY.Checked = false;
            checkN.Checked = false;
        }  
    }

    private void checkY_Click(Object sender, EventArgs e) throws Exception {
        if (checkY.Checked)
        {
            checkN.Checked = false;
        }
         
    }

    private void checkN_Click(Object sender, EventArgs e) throws Exception {
        if (checkN.Checked)
        {
            checkY.Checked = false;
        }
         
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
        this.checkY = new System.Windows.Forms.CheckBox();
        this.checkN = new System.Windows.Forms.CheckBox();
        this.SuspendLayout();
        //
        // checkY
        //
        this.checkY.AutoSize = true;
        this.checkY.Location = new System.Drawing.Point(0, 3);
        this.checkY.Name = "checkY";
        this.checkY.Size = new System.Drawing.Size(44, 17);
        this.checkY.TabIndex = 0;
        this.checkY.Text = "Yes";
        this.checkY.UseVisualStyleBackColor = true;
        this.checkY.Click += new System.EventHandler(this.checkY_Click);
        //
        // checkN
        //
        this.checkN.AutoSize = true;
        this.checkN.Location = new System.Drawing.Point(55, 3);
        this.checkN.Name = "checkN";
        this.checkN.Size = new System.Drawing.Size(40, 17);
        this.checkN.TabIndex = 1;
        this.checkN.Text = "No";
        this.checkN.UseVisualStyleBackColor = true;
        this.checkN.Click += new System.EventHandler(this.checkN_Click);
        //
        // ContrYN
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.checkN);
        this.Controls.Add(this.checkY);
        this.Name = "ContrYN";
        this.Size = new System.Drawing.Size(231, 21);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.CheckBox checkY = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkN = new System.Windows.Forms.CheckBox();
}


