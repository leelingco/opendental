//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import OpenDental.FormAuditOneType;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLog;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Userods;

/**
* This form shows all of the security log entries for one fKey item. So far this only applies to a single appointment or a single procedure code.
*/
public class FormAuditOneType  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid grid;
    private long PatNum = new long();
    private Label labelDisclaimer = new Label();
    private List<Permissions> PermTypes = new List<Permissions>();
    private long FKey = new long();
    /**
    * Supply the patient, types, and title.
    */
    public FormAuditOneType(long patNum, List<Permissions> permTypes, String title, long fKey) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        Text = title;
        PatNum = patNum;
        PermTypes = new List<Permissions>(permTypes);
        FKey = fKey;
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAuditOneType.class);
        this.grid = new OpenDental.UI.ODGrid();
        this.labelDisclaimer = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // grid
        //
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(8, 21);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.grid.Size = new System.Drawing.Size(888, 602);
        this.grid.TabIndex = 2;
        this.grid.setTitle("Audit Trail");
        this.grid.setTranslationName("TableAudit");
        //
        // labelDisclaimer
        //
        this.labelDisclaimer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDisclaimer.ForeColor = System.Drawing.Color.Firebrick;
        this.labelDisclaimer.Location = new System.Drawing.Point(8, 3);
        this.labelDisclaimer.Name = "labelDisclaimer";
        this.labelDisclaimer.Size = new System.Drawing.Size(780, 15);
        this.labelDisclaimer.TabIndex = 3;
        this.labelDisclaimer.Text = "Changes made to this appointment before the update to 12.3 will not be reflected " + "below, but can be found in the regular audit trail.";
        this.labelDisclaimer.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormAuditOneType
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(905, 634);
        this.Controls.Add(this.labelDisclaimer);
        this.Controls.Add(this.grid);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAuditOneType";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Audit Trail";
        this.Load += new System.EventHandler(this.FormAuditOneType_Load);
        this.ResumeLayout(false);
    }

    private void formAuditOneType_Load(Object sender, System.EventArgs e) throws Exception {
        //Default is "Changes made to this appointment before the update to 12.3 will not be reflected below, but can be found in the regular audit trail."
        if (PermTypes.Contains(Permissions.ProcFeeEdit))
        {
            labelDisclaimer.Text = Lan.g(this,"Changes made to this procedure fee before the update to 13.2 were not tracked in the audit trail.");
        }
        else if (PermTypes.Contains(Permissions.InsPlanChangeCarrierName))
        {
            labelDisclaimer.Text = Lan.g(this,"Changes made to the carrier for this ins plan before the update to 13.2 were not tracked in the audit trail.");
        }
          
        fillGrid();
    }

    private void fillGrid() throws Exception {
        SecurityLog[] logList = SecurityLogs.refresh(PatNum,PermTypes,FKey);
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TableAudit","Date Time"),120);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","User"),70);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Permission"),110);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Log Text"),569);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < logList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(logList[i].LogDateTime.ToShortDateString() + " " + logList[i].LogDateTime.ToShortTimeString());
            row.getCells().Add(Userods.GetUser(logList[i].UserNum).UserName);
            row.getCells().Add(logList[i].PermType.ToString());
            row.getCells().Add(logList[i].LogText);
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.scrollToEnd();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        this.Close();
    }

}


