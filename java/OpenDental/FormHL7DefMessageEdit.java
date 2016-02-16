//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormHL7DefSegmentEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7DefMessages;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormHL7DefMessageEdit;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;


/**
* 
*/
public class FormHL7DefMessageEdit  extends System.Windows.Forms.Form 
{

    public HL7DefMessage HL7DefMesCur;
    public boolean IsHL7DefInternal = new boolean();
    /**
    * 
    */
    public FormHL7DefMessageEdit() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    private void formHL7DefMessageEdit_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
        for (int i = 1;i < Enum.GetNames(MessageTypeHL7.class).Length;i++)
        {
            //Start at enum 1, 0 is NotDefined and is not displayed for user to select.  Used for unsupported message types
            comboMsgType.Items.Add(Lan.g("enumMessageTypeHL7", Enum.GetName(MessageTypeHL7.class, i).ToString()));
        }
        for (int i = 1;i < Enum.GetNames(EventTypeHL7.class).Length;i++)
        {
            //start at enum 1, 0 is NotDefined and is not displayed for user to select.  Used for unsupported event types
            comboEventType.Items.Add(Lan.g("enumEventTypeHL7", Enum.GetName(EventTypeHL7.class, i).ToString()));
        }
        if (HL7DefMesCur != null)
        {
            comboMsgType.SelectedIndex = ((Enum)HL7DefMesCur.MessageType).ordinal() - 1;
            //enum 0 is the NotDefined message type and is not in the list to select, so minus 1
            comboEventType.SelectedIndex = ((Enum)HL7DefMesCur.EventType).ordinal() - 1;
            //enum 0 is the NotDefined event type and is not in the list to select, so minus 1
            textItemOrder.Text = HL7DefMesCur.ItemOrder.ToString();
            textNote.Text = HL7DefMesCur.Note;
            if (HL7DefMesCur.InOrOut == InOutHL7.Incoming)
            {
                radioIn.Checked = true;
            }
            else
            {
                //outgoing
                radioOut.Checked = true;
            } 
        }
         
