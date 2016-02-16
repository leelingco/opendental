//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:34 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.BleedingFlags;
import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioMeasures;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PerioSurf;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothNumberingNomenclature;
import SparksToothChart.LineSimple;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGraphicCollection;
import SparksToothChart.Vertex3f;

/**
* This is an object full of data about how to draw the 3D graphical teeth.  It also contains a number of helper functions that need to be shared between the different tooth charts.
*/
public class ToothChartData   
{
    /**
    * A strongly typed collection of ToothGraphics.  This includes all 32 perm and all 20 primary teeth, whether they will be drawn or not.  If a tooth is missing, it gets marked as visible false.  If it's set to primary, then the permanent tooth gets repositioned under the primary, and a primary gets set to visible true.  If a tooth is impacted, it gets repositioned.  Supernumerary graphics are not yet supported, but they might be handled by adding to this list.  "implant" is also stored as another tooth in this collection.  It is just used to store the graphics for any implant.
    */
    public ToothGraphicCollection ListToothGraphics;
    /**
    * The main color of the background behind the teeth.
    */
    public Color ColorBackground = new Color();
    /**
    * The normal color of the text for tooth numbers and letters.
    */
    public Color ColorText = new Color();
    /**
    * The color of text when a tooth number is highlighted.
    */
    public Color ColorTextHighlight = new Color();
    /**
    * The color of the background highlight rectangle around a selected tooth number.
    */
    public Color ColorBackHighlight = new Color();
    private List<String> selectedTeeth = new List<String>();
    public List<ToothInitial> DrawingSegmentList = new List<ToothInitial>();
    /**
    * This is the size of the control in screen pixels.
    */
    private Size sizeControl = new Size();
    /**
    * In tooth mm, exactly how much of the projection to show.
    */
    public SizeF SizeOriginalProjection = new SizeF(130f, 97.34f);
    /**
    * Ratio of pix/mm.  Gets recalculated every time SizeControl changes due to wrapper resize.  Multiply this ratio times a tooth mm measurement to get a pixel equivalent. If starting with a pixel value, then divide it by this ratio to get mm.
    */
    public float ScaleMmToPix = new float();
    /**
    * Whenever the control is resized, this value is set.  If the control ratio is wider than the 3D chart ratio, then this is true.  There would be extra background space on the sides.  If the ratio is taller than the 3D chart, then extra background on the top and bottom.  Default is (barely) false.
    */
    public boolean IsWide = new boolean();
    /**
    * This defines a rectangle within the control where the tooth chart is to be drawn.  It will be different than the SizeControl if the control is wider or taller than the projection ratio.  This is set every time the control is resized.  It's in pixels.
    */
    public Rectangle RectTarget = new Rectangle();
    /**
    * When the drawing feature was originally added, this was the size of the tooth chart. This number must forever be preserved and drawings scaled to account for it.
    */
    public Size SizeOriginalDrawing = new Size(410, 307);
    //NEVER CHANGE
    /**
    * An enum that indicates which kind of cursor is currently being used.
    */
    public SparksToothChart.CursorTool CursorTool = SparksToothChart.CursorTool.Pointer;
    /**
    * The color being used for freehand drawing.
    */
    public Color ColorDrawing = new Color();
    /**
    * This font object is used to measure strings.
    */
    public System.Drawing.Font Font = new System.Drawing.Font();
    /**
    * A list of points for a line currently being drawn.  Once the mouse is raised, this list gets cleared.
    */
    public List<Point> PointList = new List<Point>();
    /**
    * The size of the current drawing in pixels / the size of the original drawing.  This number is used to scale original drawing to the new size.
    */
    public float PixelScaleRatio = new float();
    public ToothNumberingNomenclature ToothNumberingNomenclature = ToothNumberingNomenclature.Universal;
    public boolean PerioMode = new boolean();
    /**
    * This very closely mirrors the organization in the db, but we don't include here mobility or skiptooth.
    */
    public List<PerioMeasure> ListPerioMeasure = new List<PerioMeasure>();
    public Color ColorBleeding = new Color();
    public Color ColorSuppuration = new Color();
    public Color ColorFurcations = new Color();
    public Color ColorFurcationsRed = new Color();
    public Color ColorGingivalMargin = new Color();
    public Color ColorCAL = new Color();
    public Color ColorMGJ = new Color();
    public Color ColorProbing = new Color();
    public Color ColorProbingRed = new Color();
    /**
    * Any probing bar that is deeper than or equal to this number will show in 'red'.  Typical value is 4 or 5.
    */
    public int RedLimitProbing = new int();
    /**
    * Any furcation grade that is greater than or equal to this number will show in 'red'.  Typical value is 2.
    */
    public int RedLimitFurcations = new int();
    public ToothChartData() throws Exception {
        ListToothGraphics = new ToothGraphicCollection();
        ColorBackground = Color.FromArgb(150, 145, 152);
        //defaults to gray
        ColorText = Color.White;
        ColorTextHighlight = Color.Red;
        ColorBackHighlight = Color.White;
        selectedTeeth = new List<String>();
        sizeControl = SizeOriginalDrawing;
        DrawingSegmentList = new List<ToothInitial>();
        CursorTool = CursorTool.Pointer;
        ColorDrawing = Color.Black;
        Font = new System.Drawing.Font(FontFamily.GenericSansSerif, 8.25f);
        PointList = new List<Point>();
        ListPerioMeasure = new List<PerioMeasure>();
    }

    /**
    * 
    */
    public SparksToothChart.ToothChartData copy() throws Exception {
        SparksToothChart.ToothChartData data = new SparksToothChart.ToothChartData();
        data.ListToothGraphics = this.ListToothGraphics.copy();
        data.ColorBackground = this.ColorBackground;
        data.ColorText = this.ColorText;
        data.ColorTextHighlight = this.ColorTextHighlight;
        data.ColorBackHighlight = this.ColorBackHighlight;
        data.DrawingSegmentList = this.DrawingSegmentList;
        //some values set when control resized.
        data.Font = this.Font;
        return data;
    }

    /**
    * Unregisters all vertex and index buffers from their current device.
    */
    public void cleanupDirectX() throws Exception {
        for (int i = 0;i < ListToothGraphics.Count;i++)
        {
            ListToothGraphics.get___idx(i).cleanupDirectX();
        }
    }

