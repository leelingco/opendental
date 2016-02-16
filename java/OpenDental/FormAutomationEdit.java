//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormAutomationConditionEdit;
import OpenDental.FormAutomationEdit;
import OpenDental.FormProcCodes;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Automation;
import OpenDentBusiness.AutomationAction;
import OpenDentBusiness.AutomationCondition;
import OpenDentBusiness.AutomationConditions;
import OpenDentBusiness.Automations;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.SheetDefC;
import OpenDentBusiness.SheetTypeEnum;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAutomationEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private Label label1 = new Label();
    private TextBox textDescription = new TextBox();
    private Label label2 = new Label();
    private TextBox textProcCodes = new TextBox();
    private Label labelProcCodes = new Label();
    private Label label4 = new Label();
    private Label labelSheetDef = new Label();
    private Label labelCommType = new Label();
    private Label labelMessage = new Label();
    private TextBox textMessage = new TextBox();
    private ComboBox comboTrigger = new ComboBox();
    private ComboBox comboAction = new ComboBox();
    private ComboBox comboCommType = new ComboBox();
    private ComboBox comboSheetDef = new ComboBox();
    private OpenDental.UI.Button butProcCode;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private Automation AutoCur;
    private List<AutomationCondition> autoList = new List<AutomationCondition>();
    /**
    * 
    */
    public FormAutomationEdit(Automation autoCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        AutoCur = autoCur.copy();
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
        OpenDental.UI.Button butDelete;
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutomationEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textProcCodes = new System.Windows.Forms.TextBox();
        this.labelProcCodes = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.labelSheetDef = new System.Windows.Forms.Label();
        this.labelCommType = new System.Windows.Forms.Label();
        this.labelMessage = new System.Windows.Forms.Label();
        this.textMessage = new System.Windows.Forms.TextBox();
        this.comboTrigger = new System.Windows.Forms.ComboBox();
        this.comboAction = new System.Windows.Forms.ComboBox();
        this.comboCommType = new System.Windows.Forms.ComboBox();
        this.comboSheetDef = new System.Windows.Forms.ComboBox();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butProcCode = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butDelete
        //
        butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        butDelete.setAutosize(true);
        butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        butDelete.setCornerRadius(4F);
        butDelete.Image = Resources.getdeleteX();
        butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        butDelete.Location = new System.Drawing.Point(48, 454);
        butDelete.Name = "butDelete";
        butDelete.Size = new System.Drawing.Size(75, 24);
        butDelete.TabIndex = 16;
        butDelete.Text = "&Delete";
        butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(48, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(111, 20);
        this.label1.TabIndex = 11;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(161, 25);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(316, 20);
        this.textDescription.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(48, 50);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 20);
        this.label2.TabIndex = 18;
        this.label2.Text = "Trigger";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProcCodes
        //
        this.textProcCodes.Location = new System.Drawing.Point(161, 77);
        this.textProcCodes.Name = "textProcCodes";
        this.textProcCodes.Size = new System.Drawing.Size(316, 20);
        this.textProcCodes.TabIndex = 2;
        //
        // labelProcCodes
        //
        this.labelProcCodes.Location = new System.Drawing.Point(13, 76);
        this.labelProcCodes.Name = "labelProcCodes";
        this.labelProcCodes.Size = new System.Drawing.Size(146, 29);
        this.labelProcCodes.TabIndex = 20;
        this.labelProcCodes.Text = "Procedure Code(s)\r\n(separated with commas)";
        this.labelProcCodes.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(48, 255);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 20);
        this.label4.TabIndex = 21;
        this.label4.Text = "Action";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelSheetDef
        //
        this.labelSheetDef.Location = new System.Drawing.Point(48, 282);
        this.labelSheetDef.Name = "labelSheetDef";
        this.labelSheetDef.Size = new System.Drawing.Size(111, 20);
        this.labelSheetDef.TabIndex = 22;
        this.labelSheetDef.Text = "Sheet Def";
        this.labelSheetDef.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCommType
        //
        this.labelCommType.Location = new System.Drawing.Point(48, 307);
        this.labelCommType.Name = "labelCommType";
        this.labelCommType.Size = new System.Drawing.Size(111, 20);
        this.labelCommType.TabIndex = 24;
        this.labelCommType.Text = "CommType";
        this.labelCommType.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMessage
        //
        this.labelMessage.Location = new System.Drawing.Point(39, 333);
        this.labelMessage.Name = "labelMessage";
        this.labelMessage.Size = new System.Drawing.Size(120, 20);
        this.labelMessage.TabIndex = 25;
        this.labelMessage.Text = "Message";
        this.labelMessage.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMessage
        //
        this.textMessage.Location = new System.Drawing.Point(161, 334);
        this.textMessage.Multiline = true;
        this.textMessage.Name = "textMessage";
        this.textMessage.Size = new System.Drawing.Size(316, 73);
        this.textMessage.TabIndex = 26;
        //
        // comboTrigger
        //
        this.comboTrigger.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboTrigger.FormattingEnabled = true;
        this.comboTrigger.Location = new System.Drawing.Point(161, 50);
        this.comboTrigger.Name = "comboTrigger";
        this.comboTrigger.Size = new System.Drawing.Size(183, 21);
        this.comboTrigger.TabIndex = 27;
        this.comboTrigger.SelectedIndexChanged += new System.EventHandler(this.comboTrigger_SelectedIndexChanged);
        //
        // comboAction
        //
        this.comboAction.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAction.FormattingEnabled = true;
        this.comboAction.Location = new System.Drawing.Point(161, 255);
        this.comboAction.Name = "comboAction";
        this.comboAction.Size = new System.Drawing.Size(183, 21);
        this.comboAction.TabIndex = 28;
        this.comboAction.SelectedIndexChanged += new System.EventHandler(this.comboAction_SelectedIndexChanged);
        //
        // comboCommType
        //
        this.comboCommType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCommType.FormattingEnabled = true;
        this.comboCommType.Location = new System.Drawing.Point(161, 307);
        this.comboCommType.Name = "comboCommType";
        this.comboCommType.Size = new System.Drawing.Size(183, 21);
        this.comboCommType.TabIndex = 29;
        //
        // comboSheetDef
        //
        this.comboSheetDef.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSheetDef.FormattingEnabled = true;
        this.comboSheetDef.Location = new System.Drawing.Point(161, 281);
        this.comboSheetDef.Name = "comboSheetDef";
        this.comboSheetDef.Size = new System.Drawing.Size(183, 21);
        this.comboSheetDef.TabIndex = 31;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(677, 225);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(65, 24);
        this.butAdd.TabIndex = 35;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(161, 103);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridMain.Size = new System.Drawing.Size(510, 146);
        this.gridMain.TabIndex = 34;
        this.gridMain.setTitle("Conditions");
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
        // butProcCode
        //
        this.butProcCode.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProcCode.setAutosize(true);
        this.butProcCode.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProcCode.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProcCode.setCornerRadius(4F);
        this.butProcCode.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProcCode.Location = new System.Drawing.Point(479, 75);
        this.butProcCode.Name = "butProcCode";
        this.butProcCode.Size = new System.Drawing.Size(23, 24);
        this.butProcCode.TabIndex = 32;
        this.butProcCode.Text = "...";
        this.butProcCode.Click += new System.EventHandler(this.butProcCode_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(677, 422);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(677, 454);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAutomationEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(778, 498);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butProcCode);
        this.Controls.Add(this.comboSheetDef);
        this.Controls.Add(this.comboCommType);
        this.Controls.Add(this.comboAction);
        this.Controls.Add(this.comboTrigger);
        this.Controls.Add(this.textMessage);
        this.Controls.Add(this.labelMessage);
        this.Controls.Add(this.labelCommType);
        this.Controls.Add(this.labelSheetDef);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textProcCodes);
        this.Controls.Add(this.labelProcCodes);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(butDelete);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutomationEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Automation";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormAutomationEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormAutomationEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAutomationEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = AutoCur.Description;
        for (int i = 0;i < Enum.GetNames(AutomationTrigger.class).Length;i++)
        {
            comboTrigger.Items.Add(Enum.GetNames(AutomationTrigger.class)[i]);
        }
        comboTrigger.SelectedIndex = ((Enum)AutoCur.Autotrigger).ordinal();
        textProcCodes.Text = AutoCur.ProcCodes;
        for (int i = 0;i < Enum.GetNames(AutomationAction.class).Length;i++)
        {
            //although might not be visible.
            comboAction.Items.Add(Enum.GetNames(AutomationAction.class)[i]);
        }
        comboAction.SelectedIndex = ((Enum)AutoCur.AutoAction).ordinal();
        for (int i = 0;i < SheetDefC.getListt().Count;i++)
        {
            comboSheetDef.Items.Add(SheetDefC.getListt()[i].Description);
            if (AutoCur.SheetDefNum == SheetDefC.getListt()[i].SheetDefNum)
            {
                comboSheetDef.SelectedIndex = i;
            }
             
        }
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()].Length;i++)
        {
            comboCommType.Items.Add(DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][i].DefNum == AutoCur.CommType)
            {
                comboCommType.SelectedIndex = i;
            }
             
        }
        textMessage.Text = AutoCur.MessageContent;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        AutomationConditions.refreshCache();
        autoList = AutomationConditions.getListByAutomationNum(AutoCur.AutomationNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("AutomationCondition","Field"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("AutomationCondition","Comparison"),75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("AutomationCondition","Text"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < autoList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(autoList[i].CompareField.ToString());
            row.getCells().Add(autoList[i].Comparison.ToString());
            row.getCells().Add(autoList[i].CompareString);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void comboTrigger_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (comboTrigger.SelectedIndex == ((Enum)AutomationTrigger.CompleteProcedure).ordinal())
        {
            labelProcCodes.Visible = true;
            textProcCodes.Visible = true;
            butProcCode.Visible = true;
        }
        else
        {
            labelProcCodes.Visible = false;
            textProcCodes.Visible = false;
            butProcCode.Visible = false;
        } 
    }

    private void comboAction_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.CreateCommlog).ordinal())
        {
            labelSheetDef.Visible = false;
            comboSheetDef.Visible = false;
            labelCommType.Visible = true;
            comboCommType.Visible = true;
            labelMessage.Visible = true;
            textMessage.Visible = true;
        }
        else if (comboAction.SelectedIndex == ((Enum)AutomationAction.PopUp).ordinal())
        {
            labelSheetDef.Visible = false;
            comboSheetDef.Visible = false;
            labelCommType.Visible = false;
            comboCommType.Visible = false;
            labelMessage.Visible = true;
            textMessage.Visible = true;
        }
        else
        {
            labelSheetDef.Visible = true;
            comboSheetDef.Visible = true;
            labelCommType.Visible = false;
            comboCommType.Visible = false;
            labelMessage.Visible = false;
            textMessage.Visible = false;
        }  
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormAutomationConditionEdit FormACE = new FormAutomationConditionEdit();
        FormACE.ConditionCur = autoList[e.getRow()];
        FormACE.ShowDialog();
        fillGrid();
    }

    private void butProcCode_Click(Object sender, EventArgs e) throws Exception {
        FormProcCodes formp = new FormProcCodes();
        formp.IsSelectionMode = true;
        formp.ShowDialog();
        if (formp.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (!StringSupport.equals(textProcCodes.Text, ""))
        {
            textProcCodes.Text += ",";
        }
         
        textProcCodes.Text += ProcedureCodes.getStringProcCode(formp.SelectedCodeNum);
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAutomationConditionEdit FormACE = new FormAutomationConditionEdit();
        FormACE.IsNew = true;
        FormACE.ConditionCur = new AutomationCondition();
        FormACE.ConditionCur.AutomationNum = AutoCur.AutomationNum;
        FormACE.ShowDialog();
        if (FormACE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //if(IsNew){
        //
        //	return;
        //}
        AutomationConditions.deleteByAutomationNum(AutoCur.AutomationNum);
        Automations.delete(AutoCur);
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text, ""))
        {
            MsgBox.show(this,"Description not allowed to be blank.");
            return ;
        }
         
        if (comboTrigger.SelectedIndex == ((Enum)AutomationTrigger.CompleteProcedure).ordinal())
        {
            if (textProcCodes.Text.Contains(" "))
            {
                MsgBox.show(this,"Procedure codes cannot contain any spaces.");
                return ;
            }
             
            if (StringSupport.equals(textProcCodes.Text, ""))
            {
                MsgBox.show(this,"Please enter valid procedure code(s) first.");
                return ;
            }
             
            String[] arrayCodes = textProcCodes.Text.Split(',');
            for (int i = 0;i < arrayCodes.Length;i++)
            {
                if (!ProcedureCodes.IsValidCode(arrayCodes[i]))
                {
                    MessageBox.Show(arrayCodes[i] + Lan.g(this," is not a valid procedure code."));
                    return ;
                }
                 
            }
        }
         
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.CreateCommlog).ordinal())
        {
            if (comboCommType.SelectedIndex == -1)
            {
                MsgBox.show(this,"A CommType must be selected.");
                return ;
            }
             
        }
         
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.PopUp).ordinal())
        {
            if (textMessage.Text.Trim() == String.Empty)
            {
                MsgBox.show(this,"Message cannot be blank.");
                return ;
            }
             
        }
         
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.PrintPatientLetter).ordinal())
        {
            if (comboSheetDef.SelectedIndex == -1)
            {
                MsgBox.show(this,"A SheetDef must be selected.");
                return ;
            }
             
            if (SheetDefC.getListt()[comboSheetDef.SelectedIndex].SheetType != SheetTypeEnum.PatientLetter)
            {
                MsgBox.show(this,"The selected sheet type must be a patient letter.");
                return ;
            }
             
        }
         
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.PrintReferralLetter).ordinal())
        {
            if (comboSheetDef.SelectedIndex == -1)
            {
                MsgBox.show(this,"A SheetDef must be selected.");
                return ;
            }
             
            if (SheetDefC.getListt()[comboSheetDef.SelectedIndex].SheetType != SheetTypeEnum.ReferralLetter)
            {
                MsgBox.show(this,"The selected sheet type must be a referral letter.");
                return ;
            }
             
        }
         
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.ShowExamSheet).ordinal())
        {
            if (comboSheetDef.SelectedIndex == -1)
            {
                MsgBox.show(this,"A SheetDef must be selected.");
                return ;
            }
             
            if (SheetDefC.getListt()[comboSheetDef.SelectedIndex].SheetType != SheetTypeEnum.ExamSheet)
            {
                MsgBox.show(this,"The selected sheet type must be an exam sheet.");
                return ;
            }
             
        }
         
        AutoCur.Description = textDescription.Text;
        AutoCur.Autotrigger = (AutomationTrigger)comboTrigger.SelectedIndex;
        if (comboTrigger.SelectedIndex == ((Enum)AutomationTrigger.CompleteProcedure).ordinal())
        {
            AutoCur.ProcCodes = textProcCodes.Text;
        }
        else
        {
            AutoCur.ProcCodes = "";
        } 
        AutoCur.AutoAction = (AutomationAction)comboAction.SelectedIndex;
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.PrintPatientLetter).ordinal() || comboAction.SelectedIndex == ((Enum)AutomationAction.PrintReferralLetter).ordinal() || comboAction.SelectedIndex == ((Enum)AutomationAction.ShowExamSheet).ordinal())
        {
            AutoCur.SheetDefNum = SheetDefC.getListt()[comboSheetDef.SelectedIndex].SheetDefNum;
        }
        else
        {
            AutoCur.SheetDefNum = 0;
        } 
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.CreateCommlog).ordinal())
        {
            AutoCur.CommType = DefC.getShort()[((Enum)DefCat.CommLogTypes).ordinal()][comboCommType.SelectedIndex].DefNum;
            AutoCur.MessageContent = textMessage.Text;
        }
        else
        {
            AutoCur.CommType = 0;
            AutoCur.MessageContent = "";
        } 
        if (comboAction.SelectedIndex == ((Enum)AutomationAction.PopUp).ordinal())
        {
            AutoCur.MessageContent = textMessage.Text;
        }
         
        //MessageContent was already set blank if not PopUp or Commlog above.
        Automations.update(AutoCur);
        //Because always inserted before opening this form.
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formAutomationEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        //this happens if cancel.
        if (IsNew)
        {
            AutomationConditions.deleteByAutomationNum(AutoCur.AutomationNum);
            Automations.delete(AutoCur);
        }
         
    }

}


