//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDental.FormEhrMeasureEventEdit;


/**
* Only used for editing smoking documentation.
*/
public class FormEhrMeasureEventEdit  extends Form 
{

    public EhrMeasureEvent MeasCur;
    public FormEhrMeasureEventEdit() throws Exception {
        initializeComponent();
    }

    private void formEhrMeasureEventEdit_Load(Object sender, EventArgs e) throws Exception {
        textDateTime.Text = MeasCur.DateTEvent.ToString();
        if (MeasCur.EventType == EhrMeasureEventType.TobaccoUseAssessed)
        {
            Loinc lCur = Loincs.getByCode(MeasCur.CodeValueEvent);
            //TobaccoUseAssessed events can be one of three types, all LOINC codes
            if (lCur != null)
            {
                textType.Text = lCur.NameLongCommon;
            }
             
            //Example: History of tobacco use Narrative
            Snomed sCur = Snomeds.getByCode(MeasCur.CodeValueResult);
            //TobaccoUseAssessed results can be any SNOMEDCT code, we recommend one of 8 codes, but the CQM measure allows 54 codes and we let the user select any SNOMEDCT they want
            if (sCur != null)
            {
                textResult.Text = sCur.Description;
            }
             
        }
         
        //Examples: Non-smoker (finding) or Smoker (finding)
        if (StringSupport.equals(textType.Text, ""))
        {
            //if not set by LOINC name above, then either not a TobaccoUseAssessed event or the code was not in the LOINC table, fill with EventType
            textType.Text = MeasCur.EventType.ToString();
        }
         
        textMoreInfo.Text = MeasCur.MoreInfo;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        EhrMeasureEvents.delete(MeasCur.EhrMeasureEventNum);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //inserts never happen here.  Only updates.
        MeasCur.MoreInfo = textMoreInfo.Text;
        EhrMeasureEvents.update(MeasCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrMeasureEventEdit.class);
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.textMoreInfo = new System.Windows.Forms.TextBox();
        this.labelDateTime = new System.Windows.Forms.Label();
        this.textDateTime = new System.Windows.Forms.TextBox();
        this.textType = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butDelete = new System.Windows.Forms.Button();
        this.textResult = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(360, 163);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(441, 163);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textMoreInfo
        //
        this.textMoreInfo.Location = new System.Drawing.Point(199, 95);
        this.textMoreInfo.Multiline = true;
        this.textMoreInfo.Name = "textMoreInfo";
        this.textMoreInfo.Size = new System.Drawing.Size(317, 55);
        this.textMoreInfo.TabIndex = 0;
        //
        // labelDateTime
        //
        this.labelDateTime.Location = new System.Drawing.Point(79, 18);
        this.labelDateTime.Name = "labelDateTime";
        this.labelDateTime.Size = new System.Drawing.Size(116, 17);
        this.labelDateTime.TabIndex = 19;
        this.labelDateTime.Text = "Date Time";
        this.labelDateTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTime
        //
        this.textDateTime.Location = new System.Drawing.Point(199, 17);
        this.textDateTime.Name = "textDateTime";
        this.textDateTime.ReadOnly = true;
        this.textDateTime.Size = new System.Drawing.Size(140, 20);
        this.textDateTime.TabIndex = 18;
        //
        // textType
        //
        this.textType.Location = new System.Drawing.Point(199, 43);
        this.textType.Name = "textType";
        this.textType.ReadOnly = true;
        this.textType.Size = new System.Drawing.Size(317, 20);
        this.textType.TabIndex = 20;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(79, 44);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(116, 17);
        this.label1.TabIndex = 21;
        this.label1.Text = "Event";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 96);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(183, 17);
        this.label2.TabIndex = 22;
        this.label2.Text = "More information about the event.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butDelete
        //
        this.butDelete.Anchor = System.Windows.Forms.AnchorStyles.None;
        this.butDelete.Location = new System.Drawing.Point(15, 163);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 23;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textResult
        //
        this.textResult.Location = new System.Drawing.Point(199, 69);
        this.textResult.Name = "textResult";
        this.textResult.ReadOnly = true;
        this.textResult.Size = new System.Drawing.Size(317, 20);
        this.textResult.TabIndex = 24;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(79, 70);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(116, 17);
        this.label3.TabIndex = 25;
        this.label3.Text = "Result";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormEhrMeasureEventEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(530, 198);
        this.Controls.Add(this.textResult);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textType);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDateTime);
        this.Controls.Add(this.labelDateTime);
        this.Controls.Add(this.textMoreInfo);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrMeasureEventEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EhrMeasureEvent Edit";
        this.Load += new System.EventHandler(this.FormEhrMeasureEventEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textMoreInfo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDateTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textResult = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