    /**
    * Registers all vertex and index buffers with the specified device.
    */
    public void prepareForDirectX(Device device) throws Exception {
        for (int i = 0;i < ListToothGraphics.Count;i++)
        {
            ListToothGraphics.get___idx(i).prepareForDirectX(device);
        }
    }

    /**
    * This gets set whenever the wrapper resizes.  It's the size of the control in screen pixels.
    */
    public Size getSizeControl() throws Exception {
        return sizeControl;
    }

    public void setSizeControl(Size value) throws Exception {
        sizeControl = value;
        //compute scaleMmToPix
        if (SizeOriginalProjection.Width / (float)sizeControl.Width < SizeOriginalProjection.Height / (float)sizeControl.Height)
        {
            //if wide, use control h.  The default settings will just barely make this false.
            IsWide = true;
            ScaleMmToPix = (float)sizeControl.Height / SizeOriginalProjection.Height;
            RectTarget.Height = sizeControl.Height;
            RectTarget.Y = 0;
            RectTarget.Width = (int)(((float)SizeOriginalDrawing.Width / SizeOriginalDrawing.Height) * RectTarget.Height);
            RectTarget.X = (sizeControl.Width - RectTarget.Width) / 2;
        }
        else
        {
            //otherwise, use control w
            IsWide = false;
            ScaleMmToPix = (float)sizeControl.Width / (float)SizeOriginalProjection.Width;
            RectTarget.Width = sizeControl.Width;
            RectTarget.X = 0;
            RectTarget.Height = (int)(((float)SizeOriginalDrawing.Height / SizeOriginalDrawing.Width) * RectTarget.Width);
            RectTarget.Y = (sizeControl.Height - RectTarget.Height) / 2;
        } 
        PixelScaleRatio = (float)RectTarget.Width / (float)SizeOriginalDrawing.Width;
    }

    /**
    * Valid values are 1-32 and A-Z.  To set which teeth are selected, use SetSelected().
    */
    public List<String> getSelectedTeeth() throws Exception {
        return selectedTeeth;
    }

    /**
    * Gets the rectangle surrounding a tooth number.  Used to draw the box and the number inside it.  Includes any mesial shifts.  Pass in the labelSize in scene mm.  The resulting rectangle has origin at lower left regardless of what quadrant it's in.
    */
    public RectangleF getNumberRecMm(String tooth_id, SizeF labelSizeF) throws Exception {
        float xPos = 0;
        float yPos = 0;
        if (ToothGraphic.isMaxillary(tooth_id))
        {
            if (Tooth.isPrimary(tooth_id))
            {
                yPos += 4.9f;
            }
            else
            {
                yPos += .8f;
            } 
        }
        else
        {
            if (Tooth.isPrimary(tooth_id))
            {
                yPos -= labelSizeF.Height + 4.9f;
            }
            else
            {
                yPos -= labelSizeF.Height + .8f;
            } 
        } 
        xPos += getTransX(tooth_id);
        //string displayNum=OpenDentBusiness.Tooth.GetToothLabelGraphic(tooth_id,ToothNumberingNomenclature);
        //float strWidthMm=g.MeasureString(displayNum,Font).Width/ScaleMmToPix;
        //xPos-=strWidthMm/2f;
        xPos -= labelSizeF.Width / 2f;
        //only use the ShiftM portion of the user translation
        if (ToothGraphic.isRight(tooth_id))
        {
            xPos += ListToothGraphics.get___idx(tooth_id).ShiftM;
        }
        else
        {
            xPos -= ListToothGraphics.get___idx(tooth_id).ShiftM;
        } 
        //float toMm=(float)WidthProjection/(float)widthControl;//mm/pix
        float toMm = 1f / ScaleMmToPix;
        RectangleF recMm = new RectangleF(xPos - 0f * toMm, yPos - 0f * toMm, labelSizeF.Width - 0f * toMm, labelSizeF.Height);
        return recMm;
    }

    //this rec has origin at LL
    /**
    * Used by 2D tooth chart to get the rectangle in pixels surrounding a tooth number.  Used to draw the box and the number inside it.
    */
    public Rectangle getNumberRecPix(String tooth_id, SizeF labelSizeF) throws Exception {
        return convertRecToPix(getNumberRecMm(tooth_id,labelSizeF));
    }

    /*
    			float xPos=GetTransXpix(tooth_id);
    			float yPos=sizeControl.Height/2f;
    			if(ToothGraphic.IsMaxillary(tooth_id)) {
    				if(Tooth.IsPrimary(tooth_id)) {
    					yPos-=25;
    				}
    				else {
    					yPos-=14;
    				}
    			}
    			else {
    				if(Tooth.IsPrimary(tooth_id)) {
    					yPos+=14;
    				}
    				else {
    					yPos+=3;
    				}
    			}
    			string displayNum =tooth_id;
    			//displayNum =OpenDentBusiness.Tooth.GetToothLabel(tooth_id);
    			float strWidth=g.MeasureString(displayNum,Font).Width;
    			xPos-=strWidth/2f;
    			RectangleF rec=new RectangleF(xPos-1,yPos-1,strWidth,12);//this rec has origin at UL
    			return rec;*/
    /**
    * Pri or perm tooth numbers are valid.  Only locations of perm teeth are stored.  This also converts mm to screen pixels.  This is currently only used in 2D mode.
    */
    public float getTransXpix(String tooth_id) throws Exception {
        int toothInt = ToothGraphic.idToInt(tooth_id);
        if (toothInt == -1)
        {
            throw new ApplicationException("Invalid tooth number: " + tooth_id);
        }
         
        //only for debugging
        float xmm = ToothGraphic.getDefaultOrthoXpos(toothInt);
        return (sizeControl.Width / 2) + (xmm * ScaleMmToPix);
    }

    //in +/- mm from center
    //( SizeOriginalProjection.Width/2f+xmm)*ScaleMmToPix;//*widthControl/WidthProjection;
    /**
    * In control coords rather than mm.  This is not really meaninful except in 2D mode.  It calculates the location of the facial view in order to draw the big red X.
    */
    public float getTransYfacialPix(String tooth_id) throws Exception {
        if (ToothGraphic.isMaxillary(tooth_id))
        {
            return sizeControl.Height / 2 - (101f * PixelScaleRatio);
        }
         
        return sizeControl.Height / 2 + (101f * PixelScaleRatio);
    }

