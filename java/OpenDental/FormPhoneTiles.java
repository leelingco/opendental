//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPhoneEmpDefaults;
import OpenDental.FormScreenshotBrowse;
import OpenDental.PhoneTile;
import OpenDental.PhoneUI;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;

public class FormPhoneTiles  extends Form 
{

    /**
    * When the GoToChanged event fires, this tells us which patnum.
    */
    public long GotoPatNum = new long();
    /**
    * 
    */
    public EventHandler GoToChanged = null;
    /**
    * This is the difference between server time and local computer time.  Used to ensure that times displayed are accurate to the second.  This value is usally just a few seconds, but possibly a few minutes.
    */
    private TimeSpan timeDelta = new TimeSpan();
    private int msgCount = new int();
    private PhoneTile selectedTile;
    /**
    * This thread fills labelMsg
    */
    private List<Phone> PhoneList = new List<Phone>();
    private List<PhoneEmpDefault> PhoneEmpDefaultList = new List<PhoneEmpDefault>();
    /**
    * Max number of tiles that can be shown. Columns and tiles which are not needed will be hidden and the window will be sized accordingly.
    */
    private int TileCount = 120;
    /**
    * How many phone tiles should show up in each column before creating a new column.
    */
    private int TilesPerColumn = 15;
    private OpenDentBusiness.Phones.PhoneComparer.SortBy SortBy = OpenDentBusiness.Phones.PhoneComparer.SortBy.name;
    public void setPhoneList(List<PhoneEmpDefault> peds, List<Phone> phones) throws Exception {
        //create a new list so our sorting doesn't affect this list elsewhere
        PhoneList = new List<Phone>(phones);
        PhoneList.Sort(new OpenDentBusiness.Phones.PhoneComparer(SortBy));
        PhoneEmpDefaultList = peds;
        fillTiles(false);
    }

