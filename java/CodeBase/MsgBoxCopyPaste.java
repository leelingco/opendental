//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;

/**
* Summary description for FormBasicTemplate.
*/
public class MsgBoxCopyPaste  extends System.Windows.Forms.Form 
{
    private Button butOK = new Button();
    public TextBox textMain = new TextBox();
    private Button butPrint = new Button();
    //this way, the text will be available on close.
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private Button butCopyAll = new Button();
    private int pagesPrinted = new int();
    /**
    * This presents a message box to the user, but is better because it allows us to copy the text and paste it into another program for testing.  Especially useful for queries.
    */
    public MsgBoxCopyPaste(String displayText) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        //Lan.F(this);
        textMain.Text = displayText;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(MsgBoxCopyPaste.class);
        this.butOK = new System.Windows.Forms.Button();
        this.textMain = new System.Windows.Forms.TextBox();
        this.butPrint = new System.Windows.Forms.Button();
        this.butCopyAll = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(615, 606);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textMain
        //
        this.textMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textMain.BackColor = System.Drawing.SystemColors.Window;
        this.textMain.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textMain.Location = new System.Drawing.Point(12, 12);
        this.textMain.Multiline = true;
        this.textMain.Name = "textMain";
        this.textMain.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textMain.Size = new System.Drawing.Size(678, 588);
        this.textMain.TabIndex = 2;
        //
        // butPrint
        //
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.Image = ((System.Drawing.Image)(resources.GetObject("butPrint.Image")));
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(530, 606);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 26);
        this.butPrint.TabIndex = 3;
        this.butPrint.Text = "    &Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butCopyAll
        //
        this.butCopyAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCopyAll.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCopyAll.Location = new System.Drawing.Point(126, 606);
        this.butCopyAll.Name = "butCopyAll";
        this.butCopyAll.Size = new System.Drawing.Size(79, 26);
        this.butCopyAll.TabIndex = 4;
        this.butCopyAll.Text = "Copy All";
        this.butCopyAll.Click += new System.EventHandler(this.butCopyAll_Click);
        //
        // MsgBoxCopyPaste
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(702, 644);
        this.Controls.Add(this.butCopyAll);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimizeBox = false;
        this.Name = "MsgBoxCopyPaste";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Load += new System.EventHandler(this.MsgBoxCopyPaste_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void msgBoxCopyPaste_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(50, 50, 50, 50);
        //Half-inch all around.
        //This prevents a bug caused by some printer drivers not reporting their papersize.
        //But remember that other countries use A4 paper instead of 8 1/2 x 11.
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        pd.PrinterSettings.Duplex = Duplex.Horizontal;
        //Print double sided when possible.
        pagesPrinted = 0;
        pd.Print();
    }

    //No print previews, since this form is in and of itself a print preview.
    /**
    * Called for each page to be printed.
    */
    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        e.HasMorePages = !Print(e.Graphics, pagesPrinted++, e.MarginBounds);
    }

    /**
    * Prints one page. Returns true if pageToPrint is the last page in this print job.
    */
    private boolean print(Graphics g, int pageToPrint, Rectangle margins) throws Exception {
        //Messages may span multiple pages. We print the header on each page as well as the page number.
        float baseY = margins.Top;
        String text = "Page " + (pageToPrint + 1);
        Font font = Font;
        SizeF textSize = g.MeasureString(text, font);
        g.DrawString(text, font, Brushes.Black, margins.Right - textSize.Width, baseY);
        baseY += textSize.Height;
        text = Text;
        font = new Font(Font.FontFamily, 16, FontStyle.Bold);
        textSize = g.MeasureString(text, font);
        g.DrawString(text, font, Brushes.Black, (margins.Width - textSize.Width) / 2, baseY);
        baseY += textSize.Height;
        font.Dispose();
        font = new Font(Font.FontFamily, 14, FontStyle.Bold);
        text = DateTime.Now.ToString();
        textSize = g.MeasureString(text, font);
        g.DrawString(text, font, Brushes.Black, (margins.Width - textSize.Width) / 2, baseY);
        baseY += textSize.Height;
        font.Dispose();
        String[] messageLines = textMain.Text.Split(new String[]{ Environment.NewLine }, StringSplitOptions.None);
        font = Font;
        boolean isLastPage = false;
        float y = 0;
        for (int curPage = 0, msgLine = 0;curPage <= pageToPrint;curPage++)
        {
            //Set y to its initial value for the current page (right after the header).
            y = curPage * (margins.Bottom - baseY);
            while (msgLine < messageLines.Length)
            {
                //If a line is blank, we need to make sure that is counts for some vertical space.
                if (StringSupport.equals(messageLines[msgLine], ""))
                {
                    textSize = g.MeasureString("A", font);
                }
                else
                {
                    textSize = g.MeasureString(messageLines[msgLine], font);
                } 
                //Would the current text line go past the bottom margin?
                if (y + textSize.Height > (curPage + 1) * (margins.Bottom - baseY))
                {
                    break;
                }
                 
                if (curPage == pageToPrint)
                {
                    g.DrawString(messageLines[msgLine], font, Brushes.Black, margins.Left, baseY + y - curPage * (margins.Bottom - baseY));
                    if (msgLine == messageLines.Length - 1)
                    {
                        isLastPage = true;
                    }
                     
                }
                 
                y += textSize.Height;
                msgLine++;
            }
        }
        return isLastPage;
    }

    private void butCopyAll_Click(Object sender, EventArgs e) throws Exception {
        Clipboard.SetData("Text", textMain.Text);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
        Close();
    }

}


//have to have this also because sometimes this box is non-modal.