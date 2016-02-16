//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UpdateFileCopier;

import UpdateFileCopier.FormMain;
import UpdateFileCopier.Program;

public class Program   
{
    /**
    * The main entry point for the application.  Takes the following arguments: sourcedir processid destdir
    */
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] arguments) throws Exception {
        Application.EnableVisualStyles();
        Application.SetCompatibleTextRenderingDefault(false);
        if (arguments.Length == 3)
        {
            Application.Run(new FormMain(arguments[0], arguments[1], arguments[2]));
        }
        else if (arguments.Length == 2)
        {
            Application.Run(new FormMain(arguments[0], arguments[1], "C:\\Program Files\\Open Dental"));
        }
        else
        {
            //just for rare debugging situations
            Application.Run(new FormMain("C:\\OpenDentImages\\UpdateFiles","0","C:\\Program Files\\Open Dental"));
        }  
    }

}


