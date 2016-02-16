//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;
import OpenDental.FormEhrVitalsignEdit;

public class FormEhrVitalsignEdit  extends Form 
{

    public Vitalsign VitalsignCur;
    private Patient patCur;
    public FormEhrVitalsignEdit() throws Exception {
        initializeComponent();
    }

    private void formVitalsignEdit_Load(Object sender, EventArgs e) throws Exception {
        patCur = Patients.getPat(VitalsignCur.PatNum);
        if (patCur.getAge() > 0 && patCur.getAge() < 17)
        {
            //child
            groupBox1.Text = "";
            checkFollowup.Visible = false;
            checkIneligible.Text = "BMI measurement was not taken because patient is pregnant.";
        }
        else
        {
            checkNutrition.Visible = false;
            checkActivity.Visible = false;
        } 
        textDateTaken.Text = VitalsignCur.DateTaken.ToShortDateString();
        textDocumentation.Text = VitalsignCur.Documentation;
        textHeight.Text = VitalsignCur.Height.ToString();
        textWeight.Text = VitalsignCur.Weight.ToString();
        calcBMI();
        checkFollowup.Checked = VitalsignCur.HasFollowupPlan;
        checkNutrition.Checked = VitalsignCur.ChildGotNutrition;
        checkActivity.Checked = VitalsignCur.ChildGotPhysCouns;
        checkIneligible.Checked = VitalsignCur.IsIneligible;
        textBPd.Text = VitalsignCur.BpDiastolic.ToString();
        textBPs.Text = VitalsignCur.BpSystolic.ToString();
    }

    private void textWeight_TextChanged(Object sender, EventArgs e) throws Exception {
        calcBMI();
    }

    private void textHeight_TextChanged(Object sender, EventArgs e) throws Exception {
        calcBMI();
    }

