//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrCarePlanEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrCarePlan;
import OpenDentBusiness.EhrCarePlans;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrCarePlans;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrCarePlans  extends Form 
{

    private Patient _patCur;
    private List<EhrCarePlan> _listCarePlans = new List<EhrCarePlan>();
    public FormEhrCarePlans(Patient patCur) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patCur = patCur;
    }

    private void formEhrCarePlans_Load(Object sender, EventArgs e) throws Exception {
        fillCarePlans();
    }

    private void formEhrCarePlans_Resize(Object sender, EventArgs e) throws Exception {
        fillCarePlans();
    }

    //So the columns will resize.
    private void fillCarePlans() throws Exception {
        gridCarePlans.beginUpdate();
        gridCarePlans.getColumns().Clear();
        int colDatePixCount = 66;
        int variablePixCount = gridCarePlans.Width - 10 - colDatePixCount;
        int colGoalPixCount = variablePixCount / 2;
        int colInstructionsPixCount = variablePixCount - colGoalPixCount;
        gridCarePlans.getColumns().add(new ODGridColumn("Date",colDatePixCount));
        gridCarePlans.getColumns().add(new ODGridColumn("Goal",colGoalPixCount));
        gridCarePlans.getColumns().add(new ODGridColumn("Instructions",colInstructionsPixCount));
        gridCarePlans.endUpdate();
        gridCarePlans.beginUpdate();
        gridCarePlans.getRows().Clear();
        _listCarePlans = EhrCarePlans.refresh(_patCur.PatNum);
        for (int i = 0;i < _listCarePlans.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(_listCarePlans[i].DatePlanned.ToShortDateString());
            //Date
            Snomed snomedEducation = Snomeds.GetByCode(_listCarePlans[i].SnomedEducation);
            if (snomedEducation == null)
            {
                row.getCells().add("");
            }
            else
            {
                //We allow blank or "NullFlavor" SNOMEDCT codes when exporting CCDAs, so we allow them to be blank when displaying here as well.
                row.getCells().add(snomedEducation.Description);
            } 
            //GoalDescript
            row.getCells().Add(_listCarePlans[i].Instructions);
            //Instructions
            gridCarePlans.getRows().add(row);
        }
        gridCarePlans.endUpdate();
    }

    private void gridCarePlans_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrCarePlanEdit formEdit = new FormEhrCarePlanEdit(_listCarePlans[e.getRow()]);
        if (formEdit.ShowDialog() == DialogResult.OK)
        {
            fillCarePlans();
        }
         
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        EhrCarePlan ehrCarePlan = new EhrCarePlan();
        ehrCarePlan.setIsNew(true);
        ehrCarePlan.PatNum = _patCur.PatNum;
        ehrCarePlan.DatePlanned = DateTime.Today;
        FormEhrCarePlanEdit formEdit = new FormEhrCarePlanEdit(ehrCarePlan);
        if (formEdit.ShowDialog() == DialogResult.OK)
        {
            fillCarePlans();
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrCarePlans.class);
        this.gridCarePlans = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridCarePlans
        //
        this.gridCarePlans.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridCarePlans.setHScrollVisible(false);
        this.gridCarePlans.Location = new System.Drawing.Point(12, 12);
        this.gridCarePlans.Name = "gridCarePlans";
        this.gridCarePlans.setScrollValue(0);
        this.gridCarePlans.Size = new System.Drawing.Size(958, 653);
        this.gridCarePlans.TabIndex = 4;
        this.gridCarePlans.setTitle(null);
        this.gridCarePlans.setTranslationName(null);
        this.gridCarePlans.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridCarePlans.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridCarePlans_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(895, 671);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(12, 671);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(86, 24);
        this.butAdd.TabIndex = 72;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormEhrCarePlans
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 707);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridCarePlans);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(700, 300);
        this.Name = "FormEhrCarePlans";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Care Plans";
        this.Load += new System.EventHandler(this.FormEhrCarePlans_Load);
        this.Resize += new System.EventHandler(this.FormEhrCarePlans_Resize);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridCarePlans;
    private OpenDental.UI.Button butAdd;
}


