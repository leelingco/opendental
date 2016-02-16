//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:44 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.CellEventArgs;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.FormCarrierCombine;
import OpenDental.Lan;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormCarrierCombine  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.Forms.TableCarriers tbCarriers;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * After this window closes, this will be the carrierNum of the selected carrier.
    */
    public long PickedCarrierNum = new long();
    /**
    * Before opening this Form, set the carrierNums to show.
    */
    public List<long> CarrierNums = new List<long>();
    private List<Carrier> carrierList = new List<Carrier>();
    /**
    * 
    */
    public FormCarrierCombine() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCarrierCombine.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.tbCarriers = new OpenDental.Forms.TableCarriers();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(773, 465);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(773, 424);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // tbCarriers
        //
        this.tbCarriers.BackColor = System.Drawing.SystemColors.Window;
        this.tbCarriers.Location = new System.Drawing.Point(9, 42);
        this.tbCarriers.Name = "tbCarriers";
        this.tbCarriers.setScrollValue(363);
        this.tbCarriers.setSelectedIndices(new int[0]);
        this.tbCarriers.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.tbCarriers.Size = new System.Drawing.Size(839, 356);
        this.tbCarriers.TabIndex = 2;
        this.tbCarriers.CellDoubleClicked = __MultiCellEventHandler.combine(this.tbCarriers.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, CellEventArgs e) throws Exception {
                this.tbCarriers_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(13, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(476, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Please select the carrier to keep when combining";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormCarrierCombine
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(880, 511);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.tbCarriers);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormCarrierCombine";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Combine Carriers";
        this.Load += new System.EventHandler(this.FormCarrierCombine_Load);
        this.ResumeLayout(false);
    }

    private void formCarrierCombine_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        carrierList = Carriers.getCarriers(CarrierNums);
        tbCarriers.ResetRows(carrierList.Count);
        tbCarriers.SetGridColor(Color.Gray);
        tbCarriers.SetBackGColor(Color.White);
        for (int i = 0;i < carrierList.Count;i++)
        {
            tbCarriers.Cell[0, i] = carrierList[i].CarrierName;
            tbCarriers.Cell[1, i] = carrierList[i].Phone;
            tbCarriers.Cell[2, i] = carrierList[i].Address;
            tbCarriers.Cell[3, i] = carrierList[i].Address2;
            tbCarriers.Cell[4, i] = carrierList[i].City;
            tbCarriers.Cell[5, i] = carrierList[i].State;
            tbCarriers.Cell[6, i] = carrierList[i].Zip;
            tbCarriers.Cell[7, i] = carrierList[i].ElectID;
        }
        tbCarriers.layoutTables();
    }

    private void tbCarriers_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        PickedCarrierNum = carrierList[e.getRow()].CarrierNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (tbCarriers.SelectedRow == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        PickedCarrierNum = carrierList[tbCarriers.SelectedRow].CarrierNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


