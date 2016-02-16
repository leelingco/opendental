//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:12 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormHL7DefEdit;
import OpenDental.FormHL7Defs;
import OpenDental.FormHL7Msgs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefMessages;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.ModeTxHL7;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormHL7Defs  extends System.Windows.Forms.Form 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.ODGrid grid2;
    private OpenDental.UI.ODGrid grid1;
    List<HL7Def> ListInternal = new List<HL7Def>();
    private OpenDental.UI.Button butDuplicate;
    private OpenDental.UI.Button butHistory;
    List<HL7Def> ListCustom = new List<HL7Def>();
    /**
    * 
    */
    public FormHL7Defs() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHL7Defs.class);
        this.grid1 = new OpenDental.UI.ODGrid();
        this.grid2 = new OpenDental.UI.ODGrid();
        this.butDuplicate = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butHistory = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // grid1
        //
        this.grid1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.grid1.setHScrollVisible(false);
        this.grid1.Location = new System.Drawing.Point(12, 38);
        this.grid1.Name = "grid1";
        this.grid1.setScrollValue(0);
        this.grid1.Size = new System.Drawing.Size(445, 559);
        this.grid1.TabIndex = 14;
        this.grid1.setTitle("Internal");
        this.grid1.setTranslationName(null);
        this.grid1.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid1.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid1_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // grid2
        //
        this.grid2.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.grid2.setHScrollVisible(false);
        this.grid2.Location = new System.Drawing.Point(465, 38);
        this.grid2.Name = "grid2";
        this.grid2.setScrollValue(0);
        this.grid2.Size = new System.Drawing.Size(445, 559);
        this.grid2.TabIndex = 12;
        this.grid2.setTitle("Custom");
        this.grid2.setTranslationName(null);
        this.grid2.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid2.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid2_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butDuplicate
        //
        this.butDuplicate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDuplicate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDuplicate.setAutosize(true);
        this.butDuplicate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDuplicate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDuplicate.setCornerRadius(4F);
        this.butDuplicate.Image = Resources.getAdd();
        this.butDuplicate.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDuplicate.Location = new System.Drawing.Point(737, 607);
        this.butDuplicate.Name = "butDuplicate";
        this.butDuplicate.Size = new System.Drawing.Size(89, 24);
        this.butDuplicate.TabIndex = 20;
        this.butDuplicate.Text = "Duplicate";
        this.butDuplicate.Click += new System.EventHandler(this.butDuplicate_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Image = Resources.getRight();
        this.butCopy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopy.Location = new System.Drawing.Point(333, 607);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(75, 24);
        this.butCopy.TabIndex = 15;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(836, 607);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butHistory
        //
        this.butHistory.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHistory.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.butHistory.setAutosize(true);
        this.butHistory.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHistory.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHistory.setCornerRadius(4F);
        this.butHistory.Location = new System.Drawing.Point(12, 8);
        this.butHistory.Name = "butHistory";
        this.butHistory.Size = new System.Drawing.Size(75, 24);
        this.butHistory.TabIndex = 21;
        this.butHistory.Text = "History";
        this.butHistory.Click += new System.EventHandler(this.butHistory_Click);
        //
        // FormHL7Defs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(923, 641);
        this.Controls.Add(this.butHistory);
        this.Controls.Add(this.butDuplicate);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.grid1);
        this.Controls.Add(this.grid2);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormHL7Defs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HL7 Defs";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormHL7Defs_FormClosing);
        this.Load += new System.EventHandler(this.FormHL7Defs_Load);
        this.ResumeLayout(false);
    }

    private void formHL7Defs_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup,true))
        {
            butCopy.Enabled = false;
            grid2.Enabled = false;
            grid1.Enabled = false;
        }
         
        fillGrid1();
        fillGrid2();
    }

    private void fillGrid1() throws Exception {
        //Our strategy in this window and all sub windows is to get all data directly from the database (or internal).
        ListInternal = HL7Defs.getDeepInternalList();
        grid1.beginUpdate();
        grid1.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Description"),100);
        grid1.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Mode Tx"),55);
        grid1.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"In Folder / Port"),110);
        grid1.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Out Folder / Port"),110);
        grid1.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Enabled"),10);
        grid1.getColumns().add(col);
        grid1.getRows().Clear();
        for (int i = 0;i < ListInternal.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListInternal[i].Description);
            row.getCells().Add(Lan.g("enumModeTxHL7", ListInternal[i].ModeTx.ToString()));
            if (ListInternal[i].ModeTx == ModeTxHL7.File)
            {
                row.getCells().Add(ListInternal[i].IncomingFolder);
                row.getCells().Add(ListInternal[i].OutgoingFolder);
            }
            else
            {
                //TcpIp mode
                row.getCells().Add(ListInternal[i].IncomingPort);
                row.getCells().Add(ListInternal[i].OutgoingIpPort);
            } 
            row.getCells().add(ListInternal[i].IsEnabled ? "X" : "");
            grid1.getRows().add(row);
        }
        grid1.endUpdate();
    }

    private void fillGrid2() throws Exception {
        //Our strategy in this window and all sub windows is to get all data directly from the database.
        //If it's too slow in this window due to the 20-30 database calls per row in grid2, then we might later optimize to pull from the cache.
        ListCustom = HL7Defs.getDeepCustomList();
        grid2.beginUpdate();
        grid2.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Description"),100);
        grid2.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Mode Tx"),55);
        grid2.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"In Folder / Port"),110);
        grid2.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Out Folder / Port"),110);
        grid2.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Enabled"),10);
        grid2.getColumns().add(col);
        grid2.getRows().Clear();
        for (int i = 0;i < ListCustom.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListCustom[i].Description);
            row.getCells().Add(Lan.g("enumModeTxHL7", ListCustom[i].ModeTx.ToString()));
            if (ListCustom[i].ModeTx == ModeTxHL7.File)
            {
                row.getCells().Add(ListCustom[i].IncomingFolder);
                row.getCells().Add(ListCustom[i].OutgoingFolder);
            }
            else
            {
                //TcpIp mode
                row.getCells().Add(ListCustom[i].IncomingPort);
                row.getCells().Add(ListCustom[i].OutgoingIpPort);
            } 
            row.getCells().add(ListCustom[i].IsEnabled ? "X" : "");
            grid2.getRows().add(row);
        }
        grid2.endUpdate();
    }

    private void butDuplicate_Click(Object sender, System.EventArgs e) throws Exception {
        if (grid2.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a Custom HL7Def from the list on the right first.");
            return ;
        }
         
        HL7Def hl7def = ListCustom[grid2.getSelectedIndex()].Clone();
        hl7def.IsEnabled = false;
        long hl7DefNum = HL7Defs.insert(hl7def);
        for (int m = 0;m < hl7def.hl7DefMessages.Count;m++)
        {
            hl7def.hl7DefMessages[m].HL7DefNum = hl7DefNum;
            long hl7DefMessageNum = HL7DefMessages.Insert(hl7def.hl7DefMessages[m]);
            for (int s = 0;s < hl7def.hl7DefMessages[m].hl7DefSegments.Count;s++)
            {
                hl7def.hl7DefMessages[m].hl7DefSegments[s].HL7DefMessageNum = hl7DefMessageNum;
                long hl7DefSegmentNum = HL7DefSegments.Insert(hl7def.hl7DefMessages[m].hl7DefSegments[s]);
                for (int f = 0;f < hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields.Count;f++)
                {
                    hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields[f].HL7DefSegmentNum = hl7DefSegmentNum;
                    HL7DefFields.Insert(hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields[f]);
                }
            }
        }
        DataValid.setInvalid(InvalidType.HL7Defs);
        fillGrid2();
        grid2.setSelected(false);
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        if (grid1.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an internal HL7Def from the list on the left first.");
            return ;
        }
         
        HL7Def hl7def = ListInternal[grid1.getSelectedIndex()].Clone();
        hl7def.IsInternal = false;
        hl7def.IsEnabled = false;
        long hl7DefNum = HL7Defs.insert(hl7def);
        for (int m = 0;m < hl7def.hl7DefMessages.Count;m++)
        {
            hl7def.hl7DefMessages[m].HL7DefNum = hl7DefNum;
            long hl7DefMessageNum = HL7DefMessages.Insert(hl7def.hl7DefMessages[m]);
            for (int s = 0;s < hl7def.hl7DefMessages[m].hl7DefSegments.Count;s++)
            {
                hl7def.hl7DefMessages[m].hl7DefSegments[s].HL7DefMessageNum = hl7DefMessageNum;
                long hl7DefSegmentNum = HL7DefSegments.Insert(hl7def.hl7DefMessages[m].hl7DefSegments[s]);
                for (int f = 0;f < hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields.Count;f++)
                {
                    hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields[f].HL7DefSegmentNum = hl7DefSegmentNum;
                    HL7DefFields.Insert(hl7def.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields[f]);
                }
            }
        }
        DataValid.setInvalid(InvalidType.HL7Defs);
        fillGrid2();
        grid1.setSelected(false);
    }

    private void grid1_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7DefEdit FormS = new FormHL7DefEdit();
        FormS.HL7DefCur = ListInternal[e.getRow()];
        FormS.ShowDialog();
        fillGrid1();
        fillGrid2();
    }

    private void grid2_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7DefEdit FormS = new FormHL7DefEdit();
        FormS.HL7DefCur = ListCustom[e.getRow()];
        FormS.ShowDialog();
        fillGrid1();
        fillGrid2();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formHL7Defs_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        DataValid.setInvalid(InvalidType.HL7Defs);
        DataValid.setInvalid(InvalidType.Prefs);
    }

    private void butHistory_Click(Object sender, EventArgs e) throws Exception {
        FormHL7Msgs FormS = new FormHL7Msgs();
        FormS.ShowDialog();
        fillGrid1();
        fillGrid2();
    }

}


