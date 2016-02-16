//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDentBusiness.EmailMessages;

public class FormEhrEncryption  extends Form 
{

    private byte[] key = new byte[]();
    public FormEhrEncryption() throws Exception {
        initializeComponent();
    }

    private void formEncryption_Load(Object sender, EventArgs e) throws Exception {
        UTF8Encoding enc = new UTF8Encoding();
        key = enc.GetBytes("AKQjlLUjlcABVbqp");
    }

    //Random string will be key
    private void butEncrypt_Click(Object sender, EventArgs e) throws Exception {
        if (textInput.Text.Trim() == String.Empty)
        {
            MessageBox.Show("No text to encrypt.");
            return ;
        }
         
        textResult.Text = Encryption(textInput.Text);
    }

    private void butDecrypt_Click(Object sender, EventArgs e) throws Exception {
        if (textInput.Text.Trim() == String.Empty)
        {
            MessageBox.Show("No text to decrypt.");
            return ;
        }
         
        textResult.Text = decryption();
    }

    /**
    * Encrypts signature text and returns a base 64 string so that it can go directly into the database.
    */
    public String encryption(String encrypt) throws Exception {
        byte[] ecryptBytes = Encoding.UTF8.GetBytes(encrypt);
        MemoryStream ms = new MemoryStream();
        CryptoStream cs = null;
        Aes aes = new AesManaged();
        aes.Key = key;
        aes.IV = new byte[16];
        ICryptoTransform encryptor = aes.CreateEncryptor(aes.Key, aes.IV);
        cs = new CryptoStream(ms, encryptor, CryptoStreamMode.Write);
        cs.Write(ecryptBytes, 0, ecryptBytes.Length);
        cs.FlushFinalBlock();
        byte[] encryptedBytes = new byte[ms.Length];
        ms.Position = 0;
        ms.Read(encryptedBytes, 0, (int)ms.Length);
        cs.Dispose();
        ms.Dispose();
        if (aes != null)
        {
            aes.Clear();
        }
         
        return Convert.ToBase64String(encryptedBytes);
    }

    public String decryption() throws Exception {
        try
        {
            byte[] encrypted = Convert.FromBase64String(textInput.Text);
            MemoryStream ms = null;
            CryptoStream cs = null;
            StreamReader sr = null;
            Aes aes = new AesManaged();
            aes.Key = key;
            aes.IV = new byte[16];
            ICryptoTransform decryptor = aes.CreateDecryptor(aes.Key, aes.IV);
            ms = new MemoryStream(encrypted);
            cs = new CryptoStream(ms, decryptor, CryptoStreamMode.Read);
            sr = new StreamReader(cs);
            String decrypted = sr.ReadToEnd();
            ms.Dispose();
            cs.Dispose();
            sr.Dispose();
            if (aes != null)
            {
                aes.Clear();
            }
             
            return decrypted;
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Text entered was not valid encrypted text.");
            return "";
        }
    
    }

