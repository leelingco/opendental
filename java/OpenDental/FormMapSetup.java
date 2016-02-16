//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormMapAreaEdit;
import OpenDental.MapAreaPanel;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Employees.EmployeeComparer.SortBy;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapAreas;
import OpenDentBusiness.MapItemType;
import OpenDentBusiness.Phone;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Prefs;
import OpenDental.Properties.Resources;

public class FormMapSetup  extends Form 
{

    public FormMapSetup() throws Exception {
        initializeComponent();
    }

    private void formMapSetup_Load(Object sender, EventArgs e) throws Exception {
        //Get latest prefs. We will use them to setup our clinic panel.
        Cache.refresh(InvalidType.Prefs);
        //fill the employee list
        fillEmployees();
        //set preview defaults
        this.numFloorHeightFeet.Value = this.mapAreaPanel.getFloorHeightFeet();
        this.numFloorWidthFeet.Value = this.mapAreaPanel.getFloorWidthFeet();
        this.numPixelsPerFoot.Value = this.mapAreaPanel.getPixelsPerFoot();
        //map panel
        mapAreaPanel_MapAreaChanged(this,new EventArgs());
    }

    private void fillEmployees() throws Exception {
        List<Employee> employees = new List<Employee>(Employees.getListShort());
        employees.Sort(new OpenDentBusiness.Employees.EmployeeComparer(SortBy.ext));
        gridEmployees.beginUpdate();
        gridEmployees.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Ext. - Name",0);
        col.setTextAlign(HorizontalAlignment.Left);
        gridEmployees.getColumns().add(col);
        gridEmployees.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < employees.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(employees[i].PhoneExt + " - " + employees[i].FName + " " + employees[i].LName);
            row.setTag(employees[i]);
            //row.ColorBackG=gridEmployees.Rows.Count%2==0?Color.LightGray:Color.White;
            gridEmployees.getRows().add(row);
        }
        gridEmployees.endUpdate();
    }

    private void mapAreaPanel_MapAreaChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.clear(false);
        //All prefs are hard-coded for now. We will likely create a parent table which will hold these values in the event that we release this feature to customers.
        /*
        			//Load clinic panel values from database.
        			bool showGrid=false;
        			if(bool.TryParse(PrefC.GetRaw("clinicMapPanelHQ.ShowGrid"),out showGrid)) {
        				checkShowGrid.Checked=showGrid;
        			}
        			bool showOutline=false;
        			if(bool.TryParse(PrefC.GetRaw("clinicMapPanelHQ.ShowOutline"),out showOutline)) {
        				checkShowOutline.Checked=showOutline;
        			}
        			int floorWidthFeet=0;
        			if(int.TryParse(PrefC.GetRaw("clinicMapPanelHQ.FloorWidthFeet"),out floorWidthFeet)) {
        				numFloorWidthFeet.Value=floorWidthFeet;
        			}
        			int floorHeightFeet=0;
        			if(int.TryParse(PrefC.GetRaw("clinicMapPanelHQ.FloorHeightFeet"),out floorHeightFeet)) {
        				numFloorHeightFeet.Value=floorHeightFeet;
        			}
        			int pixelsPerFoot=0;
        			if(int.TryParse(PrefC.GetRaw("clinicMapPanelHQ.PixelsPerFoot"),out pixelsPerFoot)) {
        				numPixelsPerFoot.Value=pixelsPerFoot;
        			}
        			*/
        //fill the panel
        List<MapArea> clinicMapItems = MapAreas.refresh();
        for (int i = 0;i < clinicMapItems.Count;i++)
        {
            if (clinicMapItems[i].ItemType == MapItemType.Room)
            {
                mapAreaPanel.AddCubicle(clinicMapItems[i]);
            }
            else if (clinicMapItems[i].ItemType == MapItemType.DisplayLabel)
            {
                mapAreaPanel.AddDisplayLabel(clinicMapItems[i]);
            }
              
        }
    }

    /**
    * This function will NOT update the prefs table (for now). All pref values for this form are hard-coded until we decide to make this a cutsomer-facing feature.
    */
    private void tryUpdatePref(String prefName, String newValue) throws Exception {
        return ;
        try
        {
            Prefs.updateRaw(prefName,newValue);
        }
        catch (Exception e)
        {
            MessageBox.Show("Updating preference table failed. Any changes you made were not saved.\r\n\r\n" + e.Message);
        }
    
    }

    private void checkShowGrid_CheckedChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.setShowGrid(checkShowGrid.Checked);
        if (sender != null)
        {
            //Sender will be null on form load. Otherwise save the new value.
            TryUpdatePref("clinicMapPanelHQ.ShowGrid", mapAreaPanel.getShowGrid().ToString());
        }
         
    }

    private void checkShowOutline_CheckedChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.setShowOutline(checkShowOutline.Checked);
        if (sender != null)
        {
            //Sender will be null on form load. Otherwise save the new value.
            TryUpdatePref("clinicMapPanelHQ.ShowOutline", mapAreaPanel.getShowOutline().ToString());
        }
         
    }

    private void numericFloorWidthFeet_ValueChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.setFloorWidthFeet((int)numFloorWidthFeet.Value);
        if (sender != null)
        {
            //Sender will be null on form load. Otherwise save the new value.
            TryUpdatePref("clinicMapPanelHQ.FloorWidthFeet", mapAreaPanel.getFloorWidthFeet().ToString());
        }
         
    }

    private void numericFloorHeightFeet_ValueChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.setFloorHeightFeet((int)numFloorHeightFeet.Value);
        if (sender != null)
        {
            //Sender will be null on form load. Otherwise save the new value.
            TryUpdatePref("clinicMapPanelHQ.FloorHeightFeet", mapAreaPanel.getFloorHeightFeet().ToString());
        }
         
    }

    private void numericPixelsPerFoot_ValueChanged(Object sender, EventArgs e) throws Exception {
        mapAreaPanel.setPixelsPerFoot((int)numPixelsPerFoot.Value);
        if (sender != null)
        {
            //Sender will be null on form load. Otherwise save the new value.
            TryUpdatePref("clinicMapPanelHQ.PixelsPerFoot", mapAreaPanel.getPixelsPerFoot().ToString());
        }
         
    }

    private void newCubicleToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        FormMapAreaEdit FormEP = new FormMapAreaEdit();
        FormEP.MapItem = new MapArea();
        FormEP.MapItem.setIsNew(true);
        FormEP.MapItem.Width = 6;
        FormEP.MapItem.Height = 6;
        FormEP.MapItem.ItemType = MapItemType.Room;
        PointF xy = getXYFromMenuLocation(sender);
        FormEP.MapItem.XPos = Math.Round(xy.X, 3);
        FormEP.MapItem.YPos = Math.Round(xy.Y, 3);
        if (FormEP.ShowDialog(this) != DialogResult.OK)
        {
            return ;
        }
         
        mapAreaPanel_MapAreaChanged(this,new EventArgs());
    }

    private void newLabelToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        FormMapAreaEdit FormEP = new FormMapAreaEdit();
        FormEP.MapItem = new MapArea();
        FormEP.MapItem.setIsNew(true);
        FormEP.MapItem.Width = 6;
        FormEP.MapItem.Height = 6;
        FormEP.MapItem.ItemType = MapItemType.DisplayLabel;
        PointF xy = getXYFromMenuLocation(sender);
        FormEP.MapItem.XPos = Math.Round(xy.X, 3);
        FormEP.MapItem.YPos = Math.Round(xy.Y, 3);
        if (FormEP.ShowDialog(this) != DialogResult.OK)
        {
            return ;
        }
         
        mapAreaPanel_MapAreaChanged(this,new EventArgs());
    }

    private void mapAreaPanel_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        newCubicleToolStripMenuItem.Tag = e.Location;
        newLabelToolStripMenuItem.Tag = e.Location;
        menu.Show(mapAreaPanel, e.Location);
    }

    private PointF getXYFromMenuLocation(Object sender) throws Exception {
        PointF xy = PointF.Empty;
        if (sender != null && sender instanceof ToolStripMenuItem && ((ToolStripMenuItem)sender).Tag != null && ((ToolStripMenuItem)sender).Tag instanceof System.Drawing.Point)
        {
            xy = MapAreaPanel.ConvertScreenLocationToXY(((System.Drawing.Point)((ToolStripMenuItem)sender).Tag), mapAreaPanel.getPixelsPerFoot());
        }
         
        return xy;
    }

    private void butBuildFromPhoneTable_Click(Object sender, EventArgs e) throws Exception {
        if (MessageBox.Show("This action will clear all information from clinicmapitem table and recreated it from current phone table rows. Would you like to continue?", "", MessageBoxButtons.YesNo) != System.Windows.Forms.DialogResult.Yes)
        {
            return ;
        }
         
        mapAreaPanel.clear(true);
        List<Phone> phones = Phones.getPhoneList();
        int defaultSizeFeet = 6;
        int row = 1;
        int column = 0;
        for (int i = 0;i < 78;i++)
        {
            if (row >= 7)
            {
                if (++column > 8)
                {
                    column = 3;
                    row++;
                }
                 
            }
            else
            {
                if (++column > 10)
                {
                    column = 1;
                    row++;
                }
                 
                if (row == 7)
                {
                    column = 3;
                }
                 
            } 
            //row=8;
            //Phone phone=phones[i];
            MapArea clinicMapItem = new MapArea();
            clinicMapItem.Description = "";
            clinicMapItem.Extension = i;
            //phone.Extension;
            clinicMapItem.Width = defaultSizeFeet;
            clinicMapItem.Height = defaultSizeFeet;
            clinicMapItem.XPos = (1 * column) + ((column - 1) * defaultSizeFeet);
            clinicMapItem.YPos = 1 + ((row - 1) * defaultSizeFeet);
            mapAreaPanel.addCubicle(clinicMapItem);
            MapAreas.insert(clinicMapItem);
        }
    }

    private void butAddMapArea_Click(Object sender, EventArgs e) throws Exception {
        //edit this entry
        if (gridEmployees.getSelectedIndices() == null || gridEmployees.getSelectedIndices().Length <= 0 || gridEmployees.getRows()[gridEmployees.getSelectedIndices()[0]].Tag == null || !(gridEmployees.getRows()[gridEmployees.getSelectedIndices()[0]].Tag instanceof Employee))
        {
            MsgBox.show(this,"Select an employee");
            return ;
        }
         
        Employee employee = (Employee)gridEmployees.getRows()[gridEmployees.getSelectedIndices()[0]].Tag;
        FormMapAreaEdit FormEP = new FormMapAreaEdit();
        FormEP.MapItem = new MapArea();
        FormEP.MapItem.setIsNew(true);
        FormEP.MapItem.Width = 6;
        FormEP.MapItem.Height = 6;
        FormEP.MapItem.ItemType = MapItemType.Room;
        FormEP.MapItem.Extension = employee.PhoneExt;
        if (FormEP.ShowDialog(this) != DialogResult.OK)
        {
            return ;
        }
         
        mapAreaPanel_MapAreaChanged(this,new EventArgs());
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
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
        this.checkShowGrid = new System.Windows.Forms.CheckBox();
        this.label2 = new System.Windows.Forms.Label();
        this.numFloorWidthFeet = new System.Windows.Forms.NumericUpDown();
        this.label3 = new System.Windows.Forms.Label();
        this.numFloorHeightFeet = new System.Windows.Forms.NumericUpDown();
        this.label4 = new System.Windows.Forms.Label();
        this.numPixelsPerFoot = new System.Windows.Forms.NumericUpDown();
        this.checkShowOutline = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.menu = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.newCubicleToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.newLabelToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.gridEmployees = new OpenDental.UI.ODGrid();
        this.mapAreaPanel = new OpenDental.MapAreaPanel();
        this.butCancel = new OpenDental.UI.Button();
        this.butAddMapArea = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.numFloorWidthFeet)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.numFloorHeightFeet)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.numPixelsPerFoot)).BeginInit();
        this.groupBox1.SuspendLayout();
        this.menu.SuspendLayout();
        this.SuspendLayout();
        //
        // checkShowGrid
        //
        this.checkShowGrid.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowGrid.Location = new System.Drawing.Point(9, 93);
        this.checkShowGrid.Name = "checkShowGrid";
        this.checkShowGrid.Size = new System.Drawing.Size(120, 16);
        this.checkShowGrid.TabIndex = 3;
        this.checkShowGrid.Text = "Show Grid";
        this.checkShowGrid.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowGrid.UseVisualStyleBackColor = true;
        this.checkShowGrid.CheckedChanged += new System.EventHandler(this.checkShowGrid_CheckedChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 22);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(104, 16);
        this.label2.TabIndex = 15;
        this.label2.Text = "Floor Width (in feet)";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // numFloorWidthFeet
        //
        this.numFloorWidthFeet.Location = new System.Drawing.Point(115, 20);
        this.numFloorWidthFeet.Maximum = new double(new int[]{ 10000, 0, 0, 0 });
        this.numFloorWidthFeet.Minimum = new double(new int[]{ 1, 0, 0, 0 });
        this.numFloorWidthFeet.Name = "numFloorWidthFeet";
        this.numFloorWidthFeet.Size = new System.Drawing.Size(60, 20);
        this.numFloorWidthFeet.TabIndex = 14;
        this.numFloorWidthFeet.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        this.numFloorWidthFeet.Value = new double(new int[]{ 71, 0, 0, 0 });
        this.numFloorWidthFeet.ValueChanged += new System.EventHandler(this.numericFloorWidthFeet_ValueChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 47);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(104, 16);
        this.label3.TabIndex = 17;
        this.label3.Text = "Floor Height (in feet)";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // numFloorHeightFeet
        //
        this.numFloorHeightFeet.Location = new System.Drawing.Point(115, 45);
        this.numFloorHeightFeet.Maximum = new double(new int[]{ 10000, 0, 0, 0 });
        this.numFloorHeightFeet.Minimum = new double(new int[]{ 1, 0, 0, 0 });
        this.numFloorHeightFeet.Name = "numFloorHeightFeet";
        this.numFloorHeightFeet.Size = new System.Drawing.Size(60, 20);
        this.numFloorHeightFeet.TabIndex = 16;
        this.numFloorHeightFeet.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        this.numFloorHeightFeet.Value = new double(new int[]{ 57, 0, 0, 0 });
        this.numFloorHeightFeet.ValueChanged += new System.EventHandler(this.numericFloorHeightFeet_ValueChanged);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 72);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(104, 16);
        this.label4.TabIndex = 18;
        this.label4.Text = "Pixels Per Foot";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // numPixelsPerFoot
        //
        this.numPixelsPerFoot.Location = new System.Drawing.Point(115, 70);
        this.numPixelsPerFoot.Maximum = new double(new int[]{ 1000, 0, 0, 0 });
        this.numPixelsPerFoot.Minimum = new double(new int[]{ 1, 0, 0, 0 });
        this.numPixelsPerFoot.Name = "numPixelsPerFoot";
        this.numPixelsPerFoot.Size = new System.Drawing.Size(60, 20);
        this.numPixelsPerFoot.TabIndex = 19;
        this.numPixelsPerFoot.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        this.numPixelsPerFoot.Value = new double(new int[]{ 17, 0, 0, 0 });
        this.numPixelsPerFoot.ValueChanged += new System.EventHandler(this.numericPixelsPerFoot_ValueChanged);
        //
        // checkShowOutline
        //
        this.checkShowOutline.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowOutline.Checked = true;
        this.checkShowOutline.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowOutline.Location = new System.Drawing.Point(8, 113);
        this.checkShowOutline.Name = "checkShowOutline";
        this.checkShowOutline.Size = new System.Drawing.Size(120, 16);
        this.checkShowOutline.TabIndex = 21;
        this.checkShowOutline.Text = "Show Outline";
        this.checkShowOutline.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShowOutline.UseVisualStyleBackColor = true;
        this.checkShowOutline.CheckedChanged += new System.EventHandler(this.checkShowOutline_CheckedChanged);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.checkShowGrid);
        this.groupBox1.Controls.Add(this.checkShowOutline);
        this.groupBox1.Controls.Add(this.numFloorWidthFeet);
        this.groupBox1.Controls.Add(this.numFloorHeightFeet);
        this.groupBox1.Controls.Add(this.numPixelsPerFoot);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Location = new System.Drawing.Point(1392, 34);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(182, 138);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Preview Different Options";
        //
        // menu
        //
        this.menu.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.newCubicleToolStripMenuItem, this.newLabelToolStripMenuItem });
        this.menu.Name = "menu";
        this.menu.Size = new System.Drawing.Size(142, 48);
        //
        // newCubicleToolStripMenuItem
        //
        this.newCubicleToolStripMenuItem.Name = "newCubicleToolStripMenuItem";
        this.newCubicleToolStripMenuItem.Size = new System.Drawing.Size(141, 22);
        this.newCubicleToolStripMenuItem.Text = "New Cubicle";
        this.newCubicleToolStripMenuItem.Click += new System.EventHandler(this.newCubicleToolStripMenuItem_Click);
        //
        // newLabelToolStripMenuItem
        //
        this.newLabelToolStripMenuItem.Name = "newLabelToolStripMenuItem";
        this.newLabelToolStripMenuItem.Size = new System.Drawing.Size(141, 22);
        this.newLabelToolStripMenuItem.Text = "New Label";
        this.newLabelToolStripMenuItem.Click += new System.EventHandler(this.newLabelToolStripMenuItem_Click);
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(12, 11);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(378, 13);
        this.label1.TabIndex = 26;
        this.label1.Text = "Double-click an item on the map to edit. Right-click the map to add a new item.";
        //
        // gridEmployees
        //
        this.gridEmployees.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEmployees.Font = new System.Drawing.Font("Calibri", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.gridEmployees.setHScrollVisible(false);
        this.gridEmployees.Location = new System.Drawing.Point(1392, 178);
        this.gridEmployees.Name = "gridEmployees";
        this.gridEmployees.setScrollValue(0);
        this.gridEmployees.Size = new System.Drawing.Size(182, 729);
        this.gridEmployees.TabIndex = 25;
        this.gridEmployees.setTitle("Employees");
        this.gridEmployees.setTranslationName("TableEmployees");
        //
        // mapAreaPanel
        //
        this.mapAreaPanel.setAllowDragging(true);
        this.mapAreaPanel.setAllowEditing(true);
        this.mapAreaPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.mapAreaPanel.AutoScroll = true;
        this.mapAreaPanel.AutoScrollMinSize = new System.Drawing.Size(1326, 935);
        this.mapAreaPanel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.mapAreaPanel.setFloorColor(System.Drawing.Color.White);
        this.mapAreaPanel.setFloorHeightFeet(55);
        this.mapAreaPanel.setFloorWidthFeet(78);
        this.mapAreaPanel.Font = new System.Drawing.Font("Calibri", 25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.mapAreaPanel.setFontCubicle(new System.Drawing.Font("Calibri", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanel.setFontCubicleHeader(new System.Drawing.Font("Calibri", 19F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanel.setFontLabel(new System.Drawing.Font("Calibri", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0))));
        this.mapAreaPanel.setGridColor(System.Drawing.Color.LightGray);
        this.mapAreaPanel.Location = new System.Drawing.Point(12, 34);
        this.mapAreaPanel.Name = "mapAreaPanel";
        this.mapAreaPanel.setPixelsPerFoot(17);
        this.mapAreaPanel.setShowGrid(false);
        this.mapAreaPanel.setShowOutline(true);
        this.mapAreaPanel.Size = new System.Drawing.Size(1374, 973);
        this.mapAreaPanel.TabIndex = 4;
        this.mapAreaPanel.MapAreaChanged += new System.EventHandler(this.mapAreaPanel_MapAreaChanged);
        this.mapAreaPanel.MouseUp += new System.Windows.Forms.MouseEventHandler(this.mapAreaPanel_MouseUp);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(1499, 1023);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 28;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAddMapArea
        //
        this.butAddMapArea.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddMapArea.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddMapArea.setAutosize(true);
        this.butAddMapArea.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddMapArea.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddMapArea.setCornerRadius(4F);
        this.butAddMapArea.Image = Resources.getAdd();
        this.butAddMapArea.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddMapArea.Location = new System.Drawing.Point(1499, 913);
        this.butAddMapArea.Name = "butAddMapArea";
        this.butAddMapArea.Size = new System.Drawing.Size(75, 24);
        this.butAddMapArea.TabIndex = 49;
        this.butAddMapArea.Text = "Add";
        this.butAddMapArea.Click += new System.EventHandler(this.butAddMapArea_Click);
        //
        // FormMapSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.AutoScroll = true;
        this.ClientSize = new System.Drawing.Size(1579, 1049);
        this.Controls.Add(this.butAddMapArea);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridEmployees);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.mapAreaPanel);
        this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.Name = "FormMapSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Map Setup";
        this.Load += new System.EventHandler(this.FormMapSetup_Load);
        ((System.ComponentModel.ISupportInitialize)(this.numFloorWidthFeet)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.numFloorHeightFeet)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.numPixelsPerFoot)).EndInit();
        this.groupBox1.ResumeLayout(false);
        this.menu.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.MapAreaPanel mapAreaPanel;
    private System.Windows.Forms.CheckBox checkShowGrid = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown numFloorWidthFeet = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown numFloorHeightFeet = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown numPixelsPerFoot = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.CheckBox checkShowOutline = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.ContextMenuStrip menu = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem newCubicleToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.ToolStripMenuItem newLabelToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private OpenDental.UI.ODGrid gridEmployees;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butAddMapArea;
}