        if (IsHL7DefInternal)
        {
            butAdd.Enabled = false;
            butOK.Enabled = false;
            butDelete.Enabled = false;
            labelDelete.Visible = true;
        }
         
    }

    private void fillGrid() throws Exception {
        if (!IsHL7DefInternal && !HL7DefMesCur.getIsNew())
        {
            HL7DefMesCur.hl7DefSegments = HL7DefSegments.getDeepFromDb(HL7DefMesCur.HL7DefMessageNum);
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Seg"),35);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Order"), 40, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Can Repeat"), 73, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Is Optional"), 67, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        if (HL7DefMesCur != null && HL7DefMesCur.hl7DefSegments != null)
        {
            for (int i = 0;i < HL7DefMesCur.hl7DefSegments.Count;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(Lan.g("enumSegmentNameHL7", HL7DefMesCur.hl7DefSegments[i].SegmentName.ToString()));
                row.getCells().Add(HL7DefMesCur.hl7DefSegments[i].ItemOrder.ToString());
                row.getCells().add(HL7DefMesCur.hl7DefSegments[i].CanRepeat ? "X" : "");
                row.getCells().add(HL7DefMesCur.hl7DefSegments[i].IsOptional ? "X" : "");
                row.getCells().Add(HL7DefMesCur.hl7DefSegments[i].Note);
                gridMain.getRows().add(row);
            }
        }
         
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7DefSegmentEdit FormS = new FormHL7DefSegmentEdit();
        FormS.HL7DefSegCur = HL7DefMesCur.hl7DefSegments[e.getRow()];
        FormS.IsHL7DefInternal = IsHL7DefInternal;
        FormS.ShowDialog();
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete Message?"))
        {
            return ;
        }
         
        for (int s = 0;s < HL7DefMesCur.hl7DefSegments.Count;s++)
        {
            for (int f = 0;f < HL7DefMesCur.hl7DefSegments[s].hl7DefFields.Count;f++)
            {
                HL7DefFields.Delete(HL7DefMesCur.hl7DefSegments[s].hl7DefFields[f].HL7DefFieldNum);
            }
            HL7DefSegments.Delete(HL7DefMesCur.hl7DefSegments[s].HL7DefSegmentNum);
        }
        HL7DefMessages.delete(HL7DefMesCur.HL7DefMessageNum);
        DialogResult = DialogResult.OK;
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (HL7DefMesCur.getIsNew())
        {
            HL7DefMessages.insert(HL7DefMesCur);
            HL7DefMesCur.setIsNew(false);
        }
         
        FormHL7DefSegmentEdit FormS = new FormHL7DefSegmentEdit();
        FormS.HL7DefSegCur = new HL7DefSegment();
        FormS.HL7DefSegCur.HL7DefMessageNum = HL7DefMesCur.HL7DefMessageNum;
        FormS.HL7DefSegCur.setIsNew(true);
        FormS.IsHL7DefInternal = false;
        FormS.ShowDialog();
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //This button is disabled if IsHL7DefInternal
        if (radioOut.Checked && !StringSupport.equals(textItemOrder.errorProvider1.GetError(textItemOrder), ""))
        {
            MsgBox.show(this,"Please fix data entry error first.");
            return ;
        }
         
        HL7DefMesCur.MessageType = (MessageTypeHL7)comboMsgType.SelectedIndex + 1;
        //+1 because 0 is NotDefined and is not displayed for user to select
        HL7DefMesCur.EventType = (EventTypeHL7)comboEventType.SelectedIndex + 1;
        //+1 because 0 is NotDefined and is not displayed for user to select
        if (radioIn.Checked)
        {
            HL7DefMesCur.InOrOut = InOutHL7.Incoming;
        }
        else
        {
            HL7DefMesCur.InOrOut = InOutHL7.Outgoing;
            HL7DefMesCur.ItemOrder = PIn.Int(textItemOrder.Text);
        } 
        HL7DefMesCur.Note = textNote.Text;
        if (HL7DefMesCur.getIsNew())
        {
            HL7DefMessages.insert(HL7DefMesCur);
        }
        else
        {
            HL7DefMessages.update(HL7DefMesCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHL7DefMessageEdit.class);
        this.radioOut = new System.Windows.Forms.RadioButton();
        this.radioIn = new System.Windows.Forms.RadioButton();
        this.comboEventType = new System.Windows.Forms.ComboBox();
        this.comboMsgType = new System.Windows.Forms.ComboBox();
        this.labelItemOrder = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.labelDelete = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.textItemOrder = new OpenDental.ValidNum();
        this.SuspendLayout();
        //
        // radioOut
        //
        this.radioOut.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.radioOut.Location = new System.Drawing.Point(78, 85);
        this.radioOut.Name = "radioOut";
        this.radioOut.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.radioOut.Size = new System.Drawing.Size(100, 18);
        this.radioOut.TabIndex = 15;
        this.radioOut.TabStop = true;
        this.radioOut.Text = "Outgoing";
        this.radioOut.UseVisualStyleBackColor = true;
        //
        // radioIn
        //
        this.radioIn.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.radioIn.Location = new System.Drawing.Point(78, 65);
        this.radioIn.Name = "radioIn";
        this.radioIn.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.radioIn.Size = new System.Drawing.Size(100, 18);
        this.radioIn.TabIndex = 14;
        this.radioIn.TabStop = true;
        this.radioIn.Text = "Incoming";
        this.radioIn.UseVisualStyleBackColor = true;
        //
        // comboEventType
        //
        this.comboEventType.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.comboEventType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboEventType.Location = new System.Drawing.Point(164, 42);
        this.comboEventType.MaxDropDownItems = 100;
        this.comboEventType.Name = "comboEventType";
        this.comboEventType.Size = new System.Drawing.Size(138, 21);
        this.comboEventType.TabIndex = 13;
        //
        // comboMsgType
        //
        this.comboMsgType.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.comboMsgType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboMsgType.Location = new System.Drawing.Point(164, 21);
        this.comboMsgType.MaxDropDownItems = 100;
        this.comboMsgType.Name = "comboMsgType";
        this.comboMsgType.Size = new System.Drawing.Size(138, 21);
        this.comboMsgType.TabIndex = 12;
        //
        // labelItemOrder
        //
        this.labelItemOrder.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.labelItemOrder.Location = new System.Drawing.Point(38, 105);
        this.labelItemOrder.Name = "labelItemOrder";
        this.labelItemOrder.Size = new System.Drawing.Size(125, 18);
        this.labelItemOrder.TabIndex = 10;
        this.labelItemOrder.Text = "Item Order";
        this.labelItemOrder.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textNote
        //
        this.textNote.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.textNote.Location = new System.Drawing.Point(424, 16);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(285, 109);
        this.textNote.TabIndex = 17;
        //
        // label12
        //
        this.label12.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label12.Location = new System.Drawing.Point(313, 16);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(110, 18);
        this.label12.TabIndex = 8;
        this.label12.Text = "Note";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label10.Location = new System.Drawing.Point(38, 20);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(125, 18);
        this.label10.TabIndex = 9;
        this.label10.Text = "Message Type";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label8.Location = new System.Drawing.Point(38, 42);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(125, 18);
        this.label8.TabIndex = 11;
        this.label8.Text = "Event Type";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDelete
        //
        this.labelDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelDelete.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDelete.Location = new System.Drawing.Point(108, 492);
        this.labelDelete.Name = "labelDelete";
        this.labelDelete.Size = new System.Drawing.Size(266, 28);
        this.labelDelete.TabIndex = 66;
        this.labelDelete.Text = "This HL7Def is internal. To edit this HL7Def you must first copy it to the Custom" + " list.";
        this.labelDelete.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelDelete.Visible = false;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 140);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(692, 348);
        this.gridMain.TabIndex = 7;
        this.gridMain.setTitle("Segments");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(548, 494);
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
        this.butCancel.Location = new System.Drawing.Point(634, 494);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(17, 494);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(85, 24);
        this.butDelete.TabIndex = 18;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
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
        this.butAdd.Location = new System.Drawing.Point(409, 494);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 0;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // textItemOrder
        //
        this.textItemOrder.Location = new System.Drawing.Point(164, 103);
        this.textItemOrder.setMaxVal(255);
        this.textItemOrder.setMinVal(0);
        this.textItemOrder.Name = "textItemOrder";
        this.textItemOrder.Size = new System.Drawing.Size(34, 20);
        this.textItemOrder.TabIndex = 67;
        //
        // FormHL7DefMessageEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.textItemOrder);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.labelDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.radioOut);
        this.Controls.Add(this.radioIn);
        this.Controls.Add(this.comboEventType);
        this.Controls.Add(this.comboMsgType);
        this.Controls.Add(this.labelItemOrder);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormHL7DefMessageEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HL7 Def Message Edit";
        this.Load += new System.EventHandler(this.FormHL7DefMessageEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.RadioButton radioOut = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioIn = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.ComboBox comboEventType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboMsgType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelItemOrder = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelDelete = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
    private OpenDental.ValidNum textItemOrder;
}


