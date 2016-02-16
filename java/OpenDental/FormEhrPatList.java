//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrPatListElementEdit;
import OpenDental.FormEhrPatListResults;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrPatListElement;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.Lans;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrPatList  extends Form 
{

    public List<EhrPatListElement> ElementList = new List<EhrPatListElement>();
    public FormEhrPatList() throws Exception {
        initializeComponent();
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
        col = new ODGridColumn("Order", 120, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ElementList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ElementList[i].Restriction.ToString());
            row.getCells().Add(ElementList[i].CompareString);
            if (ElementList[i].Restriction == EhrRestrictionType.Gender || ElementList[i].Restriction == EhrRestrictionType.Problem || ElementList[i].Restriction == EhrRestrictionType.Medication)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ElementList[i].Operand.ToString());
            } 
            row.getCells().Add(ElementList[i].LabValue);
            if (ElementList[i].OrderBy)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void addElement(EhrRestrictionType restriction) throws Exception {
        FormEhrPatListElementEdit FormPLEE = new FormEhrPatListElementEdit();
        FormPLEE.Element = new EhrPatListElement();
        FormPLEE.Element.Restriction = restriction;
        FormPLEE.IsNew = true;
        FormPLEE.ShowDialog();
        if (FormPLEE.DialogResult == DialogResult.OK)
        {
            ElementList.Add(FormPLEE.Element);
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
         
        FormEhrPatListElementEdit FormPLEE = new FormEhrPatListElementEdit();
        FormPLEE.Element = ElementList[index];
        FormPLEE.ShowDialog();
        if (FormPLEE.DialogResult == DialogResult.Cancel && FormPLEE.Delete)
        {
            ElementList.Remove(ElementList[index]);
        }
         
        fillGrid();
    }

    private void butResults_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getRows().Count < 1)
        {
            MessageBox.Show(Lans.g(this,"Please add a data element."));
            return ;
        }
         
        boolean hasOrder = false;
        for (int i = 0;i < ElementList.Count;i++)
        {
            if (hasOrder && ElementList[i].OrderBy)
            {
                MessageBox.Show(Lans.g(this,"You can only 'Order By' exactly one data element."));
                return ;
            }
             
            if (ElementList[i].OrderBy)
            {
                hasOrder = true;
            }
             
        }
        FormEhrPatListResults FormPLR = new FormEhrPatListResults(ElementList);
        FormPLR.ShowDialog();
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butLabResult = new System.Windows.Forms.Button();
        this.butMedication = new System.Windows.Forms.Button();
        this.butDisease = new System.Windows.Forms.Button();
        this.butBirthdate = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butShow
        //
        this.butShow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butShow.Location = new System.Drawing.Point(547, 445);
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
        this.butClose.Location = new System.Drawing.Point(547, 495);
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
        this.butGender.Location = new System.Drawing.Point(547, 254);
        this.butGender.Name = "butGender";
        this.butGender.Size = new System.Drawing.Size(75, 23);
        this.butGender.TabIndex = 18;
        this.butGender.Text = "Gender";
        this.butGender.UseVisualStyleBackColor = true;
        this.butGender.Click += new System.EventHandler(this.butGender_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(2, 2);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(529, 527);
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
        // butLabResult
        //
        this.butLabResult.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butLabResult.Location = new System.Drawing.Point(547, 221);
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
        this.butMedication.Location = new System.Drawing.Point(547, 188);
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
        this.butDisease.Location = new System.Drawing.Point(547, 155);
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
        this.butBirthdate.Location = new System.Drawing.Point(547, 122);
        this.butBirthdate.Name = "butBirthdate";
        this.butBirthdate.Size = new System.Drawing.Size(75, 23);
        this.butBirthdate.TabIndex = 22;
        this.butBirthdate.Text = "Birthdate";
        this.butBirthdate.UseVisualStyleBackColor = true;
        this.butBirthdate.Click += new System.EventHandler(this.butBirthdate_Click);
        //
        // FormPatList
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(634, 530);
        this.Controls.Add(this.butBirthdate);
        this.Controls.Add(this.butDisease);
        this.Controls.Add(this.butMedication);
        this.Controls.Add(this.butLabResult);
        this.Controls.Add(this.butGender);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butShow);
        this.Controls.Add(this.gridMain);
        this.Name = "FormPatList";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient List";
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
}


