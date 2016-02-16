//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:01 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DataValid;
import OpenDental.FormZipCodeEdit;
import OpenDental.FormZipCodes;
import OpenDental.Lan;
import OpenDental.ZipCode;
import OpenDental.ZipCodes;
import OpenDentBusiness.InvalidType;

/**
* 
*/
public class FormZipCodes  extends System.Windows.Forms.Form 
{
    private OpenDental.TableZips tbZips;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormZipCodes() throws Exception {
        initializeComponent();
        tbZips.CellDoubleClicked = __MultiCellEventHandler.combine(tbZips.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbZips_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        Lan.f(this);
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormZipCodes.class);
        this.tbZips = new OpenDental.TableZips();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // tbZips
        //
        this.tbZips.BackColor = System.Drawing.SystemColors.Window;
        this.tbZips.Location = new System.Drawing.Point(19, 14);
        this.tbZips.Name = "tbZips";
        this.tbZips.setScrollValue(1);
        this.tbZips.setSelectedIndices(new int[0]);
        this.tbZips.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.tbZips.Size = new System.Drawing.Size(519, 531);
        this.tbZips.TabIndex = 25;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(615, 374);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(76, 26);
        this.butAdd.TabIndex = 28;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(615, 513);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(76, 26);
        this.butClose.TabIndex = 26;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(615, 410);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(76, 26);
        this.butDelete.TabIndex = 31;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormZipCodes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(715, 563);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.tbZips);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormZipCodes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Zip Codes";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormZipCodes_Closing);
        this.Load += new System.EventHandler(this.FormZipCodes_Load);
        this.ResumeLayout(false);
    }

    private void formZipCodes_Load(Object sender, System.EventArgs e) throws Exception {
        fillTable();
    }

    private void fillTable() throws Exception {
        ZipCodes.RefreshCache();
        tbZips.ResetRows(ZipCodes.List.Length);
        tbZips.SetGridColor(Color.Gray);
        tbZips.SetBackGColor(Color.White);
        for (int i = 0;i < ZipCodes.List.Length;i++)
        {
            tbZips.Cell[0, i] = ZipCodes.List[i].ZipCodeDigits;
            tbZips.Cell[1, i] = ZipCodes.List[i].City;
            tbZips.Cell[2, i] = ZipCodes.List[i].State;
            if (ZipCodes.List[i].IsFrequent)
            {
                tbZips.Cell[3, i] = "X";
            }
             
        }
        tbZips.SelectedRow = -1;
        tbZips.layoutTables();
    }

    private void tbZips_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        if (tbZips.SelectedRow == -1)
        {
            return ;
        }
         
        FormZipCodeEdit FormZCE = new FormZipCodeEdit();
        FormZCE.ZipCodeCur = ZipCodes.List[tbZips.SelectedRow];
        FormZCE.ShowDialog();
        if (FormZCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillTable();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (tbZips.SelectedRow == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        ZipCode ZipCur = ZipCodes.List[tbZips.SelectedRow];
        if (MessageBox.Show(Lan.g(this,"Delete Zipcode?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        ZipCodes.Delete(ZipCur);
        fillTable();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormZipCodeEdit FormZCE = new FormZipCodeEdit();
        FormZCE.ZipCodeCur = new ZipCode();
        FormZCE.IsNew = true;
        FormZCE.ShowDialog();
        if (FormZCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillTable();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formZipCodes_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ZipCodes);
        }
         
    }

}


