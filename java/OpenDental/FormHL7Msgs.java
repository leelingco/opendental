//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormHL7MsgEdit;
import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormHL7Msgs;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormHL7Msgs  extends Form 
{

    private long SelectedPatNum = new long();
    private List<HL7Msg> MsgList = new List<HL7Msg>();
    public FormHL7Msgs() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formHL7Msgs_Load(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-7).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        comboHL7Status.Items.Add(Lan.g(this,"All"));
        comboHL7Status.SelectedIndex = 0;
        for (int i = 0;i < Enum.GetNames(HL7MessageStatus.class).Length;i++)
        {
            comboHL7Status.Items.Add(Enum.GetName(HL7MessageStatus.class, i));
        }
        SelectedPatNum = 0;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            return ;
        }
         
        if (SelectedPatNum > 0)
        {
            Patient pat = Patients.getLim(SelectedPatNum);
            textPatient.Text = pat.getNameLF();
        }
         
        MsgList = HL7Msgs.GetHL7Msgs(PIn.Date(textDateStart.Text), PIn.Date(textDateEnd.Text), SelectedPatNum, comboHL7Status.SelectedIndex);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"DateTime"),180);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient"),170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"AptNum"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Status"),75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),400);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        if (MsgList != null)
        {
            for (int i = 0;i < MsgList.Count;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(MsgList[i].DateTStamp.ToString());
                if (MsgList[i].PatNum > 0)
                {
                    row.getCells().Add(Patients.GetLim(MsgList[i].PatNum).GetNameFL());
                }
                else
                {
                    row.getCells().add("");
                } 
                if (MsgList[i].AptNum > 0)
                {
                    row.getCells().Add(MsgList[i].AptNum.ToString());
                }
                else
                {
                    row.getCells().add("");
                } 
                row.getCells().Add(Enum.GetName(HL7MessageStatus.class, MsgList[i].HL7Status));
                row.getCells().Add(MsgList[i].Note);
                gridMain.getRows().add(row);
            }
        }
         
        gridMain.endUpdate();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butFind_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.SelectionModeOnly = true;
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SelectedPatNum = FormPS.SelectedPatNum;
        fillGrid();
    }

    private void comboHL7Status_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7MsgEdit FormS = new FormHL7MsgEdit();
        FormS.MsgCur = MsgList[e.getRow()];
        FormS.ShowDialog();
        fillGrid();
    }

    private void butAll_Click(Object sender, EventArgs e) throws Exception {
        SelectedPatNum = 0;
        textPatient.Text = "";
        fillGrid();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHL7Msgs.class);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butAll = new OpenDental.UI.Button();
        this.butFind = new OpenDental.UI.Button();
        this.textDateEnd = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.labelHL7Status = new System.Windows.Forms.Label();
        this.labelEndDate = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.labelPatient = new System.Windows.Forms.Label();
        this.comboHL7Status = new System.Windows.Forms.ComboBox();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butAll);
        this.groupBox1.Controls.Add(this.butFind);
        this.groupBox1.Controls.Add(this.textDateEnd);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.labelHL7Status);
        this.groupBox1.Controls.Add(this.labelEndDate);
        this.groupBox1.Controls.Add(this.textPatient);
        this.groupBox1.Controls.Add(this.labelPatient);
        this.groupBox1.Controls.Add(this.comboHL7Status);
        this.groupBox1.Controls.Add(this.labelStartDate);
        this.groupBox1.Controls.Add(this.butRefresh);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(21, 6);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(816, 62);
        this.groupBox1.TabIndex = 20;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "View";
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(382, 34);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(65, 24);
        this.butAll.TabIndex = 41;
        this.butAll.Text = "All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // butFind
        //
        this.butFind.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFind.setAutosize(true);
        this.butFind.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFind.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFind.setCornerRadius(4F);
        this.butFind.Location = new System.Drawing.Point(292, 34);
        this.butFind.Name = "butFind";
        this.butFind.Size = new System.Drawing.Size(65, 24);
        this.butFind.TabIndex = 40;
        this.butFind.Text = "Find";
        this.butFind.Click += new System.EventHandler(this.butFind_Click);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(112, 35);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 18;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(112, 12);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 17;
        //
        // labelHL7Status
        //
        this.labelHL7Status.Location = new System.Drawing.Point(465, 12);
        this.labelHL7Status.Name = "labelHL7Status";
        this.labelHL7Status.Size = new System.Drawing.Size(80, 18);
        this.labelHL7Status.TabIndex = 20;
        this.labelHL7Status.Text = "HL7Status";
        this.labelHL7Status.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelEndDate
        //
        this.labelEndDate.Location = new System.Drawing.Point(29, 35);
        this.labelEndDate.Name = "labelEndDate";
        this.labelEndDate.Size = new System.Drawing.Size(80, 18);
        this.labelEndDate.TabIndex = 12;
        this.labelEndDate.Text = "End Date";
        this.labelEndDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.BackColor = System.Drawing.SystemColors.Window;
        this.textPatient.Location = new System.Drawing.Point(292, 12);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(155, 20);
        this.textPatient.TabIndex = 38;
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(210, 12);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(80, 18);
        this.labelPatient.TabIndex = 22;
        this.labelPatient.Text = "Patient";
        this.labelPatient.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboHL7Status
        //
        this.comboHL7Status.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboHL7Status.Location = new System.Drawing.Point(547, 12);
        this.comboHL7Status.MaxDropDownItems = 40;
        this.comboHL7Status.Name = "comboHL7Status";
        this.comboHL7Status.Size = new System.Drawing.Size(155, 21);
        this.comboHL7Status.TabIndex = 21;
        this.comboHL7Status.SelectedIndexChanged += new System.EventHandler(this.comboHL7Status_SelectedIndexChanged);
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(29, 12);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(80, 18);
        this.labelStartDate.TabIndex = 11;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(736, 10);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 2;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 74);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(902, 543);
        this.gridMain.TabIndex = 19;
        this.gridMain.setTitle("HL7 Message Log");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(848, 623);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormHL7Msgs
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(939, 663);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormHL7Msgs";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HL7 Messages";
        this.Load += new System.EventHandler(this.FormHL7Msgs_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboHL7Status = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelHL7Status = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.ValidDate textDateStart;
    private System.Windows.Forms.Label labelEndDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelStartDate = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butFind;
    private OpenDental.UI.Button butAll;
}


