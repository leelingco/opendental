//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:54 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.FormContactEdit;
import OpenDental.FormContacts;
import OpenDental.Lan;
import OpenDentBusiness.Contact;
import OpenDentBusiness.Contacts;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;

/**
* Summary description for FormBasicTemplate.
*/
public class FormContacts  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listCategory = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private OpenDental.TableContacts tbContacts;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Contact[] ContactList = new Contact[]();
    /**
    * 
    */
    public FormContacts() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        tbContacts.CellDoubleClicked = __MultiCellEventHandler.combine(tbContacts.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbContacts_CellDoubleClicked(sender, e);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormContacts.class);
        this.butOK = new OpenDental.UI.Button();
        this.listCategory = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.tbContacts = new OpenDental.TableContacts();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butOK.Location = new System.Drawing.Point(798, 627);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&Close";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listCategory
        //
        this.listCategory.Location = new System.Drawing.Point(5, 37);
        this.listCategory.Name = "listCategory";
        this.listCategory.Size = new System.Drawing.Size(101, 264);
        this.listCategory.TabIndex = 2;
        this.listCategory.SelectedIndexChanged += new System.EventHandler(this.listCategory_SelectedIndexChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 16);
        this.label1.TabIndex = 3;
        this.label1.Text = "Category";
        //
        // tbContacts
        //
        this.tbContacts.BackColor = System.Drawing.SystemColors.Window;
        this.tbContacts.Location = new System.Drawing.Point(117, 11);
        this.tbContacts.Name = "tbContacts";
        this.tbContacts.setScrollValue(1);
        this.tbContacts.setSelectedIndices(new int[0]);
        this.tbContacts.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.tbContacts.Size = new System.Drawing.Size(669, 671);
        this.tbContacts.TabIndex = 4;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(797, 494);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 25);
        this.butAdd.TabIndex = 5;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormContacts
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butOK;
        this.ClientSize = new System.Drawing.Size(886, 693);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.tbContacts);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listCategory);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormContacts";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Contacts";
        this.Load += new System.EventHandler(this.FormContacts_Load);
        this.ResumeLayout(false);
    }

    private void formContacts_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ContactCategories).ordinal()].Length;i++)
        {
            listCategory.Items.Add(DefC.getShort()[((Enum)DefCat.ContactCategories).ordinal()][i].ItemName);
        }
        if (listCategory.Items.Count > 0)
            listCategory.SelectedIndex = 0;
         
    }

    private void listCategory_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ContactList = Contacts.Refresh(DefC.getShort()[((Enum)DefCat.ContactCategories).ordinal()][listCategory.SelectedIndex].DefNum);
        tbContacts.ResetRows(ContactList.Length);
        tbContacts.SetGridColor(Color.Gray);
        tbContacts.SetBackGColor(Color.White);
        for (int i = 0;i < ContactList.Length;i++)
        {
            tbContacts.Cell[0, i] = ContactList[i].LName;
            tbContacts.Cell[1, i] = ContactList[i].FName;
            tbContacts.Cell[2, i] = ContactList[i].WkPhone;
            tbContacts.Cell[3, i] = ContactList[i].Fax;
            tbContacts.Cell[4, i] = ContactList[i].Notes;
        }
        tbContacts.layoutTables();
    }

    private void tbContacts_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        FormContactEdit FormCE = new FormContactEdit();
        FormCE.ContactCur = ContactList[e.getRow()];
        FormCE.ShowDialog();
        if (FormCE.DialogResult == DialogResult.OK)
            fillGrid();
         
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Contact ContactCur = new Contact();
        ContactCur.Category = DefC.getShort()[((Enum)DefCat.ContactCategories).ordinal()][listCategory.SelectedIndex].DefNum;
        FormContactEdit FormCE = new FormContactEdit();
        FormCE.ContactCur = ContactCur;
        FormCE.IsNew = true;
        FormCE.ShowDialog();
        if (FormCE.DialogResult == DialogResult.OK)
            fillGrid();
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


