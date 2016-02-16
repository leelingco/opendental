//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:31 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DataValid;
import OpenDental.FormAuditOneType;
import OpenDental.FormFeeEdit;
import OpenDental.FormProcCodeEdit;
import OpenDental.FormProcCodeNoteEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProcCodeNote;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SubstitutionCondition;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.UI.ApptDrawing;

/**
* 
*/
public class FormProcCodeEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listTreatArea = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listCategory = new System.Windows.Forms.ListBox();
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.TextBox textProcCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAbbrev = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkNoBillIns = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butSlider = new System.Windows.Forms.Button();
    private OpenDental.TableTimeBar tbTime;
    private System.Windows.Forms.TextBox textTime2 = new System.Windows.Forms.TextBox();
    private boolean mouseIsDown = new boolean();
    private Point mouseOrigin = new Point();
    private Point sliderOrigin = new Point();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private StringBuilder strBTime = new StringBuilder();
    private System.Windows.Forms.CheckBox checkIsHygiene = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAlternateCode1 = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.CheckBox checkIsProsth = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private boolean FeeChanged = new boolean();
    private System.Windows.Forms.TextBox textMedicalCode = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODGrid gridFees;
    private Label label15 = new Label();
    private ListBox listPaintType = new ListBox();
    private Label labelColor = new Label();
    private System.Windows.Forms.Button butColor = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butColorClear;
    private TextBox textLaymanTerm = new TextBox();
    private Label label2 = new Label();
    private CheckBox checkIsCanadianLab = new CheckBox();
    private Label label16 = new Label();
    private TextBox textBaseUnits = new TextBox();
    private Label label17 = new Label();
    private Label label18 = new Label();
    private TextBox textSubstitutionCode = new TextBox();
    private OpenDental.UI.ODGrid gridNotes;
    private OpenDental.UI.Button butAddNote;
    private ProcedureCode ProcCode;
    private Label label19 = new Label();
    private ComboBox comboSubstOnlyIf = new ComboBox();
    private CheckBox checkMultiVisit = new CheckBox();
    private Label labelDrugNDC = new Label();
    private Label labelRevenueCode = new Label();
    private TextBox textDrugNDC = new TextBox();
    private TextBox textRevenueCode = new TextBox();
    private Label label20 = new Label();
    private Label label21 = new Label();
    private ComboBox comboProvNumDefault = new ComboBox();
    private Label label22 = new Label();
    private OpenDental.UI.Button butAuditTrail;
    private List<ProcCodeNote> NoteList = new List<ProcCodeNote>();
    /**
    * The procedure code must have already been insterted into the database.
    */
    public FormProcCodeEdit(ProcedureCode procCode) throws Exception {
        initializeComponent();
        tbTime.CellClicked = __MultiCellEventHandler.combine(tbTime.CellClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            // Required for Windows Form Designer support
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbTime_CellClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        Lan.f(this);
        ProcCode = procCode;
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcCodeEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textProcCode = new System.Windows.Forms.TextBox();
        this.textAbbrev = new System.Windows.Forms.TextBox();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.listTreatArea = new System.Windows.Forms.ListBox();
        this.checkNoBillIns = new System.Windows.Forms.CheckBox();
        this.listCategory = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.butSlider = new System.Windows.Forms.Button();
        this.textTime2 = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.checkIsHygiene = new System.Windows.Forms.CheckBox();
        this.textAlternateCode1 = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.checkIsProsth = new System.Windows.Forms.CheckBox();
        this.textMedicalCode = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.listPaintType = new System.Windows.Forms.ListBox();
        this.labelColor = new System.Windows.Forms.Label();
        this.butColor = new System.Windows.Forms.Button();
        this.textLaymanTerm = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkIsCanadianLab = new System.Windows.Forms.CheckBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textBaseUnits = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.label18 = new System.Windows.Forms.Label();
        this.textSubstitutionCode = new System.Windows.Forms.TextBox();
        this.label19 = new System.Windows.Forms.Label();
        this.comboSubstOnlyIf = new System.Windows.Forms.ComboBox();
        this.checkMultiVisit = new System.Windows.Forms.CheckBox();
        this.butAddNote = new OpenDental.UI.Button();
        this.gridNotes = new OpenDental.UI.ODGrid();
        this.tbTime = new OpenDental.TableTimeBar();
        this.butColorClear = new OpenDental.UI.Button();
        this.gridFees = new OpenDental.UI.ODGrid();
        this.textNote = new OpenDental.ODtextBox();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelDrugNDC = new System.Windows.Forms.Label();
        this.labelRevenueCode = new System.Windows.Forms.Label();
        this.textDrugNDC = new System.Windows.Forms.TextBox();
        this.textRevenueCode = new System.Windows.Forms.TextBox();
        this.label20 = new System.Windows.Forms.Label();
        this.label21 = new System.Windows.Forms.Label();
        this.comboProvNumDefault = new System.Windows.Forms.ComboBox();
        this.label22 = new System.Windows.Forms.Label();
        this.butAuditTrail = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(123, 5);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 14);
        this.label1.TabIndex = 0;
        this.label1.Text = "Proc Code";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(493, 247);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 14);
        this.label4.TabIndex = 3;
        this.label4.Text = "Treatment Area";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(616, 13);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 14);
        this.label5.TabIndex = 4;
        this.label5.Text = "Category";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(2, 58);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(53, 39);
        this.label6.TabIndex = 5;
        this.label6.Text = "Time Pattern";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label7
        //
        this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label7.Location = new System.Drawing.Point(109, 104);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(94, 16);
        this.label7.TabIndex = 6;
        this.label7.Text = "Abbreviation";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label8.Location = new System.Drawing.Point(109, 85);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(94, 14);
        this.label8.TabIndex = 7;
        this.label8.Text = "Description";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label10
        //
        this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label10.Location = new System.Drawing.Point(43, 354);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(148, 14);
        this.label10.TabIndex = 9;
        this.label10.Text = "Default Note";
        this.label10.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textProcCode
        //
        this.textProcCode.Location = new System.Drawing.Point(205, 3);
        this.textProcCode.Name = "textProcCode";
        this.textProcCode.ReadOnly = true;
        this.textProcCode.Size = new System.Drawing.Size(100, 20);
        this.textProcCode.TabIndex = 14;
        //
        // textAbbrev
        //
        this.textAbbrev.Location = new System.Drawing.Point(205, 103);
        this.textAbbrev.MaxLength = 20;
        this.textAbbrev.Name = "textAbbrev";
        this.textAbbrev.Size = new System.Drawing.Size(100, 20);
        this.textAbbrev.TabIndex = 1;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(205, 83);
        this.textDescription.MaxLength = 255;
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(287, 20);
        this.textDescription.TabIndex = 0;
        //
        // listTreatArea
        //
        this.listTreatArea.Items.AddRange(new Object[]{ "Surface", "Tooth", "Mouth", "Quadrant", "Sextant", "Arch", "Tooth Range" });
        this.listTreatArea.Location = new System.Drawing.Point(495, 265);
        this.listTreatArea.Name = "listTreatArea";
        this.listTreatArea.Size = new System.Drawing.Size(118, 95);
        this.listTreatArea.TabIndex = 2;
        //
        // checkNoBillIns
        //
        this.checkNoBillIns.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkNoBillIns.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNoBillIns.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.checkNoBillIns.Location = new System.Drawing.Point(18, 242);
        this.checkNoBillIns.Name = "checkNoBillIns";
        this.checkNoBillIns.Size = new System.Drawing.Size(200, 18);
        this.checkNoBillIns.TabIndex = 6;
        this.checkNoBillIns.Text = "Do not usually bill to Ins";
        this.checkNoBillIns.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listCategory
        //
        this.listCategory.Location = new System.Drawing.Point(616, 31);
        this.listCategory.Name = "listCategory";
        this.listCategory.Size = new System.Drawing.Size(120, 238);
        this.listCategory.TabIndex = 3;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(184, 669);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(388, 29);
        this.label3.TabIndex = 28;
        this.label3.Text = "There is no way to delete a code once created because it might have been used som" + "eplace.  Instead, move it to a category like \"obsolete\"";
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(750, 670);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(178, 28);
        this.label9.TabIndex = 29;
        this.label9.Text = "Even if you press cancel, changes to fees will not be undone.";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butSlider
        //
        this.butSlider.BackColor = System.Drawing.SystemColors.ControlDark;
        this.butSlider.Location = new System.Drawing.Point(12, 113);
        this.butSlider.Name = "butSlider";
        this.butSlider.Size = new System.Drawing.Size(12, 15);
        this.butSlider.TabIndex = 31;
        this.butSlider.UseVisualStyleBackColor = false;
        this.butSlider.MouseDown += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseDown);
        this.butSlider.MouseMove += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseMove);
        this.butSlider.MouseUp += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseUp);
        //
        // textTime2
        //
        this.textTime2.Location = new System.Drawing.Point(10, 674);
        this.textTime2.Name = "textTime2";
        this.textTime2.Size = new System.Drawing.Size(60, 20);
        this.textTime2.TabIndex = 32;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(76, 678);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(102, 16);
        this.label11.TabIndex = 33;
        this.label11.Text = "Minutes";
        //
        // checkIsHygiene
        //
        this.checkIsHygiene.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHygiene.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsHygiene.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.checkIsHygiene.Location = new System.Drawing.Point(44, 260);
        this.checkIsHygiene.Name = "checkIsHygiene";
        this.checkIsHygiene.Size = new System.Drawing.Size(174, 18);
        this.checkIsHygiene.TabIndex = 7;
        this.checkIsHygiene.Text = "Is Hygiene procedure";
        this.checkIsHygiene.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAlternateCode1
        //
        this.textAlternateCode1.Location = new System.Drawing.Point(205, 23);
        this.textAlternateCode1.MaxLength = 15;
        this.textAlternateCode1.Name = "textAlternateCode1";
        this.textAlternateCode1.Size = new System.Drawing.Size(100, 20);
        this.textAlternateCode1.TabIndex = 38;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(126, 25);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(79, 14);
        this.label12.TabIndex = 37;
        this.label12.Text = "Alt Code";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(311, 25);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(161, 19);
        this.label13.TabIndex = 39;
        this.label13.Text = "(For some Medicaid)";
        //
        // checkIsProsth
        //
        this.checkIsProsth.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsProsth.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsProsth.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.checkIsProsth.Location = new System.Drawing.Point(44, 278);
        this.checkIsProsth.Name = "checkIsProsth";
        this.checkIsProsth.Size = new System.Drawing.Size(174, 18);
        this.checkIsProsth.TabIndex = 41;
        this.checkIsProsth.Text = "Is Prosthesis";
        this.checkIsProsth.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMedicalCode
        //
        this.textMedicalCode.Location = new System.Drawing.Point(205, 43);
        this.textMedicalCode.MaxLength = 15;
        this.textMedicalCode.Name = "textMedicalCode";
        this.textMedicalCode.Size = new System.Drawing.Size(100, 20);
        this.textMedicalCode.TabIndex = 43;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(126, 45);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(79, 14);
        this.label14.TabIndex = 42;
        this.label14.Text = "Medical Code";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(493, 10);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(100, 18);
        this.label15.TabIndex = 46;
        this.label15.Text = "Paint Type";
        this.label15.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listPaintType
        //
        this.listPaintType.Location = new System.Drawing.Point(495, 31);
        this.listPaintType.Name = "listPaintType";
        this.listPaintType.Size = new System.Drawing.Size(118, 212);
        this.listPaintType.TabIndex = 45;
        //
        // labelColor
        //
        this.labelColor.Location = new System.Drawing.Point(87, 205);
        this.labelColor.Name = "labelColor";
        this.labelColor.Size = new System.Drawing.Size(116, 16);
        this.labelColor.TabIndex = 48;
        this.labelColor.Text = "Color Override";
        this.labelColor.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColor
        //
        this.butColor.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColor.Location = new System.Drawing.Point(205, 204);
        this.butColor.Name = "butColor";
        this.butColor.Size = new System.Drawing.Size(21, 19);
        this.butColor.TabIndex = 47;
        this.butColor.Click += new System.EventHandler(this.butColor_Click);
        //
        // textLaymanTerm
        //
        this.textLaymanTerm.Location = new System.Drawing.Point(205, 123);
        this.textLaymanTerm.MaxLength = 255;
        this.textLaymanTerm.Name = "textLaymanTerm";
        this.textLaymanTerm.Size = new System.Drawing.Size(178, 20);
        this.textLaymanTerm.TabIndex = 50;
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(79, 124);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(124, 16);
        this.label2.TabIndex = 51;
        this.label2.Text = "Layman\'s Term";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsCanadianLab
        //
        this.checkIsCanadianLab.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsCanadianLab.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsCanadianLab.Location = new System.Drawing.Point(44, 296);
        this.checkIsCanadianLab.Name = "checkIsCanadianLab";
        this.checkIsCanadianLab.Size = new System.Drawing.Size(174, 18);
        this.checkIsCanadianLab.TabIndex = 52;
        this.checkIsCanadianLab.Text = "Is Lab Fee";
        this.checkIsCanadianLab.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(100, 146);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(103, 13);
        this.label16.TabIndex = 53;
        this.label16.Text = "Base Units";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBaseUnits
        //
        this.textBaseUnits.Location = new System.Drawing.Point(205, 143);
        this.textBaseUnits.Name = "textBaseUnits";
        this.textBaseUnits.Size = new System.Drawing.Size(30, 20);
        this.textBaseUnits.TabIndex = 54;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(241, 146);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(251, 17);
        this.label17.TabIndex = 55;
        this.label17.Text = "(zero unless for some medical claims)";
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(82, 66);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(121, 13);
        this.label18.TabIndex = 56;
        this.label18.Text = "Ins. Subst Code";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubstitutionCode
        //
        this.textSubstitutionCode.Location = new System.Drawing.Point(205, 63);
        this.textSubstitutionCode.MaxLength = 255;
        this.textSubstitutionCode.Name = "textSubstitutionCode";
        this.textSubstitutionCode.Size = new System.Drawing.Size(100, 20);
        this.textSubstitutionCode.TabIndex = 57;
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(306, 64);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(46, 18);
        this.label19.TabIndex = 58;
        this.label19.Text = "Only if";
        this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // comboSubstOnlyIf
        //
        this.comboSubstOnlyIf.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSubstOnlyIf.FormattingEnabled = true;
        this.comboSubstOnlyIf.Location = new System.Drawing.Point(347, 62);
        this.comboSubstOnlyIf.Name = "comboSubstOnlyIf";
        this.comboSubstOnlyIf.Size = new System.Drawing.Size(145, 21);
        this.comboSubstOnlyIf.TabIndex = 61;
        //
        // checkMultiVisit
        //
        this.checkMultiVisit.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkMultiVisit.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkMultiVisit.Location = new System.Drawing.Point(60, 224);
        this.checkMultiVisit.Name = "checkMultiVisit";
        this.checkMultiVisit.Size = new System.Drawing.Size(158, 18);
        this.checkMultiVisit.TabIndex = 62;
        this.checkMultiVisit.Text = "Multi Visit";
        this.checkMultiVisit.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkMultiVisit.UseVisualStyleBackColor = true;
        //
        // butAddNote
        //
        this.butAddNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNote.setAutosize(true);
        this.butAddNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNote.setCornerRadius(4F);
        this.butAddNote.Image = Resources.getAdd();
        this.butAddNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNote.Location = new System.Drawing.Point(600, 450);
        this.butAddNote.Name = "butAddNote";
        this.butAddNote.Size = new System.Drawing.Size(88, 26);
        this.butAddNote.TabIndex = 60;
        this.butAddNote.Text = "Add Note";
        this.butAddNote.Click += new System.EventHandler(this.butAddNote_Click);
        //
        // gridNotes
        //
        this.gridNotes.setHScrollVisible(false);
        this.gridNotes.Location = new System.Drawing.Point(44, 482);
        this.gridNotes.Name = "gridNotes";
        this.gridNotes.setScrollValue(0);
        this.gridNotes.Size = new System.Drawing.Size(676, 180);
        this.gridNotes.TabIndex = 59;
        this.gridNotes.setTitle("Notes and Times for Specific Providers");
        this.gridNotes.setTranslationName("TableProcedureNotes");
        this.gridNotes.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridNotes.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridNotes_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // tbTime
        //
        this.tbTime.BackColor = System.Drawing.SystemColors.Window;
        this.tbTime.Location = new System.Drawing.Point(10, 108);
        this.tbTime.Name = "tbTime";
        this.tbTime.setScrollValue(150);
        this.tbTime.setSelectedIndices(new int[0]);
        this.tbTime.setSelectionMode(System.Windows.Forms.SelectionMode.None);
        this.tbTime.Size = new System.Drawing.Size(15, 561);
        this.tbTime.TabIndex = 30;
        //
        // butColorClear
        //
        this.butColorClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColorClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butColorClear.setAutosize(true);
        this.butColorClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColorClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColorClear.setCornerRadius(4F);
        this.butColorClear.Location = new System.Drawing.Point(230, 204);
        this.butColorClear.Name = "butColorClear";
        this.butColorClear.Size = new System.Drawing.Size(50, 20);
        this.butColorClear.TabIndex = 49;
        this.butColorClear.Text = "none";
        this.butColorClear.Click += new System.EventHandler(this.butColorClear_Click);
        //
        // gridFees
        //
        this.gridFees.setHScrollVisible(false);
        this.gridFees.Location = new System.Drawing.Point(739, 31);
        this.gridFees.Name = "gridFees";
        this.gridFees.setScrollValue(0);
        this.gridFees.Size = new System.Drawing.Size(199, 445);
        this.gridFees.TabIndex = 44;
        this.gridFees.setTitle("Fees");
        this.gridFees.setTranslationName("TableProcFee");
        this.gridFees.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridFees.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridFees_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(44, 372);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(550, 104);
        this.textNote.TabIndex = 40;
        this.textNote.Text = "";
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
        this.butCancel.Location = new System.Drawing.Point(850, 636);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 11;
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
        this.butOK.Location = new System.Drawing.Point(850, 600);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 10;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelDrugNDC
        //
        this.labelDrugNDC.Location = new System.Drawing.Point(100, 166);
        this.labelDrugNDC.Name = "labelDrugNDC";
        this.labelDrugNDC.Size = new System.Drawing.Size(103, 13);
        this.labelDrugNDC.TabIndex = 53;
        this.labelDrugNDC.Text = "Drug NDC";
        this.labelDrugNDC.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelRevenueCode
        //
        this.labelRevenueCode.Location = new System.Drawing.Point(57, 186);
        this.labelRevenueCode.Name = "labelRevenueCode";
        this.labelRevenueCode.Size = new System.Drawing.Size(146, 13);
        this.labelRevenueCode.TabIndex = 53;
        this.labelRevenueCode.Text = "Default Revenue Code";
        this.labelRevenueCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDrugNDC
        //
        this.textDrugNDC.Location = new System.Drawing.Point(205, 163);
        this.textDrugNDC.Name = "textDrugNDC";
        this.textDrugNDC.Size = new System.Drawing.Size(100, 20);
        this.textDrugNDC.TabIndex = 54;
        //
        // textRevenueCode
        //
        this.textRevenueCode.Location = new System.Drawing.Point(205, 183);
        this.textRevenueCode.Name = "textRevenueCode";
        this.textRevenueCode.Size = new System.Drawing.Size(100, 20);
        this.textRevenueCode.TabIndex = 54;
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(311, 166);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(181, 17);
        this.label20.TabIndex = 55;
        this.label20.Text = "(11 digits or blank)";
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(224, 278);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(181, 17);
        this.label21.TabIndex = 63;
        this.label21.Text = "(crown, bridge, denture, RPD)";
        this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // comboProvNumDefault
        //
        this.comboProvNumDefault.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvNumDefault.FormattingEnabled = true;
        this.comboProvNumDefault.Location = new System.Drawing.Point(205, 314);
        this.comboProvNumDefault.Name = "comboProvNumDefault";
        this.comboProvNumDefault.Size = new System.Drawing.Size(121, 21);
        this.comboProvNumDefault.TabIndex = 64;
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(100, 319);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(103, 13);
        this.label22.TabIndex = 65;
        this.label22.Text = "Assign To Prov";
        this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butAuditTrail
        //
        this.butAuditTrail.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAuditTrail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAuditTrail.setAutosize(true);
        this.butAuditTrail.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAuditTrail.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAuditTrail.setCornerRadius(4F);
        this.butAuditTrail.Location = new System.Drawing.Point(850, 482);
        this.butAuditTrail.Name = "butAuditTrail";
        this.butAuditTrail.Size = new System.Drawing.Size(75, 26);
        this.butAuditTrail.TabIndex = 10;
        this.butAuditTrail.Text = "Audit Trail";
        this.butAuditTrail.Click += new System.EventHandler(this.butAuditTrail_Click);
        //
        // FormProcCodeEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(941, 707);
        this.Controls.Add(this.label22);
        this.Controls.Add(this.comboProvNumDefault);
        this.Controls.Add(this.label21);
        this.Controls.Add(this.checkMultiVisit);
        this.Controls.Add(this.comboSubstOnlyIf);
        this.Controls.Add(this.butAddNote);
        this.Controls.Add(this.gridNotes);
        this.Controls.Add(this.label19);
        this.Controls.Add(this.textSubstitutionCode);
        this.Controls.Add(this.butSlider);
        this.Controls.Add(this.tbTime);
        this.Controls.Add(this.label18);
        this.Controls.Add(this.label20);
        this.Controls.Add(this.label17);
        this.Controls.Add(this.textRevenueCode);
        this.Controls.Add(this.textDrugNDC);
        this.Controls.Add(this.textBaseUnits);
        this.Controls.Add(this.labelRevenueCode);
        this.Controls.Add(this.labelDrugNDC);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.checkIsCanadianLab);
        this.Controls.Add(this.textLaymanTerm);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butColorClear);
        this.Controls.Add(this.labelColor);
        this.Controls.Add(this.butColor);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.listPaintType);
        this.Controls.Add(this.gridFees);
        this.Controls.Add(this.textMedicalCode);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.checkIsProsth);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.textAlternateCode1);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.checkIsHygiene);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textTime2);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.textAbbrev);
        this.Controls.Add(this.textProcCode);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.listCategory);
        this.Controls.Add(this.checkNoBillIns);
        this.Controls.Add(this.listTreatArea);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butAuditTrail);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcCodeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Procedure Code";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProcCodeEdit_Closing);
        this.Load += new System.EventHandler(this.FormProcCodeEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProcCodeEdit_Load(Object sender, System.EventArgs e) throws Exception {
        List<ProcedureCode> listCodes = CDT.Class1.GetADAcodes();
        if (listCodes.Count > 0 && ProcCode.ProcCode.Length == 5 && StringSupport.equals(ProcCode.ProcCode.Substring(0, 1), "D"))
        {
            for (int i = 0;i < listCodes.Count;i++)
            {
                if (StringSupport.equals(listCodes[i].ProcCode, ProcCode.ProcCode))
                {
                    textDescription.ReadOnly = true;
                }
                 
            }
        }
         
        textProcCode.Text = ProcCode.ProcCode;
        textAlternateCode1.Text = ProcCode.AlternateCode1;
        textMedicalCode.Text = ProcCode.MedicalCode;
        textSubstitutionCode.Text = ProcCode.SubstitutionCode;
        for (int i = 0;i < Enum.GetNames(SubstitutionCondition.class).Length;i++)
        {
            comboSubstOnlyIf.Items.Add(Lan.g("enumSubstitutionCondition", Enum.GetNames(SubstitutionCondition.class)[i]));
        }
        comboSubstOnlyIf.SelectedIndex = ((Enum)ProcCode.SubstOnlyIf).ordinal();
        textDescription.Text = ProcCode.Descript;
        textAbbrev.Text = ProcCode.AbbrDesc;
        textLaymanTerm.Text = ProcCode.LaymanTerm;
        strBTime = new StringBuilder(ProcCode.ProcTime);
        butColor.BackColor = ProcCode.GraphicColor;
        checkMultiVisit.Checked = ProcCode.IsMultiVisit;
        checkMultiVisit.Visible = Programs.getUsingOrion();
        checkNoBillIns.Checked = ProcCode.NoBillIns;
        checkIsHygiene.Checked = ProcCode.IsHygiene;
        checkIsProsth.Checked = ProcCode.IsProsth;
        textBaseUnits.Text = ProcCode.BaseUnits.ToString();
        textDrugNDC.Text = ProcCode.DrugNDC;
        textRevenueCode.Text = ProcCode.RevenueCodeDefault;
        if (!CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Not Canadian. en-CA or fr-CA
            checkIsCanadianLab.Visible = false;
        }
         
        //else {//always enabled
        //checkIsCanadianLab.Enabled=IsNew || !Procedures.IsUsingCode(ProcCode.CodeNum);
        //}
        checkIsCanadianLab.Checked = ProcCode.IsCanadianLab;
        textNote.Text = ProcCode.DefaultNote;
        listTreatArea.Items.Clear();
        for (int i = 1;i < Enum.GetNames(TreatmentArea.class).Length;i++)
        {
            listTreatArea.Items.Add(Lan.g("enumTreatmentArea", Enum.GetNames(TreatmentArea.class)[i]));
        }
        listTreatArea.SelectedIndex = ((Enum)ProcCode.TreatArea).ordinal() - 1;
        if (listTreatArea.SelectedIndex == -1)
            listTreatArea.SelectedIndex = 2;
         
        for (int i = 0;i < Enum.GetNames(ToothPaintingType.class).Length;i++)
        {
            listPaintType.Items.Add(Enum.GetNames(ToothPaintingType.class)[i]);
            if (((Enum)ProcCode.PaintType).ordinal() == i)
            {
                listPaintType.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()].Length;i++)
        {
            listCategory.Items.Add(DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()][i].DefNum == ProcCode.ProcCat)
            {
                listCategory.SelectedIndex = i;
            }
             
        }
        if (listCategory.SelectedIndex == -1)
        {
            listCategory.SelectedIndex = 0;
        }
         
        comboProvNumDefault.Items.Add("none");
        comboProvNumDefault.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNumDefault.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == ProcCode.ProvNumDefault)
            {
                comboProvNumDefault.SelectedIndex = i + 1;
            }
             
        }
        //List starts with None at the top.
        fillTime();
        fillFees();
        fillNotes();
    }

    private void fillTime() throws Exception {
        for (int i = 0;i < strBTime.Length;i++)
        {
            tbTime.Cell[0, i] = strBTime.ToString(i, 1);
            tbTime.BackGColor[0, i] = Color.White;
        }
        for (int i = strBTime.Length;i < tbTime.MaxRows;i++)
        {
            tbTime.Cell[0, i] = "";
            tbTime.BackGColor[0, i] = Color.FromName("Control");
        }
        tbTime.Refresh();
        butSlider.Location = new Point(tbTime.Location.X + 2, (tbTime.Location.Y + strBTime.Length * 14 + 1));
        textTime2.Text = (strBTime.Length * ApptDrawing.MinPerIncr).ToString();
    }

    private void fillFees() throws Exception {
        //This line will be added later for speed:
        //DataTable feeList=Fees.GetListForCode(ProcCode.Code);
        gridFees.beginUpdate();
        gridFees.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProcFee","Sched"),120);
        gridFees.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcFee","Amount"), 60, HorizontalAlignment.Right);
        gridFees.getColumns().add(col);
        gridFees.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Fee fee;
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            fee = Fees.GetFee(ProcCode.CodeNum, FeeSchedC.getListShort()[i].FeeSchedNum);
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(FeeSchedC.getListShort()[i].Description);
            if (fee == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(fee.Amount.ToString("n"));
            } 
            gridFees.getRows().add(row);
        }
        gridFees.endUpdate();
    }

    private void gridFees_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Fee FeeCur = Fees.GetFee(ProcCode.CodeNum, FeeSchedC.getListShort()[e.getRow()].FeeSchedNum);
        //tbFees.SelectedRow=e.Row;
        //tbFees.ColorRow(e.Row,Color.LightGray);
        FormFeeEdit FormFE = new FormFeeEdit();
        if (FeeCur == null)
        {
            FeeCur = new Fee();
            FeeCur.FeeSched = FeeSchedC.getListShort()[e.getRow()].FeeSchedNum;
            FeeCur.CodeNum = ProcCode.CodeNum;
            Fees.insert(FeeCur);
            //SecurityLog is updated in FormFeeEdit.
            FormFE.IsNew = true;
        }
         
        FormFE.FeeCur = FeeCur;
        FormFE.ShowDialog();
        if (FormFE.DialogResult == DialogResult.OK)
        {
            FeeChanged = true;
        }
         
        Fees.refreshCache();
        //tbFees.SelectedRow=-1;
        fillFees();
    }

    private void fillNotes() throws Exception {
        NoteList = ProcCodeNotes.getList(ProcCode.CodeNum);
        gridNotes.beginUpdate();
        gridNotes.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProcedureNotes","Prov"),80);
        gridNotes.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcedureNotes","Time"),150);
        gridNotes.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcedureNotes","Note"),400);
        gridNotes.getColumns().add(col);
        gridNotes.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < NoteList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Providers.GetAbbr(NoteList[i].ProvNum));
            row.getCells().Add(NoteList[i].ProcTime);
            row.getCells().Add(NoteList[i].Note);
            gridNotes.getRows().add(row);
        }
        gridNotes.endUpdate();
    }

    private void tbTime_CellClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        if (e.getRow() < strBTime.Length)
        {
            if (strBTime[e.getRow()] == '/')
            {
                strBTime.Replace('/', 'X', e.getRow(), 1);
            }
            else
            {
                strBTime.Replace(strBTime[e.getRow()], '/', e.getRow(), 1);
            } 
        }
         
        fillTime();
    }

    private void butSlider_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = true;
        mouseOrigin = new Point(e.X + butSlider.Location.X, e.Y + butSlider.Location.Y);
        sliderOrigin = butSlider.Location;
    }

    private void butSlider_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
            return ;
         
        //tempPoint represents the new location of button of smooth dragging.
        Point tempPoint = new Point(sliderOrigin.X, sliderOrigin.Y + (e.Y + butSlider.Location.Y) - mouseOrigin.Y);
        int step = (int)(Math.Round((Decimal)(tempPoint.Y - tbTime.Location.Y) / 14));
        if (step == strBTime.Length)
            return ;
         
        if (step < 1)
            return ;
         
        if (step > tbTime.MaxRows - 1)
            return ;
         
        if (step > strBTime.Length)
        {
            strBTime.Append('/');
        }
         
        if (step < strBTime.Length)
        {
            strBTime.Remove(step, 1);
        }
         
        fillTime();
    }

    private void butSlider_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = false;
    }

    private void butColor_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog colorDialog1 = new ColorDialog();
        colorDialog1.Color = butColor.BackColor;
        colorDialog1.ShowDialog();
        butColor.BackColor = colorDialog1.Color;
    }

    private void butColorClear_Click(Object sender, EventArgs e) throws Exception {
        butColor.BackColor = Color.FromArgb(0);
    }

    private void butAddNote_Click(Object sender, EventArgs e) throws Exception {
        FormProcCodeNoteEdit FormP = new FormProcCodeNoteEdit();
        FormP.IsNew = true;
        FormP.NoteCur = new ProcCodeNote();
        FormP.NoteCur.CodeNum = ProcCode.CodeNum;
        FormP.NoteCur.Note = textNote.Text;
        FormP.NoteCur.ProcTime = strBTime.ToString();
        FormP.ShowDialog();
        fillNotes();
    }

    private void gridNotes_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormProcCodeNoteEdit FormP = new FormProcCodeNoteEdit();
        FormP.NoteCur = NoteList[e.getRow()].Copy();
        FormP.ShowDialog();
        fillNotes();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textMedicalCode.Text, "") && !ProcedureCodeC.getHList().Contains(textMedicalCode.Text))
        {
            MsgBox.show(this,"Invalid medical code.  It must refer to an existing procedure code entered separately");
            return ;
        }
         
        if (!StringSupport.equals(textSubstitutionCode.Text, "") && !ProcedureCodeC.getHList().Contains(textSubstitutionCode.Text))
        {
            MsgBox.show(this,"Invalid substitution code.  It must refer to an existing procedure code entered separately");
            return ;
        }
         
        /*bool DoSynchRecall=false;
        			if(IsNew && checkSetRecall.Checked){
        				DoSynchRecall=true;
        			}
        			else if(ProcCode.SetRecall!=checkSetRecall.Checked){//set recall changed
        				DoSynchRecall=true;
        			}
        			if(DoSynchRecall){
        				if(!MsgBox.Show(this,true,"Because you have changed the recall setting for this procedure code, all your patient recalls will be resynchronized, which can take a minute or two.  Do you want to continue?")){
        					return;
        				}
        			}*/
        ProcCode.AlternateCode1 = textAlternateCode1.Text;
        ProcCode.MedicalCode = textMedicalCode.Text;
        ProcCode.SubstitutionCode = textSubstitutionCode.Text;
        ProcCode.SubstOnlyIf = (SubstitutionCondition)comboSubstOnlyIf.SelectedIndex;
        ProcCode.Descript = textDescription.Text;
        ProcCode.AbbrDesc = textAbbrev.Text;
        ProcCode.LaymanTerm = textLaymanTerm.Text;
        ProcCode.ProcTime = strBTime.ToString();
        ProcCode.GraphicColor = butColor.BackColor;
        ProcCode.IsMultiVisit = checkMultiVisit.Checked;
        ProcCode.NoBillIns = checkNoBillIns.Checked;
        ProcCode.IsProsth = checkIsProsth.Checked;
        ProcCode.IsHygiene = checkIsHygiene.Checked;
        ProcCode.IsCanadianLab = checkIsCanadianLab.Checked;
        ProcCode.DefaultNote = textNote.Text;
        ProcCode.PaintType = (ToothPaintingType)listPaintType.SelectedIndex;
        ProcCode.TreatArea = (TreatmentArea)listTreatArea.SelectedIndex + 1;
        ProcCode.BaseUnits = PIn.Int(textBaseUnits.Text.ToString());
        ProcCode.DrugNDC = textDrugNDC.Text;
        ProcCode.RevenueCodeDefault = textRevenueCode.Text;
        if (listCategory.SelectedIndex != -1)
        {
            ProcCode.ProcCat = DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()][listCategory.SelectedIndex].DefNum;
        }
         
        if (comboProvNumDefault.SelectedIndex == 0)
        {
            ProcCode.ProvNumDefault = 0;
        }
        else
        {
            ProcCode.ProvNumDefault = ProviderC.getListShort()[comboProvNumDefault.SelectedIndex - 1].ProvNum;
        } 
        ProcedureCodes.update(ProcCode);
        //whether new or not.
        //if(DoSynchRecall){
        //	Cursor=Cursors.WaitCursor;
        //	Recalls.SynchAllPatients();
        //	Cursor=Cursors.Default;
        //}
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formProcCodeEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (FeeChanged)
        {
            DataValid.setInvalid(InvalidType.Fees);
            DialogResult = DialogResult.OK;
        }
         
    }

    private void butAuditTrail_Click(Object sender, EventArgs e) throws Exception {
        List<Permissions> perms = new List<Permissions>();
        perms.Add(Permissions.ProcFeeEdit);
        FormAuditOneType FormA = new FormAuditOneType(0, perms, Lan.g(this,"All changes for") + " " + ProcCode.AbbrDesc + " - " + ProcCode.ProcCode, ProcCode.CodeNum);
        FormA.ShowDialog();
    }

}


