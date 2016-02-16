//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.WikiPages;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiImages  extends Form 
{

    /**
    * This contains the entire qualified names including path and extension.
    */
    private List<String> ImageNamesList = new List<String>();
    public String SelectedImageName = new String();
    public FormWikiImages() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiImages_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Image Name"),70);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        String[] fileNames = System.IO.Directory.GetFiles(WikiPages.getWikiPath());
        //All files from the wiki file path, including images and other files.
        ImageNamesList = new List<String>();
        for (int i = 0;i < fileNames.Length;i++)
        {
            //If the user has entered a search keyword, then only show file names which contain the keyword.
            if (!StringSupport.equals(textSearch.Text, "") && !Path.GetFileName(fileNames[i]).ToLower().Contains(textSearch.Text.ToLower()))
            {
                continue;
            }
             
            //Only add image files to the ImageNamesList, not other files such at text files.
            if (ImageHelper.HasImageExtension(fileNames[i]))
            {
                ImageNamesList.Add(fileNames[i]);
            }
             
        }
        for (int i = 0;i < ImageNamesList.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Path.GetFileName(ImageNamesList[i]));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        labelImageSize.Text = Lan.g(this,"Image Size") + ":";
        picturePreview.Image = null;
        picturePreview.Invalidate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        paintPreviewPicture();
    }

    private void paintPreviewPicture() throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        String imagePath = ImageNamesList[gridMain.getSelectedIndex()];
        Image tmpImg = new Bitmap(imagePath);
        //Could throw an exception if someone manually deletes the image right after this window loads.
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
        labelImageSize.Text = Lan.g(this,"Image Size") + ": " + (int)tmpImg.PhysicalDimension.Width + " x " + (int)tmpImg.PhysicalDimension.Height;
        picturePreview.Invalidate();
        if (tmpImg != null)
        {
            tmpImg.Dispose();
        }
         
        tmpImg = null;
    }

    private void formWikiImages_ResizeEnd(Object sender, EventArgs e) throws Exception {
        paintPreviewPicture();
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog openFD = new OpenFileDialog();
        openFD.Multiselect = true;
        if (openFD.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        Invalidate();
        for (Object __dummyForeachVar0 : openFD.FileNames)
        {
            String fileName = (String)__dummyForeachVar0;
            //check file types?
            String destinationPath = WikiPages.getWikiPath() + "\\" + Path.GetFileName(fileName);
            if (File.Exists(destinationPath))
            {
                MessageBox.APPLY __dummyScrutVar0 = MessageBox.Show(Lan.g(this,"Overwrite Existing File") + ": " + destinationPath, "", MessageBoxButtons.YesNoCancel);
                if (__dummyScrutVar0.equals(DialogResult.No))
                {
                    //rename, do not overwrite
                    InputBox ip = new InputBox(Lan.g(this,"New file name."));
                    ip.textResult.Text = Path.GetFileName(fileName);
                    ip.ShowDialog();
                    if (ip.DialogResult != DialogResult.OK)
                    {
                        continue;
                    }
                     
                    //cancel, next file.
                    boolean cancel = false;
                    while (File.Exists(WikiPages.getWikiPath() + "\\" + ip.textResult.Text) && !cancel)
                    {
                        MsgBox.show(this,"File name already exists.");
                        if (ip.ShowDialog() != DialogResult.OK)
                        {
                            cancel = true;
                        }
                         
                    }
                    if (cancel)
                    {
                        continue;
                    }
                     
                    //cancel rename, and go to next file.
                    destinationPath = WikiPages.getWikiPath() + "\\" + ip.textResult.Text;
                }
                else //proceed to save file.
                if (__dummyScrutVar0.equals(DialogResult.Yes))
                {
                    try
                    {
                        //overwrite
                        File.Delete(destinationPath);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(Lan.g(this,"Cannot copy file") + ":" + fileName + "\r\n" + ex.Message);
                        continue;
                    }
                
                }
                else
                {
                    continue;
                }  
            }
             
            //file deleted, proceed to save.
            //cancel
            //skip this file.
            File.Copy(fileName, destinationPath);
        }
        fillGrid();
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        SelectedImageName = Path.GetFileName(ImageNamesList[gridMain.getSelectedIndex()]);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        SelectedImageName = Path.GetFileName(ImageNamesList[gridMain.getSelectedIndex()]);
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
        this.label1 = new System.Windows.Forms.Label();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.picturePreview = new System.Windows.Forms.PictureBox();
        this.labelImageSize = new System.Windows.Forms.Label();
        this.butImport = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.picturePreview)).BeginInit();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(47, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(76, 18);
        this.label1.TabIndex = 15;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(125, 12);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(113, 20);
        this.textSearch.TabIndex = 14;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(248, 444);
        this.gridMain.TabIndex = 16;
        this.gridMain.setTitle("Available Images");
        this.gridMain.setTranslationName("TableWikiSearchPages");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
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
        // picturePreview
        //
        this.picturePreview.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.picturePreview.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.picturePreview.Location = new System.Drawing.Point(266, 38);
        this.picturePreview.Name = "picturePreview";
        this.picturePreview.Size = new System.Drawing.Size(444, 444);
        this.picturePreview.TabIndex = 17;
        this.picturePreview.TabStop = false;
        //
        // labelImageSize
        //
        this.labelImageSize.Location = new System.Drawing.Point(263, 13);
        this.labelImageSize.Name = "labelImageSize";
        this.labelImageSize.Size = new System.Drawing.Size(447, 18);
        this.labelImageSize.TabIndex = 21;
        this.labelImageSize.Text = "Image Size:";
        this.labelImageSize.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Location = new System.Drawing.Point(716, 38);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 24);
        this.butImport.TabIndex = 20;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(719, 428);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 19;
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
        this.butCancel.Location = new System.Drawing.Point(719, 458);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 18;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormWikiImages
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(806, 494);
        this.Controls.Add(this.labelImageSize);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.picturePreview);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSearch);
        this.Name = "FormWikiImages";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insert Image";
        this.Load += new System.EventHandler(this.FormWikiImages_Load);
        this.ResizeEnd += new System.EventHandler(this.FormWikiImages_ResizeEnd);
        ((System.ComponentModel.ISupportInitialize)(this.picturePreview)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSearch = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.PictureBox picturePreview = new System.Windows.Forms.PictureBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.Label labelImageSize = new System.Windows.Forms.Label();
}


