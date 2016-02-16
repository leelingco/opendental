//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:18 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrFamily;
import OpenDental.FormInsPlan;
import OpenDental.FormInsSelectSubscr;
import OpenDental.FormPatFieldCheckEdit;
import OpenDental.FormPatFieldCurrencyEdit;
import OpenDental.FormPatFieldDateEdit;
import OpenDental.FormPatFieldEdit;
import OpenDental.FormPatFieldPickEdit;
import OpenDental.FormPatientEdit;
import OpenDental.FormPatientSelect;
import OpenDental.FormPayorTypes;
import OpenDental.FormPlansForFamily;
import OpenDental.FormRecallsPat;
import OpenDental.FormReference;
import OpenDental.FormReferenceEntryEdit;
import OpenDental.FormReferralsPatient;
import OpenDental.FormSubscriberSelect;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.ProgramL;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CustRefEntries;
import OpenDentBusiness.CustRefEntry;
import OpenDentBusiness.CustReference;
import OpenDentBusiness.CustReferences;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.Employers;
import OpenDentBusiness.Family;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.Guardian;
import OpenDentBusiness.Guardians;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Interval;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PatFields;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PayorTypes;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PaySplits;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.Popups;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Resellers;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.TerminalActives;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.YN;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrFamily  extends System.Windows.Forms.UserControl 
{
    private System.Windows.Forms.ImageList imageListToolBar = new System.Windows.Forms.ImageList();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private OpenDental.UI.ODToolBar ToolBarMain;
    /**
    * All recalls for this entire family.
    */
    private List<Recall> RecallList = new List<Recall>();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    private Patient PatCur;
    private Family FamCur;
    private OpenDental.UI.PictureBox picturePat;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private OpenDental.UI.ODGrid gridIns;
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private OpenDental.UI.ODGrid gridPat;
    private ContextMenu menuInsurance = new ContextMenu();
    private MenuItem menuPlansForFam = new MenuItem();
    private List<Benefit> BenefitList = new List<Benefit>();
    private OpenDental.UI.ODGrid gridFamily;
    private OpenDental.UI.ODGrid gridRecall;
    private PatField[] PatFieldList = new PatField[]();
    private boolean InitializedOnStartup = new boolean();
    private OpenDental.UI.ODGrid gridSuperFam;
    private List<InsSub> SubList = new List<InsSub>();
    private List<Patient> SuperFamilyGuarantors = new List<Patient>();
    private List<Patient> SuperFamilyMembers = new List<Patient>();
    /**
    * 
    */
    public ContrFamily() throws Exception {
        Logger.openlog.log("Initializing family module...",Severity.INFO);
        initializeComponent();
    }

    // This call is required by the Windows.Forms Form Designer.
    /**
    * 
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

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrFamily.class);
        this.imageListToolBar = new System.Windows.Forms.ImageList(this.components);
        this.menuInsurance = new System.Windows.Forms.ContextMenu();
        this.menuPlansForFam = new System.Windows.Forms.MenuItem();
        this.picturePat = new OpenDental.UI.PictureBox();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.gridSuperFam = new OpenDental.UI.ODGrid();
        this.gridRecall = new OpenDental.UI.ODGrid();
        this.gridFamily = new OpenDental.UI.ODGrid();
        this.gridPat = new OpenDental.UI.ODGrid();
        this.gridIns = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // imageListToolBar
        //
        this.imageListToolBar.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListToolBar.ImageStream")));
        this.imageListToolBar.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListToolBar.Images.SetKeyName(0, "");
        this.imageListToolBar.Images.SetKeyName(1, "");
        this.imageListToolBar.Images.SetKeyName(2, "");
        this.imageListToolBar.Images.SetKeyName(3, "");
        this.imageListToolBar.Images.SetKeyName(4, "");
        this.imageListToolBar.Images.SetKeyName(5, "");
        this.imageListToolBar.Images.SetKeyName(6, "Umbrella.gif");
        //
        // menuInsurance
        //
        this.menuInsurance.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuPlansForFam });
        //
        // menuPlansForFam
        //
        this.menuPlansForFam.Index = 0;
        this.menuPlansForFam.Text = "Plans for Family";
        this.menuPlansForFam.Click += new System.EventHandler(this.menuPlansForFam_Click);
        //
        // picturePat
        //
        this.picturePat.Location = new System.Drawing.Point(1, 27);
        this.picturePat.Name = "picturePat";
        this.picturePat.Size = new System.Drawing.Size(100, 100);
        this.picturePat.TabIndex = 28;
        this.picturePat.Text = "picturePat";
        this.picturePat.setTextNullImage("Patient Picture Unavailable");
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListToolBar);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(939, 25);
        this.ToolBarMain.TabIndex = 19;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridSuperFam
        //
        this.gridSuperFam.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridSuperFam.setHScrollVisible(false);
        this.gridSuperFam.Location = new System.Drawing.Point(254, 129);
        this.gridSuperFam.Name = "gridSuperFam";
        this.gridSuperFam.setScrollValue(0);
        this.gridSuperFam.Size = new System.Drawing.Size(329, 579);
        this.gridSuperFam.TabIndex = 33;
        this.gridSuperFam.setTitle("Super Family");
        this.gridSuperFam.setTranslationName("TableSuper");
        this.gridSuperFam.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridSuperFam.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridSuperFam_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridSuperFam.CellClick = __MultiODGridClickEventHandler.combine(this.gridSuperFam.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridSuperFam_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridRecall
        //
        this.gridRecall.setHScrollVisible(false);
        this.gridRecall.Location = new System.Drawing.Point(585, 27);
        this.gridRecall.Name = "gridRecall";
        this.gridRecall.setScrollValue(0);
        this.gridRecall.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridRecall.Size = new System.Drawing.Size(525, 100);
        this.gridRecall.TabIndex = 32;
        this.gridRecall.setTitle("Recall");
        this.gridRecall.setTranslationName("TableRecall");
        this.gridRecall.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridRecall.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridRecall_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridRecall.DoubleClick += new System.EventHandler(this.gridRecall_DoubleClick);
        //
        // gridFamily
        //
        this.gridFamily.setHScrollVisible(false);
        this.gridFamily.Location = new System.Drawing.Point(103, 27);
        this.gridFamily.Name = "gridFamily";
        this.gridFamily.setScrollValue(0);
        this.gridFamily.setSelectedRowColor(System.Drawing.Color.DarkSalmon);
        this.gridFamily.Size = new System.Drawing.Size(480, 100);
        this.gridFamily.TabIndex = 31;
        this.gridFamily.setTitle("Family Members");
        this.gridFamily.setTranslationName("TablePatient");
        this.gridFamily.CellClick = __MultiODGridClickEventHandler.combine(this.gridFamily.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridFamily_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridPat
        //
        this.gridPat.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridPat.setHScrollVisible(false);
        this.gridPat.Location = new System.Drawing.Point(0, 129);
        this.gridPat.Name = "gridPat";
        this.gridPat.setScrollValue(0);
        this.gridPat.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridPat.Size = new System.Drawing.Size(252, 579);
        this.gridPat.TabIndex = 30;
        this.gridPat.setTitle("Patient Information");
        this.gridPat.setTranslationName("TablePatient");
        this.gridPat.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPat.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPat_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridIns
        //
        this.gridIns.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridIns.setHScrollVisible(true);
        this.gridIns.Location = new System.Drawing.Point(254, 129);
        this.gridIns.Name = "gridIns";
        this.gridIns.setScrollValue(0);
        this.gridIns.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridIns.Size = new System.Drawing.Size(685, 579);
        this.gridIns.TabIndex = 29;
        this.gridIns.setTitle("Insurance Plans");
        this.gridIns.setTranslationName("TableCoverage");
        this.gridIns.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridIns.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridIns_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // ContrFamily
        //
        this.Controls.Add(this.gridSuperFam);
        this.Controls.Add(this.gridRecall);
        this.Controls.Add(this.gridFamily);
        this.Controls.Add(this.gridPat);
        this.Controls.Add(this.gridIns);
        this.Controls.Add(this.picturePat);
        this.Controls.Add(this.ToolBarMain);
        this.Name = "ContrFamily";
        this.Size = new System.Drawing.Size(939, 708);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrFamily_Layout);
        this.Resize += new System.EventHandler(this.ContrFamily_Resize);
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        refreshModuleData(patNum);
        refreshModuleScreen();
        Plugins.hookAddCode(this,"ContrFamily.ModuleSelected_end",patNum);
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        FamCur = null;
        PlanList = null;
        Plugins.hookAddCode(this,"ContrFamily.ModuleUnselected_end");
    }

    private void refreshModuleData(long patNum) throws Exception {
        if (patNum == 0)
        {
            PatCur = null;
            FamCur = null;
            PatPlanList = new List<PatPlan>();
            return ;
        }
         
        FamCur = Patients.getFamily(patNum);
        PatCur = FamCur.getPatient(patNum);
        SubList = InsSubs.refreshForFam(FamCur);
        PlanList = InsPlans.refreshForSubList(SubList);
        PatPlanList = PatPlans.refresh(patNum);
        BenefitList = Benefits.refresh(PatPlanList,SubList);
        RecallList = Recalls.getList(CodeBase.MiscUtils.arrayToList(FamCur.ListPats));
        PatFieldList = PatFields.refresh(patNum);
        SuperFamilyMembers = Patients.getBySuperFamily(PatCur.SuperFamily);
        SuperFamilyGuarantors = Patients.getSuperFamilyGuarantors(PatCur.SuperFamily);
    }

    private void refreshModuleScreen() throws Exception {
        //ParentForm.Text=Patients.GetMainTitle(PatCur);
        if (PatCur != null)
        {
            //if there is a patient
            //ToolBarMain.Buttons["Recall"].Enabled=true;
            ToolBarMain.getButtons().get___idx("Add").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Delete").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Guarantor").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Move").setEnabled(true);
            if (!PrefC.getBool(PrefName.EasyHideInsurance))
            {
                ToolBarMain.getButtons().get___idx("Ins").setEnabled(true);
            }
             
            if (ToolBarMain.getButtons().get___idx("AddSuper") == null)
            {
                //because the toolbar only refreshes on restart. //PrefC.GetBool(PrefName.ShowFeatureSuperfamilies)){
                gridSuperFam.Visible = false;
            }
            else
            {
                ToolBarMain.getButtons().get___idx("AddSuper").setEnabled(true);
                ToolBarMain.getButtons().get___idx("RemoveSuper").setEnabled(true);
                ToolBarMain.getButtons().get___idx("DisbandSuper").setEnabled(true);
                if (PatCur.SuperFamily != 0)
                {
                    //show Super Family Grid
                    gridSuperFam.Visible = true;
                    gridIns.Location = new Point(gridSuperFam.Right + 2, gridIns.Top);
                    // new Point(585,129);//gridIns (X,Y) normally=(254,129) reduced=(585,129)
                    gridIns.Width = this.Width - gridIns.Left;
                }
                else
                {
                    //585;;
                    //Hide super family grid
                    gridSuperFam.Visible = false;
                    gridIns.Location = gridSuperFam.Location;
                    //254,129);//gridIns (X,Y) normally=(254,129) reduced=(585,129)
                    gridIns.Width = this.Width - gridIns.Left;
                } 
            } 
            //254;
            ToolBarMain.Invalidate();
        }
        else
        {
            //Hide super family grid, safe to run even if grid is already hidden.
            gridSuperFam.Visible = false;
            gridIns.Location = gridSuperFam.Location;
            //254,129);//gridIns (X,Y) normally=(254,129) reduced=(585,129)
            gridIns.Width = this.Width - gridIns.Left;
            //254;
            //ToolBarMain.Buttons["Recall"].Enabled=false;
            ToolBarMain.getButtons().get___idx("Add").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Delete").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Guarantor").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Move").setEnabled(false);
            if (ToolBarMain.getButtons().get___idx("AddSuper") != null)
            {
                //because the toolbar only refreshes on restart.
                ToolBarMain.getButtons().get___idx("AddSuper").setEnabled(false);
                ToolBarMain.getButtons().get___idx("RemoveSuper").setEnabled(false);
                ToolBarMain.getButtons().get___idx("DisbandSuper").setEnabled(false);
            }
             
            if (!PrefC.getBool(PrefName.EasyHideInsurance))
            {
                ToolBarMain.getButtons().get___idx("Ins").setEnabled(false);
            }
             
            ToolBarMain.Invalidate();
        } 
        //Patients.Cur=new Patient();
        if (PrefC.getBool(PrefName.EasyHideInsurance))
        {
            gridIns.Visible = false;
        }
        else
        {
            gridIns.Visible = true;
        } 
        //Cannot add new patients from OD select patient interface.  Patient must be added from HL7 message.
        if (HL7Defs.isExistingHL7Enabled())
        {
            HL7Def def = HL7Defs.getOneDeepEnabled();
            if (def.ShowDemographics != HL7ShowDemographics.ChangeAndAdd)
            {
                ToolBarMain.getButtons().get___idx("Add").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Delete").setEnabled(false);
            }
             
        }
        else
        {
            if (Programs.usingEcwFullMode())
            {
                ToolBarMain.getButtons().get___idx("Add").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Delete").setEnabled(false);
            }
             
        } 
        fillPatientPicture();
        fillPatientData();
        fillFamilyData();
        fillGridRecall();
        fillInsData();
        fillGridSuperFam();
        Plugins.hookAddCode(this,"ContrFamily.RefreshModuleScreen_end");
    }

    private void fillPatientPicture() throws Exception {
        picturePat.setImage(null);
        picturePat.setTextNullImage(Lan.g(this,"Patient Picture Unavailable"));
        if (PatCur == null || !PrefC.getAtoZfolderUsed())
        {
            return ;
        }
         
        try
        {
            //Do not use patient image when A to Z folders are disabled.
            Bitmap patPict = new Bitmap();
            RefSupport<Bitmap> refVar___0 = new RefSupport<Bitmap>();
            Documents.getPatPict(PatCur.PatNum,ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath()),refVar___0);
            patPict = refVar___0.getValue();
            picturePat.setImage(patPict);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    /**
    * 
    */
    public void initializeOnStartup() throws Exception {
        if (InitializedOnStartup)
        {
            return ;
        }
         
        InitializedOnStartup = true;
        //tbFamily.InstantClasses();
        //cannot use Lan.F(this);
        Lan.C(this, new Control[]{  });
        //butPatEdit,
        //butEditPriCov,
        //butEditPriPlan,
        //butEditSecCov,
        //butEditSecPlan
        layoutToolBar();
    }

    //gridPat.Height=this.ClientRectangle.Bottom-gridPat.Top-2;
    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Recall"),1,"","Recall"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Family Members:"), -1, "", "");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add"), 2, "Add Family Member", "Add"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Delete"), 3, Lan.g(this,"Delete Family Member"), "Delete"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Set Guarantor"), 4, Lan.g(this,"Set as Guarantor"), "Guarantor"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Move"), 5, Lan.g(this,"Move to Another Family"), "Move"));
        if (PrefC.getBool(PrefName.ShowFeatureSuperfamilies))
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Super Family:"), -1, "", "");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
            ToolBarMain.getButtons().add(button);
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add"), -1, "Add selected patient to a super family", "AddSuper"));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Remove"), -1, Lan.g(this,"Remove selected patient, and their family, from super family"), "RemoveSuper"));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Disband"), -1, Lan.g(this,"Disband the current super family by removing all members of the super family."), "DisbandSuper"));
        }
         
        if (!PrefC.getBool(PrefName.EasyHideInsurance))
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add Insurance"), 6, "", "Ins");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            button.setDropDownMenu(menuInsurance);
            ToolBarMain.getButtons().add(button);
        }
         
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.FamilyModule);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        ToolBarMain.Invalidate();
        Plugins.hookAddCode(this,"ContrFamily.LayoutToolBar_end",PatCur);
    }

    private void contrFamily_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
    }

    private void contrFamily_Resize(Object sender, EventArgs e) throws Exception {
    }

    /*if(Height>gridPat.Top){
    				gridPat.Height=Height-gridPat.Top-2;
    				gridIns.Height=Height-gridIns.Top-2;
    			}
    			if(Width>gridIns.Left){
    				gridIns.Width=Width-gridIns.Left-2;
    			}*/
    //private void butOutlook_Click(object sender, System.EventArgs e) {
    /*Process[] procsOutlook = Process.GetProcessesByName("outlook");
    			if(procsOutlook.Length==0){
    				try{
    					Process.Start("Outlook");
    				}
    				catch{}
    			}*/
    //}
    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
            //case "Recall":
            //	ToolButRecall_Click();
            //	break;
            if (__dummyScrutVar0.equals("Add"))
            {
                toolButAdd_Click();
            }
            else if (__dummyScrutVar0.equals("Delete"))
            {
                toolButDelete_Click();
            }
            else if (__dummyScrutVar0.equals("Guarantor"))
            {
                toolButGuarantor_Click();
            }
            else if (__dummyScrutVar0.equals("Move"))
            {
                toolButMove_Click();
            }
            else if (__dummyScrutVar0.equals("Ins"))
            {
                toolButIns_Click();
            }
            else if (__dummyScrutVar0.equals("AddSuper"))
            {
                toolButAddSuper_Click();
            }
            else if (__dummyScrutVar0.equals("RemoveSuper"))
            {
                toolButRemoveSuper_Click();
            }
            else if (__dummyScrutVar0.equals("DisbandSuper"))
            {
                toolButDisbandSuper_Click();
            }
                    
        }
        else if (e.getButton().getTag().GetType() == long.class)
        {
            ProgramL.execute((long)e.getButton().getTag(),PatCur);
        }
          
    }

    /**
    * 
    */
    private void onPatientSelected(Patient pat) throws Exception {
        OpenDental.PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
        if (PatientSelected != null)
        {
            PatientSelected.invoke(this,eArgs);
        }
         
    }

    private void gridPat_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (Plugins.hookMethod(this,"ContrFamily.gridPat_CellDoubleClick",PatCur))
        {
            return ;
        }
         
        if (TerminalActives.patIsInUse(PatCur.PatNum))
        {
            MsgBox.show(this,"Patient is currently entering info at a reception terminal.  Please try again later.");
            return ;
        }
         
        if (gridPat.getRows().get___idx(e.getRow()).getTag() != null)
        {
            if (StringSupport.equals(gridPat.getRows().get___idx(e.getRow()).getTag().ToString(), "Referral"))
            {
                //RefAttach refattach=(RefAttach)gridPat.Rows[e.Row].Tag;
                FormReferralsPatient FormRE = new FormReferralsPatient();
                FormRE.PatNum = PatCur.PatNum;
                FormRE.ShowDialog();
            }
            else if (StringSupport.equals(gridPat.getRows().get___idx(e.getRow()).getTag().ToString(), "References"))
            {
                FormReference FormR = new FormReference();
                FormR.ShowDialog();
                if (FormR.GotoPatNum != 0)
                {
                    Patient pat = Patients.getPat(FormR.GotoPatNum);
                    onPatientSelected(pat);
                    GotoModule.gotoFamily(FormR.GotoPatNum);
                    return ;
                }
                 
                if (FormR.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                for (int i = 0;i < FormR.SelectedCustRefs.Count;i++)
                {
                    CustRefEntry custEntry = new CustRefEntry();
                    custEntry.DateEntry = DateTime.Now;
                    custEntry.PatNumCust = PatCur.PatNum;
                    custEntry.PatNumRef = FormR.SelectedCustRefs[i].PatNum;
                    CustRefEntries.insert(custEntry);
                }
            }
            else if (gridPat.getRows().get___idx(e.getRow()).getTag().GetType() == CustRefEntry.class)
            {
                FormReferenceEntryEdit FormRE = new FormReferenceEntryEdit((CustRefEntry)gridPat.getRows().get___idx(e.getRow()).getTag());
                FormRE.ShowDialog();
            }
            else if (gridPat.getRows().get___idx(e.getRow()).getTag().ToString().Equals("Payor Types"))
            {
                FormPayorTypes FormPT = new FormPayorTypes();
                FormPT.PatCur = PatCur;
                FormPT.ShowDialog();
            }
            else
            {
                //patfield
                String tag = gridPat.getRows().get___idx(e.getRow()).getTag().ToString();
                tag = tag.Substring(8);
                //strips off all but the number: PatField1
                int index = PIn.Int(tag);
                PatField field = PatFields.GetByName(PatFieldDefs.getList()[index].FieldName, PatFieldList);
                if (field == null)
                {
                    field = new PatField();
                    field.PatNum = PatCur.PatNum;
                    field.FieldName = PatFieldDefs.getList()[index].FieldName;
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Text)
                    {
                        FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.PickList)
                    {
                        FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Date)
                    {
                        FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Checkbox)
                    {
                        FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Currency)
                    {
                        FormPatFieldCurrencyEdit FormPF = new FormPatFieldCurrencyEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                }
                else
                {
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Text)
                    {
                        FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.PickList)
                    {
                        FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Date)
                    {
                        FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Checkbox)
                    {
                        FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Currency)
                    {
                        FormPatFieldCurrencyEdit FormPF = new FormPatFieldCurrencyEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                } 
            }    
        }
        else
        {
            String email = PatCur.Email;
            long siteNum = PatCur.SiteNum;
            //
            FormPatientEdit FormP = new FormPatientEdit(PatCur,FamCur);
            FormP.IsNew = false;
            FormP.ShowDialog();
            //there are many things which may have changed that need to trigger refresh:
            //FName, LName, MiddleI, Preferred, SiteNum, or ChartNumber should refresh title bar.
            //Email change should change email but enabled.
            //Instead of checking for each of those:
            /*
            				if(email!=PatCur.Email){//PatCur.EmailChanged){//do it this way later
            					OnPatientSelected(PatCur.PatNum,PatCur.GetNameLF(),PatCur.Email!="",PatCur.ChartNumber);
            				}
            				if(siteNum!=PatCur.SiteNum){
            					OnPatientSelected(PatCur.PatNum,PatCur.GetNameLF(),PatCur.Email!="",PatCur.ChartNumber);
            				}*/
            if (FormP.DialogResult == DialogResult.OK)
            {
                onPatientSelected(PatCur);
            }
             
        } 
        moduleSelected(PatCur.PatNum);
    }

    private void fillPatientData() throws Exception {
        if (PatCur == null)
        {
            gridPat.beginUpdate();
            gridPat.getRows().Clear();
            gridPat.getColumns().Clear();
            gridPat.endUpdate();
            return ;
        }
         
        gridPat.beginUpdate();
        gridPat.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridPat.getColumns().add(col);
        col = new ODGridColumn("",150);
        gridPat.getColumns().add(col);
        gridPat.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.PatientInformation);
        for (int f = 0;f < fields.Count;f++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (StringSupport.equals(fields[f].Description, ""))
            {
                if (StringSupport.equals(fields[f].InternalName, "SS#"))
                {
                    if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                    {
                        //Canadian. en-CA or fr-CA
                        row.getCells().add("SIN");
                    }
                    else if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "GB"))
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        row.getCells().add("SS#");
                    }  
                }
                else if (StringSupport.equals(fields[f].InternalName, "State"))
                {
                    if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                    {
                        //Canadian. en-CA or fr-CA
                        row.getCells().add("Province");
                    }
                    else if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "GB"))
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        row.getCells().add("State");
                    }  
                }
                else if (StringSupport.equals(fields[f].InternalName, "Zip"))
                {
                    if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                    {
                        //Canadian. en-CA or fr-CA
                        row.getCells().add("Postal Code");
                    }
                    else if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "GB"))
                    {
                        row.getCells().add("Postcode");
                    }
                    else
                    {
                        row.getCells().add(Lan.g("TablePatient","Zip"));
                    }  
                }
                else if (StringSupport.equals(fields[f].InternalName, "PatFields"))
                {
                }
                else
                {
                    //don't add a cell
                    row.getCells().Add(fields[f].InternalName);
                }    
            }
            else
            {
                if (StringSupport.equals(fields[f].InternalName, "PatFields"))
                {
                }
                else
                {
                    //don't add a cell
                    row.getCells().Add(fields[f].Description);
                } 
            } 
            InternalName __dummyScrutVar1 = fields[f].InternalName;
            if (__dummyScrutVar1.equals("Last"))
            {
                row.getCells().add(PatCur.LName);
            }
            else if (__dummyScrutVar1.equals("First"))
            {
                row.getCells().add(PatCur.FName);
            }
            else if (__dummyScrutVar1.equals("Middle"))
            {
                row.getCells().add(PatCur.MiddleI);
            }
            else if (__dummyScrutVar1.equals("Preferred"))
            {
                row.getCells().add(PatCur.Preferred);
            }
            else if (__dummyScrutVar1.equals("Title"))
            {
                row.getCells().add(PatCur.Title);
            }
            else if (__dummyScrutVar1.equals("Salutation"))
            {
                row.getCells().add(PatCur.Salutation);
            }
            else if (__dummyScrutVar1.equals("Status"))
            {
                row.getCells().Add(PatCur.PatStatus.ToString());
                if (PatCur.PatStatus == PatientStatus.Deceased)
                {
                    row.setColorText(Color.Red);
                }
                 
            }
            else if (__dummyScrutVar1.equals("Gender"))
            {
                row.getCells().Add(PatCur.Gender.ToString());
            }
            else if (__dummyScrutVar1.equals("Position"))
            {
                row.getCells().Add(PatCur.Position.ToString());
            }
            else if (__dummyScrutVar1.equals("Birthdate"))
            {
                if (PatCur.Birthdate.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(PatCur.Birthdate.ToString("d"));
                } 
            }
            else if (__dummyScrutVar1.equals("Age"))
            {
                row.getCells().add(PatientLogic.dateToAgeString(PatCur.Birthdate,PatCur.DateTimeDeceased));
            }
            else if (__dummyScrutVar1.equals("SS#"))
            {
                if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "US") && PatCur.SSN != null && PatCur.SSN.Length == 9)
                {
                    row.getCells().Add(PatCur.SSN.Substring(0, 3) + "-" + PatCur.SSN.Substring(3, 2) + "-" + PatCur.SSN.Substring(5, 4));
                }
                else
                {
                    row.getCells().add(PatCur.SSN);
                } 
            }
            else if (__dummyScrutVar1.equals("Address"))
            {
                row.getCells().add(PatCur.Address);
                row.setBold(true);
            }
            else if (__dummyScrutVar1.equals("Address2"))
            {
                row.getCells().add(PatCur.Address2);
            }
            else if (__dummyScrutVar1.equals("City"))
            {
                row.getCells().add(PatCur.City);
            }
            else if (__dummyScrutVar1.equals("State"))
            {
                row.getCells().add(PatCur.State);
            }
            else if (__dummyScrutVar1.equals("Country"))
            {
                row.getCells().add(PatCur.Country);
            }
            else if (__dummyScrutVar1.equals("Zip"))
            {
                row.getCells().add(PatCur.Zip);
            }
            else if (__dummyScrutVar1.equals("Hm Phone"))
            {
                row.getCells().add(PatCur.HmPhone);
                if (PatCur.PreferContactMethod == ContactMethod.HmPhone || PatCur.PreferContactMethod == ContactMethod.None)
                {
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("Wk Phone"))
            {
                row.getCells().add(PatCur.WkPhone);
                if (PatCur.PreferContactMethod == ContactMethod.WkPhone)
                {
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("Wireless Ph"))
            {
                row.getCells().add(PatCur.WirelessPhone);
                if (PatCur.PreferContactMethod == ContactMethod.WirelessPh)
                {
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("E-mail"))
            {
                row.getCells().add(PatCur.Email);
                if (PatCur.PreferContactMethod == ContactMethod.Email)
                {
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("Contact Method"))
            {
                row.getCells().Add(PatCur.PreferContactMethod.ToString());
                if (PatCur.PreferContactMethod == ContactMethod.DoNotCall || PatCur.PreferContactMethod == ContactMethod.SeeNotes)
                {
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("ABC0"))
            {
                row.getCells().add(PatCur.CreditType);
            }
            else if (__dummyScrutVar1.equals("Chart Num"))
            {
                row.getCells().add(PatCur.ChartNumber);
            }
            else if (__dummyScrutVar1.equals("Billing Type"))
            {
                row.getCells().add(DefC.getName(DefCat.BillingTypes,PatCur.BillingType));
            }
            else if (__dummyScrutVar1.equals("Ward"))
            {
                row.getCells().add(PatCur.Ward);
            }
            else if (__dummyScrutVar1.equals("AdmitDate"))
            {
                row.getCells().Add(PatCur.AdmitDate.ToShortDateString());
            }
            else if (__dummyScrutVar1.equals("Primary Provider"))
            {
                row.getCells().add(Providers.getLongDesc(Patients.getProvNum(PatCur)));
            }
            else if (__dummyScrutVar1.equals("Sec. Provider"))
            {
                if (PatCur.SecProv != 0)
                {
                    row.getCells().add(Providers.getLongDesc(PatCur.SecProv));
                }
                else
                {
                    row.getCells().add("None");
                } 
            }
            else if (__dummyScrutVar1.equals("Payor Types"))
            {
                row.setTag("Payor Types");
                row.getCells().add(PayorTypes.getCurrentDescription(PatCur.PatNum));
            }
            else if (__dummyScrutVar1.equals("Language"))
            {
                if (StringSupport.equals(PatCur.Language, "") || PatCur.Language == null)
                {
                    row.getCells().add("");
                }
                else
                {
                    try
                    {
                        row.getCells().Add(CodeBase.MiscUtils.getCultureFromThreeLetter(PatCur.Language).DisplayName);
                    }
                    catch (Exception __dummyCatchVar1)
                    {
                        //row.Cells.Add(CultureInfo.GetCultureInfo(PatCur.Language).DisplayName);
                        row.getCells().add(PatCur.Language);
                    }
                
                } 
            }
            else if (__dummyScrutVar1.equals("Clinic"))
            {
                row.getCells().add(Clinics.getDesc(PatCur.ClinicNum));
            }
            else if (__dummyScrutVar1.equals("ResponsParty"))
            {
                if (PatCur.ResponsParty == 0)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().add(Patients.getLim(PatCur.ResponsParty).getNameLF());
                } 
                row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
            }
            else if (__dummyScrutVar1.equals("Referrals"))
            {
                List<RefAttach> RefList = RefAttaches.Refresh(PatCur.PatNum);
                if (RefList.Count == 0)
                {
                    row.getCells().add(Lan.g("TablePatient","None"));
                    row.setTag("Referral");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                }
                 
                for (int i = 0;i < RefList.Count;i++)
                {
                    //else{
                    //	row.Cells.Add("");
                    //	row.Tag="Referral";
                    //	row.ColorBackG=DefC.Short[(int)DefCat.MiscColors][8].ItemColor;
                    //}
                    row = new OpenDental.UI.ODGridRow();
                    if (RefList[i].IsFrom)
                    {
                        row.getCells().add(Lan.g("TablePatient","Referred From"));
                    }
                    else
                    {
                        row.getCells().add(Lan.g("TablePatient","Referred To"));
                    } 
                    try
                    {
                        String refInfo = Referrals.GetNameLF(RefList[i].ReferralNum);
                        String phoneInfo = Referrals.GetPhone(RefList[i].ReferralNum);
                        if (!StringSupport.equals(phoneInfo, "") || !StringSupport.equals(RefList[i].Note, ""))
                        {
                            refInfo += "\r\n" + phoneInfo + " " + RefList[i].Note;
                        }
                         
                        row.getCells().add(refInfo);
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                        row.getCells().add("");
                    }

                    //if referral is null because using random keys and had bug.
                    row.setTag("Referral");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                    if (i < RefList.Count - 1)
                    {
                        gridPat.getRows().add(row);
                    }
                     
                }
            }
            else if (__dummyScrutVar1.equals("Addr/Ph Note"))
            {
                row.getCells().add(PatCur.AddrNote);
                if (!StringSupport.equals(PatCur.AddrNote, ""))
                {
                    row.setColorText(Color.Red);
                    row.setBold(true);
                }
                 
            }
            else if (__dummyScrutVar1.equals("Guardians"))
            {
                List<Guardian> guardianList = Guardians.refresh(PatCur.PatNum);
                String str = "";
                for (int g = 0;g < guardianList.Count;g++)
                {
                    if (!guardianList[g].IsGuardian)
                    {
                        continue;
                    }
                     
                    if (g > 0)
                    {
                        str += ",";
                    }
                     
                    str += FamCur.GetNameInFamFirst(guardianList[g].PatNumGuardian) + Guardians.GetGuardianRelationshipStr(guardianList[g].Relationship);
                }
                row.getCells().add(str);
            }
            else if (__dummyScrutVar1.equals("PatFields"))
            {
                PatField field;
                for (int i = 0;i < PatFieldDefs.getList().Length;i++)
                {
                    if (i > 0)
                    {
                        row = new OpenDental.UI.ODGridRow();
                    }
                     
                    row.getCells().Add(PatFieldDefs.getList()[i].FieldName);
                    field = PatFields.GetByName(PatFieldDefs.getList()[i].FieldName, PatFieldList);
                    if (field == null)
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        if (PatFieldDefs.getList()[i].FieldType == PatFieldType.Checkbox)
                        {
                            row.getCells().add("X");
                        }
                        else if (PatFieldDefs.getList()[i].FieldType == PatFieldType.Currency)
                        {
                            row.getCells().Add(PIn.Double(field.FieldValue).ToString("c"));
                        }
                        else
                        {
                            row.getCells().add(field.FieldValue);
                        }  
                    } 
                    row.setTag("PatField" + i.ToString());
                    gridPat.getRows().add(row);
                }
            }
            else if (__dummyScrutVar1.equals("Arrive Early"))
            {
                if (PatCur.AskToArriveEarly == 0)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(PatCur.AskToArriveEarly.ToString());
                } 
            }
            else if (__dummyScrutVar1.equals("References"))
            {
                List<CustRefEntry> custREList = CustRefEntries.getEntryListForCustomer(PatCur.PatNum);
                if (custREList.Count == 0)
                {
                    row.getCells().add(Lan.g("TablePatient","None"));
                    row.setTag("References");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                }
                else
                {
                    row.getCells().add(Lan.g("TablePatient",""));
                    row.setTag("References");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                    gridPat.getRows().add(row);
                } 
                for (int i = 0;i < custREList.Count;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    if (custREList[i].PatNumRef == PatCur.PatNum)
                    {
                        row.getCells().Add(custREList[i].DateEntry.ToShortDateString());
                        row.getCells().add("For: " + CustReferences.GetCustNameFL(custREList[i].PatNumCust));
                    }
                    else
                    {
                        row.getCells().add("");
                        row.getCells().Add(CustReferences.GetCustNameFL(custREList[i].PatNumRef));
                    } 
                    row.setTag(custREList[i]);
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                    if (i < custREList.Count - 1)
                    {
                        gridPat.getRows().add(row);
                    }
                     
                }
            }
            else if (__dummyScrutVar1.equals("Super Head"))
            {
                String fieldVal = "";
                if (PatCur.SuperFamily != 0)
                {
                    Patient supHead = Patients.getPat(PatCur.SuperFamily);
                    fieldVal = supHead.getNameLF() + " (" + supHead.PatNum + ")";
                }
                 
                row.getCells().add(fieldVal);
            }
                                                     
            if (StringSupport.equals(fields[f].InternalName, "PatFields"))
            {
            }
            else
            {
                //don't add the row here
                gridPat.getRows().add(row);
            } 
        }
        gridPat.endUpdate();
    }

    private void fillFamilyData() throws Exception {
        gridFamily.beginUpdate();
        gridFamily.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePatient","Name"),140);
        gridFamily.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePatient","Position"),65);
        gridFamily.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePatient","Gender"),55);
        gridFamily.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePatient","Status"),65);
        gridFamily.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePatient","Age"),45);
        gridFamily.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePatient","Recall Due"),80);
        gridFamily.getColumns().add(col);
        gridFamily.getRows().Clear();
        if (PatCur == null)
        {
            gridFamily.endUpdate();
            return ;
        }
         
        OpenDental.UI.ODGridRow row;
        DateTime recallDate = new DateTime();
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(FamCur.getNameInFamLFI(i));
            row.getCells().Add(Lan.g("enumPatientPosition", FamCur.ListPats[i].Position.ToString()));
            row.getCells().Add(Lan.g("enumPatientGender", FamCur.ListPats[i].Gender.ToString()));
            row.getCells().Add(Lan.g("enumPatientStatus", FamCur.ListPats[i].PatStatus.ToString()));
            row.getCells().Add(Patients.AgeToString(FamCur.ListPats[i].Age));
            recallDate = DateTime.MinValue;
            for (int j = 0;j < RecallList.Count;j++)
            {
                if (RecallList[j].PatNum == FamCur.ListPats[i].PatNum && (RecallList[j].RecallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialProphy) || RecallList[j].RecallTypeNum == PrefC.getLong(PrefName.RecallTypeSpecialPerio)))
                {
                    recallDate = RecallList[j].DateDue;
                }
                 
            }
            cell = new OpenDental.UI.ODGridCell();
            if (recallDate.Year > 1880)
            {
                cell.setText(recallDate.ToShortDateString());
                if (recallDate < DateTime.Today)
                {
                    cell.setBold(YN.Yes);
                    cell.setColorText(Color.Firebrick);
                }
                 
            }
             
            row.getCells().Add(cell);
            if (i == 0)
            {
                //guarantor
                row.setBold(true);
            }
             
            gridFamily.getRows().add(row);
        }
        gridFamily.endUpdate();
        gridFamily.setSelected(FamCur.getIndex(PatCur.PatNum),true);
    }

    private void gridFamily_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //if (tbFamily.SelectedRow != -1){
        //	tbFamily.ColorRow(tbFamily.SelectedRow,Color.White);
        //}
        //tbFamily.SelectedRow=e.Row;
        //tbFamily.ColorRow(e.Row,Color.DarkSalmon);
        OnPatientSelected(FamCur.ListPats[e.getRow()]);
        ModuleSelected(FamCur.ListPats[e.getRow()].PatNum);
    }

    //private void butAddPt_Click(object sender, System.EventArgs e) {
    private void toolButAdd_Click() throws Exception {
        //At HQ, we cannot allow users to add patients to reseller families.
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && Resellers.isResellerFamily(PatCur.Guarantor))
        {
            MsgBox.show(this,"Cannot add patients to a reseller family.");
            return ;
        }
         
        Patient tempPat = new Patient();
        tempPat.LName = PatCur.LName;
        tempPat.PatStatus = PatientStatus.Patient;
        tempPat.Address = PatCur.Address;
        tempPat.Address2 = PatCur.Address2;
        tempPat.City = PatCur.City;
        tempPat.State = PatCur.State;
        tempPat.Zip = PatCur.Zip;
        tempPat.HmPhone = PatCur.HmPhone;
        tempPat.Guarantor = PatCur.Guarantor;
        tempPat.CreditType = PatCur.CreditType;
        tempPat.PriProv = PatCur.PriProv;
        tempPat.SecProv = PatCur.SecProv;
        tempPat.FeeSched = PatCur.FeeSched;
        tempPat.BillingType = PatCur.BillingType;
        tempPat.AddrNote = PatCur.AddrNote;
        tempPat.ClinicNum = PatCur.ClinicNum;
        //this is probably better in case they don't have user.ClinicNums set.
        //tempPat.ClinicNum  =Security.CurUser.ClinicNum;
        if (Patients.getPat(tempPat.Guarantor).SuperFamily != 0)
        {
            tempPat.SuperFamily = PatCur.SuperFamily;
        }
         
        Patients.insert(tempPat,false);
        CustReference custRef = new CustReference();
        custRef.PatNum = tempPat.PatNum;
        CustReferences.insert(custRef);
        FormPatientEdit FormPE = new FormPatientEdit(tempPat,FamCur);
        FormPE.IsNew = true;
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.OK)
        {
            onPatientSelected(tempPat);
            moduleSelected(tempPat.PatNum);
        }
        else
        {
            moduleSelected(PatCur.PatNum);
        } 
    }

    //private void butDeletePt_Click(object sender, System.EventArgs e) {
    private void toolButDelete_Click() throws Exception {
        //this doesn't actually delete the patient, just changes their status
        //and they will never show again in the patient selection list.
        //check for plans, appointments, procedures, etc.
        List<Procedure> procList = Procedures.refresh(PatCur.PatNum);
        List<Claim> claimList = Claims.refresh(PatCur.PatNum);
        Adjustment[] AdjustmentList = Adjustments.refresh(PatCur.PatNum);
        PaySplit[] PaySplitList = PaySplits.refresh(PatCur.PatNum);
        //
        List<ClaimProc> claimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Commlog> commlogList = Commlogs.refresh(PatCur.PatNum);
        int payPlanCount = PayPlans.getDependencyCount(PatCur.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<MedicationPat> medList = MedicationPats.refresh(PatCur.PatNum,false);
        PatPlanList = PatPlans.refresh(PatCur.PatNum);
        //CovPats.Refresh(planList,PatPlanList);
        List<RefAttach> RefAttachList = RefAttaches.Refresh(PatCur.PatNum);
        List<Sheet> sheetList = Sheets.getForPatient(PatCur.PatNum);
        boolean hasProcs = procList.Count > 0;
        boolean hasClaims = claimList.Count > 0;
        boolean hasAdj = AdjustmentList.Length > 0;
        boolean hasPay = PaySplitList.Length > 0;
        boolean hasClaimProcs = claimProcList.Count > 0;
        boolean hasComm = commlogList.Count > 0;
        boolean hasPayPlans = payPlanCount > 0;
        boolean hasInsPlans = false;
        boolean hasMeds = medList.Count > 0;
        boolean isSuperFamilyHead = PatCur.PatNum == PatCur.SuperFamily;
        for (int i = 0;i < subList.Count;i++)
        {
            if (subList[i].Subscriber == PatCur.PatNum)
            {
                hasInsPlans = true;
            }
             
        }
        boolean hasRef = RefAttachList.Count > 0;
        boolean hasSheets = sheetList.Count > 0;
        if (hasProcs || hasClaims || hasAdj || hasPay || hasClaimProcs || hasComm || hasPayPlans || hasInsPlans || hasRef || hasMeds || isSuperFamilyHead || hasSheets)
        {
            String message = Lan.g(this,"You cannot delete this patient without first deleting the following data:") + "\r";
            if (hasProcs)
                message += Lan.g(this,"Procedures") + "\r";
             
            if (hasClaims)
                message += Lan.g(this,"Claims") + "\r";
             
            if (hasAdj)
                message += Lan.g(this,"Adjustments") + "\r";
             
            if (hasPay)
                message += Lan.g(this,"Payments") + "\r";
             
            if (hasClaimProcs)
                message += Lan.g(this,"Procedures attached to claims") + "\r";
             
            if (hasComm)
                message += Lan.g(this,"Commlog entries") + "\r";
             
            if (hasPayPlans)
                message += Lan.g(this,"Payment plans") + "\r";
             
            if (hasInsPlans)
                message += Lan.g(this,"Insurance plans") + "\r";
             
            if (hasRef)
                message += Lan.g(this,"References") + "\r";
             
            if (hasMeds)
                message += Lan.g(this,"Medications") + "\r";
             
            if (isSuperFamilyHead)
                message += Lan.g(this,"Attached Super Family") + "\r";
             
            if (hasSheets)
                message += Lan.g(this,"Sheets") + "\r";
             
            MessageBox.Show(message);
            return ;
        }
         
        Patient PatOld = PatCur.copy();
        if (PatCur.PatNum == PatCur.Guarantor)
        {
            //if selecting guarantor
            if (FamCur.ListPats.Length == 1)
            {
                if (!MsgBox.show(this,true,"Delete Patient?"))
                {
                    return ;
                }
                 
                PatCur.PatStatus = PatientStatus.Deleted;
                PatCur.ChartNumber = "";
                PatCur.ClinicNum = 0;
                Popups.moveForDeletePat(PatCur);
                PatCur.SuperFamily = 0;
                Patients.update(PatCur,PatOld);
                for (int i = 0;i < RecallList.Count;i++)
                {
                    if (RecallList[i].PatNum == PatCur.PatNum)
                    {
                        RecallList[i].IsDisabled = true;
                        RecallList[i].DateDue = DateTime.MinValue;
                        Recalls.Update(RecallList[i]);
                    }
                     
                }
                onPatientSelected(new Patient());
                ModuleSelected(0);
            }
            else
            {
                //does not delete notes or plans, etc.
                MessageBox.Show(Lan.g(this,"You cannot delete the guarantor if there are other family members. You would have to make a different family member the guarantor first."));
            } 
        }
        else
        {
            //not selecting guarantor
            if (!MsgBox.show(this,true,"Delete Patient?"))
            {
                return ;
            }
             
            PatCur.PatStatus = PatientStatus.Deleted;
            PatCur.ChartNumber = "";
            PatCur.ClinicNum = 0;
            Popups.moveForDeletePat(PatCur);
            PatCur.Guarantor = PatCur.PatNum;
            PatCur.SuperFamily = 0;
            Patients.update(PatCur,PatOld);
            for (int i = 0;i < RecallList.Count;i++)
            {
                if (RecallList[i].PatNum == PatCur.PatNum)
                {
                    RecallList[i].IsDisabled = true;
                    RecallList[i].DateDue = DateTime.MinValue;
                    Recalls.Update(RecallList[i]);
                }
                 
            }
            moduleSelected(PatOld.Guarantor);
        } 
    }

    //private void butSetGuar_Click(object sender,System.EventArgs e){
    private void toolButGuarantor_Click() throws Exception {
        if (PatCur.PatNum == PatCur.Guarantor)
        {
            MessageBox.Show(Lan.g(this,"Patient is already the guarantor.  Please select a different family member."));
            return ;
        }
         
        //At HQ, we cannot allow users to change the guarantor of reseller families.
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && Resellers.isResellerFamily(PatCur.Guarantor))
        {
            MsgBox.show(this,"Cannot change the guarantor of a reseller family.");
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Make the selected patient the guarantor?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        if (PatCur.SuperFamily == PatCur.Guarantor)
        {
            //guarantor is also the head of a super family
            Patients.moveSuperFamily(PatCur.SuperFamily,PatCur.PatNum);
        }
         
        Patients.changeGuarantorToCur(FamCur,PatCur);
        moduleSelected(PatCur.PatNum);
    }

    //private void butMovePat_Click(object sender, System.EventArgs e) {
    private void toolButMove_Click() throws Exception {
        //At HQ, we cannot allow users to move patients of reseller families.
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && Resellers.isResellerFamily(PatCur.Guarantor))
        {
            MsgBox.show(this,"Cannot move patients of a reseller family.");
            return ;
        }
         
        Patient PatOld = PatCur.copy();
        //Patient PatCur;
        if (PatCur.PatNum == PatCur.Guarantor)
        {
            //if guarantor selected
            if (FamCur.ListPats.Length == 1)
            {
                //and no other family members
                //no need to check insurance.  It will follow.
                if (!MsgBox.show(this,true,"Moving the guarantor will cause two families to be combined.  The financial notes for both families will be combined and may need to be edited.  The address notes will also be combined and may need to be edited. Do you wish to continue?"))
                {
                    return ;
                }
                 
                if (!MsgBox.show(this,true,"Select the family to move this patient to from the list that will come up next."))
                {
                    return ;
                }
                 
                FormPatientSelect FormPS = new FormPatientSelect();
                FormPS.SelectionModeOnly = true;
                FormPS.ShowDialog();
                if (FormPS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                Patient patInNewFam = Patients.getPat(FormPS.SelectedPatNum);
                if (PatCur.SuperFamily != patInNewFam.SuperFamily)
                {
                    //If they are moving into or out of a superfamily
                    if (PatCur.SuperFamily != 0)
                    {
                        //If they are currently in a SuperFamily and moving out.  Otherwise, no superfamily popups to worry about.
                        Popups.copyForMovingSuperFamily(PatCur,patInNewFam.SuperFamily);
                    }
                     
                }
                 
                PatCur.Guarantor = patInNewFam.Guarantor;
                PatCur.SuperFamily = patInNewFam.SuperFamily;
                Patients.update(PatCur,PatOld);
                FamCur = Patients.getFamily(PatCur.PatNum);
                Patients.combineGuarantors(FamCur,PatCur);
            }
            else
            {
                //there are other family members
                MessageBox.Show(Lan.g(this,"You cannot move the guarantor.  If you wish to move the guarantor, you must make another family member the guarantor first."));
            } 
        }
        else
        {
            //guarantor not selected
            if (!MsgBox.show(this,true,"Preparing to move family member.  Financial notes and address notes will not be transferred.  Popups will be copied.  Proceed to next step?"))
            {
                return ;
            }
             
            MessageBox.APPLY __dummyScrutVar2 = MessageBox.Show(Lan.g(this,"Create new family instead of moving to an existing family?"), "", MessageBoxButtons.YesNoCancel);
            if (__dummyScrutVar2.equals(DialogResult.Cancel))
            {
                return ;
            }
            else if (__dummyScrutVar2.equals(DialogResult.Yes))
            {
                //new family (split)
                Popups.copyForMovingFamilyMember(PatCur);
                //Copy Family Level Popups to new family.
                //Don't need to copy SuperFamily Popups. Stays in same super family.
                PatCur.Guarantor = PatCur.PatNum;
                //keep current superfamily
                Patients.update(PatCur,PatOld);
            }
            else if (__dummyScrutVar2.equals(DialogResult.No))
            {
                //move to an existing family
                if (!MsgBox.show(this,true,"Select the family to move this patient to from the list that will come up next."))
                {
                    return ;
                }
                 
                FormPatientSelect FormPS = new FormPatientSelect();
                FormPS.SelectionModeOnly = true;
                FormPS.ShowDialog();
                if (FormPS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                Patient patInNewFam = Patients.getPat(FormPS.SelectedPatNum);
                if (patInNewFam.Guarantor == PatCur.Guarantor)
                {
                    return ;
                }
                 
                // Patient is already a part of the family.
                Popups.copyForMovingFamilyMember(PatCur);
                //Copy Family Level Popups to new Family.
                if (PatCur.SuperFamily != patInNewFam.SuperFamily)
                {
                    //If they are moving into or out of a superfamily
                    if (PatCur.SuperFamily != 0)
                    {
                        //If they are currently in a SuperFamily.  Otherwise, no superfamily popups to worry about.
                        Popups.copyForMovingSuperFamily(PatCur,patInNewFam.SuperFamily);
                    }
                     
                }
                 
                PatCur.Guarantor = patInNewFam.Guarantor;
                PatCur.SuperFamily = patInNewFam.SuperFamily;
                //assign to the new superfamily
                Patients.update(PatCur,PatOld);
            }
               
        } 
        //end guarantor not selected
        moduleSelected(PatCur.PatNum);
    }

    private void fillGridRecall() throws Exception {
        gridRecall.beginUpdate();
        //standard width is 354.  Nice to grow it to 525 if space allows.
        int maxWidth = Width - gridRecall.Left;
        if (maxWidth > 525)
        {
            maxWidth = 525;
        }
         
        if (maxWidth > 354)
        {
            gridRecall.Width = maxWidth;
        }
        else
        {
            gridRecall.Width = 354;
        } 
        gridRecall.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRecall","Type"),90);
        gridRecall.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecall","Due Date"),80);
        gridRecall.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecall","Sched Date"),80);
        gridRecall.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecall","Notes"),80);
        gridRecall.getColumns().add(col);
        gridRecall.getRows().Clear();
        if (PatCur == null)
        {
            gridRecall.endUpdate();
            return ;
        }
         
        //we just want the recall for the current patient
        List<Recall> recallListPat = new List<Recall>();
        for (int i = 0;i < RecallList.Count;i++)
        {
            if (RecallList[i].PatNum == PatCur.PatNum)
            {
                recallListPat.Add(RecallList[i]);
            }
             
        }
        OpenDental.UI.ODGridRow row;
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < recallListPat.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //Type
            String cellStr = RecallTypes.GetDescription(recallListPat[i].RecallTypeNum);
            row.getCells().add(cellStr);
            //Due date
            if (recallListPat[i].DateDue.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                cell = new OpenDental.UI.ODGridCell(recallListPat[i].DateDue.ToShortDateString());
                if (recallListPat[i].DateDue < DateTime.Today)
                {
                    cell.setBold(YN.Yes);
                    cell.setColorText(Color.Firebrick);
                }
                 
                row.getCells().Add(cell);
            } 
            //Sched Date
            if (recallListPat[i].DateScheduled.Year > 1880)
            {
                row.getCells().Add(recallListPat[i].DateScheduled.ToShortDateString());
            }
            else
            {
                row.getCells().add("");
            } 
            //Notes
            cellStr = "";
            if (recallListPat[i].IsDisabled)
            {
                cellStr += Lan.g(this,"Disabled");
                if (recallListPat[i].DatePrevious.Year > 1800)
                {
                    cellStr += Lan.g(this,". Previous: ") + recallListPat[i].DatePrevious.ToShortDateString();
                    if (recallListPat[i].RecallInterval != new Interval(0,0,0,0))
                    {
                        DateTime duedate = recallListPat[i].DatePrevious + recallListPat[i].RecallInterval;
                        cellStr += Lan.g(this,". (Due): ") + duedate.ToShortDateString();
                    }
                     
                }
                 
            }
             
            if (recallListPat[i].DisableUntilDate.Year > 1880)
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += Lan.g(this,"Disabled until ") + recallListPat[i].DisableUntilDate.ToShortDateString();
            }
             
            if (recallListPat[i].DisableUntilBalance > 0)
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += Lan.g(this,"Disabled until balance ") + recallListPat[i].DisableUntilBalance.ToString("c");
            }
             
            if (recallListPat[i].RecallStatus != 0)
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += DefC.GetName(DefCat.RecallUnschedStatus, recallListPat[i].RecallStatus);
            }
             
            if (!StringSupport.equals(recallListPat[i].Note, ""))
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += recallListPat[i].Note;
            }
             
            row.getCells().add(cellStr);
            gridRecall.getRows().add(row);
        }
        gridRecall.endUpdate();
    }

    private void gridRecall_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //use doubleclick instead
    private void gridRecall_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        FormRecallsPat FormR = new FormRecallsPat();
        FormR.PatNum = PatCur.PatNum;
        FormR.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void fillGridSuperFam() throws Exception {
        gridSuperFam.beginUpdate();
        gridSuperFam.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("gridSuperFam",""),140);
        gridSuperFam.getColumns().add(col);
        gridSuperFam.getRows().Clear();
        if (PatCur == null)
        {
            return ;
        }
         
        OpenDental.UI.ODGridRow row;
        SuperFamilyGuarantors.Sort(sortPatientListBySuperFamily);
        String superfam = "";
        for (int i = 0;i < SuperFamilyGuarantors.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            superfam = SuperFamilyGuarantors[i].GetNameFL();
            for (int j = 0;j < SuperFamilyMembers.Count;j++)
            {
                if (SuperFamilyMembers[j].Guarantor == SuperFamilyGuarantors[i].Guarantor && SuperFamilyMembers[j].PatNum != SuperFamilyGuarantors[i].PatNum)
                {
                    superfam += ", " + SuperFamilyMembers[j].GetNameFL();
                }
                 
            }
            row.getCells().add(superfam);
            row.setTag(SuperFamilyGuarantors[i].PatNum);
            if (i == 0)
            {
                row.getCells()[0].Bold = YN.Yes;
                row.getCells()[0].ColorText = Color.OrangeRed;
            }
             
            gridSuperFam.getRows().add(row);
        }
        gridSuperFam.endUpdate();
        for (int i = 0;i < gridSuperFam.getRows().Count;i++)
        {
            if ((Long)gridSuperFam.getRows().get___idx(i).getTag() == PatCur.Guarantor)
            {
                gridSuperFam.setSelected(i,true);
                break;
            }
             
        }
    }

    private int sortPatientListBySuperFamily(Patient pat1, Patient pat2) throws Exception {
        if (pat1.PatNum == pat2.PatNum)
        {
            return 0;
        }
         
        if (pat1.PatNum == pat1.SuperFamily)
        {
            return -1;
        }
         
        //if pat1 is superhead
        //pat1 comes first
        if (pat2.PatNum == pat2.SuperFamily)
        {
            return 1;
        }
         
        return (int)(pat1.PatNum - pat2.PatNum);
    }

    //if pat2 is superhead
    //sort by patnums.
    //return pat1.GetNameFL().CompareTo(pat2.GetNameFL());//Alphabetize them if nothing else.
    private void gridSuperFam_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        OnPatientSelected(SuperFamilyGuarantors[e.getRow()]);
        ModuleSelected(SuperFamilyGuarantors[e.getRow()].PatNum);
    }

    private void gridSuperFam_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //OnPatientSelected(SuperFamilyGuarantors[e.Row].PatNum,SuperFamilyGuarantors[e.Row].GetNameLF(),SuperFamilyGuarantors[e.Row].Email!="",
    //  SuperFamilyGuarantors[e.Row].ChartNumber);
    //ModuleSelected(SuperFamilyGuarantors[e.Row].PatNum);
    private void toolButAddSuper_Click() throws Exception {
        if (PatCur.SuperFamily == 0)
        {
            Patients.assignToSuperfamily(PatCur.Guarantor,PatCur.Guarantor);
        }
        else
        {
            //we must want to add some other family to this superfamily
            FormPatientSelect formPS = new FormPatientSelect();
            formPS.SelectionModeOnly = true;
            formPS.ShowDialog();
            if (formPS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            Patient patSelected = Patients.getPat(formPS.SelectedPatNum);
            if (patSelected.SuperFamily == PatCur.SuperFamily)
            {
                MsgBox.show(this,"That patient is already part of this superfamily.");
                return ;
            }
             
            Patients.assignToSuperfamily(patSelected.Guarantor,PatCur.SuperFamily);
        } 
        moduleSelected(PatCur.PatNum);
    }

    private void toolButRemoveSuper_Click() throws Exception {
        if (PatCur.SuperFamily == PatCur.Guarantor)
        {
            MsgBox.show(this,"You cannot delete the head of a super family.");
            return ;
        }
         
        if (PatCur.SuperFamily == 0)
        {
            return ;
        }
         
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            //remove whole family
            Patient tempPat = FamCur.ListPats[i].Copy();
            Popups.CopyForMovingSuperFamily(tempPat, 0);
            tempPat.SuperFamily = 0;
            Patients.Update(tempPat, FamCur.ListPats[i]);
        }
        moduleSelected(PatCur.PatNum);
    }

    private void toolButDisbandSuper_Click() throws Exception {
        if (PatCur.SuperFamily == 0)
        {
            return ;
        }
         
        Patient superHead = Patients.getPat(PatCur.SuperFamily);
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Would you like to disband and remove all members in the super family of " + superHead.getNameFL() + "?"))
        {
            return ;
        }
         
        Popups.removeForDisbandingSuperFamily(PatCur);
        Patients.disbandSuperFamily(superHead.PatNum);
        moduleSelected(PatCur.PatNum);
    }

    private void menuPlansForFam_Click(Object sender, EventArgs e) throws Exception {
        FormPlansForFamily FormP = new FormPlansForFamily();
        FormP.FamCur = FamCur;
        FormP.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void toolButIns_Click() throws Exception {
        DialogResult result = MessageBox.Show(Lan.g(this,"Is this patient the subscriber?"), "", MessageBoxButtons.YesNoCancel);
        if (result == DialogResult.Cancel)
        {
            return ;
        }
         
        //Pick a subscriber------------------------------------------------------------------------------------------------
        Patient subscriber;
        if (result == DialogResult.Yes)
        {
            //current patient is subscriber
            subscriber = PatCur.copy();
        }
        else
        {
            //patient is not subscriber
            //show list of patients in this family
            FormSubscriberSelect FormS = new FormSubscriberSelect(FamCur);
            FormS.ShowDialog();
            if (FormS.DialogResult == DialogResult.Cancel)
            {
                return ;
            }
             
            subscriber = Patients.getPat(FormS.SelectedPatNum);
        } 
        //Subscriber has been chosen. Now, pick a plan-------------------------------------------------------------------
        InsPlan plan = null;
        InsSub sub = null;
        boolean planIsNew = false;
        List<InsSub> subList = InsSubs.getListForSubscriber(subscriber.PatNum);
        if (subList.Count == 0)
        {
            planIsNew = true;
        }
        else
        {
            FormInsSelectSubscr FormISS = new FormInsSelectSubscr(subscriber.PatNum);
            FormISS.ShowDialog();
            if (FormISS.DialogResult == DialogResult.Cancel)
            {
                return ;
            }
             
            if (FormISS.SelectedInsSubNum == 0)
            {
                //'New' option selected.
                planIsNew = true;
            }
            else
            {
                sub = InsSubs.GetSub(FormISS.SelectedInsSubNum, subList);
                plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
            } 
        } 
        //New plan was selected instead of an existing plan.  Create the plan--------------------------------------------
        if (planIsNew)
        {
            plan = new InsPlan();
            plan.EmployerNum = subscriber.EmployerNum;
            plan.PlanType = "";
            InsPlans.insert(plan);
            sub = new InsSub();
            sub.PlanNum = plan.PlanNum;
            sub.Subscriber = subscriber.PatNum;
            if (StringSupport.equals(subscriber.MedicaidID, ""))
            {
                sub.SubscriberID = subscriber.SSN;
            }
            else
            {
                sub.SubscriberID = subscriber.MedicaidID;
            } 
            sub.ReleaseInfo = true;
            sub.AssignBen = true;
            InsSubs.insert(sub);
            Benefit ben;
            for (int i = 0;i < CovCatC.getListShort().Count;i++)
            {
                if (CovCatC.getListShort()[i].DefaultPercent == -1)
                {
                    continue;
                }
                 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCatC.getListShort()[i].CovCatNum;
                ben.PlanNum = plan.PlanNum;
                ben.Percent = CovCatC.getListShort()[i].DefaultPercent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.CodeNum = 0;
                Benefits.insert(ben);
            }
            //Zero deductible diagnostic
            if (CovCats.getForEbenCat(EbenefitCategory.Diagnostic) != null)
            {
                ben = new Benefit();
                ben.CodeNum = 0;
                ben.BenefitType = InsBenefitType.Deductible;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
                ben.PlanNum = plan.PlanNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = 0;
                ben.Percent = -1;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                Benefits.insert(ben);
            }
             
            //Zero deductible preventive
            if (CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) != null)
            {
                ben = new Benefit();
                ben.CodeNum = 0;
                ben.BenefitType = InsBenefitType.Deductible;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                ben.PlanNum = plan.PlanNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = 0;
                ben.Percent = -1;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                Benefits.insert(ben);
            }
             
        }
         
        //Then attach plan------------------------------------------------------------------------------------------------
        PatPlan patplan = new PatPlan();
        patplan.Ordinal = (byte)(PatPlanList.Count + 1);
        //so the ordinal of the first entry will be 1, NOT 0.
        patplan.PatNum = PatCur.PatNum;
        patplan.InsSubNum = sub.InsSubNum;
        patplan.Relationship = Relat.Self;
        PatPlans.insert(patplan);
        //Then, display insPlanEdit to user-------------------------------------------------------------------------------
        FormInsPlan FormI = new FormInsPlan(plan,patplan,sub);
        FormI.IsNewPlan = planIsNew;
        FormI.IsNewPatPlan = true;
        FormI.ShowDialog();
        //this updates estimates also.
        //if cancel, then patplan is deleted from within that dialog.
        //if cancel, and planIsNew, then plan and benefits are also deleted.
        moduleSelected(PatCur.PatNum);
    }

    private void fillInsData() throws Exception {
        if (PatPlanList.Count == 0)
        {
            gridIns.beginUpdate();
            gridIns.getColumns().Clear();
            gridIns.getRows().Clear();
            gridIns.endUpdate();
            return ;
        }
         
        List<InsSub> subArray = new List<InsSub>();
        //prevents repeated calls to db.
        List<InsPlan> planArray = new List<InsPlan>();
        InsSub sub;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            sub = InsSubs.GetSub(PatPlanList[i].InsSubNum, SubList);
            subArray.Add(sub);
            planArray.Add(InsPlans.getPlan(sub.PlanNum,PlanList));
        }
        gridIns.beginUpdate();
        gridIns.getColumns().Clear();
        gridIns.getRows().Clear();
        OpenDental.UI.ODGridColumn col;
        col = new OpenDental.UI.ODGridColumn("",150);
        gridIns.getColumns().add(col);
        int dentalOrdinal = 1;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            if (planArray[i].IsMedical)
            {
                col = new OpenDental.UI.ODGridColumn(Lan.g("TableCoverage","Medical"),170);
                gridIns.getColumns().add(col);
            }
            else
            {
                //dental
                if (dentalOrdinal == 1)
                {
                    col = new OpenDental.UI.ODGridColumn(Lan.g("TableCoverage","Primary"),170);
                    gridIns.getColumns().add(col);
                }
                else if (dentalOrdinal == 2)
                {
                    col = new OpenDental.UI.ODGridColumn(Lan.g("TableCoverage","Secondary"),170);
                    gridIns.getColumns().add(col);
                }
                else
                {
                    col = new OpenDental.UI.ODGridColumn(Lan.g("TableCoverage","Other"),170);
                    gridIns.getColumns().add(col);
                }  
                dentalOrdinal++;
            } 
        }
        OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
        //subscriber
        row.getCells().add(Lan.g("TableCoverage","Subscriber"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(FamCur.GetNameInFamFL(subArray[i].Subscriber));
        }
        row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor);
        gridIns.getRows().add(row);
        //subscriber ID
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Subscriber ID"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(subArray[i].SubscriberID);
        }
        row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor);
        gridIns.getRows().add(row);
        //relationship
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Rel'ship to Sub"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(Lan.g("enumRelat", PatPlanList[i].Relationship.ToString()));
        }
        row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor);
        gridIns.getRows().add(row);
        //patient ID
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Patient ID"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(PatPlanList[i].PatID);
        }
        row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor);
        gridIns.getRows().add(row);
        //pending
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Pending"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            if (PatPlanList[i].IsPending)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
        }
        row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor);
        row.setColorLborder(Color.Black);
        gridIns.getRows().add(row);
        //employer
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Employer"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(Employers.GetName(planArray[i].EmployerNum));
        }
        gridIns.getRows().add(row);
        //carrier
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Carrier"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(InsPlans.GetCarrierName(planArray[i].PlanNum, planArray));
        }
        gridIns.getRows().add(row);
        //group name
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Group Name"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(planArray[i].GroupName);
        }
        gridIns.getRows().add(row);
        //group number
        row = new OpenDental.UI.ODGridRow();
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            row.getCells().add(Lan.g("TableCoverage","Plan Number"));
        }
        else
        {
            row.getCells().add(Lan.g("TableCoverage","Group Number"));
        } 
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            row.getCells().Add(planArray[i].GroupNum);
        }
        gridIns.getRows().add(row);
        //plan type
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Type"));
        for (int i = 0;i < planArray.Count;i++)
        {
            PlanType __dummyScrutVar3 = planArray[i].PlanType;
            //malfunction
            if (__dummyScrutVar3.equals(""))
            {
                row.getCells().add(Lan.g(this,"Category Percentage"));
            }
            else if (__dummyScrutVar3.equals("p"))
            {
                row.getCells().add(Lan.g(this,"PPO Percentage"));
            }
            else if (__dummyScrutVar3.equals("f"))
            {
                row.getCells().add(Lan.g(this,"Medicaid or Flat Co-pay"));
            }
            else if (__dummyScrutVar3.equals("c"))
            {
                row.getCells().add(Lan.g(this,"Capitation"));
            }
            else
            {
                row.getCells().add("");
            }    
        }
        gridIns.getRows().add(row);
        //fee schedule
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Fee Schedule"));
        for (int i = 0;i < planArray.Count;i++)
        {
            row.getCells().Add(FeeScheds.GetDescription(planArray[i].FeeSched));
        }
        row.setColorLborder(Color.Black);
        gridIns.getRows().add(row);
        //Calendar vs service year------------------------------------------------------------------------------------
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Benefit Period"));
        for (int i = 0;i < planArray.Count;i++)
        {
            if (planArray[i].MonthRenew == 0)
            {
                row.getCells().add(Lan.g("TableCoverage","Calendar Year"));
            }
            else
            {
                DateTime dateservice = new DateTime(2000, planArray[i].MonthRenew, 1);
                row.getCells().add(Lan.g("TableCoverage","Service year begins:") + " " + dateservice.ToString("MMMM"));
            } 
        }
        gridIns.getRows().add(row);
        //Benefits-----------------------------------------------------------------------------------------------------
        List<Benefit> bensForPat = Benefits.refresh(PatPlanList,SubList);
        Benefit[][] benMatrix = Benefits.GetDisplayMatrix(bensForPat, PatPlanList, SubList);
        String desc = new String();
        String val = new String();
        ProcedureCode proccode = null;
        for (int y = 0;y < benMatrix.GetLength(1);y++)
        {
            //rows
            row = new OpenDental.UI.ODGridRow();
            desc = "";
            for (int x = 0;x < benMatrix.GetLength(0);x++)
            {
                //some of the columns might be null, but at least one will not be.  Find it.
                //columns
                if (benMatrix[x, y] == null)
                {
                    continue;
                }
                 
                //create a description for the benefit
                if (benMatrix[x, y].PatPlanNum != 0)
                {
                    desc += Lan.g(this,"(pat)") + " ";
                }
                 
                if (benMatrix[x, y].CoverageLevel == BenefitCoverageLevel.Family)
                {
                    desc += Lan.g(this,"Fam") + " ";
                }
                 
                if (benMatrix[x, y].CodeNum != 0)
                {
                    proccode = ProcedureCodes.GetProcCode(benMatrix[x, y].CodeNum);
                }
                 
                if (benMatrix[x, y].BenefitType == InsBenefitType.CoInsurance && benMatrix[x, y].Percent != -1)
                {
                    if (benMatrix[x, y].CodeNum == 0)
                    {
                        desc += CovCats.GetDesc(benMatrix[x, y].CovCatNum) + " % ";
                    }
                    else
                    {
                        desc += proccode.ProcCode + "-" + proccode.AbbrDesc + " % ";
                    } 
                }
                else if (benMatrix[x, y].BenefitType == InsBenefitType.Deductible)
                {
                    desc += Lan.g(this,"Deductible") + " " + CovCats.GetDesc(benMatrix[x, y].CovCatNum) + " ";
                }
                else if (benMatrix[x, y].BenefitType == InsBenefitType.Limitations && benMatrix[x, y].QuantityQualifier == BenefitQuantity.None && (benMatrix[x, y].TimePeriod == BenefitTimePeriod.ServiceYear || benMatrix[x, y].TimePeriod == BenefitTimePeriod.CalendarYear))
                {
                    //annual max
                    desc += Lan.g(this,"Annual Max") + " ";
                }
                else if (benMatrix[x, y].BenefitType == InsBenefitType.Limitations && CovCats.getForEbenCat(EbenefitCategory.Orthodontics) != null && benMatrix[x, y].CovCatNum == CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum && benMatrix[x, y].QuantityQualifier == BenefitQuantity.None && benMatrix[x, y].TimePeriod == BenefitTimePeriod.Lifetime)
                {
                    desc += Lan.g(this,"Ortho Max") + " ";
                }
                else if (benMatrix[x, y].BenefitType == InsBenefitType.Limitations && CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) != null && benMatrix[x, y].CovCatNum == CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum && benMatrix[x, y].Quantity != 0)
                {
                    desc += "Exam frequency ";
                }
                else //4BW
                if (benMatrix[x, y].BenefitType == InsBenefitType.Limitations && benMatrix[x, y].CodeNum == ProcedureCodes.getCodeNum("D0274") && benMatrix[x, y].Quantity != 0)
                {
                    desc += "BW frequency ";
                }
                else //Pano
                if (benMatrix[x, y].BenefitType == InsBenefitType.Limitations && benMatrix[x, y].CodeNum == ProcedureCodes.getCodeNum("D0330") && benMatrix[x, y].Quantity != 0)
                {
                    desc += "Pano/FMX frequency ";
                }
                else if (benMatrix[x, y].CodeNum == 0)
                {
                    //e.g. flo
                    desc += ProcedureCodes.GetProcCode(benMatrix[x, y].CodeNum).AbbrDesc + " ";
                }
                else
                {
                    desc += Lan.g("enumInsBenefitType", benMatrix[x, y].BenefitType.ToString()) + " ";
                }        
                row.getCells().add(desc);
                break;
            }
            for (int x = 0;x < benMatrix.GetLength(0);x++)
            {
                //remember that matrix does not include the description column
                //columns
                val = "";
                //this matrix cell might be null
                if (benMatrix[x, y] == null)
                {
                    row.getCells().add("");
                    continue;
                }
                 
                if (benMatrix[x, y].Percent != -1)
                {
                    val += benMatrix[x, y].Percent.ToString() + "% ";
                }
                 
                if (benMatrix[x, y].MonetaryAmt != -1)
                {
                    val += benMatrix[x, y].MonetaryAmt.ToString("c0") + " ";
                }
                 
                /*
                					if(benMatrix[x,y].BenefitType==InsBenefitType.CoInsurance) {
                						val+=benMatrix[x,y].Percent.ToString()+" ";
                					}
                					else if(benMatrix[x,y].BenefitType==InsBenefitType.Deductible
                						&& benMatrix[x,y].MonetaryAmt==0)
                					{//deductible 0
                						val+=benMatrix[x,y].MonetaryAmt.ToString("c0")+" ";
                					}
                					else if(benMatrix[x,y].BenefitType==InsBenefitType.Limitations
                						&& benMatrix[x,y].QuantityQualifier==BenefitQuantity.None
                						&& (benMatrix[x,y].TimePeriod==BenefitTimePeriod.ServiceYear
                						|| benMatrix[x,y].TimePeriod==BenefitTimePeriod.CalendarYear)
                						&& benMatrix[x,y].MonetaryAmt==0)
                					{//annual max 0
                						val+=benMatrix[x,y].MonetaryAmt.ToString("c0")+" ";
                					}*/
                if (benMatrix[x, y].BenefitType == InsBenefitType.Exclusions || benMatrix[x, y].BenefitType == InsBenefitType.Limitations)
                {
                    if (benMatrix[x, y].CodeNum != 0)
                    {
                        proccode = ProcedureCodes.GetProcCode(benMatrix[x, y].CodeNum);
                        val += proccode.ProcCode + "-" + proccode.AbbrDesc + " ";
                    }
                    else if (benMatrix[x, y].CovCatNum != 0)
                    {
                        val += CovCats.GetDesc(benMatrix[x, y].CovCatNum) + " ";
                    }
                      
                }
                 
                if (benMatrix[x, y].QuantityQualifier == BenefitQuantity.NumberOfServices)
                {
                    //eg 2 times per CalendarYear
                    val += benMatrix[x, y].Quantity.ToString() + " " + Lan.g(this,"times per") + " " + Lan.g("enumBenefitQuantity", benMatrix[x, y].TimePeriod.ToString()) + " ";
                }
                else if (benMatrix[x, y].QuantityQualifier == BenefitQuantity.Months)
                {
                    //eg Every 2 months
                    val += Lan.g(this,"Every ") + benMatrix[x, y].Quantity.ToString() + " month";
                    if (benMatrix[x, y].Quantity > 1)
                    {
                        val += "s";
                    }
                     
                }
                else if (benMatrix[x, y].QuantityQualifier == BenefitQuantity.Years)
                {
                    //eg Every 2 years
                    val += "Every " + benMatrix[x, y].Quantity.ToString() + " year";
                    if (benMatrix[x, y].Quantity > 1)
                    {
                        val += "s";
                    }
                     
                }
                else
                {
                    if (benMatrix[x, y].QuantityQualifier != BenefitQuantity.None)
                    {
                        //e.g. flo
                        val += Lan.g("enumBenefitQuantity", benMatrix[x, y].QuantityQualifier.ToString()) + " ";
                    }
                     
                    if (benMatrix[x, y].Quantity != 0)
                    {
                        val += benMatrix[x, y].Quantity.ToString() + " ";
                    }
                     
                }   
                //if(benMatrix[x,y].MonetaryAmt!=0){
                //	val+=benMatrix[x,y].MonetaryAmt.ToString("c0")+" ";
                //}
                //if(val==""){
                //	val="val";
                //}
                row.getCells().add(val);
            }
            gridIns.getRows().add(row);
        }
        //Plan note
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Ins Plan Note"));
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            cell = new OpenDental.UI.ODGridCell();
            cell.setText(planArray[i].PlanNote);
            cell.setColorText(Color.Red);
            cell.setBold(YN.Yes);
            row.getCells().Add(cell);
        }
        gridIns.getRows().add(row);
        //Subscriber Note
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add(Lan.g("TableCoverage","Subscriber Note"));
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            cell = new OpenDental.UI.ODGridCell();
            cell.setText(subArray[i].SubscNote);
            cell.setColorText(Color.Red);
            cell.setBold(YN.Yes);
            row.getCells().Add(cell);
        }
        gridIns.getRows().add(row);
        gridIns.endUpdate();
    }

    private void gridIns_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() == 0)
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        PatPlan patPlan = PatPlanList[e.getCol() - 1];
        InsSub insSub = InsSubs.getSub(patPlan.InsSubNum,SubList);
        PlanList = InsPlans.refreshForSubList(SubList);
        //this is only here in case, if in FormModuleSetup, the InsDefaultCobRule is changed and cob changed for all plans.
        InsPlan insPlan = InsPlans.getPlan(insSub.PlanNum,PlanList);
        FormInsPlan FormIP = new FormInsPlan(insPlan,patPlan,insSub);
        FormIP.ShowDialog();
        Cursor = Cursors.Default;
        moduleSelected(PatCur.PatNum);
    }

}


