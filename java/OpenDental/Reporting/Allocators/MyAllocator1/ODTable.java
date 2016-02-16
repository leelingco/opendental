//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;


public class ODTable   
{
    public final String[] columns = null;
    public final Type[] columntypes = null;
    public final String name = "";
    /**
    * Just Throws an exception if setup is bad.  Call only from a constructor.
    */
    protected void checkODTableSetup() throws Exception {
        if (columns == null || columntypes == null || columns.Length != columntypes.Length)
            throw new Exception("ODTable constructor Failed");
         
    }

}


