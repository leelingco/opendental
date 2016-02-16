//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:31 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormDefinitions;
import OpenDental.FormProcButtonEdit;
import OpenDental.FormProcButtons;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProcButton;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormProcButtons  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private IContainer components = new IContainer();
    private Label label1 = new Label();
    private Label label2 = new Label();
    private ListBox listCategories = new ListBox();
    private OpenDental.UI.Button butEdit;
    private boolean changed = new boolean();
    /**
    * defnum
    */
    private long selectedCat = new long();
    private ListView listViewButtons = new ListView();
    private ColumnHeader columnHeader1 = new ColumnHeader();
    private ImageList imageListProcButtons = new ImageList();
    /**
    * This list of displayed buttons for the selected cat.
    */
    private ProcButton[] ButtonList = new ProcButton[]();
    /**
    * 
    */
    public FormProcButtons() throws Exception {
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

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcButtons.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listCategories = new System.Windows.Forms.ListBox();
        this.listViewButtons = new System.Windows.Forms.ListView();
        this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
        this.imageListProcButtons = new System.Windows.Forms.ImageList(this.components);
        this.butEdit = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(324, 33);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(237, 22);
        this.label1.TabIndex = 36;
        this.label1.Text = "Buttons for the selected category";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(36, 33);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(237, 22);
        this.label2.TabIndex = 38;
        this.label2.Text = "Button Categories";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listCategories
        //
        this.listCategories.Location = new System.Drawing.Point(38, 59);
        this.listCategories.Name = "listCategories";
        this.listCategories.Size = new System.Drawing.Size(234, 316);
        this.listCategories.TabIndex = 37;
        this.listCategories.Click += new System.EventHandler(this.listCategories_Click);
        //
        // listViewButtons
        //
        this.listViewButtons.Activation = System.Windows.Forms.ItemActivation.OneClick;
        this.listViewButtons.AutoArrange = false;
        this.listViewButtons.Columns.AddRange(new System.Windows.Forms.ColumnHeader[]{ this.columnHeader1 });
        this.listViewButtons.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.None;
        this.listViewButtons.HideSelection = false;
        this.listViewButtons.Location = new System.Drawing.Point(326, 59);
        this.listViewButtons.MultiSelect = false;
        this.listViewButtons.Name = "listViewButtons";
        this.listViewButtons.Size = new System.Drawing.Size(234, 316);
        this.listViewButtons.SmallImageList = this.imageListProcButtons;
        this.listViewButtons.TabIndex = 189;
        this.listViewButtons.UseCompatibleStateImageBehavior = false;
        this.listViewButtons.View = System.Windows.Forms.View.Details;
        this.listViewButtons.DoubleClick += new System.EventHandler(this.listViewButtons_DoubleClick);
        //
        // columnHeader1
        //
        this.columnHeader1.Width = 155;
        //
        // imageListProcButtons
        //
        this.imageListProcButtons.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListProcButtons.ImageStream")));
        this.imageListProcButtons.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListProcButtons.Images.SetKeyName(0, "deposit.gif");
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Image = Resources.getAdd();
        this.butEdit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEdit.Location = new System.Drawing.Point(38, 395);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(109, 26);
        this.butEdit.TabIndex = 39;
        this.butEdit.Text = "Edit Categories";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(478, 433);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(82, 26);
        this.butDown.TabIndex = 34;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(478, 395);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(82, 26);
        this.butUp.TabIndex = 35;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
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
        this.butAdd.Location = new System.Drawing.Point(326, 395);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(82, 26);
        this.butAdd.TabIndex = 32;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
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
        this.butDelete.Location = new System.Drawing.Point(326, 433);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(82, 26);
        this.butDelete.TabIndex = 33;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
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
        this.butClose.Location = new System.Drawing.Point(648, 433);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 8;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormProcButtons
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(746, 483);
        this.Controls.Add(this.listViewButtons);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listCategories);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcButtons";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Procedure Buttons";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormProcButtons_Closing);
        this.Load += new System.EventHandler(this.FormChartProcedureEntry_Load);
        this.ResumeLayout(false);
    }

    private void formChartProcedureEntry_Load(Object sender, System.EventArgs e) throws Exception {
        fillCategories();
        fillButtons();
    }

    private void fillCategories() throws Exception {
        listCategories.Items.Clear();
        if (DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()].Length == 0)
        {
            selectedCat = 0;
            MsgBox.show(this,"You must have at least one category setup.");
            return ;
        }
         
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()].Length;i++)
        {
            listCategories.Items.Add(DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][i].ItemName);
            if (selectedCat == DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][i].DefNum)
            {
                listCategories.SelectedIndex = i;
            }
             
        }
        if (listCategories.SelectedIndex == -1)
        {
            //category was hidden, or just openning the form
            listCategories.SelectedIndex = 0;
        }
         
        selectedCat = DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][listCategories.SelectedIndex].DefNum;
    }

    private void fillButtons() throws Exception {
        listViewButtons.Items.Clear();
        imageListProcButtons.Images.Clear();
        if (selectedCat == 0)
        {
            ButtonList = new ProcButton[0];
            return ;
        }
         
        ProcButtons.refreshCache();
        ButtonList = ProcButtons.getForCat(selectedCat);
        for (int i = 0;i < ButtonList.Length;i++)
        {
            //first check and fix any order problems
            if (ButtonList[i].ItemOrder != i)
            {
                ButtonList[i].ItemOrder = i;
                ProcButtons.Update(ButtonList[i]);
            }
             
        }
        ListViewItem item = new ListViewItem();
        for (int i = 0;i < ButtonList.Length;i++)
        {
            if (!StringSupport.equals(ButtonList[i].ButtonImage, ""))
            {
                try
                {
                    //image keys are simply the ProcButtonNum
                    imageListProcButtons.Images.Add(ButtonList[i].ProcButtonNum.ToString(), PIn.Bitmap(ButtonList[i].ButtonImage));
                }
                catch (Exception __dummyCatchVar0)
                {
                    imageListProcButtons.Images.Add(new Bitmap(20, 20));
                }
            
            }
             
            //Add a blank image so the list stays in synch
            item = new ListViewItem(new String[]{ ButtonList[i].Description }, ButtonList[i].ProcButtonNum.ToString());
            listViewButtons.Items.Add(item);
        }
    }

    private void listViewButtons_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listViewButtons.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        ProcButton but = ButtonList[listViewButtons.SelectedIndices[0]].Copy();
        FormProcButtonEdit FormPBE = new FormProcButtonEdit(but);
        FormPBE.ShowDialog();
        changed = true;
        fillButtons();
    }

    private void listCategories_Click(Object sender, EventArgs e) throws Exception {
        if (listCategories.SelectedIndex == -1)
        {
            return ;
        }
         
        selectedCat = DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][listCategories.SelectedIndex].DefNum;
        fillButtons();
    }

    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions FormD = new FormDefinitions(DefCat.ProcButtonCats);
        FormD.ShowDialog();
        fillCategories();
        fillButtons();
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        int selected = 0;
        if (listViewButtons.SelectedIndices.Count == 0)
        {
            return ;
        }
        else if (listViewButtons.SelectedIndices[0] == listViewButtons.Items.Count - 1)
        {
            return ;
        }
        else
        {
            ProcButton but = ButtonList[listViewButtons.SelectedIndices[0]].Copy();
            but.ItemOrder++;
            ProcButtons.update(but);
            selected = but.ItemOrder;
            but = ButtonList[listViewButtons.SelectedIndices[0] + 1].Copy();
            but.ItemOrder--;
            ProcButtons.update(but);
        }  
        fillButtons();
        changed = true;
        listViewButtons.SelectedIndices.Clear();
        listViewButtons.SelectedIndices.Add(selected);
    }

    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        int selected = 0;
        if (listViewButtons.SelectedIndices.Count == 0)
        {
            return ;
        }
        else if (listViewButtons.SelectedIndices[0] == 0)
        {
            return ;
        }
        else
        {
            ProcButton but = ButtonList[listViewButtons.SelectedIndices[0]].Copy();
            but.ItemOrder--;
            ProcButtons.update(but);
            selected = but.ItemOrder;
            but = ButtonList[listViewButtons.SelectedIndices[0] - 1].Copy();
            but.ItemOrder++;
            ProcButtons.update(but);
        }  
        fillButtons();
        changed = true;
        listViewButtons.SelectedIndices.Clear();
        listViewButtons.SelectedIndices.Add(selected);
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCategories.SelectedIndex == -1)
        {
            return ;
        }
         
        ProcButton but = new ProcButton();
        but.Category = selectedCat;
        but.ItemOrder = listViewButtons.Items.Count;
        FormProcButtonEdit FormPBE = new FormProcButtonEdit(but);
        FormPBE.IsNew = true;
        FormPBE.ShowDialog();
        changed = true;
        fillButtons();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (listViewButtons.SelectedIndices.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        ProcButtons.Delete(ButtonList[listViewButtons.SelectedIndices[0]]);
        changed = true;
        fillButtons();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formProcButtons_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.ProcButtons);
        }
         
    }

}


