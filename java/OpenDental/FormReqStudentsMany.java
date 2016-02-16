//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReqStudentOne;
import OpenDental.FormReqStudentsMany;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ReqStudents;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.SchoolCourses;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReqStudentsMany  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Label label2 = new Label();
    private ComboBox comboCourse = new ComboBox();
    private Label label1 = new Label();
    private ComboBox comboClass = new ComboBox();
    private DataTable table = new DataTable();
    /**
    * 
    */
    public FormReqStudentsMany() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReqStudentsMany.class);
        this.label2 = new System.Windows.Forms.Label();
        this.comboCourse = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.comboClass = new System.Windows.Forms.ComboBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(351, 39);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(77, 18);
        this.label2.TabIndex = 22;
        this.label2.Text = "Course";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCourse
        //
        this.comboCourse.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCourse.FormattingEnabled = true;
        this.comboCourse.Location = new System.Drawing.Point(432, 39);
        this.comboCourse.Name = "comboCourse";
        this.comboCourse.Size = new System.Drawing.Size(234, 21);
        this.comboCourse.TabIndex = 21;
        this.comboCourse.SelectionChangeCommitted += new System.EventHandler(this.comboCourse_SelectionChangeCommitted);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(348, 12);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(81, 18);
        this.label1.TabIndex = 20;
        this.label1.Text = "Class";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClass
        //
        this.comboClass.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClass.FormattingEnabled = true;
        this.comboClass.Location = new System.Drawing.Point(432, 12);
        this.comboClass.Name = "comboClass";
        this.comboClass.Size = new System.Drawing.Size(234, 21);
        this.comboClass.TabIndex = 19;
        this.comboClass.SelectionChangeCommitted += new System.EventHandler(this.comboClass_SelectionChangeCommitted);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(329, 637);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Student Requirements");
        this.gridMain.setTranslationName("TableReqStudentMany");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(591, 623);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormReqStudentsMany
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(689, 661);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboCourse);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboClass);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReqStudentsMany";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Student Requirements - Many";
        this.Load += new System.EventHandler(this.FormReqStudentsMany_Load);
        this.ResumeLayout(false);
    }

    private void formReqStudentsMany_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < SchoolClasses.getList().Length;i++)
        {
            comboClass.Items.Add(SchoolClasses.GetDescript(SchoolClasses.getList()[i]));
        }
        if (comboClass.Items.Count > 0)
        {
            comboClass.SelectedIndex = 0;
        }
         
        for (int i = 0;i < SchoolCourses.getList().Length;i++)
        {
            comboCourse.Items.Add(SchoolCourses.GetDescript(SchoolCourses.getList()[i]));
        }
        if (comboCourse.Items.Count > 0)
        {
            comboCourse.SelectedIndex = 0;
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (comboClass.SelectedIndex == -1 || comboCourse.SelectedIndex == -1)
        {
            return ;
        }
         
        long schoolClass = SchoolClasses.getList()[comboClass.SelectedIndex].SchoolClassNum;
        long schoolCourse = SchoolCourses.getList()[comboCourse.SelectedIndex].SchoolCourseNum;
        table = ReqStudents.refreshManyStudents(schoolClass,schoolCourse);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableReqStudentMany","Last"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentMany","First"),100);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g("TableReqStudentMany","Total"),50);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentMany","Done"),50);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["LName"].ToString());
            row.getCells().Add(table.Rows[i]["FName"].ToString());
            //row.Cells.Add(table.Rows[i]["totalreq"].ToString());
            row.getCells().Add(table.Rows[i]["donereq"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormReqStudentOne FormR = new FormReqStudentOne();
        FormR.ProvNum = PIn.Long(table.Rows[e.getRow()]["studentNum"].ToString());
        FormR.ShowDialog();
        fillGrid();
    }

    private void comboClass_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboCourse_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


