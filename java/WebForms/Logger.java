//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package WebForms;

import CS2JNet.System.StringSupport;

///Dennis: There are far too many intricacies for the Enterprise library  all of which would be an overkill if througly explored.
///At this stge all I needed was simple logging to a flat files. If needed the more features could be added later.
/// The following code has being pieced together from the web uses the of Enterprise Library Logging Application Block without the hassle of configuration setting in the config file.
/// There is only one flat listener in this code.The other listenr in the code i.e event log listener has been commented.
///To compile this code the Enterprise library should installed from http://www.microsoft.com/downloads/details.aspx?familyid=1643758B-2986-47F7-B529-3E41584B6CE5&displaylang=en
///Update: Added 2 Microsoft Enterprise Library dlls which previously had to be installed into the system from the Microsoft site. This means that each developer need not install the Microsoft enterprise library for the code to compile.
public class Logger   
{
    /**
    * Log writer
    */
    static final LogWriter writer = new LogWriter();
    // readonly modifier can be initialized only in a constructor
    static final boolean LogErr = false;
    static final boolean LogInfo = false;
    static {
        try
        {
            /**
            * Static constructor
            */
            String LogFile = ConfigurationManager.AppSettings["LogFile"].ToString();
            // a static constructor works because changing the web.config restarts the appliaction.
            // this defaults to the namespace WebForms even when used in another application so it's not used
            //string LogFile=Properties.Settings.Default.LogFile;
            if (StringSupport.equals(ConfigurationManager.AppSettings["LogErr"].ToString().ToLower(), "yes"))
            {
                LogErr = true;
            }
             
            if (StringSupport.equals(ConfigurationManager.AppSettings["LogInfo"].ToString().ToLower(), "yes"))
            {
                LogInfo = true;
            }
             
            // formatter
            TextFormatter formatter = new TextFormatter("[{timestamp(local)}] [{machine}] {category}  \t: {message}");
            // listeners
            FlatFileTraceListener logFileListener = new FlatFileTraceListener(LogFile, "", "", formatter);
            RollingFlatFileTraceListener rollingFlatFileListener = new RollingFlatFileTraceListener(LogFile, "", "", formatter, 1000, "yyyy-MM-dd", RollFileExistsBehavior.Increment, RollInterval.Day);
            //uncomment if an event log is needed
            //FormattedEventLogTraceListener logEventListener = new FormattedEventLogTraceListener("Enterprise Library Logging",formatter);
            // Sources
            LogSource mainLogSource = new LogSource("MainLogSource", SourceLevels.All);
            //mainLogSource.Listeners.Add(logFileListener);//regular flat file
            mainLogSource.Listeners.Add(rollingFlatFileListener);
            //uncomment if an event log is needed
            //LogSource errorLogSource = new LogSource("ErrorLogSource",SourceLevels.Error);
            //errorLogSource.Listeners.Add(logEventListener);
            // empty source
            LogSource nonExistantLogSource = new LogSource("Empty");
            //non matching category.
            // trace sources
            IDictionary<String, LogSource> traceSources = new Dictionary<String, LogSource>();
            //traceSources.Add("Error",errorLogSource);//uncomment if an event log is needed
            traceSources.Add("Warning", mainLogSource);
            traceSources.Add("Information", mainLogSource);
            // log writer
            writer = new LogWriter(new ILogFilter[0], traceSources, mainLogSource, nonExistantLogSource, mainLogSource, "Error", false, true);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    //writer = new LogWriter(new ILogFilter[0],traceSources,mainLogSource,nonExistantLogSource,
    //errorLogSource,"Error",false,true);//uncomment if 'internal' error are to be logged to an event log is needed
    /**
    * Writes an Error to the log. Dennis - uncomment method below to enable Error()
    * 
    *  @param message Error Message
    */
    /*
    		public static void Error(string message)
    		{ 
    			Write(message,TraceEventType.Error);
    		}
    		*/
    /**
    * dennis - uncomment method below to enable Warning()
    * Writes a Warning to the log.
    * 
    *  @param message Warning Message
    */
    /*
    		public static void Warning(string message)
    		{  
    			Write(message,TraceEventType.Warning);
    		}
    		*/
    /**
    * Writes an Information to the log.
    * 
    *  @param message Information Message
    */
    public static void information(String message) throws Exception {
        if (!LogInfo)
        {
            return ;
        }
         
        StackFrame stFrame = new StackFrame(1, true);
        String Filename = " Filename: " + stFrame.GetFileName().Substring(stFrame.GetFileName().LastIndexOf("\\") + 1);
        String MethodName = " Method: " + stFrame.GetMethod();
        String LineNumber = " LineNumber: " + stFrame.GetFileLineNumber();
        message = message + Filename + MethodName + LineNumber;
        Write(message, TraceEventType.Information);
    }

    /**
    * Writes an Error and it's inner exception if any to the log.
    * 
    *  @param message Information Message
    */
    public static void logError(String Message, Exception ex) throws Exception {
        if (!LogErr)
        {
            return ;
        }
         
        String message = Message + " " + ex.Message.ToString();
        StackFrame stFrame = new StackFrame(1, true);
        String Filename = " Filename: " + stFrame.GetFileName().Substring(stFrame.GetFileName().LastIndexOf("\\") + 1);
        String MethodName = " Method: " + stFrame.GetMethod();
        String LineNumber = " LineNumber: " + stFrame.GetFileLineNumber();
        String StackTrace = " StackTrace: " + ex.StackTrace;
        message = message + Filename + MethodName + LineNumber + StackTrace;
        Write(message, TraceEventType.Error);
        if (ex.InnerException != null)
        {
            Console.WriteLine("Inner Exception");
            Write("InnerException " + ex.InnerException.StackTrace + " " + ex.InnerException.Message, TraceEventType.Error);
            if (ex.InnerException.InnerException != null)
            {
                Write("InnerException of  InnerException" + ex.InnerException.InnerException.StackTrace + " " + ex.InnerException.InnerException.Message, TraceEventType.Error);
            }
             
        }
         
    }

    public static void logError(Exception ex) throws Exception {
        logError("",ex);
    }

    /**
    * Writes a message to the log using the specified category.
    * 
    *  @param message  Message to log
    *  @param category Message category. e.g. 'Error','Warning','Information'
    */
    private static void write(String message, String category) throws Exception {
        LogEntry entry = new LogEntry();
        entry.Categories.Add(category);
        entry.Message = message;
        writer.Write(entry);
    }

    /**
    * Writes a message to the log using the specified
    * category.
    * 
    *  @param message 
    *  @param category
    */
    private static void write(String message, TraceEventType severity) throws Exception {
        LogEntry entry = new LogEntry();
        entry.Categories.Add(severity.ToString());
        entry.Message = message;
        entry.Severity = severity;
        writer.Write(entry);
    }

}


