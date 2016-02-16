//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:12 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormImageSelectClaimAttach;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClaimAttach;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageSettingFlags;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;

/**
* 
*/
public class FormImageSelectClaimAttach  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public long PatNum = new long();
    private Document[] Docs = new Document[]();
    /**
    * If DialogResult==OK, then this will contain the new ClaimAttach with the filename that the file was saved under.  File will be in the EmailAttachments folder.  But ClaimNum will not be set.
    */
    public ClaimAttach ClaimAttachNew;
    /**
    * 
    */
    public FormImageSelectClaimAttach() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormImageSelectClaimAttach.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(505, 472);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(505, 513);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(451, 527);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Images");
        this.gridMain.setTranslationName("FormImageSelect");
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
        //
        // FormImageSelectClaimAttach
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(632, 564);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormImageSelectClaimAttach";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Image for Claim Attachment";
        this.Load += new System.EventHandler(this.FormImageSelect_Load);
        this.ResumeLayout(false);
    }

    private void formImageSelect_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Category"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Docs = Documents.getAllWithPat(PatNum);
        for (int i = 0;i < Docs.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Docs[i].DateCreated.ToShortDateString());
            row.getCells().Add(DefC.GetName(DefCat.ImageCats, Docs[i].DocCategory));
            row.getCells().Add(Docs[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        saveAttachment();
    }

    private void saveAttachment() throws Exception {
        Patient PatCur = Patients.getPat(PatNum);
        //if(PatCur.ImageFolder=="") {
        //	MsgBox.Show(this,"Invalid patient image folder.");
        //	return;
        //}
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Error. Not using AtoZ images folder.");
            return ;
        }
         
        String patfolder = ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath());
        //ODFileUtils.CombinePaths(
        //FormPath.GetPreferredImagePath(),PatCur.ImageFolder.Substring(0,1).ToUpper(),PatCur.ImageFolder);
        //if(!Directory.Exists(patfolder)) {
        //	MsgBox.Show(this,"Patient folder not found in AtoZ folder.");
        //	return;
        //}
        Document doc = Docs[gridMain.getSelectedIndex()];
        if (!ImageHelper.hasImageExtension(doc.FileName))
        {
            MsgBox.show(this,"Invalid file.  Only images may be attached, no other file format.");
            return ;
        }
         
        String oldPath = CodeBase.ODFileUtils.combinePaths(patfolder,doc.FileName);
        Random rnd = new Random();
        String newName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + Path.GetExtension(oldPath);
        String attachPath = EmailMessages.getEmailAttachPath();
        String newPath = CodeBase.ODFileUtils.combinePaths(attachPath,newName);
        try
        {
            if (ImageHelper.hasImageExtension(oldPath))
            {
                if (doc.CropH != 0 || doc.CropW != 0 || doc.CropX != 0 || doc.CropY != 0 || doc.DegreesRotated != 0 || doc.IsFlipped || doc.WindowingMax != 0 || doc.WindowingMin != 0)
                {
                    //this does result in a significantly larger images size if jpg.  A later optimization would recompress it.
                    Bitmap bitmapold = (Bitmap)Bitmap.FromFile(oldPath);
                    Bitmap bitmapnew = ImageHelper.applyDocumentSettingsToImage(doc,bitmapold,ImageSettingFlags.ALL);
                    bitmapnew.Save(newPath);
                }
                else
                {
                    File.Copy(oldPath, newPath);
                } 
            }
            else
            {
                File.Copy(oldPath, newPath);
            } 
            ClaimAttachNew = new ClaimAttach();
            ClaimAttachNew.DisplayedFileName = Docs[gridMain.getSelectedIndex()].FileName;
            ClaimAttachNew.ActualFileName = newName;
            DialogResult = DialogResult.OK;
        }
        catch (FileNotFoundException ex)
        {
            MessageBox.Show(Lan.g(this,"File not found: ") + ex.Message);
            return ;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an image first.");
            return ;
        }
         
        saveAttachment();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


