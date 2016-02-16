//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;


public abstract class PluginBase   
{
    private Form host = new Form();
    /**
    * This will be a refrence to the main FormOpenDental so that it can be used by    the plugin if needed.  It is set once on startup, so it's a good place to put startup code.
    */
    public Form getHost() throws Exception {
        return host;
    }

    public void setHost(Form value) throws Exception {
        host = value;
    }

    /**
    * These types of hooks are designed to completely replace the existing functionality of specific methods.  They always belong at the top of a method.
    */
    public boolean hookMethod(Object sender, String methodName, Object... parameters) throws Exception {
        return false;
    }

    //by default, no hooks are implemented.
    /**
    * These types of hooks allow adding extra code in at some point without disturbing the existing code.
    */
    public boolean hookAddCode(Object sender, String hookName, Object... parameters) throws Exception {
        return false;
    }

    public void launchToolbarButton(long patNum) throws Exception {
    }

}


