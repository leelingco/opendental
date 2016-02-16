//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.FormEHR;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.SiteC;
import OpenDental.FormEhrPatientExport;

public class FormEhrPatientExport  extends Form 
{

    DataTable table = new DataTable();
    public FormEhrPatientExport() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrPatientExport_Load(Object sender, EventArgs e) throws Exception {
        comboProv.Items.Add(Lan.g(this,"All"));
        comboProv.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add(Lan.g(this,"All"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
        } 
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            comboSite.Visible = false;
            labelSite.Visible = false;
        }
        else
        {
            comboSite.Items.Add(Lan.g(this,"All"));
            comboSite.SelectedIndex = 0;
            for (int i = 0;i < SiteC.getList().Length;i++)
            {
                comboSite.Items.Add(SiteC.getList()[i].Description);
            }
        } 
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(false);
        fillGridMain();
    }

    private void fillGridMain() throws Exception {
        //Get filters from user input
        String firstName = "";
        if (!StringSupport.equals(textFName.Text, ""))
        {
            firstName = textFName.Text;
        }
         
        String lastName = "";
        if (!StringSupport.equals(textLName.Text, ""))
        {
            lastName = textLName.Text;
        }
         
        int patNum = 0;
        try
        {
            if (!StringSupport.equals(textPatNum.Text, ""))
            {
                patNum = PIn.Int(textPatNum.Text);
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Invalid PatNum");
            return ;
        }

        long provNum = 0;
        if (comboProv.SelectedIndex != 0)
        {
            provNum = ProviderC.getListShort()[comboProv.SelectedIndex - 1].ProvNum;
        }
         
        long clinicNum = 0;
        if (!PrefC.getBool(PrefName.EasyNoClinics) && comboClinic.SelectedIndex != 0)
        {
            clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        long siteNum = 0;
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth) && comboSite.SelectedIndex != 0)
        {
            siteNum = SiteC.getList()[comboSite.SelectedIndex - 1].SiteNum;
        }
         
        //Get table
        table = Patients.GetExportList(patNum, firstName, lastName, provNum, clinicNum, siteNum);
        //Create grid
        //Patient Name | Primary Provider | Date Last Visit | Clinic | Site
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("PatNum",60);
        col.setSortingStrategy(GridSortingStrategy.AmountParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Patient Name",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Primary Provider",110);
        gridMain.getColumns().add(col);
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            col = new ODGridColumn("Clinic",110);
            gridMain.getColumns().add(col);
        }
         
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            col = new ODGridColumn("Site",110);
            gridMain.getColumns().add(col);
        }
         
        //Fill grid
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["PatNum"].ToString());
            row.getCells().Add(table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString());
            row.getCells().Add(table.Rows[i]["Provider"].ToString());
            if (!PrefC.getBool(PrefName.EasyNoClinics))
            {
                row.getCells().Add(table.Rows[i]["Clinic"].ToString());
            }
             
            if (!PrefC.getBool(PrefName.EasyHidePublicHealth))
            {
                row.getCells().Add(table.Rows[i]["Site"].ToString());
            }
             
            row.setTag(PIn.Long(table.Rows[i]["PatNum"].ToString()));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butSelectAll_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(true);
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        String strCcdValidationErrors = EhrCCD.validateSettings();
        if (!StringSupport.equals(strCcdValidationErrors, ""))
        {
            //Do not even try to export if global settings are invalid.
            MessageBox.Show(strCcdValidationErrors);
            return ;
        }
         
        //We do not want to use translations here, because the text is dynamic. The errors are generated in the business layer, and Lan.g() is not available there.
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        DialogResult result = dlg.ShowDialog();
        if (result != DialogResult.OK)
        {
            return ;
        }
         
        DateTime dateNow = DateTime.Now;
        String folderPath = CodeBase.ODFileUtils.CombinePaths(dlg.SelectedPath, (dateNow.Year + "_" + dateNow.Month + "_" + dateNow.Day));
        if (Directory.Exists(folderPath))
        {
            int loopCount = 1;
            while (Directory.Exists(folderPath + "_" + loopCount))
            {
                loopCount++;
            }
            folderPath = folderPath + "_" + loopCount;
        }
         
        try
        {
            Directory.CreateDirectory(folderPath);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Error, Could not create folder");
            return ;
        }

        this.Cursor = Cursors.WaitCursor;
        Patient patCur;
        String fileName = new String();
        int numSkipped = 0;
        //Number of patients skipped. Set to -1 if only one patient was selected and had CcdValidationErrors.
        String patientsSkipped = "";
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //Names of the patients that were skipped, so we can tell the user which ones didn't export correctly.
            patCur = Patients.getPat((long)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag);
            //Cannot use GetLim because more information is needed in the CCD message generation below.
            strCcdValidationErrors = EhrCCD.validatePatient(patCur);
            if (!StringSupport.equals(strCcdValidationErrors, ""))
            {
                if (gridMain.getSelectedIndices().Length == 1)
                {
                    numSkipped = -1;
                    //Set to -1 so we know below to not show the "exported" message.
                    MessageBox.Show(Lan.g(this,"Patient not exported due to the following errors") + ":\r\n" + strCcdValidationErrors);
                    continue;
                }
                 
                //If one patient is missing the required information for export, then simply skip the patient. We do not want to popup a message,
                //because it would be hard to get through the export if many patients were missing required information.
                numSkipped++;
                patientsSkipped += "\r\n" + patCur.LName + ", " + patCur.FName;
                continue;
            }
             
            fileName = "";
            String lName = patCur.LName;
            for (int j = 0;j < lName.Length;j++)
            {
                //Strip all non-letters from FName
                if (Char.IsLetter(lName, j))
                {
                    fileName += lName.Substring(j, 1);
                }
                 
            }
            fileName += "_";
            String fName = patCur.FName;
            for (int k = 0;k < fName.Length;k++)
            {
                //Strip all non-letters from LName
                if (Char.IsLetter(fName, k))
                {
                    fileName += fName.Substring(k, 1);
                }
                 
            }
            fileName += "_" + patCur.PatNum;
            //LName_FName_PatNum
            String ccd = EhrCCD.generatePatientExport(patCur);
            try
            {
                File.WriteAllText(CodeBase.ODFileUtils.combinePaths(folderPath,fileName + ".xml"), ccd);
            }
            catch (Exception __dummyCatchVar2)
            {
                MessageBox.Show("Error, Could not create xml file");
                this.Cursor = Cursors.Default;
                return ;
            }
        
        }
        if (numSkipped == -1)
        {
            //Will be -1 if only one patient was selected, and it did not export correctly.
            this.Cursor = Cursors.Default;
            return ;
        }
         
        try
        {
            //Don't display "Exported" to the user because the CCD was not exported.
            File.WriteAllText(CodeBase.ODFileUtils.combinePaths(folderPath,"CCD.xsl"), FormEHR.getEhrResource("CCD"));
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show("Error, Could not create stylesheet file");
        }

        String strMsg = Lan.g(this,"Exported");
        if (numSkipped > 0)
        {
            strMsg += ". " + Lan.g(this,"Patients skipped due to missing information") + ": " + numSkipped + patientsSkipped;
            MsgBoxCopyPaste msgCP = new MsgBoxCopyPaste(strMsg);
            msgCP.Show();
        }
        else
        {
            MessageBox.Show(strMsg);
        } 
        this.Cursor = Cursors.Default;
    }

    private void butExportAll_Click(Object sender, EventArgs e) throws Exception {
    }

    //Export all active patients
    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrPatientExport.class);
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboSite = new System.Windows.Forms.ComboBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.labelLName = new System.Windows.Forms.Label();
        this.labelFName = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSearch = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.textFName = new OpenDental.ODtextBox();
        this.textLName = new OpenDental.ODtextBox();
        this.butSelectAll = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textPatNum = new OpenDental.ODtextBox();
        this.SuspendLayout();
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(348, 12);
        this.comboProv.MaxDropDownItems = 40;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(160, 21);
        this.comboProv.TabIndex = 23;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(256, 12);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(90, 21);
        this.label4.TabIndex = 22;
        this.label4.Text = "Primary Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSite
        //
        this.comboSite.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSite.Location = new System.Drawing.Point(348, 54);
        this.comboSite.MaxDropDownItems = 40;
        this.comboSite.Name = "comboSite";
        this.comboSite.Size = new System.Drawing.Size(160, 21);
        this.comboSite.TabIndex = 29;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(276, 54);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(70, 21);
        this.labelSite.TabIndex = 28;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(348, 33);
        this.comboClinic.MaxDropDownItems = 40;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(160, 21);
        this.comboClinic.TabIndex = 27;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(276, 33);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(70, 21);
        this.labelClinic.TabIndex = 26;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelLName
        //
        this.labelLName.Location = new System.Drawing.Point(15, 32);
        this.labelLName.Name = "labelLName";
        this.labelLName.Size = new System.Drawing.Size(70, 22);
        this.labelLName.TabIndex = 37;
        this.labelLName.Text = "Last Name";
        this.labelLName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelFName
        //
        this.labelFName.Location = new System.Drawing.Point(15, 12);
        this.labelFName.Name = "labelFName";
        this.labelFName.Size = new System.Drawing.Size(70, 21);
        this.labelFName.TabIndex = 35;
        this.labelFName.Text = "First Name";
        this.labelFName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.setAllowSortingByColumn(true);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 86);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(590, 365);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Patient Export List");
        this.gridMain.setTranslationName(null);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(531, 10);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 23);
        this.butSearch.TabIndex = 33;
        this.butSearch.Text = "&Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Location = new System.Drawing.Point(18, 462);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(100, 24);
        this.butExport.TabIndex = 30;
        this.butExport.Text = "Export Selected";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // textFName
        //
        this.textFName.AcceptsTab = true;
        this.textFName.DetectUrls = false;
        this.textFName.Location = new System.Drawing.Point(87, 12);
        this.textFName.Multiline = false;
        this.textFName.Name = "textFName";
        this.textFName.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textFName.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textFName.Size = new System.Drawing.Size(160, 21);
        this.textFName.TabIndex = 39;
        this.textFName.Text = "";
        //
        // textLName
        //
        this.textLName.AcceptsTab = true;
        this.textLName.DetectUrls = false;
        this.textLName.Location = new System.Drawing.Point(87, 33);
        this.textLName.Multiline = false;
        this.textLName.Name = "textLName";
        this.textLName.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textLName.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textLName.Size = new System.Drawing.Size(160, 21);
        this.textLName.TabIndex = 38;
        this.textLName.Text = "";
        //
        // butSelectAll
        //
        this.butSelectAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSelectAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSelectAll.setAutosize(true);
        this.butSelectAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSelectAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSelectAll.setCornerRadius(4F);
        this.butSelectAll.Location = new System.Drawing.Point(276, 462);
        this.butSelectAll.Name = "butSelectAll";
        this.butSelectAll.Size = new System.Drawing.Size(75, 24);
        this.butSelectAll.TabIndex = 33;
        this.butSelectAll.Text = "Select All";
        this.butSelectAll.Click += new System.EventHandler(this.butSelectAll_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(531, 462);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 54);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(70, 22);
        this.label1.TabIndex = 40;
        this.label1.Text = "Patnum";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatNum
        //
        this.textPatNum.AcceptsTab = true;
        this.textPatNum.DetectUrls = false;
        this.textPatNum.Location = new System.Drawing.Point(87, 55);
        this.textPatNum.Multiline = false;
        this.textPatNum.Name = "textPatNum";
        this.textPatNum.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textPatNum.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textPatNum.Size = new System.Drawing.Size(160, 21);
        this.textPatNum.TabIndex = 41;
        this.textPatNum.Text = "";
        //
        // FormEhrPatientExport
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(626, 498);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textPatNum);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.butExport);
        this.Controls.Add(this.textFName);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.labelLName);
        this.Controls.Add(this.labelSite);
        this.Controls.Add(this.textLName);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelFName);
        this.Controls.Add(this.butSelectAll);
        this.Controls.Add(this.comboSite);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.comboProv);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(611, 243);
        this.Name = "FormEhrPatientExport";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Ehr Patient Export";
        this.Load += new System.EventHandler(this.FormEhrPatientExport_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.ComboBox comboProv = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSite = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelSite = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butExport;
    private OpenDental.UI.Button butSelectAll;
    private OpenDental.UI.Button butSearch;
    private System.Windows.Forms.Label labelLName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelFName = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textFName;
    private OpenDental.ODtextBox textLName;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textPatNum;
}