    /**
    * In control coords rather than mm.  This is not really meaninful except in 2D mode.  It calculates the location of the occlusal surface in order to draw the bullseye.
    */
    public float getTransYocclusalPix(String tooth_id) throws Exception {
        //,int heightControl) {
        if (ToothGraphic.isMaxillary(tooth_id))
        {
            return sizeControl.Height / 2 - (48f * PixelScaleRatio);
        }
         
        return sizeControl.Height / 2 + (48f * PixelScaleRatio);
    }

    /**
    * Pri or perm tooth numbers are valid.  Only locations of perm teeth are stored.
    */
    public float getTransX(String tooth_id) throws Exception {
        int toothInt = ToothGraphic.idToInt(tooth_id);
        if (toothInt == -1)
        {
            throw new ApplicationException("Invalid tooth number: " + tooth_id);
        }
         
        return ToothGraphic.getDefaultOrthoXpos(toothInt);
    }

    //only for debugging
    public float getTransYfacial(String tooth_id) throws Exception {
        float basic = 29f;
        if (StringSupport.equals(tooth_id, "6") || StringSupport.equals(tooth_id, "11"))
        {
            return basic + 1f;
        }
         
        if (StringSupport.equals(tooth_id, "7") || StringSupport.equals(tooth_id, "10"))
        {
            return basic + 1f;
        }
        else if (StringSupport.equals(tooth_id, "8") || StringSupport.equals(tooth_id, "9"))
        {
            return basic + 2f;
        }
        else if (StringSupport.equals(tooth_id, "22") || StringSupport.equals(tooth_id, "27"))
        {
            return -basic - 2f;
        }
        else if (StringSupport.equals(tooth_id, "23") || StringSupport.equals(tooth_id, "24") || StringSupport.equals(tooth_id, "25") || StringSupport.equals(tooth_id, "26"))
        {
            return -basic - 2f;
        }
        else if (ToothGraphic.isMaxillary(tooth_id))
        {
            return basic;
        }
             
        return -basic;
    }

    public float getTransYocclusal(String tooth_id) throws Exception {
        if (ToothGraphic.isMaxillary(tooth_id))
        {
            return 13f;
        }
         
        return -13f;
    }

    /**
    * This also adjusts the result to account for a control that is not the same proportion as the original.  Result could be outside the projection area.
    */
    public PointF pointPixToMm(Point pixPoint) throws Exception {
        /*
        			float toMmRatio=(float)WidthProjection/(float)widthControl;//mm/pix
        			float mmX=(((float)pixPoint.X)*toMmRatio)-((float)WidthProjection)/2f;
        			float idealHeightProjection=(float)WidthProjection*(float)SizeOriginalDrawing.Height/(float)SizeOriginalDrawing.Width;
        			float actualHeightProjection=(float)WidthProjection*(float)heightControl/(float)widthControl;
        			float mmY=(idealHeightProjection)/2f-(((float)pixPoint.Y)*toMmRatio);
        			return new PointF(mmX,mmY);*/
        float toMmRatio = 1f / ScaleMmToPix;
        float mmX = (((float)(pixPoint.X - RectTarget.X)) * toMmRatio) - ((float)SizeOriginalProjection.Width) / 2f;
        //float idealHeightProjection=(float)WidthProjection*(float)SizeOriginalDrawing.Height/(float)SizeOriginalDrawing.Width;
        //float actualHeightProjection=(float)WidthProjection*(float)heightControl/(float)widthControl;
        float mmY = (SizeOriginalProjection.Height) / 2f - (((float)(pixPoint.Y - RectTarget.Y)) * toMmRatio);
        return new PointF(mmX, mmY);
    }

    /**
    * Takes an original db point as originally entered in unscaled control coordinates, and returns coordinates in scene mm's.
    */
    public PointF pointDrawingPixToMm(Point pixPoint) throws Exception {
        float toMmRatio = 1f / ScaleMmToPix;
        float mmX = (((float)pixPoint.X * PixelScaleRatio) * toMmRatio) - ((float)SizeOriginalProjection.Width) / 2f;
        float mmY = ((float)SizeOriginalProjection.Height) / 2f - (((float)pixPoint.Y * PixelScaleRatio) * toMmRatio);
        return new PointF(mmX, mmY);
    }

    /*
    		///<summary>The recPix has origin at UL.  The result has origin at LL.</summary>
    		private RectangleF ConvertRecToMm(RectangleF recPix) {
    			float w=recPix.Width/ScaleMmToPix;
    			float h=recPix.Height/ScaleMmToPix;
    			float x=(recPix.X-(float)(sizeControl.Width/2))/ScaleMmToPix;
    			float y=(recPix.Bottom-(float)(sizeControl.Height/2))/ScaleMmToPix;
    			return new RectangleF(x,y,w,h);			
    		}*/
    /**
    * The recMm has origin at LL.  The result has origin at UL and is in control coords.
    */
    private Rectangle convertRecToPix(RectangleF recMm) throws Exception {
        int w = (int)(recMm.Width * ScaleMmToPix);
        int h = (int)(recMm.Height * ScaleMmToPix);
        int x = (int)(recMm.X * ScaleMmToPix + sizeControl.Width / 2);
        int y = (int)((sizeControl.Height / 2 - recMm.Y * ScaleMmToPix) - h);
        return new Rectangle(x, y, w, h);
    }

