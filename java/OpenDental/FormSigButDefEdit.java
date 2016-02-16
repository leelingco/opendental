//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:50 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSigButDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.SigButDefElements;
import OpenDentBusiness.SigButDefs;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.SignalElementType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSigButDefEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label2 = new Label();
    private TextBox textButtonText = new TextBox();
    private Label label3 = new Label();
    private Label label5 = new Label();
    private OpenDental.ValidNum textSynchIcon;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDelete;
    private OpenFileDialog openFileDialog1 = new OpenFileDialog();
    private SaveFileDialog saveFileDialog1 = new SaveFileDialog();
    private RadioButton radioAll = new RadioButton();
    private RadioButton radioOne = new RadioButton();
    private TextBox textComputerName = new TextBox();
    private ComboBox comboTo = new ComboBox();
    private Label label4 = new Label();
    private Label label10 = new Label();
    private ComboBox comboExtras = new ComboBox();
    private Label label11 = new Label();
    private ComboBox comboMessage = new ComboBox();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private SigElementDef[] sigElementDefUser = new SigElementDef[]();
    private SigElementDef[] sigElementDefExtras = new SigElementDef[]();
    private SigElementDef[] sigElementDefMessages = new SigElementDef[]();
    //<summary>This needs to be set before calling this form.  Blank means applies to all.</summary>
    //public string ComputerName;
    /**
    * Required to be set before opening this form.
    */
    public SigButDef ButtonCur;
    /**
    * 
    */
    public FormSigButDefEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSigButDefEdit.class);
        this.label2 = new System.Windows.Forms.Label();
        this.textButtonText = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
        this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
        this.butDelete = new OpenDental.UI.Button();
        this.textSynchIcon = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.radioAll = new System.Windows.Forms.RadioButton();
        this.radioOne = new System.Windows.Forms.RadioButton();
        this.textComputerName = new System.Windows.Forms.TextBox();
        this.comboTo = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.comboExtras = new System.Windows.Forms.ComboBox();
        this.label11 = new System.Windows.Forms.Label();
        this.comboMessage = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label2.Location = new System.Drawing.Point(102, 84);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 20);
        this.label2.TabIndex = 4;
        this.label2.Text = "Text";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textButtonText
        //
        this.textButtonText.Location = new System.Drawing.Point(204, 81);
        this.textButtonText.Name = "textButtonText";
        this.textButtonText.Size = new System.Drawing.Size(105, 20);
        this.textButtonText.TabIndex = 0;
        //
        // label3
        //
        this.label3.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label3.Location = new System.Drawing.Point(261, 104);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(304, 40);
        this.label3.TabIndex = 6;
        this.label3.Text = "The cell number (1-9) of the main program icon that should light up whenever this" + " button lights up.  0 for none.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label5
        //
        this.label5.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label5.Location = new System.Drawing.Point(102, 114);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 20);
        this.label5.TabIndex = 8;
        this.label5.Text = "Synch Icon";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // openFileDialog1
        //
        this.openFileDialog1.FileName = "openFileDialog1";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(45, 292);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(82, 26);
        this.butDelete.TabIndex = 14;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textSynchIcon
        //
        this.textSynchIcon.Location = new System.Drawing.Point(204, 114);
        this.textSynchIcon.setMaxVal(9);
        this.textSynchIcon.setMinVal(0);
        this.textSynchIcon.Name = "textSynchIcon";
        this.textSynchIcon.Size = new System.Drawing.Size(51, 20);
        this.textSynchIcon.TabIndex = 1;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(442, 292);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(544, 292);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // radioAll
        //
        this.radioAll.AutoCheck = false;
        this.radioAll.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioAll.Location = new System.Drawing.Point(2, 10);
        this.radioAll.Name = "radioAll";
        this.radioAll.Size = new System.Drawing.Size(214, 20);
        this.radioAll.TabIndex = 15;
        this.radioAll.TabStop = true;
        this.radioAll.Text = "Applies to all computers";
        this.radioAll.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioAll.UseVisualStyleBackColor = true;
        //
        // radioOne
        //
        this.radioOne.AutoCheck = false;
        this.radioOne.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioOne.Location = new System.Drawing.Point(2, 33);
        this.radioOne.Name = "radioOne";
        this.radioOne.Size = new System.Drawing.Size(214, 20);
        this.radioOne.TabIndex = 16;
        this.radioOne.TabStop = true;
        this.radioOne.Text = "Only applies to one computer:";
        this.radioOne.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioOne.UseVisualStyleBackColor = true;
        //
        // textComputerName
        //
        this.textComputerName.Location = new System.Drawing.Point(225, 33);
        this.textComputerName.Name = "textComputerName";
        this.textComputerName.ReadOnly = true;
        this.textComputerName.Size = new System.Drawing.Size(154, 20);
        this.textComputerName.TabIndex = 17;
        //
        // comboTo
        //
        this.comboTo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboTo.FormattingEnabled = true;
        this.comboTo.Location = new System.Drawing.Point(204, 147);
        this.comboTo.MaxDropDownItems = 30;
        this.comboTo.Name = "comboTo";
        this.comboTo.Size = new System.Drawing.Size(121, 21);
        this.comboTo.TabIndex = 18;
        //
        // label4
        //
        this.label4.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label4.Location = new System.Drawing.Point(102, 148);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 20);
        this.label4.TabIndex = 19;
        this.label4.Text = "To User";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label10.Location = new System.Drawing.Point(102, 183);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(100, 20);
        this.label10.TabIndex = 24;
        this.label10.Text = "Extras";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboExtras
        //
        this.comboExtras.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboExtras.FormattingEnabled = true;
        this.comboExtras.Location = new System.Drawing.Point(204, 182);
        this.comboExtras.MaxDropDownItems = 30;
        this.comboExtras.Name = "comboExtras";
        this.comboExtras.Size = new System.Drawing.Size(121, 21);
        this.comboExtras.TabIndex = 23;
        //
        // label11
        //
        this.label11.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.label11.Location = new System.Drawing.Point(102, 217);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(100, 20);
        this.label11.TabIndex = 26;
        this.label11.Text = "Message";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboMessage
        //
        this.comboMessage.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboMessage.FormattingEnabled = true;
        this.comboMessage.Location = new System.Drawing.Point(204, 216);
        this.comboMessage.MaxDropDownItems = 30;
        this.comboMessage.Name = "comboMessage";
        this.comboMessage.Size = new System.Drawing.Size(121, 21);
        this.comboMessage.TabIndex = 25;
        //
        // FormSigButDefEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(671, 343);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.comboMessage);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.comboExtras);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.comboTo);
        this.Controls.Add(this.textComputerName);
        this.Controls.Add(this.radioOne);
        this.Controls.Add(this.radioAll);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textSynchIcon);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textButtonText);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSigButDefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Message Button";
        this.Load += new System.EventHandler(this.FormSigButDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formSigButDefEdit_Load(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(ButtonCur.ComputerName, ""))
        {
            radioAll.Checked = true;
        }
        else
        {
            radioOne.Checked = true;
            textComputerName.Text = ButtonCur.ComputerName;
        } 
        textButtonText.Text = ButtonCur.ButtonText;
        textSynchIcon.Text = ButtonCur.SynchIcon.ToString();
        sigElementDefUser = SigElementDefs.getSubList(SignalElementType.User);
        sigElementDefExtras = SigElementDefs.getSubList(SignalElementType.Extra);
        sigElementDefMessages = SigElementDefs.getSubList(SignalElementType.Message);
        SigButDefElement elementUser = SigButDefs.getElement(ButtonCur,SignalElementType.User);
        SigButDefElement elementExtra = SigButDefs.getElement(ButtonCur,SignalElementType.Extra);
        SigButDefElement elementMessage = SigButDefs.getElement(ButtonCur,SignalElementType.Message);
        comboTo.Items.Clear();
        comboTo.Items.Add(Lan.g(this,"none"));
        comboTo.SelectedIndex = 0;
        for (int i = 0;i < sigElementDefUser.Length;i++)
        {
            comboTo.Items.Add(sigElementDefUser[i].SigText);
            if (elementUser != null && elementUser.SigElementDefNum == sigElementDefUser[i].SigElementDefNum)
            {
                comboTo.SelectedIndex = i + 1;
            }
             
        }
        comboExtras.Items.Clear();
        comboExtras.Items.Add(Lan.g(this,"none"));
        comboExtras.SelectedIndex = 0;
        for (int i = 0;i < sigElementDefExtras.Length;i++)
        {
            comboExtras.Items.Add(sigElementDefExtras[i].SigText);
            if (elementExtra != null && elementExtra.SigElementDefNum == sigElementDefExtras[i].SigElementDefNum)
            {
                comboExtras.SelectedIndex = i + 1;
            }
             
        }
        comboMessage.Items.Clear();
        comboMessage.Items.Add(Lan.g(this,"none"));
        comboMessage.SelectedIndex = 0;
        for (int i = 0;i < sigElementDefMessages.Length;i++)
        {
            comboMessage.Items.Add(sigElementDefMessages[i].SigText);
            if (elementMessage != null && elementMessage.SigElementDefNum == sigElementDefMessages[i].SigElementDefNum)
            {
                comboMessage.SelectedIndex = i + 1;
            }
             
        }
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            if (!MsgBox.show(this,true,"Delete?"))
            {
                return ;
            }
             
            SigButDefs.delete(ButtonCur);
            //also deletes elements
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textSynchIcon.errorProvider1.GetError(textSynchIcon), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textButtonText.Text, ""))
        {
            MsgBox.show(this,"Please enter a text description first.");
            return ;
        }
         
        if (StringSupport.equals(textSynchIcon.Text, ""))
        {
            textSynchIcon.Text = "0";
        }
         
        ButtonCur.ButtonText = textButtonText.Text;
        ButtonCur.SynchIcon = PIn.Byte(textSynchIcon.Text);
        if (IsNew)
        {
            SigButDefs.insert(ButtonCur);
        }
        else
        {
            SigButDefs.update(ButtonCur);
        } 
        //delete all the existing elements
        SigButDefs.deleteElements(ButtonCur);
        SigButDefElement element;
        if (comboTo.SelectedIndex != 0)
        {
            element = new SigButDefElement();
            element.SigButDefNum = ButtonCur.SigButDefNum;
            element.SigElementDefNum = sigElementDefUser[comboTo.SelectedIndex - 1].SigElementDefNum;
            SigButDefElements.insert(element);
        }
         
        if (comboExtras.SelectedIndex != 0)
        {
            element = new SigButDefElement();
            element.SigButDefNum = ButtonCur.SigButDefNum;
            element.SigElementDefNum = sigElementDefExtras[comboExtras.SelectedIndex - 1].SigElementDefNum;
            SigButDefElements.insert(element);
        }
         
        if (comboMessage.SelectedIndex != 0)
        {
            element = new SigButDefElement();
            element.SigButDefNum = ButtonCur.SigButDefNum;
            element.SigElementDefNum = sigElementDefMessages[comboMessage.SelectedIndex - 1].SigElementDefNum;
            SigButDefElements.insert(element);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


