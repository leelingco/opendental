//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:29 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormPerio;
import OpenDental.FormPerioEdit;
import OpenDental.FormPerioGraphical;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDentBusiness.BleedingFlags;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.DrawingMode;
import OpenDentBusiness.ImageCategorySpecial;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PerioExam;
import OpenDentBusiness.PerioExams;
import OpenDentBusiness.PerioMeasures;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPerio  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button but7;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioLeft = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioRight = new System.Windows.Forms.RadioButton();
    private OpenDental.UI.Button but3;
    private OpenDental.UI.Button but2;
    private OpenDental.UI.Button but1;
    private OpenDental.UI.Button but6;
    private OpenDental.UI.Button but9;
    private OpenDental.UI.Button but5;
    private OpenDental.UI.Button but4;
    private OpenDental.UI.Button but8;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private System.Windows.Forms.Button butColorBleed = new System.Windows.Forms.Button();
    private System.Windows.Forms.ColorDialog colorDialog1 = new System.Windows.Forms.ColorDialog();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butColorPus = new System.Windows.Forms.Button();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button but0;
    private OpenDental.UI.Button but10;
    private OpenDental.UI.Button butBleed;
    private OpenDental.UI.Button butPus;
    private System.Windows.Forms.CheckBox checkThree = new System.Windows.Forms.CheckBox();
    private boolean localDefsChanged = new boolean();
    private System.Windows.Forms.ListBox listExams = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butSkip;
    private OpenDental.UI.Button butPrint;
    private System.Windows.Forms.Button butColorCalculus = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butColorPlaque = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butCalculus;
    private OpenDental.UI.Button butPlaque;
    private System.Windows.Forms.TextBox textIndexPlaque = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textIndexSupp = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textIndexBleeding = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textIndexCalculus = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCalcIndex;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textRedProb = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCount;
    private System.Windows.Forms.DomainUpDown updownProb = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textCountProb = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCountMGJ = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DomainUpDown updownMGJ = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textRedMGJ = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCountGing = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DomainUpDown updownGing = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textRedGing = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCountCAL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DomainUpDown updownCAL = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textRedCAL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCountFurc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DomainUpDown updownFurc = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textRedFurc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCountMob = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.DomainUpDown updownMob = new System.Windows.Forms.DomainUpDown();
    private System.Windows.Forms.TextBox textRedMob = new System.Windows.Forms.TextBox();
    //private OpenDental.ContrPerio gridP;
    //private OpenDental.ContrPerio contrPerio1;
    private OpenDental.ContrPerio gridP;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private System.Windows.Forms.PrintDialog printDialog2 = new System.Windows.Forms.PrintDialog();
    private boolean TenIsDown = new boolean();
    private System.Windows.Forms.PrintPreviewDialog printPreviewDlg = new System.Windows.Forms.PrintPreviewDialog();
    //private int pagesPrinted;
    private List<String> MissingTeeth = new List<String>();
    private Patient PatCur;
    private OpenDental.UI.Button butGraphical;
    private OpenDental.UI.Button butSave;
    private Label labelPlaqueHistory = new Label();
    private ListBox listPlaqueHistory = new ListBox();
    private CheckBox checkGingMarg = new CheckBox();
    private PerioExam PerioExamCur;
    /**
    * 
    */
    public FormPerio(Patient patCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        PatCur = patCur;
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPerio.class);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioRight = new System.Windows.Forms.RadioButton();
        this.radioLeft = new System.Windows.Forms.RadioButton();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.butColorBleed = new System.Windows.Forms.Button();
        this.butColorPus = new System.Windows.Forms.Button();
        this.butColorCalculus = new System.Windows.Forms.Button();
        this.butColorPlaque = new System.Windows.Forms.Button();
        this.checkThree = new System.Windows.Forms.CheckBox();
        this.butSave = new OpenDental.UI.Button();
        this.butCalcIndex = new OpenDental.UI.Button();
        this.butCalculus = new OpenDental.UI.Button();
        this.butPlaque = new OpenDental.UI.Button();
        this.butSkip = new OpenDental.UI.Button();
        this.butCount = new OpenDental.UI.Button();
        this.butPus = new OpenDental.UI.Button();
        this.butBleed = new OpenDental.UI.Button();
        this.but10 = new OpenDental.UI.Button();
        this.checkGingMarg = new System.Windows.Forms.CheckBox();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textCountMob = new System.Windows.Forms.TextBox();
        this.updownMob = new System.Windows.Forms.DomainUpDown();
        this.textRedMob = new System.Windows.Forms.TextBox();
        this.textCountFurc = new System.Windows.Forms.TextBox();
        this.updownFurc = new System.Windows.Forms.DomainUpDown();
        this.textRedFurc = new System.Windows.Forms.TextBox();
        this.textCountCAL = new System.Windows.Forms.TextBox();
        this.updownCAL = new System.Windows.Forms.DomainUpDown();
        this.textRedCAL = new System.Windows.Forms.TextBox();
        this.textCountGing = new System.Windows.Forms.TextBox();
        this.updownGing = new System.Windows.Forms.DomainUpDown();
        this.textRedGing = new System.Windows.Forms.TextBox();
        this.textCountMGJ = new System.Windows.Forms.TextBox();
        this.updownMGJ = new System.Windows.Forms.DomainUpDown();
        this.textRedMGJ = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.textCountProb = new System.Windows.Forms.TextBox();
        this.updownProb = new System.Windows.Forms.DomainUpDown();
        this.label13 = new System.Windows.Forms.Label();
        this.textRedProb = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.listExams = new System.Windows.Forms.ListBox();
        this.textIndexPlaque = new System.Windows.Forms.TextBox();
        this.textIndexSupp = new System.Windows.Forms.TextBox();
        this.textIndexBleeding = new System.Windows.Forms.TextBox();
        this.textIndexCalculus = new System.Windows.Forms.TextBox();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.printDialog2 = new System.Windows.Forms.PrintDialog();
        this.printPreviewDlg = new System.Windows.Forms.PrintPreviewDialog();
        this.butGraphical = new OpenDental.UI.Button();
        this.gridP = new OpenDental.ContrPerio();
        this.butPrint = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.but0 = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.but8 = new OpenDental.UI.Button();
        this.but4 = new OpenDental.UI.Button();
        this.but5 = new OpenDental.UI.Button();
        this.but9 = new OpenDental.UI.Button();
        this.but6 = new OpenDental.UI.Button();
        this.but1 = new OpenDental.UI.Button();
        this.but2 = new OpenDental.UI.Button();
        this.but3 = new OpenDental.UI.Button();
        this.but7 = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.labelPlaqueHistory = new System.Windows.Forms.Label();
        this.listPlaqueHistory = new System.Windows.Forms.ListBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioRight);
        this.groupBox1.Controls.Add(this.radioLeft);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(765, 4);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(185, 43);
        this.groupBox1.TabIndex = 13;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Auto Advance";
        //
        // radioRight
        //
        this.radioRight.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioRight.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioRight.Location = new System.Drawing.Point(10, 20);
        this.radioRight.Name = "radioRight";
        this.radioRight.Size = new System.Drawing.Size(75, 18);
        this.radioRight.TabIndex = 1;
        this.radioRight.Text = "Right";
        this.radioRight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioRight.Click += new System.EventHandler(this.radioRight_Click);
        //
        // radioLeft
        //
        this.radioLeft.Checked = true;
        this.radioLeft.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioLeft.Location = new System.Drawing.Point(98, 20);
        this.radioLeft.Name = "radioLeft";
        this.radioLeft.Size = new System.Drawing.Size(75, 18);
        this.radioLeft.TabIndex = 0;
        this.radioLeft.TabStop = true;
        this.radioLeft.Text = "Left";
        this.radioLeft.Click += new System.EventHandler(this.radioLeft_Click);
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(136, 102);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(18, 23);
        this.label2.TabIndex = 35;
        this.label2.Text = "F";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label3.Location = new System.Drawing.Point(136, 562);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(18, 23);
        this.label3.TabIndex = 36;
        this.label3.Text = "F";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label4.Location = new System.Drawing.Point(136, 428);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(18, 23);
        this.label4.TabIndex = 37;
        this.label4.Text = "L";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label5.Location = new System.Drawing.Point(136, 222);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(18, 23);
        this.label5.TabIndex = 38;
        this.label5.Text = "L";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorBleed
        //
        this.butColorBleed.BackColor = System.Drawing.Color.Red;
        this.butColorBleed.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorBleed.Location = new System.Drawing.Point(850, 302);
        this.butColorBleed.Name = "butColorBleed";
        this.butColorBleed.Size = new System.Drawing.Size(12, 24);
        this.butColorBleed.TabIndex = 43;
        this.toolTip1.SetToolTip(this.butColorBleed, "Edit Color");
        this.butColorBleed.UseVisualStyleBackColor = false;
        this.butColorBleed.Click += new System.EventHandler(this.butColorBleed_Click);
        //
        // butColorPus
        //
        this.butColorPus.BackColor = System.Drawing.Color.Gold;
        this.butColorPus.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorPus.Location = new System.Drawing.Point(850, 332);
        this.butColorPus.Name = "butColorPus";
        this.butColorPus.Size = new System.Drawing.Size(12, 24);
        this.butColorPus.TabIndex = 50;
        this.toolTip1.SetToolTip(this.butColorPus, "Edit Color");
        this.butColorPus.UseVisualStyleBackColor = false;
        this.butColorPus.Click += new System.EventHandler(this.butColorPus_Click);
        //
        // butColorCalculus
        //
        this.butColorCalculus.BackColor = System.Drawing.Color.Green;
        this.butColorCalculus.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorCalculus.Location = new System.Drawing.Point(850, 272);
        this.butColorCalculus.Name = "butColorCalculus";
        this.butColorCalculus.Size = new System.Drawing.Size(12, 24);
        this.butColorCalculus.TabIndex = 67;
        this.toolTip1.SetToolTip(this.butColorCalculus, "Edit Color");
        this.butColorCalculus.UseVisualStyleBackColor = false;
        this.butColorCalculus.Click += new System.EventHandler(this.butColorCalculus_Click);
        //
        // butColorPlaque
        //
        this.butColorPlaque.BackColor = System.Drawing.Color.RoyalBlue;
        this.butColorPlaque.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColorPlaque.Location = new System.Drawing.Point(850, 242);
        this.butColorPlaque.Name = "butColorPlaque";
        this.butColorPlaque.Size = new System.Drawing.Size(12, 24);
        this.butColorPlaque.TabIndex = 66;
        this.toolTip1.SetToolTip(this.butColorPlaque, "Edit Color");
        this.butColorPlaque.UseVisualStyleBackColor = false;
        this.butColorPlaque.Click += new System.EventHandler(this.butColorPlaque_Click);
        //
        // checkThree
        //
        this.checkThree.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkThree.Location = new System.Drawing.Point(765, 49);
        this.checkThree.Name = "checkThree";
        this.checkThree.Size = new System.Drawing.Size(100, 19);
        this.checkThree.TabIndex = 57;
        this.checkThree.Text = "Triplets";
        this.toolTip1.SetToolTip(this.checkThree, "Enter numbers three at a time");
        this.checkThree.Click += new System.EventHandler(this.checkThree_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(764, 616);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(88, 24);
        this.butSave.TabIndex = 77;
        this.butSave.Text = "Save to Images";
        this.toolTip1.SetToolTip(this.butSave, "Toggle the selected teeth as skipped");
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butCalcIndex
        //
        this.butCalcIndex.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCalcIndex.setAutosize(true);
        this.butCalcIndex.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCalcIndex.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCalcIndex.setCornerRadius(4F);
        this.butCalcIndex.Location = new System.Drawing.Point(863, 215);
        this.butCalcIndex.Name = "butCalcIndex";
        this.butCalcIndex.Size = new System.Drawing.Size(84, 24);
        this.butCalcIndex.TabIndex = 74;
        this.butCalcIndex.Text = "Calc Index %";
        this.toolTip1.SetToolTip(this.butCalcIndex, "Calculate the Index for all four types");
        this.butCalcIndex.Click += new System.EventHandler(this.butCalcIndex_Click);
        //
        // butCalculus
        //
        this.butCalculus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCalculus.setAutosize(true);
        this.butCalculus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCalculus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCalculus.setCornerRadius(4F);
        this.butCalculus.Location = new System.Drawing.Point(762, 272);
        this.butCalculus.Name = "butCalculus";
        this.butCalculus.Size = new System.Drawing.Size(88, 24);
        this.butCalculus.TabIndex = 65;
        this.butCalculus.Text = "Calculus";
        this.toolTip1.SetToolTip(this.butCalculus, "C on your keyboard");
        this.butCalculus.Click += new System.EventHandler(this.butCalculus_Click);
        //
        // butPlaque
        //
        this.butPlaque.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPlaque.setAutosize(true);
        this.butPlaque.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPlaque.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPlaque.setCornerRadius(4F);
        this.butPlaque.Location = new System.Drawing.Point(762, 242);
        this.butPlaque.Name = "butPlaque";
        this.butPlaque.Size = new System.Drawing.Size(88, 24);
        this.butPlaque.TabIndex = 64;
        this.butPlaque.Text = "Plaque";
        this.toolTip1.SetToolTip(this.butPlaque, "P on your keyboard");
        this.butPlaque.Click += new System.EventHandler(this.butPlaque_Click);
        //
        // butSkip
        //
        this.butSkip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSkip.setAutosize(true);
        this.butSkip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSkip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSkip.setCornerRadius(4F);
        this.butSkip.Location = new System.Drawing.Point(764, 580);
        this.butSkip.Name = "butSkip";
        this.butSkip.Size = new System.Drawing.Size(88, 24);
        this.butSkip.TabIndex = 61;
        this.butSkip.Text = "SkipTeeth";
        this.toolTip1.SetToolTip(this.butSkip, "Toggle the selected teeth as skipped");
        this.butSkip.Click += new System.EventHandler(this.butSkip_Click);
        //
        // butCount
        //
        this.butCount.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCount.setAutosize(true);
        this.butCount.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCount.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCount.setCornerRadius(4F);
        this.butCount.Location = new System.Drawing.Point(92, 18);
        this.butCount.Name = "butCount";
        this.butCount.Size = new System.Drawing.Size(84, 24);
        this.butCount.TabIndex = 1;
        this.butCount.Text = "Count Teeth";
        this.toolTip1.SetToolTip(this.butCount, "Count all six types");
        this.butCount.Click += new System.EventHandler(this.butCount_Click);
        //
        // butPus
        //
        this.butPus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPus.setAutosize(true);
        this.butPus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPus.setCornerRadius(4F);
        this.butPus.Location = new System.Drawing.Point(762, 332);
        this.butPus.Name = "butPus";
        this.butPus.Size = new System.Drawing.Size(88, 24);
        this.butPus.TabIndex = 42;
        this.butPus.Text = "Suppuration";
        this.toolTip1.SetToolTip(this.butPus, "S on your keyboard");
        this.butPus.Click += new System.EventHandler(this.butPus_Click);
        //
        // butBleed
        //
        this.butBleed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBleed.setAutosize(true);
        this.butBleed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBleed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBleed.setCornerRadius(4F);
        this.butBleed.Location = new System.Drawing.Point(762, 302);
        this.butBleed.Name = "butBleed";
        this.butBleed.Size = new System.Drawing.Size(88, 24);
        this.butBleed.TabIndex = 41;
        this.butBleed.Text = "Bleeding";
        this.toolTip1.SetToolTip(this.butBleed, "Space bar or B on your keyboard");
        this.butBleed.Click += new System.EventHandler(this.butBleed_Click);
        //
        // but10
        //
        this.but10.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but10.setAutosize(true);
        this.but10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but10.setCornerRadius(4F);
        this.but10.Location = new System.Drawing.Point(833, 173);
        this.but10.Name = "but10";
        this.but10.Size = new System.Drawing.Size(32, 32);
        this.but10.TabIndex = 40;
        this.but10.Text = "10";
        this.toolTip1.SetToolTip(this.but10, "Or hold down the Ctrl key");
        this.but10.Click += new System.EventHandler(this.but10_Click);
        //
        // checkGingMarg
        //
        this.checkGingMarg.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkGingMarg.Location = new System.Drawing.Point(871, 49);
        this.checkGingMarg.Name = "checkGingMarg";
        this.checkGingMarg.Size = new System.Drawing.Size(99, 19);
        this.checkGingMarg.TabIndex = 80;
        this.checkGingMarg.Text = "Ging Marg +";
        this.toolTip1.SetToolTip(this.checkGingMarg, "Or hold down the Ctrl key while you type numbers.  Affects gingival margins only." + "  Used to input positive gingival margins (hyperplasia).");
        this.checkGingMarg.Click += new System.EventHandler(this.checkGingMarg_CheckedChanged);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textCountMob);
        this.groupBox2.Controls.Add(this.updownMob);
        this.groupBox2.Controls.Add(this.textRedMob);
        this.groupBox2.Controls.Add(this.textCountFurc);
        this.groupBox2.Controls.Add(this.updownFurc);
        this.groupBox2.Controls.Add(this.textRedFurc);
        this.groupBox2.Controls.Add(this.textCountCAL);
        this.groupBox2.Controls.Add(this.updownCAL);
        this.groupBox2.Controls.Add(this.textRedCAL);
        this.groupBox2.Controls.Add(this.textCountGing);
        this.groupBox2.Controls.Add(this.updownGing);
        this.groupBox2.Controls.Add(this.textRedGing);
        this.groupBox2.Controls.Add(this.textCountMGJ);
        this.groupBox2.Controls.Add(this.updownMGJ);
        this.groupBox2.Controls.Add(this.textRedMGJ);
        this.groupBox2.Controls.Add(this.label14);
        this.groupBox2.Controls.Add(this.textCountProb);
        this.groupBox2.Controls.Add(this.updownProb);
        this.groupBox2.Controls.Add(this.label13);
        this.groupBox2.Controls.Add(this.textRedProb);
        this.groupBox2.Controls.Add(this.label12);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.Controls.Add(this.label11);
        this.groupBox2.Controls.Add(this.label10);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.butCount);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(764, 371);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(196, 201);
        this.groupBox2.TabIndex = 49;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Numbers in red";
        //
        // textCountMob
        //
        this.textCountMob.Location = new System.Drawing.Point(141, 170);
        this.textCountMob.Name = "textCountMob";
        this.textCountMob.ReadOnly = true;
        this.textCountMob.Size = new System.Drawing.Size(34, 20);
        this.textCountMob.TabIndex = 26;
        //
        // updownMob
        //
        this.updownMob.InterceptArrowKeys = false;
        this.updownMob.Location = new System.Drawing.Point(97, 170);
        this.updownMob.Name = "updownMob";
        this.updownMob.Size = new System.Drawing.Size(19, 20);
        this.updownMob.TabIndex = 25;
        this.updownMob.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // textRedMob
        //
        this.textRedMob.Location = new System.Drawing.Point(70, 170);
        this.textRedMob.Name = "textRedMob";
        this.textRedMob.ReadOnly = true;
        this.textRedMob.Size = new System.Drawing.Size(27, 20);
        this.textRedMob.TabIndex = 24;
        //
        // textCountFurc
        //
        this.textCountFurc.Location = new System.Drawing.Point(141, 150);
        this.textCountFurc.Name = "textCountFurc";
        this.textCountFurc.ReadOnly = true;
        this.textCountFurc.Size = new System.Drawing.Size(34, 20);
        this.textCountFurc.TabIndex = 23;
        //
        // updownFurc
        //
        this.updownFurc.InterceptArrowKeys = false;
        this.updownFurc.Location = new System.Drawing.Point(97, 150);
        this.updownFurc.Name = "updownFurc";
        this.updownFurc.Size = new System.Drawing.Size(19, 20);
        this.updownFurc.TabIndex = 22;
        this.updownFurc.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // textRedFurc
        //
        this.textRedFurc.Location = new System.Drawing.Point(70, 150);
        this.textRedFurc.Name = "textRedFurc";
        this.textRedFurc.ReadOnly = true;
        this.textRedFurc.Size = new System.Drawing.Size(27, 20);
        this.textRedFurc.TabIndex = 21;
        //
        // textCountCAL
        //
        this.textCountCAL.Location = new System.Drawing.Point(141, 130);
        this.textCountCAL.Name = "textCountCAL";
        this.textCountCAL.ReadOnly = true;
        this.textCountCAL.Size = new System.Drawing.Size(34, 20);
        this.textCountCAL.TabIndex = 20;
        //
        // updownCAL
        //
        this.updownCAL.InterceptArrowKeys = false;
        this.updownCAL.Location = new System.Drawing.Point(97, 130);
        this.updownCAL.Name = "updownCAL";
        this.updownCAL.Size = new System.Drawing.Size(19, 20);
        this.updownCAL.TabIndex = 19;
        this.updownCAL.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // textRedCAL
        //
        this.textRedCAL.Location = new System.Drawing.Point(70, 130);
        this.textRedCAL.Name = "textRedCAL";
        this.textRedCAL.ReadOnly = true;
        this.textRedCAL.Size = new System.Drawing.Size(27, 20);
        this.textRedCAL.TabIndex = 18;
        //
        // textCountGing
        //
        this.textCountGing.Location = new System.Drawing.Point(141, 110);
        this.textCountGing.Name = "textCountGing";
        this.textCountGing.ReadOnly = true;
        this.textCountGing.Size = new System.Drawing.Size(34, 20);
        this.textCountGing.TabIndex = 17;
        //
        // updownGing
        //
        this.updownGing.InterceptArrowKeys = false;
        this.updownGing.Location = new System.Drawing.Point(97, 110);
        this.updownGing.Name = "updownGing";
        this.updownGing.Size = new System.Drawing.Size(19, 20);
        this.updownGing.TabIndex = 16;
        this.updownGing.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // textRedGing
        //
        this.textRedGing.Location = new System.Drawing.Point(70, 110);
        this.textRedGing.Name = "textRedGing";
        this.textRedGing.ReadOnly = true;
        this.textRedGing.Size = new System.Drawing.Size(27, 20);
        this.textRedGing.TabIndex = 15;
        //
        // textCountMGJ
        //
        this.textCountMGJ.Location = new System.Drawing.Point(141, 90);
        this.textCountMGJ.Name = "textCountMGJ";
        this.textCountMGJ.ReadOnly = true;
        this.textCountMGJ.Size = new System.Drawing.Size(34, 20);
        this.textCountMGJ.TabIndex = 14;
        //
        // updownMGJ
        //
        this.updownMGJ.InterceptArrowKeys = false;
        this.updownMGJ.Location = new System.Drawing.Point(97, 90);
        this.updownMGJ.Name = "updownMGJ";
        this.updownMGJ.Size = new System.Drawing.Size(19, 20);
        this.updownMGJ.TabIndex = 13;
        this.updownMGJ.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // textRedMGJ
        //
        this.textRedMGJ.Location = new System.Drawing.Point(70, 90);
        this.textRedMGJ.Name = "textRedMGJ";
        this.textRedMGJ.ReadOnly = true;
        this.textRedMGJ.Size = new System.Drawing.Size(27, 20);
        this.textRedMGJ.TabIndex = 12;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(125, 49);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(52, 16);
        this.label14.TabIndex = 11;
        this.label14.Text = "# Teeth";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCountProb
        //
        this.textCountProb.Location = new System.Drawing.Point(141, 70);
        this.textCountProb.Name = "textCountProb";
        this.textCountProb.ReadOnly = true;
        this.textCountProb.Size = new System.Drawing.Size(34, 20);
        this.textCountProb.TabIndex = 10;
        //
        // updownProb
        //
        this.updownProb.InterceptArrowKeys = false;
        this.updownProb.Location = new System.Drawing.Point(97, 70);
        this.updownProb.Name = "updownProb";
        this.updownProb.Size = new System.Drawing.Size(19, 20);
        this.updownProb.TabIndex = 9;
        this.updownProb.MouseDown += new System.Windows.Forms.MouseEventHandler(this.updownRed_MouseDown);
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(7, 50);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(84, 16);
        this.label13.TabIndex = 8;
        this.label13.Text = "Red if >=";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRedProb
        //
        this.textRedProb.Location = new System.Drawing.Point(70, 70);
        this.textRedProb.Name = "textRedProb";
        this.textRedProb.ReadOnly = true;
        this.textRedProb.Size = new System.Drawing.Size(27, 20);
        this.textRedProb.TabIndex = 0;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(6, 152);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(64, 16);
        this.label12.TabIndex = 7;
        this.label12.Text = "Furc";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(6, 172);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(64, 16);
        this.label7.TabIndex = 6;
        this.label7.Text = "Mobility";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(6, 132);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(64, 16);
        this.label11.TabIndex = 5;
        this.label11.Text = "CAL";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(6, 112);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(64, 16);
        this.label10.TabIndex = 4;
        this.label10.Text = "Ging Marg";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(6, 92);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(64, 16);
        this.label9.TabIndex = 3;
        this.label9.Text = "MGJ (<=)";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(6, 72);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(64, 16);
        this.label8.TabIndex = 2;
        this.label8.Text = "Probing";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(112, 19);
        this.label1.TabIndex = 51;
        this.label1.Text = "Exams";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(757, 641);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(123, 42);
        this.label6.TabIndex = 54;
        this.label6.Text = "(All exams are saved automatically)";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // listExams
        //
        this.listExams.ItemHeight = 14;
        this.listExams.Location = new System.Drawing.Point(7, 37);
        this.listExams.Name = "listExams";
        this.listExams.Size = new System.Drawing.Size(124, 130);
        this.listExams.TabIndex = 59;
        this.listExams.DoubleClick += new System.EventHandler(this.listExams_DoubleClick);
        this.listExams.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listExams_MouseDown);
        //
        // textIndexPlaque
        //
        this.textIndexPlaque.Location = new System.Drawing.Point(868, 245);
        this.textIndexPlaque.Name = "textIndexPlaque";
        this.textIndexPlaque.ReadOnly = true;
        this.textIndexPlaque.Size = new System.Drawing.Size(38, 20);
        this.textIndexPlaque.TabIndex = 70;
        this.textIndexPlaque.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textIndexSupp
        //
        this.textIndexSupp.Location = new System.Drawing.Point(868, 335);
        this.textIndexSupp.Name = "textIndexSupp";
        this.textIndexSupp.ReadOnly = true;
        this.textIndexSupp.Size = new System.Drawing.Size(38, 20);
        this.textIndexSupp.TabIndex = 71;
        this.textIndexSupp.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textIndexBleeding
        //
        this.textIndexBleeding.Location = new System.Drawing.Point(868, 305);
        this.textIndexBleeding.Name = "textIndexBleeding";
        this.textIndexBleeding.ReadOnly = true;
        this.textIndexBleeding.Size = new System.Drawing.Size(38, 20);
        this.textIndexBleeding.TabIndex = 72;
        this.textIndexBleeding.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textIndexCalculus
        //
        this.textIndexCalculus.Location = new System.Drawing.Point(868, 275);
        this.textIndexCalculus.Name = "textIndexCalculus";
        this.textIndexCalculus.ReadOnly = true;
        this.textIndexCalculus.Size = new System.Drawing.Size(38, 20);
        this.textIndexCalculus.TabIndex = 73;
        this.textIndexCalculus.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // printPreviewDlg
        //
        this.printPreviewDlg.AutoScrollMargin = new System.Drawing.Size(0, 0);
        this.printPreviewDlg.AutoScrollMinSize = new System.Drawing.Size(0, 0);
        this.printPreviewDlg.ClientSize = new System.Drawing.Size(400, 300);
        this.printPreviewDlg.Enabled = true;
        this.printPreviewDlg.Icon = ((System.Drawing.Icon)(resources.GetObject("printPreviewDlg.Icon")));
        this.printPreviewDlg.Name = "printPreviewDlg";
        this.printPreviewDlg.Visible = false;
        //
        // butGraphical
        //
        this.butGraphical.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGraphical.setAutosize(true);
        this.butGraphical.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGraphical.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGraphical.setCornerRadius(4F);
        this.butGraphical.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGraphical.Location = new System.Drawing.Point(885, 580);
        this.butGraphical.Name = "butGraphical";
        this.butGraphical.Size = new System.Drawing.Size(75, 24);
        this.butGraphical.TabIndex = 76;
        this.butGraphical.Text = "Graphical";
        this.butGraphical.Click += new System.EventHandler(this.butGraphical_Click);
        //
        // gridP
        //
        this.gridP.BackColor = System.Drawing.SystemColors.Window;
        this.gridP.Location = new System.Drawing.Point(157, 11);
        this.gridP.Name = "gridP";
        this.gridP.setSelectedExam(0);
        this.gridP.Size = new System.Drawing.Size(595, 665);
        this.gridP.TabIndex = 75;
        this.gridP.Text = "contrPerio2";
        this.gridP.DirectionChangedRight += new System.EventHandler(this.gridP_DirectionChangedRight);
        this.gridP.DirectionChangedLeft += new System.EventHandler(this.gridP_DirectionChangedLeft);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(885, 616);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 24);
        this.butPrint.TabIndex = 62;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
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
        this.butAdd.Location = new System.Drawing.Point(7, 197);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(82, 24);
        this.butAdd.TabIndex = 53;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // but0
        //
        this.but0.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but0.setAutosize(true);
        this.but0.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but0.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but0.setCornerRadius(4F);
        this.but0.Location = new System.Drawing.Point(763, 173);
        this.but0.Name = "but0";
        this.but0.Size = new System.Drawing.Size(67, 32);
        this.but0.TabIndex = 39;
        this.but0.Text = "0";
        this.but0.Click += new System.EventHandler(this.but0_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(7, 228);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(82, 24);
        this.butDelete.TabIndex = 34;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // but8
        //
        this.but8.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but8.setAutosize(true);
        this.but8.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but8.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but8.setCornerRadius(4F);
        this.but8.Location = new System.Drawing.Point(798, 68);
        this.but8.Name = "but8";
        this.but8.Size = new System.Drawing.Size(32, 32);
        this.but8.TabIndex = 11;
        this.but8.Text = "8";
        this.but8.Click += new System.EventHandler(this.but8_Click);
        //
        // but4
        //
        this.but4.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but4.setAutosize(true);
        this.but4.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but4.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but4.setCornerRadius(4F);
        this.but4.Location = new System.Drawing.Point(763, 103);
        this.but4.Name = "but4";
        this.but4.Size = new System.Drawing.Size(32, 32);
        this.but4.TabIndex = 10;
        this.but4.Text = "4";
        this.but4.Click += new System.EventHandler(this.but4_Click);
        //
        // but5
        //
        this.but5.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but5.setAutosize(true);
        this.but5.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but5.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but5.setCornerRadius(4F);
        this.but5.Location = new System.Drawing.Point(798, 103);
        this.but5.Name = "but5";
        this.but5.Size = new System.Drawing.Size(32, 32);
        this.but5.TabIndex = 9;
        this.but5.Text = "5";
        this.but5.Click += new System.EventHandler(this.but5_Click);
        //
        // but9
        //
        this.but9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but9.setAutosize(true);
        this.but9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but9.setCornerRadius(4F);
        this.but9.Location = new System.Drawing.Point(833, 68);
        this.but9.Name = "but9";
        this.but9.Size = new System.Drawing.Size(32, 32);
        this.but9.TabIndex = 8;
        this.but9.Text = "9";
        this.but9.Click += new System.EventHandler(this.but9_Click);
        //
        // but6
        //
        this.but6.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but6.setAutosize(true);
        this.but6.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but6.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but6.setCornerRadius(4F);
        this.but6.Location = new System.Drawing.Point(833, 103);
        this.but6.Name = "but6";
        this.but6.Size = new System.Drawing.Size(32, 32);
        this.but6.TabIndex = 7;
        this.but6.Text = "6";
        this.but6.Click += new System.EventHandler(this.but6_Click);
        //
        // but1
        //
        this.but1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but1.setAutosize(true);
        this.but1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but1.setCornerRadius(4F);
        this.but1.Location = new System.Drawing.Point(763, 138);
        this.but1.Name = "but1";
        this.but1.Size = new System.Drawing.Size(32, 32);
        this.but1.TabIndex = 6;
        this.but1.Text = "1";
        this.but1.Click += new System.EventHandler(this.but1_Click);
        //
        // but2
        //
        this.but2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but2.setAutosize(true);
        this.but2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but2.setCornerRadius(4F);
        this.but2.Location = new System.Drawing.Point(798, 138);
        this.but2.Name = "but2";
        this.but2.Size = new System.Drawing.Size(32, 32);
        this.but2.TabIndex = 5;
        this.but2.Text = "2";
        this.but2.Click += new System.EventHandler(this.but2_Click);
        //
        // but3
        //
        this.but3.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but3.setAutosize(true);
        this.but3.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but3.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but3.setCornerRadius(4F);
        this.but3.Location = new System.Drawing.Point(833, 138);
        this.but3.Name = "but3";
        this.but3.Size = new System.Drawing.Size(32, 32);
        this.but3.TabIndex = 4;
        this.but3.Text = "3";
        this.but3.Click += new System.EventHandler(this.but3_Click);
        //
        // but7
        //
        this.but7.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but7.setAutosize(true);
        this.but7.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but7.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but7.setCornerRadius(4F);
        this.but7.Location = new System.Drawing.Point(763, 68);
        this.but7.Name = "but7";
        this.but7.Size = new System.Drawing.Size(32, 32);
        this.but7.TabIndex = 3;
        this.but7.Text = "7";
        this.but7.Click += new System.EventHandler(this.but7_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(885, 658);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // labelPlaqueHistory
        //
        this.labelPlaqueHistory.Location = new System.Drawing.Point(5, 334);
        this.labelPlaqueHistory.Name = "labelPlaqueHistory";
        this.labelPlaqueHistory.Size = new System.Drawing.Size(126, 19);
        this.labelPlaqueHistory.TabIndex = 78;
        this.labelPlaqueHistory.Text = "Plaque Index History";
        this.labelPlaqueHistory.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelPlaqueHistory.Visible = false;
        //
        // listPlaqueHistory
        //
        this.listPlaqueHistory.ItemHeight = 14;
        this.listPlaqueHistory.Location = new System.Drawing.Point(7, 356);
        this.listPlaqueHistory.Name = "listPlaqueHistory";
        this.listPlaqueHistory.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listPlaqueHistory.Size = new System.Drawing.Size(124, 130);
        this.listPlaqueHistory.TabIndex = 79;
        this.listPlaqueHistory.Visible = false;
        //
        // FormPerio
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(982, 700);
        this.Controls.Add(this.checkGingMarg);
        this.Controls.Add(this.listPlaqueHistory);
        this.Controls.Add(this.labelPlaqueHistory);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.butGraphical);
        this.Controls.Add(this.gridP);
        this.Controls.Add(this.butCalcIndex);
        this.Controls.Add(this.textIndexCalculus);
        this.Controls.Add(this.textIndexBleeding);
        this.Controls.Add(this.textIndexSupp);
        this.Controls.Add(this.textIndexPlaque);
        this.Controls.Add(this.butColorCalculus);
        this.Controls.Add(this.butColorPlaque);
        this.Controls.Add(this.butCalculus);
        this.Controls.Add(this.butPlaque);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butSkip);
        this.Controls.Add(this.listExams);
        this.Controls.Add(this.checkThree);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butColorPus);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butColorBleed);
        this.Controls.Add(this.butPus);
        this.Controls.Add(this.butBleed);
        this.Controls.Add(this.but10);
        this.Controls.Add(this.but0);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.but8);
        this.Controls.Add(this.but4);
        this.Controls.Add(this.but5);
        this.Controls.Add(this.but9);
        this.Controls.Add(this.but6);
        this.Controls.Add(this.but1);
        this.Controls.Add(this.but2);
        this.Controls.Add(this.but3);
        this.Controls.Add(this.but7);
        this.Controls.Add(this.butClose);
        this.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPerio";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Perio Chart";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormPerio_Closing);
        this.Load += new System.EventHandler(this.FormPerio_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPerio_Load(Object sender, System.EventArgs e) throws Exception {
        butColorBleed.BackColor = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][1].ItemColor;
        butColorPus.BackColor = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][2].ItemColor;
        butColorPlaque.BackColor = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][4].ItemColor;
        butColorCalculus.BackColor = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][5].ItemColor;
        textRedProb.Text = PrefC.getString(PrefName.PerioRedProb);
        textRedMGJ.Text = PrefC.getString(PrefName.PerioRedMGJ);
        textRedGing.Text = PrefC.getString(PrefName.PerioRedGing);
        textRedCAL.Text = PrefC.getString(PrefName.PerioRedCAL);
        textRedFurc.Text = PrefC.getString(PrefName.PerioRedFurc);
        textRedMob.Text = PrefC.getString(PrefName.PerioRedMob);
        //Procedure[] procList=Procedures.Refresh(PatCur.PatNum);
        List<ToothInitial> initialList = ToothInitials.refresh(PatCur.PatNum);
        MissingTeeth = ToothInitials.GetMissingOrHiddenTeeth(initialList);
        refreshListExams();
        if (Programs.getUsingOrion())
        {
            labelPlaqueHistory.Visible = true;
            listPlaqueHistory.Visible = true;
            refreshListPlaque();
        }
         
        listExams.SelectedIndex = PerioExams.ListExams.Count - 1;
        //this works even if no items.
        fillGrid();
    }

    /**
    * After this method runs, the selected index is usually set.
    */
    private void refreshListExams() throws Exception {
        //most recent date at the bottom
        PerioExams.refresh(PatCur.PatNum);
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        listExams.Items.Clear();
        for (int i = 0;i < PerioExams.ListExams.Count;i++)
        {
            listExams.Items.Add(PerioExams.ListExams[i].ExamDate.ToShortDateString() + "   " + Providers.GetAbbr(PerioExams.ListExams[i].ProvNum));
        }
    }

    private void refreshListPlaque() throws Exception {
        PerioExams.refresh(PatCur.PatNum);
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        listPlaqueHistory.Items.Clear();
        for (int i = 0;i < PerioExams.ListExams.Count;i++)
        {
            String ph = "";
            ph = PerioExams.ListExams[i].ExamDate.ToShortDateString() + "\t";
            gridP.setSelectedExam(i);
            gridP.loadData();
            ph += gridP.computeOrionPlaqueIndex();
            listPlaqueHistory.Items.Add(ph);
        }
        //Not sure if necessary but set it back to what it was
        gridP.setSelectedExam(listExams.SelectedIndex);
    }

    /**
    * Usually set the selected index first
    */
    private void fillGrid() throws Exception {
        if (listExams.SelectedIndex != -1)
        {
            gridP.perioEdit = true;
            if (!Security.IsAuthorized(Permissions.PerioEdit, PerioExams.ListExams[listExams.SelectedIndex].ExamDate, true))
            {
                gridP.perioEdit = false;
            }
             
            PerioExamCur = PerioExams.ListExams[listExams.SelectedIndex];
        }
         
        gridP.setSelectedExam(listExams.SelectedIndex);
        gridP.loadData();
        fillIndexes();
        fillCounts();
        gridP.Invalidate();
        gridP.Focus();
    }

    //this still doesn't seem to work to enable first arrow click to move
    private void listExams_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (listExams.SelectedIndex == gridP.getSelectedExam())
            return ;
         
        //Only continues if clicked on other than current exam
        gridP.saveCurExam(PerioExamCur.PerioExamNum);
        //no need to RefreshListExams because it has not changed
        PerioExams.refresh(PatCur.PatNum);
        //refresh instead
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        fillGrid();
    }

    private void listExams_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        //remember that the first click may not have triggered the mouse down routine
        //and the second click will never trigger it.
        if (listExams.SelectedIndex == -1)
        {
            return ;
        }
         
        if (!Security.IsAuthorized(Permissions.PerioEdit, PerioExams.ListExams[listExams.SelectedIndex].ExamDate))
        {
            return ;
        }
         
        //a PerioExam.Cur will always have been set through mousedown(or similar),then FillGrid
        gridP.saveCurExam(PerioExamCur.PerioExamNum);
        PerioExams.refresh(PatCur.PatNum);
        //list will not change
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        FormPerioEdit FormPE = new FormPerioEdit();
        FormPE.PerioExamCur = PerioExamCur;
        FormPE.ShowDialog();
        int curIndex = listExams.SelectedIndex;
        refreshListExams();
        listExams.SelectedIndex = curIndex;
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.PerioEdit,MiscData.getNowDateTime()))
        {
            return ;
        }
         
        if (listExams.SelectedIndex != -1)
        {
            gridP.saveCurExam(PerioExamCur.PerioExamNum);
        }
         
        PerioExamCur = new PerioExam();
        PerioExamCur.PatNum = PatCur.PatNum;
        PerioExamCur.ExamDate = DateTimeOD.getToday();
        PerioExamCur.ProvNum = PatCur.PriProv;
        PerioExams.insert(PerioExamCur);
        List<int> skippedTeeth = new List<int>();
        //int 1-32
        if (PerioExams.ListExams.Count == 0)
        {
            for (int i = 0;i < MissingTeeth.Count;i++)
            {
                //if a number
                if (((String)MissingTeeth[i]).CompareTo("A") < 0 || ((String)MissingTeeth[i]).CompareTo("Z") > 0)
                {
                    skippedTeeth.Add(PIn.Int(MissingTeeth[i]));
                }
                 
            }
        }
        else
        {
            //set skipped teeth based on the last exam in the list:
            skippedTeeth = PerioMeasures.GetSkipped(PerioExams.ListExams[PerioExams.ListExams.Count - 1].PerioExamNum);
        } 
        PerioMeasures.SetSkipped(PerioExamCur.PerioExamNum, skippedTeeth);
        refreshListExams();
        listExams.SelectedIndex = PerioExams.ListExams.Count - 1;
        fillGrid();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listExams.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (!Security.IsAuthorized(Permissions.PerioEdit, PerioExams.ListExams[listExams.SelectedIndex].ExamDate))
        {
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete Exam?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        int curselected = listExams.SelectedIndex;
        PerioExams.delete(PerioExamCur);
        refreshListExams();
        if (curselected < listExams.Items.Count)
            listExams.SelectedIndex = curselected;
        else
            listExams.SelectedIndex = PerioExams.ListExams.Count - 1; 
        fillGrid();
    }

    private void radioRight_Click(Object sender, System.EventArgs e) throws Exception {
        gridP.DirectionIsRight = true;
        gridP.Focus();
    }

    private void radioLeft_Click(Object sender, System.EventArgs e) throws Exception {
        gridP.DirectionIsRight = false;
        gridP.Focus();
    }

    private void gridP_DirectionChangedRight(Object sender, System.EventArgs e) throws Exception {
        radioRight.Checked = true;
    }

    private void gridP_DirectionChangedLeft(Object sender, System.EventArgs e) throws Exception {
        radioLeft.Checked = true;
    }

    private void checkThree_Click(Object sender, System.EventArgs e) throws Exception {
        gridP.ThreeAtATime = checkThree.Checked;
        gridP.Focus();
    }

    private void but0_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(0);
    }

    private void but1_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(1);
    }

    private void but2_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(2);
    }

    private void but3_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(3);
    }

    private void but4_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(4);
    }

    private void but5_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(5);
    }

    private void but6_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(6);
    }

    private void but7_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(7);
    }

    private void but8_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(8);
    }

    private void but9_Click(Object sender, System.EventArgs e) throws Exception {
        numberClicked(9);
    }

    /**
    * The only valid numbers are 0 through 9
    */
    private void numberClicked(int number) throws Exception {
        if (gridP.getSelectedExam() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please add or select an exam first in the list to the left."));
            return ;
        }
         
        if (TenIsDown)
        {
            gridP.buttonPressed(10 + number);
        }
        else
        {
            gridP.buttonPressed(number);
        } 
        TenIsDown = false;
        gridP.Focus();
    }

    private void but10_Click(Object sender, System.EventArgs e) throws Exception {
        TenIsDown = true;
    }

    private void butCalcIndex_Click(Object sender, System.EventArgs e) throws Exception {
        fillIndexes();
        if (listPlaqueHistory.Visible)
        {
            gridP.saveCurExam(PerioExamCur.PerioExamNum);
            refreshListPlaque();
            fillGrid();
        }
         
    }

    private void fillIndexes() throws Exception {
        textIndexPlaque.Text = gridP.computeIndex(BleedingFlags.Plaque);
        textIndexCalculus.Text = gridP.computeIndex(BleedingFlags.Calculus);
        textIndexBleeding.Text = gridP.computeIndex(BleedingFlags.Blood);
        textIndexSupp.Text = gridP.computeIndex(BleedingFlags.Suppuration);
    }

    private void butBleed_Click(Object sender, System.EventArgs e) throws Exception {
        TenIsDown = false;
        gridP.buttonPressed("b");
        gridP.Focus();
    }

    private void butPus_Click(Object sender, System.EventArgs e) throws Exception {
        TenIsDown = false;
        gridP.buttonPressed("s");
        gridP.Focus();
    }

    private void butPlaque_Click(Object sender, System.EventArgs e) throws Exception {
        TenIsDown = false;
        gridP.buttonPressed("p");
        gridP.Focus();
    }

    private void butCalculus_Click(Object sender, System.EventArgs e) throws Exception {
        TenIsDown = false;
        gridP.buttonPressed("c");
        gridP.Focus();
    }

    private void butColorBleed_Click(Object sender, System.EventArgs e) throws Exception {
        colorDialog1.Color = butColorBleed.BackColor;
        if (colorDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        butColorBleed.BackColor = colorDialog1.Color;
        Def DefCur = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][1].Copy();
        DefCur.ItemColor = colorDialog1.Color;
        Defs.update(DefCur);
        Cache.refresh(InvalidType.Defs);
        localDefsChanged = true;
        gridP.setColors();
        gridP.Invalidate();
        gridP.Focus();
    }

    private void butColorPus_Click(Object sender, System.EventArgs e) throws Exception {
        colorDialog1.Color = butColorPus.BackColor;
        if (colorDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        butColorPus.BackColor = colorDialog1.Color;
        Def DefCur = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][2].Copy();
        DefCur.ItemColor = colorDialog1.Color;
        Defs.update(DefCur);
        Cache.refresh(InvalidType.Defs);
        localDefsChanged = true;
        gridP.setColors();
        gridP.Invalidate();
        gridP.Focus();
    }

    private void butColorPlaque_Click(Object sender, System.EventArgs e) throws Exception {
        colorDialog1.Color = butColorPlaque.BackColor;
        if (colorDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        butColorPlaque.BackColor = colorDialog1.Color;
        Def DefCur = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][4].Copy();
        DefCur.ItemColor = colorDialog1.Color;
        Defs.update(DefCur);
        Cache.refresh(InvalidType.Defs);
        localDefsChanged = true;
        gridP.setColors();
        gridP.Invalidate();
        gridP.Focus();
    }

    private void butColorCalculus_Click(Object sender, System.EventArgs e) throws Exception {
        colorDialog1.Color = butColorCalculus.BackColor;
        if (colorDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        butColorCalculus.BackColor = colorDialog1.Color;
        Def DefCur = DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][5].Copy();
        DefCur.ItemColor = colorDialog1.Color;
        Defs.update(DefCur);
        Cache.refresh(InvalidType.Defs);
        localDefsChanged = true;
        gridP.setColors();
        gridP.Invalidate();
        gridP.Focus();
    }

    private void butSkip_Click(Object sender, System.EventArgs e) throws Exception {
        if (listExams.SelectedIndex < 0)
        {
            //PerioExamCur could still be set to a deleted exam and would not be null even if there is no exam.
            MessageBox.Show(Lan.g(this,"Please select an exam first."));
            return ;
        }
         
        gridP.toggleSkip(PerioExamCur.PerioExamNum);
    }

    private void updownRed_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        //this is necessary because Microsoft's updown control is too buggy to be useful
        Cursor = Cursors.WaitCursor;
        PrefName prefname = PrefName.PerioRedProb;
        if (sender == updownProb)
        {
            prefname = PrefName.PerioRedProb;
        }
        else if (sender == updownMGJ)
        {
            prefname = PrefName.PerioRedMGJ;
        }
        else if (sender == updownGing)
        {
            prefname = PrefName.PerioRedGing;
        }
        else if (sender == updownCAL)
        {
            prefname = PrefName.PerioRedCAL;
        }
        else if (sender == updownFurc)
        {
            prefname = PrefName.PerioRedFurc;
        }
        else if (sender == updownMob)
        {
            prefname = PrefName.PerioRedMob;
        }
              
        int currentValue = PrefC.getInt(prefname);
        if (e.Y < 8)
        {
            //up
            currentValue++;
        }
        else
        {
            //down
            if (currentValue == 0)
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            currentValue--;
        } 
        Prefs.UpdateLong(prefname, currentValue);
        //pref.ValueString=currentValue.ToString();
        //Prefs.Update(pref);
        localDefsChanged = true;
        Cache.refresh(InvalidType.Prefs);
        if (sender == updownProb)
        {
            textRedProb.Text = currentValue.ToString();
            textCountProb.Text = gridP.countTeeth(PerioSequenceType.Probing).Count.ToString();
        }
        else if (sender == updownMGJ)
        {
            textRedMGJ.Text = currentValue.ToString();
            textCountMGJ.Text = gridP.countTeeth(PerioSequenceType.MGJ).Count.ToString();
        }
        else if (sender == updownGing)
        {
            textRedGing.Text = currentValue.ToString();
            textCountGing.Text = gridP.countTeeth(PerioSequenceType.GingMargin).Count.ToString();
        }
        else if (sender == updownCAL)
        {
            textRedCAL.Text = currentValue.ToString();
            textCountCAL.Text = gridP.countTeeth(PerioSequenceType.CAL).Count.ToString();
        }
        else if (sender == updownFurc)
        {
            textRedFurc.Text = currentValue.ToString();
            textCountFurc.Text = gridP.countTeeth(PerioSequenceType.Furcation).Count.ToString();
        }
        else if (sender == updownMob)
        {
            textRedMob.Text = currentValue.ToString();
            textCountMob.Text = gridP.countTeeth(PerioSequenceType.Mobility).Count.ToString();
        }
              
        gridP.Invalidate();
        Cursor = Cursors.Default;
        gridP.Focus();
    }

    private void butCount_Click(Object sender, System.EventArgs e) throws Exception {
        fillCounts();
        gridP.Focus();
    }

    private void fillCounts() throws Exception {
        textCountProb.Text = gridP.countTeeth(PerioSequenceType.Probing).Count.ToString();
        textCountMGJ.Text = gridP.countTeeth(PerioSequenceType.MGJ).Count.ToString();
        textCountGing.Text = gridP.countTeeth(PerioSequenceType.GingMargin).Count.ToString();
        textCountCAL.Text = gridP.countTeeth(PerioSequenceType.CAL).Count.ToString();
        textCountFurc.Text = gridP.countTeeth(PerioSequenceType.Furcation).Count.ToString();
        textCountMob.Text = gridP.countTeeth(PerioSequenceType.Mobility).Count.ToString();
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (!PrinterL.SetPrinter(pd2, PrintSituation.TPPerio, PatCur.PatNum, "Perio chart from " + PerioExamCur.ExamDate + " printed"))
        {
            return ;
        }
         
        try
        {
            pd2.Print();
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }

        gridP.Focus();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        if (this.listExams.SelectedIndex < 0)
        {
            MessageBox.Show(Lan.g(this,"Please select an exam first."));
            return ;
        }
         
        gridP.saveCurExam(PerioExamCur.PerioExamNum);
        PerioExams.refresh(PatCur.PatNum);
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        fillGrid();
        Bitmap gridImage = null;
        Bitmap perioPrintImage = null;
        Graphics g = null;
        //Document doc=new Document();
        //try {
        perioPrintImage = new Bitmap(750, 1000);
        perioPrintImage.SetResolution(96, 96);
        g = Graphics.FromImage(perioPrintImage);
        g.Clear(Color.White);
        g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
        g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
        g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;
        g.TextRenderingHint = System.Drawing.Text.TextRenderingHint.ClearTypeGridFit;
        String clinicName = "";
        //This clinic name could be more accurate here in the future if we make perio exams clinic specific.
        //Perhaps if there were a perioexam.ClinicNum column.
        if (PatCur.ClinicNum != 0)
        {
            Clinic clinic = Clinics.getClinic(PatCur.ClinicNum);
            clinicName = clinic.Description;
        }
        else
        {
            clinicName = PrefC.getString(PrefName.PracticeTitle);
        } 
        float y = 50f;
        SizeF m = new SizeF();
        Font font = new Font("Arial", 15);
        String titleStr = "PERIODONTAL EXAMINATION";
        m = g.MeasureString(titleStr, font);
        g.DrawString(titleStr, font, Brushes.Black, new PointF(perioPrintImage.Width / 2f - m.Width / 2f, y));
        y += m.Height;
        font = new Font("Arial", 11);
        m = g.MeasureString(clinicName, font);
        g.DrawString(clinicName, font, Brushes.Black, new PointF(perioPrintImage.Width / 2f - m.Width / 2f, y));
        y += m.Height;
        String patNameStr = PatCur.getNameFLFormal();
        m = g.MeasureString(patNameStr, font);
        g.DrawString(patNameStr, font, Brushes.Black, new PointF(perioPrintImage.Width / 2f - m.Width / 2f, y));
        y += m.Height;
        DateTime serverTimeNow = MiscData.getNowDateTime();
        String timeNowStr = serverTimeNow.ToShortDateString();
        //Locale specific date.
        m = g.MeasureString(timeNowStr, font);
        g.DrawString(timeNowStr, font, Brushes.Black, new PointF(perioPrintImage.Width / 2f - m.Width / 2f, y));
        y += m.Height;
        gridImage = new Bitmap(gridP.Width, gridP.Height);
        gridP.DrawToBitmap(gridImage, new Rectangle(0, 0, gridImage.Width, gridImage.Height));
        g.DrawImageUnscaled(gridImage, (int)((perioPrintImage.Width - gridImage.Width) / 2f), (int)y);
        long defNumCategory = DefC.getImageCat(ImageCategorySpecial.T);
        if (defNumCategory == 0)
        {
            MsgBox.show(this,"No image category set for tooth charts in definitions.");
            perioPrintImage.Dispose();
            gridImage.Dispose();
            perioPrintImage = null;
            gridImage = null;
            g.Dispose();
            return ;
        }
         
        try
        {
            ImageStore.import(perioPrintImage,defNumCategory,ImageType.Photo,PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Unable to save file. ") + ex.Message);
            perioPrintImage.Dispose();
            gridImage.Dispose();
            perioPrintImage = null;
            gridImage = null;
            g.Dispose();
            return ;
        }

        MsgBox.show(this,"Saved.");
        /*
        			string patImagePath=ImageStore.GetPatientFolder(PatCur);
        			string filePath="";
        			do {
        				doc.DateCreated=MiscData.GetNowDateTime();
        				doc.FileName="perioexam_"+doc.DateCreated.ToString("yyyy_MM_dd_hh_mm_ss")+".png";
        				filePath=ODFileUtils.CombinePaths(patImagePath,doc.FileName);
        			} while(File.Exists(filePath));//if a file with this name already exists, then it will stay in this loop
        			doc.PatNum=PatCur.PatNum;
        			doc.ImgType=ImageType.Photo;
        			doc.DocCategory=DefC.GetByExactName(DefCat.ImageCats,"Tooth Charts");
        			doc.Description="Perio Exam";
        			Documents.Insert(doc,PatCur);
        			docCreated=true;
        			perioPrintImage.Save(filePath,System.Drawing.Imaging.ImageFormat.Png);
        			MessageBox.Show(Lan.g(this,"Image saved."));*/
        /*} 
        			catch(Exception ex) {
        				MessageBox.Show(Lan.g(this,"Image failed to save: "+Environment.NewLine+ex.ToString()));
        				if(docCreated) {
        					Documents.Delete(doc);
        				}
        			} 
        			finally {
        				if(gridImage!=null){
        					gridImage.Dispose();
        				}
        				if(g!=null) {
        					g.Dispose();
        					g=null;
        				}
        				if(perioPrintImage!=null) {
        					perioPrintImage.Dispose();
        					perioPrintImage=null;
        				}
        			}*/
        perioPrintImage.Dispose();
        gridImage.Dispose();
        perioPrintImage = null;
        gridImage = null;
        g.Dispose();
    }

    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        Graphics grfx = ev.Graphics;
        //MessageBox.Show(grfx.
        float yPos = 67 + 25 + 20 + 20 + 6;
        float xPos = 100;
        grfx.TranslateTransform(xPos, yPos);
        gridP.drawChart(grfx);
        //have to print graphics first, or they cover up title.
        grfx.DrawString("F", new Font("Arial", 15), Brushes.Black, new Point(-26, 92));
        grfx.DrawString("L", new Font("Arial", 15), Brushes.Black, new Point(-26, 212));
        grfx.DrawString("L", new Font("Arial", 15), Brushes.Black, new Point(-26, 416));
        grfx.DrawString("F", new Font("Arial", 15), Brushes.Black, new Point(-26, 552));
        grfx.TranslateTransform(-xPos, -yPos);
        yPos = 67;
        xPos = 100;
        Font font = new Font("Arial", 9);
        StringFormat format = new StringFormat();
        format.Alignment = StringAlignment.Center;
        grfx.DrawString("Periodontal Charting", new Font("Arial", 15), Brushes.Black, new RectangleF(xPos, yPos, 650, 25), format);
        yPos += 25;
        grfx.DrawString(PrefC.getString(PrefName.PracticeTitle), new Font("Arial", 11), Brushes.Black, new RectangleF(xPos, yPos, 650, 25), format);
        yPos += 20;
        grfx.DrawString(PatCur.getNameFL(), new Font("Arial", 11), Brushes.Black, new RectangleF(xPos, yPos, 650, 25), format);
        yPos += 20;
        //grfx.TranslateTransform(xPos,yPos);
        //gridP.DrawChart(grfx);
        //grfx.TranslateTransform(-xPos,-yPos);
        yPos += 688;
        grfx.FillEllipse(new SolidBrush(butColorPlaque.BackColor), xPos, yPos + 3, 8, 8);
        grfx.DrawString("Plaque Index: " + gridP.computeIndex(BleedingFlags.Plaque) + " %", font, Brushes.Black, xPos + 12, yPos);
        yPos += 20;
        grfx.FillEllipse(new SolidBrush(butColorCalculus.BackColor), xPos, yPos + 3, 8, 8);
        grfx.DrawString("Calculus Index: " + gridP.computeIndex(BleedingFlags.Calculus) + " %", font, Brushes.Black, xPos + 12, yPos);
        yPos += 20;
        grfx.FillEllipse(new SolidBrush(butColorBleed.BackColor), xPos, yPos + 3, 8, 8);
        grfx.DrawString("Bleeding Index: " + gridP.computeIndex(BleedingFlags.Blood) + " %", font, Brushes.Black, xPos + 12, yPos);
        yPos += 20;
        grfx.FillEllipse(new SolidBrush(butColorPus.BackColor), xPos, yPos + 3, 8, 8);
        grfx.DrawString("Suppuration Index: " + gridP.computeIndex(BleedingFlags.Suppuration) + " %", font, Brushes.Black, xPos + 12, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with Probing greater than or equal to " + textRedProb.Text + " mm: " + convertALtoString(gridP.countTeeth(PerioSequenceType.Probing)), font, Brushes.Black, xPos, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with MGJ less than or equal to " + textRedMGJ.Text + " mm: " + convertALtoString(gridP.countTeeth(PerioSequenceType.MGJ)), font, Brushes.Black, xPos, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with Gingival Margin greater than or equal to " + textRedGing.Text + " mm: " + convertALtoString(gridP.countTeeth(PerioSequenceType.GingMargin)), font, Brushes.Black, xPos, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with CAL greater than or equal to " + textRedCAL.Text + " mm: " + convertALtoString(gridP.countTeeth(PerioSequenceType.CAL)), font, Brushes.Black, xPos, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with Furcations greater than or equal to class " + textRedFurc.Text + ": " + convertALtoString(gridP.countTeeth(PerioSequenceType.Furcation)), font, Brushes.Black, xPos, yPos);
        yPos += 20;
        grfx.DrawString("Teeth with Mobility greater than or equal to " + textRedMob.Text + ": " + convertALtoString(gridP.countTeeth(PerioSequenceType.Mobility)), font, Brushes.Black, xPos, yPos);
        //pagesPrinted++;
        ev.HasMorePages = false;
        grfx.Dispose();
    }

    private String convertALtoString(ArrayList ALteeth) throws Exception {
        if (ALteeth.Count == 0)
        {
            return "none";
        }
         
        String retVal = "";
        for (int i = 0;i < ALteeth.Count;i++)
        {
            if (i > 0)
                retVal += ",";
             
            retVal += ALteeth[i];
        }
        return retVal;
    }

    private void butGraphical_Click(Object sender, EventArgs e) throws Exception {
        if (ComputerPrefs.getLocalComputer().GraphicsSimple != DrawingMode.DirectX)
        {
            MsgBox.show(this,"In the Graphics setup window, you must first select DirectX.");
            return ;
        }
         
        if (listExams.SelectedIndex == -1)
        {
            MsgBox.show(this,"Exam must be selected first.");
            return ;
        }
         
        if (localDefsChanged)
        {
            DataValid.setInvalid(InvalidType.Defs,InvalidType.Prefs);
        }
         
        //if(listExams.SelectedIndex!=-1) {
        gridP.saveCurExam(PerioExamCur.PerioExamNum);
        PerioExams.refresh(PatCur.PatNum);
        //refresh instead
        PerioMeasures.refresh(PatCur.PatNum,PerioExams.ListExams);
        fillGrid();
        FormPerioGraphical formg = new FormPerioGraphical(PerioExams.ListExams[listExams.SelectedIndex], PatCur);
        formg.ShowDialog();
        formg.Dispose();
    }

    private void checkGingMarg_CheckedChanged(Object sender, EventArgs e) throws Exception {
        gridP.GingMargPlus = checkGingMarg.Checked;
        gridP.Focus();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formPerio_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (localDefsChanged)
        {
            DataValid.setInvalid(InvalidType.Defs,InvalidType.Prefs);
        }
         
        if (listExams.SelectedIndex != -1)
        {
            gridP.saveCurExam(PerioExamCur.PerioExamNum);
        }
         
    }

}


