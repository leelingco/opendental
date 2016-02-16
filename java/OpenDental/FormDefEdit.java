//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.Patients;
import OpenDentBusiness.ProcedureCodes;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormDefEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butColor = new System.Windows.Forms.Button();
    private System.Windows.Forms.ColorDialog colorDialog1 = new System.Windows.Forms.ColorDialog();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label labelColor = new System.Windows.Forms.Label();
    /**
    * 
    */
    public static boolean CanEditName = new boolean();
    /**
    * 
    */
    public static boolean EnableValue = new boolean();
    /**
    * 
    */
    public static boolean EnableColor = new boolean();
    /**
    * 
    */
    public static String HelpText = new String();
    private System.Windows.Forms.CheckBox checkHidden = new System.Windows.Forms.CheckBox();
    /**
    * 
    */
    public static String ValueText = new String();
    private Def DefCur;
    public static boolean CanHide = new boolean();
    private OpenDental.UI.Button butDelete;
    public static boolean CanDelete = new boolean();
    /**
    * 
    */
    public FormDefEdit(Def defCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Lan.f(this);
        DefCur = defCur.copy();
    }

    /**
    * 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDefEdit.class);
        this.labelName = new System.Windows.Forms.Label();
        this.labelValue = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.textValue = new System.Windows.Forms.TextBox();
        this.butColor = new System.Windows.Forms.Button();
        this.colorDialog1 = new System.Windows.Forms.ColorDialog();
        this.labelColor = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkHidden = new System.Windows.Forms.CheckBox();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelName
        //
        this.labelName.Location = new System.Drawing.Point(47, 24);
        this.labelName.Name = "labelName";
        this.labelName.Size = new System.Drawing.Size(150, 16);
        this.labelName.TabIndex = 0;
        this.labelName.Text = "Name";
        this.labelName.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelValue
        //
        this.labelValue.Location = new System.Drawing.Point(218, 22);
        this.labelValue.Name = "labelValue";
        this.labelValue.Size = new System.Drawing.Size(164, 16);
        this.labelValue.TabIndex = 1;
        this.labelValue.Text = "Value";
        this.labelValue.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(32, 40);
        this.textName.Multiline = true;
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(178, 64);
        this.textName.TabIndex = 0;
        //
        // textValue
        //
        this.textValue.Location = new System.Drawing.Point(210, 40);
        this.textValue.MaxLength = 256;
        this.textValue.Multiline = true;
        this.textValue.Name = "textValue";
        this.textValue.Size = new System.Drawing.Size(180, 64);
        this.textValue.TabIndex = 1;
        //
        // butColor
        //
        this.butColor.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColor.Location = new System.Drawing.Point(392, 40);
        this.butColor.Name = "butColor";
        this.butColor.Size = new System.Drawing.Size(30, 20);
        this.butColor.TabIndex = 2;
        this.butColor.Click += new System.EventHandler(this.butColor_Click);
        //
        // colorDialog1
        //
        this.colorDialog1.FullOpen = true;
        //
        // labelColor
        //
        this.labelColor.Location = new System.Drawing.Point(389, 21);
        this.labelColor.Name = "labelColor";
        this.labelColor.Size = new System.Drawing.Size(74, 16);
        this.labelColor.TabIndex = 5;
        this.labelColor.Text = "Color";
        this.labelColor.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(298, 131);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 4;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(393, 131);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 5;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkHidden
        //
        this.checkHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHidden.Location = new System.Drawing.Point(432, 38);
        this.checkHidden.Name = "checkHidden";
        this.checkHidden.Size = new System.Drawing.Size(99, 24);
        this.checkHidden.TabIndex = 3;
        this.checkHidden.Text = "Hidden";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(32, 131);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 25);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormDefEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(558, 176);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkHidden);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelColor);
        this.Controls.Add(this.butColor);
        this.Controls.Add(this.textValue);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.labelValue);
        this.Controls.Add(this.labelName);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Definition";
        this.Load += new System.EventHandler(this.FormDefEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDefEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
                ;
        }
         
        //handled from previous form
        if (!CanEditName)
        {
            textName.ReadOnly = true;
            if (!DefCur.IsHidden)
            {
                checkHidden.Enabled = false;
            }
             
        }
         
        //prevent hiding these types of defs
        labelValue.Text = ValueText;
        if (DefCur.Category == DefCat.AdjTypes && !IsNew)
        {
            labelValue.Text = "Not allowed to change sign after an adjustment is created.";
            textValue.Visible = false;
        }
         
        if (!EnableValue)
        {
            labelValue.Visible = false;
            textValue.Visible = false;
        }
         
        if (!EnableColor)
        {
            labelColor.Visible = false;
            butColor.Visible = false;
        }
         
        if (!CanHide)
        {
            checkHidden.Visible = false;
        }
         
        if (!CanDelete)
        {
            butDelete.Visible = false;
        }
         
        textName.Text = DefCur.ItemName;
        textValue.Text = DefCur.ItemValue;
        butColor.BackColor = DefCur.ItemColor;
        checkHidden.Checked = DefCur.IsHidden;
    }

    //MessageBox.Show(Preferences.Cur.ItemColor.ToString());
    private void butColor_Click(Object sender, System.EventArgs e) throws Exception {
        colorDialog1.Color = butColor.BackColor;
        colorDialog1.ShowDialog();
        butColor.BackColor = colorDialog1.Color;
    }

    //textColor.Text=colorDialog1.Color.Name;
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //This is VERY new.  Only allowed and visible for two categories so far: supply cats and claim custom tracking.
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        try
        {
            Defs.delete(DefCur);
            DialogResult = DialogResult.OK;
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textName.Text, ""))
        {
            MsgBox.show(this,"Name required.");
            return ;
        }
         
        switch((DefCat)DefCur.Category)
        {
            case AdjTypes: 
                if (!StringSupport.equals(textValue.Text, "+") && !StringSupport.equals(textValue.Text, "-"))
                {
                    MessageBox.Show(Lan.g(this,"Valid values are + or -."));
                    return ;
                }
                 
                break;
            case ApptProcsQuickAdd: 
                String[] procs = textValue.Text.Split(',');
                for (int i = 0;i < procs.Length;i++)
                {
                    if (ProcedureCodes.GetProcCode(procs[i]).ProcCode == null)
                    {
                        MessageBox.Show(Lan.g(this,"Invalid procedure code or formatting. Valid format example: D1234,D2345,D3456"));
                        return ;
                    }
                     
                }
                break;
            case BillingTypes: 
                //test for not require tooth number if time
                if (!StringSupport.equals(textValue.Text, "") && !StringSupport.equals(textValue.Text, "E"))
                {
                    MsgBox.show(this,"Valid values are blank or E.");
                    return ;
                }
                 
                if (checkHidden.Checked && Patients.isBillingTypeInUse(DefCur.DefNum))
                {
                    if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Warning: Billing type is currently in use by patients."))
                    {
                        return ;
                    }
                     
                }
                 
                break;
            case CommLogTypes: 
                if (!StringSupport.equals(textValue.Text, "") && !StringSupport.equals(textValue.Text, "MISC") && !StringSupport.equals(textValue.Text, "APPT") && !StringSupport.equals(textValue.Text, "FIN") && !StringSupport.equals(textValue.Text, "RECALL"))
                {
                    MessageBox.Show(Lan.g(this,"Valid values are blank,APPT,FIN,RECALL,or MISC."));
                    return ;
                }
                 
                break;
            case RecallUnschedStatus: 
                if (textValue.Text.Length > 7)
                {
                    MessageBox.Show(Lan.g(this,"Maximum length is 7."));
                    return ;
                }
                 
                break;
            case DiscountTypes: 
                int discVal = new int();
                if (StringSupport.equals(textValue.Text, ""))
                    break;
                 
                try
                {
                    discVal = System.Convert.ToInt32(textValue.Text);
                }
                catch (Exception __dummyCatchVar0)
                {
                    MessageBox.Show(Lan.g(this,"Not a valid number"));
                    return ;
                }

                if (discVal < 0 || discVal > 100)
                {
                    MessageBox.Show(Lan.g(this,"Valid values are between 0 and 100"));
                    return ;
                }
                 
                textValue.Text = discVal.ToString();
                break;
            case OperatoriesOld: 
                if (textValue.Text.Length > 5)
                {
                    MessageBox.Show(Lan.g(this,"Maximum length of abbreviation is 5."));
                    return ;
                }
                 
                break;
            case TxPriorities: 
                if (textValue.Text.Length > 7)
                {
                    MessageBox.Show(Lan.g(this,"Maximum length of abbreviation is 7."));
                    return ;
                }
                 
                break;
            case ImageCats: 
                textValue.Text = textValue.Text.ToUpper().Replace(",", "");
                if (!Regex.IsMatch(textValue.Text, "^[XPS]*$"))
                {
                    textValue.Text = "";
                }
                 
                break;
            case ProcCodeCats: 
                if (checkHidden.Checked)
                {
                    Defs.refreshCache();
                    Def[] enabledDefs = DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()];
                    //if no enabled defs or this is the only enabled def, don't allow disabling
                    if (enabledDefs.Length == 0 || (enabledDefs.Length == 1 && enabledDefs[0].DefNum == DefCur.DefNum))
                    {
                        MsgBox.show(this,"At least one procedure code category must be enabled.");
                        return ;
                    }
                     
                }
                 
                break;
        
        }
        /*case DefCat.FeeSchedNames:
        					if(textValue.Text=="C" || textValue.Text=="c") {
        						textValue.Text="C";
        					}
        					else if(textValue.Text=="A" || textValue.Text=="a") {
        						textValue.Text="A";
        					}
        					else textValue.Text="";
        					break;*/
        //end switch
        DefCur.ItemName = textName.Text;
        if (EnableValue)
            DefCur.ItemValue = textValue.Text;
         
        if (EnableColor)
            DefCur.ItemColor = butColor.BackColor;
         
        DefCur.IsHidden = checkHidden.Checked;
        if (IsNew)
        {
            Defs.insert(DefCur);
        }
        else
        {
            Defs.update(DefCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


