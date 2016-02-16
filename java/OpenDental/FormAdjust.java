//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:34 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAdjust;
import OpenDental.FormProviderPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.Lans;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormAdjust  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private OpenDental.ValidDouble textAmount;
    private System.Windows.Forms.ListBox listTypePos = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listTypeNeg = new System.Windows.Forms.ListBox();
    private ArrayList PosIndex = new ArrayList();
    private OpenDental.ODtextBox textNote;
    private ArrayList NegIndex = new ArrayList();
    private Patient PatCur;
    private OpenDental.ValidDate textProcDate;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textAdjDate;
    private Adjustment AdjustmentCur;
    private OpenDental.ValidDate textDateEntry;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPickProv;
    private ComboBox comboProv = new ComboBox();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    /**
    * 
    */
    private DateTime dateLimit = DateTime.MinValue;
    private boolean checkZeroAmount = new boolean();
    //<summary>Keeps track of current server time so that user cannot bypass security by altering workstation clock.  Sometimes we compare to nowDate, but sometimes, we're just interested in the date of the adjustment.</summary>
    //private DateTime nowDate;
    /**
    * 
    */
    public FormAdjust(Patient patCur, Adjustment adjustmentCur) throws Exception {
        initializeComponent();
        PatCur = patCur;
        AdjustmentCur = adjustmentCur;
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAdjust.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textAdjDate = new OpenDental.ValidDate();
        this.label3 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.textAmount = new OpenDental.ValidDouble();
        this.listTypePos = new System.Windows.Forms.ListBox();
        this.listTypeNeg = new System.Windows.Forms.ListBox();
        this.textProcDate = new OpenDental.ValidDate();
        this.label7 = new System.Windows.Forms.Label();
        this.textDateEntry = new OpenDental.ValidDate();
        this.label8 = new System.Windows.Forms.Label();
        this.butPickProv = new OpenDental.UI.Button();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 54);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(104, 16);
        this.label1.TabIndex = 0;
        this.label1.Text = "Adjustment Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(175, 333);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 16);
        this.label4.TabIndex = 3;
        this.label4.Text = "Note";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(11, 102);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 4;
        this.label5.Text = "Amount";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(315, 14);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(167, 16);
        this.label6.TabIndex = 5;
        this.label6.Text = "Additions";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(11, 128);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 16);
        this.label2.TabIndex = 10;
        this.label2.Text = "Provider";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(614, 433);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 6;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(614, 471);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textAdjDate
        //
        this.textAdjDate.Location = new System.Drawing.Point(112, 52);
        this.textAdjDate.Name = "textAdjDate";
        this.textAdjDate.Size = new System.Drawing.Size(80, 20);
        this.textAdjDate.TabIndex = 0;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(528, 14);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(182, 16);
        this.label3.TabIndex = 16;
        this.label3.Text = "Subtractions";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(24, 469);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 17;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(112, 100);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(68, 20);
        this.textAmount.TabIndex = 1;
        //
        // listTypePos
        //
        this.listTypePos.Location = new System.Drawing.Point(299, 34);
        this.listTypePos.Name = "listTypePos";
        this.listTypePos.Size = new System.Drawing.Size(202, 264);
        this.listTypePos.TabIndex = 3;
        this.listTypePos.SelectedIndexChanged += new System.EventHandler(this.listTypePos_SelectedIndexChanged);
        //
        // listTypeNeg
        //
        this.listTypeNeg.Location = new System.Drawing.Point(515, 34);
        this.listTypeNeg.Name = "listTypeNeg";
        this.listTypeNeg.Size = new System.Drawing.Size(206, 264);
        this.listTypeNeg.TabIndex = 4;
        this.listTypeNeg.SelectedIndexChanged += new System.EventHandler(this.listTypeNeg_SelectedIndexChanged);
        //
        // textProcDate
        //
        this.textProcDate.Location = new System.Drawing.Point(112, 76);
        this.textProcDate.Name = "textProcDate";
        this.textProcDate.Size = new System.Drawing.Size(80, 20);
        this.textProcDate.TabIndex = 19;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(7, 78);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(104, 16);
        this.label7.TabIndex = 18;
        this.label7.Text = "(procedure date)";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(112, 28);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(80, 20);
        this.textDateEntry.TabIndex = 21;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(7, 30);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(104, 16);
        this.label8.TabIndex = 20;
        this.label8.Text = "Entry Date";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(271, 124);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 21);
        this.butPickProv.TabIndex = 165;
        this.butPickProv.Text = "...";
        this.butPickProv.Click += new System.EventHandler(this.butPickProv_Click);
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(112, 124);
        this.comboProv.MaxDropDownItems = 30;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(158, 21);
        this.comboProv.TabIndex = 164;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(112, 149);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(177, 21);
        this.comboClinic.TabIndex = 162;
        this.comboClinic.SelectionChangeCommitted += new System.EventHandler(this.comboClinic_SelectionChangeCommitted);
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(-4, 151);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(114, 16);
        this.labelClinic.TabIndex = 163;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(176, 354);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Adjustment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(355, 140);
        this.textNote.TabIndex = 0;
        //
        // FormAdjust
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(731, 528);
        this.Controls.Add(this.butPickProv);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textProcDate);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.listTypeNeg);
        this.Controls.Add(this.listTypePos);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textAdjDate);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAdjust";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Adjustment";
        this.Load += new System.EventHandler(this.FormAdjust_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAdjust_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            if (!Security.isAuthorized(Permissions.AdjustmentCreate,true))
            {
                //Date not checked here.  Message will show later.
                if (!Security.isAuthorized(Permissions.AdjustmentEditZero,true))
                {
                    //Let user create an adjustment of zero if they have this perm.
                    MessageBox.Show(Lans.g("Security","Not authorized for") + "\r\n" + GroupPermissions.getDesc(Permissions.AdjustmentCreate));
                    DialogResult = DialogResult.Cancel;
                    return ;
                }
                 
                //Make sure amount is 0 after OK click.
                checkZeroAmount = true;
            }
             
        }
        else
        {
            if (!Security.isAuthorized(Permissions.AdjustmentEdit,AdjustmentCur.AdjDate))
            {
                butOK.Enabled = false;
                butDelete.Enabled = false;
                //User can't edit but has edit zero amount perm.  Allow delete only if date is today.
                if (Security.isAuthorized(Permissions.AdjustmentEditZero,true) && AdjustmentCur.AdjAmt == 0 && AdjustmentCur.DateEntry.Date == MiscData.getNowDateTime().Date)
                {
                    butDelete.Enabled = true;
                }
                 
            }
             
        } 
        textDateEntry.Text = AdjustmentCur.DateEntry.ToShortDateString();
        textAdjDate.Text = AdjustmentCur.AdjDate.ToShortDateString();
        textProcDate.Text = AdjustmentCur.ProcDate.ToShortDateString();
        if (StringSupport.equals(DefC.getValue(DefCat.AdjTypes,AdjustmentCur.AdjType), "+"))
        {
            //pos
            textAmount.Text = AdjustmentCur.AdjAmt.ToString("F");
        }
        else
        {
            //neg
            textAmount.Text = (-AdjustmentCur.AdjAmt).ToString("F");
        } 
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            //shows without the neg sign
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (ProviderC.getListShort()[i].ProvNum == AdjustmentCur.ProvNum)
            {
                comboProv.SelectedIndex = i;
            }
             
        }
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            labelClinic.Visible = false;
            comboClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("none");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == AdjustmentCur.ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        for (int i = 0;i < DefC.getShort()[1].Length;i++)
        {
            //temp.AdjType
            if (StringSupport.equals(DefC.getShort()[1][i].ItemValue, "+"))
            {
                PosIndex.Add(i);
                listTypePos.Items.Add(DefC.getShort()[1][i].ItemName);
                if (DefC.getShort()[1][i].DefNum == AdjustmentCur.AdjType)
                    listTypePos.SelectedIndex = PosIndex.Count - 1;
                 
            }
            else if (StringSupport.equals(DefC.getShort()[1][i].ItemValue, "-"))
            {
                NegIndex.Add(i);
                listTypeNeg.Items.Add(DefC.getShort()[1][i].ItemName);
                if (DefC.getShort()[1][i].DefNum == AdjustmentCur.AdjType)
                    listTypeNeg.SelectedIndex = NegIndex.Count - 1;
                 
            }
              
        }
        //this.listProvNum.SelectedIndex=(int)temp.ProvNum;
        this.textNote.Text = AdjustmentCur.AdjNote;
    }

    private void listTypePos_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (listTypePos.SelectedIndex != -1)
            listTypeNeg.SelectedIndex = -1;
         
    }

    private void listTypeNeg_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (listTypeNeg.SelectedIndex != -1)
            listTypePos.SelectedIndex = -1;
         
    }

    private void butPickProv_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formp = new FormProviderPick();
        if (comboProv.SelectedIndex > -1)
        {
            formp.SelectedProvNum = ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum;
        }
         
        formp.ShowDialog();
        if (formp.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProv.SelectedIndex = Providers.getIndex(formp.SelectedProvNum);
    }

    //ProcCur.ProvNum=formp.SelectedProvNum;
    private void comboClinic_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
    }

    /*
    			if(comboClinic.SelectedIndex==0) {
    				ProcCur.ClinicNum=0;
    			}
    			else {
    				ProcCur.ClinicNum=Clinics.List[comboClinic.SelectedIndex-1].ClinicNum;
    			}
    			for(int i=0;i<ClaimProcsForProc.Count;i++) {
    				ClaimProcsForProc[i].ClinicNum=ProcCur.ClinicNum;
    			}*/
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textAdjDate.errorProvider1.GetError(textAdjDate), "") || !StringSupport.equals(textProcDate.errorProvider1.GetError(textProcDate), "") || !StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textAmount.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Please enter an amount."));
            return ;
        }
         
        if (listTypeNeg.SelectedIndex == -1 && listTypePos.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a type first.");
            return ;
        }
         
        if (IsNew)
        {
            //prevents backdating of initial adjustment
            if (!Security.IsAuthorized(Permissions.AdjustmentCreate, PIn.Date(textAdjDate.Text), true))
            {
                //Give message later.
                if (!checkZeroAmount)
                {
                    //Let user create as long as Amount is zero and has edit zero permissions.  This was checked on load.
                    MessageBox.Show(Lans.g("Security","Not authorized for") + "\r\n" + GroupPermissions.getDesc(Permissions.AdjustmentCreate));
                    return ;
                }
                 
            }
             
        }
        else
        {
            //Editing an old entry will already be blocked if the date was too old, and user will not be able to click OK button
            //This catches it if user changed the date to be older.
            if (!Security.IsAuthorized(Permissions.AdjustmentEdit, PIn.Date(textAdjDate.Text)))
            {
                return ;
            }
             
        } 
        //DateEntry not allowed to change
        AdjustmentCur.AdjDate = PIn.Date(textAdjDate.Text);
        AdjustmentCur.ProcDate = PIn.Date(textProcDate.Text);
        if (comboProv.SelectedIndex == -1)
        {
        }
        else
        {
            //might be a hidden provider, so don't change.
            //	AdjustmentCur.ProvNum=PatCur.PriProv;
            AdjustmentCur.ProvNum = ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum;
        } 
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            if (comboClinic.SelectedIndex == 0)
            {
                AdjustmentCur.ClinicNum = 0;
            }
            else
            {
                AdjustmentCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
            } 
        }
         
        if (listTypePos.SelectedIndex != -1)
        {
            AdjustmentCur.AdjType = DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][(int)PosIndex[listTypePos.SelectedIndex]].DefNum;
        }
         
        if (listTypeNeg.SelectedIndex != -1)
        {
            AdjustmentCur.AdjType = DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][(int)NegIndex[listTypeNeg.SelectedIndex]].DefNum;
        }
         
        if (StringSupport.equals(DefC.getValue(DefCat.AdjTypes,AdjustmentCur.AdjType), "+"))
        {
            //pos
            AdjustmentCur.AdjAmt = PIn.Double(textAmount.Text);
        }
        else
        {
            //neg
            AdjustmentCur.AdjAmt = -PIn.Double(textAmount.Text);
        } 
        if (checkZeroAmount)
        {
            if (AdjustmentCur.AdjAmt != 0)
            {
                MsgBox.show(this,"Amount has to be 0.00 due to security permission.");
                return ;
            }
             
        }
         
        AdjustmentCur.AdjNote = textNote.Text;
        try
        {
            if (IsNew)
            {
                Adjustments.insert(AdjustmentCur);
            }
            else
            {
                Adjustments.update(AdjustmentCur);
            } 
        }
        catch (Exception ex)
        {
            //even though it doesn't currently throw any exceptions
            MessageBox.Show(ex.Message);
            return ;
        }

        if (IsNew)
        {
            SecurityLogs.makeLogEntry(Permissions.AdjustmentCreate,AdjustmentCur.PatNum,Patients.getLim(AdjustmentCur.PatNum).getNameLF() + ", " + AdjustmentCur.AdjAmt.ToString("c"));
        }
        else
        {
            SecurityLogs.makeLogEntry(Permissions.AdjustmentEdit,AdjustmentCur.PatNum,Patients.getLim(AdjustmentCur.PatNum).getNameLF() + ", " + AdjustmentCur.AdjAmt.ToString("c"));
        } 
        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            SecurityLogs.makeLogEntry(Permissions.AdjustmentEdit,AdjustmentCur.PatNum,"Delete for patient: " + Patients.getLim(AdjustmentCur.PatNum).getNameLF() + ", " + AdjustmentCur.AdjAmt.ToString("c"));
            Adjustments.delete(AdjustmentCur);
            DialogResult = DialogResult.OK;
        } 
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


