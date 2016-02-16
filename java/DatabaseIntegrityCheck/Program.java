//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package DatabaseIntegrityCheck;

import DatabaseIntegrityCheck.FormDatabaseCheck;
import DatabaseIntegrityCheck.Program;

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
        Application.Run(new FormDatabaseCheck());
    }

}


