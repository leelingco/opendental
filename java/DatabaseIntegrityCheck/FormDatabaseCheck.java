//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package DatabaseIntegrityCheck;

import CS2JNet.System.StringSupport;
import DatabaseIntegrityCheck.FormDatabaseCheck;

public class FormDatabaseCheck  extends Form 
{

    //private string logData;
    public FormDatabaseCheck() throws Exception {
        initializeComponent();
    }

    private void formDatabaseCheck_Load(Object sender, EventArgs e) throws Exception {
        XmlDocument document = new XmlDocument();
        if (!File.Exists("FreeDentalConfig.xml"))
        {
            textComputerName.Text = "localhost";
            textDatabase.Text = "opendental";
            textUser.Text = "root";
            return ;
        }
         
        try
        {
            document.Load("FreeDentalConfig.xml");
            XmlNodeReader reader = new XmlNodeReader(document);
            String currentElement = "";
            while (reader.Read())
            {
                if (reader.NodeType == XmlNodeType.Element)
                {
                    currentElement = reader.Name;
                }
                else if (reader.NodeType == XmlNodeType.Text)
                {
                    System.String __dummyScrutVar0 = currentElement;
                    if (__dummyScrutVar0.equals("ComputerName"))
                    {
                        textComputerName.Text = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("Database"))
                    {
                        textDatabase.Text = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("User"))
                    {
                        textUser.Text = reader.Value;
                    }
                    else if (__dummyScrutVar0.equals("Password"))
                    {
                        textPassword.Text = reader.Value;
                    }
                        
                }
                  
            }
            reader.Close();
        }
        catch (Exception __dummyCatchVar0)
        {
            //Exception e) {
            //MessageBox.Show(e.Message);
            textComputerName.Text = "localhost";
            textDatabase.Text = "opendental";
            textUser.Text = "root";
        }
    
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        //this tool would only be used with MySQL, so the current code is just fine.
        MySqlDataAdapter da = new MySqlDataAdapter();
        MySqlConnection con = new MySqlConnection("Server=" + textComputerName.Text + ";Database=" + textDatabase.Text + ";User ID=" + textUser.Text + ";Password=" + textPassword.Text + ";CharSet=utf8");
        //MySqlDataReader dr;
        MySqlCommand cmd = new MySqlCommand();
        //int InsertID;
        cmd.Connection = con;
        cmd.CommandText = "SHOW TABLES";
        DataTable table = new DataTable();
        try
        {
            Cursor = Cursors.WaitCursor;
            da = new MySqlDataAdapter(cmd);
            da.Fill(table);
            String[] tableName = new String[table.Rows.Count];
            int lastRow = new int();
            ArrayList corruptTables = new ArrayList();
            for (int i = 0;i < table.Rows.Count;i++)
            {
                tableName[i] = table.Rows[i][0].ToString();
            }
            for (int i = 0;i < tableName.Length;i++)
            {
                cmd.CommandText = "CHECK TABLE " + tableName[i];
                table = new DataTable();
                da = new MySqlDataAdapter(cmd);
                da.Fill(table);
                lastRow = table.Rows.Count - 1;
                String lastcell = table.Rows[lastRow][3].ToString();
                if (!StringSupport.equals(lastcell, "OK"))
                {
                    corruptTables.Add(tableName[i]);
                }
                 
            }
            Cursor = Cursors.Default;
            if (corruptTables.Count == 0)
            {
                MessageBox.Show("You have no corrupted tables.");
                return ;
            }
             
            String corruptS = "";
            for (int i = 0;i < corruptTables.Count;i++)
            {
                corruptS += corruptTables[i] + ", ";
            }
            if (MessageBox.Show("You have the following corrupt tables:\r\n" + corruptS + "\r\n" + "It is strongly suggested that you select Cancel and make a backup before continuing.  Select OK to repair tables.", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
            Cursor = Cursors.WaitCursor;
            for (int i = 0;i < corruptTables.Count;i++)
            {
                cmd.CommandText = "REPAIR TABLE " + corruptTables[i];
                table = new DataTable();
                da = new MySqlDataAdapter(cmd);
                da.Fill(table);
                saveToLog((String)corruptTables[i],table);
            }
            Cursor = Cursors.Default;
            //PrintLog();
            MessageBox.Show("The tables have probably been repaired, but the repairlog must be analyzed.  Open RepairLog.txt to view.");
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
        }
        finally
        {
            Cursor = Cursors.Default;
            con.Close();
        }
    }

    private void saveToLog(String corruptTable, DataTable table) throws Exception {
        FileStream fs = new FileStream("RepairLog.txt", FileMode.Append, FileAccess.Write, FileShare.Read);
        StreamWriter sw = new StreamWriter(fs);
        String line = "";
        line = corruptTable + " " + DateTime.Now.ToString() + "\r\n";
        sw.Write(line);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //logData+=line;
            line = "";
            for (int j = 0;j < table.Columns.Count;j++)
            {
                line += table.Rows[i][j].ToString() + ",";
            }
            line += "\r\n";
            sw.Write(line);
        }
        //logData+=line;
        sw.Close();
        sw = null;
        fs.Close();
        fs = null;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDatabaseCheck.class);
        this.butRun = new System.Windows.Forms.Button();
        this.textDatabase = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textComputerName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // butRun
        //
        this.butRun.Location = new System.Drawing.Point(370, 171);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 25);
        this.butRun.TabIndex = 0;
        this.butRun.Text = "Run";
        this.butRun.UseVisualStyleBackColor = true;
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // textDatabase
        //
        this.textDatabase.Location = new System.Drawing.Point(179, 47);
        this.textDatabase.Name = "textDatabase";
        this.textDatabase.Size = new System.Drawing.Size(154, 20);
        this.textDatabase.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(59, 50);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(118, 13);
        this.label1.TabIndex = 2;
        this.label1.Text = "Database";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(59, 24);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 13);
        this.label2.TabIndex = 4;
        this.label2.Text = "Computer";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textComputerName
        //
        this.textComputerName.Location = new System.Drawing.Point(179, 21);
        this.textComputerName.Name = "textComputerName";
        this.textComputerName.Size = new System.Drawing.Size(154, 20);
        this.textComputerName.TabIndex = 3;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(59, 76);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(118, 13);
        this.label3.TabIndex = 6;
        this.label3.Text = "Username";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(179, 73);
        this.textUser.Name = "textUser";
        this.textUser.Size = new System.Drawing.Size(154, 20);
        this.textUser.TabIndex = 5;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(59, 102);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(118, 13);
        this.label4.TabIndex = 8;
        this.label4.Text = "Password";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(179, 99);
        this.textPassword.Name = "textPassword";
        this.textPassword.Size = new System.Drawing.Size(154, 20);
        this.textPassword.TabIndex = 7;
        //
        // FormDatabaseCheck
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(457, 225);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textComputerName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDatabase);
        this.Controls.Add(this.butRun);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormDatabaseCheck";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Database Integrity Check";
        this.Load += new System.EventHandler(this.FormDatabaseCheck_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butRun = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textDatabase = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textComputerName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUser = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
}


