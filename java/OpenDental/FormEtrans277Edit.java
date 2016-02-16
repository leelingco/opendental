//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.X277;
import OpenDental.FormEtrans277Edit;

public class FormEtrans277Edit  extends Form 
{

    public Etrans EtransCur;
    private String MessageText = new String();
    private X277 x277;
    public FormEtrans277Edit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEtrans277Edit_Load(Object sender, EventArgs e) throws Exception {
        MessageText = EtransMessageTexts.getMessageText(EtransCur.EtransMessageTextNum);
        x277 = new X277(MessageText);
        fillHeader();
        fillGrid();
    }

    private void formEtrans277Edit_Resize(Object sender, EventArgs e) throws Exception {
        //This funciton is called before FormEtrans277Edit_Load() when using ShowDialog(). Therefore, x277 is null the first time FormEtrans277Edit_Resize() is called.
        if (x277 == null)
        {
            return ;
        }
         
        fillGrid();
    }

    private void fillHeader() throws Exception {
        //Set the title of the window to include he reponding entity type and name (i.e. payor delta, clearinghouse emdeon, etc...)
        Text += x277.getInformationSourceType() + " " + x277.getInformationSourceName();
        //Fill the textboxes in the upper portion of the window.
        textReceiptDate.Text = x277.getInformationSourceReceiptDate().ToShortDateString();
        textProcessDate.Text = x277.getInformationSourceProcessDate().ToShortDateString();
    }

