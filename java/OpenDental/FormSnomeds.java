//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEHR;
import OpenDental.FormInfobutton;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.EhrQuarterlyKeys;
import OpenDentBusiness.Security;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormSnomeds;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormSnomeds  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public boolean IsMultiSelectMode = new boolean();
    public Snomed SelectedSnomed;
    public List<Snomed> ListSelectedSnomeds = new List<Snomed>();
    private List<Snomed> SnomedList = new List<Snomed>();
    private boolean changed = new boolean();
    private boolean _showingInfoButton = new boolean();
    //used when filling grid. for increased speed.
    private int _showingInfobuttonShift = new int();
    //used when sorting grid rows. 1 if showing, 0 if hidden
    public FormSnomeds() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSnomeds_Load(Object sender, EventArgs e) throws Exception {
        _showingInfoButton = CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton;
        _showingInfobuttonShift = (_showingInfoButton ? 1 : 0);
        if (IsSelectionMode || IsMultiSelectMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
        } 
        if (IsMultiSelectMode)
        {
            gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        }
         
        ActiveControl = textCode;
        //This check is here to prevent Snomeds from being used in non-member nations.
        List<EhrQuarterlyKey> ehrKeys = EhrQuarterlyKeys.getAllKeys();
        groupBox1.Visible = false;
        for (int i = 0;i < ehrKeys.Count;i++)
        {
            if (FormEHR.QuarterlyKeyIsValid(ehrKeys[i].YearValue.ToString(), ehrKeys[i].QuarterValue.ToString(), ehrKeys[i].PracticeName, ehrKeys[i].KeyValue))
            {
                //EHR has been valid.
                groupBox1.Visible = true;
                break;
            }
             
        }
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        if (_showingInfoButton)
        {
            //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
            col = new ODGridColumn("",18);
            //infoButton
            col.setImageList(imageListInfoButton);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn("SNOMED CT",100);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Deprecated",75,HorizontalAlignment.Center);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn("Description",500);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Used By CQM's",75);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Date Of Standard",100);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        if (textCode.Text.Contains(","))
        {
            SnomedList = Snomeds.GetByCodes(textCode.Text);
        }
        else
        {
            SnomedList = Snomeds.GetByCodeOrDescription(textCode.Text);
        } 
        if (SnomedList.Count >= 10000)
        {
            //Max number of results returned.
            MsgBox.show(this,"Too many results. Only the first 10,000 results will be shown.");
        }
         
        List<OpenDental.UI.ODGridRow> listAll = new List<OpenDental.UI.ODGridRow>();
        for (int i = 0;i < SnomedList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (_showingInfoButton)
            {
                //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
                row.getCells().add("0");
            }
             
            //index of infobutton
            row.getCells().Add(SnomedList[i].SnomedCode);
            //row.Cells.Add("");//IsActive==NotDeprecated
            row.getCells().Add(SnomedList[i].Description);
            row.getCells().Add(EhrCodes.GetMeasureIdsForCode(SnomedList[i].SnomedCode, "SNOMEDCT"));
            row.setTag(SnomedList[i]);
            //row.Cells.Add("");
            listAll.Add(row);
        }
        listAll.Sort(SortMeasuresMet);
        for (int i = 0;i < listAll.Count;i++)
        {
            gridMain.getRows().Add(listAll[i]);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowInfobutton)
        {
            return ;
        }
         
        //Security.IsAuthorized(Permissions.EhrInfoButton,true)) {
        if (e.getCol() != 0)
        {
            return ;
        }
         
        FormInfobutton FormIB = new FormInfobutton();
        FormIB.ListObjects.Add(Snomeds.GetByCode(gridMain.getRows().get___idx(e.getRow()).getCells()[1].Text));
        FormIB.ShowDialog();
    }

    /**
    * Sort function to put the codes that apply to the most number of CQM's at the top so the user can see which codes they should select.
    */
    private int sortMeasuresMet(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        //int i=(CDSPermissions.GetForUser(Security.CurUser.UserNum).ShowInfobutton?1:0);//used to accomodate infobutton column.
        //First sort by the number of measures the codes apply to in a comma delimited list
        int diff = row2.getCells()[2 + _showingInfobuttonShift].Text.Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries).Length - row1.getCells()[2 + _showingInfobuttonShift].Text.Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries).Length;
        if (diff != 0)
        {
            return diff;
        }
         
        try
        {
            return row1.getCells()[2 + _showingInfobuttonShift].Text.CompareTo(row2.getCells()[2 + _showingInfobuttonShift].Text);
        }
        catch (Exception ex)
        {
            return 0;
        }
    
    }

    //if the codes apply to the same number of CQMs, order by the code values
    //return PIn.Long(row1.Cells[2+_showingInfobuttonShift].Text).CompareTo(PIn.Long(row2.Cells[2+_showingInfobuttonShift].Text));
    //Just string compare
    //private void butImport_Click(object sender,EventArgs e) {
    //	if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"Snomed Codes will be cleared and and completely replaced with the codes in the file you are importing.  This will not damage patient records, but will reset any Snomed descriptions that had been changed.  Continue anyway?")) {
    //		return;
    //	}
    //	Cursor=Cursors.WaitCursor;
    //	OpenFileDialog Dlg=new OpenFileDialog();
    //	if(Directory.Exists(PrefC.GetString(PrefName.ExportPath))) {
    //		Dlg.InitialDirectory=PrefC.GetString(PrefName.ExportPath);
    //	}
    //	else if(Directory.Exists("C:\\")) {
    //		Dlg.InitialDirectory="C:\\";
    //	}
    //	if(Dlg.ShowDialog()!=DialogResult.OK) {
    //		Cursor=Cursors.Default;
    //		return;
    //	}
    //	if(!File.Exists(Dlg.FileName)) {
    //		Cursor=Cursors.Default;
    //		MsgBox.Show(this,"File not found");
    //		return;
    //	}
    //	string[] fields;
    //	Snomed snomed;
    //	using(StreamReader sr=new StreamReader(Dlg.FileName)) {
    //		//string line=sr.ReadLine();
    //		//Fields are: 0-id, 1-effectiveTime, 2-active, 3-moduleId, 4-conceptId, 5-languageCode, 6-typeId, 7-term, 8-caseSignificanceId
    //		fields=sr.ReadLine().Split(new string[] { "\t" },StringSplitOptions.None);
    //		if(fields.Length<8) {//We will attempt to access fields 4 - conceptId (SnomedCode) and 7 - term (Description). 0 indexed so field 7 is the 8th field.
    //			MsgBox.Show(this,"You have selected the wrong file. There should be 9 columns in this file.");
    //			return;
    //		}
    //		if(fields[4]!="conceptId" || fields[7]!="term") {//Headers in first line have the wrong names.
    //			MsgBox.Show(this,"You have selected the wrong file: \"conceptId\" and \"term\" are not columns 5 and 8.");
    //			return;//Headers are not right. Wrong file.
    //		}
    //		Cursor=Cursors.WaitCursor;
    //		Cursor=Cursors.WaitCursor;
    //		Snomeds.DeleteAll();//Last thing we do before looping through and adding new snomeds is to delete all the old snomeds.
    //		while(!sr.EndOfStream) {					//line=sr.ReadLine();
    //			//Fields are: 0-id, 1-effectiveTime, 2-active, 3-moduleId, 4-conceptId, 5-languageCode, 6-typeId, 7-term, 8-caseSignificanceId
    //			fields=sr.ReadLine().Split(new string[1] { "\t" },StringSplitOptions.None);
    //			if(fields.Length<8) {//We will attempt to access fieds 4 - conceptId (SnomedCode) and 7 - term (Description).
    //				sr.ReadLine();
    //				continue;
    //			}
    //			if(fields[6]!="900000000000003001") {//full qualified name(FQN), alternative is "900000000000013009", "Synonym"
    //				continue;//skip anything that is not an FQN
    //			}
    //			snomed=new Snomed();
    //			snomed.SnomedCode=fields[4];
    //			snomed.Description=fields[7];
    //			//snomed.DateOfStandard=DateTime.MinValue();//=PIn.Date(""+fields[1].Substring(4,2)+"/"+fields[1].Substring(6,2)+"/"+fields[1].Substring(0,4));//format from yyyyMMdd to MM/dd/yyyy
    //			//snomed.IsActive=(fields[2]=="1");//true if column equals 1, false if column equals 0 or anything else.
    //			Snomeds.Insert(snomed);
    //		}
    //	}
    //	Cursor=Cursors.Default;
    //	MsgBox.Show(this,"Import successful.");
    //}
    //private void listMain_DoubleClick(object sender,System.EventArgs e) {
    //  if(listMain.SelectedIndex==-1) {
    //    return;
    //  }
    //  if(IsSelectionMode) {
    //    SelectedSnomed=SnomedList[listMain.SelectedIndex];
    //    DialogResult=DialogResult.OK;
    //    return;
    //  }
    //  changed=true;
    //  FormSnomedEdit FormI=new FormSnomedEdit(SnomedList[listMain.SelectedIndex]);
    //  FormI.ShowDialog();
    //  if(FormI.DialogResult!=DialogResult.OK) {
    //    return;
    //  }
    //  FillGrid();
    //}
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode || IsMultiSelectMode)
        {
            SelectedSnomed = (Snomed)gridMain.getRows().get___idx(e.getRow()).getTag();
            ListSelectedSnomeds = new List<Snomed>();
            ListSelectedSnomeds.Add((Snomed)gridMain.getRows().get___idx(e.getRow()).getTag());
            DialogResult = DialogResult.OK;
            return ;
        }
         
    }

    //changed=true;
    //FormSnomedEdit FormSE=new FormSnomedEdit((Snomed)gridMain.Rows[e.Row].Tag);
    //FormSE.ShowDialog();
    //if(FormSE.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //FillGrid();
    /*private void butAdd_Click(object sender,EventArgs e) {
    			//TODO: Either change to adding a snomed code instead of an ICD9 or don't allow users to add SNOMED codes other than importing.
    			changed=true;
    			Snomed snomed=new Snomed();
    			FormSnomedEdit FormI=new FormSnomedEdit(snomed);
    			FormI.IsNew=true;
    			FormI.ShowDialog();
    			FillGrid();
    		}*/
    private void butCrossMap_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This button takes a while to run. It imports two \"cross table\" files from the desktop (Ryan's computer only.) These tables are then used to create two more tables that contain a more useful form of the data. This button will probably not be part of the release version and if so it will behave much differently. \r\n\r\n Continue?\r\n (You should select CANCEL if you don't know exactly what this button does.)"))
        {
            return ;
        }
         
        String snomedMap = "C:\\Users\\Ryan\\Downloads\\SnomedCT_Release_INT_20130131\\SnomedCT_Release_INT_20130131\\RF1Release\\CrossMaps\\ICD9\\der1_CrossMaps_ICD9_INT_20130131.txt";
        String icd9Targets = "C:\\Users\\Ryan\\Downloads\\SnomedCT_Release_INT_20130131\\SnomedCT_Release_INT_20130131\\RF1Release\\CrossMaps\\ICD9\\der1_CrossMapTargets_ICD9_INT_20130131.txt";
        Cursor = Cursors.WaitCursor;
        //This code is useful for debugging and should be identical to the code found in convertDB3.
        String command = "DROP TABLE IF EXISTS tempcrossmap";
        DataCore.nonQ(command);
        command = "CREATE TABLE tempcrossmap (\r\n" + 
        "\t\t\t\t\t\tTempcrossmapNum BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
        "\t\t\t\t\t\tSNOMEDCode VARCHAR(255) NOT NULL,\r\n" + 
        "\t\t\t\t\t\tTargetID VARCHAR(255) NOT NULL,\r\n" + 
        "\t\t\t\t\t\tMappable VARCHAR(255) NOT NULL\r\n" + 
        "\t\t\t\t\t\t) DEFAULT CHARSET=utf8";
        DataCore.nonQ(command);
        command = "DROP TABLE IF EXISTS tempicd9targets";
        DataCore.nonQ(command);
        command = "CREATE TABLE tempicd9targets (\r\n" + 
        "\t\t\t\t\t\tTempicd9targetsNum BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
        "\t\t\t\t\t\tTargetID VARCHAR(255) NOT NULL,\r\n" + 
        "\t\t\t\t\t\tICD9Codes VARCHAR(255) NOT NULL\r\n" + 
        "\t\t\t\t\t\t) DEFAULT CHARSET=utf8";
        DataCore.nonQ(command);
        //Import CrossMap Table----------------------------------------------------------------------------------------------------------
        System.IO.StreamReader sr = new System.IO.StreamReader(snomedMap);
        String[] arraySnomedMap = new String[]();
        sr.ReadLine();
        while (!sr.EndOfStream)
        {
            //skip headers
            //each loop should read exactly one line
            arraySnomedMap = sr.ReadLine().Split('\t');
            command = "INSERT INTO tempcrossmap (SNOMEDCode,TargetID,Mappable) VALUES ('" + arraySnomedMap[1] + "','" + arraySnomedMap[4] + "','" + arraySnomedMap[6] + "')";
            DataCore.nonQ(command);
        }
        //Import Target Table----------------------------------------------------------------------------------------------------------
        sr = new System.IO.StreamReader(icd9Targets);
        String[] arrayTargets = new String[]();
        sr.ReadLine();
        while (!sr.EndOfStream)
        {
            //skip headers
            //each loop should read exactly one line
            arrayTargets = sr.ReadLine().Split('\t');
            command = "INSERT INTO tempicd9targets (TargetID,ICD9Codes) VALUES ('" + arrayTargets[0] + "','" + arrayTargets[2] + "')";
            DataCore.nonQ(command);
        }
        //Import tac a code onto ICD9 codes...----------------------------------------------------------------------------------------------------------
        //command="SELECT tempcrossmap.SNOMEDCode,tempicd9targets.ICD9Codes FROM tempcrossmap,tempicd9targets WHERE tempcrossmap.TargetID=tempicd9targets.TargetID";
        //DataTable table = DataCore.GetTable(command);
        //foreach(DataRow in table.Rows){
        //}
        Cursor = Cursors.Default;
    }

    private void butMapToSnomed_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Will add SNOMED CT code to existing problems list only if the ICD9 code correlates to exactly one SNOMED CT code. If there is any ambiguity at all the code will not be added."))
        {
            return ;
        }
         
        int changeCount = 0;
        Dictionary<String, String> dictionaryIcd9ToSnomed = Snomeds.getICD9toSNOMEDDictionary();
        DiseaseDefs.refreshCache();
        for (int i = 0;i < DiseaseDefs.getListLong().Length;i++)
        {
            if (!dictionaryIcd9ToSnomed.ContainsKey(DiseaseDefs.getListLong()[i].ICD9Code))
            {
                continue;
            }
             
            DiseaseDef def = DiseaseDefs.getListLong()[i];
            if (!StringSupport.equals(def.SnomedCode, ""))
            {
                continue;
            }
             
            def.SnomedCode = dictionaryIcd9ToSnomed[def.ICD9Code];
            DiseaseDefs.update(def);
            changeCount++;
        }
        MessageBox.Show(Lan.g(this,"SNOMED CT codes added: ") + changeCount);
    }

    //		private void butRSIT_Click(object sender,EventArgs e) {//Ryan's Snomed Import Tool
    //			if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"This tool is being used by Ryan to process the raw SNOMED data. Push Cancel if you do not know what this tool is doing.")) {
    //				return;
    //			}
    //			Cursor=Cursors.WaitCursor;
    //						string command="DROP TABLE IF EXISTS snomedonly.snomedusraw";
    //						DataCore.NonQ(command);
    //						command=@"CREATE TABLE snomedonly.snomedusraw ("
    //									//snomedrawNum bigint NOT NULL auto_increment PRIMARY KEY,
    //								+@"id VarChar(20) NOT NULL,
    //									effectiveTime VarChar(8) NOT NULL,
    //									active VarChar(1) NOT NULL,
    //									moduleId VarChar(20) NOT NULL,
    //									conceptId VarChar(20) NOT NULL,
    //									languageCode VarChar(2) NOT NULL,
    //									typeId VarChar(20) NOT NULL,
    //									term text NOT NULL,
    //									caseSignificanceId VarChar(20) NOT NULL,"
    //								//INDEX(snomedrawNum),
    //								+@"INDEX(id),
    //									INDEX(active),
    //									INDEX(moduleId),
    //									INDEX(conceptId),
    //									INDEX(languageCode),
    //									INDEX(typeId),
    //									INDEX(caseSignificanceId)
    //									) DEFAULT CHARSET=utf8";
    //						DataCore.NonQ(command);
    //						//Load raw data into DB
    //						string[] lines=File.ReadAllLines(@"C:\Docs\SNOMEDUS.TXT");
    //						for(int i=1;i<lines.Length;i++) {//each loop should read exactly one line of code. and each line of code should be a unique code
    //						//foreach(string line in lines){
    //							string[] arraysnomed=lines[i].Split(new string[] { "\t" },StringSplitOptions.None);
    //							command=@"INSERT INTO snomedonly.snomedusraw VALUES (";
    //								for(int j=0;j<arraysnomed.Length;j++){
    //									command+="'"+POut.String(arraysnomed[j])+"'"+",";
    //								}
    //							command=command.Trim(',')+")";
    //							DataCore.NonQ(command);
    //						}
    //			//Manipulate here.
    //			//900000000000013009 is synonym
    //			//900000000000003001 is Fully specified name
    //			command="DROP TABLE IF EXISTS snomedonly.snomed";
    //			DataCore.NonQ(command);
    //			command=@"CREATE TABLE snomedonly.snomed (
    //						SnomedNum bigint NOT NULL auto_increment PRIMARY KEY,
    //						SnomedCode VarChar(255) NOT NULL,
    //						Description VarChar(255) NOT NULL,
    //						INDEX(SnomedCode)
    //						) DEFAULT CHARSET=utf8";
    //			DataCore.NonQ(command);
    //			//Load raw data into DB
    //			//command="INSERT INTO snomedonly.snomed (SnomedCode,Description) SELECT t.* FROM (SELECT conceptID, term FROM snomedonly.snomedusraw WHERE GROUP BY conceptid) t";
    //			command="SELECT t.* FROM (SELECT conceptID, term FROM snomedonly.snomedusraw WHERE typeid='900000000000003001' ORDER BY Cast(conceptid as unsigned) asc) t";
    //			//DataCore.NonQ(command);
    //			DataTable Table=DataCore.GetTable(command);
    //			HashSet<string> hss=new HashSet<string>();
    //			//string[]
    //			lines=File.ReadAllLines(@"C:\Docs\SNOMEDUS.TXT");
    //			for(int i=1;i<lines.Length;i++) {//each loop should read exactly one line of code. and each line of code should be a unique code
    //				//foreach(string line in lines){
    //				string[] arraysnomed=lines[i].Split(new string[] { "\t" },StringSplitOptions.None);
    //				//if(arraysnomed[6]!="900000000000003001") {
    //				//	continue;//not equal to a fully specified name.
    //				//}
    //				if(hss.Contains(arraysnomed[4])) {
    //					continue;//snomedcode already added.
    //				}
    //				hss.Add(arraysnomed[4]);
    //				command=@"INSERT INTO snomedonly.snomed VALUES ("+i+",'"+POut.String(arraysnomed[4])+"','"+POut.String(arraysnomed[7])+"')";
    //				DataCore.NonQ(command);
    //			}
    //			Cursor=Cursors.Default;
    //			MsgBox.Show(this,"Done.");
    //		}
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless IsSelectionMode
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedSnomed = (Snomed)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag();
        ListSelectedSnomeds = new List<Snomed>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            ListSelectedSnomeds.Add((Snomed)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag);
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSnomeds.class);
        this.butOK = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.textCode = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butSearch = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butMapICD9 = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.imageListInfoButton = new System.Windows.Forms.ImageList(this.components);
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(864, 625);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(864, 655);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textCode
        //
        this.textCode.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textCode.Location = new System.Drawing.Point(180, 10);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(399, 20);
        this.textCode.TabIndex = 17;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(172, 16);
        this.label1.TabIndex = 18;
        this.label1.Text = "Code(s) or Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(585, 8);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 19;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(20, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(817, 641);
        this.gridMain.TabIndex = 20;
        this.gridMain.setTitle("SNOMED CT Codes");
        this.gridMain.setTranslationName("FormSnomedctCodes");
        this.gridMain.setWrapText(false);
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
        // butMapICD9
        //
        this.butMapICD9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMapICD9.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.butMapICD9.setAutosize(true);
        this.butMapICD9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMapICD9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMapICD9.setCornerRadius(4F);
        this.butMapICD9.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butMapICD9.Location = new System.Drawing.Point(6, 19);
        this.butMapICD9.Name = "butMapICD9";
        this.butMapICD9.Size = new System.Drawing.Size(103, 24);
        this.butMapICD9.TabIndex = 24;
        this.butMapICD9.Text = "Map to ICD9";
        this.butMapICD9.Click += new System.EventHandler(this.butMapToSnomed_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.butMapICD9);
        this.groupBox1.Location = new System.Drawing.Point(843, 33);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(115, 50);
        this.groupBox1.TabIndex = 25;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "One-Time Tools";
        this.groupBox1.Visible = false;
        //
        // imageListInfoButton
        //
        this.imageListInfoButton.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListInfoButton.ImageStream")));
        this.imageListInfoButton.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListInfoButton.Images.SetKeyName(0, "iButton_16px.png");
        //
        // FormSnomeds
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(961, 691);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormSnomeds";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "SNOMED CT";
        this.Load += new System.EventHandler(this.FormSnomeds_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSearch;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butMapICD9;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ImageList imageListInfoButton = new System.Windows.Forms.ImageList();
}


