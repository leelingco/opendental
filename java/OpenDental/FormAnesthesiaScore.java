//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Userod;
import OpenDental.FormAnesthesiaScore;
import OpenDental.Properties.Resources;

public class FormAnesthesiaScore  extends Form 
{

    public static Userod CurUser;
    private Patient PatCur;
    public int ActQ2 = new int();
    public int ActQ1 = new int();
    public int ActQ0 = new int();
    public int RespQ2 = new int();
    public int RespQ1 = new int();
    public int RespQ0 = new int();
    public int CircQ2 = new int();
    public int CircQ1 = new int();
    public int CircQ0 = new int();
    public int ConcQ2 = new int();
    public int ConcQ1 = new int();
    public int ConcQ0 = new int();
    public int ColorQ2 = new int();
    public int ColorQ1 = new int();
    public int ColorQ0 = new int();
    public int DischAmb = new int();
    public int DischWheelChr = new int();
    public int DischAmbulance = new int();
    public int DischCondStable = new int();
    public int DischCondUnstable = new int();
    public int AnestheticRecordNum = new int();
    public String AnesthClose = new String();
    public PrintWindowL printWindow = new PrintWindowL();
    public FormAnesthesiaScore(Patient patCur, int anestheticRecordNum) throws Exception {
        this.components = new System.ComponentModel.Container();
        initializeComponent();
        Lan.F(this);
        PatCur = patCur;
        AnestheticRecordNum = anestheticRecordNum;
    }

    private void formAnesthesiaScore_Load(Object sender, EventArgs e) throws Exception {
        //display Patient name
        textPatient.Text = Patients.getPat(PatCur.PatNum).getNameFL();
        //display Patient ID number
        textPatID.Text = PatCur.PatNum.ToString();
        //AnesthClose time is used as time for this form since the Anesthesia Score should be done right around discharge time
        String curDateTime = AnestheticRecords.GetAnesthDate(AnestheticRecordNum) + " " + AnestheticRecords.GetAnesthCloseTime(AnestheticRecordNum);
        textDate.Text = curDateTime;
        fillControls();
    }

    private void refreshScore() throws Exception {
        ActQ2 = 0;
        ActQ1 = 0;
        ActQ0 = 0;
        RespQ2 = 0;
        RespQ1 = 0;
        RespQ0 = 0;
        CircQ2 = 0;
        CircQ1 = 0;
        CircQ0 = 0;
        ConcQ2 = 0;
        ConcQ1 = 0;
        ConcQ0 = 0;
        ColorQ2 = 0;
        ColorQ1 = 0;
        ColorQ0 = 0;
        if (radActivityQ2.Checked == true)
        {
            ActQ2 = 2;
        }
        else if (radActivityQ1.Checked == true)
        {
            ActQ1 = 1;
        }
          
        if (radRespQ2.Checked == true)
        {
            RespQ2 = 2;
        }
        else if (radRespQ1.Checked == true)
        {
            RespQ1 = 1;
        }
          
        if (radCircQ2.Checked == true)
        {
            CircQ2 = 2;
        }
        else if (radCircQ1.Checked == true)
        {
            CircQ1 = 1;
        }
          
        if (radConcQ2.Checked == true)
        {
            ConcQ2 = 2;
        }
        else if (radConcQ1.Checked == true)
        {
            ConcQ1 = 1;
        }
          
        if (radColorQ2.Checked == true)
        {
            ColorQ2 = 2;
        }
        else if (radColorQ1.Checked == true)
        {
            ColorQ1 = 1;
        }
          
        textPARSSTotal.Text = Convert.ToString(ActQ2 + ActQ1 + ActQ0 + RespQ2 + RespQ1 + RespQ0 + CircQ2 + CircQ1 + CircQ0 + ConcQ2 + ConcQ1 + ConcQ0 + ColorQ2 + ColorQ1 + ColorQ0);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        int QActivity = 0;
        int QResp = 0;
        int QCirc = 0;
        int QConc = 0;
        int QColor = 0;
        int AnesthesiaScore = 0;
        if (radDischAmb.Checked == true)
        {
            DischAmb = 1;
        }
         
        if (radDischWheelChr.Checked == true)
        {
            DischWheelChr = 1;
        }
         
        if (radDischAmbulance.Checked == true)
        {
            DischAmbulance = 1;
        }
         
        if (radDischCondStable.Checked == true)
        {
            DischCondStable = 1;
        }
         
        if (radDischCondUnstable.Checked == true)
        {
            DischCondUnstable = 1;
        }
         
        //calculate Anesthesia Score
        QActivity = ActQ2 + ActQ1;
        QResp = RespQ2 + RespQ1;
        QCirc = CircQ2 + CircQ1;
        QConc = ConcQ2 + ConcQ1;
        QColor = ColorQ2 + ColorQ1;
        AnesthesiaScore = QActivity + QResp + QCirc + QConc + QColor;
        int AnesthRecordNum = AnestheticRecords.GetScoreRecordNum(AnestheticRecordNum);
        if (AnesthRecordNum == 0)
        {
            //no Anesthesia Score record exists yet
            AnestheticRecords.InsertAnesthScore(AnestheticRecordNum, QActivity, QResp, QCirc, QConc, QColor, AnesthesiaScore, DischAmb, DischWheelChr, DischAmbulance, DischCondStable, DischCondUnstable);
        }
        else
            AnestheticRecords.UpdateAnesthScore(AnestheticRecordNum, QActivity, QResp, QCirc, QConc, QColor, AnesthesiaScore, DischAmb, DischWheelChr, DischAmbulance, DischCondStable, DischCondUnstable); 
        //update existing Anesthesia Score
        DialogResult = DialogResult.OK;
    }

