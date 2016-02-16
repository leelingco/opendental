//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAutoItemEdit;
import OpenDental.FormProcCodes;
import OpenDental.Lan;
import OpenDentBusiness.AutoCodeC;
import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.AutoCodeCondC;
import OpenDentBusiness.AutoCodeConds;
import OpenDentBusiness.AutoCodeItem;
import OpenDentBusiness.AutoCodeItemC;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.AutoCondition;
import OpenDentBusiness.ProcedureCodes;

/**
* 
*/
public class FormAutoItemEdit  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textADA = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listConditions = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butChange;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * Set this value externally before opening this form, even if IsNew.
    */
    public AutoCodeItem AutoCodeItemCur;
    /**
    * 
    */
    public FormAutoItemEdit() throws Exception {
        initializeComponent();
        Lan.f(this);
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoItemEdit.class);
        this.textADA = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.listConditions = new System.Windows.Forms.ListBox();
        this.butChange = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textADA
        //
        this.textADA.Location = new System.Drawing.Point(108, 54);
        this.textADA.Name = "textADA";
        this.textADA.ReadOnly = true;
        this.textADA.Size = new System.Drawing.Size(100, 20);
        this.textADA.TabIndex = 0;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 58);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 12);
        this.label1.TabIndex = 1;
        this.label1.Text = "Code";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listConditions
        //
        this.listConditions.Location = new System.Drawing.Point(334, 56);
        this.listConditions.Name = "listConditions";
        this.listConditions.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listConditions.Size = new System.Drawing.Size(166, 407);
        this.listConditions.TabIndex = 2;
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(214, 50);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(76, 25);
        this.butChange.TabIndex = 24;
        this.butChange.Text = "C&hange";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(540, 442);
        this.butCancel.Name = "butCancel";
        this.butCancel.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 23;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(540, 408);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 22;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(356, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 14);
        this.label2.TabIndex = 25;
        this.label2.Text = "Conditions";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // FormAutoItemEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(644, 490);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.listConditions);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textADA);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutoItemEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit AutoItem";
        this.Load += new System.EventHandler(this.FormAutoItemEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAutoItemEdit_Load(Object sender, System.EventArgs e) throws Exception {
        AutoCodeConds.refreshCache();
        if (IsNew)
        {
            this.Text = Lan.g(this,"Add Auto Code Item");
        }
        else
        {
            this.Text = Lan.g(this,"Edit Auto Code Item");
            textADA.Text = ProcedureCodes.getStringProcCode(AutoCodeItemCur.CodeNum);
        } 
        fillList();
    }

    private void fillList() throws Exception {
        listConditions.Items.Clear();
        for (Object __dummyForeachVar0 : Enum.GetNames(AutoCondition.class))
        {
            String s = (String)__dummyForeachVar0;
            listConditions.Items.Add(Lan.g("enumAutoConditions",s));
        }
        for (int i = 0;i < AutoCodeCondC.getList().Length;i++)
        {
            if (AutoCodeCondC.getList()[i].AutoCodeItemNum == AutoCodeItemCur.AutoCodeItemNum)
            {
                listConditions.SetSelected((int)AutoCodeCondC.getList()[i].Cond, true);
            }
             
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textADA.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Code cannot be left blank."));
            listConditions.SelectedIndex = -1;
            fillList();
            return ;
        }
         
        AutoCodeItemCur.CodeNum = ProcedureCodes.GetCodeNum(textADA.Text);
        if (IsNew)
        {
            AutoCodeItems.insert(AutoCodeItemCur);
        }
        else
        {
            AutoCodeItems.update(AutoCodeItemCur);
        } 
        AutoCodeConds.deleteForItemNum(AutoCodeItemCur.AutoCodeItemNum);
        for (int i = 0;i < listConditions.SelectedIndices.Count;i++)
        {
            AutoCodeCond AutoCodeCondCur = new AutoCodeCond();
            AutoCodeCondCur.AutoCodeItemNum = AutoCodeItemCur.AutoCodeItemNum;
            AutoCodeCondCur.Cond = (AutoCondition)listConditions.SelectedIndices[i];
            AutoCodeConds.insert(AutoCodeCondCur);
        }
        DialogResult = DialogResult.OK;
    }

    private void butChange_Click(Object sender, System.EventArgs e) throws Exception {
        FormProcCodes FormP = new FormProcCodes();
        FormP.IsSelectionMode = true;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            textADA.Text = ProcedureCodes.getStringProcCode(AutoCodeItemCur.CodeNum);
            return ;
        }
         
        if (AutoCodeItemC.getHList().ContainsKey(FormP.SelectedCodeNum) && (long)AutoCodeItemC.getHList()[FormP.SelectedCodeNum] != AutoCodeItemCur.AutoCodeNum)
        {
            //This section is a fix for an old bug that did not cause items to get deleted properly
            if (!AutoCodeC.getHList().ContainsKey((long)AutoCodeItemC.getHList()[FormP.SelectedCodeNum]))
            {
                AutoCodeItems.delete((long)AutoCodeItemC.getHList()[FormP.SelectedCodeNum]);
                textADA.Text = ProcedureCodes.getStringProcCode(FormP.SelectedCodeNum);
            }
            else
            {
                MessageBox.Show(Lan.g(this,"That procedure code is already in use in a different Auto Code.  Not allowed to use it here."));
                textADA.Text = ProcedureCodes.getStringProcCode(AutoCodeItemCur.CodeNum);
            } 
        }
        else
        {
            textADA.Text = ProcedureCodes.getStringProcCode(FormP.SelectedCodeNum);
        } 
    }

}


