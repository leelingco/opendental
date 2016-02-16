//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;

/**
* 
*/
public class ValidDouble  extends System.Windows.Forms.TextBox 
{
    /**
    * 
    */
    public Double MaxVal = 100000000;
    /**
    * 
    */
    public Double MinVal = -100000000;
    public ErrorProvider errorProvider1 = new ErrorProvider();
    /**
    * 
    */
    public ValidDouble() throws Exception {
        initializeComponent();
        errorProvider1.BlinkStyle = ErrorBlinkStyle.NeverBlink;
    }

    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ValidDouble
        //
        this.TextChanged += new System.EventHandler(this.ValidDouble_TextChanged);
        this.ResumeLayout(false);
    }

    /*private void ValidDouble_Validating(object sender, System.ComponentModel.CancelEventArgs e) {
    			string myMessage="";
    			try{
    				if(Text==""){
    					errorProvider1.SetError(this,"");
    					return;//Text="0";
    				}
    				if(System.Convert.ToDouble(this.Text)>MaxVal)
    					throw new Exception("Number must be less than "+(MaxVal+1).ToString());
    				if(System.Convert.ToDouble(this.Text)<MinVal)
    					throw new Exception("Number must be greater than or equal to "+(MinVal).ToString());
    				errorProvider1.SetError(this,"");
    			}
    			catch(Exception ex){
    				if(ex.Message=="Input string was not in a correct format."){
    					myMessage="Must be a number. No letters or symbols allowed";
    				}
    				else{
    					myMessage=ex.Message;
    				}	
    				errorProvider1.SetError(this,myMessage);
    			}			
    		}*/
    private void validDouble_TextChanged(Object sender, EventArgs e) throws Exception {
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


