//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package OpenDentBusiness.Tests;

import OpenDentBusiness.RemotingClient;

public class ServiceWrapper  extends MarshalByRefObject 
{
    public ServiceWrapper() throws Exception {
        RemotingClient.OpenDentBusinessIsLocal = true;
        // Connect to the local mysql server.
        OpenDentBusiness.DataConnection connection = new OpenDentBusiness.DataConnection();
        connection.setDb("localhost","opendental","root","","root","",OpenDentBusiness.DatabaseType.MySql);
        service = new OpenDentalService();
        thread = new Thread((ThreadStart));
    }

    private OpenDentalService service = new OpenDentalService();
    public OpenDentalService getService() throws Exception {
        return service;
    }

    private Thread thread = new Thread();
    public Thread getThread() throws Exception {
        return thread;
    }

    public void start() throws Exception {
        if (getThread().IsAlive)
            stop();
         
        getThread().Start();
    }

    public void stop() throws Exception {
        if (getThread().IsAlive)
        {
            OpenDentalService.server.Stop();
            getThread().Abort();
        }
         
        while (getThread().IsAlive)
        {
            getThread().Sleep(100);
        }
    }

}


