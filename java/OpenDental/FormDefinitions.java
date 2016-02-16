//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DataValid;
import OpenDental.DefL;
import OpenDental.FormDefEdit;
import OpenDental.FormDefEditImages;
import OpenDental.FormDefinitions;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patients;

/**
* 
*/
public class FormDefinitions  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textGuide = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupEdit = new System.Windows.Forms.GroupBox();
    private OpenDental.TableDefs tbDefs;
    private System.Windows.Forms.ListBox listCategory = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butHide;
    //<summary>This is the index of the selected cat.</summary>
    //private int InitialCat;
    /**
    * this is (int)DefCat, not the index of the selected Cat.
    */
    private int SelectedCat = new int();
    private boolean changed = new boolean();
    /**
    * Gives the DefCat for each item in the list.
    */
    private DefCat[] lookupCat = new DefCat[]();
    //private User user;
    private boolean DefsIsSelected = new boolean();
    private Def[] DefsList = new Def[]();
    private int DefsSelected = new int();
    /**
    * Must check security before allowing this window to open.
    */
    public FormDefinitions(DefCat selectedCat) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        SelectedCat = ((Enum)selectedCat).ordinal();
        tbDefs.CellDoubleClicked = __MultiCellEventHandler.combine(tbDefs.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbDefs_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        tbDefs.CellClicked = __MultiCellEventHandler.combine(tbDefs.CellClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbDefs_CellClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        Lan.f(this);
    }

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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDefinitions.class);
        this.butClose = new OpenDental.UI.Button();
        this.label14 = new System.Windows.Forms.Label();
        this.textGuide = new System.Windows.Forms.TextBox();
        this.groupEdit = new System.Windows.Forms.GroupBox();
        this.butHide = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.tbDefs = new OpenDental.TableDefs();
        this.listCategory = new System.Windows.Forms.ListBox();
        this.label13 = new System.Windows.Forms.Label();
        this.groupEdit.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(686, 638);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(92, 604);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(100, 18);
        this.label14.TabIndex = 22;
        this.label14.Text = "Guidelines";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textGuide
        //
        this.textGuide.Location = new System.Drawing.Point(198, 604);
        this.textGuide.Multiline = true;
        this.textGuide.Name = "textGuide";
        this.textGuide.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textGuide.Size = new System.Drawing.Size(460, 63);
        this.textGuide.TabIndex = 2;
        //
        // groupEdit
        //
        this.groupEdit.Controls.Add(this.butHide);
        this.groupEdit.Controls.Add(this.butDown);
        this.groupEdit.Controls.Add(this.butUp);
        this.groupEdit.Controls.Add(this.butAdd);
        this.groupEdit.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupEdit.Location = new System.Drawing.Point(198, 549);
        this.groupEdit.Name = "groupEdit";
        this.groupEdit.Size = new System.Drawing.Size(460, 51);
        this.groupEdit.TabIndex = 1;
        this.groupEdit.TabStop = false;
        this.groupEdit.Text = "Edit Items";
        //
        // butHide
        //
        this.butHide.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHide.setAutosize(true);
        this.butHide.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHide.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHide.setCornerRadius(4F);
        this.butHide.Location = new System.Drawing.Point(138, 18);
        this.butHide.Name = "butHide";
        this.butHide.Size = new System.Drawing.Size(79, 24);
        this.butHide.TabIndex = 10;
        this.butHide.Text = "&Hide";
        this.butHide.Click += new System.EventHandler(this.butHide_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(346, 18);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(79, 24);
        this.butDown.TabIndex = 9;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(242, 18);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(79, 24);
        this.butUp.TabIndex = 8;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
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
        this.butAdd.Location = new System.Drawing.Point(34, 18);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 6;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // tbDefs
        //
        this.tbDefs.BackColor = System.Drawing.SystemColors.Window;
        this.tbDefs.Location = new System.Drawing.Point(199, 6);
        this.tbDefs.Name = "tbDefs";
        this.tbDefs.setScrollValue(1);
        this.tbDefs.setSelectedIndices(new int[0]);
        this.tbDefs.setSelectionMode(System.Windows.Forms.SelectionMode.None);
        this.tbDefs.Size = new System.Drawing.Size(459, 538);
        this.tbDefs.TabIndex = 19;
        //
        // listCategory
        //
        this.listCategory.Items.AddRange(new Object[]{ "Account Colors", "Adj Types", "Appointment Colors", "Appt Confirmed", "Appt Procs Quick Add", "Billing Types", "Blockout Types", "Chart Graphic Colors", "Claim Custom Tracking", "Commlog Types", "Contact Categories", "Diagnosis", "Image Categories", "Letter Merge Cats", "Misc Colors", "Payment Types", "PaySplit Unearned Types", "Proc Button Categories", "Proc Code Categories", "Prog Notes Colors", "Prognosis", "Recall/Unsch Status", "Supply Categories", "Treat\' Plan Priorities" });
        this.listCategory.Location = new System.Drawing.Point(22, 36);
        this.listCategory.Name = "listCategory";
        this.listCategory.Size = new System.Drawing.Size(147, 316);
        this.listCategory.TabIndex = 0;
        this.listCategory.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listCategory_MouseDown);
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(22, 18);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(162, 17);
        this.label13.TabIndex = 17;
        this.label13.Text = "Select Category:";
        this.label13.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormDefinitions
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(789, 675);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.textGuide);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.groupEdit);
        this.Controls.Add(this.tbDefs);
        this.Controls.Add(this.listCategory);
        this.Controls.Add(this.label13);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDefinitions";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Definitions";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormDefinitions_Closing);
        this.Load += new System.EventHandler(this.FormDefinitions_Load);
        this.groupEdit.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDefinitions_Load(Object sender, System.EventArgs e) throws Exception {
        /*if(PermissionsOld.AuthorizationRequired("Definitions")){
        				user=Users.Authenticate("Definitions");
        				if(!UserPermissions.IsAuthorized("Definitions",user)){
        					MsgBox.Show(this,"You do not have permission for this feature");
        					DialogResult=DialogResult.Cancel;
        					return;
        				}	
        			}*/
        lookupCat = new DefCat[listCategory.Items.Count];
        lookupCat[0] = DefCat.AccountColors;
        lookupCat[1] = DefCat.AdjTypes;
        lookupCat[2] = DefCat.AppointmentColors;
        lookupCat[3] = DefCat.ApptConfirmed;
        lookupCat[4] = DefCat.ApptProcsQuickAdd;
        lookupCat[5] = DefCat.BillingTypes;
        lookupCat[6] = DefCat.BlockoutTypes;
        lookupCat[7] = DefCat.ChartGraphicColors;
        lookupCat[8] = DefCat.ClaimCustomTracking;
        lookupCat[9] = DefCat.CommLogTypes;
        lookupCat[10] = DefCat.ContactCategories;
        lookupCat[11] = DefCat.Diagnosis;
        lookupCat[12] = DefCat.ImageCats;
        lookupCat[13] = DefCat.LetterMergeCats;
        lookupCat[14] = DefCat.MiscColors;
        lookupCat[15] = DefCat.PaymentTypes;
        lookupCat[16] = DefCat.PaySplitUnearnedType;
        lookupCat[17] = DefCat.ProcButtonCats;
        lookupCat[18] = DefCat.ProcCodeCats;
        lookupCat[19] = DefCat.ProgNoteColors;
        lookupCat[20] = DefCat.Prognosis;
        lookupCat[21] = DefCat.RecallUnschedStatus;
        lookupCat[22] = DefCat.SupplyCats;
        lookupCat[23] = DefCat.TxPriorities;
        for (int i = 0;i < listCategory.Items.Count;i++)
        {
            listCategory.Items[i] = Lan.g(this,(String)listCategory.Items[i]);
            if ((int)lookupCat[i] == SelectedCat)
            {
                listCategory.SelectedIndex = i;
            }
             
        }
        fillCats();
    }

    private void listCategory_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        listCategory.SelectedIndex = listCategory.IndexFromPoint(e.X, e.Y);
        //test for -1 only necessary if there is whitespace, which there is not.
        SelectedCat = (int)lookupCat[listCategory.SelectedIndex];
        fillCats();
    }

    private void fillCats() throws Exception {
        //a category is ALWAYS selected; never -1.
        DefsIsSelected = false;
        FormDefEdit.EnableColor = false;
        FormDefEdit.EnableValue = false;
        FormDefEdit.CanEditName = true;
        FormDefEdit.CanDelete = false;
        FormDefEdit.CanHide = true;
        tbDefs.Fields[1] = "";
        FormDefEdit.ValueText = "";
        butHide.Visible = true;
        SelectedIndex __dummyScrutVar0 = listCategory.SelectedIndex;
        if (__dummyScrutVar0.equals(0))
        {
            //"Account Colors":
            //SelectedCat=0;
            FormDefEdit.CanEditName = false;
            FormDefEdit.EnableColor = true;
            FormDefEdit.HelpText = Lan.g(this,"Changes the color of text for different types of entries in Account Module");
        }
        else if (__dummyScrutVar0.equals(1))
        {
            //"Adj Types":
            //SelectedCat=1;
            FormDefEdit.ValueText = Lan.g(this,"+ or -");
            FormDefEdit.EnableValue = true;
            FormDefEdit.HelpText = Lan.g(this,"Plus increases the patient balance.  Minus decreases it.  Not allowed to change value after creating new type since changes affect all patient accounts.");
        }
        else if (__dummyScrutVar0.equals(2))
        {
            //"Appointment Colors":
            //SelectedCat=17;
            FormDefEdit.EnableColor = true;
            FormDefEdit.CanEditName = false;
            FormDefEdit.HelpText = Lan.g(this,"Changes colors of background in Appointments Module, and colors for completed appointments.");
        }
        else if (__dummyScrutVar0.equals(3))
        {
            //"Appt Confirmed":
            //SelectedCat=2;
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"Abbrev");
            FormDefEdit.EnableColor = true;
            //tbDefs.Fields[2]="Color";
            FormDefEdit.HelpText = Lan.g(this,"Color shows on each appointment if Appointment View is set to show ConfirmedColor.");
        }
        else if (__dummyScrutVar0.equals(4))
        {
            //"Appt Procs Quick Add":
            //SelectedCat=3;
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"ADA Code(s)");
            FormDefEdit.HelpText = Lan.g(this,"These are the procedures that you can quickly add to the treatment plan from within the appointment editing window.  They must not require a tooth number. Multiple procedures may be separated by commas with no spaces. These definitions may be freely edited without affecting any patient records.");
        }
        else if (__dummyScrutVar0.equals(5))
        {
            //"Billing Types":
            //SelectedCat=4;
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"E=Email bill");
            FormDefEdit.HelpText = Lan.g(this,"It is recommended to use as few billing types as possible.  They can be useful when running reports to separate delinquent accounts, but can cause 'forgotten accounts' if used without good office procedures. Changes affect all patients.");
        }
        else if (__dummyScrutVar0.equals(6))
        {
            //"Blockout Types":
            FormDefEdit.EnableColor = true;
            FormDefEdit.EnableValue = false;
            FormDefEdit.HelpText = Lan.g(this,"Blockout types are used in the appointments module.");
        }
        else if (__dummyScrutVar0.equals(7))
        {
            //"Chart Graphic Colors":
            //SelectedCat=22;
            FormDefEdit.EnableColor = true;
            FormDefEdit.CanEditName = false;
            FormDefEdit.HelpText = Lan.g(this,"These colors will be used on the graphical tooth chart to draw restorations.");
        }
        else if (__dummyScrutVar0.equals(8))
        {
            //"Custom Tracking":
            butHide.Visible = false;
            FormDefEdit.CanDelete = true;
            FormDefEdit.CanHide = false;
            FormDefEdit.EnableColor = false;
            FormDefEdit.HelpText = Lan.g(this,"Some offices may set up claim tracking statuses such as 'review', 'hold', 'riskmanage', etc.");
        }
        else if (__dummyScrutVar0.equals(9))
        {
            //"Commlog Types"
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"APPT,FIN,RECALL,MISC");
            FormDefEdit.HelpText = Lan.g(this,"Changes affect all current commlog entries.  In the second column, you can optionally specify APPT,FIN,RECALL,or MISC. Only one of each. This helps automate new entries.");
        }
        else if (__dummyScrutVar0.equals(10))
        {
            //"Contact Categories":
            //SelectedCat=(int)DefCat.ContactCategories;
            FormDefEdit.HelpText = Lan.g(this,"You can add as many categories as you want.  Changes affect all current contact records.");
        }
        else if (__dummyScrutVar0.equals(11))
        {
            //"Diagnosis":
            //SelectedCat=16;
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"1 or 2 letter abbreviation");
            FormDefEdit.HelpText = Lan.g(this,"The diagnosis list is shown when entering a procedure.  Ones that are less used should go lower on the list.  The abbreviation is shown in the progress notes.  BE VERY CAREFUL.  Changes affect all patients.");
        }
        else if (__dummyScrutVar0.equals(12))
        {
            //"Image Categories":
            //SelectedCat=18;
            //FormDefEdit.EnableValue=true;
            FormDefEdit.ValueText = Lan.g(this,"Usage");
            FormDefEdit.HelpText = Lan.g(this,"These are the categories that will be available in the image and chart modules.  If you hide a category, images in that category will be hidden, so only hide a category if you are certain it has never been used.  Multiple categories can be set to show in the Chart module, but only one category should set for patient pictures, statements, and tooth chart. Affects all patient records.");
        }
        else if (__dummyScrutVar0.equals(13))
        {
            //"Letter Merge Cats"
            //SelectedCat=(int)DefCat.LetterMergeCats;
            FormDefEdit.HelpText = Lan.g(this,"Categories for Letter Merge.  You can safely make any changes you want.");
        }
        else if (__dummyScrutVar0.equals(14))
        {
            //"Misc Colors":
            //SelectedCat=21;
            FormDefEdit.EnableColor = true;
            FormDefEdit.CanEditName = false;
            FormDefEdit.HelpText = "";
        }
        else if (__dummyScrutVar0.equals(15))
        {
            //"Payment Types":
            //SelectedCat=10;
            FormDefEdit.HelpText = Lan.g(this,"Types of payments that patients might make. Any changes will affect all patients.");
        }
        else if (__dummyScrutVar0.equals(16))
        {
            //paysplit unearned types
            FormDefEdit.HelpText = Lan.g(this,"Usually only used by offices that use accrual basis accounting instead of cash basis accounting. Any changes will affect all patients.");
        }
        else if (__dummyScrutVar0.equals(17))
        {
            //"Proc Button Categories":
            FormDefEdit.HelpText = Lan.g(this,"These are similar to the procedure code categories, but are only used for organizing and grouping the procedure buttons in the Chart module.");
        }
        else if (__dummyScrutVar0.equals(18))
        {
            //"Proc Code Categories":
            //SelectedCat=11;
            FormDefEdit.HelpText = Lan.g(this,"These are the categories for organizing procedure codes. They do not have to follow ADA categories.  There is no relationship to insurance categories which are setup in the Ins Categories section.  Does not affect any patient records.");
        }
        else if (__dummyScrutVar0.equals(19))
        {
            //"Prog Notes Colors":
            //SelectedCat=12;
            FormDefEdit.EnableColor = true;
            FormDefEdit.CanEditName = false;
            FormDefEdit.HelpText = Lan.g(this,"Changes color of text for different types of entries in the Chart Module Progress Notes.");
        }
        else if (__dummyScrutVar0.equals(20))
        {
            //"Prognosis":
            //Nothing special. Might add HelpText later.
            FormDefEdit.HelpText = Lan.g(this,"");
        }
        else if (__dummyScrutVar0.equals(21))
        {
            //"Recall/Unsch Status":
            //SelectedCat=13;
            FormDefEdit.EnableValue = true;
            FormDefEdit.ValueText = Lan.g(this,"Abbreviation");
            FormDefEdit.HelpText = Lan.g(this,"Recall/Unsched Status.  Abbreviation must be 7 characters or less.  Changes affect all patients.");
        }
        else if (__dummyScrutVar0.equals(22))
        {
            //Supply Categories
            butHide.Visible = false;
            FormDefEdit.CanDelete = true;
            FormDefEdit.CanHide = false;
            FormDefEdit.HelpText = Lan.g(this,"The categories for inventory supplies.");
        }
        else if (__dummyScrutVar0.equals(23))
        {
            //"Treat' Plan Priorities":
            //SelectedCat=20;
            FormDefEdit.EnableColor = true;
            FormDefEdit.HelpText = Lan.g(this,"Priorities available for selection in the Treatment Plan module.  They can be simple numbers or descriptive abbreviations 7 letters or less.  Changes affect all procedures where the definition is used.");
        }
                                
        fillDefs();
    }

    private void fillDefs() throws Exception {
        //Defs.IsSelected=false;
        int scroll = tbDefs.getScrollValue();
        DefsList = Defs.getCatList(SelectedCat);
        tbDefs.ResetRows(DefsList.Length);
        tbDefs.SetBackGColor(Color.White);
        for (int i = 0;i < DefsList.Length;i++)
        {
            tbDefs.Cell[0, i] = DefsList[i].ItemName;
            if (lookupCat[listCategory.SelectedIndex] == DefCat.ImageCats)
            {
                tbDefs.Cell[1, i] = GetItemDescForImages(DefsList[i].ItemValue);
            }
            else
            {
                tbDefs.Cell[1, i] = DefsList[i].ItemValue;
            } 
            if (FormDefEdit.EnableColor)
            {
                tbDefs.BackGColor[2, i] = DefsList[i].ItemColor;
            }
             
            if (DefsList[i].IsHidden)
            {
                tbDefs.Cell[3, i] = "X";
            }
             
        }
        //else tbDefs.Cell[3,i]="";
        if (DefsSelected > DefsList.Length - 1)
        {
            DefsSelected = -1;
            DefsIsSelected = false;
        }
         
        if (DefsIsSelected)
        {
            tbDefs.BackGColor[0, DefsSelected] = Color.LightGray;
            tbDefs.BackGColor[1, DefsSelected] = Color.LightGray;
        }
         
        tbDefs.Fields[1] = FormDefEdit.ValueText;
        if (FormDefEdit.EnableColor)
        {
            tbDefs.Fields[2] = "Color";
        }
        else
        {
            tbDefs.Fields[2] = "";
        } 
        tbDefs.layoutTables();
        tbDefs.setScrollValue(scroll);
        //the following do not require a refresh of the table:
        if (FormDefEdit.CanEditName)
        {
            groupEdit.Enabled = true;
            groupEdit.Text = "Edit Items";
        }
        else
        {
            groupEdit.Enabled = false;
            groupEdit.Text = "Not allowed";
        } 
        textGuide.Text = FormDefEdit.HelpText;
    }

    private String getItemDescForImages(String itemValue) throws Exception {
        String retVal = "";
        if (itemValue.Contains("X"))
        {
            retVal = Lan.g(this,"ChartModule");
        }
         
        if (itemValue.Contains("F"))
        {
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += Lan.g(this,"PatientForm");
        }
         
        if (itemValue.Contains("P"))
        {
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += Lan.g(this,"PatientPic");
        }
         
        if (itemValue.Contains("S"))
        {
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += Lan.g(this,"Statement");
        }
         
        if (itemValue.Contains("T"))
        {
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += Lan.g(this,"ToothChart");
        }
         
        return retVal;
    }

    private void tbDefs_CellClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        if (tbDefs.Cell.GetLength(1) == 0)
        {
            return ;
        }
         
        //Last row was deleted.
        //Can't move this logic into the Table control because we never want to paint on col 3
        if (DefsIsSelected)
        {
            if (DefsSelected == e.getRow())
            {
                tbDefs.BackGColor[0, e.getRow()] = Color.White;
                tbDefs.BackGColor[1, e.getRow()] = Color.White;
                DefsIsSelected = false;
            }
            else
            {
                tbDefs.BackGColor[0, DefsSelected] = Color.White;
                tbDefs.BackGColor[1, DefsSelected] = Color.White;
                tbDefs.BackGColor[0, e.getRow()] = Color.LightGray;
                tbDefs.BackGColor[1, e.getRow()] = Color.LightGray;
                DefsSelected = e.getRow();
                DefsIsSelected = true;
            } 
        }
        else
        {
            tbDefs.BackGColor[0, e.getRow()] = Color.LightGray;
            tbDefs.BackGColor[1, e.getRow()] = Color.LightGray;
            DefsSelected = e.getRow();
            DefsIsSelected = true;
        } 
        tbDefs.Refresh();
    }

    private void tbDefs_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        tbDefs.BackGColor[0, e.getRow()] = SystemColors.Highlight;
        tbDefs.BackGColor[1, e.getRow()] = SystemColors.Highlight;
        tbDefs.Refresh();
        DefsIsSelected = true;
        DefsSelected = e.getRow();
        if (lookupCat[listCategory.SelectedIndex] == DefCat.ImageCats)
        {
            FormDefEditImages FormDEI = new FormDefEditImages(DefsList[e.getRow()]);
            FormDEI.IsNew = false;
            FormDEI.ShowDialog();
        }
        else
        {
            FormDefEdit FormDefEdit2 = new FormDefEdit(DefsList[e.getRow()]);
            FormDefEdit2.IsNew = false;
            FormDefEdit2.ShowDialog();
        } 
        //Preferences2.GetCatList(listCategory.SelectedIndex);
        changed = true;
        fillDefs();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Def DefCur = new Def();
        DefCur.ItemOrder = DefsList.Length;
        DefCur.Category = DefCat.values()[SelectedCat];
        DefCur.ItemName = "";
        DefCur.ItemValue = "";
        //necessary
        if (lookupCat[listCategory.SelectedIndex] == DefCat.ImageCats)
        {
            FormDefEditImages FormDEI = new FormDefEditImages(DefCur);
            FormDEI.IsNew = true;
            FormDEI.ShowDialog();
            if (FormDEI.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else
        {
            FormDefEdit FormDE = new FormDefEdit(DefCur);
            FormDE.IsNew = true;
            FormDE.ShowDialog();
            if (FormDE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        } 
        DefsSelected = DefsList.Length;
        //this is one more than allowed, but it's ok
        DefsIsSelected = true;
        changed = true;
        fillDefs();
    }

    private void butHide_Click(Object sender, System.EventArgs e) throws Exception {
        if (!DefsIsSelected)
        {
            MessageBox.Show(Lan.g(this,"Please select item first,"));
            return ;
        }
         
        //Warn the user if they are about to hide a billing type currently in use.
        if (DefsList[DefsSelected].Category == DefCat.BillingTypes && Patients.IsBillingTypeInUse(DefsList[DefsSelected].DefNum))
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning: Billing type is currently in use by patients."))
            {
                return ;
            }
             
        }
         
        Defs.HideDef(DefsList[DefsSelected]);
        changed = true;
        fillDefs();
    }

    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        DefsSelected = DefL.moveUp(DefsIsSelected,DefsSelected,DefsList);
        changed = true;
        fillDefs();
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        DefsSelected = DefL.moveDown(DefsIsSelected,DefsSelected,DefsList);
        changed = true;
        fillDefs();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formDefinitions_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Defs,InvalidType.Fees);
        }
         
        DefsIsSelected = false;
    }

}


//if(user!=null){
//SecurityLogs.MakeLogEntry("Definitions","Altered Definitions",user);
//}