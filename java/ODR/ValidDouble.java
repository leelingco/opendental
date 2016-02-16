//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package ODR;

import CS2JNet.System.StringSupport;

/**
* 
*/
public class ValidDouble  extends System.Windows.Forms.TextBox 
{
    public ErrorProvider errorProvider1 = new ErrorProvider();
    /**
    * 
    */
    public Double MaxVal = 100000000;
    /**
    * 
    */
    public Double MinVal = -100000000;
    /**
    * 
    */
    public ValidDouble() throws Exception {
        initializeComponent();
    }

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ValidDouble
        //
        this.Validating += new System.ComponentModel.CancelEventHandler(this.ValidDouble_Validating);
        this.ResumeLayout(false);
    }

    private void validDouble_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        String myMessage = "";
        try
        {
            if (StringSupport.equals(Text, ""))
            {
                errorProvider1.SetError(this, "");
                return ;
            }
             
            //Text="0";
            if (System.Convert.ToDouble(this.Text) > MaxVal)
                throw new Exception("Number must be less than " + (MaxVal + 1).ToString());
             
            if (System.Convert.ToDouble(this.Text) < MinVal)
                throw new Exception("Number must be greater than or equal to " + (MinVal).ToString());
             
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
            errorProvider1.SetError(this, myMessage);
        }
    
    }

}


