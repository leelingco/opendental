//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package UnitTests;

import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import UnitTests.AllTests;
import UnitTests.BenefitT;
import UnitTests.CoreTypesT;
import UnitTests.DatabaseTools;
import UnitTests.HL7TestInterfaceEnum;
import UnitTests.HL7Tests;
import UnitTests.SchemaT;
import UnitTests.ToothT;
import UnitTests.TopazT;
import UnitTests.WebServiceT;
import UnitTests.FormUnitTests;

public class FormUnitTests  extends Form 
{

    private boolean isOracle = new boolean();
    public FormUnitTests() throws Exception {
        initializeComponent();
    }

    private void formUnitTests_Load(Object sender, EventArgs e) throws Exception {
        listType.Items.Add("MySql");
        listType.Items.Add("Oracle");
        listType.SelectedIndex = 0;
    }

    //throw new Exception("");
    //if(!DatabaseTools.SetDbConnection("unittest")){
    //}
    //if(!DatabaseTools.DbExists()){
    //	MessageBox.Show("Database does not exist: "+DatabaseTools.dbName);
    //}
    private void listType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        if (listType.SelectedIndex == 0)
        {
            //Only two selections, MySQL or Oracle.
            isOracle = false;
        }
        else
        {
            isOracle = true;
        } 
    }

    private void butWebService_Click(Object sender, EventArgs e) throws Exception {
        RemotingClient.ServerURI = "http://localhost:49262/ServiceMain.asmx";
        Cursor = Cursors.WaitCursor;
        try
        {
            if (!isOracle)
            {
                Userod user = Security.LogInWeb("Admin", "", "", Application.ProductVersion, false);
                //Userods.EncryptPassword("pass",false)
                Security.setCurUser(user);
                RemotingClient.RemotingRole = RemotingRole.ClientWeb;
            }
            else if (isOracle)
            {
                MsgBox.show(this,"Oracle does not have unit test for web service yet.");
                Cursor = Cursors.Default;
                return ;
            }
              
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        textResults.Text = "";
        Application.DoEvents();
        textResults.Text += WebServiceT.runAll();
        Cursor = Cursors.Default;
    }

    private void butSchema_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        textResults.Text = "";
        Application.DoEvents();
        if (radioSchema1.Checked)
        {
            textResults.Text += SchemaT.testProposedCrud(isOracle);
        }
        else
        {
            textResults.Text += SchemaT.compareProposedToGenerated(isOracle);
        } 
        Cursor = Cursors.Default;
    }

    private void butCore_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        textResults.Text = "";
        Application.DoEvents();
        textResults.Text += CoreTypesT.createTempTable(isOracle);
        Application.DoEvents();
        textResults.Text += CoreTypesT.runAll();
        //}
        //else {
        //	textResults.Text+=CoreTypesT.RunAllMySql();
        //}
        Cursor = Cursors.Default;
    }

    private void butTopaz_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        textResults.Text = "";
        Application.DoEvents();
        textResults.Text += TopazT.runAll();
        Cursor = Cursors.Default;
    }

    private void butNewDb_Click(Object sender, EventArgs e) throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!isOracle)
        {
            if (!DatabaseTools.setDbConnection("",isOracle))
            {
                MessageBox.Show("Could not connect");
                return ;
            }
             
        }
         
        DatabaseTools.freshFromDump(isOracle);
        textResults.Text += "Fresh database loaded from sql dump.";
        Cursor = Cursors.Default;
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!DatabaseTools.setDbConnection("unittest",false))
        {
            //if database doesn't exist
            DatabaseTools.setDbConnection("",false);
            textResults.Text += DatabaseTools.freshFromDump(false);
        }
        else
        {
            //this also sets database to be unittest.
            textResults.Text += DatabaseTools.clearDb();
        } 
        try
        {
            textResults.Text += BenefitT.benefitComputeRenewDate();
        }
        catch (Exception ex)
        {
            textResults.Text += ex.Message;
        }

        try
        {
            //failed
            textResults.Text += ToothT.formatRanges();
        }
        catch (Exception ex)
        {
            textResults.Text += ex.Message;
        }

        //failed
        int specificTest = 0;
        try
        {
            Application.DoEvents();
            specificTest = PIn.Int(textSpecificTest.Text);
            //typically zero
            textResults.Text += AllTests.testOneTwo(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "1&2: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThree(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "3: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testFour(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "4: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testFive(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "5: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testSix(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "6: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testSeven(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "7: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testEight(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "8: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testNine(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "9: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "10: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testEleven(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "11: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwelve(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "12: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "13: Failed. " + ex.Message + "\r\n";
        }

        try
        {
            //try {//There is a phantom TestFourteen that has been commented out. It is similary to TestOne.
            //  Application.DoEvents();
            //  textResults.Text+=AllTests.TestFourteen(specificTest);
            //}
            //catch(Exception ex) {
            //  textResults.Text+="14: Failed. "+ex.Message+"\r\n";
            //}
            Application.DoEvents();
            textResults.Text += AllTests.testFourteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "14: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testFifteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "15: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testSixteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "16: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testSeventeen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "17: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testEighteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "18: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testNineteen(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "19: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwenty(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "20: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyOne(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "21: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyTwo(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "22: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyThree(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "23: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyFour(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "24: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyFive(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "25: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentySix(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "26: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentySeven(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "27: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyEight(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "28: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testTwentyNine(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "29: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirty(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "30: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirtyOne(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "31: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirtyTwo(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "32: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirtyThree(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "33: Failed. " + ex.Message;
        }

        try
        {
            Application.DoEvents();
            textResults.Text += AllTests.testThirtyFour(specificTest);
        }
        catch (Exception ex)
        {
            textResults.Text += "34: Failed. " + ex.Message;
        }

        textResults.Text += "Done";
        Cursor = Cursors.Default;
    }

    private void butHL7_Click(Object sender, EventArgs e) throws Exception {
        textResults.Text = "";
        Application.DoEvents();
        Cursor = Cursors.WaitCursor;
        if (!DatabaseTools.setDbConnection("unittest",false))
        {
            //if database doesn't exist
            DatabaseTools.setDbConnection("",false);
            textResults.Text += DatabaseTools.freshFromDump(false);
        }
         
        for (Object __dummyForeachVar0 : Enum.GetValues(HL7TestInterfaceEnum.class))
        {
            //this also sets database to be unittest.
            HL7TestInterfaceEnum hl7TestInterfaceEnum = (HL7TestInterfaceEnum)__dummyForeachVar0;
            textResults.Text += DatabaseTools.clearDb();
            textResults.Text += "Testing " + hl7TestInterfaceEnum.ToString() + " interface.\r\n";
            textResults.Text += HL7Tests.hL7TestAll(hl7TestInterfaceEnum);
            textResults.Text += "\r\n\r\n";
        }
        textResults.Text += "Done\r\n";
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUnitTests.class);
        this.textResults = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butNewDb = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.butRun = new System.Windows.Forms.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.textSpecificTest = new System.Windows.Forms.TextBox();
        this.butWebService = new System.Windows.Forms.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.butCore = new System.Windows.Forms.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.listType = new System.Windows.Forms.ListBox();
        this.label7 = new System.Windows.Forms.Label();
        this.butSchema = new System.Windows.Forms.Button();
        this.radioSchema1 = new System.Windows.Forms.RadioButton();
        this.radioSchema2 = new System.Windows.Forms.RadioButton();
        this.label6 = new System.Windows.Forms.Label();
        this.butTopaz = new System.Windows.Forms.Button();
        this.label8 = new System.Windows.Forms.Label();
        this.butHL7 = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // textResults
        //
        this.textResults.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textResults.Location = new System.Drawing.Point(12, 208);
        this.textResults.Multiline = true;
        this.textResults.Name = "textResults";
        this.textResults.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textResults.Size = new System.Drawing.Size(733, 587);
        this.textResults.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 34);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(347, 18);
        this.label1.TabIndex = 3;
        this.label1.Text = "Before running the tests below, make sure \'unittest\' database exists.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butNewDb
        //
        this.butNewDb.Location = new System.Drawing.Point(12, 154);
        this.butNewDb.Name = "butNewDb";
        this.butNewDb.Size = new System.Drawing.Size(75, 23);
        this.butNewDb.TabIndex = 0;
        this.butNewDb.Text = "Fresh Db";
        this.butNewDb.UseVisualStyleBackColor = true;
        this.butNewDb.Click += new System.EventHandler(this.butNewDb_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(92, 156);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(505, 18);
        this.label2.TabIndex = 6;
        this.label2.Text = "The scripts are all designed so that this will not normally be necessary except f" + "or new versions.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butRun
        //
        this.butRun.Location = new System.Drawing.Point(12, 179);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 23);
        this.butRun.TabIndex = 7;
        this.butRun.Text = "Run";
        this.butRun.UseVisualStyleBackColor = true;
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(92, 181);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(90, 18);
        this.label3.TabIndex = 8;
        this.label3.Text = "Specific test #";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textSpecificTest
        //
        this.textSpecificTest.Location = new System.Drawing.Point(177, 181);
        this.textSpecificTest.Name = "textSpecificTest";
        this.textSpecificTest.Size = new System.Drawing.Size(74, 20);
        this.textSpecificTest.TabIndex = 9;
        //
        // butWebService
        //
        this.butWebService.Location = new System.Drawing.Point(12, 5);
        this.butWebService.Name = "butWebService";
        this.butWebService.Size = new System.Drawing.Size(75, 23);
        this.butWebService.TabIndex = 10;
        this.butWebService.Text = "Middle Tier";
        this.butWebService.UseVisualStyleBackColor = true;
        this.butWebService.Click += new System.EventHandler(this.butWebService_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(92, 7);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(600, 18);
        this.label4.TabIndex = 11;
        this.label4.Text = "Set both this project and OpenDentalServer as startup.  Edit OpenDentalServer.Ope" + "nDentalServerConfig.xml to be valid.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butCore
        //
        this.butCore.Location = new System.Drawing.Point(12, 79);
        this.butCore.Name = "butCore";
        this.butCore.Size = new System.Drawing.Size(75, 23);
        this.butCore.TabIndex = 12;
        this.butCore.Text = "Core Types";
        this.butCore.UseVisualStyleBackColor = true;
        this.butCore.Click += new System.EventHandler(this.butCore_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(93, 82);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(547, 18);
        this.label5.TabIndex = 13;
        this.label5.Text = "Stores and retrieves core data types in database, ensuring that db engine and con" + "nector are working.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // listType
        //
        this.listType.FormattingEnabled = true;
        this.listType.Location = new System.Drawing.Point(646, 63);
        this.listType.Name = "listType";
        this.listType.Size = new System.Drawing.Size(99, 30);
        this.listType.TabIndex = 22;
        this.listType.SelectedIndexChanged += new System.EventHandler(this.listType_SelectedIndexChanged);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(643, 42);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(99, 18);
        this.label7.TabIndex = 21;
        this.label7.Text = "Database Type";
        this.label7.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSchema
        //
        this.butSchema.Location = new System.Drawing.Point(12, 54);
        this.butSchema.Name = "butSchema";
        this.butSchema.Size = new System.Drawing.Size(75, 23);
        this.butSchema.TabIndex = 23;
        this.butSchema.Text = "Schema";
        this.butSchema.UseVisualStyleBackColor = true;
        this.butSchema.Click += new System.EventHandler(this.butSchema_Click);
        //
        // radioSchema1
        //
        this.radioSchema1.Checked = true;
        this.radioSchema1.Location = new System.Drawing.Point(97, 57);
        this.radioSchema1.Name = "radioSchema1";
        this.radioSchema1.Size = new System.Drawing.Size(133, 18);
        this.radioSchema1.TabIndex = 24;
        this.radioSchema1.TabStop = true;
        this.radioSchema1.Text = "Test proposed crud";
        this.radioSchema1.UseVisualStyleBackColor = true;
        //
        // radioSchema2
        //
        this.radioSchema2.Location = new System.Drawing.Point(234, 57);
        this.radioSchema2.Name = "radioSchema2";
        this.radioSchema2.Size = new System.Drawing.Size(189, 18);
        this.radioSchema2.TabIndex = 25;
        this.radioSchema2.Text = "Compare proposed to generated";
        this.radioSchema2.UseVisualStyleBackColor = true;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(93, 107);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(547, 18);
        this.label6.TabIndex = 27;
        this.label6.Text = "Checks signatures from both old and new Topaz dlls.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butTopaz
        //
        this.butTopaz.Location = new System.Drawing.Point(12, 104);
        this.butTopaz.Name = "butTopaz";
        this.butTopaz.Size = new System.Drawing.Size(75, 23);
        this.butTopaz.TabIndex = 26;
        this.butTopaz.Text = "Topaz";
        this.butTopaz.UseVisualStyleBackColor = true;
        this.butTopaz.Click += new System.EventHandler(this.butTopaz_Click);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(92, 131);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(445, 18);
        this.label8.TabIndex = 29;
        this.label8.Text = "This will test the old eCW HL7 message processing as well as the new HL7Defs.";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butHL7
        //
        this.butHL7.Location = new System.Drawing.Point(12, 129);
        this.butHL7.Name = "butHL7";
        this.butHL7.Size = new System.Drawing.Size(75, 23);
        this.butHL7.TabIndex = 28;
        this.butHL7.Text = "HL7";
        this.butHL7.UseVisualStyleBackColor = true;
        this.butHL7.Click += new System.EventHandler(this.butHL7_Click);
        //
        // FormUnitTests
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(757, 807);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.butHL7);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butTopaz);
        this.Controls.Add(this.radioSchema2);
        this.Controls.Add(this.radioSchema1);
        this.Controls.Add(this.butSchema);
        this.Controls.Add(this.listType);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butCore);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butWebService);
        this.Controls.Add(this.textSpecificTest);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butNewDb);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textResults);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormUnitTests";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormUnitTests";
        this.Load += new System.EventHandler(this.FormUnitTests_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textResults = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butNewDb = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butRun = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpecificTest = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butWebService = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCore = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butSchema = new System.Windows.Forms.Button();
    private System.Windows.Forms.RadioButton radioSchema1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioSchema2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butTopaz = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butHL7 = new System.Windows.Forms.Button();
}


