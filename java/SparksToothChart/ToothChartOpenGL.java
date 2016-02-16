//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package SparksToothChart;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Tooth;
import SparksToothChart.LineSimple;
import SparksToothChart.ToothChartDrawEventArgs;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGroup;
import SparksToothChart.ToothGroupType;


/*=============================================================================================================
Copyright (C) 2006  Jordan Sparks, DMD.  http://www.open-dent.com,  http://www.docsparks.com
This program is free software; you can redistribute it and/or modify it under the terms of the
GNU Db Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.
This program is distributed in the hope that it will be useful, but without any warranty. See the GNU Db Public License
for more details, available at http://www.opensource.org/licenses/gpl-license.php
Any changes to this program must follow the guidelines of the GPL license if a modified version is to be
redistributed.
===============================================================================================================*/
public class ToothChartOpenGL  extends CodeBase.OpenGLWinFormsControl 
{

    //.SimpleOpenGlControl {
    float[] specular_color_normal = new float[]();
    //white
    float[] specular_color_cementum = new float[]();
    //gray
    float[] shininess = new float[]();
    private boolean MouseIsDown = new boolean();
    /**
    * Mouse move causes this variable to be updated with the current tooth that the mouse is hovering over.
    */
    private String hotTooth = new String();
    /**
    * The previous hotTooth.  If this is different than hotTooth, then mouse has just now moved to a new tooth.  Can be 0 to represent no previous.
    */
    private String hotToothOld = new String();
    private int fontOffset = new int();
    private int displayListOffset = new int();
    private String[][] fontsymbols = new String[][]();
    //<summary>This gets set to true during certain operations where we do not need to redraw all the teeth.  Specifically, during tooth selection where only the color of the tooth number text needs to change.  In this case, the rest of the scene will not be rendered again.</summary>
    //private bool suspendRendering;
    private int selectedPixelFormat = new int();
    /**
    * 
    */
    public SparksToothChart.ToothChartDrawEventHandler SegmentDrawn = null;
    /**
    * This is a reference to the TcData object that's at the wrapper level.
    */
    public SparksToothChart.ToothChartData TcData;
    //<summary>GDI+ handle to this control. Used for line drawing and font measurement.</summary>
    //private Graphics g=null;
    /**
    * Specify the hardware mode to create the tooth chart with. Set hardwareMode=true to try for hardware accelerated graphics, and set hardwareMode=false to try and get software graphics.
    */
    public ToothChartOpenGL(boolean hardwareMode, int preferredPixelFormatNum) throws Exception {
        usehardware = hardwareMode;
        initializeComponent();
        this.TaoSetupContext += new System.EventHandler(ToothChart_TaoSetupContext);
        this.TaoRenderScene += new System.EventHandler(ToothChart_TaoRenderScene);
        selectedPixelFormat = taoInitializeContexts(preferredPixelFormatNum);
        setTaoRenderEnabled(true);
        Gl.glDisable(Gl.GL_TEXTURE);
    }

    //Disable texturing, since we don't use it.  This should prevent a glCopyPixels() problem in Gdi.SwapBuffersFast() on ATI graphics cards.
    /* This didn't work because the OpenGL control is not designed to be a container.  I don't have time to wrap it in another layer.
    			pictBox=new PictureBox();
    			pictBox.Location=new Point(0,0);
    			pictBox.Name="pictBox";
    			pictBox.Size=this.Size;
    			pictBox.Dock=DockStyle.Fill;
    			pictBox.Image=bitmapInPictBox;
    			Controls.Add(pictBox);*/
    public void initializeGraphics() throws Exception {
        makeDisplayLists();
    }

    //g=this.CreateGraphics();
    protected void onResize(EventArgs e) throws Exception {
        super.OnResize(e);
    }

    private void toothChart_TaoSetupContext(Object sender, System.EventArgs e) throws Exception {
        //event from base class when context needs to be setup.
        makeRasterFont();
    }

    public int getSelectedPixelFormatNumber() throws Exception {
        return selectedPixelFormat;
    }

    /**
    * Returns a bitmap of what is showing in the control.  Used for printing.
    */
    public Bitmap getBitmap() throws Exception {
        //This doesn't seem to work sometimes
        //return GetBitmapOfOpenGL();
        //this seems convoluted
        Bitmap dummy = new Bitmap(Width, Height);
        Graphics g = Graphics.FromImage(dummy);
        PaintEventArgs e = new PaintEventArgs(g, new Rectangle(0, 0, Width, Height));
        render(e);
        //Bitmap result=ReadFrontBuffer();
        Bitmap result = getBitmapOfOpenGL();
        g.Dispose();
        return result;
    }

    /**
    * This only gets the 3D scene.
    */
    private Bitmap getBitmapOfOpenGL() throws Exception {
        Gl.glFlush();
        Bitmap bitmap = new Bitmap(Width, Height);
        BitmapData bitmapData = bitmap.LockBits(new Rectangle(0, 0, bitmap.Width, bitmap.Height), ImageLockMode.WriteOnly, PixelFormat.Format24bppRgb);
        Gl.glReadPixels(0, 0, bitmap.Width, bitmap.Height, Gl.GL_BGR_EXT, Gl.GL_UNSIGNED_BYTE, bitmapData.Scan0);
        bitmap.UnlockBits(bitmapData);
        bitmap.RotateFlip(RotateFlipType.RotateNoneFlipY);
        return bitmap;
    }

    protected void onPaint(PaintEventArgs e) throws Exception {
        super.onPaint(e);
    }

