//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EhrLabImage;
import OpenDentBusiness.EhrLabImages;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patients;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrLabImageEdit;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabImageEdit  extends Form 
{

    private long _ehrLabNum = new long();
    private long _patNum = new long();
    /**
    * List of EHR Lab Images attached to this EhrLabNum. This list will be retrieved from the database on init and then modified while dialog is up. Final list will be inserted into database if user clicks OK.
    */
    private List<EhrLabImage> _listAttached = new List<EhrLabImage>();
    /**
    * List of Documents (images) attached to this patient. This list is not modified by this form.
    */
    private List<Document> _listPatientDocuments = new List<Document>();
    /**
    * Directory which hold's the Patient's documents.
    */
    private String _patFolder = new String();
    public FormEhrLabImageEdit(long patNum, long ehrLabNum) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patNum = patNum;
        _ehrLabNum = ehrLabNum;
    }

    private void formEhrLabeImageEdit_Load(Object sender, EventArgs e) throws Exception {
        Height = System.Windows.Forms.Screen.GetWorkingArea(this).Height;
        this.SetDesktopLocation(DesktopLocation.X, 0);
        checkWaitingForImages.Checked = EhrLabImages.isWaitingForImages(_ehrLabNum);
        _listPatientDocuments = new List<Document>(Documents.getAllWithPat(_patNum));
        _patFolder = ImageStore.getPatientFolder(Patients.getPat(_patNum),ImageStore.getPreferredAtoZpath());
        //This is where the pat folder gets created if it does not yet exist.
        _listAttached = EhrLabImages.refresh(_ehrLabNum);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        int curSelection = gridMain.getSelectedIndex();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        gridMain.getColumns().add(new ODGridColumn(Lan.g("TableLabImage","Attached"), 60, HorizontalAlignment.Center));
        gridMain.getColumns().add(new ODGridColumn(Lan.g("TableLabImage","Date"), 80, HorizontalAlignment.Center));
        gridMain.getColumns().add(new ODGridColumn(Lan.g("TableLabImage","Category"), 80, HorizontalAlignment.Center));
        gridMain.getColumns().add(new ODGridColumn(Lan.g("TableLabImage","Desc"), 180, HorizontalAlignment.Left));
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < _listPatientDocuments.Count;i++)
        {
            if (_listPatientDocuments[i].DocNum <= 0)
            {
                continue;
            }
             
            //Invalid doc num indicates 'Waiting for images'. This flag is set in the Load event.
            //Test if this is a valid image.
            Bitmap bmp = ImageStore.OpenImage(_listPatientDocuments[i], _patFolder);
            if (bmp == null)
            {
                continue;
            }
             
            bmp.Dispose();
            bmp = null;
            boolean isAttached = EhrLabImages.GetDocNumExistsInList(_ehrLabNum, _listPatientDocuments[i].DocNum, _listAttached);
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(isAttached ? "X" : "");
            row.getCells().Add(_listPatientDocuments[i].DateCreated.ToShortDateString());
            row.getCells().Add(DefC.GetName(DefCat.ImageCats, _listPatientDocuments[i].DocCategory));
            row.getCells().Add(_listPatientDocuments[i].Description);
            row.setTag(_listPatientDocuments[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        if (curSelection >= 0)
        {
            gridMain.setSelected(curSelection,true);
        }
         
    }

    /**
    * Throws exception if current grid selection is invalid
    */
    private Document getSelectedDocument() throws Exception {
        if (gridMain.getSelectedIndex() == -1 || gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag() == null || !(gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag() instanceof Document))
        {
            throw new Exception("Invalid selection");
        }
         
        Document doc = (Document)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag();
        return doc;
    }

    private void paintPreviewPicture() throws Exception {
        try
        {
            Document doc = getSelectedDocument();
            String imagePath = CodeBase.ODFileUtils.combinePaths(_patFolder,doc.FileName);
            if (!File.Exists(imagePath))
            {
                throw new Exception("File not found");
            }
             
            //Could throw an exception if someone manually deletes the image right after this window loads.
            Image tmpImg = new Bitmap(imagePath);
            float imgScale = 1;
            //will be between 0 and 1
            if (tmpImg.PhysicalDimension.Height > picturePreview.Height || tmpImg.PhysicalDimension.Width > picturePreview.Width)
            {
                //image is too large
                //Image is larger than PictureBox, resize to fit.
                if (tmpImg.PhysicalDimension.Width / picturePreview.Width > tmpImg.PhysicalDimension.Height / picturePreview.Height)
                {
                    //resize image based on width
                    imgScale = picturePreview.Width / tmpImg.PhysicalDimension.Width;
                }
                else
                {
                    //resize image based on height
                    imgScale = picturePreview.Height / tmpImg.PhysicalDimension.Height;
                } 
            }
             
            if (picturePreview.Image != null)
            {
                picturePreview.Image.Dispose();
                picturePreview.Image = null;
            }
             
            picturePreview.Image = new Bitmap(tmpImg, (int)(tmpImg.PhysicalDimension.Width * imgScale), (int)(tmpImg.PhysicalDimension.Height * imgScale));
            //labelDescription.Text=Lan.g(this,"Description")+": "+doc.Description;
            picturePreview.Invalidate();
            if (tmpImg != null)
            {
                tmpImg.Dispose();
            }
             
            tmpImg = null;
        }
        catch (Exception e)
        {
            picturePreview.Image = null;
            picturePreview.Invalidate();
        }
    
    }

    private void splitContainer_Resize(Object sender, EventArgs e) throws Exception {
        paintPreviewPicture();
    }

    private void splitContainer_SplitterMoved(Object sender, SplitterEventArgs e) throws Exception {
        paintPreviewPicture();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        try
        {
            paintPreviewPicture();
            if (e.getCol() != 0)
            {
                return ;
            }
             
            Document doc = getSelectedDocument();
            int existingIndex = -1;
            for (int i = 0;i < _listAttached.Count;i++)
            {
                if (_listAttached[i].EhrLabNum == _ehrLabNum && _listAttached[i].DocNum == doc.DocNum)
                {
                    //found it, mark it for delete
                    existingIndex = i;
                    break;
                }
                 
            }
            if (existingIndex >= 0)
            {
                //it exists so delete it
                _listAttached.RemoveAt(existingIndex);
            }
            else
            {
                //it doesn't exist so add it
                EhrLabImage labImage = new EhrLabImage();
                labImage.EhrLabNum = _ehrLabNum;
                labImage.DocNum = doc.DocNum;
                _listAttached.Add(labImage);
            } 
            fillGrid();
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //Uncheck the waiting check box if any images are attached. User only has the option of setting the 'Waiting' flag if there are no images attached.
        List<long> docNums = new List<long>();
        for (int i = 0;i < _listAttached.Count;i++)
        {
            if (!docNums.Contains(_listAttached[i].DocNum))
            {
                docNums.Add(_listAttached[i].DocNum);
            }
             
        }
        if (checkWaitingForImages.Checked && docNums.Count > 0)
        {
            MsgBox.show(this,"'Waiting for Images' is only allowed if there are no images currently attached. Detach all images or uncheck 'Waiting for Images'.");
            return ;
        }
         
        EhrLabImages.InsertAllForLabNum(_ehrLabNum, checkWaitingForImages.Checked, docNums);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrLabImageEdit.class);
        this.picturePreview = new System.Windows.Forms.PictureBox();
        this.checkWaitingForImages = new System.Windows.Forms.CheckBox();
        this.splitContainer = new System.Windows.Forms.SplitContainer();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.picturePreview)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.splitContainer)).BeginInit();
        this.splitContainer.Panel1.SuspendLayout();
        this.splitContainer.Panel2.SuspendLayout();
        this.splitContainer.SuspendLayout();
        this.SuspendLayout();
        //
        // picturePreview
        //
        this.picturePreview.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.picturePreview.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.picturePreview.Location = new System.Drawing.Point(3, 3);
        this.picturePreview.Name = "picturePreview";
        this.picturePreview.Size = new System.Drawing.Size(759, 310);
        this.picturePreview.TabIndex = 19;
        this.picturePreview.TabStop = false;
        //
        // checkWaitingForImages
        //
        this.checkWaitingForImages.Location = new System.Drawing.Point(7, 8);
        this.checkWaitingForImages.Name = "checkWaitingForImages";
        this.checkWaitingForImages.Size = new System.Drawing.Size(762, 20);
        this.checkWaitingForImages.TabIndex = 266;
        this.checkWaitingForImages.Text = "Waiting For Images. This status will only be applied if there are no images attac" + "hed.";
        this.checkWaitingForImages.UseVisualStyleBackColor = true;
        //
        // splitContainer
        //
        this.splitContainer.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.splitContainer.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
        this.splitContainer.Location = new System.Drawing.Point(7, 34);
        this.splitContainer.Name = "splitContainer";
        this.splitContainer.Orientation = System.Windows.Forms.Orientation.Horizontal;
        //
        // splitContainer.Panel1
        //
        this.splitContainer.Panel1.Controls.Add(this.gridMain);
        //
        // splitContainer.Panel2
        //
        this.splitContainer.Panel2.Controls.Add(this.picturePreview);
        this.splitContainer.Size = new System.Drawing.Size(765, 513);
        this.splitContainer.SplitterDistance = 193;
        this.splitContainer.TabIndex = 268;
        this.splitContainer.SplitterMoved += new System.Windows.Forms.SplitterEventHandler(this.splitContainer_SplitterMoved);
        this.splitContainer.Resize += new System.EventHandler(this.splitContainer_Resize);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(3, 3);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(759, 187);
        this.gridMain.TabIndex = 18;
        this.gridMain.setTitle("Available Images");
        this.gridMain.setTranslationName("TableWikiSearchPages");
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(602, 553);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
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
        this.butCancel.Location = new System.Drawing.Point(697, 553);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormEhrLabeImageEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(784, 589);
        this.Controls.Add(this.splitContainer);
        this.Controls.Add(this.checkWaitingForImages);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(100, 100);
        this.Name = "FormEhrLabeImageEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Order Image Edit";
        this.Load += new System.EventHandler(this.FormEhrLabeImageEdit_Load);
        ((System.ComponentModel.ISupportInitialize)(this.picturePreview)).EndInit();
        this.splitContainer.Panel1.ResumeLayout(false);
        this.splitContainer.Panel2.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.splitContainer)).EndInit();
        this.splitContainer.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.PictureBox picturePreview = new System.Windows.Forms.PictureBox();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.CheckBox checkWaitingForImages = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.SplitContainer splitContainer = new System.Windows.Forms.SplitContainer();
}


