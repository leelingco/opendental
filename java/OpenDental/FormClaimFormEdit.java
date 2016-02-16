//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:48 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormClaimFormEdit;
import OpenDental.FormClaimFormItemEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.ClaimFormItems;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.PrintSituation;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClaimFormEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Panel panel2 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.VScrollBar vScrollBar1 = new System.Windows.Forms.VScrollBar();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkIsHidden = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listItems = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textXPos = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textYPos = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textWidth = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textHeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.FontDialog fontDialog1 = new System.Windows.Forms.FontDialog();
    private OpenDental.UI.Button butFont;
    private OpenDental.UI.Button butPrint;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    //private bool shiftIsDown;
    private boolean controlIsDown = new boolean();
    private boolean mouseIsDown = new boolean();
    private PointF mouseDownLoc = new PointF();
    private PointF[] oldItemLocs = new PointF[]();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private System.Windows.Forms.Label labelUniqueID = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUniqueID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelWarning = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkPrintImages = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.ValidNum textOffsetX;
    private OpenDental.ValidNum textOffsetY;
    private String[] displayStrings = new String[]();
    /**
    * 
    */
    public ClaimForm ClaimFormCur;
    /**
    * 
    */
    public FormClaimFormEdit() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimFormEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.panel2 = new System.Windows.Forms.Panel();
        this.labelWarning = new System.Windows.Forms.Label();
        this.vScrollBar1 = new System.Windows.Forms.VScrollBar();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.checkIsHidden = new System.Windows.Forms.CheckBox();
        this.label2 = new System.Windows.Forms.Label();
        this.listItems = new System.Windows.Forms.ListBox();
        this.butAdd = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.textXPos = new System.Windows.Forms.TextBox();
        this.textYPos = new System.Windows.Forms.TextBox();
        this.textWidth = new System.Windows.Forms.TextBox();
        this.textHeight = new System.Windows.Forms.TextBox();
        this.fontDialog1 = new System.Windows.Forms.FontDialog();
        this.butFont = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.labelUniqueID = new System.Windows.Forms.Label();
        this.textUniqueID = new System.Windows.Forms.TextBox();
        this.checkPrintImages = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.textOffsetX = new OpenDental.ValidNum();
        this.textOffsetY = new OpenDental.ValidNum();
        this.panel2.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(892, 667);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(892, 637);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // panel2
        //
        this.panel2.BackColor = System.Drawing.Color.White;
        this.panel2.Controls.Add(this.labelWarning);
        this.panel2.Location = new System.Drawing.Point(0, 0);
        this.panel2.Name = "panel2";
        this.panel2.Size = new System.Drawing.Size(850, 1200);
        this.panel2.TabIndex = 2;
        this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
        this.panel2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseDown);
        this.panel2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseMove);
        this.panel2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseUp);
        //
        // labelWarning
        //
        this.labelWarning.Location = new System.Drawing.Point(13, 3);
        this.labelWarning.Name = "labelWarning";
        this.labelWarning.Size = new System.Drawing.Size(782, 28);
        this.labelWarning.TabIndex = 0;
        this.labelWarning.Text = "Warning.  This is not a user-added claim form.  Any changes you make might be los" + "t when you upgrade.  Add your own claim form if you want your changes to be pres" + "erved.";
        this.labelWarning.Visible = false;
        //
        // vScrollBar1
        //
        this.vScrollBar1.Location = new System.Drawing.Point(850, 0);
        this.vScrollBar1.Name = "vScrollBar1";
        this.vScrollBar1.Size = new System.Drawing.Size(17, 650);
        this.vScrollBar1.TabIndex = 3;
        this.vScrollBar1.Scroll += new System.Windows.Forms.ScrollEventHandler(this.vScrollBar1_Scroll);
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(867, 18);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(114, 20);
        this.textDescription.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(868, 3);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(108, 16);
        this.label1.TabIndex = 5;
        this.label1.Text = "Description";
        //
        // checkIsHidden
        //
        this.checkIsHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsHidden.Location = new System.Drawing.Point(869, 41);
        this.checkIsHidden.Name = "checkIsHidden";
        this.checkIsHidden.Size = new System.Drawing.Size(109, 16);
        this.checkIsHidden.TabIndex = 6;
        this.checkIsHidden.Text = "Is Hidden";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(870, 162);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(108, 15);
        this.label2.TabIndex = 7;
        this.label2.Text = "Items:";
        //
        // listItems
        //
        this.listItems.Location = new System.Drawing.Point(867, 178);
        this.listItems.MultiColumn = true;
        this.listItems.Name = "listItems";
        this.listItems.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listItems.Size = new System.Drawing.Size(114, 329);
        this.listItems.TabIndex = 8;
        this.listItems.DoubleClick += new System.EventHandler(this.listItems_DoubleClick);
        this.listItems.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listItems_MouseDown);
        this.listItems.MouseUp += new System.Windows.Forms.MouseEventHandler(this.listItems_MouseUp);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(946, 157);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(37, 20);
        this.butAdd.TabIndex = 9;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label4
        //
        this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label4.Location = new System.Drawing.Point(869, 520);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(51, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "X Pos";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label5.Location = new System.Drawing.Point(869, 541);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(51, 16);
        this.label5.TabIndex = 11;
        this.label5.Text = "Y Pos";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label6.Location = new System.Drawing.Point(869, 562);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(51, 16);
        this.label6.TabIndex = 12;
        this.label6.Text = "Width";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label7.Location = new System.Drawing.Point(869, 583);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(51, 16);
        this.label7.TabIndex = 13;
        this.label7.Text = "Height";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textXPos
        //
        this.textXPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textXPos.Location = new System.Drawing.Point(921, 517);
        this.textXPos.Name = "textXPos";
        this.textXPos.Size = new System.Drawing.Size(60, 20);
        this.textXPos.TabIndex = 14;
        this.textXPos.Validated += new System.EventHandler(this.textXPos_Validated);
        //
        // textYPos
        //
        this.textYPos.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textYPos.Location = new System.Drawing.Point(921, 537);
        this.textYPos.Name = "textYPos";
        this.textYPos.Size = new System.Drawing.Size(60, 20);
        this.textYPos.TabIndex = 15;
        this.textYPos.Validated += new System.EventHandler(this.textYPos_Validated);
        //
        // textWidth
        //
        this.textWidth.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textWidth.Location = new System.Drawing.Point(921, 557);
        this.textWidth.Name = "textWidth";
        this.textWidth.Size = new System.Drawing.Size(60, 20);
        this.textWidth.TabIndex = 16;
        this.textWidth.Validated += new System.EventHandler(this.textWidth_Validated);
        //
        // textHeight
        //
        this.textHeight.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textHeight.Location = new System.Drawing.Point(921, 577);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(60, 20);
        this.textHeight.TabIndex = 17;
        this.textHeight.Validated += new System.EventHandler(this.textHeight_Validated);
        //
        // fontDialog1
        //
        this.fontDialog1.MaxSize = 25;
        this.fontDialog1.MinSize = 5;
        this.fontDialog1.ShowEffects = false;
        //
        // butFont
        //
        this.butFont.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFont.setAutosize(true);
        this.butFont.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFont.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFont.setCornerRadius(4F);
        this.butFont.Location = new System.Drawing.Point(870, 94);
        this.butFont.Name = "butFont";
        this.butFont.Size = new System.Drawing.Size(111, 21);
        this.butFont.TabIndex = 20;
        this.butFont.Text = "&Font";
        this.butFont.Click += new System.EventHandler(this.butFont_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Location = new System.Drawing.Point(892, 607);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 26);
        this.butPrint.TabIndex = 22;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // labelUniqueID
        //
        this.labelUniqueID.Location = new System.Drawing.Point(870, 58);
        this.labelUniqueID.Name = "labelUniqueID";
        this.labelUniqueID.Size = new System.Drawing.Size(59, 15);
        this.labelUniqueID.TabIndex = 23;
        this.labelUniqueID.Text = "UniqueID";
        this.labelUniqueID.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUniqueID
        //
        this.textUniqueID.Location = new System.Drawing.Point(928, 55);
        this.textUniqueID.Name = "textUniqueID";
        this.textUniqueID.ReadOnly = true;
        this.textUniqueID.Size = new System.Drawing.Size(51, 20);
        this.textUniqueID.TabIndex = 24;
        //
        // checkPrintImages
        //
        this.checkPrintImages.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPrintImages.Location = new System.Drawing.Point(869, 76);
        this.checkPrintImages.Name = "checkPrintImages";
        this.checkPrintImages.Size = new System.Drawing.Size(109, 16);
        this.checkPrintImages.TabIndex = 25;
        this.checkPrintImages.Text = "Print Images";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(872, 120);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(58, 15);
        this.label3.TabIndex = 26;
        this.label3.Text = "Offset X";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(872, 139);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(58, 15);
        this.label8.TabIndex = 28;
        this.label8.Text = "Offset Y";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOffsetX
        //
        this.textOffsetX.Location = new System.Drawing.Point(929, 116);
        this.textOffsetX.setMaxVal(255);
        this.textOffsetX.setMinVal(-9999);
        this.textOffsetX.Name = "textOffsetX";
        this.textOffsetX.Size = new System.Drawing.Size(50, 20);
        this.textOffsetX.TabIndex = 30;
        //
        // textOffsetY
        //
        this.textOffsetY.Location = new System.Drawing.Point(929, 136);
        this.textOffsetY.setMaxVal(255);
        this.textOffsetY.setMinVal(-9999);
        this.textOffsetY.Name = "textOffsetY";
        this.textOffsetY.Size = new System.Drawing.Size(50, 20);
        this.textOffsetY.TabIndex = 31;
        //
        // FormClaimFormEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(992, 700);
        this.Controls.Add(this.textOffsetY);
        this.Controls.Add(this.textOffsetX);
        this.Controls.Add(this.textUniqueID);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.textWidth);
        this.Controls.Add(this.textYPos);
        this.Controls.Add(this.textXPos);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butFont);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.checkPrintImages);
        this.Controls.Add(this.labelUniqueID);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.listItems);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.checkIsHidden);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.vScrollBar1);
        this.Controls.Add(this.panel2);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.Name = "FormClaimFormEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormClaimFormEdit_Closing);
        this.Load += new System.EventHandler(this.FormClaimFormEdit_Load);
        this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.FormClaimFormEdit_KeyDown);
        this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.FormClaimFormEdit_KeyUp);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormClaimFormEdit_Layout);
        this.panel2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimFormEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //must have already saved the claimform, because clicking ok, only updates it.
        fillForm();
        panel2.Invalidate();
    }

    private void formClaimFormEdit_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        vScrollBar1.Height = this.ClientSize.Height;
        vScrollBar1.Minimum = 0;
        vScrollBar1.Maximum = panel2.Height;
        //1200;
        vScrollBar1.LargeChange = ClientSize.Height;
        listItems.Height = textXPos.Location.Y - listItems.Location.Y - 4;
        listItems.Width = this.ClientSize.Width - listItems.Location.X - 3;
    }

    private void fillForm() throws Exception {
        if (!StringSupport.equals(ClaimFormCur.UniqueID, ""))
        {
            labelWarning.Visible = true;
        }
         
        //stupid thing malfunctions if you try to display a MessageBox.
        textDescription.Text = ClaimFormCur.Description;
        checkIsHidden.Checked = ClaimFormCur.IsHidden;
        textUniqueID.Text = ClaimFormCur.UniqueID.ToString();
        checkPrintImages.Checked = ClaimFormCur.PrintImages;
        textOffsetX.Text = ClaimFormCur.OffsetX.ToString();
        textOffsetY.Text = ClaimFormCur.OffsetY.ToString();
        if (StringSupport.equals(ClaimFormCur.FontName, "") || ClaimFormCur.FontSize == 0)
        {
            ClaimFormCur.FontName = "Arial";
            ClaimFormCur.FontSize = 8;
        }
         
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        listItems.Items.Clear();
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, ""))
            {
                //field
                listItems.Items.Add(ClaimFormCur.Items[i].FieldName);
            }
            else
            {
                //image
                listItems.Items.Add(ClaimFormCur.Items[i].ImageFileName);
            } 
        }
    }

    private void fillItem() throws Exception {
        if (listItems.SelectedIndices.Count == 0)
        {
            textXPos.Text = "";
            textYPos.Text = "";
            textWidth.Text = "";
            textHeight.Text = "";
        }
        else if (listItems.SelectedIndices.Count == 1)
        {
            //ClaimFormItems.Cur=ClaimFormItems.ListForForm[listItems.SelectedIndices[0]];
            textXPos.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].XPos.ToString();
            textYPos.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].YPos.ToString();
            textWidth.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].Width.ToString();
            textHeight.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].Height.ToString();
        }
        else
        {
            //2 or more selected
            //only shows a value if all are the same
            boolean xSame = true;
            boolean ySame = true;
            boolean wSame = true;
            boolean hSame = true;
            for (int i = 1;i < listItems.SelectedIndices.Count;i++)
            {
                //loop starts with second items to compare
                if (ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos != ClaimFormCur.Items[listItems.SelectedIndices[i - 1]].XPos)
                {
                    xSame = false;
                }
                 
                if (ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos != ClaimFormCur.Items[listItems.SelectedIndices[i - 1]].YPos)
                {
                    ySame = false;
                }
                 
                if (ClaimFormCur.Items[listItems.SelectedIndices[i]].Width != ClaimFormCur.Items[listItems.SelectedIndices[i - 1]].Width)
                {
                    wSame = false;
                }
                 
                if (ClaimFormCur.Items[listItems.SelectedIndices[i]].Height != ClaimFormCur.Items[listItems.SelectedIndices[i - 1]].Height)
                {
                    hSame = false;
                }
                 
            }
            if (xSame)
                textXPos.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].XPos.ToString();
            else
                textXPos.Text = ""; 
            if (ySame)
                textYPos.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].YPos.ToString();
            else
                textYPos.Text = ""; 
            if (wSame)
                textWidth.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].Width.ToString();
            else
                textWidth.Text = ""; 
            if (hSame)
                textHeight.Text = ClaimFormCur.Items[listItems.SelectedIndices[0]].Height.ToString();
            else
                textHeight.Text = ""; 
        }  
    }

    //fill item
    private void panel2_Paint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        //could make this much faster if invalidated only certain areas, but no time
        fillDisplayStrings();
        Graphics grfx = e.Graphics;
        Color myColor = new Color();
        float xPosRect = new float();
        float xPosText = new float();
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, ""))
            {
                //field
                if (listItems.SelectedIndices.Contains(i))
                    myColor = Color.Red;
                else
                    myColor = Color.Blue; 
                xPosRect = ClaimFormCur.Items[i].XPos;
                xPosText = xPosRect;
                if (StringSupport.equals(displayStrings[i], "1234.00"))
                {
                    xPosRect -= ClaimFormCur.Items[i].Width;
                    //this aligns it to the right
                    xPosText -= grfx.MeasureString("1234.00", new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize)).Width;
                }
                 
                grfx.DrawRectangle(new Pen(myColor), xPosRect, ClaimFormCur.Items[i].YPos, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height);
                grfx.DrawString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize), new SolidBrush(myColor), new RectangleF(xPosText, ClaimFormCur.Items[i].YPos, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height));
            }
            else
            {
                //image
                Image thisImage = new Image();
                String extension = new String();
                if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2006.gif"))
                {
                    thisImage = CDT.Class1.GetADA2006();
                    extension = ".gif";
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2012.gif"))
                {
                    thisImage = CDT.Class1.GetADA2012();
                    extension = ".gif";
                }
                else
                {
                    String fileName = CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), ClaimFormCur.Items[i].ImageFileName);
                    if (!File.Exists(fileName))
                    {
                        grfx.DrawString("IMAGE FILE NOT FOUND", new Font(FontFamily.GenericSansSerif, 12, FontStyle.Bold), Brushes.DarkRed, 0, 0);
                        continue;
                    }
                     
                    //MessageBox.Show("File not found.");
                    thisImage = Image.FromFile(fileName);
                    extension = Path.GetExtension(fileName);
                }  
                if (StringSupport.equals(extension, ".jpg"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos, ClaimFormCur.Items[i].YPos, (int)(thisImage.Width / thisImage.HorizontalResolution * 100), (int)(thisImage.Height / thisImage.VerticalResolution * 100));
                }
                else if (StringSupport.equals(extension, ".gif"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos, ClaimFormCur.Items[i].YPos, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height);
                }
                else if (StringSupport.equals(extension, ".emf"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos, ClaimFormCur.Items[i].YPos, thisImage.Width, thisImage.Height);
                }
                   
            } 
        }
    }

    private void fillDisplayStrings() throws Exception {
        displayStrings = new String[ClaimFormCur.Items.Length];
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            FieldName __dummyScrutVar0 = ClaimFormCur.Items[i].FieldName;
            //image="", or most fields = name of field
            //bool
            //of either kind
            if (__dummyScrutVar0.equals("IsPreAuth") || __dummyScrutVar0.equals("IsStandardClaim") || __dummyScrutVar0.equals("IsMedicaidClaim") || __dummyScrutVar0.equals("IsGroupHealthPlan") || __dummyScrutVar0.equals("OtherInsExists") || __dummyScrutVar0.equals("OtherInsNotExists") || __dummyScrutVar0.equals("OtherInsExistsDent") || __dummyScrutVar0.equals("OtherInsExistsMed") || __dummyScrutVar0.equals("OtherInsSubscrIsMale") || __dummyScrutVar0.equals("OtherInsSubscrIsFemale") || __dummyScrutVar0.equals("OtherInsRelatIsSelf") || __dummyScrutVar0.equals("OtherInsRelatIsSpouse") || __dummyScrutVar0.equals("OtherInsRelatIsChild") || __dummyScrutVar0.equals("OtherInsRelatIsOther") || __dummyScrutVar0.equals("SubscrIsMale") || __dummyScrutVar0.equals("SubscrIsFemale") || __dummyScrutVar0.equals("SubscrIsMarried") || __dummyScrutVar0.equals("SubscrIsSingle") || __dummyScrutVar0.equals("SubscrIsFTStudent") || __dummyScrutVar0.equals("SubscrIsPTStudent") || __dummyScrutVar0.equals("RelatIsSelf") || __dummyScrutVar0.equals("RelatIsSpouse") || __dummyScrutVar0.equals("RelatIsChild") || __dummyScrutVar0.equals("RelatIsOther") || __dummyScrutVar0.equals("IsFTStudent") || __dummyScrutVar0.equals("IsPTStudent") || __dummyScrutVar0.equals("IsStudent") || __dummyScrutVar0.equals("PatientIsMale") || __dummyScrutVar0.equals("PatientIsFemale") || __dummyScrutVar0.equals("PatientIsMarried") || __dummyScrutVar0.equals("PatientIsSingle") || __dummyScrutVar0.equals("Miss1") || __dummyScrutVar0.equals("Miss2") || __dummyScrutVar0.equals("Miss3") || __dummyScrutVar0.equals("Miss4") || __dummyScrutVar0.equals("Miss5") || __dummyScrutVar0.equals("Miss6") || __dummyScrutVar0.equals("Miss7") || __dummyScrutVar0.equals("Miss8") || __dummyScrutVar0.equals("Miss9") || __dummyScrutVar0.equals("Miss10") || __dummyScrutVar0.equals("Miss11") || __dummyScrutVar0.equals("Miss12") || __dummyScrutVar0.equals("Miss13") || __dummyScrutVar0.equals("Miss14") || __dummyScrutVar0.equals("Miss15") || __dummyScrutVar0.equals("Miss16") || __dummyScrutVar0.equals("Miss17") || __dummyScrutVar0.equals("Miss18") || __dummyScrutVar0.equals("Miss19") || __dummyScrutVar0.equals("Miss20") || __dummyScrutVar0.equals("Miss21") || __dummyScrutVar0.equals("Miss22") || __dummyScrutVar0.equals("Miss23") || __dummyScrutVar0.equals("Miss24") || __dummyScrutVar0.equals("Miss25") || __dummyScrutVar0.equals("Miss26") || __dummyScrutVar0.equals("Miss27") || __dummyScrutVar0.equals("Miss28") || __dummyScrutVar0.equals("Miss29") || __dummyScrutVar0.equals("Miss30") || __dummyScrutVar0.equals("Miss31") || __dummyScrutVar0.equals("Miss32") || __dummyScrutVar0.equals("PlaceIsOffice") || __dummyScrutVar0.equals("PlaceIsHospADA2002") || __dummyScrutVar0.equals("PlaceIsExtCareFacilityADA2002") || __dummyScrutVar0.equals("PlaceIsOtherADA2002") || __dummyScrutVar0.equals("PlaceIsInpatHosp") || __dummyScrutVar0.equals("PlaceIsOutpatHosp") || __dummyScrutVar0.equals("PlaceIsAdultLivCareFac") || __dummyScrutVar0.equals("PlaceIsSkilledNursFac") || __dummyScrutVar0.equals("PlaceIsPatientsHome") || __dummyScrutVar0.equals("PlaceIsOtherLocation") || __dummyScrutVar0.equals("IsRadiographsAttached") || __dummyScrutVar0.equals("RadiographsNotAttached") || __dummyScrutVar0.equals("IsEnclosuresAttached") || __dummyScrutVar0.equals("IsNotOrtho") || __dummyScrutVar0.equals("IsOrtho") || __dummyScrutVar0.equals("IsNotProsth") || __dummyScrutVar0.equals("IsInitialProsth") || __dummyScrutVar0.equals("IsNotReplacementProsth") || __dummyScrutVar0.equals("IsReplacementProsth") || __dummyScrutVar0.equals("IsOccupational") || __dummyScrutVar0.equals("IsNotOccupational") || __dummyScrutVar0.equals("IsAutoAccident") || __dummyScrutVar0.equals("IsNotAutoAccident") || __dummyScrutVar0.equals("IsOtherAccident") || __dummyScrutVar0.equals("IsNotOtherAccident") || __dummyScrutVar0.equals("IsNotAccident") || __dummyScrutVar0.equals("IsAccident") || __dummyScrutVar0.equals("BillingDentistNumIsSSN") || __dummyScrutVar0.equals("BillingDentistNumIsTIN"))
            {
                displayStrings[i] = "X";
            }
            else //short strings custom
            if (__dummyScrutVar0.equals("PriInsST") || __dummyScrutVar0.equals("OtherInsST"))
            {
                displayStrings[i] = "ST";
            }
            else //date
            if (__dummyScrutVar0.equals("PatientDOB") || __dummyScrutVar0.equals("SubscrDOB") || __dummyScrutVar0.equals("OtherInsSubscrDOB") || __dummyScrutVar0.equals("P1Date") || __dummyScrutVar0.equals("P2Date") || __dummyScrutVar0.equals("P3Date") || __dummyScrutVar0.equals("P4Date") || __dummyScrutVar0.equals("P5Date") || __dummyScrutVar0.equals("P6Date") || __dummyScrutVar0.equals("P7Date") || __dummyScrutVar0.equals("P8Date") || __dummyScrutVar0.equals("P9Date") || __dummyScrutVar0.equals("P10Date") || __dummyScrutVar0.equals("PatientReleaseDate") || __dummyScrutVar0.equals("PatientAssignmentDate") || __dummyScrutVar0.equals("DateOrthoPlaced") || __dummyScrutVar0.equals("DatePriorProsthPlaced") || __dummyScrutVar0.equals("AccidentDate") || __dummyScrutVar0.equals("TreatingDentistSigDate"))
            {
                if (StringSupport.equals(ClaimFormCur.Items[i].FormatString, ""))
                    displayStrings[i] = "";
                else
                    //DateTime.Today.ToShortDateString();
                    displayStrings[i] = DateTime.Today.ToString(ClaimFormCur.Items[i].FormatString); 
            }
            else if (__dummyScrutVar0.equals("P1Fee") || __dummyScrutVar0.equals("P2Fee") || __dummyScrutVar0.equals("P3Fee") || __dummyScrutVar0.equals("P4Fee") || __dummyScrutVar0.equals("P5Fee") || __dummyScrutVar0.equals("P6Fee") || __dummyScrutVar0.equals("P7Fee") || __dummyScrutVar0.equals("P8Fee") || __dummyScrutVar0.equals("P9Fee") || __dummyScrutVar0.equals("P10Fee") || __dummyScrutVar0.equals("P1Lab") || __dummyScrutVar0.equals("P2Lab") || __dummyScrutVar0.equals("P3Lab") || __dummyScrutVar0.equals("P4Lab") || __dummyScrutVar0.equals("P5Lab") || __dummyScrutVar0.equals("P6Lab") || __dummyScrutVar0.equals("P7Lab") || __dummyScrutVar0.equals("P8Lab") || __dummyScrutVar0.equals("P9Lab") || __dummyScrutVar0.equals("P10Lab") || __dummyScrutVar0.equals("P1FeeMinusLab") || __dummyScrutVar0.equals("P2FeeMinusLab") || __dummyScrutVar0.equals("P3FeeMinusLab") || __dummyScrutVar0.equals("P4FeeMinusLab") || __dummyScrutVar0.equals("P5FeeMinusLab") || __dummyScrutVar0.equals("P6FeeMinusLab") || __dummyScrutVar0.equals("P7FeeMinusLab") || __dummyScrutVar0.equals("P8FeeMinusLab") || __dummyScrutVar0.equals("P9FeeMinusLab") || __dummyScrutVar0.equals("P10FeeMinusLab") || __dummyScrutVar0.equals("MedInsAAmtDue") || __dummyScrutVar0.equals("MedInsBAmtDue") || __dummyScrutVar0.equals("MedInsCAmtDue") || __dummyScrutVar0.equals("MedInsAPriorPmt") || __dummyScrutVar0.equals("MedInsBPriorPmt") || __dummyScrutVar0.equals("MedInsCPriorPmt") || __dummyScrutVar0.equals("TotalFee") || __dummyScrutVar0.equals("MedValAmount39a") || __dummyScrutVar0.equals("MedValAmount39b") || __dummyScrutVar0.equals("MedValAmount39c") || __dummyScrutVar0.equals("MedValAmount39d") || __dummyScrutVar0.equals("MedValAmount40a") || __dummyScrutVar0.equals("MedValAmount40b") || __dummyScrutVar0.equals("MedValAmount40c") || __dummyScrutVar0.equals("MedValAmount40d") || __dummyScrutVar0.equals("MedValAmount41a") || __dummyScrutVar0.equals("MedValAmount41b") || __dummyScrutVar0.equals("MedValAmount41c") || __dummyScrutVar0.equals("MedValAmount41d"))
            {
                displayStrings[i] = "1234.00";
            }
            else if (__dummyScrutVar0.equals("MedUniformBillType"))
            {
                displayStrings[i] = "831";
            }
            else if (__dummyScrutVar0.equals("MedAdmissionTypeCode") || __dummyScrutVar0.equals("MedAdmissionSourceCode"))
            {
                displayStrings[i] = "1";
            }
            else if (__dummyScrutVar0.equals("MedPatientStatusCode") || __dummyScrutVar0.equals("MedConditionCode18") || __dummyScrutVar0.equals("MedConditionCode19") || __dummyScrutVar0.equals("MedConditionCode20") || __dummyScrutVar0.equals("MedConditionCode21") || __dummyScrutVar0.equals("MedConditionCode22") || __dummyScrutVar0.equals("MedConditionCode23") || __dummyScrutVar0.equals("MedConditionCode24") || __dummyScrutVar0.equals("MedConditionCode25") || __dummyScrutVar0.equals("MedConditionCode26") || __dummyScrutVar0.equals("MedConditionCode27") || __dummyScrutVar0.equals("MedConditionCode28"))
            {
                displayStrings[i] = "01";
            }
            else if (__dummyScrutVar0.equals("Remarks"))
            {
                displayStrings[i] = "This is a test of the remarks section of the claim form.";
            }
            else if (__dummyScrutVar0.equals("FixedText"))
            {
                displayStrings[i] = ClaimFormCur.Items[i].FormatString;
            }
            else
            {
                displayStrings[i] = ClaimFormCur.Items[i].FieldName;
            }         
        }
    }

    //switch
    //for
    private void vScrollBar1_Scroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        panel2.Location = new Point(0, -vScrollBar1.Value);
    }

    private void listItems_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        int index = listItems.SelectedIndices[0];
        FormClaimFormItemEdit FormCFIE = new FormClaimFormItemEdit();
        FormCFIE.CFIcur = ClaimFormCur.Items[index];
        FormCFIE.ShowDialog();
        ClaimFormItems.refreshCache();
        fillForm();
        panel2.Invalidate();
        if (listItems.Items.Count > index)
            //in case the last item in the list was deleted
            listItems.SetSelected(index, true);
         
        fillItem();
    }

    private void textXPos_Validated(Object sender, System.EventArgs e) throws Exception {
        if (listItems.SelectedIndices.Count == 0)
            return ;
         
        if (listItems.SelectedIndices.Count > 1 && StringSupport.equals(textXPos.Text, ""))
            return ;
         
        //blank means that the values for the selected items are not the same
        //so disregard unless you put in an actual value
        float xPos = new float();
        try
        {
            xPos = Convert.ToSingle(textXPos.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            xPos = 0;
        }

        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos = xPos;
            ClaimFormItems.Update(ClaimFormCur.Items[listItems.SelectedIndices[i]]);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void textYPos_Validated(Object sender, System.EventArgs e) throws Exception {
        if (listItems.SelectedIndices.Count == 0)
            return ;
         
        if (listItems.SelectedIndices.Count > 1 && StringSupport.equals(textYPos.Text, ""))
            return ;
         
        float yPos = new float();
        try
        {
            yPos = Convert.ToSingle(textYPos.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            yPos = 0;
        }

        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos = yPos;
            ClaimFormItems.Update(ClaimFormCur.Items[listItems.SelectedIndices[i]]);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void textWidth_Validated(Object sender, System.EventArgs e) throws Exception {
        //MessageBox.Show("width");
        if (listItems.SelectedIndices.Count == 0)
            return ;
         
        if (listItems.SelectedIndices.Count > 1 && StringSupport.equals(textWidth.Text, ""))
            return ;
         
        float width = new float();
        try
        {
            width = Convert.ToSingle(textWidth.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            width = 0;
        }

        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            ClaimFormCur.Items[listItems.SelectedIndices[i]].Width = width;
            ClaimFormItems.Update(ClaimFormCur.Items[listItems.SelectedIndices[i]]);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void textHeight_Validated(Object sender, System.EventArgs e) throws Exception {
        if (listItems.SelectedIndices.Count == 0)
            return ;
         
        if (listItems.SelectedIndices.Count > 1 && StringSupport.equals(textHeight.Text, ""))
            return ;
         
        float height = new float();
        try
        {
            height = Convert.ToSingle(textHeight.Text);
        }
        catch (Exception __dummyCatchVar3)
        {
            height = 0;
        }

        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            ClaimFormCur.Items[listItems.SelectedIndices[i]].Height = height;
            ClaimFormItems.Update(ClaimFormCur.Items[listItems.SelectedIndices[i]]);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void listItems_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    private void listItems_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        fillItem();
        panel2.Invalidate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormClaimFormItemEdit FormCFIE = new FormClaimFormItemEdit();
        FormCFIE.CFIcur = new ClaimFormItem();
        FormCFIE.CFIcur.ClaimFormNum = ClaimFormCur.ClaimFormNum;
        FormCFIE.CFIcur.YPos = 540;
        FormCFIE.IsNew = true;
        FormCFIE.ShowDialog();
        if (FormCFIE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //MessageBox.Show(ClaimFormItems.Cur.ClaimFormItemNum.ToString());
        //ClaimFormItems.Refresh();
        //ClaimFormCur.Items=ClaimFormItems.GetListForForm(ClaimFormCur.ClaimFormNum);
        //FillDisplayStrings();
        //set the width and height
        //Graphics grfx=panel2.CreateGraphics();
        //yes, I know this is buggy, but it's not that important, just a little annoying.
        /*if(displayStrings[ClaimFormItems.ListForForm.Length-1]!="X"){
        				ClaimFormItems.Cur.Width=(float)(int)grfx.MeasureString
        					(displayStrings[ClaimFormItems.ListForForm.Length-1]
        					,new Font(ClaimFormCur.FontName,ClaimFormCur.FontSize)).Width;
        				ClaimFormItems.Cur.Height=(float)(int)grfx.MeasureString
        					(displayStrings[ClaimFormItems.ListForForm.Length-1]
        					,new Font(ClaimFormCur.FontName,ClaimFormCur.FontSize)).Height;
        			}*/
        //grfx.Dispose();
        //ClaimFormItems.Cur.YPos=540;
        //ClaimFormItems.UpdateCur();
        ClaimFormItems.refreshCache();
        fillForm();
        //also gets ListForForm
        listItems.ClearSelected();
        listItems.SetSelected(listItems.Items.Count - 1, true);
        //selects last item in list
        panel2.Invalidate();
        //also Fills displayStrings
        fillItem();
    }

    private void butFont_Click(Object sender, System.EventArgs e) throws Exception {
        Font myFont = new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize);
        fontDialog1.Font = myFont;
        if (fontDialog1.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        if (fontDialog1.Font.Style != FontStyle.Regular)
        {
            MessageBox.Show(Lan.g(this,"Only regular font style allowed."));
        }
         
        ClaimFormCur.FontName = fontDialog1.Font.Name;
        ClaimFormCur.FontSize = fontDialog1.Font.Size;
        //MessageBox.Show(fontDialog1.Font.Size.ToString());
        panel2.Invalidate();
    }

    //fontDialog1.Font
    private void formClaimFormEdit_KeyDown(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        e.Handled = true;
        if (e.KeyCode != Keys.Up && e.KeyCode != Keys.Down && e.KeyCode != Keys.Left && e.KeyCode != Keys.Right && e.KeyCode != Keys.ShiftKey && e.KeyCode != Keys.ControlKey)
        {
            return ;
        }
         
        //if(e.Shift){
        //	shiftIsDown=true;
        //}
        if (e.Control)
        {
            controlIsDown = true;
        }
         
        if (e.KeyCode == Keys.ShiftKey)
        {
            return ;
        }
         
        if (e.KeyCode == Keys.ControlKey)
        {
            return ;
        }
         
        //loop through all items selected and change them
        ClaimFormItem curItem;
        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            //MessageBox.Show(i.ToString());
            curItem = ClaimFormCur.Items[listItems.SelectedIndices[i]];
            KeyCode __dummyScrutVar1 = e.KeyCode;
            if (__dummyScrutVar1.equals(Keys.Up))
            {
                if (e.Shift)
                    curItem.YPos -= 10;
                else
                    curItem.YPos -= 1; 
            }
            else if (__dummyScrutVar1.equals(Keys.Down))
            {
                if (e.Shift)
                    curItem.YPos += 10;
                else
                    curItem.YPos += 1; 
            }
            else if (__dummyScrutVar1.equals(Keys.Left))
            {
                if (e.Shift)
                    curItem.XPos -= 10;
                else
                    curItem.XPos -= 1; 
            }
            else if (__dummyScrutVar1.equals(Keys.Right))
            {
                if (e.Shift)
                    curItem.XPos += 10;
                else
                    curItem.XPos += 1; 
            }
                
            if (curItem.YPos < 0)
                curItem.YPos = 0;
             
            if (curItem.YPos > 1100)
                curItem.YPos = 1100;
             
            if (curItem.XPos < 0)
                curItem.XPos = 0;
             
            if (curItem.XPos > 850)
                curItem.XPos = 850;
             
            ClaimFormItems.update(curItem);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void formClaimFormEdit_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        //shiftIsDown=false;
        controlIsDown = false;
    }

    private void panel2_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = true;
        mouseDownLoc = new Point(e.X, e.Y);
        //find the item and select it in the list
        float width = new float();
        //of item
        float height = new float();
        Graphics grfx = panel2.CreateGraphics();
        for (int i = ClaimFormCur.Items.Length - 1;i > 0;i--)
        {
            //start at the end of the list and work backwards until match
            if (ClaimFormCur.Items[i].Width == 0 || ClaimFormCur.Items[i].Height == 0)
            {
                width = grfx.MeasureString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize)).Width;
                height = grfx.MeasureString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize)).Height;
            }
            else
            {
                //a width and height are available, so use them
                width = ClaimFormCur.Items[i].Width;
                height = ClaimFormCur.Items[i].Height;
            } 
            if (e.X > ClaimFormCur.Items[i].XPos && e.X < ClaimFormCur.Items[i].XPos + width && e.Y > ClaimFormCur.Items[i].YPos && e.Y < ClaimFormCur.Items[i].YPos + height)
            {
                if (controlIsDown)
                {
                    if (listItems.SelectedIndices.Contains(i))
                    {
                        //if this item already selected
                        listItems.SetSelected(i, false);
                    }
                    else
                    {
                        //unselect it
                        //if not selected
                        listItems.SetSelected(i, true);
                    } 
                }
                else
                {
                    //select it
                    //control not down
                    //if multiple items already selected
                    if (listItems.SelectedIndices.Count > 1 && listItems.SelectedIndices.Contains(i))
                    {
                    }
                    else
                    {
                        //and this is one of them
                        //don't do anything.  The user is getting ready to drag a group
                        listItems.ClearSelected();
                        listItems.SetSelected(i, true);
                    } 
                } 
                break;
            }
             
        }
        grfx.Dispose();
        fillItem();
        //also sets the oldItemLocs
        oldItemLocs = new PointF[listItems.SelectedIndices.Count];
        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            //then a normal loop to set oldlocs for dragging
            oldItemLocs[i] = new PointF((float)ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos, (float)ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos);
        }
        panel2.Invalidate();
    }

    private void panel2_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown || listItems.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        for (int i = 0;i < listItems.SelectedIndices.Count;i++)
        {
            //ClaimFormItems.Cur=ClaimFormCur.Items[listItems.SelectedIndices[i]];
            ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos = oldItemLocs[i].X + e.X - mouseDownLoc.X;
            ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos = oldItemLocs[i].Y + e.Y - mouseDownLoc.Y;
            if (ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos < 0)
                ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos = 0;
             
            if (ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos > 1100)
                ClaimFormCur.Items[listItems.SelectedIndices[i]].YPos = 1100;
             
            if (ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos < 0)
                ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos = 0;
             
            if (ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos > 850)
                ClaimFormCur.Items[listItems.SelectedIndices[i]].XPos = 850;
             
            ClaimFormItems.Update(ClaimFormCur.Items[listItems.SelectedIndices[i]]);
        }
        ClaimFormItems.refreshCache();
        ClaimFormCur.Items = ClaimFormItems.getListForForm(ClaimFormCur.ClaimFormNum);
        fillItem();
        panel2.Invalidate();
    }

    private void panel2_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = false;
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (!updateCur())
            return ;
         
        pd2 = new PrintDocument();
        pd2.OriginAtMargins = true;
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        if (!PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Claim form " + ClaimFormCur.Description + " printed"))
        {
            return ;
        }
         
        try
        {
            pd2.Print();
        }
        catch (Exception __dummyCatchVar4)
        {
            MessageBox.Show(Lan.g(this,"Printer not available."));
        }
    
    }

    private void pd2_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        //raised for each page to be printed.
        Graphics grfx = ev.Graphics;
        Color myColor = new Color();
        float xPosRect = new float();
        float xPosText = new float();
        for (int i = 0;i < ClaimFormCur.Items.Length;i++)
        {
            if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, ""))
            {
                //field
                myColor = Color.Blue;
                xPosRect = ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX;
                xPosText = xPosRect;
                if (StringSupport.equals(displayStrings[i], "1234.00"))
                {
                    xPosRect -= ClaimFormCur.Items[i].Width;
                    //this aligns it to the right
                    xPosText -= grfx.MeasureString("1234.00", new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize)).Width;
                }
                 
                grfx.DrawRectangle(new Pen(myColor), xPosRect, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height);
                grfx.DrawString(displayStrings[i], new Font(ClaimFormCur.FontName, ClaimFormCur.FontSize), new SolidBrush(myColor), new RectangleF(xPosText, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height));
            }
            else
            {
                //image
                if (!ClaimFormCur.PrintImages)
                {
                    continue;
                }
                 
                String fileName = CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), ClaimFormCur.Items[i].ImageFileName);
                Image thisImage = null;
                if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2006.gif"))
                {
                    thisImage = CDT.Class1.GetADA2006();
                }
                else if (StringSupport.equals(ClaimFormCur.Items[i].ImageFileName, "ADA2012.gif"))
                {
                    thisImage = CDT.Class1.GetADA2012();
                }
                else
                {
                    if (!File.Exists(fileName))
                    {
                        MessageBox.Show("File not found.");
                        continue;
                    }
                     
                    thisImage = Image.FromFile(fileName);
                }  
                if (StringSupport.equals(fileName.Substring(fileName.Length - 3), "jpg"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, (int)(thisImage.Width / thisImage.HorizontalResolution * 100), (int)(thisImage.Height / thisImage.VerticalResolution * 100));
                }
                else if (StringSupport.equals(fileName.Substring(fileName.Length - 3), "gif"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, ClaimFormCur.Items[i].Width, ClaimFormCur.Items[i].Height);
                }
                else if (StringSupport.equals(fileName.Substring(fileName.Length - 3), "emf"))
                {
                    grfx.DrawImage(thisImage, ClaimFormCur.Items[i].XPos + ClaimFormCur.OffsetX, ClaimFormCur.Items[i].YPos + ClaimFormCur.OffsetY, thisImage.Width, thisImage.Height);
                }
                   
            } 
        }
        ev.HasMorePages = false;
    }

    private boolean updateCur() throws Exception {
        if (!StringSupport.equals(textOffsetX.errorProvider1.GetError(textOffsetX), "") || !StringSupport.equals(textOffsetY.errorProvider1.GetError(textOffsetY), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return false;
        }
         
        ClaimFormCur.Description = textDescription.Text;
        ClaimFormCur.IsHidden = checkIsHidden.Checked;
        ClaimFormCur.UniqueID = textUniqueID.Text;
        ClaimFormCur.PrintImages = checkPrintImages.Checked;
        ClaimFormCur.OffsetX = PIn.Int(textOffsetX.Text);
        ClaimFormCur.OffsetY = PIn.Int(textOffsetY.Text);
        ClaimForms.update(ClaimFormCur);
        return true;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //MessageBox.Show(ClaimForms.Cur.ClaimFormNum.ToString());
        if (!updateCur())
            return ;
         
        if (StringSupport.equals(ClaimFormCur.Description, ""))
        {
            MessageBox.Show(Lan.g(this,"You must enter a description first."));
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formClaimFormEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
            return ;
         
        if (IsNew)
        {
            ClaimForms.delete(ClaimFormCur);
        }
         
    }

}


