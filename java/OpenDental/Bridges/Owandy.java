//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import OpenDentBusiness.Patient;
import OpenDentBusiness.Program;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Owandy   
{
    /**
    * 
    */
    public Owandy() throws Exception {
    }

    //AAD External Call declaration for Owandy bridge (Start)
    /**
    * 
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ IntPtr findWindow(String lpClassName, String lpWindowName) throws Exception ;

    /**
    * 
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ Boolean isWindow(IntPtr hWnd) throws Exception ;

    /**
    * 
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ IntPtr sendMessage(IntPtr hWnd, int Msg, Int32 wParam, String lParam) throws Exception ;

    /**
    * 
    */
    //public IntPtr formHandle;
    /**
    * 
    */
    public static IntPtr hwndLink = new IntPtr();
    /**
    * 
    */
    public static final int WM_SETTEXT = 0x000C;
    // AAD External Call declaration for Owandy bridge (nd)
    //static extern long SendMessage(long hWnd, long Msg, long wParam, string lParam);
    /**
    * Launches the program using command line, then passes some data using Windows API.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        //ProgramProperties.GetForProgram();
        String info = new String();
        if (pat != null)
        {
            try
            {
                //formHandle = Parent.Handle;
                System.Diagnostics.Process.Start(path, ProgramCur.CommandLine);
                //"C /LINK "+ formHandle;
                if (!isWindow(hwndLink))
                {
                    hwndLink = findWindow("MjLinkWndClass",null);
                }
                 
                // info = "/P:1,DEMO,Patient1";
                //Patient id can be any string format
                info = "/P:" + pat.PatNum + "," + pat.LName + "," + pat.FName;
                if (isWindow(hwndLink) == true)
                {
                    IntPtr lResp = SendMessage(hwndLink, WM_SETTEXT, 0, info);
                }
                 
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(path + " is not available, or there is an error in the command line options.");
            }
        
        }
        else
        {
            try
            {
                //if patient is loaded
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar1)
            {
                //should start Owandy without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }
        
        } 
    }

}


