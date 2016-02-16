//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormPatListElementEditEHR2014;
import OpenDental.FormPatListResultsEHR2014;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrPatListElement2014;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPatListEHR2014  extends Form 
{

    private List<EhrPatListElement2014> _elementList = new List<EhrPatListElement2014>();
    public FormPatListEHR2014() throws Exception {
        initializeComponent();
    }

    private void formPatListEHR2014_Load(Object sender, EventArgs e) throws Exception {
        _elementList = new List<EhrPatListElement2014>();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Restriction",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Compare string",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Operand",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Lab value",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("After Date",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Before Date",120);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Order",120,HorizontalAlignment.Center);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < _elementList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(_elementList[i].Restriction.ToString());
            if (_elementList[i].Restriction == EhrRestrictionType.Problem)
            {
                if (Snomeds.CodeExists(_elementList[i].CompareString))
                {
                    row.getCells().Add(_elementList[i].CompareString + " - " + Snomeds.GetByCode(_elementList[i].CompareString).Description);
                }
                else
                {
                    row.getCells().Add(_elementList[i].CompareString + " - NON-SNOMED CT CODE");
                } 
            }
            else
            {
                row.getCells().Add(_elementList[i].CompareString);
            } 
            if (_elementList[i].Restriction == EhrRestrictionType.Gender || _elementList[i].Restriction == EhrRestrictionType.Problem || _elementList[i].Restriction == EhrRestrictionType.Medication || _elementList[i].Restriction == EhrRestrictionType.CommPref || _elementList[i].Restriction == EhrRestrictionType.Allergy)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(_elementList[i].Operand.ToString());
            } 
            row.getCells().Add(_elementList[i].LabValue);
            if (_elementList[i].StartDate.Year > 1880)
            {
                row.getCells().Add(_elementList[i].StartDate.ToShortDateString());
            }
            else
            {
                row.getCells().add("");
            } 
            if (_elementList[i].EndDate.Year > 1880)
            {
                row.getCells().Add(_elementList[i].EndDate.ToShortDateString());
            }
            else
            {
                row.getCells().add("");
            } 
            //if(ElementList[i].OrderBy) {
            //  row.Cells.Add("X");
            //}
            //else {
            //  row.Cells.Add("");
            //}
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void addElement(EhrRestrictionType restriction) throws Exception {
        FormPatListElementEditEHR2014 FormPLEE = new FormPatListElementEditEHR2014();
        FormPLEE.Element = new EhrPatListElement2014();
        FormPLEE.Element.Restriction = restriction;
        FormPLEE.IsNew = true;
        FormPLEE.ShowDialog();
        if (FormPLEE.DialogResult == DialogResult.OK)
        {
            _elementList.Add(FormPLEE.Element);
        }
         
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int index = gridMain.getSelectedIndex();
        if (index == -1)
        {
            MessageBox.Show("Please select a data element first.");
            return ;
        }
         
        FormPatListElementEditEHR2014 FormPLEE = new FormPatListElementEditEHR2014();
        FormPLEE.Element = _elementList[index];
        FormPLEE.ShowDialog();
        if (FormPLEE.DialogResult == DialogResult.Cancel && FormPLEE.Delete)
        {
            _elementList.Remove(_elementList[index]);
        }
         
        fillGrid();
    }

    private void butResults_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lans.g(this,"Please add a data element."));
            return ;
        }
         
        //bool hasOrder=false;
        //for(int i=0;i<ElementList.Count;i++) {
        //  if(hasOrder && ElementList[i].OrderBy) {
        //    MessageBox.Show(Lans.g(this,"You can only 'Order By' exactly one data element."));
        //    return;
        //  }
        //  if(ElementList[i].OrderBy) {
        //    hasOrder=true;
        //  }
        //}
        FormPatListResultsEHR2014 FormPLR14 = new FormPatListResultsEHR2014(_elementList);
        FormPLR14.ShowDialog();
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        _elementList.Clear();
        fillGrid();
    }

    private void butBirthdate_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.Birthdate);
    }

    private void butDisease_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.Problem);
    }

    private void butMedication_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.Medication);
    }

    private void butLabResult_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.LabResult);
    }

    private void butGender_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.Gender);
    }

    private void butAllergy_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.Allergy);
    }

    private void butCommPref_Click(Object sender, EventArgs e) throws Exception {
        addElement(EhrRestrictionType.CommPref);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.butShow = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.butGender = new System.Windows.Forms.Button();
        this.butLabResult = new System.Windows.Forms.Button();
        this.butMedication = new System.Windows.Forms.Button();
        this.butDisease = new System.Windows.Forms.Button();
        this.butBirthdate = new System.Windows.Forms.Button();
        this.butAllergy = new System.Windows.Forms.Button();
        this.butCommPref = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClear = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butShow
        //
        this.butShow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butShow.Location = new System.Drawing.Point(660, 445);
        this.butShow.Name = "butShow";
        this.butShow.Size = new System.Drawing.Size(75, 23);
        this.butShow.TabIndex = 15;
        this.butShow.Text = "Results";
        this.butShow.UseVisualStyleBackColor = true;
        this.butShow.Click += new System.EventHandler(this.butResults_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(660, 495);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 17;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butGender
        //
        this.butGender.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butGender.Location = new System.Drawing.Point(660, 254);
        this.butGender.Name = "butGender";
        this.butGender.Size = new System.Drawing.Size(75, 23);
        this.butGender.TabIndex = 18;
        this.butGender.Text = "Gender";
        this.butGender.UseVisualStyleBackColor = true;
        this.butGender.Click += new System.EventHandler(this.butGender_Click);
        //
        // butLabResult
        //
        this.butLabResult.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabResult.Location = new System.Drawing.Point(660, 221);
        this.butLabResult.Name = "butLabResult";
        this.butLabResult.Size = new System.Drawing.Size(75, 23);
        this.butLabResult.TabIndex = 19;
        this.butLabResult.Text = "Lab Result";
        this.butLabResult.UseVisualStyleBackColor = true;
        this.butLabResult.Click += new System.EventHandler(this.butLabResult_Click);
        //
        // butMedication
        //
        this.butMedication.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butMedication.Location = new System.Drawing.Point(660, 188);
        this.butMedication.Name = "butMedication";
        this.butMedication.Size = new System.Drawing.Size(75, 23);
        this.butMedication.TabIndex = 20;
        this.butMedication.Text = "Medication";
        this.butMedication.UseVisualStyleBackColor = true;
        this.butMedication.Click += new System.EventHandler(this.butMedication_Click);
        //
        // butDisease
        //
        this.butDisease.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDisease.Location = new System.Drawing.Point(660, 155);
        this.butDisease.Name = "butDisease";
        this.butDisease.Size = new System.Drawing.Size(75, 23);
        this.butDisease.TabIndex = 21;
        this.butDisease.Text = "Problem";
        this.butDisease.UseVisualStyleBackColor = true;
        this.butDisease.Click += new System.EventHandler(this.butDisease_Click);
        //
        // butBirthdate
        //
        this.butBirthdate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butBirthdate.Location = new System.Drawing.Point(660, 122);
        this.butBirthdate.Name = "butBirthdate";
        this.butBirthdate.Size = new System.Drawing.Size(75, 23);
        this.butBirthdate.TabIndex = 22;
        this.butBirthdate.Text = "Birthdate";
        this.butBirthdate.UseVisualStyleBackColor = true;
        this.butBirthdate.Click += new System.EventHandler(this.butBirthdate_Click);
        //
        // butAllergy
        //
        this.butAllergy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAllergy.Location = new System.Drawing.Point(660, 318);
        this.butAllergy.Name = "butAllergy";
        this.butAllergy.Size = new System.Drawing.Size(75, 23);
        this.butAllergy.TabIndex = 23;
        this.butAllergy.Text = "Allergy";
        this.butAllergy.UseVisualStyleBackColor = true;
        this.butAllergy.Click += new System.EventHandler(this.butAllergy_Click);
        //
        // butCommPref
        //
        this.butCommPref.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCommPref.Location = new System.Drawing.Point(660, 286);
        this.butCommPref.Name = "butCommPref";
        this.butCommPref.Size = new System.Drawing.Size(75, 23);
        this.butCommPref.TabIndex = 24;
        this.butCommPref.Text = "Comm Pref";
        this.butCommPref.UseVisualStyleBackColor = true;
        this.butCommPref.Click += new System.EventHandler(this.butCommPref_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(2, 2);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(642, 527);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Data Elements");
        this.gridMain.setTranslationName("FormPatientList");
        this.gridMain.setWrapText(false);
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
        // butClear
        //
        this.butClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClear.Location = new System.Drawing.Point(660, 12);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(75, 23);
        this.butClear.TabIndex = 25;
        this.butClear.Text = "Clear";
        this.butClear.UseVisualStyleBackColor = true;
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // FormPatListEHR2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(747, 530);
        this.Controls.Add(this.butClear);
        this.Controls.Add(this.butCommPref);
        this.Controls.Add(this.butAllergy);
        this.Controls.Add(this.butBirthdate);
        this.Controls.Add(this.butDisease);
        this.Controls.Add(this.butMedication);
        this.Controls.Add(this.butLabResult);
        this.Controls.Add(this.butGender);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butShow);
        this.Controls.Add(this.gridMain);
        this.Name = "FormPatListEHR2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient List";
        this.Load += new System.EventHandler(this.FormPatListEHR2014_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butShow = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGender = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butLabResult = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butMedication = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDisease = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butBirthdate = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAllergy = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCommPref = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClear = new System.Windows.Forms.Button();
}


