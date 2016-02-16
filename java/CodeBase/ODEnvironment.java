//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CS2JNet.System.StringSupport;

public class ODEnvironment   
{
    //public static bool Is64BitOperatingSystem(){
    //  string arch="";
    //  try{
    //      arch=Environment.GetEnvironmentVariable("PROCESSOR_ARCHITECTURE");
    //  }catch{
    //      //May fail if the environemnt variable is not present on the target machine (i.e. Unix).
    //  }
    //  bool retVal=Regex.IsMatch(arch,".*64.*");
    //  return retVal;
    //}
    /**
    * Will return true if the provided id matches the local computer name or a local IPv4 or IPv6 address. Will return false if id is 'localhost' or '127.0.0.1'. Returns false in all other cases.
    */
    public static boolean idIsThisComputer(String id) throws Exception {
        id = id.ToLower();
        //Compare ID against the local host name.
        if (StringSupport.equals(Environment.MachineName.ToLower(), id))
        {
            return true;
        }
         
        IPHostEntry iphostentry = Dns.GetHostEntry(Environment.MachineName);
        for (Object __dummyForeachVar0 : iphostentry.AddressList)
        {
            //Check against the local computer's IP addresses (does not include 127.0.0.1). Includes IPv4 and IPv6.
            IPAddress ipaddress = (IPAddress)__dummyForeachVar0;
            if (StringSupport.equals(ipaddress.ToString(), id))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Will return true if the provided servername matches the local computer name or a local IPv4 or IPv6 address.  Will return true if servername is 'localhost' or '127.0.0.1'.  Returns false in all other cases.
    */
    public static boolean isRunningOnDbServer(String servername) throws Exception {
        servername = servername.ToLower();
        //Compare servername against the local host name.  Also check if the servername is "localhost".
        if (StringSupport.equals(Environment.MachineName.ToLower(), servername) || StringSupport.equals(servername, "localhost"))
        {
            return true;
        }
         
        try
        {
            //Check to see if the servername is an ipaddress that is a loopback (127.XXX.XXX.XXX).  Catches failure in parsing.
            if (IPAddress.IsLoopback(IPAddress.Parse(servername)))
            {
                return true;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        //not a valid IP address
        IPHostEntry iphostentry = Dns.GetHostEntry(Environment.MachineName);
        for (Object __dummyForeachVar1 : iphostentry.AddressList)
        {
            //Check against the local computer's IP addresses (does not include 127.0.0.1). Includes IPv4 and IPv6.
            IPAddress ipaddress = (IPAddress)__dummyForeachVar1;
            if (StringSupport.equals(ipaddress.ToString(), servername))
            {
                return true;
            }
             
        }
        return false;
    }

}


