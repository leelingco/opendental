//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;


public enum RemotingRole
{
    /**
    * This dll is on a local workstation, and this workstation has successfully connected directly to the database with no 'server' layer.
    */
    ClientDirect,
    /**
    * Workstation that is getting its data from a web service on the server.
    */
    ClientWeb,
    /**
    * This dll is part of a web server that is providing data via web services.
    */
    ServerWeb
}

