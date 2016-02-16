//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.InputBox;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrLabNote;

public class FormEhrLabNoteEdit  extends Form 
{

    public EhrLabNote LabNoteCur;
    public boolean IsImport = new boolean();
    public boolean IsViewOnly = new boolean();
    public FormEhrLabNoteEdit() throws Exception {
        initializeComponent();
    }

    private void formEhrLabOrders_Load(Object sender, EventArgs e) throws Exception {
        if (IsImport || IsViewOnly)
        {
            for (Object __dummyForeachVar0 : Controls)
            {
                Control c = (Control)__dummyForeachVar0;
                c.Enabled = false;
            }
            butCancel.Text = "Close";
            butCancel.Enabled = true;
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Comments",80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        String[] comments = LabNoteCur.Comments.Split('^');
        for (int i = 0;i < comments.Length;i++)
        {
            if (StringSupport.equals(LabNoteCur.Comments, ""))
            {
                break;
            }
             
            //prevents empty line from being added when the note is actually empty.
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(comments[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAddComment_Click(Object sender, EventArgs e) throws Exception {
        InputBox ipb = new InputBox("Add comment to note.");
        ipb.ShowDialog();
        if (ipb.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        String result = ipb.textResult.Text;
        result = result.Replace("|", "");
        //reserved character
        result = result.Replace("^", "");
        //reserved character
        result = result.Replace("~", "");
        //reserved character
        result = result.Replace("&", "");
        //reserved character
        result = result.Replace("#", "");
        //reserved character
        result = result.Replace("\\", "");
        //reserved character
        if (!StringSupport.equals(result, ipb.textResult.Text))
        {
            MsgBox.show(this,"Special characters were removed from comment. The characters |,^,~,\\,&, and # cannot be used in a comment.");
        }
         
        LabNoteCur.Comments += (StringSupport.equals(LabNoteCur.Comments, "") ? "" : "^") + result;
        fillGrid();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
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
        this.butCancel = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSave = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.butAddComment = new System.Windows.Forms.Button();
        this.label57 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(627, 357);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 9;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(609, 339);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Lab Note Comments");
        this.gridMain.setTranslationName(null);
        //
        // butSave
        //
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSave.Location = new System.Drawing.Point(546, 357);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(75, 23);
        this.butSave.TabIndex = 10;
        this.butSave.Text = "Save";
        this.butSave.UseVisualStyleBackColor = true;
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.Location = new System.Drawing.Point(12, 357);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 11;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        //
        // butAddComment
        //
        this.butAddComment.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddComment.Location = new System.Drawing.Point(627, 12);
        this.butAddComment.Name = "butAddComment";
        this.butAddComment.Size = new System.Drawing.Size(75, 23);
        this.butAddComment.TabIndex = 12;
        this.butAddComment.Text = "Add";
        this.butAddComment.UseVisualStyleBackColor = true;
        this.butAddComment.Click += new System.EventHandler(this.butAddComment_Click);
        //
        // label57
        //
        this.label57.Location = new System.Drawing.Point(93, 360);
        this.label57.Name = "label57";
        this.label57.Size = new System.Drawing.Size(137, 17);
        this.label57.TabIndex = 259;
        this.label57.Text = "Deletes the entire lab note";
        this.label57.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormEhrLabNoteEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(714, 392);
        this.Controls.Add(this.label57);
        this.Controls.Add(this.butAddComment);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormEhrLabNoteEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Note";
        this.Load += new System.EventHandler(this.FormEhrLabOrders_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSave = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAddComment = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label57 = new System.Windows.Forms.Label();
}


