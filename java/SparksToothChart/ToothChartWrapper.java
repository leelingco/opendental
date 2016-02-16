//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothNumberingNomenclature;
import SparksToothChart.__MultiToothChartDrawEventHandler;
import SparksToothChart.ToothChart2D;
import SparksToothChart.ToothChartDirectX;
import SparksToothChart.ToothChartDrawEventArgs;
import SparksToothChart.ToothChartOpenGL;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGroupType;

public class ToothChartWrapper  extends UserControl 
{

    private String[] selectedTeeth = new String[]();
    /**
    * True for hardware graphics, false for software graphics.
    */
    private boolean hardwareMode = false;
    private ToothChartOpenGL toothChartOpenGL;
    private ToothChartDirectX toothChartDirectX;
    private int preferredPixelFormatNum = new int();
    private SparksToothChart.ToothChartDirectX.DirectXDeviceFormat deviceFormat = null;
    /**
    * 
    */
    public SparksToothChart.ToothChartDrawEventHandler SegmentDrawn = null;
    private OpenDentBusiness.DrawingMode drawMode = OpenDentBusiness.DrawingMode.DirectX;
    private SparksToothChart.ToothChartData tcData;
    public ToothChartWrapper() throws Exception {
        drawMode = OpenDentBusiness.DrawingMode.Simple2D;
        tcData = new SparksToothChart.ToothChartData();
        initializeComponent();
        resetControls();
    }

    public OpenDentBusiness.DrawingMode getDrawMode() throws Exception {
        return drawMode;
    }

    public void setDrawMode(OpenDentBusiness.DrawingMode value) throws Exception {
        if (Environment.OSVersion.Platform == PlatformID.Unix)
        {
            return ;
        }
         
        //disallow changing from simpleMode if platform is Unix
        //do not break out if not changing mode.  ContrChart.InitializeOnStartup assumes this code will always run.
        if (drawMode == OpenDentBusiness.DrawingMode.DirectX && value != OpenDentBusiness.DrawingMode.DirectX)
        {
            //If switching from from DirectX to another drawing mode,
            //then we need to cleanup DirectX resources in case the
            //chart is never switched back to DirectX mode.
            toothChartDirectX.Dispose();
        }
         
        try
        {
            //Calls CleanupDirectX() and device.Dispose().
            drawMode = value;
            resetControls();
        }
        catch (Exception __dummyCatchVar0)
        {
            drawMode = OpenDentBusiness.DrawingMode.Simple2D;
            resetControls();
        }
    
    }

    /**
    * This data object holds nearly all information about what to draw.  It is normally acted on by public methods of the wrapper instead of being accessed directly.
    */
    public SparksToothChart.ToothChartData getTcData() throws Exception {
        return tcData;
    }

    public void setTcData(SparksToothChart.ToothChartData value) throws Exception {
        if (drawMode == OpenDentBusiness.DrawingMode.Simple2D)
        {
            toothChart2D.TcData = value;
        }
        else if (drawMode == OpenDentBusiness.DrawingMode.DirectX)
        {
            if (tcData != null)
            {
                tcData.cleanupDirectX();
            }
             
            //Clean up old tc data DirectX objects to free video memory.
            toothChartDirectX.TcData = value;
            toothChartDirectX.TcData.prepareForDirectX(toothChartDirectX.device);
        }
        else if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            toothChartOpenGL.TcData = value;
        }
           
