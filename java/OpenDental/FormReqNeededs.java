//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReqNeededEdit;
import OpenDental.FormReqNeededs;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ReqNeeded;
import OpenDentBusiness.ReqNeededs;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.SchoolCourses;

/**
* 
*/
public class FormReqNeededs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private ComboBox comboClass = new ComboBox();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private ComboBox comboCourse = new ComboBox();
    private DataTable table = new DataTable();
    /**
    * 
    */
    public FormReqNeededs() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReqNeededs.class);
        this.label1 = new System.Windows.Forms.Label();
        this.comboClass = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboCourse = new System.Windows.Forms.ComboBox();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 7);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(81, 18);
        this.label1.TabIndex = 16;
        this.label1.Text = "Class";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClass
        //
        this.comboClass.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClass.FormattingEnabled = true;
        this.comboClass.Location = new System.Drawing.Point(106, 7);
        this.comboClass.Name = "comboClass";
        this.comboClass.Size = new System.Drawing.Size(234, 21);
        this.comboClass.TabIndex = 0;
        this.comboClass.SelectionChangeCommitted += new System.EventHandler(this.comboClass_SelectionChangeCommitted);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 34);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 18);
        this.label2.TabIndex = 18;
        this.label2.Text = "Course";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCourse
        //
        this.comboCourse.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCourse.FormattingEnabled = true;
        this.comboCourse.Location = new System.Drawing.Point(106, 34);
        this.comboCourse.Name = "comboCourse";
        this.comboCourse.Size = new System.Drawing.Size(234, 21);
        this.comboCourse.TabIndex = 17;
        this.comboCourse.SelectionChangeCommitted += new System.EventHandler(this.comboCourse_SelectionChangeCommitted);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(511, 490);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(82, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(16, 61);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(433, 593);
        this.gridMain.TabIndex = 13;
        this.gridMain.setTitle("Requirements Needed");
        this.gridMain.setTranslationName("TableRequirementsNeeded");
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
        this.butClose.Location = new System.Drawing.Point(511, 628);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(82, 26);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormReqNeededs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(614, 670);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.comboCourse);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.comboClass);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReqNeededs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Requirements Needed";
        this.Load += new System.EventHandler(this.FormRequirementsNeeded_Load);
        this.ResumeLayout(false);
    }

    private void formRequirementsNeeded_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < SchoolClasses.getList().Length;i++)
        {
            //comboClass.Items.Add(Lan.g(this,"All"));
            //comboClass.SelectedIndex=0;
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
         
        long selected = 0;
        if (gridMain.getSelectedIndex() != -1)
        {
            selected = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["ReqNeededNum"].ToString());
        }
         
        int scroll = gridMain.getScrollValue();
        long schoolClass = SchoolClasses.getList()[comboClass.SelectedIndex].SchoolClassNum;
        long schoolCourse = SchoolCourses.getList()[comboCourse.SelectedIndex].SchoolCourseNum;
        table = ReqNeededs.refresh(schoolClass,schoolCourse);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        //col=new ODGridColumn(Lan.g("TableRequirementsNeeded","Class"),100);
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn(Lan.g("TableRequirementsNeeded","Course"),100);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableRequirementsNeeded","Description"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //row.Cells.Add(SchoolClasses.GetDescript(PIn.PInt(table.Rows[i]["SchoolClassNum"].ToString())));
            //row.Cells.Add(SchoolCourses.GetCourseID(PIn.PInt(table.Rows[i]["SchoolCourseNum"].ToString())));
            row.getCells().Add(table.Rows[i]["Descript"].ToString());
            //row.Tag
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["ReqNeededNum"].ToString() == selected.ToString())
            {
                gridMain.setSelected(i,true);
                break;
            }
             
        }
        gridMain.setScrollValue(scroll);
    }

    private void comboClass_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboCourse_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (comboClass.SelectedIndex == -1 || comboCourse.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a Class and Course first.");
            return ;
        }
         
        FormReqNeededEdit FormR = new FormReqNeededEdit();
        FormR.ReqCur = new ReqNeeded();
        FormR.ReqCur.SchoolClassNum = SchoolClasses.getList()[comboClass.SelectedIndex].SchoolClassNum;
        FormR.ReqCur.SchoolCourseNum = SchoolCourses.getList()[comboCourse.SelectedIndex].SchoolCourseNum;
        FormR.IsNew = true;
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
        gridMain.scrollToEnd();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["ReqNeededNum"].ToString() == FormR.ReqCur.ReqNeededNum.ToString())
            {
                gridMain.setSelected(i,true);
                break;
            }
             
        }
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormReqNeededEdit FormR = new FormReqNeededEdit();
        FormR.ReqCur = ReqNeededs.GetReq(PIn.Long(table.Rows[e.getRow()]["ReqNeededNum"].ToString()));
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    /*private void butSynch_Click(object sender,EventArgs e) {
    			if(comboClass.SelectedIndex==-1 || comboCourse.SelectedIndex==-1) {
    				MsgBox.Show(this,"Please select a Class and Course first.");
    				return;
    			}
    			ReqNeededs.Synch(SchoolClasses.List[comboClass.SelectedIndex].SchoolClassNum,
    				SchoolCourses.List[comboCourse.SelectedIndex].SchoolCourseNum);
    			MsgBox.Show(this,"Done.");
    		}*/
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


