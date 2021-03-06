//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:18 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RdlDesign.DataSourceValues;
import fyiReporting.RdlDesign.DesignerUtility;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogExprEditor;
import fyiReporting.RdlDesign.ReportNames;

/**
* Summary description for DialogDataSourceRef.
*/
public class DialogDataSources  extends System.Windows.Forms.Form 
{
    DesignXmlDraw _Draw;
    private System.Windows.Forms.TextBox tbFilename = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bGetFilename = new System.Windows.Forms.Button();
    private System.Windows.Forms.ComboBox cbDataProvider = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox tbConnection = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox ckbIntSecurity = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox tbPrompt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bTestConnection = new System.Windows.Forms.Button();
    private System.Windows.Forms.ListBox lbDataSources = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Button bRemove = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.CheckBox chkSharedDataSource = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lDataProvider = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lConnectionString = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lPrompt = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbDSName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bExprConnect = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogDataSources(DesignXmlDraw draw) throws Exception {
        _Draw = draw;
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        initValues();
    }

    private void initValues() throws Exception {
        // Populate the DataProviders
        cbDataProvider.Items.Clear();
        String[] items = RdlEngineConfig.getProviders();
        cbDataProvider.Items.AddRange(items);
        //
        // Obtain the existing DataSets info
        //
        XmlNode rNode = _Draw.getReportNode();
        XmlNode dsNode = _Draw.getNamedChildNode(rNode,"DataSources");
        if (dsNode == null)
            return ;
         
        for (Object __dummyForeachVar0 : dsNode)
        {
            XmlNode dNode = (XmlNode)__dummyForeachVar0;
            if (!StringSupport.equals(dNode.Name, "DataSource"))
                continue;
             
            XmlAttribute nAttr = dNode.Attributes["Name"];
            if (nAttr == null)
                continue;
             
            // shouldn't really happen
            DataSourceValues dsv = new DataSourceValues(nAttr.Value);
            dsv.setNode(dNode);
            dsv.setDataSourceReference(_Draw.getElementValue(dNode,"DataSourceReference",null));
            if (dsv.getDataSourceReference() == null)
            {
                // this is not a data source reference
                dsv.setbDataSourceReference(false);
                dsv.setDataSourceReference("");
                XmlNode cpNode = DesignXmlDraw.findNextInHierarchy(dNode,"ConnectionProperties","ConnectString");
                dsv.setConnectionString(cpNode == null ? "" : cpNode.InnerText);
                XmlNode datap = DesignXmlDraw.findNextInHierarchy(dNode,"ConnectionProperties","DataProvider");
                dsv.setDataProvider(datap == null ? "" : datap.InnerText);
                XmlNode p = DesignXmlDraw.findNextInHierarchy(dNode,"ConnectionProperties","Prompt");
                dsv.setPrompt(p == null ? "" : p.InnerText);
            }
            else
            {
                // we have a data source reference
                dsv.setbDataSourceReference(true);
                dsv.setConnectionString("");
                dsv.setDataProvider("");
                dsv.setPrompt("");
            } 
            this.lbDataSources.Items.Add(dsv);
        }
        if (lbDataSources.Items.Count > 0)
            lbDataSources.SelectedIndex = 0;
        else
            this.bOK.Enabled = false; 
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
        this.tbFilename = new System.Windows.Forms.TextBox();
        this.bGetFilename = new System.Windows.Forms.Button();
        this.lDataProvider = new System.Windows.Forms.Label();
        this.cbDataProvider = new System.Windows.Forms.ComboBox();
        this.lConnectionString = new System.Windows.Forms.Label();
        this.tbConnection = new System.Windows.Forms.TextBox();
        this.ckbIntSecurity = new System.Windows.Forms.CheckBox();
        this.lPrompt = new System.Windows.Forms.Label();
        this.tbPrompt = new System.Windows.Forms.TextBox();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.bTestConnection = new System.Windows.Forms.Button();
        this.lbDataSources = new System.Windows.Forms.ListBox();
        this.bRemove = new System.Windows.Forms.Button();
        this.bAdd = new System.Windows.Forms.Button();
        this.chkSharedDataSource = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.tbDSName = new System.Windows.Forms.TextBox();
        this.bExprConnect = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // tbFilename
        //
        this.tbFilename.Location = new System.Drawing.Point(192, 112);
        this.tbFilename.Name = "tbFilename";
        this.tbFilename.Size = new System.Drawing.Size(216, 20);
        this.tbFilename.TabIndex = 2;
        this.tbFilename.Text = "";
        this.tbFilename.TextChanged += new System.EventHandler(this.tbFilename_TextChanged);
        //
        // bGetFilename
        //
        this.bGetFilename.Location = new System.Drawing.Point(424, 112);
        this.bGetFilename.Name = "bGetFilename";
        this.bGetFilename.Size = new System.Drawing.Size(24, 23);
        this.bGetFilename.TabIndex = 3;
        this.bGetFilename.Text = "...";
        this.bGetFilename.Click += new System.EventHandler(this.bGetFilename_Click);
        //
        // lDataProvider
        //
        this.lDataProvider.Location = new System.Drawing.Point(8, 152);
        this.lDataProvider.Name = "lDataProvider";
        this.lDataProvider.Size = new System.Drawing.Size(80, 23);
        this.lDataProvider.TabIndex = 7;
        this.lDataProvider.Text = "Data provider";
        //
        // cbDataProvider
        //
        this.cbDataProvider.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataProvider.Items.AddRange(new Object[]{ "SQL", "ODBC", "OLEDB" });
        this.cbDataProvider.Location = new System.Drawing.Point(96, 152);
        this.cbDataProvider.Name = "cbDataProvider";
        this.cbDataProvider.Size = new System.Drawing.Size(144, 21);
        this.cbDataProvider.TabIndex = 4;
        this.cbDataProvider.SelectedIndexChanged += new System.EventHandler(this.cbDataProvider_SelectedIndexChanged);
        //
        // lConnectionString
        //
        this.lConnectionString.Location = new System.Drawing.Point(8, 192);
        this.lConnectionString.Name = "lConnectionString";
        this.lConnectionString.Size = new System.Drawing.Size(100, 16);
        this.lConnectionString.TabIndex = 10;
        this.lConnectionString.Text = "Connection string";
        //
        // tbConnection
        //
        this.tbConnection.Location = new System.Drawing.Point(16, 216);
        this.tbConnection.Multiline = true;
        this.tbConnection.Name = "tbConnection";
        this.tbConnection.Size = new System.Drawing.Size(424, 40);
        this.tbConnection.TabIndex = 6;
        this.tbConnection.Text = "";
        this.tbConnection.TextChanged += new System.EventHandler(this.tbConnection_TextChanged);
        //
        // ckbIntSecurity
        //
        this.ckbIntSecurity.Location = new System.Drawing.Point(280, 152);
        this.ckbIntSecurity.Name = "ckbIntSecurity";
        this.ckbIntSecurity.Size = new System.Drawing.Size(144, 24);
        this.ckbIntSecurity.TabIndex = 5;
        this.ckbIntSecurity.Text = "Use integrated security";
        this.ckbIntSecurity.CheckedChanged += new System.EventHandler(this.ckbIntSecurity_CheckedChanged);
        //
        // lPrompt
        //
        this.lPrompt.Location = new System.Drawing.Point(8, 272);
        this.lPrompt.Name = "lPrompt";
        this.lPrompt.Size = new System.Drawing.Size(432, 16);
        this.lPrompt.TabIndex = 12;
        this.lPrompt.Text = "(Optional) Enter the prompt displayed when asking for database credentials ";
        //
        // tbPrompt
        //
        this.tbPrompt.Location = new System.Drawing.Point(16, 296);
        this.tbPrompt.Name = "tbPrompt";
        this.tbPrompt.Size = new System.Drawing.Size(424, 20);
        this.tbPrompt.TabIndex = 7;
        this.tbPrompt.Text = "";
        this.tbPrompt.TextChanged += new System.EventHandler(this.tbPrompt_TextChanged);
        //
        // bOK
        //
        this.bOK.Location = new System.Drawing.Point(272, 344);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 9;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.CausesValidation = false;
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(368, 344);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 10;
        this.bCancel.Text = "Cancel";
        //
        // bTestConnection
        //
        this.bTestConnection.Location = new System.Drawing.Point(16, 344);
        this.bTestConnection.Name = "bTestConnection";
        this.bTestConnection.Size = new System.Drawing.Size(96, 23);
        this.bTestConnection.TabIndex = 8;
        this.bTestConnection.Text = "Test Connection";
        this.bTestConnection.Click += new System.EventHandler(this.bTestConnection_Click);
        //
        // lbDataSources
        //
        this.lbDataSources.Location = new System.Drawing.Point(16, 8);
        this.lbDataSources.Name = "lbDataSources";
        this.lbDataSources.Size = new System.Drawing.Size(120, 82);
        this.lbDataSources.TabIndex = 11;
        this.lbDataSources.SelectedIndexChanged += new System.EventHandler(this.lbDataSources_SelectedIndexChanged);
        //
        // bRemove
        //
        this.bRemove.Location = new System.Drawing.Point(144, 40);
        this.bRemove.Name = "bRemove";
        this.bRemove.Size = new System.Drawing.Size(56, 23);
        this.bRemove.TabIndex = 20;
        this.bRemove.Text = "Remove";
        this.bRemove.Click += new System.EventHandler(this.bRemove_Click);
        //
        // bAdd
        //
        this.bAdd.Location = new System.Drawing.Point(144, 8);
        this.bAdd.Name = "bAdd";
        this.bAdd.Size = new System.Drawing.Size(56, 23);
        this.bAdd.TabIndex = 19;
        this.bAdd.Text = "Add";
        this.bAdd.Click += new System.EventHandler(this.bAdd_Click);
        //
        // chkSharedDataSource
        //
        this.chkSharedDataSource.Location = new System.Drawing.Point(8, 112);
        this.chkSharedDataSource.Name = "chkSharedDataSource";
        this.chkSharedDataSource.Size = new System.Drawing.Size(184, 16);
        this.chkSharedDataSource.TabIndex = 1;
        this.chkSharedDataSource.Text = "Shared Data Source Reference";
        this.chkSharedDataSource.CheckedChanged += new System.EventHandler(this.chkSharedDataSource_CheckedChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(216, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(104, 23);
        this.label1.TabIndex = 22;
        this.label1.Text = "Data Source Name";
        //
        // tbDSName
        //
        this.tbDSName.Location = new System.Drawing.Point(216, 24);
        this.tbDSName.Name = "tbDSName";
        this.tbDSName.Size = new System.Drawing.Size(216, 20);
        this.tbDSName.TabIndex = 0;
        this.tbDSName.Text = "";
        this.tbDSName.Validating += new System.ComponentModel.CancelEventHandler(this.tbDSName_Validating);
        this.tbDSName.TextChanged += new System.EventHandler(this.tbDSName_TextChanged);
        //
        // bExprConnect
        //
        this.bExprConnect.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bExprConnect.Location = new System.Drawing.Point(416, 192);
        this.bExprConnect.Name = "bExprConnect";
        this.bExprConnect.Size = new System.Drawing.Size(22, 16);
        this.bExprConnect.TabIndex = 23;
        this.bExprConnect.Tag = "pright";
        this.bExprConnect.Text = "fx";
        this.bExprConnect.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bExprConnect.Click += new System.EventHandler(this.bExprConnect_Click);
        //
        // DialogDataSources
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(456, 374);
        this.Controls.Add(this.bExprConnect);
        this.Controls.Add(this.tbDSName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.chkSharedDataSource);
        this.Controls.Add(this.bRemove);
        this.Controls.Add(this.bAdd);
        this.Controls.Add(this.lbDataSources);
        this.Controls.Add(this.bTestConnection);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.bOK);
        this.Controls.Add(this.tbPrompt);
        this.Controls.Add(this.lPrompt);
        this.Controls.Add(this.ckbIntSecurity);
        this.Controls.Add(this.tbConnection);
        this.Controls.Add(this.lConnectionString);
        this.Controls.Add(this.cbDataProvider);
        this.Controls.Add(this.lDataProvider);
        this.Controls.Add(this.bGetFilename);
        this.Controls.Add(this.tbFilename);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogDataSources";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "DataSources in Report";
        this.ResumeLayout(false);
    }

    public void apply() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        _Draw.removeElement(rNode,"DataSources");
        // remove old DataSources
        if (this.lbDataSources.Items.Count <= 0)
            return ;
         
        // nothing in list?  all done
        XmlNode dsNode = _Draw.setElement(rNode,"DataSources",null);
        for (Object __dummyForeachVar1 : lbDataSources.Items)
        {
            DataSourceValues dsv = (DataSourceValues)__dummyForeachVar1;
            if (dsv.getName() == null || dsv.getName().Length <= 0)
                continue;
             
            // shouldn't really happen
            XmlNode dNode = _Draw.createElement(dsNode,"DataSource",null);
            // Create the name attribute
            _Draw.setElementAttribute(dNode,"Name",dsv.getName());
            if (dsv.getbDataSourceReference())
            {
                _Draw.setElement(dNode,"DataSourceReference",dsv.getDataSourceReference());
                continue;
            }
             
            // must fill out the connection properties
            XmlNode cNode = _Draw.createElement(dNode,"ConnectionProperties",null);
            _Draw.setElement(cNode,"DataProvider",dsv.getDataProvider());
            _Draw.setElement(cNode,"ConnectString",dsv.getConnectionString());
            _Draw.setElement(cNode,"IntegratedSecurity",dsv.getIntegratedSecurity() ? "true" : "false");
            if (dsv.getPrompt() != null && dsv.getPrompt().Length > 0)
                _Draw.setElement(cNode,"Prompt",dsv.getPrompt());
             
        }
    }

