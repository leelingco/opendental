//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormCreditCardEdit;
import OpenDental.FormPatientSelect;
import OpenDental.FormXchargeSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDental.FormCreditCardManage;
import OpenDental.Properties.Resources;

public class FormCreditCardManage  extends Form 
{

    private Patient PatCur;
    private List<CreditCard> creditCards = new List<CreditCard>();
    public FormCreditCardManage(Patient pat) throws Exception {
        initializeComponent();
        Lan.F(this);
        PatCur = pat;
    }

    private void formCreditCardManage_Load(Object sender, EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.StoreCCnumbers) && Programs.isEnabled(ProgramName.Xcharge))
        {
            labelXChargeWarning.Visible = true;
        }
         
        refreshCardList();
        if (creditCards.Count > 0)
        {
            listCreditCards.SelectedIndex = 0;
        }
         
    }

    private void refreshCardList() throws Exception {
        listCreditCards.Items.Clear();
        creditCards = CreditCards.refresh(PatCur.PatNum);
        for (int i = 0;i < creditCards.Count;i++)
        {
            listCreditCards.Items.Add(creditCards[i].CCNumberMasked);
        }
    }

    private void listCreditCards_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        if (listCreditCards.SelectedIndex == -1)
        {
            return ;
        }
         
        int prev = creditCards.Count;
        int placement = listCreditCards.SelectedIndex;
        FormCreditCardEdit FormCCE = new FormCreditCardEdit(PatCur);
        FormCCE.CreditCardCur = creditCards[placement];
        FormCCE.ShowDialog();
        refreshCardList();
        if (creditCards.Count == prev)
        {
            listCreditCards.SelectedIndex = placement;
        }
        else if (creditCards.Count > 0)
        {
            listCreditCards.SelectedIndex = 0;
        }
          
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!PrefC.getBool(PrefName.StoreCCnumbers))
        {
            if (Programs.isEnabled(ProgramName.Xcharge))
            {
                Program prog = Programs.getCur(ProgramName.Xcharge);
                String path = Programs.getProgramPath(prog);
                if (!File.Exists(path))
                {
                    MsgBox.show(this,"Path is not valid.");
                    if (Security.isAuthorized(Permissions.Setup))
                    {
                        FormXchargeSetup FormX = new FormXchargeSetup();
                        FormX.ShowDialog();
                        if (FormX.DialogResult != DialogResult.OK)
                        {
                            return ;
                        }
                         
                    }
                     
                }
                 
                String user = ProgramProperties.getPropVal(prog.ProgramNum,"Username");
                String password = ProgramProperties.getPropVal(prog.ProgramNum,"Password");
                ProcessStartInfo info = new ProcessStartInfo(path);
                String resultfile = Path.Combine(Path.GetDirectoryName(path), "XResult.txt");
                File.Delete(resultfile);
                //delete the old result file.
                info.Arguments = "";
                info.Arguments += "/TRANSACTIONTYPE:ArchiveVaultAdd /LOCKTRANTYPE ";
                info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
                info.Arguments += "/USERID:" + user + " ";
                info.Arguments += "/PASSWORD:" + password + " ";
                info.Arguments += "/VALIDATEARCHIVEVAULTACCOUNT ";
                info.Arguments += "/STAYONTOP ";
                info.Arguments += "/SMARTAUTOPROCESS ";
                info.Arguments += "/AUTOCLOSE ";
                info.Arguments += "/HIDEMAINWINDOW ";
                info.Arguments += "/SMALLWINDOW ";
                info.Arguments += "/NORESULTDIALOG ";
                info.Arguments += "/TOOLBAREXITBUTTON ";
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
                String xChargeToken = "";
                String accountMasked = "";
                String exp = "";
                    ;
                boolean insertCard = false;
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
                                    break;
                                }
                                 
                                insertCard = true;
                            }
                             
                            if (line.StartsWith("XCACCOUNTID="))
                            {
                                xChargeToken = PIn.String(line.Substring(12));
                            }
                             
                            if (line.StartsWith("ACCOUNT="))
                            {
                                accountMasked = PIn.String(line.Substring(8));
                            }
                             
                            if (line.StartsWith("EXPIRATION="))
                            {
                                exp = PIn.String(line.Substring(11));
                            }
                             
                            line = reader.ReadLine();
                        }
                        if (insertCard && !StringSupport.equals(xChargeToken, ""))
                        {
                            //Might not be necessary but we've had successful charges with no tokens returned before.
                            CreditCard creditCardCur = new CreditCard();
                            List<CreditCard> itemOrderCount = CreditCards.refresh(PatCur.PatNum);
                            creditCardCur.PatNum = PatCur.PatNum;
                            creditCardCur.ItemOrder = itemOrderCount.Count;
                            creditCardCur.CCNumberMasked = accountMasked;
                            creditCardCur.XChargeToken = xChargeToken;
                            creditCardCur.CCExpiration = new DateTime(Convert.ToInt32("20" + PIn.String(exp.Substring(2, 2))), Convert.ToInt32(PIn.String(exp.Substring(0, 2))), 1);
                            CreditCards.insert(creditCardCur);
                        }
                         
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
                refreshCardList();
                return ;
            }
            else
            {
                MsgBox.show(this,"Not allowed to store credit cards.");
                return ;
            } 
        }
         
        boolean remember = false;
        int placement = listCreditCards.SelectedIndex;
        if (placement != -1)
        {
            remember = true;
        }
         
        FormCreditCardEdit FormCCE = new FormCreditCardEdit(PatCur);
        FormCCE.CreditCardCur = new CreditCard();
        FormCCE.CreditCardCur.setIsNew(true);
        FormCCE.ShowDialog();
        refreshCardList();
        if (remember)
        {
            //in case they canceled and had one selected
            listCreditCards.SelectedIndex = placement;
        }
         
        if (FormCCE.DialogResult == DialogResult.OK && creditCards.Count > 0)
        {
            listCreditCards.SelectedIndex = 0;
        }
         
    }

    private void butMoveTo_Click(Object sender, EventArgs e) throws Exception {
        if (listCreditCards.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a card first.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Move this credit card information to a different patient account?"))
        {
            return ;
        }
         
        FormPatientSelect form = new FormPatientSelect();
        if (form.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        int selected = listCreditCards.SelectedIndex;
        CreditCard creditCard = creditCards[selected];
        creditCard.PatNum = form.SelectedPatNum;
        CreditCards.update(creditCard);
        refreshCardList();
        MessageBox.Show("Credit card moved successfully");
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        int placement = listCreditCards.SelectedIndex;
        if (placement == -1)
        {
            MsgBox.show(this,"Please select a card first.");
            return ;
        }
         
        if (placement == 0)
        {
            return ;
        }
         
        //can't move up any more
        int oldIdx = new int();
        int newIdx = new int();
        CreditCard oldItem;
        CreditCard newItem;
        oldIdx = creditCards[placement].ItemOrder;
        newIdx = oldIdx + 1;
        for (int i = 0;i < creditCards.Count;i++)
        {
            if (creditCards[i].ItemOrder == oldIdx)
            {
                oldItem = creditCards[i];
                newItem = creditCards[i - 1];
                oldItem.ItemOrder = newItem.ItemOrder;
                newItem.ItemOrder -= 1;
                CreditCards.update(oldItem);
                CreditCards.update(newItem);
            }
             
        }
        refreshCardList();
        listCreditCards.SetSelected(placement - 1, true);
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        int placement = listCreditCards.SelectedIndex;
        if (placement == -1)
        {
            MsgBox.show(this,"Please select a card first.");
            return ;
        }
         
        if (placement == creditCards.Count - 1)
        {
            return ;
        }
         
        //can't move down any more
        int oldIdx = new int();
        int newIdx = new int();
        CreditCard oldItem;
        CreditCard newItem;
        oldIdx = creditCards[placement].ItemOrder;
        newIdx = oldIdx - 1;
        for (int i = 0;i < creditCards.Count;i++)
        {
            if (creditCards[i].ItemOrder == newIdx)
            {
                newItem = creditCards[i];
                oldItem = creditCards[i - 1];
                newItem.ItemOrder = oldItem.ItemOrder;
                oldItem.ItemOrder -= 1;
                CreditCards.update(oldItem);
                CreditCards.update(newItem);
            }
             
        }
        refreshCardList();
        listCreditCards.SetSelected(placement + 1, true);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCreditCardManage.class);
        this.butClose = new OpenDental.UI.Button();
        this.listCreditCards = new System.Windows.Forms.ListBox();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.labelXChargeWarning = new System.Windows.Forms.Label();
        this.butMoveTo = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(210, 165);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(82, 26);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // listCreditCards
        //
        this.listCreditCards.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listCreditCards.FormattingEnabled = true;
        this.listCreditCards.IntegralHeight = false;
        this.listCreditCards.Location = new System.Drawing.Point(12, 12);
        this.listCreditCards.Name = "listCreditCards";
        this.listCreditCards.Size = new System.Drawing.Size(175, 179);
        this.listCreditCards.TabIndex = 4;
        this.listCreditCards.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.listCreditCards_MouseDoubleClick);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(210, 113);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(82, 26);
        this.butDown.TabIndex = 37;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(210, 84);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(82, 26);
        this.butUp.TabIndex = 38;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(210, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(82, 26);
        this.butAdd.TabIndex = 36;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // labelXChargeWarning
        //
        this.labelXChargeWarning.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelXChargeWarning.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
        this.labelXChargeWarning.Location = new System.Drawing.Point(12, 195);
        this.labelXChargeWarning.Name = "labelXChargeWarning";
        this.labelXChargeWarning.Size = new System.Drawing.Size(280, 45);
        this.labelXChargeWarning.TabIndex = 39;
        this.labelXChargeWarning.Text = "You should turn off the option in Module Setup for \"allow storing credit card num" + "bers\" in order to start using tokens.";
        this.labelXChargeWarning.Visible = false;
        //
        // butMoveTo
        //
        this.butMoveTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMoveTo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butMoveTo.setAutosize(true);
        this.butMoveTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMoveTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMoveTo.setCornerRadius(4F);
        this.butMoveTo.Location = new System.Drawing.Point(210, 41);
        this.butMoveTo.Name = "butMoveTo";
        this.butMoveTo.Size = new System.Drawing.Size(82, 26);
        this.butMoveTo.TabIndex = 40;
        this.butMoveTo.Text = "Move To Pat";
        this.butMoveTo.Click += new System.EventHandler(this.butMoveTo_Click);
        //
        // FormCreditCardManage
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(304, 240);
        this.Controls.Add(this.butMoveTo);
        this.Controls.Add(this.labelXChargeWarning);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.listCreditCards);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormCreditCardManage";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Credit Card Manage";
        this.Load += new System.EventHandler(this.FormCreditCardManage_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ListBox listCreditCards = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label labelXChargeWarning = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butMoveTo;
}


