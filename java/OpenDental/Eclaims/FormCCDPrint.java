//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.Eclaims;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.CCDerror;
import OpenDental.Eclaims.CCDField;
import OpenDental.Eclaims.CCDFieldInputter;
import OpenDental.Eclaims.DocumentGenerator;
import OpenDental.Eclaims.FormCCDPrint;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Tooth;

public class FormCCDPrint  extends Form 
{

    private PrintDocument pd = null;
    /**
    * Total pages in the printed document.
    */
    int totalPages = new int();
    /**
    * Keeps track of the number of pages which have already been completely printed.
    */
    int pagesPrinted = new int();
    /**
    * Set to true to print page number in upper right-hand corner of the screen.
    */
    boolean printPageNumbers = new boolean();
    /**
    * Set to true when the document has not been renderd into the local container.
    */
    boolean dirty = true;
    /**
    * English by default (represented by false). Set to true later if using French.
    */
    boolean isFrench = false;
    String formatVersionNumber = new String();
    boolean autoPrint = new boolean();
    int copiesToPrint = new int();
    boolean predetermination = new boolean();
    boolean patientCopy = true;
    Font headingFont = new Font(FontFamily.GenericMonospace, 10, FontStyle.Bold);
    Font standardUnderline = new Font(FontFamily.GenericMonospace, 10, FontStyle.Underline);
    Font standardSmall = new Font(FontFamily.GenericMonospace, 8);
    DocumentGenerator doc = new DocumentGenerator();
    String transactionCode = new String();
    /**
    * Used to represent a single line break (the maximum line hight for the standard font).
    */
    float verticalLine = new float();
    /**
    * Represents the maximum width of any character in our standard font.
    */
    float maxCharWidth = new float();
    /**
    * Contains the x-value for the center of the in-doc.bounds print page values.
    */
    float center = new float();
    /**
    * the x position where the current printing is happening.
    */
    float x = new float();
    /**
    * A value is assigned to text sometimes so that it can be measured or set to French. Not always needed.
    */
    String text = new String();
    /**
    * Used to render numbered bullet lists.
    */
    int bullet = 1;
    /**
    * Used to hold a single group of graphical primitives, so that they can be moved together in case they align with the bottom of a page.
    */
    Pen breakLinePen = new Pen(Pens.Gray.Brush);
    /**
    * The parsed elements/fields of the etrans.MessageText string or embeded message.
    */
    CCDFieldInputter formData;
    /**
    * Attained from field G42. Tells which form to print.
    */
    String formId = new String();
    Etrans etrans;
    Patient patient;
    Patient subscriber;
    Patient subscriber2;
    Carrier primaryCarrier;
    Carrier secondaryCarrier;
    Claim claim;
    Provider provTreat;
    Provider provBill;
    InsPlan insplan;
    InsPlan insplan2;
    InsSub insSub;
    InsSub insSub2;
    List<ClaimProc> claimprocs = new List<ClaimProc>();
    List<Procedure> extracted = new List<Procedure>();
    private String MessageText = new String();
    List<PatPlan> patPlansForPat = new List<PatPlan>();
    PatPlan patPlanPri;
    PatPlan patPlanSec;
    Clinic clinic;
    String responseStatus = new String();
    /**
    * Called externally to display and/or print the messageText as a form.
    */
    public FormCCDPrint(Etrans pEtrans, String messageText, boolean pAutoPrint) throws Exception {
        etrans = pEtrans;
        MessageText = messageText;
        copiesToPrint = 0;
        autoPrint = pAutoPrint;
        init();
    }

    /**
    * Only called internally.
    */
    protected FormCCDPrint(Etrans pEtrans, String messageText, int pCopiesToPrint, boolean pAutoPrint, boolean pPatientCopy) throws Exception {
        etrans = pEtrans;
        MessageText = messageText;
        copiesToPrint = pCopiesToPrint;
        autoPrint = pAutoPrint;
        patientCopy = pPatientCopy;
        init();
    }

    protected void onFormClosed(FormClosedEventArgs e) throws Exception {
        if (pd != null)
        {
            pd.Dispose();
        }
         
    }

    private void init() throws Exception {
        initializeComponent();
        breakLinePen.Width = 2;
        if (etrans.PatNum != 0)
        {
            //Some transactions are not patient specific.
            patient = Patients.getPat(etrans.PatNum);
            patPlansForPat = PatPlans.refresh(etrans.PatNum);
            claim = Claims.getClaim(etrans.ClaimNum);
            primaryCarrier = Carriers.getCarrier(etrans.CarrierNum);
            if (claim == null)
            {
                //for eligibility
                //Get primary info
                insSub = InsSubs.GetSub(etrans.InsSubNum, new List<InsSub>());
                subscriber = Patients.getPat(insSub.Subscriber);
                insplan = InsPlans.GetPlan(etrans.PlanNum, new List<InsPlan>());
                patPlanPri = PatPlans.getFromList(patPlansForPat,insSub.InsSubNum);
            }
            else
            {
                //Get primary info
                insSub = InsSubs.GetSub(claim.InsSubNum, new List<InsSub>());
                subscriber = Patients.getPat(insSub.Subscriber);
                insplan = InsPlans.GetPlan(claim.PlanNum, new List<InsPlan>());
                patPlanPri = PatPlans.getFromList(patPlansForPat,insSub.InsSubNum);
                //Get secondary info
                if (claim.InsSubNum2 != 0)
                {
                    patPlanSec = PatPlans.getFromList(patPlansForPat,claim.InsSubNum2);
                    insSub2 = InsSubs.GetSub(claim.InsSubNum2, new List<InsSub>());
                    subscriber2 = Patients.getPat(insSub2.Subscriber);
                    insplan2 = InsPlans.GetPlan(claim.PlanNum2, new List<InsPlan>());
                    secondaryCarrier = Carriers.getCarrier(insplan2.CarrierNum);
                }
                 
                //Provider info
                provTreat = Providers.getProv(claim.ProvTreat);
                provBill = Providers.getProv(claim.ProvBill);
                //Claim related info
                claimprocs = ClaimProcs.refreshForClaim(claim.ClaimNum);
                long clinicNum = 0;
                for (int i = 0;i < claimprocs.Count;i++)
                {
                    if (claimprocs[i].ClinicNum != 0)
                    {
                        clinicNum = claimprocs[i].ClinicNum;
                        break;
                    }
                     
                }
                if (clinicNum != 0)
                {
                    clinic = Clinics.getClinic(clinicNum);
                }
                else if (!PrefC.getBool(PrefName.EasyNoClinics) && Clinics.getList().Length > 0)
                {
                    clinic = Clinics.getList()[0];
                }
                  
            } 
            if (provTreat == null)
            {
                provTreat = Providers.getProv(Patients.getProvNum(patient));
            }
             
            if (provBill == null)
            {
                provBill = Providers.getProv(Patients.getProvNum(patient));
            }
             
            List<Procedure> procsAll = Procedures.refresh(etrans.PatNum);
            extracted = Procedures.GetCanadianExtractedTeeth(procsAll);
        }
         
        if (MessageText == null || MessageText.Length < 23)
        {
            throw new Exception("CCD message format too short: " + MessageText);
        }
         
        formData = new CCDFieldInputter(MessageText);
        //Input the fields of the given message.
        CCDField languageOfInsured = formData.getFieldById("G27");
        if (languageOfInsured != null)
        {
            if (StringSupport.equals(languageOfInsured.valuestr, "F"))
            {
                isFrench = true;
            }
             
        }
        else if (subscriber != null && StringSupport.equals(subscriber.Language, "fr"))
        {
            isFrench = true;
        }
          
        formatVersionNumber = formData.getFieldById("A03").valuestr;
        //Must always exist so no error checking here.
        transactionCode = formData.getFieldById("A04").valuestr;
        //Must always exist so no error checking here.
        if (StringSupport.equals(formatVersionNumber, "04"))
        {
            //FormId field does not exist in version 02 in any of the message texts.
            CCDField formIdField = formData.getFieldById("G42");
            //Usually exists in version 04 response messages.
            //Only a few response transactions don't define field G42. So far, those are transactions 15 (Summary Reconciliation), 16 (Payment Reconciliation) and 24 (Email).
            //In these cases, we simply do not use the formId field later on in the display code.
            if (formIdField != null)
            {
                formId = formIdField.valuestr;
            }
             
        }
        else
        {
            //Version 02
            //Since there is no FormID field in version 02, we figure out what the formId should be based on the transaction type.
            if (StringSupport.equals(transactionCode, "10"))
            {
                //Eligibility Response.
                formId = "08";
            }
            else //Eligibility Form
            if (StringSupport.equals(transactionCode, "11"))
            {
                //Claim Response.
                formId = "03";
            }
            else //Claim Acknowledgement Form
            if (StringSupport.equals(transactionCode, "21"))
            {
                //EOB
                formId = "01";
                //EOB Form
                CCDField g02 = formData.getFieldById("G02");
                if (g02 != null && StringSupport.equals(g02.valuestr, "Y"))
                {
                    formId = "04";
                }
                 
            }
            else //Employer Certified.
            if (StringSupport.equals(transactionCode, "13"))
            {
                //Response to Pre-Determination.
                formId = "06";
            }
            else //Pre-Determination Acknowledgement Form
            if (StringSupport.equals(transactionCode, "12"))
            {
            }
            else
            {
                throw new Exception("Unhandled transactionCode '" + transactionCode + "' for version 02 message.");
            }     
        } 
        //Reversal response
        //There is no standard form for a reversal response, but we print the reversal response later on based on the transactioncode so we don't need to do anything here.
        CCDField status = formData.getFieldById("G05");
        if (status != null && status.valuestr != null)
        {
            responseStatus = status.valuestr.ToUpper();
        }
         
        transactionCode = formData.getFieldById("A04").valuestr;
        predetermination = (StringSupport.equals(transactionCode, "23") || StringSupport.equals(transactionCode, "13"));
        //Be sure to list all predetermination response types here!
        if (copiesToPrint <= 0)
        {
            //Show the form on screen if there are no copies to print.
            showDisplayMessages();
            CCDField fieldPayTo = formData.getFieldById("F01");
            if (fieldPayTo != null)
            {
                boolean paySubscriber = (StringSupport.equals(fieldPayTo.valuestr, "1"));
                //same for version 02 and version 04
                //Typically, insurance companies in Canada prefer to pay the subscriber instead of the dentist.
                if (assignmentOfBenefits())
                {
                    //The insurance plan is set to pay the dentist
                    if (paySubscriber)
                    {
                        //The carrier has decided to pay the subscriber.
                        MsgBox.show("Canadian","INFORMATION: The carrier changed the payee from the dentist to the subscriber.");
                    }
                     
                }
                else
                {
                    //This check was required for certification.
                    //The insurance plan is set to pay the subscriber
                    if (!paySubscriber)
                    {
                        //The carrier has decided to pay the dentist.
                        MsgBox.show("Canadian","INFORMATION: The carrier changed the payee from the subscriber to the dentist.");
                    }
                     
                } 
            }
             
            //This check was required for certification.
            CCDField paymentAdjustmentAmount = formData.getFieldById("G33");
            if (paymentAdjustmentAmount != null)
            {
                if (!StringSupport.equals(paymentAdjustmentAmount.valuestr.Substring(1), "000000"))
                {
                    MessageBox.Show(Lan.g(this,"Payment adjustment amount") + ": " + rawMoneyStrToDisplayMoney(paymentAdjustmentAmount.valuestr));
                }
                 
            }
             
            if (autoPrint)
            {
                if (!StringSupport.equals(responseStatus, "R"))
                {
                    //We are not required to automatically print rejection notices.
                    //Automatically print a patient copy only. We are never required to autoprint a dentist copy, but it can be done manually instead.
                    new FormCCDPrint(etrans.copy(),MessageText,1,false,true);
                }
                 
            }
             
            if (StringSupport.equals(formId, "05"))
            {
                //Manual claim form
                Canadian.showManualClaimForm(claim);
                Close();
            }
            else
            {
                pd = createPrintDocument();
                printPreviewControl1.Document = pd;
                //Setting the document causes system to call pd_PrintPage, which will print the document in the preview window.
                ShowDialog();
            } 
        }
        else
        {
            pd = createPrintDocument();
            if (StringSupport.equals(formId, "05"))
            {
                //Manual claim form
                Canadian.printManualClaimForm(claim);
            }
            else
            {
                //Send the print job to the physical printer.
                pd.Print();
            } 
            //Send the print job to the physical printer.
            //Print the remaining copies recursively.
            if (copiesToPrint >= 2)
            {
                new FormCCDPrint(etrans.copy(),MessageText,copiesToPrint - 1,false,patientCopy);
            }
             
        } 
        CCDField embeddedTransaction = formData.getFieldById("G40");
        if (embeddedTransaction != null)
        {
            new FormCCDPrint(etrans.copy(),embeddedTransaction.valuestr,copiesToPrint,autoPrint,patientCopy);
        }
         
    }

    private PrintDocument createPrintDocument() throws Exception {
        //have any signatures on the same piece of paper as the rest of the info.
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(50, 50, 50, 50);
        //Half-inch all around.
        //This prevents a bug caused by some printer drivers not reporting their papersize.
        //But remember that other countries use A4 paper instead of 8 1/2 x 11.
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        pd.PrinterSettings.Duplex = Duplex.Horizontal;
        return pd;
    }

    //Print double sided when possible, since forms are usually 1-2 pages.
    private void formCCDPrint_Resize(Object sender, EventArgs e) throws Exception {
        labelPage.Location = new Point((Width - butPrint.Width - labelPage.Width) / 2, labelPage.Location.Y);
        butBack.Location = new Point(labelPage.Left - butBack.Width - 6, butBack.Location.Y);
        butForward.Location = new Point(labelPage.Right + 6, butForward.Location.Y);
    }

