//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;

public class FormSheetPicker  extends Form 
{

    public SheetTypeEnum SheetType = SheetTypeEnum.LabelPatient;
    private List<SheetDef> listSheets = new List<SheetDef>();
    /**
    * Only if OK.
    */
    public List<SheetDef> SelectedSheetDefs = new List<SheetDef>();
    //private bool showingInternalSheetDefs;
    //private bool showingInternalMed;
    /**
    * Stores the indices of the sheetDefs already added to SelectedSheetDefs.  Prevents adding the same one twice.  Only used with terminal.
    */
    private List<int> alreadyAdded = new List<int>();
    /**
    * On closing, this will be true if the ToTerminal button was used and if the selected sheets should be sent to a terminal.
    */
    public boolean TerminalSend = new boolean();
    /**
    * It will always be hidden anyway if sheetType is not PatientForm.  So this is only useful if it is a PatientForm and you also want to hide the button.
    */
    public boolean HideKioskButton = new boolean();
    public FormSheetPicker() throws Exception {
        initializeComponent();
        Lan.F(this);
        alreadyAdded = new List<int>();
    }

    private void formSheetPicker_Load(Object sender, EventArgs e) throws Exception {
        listSheets = SheetDefs.getCustomForType(SheetType);
        if (listSheets.Count == 0 && SheetType == SheetTypeEnum.PatientForm)
        {
            //showingInternalSheetDefs=true;
            listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.PatientRegistration));
            listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.FinancialAgreement));
            listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.HIPAA));
            if (PrefC.getBool(PrefName.PatientFormsShowConsent))
            {
                listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.Consent));
            }
             
        }
         
        if (SheetType == SheetTypeEnum.PatientForm)
        {
            //we will also show medical history
            List<SheetDef> listMedSheets = SheetDefs.getCustomForType(SheetTypeEnum.MedicalHistory);
            List<SheetDef> listConSheets = SheetDefs.getCustomForType(SheetTypeEnum.Consent);
            if (listMedSheets.Count == 0)
            {
                //showingInternalMed=true;
                listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.MedicalHistSimple));
            }
            else
            {
                for (int i = 0;i < listMedSheets.Count;i++)
                {
                    //if user has added any of their own medical history forms
                    listSheets.Add(listMedSheets[i]);
                }
            } 
            labelSheetType.Text = Lan.g("this","Patient Forms and Medical Histories");
            //Change name?
            if (PrefC.getBool(PrefName.PatientFormsShowConsent))
            {
                //only if they want to see consent forms with patient forms.
                if (listConSheets.Count == 0)
                {
                    //use internal consent forms
                    listSheets.Add(SheetsInternal.getSheetDef(SheetInternalType.Consent));
                }
                else
                {
                    for (int i = 0;i < listConSheets.Count;i++)
                    {
                        //if user has added any of their own consent forms
                        listSheets.Add(listConSheets[i]);
                    }
                } 
                labelSheetType.Text = Lan.g("this","Patient, Consent, and Medical History Forms");
            }
             
        }
        else
        {
            labelSheetType.Text = Lan.g("enumSheetTypeEnum", SheetType.ToString());
            butTerminal.Visible = false;
            labelTerminal.Visible = false;
        } 
        if (HideKioskButton)
        {
            butTerminal.Visible = false;
            labelTerminal.Visible = false;
        }
         
        for (int i = 0;i < listSheets.Count;i++)
        {
            listMain.Items.Add(listSheets[i].Description);
        }
    }

    private void listMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndices.Count != 1)
        {
            return ;
        }
         
        SelectedSheetDefs = new List<SheetDef>();
        SheetDef sheetDef = listSheets[listMain.SelectedIndices[0]];
        if (sheetDef.SheetDefNum != 0)
        {
            SheetDefs.getFieldsAndParameters(sheetDef);
        }
         
        /*
        			if(sheetDef.SheetType==SheetTypeEnum.PatientForm && !showingInternalSheetDefs) {
        				SheetDefs.GetFieldsAndParameters(sheetDef);
        			}
        			if(sheetDef.SheetType==SheetTypeEnum.MedicalHistory && !showingInternalMed) {
        				SheetDefs.GetFieldsAndParameters(sheetDef);
        			}*/
        SelectedSheetDefs.Add(sheetDef);
        TerminalSend = false;
        DialogResult = DialogResult.OK;
    }

    private void butTerminal_Click(Object sender, EventArgs e) throws Exception {
        //only visible when used from patient forms window.
        if (listMain.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"Please select at least one item first.");
            return ;
        }
         
        if (SelectedSheetDefs == null)
        {
            SelectedSheetDefs = new List<SheetDef>();
        }
         
        SheetDef sheetDef;
        for (int i = 0;i < listMain.SelectedIndices.Count;i++)
        {
            //test to make sure this sheetDef was not already added
            if (alreadyAdded.Contains(listMain.SelectedIndices[i]))
            {
                continue;
            }
             
            alreadyAdded.Add(listMain.SelectedIndices[i]);
            sheetDef = listSheets[listMain.SelectedIndices[i]];
            if (sheetDef.SheetDefNum != 0)
            {
                SheetDefs.getFieldsAndParameters(sheetDef);
            }
             
            SelectedSheetDefs.Add(sheetDef);
        }
        TerminalSend = true;
        DialogResult = DialogResult.OK;
    }

    //for(int i=0;i<listMain.Items.Count;i++) {
    //	listMain.SetSelected(i,false);
    //}
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndices.Count != 1)
        {
            MsgBox.show(this,"Please select one item first.");
            return ;
        }
         
        SelectedSheetDefs = new List<SheetDef>();
        SheetDef sheetDef = listSheets[listMain.SelectedIndices[0]];
        if (sheetDef.SheetDefNum != 0)
        {
            SheetDefs.getFieldsAndParameters(sheetDef);
        }
         
        /*
        			if(sheetDef.SheetType==SheetTypeEnum.PatientForm && !showingInternalSheetDefs) {
        				SheetDefs.GetFieldsAndParameters(sheetDef);
        			}
        			if(sheetDef.SheetType==SheetTypeEnum.MedicalHistory && !showingInternalMed) {
        				SheetDefs.GetFieldsAndParameters(sheetDef);
        			}*/
        SelectedSheetDefs.Add(sheetDef);
        TerminalSend = false;
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        if (SelectedSheetDefs == null || SelectedSheetDefs.Count == 0)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //TerminalSend will have already been set true in this case.
        DialogResult = DialogResult.OK;
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
        this.label2 = new System.Windows.Forms.Label();
        this.labelSheetType = new System.Windows.Forms.Label();
        this.listMain = new System.Windows.Forms.ListBox();
        this.butTerminal = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.labelTerminal = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(29, 9);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(167, 40);
        this.label2.TabIndex = 15;
        this.label2.Text = "Sheets can be added and edited from Setup Sheets.";
        //
        // labelSheetType
        //
        this.labelSheetType.Location = new System.Drawing.Point(29, 62);
        this.labelSheetType.Name = "labelSheetType";
        this.labelSheetType.Size = new System.Drawing.Size(239, 14);
        this.labelSheetType.TabIndex = 14;
        this.labelSheetType.Text = "Patient Forms and Medical Histories";
        this.labelSheetType.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(30, 81);
        this.listMain.Name = "listMain";
        this.listMain.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listMain.Size = new System.Drawing.Size(164, 329);
        this.listMain.TabIndex = 13;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butTerminal
        //
        this.butTerminal.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTerminal.setAutosize(true);
        this.butTerminal.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTerminal.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTerminal.setCornerRadius(4F);
        this.butTerminal.Location = new System.Drawing.Point(284, 175);
        this.butTerminal.Name = "butTerminal";
        this.butTerminal.Size = new System.Drawing.Size(75, 24);
        this.butTerminal.TabIndex = 16;
        this.butTerminal.Text = "To Kiosk";
        this.butTerminal.Click += new System.EventHandler(this.butTerminal_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(284, 351);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(284, 385);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // labelTerminal
        //
        this.labelTerminal.Location = new System.Drawing.Point(282, 205);
        this.labelTerminal.Name = "labelTerminal";
        this.labelTerminal.Size = new System.Drawing.Size(88, 30);
        this.labelTerminal.TabIndex = 19;
        this.labelTerminal.Text = "Multiple sheets can be sent";
        //
        // FormSheetPicker
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(394, 436);
        this.Controls.Add(this.labelTerminal);
        this.Controls.Add(this.butTerminal);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.labelSheetType);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butClose);
        this.Name = "FormSheetPicker";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pick Sheet";
        this.Load += new System.EventHandler(this.FormSheetPicker_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelSheetType = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butTerminal;
    private System.Windows.Forms.Label labelTerminal = new System.Windows.Forms.Label();
}


