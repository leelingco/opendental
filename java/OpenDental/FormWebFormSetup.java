//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormSheetPicker;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.POut;
import OpenDental.Properties.Resources;
import OpenDental.SheetUtil;
import OpenDental.UI.ODGridColumn;
import OpenDental.WebSheets.Sheets;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;


/**
* This Form is primarily used by the dental office to upload sheetDefs
*/
public class FormWebFormSetup  extends Form 
{

    String RegistrationKey = PrefC.getString(PrefName.RegistrationKey);
    String SheetDefAddress = "";
    Sheets wh = new Sheets();
    OpenDental.WebSheets.webforms_sheetdef[] sheetDefList = new OpenDental.WebSheets.webforms_sheetdef[]();
    long DentalOfficeID = 0;
    //private String SynchUrlStaging="https://192.168.0.196/WebHostSynch/Sheets.asmx";
    private String SynchUrlStaging = "https://10.10.1.196/WebHostSynch/Sheets.asmx";
    private String SynchUrlDev = "http://localhost:2923/Sheets.asmx";
    public FormWebFormSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWebFormSetup_Load(Object sender, EventArgs e) throws Exception {
    }

    private void formWebFormSetup_Shown(Object sender, EventArgs e) throws Exception {
        fetchValuesFromWebServer();
    }

    private void fetchValuesFromWebServer() throws Exception {
        try
        {
            String WebHostSynchServerURL = PrefC.getString(PrefName.WebHostSynchServerURL);
            textboxWebHostAddress.Text = WebHostSynchServerURL;
            butSave.Enabled = false;
            //#if DEBUG // ignore the certificate errors for the staging machine and development machine
            if ((StringSupport.equals(WebHostSynchServerURL, SynchUrlStaging)) || (StringSupport.equals(WebHostSynchServerURL, SynchUrlDev)))
            {
                ignoreCertificateErrors();
            }
             
            //#endif
            Cursor = Cursors.WaitCursor;
            if (!testWebServiceExists())
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"Either the web service is not available or the WebHostSynch URL is incorrect");
                return ;
            }
             
