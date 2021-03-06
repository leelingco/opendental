//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:57 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormRegistrationKey;
import OpenDental.FormUpdateSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PrefL;
import OpenDentBusiness.DocumentMisc;
import OpenDentBusiness.DocumentMiscs;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* 
*/
public class FormUpdateSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private TextBox textWebsitePath = new TextBox();
    private Label label3 = new Label();
    private TextBox textRegKey = new TextBox();
    private Label label2 = new Label();
    private Label label4 = new Label();
    private TextBox textUpdateServerAddress = new TextBox();
    private Label label1 = new Label();
    private TextBox textMultiple = new TextBox();
    private Label label5 = new Label();
    private Label label6 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private TextBox textWebProxyPassword = new TextBox();
    private Label label9 = new Label();
    private TextBox textWebProxyUserName = new TextBox();
    private Label label8 = new Label();
    private TextBox textWebProxyAddress = new TextBox();
    private Label label7 = new Label();
    private OpenDental.UI.Button butChange;
    private CheckBox checkShowMsi = new CheckBox();
    private Label label10 = new Label();
    private Label labelRecopy = new Label();
    private OpenDental.UI.Button butRecopy;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormUpdateSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUpdateSetup.class);
        this.textWebsitePath = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textRegKey = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textUpdateServerAddress = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textMultiple = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textWebProxyPassword = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textWebProxyUserName = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textWebProxyAddress = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.checkShowMsi = new System.Windows.Forms.CheckBox();
        this.label10 = new System.Windows.Forms.Label();
        this.labelRecopy = new System.Windows.Forms.Label();
        this.butRecopy = new OpenDental.UI.Button();
        this.butChange = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // textWebsitePath
        //
        this.textWebsitePath.Location = new System.Drawing.Point(249, 69);
        this.textWebsitePath.Name = "textWebsitePath";
        this.textWebsitePath.Size = new System.Drawing.Size(434, 20);
        this.textWebsitePath.TabIndex = 36;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(69, 70);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(180, 19);
        this.label3.TabIndex = 37;
        this.label3.Text = "Website Path for Updates";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRegKey
        //
        this.textRegKey.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textRegKey.Location = new System.Drawing.Point(249, 211);
        this.textRegKey.Name = "textRegKey";
        this.textRegKey.ReadOnly = true;
        this.textRegKey.Size = new System.Drawing.Size(193, 20);
        this.textRegKey.TabIndex = 40;
        this.textRegKey.TextChanged += new System.EventHandler(this.textRegKey_TextChanged);
        this.textRegKey.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textRegKey_KeyUp);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(69, 212);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(180, 19);
        this.label2.TabIndex = 41;
        this.label2.Text = "Registration Key";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(521, 212);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(204, 33);
        this.label4.TabIndex = 42;
        this.label4.Text = "Valid for one office ONLY.  This is tracked.";
        //
        // textUpdateServerAddress
        //
        this.textUpdateServerAddress.Location = new System.Drawing.Point(249, 31);
        this.textUpdateServerAddress.Name = "textUpdateServerAddress";
        this.textUpdateServerAddress.Size = new System.Drawing.Size(434, 20);
        this.textUpdateServerAddress.TabIndex = 43;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(69, 32);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(180, 19);
        this.label1.TabIndex = 44;
        this.label1.Text = "Server Address for Updates";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMultiple
        //
        this.textMultiple.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textMultiple.Location = new System.Drawing.Point(249, 248);
        this.textMultiple.Multiline = true;
        this.textMultiple.Name = "textMultiple";
        this.textMultiple.Size = new System.Drawing.Size(266, 41);
        this.textMultiple.TabIndex = 45;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(69, 249);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(180, 40);
        this.label5.TabIndex = 46;
        this.label5.Text = "Simultaneously update other databases";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(521, 248);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(210, 41);
        this.label6.TabIndex = 47;
        this.label6.Text = "Separate with commas.  Do not include this database.";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textWebProxyPassword);
        this.groupBox1.Controls.Add(this.label9);
        this.groupBox1.Controls.Add(this.textWebProxyUserName);
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.textWebProxyAddress);
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Location = new System.Drawing.Point(69, 105);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(614, 100);
        this.groupBox1.TabIndex = 48;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Web Proxy (most users will ignore this section)";
        //
        // textWebProxyPassword
        //
        this.textWebProxyPassword.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textWebProxyPassword.Location = new System.Drawing.Point(180, 71);
        this.textWebProxyPassword.Name = "textWebProxyPassword";
        this.textWebProxyPassword.PasswordChar = '*';
        this.textWebProxyPassword.Size = new System.Drawing.Size(127, 20);
        this.textWebProxyPassword.TabIndex = 46;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(25, 72);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(155, 19);
        this.label9.TabIndex = 47;
        this.label9.Text = "Password";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textWebProxyUserName
        //
        this.textWebProxyUserName.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textWebProxyUserName.Location = new System.Drawing.Point(180, 45);
        this.textWebProxyUserName.Name = "textWebProxyUserName";
        this.textWebProxyUserName.Size = new System.Drawing.Size(127, 20);
        this.textWebProxyUserName.TabIndex = 44;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(25, 46);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(155, 19);
        this.label8.TabIndex = 45;
        this.label8.Text = "UserName";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textWebProxyAddress
        //
        this.textWebProxyAddress.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textWebProxyAddress.Location = new System.Drawing.Point(180, 19);
        this.textWebProxyAddress.Name = "textWebProxyAddress";
        this.textWebProxyAddress.Size = new System.Drawing.Size(363, 20);
        this.textWebProxyAddress.TabIndex = 42;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(25, 20);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(155, 19);
        this.label7.TabIndex = 43;
        this.label7.Text = "Address";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowMsi
        //
        this.checkShowMsi.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowMsi.Location = new System.Drawing.Point(69, 307);
        this.checkShowMsi.Name = "checkShowMsi";
        this.checkShowMsi.Size = new System.Drawing.Size(194, 24);
        this.checkShowMsi.TabIndex = 51;
        this.checkShowMsi.Text = "Show buttons for msi";
        this.checkShowMsi.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowMsi.UseVisualStyleBackColor = true;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(269, 309);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(246, 19);
        this.label10.TabIndex = 52;
        this.label10.Text = "(most users will not check this option)";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelRecopy
        //
        this.labelRecopy.Location = new System.Drawing.Point(11, 348);
        this.labelRecopy.Name = "labelRecopy";
        this.labelRecopy.Size = new System.Drawing.Size(237, 60);
        this.labelRecopy.TabIndex = 53;
        this.labelRecopy.Text = "The AtoZ folder contains an UpdateFiles folder which should have current copies o" + "f all the files from C:\\Program Files\\Open Dental\\\r\n";
        this.labelRecopy.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butRecopy
        //
        this.butRecopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRecopy.setAutosize(true);
        this.butRecopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRecopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRecopy.setCornerRadius(4F);
        this.butRecopy.Location = new System.Drawing.Point(249, 355);
        this.butRecopy.Name = "butRecopy";
        this.butRecopy.Size = new System.Drawing.Size(67, 23);
        this.butRecopy.TabIndex = 54;
        this.butRecopy.Text = "Recopy";
        this.butRecopy.Click += new System.EventHandler(this.butRecopy_Click);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(448, 209);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(67, 23);
        this.butChange.TabIndex = 50;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(607, 341);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(607, 382);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormUpdateSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(734, 433);
        this.Controls.Add(this.butRecopy);
        this.Controls.Add(this.labelRecopy);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.checkShowMsi);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textMultiple);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textUpdateServerAddress);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textRegKey);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textWebsitePath);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormUpdateSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Update Setup";
        this.Load += new System.EventHandler(this.FormUpdateSetup_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formUpdateSetup_Load(Object sender, EventArgs e) throws Exception {
        textUpdateServerAddress.Text = PrefC.getString(PrefName.UpdateServerAddress);
        textWebsitePath.Text = PrefC.getString(PrefName.UpdateWebsitePath);
        textWebProxyAddress.Text = PrefC.getString(PrefName.UpdateWebProxyAddress);
        textWebProxyUserName.Text = PrefC.getString(PrefName.UpdateWebProxyUserName);
        textWebProxyPassword.Text = PrefC.getString(PrefName.UpdateWebProxyPassword);
        String regkey = PrefC.getString(PrefName.RegistrationKey);
        if (regkey.Length == 16)
        {
            textRegKey.Text = regkey.Substring(0, 4) + "-" + regkey.Substring(4, 4) + "-" + regkey.Substring(8, 4) + "-" + regkey.Substring(12, 4);
        }
        else
        {
            textRegKey.Text = regkey;
        } 
        textMultiple.Text = PrefC.getString(PrefName.UpdateMultipleDatabases);
        checkShowMsi.Checked = PrefC.getBool(PrefName.UpdateShowMsiButtons);
        if (!PrefC.getAtoZfolderUsed())
        {
            labelRecopy.Text = "Recopy all of the files from C:\\Program Files\\Open Dental\\ into a special place in the database for future use in updating other computers.";
        }
         
    }

    private void textRegKey_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        int cursor = textRegKey.SelectionStart;
        //textRegKey.Text=textRegKey.Text.ToUpper();
        int length = textRegKey.Text.Length;
        if (Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{5}$"))
        {
            textRegKey.Text = textRegKey.Text.Substring(0, 4) + "-" + textRegKey.Text.Substring(4);
        }
        else if (Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{4}-[A-Z0-9]{5}$"))
        {
            textRegKey.Text = textRegKey.Text.Substring(0, 9) + "-" + textRegKey.Text.Substring(9);
        }
        else if (Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{5}$"))
        {
            textRegKey.Text = textRegKey.Text.Substring(0, 14) + "-" + textRegKey.Text.Substring(14);
        }
           
        if (textRegKey.Text.Length > length)
        {
            cursor++;
        }
         
        textRegKey.SelectionStart = cursor;
    }

    private void textRegKey_TextChanged(Object sender, EventArgs e) throws Exception {
        int cursor = textRegKey.SelectionStart;
        textRegKey.Text = textRegKey.Text.ToUpper();
        textRegKey.SelectionStart = cursor;
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        FormRegistrationKey formR = new FormRegistrationKey();
        formR.ShowDialog();
        DataValid.setInvalid(InvalidType.Prefs);
        String regkey = PrefC.getString(PrefName.RegistrationKey);
        if (regkey.Length == 16)
        {
            textRegKey.Text = regkey.Substring(0, 4) + "-" + regkey.Substring(4, 4) + "-" + regkey.Substring(8, 4) + "-" + regkey.Substring(12, 4);
        }
        else
        {
            textRegKey.Text = regkey;
        } 
    }

    private void butRecopy_Click(Object sender, EventArgs e) throws Exception {
        Version versionCurrent = new Version(Application.ProductVersion);
        String folderUpdate = "";
        if (PrefC.getAtoZfolderUsed())
        {
            folderUpdate = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"UpdateFiles");
        }
        else
        {
            //db
            folderUpdate = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), "UpdateFiles");
            if (Directory.Exists(folderUpdate))
            {
                Directory.Delete(folderUpdate, true);
            }
             
            DocumentMisc docmisc = DocumentMiscs.getUpdateFilesZip();
            if (docmisc != null)
            {
                byte[] rawBytes = Convert.FromBase64String(docmisc.RawBase64);
                ZipFile unzipped = ZipFile.Read(rawBytes);
                try
                {
                    {
                        unzipped.ExtractAll(folderUpdate);
                    }
                }
                finally
                {
                    if (unzipped != null)
                        Disposable.mkDisposable(unzipped).dispose();
                     
                }
            }
             
        } 
        //identify the ideal situation where everything is already in place and no copy is needed.
        if (Directory.Exists(folderUpdate))
        {
            String filePath = CodeBase.ODFileUtils.combinePaths(folderUpdate,"Manifest.txt");
            if (File.Exists(filePath))
            {
                String fileText = File.ReadAllText(filePath);
                if (StringSupport.equals(fileText, versionCurrent.ToString(3)))
                {
                    if (!MsgBox.show(this,MsgBoxButtons.YesNo,"According to the information in UpdateFiles\\Manifest.txt, the UpdateFiles folder is current.  Recopy anyway?"))
                    {
                        return ;
                    }
                     
                }
                 
            }
             
        }
         
        Cursor = Cursors.WaitCursor;
        if (!PrefL.copyFromHereToUpdateFiles(versionCurrent))
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        Cursor = Cursors.Default;
        MsgBox.show(this,"Recopied.");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textRegKey.Text, "") && !Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$") && !Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{16}$"))
        {
            MsgBox.show(this,"Invalid registration key format.");
            return ;
        }
         
        if (textMultiple.Text.Contains(" "))
        {
            MsgBox.show(this,"No spaces allowed in the database list.");
            return ;
        }
         
        String regkey = "";
        if (Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$"))
        {
            regkey = textRegKey.Text.Substring(0, 4) + textRegKey.Text.Substring(5, 4) + textRegKey.Text.Substring(10, 4) + textRegKey.Text.Substring(15, 4);
        }
        else if (Regex.IsMatch(textRegKey.Text, "^[A-Z0-9]{16}$"))
        {
            regkey = textRegKey.Text;
        }
          
        if (Prefs.UpdateString(PrefName.UpdateServerAddress, textUpdateServerAddress.Text) | Prefs.UpdateBool(PrefName.UpdateShowMsiButtons, checkShowMsi.Checked) | Prefs.UpdateString(PrefName.UpdateWebsitePath, textWebsitePath.Text) | Prefs.UpdateString(PrefName.UpdateWebProxyAddress, textWebProxyAddress.Text) | Prefs.UpdateString(PrefName.UpdateWebProxyUserName, textWebProxyUserName.Text) | Prefs.UpdateString(PrefName.UpdateWebProxyPassword, textWebProxyPassword.Text) | Prefs.updateString(PrefName.RegistrationKey,regkey) | Prefs.UpdateString(PrefName.UpdateMultipleDatabases, textMultiple.Text))
        {
            Cursor = Cursors.WaitCursor;
            DataValid.setInvalid(InvalidType.Prefs);
            Cursor = Cursors.Default;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


