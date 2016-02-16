//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:32 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.FormProcCodeNoteEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.ProcCodeNote;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.UI.ApptDrawing;

/**
* 
*/
public class FormProcCodeNoteEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private ListBox listProv = new ListBox();
    private Label label15 = new Label();
    private Button butSlider = new Button();
    private OpenDental.TableTimeBar tbTime;
    private Label label11 = new Label();
    private TextBox textTime2 = new TextBox();
    private Label label6 = new Label();
    private OpenDental.ODtextBox textNote;
    private Label label10 = new Label();
    public boolean IsNew = new boolean();
    private StringBuilder strBTime = new StringBuilder();
    public ProcCodeNote NoteCur;
    private boolean mouseIsDown = new boolean();
    private Point mouseOrigin = new Point();
    private OpenDental.UI.Button butDelete;
    private Point sliderOrigin = new Point();
    /**
    * 
    */
    public FormProcCodeNoteEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        tbTime.CellClicked = __MultiCellEventHandler.combine(tbTime.CellClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                tbTime_CellClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcCodeNoteEdit.class);
        this.listProv = new System.Windows.Forms.ListBox();
        this.label15 = new System.Windows.Forms.Label();
        this.butSlider = new System.Windows.Forms.Button();
        this.label11 = new System.Windows.Forms.Label();
        this.textTime2 = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.tbTime = new OpenDental.TableTimeBar();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listProv
        //
        this.listProv.FormattingEnabled = true;
        this.listProv.Location = new System.Drawing.Point(92, 51);
        this.listProv.Name = "listProv";
        this.listProv.Size = new System.Drawing.Size(176, 199);
        this.listProv.TabIndex = 2;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(89, 30);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(100, 18);
        this.label15.TabIndex = 47;
        this.label15.Text = "Provider";
        this.label15.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSlider
        //
        this.butSlider.BackColor = System.Drawing.SystemColors.ControlDark;
        this.butSlider.Location = new System.Drawing.Point(14, 56);
        this.butSlider.Name = "butSlider";
        this.butSlider.Size = new System.Drawing.Size(12, 15);
        this.butSlider.TabIndex = 50;
        this.butSlider.UseVisualStyleBackColor = false;
        this.butSlider.MouseDown += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseDown);
        this.butSlider.MouseMove += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseMove);
        this.butSlider.MouseUp += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseUp);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(78, 621);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(102, 16);
        this.label11.TabIndex = 52;
        this.label11.Text = "Minutes";
        //
        // textTime2
        //
        this.textTime2.Location = new System.Drawing.Point(12, 617);
        this.textTime2.Name = "textTime2";
        this.textTime2.Size = new System.Drawing.Size(60, 20);
        this.textTime2.TabIndex = 51;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(4, 1);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(53, 39);
        this.label6.TabIndex = 48;
        this.label6.Text = "Time Pattern";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(91, 260);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(148, 14);
        this.label10.TabIndex = 53;
        this.label10.Text = "Note";
        this.label10.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(92, 278);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(540, 219);
        this.textNote.TabIndex = 54;
        //
        // tbTime
        //
        this.tbTime.BackColor = System.Drawing.SystemColors.Window;
        this.tbTime.Location = new System.Drawing.Point(12, 51);
        this.tbTime.Name = "tbTime";
        this.tbTime.setScrollValue(150);
        this.tbTime.setSelectedIndices(new int[0]);
        this.tbTime.setSelectionMode(System.Windows.Forms.SelectionMode.None);
        this.tbTime.Size = new System.Drawing.Size(15, 561);
        this.tbTime.TabIndex = 49;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(602, 567);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(602, 608);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(207, 608);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(82, 26);
        this.butDelete.TabIndex = 55;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormProcCodeNoteEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(689, 646);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.butSlider);
        this.Controls.Add(this.tbTime);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textTime2);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcCodeNoteEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit ProcedureCode Note";
        this.Load += new System.EventHandler(this.FormProcCodeNoteEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProcCodeNoteEdit_Load(Object sender, EventArgs e) throws Exception {
        strBTime = new StringBuilder(NoteCur.ProcTime);
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (NoteCur.ProvNum == ProviderC.getListShort()[i].ProvNum)
            {
                listProv.SelectedIndex = i;
            }
             
        }
        textNote.Text = NoteCur.Note;
        fillTime();
    }

    private void fillTime() throws Exception {
        for (int i = 0;i < strBTime.Length;i++)
        {
            tbTime.Cell[0, i] = strBTime.ToString(i, 1);
            tbTime.BackGColor[0, i] = Color.White;
        }
        for (int i = strBTime.Length;i < tbTime.MaxRows;i++)
        {
            tbTime.Cell[0, i] = "";
            tbTime.BackGColor[0, i] = Color.FromName("Control");
        }
        tbTime.Refresh();
        butSlider.Location = new Point(tbTime.Location.X + 2, (tbTime.Location.Y + strBTime.Length * 14 + 1));
        textTime2.Text = (strBTime.Length * ApptDrawing.MinPerIncr).ToString();
    }

    private void tbTime_CellClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        if (e.getRow() < strBTime.Length)
        {
            if (strBTime[e.getRow()] == '/')
            {
                strBTime.Replace('/', 'X', e.getRow(), 1);
            }
            else
            {
                strBTime.Replace(strBTime[e.getRow()], '/', e.getRow(), 1);
            } 
        }
         
        fillTime();
    }

    private void butSlider_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = true;
        mouseOrigin = new Point(e.X + butSlider.Location.X, e.Y + butSlider.Location.Y);
        sliderOrigin = butSlider.Location;
    }

    private void butSlider_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
            return ;
         
        //tempPoint represents the new location of button of smooth dragging.
        Point tempPoint = new Point(sliderOrigin.X, sliderOrigin.Y + (e.Y + butSlider.Location.Y) - mouseOrigin.Y);
        int step = (int)(Math.Round((Decimal)(tempPoint.Y - tbTime.Location.Y) / 14));
        if (step == strBTime.Length)
            return ;
         
        if (step < 1)
            return ;
         
        if (step > tbTime.MaxRows - 1)
            return ;
         
        if (step > strBTime.Length)
        {
            strBTime.Append('/');
        }
         
        if (step < strBTime.Length)
        {
            strBTime.Remove(step, 1);
        }
         
        fillTime();
    }

    private void butSlider_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = false;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        ProcCodeNotes.delete(NoteCur.ProcCodeNoteNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a provider first.");
            return ;
        }
         
        NoteCur.ProcTime = strBTime.ToString();
        NoteCur.ProvNum = ProviderC.getListShort()[listProv.SelectedIndex].ProvNum;
        NoteCur.Note = textNote.Text;
        if (IsNew)
        {
            ProcCodeNotes.insert(NoteCur);
        }
        else
        {
            ProcCodeNotes.update(NoteCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


