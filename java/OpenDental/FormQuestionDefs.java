//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormQuestionDefEdit;
import OpenDental.FormQuestionDefs;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.QuestionDef;
import OpenDentBusiness.QuestionDefs;

/**
* 
*/
public class FormQuestionDefs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private Label label1 = new Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private QuestionDef[] QuestionList = new QuestionDef[]();
    /**
    * 
    */
    public FormQuestionDefs() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuestionDefs.class);
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.label1 = new System.Windows.Forms.Label();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(709, 50);
        this.label1.TabIndex = 8;
        this.label1.Text = "These are the questions that will show on the patient questionnaire.  You can saf" + "ely move or delete any questions without harming previously completed questionna" + "ires.";
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(740, 637);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(79, 26);
        this.butClose.TabIndex = 1;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butAdd.Location = new System.Drawing.Point(740, 462);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(740, 569);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(79, 26);
        this.butDown.TabIndex = 14;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(740, 537);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(79, 26);
        this.butUp.TabIndex = 13;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 62);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(706, 601);
        this.gridMain.TabIndex = 15;
        this.gridMain.setTitle("Questions");
        this.gridMain.setTranslationName("TableQuestionDefs");
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
        // FormQuestionDefs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(838, 675);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQuestionDefs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Questionnaire Setup";
        this.Load += new System.EventHandler(this.FormQuestionDefs_Load);
        this.ResumeLayout(false);
    }

    private void formQuestionDefs_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        QuestionList = QuestionDefs.refresh();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableQuestionDefs","Type"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQuestionDefs","Question"),570);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < QuestionList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Lan.g("enumQuestionType", QuestionList[i].QuestType.ToString()));
            row.getCells().Add(QuestionList[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormQuestionDefEdit FormQ = new FormQuestionDefEdit(QuestionList[e.getRow()]);
        FormQ.ShowDialog();
        if (FormQ.DialogResult != DialogResult.OK)
            return ;
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        QuestionDef def = new QuestionDef();
        def.ItemOrder = QuestionList.Length;
        FormQuestionDefEdit FormQ = new FormQuestionDefEdit(def);
        FormQ.IsNew = true;
        FormQ.ShowDialog();
        fillGrid();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        int selected = gridMain.getSelectedIndex();
        try
        {
            QuestionDefs.moveUp(selected,QuestionList);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        fillGrid();
        if (selected == 0)
        {
            gridMain.setSelected(0,true);
        }
        else
        {
            gridMain.setSelected(selected - 1,true);
        } 
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        int selected = gridMain.getSelectedIndex();
        try
        {
            QuestionDefs.moveDown(selected,QuestionList);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        fillGrid();
        if (selected == QuestionList.Length - 1)
        {
            gridMain.setSelected(selected,true);
        }
        else
        {
            gridMain.setSelected(selected + 1,true);
        } 
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


