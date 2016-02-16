//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormXchargeSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlanCharges;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDental.FormCreditCardEdit;
import OpenDental.Properties.Resources;

public class FormCreditCardEdit  extends Form 
{

    private Patient PatCur;
    private List<PayPlan> PayPlanList = new List<PayPlan>();
    private CreditCard CreditCardOld;
    public CreditCard CreditCardCur;
    /**
    * True if X-Charge is enabled.  Recurring charge section will only show if using X-Charge.
    */
    private boolean IsXCharge = new boolean();
    public FormCreditCardEdit(Patient pat) throws Exception {
        initializeComponent();
        Lan.F(this);
        PatCur = pat;
        IsXCharge = Programs.isEnabled(ProgramName.Xcharge);
    }

    private void formCreditCardEdit_Load(Object sender, EventArgs e) throws Exception {
        CreditCardOld = CreditCardCur.clone();
        fillData();
        if (IsXCharge)
        {
            //Get recurring payment plan information if using X-Charge.
            List<PayPlanCharge> chargeList = PayPlanCharges.refresh(PatCur.PatNum);
            PayPlanList = PayPlans.getValidPlansNoIns(PatCur.PatNum);
            comboPaymentPlans.Items.Add("None");
            comboPaymentPlans.SelectedIndex = 0;
            for (int i = 0;i < PayPlanList.Count;i++)
            {
                comboPaymentPlans.Items.Add(PayPlans.GetTotalPrinc(PayPlanList[i].PayPlanNum, chargeList).ToString("F") + "  " + Patients.GetPat(PayPlanList[i].PatNum).GetNameFL());
                if (PayPlanList[i].PayPlanNum == CreditCardCur.PayPlanNum)
                {
                    comboPaymentPlans.SelectedIndex = i + 1;
                }
                 
            }
        }
        else
        {
            //This will hide the recurring section and change the window size.
            groupRecurringCharges.Visible = false;
            this.ClientSize = new System.Drawing.Size(this.ClientSize.Width, this.ClientSize.Height - 215);
        } 
    }

    private void fillData() throws Exception {
        if (!CreditCardCur.getIsNew())
        {
            textCardNumber.Text = CreditCardCur.CCNumberMasked;
            textAddress.Text = CreditCardCur.Address;
            if (CreditCardCur.CCExpiration.Year > 1800)
            {
                textExpDate.Text = CreditCardCur.CCExpiration.ToString("MMyy");
            }
             
            textZip.Text = CreditCardCur.Zip;
            if (IsXCharge)
            {
                //Only fill information if using X-Charge.
                if (CreditCardCur.ChargeAmt > 0)
                {
                    textChargeAmt.Text = CreditCardCur.ChargeAmt.ToString("F");
                }
                 
                if (CreditCardCur.DateStart.Year > 1880)
                {
                    textDateStart.Text = CreditCardCur.DateStart.ToShortDateString();
                }
                 
                if (CreditCardCur.DateStop.Year > 1880)
                {
                    textDateStop.Text = CreditCardCur.DateStop.ToShortDateString();
                }
                 
                textNote.Text = CreditCardCur.Note;
            }
             
        }
         
    }

