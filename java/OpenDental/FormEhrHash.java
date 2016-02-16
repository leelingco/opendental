//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.EmailMessages;

public class FormEhrHash  extends Form 
{

    public FormEhrHash() throws Exception {
        initializeComponent();
    }

    private void butTransmit_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textHash.Text.Trim(), "") || StringSupport.equals(textMessage.Text.Trim(), ""))
        {
            MessageBox.Show("Data or hash should not be blank.");
            return ;
        }
         
        String attachContents = "Original message:\r\n" + textMessage.Text + "\r\n\r\n\r\nHash:\r\n" + textHash.Text;
        Cursor = Cursors.WaitCursor;
        try
        {
            EmailMessages.sendTestUnsecure("Hash","hash.txt",attachContents);
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MessageBox.Show("Sent");
    }

    private void butGenerate_Click(Object sender, EventArgs e) throws Exception {
        byte[] data = Encoding.ASCII.GetBytes(textMessage.Text);
        HashAlgorithm algorithm = SHA1.Create();
        byte[] hashbytes = algorithm.ComputeHash(data);
        byte digit1 = new byte();
        byte digit2 = new byte();
        String char1 = new String();
        String char2 = new String();
        StringBuilder strbuild = new StringBuilder();
        for (int i = 0;i < hashbytes.Length;i++)
        {
            if (hashbytes[i] == 0)
            {
                digit1 = 0;
                digit2 = 0;
            }
            else
            {
                digit1 = (byte)Math.Floor((double)hashbytes[i] / 16d);
                //double remainder=Math.IEEERemainder((double)hashbytes[i],16d);
                digit2 = (byte)(hashbytes[i] - (byte)(16 * digit1));
            } 
            char1 = ByteToStr(digit1);
            char2 = ByteToStr(digit2);
            strbuild.Append(char1);
            strbuild.Append(char2);
        }
        //return strbuild.ToString();
        //string strHash="";
        //for(int i=0;i<hash.Length;i++) {
        //	strHash+=ByteToStr(hash[i]);
        //}
        textHash.Text = strbuild.ToString();
    }

    /**
    * The only valid input is a value between 0 and 15.  Text returned will be 1-9 or a-f.
    */
    private static String byteToStr(Byte byteVal) throws Exception {
        //No need to check RemotingRole; no call to db.
        Byte __dummyScrutVar0 = byteVal;
        if (__dummyScrutVar0.equals(10))
        {
            return "a";
        }
        else if (__dummyScrutVar0.equals(11))
        {
            return "b";
        }
        else if (__dummyScrutVar0.equals(12))
        {
            return "c";
        }
        else if (__dummyScrutVar0.equals(13))
        {
            return "d";
        }
        else if (__dummyScrutVar0.equals(14))
        {
            return "e";
        }
        else if (__dummyScrutVar0.equals(15))
        {
            return "f";
        }
        else
        {
            return byteVal.ToString();
        }      
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
        this.textHash = new System.Windows.Forms.TextBox();
        this.butGererate = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textMessage = new System.Windows.Forms.TextBox();
        this.butTransmit = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // textHash
        //
        this.textHash.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.textHash.Location = new System.Drawing.Point(192, 220);
        this.textHash.Name = "textHash";
        this.textHash.Size = new System.Drawing.Size(357, 20);
        this.textHash.TabIndex = 1;
        //
        // butGererate
        //
        this.butGererate.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butGererate.Location = new System.Drawing.Point(12, 218);
        this.butGererate.Name = "butGererate";
        this.butGererate.Size = new System.Drawing.Size(104, 23);
        this.butGererate.TabIndex = 2;
        this.butGererate.Text = "Generate Hash";
        this.butGererate.UseVisualStyleBackColor = true;
        this.butGererate.Click += new System.EventHandler(this.butGenerate_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(10, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(299, 17);
        this.label1.TabIndex = 3;
        this.label1.Text = "Enter data below";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(27, 244);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(133, 17);
        this.label2.TabIndex = 4;
        this.label2.Text = "( uses SHA-1 )";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label3.Location = new System.Drawing.Point(131, 221);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(57, 17);
        this.label3.TabIndex = 5;
        this.label3.Text = "Hash";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMessage
        //
        this.textMessage.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textMessage.Location = new System.Drawing.Point(12, 34);
        this.textMessage.Multiline = true;
        this.textMessage.Name = "textMessage";
        this.textMessage.Size = new System.Drawing.Size(537, 178);
        this.textMessage.TabIndex = 0;
        //
        // butTransmit
        //
        this.butTransmit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butTransmit.Location = new System.Drawing.Point(386, 250);
        this.butTransmit.Name = "butTransmit";
        this.butTransmit.Size = new System.Drawing.Size(75, 23);
        this.butTransmit.TabIndex = 6;
        this.butTransmit.Text = "Transmit";
        this.butTransmit.UseVisualStyleBackColor = true;
        this.butTransmit.Click += new System.EventHandler(this.butTransmit_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(474, 250);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 8;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormHash
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(561, 281);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butTransmit);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butGererate);
        this.Controls.Add(this.textHash);
        this.Controls.Add(this.textMessage);
        this.Name = "FormHash";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Hash";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textHash = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butGererate = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMessage = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butTransmit = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
}


