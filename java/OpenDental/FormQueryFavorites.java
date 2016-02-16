//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import OpenDental.FormQueryEdit;
import OpenDental.FormQueryFavorites;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.ReportSimpleGrid;
import OpenDental.UserQueries;
import OpenDental.UserQuery;

/**
* 
*/
public class FormQueryFavorites  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox list2 = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textQuery = new System.Windows.Forms.TextBox();
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTitle = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFileName = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butEdit;
    public UserQuery UserQueryCur = new UserQuery();
    private List<UserQuery> QueryList = new List<UserQuery>();
    /**
    * 
    */
    public FormQueryFavorites() throws Exception {
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQueryFavorites.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.list2 = new System.Windows.Forms.ListBox();
        this.textQuery = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textTitle = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textFileName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butEdit = new OpenDental.UI.Button();
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
        this.butOK.Location = new System.Drawing.Point(808, 560);
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
        this.butCancel.Location = new System.Drawing.Point(808, 596);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // list2
        //
        this.list2.Location = new System.Drawing.Point(32, 20);
        this.list2.Name = "list2";
        this.list2.Size = new System.Drawing.Size(186, 537);
        this.list2.TabIndex = 0;
        this.list2.DoubleClick += new System.EventHandler(this.list2_DoubleClick);
        this.list2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.list2_MouseDown);
        //
        // textQuery
        //
        this.textQuery.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textQuery.Location = new System.Drawing.Point(252, 90);
        this.textQuery.Multiline = true;
        this.textQuery.Name = "textQuery";
        this.textQuery.ReadOnly = true;
        this.textQuery.Size = new System.Drawing.Size(522, 467);
        this.textQuery.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(252, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 14);
        this.label1.TabIndex = 11;
        this.label1.Text = "Title";
        //
        // textTitle
        //
        this.textTitle.Location = new System.Drawing.Point(252, 36);
        this.textTitle.Name = "textTitle";
        this.textTitle.ReadOnly = true;
        this.textTitle.Size = new System.Drawing.Size(360, 20);
        this.textTitle.TabIndex = 12;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(252, 70);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 14);
        this.label2.TabIndex = 13;
        this.label2.Text = "Query";
        //
        // textFileName
        //
        this.textFileName.Location = new System.Drawing.Point(252, 600);
        this.textFileName.Name = "textFileName";
        this.textFileName.ReadOnly = true;
        this.textFileName.Size = new System.Drawing.Size(360, 20);
        this.textFileName.TabIndex = 16;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(252, 580);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(271, 14);
        this.label3.TabIndex = 15;
        this.label3.Text = "Save As File Name";
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
        this.butAdd.Location = new System.Drawing.Point(136, 596);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(82, 24);
        this.butAdd.TabIndex = 34;
        this.butAdd.Text = "&New";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butDelete.Location = new System.Drawing.Point(32, 596);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(82, 24);
        this.butDelete.TabIndex = 35;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Image = Resources.geteditPencil();
        this.butEdit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEdit.Location = new System.Drawing.Point(136, 566);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(82, 24);
        this.butEdit.TabIndex = 36;
        this.butEdit.Text = "Edit";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // FormQueryFavorites
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(900, 652);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textFileName);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textTitle);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textQuery);
        this.Controls.Add(this.list2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQueryFavorites";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Query Favorites";
        this.Load += new System.EventHandler(this.FormQueryFormulate_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formQueryFormulate_Load(Object sender, System.EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        QueryList = UserQueries.refresh();
        int tempIndex = list2.SelectedIndex;
        list2.Items.Clear();
        for (int i = 0;i < QueryList.Count;i++)
        {
            this.list2.Items.Add(QueryList[i].Description);
        }
        list2.SelectedIndex = tempIndex;
    }

    private void list2_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (list2.IndexFromPoint(e.X, e.Y) < 0)
        {
            return ;
        }
         
        //>list2.Items.Count){
        UserQueryCur = QueryList[list2.IndexFromPoint(e.X, e.Y)];
        textQuery.Text = UserQueryCur.QueryText;
        textTitle.Text = UserQueryCur.Description;
        textFileName.Text = UserQueryCur.FileName;
    }

    private void list2_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (list2.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = UserQueryCur.QueryText;
        DialogResult = DialogResult.OK;
    }

    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        if (list2.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        FormQueryEdit FormQE = new FormQueryEdit();
        FormQE.UserQueryCur = UserQueryCur;
        FormQE.IsNew = false;
        FormQE.ShowDialog();
        fillList();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormQueryEdit FormQE = new FormQueryEdit();
        FormQE.IsNew = true;
        FormQE.UserQueryCur = new UserQuery();
        FormQE.ShowDialog();
        if (FormQE.DialogResult == DialogResult.OK)
        {
            fillList();
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (list2.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete Item?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        UserQueries.Delete(UserQueryCur);
        list2.SelectedIndex = -1;
        fillList();
        textTitle.Text = "";
        textQuery.Text = "";
        textFileName.Text = "";
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (list2.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = UserQueryCur.QueryText;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


