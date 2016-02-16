//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package QueryExecutor;

import QueryExecutor.FormQueryExecutor;

public class FormQueryExecutor  extends Form 
{

    private String conStr = new String();
    public FormQueryExecutor() throws Exception {
        initializeComponent();
    }

    private void formQueryExecutor_Load(Object sender, EventArgs e) throws Exception {
        //this.Handle
        //Char char4=Convert.ToChar(4);
        //string command=char4.ToString();
        //string command=
        //	"\0x04";
        //"\004";
        //MessageBox.Show("*"+command+"*");
        setConnStr();
    }

    private void setConnStr() throws Exception {
        conStr = "Server=localhost" + ";Database=" + textDatabase.Text + ";User ID=root" + ";Password=" + ";CharSet=utf8";
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        setConnStr();
    }

    private void butExecute_Click(Object sender, EventArgs e) throws Exception {
        MySqlConnection con = new MySqlConnection(conStr);
        MySqlCommand cmd = new MySqlCommand();
        cmd.Connection = con;
        cmd.CommandText = textQuery.Text;
        int rowsChanged = 0;
        try
        {
            con.Open();
            rowsChanged = cmd.ExecuteNonQuery();
            con.Close();
            MessageBox.Show("Rows changed:" + rowsChanged.ToString());
        }
        catch (MySqlException ex)
        {
            MessageBox.Show(ex.Message);
        }
        catch (Exception ex)
        {
            MessageBox.Show("Error: " + ex.Message);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormQueryExecutor.class);
        this.textQuery = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textDatabase = new System.Windows.Forms.TextBox();
        this.butChange = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.butExecute = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // textQuery
        //
        this.textQuery.Font = new System.Drawing.Font("Courier New", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textQuery.Location = new System.Drawing.Point(12, 37);
        this.textQuery.Multiline = true;
        this.textQuery.Name = "textQuery";
        this.textQuery.Size = new System.Drawing.Size(896, 635);
        this.textQuery.TabIndex = 0;
        //
        // label1
        //
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(232, 7);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(53, 13);
        this.label1.TabIndex = 1;
        this.label1.Text = "Database";
        //
        // textDatabase
        //
        this.textDatabase.Location = new System.Drawing.Point(292, 4);
        this.textDatabase.Name = "textDatabase";
        this.textDatabase.Size = new System.Drawing.Size(178, 20);
        this.textDatabase.TabIndex = 2;
        this.textDatabase.Text = "development45";
        //
        // butChange
        //
        this.butChange.Location = new System.Drawing.Point(479, 3);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(75, 23);
        this.butChange.TabIndex = 3;
        this.butChange.Text = "change";
        this.butChange.UseVisualStyleBackColor = true;
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // label2
        //
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(12, 21);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(35, 13);
        this.label2.TabIndex = 4;
        this.label2.Text = "Query";
        //
        // butExecute
        //
        this.butExecute.Location = new System.Drawing.Point(833, 678);
        this.butExecute.Name = "butExecute";
        this.butExecute.Size = new System.Drawing.Size(75, 23);
        this.butExecute.TabIndex = 5;
        this.butExecute.Text = "Execute";
        this.butExecute.UseVisualStyleBackColor = true;
        this.butExecute.Click += new System.EventHandler(this.butExecute_Click);
        //
        // FormQueryExecutor
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(920, 717);
        this.Controls.Add(this.butExecute);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butChange);
        this.Controls.Add(this.textDatabase);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textQuery);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormQueryExecutor";
        this.Text = "Query Executor";
        this.Load += new System.EventHandler(this.FormQueryExecutor_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textQuery = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDatabase = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butChange = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butExecute = new System.Windows.Forms.Button();
}


