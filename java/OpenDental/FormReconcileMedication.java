//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormCDSIntervention;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Security;
import OpenDental.FormReconcileMedication;
import OpenDental.Properties.Resources;

public class FormReconcileMedication  extends Form 
{

    public List<MedicationPat> ListMedicationPatNew = new List<MedicationPat>();
    private List<MedicationPat> _listMedicationPatReconcile = new List<MedicationPat>();
    private List<MedicationPat> _listMedicationPatCur = new List<MedicationPat>();
    private List<Medication> _listMedicationCur = new List<Medication>();
    private Patient _patCur;
    /**
    * Patient must be valid.  Do not pass null.
    */
    public FormReconcileMedication(Patient patCur) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patCur = patCur;
    }

    private void formReconcileMedication_Load(Object sender, EventArgs e) throws Exception {
        for (int index = 0;index < ListMedicationPatNew.Count;index++)
        {
            ListMedicationPatNew[index].PatNum = _patCur.PatNum;
        }
        fillExistingGrid();
        _listMedicationPatReconcile = new List<MedicationPat>(_listMedicationPatCur);
        //ListMedicationPatNew=new List<MedicationPat>();
        //MedicationPat medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //medP.MedDescript="Valpax";
        //medP.PatNote="Two a day";
        //medP.RxCui=542687;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //medP.MedDescript="Usept";
        //medP.PatNote="Three a day";
        //medP.RxCui=405384;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(1));
        //medP.MedDescript="SmileGuard";
        //medP.PatNote="Two a day";
        //medP.RxCui=1038751;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //medP.MedDescript="Slozem";
        //medP.PatNote="One a day";
        //medP.RxCui=151154;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(6));
        //medP.MedDescript="Prax";
        //medP.PatNote="Four a day";
        //medP.RxCui=219336;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //medP.MedDescript="PrameGel";
        //medP.PatNote="Two a day";
        //medP.RxCui=93822;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //medP.MedDescript="Pramotic";
        //medP.PatNote="Five a day";
        //medP.RxCui=405268;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(3));
        //medP.MedDescript="Medetomidine";
        //medP.PatNote="Three a day";
        //medP.RxCui=52016;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //medP=new MedicationPat();
        //medP.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //medP.MedDescript="Medcodin";
        //medP.PatNote="One a day";
        //medP.RxCui=218274;
        //medP.IsNew=true;
        //medP.PatNum=PatCur.PatNum;
        //ListMedicationPatNew.Add(medP);
        //Automation to initially fill reconcile grid with "recommended" rows.
        boolean isValid = new boolean();
        for (int i = 0;i < ListMedicationPatNew.Count;i++)
        {
            isValid = true;
            for (int j = 0;j < _listMedicationPatReconcile.Count;j++)
            {
                if (_listMedicationPatReconcile[j].RxCui == ListMedicationPatNew[i].RxCui)
                {
                    isValid = false;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listMedicationPatReconcile.Add(ListMedicationPatNew[i]);
            }
             
        }
        fillImportGrid();
        fillReconcileGrid();
    }

    private void fillImportGrid() throws Exception {
        gridMedImport.beginUpdate();
        gridMedImport.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 100, HorizontalAlignment.Center);
        gridMedImport.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridMedImport.getColumns().add(col);
        col = new ODGridColumn("Date Stop", 100, HorizontalAlignment.Center);
        gridMedImport.getColumns().add(col);
        col = new ODGridColumn("Description",220);
        gridMedImport.getColumns().add(col);
        gridMedImport.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListMedicationPatNew.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (ListMedicationPatNew[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListMedicationPatNew[i].DateStart.ToShortDateString());
            } 
            if (ListMedicationPatNew[i].DateStop.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListMedicationPatNew[i].DateStop.ToShortDateString());
            } 
            if (ListMedicationPatNew[i].MedDescript == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListMedicationPatNew[i].MedDescript);
            } 
            gridMedImport.getRows().add(row);
        }
        gridMedImport.endUpdate();
    }

    private void fillExistingGrid() throws Exception {
        gridMedExisting.beginUpdate();
        gridMedExisting.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 100, HorizontalAlignment.Center);
        gridMedExisting.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridMedExisting.getColumns().add(col);
        col = new ODGridColumn("Date Stop", 100, HorizontalAlignment.Center);
        gridMedExisting.getColumns().add(col);
        col = new ODGridColumn("Description",320);
        gridMedExisting.getColumns().add(col);
        gridMedExisting.getRows().Clear();
        _listMedicationPatCur = MedicationPats.getMedPatsForReconcile(_patCur.PatNum);
        List<long> medicationNums = new List<long>();
        for (int h = 0;h < _listMedicationPatCur.Count;h++)
        {
            if (_listMedicationPatCur[h].MedicationNum > 0)
            {
                medicationNums.Add(_listMedicationPatCur[h].MedicationNum);
            }
             
        }
        _listMedicationCur = Medications.GetMultMedications(medicationNums);
        OpenDental.UI.ODGridRow row;
        Medication med;
        for (int i = 0;i < _listMedicationPatCur.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            med = Medications.GetMedication(_listMedicationPatCur[i].MedicationNum);
            //Possibly change if we decided to postpone caching medications
            row.getCells().Add(_listMedicationPatCur[i].DateTStamp.ToShortDateString());
            if (_listMedicationPatCur[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listMedicationPatCur[i].DateStart.ToShortDateString());
            } 
            if (_listMedicationPatCur[i].DateStop.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listMedicationPatCur[i].DateStop.ToShortDateString());
            } 
            if (med.MedName == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(med.MedName);
            } 
            gridMedExisting.getRows().add(row);
        }
        gridMedExisting.endUpdate();
    }

    private void fillReconcileGrid() throws Exception {
        gridMedReconcile.beginUpdate();
        gridMedReconcile.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 130, HorizontalAlignment.Center);
        gridMedReconcile.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridMedReconcile.getColumns().add(col);
        col = new ODGridColumn("Date Stop", 100, HorizontalAlignment.Center);
        gridMedReconcile.getColumns().add(col);
        col = new ODGridColumn("Description",350);
        gridMedReconcile.getColumns().add(col);
        col = new ODGridColumn("Notes",150);
        gridMedReconcile.getColumns().add(col);
        col = new ODGridColumn("Is Incoming", 50, HorizontalAlignment.Center);
        gridMedReconcile.getColumns().add(col);
        gridMedReconcile.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < _listMedicationPatReconcile.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (_listMedicationPatReconcile[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listMedicationPatReconcile[i].DateStart.ToShortDateString());
            } 
            if (_listMedicationPatReconcile[i].DateStop.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listMedicationPatReconcile[i].DateStop.ToShortDateString());
            } 
            if (_listMedicationPatReconcile[i].IsNew)
            {
                if (_listMedicationPatReconcile[i].MedDescript == null)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(_listMedicationPatReconcile[i].MedDescript);
                } 
            }
            else
            {
                Medication med = Medications.GetMedication(_listMedicationPatReconcile[i].MedicationNum);
                if (med.MedName == null)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().add(med.MedName);
                } 
            } 
            if (_listMedicationPatReconcile[i].PatNote == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listMedicationPatReconcile[i].PatNote);
            } 
            row.getCells().add(_listMedicationPatReconcile[i].IsNew ? "X" : "");
            gridMedReconcile.getRows().add(row);
        }
        gridMedReconcile.endUpdate();
    }

    private void butAddNew_Click(Object sender, EventArgs e) throws Exception {
        if (gridMedImport.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        MedicationPat medP;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridMedImport.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridMedImport and ListMedicationPatNew are a 1:1 list we can use the selected index position to get our medP
            medP = ListMedicationPatNew[gridMedImport.getSelectedIndices()[i]];
            for (int j = 0;j < gridMedReconcile.getRows().Count;j++)
            {
                if (medP.RxCui > 0 && medP.RxCui == _listMedicationPatReconcile[j].RxCui)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listMedicationPatReconcile.Add(medP);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because medication already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butAddExist_Click(Object sender, EventArgs e) throws Exception {
        if (gridMedExisting.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        MedicationPat medP;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridMedExisting.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridMedImport and ListMedicationPatNew are a 1:1 list we can use the selected index position to get our medP
            medP = _listMedicationPatCur[gridMedExisting.getSelectedIndices()[i]];
            for (int j = 0;j < gridMedReconcile.getRows().Count;j++)
            {
                if (medP.RxCui > 0 && medP.RxCui == _listMedicationPatReconcile[j].RxCui)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
                if (medP.MedicationNum == _listMedicationPatReconcile[j].MedicationNum)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listMedicationPatReconcile.Add(medP);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because medication already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butRemoveRec_Click(Object sender, EventArgs e) throws Exception {
        if (gridMedReconcile.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to remove");
            return ;
        }
         
        MedicationPat medP;
        for (int i = gridMedReconcile.getSelectedIndices().Length - 1;i > -1;i--)
        {
            //Loop backwards so that we can remove from the list as we go
            medP = _listMedicationPatReconcile[gridMedReconcile.getSelectedIndices()[i]];
            _listMedicationPatReconcile.Remove(medP);
        }
        fillReconcileGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (_listMedicationPatReconcile.Count == 0)
        {
            if (!MsgBox.show(this,true,"The reconcile list is empty which will cause all existing medications to be removed.  Continue?"))
            {
                return ;
            }
             
        }
         
        MedicationPat medP;
        boolean isActive = new boolean();
        for (int i = 0;i < _listMedicationPatCur.Count;i++)
        {
            //Discontinue any current medications that are not present in the reconcile list.
            //Start looping through all current medications
            isActive = false;
            medP = _listMedicationPatCur[i];
            for (int j = 0;j < _listMedicationPatReconcile.Count;j++)
            {
                //Compare each reconcile medication to the current medication
                if (medP.RxCui > 0 && medP.RxCui == _listMedicationPatReconcile[j].RxCui && _listMedicationPatReconcile[j].MedicationNum == _listMedicationPatCur[i].MedicationNum)
                {
                    //Has an RxNorm code and they are equal
                    isActive = true;
                    break;
                }
                 
            }
            if (!isActive)
            {
                //Update current medications.
                _listMedicationPatCur[i].DateStop = DateTime.Now;
                //Set the current DateStop to today (to set the medication as discontinued)
                MedicationPats.Update(_listMedicationPatCur[i]);
            }
             
        }
        //Always update every current medication for the patient so that DateTStamp reflects the last reconcile date.
        if (_listMedicationPatCur.Count > 0)
        {
            MedicationPats.resetTimeStamps(_patCur.PatNum,true);
        }
         
        Medication med;
        int index = new int();
        for (int j = 0;j < _listMedicationPatReconcile.Count;j++)
        {
            index = ListMedicationPatNew.IndexOf(_listMedicationPatReconcile[j]);
            if (index < 0)
            {
                continue;
            }
             
            if (_listMedicationPatReconcile[j] == ListMedicationPatNew[index])
            {
                med = Medications.GetMedicationFromDbByRxCui(_listMedicationPatReconcile[j].RxCui);
                if (med == null)
                {
                    med = new Medication();
                    med.MedName = ListMedicationPatNew[index].MedDescript;
                    med.RxCui = ListMedicationPatNew[index].RxCui;
                    ListMedicationPatNew[index].MedicationNum = Medications.insert(med);
                    med.GenericNum = med.MedicationNum;
                    Medications.update(med);
                }
                else
                {
                    ListMedicationPatNew[index].MedicationNum = med.MedicationNum;
                } 
                ListMedicationPatNew[index].ProvNum = 0;
                //Since imported, set provnum to 0 so it does not affect CPOE.
                MedicationPats.Insert(ListMedicationPatNew[index]);
            }
             
        }
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.MedicationReconcile;
        newMeasureEvent.PatNum = _patCur.PatNum;
        newMeasureEvent.MoreInfo = "";
        EhrMeasureEvents.insert(newMeasureEvent);
        for (int inter = 0;inter < _listMedicationPatReconcile.Count;inter++)
        {
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).MedicationCDS)
            {
                Medication medInter = Medications.GetMedicationFromDbByRxCui(_listMedicationPatReconcile[inter].RxCui);
                FormCDSIntervention FormCDSI = new FormCDSIntervention();
                FormCDSI.ListCDSI = EhrTriggers.triggerMatch(medInter,_patCur);
                FormCDSI.showIfRequired(false);
            }
             
        }
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReconcileMedication.class);
        this.gridMedExisting = new OpenDental.UI.ODGrid();
        this.gridMedImport = new OpenDental.UI.ODGrid();
        this.gridMedReconcile = new OpenDental.UI.ODGrid();
        this.butRemoveRec = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butAddNew = new OpenDental.UI.Button();
        this.butAddExist = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.labelBatch = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // gridMedExisting
        //
        this.gridMedExisting.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMedExisting.setHScrollVisible(false);
        this.gridMedExisting.Location = new System.Drawing.Point(4, 12);
        this.gridMedExisting.Name = "gridMedExisting";
        this.gridMedExisting.setScrollValue(0);
        this.gridMedExisting.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMedExisting.Size = new System.Drawing.Size(480, 245);
        this.gridMedExisting.TabIndex = 65;
        this.gridMedExisting.setTitle("Current Medications");
        this.gridMedExisting.setTranslationName("GridMedExisting");
        //
        // gridMedImport
        //
        this.gridMedImport.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMedImport.setHScrollVisible(false);
        this.gridMedImport.Location = new System.Drawing.Point(497, 12);
        this.gridMedImport.Name = "gridMedImport";
        this.gridMedImport.setScrollValue(0);
        this.gridMedImport.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMedImport.Size = new System.Drawing.Size(480, 245);
        this.gridMedImport.TabIndex = 77;
        this.gridMedImport.setTitle("Transition of Care/Referral Summary");
        this.gridMedImport.setTranslationName("GridMedImport");
        //
        // gridMedReconcile
        //
        this.gridMedReconcile.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMedReconcile.setHScrollVisible(false);
        this.gridMedReconcile.Location = new System.Drawing.Point(4, 293);
        this.gridMedReconcile.Name = "gridMedReconcile";
        this.gridMedReconcile.setScrollValue(0);
        this.gridMedReconcile.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMedReconcile.Size = new System.Drawing.Size(973, 300);
        this.gridMedReconcile.TabIndex = 67;
        this.gridMedReconcile.setTitle("Reconciled Medications");
        this.gridMedReconcile.setTranslationName("gridMedReconcile");
        //
        // butRemoveRec
        //
        this.butRemoveRec.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemoveRec.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.butRemoveRec.setAutosize(true);
        this.butRemoveRec.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemoveRec.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemoveRec.setCornerRadius(4F);
        this.butRemoveRec.Location = new System.Drawing.Point(437, 599);
        this.butRemoveRec.Name = "butRemoveRec";
        this.butRemoveRec.Size = new System.Drawing.Size(99, 24);
        this.butRemoveRec.TabIndex = 82;
        this.butRemoveRec.Text = "Remove Selected";
        this.butRemoveRec.Click += new System.EventHandler(this.butRemoveRec_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(821, 640);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 81;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butAddNew
        //
        this.butAddNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddNew.setAutosize(true);
        this.butAddNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNew.setCornerRadius(4F);
        this.butAddNew.Image = Resources.getdown();
        this.butAddNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNew.Location = new System.Drawing.Point(712, 263);
        this.butAddNew.Name = "butAddNew";
        this.butAddNew.Size = new System.Drawing.Size(51, 24);
        this.butAddNew.TabIndex = 80;
        this.butAddNew.Text = "Add";
        this.butAddNew.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butAddNew.Click += new System.EventHandler(this.butAddNew_Click);
        //
        // butAddExist
        //
        this.butAddExist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddExist.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.butAddExist.setAutosize(true);
        this.butAddExist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddExist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddExist.setCornerRadius(4F);
        this.butAddExist.Image = Resources.getdown();
        this.butAddExist.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddExist.Location = new System.Drawing.Point(218, 263);
        this.butAddExist.Name = "butAddExist";
        this.butAddExist.Size = new System.Drawing.Size(51, 24);
        this.butAddExist.TabIndex = 79;
        this.butAddExist.Text = "Add";
        this.butAddExist.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butAddExist.Click += new System.EventHandler(this.butAddExist_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(902, 640);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // labelBatch
        //
        this.labelBatch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.labelBatch.Location = new System.Drawing.Point(76, 640);
        this.labelBatch.Name = "labelBatch";
        this.labelBatch.Size = new System.Drawing.Size(739, 24);
        this.labelBatch.TabIndex = 153;
        this.labelBatch.Text = "Clicking OK updates the patient\'s medications to match the reconciled list.";
        this.labelBatch.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormReconcileMedication
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 676);
        this.Controls.Add(this.labelBatch);
        this.Controls.Add(this.gridMedExisting);
        this.Controls.Add(this.gridMedImport);
        this.Controls.Add(this.butRemoveRec);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butAddNew);
        this.Controls.Add(this.butAddExist);
        this.Controls.Add(this.gridMedReconcile);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(530, 518);
        this.Name = "FormReconcileMedication";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reconcile Medication";
        this.Load += new System.EventHandler(this.FormReconcileMedication_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMedReconcile;
    private OpenDental.UI.ODGrid gridMedImport;
    private OpenDental.UI.ODGrid gridMedExisting;
    private OpenDental.UI.Button butAddExist;
    private OpenDental.UI.Button butAddNew;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butRemoveRec;
    private System.Windows.Forms.Label labelBatch = new System.Windows.Forms.Label();
}


