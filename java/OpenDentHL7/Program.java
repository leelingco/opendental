//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package OpenDentHL7;

import CS2JNet.System.StringSupport;
import OpenDentHL7.Program;
import OpenDentHL7.ServiceHL7;

public class Program   
{
    /**
    * The main entry point for the application.
    */
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        EventLog.WriteEntry("OpenDentHL7.Main", DateTime.Now.ToLongTimeString() + " - Service main method starting...");
        ServiceHL7 serviceHL7 = new ServiceHL7();
        serviceHL7.ServiceName = "OpenDentalHL7";
        //default
        //Get the executing assembly location directory (location of this OpenDentHL7.exe)
        String executingDir = Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
        //Get all installed services
        List<ServiceController> serviceControllersOD = new List<ServiceController>();
        ServiceController[] serviceControllersAll = ServiceController.GetServices();
        for (int i = 0;i < serviceControllersAll.Length;i++)
        {
            //Get all installed services that have names that start with "OpenDent"
            if (serviceControllersAll[i].ServiceName.StartsWith("OpenDent"))
            {
                serviceControllersOD.Add(serviceControllersAll[i]);
            }
             
        }
        String pathToODHL7exe = new String();
        for (int i = 0;i < serviceControllersOD.Count;i++)
        {
            //Get the service that is installed from the same directory as the current directory
            RegistryKey hklm = Registry.LocalMachine;
            hklm = hklm.OpenSubKey("System\\CurrentControlSet\\Services\\" + serviceControllersOD[i].ServiceName);
            pathToODHL7exe = hklm.GetValue("ImagePath").ToString();
            pathToODHL7exe = pathToODHL7exe.Replace("\"", "");
            pathToODHL7exe = Path.GetDirectoryName(pathToODHL7exe);
            if (StringSupport.equals(pathToODHL7exe, executingDir))
            {
                //Set the name of the service to run as the name of the service installed from this directory
                serviceHL7.ServiceName = serviceControllersOD[i].ServiceName;
                break;
            }
             
        }
        ServiceBase.Run(serviceHL7);
        EventLog.WriteEntry("OpenDentHL7.Main", DateTime.Now.ToLongTimeString() + " - Service main method exiting...");
    }

}


