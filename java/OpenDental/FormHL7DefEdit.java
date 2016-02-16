//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormHL7DefMessageEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7DefMessages;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.HL7ShowDemographics;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.ModeTxHL7;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormHL7DefEdit;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;


/**
* 
*/
public class FormHL7DefEdit  extends System.Windows.Forms.Form 
{

    public HL7Def HL7DefCur;
    /**
    * 
    */
    public FormHL7DefEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    private void formHL7DefEdit_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
        for (int i = 0;i < Enum.GetNames(ModeTxHL7.class).Length;i++)
        {
            comboModeTx.Items.Add(Lan.g("enumModeTxHL7", Enum.GetName(ModeTxHL7.class, i).ToString()));
            if (((Enum)HL7DefCur.ModeTx).ordinal() == i)
            {
                comboModeTx.SelectedIndex = i;
            }
             
        }
        textDescription.Text = HL7DefCur.Description;
        checkInternal.Checked = HL7DefCur.IsInternal;
        checkEnabled.Checked = HL7DefCur.IsEnabled;
        textInternalType.Text = HL7DefCur.InternalType;
        textInternalTypeVersion.Text = HL7DefCur.InternalTypeVersion;
        textInPort.Text = HL7DefCur.IncomingPort;
        textInPath.Text = HL7DefCur.IncomingFolder;
        textOutPort.Text = HL7DefCur.OutgoingIpPort;
        textOutPath.Text = HL7DefCur.OutgoingFolder;
        textFieldSep.Text = HL7DefCur.FieldSeparator;
        textRepSep.Text = HL7DefCur.RepetitionSeparator;
        textCompSep.Text = HL7DefCur.ComponentSeparator;
        textSubcompSep.Text = HL7DefCur.SubcomponentSeparator;
        textEscChar.Text = HL7DefCur.EscapeCharacter;
        textNote.Text = HL7DefCur.Note;
        textHL7Server.Text = HL7DefCur.HL7Server;
        textHL7ServiceName.Text = HL7DefCur.HL7ServiceName;
        setShowRadioButtons();
        checkShowAccount.Checked = HL7DefCur.ShowAccount;
        checkShowAppts.Checked = HL7DefCur.ShowAppts;
        if (HL7DefCur.IsInternal)
        {
            if (!HL7DefCur.IsEnabled)
            {
                textDescription.ReadOnly = true;
                textInPath.ReadOnly = true;
                textOutPath.ReadOnly = true;
                textInPort.ReadOnly = true;
                textOutPort.ReadOnly = true;
                butBrowseIn.Enabled = false;
                butBrowseOut.Enabled = false;
                textFieldSep.ReadOnly = true;
                textRepSep.ReadOnly = true;
                textCompSep.ReadOnly = true;
                textSubcompSep.ReadOnly = true;
                textEscChar.ReadOnly = true;
                textHL7Server.ReadOnly = true;
                textHL7ServiceName.ReadOnly = true;
                groupShowDemographics.Enabled = false;
                checkShowAppts.Enabled = false;
                checkShowAccount.Enabled = false;
            }
             
            butAdd.Enabled = false;
            butDelete.Enabled = false;
            labelDelete.Visible = true;
        }
         
    }

    private void fillGrid() throws Exception {
        //Our strategy in this window and all sub windows is to get all data directly from the database.
        if (!HL7DefCur.IsInternal && !HL7DefCur.getIsNew())
        {
            HL7DefCur.hl7DefMessages = HL7DefMessages.getDeepFromDb(HL7DefCur.HL7DefNum);
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Message"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Seg"),35);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        if (HL7DefCur != null && HL7DefCur.hl7DefMessages != null)
        {
            for (int i = 0;i < HL7DefCur.hl7DefMessages.Count;i++)
            {
                OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
                row.getCells().Add(HL7DefCur.hl7DefMessages[i].MessageType.ToString() + "-" + HL7DefCur.hl7DefMessages[i].EventType.ToString() + ", " + HL7DefCur.hl7DefMessages[i].InOrOut.ToString());
                row.getCells().add("");
                row.getCells().Add(HL7DefCur.hl7DefMessages[i].Note);
                row.setTag(HL7DefCur.hl7DefMessages[i]);
                gridMain.getRows().add(row);
                if (HL7DefCur.hl7DefMessages[i].hl7DefSegments != null)
                {
                    for (int j = 0;j < HL7DefCur.hl7DefMessages[i].hl7DefSegments.Count;j++)
                    {
                        row = new OpenDental.UI.ODGridRow();
                        row.getCells().add("");
                        row.getCells().Add(HL7DefCur.hl7DefMessages[i].hl7DefSegments[j].SegmentName.ToString());
                        row.getCells().Add(HL7DefCur.hl7DefMessages[i].hl7DefSegments[j].Note);
                        row.setTag(HL7DefCur.hl7DefMessages[i]);
                        gridMain.getRows().add(row);
                    }
                }
                 
            }
        }
         
        gridMain.endUpdate();
    }

    /**
    * Sets radio button for the current def's ShowDemographics setting.
    */
    private void setShowRadioButtons() throws Exception {
        switch(HL7DefCur.ShowDemographics)
        {
            case Hide: 
                radioHide.Checked = true;
                break;
            case Show: 
                radioShow.Checked = true;
                break;
            case Change: 
                radioChange.Checked = true;
                break;
            case ChangeAndAdd: 
                radioChangeAndAdd.Checked = true;
                break;
        
        }
    }

    private void butBrowseIn_Click(Object sender, EventArgs e) throws Exception {
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        dlg.SelectedPath = textInPath.Text;
        if (dlg.ShowDialog() == DialogResult.OK)
        {
            textInPath.Text = dlg.SelectedPath;
        }
         
    }

    private void butBrowseOut_Click(Object sender, EventArgs e) throws Exception {
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        dlg.SelectedPath = textOutPath.Text;
        if (dlg.ShowDialog() == DialogResult.OK)
        {
            textOutPath.Text = dlg.SelectedPath;
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormHL7DefMessageEdit FormS = new FormHL7DefMessageEdit();
        FormS.HL7DefMesCur = (HL7DefMessage)gridMain.getRows().get___idx(e.getRow()).getTag();
        FormS.IsHL7DefInternal = HL7DefCur.IsInternal;
        FormS.ShowDialog();
        fillGrid();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag() == gridMain.getRows().get___idx(e.getRow()).getTag())
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Red);
            }
            else
            {
                gridMain.getRows().get___idx(i).setColorText(Color.Black);
            } 
        }
        gridMain.Invalidate();
    }

    private void checkEnabled_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (checkEnabled.Checked)
        {
            butBrowseIn.Enabled = true;
            butBrowseOut.Enabled = true;
            textInPath.ReadOnly = false;
            textInPort.ReadOnly = false;
            textOutPath.ReadOnly = false;
            textOutPort.ReadOnly = false;
            textDescription.ReadOnly = false;
            textFieldSep.ReadOnly = false;
            textRepSep.ReadOnly = false;
            textCompSep.ReadOnly = false;
            textSubcompSep.ReadOnly = false;
            textEscChar.ReadOnly = false;
            textHL7Server.ReadOnly = false;
            textHL7ServiceName.ReadOnly = false;
            groupShowDemographics.Enabled = true;
            checkShowAppts.Enabled = true;
            checkShowAccount.Enabled = true;
        }
        else
        {
            butBrowseIn.Enabled = false;
            butBrowseOut.Enabled = false;
            textInPath.ReadOnly = true;
            textInPort.ReadOnly = true;
            textOutPath.ReadOnly = true;
            textOutPort.ReadOnly = true;
            textDescription.ReadOnly = true;
            textFieldSep.ReadOnly = true;
            textRepSep.ReadOnly = true;
            textCompSep.ReadOnly = true;
            textSubcompSep.ReadOnly = true;
            textEscChar.ReadOnly = true;
            textHL7Server.ReadOnly = true;
            textHL7ServiceName.ReadOnly = true;
            groupShowDemographics.Enabled = false;
            checkShowAppts.Enabled = false;
            checkShowAccount.Enabled = false;
        } 
    }

    private void checkEnabled_Click(Object sender, EventArgs e) throws Exception {
        if (checkEnabled.Checked)
        {
            boolean isHL7Enabled = HL7Defs.isExistingHL7Enabled(HL7DefCur.HL7DefNum);
            if (isHL7Enabled)
            {
                checkEnabled.Checked = false;
                MsgBox.show(this,"Only one HL7 process can be enabled.  Another HL7 definition is enabled.");
                return ;
            }
             
            if (Programs.isEnabled(ProgramName.eClinicalWorks))
            {
                MsgBox.show(this,"The eClinicalWorks program link is enabled.  This definition will now control the HL7 messages.");
            }
             
        }
        else
        {
        } 
    }

    //
    private void comboModeTx_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (comboModeTx.SelectedIndex == 0)
        {
            textInPort.Visible = false;
            textOutPort.Visible = false;
            labelInPort.Visible = false;
            labelInPortEx.Visible = false;
            labelOutPort.Visible = false;
            labelOutPortEx.Visible = false;
            textInPath.Visible = true;
            textOutPath.Visible = true;
            labelInPath.Visible = true;
            butBrowseIn.Visible = true;
            labelOutPath.Visible = true;
            butBrowseOut.Visible = true;
            textInPort.TabStop = false;
            textOutPort.TabStop = false;
            butBrowseIn.TabStop = true;
            butBrowseOut.TabStop = true;
        }
        else if (comboModeTx.SelectedIndex == 1)
        {
            comboModeTx.SelectedIndex = 1;
            textInPort.Visible = true;
            textOutPort.Visible = true;
            labelInPort.Visible = true;
            labelInPortEx.Visible = true;
            labelOutPort.Visible = true;
            labelOutPortEx.Visible = true;
            textInPath.Visible = false;
            textOutPath.Visible = false;
            labelInPath.Visible = false;
            butBrowseIn.Visible = false;
            labelOutPath.Visible = false;
            butBrowseOut.Visible = false;
            textInPort.TabStop = true;
            textOutPort.TabStop = true;
            butBrowseIn.TabStop = false;
            butBrowseOut.TabStop = false;
        }
          
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        //This button is only enabled if this is a custom def.
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete entire HL7Def?"))
        {
            return ;
        }
         
        for (int m = 0;m < HL7DefCur.hl7DefMessages.Count;m++)
        {
            for (int s = 0;s < HL7DefCur.hl7DefMessages[m].hl7DefSegments.Count;s++)
            {
                for (int f = 0;f < HL7DefCur.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields.Count;f++)
                {
                    HL7DefFields.Delete(HL7DefCur.hl7DefMessages[m].hl7DefSegments[s].hl7DefFields[f].HL7DefFieldNum);
                }
                HL7DefSegments.Delete(HL7DefCur.hl7DefMessages[m].hl7DefSegments[s].HL7DefSegmentNum);
            }
            HL7DefMessages.Delete(HL7DefCur.hl7DefMessages[m].HL7DefMessageNum);
        }
        HL7Defs.delete(HL7DefCur.HL7DefNum);
        DataValid.setInvalid(InvalidType.HL7Defs);
        DialogResult = DialogResult.OK;
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        //This button is only enabled if this is a custom def.
        FormHL7DefMessageEdit FormS = new FormHL7DefMessageEdit();
        FormS.HL7DefMesCur = new HL7DefMessage();
        FormS.HL7DefMesCur.HL7DefNum = HL7DefCur.HL7DefNum;
        FormS.HL7DefMesCur.setIsNew(true);
        FormS.IsHL7DefInternal = false;
        FormS.ShowDialog();
        fillGrid();
    }

    /**
    * Make user enter password before allowing them to add patients through OD since this could be dangerous
    */
    private void radioAddPts_Click(Object sender, EventArgs e) throws Exception {
        InputBox pwd = new InputBox("In our online manual, on the HL7 page, look for the password and enter it below.");
        if (pwd.ShowDialog() != DialogResult.OK)
        {
            setShowRadioButtons();
            return ;
        }
         
        if (!StringSupport.equals(pwd.textResult.Text, "hl7"))
        {
            MessageBox.Show("Wrong password.");
            setShowRadioButtons();
            return ;
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //validation
        if (checkEnabled.Checked)
        {
            if (StringSupport.equals(textHL7Server.Text, ""))
            {
                MsgBox.show(this,"HL7 Server may not be blank.");
                return ;
            }
             
            if (StringSupport.equals(textHL7ServiceName.Text, ""))
            {
                MsgBox.show(this,"HL7 Service Name may not be blank.");
                return ;
            }
             
            if (comboModeTx.SelectedIndex == ((Enum)ModeTxHL7.File).ordinal())
            {
                if (StringSupport.equals(textInPath.Text, ""))
                {
                    MsgBox.show(this,"The path for Incoming Folder is empty.");
                    return ;
                }
                 
                if (!Directory.Exists(textInPath.Text))
                {
                    MsgBox.show(this,"The path for Incoming Folder is invalid.");
                    return ;
                }
                 
                if (StringSupport.equals(textOutPath.Text, ""))
                {
                    MsgBox.show(this,"The path for Outgoing Folder is empty.");
                    return ;
                }
                 
                if (!Directory.Exists(textOutPath.Text))
                {
                    MsgBox.show(this,"The path for Outgoing Folder is invalid.");
                    return ;
                }
                 
            }
            else
            {
                //TcpIp mode
                if (StringSupport.equals(textInPort.Text, ""))
                {
                    MsgBox.show(this,"The Incoming Port is empty.");
                    return ;
                }
                 
                if (StringSupport.equals(textOutPort.Text, ""))
                {
                    MsgBox.show(this,"The Outgoing IP:Port is empty.");
                    return ;
                }
                 
                String[] strIpPort = textOutPort.Text.Split(':');
                if (strIpPort.Length != 2)
                {
                    //there isn't a ':' in the IpPort field
                    MsgBox.show(this,"The Outgoing IP:Port field requires an IP address, followed by a colon, followed by a port number.");
                    return ;
                }
                 
                try
                {
                    System.Net.IPAddress.Parse(strIpPort[0]);
                }
                catch (Exception __dummyCatchVar0)
                {
                    MsgBox.show(this,"The Outgoing IP address is invalid.");
                    return ;
                }

                try
                {
                    int.Parse(strIpPort[1]);
                }
                catch (Exception __dummyCatchVar1)
                {
                    MsgBox.show(this,"The Outgoing port must be a valid integer.");
                    return ;
                }

                try
                {
                    int.Parse(textInPort.Text.ToString());
                }
                catch (Exception __dummyCatchVar2)
                {
                    MsgBox.show(this,"The Incoming port must be a valid integer.");
                    return ;
                }
            
            } 
        }
         
        HL7DefCur.HL7Server = textHL7Server.Text;
        HL7DefCur.HL7ServiceName = textHL7ServiceName.Text;
        HL7DefCur.IsInternal = checkInternal.Checked;
        HL7DefCur.InternalType = textInternalType.Text;
        HL7DefCur.InternalTypeVersion = textInternalTypeVersion.Text;
        HL7DefCur.Description = textDescription.Text;
        HL7DefCur.FieldSeparator = textFieldSep.Text;
        HL7DefCur.RepetitionSeparator = textRepSep.Text;
        HL7DefCur.ComponentSeparator = textCompSep.Text;
        HL7DefCur.SubcomponentSeparator = textSubcompSep.Text;
        HL7DefCur.EscapeCharacter = textEscChar.Text;
        HL7DefCur.Note = textNote.Text;
        HL7DefCur.ModeTx = (ModeTxHL7)comboModeTx.SelectedIndex;
        if (radioHide.Checked)
        {
            HL7DefCur.ShowDemographics = HL7ShowDemographics.Hide;
        }
        else if (radioShow.Checked)
        {
            HL7DefCur.ShowDemographics = HL7ShowDemographics.Show;
        }
        else if (radioChange.Checked)
        {
            HL7DefCur.ShowDemographics = HL7ShowDemographics.Change;
        }
        else
        {
            //must be ChangeAndAdd
            HL7DefCur.ShowDemographics = HL7ShowDemographics.ChangeAndAdd;
        }   
        HL7DefCur.ShowAccount = checkShowAccount.Checked;
        HL7DefCur.ShowAppts = checkShowAppts.Checked;
        if (comboModeTx.SelectedIndex == ((Enum)ModeTxHL7.File).ordinal())
        {
            HL7DefCur.IncomingFolder = textInPath.Text;
            HL7DefCur.OutgoingFolder = textOutPath.Text;
            HL7DefCur.IncomingPort = "";
            HL7DefCur.OutgoingIpPort = "";
        }
        else
        {
            //TcpIp mode
            HL7DefCur.IncomingPort = textInPort.Text;
            HL7DefCur.OutgoingIpPort = textOutPort.Text;
            HL7DefCur.IncomingFolder = "";
            HL7DefCur.OutgoingFolder = "";
        } 
        //save
        if (checkEnabled.Checked)
        {
            HL7DefCur.IsEnabled = true;
            if (checkInternal.Checked)
            {
                if (HL7Defs.getInternalFromDb(HL7DefCur.InternalType) == null)
                {
                    //it's not in the database.
                    HL7Defs.insert(HL7DefCur);
                }
                else
                {
                    //The user wants to enable this, so we will need to save this def to the db.
                    HL7Defs.update(HL7DefCur);
                } 
            }
            else
            {
                //all custom defs are already in the db.
                HL7Defs.update(HL7DefCur);
            } 
        }
        else
        {
            //not enabled
            if (HL7DefCur.IsInternal)
            {
                if (HL7DefCur.IsEnabled)
                {
                    //If def was enabled but user wants to disable
                    if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Disable HL7Def?  Changes made will be lost.  Continue?"))
                    {
                        HL7Defs.delete(HL7DefCur.HL7DefNum);
                    }
                    else
                    {
                        return ;
                    } 
                }
                else
                {
                    //user selected Cancel
                    //was disabled and is still disabled
                    if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Changes made will be lost.  Continue?"))
                    {
                        return ;
                    }
                     
                } 
            }
            else
            {
                //do nothing.  Changes will be lost.
                //custom
                //Disable the custom def
                HL7DefCur.IsEnabled = false;
                HL7Defs.update(HL7DefCur);
            } 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHL7DefEdit.class);
        this.label15 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.labelOutPortEx = new System.Windows.Forms.Label();
        this.labelInPortEx = new System.Windows.Forms.Label();
        this.textInternalTypeVersion = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textInternalType = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.checkEnabled = new System.Windows.Forms.CheckBox();
        this.textEscChar = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.checkInternal = new System.Windows.Forms.CheckBox();
        this.label11 = new System.Windows.Forms.Label();
        this.textSubcompSep = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textRepSep = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textCompSep = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textFieldSep = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.comboModeTx = new System.Windows.Forms.ComboBox();
        this.textOutPort = new System.Windows.Forms.TextBox();
        this.labelOutPort = new System.Windows.Forms.Label();
        this.textInPort = new System.Windows.Forms.TextBox();
        this.labelInPort = new System.Windows.Forms.Label();
        this.textOutPath = new System.Windows.Forms.TextBox();
        this.labelOutPath = new System.Windows.Forms.Label();
        this.textInPath = new System.Windows.Forms.TextBox();
        this.labelInPath = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.labelDelete = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.label17 = new System.Windows.Forms.Label();
        this.textHL7ServiceName = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.textHL7Server = new System.Windows.Forms.TextBox();
        this.label19 = new System.Windows.Forms.Label();
        this.groupShowDemographics = new System.Windows.Forms.GroupBox();
        this.label20 = new System.Windows.Forms.Label();
        this.radioChangeAndAdd = new System.Windows.Forms.RadioButton();
        this.radioChange = new System.Windows.Forms.RadioButton();
        this.radioShow = new System.Windows.Forms.RadioButton();
        this.radioHide = new System.Windows.Forms.RadioButton();
        this.checkShowAccount = new System.Windows.Forms.CheckBox();
        this.checkShowAppts = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butBrowseOut = new OpenDental.UI.Button();
        this.butBrowseIn = new OpenDental.UI.Button();
        this.groupShowDemographics.SuspendLayout();
        this.SuspendLayout();
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(196, 253);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(105, 18);
        this.label15.TabIndex = 63;
        this.label15.Text = "Default: \\";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(196, 233);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(105, 18);
        this.label6.TabIndex = 62;
        this.label6.Text = "Default: &";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label6.UseMnemonic = false;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(196, 213);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(105, 18);
        this.label5.TabIndex = 61;
        this.label5.Text = "Default: ^";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(196, 193);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(105, 18);
        this.label4.TabIndex = 60;
        this.label4.Text = "Default: ~";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(196, 173);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(105, 18);
        this.label3.TabIndex = 59;
        this.label3.Text = "Default: |";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(519, 112);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(356, 62);
        this.textNote.TabIndex = 56;
        //
        // labelOutPortEx
        //
        this.labelOutPortEx.Location = new System.Drawing.Point(307, 155);
        this.labelOutPortEx.Name = "labelOutPortEx";
        this.labelOutPortEx.Size = new System.Drawing.Size(145, 18);
        this.labelOutPortEx.TabIndex = 58;
        this.labelOutPortEx.Text = "Ex: 192.168.0.23:5846";
        this.labelOutPortEx.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelInPortEx
        //
        this.labelInPortEx.Location = new System.Drawing.Point(307, 134);
        this.labelInPortEx.Name = "labelInPortEx";
        this.labelInPortEx.Size = new System.Drawing.Size(82, 18);
        this.labelInPortEx.TabIndex = 57;
        this.labelInPortEx.Text = "Ex: 5845";
        this.labelInPortEx.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textInternalTypeVersion
        //
        this.textInternalTypeVersion.Location = new System.Drawing.Point(165, 113);
        this.textInternalTypeVersion.Name = "textInternalTypeVersion";
        this.textInternalTypeVersion.ReadOnly = true;
        this.textInternalTypeVersion.Size = new System.Drawing.Size(100, 20);
        this.textInternalTypeVersion.TabIndex = 44;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(19, 113);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(145, 18);
        this.label13.TabIndex = 39;
        this.label13.Text = "Internal Type Version";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInternalType
        //
        this.textInternalType.Location = new System.Drawing.Point(165, 93);
        this.textInternalType.Name = "textInternalType";
        this.textInternalType.ReadOnly = true;
        this.textInternalType.Size = new System.Drawing.Size(138, 20);
        this.textInternalType.TabIndex = 43;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(19, 93);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(145, 18);
        this.label14.TabIndex = 28;
        this.label14.Text = "Internal Type";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkEnabled
        //
        this.checkEnabled.Checked = true;
        this.checkEnabled.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkEnabled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkEnabled.Location = new System.Drawing.Point(33, 31);
        this.checkEnabled.Name = "checkEnabled";
        this.checkEnabled.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkEnabled.Size = new System.Drawing.Size(145, 18);
        this.checkEnabled.TabIndex = 40;
        this.checkEnabled.Text = "Enabled";
        this.checkEnabled.CheckedChanged += new System.EventHandler(this.checkEnabled_CheckedChanged);
        this.checkEnabled.Click += new System.EventHandler(this.checkEnabled_Click);
        //
        // textEscChar
        //
        this.textEscChar.Location = new System.Drawing.Point(165, 253);
        this.textEscChar.Name = "textEscChar";
        this.textEscChar.Size = new System.Drawing.Size(27, 20);
        this.textEscChar.TabIndex = 55;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(19, 253);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(145, 18);
        this.label12.TabIndex = 26;
        this.label12.Text = "Escape Character";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkInternal
        //
        this.checkInternal.Enabled = false;
        this.checkInternal.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkInternal.Location = new System.Drawing.Point(33, 13);
        this.checkInternal.Name = "checkInternal";
        this.checkInternal.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkInternal.Size = new System.Drawing.Size(145, 18);
        this.checkInternal.TabIndex = 31;
        this.checkInternal.TabStop = false;
        this.checkInternal.Text = "Internal";
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(391, 112);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(127, 18);
        this.label11.TabIndex = 32;
        this.label11.Text = "Note";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubcompSep
        //
        this.textSubcompSep.Location = new System.Drawing.Point(165, 233);
        this.textSubcompSep.Name = "textSubcompSep";
        this.textSubcompSep.Size = new System.Drawing.Size(27, 20);
        this.textSubcompSep.TabIndex = 54;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(19, 233);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(145, 18);
        this.label9.TabIndex = 30;
        this.label9.Text = "Subcomponent Separator";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRepSep
        //
        this.textRepSep.Location = new System.Drawing.Point(165, 193);
        this.textRepSep.Name = "textRepSep";
        this.textRepSep.Size = new System.Drawing.Size(27, 20);
        this.textRepSep.TabIndex = 52;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(19, 193);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(145, 18);
        this.label10.TabIndex = 27;
        this.label10.Text = "Repetition Separator";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCompSep
        //
        this.textCompSep.Location = new System.Drawing.Point(165, 213);
        this.textCompSep.Name = "textCompSep";
        this.textCompSep.Size = new System.Drawing.Size(27, 20);
        this.textCompSep.TabIndex = 53;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(19, 213);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(145, 18);
        this.label8.TabIndex = 25;
        this.label8.Text = "Component Separator";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFieldSep
        //
        this.textFieldSep.Location = new System.Drawing.Point(165, 173);
        this.textFieldSep.Name = "textFieldSep";
        this.textFieldSep.Size = new System.Drawing.Size(27, 20);
        this.textFieldSep.TabIndex = 51;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(19, 173);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(145, 18);
        this.label7.TabIndex = 36;
        this.label7.Text = "Field Separator";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboModeTx
        //
        this.comboModeTx.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboModeTx.Location = new System.Drawing.Point(165, 72);
        this.comboModeTx.MaxDropDownItems = 100;
        this.comboModeTx.Name = "comboModeTx";
        this.comboModeTx.Size = new System.Drawing.Size(138, 21);
        this.comboModeTx.TabIndex = 42;
        this.comboModeTx.SelectedIndexChanged += new System.EventHandler(this.comboModeTx_SelectedIndexChanged);
        //
        // textOutPort
        //
        this.textOutPort.Location = new System.Drawing.Point(165, 153);
        this.textOutPort.Name = "textOutPort";
        this.textOutPort.Size = new System.Drawing.Size(138, 20);
        this.textOutPort.TabIndex = 46;
        //
        // labelOutPort
        //
        this.labelOutPort.Location = new System.Drawing.Point(19, 153);
        this.labelOutPort.Name = "labelOutPort";
        this.labelOutPort.Size = new System.Drawing.Size(145, 18);
        this.labelOutPort.TabIndex = 29;
        this.labelOutPort.Text = "Outgoing IP:Port";
        this.labelOutPort.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInPort
        //
        this.textInPort.Location = new System.Drawing.Point(165, 133);
        this.textInPort.Name = "textInPort";
        this.textInPort.Size = new System.Drawing.Size(70, 20);
        this.textInPort.TabIndex = 45;
        //
        // labelInPort
        //
        this.labelInPort.Location = new System.Drawing.Point(19, 133);
        this.labelInPort.Name = "labelInPort";
        this.labelInPort.Size = new System.Drawing.Size(145, 18);
        this.labelInPort.TabIndex = 37;
        this.labelInPort.Text = "Incoming Port";
        this.labelInPort.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOutPath
        //
        this.textOutPath.Location = new System.Drawing.Point(519, 47);
        this.textOutPath.Name = "textOutPath";
        this.textOutPath.Size = new System.Drawing.Size(356, 20);
        this.textOutPath.TabIndex = 49;
        //
        // labelOutPath
        //
        this.labelOutPath.Location = new System.Drawing.Point(391, 47);
        this.labelOutPath.Name = "labelOutPath";
        this.labelOutPath.Size = new System.Drawing.Size(127, 18);
        this.labelOutPath.TabIndex = 38;
        this.labelOutPath.Text = "Outgoing Folder";
        this.labelOutPath.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInPath
        //
        this.textInPath.Location = new System.Drawing.Point(519, 18);
        this.textInPath.Name = "textInPath";
        this.textInPath.Size = new System.Drawing.Size(356, 20);
        this.textInPath.TabIndex = 47;
        //
        // labelInPath
        //
        this.labelInPath.Location = new System.Drawing.Point(391, 18);
        this.labelInPath.Name = "labelInPath";
        this.labelInPath.Size = new System.Drawing.Size(127, 18);
        this.labelInPath.TabIndex = 35;
        this.labelInPath.Text = "Incoming Folder";
        this.labelInPath.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(19, 72);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(145, 18);
        this.label2.TabIndex = 34;
        this.label2.Text = "ModeTx";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(19, 51);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(145, 18);
        this.label1.TabIndex = 33;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(165, 51);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(138, 20);
        this.textDescription.TabIndex = 41;
        //
        // labelDelete
        //
        this.labelDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelDelete.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDelete.Location = new System.Drawing.Point(108, 654);
        this.labelDelete.Name = "labelDelete";
        this.labelDelete.Size = new System.Drawing.Size(266, 28);
        this.labelDelete.TabIndex = 65;
        this.labelDelete.Text = "This HL7Def is internal. To edit this HL7Def you must first copy it to the Custom" + " list.";
        this.labelDelete.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelDelete.Visible = false;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(720, 94);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(237, 18);
        this.label16.TabIndex = 77;
        this.label16.Text = "--Typically OpenDentalHL7.";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(720, 69);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(242, 28);
        this.label17.TabIndex = 76;
        this.label17.Text = "--The computer name (not IP) where the HL7\r\n     Service is running.";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textHL7ServiceName
        //
        this.textHL7ServiceName.Location = new System.Drawing.Point(519, 92);
        this.textHL7ServiceName.Name = "textHL7ServiceName";
        this.textHL7ServiceName.Size = new System.Drawing.Size(195, 20);
        this.textHL7ServiceName.TabIndex = 75;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(348, 92);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(170, 18);
        this.label18.TabIndex = 74;
        this.label18.Text = "HL7 Service Name";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHL7Server
        //
        this.textHL7Server.Location = new System.Drawing.Point(519, 72);
        this.textHL7Server.Name = "textHL7Server";
        this.textHL7Server.Size = new System.Drawing.Size(195, 20);
        this.textHL7Server.TabIndex = 73;
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(348, 72);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(170, 18);
        this.label19.TabIndex = 72;
        this.label19.Text = "OpenDental HL7 Server";
        this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupShowDemographics
        //
        this.groupShowDemographics.Controls.Add(this.label20);
        this.groupShowDemographics.Controls.Add(this.radioChangeAndAdd);
        this.groupShowDemographics.Controls.Add(this.radioChange);
        this.groupShowDemographics.Controls.Add(this.radioShow);
        this.groupShowDemographics.Controls.Add(this.radioHide);
        this.groupShowDemographics.Location = new System.Drawing.Point(377, 215);
        this.groupShowDemographics.Name = "groupShowDemographics";
        this.groupShowDemographics.Size = new System.Drawing.Size(413, 91);
        this.groupShowDemographics.TabIndex = 78;
        this.groupShowDemographics.TabStop = false;
        this.groupShowDemographics.Text = "Show Demographics (Address, etc.)";
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(158, 53);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(251, 31);
        this.label20.TabIndex = 77;
        this.label20.Text = "Changes to patient demographic information might get overwritten by incoming HL7 " + "messages.";
        this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // radioChangeAndAdd
        //
        this.radioChangeAndAdd.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioChangeAndAdd.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioChangeAndAdd.Location = new System.Drawing.Point(26, 69);
        this.radioChangeAndAdd.Name = "radioChangeAndAdd";
        this.radioChangeAndAdd.Size = new System.Drawing.Size(130, 18);
        this.radioChangeAndAdd.TabIndex = 3;
        this.radioChangeAndAdd.TabStop = true;
        this.radioChangeAndAdd.Text = "Change and Add Pts";
        this.radioChangeAndAdd.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioChangeAndAdd.UseVisualStyleBackColor = true;
        this.radioChangeAndAdd.Click += new System.EventHandler(this.RadioAddPts_Click);
        //
        // radioChange
        //
        this.radioChange.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioChange.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioChange.Location = new System.Drawing.Point(26, 51);
        this.radioChange.Name = "radioChange";
        this.radioChange.Size = new System.Drawing.Size(130, 18);
        this.radioChange.TabIndex = 2;
        this.radioChange.TabStop = true;
        this.radioChange.Text = "Change";
        this.radioChange.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioChange.UseVisualStyleBackColor = true;
        //
        // radioShow
        //
        this.radioShow.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioShow.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioShow.Location = new System.Drawing.Point(26, 33);
        this.radioShow.Name = "radioShow";
        this.radioShow.Size = new System.Drawing.Size(130, 18);
        this.radioShow.TabIndex = 1;
        this.radioShow.TabStop = true;
        this.radioShow.Text = "Show";
        this.radioShow.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioShow.UseVisualStyleBackColor = true;
        //
        // radioHide
        //
        this.radioHide.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.radioHide.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioHide.Location = new System.Drawing.Point(26, 15);
        this.radioHide.Name = "radioHide";
        this.radioHide.Size = new System.Drawing.Size(130, 18);
        this.radioHide.TabIndex = 0;
        this.radioHide.TabStop = true;
        this.radioHide.Text = "Hide";
        this.radioHide.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShowAccount
        //
        this.checkShowAccount.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowAccount.Location = new System.Drawing.Point(387, 195);
        this.checkShowAccount.Name = "checkShowAccount";
        this.checkShowAccount.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkShowAccount.Size = new System.Drawing.Size(145, 18);
        this.checkShowAccount.TabIndex = 80;
        this.checkShowAccount.Text = "Show Account Module";
        //
        // checkShowAppts
        //
        this.checkShowAppts.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowAppts.Location = new System.Drawing.Point(387, 177);
        this.checkShowAppts.Name = "checkShowAppts";
        this.checkShowAppts.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkShowAppts.Size = new System.Drawing.Size(145, 18);
        this.checkShowAppts.TabIndex = 79;
        this.checkShowAppts.Text = "Show Appts Module";
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 312);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(941, 338);
        this.gridMain.TabIndex = 18;
        this.gridMain.setTitle("Messages / Segments");
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
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(658, 656);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 0;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(797, 656);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 19;
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
        this.butCancel.Location = new System.Drawing.Point(883, 656);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 20;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butDelete.Location = new System.Drawing.Point(17, 656);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(85, 24);
        this.butDelete.TabIndex = 0;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butBrowseOut
        //
        this.butBrowseOut.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseOut.setAutosize(true);
        this.butBrowseOut.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseOut.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseOut.setCornerRadius(4F);
        this.butBrowseOut.Location = new System.Drawing.Point(881, 44);
        this.butBrowseOut.Name = "butBrowseOut";
        this.butBrowseOut.Size = new System.Drawing.Size(76, 25);
        this.butBrowseOut.TabIndex = 50;
        this.butBrowseOut.Text = "&Browse";
        this.butBrowseOut.Click += new System.EventHandler(this.butBrowseOut_Click);
        //
        // butBrowseIn
        //
        this.butBrowseIn.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseIn.setAutosize(true);
        this.butBrowseIn.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseIn.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseIn.setCornerRadius(4F);
        this.butBrowseIn.Location = new System.Drawing.Point(881, 15);
        this.butBrowseIn.Name = "butBrowseIn";
        this.butBrowseIn.Size = new System.Drawing.Size(76, 25);
        this.butBrowseIn.TabIndex = 48;
        this.butBrowseIn.Text = "&Browse";
        this.butBrowseIn.Click += new System.EventHandler(this.butBrowseIn_Click);
        //
        // FormHL7DefEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
        this.ClientSize = new System.Drawing.Size(974, 696);
        this.Controls.Add(this.label17);
        this.Controls.Add(this.checkShowAccount);
        this.Controls.Add(this.checkShowAppts);
        this.Controls.Add(this.groupShowDemographics);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.textHL7ServiceName);
        this.Controls.Add(this.label18);
        this.Controls.Add(this.textHL7Server);
        this.Controls.Add(this.label19);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.labelDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.labelOutPortEx);
        this.Controls.Add(this.textInternalTypeVersion);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.textInternalType);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.checkEnabled);
        this.Controls.Add(this.textEscChar);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.checkInternal);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textSubcompSep);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.textRepSep);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.textCompSep);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.textFieldSep);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.comboModeTx);
        this.Controls.Add(this.butBrowseOut);
        this.Controls.Add(this.butBrowseIn);
        this.Controls.Add(this.textOutPort);
        this.Controls.Add(this.labelOutPort);
        this.Controls.Add(this.textInPort);
        this.Controls.Add(this.labelInPort);
        this.Controls.Add(this.textOutPath);
        this.Controls.Add(this.labelOutPath);
        this.Controls.Add(this.textInPath);
        this.Controls.Add(this.labelInPath);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.labelInPortEx);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormHL7DefEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HL7 Def Edit";
        this.Load += new System.EventHandler(this.FormHL7DefEdit_Load);
        this.groupShowDemographics.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelOutPortEx = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelInPortEx = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textInternalTypeVersion = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textInternalType = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkEnabled = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textEscChar = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkInternal = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubcompSep = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textRepSep = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCompSep = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFieldSep = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboModeTx = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butBrowseOut;
    private OpenDental.UI.Button butBrowseIn;
    private System.Windows.Forms.TextBox textOutPort = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelOutPort = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textInPort = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelInPort = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOutPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelOutPath = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textInPath = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelInPath = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelDelete = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textHL7ServiceName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label18 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textHL7Server = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label19 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupShowDemographics = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioChangeAndAdd = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioChange = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioShow = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioHide = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.CheckBox checkShowAccount = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkShowAppts = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label20 = new System.Windows.Forms.Label();
}


