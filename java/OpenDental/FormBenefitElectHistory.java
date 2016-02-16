//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEtrans270Edit;
import OpenDental.FormEtransEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormBenefitElectHistory  extends Form 
{

    private List<Etrans> list = new List<Etrans>();
    private long PlanNum = new long();
    private long PatPlanNum = new long();
    public List<Benefit> BenList = new List<Benefit>();
    private long SubNum = new long();
    public FormBenefitElectHistory(long planNum, long patPlanNum, long subNum) throws Exception {
        initializeComponent();
        Lan.F(this);
        PlanNum = planNum;
        PatPlanNum = patPlanNum;
        SubNum = subNum;
    }

    private void formBenefitElectHistory_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        list = Etranss.getList270ForPlan(PlanNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Response"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < list.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(list[i].DateTimeTrans.ToShortDateString());
            row.getCells().Add(list[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Etrans etrans = list[e.getRow()];
        if (etrans.Etype == EtransType.Eligibility_CA)
        {
            FormEtransEdit formETE = new FormEtransEdit();
            formETE.EtransCur = etrans;
            formETE.ShowDialog();
        }
        else
        {
            FormEtrans270Edit formE = new FormEtrans270Edit(PatPlanNum,PlanNum,SubNum);
            formE.EtransCur = etrans;
            formE.benList = BenList;
            formE.ShowDialog();
        } 
        fillGrid();
    }

    //private void butOK_Click(object sender,EventArgs e) {
    //	DialogResult=DialogResult.OK;
    //}
    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(482, 271);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Electronic Benefit Request History");
        this.gridMain.setTranslationName(null);
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
        this.butClose.Location = new System.Drawing.Point(419, 289);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormBenefitElectHistory
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(506, 325);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormBenefitElectHistory";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Electronic Benefit History";
        this.Load += new System.EventHandler(this.FormBenefitElectHistory_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
}