        tcData = value;
    }

    //Must set last so old tcdata can be cleaned up first.
    /**
    * Valid values are 1-32 and A-Z.
    */
    public List<String> getSelectedTeeth() throws Exception {
        return tcData.getSelectedTeeth();
    }

    /**
    * 
    */
    public Color getColorBackground() throws Exception {
        return tcData.ColorBackground;
    }

    public void setColorBackground(Color value) throws Exception {
        tcData.ColorBackground = value;
        Invalidate();
    }

    /**
    * 
    */
    public void setColorText(Color value) throws Exception {
        tcData.ColorText = value;
        Invalidate();
    }

    /**
    * 
    */
    public void setColorTextHighlight(Color value) throws Exception {
        tcData.ColorTextHighlight = value;
        Invalidate();
    }

    /**
    * 
    */
    public void setColorBackHighlight(Color value) throws Exception {
        tcData.ColorBackHighlight = value;
        Invalidate();
    }

    /**
    * Set to true when using hardware rendering in OpenGL, and false otherwise. This will have no effect when in simple 2D graphics mode.
    */
    public boolean getUseHardware() throws Exception {
        return hardwareMode;
    }

    public void setUseHardware(boolean value) throws Exception {
        hardwareMode = value;
    }

    public boolean getAutoFinish() throws Exception {
        if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            return toothChartOpenGL.autoFinish;
        }
        else
        {
            return false;
        } 
    }

    public void setAutoFinish(boolean value) throws Exception {
        if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            toothChartOpenGL.autoFinish = value;
        }
         
    }

    public int getPreferredPixelFormatNumber() throws Exception {
        return preferredPixelFormatNum;
    }

    public void setPreferredPixelFormatNumber(int value) throws Exception {
        preferredPixelFormatNum = value;
    }

    public SparksToothChart.ToothChartDirectX.DirectXDeviceFormat getDeviceFormat() throws Exception {
        return deviceFormat;
    }

    public void setDeviceFormat(SparksToothChart.ToothChartDirectX.DirectXDeviceFormat value) throws Exception {
        deviceFormat = value;
    }

    public SparksToothChart.CursorTool getCursorTool() throws Exception {
        return tcData.CursorTool;
    }

    public void setCursorTool(SparksToothChart.CursorTool value) throws Exception {
        tcData.CursorTool = value;
        if (tcData.CursorTool == getCursorTool().Pointer)
        {
            this.Cursor = Cursors.Default;
        }
         
        if (tcData.CursorTool == getCursorTool().Pen)
        {
            this.Cursor = new Cursor(GetType(), "Pen.cur");
        }
         
        if (tcData.CursorTool == getCursorTool().Eraser)
        {
            this.Cursor = new Cursor(GetType(), "EraseCircle.cur");
        }
         
        if (tcData.CursorTool == getCursorTool().ColorChanger)
        {
            this.Cursor = new Cursor(GetType(), "ColorChanger.cur");
        }
         
    }

    //if(drawMode!=DrawingMode.Simple2D) {
    //	toothChartOpenGL.CursorTool=value;
    //}
    /**
    * For the freehand drawing tool.
    */
    public void setColorDrawing(Color value) throws Exception {
        tcData.ColorDrawing = value;
    }

    public boolean getPerioMode() throws Exception {
        return tcData.PerioMode;
    }

    public void setPerioMode(boolean value) throws Exception {
        if (drawMode != OpenDentBusiness.DrawingMode.DirectX && value == true)
        {
            throw new Exception("Only allowed in DirectX");
        }
         
        tcData.PerioMode = value;
        Invalidate();
    }

    /**
    * 
    */
    public void setColorBleeding(Color value) throws Exception {
        tcData.ColorBleeding = value;
    }

    /**
    * 
    */
    public void setColorSuppuration(Color value) throws Exception {
        tcData.ColorSuppuration = value;
    }

    /**
    * 
    */
    public void setColorFurcations(Color value) throws Exception {
        tcData.ColorFurcations = value;
    }

    /**
    * 
    */
    public void setColorFurcationsRed(Color value) throws Exception {
        tcData.ColorFurcationsRed = value;
    }

    /**
    * 
    */
    public void setColorGingivalMargin(Color value) throws Exception {
        tcData.ColorGingivalMargin = value;
    }

    /**
    * 
    */
    public void setColorCAL(Color value) throws Exception {
        tcData.ColorCAL = value;
    }

    /**
    * 
    */
    public void setColorMGJ(Color value) throws Exception {
        tcData.ColorMGJ = value;
    }

    /**
    * 
    */
    public void setColorProbing(Color value) throws Exception {
        tcData.ColorProbing = value;
    }

    /**
    * 
    */
    public void setColorProbingRed(Color value) throws Exception {
        tcData.ColorProbingRed = value;
    }

    /**
    * 
    */
    public void setRedLimitProbing(int value) throws Exception {
        tcData.RedLimitProbing = value;
    }

    /**
    * 
    */
    public void setRedLimitFurcations(int value) throws Exception {
        tcData.RedLimitFurcations = value;
    }

    protected void onInvalidated(InvalidateEventArgs e) throws Exception {
        super.OnInvalidated(e);
        if (drawMode == OpenDentBusiness.DrawingMode.Simple2D)
        {
            toothChart2D.Invalidate();
        }
        else if (drawMode == OpenDentBusiness.DrawingMode.DirectX)
        {
            toothChartDirectX.Invalidate();
        }
        else if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            toothChartOpenGL.Invalidate();
        }
           
    }

    //toothChartOpenGL.TaoDraw();
    private void resetControls() throws Exception {
        selectedTeeth = new String[0];
        this.Controls.Clear();
        if (drawMode == OpenDentBusiness.DrawingMode.Simple2D)
        {
            //this.Invalidate();
            toothChart2D = new ToothChart2D();
            toothChart2D.Dock = System.Windows.Forms.DockStyle.Fill;
            toothChart2D.Location = new System.Drawing.Point(0, 0);
            toothChart2D.Name = "toothChart2D";
            toothChart2D.SegmentDrawn = __MultiToothChartDrawEventHandler.combine(toothChart2D.SegmentDrawn,new SparksToothChart.ToothChartDrawEventHandler() 
              { 
                //toothChart2D.Size = new System.Drawing.Size(719,564);//unnecessary?
                public System.Void invoke(System.Object sender, ToothChartDrawEventArgs e) throws Exception {
                    toothChart_SegmentDrawn(sender, e);
                }

                public List<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception {
                    List<SparksToothChart.ToothChartDrawEventHandler> ret = new ArrayList<SparksToothChart.ToothChartDrawEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
            toothChart2D.TcData = tcData;
            toothChart2D.SuspendLayout();
            this.Controls.Add(toothChart2D);
            resetTeeth();
            toothChart2D.initializeGraphics();
            toothChart2D.ResumeLayout();
        }
        else if (drawMode == OpenDentBusiness.DrawingMode.DirectX)
        {
            if (toothChartDirectX != null)
            {
                toothChartDirectX.Dispose();
                toothChartDirectX = null;
            }
             
            toothChartDirectX = new ToothChartDirectX();
            //(hardwareMode,preferredPixelFormatNum);
            //preferredPixelFormatNum=toothChart.SelectedPixelFormatNumber;
            //toothChartDirectX.ColorText=colorText;
            toothChartDirectX.Dock = System.Windows.Forms.DockStyle.Fill;
            toothChartDirectX.Location = new System.Drawing.Point(0, 0);
            toothChartDirectX.Name = "toothChartDirectX";
            toothChartDirectX.SegmentDrawn = __MultiToothChartDrawEventHandler.combine(toothChartDirectX.SegmentDrawn,new SparksToothChart.ToothChartDrawEventHandler() 
              { 
                //toothChartDirectX.Size = new System.Drawing.Size(719,564);//unnecessary?
                public System.Void invoke(System.Object sender, ToothChartDrawEventArgs e) throws Exception {
                    toothChart_SegmentDrawn(sender, e);
                }

                public List<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception {
                    List<SparksToothChart.ToothChartDrawEventHandler> ret = new ArrayList<SparksToothChart.ToothChartDrawEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
            toothChartDirectX.TcData = tcData;
            toothChartDirectX.SuspendLayout();
            //Might help with the MDA debug error we used to get (if the option wasn't disabled in our compilers).
            this.Controls.Add(toothChartDirectX);
            resetTeeth();
            toothChartDirectX.deviceFormat = deviceFormat;
            toothChartDirectX.initializeGraphics();
            toothChartDirectX.ResumeLayout();
        }
        else //Might help with the MDA debug error we used to get (if the option wasn't disabled in our compilers).
        if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            toothChartOpenGL = new ToothChartOpenGL(hardwareMode,preferredPixelFormatNum);
            preferredPixelFormatNum = toothChartOpenGL.getSelectedPixelFormatNumber();
            //toothChartOpenGL.ColorText=colorText;
            toothChartOpenGL.Dock = System.Windows.Forms.DockStyle.Fill;
            toothChartOpenGL.Location = new System.Drawing.Point(0, 0);
            toothChartOpenGL.Name = "toothChartOpenGL";
            //toothChartOpenGL.Size = new System.Drawing.Size(719,564);//unnecessary?
            toothChartOpenGL.TcData = tcData;
            toothChartOpenGL.SegmentDrawn = __MultiToothChartDrawEventHandler.combine(toothChartOpenGL.SegmentDrawn,new SparksToothChart.ToothChartDrawEventHandler() 
              { 
                public System.Void invoke(System.Object sender, ToothChartDrawEventArgs e) throws Exception {
                    toothChart_SegmentDrawn(sender, e);
                }

                public List<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception {
                    List<SparksToothChart.ToothChartDrawEventHandler> ret = new ArrayList<SparksToothChart.ToothChartDrawEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
            toothChartOpenGL.SuspendLayout();
            this.Controls.Add(toothChartOpenGL);
            resetTeeth();
            toothChartOpenGL.initializeGraphics();
            //MakeDisplayLists();
            toothChartOpenGL.ResumeLayout();
        }
           
    }

    //This step usually happens when the DrawMode property is set on the tooth chart wrapper, by way of the ResetControls() function.
    /**
    * Not normally used unless we are just trying to make a copy of an existing directX control.
    */
    //public void InitializeDirectXGraphics() {
    //  toothChartDirectX.InitializeGraphics();
    //}
    /**
    * If ListToothGraphics is empty, then this fills it, including the complex process of loading all drawing points from local resources.  Or if not empty, then this resets all 32+20 teeth to default postitions, no restorations, etc. Primary teeth set to visible false.  Also clears selected.  Should surround with SuspendLayout / ResumeLayout.
    */
    public void resetTeeth() throws Exception {
        //selectedTeeth=new string[0];
        //this will only happen once when program first loads.  Unfortunately, there is no way to tell what the drawMode is going to be when loading the graphics from the file.  So any other initialization must happen in resetControls.
        if (tcData.ListToothGraphics.Count == 0)
        {
            tcData.ListToothGraphics.Clear();
            ToothGraphic tooth;
            for (int i = 1;i <= 32;i++)
            {
                tooth = new ToothGraphic(i.ToString());
                tooth.setVisible(true);
                tcData.ListToothGraphics.add(tooth);
                //primary
                if (!StringSupport.equals(OpenDentBusiness.Tooth.PermToPri(i.ToString()), ""))
                {
                    tooth = new ToothGraphic(OpenDentBusiness.Tooth.PermToPri(i.ToString()));
                    tooth.setVisible(false);
                    tcData.ListToothGraphics.add(tooth);
                }
                 
            }
            tooth = new ToothGraphic("implant");
            tcData.ListToothGraphics.add(tooth);
        }
        else
        {
            for (int i = 0;i < tcData.ListToothGraphics.Count;i++)
            {
                //list was already initially filled, but now user needs to reset it.
                //loop through all perm and pri teeth.
                tcData.ListToothGraphics.get___idx(i).reset();
            }
        } 
        tcData.getSelectedTeeth().Clear();
        tcData.DrawingSegmentList = new List<ToothInitial>();
        tcData.PointList = new List<Point>();
        Invalidate();
    }

    /**
    * Moves position of tooth.  Rotations first in order listed, then translations.  Tooth doesn't get moved immediately, just when painting.  All changes are cumulative and are in addition to any previous translations and rotations.
    */
    public void moveTooth(String toothID, float rotate, float tipM, float tipB, float shiftM, float shiftO, float shiftB) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).ShiftM += shiftM;
        tcData.ListToothGraphics.get___idx(toothID).ShiftO += shiftO;
        tcData.ListToothGraphics.get___idx(toothID).ShiftB += shiftB;
        tcData.ListToothGraphics.get___idx(toothID).Rotate += rotate;
        tcData.ListToothGraphics.get___idx(toothID).TipM += tipM;
        tcData.ListToothGraphics.get___idx(toothID).TipB += tipB;
        Invalidate();
    }

    /**
    * Sets the specified permanent tooth to primary. Works as follows: Sets ShowPrimaryLetter to true for the perm tooth.  Makes pri tooth visible=true.  Also repositions perm tooth by translating -Y.  Moves primary tooth slightly to M or D sometimes for better alignment.  And if 2nd primary molar, then because of the larger size, it must move all perm molars to distal.
    */
    public void setPrimary(String toothID) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        if (OpenDentBusiness.Tooth.isPrimary(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).ShiftO -= 12;
        if (ToothGraphic.isValidToothID(OpenDentBusiness.Tooth.permToPri(toothID)))
        {
            //if there's a primary tooth at this location
            tcData.ListToothGraphics.get___idx(OpenDentBusiness.Tooth.permToPri(toothID)).setVisible(true);
            //show the primary.
            tcData.ListToothGraphics.get___idx(toothID).ShowPrimaryLetter = true;
        }
         
        //first pri mand molars, shift slightly to M
        if (StringSupport.equals(toothID, "21"))
        {
            tcData.ListToothGraphics.get___idx("J").ShiftM += 0.5f;
        }
         
        if (StringSupport.equals(toothID, "28"))
        {
            tcData.ListToothGraphics.get___idx("S").ShiftM += 0.5f;
        }
         
        //second pri molars are huge, so shift distally for space
        //and move all the perm molars distally too
        if (StringSupport.equals(toothID, "4"))
        {
            tcData.ListToothGraphics.get___idx("A").ShiftM -= 0.5f;
            tcData.ListToothGraphics.get___idx("1").ShiftM -= 1;
            tcData.ListToothGraphics.get___idx("2").ShiftM -= 1;
            tcData.ListToothGraphics.get___idx("3").ShiftM -= 1;
        }
         
        if (StringSupport.equals(toothID, "13"))
        {
            tcData.ListToothGraphics.get___idx("J").ShiftM -= 0.5f;
            tcData.ListToothGraphics.get___idx("14").ShiftM -= 1;
            tcData.ListToothGraphics.get___idx("15").ShiftM -= 1;
            tcData.ListToothGraphics.get___idx("16").ShiftM -= 1;
        }
         
        if (StringSupport.equals(toothID, "20"))
        {
            tcData.ListToothGraphics.get___idx("K").ShiftM -= 1.2f;
            tcData.ListToothGraphics.get___idx("17").ShiftM -= 2.3f;
            tcData.ListToothGraphics.get___idx("18").ShiftM -= 2.3f;
            tcData.ListToothGraphics.get___idx("19").ShiftM -= 2.3f;
        }
         
        if (StringSupport.equals(toothID, "29"))
        {
            tcData.ListToothGraphics.get___idx("T").ShiftM -= 1.2f;
            tcData.ListToothGraphics.get___idx("30").ShiftM -= 2.3f;
            tcData.ListToothGraphics.get___idx("31").ShiftM -= 2.3f;
            tcData.ListToothGraphics.get___idx("32").ShiftM -= 2.3f;
        }
         
        Invalidate();
    }

    /**
    * This is used for crowns and for retainers.  Crowns will be visible on missing teeth with implants.  Crowns are visible on F and O views, unlike ponics which are only visible on F view.  If the tooth is not visible, that should be set before this call, because then, this will set the root invisible.
    */
    public void setCrown(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).IsCrown = true;
        if (!tcData.ListToothGraphics.get___idx(toothID).getVisible())
        {
            //tooth not visible, so set root invisible.
            tcData.ListToothGraphics.get___idx(toothID).setGroupVisibility(ToothGroupType.Cementum,false);
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setSurfaceColors("MODBLFIV",color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.Enamel,color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.EnamelF,color);
        this.Invalidate();
    }

    /**
    * A series of color settings will result in the last ones entered overriding earlier entries.
    */
    public void setSurfaceColors(String toothID, String surfaces, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setSurfaceColors(surfaces,color);
        Invalidate();
    }

    /**
    * Used for missing teeth.  This should always be done before setting restorations, because a pontic will cause the tooth to become visible again except for the root.  So if setMissing after a pontic, then the pontic can't show.
    */
    public void setMissing(String toothID) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setVisible(false);
        Invalidate();
    }

    /**
    * This is just the same as SetMissing, except that it also hides the number from showing.  This is used, for example, if premolars are missing, and ortho has completely closed the space.  User will not be able to select this tooth because the number is hidden.
    */
    public void setHidden(String toothID) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setVisible(false);
        tcData.ListToothGraphics.get___idx(toothID).setHideNumber(true);
        Invalidate();
    }

    /**
    * This is used for any pontic, including bridges, full dentures, and partials.  It is usually used on a tooth that has already been set invisible.  This routine cuases the tooth to show again, but the root needs to be invisible.  Then, it sets the entire crown to the specified color.  If the tooth is already visible, then it does not set the root invisible.
    */
    public void setPontic(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).IsPontic = true;
        if (!tcData.ListToothGraphics.get___idx(toothID).getVisible())
        {
            //tooth not visible, but since IsPontic changes the visibility behavior of the tooth, we need to set the root invisible.
            tcData.ListToothGraphics.get___idx(toothID).setGroupVisibility(ToothGroupType.Cementum,false);
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setSurfaceColors("MODBLFIV",color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.Enamel,color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.EnamelF,color);
        Invalidate();
    }

    /**
    * Root canals are initially not visible.  This routine sets the canals visible, changes the color to the one specified, and also sets the cementum for the tooth to be semitransparent so that the canals can be seen.  Also sets the IsRCT flag for the tooth to true.
    */
    public void setRCT(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).IsRCT = true;
        tcData.ListToothGraphics.get___idx(toothID).colorRCT = color;
        Invalidate();
    }

    /**
    * This draws a big red extraction X right on top of the tooth.  It's up to the calling application to figure out when it's appropriate to do this.  Even if the tooth has been marked invisible, there's a good chance that this will still get drawn because a tooth can be set visible again for the drawing the pontic.  So the calling application needs to figure out when it's appropriate to draw the X, and not set this otherwise.
    */
    public void setBigX(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).DrawBigX = true;
        tcData.ListToothGraphics.get___idx(toothID).colorX = color;
        Invalidate();
    }

    /**
    * Set this tooth to show a BU or post.
    */
    public void setBU(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        //TcData.ListToothGraphics[toothID].IsBU=true;
        //TcData.ListToothGraphics[toothID].colorBU=color;
        //Buildups are now just another group, so
        tcData.ListToothGraphics.get___idx(toothID).setGroupVisibility(ToothGroupType.Buildup,true);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.Buildup,color);
        Invalidate();
    }

    /**
    * Set this tooth to show an implant
    */
    public void setImplant(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).IsImplant = true;
        tcData.ListToothGraphics.get___idx(toothID).colorImplant = color;
        Invalidate();
    }

    /**
    * Set this tooth to show a sealant
    */
    public void setSealant(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).IsSealant = true;
        tcData.ListToothGraphics.get___idx(toothID).colorSealant = color;
        Invalidate();
    }

    /**
    * This will mostly only be successful on certain anterior teeth.   For others, it will just show F coloring.
    */
    public void setVeneer(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).setSurfaceColors("BFV",color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.EnamelF,color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.DF,color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.MF,color);
        tcData.ListToothGraphics.get___idx(toothID).setGroupColor(ToothGroupType.IF,color);
        Invalidate();
    }

    /**
    * Set this tooth to show a 'W' to indicate that the tooth is being watched.
    */
    public void setWatch(String toothID, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).Watch = true;
        tcData.ListToothGraphics.get___idx(toothID).colorWatch = color;
        Invalidate();
    }

    /**
    * 
    */
    public void addDrawingSegment(ToothInitial drawingSegment) throws Exception {
        boolean alreadyAdded = false;
        for (int i = 0;i < tcData.DrawingSegmentList.Count;i++)
        {
            if (StringSupport.equals(tcData.DrawingSegmentList[i].DrawingSegment, drawingSegment.DrawingSegment))
            {
                alreadyAdded = true;
                break;
            }
             
        }
        if (!alreadyAdded)
        {
            tcData.DrawingSegmentList.Add(drawingSegment);
        }
         
        Invalidate();
    }

    //toothChartOpenGL.AddDrawingSegment(drawingSegment);
    /**
    * Returns a bitmap of what is showing in the control.  Used for printing.
    */
    public Bitmap getBitmap() throws Exception {
        /*
        			Bitmap bmp=new Bitmap(this.Width,this.Height);
        			Graphics g=Graphics.FromImage(dummy);
        			PaintEventArgs e=new PaintEventArgs(g,new Rectangle(0,0,dummy.Width,dummy.Height));
        			if(simpleMode) {
        				OnPaint(e);
        				return dummy;
        			}
        			toothChartOpenGL.Render(e);
        			Bitmap result=toothChartOpenGL.ReadFrontBuffer();
        			g.Dispose();
        			return result;
        			return null;*/
        if (drawMode == OpenDentBusiness.DrawingMode.Simple2D)
        {
            return toothChart2D.getBitmap();
        }
         
        if (drawMode == OpenDentBusiness.DrawingMode.OpenGL)
        {
            return toothChartOpenGL.getBitmap();
        }
         
        if (drawMode == OpenDentBusiness.DrawingMode.DirectX)
        {
            return toothChartDirectX.getBitmap();
        }
         
        return null;
    }

    public void setToothNumberingNomenclature(ToothNumberingNomenclature nomenclature) throws Exception {
        tcData.ToothNumberingNomenclature = nomenclature;
        Invalidate();
    }

    public void setMobility(String toothID, String mobility, Color color) throws Exception {
        if (!ToothGraphic.isValidToothID(toothID))
        {
            return ;
        }
         
        tcData.ListToothGraphics.get___idx(toothID).Mobility = mobility;
        tcData.ListToothGraphics.get___idx(toothID).colorMobility = color;
        Invalidate();
    }

    public void addPerioMeasure(int intTooth, PerioSequenceType sequenceType, int mb, int b, int db, int ml, int l, int dl) throws Exception {
        PerioMeasure pm = new PerioMeasure();
        pm.MBvalue = mb;
        pm.Bvalue = b;
        pm.DBvalue = db;
        pm.MLvalue = ml;
        pm.Lvalue = l;
        pm.DLvalue = dl;
        pm.IntTooth = intTooth;
        pm.SequenceType = sequenceType;
        tcData.ListPerioMeasure.Add(pm);
    }

    public void addPerioMeasure(PerioMeasure pm) throws Exception {
        tcData.ListPerioMeasure.Add(pm);
    }

    protected void onResize(EventArgs e) throws Exception {
        super.OnResize(e);
        tcData.setSizeControl(this.Size);
        Invalidate();
        if (drawMode == OpenDentBusiness.DrawingMode.DirectX)
        {
            //Fire the resize event for the DirectX tooth chart.
            //For some reason the Resize() and Size() events don't fire on the DirectX control
            //if you create them through the designer. Perhaps there is something wrong, but this
            //works for now.
            toothChartDirectX.setSize(this.Size);
        }
         
    }

    public void setSelected(String tooth_id, boolean setValue) throws Exception {
        tcData.setSelected(tooth_id,setValue);
        Invalidate();
    }

    /**
    * 
    */
    protected void onSegmentDrawn(String drawingSegment) throws Exception {
        ToothChartDrawEventArgs tArgs = new ToothChartDrawEventArgs(drawingSegment);
        if (SegmentDrawn != null)
        {
            SegmentDrawn.invoke(this,tArgs);
        }
         
    }

    private void toothChart_SegmentDrawn(Object sender, ToothChartDrawEventArgs e) throws Exception {
        onSegmentDrawn(e.getDrawingSegement());
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
        this.toothChart2D = new SparksToothChart.ToothChart2D();
        this.SuspendLayout();
        //
        // toothChart2D
        //
        this.toothChart2D.Location = new System.Drawing.Point(0, 0);
        this.toothChart2D.Name = "toothChart2D";
        this.toothChart2D.Size = new System.Drawing.Size(410, 307);
        this.toothChart2D.TabIndex = 0;
        //
        // ToothChartWrapper
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.toothChart2D);
        this.Name = "ToothChartWrapper";
        this.Size = new System.Drawing.Size(544, 351);
        this.ResumeLayout(false);
    }

    private SparksToothChart.ToothChart2D toothChart2D;
}


