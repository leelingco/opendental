//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;

/*=========================================================================================
	=================================== class PIn ===========================================*/
/**
* Converts strings coming in from the database into the appropriate type. "P" was originally short for Parameter because this class was written specifically to replace parameters in the mysql queries. Using strings instead of parameters is much easier to debug.  This will later be rewritten as a System.IConvertible interface on custom mysql types.  I would rather not ever depend on the mysql connector for this so that this program remains very db independent.
*/
public class PIn   
{
    /**
    * 
    */
    public static boolean pBool(String myString) throws Exception {
        return StringSupport.equals(myString, "1");
    }

    /**
    * 
    */
    public static byte pByte(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            return System.Convert.ToByte(myString);
        } 
    }

    /**
    * 
    */
    public static DateTime pDate(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
            return DateTime.MinValue;
         
        try
        {
            return (DateTime.Parse(myString));
        }
        catch (Exception __dummyCatchVar0)
        {
            return DateTime.MinValue;
        }
    
    }

    /**
    * 
    */
    public static DateTime pDateT(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
            return DateTime.MinValue;
         
        try
        {
            return (DateTime.Parse(myString));
        }
        catch (Exception __dummyCatchVar1)
        {
            return DateTime.MinValue;
        }
    
    }

    //if(myString=="0000-00-00 00:00:00")//useless
    //	return DateTime.MinValue;
    /**
    * If blank or invalid, returns 0. Otherwise, parses.
    */
    public static double pDouble(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            try
            {
                return System.Convert.ToDouble(myString);
            }
            catch (Exception __dummyCatchVar2)
            {
                MessageBox.Show("Error converting " + myString + " to double");
                return 0;
            }
        
        } 
    }

    /**
    * 
    */
    public static int pInt(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            return System.Convert.ToInt32(myString);
        } 
    }

    /**
    * 
    */
    public static float pFloat(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
         
        return System.Convert.ToSingle(myString);
    }

    //try{
    //}
    //catch{
    //	return 0;
    //}
    /**
    * 
    */
    public static String pString(String myString) throws Exception {
        return myString;
    }

    //<summary></summary>
    //public static string PTime (string myTime){
    //	return DateTime.Parse(myTime).ToString("HH:mm:ss");
    //}
    //<summary></summary>
    //public static string PBytes (byte[] myBytes){
    //	return Convert.ToBase64String(myBytes);
    //}
    /**
    * 
    */
    public static Bitmap pBitmap(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return null;
        }
         
        byte[] rawData = Convert.FromBase64String(myString);
        MemoryStream stream = new MemoryStream(rawData);
        Bitmap image = new Bitmap(stream);
        return image;
    }

    /**
    * Saves the string representation of a sound into a .wav file.  The timing of this is different than with the other "P" functions, and is only used by the export button in FormSigElementDefEdit
    */
    public static void pSound(String sound, String filename) throws Exception {
        if (!filename.EndsWith(".wav"))
        {
            throw new ApplicationException("Filename must end with .wav");
        }
         
        byte[] rawData = Convert.FromBase64String(sound);
        FileStream stream = new FileStream(filename, FileMode.Create, FileAccess.Write);
        stream.Write(rawData, 0, rawData.Length);
    }

}


