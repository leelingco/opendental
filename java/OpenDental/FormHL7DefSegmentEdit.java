//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormHL7DefFieldEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.HL7DefField;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.SegmentNameHL7;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormHL7DefSegmentEdit;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;


/**
* 
*/
public class FormHL7DefSegmentEdit  extends System.Windows.Forms.Form 
{

    public HL7DefSegment HL7DefSegCur;
    public boolean IsHL7DefInternal = new boolean();
    /**
    * 
    */
    public FormHL7DefSegmentEdit() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    private void formHL7DefSegmentEdit_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
        for (int i = 0;i < Enum.GetNames(SegmentNameHL7.class).Length;i++)
        {
            comboSegmentName.Items.Add(Lan.g("enumSegmentNameHL7", Enum.GetName(SegmentNameHL7.class, i).ToString()));
        }
        if (HL7DefSegCur != null)
        {
            comboSegmentName.SelectedIndex = ((Enum)HL7DefSegCur.SegmentName).ordinal();
            textItemOrder.Text = HL7DefSegCur.ItemOrder.ToString();
            checkCanRepeat.Checked = HL7DefSegCur.CanRepeat;
            checkIsOptional.Checked = HL7DefSegCur.IsOptional;
            textNote.Text = HL7DefSegCur.Note;
        }
         
