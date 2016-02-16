//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormAutoNoteControlEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AutoNote;
import OpenDentBusiness.AutoNoteControl;
import OpenDentBusiness.AutoNoteControls;
import OpenDentBusiness.AutoNotes;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDental.FormAutoNoteEdit;
import OpenDental.Properties.Resources;

public class FormAutoNoteEdit  extends Form 
{

    public boolean IsNew = new boolean();
    public AutoNote AutoNoteCur;
    private int textSelectionStart = new int();
    public FormAutoNoteEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.F(this);
    }

    private void formAutoNoteEdit_Load(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AutoNoteQuickNoteEdit,true))
        {
            butAdd.Enabled = false;
            butDelete.Enabled = false;
            butOK.Enabled = false;
            textMain.ReadOnly = true;
            textMain.BackColor = SystemColors.Window;
            textBoxAutoNoteName.ReadOnly = true;
            textBoxAutoNoteName.BackColor = SystemColors.Window;
        }
        else
        {
            gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
              { 
                //user has permission to edit auto notes
                public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                    gridMain_CellDoubleClick(sender, e);
                }

                public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                    List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
        } 
        textBoxAutoNoteName.Text = AutoNoteCur.AutoNoteName;
        textMain.Text = AutoNoteCur.MainText;
        fillGrid();
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        AutoNoteControls.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < AutoNoteControls.Listt.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(AutoNoteControls.Listt[i].Descript);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormAutoNoteControlEdit FormA = new FormAutoNoteControlEdit();
        FormA.ControlCur = AutoNoteControls.Listt[e.getRow()];
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAutoNoteControlEdit FormA = new FormAutoNoteControlEdit();
        AutoNoteControl control = new AutoNoteControl();
        control.ControlType = "Text";
        FormA.ControlCur = control;
        FormA.IsNew = true;
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butInsert_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a prompt first.");
            return ;
        }
         
        String fieldStr = AutoNoteControls.Listt[gridMain.getSelectedIndex()].Descript;
        if (textSelectionStart < textMain.Text.Length - 1)
        {
            textMain.Text = textMain.Text.Substring(0, textSelectionStart) + "[Prompt:\"" + fieldStr + "\"]" + textMain.Text.Substring(textSelectionStart);
        }
        else
        {
            //otherwise, just tack it on the end
            textMain.Text += "[Prompt:\"" + fieldStr + "\"]";
        } 
        textMain.Select(textSelectionStart + fieldStr.Length + 11, 0);
        textMain.Focus();
    }

    private void textMain_Leave(Object sender, EventArgs e) throws Exception {
        textSelectionStart = textMain.SelectionStart;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this autonote?"))
        {
            return ;
        }
         
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        AutoNotes.delete(AutoNoteCur.AutoNoteNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        AutoNoteCur.AutoNoteName = textBoxAutoNoteName.Text;
        AutoNoteCur.MainText = textMain.Text;
        if (IsNew)
        {
            AutoNotes.insert(AutoNoteCur);
        }
        else
        {
            AutoNotes.update(AutoNoteCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoNoteEdit.class);
        this.textBoxAutoNoteName = new System.Windows.Forms.TextBox();
        this.labelName = new System.Windows.Forms.Label();
        this.textMain = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butInsert = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textBoxAutoNoteName
        //
        this.textBoxAutoNoteName.Location = new System.Drawing.Point(107, 12);
        this.textBoxAutoNoteName.Name = "textBoxAutoNoteName";
        this.textBoxAutoNoteName.Size = new System.Drawing.Size(356, 20);
        this.textBoxAutoNoteName.TabIndex = 0;
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(19, 15);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(86, 13);
        this.labelName.TabIndex = 1;
        this.labelName.Text = "Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMain
        //
        this.textMain.AcceptsReturn = true;
        this.textMain.HideSelection = false;
        this.textMain.Location = new System.Drawing.Point(6, 59);
        this.textMain.Multiline = true;
        this.textMain.Name = "textMain";
        this.textMain.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textMain.Size = new System.Drawing.Size(504, 552);
        this.textMain.TabIndex = 108;
        this.textMain.Leave += new System.EventHandler(this.textMain_Leave);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 43);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(86, 13);
        this.label1.TabIndex = 109;
        this.label1.Text = "Text";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(603, 8);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 24);
        this.butAdd.TabIndex = 110;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(6, 620);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 107;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(603, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(242, 573);
        this.gridMain.TabIndex = 106;
        this.gridMain.setTitle("Available Prompts");
        this.gridMain.setTranslationName("FormAutoNoteEdit");
        //
        // butInsert
        //
        this.butInsert.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInsert.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butInsert.setAutosize(true);
        this.butInsert.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInsert.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInsert.setCornerRadius(4F);
        this.butInsert.Image = Resources.getLeft();
        this.butInsert.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butInsert.Location = new System.Drawing.Point(516, 240);
        this.butInsert.Name = "butInsert";
        this.butInsert.Size = new System.Drawing.Size(79, 24);
        this.butInsert.TabIndex = 105;
        this.butInsert.Text = "Insert";
        this.butInsert.Click += new System.EventHandler(this.butInsert_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(683, 620);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(78, 24);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "OK";
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
        this.butCancel.Location = new System.Drawing.Point(767, 620);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(78, 24);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAutoNoteEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(857, 656);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butInsert);
        this.Controls.Add(this.labelName);
        this.Controls.Add(this.textBoxAutoNoteName);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAutoNoteEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Auto Note Edit";
        this.Load += new System.EventHandler(this.FormAutoNoteEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textBoxAutoNoteName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelName = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butInsert;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.TextBox textMain = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
}


