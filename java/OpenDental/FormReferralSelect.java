//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:40 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPatientSelect;
import OpenDental.FormReferralEdit;
import OpenDental.FormReferralSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormReferralSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.CheckBox checkHidden = new System.Windows.Forms.CheckBox();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsSelectionMode = new boolean();
    private OpenDental.UI.Button butAdd;
    private List<Referral> listRef = new List<Referral>();
    private OpenDental.UI.ODGrid gridMain;
    private TextBox textSearch = new TextBox();
    private Label label1 = new Label();
    private Label labelResultCount = new Label();
    /**
    * This will contain the referral that was selected.
    */
    public Referral SelectedReferral;
    /**
    * 
    */
    public FormReferralSelect() throws Exception {
        initializeComponent();
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReferralSelect.class);
        this.checkHidden = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.labelResultCount = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // checkHidden
        //
        this.checkHidden.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.checkHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHidden.Location = new System.Drawing.Point(844, 6);
        this.checkHidden.Name = "checkHidden";
        this.checkHidden.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkHidden.Size = new System.Drawing.Size(104, 24);
        this.checkHidden.TabIndex = 11;
        this.checkHidden.Text = "Show Hidden  ";
        this.checkHidden.Click += new System.EventHandler(this.checkHidden_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 34);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(940, 618);
        this.gridMain.TabIndex = 15;
        this.gridMain.setTitle("Select Referral");
        this.gridMain.setTranslationName("TableSelectReferral");
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
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(106, 8);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(201, 20);
        this.textSearch.TabIndex = 0;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 10);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 17;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelResultCount
        //
        this.labelResultCount.Location = new System.Drawing.Point(313, 10);
        this.labelResultCount.Name = "labelResultCount";
        this.labelResultCount.Size = new System.Drawing.Size(129, 17);
        this.labelResultCount.TabIndex = 18;
        this.labelResultCount.Text = "# results found";
        this.labelResultCount.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(8, 661);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 12;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butCancel.ImageAlign = System.Drawing.ContentAlignment.TopRight;
        this.butCancel.Location = new System.Drawing.Point(873, 661);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 6;
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
        this.butOK.Location = new System.Drawing.Point(785, 661);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormReferralSelect
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(962, 696);
        this.Controls.Add(this.labelResultCount);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.checkHidden);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReferralSelect";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Referrals";
        this.Load += new System.EventHandler(this.FormReferralSelect_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formReferralSelect_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.ReferralAdd,true))
        {
            butAdd.Enabled = false;
        }
         
        fillTable();
    }

    //labelResultCount.Text="";
    private void fillTable() throws Exception {
        Referrals.refreshCache();
        listRef = new List<Referral>();
        for (int i = 0;i < Referrals.getList().Length;i++)
        {
            if (!checkHidden.Checked)
            {
                //don't include hidden
                if (Referrals.getList()[i].IsHidden)
                {
                    continue;
                }
                 
            }
             
            //if hidden
            if (!StringSupport.equals(textSearch.Text, ""))
            {
                if (!Referrals.getList()[i].LName.ToLower().StartsWith(textSearch.Text.ToLower()))
                {
                    continue;
                }
                 
            }
             
            //no match
            listRef.Add(Referrals.getList()[i]);
        }
        int scrollValue = gridMain.getScrollValue();
        long selectedRefNum = -1;
        if (gridMain.getSelectedIndex() != -1)
        {
            selectedRefNum = ((Referral)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag()).ReferralNum;
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lans.g("TableSelectRefferal","LastName"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","FirstName"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","MI"),30);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","Title"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","Specialty"),60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","Patient"),45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lans.g("TableSelectRefferal","Note"),250);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listRef.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listRef[i].LName);
            row.getCells().Add(listRef[i].FName);
            if (!StringSupport.equals(listRef[i].MName, ""))
            {
                row.getCells().Add(listRef[i].MName.Substring(0, 1).ToUpper());
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(listRef[i].Title);
            if (listRef[i].IsDoctor)
            {
                row.getCells().Add(Lan.g("enumDentalSpecialty", ((DentalSpecialty)(listRef[i].Specialty)).ToString()));
            }
            else
            {
                row.getCells().add("");
            } 
            if (listRef[i].PatNum > 0)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(listRef[i].Note);
            if (listRef[i].IsHidden)
            {
                row.setColorText(Color.Gray);
            }
             
            row.setTag(listRef[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        labelResultCount.Text = gridMain.getRows().Count.ToString() + " results found.";
        gridMain.setScrollValue(scrollValue);
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (((Referral)gridMain.getRows().get___idx(i).getTag()).ReferralNum == selectedRefNum)
            {
                gridMain.setSelected(i,true);
                break;
            }
             
        }
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //This does not automatically select a referral when in selection mode; it just lets user edit.
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a referral first");
            return ;
        }
         
        FormReferralEdit FormRE = new FormReferralEdit(listRef[e.getRow()]);
        FormRE.ShowDialog();
        if (FormRE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //int selectedIndex=gridMain.GetSelectedIndex();
        fillTable();
    }

    //gridMain.SetSelected(selectedIndex,true);
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Referral refCur = new Referral();
        boolean referralIsNew = true;
        if (MessageBox.Show(Lan.g(this,"Is the referral source an existing patient?"), "", MessageBoxButtons.YesNo) == DialogResult.Yes)
        {
            FormPatientSelect FormPS = new FormPatientSelect();
            FormPS.SelectionModeOnly = true;
            FormPS.ShowDialog();
            if (FormPS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            refCur.PatNum = FormPS.SelectedPatNum;
            for (int i = 0;i < Referrals.getList().Length;i++)
            {
                if (Referrals.getList()[i].PatNum == FormPS.SelectedPatNum)
                {
                    //referral already existed
                    refCur = Referrals.getList()[i];
                    referralIsNew = false;
                    break;
                }
                 
            }
        }
         
        FormReferralEdit FormRE2 = new FormReferralEdit(refCur);
        //the ReferralNum must be added here
        FormRE2.IsNew = referralIsNew;
        FormRE2.ShowDialog();
        if (FormRE2.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        if (IsSelectionMode)
        {
            SelectedReferral = FormRE2.RefCur;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            fillTable();
            for (int i = 0;i < listRef.Count;i++)
            {
                if (listRef[i].ReferralNum == FormRE2.RefCur.ReferralNum)
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
        } 
    }

    private void checkHidden_Click(Object sender, System.EventArgs e) throws Exception {
        fillTable();
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        fillTable();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            if (gridMain.getSelectedIndex() == -1)
            {
                MsgBox.show(this,"Please select a referral first");
                return ;
            }
             
            SelectedReferral = (Referral)listRef[gridMain.getSelectedIndex()];
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


