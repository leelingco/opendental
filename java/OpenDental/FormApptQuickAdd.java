//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.ProcedureCodes;

public class FormApptQuickAdd  extends Form 
{

    /**
    * If form closes with OK, this contains selected code nums.  All codeNums will already have been checked for validity.
    */
    public List<long> SelectedCodeNums = new List<long>();
    public Point ParentFormLocation = new Point();
    /**
    * Security handled in calling form.
    */
    public FormApptQuickAdd() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formApptQuickAdd_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()].Length;i++)
        {
            listQuickAdd.Items.Add(DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()][i].ItemName);
        }
        this.Location = new Point(this.ParentFormLocation.X + 75, this.ParentFormLocation.Y + 25);
    }

    private void formApptQuickAdd_Shown(Object sender, EventArgs e) throws Exception {
    }

    private void listQuickAdd_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listQuickAdd.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        SelectedCodeNums = new List<long>();
        String[] procCodeStrArray = DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()][listQuickAdd.SelectedIndices[0]].ItemValue.Split(',');
        long codeNum = new long();
        for (int i = 0;i < procCodeStrArray.Length;i++)
        {
            codeNum = ProcedureCodes.GetCodeNum(procCodeStrArray[i]);
            if (codeNum == 0)
            {
                MessageBox.Show(Lan.g(this,"Definition contains invalid code: ") + procCodeStrArray[i]);
                return ;
            }
             
            SelectedCodeNums.Add(codeNum);
        }
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listQuickAdd.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"Please select items first.");
            return ;
        }
         
        SelectedCodeNums = new List<long>();
        String[] procCodeStrArray = new String[]();
        long codeNum = new long();
        for (int s = 0;s < listQuickAdd.SelectedIndices.Count;s++)
        {
            procCodeStrArray = DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()][listQuickAdd.SelectedIndices[s]].ItemValue.Split(',');
            for (int i = 0;i < procCodeStrArray.Length;i++)
            {
                codeNum = ProcedureCodes.GetCodeNum(procCodeStrArray[i]);
                if (codeNum == 0)
                {
                    MessageBox.Show(Lan.g(this,"Definition contains invalid code: ") + procCodeStrArray[i]);
                    return ;
                }
                 
                SelectedCodeNums.Add(codeNum);
            }
        }
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.listQuickAdd = new System.Windows.Forms.ListBox();
        this.labelQuickAdd = new System.Windows.Forms.Label();
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
        this.butOK.Location = new System.Drawing.Point(292, 384);
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
        this.butCancel.Location = new System.Drawing.Point(292, 414);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listQuickAdd
        //
        this.listQuickAdd.IntegralHeight = false;
        this.listQuickAdd.Location = new System.Drawing.Point(70, 63);
        this.listQuickAdd.Name = "listQuickAdd";
        this.listQuickAdd.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listQuickAdd.Size = new System.Drawing.Size(186, 375);
        this.listQuickAdd.TabIndex = 152;
        this.listQuickAdd.DoubleClick += new System.EventHandler(this.listQuickAdd_DoubleClick);
        //
        // labelQuickAdd
        //
        this.labelQuickAdd.Location = new System.Drawing.Point(1, 9);
        this.labelQuickAdd.Name = "labelQuickAdd";
        this.labelQuickAdd.Size = new System.Drawing.Size(378, 45);
        this.labelQuickAdd.TabIndex = 151;
        this.labelQuickAdd.Text = "Double click on an item in the list to add procedures to this appointment.\r\nor\r\nH" + "old down the Ctrl key while selecting multiple items, then click OK.";
        this.labelQuickAdd.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // FormApptQuickAdd
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(379, 450);
        this.Controls.Add(this.listQuickAdd);
        this.Controls.Add(this.labelQuickAdd);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormApptQuickAdd";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Add Procedures";
        this.Load += new System.EventHandler(this.FormApptQuickAdd_Load);
        this.Shown += new System.EventHandler(this.FormApptQuickAdd_Shown);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listQuickAdd = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelQuickAdd = new System.Windows.Forms.Label();
}


