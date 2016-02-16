//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;


public class TextBoxWiki  extends UserControl 
{

    /**
    * This gets set externally to prevent flicker while dragging text.
    */
    private boolean paintIsBlocked = new boolean();
    //these fields allow dragging text to a new position
    /**
    * Once this is set to true, it will stay true until mouse up.
    */
    private boolean MouseIsDownOnSelectedText = new boolean();
    private int SelectionStartMem = new int();
    private int SelectionLengthMem = new int();
    private Point MouseDownLocation = new Point();
    private Bitmap BitmapWhileDragging = new Bitmap();
    /**
    * 
    */
    public EventHandler TextChanged = null;
    public TextBoxWiki() throws Exception {
        initializeComponent();
    }

    /**
    * 
    */
    public ContextMenuStrip getContextMenuStrip() throws Exception {
        return textBoxMain.ContextMenuStrip;
    }

    public void setContextMenuStrip(ContextMenuStrip value) throws Exception {
        textBoxMain.ContextMenuStrip = value;
    }

    /**
    * 
    */
    public String getSelectedText() throws Exception {
        return textBoxMain.SelectedText;
    }

    public void setSelectedText(String value) throws Exception {
        textBoxMain.SelectedText = value;
    }

    /**
    * 
    */
    public int getSelectionLength() throws Exception {
        return textBoxMain.SelectionLength;
    }

    public void setSelectionLength(int value) throws Exception {
        textBoxMain.SelectionLength = value;
    }

    /**
    * 
    */
    public int getSelectionStart() throws Exception {
        return textBoxMain.SelectionStart;
    }

    public void setSelectionStart(int value) throws Exception {
        textBoxMain.SelectionStart = value;
    }

    /**
    * 
    */
    public boolean getReadOnly() throws Exception {
        return textBoxMain.ReadOnly;
    }

    public void setReadOnly(boolean value) throws Exception {
        textBoxMain.ReadOnly = value;
        textBoxMain.BackColor = SystemColors.Window;
    }

    protected void onPaint(PaintEventArgs pe) throws Exception {
        super.OnPaint(pe);
        if (paintIsBlocked)
        {
            pe.Graphics.DrawImage(BitmapWhileDragging, new Point(0, 0));
        }
         
    }

    /**
    * 
    */
    public String getText() throws Exception {
        return textBoxMain.Text;
    }

    public void setText(String value) throws Exception {
        textBoxMain.Text = value;
    }

    public Font getFont() throws Exception {
        return super.Font;
    }

    public void setFont(Font value) throws Exception {
        super.Font = value;
        textBoxMain.Font = value;
    }

    public void copy() throws Exception {
        textBoxMain.Copy();
    }

    public void cut() throws Exception {
        textBoxMain.Cut();
    }

    public int getCharIndexFromPosition(Point pt) throws Exception {
        return textBoxMain.GetCharIndexFromPosition(pt);
    }

    public void paste() throws Exception {
        textBoxMain.Paste();
    }

    public void paste(String text) throws Exception {
        textBoxMain.Paste(text);
    }

    public void undo() throws Exception {
        textBoxMain.Undo();
    }

    /**
    * This gets set externally to prevent flicker while dragging text.
    */
    private void blockPainting(boolean newVal) throws Exception {
        if (newVal)
        {
            BitmapWhileDragging = new Bitmap(textBoxMain.Width, textBoxMain.Height);
            Rectangle bounds = new Rectangle(new Point(0, 0), BitmapWhileDragging.Size);
            textBoxMain.DrawToBitmap(BitmapWhileDragging, bounds);
            paintIsBlocked = true;
            this.Invalidate();
            textBoxMain.Visible = false;
        }
        else
        {
            textBoxMain.Visible = true;
            paintIsBlocked = false;
            if (BitmapWhileDragging != null)
            {
                BitmapWhileDragging.Dispose();
                BitmapWhileDragging = null;
            }
             
        } 
    }

