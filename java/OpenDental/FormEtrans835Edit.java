//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.X835;
import OpenDental.FormEtrans835Edit;

public class FormEtrans835Edit  extends Form 
{

    public Etrans EtransCur;
    private String MessageText = new String();
    private X835 x835;
    public FormEtrans835Edit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEtrans835Edit_Load(Object sender, EventArgs e) throws Exception {
        MessageText = EtransMessageTexts.getMessageText(EtransCur.EtransMessageTextNum);
        x835 = new X835(MessageText);
        fillAll();
    }

    private void formEtrans835Edit_Resize(Object sender, EventArgs e) throws Exception {
        //This funciton is called before FormEtrans835Edit_Load() when using ShowDialog(). Therefore, x835 is null the first time FormEtrans835Edit_Resize() is called.
        if (x835 == null)
        {
            return ;
        }
         
        fillClaimDetails();
    }

    //Because the grid columns change size depending on the form size.
    /**
    * Reads the X12 835 text in the MessageText variable and displays the information in this form.
    */
    private void fillAll() throws Exception {
        //*835 has 3 parts: Table 1 (header), Table 2 (claim level details, one CLP segment for each claim), and Table 3 (PLB: provider/check level details).
        fillHeader();
        //Table 1
        fillClaimDetails();
        //Table 2
        fillProviderAdjustmentDetails();
    }

    //Table 3
    //The following concepts should each be addressed as development progresses.
    //*837 CLM01 -> 835 CLP01 (even for split claims)
    //*Reassociation (pg. 19): 835 TRN = Reassociation Key Segment. See TRN02.
    //*SVC02-(CAS03+CAS06+CAS09+CAS12+CAS15+CAS18)=SVC03
    //When the service payment information loop is not present, then: CLP03-(CAS03+CAS06+CAS09+CAS12+CAS15+CAS18)=CLP04
    //*Otherwise, CAS must also be considered from the service adjustment segment.
    //*Reassociation (pg. 20): Use the trace # in TRN02 and the company ID number in TRN03 to uniquely identify the claim payment/data.
    //*Institutional (pg. 23): CAS reason code 78 requires special handling.
    //*Advance payments (pg. 23): in PLB segment with adjustment reason code PI. Can be yearly or monthly.
    //*Bundled procs (pg. 27): have the original proc listed in SV06. Use Line Item Control Number to identify the original proc line.
    //*Line Item Control Number (pgs. 28 & 36): REF*6B or LX01 from 837 -> 2110REF in 835. We are not using REF*6B in 837, so we will get LX01 back in 835.
    //*Predetermination (pg. 28): Identified by claim status code 25 in CLP02. Claim adjustment reason code is 101.
    //*Claim reversals (pg. 30): Identified by code 22 in CLP02. The original claim adjustment codes can be found in CAS01 to negate the original claim.
    //Use CLP07 to identify the original claim, or if different, get the original ref num from REF02 of 2040REF*F8.
    //*Interest and Prompt Payment Discounts (pg. 31): Located in AMT segments with qualifiers I (interest) and D8 (discount). Found at claim and provider/check level.
    //Not part of AR, but part of deposit. Handle this situation by using claimprocs with 2 new status, one for interest and one for discount? Would allow reports, deposits, and claim checks to work as is.
    //*Capitation and related payments or adjustments (pg. 34 & 52): Not many of our customers use capitation, so this will probably be our last concern.
    //*Claim splits (pg. 36): MIA or MOA segments will exist to indicate the claim was split.
    //*Service Line Splits (pg. 42): LQ segment with LQ01=HE and LQ02=N123 indicate the procedure was split.
    //*PPOs (pg. 47): 2100CAS or 2110CAS will contain the value CO (Contractual Obligation) in CAS01. The PPO name is reported in REF02 of the Other Claim Related Information segment REF*CE.
    /**
    * Reads the X12 835 text in the MessageText variable and displays the information from Table 1 (Header).
    */
    private void fillHeader() throws Exception {
        //Payer information
        textPayerName.Text = x835.getPayerName();
        textPayerID.Text = x835.getPayerID();
        textPayerAddress1.Text = x835.getPayerAddress1();
        textPayerCity.Text = x835.getPayerCityName();
        textPayerState.Text = x835.getPayerState();
        textPayerZip.Text = x835.getPayerZip();
        textPayerContactInfo.Text = x835.getPayerContactInfo();
        //Payee information
        textPayeeName.Text = x835.getPayeeName();
        textPayeeIdType.Text = x835.getPayeeIdType();
        textPayeeID.Text = x835.getPayeeId();
        //Payment information
        textTransHandlingDesc.Text = x835.getTransactionHandlingCodeDescription();
        textPaymentMethod.Text = x835.getPaymentMethodDescription();
        textPaymentAmount.Text = x835.getPaymentAmount();
        textCreditOrDebit.Text = x835.getCreditDebit();
        textAcctNumEndingIn.Text = x835.getAccountNumReceivingShort();
        DateTime dateEffective = x835.getDateEffective();
        if (dateEffective.Year > 1880)
        {
            textDateEffective.Text = dateEffective.ToShortDateString();
        }
         
        textCheckNumOrRefNum.Text = x835.getTransactionReferenceNumber();
    }