    private void toothChart_TaoRenderScene(Object sender, System.EventArgs e) throws Exception {
        //if(suspendRendering){
        //	return;
        //}
        //This first part was originally in setup context
        Gl.glClearColor((float)TcData.ColorBackground.R / 255f, (float)TcData.ColorBackground.G / 255f, (float)TcData.ColorBackground.B / 255f, 0f);
        Gl.glClearAccum(0f, 0f, 0f, 0f);
        //Lighting
        float ambI = .2f;
        //.1f;//Darker for testing
        float difI = .6f;
        //.3f;//Darker for testing
        float specI = 1f;
        float[] light_ambient = new float[]{ ambI, ambI, ambI, 1f };
        //RGB,A=1 for no transparency. Default 0001
        float[] light_diffuse = new float[]{ difI, difI, difI, 1f };
        //RGBA. Default 1111. 'typical'
        float[] light_specular = new float[]{ specI, specI, specI, 1f };
        //RGBA. Default 1111
        float[] light_position = new float[]{ -0.5f, 0.1f, 1f, 0f };
        //xyz(direction, not position), w=0 for infinite
        Gl.glLightfv(Gl.GL_LIGHT0, Gl.GL_AMBIENT, light_ambient);
        Gl.glLightfv(Gl.GL_LIGHT0, Gl.GL_DIFFUSE, light_diffuse);
        Gl.glLightfv(Gl.GL_LIGHT0, Gl.GL_SPECULAR, light_specular);
        Gl.glLightfv(Gl.GL_LIGHT0, Gl.GL_POSITION, light_position);
        //Materials
        Gl.glShadeModel(Gl.GL_SMOOTH);
        //OK to just set these three once.
        specular_color_normal = new float[]{ 1.0f, 1.0f, 1.0f, 1.0f };
        //1111 for white. RGBA
        specular_color_cementum = new float[]{ 0.1f, 0.1f, 0.1f, 1.0f };
        //gray
        shininess = new float[]{ 90f };
        //0 to 128. Size of specular reflection. 128 smallest
        Gl.glEnable(Gl.GL_LIGHTING);
        Gl.glEnable(Gl.GL_LIGHT0);
        //Render Scene starts here----------------------------------------------------------------------------------
        Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT);
        //Clears the color buffer and depth buffer.
        //viewing transformation.  gluLookAt is too complex, so not used
        //default was Z=1, looking towards the origin
        Gl.glMatrixMode(Gl.GL_MODELVIEW);
        Gl.glLoadIdentity();
        //clears the matrix
        Gl.glMatrixMode(Gl.GL_PROJECTION);
        //only the projection matrix will be affected.
        Gl.glLoadIdentity();
        //double HeightProjection=WidthProjection*this.Height/this.Width;
        //Gl.glOrtho(-WidthProjection/2,WidthProjection/2,//orthographic projection. L,R
        //	-HeightProjection/2,HeightProjection/2,//Bot,Top
        //	-WidthProjection/2,WidthProjection/2);//Near,Far
        //double widthProj=(double)TcData.SizeOriginalProjection.Width;
        //double heightProj=widthProj*Height/Width;
        double heightProj = (double)TcData.SizeOriginalProjection.Height;
        double widthProj = (double)TcData.SizeOriginalProjection.Width;
        if (TcData.IsWide)
        {
            widthProj = heightProj * Width / Height;
        }
        else
        {
            //tall
            heightProj = widthProj * Height / Width;
        } 
        //orthographic projection. L,R
        //Bot,Top
        Gl.glOrtho(-widthProj / 2, widthProj / 2, -heightProj / 2, heightProj / 2, -widthProj / 2, widthProj / 2);
        //Near,Far
        Gl.glMatrixMode(Gl.GL_MODELVIEW);
        //viewport transformation not used. Default is to fill entire control.
        Gl.glEnable(Gl.GL_BLEND);
        Gl.glEnable(Gl.GL_LINE_SMOOTH);
        Gl.glHint(Gl.GL_LINE_SMOOTH_HINT, Gl.GL_DONT_CARE);
        drawScene();
    }

    //jitter code for antialias starts here, but I can't get it to work:
    //Gl.glFlush();//handled for me in base class
    private void drawScene() throws Exception {
        SparksToothChart.ToothChartWrapper wrapper = (SparksToothChart.ToothChartWrapper)this.Parent;
        String wrappername = wrapper.Name;
        boolean isRCT8 = wrapper.getTcData().ListToothGraphics.get___idx("8").IsRCT;
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each tooth
            if (StringSupport.equals(TcData.ListToothGraphics.get___idx(t).getToothID(), "implant"))
            {
                continue;
            }
             
            //this is not an actual tooth.
            drawFacialView(TcData.ListToothGraphics.get___idx(t));
            drawOcclusalView(TcData.ListToothGraphics.get___idx(t));
        }
        //
        //bitmapInPictBox=GetBitmapOfOpenGL();
        //gg=Graphics.FromImage(bitmapInPictBox);
        drawWatches();
        drawNumbersAndLines();
        drawDrawingSegments();
        //g.DrawImage(bitmapInPictBox,0,0);
        Gl.glFlush();
    }

    private void drawFacialView(ToothGraphic toothGraphic) throws Exception {
        Gl.glPushMatrix();
        //remember position of origin
        //Move the tooth to the correct position for facial view
        Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYfacial(toothGraphic.getToothID()), 0);
        rotateAndTranslateUser(toothGraphic);
        if (toothGraphic.getVisible() || (toothGraphic.IsCrown && toothGraphic.IsImplant) || toothGraphic.IsPontic)
        {
            drawTooth(toothGraphic);
        }
         
        Gl.glDisable(Gl.GL_DEPTH_TEST);
        if (toothGraphic.DrawBigX)
        {
            Gl.glDisable(Gl.GL_LIGHTING);
            Gl.glEnable(Gl.GL_BLEND);
            //move the bigX 6mm to the Facial so it will paint in front of the tooth
            Gl.glTranslatef(0, 0, 6f);
            Gl.glBlendFunc(Gl.GL_SRC_ALPHA, Gl.GL_ONE_MINUS_SRC_ALPHA);
            Gl.glLineWidth(2f * TcData.PixelScaleRatio);
            //thickness of line depends on size of window
            Gl.glColor3f((float)toothGraphic.colorX.R / 255f, (float)toothGraphic.colorX.G / 255f, (float)toothGraphic.colorX.B / 255f);
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2f(-2f, 12f);
                Gl.glVertex2f(2f, -6f);
                Gl.glEnd();
                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2f(2f, 12f);
                Gl.glVertex2f(-2f, -6f);
                Gl.glEnd();
            }
            else
            {
                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2f(-2f, 6f);
                Gl.glVertex2f(2f, -12f);
                Gl.glEnd();
                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2f(2f, 6f);
                Gl.glVertex2f(-2f, -12f);
                Gl.glEnd();
            } 
        }
         
        Gl.glPopMatrix();
        //reset to origin
        if (toothGraphic.getVisible() && toothGraphic.IsRCT)
        {
            //draw RCT
            Gl.glPushMatrix();
            Gl.glTranslatef(0, 0, 10f);
            //move RCT forward 10mm so it will be visible.
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYfacial(toothGraphic.getToothID()), 0);
            Gl.glDisable(Gl.GL_LIGHTING);
            Gl.glEnable(Gl.GL_BLEND);
            Gl.glColor3f((float)toothGraphic.colorRCT.R / 255f, (float)toothGraphic.colorRCT.G / 255f, (float)toothGraphic.colorRCT.B / 255f);
            //.5f);//only 1/2 darkness
            Gl.glBlendFunc(Gl.GL_SRC_ALPHA, Gl.GL_ONE_MINUS_SRC_ALPHA);
            Gl.glLineWidth(2.2f * TcData.PixelScaleRatio);
            Gl.glPointSize(1.8f * TcData.PixelScaleRatio);
            //point is slightly smaller since no antialiasing
            rotateAndTranslateUser(toothGraphic);
            List<LineSimple> lines = toothGraphic.getRctLines();
            for (int i = 0;i < lines.Count;i++)
            {
                Gl.glBegin(Gl.GL_LINE_STRIP);
                for (int j = 0;j < lines[i].Vertices.Count;j++)
                {
                    Gl.glVertex3f(lines[i].Vertices[j].X, lines[i].Vertices[j].Y, lines[i].Vertices[j].Z);
                }
                Gl.glEnd();
            }
            Gl.glPopMatrix();
            //This section is a necessary workaround for OpenGL.
            //It draws a point at each intersection to hide the unsightly transitions between line segments.
            Gl.glPushMatrix();
            Gl.glTranslatef(0, 0, 10.5f);
            //move forward 10.5mm so it will cover the lines
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYfacial(toothGraphic.getToothID()), 0);
            rotateAndTranslateUser(toothGraphic);
            Gl.glDisable(Gl.GL_BLEND);
            for (int i = 0;i < lines.Count;i++)
            {
                Gl.glBegin(Gl.GL_POINTS);
                for (int j = 0;j < lines[i].Vertices.Count;j++)
                {
                    //but ignore the first and last.  We are only concerned with where lines meet.
                    if (j == 0 || j == lines[i].Vertices.Count - 1)
                    {
                        continue;
                    }
                     
                    Gl.glVertex3f(lines[i].Vertices[j].X, lines[i].Vertices[j].Y, lines[i].Vertices[j].Z);
                }
                Gl.glEnd();
            }
            Gl.glPopMatrix();
        }
         
        ToothGroup groupBU = toothGraphic.getGroup(ToothGroupType.Buildup);
        //during debugging, not all teeth have a BU group yet.
        if (toothGraphic.getVisible() && groupBU != null && groupBU.Visible)
        {
            //BU or Post
            Gl.glPushMatrix();
            Gl.glTranslatef(0, 0, 13f);
            //move BU forward 13mm so it will be visible.
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYfacial(toothGraphic.getToothID()), 0);
            Gl.glDisable(Gl.GL_LIGHTING);
            Gl.glDisable(Gl.GL_BLEND);
            Color colorBU = toothGraphic.getGroup(ToothGroupType.Buildup).PaintColor;
            Gl.glColor3f((float)colorBU.R / 255f, (float)colorBU.G / 255f, (float)colorBU.B / 255f);
            rotateAndTranslateUser(toothGraphic);
            Gl.glCallList(displayListOffset + toothGraphic.getIndexForDisplayList(toothGraphic.getGroup(ToothGroupType.Buildup)));
            //Triangle poly=toothGraphic.GetBUpoly();
            //Gl.glBegin(Gl.GL_POLYGON);
            //for(int i=0;i<poly.Vertices.Count;i++) {
            //	Gl.glVertex3f(poly.Vertices[i].X,poly.Vertices[i].Y,poly.Vertices[i].Z);
            //}
            //Gl.glEnd();
            Gl.glPopMatrix();
        }
         
        if (toothGraphic.IsImplant)
        {
            Gl.glPushMatrix();
            //Move the tooth to the correct position for facial view
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYfacial(toothGraphic.getToothID()), 0);
            rotateAndTranslateUser(toothGraphic);
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //flip the implant upside down
                Gl.glRotatef(180f, 0, 0, 1f);
            }
             
            Gl.glEnable(Gl.GL_LIGHTING);
            Gl.glEnable(Gl.GL_BLEND);
            Gl.glEnable(Gl.GL_DEPTH_TEST);
            ToothGroup group = (ToothGroup)TcData.ListToothGraphics.get___idx("implant").Groups[0];
            float[] material_color = new float[]{ (float)toothGraphic.colorImplant.R / 255f, (float)toothGraphic.colorImplant.G / 255f, (float)toothGraphic.colorImplant.B / 255f, (float)toothGraphic.colorImplant.A / 255f };
            //RGBA
            Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_SPECULAR, specular_color_normal);
            Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_SHININESS, shininess);
            Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_AMBIENT_AND_DIFFUSE, material_color);
            Gl.glBlendFunc(Gl.GL_ONE, Gl.GL_ZERO);
            Gl.glHint(Gl.GL_POLYGON_SMOOTH_HINT, Gl.GL_NICEST);
            for (int i = 0;i < group.Faces.Count;i++)
            {
                //  .GetLength(0);i++) {//loop through each face
                Gl.glBegin(Gl.GL_POLYGON);
                for (int j = 0;j < group.Faces[i].IndexList.Count;j++)
                {
                    //.Length;j++) {//loop through each vertex
                    //The index for both will always be the same because we enforce a 1:1 relationship.
                    //We show grabbing a float[3], but we could just as easily use the index itself.
                    Gl.glVertex3fv(TcData.ListToothGraphics.get___idx("implant").VertexNormals[group.Faces[i].IndexList[j]].Vertex.GetFloatArray());
                    //Vertices[group.Faces[i][j][0]]);
                    Gl.glNormal3fv(TcData.ListToothGraphics.get___idx("implant").VertexNormals[group.Faces[i].IndexList[j]].Normal.GetFloatArray());
                }
                //.Normals[group.Faces[i][j][1]]);
                Gl.glEnd();
            }
            Gl.glPopMatrix();
        }
         
    }

    private void drawOcclusalView(ToothGraphic toothGraphic) throws Exception {
        //now the occlusal surface. Notice that it's relative to origin again
        Gl.glPushMatrix();
        //remember position of origin
        Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYocclusal(toothGraphic.getToothID()), 0);
        if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
        {
            Gl.glRotatef(-110f, 1f, 0, 0);
        }
        else
        {
            //rotate angle about line from origin to x,y,z
            //mandibular
            if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
            {
                Gl.glRotatef(110f, 1f, 0, 0);
            }
            else
            {
                Gl.glRotatef(120f, 1f, 0, 0);
            } 
        } 
        rotateAndTranslateUser(toothGraphic);
        //if perm tooth
        if (!Tooth.isPrimary(toothGraphic.getToothID()) && Tooth.isValidDB(Tooth.permToPri(toothGraphic.getToothID())) && TcData.ListToothGraphics.get___idx(Tooth.permToPri(toothGraphic.getToothID())).getVisible())
        {
        }
        else //and the primary tooth is visible
        //do not paint
        //might not be visible if an implant
        if (toothGraphic.getVisible() || (toothGraphic.IsCrown && toothGraphic.IsImplant))
        {
            //a crown on an implant will paint
            //pontics won't paint, because tooth is invisible
            drawTooth(toothGraphic);
        }
          
        Gl.glPopMatrix();
        //reset to origin
        if (toothGraphic.getVisible() && toothGraphic.IsSealant)
        {
            //draw sealant
            Gl.glPushMatrix();
            Gl.glTranslatef(0, 0, 6f);
            //move forward 6mm so it will be visible.
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYocclusal(toothGraphic.getToothID()), 0);
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glRotatef(-110f, 1f, 0, 0);
            }
            else
            {
                //rotate angle about line from origin to x,y,z
                //mandibular
                if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
                {
                    Gl.glRotatef(110f, 1f, 0, 0);
                }
                else
                {
                    Gl.glRotatef(120f, 1f, 0, 0);
                } 
            } 
            Gl.glDisable(Gl.GL_LIGHTING);
            Gl.glEnable(Gl.GL_BLEND);
            Gl.glColor3f((float)toothGraphic.colorSealant.R / 255f, (float)toothGraphic.colorSealant.G / 255f, (float)toothGraphic.colorSealant.B / 255f);
            //.5f);//only 1/2 darkness
            Gl.glBlendFunc(Gl.GL_SRC_ALPHA, Gl.GL_ONE_MINUS_SRC_ALPHA);
            Gl.glLineWidth(2f * TcData.PixelScaleRatio);
            Gl.glPointSize(1.7f * TcData.PixelScaleRatio);
            //point is slightly smaller since no antialiasing
            rotateAndTranslateUser(toothGraphic);
            LineSimple line = toothGraphic.getSealantLine();
            Gl.glBegin(Gl.GL_LINE_STRIP);
            for (int j = 0;j < line.Vertices.Count;j++)
            {
                //loop through each vertex
                Gl.glVertex3f(line.Vertices[j].X, line.Vertices[j].Y, line.Vertices[j].Z);
            }
            Gl.glEnd();
            //The next 30 or so lines are all a stupid OpenGL workaround to hide the line intersections with big dots.
            Gl.glPopMatrix();
            //now, draw a point at each intersection to hide the unsightly transitions
            Gl.glPushMatrix();
            //move foward so it will cover the lines
            Gl.glTranslatef(0, 0, 6.5f);
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()), TcData.getTransYocclusal(toothGraphic.getToothID()), 0);
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glRotatef(-110f, 1f, 0, 0);
            }
            else
            {
                //rotate angle about line from origin to x,y,z
                //mandibular
                if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
                {
                    Gl.glRotatef(110f, 1f, 0, 0);
                }
                else
                {
                    Gl.glRotatef(120f, 1f, 0, 0);
                } 
            } 
            rotateAndTranslateUser(toothGraphic);
            Gl.glDisable(Gl.GL_BLEND);
            Gl.glBegin(Gl.GL_POINTS);
            for (int j = 0;j < line.Vertices.Count;j++)
            {
                //loop through each vertex
                //but ignore the first and last.  We are only concerned with where lines meet.
                if (j == 0 || j == line.Vertices.Count - 1)
                {
                    continue;
                }
                 
                Gl.glVertex3f(line.Vertices[j].X, line.Vertices[j].Y, line.Vertices[j].Z);
            }
            Gl.glEnd();
            Gl.glPopMatrix();
        }
         
    }

    private void drawWatches() throws Exception {
        Gl.glDisable(Gl.GL_LIGHTING);
        Gl.glDisable(Gl.GL_BLEND);
        Gl.glDisable(Gl.GL_DEPTH_TEST);
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
            renderToothWatch((ToothGraphic)toothGraphic.Value);
        }
    }

    private void renderToothWatch(ToothGraphic toothGraphic) throws Exception {
        Gl.glPushMatrix();
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()) + toothGraphic.ShiftM, 0, 0);
        }
        else
        {
            Gl.glTranslatef(TcData.getTransX(toothGraphic.getToothID()) - toothGraphic.ShiftM, 0, 0);
        } 
        float toMm = 1f / TcData.ScaleMmToPix;
        LineSimple line = toothGraphic.getWatchLine();
        Gl.glLineWidth(3f * TcData.PixelScaleRatio);
        Gl.glColor3f(1.0f, 1.0f, 1.0f);
        //White
        Gl.glBegin(Gl.GL_LINE_STRIP);
        for (int j = 0;j < line.Vertices.Count;j++)
        {
            //loop through each vertex and render the white W lines.
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (150f * toMm), line.Vertices[j].Z - 7f);
            }
            else
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (-140f * toMm), line.Vertices[j].Z - 7f);
            } 
        }
        Gl.glEnd();
        Gl.glLineWidth(0.6f * TcData.PixelScaleRatio);
        Gl.glColor3f((float)toothGraphic.colorWatch.R / 255f, (float)toothGraphic.colorWatch.G / 255f, (float)toothGraphic.colorWatch.B / 255f);
        Gl.glBegin(Gl.GL_LINE_STRIP);
        for (int j = 0;j < line.Vertices.Count;j++)
        {
            //loop through each vertex and render the colored W lines.
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (150f * toMm), line.Vertices[j].Z - 6f);
            }
            else
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (-140f * toMm), line.Vertices[j].Z - 6f);
            } 
        }
        Gl.glEnd();
        Gl.glDisable(Gl.GL_BLEND);
        Gl.glPointSize(2f * TcData.PixelScaleRatio);
        Gl.glColor3f(1.0f, 1.0f, 1.0f);
        //White
        Gl.glBegin(Gl.GL_POINTS);
        for (int j = 0;j < line.Vertices.Count;j++)
        {
            //loop through each vertex and render the colored W points.
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (150f * toMm), line.Vertices[j].Z + 7f);
            }
            else
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (-140f * toMm), line.Vertices[j].Z + 7f);
            } 
        }
        Gl.glEnd();
        Gl.glPointSize(0.4f * TcData.PixelScaleRatio);
        //point is slightly smaller since no antialiasing
        Gl.glColor3f((float)toothGraphic.colorWatch.R / 255f, (float)toothGraphic.colorWatch.G / 255f, (float)toothGraphic.colorWatch.B / 255f);
        Gl.glBegin(Gl.GL_POINTS);
        for (int j = 0;j < line.Vertices.Count;j++)
        {
            //loop through each vertex and render the colored W points.
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (150f * toMm), line.Vertices[j].Z + 6f);
            }
            else
            {
                Gl.glVertex3f(line.Vertices[j].X + TcData.PixelScaleRatio * (-6f * toMm), line.Vertices[j].Y + TcData.PixelScaleRatio * (-140f * toMm), line.Vertices[j].Z + 6f);
            } 
        }
        Gl.glEnd();
        Gl.glPopMatrix();
    }

    private void drawNumbersAndLines() throws Exception {
        Gl.glPushMatrix();
        Gl.glDisable(Gl.GL_LIGHTING);
        Gl.glDisable(Gl.GL_BLEND);
        Gl.glDisable(Gl.GL_DEPTH_TEST);
        Gl.glColor3f((float)TcData.ColorText.R / 255f, (float)TcData.ColorText.G / 255f, (float)TcData.ColorText.B / 255f);
        //this still doesn't seem to work quite right, but it's tolerable
        Gl.glLineWidth(1f * TcData.PixelScaleRatio);
        Gl.glBegin(Gl.GL_LINE_STRIP);
        //Gl.glVertex3f(-(float)WidthProjection/2f,0,0);
        //Gl.glVertex3f((float)WidthProjection/2f,0,0);
        Gl.glVertex3f(-TcData.SizeOriginalProjection.Width / 2f, 0, 0);
        Gl.glVertex3f(TcData.SizeOriginalProjection.Width / 2f, 0, 0);
        Gl.glEnd();
        String tooth_id = new String();
        for (int i = 1;i <= 52;i++)
        {
            tooth_id = Tooth.fromOrdinal(i);
            if (TcData.getSelectedTeeth().Contains(tooth_id))
            {
                drawNumber(tooth_id,true,true);
            }
            else
            {
                drawNumber(tooth_id,false,true);
            } 
        }
        Gl.glPopMatrix();
    }

    private void drawDrawingSegments() throws Exception {
        /*string[] pointStr;
        			List<Point> points;
        			Point point;
        			string[] xy;
        			Pen pen;
        			for(int s=0;s<TcData.DrawingSegmentList.Count;s++) {
        				pen=new Pen(TcData.DrawingSegmentList[s].ColorDraw,2f);
        				pointStr=TcData.DrawingSegmentList[s].DrawingSegment.Split(';');
        				points=new List<Point>();
        				for(int p=0;p<pointStr.Length;p++) {
        					xy=pointStr[p].Split(',');
        					if(xy.Length==2) {
        						point=new Point(int.Parse(xy[0]),int.Parse(xy[1]));
        						points.Add(point);
        					}
        				}
        				for(int i=1;i<points.Count;i++) {
        					//if we set 0,0 to center, then this is where we would convert it back.
        					gg.DrawLine(pen,points[i-1].X,
        						points[i-1].Y,
        						points[i].X,
        						points[i].Y);
        				}
        			}*/
        Gl.glPushMatrix();
        Gl.glDisable(Gl.GL_LIGHTING);
        Gl.glEnable(Gl.GL_BLEND);
        Gl.glBlendFunc(Gl.GL_SRC_ALPHA, Gl.GL_ONE_MINUS_SRC_ALPHA);
        Gl.glDisable(Gl.GL_DEPTH_TEST);
        //
        //
        Gl.glLineWidth(2.2f * TcData.PixelScaleRatio);
        Gl.glPointSize(1.8f * TcData.PixelScaleRatio);
        //slightly smaller
        String[] pointStr = new String[]();
        List<PointF> points = new List<PointF>();
        Point point = new Point();
        String[] xy = new String[]();
        PointF pointMm = new PointF();
        Color color = new Color();
        float scaleDrawing = (float)Width / (float)TcData.SizeOriginalDrawing.Width;
        for (int s = 0;s < TcData.DrawingSegmentList.Count;s++)
        {
            color = TcData.DrawingSegmentList[s].ColorDraw;
            Gl.glColor3f((float)color.R / 255f, (float)color.G / 255f, (float)color.B / 255f);
            pointStr = TcData.DrawingSegmentList[s].DrawingSegment.Split(';');
            points = new List<PointF>();
            for (int p = 0;p < pointStr.Length;p++)
            {
                xy = pointStr[p].Split(',');
                if (xy.Length == 2)
                {
                    point = new Point(Int32.Parse(xy[0]), Int32.Parse(xy[1]));
                    pointMm = TcData.pointDrawingPixToMm(point);
                    points.Add(pointMm);
                }
                 
            }
            Gl.glBegin(Gl.GL_LINE_STRIP);
            for (int i = 0;i < points.Count;i++)
            {
                //if we set 0,0 to center, then this is where we would convert it back.
                //pointMm=TcData.PixToMm(new Point(points[i].X,points[i].Y));
                //Gl.glVertex3f(pointMm.X,pointMm.Y,0);
                Gl.glVertex3f(points[i].X, points[i].Y, 0);
            }
            Gl.glEnd();
            //now draw a filled circle at each line strip intersection to make it look nicer when viewing fullscreen
            Gl.glBegin(Gl.GL_POINTS);
            for (int i = 0;i < points.Count;i++)
            {
                //but ignore the first and last.  We are only concerned with where lines meet.
                if (i == 0 || i == points.Count - 1)
                {
                    continue;
                }
                 
                Gl.glVertex3f(points[i].X, points[i].Y, 0);
            }
            Gl.glEnd();
        }
        Gl.glPopMatrix();
    }

    /**
    * Draws the number and the small rectangle behind it.  Draws in the appropriate color.  isFullRedraw means that all of the toothnumbers are being redrawn.  This helps with a few optimizations and with not painting blank rectangles when not needed.
    */
    private void drawNumber(String tooth_id, boolean isSelected, boolean isFullRedraw) throws Exception {
        /*if(!Tooth.IsValidDB(tooth_id)) {
        				return;
        			}
        			if(isFullRedraw) {//if redrawing all numbers
        				if(TcData.ListToothGraphics[tooth_id].HideNumber) {//and this is a "hidden" number
        					return;//skip
        				}
        				if(Tooth.IsPrimary(tooth_id)
        					&& !TcData.ListToothGraphics[Tooth.PriToPerm(tooth_id)].ShowPrimaryLetter)//but not set to show primary letters
        				{
        					return;
        				}
        			}
        			//fix this.  No calls to OpenDentBusiness that require database.
        			//string displayNum=OpenDentBusiness.Tooth.GetToothLabelGraphic(tooth_id);
        			string displayNum=tooth_id;
        			float toMm=1f/TcData.ScaleMmToPix;
        			Rectangle rec=TcData.GetNumberRecPix(tooth_id,g);
        			//Rectangle recPix=TcData.ConvertRecToPix(recMm);
        			if(isSelected) {
        				gg.FillRectangle(new SolidBrush(TcData.ColorBackHighlight),rec);
        			}
        			else {
        				gg.FillRectangle(new SolidBrush(TcData.ColorBackground),rec);
        			}
        			if(TcData.ListToothGraphics[tooth_id].HideNumber) {//If number is hidden.
        				//do not print string
        			}
        			else if(Tooth.IsPrimary(tooth_id)
        				&& !TcData.ListToothGraphics[Tooth.PriToPerm(tooth_id)].ShowPrimaryLetter) {
        				//do not print string
        			}
        			else if(isSelected) {
        				gg.DrawString(displayNum,Font,new SolidBrush(TcData.ColorTextHighlight),rec.X,rec.Y);
        			}
        			else {
        				gg.DrawString(displayNum,Font,new SolidBrush(TcData.ColorText),rec.X,rec.Y);
        			}*/
        if (!Tooth.isValidDB(tooth_id))
        {
            return ;
        }
         
        Gl.glPushMatrix();
        Gl.glDisable(Gl.GL_LIGHTING);
        Gl.glDisable(Gl.GL_BLEND);
        Gl.glDisable(Gl.GL_DEPTH_TEST);
        //string tooth_id=intTooth.ToString();
        //if(ToothGraphic.IsValidToothID(ToothGraphic.PermToPri(intTooth.ToString()))//pri is valid
        //	&& TcData.ListToothGraphics[ToothGraphic.PermToPri(intTooth.ToString())].Visible)//and pri visible
        //{
        //	tooth_id=ToothGraphic.PermToPri(intTooth.ToString());
        //}
        if (isFullRedraw)
        {
            //if redrawing all numbers
            if (TcData.ListToothGraphics.get___idx(tooth_id).getHideNumber())
            {
                //and this is a "hidden" number
                Gl.glPopMatrix();
                return ;
            }
             
            //skip
            if (Tooth.isPrimary(tooth_id) && !TcData.ListToothGraphics.get___idx(Tooth.priToPerm(tooth_id)).ShowPrimaryLetter)
            {
                //but not set to show primary letters
                Gl.glPopMatrix();
                return ;
            }
             
        }
         
        String displayNum = OpenDentBusiness.Tooth.getToothLabelGraphic(tooth_id,TcData.ToothNumberingNomenclature);
        //string displayNum=tooth_id;
        float toMm = 1f / TcData.ScaleMmToPix;
        //float toMm=(float)WidthProjection/(float)Width;//mm/pix, a ratio that is used for conversions below. Fix this.
        //float strWidthMm=MeasureStringMm(displayNum);
        SizeF labelSizeF = measureStringMm(displayNum);
        // g.MeasureString(displayNum,Font).Width/TcData.ScaleMmToPix;
        //SizeF labelSizeF
        RectangleF recMm = TcData.getNumberRecMm(tooth_id,labelSizeF);
        //Rectangle recPix=TcData.ConvertRecToPix(recMm);
        if (isSelected)
        {
            Gl.glColor3f((float)TcData.ColorBackHighlight.R / 255f, (float)TcData.ColorBackHighlight.G / 255f, (float)TcData.ColorBackHighlight.B / 255f);
            Gl.glBegin(Gl.GL_QUADS);
            Gl.glVertex3f(recMm.X, recMm.Y, 14);
            //LL
            Gl.glVertex3f(recMm.X, recMm.Y + recMm.Height, 14);
            //UL
            Gl.glVertex3f(recMm.X + recMm.Width, recMm.Y + recMm.Height, 14);
            //UR
            Gl.glVertex3f(recMm.X + recMm.Width, recMm.Y, 14);
            //LR
            Gl.glEnd();
            Gl.glColor3f((float)TcData.ColorTextHighlight.R / 255f, (float)TcData.ColorTextHighlight.G / 255f, (float)TcData.ColorTextHighlight.B / 255f);
        }
        else
        {
            //Gl.glRasterPos3f(recMm.X+2f*toMm,recMm.Y+2f*toMm,15f);
            Gl.glColor3f((float)TcData.ColorBackground.R / 255f, (float)TcData.ColorBackground.G / 255f, (float)TcData.ColorBackground.B / 255f);
            Gl.glBegin(Gl.GL_QUADS);
            Gl.glVertex3f(recMm.X, recMm.Y, 14);
            //LL
            Gl.glVertex3f(recMm.X, recMm.Y + recMm.Height, 14);
            //UL
            Gl.glVertex3f(recMm.X + recMm.Width, recMm.Y + recMm.Height, 14);
            //UR
            Gl.glVertex3f(recMm.X + recMm.Width, recMm.Y, 14);
            //LR
            Gl.glEnd();
            Gl.glColor3f((float)TcData.ColorText.R / 255f, (float)TcData.ColorText.G / 255f, (float)TcData.ColorText.B / 255f);
        } 
        //Gl.glRasterPos3f(recMm.X+2f*toMm,recMm.Y+2f*toMm,15f);
        if (TcData.ListToothGraphics.get___idx(tooth_id).getHideNumber())
        {
        }
        else //If number is hidden.
        //do not print string
        if (OpenDentBusiness.Tooth.isPrimary(tooth_id) && !TcData.ListToothGraphics.get___idx(OpenDentBusiness.Tooth.priToPerm(tooth_id)).ShowPrimaryLetter)
        {
        }
        else
        {
            //do not print string
            Gl.glRasterPos3f(recMm.X + 2f * toMm, recMm.Y + 2f * toMm, 15f);
            printString(displayNum);
        }  
        Gl.glPopMatrix();
        //todo: get rid of this?
        Gl.glFlush();
    }

    /*
    		///<summary>Return value is in tooth coordinates, not pixels.  I left this in OpenGL rather than moving it to ToothChartData because the measurement strategy is very specific to the raster font defined here.</summary>
    		private float MeasureStringPix(string text){
    			
    			return retVal;
    		}*/
    /**
    * I left this in OpenGL rather than moving it to ToothChartData because the measurement strategy is very specific to the raster font defined here.
    */
    private SizeF measureStringMm(String text) throws Exception {
        float pixelW = 0;
        for (int i = 0;i < text.Length;i++)
        {
            if (fontsymbols[(byte)text[i]] != null)
            {
                pixelW += fontsymbols[(byte)text[i]][0].Length + 1;
            }
             
        }
        pixelW += 2;
        return new SizeF(pixelW / TcData.ScaleMmToPix, 12f / TcData.ScaleMmToPix);
    }

    private void makeRasterFont() throws Exception {
        fontsymbols = new String[255];
        fontsymbols['+'] = new String[]{ "00000", "00000", "00100", "00100", "11111", "00100", "00100", "00000", "00000" };
        fontsymbols['-'] = new String[]{ "00000", "00000", "00000", "00000", "11111", "00000", "00000", "00000", "00000" };
        fontsymbols['A'] = new String[]{ "0001000", "0001000", "0010100", "0010100", "0100010", "0100010", "0111110", "1000001", "1000001" };
        //A
        fontsymbols['B'] = new String[]{ "11110", "10001", "10001", "10001", "11110", "10001", "10001", "10001", "11110" };
        //B
        fontsymbols['C'] = new String[]{ "011110", "100001", "100000", "100000", "100000", "100000", "100000", "100001", "011110" };
        //C
        fontsymbols['D'] = new String[]{ "111100", "100010", "100001", "100001", "100001", "100001", "100001", "100010", "111100" };
        //D
        fontsymbols['E'] = new String[]{ "11111", "10000", "10000", "10000", "11110", "10000", "10000", "10000", "11111" };
        //E
        fontsymbols['F'] = new String[]{ "11111", "10000", "10000", "10000", "11110", "10000", "10000", "10000", "10000" };
        //F
        fontsymbols['G'] = new String[]{ "011110", "100001", "100000", "100000", "100111", "100001", "100001", "100011", "011101" };
        //G
        fontsymbols['H'] = new String[]{ "100001", "100001", "100001", "100001", "111111", "100001", "100001", "100001", "100001" };
        //H
        fontsymbols['I'] = new String[]{ "111", "010", "010", "010", "010", "010", "010", "010", "111" };
        //I
        fontsymbols['J'] = new String[]{ "00001", "00001", "00001", "00001", "00001", "00001", "10001", "10001", "01110" };
        //J
        fontsymbols['K'] = new String[]{ "100001", "100010", "100100", "101000", "110000", "101000", "100100", "100010", "100001" };
        //K
        fontsymbols['L'] = new String[]{ "10000", "10000", "10000", "10000", "10000", "10000", "10000", "10000", "11111" };
        //L
        fontsymbols['M'] = new String[]{ "1000001", "1000001", "1100011", "1100011", "1010101", "1010101", "1001001", "1001001", "1000001" };
        //M
        fontsymbols['N'] = new String[]{ "100001", "110001", "110001", "101001", "101001", "100101", "100011", "100011", "100001" };
        //N
        fontsymbols['O'] = new String[]{ "011110", "100001", "100001", "100001", "100001", "100001", "100001", "100001", "011110" };
        //O
        fontsymbols['P'] = new String[]{ "111110", "100001", "100001", "100001", "111110", "100000", "100000", "100000", "100000" };
        //P
        fontsymbols['Q'] = new String[]{ "011110", "100001", "100001", "100001", "100001", "100001", "100101", "100010", "011101" };
        //Q
        fontsymbols['R'] = new String[]{ "111110", "100001", "100001", "100001", "111110", "100100", "100010", "100010", "100001" };
        //R
        fontsymbols['S'] = new String[]{ "011110", "100001", "100000", "100000", "011110", "000001", "000001", "100001", "011110" };
        //S
        fontsymbols['T'] = new String[]{ "1111111", "0001000", "0001000", "0001000", "0001000", "0001000", "0001000", "0001000", "0001000" };
        //T
        fontsymbols['0'] = new String[]{ "01110", "10001", "10001", "10001", "10001", "10001", "10001", "10001", "01110" };
        //0
        fontsymbols['1'] = new String[]{ "0010", "1110", "0010", "0010", "0010", "0010", "0010", "0010", "0010" };
        //1
        fontsymbols['2'] = new String[]{ "01110", "10001", "00001", "00001", "00010", "00100", "01000", "10000", "11111" };
        //2
        fontsymbols['3'] = new String[]{ "01110", "10001", "00001", "00001", "00110", "00001", "00001", "10001", "01110" };
        //3
        fontsymbols['4'] = new String[]{ "00010", "00110", "00110", "01010", "01010", "10010", "11111", "00010", "00010" };
        //4
        fontsymbols['5'] = new String[]{ "11111", "10000", "10000", "11110", "10001", "00001", "00001", "10001", "01110" };
        //5
        fontsymbols['6'] = new String[]{ "01110", "10001", "10000", "10000", "11110", "10001", "10001", "10001", "01110" };
        //6
        fontsymbols['7'] = new String[]{ "11111", "00001", "00010", "00010", "00100", "00100", "01000", "01000", "01000" };
        //7
        fontsymbols['8'] = new String[]{ "01110", "10001", "10001", "10001", "01110", "10001", "10001", "10001", "01110" };
        //8
        fontsymbols['9'] = new String[]{ "01110", "10001", "10001", "10001", "01111", "00001", "00001", "10001", "01110" };
        //9
        int i = new int();
        byte[] letter = new byte[]();
        Gl.glPixelStorei(Gl.GL_UNPACK_ALIGNMENT, 1);
        fontOffset = Gl.glGenLists(128);
        int letterW = new int();
        int letterH = new int();
        String row = new String();
        for (i = 0;i < fontsymbols.Length;i++)
        {
            if (fontsymbols[i] == null)
                continue;
             
            letterW = fontsymbols[i][0].Length;
            letterH = fontsymbols[i].Length;
            letter = new byte[letterH];
            for (int h = 0;h < letterH;h++)
            {
                //actually draws the letter from the bottom up.
                letter[h] = 0;
                row = fontsymbols[i][letterH - h - 1];
                for (int w = 0;w < letterW;w++)
                {
                    if (StringSupport.equals(row.Substring(w, 1), "1"))
                    {
                        letter[h] = (byte)(letter[h] | (byte)Math.Pow(2, 7 - w));
                    }
                     
                }
            }
            Gl.glNewList(fontOffset + i, Gl.GL_COMPILE);
            Gl.glBitmap(letterW, letterH, 0, 0, letterW + 1, 0, letter);
            Gl.glEndList();
        }
    }

    private void printString(String text) throws Exception {
        Gl.glListBase(fontOffset);
        byte[] textbytes = new byte[text.Length];
        for (int i = 0;i < text.Length;i++)
        {
            textbytes[i] = (byte)text[i];
        }
        try
        {
            Gl.glCallLists(text.Length, Gl.GL_UNSIGNED_BYTE, textbytes);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //Do nothing
    /**
    * Performs the rotations and translations entered by user for this tooth.  Usually, all numbers are just 0, resulting in no movement here.
    */
    private void rotateAndTranslateUser(ToothGraphic toothGraphic) throws Exception {
        //remembering that they actually show in the opposite order, so:
        //1: translate
        //2: tipM last
        //3: tipB second
        //4: rotate first
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UR
                Gl.glTranslatef(toothGraphic.ShiftM, -toothGraphic.ShiftO, toothGraphic.ShiftB);
                Gl.glRotatef(toothGraphic.TipM, 0, 0, 1f);
                Gl.glRotatef(-toothGraphic.TipB, 1f, 0, 0);
                Gl.glRotatef(toothGraphic.Rotate, 0, 1f, 0);
            }
            else
            {
                //LR
                Gl.glTranslatef(toothGraphic.ShiftM, toothGraphic.ShiftO, toothGraphic.ShiftB);
                Gl.glRotatef(-toothGraphic.TipM, 0, 0, 1f);
                Gl.glRotatef(toothGraphic.TipB, 1f, 0, 0);
                Gl.glRotatef(-toothGraphic.Rotate, 0, 1f, 0);
            } 
        }
        else
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UL
                Gl.glTranslatef(-toothGraphic.ShiftM, -toothGraphic.ShiftO, toothGraphic.ShiftB);
                Gl.glRotatef(-toothGraphic.TipM, 0, 0, 1f);
                Gl.glRotatef(-toothGraphic.TipB, 1f, 0, 0);
                Gl.glRotatef(toothGraphic.Rotate, 0, 1f, 0);
            }
            else
            {
                //LL
                Gl.glTranslatef(-toothGraphic.ShiftM, toothGraphic.ShiftO, toothGraphic.ShiftB);
                Gl.glRotatef(toothGraphic.TipM, 0, 0, 1f);
                Gl.glRotatef(toothGraphic.TipB, 1f, 0, 0);
                Gl.glRotatef(-toothGraphic.Rotate, 0, 1f, 0);
            } 
        } 
    }

    /**
    * 
    */
    private void drawTooth(ToothGraphic toothGraphic) throws Exception {
        Gl.glEnable(Gl.GL_LIGHTING);
        Gl.glEnable(Gl.GL_BLEND);
        Gl.glEnable(Gl.GL_DEPTH_TEST);
        ToothGroup group;
        float[] material_color = new float[]();
        for (int g = 0;g < toothGraphic.Groups.Count;g++)
        {
            group = (ToothGroup)toothGraphic.Groups[g];
            if (group.GroupType == ToothGroupType.Buildup)
            {
                continue;
            }
             
            if (group.GroupType == ToothGroupType.None)
            {
                continue;
            }
             
            if (!group.Visible)
            {
                continue;
            }
             
            //group.PaintColor=Color.FromArgb(255,255,253,209);//temp only for testing
            if (toothGraphic.ShiftO < -10)
            {
                //if unerupted
                material_color = new float[]{ (float)group.PaintColor.R / 255f / 2f, (float)group.PaintColor.G / 255f / 2f, (float)group.PaintColor.B / 255f / 2f, (float)group.PaintColor.A / 255f / 2f };
            }
            else
            {
                material_color = new float[]{ (float)group.PaintColor.R / 255f, (float)group.PaintColor.G / 255f, (float)group.PaintColor.B / 255f, (float)group.PaintColor.A / 255f };
            } 
            if (group.GroupType == ToothGroupType.Cementum)
            {
                Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_SPECULAR, specular_color_cementum);
            }
            else
            {
                Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_SPECULAR, specular_color_normal);
            } 
            Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_SHININESS, shininess);
            Gl.glMaterialfv(Gl.GL_FRONT, Gl.GL_AMBIENT_AND_DIFFUSE, material_color);
            Gl.glBlendFunc(Gl.GL_ONE, Gl.GL_ZERO);
            Gl.glHint(Gl.GL_POLYGON_SMOOTH_HINT, Gl.GL_NICEST);
            Gl.glListBase(displayListOffset);
            //draw the group
            Gl.glCallList(displayListOffset + toothGraphic.getIndexForDisplayList(group));
        }
    }

    /**
    * Only called once as part of initialization.
    */
    public void makeDisplayLists() throws Exception {
        //total number of display lists will be: (52 teeth) x (15 group types)=780. But 1-14 not used, and 780-794 are used.
        //520. But 1-9 not used, and 521-529 are used.
        //displayListOffset=Gl.glGenLists(530);//not sure if I did this right
        displayListOffset = Gl.glGenLists(795);
        ToothGraphic toothGraphic;
        ToothGroup group;
        for (int t = 1;t <= 52;t++)
        {
            if (t > 32 && t <= 42)
            {
                //33-42:  A-J = 4-13
                toothGraphic = TcData.ListToothGraphics.get___idx(OpenDentBusiness.Tooth.permToPri(t - 29));
            }
            else if (t > 42 && t <= 52)
            {
                //43-52:  K-T = 20-29
                toothGraphic = TcData.ListToothGraphics.get___idx(OpenDentBusiness.Tooth.permToPri(t - 23));
            }
            else
            {
                //perm
                toothGraphic = TcData.ListToothGraphics[t.ToString()];
            }  
            for (int g = 0;g < 15;g++)
            {
                //groups 0-14
                group = toothGraphic.getGroup(ToothGroupType.values()[g]);
                Gl.glNewList(displayListOffset + (t * 15) + g, Gl.GL_COMPILE);
                if (group != null)
                {
                    for (int f = 0;f < group.Faces.Count;f++)
                    {
                        //ToothGraphic.GetDisplayListNum(i.ToString())
                        //.GetLength(0);f++) {//loop through each face
                        Gl.glBegin(Gl.GL_POLYGON);
                        for (int j = 0;j < group.Faces[f].IndexList.Count;j++)
                        {
                            //.Length;j++) {//loop through each vertex
                            //The index for both will always be the same because we enforce a 1:1 relationship.
                            //We show grabbing a float[3], but we could just as easily use the index itself.
                            Gl.glNormal3fv(toothGraphic.VertexNormals[group.Faces[f].IndexList[j]].Normal.GetFloatArray());
                            Gl.glVertex3fv(toothGraphic.VertexNormals[group.Faces[f].IndexList[j]].Vertex.GetFloatArray());
                        }
                        //for(int v=0;v<group.Faces[f].VertexNormals.Count;v++){//  .Length;v++) {//loop through each vertex
                        //	Gl.glNormal3fv(toothGraphic.Normals[group.Faces[f][v][1]]);
                        //	Gl.glVertex3fv(toothGraphic.Vertices[group.Faces[f][v][0]]);
                        //}
                        Gl.glEnd();
                    }
                }
                 
                Gl.glEndList();
            }
        }
    }

    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        MouseIsDown = true;
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            String toothClicked = TcData.GetToothAtPoint(e.Location);
            //MessageBox.Show(toothClicked.ToString());
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
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            hotTooth = TcData.GetToothAtPoint(e.Location);
            if (StringSupport.equals(hotTooth, hotToothOld))
            {
                return ;
            }
             
            //mouse has not moved to another tooth
            if (MouseIsDown)
            {
                //drag action
                List<String> affectedTeeth = TcData.GetAffectedTeeth(hotToothOld, hotTooth, TcData.PointPixToMm(e.Location).Y);
                for (int i = 0;i < affectedTeeth.Count;i++)
                {
                    if (TcData.getSelectedTeeth().Contains(affectedTeeth[i]))
                    {
                        SetSelected(affectedTeeth[i], false);
                    }
                    else
                    {
                        SetSelected(affectedTeeth[i], true);
                    } 
                }
                hotToothOld = hotTooth;
            }
            else
            {
                //Invalidate();
                //Application.DoEvents();
                hotToothOld = hotTooth;
            } 
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            if (!MouseIsDown)
            {
                return ;
            }
             
            TcData.PointList.Add(new Point(e.X, e.Y));
            //just add the last line segment instead of redrawing the whole thing.
            /*
            				gg.SmoothingMode=SmoothingMode.HighQuality;
            				//g.CompositingMode=CompositingMode.SourceOver;
            			
            				Pen pen=new Pen(TcData.ColorDrawing,2f);
            				int i=TcData.PointList.Count-1;
            				gg.DrawLine(pen,TcData.PointList[i-1].X,TcData.PointList[i-1].Y,TcData.PointList[i].X,TcData.PointList[i].Y);
            				//g.DrawImage(bitmapInPictBox,0,0);*/
            Gl.glPushMatrix();
            Gl.glDisable(Gl.GL_LIGHTING);
            Gl.glEnable(Gl.GL_BLEND);
            Gl.glBlendFunc(Gl.GL_SRC_ALPHA, Gl.GL_ONE_MINUS_SRC_ALPHA);
            Gl.glDisable(Gl.GL_DEPTH_TEST);
            Gl.glColor3f((float)TcData.ColorDrawing.R / 255f, (float)TcData.ColorDrawing.G / 255f, (float)TcData.ColorDrawing.B / 255f);
            Gl.glLineWidth((float)Width / 220f);
            //300f);//about 2
            int i = TcData.PointList.Count - 1;
            Gl.glBegin(Gl.GL_LINE_STRIP);
            //if we set 0,0 to center, then this is where we would convert it back.
            PointF pointMm = TcData.PointPixToMm(TcData.PointList[i - 1]);
            Gl.glVertex3f(pointMm.X, pointMm.Y, 0);
            pointMm = TcData.PointPixToMm(TcData.PointList[i]);
            Gl.glVertex3f(pointMm.X, pointMm.Y, 0);
            Gl.glEnd();
            Gl.glPopMatrix();
            Gl.glFlush();
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
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
        else //Invalidate();
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
        //suspendRendering=true;
        TcData.setSelected(tooth_id,setValue);
        if (setValue)
        {
            drawNumber(tooth_id,true,false);
        }
        else
        {
            drawNumber(tooth_id,false,false);
        } 
        //g.DrawImage(bitmapInPictBox,0,0);
        //RectangleF recMm=TcData.GetNumberRecMm(tooth_id,);
        //Rectangle rec=TcData.ConvertRecToPix(recMm);
        Invalidate();
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
         
        //if(g!=null) {
        //	g.Dispose();
        //}
        super.dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ToothChartWrapper
        //
        this.Name = "GraphicalToothChart";
        this.Size = new System.Drawing.Size(544, 351);
        this.ResumeLayout(false);
    }

}
//rec);//but it invalidates the whole thing anyway.  Oh, well.//Application.DoEvents();//suspendRendering=false;

//rec);//but it invalidates the whole thing anyway.  Oh, well.//Application.DoEvents();//suspendRendering=false;