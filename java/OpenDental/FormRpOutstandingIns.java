//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormClaimEdit;
import OpenDental.FormRpOutstandingIns;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Claim;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Family;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;

public class FormRpOutstandingIns  extends Form 
{

    private OpenDental.UI.ODGrid gridMain;
    private CheckBox checkPreauth = new CheckBox();
    private Label labelProv = new Label();
    private OpenDental.ValidNum textDaysOldMin;
    private Label labelDaysOldMin = new Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.ValidNum textDaysOldMax;
    private Label labelDaysOldMax = new Label();
    private DateTime dateMin = new DateTime();
    private DateTime dateMax = new DateTime();
    private List<long> provNumList = new List<long>();
    private boolean isAllProv = new boolean();
    private boolean isPreauth = new boolean();
    private DataTable Table = new DataTable();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.ComboBoxMulti comboBoxMultiProv;
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private TextBox textBox1 = new TextBox();
    private OpenDental.UI.Button butExport;
    private int headingPrintH = new int();
    private OpenDental.UI.Button butRefresh;
    private double total = new double();
    public FormRpOutstandingIns() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpOutstandingIns.class);
        this.checkPreauth = new System.Windows.Forms.CheckBox();
        this.labelProv = new System.Windows.Forms.Label();
        this.labelDaysOldMin = new System.Windows.Forms.Label();
        this.labelDaysOldMax = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butRefresh = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.comboBoxMultiProv = new OpenDental.UI.ComboBoxMulti();
        this.butPrint = new OpenDental.UI.Button();
        this.textDaysOldMax = new OpenDental.ValidNum();
        this.textDaysOldMin = new OpenDental.ValidNum();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // checkPreauth
        //
        this.checkPreauth.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkPreauth.Checked = true;
        this.checkPreauth.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkPreauth.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPreauth.Location = new System.Drawing.Point(296, 9);
        this.checkPreauth.Name = "checkPreauth";
        this.checkPreauth.Size = new System.Drawing.Size(113, 18);
        this.checkPreauth.TabIndex = 51;
        this.checkPreauth.Text = "Include Preauths";
        this.checkPreauth.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkPreauth.CheckedChanged += new System.EventHandler(this.checkPreauth_CheckedChanged);
        //
        // labelProv
        //
        this.labelProv.Location = new System.Drawing.Point(412, 8);
        this.labelProv.Name = "labelProv";
        this.labelProv.Size = new System.Drawing.Size(87, 16);
        this.labelProv.TabIndex = 48;
        this.labelProv.Text = "Treat Provs";
        this.labelProv.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // labelDaysOldMin
        //
        this.labelDaysOldMin.Location = new System.Drawing.Point(9, 7);
        this.labelDaysOldMin.Name = "labelDaysOldMin";
        this.labelDaysOldMin.Size = new System.Drawing.Size(93, 18);
        this.labelDaysOldMin.TabIndex = 46;
        this.labelDaysOldMin.Text = "Days Old (min)";
        this.labelDaysOldMin.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDaysOldMax
        //
        this.labelDaysOldMax.Location = new System.Drawing.Point(163, 7);
        this.labelDaysOldMax.Name = "labelDaysOldMax";
        this.labelDaysOldMax.Size = new System.Drawing.Size(53, 18);
        this.labelDaysOldMax.TabIndex = 46;
        this.labelDaysOldMax.Text = "(max)";
        this.labelDaysOldMax.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(100, 25);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(175, 18);
        this.label1.TabIndex = 54;
        this.label1.Text = "(leave both blank to show all)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label2.Location = new System.Drawing.Point(520, 480);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(69, 18);
        this.label2.TabIndex = 46;
        this.label2.Text = "Total";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBox1
        //
        this.textBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.textBox1.Location = new System.Drawing.Point(595, 479);
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(61, 20);
        this.textBox1.TabIndex = 56;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(670, 5);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(82, 24);
        this.butRefresh.TabIndex = 58;
        this.butRefresh.Text = "&Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Image = Resources.getbutExport();
        this.butExport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExport.Location = new System.Drawing.Point(97, 476);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(79, 24);
        this.butExport.TabIndex = 57;
        this.butExport.Text = "&Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // comboBoxMultiProv
        //
        this.comboBoxMultiProv.BackColor = System.Drawing.SystemColors.Window;
        this.comboBoxMultiProv.setDroppedDown(false);
        this.comboBoxMultiProv.setItems(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiProv.Items"))));
        this.comboBoxMultiProv.Location = new System.Drawing.Point(498, 7);
        this.comboBoxMultiProv.Name = "comboBoxMultiProv";
        this.comboBoxMultiProv.setSelectedIndices(((System.Collections.ArrayList)(resources.GetObject("comboBoxMultiProv.SelectedIndices"))));
        this.comboBoxMultiProv.Size = new System.Drawing.Size(160, 21);
        this.comboBoxMultiProv.TabIndex = 53;
        this.comboBoxMultiProv.setUseCommas(true);
        this.comboBoxMultiProv.Leave += new System.EventHandler(this.comboBoxMultiProv_Leave);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(12, 476);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 24);
        this.butPrint.TabIndex = 52;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // textDaysOldMax
        //
        this.textDaysOldMax.Location = new System.Drawing.Point(219, 7);
        this.textDaysOldMax.setMaxVal(255);
        this.textDaysOldMax.setMinVal(0);
        this.textDaysOldMax.Name = "textDaysOldMax";
        this.textDaysOldMax.Size = new System.Drawing.Size(60, 20);
        this.textDaysOldMax.TabIndex = 47;
        this.textDaysOldMax.TextChanged += new System.EventHandler(this.textDaysOldMax_TextChanged);
        //
        // textDaysOldMin
        //
        this.textDaysOldMin.Location = new System.Drawing.Point(106, 7);
        this.textDaysOldMin.setMaxVal(255);
        this.textDaysOldMin.setMinVal(0);
        this.textDaysOldMin.Name = "textDaysOldMin";
        this.textDaysOldMin.Size = new System.Drawing.Size(60, 20);
        this.textDaysOldMin.TabIndex = 47;
        this.textDaysOldMin.Text = "30";
        this.textDaysOldMin.TextChanged += new System.EventHandler(this.textDaysOldMin_TextChanged);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(670, 476);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 45;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 46);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(740, 416);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle(null);
        this.gridMain.setTranslationName(null);
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
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormRpOutstandingIns
        //
        this.ClientSize = new System.Drawing.Size(764, 512);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.butExport);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.labelDaysOldMin);
        this.Controls.Add(this.comboBoxMultiProv);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.checkPreauth);
        this.Controls.Add(this.labelProv);
        this.Controls.Add(this.textDaysOldMax);
        this.Controls.Add(this.textDaysOldMin);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.labelDaysOldMax);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormRpOutstandingIns";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Outstanding Insurance Claims";
        this.Load += new System.EventHandler(this.FormRpOutIns_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRpOutIns_Load(Object sender, EventArgs e) throws Exception {
        fillProvs();
        fillGrid();
    }

    private void fillProvs() throws Exception {
        comboBoxMultiProv.getItems().Add("All");
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboBoxMultiProv.getItems().Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        comboBoxMultiProv.setSelected(0,true);
        comboBoxMultiProv.refreshText();
        isAllProv = true;
    }

    private void fillGrid() throws Exception {
        if (StringSupport.equals(textDaysOldMin.Text.Trim(), "") || PIn.Double(textDaysOldMin.Text) == 0)
        {
            dateMin = DateTime.MinValue;
        }
        else
        {
            dateMin = DateTimeOD.getToday().AddDays(-1 * PIn.Int(textDaysOldMin.Text));
        } 
        if (StringSupport.equals(textDaysOldMax.Text.Trim(), "") || PIn.Double(textDaysOldMax.Text) == 0)
        {
            dateMax = DateTime.MinValue;
        }
        else
        {
            dateMax = DateTimeOD.getToday().AddDays(-1 * PIn.Int(textDaysOldMax.Text));
        } 
        if (StringSupport.equals(comboBoxMultiProv.getSelectedIndices()[0].ToString(), "0"))
        {
            isAllProv = true;
        }
        else
        {
            isAllProv = false;
            provNumList = new List<long>();
            for (int i = 0;i < comboBoxMultiProv.getSelectedIndices().Count;i++)
            {
                provNumList.Add((long)ProviderC.getListShort()[(int)comboBoxMultiProv.getSelectedIndices()[i] - 1].ProvNum);
            }
        } 
        isPreauth = checkPreauth.Checked;
        Table = Claims.getOutInsClaims(isAllProv,provNumList,dateMin,dateMax,isPreauth);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"Carrier"),180);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Phone"),103);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Type"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Patient Name"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Date of Service"),93);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Date Sent"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Amount"), 85, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String type = new String();
        total = 0;
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Table.Rows[i]["CarrierName"].ToString());
            row.getCells().Add(Table.Rows[i]["Phone"].ToString());
            type = Table.Rows[i]["ClaimType"].ToString();
            System.String __dummyScrutVar0 = type;
            if (__dummyScrutVar0.equals("P"))
            {
                type = "Primary";
            }
            else if (__dummyScrutVar0.equals("S"))
            {
                type = "Secondary";
            }
            else if (__dummyScrutVar0.equals("PreAuth"))
            {
                type = "Preauth";
            }
            else if (__dummyScrutVar0.equals("Other"))
            {
                type = "Other";
            }
            else if (__dummyScrutVar0.equals("Cap"))
            {
                type = "Capitation";
            }
            else if (__dummyScrutVar0.equals("Med"))
            {
                type = "Medical";
            }
            else
            {
                type = "Error";
            }      
            //For possible future use.
            //Not allowed to be blank.
            row.getCells().add(type);
            if (PrefC.getBool(PrefName.ReportsShowPatNum))
            {
                row.getCells().Add(Table.Rows[i]["PatNum"].ToString() + "-" + Table.Rows[i]["LName"].ToString() + ", " + Table.Rows[i]["FName"].ToString() + " " + Table.Rows[i]["MiddleI"].ToString());
            }
            else
            {
                row.getCells().Add(Table.Rows[i]["LName"].ToString() + ", " + Table.Rows[i]["FName"].ToString() + " " + Table.Rows[i]["MiddleI"].ToString());
            } 
            DateTime dateService = PIn.Date(Table.Rows[i]["DateService"].ToString());
            if (dateService.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(dateService.ToShortDateString());
            } 
            row.getCells().Add(PIn.Date(Table.Rows[i]["DateSent"].ToString()).ToShortDateString());
            row.getCells().Add(PIn.Double(Table.Rows[i]["ClaimFee"].ToString()).ToString("c"));
            gridMain.getRows().add(row);
            total += PIn.Decimal(Table.Rows[i]["ClaimFee"].ToString());
        }
        textBox1.Text = total.ToString("c");
        gridMain.endUpdate();
    }

    private void listProv_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        Plugins.hookAddCode(this,"FormRpOutstandingIns.butRefresh_begin");
        fillGrid();
    }

    private void textDaysOldMin_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkPreauth_CheckedChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textDaysOldMax_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboBoxMultiProv_Leave(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < comboBoxMultiProv.getSelectedIndices().Count;i++)
        {
            if (StringSupport.equals(comboBoxMultiProv.getSelectedIndices()[i].ToString(), "0"))
            {
                comboBoxMultiProv.getSelectedIndices().Clear();
                comboBoxMultiProv.setSelected(0,true);
                comboBoxMultiProv.refreshText();
            }
             
        }
        if (comboBoxMultiProv.getSelectedIndices().Count == 0)
        {
            comboBoxMultiProv.getSelectedIndices().Clear();
            comboBoxMultiProv.setSelected(0,true);
            comboBoxMultiProv.refreshText();
        }
         
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        GotoModule.GotoAccount(PIn.Long(Table.Rows[e.getRow()]["PatNum"].ToString()));
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Claim claim = Claims.GetClaim(PIn.Long(Table.Rows[e.getRow()]["ClaimNum"].ToString()));
        Patient pat = Patients.getPat(claim.PatNum);
        Family fam = Patients.getFamily(pat.PatNum);
        FormClaimEdit FormCE = new FormClaimEdit(claim,pat,fam);
        FormCE.IsNew = false;
        FormCE.ShowDialog();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        /**
        * /Validating of parameters is done during RefreshGrid().
        */
        //ReportSimpleGrid report=new ReportSimpleGrid();
        //report.Query = "SELECT carrier.CarrierName,patient.HmPhone,claim.ClaimType,patient.FName,patient.LName,patient.MiddleI,patient.PatNum,claim.DateService,claim.DateSent,claim.ClaimFee,claim.ClaimNum "
        //  +"FROM carrier,patient,claim,insplan "
        //  +"WHERE carrier.CarrierNum = insplan.CarrierNum "
        //  +"AND claim.PlanNum = insplan.PlanNum "
        //  +"AND claim.PatNum = patient.PatNum "
        //  +"AND claim.ClaimStatus='S' ";
        //if(dateMin!=DateTime.MinValue) {
        //  report.Query+="AND claim.DateSent <= "+POut.Date(dateMin)+" ";
        //}
        //if(dateMax!=DateTime.MinValue) {
        //  report.Query+="AND claim.DateSent >= "+POut.Date(dateMax)+" ";
        //}
        //if(!isAllProv) {
        //  if(provNumList.Count>0) {
        //    report.Query+="AND claim.ProvBill IN (";
        //    report.Query+=""+provNumList[0];
        //    for(int i=1;i<provNumList.Count;i++) {
        //      report.Query+=","+provNumList[i];
        //    }
        //    report.Query+=") ";
        //  }
        //}
        //if(!isPreauth) {
        //  report.Query+="AND claim.ClaimType!='Preauth' ";
        //}
        //report.Query+="ORDER BY carrier.Phone,insplan.PlanNum, carrier.Phone,insplan.PlanNum";
        //FormQuery FormQuery2=new FormQuery(report);
        //FormQuery2.IsReport=true;
        //DataTable tableTemp= report.GetTempTable();
        //report.TableQ=new DataTable(null);//new table no name
        //for(int i=0;i<6;i++) {//add columns
        //  report.TableQ.Columns.Add(new System.Data.DataColumn());//blank columns
        //}
        //report.InitializeColumns();
        //for(int i=0;i<tableTemp.Rows.Count;i++) {//loop through data rows
        //  DataRow row = report.TableQ.NewRow();//create new row called 'row' based on structure of TableQ
        //  //start filling 'row'. First column is carrier:
        //  row[0]=tableTemp.Rows[i][0];
        //  row[1]=tableTemp.Rows[i][7];
        //  if(PIn.String(tableTemp.Rows[i][2].ToString())=="P")
        //    row[2]="Primary";
        //  if(PIn.String(tableTemp.Rows[i][2].ToString())=="S")
        //    row[2]="Secondary";
        //  if(PIn.String(tableTemp.Rows[i][2].ToString())=="PreAuth")
        //    row[2]="PreAuth";
        //  if(PIn.String(tableTemp.Rows[i][2].ToString())=="Other")
        //    row[2]="Other";
        //  row[3]=tableTemp.Rows[i][4];
        //  row[4]=(PIn.Date(tableTemp.Rows[i][3].ToString())).ToString("d");
        //  row[5]=PIn.Double(tableTemp.Rows[i][6].ToString()).ToString("F");
        //  //TimeSpan d = DateTime.Today.Subtract((PIn.PDate(tableTemp.Rows[i][5].ToString())));
        //  //if(d.Days>5000)
        //  //	row[4]="";
        //  //else
        //  //	row[4]=d.Days.ToString();
        //  report.ColTotal[5]+=PIn.Double(tableTemp.Rows[i][6].ToString());
        //  report.TableQ.Rows.Add(row);
        //}
        //FormQuery2.ResetGrid();//this is a method in FormQuery;
        //report.Title="OUTSTANDING INSURANCE CLAIMS";
        //report.SubTitle.Add(PrefC.GetString(PrefName.PracticeTitle));
        //report.SubTitle.Add("Sent before "+dateMin.Date.ToShortDateString());
        //report.ColPos[0]=20;
        //report.ColPos[1]=210;
        //report.ColPos[2]=330;
        //report.ColPos[3]=430;
        //report.ColPos[4]=600;
        //report.ColPos[5]=690;
        //report.ColPos[6]=770;
        //report.ColCaption[0]=Lan.g(this,"Carrier");
        //report.ColCaption[1]=Lan.g(this,"Phone");
        //report.ColCaption[2]=Lan.g(this,"Type");
        //report.ColCaption[3]=Lan.g(this,"Patient Name");
        //report.ColCaption[4]=Lan.g(this,"Date of Service");
        //report.ColCaption[5]=Lan.g(this,"Amount");
        //report.ColAlign[5]=HorizontalAlignment.Right;
        //FormQuery2.ShowDialog();
        //DialogResult=DialogResult.OK;
        pagesPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        pd.DefaultPageSettings.Landscape = false;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Outstanding insurance report printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Outstanding Insurance Claims");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            if (isPreauth)
            {
                text = "Including Preauthorization";
            }
            else
            {
                text = "Not Including Preauthorization";
            } 
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            if (isAllProv)
            {
                text = "For All Providers";
            }
            else
            {
                text = "For Providers: ";
                for (int i = 0;i < provNumList.Count;i++)
                {
                    text += Providers.GetFormalName(provNumList[i]);
                }
            } 
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
            text = "Total: $" + total.ToString("F");
            g.DrawString(text, subHeadingFont, Brushes.Black, center + gridMain.Width / 2 - g.MeasureString(text, subHeadingFont).Width - 10, yPos);
        } 
        g.Dispose();
    }

    private void butExport_Click(Object sender, System.EventArgs e) throws Exception {
        SaveFileDialog saveFileDialog = new SaveFileDialog();
        saveFileDialog.AddExtension = true;
        saveFileDialog.FileName = "Outstanding Insurance Claims";
        if (!Directory.Exists(PrefC.getString(PrefName.ExportPath)))
        {
            try
            {
                Directory.CreateDirectory(PrefC.getString(PrefName.ExportPath));
                saveFileDialog.InitialDirectory = PrefC.getString(PrefName.ExportPath);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
        else
        {
            //initialDirectory will be blank
            saveFileDialog.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        } 
        saveFileDialog.Filter = "Text files(*.txt)|*.txt|Excel Files(*.xls)|*.xls|All files(*.*)|*.*";
        saveFileDialog.FilterIndex = 0;
        if (saveFileDialog.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            StreamWriter sw = new StreamWriter(saveFileDialog.FileName, false);
            try
            {
                {
                    //new FileStream(,FileMode.Create,FileAccess.Write,FileShare.Read)))
                    String line = "";
                    line += Lan.g(this,"Carrier") + "\t" + Lan.g(this,"Phone") + "\t" + Lan.g(this,"Type") + "\t" + Lan.g(this,"Patient Name") + "\t" + Lan.g(this,"Date of Service") + "\t" + Lan.g(this,"Date Sent") + "\t" + Lan.g(this,"Amount");
                    sw.WriteLine(line);
                    for (int i = 0;i < gridMain.getRows().Count;i++)
                    {
                        line = "";
                        for (int j = 0;j < gridMain.getColumns().Count;j++)
                        {
                            line += gridMain.getRows().get___idx(i).getCells()[j].Text;
                            if (j < gridMain.getColumns().Count - 1)
                            {
                                line += "\t";
                            }
                             
                        }
                        sw.WriteLine(line);
                    }
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
            return ;
        }

        MessageBox.Show(Lan.g(this,"File created successfully"));
    }

}


