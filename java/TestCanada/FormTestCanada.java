//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDental.FormEtransEdit;
import OpenDental.PIn;
import OpenDentBusiness.Claim;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Prefs;
import TestCanada.CarrierTC;
import TestCanada.ClaimTC;
import TestCanada.DatabaseTools;
import TestCanada.Eligibility;
import TestCanada.OutstandingTrans;
import TestCanada.PatientTC;
import TestCanada.PaymentReconciliation;
import TestCanada.PredeterminationTC;
import TestCanada.ProviderTC;
import TestCanada.Reversal;
import TestCanada.SummaryReconciliation;

public class FormTestCanada  extends Form 
{

    private static String dbname = "canadatest";
    public FormTestCanada() throws Exception {
        initializeComponent();
    }

    private void butNewDb_Click(Object sender, EventArgs e) throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!DatabaseTools.setDbConnection(""))
        {
            MessageBox.Show("Could not connect");
            return ;
        }
         
        DatabaseTools.freshFromDump();
        textResults.Text += "Fresh database loaded from sql dump.";
        Cursor = Cursors.Default;
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!DatabaseTools.setDbConnection(dbname))
        {
            //if database doesn't exist
            //MessageBox.Show("Database canadatest does not exist.");
            DatabaseTools.setDbConnection("");
            textResults.Text += DatabaseTools.freshFromDump();
        }
         
        //this also sets database to be unittest.
        textResults.Text += DatabaseTools.clearDb();
        textResults.Text += "Done.";
        Cursor = Cursors.Default;
    }

    private void butObjects_Click(Object sender, EventArgs e) throws Exception {
        fillObjects();
        textResults.Text += "Done.";
        Cursor = Cursors.Default;
    }

    private void fillObjects() throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!DatabaseTools.setDbConnection("canadatest"))
        {
            //if database doesn't exist
            //MessageBox.Show("Database canadatest does not exist.");
            DatabaseTools.setDbConnection("");
            textResults.Text += DatabaseTools.freshFromDump();
        }
        else
        {
            //this also sets database to be unittest.
            textResults.Text += DatabaseTools.clearDb();
        } 
        Prefs.refreshCache();
        textResults.Text += ProviderTC.setInitialProviders();
        Application.DoEvents();
        textResults.Text += CarrierTC.setInitialCarriers();
        Application.DoEvents();
        textResults.Text += PatientTC.setInitialPatients();
        Application.DoEvents();
        textResults.Text += ClaimTC.createAllClaims();
        Application.DoEvents();
        textResults.Text += PredeterminationTC.createAllPredeterminations();
        Application.DoEvents();
    }

    private void butScripts_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textSingleScript.Text, ""))
        {
            MessageBox.Show("Please enter a script number first.");
            return ;
        }
         
        int singleScript = 0;
        try
        {
            singleScript = PIn.Int(textSingleScript.Text);
            if (singleScript == 0)
            {
                MessageBox.Show("Invalid number.");
                return ;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Invalid number.");
            return ;
        }

        int checkedCount = 0;
        if (checkEligibility.Checked)
        {
            checkedCount++;
        }
         
        if (checkClaims.Checked)
        {
            checkedCount++;
        }
         
        if (checkClaimReversals.Checked)
        {
            checkedCount++;
        }
         
        if (checkOutstanding.Checked)
        {
            checkedCount++;
        }
         
        if (checkPredeterm.Checked)
        {
            checkedCount++;
        }
         
        if (checkPayReconcil.Checked)
        {
            checkedCount++;
        }
         
        if (checkSumReconcil.Checked)
        {
            checkedCount++;
        }
         
        if (checkedCount == 0)
        {
            MessageBox.Show("Please select a category.");
            return ;
        }
         
        fillObjects();
        textResults.Text += "---------------------------------------\r\n";
        Application.DoEvents();
        if (checkEligibility.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += Eligibility.RunOne(checkShowForms.Checked);
            }
            else if (singleScript == 2)
            {
                textResults.Text += Eligibility.RunTwo(checkShowForms.Checked);
            }
            else if (singleScript == 3)
            {
                textResults.Text += Eligibility.RunThree(checkShowForms.Checked);
            }
            else if (singleScript == 4)
            {
                textResults.Text += Eligibility.RunFour(checkShowForms.Checked);
            }
            else if (singleScript == 5)
            {
                textResults.Text += Eligibility.RunFive(checkShowForms.Checked);
            }
            else if (singleScript == 6)
            {
                textResults.Text += Eligibility.RunSix(checkShowForms.Checked);
            }
            else
            {
                MessageBox.Show("Script number not found.");
                return ;
            }      
        }
         
        if (checkClaims.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += ClaimTC.RunOne(checkShowForms.Checked);
            }
            else if (singleScript == 2)
            {
                textResults.Text += ClaimTC.RunTwo(checkShowForms.Checked);
            }
            else if (singleScript == 3)
            {
                textResults.Text += ClaimTC.RunThree(checkShowForms.Checked);
            }
            else if (singleScript == 4)
            {
                textResults.Text += ClaimTC.RunFour(checkShowForms.Checked);
            }
            else if (singleScript == 5)
            {
                textResults.Text += ClaimTC.RunFive(checkShowForms.Checked);
            }
            else if (singleScript == 6)
            {
                textResults.Text += ClaimTC.RunSix(checkShowForms.Checked);
            }
            else if (singleScript == 7)
            {
                textResults.Text += ClaimTC.RunSeven(checkShowForms.Checked);
            }
            else if (singleScript == 8)
            {
                textResults.Text += ClaimTC.RunEight(checkShowForms.Checked);
            }
            else if (singleScript == 9)
            {
                textResults.Text += ClaimTC.RunNine(checkShowForms.Checked);
            }
            else if (singleScript == 10)
            {
                textResults.Text += ClaimTC.RunTen(checkShowForms.Checked);
            }
            else if (singleScript == 11)
            {
                textResults.Text += ClaimTC.RunEleven(checkShowForms.Checked);
            }
            else if (singleScript == 12)
            {
                textResults.Text += ClaimTC.RunTwelve(checkShowForms.Checked);
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }            
        }
         
        if (checkClaimReversals.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += Reversal.runOne();
            }
            else if (singleScript == 2)
            {
                textResults.Text += Reversal.runTwo();
            }
            else if (singleScript == 3)
            {
                textResults.Text += Reversal.runThree();
            }
            else if (singleScript == 4)
            {
                textResults.Text += Reversal.runFour();
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }    
        }
         
        if (checkOutstanding.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += OutstandingTrans.runOne();
            }
            else if (singleScript == 2)
            {
                textResults.Text += OutstandingTrans.runTwo();
            }
            else if (singleScript == 3)
            {
                textResults.Text += OutstandingTrans.runThree();
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }   
        }
         
        if (checkPredeterm.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += PredeterminationTC.RunOne(checkShowForms.Checked);
            }
            else if (singleScript == 2)
            {
                textResults.Text += PredeterminationTC.RunTwo(checkShowForms.Checked);
            }
            else if (singleScript == 3)
            {
                textResults.Text += PredeterminationTC.RunThree(checkShowForms.Checked);
            }
            else if (singleScript == 4)
            {
                textResults.Text += PredeterminationTC.RunFour(checkShowForms.Checked);
            }
            else if (singleScript == 5)
            {
                textResults.Text += PredeterminationTC.RunFive(checkShowForms.Checked);
            }
            else if (singleScript == 6)
            {
                textResults.Text += PredeterminationTC.RunSix(checkShowForms.Checked);
            }
            else if (singleScript == 7)
            {
                textResults.Text += PredeterminationTC.RunSeven(checkShowForms.Checked);
            }
            else if (singleScript == 8)
            {
                textResults.Text += PredeterminationTC.RunEight(checkShowForms.Checked);
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }        
        }
         
        if (checkPayReconcil.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += PaymentReconciliation.runOne();
            }
            else if (singleScript == 2)
            {
                textResults.Text += PaymentReconciliation.runTwo();
            }
            else if (singleScript == 3)
            {
                textResults.Text += PaymentReconciliation.runThree();
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }   
        }
         
        if (checkSumReconcil.Checked)
        {
            if (singleScript == 1)
            {
                textResults.Text += SummaryReconciliation.runOne();
            }
            else if (singleScript == 2)
            {
                textResults.Text += SummaryReconciliation.runTwo();
            }
            else if (singleScript == 3)
            {
                textResults.Text += SummaryReconciliation.runThree();
            }
            else
            {
                MessageBox.Show("Script number not found (not implemented yet).");
                return ;
            }   
        }
         
        textResults.Text += "Done.";
        Cursor = Cursors.Default;
    }

    private void checkEligibility_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkEligibility);
    }

    private void checkClaims_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkClaims);
    }

    private void checkClaimReversals_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkClaimReversals);
    }

    private void checkOutstanding_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkOutstanding);
    }

    private void checkPredeterm_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkPredeterm);
    }

    private void checkPayReconcil_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkPayReconcil);
    }

    private void checkSumReconcil_Click(Object sender, EventArgs e) throws Exception {
        UncheckAllExcept(checkSumReconcil);
    }

    private void uncheckAllExcept(CheckBox checkbox) throws Exception {
        if (checkbox != checkEligibility)
        {
            checkEligibility.Checked = false;
        }
         
        if (checkbox != checkClaims)
        {
            checkClaims.Checked = false;
        }
         
        if (checkbox != checkClaimReversals)
        {
            checkClaimReversals.Checked = false;
        }
         
        if (checkbox != checkOutstanding)
        {
            checkOutstanding.Checked = false;
        }
         
        if (checkbox != checkPredeterm)
        {
            checkPredeterm.Checked = false;
        }
         
        if (checkbox != checkPayReconcil)
        {
            checkPayReconcil.Checked = false;
        }
         
        if (checkbox != checkSumReconcil)
        {
            checkSumReconcil.Checked = false;
        }
         
    }

    private void butShowEtrans_Click(Object sender, EventArgs e) throws Exception {
        if (!checkClaims.Checked)
        {
            MessageBox.Show("Only works for claims right now.");
            return ;
        }
         
        //In case the form was just opened
        DatabaseTools.setDbConnection(dbname);
        int scriptNum = PIn.Int(textSingleScript.Text);
        long patNum = 0;
        double claimFee = 0;
        String predeterm = "";
        switch(scriptNum)
        {
            case 1: 
                patNum = Patients.getPatNumByNameAndBirthday("Fête","Lisa",new DateTime(1960, 4, 12));
                claimFee = 222.35;
                break;
            case 2: 
                patNum = Patients.getPatNumByNameAndBirthday("Fête","Lisa",new DateTime(1960, 4, 12));
                claimFee = 1254.85;
                break;
            case 3: 
                patNum = Patients.getPatNumByNameAndBirthday("Smith","John",new DateTime(1948, 3, 2));
                claimFee = 439.55;
                break;
            case 4: 
                patNum = Patients.getPatNumByNameAndBirthday("Smith","John",new DateTime(1988, 11, 2));
                claimFee = 222.35;
                break;
            case 5: 
                patNum = Patients.getPatNumByNameAndBirthday("Howard","Bob",new DateTime(1964, 5, 16));
                claimFee = 222.35;
                break;
            case 6: 
                patNum = Patients.getPatNumByNameAndBirthday("Howard","Bob",new DateTime(1964, 5, 16));
                claimFee = 232.35;
                break;
            case 7: 
                patNum = Patients.getPatNumByNameAndBirthday("Howard","Bob",new DateTime(1964, 5, 16));
                claimFee = 232.35;
                predeterm = "PD78901234";
                break;
            case 8: 
                patNum = Patients.getPatNumByNameAndBirthday("West","Martha",new DateTime(1954, 12, 25));
                claimFee = 565.35;
                break;
            case 9: 
                patNum = Patients.getPatNumByNameAndBirthday("Arpège","Madeleine",new DateTime(1940, 5, 1));
                claimFee = 527.35;
                break;
        
        }
        List<Claim> claimList = Claims.refresh(patNum);
        Claim claim = null;
        for (int i = 0;i < claimList.Count;i++)
        {
            if (claimList[i].ClaimFee == claimFee && StringSupport.equals(claimList[i].PreAuthString, predeterm))
            {
                claim = claimList[i];
            }
             
        }
        if (claim == null)
        {
            MessageBox.Show("Claim not found.");
            return ;
        }
         
        List<Etrans> etransList = Etranss.getHistoryOneClaim(claim.ClaimNum);
        if (etransList.Count == 0)
        {
            MessageBox.Show("No history found of sent e-claim.");
            return ;
        }
         
        FormEtransEdit FormE = new FormEtransEdit();
        FormE.EtransCur = etransList[0];
        FormE.ShowDialog();
    }

    private void radioCompareInput_Click(Object sender, EventArgs e) throws Exception {
        radioCompareInput.Checked = true;
        radioCompareOutput.Checked = false;
    }

    private void radioCompareOutput_Click(Object sender, EventArgs e) throws Exception {
        radioCompareInput.Checked = false;
        radioCompareOutput.Checked = true;
    }

    private void buttonCompFileBrowse_Click(Object sender, EventArgs e) throws Exception {
        openFileDialog1.FileName = textCompareFilePath.Text;
        if (openFileDialog1.ShowDialog() == DialogResult.OK)
        {
            textCompareFilePath.Text = openFileDialog1.FileName;
        }
         
    }

    private void butCompare_Click(Object sender, EventArgs e) throws Exception {
        if (!File.Exists(textCompareFilePath.Text))
        {
            MessageBox.Show("The specified file path of file to compare either doesn't exist or is inaccessible.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        String outputToCompare = "";
        if (radioCompareInput.Checked)
        {
            outputToCompare = File.ReadAllText("C:\\iCA\\_nput.000", Encoding.GetEncoding(850));
        }
        else
        {
            //radioCompareOutput.Checked
            outputToCompare = File.ReadAllText("C:\\iCA\\output.000", Encoding.GetEncoding(850));
        } 
        String[] fileLines = File.ReadAllLines(textCompareFilePath.Text, Encoding.GetEncoding(850));
        richTextCompare.Text = "";
        for (int i = 0;i < fileLines.Length;i++)
        {
            String line = fileLines[i];
            if (StringSupport.equals(line.Trim(), ""))
            {
                continue;
            }
             
            for (int j = 0;j < outputToCompare.Length;j++)
            {
                //Display the top line
                richTextCompare.SelectionColor = Color.Red;
                if (j < line.Length)
                {
                    if (outputToCompare[j] == line[j])
                    {
                        richTextCompare.SelectionColor = Color.Gray;
                    }
                     
                }
                 
                richTextCompare.SelectedText += outputToCompare[j];
            }
            richTextCompare.SelectedText += Environment.NewLine;
            for (int j = 0;j < line.Length;j++)
            {
                //Display the bottom line
                richTextCompare.SelectionColor = Color.Green;
                if (j < outputToCompare.Length)
                {
                    if (line[j] == outputToCompare[j])
                    {
                        richTextCompare.SelectionColor = Color.Black;
                    }
                     
                }
                 
                richTextCompare.SelectedText += line[j];
            }
            richTextCompare.SelectedText += Environment.NewLine;
        }
        Cursor = Cursors.Default;
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
        this.butObjects = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.butNewDb = new System.Windows.Forms.Button();
        this.butClear = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butScripts = new System.Windows.Forms.Button();
        this.textResults = new System.Windows.Forms.TextBox();
        this.checkEligibility = new System.Windows.Forms.CheckBox();
        this.checkClaims = new System.Windows.Forms.CheckBox();
        this.checkClaimReversals = new System.Windows.Forms.CheckBox();
        this.checkOutstanding = new System.Windows.Forms.CheckBox();
        this.checkPredeterm = new System.Windows.Forms.CheckBox();
        this.checkPayReconcil = new System.Windows.Forms.CheckBox();
        this.checkSumReconcil = new System.Windows.Forms.CheckBox();
        this.checkShowForms = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textSingleScript = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butShowEtrans = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioCompareInput = new System.Windows.Forms.RadioButton();
        this.radioCompareOutput = new System.Windows.Forms.RadioButton();
        this.textCompareFilePath = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.buttonCompFileBrowse = new System.Windows.Forms.Button();
        this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
        this.butCompare = new System.Windows.Forms.Button();
        this.richTextCompare = new System.Windows.Forms.RichTextBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butObjects
        //
        this.butObjects.Location = new System.Drawing.Point(12, 64);
        this.butObjects.Name = "butObjects";
        this.butObjects.Size = new System.Drawing.Size(87, 23);
        this.butObjects.TabIndex = 10;
        this.butObjects.Text = "+ Objects";
        this.butObjects.UseVisualStyleBackColor = true;
        this.butObjects.Click += new System.EventHandler(this.butObjects_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(106, 14);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(505, 18);
        this.label2.TabIndex = 9;
        this.label2.Text = "The scripts are all designed so that this will not normally be necessary except f" + "or new versions.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butNewDb
        //
        this.butNewDb.Location = new System.Drawing.Point(12, 12);
        this.butNewDb.Name = "butNewDb";
        this.butNewDb.Size = new System.Drawing.Size(87, 23);
        this.butNewDb.TabIndex = 8;
        this.butNewDb.Text = "Fresh Db";
        this.butNewDb.UseVisualStyleBackColor = true;
        this.butNewDb.Click += new System.EventHandler(this.butNewDb_Click);
        //
        // butClear
        //
        this.butClear.Location = new System.Drawing.Point(12, 38);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(87, 23);
        this.butClear.TabIndex = 11;
        this.butClear.Text = "Clear";
        this.butClear.UseVisualStyleBackColor = true;
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(106, 66);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(505, 18);
        this.label1.TabIndex = 12;
        this.label1.Text = "Dentists, Carriers, Patients, InsPlans, Procedures, Claims";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butScripts
        //
        this.butScripts.Location = new System.Drawing.Point(12, 90);
        this.butScripts.Name = "butScripts";
        this.butScripts.Size = new System.Drawing.Size(87, 23);
        this.butScripts.TabIndex = 15;
        this.butScripts.Text = "+ Script";
        this.butScripts.UseVisualStyleBackColor = true;
        this.butScripts.Click += new System.EventHandler(this.butScripts_Click);
        //
        // textResults
        //
        this.textResults.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textResults.Location = new System.Drawing.Point(12, 211);
        this.textResults.Multiline = true;
        this.textResults.Name = "textResults";
        this.textResults.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textResults.Size = new System.Drawing.Size(759, 207);
        this.textResults.TabIndex = 16;
        //
        // checkEligibility
        //
        this.checkEligibility.Checked = true;
        this.checkEligibility.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkEligibility.Location = new System.Drawing.Point(13, 136);
        this.checkEligibility.Name = "checkEligibility";
        this.checkEligibility.Size = new System.Drawing.Size(161, 18);
        this.checkEligibility.TabIndex = 17;
        this.checkEligibility.Text = "Eligibility 1-6";
        this.checkEligibility.UseVisualStyleBackColor = true;
        this.checkEligibility.Click += new System.EventHandler(this.checkEligibility_Click);
        //
        // checkClaims
        //
        this.checkClaims.Location = new System.Drawing.Point(13, 154);
        this.checkClaims.Name = "checkClaims";
        this.checkClaims.Size = new System.Drawing.Size(161, 18);
        this.checkClaims.TabIndex = 18;
        this.checkClaims.Text = "Claims 1-12";
        this.checkClaims.UseVisualStyleBackColor = true;
        this.checkClaims.Click += new System.EventHandler(this.checkClaims_Click);
        //
        // checkClaimReversals
        //
        this.checkClaimReversals.Location = new System.Drawing.Point(13, 172);
        this.checkClaimReversals.Name = "checkClaimReversals";
        this.checkClaimReversals.Size = new System.Drawing.Size(161, 18);
        this.checkClaimReversals.TabIndex = 19;
        this.checkClaimReversals.Text = "ClaimReversals 1-4";
        this.checkClaimReversals.UseVisualStyleBackColor = true;
        this.checkClaimReversals.Click += new System.EventHandler(this.checkClaimReversals_Click);
        //
        // checkOutstanding
        //
        this.checkOutstanding.Location = new System.Drawing.Point(13, 190);
        this.checkOutstanding.Name = "checkOutstanding";
        this.checkOutstanding.Size = new System.Drawing.Size(182, 18);
        this.checkOutstanding.TabIndex = 20;
        this.checkOutstanding.Text = "Outstanding Transactions 1-3";
        this.checkOutstanding.UseVisualStyleBackColor = true;
        this.checkOutstanding.Click += new System.EventHandler(this.checkOutstanding_Click);
        //
        // checkPredeterm
        //
        this.checkPredeterm.Location = new System.Drawing.Point(207, 136);
        this.checkPredeterm.Name = "checkPredeterm";
        this.checkPredeterm.Size = new System.Drawing.Size(152, 18);
        this.checkPredeterm.TabIndex = 21;
        this.checkPredeterm.Text = "Predeterminations 1-8";
        this.checkPredeterm.UseVisualStyleBackColor = true;
        this.checkPredeterm.Click += new System.EventHandler(this.checkPredeterm_Click);
        //
        // checkPayReconcil
        //
        this.checkPayReconcil.Location = new System.Drawing.Point(207, 154);
        this.checkPayReconcil.Name = "checkPayReconcil";
        this.checkPayReconcil.Size = new System.Drawing.Size(215, 18);
        this.checkPayReconcil.TabIndex = 22;
        this.checkPayReconcil.Text = "PaymentReconciliations 1-3";
        this.checkPayReconcil.UseVisualStyleBackColor = true;
        this.checkPayReconcil.Click += new System.EventHandler(this.checkPayReconcil_Click);
        //
        // checkSumReconcil
        //
        this.checkSumReconcil.Location = new System.Drawing.Point(207, 172);
        this.checkSumReconcil.Name = "checkSumReconcil";
        this.checkSumReconcil.Size = new System.Drawing.Size(189, 18);
        this.checkSumReconcil.TabIndex = 23;
        this.checkSumReconcil.Text = "Summary Reconciliations 1-3";
        this.checkSumReconcil.UseVisualStyleBackColor = true;
        this.checkSumReconcil.Click += new System.EventHandler(this.checkSumReconcil_Click);
        //
        // checkShowForms
        //
        this.checkShowForms.Location = new System.Drawing.Point(122, 116);
        this.checkShowForms.Name = "checkShowForms";
        this.checkShowForms.Size = new System.Drawing.Size(185, 18);
        this.checkShowForms.TabIndex = 24;
        this.checkShowForms.Text = "Show form on screen";
        this.checkShowForms.UseVisualStyleBackColor = true;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(9, 115);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(56, 18);
        this.label3.TabIndex = 26;
        this.label3.Text = "Script #";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSingleScript
        //
        this.textSingleScript.Location = new System.Drawing.Point(67, 115);
        this.textSingleScript.Name = "textSingleScript";
        this.textSingleScript.Size = new System.Drawing.Size(49, 20);
        this.textSingleScript.TabIndex = 27;
        this.textSingleScript.Text = "2";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(106, 92);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(648, 18);
        this.label4.TabIndex = 28;
        this.label4.Text = "Their test environment is underpowered and can only handle one script at a time.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butShowEtrans
        //
        this.butShowEtrans.Location = new System.Drawing.Point(684, 90);
        this.butShowEtrans.Name = "butShowEtrans";
        this.butShowEtrans.Size = new System.Drawing.Size(87, 23);
        this.butShowEtrans.TabIndex = 29;
        this.butShowEtrans.Text = "Show Etrans";
        this.butShowEtrans.UseVisualStyleBackColor = true;
        this.butShowEtrans.Click += new System.EventHandler(this.butShowEtrans_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.richTextCompare);
        this.groupBox1.Controls.Add(this.butCompare);
        this.groupBox1.Controls.Add(this.buttonCompFileBrowse);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textCompareFilePath);
        this.groupBox1.Controls.Add(this.radioCompareOutput);
        this.groupBox1.Controls.Add(this.radioCompareInput);
        this.groupBox1.Location = new System.Drawing.Point(13, 424);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(758, 431);
        this.groupBox1.TabIndex = 30;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Compare Output";
        //
        // radioCompareInput
        //
        this.radioCompareInput.AutoSize = true;
        this.radioCompareInput.Checked = true;
        this.radioCompareInput.Location = new System.Drawing.Point(36, 19);
        this.radioCompareInput.Name = "radioCompareInput";
        this.radioCompareInput.Size = new System.Drawing.Size(166, 17);
        this.radioCompareInput.TabIndex = 0;
        this.radioCompareInput.TabStop = true;
        this.radioCompareInput.Text = "Compare to C:\\iCA\\_nput.000";
        this.radioCompareInput.UseVisualStyleBackColor = true;
        this.radioCompareInput.Click += new System.EventHandler(this.radioCompareInput_Click);
        //
        // radioCompareOutput
        //
        this.radioCompareOutput.AutoSize = true;
        this.radioCompareOutput.Location = new System.Drawing.Point(218, 19);
        this.radioCompareOutput.Name = "radioCompareOutput";
        this.radioCompareOutput.Size = new System.Drawing.Size(169, 17);
        this.radioCompareOutput.TabIndex = 1;
        this.radioCompareOutput.Text = "Compare to C:\\iCA\\output.000";
        this.radioCompareOutput.UseVisualStyleBackColor = true;
        this.radioCompareOutput.Click += new System.EventHandler(this.radioCompareOutput_Click);
        //
        // textCompareFilePath
        //
        this.textCompareFilePath.Location = new System.Drawing.Point(20, 74);
        this.textCompareFilePath.Name = "textCompareFilePath";
        this.textCompareFilePath.Size = new System.Drawing.Size(640, 20);
        this.textCompareFilePath.TabIndex = 2;
        this.textCompareFilePath.Text = "\\\\serverfiles\\storage\\OPEN DENTAL\\Programmers Documents\\Standards (X12, ADA, etc)" + "\\Canada CDAnet\\Certification\\Test Transactions 2\\C_E1.NET";
        //
        // label5
        //
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(17, 58);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(153, 13);
        this.label5.TabIndex = 3;
        this.label5.Text = "Path to .NET file to compare to";
        //
        // buttonCompFileBrowse
        //
        this.buttonCompFileBrowse.Location = new System.Drawing.Point(666, 72);
        this.buttonCompFileBrowse.Name = "buttonCompFileBrowse";
        this.buttonCompFileBrowse.Size = new System.Drawing.Size(75, 23);
        this.buttonCompFileBrowse.TabIndex = 4;
        this.buttonCompFileBrowse.Text = "Browse";
        this.buttonCompFileBrowse.UseVisualStyleBackColor = true;
        this.buttonCompFileBrowse.Click += new System.EventHandler(this.buttonCompFileBrowse_Click);
        //
        // openFileDialog1
        //
        this.openFileDialog1.FileName = "openFileDialog1";
        //
        // butCompare
        //
        this.butCompare.Location = new System.Drawing.Point(334, 119);
        this.butCompare.Name = "butCompare";
        this.butCompare.Size = new System.Drawing.Size(75, 23);
        this.butCompare.TabIndex = 5;
        this.butCompare.Text = "Compare";
        this.butCompare.UseVisualStyleBackColor = true;
        this.butCompare.Click += new System.EventHandler(this.butCompare_Click);
        //
        // richTextCompare
        //
        this.richTextCompare.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.richTextCompare.HideSelection = false;
        this.richTextCompare.Location = new System.Drawing.Point(20, 160);
        this.richTextCompare.Name = "richTextCompare";
        this.richTextCompare.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.ForcedHorizontal;
        this.richTextCompare.Size = new System.Drawing.Size(721, 250);
        this.richTextCompare.TabIndex = 6;
        this.richTextCompare.Text = "";
        this.richTextCompare.WordWrap = false;
        //
        // FormTestCanada
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(783, 867);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butShowEtrans);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textSingleScript);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.checkShowForms);
        this.Controls.Add(this.checkSumReconcil);
        this.Controls.Add(this.checkPayReconcil);
        this.Controls.Add(this.checkPredeterm);
        this.Controls.Add(this.checkOutstanding);
        this.Controls.Add(this.checkClaimReversals);
        this.Controls.Add(this.checkClaims);
        this.Controls.Add(this.checkEligibility);
        this.Controls.Add(this.textResults);
        this.Controls.Add(this.butScripts);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClear);
        this.Controls.Add(this.butObjects);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butNewDb);
        this.Name = "FormTestCanada";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormTestCanada";
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butObjects = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butNewDb = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClear = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butScripts = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textResults = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkEligibility = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkClaims = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkClaimReversals = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkOutstanding = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkPredeterm = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkPayReconcil = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkSumReconcil = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkShowForms = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSingleScript = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butShowEtrans = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioCompareOutput = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioCompareInput = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Button buttonCompFileBrowse = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCompareFilePath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.OpenFileDialog openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
    private System.Windows.Forms.RichTextBox richTextCompare = new System.Windows.Forms.RichTextBox();
    private System.Windows.Forms.Button butCompare = new System.Windows.Forms.Button();
}
/*
		private void SetCheckAll() {
			bool someChecked=false;
			if(checkEligibility.Checked
				|| checkClaims.Checked
				|| checkClaimReversals.Checked
				|| checkOutstanding.Checked
				|| checkPredeterm.Checked
				|| checkPayReconcil.Checked
				|| checkSumReconcil.Checked) 
			{
				someChecked=true;
			}
			bool someUnchecked=false;
			if(!checkEligibility.Checked
				|| !checkClaims.Checked
				|| !checkClaimReversals.Checked
				|| !checkOutstanding.Checked
				|| !checkPredeterm.Checked
				|| !checkPayReconcil.Checked
				|| !checkSumReconcil.Checked) 
			{
				someUnchecked=true;
			}
			if(someChecked && someUnchecked) {
				checkAll.CheckState=CheckState.Indeterminate;
			}
			else if(someChecked) {
				checkAll.CheckState=CheckState.Checked;
			}
			else {
				checkAll.CheckState=CheckState.Unchecked;
			}
		}
		private void checkAll_Click(object sender,EventArgs e) {
			if(checkAll.CheckState==CheckState.Indeterminate) {
				checkAll.CheckState=CheckState.Unchecked;//make it behave like a two state box
			}
			if(checkAll.CheckState==CheckState.Checked) {
				checkEligibility.Checked=true;
				checkClaims.Checked=true;
				checkClaimReversals.Checked=true;
				checkOutstanding.Checked=true;
				checkPredeterm.Checked=true;
				checkPayReconcil.Checked=true;
				checkSumReconcil.Checked=true;
			}
			if(checkAll.CheckState==CheckState.Unchecked) {
				checkEligibility.Checked=false;
				checkClaims.Checked=false;
				checkClaimReversals.Checked=false;
				checkOutstanding.Checked=false;
				checkPredeterm.Checked=false;
				checkPayReconcil.Checked=false;
				checkSumReconcil.Checked=false;
			}
		}*/

