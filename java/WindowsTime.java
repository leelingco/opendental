//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//


import CS2JNet.JavaSupport.language.RefSupport;

public class WindowsTime   
{
    private /* [UNSUPPORTED] 'extern' modifier not supported */ static uint setLocalTime(RefSupport<SYSTEMTIME> lpSystemTime) throws Exception ;

    private static class SYSTEMTIME   
    {
        public SYSTEMTIME() {
        }

        public ushort wYear = new ushort();
        public ushort wMonth = new ushort();
        public ushort wDayOfWeek = new ushort();
        public ushort wDay = new ushort();
        public ushort wHour = new ushort();
        public ushort wMinute = new ushort();
        public ushort wSecond = new ushort();
        public ushort wMilliseconds = new ushort();
    }

    public WindowsTime() throws Exception {
    }

    /**
    * Set the windows system time.
    */
    public static void setTime(DateTime newTime) throws Exception {
        // Call the native SetLocalTime method
        // with the defined structure.
        SYSTEMTIME systime = new SYSTEMTIME();
        systime.wYear = (ushort)newTime.Year;
        systime.wMonth = (ushort)newTime.Month;
        systime.wDayOfWeek = (ushort)newTime.DayOfWeek;
        systime.wDay = (ushort)newTime.Day;
        systime.wHour = (ushort)newTime.Hour;
        systime.wMinute = (ushort)newTime.Minute;
        systime.wSecond = (ushort)newTime.Second;
        systime.wMilliseconds = (ushort)newTime.Millisecond;
        RefSupport<SYSTEMTIME> refVar___0 = new RefSupport<SYSTEMTIME>(systime);
        setLocalTime(refVar___0);
        systime = refVar___0.getValue();
        String messageText = "System date and time set to:  " + newTime.ToString("MM/dd/yyyy hh:mm:ss.fff tt") + ".";
        EventLog.WriteEntry("OpenDental", messageText, EventLogEntryType.Information);
    }

}


