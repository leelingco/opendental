//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRecallEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Interval;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.YN;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormRecallsPat  extends Form 
{

    public long PatNum = new long();
    /**
    * This is just the list for the current patient.
    */
    private List<Recall> RecallList = new List<Recall>();
    private boolean IsPerio = new boolean();
    public FormRecallsPat() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formRecallsPat_Load(Object sender, EventArgs e) throws Exception {
        /*
        			//patient may or may not have existing recalls.
        			Recall recallCur=null;
        			for(int i=0;i<RecallList.Count;i++){
        				if(RecallList[i].PatNum==PatCur.PatNum){
        					recallCur=RecallList[i];
        				}
        			}*/
        //for testing purposes and because synchronization might have bugs, always synch here:
        //This might add a recall.
        //Recalls.Synch(PatNum);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Recalls.synch(PatNum);
        Recalls.synchScheduledApptFull(PatNum);
        RecallList = Recalls.getList(PatNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRecallsPat","Type"),90);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g("TableRecallsPat","Disabled"),60,HorizontalAlignment.Center);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","PreviousDate"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","Due Date"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","Sched Date"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","Interval"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","Status"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecallsPat","Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        OpenDental.UI.ODGridCell cell;
        IsPerio = false;
        butPerio.Text = Lan.g(this,"Set Perio");
        String cellStr = new String();
        for (int i = 0;i < RecallList.Count;i++)
        {
            if (PrefC.getLong(PrefName.RecallTypeSpecialPerio) == RecallList[i].RecallTypeNum)
            {
                IsPerio = true;
                butPerio.Text = Lan.g(this,"Set Prophy");
            }
             
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RecallTypes.GetDescription(RecallList[i].RecallTypeNum));
            //if(RecallList[i].IsDisabled){
            //	row.Cells.Add("X");
            //}
            //else{
            //	row.Cells.Add("");
            //}
            if (RecallList[i].DatePrevious.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(RecallList[i].DatePrevious.ToShortDateString());
            } 
            if (RecallList[i].DateDue.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                cell = new OpenDental.UI.ODGridCell(RecallList[i].DateDue.ToShortDateString());
                if (RecallList[i].DateDue < DateTime.Today)
                {
                    cell.setBold(YN.Yes);
                    cell.setColorText(Color.Firebrick);
                }
                 
                row.getCells().Add(cell);
            } 
            if (RecallList[i].DateScheduled.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(RecallList[i].DateScheduled.ToShortDateString());
            } 
            row.getCells().Add(RecallList[i].RecallInterval.ToString());
            row.getCells().Add(DefC.GetValue(DefCat.RecallUnschedStatus, RecallList[i].RecallStatus));
            cellStr = "";
            if (RecallList[i].IsDisabled)
            {
                cellStr += Lan.g(this,"Disabled");
            }
             
            if (RecallList[i].DisableUntilDate.Year > 1880)
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += Lan.g(this,"Disabled until ") + RecallList[i].DisableUntilDate.ToShortDateString();
            }
             
            if (RecallList[i].DisableUntilBalance > 0)
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += Lan.g(this,"Disabled until balance ") + RecallList[i].DisableUntilBalance.ToString("c");
            }
             
            if (!StringSupport.equals(RecallList[i].Note, ""))
            {
                if (!StringSupport.equals(cellStr, ""))
                {
                    cellStr += ", ";
                }
                 
                cellStr += RecallList[i].Note;
            }
             
            row.getCells().add(cellStr);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormRecallEdit FormR = new FormRecallEdit();
        FormR.RecallCur = RecallList[e.getRow()].Copy();
        FormR.ShowDialog();
        fillGrid();
    }

    private void butPerio_Click(Object sender, EventArgs e) throws Exception {
        //make sure we have both special types properly setup.
        if (!RecallTypes.perioAndProphyBothHaveTriggers())
        {
            MsgBox.show(this,"Prophy and Perio special recall types are not setup properly.  They must both exist, and they must both have a trigger.");
            return ;
        }
         
        if (IsPerio)
        {
            for (int i = 0;i < RecallList.Count;i++)
            {
                //change the perio types to prophy
                if (PrefC.getLong(PrefName.RecallTypeSpecialPerio) == RecallList[i].RecallTypeNum)
                {
                    RecallList[i].RecallTypeNum = PrefC.getLong(PrefName.RecallTypeSpecialProphy);
                    RecallList[i].RecallInterval = RecallTypes.getInterval(PrefC.getLong(PrefName.RecallTypeSpecialProphy));
                    //previous date will be reset below in synch, but probably won't change since similar triggers.
                    Recalls.Update(RecallList[i]);
                    break;
                }
                 
            }
        }
        else
        {
            boolean found = false;
            for (int i = 0;i < RecallList.Count;i++)
            {
                //change any prophy types to perio
                if (PrefC.getLong(PrefName.RecallTypeSpecialProphy) == RecallList[i].RecallTypeNum)
                {
                    RecallList[i].RecallTypeNum = PrefC.getLong(PrefName.RecallTypeSpecialPerio);
                    RecallList[i].RecallInterval = RecallTypes.getInterval(PrefC.getLong(PrefName.RecallTypeSpecialPerio));
                    //previous date will be reset below in synch, but probably won't change since similar triggers.
                    Recalls.Update(RecallList[i]);
                    found = true;
                    break;
                }
                 
            }
            //if none found, then add a perio
            if (!found)
            {
                Recall recall = new Recall();
                recall.PatNum = PatNum;
                recall.RecallInterval = RecallTypes.getInterval(PrefC.getLong(PrefName.RecallTypeSpecialPerio));
                recall.RecallTypeNum = PrefC.getLong(PrefName.RecallTypeSpecialPerio);
                Recalls.insert(recall);
            }
             
        } 
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        Recall recall = new Recall();
        recall.RecallTypeNum = 0;
        //user will have to pick
        recall.PatNum = PatNum;
        recall.RecallInterval = new Interval(0,0,6,0);
        FormRecallEdit FormRE = new FormRecallEdit();
        FormRE.IsNew = true;
        FormRE.RecallCur = recall;
        FormRE.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void formRecallsPat_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //check for duplicates that might cause a malfunction.
        int prophyCount = 0;
        for (int i = 0;i < RecallList.Count;i++)
        {
            if (RecallTypes.getProphyType() == RecallList[i].RecallTypeNum)
            {
                prophyCount++;
            }
             
            if (RecallTypes.getPerioType() == RecallList[i].RecallTypeNum)
            {
                prophyCount++;
            }
             
        }
        if (prophyCount > 1)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Multiple prophy and/or perio recalls detected.  A patient should have only one prophy or perio recall, and the calculations will not work correctly otherwise.  Continue anyway?"))
            {
                e.Cancel = true;
            }
             
        }
         
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
        this.butPerio = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butPerio
        //
        this.butPerio.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPerio.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPerio.setAutosize(true);
        this.butPerio.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPerio.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPerio.setCornerRadius(4F);
        this.butPerio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPerio.Location = new System.Drawing.Point(267, 396);
        this.butPerio.Name = "butPerio";
        this.butPerio.Size = new System.Drawing.Size(83, 24);
        this.butPerio.TabIndex = 35;
        this.butPerio.Text = "Set Perio";
        this.butPerio.Click += new System.EventHandler(this.butPerio_Click);
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
        this.butAdd.Location = new System.Drawing.Point(12, 396);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 34;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridMain.Size = new System.Drawing.Size(721, 370);
        this.gridMain.TabIndex = 33;
        this.gridMain.setTitle("Recalls");
        this.gridMain.setTranslationName("TableRecallsPat");
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
        this.butClose.Location = new System.Drawing.Point(658, 396);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormRecallsPat
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(758, 439);
        this.Controls.Add(this.butPerio);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormRecallsPat";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Recalls for Patient";
        this.Load += new System.EventHandler(this.FormRecallsPat_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormRecallsPat_FormClosing);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butPerio;
}


