//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.Bridges.PayConnect;
import OpenDental.CreditCardUtils;
import OpenDental.Lan;
import OpenDental.MagstripCardParseException;
import OpenDental.MagstripCardParser;
import OpenDental.MigraDocHelper;
import OpenDental.MsgBox;
import OpenDental.PayConnectService.transResponse;
import OpenDental.PayConnectService.transType;
import OpenDental.PrinterL;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Payment;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDental.FormPayConnect;

public class FormPayConnect  extends Form 
{

    private Payment PaymentCur;
    private Patient PatCur;
    private String amountInit = new String();
    private transResponse response;
    private MagstripCardParser parser = null;
    private String receiptStr = new String();
    private transType trantype = transType.SALE;
    private CreditCard CreditCardCur;
    /**
    * Can handle CreditCard being null.
    */
    public FormPayConnect(Payment payment, Patient pat, String amount, CreditCard creditCard) throws Exception {
        initializeComponent();
        Lan.F(this);
        PaymentCur = payment;
        PatCur = pat;
        amountInit = amount;
        receiptStr = "";
        CreditCardCur = creditCard;
    }

    private void formPayConnect_Load(Object sender, EventArgs e) throws Exception {
        if (CreditCardCur != null)
        {
            //User selected a credit card from drop down.
            if (!StringSupport.equals(CreditCardCur.CCNumberMasked, ""))
            {
                textCardNumber.Text = CreditCardCur.CCNumberMasked;
            }
             
            if (CreditCardCur.CCExpiration != null && CreditCardCur.CCExpiration.Year > 2005)
            {
                textExpDate.Text = CreditCardCur.CCExpiration.ToString("MMyy");
            }
             
            if (!StringSupport.equals(CreditCardCur.Zip, ""))
            {
                textZipCode.Text = CreditCardCur.Zip;
            }
            else
            {
                textZipCode.Text = PatCur.Zip;
            } 
        }
        else
        {
            this.textZipCode.Text = PatCur.Zip;
        } 
        this.textNameOnCard.Text = PatCur.getNameFL();
        this.textAmount.Text = amountInit;
    }

    private void radioSale_Click(Object sender, EventArgs e) throws Exception {
        radioSale.Checked = true;
        radioAuthorization.Checked = false;
        radioVoid.Checked = false;
        radioReturn.Checked = false;
        textRefNumber.Visible = false;
        labelRefNumber.Visible = false;
        trantype = transType.SALE;
        textCardNumber.Focus();
    }

    //Usually transaction type is chosen before card number is entered, but textCardNumber box must be selected in order for card swipe to work.
    private void radioAuthorization_Click(Object sender, EventArgs e) throws Exception {
        radioSale.Checked = false;
        radioAuthorization.Checked = true;
        radioVoid.Checked = false;
        radioReturn.Checked = false;
        textRefNumber.Visible = false;
        labelRefNumber.Visible = false;
        trantype = transType.AUTH;
        textCardNumber.Focus();
    }

    //Usually transaction type is chosen before card number is entered, but textCardNumber box must be selected in order for card swipe to work.
    private void radioVoid_Click(Object sender, EventArgs e) throws Exception {
        radioSale.Checked = false;
        radioAuthorization.Checked = false;
        radioVoid.Checked = true;
        radioReturn.Checked = false;
        textRefNumber.Visible = true;
        labelRefNumber.Visible = true;
        trantype = transType.VOID;
        textCardNumber.Focus();
    }

    //Usually transaction type is chosen before card number is entered, but textCardNumber box must be selected in order for card swipe to work.
    private void radioReturn_Click(Object sender, EventArgs e) throws Exception {
        radioSale.Checked = false;
        radioAuthorization.Checked = false;
        radioVoid.Checked = false;
        radioReturn.Checked = true;
        textRefNumber.Visible = true;
        labelRefNumber.Visible = true;
        trantype = transType.RETURN;
        textCardNumber.Focus();
    }

    //Usually transaction type is chosen before card number is entered, but textCardNumber box must be selected in order for card swipe to work.
    private void textCardNumber_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        if (String.IsNullOrEmpty(textCardNumber.Text))
        {
            return ;
        }
         
