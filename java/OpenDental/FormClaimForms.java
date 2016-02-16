//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:48 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormClaimFormEdit;
import OpenDental.FormClaimForms;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimFormItems;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClaimForms  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListBox listClaimForms = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butExport;
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDefault;
    private OpenDental.UI.Button butReassign;
    private Label label2 = new Label();
    private ComboBox comboReassign = new ComboBox();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormClaimForms() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimForms.class);
        this.listClaimForms = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboReassign = new System.Windows.Forms.ComboBox();
        this.butReassign = new OpenDental.UI.Button();
        this.butDefault = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // listClaimForms
        //
        this.listClaimForms.Location = new System.Drawing.Point(45, 33);
        this.listClaimForms.Name = "listClaimForms";
        this.listClaimForms.Size = new System.Drawing.Size(203, 251);
        this.listClaimForms.TabIndex = 2;
        this.listClaimForms.DoubleClick += new System.EventHandler(this.listClaimForms_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(44, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(161, 16);
        this.label1.TabIndex = 6;
        this.label1.Text = "Edit Claim Form";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butCopy);
        this.groupBox1.Controls.Add(this.butExport);
        this.groupBox1.Controls.Add(this.butAdd);
        this.groupBox1.Controls.Add(this.butImport);
        this.groupBox1.Controls.Add(this.butDelete);
        this.groupBox1.Location = new System.Drawing.Point(29, 298);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(232, 122);
        this.groupBox1.TabIndex = 11;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Advanced Users Only";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(264, 106);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(203, 47);
        this.label2.TabIndex = 14;
        this.label2.Text = "Reassign all insurance plans that use the selected claim form at the left to the " + "claim form below";
        //
        // comboReassign
        //
        this.comboReassign.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboReassign.FormattingEnabled = true;
        this.comboReassign.Location = new System.Drawing.Point(266, 156);
        this.comboReassign.MaxDropDownItems = 20;
        this.comboReassign.Name = "comboReassign";
        this.comboReassign.Size = new System.Drawing.Size(169, 21);
        this.comboReassign.TabIndex = 15;
        //
        // butReassign
        //
        this.butReassign.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReassign.setAutosize(true);
        this.butReassign.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReassign.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReassign.setCornerRadius(4F);
        this.butReassign.Location = new System.Drawing.Point(266, 73);
        this.butReassign.Name = "butReassign";
        this.butReassign.Size = new System.Drawing.Size(87, 23);
        this.butReassign.TabIndex = 13;
        this.butReassign.Text = "Reassign";
        this.butReassign.Click += new System.EventHandler(this.butReassign_Click);
        //
        // butDefault
        //
        this.butDefault.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDefault.setAutosize(true);
        this.butDefault.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDefault.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDefault.setCornerRadius(4F);
        this.butDefault.Location = new System.Drawing.Point(266, 33);
        this.butDefault.Name = "butDefault";
        this.butDefault.Size = new System.Drawing.Size(87, 23);
        this.butDefault.TabIndex = 12;
        this.butDefault.Text = "Set Default";
        this.butDefault.Click += new System.EventHandler(this.butDefault_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Location = new System.Drawing.Point(15, 87);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(115, 23);
        this.butCopy.TabIndex = 4;
        this.butCopy.Text = "Make a Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Location = new System.Drawing.Point(142, 25);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(75, 23);
        this.butExport.TabIndex = 9;
        this.butExport.Text = "Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(15, 25);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 3;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Location = new System.Drawing.Point(141, 56);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 23);
        this.butImport.TabIndex = 10;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(15, 56);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 5;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(385, 397);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormClaimForms
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(495, 440);
        this.Controls.Add(this.comboReassign);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butReassign);
        this.Controls.Add(this.butDefault);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listClaimForms);
        this.Controls.Add(this.butClose);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimForms";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Claim Forms";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormClaimForms_Closing);
        this.Load += new System.EventHandler(this.FormClaimForms_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formClaimForms_Load(Object sender, System.EventArgs e) throws Exception {
        ClaimFormItems.refreshCache();
        fillList();
    }

    /**
    * 
    */
    private void fillList() throws Exception {
        ClaimForms.refreshCache();
        listClaimForms.Items.Clear();
        comboReassign.Items.Clear();
        String description = new String();
        for (int i = 0;i < ClaimForms.getListLong().Length;i++)
        {
            description = ClaimForms.getListLong()[i].Description;
            if (ClaimForms.getListLong()[i].IsHidden)
            {
                description += " (hidden)";
            }
             
            if (ClaimForms.getListLong()[i].ClaimFormNum == PrefC.getLong(PrefName.DefaultClaimForm))
            {
                description += " (default)";
            }
             
            listClaimForms.Items.Add(description);
            comboReassign.Items.Add(description);
        }
    }

    private void listClaimForms_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
            return ;
         
        FormClaimFormEdit FormCFE = new FormClaimFormEdit();
        FormCFE.ClaimFormCur = ClaimForms.getListLong()[listClaimForms.SelectedIndex];
        FormCFE.ShowDialog();
        changed = true;
        //we don't really know if they changed it, but always refresh
        //ClaimFormItems refreshed within FormCFE
        fillList();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ClaimForm ClaimFormCur = new ClaimForm();
        ClaimForms.insert(ClaimFormCur);
        FormClaimFormEdit FormCFE = new FormClaimFormEdit();
        FormCFE.ClaimFormCur = ClaimFormCur;
        FormCFE.IsNew = true;
        FormCFE.ShowDialog();
        if (FormCFE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        //ClaimFormItems refreshed within FormCFE
        fillList();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        //ClaimForms.Cur=ClaimForms.ListLong[listClaimForms.SelectedIndex];
        if (!StringSupport.equals(ClaimForms.getListLong()[listClaimForms.SelectedIndex].UniqueID, ""))
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete a premade claimform, but you can hide it instead."));
            return ;
        }
         
        if (!ClaimForms.Delete(ClaimForms.getListLong()[listClaimForms.SelectedIndex]))
        {
            MessageBox.Show(Lan.g(this,"Claim form is already in use."));
            return ;
        }
         
        changed = true;
        ClaimFormItems.refreshCache();
        fillList();
    }

    private void butCopy_Click(Object sender, System.EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        ClaimForm ClaimFormCur = ClaimForms.getListLong()[listClaimForms.SelectedIndex].Copy();
        long oldClaimFormNum = ClaimFormCur.ClaimFormNum;
        ClaimFormCur.UniqueID = "";
        //designates it as a user added claimform
        ClaimForms.insert(ClaimFormCur);
        //this duplicates the original claimform, but no items.
        long newClaimFormNum = ClaimFormCur.ClaimFormNum;
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            //ClaimFormItems.GetListForForm(ClaimForms.ListLong[listClaimForms.SelectedIndex].ClaimFormNum);
            //ClaimFormItems.Cur=ClaimFormItems.ListForForm[i];
            ClaimFormCur.Items[i].ClaimFormNum = newClaimFormNum;
            ClaimFormItems.Insert(ClaimFormCur.Items[i]);
        }
        ClaimFormItems.refreshCache();
        changed = true;
        fillList();
    }

    private void butExport_Click(Object sender, System.EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        ClaimForm ClaimFormCur = ClaimForms.getListLong()[listClaimForms.SelectedIndex];
        SaveFileDialog saveDlg = new SaveFileDialog();
        String filename = "ClaimForm" + ClaimFormCur.Description + ".xml";
        saveDlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        saveDlg.FileName = filename;
        if (saveDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        //MessageBox.Show(saveDlg.FileName);
        XmlSerializer serializer = new XmlSerializer(ClaimForm.class);
        TextWriter writer = new StreamWriter(saveDlg.FileName);
        serializer.Serialize(writer, ClaimFormCur);
        writer.Close();
        MessageBox.Show("Exported");
    }

    private void butImport_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog openDlg = new OpenFileDialog();
        openDlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        if (openDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            ImportForm(openDlg.FileName, false, "");
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        MessageBox.Show("Imported");
        ClaimFormItems.refreshCache();
        changed = true;
        fillList();
    }

    /**
    * Can be called externally as part of the conversion sequence.  Surround with try catch.  Always returns the ClaimFormNum of the claimform.  If using xmlData, path will be ignored, so leave it blank.
    */
    public static long importForm(String path, boolean isUpdateSequence, String xmlData) throws Exception {
        ClaimForm tempClaimForm = new ClaimForm();
        XmlSerializer serializer = new XmlSerializer(ClaimForm.class);
        if (StringSupport.equals(xmlData, ""))
        {
            //use path
            if (!File.Exists(path))
            {
                throw new ApplicationException(Lan.g("FormClaimForm","File does not exist."));
            }
             
            try
            {
                TextReader reader = new StreamReader(path);
                try
                {
                    {
                        tempClaimForm = (ClaimForm)serializer.Deserialize(reader);
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new ApplicationException(Lan.g("FormClaimForm","Invalid file format"));
            }
        
        }
        else
        {
            try
            {
                //use xmlData
                TextReader reader = new StringReader(xmlData);
                try
                {
                    {
                        tempClaimForm = (ClaimForm)serializer.Deserialize(reader);
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
            catch (Exception __dummyCatchVar1)
            {
                throw new ApplicationException(Lan.g("FormClaimForm","Invalid file format"));
            }
        
        } 
        long retVal = 0;
        boolean isNew = true;
        if (isUpdateSequence)
        {
            ClaimFormItems.refreshCache();
            ClaimForms.refreshCache();
        }
         
        if (!StringSupport.equals(tempClaimForm.UniqueID, ""))
        {
            for (int i = 0;i < ClaimForms.getListLong().Length;i++)
            {
                //if it's blank, it's always inserted.
                if (StringSupport.equals(ClaimForms.getListLong()[i].UniqueID, tempClaimForm.UniqueID))
                {
                    isNew = false;
                    retVal = ClaimForms.getListLong()[i].ClaimFormNum;
                }
                 
            }
        }
         
        if (isNew)
        {
            ClaimForms.insert(tempClaimForm);
            //now we have a primary key.
            retVal = tempClaimForm.ClaimFormNum;
            for (int j = 0;j < tempClaimForm.Items.Length;j++)
            {
                tempClaimForm.Items[j].ClaimFormNum = tempClaimForm.ClaimFormNum;
                //so even though the ClaimFormNum is wrong, this line fixes it.
                ClaimFormItems.Insert(tempClaimForm.Items[j]);
            }
        }
        else
        {
            if (!isUpdateSequence)
            {
                if (MessageBox.Show(tempClaimForm.Description + " already exists.  Replace?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
                {
                    return 0;
                }
                 
            }
             
            ClaimForms.updateByUniqueID(tempClaimForm);
        } 
        return retVal;
    }

    private void butDefault_Click(Object sender, EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a claimform from the list first.");
            return ;
        }
         
        if (Prefs.UpdateLong(PrefName.DefaultClaimForm, ClaimForms.getListLong()[listClaimForms.SelectedIndex].ClaimFormNum))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        fillList();
    }

    private void butReassign_Click(Object sender, EventArgs e) throws Exception {
        if (listClaimForms.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a claimform from the list at the left first.");
            return ;
        }
         
        if (comboReassign.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a claimform from the list below.");
            return ;
        }
         
        long result = ClaimForms.Reassign(ClaimForms.getListLong()[listClaimForms.SelectedIndex].ClaimFormNum, ClaimForms.getListLong()[comboReassign.SelectedIndex].ClaimFormNum);
        MessageBox.Show(result.ToString() + Lan.g(this," plans changed."));
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        //Prefs.Cur.PrefName="GenericEClaimsForm";
        //if(listEClaim.SelectedIndex==-1){
        //	Prefs.Cur.ValueString="";
        //}
        //else{
        //	Prefs.Cur.ValueString=POut.PInt(ClaimForms.ListShort[listEClaim.SelectedIndex].ClaimFormNum);
        //}
        //Prefs.UpdateCur();
        Close();
    }

    private void formClaimForms_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ClaimForms);
        }
         
    }

}