    /**
    * 
    */
    private String generateHash(String message) throws Exception {
        byte[] data = Encoding.ASCII.GetBytes(message);
        HashAlgorithm algorithm = SHA1.Create();
        byte[] hashbytes = algorithm.ComputeHash(data);
        byte digit1 = new byte();
        byte digit2 = new byte();
        String char1 = new String();
        String char2 = new String();
        StringBuilder strHash = new StringBuilder();
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
            strHash.Append(char1);
            strHash.Append(char2);
        }
        return strHash.ToString();
    }

    /**
    * The only valid input is a value between 0 and 15.  Text returned will be 1-9 or a-f.
    */
    private String byteToStr(Byte byteVal) throws Exception {
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

    private void butTransmit_Click(Object sender, EventArgs e) throws Exception {
        if (textInput.Text.Trim() == String.Empty)
        {
            MessageBox.Show("No input text to send.");
            return ;
        }
         
        //Encrypt the message.
        String encryptedMessage = Encryption(textInput.Text);
        //Generate a hash for the message.
        String hashedMessage = GenerateHash(textInput.Text);
        //Encrypt the hash.
        //string encryptedHash=Encryption(hashedMessage);
        String attachContents = "Encrypted message:\r\n" + encryptedMessage + "\r\n\r\nHash:\r\n" + hashedMessage;
        //Send the encrypted message followed by encrypted hash.
        Cursor = Cursors.WaitCursor;
        try
        {
            EmailMessages.sendTestUnsecure("Encryption","encryption.txt",attachContents);
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

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.butEncrypt = new System.Windows.Forms.Button();
        this.butDecrypt = new System.Windows.Forms.Button();
        this.textInput = new System.Windows.Forms.TextBox();
        this.textResult = new System.Windows.Forms.TextBox();
        this.labelInput = new System.Windows.Forms.Label();
        this.labelOutput = new System.Windows.Forms.Label();
        this.butClose = new System.Windows.Forms.Button();
        this.butTransmit = new System.Windows.Forms.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // butEncrypt
        //
        this.butEncrypt.Location = new System.Drawing.Point(296, 98);
        this.butEncrypt.Name = "butEncrypt";
        this.butEncrypt.Size = new System.Drawing.Size(81, 24);
        this.butEncrypt.TabIndex = 1;
        this.butEncrypt.Text = "Encrypt >>";
        this.butEncrypt.UseVisualStyleBackColor = true;
        this.butEncrypt.Click += new System.EventHandler(this.butEncrypt_Click);
        //
        // butDecrypt
        //
        this.butDecrypt.Location = new System.Drawing.Point(296, 160);
        this.butDecrypt.Name = "butDecrypt";
        this.butDecrypt.Size = new System.Drawing.Size(81, 24);
        this.butDecrypt.TabIndex = 2;
        this.butDecrypt.Text = "Decrypt >>";
        this.butDecrypt.UseVisualStyleBackColor = true;
        this.butDecrypt.Click += new System.EventHandler(this.butDecrypt_Click);
        //
        // textInput
        //
        this.textInput.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.textInput.Location = new System.Drawing.Point(12, 35);
        this.textInput.Multiline = true;
        this.textInput.Name = "textInput";
        this.textInput.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textInput.Size = new System.Drawing.Size(269, 234);
        this.textInput.TabIndex = 3;
        //
        // textResult
        //
        this.textResult.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.textResult.Location = new System.Drawing.Point(389, 35);
        this.textResult.Multiline = true;
        this.textResult.Name = "textResult";
        this.textResult.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textResult.Size = new System.Drawing.Size(270, 234);
        this.textResult.TabIndex = 4;
        //
        // labelInput
        //
        this.labelInput.Location = new System.Drawing.Point(12, 15);
        this.labelInput.Name = "labelInput";
        this.labelInput.Size = new System.Drawing.Size(233, 17);
        this.labelInput.TabIndex = 5;
        this.labelInput.Text = "Enter text to encrypt or decrypt";
        this.labelInput.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelOutput
        //
        this.labelOutput.Location = new System.Drawing.Point(389, 15);
        this.labelOutput.Name = "labelOutput";
        this.labelOutput.Size = new System.Drawing.Size(124, 17);
        this.labelOutput.TabIndex = 6;
        this.labelOutput.Text = "Result";
        this.labelOutput.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(586, 278);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(73, 24);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butTransmit
        //
        this.butTransmit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butTransmit.Location = new System.Drawing.Point(484, 278);
        this.butTransmit.Name = "butTransmit";
        this.butTransmit.Size = new System.Drawing.Size(85, 24);
        this.butTransmit.TabIndex = 8;
        this.butTransmit.Text = "Transmit";
        this.butTransmit.UseVisualStyleBackColor = true;
        this.butTransmit.Click += new System.EventHandler(this.butTransmit_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(283, 134);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(104, 17);
        this.label2.TabIndex = 9;
        this.label2.Text = "( uses AES-128 )";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // FormEncryption
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(680, 319);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butTransmit);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.labelOutput);
        this.Controls.Add(this.labelInput);
        this.Controls.Add(this.textResult);
        this.Controls.Add(this.textInput);
        this.Controls.Add(this.butDecrypt);
        this.Controls.Add(this.butEncrypt);
        this.Name = "FormEncryption";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Encryption";
        this.Load += new System.EventHandler(this.FormEncryption_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button butEncrypt = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDecrypt = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textInput = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textResult = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelInput = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelOutput = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butTransmit = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