    private void calcBMI() throws Exception {
        //BMI = (lbs*703)/(in^2)
        float height = new float();
        float weight = new float();
        try
        {
            height = float.Parse(textHeight.Text);
            weight = float.Parse(textWeight.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        if (height == 0)
        {
            return ;
        }
         
        if (weight == 0)
        {
            return ;
        }
         
        float bmi = Vitalsigns.calcBMI(weight,height);
        // ((float)(weight*703)/(height*height));
        textBMI.Text = bmi.ToString("n1");
        return ;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (VitalsignCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (MessageBox.Show("Delete?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        Vitalsigns.delete(VitalsignCur.VitalsignNum);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //validate
        DateTime date = new DateTime();
        if (StringSupport.equals(textDateTaken.Text, ""))
        {
            MessageBox.Show("Please enter a date.");
            return ;
        }
         
        try
        {
            date = DateTime.Parse(textDateTaken.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Please fix date first.");
            return ;
        }

        //validate height
        float height = 0;
        try
        {
            if (!StringSupport.equals(textHeight.Text, ""))
            {
                height = float.Parse(textHeight.Text);
            }
             
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show("Please fix height first.");
            return ;
        }

        //validate weight
        float weight = 0;
        try
        {
            if (!StringSupport.equals(textWeight.Text, ""))
            {
                weight = float.Parse(textWeight.Text);
            }
             
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show("Please fix weight first.");
            return ;
        }

        //validate bp
        int BPsys = 0;
        int BPdia = 0;
        try
        {
            if (!StringSupport.equals(textBPs.Text, ""))
            {
                BPsys = int.Parse(textBPs.Text);
            }
             
            if (!StringSupport.equals(textBPd.Text, ""))
            {
                BPdia = int.Parse(textBPd.Text);
            }
             
        }
        catch (Exception __dummyCatchVar4)
        {
            MessageBox.Show("Please fix BP first.");
            return ;
        }

        if (checkFollowup.Checked || checkIneligible.Checked)
        {
            if (StringSupport.equals(textDocumentation.Text, ""))
            {
                MessageBox.Show("Documentation must be entered.");
                return ;
            }
             
        }
         
        //save------------------------------------------------------------------
        VitalsignCur.DateTaken = date;
        VitalsignCur.Height = height;
        VitalsignCur.Weight = weight;
        VitalsignCur.BpDiastolic = BPdia;
        VitalsignCur.BpSystolic = BPsys;
        VitalsignCur.HasFollowupPlan = checkFollowup.Checked;
        VitalsignCur.ChildGotNutrition = checkNutrition.Checked;
        VitalsignCur.ChildGotPhysCouns = checkActivity.Checked;
        VitalsignCur.IsIneligible = checkIneligible.Checked;
        VitalsignCur.Documentation = textDocumentation.Text;
        if (VitalsignCur.getIsNew())
        {
            Vitalsigns.insert(VitalsignCur);
        }
        else
        {
            Vitalsigns.update(VitalsignCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrVitalsignEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.labelBMI = new System.Windows.Forms.Label();
        this.labelBP = new System.Windows.Forms.Label();
        this.labelWeight = new System.Windows.Forms.Label();
        this.labelHeight = new System.Windows.Forms.Label();
        this.textDateTaken = new System.Windows.Forms.TextBox();
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textBPd = new System.Windows.Forms.TextBox();
        this.textBPs = new System.Windows.Forms.TextBox();
        this.textWeight = new System.Windows.Forms.TextBox();
        this.textHeight = new System.Windows.Forms.TextBox();
        this.labelPound = new System.Windows.Forms.Label();
        this.labelDiv = new System.Windows.Forms.Label();
        this.textBMI = new System.Windows.Forms.TextBox();
        this.labelInch = new System.Windows.Forms.Label();
        this.checkFollowup = new System.Windows.Forms.CheckBox();
        this.textDocumentation = new System.Windows.Forms.TextBox();
        this.checkIneligible = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkActivity = new System.Windows.Forms.CheckBox();
        this.checkNutrition = new System.Windows.Forms.CheckBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(26, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(93, 17);
        this.label1.TabIndex = 0;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBMI
        //
        this.labelBMI.Location = new System.Drawing.Point(26, 130);
        this.labelBMI.Name = "labelBMI";
        this.labelBMI.Size = new System.Drawing.Size(93, 17);
        this.labelBMI.TabIndex = 2;
        this.labelBMI.Text = "BMI";
        this.labelBMI.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBP
        //
        this.labelBP.Location = new System.Drawing.Point(26, 103);
        this.labelBP.Name = "labelBP";
        this.labelBP.Size = new System.Drawing.Size(93, 17);
        this.labelBP.TabIndex = 4;
        this.labelBP.Text = "BP";
        this.labelBP.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelWeight
        //
        this.labelWeight.Location = new System.Drawing.Point(26, 74);
        this.labelWeight.Name = "labelWeight";
        this.labelWeight.Size = new System.Drawing.Size(93, 17);
        this.labelWeight.TabIndex = 5;
        this.labelWeight.Text = "Weight";
        this.labelWeight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelHeight
        //
        this.labelHeight.Location = new System.Drawing.Point(26, 48);
        this.labelHeight.Name = "labelHeight";
        this.labelHeight.Size = new System.Drawing.Size(93, 17);
        this.labelHeight.TabIndex = 6;
        this.labelHeight.Text = "Height";
        this.labelHeight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTaken
        //
        this.textDateTaken.Location = new System.Drawing.Point(122, 18);
        this.textDateTaken.Name = "textDateTaken";
        this.textDateTaken.Size = new System.Drawing.Size(100, 20);
        this.textDateTaken.TabIndex = 0;
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(471, 469);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(390, 469);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 469);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 10;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textBPd
        //
        this.textBPd.Location = new System.Drawing.Point(178, 101);
        this.textBPd.Name = "textBPd";
        this.textBPd.Size = new System.Drawing.Size(39, 20);
        this.textBPd.TabIndex = 4;
        //
        // textBPs
        //
        this.textBPs.Location = new System.Drawing.Point(122, 101);
        this.textBPs.Name = "textBPs";
        this.textBPs.Size = new System.Drawing.Size(40, 20);
        this.textBPs.TabIndex = 3;
        //
        // textWeight
        //
        this.textWeight.Location = new System.Drawing.Point(122, 73);
        this.textWeight.Name = "textWeight";
        this.textWeight.Size = new System.Drawing.Size(56, 20);
        this.textWeight.TabIndex = 2;
        this.textWeight.TextChanged += new System.EventHandler(this.textWeight_TextChanged);
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(122, 46);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(56, 20);
        this.textHeight.TabIndex = 1;
        this.textHeight.TextChanged += new System.EventHandler(this.textHeight_TextChanged);
        //
        // labelPound
        //
        this.labelPound.Location = new System.Drawing.Point(180, 76);
        this.labelPound.Name = "labelPound";
        this.labelPound.Size = new System.Drawing.Size(93, 17);
        this.labelPound.TabIndex = 15;
        this.labelPound.Text = "lbs.";
        this.labelPound.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelDiv
        //
        this.labelDiv.Location = new System.Drawing.Point(153, 103);
        this.labelDiv.Name = "labelDiv";
        this.labelDiv.Size = new System.Drawing.Size(32, 17);
        this.labelDiv.TabIndex = 16;
        this.labelDiv.Text = "/";
        this.labelDiv.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // textBMI
        //
        this.textBMI.Location = new System.Drawing.Point(122, 129);
        this.textBMI.Name = "textBMI";
        this.textBMI.ReadOnly = true;
        this.textBMI.Size = new System.Drawing.Size(40, 20);
        this.textBMI.TabIndex = 17;
        this.textBMI.TabStop = false;
        //
        // labelInch
        //
        this.labelInch.Location = new System.Drawing.Point(180, 47);
        this.labelInch.Name = "labelInch";
        this.labelInch.Size = new System.Drawing.Size(93, 17);
        this.labelInch.TabIndex = 18;
        this.labelInch.Text = "in.";
        this.labelInch.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // checkFollowup
        //
        this.checkFollowup.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkFollowup.Location = new System.Drawing.Point(8, 21);
        this.checkFollowup.Name = "checkFollowup";
        this.checkFollowup.Size = new System.Drawing.Size(506, 47);
        this.checkFollowup.TabIndex = 19;
        this.checkFollowup.Text = resources.GetString("checkFollowup.Text");
        this.checkFollowup.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkFollowup.UseVisualStyleBackColor = true;
        //
        // textDocumentation
        //
        this.textDocumentation.Location = new System.Drawing.Point(9, 219);
        this.textDocumentation.Multiline = true;
        this.textDocumentation.Name = "textDocumentation";
        this.textDocumentation.Size = new System.Drawing.Size(495, 58);
        this.textDocumentation.TabIndex = 20;
        //
        // checkIneligible
        //
        this.checkIneligible.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkIneligible.Location = new System.Drawing.Point(8, 98);
        this.checkIneligible.Name = "checkIneligible";
        this.checkIneligible.Size = new System.Drawing.Size(506, 102);
        this.checkIneligible.TabIndex = 21;
        this.checkIneligible.Text = resources.GetString("checkIneligible.Text");
        this.checkIneligible.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkIneligible.UseVisualStyleBackColor = true;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 199);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(155, 17);
        this.label3.TabIndex = 22;
        this.label3.Text = "Documentation";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.checkActivity);
        this.groupBox1.Controls.Add(this.checkNutrition);
        this.groupBox1.Controls.Add(this.checkFollowup);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.textDocumentation);
        this.groupBox1.Controls.Add(this.checkIneligible);
        this.groupBox1.Location = new System.Drawing.Point(26, 166);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(518, 289);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Documentation of a Followup Plan or Ineligibility";
        //
        // checkActivity
        //
        this.checkActivity.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkActivity.Location = new System.Drawing.Point(8, 80);
        this.checkActivity.Name = "checkActivity";
        this.checkActivity.Size = new System.Drawing.Size(249, 17);
        this.checkActivity.TabIndex = 25;
        this.checkActivity.Text = "Counseled for physical activity";
        this.checkActivity.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkActivity.UseVisualStyleBackColor = true;
        //
        // checkNutrition
        //
        this.checkNutrition.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkNutrition.Location = new System.Drawing.Point(8, 61);
        this.checkNutrition.Name = "checkNutrition";
        this.checkNutrition.Size = new System.Drawing.Size(249, 17);
        this.checkNutrition.TabIndex = 24;
        this.checkNutrition.Text = "Counseled for nutrition";
        this.checkNutrition.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkNutrition.UseVisualStyleBackColor = true;
        //
        // FormVitalsignEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(558, 504);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.labelInch);
        this.Controls.Add(this.textBMI);
        this.Controls.Add(this.textBPd);
        this.Controls.Add(this.textBPs);
        this.Controls.Add(this.labelDiv);
        this.Controls.Add(this.labelPound);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.textWeight);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textDateTaken);
        this.Controls.Add(this.labelHeight);
        this.Controls.Add(this.labelWeight);
        this.Controls.Add(this.labelBP);
        this.Controls.Add(this.labelBMI);
        this.Controls.Add(this.label1);
        this.Name = "FormVitalsignEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Vitalsign";
        this.Load += new System.EventHandler(this.FormVitalsignEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBMI = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBP = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelWeight = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelHeight = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTaken = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textBPd = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBPs = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textWeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textHeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelPound = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDiv = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBMI = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelInch = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkFollowup = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textDocumentation = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkIneligible = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkActivity = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkNutrition = new System.Windows.Forms.CheckBox();
}


