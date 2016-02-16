//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEcwDiagAdv  extends Form 
{

    private String connString = new String();
    private String username = "ecwUser";
    private String password = "l69Rr4Rmj4CjiCTLxrIblg==";
    //encrypted
    private String server = new String();
    private String port = new String();
    private String command = new String();
    private String dummyConnString = new String();
    private Graphics g = new Graphics();
    DataTable queryResult = new DataTable();
    DataTable dbTables = new DataTable();
    private StringBuilder arbitraryStringName = new StringBuilder();
    public FormEcwDiagAdv() throws Exception {
        initializeComponent();
        server = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWServer");
        port = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWPort");
        buildConnectionString();
        Lan.F(this);
    }

    private void formEcwDiagAdv_Load(Object sender, EventArgs e) throws Exception {
        fillQueryList();
        server = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWServer");
        port = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"eCWPort");
        buildConnectionString();
        //although this does seem to cause a bug in Mono.  We will revisit this bug if needed to exclude the port option only for Mono.
        //ecwMaster;"
        //+"Connect Timeout=20;"
        dummyConnString = "Server=" + server + ";" + "Port=" + port + ";" + "Database=;" + "User ID=" + username + ";" + "Password=;" + "CharSet=utf8;" + "Treat Tiny As Boolean=false;" + "Allow User Variables=true;" + "Default Command Timeout=300;" + "Pooling=false";
        //no password information.
        //default is 30seconds
        //although this does seem to cause a bug in Mono.  We will revisit this bug if needed to exclude the port option only for Mono.
        //ecwMaster;"
        //+"Connect Timeout=20;"
        textConnString.Text = "Server=" + server + ";" + "Port=" + port + ";" + "Database=;" + "User ID=" + username + ";" + "Password=;" + "CharSet=utf8;" + "Treat Tiny As Boolean=false;" + "Allow User Variables=true;" + "Default Command Timeout=300;" + "Pooling=false";
            ;
        //no password information
        //default is 30seconds
        //textQuery.Text="SHOW VARIABLES;";
        //Show some relevent variables
        textQuery.Text = "SHOW VARIABLES " + "WHERE Variable_name IN " + "('basedir'," + " 'connect_timout'," + " 'datadir'," + " 'default_storage_engine'," + " 'general_log'," + " 'general_log_file'," + " 'hostname'," + " 'log_error'," + " 'pid_file'," + " 'port'," + " 'storage_engine'," + " 'tmpdir'," + " 'version'," + " 'version_compile_machine'," + " 'version_compile_os'" + ");";
        runQuery();
        fillTables();
    }

    private void fillQueryList() throws Exception {
        listQuery.Items.Add("SELECT * FROM itemkeys WHERE NAME IN ('pwd', 'user', 'FtpPath', 'Administrator', 'ClientVersion', 'upgrade_sqlver', 'ReconcilePatientFlag', 'ReconciliationPath', 'InterfaceID', 'GenericResultsPath', 'IsSIUOutboundConfigured', 'IsSIUOutboundVirtualTelConfigured', 'IsADTOutboundConfigured', 'IsADTWithOutHL7Interface', 'DentalEMRAppPath', 'EnableDentalEMR', 'isSaasPractice');");
        listQuery.Items.Add("SELECT * FROM mobiledoc.pmitemkeys WHERE name LIKE '%_Filter_for_%';/*look at 'value' column. Look for values other than 'no'*/");
        listQuery.Items.Add("SELECT * FROM mobiledoc.pmitemkeys WHERE value LIKE '%HL7%';/*used to show ecw paths to HL7 folders*/");
        listQuery.Items.Add("SELECT * FROM mobiledoc.hl7segment_details;/*Look for AIG or PV1 segment in the SIU messages.*/");
        listQuery.Items.Add("SELECT * FROM mobiledoc.hl7segment_groups;/*Look at group definitions.*/");
        listQuery.Items.Add("SELECT * FROM mobiledoc.itemkeys WHERE NAME IN ('pwd', 'user', 'FtpPath', 'Administrator', 'ClientVersion', 'upgrade_sqlver', 'ReconcilePatientFlag', 'ReconciliationPath', 'InterfaceID', 'GenericResultsPath', 'IsSIUOutboundConfigured', 'IsSIUOutboundVirtualTelConfigured', 'IsADTOutboundConfigured', 'IsADTWithOutHL7Interface', 'DentalEMRAppPath', 'EnableDentalEMR', 'isSaasPractice');/*General settings that might be useful.*/");
        listQuery.Items.Add("SELECT * FROM pmcodes;");
        listQuery.Items.Add("SELECT * FROM visitcodes ORDER by dentalvisit DESC;");
        listQuery.Items.Add("SELECT * FROM visitcodes LEFT OUTER JOIN pmcodes ON visitcodes.Name=pmcodes.ecwcode WHERE dentalvisit=1;");
    }

    //listQuery.Items.Add("");
    private void fillTables() throws Exception {
        try
        {
            dbTables = MySqlHelper.ExecuteDataset(connString, "SHOW TABLES").Tables[0];
        }
        catch (Exception ex)
        {
            MsgBox.Show(this, ex.Message);
        }

        gridTables.beginUpdate();
        gridTables.getColumns().Clear();
        ODGridColumn col;
        for (Object __dummyForeachVar1 : dbTables.Columns)
        {
            DataColumn colCur = (DataColumn)__dummyForeachVar1;
            List<int> colWidths = new List<int>();
            colWidths.Add(colCur.ColumnName.Length);
            for (Object __dummyForeachVar0 : dbTables.Rows)
            {
                DataRow dr = (DataRow)__dummyForeachVar0;
                colWidths.Add(dr[colCur.ColumnName].ToString().Length);
            }
            colWidths.Sort();
            //int colWidth=Math.Max(colCur.ColumnName.Length,queryResult.Rows[0][colCur.ColumnName].ToString().Length)*8;//8px per character based on the length of either column contents or col name.
            col = new ODGridColumn(colCur.ColumnName, (int)(colWidths[colWidths.Count - 1] * 5.5));
            //longest string * 5.8px
            gridTables.getColumns().add(col);
        }
        gridTables.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        int colCount = dbTables.Columns.Count;
        for (Object __dummyForeachVar2 : dbTables.Rows)
        {
            //to speed things up and not have to re-reference queryResults.Columns.Count for every row found.
            DataRow rowCur = (DataRow)__dummyForeachVar2;
            row = new OpenDental.UI.ODGridRow();
            for (int c = 0;c < colCount;c++)
            {
                row.getCells().Add(rowCur[c].ToString());
            }
            gridTables.getRows().add(row);
        }
        gridTables.endUpdate();
    }

    /**
    * Used to construct a default construction string.
    */
    private void buildConnectionString() throws Exception {
        if (!StringSupport.equals(textConnString.Text, dummyConnString) && !StringSupport.equals(textConnString.Text, ""))
        {
            //use the user provided connection string
            connString = textConnString.Text;
            return ;
        }
         
        //although this does seem to cause a bug in Mono.  We will revisit this bug if needed to exclude the port option only for Mono.
        //ecwMaster;"
        //+"Connect Timeout=20;"
        connString = "Server=" + server + ";" + "Port=" + port + ";" + "Database=mobiledoc;" + "User ID=" + username + ";" + "Password=" + decrypt(password) + ";" + "CharSet=utf8;" + "Treat Tiny As Boolean=false;" + "Allow User Variables=true;" + "Default Command Timeout=300;" + "Pooling=false";
    }

    //default is 30seconds
    private String decrypt(String encString) throws Exception {
        try
        {
            byte[] encrypted = Convert.FromBase64String(encString);
            MemoryStream ms = null;
            CryptoStream cs = null;
            StreamReader sr = null;
            Aes aes = new AesManaged();
            UTF8Encoding enc = new UTF8Encoding();
            aes.Key = enc.GetBytes("AKQjlLUjlcABVbqp");
            //Random string will be key
            aes.IV = new byte[16];
            ICryptoTransform decryptor = aes.CreateDecryptor(aes.Key, aes.IV);
            ms = new MemoryStream(encrypted);
            cs = new CryptoStream(ms, decryptor, CryptoStreamMode.Read);
            sr = new StreamReader(cs);
            String decrypted = sr.ReadToEnd();
            ms.Dispose();
            cs.Dispose();
            sr.Dispose();
            if (aes != null)
            {
                aes.Clear();
            }
             
            return decrypted;
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Text entered was not valid encrypted text.");
            return "";
        }
    
    }

    private void fillGrid() throws Exception {
        if (queryResult == null)
        {
            return ;
        }
         
        //DataTable Table=queryResult;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        for (Object __dummyForeachVar4 : queryResult.Columns)
        {
            DataColumn colCur = (DataColumn)__dummyForeachVar4;
            List<int> colWidths = new List<int>();
            colWidths.Add(5);
            //min width of 27px=(int)(5*5.5)
            colWidths.Add(colCur.ColumnName.Length + 5);
            for (Object __dummyForeachVar3 : queryResult.Rows)
            {
                DataRow dr = (DataRow)__dummyForeachVar3;
                colWidths.Add(dr[colCur.ColumnName].ToString().Length + 1);
            }
            colWidths.Sort();
            //int colWidth=Math.Max(colCur.ColumnName.Length,queryResult.Rows[0][colCur.ColumnName].ToString().Length)*8;//8px per character based on the length of either column contents or col name.
            col = new ODGridColumn(colCur.ColumnName, (int)(colWidths[colWidths.Count - 1] * 5.8));
            //longest string * 5.8px
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        int colCount = queryResult.Columns.Count;
        for (Object __dummyForeachVar5 : queryResult.Rows)
        {
            //to speed things up and not have to re-reference queryResults.Columns.Count for every row found.
            DataRow rowCur = (DataRow)__dummyForeachVar5;
            row = new OpenDental.UI.ODGridRow();
            for (int c = 0;c < colCount;c++)
            {
                row.getCells().Add(rowCur[c].ToString());
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        fillTables();
    }

    private void gridTables_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        char pressed = e.KeyChar;
        for (int i = 0;i < gridTables.getRows().Count;i++)
        {
            if (gridTables.getRows().get___idx(i).getCells()[0].Text[0] == pressed)
            {
                gridTables.scrollToEnd();
                float rowHeight = (float)gridTables.getScrollValue() / (float)gridTables.getRows().Count;
                gridTables.setScrollValue(i * (int)Math.Round(rowHeight, 0, MidpointRounding.AwayFromZero));
                return ;
            }
             
        }
    }

    /**
    * Double clicking on a table name will run "select * from" that table.
    */
    private void gridTables_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        Application.DoEvents();
        try
        {
            command = "SELECT * FROM " + gridTables.getRows().get___idx(e.getRow()).getCells()[0].Text + " ;";
            queryResult = MySqlHelper.ExecuteDataset(connString, command).Tables[0];
            fillGrid();
        }
        catch (Exception ex)
        {
            MsgBox.Show(this, ex.Message);
        }

        Cursor = Cursors.Default;
        Application.DoEvents();
    }

    private void butRunQ_Click(Object sender, EventArgs e) throws Exception {
        runQuery();
    }

    private void runQuery() throws Exception {
        Cursor = Cursors.WaitCursor;
        Application.DoEvents();
        try
        {
            buildConnectionString();
            command = textQuery.Text;
            queryResult = MySqlHelper.ExecuteDataset(connString, command).Tables[0];
            fillGrid();
        }
        catch (Exception ex)
        {
            MsgBox.Show(this, ex.Message);
        }

        Cursor = Cursors.Default;
        Application.DoEvents();
    }

    private void textQuery_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.F9)
        {
            runQuery();
        }
         
    }

    private void listQuery_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        textQuery.Text = listQuery.Items[listQuery.SelectedIndex].ToString();
        runQuery();
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
        this.textQuery = new System.Windows.Forms.TextBox();
        this.textConnString = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.gridTables = new OpenDental.UI.ODGrid();
        this.butRunQ = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.listQuery = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
        //
        // textQuery
        //
        this.textQuery.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textQuery.Location = new System.Drawing.Point(246, 58);
        this.textQuery.Multiline = true;
        this.textQuery.Name = "textQuery";
        this.textQuery.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textQuery.Size = new System.Drawing.Size(728, 99);
        this.textQuery.TabIndex = 3;
        this.textQuery.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textQuery_KeyDown);
        //
        // textConnString
        //
        this.textConnString.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textConnString.Location = new System.Drawing.Point(12, 12);
        this.textConnString.Multiline = true;
        this.textConnString.Name = "textConnString";
        this.textConnString.Size = new System.Drawing.Size(962, 40);
        this.textConnString.TabIndex = 4;
        //
        // gridMain
        //
        this.gridMain.setAllowSelection(false);
        this.gridMain.setAllowSortingByColumn(true);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(246, 163);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(728, 505);
        this.gridMain.TabIndex = 7;
        this.gridMain.setTitle("Query Results");
        this.gridMain.setTranslationName(null);
        //
        // gridTables
        //
        this.gridTables.setAllowSelection(false);
        this.gridTables.setAllowSortingByColumn(true);
        this.gridTables.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridTables.AutoScroll = true;
        this.gridTables.setHScrollVisible(true);
        this.gridTables.Location = new System.Drawing.Point(12, 163);
        this.gridTables.Name = "gridTables";
        this.gridTables.setScrollValue(0);
        this.gridTables.Size = new System.Drawing.Size(228, 505);
        this.gridTables.TabIndex = 8;
        this.gridTables.setTitle("Tables Available");
        this.gridTables.setTranslationName(null);
        this.gridTables.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridTables.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridTables_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridTables.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.gridTables_KeyPress);
        //
        // butRunQ
        //
        this.butRunQ.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRunQ.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRunQ.setAutosize(true);
        this.butRunQ.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRunQ.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRunQ.setCornerRadius(4F);
        this.butRunQ.Image = Resources.getbutGoto();
        this.butRunQ.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRunQ.Location = new System.Drawing.Point(986, 58);
        this.butRunQ.Name = "butRunQ";
        this.butRunQ.Size = new System.Drawing.Size(75, 24);
        this.butRunQ.TabIndex = 6;
        this.butRunQ.Text = "Run";
        this.butRunQ.Click += new System.EventHandler(this.butRunQ_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(986, 644);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // listQuery
        //
        this.listQuery.FormattingEnabled = true;
        this.listQuery.Location = new System.Drawing.Point(12, 58);
        this.listQuery.Name = "listQuery";
        this.listQuery.Size = new System.Drawing.Size(228, 95);
        this.listQuery.TabIndex = 9;
        this.listQuery.SelectedIndexChanged += new System.EventHandler(this.listQuery_SelectedIndexChanged);
        //
        // FormEcwDiagAdv
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1073, 680);
        this.Controls.Add(this.listQuery);
        this.Controls.Add(this.gridTables);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butRunQ);
        this.Controls.Add(this.textConnString);
        this.Controls.Add(this.textQuery);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEcwDiagAdv";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "eClinical Works Diagnostic";
        this.Load += new System.EventHandler(this.FormEcwDiagAdv_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textQuery = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textConnString = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butRunQ;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.ODGrid gridTables;
    private System.Windows.Forms.ListBox listQuery = new System.Windows.Forms.ListBox();
}


