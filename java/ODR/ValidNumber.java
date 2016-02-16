//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package ODR;

import CS2JNet.System.StringSupport;

/**
* This differs slightly from ValidNum. Use this to allow a blank entry instead of defaulting to 0.
*/
public class ValidNumber  extends System.Windows.Forms.TextBox 
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
    public ValidNumber() throws Exception {
        initializeComponent();
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
        // ValidNumber
        //
        this.Validating += new System.ComponentModel.CancelEventHandler(this.ValidNumber_Validating);
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    public int getMaxVal() throws Exception {
        return maxVal;
    }

    public void setMaxVal(int value) throws Exception {
        maxVal = value;
    }

    /**
    * 
    */
    public int getMinVal() throws Exception {
        return minVal;
    }

    public void setMinVal(int value) throws Exception {
        minVal = value;
    }

    private void validNumber_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        String myMessage = "";
        if (StringSupport.equals(Text, ""))
        {
            errorProvider1.SetError(this, myMessage);
            return ;
        }
         
        try
        {
            //sets no error message. (empty is OK)
            if (System.Convert.ToInt32(this.Text) > getMaxVal())
                throw new Exception("Number must be less than " + (getMaxVal() + 1).ToString());
             
            if (System.Convert.ToInt32(this.Text) < getMinVal())
                throw new Exception("Number must be greater than or equal to " + (getMinVal()).ToString());
             
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
        }

        errorProvider1.SetError(this, myMessage);
    }

}


