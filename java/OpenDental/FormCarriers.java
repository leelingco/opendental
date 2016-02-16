//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:44 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormCarrierCombine;
import OpenDental.FormCarrierEdit;
import OpenDental.FormCarriers;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TelephoneNumbers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormCarriers  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private OpenDental.UI.Button butCombine;
    /**
    * Set to true if using this dialog to select a carrier.
    */
    public boolean IsSelectMode = new boolean();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private CheckBox checkCDAnet = new CheckBox();
    private boolean changed = new boolean();
    private CheckBox checkShowHidden = new CheckBox();
    public TextBox textCarrier = new TextBox();
    private Label label2 = new Label();
    private OpenDental.UI.Button butOK;
    //keeps track of whether an update is necessary.
    private DataTable table = new DataTable();
    public TextBox textPhone = new TextBox();
    private Label labelPhone = new Label();
    public Carrier SelectedCarrier;
    /**
    * 
    */
    public FormCarriers() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCarriers.class);
        this.butAdd = new OpenDental.UI.Button();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.butCombine = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.checkCDAnet = new System.Windows.Forms.CheckBox();
        this.checkShowHidden = new System.Windows.Forms.CheckBox();
        this.textCarrier = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butOK = new OpenDental.UI.Button();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.labelPhone = new System.Windows.Forms.Label();
        this.SuspendLayout();
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
        this.butAdd.Location = new System.Drawing.Point(830, 435);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(90, 26);
        this.butAdd.TabIndex = 7;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butCombine
        //
        this.butCombine.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCombine.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCombine.setAutosize(true);
        this.butCombine.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCombine.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCombine.setCornerRadius(4F);
        this.butCombine.Location = new System.Drawing.Point(830, 471);
        this.butCombine.Name = "butCombine";
        this.butCombine.Size = new System.Drawing.Size(90, 26);
        this.butCombine.TabIndex = 10;
        this.butCombine.Text = "Co&mbine";
        this.toolTip1.SetToolTip(this.butCombine, "Combines multiple Employers");
        this.butCombine.Click += new System.EventHandler(this.butCombine_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(830, 623);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(90, 26);
        this.butCancel.TabIndex = 12;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(11, 29);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(796, 620);
        this.gridMain.TabIndex = 13;
        this.gridMain.setTitle("Carriers");
        this.gridMain.setTranslationName("TableCarriers");
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
        // checkCDAnet
        //
        this.checkCDAnet.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkCDAnet.Location = new System.Drawing.Point(686, 6);
        this.checkCDAnet.Name = "checkCDAnet";
        this.checkCDAnet.Size = new System.Drawing.Size(96, 17);
        this.checkCDAnet.TabIndex = 99;
        this.checkCDAnet.Text = "CDAnet Only";
        this.checkCDAnet.Click += new System.EventHandler(this.checkCDAnet_Click);
        //
        // checkShowHidden
        //
        this.checkShowHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowHidden.Location = new System.Drawing.Point(545, 6);
        this.checkShowHidden.Name = "checkShowHidden";
        this.checkShowHidden.Size = new System.Drawing.Size(96, 17);
        this.checkShowHidden.TabIndex = 100;
        this.checkShowHidden.Text = "Show Hidden";
        this.checkShowHidden.Click += new System.EventHandler(this.checkShowHidden_Click);
        //
        // textCarrier
        //
        this.textCarrier.Location = new System.Drawing.Point(118, 4);
        this.textCarrier.Name = "textCarrier";
        this.textCarrier.Size = new System.Drawing.Size(140, 20);
        this.textCarrier.TabIndex = 101;
        this.textCarrier.TextChanged += new System.EventHandler(this.textCarrier_TextChanged);
        //
        // label2
        //
        this.label2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.label2.Location = new System.Drawing.Point(12, 7);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 102;
        this.label2.Text = "Carrier";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(830, 587);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(90, 26);
        this.butOK.TabIndex = 103;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(342, 4);
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(140, 20);
        this.textPhone.TabIndex = 104;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // labelPhone
        //
        this.labelPhone.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.labelPhone.Location = new System.Drawing.Point(264, 7);
        this.labelPhone.Name = "labelPhone";
        this.labelPhone.Size = new System.Drawing.Size(72, 17);
        this.labelPhone.TabIndex = 105;
        this.labelPhone.Text = "Phone";
        this.labelPhone.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormCarriers
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(927, 672);
        this.Controls.Add(this.textPhone);
        this.Controls.Add(this.labelPhone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textCarrier);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.checkShowHidden);
        this.Controls.Add(this.checkCDAnet);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butCombine);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormCarriers";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Carriers";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormCarriers_Closing);
        this.Load += new System.EventHandler(this.FormCarriers_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formCarriers_Load(Object sender, System.EventArgs e) throws Exception {
        //if(CultureInfo.CurrentCulture.Name.EndsWith("CA")) {//Canadian. en-CA or fr-CA
        //No.  Even Canadian users will want to see all their carriers and only use the checkbox for special situations.
        //	checkCDAnet.Checked=true;
        //}
        //else{
        //	checkCDAnet.Visible=false;
        //}
        if (IsSelectMode)
        {
            butCancel.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butCancel.Text = Lan.g(this,"Close");
            butOK.Visible = false;
        } 
        if (!Security.isAuthorized(Permissions.CarrierCreate,true))
        {
            butAdd.Enabled = false;
        }
         
        Carriers.refreshCache();
        fillGrid();
    }

    private void fillGrid() throws Exception {
        List<String> selectedCarrierNums = new List<String>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            selectedCarrierNums.Add(table.Rows[gridMain.getSelectedIndices()[i]]["CarrierNum"].ToString());
        }
        //Carriers.Refresh();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        /*if(checkCDAnet.Checked){
        				//gridMain.Size=new Size(745,gridMain.Height);
        				col=new ODGridColumn(Lan.g("TableCarriers","Carrier Name"),160);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","EDI Code"),60);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","PMP"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","Network"),50);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","Version"),50);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","02"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","03"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","04"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","05"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","06"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","07"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","08"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        				col=new ODGridColumn(Lan.g("TableCarriers","Hidden"),50,HorizontalAlignment.Center);
        				gridMain.Columns.Add(col);
        			}
        			else{*/
        //gridMain.Size=new Size(839,gridMain.Height);
        col = new ODGridColumn(Lan.g("TableCarriers","Carrier Name"),160);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","Phone"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","Address"),130);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g("TableCarriers","Address2"),120);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","City"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","ST"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","Zip"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","ElectID"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","Hidden"), 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCarriers","Plans"),50);
        gridMain.getColumns().add(col);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            col = new ODGridColumn(Lan.g("TableCarriers","CDAnet"),50);
            gridMain.getColumns().add(col);
        }
         
        //}
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        table = Carriers.GetBigList(checkCDAnet.Checked, checkShowHidden.Checked, textCarrier.Text, textPhone.Text);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            /*if(checkCDAnet.Checked){
            					row.Cells.Add(table.Rows[i]["CarrierName"].ToString());
            					row.Cells.Add(table.Rows[i]["ElectID"].ToString());
            					row.Cells.Add(table.Rows[i]["pMP"].ToString());
            					row.Cells.Add(table.Rows[i]["network"].ToString());
            					row.Cells.Add(table.Rows[i]["version"].ToString());
            					row.Cells.Add(table.Rows[i]["trans02"].ToString());
            					row.Cells.Add(table.Rows[i]["trans03"].ToString());
            					row.Cells.Add(table.Rows[i]["trans04"].ToString());
            					row.Cells.Add(table.Rows[i]["trans05"].ToString());
            					row.Cells.Add(table.Rows[i]["trans06"].ToString());
            					row.Cells.Add(table.Rows[i]["trans07"].ToString());
            					row.Cells.Add(table.Rows[i]["trans08"].ToString());
            					row.Cells.Add(table.Rows[i]["isHidden"].ToString());
            				}
            				else{*/
            row.getCells().Add(table.Rows[i]["CarrierName"].ToString());
            row.getCells().Add(table.Rows[i]["Phone"].ToString());
            row.getCells().Add(table.Rows[i]["Address"].ToString());
            //row.Cells.Add(table.Rows[i]["Address2"].ToString());
            row.getCells().Add(table.Rows[i]["City"].ToString());
            row.getCells().Add(table.Rows[i]["State"].ToString());
            row.getCells().Add(table.Rows[i]["Zip"].ToString());
            row.getCells().Add(table.Rows[i]["ElectID"].ToString());
            row.getCells().Add(table.Rows[i]["isHidden"].ToString());
            row.getCells().Add(table.Rows[i]["insPlanCount"].ToString());
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                row.getCells().Add(table.Rows[i]["isCDA"].ToString());
            }
             
            //}
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (selectedCarrierNums.Contains(table.Rows[i]["CarrierNum"].ToString()))
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    //if(tbCarriers.SelectedIndices.Length>0){
    //	tbCarriers.ScrollToLine(tbCarriers.SelectedIndices[0]);
    //}
    private void textCarrier_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textPhone_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Carrier carrier = Carriers.GetCarrier(PIn.Long(table.Rows[e.getRow()]["CarrierNum"].ToString()));
        if (IsSelectMode)
        {
            SelectedCarrier = carrier;
            DialogResult = DialogResult.OK;
            return ;
        }
         
        FormCarrierEdit FormCE = new FormCarrierEdit();
        FormCE.CarrierCur = carrier;
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillGrid();
    }

    private void checkCDAnet_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void checkShowHidden_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormCarrierEdit FormCE = new FormCarrierEdit();
        FormCE.IsNew = true;
        Carrier carrier = new Carrier();
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            carrier.IsCDA = true;
        }
         
        carrier.CarrierName = textCarrier.Text;
        //The phone number will get formated while the user types inside the carrier edit window.
        //However, the user could have typed in a poorly formatted number so we will reformat the number once before load.
        String phoneFormatted = TelephoneNumbers.ReFormat(textPhone.Text);
        carrier.Phone = phoneFormatted;
        FormCE.CarrierCur = carrier;
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        //Load the name and phone number of the newly added carrier to the search fields so that the new carrier shows up in the grid.
        textCarrier.Text = FormCE.CarrierCur.CarrierName;
        textPhone.Text = FormCE.CarrierCur.Phone;
        fillGrid();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (FormCE.CarrierCur.CarrierNum.ToString() == table.Rows[i]["CarrierNum"].ToString())
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butCombine_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 2)
        {
            MessageBox.Show(Lan.g(this,"Please select multiple items first while holding down the control key."));
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Combine all these carriers into a single carrier? This will affect all patients using these carriers.  The next window will let you select which carrier to keep when combining."), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        List<long> pickedCarrierNums = new List<long>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            pickedCarrierNums.Add(PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["CarrierNum"].ToString()));
        }
        FormCarrierCombine FormCB = new FormCarrierCombine();
        FormCB.CarrierNums = pickedCarrierNums;
        FormCB.ShowDialog();
        if (FormCB.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        try
        {
            //int[] combCarrierNums=new int[tbCarriers.SelectedIndices.Length];
            //for(int i=0;i<tbCarriers.SelectedIndices.Length;i++){
            //	carrierNums[i]=Carriers.List[tbCarriers.SelectedIndices[i]].CarrierNum;
            //}
            //Prepare audit trail entry data, then combine, then make audit trail entries if successful
            List<String> carrierNames = new List<String>();
            List<List<long>> insPlanNums = new List<List<long>>();
            String carrierTo = Carriers.getName(FormCB.PickedCarrierNum);
            for (int i = 0;i < pickedCarrierNums.Count;i++)
            {
                carrierNames.Add(Carriers.GetName(pickedCarrierNums[i]));
                insPlanNums.Add(InsPlans.GetPlanNumsByCarrierNum(pickedCarrierNums[i]));
            }
            Carriers.Combine(pickedCarrierNums, FormCB.PickedCarrierNum);
            for (int i = 0;i < insPlanNums.Count;i++)
            {
                for (int j = 0;j < insPlanNums[i].Count;j++)
                {
                    //Carriers were combined successfully. Loop through all the associated insplans and make a securitylog entry that their carrier changed.
                    SecurityLogs.MakeLogEntry(Permissions.InsPlanChangeCarrierName, 0, Lan.g(this,"Carrier with name ") + " " + carrierNames[i] + " " + Lan.g(this,"was merged with") + " " + carrierTo, insPlanNums[i][j]);
                }
            }
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //only visible if IsSelectMode
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 1)
        {
            MessageBox.Show(Lan.g(this,"Please select only one item first."));
            return ;
        }
         
        SelectedCarrier = Carriers.GetCarrier(PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["CarrierNum"].ToString()));
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formCarriers_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        //it doesn't matter whether the user hits ok, or cancel for this to happen
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Carriers);
        }
         
    }

}


