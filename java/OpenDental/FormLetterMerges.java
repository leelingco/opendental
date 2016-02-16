//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import OpenDental.DataValid;
import OpenDental.FormDefinitions;
import OpenDental.FormLetterMergeEdit;
import OpenDental.FormLetterMerges;
import OpenDental.FormQuery;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.LetterMerge;
import OpenDentBusiness.LetterMergeFields;
import OpenDentBusiness.LetterMerges;
import OpenDentBusiness.LetterMergesQueries;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Security;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLetterMerges  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butCancel;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    //private bool localChanged;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    //private int pagesPrinted=0;
    private System.Windows.Forms.ListBox listCategories = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listLetters = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butEditCats;
    private Patient PatCur;
    private List<LetterMerge> ListForCat = new List<LetterMerge>();
    private boolean changed = new boolean();
    private String mergePath = new String();
    //private Word.Application wrdApp;
    private Word._Document wrdDoc = new Word._Document();
    private Object oMissing = System.Reflection.Missing.Value;
    private Object oFalse = false;
    private OpenDental.UI.Button butMerge;
    private OpenDental.UI.Button butCreateData;
    private OpenDental.UI.Button butEditTemplate;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butViewData;
    private OpenDental.UI.Button butPreview;
    /**
    * 
    */
    public FormLetterMerges(Patient patCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        PatCur = patCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLetterMerges.class);
        this.butCancel = new OpenDental.UI.Button();
        this.listCategories = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butMerge = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.listLetters = new System.Windows.Forms.ListBox();
        this.butEditCats = new OpenDental.UI.Button();
        this.butCreateData = new OpenDental.UI.Button();
        this.butEditTemplate = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butPreview = new OpenDental.UI.Button();
        this.butViewData = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(462, 405);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(79, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listCategories
        //
        this.listCategories.Location = new System.Drawing.Point(15, 33);
        this.listCategories.Name = "listCategories";
        this.listCategories.Size = new System.Drawing.Size(164, 368);
        this.listCategories.TabIndex = 2;
        this.listCategories.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listCategories_MouseDown);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(124, 14);
        this.label1.TabIndex = 3;
        this.label1.Text = "Categories";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(206, 408);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butMerge
        //
        this.butMerge.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMerge.setAutosize(true);
        this.butMerge.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMerge.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMerge.setCornerRadius(4F);
        this.butMerge.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butMerge.Location = new System.Drawing.Point(21, 84);
        this.butMerge.Name = "butMerge";
        this.butMerge.Size = new System.Drawing.Size(79, 24);
        this.butMerge.TabIndex = 17;
        this.butMerge.Text = "Print";
        this.butMerge.Click += new System.EventHandler(this.butPrint_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(205, 14);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(124, 14);
        this.label3.TabIndex = 19;
        this.label3.Text = "Letters";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listLetters
        //
        this.listLetters.Location = new System.Drawing.Point(206, 33);
        this.listLetters.Name = "listLetters";
        this.listLetters.Size = new System.Drawing.Size(164, 368);
        this.listLetters.TabIndex = 18;
        this.listLetters.DoubleClick += new System.EventHandler(this.listLetters_DoubleClick);
        //
        // butEditCats
        //
        this.butEditCats.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditCats.setAutosize(true);
        this.butEditCats.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditCats.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditCats.setCornerRadius(4F);
        this.butEditCats.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEditCats.Location = new System.Drawing.Point(14, 408);
        this.butEditCats.Name = "butEditCats";
        this.butEditCats.Size = new System.Drawing.Size(98, 24);
        this.butEditCats.TabIndex = 20;
        this.butEditCats.Text = "Edit Categories";
        this.butEditCats.Click += new System.EventHandler(this.butEditCats_Click);
        //
        // butCreateData
        //
        this.butCreateData.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCreateData.setAutosize(true);
        this.butCreateData.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCreateData.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCreateData.setCornerRadius(4F);
        this.butCreateData.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCreateData.Location = new System.Drawing.Point(21, 22);
        this.butCreateData.Name = "butCreateData";
        this.butCreateData.Size = new System.Drawing.Size(79, 24);
        this.butCreateData.TabIndex = 21;
        this.butCreateData.Text = "Data File";
        this.butCreateData.Click += new System.EventHandler(this.butCreateData_Click);
        //
        // butEditTemplate
        //
        this.butEditTemplate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditTemplate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEditTemplate.setAutosize(true);
        this.butEditTemplate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditTemplate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditTemplate.setCornerRadius(4F);
        this.butEditTemplate.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEditTemplate.Location = new System.Drawing.Point(449, 348);
        this.butEditTemplate.Name = "butEditTemplate";
        this.butEditTemplate.Size = new System.Drawing.Size(92, 24);
        this.butEditTemplate.TabIndex = 22;
        this.butEditTemplate.Text = "Edit Template";
        this.butEditTemplate.Click += new System.EventHandler(this.butEditTemplate_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butViewData);
        this.groupBox1.Controls.Add(this.butPreview);
        this.groupBox1.Controls.Add(this.butMerge);
        this.groupBox1.Controls.Add(this.butCreateData);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(441, 178);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(126, 152);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Create";
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPreview.Location = new System.Drawing.Point(21, 115);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(79, 24);
        this.butPreview.TabIndex = 22;
        this.butPreview.Text = "Preview";
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // butViewData
        //
        this.butViewData.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butViewData.setAutosize(true);
        this.butViewData.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butViewData.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butViewData.setCornerRadius(4F);
        this.butViewData.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butViewData.Location = new System.Drawing.Point(21, 53);
        this.butViewData.Name = "butViewData";
        this.butViewData.Size = new System.Drawing.Size(79, 24);
        this.butViewData.TabIndex = 23;
        this.butViewData.Text = "View Data";
        this.butViewData.Click += new System.EventHandler(this.butViewData_Click);
        //
        // FormLetterMerges
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(579, 446);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butEditTemplate);
        this.Controls.Add(this.butEditCats);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listLetters);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listCategories);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLetterMerges";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Letter Merge";
        this.Load += new System.EventHandler(this.FormLetterMerges_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormLetterMerges_Closing);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formLetterMerges_Load(Object sender, System.EventArgs e) throws Exception {
        mergePath = PrefC.getString(PrefName.LetterMergePath);
        fillCats();
        if (listCategories.Items.Count > 0)
        {
            listCategories.SelectedIndex = 0;
        }
         
        fillLetters();
        if (listLetters.Items.Count > 0)
        {
            listLetters.SelectedIndex = 0;
        }
         
    }

    private void fillCats() throws Exception {
        listCategories.Items.Clear();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()].Length;i++)
        {
            listCategories.Items.Add(DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][i].ItemName);
        }
    }

    private void fillLetters() throws Exception {
        listLetters.Items.Clear();
        if (listCategories.SelectedIndex == -1)
        {
            ListForCat = new List<LetterMerge>();
            return ;
        }
         
        LetterMergeFields.refreshCache();
        LetterMerges.refreshCache();
        ListForCat = LetterMerges.GetListForCat(listCategories.SelectedIndex);
        for (int i = 0;i < ListForCat.Count;i++)
        {
            listLetters.Items.Add(ListForCat[i].Description);
        }
    }

    private void listCategories_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        //selectedIndex already changed.
        fillLetters();
        if (listLetters.Items.Count > 0)
        {
            listLetters.SelectedIndex = 0;
        }
         
    }

    private void butEditCats_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions FormD = new FormDefinitions(DefCat.LetterMergeCats);
        FormD.ShowDialog();
        fillCats();
    }

    private void listLetters_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listLetters.SelectedIndex == -1)
        {
            return ;
        }
         
        int selectedRow = listLetters.SelectedIndex;
        LetterMerge letter = ListForCat[listLetters.SelectedIndex];
        FormLetterMergeEdit FormL = new FormLetterMergeEdit(letter);
        FormL.ShowDialog();
        fillLetters();
        if (listLetters.Items.Count > selectedRow)
        {
            listLetters.SetSelected(selectedRow, true);
        }
         
        changed = true;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCategories.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        LetterMerge letter = new LetterMerge();
        letter.Category = DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][listCategories.SelectedIndex].DefNum;
        letter.Fields = new List<String>();
        FormLetterMergeEdit FormL = new FormLetterMergeEdit(letter);
        FormL.IsNew = true;
        FormL.ShowDialog();
        fillLetters();
        changed = true;
    }

    private boolean createDataFile(String fileName, LetterMerge letter) throws Exception {
        DataTable table = LetterMergesQueries.getLetterMergeInfo(PatCur,letter);
        table = FormQuery.makeReadable(table,null);
        try
        {
            StreamWriter sw = new StreamWriter(fileName, false);
            try
            {
                {
                    String line = "";
                    for (int i = 0;i < letter.Fields.Count;i++)
                    {
                        if (letter.Fields[i].StartsWith("referral."))
                        {
                            line += "Ref" + letter.Fields[i].Substring(9);
                        }
                        else
                        {
                            line += letter.Fields[i];
                        } 
                        if (i < letter.Fields.Count - 1)
                        {
                            line += "\t";
                        }
                         
                    }
                    sw.WriteLine(line);
                    String cell = new String();
                    for (int i = 0;i < table.Rows.Count;i++)
                    {
                        line = "";
                        for (int j = 0;j < table.Columns.Count;j++)
                        {
                            cell = table.Rows[i][j].ToString();
                            cell = cell.Replace("\r", "");
                            cell = cell.Replace("\n", "");
                            cell = cell.Replace("\t", "");
                            cell = cell.Replace("\"", "");
                            line += cell;
                            if (j < table.Columns.Count - 1)
                            {
                                line += "\t";
                            }
                             
                        }
                        sw.WriteLine(line);
                    }
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"File in use by another program.  Close and try again.");
            return false;
        }

        return true;
    }

    private void butCreateData_Click(Object sender, System.EventArgs e) throws Exception {
        if (!createData())
        {
            return ;
        }
         
        MsgBox.show(this,"done");
    }

    private void butViewData_Click(Object sender, EventArgs e) throws Exception {
        if (!createData())
        {
            return ;
        }
         
        LetterMerge letterCur = ListForCat[listLetters.SelectedIndex];
        String dataFile = PrefC.getString(PrefName.LetterMergePath) + letterCur.DataFileName;
        Process.Start(dataFile);
    }

    private boolean createData() throws Exception {
        if (listLetters.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a letter first.");
            return false;
        }
         
        LetterMerge letterCur = ListForCat[listLetters.SelectedIndex];
        String dataFile = PrefC.getString(PrefName.LetterMergePath) + letterCur.DataFileName;
        if (!Directory.Exists(PrefC.getString(PrefName.LetterMergePath)))
        {
            MsgBox.show(this,"Letter merge path not valid.");
            return false;
        }
         
        Cursor = Cursors.WaitCursor;
        if (!createDataFile(dataFile,letterCur))
        {
            Cursor = Cursors.Default;
            return false;
        }
         
        Cursor = Cursors.Default;
        return true;
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (listLetters.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a letter first.");
            return ;
        }
         
        LetterMerge letterCur = ListForCat[listLetters.SelectedIndex];
        String templateFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.TemplateName);
        String dataFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.DataFileName);
        if (!File.Exists(templateFile))
        {
            MsgBox.show(this,"Template file does not exist.");
            return ;
        }
         
        PrintDocument pd = new PrintDocument();
        if (!PrinterL.setPrinter(pd,PrintSituation.Default,PatCur.PatNum,"Letter merge " + letterCur.Description + " printed"))
        {
            return ;
        }
         
        if (!createDataFile(dataFile,letterCur))
        {
            return ;
        }
         
        Word.MailMerge wrdMailMerge = new Word.MailMerge();
        //Create an instance of Word.
        Word.Application WrdApp = new Word.Application();
        try
        {
            WrdApp = LetterMerges.getWordApp();
        }
        catch (Exception __dummyCatchVar1)
        {
            MsgBox.show(this,"Error.  Is MS Word installed?");
            return ;
        }

        //Open a document.
        Object oName = templateFile;
        RefSupport<Object> refVar___0 = new RefSupport<Object>(oName);
        RefSupport refVar___1 = new RefSupport(oMissing);
        RefSupport refVar___2 = new RefSupport(oMissing);
        RefSupport refVar___3 = new RefSupport(oMissing);
        RefSupport refVar___4 = new RefSupport(oMissing);
        RefSupport refVar___5 = new RefSupport(oMissing);
        RefSupport refVar___6 = new RefSupport(oMissing);
        RefSupport refVar___7 = new RefSupport(oMissing);
        RefSupport refVar___8 = new RefSupport(oMissing);
        RefSupport refVar___9 = new RefSupport(oMissing);
        RefSupport refVar___10 = new RefSupport(oMissing);
        RefSupport refVar___11 = new RefSupport(oMissing);
        RefSupport refVar___12 = new RefSupport(oMissing);
        RefSupport refVar___13 = new RefSupport(oMissing);
        RefSupport refVar___14 = new RefSupport(oMissing);
        wrdDoc = WrdApp.Documents.Open(refVar___0, refVar___1, refVar___2, refVar___3, refVar___4, refVar___5, refVar___6, refVar___7, refVar___8, refVar___9, refVar___10, refVar___11, refVar___12, refVar___13, refVar___14);
        oName = refVar___0.getValue();
        oMissing = refVar___1.getValue();
        oMissing = refVar___2.getValue();
        oMissing = refVar___3.getValue();
        oMissing = refVar___4.getValue();
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
        wrdDoc.Select();
        wrdMailMerge = wrdDoc.MailMerge;
        //Attach the data file.
        RefSupport refVar___15 = new RefSupport(oMissing);
        RefSupport refVar___16 = new RefSupport(oMissing);
        RefSupport refVar___17 = new RefSupport(oMissing);
        RefSupport refVar___18 = new RefSupport(oMissing);
        RefSupport refVar___19 = new RefSupport(oMissing);
        RefSupport refVar___20 = new RefSupport(oMissing);
        RefSupport refVar___21 = new RefSupport(oMissing);
        RefSupport refVar___22 = new RefSupport(oMissing);
        RefSupport refVar___23 = new RefSupport(oMissing);
        RefSupport refVar___24 = new RefSupport(oMissing);
        RefSupport refVar___25 = new RefSupport(oMissing);
        RefSupport refVar___26 = new RefSupport(oMissing);
        RefSupport refVar___27 = new RefSupport(oMissing);
        RefSupport refVar___28 = new RefSupport(oMissing);
        RefSupport refVar___29 = new RefSupport(oMissing);
        wrdDoc.MailMerge.OpenDataSource(dataFile, refVar___15, refVar___16, refVar___17, refVar___18, refVar___19, refVar___20, refVar___21, refVar___22, refVar___23, refVar___24, refVar___25, refVar___26, refVar___27, refVar___28, refVar___29);
        oMissing = refVar___15.getValue();
        oMissing = refVar___16.getValue();
        oMissing = refVar___17.getValue();
        oMissing = refVar___18.getValue();
        oMissing = refVar___19.getValue();
        oMissing = refVar___20.getValue();
        oMissing = refVar___21.getValue();
        oMissing = refVar___22.getValue();
        oMissing = refVar___23.getValue();
        oMissing = refVar___24.getValue();
        oMissing = refVar___25.getValue();
        oMissing = refVar___26.getValue();
        oMissing = refVar___27.getValue();
        oMissing = refVar___28.getValue();
        oMissing = refVar___29.getValue();
        wrdMailMerge.Destination = Word.WdMailMergeDestination.wdSendToPrinter;
        //WrdApp.ActivePrinter=pd.PrinterSettings.PrinterName;
        //replaced with following 4 lines due to MS bug that changes computer default printer
        Object oWBasic = WrdApp.WordBasic;
        Object[] oWBValues = new Object[]{ pd.PrinterSettings.PrinterName, 1 };
        String[] sWBNames = new String[]{ "Printer", "DoNotSetAsSysDefault" };
        oWBasic.GetType().InvokeMember("FilePrintSetup", BindingFlags.InvokeMethod, null, oWBasic, oWBValues, null, null, sWBNames);
        RefSupport refVar___30 = new RefSupport(oFalse);
        wrdMailMerge.Execute(refVar___30);
        oFalse = refVar___30.getValue();
        //Close the original form document since just one record.
        wrdDoc.Saved = true;
        RefSupport refVar___31 = new RefSupport(oFalse);
        RefSupport refVar___32 = new RefSupport(oMissing);
        RefSupport refVar___33 = new RefSupport(oMissing);
        wrdDoc.Close(refVar___31, refVar___32, refVar___33);
        oFalse = refVar___31.getValue();
        oMissing = refVar___32.getValue();
        oMissing = refVar___33.getValue();
        //At this point, Word remains open with no documents.
        WrdApp.WindowState = Word.WdWindowState.wdWindowStateMinimize;
        wrdMailMerge = null;
        wrdDoc = null;
        Commlog CommlogCur = new Commlog();
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        CommlogCur.Mode_ = CommItemMode.Mail;
        CommlogCur.SentOrReceived = CommSentOrReceived.Sent;
        CommlogCur.PatNum = PatCur.PatNum;
        CommlogCur.Note = "Letter sent: " + letterCur.Description + ". ";
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        Commlogs.insert(CommlogCur);
        DialogResult = DialogResult.OK;
    }

    private void butPreview_Click(Object sender, System.EventArgs e) throws Exception {
        if (listLetters.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a letter first.");
            return ;
        }
         
        LetterMerge letterCur = ListForCat[listLetters.SelectedIndex];
        String templateFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.TemplateName);
        String dataFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.DataFileName);
        if (!File.Exists(templateFile))
        {
            MsgBox.show(this,"Template file does not exist.");
            return ;
        }
         
        if (!createDataFile(dataFile,letterCur))
        {
            return ;
        }
         
        Word.MailMerge wrdMailMerge = new Word.MailMerge();
        //Create an instance of Word.
        Word.Application WrdApp = new Word.Application();
        try
        {
            WrdApp = LetterMerges.getWordApp();
        }
        catch (Exception __dummyCatchVar2)
        {
            MsgBox.show(this,"Error. Is Word installed?");
            return ;
        }

        //Open a document.
        Object oName = templateFile;
        RefSupport<Object> refVar___34 = new RefSupport<Object>(oName);
        RefSupport refVar___35 = new RefSupport(oMissing);
        RefSupport refVar___36 = new RefSupport(oMissing);
        RefSupport refVar___37 = new RefSupport(oMissing);
        RefSupport refVar___38 = new RefSupport(oMissing);
        RefSupport refVar___39 = new RefSupport(oMissing);
        RefSupport refVar___40 = new RefSupport(oMissing);
        RefSupport refVar___41 = new RefSupport(oMissing);
        RefSupport refVar___42 = new RefSupport(oMissing);
        RefSupport refVar___43 = new RefSupport(oMissing);
        RefSupport refVar___44 = new RefSupport(oMissing);
        RefSupport refVar___45 = new RefSupport(oMissing);
        RefSupport refVar___46 = new RefSupport(oMissing);
        RefSupport refVar___47 = new RefSupport(oMissing);
        RefSupport refVar___48 = new RefSupport(oMissing);
        wrdDoc = WrdApp.Documents.Open(refVar___34, refVar___35, refVar___36, refVar___37, refVar___38, refVar___39, refVar___40, refVar___41, refVar___42, refVar___43, refVar___44, refVar___45, refVar___46, refVar___47, refVar___48);
        oName = refVar___34.getValue();
        oMissing = refVar___35.getValue();
        oMissing = refVar___36.getValue();
        oMissing = refVar___37.getValue();
        oMissing = refVar___38.getValue();
        oMissing = refVar___39.getValue();
        oMissing = refVar___40.getValue();
        oMissing = refVar___41.getValue();
        oMissing = refVar___42.getValue();
        oMissing = refVar___43.getValue();
        oMissing = refVar___44.getValue();
        oMissing = refVar___45.getValue();
        oMissing = refVar___46.getValue();
        oMissing = refVar___47.getValue();
        oMissing = refVar___48.getValue();
        wrdDoc.Select();
        wrdMailMerge = wrdDoc.MailMerge;
        //Attach the data file.
        RefSupport refVar___49 = new RefSupport(oMissing);
        RefSupport refVar___50 = new RefSupport(oMissing);
        RefSupport refVar___51 = new RefSupport(oMissing);
        RefSupport refVar___52 = new RefSupport(oMissing);
        RefSupport refVar___53 = new RefSupport(oMissing);
        RefSupport refVar___54 = new RefSupport(oMissing);
        RefSupport refVar___55 = new RefSupport(oMissing);
        RefSupport refVar___56 = new RefSupport(oMissing);
        RefSupport refVar___57 = new RefSupport(oMissing);
        RefSupport refVar___58 = new RefSupport(oMissing);
        RefSupport refVar___59 = new RefSupport(oMissing);
        RefSupport refVar___60 = new RefSupport(oMissing);
        RefSupport refVar___61 = new RefSupport(oMissing);
        RefSupport refVar___62 = new RefSupport(oMissing);
        RefSupport refVar___63 = new RefSupport(oMissing);
        wrdDoc.MailMerge.OpenDataSource(dataFile, refVar___49, refVar___50, refVar___51, refVar___52, refVar___53, refVar___54, refVar___55, refVar___56, refVar___57, refVar___58, refVar___59, refVar___60, refVar___61, refVar___62, refVar___63);
        oMissing = refVar___49.getValue();
        oMissing = refVar___50.getValue();
        oMissing = refVar___51.getValue();
        oMissing = refVar___52.getValue();
        oMissing = refVar___53.getValue();
        oMissing = refVar___54.getValue();
        oMissing = refVar___55.getValue();
        oMissing = refVar___56.getValue();
        oMissing = refVar___57.getValue();
        oMissing = refVar___58.getValue();
        oMissing = refVar___59.getValue();
        oMissing = refVar___60.getValue();
        oMissing = refVar___61.getValue();
        oMissing = refVar___62.getValue();
        oMissing = refVar___63.getValue();
        wrdMailMerge.Destination = Word.WdMailMergeDestination.wdSendToNewDocument;
        RefSupport refVar___64 = new RefSupport(oFalse);
        wrdMailMerge.Execute(refVar___64);
        oFalse = refVar___64.getValue();
        //Close the original form document since just one record.
        wrdDoc.Saved = true;
        RefSupport refVar___65 = new RefSupport(oFalse);
        RefSupport refVar___66 = new RefSupport(oMissing);
        RefSupport refVar___67 = new RefSupport(oMissing);
        wrdDoc.Close(refVar___65, refVar___66, refVar___67);
        oFalse = refVar___65.getValue();
        oMissing = refVar___66.getValue();
        oMissing = refVar___67.getValue();
        //At this point, Word remains open with just one new document.
        WrdApp.Activate();
        if (WrdApp.WindowState == Word.WdWindowState.wdWindowStateMinimize)
        {
            WrdApp.WindowState = Word.WdWindowState.wdWindowStateMaximize;
        }
         
        wrdMailMerge = null;
        wrdDoc = null;
        Commlog CommlogCur = new Commlog();
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        CommlogCur.Mode_ = CommItemMode.Mail;
        CommlogCur.SentOrReceived = CommSentOrReceived.Sent;
        CommlogCur.PatNum = PatCur.PatNum;
        CommlogCur.Note = "Letter sent: " + letterCur.Description + ". ";
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        Commlogs.insert(CommlogCur);
        //this window now closes regardless of whether the user saved the comm item.
        DialogResult = DialogResult.OK;
    }

    private void butEditTemplate_Click(Object sender, System.EventArgs e) throws Exception {
        if (listLetters.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a letter first.");
            return ;
        }
         
        LetterMerge letterCur = ListForCat[listLetters.SelectedIndex];
        String templateFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.TemplateName);
        String dataFile = CodeBase.ODFileUtils.combinePaths(PrefC.getString(PrefName.LetterMergePath),letterCur.DataFileName);
        if (!File.Exists(templateFile))
        {
            MessageBox.Show(Lan.g(this,"Template file does not exist:") + "  " + templateFile);
            return ;
        }
         
        if (!createDataFile(dataFile,letterCur))
        {
            return ;
        }
         
        //Create an instance of Word.
        Word.Application WrdApp = new Word.Application();
        try
        {
            WrdApp = LetterMerges.getWordApp();
        }
        catch (Exception __dummyCatchVar3)
        {
            MsgBox.show(this,"Error.  Is MS Word installed?");
            return ;
        }

        //Open a document.
        Object oName = templateFile;
        RefSupport<Object> refVar___68 = new RefSupport<Object>(oName);
        RefSupport refVar___69 = new RefSupport(oMissing);
        RefSupport refVar___70 = new RefSupport(oMissing);
        RefSupport refVar___71 = new RefSupport(oMissing);
        RefSupport refVar___72 = new RefSupport(oMissing);
        RefSupport refVar___73 = new RefSupport(oMissing);
        RefSupport refVar___74 = new RefSupport(oMissing);
        RefSupport refVar___75 = new RefSupport(oMissing);
        RefSupport refVar___76 = new RefSupport(oMissing);
        RefSupport refVar___77 = new RefSupport(oMissing);
        RefSupport refVar___78 = new RefSupport(oMissing);
        RefSupport refVar___79 = new RefSupport(oMissing);
        RefSupport refVar___80 = new RefSupport(oMissing);
        RefSupport refVar___81 = new RefSupport(oMissing);
        RefSupport refVar___82 = new RefSupport(oMissing);
        wrdDoc = WrdApp.Documents.Open(refVar___68, refVar___69, refVar___70, refVar___71, refVar___72, refVar___73, refVar___74, refVar___75, refVar___76, refVar___77, refVar___78, refVar___79, refVar___80, refVar___81, refVar___82);
        oName = refVar___68.getValue();
        oMissing = refVar___69.getValue();
        oMissing = refVar___70.getValue();
        oMissing = refVar___71.getValue();
        oMissing = refVar___72.getValue();
        oMissing = refVar___73.getValue();
        oMissing = refVar___74.getValue();
        oMissing = refVar___75.getValue();
        oMissing = refVar___76.getValue();
        oMissing = refVar___77.getValue();
        oMissing = refVar___78.getValue();
        oMissing = refVar___79.getValue();
        oMissing = refVar___80.getValue();
        oMissing = refVar___81.getValue();
        oMissing = refVar___82.getValue();
        wrdDoc.Select();
        //Attach the data file.
        RefSupport refVar___83 = new RefSupport(oMissing);
        RefSupport refVar___84 = new RefSupport(oMissing);
        RefSupport refVar___85 = new RefSupport(oMissing);
        RefSupport refVar___86 = new RefSupport(oMissing);
        RefSupport refVar___87 = new RefSupport(oMissing);
        RefSupport refVar___88 = new RefSupport(oMissing);
        RefSupport refVar___89 = new RefSupport(oMissing);
        RefSupport refVar___90 = new RefSupport(oMissing);
        RefSupport refVar___91 = new RefSupport(oMissing);
        RefSupport refVar___92 = new RefSupport(oMissing);
        RefSupport refVar___93 = new RefSupport(oMissing);
        RefSupport refVar___94 = new RefSupport(oMissing);
        RefSupport refVar___95 = new RefSupport(oMissing);
        RefSupport refVar___96 = new RefSupport(oMissing);
        RefSupport refVar___97 = new RefSupport(oMissing);
        wrdDoc.MailMerge.OpenDataSource(dataFile, refVar___83, refVar___84, refVar___85, refVar___86, refVar___87, refVar___88, refVar___89, refVar___90, refVar___91, refVar___92, refVar___93, refVar___94, refVar___95, refVar___96, refVar___97);
        oMissing = refVar___83.getValue();
        oMissing = refVar___84.getValue();
        oMissing = refVar___85.getValue();
        oMissing = refVar___86.getValue();
        oMissing = refVar___87.getValue();
        oMissing = refVar___88.getValue();
        oMissing = refVar___89.getValue();
        oMissing = refVar___90.getValue();
        oMissing = refVar___91.getValue();
        oMissing = refVar___92.getValue();
        oMissing = refVar___93.getValue();
        oMissing = refVar___94.getValue();
        oMissing = refVar___95.getValue();
        oMissing = refVar___96.getValue();
        oMissing = refVar___97.getValue();
        //At this point, Word remains open with just one new document.
        if (WrdApp.WindowState == Word.WdWindowState.wdWindowStateMinimize)
        {
            WrdApp.WindowState = Word.WdWindowState.wdWindowStateMaximize;
        }
         
        wrdDoc = null;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formLetterMerges_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.LetterMerge);
        }
         
    }

}


