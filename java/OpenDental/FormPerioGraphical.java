//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormPerioGraphicalSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PrinterL;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Document;
import OpenDentBusiness.DrawingMode;
import OpenDentBusiness.ImageCategorySpecial;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PerioExam;
import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioMeasures;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ToothNumberingNomenclature;
import SparksToothChart.ToothChartDirectX;
import OpenDental.Properties.Resources;

public class FormPerioGraphical  extends Form 
{

    public PerioExam PerioExamCur;
    public Patient PatCur;
    //public List<PerioMeasure> ListPerioMeasures;
    public FormPerioGraphical(PerioExam perioExam, Patient patient) throws Exception {
        PerioExamCur = perioExam;
        PatCur = patient;
        initializeComponent();
        //ComputerPref localComputerPrefs=ComputerPrefs.GetForLocalComputer();
        toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(ComputerPrefs.getLocalComputer().DirectXFormat));
        //Must be set before draw mode
        toothChart.setDrawMode(DrawingMode.DirectX);
        toothChart.setToothNumberingNomenclature(ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
        toothChart.setColorBackground(Color.White);
        toothChart.setColorText(Color.Black);
        toothChart.setPerioMode(true);
        toothChart.setColorBleeding(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][1].ItemColor);
        toothChart.setColorSuppuration(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][2].ItemColor);
        toothChart.setColorProbing(PrefC.getColor(PrefName.PerioColorProbing));
        toothChart.setColorProbingRed(PrefC.getColor(PrefName.PerioColorProbingRed));
        toothChart.setColorGingivalMargin(PrefC.getColor(PrefName.PerioColorGM));
        toothChart.setColorCAL(PrefC.getColor(PrefName.PerioColorCAL));
        toothChart.setColorMGJ(PrefC.getColor(PrefName.PerioColorMGJ));
        toothChart.setColorFurcations(PrefC.getColor(PrefName.PerioColorFurcations));
        toothChart.setColorFurcationsRed(PrefC.getColor(PrefName.PerioColorFurcationsRed));
        toothChart.setRedLimitProbing(PrefC.getInt(PrefName.PerioRedProb));
        toothChart.setRedLimitFurcations(PrefC.getInt(PrefName.PerioRedFurc));
        List<PerioMeasure> listMeas = PerioMeasures.getAllForExam(PerioExamCur.PerioExamNum);
        //compute CAL's for each site.  If a CAL is valid, pass it in.
        PerioMeasure measureProbe;
        PerioMeasure measureGM;
        int gm = new int();
        int pd = new int();
        int calMB = new int();
        int calB = new int();
        int calDB = new int();
        int calML = new int();
        int calL = new int();
        int calDL = new int();
        for (int t = 1;t <= 32;t++)
        {
            measureProbe = null;
            measureGM = null;
            for (int i = 0;i < listMeas.Count;i++)
            {
                if (listMeas[i].IntTooth != t)
                {
                    continue;
                }
                 
                if (listMeas[i].SequenceType == PerioSequenceType.Probing)
                {
                    measureProbe = listMeas[i];
                }
                 
                if (listMeas[i].SequenceType == PerioSequenceType.GingMargin)
                {
                    measureGM = listMeas[i];
                }
                 
            }
            if (measureProbe == null || measureGM == null)
            {
                continue;
            }
             
            //to the next tooth
            //mb
            calMB = -1;
            gm = measureGM.MBvalue;
            //MBvalue must stay over 100 for hyperplasia, because that's how we're storing it in ToothChartData.ListPerioMeasure.
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.MBvalue;
            if (measureGM.MBvalue != -1 && pd != -1)
            {
                calMB = gm + pd;
                if (calMB < 0)
                {
                    calMB = 0;
                }
                 
            }
             
            //CALs can't be negative
            //B
            calB = -1;
            gm = measureGM.Bvalue;
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.Bvalue;
            if (measureGM.Bvalue != -1 && pd != -1)
            {
                calB = gm + pd;
                if (calB < 0)
                {
                    calB = 0;
                }
                 
            }
             
            //DB
            calDB = -1;
            gm = measureGM.DBvalue;
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.DBvalue;
            if (measureGM.DBvalue != -1 && pd != -1)
            {
                calDB = gm + pd;
                if (calDB < 0)
                {
                    calDB = 0;
                }
                 
            }
             
            //ML
            calML = -1;
            gm = measureGM.MLvalue;
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.MLvalue;
            if (measureGM.MLvalue != -1 && pd != -1)
            {
                calML = gm + pd;
                if (calML < 0)
                {
                    calML = 0;
                }
                 
            }
             
            //L
            calL = -1;
            gm = measureGM.Lvalue;
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.Lvalue;
            if (measureGM.Lvalue != -1 && pd != -1)
            {
                calL = gm + pd;
                if (calL < 0)
                {
                    calL = 0;
                }
                 
            }
             
            //DL
            calDL = -1;
            gm = measureGM.DLvalue;
            if (gm > 100)
            {
                //hyperplasia
                gm = 100 - gm;
            }
             
            //e.g. 100-103=-3
            pd = measureProbe.DLvalue;
            if (measureGM.DLvalue != -1 && pd != -1)
            {
                calDL = gm + pd;
                if (calDL < 0)
                {
                    calDL = 0;
                }
                 
            }
             
            if (calMB != -1 || calB != -1 || calDB != -1 || calML != -1 || calL != -1 || calDL != -1)
            {
                toothChart.addPerioMeasure(t,PerioSequenceType.CAL,calMB,calB,calDB,calML,calL,calDL);
            }
             
        }
        for (int i = 0;i < listMeas.Count;i++)
        {
            if (listMeas[i].SequenceType == PerioSequenceType.SkipTooth)
            {
                toothChart.SetMissing(listMeas[i].IntTooth.ToString());
            }
            else if (listMeas[i].SequenceType == PerioSequenceType.Mobility)
            {
                int mob = listMeas[i].ToothValue;
                Color color = Color.Black;
                if (mob >= PrefC.getInt(PrefName.PerioRedMob))
                {
                    color = Color.Red;
                }
                 
                toothChart.SetMobility(listMeas[i].IntTooth.ToString(), mob.ToString(), color);
            }
            else
            {
                toothChart.AddPerioMeasure(listMeas[i]);
            }  
        }
    }

    /*
    			toothChart.SetMissing("13");
    			toothChart.SetMissing("14");
    			toothChart.SetMissing("18");
    			toothChart.SetMissing("25");
    			toothChart.SetMissing("26");
    			toothChart.SetImplant("14",Color.Gray);
    			//Movements are too low of a priority to test right now.  We might not even want to implement them.
    			//toothChart.MoveTooth("4",0,0,0,0,-5,0);
    			//toothChart.MoveTooth("16",0,20,0,-3,0,0);
    			//toothChart.MoveTooth("24",15,2,0,0,0,0);
    			toothChart.SetMobility("2","3",Color.Red);
    			toothChart.SetMobility("7","2",Color.Red);
    			toothChart.SetMobility("8","2",Color.Red);
    			toothChart.SetMobility("9","2",Color.Red);
    			toothChart.SetMobility("10","2",Color.Red);
    			toothChart.SetMobility("16","3",Color.Red);
    			toothChart.SetMobility("24","2",Color.Red);
    			toothChart.SetMobility("31","3",Color.Red);
    			toothChart.AddPerioMeasure(1,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
    			toothChart.AddPerioMeasure(2,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
    			toothChart.AddPerioMeasure(3,PerioSequenceType.Furcation,-1,2,-1,1,-1,-1);
    			toothChart.AddPerioMeasure(5,PerioSequenceType.Furcation,1,-1,-1,-1,-1,-1);
    			toothChart.AddPerioMeasure(30,PerioSequenceType.Furcation,-1,-1,-1,-1,3,-1);
    			for(int i=1;i<=32;i++) {
    				//string tooth_id=i.ToString();
    				//bleeding and suppuration on all MB sites
    				//bleeding only all DL sites
    				//suppuration only all B sites
    				//blood=1, suppuration=2, both=3
    				toothChart.AddPerioMeasure(i,PerioSequenceType.Bleeding,  3,2,-1,-1,-1,1);
    				toothChart.AddPerioMeasure(i,PerioSequenceType.GingMargin,0,1,1,1,0,0);
    				toothChart.AddPerioMeasure(i,PerioSequenceType.Probing,   3,2,3,4,2,3);
    				toothChart.AddPerioMeasure(i,PerioSequenceType.CAL,       3,3,4,5,2,3);//basically GingMargin+Probing, unless one of them is -1
    				toothChart.AddPerioMeasure(i,PerioSequenceType.MGJ,       5,5,5,6,6,6);
    			}*/
    private void formPerioGraphic_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        PrintDocument pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (!PrinterL.setPrinter(pd2,PrintSituation.TPPerio,PatCur.PatNum,"Graphical perio chart printed"))
        {
            return ;
        }
         
        pd2.Print();
    }

    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        Graphics g = ev.Graphics;
        RenderPerioPrintout(g, PatCur, ev.MarginBounds);
    }

    //,new Rectangle(0,50,ev.MarginBounds.Width,ev.MarginBounds.Height));
    public void renderPerioPrintout(Graphics g, Patient pat, Rectangle marginBounds) throws Exception {
        String clinicName = "";
        //This clinic name could be more accurate here in the future if we make perio exams clinic specific.
        //Perhaps if there were a perioexam.ClinicNum column.
        if (pat.ClinicNum != 0)
        {
            Clinic clinic = Clinics.getClinic(pat.ClinicNum);
            clinicName = clinic.Description;
        }
        else
        {
            clinicName = PrefC.getString(PrefName.PracticeTitle);
        } 
        float y = 70;
        SizeF sizeFPage = new SizeF(marginBounds.Width, marginBounds.Height);
        SizeF sizeStr = new SizeF();
        Font font = new Font("Arial", 15);
        String titleStr = "PERIODONTAL EXAMINATION";
        sizeStr = g.MeasureString(titleStr, font);
        g.DrawString(titleStr, font, Brushes.Black, new PointF(sizeFPage.Width / 2f - sizeStr.Width / 2f, y));
        y += sizeStr.Height;
        font = new Font("Arial", 11);
        sizeStr = g.MeasureString(clinicName, font);
        g.DrawString(clinicName, font, Brushes.Black, new PointF(sizeFPage.Width / 2f - sizeStr.Width / 2f, y));
        y += sizeStr.Height;
        String patNameStr = PatCur.getNameFLFormal();
        sizeStr = g.MeasureString(patNameStr, font);
        g.DrawString(patNameStr, font, Brushes.Black, new PointF(sizeFPage.Width / 2f - sizeStr.Width / 2f, y));
        y += sizeStr.Height;
        String examDateStr = PerioExamCur.ExamDate.ToShortDateString();
        //Locale specific exam date.
        sizeStr = g.MeasureString(examDateStr, font);
        g.DrawString(examDateStr, font, Brushes.Black, new PointF(sizeFPage.Width / 2f - sizeStr.Width / 2f, y));
        y += sizeStr.Height;
        Bitmap bitmapTC = toothChart.getBitmap();
        g.DrawImage(bitmapTC, sizeFPage.Width / 2f - bitmapTC.Width / 2f, y, bitmapTC.Width, bitmapTC.Height);
    }

    private void butSetup_Click(Object sender, EventArgs e) throws Exception {
        FormPerioGraphicalSetup fpgs = new FormPerioGraphicalSetup();
        if (fpgs.ShowDialog() == DialogResult.OK)
        {
            toothChart.setColorCAL(PrefC.getColor(PrefName.PerioColorCAL));
            toothChart.setColorFurcations(PrefC.getColor(PrefName.PerioColorFurcations));
            toothChart.setColorFurcationsRed(PrefC.getColor(PrefName.PerioColorFurcationsRed));
            toothChart.setColorGingivalMargin(PrefC.getColor(PrefName.PerioColorGM));
            toothChart.setColorMGJ(PrefC.getColor(PrefName.PerioColorMGJ));
            toothChart.setColorProbing(PrefC.getColor(PrefName.PerioColorProbing));
            toothChart.setColorProbingRed(PrefC.getColor(PrefName.PerioColorProbingRed));
            this.toothChart.Invalidate();
        }
         
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        long defNumToothCharts = DefC.getImageCat(ImageCategorySpecial.T);
        if (defNumToothCharts == 0)
        {
            MsgBox.show(this,"In Setup, Definitions, Image Categories, a category needs to be set for graphical tooth charts.");
            return ;
        }
         
        Bitmap bitmap = null;
        Graphics g = null;
        Document doc = new Document();
        bitmap = new Bitmap(750, 1000);
        g = Graphics.FromImage(bitmap);
        g.Clear(Color.White);
        g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
        g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;
        renderPerioPrintout(g,PatCur,new Rectangle(0, 0, bitmap.Width, bitmap.Height));
        try
        {
            ImageStore.import(bitmap,defNumToothCharts,ImageType.Photo,PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Unable to save file: ") + ex.Message);
            bitmap.Dispose();
            bitmap = null;
            g.Dispose();
            return ;
        }

        MsgBox.show(this,"Saved.");
        if (g != null)
        {
            g.Dispose();
            g = null;
        }
         
        if (bitmap != null)
        {
            bitmap.Dispose();
            bitmap = null;
        }
         
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        SparksToothChart.ToothChartData toothChartData1 = new SparksToothChart.ToothChartData();
        this.toothChart = new SparksToothChart.ToothChartWrapper();
        this.butPrint = new OpenDental.UI.Button();
        this.butSave = new OpenDental.UI.Button();
        this.butSetup = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // toothChart
        //
        this.toothChart.setAutoFinish(false);
        this.toothChart.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChart.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChart.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChart.setDeviceFormat(null);
        this.toothChart.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChart.Location = new System.Drawing.Point(66, 12);
        this.toothChart.Name = "toothChart";
        this.toothChart.setPerioMode(false);
        this.toothChart.setPreferredPixelFormatNumber(0);
        this.toothChart.Size = new System.Drawing.Size(700, 751);
        this.toothChart.TabIndex = 198;
        toothChartData1.setSizeControl(new System.Drawing.Size(700, 751));
        this.toothChart.setTcData(toothChartData1);
        this.toothChart.setUseHardware(false);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(564, 787);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(81, 24);
        this.butPrint.TabIndex = 220;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(466, 787);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(92, 24);
        this.butSave.TabIndex = 219;
        this.butSave.Text = "Save to Images";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butSetup
        //
        this.butSetup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSetup.setAutosize(true);
        this.butSetup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetup.setCornerRadius(4F);
        this.butSetup.Location = new System.Drawing.Point(66, 787);
        this.butSetup.Name = "butSetup";
        this.butSetup.Size = new System.Drawing.Size(88, 24);
        this.butSetup.TabIndex = 221;
        this.butSetup.Text = "Setup Colors";
        this.butSetup.Click += new System.EventHandler(this.butSetup_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(691, 787);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 222;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormPerioGraphical
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(846, 851);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butSetup);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.toothChart);
        this.Name = "FormPerioGraphical";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Graphical Perio Chart";
        this.Load += new System.EventHandler(this.FormPerioGraphic_Load);
        this.ResumeLayout(false);
    }

    private SparksToothChart.ToothChartWrapper toothChart;
    private OpenDental.UI.Button butSave;
    private OpenDental.UI.Button butSetup;
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butClose;
}


