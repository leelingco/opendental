//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormCDSIntervention;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Security;
import OpenDental.FormReconcileAllergy;
import OpenDental.Properties.Resources;

public class FormReconcileAllergy  extends Form 
{

    public List<AllergyDef> ListAllergyDefNew = new List<AllergyDef>();
    public List<Allergy> ListAllergyNew = new List<Allergy>();
    private List<Allergy> _listAllergyReconcile = new List<Allergy>();
    private List<AllergyDef> _listAllergyDefCur = new List<AllergyDef>();
    private List<Allergy> _listAllergyCur = new List<Allergy>();
    private Patient _patCur;
    /**
    * Patient must be valid.  Do not pass null.
    */
    public FormReconcileAllergy(Patient patCur) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patCur = patCur;
    }

    private void formReconcileAllergy_Load(Object sender, EventArgs e) throws Exception {
        for (int index = 0;index < ListAllergyNew.Count;index++)
        {
            ListAllergyNew[index].PatNum = _patCur.PatNum;
        }
        fillExistingGrid();
        //Done first so that _listAllergyCur and _listAllergyDefCur are populated.
        _listAllergyReconcile = new List<Allergy>(_listAllergyCur);
        //-------------------------------Delete after testing
        //ListAllergyNew=new List<Allergy>();
        //Allergy al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //al.SnomedReaction="51242-b";
        //al.Reaction="Hives";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //al.SnomedReaction="66452-b";
        //al.Reaction="Chaffing";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(10));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(10));
        //al.SnomedReaction="48518475-b";
        //al.Reaction="Shivers";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //al.SnomedReaction="5984145-b";
        //al.Reaction="Vomiting";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(9));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(9));
        //al.SnomedReaction="5461238-b";
        //al.Reaction="Swelling";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //al.SnomedReaction="253-b";
        //al.Reaction="Yuck";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(12));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(12));
        //al.SnomedReaction="45451-b";
        //al.Reaction="Epic Swelling";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //al.SnomedReaction="511232-b";
        //al.Reaction="Rashes";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //al=new Allergy();
        //al.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //al.DateAdverseReaction=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //al.SnomedReaction="986321-b";
        //al.Reaction="Death";
        //al.StatusIsActive=true;
        //al.PatNum=PatCur.PatNum;
        //al.IsNew=true;
        //ListAllergyNew.Add(al);
        //ListAllergyDefNew=new List<AllergyDef>();
        //AllergyDef ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(5));
        //ald.SnomedAllergyTo="51242";
        //ald.SnomedType=SnomedAllergy.FoodIntolerance;
        //ald.Description="Allergy - Milk";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(2));
        //ald.SnomedAllergyTo="66452";
        //ald.SnomedType=SnomedAllergy.DrugIntolerance;
        //ald.Description="Allergy - Ibuprofen";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(10));
        //ald.SnomedAllergyTo="48518475";
        //ald.SnomedType=SnomedAllergy.AllergyToSubstance;
        //ald.Description="Allergy - Alcohol";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(4));
        //ald.SnomedAllergyTo="5984145";
        //ald.SnomedType=SnomedAllergy.DrugAllergy;
        //ald.Description="Allergy - Morphine";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(9));
        //ald.SnomedAllergyTo="5461238";
        //ald.SnomedType=SnomedAllergy.AdverseReactionsToFood;
        //ald.Description="Allergy - Nuts";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(7));
        //ald.SnomedAllergyTo="253";
        //ald.SnomedType=SnomedAllergy.FoodAllergy;
        //ald.Description="Allergy - Tomatoes";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(12));
        //ald.SnomedAllergyTo="45451";
        //ald.SnomedType=SnomedAllergy.AllergyToSubstance;
        //ald.Description="Allergy - Bees";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(11));
        //ald.SnomedAllergyTo="511232";
        //ald.SnomedType=SnomedAllergy.AllergyToSubstance;
        //ald.Description="Allergy - Latex";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //ald=new AllergyDef();
        //ald.DateTStamp=DateTime.Now.Subtract(TimeSpan.FromDays(8));
        //ald.SnomedAllergyTo="986321";
        //ald.SnomedType=SnomedAllergy.AdverseReactionsToSubstance;
        //ald.Description="Allergy - Air";
        //ald.IsNew=true;
        //ListAllergyDefNew.Add(ald);
        //-------------------------------
        //Automation to initially fill reconcile grid with "recommended" rows.
        boolean isValid = new boolean();
        for (int i = 0;i < ListAllergyNew.Count;i++)
        {
            isValid = true;
            for (int j = 0;j < _listAllergyDefCur.Count;j++)
            {
                //if(_listAllergyDefCur[j].SnomedAllergyTo==ListAllergyDefNew[i].SnomedAllergyTo) {//TODO: Change to UNII
                //	isValid=false;
                //	break;
                //}
                if (_listAllergyDefCur[j].MedicationNum == ListAllergyDefNew[i].MedicationNum)
                {
                    //Check Medications to determine if the Reconcile list already has that MedicationNum
                    isValid = false;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listAllergyReconcile.Add(ListAllergyNew[i]);
            }
             
        }
        fillImportGrid();
        fillReconcileGrid();
    }

    private void fillImportGrid() throws Exception {
        gridAllergyImport.beginUpdate();
        gridAllergyImport.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 90, HorizontalAlignment.Center);
        gridAllergyImport.getColumns().add(col);
        col = new ODGridColumn("Description",200);
        gridAllergyImport.getColumns().add(col);
        col = new ODGridColumn("Reaction",100);
        gridAllergyImport.getColumns().add(col);
        col = new ODGridColumn("Inactive", 80, HorizontalAlignment.Center);
        gridAllergyImport.getColumns().add(col);
        gridAllergyImport.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListAllergyNew.Count;i++)
        {
            //ListAllergyNew and ListAllergyDefNew should be a 1:1 ratio so we can use the same loop for both.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (ListAllergyDefNew[i].Description == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListAllergyDefNew[i].Description);
            } 
            if (ListAllergyNew[i].Reaction == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListAllergyNew[i].Reaction);
            } 
            if (ListAllergyNew[i].StatusIsActive)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            gridAllergyImport.getRows().add(row);
        }
        gridAllergyImport.endUpdate();
    }

    private void fillExistingGrid() throws Exception {
        gridAllergyExisting.beginUpdate();
        gridAllergyExisting.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 90, HorizontalAlignment.Center);
        gridAllergyExisting.getColumns().add(col);
        col = new ODGridColumn("Description",200);
        gridAllergyExisting.getColumns().add(col);
        col = new ODGridColumn("Reaction",100);
        gridAllergyExisting.getColumns().add(col);
        col = new ODGridColumn("Inactive", 80, HorizontalAlignment.Center);
        gridAllergyExisting.getColumns().add(col);
        gridAllergyExisting.getRows().Clear();
        _listAllergyCur = Allergies.getAll(_patCur.PatNum,false);
        List<long> allergyDefNums = new List<long>();
        for (int h = 0;h < _listAllergyCur.Count;h++)
        {
            if (_listAllergyCur[h].AllergyDefNum > 0)
            {
                allergyDefNums.Add(_listAllergyCur[h].AllergyDefNum);
            }
             
        }
        _listAllergyDefCur = AllergyDefs.GetMultAllergyDefs(allergyDefNums);
        OpenDental.UI.ODGridRow row;
        AllergyDef ald;
        for (int i = 0;i < _listAllergyCur.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            ald = new AllergyDef();
            ald = AllergyDefs.GetOne(_listAllergyCur[i].AllergyDefNum, _listAllergyDefCur);
            row.getCells().Add(_listAllergyCur[i].DateTStamp.ToShortDateString());
            if (ald.Description == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(ald.Description);
            } 
            if (_listAllergyCur[i].Reaction == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listAllergyCur[i].Reaction);
            } 
            if (_listAllergyCur[i].StatusIsActive)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            gridAllergyExisting.getRows().add(row);
        }
        gridAllergyExisting.endUpdate();
    }

    private void fillReconcileGrid() throws Exception {
        gridAllergyReconcile.beginUpdate();
        gridAllergyReconcile.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Last Modified", 90, HorizontalAlignment.Center);
        gridAllergyReconcile.getColumns().add(col);
        col = new ODGridColumn("Description",400);
        gridAllergyReconcile.getColumns().add(col);
        col = new ODGridColumn("Reaction",300);
        gridAllergyReconcile.getColumns().add(col);
        col = new ODGridColumn("Inactive", 80, HorizontalAlignment.Center);
        gridAllergyReconcile.getColumns().add(col);
        col = new ODGridColumn("Is Incoming", 100, HorizontalAlignment.Center);
        gridAllergyReconcile.getColumns().add(col);
        gridAllergyReconcile.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        AllergyDef ald = new AllergyDef();
        for (int i = 0;i < _listAllergyReconcile.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            ald = new AllergyDef();
            if (_listAllergyReconcile[i].IsNew)
            {
                //To find the allergy def for new allergies, get the index of the matching allergy in ListAllergyNew, and use that index in ListAllergyDefNew because they are 1 to 1 lists.
                ald = ListAllergyDefNew[ListAllergyNew.IndexOf(_listAllergyReconcile[i])];
            }
             
            for (int j = 0;j < _listAllergyDefCur.Count;j++)
            {
                if (_listAllergyReconcile[i].AllergyDefNum > 0 && _listAllergyReconcile[i].AllergyDefNum == _listAllergyDefCur[j].AllergyDefNum)
                {
                    ald = _listAllergyDefCur[j];
                    break;
                }
                 
            }
            //Gets the allergydef matching the allergy so we can use it to populate the grid
            row.getCells().Add(DateTime.Now.ToShortDateString());
            if (ald.Description == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(ald.Description);
            } 
            if (_listAllergyReconcile[i].Reaction == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_listAllergyReconcile[i].Reaction);
            } 
            if (_listAllergyReconcile[i].StatusIsActive)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            row.getCells().add(_listAllergyReconcile[i].IsNew ? "X" : "");
            gridAllergyReconcile.getRows().add(row);
        }
        gridAllergyReconcile.endUpdate();
    }

    private void butAddNew_Click(Object sender, EventArgs e) throws Exception {
        if (gridAllergyImport.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        Allergy al;
        AllergyDef alD;
        AllergyDef alDR = null;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridAllergyImport.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridAllergyImport and ListAllergyNew are a 1:1 list we can use the selected index position to get our allergy
            al = ListAllergyNew[gridAllergyImport.getSelectedIndices()[i]];
            alD = ListAllergyDefNew[gridAllergyImport.getSelectedIndices()[i]];
            for (int j = 0;j < _listAllergyReconcile.Count;j++)
            {
                //ListAllergyDefNew is also a 1:1 to gridAllergyImport.
                if (_listAllergyReconcile[j].IsNew)
                {
                    alDR = ListAllergyDefNew[ListAllergyNew.IndexOf(_listAllergyReconcile[j])];
                }
                else
                {
                    alDR = AllergyDefs.GetOne(_listAllergyReconcile[j].AllergyDefNum);
                } 
                if (alDR == null)
                {
                    continue;
                }
                 
                //if(alDR.SnomedAllergyTo!="" && alDR.SnomedAllergyTo!=null && alDR.SnomedAllergyTo==alD.SnomedAllergyTo) {//TODO: Change to UNII
                //	isValid=false;
                //	skipCount++;
                //	break;
                //}
                if (alDR.MedicationNum != 0 && alDR.MedicationNum == alD.MedicationNum)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
            }
            if (isValid)
            {
                _listAllergyReconcile.Add(al);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because allergy already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butAddExist_Click(Object sender, EventArgs e) throws Exception {
        if (gridAllergyExisting.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to add");
            return ;
        }
         
        Allergy al;
        AllergyDef alD;
        int skipCount = 0;
        boolean isValid = new boolean();
        for (int i = 0;i < gridAllergyExisting.getSelectedIndices().Length;i++)
        {
            isValid = true;
            //Since gridAllergyExisting and _listAllergyCur are a 1:1 list we can use the selected index position to get our allergy
            al = _listAllergyCur[gridAllergyExisting.getSelectedIndices()[i]];
            alD = AllergyDefs.getOne(al.AllergyDefNum,_listAllergyDefCur);
            if (_listAllergyReconcile.Count == 0)
            {
                _listAllergyReconcile.Add(al);
                continue;
            }
             
            for (int j = 0;j < _listAllergyReconcile.Count;j++)
            {
                if (!_listAllergyReconcile[j].IsNew && _listAllergyReconcile[j].AllergyNum == al.AllergyNum)
                {
                    //If not new, then from existing list.  Check allergynums
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
                if (_listAllergyReconcile[j].IsNew)
                {
                    //This is an allergy that is coming in from and external source.
                    AllergyDef alDN = null;
                    int index = ListAllergyNew.IndexOf(_listAllergyReconcile[j]);
                    //Find the corresponding allergy def by looping through the incoming allergies.
                    if (index == -1)
                    {
                        continue;
                    }
                     
                    //This should not happen
                    alDN = ListAllergyDefNew[index];
                    //Incoming allergy and allergy def lists are 1 to 1 so we can use the same index.
                    if (alDN != null && alDN.MedicationNum == alD.MedicationNum)
                    {
                        isValid = false;
                        skipCount++;
                        break;
                    }
                     
                }
                 
                if (al.AllergyDefNum == _listAllergyReconcile[j].AllergyDefNum)
                {
                    isValid = false;
                    skipCount++;
                    break;
                }
                 
                if (!isValid)
                {
                    break;
                }
                 
            }
            if (isValid)
            {
                _listAllergyReconcile.Add(al);
            }
             
        }
        if (skipCount > 0)
        {
            MessageBox.Show(Lan.g(this," Row(s) skipped because allergy already present in the reconcile list") + ": " + skipCount);
        }
         
        fillReconcileGrid();
    }

    private void butRemoveRec_Click(Object sender, EventArgs e) throws Exception {
        if (gridAllergyReconcile.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"A row must be selected to remove");
            return ;
        }
         
        Allergy al;
        for (int i = gridAllergyReconcile.getSelectedIndices().Length - 1;i > -1;i--)
        {
            //Loop backwards so that we can remove from the list as we go
            al = _listAllergyReconcile[gridAllergyReconcile.getSelectedIndices()[i]];
            _listAllergyReconcile.Remove(al);
        }
        fillReconcileGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (_listAllergyReconcile.Count == 0)
        {
            if (!MsgBox.show(this,true,"The reconcile list is empty which will cause all existing allergies to be removed.  Continue?"))
            {
                return ;
            }
             
        }
         
        Allergy al;
        AllergyDef alD;
        boolean isActive = new boolean();
        for (int i = 0;i < _listAllergyCur.Count;i++)
        {
            //Discontinue any current medications that are not present in the reconcile list.
            //Start looping through all current allergies
            isActive = false;
            al = _listAllergyCur[i];
            alD = AllergyDefs.getOne(al.AllergyDefNum,_listAllergyDefCur);
            for (int j = 0;j < _listAllergyReconcile.Count;j++)
            {
                //Compare each reconcile allergy to the current allergy
                AllergyDef alDR = AllergyDefs.GetOne(_listAllergyReconcile[j].AllergyDefNum, _listAllergyDefCur);
                if (_listAllergyReconcile[j].AllergyDefNum == _listAllergyCur[i].AllergyDefNum)
                {
                    //Has identical AllergyDefNums
                    isActive = true;
                    break;
                }
                 
                if (alDR == null)
                {
                    continue;
                }
                 
                //if(alDR.SnomedAllergyTo!="" && alDR.SnomedAllergyTo==alD.SnomedAllergyTo) {//TODO: Change to UNII
                //	isActive=true;
                //	break;
                //}
                if (alDR.MedicationNum != 0 && alDR.MedicationNum == alD.MedicationNum)
                {
                    //Has a Snomed code and they are equal
                    isActive = true;
                    break;
                }
                 
            }
            if (!isActive)
            {
                _listAllergyCur[i].StatusIsActive = isActive;
                Allergies.Update(_listAllergyCur[i]);
            }
             
        }
        //Always update every current allergy for the patient so that DateTStamp reflects the last reconcile date.
        if (_listAllergyCur.Count > 0)
        {
            Allergies.resetTimeStamps(_patCur.PatNum,true);
        }
         
        AllergyDef alDU;
        int index = new int();
        for (int j = 0;j < _listAllergyReconcile.Count;j++)
        {
            if (!_listAllergyReconcile[j].IsNew)
            {
                continue;
            }
             
            index = ListAllergyNew.IndexOf(_listAllergyReconcile[j]);
            //Returns -1 if not found.
            if (index < 0)
            {
                continue;
            }
             
            //Insert the AllergyDef and Allergy if needed.
            if (ListAllergyDefNew[index].MedicationNum != 0)
            {
                alDU = AllergyDefs.GetAllergyDefFromMedication(ListAllergyDefNew[index].MedicationNum);
            }
            else
            {
                alDU = null;
            } 
            //remove once UNII is implemented
            //alDU=AllergyDefs.GetAllergyDefFromCode(ListAllergyDefNew[index].SnomedAllergyTo);//TODO: Change to UNII
            if (alDU == null)
            {
                //db is missing the def
                ListAllergyNew[index].AllergyDefNum = AllergyDefs.Insert(ListAllergyDefNew[index]);
            }
            else
            {
                ListAllergyNew[index].AllergyDefNum = alDU.AllergyDefNum;
            } 
            //Set the allergydefnum on the allergy.
            Allergies.Insert(ListAllergyNew[index]);
        }
        for (int inter = 0;inter < _listAllergyReconcile.Count;inter++)
        {
            //TODO: Make an allergy measure event if one is needed for MU3.
            //EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            //newMeasureEvent.DateTEvent=DateTime.Now;
            //newMeasureEvent.EventType=EhrMeasureEventType.AllergyReconcile;
            //newMeasureEvent.PatNum=PatCur.PatNum;
            //newMeasureEvent.MoreInfo="";
            //EhrMeasureEvents.Insert(newMeasureEvent);
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).AllergyCDS)
            {
                AllergyDef alDInter = AllergyDefs.GetOne(_listAllergyReconcile[inter].AllergyDefNum);
                FormCDSIntervention FormCDSI = new FormCDSIntervention();
                FormCDSI.ListCDSI = EhrTriggers.triggerMatch(alDInter,_patCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReconcileAllergy.class);
        this.labelBatch = new System.Windows.Forms.Label();
        this.gridAllergyExisting = new OpenDental.UI.ODGrid();
        this.gridAllergyImport = new OpenDental.UI.ODGrid();
        this.butRemoveRec = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butAddNew = new OpenDental.UI.Button();
        this.butAddExist = new OpenDental.UI.Button();
        this.gridAllergyReconcile = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelBatch
        //
        this.labelBatch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.labelBatch.Location = new System.Drawing.Point(76, 640);
        this.labelBatch.Name = "labelBatch";
        this.labelBatch.Size = new System.Drawing.Size(739, 24);
        this.labelBatch.TabIndex = 152;
        this.labelBatch.Text = "Clicking OK updates the patient\'s allergies to match the reconciled list.";
        this.labelBatch.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridAllergyExisting
        //
        this.gridAllergyExisting.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridAllergyExisting.setHScrollVisible(false);
        this.gridAllergyExisting.Location = new System.Drawing.Point(4, 12);
        this.gridAllergyExisting.Name = "gridAllergyExisting";
        this.gridAllergyExisting.setScrollValue(0);
        this.gridAllergyExisting.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAllergyExisting.Size = new System.Drawing.Size(477, 245);
        this.gridAllergyExisting.TabIndex = 65;
        this.gridAllergyExisting.setTitle("Current Allergies");
        this.gridAllergyExisting.setTranslationName("GridAllergyExisting");
        //
        // gridAllergyImport
        //
        this.gridAllergyImport.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridAllergyImport.setHScrollVisible(false);
        this.gridAllergyImport.Location = new System.Drawing.Point(497, 12);
        this.gridAllergyImport.Name = "gridAllergyImport";
        this.gridAllergyImport.setScrollValue(0);
        this.gridAllergyImport.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAllergyImport.Size = new System.Drawing.Size(480, 245);
        this.gridAllergyImport.TabIndex = 77;
        this.gridAllergyImport.setTitle("Transition of Care/Referral Summary");
        this.gridAllergyImport.setTranslationName("GridAllergyImport");
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
        // gridAllergyReconcile
        //
        this.gridAllergyReconcile.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridAllergyReconcile.setHScrollVisible(false);
        this.gridAllergyReconcile.Location = new System.Drawing.Point(4, 293);
        this.gridAllergyReconcile.Name = "gridAllergyReconcile";
        this.gridAllergyReconcile.setScrollValue(0);
        this.gridAllergyReconcile.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAllergyReconcile.Size = new System.Drawing.Size(973, 300);
        this.gridAllergyReconcile.TabIndex = 67;
        this.gridAllergyReconcile.setTitle("Reconciled Allergies");
        this.gridAllergyReconcile.setTranslationName("gridAllergyReconcile");
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
        // FormReconcileAllergy
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 676);
        this.Controls.Add(this.labelBatch);
        this.Controls.Add(this.gridAllergyExisting);
        this.Controls.Add(this.gridAllergyImport);
        this.Controls.Add(this.butRemoveRec);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butAddNew);
        this.Controls.Add(this.butAddExist);
        this.Controls.Add(this.gridAllergyReconcile);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(530, 518);
        this.Name = "FormReconcileAllergy";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reconcile Allergies";
        this.Load += new System.EventHandler(this.FormReconcileAllergy_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridAllergyReconcile;
    private OpenDental.UI.ODGrid gridAllergyImport;
    private OpenDental.UI.ODGrid gridAllergyExisting;
    private OpenDental.UI.Button butAddExist;
    private OpenDental.UI.Button butAddNew;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butRemoveRec;
    private System.Windows.Forms.Label labelBatch = new System.Windows.Forms.Label();
}


