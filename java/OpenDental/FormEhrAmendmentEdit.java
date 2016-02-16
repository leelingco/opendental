//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormImages;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.AmendmentSource;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.EhrAmendments;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.YN;
import OpenDental.Properties.Resources;

public class FormEhrAmendmentEdit  extends Form 
{

    private EhrAmendment EhrAmendmentCur;
    public FormEhrAmendmentEdit(EhrAmendment ehrAmdCur) throws Exception {
        initializeComponent();
        EhrAmendmentCur = ehrAmdCur;
    }

    private void formEhrAmendmentEdit_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < Enum.GetNames(AmendmentSource.class).Length;i++)
        {
            comboSource.Items.Add(Enum.GetNames(AmendmentSource.class)[i]);
        }
        listAmdStatus.Items.Add(Lan.g(this,"Requested"));
        listAmdStatus.Items.Add(Lan.g(this,"Accepted"));
        listAmdStatus.Items.Add(Lan.g(this,"Denied"));
        if (EhrAmendmentCur.getIsNew())
        {
            return ;
        }
         
        if (EhrAmendmentCur.DateTAppend.Year > 1880)
        {
            labelScan.Visible = true;
            butScan.Text = "View";
        }
         
        if (EhrAmendmentCur.IsAccepted == YN.Yes)
        {
            listAmdStatus.SelectedIndex = 1;
        }
        else if (EhrAmendmentCur.IsAccepted == YN.No)
        {
            listAmdStatus.SelectedIndex = 2;
        }
        else
        {
            listAmdStatus.SelectedIndex = 0;
        }  
        comboSource.SelectedIndex = ((Enum)EhrAmendmentCur.Source).ordinal();
        textSourceName.Text = EhrAmendmentCur.SourceName;
        textDescription.Text = EhrAmendmentCur.Description;
        if (EhrAmendmentCur.DateTRequest.Year > 1880)
        {
            textDateReq.Text = EhrAmendmentCur.DateTRequest.ToShortDateString() + " " + EhrAmendmentCur.DateTRequest.ToShortTimeString();
        }
         
        if (EhrAmendmentCur.DateTAcceptDeny.Year > 1880)
        {
            textDateAcc.Text = EhrAmendmentCur.DateTAcceptDeny.ToShortDateString() + " " + EhrAmendmentCur.DateTAcceptDeny.ToShortTimeString();
        }
         
        if (EhrAmendmentCur.DateTAppend.Year > 1880)
        {
            textDateApp.Text = EhrAmendmentCur.DateTAppend.ToShortDateString() + " " + EhrAmendmentCur.DateTAppend.ToShortTimeString();
        }
         
    }

    private void butNowReq_Click(Object sender, EventArgs e) throws Exception {
        textDateReq.Text = DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString();
    }

    private void butNowAcc_Click(Object sender, EventArgs e) throws Exception {
        textDateAcc.Text = DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString();
    }

    private void butNowApp_Click(Object sender, EventArgs e) throws Exception {
        textDateApp.Text = DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString();
    }

    private void butScan_Click(Object sender, EventArgs e) throws Exception {
        FormImages FormI = new FormImages();
        EhrAmendment amendmentOld = EhrAmendmentCur;
        FormI.EhrAmendmentCur = EhrAmendmentCur;
        FormI.ShowDialog();
        EhrAmendmentCur = EhrAmendments.getOne(EhrAmendmentCur.EhrAmendmentNum);
        if (!StringSupport.equals(EhrAmendmentCur.FileName, ""))
        {
            labelScan.Visible = true;
            butScan.Text = "View";
            textDateApp.Text = DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString();
        }
        else
        {
            labelScan.Visible = false;
            butScan.Text = "Scan";
            textDateApp.Text = "";
        } 
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (EhrAmendmentCur.getIsNew())
        {
        }
        else
        {
            //no need to ask them
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete Amendment?"))
            {
                return ;
            }
             
        } 
        //in both cases, delete:
        ImageStore.cleanAmdAttach(EhrAmendmentCur.FileName);
        EhrAmendments.delete(EhrAmendmentCur.EhrAmendmentNum);
        DialogResult = DialogResult.OK;
    }

    //Causes grid to refresh in case this amendment is not new.
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            if (!StringSupport.equals(textDateReq.Text, ""))
            {
                DateTime.Parse(textDateReq.Text);
            }
             
            if (!StringSupport.equals(textDateAcc.Text, ""))
            {
                DateTime.Parse(textDateAcc.Text);
            }
             
            if (!StringSupport.equals(textDateApp.Text, ""))
            {
                DateTime.Parse(textDateApp.Text);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"The date entered does not match the required format.");
            return ;
        }

        if (comboSource.SelectedIndex == -1)
        {
            MessageBox.Show("Please select an amendment source.");
            return ;
        }
         
        if (StringSupport.equals(textSourceName.Text, ""))
        {
            MessageBox.Show("Please input a source name.");
            return ;
        }
         
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MessageBox.Show("Please input an amendment description.");
            return ;
        }
         
        YN status = YN.Unknown;
        if (listAmdStatus.SelectedIndex == 1)
        {
            status = YN.Yes;
        }
        else if (listAmdStatus.SelectedIndex == 2)
        {
            status = YN.No;
        }
        else
        {
            status = YN.Unknown;
        }  
        if (EhrAmendmentCur.getIsNew() && StringSupport.equals(textDateReq.Text, ""))
        {
            EhrAmendmentCur.DateTRequest = DateTime.Now;
        }
         
        //Accepted or Denied (not just U/requested)
        if ((status == YN.Yes || status == YN.No) && StringSupport.equals(textDateAcc.Text, ""))
        {
            if (EhrAmendmentCur.getIsNew() || EhrAmendmentCur.IsAccepted != status)
            {
                //if status has changed
                EhrAmendmentCur.DateTAcceptDeny = DateTime.Now;
            }
             
        }
         
        //automatically fill date
        if (StringSupport.equals(textDateReq.Text, ""))
        {
            EhrAmendmentCur.DateTRequest = DateTime.MinValue;
        }
        else
        {
            EhrAmendmentCur.DateTRequest = DateTime.Parse(textDateReq.Text);
        } 
        if (StringSupport.equals(textDateAcc.Text, ""))
        {
            EhrAmendmentCur.DateTAcceptDeny = DateTime.MinValue;
        }
        else
        {
            EhrAmendmentCur.DateTAcceptDeny = DateTime.Parse(textDateAcc.Text);
        } 
        if (StringSupport.equals(textDateApp.Text, ""))
        {
            EhrAmendmentCur.DateTAppend = DateTime.MinValue;
        }
        else
        {
            EhrAmendmentCur.DateTAppend = DateTime.Parse(textDateApp.Text);
        } 
        EhrAmendmentCur.IsAccepted = status;
        EhrAmendmentCur.Source = (AmendmentSource)comboSource.SelectedIndex;
        EhrAmendmentCur.SourceName = textSourceName.Text;
        EhrAmendmentCur.Description = textDescription.Text;
        EhrAmendments.update(EhrAmendmentCur);
        //always saved to db before entering this form
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formEhrAmendmentEdit_Closing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (EhrAmendmentCur.getIsNew())
        {
            EhrAmendments.delete(EhrAmendmentCur.EhrAmendmentNum);
            ImageStore.cleanAmdAttach(EhrAmendmentCur.FileName);
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
        this.label5 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.labelScan = new System.Windows.Forms.Label();
        this.comboSource = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textSourceName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.listAmdStatus = new System.Windows.Forms.ListBox();
        this.label8 = new System.Windows.Forms.Label();
        this.butNowReq = new OpenDental.UI.Button();
        this.butNowAcc = new OpenDental.UI.Button();
        this.butNowApp = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butScan = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDateAcc = new System.Windows.Forms.TextBox();
        this.textDateReq = new System.Windows.Forms.TextBox();
        this.textDateApp = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(68, 198);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(60, 39);
        this.label5.TabIndex = 16;
        this.label5.Text = "Description/Location";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(130, 199);
        this.textDescription.Multiline = true;
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(435, 54);
        this.textDescription.TabIndex = 17;
        //
        // labelScan
        //
        this.labelScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelScan.Location = new System.Drawing.Point(123, 279);
        this.labelScan.Name = "labelScan";
        this.labelScan.Size = new System.Drawing.Size(176, 16);
        this.labelScan.TabIndex = 111;
        this.labelScan.Text = "An amendment has been scanned";
        this.labelScan.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelScan.Visible = false;
        //
        // comboSource
        //
        this.comboSource.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSource.FormattingEnabled = true;
        this.comboSource.Location = new System.Drawing.Point(130, 145);
        this.comboSource.Name = "comboSource";
        this.comboSource.Size = new System.Drawing.Size(121, 21);
        this.comboSource.TabIndex = 114;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(42, 146);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(86, 17);
        this.label7.TabIndex = 113;
        this.label7.Text = "Source";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSourceName
        //
        this.textSourceName.Location = new System.Drawing.Point(130, 172);
        this.textSourceName.Multiline = true;
        this.textSourceName.Name = "textSourceName";
        this.textSourceName.Size = new System.Drawing.Size(435, 21);
        this.textSourceName.TabIndex = 119;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(42, 171);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(87, 21);
        this.label2.TabIndex = 120;
        this.label2.Text = "Source Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(42, 17);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(86, 17);
        this.label3.TabIndex = 129;
        this.label3.Text = "Date Requested";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(7, 44);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(121, 17);
        this.label4.TabIndex = 130;
        this.label4.Text = "Date Accepted/Denied";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(7, 71);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(121, 17);
        this.label6.TabIndex = 131;
        this.label6.Text = "Date Appended";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listAmdStatus
        //
        this.listAmdStatus.FormattingEnabled = true;
        this.listAmdStatus.Location = new System.Drawing.Point(130, 98);
        this.listAmdStatus.Name = "listAmdStatus";
        this.listAmdStatus.Size = new System.Drawing.Size(120, 43);
        this.listAmdStatus.TabIndex = 138;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(43, 98);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(86, 17);
        this.label8.TabIndex = 139;
        this.label8.Text = "Status";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butNowReq
        //
        this.butNowReq.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNowReq.setAutosize(true);
        this.butNowReq.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNowReq.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNowReq.setCornerRadius(4F);
        this.butNowReq.Location = new System.Drawing.Point(304, 13);
        this.butNowReq.Name = "butNowReq";
        this.butNowReq.Size = new System.Drawing.Size(75, 24);
        this.butNowReq.TabIndex = 134;
        this.butNowReq.Text = "Now";
        this.butNowReq.Click += new System.EventHandler(this.butNowReq_Click);
        //
        // butNowAcc
        //
        this.butNowAcc.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNowAcc.setAutosize(true);
        this.butNowAcc.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNowAcc.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNowAcc.setCornerRadius(4F);
        this.butNowAcc.Location = new System.Drawing.Point(304, 40);
        this.butNowAcc.Name = "butNowAcc";
        this.butNowAcc.Size = new System.Drawing.Size(75, 24);
        this.butNowAcc.TabIndex = 133;
        this.butNowAcc.Text = "Now";
        this.butNowAcc.Click += new System.EventHandler(this.butNowAcc_Click);
        //
        // butNowApp
        //
        this.butNowApp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNowApp.setAutosize(true);
        this.butNowApp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNowApp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNowApp.setCornerRadius(4F);
        this.butNowApp.Location = new System.Drawing.Point(304, 67);
        this.butNowApp.Name = "butNowApp";
        this.butNowApp.Size = new System.Drawing.Size(75, 24);
        this.butNowApp.TabIndex = 132;
        this.butNowApp.Text = "Now";
        this.butNowApp.Click += new System.EventHandler(this.butNowApp_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(17, 275);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 124;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butScan
        //
        this.butScan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butScan.setAutosize(true);
        this.butScan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butScan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butScan.setCornerRadius(4F);
        this.butScan.Location = new System.Drawing.Point(301, 275);
        this.butScan.Name = "butScan";
        this.butScan.Size = new System.Drawing.Size(75, 24);
        this.butScan.TabIndex = 123;
        this.butScan.Text = "Scan";
        this.butScan.Click += new System.EventHandler(this.butScan_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(517, 275);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 122;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(436, 275);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 121;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDateAcc
        //
        this.textDateAcc.Location = new System.Drawing.Point(130, 42);
        this.textDateAcc.Multiline = true;
        this.textDateAcc.Name = "textDateAcc";
        this.textDateAcc.Size = new System.Drawing.Size(168, 21);
        this.textDateAcc.TabIndex = 140;
        //
        // textDateReq
        //
        this.textDateReq.Location = new System.Drawing.Point(130, 15);
        this.textDateReq.Multiline = true;
        this.textDateReq.Name = "textDateReq";
        this.textDateReq.Size = new System.Drawing.Size(168, 21);
        this.textDateReq.TabIndex = 141;
        //
        // textDateApp
        //
        this.textDateApp.Location = new System.Drawing.Point(130, 69);
        this.textDateApp.Multiline = true;
        this.textDateApp.Name = "textDateApp";
        this.textDateApp.Size = new System.Drawing.Size(168, 21);
        this.textDateApp.TabIndex = 142;
        //
        // FormEhrAmendmentEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(604, 310);
        this.Controls.Add(this.textDateApp);
        this.Controls.Add(this.textDateReq);
        this.Controls.Add(this.textDateAcc);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.listAmdStatus);
        this.Controls.Add(this.butNowReq);
        this.Controls.Add(this.butNowAcc);
        this.Controls.Add(this.butNowApp);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butScan);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textSourceName);
        this.Controls.Add(this.comboSource);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.labelScan);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label5);
        this.Name = "FormEhrAmendmentEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Amendment";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormEhrAmendmentEdit_Closing);
        this.Load += new System.EventHandler(this.FormEhrAmendmentEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelScan = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSource = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSourceName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butScan;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butNowApp;
    private OpenDental.UI.Button butNowAcc;
    private OpenDental.UI.Button butNowReq;
    private System.Windows.Forms.ListBox listAmdStatus = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateAcc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateReq = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateApp = new System.Windows.Forms.TextBox();
}


