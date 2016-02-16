//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPatientSelect;
import OpenDental.FormScreenEdit;
import OpenDental.FormScreenPatEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.ScreenGroup;
import OpenDental.ScreenGroups;
import OpenDental.Screens;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Counties;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.ScreenPat;
import OpenDentBusiness.ScreenPats;
import OpenDentBusiness.SiteC;
import OpenDentBusiness.YN;

/**
* Summary description for FormBasicTemplate.
*/
public class FormScreenGroupEdit  extends System.Windows.Forms.Form 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelProv = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProv = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPlaceService = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ComboBox comboCounty = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelScreener = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textScreenDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProvName = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.ComboBox comboGradeSchool = new System.Windows.Forms.ComboBox();
    public ScreenGroup ScreenGroupCur = new ScreenGroup();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDentBusiness.Screen[] ScreenList = new OpenDentBusiness.Screen[]();
    private List<ScreenPat> ListScreenPats = new List<ScreenPat>();
    private List<Patient> ListPats = new List<Patient>();
    /**
    * 
    */
    public FormScreenGroupEdit() throws Exception {
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
        this.label14 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.labelProv = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textScreenDate = new System.Windows.Forms.TextBox();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.comboPlaceService = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.comboCounty = new System.Windows.Forms.ComboBox();
        this.comboGradeSchool = new System.Windows.Forms.ComboBox();
        this.textProvName = new System.Windows.Forms.TextBox();
        this.labelScreener = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(40, 116);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(44, 16);
        this.label14.TabIndex = 12;
        this.label14.Text = "School";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(31, 95);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(52, 15);
        this.label13.TabIndex = 11;
        this.label13.Text = "County";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelProv
        //
        this.labelProv.Location = new System.Drawing.Point(32, 74);
        this.labelProv.Name = "labelProv";
        this.labelProv.Size = new System.Drawing.Size(52, 16);
        this.labelProv.TabIndex = 50;
        this.labelProv.Text = "Or Prov";
        this.labelProv.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(61, 17);
        this.label1.TabIndex = 51;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textScreenDate
        //
        this.textScreenDate.Location = new System.Drawing.Point(83, 12);
        this.textScreenDate.Name = "textScreenDate";
        this.textScreenDate.Size = new System.Drawing.Size(64, 20);
        this.textScreenDate.TabIndex = 0;
        this.textScreenDate.Validating += new System.ComponentModel.CancelEventHandler(this.textScreenDate_Validating);
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(83, 32);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(173, 20);
        this.textDescription.TabIndex = 1;
        this.textDescription.TextChanged += new System.EventHandler(this.textProvName_TextChanged);
        //
        // comboProv
        //
        this.comboProv.BackColor = System.Drawing.SystemColors.Window;
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(83, 72);
        this.comboProv.MaxDropDownItems = 25;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(173, 21);
        this.comboProv.TabIndex = 2;
        this.comboProv.SelectedIndexChanged += new System.EventHandler(this.comboProv_SelectedIndexChanged);
        this.comboProv.SelectionChangeCommitted += new System.EventHandler(this.comboProv_SelectionChangeCommitted);
        this.comboProv.KeyDown += new System.Windows.Forms.KeyEventHandler(this.comboProv_KeyDown);
        //
        // comboPlaceService
        //
        this.comboPlaceService.BackColor = System.Drawing.SystemColors.Window;
        this.comboPlaceService.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPlaceService.Location = new System.Drawing.Point(83, 135);
        this.comboPlaceService.MaxDropDownItems = 25;
        this.comboPlaceService.Name = "comboPlaceService";
        this.comboPlaceService.Size = new System.Drawing.Size(173, 21);
        this.comboPlaceService.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(31, 136);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(52, 17);
        this.label2.TabIndex = 119;
        this.label2.Text = "Location";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(-20, 33);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(103, 16);
        this.label3.TabIndex = 128;
        this.label3.Text = "Descript";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(632, 583);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(70, 24);
        this.butOK.TabIndex = 24;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // comboCounty
        //
        this.comboCounty.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCounty.Location = new System.Drawing.Point(83, 93);
        this.comboCounty.Name = "comboCounty";
        this.comboCounty.Size = new System.Drawing.Size(173, 21);
        this.comboCounty.TabIndex = 4;
        this.comboCounty.KeyDown += new System.Windows.Forms.KeyEventHandler(this.comboCounty_KeyDown);
        //
        // comboGradeSchool
        //
        this.comboGradeSchool.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboGradeSchool.Location = new System.Drawing.Point(83, 114);
        this.comboGradeSchool.Name = "comboGradeSchool";
        this.comboGradeSchool.Size = new System.Drawing.Size(173, 21);
        this.comboGradeSchool.TabIndex = 140;
        this.comboGradeSchool.KeyDown += new System.Windows.Forms.KeyEventHandler(this.comboGradeSchool_KeyDown);
        //
        // textProvName
        //
        this.textProvName.Location = new System.Drawing.Point(83, 52);
        this.textProvName.Name = "textProvName";
        this.textProvName.Size = new System.Drawing.Size(173, 20);
        this.textProvName.TabIndex = 141;
        this.textProvName.TextChanged += new System.EventHandler(this.textProvName_TextChanged);
        this.textProvName.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textProvName_KeyUp);
        //
        // labelScreener
        //
        this.labelScreener.Location = new System.Drawing.Point(33, 54);
        this.labelScreener.Name = "labelScreener";
        this.labelScreener.Size = new System.Drawing.Size(50, 16);
        this.labelScreener.TabIndex = 142;
        this.labelScreener.Text = "Screener";
        this.labelScreener.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butAdd.Location = new System.Drawing.Point(307, 613);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(78, 24);
        this.butAdd.TabIndex = 146;
        this.butAdd.Text = "Add Item";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(632, 613);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(70, 24);
        this.butCancel.TabIndex = 24;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 613);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(70, 24);
        this.butDelete.TabIndex = 24;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(2, 162);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(710, 415);
        this.gridMain.TabIndex = 147;
        this.gridMain.setTitle("Screenings");
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
        // FormScreenGroupEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(714, 641);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.textProvName);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.labelProv);
        this.Controls.Add(this.labelScreener);
        this.Controls.Add(this.comboGradeSchool);
        this.Controls.Add(this.comboCounty);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.textScreenDate);
        this.Controls.Add(this.comboPlaceService);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label3);
        this.Name = "FormScreenGroupEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Edit Screening Group";
        this.Load += new System.EventHandler(this.FormScreenGroup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formScreenGroup_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            ScreenGroups.Insert(ScreenGroupCur);
        }
         
        if (PrefC.getBool(PrefName.PublicHealthScreeningUsePat))
        {
            labelScreener.Visible = false;
            textProvName.Visible = false;
            labelProv.Visible = false;
            comboProv.Visible = false;
            ScreenList = new OpenDentBusiness.Screen[0];
            fillGridScreenPat();
        }
        else
        {
            ListPats = new List<Patient>();
            fillGrid();
        } 
        if (ScreenList.Length > 0)
        {
            OpenDentBusiness.Screen ScreenCur = ScreenList[0];
            ScreenGroupCur.SGDate = ScreenCur.ScreenDate;
            ScreenGroupCur.ProvName = ScreenCur.ProvName;
            ScreenGroupCur.ProvNum = ScreenCur.ProvNum;
            ScreenGroupCur.County = ScreenCur.County;
            ScreenGroupCur.GradeSchool = ScreenCur.GradeSchool;
            ScreenGroupCur.PlaceService = ScreenCur.PlaceService;
        }
         
        textScreenDate.Text = ScreenGroupCur.SGDate.ToShortDateString();
        textDescription.Text = ScreenGroupCur.Description;
        textProvName.Text = ScreenGroupCur.ProvName;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            //has to be filled before provnum
            comboProv.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ScreenGroupCur.ProvNum == ProviderC.getListShort()[i].ProvNum)
            {
                comboProv.SelectedIndex = i;
            }
             
        }
        String[] CountiesListNames = Counties.getListNames();
        comboCounty.Items.AddRange(CountiesListNames);
        if (ScreenGroupCur.County == null)
            ScreenGroupCur.County = "";
         
        //prevents the next line from crashing
        comboCounty.SelectedIndex = comboCounty.Items.IndexOf(ScreenGroupCur.County);
        for (int i = 0;i < SiteC.getList().Length;i++)
        {
            //"" etc OK
            comboGradeSchool.Items.Add(SiteC.getList()[i].Description);
        }
        if (ScreenGroupCur.GradeSchool == null)
            ScreenGroupCur.GradeSchool = "";
         
        //prevents the next line from crashing
        comboGradeSchool.SelectedIndex = comboGradeSchool.Items.IndexOf(ScreenGroupCur.GradeSchool);
        //"" etc OK
        comboPlaceService.Items.AddRange(Enum.GetNames(PlaceOfService.class));
        comboPlaceService.SelectedIndex = ((Enum)ScreenGroupCur.PlaceService).ordinal();
    }

    private void fillGridScreenPat() throws Exception {
        ListScreenPats = ScreenPats.GetForScreenGroup(ScreenGroupCur.ScreenGroupNum);
        ListPats = Patients.GetPatsForScreenGroup(ScreenGroupCur.ScreenGroupNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"PatNum"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Name"),300);
        gridMain.getColumns().add(col);
        //todo: birthdate
        col = new ODGridColumn(Lan.g(this,"Age"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Race"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Gender"),80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListPats.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListPats[i].PatNum.ToString());
            row.getCells().Add(ListPats[i].GetNameLF());
            row.getCells().Add(ListPats[i].Age.ToString());
            row.getCells().Add(ListPats[i].Race.ToString());
            row.getCells().Add(ListPats[i].Gender.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillGrid() throws Exception {
        ScreenList = Screens.Refresh(ScreenGroupCur.ScreenGroupNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"#"),30);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Grade"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Age"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Race"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Sex"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Urgency"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Caries"),45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"ECC"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"CarExp"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"ExSeal"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"NeedSeal"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"NoTeeth"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Comments"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ScreenList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ScreenList[i].ScreenGroupOrder.ToString());
            row.getCells().Add(ScreenList[i].GradeLevel.ToString());
            row.getCells().Add(ScreenList[i].Age.ToString());
            row.getCells().Add(ScreenList[i].Race.ToString());
            row.getCells().Add(ScreenList[i].Gender.ToString());
            row.getCells().Add(ScreenList[i].Urgency.ToString());
            row.getCells().Add(getX(ScreenList[i].HasCaries));
            row.getCells().Add(getX(ScreenList[i].EarlyChildCaries));
            row.getCells().Add(getX(ScreenList[i].CariesExperience));
            row.getCells().Add(getX(ScreenList[i].ExistingSealants));
            row.getCells().Add(getX(ScreenList[i].NeedsSealants));
            row.getCells().Add(getX(ScreenList[i].MissingAllTeeth));
            row.getCells().Add(ScreenList[i].Comments);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private String getX(YN ynValue) throws Exception {
        if (ynValue == YN.Yes)
            return "X";
         
        return "";
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.PublicHealthScreeningUsePat))
        {
            FormScreenPatEdit FormSPE = new FormScreenPatEdit();
            FormSPE.ShowDialog();
            if (FormSPE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            fillGridScreenPat();
        }
        else
        {
            FormScreenEdit FormSE = new FormScreenEdit();
            FormSE.ScreenCur = ScreenList[gridMain.getSelectedIndices()[0]];
            FormSE.ScreenGroupCur = ScreenGroupCur;
            FormSE.ShowDialog();
            if (FormSE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            fillGrid();
        } 
    }

    private void textScreenDate_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        try
        {
            DateTime.Parse(textScreenDate.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Date invalid");
            e.Cancel = true;
        }
    
    }

    private void textProvName_TextChanged(Object sender, System.EventArgs e) throws Exception {
    }

    /*if(textProvName.Text!=""){    //if a prov name was entered
    				comboProv.SelectedIndex=-1;//then set the provnum to none.
    			}*/
    private void textProvName_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        comboProv.SelectedIndex = -1;
    }

    //set the provnum to none.
    private void comboProv_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (comboProv.SelectedIndex != -1)
        {
            //if a prov was selected
            //set the provname accordingly
            textProvName.Text = ProviderC.getListShort()[comboProv.SelectedIndex].LName + ", " + ProviderC.getListShort()[comboProv.SelectedIndex].FName;
        }
         
    }

    private void comboProv_SelectionChangeCommitted(Object sender, System.EventArgs e) throws Exception {
    }

    private void comboProv_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Back || e.KeyCode == Keys.Delete)
        {
            comboProv.SelectedIndex = -1;
        }
         
    }

    private void comboCounty_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Back || e.KeyCode == Keys.Delete)
        {
            comboCounty.SelectedIndex = -1;
        }
         
    }

    private void comboGradeSchool_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Back || e.KeyCode == Keys.Delete)
        {
            comboGradeSchool.SelectedIndex = -1;
        }
         
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.PublicHealthScreeningUsePat))
        {
            /*
            				FormScreenPatEdit FormSPE=new FormScreenPatEdit();
            				FormSPE.IsNew=true;
            				while(true) {
            					FormSPE.ScreenPatCur=new ScreenPat();
            					FormSPE.ScreenPatCur.ScreenGroupNum=ScreenGroupCur.ScreenGroupNum;
            					FormSPE.ScreenPatCur.SheetNum=PrefC.GetLong(PrefName.PublicHealthScreeningSheet);
            					FormSPE.ScreenGroupCur=ScreenGroupCur;
            					FormSPE.ScreenGroupCur.Description=textDescription.Text;
            					FormSPE.ShowDialog();
            					if(FormSPE.DialogResult!=DialogResult.OK) {
            						return;
            					}
            					FillGridScreenPat();
            				}
            				*/
            FormScreenPatEdit FormSPE = new FormScreenPatEdit();
            while (true)
            {
                FormPatientSelect FormPS = new FormPatientSelect();
                FormPS.ShowDialog();
                if (FormPS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                ScreenPat screenPat = new ScreenPat();
                screenPat.ScreenGroupNum = ScreenGroupCur.ScreenGroupNum;
                screenPat.SheetNum = PrefC.getLong(PrefName.PublicHealthScreeningSheet);
                screenPat.PatNum = FormPS.SelectedPatNum;
                ScreenPats.insert(screenPat);
                if (FormPS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                fillGridScreenPat();
            }
        }
        else
        {
            FormScreenEdit FormSE = new FormScreenEdit();
            FormSE.ScreenGroupCur = ScreenGroupCur;
            FormSE.IsNew = true;
            if (ScreenList.Length == 0)
            {
                FormSE.ScreenCur = new OpenDentBusiness.Screen();
                FormSE.ScreenCur.ScreenGroupOrder = 1;
            }
            else
            {
                FormSE.ScreenCur = ScreenList[ScreenList.Length - 1];
                //'remembers' the last entry
                FormSE.ScreenCur.ScreenGroupOrder = FormSE.ScreenCur.ScreenGroupOrder + 1;
            } 
            while (true)
            {
                //increments for next
                FormSE.ShowDialog();
                if (FormSE.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                FormSE.ScreenCur.ScreenGroupOrder++;
                fillGrid();
            }
        } 
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.PublicHealthScreeningUsePat))
        {
        }
        else
        {
            //todo: Bring up the attached sheet for this patient or create a new sheet for this patient.
            //FormScreenPatEdit FormSPE=new FormScreenPatEdit();
            //FormSPE.ScreenGroupCur=ScreenGroupCur;
            //FormSPE.ScreenGroupCur.Description=textDescription.Text;
            //FormSPE.IsNew=false;
            //FormSPE.ScreenPatCur=ListScreenPats[e.Row];
            //FormSPE.ShowDialog();
            //FillGrid();
            FormScreenEdit FormSE = new FormScreenEdit();
            FormSE.ScreenGroupCur = ScreenGroupCur;
            FormSE.IsNew = false;
            FormSE.ScreenCur = ScreenList[e.getRow()];
            FormSE.ShowDialog();
            fillGrid();
        } 
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (ScreenList.Length > 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Are you sure you want to delete this screening group? All screenings in this group will be deleted?"))
            {
                return ;
            }
             
        }
         
        for (int i = 0;i < ScreenList.Length;i++)
        {
            Screens.Delete(ScreenList[i]);
        }
        ScreenGroups.Delete(ScreenGroupCur);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (ScreenList.Length == 0)
        {
            if (MessageBox.Show("Since you have no items in the list, the screener and location information cannot be saved. Continue?", "", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
            {
                return ;
            }
             
        }
         
        ScreenGroupCur.SGDate = PIn.Date(textScreenDate.Text);
        ScreenGroupCur.Description = textDescription.Text;
        ScreenGroupCur.ProvName = textProvName.Text;
        ScreenGroupCur.ProvNum = comboProv.SelectedIndex + 1;
        //this works for -1 also.
        if (comboCounty.SelectedIndex == -1)
            ScreenGroupCur.County = "";
        else
            ScreenGroupCur.County = comboCounty.SelectedItem.ToString(); 
        if (comboGradeSchool.SelectedIndex == -1)
            ScreenGroupCur.GradeSchool = "";
        else
            ScreenGroupCur.GradeSchool = comboGradeSchool.SelectedItem.ToString(); 
        ScreenGroupCur.PlaceService = (PlaceOfService)comboPlaceService.SelectedIndex;
        ScreenGroups.Update(ScreenGroupCur);
        Screens.UpdateForGroup(ScreenGroupCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


