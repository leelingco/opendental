//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReqAppt;
import OpenDental.FormReqStudentEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.ReqStudent;
import OpenDentBusiness.ReqStudents;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.SchoolCourses;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReqAppt  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridStudents;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Label label2 = new Label();
    private ComboBox comboCourse = new ComboBox();
    private Label label1 = new Label();
    private ComboBox comboClass = new ComboBox();
    private OpenDental.UI.ODGrid gridAttached;
    private OpenDental.UI.Button butRemove;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.ODGrid gridReqs;
    private OpenDental.UI.Button butOK;
    //private DataTable table;
    private List<Provider> StudentList = new List<Provider>();
    private DataTable ReqTable = new DataTable();
    private List<ReqStudent> reqsAttached = new List<ReqStudent>();
    public long AptNum = new long();
    private Label label3 = new Label();
    private ComboBox comboInstructor = new ComboBox();
    private boolean hasChanged = new boolean();
    public long PatNum = new long();
    /**
    * 
    */
    public FormReqAppt() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReqAppt.class);
        this.label2 = new System.Windows.Forms.Label();
        this.comboCourse = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.comboClass = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.comboInstructor = new System.Windows.Forms.ComboBox();
        this.butOK = new OpenDental.UI.Button();
        this.gridReqs = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butRemove = new OpenDental.UI.Button();
        this.gridAttached = new OpenDental.UI.ODGrid();
        this.gridStudents = new OpenDental.UI.ODGrid();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(566, 39);
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
        this.comboCourse.Location = new System.Drawing.Point(647, 39);
        this.comboCourse.Name = "comboCourse";
        this.comboCourse.Size = new System.Drawing.Size(234, 21);
        this.comboCourse.TabIndex = 21;
        this.comboCourse.SelectionChangeCommitted += new System.EventHandler(this.comboCourse_SelectionChangeCommitted);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(563, 12);
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
        this.comboClass.Location = new System.Drawing.Point(647, 12);
        this.comboClass.Name = "comboClass";
        this.comboClass.Size = new System.Drawing.Size(234, 21);
        this.comboClass.TabIndex = 19;
        this.comboClass.SelectionChangeCommitted += new System.EventHandler(this.comboClass_SelectionChangeCommitted);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(497, 66);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(146, 18);
        this.label3.TabIndex = 29;
        this.label3.Text = "Instructor";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboInstructor
        //
        this.comboInstructor.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboInstructor.FormattingEnabled = true;
        this.comboInstructor.Location = new System.Drawing.Point(647, 66);
        this.comboInstructor.Name = "comboInstructor";
        this.comboInstructor.Size = new System.Drawing.Size(234, 21);
        this.comboInstructor.TabIndex = 28;
        this.comboInstructor.SelectionChangeCommitted += new System.EventHandler(this.comboInstructor_SelectionChangeCommitted);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(806, 591);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 27;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridReqs
        //
        this.gridReqs.setHScrollVisible(false);
        this.gridReqs.Location = new System.Drawing.Point(223, 12);
        this.gridReqs.Name = "gridReqs";
        this.gridReqs.setScrollValue(0);
        this.gridReqs.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridReqs.Size = new System.Drawing.Size(268, 637);
        this.gridReqs.TabIndex = 26;
        this.gridReqs.setTitle("Requirements");
        this.gridReqs.setTranslationName("TableReqStudentMany");
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getdown();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(497, 273);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(90, 26);
        this.butAdd.TabIndex = 25;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butRemove
        //
        this.butRemove.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemove.setAutosize(true);
        this.butRemove.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemove.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemove.setCornerRadius(4F);
        this.butRemove.Image = Resources.getdeleteX();
        this.butRemove.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRemove.Location = new System.Drawing.Point(596, 273);
        this.butRemove.Name = "butRemove";
        this.butRemove.Size = new System.Drawing.Size(90, 26);
        this.butRemove.TabIndex = 24;
        this.butRemove.Text = "Remove";
        this.butRemove.Click += new System.EventHandler(this.butRemove_Click);
        //
        // gridAttached
        //
        this.gridAttached.setHScrollVisible(false);
        this.gridAttached.Location = new System.Drawing.Point(497, 305);
        this.gridAttached.Name = "gridAttached";
        this.gridAttached.setScrollValue(0);
        this.gridAttached.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAttached.Size = new System.Drawing.Size(384, 225);
        this.gridAttached.TabIndex = 23;
        this.gridAttached.setTitle("Currently Attached Requirements");
        this.gridAttached.setTranslationName("TableReqStudentMany");
        this.gridAttached.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridAttached.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAttached_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridStudents
        //
        this.gridStudents.setHScrollVisible(false);
        this.gridStudents.Location = new System.Drawing.Point(10, 12);
        this.gridStudents.Name = "gridStudents";
        this.gridStudents.setScrollValue(0);
        this.gridStudents.Size = new System.Drawing.Size(207, 637);
        this.gridStudents.TabIndex = 3;
        this.gridStudents.setTitle("Students");
        this.gridStudents.setTranslationName("TableReqStudentMany");
        this.gridStudents.CellClick = __MultiODGridClickEventHandler.combine(this.gridStudents.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridStudents_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(806, 623);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormReqAppt
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(893, 661);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.comboInstructor);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridReqs);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butRemove);
        this.Controls.Add(this.gridAttached);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboCourse);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboClass);
        this.Controls.Add(this.gridStudents);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReqAppt";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Student Requirements for Appointment";
        this.Load += new System.EventHandler(this.FormReqAppt_Load);
        this.ResumeLayout(false);
    }

    private void formReqAppt_Load(Object sender, EventArgs e) throws Exception {
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
         
        comboInstructor.Items.Add(Lan.g(this,"None"));
        comboInstructor.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboInstructor.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        //if(ProviderC.List[i].ProvNum==ReqCur.InstructorNum) {
        //	comboInstructor.SelectedIndex=i+1;
        //}
        fillStudents();
        fillReqs();
        reqsAttached = ReqStudents.getForAppt(AptNum);
        if (reqsAttached.Count > 0)
        {
            comboInstructor.SelectedIndex = Providers.GetIndex(reqsAttached[0].ProvNum) + 1;
        }
         
        //this will turn a -1 into a 0.
        fillAttached();
    }

    private void fillStudents() throws Exception {
        if (comboClass.SelectedIndex == -1)
        {
            return ;
        }
         
        long schoolClass = SchoolClasses.getList()[comboClass.SelectedIndex].SchoolClassNum;
        //int schoolCourse=SchoolCourses.List[comboCourse.SelectedIndex].SchoolCourseNum;
        StudentList = ReqStudents.getStudents(schoolClass);
        gridStudents.beginUpdate();
        gridStudents.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridStudents.getColumns().add(col);
        gridStudents.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < StudentList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(StudentList[i].LName + ", " + StudentList[i].FName);
            gridStudents.getRows().add(row);
        }
        gridStudents.endUpdate();
    }

    private void fillReqs() throws Exception {
        long schoolCourse = 0;
        if (comboCourse.SelectedIndex != -1)
        {
            schoolCourse = SchoolCourses.getList()[comboCourse.SelectedIndex].SchoolCourseNum;
        }
         
        long schoolClass = 0;
        if (comboClass.SelectedIndex != -1)
        {
            schoolClass = SchoolClasses.getList()[comboClass.SelectedIndex].SchoolClassNum;
        }
         
        gridReqs.beginUpdate();
        gridReqs.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridReqs.getColumns().add(col);
        gridReqs.getRows().Clear();
        if (gridStudents.getSelectedIndex() == -1)
        {
            gridReqs.endUpdate();
            return ;
        }
         
        ReqTable = ReqStudents.getForCourseClass(schoolCourse,schoolClass);
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ReqTable.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ReqTable.Rows[i]["Descript"].ToString());
            gridReqs.getRows().add(row);
        }
        gridReqs.endUpdate();
    }

    /**
    * All alterations to TableAttached should have been made
    */
    private void fillAttached() throws Exception {
        gridAttached.beginUpdate();
        gridAttached.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Student",130);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn("Descript",150);
        gridAttached.getColumns().add(col);
        col = new ODGridColumn("Completed",40);
        gridAttached.getColumns().add(col);
        gridAttached.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < reqsAttached.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Providers.GetAbbr(reqsAttached[i].ProvNum));
            row.getCells().Add(reqsAttached[i].Descript);
            if (reqsAttached[i].DateCompleted.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            gridAttached.getRows().add(row);
        }
        gridAttached.endUpdate();
    }

    private void gridAttached_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (hasChanged)
        {
            MsgBox.show(this,"Not allowed to edit individual requirements immediately after adding or removing.");
            return ;
        }
         
        FormReqStudentEdit FormRSE = new FormReqStudentEdit();
        FormRSE.ReqCur = reqsAttached[e.getRow()];
        FormRSE.ShowDialog();
        if (FormRSE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        reqsAttached = ReqStudents.getForAppt(AptNum);
        fillAttached();
    }

    private void gridStudents_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        fillReqs();
    }

    private void comboClass_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillStudents();
        fillReqs();
    }

    private void comboCourse_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillReqs();
    }

    private void comboInstructor_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < reqsAttached.Count;i++)
        {
            if (reqsAttached[i].DateCompleted.Year > 1880)
            {
                continue;
            }
             
            //don't alter instructor of completed reqs.
            if (comboInstructor.SelectedIndex == 0)
            {
                reqsAttached[i].InstructorNum = 0;
            }
            else
            {
                reqsAttached[i].InstructorNum = ProviderC.getListShort()[comboInstructor.SelectedIndex - 1].ProvNum;
            } 
        }
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (gridReqs.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one requirement from the list at the left first.");
            return ;
        }
         
        ReqStudent req;
        for (int i = 0;i < gridReqs.getSelectedIndices().Length;i++)
        {
            req = new ReqStudent();
            req.AptNum = AptNum;
            //req.DateCompleted
            req.Descript = ReqTable.Rows[gridReqs.getSelectedIndices()[i]]["Descript"].ToString();
            if (comboInstructor.SelectedIndex > 0)
            {
                req.InstructorNum = ProviderC.getListShort()[comboInstructor.SelectedIndex - 1].ProvNum;
            }
             
            req.PatNum = PatNum;
            req.ProvNum = StudentList[gridStudents.getSelectedIndex()].ProvNum;
            req.ReqNeededNum = PIn.Long(ReqTable.Rows[gridReqs.getSelectedIndices()[i]]["ReqNeededNum"].ToString());
            //req.ReqStudentNum=0 until synch on OK.
            req.SchoolCourseNum = SchoolCourses.getList()[comboCourse.SelectedIndex].SchoolCourseNum;
            reqsAttached.Add(req);
            hasChanged = true;
        }
        fillAttached();
    }

    private void butRemove_Click(Object sender, EventArgs e) throws Exception {
        if (gridAttached.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one requirement from the list below first.");
            return ;
        }
         
        for (int i = gridAttached.getSelectedIndices().Length - 1;i >= 0;i--)
        {
            //go backwards to remove from end of list
            reqsAttached.RemoveAt(gridAttached.getSelectedIndices()[i]);
            hasChanged = true;
        }
        fillAttached();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        ReqStudents.synchApt(reqsAttached,AptNum);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


