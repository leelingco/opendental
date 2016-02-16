//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormInfobutton;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSIntervention;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormCDSIntervention;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCDSIntervention  extends Form 
{

    /**
    * This should be set to the result from EhrTriggers.TriggerMatch.
    */
    public List<CDSIntervention> ListCDSI = new List<CDSIntervention>();
    /**
    * //This should be set to the result from EhrTriggers.TriggerMatch.  Key is a string that contains the message to be displayed.
    * //The value is a list of objects to be passed to form infobutton.
    */
    //public Dictionary<string,List<object>> DictEhrTriggerResults;
    /**
    * Used for assembling the Interventions, values set using ShowIfRequired().
    */
    private DataTable _table = new DataTable();
    public FormCDSIntervention() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formCDSIntervention_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
        {
            col = new ODGridColumn("",18);
            //infobutton
            col.setImageList(imageListInfoButton);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn("Conditions",300);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Instructions",400);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Bibliography",120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < _table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
            {
                row.getCells().Add(_table.Rows[i][0].ToString());
            }
             
            //infobutton
            row.getCells().Add(_table.Rows[i][1].ToString());
            //Trigger Text
            row.getCells().Add(_table.Rows[i][2].ToString());
            //TriggerInstructions
            row.getCells().Add(_table.Rows[i][3].ToString());
            //Bibliography
            row.setTag((List<Object>)_table.Rows[i][4]);
            //List of objects to be sent to FormInfobutton;
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
        {
            return ;
        }
         
        if (e.getCol() != 0)
        {
            return ;
        }
         
        //not infobutton column
        FormInfobutton FormIB = new FormInfobutton();
        FormIB.ListObjects = (List<Object>)gridMain.getRows().get___idx(e.getRow()).getTag();
        FormIB.ShowDialog();
    }

    public void showIfRequired() throws Exception {
        showIfRequired(true);
    }

    /**
    * Run after assigning value to DictEhrTriggerResults.  FormCDSIntervention will display if needed, otherwise Dialogresult will be null.
    */
    public void showIfRequired(boolean showCancelButton) throws Exception {
        if (ListCDSI == null || ListCDSI.Count == 0)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //No interventions matched.
        _table = new DataTable();
        _table.Columns.Add("");
        //infobutton
        _table.Columns.Add("");
        //Conditions = Description and match conditions
        _table.Columns.Add("");
        //Instructions
        _table.Columns.Add("");
        //Bibliographic information
        _table.Columns.Add("", List<Object>.class);
        for (Object __dummyForeachVar0 : ListCDSI)
        {
            //Used to store the list of matched objects to later be passed to formInfobutton.
            CDSIntervention cdsi = (CDSIntervention)__dummyForeachVar0;
            DataRow row = _table.NewRow();
            row[0] = "0";
            row[1] = cdsi.InterventionMessage;
            row[2] = cdsi.EhrTrigger.Instructions;
            row[3] = cdsi.EhrTrigger.Bibliography;
            row[4] = cdsi.TriggerObjects;
            _table.Rows.Add(row);
        }
        if (_table.Rows.Count == 0)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //should never happen
        butCancel.Visible = showCancelButton;
        this.ShowDialog();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Abort;
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCDSIntervention.class);
        this.imageListInfoButton = new System.Windows.Forms.ImageList(this.components);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // imageListInfoButton
        //
        this.imageListInfoButton.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListInfoButton.ImageStream")));
        this.imageListInfoButton.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListInfoButton.Images.SetKeyName(0, "iButton_16px.png");
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(899, 233);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("CDS Interventions");
        this.gridMain.setTranslationName("");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(703, 251);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(784, 251);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(127, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel Current Action";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormCDSIntervention
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(923, 287);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(100, 100);
        this.Name = "FormCDSIntervention";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "CDS Intervention";
        this.Load += new System.EventHandler(this.FormCDSIntervention_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.ImageList imageListInfoButton = new System.Windows.Forms.ImageList();
}