            DentalOfficeID = wh.getDentalOfficeID(RegistrationKey);
            if (wh.getDentalOfficeID(RegistrationKey) == 0)
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"Registration key provided by the dental office is incorrect");
                return ;
            }
             
            OpenDental.WebSheets.webforms_preference PrefObj = wh.getPreferences(RegistrationKey);
            if (PrefObj == null)
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"There has been an error retrieving values from the server");
            }
             
            butWebformBorderColor.BackColor = Color.FromArgb(PrefObj.getColorBorder());
            SheetDefAddress = wh.getSheetDefAddress(RegistrationKey);
            //dennis: the below if statement is for backward compatibility only April 14 2011 and can be removed later.
            if (String.IsNullOrEmpty(PrefObj.getCultureName()))
            {
                PrefObj.setCultureName(System.Globalization.CultureInfo.CurrentCulture.Name);
                wh.setPreferencesV2(RegistrationKey,PrefObj);
            }
             
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }

        fillGrid();
        //Also gets sheet def list from server
        Cursor = Cursors.Default;
    }

    private void ignoreCertificateErrors() throws Exception {
        System.Net.ServicePointManager.ServerCertificateValidationCallback += ;
    }

    //accept any certificate if debugging
    /**
    * An empty method to test if the webservice is up and running. This was made with the intention of testing the correctness of the webservice URL. If an incorrect webservice URL is used in a background thread the exception cannot be handled easily to a point where even a correct URL cannot be keyed in by the user. Because an exception in a background thread closes the Form which spawned it.
    * 
    *  @return
    */
    private boolean testWebServiceExists() throws Exception {
        try
        {
            wh.setUrl(textboxWebHostAddress.Text);
            //if(textboxWebHostAddress.Text.Contains("192.168.0.196") || textboxWebHostAddress.Text.Contains("localhost")) {
            if (textboxWebHostAddress.Text.Contains("10.10.1.196") || textboxWebHostAddress.Text.Contains("localhost"))
            {
                ignoreCertificateErrors();
            }
             
            // done so that TestWebServiceExists() does not thow an error.
            if (wh.serviceExists())
            {
                return true;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }

        return true;
    }

    //(Exception ex) {
    /**
    * This now also gets a new list of sheet defs from the server.  But it's only called after testing that the web service exists.
    */
    private void fillGrid() throws Exception {
        try
        {
            wh.setUrl(textboxWebHostAddress.Text);
            sheetDefList = wh.downloadSheetDefs(RegistrationKey);
            gridMain.getColumns().Clear();
            ODGridColumn col = new ODGridColumn(Lan.g(this,"Description"),200);
            gridMain.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Browser Address For Patients"),510);
            gridMain.getColumns().add(col);
            gridMain.getRows().Clear();
            for (int i = 0;i < sheetDefList.Length;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.setTag(sheetDefList[i]);
                row.getCells().Add(sheetDefList[i].Description);
                String SheetFormAddress = SheetDefAddress + "?DentalOfficeID=" + DentalOfficeID + "&WebSheetDefID=" + sheetDefList[i].WebSheetDefID;
                row.getCells().add(SheetFormAddress);
                gridMain.getRows().add(row);
            }
            gridMain.endUpdate();
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }
    
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        openBrowser();
    }

    private void gridMain_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Right)
        {
            menuWebFormSetupRight.Show(gridMain, new Point(e.X, e.Y));
        }
         
    }

    private void menuItemNavigateURL_Click(Object sender, EventArgs e) throws Exception {
        openBrowser();
    }

    private void menuItemCopyURL_Click(Object sender, EventArgs e) throws Exception {
        OpenDental.WebSheets.webforms_sheetdef WebSheetDef = (OpenDental.WebSheets.webforms_sheetdef)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag;
        String SheetFormAddress = SheetDefAddress + "?DentalOfficeID=" + DentalOfficeID + "&WebSheetDefID=" + WebSheetDef.getWebSheetDefID();
        Clipboard.SetText(SheetFormAddress);
    }

    private void openBrowser() throws Exception {
        OpenDental.WebSheets.webforms_sheetdef WebSheetDef = (OpenDental.WebSheets.webforms_sheetdef)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag;
        String SheetFormAddress = SheetDefAddress + "?DentalOfficeID=" + DentalOfficeID + "&WebSheetDefID=" + WebSheetDef.getWebSheetDefID();
        System.Diagnostics.Process.Start(SheetFormAddress);
    }

    private void textboxWebHostAddress_TextChanged(Object sender, EventArgs e) throws Exception {
        butSave.Enabled = true;
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        //disabled unless user changed url
        Cursor = Cursors.WaitCursor;
        if (!testWebServiceExists())
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Either the web service is not available or the WebHostSynch URL is incorrect");
            return ;
        }
         
        try
        {
            Prefs.UpdateString(PrefName.WebHostSynchServerURL, textboxWebHostAddress.Text.Trim());
            butSave.Enabled = false;
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }

        fetchValuesFromWebServer();
        Cursor = Cursors.Default;
    }

    private void butWebformBorderColor_Click(Object sender, EventArgs e) throws Exception {
        showColorDialog();
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        showColorDialog();
    }

    private void showColorDialog() throws Exception {
        colorDialog1.Color = butWebformBorderColor.BackColor;
        if (colorDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        butWebformBorderColor.BackColor = colorDialog1.Color;
        Cursor = Cursors.WaitCursor;
        if (!testWebServiceExists())
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Either the web service is not available or the WebHostSynch URL is incorrect.");
            return ;
        }
         
        try
        {
            if (wh.getDentalOfficeID(RegistrationKey) == 0)
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"Registration key incorrect.");
                return ;
            }
             
            OpenDental.WebSheets.webforms_preference PrefObj = new OpenDental.WebSheets.webforms_preference();
            PrefObj.setColorBorder(butWebformBorderColor.BackColor.ToArgb());
            PrefObj.setCultureName(System.Globalization.CultureInfo.CurrentCulture.Name);
            boolean IsPrefSet = wh.setPreferencesV2(RegistrationKey,PrefObj);
            Cursor = Cursors.Default;
            if (!IsPrefSet)
            {
                MsgBox.show(this,"Error, color could not be saved to server.");
            }
             
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }
    
    }

    private void loadImagesToSheetDef(SheetDef sheetDefCur) throws Exception {
        for (int j = 0;j < sheetDefCur.SheetFieldDefs.Count;j++)
        {
            try
            {
                if (sheetDefCur.SheetFieldDefs[j].FieldType == SheetFieldType.Image)
                {
                    String filePathAndName = CodeBase.ODFileUtils.CombinePaths(SheetUtil.getImagePath(), sheetDefCur.SheetFieldDefs[j].FieldName);
                    Image img = null;
                    if (StringSupport.equals(sheetDefCur.SheetFieldDefs[j].FieldName, "Patient Info.gif"))
                    {
                        img = Resources.getPatient_Info();
                    }
                    else if (File.Exists(filePathAndName))
                    {
                        img = Image.FromFile(filePathAndName);
                    }
                      
                    //sheetDefCur.SheetFieldDefs[j].ImageData=POut.Bitmap(new Bitmap(img),ImageFormat.Png);//Because that's what we did before. Review this later.
                    long fileByteSize = 0;
                    MemoryStream ms = new MemoryStream();
                    try
                    {
                        {
                            img.Save(ms, img.RawFormat);
                            // done solely to compute the file size of the image
                            fileByteSize = ms.Length;
                        }
                    }
                    finally
                    {
                        if (ms != null)
                            Disposable.mkDisposable(ms).dispose();
                         
                    }
                    if (fileByteSize > 2000000)
                    {
                        //for large images greater that ~2MB use jpeg format for compression. Large images in the 4MB + range have difficulty being displayed. It could be an issue with MYSQL or ASP.NET
                        sheetDefCur.SheetFieldDefs[j].ImageData = POut.Bitmap(new Bitmap(img), ImageFormat.Jpeg);
                    }
                    else
                    {
                        sheetDefCur.SheetFieldDefs[j].ImageData = POut.Bitmap(new Bitmap(img), img.RawFormat);
                    } 
                }
                else
                {
                    sheetDefCur.SheetFieldDefs[j].ImageData = "";
                } 
            }
            catch (Exception ex)
            {
                // because null is not allowed
                sheetDefCur.SheetFieldDefs[j].ImageData = "";
                MessageBox.Show(ex.Message);
            }
        
        }
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormSheetPicker FormS = new FormSheetPicker();
        FormS.SheetType = SheetTypeEnum.PatientForm;
        FormS.HideKioskButton = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < FormS.SelectedSheetDefs.Count;i++)
        {
            //Make sure each selected sheet contains FName, LName, and Birthdate.
            //There will always only be one
            boolean hasFName = false;
            boolean hasLName = false;
            boolean hasBirthdate = false;
            for (int j = 0;j < FormS.SelectedSheetDefs[i].SheetFieldDefs.Count;j++)
            {
                if (FormS.SelectedSheetDefs[i].SheetFieldDefs[j].FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                if (StringSupport.equals(FormS.SelectedSheetDefs[i].SheetFieldDefs[j].FieldName, "FName"))
                {
                    hasFName = true;
                }
                else if (StringSupport.equals(FormS.SelectedSheetDefs[i].SheetFieldDefs[j].FieldName, "LName"))
                {
                    hasLName = true;
                }
                else if (StringSupport.equals(FormS.SelectedSheetDefs[i].SheetFieldDefs[j].FieldName, "Birthdate"))
                {
                    hasBirthdate = true;
                }
                   
            }
            if (!hasFName || !hasLName || !hasBirthdate)
            {
                MessageBox.Show(Lan.g(this,"The sheet called ") + "\"" + FormS.SelectedSheetDefs[i].Description + "\"" + Lan.g(this," does not contain all three required fields: LName, FName, and Birthdate."));
                return ;
            }
             
        }
        Cursor = Cursors.WaitCursor;
        if (!testWebServiceExists())
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Either the web service is not available or the WebHostSynch URL is incorrect");
            return ;
        }
         
        for (int i = 0;i < FormS.SelectedSheetDefs.Count;i++)
        {
            //There will always only be one
            LoadImagesToSheetDef(FormS.SelectedSheetDefs[i]);
            wh.Timeout = 300000;
            //for slow connections more timeout is provided. The  default is 100 seconds i.e 100000
            wh.UpLoadSheetDef(RegistrationKey, FormS.SelectedSheetDefs[i]);
        }
        fillGrid();
        Cursor = Cursors.Default;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item from the grid first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        if (!testWebServiceExists())
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Either the web service is not available or the WebHostSynch URL is incorrect");
            return ;
        }
         
        OpenDental.WebSheets.webforms_sheetdef wf_sheetDef = (OpenDental.WebSheets.webforms_sheetdef)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag;
        wh.deleteSheetDef(RegistrationKey,wf_sheetDef.getWebSheetDefID());
        fillGrid();
        Cursor = Cursors.Default;
    }

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
        this.textboxWebHostAddress = new System.Windows.Forms.TextBox();
        this.labelWebhostURL = new System.Windows.Forms.Label();
        this.labelBorderColor = new System.Windows.Forms.Label();
        this.butWebformBorderColor = new System.Windows.Forms.Button();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.butChange = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butCancel = new OpenDental.UI.Button();
        this.butSave = new OpenDental.UI.Button();
        this.menuWebFormSetupRight = new System.Windows.Forms.ContextMenu();
        this.menuItemCopyURL = new System.Windows.Forms.MenuItem();
        this.menuItemNavigateURL = new System.Windows.Forms.MenuItem();
        this.SuspendLayout();
        //
        // textboxWebHostAddress
        //
        this.textboxWebHostAddress.Location = new System.Drawing.Point(171, 12);
        this.textboxWebHostAddress.Name = "textboxWebHostAddress";
        this.textboxWebHostAddress.Size = new System.Drawing.Size(445, 20);
        this.textboxWebHostAddress.TabIndex = 45;
        this.textboxWebHostAddress.TextChanged += new System.EventHandler(this.textboxWebHostAddress_TextChanged);
        //
        // labelWebhostURL
        //
        this.labelWebhostURL.Location = new System.Drawing.Point(0, 13);
        this.labelWebhostURL.Name = "labelWebhostURL";
        this.labelWebhostURL.Size = new System.Drawing.Size(169, 19);
        this.labelWebhostURL.TabIndex = 46;
        this.labelWebhostURL.Text = "Host Server Address";
        this.labelWebhostURL.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBorderColor
        //
        this.labelBorderColor.Location = new System.Drawing.Point(53, 37);
        this.labelBorderColor.Name = "labelBorderColor";
        this.labelBorderColor.Size = new System.Drawing.Size(111, 19);
        this.labelBorderColor.TabIndex = 48;
        this.labelBorderColor.Text = "Border Color";
        this.labelBorderColor.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butWebformBorderColor
        //
        this.butWebformBorderColor.BackColor = System.Drawing.Color.RoyalBlue;
        this.butWebformBorderColor.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butWebformBorderColor.Location = new System.Drawing.Point(172, 37);
        this.butWebformBorderColor.Name = "butWebformBorderColor";
        this.butWebformBorderColor.Size = new System.Drawing.Size(24, 24);
        this.butWebformBorderColor.TabIndex = 71;
        this.butWebformBorderColor.UseVisualStyleBackColor = false;
        this.butWebformBorderColor.Click += new System.EventHandler(this.butWebformBorderColor_Click);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(202, 37);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(68, 24);
        this.butChange.TabIndex = 72;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
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
        this.butDelete.Location = new System.Drawing.Point(223, 446);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 58;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(121, 446);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 57;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 67);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(860, 363);
        this.gridMain.TabIndex = 56;
        this.gridMain.setTitle("Sheet Defs");
        this.gridMain.setTranslationName("TableSheetDefs");
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
        this.gridMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridMain_MouseUp);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(795, 446);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(622, 10);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(64, 24);
        this.butSave.TabIndex = 74;
        this.butSave.Text = "Save";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // menuWebFormSetupRight
        //
        this.menuWebFormSetupRight.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemCopyURL, this.menuItemNavigateURL });
        //
        // menuItemCopyURL
        //
        this.menuItemCopyURL.Index = 0;
        this.menuItemCopyURL.Text = "Copy URL to clipboard";
        this.menuItemCopyURL.Click += new System.EventHandler(this.menuItemCopyURL_Click);
        //
        // menuItemNavigateURL
        //
        this.menuItemNavigateURL.Index = 1;
        this.menuItemNavigateURL.Text = "Navigate to the URL on the web browser";
        this.menuItemNavigateURL.Click += new System.EventHandler(this.menuItemNavigateURL_Click);
        //
        // FormWebFormSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(884, 486);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butWebformBorderColor);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.labelBorderColor);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.textboxWebHostAddress);
        this.Controls.Add(this.labelWebhostURL);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWebFormSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Web Form Setup";
        this.Load += new System.EventHandler(this.FormWebFormSetup_Load);
        this.Shown += new System.EventHandler(this.FormWebFormSetup_Shown);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textboxWebHostAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelWebhostURL = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBorderColor = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butWebformBorderColor = new System.Windows.Forms.Button();
    private System.Windows.Forms.ColorDialog colorDialog1 = new System.Windows.Forms.ColorDialog();
    private OpenDental.UI.Button butChange;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butSave;
    private System.Windows.Forms.ContextMenu menuWebFormSetupRight = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemCopyURL = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemNavigateURL = new System.Windows.Forms.MenuItem();
}


