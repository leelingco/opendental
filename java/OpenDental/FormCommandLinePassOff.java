//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;


public class FormCommandLinePassOff  extends Form 
{

    public String[] CommandLineArgs = new String[]();
    public FormCommandLinePassOff() throws Exception {
        initializeComponent();
    }

    private void formCommandLinePassOff_Load(Object sender, EventArgs e) throws Exception {
        try
        {
            TcpClient client = new TcpClient("localhost", 2123);
            NetworkStream ns = client.GetStream();
            XmlSerializer serializer = new XmlSerializer(String[].class);
            serializer.Serialize(ns, CommandLineArgs);
            ns.Close();
            client.Close();
        }
        catch (Exception __dummyCatchVar0)
        {
        }

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
        this.SuspendLayout();
        //
        // FormCommandLinePassOff
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(284, 264);
        this.Name = "FormCommandLinePassOff";
        this.Text = "FormCommandLinePassOff";
        this.Load += new System.EventHandler(this.FormCommandLinePassOff_Load);
        this.ResumeLayout(false);
    }

}


