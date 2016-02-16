//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;

/**
* this differs slightly from ValidNumber.  Use this when default is 0 instead of blank.
*/
public class ValidNum  extends System.Windows.Forms.TextBox 
{
    private System.ComponentModel.Container components = null;
    public ErrorProvider errorProvider1 = new ErrorProvider();
    /**
    * 
    */
    private int maxVal = 255;
    /**
    * 
    */
    private int minVal = 0;
    /**
    * 
    */
    public ValidNum() throws Exception {
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ValidNum
        //
        this.Validated += new System.EventHandler(this.ValidNum_Validated);
        this.Validating += new System.ComponentModel.CancelEventHandler(this.ValidNum_Validating);
        this.ResumeLayout(false);
    }

    /**
    * The minumum value that this number can be set to without generating an error.
    */
    public int getMinVal() throws Exception {
        return minVal;
    }

    public void setMinVal(int value) throws Exception {
        minVal = value;
    }

    /**
    * The maximum value that this number can be set to without generating an error.
    */
    public int getMaxVal() throws Exception {
        return maxVal;
    }

    public void setMaxVal(int value) throws Exception {
        maxVal = value;
    }

    private void validNum_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        String myMessage = "";
        try
        {
            if (StringSupport.equals(Text, ""))
            {
                Text = "0";
            }
             
            if (System.Convert.ToInt32(this.Text) > getMaxVal())
                throw new Exception("Number must be less than " + (getMaxVal() + 1).ToString());
             
            if (System.Convert.ToInt32(this.Text) < minVal)
                throw new Exception("Number must be greater than or equal to " + (minVal).ToString());
             
            errorProvider1.SetError(this, "");
        }
        catch (Exception ex)
        {
            if (StringSupport.equals(ex.Message, "Input string was not in a correct format."))
            {
                myMessage = "Must be a number. No letters or symbols allowed";
            }
            else
            {
                myMessage = ex.Message;
            } 
            this.errorProvider1.SetError(this, myMessage);
        }
    
    }

    private void validNum_Validated(Object sender, System.EventArgs e) throws Exception {
    }

}


//FormValid=true;