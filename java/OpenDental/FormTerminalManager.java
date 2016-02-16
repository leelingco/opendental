//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormOpenDental;
import OpenDental.FormTerminalManager;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.TerminalActive;
import OpenDentBusiness.TerminalActives;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTerminalManager  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private TextBox textPassword = new TextBox();
    private OpenDental.UI.Button butSave;
    private IContainer components = new IContainer();
    private OpenDental.UI.Button butLoad;
    private Timer timer1 = new Timer();
    private GroupBox groupBox1 = new GroupBox();
    private ListBox listSheets = new ListBox();
    private Label labelSheets = new Label();
    private Label labelPatient = new Label();
    private GroupBox groupBox2 = new GroupBox();
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.Button butDelete;
    private TerminalActive[] TerminalList = new TerminalActive[]();
    //private int counterActivated;
    //private bool isAdvanced;
    /**
    * 
    */
    public FormTerminalManager() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        //gridMain.ContextMenu=contextMenuMain;
        Lan.f(this);
    }

    //#if DEBUG
    //label4.Visible=true;
    //#endif
    //isAdvanced=false;
    //Width=239;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTerminalManager.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelSheets = new System.Windows.Forms.Label();
        this.labelPatient = new System.Windows.Forms.Label();
        this.listSheets = new System.Windows.Forms.ListBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClear = new OpenDental.UI.Button();
        this.butSave = new OpenDental.UI.Button();
        this.butLoad = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(437, 49);
        this.label1.TabIndex = 3;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(7, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(327, 31);
        this.label2.TabIndex = 4;
        this.label2.Text = "To close a kiosk, go to that computer and click the hidden button at the lower ri" + "ght.  You will need to enter this password:";
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(10, 50);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(129, 20);
        this.textPassword.TabIndex = 5;
        //
        // timer1
        //
        this.timer1.Enabled = true;
        this.timer1.Interval = 4000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelSheets);
        this.groupBox1.Controls.Add(this.labelPatient);
        this.groupBox1.Controls.Add(this.listSheets);
        this.groupBox1.Controls.Add(this.butLoad);
        this.groupBox1.Location = new System.Drawing.Point(475, 60);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(168, 252);
        this.groupBox1.TabIndex = 11;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Current Patient";
        //
        // labelSheets
        //
        this.labelSheets.Location = new System.Drawing.Point(11, 37);
        this.labelSheets.Name = "labelSheets";
        this.labelSheets.Size = new System.Drawing.Size(123, 18);
        this.labelSheets.TabIndex = 10;
        this.labelSheets.Text = "Forms for Kiosk";
        this.labelSheets.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(11, 19);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(147, 18);
        this.labelPatient.TabIndex = 9;
        this.labelPatient.Text = "Fname Lname";
        //
        // listSheets
        //
        this.listSheets.FormattingEnabled = true;
        this.listSheets.Location = new System.Drawing.Point(14, 59);
        this.listSheets.Name = "listSheets";
        this.listSheets.Size = new System.Drawing.Size(120, 147);
        this.listSheets.TabIndex = 8;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textPassword);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.butSave);
        this.groupBox2.Location = new System.Drawing.Point(21, 315);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(344, 80);
        this.groupBox2.TabIndex = 12;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Password";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(21, 279);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 14;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 67);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(421, 206);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Active Kiosks");
        this.gridMain.setTranslationName("TableTerminals");
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Location = new System.Drawing.Point(349, 279);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(93, 24);
        this.butClear.TabIndex = 13;
        this.butClear.Text = "Clear Patient";
        this.butClear.UseVisualStyleBackColor = true;
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(145, 48);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(97, 24);
        this.butSave.TabIndex = 6;
        this.butSave.Text = "Save Password";
        this.butSave.UseVisualStyleBackColor = true;
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butLoad
        //
        this.butLoad.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLoad.setAutosize(true);
        this.butLoad.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLoad.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLoad.setCornerRadius(4F);
        this.butLoad.Location = new System.Drawing.Point(14, 219);
        this.butLoad.Name = "butLoad";
        this.butLoad.Size = new System.Drawing.Size(93, 24);
        this.butLoad.TabIndex = 7;
        this.butLoad.Text = "Load Patient";
        this.butLoad.UseVisualStyleBackColor = true;
        this.butLoad.Click += new System.EventHandler(this.butLoad_Click);
        //
        // FormTerminalManager
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(665, 410);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClear);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTerminalManager";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Kiosk Manager";
        this.Load += new System.EventHandler(this.FormTerminalManager_Load);
        this.Activated += new System.EventHandler(this.FormTerminalManager_Activated);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formTerminalManager_Load(Object sender, EventArgs e) throws Exception {
        //MessageBox.Show("load");
        textPassword.Text = PrefC.getString(PrefName.TerminalClosePassword);
        fillGrid();
    }

    private void formTerminalManager_Activated(Object sender, EventArgs e) throws Exception {
        //MessageBox.Show("activated");
        //counterActivated++;
        //label4.Text=counterActivated.ToString();
        fillPat();
    }

    private void fillGrid() throws Exception {
        try
        {
            TerminalList = TerminalActives.refresh();
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        //SocketException if db connection gets lost.
        int selected = gridMain.getSelectedIndex();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableTerminals","Computer Name"),150);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g("TableTerminals","Status"),100);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableTerminals","Patient"),150);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < TerminalList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(TerminalList[i].ComputerName);
            //row.Cells.Add(Lan.g("TerminalStatusEnum",TerminalList[i].TerminalStatus.ToString()));
            if (TerminalList[i].PatNum == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(Patients.GetLim(TerminalList[i].PatNum).GetNameLF());
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setSelected(selected,true);
        if (gridMain.getSelectedIndex() == -1 && gridMain.getRows().Count > 0)
        {
            gridMain.setSelected(0,true);
        }
         
    }

    private void fillPat() throws Exception {
        if (FormOpenDental.CurPatNum == 0)
        {
            labelPatient.Text = Lan.g(this,"none");
            labelSheets.Visible = false;
            listSheets.Visible = false;
            butLoad.Visible = false;
        }
        else
        {
            Patient pat = Patients.getLim(FormOpenDental.CurPatNum);
            labelPatient.Text = pat.getNameFL();
            labelSheets.Visible = true;
            listSheets.Visible = true;
            butLoad.Visible = true;
            listSheets.Items.Clear();
            List<Sheet> sheetList = Sheets.getForTerminal(FormOpenDental.CurPatNum);
            for (int i = 0;i < sheetList.Count;i++)
            {
                listSheets.Items.Add(sheetList[i].Description);
            }
        } 
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a terminal first.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"A terminal row should not be deleted unless it is showing erroneously and there really is no terminal running on the computer shown.  Continue anyway?"))
        {
            return ;
        }
         
        //string computerName=
        TerminalActives.DeleteAllForComputer(TerminalList[gridMain.getSelectedIndex()].ComputerName);
        fillGrid();
    }

    private void butClear_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a terminal first.");
            return ;
        }
         
        TerminalActive terminal = TerminalList[gridMain.getSelectedIndex()].Copy();
        if (terminal.PatNum == 0)
        {
            return ;
        }
         
        if (!MsgBox.show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?"))
        {
            return ;
        }
         
        terminal.PatNum = 0;
        TerminalActives.update(terminal);
        fillGrid();
    }

    private void butLoad_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a terminal first.");
            return ;
        }
         
        TerminalActive terminal = TerminalList[gridMain.getSelectedIndex()].Copy();
        if (terminal.PatNum != 0)
        {
            if (!MsgBox.show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?"))
            {
                return ;
            }
             
        }
         
        long patNum = FormOpenDental.CurPatNum;
        terminal.PatNum = patNum;
        TerminalActives.update(terminal);
        fillGrid();
    }

    /*
    			else {
    				//just load up a terminal on this computer in a modal window.  The terminal window itself will handle clearing it from the db when done.
    				TerminalActives.DeleteAllForComputer(Environment.MachineName);
    				TerminalActive terminal=new TerminalActive();
    				terminal.ComputerName=Environment.MachineName;
    				terminal.PatNum=FormOpenDental.CurPatNum;
    				TerminalActives.Insert(terminal);
    				//Still need to start the modal window
    			}*/
    /*
    		private void menuItemStandby_Click(object sender,EventArgs e) {
    			if(gridMain.GetSelectedIndex()==-1) {
    				MsgBox.Show(this,"Please select a terminal first.");
    				return;
    			}
    			TerminalActive terminal=TerminalList[gridMain.GetSelectedIndex()].Clone();
    			if(terminal.TerminalStatus==TerminalStatusEnum.Standby) {
    				//MsgBox.Show(this,"Please load a patient onto this terminal first.");
    				return;
    			}
    			if(!MsgBox.Show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?")) {
    				return;
    			}
    			terminal.PatNum=0;
    			terminal.TerminalStatus=TerminalStatusEnum.Standby;
    			TerminalActives.Update(terminal);
    			FillGrid();
    		}
    		private void menuItemPatientInfo_Click(object sender,EventArgs e) {
    			if(gridMain.GetSelectedIndex()==-1) {
    				MsgBox.Show(this,"Please select a terminal first.");
    				return;
    			}
    			TerminalActive terminal=TerminalList[gridMain.GetSelectedIndex()].Clone();
    			if(terminal.TerminalStatus==TerminalStatusEnum.Standby) {
    				MsgBox.Show(this,"Please load a patient onto this terminal first.");
    				return;
    			}
    			if(!MsgBox.Show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?")) {
    				return;
    			}
    			terminal.TerminalStatus=TerminalStatusEnum.PatientInfo;
    			TerminalActives.Update(terminal);
    			FillGrid();
    		}
    		private void menuItemMedical_Click(object sender,EventArgs e) {
    			if(gridMain.GetSelectedIndex()==-1) {
    				MsgBox.Show(this,"Please select a terminal first.");
    				return;
    			}
    			TerminalActive terminal=TerminalList[gridMain.GetSelectedIndex()].Clone();
    			if(terminal.TerminalStatus==TerminalStatusEnum.Standby) {
    				MsgBox.Show(this,"Please load a patient onto this terminal first.");
    				return;
    			}
    			//See if the selected patient already has diseases attached
    			Disease[] DiseaseList=Diseases.Refresh(terminal.PatNum);
    			if(DiseaseList.Length>0) {
    				MsgBox.Show(this,"This patient already has diseases attached.  This function is only intended for new patients.  Patient cannot be loaded.");
    				return;
    			}
    			//See if the selected patient already has questions attached
    			//if(Questions.PatHasQuest(terminal.PatNum)) {
    				//MsgBox.Show(this,"This patient already has questions attached.  This function is only intended for new patients.  Patient cannot be loaded.");
    				//return;
    			//}
    			if(!MsgBox.Show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?")) {
    				return;
    			}
    			terminal.TerminalStatus=TerminalStatusEnum.Medical;
    			TerminalActives.Update(terminal);
    			FillGrid();
    		}
    		private void menuItemUpdateOnly_Click(object sender,EventArgs e) {
    			if(gridMain.GetSelectedIndex()==-1) {
    				MsgBox.Show(this,"Please select a terminal first.");
    				return;
    			}
    			TerminalActive terminal=TerminalList[gridMain.GetSelectedIndex()].Clone();
    			if(terminal.TerminalStatus==TerminalStatusEnum.Standby) {
    				MsgBox.Show(this,"Please load a patient onto this terminal first.");
    				return;
    			}
    			if(!MsgBox.Show(this,true,"A patient is currently using the terminal.  If you continue, they will lose the information that is on their screen.  Continue anyway?")) {
    				return;
    			}
    			terminal.TerminalStatus=TerminalStatusEnum.UpdateOnly;
    			TerminalActives.Update(terminal);
    			FillGrid();
    		}*/
    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        //happens every 4 seconds to refresh list from db.
        fillGrid();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        if (Prefs.UpdateString(PrefName.TerminalClosePassword, textPassword.Text))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        MsgBox.show(this,"Done.");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


