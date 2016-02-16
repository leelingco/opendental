//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package OpenDentServer;


public class MyProjectInstaller  extends Installer 
{
    private ServiceInstaller serviceInstaller1 = new ServiceInstaller();
    private ServiceProcessInstaller processInstaller = new ServiceProcessInstaller();
    public MyProjectInstaller() throws Exception {
        processInstaller = new ServiceProcessInstaller();
        serviceInstaller1 = new ServiceInstaller();
        processInstaller.Account = ServiceAccount.LocalSystem;
        serviceInstaller1.StartType = ServiceStartMode.Automatic;
        serviceInstaller1.ServiceName = "OpenDentHL7";
        String[] args = Environment.GetCommandLineArgs();
        for (int i = 0;i < args.Length;i++)
        {
            if (args[i].StartsWith("/ServiceName"))
            {
                serviceInstaller1.ServiceName = args[i].Substring(13);
            }
             
        }
        Installers.Add(serviceInstaller1);
        Installers.Add(processInstaller);
    }

}


