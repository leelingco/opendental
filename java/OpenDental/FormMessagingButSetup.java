//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:20 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormMessagingButSetup;
import OpenDental.FormSigButDefEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Computers;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.SigButDefElements;
import OpenDentBusiness.SigButDefs;
import OpenDentBusiness.SigElementDefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormMessagingButSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private Label label1 = new Label();
    private ListBox listButtons = new ListBox();
    private ListBox listComputers = new ListBox();
    private Label label2 = new Label();
    private SigButDef[] SubList = new SigButDef[]();
    /**
    * 
    */
    public FormMessagingButSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMessagingButSetup.class);
        this.label1 = new System.Windows.Forms.Label();
        this.listButtons = new System.Windows.Forms.ListBox();
        this.listComputers = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(274, 26);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(78, 16);
        this.label1.TabIndex = 11;
        this.label1.Text = "Buttons";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listButtons
        //
        this.listButtons.FormattingEnabled = true;
        this.listButtons.Location = new System.Drawing.Point(276, 45);
        this.listButtons.Name = "listButtons";
        this.listButtons.Size = new System.Drawing.Size(200, 264);
        this.listButtons.TabIndex = 12;
        this.listButtons.DoubleClick += new System.EventHandler(this.listButtons_DoubleClick);
        //
        // listComputers
        //
        this.listComputers.FormattingEnabled = true;
        this.listComputers.Location = new System.Drawing.Point(36, 45);
        this.listComputers.Name = "listComputers";
        this.listComputers.Size = new System.Drawing.Size(198, 264);
        this.listComputers.TabIndex = 23;
        this.listComputers.Click += new System.EventHandler(this.listComputers_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(34, 26);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(78, 16);
        this.label2.TabIndex = 22;
        this.label2.Text = "Computer";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
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
        this.butDown.Location = new System.Drawing.Point(401, 332);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(75, 26);
        this.butDown.TabIndex = 21;
        this.butDown.Text = "Down";
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
        this.butUp.Location = new System.Drawing.Point(276, 332);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(75, 26);
        this.butUp.TabIndex = 20;
        this.butUp.Text = "Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(545, 332);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormMessagingButSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(654, 383);
        this.Controls.Add(this.listComputers);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.listButtons);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMessagingButSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Messaging Button Setup";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormMessagingButSetup_FormClosing);
        this.Load += new System.EventHandler(this.FormMessagingButSetup_Load);
        this.ResumeLayout(false);
    }

    private void formMessagingButSetup_Load(Object sender, EventArgs e) throws Exception {
        listComputers.Items.Clear();
        listComputers.Items.Add(Lan.g(this,"All"));
        String s = new String();
        for (int i = 0;i < Computers.getList().Length;i++)
        {
            s = Computers.getList()[i].CompName;
            if (SystemInformation.ComputerName == Computers.getList()[i].CompName)
            {
                s += " " + Lan.g(this,"(this computer)");
            }
             
            listComputers.Items.Add(s);
        }
        listComputers.SelectedIndex = 0;
        fillList();
    }

    private void fillList() throws Exception {
        SigButDefs.refreshCache();
        SigButDefElements.refreshCache();
        if (listComputers.SelectedIndex == -1)
        {
            //although I don't know how this could happen
            listComputers.SelectedIndex = 0;
        }
         
        if (listComputers.SelectedIndex == 0)
        {
            SubList = SigButDefs.getByComputer("");
        }
        else
        {
            //remember, defaults are mixed into this list unless overridden:
            SubList = SigButDefs.GetByComputer(Computers.getList()[listComputers.SelectedIndex - 1].CompName);
        } 
        int selected = listButtons.SelectedIndex;
        listButtons.Items.Clear();
        SigButDef button;
        SigButDefElement[] elements = new SigButDefElement[]();
        String s = new String();
        for (int i = 0;i < 20;i++)
        {
            button = SigButDefs.getByIndex(i,SubList);
            if (button == null)
            {
                listButtons.Items.Add("-" + (i + 1).ToString() + "-");
            }
            else
            {
                s = button.ButtonText;
                elements = SigButDefElements.getForButton(button.SigButDefNum);
                for (int e = 0;e < elements.Length;e++)
                {
                    if (e == 0)
                    {
                        s += " (";
                    }
                    else
                    {
                        s += ", ";
                    } 
                    s += SigElementDefs.GetElement(elements[e].SigElementDefNum).SigText;
                    if (e == elements.Length - 1)
                    {
                        s += ")";
                    }
                     
                }
                if (StringSupport.equals(button.ComputerName, "") && listComputers.SelectedIndex != 0)
                {
                    s += " " + Lan.g(this,"(all)");
                }
                 
                listButtons.Items.Add(s);
            } 
        }
    }

    private void listComputers_Click(Object sender, EventArgs e) throws Exception {
        fillList();
    }

    private void listButtons_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listButtons.SelectedIndex == -1)
        {
            return ;
        }
         
        //should never happen
        int selected = listButtons.SelectedIndex;
        SigButDef button = SigButDefs.getByIndex(selected,SubList);
        if (button == null)
        {
            //Add
            FormSigButDefEdit FormS = new FormSigButDefEdit();
            FormS.IsNew = true;
            button = new SigButDef();
            button.ElementList = new SigButDefElement[0];
            button.ButtonIndex = selected;
            if (listComputers.SelectedIndex == 0)
            {
                button.ComputerName = "";
            }
            else
            {
                button.ComputerName = Computers.getList()[listComputers.SelectedIndex - 1].CompName;
            } 
            FormS.ButtonCur = button.copy();
            FormS.ShowDialog();
        }
        else if (StringSupport.equals(button.ComputerName, "") && listComputers.SelectedIndex != 0)
        {
            //create a copy of the default, and treat it as a new
            FormSigButDefEdit FormS = new FormSigButDefEdit();
            FormS.IsNew = true;
            button.ComputerName = Computers.getList()[listComputers.SelectedIndex - 1].CompName;
            FormS.ButtonCur = button.copy();
            FormS.ShowDialog();
        }
        else
        {
            //edit
            FormSigButDefEdit FormS = new FormSigButDefEdit();
            FormS.ButtonCur = button.copy();
            FormS.ShowDialog();
        }  
        fillList();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        if (listButtons.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        int selected = listButtons.SelectedIndex;
        if (selected == 0)
        {
            return ;
        }
         
        SigButDef button = SigButDefs.getByIndex(selected,SubList);
        if (button == null)
        {
            return ;
        }
         
        SigButDefs.moveUp(button,SubList);
        fillList();
        listButtons.SelectedIndex = selected - 1;
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        if (listButtons.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        int selected = listButtons.SelectedIndex;
        if (selected == listButtons.Items.Count - 1)
        {
            return ;
        }
         
        SigButDef button = SigButDefs.getByIndex(selected,SubList);
        if (button == null)
        {
            return ;
        }
         
        SigButDefs.moveDown(button,SubList);
        fillList();
        listButtons.SelectedIndex = selected + 1;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formMessagingButSetup_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        DataValid.setInvalid(InvalidType.Signals);
    }

}


