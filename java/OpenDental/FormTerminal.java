//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSheetFillEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.TerminalActive;
import OpenDentBusiness.TerminalActives;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTerminal  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butDone;
    private Timer timer1 = new Timer();
    private TextBox textWelcome = new TextBox();
    private IContainer components = new IContainer();
    //private bool IsChangingTab;
    //private TerminalStatusEnum TerminalStatus;
    //private Patient PatCur;
    private Label labelConnection = new Label();
    //private Family FamCur;
    private Label labelForms = new Label();
    private ListBox listForms = new ListBox();
    /**
    * This is the list of sheets that the patient will be filling out.
    */
    private List<Sheet> SheetList = new List<Sheet>();
    /**
    * This gets set externally when in SimpleMode, and it also gets set in nonSimpleMode by the timer signal.
    */
    public long PatNum = new long();
    private Panel panelClose = new Panel();
    private Label labelThankYou = new Label();
    /**
    * In simple mode, the terminal is launched from the local machine, and is not tracked in the database in any way.
    */
    public boolean IsSimpleMode = new boolean();
    private FormSheetFillEdit formSheetFillEdit;
    /**
    * 
    */
    public FormTerminal() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.textWelcome = new System.Windows.Forms.TextBox();
        this.labelConnection = new System.Windows.Forms.Label();
        this.listForms = new System.Windows.Forms.ListBox();
        this.labelForms = new System.Windows.Forms.Label();
        this.panelClose = new System.Windows.Forms.Panel();
        this.labelThankYou = new System.Windows.Forms.Label();
        this.butDone = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // timer1
        //
        this.timer1.Enabled = true;
        this.timer1.Interval = 4000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // textWelcome
        //
        this.textWelcome.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.textWelcome.BackColor = System.Drawing.SystemColors.Control;
        this.textWelcome.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textWelcome.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textWelcome.Location = new System.Drawing.Point(235, 149);
        this.textWelcome.Multiline = true;
        this.textWelcome.Name = "textWelcome";
        this.textWelcome.ReadOnly = true;
        this.textWelcome.Size = new System.Drawing.Size(366, 169);
        this.textWelcome.TabIndex = 3;
        this.textWelcome.TabStop = false;
        this.textWelcome.Text = "Welcome!\r\n\r\nThis kiosk is used for filling out forms.\r\n\r\nThe receptionist will pr" + "epare the screen for you.";
        this.textWelcome.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // labelConnection
        //
        this.labelConnection.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.labelConnection.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelConnection.Location = new System.Drawing.Point(196, 650);
        this.labelConnection.Name = "labelConnection";
        this.labelConnection.Size = new System.Drawing.Size(444, 29);
        this.labelConnection.TabIndex = 4;
        this.labelConnection.Text = "Connection to server has been lost";
        this.labelConnection.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // listForms
        //
        this.listForms.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.listForms.FormattingEnabled = true;
        this.listForms.Location = new System.Drawing.Point(343, 361);
        this.listForms.Name = "listForms";
        this.listForms.Size = new System.Drawing.Size(149, 173);
        this.listForms.TabIndex = 5;
        this.listForms.DoubleClick += new System.EventHandler(this.listForms_DoubleClick);
        //
        // labelForms
        //
        this.labelForms.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.labelForms.Location = new System.Drawing.Point(342, 340);
        this.labelForms.Name = "labelForms";
        this.labelForms.Size = new System.Drawing.Size(175, 18);
        this.labelForms.TabIndex = 6;
        this.labelForms.Text = "Forms - Double click to edit";
        this.labelForms.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // panelClose
        //
        this.panelClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.panelClose.Location = new System.Drawing.Point(799, 628);
        this.panelClose.Name = "panelClose";
        this.panelClose.Size = new System.Drawing.Size(66, 59);
        this.panelClose.TabIndex = 7;
        this.panelClose.Click += new System.EventHandler(this.panelClose_Click);
        //
        // labelThankYou
        //
        this.labelThankYou.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.labelThankYou.Location = new System.Drawing.Point(367, 530);
        this.labelThankYou.Name = "labelThankYou";
        this.labelThankYou.Size = new System.Drawing.Size(100, 23);
        this.labelThankYou.TabIndex = 8;
        this.labelThankYou.Text = "Thank You";
        this.labelThankYou.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        this.labelThankYou.Visible = false;
        //
        // butDone
        //
        this.butDone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDone.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.butDone.setAutosize(true);
        this.butDone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDone.setCornerRadius(4F);
        this.butDone.Location = new System.Drawing.Point(380, 551);
        this.butDone.Name = "butDone";
        this.butDone.Size = new System.Drawing.Size(75, 24);
        this.butDone.TabIndex = 1;
        this.butDone.Text = "Done";
        this.butDone.Click += new System.EventHandler(this.butDone_Click);
        //
        // FormTerminal
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(864, 686);
        this.ControlBox = false;
        this.Controls.Add(this.butDone);
        this.Controls.Add(this.listForms);
        this.Controls.Add(this.labelThankYou);
        this.Controls.Add(this.panelClose);
        this.Controls.Add(this.labelForms);
        this.Controls.Add(this.labelConnection);
        this.Controls.Add(this.textWelcome);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTerminal";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.FormTerminal_Load);
        this.Shown += new System.EventHandler(this.FormTerminal_Shown);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTerminal_Load(Object sender, EventArgs e) throws Exception {
        this.Size = System.Windows.Forms.Screen.PrimaryScreen.Bounds.Size;
        this.Location = new Point(0, 0);
        labelConnection.Visible = false;
        if (IsSimpleMode)
        {
            timer1.Enabled = false;
            textWelcome.Visible = false;
        }
        else
        {
            //tell the database that a terminal is newly active on this computer.
            TerminalActives.DeleteAllForComputer(Environment.MachineName);
            TerminalActive terminal = new TerminalActive();
            terminal.ComputerName = Environment.MachineName;
            TerminalActives.insert(terminal);
            unloadPatient();
        } 
    }

    //do not load a patient
    private void formTerminal_Shown(Object sender, EventArgs e) throws Exception {
        if (IsSimpleMode)
        {
            loadPatient();
        }
         
    }

    /**
    * Used in both modes.  Loads the list of sheets into the listbox.  Then launches the first sheet and goes through the sequence of sheets.  If user clicks cancel, the seqeunce will exit.  If not IsSimpleMode, then the TerminalManager can also send a signal to immediately terminate the sequence.
    */
    private void loadPatient() throws Exception {
        SheetList = Sheets.getForTerminal(PatNum);
        listForms.Items.Clear();
        if (SheetList.Count == 0)
        {
            return ;
        }
         
        for (int i = 0;i < SheetList.Count;i++)
        {
            listForms.Items.Add(SheetList[i].Description);
        }
        for (int i = 0;i < SheetList.Count;i++)
        {
            //we want the very freshest copy of the sheet, so we go straight to the database for it
            Sheet sheet = Sheets.GetSheet(SheetList[i].SheetNum);
            formSheetFillEdit = new FormSheetFillEdit(sheet);
            formSheetFillEdit.IsInTerminal = true;
            if (!IsSimpleMode)
            {
                formSheetFillEdit.TerminalListenShut = true;
            }
             
            formSheetFillEdit.ShowDialog();
            if (formSheetFillEdit.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
    }

    //either patient clicked cancel, or in not IsSimpleMode a close signal was received.
    //breaks out of looping through sheets.
    private void listForms_DoubleClick(Object sender, EventArgs e) throws Exception {
        //this might be used after the patient has completed all the forms and wishes to review or alter one of them
        if (listForms.SelectedIndex == -1)
        {
            return ;
        }
         
        Sheet sheet = Sheets.GetSheet(SheetList[listForms.SelectedIndex].SheetNum);
        formSheetFillEdit = new FormSheetFillEdit(sheet);
        formSheetFillEdit.IsInTerminal = true;
        formSheetFillEdit.ShowDialog();
    }

    //no point in refreshing the list because patient cannot edit description or delete sheet.
    /**
    * Only in nonSimpleMode.  Occurs every 4 seconds. Checks database for status changes.
    */
    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        TerminalActive terminal;
        try
        {
            terminal = TerminalActives.GetTerminal(Environment.MachineName);
            labelConnection.Visible = false;
        }
        catch (Exception __dummyCatchVar0)
        {
            //SocketException if db connection gets lost.
            labelConnection.Visible = true;
            return ;
        }

        if (terminal == null)
        {
            return ;
        }
         
        if (terminal.PatNum == PatNum)
        {
            return ;
        }
         
        //someone changed the PatNum remotely from the terminal manager.
        if (terminal.PatNum == 0)
        {
            //force clearing of patient (rare)
            unloadPatient();
        }
        else
        {
            //receptionist wants to load up a patient.  This should also work if patient is quickly changed in less than 4 seconds.
            PatNum = terminal.PatNum;
            textWelcome.Visible = false;
            labelForms.Visible = true;
            listForms.Visible = true;
            butDone.Visible = true;
            loadPatient();
        } 
    }

    private void butDone_Click(Object sender, EventArgs e) throws Exception {
        if (IsSimpleMode)
        {
            labelForms.Visible = false;
            listForms.Visible = false;
            butDone.Visible = false;
            Sheets.clearFromTerminal(PatNum);
            PatNum = 0;
            labelThankYou.Visible = true;
        }
        else
        {
            Sheets.clearFromTerminal(PatNum);
            unloadPatient();
            //tell the database about it so that the terminal manager can see
            TerminalActive terminal = TerminalActives.GetTerminal(Environment.MachineName);
            if (terminal == null)
            {
                return ;
            }
             
            terminal.PatNum = 0;
            TerminalActives.update(terminal);
        } 
    }

    /**
    * Only used in nonSimpleMode.  At startup, in response to a signal from terminal manager, or when click Done button.
    */
    private void unloadPatient() throws Exception {
        textWelcome.Visible = true;
        labelForms.Visible = false;
        listForms.Visible = false;
        butDone.Visible = false;
        PatNum = 0;
    }

    private void panelClose_Click(Object sender, EventArgs e) throws Exception {
        //It is fairly safe to not have a password, because the program will close completely in remote mode,
        //and in simple mode, the patient is usually supervised.
        if (StringSupport.equals(PrefC.getString(PrefName.TerminalClosePassword), ""))
        {
            if (!IsSimpleMode)
            {
                TerminalActives.DeleteAllForComputer(Environment.MachineName);
            }
             
            Close();
            return ;
        }
         
        InputBox input = new InputBox("Password");
        input.textResult.PasswordChar = '*';
        input.ShowDialog();
        if (input.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (!StringSupport.equals(input.textResult.Text, PrefC.getString(PrefName.TerminalClosePassword)))
        {
            MsgBox.show(this,"Invalid password.");
            return ;
        }
         
        if (!IsSimpleMode)
        {
            TerminalActives.DeleteAllForComputer(Environment.MachineName);
        }
         
        Close();
    }

}


