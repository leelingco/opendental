//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;

import CS2JNet.System.LCC.Disposable;
import OpenDentBusiness.POut;

//using System.Windows.Forms;
/**
* Converts various datatypes into strings formatted correctly for MySQL. "P" was originally short for Parameter because this class was written specifically to replace parameters in the mysql queries. Using strings instead of parameters is much easier to debug. I would rather not ever depend on the mysql connector for this because the authors of the connector have been known to suddenly change its behavior.
*/
public class POut   
{
    /**
    * 
    */
    public static String bool(boolean myBool) throws Exception {
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
    public static String byte(byte myByte) throws Exception {
        return myByte.ToString();
    }

    /**
    * Always encapsulates the result, depending on the current database connection.
    */
    public static String dateT(DateTime myDateT) throws Exception {
        return dateT(myDateT,true);
    }

    /**
    * 
    */
    public static String dateT(DateTime myDateT, boolean encapsulate) throws Exception {
        if (myDateT.Year < 1880)
        {
            myDateT = DateTime.MinValue;
        }
         
        try
        {
            String outDate = myDateT.ToString("yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture);
            //new DateTimeFormatInfo());
            String frontCap = "'";
            String backCap = "'";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                frontCap = "TO_DATE('";
                backCap = "','YYYY-MM-DD HH24:MI:SS')";
            }
             
            if (encapsulate)
            {
                outDate = frontCap + outDate + backCap;
            }
             
            return outDate;
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }
    
    }

    //this saves zero's to a mysql database
    /**
    * Converts a date to yyyy-MM-dd format which is the format required by MySQL. myDate is the date you want to convert. encapsulate is true for the first overload, making the result look like this: 'yyyy-MM-dd' for MySQL.
    */
    public static String date(DateTime myDate) throws Exception {
        return date(myDate,true);
    }

    public static String date(DateTime myDate, boolean encapsulate) throws Exception {
        try
        {
            //js I commented this out Jan 2010 because we do not want this method to behave unexpectedly.
            //As a result, we have already had one bug in the recall, and we might have more.
            //But this must not be reverted.
            //if(myDate.Year<1880) {
            //	myDate=DateTime.MinValue;
            //}
            //the new DTFormatInfo is to prevent changes in year for Korea
            String outDate = myDate.ToString("yyyy-MM-dd", new DateTimeFormatInfo());
            String frontCap = "'";
            String backCap = "'";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                frontCap = "TO_DATE('";
                backCap = "','YYYY-MM-DD')";
            }
             
            if (encapsulate)
            {
                outDate = frontCap + outDate + backCap;
            }
             
            return outDate;
        }
        catch (Exception __dummyCatchVar1)
        {
            return "";
        }
    
    }

    //return "0000-00-00";
    //this saves zeros to the database
    /**
    * Timespans that might be invalid time of day.  Can be + or - and can be up to 800+ hours.  Stored in Oracle as varchar2.  Never encapsulates
    */
    public static String tSpan(TimeSpan myTimeSpan) throws Exception {
        if (myTimeSpan == System.TimeSpan.Zero)
        {
            return "00:00:00";
                ;
        }
         
        try
        {
            String retval = "";
            if (myTimeSpan < System.TimeSpan.Zero)
            {
                retval += "-";
                myTimeSpan = myTimeSpan.Duration();
            }
             
            int hours = (myTimeSpan.Days * 24) + myTimeSpan.Hours;
            retval += hours.ToString().PadLeft(2, '0') + ":" + myTimeSpan.Minutes.ToString().PadLeft(2, '0') + ":" + myTimeSpan.Seconds.ToString().PadLeft(2, '0');
            return retval;
        }
        catch (Exception __dummyCatchVar2)
        {
            return "00:00:00";
        }
    
    }

    /**
    * Timespans that are guaranteed to always be a valid time of day.  No negatives or hours over 24.  Stored in Oracle as datetime.  Encapsulated by default.
    */
    public static String time(TimeSpan myTimeSpan) throws Exception {
        return POut.time(myTimeSpan,true);
    }

