//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:10 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPhoneEmpDefaultEdit;
import OpenDental.FormPhoneGraphEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneEmpStatusOverride;
import OpenDentBusiness.PhoneGraph;
import OpenDentBusiness.PhoneGraphs;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Security;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPhoneEmpDefaultEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private OpenDental.UI.Button butDelete;
    private ListBox listRingGroup = new ListBox();
    private CheckBox checkIsGraphed = new CheckBox();
    private Label label5 = new Label();
    private CheckBox checkHasColor = new CheckBox();
    private TextBox textEmpName = new TextBox();
    private Label label6 = new Label();
    private TextBox textNotes = new TextBox();
    private Label label3 = new Label();
    private TextBox textComputerName = new TextBox();
    private Label label4 = new Label();
    private CheckBox checkIsPrivateScreen = new CheckBox();
    private Label label7 = new Label();
    private Label label8 = new Label();
    private Label label9 = new Label();
    private Label label10 = new Label();
    private Label label11 = new Label();
    private Label label12 = new Label();
    private Label label13 = new Label();
    private Label label14 = new Label();
    private Label label15 = new Label();
    private Label label16 = new Label();
    private OpenDental.ValidNum textEmployeeNum;
    private OpenDental.ValidNum textPhoneExt;
    private ListBox listStatusOverride = new ListBox();
    private Label label17 = new Label();
    private CheckBox checkIsTriageOperator = new CheckBox();
    public PhoneEmpDefault PedCur;
    private OpenDental.UI.ODGrid gridGraph;
    private OpenDental.UI.Button butAddPhoneGraphEntry;
    /**
    * Will always be the override status upon load.
    */
    private PhoneEmpStatusOverride StatusOld = PhoneEmpStatusOverride.None;
    /**
    * 
    */
    public FormPhoneEmpDefaultEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPhoneEmpDefaultEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listRingGroup = new System.Windows.Forms.ListBox();
        this.checkIsGraphed = new System.Windows.Forms.CheckBox();
        this.label5 = new System.Windows.Forms.Label();
        this.checkHasColor = new System.Windows.Forms.CheckBox();
        this.textEmpName = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textNotes = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textComputerName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkIsPrivateScreen = new System.Windows.Forms.CheckBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.listStatusOverride = new System.Windows.Forms.ListBox();
        this.label17 = new System.Windows.Forms.Label();
        this.checkIsTriageOperator = new System.Windows.Forms.CheckBox();
        this.butAddPhoneGraphEntry = new OpenDental.UI.Button();
        this.gridGraph = new OpenDental.UI.ODGrid();
        this.textPhoneExt = new OpenDental.ValidNum();
        this.textEmployeeNum = new OpenDental.ValidNum();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(40, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 20);
        this.label1.TabIndex = 11;
        this.label1.Text = "EmployeeNum";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(1, 144);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(139, 20);
        this.label2.TabIndex = 13;
        this.label2.Text = "Default Ring Group";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listRingGroup
        //
        this.listRingGroup.FormattingEnabled = true;
        this.listRingGroup.Location = new System.Drawing.Point(144, 144);
        this.listRingGroup.Name = "listRingGroup";
        this.listRingGroup.Size = new System.Drawing.Size(120, 43);
        this.listRingGroup.TabIndex = 4;
        //
        // checkIsGraphed
        //
        this.checkIsGraphed.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsGraphed.Location = new System.Drawing.Point(3, 87);
        this.checkIsGraphed.Name = "checkIsGraphed";
        this.checkIsGraphed.Size = new System.Drawing.Size(155, 20);
        this.checkIsGraphed.TabIndex = 2;
        this.checkIsGraphed.Text = "Is Graphed (default)";
        this.checkIsGraphed.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsGraphed.UseVisualStyleBackColor = true;
        this.checkIsGraphed.Click += new System.EventHandler(this.checkIsGraphed_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(40, 200);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 20);
        this.label5.TabIndex = 23;
        this.label5.Text = "Extension";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkHasColor
        //
        this.checkHasColor.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHasColor.Location = new System.Drawing.Point(3, 113);
        this.checkHasColor.Name = "checkHasColor";
        this.checkHasColor.Size = new System.Drawing.Size(155, 20);
        this.checkHasColor.TabIndex = 3;
        this.checkHasColor.Text = "Has Color";
        this.checkHasColor.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHasColor.UseVisualStyleBackColor = true;
        this.checkHasColor.Click += new System.EventHandler(this.checkHasColor_Click);
        //
        // textEmpName
        //
        this.textEmpName.Location = new System.Drawing.Point(144, 56);
        this.textEmpName.Name = "textEmpName";
        this.textEmpName.Size = new System.Drawing.Size(170, 20);
        this.textEmpName.TabIndex = 1;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 55);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(137, 20);
        this.label6.TabIndex = 26;
        this.label6.Text = "Employee First Name";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNotes
        //
        this.textNotes.Location = new System.Drawing.Point(144, 292);
        this.textNotes.Multiline = true;
        this.textNotes.Name = "textNotes";
        this.textNotes.Size = new System.Drawing.Size(352, 51);
        this.textNotes.TabIndex = 7;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(40, 291);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 20);
        this.label3.TabIndex = 29;
        this.label3.Text = "Notes";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textComputerName
        //
        this.textComputerName.Location = new System.Drawing.Point(144, 358);
        this.textComputerName.Name = "textComputerName";
        this.textComputerName.Size = new System.Drawing.Size(213, 20);
        this.textComputerName.TabIndex = 8;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(40, 357);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 20);
        this.label4.TabIndex = 31;
        this.label4.Text = "Computer Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsPrivateScreen
        //
        this.checkIsPrivateScreen.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPrivateScreen.Location = new System.Drawing.Point(3, 389);
        this.checkIsPrivateScreen.Name = "checkIsPrivateScreen";
        this.checkIsPrivateScreen.Size = new System.Drawing.Size(155, 20);
        this.checkIsPrivateScreen.TabIndex = 9;
        this.checkIsPrivateScreen.Text = "Private Screen";
        this.checkIsPrivateScreen.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPrivateScreen.UseVisualStyleBackColor = true;
        this.checkIsPrivateScreen.Click += new System.EventHandler(this.checkIsPrivateScreen_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(200, 24);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(366, 20);
        this.label7.TabIndex = 34;
        this.label7.Text = "This number must be looked up in the employee table";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(161, 85);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(428, 27);
        this.label8.TabIndex = 35;
        this.label8.Text = "This employee\'s default \'Graph\' status. Should be checked for most phone techs.\r\n" + "Use Phone Graph Edits grid to create exceptions.";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(161, 112);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(414, 20);
        this.label9.TabIndex = 36;
        this.label9.Text = "Show the red and green phone status colors in the phone panel";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(267, 143);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(359, 47);
        this.label10.TabIndex = 37;
        this.label10.Text = "The normal ring group for this employee when clocked in.  If you change this valu" + "e, the change will not immediately show on each workstation, but will instead re" + "quire a restart of OD.";
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(204, 194);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(498, 32);
        this.label11.TabIndex = 38;
        this.label11.Text = "Phone extension for this employee.  Change this number to 0 if you are going to b" + "e floating.  Changing the extension to 0 will allow you to use the manage module" + " to clock in and out.";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(315, 56);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(366, 20);
        this.label12.TabIndex = 39;
        this.label12.Text = "This is the name that will show in the phone panel.";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(268, 235);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(321, 20);
        this.label13.TabIndex = 40;
        this.label13.Text = "Mark yourself unavailable only if approved by manager";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(502, 291);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(157, 35);
        this.label14.TabIndex = 41;
        this.label14.Text = "Why unavailable?\r\nWhy offline assist?";
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(363, 355);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(350, 48);
        this.label15.TabIndex = 42;
        this.label15.Text = "Do NOT set computer name unless approved by management.  Used when the IP does no" + "t match the extension.  Not used by floaters.";
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(161, 386);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(194, 47);
        this.label16.TabIndex = 43;
        this.label16.Text = "Halts screen captures.  Only used/allowed by managers. ";
        //
        // listStatusOverride
        //
        this.listStatusOverride.FormattingEnabled = true;
        this.listStatusOverride.Location = new System.Drawing.Point(144, 235);
        this.listStatusOverride.Name = "listStatusOverride";
        this.listStatusOverride.Size = new System.Drawing.Size(120, 43);
        this.listStatusOverride.TabIndex = 6;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(2, 237);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(139, 20);
        this.label17.TabIndex = 46;
        this.label17.Text = "StatusOverride";
        this.label17.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkIsTriageOperator
        //
        this.checkIsTriageOperator.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsTriageOperator.Location = new System.Drawing.Point(3, 415);
        this.checkIsTriageOperator.Name = "checkIsTriageOperator";
        this.checkIsTriageOperator.Size = new System.Drawing.Size(155, 20);
        this.checkIsTriageOperator.TabIndex = 10;
        this.checkIsTriageOperator.Text = "Triage Operator";
        this.checkIsTriageOperator.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsTriageOperator.UseVisualStyleBackColor = true;
        //
        // butAddPhoneGraphEntry
        //
        this.butAddPhoneGraphEntry.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddPhoneGraphEntry.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddPhoneGraphEntry.setAutosize(true);
        this.butAddPhoneGraphEntry.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddPhoneGraphEntry.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddPhoneGraphEntry.setCornerRadius(4F);
        this.butAddPhoneGraphEntry.Image = Resources.getAdd();
        this.butAddPhoneGraphEntry.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddPhoneGraphEntry.Location = new System.Drawing.Point(832, 415);
        this.butAddPhoneGraphEntry.Name = "butAddPhoneGraphEntry";
        this.butAddPhoneGraphEntry.Size = new System.Drawing.Size(75, 24);
        this.butAddPhoneGraphEntry.TabIndex = 48;
        this.butAddPhoneGraphEntry.Text = "Add";
        this.butAddPhoneGraphEntry.Click += new System.EventHandler(this.butAddPhoneGraphEntry_Click);
        //
        // gridGraph
        //
        this.gridGraph.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridGraph.setHScrollVisible(false);
        this.gridGraph.Location = new System.Drawing.Point(745, 55);
        this.gridGraph.Name = "gridGraph";
        this.gridGraph.setScrollValue(0);
        this.gridGraph.Size = new System.Drawing.Size(162, 354);
        this.gridGraph.TabIndex = 47;
        this.gridGraph.setTitle("Phone Graph Edits");
        this.gridGraph.setTranslationName("TablePhoneGraph");
        this.gridGraph.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridGraph.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridGraph_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textPhoneExt
        //
        this.textPhoneExt.Location = new System.Drawing.Point(144, 201);
        this.textPhoneExt.setMaxVal(1000);
        this.textPhoneExt.setMinVal(0);
        this.textPhoneExt.Name = "textPhoneExt";
        this.textPhoneExt.Size = new System.Drawing.Size(54, 20);
        this.textPhoneExt.TabIndex = 5;
        //
        // textEmployeeNum
        //
        this.textEmployeeNum.Location = new System.Drawing.Point(144, 24);
        this.textEmployeeNum.setMaxVal(255);
        this.textEmployeeNum.setMinVal(0);
        this.textEmployeeNum.Name = "textEmployeeNum";
        this.textEmployeeNum.Size = new System.Drawing.Size(54, 20);
        this.textEmployeeNum.TabIndex = 0;
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
        this.butDelete.Location = new System.Drawing.Point(28, 474);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 13;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(739, 474);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 11;
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
        this.butCancel.Location = new System.Drawing.Point(832, 474);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 12;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPhoneEmpDefaultEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(924, 511);
        this.Controls.Add(this.butAddPhoneGraphEntry);
        this.Controls.Add(this.gridGraph);
        this.Controls.Add(this.checkIsTriageOperator);
        this.Controls.Add(this.listStatusOverride);
        this.Controls.Add(this.label17);
        this.Controls.Add(this.textPhoneExt);
        this.Controls.Add(this.textEmployeeNum);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.checkIsPrivateScreen);
        this.Controls.Add(this.textComputerName);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textEmpName);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.checkHasColor);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.checkIsGraphed);
        this.Controls.Add(this.listRingGroup);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPhoneEmpDefaultEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Employee Setting";
        this.Load += new System.EventHandler(this.FormPhoneEmpDefaultEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPhoneEmpDefaultEdit_Load(Object sender, System.EventArgs e) throws Exception {
        StatusOld = PedCur.StatusOverride;
        //We use this for testing when user clicks OK.
        if (!IsNew)
        {
            textEmployeeNum.ReadOnly = true;
        }
         
        textEmployeeNum.Text = PedCur.EmployeeNum.ToString();
        textEmpName.Text = PedCur.EmpName;
        checkIsGraphed.Checked = PedCur.IsGraphed;
        checkHasColor.Checked = PedCur.HasColor;
        for (int i = 0;i < Enum.GetNames(AsteriskRingGroups.class).Length;i++)
        {
            listRingGroup.Items.Add(Enum.GetNames(AsteriskRingGroups.class)[i]);
        }
        listRingGroup.SelectedIndex = ((Enum)PedCur.RingGroups).ordinal();
        textPhoneExt.Text = PedCur.PhoneExt.ToString();
        for (int i = 0;i < Enum.GetNames(PhoneEmpStatusOverride.class).Length;i++)
        {
            listStatusOverride.Items.Add(Enum.GetNames(PhoneEmpStatusOverride.class)[i]);
        }
        listStatusOverride.SelectedIndex = ((Enum)PedCur.StatusOverride).ordinal();
        textNotes.Text = PedCur.Notes;
        textComputerName.Text = PedCur.ComputerName;
        checkIsPrivateScreen.Checked = PedCur.IsPrivateScreen;
        checkIsTriageOperator.Checked = PedCur.IsTriageOperator;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //get PhoneGraphs for this employee num and fill the grid
        List<PhoneGraph> phoneGraphs = PhoneGraphs.getAllForEmployeeNum(PedCur.EmployeeNum);
        gridGraph.beginUpdate();
        gridGraph.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePhoneGraph","Date"),70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePhoneGraph","IsGraphed"),60);
        col.setTextAlign(HorizontalAlignment.Center);
        gridGraph.getColumns().add(col);
        gridGraph.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < phoneGraphs.Count;i++)
        {
            if (phoneGraphs[i].DateEntry < DateTime.Today)
            {
                continue;
            }
             
            //do not show past date entries
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(phoneGraphs[i].DateEntry.ToShortDateString());
            row.getCells().add(phoneGraphs[i].IsGraphed ? "X" : "");
            row.setTag(phoneGraphs[i]);
            gridGraph.getRows().add(row);
        }
        gridGraph.endUpdate();
    }

    private void gridGraph_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //edit this entry
        if (gridGraph.getRows().get___idx(e.getRow()).getTag() == null || !(gridGraph.getRows().get___idx(e.getRow()).getTag() instanceof PhoneGraph))
        {
            return ;
        }
         
        PhoneGraph phoneGraph = (PhoneGraph)gridGraph.getRows().get___idx(e.getRow()).getTag();
        FormPhoneGraphEdit FormPGE = new FormPhoneGraphEdit(PedCur.EmployeeNum);
        FormPGE.PhoneGraphCur = phoneGraph;
        FormPGE.ShowDialog();
        fillGrid();
    }

    private void butAddPhoneGraphEntry_Click(Object sender, EventArgs e) throws Exception {
        FormPhoneGraphEdit FormPGE = new FormPhoneGraphEdit(PedCur.EmployeeNum);
        FormPGE.PhoneGraphCur = new PhoneGraph();
        FormPGE.PhoneGraphCur.setIsNew(true);
        if (FormPGE.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void checkIsPrivateScreen_Click(Object sender, EventArgs e) throws Exception {
        //Debbie
        //Shannon
        //Nathan
        if (Security.getCurUser().EmployeeNum != 10 && Security.getCurUser().EmployeeNum != 13 && Security.getCurUser().EmployeeNum != 17 && Security.getCurUser().EmployeeNum != 22)
        {
            //Jordan
            //Put the checkbox back the way it was before user clicked on it.
            if (checkIsPrivateScreen.Checked)
            {
                checkIsPrivateScreen.Checked = false;
            }
            else
            {
                checkIsPrivateScreen.Checked = true;
            } 
            MsgBox.show(this,"You do not have permission to halt screen captures.");
        }
         
    }

    private void checkIsGraphed_Click(Object sender, EventArgs e) throws Exception {
        if (Security.isAuthorized(Permissions.Schedules))
        {
            PhoneGraph phoneGraph = PhoneGraphs.GetForEmployeeNumAndDate(PedCur.EmployeeNum, DateTime.Today);
            //check for override
            if (phoneGraph == null)
            {
                return ;
            }
             
            //no existing entry so exit
            if (checkIsGraphed.Checked == phoneGraph.IsGraphed)
            {
                return ;
            }
             
            //default matches existing entry so exit
            //we have an existing entry so ask if they want to get rid of it
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Phone Graph override exists to the contrary of new setting for this employee. Do you want to delete the override and use the default?"))
            {
                return ;
            }
             
            //get rid of the existing
            PhoneGraphs.delete(phoneGraph.PhoneGraphNum);
            fillGrid();
            return ;
        }
         
        //Put the checkbox back the way it was before user clicked on it.
        if (checkIsGraphed.Checked)
        {
            checkIsGraphed.Checked = false;
        }
        else
        {
            checkIsGraphed.Checked = true;
        } 
    }

    private void checkHasColor_Click(Object sender, EventArgs e) throws Exception {
        if (Security.isAuthorized(Permissions.Schedules))
        {
            return ;
        }
         
        //Put the checkbox back the way it was before user clicked on it.
        if (checkHasColor.Checked)
        {
            checkHasColor.Checked = false;
        }
        else
        {
            checkHasColor.Checked = true;
        } 
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        PhoneEmpDefaults.delete(PedCur.EmployeeNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //Using a switch statement in case we want special functionality for the other statuses later on.
        switch((PhoneEmpStatusOverride)listStatusOverride.SelectedIndex)
        {
            case None: 
                if (StatusOld == PhoneEmpStatusOverride.Unavailable)
                {
                    MsgBox.show(this,"Change your status from unavailable by using the small phone panel.");
                    return ;
                }
                 
                break;
            case OfflineAssist: 
                if (StatusOld == PhoneEmpStatusOverride.Unavailable)
                {
                    MsgBox.show(this,"Change your status from unavailable by using the small phone panel.");
                    return ;
                }
                 
                break;
        
        }
        if (IsNew)
        {
            if (StringSupport.equals(textEmployeeNum.Text, ""))
            {
                MsgBox.show(this,"Unique EmployeeNum is required.");
                return ;
            }
             
            if (StringSupport.equals(textEmpName.Text, ""))
            {
                MsgBox.show(this,"Employee name is required.");
                return ;
            }
             
            PedCur.EmployeeNum = PIn.Long(textEmployeeNum.Text);
        }
         
        //Get the current database state of the phone emp default (before we change it)
        PhoneEmpDefault pedFromDatabase = PhoneEmpDefaults.getOne(PedCur.EmployeeNum);
        if (pedFromDatabase == null)
        {
            pedFromDatabase = new PhoneEmpDefault();
        }
         
        int newExtension = PIn.Int(textPhoneExt.Text);
        boolean extensionChange = pedFromDatabase.PhoneExt != newExtension;
        if (extensionChange)
        {
            //Only check when extension has changed and clocked in.
            //We need to prevent changes to phoneempdefault table which involve employees who are currently logged in.
            //Failing to do so would cause subtle race conditions between the phone table and phoneempdefault.
            //Net result would be the phone panel looking wrong.
            if (ClockEvents.isClockedIn(PedCur.EmployeeNum))
            {
                //Prevent any change if employee being edited is currently clocked in.
                MsgBox.show(this,"You must first clock out before making changes");
                return ;
            }
             
            //Find out if the target extension is already being occuppied by a different employee.
            Phone phoneOccuppied = Phones.GetPhoneForExtension(Phones.getPhoneList(), PIn.Int(textPhoneExt.Text));
            if (phoneOccuppied != null)
            {
                if (ClockEvents.isClockedIn(phoneOccuppied.EmployeeNum))
                {
                    //Prevent change if employee's new extension is occupied by a different employee who is currently clocked in.
                    MessageBox.Show(Lan.g(this,"This extension cannot be inherited because it is currently occuppied by an employee who is currently logged in.\r\n\r\nExisting employee: ") + phoneOccuppied.EmployeeName);
                    return ;
                }
                 
                if (phoneOccuppied.EmployeeNum != PedCur.EmployeeNum)
                {
                    //We are setting to a new employee so let's clean up the old employee.
                    //This will prevent duplicates in the phone table and subsequently prevent duplicates in the phone panel.
                    Phones.updatePhoneToEmpty(phoneOccuppied.EmployeeNum,-1);
                    PhoneEmpDefault pedOccuppied = PhoneEmpDefaults.getOne(phoneOccuppied.EmployeeNum);
                    if (pedOccuppied != null)
                    {
                        //prevent duplicate in phoneempdefault
                        pedOccuppied.PhoneExt = 0;
                        PhoneEmpDefaults.update(pedOccuppied);
                    }
                     
                }
                 
            }
             
            //Get the employee that is normally assigned to this extension (assigned ext set in the employee table).
            long permanentLinkageEmployeeNum = Employees.getEmpNumAtExtension(pedFromDatabase.PhoneExt);
            if (permanentLinkageEmployeeNum >= 1)
            {
                //Extension is nomrally assigned to an employee.
                if (PedCur.EmployeeNum != permanentLinkageEmployeeNum)
                {
                    //This is not the normally linked employee so let's revert back to the proper employee.
                    PhoneEmpDefault pedRevertTo = PhoneEmpDefaults.getOne(permanentLinkageEmployeeNum);
                    //Make sure the employee we are about to revert is not logged in at yet a different workstation. This would be rare but it's worth checking.
                    if (pedRevertTo != null && !ClockEvents.isClockedIn(pedRevertTo.EmployeeNum))
                    {
                        //Revert to the permanent extension for this PhoneEmpDefault.
                        pedRevertTo.PhoneExt = pedFromDatabase.PhoneExt;
                        PhoneEmpDefaults.update(pedRevertTo);
                        //Update phone table to match this change.
                        Phones.setPhoneStatus(ClockStatusEnum.Home,pedRevertTo.PhoneExt,pedRevertTo.EmployeeNum);
                    }
                     
                }
                 
            }
             
        }
         
        //Ordering of these updates is IMPORTANT!!!
        //Phone Emp Default must be updated first
        PedCur.EmpName = textEmpName.Text;
        PedCur.IsGraphed = checkIsGraphed.Checked;
        PedCur.HasColor = checkHasColor.Checked;
        PedCur.RingGroups = (AsteriskRingGroups)listRingGroup.SelectedIndex;
        PedCur.PhoneExt = PIn.Int(textPhoneExt.Text);
        PedCur.StatusOverride = (PhoneEmpStatusOverride)listStatusOverride.SelectedIndex;
        PedCur.Notes = textNotes.Text;
        PedCur.ComputerName = textComputerName.Text;
        PedCur.IsPrivateScreen = checkIsPrivateScreen.Checked;
        PedCur.IsTriageOperator = checkIsTriageOperator.Checked;
        if (IsNew)
        {
            PhoneEmpDefaults.insert(PedCur);
            //insert the new Phone record to keep the 2 tables in sync
            Phone phoneNew = new Phone();
            phoneNew.EmployeeName = PedCur.EmpName;
            phoneNew.EmployeeNum = PedCur.EmployeeNum;
            phoneNew.Extension = PedCur.PhoneExt;
            phoneNew.ClockStatus = ClockStatusEnum.Home;
            Phones.insert(phoneNew);
        }
        else
        {
            PhoneEmpDefaults.update(PedCur);
        } 
        //It is now safe to update Phone table as it will draw from the newly updated Phone Emp Default row
        if ((PhoneEmpStatusOverride)listStatusOverride.SelectedIndex == PhoneEmpStatusOverride.Unavailable && ClockEvents.isClockedIn(PedCur.EmployeeNum))
        {
            //We set ourselves unavailable from this window because we require an explanation.
            //This is the only status that will synch with the phone table, all others should be handled by the small phone panel.
            Phones.setPhoneStatus(ClockStatusEnum.Unavailable,PedCur.PhoneExt,PedCur.EmployeeNum);
        }
         
        if (extensionChange)
        {
            //Phone extension has changed so update the phone table as well.
            //We have already guaranteed that this employee is Clocked Out (above) so set to home and update phone table.
            Phones.setPhoneStatus(ClockStatusEnum.Home,PedCur.PhoneExt,PedCur.EmployeeNum);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