    private void bGetFilename_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Data source reference files (*.dsr)|*.dsr" + "All files (*.*)|*.*|";
        ofd.FilterIndex = 1;
        if (tbFilename.Text.Length > 0)
            ofd.FileName = tbFilename.Text;
        else
            ofd.FileName = "*.dsr"; 
        ofd.Title = "Specify Data Source Reference File Name";
        ofd.DefaultExt = "dsr";
        ofd.AddExtension = true;
        if (ofd.ShowDialog() == DialogResult.OK)
            tbFilename.Text = ofd.FileName;
         
    }

    private void tbFilename_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setDataSourceReference(tbFilename.Text);
        return ;
    }

    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        // Verify there are no duplicate names in the data sources
        Hashtable ht = new Hashtable(this.lbDataSources.Items.Count + 1);
        for (Object __dummyForeachVar2 : lbDataSources.Items)
        {
            DataSourceValues dsv = (DataSourceValues)__dummyForeachVar2;
            if (dsv.getName() == null || dsv.getName().Length == 0)
            {
                MessageBox.Show(this, "Name must be specified for all DataSources.", "Data Sources");
                return ;
            }
             
            if (!ReportNames.isNameValid(dsv.getName()))
            {
                MessageBox.Show(this, String.Format("Name '{0}' contains invalid characters.", dsv.getName()), "Data Sources");
                return ;
            }
             
            String name = (String)ht[dsv.getName()];
            if (name != null)
            {
                MessageBox.Show(this, String.Format("Each DataSource must have a unique name. '{0}' is repeated.", dsv.getName()), "Data Sources");
                return ;
            }
             
            ht.Add(dsv.getName(), dsv.getName());
        }
        // apply the result
        apply();
        DialogResult = DialogResult.OK;
    }

    private void bTestConnection_Click(Object sender, System.EventArgs e) throws Exception {
        if (DesignerUtility.TestConnection(this.cbDataProvider.Text, tbConnection.Text))
            MessageBox.Show("Connection succeeded!", "Test Connection");
         
    }

    private void tbDSName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        if (StringSupport.equals(dsv.getName(), tbDSName.Text))
            return ;
         
        dsv.setName(tbDSName.Text);
        // text doesn't change in listbox; force change by removing and re-adding item
        lbDataSources.BeginUpdate();
        lbDataSources.Items.RemoveAt(cur);
        lbDataSources.Items.Insert(cur, dsv);
        lbDataSources.SelectedIndex = cur;
        lbDataSources.EndUpdate();
    }

    private void chkSharedDataSource_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setbDataSourceReference(chkSharedDataSource.Checked);
        boolean bEnableDataSourceRef = chkSharedDataSource.Checked;
        // shared data source fields
        tbFilename.Enabled = bEnableDataSourceRef;
        bGetFilename.Enabled = bEnableDataSourceRef;
        // in report data source
        cbDataProvider.Enabled = !bEnableDataSourceRef;
        tbConnection.Enabled = !bEnableDataSourceRef;
        ckbIntSecurity.Enabled = !bEnableDataSourceRef;
        tbPrompt.Enabled = !bEnableDataSourceRef;
        bTestConnection.Enabled = !bEnableDataSourceRef;
        lDataProvider.Enabled = !bEnableDataSourceRef;
        lConnectionString.Enabled = !bEnableDataSourceRef;
        lPrompt.Enabled = !bEnableDataSourceRef;
    }

    private void lbDataSources_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        tbDSName.Text = dsv.getName();
        cbDataProvider.Text = dsv.getDataProvider();
        tbConnection.Text = dsv.getConnectionString();
        ckbIntSecurity.Checked = dsv.getIntegratedSecurity();
        this.tbFilename.Text = dsv.getDataSourceReference();
        tbPrompt.Text = dsv.getPrompt();
        this.chkSharedDataSource.Checked = dsv.getbDataSourceReference();
        chkSharedDataSource_CheckedChanged(this.chkSharedDataSource,e);
    }

    // force message
    private void bAdd_Click(Object sender, System.EventArgs e) throws Exception {
        DataSourceValues dsv = new DataSourceValues("datasource");
        int cur = this.lbDataSources.Items.Add(dsv);
        lbDataSources.SelectedIndex = cur;
        this.tbDSName.Focus();
    }

    private void bRemove_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        lbDataSources.Items.RemoveAt(cur);
        if (lbDataSources.Items.Count <= 0)
            return ;
         
        cur--;
        if (cur < 0)
            cur = 0;
         
        lbDataSources.SelectedIndex = cur;
    }

    private void tbDSName_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        if (!ReportNames.IsNameValid(tbDSName.Text))
        {
            e.Cancel = true;
            MessageBox.Show(this, String.Format("Name '{0}' contains invalid characters.", tbDSName.Text), "Data Sources");
        }
         
    }

    private void tbConnection_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setConnectionString(tbConnection.Text);
    }

    private void tbPrompt_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setPrompt(tbPrompt.Text);
    }

    private void cbDataProvider_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setDataProvider(cbDataProvider.Text);
    }

    private void ckbIntSecurity_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbDataSources.SelectedIndex;
        if (cur < 0)
            return ;
         
        DataSourceValues dsv = lbDataSources.Items[cur] instanceof DataSourceValues ? (DataSourceValues)lbDataSources.Items[cur] : (DataSourceValues)null;
        if (dsv == null)
            return ;
         
        dsv.setIntegratedSecurity(ckbIntSecurity.Checked);
    }

    private void bExprConnect_Click(Object sender, System.EventArgs e) throws Exception {
        DialogExprEditor ee = new DialogExprEditor(_Draw, this.tbConnection.Text, null, false);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
            tbConnection.Text = ee.getExpression();
         
    }

}