    //this.Invalidate();
    private void textBoxMain_DoubleClick(Object sender, EventArgs e) throws Exception {
        //the default behavior selects the word, but also selects some junk surrounding text.
        String str = textBoxMain.SelectedText;
        int charsRemovedEnd = 0;
        int charsRemovedStart = 0;
        while (true)
        {
            if (str.EndsWith(" ") || str.EndsWith(".") || str.EndsWith(",") || str.EndsWith("!") || str.EndsWith("]") || str.EndsWith("}") || str.EndsWith(">") || str.EndsWith("'") || str.EndsWith("\"") || str.EndsWith("|") || str.EndsWith("-"))
            {
                str = str.Substring(0, str.Length - 1);
                charsRemovedEnd++;
            }
            else if (str.StartsWith(" ") || str.StartsWith(".") || str.StartsWith(",") || str.StartsWith("!") || str.StartsWith("[") || str.StartsWith("{") || str.StartsWith("<") || str.StartsWith("/") || str.StartsWith("'") || str.StartsWith("\"") || str.StartsWith("|") || str.StartsWith("-"))
            {
                str = str.Substring(1, str.Length - 1);
                charsRemovedStart++;
            }
            else if (str.Contains("<"))
            {
                charsRemovedEnd += (str.Length - str.IndexOf("<"));
                str = str.Substring(0, str.IndexOf("<"));
            }
            else if (str.Contains(">"))
            {
                charsRemovedStart += (str.IndexOf(">") + 1);
                str = str.Substring(str.IndexOf(">") + 1);
            }
            else
            {
                break;
            }    
        }
        textBoxMain.SelectionStart += charsRemovedStart;
        textBoxMain.SelectionLength -= (charsRemovedStart + charsRemovedEnd);
    }