    /*
    		///<summary>Always returns a number between 1 and 32.  This isn't perfect, since it only operates on perm teeth, and assumes that any primary tooth will be at the same x pos as its perm tooth.</summary>
    		public int GetToothAtPoint(int x,int y) {
    			//This version was originally used in 2D chart
    			
    			float closestDelta=(float)(Width*2);//start it off really big
    			int closestTooth=1;
    			float toothPos=0;
    			float delta=0;
    			//float xPos=(float)((float)(x-Width/2)*WidthProjection/(float)Width);//in mm instead of screen coordinates
    			if(y<Height/2) {//max
    				for(int i=1;i<=16;i++) {
    					if(ListToothGraphics[i.ToString()].HideNumber) {
    						continue;
    					}
    					toothPos=GetTransX(i.ToString());//ToothGraphic.GetDefaultOrthoXpos(i);
    					if(x>toothPos) {//xPos>toothPos) {
    						delta=x-toothPos;
    					}
    					else {
    						delta=toothPos-x;
    					}
    					if(delta<closestDelta) {
    						closestDelta=delta;
    						closestTooth=i;
    					}
    				}
    				return closestTooth;
    			}
    			else {//mand
    				for(int i=17;i<=32;i++) {
    					if(ListToothGraphics[i.ToString()].HideNumber) {
    						continue;
    					}
    					toothPos=GetTransX(i.ToString());//ToothGraphic.GetDefaultOrthoXpos(i);//in mm.
    					if(x>toothPos) {
    						delta=x-toothPos;
    					}
    					else {
    						delta=toothPos-x;
    					}
    					if(delta<closestDelta) {
    						closestDelta=delta;
    						closestTooth=i;
    					}
    				}
    				return closestTooth;
    			}
    			return 1;
    		}*/
    /**
    * Input is pixel coordinates of control.  Always returns a string, 1 through 32 or A through T.  Primary letters are only returned if that tooth is set as primary.
    */
    public String getToothAtPoint(Point point) throws Exception {
        //This version was originally in OpenGL.
        float closestDelta = (float)(SizeOriginalProjection.Width * 2);
        //start it off really big
        String closestTooth = "1";
        float toothPos = 0;
        float delta = 0;
        //convert x and y to mm.  Use those measurements to match the closest tooth.
        //float xPos=(float)((float)(x-Width/2)*WidthProjection/(float)Width);//in mm instead of screen coordinates
        float xPos = pointPixToMm(point).X;
        float yPos = pointPixToMm(point).Y;
        String perm_id = new String();
        boolean isPriArea = new boolean();
        //this point is where a primary letter might sometimes show
        boolean priShowing = new boolean();
        boolean permShowing = new boolean();
        boolean usePri = new boolean();
        //otherwise, use perm
        String tooth_id = new String();
        if (yPos > 0)
        {
            for (int i = 1;i <= 16;i++)
            {
                //maxillary
                perm_id = i.ToString();
                if (i >= 4 && i <= 13 && !isPermArea(yPos))
                {
                    isPriArea = true;
                }
                else
                {
                    isPriArea = false;
                } 
                //Determine which numbers are showing
                priShowing = false;
                if (ListToothGraphics.get___idx(perm_id).ShowPrimaryLetter && !ListToothGraphics.get___idx(Tooth.permToPri(perm_id)).getHideNumber())
                {
                    priShowing = true;
                }
                 
                permShowing = true;
                if (ListToothGraphics.get___idx(perm_id).getHideNumber())
                {
                    permShowing = false;
                }
                 
                if (!priShowing && !permShowing)
                {
                    continue;
                }
                 
                //if neither # showing
                if (priShowing)
                {
                    if (permShowing)
                    {
                        //both showing
                        if (isPriArea)
                        {
                            usePri = true;
                        }
                        else
                        {
                            usePri = false;
                        } 
                    }
                    else
                    {
                        usePri = true;
                    } 
                }
                else
                {
                    //only perm showing
                    usePri = false;
                } 
                if (usePri)
                {
                    tooth_id = Tooth.permToPri(perm_id);
                }
                else
                {
                    tooth_id = perm_id;
                } 
                toothPos = ToothGraphic.getDefaultOrthoXpos(i);
                if (ToothGraphic.isRight(perm_id))
                {
                    toothPos += (int)ListToothGraphics.get___idx(tooth_id).ShiftM;
                }
                else
                {
                    toothPos -= (int)ListToothGraphics.get___idx(tooth_id).ShiftM;
                } 
                if (xPos > toothPos)
                {
                    delta = xPos - toothPos;
                }
                else
                {
                    delta = toothPos - xPos;
                } 
                if (delta < closestDelta)
                {
                    closestDelta = delta;
                    closestTooth = tooth_id;
                }
                 
            }
            return closestTooth;
        }
        else
        {
            for (int i = 17;i <= 32;i++)
            {
                //mandibular
                perm_id = i.ToString();
                if (i >= 20 && i <= 29 && !isPermArea(yPos))
                {
                    isPriArea = true;
                }
                else
                {
                    isPriArea = false;
                } 
                //Determine which numbers are showing
                priShowing = false;
                if (ListToothGraphics.get___idx(perm_id).ShowPrimaryLetter && !ListToothGraphics.get___idx(Tooth.permToPri(perm_id)).getHideNumber())
                {
                    priShowing = true;
                }
                 
                permShowing = true;
                if (ListToothGraphics.get___idx(perm_id).getHideNumber())
                {
                    permShowing = false;
                }
                 
                if (!priShowing && !permShowing)
                {
                    continue;
                }
                 
                //if neither # showing
                if (priShowing)
                {
                    if (permShowing)
                    {
                        //both showing
                        if (isPriArea)
                        {
                            usePri = true;
                        }
                        else
                        {
                            usePri = false;
                        } 
                    }
                    else
                    {
                        usePri = true;
                    } 
                }
                else
                {
                    //only perm showing
                    usePri = false;
                } 
                if (usePri)
                {
                    tooth_id = Tooth.permToPri(perm_id);
                }
                else
                {
                    tooth_id = perm_id;
                } 
                toothPos = ToothGraphic.getDefaultOrthoXpos(i);
                if (ToothGraphic.isRight(perm_id))
                {
                    toothPos += (int)ListToothGraphics.get___idx(tooth_id).ShiftM;
                }
                else
                {
                    toothPos -= (int)ListToothGraphics.get___idx(tooth_id).ShiftM;
                } 
                if (xPos > toothPos)
                {
                    delta = xPos - toothPos;
                }
                else
                {
                    delta = toothPos - xPos;
                } 
                if (delta < closestDelta)
                {
                    closestDelta = delta;
                    closestTooth = tooth_id;
                }
                 
            }
            return closestTooth;
        } 
    }

    /**
    * Input is y position in mm scene coordinates.  Will return true if it's close to the horizontal midline, in the area where the perm tooth numbers are.
    */
    public boolean isPermArea(float yPos) throws Exception {
        if (yPos > 5.1f || yPos < -4.4f)
        {
            return false;
        }
         
        return true;
    }

