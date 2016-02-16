//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormReplicationEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ReplicationServer;
import OpenDentBusiness.ReplicationServers;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReplicationSetup;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormReplicationSetup  extends Form 
{

    private boolean changed = new boolean();
    public FormReplicationSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
        changed = false;
    }

    private void formReplicationSetup_Load(Object sender, EventArgs e) throws Exception {
        checkRandomPrimaryKeys.Checked = PrefC.getBool(PrefName.RandomPrimaryKeys);
        if (checkRandomPrimaryKeys.Checked)
        {
            //not allowed to uncheck it
            checkRandomPrimaryKeys.Enabled = false;
        }
         
        if (PrefC.getInt(PrefName.ReplicationFailureAtServer_id) > 0)
        {
            groupBoxReplicationFailure.Visible = true;
            textReplicaitonFailureAtServer_id.Text = PrefC.getInt(PrefName.ReplicationFailureAtServer_id).ToString();
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ReplicationServers.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormReplicationSetup","Description"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","server_id"),65);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","Key Range Start"),160);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","Key Range End"),160);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","AtoZ Path"),160);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","UpdateBlocked"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormReplicationSetup","SlaveMonitor"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ReplicationServers.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ReplicationServers.getListt()[i].Descript);
            row.getCells().Add(ReplicationServers.getListt()[i].ServerId.ToString());
            row.getCells().Add(ReplicationServers.getListt()[i].RangeStart.ToString("n0"));
            row.getCells().Add(ReplicationServers.getListt()[i].RangeEnd.ToString("n0"));
            row.getCells().Add(ReplicationServers.getListt()[i].AtoZpath);
            row.getCells().add(ReplicationServers.getListt()[i].UpdateBlocked ? "X" : "");
            row.getCells().Add(ReplicationServers.getListt()[i].SlaveMonitor.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void checkRandomPrimaryKeys_Click(Object sender, System.EventArgs e) throws Exception {
        if (checkRandomPrimaryKeys.Checked)
        {
            if (MessageBox.Show("Are you absolutely sure you want to enable random primary keys?\r\n" + "Advantages:\r\n" + "Multiple servers can stay synchronized using merge replication.\r\n" + "Realtime connection between servers not required.\r\n" + "Data can be entered on all servers and synchronized later.\r\n" + "Disadvantages:\r\n" + "Slightly slower.\r\n" + "Difficult to set up.\r\n" + "Primary keys much longer, so not as user friendly.", "", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
            {
                checkRandomPrimaryKeys.Checked = false;
                return ;
            }
             
            //immediately make the change
            Prefs.updateBool(PrefName.RandomPrimaryKeys,true);
            DataValid.setInvalid(InvalidType.Prefs);
        }
        else
        {
            //user just unchecked the box
            //this would only happen if the user had just enabled and then changed their mind
            //usually, the checkbox is disabled to prevent changing back
            Prefs.updateBool(PrefName.RandomPrimaryKeys,false);
            DataValid.setInvalid(InvalidType.Prefs);
        } 
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormReplicationEdit FormR = new FormReplicationEdit();
        FormR.RepServ = ReplicationServers.getListt()[e.getRow()];
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormReplicationEdit FormR = new FormReplicationEdit();
        FormR.RepServ = new ReplicationServer();
        FormR.RepServ.setIsNew(true);
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillGrid();
    }

    private void butSetRanges_Click(Object sender, EventArgs e) throws Exception {
        if (ReplicationServers.getListt().Count == 0)
        {
            MessageBox.Show(Lan.g(this,"Please add at least one replication server to the list first"));
            return ;
        }
         
        //long serverCount=ReplicationServers.Listt.Count;
        long offset = 10000;
        long span = (long.MaxValue - offset) / (long)ReplicationServers.getListt().Count;
        //rounds down
        long counter = offset;
        for (int i = 0;i < ReplicationServers.getListt().Count;i++)
        {
            ReplicationServers.getListt()[i].RangeStart = counter;
            counter += span - 1;
            if (i == ReplicationServers.getListt().Count - 1)
            {
                ReplicationServers.getListt()[i].RangeEnd = long.MaxValue;
            }
            else
            {
                ReplicationServers.getListt()[i].RangeEnd = counter;
                counter += 1;
            } 
            ReplicationServers.Update(ReplicationServers.getListt()[i]);
        }
        changed = true;
        fillGrid();
    }

    private void butTest_Click(Object sender, EventArgs e) throws Exception {
        int server_id = ReplicationServers.getServer_id();
        String msg = "";
        if (server_id == 0)
        {
            msg = "server_id not set for this server.\r\n\r\n";
        }
        else
        {
            msg = "server_id = " + server_id.ToString() + "\r\n\r\n";
        } 
        msg += "Sample generated keys:";
        long key = new long();
        List<long> longlist = new List<long>();
        for (int i = 0;i < 15;i++)
        {
            do
            {
                key = ReplicationServers.getKey("patient","PatNum");
            }
            while (longlist.Contains(key));
            //unfortunately this "random" key is based on time, so we need to ensure that the result set is unique.
            //I think it takes one millisecond to get each key this way.
            longlist.Add(key);
            msg += "\r\n" + key.ToString("n0");
        }
        MessageBox.Show(msg);
    }

    private void butSynch_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textUsername.Text, ""))
        {
            MsgBox.show(this,"Please enter a username first.");
            return ;
        }
         
        if (ReplicationServers.getListt().Count == 0)
        {
            MsgBox.show(this,"Please add at servers to the list first");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        String currentDatabaseName = MiscData.getCurrentDatabase();
        for (int i = 0;i < ReplicationServers.getListt().Count;i++)
        {
            String compName = ReplicationServers.getListt()[i].Descript;
            OpenDentBusiness.DataConnection dc = new OpenDentBusiness.DataConnection();
            try
            {
                //try {
                dc.SetDb(compName, currentDatabaseName, textUsername.Text, textPassword.Text, "", "", OpenDentBusiness.DataConnection.DBtype);
                //}
                //catch(MySql.Data.MySqlClient.MySqlException ex) {
                //	if(ex.Number==1042) {//The error 1042 is issued when the connection could not be made.
                //		throw ex;//Pass the exception along.
                //	}
                //	DataConnection.cmd.Connection.Close();
                //}
                //Connection is considered to be successfull at this point. Now restart the slave process to force replication.
                String command = "SLAVE STOP; START SLAVE; SHOW SLAVE STATUS;";
                DataTable slaveStatus = dc.getTable(command);
                for (int j = 0;j < 40 && !StringSupport.equals(slaveStatus.Rows[0]["Slave_IO_Running"].ToString().ToLower(), "yes");j++)
                {
                    //Wait for the slave process to become active again.
                    Thread.Sleep(1000);
                    command = "SHOW SLAVE STATUS";
                    slaveStatus = dc.getTable(command);
                }
                if (!StringSupport.equals(slaveStatus.Rows[0]["Slave_IO_Running"].ToString().ToLower(), "yes"))
                {
                    throw new Exception("Slave IO is not running on computer " + compName);
                }
                 
                if (!StringSupport.equals(slaveStatus.Rows[0]["Slave_SQL_Running"].ToString().ToLower(), "yes"))
                {
                    throw new Exception("Slave SQL is not running on computer " + compName);
                }
                 
                while (!StringSupport.equals(slaveStatus.Rows[0]["Slave_IO_State"].ToString().ToLower(), "waiting for master to send event") || !StringSupport.equals(slaveStatus.Rows[0]["Seconds_Behind_Master"].ToString(), "0"))
                {
                    //Wait for replication to complete.
                    slaveStatus = dc.getTable(command);
                }
            }
            catch (Exception ex)
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error forcing replication on computer") + " " + compName + ": " + ex.Message);
                return ;
            }
        
        }
        //Cancel operation.
        Cursor = Cursors.Default;
        MessageBox.Show(Lan.g(this,"Database synch completed successfully."));
    }

    private void butResetReplicationFailureAtServer_id_Click(Object sender, EventArgs e) throws Exception {
        Prefs.updateInt(PrefName.ReplicationFailureAtServer_id,0);
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void formReplicationSetup_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ReplicationServers);
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReplicationSetup.class);
        this.checkRandomPrimaryKeys = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textUsername = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butSynch = new OpenDental.UI.Button();
        this.butTest = new OpenDental.UI.Button();
        this.butSetRanges = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.groupBoxReplicationFailure = new System.Windows.Forms.GroupBox();
        this.butClearReplicationFailureAtServer_id = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.textReplicaitonFailureAtServer_id = new System.Windows.Forms.TextBox();
        this.groupBox1.SuspendLayout();
        this.groupBoxReplicationFailure.SuspendLayout();
        this.SuspendLayout();
        //
        // checkRandomPrimaryKeys
        //
        this.checkRandomPrimaryKeys.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkRandomPrimaryKeys.Location = new System.Drawing.Point(17, 15);
        this.checkRandomPrimaryKeys.Name = "checkRandomPrimaryKeys";
        this.checkRandomPrimaryKeys.Size = new System.Drawing.Size(346, 17);
        this.checkRandomPrimaryKeys.TabIndex = 56;
        this.checkRandomPrimaryKeys.Text = "Use Random Primary Keys (BE VERY CAREFUL.  IRREVERSIBLE)";
        this.checkRandomPrimaryKeys.Click += new System.EventHandler(this.checkRandomPrimaryKeys_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(113, 510);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(550, 35);
        this.label1.TabIndex = 61;
        this.label1.Text = resources.GetString("label1.Text");
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(113, 552);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(431, 35);
        this.label2.TabIndex = 63;
        this.label2.Text = "Use the Test button to generate and display some sample key values for this compu" + "ter.  The displayed values should fall within the range set above.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(103, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(431, 45);
        this.label3.TabIndex = 65;
        this.label3.Text = resources.GetString("label3.Text");
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textPassword);
        this.groupBox1.Controls.Add(this.label5);
        this.groupBox1.Controls.Add(this.textUsername);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.butSynch);
        this.groupBox1.Location = new System.Drawing.Point(10, 591);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(771, 66);
        this.groupBox1.TabIndex = 66;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Force Synch";
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(641, 40);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(125, 20);
        this.textPassword.TabIndex = 69;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(530, 40);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(111, 18);
        this.label5.TabIndex = 68;
        this.label5.Text = "Password";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUsername
        //
        this.textUsername.Location = new System.Drawing.Point(641, 18);
        this.textUsername.Name = "textUsername";
        this.textUsername.Size = new System.Drawing.Size(125, 20);
        this.textUsername.TabIndex = 67;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(530, 18);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 18);
        this.label4.TabIndex = 66;
        this.label4.Text = "MySQL Username";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSynch
        //
        this.butSynch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSynch.setAutosize(true);
        this.butSynch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSynch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSynch.setCornerRadius(4F);
        this.butSynch.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSynch.Location = new System.Drawing.Point(7, 26);
        this.butSynch.Name = "butSynch";
        this.butSynch.Size = new System.Drawing.Size(90, 24);
        this.butSynch.TabIndex = 64;
        this.butSynch.Text = "Synch";
        this.butSynch.Click += new System.EventHandler(this.butSynch_Click);
        //
        // butTest
        //
        this.butTest.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTest.setAutosize(true);
        this.butTest.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTest.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTest.setCornerRadius(4F);
        this.butTest.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butTest.Location = new System.Drawing.Point(17, 557);
        this.butTest.Name = "butTest";
        this.butTest.Size = new System.Drawing.Size(90, 24);
        this.butTest.TabIndex = 62;
        this.butTest.Text = "Test";
        this.butTest.Click += new System.EventHandler(this.butTest_Click);
        //
        // butSetRanges
        //
        this.butSetRanges.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetRanges.setAutosize(true);
        this.butSetRanges.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetRanges.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetRanges.setCornerRadius(4F);
        this.butSetRanges.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSetRanges.Location = new System.Drawing.Point(17, 516);
        this.butSetRanges.Name = "butSetRanges";
        this.butSetRanges.Size = new System.Drawing.Size(90, 24);
        this.butSetRanges.TabIndex = 59;
        this.butSetRanges.Text = "Set Ranges";
        this.butSetRanges.Click += new System.EventHandler(this.butSetRanges_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(798, 470);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 58;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(17, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(764, 452);
        this.gridMain.TabIndex = 57;
        this.gridMain.setTitle("Servers");
        this.gridMain.setTranslationName("FormReplicationSetup");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(798, 630);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // groupBoxReplicationFailure
        //
        this.groupBoxReplicationFailure.Controls.Add(this.butClearReplicationFailureAtServer_id);
        this.groupBoxReplicationFailure.Controls.Add(this.label6);
        this.groupBoxReplicationFailure.Controls.Add(this.textReplicaitonFailureAtServer_id);
        this.groupBoxReplicationFailure.ForeColor = System.Drawing.Color.Red;
        this.groupBoxReplicationFailure.Location = new System.Drawing.Point(550, 537);
        this.groupBoxReplicationFailure.Name = "groupBoxReplicationFailure";
        this.groupBoxReplicationFailure.Size = new System.Drawing.Size(323, 50);
        this.groupBoxReplicationFailure.TabIndex = 67;
        this.groupBoxReplicationFailure.TabStop = false;
        this.groupBoxReplicationFailure.Text = "Replication Failure Detected";
        this.groupBoxReplicationFailure.Visible = false;
        //
        // butClearReplicationFailureAtServer_id
        //
        this.butClearReplicationFailureAtServer_id.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearReplicationFailureAtServer_id.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClearReplicationFailureAtServer_id.setAutosize(true);
        this.butClearReplicationFailureAtServer_id.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearReplicationFailureAtServer_id.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearReplicationFailureAtServer_id.setCornerRadius(4F);
        this.butClearReplicationFailureAtServer_id.ForeColor = System.Drawing.SystemColors.ControlText;
        this.butClearReplicationFailureAtServer_id.Location = new System.Drawing.Point(248, 19);
        this.butClearReplicationFailureAtServer_id.Name = "butClearReplicationFailureAtServer_id";
        this.butClearReplicationFailureAtServer_id.Size = new System.Drawing.Size(69, 24);
        this.butClearReplicationFailureAtServer_id.TabIndex = 2;
        this.butClearReplicationFailureAtServer_id.Text = "Clear";
        this.butClearReplicationFailureAtServer_id.Click += new System.EventHandler(this.butResetReplicationFailureAtServer_id_Click);
        //
        // label6
        //
        this.label6.ForeColor = System.Drawing.SystemColors.ControlText;
        this.label6.Location = new System.Drawing.Point(6, 22);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(155, 18);
        this.label6.TabIndex = 66;
        this.label6.Text = "Replication Failure at Server_id";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textReplicaitonFailureAtServer_id
        //
        this.textReplicaitonFailureAtServer_id.Location = new System.Drawing.Point(167, 22);
        this.textReplicaitonFailureAtServer_id.Name = "textReplicaitonFailureAtServer_id";
        this.textReplicaitonFailureAtServer_id.ReadOnly = true;
        this.textReplicaitonFailureAtServer_id.Size = new System.Drawing.Size(75, 20);
        this.textReplicaitonFailureAtServer_id.TabIndex = 67;
        //
        // FormReplicationSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(885, 668);
        this.Controls.Add(this.groupBoxReplicationFailure);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butTest);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butSetRanges);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.checkRandomPrimaryKeys);
        this.Controls.Add(this.butClose);
        this.Name = "FormReplicationSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Replication Setup";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormReplicationSetup_FormClosing);
        this.Load += new System.EventHandler(this.FormReplicationSetup_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBoxReplicationFailure.ResumeLayout(false);
        this.groupBoxReplicationFailure.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.CheckBox checkRandomPrimaryKeys = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butSetRanges;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butTest;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSynch;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUsername = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBoxReplicationFailure = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butClearReplicationFailureAtServer_id;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReplicaitonFailureAtServer_id = new System.Windows.Forms.TextBox();
}


