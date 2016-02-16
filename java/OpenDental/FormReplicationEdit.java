//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.ReplicationServer;
import OpenDentBusiness.ReplicationServers;
import OpenDental.Properties.Resources;

public class FormReplicationEdit  extends Form 
{

    public ReplicationServer RepServ;
    public FormReplicationEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReplicationEdit_Load(Object sender, EventArgs e) throws Exception {
        textDescript.Text = RepServ.Descript;
        textServerId.Text = RepServ.ServerId.ToString();
        if (RepServ.RangeStart != 0)
        {
            textRangeStart.Text = RepServ.RangeStart.ToString();
        }
         
        if (RepServ.RangeEnd != 0)
        {
            textRangeEnd.Text = RepServ.RangeEnd.ToString();
        }
         
        textAtoZpath.Text = RepServ.AtoZpath;
        checkUpdateBlocked.Checked = RepServ.UpdateBlocked;
        textSlaveMonitor.Text = RepServ.SlaveMonitor;
    }

    private void butThisComputer_Click(Object sender, EventArgs e) throws Exception {
        textSlaveMonitor.Text = Dns.GetHostName();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (RepServ.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        ReplicationServers.deleteObject(RepServ.ReplicationServerNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textDescript.Text, ""))
        {
        }
         
        //I guess we don't need to force descript to have a value
        if (!StringSupport.equals(textServerId.errorProvider1.GetError(textServerId), ""))
        {
            MsgBox.show(this,"Please fix server_id.");
            return ;
        }
         
        int serverid = PIn.Int(textServerId.Text);
        if (serverid == 0)
        {
            MsgBox.show(this,"Please enter a server_id number greater than zero.");
            return ;
        }
         
        long rangeStart = 0;
        if (!StringSupport.equals(textRangeStart.Text, ""))
        {
            try
            {
                rangeStart = long.Parse(textRangeStart.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Please fix range start.");
                return ;
            }
        
        }
         
        long rangeEnd = 0;
        if (!StringSupport.equals(textRangeEnd.Text, ""))
        {
            try
            {
                rangeEnd = long.Parse(textRangeEnd.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"Please fix range end.");
                return ;
            }
        
        }
         
        if ((!StringSupport.equals(textRangeStart.Text, "") || !StringSupport.equals(textRangeEnd.Text, "")) && rangeEnd - rangeStart < 999999)
        {
            MsgBox.show(this,"The end of the range must be at least 999,999 greater than the start of the range.");
            return ;
        }
         
        RepServ.Descript = textDescript.Text;
        RepServ.ServerId = serverid;
        //will be valid and greater than 0.
        RepServ.RangeStart = rangeStart;
        RepServ.RangeEnd = rangeEnd;
        RepServ.AtoZpath = textAtoZpath.Text;
        RepServ.UpdateBlocked = checkUpdateBlocked.Checked;
        RepServ.SlaveMonitor = textSlaveMonitor.Text;
        if (RepServ.getIsNew())
        {
            ReplicationServers.insert(RepServ);
        }
        else
        {
            ReplicationServers.update(RepServ);
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
        this.label1 = new System.Windows.Forms.Label();
        this.textDescript = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textRangeStart = new System.Windows.Forms.TextBox();
        this.textRangeEnd = new System.Windows.Forms.TextBox();
        this.textAtoZpath = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.checkUpdateBlocked = new System.Windows.Forms.CheckBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.textServerId = new OpenDental.ValidNum();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textSlaveMonitor = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.butThisComputer = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(340, 49);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(327, 48);
        this.label1.TabIndex = 60;
        this.label1.Text = "this also needs to be set in the my.ini file on each server.  If the my.ini file " + "gets changed, be sure to restart the server and each workstation client.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textDescript
        //
        this.textDescript.Location = new System.Drawing.Point(238, 22);
        this.textDescript.Name = "textDescript";
        this.textDescript.Size = new System.Drawing.Size(318, 20);
        this.textDescript.TabIndex = 61;
        this.textDescript.WordWrap = false;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(10, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(226, 18);
        this.label2.TabIndex = 62;
        this.label2.Text = "Server Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(10, 64);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(226, 18);
        this.label3.TabIndex = 63;
        this.label3.Text = "server_id";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(10, 107);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(226, 18);
        this.label4.TabIndex = 65;
        this.label4.Text = "Range Start";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(10, 137);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(226, 18);
        this.label5.TabIndex = 67;
        this.label5.Text = "Range End";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRangeStart
        //
        this.textRangeStart.Location = new System.Drawing.Point(238, 107);
        this.textRangeStart.Name = "textRangeStart";
        this.textRangeStart.Size = new System.Drawing.Size(175, 20);
        this.textRangeStart.TabIndex = 68;
        this.textRangeStart.WordWrap = false;
        //
        // textRangeEnd
        //
        this.textRangeEnd.Location = new System.Drawing.Point(238, 137);
        this.textRangeEnd.Name = "textRangeEnd";
        this.textRangeEnd.Size = new System.Drawing.Size(175, 20);
        this.textRangeEnd.TabIndex = 69;
        this.textRangeEnd.WordWrap = false;
        //
        // textAtoZpath
        //
        this.textAtoZpath.Location = new System.Drawing.Point(238, 168);
        this.textAtoZpath.Name = "textAtoZpath";
        this.textAtoZpath.Size = new System.Drawing.Size(388, 20);
        this.textAtoZpath.TabIndex = 72;
        this.textAtoZpath.WordWrap = false;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(10, 168);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(226, 18);
        this.label6.TabIndex = 71;
        this.label6.Text = "A to Z images folder";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkUpdateBlocked
        //
        this.checkUpdateBlocked.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkUpdateBlocked.Location = new System.Drawing.Point(16, 200);
        this.checkUpdateBlocked.Name = "checkUpdateBlocked";
        this.checkUpdateBlocked.Size = new System.Drawing.Size(236, 18);
        this.checkUpdateBlocked.TabIndex = 99;
        this.checkUpdateBlocked.Text = "Update Blocked";
        this.checkUpdateBlocked.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkUpdateBlocked.UseVisualStyleBackColor = true;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(258, 200);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(368, 48);
        this.label7.TabIndex = 100;
        this.label7.Text = "Use this option carefully. It really will block the ability of the server to upda" + "te database versions, and it\'s possible that this could prevent startup of the p" + "rogram in certain situations.";
        //
        // label8
        //
        this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label8.Location = new System.Drawing.Point(418, 141);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(258, 13);
        this.label8.TabIndex = 101;
        this.label8.Text = "Range must be at least 1,000,000 numbers.";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(24, 333);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(86, 24);
        this.butDelete.TabIndex = 70;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textServerId
        //
        this.textServerId.Location = new System.Drawing.Point(238, 64);
        this.textServerId.setMaxVal(2000000000);
        this.textServerId.setMinVal(0);
        this.textServerId.Name = "textServerId";
        this.textServerId.Size = new System.Drawing.Size(100, 20);
        this.textServerId.TabIndex = 64;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(488, 333);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(579, 333);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textSlaveMonitor
        //
        this.textSlaveMonitor.Location = new System.Drawing.Point(238, 251);
        this.textSlaveMonitor.Name = "textSlaveMonitor";
        this.textSlaveMonitor.Size = new System.Drawing.Size(306, 20);
        this.textSlaveMonitor.TabIndex = 103;
        this.textSlaveMonitor.WordWrap = false;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(10, 251);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(226, 18);
        this.label9.TabIndex = 102;
        this.label9.Text = "Slave Monitor";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(238, 274);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(306, 41);
        this.label10.TabIndex = 100;
        this.label10.Text = "Enter the name of the computer that will constantly monitor the health of the rep" + "lication process. This machine should stay on at all times.";
        //
        // butThisComputer
        //
        this.butThisComputer.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butThisComputer.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butThisComputer.setAutosize(true);
        this.butThisComputer.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butThisComputer.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butThisComputer.setCornerRadius(4F);
        this.butThisComputer.Location = new System.Drawing.Point(550, 251);
        this.butThisComputer.Name = "butThisComputer";
        this.butThisComputer.Size = new System.Drawing.Size(87, 24);
        this.butThisComputer.TabIndex = 3;
        this.butThisComputer.Text = "This Computer";
        this.butThisComputer.Click += new System.EventHandler(this.butThisComputer_Click);
        //
        // FormReplicationEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(678, 372);
        this.Controls.Add(this.textSlaveMonitor);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.checkUpdateBlocked);
        this.Controls.Add(this.textAtoZpath);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textRangeEnd);
        this.Controls.Add(this.textRangeStart);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textServerId);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDescript);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butThisComputer);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormReplicationEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Replication Server";
        this.Load += new System.EventHandler(this.FormReplicationEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescript = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textServerId;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textRangeStart = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textRangeEnd = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textAtoZpath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkUpdateBlocked = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSlaveMonitor = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butThisComputer;
}


