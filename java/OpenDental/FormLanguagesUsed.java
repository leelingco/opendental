//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormLanguagesUsed;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLanguagesUsed  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private ListBox listAvailable = new ListBox();
    private Label label1 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Label label2 = new Label();
    private Label label3 = new Label();
    private ListBox listUsed = new ListBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private CultureInfo[] AllCultures = new CultureInfo[]();
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private TextBox textCustom = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butAddCustom;
    private ComboBox comboLanguagesIndicateNone = new ComboBox();
    private Label label5 = new Label();
    private List<String> LangsUsed = new List<String>();
    /**
    * 
    */
    public FormLanguagesUsed() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLanguagesUsed.class);
        this.listAvailable = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.listUsed = new System.Windows.Forms.ListBox();
        this.textCustom = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butAddCustom = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.comboLanguagesIndicateNone = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // listAvailable
        //
        this.listAvailable.FormattingEnabled = true;
        this.listAvailable.Location = new System.Drawing.Point(32, 107);
        this.listAvailable.Name = "listAvailable";
        this.listAvailable.Size = new System.Drawing.Size(278, 394);
        this.listAvailable.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(30, 80);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(281, 23);
        this.label1.TabIndex = 2;
        this.label1.Text = "All Languages";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(29, 26);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(474, 53);
        this.label2.TabIndex = 3;
        this.label2.Text = "This window lets you define which languages will be available to assign to patien" + "ts.\r\nThis will not change the language of the user interface.\r\nIt will only be u" + "sed when interacting with patients.";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(444, 80);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(281, 23);
        this.label3.TabIndex = 5;
        this.label3.Text = "Languages used by patients";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listUsed
        //
        this.listUsed.FormattingEnabled = true;
        this.listUsed.Location = new System.Drawing.Point(446, 107);
        this.listUsed.Name = "listUsed";
        this.listUsed.Size = new System.Drawing.Size(278, 134);
        this.listUsed.TabIndex = 4;
        //
        // textCustom
        //
        this.textCustom.Location = new System.Drawing.Point(32, 531);
        this.textCustom.Name = "textCustom";
        this.textCustom.Size = new System.Drawing.Size(278, 20);
        this.textCustom.TabIndex = 11;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(30, 504);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(281, 23);
        this.label4.TabIndex = 12;
        this.label4.Text = "Custom";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butAddCustom
        //
        this.butAddCustom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddCustom.setAutosize(true);
        this.butAddCustom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddCustom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddCustom.setCornerRadius(4F);
        this.butAddCustom.Image = Resources.getRight();
        this.butAddCustom.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butAddCustom.Location = new System.Drawing.Point(340, 527);
        this.butAddCustom.Name = "butAddCustom";
        this.butAddCustom.Size = new System.Drawing.Size(75, 26);
        this.butAddCustom.TabIndex = 13;
        this.butAddCustom.Text = "Add";
        this.butAddCustom.Click += new System.EventHandler(this.butAddCustom_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.Location = new System.Drawing.Point(618, 250);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(53, 26);
        this.butDown.TabIndex = 10;
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.Location = new System.Drawing.Point(547, 250);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(53, 26);
        this.butUp.TabIndex = 9;
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(446, 250);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(83, 26);
        this.butDelete.TabIndex = 8;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getRight();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butAdd.Location = new System.Drawing.Point(340, 107);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(649, 486);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
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
        this.butCancel.Location = new System.Drawing.Point(649, 527);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // comboLanguagesIndicateNone
        //
        this.comboLanguagesIndicateNone.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboLanguagesIndicateNone.Location = new System.Drawing.Point(446, 328);
        this.comboLanguagesIndicateNone.MaxDropDownItems = 30;
        this.comboLanguagesIndicateNone.Name = "comboLanguagesIndicateNone";
        this.comboLanguagesIndicateNone.Size = new System.Drawing.Size(278, 21);
        this.comboLanguagesIndicateNone.TabIndex = 163;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(444, 295);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(281, 30);
        this.label5.TabIndex = 164;
        this.label5.Text = "Indicator that patient has no specified language\r\nCustom languages only";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormLanguagesUsed
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(776, 580);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboLanguagesIndicateNone);
        this.Controls.Add(this.butAddCustom);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCustom);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listUsed);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listAvailable);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLanguagesUsed";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Language Definitions";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormLanguagesUsed_FormClosing);
        this.Load += new System.EventHandler(this.FormLanguagesUsed_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formLanguagesUsed_Load(Object sender, EventArgs e) throws Exception {
        AllCultures = CultureInfo.GetCultures(CultureTypes.NeutralCultures);
        String[] culturedescripts = new String[AllCultures.Length];
        for (int i = 0;i < AllCultures.Length;i++)
        {
            culturedescripts[i] = AllCultures[i].DisplayName;
        }
        Array.Sort(culturedescripts, AllCultures);
        for (int i = 0;i < AllCultures.Length;i++)
        {
            //sort based on descriptions
            listAvailable.Items.Add(AllCultures[i].DisplayName);
        }
        if (StringSupport.equals(PrefC.getString(PrefName.LanguagesUsedByPatients), ""))
        {
            LangsUsed = new List<String>();
        }
        else
        {
            LangsUsed = new List<String>(PrefC.getString(PrefName.LanguagesUsedByPatients).Split(','));
        } 
        fillListUsed();
    }

    /**
    * Also calls FillComboLanguagesIndicateNone().
    */
    private void fillListUsed() throws Exception {
        listUsed.Items.Clear();
        for (int i = 0;i < LangsUsed.Count;i++)
        {
            if (StringSupport.equals(LangsUsed[i], ""))
            {
                continue;
            }
             
            CultureInfo culture = CodeBase.MiscUtils.GetCultureFromThreeLetter(LangsUsed[i]);
            if (culture == null)
            {
                //custom language
                listUsed.Items.Add(LangsUsed[i]);
            }
            else
            {
                listUsed.Items.Add(culture.DisplayName);
            } 
        }
        fillComboLanguagesIndicateNone();
    }

    private void fillComboLanguagesIndicateNone() throws Exception {
        comboLanguagesIndicateNone.Items.Clear();
        for (int i = 0;i < LangsUsed.Count;i++)
        {
            if (StringSupport.equals(LangsUsed[i], ""))
            {
                continue;
            }
             
            CultureInfo culture = CodeBase.MiscUtils.GetCultureFromThreeLetter(LangsUsed[i]);
            if (culture == null)
            {
                //custom language
                comboLanguagesIndicateNone.Items.Add(LangsUsed[i]);
                //Only add custom languages to this combobox.
                if (StringSupport.equals(LangsUsed[i], PrefC.getString(PrefName.LanguagesIndicateNone)))
                {
                    comboLanguagesIndicateNone.SelectedIndex = comboLanguagesIndicateNone.Items.Count - 1;
                }
                 
            }
             
        }
    }

    //Select the item we just added.
    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (listAvailable.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a language first");
            return ;
        }
         
        String lang = AllCultures[listAvailable.SelectedIndex].ThreeLetterISOLanguageName;
        //eng,spa etc
        if (LangsUsed.Contains(lang))
        {
            MsgBox.show(this,"Language already added.");
            return ;
        }
         
        LangsUsed.Add(lang);
        fillListUsed();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (listUsed.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a language first");
            return ;
        }
         
        LangsUsed.RemoveAt(listUsed.SelectedIndex);
        fillListUsed();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        if (listUsed.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a language first");
            return ;
        }
         
        if (listUsed.SelectedIndex == 0)
        {
            return ;
        }
         
        int newIndex = listUsed.SelectedIndex - 1;
        LangsUsed.Reverse(listUsed.SelectedIndex - 1, 2);
        fillListUsed();
        listUsed.SetSelected(newIndex, true);
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        if (listUsed.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a language first");
            return ;
        }
         
        if (listUsed.SelectedIndex == listUsed.Items.Count - 1)
        {
            return ;
        }
         
        int newIndex = listUsed.SelectedIndex + 1;
        LangsUsed.Reverse(listUsed.SelectedIndex, 2);
        fillListUsed();
        listUsed.SetSelected(newIndex, true);
    }

    private void butAddCustom_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textCustom.Text, ""))
        {
            MsgBox.show(this,"Please enter a custom language first");
            return ;
        }
         
        String lang = textCustom.Text;
        if (LangsUsed.Contains(lang))
        {
            MsgBox.show(this,"Language already added.");
            return ;
        }
         
        LangsUsed.Add(lang);
        textCustom.Clear();
        fillListUsed();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        String str = "";
        for (int i = 0;i < LangsUsed.Count;i++)
        {
            if (i > 0)
            {
                str += ",";
            }
             
            str += LangsUsed[i];
        }
        Prefs.updateString(PrefName.LanguagesUsedByPatients,str);
        if (comboLanguagesIndicateNone.SelectedIndex == -1)
        {
            Prefs.updateString(PrefName.LanguagesIndicateNone,"");
        }
        else
        {
            Prefs.UpdateString(PrefName.LanguagesIndicateNone, comboLanguagesIndicateNone.Items[comboLanguagesIndicateNone.SelectedIndex].ToString());
        } 
        //prefs refresh handled by the calling form.
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formLanguagesUsed_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //if LanguagesUsedByPatients does not contain LanguagesIndicateNone clear LanguagesIndicateNone
        if (!PrefC.getString(PrefName.LanguagesUsedByPatients).Contains(PrefC.getString(PrefName.LanguagesIndicateNone)))
        {
            Prefs.updateString(PrefName.LanguagesIndicateNone,"");
        }
         
    }

}


