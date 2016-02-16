//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;


/**
* Programming Utilites (Kinda like snipits)
*/
public class PU   
{
    /**
    * Throws an exception with message given.
    */
    public static void setEx(String value) throws Exception {
        throw new Exception(value);
    }

    //1.ToString();
    //1.ToString();
    /**
    * Message Box with "Message" as Caption
    */
    public static void setMB(String value) throws Exception {
        MessageBox.Show(value, "Message");
    }

    /**
    * Returns the Name of the Calling Method. Needs Tested.
    * If this works, I thought it is pretty cool.  Now you can tell where your
    * error comes from.  Gives me goose bumps. - Dan Krueger
    * 
    *  @return Returns the Name of the Calling Method
    */
    public static String getMethod() throws Exception {
        String rValue = "Method Name not Found";
        System.Diagnostics.StackTrace st = new System.Diagnostics.StackTrace(true);
        String s2 = st.ToString();
        String[] lines = s2.Split('\n');
        if (lines != null && lines.Length >= 1)
            rValue = lines[1];
         
        return rValue;
    }

}


//if (st.FrameCount  >= 1)
//{
//    System.Diagnostics.StackFrame sf = st.GetFrame(1);
//    rValue = sf.GetMethod().Name;
//    string s1 = st.ToString();
//}