//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:18 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataSourceReference;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.XmlUtil;
import fyiReporting.RdlDesign.DesignerUtility;
import fyiReporting.RdlDesign.DialogValidValues;
import fyiReporting.RdlDesign.ParameterValueItem;
import fyiReporting.RdlDesign.RdlDesigner;
import fyiReporting.RdlDesign.ReportParm;
import fyiReporting.RdlDesign.SqlColumn;
import fyiReporting.RdlDesign.SqlSchemaInfo;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Summary description for DialogDatabase.
*/
public class DialogDatabase  extends System.Windows.Forms.Form 
{
    RdlDesigner _rDesigner = null;
    static private final String SHARED_CONNECTION = "Shared Data Source";
    String _StashConnection = null;
    private System.Windows.Forms.Button btnCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button btnOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.TabPage DBConnection = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage DBSql = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage ReportType = new System.Windows.Forms.TabPage();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton rbTable = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbList = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbMatrix = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbChart = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.TextBox tbConnection = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TabPage ReportSyntax = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TextBox tbReportSyntax = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TabPage ReportPreview = new System.Windows.Forms.TabPage();
    private List<SqlColumn> _ColumnList = null;
    private String _TempFileName = null;
    private String _ResultReport = "nothing";
    private final String _Schema2003 = "xmlns=\"http://schemas.microsoft.com/sqlserver/reporting/2003/10/reportdefinition\" xmlns:rd=\"http://schemas.microsoft.com/SQLServer/reporting/reportdesigner\"";
    private final String _Schema2005 = "xmlns=\"http://schemas.microsoft.com/sqlserver/reporting/2005/01/reportdefinition\" xmlns:rd=\"http://schemas.microsoft.com/SQLServer/reporting/reportdesigner\"";
    private String _TemplateChart = " some junk";
    private String _TemplateMatrix = " some junk";
    private String _TemplateTable = "<?xml version=\'1.0\' encoding=\'UTF-8\'?>\r\n" + 
    "<Report |schema| > \r\n" + 
    "\t<Description>|description|</Description>\r\n" + 
    "\t<Author>|author|</Author>\r\n" + 
    "\t|orientation|\r\n" + 
    "\t<DataSources>\r\n" + 
    "\t\t<DataSource Name=\'DS1\'>\r\n" + 
    "\t\t\t|connectionproperties|\r\n" + 
    "\t\t</DataSource>\r\n" + 
    "\t</DataSources>\r\n" + 
    "\t<Width>7.5in</Width>\r\n" + 
    "\t<TopMargin>.25in</TopMargin>\r\n" + 
    "\t<LeftMargin>.25in</LeftMargin>\r\n" + 
    "\t<RightMargin>.25in</RightMargin>\r\n" + 
    "\t<BottomMargin>.25in</BottomMargin>\r\n" + 
    "\t|reportparameters|\r\n" + 
    "\t<DataSets>\r\n" + 
    "\t\t<DataSet Name=\'Data\'>\r\n" + 
    "\t\t\t<Query>\r\n" + 
    "\t\t\t\t<DataSourceName>DS1</DataSourceName>\r\n" + 
    "\t\t\t\t<CommandText>|sqltext|</CommandText>\r\n" + 
    "\t\t\t\t|queryparameters|\r\n" + 
    "\t\t\t</Query>\r\n" + 
    "\t\t\t<Fields>\r\n" + 
    "\t\t\t|sqlfields|\r\n" + 
    "\t\t\t</Fields>\r\n" + 
    "\t\t</DataSet>\r\n" + 
    "\t</DataSets>\r\n" + 
    "|ifdef reportname|\r\n" + 
    "\t<PageHeader>\r\n" + 
    "\t\t<Height>.5in</Height>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "\t\t\t<Textbox><Top>.1in</Top><Left>.1in</Left><Width>6in</Width><Height>.25in</Height><Value>|reportnameasis|</Value><Style><FontSize>15pt</FontSize><FontWeight>Bold</FontWeight></Style></Textbox> \r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t</PageHeader>\r\n" + 
    "|endif|\r\n" + 
    "\t<Body>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "\t\t\t<Table>\r\n" + 
    "\t\t\t\t<DataSetName>Data</DataSetName>\r\n" + 
    "\t\t\t\t<NoRows>Query returned no rows!</NoRows>\r\n" + 
    "\t\t\t\t<Style><BorderStyle><Default>Solid</Default></BorderStyle></Style>\r\n" + 
    "\t\t\t\t<TableColumns>\r\n" + 
    "\t\t\t\t\t|tablecolumns|\r\n" + 
    "\t\t\t\t</TableColumns>\r\n" + 
    "\t\t\t\t<Header>\r\n" + 
    "\t\t\t\t\t<TableRows>\r\n" + 
    "\t\t\t\t\t\t<TableRow>\r\n" + 
    "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
    "\t\t\t\t\t\t\t<TableCells>|tableheaders|</TableCells>\r\n" + 
    "\t\t\t\t\t\t</TableRow>\r\n" + 
    "\t\t\t\t\t</TableRows>\r\n" + 
    "\t\t\t\t\t<RepeatOnNewPage>true</RepeatOnNewPage>\r\n" + 
    "\t\t\t\t</Header>\r\n" + 
    "|ifdef grouping|\r\n" + 
    "\t\t\t\t<TableGroups>\r\n" + 
    "\t\t\t\t<TableGroup>\r\n" + 
    "\t\t\t\t<Header>\r\n" + 
    "\t\t\t\t\t<TableRows>\r\n" + 
    "\t\t\t\t\t\t<TableRow>\r\n" + 
    "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
    "\t\t\t\t\t\t\t<TableCells>\r\n" + 
    "\t\t\t\t\t\t\t\t<TableCell>\r\n" + 
    "\t\t\t\t\t\t\t\t\t<ColSpan>|columncount|</ColSpan>\r\n" + 
    "\t\t\t\t\t\t\t\t\t<ReportItems><Textbox><Value>=Fields.|groupbycolumn|.Value</Value><Style><PaddingLeft>2 pt</PaddingLeft><BorderStyle><Default>Solid</Default></BorderStyle><FontWeight>Bold</FontWeight></Style></Textbox></ReportItems>\r\n" + 
    "\t\t\t\t\t\t\t\t</TableCell>\r\n" + 
    "\t\t\t\t\t\t\t</TableCells>\r\n" + 
    "\t\t\t\t\t\t</TableRow>\r\n" + 
    "\t\t\t\t\t</TableRows>\r\n" + 
    "\t\t\t\t\t<RepeatOnNewPage>true</RepeatOnNewPage>\r\n" + 
    "\t\t\t\t</Header>\r\n" + 
    "\t\t\t\t<Footer>\r\n" + 
    "\t\t\t\t\t<TableRows>\r\n" + 
    "\t\t\t\t\t\t<TableRow>\r\n" + 
    "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
    "\t\t\t\t\t\t\t<TableCells>|gtablefooters|</TableCells>\r\n" + 
    "\t\t\t\t\t\t</TableRow>\r\n" + 
    "\t\t\t\t\t</TableRows>\r\n" + 
    "\t\t\t\t</Footer>\r\n" + 
    "\t\t<Grouping Name=\'|groupbycolumn|Group\'><GroupExpressions><GroupExpression>=Fields!|groupbycolumn|.Value</GroupExpression></GroupExpressions></Grouping>\r\n" + 
    "\t\t</TableGroup>\r\n" + 
    "\t\t</TableGroups>\r\n" + 
    "|endif|\r\n" + 
    "\t\t\t\t<Details>\r\n" + 
    "\t\t\t\t\t<TableRows>\r\n" + 
    "\t\t\t\t\t\t<TableRow>\r\n" + 
    "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
    "\t\t\t\t\t\t\t<TableCells>|tablevalues|</TableCells>\r\n" + 
    "\t\t\t\t\t\t</TableRow>\r\n" + 
    "\t\t\t\t\t</TableRows>\r\n" + 
    "\t\t\t\t</Details>\r\n" + 
    "|ifdef footers|\r\n" + 
    "\t\t\t\t<Footer>\r\n" + 
    "\t\t\t\t\t<TableRows>\r\n" + 
    "\t\t\t\t\t\t<TableRow>\r\n" + 
    "\t\t\t\t\t\t\t<Height>12 pt</Height>\r\n" + 
    "\t\t\t\t\t\t\t<TableCells>|tablefooters|</TableCells>\r\n" + 
    "\t\t\t\t\t\t</TableRow>\r\n" + 
    "\t\t\t\t\t</TableRows>\r\n" + 
    "\t\t\t\t</Footer>\r\n" + 
    "|endif|\r\n" + 
    "\t\t\t</Table>\r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t\t<Height>|bodyheight|</Height>\r\n" + 
    "\t</Body>\r\n" + 
    "\t<PageFooter>\r\n" + 
    "\t\t<Height>14 pt</Height>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "\t\t\t<Textbox><Top>1 pt</Top><Left>10 pt</Left><Height>12 pt</Height><Width>3in</Width>\r\n" + 
    "\t\t\t\t<Value>=Globals!PageNumber.Value + \' of \' + Globals!TotalPages.Value</Value>\r\n" + 
    "\t\t\t\t<Style><FontSize>10pt</FontSize><FontWeight>Normal</FontWeight></Style>\r\n" + 
    "\t\t\t</Textbox> \t\r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t</PageFooter>\r\n" + 
    "</Report>";
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbReportName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbReportDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbReportAuthor = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Panel panel2 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.TreeView tvTablesColumns = new System.Windows.Forms.TreeView();
    private System.Windows.Forms.TextBox tbSQL = new System.Windows.Forms.TextBox();
    private fyiReporting.RdlViewer.RdlViewer rdlViewer1;
    private System.Windows.Forms.TabPage ReportParameters = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.ListBox lbParameters = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Button bAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bRemove = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label lParmName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbParmName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label lParmType = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbParmType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label lParmPrompt = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbParmPrompt = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox ckbParmAllowBlank = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label lbParmValidValues = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbParmValidValues = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bParmUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bParmDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.CheckBox ckbParmAllowNull = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TabControl tcDialog = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.Label lDefaultValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbParmDefaultValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TabPage TabularGroup = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.ComboBox cbColumnList = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox ckbGrandTotal = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckedListBox clbSubtotal = new System.Windows.Forms.CheckedListBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbOrientation = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button bMove = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bValidValues = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbConnectionTypes = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label lODBC = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbOdbcNames = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button bTestConnection = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label lConnection = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button bShared = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton rbSchemaNo = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbSchema2003 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbSchema2005 = new System.Windows.Forms.RadioButton();
    private String _TemplateList = "<?xml version=\'1.0\' encoding=\'UTF-8\'?>\r\n" + 
    "<Report |schema| > \r\n" + 
    "\t<Description>|description|</Description>\r\n" + 
    "\t<Author>|author|</Author>\r\n" + 
    "\t|orientation|\r\n" + 
    "\t<DataSources>\r\n" + 
    "\t\t<DataSource Name=\'DS1\'>\r\n" + 
    "\t\t\t|connectionproperties|\r\n" + 
    "\t\t</DataSource>\r\n" + 
    "\t</DataSources>\r\n" + 
    "\t<Width>7.5in</Width>\r\n" + 
    "\t<TopMargin>.25in</TopMargin>\r\n" + 
    "\t<LeftMargin>.25in</LeftMargin>\r\n" + 
    "\t<RightMargin>.25in</RightMargin>\r\n" + 
    "\t<BottomMargin>.25in</BottomMargin>\r\n" + 
    "\t|reportparameters|\r\n" + 
    "\t<DataSets>\r\n" + 
    "\t\t<DataSet Name=\'Data\'>\r\n" + 
    "\t\t\t<Query>\r\n" + 
    "\t\t\t\t<DataSourceName>DS1</DataSourceName>\r\n" + 
    "\t\t\t\t<CommandText>|sqltext|</CommandText>\r\n" + 
    "\t\t\t\t|queryparameters|\r\n" + 
    "\t\t\t</Query>\r\n" + 
    "\t\t\t<Fields>\r\n" + 
    "\t\t\t\t|sqlfields|\r\n" + 
    "\t\t\t</Fields>\r\n" + 
    "\t\t</DataSet>\r\n" + 
    "\t</DataSets>\r\n" + 
    "\t<PageHeader>\r\n" + 
    "\t\t<Height>.5in</Height>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "|ifdef reportname|\r\n" + 
    "\t\t\t<Textbox><Top>.02in</Top><Left>.1in</Left><Width>6in</Width><Height>.25in</Height><Value>|reportname|</Value><Style><FontSize>15pt</FontSize><FontWeight>Bold</FontWeight></Style></Textbox> \r\n" + 
    "|endif|\r\n" + 
    "\t\t\t|listheaders|\r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t</PageHeader>\r\n" + 
    "\t<Body><Height>25 pt</Height>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "\t\t\t<List>\r\n" + 
    "\t\t\t\t<DataSetName>Data</DataSetName>\r\n" + 
    "\t\t\t\t<Height>24 pt</Height>\r\n" + 
    "\t\t\t\t<NoRows>Query returned no rows!</NoRows>\r\n" + 
    "\t\t\t\t<ReportItems>\r\n" + 
    "\t\t\t\t\t|listvalues|\r\n" + 
    "\t\t\t\t</ReportItems>\r\n" + 
    "\t\t\t\t<Width>|listwidth|</Width>\r\n" + 
    "\t\t\t</List>\r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t\t</Body>\r\n" + 
    "\t<PageFooter>\r\n" + 
    "\t\t<Height>14 pt</Height>\r\n" + 
    "\t\t<ReportItems>\r\n" + 
    "\t\t\t<Textbox><Top>1 pt</Top><Left>10 pt</Left><Height>12 pt</Height><Width>3in</Width>\r\n" + 
    "\t\t\t\t<Value>=Globals!PageNumber.Value + \' of \' + Globals!TotalPages.Value</Value>\r\n" + 
    "\t\t\t\t<Style><FontSize>10pt</FontSize><FontWeight>Normal</FontWeight></Style>\r\n" + 
    "\t\t\t</Textbox> \t\r\n" + 
    "\t\t</ReportItems>\r\n" + 
    "\t</PageFooter>\r\n" + 
    "\t\t</Report>";
    public DialogDatabase(RdlDesigner rDesigner) throws Exception {
        _rDesigner = rDesigner;
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        String[] items = RdlEngineConfig.getProviders();
        cbConnectionTypes.Items.Add(SHARED_CONNECTION);
        cbConnectionTypes.Items.AddRange(items);
        cbConnectionTypes.SelectedIndex = 1;
        cbOrientation.SelectedIndex = 0;
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
        this.tcDialog = new System.Windows.Forms.TabControl();
        this.ReportType = new System.Windows.Forms.TabPage();
        this.cbOrientation = new System.Windows.Forms.ComboBox();
        this.label6 = new System.Windows.Forms.Label();
        this.tbReportAuthor = new System.Windows.Forms.TextBox();
        this.tbReportDescription = new System.Windows.Forms.TextBox();
        this.tbReportName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.rbChart = new System.Windows.Forms.RadioButton();
        this.rbMatrix = new System.Windows.Forms.RadioButton();
        this.rbList = new System.Windows.Forms.RadioButton();
        this.rbTable = new System.Windows.Forms.RadioButton();
        this.DBConnection = new System.Windows.Forms.TabPage();
        this.bShared = new System.Windows.Forms.Button();
        this.bTestConnection = new System.Windows.Forms.Button();
        this.cbOdbcNames = new System.Windows.Forms.ComboBox();
        this.lODBC = new System.Windows.Forms.Label();
        this.lConnection = new System.Windows.Forms.Label();
        this.cbConnectionTypes = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.tbConnection = new System.Windows.Forms.TextBox();
        this.ReportParameters = new System.Windows.Forms.TabPage();
        this.bValidValues = new System.Windows.Forms.Button();
        this.tbParmDefaultValue = new System.Windows.Forms.TextBox();
        this.lDefaultValue = new System.Windows.Forms.Label();
        this.bParmDown = new System.Windows.Forms.Button();
        this.bParmUp = new System.Windows.Forms.Button();
        this.tbParmValidValues = new System.Windows.Forms.TextBox();
        this.lbParmValidValues = new System.Windows.Forms.Label();
        this.ckbParmAllowBlank = new System.Windows.Forms.CheckBox();
        this.ckbParmAllowNull = new System.Windows.Forms.CheckBox();
        this.tbParmPrompt = new System.Windows.Forms.TextBox();
        this.lParmPrompt = new System.Windows.Forms.Label();
        this.cbParmType = new System.Windows.Forms.ComboBox();
        this.lParmType = new System.Windows.Forms.Label();
        this.tbParmName = new System.Windows.Forms.TextBox();
        this.lParmName = new System.Windows.Forms.Label();
        this.bRemove = new System.Windows.Forms.Button();
        this.bAdd = new System.Windows.Forms.Button();
        this.lbParameters = new System.Windows.Forms.ListBox();
        this.DBSql = new System.Windows.Forms.TabPage();
        this.panel2 = new System.Windows.Forms.Panel();
        this.bMove = new System.Windows.Forms.Button();
        this.tbSQL = new System.Windows.Forms.TextBox();
        this.tvTablesColumns = new System.Windows.Forms.TreeView();
        this.TabularGroup = new System.Windows.Forms.TabPage();
        this.clbSubtotal = new System.Windows.Forms.CheckedListBox();
        this.ckbGrandTotal = new System.Windows.Forms.CheckBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.cbColumnList = new System.Windows.Forms.ComboBox();
        this.ReportSyntax = new System.Windows.Forms.TabPage();
        this.tbReportSyntax = new System.Windows.Forms.TextBox();
        this.ReportPreview = new System.Windows.Forms.TabPage();
        this.rdlViewer1 = new fyiReporting.RdlViewer.RdlViewer();
        this.btnCancel = new System.Windows.Forms.Button();
        this.panel1 = new System.Windows.Forms.Panel();
        this.btnOK = new System.Windows.Forms.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.rbSchemaNo = new System.Windows.Forms.RadioButton();
        this.rbSchema2003 = new System.Windows.Forms.RadioButton();
        this.rbSchema2005 = new System.Windows.Forms.RadioButton();
        this.tcDialog.SuspendLayout();
        this.ReportType.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.DBConnection.SuspendLayout();
        this.ReportParameters.SuspendLayout();
        this.DBSql.SuspendLayout();
        this.panel2.SuspendLayout();
        this.TabularGroup.SuspendLayout();
        this.ReportSyntax.SuspendLayout();
        this.ReportPreview.SuspendLayout();
        this.panel1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // tcDialog
        //
        this.tcDialog.Controls.Add(this.ReportType);
        this.tcDialog.Controls.Add(this.DBConnection);
        this.tcDialog.Controls.Add(this.ReportParameters);
        this.tcDialog.Controls.Add(this.DBSql);
        this.tcDialog.Controls.Add(this.TabularGroup);
        this.tcDialog.Controls.Add(this.ReportSyntax);
        this.tcDialog.Controls.Add(this.ReportPreview);
        this.tcDialog.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tcDialog.Location = new System.Drawing.Point(0, 0);
        this.tcDialog.Name = "tcDialog";
        this.tcDialog.SelectedIndex = 0;
        this.tcDialog.Size = new System.Drawing.Size(528, 326);
        this.tcDialog.TabIndex = 0;
        this.tcDialog.SelectedIndexChanged += new System.EventHandler(this.tabControl1_SelectedIndexChanged);
        //
        // ReportType
        //
        this.ReportType.Controls.Add(this.groupBox2);
        this.ReportType.Controls.Add(this.cbOrientation);
        this.ReportType.Controls.Add(this.label6);
        this.ReportType.Controls.Add(this.tbReportAuthor);
        this.ReportType.Controls.Add(this.tbReportDescription);
        this.ReportType.Controls.Add(this.tbReportName);
        this.ReportType.Controls.Add(this.label3);
        this.ReportType.Controls.Add(this.label2);
        this.ReportType.Controls.Add(this.label1);
        this.ReportType.Controls.Add(this.groupBox1);
        this.ReportType.Location = new System.Drawing.Point(4, 22);
        this.ReportType.Name = "ReportType";
        this.ReportType.Size = new System.Drawing.Size(520, 300);
        this.ReportType.TabIndex = 3;
        this.ReportType.Tag = "type";
        this.ReportType.Text = "Report Info";
        //
        // cbOrientation
        //
        this.cbOrientation.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbOrientation.Items.AddRange(new Object[]{ "Portrait (8.5\" by 11\")", "Landscape (11\" by 8.5\")" });
        this.cbOrientation.Location = new System.Drawing.Point(96, 224);
        this.cbOrientation.Name = "cbOrientation";
        this.cbOrientation.Size = new System.Drawing.Size(168, 21);
        this.cbOrientation.TabIndex = 8;
        this.cbOrientation.SelectedIndexChanged += new System.EventHandler(this.emptyReportSyntax);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(16, 224);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(64, 23);
        this.label6.TabIndex = 7;
        this.label6.Text = "Orientation:";
        //
        // tbReportAuthor
        //
        this.tbReportAuthor.Location = new System.Drawing.Point(96, 192);
        this.tbReportAuthor.Name = "tbReportAuthor";
        this.tbReportAuthor.Size = new System.Drawing.Size(304, 20);
        this.tbReportAuthor.TabIndex = 6;
        this.tbReportAuthor.Text = "";
        this.tbReportAuthor.TextChanged += new System.EventHandler(this.tbReportAuthor_TextChanged);
        //
        // tbReportDescription
        //
        this.tbReportDescription.Location = new System.Drawing.Point(96, 160);
        this.tbReportDescription.Name = "tbReportDescription";
        this.tbReportDescription.Size = new System.Drawing.Size(304, 20);
        this.tbReportDescription.TabIndex = 5;
        this.tbReportDescription.Text = "";
        this.tbReportDescription.TextChanged += new System.EventHandler(this.tbReportDescription_TextChanged);
        //
        // tbReportName
        //
        this.tbReportName.Location = new System.Drawing.Point(96, 128);
        this.tbReportName.Name = "tbReportName";
        this.tbReportName.Size = new System.Drawing.Size(304, 20);
        this.tbReportName.TabIndex = 4;
        this.tbReportName.Text = "";
        this.tbReportName.TextChanged += new System.EventHandler(this.tbReportName_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 192);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(72, 23);
        this.label3.TabIndex = 3;
        this.label3.Text = "Author:";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(16, 160);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(72, 23);
        this.label2.TabIndex = 2;
        this.label2.Text = "Description:";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(16, 128);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(64, 23);
        this.label1.TabIndex = 1;
        this.label1.Text = "Name:";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.rbChart);
        this.groupBox1.Controls.Add(this.rbMatrix);
        this.groupBox1.Controls.Add(this.rbList);
        this.groupBox1.Controls.Add(this.rbTable);
        this.groupBox1.Location = new System.Drawing.Point(16, 16);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(384, 104);
        this.groupBox1.TabIndex = 0;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Report Type";
        //
        // rbChart
        //
        this.rbChart.Location = new System.Drawing.Point(168, 56);
        this.rbChart.Name = "rbChart";
        this.rbChart.TabIndex = 3;
        this.rbChart.Text = "Chart";
        this.rbChart.Visible = false;
        this.rbChart.CheckedChanged += new System.EventHandler(this.rbChart_CheckedChanged);
        //
        // rbMatrix
        //
        this.rbMatrix.Location = new System.Drawing.Point(168, 24);
        this.rbMatrix.Name = "rbMatrix";
        this.rbMatrix.TabIndex = 2;
        this.rbMatrix.Text = "Matrix";
        this.rbMatrix.Visible = false;
        this.rbMatrix.CheckedChanged += new System.EventHandler(this.rbMatrix_CheckedChanged);
        //
        // rbList
        //
        this.rbList.Location = new System.Drawing.Point(32, 56);
        this.rbList.Name = "rbList";
        this.rbList.TabIndex = 1;
        this.rbList.Text = "List";
        this.rbList.CheckedChanged += new System.EventHandler(this.rbList_CheckedChanged);
        //
        // rbTable
        //
        this.rbTable.Checked = true;
        this.rbTable.Location = new System.Drawing.Point(32, 24);
        this.rbTable.Name = "rbTable";
        this.rbTable.TabIndex = 0;
        this.rbTable.TabStop = true;
        this.rbTable.Text = "Table";
        this.rbTable.CheckedChanged += new System.EventHandler(this.rbTable_CheckedChanged);
        //
        // DBConnection
        //
        this.DBConnection.CausesValidation = false;
        this.DBConnection.Controls.Add(this.bShared);
        this.DBConnection.Controls.Add(this.bTestConnection);
        this.DBConnection.Controls.Add(this.cbOdbcNames);
        this.DBConnection.Controls.Add(this.lODBC);
        this.DBConnection.Controls.Add(this.lConnection);
        this.DBConnection.Controls.Add(this.cbConnectionTypes);
        this.DBConnection.Controls.Add(this.label7);
        this.DBConnection.Controls.Add(this.tbConnection);
        this.DBConnection.Location = new System.Drawing.Point(4, 22);
        this.DBConnection.Name = "DBConnection";
        this.DBConnection.Size = new System.Drawing.Size(520, 300);
        this.DBConnection.TabIndex = 0;
        this.DBConnection.Tag = "connect";
        this.DBConnection.Text = "Connection";
        this.DBConnection.Validating += new System.ComponentModel.CancelEventHandler(this.DBConnection_Validating);
        //
        // bShared
        //
        this.bShared.Location = new System.Drawing.Point(216, 48);
        this.bShared.Name = "bShared";
        this.bShared.Size = new System.Drawing.Size(24, 16);
        this.bShared.TabIndex = 6;
        this.bShared.Text = "...";
        this.bShared.Click += new System.EventHandler(this.bShared_Click);
        //
        // bTestConnection
        //
        this.bTestConnection.Location = new System.Drawing.Point(16, 104);
        this.bTestConnection.Name = "bTestConnection";
        this.bTestConnection.Size = new System.Drawing.Size(104, 23);
        this.bTestConnection.TabIndex = 3;
        this.bTestConnection.Text = "Test Connection";
        this.bTestConnection.Click += new System.EventHandler(this.bTestConnection_Click);
        //
        // cbOdbcNames
        //
        this.cbOdbcNames.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbOdbcNames.Location = new System.Drawing.Point(352, 16);
        this.cbOdbcNames.Name = "cbOdbcNames";
        this.cbOdbcNames.Size = new System.Drawing.Size(152, 21);
        this.cbOdbcNames.Sorted = true;
        this.cbOdbcNames.TabIndex = 1;
        this.cbOdbcNames.SelectedIndexChanged += new System.EventHandler(this.cbOdbcNames_SelectedIndexChanged);
        //
        // lODBC
        //
        this.lODBC.Location = new System.Drawing.Point(240, 16);
        this.lODBC.Name = "lODBC";
        this.lODBC.Size = new System.Drawing.Size(112, 23);
        this.lODBC.TabIndex = 5;
        this.lODBC.Text = "ODBC Data Sources";
        //
        // lConnection
        //
        this.lConnection.Location = new System.Drawing.Point(16, 48);
        this.lConnection.Name = "lConnection";
        this.lConnection.Size = new System.Drawing.Size(184, 16);
        this.lConnection.TabIndex = 4;
        this.lConnection.Text = "Connection:";
        //
        // cbConnectionTypes
        //
        this.cbConnectionTypes.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbConnectionTypes.Location = new System.Drawing.Point(112, 16);
        this.cbConnectionTypes.Name = "cbConnectionTypes";
        this.cbConnectionTypes.Size = new System.Drawing.Size(128, 21);
        this.cbConnectionTypes.TabIndex = 0;
        this.cbConnectionTypes.SelectedIndexChanged += new System.EventHandler(this.cbConnectionTypes_SelectedIndexChanged);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(16, 16);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(96, 23);
        this.label7.TabIndex = 2;
        this.label7.Text = "Connection Type:";
        //
        // tbConnection
        //
        this.tbConnection.Location = new System.Drawing.Point(16, 72);
        this.tbConnection.Name = "tbConnection";
        this.tbConnection.Size = new System.Drawing.Size(488, 20);
        this.tbConnection.TabIndex = 2;
        this.tbConnection.Text = "Server=(local)\\VSDotNet;DataBase=Northwind;Integrated Security=SSPI;Connect Timeo" + "ut=5";
        this.tbConnection.TextChanged += new System.EventHandler(this.tbConnection_TextChanged);
        //
        // ReportParameters
        //
        this.ReportParameters.Controls.Add(this.bValidValues);
        this.ReportParameters.Controls.Add(this.tbParmDefaultValue);
        this.ReportParameters.Controls.Add(this.lDefaultValue);
        this.ReportParameters.Controls.Add(this.bParmDown);
        this.ReportParameters.Controls.Add(this.bParmUp);
        this.ReportParameters.Controls.Add(this.tbParmValidValues);
        this.ReportParameters.Controls.Add(this.lbParmValidValues);
        this.ReportParameters.Controls.Add(this.ckbParmAllowBlank);
        this.ReportParameters.Controls.Add(this.ckbParmAllowNull);
        this.ReportParameters.Controls.Add(this.tbParmPrompt);
        this.ReportParameters.Controls.Add(this.lParmPrompt);
        this.ReportParameters.Controls.Add(this.cbParmType);
        this.ReportParameters.Controls.Add(this.lParmType);
        this.ReportParameters.Controls.Add(this.tbParmName);
        this.ReportParameters.Controls.Add(this.lParmName);
        this.ReportParameters.Controls.Add(this.bRemove);
        this.ReportParameters.Controls.Add(this.bAdd);
        this.ReportParameters.Controls.Add(this.lbParameters);
        this.ReportParameters.Location = new System.Drawing.Point(4, 22);
        this.ReportParameters.Name = "ReportParameters";
        this.ReportParameters.Size = new System.Drawing.Size(520, 300);
        this.ReportParameters.TabIndex = 6;
        this.ReportParameters.Tag = "parameters";
        this.ReportParameters.Text = "Parameters";
        //
        // bValidValues
        //
        this.bValidValues.Location = new System.Drawing.Point(432, 152);
        this.bValidValues.Name = "bValidValues";
        this.bValidValues.Size = new System.Drawing.Size(24, 23);
        this.bValidValues.TabIndex = 16;
        this.bValidValues.Text = "...";
        this.bValidValues.Click += new System.EventHandler(this.bValidValues_Click);
        //
        // tbParmDefaultValue
        //
        this.tbParmDefaultValue.Location = new System.Drawing.Point(208, 200);
        this.tbParmDefaultValue.Name = "tbParmDefaultValue";
        this.tbParmDefaultValue.Size = new System.Drawing.Size(216, 20);
        this.tbParmDefaultValue.TabIndex = 10;
        this.tbParmDefaultValue.Text = "";
        this.tbParmDefaultValue.TextChanged += new System.EventHandler(this.tbParmDefaultValue_TextChanged);
        //
        // lDefaultValue
        //
        this.lDefaultValue.Location = new System.Drawing.Point(208, 184);
        this.lDefaultValue.Name = "lDefaultValue";
        this.lDefaultValue.Size = new System.Drawing.Size(100, 16);
        this.lDefaultValue.TabIndex = 15;
        this.lDefaultValue.Text = "Default Value";
        //
        // bParmDown
        //
        this.bParmDown.Font = new System.Drawing.Font("Wingdings", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(2)));
        this.bParmDown.Location = new System.Drawing.Point(168, 64);
        this.bParmDown.Name = "bParmDown";
        this.bParmDown.Size = new System.Drawing.Size(24, 24);
        this.bParmDown.TabIndex = 14;
        this.bParmDown.Text = "";
        this.bParmDown.Click += new System.EventHandler(this.bParmDown_Click);
        //
        // bParmUp
        //
        this.bParmUp.Font = new System.Drawing.Font("Wingdings", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(2)));
        this.bParmUp.Location = new System.Drawing.Point(168, 24);
        this.bParmUp.Name = "bParmUp";
        this.bParmUp.Size = new System.Drawing.Size(24, 24);
        this.bParmUp.TabIndex = 13;
        this.bParmUp.Text = "";
        this.bParmUp.Click += new System.EventHandler(this.bParmUp_Click);
        //
        // tbParmValidValues
        //
        this.tbParmValidValues.Location = new System.Drawing.Point(208, 152);
        this.tbParmValidValues.Name = "tbParmValidValues";
        this.tbParmValidValues.ReadOnly = true;
        this.tbParmValidValues.Size = new System.Drawing.Size(216, 20);
        this.tbParmValidValues.TabIndex = 9;
        this.tbParmValidValues.Text = "";
        //
        // lbParmValidValues
        //
        this.lbParmValidValues.Location = new System.Drawing.Point(208, 136);
        this.lbParmValidValues.Name = "lbParmValidValues";
        this.lbParmValidValues.Size = new System.Drawing.Size(100, 16);
        this.lbParmValidValues.TabIndex = 11;
        this.lbParmValidValues.Text = "Valid Values";
        //
        // ckbParmAllowBlank
        //
        this.ckbParmAllowBlank.Location = new System.Drawing.Point(288, 232);
        this.ckbParmAllowBlank.Name = "ckbParmAllowBlank";
        this.ckbParmAllowBlank.Size = new System.Drawing.Size(152, 24);
        this.ckbParmAllowBlank.TabIndex = 12;
        this.ckbParmAllowBlank.Text = "Allow blank (strings only)";
        this.ckbParmAllowBlank.CheckedChanged += new System.EventHandler(this.ckbParmAllowBlank_CheckedChanged);
        //
        // ckbParmAllowNull
        //
        this.ckbParmAllowNull.Location = new System.Drawing.Point(208, 232);
        this.ckbParmAllowNull.Name = "ckbParmAllowNull";
        this.ckbParmAllowNull.Size = new System.Drawing.Size(72, 24);
        this.ckbParmAllowNull.TabIndex = 11;
        this.ckbParmAllowNull.Text = "Allow null";
        this.ckbParmAllowNull.CheckedChanged += new System.EventHandler(this.ckbParmAllowNull_CheckedChanged);
        //
        // tbParmPrompt
        //
        this.tbParmPrompt.Location = new System.Drawing.Point(208, 104);
        this.tbParmPrompt.Name = "tbParmPrompt";
        this.tbParmPrompt.Size = new System.Drawing.Size(216, 20);
        this.tbParmPrompt.TabIndex = 8;
        this.tbParmPrompt.Text = "";
        this.tbParmPrompt.TextChanged += new System.EventHandler(this.tbParmPrompt_TextChanged);
        //
        // lParmPrompt
        //
        this.lParmPrompt.Location = new System.Drawing.Point(208, 88);
        this.lParmPrompt.Name = "lParmPrompt";
        this.lParmPrompt.Size = new System.Drawing.Size(48, 16);
        this.lParmPrompt.TabIndex = 7;
        this.lParmPrompt.Text = "Prompt";
        //
        // cbParmType
        //
        this.cbParmType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbParmType.Items.AddRange(new Object[]{ "Boolean", "DateTime", "Integer", "Float", "String" });
        this.cbParmType.Location = new System.Drawing.Point(288, 56);
        this.cbParmType.Name = "cbParmType";
        this.cbParmType.Size = new System.Drawing.Size(80, 21);
        this.cbParmType.TabIndex = 6;
        this.cbParmType.SelectedIndexChanged += new System.EventHandler(this.cbParmType_SelectedIndexChanged);
        //
        // lParmType
        //
        this.lParmType.Location = new System.Drawing.Point(208, 56);
        this.lParmType.Name = "lParmType";
        this.lParmType.Size = new System.Drawing.Size(56, 23);
        this.lParmType.TabIndex = 5;
        this.lParmType.Text = "Datatype";
        //
        // tbParmName
        //
        this.tbParmName.Location = new System.Drawing.Point(288, 24);
        this.tbParmName.Name = "tbParmName";
        this.tbParmName.Size = new System.Drawing.Size(136, 20);
        this.tbParmName.TabIndex = 4;
        this.tbParmName.Text = "";
        this.tbParmName.TextChanged += new System.EventHandler(this.tbParmName_TextChanged);
        //
        // lParmName
        //
        this.lParmName.Location = new System.Drawing.Point(208, 24);
        this.lParmName.Name = "lParmName";
        this.lParmName.Size = new System.Drawing.Size(48, 16);
        this.lParmName.TabIndex = 3;
        this.lParmName.Text = "Name";
        //
        // bRemove
        //
        this.bRemove.Location = new System.Drawing.Point(104, 264);
        this.bRemove.Name = "bRemove";
        this.bRemove.Size = new System.Drawing.Size(56, 23);
        this.bRemove.TabIndex = 2;
        this.bRemove.Text = "Remove";
        this.bRemove.Click += new System.EventHandler(this.bRemove_Click);
        //
        // bAdd
        //
        this.bAdd.Location = new System.Drawing.Point(16, 264);
        this.bAdd.Name = "bAdd";
        this.bAdd.Size = new System.Drawing.Size(56, 23);
        this.bAdd.TabIndex = 1;
        this.bAdd.Text = "Add";
        this.bAdd.Click += new System.EventHandler(this.bAdd_Click);
        //
        // lbParameters
        //
        this.lbParameters.Location = new System.Drawing.Point(16, 16);
        this.lbParameters.Name = "lbParameters";
        this.lbParameters.Size = new System.Drawing.Size(144, 238);
        this.lbParameters.TabIndex = 0;
        this.lbParameters.SelectedIndexChanged += new System.EventHandler(this.lbParameters_SelectedIndexChanged);
        //
        // DBSql
        //
        this.DBSql.Controls.Add(this.panel2);
        this.DBSql.Location = new System.Drawing.Point(4, 22);
        this.DBSql.Name = "DBSql";
        this.DBSql.Size = new System.Drawing.Size(520, 300);
        this.DBSql.TabIndex = 1;
        this.DBSql.Tag = "sql";
        this.DBSql.Text = "SQL";
        //
        // panel2
        //
        this.panel2.Controls.Add(this.bMove);
        this.panel2.Controls.Add(this.tbSQL);
        this.panel2.Controls.Add(this.tvTablesColumns);
        this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
        this.panel2.Location = new System.Drawing.Point(0, 0);
        this.panel2.Name = "panel2";
        this.panel2.Size = new System.Drawing.Size(520, 300);
        this.panel2.TabIndex = 1;
        //
        // bMove
        //
        this.bMove.Location = new System.Drawing.Point(140, 16);
        this.bMove.Name = "bMove";
        this.bMove.Size = new System.Drawing.Size(32, 23);
        this.bMove.TabIndex = 4;
        this.bMove.Text = ">>";
        this.bMove.Click += new System.EventHandler(this.bMove_Click);
        //
        // tbSQL
        //
        this.tbSQL.AllowDrop = true;
        this.tbSQL.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.tbSQL.Location = new System.Drawing.Point(176, 0);
        this.tbSQL.Multiline = true;
        this.tbSQL.Name = "tbSQL";
        this.tbSQL.Size = new System.Drawing.Size(344, 300);
        this.tbSQL.TabIndex = 3;
        this.tbSQL.Text = "";
        this.tbSQL.TextChanged += new System.EventHandler(this.tbSQL_TextChanged);
        //
        // tvTablesColumns
        //
        this.tvTablesColumns.Dock = System.Windows.Forms.DockStyle.Left;
        this.tvTablesColumns.FullRowSelect = true;
        this.tvTablesColumns.ImageIndex = -1;
        this.tvTablesColumns.Location = new System.Drawing.Point(0, 0);
        this.tvTablesColumns.Name = "tvTablesColumns";
        this.tvTablesColumns.SelectedImageIndex = -1;
        this.tvTablesColumns.Size = new System.Drawing.Size(136, 300);
        this.tvTablesColumns.TabIndex = 1;
        this.tvTablesColumns.BeforeExpand += new System.Windows.Forms.TreeViewCancelEventHandler(this.tvTablesColumns_BeforeExpand);
        //
        // TabularGroup
        //
        this.TabularGroup.Controls.Add(this.clbSubtotal);
        this.TabularGroup.Controls.Add(this.ckbGrandTotal);
        this.TabularGroup.Controls.Add(this.label5);
        this.TabularGroup.Controls.Add(this.label4);
        this.TabularGroup.Controls.Add(this.cbColumnList);
        this.TabularGroup.Location = new System.Drawing.Point(4, 22);
        this.TabularGroup.Name = "TabularGroup";
        this.TabularGroup.Size = new System.Drawing.Size(520, 300);
        this.TabularGroup.TabIndex = 7;
        this.TabularGroup.Tag = "group";
        this.TabularGroup.Text = "Grouping";
        //
        // clbSubtotal
        //
        this.clbSubtotal.CheckOnClick = true;
        this.clbSubtotal.Location = new System.Drawing.Point(232, 32);
        this.clbSubtotal.Name = "clbSubtotal";
        this.clbSubtotal.Size = new System.Drawing.Size(192, 139);
        this.clbSubtotal.TabIndex = 5;
        this.clbSubtotal.SelectedIndexChanged += new System.EventHandler(this.emptyReportSyntax);
        //
        // ckbGrandTotal
        //
        this.ckbGrandTotal.Location = new System.Drawing.Point(16, 88);
        this.ckbGrandTotal.Name = "ckbGrandTotal";
        this.ckbGrandTotal.Size = new System.Drawing.Size(160, 24);
        this.ckbGrandTotal.TabIndex = 4;
        this.ckbGrandTotal.Text = "Calculate grand totals";
        this.ckbGrandTotal.CheckedChanged += new System.EventHandler(this.emptyReportSyntax);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(232, 16);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(208, 23);
        this.label5.TabIndex = 2;
        this.label5.Text = "Check columns you want to subtotal";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(16, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(216, 16);
        this.label4.TabIndex = 1;
        this.label4.Text = "Pick a column to group (create hierarchy)";
        //
        // cbColumnList
        //
        this.cbColumnList.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbColumnList.Location = new System.Drawing.Point(16, 32);
        this.cbColumnList.Name = "cbColumnList";
        this.cbColumnList.Size = new System.Drawing.Size(200, 21);
        this.cbColumnList.TabIndex = 0;
        this.cbColumnList.SelectedIndexChanged += new System.EventHandler(this.emptyReportSyntax);
        //
        // ReportSyntax
        //
        this.ReportSyntax.Controls.Add(this.tbReportSyntax);
        this.ReportSyntax.Location = new System.Drawing.Point(4, 22);
        this.ReportSyntax.Name = "ReportSyntax";
        this.ReportSyntax.Size = new System.Drawing.Size(520, 300);
        this.ReportSyntax.TabIndex = 4;
        this.ReportSyntax.Tag = "syntax";
        this.ReportSyntax.Text = "Report Syntax";
        //
        // tbReportSyntax
        //
        this.tbReportSyntax.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tbReportSyntax.Location = new System.Drawing.Point(0, 0);
        this.tbReportSyntax.Multiline = true;
        this.tbReportSyntax.Name = "tbReportSyntax";
        this.tbReportSyntax.ReadOnly = true;
        this.tbReportSyntax.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tbReportSyntax.Size = new System.Drawing.Size(520, 300);
        this.tbReportSyntax.TabIndex = 0;
        this.tbReportSyntax.Text = "";
        this.tbReportSyntax.WordWrap = false;
        //
        // ReportPreview
        //
        this.ReportPreview.Controls.Add(this.rdlViewer1);
        this.ReportPreview.Location = new System.Drawing.Point(4, 22);
        this.ReportPreview.Name = "ReportPreview";
        this.ReportPreview.Size = new System.Drawing.Size(520, 300);
        this.ReportPreview.TabIndex = 5;
        this.ReportPreview.Tag = "preview";
        this.ReportPreview.Text = "Report Preview";
        //
        // rdlViewer1
        //
        this.rdlViewer1.Cursor = System.Windows.Forms.Cursors.Default;
        this.rdlViewer1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.rdlViewer1.setFolder(null);
        this.rdlViewer1.Location = new System.Drawing.Point(0, 0);
        this.rdlViewer1.Name = "rdlViewer1";
        this.rdlViewer1.setParameters(null);
        this.rdlViewer1.setReportName(null);
        this.rdlViewer1.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.Continuous);
        this.rdlViewer1.setShowParameterPanel(true);
        this.rdlViewer1.Size = new System.Drawing.Size(520, 300);
        this.rdlViewer1.setSourceFile(null);
        this.rdlViewer1.setSourceRdl(null);
        this.rdlViewer1.TabIndex = 0;
        this.rdlViewer1.Text = "rdlViewer1";
        this.rdlViewer1.setZoom(0.5947548F);
        this.rdlViewer1.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitWidth);
        //
        // btnCancel
        //
        this.btnCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.btnCancel.CausesValidation = false;
        this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.btnCancel.Location = new System.Drawing.Point(440, 10);
        this.btnCancel.Name = "btnCancel";
        this.btnCancel.TabIndex = 0;
        this.btnCancel.Text = "Cancel";
        //
        // panel1
        //
        this.panel1.Controls.Add(this.btnOK);
        this.panel1.Controls.Add(this.btnCancel);
        this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panel1.Location = new System.Drawing.Point(0, 326);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(528, 40);
        this.panel1.TabIndex = 3;
        //
        // btnOK
        //
        this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.btnOK.Location = new System.Drawing.Point(344, 10);
        this.btnOK.Name = "btnOK";
        this.btnOK.TabIndex = 1;
        this.btnOK.Text = "OK";
        this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.rbSchema2005);
        this.groupBox2.Controls.Add(this.rbSchema2003);
        this.groupBox2.Controls.Add(this.rbSchemaNo);
        this.groupBox2.Location = new System.Drawing.Point(16, 256);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(384, 40);
        this.groupBox2.TabIndex = 9;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "RDL Schema";
        //
        // rbSchemaNo
        //
        this.rbSchemaNo.Location = new System.Drawing.Point(8, 16);
        this.rbSchemaNo.Name = "rbSchemaNo";
        this.rbSchemaNo.Size = new System.Drawing.Size(104, 16);
        this.rbSchemaNo.TabIndex = 0;
        this.rbSchemaNo.Text = "None";
        //
        // rbSchema2003
        //
        this.rbSchema2003.Location = new System.Drawing.Point(120, 16);
        this.rbSchema2003.Name = "rbSchema2003";
        this.rbSchema2003.Size = new System.Drawing.Size(104, 16);
        this.rbSchema2003.TabIndex = 1;
        this.rbSchema2003.Text = "2003";
        //
        // rbSchema2005
        //
        this.rbSchema2005.Checked = true;
        this.rbSchema2005.Location = new System.Drawing.Point(248, 16);
        this.rbSchema2005.Name = "rbSchema2005";
        this.rbSchema2005.Size = new System.Drawing.Size(104, 16);
        this.rbSchema2005.TabIndex = 2;
        this.rbSchema2005.TabStop = true;
        this.rbSchema2005.Text = "2005";
        //
        // DialogDatabase
        //
        this.AcceptButton = this.btnOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.btnCancel;
        this.ClientSize = new System.Drawing.Size(528, 366);
        this.Controls.Add(this.tcDialog);
        this.Controls.Add(this.panel1);
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogDatabase";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "New Report from Database";
        this.Closed += new System.EventHandler(this.DialogDatabase_Closed);
        this.tcDialog.ResumeLayout(false);
        this.ReportType.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.DBConnection.ResumeLayout(false);
        this.ReportParameters.ResumeLayout(false);
        this.DBSql.ResumeLayout(false);
        this.panel2.ResumeLayout(false);
        this.TabularGroup.ResumeLayout(false);
        this.ReportSyntax.ResumeLayout(false);
        this.ReportPreview.ResumeLayout(false);
        this.panel1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public String getResultReport() throws Exception {
        return _ResultReport;
    }

    private void btnOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!doReportSyntax())
            return ;
         
        DialogResult = DialogResult.OK;
        _ResultReport = tbReportSyntax.Text;
        this.Close();
    }

    private void tabControl1_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        TabControl tc = (TabControl)sender;
        String tag = (String)tc.TabPages[tc.SelectedIndex].Tag;
        System.String __dummyScrutVar0 = tag;
        if (__dummyScrutVar0.equals("type"))
        {
        }
        else // nothing to do here
        if (__dummyScrutVar0.equals("connect"))
        {
        }
        else // nothing to do here
        if (__dummyScrutVar0.equals("sql"))
        {
            // obtain table and column information
            doSqlSchema();
        }
        else if (__dummyScrutVar0.equals("group"))
        {
            // obtain group by information using connection & sql
            doGrouping();
        }
        else if (__dummyScrutVar0.equals("syntax"))
        {
            // obtain report using connection, sql,
            doReportSyntax();
        }
        else if (__dummyScrutVar0.equals("preview"))
        {
            // run report using generated report syntax
            doReportPreview();
        }
        else
        {
        }      
    }

    // Fill out tvTablesColumns
    private void doSqlSchema() throws Exception {
        // TODO be more efficient and remember schema info;
        //   need to mark changes to connections
        if (tvTablesColumns.Nodes.Count > 0)
            return ;
         
        // suppress redraw until tree view is complete
        tvTablesColumns.BeginUpdate();
        // Get the schema information
        List<SqlSchemaInfo> si = DesignerUtility.getSchemaInfo(getDataProvider(),getDataConnection());
        TreeNode ndRoot = new TreeNode("Tables");
        tvTablesColumns.Nodes.Add(ndRoot);
        boolean bView = false;
        for (Object __dummyForeachVar0 : si)
        {
            SqlSchemaInfo ssi = (SqlSchemaInfo)__dummyForeachVar0;
            if (!bView && StringSupport.equals(ssi.getType(), "VIEW"))
            {
                // Switch over to views
                ndRoot = new TreeNode("Views");
                tvTablesColumns.Nodes.Add(ndRoot);
                bView = true;
            }
             
            // Add the node to the tree
            TreeNode aRoot = new TreeNode(ssi.getName());
            ndRoot.Nodes.Add(aRoot);
            aRoot.Nodes.Add("");
        }
        // Now do parameters
        if (lbParameters.Items.Count > 0)
        {
            ndRoot = new TreeNode("Parameters");
            tvTablesColumns.Nodes.Add(ndRoot);
            for (Object __dummyForeachVar1 : lbParameters.Items)
            {
                ReportParm rp = (ReportParm)__dummyForeachVar1;
                String paramName = new String();
                // force the name to start with @
                if (rp.getName()[0] == '@')
                    paramName = rp.getName();
                else
                    paramName = "@" + rp.getName(); 
                // Add the node to the tree
                TreeNode aRoot = new TreeNode(paramName);
                ndRoot.Nodes.Add(aRoot);
            }
        }
         
        tvTablesColumns.EndUpdate();
    }

    private void doGrouping() throws Exception {
        if (cbColumnList.Items.Count > 0)
            return ;
         
        // We already have the columns?
        if (_ColumnList == null)
            _ColumnList = DesignerUtility.GetSqlColumns(getDataProvider(), getDataConnection(), tbSQL.Text, this.lbParameters.Items);
         
        for (Object __dummyForeachVar2 : _ColumnList)
        {
            SqlColumn sq = (SqlColumn)__dummyForeachVar2;
            cbColumnList.Items.Add(sq);
            clbSubtotal.Items.Add(sq);
        }
        SqlColumn sqc = new SqlColumn();
        sqc.setName("");
        cbColumnList.Items.Add(sqc);
        return ;
    }

    private boolean doReportSyntax() throws Exception {
        String template = new String();
        if (rbList.Checked)
            template = _TemplateList;
        else if (rbTable.Checked)
            template = _TemplateTable;
        else if (rbMatrix.Checked)
            template = _TemplateMatrix;
        else if (rbChart.Checked)
            template = _TemplateChart;
        else
            template = _TemplateTable;    
        // default to table- should never reach
        if (_ColumnList == null)
            _ColumnList = DesignerUtility.GetSqlColumns(getDataProvider(), getDataConnection(), tbSQL.Text, this.lbParameters.Items);
         
        if (_ColumnList.Count == 0)
            return false;
         
        // can only happen by an error
        String[] parts = template.Split('|');
        StringBuilder sb = new StringBuilder(template.Length);
        double left = 0m;
        double width = new double();
        double bodyHeight = 0;
        String name = new String();
        int skip = 0;
        // skip is used to allow nesting of ifdef
        String args = new String();
        String canGrow = new String();
        String align = new String();
        // handle the group by column
        String gbcolumn = new String();
        if (this.cbColumnList.Text.Length > 0)
            gbcolumn = GetFieldName(this.cbColumnList.Text);
        else
            gbcolumn = null; 
        CultureInfo cinfo = new CultureInfo("", false);
        for (Object __dummyForeachVar9 : parts)
        {
            String p = (String)__dummyForeachVar9;
            // Handle conditional special
            if (StringSupport.equals(p.Substring(0, 5), "ifdef"))
            {
                args = p.Substring(6);
                System.String __dummyScrutVar1 = args;
                if (__dummyScrutVar1.equals("reportname"))
                {
                    if (tbReportName.Text.Length == 0)
                        skip++;
                     
                }
                else if (__dummyScrutVar1.equals("description"))
                {
                    if (tbReportDescription.Text.Length == 0)
                        skip++;
                     
                }
                else if (__dummyScrutVar1.equals("author"))
                {
                    if (tbReportAuthor.Text.Length == 0)
                        skip++;
                     
                }
                else if (__dummyScrutVar1.equals("grouping"))
                {
                    if (gbcolumn == null)
                        skip++;
                     
                }
                else if (__dummyScrutVar1.equals("footers"))
                {
                    if (!this.ckbGrandTotal.Checked)
                        skip++;
                    else if (this.clbSubtotal.CheckedItems.Count <= 0)
                        skip++;
                      
                }
                else
                {
                    throw new Exception(String.Format("Unknown ifdef element {0} specified in template.", args));
                }     
                continue;
            }
             
            // if skipping lines (due to ifdef) then go to next endif
            if (skip > 0 && !StringSupport.equals(p, "endif"))
                continue;
             
            System.String __dummyScrutVar2 = p;
            if (__dummyScrutVar2.equals("endif"))
            {
                if (skip > 0)
                    skip--;
                 
            }
            else if (__dummyScrutVar2.equals("schema"))
            {
                if (this.rbSchema2003.Checked)
                    sb.Append(_Schema2003);
                else if (this.rbSchema2005.Checked)
                    sb.Append(_Schema2005);
                  
            }
            else if (__dummyScrutVar2.equals("reportname"))
            {
                sb.Append(tbReportName.Text.Replace('\'', '_'));
            }
            else if (__dummyScrutVar2.equals("reportnameasis"))
            {
                sb.Append(tbReportName.Text);
            }
            else if (__dummyScrutVar2.equals("description"))
            {
                sb.Append(tbReportDescription.Text);
            }
            else if (__dummyScrutVar2.equals("author"))
            {
                sb.Append(tbReportAuthor.Text);
            }
            else if (__dummyScrutVar2.equals("connectionproperties"))
            {
                if (StringSupport.equals(this.cbConnectionTypes.Text, SHARED_CONNECTION))
                {
                    String file = this.tbConnection.Text;
                    file = Path.GetFileNameWithoutExtension(file);
                    sb.AppendFormat("<DataSourceReference>{0}</DataSourceReference>", file);
                }
                else
                    sb.AppendFormat("<ConnectionProperties><DataProvider>{0}</DataProvider><ConnectString>{1}</ConnectString></ConnectionProperties>", getDataProvider(), getDataConnection()); 
            }
            else if (__dummyScrutVar2.equals("dataprovider"))
            {
                sb.Append(getDataProvider());
            }
            else if (__dummyScrutVar2.equals("connectstring"))
            {
                sb.Append(tbConnection.Text);
            }
            else if (__dummyScrutVar2.equals("columncount"))
            {
                sb.Append(_ColumnList.Count);
            }
            else if (__dummyScrutVar2.equals("orientation"))
            {
                if (this.cbOrientation.SelectedIndex == 0)
                {
                    // Portrait is first in the list
                    sb.Append("<PageHeight>11in</PageHeight><PageWidth>8.5in</PageWidth>");
                }
                else
                {
                    sb.Append("<PageHeight>8.5in</PageHeight><PageWidth>11in</PageWidth>");
                } 
            }
            else if (__dummyScrutVar2.equals("groupbycolumn"))
            {
                sb.Append(gbcolumn);
            }
            else if (__dummyScrutVar2.equals("reportparameters"))
            {
                doReportSyntaxParameters(cinfo,sb);
            }
            else if (__dummyScrutVar2.equals("queryparameters"))
            {
                DoReportSyntaxQParameters(cinfo, sb, tbSQL.Text);
            }
            else if (__dummyScrutVar2.equals("sqltext"))
            {
                sb.Append(tbSQL.Text.Replace("<", "&lt;"));
            }
            else if (__dummyScrutVar2.equals("sqlfields"))
            {
                for (Object __dummyForeachVar3 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar3;
                    name = getFieldName(sq.getName());
                    String type = sq.getDataType().FullName;
                    if (this.rbSchemaNo.Checked)
                        sb.AppendFormat(cinfo, "<Field Name='{0}'><DataField>{1}</DataField><TypeName>{2}</TypeName></Field>", name, sq.getName(), type);
                    else
                        sb.AppendFormat(cinfo, "<Field Name='{0}'><DataField>{1}</DataField><rd:TypeName>{2}</rd:TypeName></Field>", name, sq.getName(), type); 
                }
            }
            else if (__dummyScrutVar2.equals("listheaders"))
            {
                left = .0m;
                for (Object __dummyForeachVar4 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar4;
                    name = sq.getName();
                    width = name.Length / 8m;
                    if (width < 1)
                        width = 1;
                     
                    sb.AppendFormat(cinfo, "\r\n" + 
                    "\t\t<Textbox><Top>.3in</Top><Left>{0}in</Left><Width>{1}in</Width><Height>.2in</Height><Value>{2}</Value>\r\n" + 
                    "\t\t\t<Style><FontWeight>Bold</FontWeight><BorderStyle><Bottom>Solid</Bottom></BorderStyle>\r\n" + 
                    "\t\t\t\t<BorderWidth><Bottom>3pt</Bottom></BorderWidth></Style>\r\n" + 
                    "\t\t</Textbox>", left, width, name);
                    left += width;
                }
            }
            else if (__dummyScrutVar2.equals("listvalues"))
            {
                left = .0m;
                for (Object __dummyForeachVar5 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar5;
                    name = getFieldName(sq.getName());
                    RefSupport<String> refVar___0 = new RefSupport<String>();
                    RefSupport<String> refVar___1 = new RefSupport<String>();
                    doAlignAndCanGrow(sq.getDataType(),refVar___0,refVar___1);
                    canGrow = refVar___0.getValue();
                    align = refVar___1.getValue();
                    width = name.Length / 8m;
                    if (width < 1)
                        width = 1;
                     
                    sb.AppendFormat(cinfo, "\r\n" + 
                    "\t\t<Textbox Name=\'{2}\'><Top>.1in</Top><Left>{0}in</Left><Width>{1}in</Width><Height>.25in</Height><Value>=Fields!{2}.Value</Value><CanGrow>{3}</CanGrow><Style>{4}</Style></Textbox>", left, width, name, canGrow, align);
                    left += width;
                }
                bodyHeight = .4m;
            }
            else if (__dummyScrutVar2.equals("listwidth"))
            {
                // in template list width must follow something that sets left
                sb.AppendFormat(cinfo, "{0}in", left);
            }
            else if (__dummyScrutVar2.equals("tableheaders"))
            {
                // the group by column is always the first one in the table
                if (gbcolumn != null)
                {
                    bodyHeight += 12m;
                    sb.AppendFormat(cinfo, "\r\n" + 
                    "\t\t\t\t\t\t\t<TableCell>\r\n" + 
                    "\t\t\t\t\t\t\t\t<ReportItems><Textbox><Value>{0}</Value><Style><TextAlign>Center</TextAlign><BorderStyle><Default>Solid</Default></BorderStyle><FontWeight>Bold</FontWeight></Style></Textbox></ReportItems>\r\n" + 
                    "\t\t\t\t\t\t\t</TableCell>", this.cbColumnList.Text);
                }
                 
                bodyHeight += 12m;
                for (Object __dummyForeachVar6 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar6;
                    name = sq.getName();
                    if (StringSupport.equals(name, this.cbColumnList.Text))
                        continue;
                     
                    sb.AppendFormat(cinfo, "\r\n" + 
                    "\t\t\t\t\t\t\t<TableCell>\r\n" + 
                    "\t\t\t\t\t\t\t\t<ReportItems><Textbox><Value>{0}</Value><Style><TextAlign>Center</TextAlign><BorderStyle><Default>Solid</Default></BorderStyle><FontWeight>Bold</FontWeight></Style></Textbox></ReportItems>\r\n" + 
                    "\t\t\t\t\t\t\t</TableCell>", name);
                }
            }
            else if (__dummyScrutVar2.equals("tablecolumns"))
            {
                if (gbcolumn != null)
                {
                    bodyHeight += 12m;
                    width = gbcolumn.Length / 8m;
                    // TODO should really use data value
                    if (width < 1)
                        width = 1;
                     
                    sb.AppendFormat(cinfo, "<TableColumn><Width>{0}in</Width></TableColumn>", width);
                }
                 
                bodyHeight += 12m;
                for (Object __dummyForeachVar7 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar7;
                    name = getFieldName(sq.getName());
                    if (StringSupport.equals(name, gbcolumn))
                        continue;
                     
                    width = name.Length / 8m;
                    // TODO should really use data value
                    if (width < 1)
                        width = 1;
                     
                    sb.AppendFormat(cinfo, "<TableColumn><Width>{0}in</Width></TableColumn>", width);
                }
            }
            else if (__dummyScrutVar2.equals("tablevalues"))
            {
                bodyHeight += 12m;
                if (gbcolumn != null)
                {
                    sb.Append("<TableCell>\r\n" + 
                    "\t\t\t\t\t\t\t\t<ReportItems><Textbox><Value></Value><Style><BorderStyle><Default>None</Default><Left>Solid</Left></BorderStyle></Style></Textbox></ReportItems>\r\n" + 
                    "\t\t\t\t\t\t\t</TableCell>");
                }
                 
                for (Object __dummyForeachVar8 : _ColumnList)
                {
                    SqlColumn sq = (SqlColumn)__dummyForeachVar8;
                    name = getFieldName(sq.getName());
                    if (StringSupport.equals(name, gbcolumn))
                        continue;
                     
                    RefSupport<String> refVar___2 = new RefSupport<String>();
                    RefSupport<String> refVar___3 = new RefSupport<String>();
                    doAlignAndCanGrow(sq.getDataType(),refVar___2,refVar___3);
                    canGrow = refVar___2.getValue();
                    align = refVar___3.getValue();
                    sb.AppendFormat(cinfo, "\r\n" + 
                    "\t\t\t\t\t\t\t<TableCell>\r\n" + 
                    "\t\t\t\t\t\t\t\t<ReportItems><Textbox Name=\'{0}\'><Value>=Fields!{0}.Value</Value><CanGrow>{1}</CanGrow><Style><BorderStyle><Default>Solid</Default></BorderStyle>{2}</Style></Textbox></ReportItems>\r\n" + 
                    "\t\t\t\t\t\t\t</TableCell>", name, canGrow, align);
                }
            }
            else if (__dummyScrutVar2.equals("gtablefooters") || __dummyScrutVar2.equals("tablefooters"))
            {
                bodyHeight += 12m;
                canGrow = "false";
                align = "";
                String nameprefix = StringSupport.equals(p, "gtablefooters") ? "gf" : "tf";
                if (gbcolumn != null)
                {
                    // handle group by column first
                    int i = clbSubtotal.FindStringExact(this.cbColumnList.Text);
                    SqlColumn sq = i < 0 ? null : (SqlColumn)clbSubtotal.Items[i];
                    if (i >= 0 && clbSubtotal.GetItemChecked(i))
                    {
                        String funct = DesignerUtility.isNumeric(sq.getDataType()) ? "Sum" : "Count";
                        RefSupport<String> refVar___4 = new RefSupport<String>();
                        RefSupport<String> refVar___5 = new RefSupport<String>();
                        DoAlignAndCanGrow(((Object)0).GetType(), refVar___4, refVar___5);
                        canGrow = refVar___4.getValue();
                        align = refVar___5.getValue();
                        sb.AppendFormat(cinfo, "\r\n" + 
                        "\t\t\t\t\t\t\t<TableCell>\r\n" + 
                        "\t\t\t\t\t\t\t\t<ReportItems><Textbox Name=\'{4}_{0}\'><Value>={1}(Fields!{0}.Value)</Value><CanGrow>{2}</CanGrow><Style><BorderStyle><Default>Solid</Default></BorderStyle>{3}</Style></Textbox></ReportItems>\r\n" + 
                        "\t\t\t\t\t\t\t</TableCell>", gbcolumn, funct, canGrow, align, nameprefix);
                    }
                    else
                    {
                        sb.AppendFormat(cinfo, "<TableCell><ReportItems><Textbox><Value></Value><Style><BorderStyle><Default>Solid</Default></BorderStyle></Style></Textbox></ReportItems></TableCell>");
                    } 
                }
                 
                for (int i = 0;i < this.clbSubtotal.Items.Count;i++)
                {
                    SqlColumn sq = (SqlColumn)clbSubtotal.Items[i];
                    name = getFieldName(sq.getName());
                    if (StringSupport.equals(name, gbcolumn))
                        continue;
                     
                    if (clbSubtotal.GetItemChecked(i))
                    {
                        String funct = DesignerUtility.isNumeric(sq.getDataType()) ? "Sum" : "Count";
                        RefSupport<String> refVar___6 = new RefSupport<String>();
                        RefSupport<String> refVar___7 = new RefSupport<String>();
                        DoAlignAndCanGrow(((Object)0).GetType(), refVar___6, refVar___7);
                        canGrow = refVar___6.getValue();
                        align = refVar___7.getValue();
                        sb.AppendFormat(cinfo, "\r\n" + 
                        "\t\t\t\t\t\t\t<TableCell>\r\n" + 
                        "\t\t\t\t\t\t\t\t<ReportItems><Textbox Name=\'{4}_{0}\'><Value>={1}(Fields!{0}.Value)</Value><CanGrow>{2}</CanGrow><Style><BorderStyle><Default>Solid</Default></BorderStyle>{3}</Style></Textbox></ReportItems>\r\n" + 
                        "\t\t\t\t\t\t\t</TableCell>", name, funct, canGrow, align, nameprefix);
                    }
                    else
                    {
                        sb.AppendFormat(cinfo, "<TableCell><ReportItems><Textbox><Value></Value><Style><BorderStyle><Default>Solid</Default></BorderStyle></Style></Textbox></ReportItems></TableCell>");
                    } 
                }
            }
            else if (__dummyScrutVar2.equals("bodyheight"))
            {
                // Note: this must follow the table definition
                sb.AppendFormat(cinfo, "{0}pt", bodyHeight);
            }
            else
            {
                sb.Append(p);
            }                        
        }
        try
        {
            tbReportSyntax.Text = DesignerUtility.FormatXml(sb.ToString());
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Internal Error");
            tbReportSyntax.Text = sb.ToString();
        }

        return true;
    }

    private String getFieldName(String sqlName) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Object __dummyForeachVar10 : sqlName)
        {
            char c = (Character)__dummyForeachVar10;
            if (Char.IsLetterOrDigit(c) || c == '_')
                sb.Append(c);
            else
                sb.Append('_'); 
        }
        return sb.ToString();
    }

    private void doAlignAndCanGrow(Type t, RefSupport<String> canGrow, RefSupport<String> align) throws Exception {
        String st = t.ToString();
        System.String __dummyScrutVar3 = st;
        if (__dummyScrutVar3.equals("System.String"))
        {
            canGrow.setValue("true");
            align.setValue("<PaddingLeft>2 pt</PaddingLeft>");
        }
        else if (__dummyScrutVar3.equals("System.Int16") || __dummyScrutVar3.equals("System.Int32") || __dummyScrutVar3.equals("System.Single") || __dummyScrutVar3.equals("System.Double") || __dummyScrutVar3.equals("System.Decimal"))
        {
            canGrow.setValue("false");
            align.setValue("<PaddingRight>2 pt</PaddingRight><TextAlign>right</TextAlign>");
        }
        else
        {
            canGrow.setValue("false");
            align.setValue("<PaddingLeft>2 pt</PaddingLeft>");
        }  
        return ;
    }

    private void doReportSyntaxParameters(CultureInfo cinfo, StringBuilder sb) throws Exception {
        if (this.lbParameters.Items.Count <= 0)
            return ;
         
        sb.Append("<ReportParameters>");
        for (Object __dummyForeachVar13 : lbParameters.Items)
        {
            ReportParm rp = (ReportParm)__dummyForeachVar13;
            sb.AppendFormat(cinfo, "<ReportParameter Name=\"{0}\">", rp.getName());
            sb.AppendFormat(cinfo, "<DataType>{0}</DataType>", rp.getDataType());
            sb.AppendFormat(cinfo, "<Nullable>{0}</Nullable>", rp.getAllowNull().ToString());
            if (rp.getDefaultValue() != null && rp.getDefaultValue().Count > 0)
            {
                sb.AppendFormat(cinfo, "<DefaultValue><Values>");
                for (Object __dummyForeachVar11 : rp.getDefaultValue())
                {
                    String dv = (String)__dummyForeachVar11;
                    sb.AppendFormat(cinfo, "<Value>{0}</Value>", XmlUtil.xmlAnsi(dv));
                }
                sb.AppendFormat(cinfo, "</Values></DefaultValue>");
            }
             
            sb.AppendFormat(cinfo, "<AllowBlank>{0}</AllowBlank>", rp.getAllowBlank());
            if (rp.getPrompt() != null && rp.getPrompt().Length > 0)
                sb.AppendFormat(cinfo, "<Prompt>{0}</Prompt>", rp.getPrompt());
             
            if (rp.getValidValues() != null && rp.getValidValues().Count > 0)
            {
                sb.Append("<ValidValues><ParameterValues>");
                for (Object __dummyForeachVar12 : rp.getValidValues())
                {
                    ParameterValueItem pvi = (ParameterValueItem)__dummyForeachVar12;
                    sb.Append("<ParameterValue>");
                    sb.AppendFormat(cinfo, "<Value>{0}</Value>", XmlUtil.xmlAnsi(pvi.Value));
                    if (pvi.Label != null)
                        sb.AppendFormat(cinfo, "<Label>{0}</Label>", XmlUtil.xmlAnsi(pvi.Label));
                     
                    sb.Append("</ParameterValue>");
                }
                sb.Append("</ParameterValues></ValidValues>");
            }
             
            sb.Append("</ReportParameter>");
        }
        sb.Append("</ReportParameters>");
    }

    private void doReportSyntaxQParameters(CultureInfo cinfo, StringBuilder sb, String sql) throws Exception {
        if (this.lbParameters.Items.Count <= 0)
            return ;
         
        boolean bFirst = true;
        for (Object __dummyForeachVar14 : lbParameters.Items)
        {
            ReportParm rp = (ReportParm)__dummyForeachVar14;
            // force the name to start with @
            String paramName = new String();
            if (rp.getName()[0] == '@')
                paramName = rp.getName();
            else
                paramName = "@" + rp.getName(); 
            // Only create a query parameter if parameter is used in the query
            if (sql.IndexOf(paramName) >= 0)
            {
                if (bFirst)
                {
                    // Only put out queryparameters if we actually have one
                    sb.Append("<QueryParameters>");
                    bFirst = false;
                }
                 
                sb.AppendFormat(cinfo, "<QueryParameter Name=\"{0}\">", rp.getName());
                sb.AppendFormat(cinfo, "<Value>=Parameters!{0}</Value>", rp.getName());
                sb.Append("</QueryParameter>");
            }
             
        }
        if (!bFirst)
            sb.Append("</QueryParameters>");
         
    }

    private boolean doReportPreview() throws Exception {
        if (tbReportSyntax.Text.Length < 1)
        {
            if (!doReportSyntax())
                return false;
             
        }
         
        rdlViewer1.setSourceRdl(tbReportSyntax.Text);
        return true;
    }

    private String getDataProvider() throws Exception {
        String cType = cbConnectionTypes.Text;
        _StashConnection = null;
        if (StringSupport.equals(cType, SHARED_CONNECTION))
        {
            String pswd = null;
            String xml = "";
            try
            {
                pswd = _rDesigner.getPassword();
                if (pswd == null)
                    return null;
                 
                xml = DataSourceReference.Retrieve(tbConnection.Text, pswd);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show("Unable to open shared connection, password or file is invalid.", "Test Connection");
                _rDesigner.resetPassword();
                return null;
            }

            // make sure to prompt again for the password
            XmlDocument xDoc = new XmlDocument();
            xDoc.LoadXml(xml);
            XmlNode xNodeLoop = xDoc.FirstChild;
            for (Object __dummyForeachVar15 : xNodeLoop.ChildNodes)
            {
                XmlNode node = (XmlNode)__dummyForeachVar15;
                Name __dummyScrutVar4 = node.Name;
                if (__dummyScrutVar4.equals("DataProvider"))
                {
                    cType = node.InnerText;
                }
                else if (__dummyScrutVar4.equals("ConnectString"))
                {
                    _StashConnection = node.InnerText;
                }
                else
                {
                }  
            }
        }
        else
        {
            _StashConnection = tbConnection.Text;
        } 
        return cType;
    }

    private String getDataConnection() throws Exception {
        return _StashConnection;
    }

    // GetDataProvider must be called first to ensure the DataConnection is correct.
    private void tvTablesColumns_BeforeExpand(Object sender, System.Windows.Forms.TreeViewCancelEventArgs e) throws Exception {
        tvTablesColumns_ExpandTable(e.Node);
    }

    private void tvTablesColumns_ExpandTable(TreeNode tNode) throws Exception {
        if (tNode.Parent == null)
            return ;
         
        // Check for Tables or Views
        if (!StringSupport.equals(tNode.FirstNode.Text, ""))
            return ;
         
        // Have we already filled it out?
        // Need to obtain the column information for the requested table/view
        // suppress redraw until tree view is complete
        tvTablesColumns.BeginUpdate();
        String sql = "SELECT * FROM " + NormalizeName(tNode.Text);
        List<SqlColumn> tColumns = DesignerUtility.getSqlColumns(getDataProvider(),getDataConnection(),sql,null);
        boolean bFirstTime = true;
        for (Object __dummyForeachVar16 : tColumns)
        {
            SqlColumn sc = (SqlColumn)__dummyForeachVar16;
            if (bFirstTime)
            {
                bFirstTime = false;
                tNode.FirstNode.Text = sc.getName();
            }
            else
                tNode.Nodes.Add(sc.getName()); 
        }
        tvTablesColumns.EndUpdate();
    }

    private void tbSQL_DragEnter(Object sender, System.Windows.Forms.DragEventArgs e) throws Exception {
        if (e.Data.GetDataPresent(DataFormats.Text))
            // only accept text
            e.Effect = DragDropEffects.Copy;
         
    }

    private void tbSQL_DragDrop(Object sender, System.Windows.Forms.DragEventArgs e) throws Exception {
        if (e.Data.GetDataPresent(DataFormats.Text))
            tbSQL.SelectedText = (String)e.Data.GetData(DataFormats.Text);
         
    }

    private void tvTablesColumns_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        TreeNode node = tvTablesColumns.GetNodeAt(e.X, e.Y);
        if (node == null || node.Parent == null)
            return ;
         
        String dragText = new String();
        if (StringSupport.equals(tbSQL.Text, ""))
        {
            if (node.Parent.Parent == null)
            {
                // select table; generate full select for table
                tvTablesColumns_ExpandTable(node);
                // make sure we've obtained the columns
                dragText = "SELECT ";
                TreeNode next = node.FirstNode;
                while (true)
                {
                    dragText += NormalizeName(next.Text);
                    next = next.NextNode;
                    if (next == null)
                        break;
                     
                    dragText += ", ";
                }
                dragText += (" FROM " + NormalizeName(node.Text));
            }
            else
            {
                // select column; generate select of that column
                dragText = "SELECT " + NormalizeName(node.Text) + " FROM " + NormalizeName(node.Parent.Text);
            } 
        }
        else
            dragText = node.Text; 
        tvTablesColumns.DoDragDrop(dragText, DragDropEffects.Copy);
    }

    private String normalizeName(String name) throws Exception {
        // Routine ensures valid sql name
        boolean bLetterOrDigit = true;
        for (int i = 0;i < name.Length && bLetterOrDigit;i++)
        {
            if (name[i] == '.')
            {
            }
            else // allow names to have a "." for owner qualified tables
            if (!Char.IsLetterOrDigit(name, i))
                bLetterOrDigit = false;
              
        }
        if (bLetterOrDigit)
            return name;
        else
            return "\"" + name + "\""; 
    }

    private void dialogDatabase_Closed(Object sender, System.EventArgs e) throws Exception {
        if (_TempFileName != null)
            File.Delete(_TempFileName);
         
    }

    private void tbSQL_TextChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
        // when SQL changes get rid of report syntax
        _ColumnList = null;
        // get rid of any column list as well
        cbColumnList.Items.Clear();
        // and clear out other places where columns show
        cbColumnList.Text = "";
        clbSubtotal.Items.Clear();
    }

    private void tbReportName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void tbReportDescription_TextChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void tbReportAuthor_TextChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void rbTable_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
        // when SQL changes get rid of report syntax
        if (rbTable.Checked)
        {
            TabularGroup.Enabled = true;
        }
        else
        {
            TabularGroup.Enabled = false;
        } 
    }

    private void rbList_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void rbMatrix_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void rbChart_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // when SQL changes get rid of report syntax
    private void bAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ReportParm rp = new ReportParm("newparm");
        int cur = this.lbParameters.Items.Add(rp);
        lbParameters.SelectedIndex = cur;
        this.tbParmName.Focus();
    }

    private void bRemove_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        lbParameters.Items.RemoveAt(cur);
        if (lbParameters.Items.Count <= 0)
            return ;
         
        cur--;
        if (cur < 0)
            cur = 0;
         
        lbParameters.SelectedIndex = cur;
    }

    private void lbParameters_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        tbParmName.Text = rp.getName();
        cbParmType.Text = rp.getDataType();
        tbParmPrompt.Text = rp.getPrompt();
        tbParmDefaultValue.Text = rp.getDefaultValueDisplay();
        ckbParmAllowBlank.Checked = rp.getAllowBlank();
        tbParmValidValues.Text = rp.getValidValuesDisplay();
        ckbParmAllowNull.Checked = rp.getAllowNull();
    }

    private void lbParameters_MoveItem(int curloc, int newloc) throws Exception {
        ReportParm rp = lbParameters.Items[curloc] instanceof ReportParm ? (ReportParm)lbParameters.Items[curloc] : (ReportParm)null;
        if (rp == null)
            return ;
         
        lbParameters.BeginUpdate();
        lbParameters.Items.RemoveAt(curloc);
        lbParameters.Items.Insert(newloc, rp);
        lbParameters.SelectedIndex = newloc;
        lbParameters.EndUpdate();
    }

    private void tbParmName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        if (StringSupport.equals(rp.getName(), tbParmName.Text))
            return ;
         
        rp.setName(tbParmName.Text);
        // text doesn't change in listbox; force change by removing and re-adding item
        lbParameters_MoveItem(cur,cur);
    }

    private void cbParmType_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setDataType(cbParmType.Text);
    }

    private void tbParmPrompt_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setPrompt(tbParmPrompt.Text);
    }

    private void ckbParmAllowNull_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setAllowNull(ckbParmAllowNull.Checked);
    }

    private void ckbParmAllowBlank_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        rp.setAllowBlank(ckbParmAllowBlank.Checked);
    }

    private void bParmUp_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur <= 0)
            return ;
         
        lbParameters_MoveItem(cur,cur - 1);
    }

    private void bParmDown_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur + 1 >= lbParameters.Items.Count)
            return ;
         
        lbParameters_MoveItem(cur,cur + 1);
    }

    private void tbParmDefaultValue_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        if (tbParmDefaultValue.Text.Length > 0)
        {
            if (rp.getDefaultValue() == null)
                rp.setDefaultValue(new List<String>());
            else
                rp.getDefaultValue().Clear(); 
            rp.getDefaultValue().Add(tbParmDefaultValue.Text);
        }
        else
            rp.setDefaultValue(null); 
    }

    private void tbConnection_TextChanged(Object sender, System.EventArgs e) throws Exception {
        tvTablesColumns.Nodes.Clear();
    }

    private void emptyReportSyntax(Object sender, System.EventArgs e) throws Exception {
        tbReportSyntax.Text = "";
    }

    // need to generate another report
    private void bMove_Click(Object sender, System.EventArgs e) throws Exception {
        if (tvTablesColumns.SelectedNode == null || tvTablesColumns.SelectedNode.Parent == null)
            return ;
         
        // this is the Tables/Views node
        TreeNode node = tvTablesColumns.SelectedNode;
        String t = node.Text;
        if (StringSupport.equals(tbSQL.Text, ""))
        {
            if (node.Parent.Parent == null)
            {
                // select table; generate full select for table
                tvTablesColumns_ExpandTable(node);
                // make sure we've obtained the columns
                StringBuilder sb = new StringBuilder("SELECT ");
                TreeNode next = node.FirstNode;
                while (true)
                {
                    sb.Append(NormalizeName(next.Text));
                    next = next.NextNode;
                    if (next == null)
                        break;
                     
                    sb.Append(", ");
                }
                sb.Append(" FROM ");
                sb.Append(NormalizeName(node.Text));
                t = sb.ToString();
            }
            else
            {
                // select column; generate select of that column
                t = "SELECT " + NormalizeName(node.Text) + " FROM " + NormalizeName(node.Parent.Text);
            } 
        }
         
        tbSQL.SelectedText = t;
    }

    private void bValidValues_Click(Object sender, System.EventArgs e) throws Exception {
        int cur = lbParameters.SelectedIndex;
        if (cur < 0)
            return ;
         
        ReportParm rp = lbParameters.Items[cur] instanceof ReportParm ? (ReportParm)lbParameters.Items[cur] : (ReportParm)null;
        if (rp == null)
            return ;
         
        DialogValidValues dvv = new DialogValidValues(rp.getValidValues());
        if (dvv.ShowDialog() != DialogResult.OK)
            return ;
         
        rp.setValidValues(dvv.getValidValues());
        this.tbParmValidValues.Text = rp.getValidValuesDisplay();
    }

    private void cbConnectionTypes_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(cbConnectionTypes.Text, SHARED_CONNECTION))
        {
            this.lConnection.Text = "Shared Data Source File:";
            bShared.Visible = true;
        }
        else
        {
            this.lConnection.Text = "Connection:";
            bShared.Visible = false;
        } 
        if (StringSupport.equals(cbConnectionTypes.Text, "ODBC"))
        {
            lODBC.Visible = cbOdbcNames.Visible = true;
            DesignerUtility.FillOdbcNames(cbOdbcNames);
        }
        else
        {
            lODBC.Visible = cbOdbcNames.Visible = false;
        } 
    }

    private void cbOdbcNames_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        String name = "dsn=" + cbOdbcNames.Text + ";";
        this.tbConnection.Text = name;
    }

    private void bTestConnection_Click(Object sender, System.EventArgs e) throws Exception {
        String cType = getDataProvider();
        if (cType == null)
            return ;
         
        if (DesignerUtility.testConnection(cType,getDataConnection()))
            MessageBox.Show("Connection successful!", "Test Connection");
         
    }

    private void dBConnection_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (!DesignerUtility.testConnection(this.getDataConnection(),getDataConnection()))
            e.Cancel = true;
         
    }

    private void bShared_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Data source reference files (*.dsr)|*.dsr" + "All files (*.*)|*.*|";
        ofd.FilterIndex = 1;
        if (tbConnection.Text.Length > 0)
            ofd.FileName = tbConnection.Text;
        else
            ofd.FileName = "*.dsr"; 
        ofd.Title = "Specify Data Source Reference File Name";
        ofd.CheckFileExists = true;
        ofd.DefaultExt = "dsr";
        ofd.AddExtension = true;
        if (ofd.ShowDialog() == DialogResult.OK)
            tbConnection.Text = ofd.FileName;
         
    }

}


