//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEmailAddresses;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormPatientSelect;
import OpenDental.FormWebMailMessageEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEmailInbox;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEmailInbox  extends Form 
{

    private EmailAddress AddressInbox;
    private List<EmailMessage> ListEmailMessages = new List<EmailMessage>();
    public FormEmailInbox() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEmailInbox_Load(Object sender, EventArgs e) throws Exception {
        labelInboxComputerName.Text = "Computer Name Where New Email Is Fetched: " + PrefC.getString(PrefName.EmailInboxComputerName);
        labelThisComputer.Text += Dns.GetHostName();
        Application.DoEvents();
        //Show the form contents before loading email into the grid.
        getMessages();
    }

    //If no new messages, then the user will know based on what shows in the grid.
    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormEmailAddresses formEA = new FormEmailAddresses();
        formEA.ShowDialog();
        labelInboxComputerName.Text = "Computer Name Where New Email Is Fetched: " + PrefC.getString(PrefName.EmailInboxComputerName);
        getMessages();
    }

    //Get new messages, just in case the user entered email information for the first time.
    /**
    * Gets new messages from email inbox, as well as older messages from the db. Also fills the grid.
    */
    private int getMessages() throws Exception {
        AddressInbox = EmailAddresses.GetByClinic(0);
        //Default for clinic/practice.
        Cursor = Cursors.WaitCursor;
        fillGridEmailMessages();
        //Show what is in db.
        Cursor = Cursors.Default;
        if (StringSupport.equals(AddressInbox.Pop3ServerIncoming, ""))
        {
            //Email address not setup.
            Text = "Email Inbox";
            AddressInbox = null;
            return 0;
        }
         
        //todo: Message Box is too instrusive, move this to a status label.
        //MsgBox.Show(this,"Default email address has not been setup completely.");
        Text = "Email Inbox for " + AddressInbox.EmailUsername;
        Application.DoEvents();
        //So that something is showing while the page is loading.
        if (!CodeBase.ODEnvironment.idIsThisComputer(PrefC.getString(PrefName.EmailInboxComputerName)))
        {
            return 0;
        }
         
        //This is not the computer to get new messages from.
        if (StringSupport.equals(PrefC.getString(PrefName.EmailInboxComputerName), ""))
        {
            MsgBox.show(this,"Computer name to fetch new email from has not been setup.");
            return 0;
        }
         
        Cursor = Cursors.WaitCursor;
        int emailMessagesTotalCount = 0;
        Text = "Email Inbox for " + AddressInbox.EmailUsername + " - Fetching new email...";
        try
        {
            boolean hasMoreEmail = true;
            while (hasMoreEmail)
            {
                List<EmailMessage> emailMessages = EmailMessages.receiveFromInbox(1,AddressInbox);
                emailMessagesTotalCount += emailMessages.Count;
                if (emailMessages.Count == 0)
                {
                    hasMoreEmail = false;
                }
                else
                {
                    //Show messages as they are downloaded, to indicate to the user that the program is still processing.
                    fillGridEmailMessages();
                    Application.DoEvents();
                } 
            }
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error retrieving email messages") + ": " + ex.Message);
        }
        finally
        {
            Text = "Email Inbox for " + AddressInbox.EmailUsername;
        }
        Text = "Email Inbox for " + AddressInbox.EmailUsername + " - Resending any acknowledgments which previously failed...";
        EmailMessages.sendOldestUnsentAck(AddressInbox);
        Text = "Email Inbox for " + AddressInbox.EmailUsername;
        Cursor = Cursors.Default;
        return emailMessagesTotalCount;
    }

    /**
    * Gets new emails and also shows older emails from the database.
    */
    private void fillGridEmailMessages() throws Exception {
        if (AddressInbox == null)
        {
            ListEmailMessages = new List<EmailMessage>();
        }
        else
        {
            ListEmailMessages = EmailMessages.getInboxForAddress(AddressInbox.EmailUsername,Security.getCurUser().ProvNum);
        } 
        if (gridEmailMessages.getColumns().Count == 0)
        {
            //Columns do not change.  We only need to set them once.
            gridEmailMessages.beginUpdate();
            gridEmailMessages.getColumns().Clear();
            int colReceivedDatePixCount = 140;
            int colStatusPixCount = 120;
            int colFromPixCount = 200;
            int colSubjectPixCount = 200;
            int colPatientPixCount = 140;
            int colVariablePixCount = gridEmailMessages.Width - 22 - colReceivedDatePixCount - colStatusPixCount - colFromPixCount - colSubjectPixCount - colPatientPixCount;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"ReceivedDate"), colReceivedDatePixCount, HorizontalAlignment.Center));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.DateParse;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"Sent/Received"), colStatusPixCount, HorizontalAlignment.Center));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.StringCompare;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"Subject"), colSubjectPixCount, HorizontalAlignment.Left));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.StringCompare;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"From"), colFromPixCount, HorizontalAlignment.Left));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.StringCompare;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"Patient"), colPatientPixCount, HorizontalAlignment.Left));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.StringCompare;
            gridEmailMessages.getColumns().add(new ODGridColumn(Lan.g(this,"Preview"), colVariablePixCount, HorizontalAlignment.Left));
            gridEmailMessages.getColumns()[gridEmailMessages.getColumns().Count - 1].SortingStrategy = GridSortingStrategy.StringCompare;
        }
         
        gridEmailMessages.getRows().Clear();
        for (int i = 0;i < ListEmailMessages.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.setTag(ListEmailMessages[i]);
            //Used to locate the correct email message if the user decides to sort the grid.
            if (ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.Received || ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.WebMailReceived || ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.ReceivedEncrypted || ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.ReceivedDirect)
            {
                row.setBold(true);
            }
             
            //unread
            row.getCells().Add(new OpenDental.UI.ODGridCell(ListEmailMessages[i].MsgDateTime.ToString()));
            //ReceivedDate
            row.getCells().Add(new OpenDental.UI.ODGridCell(ListEmailMessages[i].SentOrReceived.ToString()));
            //Status
            row.getCells().Add(new OpenDental.UI.ODGridCell(ListEmailMessages[i].Subject));
            //Subject
            row.getCells().Add(new OpenDental.UI.ODGridCell(ListEmailMessages[i].FromAddress));
            //From
            long patNumRegardingPatient = ListEmailMessages[i].PatNum;
            //Webmail messages should list the patient as the PatNumSubj, which means "the patient whom this message is regarding".
            if (ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.WebMailReceived || ListEmailMessages[i].SentOrReceived == EmailSentOrReceived.WebMailRecdRead)
            {
                patNumRegardingPatient = ListEmailMessages[i].PatNumSubj;
            }
             
            if (patNumRegardingPatient == 0)
            {
                row.getCells().Add(new OpenDental.UI.ODGridCell(""));
            }
            else
            {
                //Patient
                Patient pat = Patients.getPat(patNumRegardingPatient);
                row.getCells().Add(new OpenDental.UI.ODGridCell(pat.getNameLF()));
            } 
            //Patient
            String preview = ListEmailMessages[i].BodyText.Replace("\r\n", " ").Replace('\n', ' ');
            //Replace newlines with spaces, in order to compress the preview.
            if (preview.Length > 50)
            {
                preview = preview.Substring(0, 50) + "...";
            }
             
            row.getCells().Add(new OpenDental.UI.ODGridCell(preview));
            //Preview
            gridEmailMessages.getRows().add(row);
        }
        gridEmailMessages.endUpdate();
    }

    private void gridEmailMessages_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getRow() == -1)
        {
            return ;
        }
         
        EmailMessage emailMessage = (EmailMessage)gridEmailMessages.getRows().get___idx(e.getRow()).getTag();
        if (emailMessage.SentOrReceived == EmailSentOrReceived.WebMailReceived || emailMessage.SentOrReceived == EmailSentOrReceived.WebMailRecdRead || emailMessage.SentOrReceived == EmailSentOrReceived.WebMailSent || emailMessage.SentOrReceived == EmailSentOrReceived.WebMailSentRead)
        {
            //web mail uses special secure messaging portal
            FormWebMailMessageEdit FormWMME = new FormWebMailMessageEdit(emailMessage.PatNum,emailMessage.EmailMessageNum);
            if (FormWMME.ShowDialog() != DialogResult.Abort)
            {
                //will only return Abort if validation fails on load, in which case the message will remain unread
                EmailMessages.updateSentOrReceivedRead(emailMessage);
            }
             
        }
        else
        {
            //Mark the message read.
            FormEmailMessageEdit formEME = new FormEmailMessageEdit(emailMessage);
            formEME.ShowDialog();
            emailMessage = EmailMessages.getOne(emailMessage.EmailMessageNum);
            //Fetch from DB, in case changed to to decrypt.
            if (emailMessage != null && emailMessage.SentOrReceived != EmailSentOrReceived.ReceivedEncrypted)
            {
                //emailMessage could be null if the message was deleted in FormEmailMessageEdit().
                EmailMessages.updateSentOrReceivedRead(emailMessage);
            }
             
        } 
        fillGridEmailMessages();
    }

    //To show the email is read.
    private void butChangePat_Click(Object sender, EventArgs e) throws Exception {
        if (gridEmailMessages.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an email message.");
            return ;
        }
         
        FormPatientSelect form = new FormPatientSelect();
        if (form.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < gridEmailMessages.getSelectedIndices().Length;i++)
        {
            EmailMessage emailMessage = (EmailMessage)gridEmailMessages.getRows()[gridEmailMessages.getSelectedIndices()[i]].Tag;
            emailMessage.PatNum = form.SelectedPatNum;
            EmailMessages.updatePatNum(emailMessage);
        }
        MessageBox.Show(Lan.g(this,"Email messages moved successfully") + ": " + gridEmailMessages.getSelectedIndices().Length);
        fillGridEmailMessages();
    }

    //Refresh grid to show changed patient.
    private void butMarkUnread_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        for (int i = 0;i < gridEmailMessages.getSelectedIndices().Length;i++)
        {
            EmailMessage emailMessage = (EmailMessage)gridEmailMessages.getRows()[gridEmailMessages.getSelectedIndices()[i]].Tag;
            EmailMessages.updateSentOrReceivedUnread(emailMessage);
        }
        fillGridEmailMessages();
        Cursor = Cursors.Default;
    }

    private void butMarkRead_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        for (int i = 0;i < gridEmailMessages.getSelectedIndices().Length;i++)
        {
            EmailMessage emailMessage = (EmailMessage)gridEmailMessages.getRows()[gridEmailMessages.getSelectedIndices()[i]].Tag;
            EmailMessages.updateSentOrReceivedRead(emailMessage);
        }
        fillGridEmailMessages();
        Cursor = Cursors.Default;
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        getMessages();
    }

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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailInbox.class);
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.labelInboxComputerName = new System.Windows.Forms.Label();
        this.labelThisComputer = new System.Windows.Forms.Label();
        this.butChangePat = new OpenDental.UI.Button();
        this.butMarkUnread = new OpenDental.UI.Button();
        this.butMarkRead = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.gridEmailMessages = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.odToolBarButton1 = new OpenDental.UI.ODToolBarButton();
        this.SuspendLayout();
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // labelInboxComputerName
        //
        this.labelInboxComputerName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelInboxComputerName.Location = new System.Drawing.Point(9, 233);
        this.labelInboxComputerName.Name = "labelInboxComputerName";
        this.labelInboxComputerName.Size = new System.Drawing.Size(410, 16);
        this.labelInboxComputerName.TabIndex = 144;
        this.labelInboxComputerName.Text = "Computer Name Where New Email Is Fetched: ";
        this.labelInboxComputerName.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelThisComputer
        //
        this.labelThisComputer.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelThisComputer.Location = new System.Drawing.Point(127, 249);
        this.labelThisComputer.Name = "labelThisComputer";
        this.labelThisComputer.Size = new System.Drawing.Size(292, 16);
        this.labelThisComputer.TabIndex = 145;
        this.labelThisComputer.Text = "This Computer Name: ";
        this.labelThisComputer.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butChangePat
        //
        this.butChangePat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangePat.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butChangePat.setAutosize(true);
        this.butChangePat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangePat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangePat.setCornerRadius(4F);
        this.butChangePat.Location = new System.Drawing.Point(639, 0);
        this.butChangePat.Name = "butChangePat";
        this.butChangePat.Size = new System.Drawing.Size(75, 24);
        this.butChangePat.TabIndex = 146;
        this.butChangePat.Text = "Change Pat";
        this.butChangePat.Click += new System.EventHandler(this.butChangePat_Click);
        //
        // butMarkUnread
        //
        this.butMarkUnread.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMarkUnread.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butMarkUnread.setAutosize(true);
        this.butMarkUnread.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMarkUnread.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMarkUnread.setCornerRadius(4F);
        this.butMarkUnread.Location = new System.Drawing.Point(720, 0);
        this.butMarkUnread.Name = "butMarkUnread";
        this.butMarkUnread.Size = new System.Drawing.Size(75, 24);
        this.butMarkUnread.TabIndex = 143;
        this.butMarkUnread.Text = "Mark Unread";
        this.butMarkUnread.Click += new System.EventHandler(this.butMarkUnread_Click);
        //
        // butMarkRead
        //
        this.butMarkRead.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMarkRead.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butMarkRead.setAutosize(true);
        this.butMarkRead.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMarkRead.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMarkRead.setCornerRadius(4F);
        this.butMarkRead.Location = new System.Drawing.Point(801, 0);
        this.butMarkRead.Name = "butMarkRead";
        this.butMarkRead.Size = new System.Drawing.Size(75, 24);
        this.butMarkRead.TabIndex = 142;
        this.butMarkRead.Text = "Mark Read";
        this.butMarkRead.Click += new System.EventHandler(this.butMarkRead_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(882, 0);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 141;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // gridEmailMessages
        //
        this.gridEmailMessages.setAllowSortingByColumn(true);
        this.gridEmailMessages.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEmailMessages.setHScrollVisible(false);
        this.gridEmailMessages.Location = new System.Drawing.Point(12, 27);
        this.gridEmailMessages.Name = "gridEmailMessages";
        this.gridEmailMessages.setScrollValue(0);
        this.gridEmailMessages.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridEmailMessages.Size = new System.Drawing.Size(945, 203);
        this.gridEmailMessages.TabIndex = 140;
        this.gridEmailMessages.setTitle("Email Messages");
        this.gridEmailMessages.setTranslationName("TableApptProcs");
        this.gridEmailMessages.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridEmailMessages.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEmailMessages_CellDoubleClick(sender, e);
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
        this.butClose.Location = new System.Drawing.Point(882, 236);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // odToolBarButton1
        //
        this.odToolBarButton1.setBounds(new System.Drawing.Rectangle(0, 0, 0, 0));
        this.odToolBarButton1.setDropDownMenu(null);
        this.odToolBarButton1.setEnabled(true);
        this.odToolBarButton1.setImageIndex(-1);
        this.odToolBarButton1.setPushed(false);
        this.odToolBarButton1.setState(OpenDental.UI.ToolBarButtonState.Normal);
        this.odToolBarButton1.setStyle(OpenDental.UI.ODToolBarButtonStyle.PushButton);
        this.odToolBarButton1.setTag("");
        this.odToolBarButton1.setText("");
        this.odToolBarButton1.setToolTipText("");
        //
        // FormEmailInbox
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(982, 287);
        this.Controls.Add(this.butChangePat);
        this.Controls.Add(this.labelThisComputer);
        this.Controls.Add(this.labelInboxComputerName);
        this.Controls.Add(this.butMarkUnread);
        this.Controls.Add(this.butMarkRead);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.gridEmailMessages);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Menu = this.mainMenu1;
        this.MinimumSize = new System.Drawing.Size(932, 200);
        this.Name = "FormEmailInbox";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Email Inbox";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormEmailInbox_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSetup = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODGrid gridEmailMessages;
    private OpenDental.UI.ODToolBarButton odToolBarButton1;
    private OpenDental.UI.Button butRefresh;
    private OpenDental.UI.Button butMarkRead;
    private OpenDental.UI.Button butMarkUnread;
    private System.Windows.Forms.Label labelInboxComputerName = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelThisComputer = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butChangePat;
}


