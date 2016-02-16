//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:34 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormProcSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Tooth;

/**
* 
*/
public class FormProcSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private long PatNum = new long();
    private List<Procedure> ProcList = new List<Procedure>();
    private OpenDental.UI.ODGrid gridMain;
    /**
    * If form closes with OK, this contains selected proc num.
    */
    public long SelectedProcNum = new long();
    /**
    * When this is set to true, it shows a different list of procs.  It shows all completed procs for the family that are not already attached to a provkey.
    */
    public boolean IsForProvKeys = new boolean();
    /**
    * This form only displays completed procedures to pick from.
    */
    public FormProcSelect(long patNum) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        PatNum = patNum;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcSelect.class);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(40, 4);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(582, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Select a procedure from the list";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(40, 34);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(559, 505);
        this.gridMain.TabIndex = 140;
        this.gridMain.setTitle("Procedures");
        this.gridMain.setTranslationName("TableProcSelect");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(637, 472);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(637, 513);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormProcSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(764, 564);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcSelect";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Procedure";
        this.Load += new System.EventHandler(this.FormProcSelect_Load);
        this.ResumeLayout(false);
    }

    private void formProcSelect_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsForProvKeys)
        {
            ProcList = Procedures.getForProvKey(PatNum);
        }
        else
        {
            List<Procedure> entireList = Procedures.refresh(PatNum);
            ProcList = new List<Procedure>();
            for (int i = 0;i < entireList.Count;i++)
            {
                if (entireList[i].ProcStatus == ProcStat.C)
                {
                    ProcList.Add(entireList[i]);
                }
                 
            }
        } 
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProcSelect","Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Prov"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Code"),55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Tooth"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Description"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcSelect","Fee"), 60, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ProcList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ProcList[i].ProcDate.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(ProcList[i].ProvNum));
            row.getCells().Add(ProcedureCodes.GetStringProcCode(ProcList[i].CodeNum));
            row.getCells().Add(Tooth.ToInternat(ProcList[i].ToothNum));
            String descript = "";
            if (ProcList[i].PatNum != PatNum)
            {
                //when IsForProvKeys
                Patient pat = Patients.GetLim(ProcList[i].PatNum);
                descript += pat.getNameLF() + " - ";
            }
             
            descript += ProcedureCodes.GetProcCode(ProcList[i].CodeNum).Descript;
            row.getCells().add(descript);
            row.getCells().Add(ProcList[i].ProcFee.ToString("F"));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedProcNum = ProcList[e.getRow()].ProcNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedProcNum = ProcList[gridMain.getSelectedIndex()].ProcNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


