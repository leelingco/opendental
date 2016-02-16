//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermission;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.UserGroup;
import OpenDentBusiness.UserGroups;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormCDSSetup;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCDSSetup  extends Form 
{

    private List<CDSPermission> _listCdsPermissions = new List<CDSPermission>();
    private List<CDSPermission> _listCdsPermissionsOld = new List<CDSPermission>();
    public FormCDSSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formCDSSetup_Load(Object sender, EventArgs e) throws Exception {
        _listCdsPermissions = CDSPermissions.getAll();
        _listCdsPermissionsOld = CDSPermissions.getAll();
        fillGrid();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() < 2)
        {
            return ;
        }
         
        for (int i = 0;i < _listCdsPermissions.Count;i++)
        {
            //name and group name columns.
            if (_listCdsPermissions[i].CDSPermissionNum != (Long)gridMain.getRows().get___idx(e.getRow()).getTag())
            {
                continue;
            }
             
            switch(e.getCol())
            {
                case 0: 
                case 1: 
                    break;
                case 2: 
                    //should never happen.
                    _listCdsPermissions[i].ShowCDS = !_listCdsPermissions[i].ShowCDS;
                    break;
                case 3: 
                    _listCdsPermissions[i].SetupCDS = !_listCdsPermissions[i].SetupCDS;
                    break;
                case 4: 
                    _listCdsPermissions[i].ShowInfobutton = !_listCdsPermissions[i].ShowInfobutton;
                    break;
                case 5: 
                    _listCdsPermissions[i].EditBibliography = !_listCdsPermissions[i].EditBibliography;
                    break;
                case 6: 
                    _listCdsPermissions[i].ProblemCDS = !_listCdsPermissions[i].ProblemCDS;
                    break;
                case 7: 
                    _listCdsPermissions[i].MedicationCDS = !_listCdsPermissions[i].MedicationCDS;
                    break;
                case 8: 
                    _listCdsPermissions[i].AllergyCDS = !_listCdsPermissions[i].AllergyCDS;
                    break;
                case 9: 
                    _listCdsPermissions[i].DemographicCDS = !_listCdsPermissions[i].DemographicCDS;
                    break;
                case 10: 
                    _listCdsPermissions[i].LabTestCDS = !_listCdsPermissions[i].LabTestCDS;
                    break;
                case 11: 
                    _listCdsPermissions[i].VitalCDS = !_listCdsPermissions[i].VitalCDS;
                    break;
                default: 
                    break;
            
            }
            break;
        }
        //should never happen.
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("User Name",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Group Name",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Show CDS", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Show i", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Edit CDS", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Source", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Problem", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Medication", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Allergy", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Demographic", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Labs", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Vitals", 80, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        List<Userod> ListUsers = Userods.getNotHidden();
        UserGroup[] ArrayGroups = UserGroups.getList();
        for (int i = 0;i < ListUsers.Count;i++)
        {
            //if(radioUser.Checked) {//by user
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListUsers[i].UserName);
            for (int g = 0;g < ArrayGroups.Length;g++)
            {
                //group name.
                if (ListUsers[i].UserGroupNum != ArrayGroups[g].UserGroupNum)
                {
                    continue;
                }
                 
                row.getCells().Add(ArrayGroups[g].Description);
                break;
            }
            for (int p = 0;p < _listCdsPermissions.Count;p++)
            {
                if (ListUsers[i].UserNum != _listCdsPermissions[p].UserNum)
                {
                    continue;
                }
                 
                row.getCells().add((_listCdsPermissions[p].ShowCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].SetupCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].ShowInfobutton ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].EditBibliography ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].ProblemCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].MedicationCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].AllergyCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].DemographicCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].LabTestCDS ? "X" : ""));
                //"X" if user has permission
                row.getCells().add((_listCdsPermissions[p].VitalCDS ? "X" : ""));
                //"X" if user has permission
                row.setTag(_listCdsPermissions[p].CDSPermissionNum);
                break;
            }
            //used to edit correct permission.
            gridMain.getRows().add(row);
        }
        //}
        //else {//by user group
        //	for(int g=0;g<ArrayGroups.Length;g++) {
        //		row=new ODGridRow();
        //		row.Cells.Add("");//No User Name
        //		row.Cells.Add(ArrayGroups[g].Description);
        //TODO: Later. No time now for group level permission editing.
        //		gridMain.Rows.Add(row);
        //	}
        //}
        gridMain.endUpdate();
    }

    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < _listCdsPermissions.Count;i++)
        {
            //TODO:instead of updating all permissions. Update only the permissions neccesary.
            if (_listCdsPermissions[i].UserNum != _listCdsPermissionsOld[i].UserNum)
            {
                continue;
            }
             
            //should never happen, but userNums were mismatched.
            if (_listCdsPermissions[i].SetupCDS == _listCdsPermissionsOld[i].SetupCDS && _listCdsPermissions[i].ShowCDS == _listCdsPermissionsOld[i].ShowCDS && _listCdsPermissions[i].ShowInfobutton == _listCdsPermissionsOld[i].ShowInfobutton && _listCdsPermissions[i].EditBibliography == _listCdsPermissionsOld[i].EditBibliography && _listCdsPermissions[i].ProblemCDS == _listCdsPermissionsOld[i].ProblemCDS && _listCdsPermissions[i].MedicationCDS == _listCdsPermissionsOld[i].MedicationCDS && _listCdsPermissions[i].AllergyCDS == _listCdsPermissionsOld[i].AllergyCDS && _listCdsPermissions[i].DemographicCDS == _listCdsPermissionsOld[i].DemographicCDS && _listCdsPermissions[i].LabTestCDS == _listCdsPermissionsOld[i].LabTestCDS && _listCdsPermissions[i].VitalCDS == _listCdsPermissionsOld[i].VitalCDS)
            {
                continue;
            }
             
            //nothing to change.
            CDSPermissions.Update(_listCdsPermissions[i]);
            //The following line of code should never be re-ordered, only added to if needed.  Otherwise historical security logs may not be enterpreted correctly.
            String cdsLog = "CDSPermChanged,U:" + _listCdsPermissions[i].UserNum + "," + (_listCdsPermissions[i].SetupCDS ? "T" : "F") + (_listCdsPermissions[i].ShowCDS ? "T" : "F") + (_listCdsPermissions[i].ShowInfobutton ? "T" : "F") + (_listCdsPermissions[i].EditBibliography ? "T" : "F") + (_listCdsPermissions[i].ProblemCDS ? "T" : "F") + (_listCdsPermissions[i].MedicationCDS ? "T" : "F") + (_listCdsPermissions[i].AllergyCDS ? "T" : "F") + (_listCdsPermissions[i].DemographicCDS ? "T" : "F") + (_listCdsPermissions[i].LabTestCDS ? "T" : "F") + (_listCdsPermissions[i].VitalCDS ? "T" : "F");
            SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, cdsLog);
        }
        //Log entry example: CDSPermChanged,33,TTTFFFFFF
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCDSSetup.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.radioGroup = new System.Windows.Forms.RadioButton();
        this.radioUser = new System.Windows.Forms.RadioButton();
        this.butOk = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 54);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(710, 433);
        this.gridMain.TabIndex = 60;
        this.gridMain.setTitle("Users");
        this.gridMain.setTranslationName("TableSecurity");
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // radioGroup
        //
        this.radioGroup.AutoCheck = false;
        this.radioGroup.Enabled = false;
        this.radioGroup.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioGroup.Location = new System.Drawing.Point(12, 30);
        this.radioGroup.Name = "radioGroup";
        this.radioGroup.Size = new System.Drawing.Size(158, 18);
        this.radioGroup.TabIndex = 62;
        this.radioGroup.Text = "by Group";
        this.radioGroup.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        //
        // radioUser
        //
        this.radioUser.AutoCheck = false;
        this.radioUser.Enabled = false;
        this.radioUser.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioUser.Location = new System.Drawing.Point(12, 8);
        this.radioUser.Name = "radioUser";
        this.radioUser.Size = new System.Drawing.Size(91, 22);
        this.radioUser.TabIndex = 61;
        this.radioUser.Text = "by User";
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(566, 493);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 63;
        this.butOk.Text = "Save";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(647, 493);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 64;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label6.Location = new System.Drawing.Point(301, 26);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(421, 18);
        this.label6.TabIndex = 65;
        this.label6.Text = "Click the cells to grant or revoke permissions.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // FormCDSSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(734, 529);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.radioGroup);
        this.Controls.Add(this.radioUser);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormCDSSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinical Decision Support Setup";
        this.Load += new System.EventHandler(this.FormCDSSetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.RadioButton radioGroup = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioUser = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
}


