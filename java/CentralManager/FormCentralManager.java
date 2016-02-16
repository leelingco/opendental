//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package CentralManager;

import CentralManager.FormCentralConnectionsSetup;
import CentralManager.FormCentralPasswordChange;
import CentralManager.FormCentralPasswordCheck;
import CS2JNet.System.StringSupport;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.Cache;
import OpenDentBusiness.CentralConnection;
import OpenDentBusiness.CentralConnections;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DataConnection;
import OpenDentBusiness.Db;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCentralManager  extends Form 
{

    public static byte[] EncryptionKey = new byte[]();
    private List<CentralConnection> ConnList = new List<CentralConnection>();
    private boolean IsStartingUp = new boolean();
    public FormCentralManager() throws Exception {
        initializeComponent();
        UTF8Encoding enc = new UTF8Encoding();
        EncryptionKey = enc.GetBytes("mQlEGebnokhGFEFV");
    }

    private void formCentralManager_Load(Object sender, EventArgs e) throws Exception {
        IsStartingUp = true;
        if (!getConfigAndConnect())
        {
            return ;
        }
         
        Cache.refresh(InvalidType.Prefs);
        Version storedVersion = new Version(PrefC.getString(PrefName.ProgramVersion));
        Version currentVersion = Assembly.GetAssembly(Db.class).GetName().Version;
        if (storedVersion.CompareTo(currentVersion) != 0)
        {
            MessageBox.Show("Program version: " + currentVersion.ToString() + "\r\n" + "Database version: " + storedVersion.ToString() + "\r\n" + "Versions must match.  Please manually connect to the database through the main program in order to update the version.");
            Application.Exit();
            return ;
        }
         
        if (!StringSupport.equals(PrefC.getString(PrefName.CentralManagerPassHash), ""))
        {
            FormCentralPasswordCheck formC = new FormCentralPasswordCheck();
            formC.ShowDialog();
            if (formC.DialogResult != DialogResult.OK)
            {
                Application.Exit();
                return ;
            }
             
        }
         
        fillGrid();
        IsStartingUp = false;
    }

    /**
    * Gets the settings from the config file and attempts to connect.
    */
    private boolean getConfigAndConnect() throws Exception {
        String xmlPath = Path.Combine(Application.StartupPath, "CentralManagerConfig.xml");
        if (!File.Exists(xmlPath))
        {
            MessageBox.Show("Please create CentralManagerConfig.xml according to the manual before using this tool.");
            Application.Exit();
            return false;
        }
         
        XmlDocument document = new XmlDocument();
        String computerName = "";
        String database = "";
        String user = "";
        String password = "";
        try
        {
            document.Load(xmlPath);
            XPathNavigator Navigator = document.CreateNavigator();
            XPathNavigator nav = new XPathNavigator();
            DataConnection.DBtype = DatabaseType.MySql;
            //See if there's a DatabaseConnection
            nav = Navigator.SelectSingleNode("//DatabaseConnection");
            if (nav == null)
            {
                MessageBox.Show("DatabaseConnection element missing from CentralManagerConfig.xml.");
                Application.Exit();
                return false;
            }
             
            computerName = nav.SelectSingleNode("ComputerName").Value;
            database = nav.SelectSingleNode("Database").Value;
            user = nav.SelectSingleNode("User").Value;
            password = nav.SelectSingleNode("Password").Value;
        }
        catch (Exception ex)
        {
            //Common error: root element is missing
            MessageBox.Show(ex.Message);
            Application.Exit();
            return false;
        }

        DataConnection.DBtype = DatabaseType.MySql;
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        try
        {
            //Try to connect to the database directly
            dcon.setDb(computerName,database,user,password,"","",OpenDentBusiness.DataConnection.DBtype);
            RemotingClient.RemotingRole = RemotingRole.ClientDirect;
            return true;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            Application.Exit();
            return false;
        }
    
    }

    private void butPassword_Click(Object sender, EventArgs e) throws Exception {
        FormCentralPasswordChange formC = new FormCentralPasswordChange();
        formC.ShowDialog();
    }

    private void butConSetup_Click(Object sender, EventArgs e) throws Exception {
        FormCentralConnectionsSetup formS = new FormCentralConnectionsSetup();
        formS.ShowDialog();
        fillGrid();
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        if (IsStartingUp)
        {
            return ;
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ConnList = CentralConnections.Refresh(textSearch.Text);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("#",40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Database",320);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < ConnList.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(ConnList[i].ItemOrder.ToString());
            if (StringSupport.equals(ConnList[i].DatabaseName, ""))
            {
                //uri
                row.getCells().Add(ConnList[i].ServiceURI);
            }
            else
            {
                row.getCells().Add(ConnList[i].ServerName + ", " + ConnList[i].DatabaseName);
            } 
            row.getCells().Add(ConnList[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        String args = "";
        if (!StringSupport.equals(ConnList[e.getRow()].DatabaseName, ""))
        {
            //ServerName=localhost DatabaseName=opendental MySqlUser=root MySqlPassword=
            args += "ServerName=\"" + ConnList[e.getRow()].ServerName + "\" " + "DatabaseName=\"" + ConnList[e.getRow()].DatabaseName + "\" " + "MySqlUser=\"" + ConnList[e.getRow()].MySqlUser + "\" ";
            if (!StringSupport.equals(ConnList[e.getRow()].MySqlPassword, ""))
            {
                args += "MySqlPassword=\"" + CentralConnections.Decrypt(ConnList[e.getRow()].MySqlPassword, EncryptionKey) + "\" ";
            }
             
        }
        else if (!StringSupport.equals(ConnList[e.getRow()].ServiceURI, ""))
        {
            args += "WebServiceURI=\"" + ConnList[e.getRow()].ServiceURI + "\" ";
            if (ConnList[e.getRow()].WebServiceIsEcw)
            {
                args += "WebServiceIsEcw=True ";
            }
             
        }
        else
        {
            MessageBox.Show("Either a database or a Middle Tier URI must be specified in the connection.");
            return ;
        }  
        //od username and password always allowed
        if (!StringSupport.equals(ConnList[e.getRow()].OdUser, ""))
        {
            args += "UserName=\"" + ConnList[e.getRow()].OdUser + "\" ";
        }
         
        if (!StringSupport.equals(ConnList[e.getRow()].OdPassword, ""))
        {
            args += "OdPassword=\"" + CentralConnections.Decrypt(ConnList[e.getRow()].OdPassword, EncryptionKey) + "\" ";
        }
         
        Process.Start("OpenDental.exe", args);
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
        this.butConSetup = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butPassword = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label2 = new System.Windows.Forms.Label();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butConSetup
        //
        this.butConSetup.Location = new System.Drawing.Point(101, 19);
        this.butConSetup.Name = "butConSetup";
        this.butConSetup.Size = new System.Drawing.Size(87, 24);
        this.butConSetup.TabIndex = 0;
        this.butConSetup.Text = "Connections";
        this.butConSetup.UseVisualStyleBackColor = true;
        this.butConSetup.Click += new System.EventHandler(this.butConSetup_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butPassword);
        this.groupBox1.Controls.Add(this.butConSetup);
        this.groupBox1.Location = new System.Drawing.Point(11, 10);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(204, 51);
        this.groupBox1.TabIndex = 6;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Setup";
        //
        // butPassword
        //
        this.butPassword.Location = new System.Drawing.Point(15, 19);
        this.butPassword.Name = "butPassword";
        this.butPassword.Size = new System.Drawing.Size(78, 24);
        this.butPassword.TabIndex = 1;
        this.butPassword.Text = "Password";
        this.butPassword.UseVisualStyleBackColor = true;
        this.butPassword.Click += new System.EventHandler(this.butPassword_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(11, 72);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(760, 623);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Connections - double click to launch");
        this.gridMain.setTranslationName("");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(237, 35);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 17);
        this.label2.TabIndex = 212;
        this.label2.Text = "Search";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(351, 32);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(190, 20);
        this.textSearch.TabIndex = 211;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // FormCentralManager
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(784, 707);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Name = "FormCentralManager";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Central Manager";
        this.Load += new System.EventHandler(this.FormCentralManager_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butConSetup = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butPassword = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSearch = new System.Windows.Forms.TextBox();
}


