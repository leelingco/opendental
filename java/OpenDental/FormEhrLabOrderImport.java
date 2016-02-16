//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormCDSIntervention;
import OpenDental.FormEhrLabOrderEdit2014;
import OpenDental.FormPatientSelect;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabOrderImport  extends Form 
{

    public String Hl7LabMessage = new String();
    private List<EhrLab> ListEhrLabs = new List<EhrLab>();
    public Patient PatCur;
    public FormEhrLabOrderImport() throws Exception {
        initializeComponent();
    }

    private void formEhrLabOrders_Load(Object sender, EventArgs e) throws Exception {
        ListEhrLabs = EhrLabs.processHl7Message(Hl7LabMessage,true);
        attachPatientHelper();
        fillPatientPicker();
        fillPatientInfo();
        fillGrid();
        for (int i = 0;i < ListEhrLabs.Count;i++)
        {
            //check for existing labs in DB.
            if (EhrLabs.GetByGUID(ListEhrLabs[i].PlacerOrderUniversalID, ListEhrLabs[i].PlacerOrderNum) != null || EhrLabs.GetByGUID(ListEhrLabs[i].FillerOrderUniversalID, ListEhrLabs[i].FillerOrderNum) != null)
            {
                labelExistingLab.Visible = true;
                break;
            }
             
        }
    }

    private void attachPatientHelper() throws Exception {
        Patient patAttach = EhrLabs.findAttachedPatient(Hl7LabMessage);
        if (patAttach == null)
        {
            return ;
        }
        else //no reccomended patient
        if (PatCur == null)
        {
            PatCur = patAttach;
        }
        else if (PatCur.PatNum != patAttach.PatNum)
        {
            MsgBox.show(this,"Patient mismatch. Selected patient does not match detected patient.");
            //will only happen if we set PatCur from somewhere else. Probably wont ever happen.
            PatCur = patAttach;
        }
        else
        {
        }   
    }

    //I dunno what to put here; maybe a little picture of a dog wearing a fireman costume?
    private void fillPatientPicker() throws Exception {
        if (PatCur == null)
        {
            textName.Text = "";
            return ;
        }
         
        textName.Text = PatCur.getNameFL();
    }

    /**
    * Fills patient information from message contents, not from PatCur.
    */
    private void fillPatientInfo() throws Exception {
        String[] PIDFields = new String[]();
        try
        {
            PIDFields = Hl7LabMessage.Split(new String[]{ "\r\n" }, StringSplitOptions.RemoveEmptyEntries)[1].Split('|');
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        for (int i = 0;i < PIDFields[5].Split('~').Length;i++)
        {
            //invalid HL7Message
            //patient name(s)
            String patName = "";
            try
            {
                patName += PIDFields[5].Split('~')[i].Split('^')[4] + " ";
            }
            catch (Exception __dummyCatchVar1)
            {
            }

            try
            {
                //Prefix
                patName += PIDFields[5].Split('~')[i].Split('^')[1] + " ";
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            try
            {
                //FName
                patName += PIDFields[5].Split('~')[i].Split('^')[2] + " ";
            }
            catch (Exception __dummyCatchVar3)
            {
            }

            try
            {
                //Middle Name(s)
                patName += PIDFields[5].Split('~')[i].Split('^')[0] + " ";
            }
            catch (Exception __dummyCatchVar4)
            {
            }

            try
            {
                //Last Name
                patName += PIDFields[5].Split('~')[i].Split('^')[3];
            }
            catch (Exception __dummyCatchVar5)
            {
            }

            //Suffix
            listBoxNames.Items.Add(patName);
        }
        //Birthdate
        textBirthdate.Text = PIDFields[7];
        //Gender
        textGender.Text = PIDFields[8];
        for (int i = 0;i < PIDFields[10].Split('~').Length;i++)
        {
            //Race(s)
            try
            {
                listBoxRaces.Items.Add(PIDFields[10].Split('~')[i].Split('^')[1]);
            }
            catch (Exception __dummyCatchVar6)
            {
            }

            try
            {
                listBoxRaces.Items.Add(PIDFields[10].Split('~')[i].Split('^')[4]);
            }
            catch (Exception __dummyCatchVar7)
            {
            }
        
        }
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Date Time",80);
        //Formatted yyyyMMdd
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Placer Order Number",180);
        //Should be PK but might not be. Instead use Placer Order Num.
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Filler Order Number",180);
        //Should be PK but might not be. Instead use Placer Order Num.
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Results",80);
        //Or date of latest result? or both?
        gridMain.getColumns().add(col);
        //ListEhrLabs = EhrLabs.GetAllForPat(PatCur.PatNum);//do not update here, all this lab information is cached.
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListEhrLabs.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            String dateSt = ListEhrLabs[i].ObservationDateTimeStart.Substring(0, 8);
            //stored in DB as yyyyMMddhhmmss-zzzz
            DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
            row.getCells().Add(dateT.ToShortDateString());
            //date only
            row.getCells().Add(ListEhrLabs[i].PlacerOrderNum);
            row.getCells().Add(ListEhrLabs[i].FillerOrderNum);
            row.getCells().Add(ListEhrLabs[i].ListEhrLabResults.Count.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabOrderEdit2014 FormLOE = new FormEhrLabOrderEdit2014();
        FormLOE.EhrLabCur = ListEhrLabs[e.getRow()];
        FormLOE.IsImport = true;
        FormLOE.ShowDialog();
    }

    //if(FormLOE.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //EhrLabs.SaveToDB(FormLOE.EhrLabCur);
    //for(int i=0;i<FormLOE.EhrLabCur.ListEhrLabResults.Count;i++) {
    //	if(CDSPermissions.GetForUser(Security.CurUser.UserNum).ShowCDS && CDSPermissions.GetForUser(Security.CurUser.UserNum).LabTestCDS){
    //		FormCDSIntervention FormCDSI=new FormCDSIntervention();
    //		FormCDSI.ListCDSI=EhrTriggers.TriggerMatch(FormLOE.EhrLabCur.ListEhrLabResults[i],PatCur);
    //		FormCDSI.ShowIfRequired(false);
    //	}
    //}
    //TODO:maybe add more code here for when we come back from form... In case we delete a lab from the form.
    private void butPatSelect_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PatCur = Patients.getPat(FormPS.SelectedPatNum);
        fillPatientPicker();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            MsgBox.show(this,"Please attach to patient first.");
            return ;
        }
         
        for (int i = 0;i < ListEhrLabs.Count;i++)
        {
            //Check lab dates to see if these labs already exist.
            EhrLab tempLab = null;
            //lab from DB if it exists.
            tempLab = EhrLabs.GetByGUID(ListEhrLabs[i].PlacerOrderUniversalID, ListEhrLabs[i].PlacerOrderNum);
            if (tempLab == null)
            {
                tempLab = EhrLabs.GetByGUID(ListEhrLabs[i].FillerOrderUniversalID, ListEhrLabs[i].FillerOrderNum);
            }
             
            if (tempLab != null)
            {
                //validate Date of Lab and attached patient.
                //Date
                if (tempLab.ResultDateTime.CompareTo(ListEhrLabs[i].ResultDateTime) < 0)
                {
                    //string compare dates will return 1+ if tempLab Date is greater.
                    MsgBox.show(this,"This lab already exists in the database and has a more recent timestamp.");
                    continue;
                }
                 
                if (PatCur.PatNum != tempLab.PatNum)
                {
                }
                 
            }
             
            //do nothing. We are importing an updated lab result and the previous lab result was attached to the wrong patient.
            //or do something. later maybe.
            ListEhrLabs[i].PatNum = PatCur.PatNum;
            if (Security.getCurUser().ProvNum != 0 && !StringSupport.equals(Providers.getProv(Security.getCurUser().ProvNum).EhrKey, ""))
            {
                //The user who is currently logged in is a provider and has a valid EHR key.
                ListEhrLabs[i].IsCpoe = true;
            }
             
            ListEhrLabs[i] = EhrLabs.SaveToDB(ListEhrLabs[i]);
            for (int j = 0;j < ListEhrLabs[i].ListEhrLabResults.Count;j++)
            {
                //SAVE
                //EHR TRIGGER
                if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).LabTestCDS)
                {
                    FormCDSIntervention FormCDSI = new FormCDSIntervention();
                    FormCDSI.ListCDSI = EhrTriggers.triggerMatch(ListEhrLabs[i].ListEhrLabResults[j],PatCur);
                    FormCDSI.showIfRequired(false);
                }
                 
            }
        }
        DialogResult = DialogResult.OK;
    }

    //Done!
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
        this.butCancel = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSave = new System.Windows.Forms.Button();
        this.butPatSelect = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.textBirthdate = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.listBoxRaces = new System.Windows.Forms.ListBox();
        this.textGender = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.listBoxNames = new System.Windows.Forms.ListBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.labelExistingLab = new System.Windows.Forms.Label();
        this.groupBox5.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(627, 357);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 163);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(690, 188);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Laboratory Orders");
        this.gridMain.setTranslationName(null);
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butSave
        //
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSave.Location = new System.Drawing.Point(546, 357);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(75, 23);
        this.butSave.TabIndex = 10;
        this.butSave.Text = "Save";
        this.butSave.UseVisualStyleBackColor = true;
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butPatSelect
        //
        this.butPatSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatSelect.setAutosize(true);
        this.butPatSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatSelect.setCornerRadius(4F);
        this.butPatSelect.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPatSelect.Location = new System.Drawing.Point(325, 9);
        this.butPatSelect.Name = "butPatSelect";
        this.butPatSelect.Size = new System.Drawing.Size(29, 25);
        this.butPatSelect.TabIndex = 231;
        this.butPatSelect.Text = "...";
        this.butPatSelect.Click += new System.EventHandler(this.butPatSelect_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(27, 14);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(96, 17);
        this.label5.TabIndex = 230;
        this.label5.Text = "Attached Patient";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(126, 12);
        this.textName.Name = "textName";
        this.textName.ReadOnly = true;
        this.textName.Size = new System.Drawing.Size(193, 20);
        this.textName.TabIndex = 229;
        this.textName.WordWrap = false;
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.textBirthdate);
        this.groupBox5.Controls.Add(this.label2);
        this.groupBox5.Controls.Add(this.listBoxRaces);
        this.groupBox5.Controls.Add(this.textGender);
        this.groupBox5.Controls.Add(this.label1);
        this.groupBox5.Controls.Add(this.listBoxNames);
        this.groupBox5.Controls.Add(this.label7);
        this.groupBox5.Controls.Add(this.label9);
        this.groupBox5.Location = new System.Drawing.Point(12, 38);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(697, 119);
        this.groupBox5.TabIndex = 249;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Patient Information From Message";
        //
        // textBirthdate
        //
        this.textBirthdate.Location = new System.Drawing.Point(114, 64);
        this.textBirthdate.MaxLength = 100;
        this.textBirthdate.Name = "textBirthdate";
        this.textBirthdate.Size = new System.Drawing.Size(93, 20);
        this.textBirthdate.TabIndex = 244;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(330, 17);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(79, 16);
        this.label2.TabIndex = 243;
        this.label2.Text = "Race(s)";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listBoxRaces
        //
        this.listBoxRaces.Location = new System.Drawing.Point(411, 17);
        this.listBoxRaces.Name = "listBoxRaces";
        this.listBoxRaces.Size = new System.Drawing.Size(154, 43);
        this.listBoxRaces.TabIndex = 242;
        //
        // textGender
        //
        this.textGender.Location = new System.Drawing.Point(114, 88);
        this.textGender.MaxLength = 100;
        this.textGender.Name = "textGender";
        this.textGender.Size = new System.Drawing.Size(93, 20);
        this.textGender.TabIndex = 241;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 17);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(88, 16);
        this.label1.TabIndex = 240;
        this.label1.Text = "Name(s)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listBoxNames
        //
        this.listBoxNames.Location = new System.Drawing.Point(114, 17);
        this.listBoxNames.Name = "listBoxNames";
        this.listBoxNames.Size = new System.Drawing.Size(193, 43);
        this.listBoxNames.TabIndex = 239;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(30, 90);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(82, 16);
        this.label7.TabIndex = 237;
        this.label7.Text = "Gender";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(27, 67);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(85, 14);
        this.label9.TabIndex = 235;
        this.label9.Text = "Birthdate";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelExistingLab
        //
        this.labelExistingLab.ForeColor = System.Drawing.Color.Red;
        this.labelExistingLab.Location = new System.Drawing.Point(154, 360);
        this.labelExistingLab.Name = "labelExistingLab";
        this.labelExistingLab.Size = new System.Drawing.Size(386, 17);
        this.labelExistingLab.TabIndex = 250;
        this.labelExistingLab.Text = "Saving these results will update one or more existing labs.";
        this.labelExistingLab.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelExistingLab.Visible = false;
        //
        // FormEhrLabOrderImport
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(714, 392);
        this.Controls.Add(this.labelExistingLab);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.butPatSelect);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormEhrLabOrderImport";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Orders";
        this.Load += new System.EventHandler(this.FormEhrLabOrders_Load);
        this.groupBox5.ResumeLayout(false);
        this.groupBox5.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSave = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butPatSelect;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listBoxNames = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listBoxRaces = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textGender = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBirthdate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelExistingLab = new System.Windows.Forms.Label();
}


