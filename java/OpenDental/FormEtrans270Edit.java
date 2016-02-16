//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.FormBenefitEdit;
import OpenDental.FormEtrans270EBraw;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.DTP271;
import OpenDentBusiness.EB271;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.X271;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEtrans270Edit  extends Form 
{

    public Etrans EtransCur;
    private Etrans EtransAck271;
    private String MessageText = new String();
    private String MessageTextAck = new String();
    //public bool IsNew;//this makes no sense.  A 270 will never be new.  Always created, saved, and sent ahead of time.
    /**
    * True if the 270 and response have just been created and are being viewed for the first time.
    */
    public boolean IsInitialResponse = new boolean();
    /**
    * The list of EB objects parsed from the 270.
    */
    private List<EB271> listEB = new List<EB271>();
    private List<DTP271> listDTP = new List<DTP271>();
    private X271 x271;
    public List<Benefit> benList = new List<Benefit>();
    private long PatPlanNum = new long();
    private long PlanNum = new long();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    private int headingPrintH = 0;
    private long SubNum = new long();
    public FormEtrans270Edit(long patPlanNum, long planNum, long subNum) throws Exception {
        initializeComponent();
        Lan.F(this);
        PatPlanNum = patPlanNum;
        PlanNum = planNum;
        SubNum = subNum;
    }

    private void formEtrans270Edit_Load(Object sender, EventArgs e) throws Exception {
        MessageText = EtransMessageTexts.getMessageText(EtransCur.EtransMessageTextNum);
        MessageTextAck = "";
        //textMessageText.Text=MessageText;
        textNote.Text = EtransCur.Note;
        EtransAck271 = Etranss.getEtrans(EtransCur.AckEtransNum);
        x271 = null;
        if (EtransAck271 != null)
        {
            MessageTextAck = EtransMessageTexts.getMessageText(EtransAck271.EtransMessageTextNum);
            //.Replace("~","~\r\n");
            if (EtransAck271.Etype == EtransType.BenefitResponse271)
            {
                x271 = new X271(MessageTextAck);
            }
             
        }
         
        listDTP = new List<DTP271>();
        if (x271 != null)
        {
            listDTP = x271.getListDtpSubscriber();
        }
         
        fillGridDates();
        createListOfBenefits();
        fillGrid();
        fillGridBen();
        if (IsInitialResponse)
        {
            selectForImport();
        }
         
    }

    private void formEtrans270Edit_Shown(Object sender, EventArgs e) throws Exception {
        //The 997, 999, or 277 would only exist for a failure.  A success would be a 271.
        if (EtransAck271 != null && (EtransAck271.Etype == EtransType.Acknowledge_997 || EtransAck271.Etype == EtransType.Acknowledge_999 || EtransAck271.Etype == EtransType.StatusNotify_277))
        {
            if (IsInitialResponse)
            {
                MessageBox.Show(EtransCur.Note);
            }
             
        }
         
    }

    private void radioModeElect_Click(Object sender, EventArgs e) throws Exception {
        butPrint.Visible = false;
        groupImport.Visible = true;
        butImport.Visible = true;
        labelImport.Visible = true;
        gridBen.Visible = true;
        gridMain.Height = gridBen.Top - gridMain.Top - 3;
        gridMain.Width = gridBen.Right - gridMain.Left;
        fillGrid();
    }

    private void radioModeMessage_Click(Object sender, EventArgs e) throws Exception {
        butPrint.Visible = true;
        groupImport.Visible = false;
        butImport.Visible = false;
        labelImport.Visible = false;
        gridBen.Visible = false;
        gridMain.Height = labelNote.Top - gridMain.Top;
        gridMain.Width = 760;
        //to fit on a piece of paper.
        butPrint.Location = new Point(gridMain.Right - butPrint.Width, butPrint.Location.Y);
        fillGrid();
    }

    private void selectForImport() throws Exception {
        for (int i = 0;i < listEB.Count;i++)
        {
            if (listEB[i].Benefitt != null)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void createListOfBenefits() throws Exception {
        listEB = new List<EB271>();
        if (x271 != null)
        {
            listEB = x271.GetListEB(radioInNetwork.Checked);
        }
         
    }

    private void fillGridDates() throws Exception {
        gridDates.beginUpdate();
        gridDates.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),150);
        gridDates.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Qualifier"),230);
        gridDates.getColumns().add(col);
        gridDates.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listDTP.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DTP271.GetDateStr(listDTP[i].Segment.Get(2), listDTP[i].Segment.Get(3)));
            row.getCells().Add(DTP271.GetQualifierDescript(listDTP[i].Segment.Get(1)));
            gridDates.getRows().add(row);
        }
        gridDates.endUpdate();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Response"),420);
        gridMain.getColumns().add(col);
        if (radioModeElect.Checked)
        {
            col = new ODGridColumn(Lan.g(this,"Import As Benefit"),420);
            gridMain.getColumns().add(col);
        }
         
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listEB.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listEB[i].GetDescription(radioModeMessage.Checked));
            if (radioModeElect.Checked)
            {
                if (listEB[i].Benefitt == null)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(listEB[i].Benefitt.ToString());
                } 
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() == 0)
        {
            //raw benefit
            FormEtrans270EBraw FormE = new FormEtrans270EBraw();
            FormE.EB271val = listEB[e.getRow()];
            FormE.ShowDialog();
        }
        else
        {
            //user can't make changes, so no need to refresh grid.
            //generated benefit
            if (listEB[e.getRow()].Benefitt == null)
            {
                //create new benefit
                listEB[e.getRow()].Benefitt = new Benefit();
                FormBenefitEdit FormB = new FormBenefitEdit(0, PlanNum);
                FormB.IsNew = true;
                FormB.BenCur = listEB[e.getRow()].Benefitt;
                FormB.ShowDialog();
                if (FormB.BenCur == null)
                {
                    //user deleted or cancelled
                    listEB[e.getRow()].Benefitt = null;
                }
                 
            }
            else
            {
                //edit existing benefit
                FormBenefitEdit FormB = new FormBenefitEdit(0, PlanNum);
                FormB.BenCur = listEB[e.getRow()].Benefitt;
                FormB.ShowDialog();
                if (FormB.BenCur == null)
                {
                    //user deleted
                    listEB[e.getRow()].Benefitt = null;
                }
                 
            } 
            fillGrid();
        } 
    }

    private void fillGridBen() throws Exception {
        gridBen.beginUpdate();
        gridBen.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",420);
        gridBen.getColumns().add(col);
        gridBen.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < benList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(benList[i].ToString());
            gridBen.getRows().add(row);
        }
        gridBen.endUpdate();
    }

    private void gridBen_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int benefitListI = benList.IndexOf(benList[e.getRow()]);
        FormBenefitEdit FormB = new FormBenefitEdit(0, PlanNum);
        FormB.BenCur = benList[e.getRow()];
        FormB.ShowDialog();
        if (FormB.BenCur == null)
        {
            //user deleted
            benList.RemoveAt(benefitListI);
        }
         
        fillGridBen();
    }

    private void radioInNetwork_Click(Object sender, EventArgs e) throws Exception {
        createListOfBenefits();
        fillGrid();
        selectForImport();
    }

    private void radioOutNetwork_Click(Object sender, EventArgs e) throws Exception {
        createListOfBenefits();
        fillGrid();
        selectForImport();
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (listEB[gridMain.getSelectedIndices()[i]].Benefitt == null)
            {
                MsgBox.show(this,"All selected rows must contain benefits to import.");
                return ;
            }
             
        }
        Benefit ben;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            ben = listEB[gridMain.getSelectedIndices()[i]].Benefitt;
            ben.PlanNum = PlanNum;
            benList.Add(ben);
        }
        fillGridBen();
    }

    private void butShowRequest_Click(Object sender, EventArgs e) throws Exception {
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(MessageText);
        msgbox.ShowDialog();
    }

    private void butShowResponse_Click(Object sender, EventArgs e) throws Exception {
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(MessageTextAck);
        msgbox.ShowDialog();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        //only visible in Message mode.
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 80);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.setPrinter(pd,PrintSituation.Default,EtransCur.PatNum,"Electronic benefit request from " + EtransCur.DateTimeTrans.ToShortDateString() + " printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 12, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Electronic Benefits Response");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            InsSub sub = InsSubs.GetSub(this.SubNum, new List<InsSub>());
            InsPlan plan = InsPlans.GetPlan(this.PlanNum, new List<InsPlan>());
            Patient subsc = Patients.getPat(sub.Subscriber);
            text = Lan.g(this,"Subscriber: ") + subsc.getNameFL();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            Carrier carrier = Carriers.getCarrier(plan.CarrierNum);
            if (carrier.CarrierNum != 0)
            {
                //not corrupted
                text = carrier.CarrierName;
                g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            }
             
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    /*
    		private void butShowResponseDeciph_Click(object sender,EventArgs e) {
    			if(!X12object.IsX12(MessageTextAck)) {
    				MessageBox.Show("Only works with 997's");
    				return;
    			}
    			X12object x12obj=new X12object(MessageTextAck);
    			if(!x12obj.Is997()) {
    				MessageBox.Show("Only works with 997's");
    				return;
    			}
    			X997 x997=new X997(MessageTextAck);
    			string display=x997.GetHumanReadable();
    			MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(display);
    			msgbox.ShowDialog();
    		}*/
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //This button is not visible if IsNew
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete entire request and response?"))
        {
            return ;
        }
         
        if (EtransAck271 != null)
        {
            EtransMessageTexts.delete(EtransAck271.EtransMessageTextNum);
            Etranss.delete(EtransAck271.EtransNum);
        }
         
        EtransMessageTexts.delete(EtransCur.EtransMessageTextNum);
        Etranss.delete(EtransCur.EtransNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        EtransCur.Note = textNote.Text;
        Etranss.update(EtransCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        //if(IsNew) {
        //	EtransMessageTexts.Delete(EtransCur.EtransMessageTextNum);
        //	Etranss.Delete(EtransCur.EtransNum);
        //}
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
        this.labelNote = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butShowResponse = new OpenDental.UI.Button();
        this.butShowRequest = new OpenDental.UI.Button();
        this.labelImport = new System.Windows.Forms.Label();
        this.butImport = new OpenDental.UI.Button();
        this.gridBen = new OpenDental.UI.ODGrid();
        this.gridDates = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupImport = new System.Windows.Forms.GroupBox();
        this.radioInNetwork = new System.Windows.Forms.RadioButton();
        this.radioOutNetwork = new System.Windows.Forms.RadioButton();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.radioModeElect = new System.Windows.Forms.RadioButton();
        this.radioModeMessage = new System.Windows.Forms.RadioButton();
        this.butPrint = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.groupImport.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // labelNote
        //
        this.labelNote.Location = new System.Drawing.Point(6, 576);
        this.labelNote.Name = "labelNote";
        this.labelNote.Size = new System.Drawing.Size(100, 17);
        this.labelNote.TabIndex = 15;
        this.labelNote.Text = "Note";
        this.labelNote.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(9, 596);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(355, 40);
        this.textNote.TabIndex = 14;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butShowResponse);
        this.groupBox2.Controls.Add(this.butShowRequest);
        this.groupBox2.Location = new System.Drawing.Point(705, 12);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(168, 49);
        this.groupBox2.TabIndex = 116;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Show Raw Message of...";
        //
        // butShowResponse
        //
        this.butShowResponse.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowResponse.setAutosize(true);
        this.butShowResponse.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowResponse.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowResponse.setCornerRadius(4F);
        this.butShowResponse.Location = new System.Drawing.Point(87, 19);
        this.butShowResponse.Name = "butShowResponse";
        this.butShowResponse.Size = new System.Drawing.Size(75, 24);
        this.butShowResponse.TabIndex = 116;
        this.butShowResponse.Text = "Response";
        this.butShowResponse.Click += new System.EventHandler(this.butShowResponse_Click);
        //
        // butShowRequest
        //
        this.butShowRequest.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowRequest.setAutosize(true);
        this.butShowRequest.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowRequest.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowRequest.setCornerRadius(4F);
        this.butShowRequest.Location = new System.Drawing.Point(6, 19);
        this.butShowRequest.Name = "butShowRequest";
        this.butShowRequest.Size = new System.Drawing.Size(75, 24);
        this.butShowRequest.TabIndex = 115;
        this.butShowRequest.Text = "Request";
        this.butShowRequest.Click += new System.EventHandler(this.butShowRequest_Click);
        //
        // labelImport
        //
        this.labelImport.Location = new System.Drawing.Point(295, 421);
        this.labelImport.Name = "labelImport";
        this.labelImport.Size = new System.Drawing.Size(133, 17);
        this.labelImport.TabIndex = 120;
        this.labelImport.Text = "Selected Benefits";
        this.labelImport.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Image = Resources.getdown();
        this.butImport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butImport.Location = new System.Drawing.Point(347, 394);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(81, 24);
        this.butImport.TabIndex = 119;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // gridBen
        //
        this.gridBen.setHScrollVisible(false);
        this.gridBen.Location = new System.Drawing.Point(432, 394);
        this.gridBen.Name = "gridBen";
        this.gridBen.setScrollValue(0);
        this.gridBen.Size = new System.Drawing.Size(441, 242);
        this.gridBen.TabIndex = 118;
        this.gridBen.setTitle("Current Benefits");
        this.gridBen.setTranslationName("FormEtrans270Edit");
        this.gridBen.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridBen.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridBen_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridDates
        //
        this.gridDates.setHScrollVisible(false);
        this.gridDates.Location = new System.Drawing.Point(9, 12);
        this.gridDates.Name = "gridDates";
        this.gridDates.setScrollValue(0);
        this.gridDates.Size = new System.Drawing.Size(407, 119);
        this.gridDates.TabIndex = 117;
        this.gridDates.setTitle("Dates");
        this.gridDates.setTranslationName("FormEtrans270Edit");
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(9, 137);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(864, 254);
        this.gridMain.TabIndex = 114;
        this.gridMain.setTitle("Response Benefit Information");
        this.gridMain.setTranslationName("FormEtrans270Edit");
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
        this.butDelete.Location = new System.Drawing.Point(9, 641);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 113;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(717, 641);
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
        this.butCancel.Location = new System.Drawing.Point(798, 641);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // groupImport
        //
        this.groupImport.Controls.Add(this.radioInNetwork);
        this.groupImport.Controls.Add(this.radioOutNetwork);
        this.groupImport.Location = new System.Drawing.Point(470, 73);
        this.groupImport.Name = "groupImport";
        this.groupImport.Size = new System.Drawing.Size(167, 58);
        this.groupImport.TabIndex = 124;
        this.groupImport.TabStop = false;
        this.groupImport.Text = "Mark for import if";
        //
        // radioInNetwork
        //
        this.radioInNetwork.Checked = true;
        this.radioInNetwork.Location = new System.Drawing.Point(12, 16);
        this.radioInNetwork.Name = "radioInNetwork";
        this.radioInNetwork.Size = new System.Drawing.Size(121, 18);
        this.radioInNetwork.TabIndex = 121;
        this.radioInNetwork.TabStop = true;
        this.radioInNetwork.Text = "In network";
        this.radioInNetwork.UseVisualStyleBackColor = true;
        this.radioInNetwork.Click += new System.EventHandler(this.radioInNetwork_Click);
        //
        // radioOutNetwork
        //
        this.radioOutNetwork.Location = new System.Drawing.Point(12, 35);
        this.radioOutNetwork.Name = "radioOutNetwork";
        this.radioOutNetwork.Size = new System.Drawing.Size(121, 18);
        this.radioOutNetwork.TabIndex = 122;
        this.radioOutNetwork.Text = "Out of network";
        this.radioOutNetwork.UseVisualStyleBackColor = true;
        this.radioOutNetwork.Click += new System.EventHandler(this.radioOutNetwork_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.radioModeElect);
        this.groupBox3.Controls.Add(this.radioModeMessage);
        this.groupBox3.Location = new System.Drawing.Point(470, 12);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(167, 58);
        this.groupBox3.TabIndex = 126;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Viewing Mode";
        //
        // radioModeElect
        //
        this.radioModeElect.Checked = true;
        this.radioModeElect.Location = new System.Drawing.Point(12, 16);
        this.radioModeElect.Name = "radioModeElect";
        this.radioModeElect.Size = new System.Drawing.Size(121, 18);
        this.radioModeElect.TabIndex = 121;
        this.radioModeElect.TabStop = true;
        this.radioModeElect.Text = "Electronic Import";
        this.radioModeElect.UseVisualStyleBackColor = true;
        this.radioModeElect.Click += new System.EventHandler(this.radioModeElect_Click);
        //
        // radioModeMessage
        //
        this.radioModeMessage.Location = new System.Drawing.Point(12, 35);
        this.radioModeMessage.Name = "radioModeMessage";
        this.radioModeMessage.Size = new System.Drawing.Size(121, 18);
        this.radioModeMessage.TabIndex = 122;
        this.radioModeMessage.Text = "Message Text";
        this.radioModeMessage.UseVisualStyleBackColor = true;
        this.radioModeMessage.Click += new System.EventHandler(this.radioModeMessage_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Location = new System.Drawing.Point(644, 106);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 24);
        this.butPrint.TabIndex = 127;
        this.butPrint.Text = "Print";
        this.butPrint.Visible = false;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormEtrans270Edit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(882, 674);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupImport);
        this.Controls.Add(this.labelImport);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.gridBen);
        this.Controls.Add(this.gridDates);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.labelNote);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEtrans270Edit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Electronic Benefit Request";
        this.Load += new System.EventHandler(this.FormEtrans270Edit_Load);
        this.Shown += new System.EventHandler(this.FormEtrans270Edit_Shown);
        this.groupBox2.ResumeLayout(false);
        this.groupImport.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label labelNote = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butShowRequest;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butShowResponse;
    private OpenDental.UI.ODGrid gridDates;
    private OpenDental.UI.ODGrid gridBen;
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.Label labelImport = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupImport = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioInNetwork = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioOutNetwork = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioModeElect = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioModeMessage = new System.Windows.Forms.RadioButton();
    private OpenDental.UI.Button butPrint;
}


