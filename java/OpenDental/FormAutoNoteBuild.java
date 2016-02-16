//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.AutoNote;
import OpenDentBusiness.AutoNoteControl;
import OpenDentBusiness.AutoNotes;
import OpenDental.FormAutoNoteBuild;

public class FormAutoNoteBuild  extends Form 
{

    /**
    * The current AutoNoteControl that is being edited, whether new or not.
    */
    public AutoNoteControl ControlCur;
    public AutoNote AutoNoteCur;
    public FormAutoNoteBuild() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAutoNoteBuild_Load(Object sender, EventArgs e) throws Exception {
        fillList();
    }

    private void fillList() throws Exception {
        listBoxAutoNotes.Items.Clear();
        AutoNotes.refreshCache();
        for (int i = 0;i < AutoNotes.Listt.Count;i++)
        {
            listBoxAutoNotes.Items.Add(AutoNotes.Listt[i].AutoNoteName);
        }
    }

    private void drawControls() throws Exception {
    }

    /*
    			int TextBoxCount=0;
    			int ComboBoxCount=0;
    			for (int i=0; i<listBoxControls.Items.Count; i++) {
    				AutoNoteControls.RefreshControl(listBoxControls.Items[i].ToString());
    				ControlCur=AutoNoteControls.Listt[0];
    				switch (ControlCur.ControlType) {
    					case "TextBox":
    						TextBoxCount++;
    						break;
    					case "MultiLineTextBox":
    						TextBoxCount++;
    						break;
    					case "ComboBox":
    						ComboBoxCount++;
    						break;
    				}
    			}
    			System.Windows.Forms.ComboBox[] comboBox=new ComboBox[ComboBoxCount];
    			System.Windows.Forms.TextBox[] textBox=new TextBox[TextBoxCount];
    			System.Windows.Forms.Label[] label=new Label[listBoxControls.Items.Count];
    			for (int l=0; l<listBoxControls.Items.Count; l++) {
    				AutoNoteControls.RefreshControl(listBoxControls.Items[l].ToString());
    				ControlCur=AutoNoteControls.Listt[0];
    				label[l]=new Label();
    				label[l].AutoSize=true;
    				label[l].Text=ControlCur.ControlLabel;
    			}
    			int CurText=0;
    			int CurCombo=0;
    			int spacing=140;
    			for (int l=0; l<listBoxControls.Items.Count; l++) {
    				AutoNoteControls.RefreshControl(listBoxControls.Items[l].ToString());
    				ControlCur=AutoNoteControls.Listt[0];
    				switch (ControlCur.ControlType) {
    					case "TextBox":
    						textBox[CurText]=new TextBox();
    						textBox[CurText].TabIndex=this.Controls.Count;
    						textBox[CurText].Location=new Point(label[l].Text.Length * 6 + 15, spacing);
    						this.Controls.Add(textBox[CurText]);
    						label[l].Location=new Point(10, spacing);
    						spacing=spacing+30;
    						CurText++;
    						break;
    					case "MultiLineTextBox":
    						textBox[CurText]=new TextBox();
    						textBox[CurText].Multiline=true;
    						textBox[CurText].Size=new Size(177, 67);
    						textBox[CurText].Location=new Point(label[l].Text.Length * 6 + 15, spacing);
    						textBox[CurText].Text=ControlCur.MultiLineText;
    						this.Controls.Add(textBox[CurText]);
    						label[l].Location=new Point(10, spacing);
    						spacing=spacing+77;
    						CurText++;
    						break;
    					case "ComboBox":
    						comboBox[CurCombo]=new ComboBox();
    						string[] lines=ControlCur.ControlOptions.Split(new char[] { ',' });
    						for (int i=0; i<lines.Length; i++) {
    							comboBox[CurCombo].Items.Add(lines[i]);
    						}
    						comboBox[CurCombo].AutoCompleteMode=AutoCompleteMode.SuggestAppend;
    						comboBox[CurCombo].AutoCompleteSource=AutoCompleteSource.ListItems;
    						this.Controls.Add(comboBox[CurCombo]);
    						label[l].Location=new Point(10, spacing);
    						comboBox[CurCombo].Location=new Point(label[l].Text.Length * 6 + 15, spacing);
    						spacing=spacing+30;
    						CurCombo++;
    						break;
    				}
    				for (int xl = 0; xl <listBoxControls.Items.Count; xl++) {
    					this.Controls.Add(label[xl]);
    				}
    			}
    			butOK.Visible=true;
    			butOK.Location=new Point(330,spacing);
    			*/
    private void listBoxAutoNotes_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
    }

    /*
    			for (int x = 0; x <= this.Controls.Count - 1; x++) {
    				if (this.Controls[x].Name.ToString() != "listBoxAutoNotes" &&
    						this.Controls[x].Name.ToString() != "butOK" &&
    						this.Controls[x].Name.ToString() != "listBoxControls") {
    					this.Controls.RemoveAt(x);
    					x--;
    				}
    			}
    			if (listBoxAutoNotes.SelectedIndex==-1) {
    				return;
    			}
    			listBoxControls.Items.Clear();
    			AutoNoteCur=AutoNotes.Listt[listBoxAutoNotes.SelectedIndex];
    			string[] lines=AutoNoteCur.ControlsToInc.Split(new char[] { ',' });
    			for (int i=0; i<lines.Length; i++) {
    				if (lines[i].ToString()!="") {
    					listBoxControls.Items.Add(lines[i].ToString());
    				}
    			}
    			DrawControls();*/
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        /*
        			string AutoNoteOutput="";
        			int currentControl=0;
        			for (int x=0; x<this.Controls.Count; x++) {
        				if (this.Controls[x].Name.ToString() != "listBoxAutoNotes" &&
        					this.Controls[x].Name.ToString() != "butOK" &&
        					this.Controls[x].Name.ToString() != "listBoxControls") {					
        					string Text="";
        					string prefaceText="";
        					if (currentControl<listBoxControls.Items.Count) {
        						if (this.Controls[x].Text!="") {
        							AutoNoteControls.RefreshControl(listBoxControls.Items[currentControl].ToString());
        							ControlCur=AutoNoteControls.Listt[0];
        							prefaceText=ControlCur.PrefaceText;
        							Text=this.Controls[x].Text;
        							AutoNoteOutput=AutoNoteOutput+" "+prefaceText+" "+Text;
        						}
        						currentControl++;
        					}
        				}
        			}
        			AutoNoteCur.AutoNoteOutput=AutoNoteOutput;*/
        this.DialogResult = DialogResult.OK;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoNoteBuild.class);
        this.listBoxAutoNotes = new System.Windows.Forms.ListBox();
        this.listBoxControls = new System.Windows.Forms.ListBox();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listBoxAutoNotes
        //
        this.listBoxAutoNotes.FormattingEnabled = true;
        this.listBoxAutoNotes.HorizontalScrollbar = true;
        this.listBoxAutoNotes.Location = new System.Drawing.Point(108, 12);
        this.listBoxAutoNotes.Name = "listBoxAutoNotes";
        this.listBoxAutoNotes.Size = new System.Drawing.Size(197, 108);
        this.listBoxAutoNotes.TabIndex = 0;
        this.listBoxAutoNotes.SelectedIndexChanged += new System.EventHandler(this.listBoxAutoNotes_SelectedIndexChanged);
        //
        // listBoxControls
        //
        this.listBoxControls.FormattingEnabled = true;
        this.listBoxControls.Location = new System.Drawing.Point(331, 25);
        this.listBoxControls.Name = "listBoxControls";
        this.listBoxControls.Size = new System.Drawing.Size(120, 95);
        this.listBoxControls.TabIndex = 10;
        this.listBoxControls.Visible = false;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = System.Windows.Forms.AnchorStyles.None;
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(26, 24);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 25);
        this.butOK.TabIndex = 9;
        this.butOK.Text = "OK";
        this.butOK.Visible = false;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormAutoNoteBuild
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.AutoScroll = true;
        this.AutoScrollMargin = new System.Drawing.Size(0, 50);
        this.ClientSize = new System.Drawing.Size(490, 633);
        this.Controls.Add(this.listBoxControls);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.listBoxAutoNotes);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutoNoteBuild";
        this.Text = " ";
        this.Load += new System.EventHandler(this.FormAutoNoteBuild_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.ListBox listBoxAutoNotes = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listBoxControls = new System.Windows.Forms.ListBox();
}


