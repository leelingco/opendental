//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CodeBase.Logger;
import CodeBase.MsgBoxCopyPaste;
import CodeBase.ODFileUtils;

/**
* Used to log messages to our internal log file, or to other resources, such as message boxes.
*/
public class Logger   
{
    public enum Severity
    {
        /**
        * Levels of logging severity to indicate importance.
        */
        NONE,
        //Must be first.
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL_ERROR
    }
    /**
    * The number of bytes it takes to move the current log to the backup/old log, to prevent the log files from growing infinately.
    */
    private static final int logRollByteCount = 1048576;
    public static Logger openlog = new Logger(ODFileUtils.getProgramDirectory() + "openlog.txt");
    private String logFile = "";
    /**
    * Specifies the current logging level. Any severity less than the given level is not logged.
    */
    public Severity level = Severity.NONE;
    public Logger(String pLogFile) throws Exception {
        logFile = pLogFile;
    }

    /**
    * Convert a severity code into a string.
    */
    public static String severityToString(Severity sev) throws Exception {
        switch(sev)
        {
            case NONE: 
                return "NONE";
            case DEBUG: 
                return "DEBUG";
            case INFO: 
                return "INFO";
            case WARNING: 
                return "WARNING";
            case ERROR: 
                return "ERROR";
            case FATAL_ERROR: 
                return "FATAL ERROR";
            default: 
                break;
        
        }
        return "UNKNOWN SEVERITY";
    }

    public static int maxSeverityStringLength() throws Exception {
        int maxlen = 0;
        for (Severity sev = Severity.values()[1];sev < Severity.values()[7];sev++)
        {
            maxlen = Math.Max(maxlen, severityToString(sev).Length);
        }
        return maxlen;
    }

    /**
    * Log a message from an unknown source.
    */
    public boolean log(String message, Severity severity) throws Exception {
        return log(null,"",message,false,severity);
    }

    public boolean logMB(String message, Severity severity) throws Exception {
        return log(null,"",message,true,severity);
    }

    public boolean log(Object sender, String sendingFunctionName, String message, Severity severity) throws Exception {
        return Log(sender, sendingFunctionName, message, false, severity);
    }

    public boolean logMB(Object sender, String sendingFunctionName, String message, Severity severity) throws Exception {
        return Log(sender, sendingFunctionName, message, true, severity);
    }

    /**
    * Log a message to the log text file and add a description of the sender (for debugging purposes). If sender is null then a description of the sender is not printed. Always returns false so that a calling boolean function can return at the same time that it logs an error message.
    */
    public boolean log(Object sender, String sendingFunctionName, String message, boolean msgBox, Severity severity) throws Exception {
        if (severity > level)
        {
            return false;
        }
         
        try
        {
            //Only log messages with a severity matches the current level. This will even skip message boxes.
            if (sender != null)
            {
                if (sendingFunctionName != null && sendingFunctionName.Length > 0)
                {
                    message = sender.ToString() + "." + sendingFunctionName + ": " + message;
                }
                else
                {
                    message = sender.ToString() + ": " + message;
                } 
            }
            else if (sendingFunctionName != null && sendingFunctionName.Length > 0)
            {
                message = sendingFunctionName + ": " + message;
            }
              
            int procId = System.Diagnostics.Process.GetCurrentProcess().Id;
            message = DateTime.Now.ToString("dd/MM/yyyy hh:mm:ss") + " " + procId.ToString().PadLeft(6, '0') + " " + severityToString(severity) + " " + message;
            if (msgBox)
            {
                MsgBoxCopyPaste mbox = new MsgBoxCopyPaste(message);
                mbox.ShowDialog();
            }
             
        }
        catch (Exception e)
        {
            MessageBox.Show(e.ToString());
        }

        //File access is always exclusive, so if we cannot access the file, we can try again for a little while
        //and hope that the other process will release the file.
        boolean tryagain = true;
        int numtries = 0;
        while (tryagain && numtries < 5)
        {
            tryagain = false;
            numtries++;
            try
            {
                if (logFile != null)
                {
                    //Ensure that the log file always exists before trying to read it.
                    if (!File.Exists(logFile))
                    {
                        try
                        {
                            FileStream fs = File.Create(logFile);
                            fs.Dispose();
                        }
                        catch (Exception __dummyCatchVar0)
                        {
                        }
                    
                    }
                    else
                    {
                        //Make the log file roll into the old log file when it reaches the roll byte size.
                        System.IO.StreamReader sr = new System.IO.StreamReader(logFile);
                        if (sr != null)
                        {
                            Stream st = sr.BaseStream;
                            long fileLength = st.Length;
                            if (fileLength >= logRollByteCount)
                            {
                                try
                                {
                                    File.Copy(logFile, logFile + ".old.txt");
                                }
                                catch (Exception __dummyCatchVar1)
                                {
                                }

                                try
                                {
                                    File.Delete(logFile);
                                }
                                catch (Exception __dummyCatchVar2)
                                {
                                }

                                fileLength = 0;
                            }
                             
                            st.Dispose();
                            sr.Dispose();
                            if (fileLength < 1)
                            {
                                try
                                {
                                    FileStream fs = File.Create(logFile);
                                    fs.Dispose();
                                }
                                catch (Exception __dummyCatchVar3)
                                {
                                }
                            
                            }
                             
                        }
                         
                    } 
                    //Re-open the log file
                    System.IO.StreamWriter sw = new System.IO.StreamWriter(logFile, true);
                    //Open the file exclusively.
                    if (sw != null)
                    {
                        sw.WriteLine(message);
                        sw.Flush();
                        sw.Dispose();
                    }
                     
                }
                 
            }
            catch (Exception __dummyCatchVar4)
            {
                //Close the file to allow exclusive access by other instances of OpenDental.
                tryagain = true;
            }
        
        }
        return false;
    }

}


