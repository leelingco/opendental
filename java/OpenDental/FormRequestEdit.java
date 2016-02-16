//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.ApprovalEnum;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ODDataRow;
import OpenDentBusiness.ODDataTable;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDental.Properties.Resources;

public class FormRequestEdit  extends Form 
{

    public long RequestId = new long();
    public boolean IsAdminMode = new boolean();
    //private Request ReqCur;
    //public bool IsNew;//might be redundant, since RequestId will be zero anyway.
    //public table for discussion
    private ODDataTable tableObj;
    private Color colorDisabled = new Color();
    private int myPointsUsed = 0;
    private int myPointsAllotted = 0;
    //private int DiscussIdCur;//only has a value during discuss edit.
    public FormRequestEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
        colorDisabled = Color.FromArgb(230, 229, 233);
    }

    private void formRequestEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsAdminMode)
        {
            textDescription.BackColor = Color.White;
            textDescription.ReadOnly = false;
            textDetail.BackColor = Color.White;
            textDetail.ReadOnly = false;
            checkIsMine.Visible = false;
            labelSubmitter.Visible = true;
            textSubmitter.Visible = true;
            textSubmitter.BackColor = colorDisabled;
            textDifficulty.BackColor = Color.White;
            textDifficulty.ReadOnly = false;
            //groupMyVotes.Visible=false;
            butJordan.Visible = true;
            labelReqId.Visible = true;
            textRequestId.Visible = true;
            textRequestId.Text = RequestId.ToString();
            //labelAdmin.Visible=true;
            //labelAdmin.Location=groupMyVotes.Location;
            //labelAdmin.Size=groupMyVotes.Size;
            textBounty.BackColor = Color.White;
            textBounty.ReadOnly = false;
        }
        else
        {
            if (RequestId == 0)
            {
                //new
                //allow them to edit their description and detail
                textDescription.BackColor = Color.White;
                textDescription.ReadOnly = false;
                textDetail.BackColor = Color.White;
                textDetail.ReadOnly = false;
                butDelete.Visible = true;
                groupMyVotes.Visible = false;
            }
            else
            {
                //later on, it will test to see if isMine, and will then allow editing.
                textDescription.BackColor = colorDisabled;
                textDetail.BackColor = colorDisabled;
            } 
            textDifficulty.BackColor = colorDisabled;
            comboApproval.Visible = false;
        } 
        if (RequestId == 0)
        {
            checkIsMine.Checked = true;
            textDifficulty.Text = "5";
            labelDiscuss.Visible = false;
            butAddDiscuss.Visible = false;
            textNote.Visible = false;
            //gridMain.Height=butDelete.Top-gridMain.Top-4;
            gridMain.Visible = false;
        }
         
        textApproval.BackColor = colorDisabled;
        comboApproval.Items.Add("New");
        comboApproval.Items.Add("NeedsClarification");
        comboApproval.Items.Add("Redundant");
        comboApproval.Items.Add("TooBroad");
        comboApproval.Items.Add("NotARequest");
        comboApproval.Items.Add("AlreadyDone");
        comboApproval.Items.Add("Obsolete");
        comboApproval.Items.Add("Approved");
        comboApproval.Items.Add("InProgress");
        comboApproval.Items.Add("Complete");
        comboApproval.SelectedIndex = 0;
        textMyPoints.Text = "0";
        textMyPledge.Text = "0";
        if (RequestId != 0)
        {
            getOneFromServer();
            fillGrid();
        }
         
        textMyPointsRemain.BackColor = colorDisabled;
        textTotalPoints.BackColor = colorDisabled;
        textTotalCritical.BackColor = colorDisabled;
        textTotalPledged.BackColor = colorDisabled;
        textWeight.BackColor = colorDisabled;
    }

    private void getOneFromServer() throws Exception {
        //get a table with data
        Cursor = Cursors.WaitCursor;
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("FeatureRequestGetOne");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("RequestId");
                writer.WriteString(RequestId.ToString());
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
            result = updateService.FeatureRequestGetOne(strbuild.ToString());
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
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //Process a valid return value------------------------------------------------------------------------------------------------
        node = doc.SelectSingleNode("//ResultTable");
        tableObj = new ODDataTable(node.InnerXml);
        ODDataRow row = tableObj.Rows[0];
        textDescription.Text = row["Description"];
        String detail = row["Detail"];
        detail = detail.Replace("\n", "\r\n");
        textDetail.Text = detail;
        checkIsMine.Checked = PIn.Bool(row["isMine"]);
        textDifficulty.Text = row["Difficulty"];
        int approval = PIn.Int(row["Approval"]);
        if (IsAdminMode)
        {
            textSubmitter.Text = row["submitter"];
        }
         
        comboApproval.SelectedIndex = approval;
        //textApproval gets set automatically due to comboApproval_SelectedIndexChanged.
        if (!IsAdminMode && PIn.Bool(row["isMine"]))
        {
            //user editing their own request
            if (ApprovalEnum.values()[approval] == ApprovalEnum.New || ApprovalEnum.values()[approval] == ApprovalEnum.NeedsClarification || ApprovalEnum.values()[approval] == ApprovalEnum.NotARequest || ApprovalEnum.values()[approval] == ApprovalEnum.Redundant || ApprovalEnum.values()[approval] == ApprovalEnum.TooBroad)
            {
                //so user not allowed to edit if Approved,AlreadyDone,Obsolete, or InProgress.
                textDescription.BackColor = Color.White;
                textDescription.ReadOnly = false;
                textDetail.BackColor = Color.White;
                textDetail.ReadOnly = false;
                if (ApprovalEnum.values()[approval] != ApprovalEnum.New)
                {
                    butResubmit.Visible = true;
                }
                 
                butDelete.Visible = true;
            }
             
        }
         
        if (ApprovalEnum.values()[approval] != ApprovalEnum.Approved)
        {
            //only allowed to vote on Approved features.
            //All others should always have zero votes, except InProgress and Complete
            groupMyVotes.Visible = false;
        }
         
        if (ApprovalEnum.values()[approval] == ApprovalEnum.Approved || ApprovalEnum.values()[approval] == ApprovalEnum.InProgress || ApprovalEnum.values()[approval] == ApprovalEnum.Complete)
        {
            //even administrators should not be able to change things at this point
            textDescription.BackColor = colorDisabled;
            textDescription.ReadOnly = true;
            textDetail.BackColor = colorDisabled;
            textDetail.ReadOnly = true;
        }
         
        myPointsUsed = PIn.Int(row["myPointsUsed"]);
        try
        {
            myPointsAllotted = PIn.Int(row["myPointsAllotted"]);
        }
        catch (Exception __dummyCatchVar0)
        {
            myPointsAllotted = 100;
        }

        //textMyPointsRemain.Text=;this will be filled automatically when myPoints changes
        textMyPoints.Text = row["myPoints"];
        recalcMyPoints();
        checkIsCritical.Checked = PIn.Bool(row["IsCritical"]);
        textMyPledge.Text = row["myPledge"];
        textTotalPoints.Text = row["totalPoints"];
        textTotalCritical.Text = row["totalCritical"];
        textTotalPledged.Text = row["totalPledged"];
        textWeight.Text = row["Weight"];
        try
        {
            textBounty.Text = row["Bounty"];
        }
        catch (Exception __dummyCatchVar1)
        {
        }
    
    }

    private void textMyPoints_TextChanged(Object sender, EventArgs e) throws Exception {
        recalcMyPoints();
    }

    private void recalcMyPoints() throws Exception {
        try
        {
            int mypoints = 0;
            if (!StringSupport.equals(textMyPoints.Text, ""))
            {
                mypoints = Convert.ToInt32(textMyPoints.Text);
            }
             
            textMyPointsRemain.Text = (myPointsAllotted - myPointsUsed - mypoints).ToString();
        }
        catch (Exception __dummyCatchVar2)
        {
            textMyPointsRemain.Text = "";
        }
    
    }

    private void butJordan_Click(Object sender, EventArgs e) throws Exception {
        textDescription.BackColor = Color.White;
        textDescription.ReadOnly = false;
        textDetail.BackColor = Color.White;
        textDetail.ReadOnly = false;
    }

    private void comboApproval_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        int approval = comboApproval.SelectedIndex;
        switch(ApprovalEnum.values()[approval])
        {
            case New: 
                textApproval.Text = "New. Awaiting review and approval.";
                break;
            case NeedsClarification: 
                textApproval.Text = "Needs Clarification.";
                break;
            case Redundant: 
                textApproval.Text = "Redundant. An identical request already exists.";
                break;
            case TooBroad: 
                textApproval.Text = "Too broad. A request must be limited to one issue.";
                break;
            case NotARequest: 
                textApproval.Text = "Not a request. Most likely a training issue.";
                break;
            case AlreadyDone: 
                textApproval.Text = "Already done. Feature already exists in the software.";
                break;
            case Obsolete: 
                textApproval.Text = "Obsolete. No longer applies to current version.";
                break;
            case Approved: 
                textApproval.Text = "Approved. Cannot be edited by user.";
                break;
            case InProgress: 
                textApproval.Text = "In Progress. Feature currently being programmed.";
                break;
            case Complete: 
                textApproval.Text = "Complete. Feature has been implemented.";
                break;
        
        }
    }

    private void butResubmit_Click(Object sender, EventArgs e) throws Exception {
        //only visible if NeedsClarification,NotARequest,Redundant,or TooBroad
        if (!MsgBox.show(this,true,"Only continue if you have revised the original request to comply better with submission guidelines."))
        {
            return ;
        }
         
        comboApproval.SelectedIndex = 0;
        butResubmit.Visible = false;
    }

    private void checkIsCritical_Click(Object sender, EventArgs e) throws Exception {
        if (checkIsCritical.Checked)
        {
            if (!MsgBox.show(this,true,"Are you sure this is really critical?  To qualify as critical, there would be no possible workarounds.  The missing feature would probably be seriously impacting the financial status of the office.  It would be serious enough that you might be considering using another software."))
            {
                checkIsCritical.Checked = false;
                return ;
            }
             
        }
         
    }

    private void butAddDiscuss_Click(Object sender, EventArgs e) throws Exception {
        //button is not even visible if New
        if (StringSupport.equals(textNote.Text, ""))
        {
            MsgBox.show(this,"Please enter some text first.");
            return ;
        }
         
        if (!saveDiscuss())
        {
            return ;
        }
         
        textNote.Text = "";
        fillGrid();
    }

    /**
    * Never happens with a new request.
    */
    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("FeatureRequestDiscussGetList");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("RequestId");
                writer.WriteString(RequestId.ToString());
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
            result = updateService.FeatureRequestDiscussGetList(strbuild.ToString());
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
         
        //Process a valid return value------------------------------------------------------------------------------------------------
        node = doc.SelectSingleNode("//ResultTable");
        ODDataTable table = new ODDataTable(node.InnerXml);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRequestDiscuss","Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRequestDiscuss","Note"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["dateTime"]);
            row.getCells().Add(table.Rows[i]["Note"]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    /**
    * 
    */
    private boolean saveDiscuss() throws Exception {
        //bool doDelete) {
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("FeatureRequestDiscussSubmit");
                //regkey
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                //DiscussId
                //writer.WriteStartElement("DiscussId");
                //writer.WriteString(DiscussIdCur.ToString());//this will be zero for a new entry. We currently only support new entries
                //writer.WriteEndElement();
                //RequestId
                writer.WriteStartElement("RequestId");
                writer.WriteString(RequestId.ToString());
                writer.WriteEndElement();
                //can't pass patnum.  Determined on the server side.
                //date will also be figured on the server side.
                //Note
                writer.WriteStartElement("Note");
                writer.WriteString(textNote.Text);
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        /*if(doDelete){
        					//delete
        					writer.WriteStartElement("Delete");
        					writer.WriteString("true");
        					writer.WriteEndElement();
        				}*/
        Cursor = Cursors.WaitCursor;
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        //Send the message and get the result-------------------------------------------------------------------------------------
        String result = "";
        try
        {
            result = updateService.FeatureRequestDiscussSubmit(strbuild.ToString());
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show("Error: " + ex.Message);
            return false;
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
            return false;
        }
         
        return true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //only visible if New,NeedsClarification,NotARequest,Redundant,or TooBroad
        if (!MsgBox.show(this,true,"Delete this entire request?"))
        {
            return ;
        }
         
        if (!saveChangesToDb(true))
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    /**
    * Only called when user clicks Delete or OK.  Not called repeatedly when adding discussions.
    */
    private boolean saveChangesToDb(boolean doDelete) throws Exception {
        //validate---------------------------------------------------------------------------------------------------------
        int difficulty = 0;
        int myPoints = 0;
        double myPledge = 0;
        double bounty = 0;
        if (!doDelete)
        {
            if (StringSupport.equals(textDescription.Text, ""))
            {
                MsgBox.show(this,"Description cannot be blank.");
                return false;
            }
             
            try
            {
                difficulty = int.Parse(textDifficulty.Text);
            }
            catch (Exception __dummyCatchVar3)
            {
                MsgBox.show(this,"Difficulty is invalid.");
                return false;
            }

            if (difficulty < 0 || difficulty > 10)
            {
                MsgBox.show(this,"Difficulty is invalid.");
                return false;
            }
             
            if (IsAdminMode)
            {
                try
                {
                    bounty = PIn.Int(textBounty.Text);
                }
                catch (Exception __dummyCatchVar4)
                {
                    MsgBox.show(this,"Bounty is invalid.");
                    return false;
                }
            
            }
             
            if (!IsAdminMode)
            {
                try
                {
                    myPoints = PIn.Int(textMyPoints.Text);
                }
                catch (Exception __dummyCatchVar5)
                {
                    //handles "" gracefully
                    MsgBox.show(this,"Points is invalid.");
                    return false;
                }

                if (difficulty < 0 || difficulty > 100)
                {
                    MsgBox.show(this,"Points is invalid.");
                    return false;
                }
                 
                //still need to validate that they have enough points.
                if (StringSupport.equals(textMyPledge.Text, ""))
                {
                    myPledge = 0;
                }
                else
                {
                    try
                    {
                        myPledge = double.Parse(textMyPledge.Text);
                    }
                    catch (Exception __dummyCatchVar6)
                    {
                        MsgBox.show(this,"Pledge is invalid.");
                        return false;
                    }
                
                } 
                if (myPledge < 0)
                {
                    MsgBox.show(this,"Pledge is invalid.");
                    return false;
                }
                 
            }
             
            double myPointsRemain = PIn.Double(textMyPointsRemain.Text);
            if (myPointsRemain < 0)
            {
                MsgBox.show(this,"You have gone over your allotted points.");
                return false;
            }
             
        }
         
        //end of validation------------------------------------------------------------------------------------------------
        //if user has made no changes, then exit out-------------------------------------------------------------------------
        boolean changesMade = false;
        if (doDelete)
        {
            changesMade = true;
        }
         
        if (tableObj == null || tableObj.Rows.Count == 0)
        {
            //new
            changesMade = true;
        }
        else
        {
            ODDataRow row = tableObj.Rows[0];
            if (textDescription.Text != row["Description"])
            {
                changesMade = true;
            }
             
            if (textDetail.Text != row["Detail"])
            {
                changesMade = true;
            }
             
            if (textDifficulty.Text != row["Difficulty"])
            {
                changesMade = true;
            }
             
            int approval = PIn.Int(row["Approval"]);
            if (comboApproval.SelectedIndex != approval)
            {
                changesMade = true;
            }
             
            if (groupMyVotes.Visible)
            {
                if (textMyPoints.Text != row["myPoints"] || checkIsCritical.Checked != PIn.Bool(row["IsCritical"]) || textMyPledge.Text != row["myPledge"])
                {
                    changesMade = true;
                }
                 
            }
             
            try
            {
                if (textBounty.Text != row["Bounty"])
                {
                    changesMade = true;
                }
                 
            }
            catch (Exception __dummyCatchVar7)
            {
            }
        
        } 
        if (!changesMade)
        {
            return true;
        }
         
        //temporarily show me which ones shortcutted out
        //MessageBox.Show("no changes made");
        Cursor = Cursors.WaitCursor;
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                writer.WriteStartElement("FeatureRequestSubmitChanges");
                //regkey
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                //requestId
                writer.WriteStartElement("RequestId");
                writer.WriteString(RequestId.ToString());
                //this will be zero for a new request.
                writer.WriteEndElement();
                if (doDelete)
                {
                    //delete
                    writer.WriteStartElement("Delete");
                    writer.WriteString("true");
                    //all the other elements will be ignored.
                    writer.WriteEndElement();
                }
                else
                {
                    if (!textDescription.ReadOnly)
                    {
                        //description
                        writer.WriteStartElement("Description");
                        writer.WriteString(textDescription.Text);
                        writer.WriteEndElement();
                    }
                     
                    if (!textDetail.ReadOnly)
                    {
                        //detail
                        writer.WriteStartElement("Detail");
                        writer.WriteString(textDetail.Text);
                        writer.WriteEndElement();
                    }
                     
                    if (IsAdminMode || RequestId == 0)
                    {
                        //This allows the initial difficulty of 5 to get saved.
                        //difficulty
                        writer.WriteStartElement("Difficulty");
                        writer.WriteString(difficulty.ToString());
                        writer.WriteEndElement();
                    }
                     
                    if (IsAdminMode)
                    {
                        //Bounty
                        writer.WriteStartElement("Bounty");
                        writer.WriteString(bounty.ToString());
                        writer.WriteEndElement();
                    }
                     
                    //approval
                    writer.WriteStartElement("Approval");
                    writer.WriteString(comboApproval.SelectedIndex.ToString());
                    writer.WriteEndElement();
                    if (!IsAdminMode)
                    {
                        //mypoints
                        writer.WriteStartElement("MyPoints");
                        writer.WriteString(myPoints.ToString());
                        writer.WriteEndElement();
                        //iscritical
                        writer.WriteStartElement("IsCritical");
                        if (checkIsCritical.Checked)
                        {
                            writer.WriteString("1");
                        }
                        else
                        {
                            writer.WriteString("0");
                        } 
                        writer.WriteEndElement();
                        //mypledge
                        writer.WriteStartElement("MyPledge");
                        writer.WriteString(myPledge.ToString("f2"));
                        writer.WriteEndElement();
                    }
                     
                } 
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
            result = updateService.FeatureRequestSubmitChanges(strbuild.ToString());
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show("Error: " + ex.Message);
            return false;
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
            return false;
        }
         
        return true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textNote.Text, ""))
        {
            MsgBox.show(this,"You need to save your note first.");
            return ;
        }
         
        if (!saveChangesToDb(false))
        {
            return ;
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
        this.label3 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.checkIsMine = new System.Windows.Forms.CheckBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textDetail = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textDifficulty = new System.Windows.Forms.TextBox();
        this.textApproval = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupMyVotes = new System.Windows.Forms.GroupBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textMyPointsRemain = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textMyPledge = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.checkIsCritical = new System.Windows.Forms.CheckBox();
        this.textMyPoints = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textWeight = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.textTotalCritical = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textTotalPledged = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.textTotalPoints = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.comboApproval = new System.Windows.Forms.ComboBox();
        this.textSubmitter = new System.Windows.Forms.TextBox();
        this.labelSubmitter = new System.Windows.Forms.Label();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.labelDiscuss = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.butAddDiscuss = new OpenDental.UI.Button();
        this.butJordan = new OpenDental.UI.Button();
        this.butResubmit = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textRequestId = new System.Windows.Forms.TextBox();
        this.labelReqId = new System.Windows.Forms.Label();
        this.textBounty = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.groupMyVotes.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 3);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(105, 18);
        this.label3.TabIndex = 60;
        this.label3.Text = "Short Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textDescription.BackColor = System.Drawing.Color.White;
        this.textDescription.Location = new System.Drawing.Point(111, 3);
        this.textDescription.Multiline = true;
        this.textDescription.Name = "textDescription";
        this.textDescription.ReadOnly = true;
        this.textDescription.Size = new System.Drawing.Size(410, 35);
        this.textDescription.TabIndex = 0;
        //
        // checkIsMine
        //
        this.checkIsMine.AutoCheck = false;
        this.checkIsMine.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsMine.Location = new System.Drawing.Point(1, 140);
        this.checkIsMine.Name = "checkIsMine";
        this.checkIsMine.Size = new System.Drawing.Size(124, 20);
        this.checkIsMine.TabIndex = 63;
        this.checkIsMine.Text = "Submitted by me";
        this.checkIsMine.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsMine.UseVisualStyleBackColor = true;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(107, 18);
        this.label2.TabIndex = 65;
        this.label2.Text = "Detail";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDetail
        //
        this.textDetail.AcceptsReturn = true;
        this.textDetail.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textDetail.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(229)))), ((int)(((byte)(233)))));
        this.textDetail.Location = new System.Drawing.Point(111, 40);
        this.textDetail.Multiline = true;
        this.textDetail.Name = "textDetail";
        this.textDetail.ReadOnly = true;
        this.textDetail.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textDetail.Size = new System.Drawing.Size(410, 98);
        this.textDetail.TabIndex = 1;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(14, 163);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(95, 18);
        this.label4.TabIndex = 66;
        this.label4.Text = "Difficulty";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDifficulty
        //
        this.textDifficulty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textDifficulty.Location = new System.Drawing.Point(111, 162);
        this.textDifficulty.Name = "textDifficulty";
        this.textDifficulty.ReadOnly = true;
        this.textDifficulty.Size = new System.Drawing.Size(38, 20);
        this.textDifficulty.TabIndex = 67;
        this.textDifficulty.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textApproval
        //
        this.textApproval.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textApproval.Location = new System.Drawing.Point(111, 184);
        this.textApproval.Name = "textApproval";
        this.textApproval.ReadOnly = true;
        this.textApproval.Size = new System.Drawing.Size(410, 20);
        this.textApproval.TabIndex = 69;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(-1, 185);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(110, 18);
        this.label5.TabIndex = 68;
        this.label5.Text = "Approval";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupMyVotes
        //
        this.groupMyVotes.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupMyVotes.Controls.Add(this.label9);
        this.groupMyVotes.Controls.Add(this.textMyPointsRemain);
        this.groupMyVotes.Controls.Add(this.label8);
        this.groupMyVotes.Controls.Add(this.textMyPledge);
        this.groupMyVotes.Controls.Add(this.label7);
        this.groupMyVotes.Controls.Add(this.checkIsCritical);
        this.groupMyVotes.Controls.Add(this.textMyPoints);
        this.groupMyVotes.Controls.Add(this.label6);
        this.groupMyVotes.Location = new System.Drawing.Point(527, 3);
        this.groupMyVotes.Name = "groupMyVotes";
        this.groupMyVotes.Size = new System.Drawing.Size(347, 120);
        this.groupMyVotes.TabIndex = 3;
        this.groupMyVotes.TabStop = false;
        this.groupMyVotes.Text = "My Votes";
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(2, 83);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(342, 34);
        this.label9.TabIndex = 75;
        this.label9.Text = "Pledges are neither required nor requested.  They are for unusual situations wher" + "e a feature is extremely important to someone.";
        //
        // textMyPointsRemain
        //
        this.textMyPointsRemain.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textMyPointsRemain.Location = new System.Drawing.Point(264, 17);
        this.textMyPointsRemain.Name = "textMyPointsRemain";
        this.textMyPointsRemain.ReadOnly = true;
        this.textMyPointsRemain.Size = new System.Drawing.Size(38, 20);
        this.textMyPointsRemain.TabIndex = 74;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(157, 18);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(105, 18);
        this.label8.TabIndex = 73;
        this.label8.Text = "Points Remaining";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMyPledge
        //
        this.textMyPledge.BackColor = System.Drawing.SystemColors.Window;
        this.textMyPledge.Location = new System.Drawing.Point(108, 57);
        this.textMyPledge.Name = "textMyPledge";
        this.textMyPledge.Size = new System.Drawing.Size(63, 20);
        this.textMyPledge.TabIndex = 1;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(4, 58);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(103, 18);
        this.label7.TabIndex = 71;
        this.label7.Text = "Pledge Amount $";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsCritical
        //
        this.checkIsCritical.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsCritical.Location = new System.Drawing.Point(26, 37);
        this.checkIsCritical.Name = "checkIsCritical";
        this.checkIsCritical.Size = new System.Drawing.Size(97, 20);
        this.checkIsCritical.TabIndex = 70;
        this.checkIsCritical.Text = "Is Critical";
        this.checkIsCritical.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsCritical.UseVisualStyleBackColor = true;
        this.checkIsCritical.Click += new System.EventHandler(this.checkIsCritical_Click);
        //
        // textMyPoints
        //
        this.textMyPoints.BackColor = System.Drawing.SystemColors.Window;
        this.textMyPoints.Location = new System.Drawing.Point(108, 16);
        this.textMyPoints.Name = "textMyPoints";
        this.textMyPoints.Size = new System.Drawing.Size(38, 20);
        this.textMyPoints.TabIndex = 0;
        this.textMyPoints.TextChanged += new System.EventHandler(this.textMyPoints_TextChanged);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(37, 17);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(69, 18);
        this.label6.TabIndex = 68;
        this.label6.Text = "Points";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox2.Controls.Add(this.textBounty);
        this.groupBox2.Controls.Add(this.label18);
        this.groupBox2.Controls.Add(this.textWeight);
        this.groupBox2.Controls.Add(this.label17);
        this.groupBox2.Controls.Add(this.label11);
        this.groupBox2.Controls.Add(this.textTotalCritical);
        this.groupBox2.Controls.Add(this.label10);
        this.groupBox2.Controls.Add(this.textTotalPledged);
        this.groupBox2.Controls.Add(this.label12);
        this.groupBox2.Controls.Add(this.textTotalPoints);
        this.groupBox2.Controls.Add(this.label13);
        this.groupBox2.Location = new System.Drawing.Point(527, 126);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(347, 80);
        this.groupBox2.TabIndex = 72;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Totals - See manual for explanations";
        //
        // textWeight
        //
        this.textWeight.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textWeight.Location = new System.Drawing.Point(264, 17);
        this.textWeight.Name = "textWeight";
        this.textWeight.ReadOnly = true;
        this.textWeight.Size = new System.Drawing.Size(38, 20);
        this.textWeight.TabIndex = 81;
        this.textWeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(301, 18);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(38, 18);
        this.label17.TabIndex = 86;
        this.label17.Text = "/100";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(169, 18);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(95, 18);
        this.label11.TabIndex = 80;
        this.label11.Text = "Weight";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTotalCritical
        //
        this.textTotalCritical.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(229)))), ((int)(((byte)(233)))));
        this.textTotalCritical.Location = new System.Drawing.Point(108, 37);
        this.textTotalCritical.Name = "textTotalCritical";
        this.textTotalCritical.ReadOnly = true;
        this.textTotalCritical.Size = new System.Drawing.Size(38, 20);
        this.textTotalCritical.TabIndex = 74;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(7, 38);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(99, 18);
        this.label10.TabIndex = 73;
        this.label10.Text = "Is Critical";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTotalPledged
        //
        this.textTotalPledged.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textTotalPledged.Location = new System.Drawing.Point(108, 57);
        this.textTotalPledged.Name = "textTotalPledged";
        this.textTotalPledged.ReadOnly = true;
        this.textTotalPledged.Size = new System.Drawing.Size(63, 20);
        this.textTotalPledged.TabIndex = 72;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(4, 58);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(103, 18);
        this.label12.TabIndex = 71;
        this.label12.Text = "Pledged $";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTotalPoints
        //
        this.textTotalPoints.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(229)))), ((int)(((byte)(233)))));
        this.textTotalPoints.Location = new System.Drawing.Point(108, 17);
        this.textTotalPoints.Name = "textTotalPoints";
        this.textTotalPoints.ReadOnly = true;
        this.textTotalPoints.Size = new System.Drawing.Size(38, 20);
        this.textTotalPoints.TabIndex = 69;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(37, 18);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(69, 18);
        this.label13.TabIndex = 68;
        this.label13.Text = "Points";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboApproval
        //
        this.comboApproval.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboApproval.FormattingEnabled = true;
        this.comboApproval.Location = new System.Drawing.Point(434, 184);
        this.comboApproval.MaxDropDownItems = 20;
        this.comboApproval.Name = "comboApproval";
        this.comboApproval.Size = new System.Drawing.Size(87, 21);
        this.comboApproval.TabIndex = 73;
        this.comboApproval.SelectedIndexChanged += new System.EventHandler(this.comboApproval_SelectedIndexChanged);
        //
        // textSubmitter
        //
        this.textSubmitter.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textSubmitter.Location = new System.Drawing.Point(111, 140);
        this.textSubmitter.Name = "textSubmitter";
        this.textSubmitter.ReadOnly = true;
        this.textSubmitter.Size = new System.Drawing.Size(309, 20);
        this.textSubmitter.TabIndex = 76;
        this.textSubmitter.Visible = false;
        //
        // labelSubmitter
        //
        this.labelSubmitter.Location = new System.Drawing.Point(47, 140);
        this.labelSubmitter.Name = "labelSubmitter";
        this.labelSubmitter.Size = new System.Drawing.Size(62, 18);
        this.labelSubmitter.TabIndex = 77;
        this.labelSubmitter.Text = "Submitter";
        this.labelSubmitter.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelSubmitter.Visible = false;
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textBox1.Location = new System.Drawing.Point(405, 162);
        this.textBox1.Name = "textBox1";
        this.textBox1.ReadOnly = true;
        this.textBox1.Size = new System.Drawing.Size(38, 20);
        this.textBox1.TabIndex = 79;
        this.textBox1.Visible = false;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(321, 163);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 18);
        this.label1.TabIndex = 78;
        this.label1.Text = "Priority  /10";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.label1.Visible = false;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(184, 162);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(143, 18);
        this.label14.TabIndex = 80;
        this.label14.Text = "(the lower the better)";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(148, 163);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(32, 18);
        this.label16.TabIndex = 85;
        this.label16.Text = "/10";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelDiscuss
        //
        this.labelDiscuss.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelDiscuss.Location = new System.Drawing.Point(211, 537);
        this.labelDiscuss.Name = "labelDiscuss";
        this.labelDiscuss.Size = new System.Drawing.Size(655, 19);
        this.labelDiscuss.TabIndex = 86;
        this.labelDiscuss.Text = "This discussion is very leisurely.  Nobody necessarily checks it for new messages" + ".  Try to prepend your name to the note.";
        //
        // textNote
        //
        this.textNote.AcceptsReturn = true;
        this.textNote.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textNote.BackColor = System.Drawing.Color.White;
        this.textNote.Location = new System.Drawing.Point(214, 556);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(575, 89);
        this.textNote.TabIndex = 88;
        //
        // butAddDiscuss
        //
        this.butAddDiscuss.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddDiscuss.setAutosize(true);
        this.butAddDiscuss.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddDiscuss.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddDiscuss.setCornerRadius(4F);
        this.butAddDiscuss.Image = Resources.getAdd();
        this.butAddDiscuss.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddDiscuss.Location = new System.Drawing.Point(127, 556);
        this.butAddDiscuss.Name = "butAddDiscuss";
        this.butAddDiscuss.Size = new System.Drawing.Size(81, 24);
        this.butAddDiscuss.TabIndex = 87;
        this.butAddDiscuss.Text = "Save";
        this.butAddDiscuss.Click += new System.EventHandler(this.butAddDiscuss_Click);
        //
        // butJordan
        //
        this.butJordan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butJordan.setAutosize(true);
        this.butJordan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butJordan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butJordan.setCornerRadius(4F);
        this.butJordan.Location = new System.Drawing.Point(7, 96);
        this.butJordan.Name = "butJordan";
        this.butJordan.Size = new System.Drawing.Size(98, 24);
        this.butJordan.TabIndex = 84;
        this.butJordan.Text = "Jordan\'s Override";
        this.butJordan.Visible = false;
        this.butJordan.Click += new System.EventHandler(this.butJordan_Click);
        //
        // butResubmit
        //
        this.butResubmit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butResubmit.setAutosize(true);
        this.butResubmit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butResubmit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butResubmit.setCornerRadius(4F);
        this.butResubmit.Location = new System.Drawing.Point(446, 140);
        this.butResubmit.Name = "butResubmit";
        this.butResubmit.Size = new System.Drawing.Size(75, 24);
        this.butResubmit.TabIndex = 74;
        this.butResubmit.Text = "Resubmit";
        this.butResubmit.Visible = false;
        this.butResubmit.Click += new System.EventHandler(this.butResubmit_Click);
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
        this.butDelete.Location = new System.Drawing.Point(15, 638);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 75;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 208);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(775, 326);
        this.gridMain.TabIndex = 70;
        this.gridMain.setTitle("Discussion");
        this.gridMain.setTranslationName(null);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(793, 608);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 2;
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
        this.butCancel.Location = new System.Drawing.Point(793, 638);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textRequestId
        //
        this.textRequestId.Location = new System.Drawing.Point(64, 71);
        this.textRequestId.Name = "textRequestId";
        this.textRequestId.Size = new System.Drawing.Size(41, 20);
        this.textRequestId.TabIndex = 93;
        this.textRequestId.Visible = false;
        //
        // labelReqId
        //
        this.labelReqId.Location = new System.Drawing.Point(7, 71);
        this.labelReqId.Name = "labelReqId";
        this.labelReqId.Size = new System.Drawing.Size(56, 18);
        this.labelReqId.TabIndex = 92;
        this.labelReqId.Text = "Req Id";
        this.labelReqId.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelReqId.Visible = false;
        //
        // textBounty
        //
        this.textBounty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(239)))), ((int)(((byte)(243)))));
        this.textBounty.Location = new System.Drawing.Point(253, 57);
        this.textBounty.Name = "textBounty";
        this.textBounty.ReadOnly = true;
        this.textBounty.Size = new System.Drawing.Size(63, 20);
        this.textBounty.TabIndex = 76;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(179, 58);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(73, 18);
        this.label18.TabIndex = 77;
        this.label18.Text = "Bounty $";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormRequestEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(883, 668);
        this.Controls.Add(this.textRequestId);
        this.Controls.Add(this.labelReqId);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butAddDiscuss);
        this.Controls.Add(this.labelDiscuss);
        this.Controls.Add(this.textDifficulty);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.butJordan);
        this.Controls.Add(this.butResubmit);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.labelSubmitter);
        this.Controls.Add(this.checkIsMine);
        this.Controls.Add(this.textSubmitter);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.comboApproval);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupMyVotes);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.textApproval);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDetail);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormRequestEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Request";
        this.Load += new System.EventHandler(this.FormRequestEdit_Load);
        this.groupMyVotes.ResumeLayout(false);
        this.groupMyVotes.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkIsMine = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDetail = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDifficulty = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textApproval = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupMyVotes = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textMyPoints = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMyPointsRemain = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMyPledge = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsCritical = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textTotalPledged = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTotalPoints = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTotalCritical = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboApproval = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butResubmit;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textSubmitter = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelSubmitter = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textWeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butJordan;
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDiscuss = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAddDiscuss;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textRequestId = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelReqId = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBounty = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label18 = new System.Windows.Forms.Label();
}


