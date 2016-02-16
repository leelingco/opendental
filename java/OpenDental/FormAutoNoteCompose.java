//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAutoNotePromptMultiResp;
import OpenDental.FormAutoNotePromptOneResp;
import OpenDental.FormAutoNotePromptText;
import OpenDental.Lan;
import OpenDentBusiness.AutoNoteControl;
import OpenDentBusiness.AutoNoteControls;
import OpenDentBusiness.AutoNotes;

public class FormAutoNoteCompose  extends Form 
{

    public String CompletedNote = new String();
    public FormAutoNoteCompose() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAutoNoteCompose_Load(Object sender, EventArgs e) throws Exception {
        AutoNotes.refreshCache();
        AutoNoteControls.refreshCache();
        listMain.Items.Clear();
        for (int i = 0;i < AutoNotes.Listt.Count;i++)
        {
            listMain.Items.Add(AutoNotes.Listt[i].AutoNoteName);
        }
    }

    private void listMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        String note = AutoNotes.Listt[listMain.SelectedIndex].MainText;
        int selectionStart = textMain.SelectionStart;
        if (selectionStart == 0)
        {
            textMain.Text = note + textMain.Text;
        }
        else if (selectionStart == textMain.Text.Length - 1)
        {
            textMain.Text = textMain.Text + note;
        }
        else if (selectionStart == -1)
        {
            //?is this even possible?
            textMain.Text = textMain.Text + note;
        }
        else
        {
            textMain.Text = textMain.Text.Substring(0, selectionStart) + note + textMain.Text.Substring(selectionStart);
        }   
        List<AutoNoteControl> prompts = new List<AutoNoteControl>();
        MatchCollection matches = Regex.Matches(note, "\\[Prompt:\"[a-zA-Z_0-9 ]+\"\\]");
        String autoNoteDescript = new String();
        AutoNoteControl control;
        String promptResponse = new String();
        int matchloc = new int();
        for (int i = 0;i < matches.Count;i++)
        {
            //highlight the current match in red
            matchloc = textMain.Text.IndexOf(matches[i].Value);
            textMain.Select(matchloc, matches[i].Value.Length);
            textMain.SelectionBackColor = Color.Yellow;
            textMain.SelectionLength = 0;
            Application.DoEvents();
            //refresh the textbox
            autoNoteDescript = matches[i].Value.Substring(9, matches[i].Value.Length - 11);
            control = AutoNoteControls.getByDescript(autoNoteDescript);
            if (control == null)
            {
                continue;
            }
             
            //couldn't find a prompt with that name, so just ignore it.
            promptResponse = "";
            if (StringSupport.equals(control.ControlType, "Text"))
            {
                FormAutoNotePromptText FormT = new FormAutoNotePromptText();
                FormT.PromptText = control.ControlLabel;
                FormT.ResultText = control.ControlOptions;
                FormT.ShowDialog();
                if (FormT.DialogResult == DialogResult.OK)
                {
                    promptResponse = FormT.ResultText;
                }
                else
                {
                    textMain.SelectAll();
                    textMain.SelectionBackColor = Color.White;
                    textMain.Select(textMain.Text.Length, 0);
                    return ;
                } 
            }
            else if (StringSupport.equals(control.ControlType, "OneResponse"))
            {
                FormAutoNotePromptOneResp FormOR = new FormAutoNotePromptOneResp();
                FormOR.PromptText = control.ControlLabel;
                FormOR.PromptOptions = control.ControlOptions;
                FormOR.ShowDialog();
                if (FormOR.DialogResult == DialogResult.OK)
                {
                    promptResponse = FormOR.ResultText;
                }
                else
                {
                    textMain.SelectAll();
                    textMain.SelectionBackColor = Color.White;
                    textMain.Select(textMain.Text.Length, 0);
                    return ;
                } 
            }
            else if (StringSupport.equals(control.ControlType, "MultiResponse"))
            {
                FormAutoNotePromptMultiResp FormMR = new FormAutoNotePromptMultiResp();
                FormMR.PromptText = control.ControlLabel;
                FormMR.PromptOptions = control.ControlOptions;
                FormMR.ShowDialog();
                if (FormMR.DialogResult == DialogResult.OK)
                {
                    promptResponse = FormMR.ResultText;
                }
                else
                {
                    textMain.SelectAll();
                    textMain.SelectionBackColor = Color.White;
                    textMain.Select(textMain.Text.Length, 0);
                    return ;
                } 
            }
               
            String resultstr = textMain.Text.Substring(0, matchloc) + promptResponse;
            if (textMain.Text.Length > matchloc + matches[i].Value.Length)
            {
                resultstr += textMain.Text.Substring(matchloc + matches[i].Value.Length);
            }
             
            textMain.Text = resultstr;
            textMain.SelectAll();
            textMain.SelectionBackColor = Color.White;
            textMain.Select(textMain.Text.Length, 0);
            Application.DoEvents();
        }
        //refresh the textbox
        textMain.SelectAll();
        textMain.SelectionBackColor = Color.White;
        textMain.Select(textMain.Text.Length, 0);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        CompletedNote = textMain.Text.Replace("\n", "\r\n");
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.listMain = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textMain = new System.Windows.Forms.RichTextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Location = new System.Drawing.Point(12, 28);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(169, 576);
        this.listMain.TabIndex = 4;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 12);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(171, 13);
        this.label1.TabIndex = 110;
        this.label1.Text = "Select Auto Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(186, 12);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(86, 13);
        this.label2.TabIndex = 112;
        this.label2.Text = "Text";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textMain
        //
        this.textMain.Location = new System.Drawing.Point(187, 28);
        this.textMain.Name = "textMain";
        this.textMain.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textMain.Size = new System.Drawing.Size(576, 574);
        this.textMain.TabIndex = 113;
        this.textMain.Text = "";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(794, 550);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(794, 580);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAutoNoteCompose
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(881, 614);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormAutoNoteCompose";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Compose Auto Note";
        this.Load += new System.EventHandler(this.FormAutoNoteCompose_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.RichTextBox textMain = new System.Windows.Forms.RichTextBox();
}


