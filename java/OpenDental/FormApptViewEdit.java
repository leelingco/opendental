//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ApptViewItemL;
import OpenDental.FormApptViewEdit;
import OpenDental.FormApptViewItemEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewAlignment;
import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ApptViewItems;
import OpenDentBusiness.ApptViews;
import OpenDentBusiness.ApptViewStackBehavior;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.ProviderC;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptViewEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelOps = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listOps = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listProv = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butLeft;
    private OpenDental.UI.Button butRight;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * A collection of strings of all available element descriptions.  This memory list is in English and is stored in db.  Items from this list get translated before display on in this window.
    */
    private List<String> elementsAll = new List<String>();
    /**
    * A list of indices to elementsAll.  Those elements which are showing in the list of available elements.  It must be done this way to support language translation.
    */
    private List<int> displayedAvailable = new List<int>();
    /**
    * The actual ApptFieldDefNums of all available elements because no language translation is needed.
    */
    private List<long> displayedAvailableApptFieldDefs = new List<long>();
    /**
    * The actual PatFieldDefNums of all available elements because no language translation is needed.
    */
    private List<long> displayedAvailablePatFieldDefs = new List<long>();
    private System.Windows.Forms.ColorDialog colorDialog1 = new System.Windows.Forms.ColorDialog();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textRowsPerIncr = new System.Windows.Forms.TextBox();
    /**
    * A local list of ApptViewItems which are displayed in all three lists on the right.  Not updated to db until the form is closed.
    */
    private List<ApptViewItem> displayedElementsAll = new List<ApptViewItem>();
    private List<ApptViewItem> displayedElementsMain = new List<ApptViewItem>();
    private List<ApptViewItem> displayedElementsUR = new List<ApptViewItem>();
    private List<ApptViewItem> displayedElementsLR = new List<ApptViewItem>();
    private CheckBox checkOnlyScheduledProvs = new CheckBox();
    private TextBox textBeforeTime = new TextBox();
    private GroupBox groupBox1 = new GroupBox();
    private Label labelBeforeTime = new Label();
    private Label labelAfterTime = new Label();
    private TextBox textAfterTime = new TextBox();
    private GroupBox groupBox2 = new GroupBox();
    private Label label8 = new Label();
    private OpenDental.UI.ODGrid gridLR;
    private OpenDental.UI.ODGrid gridUR;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.ODGrid gridAvailable;
    private ListBox listStackLR = new ListBox();
    private Label label4 = new Label();
    private ListBox listStackUR = new ListBox();
    private Label label1 = new Label();
    private OpenDental.UI.ODGrid gridApptFieldDefs;
    private OpenDental.UI.ODGrid gridPatFieldDefs;
    private Label labelClinic = new Label();
    private ComboBox comboClinic = new ComboBox();
    /**
    * Set this value before opening the form.
    */
    public ApptView ApptViewCur;
    //<summary>Tracks MouseIsDown on listOps.</summary>
    //private bool MouseIsDown;
    /**
    * 
    */
    public FormApptViewEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptViewEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.labelOps = new System.Windows.Forms.Label();
        this.listOps = new System.Windows.Forms.ListBox();
        this.listProv = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.butRight = new OpenDental.UI.Button();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.label6 = new System.Windows.Forms.Label();
        this.textRowsPerIncr = new System.Windows.Forms.TextBox();
        this.checkOnlyScheduledProvs = new System.Windows.Forms.CheckBox();
        this.textBeforeTime = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.labelAfterTime = new System.Windows.Forms.Label();
        this.textAfterTime = new System.Windows.Forms.TextBox();
        this.labelBeforeTime = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.listStackLR = new System.Windows.Forms.ListBox();
        this.label4 = new System.Windows.Forms.Label();
        this.listStackUR = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.gridLR = new OpenDental.UI.ODGrid();
        this.gridUR = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label8 = new System.Windows.Forms.Label();
        this.gridAvailable = new OpenDental.UI.ODGrid();
        this.gridApptFieldDefs = new OpenDental.UI.ODGrid();
        this.gridPatFieldDefs = new OpenDental.UI.ODGrid();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(752, 663);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(652, 663);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
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
        this.butDelete.Location = new System.Drawing.Point(32, 663);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(87, 24);
        this.butDelete.TabIndex = 38;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // labelOps
        //
        this.labelOps.Location = new System.Drawing.Point(32, 149);
        this.labelOps.Name = "labelOps";
        this.labelOps.Size = new System.Drawing.Size(184, 35);
        this.labelOps.TabIndex = 39;
        this.labelOps.Text = "View Operatories";
        this.labelOps.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listOps
        //
        this.listOps.Location = new System.Drawing.Point(32, 186);
        this.listOps.Name = "listOps";
        this.listOps.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listOps.Size = new System.Drawing.Size(120, 186);
        this.listOps.TabIndex = 40;
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(32, 401);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(120, 212);
        this.listProv.TabIndex = 42;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(32, 376);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(128, 23);
        this.label2.TabIndex = 41;
        this.label2.Text = "View Provider Bars";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(110, 10);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(187, 18);
        this.label3.TabIndex = 43;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(298, 11);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(250, 20);
        this.textDescription.TabIndex = 44;
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
        this.butDown.Location = new System.Drawing.Point(297, 475);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(71, 24);
        this.butDown.TabIndex = 50;
        this.butDown.Text = "&Down";
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
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(219, 475);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(71, 24);
        this.butUp.TabIndex = 51;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(-1, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(404, 312);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(35, 26);
        this.butLeft.TabIndex = 52;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(404, 278);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(35, 26);
        this.butRight.TabIndex = 53;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(51, 31);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(246, 18);
        this.label6.TabIndex = 54;
        this.label6.Text = "Rows Per Time Increment (usually 1)";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textRowsPerIncr
        //
        this.textRowsPerIncr.Location = new System.Drawing.Point(298, 33);
        this.textRowsPerIncr.Name = "textRowsPerIncr";
        this.textRowsPerIncr.Size = new System.Drawing.Size(56, 20);
        this.textRowsPerIncr.TabIndex = 55;
        this.textRowsPerIncr.Validating += new System.ComponentModel.CancelEventHandler(this.textRowsPerIncr_Validating);
        //
        // checkOnlyScheduledProvs
        //
        this.checkOnlyScheduledProvs.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkOnlyScheduledProvs.Location = new System.Drawing.Point(6, 11);
        this.checkOnlyScheduledProvs.Name = "checkOnlyScheduledProvs";
        this.checkOnlyScheduledProvs.Size = new System.Drawing.Size(282, 18);
        this.checkOnlyScheduledProvs.TabIndex = 56;
        this.checkOnlyScheduledProvs.Text = "Only show operatories for scheduled providers";
        this.checkOnlyScheduledProvs.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkOnlyScheduledProvs.UseVisualStyleBackColor = true;
        this.checkOnlyScheduledProvs.Click += new System.EventHandler(this.checkOnlyScheduledProvs_Click);
        //
        // textBeforeTime
        //
        this.textBeforeTime.Location = new System.Drawing.Point(263, 30);
        this.textBeforeTime.Name = "textBeforeTime";
        this.textBeforeTime.Size = new System.Drawing.Size(56, 20);
        this.textBeforeTime.TabIndex = 57;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelClinic);
        this.groupBox1.Controls.Add(this.labelAfterTime);
        this.groupBox1.Controls.Add(this.textAfterTime);
        this.groupBox1.Controls.Add(this.labelBeforeTime);
        this.groupBox1.Controls.Add(this.checkOnlyScheduledProvs);
        this.groupBox1.Controls.Add(this.textBeforeTime);
        this.groupBox1.Location = new System.Drawing.Point(35, 49);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(398, 101);
        this.groupBox1.TabIndex = 58;
        this.groupBox1.TabStop = false;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(77, 74);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(187, 17);
        this.labelClinic.TabIndex = 61;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // labelAfterTime
        //
        this.labelAfterTime.Location = new System.Drawing.Point(77, 52);
        this.labelAfterTime.Name = "labelAfterTime";
        this.labelAfterTime.Size = new System.Drawing.Size(187, 17);
        this.labelAfterTime.TabIndex = 60;
        this.labelAfterTime.Text = "Only if after time";
        this.labelAfterTime.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textAfterTime
        //
        this.textAfterTime.Location = new System.Drawing.Point(263, 52);
        this.textAfterTime.Name = "textAfterTime";
        this.textAfterTime.Size = new System.Drawing.Size(56, 20);
        this.textAfterTime.TabIndex = 59;
        //
        // labelBeforeTime
        //
        this.labelBeforeTime.Location = new System.Drawing.Point(77, 30);
        this.labelBeforeTime.Name = "labelBeforeTime";
        this.labelBeforeTime.Size = new System.Drawing.Size(187, 17);
        this.labelBeforeTime.TabIndex = 58;
        this.labelBeforeTime.Text = "Only if before time";
        this.labelBeforeTime.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.listStackLR);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.listStackUR);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.gridLR);
        this.groupBox2.Controls.Add(this.gridUR);
        this.groupBox2.Controls.Add(this.gridMain);
        this.groupBox2.Controls.Add(this.butUp);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.butDown);
        this.groupBox2.Location = new System.Drawing.Point(449, 144);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(378, 507);
        this.groupBox2.TabIndex = 59;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Rows Displayed (double click to edit or to move to another corner)";
        //
        // listStackLR
        //
        this.listStackLR.FormattingEnabled = true;
        this.listStackLR.Location = new System.Drawing.Point(192, 315);
        this.listStackLR.Name = "listStackLR";
        this.listStackLR.Size = new System.Drawing.Size(175, 30);
        this.listStackLR.TabIndex = 66;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(190, 296);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(175, 17);
        this.label4.TabIndex = 65;
        this.label4.Text = "LR stack behavior";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listStackUR
        //
        this.listStackUR.FormattingEnabled = true;
        this.listStackUR.Location = new System.Drawing.Point(192, 177);
        this.listStackUR.Name = "listStackUR";
        this.listStackUR.Size = new System.Drawing.Size(175, 30);
        this.listStackUR.TabIndex = 64;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(190, 158);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(175, 17);
        this.label1.TabIndex = 63;
        this.label1.Text = "UR stack behavior";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridLR
        //
        this.gridLR.setHScrollVisible(false);
        this.gridLR.Location = new System.Drawing.Point(192, 349);
        this.gridLR.Name = "gridLR";
        this.gridLR.setScrollValue(0);
        this.gridLR.Size = new System.Drawing.Size(175, 120);
        this.gridLR.TabIndex = 62;
        this.gridLR.setTitle("Lower Right Corner");
        this.gridLR.setTranslationName(null);
        this.gridLR.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridLR.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridLR_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridLR.CellClick = __MultiODGridClickEventHandler.combine(this.gridLR.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridLR_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridUR
        //
        this.gridUR.setHScrollVisible(false);
        this.gridUR.Location = new System.Drawing.Point(192, 18);
        this.gridUR.Name = "gridUR";
        this.gridUR.setScrollValue(0);
        this.gridUR.Size = new System.Drawing.Size(175, 138);
        this.gridUR.TabIndex = 61;
        this.gridUR.setTitle("Upper Right Corner");
        this.gridUR.setTranslationName(null);
        this.gridUR.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridUR.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridUR_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridUR.CellClick = __MultiODGridClickEventHandler.combine(this.gridUR.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridUR_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(11, 18);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(175, 451);
        this.gridMain.TabIndex = 60;
        this.gridMain.setTitle("Main List");
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
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(11, 478);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(209, 17);
        this.label8.TabIndex = 59;
        this.label8.Text = "Move any item within its own list:";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridAvailable
        //
        this.gridAvailable.setHScrollVisible(false);
        this.gridAvailable.Location = new System.Drawing.Point(222, 162);
        this.gridAvailable.Name = "gridAvailable";
        this.gridAvailable.setScrollValue(0);
        this.gridAvailable.Size = new System.Drawing.Size(175, 269);
        this.gridAvailable.TabIndex = 61;
        this.gridAvailable.setTitle("Available Rows");
        this.gridAvailable.setTranslationName(null);
        this.gridAvailable.CellClick = __MultiODGridClickEventHandler.combine(this.gridAvailable.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAvailable_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridApptFieldDefs
        //
        this.gridApptFieldDefs.setHScrollVisible(false);
        this.gridApptFieldDefs.Location = new System.Drawing.Point(222, 434);
        this.gridApptFieldDefs.Name = "gridApptFieldDefs";
        this.gridApptFieldDefs.setScrollValue(0);
        this.gridApptFieldDefs.Size = new System.Drawing.Size(175, 88);
        this.gridApptFieldDefs.TabIndex = 62;
        this.gridApptFieldDefs.setTitle("Appt Field Defs");
        this.gridApptFieldDefs.setTranslationName(null);
        this.gridApptFieldDefs.CellClick = __MultiODGridClickEventHandler.combine(this.gridApptFieldDefs.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridApptFieldDefs_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridPatFieldDefs
        //
        this.gridPatFieldDefs.setHScrollVisible(false);
        this.gridPatFieldDefs.Location = new System.Drawing.Point(222, 525);
        this.gridPatFieldDefs.Name = "gridPatFieldDefs";
        this.gridPatFieldDefs.setScrollValue(0);
        this.gridPatFieldDefs.Size = new System.Drawing.Size(175, 88);
        this.gridPatFieldDefs.TabIndex = 63;
        this.gridPatFieldDefs.setTitle("Patient Field Defs");
        this.gridPatFieldDefs.setTranslationName(null);
        this.gridPatFieldDefs.CellClick = __MultiODGridClickEventHandler.combine(this.gridPatFieldDefs.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPatFieldDefs_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(298, 123);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(129, 21);
        this.comboClinic.TabIndex = 131;
        //
        // FormApptViewEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(852, 696);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.gridPatFieldDefs);
        this.Controls.Add(this.gridApptFieldDefs);
        this.Controls.Add(this.gridAvailable);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.textRowsPerIncr);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butRight);
        this.Controls.Add(this.butLeft);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listOps);
        this.Controls.Add(this.labelOps);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptViewEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment View Edit";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormApptViewEdit_Closing);
        this.Load += new System.EventHandler(this.FormApptViewEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formApptViewEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = ApptViewCur.Description;
        if (ApptViewCur.RowsPerIncr == 0)
        {
            textRowsPerIncr.Text = "1";
        }
        else
        {
            textRowsPerIncr.Text = ApptViewCur.RowsPerIncr.ToString();
        } 
        checkOnlyScheduledProvs.Checked = ApptViewCur.OnlyScheduledProvs;
        if (ApptViewCur.OnlySchedBeforeTime > new TimeSpan(0, 0, 0))
        {
            textBeforeTime.Text = (DateTime.Today + ApptViewCur.OnlySchedBeforeTime).ToShortTimeString();
        }
         
        if (ApptViewCur.OnlySchedAfterTime > new TimeSpan(0, 0, 0))
        {
            textAfterTime.Text = (DateTime.Today + ApptViewCur.OnlySchedAfterTime).ToShortTimeString();
        }
         
        Clinics.refreshCache();
        comboClinic.Items.Add("All");
        comboClinic.SelectedIndex = 0;
        for (int i = 0;i < Clinics.getList().Length;i++)
        {
            comboClinic.Items.Add(Clinics.getList()[i].Description);
            if (Clinics.getList()[i].ClinicNum == ApptViewCur.ClinicNum)
            {
                comboClinic.SelectedIndex = i + 1;
            }
             
        }
        setOpLabel();
        ApptViewItemL.getForCurView(ApptViewCur,true,null);
        for (int i = 0;i < OperatoryC.getListShort().Count;i++)
        {
            //passing in true triggers it to give us the proper list of ops.
            listOps.Items.Add(OperatoryC.getListShort()[i].OpName);
            if (ApptViewItemL.OpIsInView(OperatoryC.getListShort()[i].OperatoryNum))
            {
                listOps.SetSelected(i, true);
            }
             
        }
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (ApptViewItemL.ProvIsInView(ProviderC.getListShort()[i].ProvNum))
            {
                listProv.SetSelected(i, true);
            }
             
        }
        for (int i = 0;i < Enum.GetNames(ApptViewStackBehavior.class).Length;i++)
        {
            listStackUR.Items.Add(Lan.g("enumApptViewStackBehavior", Enum.GetNames(ApptViewStackBehavior.class)[i]));
            listStackLR.Items.Add(Lan.g("enumApptViewStackBehavior", Enum.GetNames(ApptViewStackBehavior.class)[i]));
        }
        listStackUR.SelectedIndex = ((Enum)ApptViewCur.StackBehavUR).ordinal();
        listStackLR.SelectedIndex = ((Enum)ApptViewCur.StackBehavLR).ordinal();
        elementsAll = new List<String>();
        elementsAll.Add("Address");
        elementsAll.Add("AddrNote");
        elementsAll.Add("Age");
        elementsAll.Add("ASAP");
        elementsAll.Add("ASAP[A]");
        elementsAll.Add("AssistantAbbr");
        elementsAll.Add("ChartNumAndName");
        elementsAll.Add("ChartNumber");
        elementsAll.Add("ConfirmedColor");
        elementsAll.Add("CreditType");
        elementsAll.Add("Guardians");
        elementsAll.Add("HasIns[I]");
        elementsAll.Add("HmPhone");
        elementsAll.Add("InsToSend[!]");
        elementsAll.Add("Lab");
        elementsAll.Add("MedOrPremed[+]");
        elementsAll.Add("MedUrgNote");
        elementsAll.Add("Note");
        elementsAll.Add("PatientName");
        elementsAll.Add("PatientNameF");
        elementsAll.Add("PatNum");
        elementsAll.Add("PatNumAndName");
        elementsAll.Add("PremedFlag");
        elementsAll.Add("Procs");
        elementsAll.Add("ProcsColored");
        elementsAll.Add("Production");
        elementsAll.Add("Provider");
        elementsAll.Add("TimeAskedToArrive");
        elementsAll.Add("WirelessPhone");
        elementsAll.Add("WkPhone");
        displayedElementsAll = new List<ApptViewItem>();
        for (int i = 0;i < ApptViewItemL.ApptRows.Count;i++)
        {
            displayedElementsAll.Add(ApptViewItemL.ApptRows[i]);
        }
        fillElements();
    }

    /**
    * Fills the five lists based on the displayedElements lists. No database transactions are performed here.
    */
    private void fillElements() throws Exception {
        displayedElementsMain = new List<ApptViewItem>();
        displayedElementsUR = new List<ApptViewItem>();
        displayedElementsLR = new List<ApptViewItem>();
        for (int i = 0;i < displayedElementsAll.Count;i++)
        {
            if (displayedElementsAll[i].ElementAlignment == ApptViewAlignment.Main)
            {
                displayedElementsMain.Add(displayedElementsAll[i]);
            }
            else if (displayedElementsAll[i].ElementAlignment == ApptViewAlignment.UR)
            {
                displayedElementsUR.Add(displayedElementsAll[i]);
            }
            else if (displayedElementsAll[i].ElementAlignment == ApptViewAlignment.LR)
            {
                displayedElementsLR.Add(displayedElementsAll[i]);
            }
               
        }
        //Now fill the lists on the screen--------------------------------------------------
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < displayedElementsMain.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (displayedElementsMain[i].ApptFieldDefNum > 0)
            {
                row.getCells().Add(ApptFieldDefs.GetFieldName(displayedElementsMain[i].ApptFieldDefNum));
            }
            else if (displayedElementsMain[i].PatFieldDefNum > 0)
            {
                row.getCells().Add(PatFieldDefs.GetFieldName(displayedElementsMain[i].PatFieldDefNum));
            }
            else
            {
                row.getCells().Add(displayedElementsMain[i].ElementDesc);
            }  
            if (StringSupport.equals(displayedElementsMain[i].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(displayedElementsMain[i].ElementDesc, "HasIns[I]") || StringSupport.equals(displayedElementsMain[i].ElementDesc, "InsToSend[!]"))
            {
                row.setColorBackG(displayedElementsMain[i].ElementColor);
            }
            else
            {
                row.setColorText(displayedElementsMain[i].ElementColor);
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        //gridUR---------------------------------------------------------
        gridUR.beginUpdate();
        gridUR.getColumns().Clear();
        col = new ODGridColumn("",100);
        gridUR.getColumns().add(col);
        gridUR.getRows().Clear();
        for (int i = 0;i < displayedElementsUR.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (displayedElementsUR[i].ApptFieldDefNum > 0)
            {
                row.getCells().Add(ApptFieldDefs.GetFieldName(displayedElementsUR[i].ApptFieldDefNum));
            }
            else if (displayedElementsUR[i].PatFieldDefNum > 0)
            {
                row.getCells().Add(PatFieldDefs.GetFieldName(displayedElementsUR[i].PatFieldDefNum));
            }
            else
            {
                row.getCells().Add(displayedElementsUR[i].ElementDesc);
            }  
            if (StringSupport.equals(displayedElementsUR[i].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(displayedElementsUR[i].ElementDesc, "HasIns[I]") || StringSupport.equals(displayedElementsUR[i].ElementDesc, "InsToSend[!]"))
            {
                row.setColorBackG(displayedElementsUR[i].ElementColor);
            }
            else
            {
                row.setColorText(displayedElementsUR[i].ElementColor);
            } 
            gridUR.getRows().add(row);
        }
        gridUR.endUpdate();
        //gridLR-----------------------------------------------------------
        gridLR.beginUpdate();
        gridLR.getColumns().Clear();
        col = new ODGridColumn("",100);
        gridLR.getColumns().add(col);
        gridLR.getRows().Clear();
        for (int i = 0;i < displayedElementsLR.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (displayedElementsLR[i].ApptFieldDefNum > 0)
            {
                row.getCells().Add(ApptFieldDefs.GetFieldName(displayedElementsLR[i].ApptFieldDefNum));
            }
            else if (displayedElementsLR[i].PatFieldDefNum > 0)
            {
                row.getCells().Add(PatFieldDefs.GetFieldName(displayedElementsLR[i].PatFieldDefNum));
            }
            else
            {
                row.getCells().Add(displayedElementsLR[i].ElementDesc);
            }  
            if (StringSupport.equals(displayedElementsLR[i].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(displayedElementsLR[i].ElementDesc, "HasIns[I]") || StringSupport.equals(displayedElementsLR[i].ElementDesc, "InsToSend[!]"))
            {
                row.setColorBackG(displayedElementsLR[i].ElementColor);
            }
            else
            {
                row.setColorText(displayedElementsLR[i].ElementColor);
            } 
            gridLR.getRows().add(row);
        }
        gridLR.endUpdate();
        //gridAvailable-----------------------------------------------------------
        gridAvailable.beginUpdate();
        gridAvailable.getColumns().Clear();
        col = new ODGridColumn("",100);
        gridAvailable.getColumns().add(col);
        gridAvailable.getRows().Clear();
        displayedAvailable = new List<int>();
        for (int i = 0;i < elementsAll.Count;i++)
        {
            if (!ElementIsDisplayed(elementsAll[i]))
            {
                displayedAvailable.Add(i);
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(Lan.g(this, elementsAll[i]));
                gridAvailable.getRows().add(row);
            }
             
        }
        gridAvailable.endUpdate();
        //gridApptFieldDefs-----------------------------------------------------------
        gridApptFieldDefs.beginUpdate();
        gridApptFieldDefs.getColumns().Clear();
        col = new ODGridColumn("",100);
        gridApptFieldDefs.getColumns().add(col);
        gridApptFieldDefs.getRows().Clear();
        displayedAvailableApptFieldDefs = new List<long>();
        for (int i = 0;i < ApptFieldDefs.getListt().Count;i++)
        {
            if (!ApptFieldIsDisplayed(ApptFieldDefs.getListt()[i].ApptFieldDefNum))
            {
                displayedAvailableApptFieldDefs.Add(ApptFieldDefs.getListt()[i].ApptFieldDefNum);
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(ApptFieldDefs.getListt()[i].FieldName);
                gridApptFieldDefs.getRows().add(row);
            }
             
        }
        gridApptFieldDefs.endUpdate();
        //gridPatFieldDefs-----------------------------------------------------------
        gridPatFieldDefs.beginUpdate();
        gridPatFieldDefs.getColumns().Clear();
        col = new ODGridColumn("",100);
        gridPatFieldDefs.getColumns().add(col);
        gridPatFieldDefs.getRows().Clear();
        displayedAvailablePatFieldDefs = new List<long>();
        for (int i = 0;i < PatFieldDefs.getList().Length;i++)
        {
            if (!PatFieldIsDisplayed(PatFieldDefs.getList()[i].PatFieldDefNum))
            {
                displayedAvailablePatFieldDefs.Add(PatFieldDefs.getList()[i].PatFieldDefNum);
                row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(PatFieldDefs.getList()[i].FieldName);
                gridPatFieldDefs.getRows().add(row);
            }
             
        }
        gridPatFieldDefs.endUpdate();
    }

    /**
    * Called from FillElements. Used to determine whether a given element is already displayed. If not, then it is displayed in the available rows on the left.
    */
    private boolean elementIsDisplayed(String elementDesc) throws Exception {
        for (int i = 0;i < displayedElementsAll.Count;i++)
        {
            if (StringSupport.equals(displayedElementsAll[i].ElementDesc, elementDesc))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Called from FillElements. Used to determine whether a apptfield is already displayed. If not, then it is displayed in the apptFieldDef rows on the left.
    */
    private boolean apptFieldIsDisplayed(long apptFieldDefNum) throws Exception {
        for (int i = 0;i < displayedElementsAll.Count;i++)
        {
            if (displayedElementsAll[i].ApptFieldDefNum == apptFieldDefNum)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Called from FillElements. Used to determine whether a PatFieldDef is already displayed. If not, then it is displayed in the patFieldDef rows on the left.
    */
    private boolean patFieldIsDisplayed(long patFieldDefNum) throws Exception {
        for (int i = 0;i < displayedElementsAll.Count;i++)
        {
            if (displayedElementsAll[i].PatFieldDefNum == patFieldDefNum)
            {
                return true;
            }
             
        }
        return false;
    }

    private void checkOnlyScheduledProvs_Click(Object sender, EventArgs e) throws Exception {
        setOpLabel();
    }

    private void setOpLabel() throws Exception {
        if (checkOnlyScheduledProvs.Checked)
        {
            labelOps.Text = Lan.g(this,"View Operatories (week view only)");
            labelBeforeTime.Visible = true;
            labelAfterTime.Visible = true;
            labelClinic.Visible = true;
            textBeforeTime.Visible = true;
            textAfterTime.Visible = true;
            comboClinic.Visible = true;
        }
        else
        {
            labelOps.Text = Lan.g(this,"View Operatories");
            labelBeforeTime.Visible = false;
            labelAfterTime.Visible = false;
            labelClinic.Visible = false;
            textBeforeTime.Visible = false;
            textAfterTime.Visible = false;
            comboClinic.Visible = false;
        } 
    }

    private void butLeft_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length > 0)
        {
            displayedElementsAll.Remove(displayedElementsMain[gridMain.getSelectedIndices()[0]]);
        }
        else if (gridUR.getSelectedIndices().Length > 0)
        {
            displayedElementsAll.Remove(displayedElementsUR[gridUR.getSelectedIndices()[0]]);
        }
        else if (gridLR.getSelectedIndices().Length > 0)
        {
            displayedElementsAll.Remove(displayedElementsLR[gridLR.getSelectedIndices()[0]]);
        }
           
        fillElements();
    }

    private void butRight_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridAvailable.getSelectedIndex() != -1)
        {
            //the item order is not used until saving to db.
            ApptViewItem item = new ApptViewItem(elementsAll[displayedAvailable[gridAvailable.getSelectedIndex()]], 0, Color.Black);
            if (gridMain.getSelectedIndices().Length == 1)
            {
                //insert
                int newIdx = displayedElementsAll.IndexOf(displayedElementsMain[gridMain.getSelectedIndex()]);
                displayedElementsAll.Insert(newIdx, item);
            }
            else
            {
                //add to end
                displayedElementsAll.Add(item);
            } 
            fillElements();
            for (int i = 0;i < displayedElementsMain.Count;i++)
            {
                //the new item will always show first in the main list.
                if (displayedElementsMain[i] == item)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
        else //reselect the item
        if (gridApptFieldDefs.getSelectedIndex() != -1)
        {
            ApptViewItem item = new ApptViewItem();
            item.ElementColor = Color.Black;
            item.ApptFieldDefNum = displayedAvailableApptFieldDefs[gridApptFieldDefs.getSelectedIndex()];
            if (gridMain.getSelectedIndices().Length == 1)
            {
                //insert
                int newIdx = displayedElementsAll.IndexOf(displayedElementsMain[gridMain.getSelectedIndex()]);
                displayedElementsAll.Insert(newIdx, item);
            }
            else
            {
                //add to end
                displayedElementsAll.Add(item);
            } 
            fillElements();
            for (int i = 0;i < displayedElementsMain.Count;i++)
            {
                //the new item will always show first in the main list.
                if (displayedElementsMain[i] == item)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
        else //reselect the item
        if (gridPatFieldDefs.getSelectedIndex() != -1)
        {
            ApptViewItem item = new ApptViewItem();
            item.ElementColor = Color.Black;
            item.PatFieldDefNum = displayedAvailablePatFieldDefs[gridPatFieldDefs.getSelectedIndex()];
            if (gridMain.getSelectedIndices().Length == 1)
            {
                //insert
                int newIdx = displayedElementsAll.IndexOf(displayedElementsMain[gridMain.getSelectedIndex()]);
                displayedElementsAll.Insert(newIdx, item);
            }
            else
            {
                //add to end
                displayedElementsAll.Add(item);
            } 
            fillElements();
            for (int i = 0;i < displayedElementsMain.Count;i++)
            {
                //the new item will always show first in the main list.
                if (displayedElementsMain[i] == item)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
           
    }

    //reselect the item
    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        int oldIdx = new int();
        int newIdx = new int();
        int newIdxAll = new int();
        //within the list of all.
        ApptViewItem item;
        if (gridMain.getSelectedIndex() != -1)
        {
            oldIdx = gridMain.getSelectedIndex();
            if (oldIdx == 0)
            {
                return ;
            }
             
            //can't move up any more
            item = displayedElementsMain[oldIdx];
            newIdx = oldIdx - 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsMain[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridMain.setSelected(newIdx,true);
        }
        else if (gridUR.getSelectedIndex() != -1)
        {
            oldIdx = gridUR.getSelectedIndex();
            if (oldIdx == 0)
            {
                return ;
            }
             
            //can't move up any more
            item = displayedElementsUR[oldIdx];
            newIdx = oldIdx - 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsUR[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridUR.setSelected(newIdx,true);
        }
        else if (gridLR.getSelectedIndex() != -1)
        {
            oldIdx = gridLR.getSelectedIndex();
            if (oldIdx == 0)
            {
                return ;
            }
             
            //can't move up any more
            item = displayedElementsLR[oldIdx];
            newIdx = oldIdx - 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsLR[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridLR.setSelected(newIdx,true);
        }
           
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        int oldIdx = new int();
        int newIdx = new int();
        int newIdxAll = new int();
        ApptViewItem item;
        if (gridMain.getSelectedIndex() != -1)
        {
            oldIdx = gridMain.getSelectedIndex();
            if (oldIdx == displayedElementsMain.Count - 1)
            {
                return ;
            }
             
            //can't move down any more
            item = displayedElementsMain[oldIdx];
            newIdx = oldIdx + 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsMain[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridMain.setSelected(newIdx,true);
        }
         
        if (gridUR.getSelectedIndex() != -1)
        {
            oldIdx = gridUR.getSelectedIndex();
            if (oldIdx == displayedElementsUR.Count - 1)
            {
                return ;
            }
             
            //can't move down any more
            item = displayedElementsUR[oldIdx];
            newIdx = oldIdx + 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsUR[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridUR.setSelected(newIdx,true);
        }
         
        if (gridLR.getSelectedIndex() != -1)
        {
            oldIdx = gridLR.getSelectedIndex();
            if (oldIdx == displayedElementsLR.Count - 1)
            {
                return ;
            }
             
            //can't move down any more
            item = displayedElementsLR[oldIdx];
            newIdx = oldIdx + 1;
            newIdxAll = displayedElementsAll.IndexOf(displayedElementsLR[newIdx]);
            displayedElementsAll.Remove(item);
            displayedElementsAll.Insert(newIdxAll, item);
            fillElements();
            gridLR.setSelected(newIdx,true);
        }
         
    }

    private void gridAvailable_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridAvailable.getSelectedIndices().Length > 0)
        {
            gridApptFieldDefs.setSelected(false);
            gridPatFieldDefs.setSelected(false);
        }
         
    }

    private void gridApptFieldDefs_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridApptFieldDefs.getSelectedIndices().Length > 0)
        {
            gridAvailable.setSelected(false);
            gridPatFieldDefs.setSelected(false);
        }
         
    }

    private void gridPatFieldDefs_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridPatFieldDefs.getSelectedIndices().Length > 0)
        {
            gridAvailable.setSelected(false);
            gridApptFieldDefs.setSelected(false);
        }
         
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length > 0)
        {
            gridUR.setSelected(false);
            gridLR.setSelected(false);
        }
         
    }

    private void gridUR_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridUR.getSelectedIndices().Length > 0)
        {
            gridMain.setSelected(false);
            gridLR.setSelected(false);
        }
         
    }

    private void gridLR_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridLR.getSelectedIndices().Length > 0)
        {
            gridUR.setSelected(false);
            gridMain.setSelected(false);
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormApptViewItemEdit formA = new FormApptViewItemEdit();
        formA.ApptVItem = displayedElementsMain[e.getRow()];
        formA.ShowDialog();
        fillElements();
        reselectItem(formA.ApptVItem);
    }

    private void gridUR_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormApptViewItemEdit formA = new FormApptViewItemEdit();
        formA.ApptVItem = displayedElementsUR[e.getRow()];
        formA.ShowDialog();
        fillElements();
        reselectItem(formA.ApptVItem);
    }

    private void gridLR_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormApptViewItemEdit formA = new FormApptViewItemEdit();
        formA.ApptVItem = displayedElementsLR[e.getRow()];
        formA.ShowDialog();
        fillElements();
        reselectItem(formA.ApptVItem);
    }

    /**
    * When we know what item we want to select, but we don't know which of the three areas it might now be in.
    */
    private void reselectItem(ApptViewItem item) throws Exception {
        for (int i = 0;i < displayedElementsMain.Count;i++)
        {
            //another way of doing this would be to test which area it was in first, but that wouldn't make the code more compact.
            if (displayedElementsMain[i] == item)
            {
                gridMain.setSelected(i,true);
                break;
            }
             
        }
        for (int i = 0;i < displayedElementsUR.Count;i++)
        {
            if (displayedElementsUR[i] == item)
            {
                gridUR.setSelected(i,true);
                break;
            }
             
        }
        for (int i = 0;i < displayedElementsLR.Count;i++)
        {
            if (displayedElementsLR[i] == item)
            {
                gridLR.setSelected(i,true);
                break;
            }
             
        }
    }

    private void textRowsPerIncr_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        try
        {
            Convert.ToInt32(textRowsPerIncr.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Must be a number between 1 and 3."));
            e.Cancel = true;
            return ;
        }

        if (PIn.Long(textRowsPerIncr.Text) < 1 || PIn.Long(textRowsPerIncr.Text) > 3)
        {
            MessageBox.Show(Lan.g(this,"Must be a number between 1 and 3."));
            e.Cancel = true;
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        //this does mess up the item orders a little, but missing numbers don't actually hurt anything.
        if (MessageBox.Show(Lan.g(this,"Delete this category?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        ApptViewItems.deleteAllForView(ApptViewCur);
        ApptViews.delete(ApptViewCur);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listProv.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"At least one provider must be selected.");
            return ;
        }
         
        if (listOps.SelectedIndices.Count == 0)
        {
            // && !checkOnlyScheduledProvs.Checked) {
            MsgBox.show(this,"At least one operatory must be selected.");
            return ;
        }
         
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"A description must be entered."));
            return ;
        }
         
        if (displayedElementsMain.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"At least one row type must be displayed."));
            return ;
        }
         
        DateTime timeBefore = new DateTime();
        //only the time portion will be used.
        if (checkOnlyScheduledProvs.Checked && !StringSupport.equals(textBeforeTime.Text, ""))
        {
            try
            {
                timeBefore = DateTime.Parse(textBeforeTime.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"Time before invalid.");
                return ;
            }
        
        }
         
        DateTime timeAfter = new DateTime();
        if (checkOnlyScheduledProvs.Checked && !StringSupport.equals(textAfterTime.Text, ""))
        {
            try
            {
                timeAfter = DateTime.Parse(textAfterTime.Text);
            }
            catch (Exception __dummyCatchVar2)
            {
                MsgBox.show(this,"Time after invalid.");
                return ;
            }
        
        }
         
        ApptViewItems.deleteAllForView(ApptViewCur);
        //start with a clean slate
        ApptViewItem item;
        for (int i = 0;i < OperatoryC.getListShort().Count;i++)
        {
            if (listOps.SelectedIndices.Contains(i))
            {
                item = new ApptViewItem();
                item.ApptViewNum = ApptViewCur.ApptViewNum;
                item.OpNum = OperatoryC.getListShort()[i].OperatoryNum;
                ApptViewItems.insert(item);
            }
             
        }
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (listProv.SelectedIndices.Contains(i))
            {
                item = new ApptViewItem();
                item.ApptViewNum = ApptViewCur.ApptViewNum;
                item.ProvNum = ProviderC.getListShort()[i].ProvNum;
                ApptViewItems.insert(item);
            }
             
        }
        ApptViewCur.StackBehavUR = (ApptViewStackBehavior)listStackUR.SelectedIndex;
        ApptViewCur.StackBehavLR = (ApptViewStackBehavior)listStackLR.SelectedIndex;
        for (int i = 0;i < displayedElementsMain.Count;i++)
        {
            item = displayedElementsMain[i];
            item.ApptViewNum = ApptViewCur.ApptViewNum;
            //elementDesc, elementColor, and Alignment already handled.
            item.ElementOrder = (byte)i;
            ApptViewItems.insert(item);
        }
        for (int i = 0;i < displayedElementsUR.Count;i++)
        {
            item = displayedElementsUR[i];
            item.ApptViewNum = ApptViewCur.ApptViewNum;
            item.ElementOrder = (byte)i;
            ApptViewItems.insert(item);
        }
        for (int i = 0;i < displayedElementsLR.Count;i++)
        {
            item = displayedElementsLR[i];
            item.ApptViewNum = ApptViewCur.ApptViewNum;
            item.ElementOrder = (byte)i;
            ApptViewItems.insert(item);
        }
        ApptViewCur.Description = textDescription.Text;
        ApptViewCur.RowsPerIncr = PIn.Byte(textRowsPerIncr.Text);
        ApptViewCur.OnlyScheduledProvs = checkOnlyScheduledProvs.Checked;
        ApptViewCur.OnlySchedBeforeTime = timeBefore.TimeOfDay;
        ApptViewCur.OnlySchedAfterTime = timeAfter.TimeOfDay;
        ApptViewCur.ClinicNum = 0;
        //Default clinics to "All"
        if (checkOnlyScheduledProvs.Checked && comboClinic.SelectedIndex > 0)
        {
            ApptViewCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        }
         
        ApptViews.update(ApptViewCur);
        //same whether isnew or not
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
            ;
    }

    private void formApptViewEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            ApptViewItems.deleteAllForView(ApptViewCur);
            ApptViews.delete(ApptViewCur);
        }
         
    }

}


