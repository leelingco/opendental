//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormImageSelect;
import OpenDental.FormMedications;
import OpenDental.FormMedPat;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageSettingFlags;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormMedicationReconcile  extends Form 
{

    public Patient PatCur;
    private Bitmap BitmapOriginal = new Bitmap();
    private List<EhrMeasureEvent> ehrMeasureEventsList = new List<EhrMeasureEvent>();
    private List<MedicationPat> medList = new List<MedicationPat>();
    public FormMedicationReconcile() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void basicTemplate_Load(Object sender, EventArgs e) throws Exception {
        fillMeds();
        fillReconcilesGrid();
    }

    private void fillMeds() throws Exception {
        Medications.refresh();
        medList = MedicationPats.Refresh(PatCur.PatNum, checkDiscontinued.Checked);
        gridMeds.beginUpdate();
        gridMeds.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableMedications","Medication"),140);
        gridMeds.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableMedications","Notes for Patient"),225);
        gridMeds.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableMedications","Disc"), 10, HorizontalAlignment.Center);
        //discontinued
        gridMeds.getColumns().add(col);
        gridMeds.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < medList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (medList[i].MedicationNum == 0)
            {
                row.getCells().Add(medList[i].MedDescript);
            }
            else
            {
                Medication generic = Medications.GetGeneric(medList[i].MedicationNum);
                String medName = Medications.GetMedication(medList[i].MedicationNum).MedName;
                if (generic.MedicationNum != medList[i].MedicationNum)
                {
                    //not generic
                    medName += " (" + generic.MedName + ")";
                }
                 
                row.getCells().add(medName);
            } 
            row.getCells().Add(medList[i].PatNote);
            if (MedicationPats.IsMedActive(medList[i]))
            {
                row.getCells().add("");
            }
            else
            {
                //discontinued
                row.getCells().add("X");
            } 
            gridMeds.getRows().add(row);
        }
        gridMeds.endUpdate();
    }

    private void resizePictBox() throws Exception {
        if (pictBox.BackgroundImage != null)
        {
            pictBox.BackgroundImage.Dispose();
        }
         
        int width = new int();
        int height = new int();
        float ratio = new float();
        //Resize the image at the width of the pictBox, then only resize to the height if it doesn't fit.
        width = pictBox.Width - 4;
        ratio = (float)width / BitmapOriginal.Width;
        height = (int)(BitmapOriginal.Height * ratio);
        if (height > pictBox.Height)
        {
            height = pictBox.Height - 4;
            ratio = (float)height / BitmapOriginal.Height;
            width = (int)(BitmapOriginal.Width * ratio);
        }
         
        Bitmap newBitmap = new Bitmap(width, height);
        Graphics g = Graphics.FromImage(newBitmap);
        g.DrawImage(BitmapOriginal, 0, 0, width, height);
        g.Dispose();
        if (pictBox.BackgroundImage != null)
        {
            pictBox.BackgroundImage.Dispose();
        }
         
        pictBox.BackgroundImage = newBitmap;
    }

    private void formMedicationReconcile_ResizeEnd(Object sender, EventArgs e) throws Exception {
        resizePictBox();
    }

    private void checkDiscontinued_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        fillMeds();
    }

    private void checkDiscontinued_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        fillMeds();
    }

    private void fillReconcilesGrid() throws Exception {
        gridReconcileEvents.beginUpdate();
        gridReconcileEvents.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime",130);
        gridReconcileEvents.getColumns().add(col);
        col = new ODGridColumn("Details",600);
        gridReconcileEvents.getColumns().add(col);
        ehrMeasureEventsList = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.MedicationReconcile);
        gridReconcileEvents.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ehrMeasureEventsList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ehrMeasureEventsList[i].DateTEvent.ToString());
            row.getCells().Add(ehrMeasureEventsList[i].EventType.ToString());
            gridReconcileEvents.getRows().add(row);
        }
        gridReconcileEvents.endUpdate();
    }

    private void gridMeds_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormMedPat FormMP = new FormMedPat();
        FormMP.MedicationPatCur = medList[e.getRow()];
        FormMP.ShowDialog();
        fillMeds();
    }

    private void formMedicationReconcile_Resize(Object sender, EventArgs e) throws Exception {
        resizePictBox();
    }

    private void butPickRxListImage_Click(Object sender, EventArgs e) throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"This option is not supported with images stored in the database.");
            return ;
        }
         
        FormImageSelect formIS = new FormImageSelect();
        formIS.PatNum = PatCur.PatNum;
        formIS.ShowDialog();
        if (formIS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        String patFolder = ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath());
        Document doc = Documents.getByNum(formIS.SelectedDocNum);
        if (!ImageStore.hasImageExtension(doc.FileName))
        {
            MsgBox.show(this,"The selected file is not a supported image type.");
            return ;
        }
         
        textDocDateDesc.Text = doc.DateTStamp.ToShortDateString() + " - " + doc.Description.ToString();
        if (BitmapOriginal != null)
        {
            BitmapOriginal.Dispose();
        }
         
        BitmapOriginal = ImageStore.openImage(doc,patFolder);
        Bitmap bitmap = ImageHelper.applyDocumentSettingsToImage(doc,BitmapOriginal,ImageSettingFlags.ALL);
        pictBox.BackgroundImage = bitmap;
        resizePictBox();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        //select medication from list.  Additional meds can be added to the list from within that dlg
        FormMedications FormM = new FormMedications();
        FormM.IsSelectionMode = true;
        FormM.ShowDialog();
        if (FormM.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        MedicationPat MedicationPatCur = new MedicationPat();
        MedicationPatCur.PatNum = PatCur.PatNum;
        MedicationPatCur.MedicationNum = FormM.SelectedMedicationNum;
        MedicationPatCur.ProvNum = PatCur.PriProv;
        FormMedPat FormMP = new FormMedPat();
        FormMP.MedicationPatCur = MedicationPatCur;
        FormMP.IsNew = true;
        FormMP.ShowDialog();
        if (FormMP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillMeds();
    }

    private void butAddEvent_Click(Object sender, EventArgs e) throws Exception {
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.MedicationReconcile;
        newMeasureEvent.PatNum = PatCur.PatNum;
        newMeasureEvent.MoreInfo = "";
        EhrMeasureEvents.insert(newMeasureEvent);
        fillReconcilesGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridReconcileEvents.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridReconcileEvents.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(ehrMeasureEventsList[gridReconcileEvents.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
        fillReconcilesGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
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
        this.textDocDateDesc = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.checkDiscontinued = new System.Windows.Forms.CheckBox();
        this.pictBox = new System.Windows.Forms.PictureBox();
        this.gridMeds = new OpenDental.UI.ODGrid();
        this.butPickRxListImage = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.splitContainer1 = new System.Windows.Forms.SplitContainer();
        this.butAddEvent = new OpenDental.UI.Button();
        this.gridReconcileEvents = new OpenDental.UI.ODGrid();
        this.label2 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.pictBox)).BeginInit();
        this.splitContainer1.Panel1.SuspendLayout();
        this.splitContainer1.Panel2.SuspendLayout();
        this.splitContainer1.SuspendLayout();
        this.SuspendLayout();
        //
        // textDocDateDesc
        //
        this.textDocDateDesc.Enabled = false;
        this.textDocDateDesc.Location = new System.Drawing.Point(101, 5);
        this.textDocDateDesc.Name = "textDocDateDesc";
        this.textDocDateDesc.Size = new System.Drawing.Size(272, 20);
        this.textDocDateDesc.TabIndex = 71;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(1, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(94, 16);
        this.label1.TabIndex = 73;
        this.label1.Text = "Rx List";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkDiscontinued
        //
        this.checkDiscontinued.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.checkDiscontinued.Location = new System.Drawing.Point(609, 5);
        this.checkDiscontinued.Name = "checkDiscontinued";
        this.checkDiscontinued.Size = new System.Drawing.Size(212, 23);
        this.checkDiscontinued.TabIndex = 70;
        this.checkDiscontinued.Tag = "";
        this.checkDiscontinued.Text = "Show Discontinued Medications";
        this.checkDiscontinued.UseVisualStyleBackColor = true;
        this.checkDiscontinued.KeyUp += new System.Windows.Forms.KeyEventHandler(this.checkDiscontinued_KeyUp);
        this.checkDiscontinued.MouseUp += new System.Windows.Forms.MouseEventHandler(this.checkDiscontinued_MouseUp);
        //
        // pictBox
        //
        this.pictBox.BackColor = System.Drawing.SystemColors.Window;
        this.pictBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
        this.pictBox.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
        this.pictBox.Cursor = System.Windows.Forms.Cursors.Hand;
        this.pictBox.Dock = System.Windows.Forms.DockStyle.Fill;
        this.pictBox.InitialImage = null;
        this.pictBox.Location = new System.Drawing.Point(0, 0);
        this.pictBox.Name = "pictBox";
        this.pictBox.Size = new System.Drawing.Size(460, 600);
        this.pictBox.TabIndex = 66;
        this.pictBox.TabStop = false;
        //
        // gridMeds
        //
        this.gridMeds.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMeds.setHScrollVisible(false);
        this.gridMeds.Location = new System.Drawing.Point(0, 0);
        this.gridMeds.Name = "gridMeds";
        this.gridMeds.setScrollValue(0);
        this.gridMeds.Size = new System.Drawing.Size(445, 395);
        this.gridMeds.TabIndex = 65;
        this.gridMeds.setTitle("Medications");
        this.gridMeds.setTranslationName("TableMedications");
        this.gridMeds.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMeds.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMeds_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butPickRxListImage
        //
        this.butPickRxListImage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickRxListImage.setAutosize(true);
        this.butPickRxListImage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickRxListImage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickRxListImage.setCornerRadius(4F);
        this.butPickRxListImage.Location = new System.Drawing.Point(379, 3);
        this.butPickRxListImage.Name = "butPickRxListImage";
        this.butPickRxListImage.Size = new System.Drawing.Size(22, 24);
        this.butPickRxListImage.TabIndex = 76;
        this.butPickRxListImage.Text = "...";
        this.butPickRxListImage.Click += new System.EventHandler(this.butPickRxListImage_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAdd.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(468, 4);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(123, 23);
        this.butAdd.TabIndex = 75;
        this.butAdd.Text = "&Add Medication";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(838, 640);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // splitContainer1
        //
        this.splitContainer1.Location = new System.Drawing.Point(4, 34);
        this.splitContainer1.Name = "splitContainer1";
        //
        // splitContainer1.Panel1
        //
        this.splitContainer1.Panel1.Controls.Add(this.pictBox);
        //
        // splitContainer1.Panel2
        //
        this.splitContainer1.Panel2.Controls.Add(this.butDelete);
        this.splitContainer1.Panel2.Controls.Add(this.butAddEvent);
        this.splitContainer1.Panel2.Controls.Add(this.gridReconcileEvents);
        this.splitContainer1.Panel2.Controls.Add(this.label2);
        this.splitContainer1.Panel2.Controls.Add(this.gridMeds);
        this.splitContainer1.Size = new System.Drawing.Size(909, 600);
        this.splitContainer1.SplitterDistance = 460;
        this.splitContainer1.TabIndex = 77;
        //
        // butAddEvent
        //
        this.butAddEvent.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddEvent.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddEvent.setAutosize(true);
        this.butAddEvent.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddEvent.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddEvent.setCornerRadius(4F);
        this.butAddEvent.Location = new System.Drawing.Point(0, 398);
        this.butAddEvent.Name = "butAddEvent";
        this.butAddEvent.Size = new System.Drawing.Size(63, 24);
        this.butAddEvent.TabIndex = 78;
        this.butAddEvent.Text = "Add";
        this.butAddEvent.Click += new System.EventHandler(this.butAddEvent_Click);
        //
        // gridReconcileEvents
        //
        this.gridReconcileEvents.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridReconcileEvents.setHScrollVisible(false);
        this.gridReconcileEvents.Location = new System.Drawing.Point(0, 425);
        this.gridReconcileEvents.Name = "gridReconcileEvents";
        this.gridReconcileEvents.setScrollValue(0);
        this.gridReconcileEvents.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridReconcileEvents.Size = new System.Drawing.Size(445, 175);
        this.gridReconcileEvents.TabIndex = 67;
        this.gridReconcileEvents.setTitle("Reconciles");
        this.gridReconcileEvents.setTranslationName("gridReconcile");
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(133, 396);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(311, 27);
        this.label2.TabIndex = 66;
        this.label2.Text = "This is a historical record of medication reconciles for this patient.  Delete an" + "y entries that are inaccurate.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(65, 398);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(63, 24);
        this.butDelete.TabIndex = 78;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormMedicationReconcile
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(918, 676);
        this.Controls.Add(this.splitContainer1);
        this.Controls.Add(this.butPickRxListImage);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textDocDateDesc);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkDiscontinued);
        this.Controls.Add(this.butClose);
        this.Name = "FormMedicationReconcile";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Medication Reconcile";
        this.Load += new System.EventHandler(this.BasicTemplate_Load);
        this.Resize += new System.EventHandler(this.FormMedicationReconcile_Resize);
        ((System.ComponentModel.ISupportInitialize)(this.pictBox)).EndInit();
        this.splitContainer1.Panel1.ResumeLayout(false);
        this.splitContainer1.Panel2.ResumeLayout(false);
        this.splitContainer1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textDocDateDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkDiscontinued = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.PictureBox pictBox = new System.Windows.Forms.PictureBox();
    private OpenDental.UI.ODGrid gridMeds;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butPickRxListImage;
    private System.Windows.Forms.SplitContainer splitContainer1 = new System.Windows.Forms.SplitContainer();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridReconcileEvents;
    private OpenDental.UI.Button butAddEvent;
    private OpenDental.UI.Button butDelete;
}


