//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.Family;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;


/**
* Not actually specific to eCW.  Using this for all HL7 interfaces where hl7def.ShowDemographics=Hide.
*/
public class ContrFamilyEcw  extends UserControl 
{

    private Patient PatCur;
    private Family FamCur;
    public ContrFamilyEcw() throws Exception {
        initializeComponent();
    }

    public void moduleSelected(long patNum) throws Exception {
        refreshModuleData(patNum);
        refreshModuleScreen();
    }

    private void refreshModuleData(long patNum) throws Exception {
        if (patNum == 0)
        {
            PatCur = null;
            FamCur = null;
            return ;
        }
         
        FamCur = Patients.getFamily(patNum);
        PatCur = FamCur.getPatient(patNum);
    }

    private void refreshModuleScreen() throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        if (PIn.Bool(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"FeeSchedulesSetManually")))
        {
            comboFeeSched.Enabled = true;
        }
        else
        {
            comboFeeSched.Enabled = false;
        } 
        comboFeeSched.Items.Clear();
        comboFeeSched.Items.Add(Lan.g(this,"none"));
        comboFeeSched.SelectedIndex = 0;
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            comboFeeSched.Items.Add(FeeSchedC.getListShort()[i].Description);
            if (FeeSchedC.getListShort()[i].FeeSchedNum == PatCur.FeeSched)
            {
                comboFeeSched.SelectedIndex = i + 1;
            }
             
        }
    }

    private void comboFeeSched_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        Patient oldPat = PatCur.copy();
        if (comboFeeSched.SelectedIndex == 0)
        {
            PatCur.FeeSched = 0;
        }
        else
        {
            PatCur.FeeSched = FeeSchedC.getListShort()[comboFeeSched.SelectedIndex - 1].FeeSchedNum;
        } 
        Patients.update(PatCur,oldPat);
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
        this.label1 = new System.Windows.Forms.Label();
        this.comboFeeSched = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(26, 80);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 1;
        this.label1.Text = "Fee Schedule";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboFeeSched
        //
        this.comboFeeSched.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboFeeSched.FormattingEnabled = true;
        this.comboFeeSched.Location = new System.Drawing.Point(129, 78);
        this.comboFeeSched.MaxDropDownItems = 30;
        this.comboFeeSched.Name = "comboFeeSched";
        this.comboFeeSched.Size = new System.Drawing.Size(198, 21);
        this.comboFeeSched.TabIndex = 21;
        this.comboFeeSched.SelectionChangeCommitted += new System.EventHandler(this.comboFeeSched_SelectionChangeCommitted);
        //
        // ContrFamilyEcw
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.comboFeeSched);
        this.Controls.Add(this.label1);
        this.Name = "ContrFamilyEcw";
        this.Size = new System.Drawing.Size(933, 743);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboFeeSched = new System.Windows.Forms.ComboBox();
}