    //textQuantityAccepted.Text=x277.GetQuantityAccepted().ToString();
    //textQuantityRejected.Text=x277.GetQuantityRejected().ToString();
    //textAmountAccepted.Text=x277.GetAmountAccepted().ToString("F");
    //textAmountRejected.Text=x277.GetAmountRejected().ToString("F");
    private void fillGrid() throws Exception {
        int numAccepted = 0;
        int numRejected = 0;
        double amountAccepted = 0;
        double amountRejected = 0;
        List<String> claimTrackingNumbers = x277.getClaimTrackingNumbers();
        //bool showInstBillType=false;
        boolean showServiceDateRange = false;
        for (int i = 0;i < claimTrackingNumbers.Count;i++)
        {
            String[] claimInfo = x277.GetClaimInfo(claimTrackingNumbers[i]);
            //if(claimInfo[5]!="") { //institutional type of bill
            //  showInstBillType=true;
            //}
            if (!StringSupport.equals(claimInfo[7], ""))
            {
                //service date end
                showServiceDateRange = true;
            }
             
        }
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        int variableWidth = this.Width - 2 * gridMain.Left;
        if (showServiceDateRange)
        {
            ;
            col = new ODGridColumn(Lan.g(this,"ServDateFrom"), serviceDateFromWidth, HorizontalAlignment.Center);
            gridMain.getColumns().add(col);
            variableWidth -= serviceDateFromWidth;
            ;
            col = new ODGridColumn(Lan.g(this,"ServDateTo"), serviceDateToWidth, HorizontalAlignment.Center);
            gridMain.getColumns().add(col);
            variableWidth -= serviceDateToWidth;
        }
        else
        {
            ;
            col = new ODGridColumn(Lan.g(this,"ServiceDate"), serviceDateWidth, HorizontalAlignment.Center);
            gridMain.getColumns().add(col);
            variableWidth -= serviceDateWidth;
        } 
        ;
        col = new ODGridColumn(Lan.g(this,"Amount"), amountWidth, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        variableWidth -= amountWidth;
        ;
        col = new ODGridColumn(Lan.g(this,"Status"), statusWidth, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        variableWidth -= statusWidth;
        ;
        ;
        ;
        ;
        variableWidth += -lnameWidth - fnameWidth - claimIdWidth - payorControlNumWidth;
        col = new ODGridColumn(Lan.g(this,"Reason"),variableWidth);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"LName"),lnameWidth);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"FName"),fnameWidth);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"ClaimIdentifier"),claimIdWidth);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"PayorControlNum"),payorControlNumWidth);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        for (int i = 0;i < claimTrackingNumbers.Count;i++)
        {
            String[] claimInfo = x277.GetClaimInfo(claimTrackingNumbers[i]);
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[6]));
            //service date start
            if (showServiceDateRange)
            {
                row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[7]));
            }
             
            //service date end
            String claimStatus = "";
            double claimAmount = PIn.Decimal(claimInfo[9]);
            if (StringSupport.equals(claimInfo[3], "A"))
            {
                claimStatus = "Accepted";
                numAccepted++;
                amountAccepted += claimAmount;
            }
            else if (StringSupport.equals(claimInfo[3], "R"))
            {
                claimStatus = "Rejected";
                numRejected++;
                amountRejected += claimAmount;
            }
              
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimAmount.ToString("F")));
            //amount
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimStatus));
            //status
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[8]));
            //reason
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[0]));
            //lname
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[1]));
            //fname
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimTrackingNumbers[i]));
            //claim identifier
            row.getCells().Add(new OpenDental.UI.ODGridCell(claimInfo[4]));
            //payor control number
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        textQuantityAccepted.Text = numAccepted.ToString();
        textQuantityRejected.Text = numRejected.ToString();
        textAmountAccepted.Text = amountAccepted.ToString("F");
        textAmountRejected.Text = amountRejected.ToString("F");
    }

    private void butRawMessage_Click(Object sender, EventArgs e) throws Exception {
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(MessageText);
        msgbox.ShowDialog();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEtrans277Edit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textQuantityAccepted = new System.Windows.Forms.TextBox();
        this.textQuantityRejected = new System.Windows.Forms.TextBox();
        this.textAmountAccepted = new System.Windows.Forms.TextBox();
        this.textAmountRejected = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textReceiptDate = new System.Windows.Forms.TextBox();
        this.textProcessDate = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butRawMessage = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(175, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(106, 20);
        this.label1.TabIndex = 128;
        this.label1.Text = "Quantity Accepted";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(175, 36);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(106, 20);
        this.label2.TabIndex = 129;
        this.label2.Text = "Quantity Rejected";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(373, 13);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(110, 20);
        this.label3.TabIndex = 130;
        this.label3.Text = "Amount Accepted";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(373, 36);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(110, 20);
        this.label4.TabIndex = 131;
        this.label4.Text = "Amount Rejected";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textQuantityAccepted
        //
        this.textQuantityAccepted.Location = new System.Drawing.Point(287, 13);
        this.textQuantityAccepted.Name = "textQuantityAccepted";
        this.textQuantityAccepted.ReadOnly = true;
        this.textQuantityAccepted.Size = new System.Drawing.Size(80, 20);
        this.textQuantityAccepted.TabIndex = 132;
        //
        // textQuantityRejected
        //
        this.textQuantityRejected.Location = new System.Drawing.Point(287, 36);
        this.textQuantityRejected.Name = "textQuantityRejected";
        this.textQuantityRejected.ReadOnly = true;
        this.textQuantityRejected.Size = new System.Drawing.Size(80, 20);
        this.textQuantityRejected.TabIndex = 133;
        //
        // textAmountAccepted
        //
        this.textAmountAccepted.Location = new System.Drawing.Point(489, 13);
        this.textAmountAccepted.Name = "textAmountAccepted";
        this.textAmountAccepted.ReadOnly = true;
        this.textAmountAccepted.Size = new System.Drawing.Size(80, 20);
        this.textAmountAccepted.TabIndex = 134;
        //
        // textAmountRejected
        //
        this.textAmountRejected.Location = new System.Drawing.Point(489, 36);
        this.textAmountRejected.Name = "textAmountRejected";
        this.textAmountRejected.ReadOnly = true;
        this.textAmountRejected.Size = new System.Drawing.Size(80, 20);
        this.textAmountRejected.TabIndex = 135;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 13);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(76, 20);
        this.label5.TabIndex = 136;
        this.label5.Text = "Receipt Date";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textReceiptDate
        //
        this.textReceiptDate.Location = new System.Drawing.Point(89, 13);
        this.textReceiptDate.Name = "textReceiptDate";
        this.textReceiptDate.ReadOnly = true;
        this.textReceiptDate.Size = new System.Drawing.Size(80, 20);
        this.textReceiptDate.TabIndex = 137;
        //
        // textProcessDate
        //
        this.textProcessDate.Location = new System.Drawing.Point(89, 36);
        this.textProcessDate.Name = "textProcessDate";
        this.textProcessDate.ReadOnly = true;
        this.textProcessDate.Size = new System.Drawing.Size(80, 20);
        this.textProcessDate.TabIndex = 139;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(6, 36);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(76, 20);
        this.label6.TabIndex = 138;
        this.label6.Text = "Process Date";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(9, 58);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(966, 613);
        this.gridMain.TabIndex = 114;
        this.gridMain.setTitle("Claim Status and Information");
        this.gridMain.setTranslationName("FormEtrans277Edit");
        //
        // butRawMessage
        //
        this.butRawMessage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRawMessage.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRawMessage.setAutosize(true);
        this.butRawMessage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRawMessage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRawMessage.setCornerRadius(4F);
        this.butRawMessage.Location = new System.Drawing.Point(875, 32);
        this.butRawMessage.Name = "butRawMessage";
        this.butRawMessage.Size = new System.Drawing.Size(100, 24);
        this.butRawMessage.TabIndex = 116;
        this.butRawMessage.Text = "Raw Message";
        this.butRawMessage.Click += new System.EventHandler(this.butRawMessage_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(900, 677);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormEtrans277Edit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(984, 709);
        this.Controls.Add(this.textProcessDate);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textReceiptDate);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textAmountRejected);
        this.Controls.Add(this.textAmountAccepted);
        this.Controls.Add(this.textQuantityRejected);
        this.Controls.Add(this.textQuantityAccepted);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butRawMessage);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(990, 734);
        this.Name = "FormEtrans277Edit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Claim Status Response From ";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormEtrans277Edit_Load);
        this.Resize += new System.EventHandler(this.FormEtrans277Edit_Resize);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butRawMessage;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textQuantityAccepted = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textQuantityRejected = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAmountAccepted = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAmountRejected = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReceiptDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcessDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
}


