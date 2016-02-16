//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:36 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormQuickPaste;
import OpenDental.FormQuickPasteCat;
import OpenDental.FormQuickPasteNoteEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.QuickPasteNote;
import OpenDental.QuickPasteNotes;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.QuickPasteCat;
import OpenDentBusiness.QuickPasteCats;
import OpenDentBusiness.Security;

/**
* Summary description for FormBasicTemplate.
*/
public class FormQuickPaste  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listCat = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listNote = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butDownCat;
    private OpenDental.UI.Button butUpCat;
    private OpenDental.UI.Button butAddCat;
    private OpenDental.UI.Button butDeleteCat;
    private OpenDental.UI.Button butAddNote;
    private OpenDental.UI.Button butDownNote;
    private OpenDental.UI.Button butUpNote;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private QuickPasteNote[] notesForCat = new QuickPasteNote[]();
    private OpenDental.UI.Button butEditNote;
    private OpenDental.UI.Button butDate;
    //<summary>This is the note that gets passed back to the calling function.</summary>
    //public string SelectedNote;
    private boolean localChanged = new boolean();
    /**
    * Set this property before calling this form. It will insert the value into this textbox.
    */
    public System.Windows.Forms.RichTextBox TextToFill = new System.Windows.Forms.RichTextBox();
    /**
    * 
    */
    public OpenDentBusiness.QuickPasteType QuickType = OpenDentBusiness.QuickPasteType.None;
    /**
    * 
    */
    public FormQuickPaste() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQuickPaste.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listCat = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listNote = new System.Windows.Forms.ListBox();
        this.butDownCat = new OpenDental.UI.Button();
        this.butUpCat = new OpenDental.UI.Button();
        this.butAddCat = new OpenDental.UI.Button();
        this.butDeleteCat = new OpenDental.UI.Button();
        this.butAddNote = new OpenDental.UI.Button();
        this.butDownNote = new OpenDental.UI.Button();
        this.butUpNote = new OpenDental.UI.Button();
        this.butEditNote = new OpenDental.UI.Button();
        this.butDate = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(827, 641);
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
        this.butOK.Location = new System.Drawing.Point(734, 641);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listCat
        //
        this.listCat.Location = new System.Drawing.Point(8, 25);
        this.listCat.Name = "listCat";
        this.listCat.Size = new System.Drawing.Size(169, 316);
        this.listCat.TabIndex = 2;
        this.listCat.DoubleClick += new System.EventHandler(this.listCat_DoubleClick);
        this.listCat.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listCat_MouseDown);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 4);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 19);
        this.label1.TabIndex = 3;
        this.label1.Text = "Category";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(181, 5);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 19);
        this.label2.TabIndex = 5;
        this.label2.Text = "Note";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listNote
        //
        this.listNote.Location = new System.Drawing.Point(181, 25);
        this.listNote.Name = "listNote";
        this.listNote.Size = new System.Drawing.Size(719, 602);
        this.listNote.TabIndex = 4;
        this.listNote.DoubleClick += new System.EventHandler(this.listNote_DoubleClick);
        this.listNote.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listNote_MouseDown);
        //
        // butDownCat
        //
        this.butDownCat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownCat.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butDownCat.setAutosize(true);
        this.butDownCat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownCat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownCat.setCornerRadius(4F);
        this.butDownCat.Image = Resources.getdown();
        this.butDownCat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDownCat.Location = new System.Drawing.Point(98, 383);
        this.butDownCat.Name = "butDownCat";
        this.butDownCat.Size = new System.Drawing.Size(79, 26);
        this.butDownCat.TabIndex = 11;
        this.butDownCat.Text = "Down";
        this.butDownCat.Click += new System.EventHandler(this.butDownCat_Click);
        //
        // butUpCat
        //
        this.butUpCat.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUpCat.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butUpCat.setAutosize(true);
        this.butUpCat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUpCat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUpCat.setCornerRadius(4F);
        this.butUpCat.Image = Resources.getup();
        this.butUpCat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUpCat.Location = new System.Drawing.Point(98, 348);
        this.butUpCat.Name = "butUpCat";
        this.butUpCat.Size = new System.Drawing.Size(79, 26);
        this.butUpCat.TabIndex = 10;
        this.butUpCat.Text = "Up";
        this.butUpCat.Click += new System.EventHandler(this.butUpCat_Click);
        //
        // butAddCat
        //
        this.butAddCat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddCat.setAutosize(true);
        this.butAddCat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddCat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddCat.setCornerRadius(4F);
        this.butAddCat.Image = Resources.getAdd();
        this.butAddCat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddCat.Location = new System.Drawing.Point(8, 348);
        this.butAddCat.Name = "butAddCat";
        this.butAddCat.Size = new System.Drawing.Size(79, 26);
        this.butAddCat.TabIndex = 12;
        this.butAddCat.Text = "Add";
        this.butAddCat.Click += new System.EventHandler(this.butAddCat_Click);
        //
        // butDeleteCat
        //
        this.butDeleteCat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteCat.setAutosize(true);
        this.butDeleteCat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteCat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteCat.setCornerRadius(4F);
        this.butDeleteCat.Image = Resources.getdeleteX();
        this.butDeleteCat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteCat.Location = new System.Drawing.Point(8, 383);
        this.butDeleteCat.Name = "butDeleteCat";
        this.butDeleteCat.Size = new System.Drawing.Size(79, 26);
        this.butDeleteCat.TabIndex = 13;
        this.butDeleteCat.Text = "Delete";
        this.butDeleteCat.Click += new System.EventHandler(this.butDeleteCat_Click);
        //
        // butAddNote
        //
        this.butAddNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNote.setAutosize(true);
        this.butAddNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNote.setCornerRadius(4F);
        this.butAddNote.Image = Resources.getAdd();
        this.butAddNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNote.Location = new System.Drawing.Point(180, 641);
        this.butAddNote.Name = "butAddNote";
        this.butAddNote.Size = new System.Drawing.Size(79, 26);
        this.butAddNote.TabIndex = 16;
        this.butAddNote.Text = "Add";
        this.butAddNote.Click += new System.EventHandler(this.butAddNote_Click);
        //
        // butDownNote
        //
        this.butDownNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownNote.setAutosize(true);
        this.butDownNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownNote.setCornerRadius(4F);
        this.butDownNote.Image = Resources.getdown();
        this.butDownNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDownNote.Location = new System.Drawing.Point(468, 641);
        this.butDownNote.Name = "butDownNote";
        this.butDownNote.Size = new System.Drawing.Size(79, 26);
        this.butDownNote.TabIndex = 15;
        this.butDownNote.Text = "Down";
        this.butDownNote.Click += new System.EventHandler(this.butDownNote_Click);
        //
        // butUpNote
        //
        this.butUpNote.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUpNote.setAutosize(true);
        this.butUpNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUpNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUpNote.setCornerRadius(4F);
        this.butUpNote.Image = Resources.getup();
        this.butUpNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUpNote.Location = new System.Drawing.Point(372, 641);
        this.butUpNote.Name = "butUpNote";
        this.butUpNote.Size = new System.Drawing.Size(79, 26);
        this.butUpNote.TabIndex = 14;
        this.butUpNote.Text = "Up";
        this.butUpNote.Click += new System.EventHandler(this.butUpNote_Click);
        //
        // butEditNote
        //
        this.butEditNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditNote.setAutosize(true);
        this.butEditNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditNote.setCornerRadius(4F);
        this.butEditNote.Image = Resources.geteditPencil();
        this.butEditNote.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEditNote.Location = new System.Drawing.Point(276, 641);
        this.butEditNote.Name = "butEditNote";
        this.butEditNote.Size = new System.Drawing.Size(79, 26);
        this.butEditNote.TabIndex = 17;
        this.butEditNote.Text = "Edit";
        this.butEditNote.Click += new System.EventHandler(this.butEditNote_Click);
        //
        // butDate
        //
        this.butDate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDate.setAutosize(true);
        this.butDate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDate.setCornerRadius(4F);
        this.butDate.Location = new System.Drawing.Point(639, 641);
        this.butDate.Name = "butDate";
        this.butDate.Size = new System.Drawing.Size(75, 26);
        this.butDate.TabIndex = 18;
        this.butDate.Text = "Date";
        this.butDate.Click += new System.EventHandler(this.butDate_Click);
        //
        // FormQuickPaste
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(918, 677);
        this.Controls.Add(this.butDate);
        this.Controls.Add(this.butEditNote);
        this.Controls.Add(this.butAddNote);
        this.Controls.Add(this.butDownNote);
        this.Controls.Add(this.butUpNote);
        this.Controls.Add(this.butDeleteCat);
        this.Controls.Add(this.butAddCat);
        this.Controls.Add(this.butDownCat);
        this.Controls.Add(this.butUpCat);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listNote);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listCat);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormQuickPaste";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Quick Paste Notes";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormQuickPaste_Closing);
        this.Load += new System.EventHandler(this.FormQuickPaste_Load);
        this.ResumeLayout(false);
    }

    private void formQuickPaste_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AutoNoteQuickNoteEdit,true))
        {
            butAddCat.Enabled = false;
            butDeleteCat.Enabled = false;
            butAddNote.Enabled = false;
            butEditNote.Text = "View";
        }
         
        fillCats();
        listCat.SelectedIndex = QuickPasteCats.getDefaultType(QuickType);
        fillNotes();
    }

    private void fillCats() throws Exception {
        int selected = listCat.SelectedIndex;
        listCat.Items.Clear();
        for (int i = 0;i < QuickPasteCats.getList().Length;i++)
        {
            listCat.Items.Add(QuickPasteCats.getList()[i].Description);
        }
        if (selected < listCat.Items.Count)
        {
            listCat.SelectedIndex = selected;
        }
         
        if (listCat.SelectedIndex == -1)
        {
            listCat.SelectedIndex = listCat.Items.Count - 1;
        }
         
    }

    private void fillNotes() throws Exception {
        if (listCat.SelectedIndex == -1)
        {
            listNote.Items.Clear();
            return ;
        }
         
        notesForCat = QuickPasteNotes.GetForCat(QuickPasteCats.getList()[listCat.SelectedIndex].QuickPasteCatNum);
        int selected = listNote.SelectedIndex;
        listNote.Items.Clear();
        for (int i = 0;i < notesForCat.Length;i++)
        {
            if (!StringSupport.equals(notesForCat[i].Abbreviation, ""))
            {
                listNote.Items.Add("?" + notesForCat[i].Abbreviation + "  " + notesForCat[i].Note);
            }
            else
            {
                listNote.Items.Add(notesForCat[i].Note);
            } 
        }
        if (selected < listNote.Items.Count)
        {
            listNote.SelectedIndex = selected;
        }
         
        if (listNote.SelectedIndex == -1)
        {
            listNote.SelectedIndex = listNote.Items.Count - 1;
        }
         
    }

    private void butAddCat_Click(Object sender, System.EventArgs e) throws Exception {
        QuickPasteCat quickCat = new QuickPasteCat();
        if (listCat.SelectedIndex == -1)
        {
            quickCat.ItemOrder = listCat.Items.Count;
        }
        else
        {
            //one more than list will hold.
            quickCat.ItemOrder = listCat.SelectedIndex;
        } 
        QuickPasteCats.insert(quickCat);
        FormQuickPasteCat FormQ = new FormQuickPasteCat(quickCat);
        FormQ.ShowDialog();
        if (FormQ.DialogResult == DialogResult.OK)
        {
            if (listCat.SelectedIndex != -1)
            {
                for (int i = listCat.SelectedIndex;i < QuickPasteCats.getList().Length;i++)
                {
                    //move other items down in list to make room for new one.
                    QuickPasteCats.getList()[i].ItemOrder++;
                    QuickPasteCats.Update(QuickPasteCats.getList()[i]);
                }
            }
             
            localChanged = true;
        }
        else
        {
            QuickPasteCats.delete(quickCat);
        } 
        QuickPasteCats.refreshCache();
        fillCats();
        fillNotes();
    }

    private void butDeleteCat_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Are you sure you want to delete the entire category and all notes in it?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < notesForCat.Length;i++)
        {
            QuickPasteNotes.Delete(notesForCat[i]);
        }
        QuickPasteCats.Delete(QuickPasteCats.getList()[listCat.SelectedIndex]);
        for (int i = listCat.SelectedIndex;i < QuickPasteCats.getList().Length;i++)
        {
            //yes, the first update won't work because already deleted
            QuickPasteCats.getList()[i].ItemOrder--;
            QuickPasteCats.Update(QuickPasteCats.getList()[i]);
        }
        QuickPasteNotes.RefreshCache();
        QuickPasteCats.refreshCache();
        fillCats();
        fillNotes();
        localChanged = true;
    }

    private void butUpCat_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        if (listCat.SelectedIndex == 0)
        {
            return ;
        }
         
        //can't go up any more
        QuickPasteCats.getList()[listCat.SelectedIndex].ItemOrder--;
        QuickPasteCats.Update(QuickPasteCats.getList()[listCat.SelectedIndex]);
        QuickPasteCats.getList()[listCat.SelectedIndex - 1].ItemOrder++;
        QuickPasteCats.Update(QuickPasteCats.getList()[listCat.SelectedIndex - 1]);
        listCat.SelectedIndex--;
        QuickPasteCats.refreshCache();
        fillCats();
        fillNotes();
        localChanged = true;
    }

    private void butDownCat_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        if (listCat.SelectedIndex == QuickPasteCats.getList().Length - 1)
        {
            return ;
        }
         
        //can't go down any more
        QuickPasteCats.getList()[listCat.SelectedIndex].ItemOrder++;
        QuickPasteCats.Update(QuickPasteCats.getList()[listCat.SelectedIndex]);
        QuickPasteCats.getList()[listCat.SelectedIndex + 1].ItemOrder--;
        QuickPasteCats.Update(QuickPasteCats.getList()[listCat.SelectedIndex + 1]);
        listCat.SelectedIndex++;
        QuickPasteCats.refreshCache();
        fillCats();
        fillNotes();
        localChanged = true;
    }

    private void listCat_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        fillNotes();
    }

    private void listCat_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AutoNoteQuickNoteEdit))
        {
            return ;
        }
         
        if (listCat.SelectedIndex == -1)
        {
            return ;
        }
         
        FormQuickPasteCat FormQ = new FormQuickPasteCat(QuickPasteCats.getList()[listCat.SelectedIndex]);
        FormQ.ShowDialog();
        if (FormQ.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        QuickPasteCats.refreshCache();
        fillCats();
        fillNotes();
        localChanged = true;
    }

    private void butAddNote_Click(Object sender, System.EventArgs e) throws Exception {
        if (listCat.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a category first."));
            return ;
        }
         
        QuickPasteNote quickNote = new QuickPasteNote();
        quickNote.QuickPasteCatNum = QuickPasteCats.getList()[listCat.SelectedIndex].QuickPasteCatNum;
        if (listNote.SelectedIndex == -1)
        {
            quickNote.ItemOrder = notesForCat.Length;
        }
        else
        {
            //one more than list will hold.
            quickNote.ItemOrder = listNote.SelectedIndex;
        } 
        FormQuickPasteNoteEdit FormQ = new FormQuickPasteNoteEdit(quickNote);
        FormQ.IsNew = true;
        FormQ.ShowDialog();
        if (FormQ.DialogResult == DialogResult.OK)
        {
            if (listNote.SelectedIndex != -1)
            {
                for (int i = quickNote.ItemOrder;i < notesForCat.Length;i++)
                {
                    //move other items down in list to make room for new one.
                    notesForCat[i].ItemOrder++;
                    QuickPasteNotes.Update(notesForCat[i]);
                }
            }
             
            localChanged = true;
        }
         
        QuickPasteNotes.RefreshCache();
        fillNotes();
    }

    private void butEditNote_Click(Object sender, System.EventArgs e) throws Exception {
        if (listNote.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a note first."));
            return ;
        }
         
        FormQuickPasteNoteEdit FormQ = new FormQuickPasteNoteEdit(notesForCat[listNote.SelectedIndex]);
        FormQ.ShowDialog();
        if (notesForCat[listNote.SelectedIndex].QuickPasteNoteNum == 0)
        {
            for (int i = listNote.SelectedIndex;i < notesForCat.Length;i++)
            {
                //deleted
                notesForCat[i].ItemOrder--;
                QuickPasteNotes.Update(notesForCat[i]);
            }
        }
         
        QuickPasteNotes.RefreshCache();
        fillNotes();
        localChanged = true;
    }

    //?
    private void butUpNote_Click(Object sender, System.EventArgs e) throws Exception {
        if (listNote.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a note first."));
            return ;
        }
         
        if (listNote.SelectedIndex == 0)
        {
            return ;
        }
         
        //can't go up any more
        notesForCat[listNote.SelectedIndex].ItemOrder--;
        QuickPasteNotes.Update(notesForCat[listNote.SelectedIndex]);
        notesForCat[listNote.SelectedIndex - 1].ItemOrder++;
        QuickPasteNotes.Update(notesForCat[listNote.SelectedIndex - 1]);
        listNote.SelectedIndex--;
        QuickPasteNotes.RefreshCache();
        fillNotes();
        localChanged = true;
    }

    private void butDownNote_Click(Object sender, System.EventArgs e) throws Exception {
        if (listNote.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a note first."));
            return ;
        }
         
        if (listNote.SelectedIndex == notesForCat.Length - 1)
        {
            return ;
        }
         
        //can't go down any more
        notesForCat[listNote.SelectedIndex].ItemOrder++;
        QuickPasteNotes.Update(notesForCat[listNote.SelectedIndex]);
        notesForCat[listNote.SelectedIndex + 1].ItemOrder--;
        QuickPasteNotes.Update(notesForCat[listNote.SelectedIndex + 1]);
        listNote.SelectedIndex++;
        QuickPasteNotes.RefreshCache();
        fillNotes();
        localChanged = true;
    }

    private void listNote_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    //do nothing
    private void listNote_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listNote.SelectedIndex == -1)
        {
            return ;
        }
         
        InsertValue(notesForCat[listNote.SelectedIndex].Note);
        DialogResult = DialogResult.OK;
    }

    private void butDate_Click(Object sender, System.EventArgs e) throws Exception {
        InsertValue(DateTime.Today.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listNote.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select a note first."));
            return ;
        }
         
        InsertValue(notesForCat[listNote.SelectedIndex].Note);
        DialogResult = DialogResult.OK;
    }

    private void insertValue(String strPaste) throws Exception {
        int caret = TextToFill.SelectionStart;
        //string strPaste=FormQ.SelectedNote;
        TextToFill.Text = TextToFill.Text.Insert(caret, strPaste);
        TextToFill.SelectionStart = caret + strPaste.Length;
        TextToFill.SelectionLength = 0;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formQuickPaste_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (localChanged)
        {
            DataValid.setInvalid(InvalidType.QuickPaste);
        }
         
    }

}


