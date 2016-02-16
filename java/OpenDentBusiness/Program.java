//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Program;
import OpenDentBusiness.TableBase;

/**
* Each row is a bridge to an outside program, frequently an imaging program.  Most of the bridges are hard coded, and simply need to be enabled.  But user can also add their own custom bridge.
*/
public class Program  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProgramNum = new long();
    /**
    * Unique name for built-in program bridges. Not user-editable. enum ProgramName
    */
    public String ProgName = new String();
    /**
    * Description that shows.
    */
    public String ProgDesc = new String();
    /**
    * True if enabled.
    */
    public boolean Enabled = new boolean();
    /**
    * The path of the executable to run or file to open.
    */
    public String Path = new String();
    /**
    * Some programs will accept command line arguments.
    */
    public String CommandLine = new String();
    /**
    * Notes about this program link. Peculiarities, etc.
    */
    public String Note = new String();
    /**
    * If this is a Plugin, then this is the filename of the dll.  The dll must be located in the application directory.
    */
    public String PluginDllName = new String();
    public Program copy() throws Exception {
        return (Program)this.MemberwiseClone();
    }

}