    private void textBoxMain_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        this.OnMouseDoubleClick(e);
    }

    private void textBoxMain_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        this.OnKeyPress(e);
    }

    private void textBoxMain_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        //the KeyPress event was useless because it wasn't even firing for the Delete key.
        KeyCode __dummyScrutVar0 = e.KeyCode;
        if (__dummyScrutVar0.equals(Keys.ControlKey) || __dummyScrutVar0.equals(Keys.ShiftKey))
        {
        }
        else //don't reset the SelectionLengthMem to 0 because the text has not be deselected yet.
        //typing a char such as backspace is a common way to move from selected text to a state where no text is selected.
        if (__dummyScrutVar0.equals(Keys.Tab))
        {
            textBoxMain.Paste("   ");
            SelectionLengthMem = 0;
            e.SuppressKeyPress = true;
        }
        else
        {
            SelectionLengthMem = 0;
        }  
    }

    private void textBoxMain_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if ((e.Button & MouseButtons.Left) != MouseButtons.Left)
        {
            return ;
        }
         
        //unfortunately, by the time this fires, the text is already deselected, but we remembered it
        if (SelectionLengthMem == 0)
        {
            return ;
        }
         
        //some text must have been selected already, and we need to decide whether we are going to try to drag the selected text.
        if (textBoxMain.GetCharIndexFromPosition(e.Location) < SelectionStartMem)
        {
            return ;
        }
         
        //clicked on a point before the selected text
        if (textBoxMain.GetCharIndexFromPosition(e.Location) > SelectionStartMem + SelectionLengthMem)
        {
            return ;
        }
         
        //clicked on a point after the selected text
        MouseDownLocation = e.Location;
        //reselect the text for visual cue
        textBoxMain.SelectionStart = SelectionStartMem;
        textBoxMain.SelectionLength = SelectionLengthMem;
        MouseIsDownOnSelectedText = true;
    }

    private void textBoxMain_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnSelectedText)
        {
            return ;
        }
         
        //the textbox has internal code for reselecting text as we move.  Need to override.
        textBoxMain.SelectionStart = SelectionStartMem;
        textBoxMain.SelectionLength = SelectionLengthMem;
        if (paintIsBlocked)
        {
        }
        else
        {
            //painting already blocked to prevent flicker
            //this is the first time the mouse has moved at all since the mouse down on selected text
            textBoxMain.Cursor = Cursors.Arrow;
            blockPainting(true);
        } 
    }

    //block painting to prevent flicker
    private void textBoxMain_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (MouseIsDownOnSelectedText)
        {
            int charIndexMouseUp = textBoxMain.GetCharIndexFromPosition(e.Location);
            if (Math.Abs(e.X - MouseDownLocation.X) < 4 && Math.Abs(e.Y - MouseDownLocation.Y) < 4)
            {
                //if mouse didn't move very much between down and up, then it's a click instead of a drag, so deselect the selected text
                textBoxMain.SelectionStart = charIndexMouseUp;
                textBoxMain.SelectionLength = 0;
            }
            else
            {
                //drag
                //remember the selected text
                String selectedText = textBoxMain.SelectedText;
                if (charIndexMouseUp < SelectionStartMem)
                {
                    //new position is before old position
                    //remove selected text from content
                    textBoxMain.Text = textBoxMain.Text.Substring(0, SelectionStartMem) + textBoxMain.Text.Substring(SelectionStartMem + SelectionLengthMem);
                    //add the text back in at the new spot
                    textBoxMain.Text = textBoxMain.Text.Substring(0, charIndexMouseUp) + selectedText + textBoxMain.Text.Substring(charIndexMouseUp);
                    //highlight the newly moved text for visual cue
                    textBoxMain.SelectionStart = charIndexMouseUp;
                    textBoxMain.SelectionLength = SelectionLengthMem;
                }
                else if (charIndexMouseUp < SelectionStartMem + SelectionLengthMem)
                {
                }
                else
                {
                    //new position is within the selected text
                    //do nothing
                    //new position is after old position
                    //add the text in at the new spot
                    textBoxMain.Text = textBoxMain.Text.Substring(0, charIndexMouseUp) + selectedText + textBoxMain.Text.Substring(charIndexMouseUp);
                    //then, remove it from the old spot
                    textBoxMain.Text = textBoxMain.Text.Substring(0, SelectionStartMem) + textBoxMain.Text.Substring(SelectionStartMem + SelectionLengthMem);
                    //highlight the newly moved text for visual cue
                    textBoxMain.SelectionStart = charIndexMouseUp - SelectionLengthMem;
                    textBoxMain.SelectionLength = SelectionLengthMem;
                }  
            } 
            //whether it was a click or drag, reset all our variables
            blockPainting(false);
            MouseDownLocation = new Point();
            SelectionStartMem = textBoxMain.SelectionStart;
            //these two actually are important in case we want to drag the text again
            SelectionLengthMem = textBoxMain.SelectionLength;
            MouseIsDownOnSelectedText = false;
            textBoxMain.Cursor = Cursors.IBeam;
        }
        else
        {
            //this will happen after text is initially highlighted
            SelectionStartMem = textBoxMain.SelectionStart;
            SelectionLengthMem = textBoxMain.SelectionLength;
        } 
    }

    private void textBoxMain_TextChanged(Object sender, EventArgs e) throws Exception {
        if (TextChanged != null)
        {
            TextChanged(this, new EventArgs());
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
        this.textBoxMain = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // textBoxMain
        //
        this.textBoxMain.AcceptsReturn = true;
        this.textBoxMain.AcceptsTab = true;
        this.textBoxMain.Dock = System.Windows.Forms.DockStyle.Fill;
        this.textBoxMain.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textBoxMain.HideSelection = false;
        this.textBoxMain.Location = new System.Drawing.Point(0, 0);
        this.textBoxMain.MaxLength = 1000000;
        this.textBoxMain.Multiline = true;
        this.textBoxMain.Name = "textBoxMain";
        this.textBoxMain.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textBoxMain.Size = new System.Drawing.Size(150, 150);
        this.textBoxMain.TabIndex = 0;
        this.textBoxMain.Text = "this is a test";
        this.textBoxMain.TextChanged += new System.EventHandler(this.textBoxMain_TextChanged);
        this.textBoxMain.DoubleClick += new System.EventHandler(this.textBoxMain_DoubleClick);
        this.textBoxMain.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxMain_KeyDown);
        this.textBoxMain.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textBoxMain_KeyPress);
        this.textBoxMain.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.textBoxMain_MouseDoubleClick);
        this.textBoxMain.MouseDown += new System.Windows.Forms.MouseEventHandler(this.textBoxMain_MouseDown);
        this.textBoxMain.MouseMove += new System.Windows.Forms.MouseEventHandler(this.textBoxMain_MouseMove);
        this.textBoxMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.textBoxMain_MouseUp);
        //
        // TextBoxWiki
        //
        this.Controls.Add(this.textBoxMain);
        this.DoubleBuffered = true;
        this.Name = "TextBoxWiki";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textBoxMain = new System.Windows.Forms.TextBox();
}