    /**
    * Timespans that are guaranteed to always be a valid time of day.  No negatives or hours over 24.  Stored in Oracle as datetime.  Encapsulated by default.
    */
    public static String time(TimeSpan myTimeSpan, boolean encapsulate) throws Exception {
        String retval = myTimeSpan.Hours.ToString().PadLeft(2, '0') + ":" + myTimeSpan.Minutes.ToString().PadLeft(2, '0') + ":" + myTimeSpan.Seconds.ToString().PadLeft(2, '0');
        if (encapsulate)
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                return "'" + retval + "'";
            }
            else
            {
                return "TO_TIMESTAMP('" + retval + "','HH24:MI:SS')";
            } 
        }
        else
        {
            return retval;
        } 
    }

    //Oracle
    /**
    * 
    */
    public static String double(double myDouble) throws Exception {
        try
        {
            return myDouble.ToString("f", CultureInfo.InvariantCulture);
        }
        catch (Exception __dummyCatchVar3)
        {
            return "0";
        }
    
    }

    //because decimal is a comma in Europe, this sends it to db with period instead
    /**
    * 
    */
    public static String long(long myLong) throws Exception {
        return myLong.ToString();
    }

    /**
    * 
    */
    public static String int(int myInt) throws Exception {
        return myInt.ToString();
    }

    //public static string Short(short myShort) {
    //	return myShort.ToString();
    //}
    /**
    * 
    */
    public static String float(float myFloat) throws Exception {
        return myFloat.ToString(CultureInfo.InvariantCulture);
    }

    //sends as comma in Europe.  (comes back from mysql later as a period)
    /**
    * Escapes all necessary characters.
    */
    public static String string(String myString) throws Exception {
        if (myString == null)
        {
            return "";
        }
         
        /*
        			if(DataConnection.DBtype!=DatabaseType.MySql){
        				if(myString.Contains(";")){
        					myString=myString.Replace(";","");
        				}
        				if(myString.Contains("'")) {
        					myString=myString.Replace("'","");
        				}
        				if(myString==null) {
        					return "";
        				}
        			}*/
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0;i < myString.Length;i++)
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                System.String.APPLY __dummyScrutVar0 = myString.Substring(i, 1);
                if (__dummyScrutVar0.equals("'"))
                {
                    strBuild.Append("''");
                }
                else
                {
                    // ' replaced by ''
                    strBuild.Append(myString.Substring(i, 1));
                } 
            }
            else
            {
                System.String.APPLY __dummyScrutVar1 = myString.Substring(i, 1);
                //note. When using binary data, must escape ',",\, and nul(? haven't done nul)
                //_ and % are special characters in LIKE clauses.  But they need not be escaped.  Only a potential problem when using LIKE.
                if (__dummyScrutVar1.equals("'"))
                {
                    strBuild.Append("\\\'");
                }
                else // ' replaced by \'
                if (__dummyScrutVar1.equals("\""))
                {
                    strBuild.Append("\\\"");
                }
                else // " replaced by \"
                if (__dummyScrutVar1.equals("\\"))
                {
                    strBuild.Append("\\\\");
                }
                else //single \ replaced by \\
                if (__dummyScrutVar1.equals("\r"))
                {
                    strBuild.Append("\\r");
                }
                else //carriage return(usually followed by new line)
                if (__dummyScrutVar1.equals("\n"))
                {
                    strBuild.Append("\\n");
                }
                else //new line
                if (__dummyScrutVar1.equals("\t"))
                {
                    strBuild.Append("\\t");
                }
                else
                {
                    //tab
                    strBuild.Append(myString.Substring(i, 1));
                }      
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
    /*
    		///<summary></summary>
    		public static string PBitmap(Bitmap bitmap) {
    			if(bitmap==null){
    				return "";
    			}
    			MemoryStream stream=new MemoryStream();
    			bitmap.Save(stream,ImageFormat.Bmp);
    			byte[] rawData=stream.ToArray();
    			return Convert.ToBase64String(rawData);
    		}*/
    /**
    * 
    */
    public static String bitmap(System.Drawing.Bitmap bitmap, ImageFormat imageFormat) throws Exception {
        if (bitmap == null)
        {
            return "";
        }
         
        MemoryStream stream = new MemoryStream();
        try
        {
            {
                bitmap.Save(stream, imageFormat);
                //was Bmp, then Png, then user defined.  So there will be a mix of different kinds.
                byte[] rawData = stream.ToArray();
                return Convert.ToBase64String(rawData);
            }
        }
        finally
        {
            if (stream != null)
                Disposable.mkDisposable(stream).dispose();
             
        }
    }

    /**
    * Converts the specified wav file into a string representation.  The timing of this is a little different than with the other "P" functions and is only used by the import button in FormSigElementDefEdit.  After that, the wav spends the rest of it's life as a string until "played" or exported.
    */
    public static String sound(String filename) throws Exception {
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

    /**
    * The supplied string should already be in safe base64 format, and should not need any special escaping.  The purpose of this function is to enforce that the supplied string meets these requirements.  This is done quickly.
    */
    public static String base64(String myString) throws Exception {
        if (myString == null)
        {
            return "";
        }
         
        if (!Regex.IsMatch(myString, "[A-Z0-9]*"))
        {
            throw new ApplicationException("Characters found that do not match base64 format.");
        }
         
        return myString;
    }

}


