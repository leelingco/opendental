//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import TestCanada.FormTestCanada;
import TestCanada.Program;

public class Program   
{
    /**
    * The main entry point for the application.
    */
    public static void main(String[] args) throws Exception {
        Program.Main();
    }

    static void Main() throws Exception {
        Application.EnableVisualStyles();
        Application.SetCompatibleTextRenderingDefault(false);
        Application.Run(new FormTestCanada());
    }

}


