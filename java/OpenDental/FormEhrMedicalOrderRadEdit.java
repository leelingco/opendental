//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrders;
import OpenDentBusiness.ProviderC;

public class FormEhrMedicalOrderRadEdit  extends Form 
{

    public MedicalOrder MedOrderCur;
    public boolean IsNew = new boolean();
    public FormEhrMedicalOrderRadEdit() throws Exception {
        initializeComponent();
    }

    private void formMedicalOrderRadEdit_Load(Object sender, EventArgs e) throws Exception {
        textDateTime.Text = MedOrderCur.DateTimeOrder.ToString();
        checkIsDiscontinued.Checked = MedOrderCur.IsDiscontinued;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (MedOrderCur.ProvNum == ProviderC.getListShort()[i].ProvNum)
            {
                comboProv.SelectedIndex = i;
            }
             
        }
        //if a provider was subsequently hidden, the combobox may now be -1.
        textDescription.Text = MedOrderCur.Description;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (MessageBox.Show("Delete?", "Delete?", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
        {
            return ;
        }
         
        try
        {
            MedicalOrders.delete(MedOrderCur.MedicalOrderNum);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MessageBox.Show(this, "Please enter a description.");
            return ;
        }
         
        try
        {
            MedOrderCur.DateTimeOrder = PIn.DateT(textDateTime.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(this, "Please enter a Date Time with format DD/MM/YYYY HH:mm AM/PM");
        }

        MedOrderCur.Description = textDescription.Text;
        MedOrderCur.IsDiscontinued = checkIsDiscontinued.Checked;
        if (comboProv.SelectedIndex == -1)
        {
        }
        else
        {
            //don't make any changes to provnum.  0 is ok, but should never happen.  ProvNum might also be for a hidden prov.
            MedOrderCur.ProvNum = ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum;
        } 
        if (IsNew)
        {
            MedicalOrders.insert(MedOrderCur);
            EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            newMeasureEvent.DateTEvent = DateTime.Now;
            newMeasureEvent.EventType = EhrMeasureEventType.CPOE_RadOrdered;
            newMeasureEvent.PatNum = MedOrderCur.PatNum;
            newMeasureEvent.MoreInfo = "";
            newMeasureEvent.FKey = MedOrderCur.MedicalOrderNum;
            EhrMeasureEvents.insert(newMeasureEvent);
        }
        else
        {
            MedicalOrders.update(MedOrderCur);
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
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.labelDescription = new System.Windows.Forms.Label();
        this.textDateTime = new System.Windows.Forms.TextBox();
        this.labelDateTime = new System.Windows.Forms.Label();
        this.checkIsDiscontinued = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(322, 243);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 11;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(403, 243);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 243);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 12;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textDescription
        //
        this.textDescription.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textDescription.Location = new System.Drawing.Point(12, 101);
        this.textDescription.Multiline = true;
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(466, 136);
        this.textDescription.TabIndex = 16;
        //
        // labelDescription
        //
        this.labelDescription.Location = new System.Drawing.Point(11, 77);
        this.labelDescription.Name = "labelDescription";
        this.labelDescription.Size = new System.Drawing.Size(116, 21);
        this.labelDescription.TabIndex = 17;
        this.labelDescription.Text = "Instructions";
        this.labelDescription.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textDateTime
        //
        this.textDateTime.Location = new System.Drawing.Point(271, 13);
        this.textDateTime.Name = "textDateTime";
        this.textDateTime.Size = new System.Drawing.Size(207, 20);
        this.textDateTime.TabIndex = 18;
        //
        // labelDateTime
        //
        this.labelDateTime.Location = new System.Drawing.Point(153, 15);
        this.labelDateTime.Name = "labelDateTime";
        this.labelDateTime.Size = new System.Drawing.Size(116, 17);
        this.labelDateTime.TabIndex = 19;
        this.labelDateTime.Text = "Date Time";
        this.labelDateTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsDiscontinued
        //
        this.checkIsDiscontinued.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsDiscontinued.Location = new System.Drawing.Point(159, 41);
        this.checkIsDiscontinued.Name = "checkIsDiscontinued";
        this.checkIsDiscontinued.Size = new System.Drawing.Size(126, 18);
        this.checkIsDiscontinued.TabIndex = 23;
        this.checkIsDiscontinued.Text = "Discontinued";
        this.checkIsDiscontinued.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsDiscontinued.UseVisualStyleBackColor = true;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(153, 67);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(116, 17);
        this.label1.TabIndex = 30;
        this.label1.Text = "Provider";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.FormattingEnabled = true;
        this.comboProv.Location = new System.Drawing.Point(271, 64);
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(207, 21);
        this.comboProv.TabIndex = 29;
        //
        // FormMedicalOrderRadEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(490, 278);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.checkIsDiscontinued);
        this.Controls.Add(this.textDateTime);
        this.Controls.Add(this.labelDateTime);
        this.Controls.Add(this.labelDescription);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormMedicalOrderRadEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Radiology Order Edit";
        this.Load += new System.EventHandler(this.FormMedicalOrderRadEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDescription = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTime = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDateTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsDiscontinued = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProv = new System.Windows.Forms.ComboBox();
}


