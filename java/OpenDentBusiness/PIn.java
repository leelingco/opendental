//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PIn;

//using System.Drawing.Imaging;
//using System.Windows.Forms;
/*=========================================================================================
	=================================== class PIn ===========================================*/
/**
* "P" stands for Parameter.  Converts strings coming in from user input into the appropriate type.
*/
public class PIn   
{
    /**
    * 
    */
    public static Bitmap bitmap(String myString) throws Exception {
        if (myString == null || myString.Length < 0x32)
        {
            return null;
        }
         
        try
        {
            //Bitmaps require a minimum length for header info.
            byte[] rawData = Convert.FromBase64String(myString);
            MemoryStream stream = new MemoryStream(rawData);
            System.Drawing.Bitmap image = new System.Drawing.Bitmap(stream);
            return image;
        }
        catch (Exception __dummyCatchVar0)
        {
            return null;
        }
    
    }

    /**
    * 
    */
    public static boolean bool(String myString) throws Exception {
        if (StringSupport.equals(myString, "") || StringSupport.equals(myString, "0"))
        {
            return false;
        }
         
        return true;
    }

    //js enhanced this to allow larger numbers to indicate bool true.  Specifically TaskUnreads
    //return myString=="1";
    /**
    * 
    */
    public static byte byte(String myString) throws Exception {
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
    * Some versions of MySQL return a GROUP_CONCAT as a string, and other versions return it as a byte array.  This method handles either way, making it work smoothly with different versions.
    */
    public static String byteArray(Object obj) throws Exception {
        if (obj.GetType() == Byte[].class)
        {
            Byte[] bytes = (Byte[])obj;
            StringBuilder strbuild = new StringBuilder();
            for (int i = 0;i < bytes.Length;i++)
            {
                strbuild.Append((char)bytes[i]);
            }
            return strbuild.ToString();
        }
        else
        {
            return obj.ToString();
        } 
    }

    //string
    /**
    * Processes dates incoming from db that look like "4/29/2013", and dates from textboxes where users entered and which have usually been validated.
    */
    public static DateTime date(String myString) throws Exception {
        if (StringSupport.equals(myString, "") || myString == null)
        {
            return DateTime.MinValue;
        }
         
        try
        {
            return (DateTime.Parse(myString));
        }
        catch (Exception __dummyCatchVar1)
        {
            return DateTime.MinValue;
        }
    
    }

    //DateTimeKind.Unspecified, which prevents -7:00, for example, from being tacked onto the end during serialization.
    //return DateTime.Parse(myString,CultureInfo.InvariantCulture);
    /**
    * 
    */
    public static DateTime dateT(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
            return DateTime.MinValue;
         
        try
        {
            return (DateTime.Parse(myString));
        }
        catch (Exception __dummyCatchVar2)
        {
            return DateTime.MinValue;
        }
    
    }

    //if(myString=="0000-00-00 00:00:00")//useless
    //	return DateTime.MinValue;
    /**
    * If blank or invalid, returns 0. Otherwise, parses.
    */
    public static double decimal(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            try
            {
                return System.Convert.ToDecimal(myString);
            }
            catch (Exception __dummyCatchVar3)
            {
                return 0;
            }
        
        } 
    }

    //MessageBox.Show("Error converting "+myString+" to decimal");
    /**
    * If blank or invalid, returns 0. Otherwise, parses.
    */
    public static double double(String myString) throws Exception {
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
            catch (Exception __dummyCatchVar4)
            {
                return 0;
            }
        
        } 
    }

    //In Europe, comes in as a comma, parsed according to culture.
    //MessageBox.Show("Error converting "+myString+" to double");
    /**
    * 
    */
    public static int int(String myString) throws Exception {
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
    public static float float(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
         
        try
        {
            return System.Convert.ToSingle(myString);
        }
        catch (Exception __dummyCatchVar5)
        {
            return System.Convert.ToSingle(myString, CultureInfo.InvariantCulture);
        }
    
    }

    //because this will fail when getting the mysql version on startup, which always comes back with a period.
    /**
    * 
    */
    public static long long(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            return System.Convert.ToInt64(myString);
        } 
    }

    /**
    * 
    */
    public static short short(String myString) throws Exception {
        if (StringSupport.equals(myString, ""))
        {
            return 0;
        }
        else
        {
            return System.Convert.ToInt16(myString);
        } 
    }

    /**
    * Saves the string representation of a sound into a .wav file.  The timing of this is different than with the other "P" functions, and is only used by the export button in FormSigElementDefEdit
    */
    public static void sound(String sound, String filename) throws Exception {
        if (!filename.EndsWith(".wav"))
        {
            throw new ApplicationException("Filename must end with .wav");
        }
         
        byte[] rawData = Convert.FromBase64String(sound);
        FileStream stream = new FileStream(filename, FileMode.Create, FileAccess.Write);
        stream.Write(rawData, 0, rawData.Length);
        stream.Close();
    }

    /**
    * Currently does nothing.
    */
    public static String string(String myString) throws Exception {
        return myString;
    }

    /**
    * Timespans that might be invalid time of day.  Can be + or - and can be up to 800+ hours.  Stored in Oracle as varchar2.
    */
    public static TimeSpan tSpan(String myString) throws Exception {
        if (String.IsNullOrEmpty(myString))
        {
            return System.TimeSpan.Zero;
        }
         
        try
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                //return System.TimeSpan.Parse(myString); //Does not work. Confuses hours with days and an exception is thrown in our large timespan test.
                boolean isNegative = false;
                if (myString.StartsWith("-"))
                {
                    isNegative = true;
                    myString = myString.Substring(1);
                }
                 
                //remove the '-'
                String[] timeValues = myString.Split(new char[]{ ':' });
                if (timeValues.Length != 3)
                {
                    return System.TimeSpan.Zero;
                }
                 
                TimeSpan retval = new TimeSpan(PIn.Int(timeValues[0]), PIn.Int(timeValues[1]), PIn.Int(timeValues[2]));
                if (isNegative)
                {
                    return retval.Negate();
                }
                 
                return retval;
            }
            else
            {
                return (System.TimeSpan.Parse(myString));
            } 
        }
        catch (Exception __dummyCatchVar6)
        {
            return System.TimeSpan.Zero;
        }
    
    }

    //mysql
    /**
    * Used for Timespans that are guaranteed to always be a valid time of day.  No negatives or hours over 24.  Stored in Oracle as datetime.
    */
    public static TimeSpan time(String myString) throws Exception {
        if (String.IsNullOrEmpty(myString))
        {
            return System.TimeSpan.Zero;
        }
         
        try
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                return DateTime.Parse(myString).TimeOfDay;
            }
            else
            {
                return (System.TimeSpan.Parse(myString));
            } 
        }
        catch (Exception __dummyCatchVar7)
        {
            return System.TimeSpan.Zero;
        }
    
    }

}


//mysql