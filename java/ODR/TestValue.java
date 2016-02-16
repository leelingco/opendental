//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;


/**
* 
*/
public class TestValue   
{
    /**
    * Used to test the sign on debits and credits for the five different account types.  Pass in a number in string format.  Like "2", for example.
    */
    public static boolean accountDebitIsPos(String accountType) throws Exception {
        System.String __dummyScrutVar0 = accountType;
        //asset
        if (__dummyScrutVar0.equals("0") || __dummyScrutVar0.equals("4"))
        {
            return true;
        }
        else //expense
        //liability
        //equity //because liabilities and equity are treated the same
        if (__dummyScrutVar0.equals("1") || __dummyScrutVar0.equals("2") || __dummyScrutVar0.equals("3"))
        {
            return false;
        }
          
        return true;
    }

}


//revenue
//will never happen