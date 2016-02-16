//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CodeBase.ODMathLib;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.EZTwain;
import OpenDental.FormDocInfo;
import OpenDental.FormDocSign;
import OpenDental.FormMountEdit;
import OpenDental.ImageNodeId;
import OpenDental.ImageNodeType;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.ProgramL;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.EhrAmendments;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabImages;
import OpenDentBusiness.EobAttach;
import OpenDentBusiness.EobAttaches;
import OpenDentBusiness.EZTwainErrorCode;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageSettingFlags;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.Mount;
import OpenDentBusiness.MountItem;
import OpenDentBusiness.MountItems;
import OpenDentBusiness.Mounts;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.ToolBarsAvail;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
//#define ISXP
/**
* 
*/
public class ContrImages  extends System.Windows.Forms.UserControl 
{
    private System.Windows.Forms.ImageList imageListTree = new System.Windows.Forms.ImageList();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.ImageList imageListTools2 = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.TreeView treeDocuments = new System.Windows.Forms.TreeView();
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItem1 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuExit = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuPrefs = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.MenuItem menuItem2 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.ContextMenu contextTree = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItem3 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem4 = new System.Windows.Forms.MenuItem();
    private Panel panelNote = new Panel();
    private Label label1 = new Label();
    private TextBox textNote = new TextBox();
    private OpenDental.UI.SignatureBox sigBox;
    private Label label15 = new Label();
    private Label labelInvalidSig = new Label();
    private OpenDental.UI.ContrWindowingSlider sliderBrightnessContrast;
    private OpenDental.UI.ODToolBar ToolBarPaint;
    private Panel panelUnderline = new Panel();
    private Panel panelVertLine = new Panel();
    private System.Windows.Forms.PictureBox pictureBoxMain = new System.Windows.Forms.PictureBox();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    private ContextMenu menuForms = new ContextMenu();
    private ContextMenuStrip MountMenu = new ContextMenuStrip();
    /**
    * Used to display Topaz signatures on Windows. Is added dynamically to avoid native code references crashing MONO.
    */
    private Control SigBoxTopaz = new Control();
    /**
    * Starts out as false. It's only used when repainting the toolbar, not to test mode.
    */
    private boolean IsCropMode = new boolean();
    private Family FamCur;
    /**
    * When dragging on Picturebox, this is the starting point in PictureBox coordinates.
    */
    private Point PointMouseDown = new Point();
    private boolean IsMouseDown = new boolean();
    /**
    * Set to true when the image in the picture box is currently being translated.
    */
    private boolean IsDragging = new boolean();
    /**
    * In-memory copies of the images being viewed/edited. No changes are made to these images in memory, they are just kept resident to avoid having to reload the images from disk each time the screen needs to be redrawn.  If no mount, this will just be one image.  A mount will contain a series of images.
    */
    private Bitmap[] ImagesCur = new Bitmap[1];
    /**
    * Used as a basis for calculating image translations.
    */
    private PointF PointImageCur = new PointF();
    /**
    * The true offset of the document image or mount image.
    */
    private PointF PointTranslation = new PointF();
    /**
    * The current zoom of the currently loaded image/mount. 1 implies normal size, less than 1 implies the image is shrunk, greater than 1 imples the image/mount is blown-up.
    */
    private float ZoomImage = 1;
    /**
    * The zoom level is 0 after the current image/mount is loaded.  User changes the zoom in integer increments.  ZoomOverall is then (initial image/mount zoom)*(2^ZoomLevel).
    */
    private int ZoomLevel = 0;
    /**
    * Represents the current factor for level of zoom from the initial zoom of the currently loaded image/mount. This is calculated directly as 2^ZoomLevel every time a zoom occurs. Recalculated from ZoomLevel each time, so that ZoomOverall always hits the exact same values for the exact same zoom levels (no loss of data).
    */
    private float ZoomOverall = 1;
    /**
    * Used to prevent concurrent access to the current images by multiple threads.  Each item in array corresponds to an image in a mount.
    */
    private int[] WidthsImagesCur = new int[1];
    /**
    * Used to prevent concurrent access to the current images by multiple threads.  Each item in array corresponds to an image in a mount.
    */
    private int[] HeightsImagesCur = new int[1];
    /**
    * The image currently on the screen.  If a mount, this will be an image representing the entire mount.
    */
    private Bitmap ImageRenderingNow = null;
    private Rectangle RectCrop = new Rectangle(0, 0, -1, -1);
    /**
    * Used for performing an xRay image capture on an imaging device.
    */
    private SuniDeviceControl xRayImageController = null;
    /**
    * Thread to handle updating the graphical image to the screen when the current document is an image.
    */
    private Thread ThreadImageUpdate = null;
    private ImageSettingFlags ImageSettingFlagsInvalidated = ImageSettingFlags.NONE;
    /**
    * Used as a thread-safe communication device between the main and worker threads.
    */
    private EventWaitHandle EventWaitHandleSettings = new EventWaitHandle(false, EventResetMode.AutoReset);
    /**
    * Edited by the main thread to reflect selection changes. Read by worker thread.
    */
    private OpenDentBusiness.Document DocForSettings = null;
    //<summary>Keeps track of the mount settings for the currently selected mount document.</summary>
    //private Mount MountForSettings=new Mount();
    /**
    * Edited by the main thread to reflect selection changes. Read by worker thread.
    */
    private ImageNodeType NodeTypeForSettings = ImageNodeType.None;
    /**
    * Indicates which documents to update in the image worker thread. This variable must be locked before accessing it and it must also be the same length as DocsInMount at all times.
    */
    private boolean[] MountIdxsFlaggedForUpdate = null;
    /**
    * Set by the main thread and read by the image worker thread. Specifies which image processing tasks are to be performed by the worker thread.
    */
    private ImageSettingFlags ImageSettingFlagsForSettings = ImageSettingFlags.NONE;
    /**
    * Used to perform mouse selections in the treeDocuments list.
    */
    private ImageNodeId NodeIdentifierDown = new ImageNodeId();
    /**
    * Used to keep track of the old document selection by document number (the only guaranteed unique idenifier). This is to help the code be compatible with both Windows and MONO.
    */
    private ImageNodeId NodeIdentifierOld = new ImageNodeId();
    /**
    * Used for Invoke() calls in RenderCurrentImage() to safely handle multi-thread access to the picture box.
    */
    private static class __MultiRenderImageCallback   implements RenderImageCallback
    {
        public void invoke(OpenDentBusiness.Document docCopy, int originalWidth, int originalHeight, float zoom, PointF translation) throws Exception {
            IList<RenderImageCallback> copy = new IList<RenderImageCallback>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<RenderImageCallback>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                RenderImageCallback d = (RenderImageCallback)__dummyForeachVar0;
                d.invoke(docCopy, originalWidth, originalHeight, zoom, translation);
            }
        }