    private boolean verifyData() throws Exception {
        if (textCardNumber.Text.Trim().Length < 5)
        {
            MsgBox.show(this,"Invalid Card Number.");
            return false;
        }
         
        try
        {
            if (Regex.IsMatch(textExpDate.Text, "^\\d\\d[/\\- ]\\d\\d$"))
            {
                //08/07 or 08-07 or 08 07
                CreditCardCur.CCExpiration = new DateTime(Convert.ToInt32("20" + textExpDate.Text.Substring(3, 2)), Convert.ToInt32(textExpDate.Text.Substring(0, 2)), 1);
            }
            else if (Regex.IsMatch(textExpDate.Text, "^\\d{4}$"))
            {
                //0807
                CreditCardCur.CCExpiration = new DateTime(Convert.ToInt32("20" + textExpDate.Text.Substring(2, 2)), Convert.ToInt32(textExpDate.Text.Substring(0, 2)), 1);
            }
            else
            {
                MsgBox.show(this,"Expiration format invalid.");
                return false;
            }  
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Expiration format invalid.");
            return false;
        }

        if (IsXCharge)
        {
            //Only validate recurring setup if using X-Charge.
            if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), "") || !StringSupport.equals(textChargeAmt.errorProvider1.GetError(textChargeAmt), ""))
            {
                MsgBox.show(this,"Please fix data entry errors first.");
                return false;
            }
             
            if ((StringSupport.equals(textChargeAmt.Text, "") && comboPaymentPlans.SelectedIndex > 0) || (StringSupport.equals(textChargeAmt.Text, "") && !StringSupport.equals(textDateStart.Text.Trim(), "")))
            {
                MsgBox.show(this,"You need a charge amount for recurring charges.");
                return false;
            }
             
            if (!StringSupport.equals(textChargeAmt.Text, "") && StringSupport.equals(textDateStart.Text.Trim(), ""))
            {
                MsgBox.show(this,"You need a start date for recurring charges.");
                return false;
            }
             
        }
         
        return true;
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        //Only clear text boxes for recurring charges group.
        textChargeAmt.Text = "";
        textDateStart.Text = "";
        textDateStop.Text = "";
        textNote.Text = "";
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (CreditCardCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
         
        CreditCards.delete(CreditCardCur.CreditCardNum);
        List<CreditCard> creditCards = CreditCards.refresh(PatCur.PatNum);
        for (int i = 0;i < creditCards.Count;i++)
        {
            creditCards[i].ItemOrder = creditCards.Count - (i + 1);
            CreditCards.Update(creditCards[i]);
        }
        //Resets ItemOrder.
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!verifyData())
        {
            return ;
        }
         
        CreditCardCur.Address = textAddress.Text;
        CreditCardCur.CCNumberMasked = textCardNumber.Text;
        CreditCardCur.PatNum = PatCur.PatNum;
        CreditCardCur.Zip = textZip.Text;
        if (IsXCharge)
        {
            //Only update recurring if using X-Charge.
            CreditCardCur.ChargeAmt = PIn.Double(textChargeAmt.Text);
            CreditCardCur.DateStart = PIn.Date(textDateStart.Text);
            CreditCardCur.DateStop = PIn.Date(textDateStop.Text);
            CreditCardCur.Note = textNote.Text;
            if (comboPaymentPlans.SelectedIndex > 0)
            {
                CreditCardCur.PayPlanNum = PayPlanList[comboPaymentPlans.SelectedIndex - 1].PayPlanNum;
            }
            else
            {
                CreditCardCur.PayPlanNum = 0;
            } 
        }
         
        //Allows users to change from a recurring payplan charge to a normal one.
        if (CreditCardCur.getIsNew())
        {
            List<CreditCard> itemOrderCount = CreditCards.refresh(PatCur.PatNum);
            CreditCardCur.ItemOrder = itemOrderCount.Count;
            CreditCards.insert(CreditCardCur);
        }
        else
        {
            //Special logic for had a token and changed number or expiration date
            if (!StringSupport.equals(CreditCardCur.XChargeToken, "") && IsXCharge && (!StringSupport.equals(CreditCardOld.CCNumberMasked, CreditCardCur.CCNumberMasked) || CreditCardOld.CCExpiration != CreditCardCur.CCExpiration))
            {
                Program prog = Programs.getCur(ProgramName.Xcharge);
                String path = Programs.getProgramPath(prog);
                if (prog == null)
                {
                    MsgBox.show(this,"X-Charge entry is missing from the database.");
                    return ;
                }
                 
                //should never happen
                if (!prog.Enabled)
                {
                    if (Security.isAuthorized(Permissions.Setup))
                    {
                        FormXchargeSetup FormX = new FormXchargeSetup();
                        FormX.ShowDialog();
                    }
                     
                    return ;
                }
                 
                if (!File.Exists(path))
                {
                    MsgBox.show(this,"Path is not valid.");
                    if (Security.isAuthorized(Permissions.Setup))
                    {
                        FormXchargeSetup FormX = new FormXchargeSetup();
                        FormX.ShowDialog();
                    }
                     
                    return ;
                }
                 
                //Either update the exp date or update credit card number by deleting archive so new token can be created next time it's used.
                ProgramProperty prop = (ProgramProperty)ProgramProperties.getForProgram(prog.ProgramNum)[0];
                ProcessStartInfo info = new ProcessStartInfo(path);
                String resultfile = Path.Combine(Path.GetDirectoryName(path), "XResult.txt");
                File.Delete(resultfile);
                //delete the old result file.
                if (!StringSupport.equals(CreditCardOld.CCNumberMasked, CreditCardCur.CCNumberMasked))
                {
                    //They changed card number which we have to delete archived token which will create a new one next time card is charged.
                    info.Arguments += "/TRANSACTIONTYPE:ARCHIVEVAULTDELETE ";
                    info.Arguments += "/XCACCOUNTID:" + CreditCardCur.XChargeToken + " ";
                    info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
                    info.Arguments += "/USERID:" + ProgramProperties.getPropVal(prog.ProgramNum,"Username") + " ";
                    info.Arguments += "/PASSWORD:" + ProgramProperties.getPropVal(prog.ProgramNum,"Password") + " ";
                    info.Arguments += "/AUTOPROCESS ";
                    info.Arguments += "/AUTOCLOSE ";
                    CreditCardCur.XChargeToken = "";
                }
                else
                {
                    //Clear the XChargeToken in our db.
                    //We can only change exp date for X-Charge via ARCHIVEAULTUPDATE.
                    info.Arguments += "/TRANSACTIONTYPE:ARCHIVEVAULTUPDATE ";
                    info.Arguments += "/XCACCOUNTID:" + CreditCardCur.XChargeToken + " ";
                    if (CreditCardCur.CCExpiration != null && CreditCardCur.CCExpiration.Year > 2005)
                    {
                        info.Arguments += "/EXP:" + CreditCardCur.CCExpiration.ToString("MMyy") + " ";
                    }
                     
                    info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
                    info.Arguments += "/USERID:" + ProgramProperties.getPropVal(prog.ProgramNum,"Username") + " ";
                    info.Arguments += "/PASSWORD:" + ProgramProperties.getPropVal(prog.ProgramNum,"Password") + " ";
                    info.Arguments += "/AUTOPROCESS ";
                    info.Arguments += "/AUTOCLOSE ";
                } 
                Cursor = Cursors.WaitCursor;
                Process process = new Process();
                process.StartInfo = info;
                process.EnableRaisingEvents = true;
                process.Start();
                while (!process.HasExited)
                {
                    Application.DoEvents();
                }
                Thread.Sleep(200);
                //Wait 2/10 second to give time for file to be created.
                Cursor = Cursors.Default;
                String resulttext = "";
                String line = "";
                TextReader reader = new StreamReader(resultfile);
                try
                {
                    {
                        line = reader.ReadLine();
                        while (line != null)
                        {
                            if (!StringSupport.equals(resulttext, ""))
                            {
                                resulttext += "\r\n";
                            }
                             
                            resulttext += line;
                            if (line.StartsWith("RESULT="))
                            {
                                if (!StringSupport.equals(line, "RESULT=SUCCESS"))
                                {
                                    CreditCardCur = CreditCards.getOne(CreditCardCur.CreditCardNum);
                                    fillData();
                                    return ;
                                }
                                 
                            }
                             
                            line = reader.ReadLine();
                        }
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
             
            //End of special token logic
            CreditCards.update(CreditCardCur);
        } 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCreditCardEdit.class);
        this.label3 = new System.Windows.Forms.Label();
        this.textCardNumber = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textExpDate = new System.Windows.Forms.TextBox();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textZip = new System.Windows.Forms.TextBox();
        this.labelAddress = new System.Windows.Forms.Label();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.labelZip = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textDateStop = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.textDateStart = new OpenDental.ValidDate();
        this.textChargeAmt = new OpenDental.ValidDouble();
        this.label5 = new System.Windows.Forms.Label();
        this.groupRecurringCharges = new System.Windows.Forms.GroupBox();
        this.labelPayPlan = new System.Windows.Forms.Label();
        this.comboPaymentPlans = new System.Windows.Forms.ComboBox();
        this.butToday = new OpenDental.UI.Button();
        this.butClear = new OpenDental.UI.Button();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.groupRecurringCharges.SuspendLayout();
        this.SuspendLayout();
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(25, 13);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 16);
        this.label3.TabIndex = 8;
        this.label3.Text = "Card Number";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCardNumber
        //
        this.textCardNumber.Location = new System.Drawing.Point(126, 12);
        this.textCardNumber.Name = "textCardNumber";
        this.textCardNumber.Size = new System.Drawing.Size(240, 20);
        this.textCardNumber.TabIndex = 1;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(41, 39);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(84, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "Exp (MMYY)";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textExpDate
        //
        this.textExpDate.Location = new System.Drawing.Point(126, 38);
        this.textExpDate.Name = "textExpDate";
        this.textExpDate.Size = new System.Drawing.Size(71, 20);
        this.textExpDate.TabIndex = 4;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(337, 345);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 10;
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
        this.butCancel.Location = new System.Drawing.Point(418, 345);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 11;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(126, 90);
        this.textZip.MaxLength = 100;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(136, 20);
        this.textZip.TabIndex = 9;
        //
        // labelAddress
        //
        this.labelAddress.Location = new System.Drawing.Point(25, 65);
        this.labelAddress.Name = "labelAddress";
        this.labelAddress.Size = new System.Drawing.Size(99, 16);
        this.labelAddress.TabIndex = 63;
        this.labelAddress.Text = "Address";
        this.labelAddress.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(126, 64);
        this.textAddress.MaxLength = 100;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(365, 20);
        this.textAddress.TabIndex = 6;
        //
        // labelZip
        //
        this.labelZip.Location = new System.Drawing.Point(29, 91);
        this.labelZip.Name = "labelZip";
        this.labelZip.Size = new System.Drawing.Size(96, 16);
        this.labelZip.TabIndex = 66;
        this.labelZip.Text = "Zip";
        this.labelZip.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butDelete.Location = new System.Drawing.Point(21, 345);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 12;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 108);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(106, 16);
        this.label1.TabIndex = 72;
        this.label1.Text = "Date Stop";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(114, 107);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.Size = new System.Drawing.Size(100, 20);
        this.textDateStop.TabIndex = 71;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 83);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(106, 16);
        this.label2.TabIndex = 70;
        this.label2.Text = "Date Start";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(114, 81);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(100, 20);
        this.textDateStart.TabIndex = 69;
        //
        // textChargeAmt
        //
        this.textChargeAmt.Location = new System.Drawing.Point(114, 55);
        this.textChargeAmt.Name = "textChargeAmt";
        this.textChargeAmt.Size = new System.Drawing.Size(100, 20);
        this.textChargeAmt.TabIndex = 68;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 56);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(106, 16);
        this.label5.TabIndex = 67;
        this.label5.Text = "Charge Amount";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupRecurringCharges
        //
        this.groupRecurringCharges.Controls.Add(this.labelPayPlan);
        this.groupRecurringCharges.Controls.Add(this.comboPaymentPlans);
        this.groupRecurringCharges.Controls.Add(this.butToday);
        this.groupRecurringCharges.Controls.Add(this.butClear);
        this.groupRecurringCharges.Controls.Add(this.textNote);
        this.groupRecurringCharges.Controls.Add(this.label7);
        this.groupRecurringCharges.Controls.Add(this.label6);
        this.groupRecurringCharges.Controls.Add(this.textChargeAmt);
        this.groupRecurringCharges.Controls.Add(this.label1);
        this.groupRecurringCharges.Controls.Add(this.label5);
        this.groupRecurringCharges.Controls.Add(this.textDateStop);
        this.groupRecurringCharges.Controls.Add(this.textDateStart);
        this.groupRecurringCharges.Controls.Add(this.label2);
        this.groupRecurringCharges.Location = new System.Drawing.Point(12, 120);
        this.groupRecurringCharges.Name = "groupRecurringCharges";
        this.groupRecurringCharges.Size = new System.Drawing.Size(479, 210);
        this.groupRecurringCharges.TabIndex = 73;
        this.groupRecurringCharges.TabStop = false;
        this.groupRecurringCharges.Text = "Authorized Recurring Charges";
        //
        // labelPayPlan
        //
        this.labelPayPlan.Location = new System.Drawing.Point(0, 27);
        this.labelPayPlan.Name = "labelPayPlan";
        this.labelPayPlan.Size = new System.Drawing.Size(112, 16);
        this.labelPayPlan.TabIndex = 132;
        this.labelPayPlan.Text = "Payment Plan";
        this.labelPayPlan.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboPaymentPlans
        //
        this.comboPaymentPlans.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPaymentPlans.Location = new System.Drawing.Point(114, 26);
        this.comboPaymentPlans.MaxDropDownItems = 30;
        this.comboPaymentPlans.Name = "comboPaymentPlans";
        this.comboPaymentPlans.Size = new System.Drawing.Size(167, 21);
        this.comboPaymentPlans.TabIndex = 131;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butToday.Location = new System.Drawing.Point(218, 80);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(63, 22);
        this.butToday.TabIndex = 77;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClear.Location = new System.Drawing.Point(218, 54);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(63, 22);
        this.butClear.TabIndex = 76;
        this.butClear.Text = "Clear";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(114, 131);
        this.textNote.MaxLength = 10000;
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(344, 64);
        this.textNote.TabIndex = 75;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(-43, 134);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(156, 16);
        this.label7.TabIndex = 74;
        this.label7.Text = "Note";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(318, 56);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(146, 71);
        this.label6.TabIndex = 73;
        this.label6.Text = "Date Stop will be blank if the charges will be repeated indefinitely.  Clear all " + "these values if no further recurring charges are planned.";
        //
        // FormCreditCardEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(510, 381);
        this.Controls.Add(this.groupRecurringCharges);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textZip);
        this.Controls.Add(this.labelAddress);
        this.Controls.Add(this.textAddress);
        this.Controls.Add(this.labelZip);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textExpDate);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCardNumber);
        this.Controls.Add(this.label3);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormCreditCardEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Credit Card Edit";
        this.Load += new System.EventHandler(this.FormCreditCardEdit_Load);
        this.groupRecurringCharges.ResumeLayout(false);
        this.groupRecurringCharges.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCardNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textExpDate = new System.Windows.Forms.TextBox();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelAddress = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelZip = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateStop;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateStart;
    private OpenDental.ValidDouble textChargeAmt;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupRecurringCharges = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.Button butToday;
    private System.Windows.Forms.Label labelPayPlan = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPaymentPlans = new System.Windows.Forms.ComboBox();
}


