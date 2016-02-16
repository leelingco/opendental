//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientPortalSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PrinterL;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Userods;

public class FormPatientPortal  extends Form 
{

    public Patient PatCur;
    /**
    * A copy of the original patient object, as it was when this form was first opened.
    */
    private Patient PatOld;
    /**
    * Keeps track if the user printed the patient's information.  Mainly used to show a reminder when the password changes and the user didn't print.
    */
    private boolean WasPrinted = new boolean();
    public FormPatientPortal() throws Exception {
        initializeComponent();
    }

    private void formPatientPortal_Load(Object sender, EventArgs e) throws Exception {
        PatOld = PatCur.copy();
        textOnlineUsername.Text = PatCur.FName + PatCur.PatNum;
        textOnlinePassword.Text = "";
        if (!StringSupport.equals(PatCur.OnlinePassword, ""))
        {
            //if a password was already filled in
            butGiveAccess.Text = "Remove Online Access";
            //We do not want to show the password hash that is stored in the database so we will fill the online password with asterisks.
            textOnlinePassword.Text = "********";
            textOnlinePassword.ReadOnly = false;
        }
         
        textPatientPortalURL.Text = PrefC.getString(PrefName.PatientPortalURL);
    }

    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        FormPatientPortalSetup formPPS = new FormPatientPortalSetup();
        formPPS.ShowDialog();
        if (formPPS.DialogResult == DialogResult.OK)
        {
            textPatientPortalURL.Text = PrefC.getString(PrefName.PatientPortalURL);
        }
         
    }

    private void butGiveAccess_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(butGiveAccess.Text, "Provide Online Access"))
        {
            //When form open opens with a blank password
            String error = validatePatientAccess();
            if (!StringSupport.equals(error, ""))
            {
                MessageBox.Show(error);
                return ;
            }
             
            Cursor = Cursors.WaitCursor;
            //1. Fill password.
            String passwordGenerated = generateRandomPassword(8);
            textOnlinePassword.Text = passwordGenerated;
            //2. Make the password editable in case they want to change it.
            textOnlinePassword.ReadOnly = false;
            //3. Save password to db.
            // We only save the hash of the generated password.
            String passwordHashed = Userods.encryptPassword(passwordGenerated,false);
            PatCur.OnlinePassword = passwordHashed;
            Patients.update(PatCur,PatOld);
            PatOld.OnlinePassword = passwordHashed;
            //Update PatOld in case the user changes password manually.
            //4. Insert EhrMeasureEvent
            EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            newMeasureEvent.DateTEvent = DateTime.Now;
            newMeasureEvent.EventType = EhrMeasureEventType.OnlineAccessProvided;
            newMeasureEvent.PatNum = PatCur.PatNum;
            newMeasureEvent.MoreInfo = "";
            EhrMeasureEvents.insert(newMeasureEvent);
            //5. Rename button
            butGiveAccess.Text = "Remove Online Access";
            Cursor = Cursors.Default;
        }
        else
        {
            //remove access
            Cursor = Cursors.WaitCursor;
            //1. Clear password
            textOnlinePassword.Text = "";
            //2. Make in uneditable
            textOnlinePassword.ReadOnly = true;
            //3. Save password to db
            PatCur.OnlinePassword = textOnlinePassword.Text;
            Patients.update(PatCur,PatOld);
            PatOld.OnlinePassword = textOnlinePassword.Text;
            //Update PatOld in case the user changes password manually.
            //4. Rename button
            butGiveAccess.Text = "Provide Online Access";
            Cursor = Cursors.Default;
        } 
    }

    private void butOpen_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textPatientPortalURL.Text, ""))
        {
            MessageBox.Show("Please use Setup to set the Online Access Link first.");
            return ;
        }
         
        try
        {
            System.Diagnostics.Process.Start(textPatientPortalURL.Text);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butGenerate_Click(Object sender, EventArgs e) throws Exception {
        if (textOnlinePassword.ReadOnly)
        {
            MessageBox.Show("Please use the Provide Online Access button first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        String passwordGenerated = generateRandomPassword(8);
        textOnlinePassword.Text = passwordGenerated;
        // We only save the hash of the generated password.
        String passwordHashed = Userods.encryptPassword(passwordGenerated,false);
        PatCur.OnlinePassword = passwordHashed;
        Patients.update(PatCur,PatOld);
        PatOld.OnlinePassword = passwordHashed;
        //Update PatOld in case the user changes password manually.
        Cursor = Cursors.Default;
    }

    /**
    * Generates a random password 8 char long containing at least one uppercase, one lowercase, and one number.
    */
    private static String generateRandomPassword(int length) throws Exception {
        //Chracters like o(letter O), 0 (Zero), l (letter l), 1 (one) etc are avoided because they can be ambigious.
        String PASSWORD_CHARS_LCASE = "abcdefgijkmnopqrstwxyz";
        String PASSWORD_CHARS_UCASE = "ABCDEFGHJKLMNPQRSTWXYZ";
        String PASSWORD_CHARS_NUMERIC = "23456789";
        //Create a local array containing supported password characters grouped by types.
        char[][] charGroups = new char[][]{ PASSWORD_CHARS_LCASE.ToCharArray(), PASSWORD_CHARS_UCASE.ToCharArray(), PASSWORD_CHARS_NUMERIC.ToCharArray() };
        //Use this array to track the number of unused characters in each character group.
        int[] charsLeftInGroup = new int[charGroups.Length];
        for (int i = 0;i < charsLeftInGroup.Length;i++)
        {
            //Initially, all characters in each group are not used.
            charsLeftInGroup[i] = charGroups[i].Length;
        }
        //Use this array to track (iterate through) unused character groups.
        int[] leftGroupsOrder = new int[charGroups.Length];
        for (int i = 0;i < leftGroupsOrder.Length;i++)
        {
            //Initially, all character groups are not used.
            leftGroupsOrder[i] = i;
        }
        Random random = new Random();
        //This array will hold password characters.
        char[] password = new char[length];
        //Index of the next character to be added to password.
        int nextCharIdx = new int();
        //Index of the next character group to be processed.
        int nextGroupIdx = new int();
        //Index which will be used to track not processed character groups.
        int nextLeftGroupsOrderIdx = new int();
        //Index of the last non-processed character in a group.
        int lastCharIdx = new int();
        //Index of the last non-processed group.
        int lastLeftGroupsOrderIdx = leftGroupsOrder.Length - 1;
        for (int i = 0;i < password.Length;i++)
        {
            //Generate password characters one at a time.
            //If only one character group remained unprocessed, process it;
            //otherwise, pick a random character group from the unprocessed
            //group list. To allow a special character to appear in the
            //first position, increment the second parameter of the Next
            //function call by one, i.e. lastLeftGroupsOrderIdx + 1.
            if (lastLeftGroupsOrderIdx == 0)
            {
                nextLeftGroupsOrderIdx = 0;
            }
            else
            {
                nextLeftGroupsOrderIdx = random.Next(0, lastLeftGroupsOrderIdx);
            } 
            //Get the actual index of the character group, from which we will
            //pick the next character.
            nextGroupIdx = leftGroupsOrder[nextLeftGroupsOrderIdx];
            //Get the index of the last unprocessed characters in this group.
            lastCharIdx = charsLeftInGroup[nextGroupIdx] - 1;
            //If only one unprocessed character is left, pick it; otherwise,
            //get a random character from the unused character list.
            if (lastCharIdx == 0)
            {
                nextCharIdx = 0;
            }
            else
            {
                nextCharIdx = random.Next(0, lastCharIdx + 1);
            } 
            //Add this character to the password.
            password[i] = charGroups[nextGroupIdx][nextCharIdx];
            //If we processed the last character in this group, start over.
            if (lastCharIdx == 0)
            {
                charsLeftInGroup[nextGroupIdx] = charGroups[nextGroupIdx].Length;
            }
            else
            {
                //There are more unprocessed characters left.
                //Swap processed character with the last unprocessed character
                //so that we don't pick it until we process all characters in
                //this group.
                if (lastCharIdx != nextCharIdx)
                {
                    char temp = charGroups[nextGroupIdx][lastCharIdx];
                    charGroups[nextGroupIdx][lastCharIdx] = charGroups[nextGroupIdx][nextCharIdx];
                    charGroups[nextGroupIdx][nextCharIdx] = temp;
                }
                 
                //Decrement the number of unprocessed characters in
                //this group.
                charsLeftInGroup[nextGroupIdx]--;
            } 
            //If we processed the last group, start all over.
            if (lastLeftGroupsOrderIdx == 0)
            {
                lastLeftGroupsOrderIdx = leftGroupsOrder.Length - 1;
            }
            else
            {
                //There are more unprocessed groups left.
                //Swap processed group with the last unprocessed group
                //so that we don't pick it until we process all groups.
                if (lastLeftGroupsOrderIdx != nextLeftGroupsOrderIdx)
                {
                    int temp = leftGroupsOrder[lastLeftGroupsOrderIdx];
                    leftGroupsOrder[lastLeftGroupsOrderIdx] = leftGroupsOrder[nextLeftGroupsOrderIdx];
                    leftGroupsOrder[nextLeftGroupsOrderIdx] = temp;
                }
                 
                //Decrement the number of unprocessed groups.
                lastLeftGroupsOrderIdx--;
            } 
        }
        return new String(password);
    }

    //Convert password characters into a string and return the result.
    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textPatientPortalURL.Text, ""))
        {
            MsgBox.show(this,"Online Access Link required. Please use Setup to set the Online Access Link first.");
            return ;
        }
         
        if (StringSupport.equals(textOnlinePassword.Text, "") || StringSupport.equals(textOnlinePassword.Text, "********"))
        {
            MessageBox.Show("Password required. Please generate a new password.");
            return ;
        }
         
        if (!passwordIsValid())
        {
            return ;
        }
         
        //this gives a messagebox if invalid
        WasPrinted = true;
        //Then, print the info that the patient will be given in order for them to log in online.
        printPatientInfo();
    }

    private void printPatientInfo() throws Exception {
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        try
        {
            if (PrinterL.setPrinter(pd,PrintSituation.Default,PatCur.PatNum,"Patient portal login information printed"))
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
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font font = new Font("Arial", 10, FontStyle.Regular);
        int yPos = bounds.Top + 100;
        int center = bounds.X + bounds.Width / 2;
        text = "Online Access";
        g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, font).Width / 2, yPos);
        yPos += 50;
        text = "Website: " + textPatientPortalURL.Text;
        g.DrawString(text, font, Brushes.Black, bounds.Left + 100, yPos);
        yPos += 25;
        text = "Username: " + textOnlineUsername.Text;
        g.DrawString(text, font, Brushes.Black, bounds.Left + 100, yPos);
        yPos += 25;
        text = "Password: " + textOnlinePassword.Text;
        g.DrawString(text, font, Brushes.Black, bounds.Left + 100, yPos);
        g.Dispose();
        e.HasMorePages = false;
    }

    /**
    * Used when click OK, and also when the Synch or Print buttons are clicked.
    */
    private boolean passwordIsValid() throws Exception {
        if (textOnlinePassword.Text.Length < 8)
        {
            MessageBox.Show(this, "Password must be at least 8 characters long.");
            return false;
        }
         
        if (!Regex.IsMatch(textOnlinePassword.Text, "[A-Z]+"))
        {
            MessageBox.Show(this, "Password must contain an uppercase letter.");
            return false;
        }
         
        if (!Regex.IsMatch(textOnlinePassword.Text, "[a-z]+"))
        {
            MessageBox.Show(this, "Password must contain an lowercase letter.");
            return false;
        }
         
        if (!Regex.IsMatch(textOnlinePassword.Text, "[0-9]+"))
        {
            MessageBox.Show(this, "Password must contain a number.");
            return false;
        }
         
        return true;
    }

    private String validatePatientAccess() throws Exception {
        String strErrors = "";
        if (StringSupport.equals(PatCur.FName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient first name.";
        }
         
        if (StringSupport.equals(PatCur.LName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient last name.";
        }
         
        if (StringSupport.equals(PatCur.Address.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient address line 1.";
        }
         
        if (StringSupport.equals(PatCur.City.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient city.";
        }
         
        if (PatCur.State.Trim().Length != 2)
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Invalid patient state.  Must be two letters.";
        }
         
        if (PatCur.Birthdate.Year < 1880)
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient birth date.";
        }
         
        if (StringSupport.equals(PatCur.HmPhone.Trim(), "") && StringSupport.equals(PatCur.WirelessPhone.Trim(), "") && StringSupport.equals(PatCur.WkPhone.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient phone. Must have home, wireless, or work phone.";
        }
         
        return strErrors;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textOnlinePassword.Text, "") && !StringSupport.equals(textOnlinePassword.Text, "********"))
        {
            if (!passwordIsValid())
            {
                return ;
            }
             
            if (!WasPrinted)
            {
                DialogResult result = MessageBox.Show(Lan.g(this,"Online Password changed but was not printed, would you like to print?"), Lan.g(this,"Print Patient Info"), MessageBoxButtons.YesNoCancel);
                if (result == DialogResult.Yes)
                {
                    //Print the showing information.
                    printPatientInfo();
                }
                else if (result == DialogResult.No)
                {
                }
                else //User does not want to print.  Do nothing.
                if (result == DialogResult.Cancel)
                {
                    return ;
                }
                   
            }
             
            PatCur.OnlinePassword = Userods.EncryptPassword(textOnlinePassword.Text, false);
            Patients.update(PatCur,PatOld);
        }
         
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
        this.components = new System.ComponentModel.Container();
        this.butGiveAccess = new System.Windows.Forms.Button();
        this.textOnlineUsername = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textPatientPortalURL = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butCancel = new System.Windows.Forms.Button();
        this.butGenerate = new System.Windows.Forms.Button();
        this.textOnlinePassword = new System.Windows.Forms.TextBox();
        this.butOK = new System.Windows.Forms.Button();
        this.butOpen = new System.Windows.Forms.Button();
        this.butPrint = new System.Windows.Forms.Button();
        this.mainMenu = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butGiveAccess
        //
        this.butGiveAccess.Location = new System.Drawing.Point(140, 12);
        this.butGiveAccess.Name = "butGiveAccess";
        this.butGiveAccess.Size = new System.Drawing.Size(140, 23);
        this.butGiveAccess.TabIndex = 30;
        this.butGiveAccess.Text = "Provide Online Access";
        this.butGiveAccess.UseVisualStyleBackColor = true;
        this.butGiveAccess.Click += new System.EventHandler(this.butGiveAccess_Click);
        //
        // textOnlineUsername
        //
        this.textOnlineUsername.Location = new System.Drawing.Point(140, 81);
        this.textOnlineUsername.Name = "textOnlineUsername";
        this.textOnlineUsername.ReadOnly = true;
        this.textOnlineUsername.Size = new System.Drawing.Size(198, 20);
        this.textOnlineUsername.TabIndex = 27;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(37, 57);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 17);
        this.label5.TabIndex = 29;
        this.label5.Text = "Patient Portal URL";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatientPortalURL
        //
        this.textPatientPortalURL.Location = new System.Drawing.Point(140, 54);
        this.textPatientPortalURL.Name = "textPatientPortalURL";
        this.textPatientPortalURL.ReadOnly = true;
        this.textPatientPortalURL.Size = new System.Drawing.Size(561, 20);
        this.textPatientPortalURL.TabIndex = 28;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(36, 108);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 25;
        this.label2.Text = "Online Password";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(36, 82);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 17);
        this.label4.TabIndex = 26;
        this.label4.Text = "Online Username";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(702, 168);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 1;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butGenerate
        //
        this.butGenerate.Location = new System.Drawing.Point(344, 106);
        this.butGenerate.Name = "butGenerate";
        this.butGenerate.Size = new System.Drawing.Size(95, 23);
        this.butGenerate.TabIndex = 34;
        this.butGenerate.Text = "Generate New";
        this.butGenerate.UseVisualStyleBackColor = true;
        this.butGenerate.Click += new System.EventHandler(this.butGenerate_Click);
        //
        // textOnlinePassword
        //
        this.textOnlinePassword.Location = new System.Drawing.Point(140, 107);
        this.textOnlinePassword.Name = "textOnlinePassword";
        this.textOnlinePassword.ReadOnly = true;
        this.textOnlinePassword.Size = new System.Drawing.Size(198, 20);
        this.textOnlinePassword.TabIndex = 33;
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(621, 168);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 0;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butOpen
        //
        this.butOpen.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butOpen.Location = new System.Drawing.Point(705, 52);
        this.butOpen.Name = "butOpen";
        this.butOpen.Size = new System.Drawing.Size(70, 23);
        this.butOpen.TabIndex = 36;
        this.butOpen.Text = "Open";
        this.butOpen.UseVisualStyleBackColor = true;
        this.butOpen.Click += new System.EventHandler(this.butOpen_Click);
        //
        // butPrint
        //
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.Location = new System.Drawing.Point(140, 168);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 37;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // mainMenu
        //
        this.mainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(137, 130);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(302, 17);
        this.label1.TabIndex = 38;
        this.label1.Text = "Existing passwords will show as asterisks.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormPatientPortal
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(789, 203);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butOpen);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butGenerate);
        this.Controls.Add(this.textOnlinePassword);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butGiveAccess);
        this.Controls.Add(this.textOnlineUsername);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textPatientPortalURL);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label4);
        this.Menu = this.mainMenu;
        this.Name = "FormPatientPortal";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Portal";
        this.Load += new System.EventHandler(this.FormPatientPortal_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butGiveAccess = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textOnlineUsername = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatientPortalURL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGenerate = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textOnlinePassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOpen = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
    private System.Windows.Forms.MainMenu mainMenu = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSetup = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


