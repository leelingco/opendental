//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormPopupEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Popup;
import OpenDentBusiness.Popups;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPopupAudit  extends Form 
{

    public Popup PopupCur;
    public Patient PatCur;
    /**
    * All archived popups for the current popup
    */
    private List<Popup> ListPopupAud = new List<Popup>();
    public FormPopupAudit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPopupAudit_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        //The grid cannot be changed to be sortable. This audit relies on the oldest popup changes being located at the top
        ListPopupAud = Popups.getArchivesForPopup(PopupCur.PopupNum);
        gridPopupAudit.beginUpdate();
        gridPopupAudit.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePopupsForFamily","Create Date"),140);
        gridPopupAudit.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Edit Date"),140);
        gridPopupAudit.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Level"),80);
        gridPopupAudit.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Disabled"), 60, HorizontalAlignment.Center);
        gridPopupAudit.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Popup Message"),100);
        gridPopupAudit.getColumns().add(col);
        gridPopupAudit.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListPopupAud.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (PopupCur.DateTimeEntry.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PopupCur.DateTimeEntry.ToString());
            } 
            if (i == 0)
            {
                row.getCells().add("");
            }
            else
            {
                //Very first pop up entry.  No edit date.
                row.getCells().Add(ListPopupAud[i - 1].DateTimeEntry.ToString());
            } 
            //Gets the previous DateTimeEntry to show as the last edit date.
            row.getCells().Add(Lan.g("enumEnumPopupLevel", ListPopupAud[i].PopupLevel.ToString()));
            row.getCells().add(ListPopupAud[i].IsDisabled ? "X" : "");
            row.getCells().Add(ListPopupAud[i].Description);
            gridPopupAudit.getRows().add(row);
        }
        gridPopupAudit.endUpdate();
    }

    private void gridPopupAudit_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormPopupEdit FormPE = new FormPopupEdit();
        FormPE.PopupAudit = PopupCur;
        DateTime dateLastEdit = DateTime.MinValue;
        if (e.getRow() > 0 && ListPopupAud[e.getRow() - 1].DateTimeEntry.Year > 1880)
        {
            FormPE.DateLastEdit = ListPopupAud[e.getRow() - 1].DateTimeEntry;
        }
         
        FormPE.PopupCur = ListPopupAud[e.getRow()];
        FormPE.ShowDialog();
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
        this.butClose = new OpenDental.UI.Button();
        this.gridPopupAudit = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(620, 246);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridPopupAudit
        //
        this.gridPopupAudit.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridPopupAudit.setHScrollVisible(false);
        this.gridPopupAudit.Location = new System.Drawing.Point(12, 15);
        this.gridPopupAudit.Name = "gridPopupAudit";
        this.gridPopupAudit.setScrollValue(0);
        this.gridPopupAudit.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridPopupAudit.Size = new System.Drawing.Size(595, 255);
        this.gridPopupAudit.TabIndex = 4;
        this.gridPopupAudit.setTitle("Audit Trail");
        this.gridPopupAudit.setTranslationName("TableAudit");
        this.gridPopupAudit.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPopupAudit.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPopupAudit_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormPopupAudit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(720, 297);
        this.Controls.Add(this.gridPopupAudit);
        this.Controls.Add(this.butClose);
        this.Name = "FormPopupAudit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Popup Audit";
        this.Load += new System.EventHandler(this.FormPopupAudit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridPopupAudit;
}


