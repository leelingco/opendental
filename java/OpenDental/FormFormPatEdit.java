//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:10 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormFormPatEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ContrMultInput;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.FormPat;
import OpenDentBusiness.FormPats;
import OpenDentBusiness.Question;
import OpenDentBusiness.QuestionDef;
import OpenDentBusiness.QuestionDefs;
import OpenDentBusiness.Questions;
import OpenDentBusiness.QuestionType;
import OpenDentBusiness.YN;

/**
* Summary description for FormBasicTemplate.
*/
public class FormFormPatEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    //private Question[] QuestionList;
    private QuestionDef[] QuestionDefList = new QuestionDef[]();
    private ContrMultInput multInput;
    public FormPat FormPatCur;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butDelete;
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormFormPatEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        //PatNum=patNum;
        //FormPatCur=formPatCur.Clone();
        multInput.IsQuestionnaire = true;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFormPatEdit.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.multInput = new OpenDental.UI.ContrMultInput();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(757, 607);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Questions");
        this.gridMain.setTranslationName("TableQuestions");
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
        // multInput
        //
        this.multInput.Location = new System.Drawing.Point(12, 12);
        this.multInput.Name = "multInput";
        this.multInput.Size = new System.Drawing.Size(757, 640);
        this.multInput.TabIndex = 2;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(795, 585);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(795, 626);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 625);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(88, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormFormPatEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(893, 671);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.multInput);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFormPatEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Questionnaire";
        this.Shown += new System.EventHandler(this.FormFormPatEdit_Shown);
        this.Load += new System.EventHandler(this.FormFormPatEdit_Load);
        this.ResumeLayout(false);
    }

    private void formFormPatEdit_Load(Object sender, EventArgs e) throws Exception {
        QuestionDefList = QuestionDefs.refresh();
        if (IsNew)
        {
            gridMain.Visible = false;
            butDelete.Visible = false;
            for (int i = 0;i < QuestionDefList.Length;i++)
            {
                //only gets filled once on startup, and not saved until OK.
                if (QuestionDefList[i].QuestType == QuestionType.FreeformText)
                {
                    multInput.AddInputItem(QuestionDefList[i].Description, FieldValueType.String, "");
                }
                else if (QuestionDefList[i].QuestType == QuestionType.YesNoUnknown)
                {
                    multInput.AddInputItem(QuestionDefList[i].Description, FieldValueType.YesNoUnknown, YN.Unknown);
                }
                  
            }
        }
        else
        {
            butOK.Visible = false;
            butCancel.Text = Lan.g(this,"Close");
            multInput.Visible = false;
            //Gets filled repeatedly.  Saved each time user double clicks on a row.  Only the answer can be edited.
            fillGrid();
        } 
    }

    /*QuestionDefList=QuestionDefs.Refresh();
    			QuestionList=Questions.Refresh(PatNum);
    			if(QuestionList.Length==0){
    				IsNew=true;
    				gridMain.Visible=false;
    				butDelete.Visible=false;
    				//only gets filled once on startup, and not saved until OK.
    				for(int i=0;i<QuestionDefList.Length;i++){
    					if(QuestionDefList[i].QuestType==QuestionType.FreeformText){
    						multInput.AddInputItem(QuestionDefList[i].Description,FieldValueType.String,"");
    					}
    					else if(QuestionDefList[i].QuestType==QuestionType.YesNoUnknown) {
    						multInput.AddInputItem(QuestionDefList[i].Description,FieldValueType.YesNoUnknown,YN.Unknown);
    					}
    				}
    			}
    			else{
    				IsNew=false;
    				butOK.Visible=false;
    				butCancel.Text=Lan.g(this,"Close");
    				multInput.Visible=false;
    				//Gets filled repeatedly.  Saved each time user double clicks on a row.  Only the answer can be edited.
    				FillGrid();
    			}*/
    private void formFormPatEdit_Shown(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            if (QuestionDefList.Length == 0)
            {
                MsgBox.show(this,"Go to Setup | Questionniare to setup questions for all patients.");
            }
             
        }
         
    }

    private void fillGrid() throws Exception {
        //QuestionList=Questions.Refresh(PatNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableQuestions","Question"),370);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableQuestions","Answer"),370);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < FormPatCur.QuestionList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(FormPatCur.QuestionList[i].Description);
            row.getCells().Add(FormPatCur.QuestionList[i].Answer);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //only visible if editing existing quesionnaire.
        InputBox input = new InputBox(FormPatCur.QuestionList[e.getRow()].Description);
        input.textResult.Text = FormPatCur.QuestionList[e.getRow()].Answer;
        input.ShowDialog();
        if (input.DialogResult == DialogResult.OK)
        {
            FormPatCur.QuestionList[e.getRow()].Answer = input.textResult.Text;
            Questions.Update(FormPatCur.QuestionList[e.getRow()]);
        }
         
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //only visible if editing existing quesionnaire.
        if (!MsgBox.show(this,true,"Delete this questionnaire?"))
        {
            return ;
        }
         
        FormPats.delete(FormPatCur.FormPatNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (QuestionDefList.Length == 0)
        {
            MsgBox.show(this,"No questions to save.");
            return ;
        }
         
        //only visible if IsNew
        FormPats.insert(FormPatCur);
        Question quest;
        ArrayList ALval = new ArrayList();
        for (int i = 0;i < QuestionDefList.Length;i++)
        {
            quest = new Question();
            quest.PatNum = FormPatCur.PatNum;
            quest.ItemOrder = QuestionDefList[i].ItemOrder;
            quest.Description = QuestionDefList[i].Description;
            if (QuestionDefList[i].QuestType == QuestionType.FreeformText)
            {
                ALval = multInput.getCurrentValues(i);
                if (ALval.Count > 0)
                {
                    quest.Answer = ALval[0].ToString();
                }
                 
            }
            else //else it will just be blank
            if (QuestionDefList[i].QuestType == QuestionType.YesNoUnknown)
            {
                quest.Answer = Lan.g("enumYN", multInput.getCurrentValues(i)[0].ToString());
            }
              
            quest.FormPatNum = FormPatCur.FormPatNum;
            Questions.insert(quest);
        }
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            Close();
        } 
    }

}


