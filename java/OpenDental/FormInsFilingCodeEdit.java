//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:15 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormInsFilingCodeEdit;
import OpenDental.FormInsFilingCodeSubtypeEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.InsFilingCodes;
import OpenDentBusiness.InsFilingCodeSubtype;
import OpenDentBusiness.InsFilingCodeSubtypes;

/**
* Summary description for FormBasicTemplate.
*/
public class FormInsFilingCodeEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textEclaimCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridInsFilingCodeSubtypes;
    public InsFilingCode InsFilingCodeCur;
    private OpenDental.UI.Button butAdd;
    private List<InsFilingCodeSubtype> insFilingCodeSubtypes = new List<InsFilingCodeSubtype>();
    /**
    * 
    */
    public FormInsFilingCodeEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsFilingCodeEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.textEclaimCode = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.gridInsFilingCodeSubtypes = new OpenDental.UI.ODGrid();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(9, 21);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(160, 20);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(291, 20);
        this.textDescription.TabIndex = 0;
        this.textDescription.TextChanged += new System.EventHandler(this.textDescription_TextChanged);
        //
        // textEclaimCode
        //
        this.textEclaimCode.Location = new System.Drawing.Point(160, 40);
        this.textEclaimCode.MaxLength = 255;
        this.textEclaimCode.Name = "textEclaimCode";
        this.textEclaimCode.Size = new System.Drawing.Size(157, 20);
        this.textEclaimCode.TabIndex = 1;
        this.textEclaimCode.TextChanged += new System.EventHandler(this.textEclaimCode_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 43);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(151, 17);
        this.label2.TabIndex = 99;
        this.label2.Text = "Eclaim Code";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridInsFilingCodeSubtypes
        //
        this.gridInsFilingCodeSubtypes.setHScrollVisible(false);
        this.gridInsFilingCodeSubtypes.Location = new System.Drawing.Point(160, 87);
        this.gridInsFilingCodeSubtypes.Name = "gridInsFilingCodeSubtypes";
        this.gridInsFilingCodeSubtypes.setScrollValue(0);
        this.gridInsFilingCodeSubtypes.Size = new System.Drawing.Size(291, 153);
        this.gridInsFilingCodeSubtypes.TabIndex = 100;
        this.gridInsFilingCodeSubtypes.setTitle("Insurance Filing Code Subtypes");
        this.gridInsFilingCodeSubtypes.setTranslationName("TableInsFilingCodeSubtypes");
        this.gridInsFilingCodeSubtypes.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridInsFilingCodeSubtypes.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridInsFilingCodeSubtypes_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
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
        this.butDelete.Location = new System.Drawing.Point(27, 302);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 26);
        this.butDelete.TabIndex = 4;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(412, 302);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 9;
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
        this.butCancel.Location = new System.Drawing.Point(503, 302);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Enabled = false;
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(160, 246);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 101;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormInsFilingCodeEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(604, 346);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridInsFilingCodeSubtypes);
        this.Controls.Add(this.textEclaimCode);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsFilingCodeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Claim Filing Code";
        this.Load += new System.EventHandler(this.FormInsFilingCodeEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsFilingCodeEdit_Load(Object sender, System.EventArgs e) throws Exception {
        textDescription.Text = InsFilingCodeCur.Descript;
        textEclaimCode.Text = InsFilingCodeCur.EclaimCode;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        InsFilingCodeSubtypes.refreshCache();
        insFilingCodeSubtypes = InsFilingCodeSubtypes.getForInsFilingCode(InsFilingCodeCur.InsFilingCodeNum);
        gridInsFilingCodeSubtypes.beginUpdate();
        gridInsFilingCodeSubtypes.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableInsFilingCodes","Description"),100);
        gridInsFilingCodeSubtypes.getColumns().add(col);
        gridInsFilingCodeSubtypes.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < insFilingCodeSubtypes.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(insFilingCodeSubtypes[i].Descript);
            gridInsFilingCodeSubtypes.getRows().add(row);
        }
        gridInsFilingCodeSubtypes.endUpdate();
    }

    private void gridInsFilingCodeSubtypes_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormInsFilingCodeSubtypeEdit FormI = new FormInsFilingCodeSubtypeEdit();
        FormI.InsFilingCodeSubtypeCur = insFilingCodeSubtypes[e.getRow()].Clone();
        FormI.ShowDialog();
        if (FormI.DialogResult == DialogResult.OK)
        {
            try
            {
                InsFilingCodeSubtypes.update(FormI.InsFilingCodeSubtypeCur);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormInsFilingCodeSubtypeEdit FormI = new FormInsFilingCodeSubtypeEdit();
        FormI.InsFilingCodeSubtypeCur = new InsFilingCodeSubtype();
        FormI.InsFilingCodeSubtypeCur.setIsNew(true);
        FormI.ShowDialog();
        if (FormI.DialogResult == DialogResult.OK)
        {
            if (InsFilingCodeCur.getIsNew())
            {
                //If we are adding a subtype to a new filing code, then we need to
                //save the filing code to the database to generate the InsFilingCodeNum,
                //so that we can then save teh InsFilingCodeSubtype record with the correct
                //foreign key.
                saveFilingCode();
                InsFilingCodeCur.setIsNew(false);
            }
             
            FormI.InsFilingCodeSubtypeCur.InsFilingCodeNum = InsFilingCodeCur.InsFilingCodeNum;
            try
            {
                InsFilingCodeSubtypes.insert(FormI.InsFilingCodeSubtypeCur);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }

            fillGrid();
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (InsFilingCodeCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete this code?"))
        {
            return ;
        }
         
        try
        {
            InsFilingCodes.delete(InsFilingCodeCur.InsFilingCodeNum);
            InsFilingCodeSubtypes.deleteForInsFilingCode(InsFilingCodeCur.InsFilingCodeNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(this.textDescription.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Please enter a description."));
            return ;
        }
         
        if (StringSupport.equals(this.textEclaimCode.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Please enter an electronic claim code."));
            return ;
        }
         
        saveFilingCode();
        DialogResult = DialogResult.OK;
    }

    private void saveFilingCode() throws Exception {
        InsFilingCodeCur.Descript = textDescription.Text;
        InsFilingCodeCur.EclaimCode = textEclaimCode.Text;
        try
        {
            if (InsFilingCodeCur.getIsNew())
            {
                InsFilingCodes.insert(InsFilingCodeCur);
            }
            else
            {
                InsFilingCodes.update(InsFilingCodeCur);
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }
    
    }

    private void checkSubtypeButtonEnabled() throws Exception {
        this.butAdd.Enabled = (!StringSupport.equals(this.textDescription.Text, "") && !StringSupport.equals(this.textEclaimCode.Text, ""));
    }

    private void textDescription_TextChanged(Object sender, EventArgs e) throws Exception {
        checkSubtypeButtonEnabled();
    }

    private void textEclaimCode_TextChanged(Object sender, EventArgs e) throws Exception {
        checkSubtypeButtonEnabled();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