    /**
    * When this is used from within a toothchart in response to mouse activity, it is typically followed by explicit drawing instructions that efficiently shows the user which teeth are selected.  When this is used from the wrapper, it's typically followed by an Invalidate().
    */
    public void setSelected(String tooth_id, boolean setValue) throws Exception {
        if (setValue)
        {
            if (!getSelectedTeeth().Contains(tooth_id))
            {
                getSelectedTeeth().Add(tooth_id);
            }
             
        }
        else
        {
            //DrawNumber(tooth_id,true,false);
            if (getSelectedTeeth().Contains(tooth_id))
            {
                getSelectedTeeth().Remove(tooth_id);
            }
             
        } 
    }

    //DrawNumber(tooth_id,false,false);
    //RectangleF recMm=TcData.GetNumberRecMm(tooth_id,);
    //Rectangle rec=TcData.ConvertRecToPix(recMm);
    /**
    * When teeth are selected using a sweeping mouse motion, it might be too fast for some intermediate teeth to be included. This method takes the first and last tooth_id's and returns a list including the last one as well as any intermediate teeth.
    */
    public List<String> getAffectedTeeth(String startingId, String endingId, float yPos) throws Exception {
        List<String> affectedTeeth = new List<String>();
        affectedTeeth.Add(endingId);
        int startingOrdinal = Tooth.toOrdinal(startingId);
        if (Tooth.isPrimary(startingId))
        {
            startingOrdinal = Tooth.toOrdinal(Tooth.priToPerm(startingId));
        }
         
        int endingOrdinal = Tooth.toOrdinal(endingId);
        if (Tooth.isPrimary(endingId))
        {
            endingOrdinal = Tooth.toOrdinal(Tooth.priToPerm(endingId));
        }
         
        if (Math.Abs(startingOrdinal - endingOrdinal) <= 1)
        {
            return affectedTeeth;
        }
         
        //if they are not separated by more than one.
        if (Tooth.isMaxillary(startingId) != Tooth.isMaxillary(endingId))
        {
            return affectedTeeth;
        }
         
        //if they are not in the same arch
        boolean isInPermArea = isPermArea(yPos);
        //close to the horizontal midline
        String permId = new String();
        String priId = new String();
        //will be blank if invalid
        if (endingOrdinal < startingOrdinal)
        {
            for (int i = endingOrdinal + 1;i < startingOrdinal;i++)
            {
                //we're only going after the teeth in between
                permId = Tooth.fromOrdinal(i);
                priId = Tooth.permToPri(permId);
                //the only situation where the following tests will fail is if set to primary, mouse is in the perm area, and perm is hidden.
                //it's possible to have a pri number here
                //and a primary letter is showing
                //and the mouse is in the primary area
                if (!StringSupport.equals(priId, "") && ListToothGraphics.get___idx(permId).ShowPrimaryLetter && !isInPermArea && !ListToothGraphics.get___idx(priId).getHideNumber())
                {
                    //and the primary tooth number is not hidden
                    affectedTeeth.Add(priId);
                }
                else if (!ListToothGraphics.get___idx(permId).getHideNumber())
                {
                    affectedTeeth.Add(permId);
                }
                  
            }
        }
        else
        {
            for (int i = startingOrdinal + 1;i < endingOrdinal;i++)
            {
                permId = Tooth.fromOrdinal(i);
                priId = Tooth.permToPri(permId);
                if (!StringSupport.equals(priId, "") && ListToothGraphics.get___idx(permId).ShowPrimaryLetter && !isInPermArea && !ListToothGraphics.get___idx(priId).getHideNumber())
                {
                    affectedTeeth.Add(priId);
                }
                else if (!ListToothGraphics.get___idx(permId).getHideNumber())
                {
                    affectedTeeth.Add(permId);
                }
                  
            }
        } 
        return affectedTeeth;
    }

    /**
    * Use this to test a site (there are 3 sites per tooth face).  If it returns 0, do not draw a furctation.  If it returns a number 1-3, then use GetFurcationPos to know where to put the triangle or V.
    */
    public int getFurcationValue(int intTooth, PerioSurf surf) throws Exception {
        for (int i = 0;i < ListPerioMeasure.Count;i++)
        {
            if (ListPerioMeasure[i].IntTooth != intTooth)
            {
                continue;
            }
             
            if (ListPerioMeasure[i].SequenceType != PerioSequenceType.Furcation)
            {
                continue;
            }
             
            int meas = 0;
            if (surf == PerioSurf.MB)
            {
                meas = ListPerioMeasure[i].MBvalue;
            }
             
            if (surf == PerioSurf.B)
            {
                meas = ListPerioMeasure[i].Bvalue;
            }
             
            if (surf == PerioSurf.DB)
            {
                meas = ListPerioMeasure[i].DBvalue;
            }
             
            if (surf == PerioSurf.ML)
            {
                meas = ListPerioMeasure[i].MLvalue;
            }
             
            if (surf == PerioSurf.L)
            {
                meas = ListPerioMeasure[i].Lvalue;
            }
             
            if (surf == PerioSurf.DL)
            {
                meas = ListPerioMeasure[i].DLvalue;
            }
             
            if (meas == -1)
            {
                return 0;
            }
             
            return meas;
        }
        return 0;
    }

    /**
    * Use GetFurcationValue first.  Then, this method returns the position in mm of the tip of the triangle or V relative to the center of the tooth.
    */
    public PointF getFurcationPos(int intTooth, PerioSurf surf) throws Exception {
        float ysign = 1;
        if (Tooth.isMaxillary(intTooth))
        {
            ysign = 1;
        }
        else
        {
            //mand
            ysign = -1;
        } 
        float xshift = getXShiftPerioSite(intTooth,surf);
        return new PointF(xshift, ysign * 9.5f);
    }

