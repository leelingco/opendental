//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:08 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEmployerEdit;
import OpenDental.FormEmployers;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Employers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormEmployers  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listEmp = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private OpenDental.UI.Button butEdit;
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private OpenDental.UI.Button butCombine;
    private List<Employer> ListEmployers = new List<Employer>();
    //<summary>Set to true if using this dialog to select an employer.</summary>
    //public bool IsSelectMode;
    /**
    * 
    */
    public FormEmployers() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        ListEmployers = new List<Employer>();
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmployers.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listEmp = new System.Windows.Forms.ListBox();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butEdit = new OpenDental.UI.Button();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.butCombine = new OpenDental.UI.Button();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(349, 597);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(79, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(349, 560);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(79, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listEmp
        //
        this.listEmp.Location = new System.Drawing.Point(18, 17);
        this.listEmp.Name = "listEmp";
        this.listEmp.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listEmp.Size = new System.Drawing.Size(265, 602);
        this.listEmp.TabIndex = 2;
        this.listEmp.DoubleClick += new System.EventHandler(this.listEmp_DoubleClick);
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
        this.butAdd.Location = new System.Drawing.Point(349, 352);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(349, 389);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 26);
        this.butDelete.TabIndex = 8;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Image = Resources.geteditPencil();
        this.butEdit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEdit.Location = new System.Drawing.Point(349, 426);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(79, 26);
        this.butEdit.TabIndex = 9;
        this.butEdit.Text = "&Edit";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // butCombine
        //
        this.butCombine.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCombine.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCombine.setAutosize(true);
        this.butCombine.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCombine.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCombine.setCornerRadius(4F);
        this.butCombine.Location = new System.Drawing.Point(349, 463);
        this.butCombine.Name = "butCombine";
        this.butCombine.Size = new System.Drawing.Size(79, 26);
        this.butCombine.TabIndex = 10;
        this.butCombine.Text = "Co&mbine";
        this.toolTip1.SetToolTip(this.butCombine, "Combines multiple Employers");
        this.butCombine.Click += new System.EventHandler(this.butCombine_Click);
        //
        // FormEmployers
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(447, 649);
        this.Controls.Add(this.listEmp);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butCombine);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butEdit);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmployers";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Employers";
        this.Load += new System.EventHandler(this.FormEmployers_Load);
        this.ResumeLayout(false);
    }

    private void formEmployers_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Employers.refreshCache();
        ListEmployers.Clear();
        for (int i = 0;i < Employers.getList().Length;i++)
        {
            ListEmployers.Add(Employers.getList()[i]);
        }
        ListEmployers.Sort(CompareEmployers);
        listEmp.Items.Clear();
        for (int i = 0;i < ListEmployers.Count;i++)
        {
            listEmp.Items.Add(ListEmployers[i].EmpName);
        }
    }

    //if(IsSelectMode && ListEmployers[i].EmployerNum==Employers.Cur.EmployerNum){
    //	listEmp.SetSelected(i,true);
    //}
    private int compareEmployers(Employer emp1, Employer emp2) throws Exception {
        return emp1.EmpName.CompareTo(emp2.EmpName);
    }

    private void listEmp_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listEmp.SelectedIndices.Count == 0)
            return ;
         
        //EmployerCur=
        //if(IsSelectMode){
        //	DialogResult=DialogResult.OK;
        //	return;
        //}
        FormEmployerEdit FormEE = new FormEmployerEdit();
        FormEE.EmployerCur = ListEmployers[listEmp.SelectedIndices[0]];
        FormEE.ShowDialog();
        if (FormEE.DialogResult != DialogResult.OK)
            return ;
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormEmployerEdit FormEE = new FormEmployerEdit();
        FormEE.EmployerCur = new Employer();
        FormEE.IsNew = true;
        FormEE.ShowDialog();
        fillGrid();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listEmp.SelectedIndices.Count != 1)
        {
            MessageBox.Show(Lan.g(this,"Please select one item first."));
            return ;
        }
         
        //Employers.Cur=;
        //make sure no dependent patients:
        String dependentNames = Employers.DependentPatients(ListEmployers[listEmp.SelectedIndices[0]]);
        if (!StringSupport.equals(dependentNames, ""))
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete this employer because it it attached to " + "the following patients.  You should combine employers instead.") + "\r\n\r\n" + dependentNames);
            return ;
        }
         
        //make sure no dependent insplans:
        dependentNames = Employers.DependentInsPlans(ListEmployers[listEmp.SelectedIndices[0]]);
        if (!StringSupport.equals(dependentNames, ""))
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete this employer because it is attached to " + "the following insurance plans.  You should combine employers instead.") + "\r\n\r\n" + dependentNames);
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete Employer?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        Employers.Delete(ListEmployers[listEmp.SelectedIndices[0]]);
        fillGrid();
    }

    private void butEdit_Click(Object sender, System.EventArgs e) throws Exception {
        if (listEmp.SelectedIndices.Count != 1)
        {
            MessageBox.Show(Lan.g(this,"Please select one item first."));
            return ;
        }
         
        FormEmployerEdit FormEE = new FormEmployerEdit();
        FormEE.EmployerCur = ListEmployers[listEmp.SelectedIndices[0]];
        FormEE.ShowDialog();
        if (FormEE.DialogResult != DialogResult.OK)
            return ;
         
        fillGrid();
    }

    private void butCombine_Click(Object sender, System.EventArgs e) throws Exception {
        if (listEmp.SelectedIndices.Count < 2)
        {
            MessageBox.Show(Lan.g(this,"Please select multiple items first while holding down the control key."));
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Combine all these employers into a single employer? This will affect all patients using these employers."), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        List<long> employerNums = new List<long>();
        for (int i = 0;i < listEmp.SelectedIndices.Count;i++)
        {
            employerNums.Add(ListEmployers[listEmp.SelectedIndices[i]].EmployerNum);
        }
        Employers.Combine(employerNums);
        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        /*if(IsSelectMode){
        				if(listEmp.SelectedIndices.Count!=1){
        					Employers.Cur=new Employer();
        					//MessageBox.Show(Lan.g(this,"Please select one item first."));
        					//return;
        				}
        				else
        					Employers.Cur=ListEmployers[listEmp.SelectedIndices[0]];
        			}
        			else{
        				//update the other computers:
        				//DataValid.SetInvalid();//not needed due to intelligent refreshing
        			}*/
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


