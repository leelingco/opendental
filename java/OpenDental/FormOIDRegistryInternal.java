//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.InputBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.OIDInternal;
import OpenDentBusiness.OIDInternals;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormOIDRegistryInternal  extends Form 
{

    private List<OIDInternal> ListOIDInternal = new List<OIDInternal>();
    /**
    * Used for refence to construct reccomended values.
    */
    public String rootOIDString = new String();
    public FormOIDRegistryInternal() throws Exception {
        initializeComponent();
    }

    private void formReminders_Load(Object sender, EventArgs e) throws Exception {
        //OIDInternals.InsertMissingValues();
        ListOIDInternal = OIDInternals.getAll();
        ListOIDInternal.Sort(sortOIDsByIDType);
        rootOIDString = OIDInternals.getForType(IdentifierType.Root).IDRoot;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Type",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Reccomended Value",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Actual Value",200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListOIDInternal.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListOIDInternal[i].IDType.ToString());
            IDType __dummyScrutVar0 = ListOIDInternal[i].IDType;
            if (__dummyScrutVar0.equals(IdentifierType.Root))
            {
                row.getCells().add("See Manual");
            }
            else //TODO:maybe enhance this.
            if (__dummyScrutVar0.equals(IdentifierType.LabOrder))
            {
                row.getCells().add(rootOIDString + ".1");
            }
            else if (__dummyScrutVar0.equals(IdentifierType.Patient))
            {
                row.getCells().add(rootOIDString + ".2");
            }
            else if (__dummyScrutVar0.equals(IdentifierType.Provider))
            {
                row.getCells().add(rootOIDString + ".3");
            }
            else if (__dummyScrutVar0.equals(IdentifierType.CqmItem))
            {
                row.getCells().add(rootOIDString + ".4");
            }
            else
            {
                //should never happen
                row.getCells().add("");
            }     
            row.getCells().Add(ListOIDInternal[i].IDRoot);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        InputBox ipb = new InputBox("New OID");
        ipb.textResult.Text = ListOIDInternal[e.getRow()].IDRoot;
        ipb.ShowDialog();
        if (ipb.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (e.getRow() == 0)
        {
            rootOIDString = ipb.textResult.Text;
        }
         
        //if(MsgBox.Show(this,MsgBoxButtons.YesNo,"Would you like to use default values for OIDs based of of this root OID?")) {
        //	for(int i=0;i<ListOIDInternal.Count;i++) {
        //		switch(ListOIDInternal[i].IDType) {
        //			case IdentifierType.Root:
        //				break;
        //			case IdentifierType.LabOrder:
        //				row.Cells.Add(rootOIDString+".1");
        //				break;
        //			case IdentifierType.Patient:
        //				row.Cells.Add(rootOIDString+".2");
        //				break;
        //			case IdentifierType.Provider:
        //				row.Cells.Add(rootOIDString+".3");
        //				break;
        //			default://should never happen
        //				row.Cells.Add("");
        //				break;
        //		}
        //		row.Cells.Add(ListOIDInternal[i].IDRoot);
        //		gridMain.Rows.Add(row);
        //	}
        //}
        ListOIDInternal[e.getRow()].IDRoot = ipb.textResult.Text;
        fillGrid();
    }

    private int sortOIDsByIDType(OIDInternal a, OIDInternal b) throws Exception {
        if (a.IDType > b.IDType)
        {
            return 1;
        }
         
        if (a.IDType < b.IDType)
        {
            return -1;
        }
         
        return 0;
    }

    //should never happen.
    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < ListOIDInternal.Count;i++)
        {
            //TODO: Validate OIDs and provide warning s if they do not make sense. For instance, if the Actual values do not match the reccomended values.
            //Also if the other OIDs are not children of the root OID.  etc...
            OIDInternals.Update(ListOIDInternal[i]);
        }
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOk = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(535, 380);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Object Identifiers");
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
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(394, 398);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 7;
        this.butOk.Text = "Ok";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(475, 398);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 8;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormOIDRegistryInternal
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(562, 430);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormOIDRegistryInternal";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "formcds";
        this.Load += new System.EventHandler(this.FormReminders_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
}