    //Load saved data into form for selected Anesthetic Record
    private void fillControls() throws Exception {
        DataTable table = AnestheticRecords.GetAnesthScoreTable(AnestheticRecordNum);
        AnesthScore Cur = new AnesthScore();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            Cur = new AnesthScore();
            Cur.AnesthScoreNum = PIn.PInt(table.Rows[i][0].ToString());
            Cur.AnestheticRecordNum = PIn.PInt(table.Rows[i][1].ToString());
            Cur.QActivity = PIn.PInt(table.Rows[i][2].ToString());
            Cur.QResp = PIn.PInt(table.Rows[i][3].ToString());
            Cur.QCirc = PIn.PInt(table.Rows[i][4].ToString());
            Cur.QConc = PIn.PInt(table.Rows[i][5].ToString());
            Cur.QColor = PIn.PInt(table.Rows[i][6].ToString());
            Cur.AnesthesiaScore = PIn.PInt(table.Rows[i][7].ToString());
            Cur.DischAmb = PIn.PInt(table.Rows[i][8].ToString());
            Cur.DischWheelChr = PIn.PInt(table.Rows[i][9].ToString());
            Cur.DischAmbulance = PIn.PInt(table.Rows[i][10].ToString());
            Cur.DischCondStable = PIn.PInt(table.Rows[i][11].ToString());
            Cur.DischCondUnstable = PIn.PInt(table.Rows[i][12].ToString());
            //fill radQActivity
            if (Cur.QActivity == 2)
            {
                radActivityQ2.Checked = true;
            }
            else if (Cur.QActivity == 1)
            {
                radActivityQ1.Checked = true;
            }
            else if (Cur.QActivity == 0)
            {
                radActivityQ0.Checked = true;
            }
               
            //fill radQResp
            if (Cur.QResp == 2)
            {
                radRespQ2.Checked = true;
            }
            else if (Cur.QResp == 1)
            {
                radRespQ1.Checked = true;
            }
            else if (Cur.QResp == 0)
            {
                radRespQ0.Checked = true;
            }
               
            //fill radQCirc
            if (Cur.QCirc == 2)
            {
                radCircQ2.Checked = true;
            }
            else if (Cur.QCirc == 1)
            {
                radCircQ1.Checked = true;
            }
            else if (Cur.QCirc == 0)
            {
                radCircQ0.Checked = true;
            }
               
            //fill radQConc
            if (Cur.QConc == 2)
            {
                radConcQ2.Checked = true;
            }
            else if (Cur.QConc == 1)
            {
                radConcQ1.Checked = true;
            }
            else if (Cur.QConc == 0)
            {
                radConcQ0.Checked = true;
            }
               
            //fill radQColor
            if (Cur.QColor == 2)
            {
                radColorQ2.Checked = true;
            }
            else if (Cur.QColor == 1)
            {
                radColorQ1.Checked = true;
            }
            else if (Cur.QColor == 0)
            {
                radColorQ0.Checked = true;
            }
               
            //fill radDischAmb
            if (Cur.DischAmb == 1)
            {
                radDischAmb.Checked = true;
            }
             
            //fill radDischWheelChr
            if (Cur.DischWheelChr == 1)
            {
                radDischWheelChr.Checked = true;
            }
             
            //fill radDischAmbulance
            if (Cur.DischAmbulance == 1)
            {
                radDischAmbulance.Checked = true;
            }
             
            //fill radDischCondStable
            if (Cur.DischCondStable == 1)
            {
                radDischCondStable.Checked = true;
            }
             
            //fill radDischCondUntable
            if (Cur.DischCondUnstable == 1)
            {
                radDischCondUnstable.Checked = true;
            }
             
        }
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void radDischUnstable_CheckedChanged(Object sender, EventArgs e) throws Exception {
    }

    private void textPatient_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void radActivityQ2_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radActivityQ1_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radActivityQ0_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radRespQ2_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radRespQ1_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radRespQ0_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radCircQ2_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radCircQ1_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radCircQ0_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radConcQ2_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radConcQ1_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radConcQ0_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radColorQ2_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radColorQ1_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void radColorQ0_CheckedChanged(Object sender, EventArgs e) throws Exception {
        refreshScore();
    }

    private void textDate_TextChanged(Object sender, EventArgs e) throws Exception {
    }

