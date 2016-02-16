//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PluginBase;
import OpenDentBusiness.PluginContainer;
import OpenDentBusiness.ProgramC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class Plugins   
{
    private static List<PluginContainer> PluginList = new List<PluginContainer>();
    //public static bool Active=false;
    public static boolean getPluginsAreLoaded() throws Exception {
        if (PluginList == null)
        {
            return false;
        }
        else
        {
            return true;
        } 
    }

    public static void loadAllPlugins(Form host) throws Exception {
        //No need to check RemotingRole; no call to db.
        PluginList = new List<PluginContainer>();
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return ;
        }
         
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            //no plugins will load.  So from now on, we can assume a direct connection.
            if (!ProgramC.getListt()[i].Enabled)
            {
                continue;
            }
             
            if (StringSupport.equals(ProgramC.getListt()[i].PluginDllName, ""))
            {
                continue;
            }
             
            String dllPath = CodeBase.ODFileUtils.CombinePaths(Application.StartupPath, ProgramC.getListt()[i].PluginDllName);
            if (dllPath.Contains("[VersionMajMin]"))
            {
                Version vers = new Version(Application.ProductVersion);
                String dllPathWithVersion = dllPath.Replace("[VersionMajMin]", vers.Major.ToString() + "." + vers.Minor.ToString());
                dllPath = dllPath.Replace("[VersionMajMin]", "");
                //now stripped clean
                if (File.Exists(dllPathWithVersion))
                {
                    File.Copy(dllPathWithVersion, dllPath, true);
                }
                 
            }
             
            if (!File.Exists(dllPath))
            {
                continue;
            }
             
            PluginBase plugin = null;
            try
            {
                Assembly ass = Assembly.LoadFile(dllPath);
                String typeName = Path.GetFileNameWithoutExtension(dllPath) + ".Plugin";
                Type type = ass.GetType(typeName);
                plugin = (PluginBase)Activator.CreateInstance(type);
                plugin.setHost(host);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                continue;
            }

            //don't add it to plugin list.
            PluginContainer container = new PluginContainer();
            container.Plugin = plugin;
            container.ProgramNum = ProgramC.getListt()[i].ProgramNum;
            PluginList.Add(container);
        }
    }

    //Active=true;
    /**
    * Will return true if a plugin implements this method, replacing the default behavior.
    */
    public static boolean hookMethod(Object sender, String hookName, Object... parameters) throws Exception {
        for (int i = 0;i < PluginList.Count;i++)
        {
            //if there are multiple plugins, we use the first implementation that we come to.
            if (PluginList[i].Plugin.HookMethod(sender, hookName, parameters))
            {
                return true;
            }
             
        }
        return false;
    }

    //no implementation was found
    /**
    * Adds code without disrupting existing code.
    */
    public static void hookAddCode(Object sender, String hookName, Object... parameters) throws Exception {
        for (int i = 0;i < PluginList.Count;i++)
        {
            //if there are multiple plugins, we run them all
            PluginList[i].Plugin.HookAddCode(sender, hookName, parameters);
        }
    }

    public static void launchToolbarButton(long programNum, long patNum) throws Exception {
        for (int i = 0;i < PluginList.Count;i++)
        {
            if (PluginList[i].ProgramNum == programNum)
            {
                PluginList[i].Plugin.LaunchToolbarButton(patNum);
                return ;
            }
             
        }
    }

}


