//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothNumberingNomenclature;
import OpenDentBusiness.ToothPaintingType;
import SparksToothChart.ToothChartDirectX;

/**
* Summary description for FormBasicTemplate.
*/
public class FormToothChartingBig  extends System.Windows.Forms.Form 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private boolean ShowBySelectedTeeth = new boolean();
    private List<ToothInitial> ToothInitialList = new List<ToothInitial>();
    private SparksToothChart.ToothChartWrapper toothChart;
    private List<DataRow> ProcList = new List<DataRow>();
    /**
    * 
    */
    public FormToothChartingBig(boolean showBySelectedTeeth, List<ToothInitial> toothInitialList, List<DataRow> procList) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        ShowBySelectedTeeth = showBySelectedTeeth;
        ToothInitialList = toothInitialList;
        ProcList = procList;
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
        SparksToothChart.ToothChartData toothChartData1 = new SparksToothChart.ToothChartData();
        this.toothChart = new SparksToothChart.ToothChartWrapper();
        this.SuspendLayout();
        //
        // toothChart
        //
        this.toothChart.setAutoFinish(false);
        this.toothChart.setColorBackground(System.Drawing.Color.Empty);
        this.toothChart.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChart.Dock = System.Windows.Forms.DockStyle.Fill;
        this.toothChart.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChart.Location = new System.Drawing.Point(0, 0);
        this.toothChart.Name = "toothChart";
        this.toothChart.setPerioMode(false);
        this.toothChart.setPreferredPixelFormatNumber(0);
        this.toothChart.Size = new System.Drawing.Size(926, 858);
        this.toothChart.TabIndex = 0;
        toothChartData1.setSizeControl(new System.Drawing.Size(926, 858));
        this.toothChart.setTcData(toothChartData1);
        this.toothChart.setUseHardware(false);
        //
        // FormToothChartingBig
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(926, 858);
        this.Controls.Add(this.toothChart);
        this.Name = "FormToothChartingBig";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormToothChartingBig_Load);
        this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.FormToothChartingBig_FormClosed);
        this.ResizeEnd += new System.EventHandler(this.FormToothChartingBig_ResizeEnd);
        this.ResumeLayout(false);
    }

    private void formToothChartingBig_Load(Object sender, EventArgs e) throws Exception {
        //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
        toothChart.setUseHardware(ComputerPrefs.getLocalComputer().GraphicsUseHardware);
        toothChart.setPreferredPixelFormatNumber(ComputerPrefs.getLocalComputer().PreferredPixelFormatNum);
        toothChart.setToothNumberingNomenclature(ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
        //Must be last preference set, last so that all settings are caried through in the reinitialization this line triggers.
        if (ComputerPrefs.getLocalComputer().GraphicsSimple == OpenDentBusiness.DrawingMode.Simple2D)
        {
            toothChart.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        }
        else if (ComputerPrefs.getLocalComputer().GraphicsSimple == OpenDentBusiness.DrawingMode.DirectX)
        {
            toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(ComputerPrefs.getLocalComputer().DirectXFormat));
            toothChart.setDrawMode(OpenDentBusiness.DrawingMode.DirectX);
        }
        else
        {
            toothChart.setDrawMode(OpenDentBusiness.DrawingMode.OpenGL);
        }  
        //The preferred pixel format number changes to the selected pixel format number after a context is chosen.
        ComputerPrefs.getLocalComputer().PreferredPixelFormatNum = toothChart.getPreferredPixelFormatNumber();
        ComputerPrefs.update(ComputerPrefs.getLocalComputer());
        fillToothChart();
    }

    //toothChart.Refresh();
    private void formToothChartingBig_ResizeEnd(Object sender, EventArgs e) throws Exception {
        fillToothChart();
    }

    //toothChart.Refresh();
    /**
    * This is, of course, called when module refreshed.  But it's also called when user sets missing teeth or tooth movements.  In that case, the Progress notes are not refreshed, so it's a little faster.  This also fills in the movement amounts.
    */
    private void fillToothChart() throws Exception {
        Cursor = Cursors.WaitCursor;
        toothChart.SuspendLayout();
        toothChart.setColorBackground(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][10].ItemColor);
        toothChart.setColorText(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][11].ItemColor);
        toothChart.setColorTextHighlight(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][12].ItemColor);
        toothChart.setColorBackHighlight(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][13].ItemColor);
        //remember which teeth were selected
        List<String> selectedTeeth = new List<String>(toothChart.getSelectedTeeth());
        //ArrayList selectedTeeth=new ArrayList();//integers 1-32
        //for(int i=0;i<toothChart.SelectedTeeth.Length;i++) {
        //	selectedTeeth.Add(Tooth.ToInt(toothChart.SelectedTeeth[i]));
        //}
        toothChart.resetTeeth();
        /*if(PatCur==null) {
        				toothChart.ResumeLayout();
        				FillMovementsAndHidden();
        				Cursor=Cursors.Default;
        				return;
        			}*/
        if (ShowBySelectedTeeth)
        {
            for (int i = 0;i < selectedTeeth.Count;i++)
            {
                toothChart.SetSelected(selectedTeeth[i], true);
            }
        }
         
        for (int i = 0;i < ToothInitialList.Count;i++)
        {
            //first, primary.  That way, you can still set a primary tooth missing afterwards.
            if (ToothInitialList[i].InitialType == ToothInitialType.Primary)
            {
                toothChart.SetPrimary(ToothInitialList[i].ToothNum);
            }
             
        }
        for (int i = 0;i < ToothInitialList.Count;i++)
        {
            InitialType __dummyScrutVar0 = ToothInitialList[i].InitialType;
            if (__dummyScrutVar0.equals(ToothInitialType.Missing))
            {
                toothChart.SetMissing(ToothInitialList[i].ToothNum);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.Hidden))
            {
                toothChart.SetHidden(ToothInitialList[i].ToothNum);
            }
            else //case ToothInitialType.Primary:
            //	break;
            if (__dummyScrutVar0.equals(ToothInitialType.Rotate))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, ToothInitialList[i].Movement, 0, 0, 0, 0, 0);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.TipM))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, ToothInitialList[i].Movement, 0, 0, 0, 0);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.TipB))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, ToothInitialList[i].Movement, 0, 0, 0);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.ShiftM))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, ToothInitialList[i].Movement, 0, 0);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.ShiftO))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, ToothInitialList[i].Movement, 0);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.ShiftB))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, 0, ToothInitialList[i].Movement);
            }
            else if (__dummyScrutVar0.equals(ToothInitialType.Drawing))
            {
                toothChart.AddDrawingSegment(ToothInitialList[i].Copy());
            }
                     
        }
        drawProcGraphics();
        toothChart.ResumeLayout();
        //FillMovementsAndHidden();
        Cursor = Cursors.Default;
    }

    private void drawProcGraphics() throws Exception {
        //this requires: ProcStatus, ProcCode, ToothNum, HideGraphics, Surf, and ToothRange.  All need to be raw database values.
        String[] teeth = new String[]();
        Color cLight = Color.White;
        Color cDark = Color.White;
        for (int i = 0;i < ProcList.Count;i++)
        {
            if (StringSupport.equals(ProcList[i]["HideGraphics"].ToString(), "1"))
            {
                continue;
            }
             
            if (ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).PaintType == ToothPaintingType.Extraction && (PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.C).ordinal() || PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.EC).ordinal() || PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.EO).ordinal()))
            {
                continue;
            }
             
            //prevents the red X. Missing teeth already handled.
            if (ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor == Color.FromArgb(0))
            {
                switch((ProcStat)PIn.Long(ProcList[i]["ProcStatus"].ToString()))
                {
                    case C: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][1].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][6].ItemColor;
                        break;
                    case TP: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][0].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][5].ItemColor;
                        break;
                    case EC: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][2].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][7].ItemColor;
                        break;
                    case EO: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][3].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][8].ItemColor;
                        break;
                    case R: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][4].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][9].ItemColor;
                        break;
                    case Cn: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][16].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][17].ItemColor;
                        break;
                
                }
            }
            else
            {
                cDark = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor;
                cLight = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor;
            } 
            PaintType __dummyScrutVar2 = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).PaintType;
            if (__dummyScrutVar2.equals(ToothPaintingType.BridgeDark))
            {
                if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, ProcList[i]["ToothNum"].ToString()))
                {
                    toothChart.SetPontic(ProcList[i]["ToothNum"].ToString(), cDark);
                }
                else
                {
                    toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cDark);
                } 
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.BridgeLight))
            {
                if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, ProcList[i]["ToothNum"].ToString()))
                {
                    toothChart.SetPontic(ProcList[i]["ToothNum"].ToString(), cLight);
                }
                else
                {
                    toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cLight);
                } 
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.CrownDark))
            {
                toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.CrownLight))
            {
                toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cLight);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.DentureDark))
            {
                if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "U"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 2).ToString();
                    }
                }
                else if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "L"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 18).ToString();
                    }
                }
                else
                {
                    teeth = ProcList[i]["ToothRange"].ToString().Split(new char[]{ ',' });
                }  
                for (int t = 0;t < teeth.Length;t++)
                {
                    if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, teeth[t]))
                    {
                        toothChart.SetPontic(teeth[t], cDark);
                    }
                    else
                    {
                        toothChart.SetCrown(teeth[t], cDark);
                    } 
                }
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.DentureLight))
            {
                if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "U"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 2).ToString();
                    }
                }
                else if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "L"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 18).ToString();
                    }
                }
                else
                {
                    teeth = ProcList[i]["ToothRange"].ToString().Split(new char[]{ ',' });
                }  
                for (int t = 0;t < teeth.Length;t++)
                {
                    if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, teeth[t]))
                    {
                        toothChart.SetPontic(teeth[t], cLight);
                    }
                    else
                    {
                        toothChart.SetCrown(teeth[t], cLight);
                    } 
                }
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.Extraction))
            {
                toothChart.SetBigX(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.FillingDark))
            {
                toothChart.SetSurfaceColors(ProcList[i]["ToothNum"].ToString(), ProcList[i]["Surf"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.FillingLight))
            {
                toothChart.SetSurfaceColors(ProcList[i]["ToothNum"].ToString(), ProcList[i]["Surf"].ToString(), cLight);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.Implant))
            {
                toothChart.SetImplant(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.PostBU))
            {
                toothChart.SetBU(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.RCT))
            {
                toothChart.SetRCT(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.Sealant))
            {
                toothChart.SetSealant(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.Veneer))
            {
                toothChart.SetVeneer(ProcList[i]["ToothNum"].ToString(), cLight);
            }
            else if (__dummyScrutVar2.equals(ToothPaintingType.Watch))
            {
                toothChart.SetWatch(ProcList[i]["ToothNum"].ToString(), cDark);
            }
                           
        }
    }

    private void formToothChartingBig_FormClosed(Object sender, FormClosedEventArgs e) throws Exception {
        //This helps ensure that the tooth chart wrapper is properly disposed of.
        //This step is necessary so that graphics memory does not fill up.
        Dispose();
    }

}