    public void butPrint_Click(Object sender, EventArgs e) throws Exception {
        printWindow = new PrintWindowL();
        printWindow.Print(this.Handle);
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnesthesiaScore.class);
        this.textDate = new System.Windows.Forms.TextBox();
        this.labelDate = new System.Windows.Forms.Label();
        this.labelPatientName = new System.Windows.Forms.Label();
        this.textPatID = new System.Windows.Forms.TextBox();
        this.labelPatID = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.groupBoxPARSS = new System.Windows.Forms.GroupBox();
        this.groupBoxDischCond = new System.Windows.Forms.GroupBox();
        this.radDischCondUnstable = new System.Windows.Forms.RadioButton();
        this.radDischCondStable = new System.Windows.Forms.RadioButton();
        this.textPARSSTotal = new System.Windows.Forms.TextBox();
        this.labelTotal = new System.Windows.Forms.Label();
        this.groupBoxCirc = new System.Windows.Forms.GroupBox();
        this.radCircQ0 = new System.Windows.Forms.RadioButton();
        this.radCircQ1 = new System.Windows.Forms.RadioButton();
        this.radCircQ2 = new System.Windows.Forms.RadioButton();
        this.labelCircQ0 = new System.Windows.Forms.Label();
        this.labelCircQ1 = new System.Windows.Forms.Label();
        this.labelCircQ2 = new System.Windows.Forms.Label();
        this.groupBoxResp = new System.Windows.Forms.GroupBox();
        this.radRespQ0 = new System.Windows.Forms.RadioButton();
        this.radRespQ1 = new System.Windows.Forms.RadioButton();
        this.radRespQ2 = new System.Windows.Forms.RadioButton();
        this.labelRespQ0 = new System.Windows.Forms.Label();
        this.labelRespQ1 = new System.Windows.Forms.Label();
        this.labelRespQ2 = new System.Windows.Forms.Label();
        this.groupBoxActivity = new System.Windows.Forms.GroupBox();
        this.radActivityQ0 = new System.Windows.Forms.RadioButton();
        this.radActivityQ1 = new System.Windows.Forms.RadioButton();
        this.radActivityQ2 = new System.Windows.Forms.RadioButton();
        this.labelActivityQ0 = new System.Windows.Forms.Label();
        this.labelActivityQ1 = new System.Windows.Forms.Label();
        this.labelActivityQ2 = new System.Windows.Forms.Label();
        this.labelPARSS = new System.Windows.Forms.Label();
        this.groupBoxDischargeMethod = new System.Windows.Forms.GroupBox();
        this.radDischAmbulance = new System.Windows.Forms.RadioButton();
        this.radDischWheelChr = new System.Windows.Forms.RadioButton();
        this.radDischAmb = new System.Windows.Forms.RadioButton();
        this.groupBoxColor = new System.Windows.Forms.GroupBox();
        this.radColorQ0 = new System.Windows.Forms.RadioButton();
        this.radColorQ1 = new System.Windows.Forms.RadioButton();
        this.radColorQ2 = new System.Windows.Forms.RadioButton();
        this.labelColorQ0 = new System.Windows.Forms.Label();
        this.labelColorQ1 = new System.Windows.Forms.Label();
        this.labelColorQ2 = new System.Windows.Forms.Label();
        this.groupBoxConc = new System.Windows.Forms.GroupBox();
        this.radConcQ0 = new System.Windows.Forms.RadioButton();
        this.radConcQ1 = new System.Windows.Forms.RadioButton();
        this.radConcQ2 = new System.Windows.Forms.RadioButton();
        this.labelConcQ0 = new System.Windows.Forms.Label();
        this.labelConcQ1 = new System.Windows.Forms.Label();
        this.labelConcQ2 = new System.Windows.Forms.Label();
        this.butPrint = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBoxPARSS.SuspendLayout();
        this.groupBoxDischCond.SuspendLayout();
        this.groupBoxCirc.SuspendLayout();
        this.groupBoxResp.SuspendLayout();
        this.groupBoxActivity.SuspendLayout();
        this.groupBoxDischargeMethod.SuspendLayout();
        this.groupBoxColor.SuspendLayout();
        this.groupBoxConc.SuspendLayout();
        this.SuspendLayout();
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(406, 14);
        this.textDate.Name = "textDate";
        this.textDate.ReadOnly = true;
        this.textDate.Size = new System.Drawing.Size(144, 20);
        this.textDate.TabIndex = 148;
        this.textDate.TextChanged += new System.EventHandler(this.textDate_TextChanged);
        //
        // labelDate
        //
        this.labelDate.AutoSize = true;
        this.labelDate.Location = new System.Drawing.Point(370, 17);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(30, 13);
        this.labelDate.TabIndex = 147;
        this.labelDate.Text = "Date";
        //
        // labelPatientName
        //
        this.labelPatientName.AutoSize = true;
        this.labelPatientName.Location = new System.Drawing.Point(18, 17);
        this.labelPatientName.Name = "labelPatientName";
        this.labelPatientName.Size = new System.Drawing.Size(40, 13);
        this.labelPatientName.TabIndex = 146;
        this.labelPatientName.Text = "Patient";
        //
        // textPatID
        //
        this.textPatID.Location = new System.Drawing.Point(278, 14);
        this.textPatID.Name = "textPatID";
        this.textPatID.ReadOnly = true;
        this.textPatID.Size = new System.Drawing.Size(69, 20);
        this.textPatID.TabIndex = 145;
        this.textPatID.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // labelPatID
        //
        this.labelPatID.AutoSize = true;
        this.labelPatID.Location = new System.Drawing.Point(234, 17);
        this.labelPatID.Name = "labelPatID";
        this.labelPatID.Size = new System.Drawing.Size(38, 13);
        this.labelPatID.TabIndex = 144;
        this.labelPatID.Text = "ID No.";
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(64, 14);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(150, 20);
        this.textPatient.TabIndex = 143;
        this.textPatient.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        this.textPatient.TextChanged += new System.EventHandler(this.textPatient_TextChanged);
        //
        // groupBoxPARSS
        //
        this.groupBoxPARSS.Controls.Add(this.groupBoxDischCond);
        this.groupBoxPARSS.Controls.Add(this.textPARSSTotal);
        this.groupBoxPARSS.Controls.Add(this.labelTotal);
        this.groupBoxPARSS.Controls.Add(this.groupBoxCirc);
        this.groupBoxPARSS.Controls.Add(this.groupBoxResp);
        this.groupBoxPARSS.Controls.Add(this.groupBoxActivity);
        this.groupBoxPARSS.Controls.Add(this.labelPARSS);
        this.groupBoxPARSS.Controls.Add(this.groupBoxDischargeMethod);
        this.groupBoxPARSS.Controls.Add(this.groupBoxColor);
        this.groupBoxPARSS.Controls.Add(this.groupBoxConc);
        this.groupBoxPARSS.Location = new System.Drawing.Point(35, 53);
        this.groupBoxPARSS.Name = "groupBoxPARSS";
        this.groupBoxPARSS.Size = new System.Drawing.Size(517, 610);
        this.groupBoxPARSS.TabIndex = 149;
        this.groupBoxPARSS.TabStop = false;
        this.groupBoxPARSS.Text = "Post-Anesthesia Recovery Score System";
        //
        // groupBoxDischCond
        //
        this.groupBoxDischCond.Controls.Add(this.radDischCondUnstable);
        this.groupBoxDischCond.Controls.Add(this.radDischCondStable);
        this.groupBoxDischCond.Location = new System.Drawing.Point(360, 537);
        this.groupBoxDischCond.Name = "groupBoxDischCond";
        this.groupBoxDischCond.Size = new System.Drawing.Size(136, 61);
        this.groupBoxDischCond.TabIndex = 9;
        this.groupBoxDischCond.TabStop = false;
        this.groupBoxDischCond.Text = "Discharge condition";
        //
        // radDischCondUnstable
        //
        this.radDischCondUnstable.AutoSize = true;
        this.radDischCondUnstable.Location = new System.Drawing.Point(20, 35);
        this.radDischCondUnstable.Name = "radDischCondUnstable";
        this.radDischCondUnstable.Size = new System.Drawing.Size(67, 17);
        this.radDischCondUnstable.TabIndex = 4;
        this.radDischCondUnstable.TabStop = true;
        this.radDischCondUnstable.Text = "Unstable";
        this.radDischCondUnstable.UseVisualStyleBackColor = true;
        this.radDischCondUnstable.CheckedChanged += new System.EventHandler(this.radDischUnstable_CheckedChanged);
        //
        // radDischCondStable
        //
        this.radDischCondStable.AutoSize = true;
        this.radDischCondStable.Checked = true;
        this.radDischCondStable.Location = new System.Drawing.Point(20, 18);
        this.radDischCondStable.Name = "radDischCondStable";
        this.radDischCondStable.Size = new System.Drawing.Size(55, 17);
        this.radDischCondStable.TabIndex = 3;
        this.radDischCondStable.TabStop = true;
        this.radDischCondStable.Text = "Stable";
        this.radDischCondStable.UseVisualStyleBackColor = true;
        //
        // textPARSSTotal
        //
        this.textPARSSTotal.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textPARSSTotal.Location = new System.Drawing.Point(376, 507);
        this.textPARSSTotal.Name = "textPARSSTotal";
        this.textPARSSTotal.ReadOnly = true;
        this.textPARSSTotal.Size = new System.Drawing.Size(62, 20);
        this.textPARSSTotal.TabIndex = 7;
        //
        // labelTotal
        //
        this.labelTotal.AutoSize = true;
        this.labelTotal.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotal.Location = new System.Drawing.Point(90, 510);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(36, 13);
        this.labelTotal.TabIndex = 6;
        this.labelTotal.Text = "Total";
        //
        // groupBoxCirc
        //
        this.groupBoxCirc.Controls.Add(this.radCircQ0);
        this.groupBoxCirc.Controls.Add(this.radCircQ1);
        this.groupBoxCirc.Controls.Add(this.radCircQ2);
        this.groupBoxCirc.Controls.Add(this.labelCircQ0);
        this.groupBoxCirc.Controls.Add(this.labelCircQ1);
        this.groupBoxCirc.Controls.Add(this.labelCircQ2);
        this.groupBoxCirc.Location = new System.Drawing.Point(85, 252);
        this.groupBoxCirc.Name = "groupBoxCirc";
        this.groupBoxCirc.Size = new System.Drawing.Size(355, 76);
        this.groupBoxCirc.TabIndex = 3;
        this.groupBoxCirc.TabStop = false;
        this.groupBoxCirc.Text = "Circulation";
        //
        // radCircQ0
        //
        this.radCircQ0.AutoSize = true;
        this.radCircQ0.Location = new System.Drawing.Point(310, 47);
        this.radCircQ0.Name = "radCircQ0";
        this.radCircQ0.Size = new System.Drawing.Size(31, 17);
        this.radCircQ0.TabIndex = 5;
        this.radCircQ0.TabStop = true;
        this.radCircQ0.Text = "0";
        this.radCircQ0.UseVisualStyleBackColor = true;
        this.radCircQ0.CheckedChanged += new System.EventHandler(this.radCircQ0_CheckedChanged);
        //
        // radCircQ1
        //
        this.radCircQ1.AutoSize = true;
        this.radCircQ1.Location = new System.Drawing.Point(310, 29);
        this.radCircQ1.Name = "radCircQ1";
        this.radCircQ1.Size = new System.Drawing.Size(31, 17);
        this.radCircQ1.TabIndex = 4;
        this.radCircQ1.TabStop = true;
        this.radCircQ1.Text = "1";
        this.radCircQ1.UseVisualStyleBackColor = true;
        this.radCircQ1.CheckedChanged += new System.EventHandler(this.radCircQ1_CheckedChanged);
        //
        // radCircQ2
        //
        this.radCircQ2.AutoSize = true;
        this.radCircQ2.Location = new System.Drawing.Point(310, 11);
        this.radCircQ2.Name = "radCircQ2";
        this.radCircQ2.Size = new System.Drawing.Size(31, 17);
        this.radCircQ2.TabIndex = 3;
        this.radCircQ2.TabStop = true;
        this.radCircQ2.Text = "2";
        this.radCircQ2.UseVisualStyleBackColor = true;
        this.radCircQ2.CheckedChanged += new System.EventHandler(this.radCircQ2_CheckedChanged);
        //
        // labelCircQ0
        //
        this.labelCircQ0.AutoSize = true;
        this.labelCircQ0.Location = new System.Drawing.Point(46, 50);
        this.labelCircQ0.Name = "labelCircQ0";
        this.labelCircQ0.Size = new System.Drawing.Size(241, 13);
        this.labelCircQ0.TabIndex = 2;
        this.labelCircQ0.Text = "BP is +/- 50 mm Hg or more of preanesthetic level";
        //
        // labelCircQ1
        //
        this.labelCircQ1.AutoSize = true;
        this.labelCircQ1.Location = new System.Drawing.Point(21, 32);
        this.labelCircQ1.Name = "labelCircQ1";
        this.labelCircQ1.Size = new System.Drawing.Size(266, 13);
        this.labelCircQ1.TabIndex = 1;
        this.labelCircQ1.Text = "BP is +/- 20 mm Hg to 50 mm Hg of preanesthetic level";
        //
        // labelCircQ2
        //
        this.labelCircQ2.AutoSize = true;
        this.labelCircQ2.Location = new System.Drawing.Point(84, 13);
        this.labelCircQ2.Name = "labelCircQ2";
        this.labelCircQ2.Size = new System.Drawing.Size(203, 13);
        this.labelCircQ2.TabIndex = 0;
        this.labelCircQ2.Text = "BP is +/- 20 mm Hg of preanesthetic level";
        //
        // groupBoxResp
        //
        this.groupBoxResp.Controls.Add(this.radRespQ0);
        this.groupBoxResp.Controls.Add(this.radRespQ1);
        this.groupBoxResp.Controls.Add(this.radRespQ2);
        this.groupBoxResp.Controls.Add(this.labelRespQ0);
        this.groupBoxResp.Controls.Add(this.labelRespQ1);
        this.groupBoxResp.Controls.Add(this.labelRespQ2);
        this.groupBoxResp.Location = new System.Drawing.Point(85, 167);
        this.groupBoxResp.Name = "groupBoxResp";
        this.groupBoxResp.Size = new System.Drawing.Size(355, 76);
        this.groupBoxResp.TabIndex = 2;
        this.groupBoxResp.TabStop = false;
        this.groupBoxResp.Text = "Respiration";
        //
        // radRespQ0
        //
        this.radRespQ0.AutoSize = true;
        this.radRespQ0.Location = new System.Drawing.Point(310, 50);
        this.radRespQ0.Name = "radRespQ0";
        this.radRespQ0.Size = new System.Drawing.Size(31, 17);
        this.radRespQ0.TabIndex = 5;
        this.radRespQ0.TabStop = true;
        this.radRespQ0.Text = "0";
        this.radRespQ0.UseVisualStyleBackColor = true;
        this.radRespQ0.CheckedChanged += new System.EventHandler(this.radRespQ0_CheckedChanged);
        //
        // radRespQ1
        //
        this.radRespQ1.AutoSize = true;
        this.radRespQ1.Location = new System.Drawing.Point(310, 30);
        this.radRespQ1.Name = "radRespQ1";
        this.radRespQ1.Size = new System.Drawing.Size(31, 17);
        this.radRespQ1.TabIndex = 4;
        this.radRespQ1.TabStop = true;
        this.radRespQ1.Text = "1";
        this.radRespQ1.UseVisualStyleBackColor = true;
        this.radRespQ1.CheckedChanged += new System.EventHandler(this.radRespQ1_CheckedChanged);
        //
        // radRespQ2
        //
        this.radRespQ2.AutoSize = true;
        this.radRespQ2.Location = new System.Drawing.Point(310, 11);
        this.radRespQ2.Name = "radRespQ2";
        this.radRespQ2.Size = new System.Drawing.Size(31, 17);
        this.radRespQ2.TabIndex = 3;
        this.radRespQ2.TabStop = true;
        this.radRespQ2.Text = "2";
        this.radRespQ2.UseVisualStyleBackColor = true;
        this.radRespQ2.CheckedChanged += new System.EventHandler(this.radRespQ2_CheckedChanged);
        //
        // labelRespQ0
        //
        this.labelRespQ0.AutoSize = true;
        this.labelRespQ0.Location = new System.Drawing.Point(89, 52);
        this.labelRespQ0.Name = "labelRespQ0";
        this.labelRespQ0.Size = new System.Drawing.Size(38, 13);
        this.labelRespQ0.TabIndex = 2;
        this.labelRespQ0.Text = "Apnea";
        //
        // labelRespQ1
        //
        this.labelRespQ1.AutoSize = true;
        this.labelRespQ1.Location = new System.Drawing.Point(88, 32);
        this.labelRespQ1.Name = "labelRespQ1";
        this.labelRespQ1.Size = new System.Drawing.Size(140, 13);
        this.labelRespQ1.TabIndex = 1;
        this.labelRespQ1.Text = "Dyspnea or limited breathing";
        //
        // labelRespQ2
        //
        this.labelRespQ2.AutoSize = true;
        this.labelRespQ2.Location = new System.Drawing.Point(87, 13);
        this.labelRespQ2.Name = "labelRespQ2";
        this.labelRespQ2.Size = new System.Drawing.Size(161, 13);
        this.labelRespQ2.TabIndex = 0;
        this.labelRespQ2.Text = "Able to breath deeply and cough";
        //
        // groupBoxActivity
        //
        this.groupBoxActivity.Controls.Add(this.radActivityQ0);
        this.groupBoxActivity.Controls.Add(this.radActivityQ1);
        this.groupBoxActivity.Controls.Add(this.radActivityQ2);
        this.groupBoxActivity.Controls.Add(this.labelActivityQ0);
        this.groupBoxActivity.Controls.Add(this.labelActivityQ1);
        this.groupBoxActivity.Controls.Add(this.labelActivityQ2);
        this.groupBoxActivity.Location = new System.Drawing.Point(85, 86);
        this.groupBoxActivity.Name = "groupBoxActivity";
        this.groupBoxActivity.Size = new System.Drawing.Size(355, 76);
        this.groupBoxActivity.TabIndex = 1;
        this.groupBoxActivity.TabStop = false;
        this.groupBoxActivity.Text = "Activity";
        //
        // radActivityQ0
        //
        this.radActivityQ0.AutoSize = true;
        this.radActivityQ0.Location = new System.Drawing.Point(310, 50);
        this.radActivityQ0.Name = "radActivityQ0";
        this.radActivityQ0.Size = new System.Drawing.Size(31, 17);
        this.radActivityQ0.TabIndex = 5;
        this.radActivityQ0.TabStop = true;
        this.radActivityQ0.Text = "0";
        this.radActivityQ0.UseVisualStyleBackColor = true;
        this.radActivityQ0.CheckedChanged += new System.EventHandler(this.radActivityQ0_CheckedChanged);
        //
        // radActivityQ1
        //
        this.radActivityQ1.AutoSize = true;
        this.radActivityQ1.Location = new System.Drawing.Point(310, 30);
        this.radActivityQ1.Name = "radActivityQ1";
        this.radActivityQ1.Size = new System.Drawing.Size(31, 17);
        this.radActivityQ1.TabIndex = 4;
        this.radActivityQ1.TabStop = true;
        this.radActivityQ1.Text = "1";
        this.radActivityQ1.UseVisualStyleBackColor = true;
        this.radActivityQ1.CheckedChanged += new System.EventHandler(this.radActivityQ1_CheckedChanged);
        //
        // radActivityQ2
        //
        this.radActivityQ2.AutoSize = true;
        this.radActivityQ2.Location = new System.Drawing.Point(310, 11);
        this.radActivityQ2.Name = "radActivityQ2";
        this.radActivityQ2.Size = new System.Drawing.Size(31, 17);
        this.radActivityQ2.TabIndex = 3;
        this.radActivityQ2.TabStop = true;
        this.radActivityQ2.Text = "2";
        this.radActivityQ2.UseVisualStyleBackColor = true;
        this.radActivityQ2.CheckedChanged += new System.EventHandler(this.radActivityQ2_CheckedChanged);
        //
        // labelActivityQ0
        //
        this.labelActivityQ0.AutoSize = true;
        this.labelActivityQ0.Location = new System.Drawing.Point(89, 52);
        this.labelActivityQ0.Name = "labelActivityQ0";
        this.labelActivityQ0.Size = new System.Drawing.Size(140, 13);
        this.labelActivityQ0.TabIndex = 2;
        this.labelActivityQ0.Text = "Not able to move extremities";
        //
        // labelActivityQ1
        //
        this.labelActivityQ1.AutoSize = true;
        this.labelActivityQ1.Location = new System.Drawing.Point(88, 32);
        this.labelActivityQ1.Name = "labelActivityQ1";
        this.labelActivityQ1.Size = new System.Drawing.Size(141, 13);
        this.labelActivityQ1.TabIndex = 1;
        this.labelActivityQ1.Text = "Able to move two extremities";
        //
        // labelActivityQ2
        //
        this.labelActivityQ2.AutoSize = true;
        this.labelActivityQ2.Location = new System.Drawing.Point(87, 13);
        this.labelActivityQ2.Name = "labelActivityQ2";
        this.labelActivityQ2.Size = new System.Drawing.Size(142, 13);
        this.labelActivityQ2.TabIndex = 0;
        this.labelActivityQ2.Text = "Able to move four extremities";
        //
        // labelPARSS
        //
        this.labelPARSS.Location = new System.Drawing.Point(40, 25);
        this.labelPARSS.Name = "labelPARSS";
        this.labelPARSS.Size = new System.Drawing.Size(450, 58);
        this.labelPARSS.TabIndex = 0;
        this.labelPARSS.Text = resources.GetString("labelPARSS.Text");
        //
        // groupBoxDischargeMethod
        //
        this.groupBoxDischargeMethod.Controls.Add(this.radDischAmbulance);
        this.groupBoxDischargeMethod.Controls.Add(this.radDischWheelChr);
        this.groupBoxDischargeMethod.Controls.Add(this.radDischAmb);
        this.groupBoxDischargeMethod.Location = new System.Drawing.Point(22, 537);
        this.groupBoxDischargeMethod.Name = "groupBoxDischargeMethod";
        this.groupBoxDischargeMethod.Size = new System.Drawing.Size(341, 61);
        this.groupBoxDischargeMethod.TabIndex = 8;
        this.groupBoxDischargeMethod.TabStop = false;
        this.groupBoxDischargeMethod.Text = "Discharge method";
        //
        // radDischAmbulance
        //
        this.radDischAmbulance.AutoSize = true;
        this.radDischAmbulance.Location = new System.Drawing.Point(14, 35);
        this.radDischAmbulance.Name = "radDischAmbulance";
        this.radDischAmbulance.Size = new System.Drawing.Size(154, 17);
        this.radDischAmbulance.TabIndex = 6;
        this.radDischAmbulance.TabStop = true;
        this.radDischAmbulance.Text = "Transported via ambulance";
        this.radDischAmbulance.UseVisualStyleBackColor = true;
        //
        // radDischWheelChr
        //
        this.radDischWheelChr.AutoSize = true;
        this.radDischWheelChr.Location = new System.Drawing.Point(179, 18);
        this.radDischWheelChr.Name = "radDischWheelChr";
        this.radDischWheelChr.Size = new System.Drawing.Size(147, 17);
        this.radDischWheelChr.TabIndex = 4;
        this.radDischWheelChr.TabStop = true;
        this.radDischWheelChr.Text = "Transported in wheelchair";
        this.radDischWheelChr.UseVisualStyleBackColor = true;
        //
        // radDischAmb
        //
        this.radDischAmb.AutoSize = true;
        this.radDischAmb.Location = new System.Drawing.Point(14, 18);
        this.radDischAmb.Name = "radDischAmb";
        this.radDischAmb.Size = new System.Drawing.Size(152, 17);
        this.radDischAmb.TabIndex = 3;
        this.radDischAmb.TabStop = true;
        this.radDischAmb.Text = "Ambulatory with assistance";
        this.radDischAmb.UseVisualStyleBackColor = true;
        //
        // groupBoxColor
        //
        this.groupBoxColor.Controls.Add(this.radColorQ0);
        this.groupBoxColor.Controls.Add(this.radColorQ1);
        this.groupBoxColor.Controls.Add(this.radColorQ2);
        this.groupBoxColor.Controls.Add(this.labelColorQ0);
        this.groupBoxColor.Controls.Add(this.labelColorQ1);
        this.groupBoxColor.Controls.Add(this.labelColorQ2);
        this.groupBoxColor.Location = new System.Drawing.Point(85, 423);
        this.groupBoxColor.Name = "groupBoxColor";
        this.groupBoxColor.Size = new System.Drawing.Size(355, 76);
        this.groupBoxColor.TabIndex = 5;
        this.groupBoxColor.TabStop = false;
        this.groupBoxColor.Text = "Color";
        //
        // radColorQ0
        //
        this.radColorQ0.AutoSize = true;
        this.radColorQ0.Location = new System.Drawing.Point(310, 50);
        this.radColorQ0.Name = "radColorQ0";
        this.radColorQ0.Size = new System.Drawing.Size(31, 17);
        this.radColorQ0.TabIndex = 5;
        this.radColorQ0.TabStop = true;
        this.radColorQ0.Text = "0";
        this.radColorQ0.UseVisualStyleBackColor = true;
        this.radColorQ0.CheckedChanged += new System.EventHandler(this.radColorQ0_CheckedChanged);
        //
        // radColorQ1
        //
        this.radColorQ1.AutoSize = true;
        this.radColorQ1.Location = new System.Drawing.Point(310, 30);
        this.radColorQ1.Name = "radColorQ1";
        this.radColorQ1.Size = new System.Drawing.Size(31, 17);
        this.radColorQ1.TabIndex = 4;
        this.radColorQ1.TabStop = true;
        this.radColorQ1.Text = "1";
        this.radColorQ1.UseVisualStyleBackColor = true;
        this.radColorQ1.CheckedChanged += new System.EventHandler(this.radColorQ1_CheckedChanged);
        //
        // radColorQ2
        //
        this.radColorQ2.AutoSize = true;
        this.radColorQ2.Location = new System.Drawing.Point(310, 11);
        this.radColorQ2.Name = "radColorQ2";
        this.radColorQ2.Size = new System.Drawing.Size(31, 17);
        this.radColorQ2.TabIndex = 3;
        this.radColorQ2.TabStop = true;
        this.radColorQ2.Text = "2";
        this.radColorQ2.UseVisualStyleBackColor = true;
        this.radColorQ2.CheckedChanged += new System.EventHandler(this.radColorQ2_CheckedChanged);
        //
        // labelColorQ0
        //
        this.labelColorQ0.AutoSize = true;
        this.labelColorQ0.Location = new System.Drawing.Point(89, 52);
        this.labelColorQ0.Name = "labelColorQ0";
        this.labelColorQ0.Size = new System.Drawing.Size(48, 13);
        this.labelColorQ0.TabIndex = 2;
        this.labelColorQ0.Text = "Cyanotic";
        //
        // labelColorQ1
        //
        this.labelColorQ1.AutoSize = true;
        this.labelColorQ1.Location = new System.Drawing.Point(88, 33);
        this.labelColorQ1.Name = "labelColorQ1";
        this.labelColorQ1.Size = new System.Drawing.Size(184, 13);
        this.labelColorQ1.TabIndex = 1;
        this.labelColorQ1.Text = "Pale, dusky, blotchy, jaundiced, other";
        //
        // labelColorQ2
        //
        this.labelColorQ2.AutoSize = true;
        this.labelColorQ2.Location = new System.Drawing.Point(87, 13);
        this.labelColorQ2.Name = "labelColorQ2";
        this.labelColorQ2.Size = new System.Drawing.Size(43, 13);
        this.labelColorQ2.TabIndex = 0;
        this.labelColorQ2.Text = "Healthy";
        //
        // groupBoxConc
        //
        this.groupBoxConc.Controls.Add(this.radConcQ0);
        this.groupBoxConc.Controls.Add(this.radConcQ1);
        this.groupBoxConc.Controls.Add(this.radConcQ2);
        this.groupBoxConc.Controls.Add(this.labelConcQ0);
        this.groupBoxConc.Controls.Add(this.labelConcQ1);
        this.groupBoxConc.Controls.Add(this.labelConcQ2);
        this.groupBoxConc.Location = new System.Drawing.Point(85, 338);
        this.groupBoxConc.Name = "groupBoxConc";
        this.groupBoxConc.Size = new System.Drawing.Size(355, 76);
        this.groupBoxConc.TabIndex = 4;
        this.groupBoxConc.TabStop = false;
        this.groupBoxConc.Text = "Consciousness";
        //
        // radConcQ0
        //
        this.radConcQ0.AutoSize = true;
        this.radConcQ0.Location = new System.Drawing.Point(310, 50);
        this.radConcQ0.Name = "radConcQ0";
        this.radConcQ0.Size = new System.Drawing.Size(31, 17);
        this.radConcQ0.TabIndex = 5;
        this.radConcQ0.TabStop = true;
        this.radConcQ0.Text = "0";
        this.radConcQ0.UseVisualStyleBackColor = true;
        this.radConcQ0.CheckedChanged += new System.EventHandler(this.radConcQ0_CheckedChanged);
        //
        // radConcQ1
        //
        this.radConcQ1.AutoSize = true;
        this.radConcQ1.Location = new System.Drawing.Point(310, 30);
        this.radConcQ1.Name = "radConcQ1";
        this.radConcQ1.Size = new System.Drawing.Size(31, 17);
        this.radConcQ1.TabIndex = 4;
        this.radConcQ1.TabStop = true;
        this.radConcQ1.Text = "1";
        this.radConcQ1.UseVisualStyleBackColor = true;
        this.radConcQ1.CheckedChanged += new System.EventHandler(this.radConcQ1_CheckedChanged);
        //
        // radConcQ2
        //
        this.radConcQ2.AutoSize = true;
        this.radConcQ2.Location = new System.Drawing.Point(310, 11);
        this.radConcQ2.Name = "radConcQ2";
        this.radConcQ2.Size = new System.Drawing.Size(31, 17);
        this.radConcQ2.TabIndex = 3;
        this.radConcQ2.TabStop = true;
        this.radConcQ2.Text = "2";
        this.radConcQ2.UseVisualStyleBackColor = true;
        this.radConcQ2.CheckedChanged += new System.EventHandler(this.radConcQ2_CheckedChanged);
        //
        // labelConcQ0
        //
        this.labelConcQ0.AutoSize = true;
        this.labelConcQ0.Location = new System.Drawing.Point(88, 52);
        this.labelConcQ0.Name = "labelConcQ0";
        this.labelConcQ0.Size = new System.Drawing.Size(72, 13);
        this.labelConcQ0.TabIndex = 2;
        this.labelConcQ0.Text = "Unresponsive";
        //
        // labelConcQ1
        //
        this.labelConcQ1.AutoSize = true;
        this.labelConcQ1.Location = new System.Drawing.Point(77, 31);
        this.labelConcQ1.Name = "labelConcQ1";
        this.labelConcQ1.Size = new System.Drawing.Size(102, 13);
        this.labelConcQ1.TabIndex = 1;
        this.labelConcQ1.Text = "Arousable on calling";
        //
        // labelConcQ2
        //
        this.labelConcQ2.AutoSize = true;
        this.labelConcQ2.Location = new System.Drawing.Point(89, 13);
        this.labelConcQ2.Name = "labelConcQ2";
        this.labelConcQ2.Size = new System.Drawing.Size(63, 13);
        this.labelConcQ2.TabIndex = 0;
        this.labelConcQ2.Text = "Fully awake";
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(108, 669);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(88, 26);
        this.butPrint.TabIndex = 150;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(449, 669);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(89, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "Save and Close";
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
        this.butCancel.Image = Resources.getdeleteX();
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCancel.Location = new System.Drawing.Point(368, 669);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAnesthesiaScore
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(584, 707);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.labelDate);
        this.Controls.Add(this.labelPatientName);
        this.Controls.Add(this.textPatID);
        this.Controls.Add(this.labelPatID);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.groupBoxPARSS);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAnesthesiaScore";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Post-Anesthesia Score";
        this.Load += new System.EventHandler(this.FormAnesthesiaScore_Load);
        this.groupBoxPARSS.ResumeLayout(false);
        this.groupBoxPARSS.PerformLayout();
        this.groupBoxDischCond.ResumeLayout(false);
        this.groupBoxDischCond.PerformLayout();
        this.groupBoxCirc.ResumeLayout(false);
        this.groupBoxCirc.PerformLayout();
        this.groupBoxResp.ResumeLayout(false);
        this.groupBoxResp.PerformLayout();
        this.groupBoxActivity.ResumeLayout(false);
        this.groupBoxActivity.PerformLayout();
        this.groupBoxDischargeMethod.ResumeLayout(false);
        this.groupBoxDischargeMethod.PerformLayout();
        this.groupBoxColor.ResumeLayout(false);
        this.groupBoxColor.PerformLayout();
        this.groupBoxConc.ResumeLayout(false);
        this.groupBoxConc.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    public OpenDental.UI.Button butPrint;
    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelPatientName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelPatID = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBoxPARSS = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textPARSSTotal = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelTotal = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxCirc = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radCircQ0 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radCircQ1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radCircQ2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelCircQ0 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCircQ1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCircQ2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxResp = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radRespQ0 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radRespQ1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radRespQ2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelRespQ0 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelRespQ1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelRespQ2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxActivity = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radActivityQ0 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radActivityQ1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radActivityQ2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelActivityQ0 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelActivityQ1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelActivityQ2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelPARSS = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxDischargeMethod = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radDischWheelChr = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radDischAmb = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBoxColor = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radColorQ0 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radColorQ1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radColorQ2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelColorQ0 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelColorQ1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelColorQ2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxConc = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radConcQ0 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radConcQ1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radConcQ2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label labelConcQ0 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelConcQ1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelConcQ2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBoxDischCond = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radDischCondUnstable = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radDischCondStable = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radDischAmbulance = new System.Windows.Forms.RadioButton();
}


