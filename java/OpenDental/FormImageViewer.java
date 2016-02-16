//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormImageViewer;
import OpenDental.Lan;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageSettingFlags;
import OpenDentBusiness.ImageStore;

/**
* Eventually, the user will be able to edit some image display settings and do a Documents.UpdateCur, but they can't actually make changes to the image.
*/
public class FormImageViewer  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.PictureBox PictureBox1 = new System.Windows.Forms.PictureBox();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.ImageList imageListTools = new System.Windows.Forms.ImageList();
    private Point MouseDownOrigin = new Point();
    private boolean MouseIsDown = false;
    private Document displayedDoc = null;
    /**
    * The offset of the image due to the grab tool. Used as a basis for calculating imageTranslation.
    */
    PointF imageLocation = new PointF(0, 0);
    /**
    * The true offset of the image in screen-space.
    */
    PointF imageTranslation = new PointF(0, 0);
    Bitmap backBuffer = null;
    Graphics backBuffGraph = null;
    Bitmap renderImage = null;
    private Bitmap ImageCurrent = null;
    ///<summary>The current zoom of the image. 1 implies normal size, <1 implies the image is shrunk, >1 imples the image is blown-up.</summary>
    float imageZoom = 1.0f;
    /**
    * The current amount. The ZoomLevel is 0 after an image is loaded. The image is zoomed a factor of (initial image zoom)*(2^ZoomLevel)
    */
    int zoomLevel = 0;
    /**
    * Represents the current factor for level of zoom from the initial zoom of the image. This is calculated directly as 2^ZoomLevel every time a zoom occurs. Recalculated from ZoomLevel each time, so that ZoomOverall always hits the exact same values for the exact same zoom levels (not loss of data).
    */
    float zoomFactor = 1;
    /**
    * 
    */
    public FormImageViewer() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
    }

    //even the title of this window is set externally, so no Lan.F is necessary
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
             
            if (backBuffGraph != null)
            {
                backBuffGraph.Dispose();
                backBuffGraph = null;
            }
             
            if (backBuffer != null)
            {
                backBuffer.Dispose();
                backBuffer = null;
            }
             
            if (renderImage != null)
            {
                renderImage.Dispose();
                renderImage = null;
            }
             
            if (ImageCurrent != null)
            {
                ImageCurrent.Dispose();
                ImageCurrent = null;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormImageViewer.class);
        this.imageListTools = new System.Windows.Forms.ImageList(this.components);
        this.PictureBox1 = new System.Windows.Forms.PictureBox();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        ((System.ComponentModel.ISupportInitialize)(this.PictureBox1)).BeginInit();
        this.SuspendLayout();
        //
        // imageListTools
        //
        this.imageListTools.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListTools.ImageStream")));
        this.imageListTools.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListTools.Images.SetKeyName(0, "");
        this.imageListTools.Images.SetKeyName(1, "");
        //
        // PictureBox1
        //
        this.PictureBox1.BackColor = System.Drawing.SystemColors.Window;
        this.PictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
        this.PictureBox1.Cursor = System.Windows.Forms.Cursors.Arrow;
        this.PictureBox1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.PictureBox1.Location = new System.Drawing.Point(0, 25);
        this.PictureBox1.Name = "PictureBox1";
        this.PictureBox1.Size = new System.Drawing.Size(903, 673);
        this.PictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
        this.PictureBox1.TabIndex = 12;
        this.PictureBox1.TabStop = false;
        this.PictureBox1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseDown);
        this.PictureBox1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseMove);
        this.PictureBox1.MouseUp += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseUp);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListTools);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(903, 25);
        this.ToolBarMain.TabIndex = 11;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormImageViewer
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(903, 698);
        this.Controls.Add(this.PictureBox1);
        this.Controls.Add(this.ToolBarMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormImageViewer";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Image Viewer";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormImageViewer_Load);
        this.Resize += new System.EventHandler(this.FormImageViewer_Resize);
        ((System.ComponentModel.ISupportInitialize)(this.PictureBox1)).EndInit();
        this.ResumeLayout(false);
    }

    private void formImageViewer_Load(Object sender, System.EventArgs e) throws Exception {
        layoutToolBar();
    }

    /**
    * This form will get the necessary images off disk so that it can control layout.
    */
    public void setImage(Document thisDocument, String displayTitle) throws Exception {
        //for now, the document is single. Later, it will get groups for composite images/mounts.
        Text = displayTitle;
        displayedDoc = thisDocument;
        List<long> docNums = new List<long>();
        docNums.Add(thisDocument.DocNum);
        String fileName = (String)Documents.GetPaths(docNums, ImageStore.getPreferredAtoZpath())[0];
        if (!File.Exists(fileName))
        {
            MessageBox.Show(fileName + " could not be found.");
            return ;
        }
         
        try
        {
            ImageCurrent = new Bitmap(fileName);
            //ContrDocs.ApplyDocumentSettingsToImage(thisDocument,ImageCurrent,
            renderImage = ImageHelper.applyDocumentSettingsToImage(thisDocument,ImageCurrent,ImageSettingFlags.CROP | ImageSettingFlags.COLORFUNCTION);
            if (renderImage == null)
            {
                imageZoom = 1;
                imageTranslation = new PointF(0, 0);
            }
            else
            {
                float matchWidth = PictureBox1.Width - 16;
                matchWidth = (matchWidth <= 0 ? 1 : matchWidth);
                float matchHeight = PictureBox1.Height - 16;
                matchHeight = (matchHeight <= 0 ? 1 : matchHeight);
                imageZoom = (float)Math.Min(matchWidth / renderImage.Width, matchHeight / renderImage.Height);
                imageTranslation = new PointF(PictureBox1.Width / 2.0f, PictureBox1.Height / 2.0f);
            } 
            zoomLevel = 0;
            zoomFactor = 1;
        }
        catch (System.Exception exception)
        {
            MessageBox.Show(Lan.g(this, exception.Message));
            ImageCurrent = null;
            renderImage = null;
        }

        updatePictureBox();
    }

    private void formImageViewer_Resize(Object sender, System.EventArgs e) throws Exception {
        if (backBuffGraph != null)
        {
            backBuffGraph.Dispose();
            backBuffGraph = null;
        }
         
        if (backBuffer != null)
        {
            backBuffer.Dispose();
            backBuffer = null;
        }
         
        int width = PictureBox1.Bounds.Width;
        int height = PictureBox1.Bounds.Height;
        if (width > 0 && height > 0)
        {
            backBuffer = new Bitmap(width, height);
            backBuffGraph = Graphics.FromImage(backBuffer);
        }
         
        PictureBox1.Image = backBuffer;
        updatePictureBox();
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        //ODToolBarButton button;
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 0, Lan.g(this,"Zoom In"), "ZoomIn"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, Lan.g(this,"Zoom Out"), "ZoomOut"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"White"), -1, Lan.g(this,"Clear screen to solid white"), "White"));
        ToolBarMain.Invalidate();
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("ZoomIn"))
        {
            onZoomIn_Click();
        }
        else if (__dummyScrutVar0.equals("ZoomOut"))
        {
            onZoomOut_Click();
        }
        else if (__dummyScrutVar0.equals("White"))
        {
            onWhite_Click();
        }
           
    }

    private void onZoomIn_Click() throws Exception {
        zoomLevel++;
        PointF c = new PointF(PictureBox1.ClientRectangle.Width / 2.0f, PictureBox1.ClientRectangle.Height / 2.0f);
        PointF p = new PointF(c.X - imageTranslation.X, c.Y - imageTranslation.Y);
        imageTranslation = new PointF(imageTranslation.X - p.X, imageTranslation.Y - p.Y);
        zoomFactor = (float)Math.Pow(2, zoomLevel);
        updatePictureBox();
    }

    private void onZoomOut_Click() throws Exception {
        zoomLevel--;
        PointF c = new PointF(PictureBox1.ClientRectangle.Width / 2.0f, PictureBox1.ClientRectangle.Height / 2.0f);
        PointF p = new PointF(c.X - imageTranslation.X, c.Y - imageTranslation.Y);
        imageTranslation = new PointF(imageTranslation.X + p.X / 2.0f, imageTranslation.Y + p.Y / 2.0f);
        zoomFactor = (float)Math.Pow(2, zoomLevel);
        updatePictureBox();
    }

    private void onWhite_Click() throws Exception {
        ImageCurrent = new Bitmap(1, 1);
        renderImage = new Bitmap(1, 1);
        updatePictureBox();
    }

    private void pictureBox1_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        MouseDownOrigin = new Point(e.X, e.Y);
        MouseIsDown = true;
        imageLocation = new PointF(imageTranslation.X, imageTranslation.Y);
        PictureBox1.Cursor = Cursors.Hand;
    }

    private void pictureBox1_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (MouseIsDown)
        {
            if (ImageCurrent != null)
            {
                imageTranslation = new PointF(imageLocation.X + (e.Location.X - MouseDownOrigin.X), imageLocation.Y + (e.Location.Y - MouseDownOrigin.Y));
                updatePictureBox();
            }
             
        }
         
    }

    private void pictureBox1_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        MouseIsDown = false;
        PictureBox1.Cursor = Cursors.Default;
    }

    private void updatePictureBox() throws Exception {
        try
        {
            backBuffGraph.Clear(Pens.White.Color);
            backBuffGraph.Transform = OpenDental.ContrImages.GetScreenMatrix(displayedDoc, ImageCurrent.Width, ImageCurrent.Height, imageZoom * zoomFactor, imageTranslation);
            backBuffGraph.DrawImage(renderImage, 0, 0);
            PictureBox1.Refresh();
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

}


//Not being able to render the image is non-fatal and probably due to a simple change in state or rounding errors.