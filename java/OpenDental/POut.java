//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;


/**
* Converts various datatypes into strings formatted correctly for MySQL. "P" was originally short for Parameter because this class was written specifically to replace parameters in the mysql queries. Using strings instead of parameters is much easier to debug.  This will later be rewritten as a System.IConvertible interface on custom mysql types.  I would rather not ever depend on the mysql connector for this so that this program remains very db independent.
*/
public class POut   
{
    /**
    * 
    */
    public static String pBool(boolean myBool) throws Exception {
        if (myBool == true)
        {
            return "1";
        }
        else
        {
            return "0";
        } 
    }

    /**
    * 
    */
    public static String pByte(byte myByte) throws Exception {
        return myByte.ToString();
    }

    /**
    * 
    */
    public static String pDateT(DateTime myDateT) throws Exception {
        try
        {
            return myDateT.ToString("yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture);
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }
    
    }

    //new DateTimeFormatInfo());
    //this actually saves zero's to the database
    /**
    * Converts a date to yyyy-MM-dd format which is the format required by MySQL.
    */
    public static String pDate(DateTime myDate) throws Exception {
        try
        {
            return myDate.ToString("yyyy-MM-dd", new DateTimeFormatInfo());
        }
        catch (Exception __dummyCatchVar1)
        {
            return "";
        }
    
    }

    //the new DTFormatInfo is to prevent changes in year for Korea
    //return "0000-00-00";
    //this saves zeros to the database
    /**
    * 
    */
    public static String pDouble(double myDouble) throws Exception {
        try
        {
            return myDouble.ToString("f", new NumberFormatInfo());
        }
        catch (Exception __dummyCatchVar2)
        {
            return "0";
        }
    
    }

    //because decimal is a comma in Europe, this sends it to db with period instead
    /**
    * 
    */
    public static String pInt(int myInt) throws Exception {
        return myInt.ToString();
    }

    /**
    * 
    */
    public static String pFloat(float myFloat) throws Exception {
        return myFloat.ToString();
    }

    /**
    * 
    */
    public static String pString(String myString) throws Exception {
        if (myString == null)
        {
            return "";
        }
         
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0;i < myString.Length;i++)
        {
            System.String.APPLY __dummyScrutVar0 = myString.Substring(i, 1);
            //note. When using binary data, must escape ',",\, and nul(? haven't done nul)
            if (__dummyScrutVar0.equals("'"))
            {
                strBuild.Append("\\\'");
            }
            else // ' replaced by \'
            if (__dummyScrutVar0.equals("\""))
            {
                strBuild.Append("\\\"");
            }
            else // " replaced by \"
            if (__dummyScrutVar0.equals("\\"))
            {
                strBuild.Append("\\\\");
            }
            else //single \ replaced by \\
            if (__dummyScrutVar0.equals("\r"))
            {
                strBuild.Append("\\r");
            }
            else //carriage return(usually followed by new line)
            if (__dummyScrutVar0.equals("\n"))
            {
                strBuild.Append("\\n");
            }
            else //new line
            if (__dummyScrutVar0.equals("\t"))
            {
                strBuild.Append("\\t");
            }
            else
            {
                //tab
                strBuild.Append(myString.Substring(i, 1));
            }      
        }
        return strBuild.ToString();
    }

    //The old slow way of doing it:
    /*string newString="";
    			for(int i=0;i<myString.Length;i++){
    				switch (myString.Substring(i,1)){
    					case "'": newString+=@"\'"; break;
    					case @"\": newString+=@"\\"; break;//single \ replaced by \\
    					case "\r": newString+=@"\r"; break;//carriage return(usually followed by new line)
    					case "\n": newString+=@"\n"; break;//new line
    					case "\t": newString+=@"\t"; break;//tab
    						//case "%": newString+="\\%"; break;//causes errors because only ambiguous in LIKE clause
    						//case "_": newString+="\\_"; break;//see above
    					default : newString+=myString.Substring(i,1); break;
    				}//end switch
    			}//end for*/
    //MessageBox.Show(strBuild.ToString());
    //<summary></summary>
    //public static string PTimee (string myTime){
    //	return DateTime.Parse(myTime).ToString("HH:mm:ss");
    //}
    /**
    * 
    */
    public static String pBitmap(Bitmap bitmap) throws Exception {
        if (bitmap == null)
        {
            return "";
        }
         
        MemoryStream stream = new MemoryStream();
        bitmap.Save(stream, ImageFormat.Bmp);
        byte[] rawData = stream.ToArray();
        return Convert.ToBase64String(rawData);
    }

    /**
    * Converts the specified wav file into a string representation.  The timing of this is a little different than with the other "P" functions and is only used by the import button in FormSigElementDefEdit.  After that, the wav spends the rest of it's life as a string until "played" or exported.
    */
    public static String pSound(String filename) throws Exception {
        if (!File.Exists(filename))
        {
            throw new ApplicationException("File does not exist.");
        }
         
        if (!filename.EndsWith(".wav"))
        {
            throw new ApplicationException("Filename must end with .wav");
        }
         
        FileStream stream = new FileStream(filename, FileMode.Open, FileAccess.Read, FileShare.ReadWrite);
        byte[] rawData = new byte[stream.Length];
        stream.Read(rawData, 0, (int)stream.Length);
        return Convert.ToBase64String(rawData);
    }

}