        if (textCardNumber.Text.StartsWith("%") && textCardNumber.Text.EndsWith("?") && e.KeyChar == 13)
        {
            e.Handled = true;
            ParseSwipedCard(textCardNumber.Text);
        }
         
    }

    private void parseSwipedCard(String data) throws Exception {
        clear();
        try
        {
            parser = new MagstripCardParser(data);
        }
        catch (MagstripCardParseException __dummyCatchVar0)
        {
            MessageBox.Show(this, "Could not read card, please try again.", "Card Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        if (parser != null)
        {
            textCardNumber.Text = parser.getAccountNumber();
            textExpDate.Text = parser.getExpirationMonth().ToString().PadLeft(2, '0') + (parser.getExpirationYear() % 100).ToString().PadLeft(2, '0');
            textNameOnCard.Text = parser.getFirstName() + " " + parser.getLastName();
            GetNextControl(textNameOnCard, true).Focus();
        }
         
    }

    //Move forward to the next control in the tab order.
    private void clear() throws Exception {
        textCardNumber.Text = "";
        textExpDate.Text = "";
        textNameOnCard.Text = "";
        textSecurityCode.Text = "";
        textZipCode.Text = "";
    }

    /**
    * Only call after the form is closed and the DialogResult is DialogResult.OK.
    */
    public String getAmountCharged() throws Exception {
        return textAmount.Text;
    }

    /**
    * Only call after the form is closed and the DialogResult is DialogResult.OK.
    */
    public transResponse getResponse() throws Exception {
        return response;
    }

    /**
    * Only call after the form is closed and the DialogResult is DialogResult.OK.
    */
    public String getReceiptStr() throws Exception {
        return receiptStr;
    }

    public transType getTranType() throws Exception {
        return trantype;
    }

    private boolean verifyData(RefSupport<int> expYear, RefSupport<int> expMonth) throws Exception {
        expYear.setValue(0);
        expMonth.setValue(0);
        // Consider adding more advanced verification methods using PayConnect validation requests.
        if (textCardNumber.Text.Trim().Length < 5)
        {
            MsgBox.show(this,"Invalid Card Number.");
            return false;
        }
         
        if (Regex.IsMatch(textExpDate.Text, "^\\d\\d[/\\- ]\\d\\d$"))
        {
            //08/07 or 08-07 or 08 07
            expYear.setValue(Convert.ToInt32("20" + textExpDate.Text.Substring(3, 2)));
            expMonth.setValue(Convert.ToInt32(textExpDate.Text.Substring(0, 2)));
        }
        else if (Regex.IsMatch(textExpDate.Text, "^\\d{4}$"))
        {
            //0807
            expYear.setValue(Convert.ToInt32("20" + textExpDate.Text.Substring(2, 2)));
            expMonth.setValue(Convert.ToInt32(textExpDate.Text.Substring(0, 2)));
        }
        else
        {
            MsgBox.show(this,"Expiration format invalid.");
            return false;
        }  
        if (StringSupport.equals(textNameOnCard.Text.Trim(), ""))
        {
            MsgBox.show(this,"Name On Card required.");
            return false;
        }
         
        if (!Regex.IsMatch(textAmount.Text, "^[0-9]+$") && !Regex.IsMatch(textAmount.Text, "^[0-9]*\\.[0-9]+$"))
        {
            MsgBox.show(this,"Invalid amount.");
            return false;
        }
         
        if ((trantype == transType.VOID || trantype == transType.RETURN) && StringSupport.equals(textRefNumber.Text, ""))
        {
            MsgBox.show(this,"Ref Number required.");
            return false;
        }
         
        return true;
    }

    private String buildReceiptString(OpenDental.PayConnectService.creditCardRequest request, transResponse response) throws Exception {
        String result = "";
        int xmin = 0;
        int xleft = xmin;
        int xright = 15;
        int xmax = 37;
        result += Environment.NewLine;
        //Print header/Practice information
        String practiceTitle = PrefC.getString(PrefName.PracticeTitle);
        if (practiceTitle.Length > 0)
        {
            result += practiceTitle + Environment.NewLine;
        }
         
        String practiceAddress = PrefC.getString(PrefName.PracticeAddress);
        if (practiceAddress.Length > 0)
        {
            result += practiceAddress + Environment.NewLine;
        }
         
        String practiceAddress2 = PrefC.getString(PrefName.PracticeAddress2);
        if (practiceAddress2.Length > 0)
        {
            result += practiceAddress2 + Environment.NewLine;
        }
         
        String practiceCity = PrefC.getString(PrefName.PracticeCity);
        String practiceState = PrefC.getString(PrefName.PracticeST);
        String practiceZip = PrefC.getString(PrefName.PracticeZip);
        if (practiceCity.Length > 0 || practiceState.Length > 0 || practiceZip.Length > 0)
        {
            String cityStateZip = practiceCity + " " + practiceState + " " + practiceZip;
            result += cityStateZip + Environment.NewLine;
        }
         
        String practicePhone = PrefC.getString(PrefName.PracticePhone);
        if (practicePhone.Length == 10 && (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") || CultureInfo.CurrentCulture.Name.EndsWith("CA")))
        {
            //Canadian. en-CA or fr-CA
            result += "(" + practicePhone.Substring(0, 3) + ")" + practicePhone.Substring(3, 3) + "-" + practicePhone.Substring(6) + Environment.NewLine;
        }
        else if (practicePhone.Length > 0)
        {
            result += practicePhone + Environment.NewLine;
        }
          
        result += Environment.NewLine;
        //Print body
        result += "Date".PadRight(xright - xleft, '.') + DateTime.Now.ToString() + Environment.NewLine;
        result += Environment.NewLine;
        result += "Trans Type".PadRight(xright - xleft, '.') + request.getTransType().ToString() + Environment.NewLine;
        result += Environment.NewLine;
        result += "Transaction #".PadRight(xright - xleft, '.') + response.getRefNumber() + Environment.NewLine;
        result += "Name".PadRight(xright - xleft, '.') + request.getNameOnCard() + Environment.NewLine;
        result += "Account".PadRight(xright - xleft, '.');
        for (int i = 0;i < request.getCardNumber().Length - 4;i++)
        {
            result += "*";
        }
        result += request.getCardNumber().Substring(request.getCardNumber().Length - 4) + Environment.NewLine;
        //last 4 digits of card number only.
        result += "Exp Date".PadRight(xright - xleft, '.') + request.getExpiration().getmonth().ToString().PadLeft(2, '0') + (request.getExpiration().getyear() % 100) + Environment.NewLine;
        result += "Card Type".PadRight(xright - xleft, '.') + CreditCardUtils.getType(request.getCardNumber()) + Environment.NewLine;
        result += "Entry".PadRight(xright - xleft, '.') + (StringSupport.equals(request.getMagData(), "") ? "Manual" : "Swiped") + Environment.NewLine;
        result += "Auth Code".PadRight(xright - xleft, '.') + response.getAuthCode() + Environment.NewLine;
        result += "Result".PadRight(xright - xleft, '.') + response.getStatus().getdescription() + Environment.NewLine;
        if (response.getMessages() != null)
        {
            String label = "Message";
            for (Object __dummyForeachVar0 : response.getMessages())
            {
                String m = (String)__dummyForeachVar0;
                result += label.PadRight(xright - xleft, '.') + m + Environment.NewLine;
                label = "";
            }
        }
         
        result += Environment.NewLine + Environment.NewLine + Environment.NewLine;
        result += "Total Amt".PadRight(xright - xleft, '.') + request.getAmount() + Environment.NewLine;
        result += Environment.NewLine + Environment.NewLine + Environment.NewLine;
        result += "I agree to pay the above total amount according to my card issuer/bank agreement." + Environment.NewLine;
        result += Environment.NewLine + Environment.NewLine + Environment.NewLine + Environment.NewLine + Environment.NewLine;
        result += "Signature X".PadRight(xmax - xleft, '_');
        return result;
    }

    private void printReceipt(String receiptStr) throws Exception {
        String[] receiptLines = receiptStr.Split(new String[]{ Environment.NewLine }, StringSplitOptions.None);
        MigraDoc.DocumentObjectModel.Document doc = new MigraDoc.DocumentObjectModel.Document();
        doc.DefaultPageSetup.PageWidth = Unit.FromInch(3.0);
        doc.DefaultPageSetup.PageHeight = Unit.FromInch(0.181 * receiptLines.Length + 0.56);
        //enough to print receipt text plus 9/16 inch (0.56) extra space at bottom.
        doc.DefaultPageSetup.TopMargin = Unit.FromInch(0.25);
        doc.DefaultPageSetup.LeftMargin = Unit.FromInch(0.25);
        doc.DefaultPageSetup.RightMargin = Unit.FromInch(0.25);
        MigraDoc.DocumentObjectModel.Font bodyFontx = MigraDocHelper.CreateFont(8, false);
        bodyFontx.Name = FontFamily.GenericMonospace.Name;
        MigraDoc.DocumentObjectModel.Section section = doc.AddSection();
        Paragraph par = section.AddParagraph();
        ParagraphFormat parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Left;
        parformat.Font = bodyFontx;
        par.Format = parformat;
        par.AddFormattedText(receiptStr, bodyFontx);
        MigraDoc.Rendering.Printing.MigraDocPrintDocument printdoc = new MigraDoc.Rendering.Printing.MigraDocPrintDocument();
        MigraDoc.Rendering.DocumentRenderer renderer = new MigraDoc.Rendering.DocumentRenderer(doc);
        renderer.PrepareDocument();
        printdoc.Renderer = renderer;
        //we might want to surround some of this with a try-catch
        if (PrinterL.SetPrinter(pd2, PrintSituation.Receipt, PatCur.PatNum, "PayConnect receipt printed"))
        {
            printdoc.PrinterSettings = pd2.PrinterSettings;
            printdoc.Print();
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        int expYear = new int();
        int expMonth = new int();
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        boolean boolVar___0 = !verifyData(refVar___0,refVar___1);
        expYear = refVar___0.getValue();
        expMonth = refVar___1.getValue();
        if (boolVar___0)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        String refNumber = "";
        if (trantype == transType.VOID || trantype == transType.RETURN)
        {
            refNumber = textRefNumber.Text;
        }
         
        OpenDental.PayConnectService.creditCardRequest request = PayConnect.BuildSaleRequest(Convert.ToDecimal(textAmount.Text), textCardNumber.Text, expYear, expMonth, textNameOnCard.Text, textSecurityCode.Text, textZipCode.Text, (parser != null ? parser.getTrack2() : null), trantype, refNumber);
        response = PayConnect.ProcessCreditCard(request);
        if (trantype == transType.SALE && response.getStatus().getcode() == 0)
        {
            //Only print a receipt if transaction is an approved SALE.
            receiptStr = buildReceiptString(request,response);
            printReceipt(receiptStr);
        }
         
        if (response == null || response.getStatus().getcode() != 0)
        {
            //error in transaction
            Cursor = Cursors.Default;
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayConnect.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textAmount = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textCardNumber = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textExpDate = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textNameOnCard = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textSecurityCode = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textZipCode = new System.Windows.Forms.TextBox();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.radioSale = new System.Windows.Forms.RadioButton();
        this.radioAuthorization = new System.Windows.Forms.RadioButton();
        this.radioVoid = new System.Windows.Forms.RadioButton();
        this.radioReturn = new System.Windows.Forms.RadioButton();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textRefNumber = new System.Windows.Forms.TextBox();
        this.labelRefNumber = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(253, 150);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Amount";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(253, 169);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(139, 20);
        this.textAmount.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(15, 66);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 16);
        this.label3.TabIndex = 8;
        this.label3.Text = "Card Number";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textCardNumber
        //
        this.textCardNumber.Location = new System.Drawing.Point(15, 85);
        this.textCardNumber.Name = "textCardNumber";
        this.textCardNumber.Size = new System.Drawing.Size(217, 20);
        this.textCardNumber.TabIndex = 1;
        this.textCardNumber.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textCardNumber_KeyPress);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(15, 108);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(150, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "Expiration (MMYY)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textExpDate
        //
        this.textExpDate.Location = new System.Drawing.Point(15, 127);
        this.textExpDate.Name = "textExpDate";
        this.textExpDate.Size = new System.Drawing.Size(136, 20);
        this.textExpDate.TabIndex = 2;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(15, 150);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 12;
        this.label5.Text = "Name On Card";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textNameOnCard
        //
        this.textNameOnCard.Location = new System.Drawing.Point(15, 169);
        this.textNameOnCard.Name = "textNameOnCard";
        this.textNameOnCard.Size = new System.Drawing.Size(217, 20);
        this.textNameOnCard.TabIndex = 3;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(253, 66);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(100, 16);
        this.label6.TabIndex = 14;
        this.label6.Text = "Security Code";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textSecurityCode
        //
        this.textSecurityCode.Location = new System.Drawing.Point(253, 85);
        this.textSecurityCode.Name = "textSecurityCode";
        this.textSecurityCode.Size = new System.Drawing.Size(71, 20);
        this.textSecurityCode.TabIndex = 4;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(253, 108);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(100, 16);
        this.label7.TabIndex = 16;
        this.label7.Text = "Zip Code";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textZipCode
        //
        this.textZipCode.Location = new System.Drawing.Point(253, 127);
        this.textZipCode.Name = "textZipCode";
        this.textZipCode.Size = new System.Drawing.Size(139, 20);
        this.textZipCode.TabIndex = 5;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(308, 217);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 17;
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
        this.butCancel.Location = new System.Drawing.Point(308, 258);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 18;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // radioSale
        //
        this.radioSale.AutoSize = true;
        this.radioSale.Checked = true;
        this.radioSale.Location = new System.Drawing.Point(6, 19);
        this.radioSale.Name = "radioSale";
        this.radioSale.Size = new System.Drawing.Size(46, 17);
        this.radioSale.TabIndex = 19;
        this.radioSale.TabStop = true;
        this.radioSale.Text = "Sale";
        this.radioSale.UseVisualStyleBackColor = true;
        this.radioSale.Click += new System.EventHandler(this.radioSale_Click);
        //
        // radioAuthorization
        //
        this.radioAuthorization.AutoSize = true;
        this.radioAuthorization.Location = new System.Drawing.Point(58, 19);
        this.radioAuthorization.Name = "radioAuthorization";
        this.radioAuthorization.Size = new System.Drawing.Size(47, 17);
        this.radioAuthorization.TabIndex = 20;
        this.radioAuthorization.Text = "Auth";
        this.radioAuthorization.UseVisualStyleBackColor = true;
        this.radioAuthorization.Click += new System.EventHandler(this.radioAuthorization_Click);
        //
        // radioVoid
        //
        this.radioVoid.AutoSize = true;
        this.radioVoid.Location = new System.Drawing.Point(111, 19);
        this.radioVoid.Name = "radioVoid";
        this.radioVoid.Size = new System.Drawing.Size(46, 17);
        this.radioVoid.TabIndex = 21;
        this.radioVoid.Text = "Void";
        this.radioVoid.UseVisualStyleBackColor = true;
        this.radioVoid.Click += new System.EventHandler(this.radioVoid_Click);
        //
        // radioReturn
        //
        this.radioReturn.AutoSize = true;
        this.radioReturn.Location = new System.Drawing.Point(163, 19);
        this.radioReturn.Name = "radioReturn";
        this.radioReturn.Size = new System.Drawing.Size(57, 17);
        this.radioReturn.TabIndex = 22;
        this.radioReturn.Text = "Return";
        this.radioReturn.UseVisualStyleBackColor = true;
        this.radioReturn.Click += new System.EventHandler(this.radioReturn_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioSale);
        this.groupBox1.Controls.Add(this.radioReturn);
        this.groupBox1.Controls.Add(this.radioAuthorization);
        this.groupBox1.Controls.Add(this.radioVoid);
        this.groupBox1.Location = new System.Drawing.Point(12, 12);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(236, 51);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Transaction Type";
        //
        // textRefNumber
        //
        this.textRefNumber.Location = new System.Drawing.Point(253, 39);
        this.textRefNumber.Name = "textRefNumber";
        this.textRefNumber.Size = new System.Drawing.Size(114, 20);
        this.textRefNumber.TabIndex = 24;
        this.textRefNumber.Visible = false;
        //
        // labelRefNumber
        //
        this.labelRefNumber.Location = new System.Drawing.Point(254, 20);
        this.labelRefNumber.Name = "labelRefNumber";
        this.labelRefNumber.Size = new System.Drawing.Size(100, 16);
        this.labelRefNumber.TabIndex = 25;
        this.labelRefNumber.Text = "Ref Number";
        this.labelRefNumber.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelRefNumber.Visible = false;
        //
        // FormPayConnect
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(408, 309);
        this.Controls.Add(this.labelRefNumber);
        this.Controls.Add(this.textRefNumber);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textZipCode);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textSecurityCode);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textNameOnCard);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textExpDate);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCardNumber);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPayConnect";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pay Connect Payment Information";
        this.Load += new System.EventHandler(this.FormPayConnect_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAmount = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCardNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textExpDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNameOnCard = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSecurityCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textZipCode = new System.Windows.Forms.TextBox();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.RadioButton radioSale = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioAuthorization = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioVoid = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioReturn = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textRefNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelRefNumber = new System.Windows.Forms.Label();
}


