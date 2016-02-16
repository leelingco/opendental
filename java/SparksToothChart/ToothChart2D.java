//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package SparksToothChart;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Tooth;
import SparksToothChart.ToothChartDrawEventArgs;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGroup;
import SparksToothChart.ToothGroupType;
import SparksToothChart.ToothChart2D;

public class ToothChart2D  extends UserControl 
{

    /**
    * This is a reference to the TcData object that's at the wrapper level.
    */
    public SparksToothChart.ToothChartData TcData;
    private boolean MouseIsDown = new boolean();
    /**
    * Mouse move causes this variable to be updated with the current tooth that the mouse is hovering over.
    */
    private String hotTooth = new String();
    /**
    * The previous hotTooth.  If this is different than hotTooth, then mouse has just now moved to a new tooth.  Can be 0 to represent no previous.
    */
    private String hotToothOld = new String();
    /**
    * 
    */
    public SparksToothChart.ToothChartDrawEventHandler SegmentDrawn = null;
    /**
    * GDI+ handle to this control. Used for line drawing and font measurement.
    */
    private Graphics g = null;
    public ToothChart2D() throws Exception {
        initializeComponent();
    }

    public void initializeGraphics() throws Exception {
        g = this.CreateGraphics();
    }

    protected void onPaint(PaintEventArgs e) throws Exception {
        super.OnPaint(e);
        if (DesignMode)
        {
            e.Graphics.DrawImage(pictBox.Image, new Rectangle(0, 0, this.Width, this.Height));
            return ;
        }
         
        //our strategy here will be to draw on a new bitmap.
        Bitmap bitmap = new Bitmap(Width, Height);
        Graphics g = Graphics.FromImage(bitmap);
        g.Clear(TcData.ColorBackground);
        //draw a copy of the tooth chart background
        g.DrawImage(pictBox.Image, TcData.RectTarget);
        g.SmoothingMode = SmoothingMode.HighQuality;
        g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each tooth
            if (StringSupport.equals(TcData.ListToothGraphics.get___idx(t).getToothID(), "implant"))
            {
                continue;
            }
             
            //this is not an actual tooth.
            drawFacialView(TcData.ListToothGraphics.get___idx(t),g);
            drawOcclusalView(TcData.ListToothGraphics.get___idx(t),g);
        }
        drawWatches(g);
        drawNumbers(g);
        drawDrawingSegments(g);
        e.Graphics.DrawImage(bitmap, 0, 0);
        g.Dispose();
    }

    protected void onPaintBackground(PaintEventArgs e) throws Exception {
    }

    //base.OnPaintBackground(e);//don't draw background
    /**
    * 
    */
    private void drawFacialView(ToothGraphic toothGraphic, Graphics g) throws Exception {
        /*
        			if(toothGraphic.Visible
        				|| (toothGraphic.IsCrown && toothGraphic.IsImplant)
        				|| toothGraphic.IsPontic) {
        				//DrawTooth(toothGraphic,g);
        			}
        			float w=0;
        			if(!Tooth.IsPrimary(toothGraphic.ToothID)) {
        				w=ToothGraphic.GetWidth(toothGraphic.ToothID)/TcData.ScaleMmToPix;
        					// /TcData.WidthProjection*(float)Width;
        			}
        			if(!Tooth.IsPrimary(toothGraphic.ToothID) && (!toothGraphic.Visible || toothGraphic.IsPontic)) {
        				if(ToothGraphic.IsMaxillary(toothGraphic.ToothID)) {
        					//g.FillRectangle(new SolidBrush(colorBackSimple),x-w/2f,0,w,Height/2f-20);
        				}
        				else {
        					//g.FillRectangle(new SolidBrush(colorBackSimple),x-w/2f,Height/2f+20,w,Height/2f-20);
        				}
        			}*/
        if (toothGraphic.DrawBigX)
        {
            float x = TcData.getTransXpix(toothGraphic.getToothID());
            float y = TcData.getTransYfacialPix(toothGraphic.getToothID());
            float halfw = 6f * TcData.PixelScaleRatio;
            float halfh = 29f * TcData.PixelScaleRatio;
            //58;
            //float offsety=73;
            //toothGraphic.colorX
            //if(ToothGraphic.IsMaxillary(toothGraphic.ToothID)) {
            //g.DrawLine(new Pen(toothGraphic.colorX),x-halfxwidth,Height/2f-offsetofx-xheight,x+halfxwidth,Height/2f-offsetofx);
            //g.DrawLine(new Pen(toothGraphic.colorX),x+halfxwidth,Height/2f-offsetofx-xheight,x-halfxwidth,Height/2f-offsetofx);
            g.DrawLine(new Pen(toothGraphic.colorX, 2f * TcData.PixelScaleRatio), x - halfw, y - halfh, x + halfw, y + halfh);
            g.DrawLine(new Pen(toothGraphic.colorX, 2f * TcData.PixelScaleRatio), x + halfw, y - halfh, x - halfw, y + halfh);
        }
         
    }

    //}
    //else {//Mandible
    //g.DrawLine(new Pen(toothGraphic.colorX),x-halfxwidth,Height/2f+offsetofx+xheight,x+halfxwidth,Height/2f+offsetofx);
    //g.DrawLine(new Pen(toothGraphic.colorX),x+halfxwidth,Height/2f+offsetofx+xheight,x-halfxwidth,Height/2f+offsetofx);
    //	g.DrawLine(new Pen(toothGraphic.colorX),x-halfw,y+halfh,x+halfw,y);
    //	g.DrawLine(new Pen(toothGraphic.colorX),x+halfw,y+halfh,x-halfw,y);
    //}
    //if(toothGraphic.Visible && toothGraphic.IsRCT) {//draw RCT
    //x=,y= etc
    //toothGraphic.colorRCT
    //?
    //}
    //if(toothGraphic.Visible && toothGraphic.IsBU) {//BU or Post
    //?
    //}
    //if(toothGraphic.IsImplant) {
    //?
    //}
    private void drawOcclusalView(ToothGraphic toothGraphic, Graphics g) throws Exception {
        //now the occlusal surface. Absolute pixels instead of mm relative to center.
        float x = new float(), y = new float();
        x = TcData.getTransXpix(toothGraphic.getToothID());
        y = TcData.getTransYocclusalPix(toothGraphic.getToothID());
        //might not be visible if an implant
        //a crown on an implant will paint
        //pontics won't paint, because tooth is invisible
        //but, unlike the regular toothchart, we do want pontics to paint here
        if (toothGraphic.getVisible() || (toothGraphic.IsCrown && toothGraphic.IsImplant) || toothGraphic.IsPontic)
        {
            drawToothOcclusal(toothGraphic,g);
        }
         
        if (toothGraphic.getVisible() && toothGraphic.IsSealant)
        {
        }
         
    }

    //draw sealant
    //?
    /**
    * 
    */
    private void drawToothOcclusal(ToothGraphic toothGraphic, Graphics g) throws Exception {
        ToothGroup group;
        float x = new float(), y = new float();
        Pen outline = new Pen(Color.Gray);
        for (int i = 0;i < toothGraphic.Groups.Count;i++)
        {
            group = (ToothGroup)toothGraphic.Groups[i];
            if (!group.Visible)
            {
                continue;
            }
             
            x = TcData.getTransXpix(toothGraphic.getToothID());
            y = TcData.getTransYocclusalPix(toothGraphic.getToothID());
            float sqB = 4;
            //half the size of the central square. B for Big.
            float cirB = 9.5f;
            //radius of outer circle
            float sqS = 3;
            //S for small
            float cirS = 8f;
            GraphicsPath path = new GraphicsPath();
            SolidBrush brush = new SolidBrush(group.PaintColor);
            String dir = new String();
            switch(group.GroupType)
            {
                case O: 
                    g.FillRectangle(brush, x - sqB, y - sqB, 2f * sqB, 2f * sqB);
                    g.DrawRectangle(outline, x - sqB, y - sqB, 2f * sqB, 2f * sqB);
                    break;
                case I: 
                    g.FillRectangle(brush, x - sqS, y - sqS, 2f * sqS, 2f * sqS);
                    g.DrawRectangle(outline, x - sqS, y - sqS, 2f * sqS, 2f * sqS);
                    break;
                case B: 
                    if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
                    {
                        path = getPath("U",x,y,sqB,cirB);
                    }
                    else
                    {
                        path = getPath("D",x,y,sqB,cirB);
                    } 
                    g.FillPath(brush, path);
                    g.DrawPath(outline, path);
                    break;
                case F: 
                    if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
                    {
                        path = getPath("U",x,y,sqS,cirS);
                    }
                    else
                    {
                        path = getPath("D",x,y,sqS,cirS);
                    } 
                    g.FillPath(brush, path);
                    g.DrawPath(outline, path);
                    break;
                case L: 
                    if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
                    {
                        dir = "D";
                    }
                    else
                    {
                        dir = "U";
                    } 
                    if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
                    {
                        path = getPath(dir,x,y,sqS,cirS);
                    }
                    else
                    {
                        path = getPath(dir,x,y,sqB,cirB);
                    } 
                    g.FillPath(brush, path);
                    g.DrawPath(outline, path);
                    break;
                case M: 
                    if (ToothGraphic.isRight(toothGraphic.getToothID()))
                    {
                        dir = "R";
                    }
                    else
                    {
                        dir = "L";
                    } 
                    if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
                    {
                        path = getPath(dir,x,y,sqS,cirS);
                    }
                    else
                    {
                        path = getPath(dir,x,y,sqB,cirB);
                    } 
                    g.FillPath(brush, path);
                    g.DrawPath(outline, path);
                    break;
                case D: 
                    if (ToothGraphic.isRight(toothGraphic.getToothID()))
                    {
                        dir = "L";
                    }
                    else
                    {
                        dir = "R";
                    } 
                    if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
                    {
                        path = getPath(dir,x,y,sqS,cirS);
                    }
                    else
                    {
                        path = getPath(dir,x,y,sqB,cirB);
                    } 
                    g.FillPath(brush, path);
                    g.DrawPath(outline, path);
                    break;
            
            }
        }
    }

    //group.PaintColor
    //Gl.glCallList(displayListOffset+toothGraphic.GetIndexForDisplayList(group));
    /**
    * Gets a path for the pie shape that represents a tooth surface.  sq and cir refer to the radius of those two elements.
    */
    private GraphicsPath getPath(String UDLR, float x, float y, float sq, float cir) throws Exception {
        GraphicsPath path = new GraphicsPath();
        float pt = cir * 0.7071f;
        //the x or y dist to the point where the circle is at 45 degrees.
        System.String __dummyScrutVar1 = UDLR;
        if (__dummyScrutVar1.equals("U"))
        {
            path.AddLine(x - sq, y - sq, x + sq, y - sq);
            path.AddLine(x + sq, y - sq, x + pt, y - pt);
            path.AddArc(x - cir, y - cir, cir * 2f, cir * 2f, 360 - 45, -90);
            path.AddLine(x - pt, y - pt, x - sq, y - sq);
        }
        else if (__dummyScrutVar1.equals("D"))
        {
            path.AddLine(x + sq, y + sq, x - sq, y + sq);
            path.AddLine(x - sq, y + sq, x - pt, y + pt);
            path.AddArc(x - cir, y - cir, cir * 2f, cir * 2f, 90 + 45, -90);
            path.AddLine(x + pt, y + pt, x + sq, y + sq);
        }
        else if (__dummyScrutVar1.equals("L"))
        {
            path.AddLine(x - sq, y + sq, x - sq, y - sq);
            path.AddLine(x - sq, y - sq, x - pt, y - pt);
            path.AddArc(x - cir, y - cir, cir * 2f, cir * 2f, 180 + 45, -90);
            path.AddLine(x - pt, y + pt, x - sq, y + sq);
        }
        else if (__dummyScrutVar1.equals("R"))
        {
            path.AddLine(x + sq, y - sq, x + sq, y + sq);
            path.AddLine(x + sq, y + sq, x + pt, y + pt);
            path.AddArc(x - cir, y - cir, cir * 2f, cir * 2f, 45, -90);
            path.AddLine(x + pt, y - pt, x + sq, y - sq);
        }
            
        return path;
    }

    private void drawWatches(Graphics g) throws Exception {
        Hashtable watchTeeth = new Hashtable(TcData.ListToothGraphics.Count);
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each adult tooth
            ToothGraphic toothGraphic = TcData.ListToothGraphics.get___idx(t);
            //If a tooth is marked to be watched then it is always visible, even if the tooth is missing/hidden.
            if (StringSupport.equals(toothGraphic.getToothID(), "implant") || !toothGraphic.Watch || Tooth.isPrimary(toothGraphic.getToothID()))
            {
                continue;
            }
             
            watchTeeth[toothGraphic.getToothID()] = toothGraphic;
        }
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each primary tooth
            ToothGraphic toothGraphic = TcData.ListToothGraphics.get___idx(t);
            //If a tooth is marked to be watched then it is always visible, even if the tooth is missing/hidden.
            if (StringSupport.equals(toothGraphic.getToothID(), "implant") || !toothGraphic.Watch || !Tooth.isPrimary(toothGraphic.getToothID()) || !toothGraphic.getVisible())
            {
                continue;
            }
             
            watchTeeth[Tooth.priToPerm(toothGraphic.getToothID())] = toothGraphic;
        }
        for (Object __dummyForeachVar0 : watchTeeth)
        {
            DictionaryEntry toothGraphic = (DictionaryEntry)__dummyForeachVar0;
            renderToothWatch(g,(ToothGraphic)toothGraphic.Value);
        }
    }

    private void renderToothWatch(Graphics g, ToothGraphic toothGraphic) throws Exception {
        float toMm = 1f / TcData.ScaleMmToPix;
        SolidBrush brush = new SolidBrush(toothGraphic.colorWatch);
        //Drawing a white silhouette around the colored watch W doesn't make sense here because unerupted teeth do not change color in this chart.
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                g.DrawString("W", Font, brush, new PointF(TcData.getTransXpix(toothGraphic.getToothID()) + toothGraphic.ShiftM - 6f, 0));
            }
            else
            {
                g.DrawString("W", Font, brush, new PointF(TcData.getTransXpix(toothGraphic.getToothID()) + toothGraphic.ShiftM - 7f, Height - Font.Size - 8f));
            } 
        }
        else
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                g.DrawString("W", Font, brush, new PointF(TcData.getTransXpix(toothGraphic.getToothID()) - toothGraphic.ShiftM - 6f, 0));
            }
            else
            {
                g.DrawString("W", Font, brush, new PointF(TcData.getTransXpix(toothGraphic.getToothID()) - toothGraphic.ShiftM - 7f, Height - Font.Size - 8f));
            } 
        } 
        brush.Dispose();
    }

    private void drawNumbers(Graphics g) throws Exception {
        if (DesignMode)
        {
            return ;
        }
         
        String tooth_id = new String();
        for (int i = 1;i <= 52;i++)
        {
            tooth_id = Tooth.fromOrdinal(i);
            if (TcData.getSelectedTeeth().Contains(tooth_id))
            {
                drawNumber(tooth_id,true,true,g);
            }
            else
            {
                drawNumber(tooth_id,false,true,g);
            } 
        }
    }

    /**
    * Draws the number and the rectangle behind it.  Draws in the appropriate color
    */
    private void drawNumber(String tooth_id, boolean isSelected, boolean isFullRedraw, Graphics g) throws Exception {
        if (DesignMode)
        {
            return ;
        }
         
        if (TcData == null)
        {
            return ;
        }
         
        //trying to fix a designtime bug.
        if (!Tooth.isValidDB(tooth_id))
        {
            return ;
        }
         
        if (TcData.ListToothGraphics.get___idx(tooth_id) == null)
        {
            return ;
        }
         
        //throw new Exception(tooth_id+" null");
        //for some reason, it's still getting to here in DesignMode
        if (isFullRedraw)
        {
            //if redrawing all numbers
            if (TcData.ListToothGraphics.get___idx(tooth_id).getHideNumber())
            {
                return ;
            }
             
            //and this is a "hidden" number
            //skip
            if (Tooth.isPrimary(tooth_id) && !TcData.ListToothGraphics.get___idx(Tooth.priToPerm(tooth_id)).ShowPrimaryLetter)
            {
                return ;
            }
             
        }
         
        //but not set to show primary letters
        String displayNum = Tooth.getToothLabelGraphic(tooth_id,TcData.ToothNumberingNomenclature);
        float toMm = 1f / TcData.ScaleMmToPix;
        float labelWidthMm = g.MeasureString(displayNum, Font).Width / TcData.ScaleMmToPix;
        float labelHeightMm = ((float)Font.Height - .5f) / TcData.ScaleMmToPix;
        SizeF labelSizeF = new SizeF(labelWidthMm, (float)Font.Height / TcData.ScaleMmToPix);
        Rectangle rec = TcData.getNumberRecPix(tooth_id,labelSizeF);
        //Rectangle recPix=TcData.ConvertRecToPix(recMm);
        if (isSelected)
        {
            g.FillRectangle(new SolidBrush(TcData.ColorBackHighlight), rec);
        }
        else
        {
            g.FillRectangle(new SolidBrush(TcData.ColorBackground), rec);
        } 
        if (TcData.ListToothGraphics.get___idx(tooth_id).getHideNumber())
        {
        }
        else //If number is hidden.
        //do not print string
        if (Tooth.isPrimary(tooth_id) && !TcData.ListToothGraphics.get___idx(Tooth.priToPerm(tooth_id)).ShowPrimaryLetter)
        {
        }
        else //do not print string
        if (isSelected)
        {
            g.DrawString(displayNum, Font, new SolidBrush(TcData.ColorTextHighlight), rec.X, rec.Y);
        }
        else
        {
            g.DrawString(displayNum, Font, new SolidBrush(TcData.ColorText), rec.X, rec.Y);
        }   
    }

    private void drawDrawingSegments(Graphics g) throws Exception {
        String[] pointStr = new String[]();
        List<PointF> points = new List<PointF>();
        PointF pointf = new PointF();
        String[] xy = new String[]();
        float x = new float();
        float y = new float();
        Pen pen = new Pen();
        for (int s = 0;s < TcData.DrawingSegmentList.Count;s++)
        {
            pen = new Pen(TcData.DrawingSegmentList[s].ColorDraw, 2.2f * TcData.PixelScaleRatio);
            pointStr = TcData.DrawingSegmentList[s].DrawingSegment.Split(';');
            points = new List<PointF>();
            for (int p = 0;p < pointStr.Length;p++)
            {
                xy = pointStr[p].Split(',');
                if (xy.Length == 2)
                {
                    x = TcData.RectTarget.X + float.Parse(xy[0]) * TcData.PixelScaleRatio;
                    y = TcData.RectTarget.Y + float.Parse(xy[1]) * TcData.PixelScaleRatio;
                    pointf = new PointF(x, y);
                    points.Add(pointf);
                }
                 
            }
            for (int i = 1;i < points.Count;i++)
            {
                g.DrawLine(pen, points[i - 1].X, points[i - 1].Y, points[i].X, points[i].Y);
            }
        }
    }

    /**
    * Returns a bitmap of what is showing in the control.  Used for printing.
    */
    public Bitmap getBitmap() throws Exception {
        Bitmap bmp = new Bitmap(this.Width, this.Height);
        Graphics gfx = Graphics.FromImage(bmp);
        PaintEventArgs e = new PaintEventArgs(gfx, new Rectangle(0, 0, bmp.Width, bmp.Height));
        onPaint(e);
        return bmp;
    }

    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        MouseIsDown = true;
        if (TcData.ListToothGraphics.Count == 0)
        {
            return ;
        }
         
        //still starting up?
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            String toothClicked = TcData.GetToothAtPoint(e.Location);
            if (TcData.getSelectedTeeth().Contains(toothClicked))
            {
                setSelected(toothClicked,false);
            }
            else
            {
                setSelected(toothClicked,true);
            } 
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            TcData.PointList.Add(new Point(e.X, e.Y));
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
        }
        else //do nothing
        if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
            //look for any lines near the "wand".
            //since the line segments are so short, it's sufficient to check end points.
            String[] xy = new String[]();
            String[] pointStr = new String[]();
            float x = new float();
            float y = new float();
            float dist = new float();
            //the distance between the point being tested and the center of the eraser circle.
            float radius = 2f;
            for (int i = 0;i < TcData.DrawingSegmentList.Count;i++)
            {
                //by trial and error to achieve best feel.
                //PointF eraserPt=new PointF(e.X+8.49f,e.Y+8.49f);
                pointStr = TcData.DrawingSegmentList[i].DrawingSegment.Split(';');
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        x = float.Parse(xy[0]);
                        y = float.Parse(xy[1]);
                        dist = (float)Math.Sqrt(Math.Pow(Math.Abs(x - e.X), 2) + Math.Pow(Math.Abs(y - e.Y), 2));
                        if (dist <= radius)
                        {
                            //testing circle intersection here
                            OnSegmentDrawn(TcData.DrawingSegmentList[i].DrawingSegment);
                            TcData.DrawingSegmentList[i].ColorDraw = TcData.ColorDrawing;
                            Invalidate();
                            return ;
                                ;
                        }
                         
                    }
                     
                }
            }
        }
            
    }

    protected void onMouseMove(MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        if (TcData.ListToothGraphics.Count == 0)
        {
            return ;
        }
         
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            //if(drawMode==DrawingMode.Simple2D) {
            hotTooth = TcData.GetToothAtPoint(e.Location);
            if (StringSupport.equals(hotTooth, hotToothOld))
            {
                return ;
            }
             
            //mouse has not moved to another tooth
            hotToothOld = hotTooth;
            if (MouseIsDown)
            {
                //drag action
                if (TcData.getSelectedTeeth().Contains(hotTooth))
                {
                    setSelected(hotTooth,false);
                }
                else
                {
                    setSelected(hotTooth,true);
                } 
            }
             
        }
        else //}
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            if (!MouseIsDown)
            {
                return ;
            }
             
            TcData.PointList.Add(new Point(e.X, e.Y));
            //just add the last line segment instead of redrawing the whole thing.
            //Graphics g=this.CreateGraphics();
            g.SmoothingMode = SmoothingMode.HighQuality;
            Pen pen = new Pen(TcData.ColorDrawing, 2f);
            int i = TcData.PointList.Count - 1;
            g.DrawLine(pen, TcData.PointList[i - 1].X, TcData.PointList[i - 1].Y, TcData.PointList[i].X, TcData.PointList[i].Y);
        }
        else //g.Dispose();
        //Invalidate();
        if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
            if (!MouseIsDown)
            {
                return ;
            }
             
            //look for any lines that intersect the "eraser".
            //since the line segments are so short, it's sufficient to check end points.
            String[] xy = new String[]();
            String[] pointStr = new String[]();
            float x = new float();
            float y = new float();
            float dist = new float();
            //the distance between the point being tested and the center of the eraser circle.
            float radius = 8f;
            //by trial and error to achieve best feel.
            PointF eraserPt = new PointF(e.X + 8.49f, e.Y + 8.49f);
            for (int i = 0;i < TcData.DrawingSegmentList.Count;i++)
            {
                pointStr = TcData.DrawingSegmentList[i].DrawingSegment.Split(';');
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        x = float.Parse(xy[0]);
                        y = float.Parse(xy[1]);
                        dist = (float)Math.Sqrt(Math.Pow(Math.Abs(x - eraserPt.X), 2) + Math.Pow(Math.Abs(y - eraserPt.Y), 2));
                        if (dist <= radius)
                        {
                            //testing circle intersection here
                            OnSegmentDrawn(TcData.DrawingSegmentList[i].DrawingSegment);
                            //triggers a deletion from db.
                            TcData.DrawingSegmentList.RemoveAt(i);
                            Invalidate();
                            return ;
                                ;
                        }
                         
                    }
                     
                }
            }
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
        }
            
    }

    //do nothing
    protected void onMouseUp(MouseEventArgs e) throws Exception {
        super.OnMouseUp(e);
        MouseIsDown = false;
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            String drawingSegment = "";
            for (int i = 0;i < TcData.PointList.Count;i++)
            {
                if (i > 0)
                {
                    drawingSegment += ";";
                }
                 
                //I could compensate to center point here:
                drawingSegment += TcData.PointList[i].X + "," + TcData.PointList[i].Y;
            }
            onSegmentDrawn(drawingSegment);
            TcData.PointList = new List<Point>();
        }
        else //Invalidate();//?
        if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
        }
        else //do nothing
        if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
        }
           
    }

    //do nothing
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

    /**
    * Used by mousedown and mouse move to set teeth selected or unselected.  A similar method is used externally in the wrapper to set teeth selected.  This private method might be faster since it only draws the changes.
    */
    private void setSelected(String tooth_id, boolean setValue) throws Exception {
        //Graphics g=this.CreateGraphics();
        if (setValue)
        {
            TcData.getSelectedTeeth().Add(tooth_id);
            drawNumber(tooth_id,true,false,g);
        }
        else
        {
            TcData.getSelectedTeeth().Remove(tooth_id);
            drawNumber(tooth_id,false,false,g);
        } 
        //string displayNum=Tooth.GetToothLabelGraphic(tooth_id,TcData.ToothNumberingNomenclature);
        //float labelWidthMm=g.MeasureString(displayNum,Font).Width/TcData.ScaleMmToPix;
        //float labelHeightMm=(float)Font.Height/TcData.ScaleMmToPix;
        //SizeF labelSizeF=new SizeF(labelWidthMm,labelHeightMm);
        //RectangleF recF=TcData.GetNumberRecPix(tooth_id,labelSizeF);
        //Rectangle rec=new Rectangle((int)recF.X,(int)recF.Y,(int)recF.Width,(int)recF.Height);
        Invalidate();
        //rec);
        Application.DoEvents();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ToothChart2D.class);
        this.pictBox = new System.Windows.Forms.PictureBox();
        ((System.ComponentModel.ISupportInitialize)(this.pictBox)).BeginInit();
        this.SuspendLayout();
        //
        // pictBox
        //
        this.pictBox.Image = ((System.Drawing.Image)(resources.GetObject("pictBox.Image")));
        this.pictBox.Location = new System.Drawing.Point(0, 0);
        this.pictBox.Name = "pictBox";
        this.pictBox.Size = new System.Drawing.Size(410, 307);
        this.pictBox.TabIndex = 1;
        this.pictBox.TabStop = false;
        this.pictBox.Visible = false;
        //
        // ToothChart2D
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.pictBox);
        this.Name = "ToothChart2D";
        this.Size = new System.Drawing.Size(410, 307);
        ((System.ComponentModel.ISupportInitialize)(this.pictBox)).EndInit();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.PictureBox pictBox = new System.Windows.Forms.PictureBox();
}
/*
			if(TcData.ALSelectedTeeth.Count==0) {
				selectedTeeth=new string[0];
			}
			else {
				selectedTeeth=new string[ALSelectedTeeth.Count];
				for(int i=0;i<ALSelectedTeeth.Count;i++) {
					if(ToothGraphic.IsValidToothID(ToothGraphic.PermToPri(ALSelectedTeeth[i].ToString()))//pri is valid
					&& ListToothGraphics[ALSelectedTeeth[i].ToString()].ShowPrimary)//and set to show pri
				{
						selectedTeeth[i]=ToothGraphic.PermToPri(ALSelectedTeeth[i].ToString());
					}
					else {
						selectedTeeth[i]=((int)ALSelectedTeeth[i]).ToString();
					}
				}
			}*///g.Dispose();

/*
			if(TcData.ALSelectedTeeth.Count==0) {
				selectedTeeth=new string[0];
			}
			else {
				selectedTeeth=new string[ALSelectedTeeth.Count];
				for(int i=0;i<ALSelectedTeeth.Count;i++) {
					if(ToothGraphic.IsValidToothID(ToothGraphic.PermToPri(ALSelectedTeeth[i].ToString()))//pri is valid
					&& ListToothGraphics[ALSelectedTeeth[i].ToString()].ShowPrimary)//and set to show pri
				{
						selectedTeeth[i]=ToothGraphic.PermToPri(ALSelectedTeeth[i].ToString());
					}
					else {
						selectedTeeth[i]=((int)ALSelectedTeeth[i]).ToString();
					}
				}
			}*///g.Dispose();