    /**
    * Reads the X12 835 text in the MessageText variable and displays the information from Table 2 (Detail).
    */
    private void fillClaimDetails() throws Exception {
        List<String> claimTrackingNumbers = x835.getClaimTrackingNumbers();
        gridClaimDetails.beginUpdate();
        gridClaimDetails.getColumns().Clear();
        ODGridColumn col;
        int variableWidth = this.Width - 2 * gridClaimDetails.Left;
        ;
        col = new ODGridColumn(Lan.g(this,"Amount"), amountWidth, HorizontalAlignment.Center);
        gridClaimDetails.getColumns().add(col);
    }

    //variableWidth-=amountWidth;
    //const int statusWidth=54;
    //col=new ODGridColumn(Lan.g(this,"Status"),statusWidth,HorizontalAlignment.Center);
    //gridMain.Columns.Add(col);
    //variableWidth-=statusWidth;
    //const int lnameWidth=150;
    //const int fnameWidth=100;
    //const int claimIdWidth=100;
    //const int payorControlNumWidth=126;
    //variableWidth+=-lnameWidth-fnameWidth-claimIdWidth-payorControlNumWidth;
    //col=new ODGridColumn(Lan.g(this,"Reason"),variableWidth);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g(this,"LName"),lnameWidth);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g(this,"FName"),fnameWidth);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g(this,"ClaimIdentifier"),claimIdWidth);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g(this,"PayorControlNum"),payorControlNumWidth);
    //gridMain.Columns.Add(col);
    //gridMain.Rows.Clear();
    //for(int i=0;i<claimTrackingNumbers.Count;i++) {
    //  string[] claimInfo=x277.GetClaimInfo(claimTrackingNumbers[i]);
    //  ODGridRow row=new ODGridRow();
    //  row.Cells.Add(new ODGridCell(claimInfo[6]));//service date start
    //  if(showServiceDateRange) {
    //    row.Cells.Add(new ODGridCell(claimInfo[7]));//service date end
    //  }
    //  string claimStatus="";
    //  decimal claimAmount=PIn.Decimal(claimInfo[9]);
    //  if(claimInfo[3]=="A") {
    //    claimStatus="Accepted";
    //    numAccepted++;
    //    amountAccepted+=claimAmount;
    //  }
    //  else if(claimInfo[3]=="R") {
    //    claimStatus="Rejected";
    //    numRejected++;
    //    amountRejected+=claimAmount;
    //  }
    //  row.Cells.Add(new ODGridCell(claimAmount.ToString("F")));//amount
    //  row.Cells.Add(new ODGridCell(claimStatus));//status
    //  row.Cells.Add(new ODGridCell(claimInfo[8]));//reason
    //  row.Cells.Add(new ODGridCell(claimInfo[0]));//lname
    //  row.Cells.Add(new ODGridCell(claimInfo[1]));//fname
    //  row.Cells.Add(new ODGridCell(claimTrackingNumbers[i]));//claim identifier
    //  row.Cells.Add(new ODGridCell(claimInfo[4]));//payor control number
    //  gridMain.Rows.Add(row);
    //}
    //gridMain.EndUpdate();
    //textQuantityAccepted.Text=numAccepted.ToString();
    //textQuantityRejected.Text=numRejected.ToString();
    //textAmountAccepted.Text=amountAccepted.ToString("F");
    //textAmountRejected.Text=amountRejected.ToString("F");
    /**
    * Reads the X12 835 text in the MessageText variable and displays the information from Table 3 (Summary).
    */
    private void fillProviderAdjustmentDetails() throws Exception {
        gridProviderAdjustments.beginUpdate();
        gridProviderAdjustments.getColumns().Clear();
        gridProviderAdjustments.getColumns().add(new ODGridColumn("NPI", 100, HorizontalAlignment.Center));
        gridProviderAdjustments.getColumns().add(new ODGridColumn("FiscalPeriod", 100, HorizontalAlignment.Center));
        gridProviderAdjustments.getColumns().add(new ODGridColumn("Reason", 440, HorizontalAlignment.Left));
        gridProviderAdjustments.getColumns().add(new ODGridColumn("ReasonCode", 100, HorizontalAlignment.Center));
        gridProviderAdjustments.getColumns().add(new ODGridColumn("RefIdent", 100, HorizontalAlignment.Left));
        gridProviderAdjustments.getColumns().add(new ODGridColumn("Amount", 100, HorizontalAlignment.Right));
        gridProviderAdjustments.endUpdate();
        gridProviderAdjustments.beginUpdate();
        gridProviderAdjustments.getRows().Clear();
        List<String[]> providerAdjustments = x835.getProviderLevelAdjustments();
        for (int i = 0;i < providerAdjustments.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][0]));
            //NPI
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][1]));
            //FiscalPeriod
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][2]));
            //Reason
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][3]));
            //ReasonCode
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][4]));
            //RefIdent
            row.getCells().Add(new OpenDental.UI.ODGridCell(providerAdjustments[i][5]));
            //Amount
            gridProviderAdjustments.getRows().add(row);
        }
        gridProviderAdjustments.endUpdate();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEtrans835Edit.class);
        this.label5 = new System.Windows.Forms.Label();
        this.textTransHandlingDesc = new System.Windows.Forms.TextBox();
        this.textPaymentMethod = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textPaymentAmount = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textCreditOrDebit = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textAcctNumEndingIn = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textDateEffective = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textCheckNumOrRefNum = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textPayerName = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textPayerID = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textPayerAddress1 = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textPayerCity = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.textPayerState = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.textPayerZip = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textPayerContactInfo = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.textPayeeName = new System.Windows.Forms.TextBox();
        this.label15 = new System.Windows.Forms.Label();
        this.textPayeeIdType = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textPayeeID = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.butRawMessage = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridProviderAdjustments = new OpenDental.UI.ODGrid();
        this.gridClaimDetails = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(472, 1);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(114, 20);
        this.label5.TabIndex = 136;
        this.label5.Text = "Trans Handling Desc";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTransHandlingDesc
        //
        this.textTransHandlingDesc.Location = new System.Drawing.Point(586, 1);
        this.textTransHandlingDesc.Name = "textTransHandlingDesc";
        this.textTransHandlingDesc.ReadOnly = true;
        this.textTransHandlingDesc.Size = new System.Drawing.Size(387, 20);
        this.textTransHandlingDesc.TabIndex = 137;
        //
        // textPaymentMethod
        //
        this.textPaymentMethod.Location = new System.Drawing.Point(586, 21);
        this.textPaymentMethod.Name = "textPaymentMethod";
        this.textPaymentMethod.ReadOnly = true;
        this.textPaymentMethod.Size = new System.Drawing.Size(387, 20);
        this.textPaymentMethod.TabIndex = 139;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(472, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(114, 20);
        this.label1.TabIndex = 138;
        this.label1.Text = "Payment Method";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPaymentAmount
        //
        this.textPaymentAmount.Location = new System.Drawing.Point(586, 61);
        this.textPaymentAmount.Name = "textPaymentAmount";
        this.textPaymentAmount.ReadOnly = true;
        this.textPaymentAmount.Size = new System.Drawing.Size(90, 20);
        this.textPaymentAmount.TabIndex = 141;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(472, 61);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(114, 20);
        this.label2.TabIndex = 140;
        this.label2.Text = "Payment Amount";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCreditOrDebit
        //
        this.textCreditOrDebit.Location = new System.Drawing.Point(586, 81);
        this.textCreditOrDebit.Name = "textCreditOrDebit";
        this.textCreditOrDebit.ReadOnly = true;
        this.textCreditOrDebit.Size = new System.Drawing.Size(90, 20);
        this.textCreditOrDebit.TabIndex = 143;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(472, 81);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(114, 20);
        this.label3.TabIndex = 142;
        this.label3.Text = "Credit or Debit";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAcctNumEndingIn
        //
        this.textAcctNumEndingIn.Location = new System.Drawing.Point(586, 101);
        this.textAcctNumEndingIn.Name = "textAcctNumEndingIn";
        this.textAcctNumEndingIn.ReadOnly = true;
        this.textAcctNumEndingIn.Size = new System.Drawing.Size(90, 20);
        this.textAcctNumEndingIn.TabIndex = 145;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(472, 101);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(114, 20);
        this.label4.TabIndex = 144;
        this.label4.Text = "Acct Num Ending In";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateEffective
        //
        this.textDateEffective.Location = new System.Drawing.Point(586, 121);
        this.textDateEffective.Name = "textDateEffective";
        this.textDateEffective.ReadOnly = true;
        this.textDateEffective.Size = new System.Drawing.Size(90, 20);
        this.textDateEffective.TabIndex = 147;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(472, 121);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(114, 20);
        this.label6.TabIndex = 146;
        this.label6.Text = "Date Effective";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCheckNumOrRefNum
        //
        this.textCheckNumOrRefNum.Location = new System.Drawing.Point(586, 41);
        this.textCheckNumOrRefNum.Name = "textCheckNumOrRefNum";
        this.textCheckNumOrRefNum.ReadOnly = true;
        this.textCheckNumOrRefNum.Size = new System.Drawing.Size(387, 20);
        this.textCheckNumOrRefNum.TabIndex = 149;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(459, 41);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(127, 20);
        this.label7.TabIndex = 148;
        this.label7.Text = "Check# or Reference#";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerName
        //
        this.textPayerName.Location = new System.Drawing.Point(100, 1);
        this.textPayerName.Name = "textPayerName";
        this.textPayerName.ReadOnly = true;
        this.textPayerName.Size = new System.Drawing.Size(357, 20);
        this.textPayerName.TabIndex = 151;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(0, 1);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(100, 20);
        this.label8.TabIndex = 150;
        this.label8.Text = "Payer Name";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerID
        //
        this.textPayerID.Location = new System.Drawing.Point(100, 21);
        this.textPayerID.Name = "textPayerID";
        this.textPayerID.ReadOnly = true;
        this.textPayerID.Size = new System.Drawing.Size(90, 20);
        this.textPayerID.TabIndex = 153;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(0, 21);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(100, 20);
        this.label9.TabIndex = 152;
        this.label9.Text = "Payer ID";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerAddress1
        //
        this.textPayerAddress1.Location = new System.Drawing.Point(100, 41);
        this.textPayerAddress1.Name = "textPayerAddress1";
        this.textPayerAddress1.ReadOnly = true;
        this.textPayerAddress1.Size = new System.Drawing.Size(357, 20);
        this.textPayerAddress1.TabIndex = 155;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(0, 41);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(100, 20);
        this.label10.TabIndex = 154;
        this.label10.Text = "Payer Address";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerCity
        //
        this.textPayerCity.Location = new System.Drawing.Point(100, 61);
        this.textPayerCity.Name = "textPayerCity";
        this.textPayerCity.ReadOnly = true;
        this.textPayerCity.Size = new System.Drawing.Size(357, 20);
        this.textPayerCity.TabIndex = 157;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(0, 61);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(100, 20);
        this.label11.TabIndex = 156;
        this.label11.Text = "Payer City";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerState
        //
        this.textPayerState.Location = new System.Drawing.Point(100, 81);
        this.textPayerState.Name = "textPayerState";
        this.textPayerState.ReadOnly = true;
        this.textPayerState.Size = new System.Drawing.Size(90, 20);
        this.textPayerState.TabIndex = 159;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(0, 81);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(100, 20);
        this.label12.TabIndex = 158;
        this.label12.Text = "Payer State";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerZip
        //
        this.textPayerZip.Location = new System.Drawing.Point(100, 101);
        this.textPayerZip.Name = "textPayerZip";
        this.textPayerZip.ReadOnly = true;
        this.textPayerZip.Size = new System.Drawing.Size(90, 20);
        this.textPayerZip.TabIndex = 161;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(0, 101);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(100, 20);
        this.label13.TabIndex = 160;
        this.label13.Text = "Payer Zip";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayerContactInfo
        //
        this.textPayerContactInfo.Location = new System.Drawing.Point(100, 121);
        this.textPayerContactInfo.Name = "textPayerContactInfo";
        this.textPayerContactInfo.ReadOnly = true;
        this.textPayerContactInfo.Size = new System.Drawing.Size(357, 20);
        this.textPayerContactInfo.TabIndex = 163;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(0, 121);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(100, 20);
        this.label14.TabIndex = 162;
        this.label14.Text = "Payer Contact Info";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayeeName
        //
        this.textPayeeName.Location = new System.Drawing.Point(100, 147);
        this.textPayeeName.Name = "textPayeeName";
        this.textPayeeName.ReadOnly = true;
        this.textPayeeName.Size = new System.Drawing.Size(357, 20);
        this.textPayeeName.TabIndex = 165;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(0, 147);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(100, 20);
        this.label15.TabIndex = 164;
        this.label15.Text = "Payee Name";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayeeIdType
        //
        this.textPayeeIdType.Location = new System.Drawing.Point(100, 167);
        this.textPayeeIdType.Name = "textPayeeIdType";
        this.textPayeeIdType.ReadOnly = true;
        this.textPayeeIdType.Size = new System.Drawing.Size(90, 20);
        this.textPayeeIdType.TabIndex = 167;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(0, 167);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(100, 20);
        this.label16.TabIndex = 166;
        this.label16.Text = "Payee ID Type";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPayeeID
        //
        this.textPayeeID.Location = new System.Drawing.Point(100, 187);
        this.textPayeeID.Name = "textPayeeID";
        this.textPayeeID.ReadOnly = true;
        this.textPayeeID.Size = new System.Drawing.Size(90, 20);
        this.textPayeeID.TabIndex = 169;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(0, 187);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(100, 20);
        this.label17.TabIndex = 168;
        this.label17.Text = "Payee ID";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butRawMessage
        //
        this.butRawMessage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRawMessage.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRawMessage.setAutosize(true);
        this.butRawMessage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRawMessage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRawMessage.setCornerRadius(4F);
        this.butRawMessage.Location = new System.Drawing.Point(891, 187);
        this.butRawMessage.Name = "butRawMessage";
        this.butRawMessage.Size = new System.Drawing.Size(82, 24);
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
        this.butOK.Location = new System.Drawing.Point(898, 675);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridProviderAdjustments
        //
        this.gridProviderAdjustments.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProviderAdjustments.setHScrollVisible(false);
        this.gridProviderAdjustments.Location = new System.Drawing.Point(9, 213);
        this.gridProviderAdjustments.Name = "gridProviderAdjustments";
        this.gridProviderAdjustments.setScrollValue(0);
        this.gridProviderAdjustments.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridProviderAdjustments.Size = new System.Drawing.Size(964, 92);
        this.gridProviderAdjustments.TabIndex = 170;
        this.gridProviderAdjustments.TabStop = false;
        this.gridProviderAdjustments.setTitle("Provider Adjustments");
        this.gridProviderAdjustments.setTranslationName("FormEtrans835Edit");
        //
        // gridClaimDetails
        //
        this.gridClaimDetails.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridClaimDetails.setHScrollVisible(false);
        this.gridClaimDetails.Location = new System.Drawing.Point(9, 311);
        this.gridClaimDetails.Name = "gridClaimDetails";
        this.gridClaimDetails.setScrollValue(0);
        this.gridClaimDetails.Size = new System.Drawing.Size(964, 358);
        this.gridClaimDetails.TabIndex = 0;
        this.gridClaimDetails.TabStop = false;
        this.gridClaimDetails.setTitle("Claim Details");
        this.gridClaimDetails.setTranslationName("FormEtrans835Edit");
        //
        // FormEtrans835Edit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 707);
        this.Controls.Add(this.gridProviderAdjustments);
        this.Controls.Add(this.textPayeeID);
        this.Controls.Add(this.label17);
        this.Controls.Add(this.textPayeeIdType);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.textPayeeName);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.textPayerContactInfo);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.textPayerZip);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.textPayerState);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.textPayerCity);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textPayerAddress1);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.textPayerID);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textPayerName);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textCheckNumOrRefNum);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textDateEffective);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textAcctNumEndingIn);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCreditOrDebit);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textPaymentAmount);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textPaymentMethod);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textTransHandlingDesc);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butRawMessage);
        this.Controls.Add(this.gridClaimDetails);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(990, 734);
        this.Name = "FormEtrans835Edit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Electronic EOB";
        this.Load += new System.EventHandler(this.FormEtrans835Edit_Load);
        this.Resize += new System.EventHandler(this.FormEtrans835Edit_Resize);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridClaimDetails;
    private OpenDental.UI.Button butRawMessage;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTransHandlingDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPaymentMethod = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPaymentAmount = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCreditOrDebit = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAcctNumEndingIn = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateEffective = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCheckNumOrRefNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerAddress1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayerContactInfo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayeeName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayeeIdType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPayeeID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridProviderAdjustments;
}


