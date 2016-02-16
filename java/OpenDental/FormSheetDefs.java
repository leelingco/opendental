//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:47 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormSheetDef;
import OpenDental.FormSheetDefEdit;
import OpenDental.FormSheetDefs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefC;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldDefs;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class FormSheetDefs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butNew;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid grid2;
    private OpenDental.UI.ODGrid grid1;
    private OpenDental.UI.Button butCopy;
    //private bool changed;
    //public bool IsSelectionMode;
    //<summary>Only used if IsSelectionMode.  On OK, contains selected siteNum.  Can be 0.  Can also be set ahead of time externally.</summary>
    //public int SelectedSiteNum;
    private List<SheetDef> internalList = new List<SheetDef>();
    private Label label1 = new Label();
    private ComboBox comboLabel = new ComboBox();
    private boolean changed = new boolean();
    private Label label2 = new Label();
    private OpenDental.UI.Button butCopy2;
    private OpenDental.UI.Button butImport;
    private OpenDental.UI.Button butExportCustom;
    List<SheetDef> LabelList = new List<SheetDef>();
    /**
    * 
    */
    public FormSheetDefs() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSheetDefs.class);
        this.label1 = new System.Windows.Forms.Label();
        this.comboLabel = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butExportCustom = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butCopy2 = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.grid1 = new OpenDental.UI.ODGrid();
        this.grid2 = new OpenDental.UI.ODGrid();
        this.butNew = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(205, 15);
        this.label1.TabIndex = 16;
        this.label1.Text = "Label assigned to patient button";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboLabel
        //
        this.comboLabel.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboLabel.FormattingEnabled = true;
        this.comboLabel.Location = new System.Drawing.Point(223, 8);
        this.comboLabel.MaxDropDownItems = 20;
        this.comboLabel.Name = "comboLabel";
        this.comboLabel.Size = new System.Drawing.Size(185, 21);
        this.comboLabel.TabIndex = 17;
        this.comboLabel.DropDown += new System.EventHandler(this.comboLabel_DropDown);
        this.comboLabel.SelectionChangeCommitted += new System.EventHandler(this.comboLabel_SelectionChangeCommitted);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(414, 6);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(428, 33);
        this.label2.TabIndex = 18;
        this.label2.Text = "Most other sheet types are assigned simply by creating custom sheets of the same " + "type.  Referral slips are set in the referral edit window of each referral.";
        //
        // butExportCustom
        //
        this.butExportCustom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExportCustom.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExportCustom.setAutosize(true);
        this.butExportCustom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExportCustom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExportCustom.setCornerRadius(4F);
        this.butExportCustom.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExportCustom.Location = new System.Drawing.Point(530, 635);
        this.butExportCustom.Name = "butExportCustom";
        this.butExportCustom.Size = new System.Drawing.Size(80, 24);
        this.butExportCustom.TabIndex = 25;
        this.butExportCustom.Text = "Export";
        this.butExportCustom.Click += new System.EventHandler(this.butExportCustom_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butImport.Location = new System.Drawing.Point(445, 635);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(80, 24);
        this.butImport.TabIndex = 24;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butCopy2
        //
        this.butCopy2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCopy2.setAutosize(true);
        this.butCopy2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy2.setCornerRadius(4F);
        this.butCopy2.Image = Resources.getAdd();
        this.butCopy2.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy2.Location = new System.Drawing.Point(700, 635);
        this.butCopy2.Name = "butCopy2";
        this.butCopy2.Size = new System.Drawing.Size(89, 24);
        this.butCopy2.TabIndex = 19;
        this.butCopy2.Text = "Duplicate";
        this.butCopy2.Click += new System.EventHandler(this.butCopy2_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Image = Resources.getRight();
        this.butCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy.Location = new System.Drawing.Point(333, 635);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(75, 24);
        this.butCopy.TabIndex = 15;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // grid1
        //
        this.grid1.setHScrollVisible(false);
        this.grid1.Location = new System.Drawing.Point(12, 42);
        this.grid1.Name = "grid1";
        this.grid1.setScrollValue(0);
        this.grid1.Size = new System.Drawing.Size(424, 583);
        this.grid1.TabIndex = 14;
        this.grid1.setTitle("Internal");
        this.grid1.setTranslationName(null);
        this.grid1.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid1.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid1_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.grid1.Click += new System.EventHandler(this.grid1_Click);
        //
        // grid2
        //
        this.grid2.setHScrollVisible(false);
        this.grid2.Location = new System.Drawing.Point(445, 42);
        this.grid2.Name = "grid2";
        this.grid2.setScrollValue(0);
        this.grid2.Size = new System.Drawing.Size(424, 583);
        this.grid2.TabIndex = 12;
        this.grid2.setTitle("Custom");
        this.grid2.setTranslationName(null);
        this.grid2.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid2.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid2_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.grid2.Click += new System.EventHandler(this.grid2_Click);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Image = Resources.getAdd();
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(615, 635);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(80, 24);
        this.butNew.TabIndex = 10;
        this.butNew.Text = "New";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(794, 635);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormSheetDefs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(881, 669);
        this.Controls.Add(this.butExportCustom);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.butCopy2);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboLabel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.grid1);
        this.Controls.Add(this.grid2);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSheetDefs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Sheet Defs";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormSheetDefs_FormClosing);
        this.Load += new System.EventHandler(this.FormSheetDefs_Load);
        this.ResumeLayout(false);
    }

    private void formSheetDefs_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup,true))
        {
            butNew.Enabled = false;
            butCopy.Enabled = false;
            butCopy2.Enabled = false;
            grid2.Enabled = false;
        }
         
        fillGrid1();
        fillGrid2();
        comboLabel.Items.Clear();
        comboLabel.Items.Add(Lan.g(this,"Default"));
        comboLabel.SelectedIndex = 0;
        LabelList = new List<SheetDef>();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetType == SheetTypeEnum.LabelPatient)
            {
                LabelList.Add(SheetDefC.getListt()[i].Copy());
            }
             
        }
        for (int i = 0;i < LabelList.Count;i++)
        {
            comboLabel.Items.Add(LabelList[i].Description);
            if (PrefC.getLong(PrefName.LabelPatientDefaultSheetDefNum) == LabelList[i].SheetDefNum)
            {
                comboLabel.SelectedIndex = i + 1;
            }
             
        }
    }

    private void fillGrid1() throws Exception {
        grid1.beginUpdate();
        grid1.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableSheetDef","Description"),170);
        grid1.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSheetDef","Type"),100);
        grid1.getColumns().add(col);
        grid1.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        internalList = SheetsInternal.getAllInternal();
        for (int i = 0;i < internalList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(internalList[i].Description);
            //Enum.GetNames(typeof(SheetInternalType))[i]);
            row.getCells().Add(internalList[i].SheetType.ToString());
            grid1.getRows().add(row);
        }
        grid1.endUpdate();
    }

    private void fillGrid2() throws Exception {
        SheetDefs.refreshCache();
        SheetFieldDefs.refreshCache();
        grid2.beginUpdate();
        grid2.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableSheetDef","Description"),170);
        grid2.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSheetDef","Type"),100);
        grid2.getColumns().add(col);
        grid2.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(SheetDefC.getListt()[i].Description);
            row.getCells().Add(SheetDefC.getListt()[i].SheetType.ToString());
            grid2.getRows().add(row);
        }
        grid2.endUpdate();
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        //This button is not enabled unless user has appropriate permission for setup.
        //Not allowed to change sheettype once a sheet is created, so we need to let user pick.
        FormSheetDef FormS = new FormSheetDef();
        FormS.IsInitial = true;
        FormS.IsReadOnly = false;
        SheetDef sheetdef = new SheetDef();
        sheetdef.FontName = "Microsoft Sans Serif";
        sheetdef.FontSize = 9;
        sheetdef.Height = 1100;
        sheetdef.Width = 850;
        FormS.SheetDefCur = sheetdef;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //what about parameters?
        sheetdef.SheetFieldDefs = new List<SheetFieldDef>();
        sheetdef.setIsNew(true);
        FormSheetDefEdit FormSD = new FormSheetDefEdit(sheetdef);
        FormSD.ShowDialog();
        //It will be saved to db inside this form.
        fillGrid2();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetDefNum == sheetdef.SheetDefNum)
            {
                grid2.setSelected(i,true);
            }
             
        }
        changed = true;
    }

    private void butCopy2_Click(Object sender, EventArgs e) throws Exception {
        //This button is not enabled unless user has appropriate permission for setup.
        if (grid2.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a sheet from the list above first.");
            return ;
        }
         
        SheetDef sheetdef = SheetDefC.getListt()[grid2.getSelectedIndex()].Copy();
        sheetdef.Description = sheetdef.Description + "2";
        SheetDefs.getFieldsAndParameters(sheetdef);
        sheetdef.setIsNew(true);
        SheetDefs.insertOrUpdate(sheetdef);
        fillGrid2();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetDefNum == sheetdef.SheetDefNum)
            {
                grid2.setSelected(i,true);
            }
             
        }
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        if (grid1.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an internal sheet from the list above first.");
            return ;
        }
         
        SheetDef sheetdef = internalList[grid1.getSelectedIndex()].Copy();
        sheetdef.setIsNew(true);
        SheetDefs.insertOrUpdate(sheetdef);
        if (sheetdef.SheetType == SheetTypeEnum.MedicalHistory && (StringSupport.equals(sheetdef.Description, "Medical History New Patient") || StringSupport.equals(sheetdef.Description, "Medical History Update")))
        {
            MsgBox.show(this,"This is just a template, it may contain allergies and problems that do not exist in your setup.");
        }
         
        grid1.setSelected(false);
        fillGrid2();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetDefNum == sheetdef.SheetDefNum)
            {
                grid2.setSelected(i,true);
            }
             
        }
    }

    private void grid1_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormSheetDefEdit FormS = new FormSheetDefEdit(internalList[e.getRow()]);
        FormS.IsInternal = true;
        FormS.ShowDialog();
    }

    private void grid1_Click(Object sender, EventArgs e) throws Exception {
        if (grid1.getSelectedIndex() > -1)
        {
            grid2.setSelected(false);
        }
         
    }

    private void grid2_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SheetDef sheetdef = SheetDefC.getListt()[e.getRow()];
        SheetDefs.getFieldsAndParameters(sheetdef);
        FormSheetDefEdit FormS = new FormSheetDefEdit(sheetdef);
        FormS.ShowDialog();
        fillGrid2();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetDefNum == sheetdef.SheetDefNum)
            {
                grid2.setSelected(i,true);
            }
             
        }
        changed = true;
    }

    private void grid2_Click(Object sender, EventArgs e) throws Exception {
        if (grid2.getSelectedIndex() > -1)
        {
            grid1.setSelected(false);
        }
         
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        OpenFileDialog openDlg = new OpenFileDialog();
        String initDir = PrefC.getString(PrefName.ExportPath);
        if (Directory.Exists(initDir))
        {
            openDlg.InitialDirectory = initDir;
        }
         
        if (openDlg.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        try
        {
            //ImportCustomSheetDef(openDlg.FileName);
            SheetDef sheetdef = new SheetDef();
            XmlSerializer serializer = new XmlSerializer(SheetDef.class);
            if (!StringSupport.equals(openDlg.FileName, ""))
            {
                if (!File.Exists(openDlg.FileName))
                {
                    throw new ApplicationException(Lan.g("FormSheetDefs","File does not exist."));
                }
                 
                try
                {
                    TextReader reader = new StreamReader(openDlg.FileName);
                    try
                    {
                        {
                            sheetdef = (SheetDef)serializer.Deserialize(reader);
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
                    throw new ApplicationException(Lan.g("FormSheetDefs","Invalid file format"));
                }
            
            }
             
            sheetdef.setIsNew(true);
            SheetDefs.insertOrUpdate(sheetdef);
            fillGrid2();
            for (int i = 0;i < SheetDefC.getListt().Count;i++)
            {
                if (SheetDefC.getListt()[i].SheetDefNum == sheetdef.SheetDefNum)
                {
                    grid2.setSelected(i,true);
                }
                 
            }
        }
        catch (ApplicationException ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            fillGrid2();
            return ;
        }

        Cursor = Cursors.Default;
        MsgBox.show(this,"Imported.");
    }

    private void butExportCustom_Click(Object sender, EventArgs e) throws Exception {
        if (grid2.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a sheet from the list above first.");
            return ;
        }
         
        SheetDef sheetdef = SheetDefs.GetSheetDef(SheetDefC.getListt()[grid2.getSelectedIndex()].SheetDefNum);
        SaveFileDialog saveDlg = new SaveFileDialog();
        String filename = "SheetDefCustom.xml";
        saveDlg.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        saveDlg.FileName = filename;
        if (saveDlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        XmlSerializer serializer = new XmlSerializer(SheetDef.class);
        TextWriter writer = new StreamWriter(saveDlg.FileName);
        try
        {
            {
                serializer.Serialize(writer, sheetdef);
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        MsgBox.show(this,"Exported");
    }

    private void comboLabel_DropDown(Object sender, EventArgs e) throws Exception {
        comboLabel.Items.Clear();
        comboLabel.Items.Add(Lan.g(this,"Default"));
        comboLabel.SelectedIndex = 0;
        LabelList = new List<SheetDef>();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            if (SheetDefC.getListt()[i].SheetType == SheetTypeEnum.LabelPatient)
            {
                LabelList.Add(SheetDefC.getListt()[i].Copy());
            }
             
        }
        for (int i = 0;i < LabelList.Count;i++)
        {
            comboLabel.Items.Add(LabelList[i].Description);
            if (PrefC.getLong(PrefName.LabelPatientDefaultSheetDefNum) == LabelList[i].SheetDefNum)
            {
                comboLabel.SelectedIndex = i + 1;
            }
             
        }
    }

    private void comboLabel_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboLabel.SelectedIndex == 0)
        {
            Prefs.UpdateLong(PrefName.LabelPatientDefaultSheetDefNum, 0);
        }
        else
        {
            Prefs.UpdateLong(PrefName.LabelPatientDefaultSheetDefNum, LabelList[comboLabel.SelectedIndex - 1].SheetDefNum);
        } 
        DataValid.setInvalid(InvalidType.Prefs);
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formSheetDefs_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Sheets);
        }
         
    }

}


