//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormLetterMergeEdit;
import OpenDental.FormPath;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.LetterMerge;
import OpenDentBusiness.LetterMergeField;
import OpenDentBusiness.LetterMergeFields;
import OpenDentBusiness.LetterMerges;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLetterMergeEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTemplateName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDataFileName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox comboCategory = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPath = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butEditPaths;
    private LetterMerge LetterMergeCur;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private String mergePath = new String();
    private System.Windows.Forms.ListBox listPatSelect = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butBrowse;
    private System.Windows.Forms.OpenFileDialog openFileDlg = new System.Windows.Forms.OpenFileDialog();
    private System.Windows.Forms.Label labelReferredFrom = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listReferral = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butNew;
    private Label label6 = new Label();
    private ListBox listOther = new ListBox();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    //private ArrayList ALpatSelect;
    /**
    * 
    */
    public FormLetterMergeEdit(LetterMerge letterMergeCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        LetterMergeCur = letterMergeCur;
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLetterMergeEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textTemplateName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textDataFileName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.comboCategory = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPath = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.butEditPaths = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.labelPatient = new System.Windows.Forms.Label();
        this.listPatSelect = new System.Windows.Forms.ListBox();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butBrowse = new OpenDental.UI.Button();
        this.openFileDlg = new System.Windows.Forms.OpenFileDialog();
        this.labelReferredFrom = new System.Windows.Forms.Label();
        this.listReferral = new System.Windows.Forms.ListBox();
        this.butNew = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.listOther = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(799, 638);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
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
        this.butOK.Location = new System.Drawing.Point(799, 597);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(20, 11);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(250, 14);
        this.label2.TabIndex = 3;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(272, 7);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(221, 20);
        this.textDescription.TabIndex = 0;
        //
        // textTemplateName
        //
        this.textTemplateName.Location = new System.Drawing.Point(272, 54);
        this.textTemplateName.Name = "textTemplateName";
        this.textTemplateName.Size = new System.Drawing.Size(346, 20);
        this.textTemplateName.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(20, 58);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(250, 14);
        this.label1.TabIndex = 5;
        this.label1.Text = "Template File Name (eg MyTemplate.doc)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDataFileName
        //
        this.textDataFileName.Location = new System.Drawing.Point(272, 77);
        this.textDataFileName.Name = "textDataFileName";
        this.textDataFileName.Size = new System.Drawing.Size(346, 20);
        this.textDataFileName.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(20, 82);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(250, 14);
        this.label3.TabIndex = 7;
        this.label3.Text = "Datafile Name (eg MyTemplate.txt)";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCategory
        //
        this.comboCategory.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCategory.Location = new System.Drawing.Point(272, 100);
        this.comboCategory.MaxDropDownItems = 30;
        this.comboCategory.Name = "comboCategory";
        this.comboCategory.Size = new System.Drawing.Size(222, 21);
        this.comboCategory.TabIndex = 8;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(20, 104);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(250, 14);
        this.label4.TabIndex = 9;
        this.label4.Text = "Category";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPath
        //
        this.textPath.Location = new System.Drawing.Point(272, 30);
        this.textPath.Name = "textPath";
        this.textPath.ReadOnly = true;
        this.textPath.Size = new System.Drawing.Size(346, 20);
        this.textPath.TabIndex = 23;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(21, 34);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(250, 14);
        this.label5.TabIndex = 24;
        this.label5.Text = "Letter Merge Path";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butEditPaths
        //
        this.butEditPaths.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditPaths.setAutosize(true);
        this.butEditPaths.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditPaths.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditPaths.setCornerRadius(4F);
        this.butEditPaths.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEditPaths.Location = new System.Drawing.Point(622, 27);
        this.butEditPaths.Name = "butEditPaths";
        this.butEditPaths.Size = new System.Drawing.Size(87, 25);
        this.butEditPaths.TabIndex = 25;
        this.butEditPaths.Text = "Edit Paths";
        this.butEditPaths.Click += new System.EventHandler(this.butEditPaths_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 638);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(87, 26);
        this.butDelete.TabIndex = 26;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(12, 121);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(170, 14);
        this.labelPatient.TabIndex = 28;
        this.labelPatient.Text = "Patient Fields";
        this.labelPatient.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listPatSelect
        //
        this.listPatSelect.Location = new System.Drawing.Point(12, 139);
        this.listPatSelect.Name = "listPatSelect";
        this.listPatSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listPatSelect.Size = new System.Drawing.Size(170, 472);
        this.listPatSelect.TabIndex = 27;
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(190, 137);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(622, 23);
        this.textBox1.TabIndex = 29;
        this.textBox1.Text = "Hint: Use the Ctrl key when clicking.  Also you can simply drag the pointer acros" + "s muliple rows to select quickly.";
        //
        // butBrowse
        //
        this.butBrowse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowse.setAutosize(true);
        this.butBrowse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowse.setCornerRadius(4F);
        this.butBrowse.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBrowse.Location = new System.Drawing.Point(622, 53);
        this.butBrowse.Name = "butBrowse";
        this.butBrowse.Size = new System.Drawing.Size(87, 25);
        this.butBrowse.TabIndex = 30;
        this.butBrowse.Text = "Browse";
        this.butBrowse.Click += new System.EventHandler(this.butBrowse_Click);
        //
        // labelReferredFrom
        //
        this.labelReferredFrom.Location = new System.Drawing.Point(206, 161);
        this.labelReferredFrom.Name = "labelReferredFrom";
        this.labelReferredFrom.Size = new System.Drawing.Size(170, 14);
        this.labelReferredFrom.TabIndex = 32;
        this.labelReferredFrom.Text = "Referred From";
        this.labelReferredFrom.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listReferral
        //
        this.listReferral.Location = new System.Drawing.Point(206, 178);
        this.listReferral.Name = "listReferral";
        this.listReferral.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listReferral.Size = new System.Drawing.Size(170, 433);
        this.listReferral.TabIndex = 31;
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(714, 53);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(68, 25);
        this.butNew.TabIndex = 33;
        this.butNew.Text = "New";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(400, 161);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(170, 14);
        this.label6.TabIndex = 35;
        this.label6.Text = "Other";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listOther
        //
        this.listOther.Location = new System.Drawing.Point(400, 178);
        this.listOther.Name = "listOther";
        this.listOther.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listOther.Size = new System.Drawing.Size(170, 433);
        this.listOther.TabIndex = 34;
        //
        // FormLetterMergeEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(894, 672);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.listOther);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.labelReferredFrom);
        this.Controls.Add(this.listReferral);
        this.Controls.Add(this.butBrowse);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.labelPatient);
        this.Controls.Add(this.listPatSelect);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butEditPaths);
        this.Controls.Add(this.textPath);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.comboCategory);
        this.Controls.Add(this.textDataFileName);
        this.Controls.Add(this.textTemplateName);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLetterMergeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Letter Merge";
        this.Load += new System.EventHandler(this.FormLetterMergeEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLetterMergeEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = LetterMergeCur.Description;
        mergePath = PrefC.getString(PrefName.LetterMergePath);
        textPath.Text = mergePath;
        textTemplateName.Text = LetterMergeCur.TemplateName;
        textDataFileName.Text = LetterMergeCur.DataFileName;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()].Length;i++)
        {
            comboCategory.Items.Add(DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][i].ItemName);
            if (LetterMergeCur.Category == DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][i].DefNum)
            {
                comboCategory.SelectedIndex = i;
            }
             
        }
        fillPatSelect();
        fillListReferral();
        fillListOther();
    }

    private void fillPatSelect() throws Exception {
        listPatSelect.Items.Clear();
        listPatSelect.Items.Add("PatNum");
        listPatSelect.Items.Add("LName");
        listPatSelect.Items.Add("FName");
        listPatSelect.Items.Add("MiddleI");
        listPatSelect.Items.Add("Preferred");
        listPatSelect.Items.Add("Title");
        listPatSelect.Items.Add("Salutation");
        listPatSelect.Items.Add("Address");
        listPatSelect.Items.Add("Address2");
        listPatSelect.Items.Add("City");
        listPatSelect.Items.Add("State");
        listPatSelect.Items.Add("Zip");
        listPatSelect.Items.Add("HmPhone");
        listPatSelect.Items.Add("WkPhone");
        listPatSelect.Items.Add("WirelessPhone");
        listPatSelect.Items.Add("Birthdate");
        listPatSelect.Items.Add("Email");
        listPatSelect.Items.Add("SSN");
        listPatSelect.Items.Add("Gender");
        listPatSelect.Items.Add("PatStatus");
        listPatSelect.Items.Add("Position");
        listPatSelect.Items.Add("CreditType");
        listPatSelect.Items.Add("BillingType");
        listPatSelect.Items.Add("ChartNumber");
        listPatSelect.Items.Add("PriProv");
        listPatSelect.Items.Add("SecProv");
        listPatSelect.Items.Add("FeeSched");
        listPatSelect.Items.Add("ApptModNote");
        listPatSelect.Items.Add("AddrNote");
        listPatSelect.Items.Add("EstBalance");
        listPatSelect.Items.Add("FamFinUrgNote");
        listPatSelect.Items.Add("Guarantor");
        listPatSelect.Items.Add("ImageFolder");
        listPatSelect.Items.Add("MedUrgNote");
        listPatSelect.Items.Add("NextAptNum");
        listPatSelect.Items.Add("SchoolName");
        listPatSelect.Items.Add("StudentStatus");
        listPatSelect.Items.Add("MedicaidID");
        listPatSelect.Items.Add("Bal_0_30");
        listPatSelect.Items.Add("Bal_31_60");
        listPatSelect.Items.Add("Bal_61_90");
        listPatSelect.Items.Add("BalOver90");
        listPatSelect.Items.Add("InsEst");
        listPatSelect.Items.Add("BalTotal");
        listPatSelect.Items.Add("EmployerNum");
        listPatSelect.Items.Add("Race");
        listPatSelect.Items.Add("County");
        listPatSelect.Items.Add("GradeSchool");
        listPatSelect.Items.Add("GradeLevel");
        listPatSelect.Items.Add("Urgency");
        listPatSelect.Items.Add("DateFirstVisit");
        for (int i = 0;i < LetterMergeCur.Fields.Count;i++)
        {
            for (int j = 0;j < listPatSelect.Items.Count;j++)
            {
                if (StringSupport.equals(listPatSelect.Items[j].ToString(), (String)LetterMergeCur.Fields[i]))
                {
                    listPatSelect.SetSelected(j, true);
                }
                 
            }
        }
    }

    private void fillListReferral() throws Exception {
        listReferral.Items.Add("LName");
        listReferral.Items.Add("FName");
        listReferral.Items.Add("MName");
        listReferral.Items.Add("Title");
        listReferral.Items.Add("Address");
        listReferral.Items.Add("Address2");
        listReferral.Items.Add("City");
        listReferral.Items.Add("ST");
        listReferral.Items.Add("Zip");
        listReferral.Items.Add("Telephone");
        listReferral.Items.Add("Phone2");
        listReferral.Items.Add("Email");
        listReferral.Items.Add("IsHidden");
        listReferral.Items.Add("NotPerson");
        listReferral.Items.Add("PatNum");
        listReferral.Items.Add("ReferralNum");
        listReferral.Items.Add("Specialty");
        listReferral.Items.Add("SSN");
        listReferral.Items.Add("UsingTIN");
        listReferral.Items.Add("Note");
        for (int i = 0;i < LetterMergeCur.Fields.Count;i++)
        {
            for (int j = 0;j < listReferral.Items.Count;j++)
            {
                if (StringSupport.equals("referral." + listReferral.Items[j].ToString(), (String)LetterMergeCur.Fields[i]))
                {
                    listReferral.SetSelected(j, true);
                }
                 
            }
        }
    }

    private void fillListOther() throws Exception {
        listOther.Items.Add("TPResponsPartyNameFL");
        listOther.Items.Add("TPResponsPartyAddress");
        listOther.Items.Add("TPResponsPartyCityStZip");
        listOther.Items.Add("SiteDescription");
        listOther.Items.Add("DateOfLastSavedTP");
        listOther.Items.Add("DateRecallDue");
        listOther.Items.Add("CarrierName");
        listOther.Items.Add("CarrierAddress");
        listOther.Items.Add("CarrierCityStZip");
        listOther.Items.Add("SubscriberNameFL");
        listOther.Items.Add("SubscriberID");
        listOther.Items.Add("NextSchedAppt");
        listOther.Items.Add("Age");
        for (int i = 0;i < LetterMergeCur.Fields.Count;i++)
        {
            for (int j = 0;j < listOther.Items.Count;j++)
            {
                if (StringSupport.equals(listOther.Items[j].ToString(), (String)LetterMergeCur.Fields[i]))
                {
                    listOther.SetSelected(j, true);
                }
                 
            }
        }
    }

    private void butEditPaths_Click(Object sender, System.EventArgs e) throws Exception {
        FormPath FormP = new FormPath();
        FormP.ShowDialog();
        mergePath = PrefC.getString(PrefName.LetterMergePath);
        textPath.Text = mergePath;
    }

    private void butBrowse_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Directory.Exists(PrefC.getString(PrefName.LetterMergePath)))
        {
            MsgBox.show(this,"Letter merge path invalid");
            return ;
        }
         
        openFileDlg.InitialDirectory = PrefC.getString(PrefName.LetterMergePath);
        if (openFileDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        textTemplateName.Text = Path.GetFileName(openFileDlg.FileName);
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Directory.Exists(PrefC.getString(PrefName.LetterMergePath)))
        {
            MsgBox.show(this,"Letter merge path invalid");
            return ;
        }
         
        if (StringSupport.equals(textTemplateName.Text, ""))
        {
            MsgBox.show(this,"Please enter a template file name first.");
            return ;
        }
         
        String templateFile = CodeBase.ODFileUtils.CombinePaths(PrefC.getString(PrefName.LetterMergePath), textTemplateName.Text);
        if (File.Exists(templateFile))
        {
            MsgBox.show(this,"A file with that name already exists.  Choose a different name, or close this window to edit the template.");
            return ;
        }
         
        Object oMissing = System.Reflection.Missing.Value;
        Object oFalse = false;
        //Create an instance of Word.
        Word.Application WrdApp = new Word.Application();
        try
        {
            WrdApp = LetterMerges.getWordApp();
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Error.  Is MS Word installed?");
            return ;
        }

        //Create a new document.
        Object oName = templateFile;
        Word._Document wrdDoc = new Word._Document();
        RefSupport<Object> refVar___0 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___1 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___2 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___3 = new RefSupport<Object>(oMissing);
        wrdDoc = WrdApp.Documents.Add(refVar___0, refVar___1, refVar___2, refVar___3);
        oMissing = refVar___0.getValue();
        oMissing = refVar___1.getValue();
        oMissing = refVar___2.getValue();
        oMissing = refVar___3.getValue();
        RefSupport<Object> refVar___4 = new RefSupport<Object>(oName);
        RefSupport<Object> refVar___5 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___6 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___7 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___8 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___9 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___10 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___11 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___12 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___13 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___14 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___15 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___16 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___17 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___18 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___19 = new RefSupport<Object>(oMissing);
        wrdDoc.SaveAs(refVar___4, refVar___5, refVar___6, refVar___7, refVar___8, refVar___9, refVar___10, refVar___11, refVar___12, refVar___13, refVar___14, refVar___15, refVar___16, refVar___17, refVar___18, refVar___19);
        oName = refVar___4.getValue();
        oMissing = refVar___5.getValue();
        oMissing = refVar___6.getValue();
        oMissing = refVar___7.getValue();
        oMissing = refVar___8.getValue();
        oMissing = refVar___9.getValue();
        oMissing = refVar___10.getValue();
        oMissing = refVar___11.getValue();
        oMissing = refVar___12.getValue();
        oMissing = refVar___13.getValue();
        oMissing = refVar___14.getValue();
        oMissing = refVar___15.getValue();
        oMissing = refVar___16.getValue();
        oMissing = refVar___17.getValue();
        oMissing = refVar___18.getValue();
        oMissing = refVar___19.getValue();
        wrdDoc.Saved = true;
        RefSupport<Object> refVar___20 = new RefSupport<Object>(oFalse);
        RefSupport<Object> refVar___21 = new RefSupport<Object>(oMissing);
        RefSupport<Object> refVar___22 = new RefSupport<Object>(oMissing);
        wrdDoc.Close(refVar___20, refVar___21, refVar___22);
        oFalse = refVar___20.getValue();
        oMissing = refVar___21.getValue();
        oMissing = refVar___22.getValue();
        WrdApp.WindowState = Word.WdWindowState.wdWindowStateMinimize;
        wrdDoc = null;
        MsgBox.show(this,"Done. You can edit the new template after closing this window.");
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        LetterMerges.delete(LetterMergeCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Please enter a description");
            return ;
        }
         
        if (StringSupport.equals(this.textDataFileName.Text, "") || StringSupport.equals(this.textTemplateName.Text, ""))
        {
            MsgBox.show(this,"Filenames cannot be left blank.");
            return ;
        }
         
        if (comboCategory.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a category");
            return ;
        }
         
        if (listPatSelect.SelectedIndices.Count == 0 && listReferral.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"Please select at least one field.");
            return ;
        }
         
        Cursor.Current = Cursors.WaitCursor;
        LetterMergeCur.Description = textDescription.Text;
        LetterMergeCur.TemplateName = textTemplateName.Text;
        LetterMergeCur.DataFileName = textDataFileName.Text;
        LetterMergeCur.Category = DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][comboCategory.SelectedIndex].DefNum;
        if (IsNew)
        {
            LetterMerges.insert(LetterMergeCur);
        }
        else
        {
            LetterMerges.update(LetterMergeCur);
        } 
        LetterMergeFields.deleteForLetter(LetterMergeCur.LetterMergeNum);
        LetterMergeField field;
        for (int i = 0;i < listPatSelect.SelectedItems.Count;i++)
        {
            field = new LetterMergeField();
            field.LetterMergeNum = LetterMergeCur.LetterMergeNum;
            field.FieldName = (String)listPatSelect.SelectedItems[i];
            //(string)listPatSelect.Items[listPatSelect.SelectedIndices[i]];
            LetterMergeFields.insert(field);
        }
        for (int i = 0;i < listReferral.SelectedItems.Count;i++)
        {
            field = new LetterMergeField();
            field.LetterMergeNum = LetterMergeCur.LetterMergeNum;
            field.FieldName = "referral." + (String)listReferral.SelectedItems[i];
            LetterMergeFields.insert(field);
        }
        for (int i = 0;i < listOther.SelectedItems.Count;i++)
        {
            field = new LetterMergeField();
            field.LetterMergeNum = LetterMergeCur.LetterMergeNum;
            field.FieldName = (String)listOther.SelectedItems[i];
            LetterMergeFields.insert(field);
        }
        Cursor.Current = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


