//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import OpenDental.FormOpenDental;
import OpenDental.ProgramEntry;

public class ProgramEntry   
{
    public static void main(String[] args) throws Exception {
        ProgramEntry.Main(args);
    }

    static void Main(String[] args) throws Exception {
        //Register an EventHandler which handles unhandled exceptions.
        //AppDomain.CurrentDomain.UnhandledException+=new UnhandledExceptionEventHandler(OnUnhandeledExceptionPolicy);
        boolean isSecondInstance = false;
        //or more.
        Process[] processes = Process.GetProcesses();
        for (int i = 0;i < processes.Length;i++)
        {
            if (processes[i].Id == Process.GetCurrentProcess().Id)
            {
                continue;
            }
             
            //we have to do it this way because during debugging, the name has vshost tacked onto the end.
            if (processes[i].ProcessName.StartsWith("OpenDental"))
            {
                isSecondInstance = true;
                break;
            }
             
        }
        /*
        			if(args.Length>0) {//if any command line args, then we will attempt to reuse an existing OD window.
        				if(isSecondInstance){
        					FormCommandLinePassOff formCommandLine=new FormCommandLinePassOff();
        					formCommandLine.CommandLineArgs=new string[args.Length];
        					args.CopyTo(formCommandLine.CommandLineArgs,0);
        					Application.Run(formCommandLine);
        					return;
        				}
        			}*/
        Application.SetCompatibleTextRenderingDefault(false);
        //designer uses new text rendering.  This makes the exe use matching text rendering.  Before this was added, it was common for labels to be longer in the running program than they were in the designer.
        Application.EnableVisualStyles();
        //changes appearance to XP
        Application.DoEvents();
        String[] cla = new String[args.Length];
        args.CopyTo(cla, 0);
        FormOpenDental formOD = new FormOpenDental(cla);
        formOD.IsSecondInstance = isSecondInstance;
        Application.Run(formOD);
    }

}


/*
		///<summary>Overrides the default Windows unhandled exception functionality.</summary>
		static void OnUnhandeledExceptionPolicy(Object Sender,UnhandledExceptionEventArgs e) {
			Exception ex=e.ExceptionObject as Exception;
			string message="Unhandled Exception: ";
			if(ex!=null) {//The unhandeled Exception is CLS compliant.
				message+=ex.ToString();
			}else{//The unhandeled Exception is not CLS compliant.				
				//You can only handle this Exception as Object
				message+="Object Type: "+e.ExceptionObject.GetType()+", Object String: "+e.ExceptionObject.ToString();
			}
			if(!e.IsTerminating){
				//Exception occurred in a thread pool or finalizer thread. Debugger launches only explicitly.
				Logger.openlog.LogMB(message,Logger.Severity.ERROR);
#if(DEBUG)
				Debugger.Launch();
#endif
			}else{
				//Exception occurred in managed thread. Debugger will automatically launch in visual studio.
				Logger.openlog.LogMB(message,Logger.Severity.FATAL_ERROR);
			}
		}*/