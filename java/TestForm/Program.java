//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:34 PM
//

package TestForm;

import TestForm.Form1;
import TestForm.Program;

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
        Application.Run(new Form1());
    }

}


