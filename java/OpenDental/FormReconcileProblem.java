//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormCDSIntervention;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.Security;
import OpenDental.FormReconcileProblem;
import OpenDental.Properties.Resources;

public class FormReconcileProblem  extends Form 
{

    public List<DiseaseDef> ListProblemDefNew = new List<DiseaseDef>();
    public List<Disease> ListProblemNew = new List<Disease>();
    private List<Disease> _listProblemReconcile = new List<Disease>();
    private List<DiseaseDef> _listProblemDefCur = new List<DiseaseDef>();
    private List<Disease> _listProblemCur = new List<Disease>();
    private Patient _patCur;
    /**
    * Patient must be valid.  Do not pass null.
    */
    public FormReconcileProblem(Patient patCur) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patCur = patCur;
    }

    private void formReconcileProblem_Load(Object sender, EventArgs e) throws Exception {
        for (int index = 0;index < ListProblemNew.Count;index++)
        {
            ListProblemNew[index].PatNum = _patCur.PatNum;
        }
        fillExistingGrid();
        //Done first so that _listReconcileCur and _listReconcileDefCur are populated.
        _listProblemReconcile = new List<Disease>(_listProblemCur);
        //-------------------------------Delete after testing
        //ListProblemNew=new List<Disease>();
        //Disease dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(3));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(3));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Terrible";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Deadly";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Other";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Can't Think";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="What is Next!";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Hmmmm...";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(1));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(1));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Otherthly";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(6));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(6));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Dependant";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //dis=new Disease();
        //dis.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //dis.DateStart=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //dis.PatNum=PatCur.PatNum;
        //dis.ProbStatus=0;
        //dis.PatNote="Shifty";
        //dis.IsNew=true;
        //ListProblemNew.Add(dis);
        //ListProblemDefNew=new List<DiseaseDef>();
        //DiseaseDef disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(3));
        //disD.DiseaseName="Totally Preggers";
        //disD.SnomedCode="54116654";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //disD.DiseaseName="Paraplegic";
        //disD.SnomedCode="4651561";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //disD.DiseaseName="HIV/AIDS";
        //disD.SnomedCode="2165";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //disD.DiseaseName="Milk Addict";
        //disD.SnomedCode="16544633";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //disD.DiseaseName="Munchies";
        //disD.SnomedCode="41842384";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //disD.DiseaseName="Gaddafid";
        //disD.SnomedCode="48416321";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(1));
        //disD.DiseaseName="D-Tosh Disease";
        //disD.SnomedCode="1847913";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(6));
        //disD.DiseaseName="Uncontrollable Hiccups";
        //disD.SnomedCode="486316";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //disD=new DiseaseDef();
        //disD.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //disD.DiseaseName="Explosive Diarrhea";
        //disD.SnomedCode="9874954165";
        //disD.IsHidden=false;
        //disD.IsNew=true;
        //ListProblemDefNew.Add(disD);
        //-------------------------------
        //Automation to initially fill reconcile grid with "recommended" rows.
        boolean isValid = new boolean();
        for (int i = 0;i < ListProblemNew.Count;i++)
        {
            isValid = true;
            for (int j = 0;j < _listProblemDefCur.Count;j++)
            {
                if (_listProblemDefCur[j].SnomedCode == ListProblemDefNew[i].SnomedCode)
                {
                    isValid = false;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listProblemReconcile.Add(ListProblemNew[i]);
            }
             
        }
        fillImportGrid();
        fillReconcileGrid();
    }

    private void fillImportGrid() throws Exception {
        gridProbImport.beginUpdate();
        gridProbImport.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 100, HorizontalAlignment.Center);
        gridProbImport.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridProbImport.getColumns().add(col);
        col = new ODGridColumn("Problem Name",200);
        gridProbImport.getColumns().add(col);
        col = new ODGridColumn("Status", 80, HorizontalAlignment.Center);
        gridProbImport.getColumns().add(col);
        gridProbImport.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListProblemNew.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (ListProblemNew[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListProblemNew[i].DateStart.ToShortDateString());
            } 
            if (ListProblemDefNew[i].DiseaseName == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListProblemDefNew[i].DiseaseName);
            } 
            if (ListProblemNew[i].ProbStatus == ProblemStatus.Active)
            {
                row.getCells().add("Active");
            }
            else if (ListProblemNew[i].ProbStatus == ProblemStatus.Resolved)
            {
                row.getCells().add("Resolved");
            }
            else
            {
                row.getCells().add("Inactive");
            }  
            gridProbImport.getRows().add(row);
        }
        gridProbImport.endUpdate();
    }

    private void fillExistingGrid() throws Exception {
        gridProbExisting.beginUpdate();
        gridProbExisting.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 100, HorizontalAlignment.Center);
        gridProbExisting.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridProbExisting.getColumns().add(col);
        col = new ODGridColumn("Problem Name",200);
        gridProbExisting.getColumns().add(col);
        col = new ODGridColumn("Status", 80, HorizontalAlignment.Center);
        gridProbExisting.getColumns().add(col);
        gridProbExisting.getRows().Clear();
        _listProblemCur = Diseases.refresh(_patCur.PatNum,true);
        List<long> problemDefNums = new List<long>();
        for (int h = 0;h < _listProblemCur.Count;h++)
        {
            if (_listProblemCur[h].DiseaseDefNum > 0)
            {
                problemDefNums.Add(_listProblemCur[h].DiseaseDefNum);
            }
             
        }
        _listProblemDefCur = DiseaseDefs.GetMultDiseaseDefs(problemDefNums);
        OpenDental.UI.ODGridRow row;
        DiseaseDef disD;
        for (int i = 0;i < _listProblemCur.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            disD = new DiseaseDef();
            disD = DiseaseDefs.GetItem(_listProblemCur[i].DiseaseDefNum);
            row.getCells().Add(_listProblemCur[i].DateTStamp.ToShortDateString());
            if (_listProblemCur[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listProblemCur[i].DateStart.ToShortDateString());
            } 
            if (disD.DiseaseName == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(disD.DiseaseName);
            } 
            if (_listProblemCur[i].ProbStatus == ProblemStatus.Active)
            {
                row.getCells().add("Active");
            }
            else if (_listProblemCur[i].ProbStatus == ProblemStatus.Resolved)
            {
                row.getCells().add("Resolved");
            }
            else
            {
                row.getCells().add("Inactive");
            }  
            gridProbExisting.getRows().add(row);
        }
        gridProbExisting.endUpdate();
    }

    private void fillReconcileGrid() throws Exception {
        gridProbReconcile.beginUpdate();
        gridProbReconcile.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 130, HorizontalAlignment.Center);
        gridProbReconcile.getColumns().add(col);
        col = new ODGridColumn("Date Start", 100, HorizontalAlignment.Center);
        gridProbReconcile.getColumns().add(col);
        col = new ODGridColumn("Problem Name",260);
        gridProbReconcile.getColumns().add(col);
        col = new ODGridColumn("Notes",300);
        gridProbReconcile.getColumns().add(col);
        col = new ODGridColumn("Status", 80, HorizontalAlignment.Center);
        gridProbReconcile.getColumns().add(col);
        col = new ODGridColumn("Is Incoming", 50, HorizontalAlignment.Center);
        gridProbReconcile.getColumns().add(col);
        gridProbReconcile.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DiseaseDef disD;
        for (int i = 0;i < _listProblemReconcile.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            disD = new DiseaseDef();
            if (_listProblemReconcile[i].IsNew)
            {
                //To find the disease def for new disease, get the index of the matching problem in ListProblemNew, and use that index in ListProblemDefNew because they are 1 to 1 lists.
                disD = ListProblemDefNew[ListProblemNew.IndexOf(_listProblemReconcile[i])];
            }
             
            for (int j = 0;j < _listProblemDefCur.Count;j++)
            {
                if (_listProblemReconcile[i].DiseaseDefNum > 0 && _listProblemReconcile[i].DiseaseDefNum == _listProblemDefCur[j].DiseaseDefNum)
                {
                    disD = _listProblemDefCur[j];
                    break;
                }
                 
            }
            //Gets the diseasedef matching the disease so we can use it to populate the grid
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (_listProblemReconcile[i].DateStart.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listProblemReconcile[i].DateStart.ToShortDateString());
            } 
            if (disD.DiseaseName == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(disD.DiseaseName);
            } 
            if (_listProblemReconcile[i] == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listProblemReconcile[i].PatNote);
            } 
            if (_listProblemReconcile[i].ProbStatus == ProblemStatus.Active)
            {
                row.getCells().add("Active");
            }
            else if (_listProblemReconcile[i].ProbStatus == ProblemStatus.Resolved)
            {
                row.getCells().add("Resolved");
            }
            else
            {
                row.getCells().add("Inactive");
            }  
            row.getCells().add(_listProblemReconcile[i].IsNew ? "X" : "");
            gridProbReconcile.getRows().add(row);
        }
        gridProbReconcile.endUpdate();
    }

    private void butAddNew_Click(Object sender, EventArgs e) throws Exception {
        if (gridProbImport.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        Disease dis;
        DiseaseDef disD;
        DiseaseDef disDR = null;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridProbImport.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridProbImport and ListProblemPatNew are a 1:1 list we can use the selected index position to get our disease
            dis = ListProblemNew[gridProbImport.getSelectedIndices()[i]];
            disD = ListProblemDefNew[gridProbImport.getSelectedIndices()[i]];
            for (int j = 0;j < _listProblemReconcile.Count;j++)
            {
                if (_listProblemReconcile[j].IsNew)
                {
                    disDR = ListProblemDefNew[ListProblemNew.IndexOf(_listProblemReconcile[j])];
                }
                else
                {
                    disDR = DiseaseDefs.GetItem(_listProblemReconcile[j].DiseaseDefNum);
                } 
                if (disDR == null)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(disDR.SnomedCode, "") && StringSupport.equals(disDR.SnomedCode, disD.SnomedCode))
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listProblemReconcile.Add(dis);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because problem already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butAddExist_Click(Object sender, EventArgs e) throws Exception {
        if (gridProbExisting.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        Disease dis;
        DiseaseDef disD;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridProbExisting.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridProbImport and ListProblemPatNew are a 1:1 list we can use the selected index position to get our dis
            dis = _listProblemCur[gridProbExisting.getSelectedIndices()[i]];
            disD = DiseaseDefs.getItem(dis.DiseaseDefNum);
            for (int j = 0;j < _listProblemReconcile.Count;j++)
            {
                if (_listProblemCur[j].IsNew)
                {
                    for (int k = 0;k < ListProblemDefNew.Count;k++)
                    {
                        if (!StringSupport.equals(disD.SnomedCode, "") && StringSupport.equals(disD.SnomedCode, ListProblemDefNew[k].SnomedCode))
                        {
                            isValid = false;
                            skipCount++;
                            break;
                        }
                         
                    }
                }
                 
                if (!isValid)
                {
                    break;
                }
                 
                if (dis.DiseaseDefNum == _listProblemReconcile[j].DiseaseDefNum)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listProblemReconcile.Add(dis);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because problem already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butRemoveRec_Click(Object sender, EventArgs e) throws Exception {
        if (gridProbReconcile.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to remove");
            return ;
        }
         
        Disease dis;
        for (int i = gridProbReconcile.getSelectedIndices().Length - 1;i > -1;i--)
        {
            //Loop backwards so that we can remove from the list as we go
            dis = _listProblemReconcile[gridProbReconcile.getSelectedIndices()[i]];
            _listProblemReconcile.Remove(dis);
        }
        fillReconcileGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (_listProblemReconcile.Count == 0)
        {
            if (!MsgBox.show(this,true,"The reconcile list is empty which will cause all existing problems to be removed.  Continue?"))
            {
                return ;
            }
             
        }
         
        Disease dis;
        DiseaseDef disD;
        boolean isActive = new boolean();
        for (int i = 0;i < _listProblemCur.Count;i++)
        {
            //Discontinue any current medications that are not present in the reconcile list.
            //Start looping through all current problems
            isActive = false;
            dis = _listProblemCur[i];
            disD = DiseaseDefs.getItem(dis.DiseaseDefNum);
            for (int j = 0;j < _listProblemReconcile.Count;j++)
            {
                //Compare each reconcile problem to the current problem
                DiseaseDef disDR = DiseaseDefs.GetItem(_listProblemReconcile[j].DiseaseDefNum);
                if (_listProblemReconcile[j].DiseaseDefNum == _listProblemCur[i].DiseaseDefNum)
                {
                    //Has identical DiseaseDefNums
                    isActive = true;
                    break;
                }
                 
                if (disDR == null)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(disDR.SnomedCode, "") && StringSupport.equals(disDR.SnomedCode, disD.SnomedCode))
                {
                    //Has a Snomed code and they are equal
                    isActive = true;
                    break;
                }
                 
            }
            if (!isActive)
            {
                //Update current problems.
                dis.ProbStatus = ProblemStatus.Inactive;
                Diseases.Update(_listProblemCur[i]);
            }
             
        }
        //Always update every current problem for the patient so that DateTStamp reflects the last reconcile date.
        if (_listProblemCur.Count > 0)
        {
            Diseases.resetTimeStamps(_patCur.PatNum,ProblemStatus.Active);
        }
         
        DiseaseDef disDU = null;
        int index = new int();
        for (int j = 0;j < _listProblemReconcile.Count;j++)
        {
            index = ListProblemNew.IndexOf(_listProblemReconcile[j]);
            if (index < 0)
            {
                continue;
            }
             
            if (_listProblemReconcile[j] == ListProblemNew[index])
            {
                disDU = DiseaseDefs.GetItem(DiseaseDefs.GetNumFromCode(ListProblemDefNew[index].SnomedCode));
                if (disDU == null)
                {
                    ListProblemNew[index].DiseaseDefNum = DiseaseDefs.Insert(ListProblemDefNew[index]);
                }
                else
                {
                    ListProblemNew[index].DiseaseDefNum = disDU.DiseaseDefNum;
                } 
                Diseases.Insert(ListProblemNew[index]);
            }
             
        }
        DataValid.setInvalid(InvalidType.Diseases);
        for (int inter = 0;inter < _listProblemReconcile.Count;inter++)
        {
            //EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            //newMeasureEvent.DateTEvent=DateTime.Now;
            //newMeasureEvent.EventType=EhrMeasureEventType.ProblemReconcile;
            //newMeasureEvent.PatNum=PatCur.PatNum;
            //newMeasureEvent.MoreInfo="";
            //EhrMeasureEvents.Insert(newMeasureEvent);
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).ProblemCDS)
            {
                DiseaseDef disDInter = DiseaseDefs.GetItem(_listProblemReconcile[inter].DiseaseDefNum);
                FormCDSIntervention FormCDSI = new FormCDSIntervention();
                FormCDSI.ListCDSI = EhrTriggers.triggerMatch(disDInter,_patCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReconcileProblem.class);
        this.gridProbExisting = new OpenDental.UI.ODGrid();
        this.gridProbImport = new OpenDental.UI.ODGrid();
        this.butRemoveRec = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butAddNew = new OpenDental.UI.Button();
        this.butAddExist = new OpenDental.UI.Button();
        this.gridProbReconcile = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.labelBatch = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // gridProbExisting
        //
        this.gridProbExisting.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProbExisting.setHScrollVisible(false);
        this.gridProbExisting.Location = new System.Drawing.Point(4, 12);
        this.gridProbExisting.Name = "gridProbExisting";
        this.gridProbExisting.setScrollValue(0);
        this.gridProbExisting.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProbExisting.Size = new System.Drawing.Size(477, 245);
        this.gridProbExisting.TabIndex = 65;
        this.gridProbExisting.setTitle("Current Problems");
        this.gridProbExisting.setTranslationName("GridProbExisting");
        //
        // gridProbImport
        //
        this.gridProbImport.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProbImport.setHScrollVisible(false);
        this.gridProbImport.Location = new System.Drawing.Point(497, 12);
        this.gridProbImport.Name = "gridProbImport";
        this.gridProbImport.setScrollValue(0);
        this.gridProbImport.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProbImport.Size = new System.Drawing.Size(480, 245);
        this.gridProbImport.TabIndex = 77;
        this.gridProbImport.setTitle("Transition of Care/Referral Summary");
        this.gridProbImport.setTranslationName("GridProbImport");
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
        // gridProbReconcile
        //
        this.gridProbReconcile.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProbReconcile.setHScrollVisible(false);
        this.gridProbReconcile.Location = new System.Drawing.Point(4, 293);
        this.gridProbReconcile.Name = "gridProbReconcile";
        this.gridProbReconcile.setScrollValue(0);
        this.gridProbReconcile.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProbReconcile.Size = new System.Drawing.Size(973, 300);
        this.gridProbReconcile.TabIndex = 67;
        this.gridProbReconcile.setTitle("Reconciled Problem");
        this.gridProbReconcile.setTranslationName("gridProbReconcile");
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
        this.labelBatch.Text = "Clicking OK updates the patient\'s problems to match the reconciled list.";
        this.labelBatch.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormReconcileProblem
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 676);
        this.Controls.Add(this.labelBatch);
        this.Controls.Add(this.gridProbExisting);
        this.Controls.Add(this.gridProbImport);
        this.Controls.Add(this.butRemoveRec);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butAddNew);
        this.Controls.Add(this.butAddExist);
        this.Controls.Add(this.gridProbReconcile);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(530, 518);
        this.Name = "FormReconcileProblem";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reconcile Problems";
        this.Load += new System.EventHandler(this.FormReconcileProblem_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridProbReconcile;
    private OpenDental.UI.ODGrid gridProbImport;
    private OpenDental.UI.ODGrid gridProbExisting;
    private OpenDental.UI.Button butAddExist;
    private OpenDental.UI.Button butAddNew;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butRemoveRec;
    private System.Windows.Forms.Label labelBatch = new System.Windows.Forms.Label();
}