    public void setVoicemailCount(int voiceMailCount) throws Exception {
        if (voiceMailCount == 0)
        {
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Regular);
            labelMsg.ForeColor = Color.Black;
        }
        else
        {
            labelMsg.Font = new Font(FontFamily.GenericSansSerif, 10f, FontStyle.Bold);
            labelMsg.ForeColor = Color.Firebrick;
        } 
        labelMsg.Text = "Voice Mails: " + voiceMailCount.ToString();
    }

    public FormPhoneTiles() throws Exception {
        initializeComponent();
    }

    private void formPhoneTiles_Load(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(Environment.MachineName.ToLower(), "jordans") && !StringSupport.equals(Environment.MachineName.ToLower(), "nathan"))
        {
            checkBoxAll.Visible = false;
        }
         
        //so this will also be visible in debug
        timeDelta = MiscData.getNowDateTime() - DateTime.Now;
        PhoneTile tile;
        int x = 0;
        int y = 0;
        for (int i = 1;i < TileCount + 1;i++)
        {
            tile = new PhoneTile();
            tile.Name = "phoneTile" + (i).ToString();
            tile.Location = new Point(tile.Width * x, butOverride.Bottom + 15 + (tile.Height * y));
            tile.GoToChanged += new System.EventHandler(this.phoneTile_GoToChanged);
            tile.SelectedTileChanged += new System.EventHandler(this.phoneTile_SelectedTileChanged);
            tile.ScreenshotClick += new EventHandler(this.phoneTile_ScreenshotClick);
            tile.MenuNumbers = menuNumbers;
            tile.MenuStatus = menuStatus;
            this.Controls.Add(tile);
            y++;
            if (i % (TilesPerColumn) == 0)
            {
                //If number is divisble by the number of tiles per column, move over to a new column.  TilesPerColumn subtracts one because i is zero based.
                y = 0;
                x++;
            }
             
        }
        fillTiles(true);
        //initial fast load and anytime data changes.  After this, pumped in from main form.
        Control[] topLeftMatch = Controls.Find("phoneTile1", false);
        if (PhoneList.Count >= 1 && topLeftMatch != null && topLeftMatch.Length >= 1)
        {
            //Size the window to fit contents
            tile = ((PhoneTile)topLeftMatch[0]);
            int columns = (int)Math.Ceiling((double)PhoneList.Count / TilesPerColumn);
            this.ClientSize = new Size(columns * tile.Width, tile.Top + (tile.Height * TilesPerColumn));
        }
         
        radioByExt.CheckedChanged += radioSort_CheckedChanged;
        radioByName.CheckedChanged += radioSort_CheckedChanged;
    }

    private void formPhoneTiles_Shown(Object sender, EventArgs e) throws Exception {
        DateTime now = DateTime.Now;
        while (now.AddSeconds(1) > DateTime.Now)
        {
            Application.DoEvents();
        }
    }

    private void fillTiles(boolean refreshList) throws Exception {
        if (refreshList)
        {
            //Refresh the phone list. This will cause a database refresh for our list and call this function again with the new list.
            setPhoneList(PhoneEmpDefaults.refresh(),Phones.getPhoneList());
            return ;
        }
         
        if (PhoneList == null)
        {
            return ;
        }
         
        PhoneTile tile;
        for (int i = 0;i < TileCount;i++)
        {
            //Application.DoEvents();
            Control[] controlMatches = Controls.Find("phoneTile" + (i + 1).ToString(), false);
            if (controlMatches.Length == 0)
            {
                continue;
            }
             
            //no match found for some reason.
            tile = ((PhoneTile)controlMatches[0]);
            tile.TimeDelta = timeDelta;
            tile.ShowImageForced = checkBoxAll.Checked;
            if (PhoneList.Count > i)
            {
                tile.SetPhone(PhoneList[i], PhoneEmpDefaults.GetEmpDefaultFromList(PhoneList[i].EmployeeNum, PhoneEmpDefaultList), PhoneEmpDefaults.IsTriageOperatorForExtension(PhoneList[i].Extension, PhoneEmpDefaultList));
            }
            else
            {
                tile.setPhone(null,null,false);
            } 
        }
    }

    private void phoneTile_GoToChanged(Object sender, EventArgs e) throws Exception {
        PhoneTile tile = (PhoneTile)sender;
        if (tile.getPhoneCur() == null)
        {
            return ;
        }
         
        if (tile.getPhoneCur().PatNum == 0)
        {
            return ;
        }
         
        GotoPatNum = tile.getPhoneCur().PatNum;
        onGoToChanged();
    }

    protected void onGoToChanged() throws Exception {
        if (GoToChanged != null)
        {
            GoToChanged(this, new EventArgs());
        }
         
    }

    private void phoneTile_SelectedTileChanged(Object sender, EventArgs e) throws Exception {
        selectedTile = (PhoneTile)sender;
    }

    private void phoneTile_ScreenshotClick(Object sender, EventArgs e) throws Exception {
        PhoneTile tile = (PhoneTile)sender;
        if (tile.getPhoneCur() == null)
        {
            return ;
        }
         
        if (StringSupport.equals(tile.getPhoneCur().ScreenshotPath, ""))
        {
            MessageBox.Show("No screenshots available yet.");
            return ;
        }
         
        if (!File.Exists(tile.getPhoneCur().ScreenshotPath))
        {
            MessageBox.Show("Could not find file: " + tile.getPhoneCur().ScreenshotPath);
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        FormScreenshotBrowse formSB = new FormScreenshotBrowse();
        formSB.ScreenshotPath = tile.getPhoneCur().ScreenshotPath;
        formSB.ShowDialog();
        Cursor = Cursors.Default;
    }

    //private void timerMain_Tick(object sender,EventArgs e) {
    //every 1.6 seconds
    //FillTiles(false);
    //}
    //Phones.SetWebCamImage(intTest+101,(Bitmap)pictureWebCam.Image,PhoneList);
    private void butOverride_Click(Object sender, EventArgs e) throws Exception {
        FormPhoneEmpDefaults formPED = new FormPhoneEmpDefaults();
        formPED.ShowDialog();
    }

    private void checkBoxAll_Click(Object sender, EventArgs e) throws Exception {
        Phones.clearImages();
        fillTiles(false);
    }

    private void radioSort_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (sender == null || !(sender instanceof RadioButton) || ((RadioButton)sender).Checked == false)
        {
            return ;
        }
         
        if (radioByExt.Checked)
        {
            SortBy = OpenDentBusiness.Phones.PhoneComparer.SortBy.ext;
        }
        else
        {
            SortBy = OpenDentBusiness.Phones.PhoneComparer.SortBy.name;
        } 
        //Get the phone tiles anew. This will force a resort according the preference we just set.
        fillTiles(true);
    }

    private void menuItemManage_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.manage(selectedTile);
    }

    private void menuItemAdd_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.add(selectedTile);
    }

    //Timecards-------------------------------------------------------------------------------------
    private void menuItemAvailable_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.available(selectedTile);
        fillTiles(true);
    }

    private void menuItemTraining_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.training(selectedTile);
        fillTiles(true);
    }

    private void menuItemTeamAssist_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.teamAssist(selectedTile);
        fillTiles(true);
    }

    private void menuItemNeedsHelp_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.needsHelp(selectedTile);
        fillTiles(true);
    }

    private void menuItemWrapUp_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.wrapUp(selectedTile);
        fillTiles(true);
    }

    private void menuItemOfflineAssist_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.offlineAssist(selectedTile);
        fillTiles(true);
    }

    private void menuItemUnavailable_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.unavailable(selectedTile);
        fillTiles(true);
    }

    private void menuItemBackup_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.backup(selectedTile);
        fillTiles(true);
    }

    //RingGroups---------------------------------------------------
    private void menuItemRinggroupAll_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupAll(selectedTile);
    }

    private void menuItemRinggroupNone_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupNone(selectedTile);
    }

    private void menuItemRinggroupsDefault_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupsDefault(selectedTile);
    }

    private void menuItemRinggroupsBackup_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.ringgroupsBackup(selectedTile);
    }

    //Timecard---------------------------------------------------
    private void menuItemLunch_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.lunch(selectedTile);
        fillTiles(true);
    }

    private void menuItemHome_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.home(selectedTile);
        fillTiles(true);
    }

    private void menuItemBreak_Click(Object sender, EventArgs e) throws Exception {
        PhoneUI.break(selectedTile);
        fillTiles(true);
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
        this.labelMsg = new System.Windows.Forms.Label();
        this.menuNumbers = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
        this.menuStatus = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.menuItemStatusOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemNeedsHelp = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemRingGroupOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemRinggroupBackup = new System.Windows.Forms.ToolStripMenuItem();
        this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
        this.menuItemClockOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
        this.menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
        this.checkBoxAll = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioByExt = new System.Windows.Forms.RadioButton();
        this.radioByName = new System.Windows.Forms.RadioButton();
        this.butOverride = new OpenDental.UI.Button();
        this.menuNumbers.SuspendLayout();
        this.menuStatus.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // labelMsg
        //
        this.labelMsg.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelMsg.ForeColor = System.Drawing.Color.Firebrick;
        this.labelMsg.Location = new System.Drawing.Point(102, 10);
        this.labelMsg.Name = "labelMsg";
        this.labelMsg.Size = new System.Drawing.Size(198, 20);
        this.labelMsg.TabIndex = 27;
        this.labelMsg.Text = "Voice Mails: 0";
        this.labelMsg.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // menuNumbers
        //
        this.menuNumbers.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemManage, this.menuItemAdd });
        this.menuNumbers.Name = "contextMenuStrip1";
        this.menuNumbers.Size = new System.Drawing.Size(291, 48);
        //
        // menuItemManage
        //
        this.menuItemManage.Name = "menuItemManage";
        this.menuItemManage.Size = new System.Drawing.Size(290, 22);
        this.menuItemManage.Text = "Manage Phone Numbers";
        this.menuItemManage.Click += new System.EventHandler(this.menuItemManage_Click);
        //
        // menuItemAdd
        //
        this.menuItemAdd.Name = "menuItemAdd";
        this.menuItemAdd.Size = new System.Drawing.Size(290, 22);
        this.menuItemAdd.Text = "Attach Phone Number to Current Patient";
        this.menuItemAdd.Click += new System.EventHandler(this.menuItemAdd_Click);
        //
        // menuStatus
        //
        this.menuStatus.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.menuItemStatusOnBehalf, this.menuItemAvailable, this.menuItemTraining, this.menuItemTeamAssist, this.menuItemNeedsHelp, this.menuItemWrapUp, this.menuItemOfflineAssist, this.menuItemUnavailable, this.menuItemBackup, this.toolStripMenuItem2, this.menuItemRingGroupOnBehalf, this.menuItemRinggroupAll, this.menuItemRinggroupNone, this.menuItemRinggroupsDefault, this.menuItemRinggroupBackup, this.toolStripMenuItem1, this.menuItemClockOnBehalf, this.menuItemLunch, this.menuItemHome, this.menuItemBreak });
        this.menuStatus.Name = "menuStatus";
        this.menuStatus.Size = new System.Drawing.Size(246, 434);
        //
        // menuItemStatusOnBehalf
        //
        this.menuItemStatusOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemStatusOnBehalf.Name = "menuItemStatusOnBehalf";
        this.menuItemStatusOnBehalf.Size = new System.Drawing.Size(245, 22);
        this.menuItemStatusOnBehalf.Text = "menuItemStatusOnBehalf";
        //
        // menuItemAvailable
        //
        this.menuItemAvailable.Name = "menuItemAvailable";
        this.menuItemAvailable.Size = new System.Drawing.Size(245, 22);
        this.menuItemAvailable.Text = "Available";
        this.menuItemAvailable.Click += new System.EventHandler(this.menuItemAvailable_Click);
        //
        // menuItemTraining
        //
        this.menuItemTraining.Name = "menuItemTraining";
        this.menuItemTraining.Size = new System.Drawing.Size(245, 22);
        this.menuItemTraining.Text = "Training";
        this.menuItemTraining.Click += new System.EventHandler(this.menuItemTraining_Click);
        //
        // menuItemTeamAssist
        //
        this.menuItemTeamAssist.Name = "menuItemTeamAssist";
        this.menuItemTeamAssist.Size = new System.Drawing.Size(245, 22);
        this.menuItemTeamAssist.Text = "TeamAssist";
        this.menuItemTeamAssist.Click += new System.EventHandler(this.menuItemTeamAssist_Click);
        //
        // menuItemNeedsHelp
        //
        this.menuItemNeedsHelp.Name = "menuItemNeedsHelp";
        this.menuItemNeedsHelp.Size = new System.Drawing.Size(245, 22);
        this.menuItemNeedsHelp.Text = "NeedsHelp";
        this.menuItemNeedsHelp.Click += new System.EventHandler(this.menuItemNeedsHelp_Click);
        //
        // menuItemWrapUp
        //
        this.menuItemWrapUp.Name = "menuItemWrapUp";
        this.menuItemWrapUp.Size = new System.Drawing.Size(245, 22);
        this.menuItemWrapUp.Text = "WrapUp";
        this.menuItemWrapUp.Click += new System.EventHandler(this.menuItemWrapUp_Click);
        //
        // menuItemOfflineAssist
        //
        this.menuItemOfflineAssist.Name = "menuItemOfflineAssist";
        this.menuItemOfflineAssist.Size = new System.Drawing.Size(245, 22);
        this.menuItemOfflineAssist.Text = "OfflineAssist";
        this.menuItemOfflineAssist.Click += new System.EventHandler(this.menuItemOfflineAssist_Click);
        //
        // menuItemUnavailable
        //
        this.menuItemUnavailable.Name = "menuItemUnavailable";
        this.menuItemUnavailable.Size = new System.Drawing.Size(245, 22);
        this.menuItemUnavailable.Text = "Unavailable";
        this.menuItemUnavailable.Click += new System.EventHandler(this.menuItemUnavailable_Click);
        //
        // menuItemBackup
        //
        this.menuItemBackup.Name = "menuItemBackup";
        this.menuItemBackup.Size = new System.Drawing.Size(245, 22);
        this.menuItemBackup.Text = "Backup";
        this.menuItemBackup.Click += new System.EventHandler(this.menuItemBackup_Click);
        //
        // toolStripMenuItem2
        //
        this.toolStripMenuItem2.Name = "toolStripMenuItem2";
        this.toolStripMenuItem2.Size = new System.Drawing.Size(242, 6);
        //
        // menuItemRingGroupOnBehalf
        //
        this.menuItemRingGroupOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemRingGroupOnBehalf.Name = "menuItemRingGroupOnBehalf";
        this.menuItemRingGroupOnBehalf.Size = new System.Drawing.Size(245, 22);
        this.menuItemRingGroupOnBehalf.Text = "menuItemRingGroupOnBehalf";
        //
        // menuItemRinggroupAll
        //
        this.menuItemRinggroupAll.Name = "menuItemRinggroupAll";
        this.menuItemRinggroupAll.Size = new System.Drawing.Size(245, 22);
        this.menuItemRinggroupAll.Text = "Ringgroups All";
        this.menuItemRinggroupAll.Click += new System.EventHandler(this.menuItemRinggroupAll_Click);
        //
        // menuItemRinggroupNone
        //
        this.menuItemRinggroupNone.Name = "menuItemRinggroupNone";
        this.menuItemRinggroupNone.Size = new System.Drawing.Size(245, 22);
        this.menuItemRinggroupNone.Text = "Ringgroups None";
        this.menuItemRinggroupNone.Click += new System.EventHandler(this.menuItemRinggroupNone_Click);
        //
        // menuItemRinggroupsDefault
        //
        this.menuItemRinggroupsDefault.Name = "menuItemRinggroupsDefault";
        this.menuItemRinggroupsDefault.Size = new System.Drawing.Size(245, 22);
        this.menuItemRinggroupsDefault.Text = "Ringgroups Default";
        this.menuItemRinggroupsDefault.Click += new System.EventHandler(this.menuItemRinggroupsDefault_Click);
        //
        // menuItemRinggroupBackup
        //
        this.menuItemRinggroupBackup.Name = "menuItemRinggroupBackup";
        this.menuItemRinggroupBackup.Size = new System.Drawing.Size(245, 22);
        this.menuItemRinggroupBackup.Text = "Ringgroups Backup";
        this.menuItemRinggroupBackup.Click += new System.EventHandler(this.menuItemRinggroupsBackup_Click);
        //
        // toolStripMenuItem1
        //
        this.toolStripMenuItem1.Name = "toolStripMenuItem1";
        this.toolStripMenuItem1.Size = new System.Drawing.Size(242, 6);
        //
        // menuItemClockOnBehalf
        //
        this.menuItemClockOnBehalf.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
        this.menuItemClockOnBehalf.Name = "menuItemClockOnBehalf";
        this.menuItemClockOnBehalf.Size = new System.Drawing.Size(245, 22);
        this.menuItemClockOnBehalf.Text = "menuItemClockOnBehalf";
        //
        // menuItemLunch
        //
        this.menuItemLunch.Name = "menuItemLunch";
        this.menuItemLunch.Size = new System.Drawing.Size(245, 22);
        this.menuItemLunch.Text = "Lunch";
        this.menuItemLunch.Click += new System.EventHandler(this.menuItemLunch_Click);
        //
        // menuItemHome
        //
        this.menuItemHome.Name = "menuItemHome";
        this.menuItemHome.Size = new System.Drawing.Size(245, 22);
        this.menuItemHome.Text = "Home";
        this.menuItemHome.Click += new System.EventHandler(this.menuItemHome_Click);
        //
        // menuItemBreak
        //
        this.menuItemBreak.Name = "menuItemBreak";
        this.menuItemBreak.Size = new System.Drawing.Size(245, 22);
        this.menuItemBreak.Text = "Break";
        this.menuItemBreak.Click += new System.EventHandler(this.menuItemBreak_Click);
        //
        // checkBoxAll
        //
        this.checkBoxAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkBoxAll.Location = new System.Drawing.Point(873, 5);
        this.checkBoxAll.Name = "checkBoxAll";
        this.checkBoxAll.Size = new System.Drawing.Size(104, 16);
        this.checkBoxAll.TabIndex = 28;
        this.checkBoxAll.Text = "Show All";
        this.checkBoxAll.UseVisualStyleBackColor = true;
        this.checkBoxAll.Click += new System.EventHandler(this.checkBoxAll_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioByExt);
        this.groupBox1.Controls.Add(this.radioByName);
        this.groupBox1.Location = new System.Drawing.Point(325, 0);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(198, 40);
        this.groupBox1.TabIndex = 29;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Sort By:";
        //
        // radioByExt
        //
        this.radioByExt.AutoSize = true;
        this.radioByExt.Location = new System.Drawing.Point(107, 17);
        this.radioByExt.Name = "radioByExt";
        this.radioByExt.Size = new System.Drawing.Size(71, 17);
        this.radioByExt.TabIndex = 1;
        this.radioByExt.Text = "Extension";
        this.radioByExt.UseVisualStyleBackColor = true;
        //
        // radioByName
        //
        this.radioByName.AutoSize = true;
        this.radioByName.Checked = true;
        this.radioByName.Location = new System.Drawing.Point(24, 17);
        this.radioByName.Name = "radioByName";
        this.radioByName.Size = new System.Drawing.Size(53, 17);
        this.radioByName.TabIndex = 0;
        this.radioByName.TabStop = true;
        this.radioByName.Text = "Name";
        this.radioByName.UseVisualStyleBackColor = true;
        //
        // butOverride
        //
        this.butOverride.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOverride.setAutosize(true);
        this.butOverride.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOverride.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOverride.setCornerRadius(4F);
        this.butOverride.Location = new System.Drawing.Point(0, 8);
        this.butOverride.Name = "butOverride";
        this.butOverride.Size = new System.Drawing.Size(75, 24);
        this.butOverride.TabIndex = 26;
        this.butOverride.Text = "Settings";
        this.butOverride.Click += new System.EventHandler(this.butOverride_Click);
        //
        // FormPhoneTiles
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.AutoScroll = true;
        this.ClientSize = new System.Drawing.Size(984, 862);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.checkBoxAll);
        this.Controls.Add(this.labelMsg);
        this.Controls.Add(this.butOverride);
        this.Name = "FormPhoneTiles";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Phones";
        this.Load += new System.EventHandler(this.FormPhoneTiles_Load);
        this.Shown += new System.EventHandler(this.FormPhoneTiles_Shown);
        this.menuNumbers.ResumeLayout(false);
        this.menuStatus.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Label labelMsg = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOverride;
    private System.Windows.Forms.ContextMenuStrip menuNumbers = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemManage = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemAdd = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ContextMenuStrip menuStatus = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem menuItemAvailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTraining = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemTeamAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemWrapUp = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemOfflineAssist = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemUnavailable = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupAll = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupNone = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupsDefault = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemBackup = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripSeparator toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
    private System.Windows.Forms.ToolStripMenuItem menuItemLunch = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemHome = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemBreak = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.CheckBox checkBoxAll = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ToolStripMenuItem menuItemNeedsHelp = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioByExt = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioByName = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.ToolStripMenuItem menuItemStatusOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRingGroupOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemClockOnBehalf = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem menuItemRinggroupBackup = new System.Windows.Forms.ToolStripMenuItem();
}