    private void butBack_Click(Object sender, EventArgs e) throws Exception {
        if (printPreviewControl1.StartPage == 0)
            return ;
         
        printPreviewControl1.StartPage--;
        labelPage.Text = (printPreviewControl1.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butFwd_Click(Object sender, EventArgs e) throws Exception {
        if (printPreviewControl1.StartPage == totalPages - 1)
            return ;
         
        printPreviewControl1.StartPage++;
        labelPage.Text = (printPreviewControl1.StartPage + 1).ToString() + " / " + totalPages.ToString();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        new FormCCDPrint(etrans.copy(),MessageText,1,false,true);
    }

    private void butPrintDentist_Click(Object sender, EventArgs e) throws Exception {
        new FormCCDPrint(etrans.copy(),MessageText,1,false,false);
    }

    public void showDisplayMessages() throws Exception {
        StringBuilder message = new StringBuilder();
        CCDField[] displayMessageFields = formData.getFieldsById("G32");
        for (int i = 0;i < displayMessageFields.Length;i++)
        {
            if (message.Length > 0)
            {
                message.Append(Environment.NewLine);
            }
             
            message.Append(displayMessageFields[i].valuestr);
        }
        CCDField[] noteOutputFlags = formData.getFieldsById("G41");
        CCDField[] noteNumbers = formData.getFieldsById("G45");
        CCDField[] noteTexts = formData.getFieldsById("G26");
        List<String> displayMessages = new List<String>();
        List<int> displayMessageNumbers = new List<int>();
        for (int i = 0;i < noteOutputFlags.Length;i++)
        {
            //We display notes on screen only if they are marked with output flag 1 (display notes on screen). Output flag 0 (prompt) is ignored here because such notes are printed on the physical printout.
            if (PIn.Int(noteOutputFlags[i].valuestr) != 1)
            {
                continue;
            }
             
            displayMessages.Add(noteTexts[i].valuestr);
            if (i < noteNumbers.Length)
            {
                displayMessageNumbers.Add(PIn.Int(noteNumbers[i].valuestr));
            }
            else
            {
                displayMessageNumbers.Add(i + 1);
            } 
        }
        while (displayMessages.Count > 0)
        {
            int indexOfMinVal = 0;
            for (int j = 1;j < displayMessageNumbers.Count;j++)
            {
                if (displayMessageNumbers[j] < displayMessageNumbers[indexOfMinVal])
                {
                    indexOfMinVal = j;
                }
                 
            }
            doc.startElement();
            if (message.Length > 0)
            {
                message.Append(Environment.NewLine);
            }
             
            message.Append(displayMessages[indexOfMinVal]);
            displayMessages.RemoveAt(indexOfMinVal);
            displayMessageNumbers.RemoveAt(indexOfMinVal);
        }
        String msg = message.ToString();
        if (msg.Length > 0)
        {
            MsgBoxCopyPaste msgBox = new MsgBoxCopyPaste(msg);
            msgBox.Text = "Messages";
            msgBox.ShowDialog();
        }
         
    }

    /**
    * Called for each page to be printed.
    */
    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        for (byte i = 0;i < 255;i++)
        {
            SizeF size = e.Graphics.MeasureString("" + Canadian.getCanadianChar(i), doc.standardFont);
            verticalLine = Math.Max(verticalLine, (float)Math.Ceiling(size.Height));
            maxCharWidth = Math.Max(maxCharWidth, (float)Math.Ceiling(size.Width));
        }
        if (dirty)
        {
            //Only render the document containers the first time through.
            dirty = false;
            doc.bounds = e.MarginBounds;
            center = doc.bounds.X + doc.bounds.Width / 2;
            x = doc.startElement();
            try
            {
                //Every printed page always starts on the first row and can choose to skip rows later if desired.
                if (StringSupport.equals(responseStatus, "R"))
                {
                    printRejection(e.Graphics);
                }
                else if (StringSupport.equals(transactionCode, "12"))
                {
                    printReversalResponse_12(e.Graphics);
                }
                else if (StringSupport.equals(transactionCode, "15"))
                {
                    printSummaryReconciliation_15(e.Graphics);
                }
                else if (StringSupport.equals(transactionCode, "16"))
                {
                    printPaymentReconciliation_16(e.Graphics);
                }
                else if (StringSupport.equals(transactionCode, "24"))
                {
                    printEmail_24(e.Graphics);
                }
                else
                {
                    System.String __dummyScrutVar0 = formId;
                    if (__dummyScrutVar0.equals("01"))
                    {
                        //CDA EOB Form
                        printEOB(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("02"))
                    {
                        //Dentaide Form
                        printDentaide(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("03"))
                    {
                        //Claim Acknowledgement Form
                        printClaimAck(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("04"))
                    {
                        //Employer Certified Form
                        printEmployerCertified(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("05"))
                    {
                    }
                    else //Plan Paper Claim Form
                    //Printed in an earlier step. This line should never be hit.
                    if (__dummyScrutVar0.equals("06"))
                    {
                        //Predetermination Acknowledgement Form
                        printPredeterminationAck(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("07"))
                    {
                        //Predetermination EOB Form
                        printEOB(e.Graphics);
                    }
                    else if (__dummyScrutVar0.equals("08"))
                    {
                        //Eligibility Form
                        printEligibility(e.Graphics);
                    }
                    else
                    {
                        defaultPrint(e.Graphics);
                    }        
                }     
                x = doc.startElement();
                //Be sure to end last element always.
                totalPages = doc.calcTotalPages(e.Graphics);
            }
            catch (Exception __dummyCatchVar0)
            {
                //Printing will fail if the user switched to Open Dental from another software system and Open Dental gets a response from ITRANS with
                //regards to a claim from their old system. In this situation we just want to show what information we have because we will not be able
                //to look up the old claim necessarily. This is also a more elegant way to show other printing errors than allowing an unhandled exception.
                defaultPrint(e.Graphics);
            }
        
        }
         
        e.Graphics.DrawRectangle(Pens.LightGray, e.MarginBounds);
        //Draw light border for context.
        pagesPrinted++;
        doc.printPage(e.Graphics,pagesPrinted);
        if (printPageNumbers)
        {
            text = "Page " + pagesPrinted.ToString() + (isFrench ? " de " : " of ") + totalPages;
            e.Graphics.DrawString(text, doc.standardFont, Pens.Black.Brush, e.MarginBounds.Right - e.Graphics.MeasureString(text, doc.standardFont).Width - 4, e.MarginBounds.Top);
        }
         
        if (pagesPrinted < totalPages)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
            labelPage.Text = (printPreviewControl1.StartPage + 1).ToString() + " / " + totalPages.ToString();
        } 
    }

    private void printRejection(Graphics g) throws Exception {
        printClaimAck(g);
    }

    //The rejection form is almost exactly the same as the claim ack so the same function is used, at least for now.
    private void printEmail_24(Graphics g) throws Exception {
        text = isFrench ? "RÉPONSE PAR COURRIER ÉLECTRONIQUE" : "E-MAIL";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement(verticalLine);
        PrintTransactionDate(g, x, 0);
        PrintTreatmentProviderOfficeNumber(g, x + 250, 0);
        CCDField field = formData.getFieldById("G54");
        doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x + 500, 0);
        //REFERENCE
        x = doc.startElement(verticalLine);
        field = formData.getFieldById("G49");
        doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        //TO
        x = doc.startElement();
        field = formData.getFieldById("G50");
        doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        //FROM
        x = doc.startElement();
        field = formData.getFieldById("G51");
        doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        //SUBJECT
        x = doc.startElement(verticalLine);
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "LIGNE" : "LINE", x, 0);
        text = "MESSAGE";
        float lineCol = x + 55;
        doc.DrawString(g, text, lineCol, 0);
        x = doc.startElement(verticalLine);
        CCDField[] noteLines = formData.getFieldsById("G53");
        //BODY OF MESSAGE
        if (noteLines != null)
        {
            for (int i = 0;i < noteLines.Length;i++)
            {
                x = doc.startElement();
                doc.DrawString(g, (i + 1).ToString().PadLeft(2, '0'), x, 0);
                if (noteLines[i] != null && noteLines[i].valuestr != null)
                {
                    doc.DrawString(g, noteLines[i].valuestr, lineCol, 0, doc.standardFont);
                }
                 
            }
        }
         
    }

    private void printSummaryReconciliation_15(Graphics g) throws Exception {
        text = isFrench ? "RÉSUMÉ DE RÉCONCILIATION" : "SUMMARY RECONCILIATION";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        doc.startElement(verticalLine);
        PrintOfficeSequenceNumber(g, x, 0);
        doc.startElement();
        doc.startElement();
        PrintTransactionReferenceNumber(g, x, 0);
        doc.startElement();
        CCDField field = formData.getFieldById("G34");
        if (field != null)
        {
            //Payment reference
            doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        }
         
        doc.startElement();
        field = formData.getFieldById("G35");
        if (field != null)
        {
            //Payment date
            doc.DrawField(g, field.getFieldName(isFrench), dateNumToPrintDate(field.valuestr), true, x, 0);
        }
         
        doc.startElement();
        field = formData.getFieldById("G36");
        //Payment amount
        if (field != null && field.valuestr != null)
        {
            doc.DrawString(g, field.getFieldName(isFrench) + ": " + rawMoneyStrToDisplayMoney(field.valuestr), x, 0, headingFont);
        }
         
        doc.startElement();
        field = formData.getFieldById("G33");
        if (field != null)
        {
            //Payment adjustment amount
            doc.DrawField(g, field.getFieldName(isFrench), rawMoneyStrToDisplayMoney(field.valuestr), true, x, 0);
        }
         
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        CCDField[] cdaProviderNumbers = formData.getFieldsById("B01");
        CCDField[] carrierIdentificationNumbers = formData.getFieldsById("A05");
        CCDField[] officeSequenceNumbers = formData.getFieldsById("A02");
        CCDField[] transactionReferenceNumbers = formData.getFieldsById("G01");
        CCDField[] transactionPayments = formData.getFieldsById("G38");
        float cdaProviderNumCol = x;
        float cdaProviderNumColWidth = 75;
        float carrierIdentificationNumCol = cdaProviderNumCol + cdaProviderNumColWidth;
        float carrierIdentificationNumColWidth = 125;
        float officeSequenceNumCol = carrierIdentificationNumCol + carrierIdentificationNumColWidth;
        float officeSequenceNumColWidth = 140;
        float transactionReferenceNumCol = officeSequenceNumCol + officeSequenceNumColWidth;
        float transactionReferenceNumColWidth = 125;
        float transactionPaymentCol = transactionReferenceNumCol + transactionReferenceNumColWidth;
        Font temp = doc.standardFont;
        doc.standardFont = new Font(standardSmall.FontFamily, 9, FontStyle.Bold);
        doc.DrawString(g, isFrench ? "NO DU\nDENTISTE" : "UNIQUE\nID NO", cdaProviderNumCol, 0);
        doc.DrawString(g, isFrench ? "IDENTIFICATION\nDE PORTEUR" : "CARRIER\nIDENTIFICATION", carrierIdentificationNumCol, 0);
        doc.DrawString(g, isFrench ? "NO DE TRANSACTION\nDU CABINET" : "DENTAL OFFICE\nCLAIM REFERENCE", officeSequenceNumCol, 0);
        doc.DrawString(g, isFrench ? "NO DE RÉFÉRENCE\nDE TRANSACTION" : "CARRIER CLAIM\nNUMBER", transactionReferenceNumCol, 0);
        doc.DrawString(g, isFrench ? "PAIEMENT DE\nTRANSACTION" : "TRANSACTION\nPAYMENT", transactionPaymentCol, 0);
        doc.standardFont = standardSmall;
        for (int i = 0;i < cdaProviderNumbers.Length;i++)
        {
            doc.startElement();
            doc.DrawString(g, cdaProviderNumbers[i].valuestr, cdaProviderNumCol, 0);
            doc.DrawString(g, carrierIdentificationNumbers[i].valuestr, carrierIdentificationNumCol, 0);
            doc.DrawString(g, officeSequenceNumbers[i + 1].valuestr, officeSequenceNumCol, 0);
            doc.DrawString(g, transactionReferenceNumbers[i + 1].valuestr, transactionReferenceNumCol, 0);
            doc.DrawString(g, RawMoneyStrToDisplayMoney(transactionPayments[i].valuestr), transactionPaymentCol, 0);
        }
        doc.standardFont = temp;
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        printNoteList(g);
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        printErrorList(g);
    }

    private void printPaymentReconciliation_16(Graphics g) throws Exception {
        text = isFrench ? "RÉCONCILIATION DE PAIEMENT" : "PAYMENT RECONCILIATION";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        doc.startElement(verticalLine);
        PrintOfficeSequenceNumber(g, x, 0);
        doc.startElement();
        CCDField field = formData.getFieldById("B04");
        if (field != null)
        {
            doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        }
         
        doc.startElement();
        PrintTransactionReferenceNumber(g, x, 0);
        doc.startElement();
        field = formData.getFieldById("G34");
        if (field != null)
        {
            //Payment reference
            doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x, 0);
        }
         
        doc.startElement();
        field = formData.getFieldById("G35");
        if (field != null)
        {
            //Payment date
            doc.DrawField(g, field.getFieldName(isFrench), dateNumToPrintDate(field.valuestr), true, x, 0);
        }
         
        doc.startElement();
        field = formData.getFieldById("G36");
        //Payment amount
        if (field != null && field.valuestr != null)
        {
            doc.DrawString(g, field.getFieldName(isFrench) + ": " + rawMoneyStrToDisplayMoney(field.valuestr), x, 0, headingFont);
        }
         
        doc.startElement();
        field = formData.getFieldById("G33");
        if (field != null)
        {
            //Payment adjustment amount
            doc.DrawField(g, field.getFieldName(isFrench), rawMoneyStrToDisplayMoney(field.valuestr), true, x, 0);
        }
         
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        CCDField[] cdaProviderNumbers = formData.getFieldsById("B01");
        CCDField[] providerOfficeNumbers = formData.getFieldsById("B02");
        CCDField[] billingProviderNumbers = formData.getFieldsById("B03");
        CCDField[] carrierIdentificationNumbers = formData.getFieldsById("A05");
        CCDField[] officeSequenceNumbers = formData.getFieldsById("A02");
        CCDField[] transactionReferenceNumbers = formData.getFieldsById("G01");
        CCDField[] transactionPayments = formData.getFieldsById("G38");
        float cdaProviderNumCol = x;
        float cdaProviderNumColWidth = 75;
        float providerOfficeNumCol = cdaProviderNumCol + cdaProviderNumColWidth;
        float providerOfficeNumColWidth = 75;
        float billingProviderNumCol = providerOfficeNumCol + providerOfficeNumColWidth;
        float billingProviderNumColWidth = 120;
        float carrierIdentificationNumCol = billingProviderNumCol + billingProviderNumColWidth;
        float carrierIdentificationNumColWidth = 125;
        float officeSequenceNumCol = carrierIdentificationNumCol + carrierIdentificationNumColWidth;
        float officeSequenceNumColWidth = 140;
        float transactionReferenceNumCol = officeSequenceNumCol + officeSequenceNumColWidth;
        float transactionReferenceNumColWidth = 125;
        float transactionPaymentCol = transactionReferenceNumCol + transactionReferenceNumColWidth;
        Font temp = doc.standardFont;
        doc.standardFont = new Font(standardSmall.FontFamily, 9, FontStyle.Bold);
        doc.DrawString(g, isFrench ? "NO DU\nDENTISTE" : "UNIQUE\nID NO", cdaProviderNumCol, 0);
        doc.DrawString(g, isFrench ? "NOMBRE\nD'OFFICE" : "OFFICE\nNUMBER", providerOfficeNumCol, 0);
        doc.DrawString(g, isFrench ? "FOURNISSEUR\nDE FACTURATION" : "BILLING\nPROVIDER", billingProviderNumCol, 0);
        doc.DrawString(g, isFrench ? "IDENTIFICATION\nDE PORTEUR" : "CARRIER\nIDENTIFICATION", carrierIdentificationNumCol, 0);
        doc.DrawString(g, isFrench ? "NO DE TRANSACTION\nDU CABINET" : "DENTAL OFFICE\nCLAIM REFERENCE", officeSequenceNumCol, 0);
        doc.DrawString(g, isFrench ? "NO DE RÉFÉRENCE\nDE TRANSACTION" : "CARRIER CLAIM\nNUMBER", transactionReferenceNumCol, 0);
        doc.DrawString(g, isFrench ? "PAIEMENT DE\nTRANSACTION" : "TRANSACTION\nPAYMENT", transactionPaymentCol, 0);
        doc.standardFont = standardSmall;
        for (int i = 0;i < cdaProviderNumbers.Length;i++)
        {
            doc.startElement();
            doc.DrawString(g, cdaProviderNumbers[i].valuestr, cdaProviderNumCol, 0);
            doc.DrawString(g, providerOfficeNumbers[i].valuestr, providerOfficeNumCol, 0);
            doc.DrawString(g, billingProviderNumbers[i].valuestr, billingProviderNumCol, 0);
            doc.DrawString(g, carrierIdentificationNumbers[i].valuestr, carrierIdentificationNumCol, 0);
            doc.DrawString(g, officeSequenceNumbers[i + 1].valuestr, officeSequenceNumCol, 0);
            doc.DrawString(g, transactionReferenceNumbers[i + 1].valuestr, transactionReferenceNumCol, 0);
            doc.DrawString(g, RawMoneyStrToDisplayMoney(transactionPayments[i].valuestr), transactionPaymentCol, 0);
        }
        doc.standardFont = temp;
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        printNoteList(g);
        doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        doc.startElement();
        printErrorList(g);
    }

    private void printEligibility(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement();
        if (patientCopy)
        {
            text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UNE DEMANDE D'ÉLIGIBILITÉ - COPIE DU PATIENT" : "ELIGIBILITY RESPONSE - PATIENT COPY";
        }
        else
        {
            text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UNE DEMANDE D'ÉLIGIBILITÉ - COPIE DU DENTISTE" : "ELIGIBILITY RESPONSE - DENTIST COPY";
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        text = isFrench ? "Nous avons utilisé les renseignements du présent formulaire pour traiter votre demande par ordinateur. Veuillez en vérifier l'exactitude et aviser votre dentiste en cas d'erreur. Prière de ne pas poster à l'assureur/administrateur du régime." : "The information contained on this form has been used to process your claim electronically. Please verify the accuracy of this data and report any discrepancies to your dental office. Do not mail this form to the insurer/plan administrator.";
        printClaimAckBody(g,text);
        if (isFrench)
        {
            text = "La présente demande de prestations a été transmise par ordinateur.".ToUpper();
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Elle sert de reçu seulement.".ToUpper();
        }
        else
        {
            text = "THIS CLAIM HAS BEEN SUBMITTED ELECTRONICALLY - THIS IS A RECEIPT ONLY";
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
    }

    //private void PrintPaperClaim(Graphics g){
    //  text=isFrench?"RÉCLAMATION DE PAPIER ORDINAIRE":"PLAIN PAPER CLAIM";
    //  doc.DrawString(g,text,center-g.MeasureString(text,headingFont).Width/2,0,headingFont);
    //  x=doc.StartElement(verticalLine);
    //  PrintTransactionDate(g,x,0);
    //  float rightCol=x+400;
    //  PrintTransactionReferenceNumber(g,rightCol,0);
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"NOMBRE DE PRÉDÉTERMINATION:":"PREDETERMINATION NO:",rightCol,0);
    //  doc.StartElement();
    //  PrintDentistName(g,x,0);
    //  PrintTreatmentProviderID(g,rightCol,0);
    //  x=doc.StartElement();
    //  PrintDentistAddress(g,x,0,0);
    //  PrintTreatmentProviderOfficeNumber(g,rightCol,0);
    //  PrintDentistPhone(g,rightCol,verticalLine);
    //  x=doc.StartElement();
    //  PrintOfficeSequenceNumber(g,x,0);
    //  doc.DrawString(g,isFrench?"VÉRIFICATION D'OFFICE":"OFFICE VERIFICATION:",rightCol,0);
    //  x=doc.StartElement(verticalLine);
    //  doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,0);
    //  x=doc.StartElement();
    //  PrintPatientName(g,x,0);
    //  PrintPatientBirthday(g,rightCol,0);
    //  x=doc.StartElement();
    //  doc.DrawField(g,isFrench?"NO DE DIVISION/SECTION":"DIVISION/SECTION NO",insplan.DivisionNo,true,x,0);//Field C11
    //  PrintPrimaryDependantNo(g,rightCol,0);
    //  doc.StartElement();
    //  PrintInsuredAddress(g,x,0,true,0);
    //  x=doc.StartElement(verticalLine);
    //  doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,0);
    //  doc.StartElement();
    //  PrintProcedureListAck(g,GetPayableToString(insSub.AssignBen));
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"C'est un rapport précis des services assurés et de tous les honoraires E. payable et OE.":
    //    "This is an accurate statement of services performed and the total fee payable E. & OE.",x,0,standardSmall);
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"AUTORISATION PATIENTE DE VERSER L'AVANTAGE SUR LE DENTISTE:":
    //    "PATIENT AUTHORIZATION TO PAY BENEFIT TO DENTIST:",x,0);
    //  x=doc.StartElement(verticalLine);
    //  doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,0);
    //  doc.StartElement();
    //  float rightMidCol=400.0f;
    //  text=isFrench?"RENSEIGNEMENTS SUR L'ASSURANCE":"INSURANCE INFORMATION";
    //  doc.DrawString(g,text,x,0);
    //  text=isFrench?"PREMIER ASSUREUR":"PRIMARY COVERAGE";
    //  float leftMidCol=center-g.MeasureString(text,doc.standardFont).Width/2;
    //  doc.DrawString(g,text,leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    text=isFrench?"SECOND ASSUREUR":"SECONDARY COVERAGE";
    //    doc.DrawString(g,text,rightMidCol,0);
    //  }
    //  x=doc.StartElement(verticalLine);
    //  text=isFrench?"ASSUREUR/ADMINIST. RÉGIME:":"CARRIER/PLAN ADMINISTRATOR:";
    //  doc.DrawString(g,text,x,0);
    //  text=primaryCarrier.CarrierName.ToUpper();//Field A05
    //  doc.DrawString(g,text,leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    text=secondaryCarrier.CarrierName.ToUpper();
    //    doc.DrawString(g,text,rightMidCol,0);
    //  }
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"ADRESSE DE PORTEUR:":"CARRIER ADDRESS:",x,0);
    //  PrintAddress(g,leftMidCol,0,primaryCarrier.Address,primaryCarrier.Address2,primaryCarrier.City+" "+primaryCarrier.State+" "+primaryCarrier.Zip,150f,0);
    //  if(claim!=null && insplan2!=null) {
    //    PrintAddress(g,rightMidCol,0,secondaryCarrier.Address,secondaryCarrier.Address2,
    //      secondaryCarrier.City+" "+secondaryCarrier.State+" "+secondaryCarrier.Zip,150f,0);
    //  }
    //  x=doc.StartElement();
    //  text=isFrench?"NO DE POLICE:":"POLICY#:";
    //  doc.DrawString(g,text,x,0);
    //  text=insplan.GroupNum;//Field C01
    //  doc.DrawString(g,text,leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    text=insplan2.GroupNum;//Field E02
    //    doc.DrawString(g,text,rightMidCol,0);
    //  }
    //  x=doc.StartElement();
    //  text=isFrench?"TITULAIRE:":"INSURED/MEMBER NAME:";
    //  doc.DrawString(g,text,x,0);
    //  text=subscriber.GetNameFLFormal();
    //  doc.DrawString(g,text,leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    text=subscriber2.GetNameFLFormal();
    //    doc.DrawString(g,text,rightMidCol,0);
    //  }
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"DATE DE NAISSANCE:":"BIRTHDATE:",x,0);
    //  doc.DrawString(g,DateToString(subscriber.Birthdate),leftMidCol,0);//Field D01
    //  if(subscriber2!=null) {
    //    doc.DrawString(g,DateToString(subscriber2.Birthdate),rightMidCol,0);//Field E04
    //  }
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"NO DU TITULAIRE/CERTIFICAT:":"INSURANCE/CERTIFICATE NO:",x,0);
    //  doc.DrawString(g,insSub.SubscriberID,leftMidCol,0);//Fields D01 and D11
    //  if(insplan2!=null) {
    //    doc.DrawString(g,insSub2.SubscriberID,rightMidCol,0);//Fields E04 and E07
    //  }
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"EMPLOYEUR:":"EMPLOYER:",x,0);
    //  doc.DrawString(g,subscriber.EmployerNum==0?"":Employers.GetName(subscriber.EmployerNum),leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    doc.DrawString(g,subscriber2.EmployerNum==0?"":Employers.GetName(subscriber2.EmployerNum),rightMidCol,0);
    //  }
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"ADRESSE:":"ADDRESS:",x,0);
    //  PrintSubscriberAddress(g,leftMidCol,0,true,0);
    //  PrintSubscriberAddress(g,rightMidCol,0,false,0);
    //  x=doc.StartElement();
    //  doc.DrawString(g,isFrench?"PARENTÉ AVEC PATIENT:":"RELATIONSHIP TO PATIENT:",x,0);
    //  text="";
    //  Relat relat=patPlanPri.Relationship;
    //  switch(Canadian.GetRelationshipCode(relat)) {//Field C03
    //    case "1":
    //      text=isFrench?"Soi-même":"Self";
    //      break;
    //    case "2":
    //      text=isFrench?"Époux(se)":"Spouse";
    //      break;
    //    case "3":
    //      text="Parent";//Same in French and English.
    //      break;
    //    case "4":
    //      text=isFrench?"Conjoint(e)":"Common Law Spouse";
    //      break;
    //    case "5":
    //      text=isFrench?"Autre":"Other";
    //      break;
    //    default:
    //      break;
    //  }
    //  doc.DrawString(g,text,leftMidCol,0);
    //  if(claim!=null && insplan2!=null) {
    //    text="";
    //    switch(Canadian.GetRelationshipCode(patPlanSec.Relationship)) {//Field E06
    //      case "1":
    //        text=isFrench?"Époux(se)":"Spouse";
    //        break;
    //      case "2":
    //        text=isFrench?"Époux(se)":"Spouse";
    //        break;
    //      case "3":
    //        text="Parent";//Same in French and English.
    //        break;
    //      //"4" does not apply.
    //      case "5":
    //        text=isFrench?"Autre":"Other";
    //        break;
    //      default:
    //        break;
    //    }
    //    doc.DrawString(g,text,rightMidCol,0);
    //  }
    //  x=doc.StartElement(verticalLine);
    //  doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,0);
    //  doc.DrawString(g,isFrench?"RENSEIGNEMENTS SUR LE PATIENT":"PATIENT INFORMATION",x,0);
    //  doc.StartElement(verticalLine);
    //  //bullet 1
    //  doc.PushX(x);
    //  Rectangle saveBounds=doc.bounds;
    //  doc.bounds=new Rectangle(doc.bounds.X,doc.bounds.Y,(int)(center-doc.bounds.Left),doc.bounds.Height);
    //  x+=doc.DrawString(g,"1. ",x,0).Width;
    //  doc.DrawString(g,isFrench?"Personne à charge:":"If dependant, indicate:",x,0);
    //  x+=doc.DrawString(g,isFrench?"Étudiant":"Student",x,verticalLine).Width;
    //  float underlineWidth=g.MeasureString("***",doc.standardFont).Width;
    //  x+=doc.HorizontalLine(g,Pens.Black,x,x+underlineWidth,2*verticalLine).Width;
    //  x+=doc.DrawString(g,isFrench?" Handicapé":" Handicapped",x,verticalLine).Width;
    //  doc.HorizontalLine(g,Pens.Black,x,x+underlineWidth,2*verticalLine);
    //  x=doc.PopX();
    //  //bullet 2
    //  doc.DrawString(g,isFrench?"2. Nom de l'institution qu'il fréquente:":"2. Name of student's school:",x,2*verticalLine);
    //  //bullet 3
    //  doc.DrawString(g,isFrench?"3. Le traitement résulte-t-il d'un accident? Oui Non":
    //    "3. Is treatment resulting from an\naccident? Yes No",x,4*verticalLine);
    //  doc.DrawString(g,isFrench?"Si Oui, donner date:":"If yes, give date:",x,6*verticalLine);
    //  //bullet 4
    //  doc.DrawString(g,isFrench?"4. S'agit-il de la première mise en bouche d'une couronne, prothèse ou pont? Oui Non":
    //      "4. Is this an initial placement for crown, denture or bridge? Yes No",x,8*verticalLine);
    //  doc.DrawString(g,isFrench?"Si le non, donnent la date du placement initial:":
    //    "If no, give date of initial placement:",x,11*verticalLine);
    //  float patientCol2=doc.bounds.Right;
    //  doc.bounds=saveBounds;
    //  //bullet 5
    //  SizeF size1=doc.DrawString(g,isFrench?"5. S'agit-il d'un traitement en vue de soins d'orthodontie? Oui Non":
    //    "5. Is treatment for orthodontic\npurposes? Yes No",patientCol2,0);
    //  doc.DrawString(g,isFrench?"6. Je comprends que les honoraires énumérés dans cette réclamation ne "+
    //    "peuvent être couverts près ou peuvent excéder mes avantages de plan. Je comprends que je "+
    //    "suis financièrement responsable à mon dentiste de la quantité entière de traitement. "+
    //    "J'autorise le dégagement de tous les information ou disques priés en ce qui concerne cette "+
    //    "réclamation à l'assureur/à administrateur de plan, et certifie que l'information fournie "+
    //    "est, corrige, et accomplis au meilleur de ma connaissance. La signature de l'assuré:":
    //    "6. I understand that the fees listed in this claim may not be covered by or may exceed my plan "+
    //    "benefits. I understand that I am financially responsible to my dentist for the entire "+
    //    "treatment amount. I authorize the release of any information or records requested in respect "+
    //    "of this claim to the insurer/plan administrator, and certify that the information given is, "+
    //    "correct, and complete to the best of my knowledge. Insured's Signature:",patientCol2,size1.Height);
    //  doc.StartElement(verticalLine);
    //  float yoff=0;
    //  yoff+=doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,yoff).Height;
    //  yoff+=doc.DrawString(g,isFrench?"INSTRUCTION POUR DES COMMENTAIRES DE SUBMISSION/DENTIST:":
    //    "INSTRUCTION FOR SUBMISSION/DENTIST'S COMMENTS:",x,breakLinePen.Width).Height;
    //  yoff+=verticalLine;
    //  yoff+=doc.HorizontalLine(g,breakLinePen,doc.bounds.Left,doc.bounds.Right,yoff).Height;
    //  yoff+=doc.DrawString(g,isFrench?"CERTIFICATION DE LA POLITIQUE HOLDER/EMPLOYER:":
    //    "POLICY HOLDER/EMPLOYER CERTIFICATION:",x,yoff).Height;
    //  doc.PushX(x);
    //  x+=doc.DrawString(g,"1. ",x,yoff).Width;
    //  size1=doc.DrawString(g,isFrench?"Assurance de date débutée":"Date Coverage Commenced",x,yoff);
    //  float xLineEnd=center;
    //  yoff+=size1.Height+doc.HorizontalLine(g,Pens.Black,x+size1.Width,xLineEnd,yoff+size1.Height).Height;
    //  x=doc.ResetX();
    //  x+=doc.DrawString(g,"2. ",x,yoff).Width;
    //  size1=doc.DrawString(g,isFrench?"Personne à charge de date couverte":"Date Dependent Covered",x,yoff);
    //  yoff+=size1.Height+doc.HorizontalLine(g,Pens.Black,x+size1.Width,xLineEnd,yoff+size1.Height).Height;
    //  x=doc.ResetX();
    //  x+=doc.DrawString(g,"3. ",x,yoff).Width;
    //  size1=doc.DrawString(g,isFrench?"Date terminée":"Date Terminated",x,yoff);
    //  yoff+=verticalLine+doc.HorizontalLine(g,Pens.Black,x+size1.Width,xLineEnd,yoff+verticalLine).Height;
    //  size1=doc.DrawString(g,"Position",x,yoff);
    //  yoff+=verticalLine+doc.HorizontalLine(g,Pens.Black,x+size1.Width,xLineEnd,yoff+verticalLine).Height;
    //  size1=doc.DrawString(g,"Date",x,yoff);
    //  yoff+=verticalLine+doc.HorizontalLine(g,Pens.Black,x+size1.Width,xLineEnd,yoff+verticalLine).Height;
    //  x=doc.ResetX();
    //  x+=doc.DrawString(g,"4. ",x,yoff).Width;
    //  size1=doc.DrawString(g,isFrench?"La politique/contractant a autorisé la signature":
    //    "Policy/Contract Holder Authorized Signature",x,yoff);
    //  doc.HorizontalLine(g,Pens.Black,x+size1.Width,doc.bounds.Right-10,yoff+size1.Height);
    //  x=doc.PopX();
    //  doc.StartElement();
    //}
    /**
    * For printing basic information about unknown/unsupported message formats (for debugging, etc.).
    */
    private void defaultPrint(Graphics g) throws Exception {
        x = doc.startElement(verticalLine);
        text = isFrench ? "DONNÉES BRUTES POUR UNE RÉPONSE INVALIDE OU INCONNU" : "RAW DATA FOR INVALID OR UNKNOWN RESPONSE";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement(verticalLine);
        CCDField[] loadedFields = formData.getLoadedFields();
        if (loadedFields != null)
        {
            for (int i = 0;i < loadedFields.Length;i++)
            {
                if (loadedFields[i] != null)
                {
                    x = doc.startElement();
                    if (loadedFields[i].fieldId != null && loadedFields[i].fieldId.Length > 0)
                    {
                        text = loadedFields[i].fieldId;
                        doc.DrawString(g, text, x, 0);
                    }
                     
                    CCDField field = loadedFields[i];
                    doc.DrawField(g, field.getFieldName(isFrench), field.valuestr, true, x + 30, 0);
                }
                 
            }
        }
         
    }

    private void printDentaide(Graphics g) throws Exception {
        doc.standardFont = standardSmall;
        printPageNumbers = true;
        int headerHeight = (int)verticalLine;
        doc.bounds = new Rectangle(doc.bounds.X, doc.bounds.Y + headerHeight, doc.bounds.Width, doc.bounds.Height - headerHeight);
        //Reset the doc.bounds so that the page numbers are on a row alone.
        x = doc.startElement();
        text = isFrench ? "FORMULAIRE DENTAIDE" : "DENTAIDE FORM";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        text = dateToString(etrans.DateTimeTrans);
        SizeF size1 = doc.DrawString(g, isFrench ? "DATE: " : "DATE SUBMITTED: ", x, 0);
        doc.DrawString(g, text, x + size1.Width, 0);
        text = "";
        CCDField fieldG01 = formData.getFieldById("G01");
        if (fieldG01 != null)
        {
            text = fieldG01.valuestr;
        }
         
        float rightMidCol = 400.0f;
        doc.DrawField(g, isFrench ? "NO DE TRANSACTION DE DENTAIDE" : "DENTAIDE TRANSACTION NO", text, true, rightMidCol, 0);
        x = doc.startElement();
        String fieldText = isFrench ? "NO DU PLAN DE TRAITEMENT" : "PREDETERMINATION NO";
        if (StringSupport.equals(transactionCode, "13"))
        {
            //predetermination ACK
            text = "";
        }
        else //We are not supposed to show a predetermination number for this transaction type.
        if (StringSupport.equals(transactionCode, "23"))
        {
        }
        else //predetermination EOB
        //The predetermination number is the same as the dentaide number. Don't change the text value.
        if (claim != null)
        {
            //Claim ACK or EOB
            //Retreive the predetermination number for this claim from the transaction history.
            text = "";
            //There may not be a predetermination, so we first clear the text.
            List<Etrans> etransHist = Etranss.getAllForOneClaim(claim.ClaimNum);
            Etrans predetermEobTrans = null;
            for (int i = 0;i < etransHist.Count;i++)
            {
                //Etrans entries are chronological, so we find the most recent one starting at the end of the list.
                if (etransHist[i].Etype == EtransType.PredetermEOB_CA)
                {
                    //Predeterm EOBs only not Predeterm Acks, because only EOBs have been truely processed by the carrier.
                    if (predetermEobTrans == null || etransHist[i].DateTimeTrans > predetermEobTrans.DateTimeTrans)
                    {
                        predetermEobTrans = etransHist[i];
                    }
                     
                }
                 
            }
            if (predetermEobTrans != null)
            {
                String predetermResponseMessage = EtransMessageTexts.getMessageText(predetermEobTrans.EtransMessageTextNum);
                CCDFieldInputter predetermResponseFields = new CCDFieldInputter(predetermResponseMessage);
                CCDField predetermFieldG01 = predetermResponseFields.getFieldById("G01");
                if (predetermFieldG01 != null)
                {
                    text = predetermFieldG01.valuestr;
                }
                 
            }
             
        }
        else
        {
            //finally retreive the predetermination number.
            //For some reason in the tests they want us to print elegibilities on the Dentaide form. In this case there is not predetermination number.
            text = "";
        }   
        doc.DrawField(g, fieldText, text, false, x, 0);
        PrintTreatmentProviderID(g, rightMidCol, 0);
        x = doc.startElement();
        PrintDentistName(g, x, 0);
        PrintTreatmentProviderOfficeNumber(g, rightMidCol, 0);
        x = doc.startElement();
        PrintDentistAddress(g, x, 0, 0);
        size1 = PrintDentistPhone(g, rightMidCol, 0);
        //Dependant NO. should be same for both primary and secondary insurance, because it is the patient account number from within Open Dental.
        SizeF size2 = PrintPrimaryDependantNo(g, rightMidCol, size1.Height, "PATIENT'S OFFICE ACCOUNT NO", "NO DE DOSSIER DU PATIENT");
        SizeF size3 = PrintOfficeSequenceNumber(g, rightMidCol, size1.Height + size2.Height);
        PrintPatientBirthday(g, rightMidCol, size1.Height + size2.Height + size3.Height);
        x = doc.startElement();
        PrintPatientName(g, x, 0);
        PrintPatientSex(g, rightMidCol, 0);
        x = doc.startElement();
        PrintComment(g, x, 0);
        x = doc.startElement();
        float leftMidCol = 290f;
        text = isFrench ? "RENSEIGNEMENTS SUR L'ASSURANCE" : "INSURANCE INFORMATION";
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Underline);
        size1 = g.MeasureString(text, doc.standardFont);
        doc.DrawString(g, text, x + (leftMidCol - x - size1.Width) / 2, 0);
        text = isFrench ? "PREMIER ASSUREUR" : "PRIMARY COVERAGE";
        size1 = g.MeasureString(text, doc.standardFont);
        float rightCol = leftMidCol + 260f;
        doc.DrawString(g, text, leftMidCol + (rightCol - leftMidCol - size1.Width) / 2, 0);
        text = isFrench ? "SECOND ASSUREUR" : "SECONDARY COVERAGE";
        size1 = g.MeasureString(text, doc.standardFont);
        doc.DrawString(g, text, rightCol + (doc.bounds.Right - rightCol - size1.Width) / 2, 0);
        x = doc.startElement();
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Regular);
        text = isFrench ? "ASSUREUR/ADMINIST. RÉGIME:" : "CARRIER/PLAN ADMINISTRATOR:";
        doc.DrawString(g, text, x, 0);
        text = primaryCarrier.CarrierName.ToUpper();
        //Field A05
        doc.DrawString(g, text, leftMidCol, 0);
        if (claim != null && insplan2 != null)
        {
            text = secondaryCarrier.CarrierName.ToUpper();
            doc.DrawString(g, text, rightCol, 0);
        }
         
        x = doc.startElement();
        text = isFrench ? "NO DE POLICE:" : "POLICY#:";
        doc.DrawString(g, text, x, 0);
        text = insplan.GroupNum;
        //Field C01
        doc.DrawString(g, text, leftMidCol, 0);
        doc.DrawString(g, isFrench ? "DIV/SECTION:" : "DIV/SECTION NO:", leftMidCol + 86, 0);
        doc.DrawString(g, insplan.DivisionNo, leftMidCol + 190, 0);
        if (claim != null && insplan2 != null)
        {
            text = insplan2.GroupNum;
            //Field E02
            doc.DrawString(g, text, rightCol, 0);
        }
         
        doc.DrawString(g, isFrench ? "DIV/SECTION:" : "DIV/SECTION NO:", rightCol + 86, 0);
        if (claim != null && insplan2 != null)
        {
            doc.DrawString(g, insplan2.DivisionNo, rightCol + 190, 0);
        }
         
        x = doc.startElement();
        text = isFrench ? "TITULAIRE:" : "INSURED/MEMBER NAME:";
        doc.DrawString(g, text, x, 0);
        text = subscriber.getNameFLFormal();
        doc.DrawString(g, text, leftMidCol, 0);
        if (claim != null && insplan2 != null)
        {
            text = subscriber2.getNameFLFormal();
            doc.DrawString(g, text, rightCol, 0);
        }
         
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "ADRESSE:" : "ADDRESS:", x, 0);
        PrintSubscriberAddress(g, leftMidCol, 0, true, rightCol - leftMidCol - 10f);
        PrintSubscriberAddress(g, rightCol, 0, false, doc.bounds.Right - rightCol - 10f);
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "DATE DE NAISSANCE:" : "BIRTHDATE:", x, 0);
        doc.DrawString(g, dateToString(subscriber.Birthdate), leftMidCol, 0);
        //Field D01
        if (subscriber2 != null)
        {
            doc.DrawString(g, dateToString(subscriber2.Birthdate), rightCol, 0);
        }
         
        //Field E04
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "NO DU TITULAIRE/CERTIFICAT:" : "INSURANCE/CERTIFICATE NO:", x, 0);
        doc.DrawString(g, insSub.SubscriberID, leftMidCol, 0);
        //Fields D01 and D11
        doc.DrawString(g, isFrench ? "- SÉQUENCE:" : "- SEQUENCE:", leftMidCol + 86, 0);
        doc.DrawString(g, insplan.DentaideCardSequence.ToString().PadLeft(2, '0'), leftMidCol + 162, 0);
        if (insplan2 != null)
        {
            doc.DrawString(g, insSub2.SubscriberID, rightCol, 0);
        }
         
        //Fields E04 and E07
        doc.DrawString(g, isFrench ? "- SÉQUENCE:" : "- SEQUENCE:", rightCol + 86, 0);
        if (insplan2 != null)
        {
            doc.DrawString(g, insplan2.DentaideCardSequence.ToString().PadLeft(2, '0'), rightCol + 162, 0);
        }
         
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "PARENTÉ AVEC PATIENT:" : "RELATIONSHIP TO PATIENT:", x, 0);
        text = "";
        Relat relatPri = getRelationshipToSubscriber();
        System.String __dummyScrutVar1 = Canadian.getRelationshipCode(relatPri);
        //Field C03
        if (__dummyScrutVar1.equals("1"))
        {
            text = isFrench ? "Soi-même" : "Self";
        }
        else if (__dummyScrutVar1.equals("2"))
        {
            text = isFrench ? "Époux(se)" : "Spouse";
        }
        else if (__dummyScrutVar1.equals("3"))
        {
            text = "Parent";
        }
        else //Same in French and English.
        if (__dummyScrutVar1.equals("4"))
        {
            text = isFrench ? "Conjoint(e)" : "Common Law Spouse";
        }
        else if (__dummyScrutVar1.equals("5"))
        {
            text = isFrench ? "Autre" : "Other";
        }
        else
        {
        }     
        doc.DrawString(g, text, leftMidCol, 0);
        if (claim != null && insplan2 != null)
        {
            text = "";
            System.String __dummyScrutVar2 = Canadian.getRelationshipCode(patPlanSec.Relationship);
            //Field E06
            if (__dummyScrutVar2.equals("1"))
            {
                text = isFrench ? "Époux(se)" : "Spouse";
            }
            else if (__dummyScrutVar2.equals("2"))
            {
                text = isFrench ? "Époux(se)" : "Spouse";
            }
            else if (__dummyScrutVar2.equals("3"))
            {
                text = "Parent";
            }
            else //Same in French and English.
            //"4" does not apply.
            if (__dummyScrutVar2.equals("5"))
            {
                text = isFrench ? "Autre" : "Other";
            }
            else
            {
            }    
            doc.DrawString(g, text, rightCol, 0);
        }
         
        //Spaces don't show up with underline, so we will have to underline manually.
        float underlineWidth = g.MeasureString("***", doc.standardFont).Width;
        String isStudent = "   ";
        String isHandicapped = "   ";
        if (relatPri == Relat.Child)
        {
            text = "";
            System.Byte __dummyScrutVar3 = patient.CanadianEligibilityCode;
            //Field C09
            if (__dummyScrutVar3.equals(1))
            {
                //Patient is a full-time student.
                isStudent = isFrench ? "Oui" : "Yes";
                text = patient.SchoolName;
            }
            else if (__dummyScrutVar3.equals(2))
            {
                //Patient is disabled.
                isHandicapped = isFrench ? "Oui" : "Yes";
            }
            else if (__dummyScrutVar3.equals(3))
            {
                //Patient is a disabled student.
                isStudent = isFrench ? "Oui" : "Yes";
                text = patient.SchoolName;
                isHandicapped = isFrench ? "Oui" : "Yes";
            }
            else
            {
            }   
        }
        else //Not applicable
        if (patient.CanadianEligibilityCode == 2)
        {
            isHandicapped = isFrench ? "Oui" : "Yes";
        }
        else if (patient.CanadianEligibilityCode == 3)
        {
            isStudent = isFrench ? "Oui" : "Yes";
            isHandicapped = isFrench ? "Oui" : "Yes";
        }
           
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "PERSONNE À CHARGE: HANDICAPÉ     ÉTUDIANT À PLEIN TEMPS     ; NOM DE L'INSTITUTION:" : "IF DEPENDANT, INDICATE: HANDICAPPED     FULL-TIME STUDENT    ;NAME OF STUDENT'S SCHOOL:", x, 0);
        doc.DrawString(g, isHandicapped, isFrench ? (x + 200) : (x + 248), 0);
        doc.DrawString(g, isStudent, isFrench ? (x + 385) : (x + 397), 0);
        x = doc.startElement();
        doc.DrawString(g, patient.SchoolName, x, 0);
        x = doc.startElement();
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Underline);
        doc.DrawString(g, isFrench ? "RENSEIGNEMENTS SUR LES SOINS:" : "TREATMENT INFORMATION:", x, 0);
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Regular);
        x = doc.startElement();
        bullet = 1;
        printAccidentBullet(g,isFrench ? "Le traitement résulte-t-il d'un accident?" : "Is treatment resulting from an accident?");
        x = doc.startElement();
        CCDField initialPlacementUpperField = formData.getFieldById("F15");
        String initialPlacementUpperStr = "X";
        if (initialPlacementUpperField != null)
        {
            initialPlacementUpperStr = initialPlacementUpperField.valuestr;
        }
         
        CCDField initialPlacementLowerField = formData.getFieldById("F18");
        String initialPlacementLowerStr = "X";
        if (initialPlacementLowerField != null)
        {
            initialPlacementLowerStr = initialPlacementLowerField.valuestr;
        }
         
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Begin indentation.
        String initialPlacementUpperReadable = StringSupport.equals(initialPlacementUpperStr, "X") ? " " : (StringSupport.equals(initialPlacementUpperStr, "N") ? (isFrench ? "Non" : "No") : (isFrench ? "Oui" : "Yes"));
        String initialPlacementLowerReadable = StringSupport.equals(initialPlacementLowerStr, "X") ? " " : (StringSupport.equals(initialPlacementLowerStr, "N") ? (isFrench ? "Non" : "No") : (isFrench ? "Oui" : "Yes"));
        doc.DrawString(g, isFrench ? "S'agit-il de la première mise en bouche d'une couronne, prothèse ou pont? Maxillaire:      Mandibule:" : "Is this an initial placement for crown, denture or bridge?      Maxillary:      Mandibular:", x, 0);
        doc.DrawString(g, initialPlacementUpperReadable, isFrench ? (x + 555) : (x + 510), 0);
        doc.DrawString(g, initialPlacementLowerReadable, isFrench ? (x + 670) : (x + 625), 0);
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Si Non, indiquer le matériau de l'ancienne prothèse et la date de mise en bouche:" : "If no, indicate the material used for the previous prosthesis and the date of insertion:", x, 0);
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Maxillaire:      Matériau:                              Date:" : "Maxillary:       Material:                              Date:", x, 0);
        doc.DrawString(g, initialPlacementUpperReadable, x + 75, 0);
        if (StringSupport.equals(initialPlacementUpperStr, "N"))
        {
            //Field F15 - Avoid invalid upper initial placement data.
            text = GetMaterialDescription(claim.CanadianMaxProsthMaterial);
            //Field F20
            doc.DrawString(g, text, x + 180, 0);
            text = dateToString(claim.CanadianDateInitialUpper);
            //Field F04
            doc.DrawString(g, text, x + 420, 0);
        }
         
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Mandibule:       Matériau:                              Date:" : "Mandibular:      Material:                              Date:", x, 0);
        doc.DrawString(g, initialPlacementLowerReadable, x + 75, 0);
        if (StringSupport.equals(initialPlacementLowerStr, "N"))
        {
            //Field F18 - Avoid invalid lower initial placement data.
            text = GetMaterialDescription(claim.CanadianMandProsthMaterial);
            //Field F21
            doc.DrawString(g, text, x + 180, 0);
            text = dateToString(claim.CanadianDateInitialLower);
            //Field F19
            doc.DrawString(g, text, x + 420, 0);
        }
         
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Si Oui, indiquer le numéro des dents manquantes et la date des extractions." : "If yes, indicate the missing teeth numbers and the date(s) on which they were removed.", x, 0);
        x = doc.startElement();
        printMissingToothList(g);
        x = doc.popX();
        //End first indentation.
        x = doc.startElement();
        size1 = doc.DrawString(g, bullet.ToString() + ". " + (isFrench ? "S'agit-il d'un traitement en vue de soins d'orthodontie?" : "Is treatment for orthodontic purposes? "), x, 0);
        bullet++;
        if (claim != null)
        {
            if (claim.IsOrtho)
            {
                //Field F05
                doc.DrawString(g, isFrench ? "Oui" : "Yes", x + size1.Width, 0);
            }
            else
            {
                doc.DrawString(g, isFrench ? "Non" : "No", x + size1.Width, 0);
            } 
        }
         
        CCDField orthodonticRecordFlagField = formData.getFieldById("F25");
        if (predetermination && orthodonticRecordFlagField != null && StringSupport.equals(orthodonticRecordFlagField.valuestr, "1"))
        {
            x = doc.startElement();
            x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
            bullet++;
            doc.pushX(x);
            //Begin indentation.
            doc.DrawString(g, isFrench ? "S'il s'agit d'un plan de traitement d'orthodontie, indiquer" : "For orthodontic treatment plan, please indicate:", x, 0);
            x = doc.startElement();
            text = formData.getFieldById("F30").valuestr;
            //Duration of treatment in months.
            if (!StringSupport.equals(text, "00"))
            {
                text = text.TrimStart('0');
                doc.DrawField(g, isFrench ? "Durée du traitement: " : "Duration of treatment: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F26").valuestr;
            //First examination fee in raw form.
            if (!StringSupport.equals(text, "000000"))
            {
                text = rawMoneyStrToDisplayMoney(text);
                doc.DrawField(g, isFrench ? "Tarif du premier examen: " : "First examination fee: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F27").valuestr;
            //Diagnostic Phase Fee in raw form.
            if (!StringSupport.equals(text, "000000"))
            {
                text = rawMoneyStrToDisplayMoney(text);
                doc.DrawField(g, isFrench ? "Tarif de la phase diagnostique: " : "Diagnostic phase fee: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F28").valuestr;
            //Initial fee in raw form.
            if (!StringSupport.equals(text, "000000"))
            {
                text = rawMoneyStrToDisplayMoney(text);
                doc.DrawField(g, isFrench ? "Paiement initial: " : "Initial fee: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F29").valuestr;
            //Payment mode or expected payment cycle as an enumeration value.
            if (!StringSupport.equals(text, "0"))
            {
                if (StringSupport.equals(text, "1"))
                {
                    text = isFrench ? "Mensuel" : "Monthly";
                }
                else if (StringSupport.equals(text, "2"))
                {
                    text = isFrench ? "Bimestriel" : "Bimonthly";
                }
                else if (StringSupport.equals(text, "3"))
                {
                    text = isFrench ? "Trimestriel" : "Quarterly";
                }
                else
                {
                    //4
                    text = isFrench ? "Aux quatre mois" : "Every four months";
                }   
                doc.DrawField(g, isFrench ? "Mode de paiement: " : "Payment mode: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F31").valuestr;
            //Number of anticipated payments
            if (!StringSupport.equals(text, "00"))
            {
                text = text.TrimStart('0');
                doc.DrawField(g, isFrench ? "Nombre prévu de paiements: " : "Number of anticipated payments: ", text, true, x, 0);
            }
             
            text = formData.getFieldById("F32").valuestr;
            if (!StringSupport.equals(text, "000000"))
            {
                text = rawMoneyStrToDisplayMoney(text);
                doc.DrawField(g, isFrench ? "Montant prévu du paiement: " : "Anticipated payment amount: ", text, true, x, 0);
            }
             
            x = doc.startElement(verticalLine);
            x = doc.popX();
        }
         
        //End indentation.
        x = doc.startElement();
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Underline);
        doc.DrawString(g, isFrench ? "Validation du titulaire/employeur" : "Policy holder / employer certification", x, 0);
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Regular);
        x = doc.startElement();
        underlineWidth = g.MeasureString("**************", doc.standardFont).Width;
        size1 = doc.DrawString(g, isFrench ? "1. Entrée en vigueur de couverture:" : "1. Date coverage commenced:", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size1.Width, x + size1.Width + underlineWidth, size1.Height);
        x = doc.startElement();
        size1 = doc.DrawString(g, isFrench ? "2. Entrée en vigueur de personne à charge:" : "2. Date dependant covered:", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size1.Width, x + size1.Width + underlineWidth, size1.Height);
        x = doc.startElement();
        size1 = doc.DrawString(g, isFrench ? "3. Terminaison:" : "3. Date terminated:", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size1.Width, x + size1.Width + underlineWidth, size1.Height);
        x = doc.startElement(verticalLine);
        doc.pushX(x);
        x += doc.DrawString(g, isFrench ? "Signature de personne autorisée:" : "Authorized signature:", x, 0).Width;
        x += doc.HorizontalLine(g, Pens.Black, x, x + 150, verticalLine).Width + 10;
        x += doc.DrawString(g, isFrench ? "Fonction:" : "Position:", x, 0).Width;
        x += doc.HorizontalLine(g, Pens.Black, x, x + 100, verticalLine).Width + 10;
        x += doc.DrawString(g, "Date:", x, 0).Width;
        x += doc.HorizontalLine(g, Pens.Black, x, x + 150, verticalLine).Width + 10;
        x = doc.popX();
        x = doc.startElement();
        float yoff = 0;
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Underline);
        yoff += doc.drawString(g,isFrench ? "Signature du patient et du dentiste" : "Patient's and Dentist's signature",x,yoff).Height;
        doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size, FontStyle.Regular);
        yoff += doc.drawString(g,isFrench ? "Je déclare qu’à ma connaissance les renseignements donnés sont " + "véridiques, exacts et complets. J’autorise la divulgation à l’assureur, ou au Centre " + "Dentaide, ou à leurs mandataires de tout renseignement ou dossier relatif à cette " + "demande de prestations. Je conviens de rebourser à l’assureur toute somme " + "débourséeindûment à mon égard. Je m;engage à verser au dentiste la portion non " + "assurée des frais et honoraires demandés pour les soins décrits ci-après." : "I certify that to my knowledge this information is truthful, accurate and complete. " + "I authorize the disclosure to the insurer, or Centre Dentaide, or their agents of any " + "information or file related to this claim. I agree to repay the insurer for any sum " + "paid on my behalf, and to pay the dentist the required fees for the uninsured portion " + "of the treatment described hereinafter.",x,yoff).Height;
        yoff += 2 * verticalLine;
        yoff += doc.HorizontalLine(g, Pens.Black, x, x + 400, yoff).Height;
        yoff += doc.drawString(g,isFrench ? "Signature du patient (ou parent/tuteur)" : "Signature of patient (or parent/guardian)",x,yoff).Height;
        yoff += verticalLine;
        yoff += doc.drawString(g,isFrench ? "La présente constitue une description exacte des soins exécutés " + "et des honoraires demandés ou, dans le cas d'un plan de traitement, des traitements " + "devant être exécutés et des honoraires s'y rapportant, sauf erreur ou omission." : "The above and the treatments described below are an accurate statement of services " + "performed and fees charged, or of services to be performed and fees to be charged in " + "the case of a treatment plan, except errors and omissions.",x,yoff).Height;
        yoff += verticalLine * 2;
        yoff += doc.HorizontalLine(g, Pens.Black, x, x + 400, yoff).Height;
        yoff += doc.drawString(g,isFrench ? "Signature du dentiste" : "Dentist's signature",x,yoff).Height;
        x = doc.startElement();
        size1 = doc.DrawString(g, isFrench ? "Date du traitement: " : "Date of Service: ", x, 0);
        text = dateToString(etrans.DateTimeTrans);
        doc.DrawString(g, text, x + size1.Width, 0);
        x = doc.startElement(verticalLine);
        boolean isEOB = StringSupport.equals(transactionCode, "21") || StringSupport.equals(transactionCode, "23");
        CCDField[] noteNumbers = formData.getFieldsById("G45");
        //Used to looking up note reference numbers.
        if (claimprocs != null)
        {
            List<Procedure> procListAll = Procedures.refresh(claim.PatNum);
            float amountWidth = g.MeasureString("****.**", doc.standardFont).Width;
            //The rest of the CCDField[] object should all be the same length, since they come bundled together as part
            //of each procedure.
            CCDField[] procedureLineNumbers = formData.getFieldsById("F07");
            CCDField[] eligibleAmounts = formData.getFieldsById("G12");
            CCDField[] deductibleAmounts = formData.getFieldsById("G13");
            CCDField[] eligiblePercentages = formData.getFieldsById("G14");
            CCDField[] dentaidePayAmounts = formData.getFieldsById("G15");
            CCDField[] explainationNoteNumbers1 = formData.getFieldsById("G16");
            CCDField[] explainationNoteNumbers2 = formData.getFieldsById("G17");
            CCDField[] eligibleLabAmounts1 = formData.getFieldsById("G43");
            CCDField[] deductibleLabAmounts1 = formData.getFieldsById("G56");
            CCDField[] eligibleLabPercentage1 = formData.getFieldsById("G57");
            CCDField[] benefitLabAmount1 = formData.getFieldsById("G58");
            CCDField[] eligibleLabAmounts2 = formData.getFieldsById("G02");
            CCDField[] deductibleLabAmounts2 = formData.getFieldsById("G59");
            CCDField[] eligibleLabPercentage2 = formData.getFieldsById("G60");
            CCDField[] benefitLabAmount2 = formData.getFieldsById("G61");
            float noteCol = x;
            float noteColWidth = (isEOB ? 55 : 0);
            float procedureCol = noteCol + noteColWidth;
            float procedureColWidth = 50;
            float toothCol = procedureCol + procedureColWidth;
            float toothColWidth = 45;
            float surfaceCol = toothCol + toothColWidth;
            float surfaceColWidth = 55;
            float feeCol = surfaceCol + surfaceColWidth;
            float feeColWidth = 75;
            float eligibleFeeCol = feeCol + feeColWidth;
            float eligibleFeeColWidth = (isEOB ? 75 : 0);
            float labCol = eligibleFeeCol + eligibleFeeColWidth;
            float labColWidth = 80;
            float eligibleLabCol = labCol + labColWidth;
            float eligibleLabColWidth = (isEOB ? 75 : 0);
            float deductibleCol = eligibleLabCol + eligibleLabColWidth;
            float deductibleColWidth = (isEOB ? 75 : 0);
            float percentCoveredCol = deductibleCol + deductibleColWidth;
            float percentCoveredColWidth = (isEOB ? 90 : 0);
            float dentaidePaysCol = percentCoveredCol + percentCoveredColWidth;
            float dentaidePaysColWidth = (isEOB ? 65 : 0);
            float endNoteCol = dentaidePaysCol + dentaidePaysColWidth;
            Font tempFont = doc.standardFont;
            doc.standardFont = standardSmall;
            double totalFee = 0;
            double totalLab = 0;
            double totalPaid = 0;
            int numProcsPrinted = 0;
            if (isEOB)
            {
                doc.DrawString(g, "Note", noteCol, 0);
                doc.DrawString(g, isFrench ? "Admissible" : "Eligible", eligibleFeeCol, 0);
                doc.DrawString(g, isFrench ? "Admissible" : "Eligible", eligibleLabCol, 0);
                doc.DrawString(g, isFrench ? "Franchise" : "Deductible", deductibleCol, 0);
                doc.DrawString(g, isFrench ? "%Couvertrem." : "Percent\nCovered", percentCoveredCol, 0);
                doc.DrawString(g, isFrench ? "Dentaide" : "Detaide\nPays", dentaidePaysCol, 0);
            }
             
            doc.DrawString(g, isFrench ? "Acte" : "Proc", procedureCol, 0);
            doc.DrawString(g, isFrench ? "Dent" : "Tooth", toothCol, 0);
            doc.DrawString(g, "Surface", surfaceCol, 0);
            doc.DrawString(g, isFrench ? "Honoraires" : "Fee", feeCol, 0);
            doc.DrawString(g, isFrench ? "Labo." : "Lab", labCol, 0);
            for (int p = 1;p <= claimprocs.Count;p++)
            {
                x = doc.startElement();
                int procLineNum = numProcsPrinted + p;
                if (procLineNum > claimprocs.Count)
                {
                    continue;
                }
                 
                ClaimProc claimproc = null;
                for (int i = 0;i < claimprocs.Count;i++)
                {
                    if (claimprocs[i].LineNumber == procLineNum)
                    {
                        claimproc = claimprocs[i];
                        break;
                    }
                     
                }
                Procedure proc = Procedures.getOneProc(claimproc.ProcNum,true);
                text = claimproc.CodeSent.PadLeft(6, ' ');
                //Field F08
                doc.DrawString(g, text, procedureCol, 0);
                text = Tooth.toInternat(proc.ToothNum);
                //Field F10
                doc.DrawString(g, text, toothCol, 0);
                text = Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum);
                //Field F11
                doc.DrawString(g, text, surfaceCol, 0);
                text = proc.ProcFee.ToString("F");
                //Field F12
                doc.DrawString(g, text, feeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                totalFee += proc.ProcFee;
                if (isEOB)
                {
                    List<Procedure> labProcs = Procedures.GetCanadianLabFees(proc.ProcNum, procListAll);
                    for (int j = 0;j < procedureLineNumbers.Length;j++)
                    {
                        if (Convert.ToInt32(procedureLineNumbers[j].valuestr) == procLineNum)
                        {
                            //Display the procedure information on its own line.
                            //For any procLineNum>0, there will only be one matching carrier procedure, by definition.
                            size1 = new SizeF(0, 0);
                            int noteIndex = Convert.ToInt32(explainationNoteNumbers1[j].valuestr);
                            if (noteIndex > 0)
                            {
                                size1 = doc.DrawString(g, noteNumbers[noteIndex].valuestr, noteCol, 0);
                            }
                             
                            noteIndex = Convert.ToInt32(explainationNoteNumbers2[j].valuestr);
                            if (noteIndex > 0)
                            {
                                doc.DrawString(g, Environment.NewLine + noteNumbers[noteIndex].valuestr, noteCol + size1.Width, 0);
                            }
                             
                            text = RawMoneyStrToDisplayMoney(eligibleAmounts[j].valuestr);
                            doc.DrawString(g, text, eligibleFeeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                            text = RawMoneyStrToDisplayMoney(deductibleAmounts[j].valuestr);
                            doc.DrawString(g, text, deductibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                            text = RawPercentToDisplayPercent(eligiblePercentages[j].valuestr);
                            doc.DrawString(g, text, percentCoveredCol, 0);
                            text = RawMoneyStrToDisplayMoney(dentaidePayAmounts[j].valuestr);
                            doc.DrawString(g, text, dentaidePaysCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                            totalPaid += Convert.ToDouble(text);
                            if (labProcs.Count > 0)
                            {
                                //Display the Lab1 information on its own line.
                                x = doc.startElement();
                                text = ProcedureCodes.GetProcCodeFromDb(labProcs[0].CodeNum).ProcCode.PadLeft(6, ' ');
                                doc.DrawString(g, text, procedureCol, 0);
                                text = labProcs[0].ProcFee.ToString("F");
                                totalLab += labProcs[0].ProcFee;
                                doc.DrawString(g, text, labCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawMoneyStrToDisplayMoney(eligibleLabAmounts1[j].valuestr);
                                doc.DrawString(g, text, eligibleLabCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawMoneyStrToDisplayMoney(deductibleLabAmounts1[j].valuestr);
                                doc.DrawString(g, text, deductibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawPercentToDisplayPercent(eligibleLabPercentage1[j].valuestr);
                                doc.DrawString(g, text, percentCoveredCol, 0);
                                text = RawMoneyStrToDisplayMoney(benefitLabAmount1[j].valuestr);
                                doc.DrawString(g, text, dentaidePaysCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                            }
                             
                            if (labProcs.Count > 1)
                            {
                                //Display the Lab2 information on its own line.
                                x = doc.startElement();
                                text = ProcedureCodes.GetProcCodeFromDb(labProcs[1].CodeNum).ProcCode.PadLeft(6, ' ');
                                doc.DrawString(g, text, procedureCol, 0);
                                text = labProcs[1].ProcFee.ToString("F");
                                totalLab += labProcs[1].ProcFee;
                                doc.DrawString(g, text, labCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawMoneyStrToDisplayMoney(eligibleLabAmounts2[j].valuestr);
                                doc.DrawString(g, text, eligibleLabCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawMoneyStrToDisplayMoney(deductibleLabAmounts2[j].valuestr);
                                doc.DrawString(g, text, deductibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                                text = RawPercentToDisplayPercent(eligibleLabPercentage2[j].valuestr);
                                doc.DrawString(g, text, percentCoveredCol, 0);
                                text = RawMoneyStrToDisplayMoney(benefitLabAmount2[j].valuestr);
                                doc.DrawString(g, text, dentaidePaysCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                            }
                             
                        }
                         
                    }
                }
                 
            }
            if (isEOB)
            {
                //List the carrier inserted procedures into the procedure list.
                CCDField[] carrierProcs = formData.getFieldsById("G19");
                CCDField[] carrierEligibleAmts = formData.getFieldsById("G20");
                CCDField[] carrierEligibleLabAmts = formData.getFieldsById("G44");
                CCDField[] carrierDeductAmts = formData.getFieldsById("G21");
                CCDField[] carrierAts = formData.getFieldsById("G22");
                CCDField[] carrierBenefitAmts = formData.getFieldsById("G23");
                CCDField[] carrierNotes1 = formData.getFieldsById("G24");
                CCDField[] carrierNotes2 = formData.getFieldsById("G25");
                for (int p = 0;p < carrierProcs.Length;p++)
                {
                    //Display the eligible proc info.
                    x = doc.startElement();
                    text = carrierProcs[p].valuestr.PadLeft(6, ' ');
                    //Field G19
                    doc.DrawString(g, text, procedureCol, 0);
                    text = ProcedureCodes.getProcCode(ProcedureCodes.getCodeNum(text)).Descript;
                    doc.DrawString(g, text, procedureCol + procedureColWidth, 0, doc.standardFont, (int)(toothCol - procedureCol - procedureColWidth - 10));
                    text = RawMoneyStrToDisplayMoney(carrierEligibleAmts[p].valuestr);
                    //Field G20
                    doc.DrawString(g, text, eligibleFeeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                    text = RawMoneyStrToDisplayMoney(carrierDeductAmts[p].valuestr);
                    //Field G21
                    doc.DrawString(g, text, deductibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                    text = RawPercentToDisplayPercent(carrierAts[p].valuestr);
                    //Field G22
                    doc.DrawString(g, text, percentCoveredCol, 0);
                    text = RawMoneyStrToDisplayMoney(carrierBenefitAmts[p].valuestr);
                    //Field G23
                    doc.DrawString(g, text, dentaidePaysCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                    text = "";
                    if (!StringSupport.equals(carrierNotes1[p].valuestr, "00"))
                    {
                        text += carrierNotes1[p].valuestr;
                    }
                     
                    if (!StringSupport.equals(carrierNotes2[p].valuestr, "00"))
                    {
                        if (text.Length > 0)
                        {
                            text += ",";
                        }
                         
                        text += carrierNotes2[p].valuestr;
                    }
                     
                    doc.DrawString(g, text, endNoteCol, 0);
                    //Display the eligible lab info for the proc but on a separate line.
                    x = doc.startElement();
                    text = "LAB(S)";
                    doc.DrawString(g, text, procedureCol, 0);
                    text = RawMoneyStrToDisplayMoney(carrierEligibleLabAmts[p].valuestr);
                    doc.DrawString(g, text, eligibleLabCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                }
            }
             
            doc.standardFont = new Font(doc.standardFont.FontFamily, doc.standardFont.Size + 1, FontStyle.Bold);
            x = doc.startElement(verticalLine);
            doc.DrawString(g, "TOTAL:", toothCol, 0);
            if (isEOB)
            {
                CCDField totalPayable = formData.getFieldById("G55");
                if (totalPayable != null)
                {
                    totalPaid = PIn.Double(rawMoneyStrToDisplayMoney(totalPayable.valuestr));
                }
                 
                doc.DrawString(g, totalPaid.ToString("F"), dentaidePaysCol, 0);
            }
             
            doc.DrawString(g, totalFee.ToString("F"), feeCol, 0);
            doc.DrawString(g, totalLab.ToString("F"), labCol, 0);
            doc.standardFont = tempFont;
        }
         
        if (isEOB)
        {
            doc.startElement();
            doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
            printNoteList(g);
        }
         
    }

    /**
    * Contains different header and footer based on wether or not this is a patient copy.
    */
    private void printClaimAck(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement();
        if (StringSupport.equals(responseStatus, "R"))
        {
            text = isFrench ? "REFUS D'UNE DEMANDE DE PRESTATIONS" : "CLAIM REJECTION NOTICE";
        }
        else
        {
            if (patientCopy)
            {
                text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UNE DEMANDE DE PRESTATIONS - COPIE DU PATIENT" : "CLAIM ACKNOWLEDGEMENT - PATIENT COPY";
            }
            else
            {
                text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UNE DEMANDE DE PRESTATIONS - COPIE DU DENTISTE" : "CLAIM ACKNOWLEDGEMENT - DENTIST COPY";
            } 
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        text = isFrench ? "Nous avons utilisé les renseignements du présent formulaire pour traiter votre demande par ordinateur. Veuillez en vérifier l'exactitude et aviser votre dentiste en cas d'erreur. Prière de ne pas poster à l'assureur/administrateur du régime." : "The information contained on this form has been used to process your claim electronically. Please verify the accuracy of this data and report any discrepancies to your dental office. Do not mail this form to the insurer/plan administrator.";
        printClaimAckBody(g,text);
        if (StringSupport.equals(responseStatus, "R"))
        {
            text = isFrench ? "VEUILLEZ CORRIGER LES ERREURS AVANT DE RESOUMETTRE LA DEMANDE." : "PLEASE CORRECT ERRORS AS SHOWN, PRIOR TO RE-SUBMITTING THE CLAIM.";
        }
        else
        {
            if (isFrench)
            {
                text = "La présente demande de prestations a été transmise par ordinateur.".ToUpper();
                doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
                x = doc.startElement();
                text = "Elle sert de reçu seulement.".ToUpper();
            }
            else
            {
                text = "THIS CLAIM HAS BEEN SUBMITTED ELECTRONICALLY - THIS IS A RECEIPT ONLY";
            } 
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
    }

    private void printPredeterminationAck(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement(verticalLine);
        if (patientCopy)
        {
            text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UN PRÉDÉTERMINATION - COPIE DU PATIENT" : "PREDETERMINATION ACKNOWLEDGMENT - PATIENT COPY";
        }
        else
        {
            text = isFrench ? "ACCUSÉ DE RÉCEPTION D'UN PRÉDÉTERMINATION - COPIE DU DENTISTE" : "PREDETERMINATION ACKNOWLEDGMENT - DENTIST COPY";
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement(verticalLine);
        text = isFrench ? "Nous avons utilisé les renseignements du présent formulaire pour traiter votre demande par" + "ordinateur. Veuillez en vérifier l'exactitude et aviser votre dentiste en cas d'erreur. " + "Prière de ne pas poster à l'assureur/administrateur du régime." : "The information contained on this form has been used to process your claim electronically. " + "Please verify the accuracy of this data and report any discrepancies to your dental office. " + "Do not mail this form to the insurer/plan administrator.";
        printClaimAckBody(g,text);
        if (isFrench)
        {
            text = "La présente demande de prestations a été transmise par ordinateur.".ToUpper();
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Elle sert de reçu seulement.".ToUpper();
        }
        else
        {
            text = "THIS PREDETERMINATION HAS BEEN SUBMITTED ELECTRONICALLY - THIS IS A RECEIPT ONLY";
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
    }

    private void printEmployerCertified(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement(verticalLine);
        if (patientCopy)
        {
            text = isFrench ? "VALIDATION PAR L'EMPLOYEUR/ADMINISTRATEUR DU RÉGIME - COPIE DU PATIENT" : "EMPLOYER/PLAN ADMINISTRATOR CERTIFIED FORM - PATIENT COPY";
        }
        else
        {
            text = isFrench ? "VALIDATION PAR L'EMPLOYEUR/ADMINISTRATEUR DU RÉGIME - COPIE DE DENTISTE" : "EMPLOYER/PLAN ADMINISTRATOR CERTIFIED FORM - DENTIST COPY";
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement(verticalLine);
        text = isFrench ? "Nous avons utilisé les renseignements du présent formulaire pour traiter votre demande par" + "ordinateur. Veuillez en vérifier l'exactitude et aviser votre dentiste en cas d'erreur." : "The information contained on this form has been used to process your claim electronically. " + "Please verify the accuracy of this data and report any discrepancies to your dental office.";
        printClaimAckBody(g,text);
        doc.DrawString(g, isFrench ? "VALIDATION DU TITULAIRE/EMPLOYEUR" : "POLICYHOLDER/EMPLOYER - CERTIFICATION", x, 0);
        x = doc.startElement(verticalLine);
        SizeF size = doc.DrawString(g, isFrench ? "EMPLOYEUR: " : "EMPLOYER: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine / 2);
        size = doc.DrawString(g, isFrench ? "ENTRÉE EN VIGUEUR DE COUVERTURE: " : "DATE COVERAGE COMMENCED: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine / 2);
        size = doc.DrawString(g, isFrench ? "ENTRÉE EN VIGUEUR DE COUVERTURE DE PERSONNE À CHARGE: " : "DATE DEPENDANT COVERED: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine / 2);
        size = doc.DrawString(g, isFrench ? "DATE DE TERMINAISON: " : "DATE TERMINATED: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine / 2);
        size = doc.DrawString(g, isFrench ? "SIGNATURE DE PERSONNE AUTORISÉE: " : "SIGNATURE OF AUTHORIZED OFFICIAL: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine / 2);
        size = doc.DrawString(g, isFrench ? "DATE D'AUTORISATION: " : "AUTHORIZATION DATE: ", x, 0);
        doc.HorizontalLine(g, Pens.Black, x + size.Width, doc.bounds.Right, size.Height);
        x = doc.startElement(verticalLine);
        text = isFrench ? "LA PRÉSENTE DEMANDE A ÉTÉ TRANSMISE PAR ORDINATEUR À:" : "THIS CLAIM HAS BEEN SUBMITTED ELECTRONICALLY TO:";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        printCarrier(g);
        x = doc.startElement();
        text = isFrench ? "VEUILLEZ LA FAIRE VALIDER PAR VOTRE. EMPLOYEUR" : "PLEASE TAKE THIS FORM TO YOUR EMPLOYER FOR CERTIFICATION";
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
    }

    /**
    * Does the actual work for printing claims. When graphicObjects is null, returns the required graphicObjects after calculating them. In normal use, this function should be called twice for each time the form is rendered, once to calculate the graphical primitives, then once more to actually render to form for printing.
    */
    private void printClaimAckBody(Graphics g, String centralDisclaimer) throws Exception {
        doc.standardFont = new Font(standardSmall.FontFamily, 8, FontStyle.Regular);
        PrintTransactionDate(g, x, 0);
        x = doc.startElement();
        PrintStatus(g, x, 0);
        x = doc.startElement();
        PrintDisposition(g, x, 0);
        x = doc.startElement();
        PrintDentistName(g, x, 0);
        PrintDentistPhone(g, x + 250, 0);
        PrintTreatmentProviderID(g, x + 500, 0);
        x = doc.startElement();
        PrintOfficeSequenceNumber(g, x, 0);
        PrintTreatmentProviderOfficeNumber(g, x + 500, 0);
        x = doc.startElement();
        PrintPolicyNo(g, x, 0, true);
        PrintDivisionSectionNo(g, x + 500, 0);
        x = doc.startElement();
        PrintCertificateNo(g, x, 0, true);
        PrintPrimaryDependantNo(g, x + 250, 0);
        PrintCardNo(g, x + 500, 0);
        x = doc.startElement();
        PrintPatientName(g, x, 0);
        PrintPatientBirthday(g, x + 500, 0);
        x = doc.startElement();
        PrintInsuredMember(g, x, 0);
        x = doc.startElement();
        PrintInsuredAddress(g, x, 0, true, 0);
        PrintTransactionReferenceNumber(g, x + 400, 0);
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        x = doc.startElement();
        //Payee not visible in predetermination
        printProcedureListAck(g,predetermination ? "" : getPayableToString(assignmentOfBenefits()));
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        x = doc.startElement();
        if (StringSupport.equals(responseStatus, "R"))
        {
            printErrorList(g);
        }
        else
        {
            doc.standardFont = new Font(standardSmall.FontFamily, 10, FontStyle.Bold);
            doc.DrawString(g, centralDisclaimer, x, 0);
            doc.standardFont = new Font(standardSmall.FontFamily, 8, FontStyle.Regular);
            x = doc.startElement();
            doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
            x = doc.startElement();
            doc.DrawString(g, isFrench ? "RENSEIGNEMENTS SUR LE PATIENT" : "PATIENT INFORMATION", x, 0);
            x = doc.startElement();
            bullet = 1;
            printDependencyBullet(g);
            x = doc.startElement();
            printSecondaryPolicyBullet(g);
            x = doc.startElement();
            printAccidentBullet(g);
            x = doc.startElement();
            printInitialPlacementBullet(g);
            x = doc.startElement();
            printToothExtractionBullet(g);
            x = doc.startElement();
            doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        } 
        x = doc.startElement(verticalLine);
    }

    /**
    * Prints the list of procedures performed for the patient on the document in question.
    */
    private void printProcedureListAck(Graphics g, String payableToStr) throws Exception {
        if (claim == null)
        {
            return ;
        }
         
        //for eligibility response.
        float amountWidth = g.MeasureString("****.**", doc.standardFont).Width;
        float procedureCodeCol = x;
        float procedureToothCol = procedureCodeCol + 470;
        float procedureSurfaceCol = procedureToothCol + 25;
        float procedureDateCol = procedureSurfaceCol + 52;
        float procedureDateColWidth = predetermination ? 0 : 80;
        float procedureChargeCol = procedureDateCol + procedureDateColWidth;
        float procedureTotalCol = procedureChargeCol + amountWidth + 10;
        //charge col width is amount width.
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "ACTE" : "PROCEDURE", procedureCodeCol, 0);
        doc.DrawString(g, isFrench ? "D#" : "TH#", procedureToothCol, 0);
        doc.DrawString(g, "SURF", procedureSurfaceCol, 0);
        //Same in both languages.
        if (!predetermination)
        {
            doc.DrawString(g, "DATE", procedureDateCol, 0);
        }
         
        //Same in both languages.
        doc.DrawString(g, isFrench ? "HONS" : "CHARGE", procedureChargeCol, 0);
        doc.DrawString(g, "TOTAL", procedureTotalCol, 0);
        //Same in both languages.
        x = doc.startElement();
        Procedure proc;
        float procCodeWidth = g.MeasureString("*******", doc.standardFont).Width;
        List<ClaimProc> claimProcsOrdered = new List<ClaimProc>();
        for (int i = 0;i < claimprocs.Count;i++)
        {
            ClaimProc claimproc = claimprocs[i];
            if (claimproc.ProcNum == 0)
            {
                continue;
            }
             
            int j = 0;
            for (;j < claimProcsOrdered.Count;j++)
            {
                if (claimProcsOrdered[j].LineNumber > claimproc.LineNumber)
                {
                    break;
                }
                 
            }
            claimProcsOrdered.Insert(j, claimproc);
        }
        List<Procedure> procListAll = Procedures.refresh(claim.PatNum);
        double totalSubmitted = 0;
        for (int i = 0;i < claimProcsOrdered.Count;i++)
        {
            ClaimProc claimproc = claimProcsOrdered[i];
            proc = Procedures.getOneProc(claimproc.ProcNum,true);
            text = claimproc.CodeSent.PadLeft(6, ' ');
            doc.DrawString(g, text, x, 0);
            text = ProcedureCodes.getProcCode(proc.CodeNum).Descript;
            doc.DrawString(g, text, procedureCodeCol + procCodeWidth, 0, doc.standardFont, (int)(procedureToothCol - procedureCodeCol - procCodeWidth - 10));
            text = Tooth.toInternat(proc.ToothNum);
            //Field F10
            doc.DrawString(g, text, procedureToothCol, 0);
            text = Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum);
            //Field F11
            doc.DrawString(g, text, procedureSurfaceCol, 0);
            if (!predetermination)
            {
                //Used to remove service dates in a predetermination ack.
                text = proc.ProcDate.ToShortDateString();
                //Field F09
                doc.DrawString(g, text, procedureDateCol, 0);
            }
             
            text = proc.ProcFee.ToString("F");
            //Field F12
            doc.DrawString(g, text, procedureChargeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            double procTotalFee = (double)proc.ProcFee;
            List<Procedure> labProcs = Procedures.GetCanadianLabFees(claimproc.ProcNum, procListAll);
            for (int j = 0;j < labProcs.Count;j++)
            {
                procTotalFee += (double)labProcs[j].ProcFee;
            }
            text = procTotalFee.ToString("F");
            doc.DrawString(g, text, procedureTotalCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            totalSubmitted += procTotalFee;
            x = doc.startElement();
        }
        x = doc.startElement();
        doc.DrawField(g, isFrench ? "DESTINATAIRE DU PAIEMENT" : "BENEFIT AMOUNT PAYABLE TO", payableToStr, true, x, 0);
        text = isFrench ? "TOTAL DEMANDÉ " : "TOTAL SUBMITTED ";
        doc.DrawString(g, text, procedureTotalCol - g.MeasureString(text, doc.standardFont).Width, 0);
        text = totalSubmitted.ToString("F");
        doc.DrawString(g, text, procedureTotalCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
    }

    /**
    * Prints the EOB header. Left in its own function, since the header is expected to be printed on each respective page of output.
    */
    private void printEOBHeader(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement();
        if (predetermination)
        {
            if (patientCopy)
            {
                text = isFrench ? "DÉTAIL DES PRESTATIONS D'UN PLAN DE TRAITEMENT - COPIE DU PATIENT" : "PREDETERMINATION EXPLANATION OF BENEFITS - PATIENT COPY";
            }
            else
            {
                text = isFrench ? "DÉTAIL DES PRESTATIONS D'UN PLAN DE TRAITEMENT - COPIE DE DENTISTE" : "PREDETERMINATION EXPLANATION OF BENEFITS - DENTIST COPY";
            } 
        }
        else
        {
            if (patientCopy)
            {
                text = isFrench ? "DÉTAIL DES PRESTATIONS - COPIE DU PATIENT" : "EXPLANATION OF BENEFITS - PATIENT COPY";
            }
            else
            {
                text = isFrench ? "DÉTAIL DES PRESTATIONS - COPIE DE DENTISTE" : "EXPLANATION OF BENEFITS - DENTIST COPY";
            } 
        } 
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        float rightCol = 450;
        PrintVertificationNo(g, x + rightCol, 0);
        x = doc.startElement();
        PrintDentistName(g, x, 0);
        PrintTreatmentProviderID(g, x + rightCol, 0);
        x = doc.startElement();
        PrintOfficeSequenceNumber(g, x, 0);
        PrintTreatmentProviderOfficeNumber(g, x + rightCol, 0);
        x = doc.startElement();
        PrintPolicyNo(g, x, 0, true);
        PrintDivisionSectionNo(g, x + rightCol, 0);
        x = doc.startElement();
        PrintCertificateNo(g, x, 0, true);
        float midCol = 240;
        PrintPrimaryDependantNo(g, x + midCol, 0);
        PrintCardNo(g, x + rightCol, 0);
        x = doc.startElement();
        PrintInsuredMember(g, x, 0);
        PrintSubscriberBirthday(g, x + rightCol, 0, true);
        x = doc.startElement();
        PrintPatientName(g, x, 0);
        PrintPatientBirthday(g, x + rightCol, 0);
        x = doc.startElement();
        PrintRelationshipToSubscriber(g, x, 0, true);
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
    }

    private void printEOB(Graphics g) throws Exception {
        doc.standardFont = new Font(doc.standardFont.FontFamily, 8, FontStyle.Regular);
        printEOBHeader(g);
        x = doc.startElement();
        PrintTransactionReferenceNumber(g, x, 0);
        PrintTransactionDate(g, x + 450, 0);
        x = doc.startElement();
        printProcedureListEOB(g);
        x = doc.startElement();
        printPaymentSummary(g);
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        x = doc.startElement();
        text = isFrench ? "Nous avons utilisé les renseignements du présent formulaire pour traiter votre demande par ordinateur." : "The information contained on this form has been used to process your claim electronically.";
        doc.DrawString(g, text, doc.bounds.Left + doc.bounds.Width / 2 - g.MeasureString(text, doc.standardFont).Width / 2, 0);
        x = doc.startElement();
        text = isFrench ? "Veuillez en vérifier l'exactitude et aviser votre dentiste en cas d'erreur." : "Please verify the accuracy of this data and report any discrepancies to your dental office.";
        doc.DrawString(g, text, doc.bounds.Left + doc.bounds.Width / 2 - g.MeasureString(text, doc.standardFont).Width / 2, 0);
        x = doc.startElement();
        text = isFrench ? "Prière de ne pas poster à l'assureur/administrateur du régime." : "Do not mail this form to the insurer/plan administrator.";
        doc.DrawString(g, text, doc.bounds.Left + doc.bounds.Width / 2 - g.MeasureString(text, doc.standardFont).Width / 2, 0);
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        if (!predetermination)
        {
            x = doc.startElement();
            doc.DrawString(g, isFrench ? "RENSEIGNEMENTS SUR LE PATIENT:" : "PATIENT INFORMATION:", x, 0);
            x = doc.startElement();
            bullet = 1;
            printDependencyBullet(g);
            x = doc.startElement();
            printSecondaryPolicyBullet(g);
            x = doc.startElement();
            printAccidentBullet(g);
            x = doc.startElement();
            printInitialPlacementBullet(g);
            x = doc.startElement();
            printToothExtractionBullet(g);
            x = doc.startElement();
            doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        }
         
        x = doc.startElement();
        printNoteList(g);
        x = doc.startElement();
        doc.HorizontalLine(g, breakLinePen, doc.bounds.Left, doc.bounds.Right, 0);
        x = doc.startElement();
        if (isFrench)
        {
            text = "La présente nous a été transmise par ordinateur par votre dentiste.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Veuillez la conserver.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Pour tout renseignement, veuillez vous adresser à " + (predetermination ? "votre assureur." : "l'assureur/administrateur du régime.");
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Les honoraires non remboursables sont déductibles de l'impôt.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
        }
        else
        {
            text = "This " + (predetermination ? "Predetermination" : "Claim") + " Has Been Submitted Electronically on Your Behalf By Your Dentist.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Please Direct Any Inquiries to Your Insurer/Plan Administrator.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Expenses Not Payable May be Considered for Income Tax Purposes.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
            text = "Please Retain Copy.";
            doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
            x = doc.startElement();
        } 
    }

    private void printProcedureListEOB(Graphics g) throws Exception {
        List<Procedure> procListAll = Procedures.refresh(claim.PatNum);
        Font tempFont = doc.standardFont;
        doc.standardFont = standardSmall;
        float procedureCodeCol = x;
        float procedureToothCol = procedureCodeCol + 270;
        float procedureDateCol = procedureToothCol + 25;
        float procedureDateColWidth = predetermination ? 0 : 75;
        float procedureChargeCol = procedureDateCol + procedureDateColWidth;
        float procedureEligibleCol = procedureChargeCol + 60;
        float procedureDeductCol = procedureEligibleCol + 60;
        float procedureAtCol = procedureDeductCol + 60;
        float procedureBenefitCol = procedureAtCol + 30;
        float procedureNotesCol = procedureBenefitCol + 60;
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "ACTE" : "PROCEDURE", procedureCodeCol, 0);
        doc.DrawString(g, isFrench ? "D#" : "TH#", procedureToothCol, 0);
        if (!predetermination)
        {
            doc.DrawString(g, "DATE", procedureDateCol, 0);
        }
         
        //Same in both languages.
        doc.DrawString(g, isFrench ? "HONS" : "CHARGE", procedureChargeCol, 0);
        doc.DrawString(g, isFrench ? "ADMIS" : "ELIG", procedureEligibleCol, 0);
        doc.DrawString(g, isFrench ? "FRANCH" : "DEDUCT", procedureDeductCol, 0);
        doc.DrawString(g, isFrench ? "%" : "AT", procedureAtCol, 0);
        doc.DrawString(g, isFrench ? "PREST" : "BENEFIT", procedureBenefitCol, 0);
        doc.DrawString(g, "NOTES", procedureNotesCol, 0);
        //First start by listing the procedures originally attached to the claim.
        CCDField[] procedureLineNumbers = formData.getFieldsById("F07");
        CCDField[] eligibleAmounts = formData.getFieldsById("G12");
        CCDField[] deductibleAmounts = formData.getFieldsById("G13");
        CCDField[] eligiblePercentage = formData.getFieldsById("G14");
        CCDField[] benefitAmountForTheProcedures = formData.getFieldsById("G15");
        CCDField[] explainationNotes1 = formData.getFieldsById("G16");
        CCDField[] explainationNotes2 = formData.getFieldsById("G17");
        CCDField[] eligibleLabAmounts1 = formData.getFieldsById("G43");
        CCDField[] deductibleLabAmounts1 = formData.getFieldsById("G56");
        CCDField[] eligibleLabPercentage1 = formData.getFieldsById("G57");
        CCDField[] benefitLabAmount1 = formData.getFieldsById("G58");
        CCDField[] eligibleLabAmounts2 = formData.getFieldsById("G02");
        CCDField[] deductibleLabAmounts2 = formData.getFieldsById("G59");
        CCDField[] eligibleLabPercentage2 = formData.getFieldsById("G60");
        CCDField[] benefitLabAmount2 = formData.getFieldsById("G61");
        Procedure proc;
        float amountWidth = g.MeasureString("****.**", doc.standardFont).Width;
        float procCodeWidth = g.MeasureString("*******", doc.standardFont).Width;
        for (int p = 0;p < procedureLineNumbers.Length;p++)
        {
            int procedureLineNumber = Convert.ToInt32(procedureLineNumbers[p].valuestr);
            int i = 0;
            while (i < claimprocs.Count && claimprocs[i].LineNumber != procedureLineNumber)
            {
                i++;
            }
            //List the current procedure.
            ClaimProc claimproc = claimprocs[i];
            x = doc.startElement();
            proc = Procedures.getOneProc(claimproc.ProcNum,true);
            text = claimproc.CodeSent.PadLeft(6, ' ');
            doc.DrawString(g, text, x, 0);
            //procedure code
            text = ProcedureCodes.getProcCode(proc.CodeNum).Descript;
            doc.DrawString(g, text, procedureCodeCol + procCodeWidth, 0, doc.standardFont, (int)(procedureToothCol - procedureCodeCol - procCodeWidth - 10));
            //proc descript
            text = Tooth.toInternat(proc.ToothNum);
            //Field F10
            doc.DrawString(g, text, procedureToothCol, 0);
            //Tooth number
            if (!predetermination)
            {
                //Used to remove service dates in a predetermination ack.
                text = proc.ProcDate.ToShortDateString();
                //Field F09
                doc.DrawString(g, text, procedureDateCol, 0);
            }
             
            //Procedure date.
            text = proc.ProcFee.ToString("F");
            //Field F12
            doc.DrawString(g, text, procedureChargeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = RawMoneyStrToDisplayMoney(eligibleAmounts[p].valuestr);
            //Field G12
            doc.DrawString(g, text, procedureEligibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = RawMoneyStrToDisplayMoney(deductibleAmounts[p].valuestr);
            //Field G13
            doc.DrawString(g, text, procedureDeductCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = RawPercentToDisplayPercent(eligiblePercentage[p].valuestr);
            //Field G14
            doc.DrawString(g, text, procedureAtCol, 0);
            text = RawMoneyStrToDisplayMoney(benefitAmountForTheProcedures[p].valuestr);
            //Field G15
            doc.DrawString(g, text, procedureBenefitCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = "";
            if (!StringSupport.equals(explainationNotes1[p].valuestr, "00"))
            {
                text += explainationNotes1[p].valuestr;
            }
             
            if (!StringSupport.equals(explainationNotes2[p].valuestr, "00"))
            {
                if (text.Length > 0)
                {
                    text += ",";
                }
                 
                text += explainationNotes2[p].valuestr;
            }
             
            doc.DrawString(g, text, procedureNotesCol, 0, doc.standardFont, (int)(doc.bounds.Right - procedureNotesCol));
            List<Procedure> labProcs = Procedures.GetCanadianLabFees(proc.ProcNum, procListAll);
            if (labProcs.Count > 0)
            {
                //List the first lab info for the current procedure on its own line.
                x = doc.startElement();
                text = ProcedureCodes.GetProcCodeFromDb(labProcs[0].CodeNum).ProcCode.PadLeft(6, ' ');
                doc.DrawString(g, text, procedureCodeCol, 0);
                //proc code
                text = labProcs[0].ProcFee.ToString("F");
                doc.DrawString(g, text, procedureChargeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                //proc fee
                text = RawMoneyStrToDisplayMoney(eligibleLabAmounts1[i].valuestr);
                //G43
                doc.DrawString(g, text, procedureEligibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                text = RawMoneyStrToDisplayMoney(deductibleLabAmounts1[i].valuestr);
                //G56
                doc.DrawString(g, text, procedureDeductCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                text = RawPercentToDisplayPercent(eligibleLabPercentage1[i].valuestr);
                //G57
                doc.DrawString(g, text, procedureAtCol, 0);
                text = RawMoneyStrToDisplayMoney(benefitLabAmount1[i].valuestr);
                //G58
                doc.DrawString(g, text, procedureBenefitCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            }
             
            if (labProcs.Count > 1)
            {
                //List the second lab info for the current procedure on its own line.
                x = doc.startElement();
                text = ProcedureCodes.GetProcCodeFromDb(labProcs[1].CodeNum).ProcCode.PadLeft(6, ' ');
                doc.DrawString(g, text, procedureCodeCol, 0);
                //proc code
                text = labProcs[1].ProcFee.ToString("F");
                doc.DrawString(g, text, procedureChargeCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                //proc fee
                text = RawMoneyStrToDisplayMoney(eligibleLabAmounts2[i].valuestr);
                //G02
                doc.DrawString(g, text, procedureEligibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                text = RawMoneyStrToDisplayMoney(deductibleLabAmounts2[i].valuestr);
                //G59
                doc.DrawString(g, text, procedureDeductCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
                text = RawPercentToDisplayPercent(eligibleLabPercentage2[i].valuestr);
                //G60
                doc.DrawString(g, text, procedureAtCol, 0);
                text = RawMoneyStrToDisplayMoney(benefitLabAmount2[i].valuestr);
                //G61
                doc.DrawString(g, text, procedureBenefitCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            }
             
        }
        //List the carrier inserted procedures into the procedure list.
        CCDField[] carrierProcs = formData.getFieldsById("G19");
        CCDField[] carrierEligibleAmts = formData.getFieldsById("G20");
        CCDField[] carrierEligibleLabAmts = formData.getFieldsById("G44");
        CCDField[] carrierDeductAmts = formData.getFieldsById("G21");
        CCDField[] carrierAts = formData.getFieldsById("G22");
        CCDField[] carrierBenefitAmts = formData.getFieldsById("G23");
        CCDField[] carrierNotes1 = formData.getFieldsById("G24");
        CCDField[] carrierNotes2 = formData.getFieldsById("G25");
        for (int p = 0;p < carrierProcs.Length;p++)
        {
            //Display the eligible proc info.
            x = doc.startElement();
            text = carrierProcs[p].valuestr.PadLeft(6, ' ');
            //Field G19
            doc.DrawString(g, text, x, 0);
            text = ProcedureCodes.getProcCode(ProcedureCodes.getCodeNum(text)).Descript;
            doc.DrawString(g, text, procedureCodeCol + procCodeWidth, 0, doc.standardFont, (int)(procedureToothCol - procedureCodeCol - procCodeWidth - 10));
            text = RawMoneyStrToDisplayMoney(carrierEligibleAmts[p].valuestr);
            //Field G20
            doc.DrawString(g, text, procedureEligibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = RawMoneyStrToDisplayMoney(carrierDeductAmts[p].valuestr);
            //Field G21
            doc.DrawString(g, text, procedureDeductCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = RawPercentToDisplayPercent(carrierAts[p].valuestr);
            //Field G22
            doc.DrawString(g, text, procedureAtCol, 0);
            text = RawMoneyStrToDisplayMoney(carrierBenefitAmts[p].valuestr);
            //Field G23
            doc.DrawString(g, text, procedureBenefitCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            text = "";
            if (!StringSupport.equals(carrierNotes1[p].valuestr, "00"))
            {
                text += carrierNotes1[p].valuestr;
            }
             
            if (!StringSupport.equals(carrierNotes2[p].valuestr, "00"))
            {
                if (text.Length > 0)
                {
                    text += ",";
                }
                 
                text += carrierNotes2[p].valuestr;
            }
             
            doc.DrawString(g, text, procedureNotesCol, 0);
            //Display the eligible lab info for the proc but on a separate line.
            x = doc.startElement();
            if (carrierEligibleLabAmts.Length > 0)
            {
                text = "LAB(S)";
                doc.DrawString(g, text, procedureCodeCol, 0);
                text = RawMoneyStrToDisplayMoney(carrierEligibleLabAmts[p].valuestr);
                doc.DrawString(g, text, procedureEligibleCol + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            }
             
        }
        //Handle the unallocated deductible amount if it exists.
        //This happens when a carrier will not supply deductibles on a procedural basis.
        String unallocatedDeductible = formData.getFieldById("G29").valuestr;
        if (!StringSupport.equals(unallocatedDeductible, "000000"))
        {
            x = doc.startElement();
            text = isFrench ? "Total Franchise" : "Total Deductible";
            doc.DrawString(g, text, x + 70, 0);
            text = rawMoneyStrToDisplayMoney(unallocatedDeductible);
            doc.DrawString(g, text, procedureDeductCol, 0);
            text = "-" + text;
            doc.DrawString(g, text, procedureBenefitCol, 0);
        }
         
        doc.standardFont = tempFont;
    }

    private void printReversalResponse_12(Graphics g) throws Exception {
        printCarrier(g);
        x = doc.startElement();
        text = (isFrench ? "Réponse d'annulation de réclamation" : "CLAIM REVERSAL RESPONSE").ToUpper();
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
        x = doc.startElement();
        PrintStatus(g, x, 0);
        x = doc.startElement();
        PrintDisposition(g, x, 0);
        x = doc.startElement();
        PrintTransactionReferenceNumber(g, x, 0);
        x = doc.startElement();
        text = isFrench ? "MONTANT TOTAL DE SERVICE: " : "TOTAL AMOUNT OF SERVICE: ";
        x += doc.DrawString(g, text, x, 0).Width + 10;
        text = rawMoneyStrToDisplayMoney(formData.getFieldById("G04").valuestr);
        doc.DrawString(g, text, x, 0);
        x = doc.startElement();
        float rightCol = 450;
        PrintDentistName(g, x, 0);
        PrintTreatmentProviderID(g, x + rightCol, 0);
        x = doc.startElement();
        PrintOfficeSequenceNumber(g, x, 0);
        PrintTreatmentProviderOfficeNumber(g, x + rightCol, 0);
        x = doc.startElement();
        x = doc.startElement();
        printErrorList(g);
    }

    /**
    * Prints carrier name centered on current form row followed by a space.
    */
    private void printCarrier(Graphics g) throws Exception {
        text = getCarrier().CarrierName;
        doc.DrawString(g, text, center - g.MeasureString(text, headingFont).Width / 2, 0, headingFont);
    }

    /**
    * For EOBs only.
    */
    private SizeF printVertificationNo(Graphics g, float X, float Y) throws Exception {
        CCDField vertificationNo = formData.getFieldById("G30");
        return doc.drawField(g,vertificationNo.getFieldName(isFrench),vertificationNo.valuestr,false,X,Y);
    }

    //Present in EOBs.
    private SizeF printTransactionDate(Graphics g, float X, float Y) throws Exception {
        text = dateToString(etrans.DateTimeTrans);
        return doc.drawField(g,isFrench ? "DATE" : "DATE SUBMITTED",text,true,X,Y);
    }

    /**
    * Corresponds to field G01.
    */
    private SizeF printTransactionReferenceNumber(Graphics g, float X, float Y) throws Exception {
        CCDField[] carrierClaimNos = formData.getFieldsById("G01");
        if (carrierClaimNos == null || carrierClaimNos.Length == 0)
        {
            throw new Exception("Field G01 does not exist in transaction, cannot print carrier claim number.");
        }
         
        return doc.DrawField(g, isFrench ? "NO DE RÉFÉRENCE DE TRANSACTION" : "CARRIER CLAIM NO", carrierClaimNos[0].valuestr, false, X, Y);
    }

    private SizeF printDisposition(Graphics g, float X, float Y) throws Exception {
        CCDField disposition = formData.getFieldById("G07");
        return doc.drawField(g,disposition.getFieldName(isFrench),disposition.valuestr,false,X,Y);
    }

    private SizeF printStatus(Graphics g, float X, float Y) throws Exception {
        CCDField status = formData.getFieldById("G05");
        String statusStr = "";
        if (status != null)
        {
            System.String __dummyScrutVar4 = status.valuestr;
            if (__dummyScrutVar4.equals(("A")))
            {
                statusStr = isFrench ? "La transaction a été accepté." : "Transaction has been accepted.";
            }
            else if (__dummyScrutVar4.equals(("E")))
            {
                statusStr = isFrench ? "Le patient est éligible." : "The patient is eligible.";
            }
            else if (__dummyScrutVar4.equals(("R")))
            {
                statusStr = isFrench ? "Transaction rejeté due aux erreurs." : "Claim is rejected because of errors.";
            }
            else if (__dummyScrutVar4.equals(("H")))
            {
                statusStr = isFrench ? "Transaction a été reçu par l'assureur et est détenu pour un traitement ultérieur. Réponse ne sera pas renvoyé chez le dentiste par voie électronique." : "Claim is received successfully by the carrier and is held for further processing. Response will NOT be sent back to the dentist electronically.";
            }
            else if (__dummyScrutVar4.equals(("B")))
            {
                statusStr = isFrench ? "Transaction a été reçu par le réseau et sera transmit en lot à l'assureur pour traitement ultérieur. Réponse ne sera pas renvoyé chez le dentiste par voie électronique." : "Claim is received successfully by the network and will be batch-forwarded on to the carrier for further processing. Response will NOT be sent back to the dentist electronically.";
            }
            else if (__dummyScrutVar4.equals(("C")))
            {
                statusStr = isFrench ? "Transaction a été reçu par l'assureur et est détenu pour un traitement ultérieur. Réponse sera peut-être renvoyé chez le dentiste par voie électronique et obtenu par une demande pour les réponses en suspend." : "Claim is received successfully by the carrier and is held for further processing. Response may be sent back to the dentist electronically and retrievable via ROT.";
            }
            else if (__dummyScrutVar4.equals(("N")))
            {
                statusStr = isFrench ? "Transaction a été reçu par le réseau et sera transmit en lot à l'assureur pour traitement ultérieur.  Réponse sera peut-être renvoyé chez le dentiste par voie électronique et obtenu par une demande pour les réponses en suspend." : "Claim is received successfully by the network and will be batch forwarded onto the carrier for further processing. Response may be sent back to the dentist electronically and retrievable via ROT.";
            }
            else if (__dummyScrutVar4.equals(("M")))
            {
                statusStr = isFrench ? "Un formulaire de transaction manuelle devrait être soumise par le patient ou le cabinet dentaire." : "Manual claim form should be submitted by the patient or the dental office.";
            }
            else if (__dummyScrutVar4.equals(("X")))
            {
                statusStr = isFrench ? "Aucunes réponses en suspend à suivre." : "No more outstanding responses to follow.";
            }
            else
            {
                statusStr = "";
            }         
        }
         
        return doc.drawField(g,isFrench ? "STATUT" : "STATUS",statusStr,true,X,Y);
    }

    private SizeF printComment(Graphics g, float X, float Y) throws Exception {
        String comment = "";
        CCDField commentField = formData.getFieldById("G07");
        if (commentField != null)
        {
            //The disposition message is not always present.
            comment = commentField.valuestr;
        }
         
        return doc.drawField(g,isFrench ? "COMMENTAIRES" : "COMMENT",comment,false,X,Y);
    }

    private SizeF printDentistName(Graphics g, float X, float Y) throws Exception {
        //Treatment provider should match that retrieved from the CDA provider number in field B01.
        text = provTreat.LName + ", " + provTreat.FName + " " + provTreat.MI + " " + provTreat.Suffix;
        return doc.drawField(g,isFrench ? "DENTISTE" : "DENTIST",text,false,X,Y);
    }

    private SizeF printDentistPhone(Graphics g, float X, float Y) throws Exception {
        text = PrefC.getString(PrefName.PracticePhone);
        if (text.Length == 10)
        {
            //May need to format for nice appearance.
            text = text.Substring(0, 3) + "-" + text.Substring(3, 3) + "-" + text.Substring(6, 4);
        }
         
        return doc.drawField(g,isFrench ? "NO DE TÉLÉPHONE" : "TELEPHONE",text,true,X,Y);
    }

    ///<summary>The output will be no wider than maxWidthInPixels, unless maxWidthInPixels<=0, in which case there is no maximum width.</summary>
    private SizeF printDentistAddress(Graphics g, float X, float Y, float maxWidthInPixels) throws Exception {
        SizeF size1 = doc.drawString(g,isFrench ? "ADRESSE: " : "ADDRESS: ",X,Y);
        SizeF size2 = PrintAddress(g, X + size1.Width, Y, PrefC.getString(PrefName.PracticeAddress), PrefC.getString(PrefName.PracticeAddress2), PrefC.getString(PrefName.PracticeCity) + ", " + PrefC.getString(PrefName.PracticeST) + " " + PrefC.getString(PrefName.PracticeZip), 150f, maxWidthInPixels);
        return new SizeF(size1.Width + size2.Width, Math.Max(size1.Height, size2.Height));
    }

    /**
    * Corresponds to field B01.
    */
    private SizeF printTreatmentProviderID(Graphics g, float X, float Y) throws Exception {
        CCDField treatmentProviderID = formData.getFieldById("B01");
        return doc.drawField(g,treatmentProviderID.getFieldName(isFrench),treatmentProviderID.valuestr,false,X,Y);
    }

    /**
    * Corresponds to field B02.
    */
    private SizeF printTreatmentProviderOfficeNumber(Graphics g, float X, float Y) throws Exception {
        CCDField cdaOfficeNumber = formData.getFieldById("B02");
        return doc.drawField(g,cdaOfficeNumber.getFieldName(isFrench),cdaOfficeNumber.valuestr,false,X,Y);
    }

    /**
    * Corresponds to field A02.
    */
    private SizeF printOfficeSequenceNumber(Graphics g, float X, float Y) throws Exception {
        CCDField[] officeSequenceNumbers = formData.getFieldsById("A02");
        if (officeSequenceNumbers == null || officeSequenceNumbers.Length == 0)
        {
            throw new Exception("There are no instances of field A02 to read, cannot print dental office claim reference number.");
        }
         
        return doc.DrawField(g, officeSequenceNumbers[0].GetFieldName(isFrench), officeSequenceNumbers[0].valuestr, false, X, Y);
    }

    private SizeF printPatientName(Graphics g, float X, float Y) throws Exception {
        return doc.drawField(g,"PATIENT",patient.getNameFLFormal(),true,X,Y);
    }

    //Fields C06,C07,C08
    private SizeF printPatientBirthday(Graphics g, float X, float Y) throws Exception {
        text = dateToString(patient.Birthdate);
        return doc.drawField(g,isFrench ? "DATE DE NAISSANCE" : "BIRTHDATE",text,true,X,Y);
    }

    //Field C05
    private SizeF printPatientSex(Graphics g, float X, float Y) throws Exception {
        switch(patient.Gender)
        {
            case Male: 
                text = "M";
                break;
            default: 
                text = "F";
                break;
        
        }
        return doc.drawField(g,isFrench ? "SEXE" : "SEX",text,true,X,Y);
    }

    private SizeF printPolicyNo(Graphics g, float X, float Y, boolean primary) throws Exception {
        text = "";
        if (primary)
        {
            text = insplan.GroupNum;
        }
        else //Field C01
        if (insplan2 != null)
        {
            text = insplan2.GroupNum;
        }
          
        return doc.drawField(g,isFrench ? "NO DE POLICE" : "POLICY#",text,true,X,Y);
    }

    //Field E02
    private SizeF printDivisionSectionNo(Graphics g, float X, float Y) throws Exception {
        return doc.drawField(g,isFrench ? "NO DE DIVISION/SECTION" : "DIVISION/SECTION NO",insplan.DivisionNo,true,X,Y);
    }

    //Field C11
    private SizeF printCertificateNo(Graphics g, float X, float Y, boolean primary) throws Exception {
        if (primary)
        {
            text = insSub.SubscriberID;
        }
        else //Field C02
        if (insplan2 != null)
        {
            text = insSub2.SubscriberID;
        }
          
        //Field E03
        //The instructions for how to deal with NIHB plans came from the version 4.1 Message Formats Document, described in the data dictionary near field C02.
        CCDField fieldC12 = formData.getFieldById("C12");
        //Plan flag. 'N' for NIHB, 'A' for Newfoundland MCP Plan - Provincial Medical Plan, 'V' for Veteran's Affairs Plan. There may be other values.
        CCDField fieldC13 = formData.getFieldById("C13");
        //Band number for NIHB plans.
        CCDField fieldC14 = formData.getFieldById("C14");
        //Family number for NIHB plans.
        //NIHB stands for "Non-Insured Health Benefits" as defined at http://www.hc-sc.gc.ca/fniah-spnia/nihb-ssna/index-eng.php. Government based program.
        //For NIHB claims, print the Band (Field C13) and Family (Field C14) numbers as required.
        //If they have NIHB, then it is probably their primary and they probably don't have any other plan.
        if (fieldC12 != null && StringSupport.equals(fieldC12.valuestr, "N") && fieldC13 != null && !StringSupport.equals(fieldC13.valuestr.Trim(), "") && fieldC14 != null && !StringSupport.equals(fieldC14.valuestr.Trim(), ""))
        {
            return doc.drawString(g,isFrench ? ("BANDE: " + fieldC13.valuestr + "  FAMILLE: " + fieldC14.valuestr) : ("BAND: " + fieldC13.valuestr + "  FAMILY: " + fieldC14.valuestr),X,Y);
        }
         
        return doc.drawField(g,isFrench ? "NO DE CERTIFICAT" : "CERTIFICATE NO",text,true,X,Y);
    }

    /**
    * Print "sequence" number.
    */
    private SizeF printCardNo(Graphics g, float X, float Y) throws Exception {
        text = (insplan.DentaideCardSequence.ToString());
        return doc.drawField(g,isFrench ? "NO DE CARTE" : "CARD NO",StringSupport.equals(text, "0") ? "" : text,true,X,Y);
    }

    //Field D11
    private SizeF printPrimaryDependantNo(Graphics g, float X, float Y) throws Exception {
        return printPrimaryDependantNo(g,X,Y,"DEPENDANT NO","NO DE PERSONNE À CHARGE");
    }

    private SizeF printPrimaryDependantNo(Graphics g, float X, float Y, String fieldText, String frenchFieldText) throws Exception {
        String patid = "";
        if (patPlanPri != null)
        {
            patid = patPlanPri.PatID;
        }
         
        return doc.drawField(g,isFrench ? frenchFieldText : fieldText,patid,true,X,Y);
    }

    private SizeF printSecondaryDependantNo(Graphics g, float X, float Y) throws Exception {
        return printSecondaryDependantNo(g,X,Y,"DEPENDANT NO","NO DE PERSONNE À CHARGE");
    }

    private SizeF printSecondaryDependantNo(Graphics g, float X, float Y, String fieldText, String frenchFieldText) throws Exception {
        return doc.drawField(g,isFrench ? frenchFieldText : fieldText,patPlanSec.PatID,true,X,Y);
    }

    private SizeF printInsuredMember(Graphics g, float X, float Y) throws Exception {
        text = subscriber.getNameFLFormal();
        return doc.drawField(g,isFrench ? "TITULAIRE" : "INSURED/MEMBER",text,true,X,Y);
    }

    //Fields D02,D03,D04
    ///<summary>The output will be no wider than maxWidthInPixels, unless maxWidthInPixels<=0, in which case there is no maximum width.</summary>
    private SizeF printSubscriberAddress(Graphics g, float X, float Y, boolean primary, float maxWidthInPixels) throws Exception {
        String line1 = "";
        String line2 = "";
        String line3 = "";
        Patient sub = primary ? subscriber : subscriber2;
        if (sub != null)
        {
            //Primary: Fields D05,D06,D07,D08,D09
            //Secondary: Fields E11,E12,E13,E14,E15
            line1 = sub.Address;
            line2 = sub.Address2;
            line3 = sub.City + ", " + sub.State + " " + sub.Zip;
        }
         
        return printAddress(g,X,Y,line1,line2,line3,maxWidthInPixels,maxWidthInPixels);
    }

    /**
    * If maxCharsPerLine>0, then the lines which are excess in length are truncated to the value specified.
    */
    private SizeF printInsuredAddress(Graphics g, float X, float Y, boolean primary, int maxCharsPerLine) throws Exception {
        SizeF size1 = doc.drawString(g,isFrench ? "ADRESSE DU TITULAIRE: " : "INSURED/MEMBER ADDRESS: ",X,Y);
        SizeF size2 = PrintSubscriberAddress(g, X + size1.Width, Y, primary, maxCharsPerLine);
        return new SizeF(size1.Width + size2.Width, Math.Max(size1.Height, size2.Height));
    }

    /**
    * Pulls the relationship from the claim if not null. Otherwise pulls the claim from the primary patinet plan. If both are null, Self is returned.
    */
    private Relat getRelationshipToSubscriber() throws Exception {
        if (claim != null)
        {
            return claim.PatRelat;
        }
        else if (patPlanPri != null)
        {
            return patPlanPri.Relationship;
        }
          
        return Relat.Self;
    }

    /**
    * Corresponds to field C03.
    */
    private SizeF printRelationshipToSubscriber(Graphics g, float X, float Y, boolean useCaps) throws Exception {
        text = getPatientRelationshipString(getRelationshipToSubscriber());
        String engStr = "RELATIONSHIP TO INSURED/MEMBER";
        String frStr = "PARENTÉ AVEC TITULAIRE";
        String label = isFrench ? frStr : engStr;
        return doc.DrawField(g, useCaps ? label.ToUpper() : label, text, true, X, Y);
    }

    private SizeF printSubscriberBirthday(Graphics g, float X, float Y, boolean useCaps) throws Exception {
        text = dateToString(subscriber.Birthdate);
        String engStr = "Birthdate";
        String frStr = "Date de naissance";
        String label = isFrench ? frStr : engStr;
        return doc.DrawField(g, useCaps ? label.ToUpper() : label, text, true, X, Y);
    }

    ///<summary>Prints a three-line address. Each line is underlined and the address is printed without a label.
    ///The output will be no wider than maxWidthInPixels, unless maxWidthInPixels<=0, in which case there is no maximum width.</summary>
    private SizeF printAddress(Graphics g, float X, float Y, String line1, String line2, String line3, float minWidthInPixels, float maxWidthInPixels) throws Exception {
        line1 = getTruncatedString(g,doc.standardFont,line1,maxWidthInPixels);
        line2 = getTruncatedString(g,doc.standardFont,line2,maxWidthInPixels);
        line3 = getTruncatedString(g,doc.standardFont,line3,maxWidthInPixels);
        String address = line1 + "\n" + line2 + "\n" + line3;
        float lineWidth = Math.Max(minWidthInPixels, g.MeasureString(address, doc.standardFont).Width);
        float yoff = 0;
        doc.drawString(g,line1,X,Y + yoff,doc.standardFont);
        yoff += verticalLine;
        yoff += doc.HorizontalLine(g, Pens.Black, X, X + lineWidth, yoff).Height;
        doc.drawString(g,line2,X,Y + yoff,doc.standardFont);
        yoff += verticalLine;
        yoff += doc.HorizontalLine(g, Pens.Black, X, X + lineWidth, yoff).Height;
        doc.drawString(g,line3,X,Y + yoff,doc.standardFont);
        yoff += verticalLine;
        yoff += doc.HorizontalLine(g, Pens.Black, X, X + lineWidth, yoff).Height;
        return new SizeF(lineWidth, yoff);
    }

    ///<summary>If the specified string is wider than maxWidthInPixels on graphics object g in the specified font,
    ///then the longest substring of str is returned which is less than or equal to maxWidthInPixels in width,
    ///starting from the first character in str. If maxWidthInPixels<=0 then str is returned without modification.
    ///In all other cases, str is returned without modification.</summary>
    private String getTruncatedString(Graphics g, Font font, String str, float maxWidthInPixels) throws Exception {
        if (maxWidthInPixels <= 0)
        {
            return str;
        }
         
        String result = str;
        SizeF strSize = g.MeasureString(result, font);
        while (result.Length > 0 && strSize.Width > maxWidthInPixels)
        {
            //Reduce the size of the string until it fits within maxWidthInPixels.
            //Remove the last character from the string.
            result = result.Substring(0, result.Length - 1);
            //Set the last 3 characters in the string to '.' if possible.
            int chrsToReplace = Math.Min(3, result.Length);
            result = result.Substring(0, result.Length - chrsToReplace) + ("".PadRight(chrsToReplace, '.'));
            //Remeasure the new result and check again.
            strSize = g.MeasureString(result, font);
        }
        return result;
    }

    private boolean printDependencies(Graphics g, boolean fillOut) throws Exception {
        String isStudent = "   ";
        String isHandicapped = "   ";
        boolean stud = false;
        text = "";
        //Used for school name.
        if (fillOut)
        {
            System.Byte __dummyScrutVar6 = patient.CanadianEligibilityCode;
            //Field C09
            if (__dummyScrutVar6.equals(1))
            {
                //Patient is a full-time student.
                isStudent = isFrench ? "Oui" : "Yes";
                stud = true;
                text = patient.SchoolName;
            }
            else if (__dummyScrutVar6.equals(2))
            {
                //Patient is disabled.
                isHandicapped = isFrench ? "Oui" : "Yes";
            }
            else if (__dummyScrutVar6.equals(3))
            {
                //Patient is a disabled student.
                isStudent = isFrench ? "Oui" : "Yes";
                stud = true;
                text = patient.SchoolName;
                isHandicapped = isFrench ? "Oui" : "Yes";
            }
            else
            {
            }   
        }
         
        doc.pushX(x);
        x += doc.DrawString(g, isFrench ? "Personne à charge: Étudiant" : "If dependant, indicate: Student", x, 0).Width;
        float isStudentHeight = doc.DrawString(g, isStudent, x, 0).Height;
        //Spaces don't show up with underline, so we will have to underline manually.
        float underlineWidth = g.MeasureString("***", doc.standardFont).Width;
        doc.HorizontalLine(g, Pens.Black, x, x + underlineWidth, isStudentHeight);
        x += underlineWidth;
        x += doc.DrawString(g, isFrench ? " Handicapé" : " Handicapped", x, 0).Width;
        float isHandicappedHeight = doc.DrawString(g, isHandicapped, x, 0).Height;
        doc.HorizontalLine(g, Pens.Black, x, x + underlineWidth, isHandicappedHeight);
        x = doc.popX();
        return stud;
    }

    private void printDependencyBullet(Graphics g) throws Exception {
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Save indentation x-value for this list number.
        PrintRelationshipToSubscriber(g, x, 0, false);
        PrintSubscriberBirthday(g, x + 360, 0, false);
        x = doc.startElement();
        printDependencies(g,true);
        doc.DrawField(g, isFrench ? "Si étudiant nom de l'école" : "If Student, Name of student's school", patient.SchoolName, true, x + 360, 0);
        x = doc.popX();
    }

    //End indentation.
    private void printSecondaryPolicyBullet(Graphics g) throws Exception {
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Save indentation spot for this bullet point.
        text = isFrench ? "A-t-il droit à des prestations ou services dans un autre régime dentaire, régime collectif ou gouvernemental? " : "Are any Dental Benefits or services provided under any other group insurance or dental plan, WCB or gov’t plan? ";
        //Only print secondary coverage information on the primary claim report.
        if (thisIsPrimary() && insplan2 != null)
        {
            doc.DrawString(g, text + (isFrench ? "Oui" : "Yes"), x, 0);
            x = doc.startElement();
            PrintPolicyNo(g, x, 0, false);
            text = secondaryCarrier.CarrierName;
            doc.DrawField(g, isFrench ? "Nom de l'assureur/administrateur" : "Name of Insurer/Plan Administrator", text, true, x + 200, 0);
            x = doc.startElement();
            PrintCertificateNo(g, x, 0, false);
            PrintSecondaryDependantNo(g, x + 200, 0);
            text = dateToString(subscriber2.Birthdate);
            //Field E04
            doc.DrawField(g, isFrench ? "Date de naissance du titulaire" : "Insured/Member Date of Birth", text, true, x + 400, 0);
            x = doc.startElement();
            text = getPatientRelationshipString(patPlanSec.Relationship);
            //Field E06
            doc.DrawField(g, isFrench ? "Parenté avec patient" : "Relationship to Patient", text, true, x, 0);
        }
        else
        {
            doc.DrawString(g, text + (isFrench ? "Non" : "No"), x, 0);
        } 
        x = doc.popX();
    }

    //End indentation.
    private void printAccidentBullet(Graphics g) throws Exception {
        printAccidentBullet(g,isFrench ? "Y-a-t-il un traitement requis par suite d'un accident?" : "Is any treatment required as the result of an accident?");
    }

    private void printAccidentBullet(Graphics g, String questionStr) throws Exception {
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Begin indentation.
        x += doc.DrawString(g, questionStr + " ", x, 0).Width;
        boolean accident = (claim != null && isValidDate(claim.AccidentDate));
        if (!accident)
        {
            //Field F02 - No accident claimed.
            doc.DrawString(g, isFrench ? "Non" : "No", x, 0);
        }
        else
        {
            doc.DrawString(g, isFrench ? "Oui" : "Yes", x, 0);
        } 
        x = doc.startElement();
        x += doc.DrawField(g, isFrench ? "Si Oui, donner date" : "If yes, give date", (accident ? dateToString(claim.AccidentDate) : "________") + " ", true, x, 0).Width;
        doc.DrawString(g, isFrench ? "et détails à part:" : "and details separately:", x, 0);
        x = doc.startElement();
        doc.DrawString(g, accident ? claim.ClaimNote : "", x, 0);
        x = doc.popX();
    }

    //End indentation.
    private void printInitialPlacementBullet(Graphics g) throws Exception {
        String initialPlacementUpper = "X";
        CCDField initialPlacementUpperField = formData.getFieldById("F15");
        if (initialPlacementUpperField != null)
        {
            initialPlacementUpper = initialPlacementUpperField.valuestr;
        }
        else if (claim != null)
        {
            initialPlacementUpper = claim.CanadianIsInitialUpper;
        }
          
        String initialPlacementLower = "X";
        CCDField initialPlacementLowerField = formData.getFieldById("F18");
        if (initialPlacementLowerField != null)
        {
            initialPlacementLower = initialPlacementLowerField.valuestr;
        }
        else if (claim != null)
        {
            initialPlacementLower = claim.CanadianIsInitialLower;
        }
          
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Begin indentation.
        doc.DrawString(g, isFrench ? "Prothèse, couronne ou pont: est-ce la première mise en bouche?" : "If Denture, crown or bridge, Is this the initial placement?", x, 0);
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Maxillaire: " : "Upper: ", x, 0);
        x += 80;
        doc.pushX(x);
        //Begin second indentation.
        if (StringSupport.equals(initialPlacementUpper, "N"))
        {
            doc.DrawString(g, isFrench ? "Non" : "No", x, 0);
            x = doc.startElement();
            text = GetMaterialDescription(claim.CanadianMaxProsthMaterial);
            //Field F20
            doc.DrawField(g, isFrench ? "Matériau initial" : "Initial Material", text, true, x, 0);
            x = doc.startElement();
            text = dateToString(claim.CanadianDateInitialUpper);
            //Field F04
            doc.DrawField(g, isFrench ? "Date de mise en bouche" : "Placement Date", text, true, x, 0);
            x = doc.startElement();
            text = getAllProcedureTypeDescriptions();
            doc.DrawField(g, isFrench ? "Motif du remplacement" : "Reason for replacement", text, true, x, 0);
        }
        else if (StringSupport.equals(initialPlacementUpper, "Y"))
        {
            doc.DrawString(g, isFrench ? "Oui" : "Yes", x, 0);
        }
          
        x = doc.popX();
        //End second indentation.
        x = doc.startElement();
        doc.DrawString(g, isFrench ? "Mandibule: " : "Lower: ", x, 0);
        x += 80;
        doc.pushX(x);
        //Begin second indentation.
        if (StringSupport.equals(initialPlacementLower, "N"))
        {
            doc.DrawString(g, isFrench ? "Non" : "No", x, 0);
            x = doc.startElement();
            text = GetMaterialDescription(claim.CanadianMandProsthMaterial);
            //Field F21
            doc.DrawField(g, isFrench ? "Matériau initial" : "Initial Material", text, true, x, 0);
            x = doc.startElement();
            text = dateToString(claim.CanadianDateInitialLower);
            //Field F19
            doc.DrawField(g, isFrench ? "Date de mise en bouche" : "Placement Date", text, true, x, 0);
            x = doc.startElement();
            text = getAllProcedureTypeDescriptions();
            doc.DrawField(g, isFrench ? "Motif du remplacement" : "Reason for replacement", text, true, x, 0);
        }
        else if (StringSupport.equals(initialPlacementLower, "Y"))
        {
            doc.DrawString(g, isFrench ? "Oui" : "Yes", x, 0);
        }
          
        x = doc.popX();
        //End second indentation.
        x = doc.popX();
    }

    //End first indentation.
    private void printToothExtractionBullet(Graphics g) throws Exception {
        x += doc.DrawString(g, bullet.ToString() + ". ", x, 0).Width;
        bullet++;
        doc.pushX(x);
        //Begin indentation.
        x += doc.DrawString(g, isFrench ? "S'agit-il d'un traitement en vue de soins d'orthodontie? " : "Is any treatment provided for orthodontic purposes? ", x, 0).Width;
        if (claim != null && claim.IsOrtho)
        {
            //Field F05
            doc.DrawString(g, isFrench ? "Oui" : "Yes", x, 0);
            x = doc.startElement();
            printMissingToothList(g);
        }
        else
        {
            doc.DrawString(g, isFrench ? "Non" : "No", x, 0);
        } 
        x = doc.popX();
    }

    //End indentation.
    private void printPaymentSummary(Graphics g) throws Exception {
        float amountWidth = (float)Math.Ceiling((double)g.MeasureString("******.**", doc.standardFont).Width);
        float valuesBlockOffset = x + 566;
        text = (isFrench ? "TOTAL DEMANDÉ:" : "TOTAL DENTIST CHARGES:");
        doc.DrawString(g, text, valuesBlockOffset - g.MeasureString(text, doc.standardFont).Width - 5, 0);
        text = rawMoneyStrToDisplayMoney(formData.getFieldById("G04").valuestr);
        doc.DrawString(g, text, valuesBlockOffset + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
        x = doc.startElement();
        text = isFrench ? "TOTAL DEDUCTIBLES NON-ALLOCER:" : "DEDUCTIBLE NOT ALLOCATED:";
        doc.DrawString(g, text, valuesBlockOffset - g.MeasureString(text, doc.standardFont).Width - 5, 0);
        text = rawMoneyStrToDisplayMoney(formData.getFieldById("G29").valuestr);
        doc.DrawString(g, text, valuesBlockOffset + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
        x = doc.startElement();
        String expPayDateStr = "";
        CCDField fieldG03 = formData.getFieldById("G03");
        if (fieldG03 != null && !StringSupport.equals(fieldG03.valuestr, "00000000"))
        {
            expPayDateStr = dateNumToPrintDate(fieldG03.valuestr);
            doc.DrawField(g, isFrench ? "DATE PRÉVUE DU PAIEMENT" : "EXPECTED PAYMENT DATE", expPayDateStr, true, x, 0);
        }
         
        CCDField f01 = formData.getFieldById("F01");
        //G55 exists in version 04 sometimes, but never in version 02 messages. When G55 is available, it includes adjustments that G28 does not include.
        CCDField totalPayable = formData.getFieldById("G55");
        if (totalPayable == null)
        {
            totalPayable = formData.getFieldById("G28");
        }
         
        //For cases when field f01 is not present, we are supposed to grab the value determining who the payment is for from the original claim,
        //but we must instead rely on the assignment of benefits flag associated with the primary insurance subscriber because there is no such field
        //in the claim object itself.
        String payableTo = (f01 == null) ? (assignmentOfBenefits() ? "4" : "1") : f01.valuestr;
        if (StringSupport.equals(payableTo, "1"))
        {
            //Pay the subscriber.
            text = isFrench ? "TOTAL REMBOURSABLE AU TITULAIRE:" : "TOTAL PAYABLE TO INSURED:";
            doc.DrawString(g, text, valuesBlockOffset - g.MeasureString(text, doc.standardFont).Width - 5, 0);
            text = rawMoneyStrToDisplayMoney(totalPayable.valuestr);
            doc.DrawString(g, text, valuesBlockOffset + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            x = doc.startElement();
            text = isFrench ? "ADRESSE DU DESTINATAIRE DU PAIEMENT:" : "PAYEE'S ADDRESS:";
            SizeF size1 = doc.DrawString(g, text, x, 0);
            text = Patients.getAddressFull(patient.Address,patient.Address2,patient.City,patient.State,patient.Zip);
            doc.DrawString(g, text, x + size1.Width + 10, 0);
        }
        else if (StringSupport.equals(payableTo, "2"))
        {
            //Pay other party.
            text = isFrench ? "TOTAL REMBOURSABLE AU AUTRES:" : "TOTAL PAYABLE TO OTHER:";
            doc.DrawString(g, text, valuesBlockOffset - g.MeasureString(text, doc.standardFont).Width - 5, 0);
            text = rawMoneyStrToDisplayMoney(totalPayable.valuestr);
            doc.DrawString(g, text, valuesBlockOffset + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            x = doc.startElement();
        }
        else if (StringSupport.equals(payableTo, "3"))
        {
        }
        else //Reserved
        if (StringSupport.equals(payableTo, "4") || StringSupport.equals(payableTo, "0"))
        {
            //Dentist
            text = isFrench ? "TOTAL REMBOURSABLE AU DENTISTE:" : "TOTAL PAYABLE TO DENTIST:";
            doc.DrawString(g, text, valuesBlockOffset - g.MeasureString(text, doc.standardFont).Width - 5, 0);
            text = rawMoneyStrToDisplayMoney(totalPayable.valuestr);
            doc.DrawString(g, text, valuesBlockOffset + amountWidth - g.MeasureString(text, doc.standardFont).Width, 0);
            x = doc.startElement();
            text = isFrench ? "ADRESSE DU DESTINATAIRE DU PAIEMENT:" : "PAYEE'S ADDRESS:";
            SizeF size1 = doc.DrawString(g, text, x, 0);
            printPracticeAddress(g,x + size1.Width + 10);
        }
            
        x = doc.startElement();
    }

    private void printPracticeAddress(Graphics g, float xPos) throws Exception {
        if (clinic == null)
        {
            if (PrefC.getBool(PrefName.UseBillingAddressOnClaims))
            {
                text = Patients.getAddressFull(PrefC.getString(PrefName.PracticeBillingAddress),PrefC.getString(PrefName.PracticeBillingAddress2),PrefC.getString(PrefName.PracticeBillingCity),PrefC.getString(PrefName.PracticeBillingST),PrefC.getString(PrefName.PracticeBillingZip));
            }
            else
            {
                text = Patients.getAddressFull(PrefC.getString(PrefName.PracticeAddress),PrefC.getString(PrefName.PracticeAddress2),PrefC.getString(PrefName.PracticeCity),PrefC.getString(PrefName.PracticeST),PrefC.getString(PrefName.PracticeZip));
            } 
        }
        else
        {
            text = Patients.getAddressFull(clinic.Address,clinic.Address2,clinic.City,clinic.State,clinic.Zip);
        } 
        doc.DrawString(g, text, xPos, 0);
    }

    private void printMissingToothList(Graphics g) throws Exception {
        CCDField initialPlacementUpperField = formData.getFieldById("F15");
        CCDField initialPlacementLowerField = formData.getFieldById("F18");
        if (initialPlacementUpperField != null && initialPlacementLowerField != null && (StringSupport.equals(initialPlacementUpperField.valuestr, "Y") || StringSupport.equals(initialPlacementUpperField.valuestr, "O")) && (StringSupport.equals(initialPlacementLowerField.valuestr, "Y") || StringSupport.equals(initialPlacementLowerField.valuestr, "O")))
        {
            //Only print extractions when F15 or F18 are "Yes"
            String title = (isFrench ? "D#" : "TH") + "  DATE(YYYYMMDD)\t";
            float titleWidth = g.MeasureString(title, doc.standardFont).Width;
            int cycleOrthoDateCount = (int)((doc.bounds.Right - x) / titleWidth);
            for (int i = 0;i < Math.Min(cycleOrthoDateCount, extracted.Count);i++)
            {
                x += doc.DrawString(g, title, x, 0).Width;
            }
            int j = 0;
            for (int i = 0;i < extracted.Count;i++)
            {
                //Count specified by field F22
                if (j % cycleOrthoDateCount == 0)
                {
                    x = doc.startElement();
                }
                 
                if (IsValidDate(extracted[i].ProcDate))
                {
                    //Tooth is considered unextracted if it doesn't have a date.
                    float thWidth = doc.DrawString(g, Tooth.ToInternat(extracted[i].ToothNum).PadLeft(2, ' ') + " ", x, 0).Width;
                    //Field F23
                    text = " " + DateToString(extracted[i].ProcDate);
                    doc.DrawString(g, text, x + thWidth, 0);
                    x += titleWidth;
                    j++;
                }
                 
            }
        }
         
    }

    private void printNoteList(Graphics g) throws Exception {
        CCDField[] noteOutputFlags = formData.getFieldsById("G41");
        CCDField[] noteNumbers = formData.getFieldsById("G45");
        CCDField[] noteTexts = formData.getFieldsById("G26");
        List<String> displayMessages = new List<String>();
        List<int> displayMessageNumbers = new List<int>();
        doc.DrawString(g, "NOTES: ", x, 0, headingFont);
        doc.startElement(verticalLine);
        for (int i = 0;i < noteTexts.Length;i++)
        {
            //noteTexts.Length<=32
            if (i < noteOutputFlags.Length)
            {
                //Sometimes G26 exists without the output flags or the note numbers.
                if (PIn.Int(noteOutputFlags[i].valuestr) == 1)
                {
                    continue;
                }
                 
            }
             
            //We will print the notes if either the output flag is 2 (print) or 0 (prompt), but will not print notes with output flag 1 (display notes on screen).
            if (i < noteNumbers.Length)
            {
                displayMessageNumbers.Add(PIn.Int(noteNumbers[i].valuestr));
            }
            else
            {
                displayMessageNumbers.Add(i + 1);
            } 
            displayMessages.Add(noteTexts[i].valuestr);
        }
        while (displayMessages.Count > 0)
        {
            int indexOfMinVal = 0;
            for (int j = 1;j < displayMessageNumbers.Count;j++)
            {
                if (displayMessageNumbers[j] < displayMessageNumbers[indexOfMinVal])
                {
                    indexOfMinVal = j;
                }
                 
            }
            doc.startElement();
            doc.DrawString(g, displayMessageNumbers[indexOfMinVal].ToString().PadLeft(2, '0'), x, 0);
            doc.DrawString(g, displayMessages[indexOfMinVal], x + 50, 0);
            displayMessages.RemoveAt(indexOfMinVal);
            displayMessageNumbers.RemoveAt(indexOfMinVal);
        }
    }

    /**
    * Returns the number of errors displayed.
    */
    private int printErrorList(Graphics g) throws Exception {
        CCDField[] errors = formData.getFieldsById("G08");
        if (errors == null)
        {
            return 0;
        }
         
        doc.DrawString(g, (isFrench ? "ERREURS (" : "ERRORS (") + errors.Length + ")", x, 0, headingFont);
        for (int i = 0;i < errors.Length;i++)
        {
            x = doc.startElement();
            doc.DrawString(g, errors[i].valuestr.PadLeft(3, '0'), x, 0);
            doc.DrawString(g, CCDerror.message(Convert.ToInt32(errors[i].valuestr), isFrench), x + 80, 0);
        }
        return errors.Length;
    }

    private boolean assignmentOfBenefits() throws Exception {
        if (claim != null)
        {
            if (StringSupport.equals(claim.ClaimType, "S"))
            {
                if (insSub2 != null)
                {
                    return insSub2.AssignBen;
                }
                 
            }
             
            if (insSub != null)
            {
                return insSub.AssignBen;
            }
             
        }
         
        return false;
    }

    private Carrier getCarrier() throws Exception {
        String carrierIdentificationNumber = formData.getFieldById("A05").valuestr;
        //Exists in all formats but 24-Email, and 16-Payment Reconciliation Response
        if (primaryCarrier != null && StringSupport.equals(primaryCarrier.ElectID, carrierIdentificationNumber))
        {
            return primaryCarrier;
        }
        else if (secondaryCarrier != null && StringSupport.equals(secondaryCarrier.ElectID, carrierIdentificationNumber))
        {
            return secondaryCarrier;
        }
          
        return primaryCarrier;
    }

    //This return statement should never happen.
    private boolean thisIsPrimary() throws Exception {
        String carrierIdentificationNumber = formData.getFieldById("A05").valuestr;
        return (primaryCarrier != null && StringSupport.equals(primaryCarrier.ElectID, carrierIdentificationNumber));
    }

    //Exists in all formats but 24-Email, and 16-Payment Reconciliation Response
    private boolean thisIsSecondary() throws Exception {
        String carrierIdentificationNumber = formData.getFieldById("A05").valuestr;
        return (secondaryCarrier != null && StringSupport.equals(secondaryCarrier.ElectID, carrierIdentificationNumber));
    }

    //Exists in all formats but 24-Email, and 16-Payment Reconciliation Response
    /**
    * The rawPercent string should be of length 3 and should be numerical digits only.
    */
    private String rawPercentToDisplayPercent(String rawPercent) throws Exception {
        return rawPercent.TrimStart('0').PadLeft(1, '0') + "%";
    }

    private boolean isValidDate(DateTime dt) throws Exception {
        return (dt.Year > 1880);
    }

    private String dateToString(DateTime dt) throws Exception {
        if (isValidDate(dt))
        {
            return dt.ToShortDateString();
        }
         
        return "";
    }

    //Invalid date.
    /**
    * Input string is expected to have the form 'YYYYMMDD'.
    */
    private String dateNumToPrintDate(String number) throws Exception {
        if (StringSupport.equals(number, "00000000"))
        {
            return DateToString(DateTime.MinValue);
        }
         
        DateTime dt = new DateTime(Convert.ToInt32(number.Substring(0, 4)), Convert.ToInt32(number.Substring(4, 2)), Convert.ToInt32(number.Substring(6, 2)));
        return dateToString(dt);
    }

    /**
    * The given number must be in the format of: [+-]?[0-9]*
    */
    private String rawMoneyStrToDisplayMoney(String number) throws Exception {
        String sign = "";
        if (number.Length > 0 && (number[0] == '+' || number[0] == '-'))
        {
            sign = number[0].ToString();
            number = number.Substring(1, number.Length - 1);
        }
         
        number = number.PadLeft(2, '0');
        return sign + number.Substring(0, number.Length - 2).TrimStart('0').PadLeft(1, '0') + "." + number.Substring(number.Length - 2, 2);
    }

    //Guarantee at least 2 digits of length.
    private String getPayableToString(boolean assignBen) throws Exception {
        if (assignBen)
        {
            return isFrench ? "DENTISTE" : "DENTIST";
        }
        else
        {
            return isFrench ? "TITULAIRE" : "INSURED/MEMBER";
        } 
    }

    /**
    * Convert a patient relationship enum value into a human-readable, CDA required string.
    */
    private String getPatientRelationshipString(Relat relat) throws Exception {
        System.String __dummyScrutVar7 = Canadian.getRelationshipCode(relat);
        if (__dummyScrutVar7.equals("1"))
        {
            return isFrench ? "Soi-même" : "Self";
        }
        else if (__dummyScrutVar7.equals("2"))
        {
            return isFrench ? "Époux(se)" : "Spouse";
        }
        else if (__dummyScrutVar7.equals("3"))
        {
            return isFrench ? "Enfant" : "Child";
        }
        else if (__dummyScrutVar7.equals("4"))
        {
            return isFrench ? "Conjoint(e)" : "Common Law Spouse";
        }
        else if (__dummyScrutVar7.equals("5"))
        {
            return isFrench ? "Autre" : "Other";
        }
        else
        {
        }     
        return "";
    }

    /**
    * Convet a code from fields F20 and F21 into a human-readable string.
    */
    private String getMaterialDescription(int materialCode) throws Exception {
        switch(materialCode)
        {
            case 1: 
                return isFrench ? "Pont fixe" : "Fixed Bridge";
            case 2: 
                return isFrench ? "Pont Maryland" : "Maryland Bridge";
            case 3: 
                return isFrench ? "Prothèse (acrylique)" : "Denture (Acrylic)";
            case 4: 
                return isFrench ? "Prothèse (chrome cobalt)" : "Denture (Chrome Cobalt)";
            case 5: 
                return isFrench ? "Implant (fixe)" : "Implant (Fixed)";
            case 6: 
                return isFrench ? "Implant (démontable)" : "Implant (Removable)";
            case 7: 
                return isFrench ? "Implant (amovible)" : "Crown";
            default: 
                break;
        
        }
        return "";
    }

    /**
    * Convert one of the type codes from field F16 into a human-readable string.
    */
    private String getProcedureTypeCodeDescription(char procedureTypeCode) throws Exception {
        switch(procedureTypeCode)
        {
            case 'A': 
                return isFrench ? "Réparation d’un traitement ou appareil; si non spécifié, il s’agit d’une mise en bouche initiale." : "Repair of a prior service or installation.";
            case 'B': 
                return isFrench ? "Mise en bouche ou traitement temporaire; si non spécifié, il s’agit d’une mise en bouche ou traitement permanent." : "Temporary placement or service.";
            case 'C': 
                return isFrench ? "Correction d’un appareil ATM." : "Service for correction of TMJ.";
            case 'E': 
                return isFrench ? "Traitement est un implant ou est exécuté conjointement avec un implant." : "Service is an implant or is performed in conjunction with implants.";
            case 'L': 
                return isFrench ? "Appareil perdu." : "Appliance lost.";
            case 'S': 
                return isFrench ? "Appareil volé." : "Appliance stolen.";
            case 'X': 
                return isFrench ? "Aucun de ces choix." : "Abnormal circumstances.";
            default: 
                break;
        
        }
        return "";
    }

    /**
    * If field F16 is defined for the current message, then the codes within it are transcribed into a paragraph of text.
    */
    private String getAllProcedureTypeDescriptions() throws Exception {
        String text = "";
        CCDField procedureTypeCodes = formData.getFieldById("F16");
        if (procedureTypeCodes != null)
        {
            for (int c = 0;c < procedureTypeCodes.valuestr.Length;c++)
            {
                if (procedureTypeCodes.valuestr[c] != ' ')
                {
                    if (!StringSupport.equals(text, ""))
                    {
                        text += Environment.NewLine;
                    }
                     
                    text += GetProcedureTypeCodeDescription(procedureTypeCodes.valuestr[c]);
                }
                 
            }
        }
         
        return text;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCCDPrint.class);
        this.printPreviewControl1 = new System.Windows.Forms.PrintPreviewControl();
        this.labelPage = new System.Windows.Forms.Label();
        this.butForward = new OpenDental.UI.Button();
        this.butBack = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butPrintDentist = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // printPreviewControl1
        //
        this.printPreviewControl1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.printPreviewControl1.Location = new System.Drawing.Point(0, 0);
        this.printPreviewControl1.Name = "printPreviewControl1";
        this.printPreviewControl1.Size = new System.Drawing.Size(888, 657);
        this.printPreviewControl1.TabIndex = 0;
        //
        // labelPage
        //
        this.labelPage.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.labelPage.AutoSize = true;
        this.labelPage.Location = new System.Drawing.Point(442, 667);
        this.labelPage.Name = "labelPage";
        this.labelPage.Size = new System.Drawing.Size(24, 13);
        this.labelPage.TabIndex = 3;
        this.labelPage.Text = "0/0";
        //
        // butForward
        //
        this.butForward.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butForward.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.butForward.setAutosize(true);
        this.butForward.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butForward.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butForward.setCornerRadius(4F);
        this.butForward.Location = new System.Drawing.Point(472, 662);
        this.butForward.Name = "butForward";
        this.butForward.Size = new System.Drawing.Size(75, 23);
        this.butForward.TabIndex = 6;
        this.butForward.Text = "Forward";
        this.butForward.UseVisualStyleBackColor = true;
        this.butForward.Click += new System.EventHandler(this.butFwd_Click);
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.setCornerRadius(4F);
        this.butBack.Location = new System.Drawing.Point(361, 662);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(75, 23);
        this.butBack.TabIndex = 5;
        this.butBack.Text = "Back";
        this.butBack.UseVisualStyleBackColor = true;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Location = new System.Drawing.Point(638, 663);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(116, 23);
        this.butPrint.TabIndex = 4;
        this.butPrint.Text = "Print Patient Copy";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butPrintDentist
        //
        this.butPrintDentist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintDentist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrintDentist.setAutosize(true);
        this.butPrintDentist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintDentist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintDentist.setCornerRadius(4F);
        this.butPrintDentist.Location = new System.Drawing.Point(760, 663);
        this.butPrintDentist.Name = "butPrintDentist";
        this.butPrintDentist.Size = new System.Drawing.Size(116, 23);
        this.butPrintDentist.TabIndex = 7;
        this.butPrintDentist.Text = "Print Dentist Copy";
        this.butPrintDentist.UseVisualStyleBackColor = true;
        this.butPrintDentist.Click += new System.EventHandler(this.butPrintDentist_Click);
        //
        // FormCCDPrint
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(888, 690);
        this.Controls.Add(this.butPrintDentist);
        this.Controls.Add(this.butForward);
        this.Controls.Add(this.butBack);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.labelPage);
        this.Controls.Add(this.printPreviewControl1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormCCDPrint";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Form1";
        this.TopMost = true;
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Resize += new System.EventHandler(this.FormCCDPrint_Resize);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.PrintPreviewControl printPreviewControl1 = new System.Windows.Forms.PrintPreviewControl();
    private System.Windows.Forms.Label labelPage = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butBack;
    private OpenDental.UI.Button butForward;
    private OpenDental.UI.Button butPrintDentist;
}