        private System.Collections.Generic.IList<RenderImageCallback> _invocationList = new ArrayList<RenderImageCallback>();
        public static RenderImageCallback combine(RenderImageCallback a, RenderImageCallback b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiRenderImageCallback ret = new __MultiRenderImageCallback();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static RenderImageCallback remove(RenderImageCallback a, RenderImageCallback b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<RenderImageCallback> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<RenderImageCallback> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiRenderImageCallback ret = new __MultiRenderImageCallback();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<RenderImageCallback> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    private static interface RenderImageCallback   
    {
        void invoke(OpenDentBusiness.Document docCopy, int originalWidth, int originalHeight, float zoom, PointF translation) throws Exception ;

        System.Collections.Generic.IList<RenderImageCallback> getInvocationList() throws Exception ;
    
    }

    /**
    * Used to safe-guard against multi-threading issues when an image capture is completed.
    */
    private static class __MultiCaptureCallback   implements CaptureCallback
    {
        public void invoke(Object sender, EventArgs e) throws Exception {
            IList<CaptureCallback> copy = new IList<CaptureCallback>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<CaptureCallback>(members);
            }
            for (Object __dummyForeachVar1 : copy)
            {
                CaptureCallback d = (CaptureCallback)__dummyForeachVar1;
                d.invoke(sender, e);
            }
        }

        private System.Collections.Generic.IList<CaptureCallback> _invocationList = new ArrayList<CaptureCallback>();
        public static CaptureCallback combine(CaptureCallback a, CaptureCallback b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiCaptureCallback ret = new __MultiCaptureCallback();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static CaptureCallback remove(CaptureCallback a, CaptureCallback b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<CaptureCallback> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<CaptureCallback> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiCaptureCallback ret = new __MultiCaptureCallback();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<CaptureCallback> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    private static interface CaptureCallback   
    {
        void invoke(Object sender, EventArgs e) throws Exception ;

        System.Collections.Generic.IList<CaptureCallback> getInvocationList() throws Exception ;
    
    }

    /**
    * Used to protect against multi-threading issues when refreshing a mount during an image capture.
    */
    private static class __MultiInvalidatesettingsCallback   implements InvalidatesettingsCallback
    {
        public void invoke(ImageSettingFlags settings, boolean reloadZoomTransCrop) throws Exception {
            IList<InvalidatesettingsCallback> copy = new IList<InvalidatesettingsCallback>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<InvalidatesettingsCallback>(members);
            }
            for (Object __dummyForeachVar2 : copy)
            {
                InvalidatesettingsCallback d = (InvalidatesettingsCallback)__dummyForeachVar2;
                d.invoke(settings, reloadZoomTransCrop);
            }
        }

        private System.Collections.Generic.IList<InvalidatesettingsCallback> _invocationList = new ArrayList<InvalidatesettingsCallback>();
        public static InvalidatesettingsCallback combine(InvalidatesettingsCallback a, InvalidatesettingsCallback b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiInvalidatesettingsCallback ret = new __MultiInvalidatesettingsCallback();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static InvalidatesettingsCallback remove(InvalidatesettingsCallback a, InvalidatesettingsCallback b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<InvalidatesettingsCallback> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<InvalidatesettingsCallback> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiInvalidatesettingsCallback ret = new __MultiInvalidatesettingsCallback();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<InvalidatesettingsCallback> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    private static interface InvalidatesettingsCallback   
    {
        void invoke(ImageSettingFlags settings, boolean reloadZoomTransCrop) throws Exception ;

        System.Collections.Generic.IList<InvalidatesettingsCallback> getInvocationList() throws Exception ;
    
    }

    /**
    * Keeps track of the document settings for the currently selected document or mount.
    */
    private OpenDentBusiness.Document DocSelected = new OpenDentBusiness.Document();
    /**
    * Keeps track of the currently selected mount object (only when a mount is selected).
    */
    private Mount MountSelected = new Mount();
    /**
    * If a mount is currently selected, this is the list of the mount items on it.
    */
    private List<MountItem> MountItemsForSelected = null;
    /**
    * The index of the currently selected item within a mount.
    */
    private int IdxSelectedInMount = 0;
    /**
    * List of documents within the currently selected mount (if any).
    */
    private OpenDentBusiness.Document[] DocsInMount = null;
    /**
    * The idxSelectedInMount when it is copied.
    */
    int IdxDocToCopy = -1;
    //private bool allowTopaz;
    DateTime TimeMouseMoved = new DateTime(1, 1, 1);
    /**
    * 
    */
    private Patient PatCur;
    private boolean InitializedOnStartup = new boolean();
    /**
    * Set with each module refresh, and that's where it's set if it doesn't yet exist.  For now, we are not using ImageStore.GetPatientFolder(), because we haven't tested whether it properly updates the patient object.  We don't want to risk using an outdated patient folder path.  And we don't want to waste time refreshing PatCur after every ImageStore.GetPatientFolder().
    */
    private String PatFolder = new String();
    private AxAcroPDFLib.AxAcroPDF axAcroPDF1 = null;
    private long PatNumPrev = 0;
    //private List<Def> DefListExpandedCats=new List<Def>();
    private List<long> ExpandedCats = new List<long>();
    /**
    * If this is not zero, then this indicates a different mode special for claimpayment.
    */
    private long ClaimPaymentNum = new long();
    /**
    * If this is not null, then this indicates a different mode special for amendments.
    */
    private EhrAmendment EhrAmendmentCur;
    /**
    * 
    */
    public EventHandler CloseClick = null;
    /**
    * 
    */
    public ContrImages() throws Exception {
        Logger.openlog.log("Initializing Document Module...",Severity.INFO);
        initializeComponent();
        //The context menu causes strange bugs in MONO when performing selections on the tree.
        //Perhaps when MONO is more developed we can remove this check.
        //Also, the SigPlusNet() object cannot be instantiated on 64-bit machines, because
        //the code for instantiation exists in a 32-bit native dll. Therefore, we have put
        //the creation code for the topaz box in CodeBase.TopazWrapper.GetTopaz() so that
        //the native code does not exist or get called anywhere in the program unless we are running on a
        //32-bit version of Windows.
        //bool is64=CodeBase.ODEnvironment.Is64BitOperatingSystem();
        boolean platformUnix = Environment.OSVersion.Platform == PlatformID.Unix;
        //allowTopaz=(!platformUnix && !is64);
        if (platformUnix)
        {
            treeDocuments.ContextMenu = null;
        }
         
        try
        {
            //if(allowTopaz){//Windows OS
            SigBoxTopaz = CodeBase.TopazWrapper.getTopaz();
            panelNote.Controls.Add(SigBoxTopaz);
            SigBoxTopaz.Location = sigBox.Location;
            //new System.Drawing.Point(437,15);
            SigBoxTopaz.Name = "sigBoxTopaz";
            SigBoxTopaz.Size = new System.Drawing.Size(362, 79);
            SigBoxTopaz.TabIndex = 93;
            SigBoxTopaz.Text = "sigPlusNET1";
            SigBoxTopaz.DoubleClick += new System.EventHandler(this.sigBoxTopaz_DoubleClick);
            CodeBase.TopazWrapper.setTopazState(SigBoxTopaz,0);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        //}
        //We always capture with a Suni device for now.
        //TODO: In the future use a device locator in the xImagingDeviceManager
        //project to return the appropriate general device control.
        xRayImageController = new SuniDeviceControl();
        this.xRayImageController.OnCaptureReady += new System.EventHandler(this.OnCaptureReady);
        this.xRayImageController.OnCaptureComplete += new System.EventHandler(this.OnCaptureComplete);
        this.xRayImageController.OnCaptureFinalize += new System.EventHandler(this.OnCaptureFinalize);
        Logger.openlog.log("Document Module initialization complete.",Severity.INFO);
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
             
            xRayImageController.KillXRayThread();
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(OpenDental.ContrImages.class);
        this.treeDocuments = new System.Windows.Forms.TreeView();
        this.contextTree = new System.Windows.Forms.ContextMenu();
        this.menuItem2 = new System.Windows.Forms.MenuItem();
        this.menuItem3 = new System.Windows.Forms.MenuItem();
        this.menuItem4 = new System.Windows.Forms.MenuItem();
        this.imageListTree = new System.Windows.Forms.ImageList(this.components);
        this.imageListTools2 = new System.Windows.Forms.ImageList(this.components);
        this.pictureBoxMain = new System.Windows.Forms.PictureBox();
        this.MountMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItem1 = new System.Windows.Forms.MenuItem();
        this.menuExit = new System.Windows.Forms.MenuItem();
        this.menuPrefs = new System.Windows.Forms.MenuItem();
        this.panelNote = new System.Windows.Forms.Panel();
        this.labelInvalidSig = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.panelUnderline = new System.Windows.Forms.Panel();
        this.panelVertLine = new System.Windows.Forms.Panel();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.ToolBarPaint = new OpenDental.UI.ODToolBar();
        this.sliderBrightnessContrast = new OpenDental.UI.ContrWindowingSlider();
        this.sigBox = new OpenDental.UI.SignatureBox();
        ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMain)).BeginInit();
        this.panelNote.SuspendLayout();
        this.SuspendLayout();
        //
        // treeDocuments
        //
        this.treeDocuments.ContextMenu = this.contextTree;
        this.treeDocuments.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.treeDocuments.FullRowSelect = true;
        this.treeDocuments.HideSelection = false;
        this.treeDocuments.ImageIndex = 2;
        this.treeDocuments.ImageList = this.imageListTree;
        this.treeDocuments.Indent = 20;
        this.treeDocuments.Location = new System.Drawing.Point(0, 29);
        this.treeDocuments.Name = "treeDocuments";
        this.treeDocuments.SelectedImageIndex = 2;
        this.treeDocuments.Size = new System.Drawing.Size(228, 519);
        this.treeDocuments.TabIndex = 0;
        this.treeDocuments.AfterCollapse += new System.Windows.Forms.TreeViewEventHandler(this.TreeDocuments_AfterCollapse);
        this.treeDocuments.AfterExpand += new System.Windows.Forms.TreeViewEventHandler(this.TreeDocuments_AfterExpand);
        this.treeDocuments.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.TreeDocuments_MouseDoubleClick);
        this.treeDocuments.MouseDown += new System.Windows.Forms.MouseEventHandler(this.TreeDocuments_MouseDown);
        this.treeDocuments.MouseLeave += new System.EventHandler(this.TreeDocuments_MouseLeave);
        this.treeDocuments.MouseMove += new System.Windows.Forms.MouseEventHandler(this.TreeDocuments_MouseMove);
        this.treeDocuments.MouseUp += new System.Windows.Forms.MouseEventHandler(this.TreeDocuments_MouseUp);
        //
        // contextTree
        //
        this.contextTree.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItem2, this.menuItem3, this.menuItem4 });
        //
        // menuItem2
        //
        this.menuItem2.Index = 0;
        this.menuItem2.Text = "Print";
        //
        // menuItem3
        //
        this.menuItem3.Index = 1;
        this.menuItem3.Text = "Delete";
        //
        // menuItem4
        //
        this.menuItem4.Index = 2;
        this.menuItem4.Text = "Info";
        //
        // imageListTree
        //
        this.imageListTree.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListTree.ImageStream")));
        this.imageListTree.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListTree.Images.SetKeyName(0, "");
        this.imageListTree.Images.SetKeyName(1, "");
        this.imageListTree.Images.SetKeyName(2, "");
        this.imageListTree.Images.SetKeyName(3, "");
        this.imageListTree.Images.SetKeyName(4, "");
        this.imageListTree.Images.SetKeyName(5, "");
        this.imageListTree.Images.SetKeyName(6, "");
        //
        // imageListTools2
        //
        this.imageListTools2.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListTools2.ImageStream")));
        this.imageListTools2.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListTools2.Images.SetKeyName(0, "Pat.gif");
        this.imageListTools2.Images.SetKeyName(1, "print.gif");
        this.imageListTools2.Images.SetKeyName(2, "deleteX.gif");
        this.imageListTools2.Images.SetKeyName(3, "info.gif");
        this.imageListTools2.Images.SetKeyName(4, "scan.gif");
        this.imageListTools2.Images.SetKeyName(5, "import.gif");
        this.imageListTools2.Images.SetKeyName(6, "paste.gif");
        this.imageListTools2.Images.SetKeyName(7, "");
        this.imageListTools2.Images.SetKeyName(8, "ZoomIn.gif");
        this.imageListTools2.Images.SetKeyName(9, "ZoomOut.gif");
        this.imageListTools2.Images.SetKeyName(10, "Hand.gif");
        this.imageListTools2.Images.SetKeyName(11, "flip.gif");
        this.imageListTools2.Images.SetKeyName(12, "rotateL.gif");
        this.imageListTools2.Images.SetKeyName(13, "rotateR.gif");
        this.imageListTools2.Images.SetKeyName(14, "scanDoc.gif");
        this.imageListTools2.Images.SetKeyName(15, "scanPhoto.gif");
        this.imageListTools2.Images.SetKeyName(16, "scanXray.gif");
        this.imageListTools2.Images.SetKeyName(17, "copy.gif");
        this.imageListTools2.Images.SetKeyName(18, "ScanMulti.gif");
        this.imageListTools2.Images.SetKeyName(19, "Export.gif");
        //
        // pictureBoxMain
        //
        this.pictureBoxMain.BackColor = System.Drawing.SystemColors.Window;
        this.pictureBoxMain.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
        this.pictureBoxMain.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
        this.pictureBoxMain.ContextMenuStrip = this.MountMenu;
        this.pictureBoxMain.Cursor = System.Windows.Forms.Cursors.Hand;
        this.pictureBoxMain.InitialImage = null;
        this.pictureBoxMain.Location = new System.Drawing.Point(233, 54);
        this.pictureBoxMain.Name = "pictureBoxMain";
        this.pictureBoxMain.Size = new System.Drawing.Size(703, 370);
        this.pictureBoxMain.TabIndex = 6;
        this.pictureBoxMain.TabStop = false;
        this.pictureBoxMain.SizeChanged += new System.EventHandler(this.PictureBox1_SizeChanged);
        this.pictureBoxMain.MouseDown += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseDown);
        this.pictureBoxMain.MouseHover += new System.EventHandler(this.PictureBox1_MouseHover);
        this.pictureBoxMain.MouseMove += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseMove);
        this.pictureBoxMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.PictureBox1_MouseUp);
        //
        // MountMenu
        //
        this.MountMenu.Name = "MountMenu";
        this.MountMenu.Size = new System.Drawing.Size(61, 4);
        this.MountMenu.Opening += new System.ComponentModel.CancelEventHandler(this.MountMenu_Opening);
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItem1, this.menuPrefs });
        //
        // menuItem1
        //
        this.menuItem1.Index = 0;
        this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuExit });
        this.menuItem1.Text = "File";
        //
        // menuExit
        //
        this.menuExit.Index = 0;
        this.menuExit.Text = "Exit";
        //
        // menuPrefs
        //
        this.menuPrefs.Index = 1;
        this.menuPrefs.Text = "Preferences";
        //
        // panelNote
        //
        this.panelNote.Controls.Add(this.labelInvalidSig);
        this.panelNote.Controls.Add(this.sigBox);
        this.panelNote.Controls.Add(this.label15);
        this.panelNote.Controls.Add(this.label1);
        this.panelNote.Controls.Add(this.textNote);
        this.panelNote.Location = new System.Drawing.Point(234, 485);
        this.panelNote.Name = "panelNote";
        this.panelNote.Size = new System.Drawing.Size(705, 64);
        this.panelNote.TabIndex = 11;
        this.panelNote.Visible = false;
        this.panelNote.DoubleClick += new System.EventHandler(this.panelNote_DoubleClick);
        //
        // labelInvalidSig
        //
        this.labelInvalidSig.BackColor = System.Drawing.SystemColors.Window;
        this.labelInvalidSig.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInvalidSig.Location = new System.Drawing.Point(398, 31);
        this.labelInvalidSig.Name = "labelInvalidSig";
        this.labelInvalidSig.Size = new System.Drawing.Size(196, 59);
        this.labelInvalidSig.TabIndex = 94;
        this.labelInvalidSig.Text = "Invalid Signature -  Document or note has changed since it was signed.";
        this.labelInvalidSig.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        this.labelInvalidSig.DoubleClick += new System.EventHandler(this.labelInvalidSig_DoubleClick);
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(305, 0);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(63, 18);
        this.label15.TabIndex = 87;
        this.label15.Text = "Signature";
        this.label15.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.label15.DoubleClick += new System.EventHandler(this.label15_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(0, 0);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(38, 18);
        this.label1.TabIndex = 1;
        this.label1.Text = "Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.label1.DoubleClick += new System.EventHandler(this.label1_DoubleClick);
        //
        // textNote
        //
        this.textNote.BackColor = System.Drawing.SystemColors.Window;
        this.textNote.Location = new System.Drawing.Point(0, 20);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ReadOnly = true;
        this.textNote.Size = new System.Drawing.Size(302, 79);
        this.textNote.TabIndex = 0;
        this.textNote.DoubleClick += new System.EventHandler(this.textNote_DoubleClick);
        this.textNote.MouseHover += new System.EventHandler(this.textNote_MouseHover);
        //
        // panelUnderline
        //
        this.panelUnderline.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panelUnderline.Location = new System.Drawing.Point(236, 48);
        this.panelUnderline.Name = "panelUnderline";
        this.panelUnderline.Size = new System.Drawing.Size(702, 2);
        this.panelUnderline.TabIndex = 15;
        //
        // panelVertLine
        //
        this.panelVertLine.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panelVertLine.Location = new System.Drawing.Point(233, 25);
        this.panelVertLine.Name = "panelVertLine";
        this.panelVertLine.Size = new System.Drawing.Size(2, 25);
        this.panelVertLine.TabIndex = 16;
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListTools2);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(939, 25);
        this.ToolBarMain.TabIndex = 10;
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
        // ToolBarPaint
        //
        this.ToolBarPaint.setImageList(this.imageListTools2);
        this.ToolBarPaint.Location = new System.Drawing.Point(440, 24);
        this.ToolBarPaint.Name = "ToolBarPaint";
        this.ToolBarPaint.Size = new System.Drawing.Size(499, 25);
        this.ToolBarPaint.TabIndex = 14;
        this.ToolBarPaint.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarPaint.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.paintTools_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // sliderBrightnessContrast
        //
        this.sliderBrightnessContrast.Enabled = false;
        this.sliderBrightnessContrast.Location = new System.Drawing.Point(240, 32);
        this.sliderBrightnessContrast.setMaxVal(255);
        this.sliderBrightnessContrast.setMinVal(0);
        this.sliderBrightnessContrast.Name = "sliderBrightnessContrast";
        this.sliderBrightnessContrast.Size = new System.Drawing.Size(194, 14);
        this.sliderBrightnessContrast.TabIndex = 12;
        this.sliderBrightnessContrast.Text = "contrWindowingSlider1";
        this.sliderBrightnessContrast.Scroll += new System.EventHandler(this.brightnessContrastSlider_Scroll);
        this.sliderBrightnessContrast.ScrollComplete += new System.EventHandler(this.brightnessContrastSlider_ScrollComplete);
        //
        // sigBox
        //
        this.sigBox.Location = new System.Drawing.Point(308, 20);
        this.sigBox.Name = "sigBox";
        this.sigBox.Size = new System.Drawing.Size(362, 79);
        this.sigBox.TabIndex = 90;
        this.sigBox.DoubleClick += new System.EventHandler(this.sigBox_DoubleClick);
        //
        // ContrImages
        //
        this.Controls.Add(this.panelVertLine);
        this.Controls.Add(this.panelUnderline);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.ToolBarPaint);
        this.Controls.Add(this.sliderBrightnessContrast);
        this.Controls.Add(this.panelNote);
        this.Controls.Add(this.pictureBoxMain);
        this.Controls.Add(this.treeDocuments);
        this.Name = "ContrImages";
        this.Size = new System.Drawing.Size(939, 585);
        this.Resize += new System.EventHandler(this.ContrImages_Resize);
        ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMain)).EndInit();
        this.panelNote.ResumeLayout(false);
        this.panelNote.PerformLayout();
        this.ResumeLayout(false);
    }

    private void contrImages_Resize(Object sender, EventArgs e) throws Exception {
        resizeAll();
    }

    /**
    * Resizes all controls in the image module to fit inside the current window, including controls which have varying visibility.
    */
    private void resizeAll() throws Exception {
        treeDocuments.Height = Height - treeDocuments.Top - 2;
        pictureBoxMain.Width = Width - pictureBoxMain.Left - 4;
        panelNote.Width = pictureBoxMain.Width;
        panelNote.Height = (int)Math.Min(114, Height - pictureBoxMain.Location.Y);
        int panelNoteHeight = (panelNote.Visible ? panelNote.Height : 0);
        pictureBoxMain.Height = Height - panelNoteHeight - pictureBoxMain.Top;
        if (axAcroPDF1 != null)
        {
            axAcroPDF1.Location = pictureBoxMain.Location;
            axAcroPDF1.Width = pictureBoxMain.Width;
            axAcroPDF1.Height = pictureBoxMain.Height;
        }
         
        panelNote.Location = new Point(pictureBoxMain.Left, Height - panelNoteHeight - 1);
        if (ClaimPaymentNum != 0 || EhrAmendmentCur != null)
        {
            //eob or amendment
            ToolBarPaint.Location = new Point(pictureBoxMain.Left, ToolBarPaint.Top);
        }
        else
        {
            //ordinary images module
            ToolBarPaint.Location = new Point(sliderBrightnessContrast.Location.X + sliderBrightnessContrast.Width + 4, ToolBarPaint.Location.Y);
        } 
        ToolBarPaint.Size = new Size(pictureBoxMain.Width - sliderBrightnessContrast.Width - 4, ToolBarPaint.Height);
        panelUnderline.Location = new Point(pictureBoxMain.Location.X, panelUnderline.Location.Y);
        panelUnderline.Width = Width - panelUnderline.Location.X;
    }

    /**
    * Sets the panelnote visibility based on the given document's signature data and the current operating system.
    */
    private void setPanelNoteVisibility(OpenDentBusiness.Document doc) throws Exception {
        panelNote.Visible = (doc != null) && (((doc.Note != null && !StringSupport.equals(doc.Note, "")) || (doc.Signature != null && !StringSupport.equals(doc.Signature, ""))) && (Environment.OSVersion.Platform != PlatformID.Unix || !doc.SigIsTopaz));
    }

    /**
    * Also does LayoutToolBar.
    */
    public void initializeOnStartup() throws Exception {
        if (InitializedOnStartup)
        {
            return ;
        }
         
        InitializedOnStartup = true;
        PointMouseDown = new Point();
        Lan.C(this, new System.Windows.Forms.Control[]{  });
        //this.button1,
        layoutToolBar();
        contextTree.MenuItems.Clear();
        contextTree.MenuItems.Add("Print", new System.EventHandler(menuTree_Click));
        contextTree.MenuItems.Add("Delete", new System.EventHandler(menuTree_Click));
        if (ClaimPaymentNum == 0)
        {
            contextTree.MenuItems.Add("Info", new System.EventHandler(menuTree_Click));
        }
         
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarPaint.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, Lan.g(this,"Print"), "Print"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 2, Lan.g(this,"Delete"), "Delete"));
        if (ClaimPaymentNum == 0)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 3, Lan.g(this,"Item Info"), "Info"));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Sign"), -1, Lan.g(this,"Sign this document"), "Sign"));
        }
         
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Scan:"), -1, "", "");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 14, Lan.g(this,"Scan Document"), "ScanDoc"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 18, Lan.g(this,"Scan Multi-Page Document"), "ScanMultiDoc"));
        if (ClaimPaymentNum == 0)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 16, Lan.g(this,"Scan Radiograph"), "ScanXRay"));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 15, Lan.g(this,"Scan Photo"), "ScanPhoto"));
        }
         
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Import"), 5, Lan.g(this,"Import From File"), "Import"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Export"), 19, Lan.g(this,"Export To File"), "Export"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Copy"), 17, Lan.g(this,"Copy displayed image to clipboard"), "Copy"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Paste"), 6, Lan.g(this,"Paste From Clipboard"), "Paste"));
        if (ClaimPaymentNum == 0)
        {
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Templates"), -1, "", "Forms");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            menuForms = new ContextMenu();
            String formDir = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"Forms");
            if (Directory.Exists(formDir))
            {
                DirectoryInfo dirInfo = new DirectoryInfo(formDir);
                FileInfo[] fileInfos = dirInfo.GetFiles();
                for (int i = 0;i < fileInfos.Length;i++)
                {
                    if (Documents.IsAcceptableFileName(fileInfos[i].FullName))
                    {
                        menuForms.MenuItems.Add(fileInfos[i].Name, new System.EventHandler(menuForms_Click));
                    }
                     
                }
            }
             
            button.setDropDownMenu(menuForms);
            ToolBarMain.getButtons().add(button);
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Capture"), -1, "Capture Image From Device", "Capture");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
            ToolBarMain.getButtons().add(button);
            //Program links:
            ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.ImagesModule);
            for (int i = 0;i < toolButItems.Count;i++)
            {
                ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
                ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
            }
        }
        else
        {
            //claimpayment
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, Lan.g(this,"Close window"), "Close"));
        } 
        if (ClaimPaymentNum == 0)
        {
            button = new OpenDental.UI.ODToolBarButton("", 7, Lan.g(this,"Crop Tool"), "Crop");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
            button.setPushed(IsCropMode);
            ToolBarPaint.getButtons().add(button);
            button = new OpenDental.UI.ODToolBarButton("", 10, Lan.g(this,"Hand Tool"), "Hand");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
            button.setPushed(!IsCropMode);
            ToolBarPaint.getButtons().add(button);
            ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        }
         
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 8, Lan.g(this,"Zoom In"), "ZoomIn"));
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 9, Lan.g(this,"Zoom Out"), "ZoomOut"));
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("100", -1, Lan.g(this,"Zoom 100"), "Zoom100"));
        if (ClaimPaymentNum == 0)
        {
            ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Rotate:"), -1, "", "");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
            ToolBarPaint.getButtons().add(button);
            ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 11, Lan.g(this,"Flip"), "Flip"));
            ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 12, Lan.g(this,"Rotate Left"), "RotateL"));
            ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 13, Lan.g(this,"Rotate Right"), "RotateR"));
        }
         
        ToolBarMain.Invalidate();
        ToolBarPaint.Invalidate();
        Plugins.hookAddCode(this,"ContrDocs.LayoutToolBar_end",PatCur);
    }

    /**
    * Toolbar Layout for Amendments
    */
    public void layoutAmendmentToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarPaint.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, Lan.g(this,"Print"), "Print"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 2, Lan.g(this,"Delete"), "Delete"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Scan:"), -1, "", "");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 14, Lan.g(this,"Scan Document"), "ScanDoc"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 18, Lan.g(this,"Scan Multi-Page Document"), "ScanMultiDoc"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Import"), 5, Lan.g(this,"Import From File"), "Import"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Export"), 19, Lan.g(this,"Export To File"), "Export"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Copy"), 17, Lan.g(this,"Copy displayed image to clipboard"), "Copy"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Paste"), 6, Lan.g(this,"Paste From Clipboard"), "Paste"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, Lan.g(this,"Close window"), "Close"));
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 8, Lan.g(this,"Zoom In"), "ZoomIn"));
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("", 9, Lan.g(this,"Zoom Out"), "ZoomOut"));
        ToolBarPaint.getButtons().add(new OpenDental.UI.ODToolBarButton("100", -1, Lan.g(this,"Zoom 100"), "Zoom100"));
        ToolBarMain.Invalidate();
        ToolBarPaint.Invalidate();
    }

    /**
    * One of two overloads.
    */
    public void moduleSelected(long patNum) throws Exception {
        ModuleSelected(patNum, 0);
    }

    /**
    * This overload is needed when jumping to a specific image from FormPatientForms.
    */
    public void moduleSelected(long patNum, long docNum) throws Exception {
        refreshModuleData(patNum);
        refreshModuleScreen();
        if (docNum != 0)
        {
            selectTreeNode(getNodeById(makeIdDoc(docNum)));
        }
         
        Plugins.hookAddCode(this,"ContrImages.ModuleSelected_end",patNum,docNum);
    }

    /**
    * This overload is for batch claim payment (EOB) images.
    */
    public void moduleSelectedClaimPayment(long claimPaymentNum) throws Exception {
        ClaimPaymentNum = claimPaymentNum;
        layoutToolBar();
        //again
        sliderBrightnessContrast.Visible = false;
        //ToolBarPaint.Location=new Point(pictureBoxMain.Left,ToolBarPaint.Top);//happens in ResizeAll().
        resizeAll();
        //RefreshModuleData-----------------------------------------------------------------------
        selectTreeNode(null);
        //Clear selection and image and reset visibilities.
        //PatFolder=ImageStore.GetPatientFolder(PatCur);//This is where the pat folder gets created if it does not yet exist.
        //RefreshModuleScreen---------------------------------------------------------------------
        enableAllTools(true);
        enableAllTreeItemTools(false);
        ToolBarMain.Invalidate();
        ToolBarPaint.Invalidate();
        fillDocList(false);
        if (treeDocuments.Nodes.Count > 0)
        {
            SelectTreeNode(treeDocuments.Nodes[0]);
        }
         
    }

    //SelectTreeNode(GetNodeById(MakeIdentifier(docNum.ToString(),"0")));
    /**
    * This overload is for amendment images.  Loads the one image for this amendment.
    */
    public void moduleSelectedAmendment(EhrAmendment amendment) throws Exception {
        EhrAmendmentCur = amendment;
        layoutAmendmentToolBar();
        sliderBrightnessContrast.Visible = false;
        //ToolBarPaint.Location=new Point(pictureBoxMain.Left,ToolBarPaint.Top);//happens in ResizeAll().
        resizeAll();
        //RefreshModuleData-----------------------------------------------------------------------
        selectTreeNode(null);
        //Clear selection and image and reset visibilities.
        //PatFolder=ImageStore.GetPatientFolder(PatCur);//This is where the pat folder gets created if it does not yet exist.
        //RefreshModuleScreen---------------------------------------------------------------------
        enableAllTools(true);
        enableAllTreeItemTools(false);
        ToolBarMain.Invalidate();
        ToolBarPaint.Invalidate();
        fillDocList(false);
        if (treeDocuments.Nodes.Count > 0)
        {
            SelectTreeNode(treeDocuments.Nodes[0]);
        }
         
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        FamCur = null;
        //Cancel current image capture.
        xRayImageController.KillXRayThread();
        Plugins.hookAddCode(this,"ContrImages.ModuleUnselected_end");
    }

    /**
    * 
    */
    private void refreshModuleData(long patNum) throws Exception {
        selectTreeNode(null);
        //Clear selection and image and reset visibilities.
        if (patNum == 0)
        {
            FamCur = null;
            return ;
        }
         
        FamCur = Patients.getFamily(patNum);
        PatCur = FamCur.getPatient(patNum);
        PatFolder = ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath());
        //This is where the pat folder gets created if it does not yet exist.
        ImageStore.addMissingFilesToDatabase(PatCur);
    }

    private void refreshModuleScreen() throws Exception {
        if (this.Enabled && PatCur != null)
        {
            //Enable tools which must always be accessible when a valid patient is selected.
            enableAllTools(true);
            //Item specific tools disabled until item chosen.
            enableAllTreeItemTools(false);
        }
        else
        {
            enableAllTools(false);
        } 
        //Disable entire menu (besides select patient).
        ToolBarMain.Invalidate();
        ToolBarPaint.Invalidate();
        fillDocList(false);
    }

    /**
    * 
    */
    private void onPatientSelected(Patient pat) throws Exception {
        OpenDental.PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
        if (PatientSelected != null)
        {
            PatientSelected.invoke(this,eArgs);
        }
         
    }

    /**
    * Applies to all tools.
    */
    private void enableAllTools(boolean enable) throws Exception {
        for (int i = 0;i < ToolBarMain.getButtons().Count;i++)
        {
            ToolBarMain.getButtons().get___idx(i).setEnabled(enable);
        }
        if (ToolBarMain.getButtons().get___idx("Capture") != null)
        {
            ToolBarMain.getButtons().get___idx("Capture").setEnabled((ToolBarMain.getButtons().get___idx("Capture").getEnabled() && Environment.OSVersion.Platform != PlatformID.Unix));
        }
         
        ToolBarMain.Invalidate();
        for (int i = 0;i < ToolBarPaint.getButtons().Count;i++)
        {
            ToolBarPaint.getButtons().get___idx(i).setEnabled(enable);
        }
        ToolBarPaint.Enabled = enable;
        ToolBarPaint.Invalidate();
        sliderBrightnessContrast.Enabled = enable;
        sliderBrightnessContrast.Invalidate();
    }

    /**
    * Defined this way to force future programming to consider which tools are enabled and disabled for every possible tool in the menu.
    */
    private void enableTreeItemTools(boolean print, boolean delete, boolean info, boolean copy, boolean sign, boolean brightAndContrast, boolean crop, boolean hand, boolean zoomIn, boolean zoomOut, boolean flip, boolean rotateL, boolean rotateR, boolean export) throws Exception {
        ToolBarMain.getButtons().get___idx("Print").setEnabled(print);
        ToolBarMain.getButtons().get___idx("Delete").setEnabled(delete);
        if (ToolBarMain.getButtons().get___idx("Info") != null)
        {
            ToolBarMain.getButtons().get___idx("Info").setEnabled(info);
        }
         
        ToolBarMain.getButtons().get___idx("Copy").setEnabled(copy);
        if (ToolBarMain.getButtons().get___idx("Sign") != null)
        {
            ToolBarMain.getButtons().get___idx("Sign").setEnabled(sign);
        }
         
        ToolBarMain.getButtons().get___idx("Export").setEnabled(export);
        ToolBarMain.Invalidate();
        if (ToolBarPaint.getButtons().get___idx("") != null)
        {
            ToolBarPaint.getButtons().get___idx("Crop").setEnabled(crop);
        }
         
        if (ToolBarPaint.getButtons().get___idx("Hand") != null)
        {
            ToolBarPaint.getButtons().get___idx("Hand").setEnabled(hand);
        }
         
        ToolBarPaint.getButtons().get___idx("ZoomIn").setEnabled(zoomIn);
        ToolBarPaint.getButtons().get___idx("ZoomOut").setEnabled(zoomOut);
        ToolBarPaint.getButtons().get___idx("Zoom100").setEnabled(zoomOut);
        if (ToolBarPaint.getButtons().get___idx("Flip") != null)
        {
            ToolBarPaint.getButtons().get___idx("Flip").setEnabled(flip);
        }
         
        if (ToolBarPaint.getButtons().get___idx("RotateR") != null)
        {
            ToolBarPaint.getButtons().get___idx("RotateR").setEnabled(rotateR);
        }
         
        if (ToolBarPaint.getButtons().get___idx("RotateL") != null)
        {
            ToolBarPaint.getButtons().get___idx("RotateL").setEnabled(rotateL);
        }
         
        //Enabled if one tool inside is enabled.
        ToolBarPaint.Enabled = (brightAndContrast || crop || hand || zoomIn || zoomOut || flip || rotateL || rotateR);
        ToolBarPaint.Invalidate();
        sliderBrightnessContrast.Enabled = brightAndContrast;
        sliderBrightnessContrast.Invalidate();
    }

    private void enableAllTreeItemTools(boolean enable) throws Exception {
        enableTreeItemTools(enable,enable,enable,enable,enable,enable,enable,enable,enable,enable,enable,enable,enable,enable);
    }

    /**
    * Selection doesn't only happen by the tree and mouse clicks, but can also happen by automatic processes, such as image import, image paste, etc...
    */
    private void selectTreeNode(TreeNode node) throws Exception {
        //Select the node always, but perform additional tasks when necessary (i.e. load an image, or mount).
        treeDocuments.SelectedNode = node;
        treeDocuments.Invalidate();
        //Clear the copy document number for mount item swapping whenever a new mount is potentially selected.
        IdxDocToCopy = -1;
        //We only perform a load if the new selection is different than the old selection.
        ImageNodeId nodeId = new ImageNodeId();
        if (node != null)
        {
            nodeId = (ImageNodeId)node.Tag;
        }
         
        if (nodeId.Equals(NodeIdentifierOld))
        {
            return ;
        }
         
        pictureBoxMain.Visible = true;
        if (axAcroPDF1 != null)
        {
            axAcroPDF1.Dispose();
        }
         
        //Clear any previously loaded Acrobat .pdf file.
        DocSelected = new OpenDentBusiness.Document();
        NodeIdentifierOld = nodeId;
        //Disable all item tools until the currently selected node is loaded properly in the picture box.
        enableAllTreeItemTools(false);
        if (ToolBarPaint.getButtons().get___idx("Hand") != null)
        {
            ToolBarPaint.getButtons().get___idx("Hand").setPushed(true);
        }
         
        if (ToolBarPaint.getButtons().get___idx("Crop") != null)
        {
            ToolBarPaint.getButtons().get___idx("Crop").setPushed(false);
        }
         
        //Stop any current image processing. This will avoid having the ImageRenderingNow set to a valid image after
        //the current image has been erased. This will also avoid concurrent access to the the currently loaded images by
        //the main and worker threads.
        eraseCurrentImages();
        if (nodeId.NodeType == ImageNodeType.Category)
        {
            //A folder was selected (or unselection, but I am not sure unselection would be possible here).
            //The panel note control is made invisible to start and then made visible for the appropriate documents. This
            //line prevents the possibility of showing a signature box after selecting a folder node.
            panelNote.Visible = false;
            //Make sure the controls are sized properly in the image module since the visibility of the panel note might
            //have just changed.
            resizeAll();
        }
        else if (nodeId.NodeType == ImageNodeType.Eob)
        {
            EobAttach eob = EobAttaches.getOne(nodeId.PriKey);
            ImagesCur = ImageStore.openImagesEob(eob);
            if (ImagesCur[0] == null)
            {
                if (ImageHelper.hasImageExtension(eob.FileName))
                {
                    MessageBox.Show(Lan.g(this,"File not found: ") + eob.FileName);
                }
                else if (StringSupport.equals(Path.GetExtension(eob.FileName).ToLower(), ".pdf"))
                {
                    try
                    {
                        //Adobe acrobat file.
                        axAcroPDF1 = new AxAcroPDFLib.AxAcroPDF();
                        this.Controls.Add(axAcroPDF1);
                        axAcroPDF1.Visible = true;
                        axAcroPDF1.Size = pictureBoxMain.Size;
                        axAcroPDF1.Location = pictureBoxMain.Location;
                        axAcroPDF1.OnError += new EventHandler(pdfFileError);
                        String pdfFilePath = CodeBase.ODFileUtils.combinePaths(ImageStore.getEobFolder(),eob.FileName);
                        if (!File.Exists(pdfFilePath))
                        {
                            MessageBox.Show(Lan.g(this,"File not found: ") + eob.FileName);
                        }
                        else
                        {
                            axAcroPDF1.LoadFile(pdfFilePath);
                            //The return status of this function doesn't seem to be helpful.
                            pictureBoxMain.Visible = false;
                        } 
                    }
                    catch (Exception __dummyCatchVar1)
                    {
                    }
                
                }
                  
            }
             
            //An exception can happen if they do not have Adobe Acrobat Reader version 8.0 or later installed.
            //Simply ignore this exception and do nothing. We never used to display .pdf files anyway, so we
            //essentially revert back to the old behavior in this case.
            //return;?
            EnableTreeItemTools(pictureBoxMain.Visible, true, true, pictureBoxMain.Visible, true, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible);
        }
        else if (nodeId.NodeType == ImageNodeType.Doc)
        {
            //Reload the doc from the db. We don't just keep reusing the tree data, because it will become more and
            //more stale with age if the program is left open in the image module for long periods of time.
            DocSelected = Documents.getByNum(nodeId.PriKey);
            IdxSelectedInMount = 0;
            ImagesCur = ImageStore.OpenImages(new OpenDentBusiness.Document[]{ DocSelected }, PatFolder);
            boolean isExportable = pictureBoxMain.Visible;
            if (ImagesCur[0] == null)
            {
                if (ImageHelper.hasImageExtension(DocSelected.FileName))
                {
                    MessageBox.Show(Lan.g(this,"File not found: ") + DocSelected.FileName);
                }
                else if (StringSupport.equals(Path.GetExtension(DocSelected.FileName).ToLower(), ".pdf"))
                {
                    try
                    {
                        //Adobe acrobat file.
                        axAcroPDF1 = new AxAcroPDFLib.AxAcroPDF();
                        this.Controls.Add(axAcroPDF1);
                        axAcroPDF1.Visible = true;
                        axAcroPDF1.Size = pictureBoxMain.Size;
                        axAcroPDF1.Location = pictureBoxMain.Location;
                        axAcroPDF1.OnError += new EventHandler(pdfFileError);
                        String pdfFilePath = CodeBase.ODFileUtils.combinePaths(PatFolder,DocSelected.FileName);
                        if (!File.Exists(pdfFilePath))
                        {
                            MessageBox.Show(Lan.g(this,"File not found: ") + DocSelected.FileName);
                        }
                        else
                        {
                            axAcroPDF1.LoadFile(pdfFilePath);
                            //The return status of this function doesn't seem to be helpful.
                            pictureBoxMain.Visible = false;
                            isExportable = true;
                        } 
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                    }
                
                }
                  
            }
             
            //An exception can happen if they do not have Adobe Acrobat Reader version 8.0 or later installed.
            //Simply ignore this exception and do nothing. We never used to display .pdf files anyway, so we
            //essentially revert back to the old behavior in this case.
            setBrightnessAndContrast();
            EnableTreeItemTools(pictureBoxMain.Visible, true, true, pictureBoxMain.Visible, true, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, isExportable);
        }
        else if (nodeId.NodeType == ImageNodeType.Mount)
        {
            //Creates a complete initial mount image. No need to call invalidate until changes are made to the mount later.
            MountItemsForSelected = MountItems.getItemsForMount(nodeId.PriKey);
            DocsInMount = Documents.getDocumentsForMountItems(MountItemsForSelected);
            IdxSelectedInMount = -1;
            //No selection to start.
            ImagesCur = ImageStore.openImages(DocsInMount,PatFolder);
            MountSelected = Mounts.getByNum(nodeId.PriKey);
            ImageRenderingNow = new Bitmap(MountSelected.Width, MountSelected.Height);
            ImageHelper.renderMountImage(ImageRenderingNow,ImagesCur,MountItemsForSelected,DocsInMount,IdxSelectedInMount);
            enableTreeItemTools(true,true,true,true,false,false,false,true,true,true,false,false,false,true);
        }
        else if (nodeId.NodeType == ImageNodeType.Amd)
        {
            EhrAmendment amd = EhrAmendments.getOne(nodeId.PriKey);
            ImagesCur = ImageStore.openImagesAmd(amd);
            if (ImagesCur[0] == null)
            {
                if (ImageHelper.hasImageExtension(amd.FileName))
                {
                    MessageBox.Show(Lan.g(this,"File not found: ") + amd.FileName);
                }
                else if (StringSupport.equals(Path.GetExtension(amd.FileName).ToLower(), ".pdf"))
                {
                    try
                    {
                        //Adobe acrobat file.
                        axAcroPDF1 = new AxAcroPDFLib.AxAcroPDF();
                        this.Controls.Add(axAcroPDF1);
                        axAcroPDF1.Visible = true;
                        axAcroPDF1.Size = pictureBoxMain.Size;
                        axAcroPDF1.Location = pictureBoxMain.Location;
                        axAcroPDF1.OnError += new EventHandler(pdfFileError);
                        String pdfFilePath = CodeBase.ODFileUtils.combinePaths(ImageStore.getAmdFolder(),amd.FileName);
                        if (!File.Exists(pdfFilePath))
                        {
                            MessageBox.Show(Lan.g(this,"File not found: ") + amd.FileName);
                        }
                        else
                        {
                            axAcroPDF1.LoadFile(pdfFilePath);
                            //The return status of this function doesn't seem to be helpful.
                            pictureBoxMain.Visible = false;
                        } 
                    }
                    catch (Exception __dummyCatchVar3)
                    {
                    }
                
                }
                  
            }
             
            //An exception can happen if they do not have Adobe Acrobat Reader version 8.0 or later installed.
            //Simply ignore this exception and do nothing. We never used to display .pdf files anyway, so we
            //essentially revert back to the old behavior in this case.
            //return;?
            EnableTreeItemTools(pictureBoxMain.Visible, true, true, pictureBoxMain.Visible, true, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible, pictureBoxMain.Visible);
        }
             
        if (nodeId.NodeType == ImageNodeType.Doc || nodeId.NodeType == ImageNodeType.Mount || nodeId.NodeType == ImageNodeType.Eob || nodeId.NodeType == ImageNodeType.Amd)
        {
            WidthsImagesCur = new int[ImagesCur.Length];
            HeightsImagesCur = new int[ImagesCur.Length];
            for (int i = 0;i < ImagesCur.Length;i++)
            {
                if (ImagesCur[i] != null)
                {
                    WidthsImagesCur[i] = ImagesCur[i].Width;
                    HeightsImagesCur[i] = ImagesCur[i].Height;
                }
                 
            }
            //Adjust visibility of panel note control based on if the new document has a signature.
            setPanelNoteVisibility(DocSelected);
            //Resize controls in our window to adjust for a possible change in the visibility of the panel note control.
            resizeAll();
            //Refresh the signature and note in case the last document also had a signature.
            fillSignature();
        }
         
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            RefSupport refVar___0 = new RefSupport();
            RefSupport refVar___1 = new RefSupport();
            RefSupport refVar___2 = new RefSupport();
            RefSupport refVar___3 = new RefSupport();
            ReloadZoomTransCrop(ImageRenderingNow.Width, ImageRenderingNow.Height, new OpenDentBusiness.Document(), new Rectangle(0, 0, pictureBoxMain.Width, pictureBoxMain.Height), refVar___0, refVar___1, refVar___2, refVar___3);
            ZoomImage = refVar___0.getValue();
            ZoomLevel = refVar___1.getValue();
            ZoomOverall = refVar___2.getValue();
            PointTranslation = refVar___3.getValue();
            RenderCurrentImage(new OpenDentBusiness.Document(), ImageRenderingNow.Width, ImageRenderingNow.Height, ZoomImage, PointTranslation);
        }
         
        if (nodeId.NodeType == ImageNodeType.Doc || nodeId.NodeType == ImageNodeType.Eob || nodeId.NodeType == ImageNodeType.Amd)
        {
            //Render the initial image within the current bounds of the picturebox (if the document is an image).
            invalidateSettings(ImageSettingFlags.ALL,true);
        }
         
    }

    private void pdfFileError(Object sender, System.EventArgs e) throws Exception {
        pictureBoxMain.Visible = true;
        ToolBarPaint.Enabled = true;
        sliderBrightnessContrast.Enabled = true;
        axAcroPDF1.Visible = false;
    }

    /**
    * Gets the category folder name for the given document node.
    */
    private String getCurrentFolderName(TreeNode node) throws Exception {
        if (node != null)
        {
            while (node.Parent != null)
            {
                //Find the corresponding root level node.
                node = node.Parent;
            }
            return node.Text;
        }
         
        //We must always return a category if one is available, so that new documents can be properly added.
        if (treeDocuments.Nodes.Count > 0)
        {
            return treeDocuments.Nodes[0].Text;
        }
         
        return "";
    }

    /**
    * Gets the document category of the current selection. The current selection can be a folder itself, or a document within a folder.
    */
    private long getCurrentCategory() throws Exception {
        return DefC.GetByExactName(DefCat.ImageCats, GetCurrentFolderName(treeDocuments.SelectedNode));
    }

    /**
    * Returns the current tree node with the given node id.
    */
    private TreeNode getNodeById(ImageNodeId nodeId) throws Exception {
        return GetNodeById(nodeId, treeDocuments.Nodes);
    }

    //This defines the root node.
    /**
    * Searches the current object tree for a row which has the given unique document number. This will work for a tree with any number of nested folders, as long as tags are defined only for items which correspond to data rows.
    */
    private TreeNode getNodeById(ImageNodeId nodeId, TreeNodeCollection rootNodes) throws Exception {
        if (rootNodes == null)
        {
            return null;
        }
         
        for (Object __dummyForeachVar3 : rootNodes)
        {
            TreeNode node = (TreeNode)__dummyForeachVar3;
            if (node == null)
            {
                continue;
            }
             
            if (((ImageNodeId)node.Tag).Equals(nodeId))
            {
                return node;
            }
             
            //Check the child nodes.
            TreeNode child = GetNodeById(nodeId, node.Nodes);
            if (child != null)
            {
                return child;
            }
             
        }
        return null;
    }

    /**
    * 
    */
    private ImageNodeId makeIdDoc(long docNum) throws Exception {
        ImageNodeId nodeId = new ImageNodeId();
        nodeId.NodeType = ImageNodeType.Doc;
        nodeId.PriKey = docNum;
        return nodeId;
    }

    //return docNum+"*"+mountNum;
    /**
    * 
    */
    private ImageNodeId makeIdMount(long mountNum) throws Exception {
        ImageNodeId nodeId = new ImageNodeId();
        nodeId.NodeType = ImageNodeType.Mount;
        nodeId.PriKey = mountNum;
        return nodeId;
    }

    /**
    * 
    */
    private ImageNodeId makeIdDef(long defNum) throws Exception {
        ImageNodeId nodeId = new ImageNodeId();
        nodeId.NodeType = ImageNodeType.Category;
        nodeId.PriKey = defNum;
        return nodeId;
    }

    /**
    * 
    */
    private ImageNodeId makeIdEob(long eobAttachNum) throws Exception {
        ImageNodeId nodeId = new ImageNodeId();
        nodeId.NodeType = ImageNodeType.Eob;
        nodeId.PriKey = eobAttachNum;
        return nodeId;
    }

    /**
    * 
    */
    private ImageNodeId makeIdAmd(long ehrAmendmentNum) throws Exception {
        ImageNodeId nodeId = new ImageNodeId();
        nodeId.NodeType = ImageNodeType.Amd;
        nodeId.PriKey = ehrAmendmentNum;
        return nodeId;
    }

    /**
    * Refreshes list from db, then fills the treeview.  Set keepSelection to true in order to keep the current selection active.
    */
    private void fillDocList(boolean keepSelection) throws Exception {
        ImageNodeId nodeIdSelection = new ImageNodeId();
        if (keepSelection && treeDocuments.SelectedNode != null)
        {
            nodeIdSelection = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        }
         
        //(keepSelection?GetNodeIdentifier(treeDocuments.SelectedNode):"");
        //Clear current tree contents.
        treeDocuments.SelectedNode = null;
        treeDocuments.Nodes.Clear();
        if (ClaimPaymentNum != 0)
        {
            List<EobAttach> listEobs = EobAttaches.refresh(ClaimPaymentNum);
            for (int i = 0;i < listEobs.Count;i++)
            {
                TreeNode node = new TreeNode(listEobs[i].FileName);
                node.Tag = MakeIdEob(listEobs[i].EobAttachNum);
                node.ImageIndex = 2;
                node.SelectedImageIndex = node.ImageIndex;
                //redundant?
                treeDocuments.Nodes.Add(node);
                if (((ImageNodeId)node.Tag).Equals(nodeIdSelection))
                {
                    selectTreeNode(node);
                }
                 
            }
            return ;
        }
        else if (EhrAmendmentCur != null)
        {
            if (EhrAmendmentCur.FileName != null && !StringSupport.equals(EhrAmendmentCur.FileName, ""))
            {
                TreeNode node = new TreeNode(EhrAmendmentCur.FileName);
                node.Tag = makeIdAmd(EhrAmendmentCur.EhrAmendmentNum);
                node.ImageIndex = 2;
                node.SelectedImageIndex = node.ImageIndex;
                //redundant?
                treeDocuments.Nodes.Add(node);
                if (((ImageNodeId)node.Tag).Equals(nodeIdSelection))
                {
                    selectTreeNode(node);
                }
                 
            }
             
            return ;
        }
          
        //the rest of this is for normal images module-------------------------------------------------------------------------------------------------
        if (PatCur == null)
        {
            return ;
        }
         
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()].Length;i++)
        {
            //Add all predefined folder names to the tree.
            treeDocuments.Nodes.Add(new TreeNode(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].ItemName));
            treeDocuments.Nodes[i].Tag = MakeIdDef(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].DefNum);
            treeDocuments.Nodes[i].SelectedImageIndex = 1;
            treeDocuments.Nodes[i].ImageIndex = 1;
        }
        //Add all relevant documents and mounts as stored in the database to the tree for the current patient.
        DataSet ds = Documents.RefreshForPatient(new String[]{ PatCur.PatNum.ToString() });
        DataRowCollection rows = ds.Tables["DocumentList"].Rows;
        for (int i = 0;i < rows.Count;i++)
        {
            TreeNode node = new TreeNode(rows[i]["description"].ToString());
            int parentFolder = PIn.Int(rows[i]["docFolder"].ToString());
            treeDocuments.Nodes[parentFolder].Nodes.Add(node);
            if (StringSupport.equals(rows[i]["DocNum"].ToString(), "0"))
            {
                //must be a mount
                node.Tag = MakeIdMount(PIn.Long(rows[i]["MountNum"].ToString()));
            }
            else
            {
                //doc
                node.Tag = MakeIdDoc(PIn.Long(rows[i]["DocNum"].ToString()));
            } 
            node.ImageIndex = 2 + Convert.ToInt32(rows[i]["ImgType"].ToString());
            node.SelectedImageIndex = node.ImageIndex;
            if (((ImageNodeId)node.Tag).Equals(nodeIdSelection))
            {
                selectTreeNode(node);
            }
             
        }
        if (PrefC.getBool(PrefName.ImagesModuleTreeIsCollapsed))
        {
            TreeNode selectedNode = treeDocuments.SelectedNode;
            //Save the selection so we can reselect after collapsing.
            treeDocuments.CollapseAll();
            //Invalidates tree and clears selection too.
            treeDocuments.SelectedNode = selectedNode;
            //This will expand any category/folder nodes necessary to show the selection.
            if (PatNumPrev == PatCur.PatNum)
            {
                for (int i = 0;i < ExpandedCats.Count;i++)
                {
                    for (int j = 0;j < treeDocuments.Nodes.Count;j++)
                    {
                        //Maintain previously expanded nodes when patient not changed.
                        if (ExpandedCats[i] == ((ImageNodeId)treeDocuments.Nodes[j].Tag).PriKey)
                        {
                            treeDocuments.Nodes[j].Expand();
                            break;
                        }
                         
                    }
                }
            }
            else
            {
                //Patient changed.
                ExpandedCats.Clear();
            } 
            PatNumPrev = PatCur.PatNum;
        }
        else
        {
            treeDocuments.ExpandAll();
        } 
    }

    //Invalidates tree too.
    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
            if (__dummyScrutVar0.equals("Print"))
            {
                toolBarPrint_Click();
            }
            else if (__dummyScrutVar0.equals("Delete"))
            {
                toolBarDelete_Click();
            }
            else if (__dummyScrutVar0.equals("Info"))
            {
                toolBarInfo_Click();
            }
            else if (__dummyScrutVar0.equals("Sign"))
            {
                toolBarSign_Click();
            }
            else if (__dummyScrutVar0.equals("ScanDoc"))
            {
                toolBarScan_Click("doc");
            }
            else if (__dummyScrutVar0.equals("ScanMultiDoc"))
            {
                toolBarScanMulti_Click();
            }
            else if (__dummyScrutVar0.equals("ScanXRay"))
            {
                toolBarScan_Click("xray");
            }
            else if (__dummyScrutVar0.equals("ScanPhoto"))
            {
                toolBarScan_Click("photo");
            }
            else if (__dummyScrutVar0.equals("Import"))
            {
                toolBarImport_Click();
            }
            else if (__dummyScrutVar0.equals("Export"))
            {
                toolBarExport_Click();
            }
            else if (__dummyScrutVar0.equals("Copy"))
            {
                toolBarCopy_Click();
            }
            else if (__dummyScrutVar0.equals("Paste"))
            {
                toolBarPaste_Click();
            }
            else if (__dummyScrutVar0.equals("Forms"))
            {
                MsgBox.show(this,"Use the dropdown list.  Add forms to the list by copying image files into your A-Z folder, Forms.  Restart the program to see newly added forms.");
            }
            else if (__dummyScrutVar0.equals("Capture"))
            {
                toolBarCapture_Click();
            }
            else if (__dummyScrutVar0.equals("Close"))
            {
                toolBarClose_Click();
            }
                           
        }
        else if (e.getButton().getTag().GetType() == long.class)
        {
            ProgramL.execute((long)e.getButton().getTag(),PatCur);
        }
          
    }

    private void paintTools_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            Object.APPLY __dummyScrutVar1 = e.getButton().getTag().ToString();
            if (__dummyScrutVar1.equals("Crop"))
            {
                toolBarCrop_Click();
            }
            else if (__dummyScrutVar1.equals("Hand"))
            {
                toolBarHand_Click();
            }
            else if (__dummyScrutVar1.equals("ZoomIn"))
            {
                toolBarZoomIn_Click();
            }
            else if (__dummyScrutVar1.equals("ZoomOut"))
            {
                toolBarZoomOut_Click();
            }
            else if (__dummyScrutVar1.equals("Zoom100"))
            {
                toolBarZoom100_Click();
            }
            else if (__dummyScrutVar1.equals("Flip"))
            {
                toolBarFlip_Click();
            }
            else if (__dummyScrutVar1.equals("RotateL"))
            {
                toolBarRotateL_Click();
            }
            else if (__dummyScrutVar1.equals("RotateR"))
            {
                toolBarRotateR_Click();
            }
                    
        }
        else if (e.getButton().getTag().GetType() == int.class)
        {
            ProgramL.Execute((int)e.getButton().getTag(), PatCur);
        }
          
    }

    private void toolBarPrint_Click() throws Exception {
        if (treeDocuments.SelectedNode == null || treeDocuments.SelectedNode.Tag == null)
        {
            MsgBox.show(this,"Cannot print. No document currently selected.");
            return ;
        }
         
        try
        {
            PrintDocument pd = new PrintDocument();
            pd.PrintPage += new PrintPageEventHandler(printDocument_PrintPage);
            PrintDialog dlg = new PrintDialog();
            dlg.AllowCurrentPage = false;
            dlg.AllowPrintToFile = true;
            dlg.AllowSelection = false;
            dlg.AllowSomePages = false;
            dlg.Document = pd;
            dlg.PrintToFile = false;
            dlg.ShowHelp = true;
            dlg.ShowNetwork = true;
            dlg.UseEXDialog = true;
            //needed because PrintDialog was not showing on 64 bit Vista systems
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                if (pd.DefaultPageSettings.PrintableArea.Width == 0 || pd.DefaultPageSettings.PrintableArea.Height == 0)
                {
                    pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
                }
                 
                pd.OriginAtMargins = true;
                pd.DefaultPageSettings.Margins = new Margins(50, 50, 50, 50);
                //Half-inch all around
                pd.Print();
                if (StringSupport.equals(DocSelected.Description, ""))
                {
                    SecurityLogs.makeLogEntry(Permissions.Printing,PatCur.PatNum,"Patient image " + DocSelected.FileName + " printed");
                }
                else
                {
                    SecurityLogs.makeLogEntry(Permissions.Printing,PatCur.PatNum,"Patient image " + DocSelected.Description + " printed");
                } 
            }
             
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"An error occurred while printing") + "\r\n" + ex.ToString());
        }
    
    }

    /**
    * If the node does not correspond to a valid document or mount, nothing happens. Otherwise the document/mount record and its corresponding file(s) are deleted.
    */
    private void toolBarDelete_Click() throws Exception {
        deleteSelection(true,true);
    }

    /**
    * Deletes the current selection from the database and refreshes the tree view. Set securityCheck false when creating a new document that might get cancelled.
    */
    private void deleteSelection(boolean verbose, boolean securityCheck) throws Exception {
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.None)
        {
            MsgBox.show(this,"No item is currently selected");
            return ;
        }
         
        //No current selection, or some kind of internal error somehow.
        if (nodeId.NodeType == ImageNodeType.Category)
        {
            MsgBox.show(this,"Cannot delete folders");
            return ;
        }
         
        OpenDentBusiness.Document doc = null;
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            doc = Documents.getByNum(nodeId.PriKey);
            if (securityCheck)
            {
                if (!Security.isAuthorized(Permissions.ImageDelete,doc.DateCreated))
                {
                    return ;
                }
                 
            }
             
            EhrLab lab = EhrLabImages.getFirstLabForDocNum(doc.DocNum);
            if (lab != null)
            {
                String dateSt = lab.ObservationDateTimeStart.PadRight(8, '0').Substring(0, 8);
                //stored in DB as yyyyMMddhhmmss-zzzz
                DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
                MessageBox.Show(Lan.g(this,"This image is attached to a lab order for this patient on " + dateT.ToShortDateString() + ". " + Lan.g(this,"Detach image from this lab order before deleting the image.")));
                return ;
            }
             
        }
        else if (nodeId.NodeType == ImageNodeType.Mount)
        {
        }
          
        //no security yet for mounts.
        enableAllTreeItemTools(false);
        OpenDentBusiness.Document[] docs = null;
        boolean refreshTree = true;
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            //Delete the mount object.
            long mountNum = nodeId.PriKey;
            Mount mount = Mounts.getByNum(mountNum);
            //Delete the mount items attached to the mount object.
            List<MountItem> mountItems = MountItems.getItemsForMount(mountNum);
            if (IdxSelectedInMount >= 0 && DocsInMount[IdxSelectedInMount] != null)
            {
                if (verbose)
                {
                    if (!MsgBox.show(this,true,"Delete mount xray image?"))
                    {
                        return ;
                    }
                     
                }
                 
                DocSelected = new OpenDentBusiness.Document();
                docs = new OpenDentBusiness.Document[]{ DocsInMount[IdxSelectedInMount] };
                DocsInMount[IdxSelectedInMount] = null;
                //Release access to current image so it may be properly deleted.
                if (ImagesCur[IdxSelectedInMount] != null)
                {
                    ImagesCur[IdxSelectedInMount].Dispose();
                    ImagesCur[IdxSelectedInMount] = null;
                }
                 
                invalidateSettings(ImageSettingFlags.ALL,false);
                refreshTree = false;
            }
            else
            {
                if (verbose)
                {
                    if (!MsgBox.show(this,true,"Delete entire mount?"))
                    {
                        return ;
                    }
                     
                }
                 
                docs = DocsInMount;
                Mounts.delete(mount);
                for (int i = 0;i < mountItems.Count;i++)
                {
                    MountItems.Delete(mountItems[i]);
                }
                selectTreeNode(null);
            } 
        }
        else //Release access to current image so it may be properly deleted.
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            if (verbose)
            {
                if (!MsgBox.show(this,true,"Delete document?"))
                {
                    return ;
                }
                 
            }
             
            docs = new OpenDentBusiness.Document[]{ doc };
            selectTreeNode(null);
        }
        else //Release access to current image so it may be properly deleted.
        if (nodeId.NodeType == ImageNodeType.Eob)
        {
            if (verbose)
            {
                if (!MsgBox.show(this,true,"Delete EOB?"))
                {
                    return ;
                }
                 
            }
             
            EobAttach eob = EobAttaches.getOne(nodeId.PriKey);
            selectTreeNode(null);
            //release access
            ImageStore.deleteEobAttach(eob);
        }
        else if (nodeId.NodeType == ImageNodeType.Amd)
        {
            if (verbose)
            {
                if (!MsgBox.show(this,true,"Delete amendment?"))
                {
                    return ;
                }
                 
            }
             
            if (EhrAmendmentCur == null)
            {
                return ;
            }
             
            selectTreeNode(null);
            //release access
            ImageStore.deleteAmdAttach(EhrAmendmentCur);
        }
            
        if (nodeId.NodeType == ImageNodeType.Doc || nodeId.NodeType == ImageNodeType.Mount)
        {
            try
            {
                //Delete all documents involved in deleting this object.
                //ImageStoreBase.verbose=verbose;
                ImageStore.DeleteDocuments(docs, PatFolder);
            }
            catch (Exception ex)
            {
                //Image could not be deleted, in use.
                MessageBox.Show(this, ex.Message);
            }
        
        }
         
        if (refreshTree)
        {
            fillDocList(false);
        }
         
    }

    private void toolBarSign_Click() throws Exception {
        //No selection
        //Internal error
        if (treeDocuments.SelectedNode == null || treeDocuments.SelectedNode.Tag == null || treeDocuments.SelectedNode.Parent == null)
        {
            return ;
        }
         
        //This is a folder node.
        //Show the underlying panel note box while the signature is being filled.
        panelNote.Visible = true;
        resizeAll();
        //Display the document signature form.
        FormDocSign docSignForm = new FormDocSign(DocSelected,PatCur);
        //Updates our local document and saves changes to db also.
        int signLeft = treeDocuments.Left;
        docSignForm.Location = PointToScreen(new Point(signLeft, this.ClientRectangle.Bottom - docSignForm.Height));
        docSignForm.Width = Math.Max(0, Math.Min(docSignForm.Width, pictureBoxMain.Right - signLeft));
        docSignForm.ShowDialog();
        fillDocList(true);
        //Adjust visibility of panel note based on changes made to the signature above.
        setPanelNoteVisibility(DocSelected);
        //Resize controls in our window to adjust for a possible change in the visibility of the panel note control.
        resizeAll();
        //Update the signature and note with the new data.
        fillSignature();
    }

    /**
    * DO NOT CALL UNLESS THE CURRENTLY SELECTED NODE IS A DOCUMENT NODE. Fills the panelnote control with the current document signature when the panelnote is visible and when a valid document is currently selected.
    */
    private void fillSignature() throws Exception {
        textNote.Text = "";
        sigBox.clearTablet();
        if (!panelNote.Visible)
        {
            return ;
        }
         
        textNote.Text = DocSelected.Note;
        labelInvalidSig.Visible = false;
        sigBox.Visible = true;
        sigBox.setTabletState(0);
        //never accepts input here
        //Topaz box is not supported in Unix, since the required dll is Windows native.
        if (DocSelected.SigIsTopaz)
        {
            if (DocSelected.Signature != null && !StringSupport.equals(DocSelected.Signature, ""))
            {
                //if(allowTopaz) {
                sigBox.Visible = false;
                SigBoxTopaz.Visible = true;
                CodeBase.TopazWrapper.clearTopaz(SigBoxTopaz);
                CodeBase.TopazWrapper.setTopazCompressionMode(SigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(SigBoxTopaz,0);
                String keystring = getHashString(DocSelected);
                CodeBase.TopazWrapper.setTopazKeyString(SigBoxTopaz,keystring);
                CodeBase.TopazWrapper.setTopazEncryptionMode(SigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazCompressionMode(SigBoxTopaz,2);
                //high compression
                CodeBase.TopazWrapper.setTopazSigString(SigBoxTopaz,DocSelected.Signature);
                SigBoxTopaz.Refresh();
                //If sig is not showing, then try encryption mode 3 for signatures signed with old SigPlusNet.dll.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(SigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazEncryptionMode(SigBoxTopaz,3);
                    //Unknown mode (told to use via TopazSystems)
                    CodeBase.TopazWrapper.setTopazSigString(SigBoxTopaz,DocSelected.Signature);
                }
                 
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(SigBoxTopaz) == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
            }
             
        }
        else
        {
            //}
            //not topaz
            if (DocSelected.Signature != null && !StringSupport.equals(DocSelected.Signature, ""))
            {
                sigBox.Visible = true;
                //if(allowTopaz) {
                SigBoxTopaz.Visible = false;
                //}
                sigBox.clearTablet();
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString(getHashString(DocSelected));
                //"0000000000000000");
                //sigBox.SetAutoKeyData(ProcCur.Note+ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);//high encryption
                //sigBox.SetSigCompressionMode(2);//high compression
                sigBox.setSigString(DocSelected.Signature);
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                sigBox.setTabletState(0);
            }
             
        } 
    }

    //not accepting input.
    private String getHashString(OpenDentBusiness.Document doc) throws Exception {
        return ImageStore.getHashString(doc,PatFolder);
    }

    /**
    * Valid values for scanType are "doc","xray",and "photo"
    */
    private void toolBarScan_Click(String scanType) throws Exception {
        if (EhrAmendmentCur != null)
        {
            if (EhrAmendmentCur.FileName != null && !StringSupport.equals(EhrAmendmentCur.FileName, ""))
            {
                if (!MsgBox.show(this,true,"This will delete your old file. Proceed?"))
                {
                    return ;
                }
                 
            }
             
        }
         
        Cursor = Cursors.WaitCursor;
        Bitmap bitmapScanned = null;
        IntPtr hdib = IntPtr.Zero;
        try
        {
            xImageDeviceManager.Obfuscator.ActivateEZTwain();
        }
        catch (Exception __dummyCatchVar4)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"EzTwain4.dll not found.  Please run the setup file in your images folder.");
            return ;
        }

        if (ComputerPrefs.getLocalComputer().ScanDocSelectSource)
        {
            if (!EZTwain.SelectImageSource(this.Handle))
            {
                //dialog to select source
                Cursor = Cursors.Default;
                return ;
            }
             
        }
         
        //User clicked cancel.
        EZTwain.setHideUI(!ComputerPrefs.getLocalComputer().ScanDocShowOptions);
        if (!EZTwain.openDefaultSource())
        {
            //if it can't open the scanner successfully
            Cursor = Cursors.Default;
            MsgBox.show(this,"Default scanner could not be opened.  Check that the default scanner works from Windows Control Panel and from Windows Fax and Scan.");
            return ;
        }
         
        EZTwain.SetResolution(ComputerPrefs.getLocalComputer().ScanDocResolution);
        if (ComputerPrefs.getLocalComputer().ScanDocGrayscale)
        {
            EZTwain.setPixelType(1);
        }
        else
        {
            //8-bit grayscale - only set if scanner dialog will not show
            EZTwain.setPixelType(2);
        } 
        //24-bit color
        EZTwain.SetJpegQuality(ComputerPrefs.getLocalComputer().ScanDocQuality);
        EZTwain.setXferMech(EZTwain.XFERMECH_MEMORY);
        Cursor = Cursors.Default;
        hdib = EZTwain.Acquire(this.Handle);
        //This is where the options dialog would come up. The settings above will not populate this window.
        int errorCode = EZTwain.lastErrorCode();
        if (errorCode != 0)
        {
            String message = "";
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_USER_CANCEL).ordinal())
            {
                return ;
            }
            else //19
            //message="\r\nScanning cancelled.";//do nothing
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_JPEG_DLL).ordinal())
            {
                //22
                message = "Missing dll\r\n\r\nRequired file EZJpeg.dll is missing.";
            }
            else if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_0_PAGES).ordinal())
            {
                return ;
            }
            else //38
            //message="\r\nScanning cancelled.";//do nothing
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_NO_PDF).ordinal())
            {
                //43
                message = "Missing dll\r\n\r\nRequired file EZPdf.dll is missing.";
            }
            else if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_DEVICE_PAPERJAM).ordinal())
            {
                //76
                message = "Paper jam\r\n\r\nPlease check the scanner document feeder and ensure there path is clear of any paper jams.";
            }
            else
            {
                message = errorCode + " " + (EZTwainErrorCode.values()[errorCode]).ToString();
            }     
            MessageBox.Show(Lan.g(this,"Unable to scan. Please make sure you can scan using other software. Error: " + message));
            return ;
        }
         
        if (hdib == (IntPtr)0)
        {
            return ;
        }
         
        //This is down here because there might also be an informative error code that we would like to use above.
        //User cancelled
        double xdpi = EZTwain.DIB_XResolution(hdib);
        double ydpi = EZTwain.DIB_XResolution(hdib);
        IntPtr hbitmap = EZTwain.DIB_ToDibSection(hdib);
        bitmapScanned = Bitmap.FromHbitmap(hbitmap);
        bitmapScanned.SetResolution((float)xdpi, (float)ydpi);
        try
        {
            Clipboard.SetImage(bitmapScanned);
        }
        catch (Exception __dummyCatchVar5)
        {
        }

        //We do this because a customer requested it, and some customers probably use it.
        //Rarely, setting the clipboard image fails, in which case we should ignore the failure because most people do not use this feature.
        ImageType imgType = ImageType.Document;
        if (StringSupport.equals(scanType, "xray"))
        {
            imgType = ImageType.Radiograph;
        }
        else if (StringSupport.equals(scanType, "photo"))
        {
            imgType = ImageType.Photo;
        }
        else
        {
            //Assume document
            imgType = ImageType.Document;
        }  
        boolean saved = true;
        if (ClaimPaymentNum != 0)
        {
            //eob
            EobAttach eob = null;
            try
            {
                eob = ImageStore.importEobAttach(bitmapScanned,ClaimPaymentNum);
            }
            catch (Exception ex)
            {
                saved = false;
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error saving eob") + ": " + ex.Message);
            }

            if (bitmapScanned != null)
            {
                bitmapScanned.Dispose();
                bitmapScanned = null;
            }
             
            if (hdib != IntPtr.Zero)
            {
                EZTwain.DIB_Free(hdib);
            }
             
            Cursor = Cursors.Default;
            if (saved)
            {
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdEob(eob.EobAttachNum)));
            }
             
        }
        else if (EhrAmendmentCur != null)
        {
            //We only allow users to scan in one amendment at a time.  Keep track of the old file name.
            String fileNameOld = EhrAmendmentCur.FileName;
            try
            {
                ImageStore.importAmdAttach(bitmapScanned,EhrAmendmentCur);
                selectTreeNode(null);
                ImageStore.cleanAmdAttach(fileNameOld);
            }
            catch (Exception ex)
            {
                //Delete the old scanned document.
                saved = false;
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error saving amendment") + ": " + ex.Message);
            }

            if (bitmapScanned != null)
            {
                bitmapScanned.Dispose();
                bitmapScanned = null;
            }
             
            if (hdib != IntPtr.Zero)
            {
                EZTwain.DIB_Free(hdib);
            }
             
            Cursor = Cursors.Default;
            if (saved)
            {
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdAmd(EhrAmendmentCur.EhrAmendmentNum)));
            }
             
        }
        else
        {
            //regular Images module
            OpenDentBusiness.Document doc = null;
            try
            {
                //Create corresponding image file.
                doc = ImageStore.import(bitmapScanned,getCurrentCategory(),imgType,PatCur);
            }
            catch (Exception ex)
            {
                saved = false;
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Unable to save document") + ": " + ex.Message);
            }

            if (bitmapScanned != null)
            {
                bitmapScanned.Dispose();
                bitmapScanned = null;
            }
             
            if (hdib != IntPtr.Zero)
            {
                EZTwain.DIB_Free(hdib);
            }
             
            Cursor = Cursors.Default;
            if (saved)
            {
                fillDocList(false);
                //Reload and keep new document selected.
                selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
                FormDocInfo formDocInfo = new FormDocInfo(PatCur, DocSelected, GetCurrentFolderName(treeDocuments.SelectedNode));
                formDocInfo.ShowDialog();
                if (formDocInfo.DialogResult != DialogResult.OK)
                {
                    deleteSelection(false,false);
                }
                else
                {
                    fillDocList(true);
                } 
            }
             
        }  
    }

    //Update tree, in case the new document's icon or category were modified in formDocInfo.
    private void toolBarScanMulti_Click() throws Exception {
        if (EhrAmendmentCur != null)
        {
            if (EhrAmendmentCur.FileName != null && !StringSupport.equals(EhrAmendmentCur.FileName, ""))
            {
                if (!MsgBox.show(this,true,"This will delete your old file. Proceed?"))
                {
                    return ;
                }
                 
            }
             
        }
         
        String tempFile = Path.GetTempFileName().Replace(".tmp", ".pdf");
        try
        {
            xImageDeviceManager.Obfuscator.ActivateEZTwain();
        }
        catch (Exception __dummyCatchVar6)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"EzTwain4.dll not found.  Please run the setup file in your images folder.");
            return ;
        }

        if (ComputerPrefs.getLocalComputer().ScanDocSelectSource)
        {
            if (!EZTwain.SelectImageSource(this.Handle))
            {
                return ;
            }
             
        }
         
        //User clicked cancel.
        //EZTwain.LogFile(7);//Writes at level 7 (very detailed) in the C:\eztwain.log text file. Useful for getting help from EZTwain support on their forum.
        EZTwain.setHideUI(!ComputerPrefs.getLocalComputer().ScanDocShowOptions);
        EZTwain.pDF_SetCompression((int)this.Handle,(int)ComputerPrefs.getLocalComputer().ScanDocQuality);
        if (!EZTwain.openDefaultSource())
        {
            //if it can't open the scanner successfully
            MsgBox.show(this,"Default scanner could not be opened.  Check that the default scanner works from Windows Control Panel and from Windows Fax and Scan.");
            Cursor = Cursors.Default;
            return ;
        }
         
        boolean duplexEnabled = EZTwain.enableDuplex(ComputerPrefs.getLocalComputer().ScanDocDuplex);
        //This line seems to cause problems.
        if (ComputerPrefs.getLocalComputer().ScanDocGrayscale)
        {
            EZTwain.setPixelType(1);
        }
        else
        {
            //8-bit grayscale
            EZTwain.setPixelType(2);
        } 
        //24-bit color
        EZTwain.SetResolution(ComputerPrefs.getLocalComputer().ScanDocResolution);
        EZTwain.AcquireMultipageFile(this.Handle, tempFile);
        //This is where the options dialog will come up if enabled. This will ignore and override the settings above.
        int errorCode = EZTwain.lastErrorCode();
        if (errorCode != 0)
        {
            String message = "";
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_USER_CANCEL).ordinal())
            {
                return ;
            }
            else //19
            //message="\r\nScanning cancelled.";//do nothing
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_JPEG_DLL).ordinal())
            {
                //22
                message = "Missing dll\r\n\r\nRequired file EZJpeg.dll is missing.";
            }
            else if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_0_PAGES).ordinal())
            {
                return ;
            }
            else //38
            //message="\r\nScanning cancelled.";//do nothing
            if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_NO_PDF).ordinal())
            {
                //43
                message = "Missing dll\r\n\r\nRequired file EZPdf.dll is missing.";
            }
            else if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_DEVICE_PAPERJAM).ordinal())
            {
                //76
                message = "Paper jam\r\n\r\nPlease check the scanner document feeder and ensure there path is clear of any paper jams.";
            }
            else if (errorCode == ((Enum)EZTwainErrorCode.EZTEC_DS_FAILURE).ordinal())
            {
                //5
                message = "Duplex failure\r\n\r\nDuplex mode without scanner options window failed. Try enabling the scanner options window or disabling duplex mode.";
            }
            else
            {
                message = errorCode + " " + (EZTwainErrorCode.values()[errorCode]).ToString();
            }      
            MessageBox.Show(Lan.g(this,"Unable to scan. Please make sure you can scan using other software. Error: " + message));
            return ;
        }
         
        ImageNodeId nodeId = new ImageNodeId();
        boolean copied = true;
        if (ClaimPaymentNum != 0)
        {
            //eob
            EobAttach eob = null;
            try
            {
                eob = ImageStore.importEobAttach(tempFile,ClaimPaymentNum);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + tempFile);
                copied = false;
            }

            if (copied)
            {
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdEob(eob.EobAttachNum)));
            }
             
            File.Delete(tempFile);
        }
        else if (EhrAmendmentCur != null)
        {
            //amendment
            String fileNameOld = EhrAmendmentCur.FileName;
            try
            {
                ImageStore.importAmdAttach(tempFile,EhrAmendmentCur);
                selectTreeNode(null);
                ImageStore.cleanAmdAttach(fileNameOld);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + tempFile);
                copied = false;
            }

            if (copied)
            {
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdAmd(EhrAmendmentCur.EhrAmendmentNum)));
            }
             
            File.Delete(tempFile);
        }
        else
        {
            //regular Images module
            OpenDentBusiness.Document doc = null;
            try
            {
                doc = ImageStore.import(tempFile,getCurrentCategory(),PatCur);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + tempFile);
                copied = false;
            }

            if (copied)
            {
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
                FormDocInfo FormD = new FormDocInfo(PatCur, doc, GetCurrentFolderName(treeDocuments.SelectedNode));
                FormD.ShowDialog();
                //some of the fields might get changed, but not the filename
                if (FormD.DialogResult != DialogResult.OK)
                {
                    deleteSelection(false,false);
                }
                else
                {
                    nodeId = makeIdDoc(doc.DocNum);
                    DocSelected = doc.copy();
                } 
            }
             
            File.Delete(tempFile);
            //Reselect the last successfully added node when necessary. js This code seems to be copied from import multi.  Simplify it.
            if (doc != null && !makeIdDoc(doc.DocNum).Equals(nodeId))
            {
                selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
            }
             
            fillDocList(true);
        }  
    }

    private void toolBarImport_Click() throws Exception {
        if (EhrAmendmentCur != null)
        {
            if (EhrAmendmentCur.FileName != null && !StringSupport.equals(EhrAmendmentCur.FileName, ""))
            {
                if (!MsgBox.show(this,true,"This will delete your old file. Proceed?"))
                {
                    return ;
                }
                 
            }
             
        }
         
        OpenFileDialog openFileDialog = new OpenFileDialog();
        openFileDialog.Multiselect = false;
        if (openFileDialog.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        String[] fileNames = openFileDialog.FileNames;
        if (fileNames.Length < 1)
        {
            return ;
        }
         
        ImageNodeId nodeId = new ImageNodeId();
        boolean copied = true;
        if (ClaimPaymentNum != 0)
        {
            //eob
            EobAttach eob = null;
            for (int i = 0;i < fileNames.Length;i++)
            {
                try
                {
                    eob = ImageStore.ImportEobAttach(fileNames[i], ClaimPaymentNum);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + openFileDialog.FileName);
                    copied = false;
                }
            
            }
            if (copied)
            {
                fillDocList(false);
            }
             
            if (eob != null)
            {
                selectTreeNode(getNodeById(makeIdEob(eob.EobAttachNum)));
            }
             
        }
        else if (EhrAmendmentCur != null)
        {
            String amdFilename = EhrAmendmentCur.FileName;
            for (int i = 0;i < fileNames.Length;i++)
            {
                try
                {
                    EhrAmendmentCur = ImageStore.ImportAmdAttach(fileNames[i], EhrAmendmentCur);
                    selectTreeNode(null);
                    ImageStore.cleanAmdAttach(amdFilename);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + openFileDialog.FileName);
                    copied = false;
                }
            
            }
            if (copied)
            {
                fillDocList(false);
            }
             
            if (EhrAmendmentCur != null)
            {
                selectTreeNode(getNodeById(makeIdAmd(EhrAmendmentCur.EhrAmendmentNum)));
            }
             
        }
        else
        {
            //regular Images module
            OpenDentBusiness.Document doc = null;
            for (int i = 0;i < fileNames.Length;i++)
            {
                try
                {
                    doc = ImageStore.Import(fileNames[i], getCurrentCategory(), PatCur);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(Lan.g(this,"Unable to copy file, May be in use: ") + ex.Message + ": " + openFileDialog.FileName);
                    copied = false;
                }

                if (copied)
                {
                    fillDocList(false);
                    selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
                    FormDocInfo FormD = new FormDocInfo(PatCur, doc, GetCurrentFolderName(treeDocuments.SelectedNode));
                    FormD.ShowDialog();
                    //some of the fields might get changed, but not the filename
                    if (FormD.DialogResult != DialogResult.OK)
                    {
                        deleteSelection(false,false);
                    }
                    else
                    {
                        nodeId = makeIdDoc(doc.DocNum);
                        DocSelected = doc.copy();
                    } 
                }
                 
            }
            //Reselect the last successfully added node when necessary.
            if (doc != null && !makeIdDoc(doc.DocNum).Equals(nodeId))
            {
                selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
            }
             
            fillDocList(true);
        }  
    }

    private void toolBarExport_Click() throws Exception {
        if (treeDocuments.SelectedNode == null)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.Category || nodeId.NodeType == ImageNodeType.Mount || nodeId.NodeType == ImageNodeType.None)
        {
            MsgBox.show(this,"Not allowed.");
            return ;
        }
         
        String fileName = "";
        SaveFileDialog dlg = new SaveFileDialog();
        dlg.Title = "Export a Document";
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            OpenDentBusiness.Document doc = Documents.getByNum(nodeId.PriKey);
            dlg.FileName = doc.FileName;
            dlg.DefaultExt = Path.GetExtension(doc.FileName);
            if (dlg.ShowDialog() != DialogResult.OK)
            {
                return ;
            }
             
            fileName = dlg.FileName;
            if (fileName.Length < 1)
            {
                MsgBox.show(this,"You must enter a file name.");
                return ;
            }
             
            try
            {
                ImageStore.export(fileName,doc,PatCur);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to export file, May be in use: ") + ex.Message + ": " + fileName);
                return ;
            }
        
        }
         
        if (nodeId.NodeType == ImageNodeType.Eob)
        {
            EobAttach eob = EobAttaches.getOne(nodeId.PriKey);
            dlg.FileName = eob.FileName;
            dlg.DefaultExt = Path.GetExtension(eob.FileName);
            if (dlg.ShowDialog() != DialogResult.OK)
            {
                return ;
            }
             
            fileName = dlg.FileName;
            if (fileName.Length < 1)
            {
                MsgBox.show(this,"You must enter a file name.");
                return ;
            }
             
            try
            {
                ImageStore.exportEobAttach(fileName,eob);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to export file, May be in use: ") + ex.Message + ": " + fileName);
                return ;
            }
        
        }
        else if (nodeId.NodeType == ImageNodeType.Amd)
        {
            EhrAmendment amd = EhrAmendments.getOne(nodeId.PriKey);
            dlg.FileName = amd.FileName;
            dlg.DefaultExt = Path.GetExtension(amd.FileName);
            if (dlg.ShowDialog() != DialogResult.OK)
            {
                return ;
            }
             
            fileName = dlg.FileName;
            if (fileName.Length < 1)
            {
                MsgBox.show(this,"You must enter a file name.");
                return ;
            }
             
            try
            {
                ImageStore.exportAmdAttach(fileName,amd);
            }
            catch (Exception ex)
            {
                MessageBox.Show(Lan.g(this,"Unable to export file, May be in use: ") + ex.Message + ": " + fileName);
                return ;
            }
        
        }
          
        MessageBox.Show(Lan.g(this,"Successfully exported to ") + fileName);
    }

    private void toolBarCopy_Click() throws Exception {
        if (treeDocuments.SelectedNode == null || treeDocuments.SelectedNode.Tag == null)
        {
            MsgBox.show(this,"Please select a document before copying");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        Bitmap bitmapCopy = null;
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            if (IdxSelectedInMount >= 0 && DocsInMount[IdxSelectedInMount] != null)
            {
                //A mount item is currently selected.
                bitmapCopy = ImageHelper.ApplyDocumentSettingsToImage(DocsInMount[IdxSelectedInMount], ImagesCur[IdxSelectedInMount], ImageSettingFlags.ALL);
            }
            else
            {
                //Assume the copy is for the entire mount.
                bitmapCopy = (Bitmap)ImageRenderingNow.Clone();
            } 
        }
        else if (nodeId.NodeType == ImageNodeType.Doc)
        {
            //Crop and color function has already been applied to the render image.
            bitmapCopy = ImageHelper.applyDocumentSettingsToImage(Documents.getByNum(nodeId.PriKey),ImageRenderingNow,ImageSettingFlags.FLIP | ImageSettingFlags.ROTATE);
        }
        else if (nodeId.NodeType == ImageNodeType.Eob)
        {
            bitmapCopy = (Bitmap)ImageRenderingNow.Clone();
        }
        else if (nodeId.NodeType == ImageNodeType.Amd)
        {
            bitmapCopy = (Bitmap)ImageRenderingNow.Clone();
        }
            
        if (bitmapCopy != null)
        {
            Clipboard.SetDataObject(bitmapCopy);
        }
         
        //Can't do this, or the clipboard object goes away.
        //bitmapCopy.Dispose();
        //bitmapCopy=null;
        SecurityLogs.makeLogEntry(Permissions.Copy,PatCur.PatNum,"Patient image " + Documents.getByNum(nodeId.PriKey).FileName + " copied to clipboard");
        Cursor = Cursors.Default;
    }

    private void toolBarPaste_Click() throws Exception {
        IDataObject clipboard = Clipboard.GetDataObject();
        if (!clipboard.GetDataPresent(DataFormats.Bitmap))
        {
            MessageBox.Show(Lan.g(this,"No bitmap present on clipboard"));
            return ;
        }
         
        Bitmap bitmapPaste = (Bitmap)clipboard.GetData(DataFormats.Bitmap);
        OpenDentBusiness.Document doc;
        ImageNodeId nodeId = new ImageNodeId();
        if (treeDocuments.SelectedNode != null && treeDocuments.SelectedNode.Tag != null)
        {
            nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        }
         
        Cursor = Cursors.WaitCursor;
        if (ClaimPaymentNum != 0)
        {
            EobAttach eob = null;
            try
            {
                eob = ImageStore.importEobAttach(bitmapPaste,ClaimPaymentNum);
            }
            catch (Exception __dummyCatchVar7)
            {
                MessageBox.Show(Lan.g(this,"Error saving eob."));
                Cursor = Cursors.Default;
                return ;
            }

            fillDocList(false);
            selectTreeNode(getNodeById(makeIdEob(eob.EobAttachNum)));
        }
        else if (EhrAmendmentCur != null)
        {
            EhrAmendment amd = null;
            try
            {
                amd = ImageStore.importAmdAttach(bitmapPaste,EhrAmendmentCur);
            }
            catch (Exception __dummyCatchVar8)
            {
                MessageBox.Show(Lan.g(this,"Error saving amendment."));
                Cursor = Cursors.Default;
                return ;
            }

            fillDocList(false);
            selectTreeNode(getNodeById(makeIdAmd(amd.EhrAmendmentNum)));
        }
        else if (nodeId.NodeType == ImageNodeType.Mount && IdxSelectedInMount >= 0)
        {
            //Pasting into the mount item of the currently selected mount.
            if (DocsInMount[IdxSelectedInMount] != null)
            {
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Do you want to replace the existing item in this mount location?"))
                {
                    this.Cursor = Cursors.Default;
                    return ;
                }
                 
                deleteSelection(false,true);
            }
             
            try
            {
                doc = ImageStore.ImportImageToMount(bitmapPaste, 0, MountItemsForSelected[IdxSelectedInMount].MountItemNum, getCurrentCategory(), PatCur);
                doc.WindowingMax = 255;
                doc.WindowingMin = 0;
                Documents.update(doc);
            }
            catch (Exception __dummyCatchVar9)
            {
                MessageBox.Show(Lan.g(this,"Error saving document."));
                Cursor = Cursors.Default;
                return ;
            }

            DocsInMount[IdxSelectedInMount] = doc;
            ImagesCur[IdxSelectedInMount] = bitmapPaste;
        }
        else
        {
            try
            {
                //Paste the image as its own unique document.
                doc = ImageStore.import(bitmapPaste,getCurrentCategory(),ImageType.Photo,PatCur);
            }
            catch (Exception __dummyCatchVar10)
            {
                MessageBox.Show(Lan.g(this,"Error saving document."));
                Cursor = Cursors.Default;
                return ;
            }

            fillDocList(false);
            selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
            FormDocInfo formD = new FormDocInfo(PatCur, doc, GetCurrentFolderName(treeDocuments.SelectedNode));
            formD.ShowDialog();
            if (formD.DialogResult != DialogResult.OK)
            {
                deleteSelection(false,false);
            }
            else
            {
                fillDocList(true);
            } 
        }   
        invalidateSettings(ImageSettingFlags.ALL,true);
        Cursor = Cursors.Default;
    }

    private void toolBarCrop_Click() throws Exception {
        //remember it's testing after the push has been completed
        if (ToolBarPaint.getButtons().get___idx("Crop").getPushed())
        {
            //Crop Mode
            ToolBarPaint.getButtons().get___idx("Hand").setPushed(false);
            pictureBoxMain.Cursor = Cursors.Default;
        }
        else
        {
            ToolBarPaint.getButtons().get___idx("Crop").setPushed(true);
        } 
        IsCropMode = true;
        ToolBarPaint.Invalidate();
    }

    private void toolBarHand_Click() throws Exception {
        if (ToolBarPaint.getButtons().get___idx("Hand").getPushed())
        {
            //Hand Mode
            ToolBarPaint.getButtons().get___idx("Crop").setPushed(false);
            pictureBoxMain.Cursor = Cursors.Hand;
        }
        else
        {
            ToolBarPaint.getButtons().get___idx("Hand").setPushed(true);
        } 
        IsCropMode = false;
        ToolBarPaint.Invalidate();
    }

    private void printDocument_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        //Keep a local pointer to the ImageRenderingNow so that the print results cannot be messed up by the current rendering thread (by changing the ImageRenderingNow).
        if (ImageRenderingNow == null)
        {
            e.HasMorePages = false;
            return ;
        }
         
        Bitmap bitmapCloned = (Bitmap)ImageRenderingNow.Clone();
        if (bitmapCloned.Width < 1 || bitmapCloned.Height < 1 || treeDocuments.SelectedNode == null || treeDocuments.SelectedNode.Tag == null)
        {
            bitmapCloned.Dispose();
            bitmapCloned = null;
            e.HasMorePages = false;
            return ;
        }
         
        Bitmap bitmapPrint = null;
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.Category)
        {
            bitmapCloned.Dispose();
            bitmapCloned = null;
            e.HasMorePages = false;
            return ;
        }
         
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            if (IdxSelectedInMount >= 0 && DocsInMount[IdxSelectedInMount] != null)
            {
                //mount item only
                bitmapPrint = ImageHelper.ApplyDocumentSettingsToImage(DocsInMount[IdxSelectedInMount], ImagesCur[IdxSelectedInMount], ImageSettingFlags.ALL);
            }
            else
            {
                //Entire mount. Individual images are already rendered onto mount with correct settings.
                bitmapPrint = bitmapCloned;
            } 
        }
        else if (nodeId.NodeType == ImageNodeType.Doc)
        {
            //Crop and color function have already been applied to the render image, now do the rest.
            bitmapPrint = ImageHelper.applyDocumentSettingsToImage(Documents.getByNum(nodeId.PriKey),bitmapCloned,ImageSettingFlags.FLIP | ImageSettingFlags.ROTATE);
        }
        else if (nodeId.NodeType == ImageNodeType.Eob)
        {
            bitmapPrint = (Bitmap)bitmapCloned.Clone();
        }
        else if (nodeId.NodeType == ImageNodeType.Amd)
        {
            bitmapPrint = (Bitmap)bitmapCloned.Clone();
        }
            
        RectangleF rectf = e.MarginBounds;
        float ratio = Math.Min(rectf.Width / (float)bitmapPrint.Width, rectf.Height / (float)bitmapPrint.Height);
        Graphics g = e.Graphics;
        g.InterpolationMode = InterpolationMode.HighQualityBicubic;
        g.CompositingQuality = CompositingQuality.HighQuality;
        g.SmoothingMode = SmoothingMode.HighQuality;
        g.PixelOffsetMode = PixelOffsetMode.HighQuality;
        g.DrawImage(bitmapPrint, 0, 0, (int)(bitmapPrint.Width * ratio), (int)(bitmapPrint.Height * ratio));
        bitmapCloned.Dispose();
        bitmapCloned = null;
        bitmapPrint.Dispose();
        bitmapPrint = null;
        e.HasMorePages = false;
    }

    private void menuTree_Click(Object sender, System.EventArgs e) throws Exception {
        Index __dummyScrutVar2 = ((MenuItem)sender).Index;
        if (__dummyScrutVar2.equals(0))
        {
            //print
            toolBarPrint_Click();
        }
        else if (__dummyScrutVar2.equals(1))
        {
            //delete
            toolBarDelete_Click();
        }
        else if (__dummyScrutVar2.equals(2))
        {
            //info
            toolBarInfo_Click();
        }
           
    }

    private void menuForms_Click(Object sender, System.EventArgs e) throws Exception {
        String formName = ((MenuItem)sender).Text;
        boolean copied = true;
        OpenDentBusiness.Document doc = null;
        try
        {
            doc = ImageStore.importForm(formName,getCurrentCategory(),PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            copied = false;
        }

        if (copied)
        {
            fillDocList(false);
            selectTreeNode(getNodeById(makeIdDoc(doc.DocNum)));
            FormDocInfo FormD = new FormDocInfo(PatCur, doc, GetCurrentFolderName(treeDocuments.SelectedNode));
            FormD.ShowDialog();
            //some of the fields might get changed, but not the filename
            if (FormD.DialogResult != DialogResult.OK)
            {
                deleteSelection(false,false);
            }
            else
            {
                fillDocList(true);
            } 
        }
         
    }

    //Refresh possible changes in the document due to FormD.
    private void textNote_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void label1_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void label15_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void sigBox_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void sigBoxTopaz_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void labelInvalidSig_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void panelNote_DoubleClick(Object sender, EventArgs e) throws Exception {
        toolBarSign_Click();
    }

    private void textNote_MouseHover(Object sender, EventArgs e) throws Exception {
        textNote.Cursor = Cursors.IBeam;
    }

    /**
    * 
    */
    private void treeDocuments_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        NodeIdentifierDown = new ImageNodeId();
        TreeNode nodeOver = treeDocuments.GetNodeAt(e.Location);
        if (nodeOver == null)
        {
            return ;
        }
         
        ImageNodeId nodeIdDown = (ImageNodeId)nodeOver.Tag;
        if (nodeIdDown.NodeType == ImageNodeType.Doc || nodeIdDown.NodeType == ImageNodeType.Mount)
        {
            //These are the only types that can be dragged.
            NodeIdentifierDown = nodeIdDown;
            TimeMouseMoved = new DateTime(1, 1, 1);
        }
         
        //For time delay. This will be set the moment the mouse actually starts moving
        //Always select the node on a mouse-down press for either right or left buttons.
        //If the left button is pressed, then the document is either being selected or dragged, so
        //setting the image at the beginning of the drag will either display the image as expected, or
        //automatically display the image while the document is being dragged (since it is in a different thread).
        //If the right button is pressed, then the user wants to view the properties of the image they are
        //clicking on, so displaying the image (in a different thread) will give the user a chance to view
        //the image corresponding to a delete, info display, etc...
        selectTreeNode(nodeOver);
    }

    private void treeDocuments_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (NodeIdentifierDown.NodeType == ImageNodeType.None)
        {
            treeDocuments.Cursor = Cursors.Default;
            return ;
        }
         
        TreeNode nodeOver = treeDocuments.GetNodeAt(e.Location);
        if (nodeOver == null)
        {
            //unknown malfunction
            treeDocuments.Cursor = Cursors.Default;
            return ;
        }
         
        if (NodeIdentifierDown.Equals((ImageNodeId)nodeOver.Tag))
        {
            //Over the original node
            treeDocuments.Cursor = Cursors.Default;
            return ;
        }
         
        //Show drag
        //Cursor cursorDrag=new System.Windows.Forms.Cursor();
        treeDocuments.Cursor = Cursors.Hand;
        //need a better cursor than this
        if (TimeMouseMoved.Year == 1)
        {
            TimeMouseMoved = DateTime.Now;
        }
         
    }

    /**
    * 
    */
    private void treeDocuments_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        treeDocuments.Cursor = Cursors.Default;
        if (NodeIdentifierDown.NodeType == ImageNodeType.None)
        {
            return ;
        }
         
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        //Dragging can only happen with the left mouse button.
        TimeSpan timeSpanDrag = (TimeSpan)(DateTime.Now - TimeMouseMoved);
        //if(timeSpanDrag.Milliseconds < 200) { //js 3/31/2012. Was 250
        //	return;//Too short of a drag and drop.  Probably human error
        //}
        TreeNode nodeOver = treeDocuments.GetNodeAt(e.Location);
        if (nodeOver == null)
        {
            return ;
        }
         
        ImageNodeId nodeOverId = (ImageNodeId)nodeOver.Tag;
        long nodeOverCategoryDefNum = 0;
        if (nodeOverId.NodeType == ImageNodeType.Category)
        {
            nodeOverCategoryDefNum = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][nodeOver.Index].DefNum;
        }
        else
        {
            nodeOverCategoryDefNum = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][nodeOver.Parent.Index].DefNum;
        } 
        TreeNode nodeOriginal = getNodeById(NodeIdentifierDown);
        long nodeOriginalCategoryDefNum = 0;
        if (NodeIdentifierDown.NodeType == ImageNodeType.Category)
        {
            nodeOriginalCategoryDefNum = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][nodeOriginal.Index].DefNum;
        }
        else
        {
            nodeOriginalCategoryDefNum = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][nodeOriginal.Parent.Index].DefNum;
        } 
        if (nodeOverCategoryDefNum == nodeOriginalCategoryDefNum)
        {
            return ;
        }
         
        //category hasn't changed
        if (NodeIdentifierDown.NodeType == ImageNodeType.Mount)
        {
            Mount mount = Mounts.getByNum(NodeIdentifierDown.PriKey);
            mount.DocCategory = nodeOverCategoryDefNum;
            Mounts.update(mount);
        }
        else if (NodeIdentifierDown.NodeType == ImageNodeType.Doc)
        {
            OpenDentBusiness.Document doc = Documents.getByNum(NodeIdentifierDown.PriKey);
            doc.DocCategory = nodeOverCategoryDefNum;
            Documents.update(doc);
        }
          
        fillDocList(true);
        NodeIdentifierDown = new ImageNodeId();
    }

    private void treeDocuments_MouseLeave(Object sender, EventArgs e) throws Exception {
        treeDocuments.Cursor = Cursors.Default;
        NodeIdentifierDown = new ImageNodeId();
    }

    private void treeDocuments_AfterExpand(Object sender, TreeViewEventArgs e) throws Exception {
        ExpandedCats.Add(((ImageNodeId)e.Node.Tag).PriKey);
    }

    private void treeDocuments_AfterCollapse(Object sender, TreeViewEventArgs e) throws Exception {
        for (int i = 0;i < ExpandedCats.Count;i++)
        {
            if (ExpandedCats[i] == ((ImageNodeId)e.Node.Tag).PriKey)
            {
                ExpandedCats.RemoveAt(i);
                return ;
            }
             
        }
    }

    /**
    * Invalidates some or all of the image settings.  This will cause those settings to be recalculated, either immediately, or when the current ApplySettings thread is finished.  If supplied settings is ApplySettings.NONE, then that part will be skipped.
    */
    private void invalidateSettings(ImageSettingFlags settings, boolean reloadZoomTransCrop) throws Exception {
        boolean[] mountIdxsToUpdate = new boolean[this.ImagesCur.Length];
        if (ImagesCur.Length == 1)
        {
            //An image is currently showing.
            mountIdxsToUpdate[0] = true;
        }
        else //Mark the document to be updated.
        if (ImagesCur.Length == 4)
        {
            //4 bite-wing mount is currently selected.
            if (IdxSelectedInMount >= 0)
            {
                //The current active document will be updated.
                mountIdxsToUpdate[IdxSelectedInMount] = true;
            }
             
        }
          
        InvalidateSettings(settings, reloadZoomTransCrop, mountIdxsToUpdate);
    }

    /**
    * Invalidates some or all of the image settings.  This will cause those settings to be recalculated, either immediately, or when the current ApplySettings thread is finished.  If supplied settings is ApplySettings.NONE, then that part will be skipped.
    */
    private void invalidateSettings(ImageSettingFlags settings, boolean reloadZoomTransCrop, boolean[] mountIdxsToUpdate) throws Exception {
        if (this.InvokeRequired)
        {
            InvalidatesettingsCallback c = new InvalidatesettingsCallback() 
              { 
                public System.Void invoke(ImageSettingFlags settings, System.Boolean reloadZoomTransCrop) throws Exception {
                    invalidateSettings(settings, reloadZoomTransCrop);
                }

                public List<InvalidatesettingsCallback> getInvocationList() throws Exception {
                    List<InvalidatesettingsCallback> ret = new ArrayList<InvalidatesettingsCallback>();
                    ret.add(this);
                    return ret;
                }
            
              };
            Invoke(c, new Object[]{ settings, reloadZoomTransCrop });
            return ;
        }
         
        //Do not allow image rendering when the paint tools are disabled. This will disable the display image when a folder or non-image document is selected, or when no document is currently selected. The ToolBarPaint.Enabled boolean is controlled in SelectTreeNode() and is set to true only if a valid image is currently being displayed.
        if (treeDocuments.SelectedNode == null || treeDocuments.SelectedNode.Tag == null)
        {
            eraseCurrentImages();
            return ;
        }
         
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.None || nodeId.NodeType == ImageNodeType.Category)
        {
            eraseCurrentImages();
            return ;
        }
         
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            if (!ToolBarPaint.Enabled)
            {
                eraseCurrentImages();
                return ;
            }
             
        }
         
        if (nodeId.NodeType == ImageNodeType.Doc || nodeId.NodeType == ImageNodeType.Eob || nodeId.NodeType == ImageNodeType.Amd)
        {
            if (reloadZoomTransCrop)
            {
                //Reloading the image settings only happens when a new image is selected, pasted, scanned, etc...
                //Therefore, the is no need for any current image processing anymore (it would be on a stale image).
                killThreadImageUpdate();
                RefSupport refVar___4 = new RefSupport();
                RefSupport refVar___5 = new RefSupport();
                RefSupport refVar___6 = new RefSupport();
                RefSupport refVar___7 = new RefSupport();
                ReloadZoomTransCrop(WidthsImagesCur[0], HeightsImagesCur[0], DocSelected, new Rectangle(0, 0, pictureBoxMain.Width, pictureBoxMain.Height), refVar___4, refVar___5, refVar___6, refVar___7);
                ZoomImage = refVar___4.getValue();
                ZoomLevel = refVar___5.getValue();
                ZoomOverall = refVar___6.getValue();
                PointTranslation = refVar___7.getValue();
                RectCrop = new Rectangle(0, 0, -1, -1);
            }
             
        }
         
        ImageSettingFlagsInvalidated |= settings;
        synchronized (EventWaitHandleSettings)
        {
            {
                //DocSelected is an individual document instance. Assigning a new document to DocForSettings here does not
                //negatively effect our image application thread, because the thread either will keep its current
                //reference to the old document, or will apply the settings with this newly assigned document. In either
                //case, the output is either what we expected originally, or is a more accurate image for more recent
                //settings. We lock here so that we are sure that the resulting document and setting tuple represent
                //a single point in time.
                //Does not actually lock the EventWaitHandleSettings object, but rather locks the variables in the block.
                MountIdxsFlaggedForUpdate = (boolean[])mountIdxsToUpdate.Clone();
                ImageSettingFlagsForSettings = ImageSettingFlagsInvalidated;
                NodeTypeForSettings = ((ImageNodeId)treeDocuments.SelectedNode.Tag).NodeType;
                if (NodeTypeForSettings == ImageNodeType.Doc)
                {
                    DocForSettings = DocSelected.copy();
                }
                 
            }
        }
        //Tell the thread to start processing (as soon as the thread is created, or as soon as otherwise
        //possible). Set() has no effect if the handle is already signaled.
        EventWaitHandleSettings.Set();
        if (ThreadImageUpdate == null)
        {
            ThreadImageUpdate = new Thread((ThreadStart)());
            ThreadImageUpdate.IsBackground = true;
            ThreadImageUpdate.Start();
        }
         
        ImageSettingFlagsInvalidated = ImageSettingFlags.NONE;
    }

    /**
    * Applies crop and colors. Then, paints ImageRenderingNow onto pictureBoxMain.
    */
    private void worker() throws Exception {
        while (true)
        {
            try
            {
                //Wait indefinitely for a signal to start processing again. Since the OS handles this function,
                //this thread will not run even a single process cycle until a signal is recieved. This is ideal,
                //since it means that we do not waste any CPU cycles when image processing is not currently needed.
                //At the same time, this function allows us to keep a single thread for as long as possible, so
                //that we do not need to destroy and recreate this thread (except in rare circumstances, such as
                //the deletion of the current image).
                EventWaitHandleSettings.WaitOne(-1, false);
                //The DocForSettings may have been reset several times at this point by calls to InvalidateSettings(), but that cannot hurt
                //us here because it simply means that we are getting even more current information than we had when this thread was
                //signaled to start. We lock here so that we are sure that the resulting document and setting tuple represent
                //a single point in time.
                OpenDentBusiness.Document docForSettings;
                ImageSettingFlags imageSettingFlagsForSettings = ImageSettingFlags.NONE;
                boolean[] idxDocsFlaggedForUpdate = new boolean[]();
                synchronized (EventWaitHandleSettings)
                {
                    {
                        //Does not actually lock the EventWaitHandleSettings object.
                        docForSettings = DocForSettings;
                        imageSettingFlagsForSettings = ImageSettingFlagsForSettings;
                        idxDocsFlaggedForUpdate = MountIdxsFlaggedForUpdate;
                    }
                }
                if (NodeTypeForSettings == ImageNodeType.Doc)
                {
                    //Perform cropping and colorfunction here if one of the two flags is set. Rotation, flip, zoom and translation are
                    //taken care of in RenderCurrentImage().
                    if ((imageSettingFlagsForSettings & ImageSettingFlags.COLORFUNCTION) != ImageSettingFlags.NONE || (imageSettingFlagsForSettings & ImageSettingFlags.CROP) != ImageSettingFlags.NONE)
                    {
                        //Ensure that memory management for the ImageRenderingNow is performed in the worker thread, otherwise the main thread
                        //will be slowed when it has to cleanup dozens of old renderImages, which causes a temporary pause in operation.
                        if (ImageRenderingNow != null)
                        {
                            //Done like this so that the ImageRenderingNow is cleared in a single atomic line of code (in case the thread is
                            //killed during this step), so that we don't end up with a pointer to a disposed image in the ImageRenderingNow.
                            Bitmap oldRenderImage = ImageRenderingNow;
                            ImageRenderingNow = null;
                            oldRenderImage.Dispose();
                            oldRenderImage = null;
                        }
                         
                        //currentImages[] is guaranteed to exist and be the current. If currentImages gets updated, this thread
                        //gets aborted with a call to KillMyThread(). The only place currentImages[] is invalid is in a call to
                        //EraseCurrentImage(), but at that point, this thread has been terminated.
                        ImageRenderingNow = ImageHelper.ApplyDocumentSettingsToImage(docForSettings, ImagesCur[IdxSelectedInMount], ImageSettingFlags.CROP | ImageSettingFlags.COLORFUNCTION);
                    }
                     
                    //Make the current ImageRenderingNow visible in the picture box, and perform rotation, flip, zoom, and translation on
                    //the ImageRenderingNow.
                    RenderCurrentImage(docForSettings, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount], ZoomImage * ZoomOverall, PointTranslation);
                }
                else if (NodeTypeForSettings == ImageNodeType.Mount)
                {
                    ImageHelper.renderMountFrames(ImageRenderingNow,MountItemsForSelected,IdxSelectedInMount);
                    //Render only the modified image over the old mount image.
                    //A null document can happen when a new image frame is selected, but there is no image in that frame.
                    if (docForSettings != null && imageSettingFlagsForSettings != ImageSettingFlags.NONE)
                    {
                        for (int i = 0;i < idxDocsFlaggedForUpdate.Length;i++)
                        {
                            if (idxDocsFlaggedForUpdate[i])
                            {
                                //js 11/12/11 I don't understand why we keep reusing the docForSettings for multiple docs.  Revisit this when mounts are enhanced.
                                ImageHelper.RenderImageIntoMount(ImageRenderingNow, MountItemsForSelected[i], ImagesCur[i], docForSettings);
                            }
                             
                        }
                    }
                     
                    RenderCurrentImage(new OpenDentBusiness.Document(), ImageRenderingNow.Width, ImageRenderingNow.Height, ZoomImage * ZoomOverall, PointTranslation);
                }
                else if (NodeTypeForSettings == ImageNodeType.Eob || NodeTypeForSettings == ImageNodeType.Amd)
                {
                    if ((imageSettingFlagsForSettings & ImageSettingFlags.COLORFUNCTION) != ImageSettingFlags.NONE || (imageSettingFlagsForSettings & ImageSettingFlags.CROP) != ImageSettingFlags.NONE)
                    {
                        if (ImageRenderingNow != null)
                        {
                            Bitmap oldRenderImage = ImageRenderingNow;
                            ImageRenderingNow = null;
                            oldRenderImage.Dispose();
                            oldRenderImage = null;
                        }
                         
                        ImageRenderingNow = ImageHelper.ApplyDocumentSettingsToImage(docForSettings, ImagesCur[IdxSelectedInMount], ImageSettingFlags.CROP | ImageSettingFlags.COLORFUNCTION);
                    }
                     
                    //ImageRenderingNow=ImagesCur[IdxSelectedInMount];//no crop or color settings in an eob
                    RenderCurrentImage(null, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount], ZoomImage * ZoomOverall, PointTranslation);
                }
                   
            }
            catch (ThreadAbortException __dummyCatchVar11)
            {
                return ;
            }
            catch (Exception __dummyCatchVar12)
            {
            }
        
        }
    }

    //Exit as requested. This can happen when the current document is being deleted,
    //or during shutdown of the program.
    //We don't draw anyting on error (because most of the time it will be due to the current selection state).
    /**
    * Kills the image processing thread if it is currently running.
    */
    private void killThreadImageUpdate() throws Exception {
        xRayImageController.KillXRayThread();
        //Stop the current xRay image thread if it is running.
        if (ThreadImageUpdate != null)
        {
            //Clear any previous image processing.
            if (ThreadImageUpdate.IsAlive)
            {
                ThreadImageUpdate.Abort();
                //this is not recommended because it results in an exception.  But it seems to work.
                ThreadImageUpdate.Join();
            }
             
            //Wait for thread to stop execution.
            ThreadImageUpdate = null;
        }
         
    }

    /**
    * Handles rendering to the PictureBox of the image in its current state. The image calculations are not performed here, only rendering of the image is performed here, so that we can guarantee a fast display.
    */
    private void renderCurrentImage(OpenDentBusiness.Document docCopy, int originalWidth, int originalHeight, float zoom, PointF translation) throws Exception {
        if (!this.Visible)
        {
            return ;
        }
         
        //Helps protect against simultaneous access to the picturebox in both the main and render worker threads.
        if (pictureBoxMain.InvokeRequired)
        {
            RenderImageCallback c = new RenderImageCallback() 
              { 
                public System.Void invoke(OpenDentBusiness.Document docCopy, System.Int32 originalWidth, System.Int32 originalHeight, System.Single zoom, PointF translation) throws Exception {
                    renderCurrentImage(docCopy, originalWidth, originalHeight, zoom, translation);
                }

                public List<RenderImageCallback> getInvocationList() throws Exception {
                    List<RenderImageCallback> ret = new ArrayList<RenderImageCallback>();
                    ret.add(this);
                    return ret;
                }
            
              };
            Invoke(c, new Object[]{ docCopy, originalWidth, originalHeight, zoom, translation });
            return ;
        }
         
        int width = pictureBoxMain.Bounds.Width;
        int height = pictureBoxMain.Bounds.Height;
        if (width <= 0 || height <= 0)
        {
            return ;
        }
         
        Bitmap backBuffer = new Bitmap(width, height);
        Graphics g = Graphics.FromImage(backBuffer);
        try
        {
            g.Clear(pictureBoxMain.BackColor);
            g.Transform = getScreenMatrix(docCopy,originalWidth,originalHeight,zoom,translation);
            g.DrawImage(ImageRenderingNow, 0, 0);
            if (RectCrop.Width > 0 && RectCrop.Height > 0)
            {
                //Must be drawn last so it is on top.
                g.ResetTransform();
                g.DrawRectangle(Pens.Blue, RectCrop);
            }
             
            g.Dispose();
            //Cleanup old back-buffer.
            if (pictureBoxMain.Image != null)
            {
                pictureBoxMain.Image.Dispose();
            }
             
            //Make sure that the calling thread performs the memory cleanup, instead of relying
            //on the memory-manager in the main thread (otherwise the graphics get spotty sometimes).
            pictureBoxMain.Image = backBuffer;
            pictureBoxMain.Refresh();
        }
        catch (Exception __dummyCatchVar13)
        {
            g.Dispose();
        }
    
    }

    //Tried this.  Program crashes when any small window is dragged across the picturebox.
    //backBuffer.Dispose();
    //backBuffer=null;
    private void treeDocuments_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        TreeNode clickedNode = treeDocuments.GetNodeAt(e.Location);
        if (clickedNode == null)
        {
            return ;
        }
         
        ImageNodeId nodeId = (ImageNodeId)clickedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.None)
        {
            return ;
        }
         
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            FormMountEdit fme = new FormMountEdit(MountSelected);
            fme.ShowDialog();
            //Edits the MountSelected object directly and updates and changes to the database as well.
            fillDocList(true);
            return ;
        }
        else //Refresh tree in case description for the mount changed.
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            OpenDentBusiness.Document nodeDoc = Documents.getByNum(nodeId.PriKey);
            String ext = ImageStore.getExtension(nodeDoc);
            if (StringSupport.equals(ext, ".jpg") || StringSupport.equals(ext, ".jpeg") || StringSupport.equals(ext, ".gif"))
            {
                return ;
            }
             
            try
            {
                //We allow anything which ends with a different extention to be viewed in the windows fax viewer.
                //Specifically, multi-page faxes can be viewed more easily by one of our customers using the fax
                //viewer. On Unix systems, it is imagined that an equivalent viewer will launch to allow the image
                //to be viewed.
                Process.Start(ImageStore.getFilePath(nodeDoc,PatFolder));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        
        }
          
    }

    private void toolBarInfo_Click() throws Exception {
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.None)
        {
            return ;
        }
         
        if (nodeId.NodeType == ImageNodeType.Mount)
        {
            FormMountEdit form = new FormMountEdit(MountSelected);
            form.ShowDialog();
            //Edits the MountSelected object directly and updates and changes to the database as well.
            fillDocList(true);
        }
        else //Refresh tree in case description for the mount changed.}
        if (nodeId.NodeType == ImageNodeType.Doc)
        {
            //The FormDocInfo object updates the DocSelected and stores the changes in the database as well.
            FormDocInfo formDocInfo2 = new FormDocInfo(PatCur, DocSelected, GetCurrentFolderName(treeDocuments.SelectedNode));
            formDocInfo2.ShowDialog();
            if (formDocInfo2.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            fillDocList(true);
        }
          
    }

    /**
    * This button is disabled for mounts, in which case this code is never called.
    */
    private void toolBarZoomIn_Click() throws Exception {
        ZoomLevel++;
        PointF c = new PointF(pictureBoxMain.ClientRectangle.Width / 2.0f, pictureBoxMain.ClientRectangle.Height / 2.0f);
        PointF p = new PointF(c.X - PointTranslation.X, c.Y - PointTranslation.Y);
        PointTranslation = new PointF(PointTranslation.X - p.X, PointTranslation.Y - p.Y);
        ZoomOverall = (float)Math.Pow(2, ZoomLevel);
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh display.
    /**
    * This button is disabled for mounts, in which case this code is never called.
    */
    private void toolBarZoomOut_Click() throws Exception {
        ZoomLevel--;
        PointF c = new PointF(pictureBoxMain.ClientRectangle.Width / 2.0f, pictureBoxMain.ClientRectangle.Height / 2.0f);
        PointF p = new PointF(c.X - PointTranslation.X, c.Y - PointTranslation.Y);
        PointTranslation = new PointF(PointTranslation.X + p.X / 2.0f, PointTranslation.Y + p.Y / 2.0f);
        ZoomOverall = (float)Math.Pow(2, ZoomLevel);
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh display.
    /**
    * This button is disabled for mounts, in which case this code is never called.
    */
    private void toolBarZoom100_Click() throws Exception {
        ZoomLevel = 0;
        ZoomOverall = 1;
        ZoomImage = 1;
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh display.
    private void deleteThumbnailImage(OpenDentBusiness.Document doc) throws Exception {
        ImageStore.deleteThumbnailImage(doc,PatFolder);
    }

    private void toolBarFlip_Click() throws Exception {
        if (((ImageNodeId)treeDocuments.SelectedNode.Tag).NodeType == ImageNodeType.None || DocSelected == null)
        {
            return ;
        }
         
        DocSelected.IsFlipped = !DocSelected.IsFlipped;
        //Since the document is always flipped and then rotated in the mathematical functions below, and since we
        //always want the selected image to rotate left to right no matter what orientation the image is in,
        //we must modify the document settings so that the document will always be flipped left to right, but
        //in such a way that the flipping always happens before the rotation.
        if (DocSelected.DegreesRotated == 90 || DocSelected.DegreesRotated == 270)
        {
            DocSelected.DegreesRotated *= -1;
            while (DocSelected.DegreesRotated < 0)
            {
                DocSelected.DegreesRotated += 360;
            }
            DocSelected.DegreesRotated = (short)(DocSelected.DegreesRotated % 360);
        }
         
        Documents.update(DocSelected);
        deleteThumbnailImage(DocSelected);
        invalidateSettings(ImageSettingFlags.FLIP,false);
    }

    //Refresh display.
    private void toolBarRotateL_Click() throws Exception {
        if (((ImageNodeId)treeDocuments.SelectedNode.Tag).NodeType == ImageNodeType.None || DocSelected == null)
        {
            return ;
        }
         
        DocSelected.DegreesRotated -= 90;
        while (DocSelected.DegreesRotated < 0)
        {
            DocSelected.DegreesRotated += 360;
        }
        Documents.update(DocSelected);
        deleteThumbnailImage(DocSelected);
        invalidateSettings(ImageSettingFlags.ROTATE,false);
    }

    //Refresh display.
    private void toolBarRotateR_Click() throws Exception {
        if (((ImageNodeId)treeDocuments.SelectedNode.Tag).NodeType == ImageNodeType.None || DocSelected == null)
        {
            return ;
        }
         
        DocSelected.DegreesRotated += 90;
        DocSelected.DegreesRotated %= 360;
        Documents.update(DocSelected);
        deleteThumbnailImage(DocSelected);
        invalidateSettings(ImageSettingFlags.ROTATE,false);
    }

    //Refresh display.
    /**
    * Keeps the back buffer for the picture box to be the same in dimensions as the picture box itself.
    */
    private void pictureBox1_SizeChanged(Object sender, EventArgs e) throws Exception {
        if (this.DesignMode)
        {
            return ;
        }
         
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh display.
    /**
    * 
    */
    private void pictureBox1_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        PointMouseDown = new Point(e.X, e.Y);
        IsMouseDown = true;
        PointImageCur = new PointF(PointTranslation.X, PointTranslation.Y);
    }

    private void pictureBox1_MouseHover(Object sender, EventArgs e) throws Exception {
        if (ToolBarPaint.getButtons().get___idx("Hand") == null)
        {
            pictureBoxMain.Cursor = Cursors.Hand;
            return ;
        }
         
        if (ToolBarPaint.getButtons().get___idx("Hand").getPushed())
        {
            //Hand mode.
            pictureBoxMain.Cursor = Cursors.Hand;
        }
        else
        {
            pictureBoxMain.Cursor = Cursors.Default;
        } 
    }

    private void pictureBox1_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!IsMouseDown)
        {
            return ;
        }
         
        IsDragging = true;
        if (treeDocuments.SelectedNode == null)
        {
            return ;
        }
         
        if (((ImageNodeId)treeDocuments.SelectedNode.Tag).NodeType == ImageNodeType.None)
        {
            return ;
        }
         
        //when hand button is not visible, it's always hand mode
        if (ToolBarPaint.getButtons().get___idx("Hand") == null || ToolBarPaint.getButtons().get___idx("Hand").getPushed())
        {
            //Hand mode.
            PointTranslation = new PointF(PointImageCur.X + (e.Location.X - PointMouseDown.X), PointImageCur.Y + (e.Location.Y - PointMouseDown.Y));
        }
        else if (ToolBarPaint.getButtons().get___idx("Crop") != null && ToolBarPaint.getButtons().get___idx("Crop").getPushed())
        {
            float[] intersect = ODMathLib.IntersectRectangles(Math.Min(e.Location.X, PointMouseDown.X), Math.Min(e.Location.Y, PointMouseDown.Y), Math.Abs(e.Location.X - PointMouseDown.X), Math.Abs(e.Location.Y - PointMouseDown.Y), pictureBoxMain.ClientRectangle.X, pictureBoxMain.ClientRectangle.Y, pictureBoxMain.ClientRectangle.Width - 1, pictureBoxMain.ClientRectangle.Height - 1);
            if (intersect.Length < 0)
            {
                RectCrop = new Rectangle(0, 0, -1, -1);
            }
            else
            {
                RectCrop = new Rectangle((int)intersect[0], (int)intersect[1], (int)intersect[2], (int)intersect[3]);
            } 
        }
          
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh display.
    /**
    * Returns the index in the DocsInMount array of the given location (relative to the upper left-hand corner of the pictureBoxMain control) or -1 if the location is outside all documents in the current mount. A mount must be currently selected to call this function.
    */
    private int getIdxAtMountLocation(Point location) throws Exception {
        PointF relativeLocation = new PointF((location.X - PointTranslation.X) / (ZoomImage * ZoomOverall) + MountSelected.Width / 2, (location.Y - PointTranslation.Y) / (ZoomImage * ZoomOverall) + MountSelected.Height / 2);
        for (int i = 0;i < MountItemsForSelected.Count;i++)
        {
            //Enumerate the image locations.
            RectangleF itemLocation = new RectangleF(MountItemsForSelected[i].Xpos, MountItemsForSelected[i].Ypos, MountItemsForSelected[i].Width, MountItemsForSelected[i].Height);
            if (itemLocation.Contains(relativeLocation))
            {
                return i;
            }
             
        }
        return -1;
    }

    //No document selected in the current mount.
    private void pictureBox1_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        boolean wasDragging = IsDragging;
        IsMouseDown = false;
        IsDragging = false;
        if (treeDocuments.SelectedNode == null)
        {
            return ;
        }
         
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType == ImageNodeType.None)
        {
            return ;
        }
         
        //if button is not visible, it's always hand mode.
        if (ToolBarPaint.getButtons().get___idx("Hand") == null || ToolBarPaint.getButtons().get___idx("Hand").getPushed())
        {
            if (e.Button != MouseButtons.Left || wasDragging)
            {
                return ;
            }
             
            if (nodeId.NodeType == ImageNodeType.Mount)
            {
                //The user may be trying to select an individual image within the current mount.
                IdxSelectedInMount = getIdxAtMountLocation(PointMouseDown);
                //Assume no item will be selected and enable tools again if an item was actually selected.
                enableTreeItemTools(true,true,true,true,false,false,false,true,true,true,false,false,false,true);
                for (int j = 0;j < MountItemsForSelected.Count;j++)
                {
                    if (MountItemsForSelected[j].OrdinalPos == IdxSelectedInMount)
                    {
                        if (DocsInMount[j] != null)
                        {
                            DocSelected = DocsInMount[j];
                            setBrightnessAndContrast();
                            enableTreeItemTools(true,true,false,true,false,true,false,true,true,true,true,true,true,true);
                        }
                         
                    }
                     
                }
                ToolBarPaint.Invalidate();
                if (IdxSelectedInMount < 0)
                {
                    //The current selection was unselected.
                    xRayImageController.KillXRayThread();
                }
                 
                //Stop xray capture, because it relies on the current selection to place images.
                invalidateSettings(ImageSettingFlags.ALL,false);
            }
             
        }
        else
        {
            //crop mode
            if (RectCrop.Width <= 0 || RectCrop.Height <= 0)
            {
                return ;
            }
             
            if (!MsgBox.show(this,true,"Crop to Rectangle?"))
            {
                RectCrop = new Rectangle(0, 0, -1, -1);
                invalidateSettings(ImageSettingFlags.NONE,false);
                return ;
            }
             
            //Refresh display (since message box was covering).
            float cropZoom = ZoomImage * ZoomOverall;
            PointF cropTrans = PointTranslation;
            PointF cropPoint1 = ScreenPointToUnalteredDocumentPoint(RectCrop.Location, DocSelected, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount], cropZoom, cropTrans);
            PointF cropPoint2 = ScreenPointToUnalteredDocumentPoint(new Point(RectCrop.Location.X + RectCrop.Width, RectCrop.Location.Y + RectCrop.Height), DocSelected, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount], cropZoom, cropTrans);
            //cropPoint1 and cropPoint2 together define an axis-aligned bounding area, or our crop area.
            //However, the two points have no guaranteed order, thus we must sort them using Math.Min.
            Rectangle rawCropRect = new Rectangle((int)Math.Round((double)Math.Min(cropPoint1.X, cropPoint2.X)), (int)Math.Round((double)Math.Min(cropPoint1.Y, cropPoint2.Y)), (int)Math.Ceiling((double)Math.Abs(cropPoint1.X - cropPoint2.X)), (int)Math.Ceiling((double)Math.Abs(cropPoint1.Y - cropPoint2.Y)));
            //We must also intersect the old cropping rectangle with the new cropping rectangle, so that part of
            //the image does not come back as a result of multiple crops.
            Rectangle oldCropRect = DocCropRect(DocSelected, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount]);
            float[] finalCropRect = ODMathLib.IntersectRectangles(rawCropRect.X, rawCropRect.Y, rawCropRect.Width, rawCropRect.Height, oldCropRect.X, oldCropRect.Y, oldCropRect.Width, oldCropRect.Height);
            //Will return a null intersection when the user chooses a crop rectangle which is
            //entirely outside the current visible portion of the image. Can also return a zero-area rect,
            //if the entire image is cropped away.
            if (finalCropRect.Length != 4 || finalCropRect[2] <= 0 || finalCropRect[3] <= 0)
            {
                RectCrop = new Rectangle(0, 0, -1, -1);
                invalidateSettings(ImageSettingFlags.NONE,false);
                return ;
            }
             
            //Refresh display (since message box was covering).
            Rectangle prevCropRect = DocCropRect(DocSelected, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount]);
            DocSelected.CropX = (int)finalCropRect[0];
            DocSelected.CropY = (int)finalCropRect[1];
            DocSelected.CropW = (int)Math.Ceiling(finalCropRect[2]);
            DocSelected.CropH = (int)Math.Ceiling(finalCropRect[3]);
            Documents.update(DocSelected);
            if (nodeId.NodeType == ImageNodeType.Doc)
            {
                deleteThumbnailImage(DocSelected);
                Rectangle newCropRect = DocCropRect(DocSelected, WidthsImagesCur[IdxSelectedInMount], HeightsImagesCur[IdxSelectedInMount]);
                //Update the location of the image so that the cropped portion of the image does not move in screen space.
                PointF prevCropCenter = new PointF(prevCropRect.X + prevCropRect.Width / 2.0f, prevCropRect.Y + prevCropRect.Height / 2.0f);
                PointF newCropCenter = new PointF(newCropRect.X + newCropRect.Width / 2.0f, newCropRect.Y + newCropRect.Height / 2.0f);
                PointF[] imageCropCenters = new PointF[]{ prevCropCenter, newCropCenter };
                Matrix docMat = getDocumentFlippedRotatedMatrix(DocSelected);
                docMat.Scale(cropZoom, cropZoom);
                docMat.TransformPoints(imageCropCenters);
                PointTranslation = new PointF(PointTranslation.X + (imageCropCenters[1].X - imageCropCenters[0].X), PointTranslation.Y + (imageCropCenters[1].Y - imageCropCenters[0].Y));
            }
             
            RectCrop = new Rectangle(0, 0, -1, -1);
            invalidateSettings(ImageSettingFlags.CROP,false);
        } 
    }

    private PointF mountSpaceToScreenSpace(PointF p) throws Exception {
        PointF relvec = new PointF(p.X / MountSelected.Width - 0.5f, p.Y / MountSelected.Height - 0.5f);
        return new PointF(PointTranslation.X + relvec.X * MountSelected.Width * ZoomImage * ZoomOverall, PointTranslation.Y + relvec.Y * MountSelected.Height * ZoomImage * ZoomOverall);
    }

    private void setBrightnessAndContrast() throws Exception {
        if (DocSelected.WindowingMax == 0)
        {
            //The document brightness/contrast settings have never been set. By default, we use settings
            //which do not alter the original image.
            sliderBrightnessContrast.setMinVal(0);
            sliderBrightnessContrast.setMaxVal(255);
        }
        else
        {
            sliderBrightnessContrast.setMinVal(DocSelected.WindowingMin);
            sliderBrightnessContrast.setMaxVal(DocSelected.WindowingMax);
        } 
    }

    private void brightnessContrastSlider_Scroll(Object sender, EventArgs e) throws Exception {
        if (DocSelected == null)
        {
            return ;
        }
         
        DocSelected.WindowingMin = sliderBrightnessContrast.getMinVal();
        DocSelected.WindowingMax = sliderBrightnessContrast.getMaxVal();
        invalidateSettings(ImageSettingFlags.COLORFUNCTION,false);
    }

    private void brightnessContrastSlider_ScrollComplete(Object sender, EventArgs e) throws Exception {
        if (DocSelected == null)
        {
            return ;
        }
         
        Documents.update(DocSelected);
        deleteThumbnailImage(DocSelected);
        invalidateSettings(ImageSettingFlags.COLORFUNCTION,false);
    }

    /**
    * Handles a change in selection of the xRay capture button.
    */
    private void toolBarCapture_Click() throws Exception {
        if (treeDocuments.SelectedNode == null)
        {
            return ;
        }
         
        if (ToolBarMain.getButtons().get___idx("Capture").getPushed())
        {
            ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
            //ComputerPref computerPrefs=ComputerPrefs.GetForLocalComputer();
            xRayImageController.SensorType = ComputerPrefs.getLocalComputer().SensorType;
            xRayImageController.PortNumber = ComputerPrefs.getLocalComputer().SensorPort;
            xRayImageController.Binned = ComputerPrefs.getLocalComputer().SensorBinned;
            xRayImageController.ExposureLevel = ComputerPrefs.getLocalComputer().SensorExposure;
            if (nodeId.NodeType != ImageNodeType.Mount)
            {
                //No mount is currently selected.
                //Show the user that they are performing an image capture by generating a new mount.
                Mount mount = new Mount();
                mount.DateCreated = DateTimeOD.getToday();
                mount.Description = "unnamed capture";
                mount.DocCategory = getCurrentCategory();
                mount.ImgType = ImageType.Mount;
                mount.PatNum = PatCur.PatNum;
                int border = Math.Max(xRayImageController.SensorSize.Width, xRayImageController.SensorSize.Height) / 24;
                mount.Width = 4 * xRayImageController.SensorSize.Width + 5 * border;
                mount.Height = xRayImageController.SensorSize.Height + 2 * border;
                mount.MountNum = Mounts.insert(mount);
                MountItem mountItem = new MountItem();
                mountItem.MountNum = mount.MountNum;
                mountItem.Width = xRayImageController.SensorSize.Width;
                mountItem.Height = xRayImageController.SensorSize.Height;
                mountItem.Ypos = border;
                mountItem.OrdinalPos = 1;
                mountItem.Xpos = border;
                MountItems.insert(mountItem);
                mountItem.OrdinalPos = 0;
                mountItem.Xpos = mountItem.Width + 2 * border;
                MountItems.insert(mountItem);
                mountItem.OrdinalPos = 2;
                mountItem.Xpos = 2 * mountItem.Width + 3 * border;
                MountItems.insert(mountItem);
                mountItem.OrdinalPos = 3;
                mountItem.Xpos = 3 * mountItem.Width + 4 * border;
                MountItems.insert(mountItem);
                fillDocList(false);
                selectTreeNode(getNodeById(makeIdMount(mount.MountNum)));
                sliderBrightnessContrast.setMinVal(PrefC.getInt(PrefName.ImageWindowingMin));
                sliderBrightnessContrast.setMaxVal(PrefC.getInt(PrefName.ImageWindowingMax));
            }
            else if (nodeId.NodeType == ImageNodeType.Mount)
            {
                //A mount is currently selected. We must allow the user to insert new images into partially complete mounts.
                //Clear the visible selection so that the user will know when the device is ready for xray exposure.
                ImageHelper.renderMountFrames(ImageRenderingNow,MountItemsForSelected,-1);
                RenderCurrentImage(new OpenDentBusiness.Document(), ImageRenderingNow.Width, ImageRenderingNow.Height, ZoomImage * ZoomOverall, PointTranslation);
            }
              
            //Here we can only allow access to the capture button during a capture, because it is too complicated and hard for a
            //user to follow what is going on if they use the other tools when a capture is taking place.
            enableAllTools(false);
            ToolBarMain.getButtons().get___idx("Capture").setEnabled(true);
            ToolBarMain.Invalidate();
            xRayImageController.CaptureXRay();
        }
        else
        {
            //The user unselected the image capture button, so cancel the current image capture.
            xRayImageController.KillXRayThread();
        } 
    }

    //Stop current xRay capture and call OnCaptureFinalize() when done.
    /**
    * Called when the image capture device is ready for exposure.
    */
    private void onCaptureReady(Object sender, EventArgs e) throws Exception {
        getNextUnusedMountItem();
        invalidateSettings(ImageSettingFlags.NONE,false);
    }

    //Refresh the selection box change (does not do any image processing here).
    /**
    * Called on successful capture of image.
    */
    private void onCaptureComplete(Object sender, EventArgs e) throws Exception {
        if (this.InvokeRequired)
        {
            CaptureCallback c = new CaptureCallback() 
              { 
                public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                    onCaptureComplete(sender, e);
                }

                public List<CaptureCallback> getInvocationList() throws Exception {
                    List<CaptureCallback> ret = new ArrayList<CaptureCallback>();
                    ret.add(this);
                    return ret;
                }
            
              };
            Invoke(c, new Object[]{ sender, e });
            return ;
        }
         
        if (IdxSelectedInMount < 0 || DocsInMount[IdxSelectedInMount] != null)
        {
            //Mount is full.
            xRayImageController.KillXRayThread();
            return ;
        }
         
        //Depending on the device being captured from, we need to rotate the images returned from the device by a certain
        //angle, and we need to place the returned images in a specific order within the mount slots. Later, we will allow
        //the user to define the rotations and slot orders, but for now, they will be hard-coded.
        short rotationAngle = 0;
        switch(IdxSelectedInMount)
        {
            case (0): 
                rotationAngle = 90;
                break;
            case (1): 
                rotationAngle = 90;
                break;
            case (2): 
                rotationAngle = 270;
                break;
            default: 
                //3
                rotationAngle = 270;
                break;
        
        }
        //Create the document object in the database for this mount image.
        Bitmap capturedImage = xRayImageController.capturedImage;
        OpenDentBusiness.Document doc = ImageStore.ImportImageToMount(capturedImage, rotationAngle, MountItemsForSelected[IdxSelectedInMount].MountItemNum, getCurrentCategory(), PatCur);
        ImagesCur[IdxSelectedInMount] = capturedImage;
        WidthsImagesCur[IdxSelectedInMount] = capturedImage.Width;
        HeightsImagesCur[IdxSelectedInMount] = capturedImage.Height;
        DocsInMount[IdxSelectedInMount] = doc;
        DocSelected = doc;
        setBrightnessAndContrast();
        //Refresh image in in picture box.
        invalidateSettings(ImageSettingFlags.ALL,false);
        //This capture was successful. Keep capturing more images until the capture is manually aborted.
        //This will cause calls to OnCaptureBegin(), then OnCaptureFinalize().
        xRayImageController.CaptureXRay();
    }

    /**
    * Called when the entire sequence of image captures is complete (possibly because of failure, or a full mount among other things).
    */
    private void onCaptureFinalize(Object sender, EventArgs e) throws Exception {
        if (this.InvokeRequired)
        {
            CaptureCallback c = new CaptureCallback() 
              { 
                public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                    onCaptureFinalize(sender, e);
                }

                public List<CaptureCallback> getInvocationList() throws Exception {
                    List<CaptureCallback> ret = new ArrayList<CaptureCallback>();
                    ret.add(this);
                    return ret;
                }
            
              };
            Invoke(c, new Object[]{ sender, e });
            return ;
        }
         
        ToolBarMain.getButtons().get___idx("Capture").setPushed(false);
        ToolBarMain.Invalidate();
        enableAllTools(true);
        if (IdxSelectedInMount > 0 && DocsInMount[IdxSelectedInMount] != null)
        {
            //The capture finished in a state where a mount item is selected.
            enableTreeItemTools(true,true,false,true,false,true,false,true,true,true,true,true,true,true);
        }
        else
        {
            //The capture finished without a mount item selected (so the mount itself is considered to be selected).
            enableTreeItemTools(true,true,true,true,false,false,false,true,true,true,false,false,false,true);
        } 
    }

    private void getNextUnusedMountItem() throws Exception {
        //Advance selection box to the location where the next image will capture to.
        if (IdxSelectedInMount < 0)
        {
            IdxSelectedInMount = 0;
        }
         
        int hotStart = IdxSelectedInMount;
        int d = IdxSelectedInMount;
        do
        {
            if (DocsInMount[IdxSelectedInMount] == null)
            {
                return ;
            }
             
            //Found an open frame in the mount.
            IdxSelectedInMount = (IdxSelectedInMount + 1) % DocsInMount.Length;
        }
        while (IdxSelectedInMount != hotStart);
        IdxSelectedInMount = -1;
    }

    /**
    * Kills ImageApplicationThread.  Disposes of both currentImages and ImageRenderingNow.  Does not actually trigger a refresh of the Picturebox, though.
    */
    private void eraseCurrentImages() throws Exception {
        killThreadImageUpdate();
        //Stop any current access to the current image and render image so we can dispose them.
        pictureBoxMain.Image = null;
        ImageSettingFlagsInvalidated = ImageSettingFlags.NONE;
        if (ImagesCur != null)
        {
            for (int i = 0;i < ImagesCur.Length;i++)
            {
                if (ImagesCur[i] != null)
                {
                    ImagesCur[i].Dispose();
                    ImagesCur[i] = null;
                }
                 
            }
        }
         
        if (ImageRenderingNow != null)
        {
            ImageRenderingNow.Dispose();
            ImageRenderingNow = null;
        }
         
        System.GC.Collect();
    }

    /**
    * Handles a change in selection of the xRay capture button.
    */
    private void toolBarClose_Click() throws Exception {
        onCloseClick();
    }

    /**
    * 
    */
    protected void onCloseClick() throws Exception {
        EventArgs args = new EventArgs();
        selectTreeNode(null);
        this.Dispose();
        if (CloseClick != null)
        {
            CloseClick(this, args);
        }
         
    }

    //Static Functions------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
    * Sets global variables: Zoom, translation, and crop to initial starting values where the image fits perfectly within the box.
    */
    private static void reloadZoomTransCrop(int docImageWidth, int docImageHeight, OpenDentBusiness.Document doc, Rectangle viewport, RefSupport<float> zoom, RefSupport<int> zoomLevel, RefSupport<float> zoomFactor, RefSupport<PointF> translation) throws Exception {
        //Choose an initial zoom so that the image is scaled to fit the destination box size.
        //Keep in mind that bitmaps are not allowed to have either a width or height of 0,
        //so the following equations will always work. The following subtracts from the
        //destination box width and height to force a little extra white space.
        RectangleF imageRect = calcImageDims(docImageWidth,docImageHeight,doc);
        float matchWidth = (int)(viewport.Width * 0.975f);
        matchWidth = (matchWidth <= 0 ? 1 : matchWidth);
        float matchHeight = (int)(viewport.Height * 0.975f);
        matchHeight = (matchHeight <= 0 ? 1 : matchHeight);
        zoom.setValue((float)Math.Min(matchWidth / imageRect.Width, matchHeight / imageRect.Height));
        zoomLevel.setValue(0);
        zoomFactor.setValue(1);
        translation.setValue(new PointF(viewport.Left + viewport.Width / 2.0f, viewport.Top + viewport.Height / 2.0f));
    }

    /**
    * The screen matrix of the image is relative to the upper left of the image, but our calculations are from the center of the image (since the calculations are easier everywhere else if taken from the center). This function converts our calculation matrix into an equivalent screen matrix for display. Assumes document rotations are in 90 degree multiples.
    */
    public static Matrix getScreenMatrix(OpenDentBusiness.Document doc, int docOriginalImageWidth, int docOriginalImageHeight, float imageScale, PointF imageTranslation) throws Exception {
        Matrix matrixDoc = getDocumentFlippedRotatedMatrix(doc);
        matrixDoc.Scale(imageScale, imageScale);
        Rectangle rectCrop = docCropRect(doc,docOriginalImageWidth,docOriginalImageHeight);
        //The screen matrix of a GDI image is always relative to the upper left hand corner of the image.
        PointF pointPreOrigin = new PointF(-rectCrop.Width / 2.0f, -rectCrop.Height / 2.0f);
        PointF[] pointsMatrixScreen = new PointF[]{ pointPreOrigin, new PointF(pointPreOrigin.X + 1, pointPreOrigin.Y), new PointF(pointPreOrigin.X, pointPreOrigin.Y + 1) };
        matrixDoc.TransformPoints(pointsMatrixScreen);
        //X.X
        //X.Y
        //Y.X
        //Y.Y
        //Dx
        Matrix matrixScreen = new Matrix(pointsMatrixScreen[1].X - pointsMatrixScreen[0].X, pointsMatrixScreen[1].Y - pointsMatrixScreen[0].Y, pointsMatrixScreen[2].X - pointsMatrixScreen[0].X, pointsMatrixScreen[2].Y - pointsMatrixScreen[0].Y, pointsMatrixScreen[0].X + imageTranslation.X, pointsMatrixScreen[0].Y + imageTranslation.Y);
        return matrixScreen;
    }

    //Dy
    /**
    * Calculates the image dimensions after factoring flip and rotation of the given document.
    */
    private static RectangleF calcImageDims(int imageWidth, int imageHeight, OpenDentBusiness.Document doc) throws Exception {
        Matrix orientation = GetScreenMatrix(doc, imageWidth, imageHeight, 1, new PointF(0, 0));
        PointF[] corners = new PointF[]{ new PointF(-imageWidth / 2, -imageHeight / 2), new PointF(imageWidth / 2, -imageHeight / 2), new PointF(-imageWidth / 2, imageHeight / 2), new PointF(imageWidth / 2, imageHeight / 2) };
        orientation.TransformPoints(corners);
        float minx = corners[0].X;
        float maxx = minx;
        float miny = corners[0].Y;
        float maxy = miny;
        for (int i = 1;i < corners.Length;i++)
        {
            if (corners[i].X < minx)
            {
                minx = corners[i].X;
            }
            else if (corners[i].X > maxx)
            {
                maxx = corners[i].X;
            }
              
            if (corners[i].Y < miny)
            {
                miny = corners[i].Y;
            }
            else if (corners[i].Y > maxy)
            {
                maxy = corners[i].Y;
            }
              
        }
        return new RectangleF(0, 0, maxx - minx, maxy - miny);
    }

    /**
    * Converts a screen-space location into a location which is relative to the given document in its unrotated/unflipped/unscaled/untranslated state.
    */
    private static PointF screenPointToUnalteredDocumentPoint(PointF screenLocation, OpenDentBusiness.Document doc, int docOriginalImageWidth, int docOriginalImageHeight, float imageScale, PointF imageTranslation) throws Exception {
        Matrix docMat = getDocumentFlippedRotatedMatrix(doc);
        docMat.Scale(imageScale, imageScale);
        //Now we have a matrix representing the image in its current state-space.
        float[] docMatAxes = docMat.Elements;
        float px = screenLocation.X - imageTranslation.X;
        float py = screenLocation.Y - imageTranslation.Y;
        //The origin of our internal image axis is always relative to the center of the crop rectangle.
        Rectangle docCropRect = docCropRect(doc,docOriginalImageWidth,docOriginalImageHeight);
        PointF cropRectCenter = new PointF(docCropRect.X + docCropRect.Width / 2.0f, docCropRect.Y + docCropRect.Height / 2.0f);
        return new PointF((cropRectCenter.X + (px * docMatAxes[0] + py * docMatAxes[1]) / (imageScale * imageScale)), (cropRectCenter.Y + (px * docMatAxes[2] + py * docMatAxes[3]) / (imageScale * imageScale)));
    }

    /**
    * Returns a matrix for the given document which represents flipping over the Y-axis before rotating. Of course, if doc.IsFlipped is false, then no flipping is performed, and if doc.DegreesRotated is a multiple of 360 then no rotation is performed.  doc may be null if eob.
    */
    private static Matrix getDocumentFlippedRotatedMatrix(OpenDentBusiness.Document doc) throws Exception {
        if (doc == null)
        {
            return new Matrix(1, 0, 0, 1, 0, 0);
        }
         
        Matrix result = new Matrix(doc.IsFlipped ? -1 : 1, 0, 0, 1, 0, 0);
        //X-axis
        //Y-axis
        //Offset/Translation(dx,dy)
        result.Rotate(doc.IsFlipped ? -doc.DegreesRotated : doc.DegreesRotated);
        return result;
    }

    /**
    * doc may be null if eob.
    */
    public static Rectangle docCropRect(OpenDentBusiness.Document doc, int originalImageWidth, int originalImageHeight) throws Exception {
        if (doc == null)
        {
            return new Rectangle(0, 0, originalImageWidth, originalImageHeight);
        }
         
        //no cropping
        if (doc.CropW == 0 && doc.CropH == 0)
        {
            return new Rectangle(0, 0, originalImageWidth, originalImageHeight);
        }
         
        return new Rectangle(doc.CropX, doc.CropY, doc.CropW, doc.CropH);
    }

    //Crop rectangles of 0 area are considered non-existant (i.e. no cropping).
    /**
    * Takes in a mount object and finds all the images pertaining to the mount, then concatonates them together into one large, unscaled image and returns that image. For use in other modules.
    */
    public Bitmap createMountImage(Mount mount) throws Exception {
        List<MountItem> mountItems = MountItems.getItemsForMount(mount.MountNum);
        OpenDentBusiness.Document[] documents = Documents.GetDocumentsForMountItems(mountItems);
        Bitmap[] originalImages = ImageStore.OpenImages(documents, PatFolder);
        Bitmap mountImage = new Bitmap(mount.Width, mount.Height);
        ImageHelper.RenderMountImage(mountImage, originalImages, mountItems, documents, -1);
        return mountImage;
    }

    private void mountMenu_Opening(Object sender, CancelEventArgs e) throws Exception {
        if (treeDocuments.SelectedNode == null)
        {
            e.Cancel = true;
            return ;
        }
         
        ImageNodeId nodeId = (ImageNodeId)treeDocuments.SelectedNode.Tag;
        if (nodeId.NodeType != ImageNodeType.Mount)
        {
            e.Cancel = true;
            return ;
        }
         
        //No mount is currently selected so cancel the menu.
        IdxSelectedInMount = getIdxAtMountLocation(PointMouseDown);
        if (IdxSelectedInMount < 0)
        {
            e.Cancel = true;
            return ;
        }
         
        //No mount item was clicked on, so cancel the menu.
        IDataObject clipboard = Clipboard.GetDataObject();
        MountMenu.Items.Clear();
        //Only show the copy option in the mount menu if the item in the mount selected contains an image.
        if (DocsInMount[IdxSelectedInMount] != null)
        {
            MountMenu.Items.Add("Copy", null, new System.EventHandler(MountMenuCopy_Click));
        }
         
        //Only show the paste option in the menu if an item is currently on the clipboard.
        if (clipboard.GetDataPresent(DataFormats.Bitmap))
        {
            MountMenu.Items.Add("Paste", null, new System.EventHandler(MountMenuPaste_Click));
        }
         
        //Only show the swap item in the menu if the item on the clipboard exists in the current mount.
        if (IdxDocToCopy >= 0 && DocsInMount[IdxSelectedInMount] != null && IdxSelectedInMount != IdxDocToCopy)
        {
            MountMenu.Items.Add("Swap", null, new System.EventHandler(MountMenuSwap_Click));
        }
         
        //Cancel the menu if no items have been added into it.
        if (MountMenu.Items.Count < 1)
        {
            e.Cancel = true;
            return ;
        }
         
        //Refresh the mount image, since the IdxSelectedInMount may have changed.
        invalidateSettings(ImageSettingFlags.ALL,false);
    }

    private void mountMenuCopy_Click(Object sender, EventArgs e) throws Exception {
        toolBarCopy_Click();
        IdxDocToCopy = IdxSelectedInMount;
    }

    private void mountMenuPaste_Click(Object sender, EventArgs e) throws Exception {
        toolBarPaste_Click();
    }

    private void mountMenuSwap_Click(Object sender, EventArgs e) throws Exception {
        long mountItemNum = DocsInMount[IdxSelectedInMount].MountItemNum;
        DocsInMount[IdxSelectedInMount].MountItemNum = DocsInMount[IdxDocToCopy].MountItemNum;
        DocsInMount[IdxDocToCopy].MountItemNum = mountItemNum;
        OpenDentBusiness.Document doc = DocsInMount[IdxSelectedInMount];
        DocsInMount[IdxSelectedInMount] = DocsInMount[IdxDocToCopy];
        DocsInMount[IdxDocToCopy] = doc;
        MountItem mount = MountItemsForSelected[IdxSelectedInMount];
        MountItemsForSelected[IdxSelectedInMount] = MountItemsForSelected[IdxDocToCopy];
        MountItemsForSelected[IdxDocToCopy] = mount;
        Documents.Update(DocsInMount[IdxSelectedInMount]);
        Documents.Update(DocsInMount[IdxDocToCopy]);
        boolean[] idxDocsToUpdate = new boolean[DocsInMount.Length];
        idxDocsToUpdate[IdxSelectedInMount] = true;
        idxDocsToUpdate[IdxDocToCopy] = true;
        //Make it so that another swap cannot be done without first copying.
        IdxDocToCopy = -1;
        //Update the mount image to reflect the swapped images.
        InvalidateSettings(ImageSettingFlags.ALL, false, idxDocsToUpdate);
    }

}


