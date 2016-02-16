//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrProvKeyEditCust;
import OpenDental.FormEhrQuarterlyKeyEditCust;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EhrProvKey;
import OpenDentBusiness.EhrProvKeys;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.EhrQuarterlyKeys;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrProvKeysCustomer  extends Form 
{

    private List<EhrProvKey> listKeys = new List<EhrProvKey>();
    private List<EhrQuarterlyKey> listKeysQuart = new List<EhrQuarterlyKey>();
    public long Guarantor = new long();
    public FormEhrProvKeysCustomer() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrProvKeysCustomer_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
        fillGridQ();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"LName"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"FName"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Reports"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Key"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Charge"), 60, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"FTE"), 35, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Notes"),100);
        gridMain.getColumns().add(col);
        listKeys = EhrProvKeys.refreshForFam(Guarantor);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        double feeTotal = 0;
        double fee = 0;
        for (int i = 0;i < listKeys.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listKeys[i].LName);
            row.getCells().Add(listKeys[i].FName);
            row.getCells().add(listKeys[i].HasReportAccess ? "X" : "");
            row.getCells().Add(listKeys[i].ProvKey);
            fee = (double)(60f * listKeys[i].FullTimeEquiv);
            feeTotal += fee;
            row.getCells().Add(fee.ToString("c"));
            row.getCells().Add(listKeys[i].FullTimeEquiv.ToString());
            row.getCells().Add(listKeys[i].Notes);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        textCharge.Text = feeTotal.ToString("c");
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrProvKeyEditCust formK = new FormEhrProvKeyEditCust();
        formK.KeyCur = listKeys[e.getRow()];
        formK.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.EhrKeyAdd))
        {
            return ;
        }
         
        FormEhrProvKeyEditCust formK = new FormEhrProvKeyEditCust();
        formK.KeyCur = new EhrProvKey();
        formK.KeyCur.PatNum = Guarantor;
        formK.KeyCur.FullTimeEquiv = 1;
        formK.KeyCur.setIsNew(true);
        formK.ShowDialog();
        SecurityLogs.makeLogEntry(Permissions.EhrKeyAdd,Guarantor,"Added provider key.");
        fillGrid();
    }

    private void fillGridQ() throws Exception {
        gridQ.beginUpdate();
        gridQ.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Practice Title"),120);
        gridQ.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Year"),40);
        gridQ.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Quarter"),50);
        gridQ.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Key"),100);
        gridQ.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Notes"),100);
        gridQ.getColumns().add(col);
        listKeysQuart = EhrQuarterlyKeys.refresh(Guarantor);
        gridQ.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listKeysQuart.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listKeysQuart[i].PracticeName);
            row.getCells().Add(listKeysQuart[i].YearValue.ToString());
            row.getCells().Add(listKeysQuart[i].QuarterValue.ToString());
            row.getCells().Add(listKeysQuart[i].KeyValue);
            row.getCells().Add(listKeysQuart[i].Notes);
            gridQ.getRows().add(row);
        }
        gridQ.endUpdate();
    }

    private void gridQ_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrQuarterlyKeyEditCust formK = new FormEhrQuarterlyKeyEditCust();
        formK.KeyCur = listKeysQuart[e.getRow()];
        formK.ShowDialog();
        fillGridQ();
    }

    private void butAddQuarterly_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.EhrKeyAdd))
        {
            return ;
        }
         
        FormEhrQuarterlyKeyEditCust formK = new FormEhrQuarterlyKeyEditCust();
        formK.KeyCur = new EhrQuarterlyKey();
        formK.KeyCur.PatNum = Guarantor;
        if (listKeysQuart.Count == 0)
        {
            formK.KeyCur.YearValue = DateTime.Today.Year - 2000;
            int quarter = 1;
            if (DateTime.Today.Month >= 4 && DateTime.Today.Month <= 6)
            {
                quarter = 2;
            }
             
            if (DateTime.Today.Month >= 7 && DateTime.Today.Month <= 9)
            {
                quarter = 3;
            }
             
            if (DateTime.Today.Month >= 10)
            {
                quarter = 4;
            }
             
            formK.KeyCur.QuarterValue = quarter;
        }
        else
        {
            formK.KeyCur.PracticeName = listKeysQuart[listKeysQuart.Count - 1].PracticeName;
            formK.KeyCur.YearValue = listKeysQuart[listKeysQuart.Count - 1].YearValue;
            formK.KeyCur.QuarterValue = listKeysQuart[listKeysQuart.Count - 1].QuarterValue + 1;
            if (formK.KeyCur.QuarterValue == 5)
            {
                formK.KeyCur.QuarterValue = 1;
                formK.KeyCur.YearValue++;
            }
             
            int monthOfQuarter = 1;
            if (formK.KeyCur.QuarterValue == 2)
            {
                monthOfQuarter = 4;
            }
             
            if (formK.KeyCur.QuarterValue == 3)
            {
                monthOfQuarter = 7;
            }
             
            if (formK.KeyCur.QuarterValue == 4)
            {
                monthOfQuarter = 10;
            }
             
            DateTime firstDayOfQuarter = new DateTime(2000 + formK.KeyCur.YearValue, monthOfQuarter, 1);
            DateTime earliestReleaseDate = firstDayOfQuarter.AddMonths(-1);
            if (DateTime.Today < earliestReleaseDate)
            {
                MessageBox.Show("Warning!  Quarterly keys cannot be released more than one month in advance.");
            }
             
        } 
        formK.KeyCur.setIsNew(true);
        formK.ShowDialog();
        SecurityLogs.makeLogEntry(Permissions.EhrKeyAdd,Guarantor,"Added quarterly key.");
        fillGridQ();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        long defNum = DefC.getList(DefCat.ImageCats)[0].DefNum;
        Bitmap bitmap = new Bitmap(this.Width, this.Height);
        this.DrawToBitmap(bitmap, new Rectangle(0, 0, this.Width, this.Height));
        Patient guar = Patients.getPat(Guarantor);
        try
        {
            ImageStore.import(bitmap,defNum,ImageType.Photo,guar);
        }
        catch (Exception ex)
        {
            MessageBox.Show("Unable to save file: " + ex.Message);
            return ;
        }

        MsgBox.show(this,"Saved.");
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.textCharge = new System.Windows.Forms.TextBox();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butSave = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.gridQ = new OpenDental.UI.ODGrid();
        this.butAddQuarterly = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label1.Location = new System.Drawing.Point(69, 596);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(240, 37);
        this.label1.TabIndex = 195;
        this.label1.Text = "Monthly repeating charge (in addition to the normal repeating charge for office) " + "should be";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCharge
        //
        this.textCharge.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textCharge.Location = new System.Drawing.Point(311, 603);
        this.textCharge.Name = "textCharge";
        this.textCharge.ReadOnly = true;
        this.textCharge.Size = new System.Drawing.Size(62, 20);
        this.textCharge.TabIndex = 196;
        this.textCharge.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(9, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 194;
        this.butAdd.Text = "Add";
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
        this.butClose.Location = new System.Drawing.Point(895, 602);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSave.Location = new System.Drawing.Point(405, 600);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(94, 24);
        this.butSave.TabIndex = 197;
        this.butSave.Text = "Save To Images";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(505, 590);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(125, 49);
        this.label2.TabIndex = 198;
        this.label2.Text = "To help archive any changes to this list";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // gridMain
        //
        this.gridMain.setAllowSortingByColumn(true);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(9, 48);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(553, 539);
        this.gridMain.TabIndex = 193;
        this.gridMain.setTitle("Provider Keys");
        this.gridMain.setTranslationName("");
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
        // gridQ
        //
        this.gridQ.setAllowSortingByColumn(true);
        this.gridQ.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridQ.setHScrollVisible(false);
        this.gridQ.Location = new System.Drawing.Point(568, 48);
        this.gridQ.Name = "gridQ";
        this.gridQ.setScrollValue(0);
        this.gridQ.Size = new System.Drawing.Size(407, 539);
        this.gridQ.TabIndex = 199;
        this.gridQ.setTitle("Quarterly Keys");
        this.gridQ.setTranslationName("");
        this.gridQ.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridQ.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridQ_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAddQuarterly
        //
        this.butAddQuarterly.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddQuarterly.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddQuarterly.setAutosize(true);
        this.butAddQuarterly.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddQuarterly.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddQuarterly.setCornerRadius(4F);
        this.butAddQuarterly.Image = Resources.getAdd();
        this.butAddQuarterly.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddQuarterly.Location = new System.Drawing.Point(568, 12);
        this.butAddQuarterly.Name = "butAddQuarterly";
        this.butAddQuarterly.Size = new System.Drawing.Size(75, 24);
        this.butAddQuarterly.TabIndex = 200;
        this.butAddQuarterly.Text = "Add";
        this.butAddQuarterly.Click += new System.EventHandler(this.butAddQuarterly_Click);
        //
        // FormEhrProvKeysCustomer
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 638);
        this.Controls.Add(this.butAddQuarterly);
        this.Controls.Add(this.gridQ);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.textCharge);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormEhrProvKeysCustomer";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Provider Keys for This Family";
        this.Load += new System.EventHandler(this.FormEhrProvKeysCustomer_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCharge = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butSave;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridQ;
    private OpenDental.UI.Button butAddQuarterly;
}


