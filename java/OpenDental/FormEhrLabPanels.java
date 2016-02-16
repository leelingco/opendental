//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.FormEhrLabPanelEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.LabPanels;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Patient;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabPanels  extends Form 
{

    private List<LabPanel> listLP = new List<LabPanel>();
    public Patient PatCur;
    public boolean IsSelectionMode = new boolean();
    public long SelectedLabPanelNum = new long();
    public FormEhrLabPanels() throws Exception {
        initializeComponent();
    }

    private void formLabPanels_Load(Object sender, EventArgs e) throws Exception {
        if (!IsSelectionMode)
        {
            butOK.Visible = false;
            butCancel.Text = "Close";
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Date Time",135);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Service",200);
        gridMain.getColumns().add(col);
        listLP = LabPanels.refresh(PatCur.PatNum);
        List<LabResult> listResults = new List<LabResult>();
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listLP.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            listResults = LabResults.GetForPanel(listLP[i].LabPanelNum);
            if (listResults.Count == 0)
            {
                row.getCells().add(" ");
            }
            else
            {
                //to avoid a very short row
                row.getCells().Add(listResults[0].DateTimeTest.ToString());
            } 
            row.getCells().Add(listLP[i].ServiceName);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabPanelEdit FormLPE = new FormEhrLabPanelEdit();
        FormLPE.PanelCur = listLP[e.getRow()];
        FormLPE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEhrLabPanelEdit FormLPE = new FormEhrLabPanelEdit();
        FormLPE.IsNew = true;
        FormLPE.PanelCur = new LabPanel();
        FormLPE.PanelCur.PatNum = PatCur.PatNum;
        FormLPE.PanelCur.SpecimenSource = "";
        FormLPE.ShowDialog();
        fillGrid();
    }

    private void butSubmit_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show("Please select lab panels first.");
            return ;
        }
         
        List<LabPanel> panels = new List<LabPanel>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            panels.Add(listLP[gridMain.getSelectedIndices()[i]]);
        }
        OpenDentBusiness.HL7.EhrORU oru = new OpenDentBusiness.HL7.EhrORU();
        Cursor = Cursors.WaitCursor;
        try
        {
            oru.Initialize(panels);
        }
        catch (ApplicationException ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        String outputStr = oru.generateMessage();
        try
        {
            EmailMessages.sendTestUnsecure("Public Health","oru.txt",outputStr);
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MessageBox.Show("Sent");
    }

    private void butShow_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show("Please select lab panels first.");
            return ;
        }
         
        List<LabPanel> panels = new List<LabPanel>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            panels.Add(listLP[gridMain.getSelectedIndices()[i]]);
        }
        OpenDentBusiness.HL7.EhrORU oru = new OpenDentBusiness.HL7.EhrORU();
        Cursor = Cursors.WaitCursor;
        try
        {
            oru.Initialize(panels);
        }
        catch (ApplicationException ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        String outputStr = oru.generateMessage();
        Cursor = Cursors.Default;
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(outputStr);
        msgbox.ShowDialog();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not visible unless in selectionMode
        if (gridMain.getSelectedIndices().Length != 1)
        {
            MessageBox.Show("Please select exactly one lab panel.");
            return ;
        }
         
        SelectedLabPanelNum = listLP[gridMain.getSelectedIndices()[0]].LabPanelNum;
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
        this.butCancel = new System.Windows.Forms.Button();
        this.butAdd = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSubmit = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butShow = new System.Windows.Forms.Button();
        this.groupHL7 = new System.Windows.Forms.GroupBox();
        this.groupHL7.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(327, 293);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 1;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butAdd
        //
        this.butAdd.Location = new System.Drawing.Point(21, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 2;
        this.butAdd.Text = "Add Panel";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(381, 228);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Lab Panels");
        this.gridMain.setTranslationName(null);
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
        // butSubmit
        //
        this.butSubmit.Anchor = System.Windows.Forms.AnchorStyles.None;
        this.butSubmit.Location = new System.Drawing.Point(9, 17);
        this.butSubmit.Name = "butSubmit";
        this.butSubmit.Size = new System.Drawing.Size(66, 24);
        this.butSubmit.TabIndex = 3;
        this.butSubmit.Text = "Submit";
        this.butSubmit.UseVisualStyleBackColor = true;
        this.butSubmit.Click += new System.EventHandler(this.butSubmit_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(246, 293);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butShow
        //
        this.butShow.Anchor = System.Windows.Forms.AnchorStyles.None;
        this.butShow.Location = new System.Drawing.Point(81, 17);
        this.butShow.Name = "butShow";
        this.butShow.Size = new System.Drawing.Size(66, 24);
        this.butShow.TabIndex = 5;
        this.butShow.Text = "Show";
        this.butShow.UseVisualStyleBackColor = true;
        this.butShow.Click += new System.EventHandler(this.butShow_Click);
        //
        // groupHL7
        //
        this.groupHL7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupHL7.Controls.Add(this.butShow);
        this.groupHL7.Controls.Add(this.butSubmit);
        this.groupHL7.Location = new System.Drawing.Point(21, 276);
        this.groupHL7.Name = "groupHL7";
        this.groupHL7.Size = new System.Drawing.Size(156, 47);
        this.groupHL7.TabIndex = 6;
        this.groupHL7.TabStop = false;
        this.groupHL7.Text = "HL7 Msg";
        //
        // FormLabPanels
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(418, 331);
        this.Controls.Add(this.groupHL7);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormLabPanels";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Panels";
        this.Load += new System.EventHandler(this.FormLabPanels_Load);
        this.groupHL7.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSubmit = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShow = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupHL7 = new System.Windows.Forms.GroupBox();
}