/*
		private void SetCheckAll() {
			bool someChecked=false;
			if(checkEligibility.Checked
				|| checkClaims.Checked
				|| checkClaimReversals.Checked
				|| checkOutstanding.Checked
				|| checkPredeterm.Checked
				|| checkPayReconcil.Checked
				|| checkSumReconcil.Checked) 
			{
				someChecked=true;
			}
			bool someUnchecked=false;
			if(!checkEligibility.Checked
				|| !checkClaims.Checked
				|| !checkClaimReversals.Checked
				|| !checkOutstanding.Checked
				|| !checkPredeterm.Checked
				|| !checkPayReconcil.Checked
				|| !checkSumReconcil.Checked) 
			{
				someUnchecked=true;
			}
			if(someChecked && someUnchecked) {
				checkAll.CheckState=CheckState.Indeterminate;
			}
			else if(someChecked) {
				checkAll.CheckState=CheckState.Checked;
			}
			else {
				checkAll.CheckState=CheckState.Unchecked;
			}
		}
		private void checkAll_Click(object sender,EventArgs e) {
			if(checkAll.CheckState==CheckState.Indeterminate) {
				checkAll.CheckState=CheckState.Unchecked;//make it behave like a two state box
			}
			if(checkAll.CheckState==CheckState.Checked) {
				checkEligibility.Checked=true;
				checkClaims.Checked=true;
				checkClaimReversals.Checked=true;
				checkOutstanding.Checked=true;
				checkPredeterm.Checked=true;
				checkPayReconcil.Checked=true;
				checkSumReconcil.Checked=true;
			}
			if(checkAll.CheckState==CheckState.Unchecked) {
				checkEligibility.Checked=false;
				checkClaims.Checked=false;
				checkClaimReversals.Checked=false;
				checkOutstanding.Checked=false;
				checkPredeterm.Checked=false;
				checkPayReconcil.Checked=false;
				checkSumReconcil.Checked=false;
			}
		}*/