    /**
    * Draws the short vertical lines that represent probing depths.  Use this on each site (3 per tooth face).  The z component will be 0.  The coordinates will be relative to the center of the tooth.  The line will always only have one segment.  It will return null if no line to draw at this site.  The color of the line will be pulled from a different method.
    */
    public LineSimple getProbingLine(int intTooth, PerioSurf surf, RefSupport<Color> color) throws Exception {
        color.setValue(ColorProbing);
        if (!ListToothGraphics[intTooth.ToString()].Visible && !ListToothGraphics[intTooth.ToString()].IsImplant)
        {
            return null;
        }
         
        float xshift = getXShiftPerioSite(intTooth,surf);
        int gm = 0;
        int pd = 0;
        for (int i = 0;i < ListPerioMeasure.Count;i++)
        {
            //line length
            if (ListPerioMeasure[i].IntTooth != intTooth)
            {
                continue;
            }
             
            if (ListPerioMeasure[i].SequenceType == PerioSequenceType.Probing)
            {
                switch(surf)
                {
                    case MB: 
                        pd = ListPerioMeasure[i].MBvalue;
                        break;
                    case B: 
                        pd = ListPerioMeasure[i].Bvalue;
                        break;
                    case DB: 
                        pd = ListPerioMeasure[i].DBvalue;
                        break;
                    case ML: 
                        pd = ListPerioMeasure[i].MLvalue;
                        break;
                    case L: 
                        pd = ListPerioMeasure[i].Lvalue;
                        break;
                    case DL: 
                        pd = ListPerioMeasure[i].DLvalue;
                        break;
                
                }
            }
             
            if (ListPerioMeasure[i].SequenceType == PerioSequenceType.GingMargin)
            {
                switch(surf)
                {
                    case MB: 
                        //Examples: 0, -1(null), 5, or 105(hyperplasia).  But the null is not even being considered.  So adjusting 100+ vals to -x would work.
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].MBvalue);
                        break;
                    case B: 
                        //Converts the above examples to 0, 0, 5, and -5.
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].Bvalue);
                        break;
                    case DB: 
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].DBvalue);
                        break;
                    case ML: 
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].MLvalue);
                        break;
                    case L: 
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].Lvalue);
                        break;
                    case DL: 
                        gm = PerioMeasures.AdjustGMVal(ListPerioMeasure[i].DLvalue);
                        break;
                
                }
            }
             
        }
        if (pd == 0 || pd == -1)
        {
            return null;
        }
         
        if (pd >= RedLimitProbing)
        {
            color.setValue(ColorProbingRed);
        }
         
        //CAL shouldn't be less than 0, so we need to draw probing lines down to zero if CAL is negative for some reason. (Or maybe we just let this happen so that dentists know something is wrong.
        if (Tooth.isMaxillary(intTooth))
        {
            return new LineSimple(xshift, gm, 0, xshift, gm + pd, 0);
        }
        else
        {
            return new LineSimple(xshift, -gm, 0, xshift, -(gm + pd), 0);
        } 
    }

    //mand
    /**
    * Relative to the center of the tooth. The sign is based on area of the mouth.  The magnitude is based on tooth width.  This will be used for the probing bars and horizontal lines.  Probably also for furcations.
    */
    private float getXShiftPerioSite(int intTooth, PerioSurf surf) throws Exception {
        if (surf == PerioSurf.B || surf == PerioSurf.L)
        {
            return 0;
        }
         
        float xdirect = 1;
        if (Tooth.isMaxillary(intTooth))
        {
            if (surf == PerioSurf.MB || surf == PerioSurf.ML)
            {
                if (ToothGraphic.IsRight(intTooth.ToString()))
                {
                    //UR quadrant
                    xdirect = 1;
                }
                else
                {
                    //UL
                    xdirect = -1;
                } 
            }
            else if (surf == PerioSurf.DB || surf == PerioSurf.DL)
            {
                if (ToothGraphic.IsRight(intTooth.ToString()))
                {
                    //UR quadrant
                    xdirect = -1;
                }
                else
                {
                    //UL
                    xdirect = 1;
                } 
            }
              
        }
        else
        {
            //mand
            if (surf == PerioSurf.MB || surf == PerioSurf.ML)
            {
                if (ToothGraphic.IsRight(intTooth.ToString()))
                {
                    //LR quadrant
                    xdirect = 1;
                }
                else
                {
                    //LL
                    xdirect = -1;
                } 
            }
            else if (surf == PerioSurf.DB || surf == PerioSurf.DL)
            {
                if (ToothGraphic.IsRight(intTooth.ToString()))
                {
                    //LR quadrant
                    xdirect = -1;
                }
                else
                {
                    //LL
                    xdirect = 1;
                } 
            }
              
        } 
        float toothW = ToothGraphic.getWidth(intTooth);
        float magnitude = new float();
        switch(intTooth)
        {
            default: 
                magnitude = .28f;
                break;
            case 1: 
            case 2: 
            case 15: 
            case 16: 
                magnitude = .32f;
                break;
            case 17: 
            case 32: 
            case 18: 
            case 31: 
                magnitude = .35f;
                break;
            case 3: 
            case 14: 
                magnitude = .38f;
                break;
            case 19: 
            case 30: 
                magnitude = .37f;
                break;
        
        }
        if (ListToothGraphics[intTooth.ToString()].IsImplant)
        {
            return 2f * xdirect;
        }
         
        return magnitude * toothW * xdirect;
    }

    /**
    * This gets the entire set of lines for one perio row for one sequence type.  The allowed types are GM, MGJ, and CAL.  Each LineSimple is a series of connected lines.  But the result could have interruptions, so we return a list, each item in the list being continuous.  There may be zero items in the list.  Each line in the list is guaranteed to have at least 2 points in it.
    */
    public List<LineSimple> getHorizontalLines(PerioSequenceType sequenceType, boolean isMaxillary, boolean isBuccal) throws Exception {
        List<LineSimple> retVal = new List<LineSimple>();
        int startTooth = 1;
        int stopTooth = 17;
        //doesn't perform a loop for 17.
        if (!isMaxillary)
        {
            startTooth = 32;
            //We still go Left to Right, even on mand.
            stopTooth = 16;
        }
         
        LineSimple line = new LineSimple();
        Vertex3f vertex;
        int val1 = -1;
        int val2 = -1;
        int val3 = -1;
        int t = startTooth;
        PerioSurf surf1 = PerioSurf.None;
        PerioSurf surf2 = PerioSurf.None;
        PerioSurf surf3 = PerioSurf.None;
        while (t != stopTooth)
        {
            if (!ListToothGraphics[t.ToString()].Visible && !ListToothGraphics[t.ToString()].IsImplant)
            {
                //stop any existing line.
                if (line.Vertices.Count == 1)
                {
                    //if there is already one point, then clear it, because a line can't have one point.
                    line.Vertices.Clear();
                }
                 
                if (line.Vertices.Count > 1)
                {
                    //if 2 or more points in the line, then add the line to the result.
                    retVal.Add(line);
                    line = new LineSimple();
                }
                 
                //and initialize a new line for future points.
                //increment to next tooth
                if (isMaxillary)
                {
                    t++;
                }
                else
                {
                    t--;
                } 
                continue;
            }
             
            //We are considering 3 points per tooth.  Reinitialize for the new tooth.
            val1 = -1;
            val2 = -1;
            val3 = -1;
            surf1 = PerioSurf.None;
            surf2 = PerioSurf.None;
            surf3 = PerioSurf.None;
            for (int i = 0;i < ListPerioMeasure.Count;i++)
            {
                if (ListPerioMeasure[i].IntTooth != t)
                {
                    continue;
                }
                 
                if (ListPerioMeasure[i].SequenceType != sequenceType)
                {
                    continue;
                }
                 
                //so we are now on the specific PerioMeasure for this sequence and tooth.  It contains 6 values, and we will use 3.
                PerioMeasure pmGM = null;
                //We need to draw MGJ as dist from GM, not CEJ
                if (sequenceType == PerioSequenceType.MGJ)
                {
                    for (int m = 0;m < ListPerioMeasure.Count;m++)
                    {
                        //we only care about this if we are trying to calculate MGJ
                        if (ListPerioMeasure[m].IntTooth == t && ListPerioMeasure[m].SequenceType == PerioSequenceType.GingMargin)
                        {
                            pmGM = ListPerioMeasure[m];
                            break;
                        }
                         
                    }
                }
                 
                //get the GM for this same tooth.
                if (isBuccal)
                {
                    if (ToothGraphic.IsRight(t.ToString()))
                    {
                        val1 = ListPerioMeasure[i].DBvalue;
                        val2 = ListPerioMeasure[i].Bvalue;
                        val3 = ListPerioMeasure[i].MBvalue;
                        if (sequenceType == PerioSequenceType.MGJ && pmGM != null)
                        {
                            if (pmGM.DBvalue != -1)
                            {
                                val1 += PerioMeasures.adjustGMVal(pmGM.DBvalue);
                            }
                             
                            if (pmGM.Bvalue != -1)
                            {
                                val2 += PerioMeasures.adjustGMVal(pmGM.Bvalue);
                            }
                             
                            if (pmGM.MBvalue != -1)
                            {
                                val3 += PerioMeasures.adjustGMVal(pmGM.MBvalue);
                            }
                             
                        }
                         
                        surf1 = PerioSurf.DB;
                        surf2 = PerioSurf.B;
                        surf3 = PerioSurf.MB;
                    }
                    else
                    {
                        //for UL and LL
                        val1 = ListPerioMeasure[i].MBvalue;
                        val2 = ListPerioMeasure[i].Bvalue;
                        val3 = ListPerioMeasure[i].DBvalue;
                        if (sequenceType == PerioSequenceType.MGJ && pmGM != null)
                        {
                            if (pmGM.MBvalue != -1)
                            {
                                val1 += PerioMeasures.adjustGMVal(pmGM.MBvalue);
                            }
                             
                            if (pmGM.Bvalue != -1)
                            {
                                val2 += PerioMeasures.adjustGMVal(pmGM.Bvalue);
                            }
                             
                            if (pmGM.DBvalue != -1)
                            {
                                val3 += PerioMeasures.adjustGMVal(pmGM.DBvalue);
                            }
                             
                        }
                         
                        surf1 = PerioSurf.MB;
                        surf2 = PerioSurf.B;
                        surf3 = PerioSurf.DB;
                    } 
                }
                else
                {
                    //lingual
                    if (ToothGraphic.IsRight(t.ToString()))
                    {
                        val1 = ListPerioMeasure[i].DLvalue;
                        val2 = ListPerioMeasure[i].Lvalue;
                        val3 = ListPerioMeasure[i].MLvalue;
                        if (sequenceType == PerioSequenceType.MGJ && pmGM != null)
                        {
                            if (pmGM.DLvalue != -1)
                            {
                                val1 += PerioMeasures.adjustGMVal(pmGM.DLvalue);
                            }
                             
                            if (pmGM.Lvalue != -1)
                            {
                                val2 += PerioMeasures.adjustGMVal(pmGM.Lvalue);
                            }
                             
                            if (pmGM.MLvalue != -1)
                            {
                                val3 += PerioMeasures.adjustGMVal(pmGM.MLvalue);
                            }
                             
                        }
                         
                        surf1 = PerioSurf.DL;
                        surf2 = PerioSurf.L;
                        surf3 = PerioSurf.ML;
                    }
                    else
                    {
                        //for UL and LL
                        val1 = ListPerioMeasure[i].MLvalue;
                        val2 = ListPerioMeasure[i].Lvalue;
                        val3 = ListPerioMeasure[i].DLvalue;
                        if (sequenceType == PerioSequenceType.MGJ && pmGM != null)
                        {
                            if (pmGM.MLvalue != -1)
                            {
                                val1 += PerioMeasures.adjustGMVal(pmGM.MLvalue);
                            }
                             
                            if (pmGM.Lvalue != -1)
                            {
                                val2 += PerioMeasures.adjustGMVal(pmGM.Lvalue);
                            }
                             
                            if (pmGM.DLvalue != -1)
                            {
                                val3 += PerioMeasures.adjustGMVal(pmGM.DLvalue);
                            }
                             
                        }
                         
                        surf1 = PerioSurf.ML;
                        surf2 = PerioSurf.L;
                        surf3 = PerioSurf.DL;
                    } 
                } 
            }
            //We have now filled our 3 points with values and need to evaluate those values.
            //Any or all of the values may still be -1.
            if (val1 == -1)
            {
                //we won't add a point to this line
                if (line.Vertices.Count == 1)
                {
                    //if there is already one point, then clear it, because a line can't have one point.
                    line.Vertices.Clear();
                }
                 
                if (line.Vertices.Count > 1)
                {
                    //if 2 or more points in the line, then add the line to the result.
                    retVal.Add(line);
                    line = new LineSimple();
                }
                 
            }
            else
            {
                //and initialize a new line for future points.
                //just add a point to the current line.
                vertex = new Vertex3f();
                vertex.Z = 0;
                //we don't use z
                if (isMaxillary)
                {
                    //this is safe to run on all sequence types because -1 has already been handled and because other types wouldn't have values > 100.
                    //Also safe to process on the vals that are MGJ, calculated above, because if they are ever negative,
                    //it would be an obvious entry error, and the MGJ line would just harmlessly disappear for -1 vals.
                    vertex.Y = PerioMeasures.adjustGMVal(val1);
                }
                else
                {
                    vertex.Y = -PerioMeasures.adjustGMVal(val1);
                } 
                vertex.X = getXShiftPerioSite(t,surf1) + ToothGraphic.getDefaultOrthoXpos(t);
                line.Vertices.Add(vertex);
            } 
            //val2--------------------------
            if (val2 == -1)
            {
                if (line.Vertices.Count == 1)
                {
                    line.Vertices.Clear();
                }
                 
                if (line.Vertices.Count > 1)
                {
                    retVal.Add(line);
                    line = new LineSimple();
                }
                 
            }
            else
            {
                vertex = new Vertex3f();
                vertex.Z = 0;
                if (isMaxillary)
                {
                    vertex.Y = PerioMeasures.adjustGMVal(val2);
                }
                else
                {
                    vertex.Y = -PerioMeasures.adjustGMVal(val2);
                } 
                vertex.X = getXShiftPerioSite(t,surf2) + ToothGraphic.getDefaultOrthoXpos(t);
                line.Vertices.Add(vertex);
            } 
            //val3-------------------------
            if (val3 == -1)
            {
                if (line.Vertices.Count == 1)
                {
                    line.Vertices.Clear();
                }
                 
                if (line.Vertices.Count > 1)
                {
                    retVal.Add(line);
                    line = new LineSimple();
                }
                 
            }
            else
            {
                vertex = new Vertex3f();
                vertex.Z = 0;
                if (isMaxillary)
                {
                    vertex.Y = PerioMeasures.adjustGMVal(val3);
                }
                else
                {
                    vertex.Y = -PerioMeasures.adjustGMVal(val3);
                } 
                vertex.X = getXShiftPerioSite(t,surf3) + ToothGraphic.getDefaultOrthoXpos(t);
                line.Vertices.Add(vertex);
            } 
            //increment to next tooth
            if (isMaxillary)
            {
                t++;
            }
            else
            {
                t--;
            } 
        }
        if (line.Vertices.Count > 1)
        {
            retVal.Add(line);
        }
         
        return retVal;
    }

    /**
    * For a given tooth and surface, gets a point at which to draw bleeding or suppuration droplet.  Use this on each site (3 per tooth face). The coordinates will be relative to the center of the tooth.  It will return 0,0 if no droplet to draw at this site.  If isBleeding is false, then it gets suppuration.
    */
    public PointF getBleedingOrSuppuration(int intTooth, PerioSurf surf, boolean isBleeding) throws Exception {
        if (!ListToothGraphics[intTooth.ToString()].Visible && !ListToothGraphics[intTooth.ToString()].IsImplant)
        {
            return new PointF(0, 0);
        }
         
        float xshift = getXShiftPerioSite(intTooth,surf);
        float yshift = -1.5f;
        //max
        if (!Tooth.isMaxillary(intTooth))
        {
            yshift = 1.5f;
        }
         
        int siteVal = -1;
        for (int i = 0;i < ListPerioMeasure.Count;i++)
        {
            if (ListPerioMeasure[i].IntTooth != intTooth)
            {
                continue;
            }
             
            if (ListPerioMeasure[i].SequenceType != PerioSequenceType.Bleeding)
            {
                continue;
            }
             
            switch(surf)
            {
                case MB: 
                    siteVal = ListPerioMeasure[i].MBvalue;
                    break;
                case B: 
                    siteVal = ListPerioMeasure[i].Bvalue;
                    break;
                case DB: 
                    siteVal = ListPerioMeasure[i].DBvalue;
                    break;
                case ML: 
                    siteVal = ListPerioMeasure[i].MLvalue;
                    break;
                case L: 
                    siteVal = ListPerioMeasure[i].Lvalue;
                    break;
                case DL: 
                    siteVal = ListPerioMeasure[i].DLvalue;
                    break;
            
            }
            break;
        }
        if (siteVal == -1 || siteVal == 0)
        {
            return new PointF(0, 0);
        }
         
        if (isBleeding)
        {
            if ((BleedingFlags.values()[siteVal] & BleedingFlags.Blood) == BleedingFlags.Blood)
            {
                return new PointF(xshift - .3f, yshift);
            }
            else
            {
                return new PointF(0, 0);
            } 
        }
        else
        {
            //shift bleeding points slightly to left.
            //suppuration
            if ((BleedingFlags.values()[siteVal] & BleedingFlags.Suppuration) == BleedingFlags.Suppuration)
            {
                return new PointF(xshift + .3f, yshift);
            }
            else
            {
                return new PointF(0, 0);
            } 
        } 
    }

    //shift suppuration points slightly to right.
    /**
    * Returns a series of points that can be used to create a droplet shape for bleeding and suppuration.  The points form a pentagon with a sixth implied point at 0,0.  Use the points and 0,0 to create 5 triangles.  Coordinates are in mm's.  If the droplet needs to be scaled, that will be done inside this method rather than externally.  The droplet will need to be flipped or rotated about the 0,0 point for use in the mandibular.
    */
    public List<PointF> getDropletVertices() throws Exception {
        List<PointF> retVal = new List<PointF>();
        float scale = 1f;
        retVal.Add(new PointF(0, scale * .89f));
        //top point
        retVal.Add(new PointF(scale * .34f, scale * .049f));
        //upper right
        retVal.Add(new PointF(scale * .21f, scale * -.35f));
        //lower right
        retVal.Add(new PointF(scale * -.21f, scale * -.35f));
        //lower left
        retVal.Add(new PointF(scale * -.34f, scale * .049f));
        return retVal;
    }

}


//upper left