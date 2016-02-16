//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:22 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormNotePick;
import OpenDental.Lan;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;

/**
* Summary description for FormBasicTemplate.
*/
public class FormNotePick  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    private TextBox textNote = new TextBox();
    private Label label2 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    private String[] Notes = new String[]();
    private OpenDental.UI.Button butCancel;
    /**
    * Upon closing with OK, this will be the final note to save.
    */
    public String SelectedNote = new String();
    public boolean UseTrojanImportDescription = new boolean();
    /**
    * 
    */
    public FormNotePick(String[] notes) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        Notes = new String[notes.Length];
        notes.CopyTo(Notes, 0);
        if (UseTrojanImportDescription)
        {
            label1.Text = Lan.g(this,"Multiple versions of the note exist.  Please pick or edit one version to retain. You can also pick multiple rows to combine notes.");
            label2.Text = Lan.g(this,"This is the final note that will be used.");
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormNotePick.class);
        this.butOK = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(473, 623);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(29, 40);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(627, 322);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Pick Note");
        this.gridMain.setTranslationName("TableNotePick");
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(29, 2);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(627, 35);
        this.label1.TabIndex = 3;
        this.label1.Text = "Multiple versions of the note exist.  Please pick or edit one version to retain. " + " This note will apply to ALL similar plans.  You can also pick multiple rows to " + "combine notes.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textNote
        //
        this.textNote.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.textNote.Location = new System.Drawing.Point(29, 396);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(627, 219);
        this.textNote.TabIndex = 4;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(29, 365);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(627, 28);
        this.label2.TabIndex = 5;
        this.label2.Text = "This is the final note that will be saved for all similar plans.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(581, 623);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormNotePick
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(692, 656);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormNotePick";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormNotePick_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formNotePick_Load(Object sender, EventArgs e) throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",630);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Notes.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Notes[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        if (Notes.Length > 0)
        {
            gridMain.setSelected(0,true);
            textNote.Text = Notes[0];
        }
         
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        textNote.Text = "";
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            textNote.Text += Notes[gridMain.getSelectedIndices()[i]];
        }
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedNote = Notes[e.getRow()];
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        SelectedNote = textNote.Text;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


