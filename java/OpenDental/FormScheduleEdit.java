//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import OpenDental.FormScheduleEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.Schedule;

/**
* 
*/
public class FormScheduleEdit  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private ComboBox comboStop = new ComboBox();
    private ComboBox comboStart = new ComboBox();
    private ListBox listOps = new ListBox();
    private Label labelOps = new Label();
    //<summary></summary>
    //public bool IsNew;
    public Schedule SchedCur;
    /**
    * 
    */
    public FormScheduleEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormScheduleEdit.class);
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboStop = new System.Windows.Forms.ComboBox();
        this.comboStart = new System.Windows.Forms.ComboBox();
        this.listOps = new System.Windows.Forms.ListBox();
        this.labelOps = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(27, 40);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(68, 16);
        this.label2.TabIndex = 9;
        this.label2.Text = "Stop Time";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(27, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(68, 16);
        this.label1.TabIndex = 7;
        this.label1.Text = "Start Time";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(97, 63);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(220, 113);
        this.textNote.TabIndex = 15;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(31, 64);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(64, 16);
        this.label4.TabIndex = 16;
        this.label4.Text = "Note";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboStop
        //
        this.comboStop.FormattingEnabled = true;
        this.comboStop.Location = new System.Drawing.Point(97, 37);
        this.comboStop.MaxDropDownItems = 48;
        this.comboStop.Name = "comboStop";
        this.comboStop.Size = new System.Drawing.Size(120, 21);
        this.comboStop.TabIndex = 25;
        //
        // comboStart
        //
        this.comboStart.FormattingEnabled = true;
        this.comboStart.Location = new System.Drawing.Point(97, 11);
        this.comboStart.MaxDropDownItems = 48;
        this.comboStart.Name = "comboStart";
        this.comboStart.Size = new System.Drawing.Size(120, 21);
        this.comboStart.TabIndex = 24;
        //
        // listOps
        //
        this.listOps.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listOps.IntegralHeight = false;
        this.listOps.Location = new System.Drawing.Point(348, 26);
        this.listOps.Name = "listOps";
        this.listOps.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listOps.Size = new System.Drawing.Size(243, 357);
        this.listOps.TabIndex = 27;
        //
        // labelOps
        //
        this.labelOps.Location = new System.Drawing.Point(345, 7);
        this.labelOps.Name = "labelOps";
        this.labelOps.Size = new System.Drawing.Size(95, 16);
        this.labelOps.TabIndex = 26;
        this.labelOps.Text = "Operatories";
        this.labelOps.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(528, 393);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 14;
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
        this.butOK.Location = new System.Drawing.Point(440, 393);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 12;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormScheduleEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(615, 431);
        this.Controls.Add(this.listOps);
        this.Controls.Add(this.labelOps);
        this.Controls.Add(this.comboStop);
        this.Controls.Add(this.comboStart);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormScheduleEdit";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Schedule";
        this.Load += new System.EventHandler(this.FormScheduleDayEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formScheduleDayEdit_Load(Object sender, System.EventArgs e) throws Exception {
        DateTime time = new DateTime();
        for (int i = 0;i < 24;i++)
        {
            time = DateTime.Today + TimeSpan.FromHours(7) + TimeSpan.FromMinutes(30 * i);
            comboStart.Items.Add(time.ToShortTimeString());
            comboStop.Items.Add(time.ToShortTimeString());
        }
        comboStart.Text = SchedCur.StartTime.ToShortTimeString();
        comboStop.Text = SchedCur.StopTime.ToShortTimeString();
        listOps.Items.Add(Lan.g(this,"not specified"));
        for (int i = 0;i < OperatoryC.getListShort().Count;i++)
        {
            listOps.Items.Add(OperatoryC.getListShort()[i].OpName);
            if (SchedCur.Ops.Contains(OperatoryC.getListShort()[i].OperatoryNum))
            {
                listOps.SetSelected(i + 1, true);
            }
             
        }
        if (listOps.SelectedIndices.Count == 0)
        {
            listOps.SetSelected(0, true);
        }
         
        textNote.Text = SchedCur.Note;
        if (SchedCur.StartTime == TimeSpan.Zero && SchedCur.StopTime == TimeSpan.Zero)
        {
            comboStop.Visible = false;
            comboStart.Visible = false;
            label1.Visible = false;
            label2.Visible = false;
            labelOps.Visible = false;
            listOps.Visible = false;
            textNote.Select();
        }
        else
        {
            comboStart.Select();
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listOps.Visible)
        {
            if (listOps.SelectedIndices.Count > 1 && listOps.SelectedIndices.Contains(0))
            {
                MsgBox.show(this,"Invalid selection of ops.");
                return ;
            }
             
            if (listOps.SelectedIndices.Count == 0)
            {
                MsgBox.show(this,"Please select ops first.");
                return ;
            }
             
        }
         
        if (comboStart.Visible)
        {
            try
            {
                DateTime.Parse(comboStart.Text);
                DateTime.Parse(comboStop.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Incorrect time format");
                return ;
            }
        
        }
         
        SchedCur.StartTime = DateTime.Parse(comboStart.Text).TimeOfDay;
        SchedCur.StopTime = DateTime.Parse(comboStop.Text).TimeOfDay;
        SchedCur.Note = textNote.Text;
        SchedCur.Ops = new List<long>();
        if (!listOps.SelectedIndices.Contains(0))
        {
            for (int i = 0;i < listOps.SelectedIndices.Count;i++)
            {
                SchedCur.Ops.Add(OperatoryC.getListShort()[listOps.SelectedIndices[i] - 1].OperatoryNum);
            }
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


