//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormCreditCardManage;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormXchargeTokenTool;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormXchargeTokenTool  extends Form 
{

    private List<CreditCard> CardList = new List<CreditCard>();
    public FormXchargeTokenTool() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formXchargeTokenTool_Load(Object sender, EventArgs e) throws Exception {
        CardList = CreditCards.getCreditCardsWithTokens();
        textTotal.Text = CardList.Count.ToString();
        textVerified.Text = "0";
        textInvalid.Text = "0";
        Program prog = Programs.getCur(ProgramName.Xcharge);
        String path = Programs.getProgramPath(prog);
        if (prog == null || !prog.Enabled)
        {
            MsgBox.show(this,"X-Charge entry is not set up.");
            //should never happen
            butCheck.Visible = false;
            return ;
        }
         
        if (!File.Exists(path))
        {
            MsgBox.show(this,"X-Charge path is not valid.");
            butCheck.Visible = false;
            return ;
        }
         
    }

    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        CardList = filterCardList();
        Cursor = Cursors.Default;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormXChargeTest","PatNum"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormXChargeTest","First"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormXChargeTest","Last"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormXChargeTest","CCNumberMasked"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormXChargeTest","Exp"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormXChargeTest","Token"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < CardList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            Patient pat = Patients.GetLim(CardList[i].PatNum);
            row.getCells().Add(CardList[i].PatNum.ToString());
            row.getCells().add(pat.FName);
            row.getCells().add(pat.LName);
            row.getCells().Add(CardList[i].CCNumberMasked);
            row.getCells().Add(CardList[i].CCExpiration.ToString("MMyy"));
            row.getCells().Add(CardList[i].XChargeToken);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        if (CardList.Count == 0)
        {
            MsgBox.show(this,"There are no invalid tokens in the database.");
        }
         
    }

    private List<CreditCard> filterCardList() throws Exception {
        int verified = 0;
        int invalid = 0;
        textVerified.Text = verified.ToString();
        textInvalid.Text = invalid.ToString();
        for (int i = CardList.Count - 1;i >= 0;i--)
        {
            Program prog = Programs.getCur(ProgramName.Xcharge);
            String path = Programs.getProgramPath(prog);
            ProgramProperty prop = (ProgramProperty)ProgramProperties.getForProgram(prog.ProgramNum)[0];
            ProcessStartInfo info = new ProcessStartInfo(path);
            String resultfile = Path.Combine(Path.GetDirectoryName(path), "XResult.txt");
            File.Delete(resultfile);
            //delete the old result file.
            info.Arguments += "/TRANSACTIONTYPE:ARCHIVEVAULTQUERY ";
            info.Arguments += "/XCACCOUNTID:" + CardList[i].XChargeToken + " ";
            info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
            info.Arguments += "/USERID:" + ProgramProperties.getPropVal(prog.ProgramNum,"Username") + " ";
            info.Arguments += "/PASSWORD:" + ProgramProperties.getPropVal(prog.ProgramNum,"Password") + " ";
            info.Arguments += "/AUTOPROCESS ";
            info.Arguments += "/AUTOCLOSE ";
            info.Arguments += "/NORESULTDIALOG ";
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
            String resulttext = "";
            String line = "";
            String account = "";
            String exp = "";
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
                        if (line.StartsWith("ACCOUNT="))
                        {
                            account = line.Substring(8);
                        }
                        else if (line.StartsWith("EXPIRATION="))
                        {
                            exp = line.Substring(11);
                        }
                          
                        line = reader.ReadLine();
                    }
                    if (CardList[i].CCNumberMasked.Length > 4 && account.Length > 4 && CardList[i].CCNumberMasked.Substring(CardList[i].CCNumberMasked.Length - 4) == account.Substring(account.Length - 4) && StringSupport.equals(CardList[i].CCExpiration.ToString("MMyy"), exp))
                    {
                        //The credit card on file matches the one in X-Charge, so remove from the list.
                        CardList.Remove(CardList[i]);
                        verified++;
                    }
                    else
                    {
                        invalid++;
                    } 
                }
            }
            finally
            {
                if (reader != null)
                    Disposable.mkDisposable(reader).dispose();
                 
            }
            textVerified.Text = verified.ToString();
            textInvalid.Text = invalid.ToString();
        }
        return CardList;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormCreditCardManage FormCCM = new FormCreditCardManage(Patients.GetPat(CardList[gridMain.getSelectedIndex()].PatNum));
        FormCCM.ShowDialog();
    }

    private void butCheck_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormXchargeTokenTool.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.labelWarning = new System.Windows.Forms.Label();
        this.butCheck = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.labelTotal = new System.Windows.Forms.Label();
        this.labelVerified = new System.Windows.Forms.Label();
        this.labelInvalid = new System.Windows.Forms.Label();
        this.textTotal = new System.Windows.Forms.TextBox();
        this.textVerified = new System.Windows.Forms.TextBox();
        this.textInvalid = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 83);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(701, 370);
        this.gridMain.TabIndex = 68;
        this.gridMain.setTitle("Invalid X-Charge Tokens");
        this.gridMain.setTranslationName("FormDisplayFields");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // labelWarning
        //
        this.labelWarning.Location = new System.Drawing.Point(9, 17);
        this.labelWarning.Name = "labelWarning";
        this.labelWarning.Size = new System.Drawing.Size(704, 63);
        this.labelWarning.TabIndex = 69;
        this.labelWarning.Text = resources.GetString("labelWarning.Text");
        //
        // butCheck
        //
        this.butCheck.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheck.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCheck.setAutosize(true);
        this.butCheck.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheck.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheck.setCornerRadius(4F);
        this.butCheck.Location = new System.Drawing.Point(12, 498);
        this.butCheck.Name = "butCheck";
        this.butCheck.Size = new System.Drawing.Size(75, 24);
        this.butCheck.TabIndex = 3;
        this.butCheck.Text = "Check";
        this.butCheck.Click += new System.EventHandler(this.butCheck_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(638, 498);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // labelTotal
        //
        this.labelTotal.Location = new System.Drawing.Point(265, 461);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(89, 16);
        this.labelTotal.TabIndex = 70;
        this.labelTotal.Text = "Total Cards:";
        this.labelTotal.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelVerified
        //
        this.labelVerified.Location = new System.Drawing.Point(265, 485);
        this.labelVerified.Name = "labelVerified";
        this.labelVerified.Size = new System.Drawing.Size(89, 16);
        this.labelVerified.TabIndex = 71;
        this.labelVerified.Text = "Verified:";
        this.labelVerified.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelInvalid
        //
        this.labelInvalid.Location = new System.Drawing.Point(265, 510);
        this.labelInvalid.Name = "labelInvalid";
        this.labelInvalid.Size = new System.Drawing.Size(89, 16);
        this.labelInvalid.TabIndex = 72;
        this.labelInvalid.Text = "Invalid:";
        this.labelInvalid.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTotal
        //
        this.textTotal.Enabled = false;
        this.textTotal.Location = new System.Drawing.Point(360, 460);
        this.textTotal.Name = "textTotal";
        this.textTotal.Size = new System.Drawing.Size(64, 20);
        this.textTotal.TabIndex = 73;
        //
        // textVerified
        //
        this.textVerified.Enabled = false;
        this.textVerified.Location = new System.Drawing.Point(360, 484);
        this.textVerified.Name = "textVerified";
        this.textVerified.Size = new System.Drawing.Size(64, 20);
        this.textVerified.TabIndex = 74;
        //
        // textInvalid
        //
        this.textInvalid.Enabled = false;
        this.textInvalid.Location = new System.Drawing.Point(360, 509);
        this.textInvalid.Name = "textInvalid";
        this.textInvalid.Size = new System.Drawing.Size(64, 20);
        this.textInvalid.TabIndex = 75;
        //
        // FormXchargeTokenTool
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.textInvalid);
        this.Controls.Add(this.textVerified);
        this.Controls.Add(this.textTotal);
        this.Controls.Add(this.labelInvalid);
        this.Controls.Add(this.labelVerified);
        this.Controls.Add(this.labelTotal);
        this.Controls.Add(this.labelWarning);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCheck);
        this.Controls.Add(this.butClose);
        this.Name = "FormXchargeTokenTool";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "X-Charge Token Tool";
        this.Load += new System.EventHandler(this.FormXchargeTokenTool_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCheck;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label labelWarning = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTotal = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelVerified = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelInvalid = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTotal = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textVerified = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textInvalid = new System.Windows.Forms.TextBox();
}


