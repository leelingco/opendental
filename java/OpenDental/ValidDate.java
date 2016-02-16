//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;

/**
* 
*/
public class ValidDate  extends System.Windows.Forms.TextBox 
{
    public ErrorProvider errorProvider1 = new ErrorProvider();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public ValidDate() throws Exception {
        initializeComponent();
        errorProvider1.BlinkStyle = ErrorBlinkStyle.NeverBlink;
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ValidDate
        //
        this.Validated += new System.EventHandler(this.ValidDate_Validated);
        this.Validating += new System.ComponentModel.CancelEventHandler(this.ValidDate_Validating);
        this.TextChanged += new System.EventHandler(this.ValidDate_TextChanged);
        this.ResumeLayout(false);
    }

    private void validDate_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        String myMessage = "";
        try
        {
            if (StringSupport.equals(Text, ""))
            {
                errorProvider1.SetError(this, "");
                return ;
            }
             
            boolean allNums = true;
            for (int i = 0;i < Text.Length;i++)
            {
                if (!Char.IsNumber(Text, i))
                {
                    allNums = false;
                }
                 
            }
            if (StringSupport.equals(CultureInfo.CurrentCulture.TwoLetterISOLanguageName, "en"))
            {
                if (allNums)
                {
                    if (Text.Length == 6)
                    {
                        Text = Text.Substring(0, 2) + "/" + Text.Substring(2, 2) + "/" + Text.Substring(4, 2);
                    }
                    else if (Text.Length == 8)
                    {
                        Text = Text.Substring(0, 2) + "/" + Text.Substring(2, 2) + "/" + Text.Substring(4, 4);
                    }
                      
                }
                 
            }
             
            if (DateTime.Parse(Text).Year < 1880 || DateTime.Parse(Text).Year > 2100)
            {
                throw new Exception("Valid dates between 1880 and 2100");
            }
            else
            {
                Text = DateTime.Parse(this.Text).ToString("d");
            } 
            //will throw exception if invalid
            errorProvider1.SetError(this, "");
        }
        catch (Exception ex)
        {
            //Cancel the event and select the text to be corrected by the user
            if (StringSupport.equals(ex.Message, "String was not recognized as a valid DateTime."))
            {
                myMessage = "Invalid date";
            }
            else
            {
                myMessage = ex.Message;
            } 
            //this.Select(0,this.Text.Length);
            errorProvider1.SetError(this, Lan.g("ValidDate",myMessage));
        }
    
    }

    private void validDate_Validated(Object sender, System.EventArgs e) throws Exception {
    }

    private void validDate_TextChanged(Object sender, System.EventArgs e) throws Exception {
    }

    /*	if(Text.Length==2 && Char.IsNumber(Text,0) && Char.IsNumber(Text,1)){
    					Text+="/";
    					this.SelectionStart=Text.Length+1;
    				}
    				if(Text.Length==5 && Char.IsNumber(Text,3) && Char.IsNumber(Text,4)){
    					Text+="/";
    					this.SelectionStart=Text.Length+1;
    				}*/
    /**
    * 
    */
    protected void onKeyPress(KeyPressEventArgs e) throws Exception {
        super.OnKeyPress(e);
        //if(CultureInfo.CurrentCulture.Name=="fr-CA" || CultureInfo.CurrentCulture.Name=="en-CA") {
        //	return;//because they use - in their regular dates which interferes with this feature.
        //}
        if (e.KeyChar != '+' && e.KeyChar != '-')
        {
            return ;
        }
         
        //base.OnKeyPress (e);
        //The user might not be done typing in the date.  Make sure that there are at least two non-numeric characters before subtracting days.
        Regex regEx = new Regex("[^0-9]");
        if (regEx.Matches(Text).Count < 2)
        {
            return ;
        }
         
        //Not a complete date yet.
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        //base.OnKeyPress (e);
        int caret = SelectionStart;
        if (e.KeyChar == '+')
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyChar == '-')
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        Text = dateDisplayed.ToShortDateString();
        SelectionStart = caret;
        e.Handled = true;
    }

    /**
    * 
    */
    protected void onKeyDown(KeyEventArgs e) throws Exception {
        super.OnKeyDown(e);
        if (e.KeyCode != Keys.Up && e.KeyCode != Keys.Down)
        {
            return ;
        }
         
        //base.OnKeyDown (e);
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            return ;
        }

        //base.OnKeyDown (e);
        int caret = SelectionStart;
        if (e.KeyCode == Keys.Up)
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyCode == Keys.Down)
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        Text = dateDisplayed.ToShortDateString();
        SelectionStart = caret;
        e.Handled = true;
    }

}


