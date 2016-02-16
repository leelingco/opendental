//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:09 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormFeatureRequest;
import OpenDental.FormRequestEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.ODDataTable;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormFeatureRequest  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private IContainer components = new IContainer();
    private Label label2 = new Label();
    private Label label5 = new Label();
    private TextBox textSearch = new TextBox();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button buttonAdd;
    private Label label4 = new Label();
    private OpenDental.UI.Button butSearch;
    private ODDataTable table;
    private boolean isAdminMode = new boolean();
    /**
    * 
    */
    public FormFeatureRequest() throws Exception {
        components = new System.ComponentModel.Container();
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeatureRequest.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butSearch = new OpenDental.UI.Button();
        this.buttonAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(0, 0);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 23);
        this.label1.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(359, 7);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(511, 16);
        this.label2.TabIndex = 51;
        this.label2.Text = "Vote for your favorite features here.  Please remember that we cannot ever give a" + "ny time estimates.";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(4, 5);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(90, 18);
        this.label5.TabIndex = 56;
        this.label5.Text = "Search terms";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(93, 5);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(167, 20);
        this.textSearch.TabIndex = 57;
        //
        // label4
        //
        this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label4.Location = new System.Drawing.Point(91, 633);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(180, 18);
        this.label4.TabIndex = 61;
        this.label4.Text = "A search is required first";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label4.Visible = false;
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSearch.Location = new System.Drawing.Point(266, 2);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 62;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // buttonAdd
        //
        this.buttonAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.buttonAdd.setAutosize(true);
        this.buttonAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAdd.setCornerRadius(4F);
        this.buttonAdd.Image = Resources.getAdd();
        this.buttonAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.buttonAdd.Location = new System.Drawing.Point(12, 630);
        this.buttonAdd.Name = "buttonAdd";
        this.buttonAdd.Size = new System.Drawing.Size(75, 24);
        this.buttonAdd.TabIndex = 60;
        this.buttonAdd.Text = "Add";
        this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 28);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(861, 599);
        this.gridMain.TabIndex = 59;
        this.gridMain.setTitle("Feature Requests");
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
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormFeatureRequest
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(882, 657);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.buttonAdd);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormFeatureRequest";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Feature Requests";
        this.Load += new System.EventHandler(this.FormFeatureRequest_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormUpdate_FormClosing);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formFeatureRequest_Load(Object sender, System.EventArgs e) throws Exception {
    }

    /*
    				if(Security.IsAuthorized(Permissions.Setup,true)) {
    					butCheck2.Visible=true;
    				}
    				else {
    					textConnectionMessage.Text=Lan.g(this,"Not authorized for")+" "+GroupPermissions.GetDesc(Permissions.Setup);
    				}
    			*/
    //if(!Synch()){
    //	return;
    //}
    //FillGrid();
    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(false);
        try
        {
            fillGrid();
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"This feature won't work until you install Microsoft dotNET 3.5.");
        }
    
    }

    private void fillGrid() throws Exception {
        //if(textSearch.Text.Length<3){
        //	MsgBox.Show(this,"Please enter a search term with at least three letters in it.");
        //	return;
        //}
        Cursor = Cursors.WaitCursor;
        //Yes, this would be slicker if it were asynchronous, but no time right now.
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("FeatureRequestGetList");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("SearchString");
                writer.WriteString(textSearch.Text);
                writer.WriteEndElement();
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        //Send the message and get the result-------------------------------------------------------------------------------------
        String result = "";
        try
        {
            result = updateService.FeatureRequestGetList(strbuild.ToString());
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show("Error: " + ex.Message);
            return ;
        }

        //textConnectionMessage.Text=Lan.g(this,"Connection successful.");
        //Application.DoEvents();
        Cursor = Cursors.Default;
        //MessageBox.Show(result);
        XmlDocument doc = new XmlDocument();
        doc.LoadXml(result);
        //Process errors------------------------------------------------------------------------------------------------------------
        XmlNode node = doc.SelectSingleNode("//Error");
        if (node != null)
        {
            //textConnectionMessage.Text=node.InnerText;
            MessageBox.Show(node.InnerText, "Error");
            return ;
        }
         
        node = doc.SelectSingleNode("//KeyDisabled");
        if (node == null)
        {
            //no error, and no disabled message
            if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,false))
            {
                //this is one of two places in the program where this happens.
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
        }
        else
        {
            //textConnectionMessage.Text=node.InnerText;
            MessageBox.Show(node.InnerText);
            if (Prefs.updateBool(PrefName.RegistrationKeyIsDisabled,true))
            {
                //this is one of two places in the program where this happens.
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
            return ;
        } 
        //Process a valid return value------------------------------------------------------------------------------------------------
        node = doc.SelectSingleNode("//ResultTable");
        table = new ODDataTable(node.InnerXml);
        //Admin mode----------------------------------------------------------------------------------------------------------------
        node = doc.SelectSingleNode("//IsAdminMode");
        if (StringSupport.equals(node.InnerText, "true"))
        {
            isAdminMode = true;
        }
        else
        {
            isAdminMode = false;
        } 
        //FillGrid used to start here------------------------------------------------
        long selectedRequestId = 0;
        int selectedIndex = gridMain.getSelectedIndex();
        if (selectedIndex != -1)
        {
            if (table.Rows.Count > selectedIndex)
            {
                selectedRequestId = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["RequestId"]);
            }
             
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRequest","Req#"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Mine"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","My Votes"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Total Votes"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Diff"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Weight"),45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Approval"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequest","Description"),500);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["RequestId"]);
            row.getCells().Add(table.Rows[i]["isMine"]);
            row.getCells().Add(table.Rows[i]["myVotes"]);
            row.getCells().Add(table.Rows[i]["totalVotes"]);
            row.getCells().Add(table.Rows[i]["Difficulty"]);
            row.getCells().Add(table.Rows[i]["Weight"]);
            row.getCells().Add(table.Rows[i]["approval"]);
            row.getCells().Add(table.Rows[i]["Description"]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (selectedRequestId.ToString() == table.Rows[i]["RequestId"])
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void buttonAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"The majority of feature requests that users submit are duplicates of existing requests.  Please take the time to do a thorough search for different keywords and become familiar with similar requests before adding one of your own.  Continue?"))
        {
            return ;
        }
         
        FormRequestEdit FormR = new FormRequestEdit();
        //FormR.IsNew=true;
        FormR.IsAdminMode = isAdminMode;
        FormR.ShowDialog();
        textSearch.Text = "";
        //so we can see our new request
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormRequestEdit FormR = new FormRequestEdit();
        FormR.RequestId = PIn.Long(table.Rows[e.getRow()]["RequestId"]);
        FormR.IsAdminMode = isAdminMode;
        FormR.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formUpdate_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


