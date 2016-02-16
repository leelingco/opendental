//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataTransferObject;
import OpenDentBusiness.DtoException;
import OpenDentBusiness.DtoGetBool;
import OpenDentBusiness.DtoGetDS;
import OpenDentBusiness.DtoGetInt;
import OpenDentBusiness.DtoGetLong;
import OpenDentBusiness.DtoGetObject;
import OpenDentBusiness.DtoGetString;
import OpenDentBusiness.DtoGetTable;
import OpenDentBusiness.DtoGetTableLow;
import OpenDentBusiness.DtoGetVoid;
import OpenDentBusiness.OpenDentalServer.ServiceMain;
import OpenDentBusiness.PIn;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.XmlConverter;

public class RemotingClient   
{
    /**
    * This dll will be in one of these three roles.  There can be a dll on the client and a dll on the server, both involved in the logic.  This keeps track of which one is which.
    */
    public static RemotingRole RemotingRole = RemotingRole.ClientDirect;
    /**
    * If ClientWeb, then this is the URL to the server.
    */
    public static String ServerURI = new String();
    /**
    * If ClientWeb (middle tier user), proxy settings can be picked up from MiddleTierProxyConfig.xml.
    */
    public static String MidTierProxyAddress = new String();
    /**
    * If ClientWeb (middle tier user), proxy settings can be picked up from MiddleTierProxyConfig.xml.
    */
    public static String MidTierProxyUserName = new String();
    /**
    * If ClientWeb (middle tier user), proxy settings can be picked up from MiddleTierProxyConfig.xml.
    */
    public static String MidTierProxyPassword = new String();
    public static DataTable processGetTable(DtoGetTable dto) throws Exception {
        String result = sendAndReceive(dto);
        try
        {
            return XmlConverter.xmlToTable(result);
        }
        catch (Exception __dummyCatchVar0)
        {
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    public static DataTable processGetTableLow(DtoGetTableLow dto) throws Exception {
        String result = sendAndReceive(dto);
        try
        {
            return XmlConverter.xmlToTable(result);
        }
        catch (Exception __dummyCatchVar1)
        {
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    /**
    * 
    */
    public static DataSet processGetDS(DtoGetDS dto) throws Exception {
        String result = sendAndReceive(dto);
        if (Regex.IsMatch(result, "<DtoException xmlns:xsi="))
        {
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
         
        try
        {
            return XmlConverter.xmlToDs(result);
        }
        catch (Exception __dummyCatchVar2)
        {
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    /**
    * 
    */
    public static long processGetLong(DtoGetLong dto) throws Exception {
        String result = sendAndReceive(dto);
        try
        {
            return PIn.long(result);
        }
        catch (Exception __dummyCatchVar3)
        {
            //this might throw an exception if server unavailable
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    /**
    * 
    */
    public static int processGetInt(DtoGetInt dto) throws Exception {
        String result = sendAndReceive(dto);
        try
        {
            return PIn.int(result);
        }
        catch (Exception __dummyCatchVar4)
        {
            //this might throw an exception if server unavailable
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    /**
    * 
    */
    public static void processGetVoid(DtoGetVoid dto) throws Exception {
        String result = sendAndReceive(dto);
        //this might throw an exception if server unavailable
        if (!StringSupport.equals(result, "0"))
        {
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
         
    }

    /**
    * 
    */
    public static <T>T processGetObject(DtoGetObject dto) throws Exception {
        String result = sendAndReceive(dto);
        try
        {
            return XmlConverter.deserialize(result);
        }
        catch (Exception __dummyCatchVar5)
        {
            //this might throw an exception if server unavailable
            /*
            				XmlSerializer serializer=new XmlSerializer(typeof(T));
            					//Type.GetType("OpenDentBusiness."+dto.ObjectType));
            				StringReader strReader=new StringReader(result);
            				XmlReader xmlReader=XmlReader.Create(strReader);
            				object obj=serializer.Deserialize(xmlReader);
            				strReader.Close();
            				xmlReader.Close();
            				return (T)obj;*/
            DtoException exception = (DtoException)DataTransferObject.deserialize(result);
            throw new Exception(exception.Message);
        }
    
    }

    /**
    * 
    */
    public static String processGetString(DtoGetString dto) throws Exception {
        String result = sendAndReceive(dto);
        //this might throw an exception if server unavailable
        DtoException exception;
        try
        {
            exception = (DtoException)DataTransferObject.deserialize(result);
        }
        catch (Exception __dummyCatchVar6)
        {
            return result;
        }

        throw new Exception(exception.Message);
    }

    /**
    * 
    */
    public static boolean processGetBool(DtoGetBool dto) throws Exception {
        String result = sendAndReceive(dto);
        if (StringSupport.equals(result, "True"))
        {
            return true;
        }
         
        if (StringSupport.equals(result, "False"))
        {
            return false;
        }
         
        DtoException exception = (DtoException)DataTransferObject.deserialize(result);
        throw new Exception(exception.Message);
    }

    public static String sendAndReceive(DataTransferObject dto) throws Exception {
        String dtoString = dto.serialize();
        ServiceMain service = new OpenDentBusiness.OpenDentalServer.ServiceMain();
        service.setUrl(ServerURI);
        if (MidTierProxyAddress != null && !StringSupport.equals(MidTierProxyAddress, ""))
        {
            IWebProxy proxy = new WebProxy(MidTierProxyAddress);
            ICredentials cred = new NetworkCredential(MidTierProxyUserName, MidTierProxyPassword);
            proxy.Credentials = cred;
            service.Proxy = proxy;
        }
         
        //The default useragent is
        //Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 4.0.30319.296)
        //But DHS firewall doesn't allow that.  MSIE 6.0 is probably too old, and their firewall also looks for IE8Mercury.
        service.UserAgent = "Mozilla/4.0 (compatible; MSIE 7.0; MS Web Services Client Protocol 4.0.30319.296; IE8Mercury)";
        String result = service.processRequest(dtoString);
        //The web service (xml) serializer/deserializer is removing the '\r' portion of our newlines during the data transfer.
        //Replacing the string is not the best solution but it works for now. The replacing happens inside ProcessRequest() (server side) and here (client side).
        //It's done server side for usage purposes within the methods being called (exampe: inserting into db) and then on the client side for displaying purposes.
        if (result != null)
        {
            result = result.Replace("\n", "\r\n");
        }
         
        return result;
    }

}