        if (IsHL7DefInternal)
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
            labelDelete.Visible = true;
            butAdd.Enabled = false;
        }
         
    }

    private void fillGrid() throws Exception {
        if (!IsHL7DefInternal && !HL7DefSegCur.getIsNew())
        {
            HL7DefSegCur.hl7DefFields = HL7DefFields.getFromDb(HL7DefSegCur.HL7DefSegmentNum);
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Field Name"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Fixed Text"),240);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Type"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Order"), 40, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Table ID"),75);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        if (HL7DefSegCur != null && HL7DefSegCur.hl7DefFields != null)
        {
            for (int i = 0;i < HL7DefSegCur.hl7DefFields.Count;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(HL7DefSegCur.hl7DefFields[i].FieldName);
                row.getCells().Add(HL7DefSegCur.hl7DefFields[i].FixedText);
                row.getCells().Add(Lan.g("enumDataTypeHL7", HL7DefSegCur.hl7DefFields[i].DataType.ToString()));
                row.getCells().Add(HL7DefSegCur.hl7DefFields[i].OrdinalPos.ToString());
                row.getCells().Add(HL7DefSegCur.hl7DefFields[i].TableId);
                gridMain.getRows().add(row);
            }
        }
         
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7DefFieldEdit FormS = new FormHL7DefFieldEdit();
        FormS.HL7DefFieldCur = HL7DefSegCur.hl7DefFields[e.getRow()];
        FormS.IsHL7DefInternal = IsHL7DefInternal;
        FormS.ShowDialog();
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete Segment?"))
        {
            return ;
        }
         
        for (int f = 0;f < HL7DefSegCur.hl7DefFields.Count;f++)
        {
            HL7DefFields.Delete(HL7DefSegCur.hl7DefFields[f].HL7DefFieldNum);
        }
        HL7DefSegments.delete(HL7DefSegCur.HL7DefSegmentNum);
        DialogResult = DialogResult.OK;
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (HL7DefSegCur.getIsNew())
        {
            HL7DefSegments.insert(HL7DefSegCur);
            HL7DefSegCur.setIsNew(false);
        }
         
        FormHL7DefFieldEdit FormS = new FormHL7DefFieldEdit();
        FormS.HL7DefFieldCur = new HL7DefField();
        FormS.HL7DefFieldCur.HL7DefSegmentNum = HL7DefSegCur.HL7DefSegmentNum;
        FormS.HL7DefFieldCur.setIsNew(true);
        FormS.HL7DefFieldCur.FixedText = "";
        FormS.IsHL7DefInternal = false;
        FormS.ShowDialog();
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not enabled if internal
        if (!StringSupport.equals(textItemOrder.errorProvider1.GetError(textItemOrder), ""))
        {
            MsgBox.show(this,"Please fix data entry error first.");
            return ;
        }
         
        HL7DefSegCur.SegmentName = (SegmentNameHL7)comboSegmentName.SelectedIndex;
        HL7DefSegCur.ItemOrder = PIn.Int(textItemOrder.Text);
        HL7DefSegCur.CanRepeat = checkCanRepeat.Checked;
        HL7DefSegCur.IsOptional = checkIsOptional.Checked;
        HL7DefSegCur.Note = textNote.Text;
        if (HL7DefSegCur.ItemOrder == 0 && HL7DefSegCur.SegmentName == SegmentNameHL7.MSH)
        {
            for (int i = 0;i < HL7DefSegCur.hl7DefFields.Count;i++)
            {
                if (StringSupport.equals(HL7DefSegCur.hl7DefFields[i].FieldName, "messageType") && HL7DefSegCur.hl7DefFields[i].OrdinalPos != 8)
                {
                    //we force messageType to be in field 8 of the message header segment or we will not be able to retrieve a definition for this type of message when processing
                    MsgBox.show(this,"The messageType field must be in position 8 of the message header segment.");
                    return ;
                }
                 
            }
        }
         
        if (HL7DefSegCur.getIsNew())
        {
            HL7DefSegments.insert(HL7DefSegCur);
        }
        else
        {
            HL7DefSegments.update(HL7DefSegCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHL7DefSegmentEdit.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.checkCanRepeat = new System.Windows.Forms.CheckBox();
        this.checkIsOptional = new System.Windows.Forms.CheckBox();
        this.comboSegmentName = new System.Windows.Forms.ComboBox();
        this.labelItemOrder = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.labelDelete = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.textItemOrder = new OpenDental.ValidNum();
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
        this.butOK.Location = new System.Drawing.Point(477, 401);
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
        this.butCancel.Location = new System.Drawing.Point(563, 401);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 140);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(621, 255);
        this.gridMain.TabIndex = 7;
        this.gridMain.setTitle("Fields");
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
        // checkCanRepeat
        //
        this.checkCanRepeat.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.checkCanRepeat.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkCanRepeat.Location = new System.Drawing.Point(26, 70);
        this.checkCanRepeat.Name = "checkCanRepeat";
        this.checkCanRepeat.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkCanRepeat.Size = new System.Drawing.Size(137, 18);
        this.checkCanRepeat.TabIndex = 13;
        this.checkCanRepeat.TabStop = false;
        this.checkCanRepeat.Text = "Can Repeat";
        //
        // checkIsOptional
        //
        this.checkIsOptional.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.checkIsOptional.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsOptional.Location = new System.Drawing.Point(26, 90);
        this.checkIsOptional.Name = "checkIsOptional";
        this.checkIsOptional.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkIsOptional.Size = new System.Drawing.Size(137, 18);
        this.checkIsOptional.TabIndex = 14;
        this.checkIsOptional.TabStop = false;
        this.checkIsOptional.Text = "Is Optional";
        //
        // comboSegmentName
        //
        this.comboSegmentName.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.comboSegmentName.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSegmentName.Location = new System.Drawing.Point(148, 22);
        this.comboSegmentName.MaxDropDownItems = 100;
        this.comboSegmentName.Name = "comboSegmentName";
        this.comboSegmentName.Size = new System.Drawing.Size(138, 21);
        this.comboSegmentName.TabIndex = 11;
        //
        // labelItemOrder
        //
        this.labelItemOrder.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.labelItemOrder.Location = new System.Drawing.Point(23, 47);
        this.labelItemOrder.Name = "labelItemOrder";
        this.labelItemOrder.Size = new System.Drawing.Size(125, 18);
        this.labelItemOrder.TabIndex = 8;
        this.labelItemOrder.Text = "Item Order";
        this.labelItemOrder.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.textNote.Location = new System.Drawing.Point(353, 17);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(285, 109);
        this.textNote.TabIndex = 15;
        //
        // label12
        //
        this.label12.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label12.Location = new System.Drawing.Point(242, 17);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(110, 18);
        this.label12.TabIndex = 9;
        this.label12.Text = "Note";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label10.Location = new System.Drawing.Point(23, 21);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(125, 18);
        this.label10.TabIndex = 10;
        this.label10.Text = "Segment Name";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(17, 401);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(85, 24);
        this.butDelete.TabIndex = 19;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // labelDelete
        //
        this.labelDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelDelete.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDelete.Location = new System.Drawing.Point(108, 399);
        this.labelDelete.Name = "labelDelete";
        this.labelDelete.Size = new System.Drawing.Size(266, 28);
        this.labelDelete.TabIndex = 66;
        this.labelDelete.Text = "This HL7Def is internal. To edit this HL7Def you must first copy it to the Custom" + " list.";
        this.labelDelete.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelDelete.Visible = false;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(368, 401);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 67;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // textItemOrder
        //
        this.textItemOrder.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.textItemOrder.Location = new System.Drawing.Point(148, 47);
        this.textItemOrder.setMaxVal(255);
        this.textItemOrder.setMinVal(0);
        this.textItemOrder.Name = "textItemOrder";
        this.textItemOrder.Size = new System.Drawing.Size(34, 20);
        this.textItemOrder.TabIndex = 12;
        //
        // FormHL7DefSegmentEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
        this.ClientSize = new System.Drawing.Size(654, 441);
        this.Controls.Add(this.textItemOrder);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.labelDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkCanRepeat);
        this.Controls.Add(this.checkIsOptional);
        this.Controls.Add(this.comboSegmentName);
        this.Controls.Add(this.labelItemOrder);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormHL7DefSegmentEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HL7 Def Segment Edit";
        this.Load += new System.EventHandler(this.FormHL7DefSegmentEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.CheckBox checkCanRepeat = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkIsOptional = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ComboBox comboSegmentName = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelItemOrder = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelDelete = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
    private OpenDental.ValidNum textItemOrder;
}


