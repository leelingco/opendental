//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EB271;
import OpenDentBusiness.X12Parse;

public class FormEtrans270EBraw  extends Form 
{

    public EB271 EB271val;
    public FormEtrans270EBraw() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEtrans270EBraw_Load(Object sender, EventArgs e) throws Exception {
        String rawText = EB271val.toString();
        if (rawText.Contains("%"))
        {
            rawText = X12Parse.urlDecode(rawText).ToLower();
            //url detection depends on a few strategically placed spaces
            rawText = rawText.Replace("http", " http");
            rawText = rawText.Replace("~", " ~");
        }
         
        textRaw.Text = rawText;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormEtrans270EBraw","#"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormEtrans270EBraw","Raw"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormEtrans270EBraw","Description"),150);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 1;i < 14;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(i.ToString());
            row.getCells().add(EB271val.Segment.get(i));
            row.getCells().add(EB271val.getDescript(i));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void textRaw_LinkClicked(Object sender, LinkClickedEventArgs e) throws Exception {
        Process.Start(e.LinkText);
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.textRaw = new System.Windows.Forms.RichTextBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(449, 18);
        this.label1.TabIndex = 4;
        this.label1.Text = "Raw benefit information as received from the insurance company";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(16, 166);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(401, 258);
        this.gridMain.TabIndex = 21;
        this.gridMain.setTitle("EB (benefit) Elements");
        this.gridMain.setTranslationName("FormEtrans270EBraw");
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(472, 401);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // textRaw
        //
        this.textRaw.Location = new System.Drawing.Point(16, 34);
        this.textRaw.Name = "textRaw";
        this.textRaw.ReadOnly = true;
        this.textRaw.Size = new System.Drawing.Size(401, 126);
        this.textRaw.TabIndex = 23;
        this.textRaw.Text = "";
        this.textRaw.LinkClicked += new System.Windows.Forms.LinkClickedEventHandler(this.textRaw_LinkClicked);
        //
        // FormEtrans270EBraw
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(572, 452);
        this.Controls.Add(this.textRaw);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Name = "FormEtrans270EBraw";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Raw Benefit Info";
        this.Load += new System.EventHandler(this.FormEtrans270EBraw_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.RichTextBox textRaw = new System.Windows.Forms.RichTextBox();
}


