//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DictCustom;
import OpenDentBusiness.DictCustoms;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormSpellCheck;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormSpellCheck  extends Form 
{

    public FormSpellCheck() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSpellCheck_Load(Object sender, EventArgs e) throws Exception {
        checkBox1.Checked = PrefC.getBool(PrefName.SpellCheckIsEnabled);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",200,false);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < DictCustoms.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DictCustoms.getListt()[i].WordText);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textCustom.Text, ""))
        {
            MsgBox.show(this,"Please enter a custom word first.");
            return ;
        }
         
        String newWord = Regex.Replace(textCustom.Text, "[\\s]|[\\p{P}\\p{S}-['-]]", "");
        for (int i = 0;i < DictCustoms.getListt().Count;i++)
        {
            //don't allow words with spaces or punctuation except ' and - in them
            //Make sure it's not already in the custom list
            if (StringSupport.equals(DictCustoms.getListt()[i].WordText, newWord))
            {
                MsgBox.show(this,"The word " + newWord + " is already in the custom word list.");
                textCustom.Clear();
                return ;
            }
             
        }
        DictCustom word = new DictCustom();
        word.WordText = newWord;
        DictCustoms.insert(word);
        DataValid.setInvalid(InvalidType.DictCustoms);
        textCustom.Clear();
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        InputBox editWord = new InputBox("Edit word");
        DictCustom origWord = DictCustoms.getListt()[e.getRow()];
        editWord.textResult.Text = origWord.WordText;
        if (editWord.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (StringSupport.equals(editWord.textResult.Text, origWord.WordText))
        {
            return ;
        }
         
        if (StringSupport.equals(editWord.textResult.Text, ""))
        {
            DictCustoms.delete(origWord.DictCustomNum);
            DataValid.setInvalid(InvalidType.DictCustoms);
            fillGrid();
            return ;
        }
         
        String newWord = Regex.Replace(editWord.textResult.Text, "[\\s]|[\\p{P}\\p{S}-['-]]", "");
        for (int i = 0;i < DictCustoms.getListt().Count;i++)
        {
            //don't allow words with spaces or punctuation except ' and - in them
            //Make sure it's not already in the custom list
            if (StringSupport.equals(DictCustoms.getListt()[i].WordText, newWord))
            {
                MsgBox.show(this,"The word " + newWord + " is already in the custom word list.");
                editWord.textResult.Text = origWord.WordText;
                return ;
            }
             
        }
        origWord.WordText = newWord;
        DictCustoms.update(origWord);
        DataValid.setInvalid(InvalidType.DictCustoms);
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Select a word to delete first.");
            return ;
        }
         
        DictCustoms.Delete(DictCustoms.getListt()[gridMain.getSelectedIndex()].DictCustomNum);
        DataValid.setInvalid(InvalidType.DictCustoms);
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void formSpellCheck_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (Prefs.UpdateBool(PrefName.SpellCheckIsEnabled, checkBox1.Checked))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSpellCheck.class);
        this.label4 = new System.Windows.Forms.Label();
        this.textCustom = new System.Windows.Forms.TextBox();
        this.checkBox1 = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butDelete = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(350, 46);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(176, 23);
        this.label4.TabIndex = 15;
        this.label4.Text = "Custom Word";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textCustom
        //
        this.textCustom.Location = new System.Drawing.Point(352, 72);
        this.textCustom.Name = "textCustom";
        this.textCustom.Size = new System.Drawing.Size(173, 20);
        this.textCustom.TabIndex = 14;
        //
        // checkBox1
        //
        this.checkBox1.AutoSize = true;
        this.checkBox1.Location = new System.Drawing.Point(12, 12);
        this.checkBox1.Name = "checkBox1";
        this.checkBox1.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.checkBox1.Size = new System.Drawing.Size(125, 17);
        this.checkBox1.TabIndex = 18;
        this.checkBox1.Text = "Spell Check Enabled";
        this.checkBox1.UseVisualStyleBackColor = true;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 35);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(245, 599);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Custom Words");
        this.gridMain.setTranslationName(null);
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
        this.butDelete.Location = new System.Drawing.Point(263, 608);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 26);
        this.butDelete.TabIndex = 17;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getLeft();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(267, 68);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 16;
        this.butAdd.Text = "Add";
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
        this.butClose.Location = new System.Drawing.Point(448, 610);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormDictCustoms
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(535, 646);
        this.Controls.Add(this.checkBox1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textCustom);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormDictCustoms";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Spell Check";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormSpellCheck_FormClosing);
        this.Load += new System.EventHandler(this.FormSpellCheck_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCustom = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.CheckBox checkBox1 = new System.Windows.Forms.CheckBox();
}


